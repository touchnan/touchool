package cn.touch.kit.encrypt;

public class DESede extends Encryptor {

    /**
     * @param keySeed
     */
    public DESede(String keySeed) {
        super(Encryptor.ENC_DESede, keySeed);
    }

    /**
     * @param keySeed
     * @param encoding
     */
    public DESede(String keySeed, String encoding) {
        super(Encryptor.ENC_DESede, keySeed, encoding);
    }

}
