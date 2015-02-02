/**
 * 
 */
package cn.touch.security.crypto.encode;

import cn.touch.security.crypto.SaltGenerator;
import cn.touch.security.crypto.hash.PKCS5S2HashGenerator;
import cn.touch.security.crypto.salt.RandomSaltGenerator;

/**
 * Jan 4, 2015
 * 
 * @author <a href="mailto:88052350@qq.com">chegnqiang.han</a>
 */
public class PKCS5S2Encoder extends TouchEncoder {

    public PKCS5S2Encoder() {
        this(new RandomSaltGenerator());
    }

    /**
     * @param identifier
     * @param hashGenerator
     * @param saltGenerator
     */
    public PKCS5S2Encoder(SaltGenerator saltGenerator) {
        super("PKCS5S2", new PKCS5S2HashGenerator(), saltGenerator);
    }

}
