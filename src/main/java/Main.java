import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main extends Printer {

    public static void main(String[] args) throws Exception {
        org.jdom2.Document document = null;
        org.jdom2.Element root;
        org.jdom2.Element channel = null;
        try {
            Println("正在初始化XML文件...");
            SetupXML();
            SAXBuilder sb = new SAXBuilder();
            document = sb.build("rss.xml");
            root = document.getRootElement();
            channel = root.getChild("channel");
            Println("初始化XML文件成功...");
        } catch (Exception e) {
            PrintErr("初始化XML文件失败！");
        }
        while (true) {
            List<Article> articleList = new ArrayList<Article>();
            DataFinder finder = new DataFinder();
            Document doc;
            try {
                String data = finder.Requester("https://hacg.me/wp/");
                if (data == null) {
                    WaitForSecond(60 * 60);
                    continue;
                }
                doc = Jsoup.parse(data);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
            // 数据处理
            if (doc == null) {
                WaitForSecond(60 * 60);
                continue;
            }
            List<Element> articles = finder.GetElementsByTagName(doc, "article");
            for (Element e :
                    articles) {
                String title, publishTime, author, content, imgLink, articleLink, magnet = "";
                title = e.getElementsByClass("entry-title").text();
                if (title.equals("")) {
                    Println("广告文章，跳过...");
                    continue;
                }
                assert channel != null;
                if (CheckTitle(channel, title)) {
                    Println("文章：" + title + " 已存在，跳过...");
                    continue;
                }
                publishTime = e.getElementsByClass("entry-date").get(0).attributes().get("datetime");
                author = e.getElementsByClass("author vcard").get(0).text();
                content = e.getElementsByClass("entry-content").text();
                imgLink = e.getElementsByTag("img").get(0).attr("src");
                articleLink = e.getElementsByClass("more-link").attr("href");
                if (!articleLink.equals("")) {
                    magnet = GetMagnet(finder, Jsoup.parse(finder.Requester(articleLink)));
                } else {
                    Println("has no magnet in " + articleLink);
                }
                articleList.add(new Article(title, publishTime, author, content, imgLink, articleLink, magnet));
            }
            // Xml写入
            if (!WriteXML(articleList, channel, document)) {
                DataFinder.SendErrMail("Rss服务器写入XML文件失败",
                        "<h1>错误信息</h1><br><div>" +
                                "错误发生时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +
                                "错误类型：写入文件失败" +
                                "文章：" + articleList.toString() + "<br>");
            }

            WaitForSecond(60 * 60 * 4);

        }

    }

    private static boolean WriteXML(List<Article> articleList, org.jdom2.Element channel, org.jdom2.Document document) {
        try {
            for (Article a :
                    articleList) {
                if (CheckTitle(channel, a.getTitle())) {
                    continue;
                }
                org.jdom2.Element item = new org.jdom2.Element("item");
                channel.addContent(item);
                org.jdom2.Element titleL = new org.jdom2.Element("title");
                titleL.setText(a.getTitle());
                item.addContent(titleL);
                org.jdom2.Element linkL = new org.jdom2.Element("link");
                linkL.setText(a.getLink());
                item.addContent(linkL);
                org.jdom2.Element descriptionL = new org.jdom2.Element("description");
                descriptionL.setText(a.getSummary() + "\n<br>" + a.getMagnet() + "<p><img src=\"" + a.getImgLink() + "\" /></p>");
                item.addContent(descriptionL);
                org.jdom2.Element author = new org.jdom2.Element("author");
                author.setText(a.getAuthor());
                item.addContent(author);
                org.jdom2.Element pubDate = new org.jdom2.Element("pubDate");
                pubDate.setText(a.getPublishTime().split("T")[0] + " " + a.getPublishTime().split("T")[1].split("\\+")[0]);
                item.addContent(pubDate);
            }
            org.jdom2.Element lastBuildDate = channel.getChild("lastBuildDate");
            assert lastBuildDate != null;
            lastBuildDate.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            //设置生成xml的格式
            Format format = Format.getCompactFormat();
            format.setIndent("");
            //生成不一样的编码
            format.setEncoding("UTF-8");
            //4.创建XMLOutputter的对象
            XMLOutputter outputter = new XMLOutputter(format);
            //5.利用outputter将document对象转换成xml文档
            outputter.output(document, new FileOutputStream("rss.xml"));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private static String GetMagnet(DataFinder finder, Document doc) {
        List<Element> list = finder.GetElementsByClass(doc, "entry-content");
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

    private static boolean CheckTitle(org.jdom2.Element channel, String text) {
        List<org.jdom2.Element> list = channel.getChildren("item");
        // System.out.println("count:" + list.size());
        for (org.jdom2.Element e :
                list) {
            String title = e.getChild("title").getText();
            // System.out.println("title:" + title);
            if (title.equals(text)) {
                // System.out.println(title + " is equals " + text);
                return true;
            }
        }
        return false;
    }

    private static void SetupXML() throws IOException {
        File file = new File("rss.xml");
        if (file.exists()) {
            return;
        }
        //1.生成一个根节点
        org.jdom2.Element rss = new org.jdom2.Element("rss");
        //2.为节点添加属性
        rss.setAttribute("version", "2.0");
        //3.生成一个document的对象
        org.jdom2.Document document = new org.jdom2.Document(rss);
        org.jdom2.Element channel = new org.jdom2.Element("channel");
        rss.addContent(channel);
        org.jdom2.Element title = new org.jdom2.Element("title");
        title.setText("琉璃神社");
        channel.addContent(title);
        org.jdom2.Element link = new org.jdom2.Element("link");
        link.setText("https://hacg.me");
        channel.addContent(link);
        org.jdom2.Element description = new org.jdom2.Element("description");
        description.setText("琉璃神社Rss");
        channel.addContent(description);
        org.jdom2.Element lastBuildDate = new org.jdom2.Element("lastBuildDate");
        lastBuildDate.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        channel.addContent(lastBuildDate);
        org.jdom2.Element language = new org.jdom2.Element("language");
        language.setText("zh-cn");
        channel.addContent(language);

        //设置生成xml的格式
        Format format = Format.getCompactFormat();
        format.setIndent("");
        //生成不一样的编码
        format.setEncoding("UTF-8");
        //4.创建XMLOutputter的对象
        XMLOutputter outputter = new XMLOutputter(format);
        //5.利用outputter将document对象转换成xml文档
        outputter.output(document, new FileOutputStream(new File("rss.xml")));
    }
}
