package cn.touch.kit.encrypt;

public class DES extends Encryptor {

    /**
     * @param keySeed
     */
    public DES(String keySeed) {
        super(Encryptor.ENC_DES, keySeed);
    }

    /**
     * @param keySeed
     * @param encoding
     */
    public DES(String keySeed, String encoding) {
        super(Encryptor.ENC_DES, keySeed, encoding);
    }

}
