package cat.nyaa.nyaamailer.mailbox;

import cat.nyaa.ecore.EconomyCore;
import cat.nyaa.nyaacore.LanguageRepository;
import cat.nyaa.nyaacore.Message;
import cat.nyaa.nyaacore.cmdreceiver.Arguments;
import cat.nyaa.nyaacore.cmdreceiver.CommandReceiver;
import cat.nyaa.nyaacore.cmdreceiver.SubCommand;
import cat.nyaa.nyaacore.utils.InventoryUtils;
import cat.nyaa.nyaamailer.NyaaMailerPlugin;
import cat.nyaa.nyaamailer.lang.MailboxLang;
import land.melon.lab.simplelanguageloader.utils.Pair;
import me.crafter.mc.lockettepro.LocketteProAPI;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class MailboxCommands extends CommandReceiver {
    private final NyaaMailerPlugin plugin;
    private final EconomyCore economyCore;

    public MailboxCommands(Object plugin, LanguageRepository i18n) {
        super((NyaaMailerPlugin) plugin, i18n);
        this.plugin = (NyaaMailerPlugin) plugin;
        this.economyCore = ((NyaaMailerPlugin) plugin).getServer().getServicesManager().getRegistration(EconomyCore.class).getProvider();
    }

    @Override
    public String getHelpPrefix() {
        return "mailbox";
    }

    @SubCommand(value = "create", permission = "nu.mailbox")
    public void createMailbox(CommandSender sender, Arguments args) {
        Player p = asPlayer(sender);
        String tmp = args.top();
        if (tmp != null) {
            if (sender.hasPermission("nu.mailadmin")) {
                createMailbox(p, args.nextOfflinePlayer());
            } else {
                new Message(MailboxLang.permissionRequired.produce()).send(sender);
            }
            return;
        }
        if (plugin.getMailboxLocations().getMailboxLocation(p.getUniqueId()) != null) {
            new Message(MailboxLang.alreadySet.produce()).send(p);
            return;
        }
        plugin.getMailboxListener().registerRightClickCallback(p, 100,
                (Location clickedBlock) -> {
                    Block b = clickedBlock.getBlock();
                    if (b.getState() instanceof Chest) {
                        plugin.getMailboxLocations().updateLocationMapping(p.getUniqueId(), b.getLocation());
                        new Message(MailboxLang.setSuccess.produce()).send(p);
                        return;
                    }
                    new Message(MailboxLang.setFail.produce()).send(p);
                });
        new Message(MailboxLang.nowRightClick.produce()).send(p);
    }

    public void createMailbox(Player admin, OfflinePlayer player) {
        UUID id = player.getUniqueId();
        if (plugin.getMailboxLocations().getMailboxLocation(id) != null) {
            new Message(MailboxLang.admin.alreadySet.produce()).send(admin);
            return;
        }
        plugin.getMailboxListener().registerRightClickCallback(admin, 100,
                (Location clickedBlock) -> {
                    Block b = clickedBlock.getBlock();
                    if (b.getState() instanceof Chest) {
                        plugin.getMailboxLocations().updateNameMapping(id, player.getName());
                        plugin.getMailboxLocations().updateLocationMapping(id, b.getLocation());
                        new Message(MailboxLang.admin.successSet.produce()).send(admin);
                        if (player.isOnline()) {
                            Player tmp = plugin.getServer().getPlayer(id);
                            if (tmp != null) {
                                new Message(MailboxLang.admin.playerHintSet.produce()).send(tmp);
                            }
                        }
                        return;
                    }
                    new Message(MailboxLang.admin.failSet.produce()).send(admin);
                });
        new Message(MailboxLang.admin.rightClickSet.produce(Pair.of("name", player.getName()))).send(admin);
    }

    @SubCommand(value = "remove", permission = "nu.mailbox")
    public void removeMailbox(CommandSender sender, Arguments args) {
        Player p = asPlayer(sender);
        String tmp = args.top();
        if (tmp != null) {
            if (sender.hasPermission("nu.mailadmin")) {
                removeMailbox(p, args.nextOfflinePlayer());
            } else {
                new Message(MailboxLang.permissionRequired.produce()).send(sender);
            }
            return;
        }
        if (plugin.getMailboxLocations().getMailboxLocation(p.getUniqueId()) == null) {
            new Message(MailboxLang.haventSetSelf.produce()).send(p);
            return;
        }
        plugin.getMailboxLocations().updateLocationMapping(p.getUniqueId(), null);
        new Message(MailboxLang.removeSuccess.produce()).send(p);
    }

    public void removeMailbox(Player admin, OfflinePlayer player) {
        if (plugin.getMailboxLocations().getMailboxLocation(player.getUniqueId()) == null) {
            new Message(MailboxLang.admin.noMailbox.produce()).send(admin);
        } else {
            UUID id = player.getUniqueId();
            plugin.getMailboxLocations().updateLocationMapping(id, null);
            new Message(MailboxLang.admin.successRemove.produce()).send(admin);
            if (player.isOnline()) {
                new Message(MailboxLang.admin.playerHintRemoved.produce()).send(player.getPlayer());
            }
        }
    }

    @SubCommand(value = "info", permission = "nu.mailbox")
    public void infoMailbox(CommandSender sender, Arguments args) {
        String tmp = args.top();
        if (tmp != null) {
            if (sender.hasPermission("nu.mailadmin")) {
                infoMailbox(sender, args.nextOfflinePlayer());
            } else {
                new Message(MailboxLang.permissionRequired.produce()).send(sender);
            }
            return;
        }
        Player p = asPlayer(sender);

        Location loc = plugin.getMailboxLocations().getMailboxLocation(p.getUniqueId());
        if (loc == null) {
            new Message(MailboxLang.haventSetSelf.produce()).send(p);
        } else {

            new Message(MailboxLang.location.produce(
                    Pair.of("x", loc.getBlockX()),
                    Pair.of("y", loc.getBlockY()),
                    Pair.of("z", loc.getBlockZ())
            )).send(p);
            new Message(MailboxLang.handPrice.produce(Pair.of("fee", String.format("%.2f", plugin.getMailboxLocations().mailHandFee)))).send(p);
            new Message(MailboxLang.chestPrice.produce(Pair.of("fee", String.format("%.2f", plugin.getMailboxLocations().mailChestFee)))).send(p);
            new Message(MailboxLang.sendCooldown.produce(Pair.of("cooldown", String.format("%.2f", ((double) plugin.getMailboxLocations().mailCooldown) / 20D)))).send(p);
            //new Messagep.send(,); user.mailbox.infosend_timeout", ((double) plugin.getMailboxLocations().mailTimeout) / 20D);
        }
    }

    public void infoMailbox(CommandSender admin, OfflinePlayer player) {
        Location loc = plugin.getMailboxLocations().getMailboxLocation(player.getUniqueId());
        if (loc != null) {
            new Message(MailboxLang.admin.info.produce(
                    Pair.of("name", player.getName()),
                    Pair.of("uuid", player.getUniqueId().toString()),
                    Pair.of("x", loc.getBlockX()),
                    Pair.of("y", loc.getBlockY()),
                    Pair.of("z", loc.getBlockZ())
            )).send(admin);
        } else {
            new Message(MailboxLang.admin.noMailbox.produce()).send(admin);
        }
    }

    @SubCommand(value = "send", permission = "nu.mailbox")
    public void sendMailbox(CommandSender sender, Arguments args) {
        Player p = asPlayer(sender);
        ItemStack stack = getItemInHand(sender);
        if (args.top() == null) {
            new Message(MailboxLang.usage.sendUsage.produce()).send(sender);
            return;
        }

        if (economyCore.getPlayerBalance(p.getUniqueId()) < plugin.getMailboxLocations().mailHandFee) {
            new Message(MailboxLang.moneyInsufficient.produce()).send(p);
            return;
        }
        OfflinePlayer toPlayer = args.nextOfflinePlayer();
        UUID recipient = toPlayer.getUniqueId();
        Location toLocation = plugin.getMailboxLocations().getMailboxLocation(recipient);

        // Check remote mailbox
        if (recipient != null && toLocation != null) {
            Block b = toLocation.getBlock();
            if (!(b.getState() instanceof InventoryHolder)) {
                plugin.getMailboxLocations().updateLocationMapping(recipient, null);
                toLocation = null;
            }
        }

        if (recipient == null) {
            new Message(MailboxLang.playerNoMailbox.produce(Pair.of("name", toPlayer.getName()))).send(sender);
            return;
        } else if (toLocation == null) {
            new Message(MailboxLang.playerNoMailbox.produce(Pair.of("name", toPlayer.getName()))).send(sender);
            if (toPlayer.isOnline()) {
                new Message(MailboxLang.createMailboxHint.produce(Pair.of("name", sender.getName()))).send(toPlayer.getPlayer());
            }
            return;
        }

        Player recp = null;
        if (toPlayer.isOnline()) recp = (Player) toPlayer;
        Inventory targetInventory = ((InventoryHolder) toLocation.getBlock().getState()).getInventory();
        if (!InventoryUtils.hasEnoughSpace(targetInventory, stack)) {
            new Message(MailboxLang.recipientNoSpace.produce()).send(sender);
            if (recp != null) {
                new Message(MailboxLang.mailboxNoSpace.produce(Pair.of("name", sender.getName()))).send(recp);
            }
        } else {
            economyCore.depositSystemVault(plugin.getMailboxLocations().mailHandFee);

            InventoryUtils.addItem(targetInventory, stack);
            p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
            new Message(MailboxLang.mailSent.produce(
                    Pair.of("name", toPlayer.getName()),
                    Pair.of("fee", plugin.getMailboxLocations().mailHandFee))
            ).send(sender);
            if (recp != null) {
                new Message(MailboxLang.mailReceived.produce(Pair.of("name", sender.getName()))).send(recp);
            }
            economyCore.withdrawPlayer(p.getUniqueId(), plugin.getMailboxLocations().mailHandFee);
        }
    }

    @SubCommand(value = "sendchest", permission = "nu.mailbox")
    public void sendBoxMailbox(CommandSender sender, Arguments args) {
        Player p = asPlayer(sender);
        if (args.top() == null) {
            new Message(MailboxLang.usage.sendchestUsage.produce()).send(sender);
            return;
        }

        if (economyCore.getPlayerBalance(p.getUniqueId()) < plugin.getMailboxLocations().mailChestFee) {
            new Message(MailboxLang.moneyInsufficient.produce()).send(p);
            return;
        }
        OfflinePlayer toPlayer = args.nextOfflinePlayer();
        UUID recipient = toPlayer.getUniqueId();
        Location toLocation = plugin.getMailboxLocations().getMailboxLocation(recipient);

        // Check remote mailbox
        if (recipient != null && toLocation != null) {
            Block b = toLocation.getBlock();
            if (!(b.getState() instanceof InventoryHolder)) {
                plugin.getMailboxLocations().updateLocationMapping(recipient, null);
                toLocation = null;
            }
        }

        if (recipient == null) {
            new Message(MailboxLang.playerNoMailbox.produce(Pair.of("name", toPlayer.getName()))).send(sender);
            return;
        } else if (toLocation == null) {
            new Message(MailboxLang.playerNoMailbox.produce(Pair.of("name", toPlayer.getName()))).send(sender);
            if (toPlayer.isOnline()) {
                new Message(MailboxLang.createMailboxHint.produce(Pair.of("name", sender.getName()))).send(toPlayer.getPlayer());
            }
            return;
        }

        final Location toLocationFinal = toLocation;
        Player recp = null;
        if (toPlayer.isOnline()) recp = (Player) toPlayer;
        final Player recpFinal = recp;

        plugin.getMailboxListener().registerRightClickCallback(p, 100,
                (Location boxLocation) -> {
                    if (economyCore.getPlayerBalance(p.getUniqueId()) < plugin.getMailboxLocations().mailChestFee) {
                        new Message(MailboxLang.moneyInsufficient.produce()).send(p);
                        return;
                    }
                    Block b = boxLocation.getBlock();
                    if (plugin.getServer().getPluginManager().getPlugin("LockettePro") != null) {
                        if (LocketteProAPI.isLocked(b) && !LocketteProAPI.isUser(b, p)) {
                            new Message(MailboxLang.chestProtected.produce()).send(p);
                            return;
                        }
                    }

                    Inventory fromInventory = ((InventoryHolder) b.getState()).getInventory();
                    Inventory toInventory = ((InventoryHolder) toLocationFinal.getBlock().getState()).getInventory();
                    ItemStack[] fromBefore = fromInventory.getStorageContents();
                    ItemStack[] fromAfter = new ItemStack[fromBefore.length];
                    fromInventory.setStorageContents(fromAfter);
                    ItemStack[] to = toInventory.getStorageContents();
                    int nextSlot = 0;
                    boolean itemMoved = false;
                    for (int i = 0; i < fromBefore.length; i++) {
                        if (fromBefore[i] != null && fromBefore[i].getType() != Material.AIR) {
                            while (nextSlot < to.length && to[nextSlot] != null && to[nextSlot].getType() != Material.AIR) {
                                nextSlot++;
                            }
                            if (nextSlot >= to.length) {
                                new Message(MailboxLang.recipientNoSpace.produce()).send(sender);
                                if (recpFinal != null) {
                                    new Message(MailboxLang.mailboxNoSpace.produce(Pair.of("name", sender.getName()))).send(recpFinal);
                                }
                                fromInventory.setStorageContents(fromBefore);
                                return;
                            }
                            to[nextSlot] = fromBefore[i].clone();
                            itemMoved = true;
                            nextSlot++;
                        }
                    }
                    if (itemMoved) {
                        economyCore.depositSystemVault(plugin.getMailboxLocations().mailChestFee);

                        toInventory.setStorageContents(to);
                        new Message(MailboxLang.mailSent.produce(
                                Pair.of("name", toPlayer.getName()),
                                Pair.of("mailChestFee", plugin.getMailboxLocations().mailChestFee)
                        )).send(sender);
                        if (recpFinal != null) {
                            new Message(MailboxLang.mailReceived.produce(Pair.of("name", sender.getName()))).send(recpFinal);
                        }
                        economyCore.withdrawPlayer(p.getUniqueId(), plugin.getMailboxLocations().mailChestFee);
                    } else {
                        new Message(MailboxLang.mailSentNothing.produce()).send(sender);
                    }
                });
        new Message(MailboxLang.nowRightClickSend.produce(Pair.of("name", toPlayer.getName()))).send(p);
    }
}