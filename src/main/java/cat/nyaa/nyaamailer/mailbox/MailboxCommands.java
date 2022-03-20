package cat.nyaa.nyaamailer.mailbox;

import cat.nyaa.ecore.EconomyCore;
import cat.nyaa.nyaacore.LanguageRepository;
import cat.nyaa.nyaacore.cmdreceiver.Arguments;
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

public class MailboxCommands extends BaseCommand {
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

    @SubCommand(value = "create", permission = " mailer.command")
    public void createMailbox(CommandSender sender, Arguments args) {
        Player p = asPlayer(sender);
        String tmp = args.top();
        if (tmp != null) {
            if (sender.hasPermission("nu.mailadmin")) {
                createMailbox(p, args.nextOfflinePlayer());
            } else {
                sender.sendMessage(MailboxLang.getInstance().permissionRequired.produce());
            }
            return;
        }
        if (plugin.getMailboxLocations().getMailboxLocation(p.getUniqueId()) != null) {
            p.sendMessage(MailboxLang.getInstance().alreadySet.produce());
            return;
        }
        plugin.getMailboxListener().registerRightClickCallback(p, 100,
                (Location clickedBlock) -> {
                    Block b = clickedBlock.getBlock();
                    if (b.getState() instanceof Chest) {
                        plugin.getMailboxLocations().updateLocationMapping(p.getUniqueId(), b.getLocation());
                        p.sendMessage(MailboxLang.getInstance().setSuccess.produce());
                        return;
                    }
                    p.sendMessage(MailboxLang.getInstance().setFail.produce());
                });
        p.sendMessage(MailboxLang.getInstance().nowRightClick.produce());
    }

    public void createMailbox(Player admin, OfflinePlayer player) {
        UUID id = player.getUniqueId();
        if (plugin.getMailboxLocations().getMailboxLocation(id) != null) {
            admin.sendMessage(MailboxLang.getInstance().admin.alreadySet.produce());
            return;
        }
        plugin.getMailboxListener().registerRightClickCallback(admin, 100,
                (Location clickedBlock) -> {
                    Block b = clickedBlock.getBlock();
                    if (b.getState() instanceof Chest) {
                        plugin.getMailboxLocations().updateNameMapping(id, player.getName());
                        plugin.getMailboxLocations().updateLocationMapping(id, b.getLocation());
                        admin.sendMessage(MailboxLang.getInstance().admin.successSet.produce());
                        if (player.isOnline()) {
                            Player tmp = plugin.getServer().getPlayer(id);
                            if (tmp != null) {
                                tmp.sendMessage(MailboxLang.getInstance().admin.playerHintSet.produce());
                            }
                        }
                        return;
                    }
                    admin.sendMessage(MailboxLang.getInstance().admin.failSet.produce());
                });
        admin.sendMessage(MailboxLang.getInstance().admin.rightClickSet.produce(Pair.of("name", player.getName())));
    }

    @SubCommand(value = "remove", permission = " mailer.command")
    public void removeMailbox(CommandSender sender, Arguments args) {
        Player p = asPlayer(sender);
        String tmp = args.top();
        if (tmp != null) {
            if (sender.hasPermission("nu.mailadmin")) {
                removeMailbox(p, args.nextOfflinePlayer());
            } else {
                sender.sendMessage(MailboxLang.getInstance().permissionRequired.produce());
            }
            return;
        }
        if (plugin.getMailboxLocations().getMailboxLocation(p.getUniqueId()) == null) {
            p.sendMessage(MailboxLang.getInstance().haventSetSelf.produce());
            return;
        }
        plugin.getMailboxLocations().updateLocationMapping(p.getUniqueId(), null);
        p.sendMessage(MailboxLang.getInstance().removeSuccess.produce());
    }

