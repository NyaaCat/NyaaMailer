package cat.nyaa.nyaamailer;

import cat.nyaa.nyaacore.LanguageRepository;
import cat.nyaa.nyaamailer.lang.MailboxCommonLang;
import cat.nyaa.nyaamailer.lang.MailboxLang;
import cat.nyaa.nyaamailer.support.LanguageAdapter;
import org.bukkit.plugin.Plugin;

public class I18n extends LanguageRepository {
    Plugin plugin;
    private LanguageAdapter adapter;
    MailboxCommonLang commonLang;

    public I18n(NyaaMailerPlugin plugin, LanguageAdapter adapter){
        this.plugin = plugin;
        this.adapter = adapter;
        this.commonLang = MailboxLang.getInstance().common;
    }

    @Override
    public String getFormatted(String key, Object... para) {
        return String.format(adapter.convert(commonLang, key).produce(), para);
    }

    @Override
    protected Plugin getPlugin() {
        return plugin;
    }

    @Override
    protected String getLanguage() {
        return "en_US";
    }
}
