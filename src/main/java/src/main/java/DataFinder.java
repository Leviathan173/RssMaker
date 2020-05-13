package src.main.java;

import com.sun.org.apache.xpath.internal.XPathAPI;
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
import org.w3c.dom.NodeList;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.List;

public class DataFinder extends Print {

    public String Requester(String url) throws IOException {
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
        }else {
            Println("获取成功...");
            HttpEntity entity = res.getEntity();
            data = EntityUtils.toString(entity,"UTF-8");
        }

        res.close();
        httpClient.close();
        if(data == null){
            PrintErr("Bad Data!!!");
            return null;
        }else {
            return data;
        }

    }

    public List<Element> GetElementByClass(Document doc, String className) {
        List<Element> list = doc.getElementsByClass(className);
        for (Element e:
             list) {
            Println(className+":"+e.text());
        }
        if(list != null){
            return list;
        }else {
            PrintErr("List of Title is null!!!");
            return null;
        }
    }

}
