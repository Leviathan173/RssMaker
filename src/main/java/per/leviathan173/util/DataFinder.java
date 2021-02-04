package per.leviathan173.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static per.leviathan173.util.MailSender.sendErrMail;

public class DataFinder extends Printer {

    public String requester(String url) throws Exception {
        String data;
        HttpGet httpget = new HttpGet(url);
        httpget.setConfig(RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build());
        httpget.addHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.7.6)");
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpClientContext context = HttpClientContext.create();
        CloseableHttpResponse res = null;
        log("requesting "+url);
        printLn("开始获取"+url+"的网页数据");
        try {
            res = httpClient.execute(httpget,context);
            int state = res.getStatusLine().getStatusCode();
            if(state!=200) {
                sendErrMail("HTTP请求失败",
                        "请求网址：" + url + "<br>" +
                        "请求错误代码：" + state + "<br>" +
                        "详细请求头：" + Arrays.toString(res.getAllHeaders()) +
                        "</div>");
                printLn("连接失败，错误码："+state);
                return null;
            }else {
                printLn("获取成功...");
                HttpEntity entity = res.getEntity();
                data = EntityUtils.toString(entity,"UTF-8");
            }
        } catch (Exception e) {
            sendErrMail("HTTP请求错误",
                        e.getMessage()+"<br>" +
                            "请求网址："+url+"<br>" +
                            "</div>");
            log(e.getMessage());
            printLn("HTTP请求错误");
            return null;
        } finally {
            if(res != null)
                res.close();
            if(httpClient != null)
                httpClient.close();
        }


        if(data == null || data.equals("")){
            sendErrMail("解析网页数据失败",
                            "请求网址："+url+"<br>");
            printLn("Bad Data!!!");
            return null;
        }else {
            return data;
        }

    }

    public List<Element> getElementsByClass(Document doc, String className) {
        List<Element> list = doc.getElementsByClass(className);
        if (list != null) {
            return list;
        } else {
            printErr("List of Title is null!!!");
            return null;
        }
    }

    public List<Element> getElementsByTagName(Document doc, String article) {
        List<Element> list = doc.getElementsByTag(article);
        if(list != null){
            return list;
        }else {
            printErr("List of per.leviathan173.entity.Article is null!!!");
            return null;
        }
    }

    public Document getDocumentFromUrl(String url) throws Exception {
        try {
            String data = this.requester(url);
            log("data=" + data);
            if (data == null) {
                printLn("null Object");
                MailSender.sendErrMail("空指针","");
                return null;
            }
            return Jsoup.parse(data);
        } catch (IOException e) {
            log("IOException:" + e.getMessage());
            printLn("IOException happened, retry");
            MailSender.sendErrMail("IOException", e.getMessage() + "<br>");
            return null;
        }
    }
}
