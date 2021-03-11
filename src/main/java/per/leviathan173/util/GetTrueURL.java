package per.leviathan173.util;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.List;

import static per.leviathan173.util.Printer.*;

public class GetTrueURL {
    public String getURL() throws Throwable {
        DataFinder finder = new DataFinder();
        Document doc = finder.getDocumentFromUrl("https://acg.gy");
        if (doc == null) {
            MailSender.sendErrMail("无法从导航网页获取正确网址", "");
            throw new Throwable("无法从导航网页获取正确网址");
        }
        log(doc.text());
        List<Element> link = finder.getElementsByTagName(doc, "a");
        return link.get(0).attr("href").concat("/wp/");
    }
}
