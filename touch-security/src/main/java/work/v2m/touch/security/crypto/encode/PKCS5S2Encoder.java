package work.v2m.touch.security.crypto.encode;

import work.v2m.touch.security.crypto.hash.PKCS5S2HashGenerator;
import work.v2m.touch.security.crypto.salt.RandomSaltGenerator;
import work.v2m.touch.security.crypto.SaltGenerator;

/**
 * Jan 4, 2015
 * 
 * @author <a href="mailto:88052350@qq.com">chengqiang.han</a>
 */
public class PKCS5S2Encoder extends TouchEncoder {

    public PKCS5S2Encoder() {
        this(new RandomSaltGenerator());
    }

    /**
     * @param saltGenerator salt generator
     */
    public PKCS5S2Encoder(SaltGenerator saltGenerator) {
        super("PKCS5S2", new PKCS5S2HashGenerator(), saltGenerator);
    }

}
