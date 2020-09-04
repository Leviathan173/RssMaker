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

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DataFinder extends Printer {

    public String Requester(String url) throws Exception {
        String data = null;
        //http请求块
        HttpGet httpget = new HttpGet(url);
        httpget.setConfig(RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build());
        httpget.addHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.7.6)");
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpClientContext context = HttpClientContext.create();
        CloseableHttpResponse res = null;
        Println("开始获取"+url+"的网页数据");
        try {
            res = httpClient.execute(httpget,context);
            int state = res.getStatusLine().getStatusCode();
            if(state!=200) {
                SendErrMail("Rss服务器错误", "<h1>错误信息</h1><br><div>" +
                        "错误发生时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +
                        "错误类型：连接失败<br>" +
                        "请求网址：" + url + "<br>" +
                        "请求错误代码：" + state + "<br>" +
                        "详细请求头：" + Arrays.toString(res.getAllHeaders()) +
                        "</div>", res, httpClient);
                System.err.println("连接失败，错误码："+state);
                return null;
            }else {
                Println("获取成功...");
                HttpEntity entity = res.getEntity();
                data = EntityUtils.toString(entity,"UTF-8");
            }
        } catch (Exception e) {
            SendErrMail("Rss服务器错误",
                        "<h1>错误信息</h1><br><div>" +
                            "错误发生时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +
                            "错误信息："+e.getMessage()+"<br>" +
                            "请求网址："+url+"<br>" +
                            "</div>" , res, httpClient);
            e.printStackTrace();
        } finally {
            if(res != null)
                res.close();
            if(httpClient != null)
                httpClient.close();
        }


        if(data == null || data.equals("")){
            SendErrMail("Rss服务器错误",
                    "<h1>错误信息</h1><br><div>" +
                            "错误发生时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +
                            "错误类型：解析网页数据失败" +
                            "请求网址："+url+"<br>", res, httpClient);
            PrintErr("Bad Data!!!");
            return null;
        }else {
            return data;
        }

    }

    public List<Element> GetElementsByClass(Document doc, String className) {
        List<Element> list = doc.getElementsByClass(className);
        if (list != null) {
            return list;
        } else {
            PrintErr("List of Title is null!!!");
            return null;
        }
    }

    public List<Element> GetElementsByTagName(Document doc, String article) {
        List<Element> list = doc.getElementsByTag(article);
        if(list != null){
            return list;
        }else {
            PrintErr("List of Article is null!!!");
            return null;
        }
    }

    public void SendErrMail(String subject, String content, CloseableHttpResponse res, CloseableHttpClient httpClient) throws Exception {
        MailSender mailSender = new MailSender();
        mailSender.SendMail(subject, content);
        if(res != null)
            res.close();
        if(httpClient != null)
            httpClient.close();
    }
    public static void SendErrMail(String subject, String content) throws Exception {
        MailSender mailSender = new MailSender();
        mailSender.SendMail(subject, content);
    }
}
