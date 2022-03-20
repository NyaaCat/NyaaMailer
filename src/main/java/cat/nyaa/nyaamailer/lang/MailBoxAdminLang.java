package cat.nyaa.nyaamailer.lang;

import land.melon.lab.simplelanguageloader.components.Text;

public class MailBoxAdminLang {
    public Text rightClickSet = Text.of("右键单击箱子来为 {name} 设置邮箱。");
    public Text alreadySet = Text.of("该玩家已经设定过邮箱");
    public Text failSet = Text.of("设置邮箱失败");
    public Text successSet = Text.of("设置邮箱成功！");
    public Text playerHintSet = Text.of("你的邮箱已由管理员进行设置。");
    public Text noMailbox = Text.of("该玩家尚未设置邮箱");
    public Text successRemove = Text.of("撤销邮箱成功");
    public Text playerHintRemoved = Text.of("你的邮箱已被管理员撤销。");
    public Text info = Text.of("玩家 {name}({uuid}) 的邮箱位置：X:{x} / Y:{y} / Z:{z}");
}
