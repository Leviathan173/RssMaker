import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {
    private static final String EXPECT_RES = "1bb624b343a8b7d6496d8def96e26b796f7dc4cd\n" +
            "1bb624b343a8b7d6496d8def96e26b796f7dc4ce\n";

    public boolean Test() {
        Print.Println("开始正则表达式测试");
        String magent = "";
        String content = "这个游戏是本月除了幻想万华镜外销量最高的游戏。" +
                " eushully社团开发的SLG游戏,剧情主要就是男主掌握诱惑技能，" +
                "然后一路收女。 游戏里大部分的女角色都有独立CG也会流血，天使恶" +
                "魔什么的一大堆。有兴趣可以玩一下，游戏性还是很不错的 继续阅读 → \n" +
                "1bb624b343a8b7d6496d8def96e26b796f7dc4cd这个游戏是本月" +
                "除了幻想万华镜外销量最高的游戏。 eushully社团开发的SLG游戏,剧" +
                "情主要就是男主掌握诱惑技能，然后一路收女。 游戏里大部分的女角色都" +
                "有独立CG也会流血，天使恶魔什么的一大堆。有兴趣可以玩一下，游戏性" +
                "还是很不错的 继续阅读 → \n" +
                "1bb624b343a8b7d6496d8def96e26b796f7dc4ce";
        Pattern pattern = Pattern.compile("[a-zA-Z0-9]{40,}");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            for (int i = 0; i <= matcher.groupCount(); i++) {
                magent += matcher.group(i);
                magent += "\n";
            }
        }
        if(!magent.equals(EXPECT_RES)){
            Print.Println("正则表达式测试出错！");
            return false;
        }
        Print.Println("正则表达式测试完成");
        return true;
    }
}
