package src.main.java;

import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        while (true){
            DataFinder finder = new DataFinder();
            Document doc = null;
            try {
                String data = finder.Requester("https://hacg.me/wp/");
//                File file = new File("data");
//                if (!file.exists()) {
//                    file.createNewFile();
//                }
//                FileReader reader = new FileReader(file);
//                BufferedReader bufferedReader = new BufferedReader(reader);
//                StringBuilder data = new StringBuilder();
//                String line = bufferedReader.readLine();
//                while (line != null) {
//                    data.append(line);
//                    data.append("\n");
//                    line = bufferedReader.readLine();
//                }
//                finder.Println(data.toString());
                doc = Jsoup.parse(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 数据处理
            assert doc != null;
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
                SetupXML();
                SAXBuilder sb = new SAXBuilder();
                org.jdom2.Document document = sb.build("rss.xml");
                org.jdom2.Element root = document.getRootElement();
                org.jdom2.Element channel = root.getChild("channel");
                for (int i = 0; i < titleRaw.size(); i++) {
                    if (CheckTitle(channel, titleRaw.get(i).text())) {
                        continue;
                    }
                    String magnet = GetMagnet(finder, Jsoup.parse(finder.Requester(linkArr.get(i))));
                    org.jdom2.Element item = new org.jdom2.Element("item");
                    channel.addContent(item);
                    org.jdom2.Element titleL = new org.jdom2.Element("title");
                    titleL.setText(titleRaw.get(i).text());
                    item.addContent(titleL);
                    org.jdom2.Element linkL = new org.jdom2.Element("link");
                    linkL.setText(linkArr.get(i));
                    item.addContent(linkL);
                    org.jdom2.Element descriptionL = new org.jdom2.Element("description");
                    descriptionL.setText(content.get(i).text() + "\n<br>" + magnet + "<p><img src=\""+img.get(i).attr("src")+"\"/></p>");
                    item.addContent(descriptionL);
                    org.jdom2.Element author = new org.jdom2.Element("author");
                    author.setText("琉璃神社");
                    item.addContent(author);
                    org.jdom2.Element pubDate = new org.jdom2.Element("pubDate");
                    pubDate.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                    item.addContent(pubDate);
                    finder.Println("loop:" + i);
                }
                //设置生成xml的格式
                Format format = Format.getCompactFormat();
                format.setIndent("");
                //生成不一样的编码
                format.setEncoding("UTF-8");
                //4.创建XMLOutputter的对象
                XMLOutputter outputter = new XMLOutputter(format);
                //5.利用outputter将document对象转换成xml文档
                outputter.output(document, new FileOutputStream("rss.xml"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JDOMException e) {
                e.printStackTrace();
            }
            for(int i = 0;i<60*60*8;i++){
                finder.Println(i+"/"+(60*60*8));
                Thread.sleep(1000);
            }

        }

    }

    private static String GetMagnet(DataFinder finder, Document doc) {
        List<Element> list = finder.GetElementByClass(doc, "entry-content");
        String magent = "";
        for (Element e :
                list) {
            String content = e.text();
            Pattern pattern = Pattern.compile("[a-zA-Z0-9]{40,}");
            Matcher matcher = pattern.matcher(content);
            if (matcher.find()) {
                finder.Println(matcher.group(0));
                magent += matcher.group(0);
            }
        }
        return magent;
    }

    private static boolean CheckTitle(org.jdom2.Element channel, String text) {
        List<org.jdom2.Element> list = channel.getChildren("item");
        System.out.println("count:" + list.size());
        for (org.jdom2.Element e :
                list) {
            String title = e.getChild("title").getText();
            System.out.println("title:" + title);
            if (title.equals(text)) {
                System.out.println(title + " is equals " + text);
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
