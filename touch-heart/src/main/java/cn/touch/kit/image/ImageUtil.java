/*
 * cn.touch.kit.image.ImageUtil.java
 * Sep 6, 2012 
 */
package cn.touch.kit.image;

import java.io.File;
import java.io.InputStream;

import org.nutz.lang.Streams;

import cn.touch.kit.MethodResult;

/**
 * Sep 6, 2012
 * 
 * @author <a href="mailto:touchnan@gmail.com">chengqiang.han</a>
 * 
 */
public class ImageUtil {
    /*-
     * 得到图像的宽和高
     * @param imf 图像文件名
     * @param w 宽
     * @param h 高
     */
    public static MethodResult getImageWH(String imf, IntegerImage w, IntegerImage h) {
        InputStream inn = Streams.fileIn(imf);
        return getImageWH(inn, w, h);
    }

    public static MethodResult getImageWH(File imf, IntegerImage w, IntegerImage h) {
        InputStream inn = Streams.fileIn(imf);
        return getImageWH(inn, w, h);
    }

    private static MethodResult getImageWH(InputStream inn, IntegerImage w, IntegerImage h) {
        if (inn == null) {
            return new MethodResult("找不到图片文件");
        }
        
        ImageInfo ii = new ImageInfo();
        ii.setInput(inn);
        ii.setDetermineImageNumber(true);
        ii.setCollectComments(true);
        if (!ii.check()) {
            return new MethodResult("无效的图片格式");
        }
        w.i = ii.getWidth();
        h.i = ii.getHeight();
        return new MethodResult();
    }
}
