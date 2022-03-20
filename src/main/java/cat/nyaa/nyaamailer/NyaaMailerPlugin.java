package cat.nyaa.nyaamailer;

import cat.nyaa.nyaamailer.lang.MailboxLang;
import cat.nyaa.nyaamailer.mailbox.MailboxCommands;
import cat.nyaa.nyaamailer.mailbox.MailboxConfigure;
import cat.nyaa.nyaamailer.mailbox.MailboxListener;
import cat.nyaa.nyaamailer.mailbox.MailboxLocations;
import com.google.gson.GsonBuilder;
import land.melon.lab.simplelanguageloader.SimpleLanguageLoader;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class NyaaMailerPlugin extends JavaPlugin {
    MailboxLocations mailboxLocations;
    MailboxListener mailboxListener;
    MailboxCommands mailboxCommands;
    MailboxConfigure mailboxConfigure;

    // dummy that makes cmdReceiver happy
    I18n i18n;
    File languageFile = new File(getDataFolder(), "language.json");


    @Override
    public void onEnable() {
        super.onEnable();
            this.reload();

    }

    public void reload() {
        try {
            mailboxLocations = new MailboxLocations(this);
            mailboxLocations.load();
            mailboxListener = new MailboxListener(this);
            i18n = new I18n(this);
            mailboxCommands = new MailboxCommands(this, i18n);
            mailboxConfigure = new MailboxConfigure(this);
            mailboxConfigure.load();

            this.getServer().getPluginManager().registerEvents(mailboxListener, this);
            getCommand("mailer").setExecutor(mailboxCommands);
            new SimpleLanguageLoader().loadOrInitialize(languageFile, MailboxLang.class, MailboxLang::getInstance);
        } catch (IOException e) {
            throw new RuntimeException("error loading config.", e);
        }

    }

    public MailboxListener getMailboxListener() {
        return mailboxListener;
    }

    public MailboxLocations getMailboxLocations() {
        return mailboxLocations;
    }

    public MailboxConfigure getMailboxConfigure() {
        return mailboxConfigure;
    }
}
