/**
 * 
 */
package cn.touch.control;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * Mar 10, 2015
 *
 * @author <a href="mailto:88052350@qq.com">chegnqiang.han</a>
 */
public class BaseControl {
   protected ResponseEntity<byte[]> down(String fileShowName, MediaType mediaType, File sourceFile) {
        try {
            return down(fileShowName,mediaType,FileUtils.readFileToByteArray(sourceFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        
    }

    protected ResponseEntity<byte[]> down(String fileShowName, MediaType mediaType, byte[] data) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        try {
            String fileName = java.net.URLEncoder.encode(fileShowName, "UTF-8");
            // headers.setContentDispositionFormData("attachment", fileName);

            /*-
             * firefox下不会自动转码,需要扩展指定编码
             */
            String contentDisposition =
                    String.format("attachment;filename=\"%s\";filename*=UTF-8''%s;", fileName, fileName);
            headers.set("Content-Disposition", contentDisposition);

            return new ResponseEntity<byte[]>(data, headers, HttpStatus.OK);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    
    protected ResponseEntity<byte[]> down(String fileShowName, MediaType mediaType, String sourceFile) {
        ClassPathResource rs = new ClassPathResource(sourceFile);
        try {
            return down(fileShowName, mediaType, rs.getFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
