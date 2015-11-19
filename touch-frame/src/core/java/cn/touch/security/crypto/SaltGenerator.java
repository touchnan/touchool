/**
 * 
 */
package cn.touch.security.crypto;

/**
 * Jan 3, 2015
 * 
 * @author <a href="mailto:88052350@qq.com">chegnqiang.han</a>
 */
public interface SaltGenerator {
    byte[] generateSalt(int length);
    
    final int DEFAULT_LENGTH =16;
}
