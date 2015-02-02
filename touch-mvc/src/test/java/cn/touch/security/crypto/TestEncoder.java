/**
 * 
 */
package cn.touch.security.crypto;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import cn.touch.security.crypto.encode.PKCS5S2Encoder;

/**
 * Jan 3, 2015
 * 
 * @author <a href="mailto:88052350@qq.com">chegnqiang.han</a>
 */
public class TestEncoder {

    @Test
    public void encode() {
        Encoder encoder = new PKCS5S2Encoder();
        String passwd = "20002014";

        List<String> encodes = new ArrayList<String>();
        encodes.add("{PKCS5S2}yOFq5PKztneInkIJ+4D1EATen9xXUWP07XIo3BCGKr2ZicTkRP1nVQDIlrH+8o7v");
        encodes.add("{PKCS5S2}0OXqW0CmlF9YBP0nx7YXi+UhT2gfbB2JxNJuXMW5MUfE7JK/RfJhvhMaZJl3DPIF");
        encodes.add("{PKCS5S2}B8LtfU/JVnYkC/A6Hng5COkSz0KwjwpAQKMiz8+1KMgL0z5e/gIehS/Jtwd1Jf5r");

        for (int i = 0; i < 3; i++) {
            encodes.add(encoder.encode(passwd));
        }

        for (String encode : encodes) {
            Assert.assertTrue("不是可解码", encoder.canDecode(encode));
            Assert.assertTrue("是否正确匹配", encoder.isValid(passwd, encode));
        }
    }

}
