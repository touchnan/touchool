/*
 * test.cn.touch.kit.ImageTest.java
 * Sep 6, 2012 
 */
package test.cn.touch.kit;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;
import org.nutz.lang.Files;

import cn.touch.kit.MethodResult;
import cn.touch.kit.image.ImageMagicProcessor;

/**
 * Sep 6, 2012
 * 
 * @author <a href="mailto:touchnan@gmail.com">chegnqiang.han</a>
 * 
 */
public class ImageTest {

    @Test
    public void gen() throws IOException {
        File file = Files.findFile("logo.gif");
        String src = file.getCanonicalPath();
        String out = "target/logo_small.gif";
        
        
        MethodResult r =
                new ImageMagicProcessor("C:/Program Files/ImageMagick-6.7.9-Q16/").creatMiniatureCommand(src,
                        out, 60, 80, 84);
        Assert.assertTrue(r.getMsg(), r.isSuccess());
    }

}
