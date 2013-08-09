package cn.touch.kit.encrypt;

/**
 * 消息摘要（散列）接口
 * 
 */
public interface Idigest {
    static final String ALG_SHA1 = "SHA1";
    static final String ALG_MD5 = "MD5";

    String encrypt(String src);// 散列并转化成16进制后返回

    String encrypt(String src, String encoding);// 散列并转化成16进制后返回

    String encryptAndBase64(String src);// 散列并进行Base64编码后返回

    String encryptAndBase64(String src, String encoding);// 散列并进行Base64编码后返回

}
