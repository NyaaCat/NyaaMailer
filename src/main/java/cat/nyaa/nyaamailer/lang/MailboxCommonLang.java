package cat.nyaa.nyaamailer.lang;


import cat.nyaa.nyaamailer.support.LanguageKey;
import land.melon.lab.simplelanguageloader.components.Text;

/**
 * adaptor for NyaaCore related lang keys.
 */
public class MailboxCommonLang {

    @LanguageKey("manual.mailbox.description")
    public Text main = Text.of("设置邮箱、发送邮件");

    @LanguageKey("manual.mailbox.usage")
    public Text usage = Text.of("/nu mailbox help");

    @LanguageKey("manual.mailbox.create.description")
    public Text create = Text.of("新建邮箱");

    @LanguageKey("manual.mailbox.create.usage")
    public Text createUsage = Text.of("/nu mailbox create [玩家名称]");

    @LanguageKey("manual.mailbox.info.description")
    public Text info = Text.of("示邮箱信息");

    @LanguageKey("manual.mailbox.info.usage")
    public Text infoUsage = Text.of("/nu mailbox info [玩家名称]");

    @LanguageKey("manual.mailbox.remo.description")
    public Text remove = Text.of("撤销邮箱");

    @LanguageKey("manual.mailbox.remo.usage")
    public Text removeUsage = Text.of("/nu mailbox remove [玩家名称]");

    @LanguageKey("manual.mailbox.send.description")
    public Text send = Text.of("将手中的物品发送给另一玩家");

    @LanguageKey("manual.mailbox.send.usage")
    public Text sendUsage = Text.of("/nu mailbox send <玩家名称>");

    @LanguageKey("manual.mailbox.send.description")
    public Text sendchest = Text.of("将一箱物品发送给另一玩家");

    @LanguageKey("manual.mailbox.send.usage")
    public Text sendchestUsage = Text.of("/nu mailbox sendchest <玩家名称>");

    @LanguageKey("manual.mailbox.help.description")
    public Text help = Text.of("显示帮助信息");

    @LanguageKey("manual.mailbox.help.usage")
    public Text helpUsage = Text.of("/nu mailbox help");


    @LanguageKey("internal.info.using_language")
    Text usingLanguage = Text.of("Now using language: %s");

    @LanguageKey("internal.info.command_complete")
    Text commandComplete = Text.of("Command Executed!");

    @LanguageKey("internal.info.usage_prompt")
    Text usagePrompt = Text.of("Usage: %s");

    @LanguageKey("internal.error.bad_subcommand")
    Text badSubcommand = Text.of("Bad subcommand handler: %s");

    @LanguageKey("internal.error.not_player")
    Text notPlayer = Text.of("Only players can do this");

    @LanguageKey("internal.error.no_item_hand")
    Text noItemHand = Text.of("No item in main hand");

    @LanguageKey("internal.error.no_item_offhand")
    Text noItemOffhand = Text.of("No item in off hand");

    @LanguageKey("internal.error.command_exception")
    Text commandException = Text.of("Internal server error. Unable to run command.");

    @LanguageKey("internal.error.no_required_permission")
    Text noRequiredPermission = Text.of("You do not have the required permission: %s");

    @LanguageKey("internal.error.invalid_command_arg")
    Text invalidCommandArg = Text.of("Invalid command line arguments.");

    @LanguageKey("internal.error.bad_int")
    Text badInt = Text.of("Not a valid integer: %s");

    @LanguageKey("internal.error.bad_double")
    Text badDouble = Text.of("Not a valid number: %s");

    @LanguageKey("internal.error.bad_enum")
    Text badEnum = Text.of("Not valid for enum type: %s. Values: %s");

    @LanguageKey("internal.error.bad_decimal_pattern")
    Text badDecimalPattern = Text.of("Not a valid pattern: %s");

    @LanguageKey("internal.error.no_more_int")
    Text noMoreInt = Text.of("No more integers in argument");

    @LanguageKey("internal.error.no_more_double")
    Text noMoreDouble = Text.of("No more numbers in argument");

    @LanguageKey("internal.error.no_more_enum")
    Text noMoreEnum = Text.of("No more enum values in argument");

    @LanguageKey("internal.error.no_more_bool")
    Text noMoreBool = Text.of("No more booleans in argument");

    @LanguageKey("internal.error.no_more_string")
    Text noMoreString = Text.of("No more strings in argument");

    @LanguageKey("internal.error.no_more_player")
    Text noMorePlayer = Text.of("No more player name in argument");

    @LanguageKey("internal.error.no_more_entity")
    Text noMoreEntity = Text.of("No more entity id in argument");

    @LanguageKey("internal.error.assert_fail")
    Text assertFail = Text.of("Command assertion fail, Expect: %s, Actual: %s");

    @LanguageKey("internal.error.player_not_found")
    Text playerNotFound = Text.of("Cannot find player \"%s\"");

    @LanguageKey("internal.error.entity_not_found")
    Text entityNotFound = Text.of("Cannot find entity \"%s\"");

    @LanguageKey("internal.named_argument.missing_arg")
    Text missingArg = Text.of("Missing argument: %s");

    @LanguageKey("internal.named_argument.not_int")
    Text notInt = Text.of("Argument \"%s\" is not an integer: %s");

    @LanguageKey("internal.named_argument.not_double")
    Text notDouble = Text.of("Argument \"%s\" is not a double: %s");

    @LanguageKey("manual.no_description")
    Text noDescription = Text.of("No description");

    @LanguageKey("manual.no_usage")
    Text noUsage = Text.of("No usage");

}
