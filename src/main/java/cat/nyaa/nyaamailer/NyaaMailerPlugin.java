package cat.nyaa.nyaamailer;

import cat.nyaa.nyaamailer.mailbox.MailboxCommands;
import cat.nyaa.nyaamailer.mailbox.MailboxListener;
import cat.nyaa.nyaamailer.mailbox.MailboxLocations;
import org.bukkit.plugin.java.JavaPlugin;

public class NyaaMailerPlugin extends JavaPlugin {
    MailboxLocations mailboxLocations;
    MailboxListener mailboxListener;
    MailboxCommands mailboxCommands;

    // dummy that makes cmdReceiver happy
    I18n i18n;

    @Override
    public void onEnable() {
        super.onEnable();
        this.reload();
    }

    private void reload() {
        mailboxLocations = new MailboxLocations(this);
        mailboxLocations.load();
        mailboxListener = new MailboxListener(this);
        i18n = new I18n();
        mailboxCommands = new MailboxCommands(this, i18n);

        this.getServer().getPluginManager().registerEvents(mailboxListener, this);

    }

    public MailboxListener getMailboxListener() {
        return mailboxListener;
    }

    public MailboxLocations getMailboxLocations() {
        return mailboxLocations;
    }
}
