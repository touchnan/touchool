package work.v2m.touch.security.crypto.salt;

import java.util.Random;

import work.v2m.touch.security.crypto.SaltGenerator;

/**
 * Jan 3, 2015
 * 
 * @author <a href="mailto:88052350@qq.com">chengqiang.han</a>
 */
public class RandomSaltGenerator implements SaltGenerator {
    private static final Random random = new Random();

    /*
     * (non-Javadoc)
     * 
     * @see cn.touch.security.crypto.SaltGenerator#generateSalt(int)
     */
    @Override
    public byte[] generateSalt(int length) {
        byte[] result = new byte[length];
        random.nextBytes(result);
        return result;
    }

}
