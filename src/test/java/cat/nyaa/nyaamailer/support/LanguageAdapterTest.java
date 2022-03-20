package cat.nyaa.nyaamailer.support;

import cat.nyaa.nyaamailer.NyaaMailerPlugin;
import cat.nyaa.nyaamailer.lang.MailboxCommonLang;
import land.melon.lab.simplelanguageloader.components.Text;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.logging.Logger;

public class LanguageAdapterTest {

    LanguageAdapter languageAdapter;

    @BeforeAll
    public void setup(){
        NyaaMailerPlugin mock = Mockito.mock(NyaaMailerPlugin.class);
        Mockito.when(mock.getLogger()).thenReturn(Logger.getLogger("LanguageAdapterTest"));
        languageAdapter = new LanguageAdapter(MailboxCommonLang.class, mock);
    }

    @Test
    public void testInit(){
        MailboxCommonLang obj = new MailboxCommonLang();
        String[] internalKeys = {
                "internal.info.command_complete",
                "internal.error.not_player",
                "internal.error.no_item_offhand" ,
                "internal.error.no_item_hand",
                "internal.error.invalid_command_arg",
                "internal.info.usage_prompt",
                "internal.error.no_required_permission",
                "internal.error.command_exception",
                "manual.mailbox.description",
                "manual.mailbox.usage",
                "manual.mailbox.create.description",
                "manual.mailbox.create.usage",
                "manual.mailbox.info.description",
                "manual.mailbox.info.usage",
                "manual.mailbox.remo.description",
                "manual.mailbox.remo.usage",
                "manual.mailbox.send.description",
                "manual.mailbox.send.usage",
                "manual.mailbox.send.description",
                "manual.mailbox.send.usage",
                "manual.mailbox.help.description",
                "manual.mailbox.help.usage",
                "manual.no_description",
                "manual.no_usage",
        };
        for (String internalKey : internalKeys) {
            Text convert = languageAdapter.convert(obj, internalKey);
            Assertions.assertNotNull(convert);
        }
    }

}