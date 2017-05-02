/*
 * cn.touch.kit.json.JackJsonKit.java
 * Aug 5, 2012 
 */
package cn.touch.kit.json;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;

import org.nutz.log.Logs;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Aug 5, 2012
 * 
 * @author <a href="mailto:touchnan@gmail.com">chengqiang.han</a>
 * 
 */
public class JackJsonKit extends JsonFactory implements JsonKit {
    private ObjectMapper mapper;
    private JsonFactory factory;

    public JackJsonKit() {
        mapper = new ObjectMapper();
        factory = new JsonFactory(mapper).enable(JsonParser.Feature.ALLOW_COMMENTS);
    }

    private void stringify(JsonGenerator g, Object obj) {
        try {
            g.writeObject(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                g.close();
            } catch (IOException e) {
                Logs.getLog(JackJsonKit.class).error(e);
            }
        }
    }

    private <T> T parse(Class<T> clazz, JsonParser jp) {
        try {
            T obj = jp.readValueAs(clazz);
            jp.close();
            return obj;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                jp.close();
            } catch (IOException e) {
                Logs.getLog(JackJsonKit.class).error(e);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.touch.kit.json.IJsonKit#stringify(java.lang.Object)
     */
    @Override
    public String stringify(Object obj) {
        try {
            return mapper.writeValueAsString(obj);

            // ByteArrayOutputStream bos = new ByteArrayOutputStream();
            // ByteArrayBuilder bos = new ByteArrayBuilder();
            // JsonGenerator g = factory.createJsonGenerator(bos);
            // stringify(g, obj);
            // return new String(bos.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.touch.kit.json.IJsonKit#stringify(java.lang.Object,
     * java.io.OutputStream)
     */
    @Override
    public void stringify(Object obj, OutputStream out) {
        try {
            stringify(factory.createJsonGenerator(out), obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.touch.kit.json.IJsonKit#stringify(java.lang.Object,
     * java.io.Writer)
     */
    @Override
    public void stringify(Object obj, Writer out) {
        try {
            stringify(factory.createJsonGenerator(out), obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.touch.kit.json.IJsonKit#stringify(java.lang.Object, java.io.File)
     */
    @Override
    public void stringify(Object obj, File file) {
        try {
            stringify(factory.createJsonGenerator(file, JsonEncoding.UTF8), obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.touch.kit.json.IJsonKit#parse(java.lang.Class, java.lang.String)
     */
    @Override
    public <T> T parse(Class<T> clazz, String json) {
        try {
            return mapper.readValue(json, clazz);

            // return parse(clazz, factory.createJsonParser(json));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.touch.kit.json.IJsonKit#parse(java.lang.Class, java.io.File)
     */
    @Override
    public <T> T parse(Class<T> clazz, File file) {
        try {
            return parse(clazz, factory.createJsonParser(file));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.touch.kit.json.IJsonKit#parse(java.lang.Class, java.net.URL)
     */
    @Override
    public <T> T parse(Class<T> clazz, URL url) {
        try {
            return parse(clazz, factory.createJsonParser(url));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.touch.kit.json.IJsonKit#parse(java.lang.Class,
     * java.io.InputStream)
     */
    @Override
    public <T> T parse(Class<T> clazz, InputStream in) {
        try {
            return parse(clazz, factory.createJsonParser(in));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.touch.kit.json.IJsonKit#parse(java.lang.Class, java.io.Reader)
     */
    @Override
    public <T> T parse(Class<T> clazz, Reader reader) {
        try {
            return parse(clazz, factory.createJsonParser(reader));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.touch.kit.json.IJsonKit#parse(java.lang.Class, byte[])
     */
    @Override
    public <T> T parse(Class<T> clazz, byte[] data) {
        try {
            return parse(clazz, factory.createJsonParser(data));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.touch.kit.json.IJsonKit#parse(java.lang.Class, byte[], int, int)
     */
    @Override
    public <T> T parse(Class<T> clazz, byte[] data, int offset, int len) {
        try {
            return parse(clazz, factory.createJsonParser(data, offset, len));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
