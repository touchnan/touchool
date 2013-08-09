package cn.touch.kit.encrypt;

public interface IEncryptor extends ISimpleEncryptor {

    /**
     * 加密后转base64
     * 
     * @param src
     * @return
     */
    public String encryptAndBase64(final String src);

    /**
     * base64转换后解密
     * 
     * @param src
     * @return
     */
    public String decryptAndBase64(final String src);
}
