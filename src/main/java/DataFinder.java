import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DataFinder extends Print {

    public String Requester(String url) throws Exception {
        String data = null;
        //http请求块
        HttpGet httpget = new HttpGet(url);
        httpget.setConfig(RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build());
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpClientContext context = HttpClientContext.create();
        CloseableHttpResponse res;
        Println("开始获取网页数据...");
        res = httpClient.execute(httpget,context);
        int state = res.getStatusLine().getStatusCode();
        if(state!=200) {
            System.err.println("连接失败，错误码："+state);
            MailSender mailSender = new MailSender();
            String subject = "Rss服务器错误";
            String content = "<h1>错误信息</h1><br><div>" +
                    "错误发生时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +
                    "错误类型：连接失败<br>" +
                    "请求网址："+url+"<br>" +
                    "请求错误代码："+state+"<br>" +
                    "详细请求头："+res.getAllHeaders().toString() +
                    "</div>";
            mailSender.SendMail(subject, content);
            res.close();
            httpClient.close();
            return null;
        }else {
            Println("获取成功...");
            HttpEntity entity = res.getEntity();
            data = EntityUtils.toString(entity,"UTF-8");
        }

        res.close();
        httpClient.close();
        if(data == null){
            PrintErr("Bad Data!!!");
            MailSender mailSender = new MailSender();
            String subject = "Rss服务器错误";
            String content = "<h1>错误信息</h1><br><div>" +
                    "错误发生时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +
                    "错误类型：解析网页数据失败" +
                    "请求网址："+url+"<br>";
            mailSender.SendMail(subject, content);
            return null;
        }else {
            return data;
        }

    }

    public List<Element> GetElementByClass(Document doc, String className) {
        List<Element> list = doc.getElementsByClass(className);
        if(list != null){
            return list;
        }else {
            PrintErr("List of Title is null!!!");
            return null;
        }
    }

    public List<Element> GetElementsByTagName(Document doc, String article) {
        List<Element> list = doc.getElementsByTag(article);
        for (Element e:
             list) {
            Println("文章："+e.text());
        }
        if(list != null){
            return list;
        }else {
            PrintErr("List of Article is null!!!");
            return null;
        }
    }
}
