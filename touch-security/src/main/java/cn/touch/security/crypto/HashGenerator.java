package cn.touch.security.crypto;

/**
 * Jan 3, 2015
 * @author <a href="mailto:88052350@qq.com">chengqiang.han</a>
 */
public interface HashGenerator {
    byte[] generateHash(byte[] raw, byte[] salt);
    int getRequiredSaltLength();
    
    int DEFAULT_ITERATION_COUNT=10000;
    int DEFAULT_OUTPUT_SIZE_BITS=256;
}
