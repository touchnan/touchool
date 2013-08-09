/*
 * test.cn.touch.kit.JsoupTest.java
 * Sep 26, 2012 
 */
package test.cn.touch.kit;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;

import cn.touch.kit.conn.HttpConnector;

/**
 * Sep 26, 2012
 * @author <a href="mailto:touchnan@gmail.com">chegnqiang.han</a>
 * 
 */
public class JsoupTest {
    HttpConnector conn;
    
    
    @Before
    public void before() {
//        conn =new HttpConnector(new HttpHost("127.0.0.1", 8580), null);
        conn = new HttpConnector();
    }
    
    @Test
    public void jsoupCyknij() throws IOException {
        String out = "Cyknij/";
        parseCyknij("http://svn.assembla.com/svn/cyknij/Cyknij/", out);
    }
    
    
    private void parseCyknij(String url, String out) throws IOException {
        String cont = conn.get(url);
        Document doc = Jsoup.parse(cont);
        Elements es = doc.select("li a");
        for (Element e : es) {
            //link.attr("href",link.attr("abs:href"));
            String href = e.attr("href");
            if (!href.startsWith(".")) {
                if (href.indexOf("/")!=-1) {
                    String nextUrl = url.concat(href);
                    parseCyknij(nextUrl, out+href);
                } else {
                    File file = new File(out);
                    if (!file.exists()) {
                        org.nutz.lang.Files.makeDir(file);
                    }
                    FileUtils.writeByteArrayToFile(new File(out+href), conn.getBytes(url+href));
//                  com.google.common.io.Files.write(conn.getBytes(url+href), new File(out+href));
                }
            }
        }        
    }
}
