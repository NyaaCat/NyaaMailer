package cat.nyaa.nyaamailer.lang;

import land.melon.lab.simplelanguageloader.components.Text;

public class MailboxUsageLang {
    public Text main = Text.of("设置邮箱、发送邮件");
    public Text usage = Text.of("/nu mailbox help");
    public Text create = Text.of("建邮箱");
    public Text createUsage = Text.of("/nu mailbox create [玩家名称]");
    public Text info = Text.of("示邮箱信息");
    public Text infoUsage = Text.of("/nu mailbox info [玩家名称]");
    public Text remove = Text.of("撤销邮箱");
    public Text removeUsage = Text.of("/nu mailbox remove [玩家名称]");
    public Text send = Text.of("将手中的物品发送给另一玩家");
    public Text sendUsage = Text.of("/nu mailbox send <玩家名称>");
    public Text sendchest = Text.of("将一箱物品发送给另一玩家");
    public Text sendchestUsage = Text.of("/nu mailbox sendchest <玩家名称>");
    public Text help = Text.of("显示帮助信息");
    public Text helpUsage = Text.of("/nu mailbox help");
}
