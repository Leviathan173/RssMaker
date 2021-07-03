package per.leviathan173.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
            String title, publishTime, author, summary, imgLink, articleLink, magnet = "";
            title = getArticleTitle(e);
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
            publishTime = getArticlePublishTime(e);
            author = getArticleAuthor(e);
            summary = getArticleSummary(e);
            imgLink = getArticleImgLink(e);
            articleLink = getArticleArticleLink(e);
            log("publishTime=" + publishTime + " \nauthor=" + author + "\n" +
                    "summary=" + summary + "\nimgLink=" + imgLink + "\n" +
                    "articleLink=" + articleLink);
            if (!articleLink.equals("")) {
                magnet = Strings.getMagnet(finder, Jsoup.parse(finder.requester(articleLink)));
                log("magnet=" + magnet);
            } else {
                printLn("has no magnet in " + articleLink);
            }
            articleList.add(new Article(title, publishTime, author, summary, imgLink, articleLink, magnet));
            log(articleList.toString());
        }
    }

    public static String getArticleTitle(Element e) {
        Elements entryTitle = e.getElementsByClass("entry-title");
        if (entryTitle.isEmpty()) {
            return "【没有标题】";
        }
        return entryTitle.text();
    }

    public static String getArticlePublishTime(Element e) {
        Elements publishTime = e.getElementsByClass("entry-date");
        if (publishTime.isEmpty()) {
            return "2021-07-04T02:00:00+08:00";
        }
        return publishTime.get(0).attributes().get("datetime");
    }

    public static String getArticleAuthor(Element e) {
        Elements author = e.getElementsByClass("author vcard");
        if (author.isEmpty()) {
            return "【佚名】";
        }
        return author.get(0).text();
    }

    public static String getArticleSummary(Element e) {
        Elements summary = e.getElementsByClass("entry-content");
        if (summary.isEmpty()) {
            return "【没有描述】";
        }
        return summary.text();
    }

    public static String getArticleImgLink(Element e) {
        Elements imgLink = e.getElementsByTag("img");
        if (imgLink.isEmpty()) {
            return "";
        }
        return imgLink.get(0).attr("src");
    }

    public static String getArticleArticleLink(Element e) {
        Elements articleLink = e.getElementsByClass("more-link");
        if (articleLink.isEmpty()) {
            return "";
        }
        return articleLink.attr("href");
    }
}
