package src.main.java;

import org.jdom2.CDATA;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


import java.io.*;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        DataFinder finder = new DataFinder();
        Document doc = null;
        try {
            //String data = finder.Requester("https://hacg.me/wp/");
            File file = new File("data");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String data = "";
            String line = bufferedReader.readLine();
            while (line != null) {
                data += line;
                data += "\n";
                line = bufferedReader.readLine();
            }
            doc = Jsoup.parse(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 数据处理
        List<Element> titleRaw = finder.GetElementByClass(doc, "entry-title");
        titleRaw.remove(0);
        List<Element> content = finder.GetElementByClass(doc, "entry-content");
        content.remove(0);
        List<Element> linkRaw = finder.GetElementByClass(doc, "more-link");
        linkRaw.remove(0);
        List<Element> img = new ArrayList<Element>();
        List<String> linkArr = new ArrayList<String>();
        for (Element e :
                content) {
            Element element = e.getElementsByTag("img").get(0);
            if (element == null) {
                finder.PrintErr("element is null");
            } else {
                finder.Println(element.attr("src"));
                img.add(element);
            }
        }
        for (Element e :
                linkRaw) {
            String href = e.attr("href");
            linkArr.add(href);
        }
        // Xml写入
        try {
            //1.生成一个根节点
            org.jdom2.Element rss = new org.jdom2.Element("rss");
            //2.为节点添加属性
            rss.setAttribute("version", "2.0");
            //3.生成一个document的对象
            org.jdom2.Document document = new org.jdom2.Document(rss);
            org.jdom2.Element channel = new org.jdom2.Element("channel");
            rss.addContent(channel);
            org.jdom2.Element title = new org.jdom2.Element("title");
            title.setText("My own rss feed");
            channel.addContent(title);
            org.jdom2.Element link = new org.jdom2.Element("link");
            link.setText("https://hacg.me");
            channel.addContent(link);
            org.jdom2.Element description = new org.jdom2.Element("description");
            description.setText("琉璃神社Rss");
            channel.addContent(description);
            org.jdom2.Element lastBuildDate = new org.jdom2.Element("lastBuildDate");
            lastBuildDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            channel.addContent(lastBuildDate);
            org.jdom2.Element language = new org.jdom2.Element("language");
            language.setText("zh-cn");
            channel.addContent(language);
            for (int i = 0; i < titleRaw.size(); i++) {
                org.jdom2.Element item = new org.jdom2.Element("item");
                document.addContent(item);
                org.jdom2.Element titleL = new org.jdom2.Element("title");
                titleL.setText(titleRaw.get(i).text());
                item.addContent(titleL);
                org.jdom2.Element linkL = new org.jdom2.Element("link");
                linkL.setText(linkArr.get(i));
                item.addContent(linkL);
                org.jdom2.Element descriptionL = new org.jdom2.Element("description");
                descriptionL.setText(content.get(i).text());
                item.addContent(descriptionL);
                org.jdom2.Element author = new org.jdom2.Element("author");
                author.setText("琉璃神社");
                item.addContent(author);
                
            }
            //设置生成xml的格式
            Format format = Format.getCompactFormat();
            format.setIndent("");
            //生成不一样的编码
            format.setEncoding("UTF-8");
            //4.创建XMLOutputter的对象
            XMLOutputter outputter = new XMLOutputter(format);
            //5.利用outputter将document对象转换成xml文档
            outputter.output(document, new FileOutputStream(new File("rssnews.xml")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
