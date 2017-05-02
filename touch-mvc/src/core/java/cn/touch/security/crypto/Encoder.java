/**
 * 
 */
package cn.touch.security.crypto;

/**
 * Jan 3, 2015
 * 
 * @author <a href="mailto:88052350@qq.com">chengqiang.han</a>
 */
public interface Encoder {
    String encode(String raw) throws IllegalArgumentException;

    boolean isValid(String raw, String prefixedEncoded) throws IllegalArgumentException;

    boolean canDecode(String encoded);
}
