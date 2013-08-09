/*
 * test.cn.kit.HttpTest.java
 * Aug 21, 2012 
 */
package test.cn.touch.kit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.junit.BeforeClass;
import org.junit.Test;

import cn.touch.kit.conn.HttpConnector;

/**
 * Aug 21, 2012
 * 
 * @author <a href="mailto:touchnan@gmail.com">chegnqiang.han</a>
 * 
 */
public class HttpTest {
    private static HttpConnector conn = null;

    @BeforeClass
    public static void before() {
        conn = new HttpConnector();
    }

    @Test
    public void getNormal() {
//        System.out.println(conn.get("http://www.baidu.com"));
//        System.out.println(conn.get("http://localhost/static/jquery/1.7/jquery-1.7.min.js?cmd=2","&a=1&b=2"));
        
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("role_ids", "1"));
        nvps.add(new BasicNameValuePair("aa", "2"));
        System.out.println(URLEncodedUtils.format(nvps, Consts.UTF_8));
        System.out.println(conn.get("http://localhost/static/jquery/1.7/jquery-1.7.min.js",nvps));
    }
    
    @Test
    public void postNormal() {
    }
    
    @Test
    public void getProx() {
        System.out.println(new HttpConnector(new HttpHost("127.0.0.1", 8580), null).get("http://svn.assembla.com/svn/cyknij/Cyknij/"));
    }

    @Test
    public void urlProx() throws IOException {

        /**
         * 下面这种方法是全局方式的设置 ，不建议使用。 System.setProperty("proxySet", "true"); System.setProperty("proxyHost",
         * "proxy.lizongbo.com"); System.setProperty("proxyPort", "8080");
         */
        String urlStr = "http://svn.assembla.com/svn/cyknij/Cyknij/";
        HttpURLConnection httpConn = null;
        StringBuilder sb = new StringBuilder();
        URL url = new URL(urlStr);
        String hostName = url.getHost();
        // 根据配置自定义url里的ip地址
        // if (hostName.toLowerCase().endsWith(".mqq.im")) {
        // url = new URL(urlStr.replace(hostName, "127.0.0.1"));
        // }
        System.out.println(url);

        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8580));
        // 只针对当前这个连接使用http代理
        httpConn = (HttpURLConnection) url.openConnection(proxy);
        httpConn.setRequestMethod("GET");
        // 手工设置Host头信息以支持虚拟主机，通过这样的方式，可以避免去手工配置/etc/hosts来绕过dns解析的麻烦，尤其是程序经常在不同服务器上运行的时候，非常实用
        httpConn.setRequestProperty("Host", hostName);
        httpConn.setConnectTimeout(5000);
        httpConn.connect();
        InputStream is = httpConn.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        String line = null;
        while ((line = br.readLine()) != null) {
            sb.append(line).append('\n');
        }
        // 关闭连接
        httpConn.disconnect();
        System.out.println(sb);
    }
}
