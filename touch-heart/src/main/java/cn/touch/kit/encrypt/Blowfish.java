package cn.touch.kit.encrypt;

public class Blowfish extends Encryptor {

    /**
     * @param keySeed
     */
    public Blowfish(String keySeed) {
        super(Encryptor.ENC_Blowfish, keySeed);
    }

    /**
     * @param keySeed
     * @param encoding
     */
    public Blowfish(String keySeed, String encoding) {
        super(Encryptor.ENC_Blowfish, keySeed, encoding);
    }

}
