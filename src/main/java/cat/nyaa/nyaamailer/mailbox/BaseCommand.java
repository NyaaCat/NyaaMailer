package cat.nyaa.nyaamailer.mailbox;

import cat.nyaa.nyaacore.ILocalizer;
import cat.nyaa.nyaacore.Message;
import cat.nyaa.nyaacore.cmdreceiver.BadCommandException;
import cat.nyaa.nyaacore.cmdreceiver.CommandReceiver;
import cat.nyaa.nyaamailer.NyaaMailerPlugin;
import cat.nyaa.nyaamailer.lang.MailboxLang;
import land.melon.lab.simplelanguageloader.components.Text;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public abstract class BaseCommand extends CommandReceiver {
    protected NyaaMailerPlugin plugin;
    /**
     * @param plugin for logging purpose only
     * @param _i18n  i18n
     */
    public BaseCommand(Plugin plugin, ILocalizer _i18n) {
        super(plugin, _i18n);
        this.plugin = (NyaaMailerPlugin) plugin;
    }

    @Override
    public void msg(CommandSender target, String template, Object... args) {
        Text convert = plugin.getAdapter().convert(MailboxLang.getInstance().common, template);
        if (convert == null){
            throw new BadCommandException();
        }
        new Message(String.format(convert.produce(), args))
                .send(target);
    }
}
