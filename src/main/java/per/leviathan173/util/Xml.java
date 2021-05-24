package per.leviathan173.util;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import per.leviathan173.entity.Article;
import per.leviathan173.entity.Options;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Xml {
    public static boolean writeXML(List<Article> articleList, org.jdom2.Element channel, org.jdom2.Document document) {
        try {
            for (int i = articleList.size() - 1; i >= 0; i--) {
                Article a = articleList.get(i);
                if (checkTitle(channel, a.getTitle())) {
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
                while (channel.getChildren("item").size() > Options.MAX_NUMBER_OF_ARTICLES) {
                    Printer.log("detached " + channel.getChildren("item").get(0).getChildText("title"));
                    channel.getChildren("item").get(0).detach();
                }
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

    public static void setupXML() throws IOException {
        File file = new File("rss.xml");
        if (file.exists()) {
            return;
        }
        org.jdom2.Element rss = new org.jdom2.Element("rss");
        rss.setAttribute("version", "2.0");
        Document document = new Document(rss);
        Element channel = new Element("channel");
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

        Format format = Format.getCompactFormat();
        format.setIndent("");
        format.setEncoding("UTF-8");
        XMLOutputter outputter = new XMLOutputter(format);
        outputter.output(document, new FileOutputStream("rss.xml"));
    }

    public static boolean checkTitle(org.jdom2.Element channel, String text) {
        List<org.jdom2.Element> list = channel.getChildren("item");
        for (org.jdom2.Element e :
                list) {
            String title = e.getChild("title").getText();
            if (title.equals(text)) {
                return true;
            }
        }
        return false;
    }
}
