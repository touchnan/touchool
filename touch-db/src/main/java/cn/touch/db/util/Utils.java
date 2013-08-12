/**
 * 
 */
package cn.touch.db.util;

import org.apache.commons.lang3.StringUtils;
import org.nutz.lang.Lang;

/**
 * Aug 11, 2013
 * @author <a href="mailto:touchnan@gmail.com">chegnqiang.han</a>
 *
 */
public class Utils {
    public static String sqlFieldFilter(String str) {
        if (!StringUtils.containsOnly(str, "0123456789_.#$abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")) {
            throw Lang.makeThrow("字符(%s)不符合规则,存在SQL注入危险", str);
        }
        return str;
    }
}
