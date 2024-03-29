package per.leviathan173.rssmaker;

import org.jdom2.input.SAXBuilder;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import per.leviathan173.entity.Article;
import per.leviathan173.entity.Options;
import per.leviathan173.util.*;

import java.util.ArrayList;
import java.util.List;


public class Main extends Printer {

    public static void main(String[] args) throws Throwable {
        CmdLineParser parser = new CmdLineParser(new Options());
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            parser.printUsage(System.out);
            System.exit(0);
        }

        new Main().doMain();
    }

    public void doMain() throws Throwable {
        org.jdom2.Document document = null;
        org.jdom2.Element root;
        org.jdom2.Element channel = null;
        try {
            printLn("正在初始化XML文件...");
            Xml.setupXML();
            SAXBuilder sb = new SAXBuilder();
            document = sb.build("rss.xml");
            root = document.getRootElement();
            channel = root.getChild("channel");
            log("doc=" + document + " root=" + root + " channel=" + channel);
            printLn("初始化XML文件成功...");
        } catch (Exception e) {
            log(e.getMessage());
            printErr("初始化XML文件失败！");
        }
        while (true) {
            List<Article> articleList = new ArrayList<>();
            DataFinder finder = new DataFinder();
            GetTrueURL getter = new GetTrueURL();
            Document doc = finder.getDocumentFromUrl(getter.getURL());
            if (doc == null) {
                printLn("doc is null");
                MailSender.sendErrMail("空指针", "");
                waitForSecond(60 * 60);
                continue;
            }
            log(doc.text());
            // 数据处理
            List<Element> articles = finder.getElementsByTagName(doc, "article");
            log(articles.toString());
            Strings.processData(articles,articleList,channel,finder);
            // Xml写入
            if (!Xml.writeXML(articleList, channel, document)) {
                MailSender.sendErrMail("写入XML文件失败",
                        "文章：" + articleList + "<br>");
                printLn("写入XML文件失败");
                waitForSecond(60 * 60);
                continue;
            }
            waitForSecond(60 * 60 * 4);
        }
    }
}
