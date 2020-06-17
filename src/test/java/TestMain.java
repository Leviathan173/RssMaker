import java.util.*;

public class TestMain {
    private static final String REQUEST = "连接测试";
    private static final String MAIL = "邮件测试";
    private static final String JSOUP = "Jsoup测试";
    private static final String REGEX = "正则表达式测试";
    public static void main(String[] args) throws Exception {
        Print.Println("初始化测试中...");
        Map map = new HashMap();
        map.put(REQUEST,false);
        map.put(REGEX,false);
        map.put(MAIL,false);
        map.put(JSOUP,false);
        RequestTest requestTest = new RequestTest();
        MailTest mailTest = new MailTest();
        JsoupTest jsoupTest = new JsoupTest();
        RegexTest regexTest = new RegexTest();

        Print.Println("开始测试...");
        map.put(REQUEST, requestTest.Test());
        map.put(REGEX, regexTest.Test());
        map.put(MAIL, mailTest.Test());
        map.put(JSOUP, jsoupTest.Test());
        Print.Println("所有测试结束");
        Print.Println("---------------------------------------------------------");
        Set<String> keySet = map.keySet();
        Collection<Boolean> values = map.values();
        Iterator<String> key = keySet.iterator();
        Iterator<Boolean> value = values.iterator();
        while (key.hasNext()){
            if(value.next()){
                Print.Println(key.next()+":成功");
            }else {
                Print.Println(key.next()+":失败");
            }
        }
    }
}