    public void removeMailbox(Player admin, OfflinePlayer player) {
        if (plugin.getMailboxLocations().getMailboxLocation(player.getUniqueId()) == null) {
            admin.sendMessage(MailboxLang.getInstance().admin.noMailbox.produce());
        } else {
            UUID id = player.getUniqueId();
            plugin.getMailboxLocations().updateLocationMapping(id, null);
            admin.sendMessage(MailboxLang.getInstance().admin.successRemove.produce());
            if (player.isOnline()) {
                player.getPlayer().sendMessage(MailboxLang.getInstance().admin.playerHintRemoved.produce());
            }
        }
    }

    @SubCommand(value = "info", permission = " mailer.command")
    public void infoMailbox(CommandSender sender, Arguments args) {
        String tmp = args.top();
        if (tmp != null) {
            if (sender.hasPermission("nu.mailadmin")) {
                infoMailbox(sender, args.nextOfflinePlayer());
            } else {
                sender.sendMessage(MailboxLang.getInstance().permissionRequired.produce());
            }
            return;
        }
        Player p = asPlayer(sender);

        Location loc = plugin.getMailboxLocations().getMailboxLocation(p.getUniqueId());
        if (loc == null) {
            p.sendMessage(MailboxLang.getInstance().haventSetSelf.produce());
        } else {

            p.sendMessage(MailboxLang.getInstance().location.produce(
                    Pair.of("x", loc.getBlockX()),
                    Pair.of("y", loc.getBlockY()),
                    Pair.of("z", loc.getBlockZ())
            ));
            p.sendMessage(MailboxLang.getInstance().handPrice.produce(Pair.of("fee", String.format("%.2f", (double) plugin.getMailboxConfigure().mailHandFee))));
            p.sendMessage(MailboxLang.getInstance().chestPrice.produce(Pair.of("fee", String.format("%.2f", (double) plugin.getMailboxConfigure().mailChestFee))));
            p.sendMessage(MailboxLang.getInstance().sendCooldown.produce(Pair.of("cooldown", String.format("%.2f", ((double) plugin.getMailboxConfigure().mailCooldown) / 20D))));
        }
    }

    public void infoMailbox(CommandSender admin, OfflinePlayer player) {
        Location loc = plugin.getMailboxLocations().getMailboxLocation(player.getUniqueId());
        if (loc != null) {
            admin.sendMessage(MailboxLang.getInstance().admin.info.produce(
                    Pair.of("name", player.getName()),
                    Pair.of("uuid", player.getUniqueId().toString()),
                    Pair.of("x", loc.getBlockX()),
                    Pair.of("y", loc.getBlockY()),
                    Pair.of("z", loc.getBlockZ())
            ));
        } else {
            admin.sendMessage(MailboxLang.getInstance().admin.noMailbox.produce());
        }
    }

    @SubCommand(value = "send", permission = " mailer.command")
    public void sendMailbox(CommandSender sender, Arguments args) {
        Player p = asPlayer(sender);
        ItemStack stack = getItemInHand(sender);
        if (args.top() == null) {
            sender.sendMessage(MailboxLang.getInstance().common.sendUsage.produce());
            return;
        }

        if (economyCore.getPlayerBalance(p.getUniqueId()) < plugin.getMailboxConfigure().mailHandFee) {
            p.sendMessage(MailboxLang.getInstance().moneyInsufficient.produce());
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
            sender.sendMessage(MailboxLang.getInstance().playerNoMailbox.produce(Pair.of("name", toPlayer.getName())));
            return;
        } else if (toLocation == null) {
            sender.sendMessage(MailboxLang.getInstance().playerNoMailbox.produce(Pair.of("name", toPlayer.getName())));
            if (toPlayer.isOnline()) {
                toPlayer.getPlayer().sendMessage(MailboxLang.getInstance().createMailboxHint.produce(Pair.of("name", sender.getName())));
            }
            return;
        }

        Player recp = null;
        if (toPlayer.isOnline()) recp = (Player) toPlayer;
        Inventory targetInventory = ((InventoryHolder) toLocation.getBlock().getState()).getInventory();
        if (!InventoryUtils.hasEnoughSpace(targetInventory, stack)) {
            sender.sendMessage(MailboxLang.getInstance().recipientNoSpace.produce());
            if (recp != null) {
                recp.sendMessage(MailboxLang.getInstance().mailboxNoSpace.produce(Pair.of("name", sender.getName())));
            }
        } else {
            economyCore.depositSystemVault(plugin.getMailboxConfigure().mailHandFee);

            InventoryUtils.addItem(targetInventory, stack);
            p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
            sender.sendMessage(MailboxLang.getInstance().mailSent.produce(
                    Pair.of("name", toPlayer.getName()),
                    Pair.of("mailChestFee", plugin.getMailboxConfigure().mailHandFee))
            );
            if (recp != null) {
                recp.sendMessage(MailboxLang.getInstance().mailReceived.produce(Pair.of("name", sender.getName())));
            }
            economyCore.withdrawPlayer(p.getUniqueId(), plugin.getMailboxConfigure().mailHandFee);
        }
    }

