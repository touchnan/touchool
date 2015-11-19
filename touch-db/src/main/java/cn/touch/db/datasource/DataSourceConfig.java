/*
 * cn.touch.db.datasource.DataSourceConfig.java
 * Feb 11, 2012 
 */
package cn.touch.db.datasource;

import java.io.File;
import java.io.InputStream;

import org.nutz.lang.Lang;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Feb 11, 2012
 * 
 * @author <a href="mailto:touchnan@gmail.com">chegnqiang.han</a>
 * 
 */
public class DataSourceConfig {
    public final static String KEY = "key";
    public final static String DRIVER_CLASS_NAME = "driverClassName";
    public final static String USERNAME = "username";
    public final static String PASSWORD = "password";
    public final static String URL = "url";
    
    private String dsClassName;
    
    private Object key;
    private String driverClassName;
    private String userName;
    private String passwd;
    private String url;

    public DataSourceConfig load(Object key, String driverClassName, String userName, String passwd, String url) {
        this.key = key;
        this.driverClassName = driverClassName;
        this.userName = userName;
        this.passwd = passwd;
        this.url = url;
        return this;
    }

    public DataSourceConfig load(String xml) {
        try {
            Document doc = Lang.xmls().parse(xml);
            assembleProperties(doc);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public DataSourceConfig load(File file) {
        try {
            Document doc = Lang.xmls().parse(file);
            assembleProperties(doc);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public DataSourceConfig load(InputStream is) {
        try {
            Document doc = Lang.xmls().parse(is);
            assembleProperties(doc);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    private void assembleProperties(Document doc) {
        Element root = doc.getDocumentElement();
        this.load(root.getAttribute(KEY), root.getAttribute(DRIVER_CLASS_NAME), root.getAttribute(USERNAME),
                root.getAttribute(PASSWORD), root.getAttribute(URL));
    }

    /**
     * @return the dsClassName
     */
    public String getDsClassName() {
        return dsClassName;
    }

    /**
     * @param dsClassName the dsClassName to set
     */
    public void setDsClassName(String dsClassName) {
        this.dsClassName = dsClassName;
    }

    /**
     * @return the key
     */
    public Object getKey() {
        return key;
    }

    /**
     * @param key
     *            the key to set
     */
    public void setKey(Object key) {
        this.key = key;
    }

    /**
     * @return the driverClassName
     */
    public String getDriverClassName() {
        return driverClassName;
    }

    /**
     * @param driverClassName
     *            the driverClassName to set
     */
    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     *            the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the passwd
     */
    public String getPasswd() {
        return passwd;
    }

    /**
     * @param passwd
     *            the passwd to set
     */
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     *            the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

}
