package cat.nyaa.nyaamailer;

import cat.nyaa.nyaacore.LanguageRepository;
import org.bukkit.plugin.Plugin;

public class I18n extends LanguageRepository {
    Plugin plugin;
    public I18n(NyaaMailerPlugin plugin){
        this.plugin = plugin;
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
