package cat.nyaa.nyaamailer.lang;

import land.melon.lab.simplelanguageloader.components.Text;

public class MailboxLang {
    public static Text rightClickTimeout = Text.of( "指定时间内未设置要作为邮箱的箱子，操作已取消。");
    public static Text alreadySet = Text.of( "设置新邮箱前，请撤销当前已设置过的邮箱。");
    public static Text haventSetSelf = Text.of( "你还没有邮箱。");
    public static Text haventSetOther = Text.of( "玩家 %s 尚未设置邮箱");
    public static Text setSuccess = Text.of( "设置邮箱成功。");
    public static Text setFail = Text.of( "设置邮箱失败。");
    public static Text removeSuccess = Text.of( "撤销邮箱成功。");
    public static Text nowRightClick = Text.of( "请在 5 秒内右键单击要作为邮箱的箱子。");
    public static Text permissionRequired = Text.of( "你没有权限执行此操作。");
    public static Text sameSrcDst = Text.of( "你不能将自己的邮箱发送给自己。");
    public static Text location = Text.of( "你的邮箱位置：X:{x} / Y:{y} / Z:{z}");
    public static Text handPrice = Text.of( "发送手中物品价格：{fee} / 次");
    public static Text chestPrice = Text.of( "发送一箱物品价格：{fee} / 次");
    public static Text sendCooldown = Text.of( "两次发送邮件间隔：{cooldown} 秒");
    public static Text sendTimeout = Text.of( "谢绝来信窗口时间：%.2f 秒");
    public static Text playerNoMailbox = Text.of( "玩家 {name} 尚未设置邮箱。");
    public static Text createMailboxHint = Text.of( """
                    玩家 {name} 想要向你发送一份邮件，但你还没有设置邮箱

                    请使用 &d/nu mailbox create&r 指令，按照提示进行邮箱的设置

                    """);
    public static Text recipientNoSpace = Text.of("对方邮箱没有足够的空间。");
    public static Text mailboxNoSpace = Text.of("玩家 {name} 想要向你发送一份邮件，但你的邮箱没有足够的空间。");
    public static Text mailSent = Text.of("成功向 {name} 发送了一份邮件，费用为：{mailChestFee}");
    public static Text mailReceived = Text.of("玩家 {name} 向你发送了一份邮件并已保存在你的邮箱。");
    public static Text nowRightClickSend = Text.of("现在请右键单击要发送给 %s 的成箱物品");
    public static Text chestProtected = Text.of("不能发送指定的箱子，因为该箱子已锁定。");
    public static Text mailSentNothing = Text.of("邮递小哥慨而慷，免费为你发空箱。");
    public static Text moneyInsufficient = Text.of("你没有足够的现金来发送邮件。");
    public static MailBoxAdminLang admin = new MailBoxAdminLang();
    public static MailboxUsageLang usage = new MailboxUsageLang();

}
