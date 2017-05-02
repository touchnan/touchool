/*
 * cn.touch.kit.template.TemplateKit.java
 * Aug 22, 2012 
 */
package cn.touch.kit.template;

import java.io.InputStream;
import java.util.Map;

/**
 * Aug 22, 2012
 * 
 * @author <a href="mailto:touchnan@gmail.com">chengqiang.han</a>
 * 
 */
public interface TemplateKit {
    InputStream genInputStream(String templateName, Map<String, Object> tempLateValueData);

    void generateFile(String templateName, Map<String, Object> tempLateValueData, String outFileName);
}