    @SubCommand(value = "sendchest", permission = " mailer.command")
    public void sendBoxMailbox(CommandSender sender, Arguments args) {
        Player p = asPlayer(sender);
        if (args.top() == null) {
            sender.sendMessage(MailboxLang.getInstance().common.sendchestUsage.produce());
            return;
        }

        if (economyCore.getPlayerBalance(p.getUniqueId()) < plugin.getMailboxConfigure().mailChestFee) {
            p.sendMessage(MailboxLang.getInstance().moneyInsufficient.produce());
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
            sender.sendMessage(MailboxLang.getInstance().playerNoMailbox.produce(Pair.of("name", toPlayer.getName())));
            return;
        } else if (toLocation == null) {
            sender.sendMessage(MailboxLang.getInstance().playerNoMailbox.produce(Pair.of("name", toPlayer.getName())));
            if (toPlayer.isOnline()) {
                sender.sendMessage(MailboxLang.getInstance().createMailboxHint.produce(Pair.of("name", sender.getName())));
            }
            return;
        }

        final Location toLocationFinal = toLocation;
        Player recp = null;
        if (toPlayer.isOnline()) recp = (Player) toPlayer;
        final Player recpFinal = recp;

        plugin.getMailboxListener().registerRightClickCallback(p, 100,
                (Location boxLocation) -> {
                    if (economyCore.getPlayerBalance(p.getUniqueId()) < plugin.getMailboxConfigure().mailChestFee) {
                        p.sendMessage(MailboxLang.getInstance().moneyInsufficient.produce());
                        return;
                    }
                    Block b = boxLocation.getBlock();
                    if (plugin.getServer().getPluginManager().getPlugin("LockettePro") != null) {
                        if (LocketteProAPI.isLocked(b) && !LocketteProAPI.isUser(b, p)) {
                            p.sendMessage(MailboxLang.getInstance().chestProtected.produce());
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
                                sender.sendMessage(MailboxLang.getInstance().recipientNoSpace.produce());
                                if (recpFinal != null) {
                                    recpFinal.sendMessage(MailboxLang.getInstance().mailboxNoSpace.produce(Pair.of("name", sender.getName())));
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
                        economyCore.depositSystemVault(plugin.getMailboxConfigure().mailChestFee);

                        toInventory.setStorageContents(to);
                        sender.sendMessage(MailboxLang.getInstance().mailSent.produce(
                                Pair.of("name", toPlayer.getName()),
                                Pair.of("mailChestFee", plugin.getMailboxConfigure().mailChestFee)
                        ));
                        if (recpFinal != null) {
                            recpFinal.sendMessage(MailboxLang.getInstance().mailReceived.produce(Pair.of("name", sender.getName())));
                        }
                        economyCore.withdrawPlayer(p.getUniqueId(), plugin.getMailboxConfigure().mailChestFee);
                    } else {
                        sender.sendMessage(MailboxLang.getInstance().mailSentNothing.produce());
                    }
                });
        p.sendMessage(MailboxLang.getInstance().nowRightClickSend.produce(Pair.of("name", toPlayer.getName())));
    }

    @SubCommand(value = "reload", permission = "mailer.admin")
    public void reload(CommandSender sender, Arguments args) {
        plugin.reload();
    }


}
