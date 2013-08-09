/*
 * cn.touch.kit.json.JsonKit.java
 * Aug 5, 2012 
 */
package cn.touch.kit.json;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;

/**
 * Aug 5, 2012
 * 
 * @author <a href="mailto:touchnan@gmail.com">chegnqiang.han</a>
 * 
 */
public interface JsonKit {
    String stringify(Object obj);

    void stringify(Object obj, OutputStream out);

    void stringify(Object obj, Writer out);

    void stringify(Object obj, File file);

    <T> T parse(Class<T> clazz, String json);

    <T> T parse(Class<T> clazz, File file);

    <T> T parse(Class<T> clazz, URL url);

    <T> T parse(Class<T> clazz, InputStream in);

    <T> T parse(Class<T> clazz, Reader reader);

    <T> T parse(Class<T> clazz, byte[] data);

    <T> T parse(Class<T> clazz, byte[] data, int offset, int len);

}
