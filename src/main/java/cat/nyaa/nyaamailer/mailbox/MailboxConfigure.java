package cat.nyaa.nyaamailer.mailbox;

import cat.nyaa.nyaacore.configuration.PluginConfigure;
import org.bukkit.plugin.java.JavaPlugin;

public class MailboxConfigure extends PluginConfigure {
    JavaPlugin plugin;
    public MailboxConfigure(JavaPlugin plugin){
        this.plugin = plugin;
    }

    /* Mailing System */
    @Serializable(name = "mail.handFee")
    public int mailHandFee = 10;
    @Serializable(name = "mail.chestFee")
    public int mailChestFee = 1000;
    @Serializable(name = "mail.cooldownTicks")
    public int mailCooldown = 20;
    @Serializable(name = "mail.timeoutTicks")
    public int mailTimeout = 200;


    @Override
    protected JavaPlugin getPlugin() {
        return null;
    }
}
