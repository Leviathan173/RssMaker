package per.leviathan173.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import per.leviathan173.entity.Article;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Strings extends Printer{
    public static String getMagnet(DataFinder finder, Document doc) {
        List<Element> list = finder.getElementsByClass(doc, "entry-content");
        StringBuilder magent = new StringBuilder();
        for (Element e :
                list) {
            String content = e.text();
            Pattern pattern = Pattern.compile("[a-zA-Z0-9]{40,}");
            Matcher matcher = pattern.matcher(content);
            while (matcher.find()) {
                for (int i = 0; i <= matcher.groupCount(); i++) {
                    magent.append(matcher.group(i));
                    magent.append("<br>");
                }
            }
            pattern = Pattern.compile("(?<!\\S)[a-zA-Z0-9]{32}(?!\\S)");
            matcher = pattern.matcher(content);
            while (matcher.find()) {
                for (int i = 0; i <= matcher.groupCount(); i++) {
                    magent.append("这是一个Base32哈希:");
                    magent.append(matcher.group(i));
                    magent.append("(").append(Base32ToHex.DecodeBase32(matcher.group(i))).append(")");
                    magent.append("<br>");
                }
            }

        }
        return magent.toString();
    }

    public static void processData(List<Element> articles,
                                   List<Article> articleList,
                                   org.jdom2.Element channel,
                                   DataFinder finder) throws Exception {
        for (Element e :
                articles) {
            String title, publishTime, author, content, imgLink, articleLink, magnet = "";
            title = e.getElementsByClass("entry-title").text();
            log("title=" + title);
            if (title.equals("")) {
                printLn("广告文章，跳过...");
                continue;
            }
            assert channel != null;
            if (Xml.checkTitle(channel, title)) {
                printLn("文章：" + title + " 已存在，跳过...");
                continue;
            }
            publishTime = e.getElementsByClass("entry-date").get(0).attributes().get("datetime");
            author = e.getElementsByClass("author vcard").get(0).text();
            content = e.getElementsByClass("entry-content").text();
            imgLink = e.getElementsByTag("img").get(0).attr("src");
            articleLink = e.getElementsByClass("more-link").attr("href");
            log("publishTime=" + publishTime + " \nauthor=" + author + "\n" +
                    "content=" + content + "\nimgLink=" + imgLink + "\n" +
                    "articleLink=" + articleLink);
            if (!articleLink.equals("")) {
                magnet = Strings.getMagnet(finder, Jsoup.parse(finder.requester(articleLink)));
                log("magnet=" + magnet);
            } else {
                printLn("has no magnet in " + articleLink);
            }
            articleList.add(new Article(title, publishTime, author, content, imgLink, articleLink, magnet));
            log(articleList.toString());
        }
    }
}
