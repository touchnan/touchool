/*
 * test.cn.kit.HttpTest.java
 * Aug 21, 2012 
 */
package test.cn.touch.kit;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
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
        System.out.println(conn.get("http://www.baidu.com"));
//        System.out.println(conn.get("http://localhost/static/jquery/1.7/jquery-1.7.min.js?cmd=2","a=1&b=2"));
        
//        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//        nvps.add(new BasicNameValuePair("role_ids", "1"));
//        nvps.add(new BasicNameValuePair("aa", "2"));
//        System.out.println(URLEncodedUtils.format(nvps, Consts.UTF_8));
        //System.out.println(conn.get("http://localhost/static/jquery/1.7/jquery-1.7.min.js",nvps));
    }
    
    @Test
    public void postNormal() {
    }
}
