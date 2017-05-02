/*
 * cn.touch.kit.Config.java
 * May 18, 2012 
 */
package cn.touch.kit;

import java.util.Enumeration;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;

/**
 * May 18, 2012
 * 
 * @author <a href="mailto:touchnan@gmail.com">chengqiang.han</a>
 * 
 */
public class Config {
    private Log log = Logs.getLog(Config.class);
    private static final String TRUE = String.valueOf(Boolean.TRUE);
    private static final String FALSE = String.valueOf(Boolean.FALSE);

    private String bundleName;
    private ResourceBundle resources;

    public Config(String bundleName) {
        super();
        this.bundleName = bundleName;
    }

    private Config getBundle() {
        try {
            resources = ResourceBundle.getBundle(bundleName, Locale.getDefault());
        } catch (MissingResourceException mre) {
            log.error(mre.getMessage(), mre);
        }
        return this;
    }

    private boolean checkResources() {
        if (resources == null) {
            getBundle();
        }
        return resources != null;
    }

    private boolean changeToBoolean(String str) throws Exception {
        String tmp = str.toLowerCase();
        if (tmp.equals(TRUE)) {
            return true;
        } else if (tmp.equals(FALSE)) {
            return false;
        } else {
            throw new Exception("class not matching.");
        }
    }

    public boolean getBoolean(String key) {
        String str = getString(key);
        try {
            return changeToBoolean(str);
        } catch (Throwable e) {
            return false;
        }
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        String str = getString(key);
        try {
            return changeToBoolean(str);
        } catch (Throwable e) {
            return defaultValue;
        }
    }

    private int changeToInt(String str) throws Exception {
        return Integer.parseInt(str);
    }

    public int getInt(String key) {
        String str = getString(key);
        try {
            return changeToInt(str);
        } catch (Throwable e) {
            return 0;
        }
    }

    public int getInt(String key, int defaultValue) {
        String str = getString(key);
        try {
            return changeToInt(str);
        } catch (Throwable e) {
            return defaultValue;
        }
    }

    public String getString(String key, String defaultValue) {
        String tmp = null;
        if (checkResources()) {
            try {
                tmp = resources.getString(key).trim();
            } catch (Throwable e) {
                log.error(e.getMessage(), e);
            }
        }
        return Strings.isBlank(tmp) ? defaultValue : tmp;
    }

    public String getString(String key) {
        if (checkResources()) {
            try {
                return resources.getString(key).trim();
            } catch (Throwable e) {
                log.error(e.getMessage(), e);
            }
        }
        return null;
    }

    public String[] getStringArray(String key) {
        if (checkResources()) {
            return resources.getStringArray(key);
        }
        return null;
    }

    public Enumeration<String> getKeys() {
        return resources.getKeys();
    }

}
