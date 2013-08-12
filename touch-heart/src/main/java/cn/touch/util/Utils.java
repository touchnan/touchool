/**
 * 
 */
package cn.touch.util;

import org.nutz.lang.Strings;

/**
 * Aug 11, 2013
 * @author <a href="mailto:touchnan@gmail.com">chegnqiang.han</a>
 *
 */
public class Utils {
    /**
     * 计算字节c在字符cs中出现的次数
     * 
     * @param cs
     * @param c
     * @return count
     */
    public static int countAlias(CharSequence cs, char c) {
        if (Strings.isBlank(cs)) {
            return 0;
        }
        int count = 0;
        int length = cs.length();
        for (int i = 0; i < length; i++) {
            if (c == cs.charAt(i)) {
                count++;
            }
        }
        return count;
    }
}
