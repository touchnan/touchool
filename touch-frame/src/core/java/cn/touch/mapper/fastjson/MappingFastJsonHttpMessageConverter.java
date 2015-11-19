/**
 * 
 */
package cn.touch.mapper.fastjson;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

/**
 * Jan 3, 2015
 * 
 * @author <a href="mailto:88052350@qq.com">chegnqiang.han</a>
 */
public class MappingFastJsonHttpMessageConverter extends FastJsonHttpMessageConverter {

    /*
     * (non-Javadoc)
     * 
     * @see com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter#writeInternal(java.lang.Object,
     * org.springframework.http.HttpOutputMessage)
     */
    @Override
    protected void writeInternal(Object obj, HttpOutputMessage outputMessage) throws IOException,
            HttpMessageNotWritableException {
        Charset charset = getEncoding(outputMessage.getHeaders().getContentType());
        JSON.writeJSONStringTo(obj, new OutputStreamWriter(outputMessage.getBody(), charset), getFeatures());
    }

    private Charset getEncoding(MediaType contentType) {
        if (contentType != null && contentType.getCharSet() != null) {
            return contentType.getCharSet();
        }
        return super.getCharset();
    }    
}
