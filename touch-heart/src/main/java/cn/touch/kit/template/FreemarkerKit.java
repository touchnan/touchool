/*
 * cn.touch.kit.template.FreemarkerKit.java
 * May 27, 2012 
 */
package cn.touch.kit.template;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import cn.touch.file.Processor;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * May 27, 2012
 * 
 * @author <a href="mailto:touchnan@gmail.com">chengqiang.han</a>
 * 
 */
/**
 * Aug 22, 2012
 * @author <a href="mailto:touchnan@gmail.com">chengqiang.han</a>
 * 
 */
/**
 * Aug 22, 2012
 * 
 * @author <a href="mailto:touchnan@gmail.com">chengqiang.han</a>
 * 
 */
public class FreemarkerKit implements TemplateKit {
    private static final Log log = Logs.getLog(FreemarkerKit.class);
    private Configuration cfg;
    private Charset templateCharset = Processor.DEFAULT_CHARSET;
    private Charset outFileCharset = Processor.GBK_CHARSET;

    public FreemarkerKit() throws IOException, URISyntaxException {
        super();
        init(defaultFilePath());
    }

    public FreemarkerKit(Charset... charsets) throws IOException, URISyntaxException {
        super();
        init(defaultFilePath(), charsets);
    }

    /**
     * @param templatePath
     * @param charsets
     *            [Charset templateCharset, Charset outFileCharset]
     * @throws IOException
     * @throws URISyntaxException
     */
    public FreemarkerKit(File templatePath, Charset... charsets) throws IOException, URISyntaxException {
        super();
        init(templatePath, charsets);
    }

    private FreemarkerKit init(File templatePath, Charset... charsets) throws IOException, URISyntaxException {
        if (charsets != null) {
            if (charsets.length > 0) {
                this.templateCharset = charsets[0];
            }
            if (charsets.length > 1) {
                this.outFileCharset = charsets[1];
            }
        }
        cfg = new Configuration();
        cfg.setTemplateLoader(new FileTemplateLoader(templatePath));
        cfg.setObjectWrapper(new DefaultObjectWrapper());
        return this;
    }

    private File defaultFilePath() throws URISyntaxException {
        return new File(getClass().getClassLoader().getResource("ftl").toURI());
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.touch.kit.template.TemplateKit#genInputStream(java.lang.String,
     * java.util.Map)
     */
    @Override
    public InputStream genInputStream(String template, Map<String, Object> data) {
        try {
            Template tmplate = getTemplate(template);
            // FastByteArrayOutputStream bos = new
            // FastByteArrayOutputStream();//struts2
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            Writer out = new OutputStreamWriter(bos, outFileCharset);
            tmplate.process(data, out);
            IOUtils.closeQuietly(out);
            return new ByteArrayInputStream(bos.toByteArray());
        } catch (IOException e) {
            log.error(e);
            throw new RuntimeException(e);
        } catch (TemplateException e) {
            log.error(e);
            throw new RuntimeException(e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.touch.kit.template.TemplateKit#generateFile(java.lang.String,
     * java.util.Map, java.lang.String)
     */
    @Override
    public void generateFile(String template, Map<String, Object> data, String outFileName) {
        File targetFile = new File(outFileName);
        File parentFile = targetFile.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        try {
            Template tmplate = getTemplate(template);
            FileOutputStream fos = new FileOutputStream(outFileName);
            Writer out = new OutputStreamWriter(fos, outFileCharset);
            tmplate.process(data, out);

            out.flush();
            fos.flush();

            IOUtils.closeQuietly(out);
            IOUtils.closeQuietly(fos);

        } catch (IOException e) {
            log.error(e);
            throw new RuntimeException(e);
        } catch (TemplateException e) {
            log.error(e);
            throw new RuntimeException(e);
        }
    }

    private Template getTemplate(String template) throws IOException {
        return cfg.getTemplate(template, templateCharset.name());
    }
}
