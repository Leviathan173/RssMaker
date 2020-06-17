import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RequestTest {
    public boolean Test() {
        try{
            Print.Println("开始连接测试");
            String data = null;
            String url = "https://hacg.me/wp/";
            //http请求块
            HttpGet httpget = new HttpGet(url);
            httpget.setConfig(RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build());
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpClientContext context = HttpClientContext.create();
            CloseableHttpResponse res;
            res = httpClient.execute(httpget,context);
            int state = res.getStatusLine().getStatusCode();
            if(state!=200) {
                Print.Println("链接测试失败！");
                Print.Println("连接失败，错误码："+state+
                        "错误信息" +
                        "错误发生时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +
                        "错误类型：连接失败" +
                        "请求网址："+url +
                        "请求错误代码："+state +
                        "详细请求头："+res.getAllHeaders().toString());
                return false;
            }else {
                HttpEntity entity = res.getEntity();
                data = EntityUtils.toString(entity,"UTF-8");
            }

            res.close();
            httpClient.close();
            if(data == null){
                Print.Println("连接测试失败！");
                Print.Println("错误信息" +
                        "错误发生时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +
                        "错误类型：解析网页数据失败" +
                        "请求网址："+url+"");
                return false;
            }else {
                Print.Println("连接测试成功");
                return true;
            }
        }catch (Exception e){
            Print.Println("连接测试失败！");
            Print.Println("错误信息："+e.getMessage());
            return false;
        }
    }
}
