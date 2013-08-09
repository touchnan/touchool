package cn.touch.kit.encrypt;

/**
 * 消息签名的自定义加解密算法接口
 * 
 */
public interface ISimpleEncryptor {

    /**
     * 加密
     * 
     * @param src
     * @return
     */
    public String encrypt(final String src);

    /**
     * 解密
     * 
     * @param src
     * @return
     */
    public String decrypt(final String src);

}
