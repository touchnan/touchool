package cn.touch.kit.encrypt;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;

/**
 * 对data各位用key异或，然后转16进制.
 * 
 */
public class SimpleEncryptor implements IEncryptor {
    private Log log = Logs.getLog(getClass());
    private String encoding;
    private byte[] key;

    public SimpleEncryptor() {
        super();
        setEncoding(null);
    }

    public SimpleEncryptor(String skey) throws Exception {
        this(skey, null);
    }

    public SimpleEncryptor(String skey, String encoding) throws Exception {
        setEncoding(encoding);
        initKey(skey);
    }

    public void initKey(String skey) throws Exception {
        if (Strings.isBlank(skey)) {
            throw new Exception("Key must not be empty.");
        } else {
            this.key = skey.getBytes(getEncoding());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.zj.pubinfo.security.IEncryptor#encrypt(java.lang.String)
     */
    @Override
    public String encrypt(String src) {
        try {
            byte[] byteMing = simpleCoder(src.getBytes(getEncoding()));
            return EncodeUtils.hexEncode(byteMing);// byte to hex
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.zj.pubinfo.security.IEncryptor#decrypt(java.lang.String)
     */
    @Override
    public String decrypt(String strMi) {
        try {
            byte[] byteMi = simpleCoder(EncodeUtils.hexDecode(strMi));// hex to
                                                                      // byte
            return new String(byteMi, getEncoding());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 
     * @param byteMing
     * @return
     */
    private byte[] simpleCoder(byte[] byteMing) {
        try {
            for (int i = 0; i < byteMing.length; i++) {// xor
                byteMing[i] = (byte) (byteMing[i] ^ key[i % key.length]);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return byteMing;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.zj.pubinfo.security.IEncryptor#encryptAndBase64(java.lang.String)
     */
    @Override
    public String encryptAndBase64(String src) {
        try {
            byte[] byteMing = simpleCoder(src.getBytes(getEncoding()));
            return EncodeUtils.base64Encode(byteMing);// byte to hex
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.zj.pubinfo.security.IEncryptor#decryptAndBase64(java.lang.String)
     */
    @Override
    public String decryptAndBase64(String src) {
        try {
            byte[] byteMi = simpleCoder(EncodeUtils.base64Decode(src));// hex to
                                                                       // byte
            return new String(byteMi, getEncoding());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * @return the encoding
     */
    public String getEncoding() {
        return encoding;
    }

    /**
     * @param encoding
     *            the encoding to set
     */
    public void setEncoding(String encoding) {
        this.encoding = Strings.isBlank(encoding) ? "utf-8" : encoding;
    }

    /**
     * @return the key
     */
    public byte[] getKey() {
        return key;
    }

    public static void main(String[] args) throws Exception {
        String key = "1234567890";
        SimpleEncryptor des = new SimpleEncryptor(key);

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String passwordStr = "lym2$571099999997$" + format.format(new Date());
        System.out.println("加密前的字符串:" + passwordStr);

        String strEnc = des.encrypt(passwordStr);// 加密字符串,返回String的密文
        System.out.println("加密后的字符串:" + strEnc);

        String strDes = des.decrypt(strEnc);// 把String 类型的密文解密
        System.out.println("解密后的字符串:" + strDes);

        passwordStr = "业务号码、工作电话、家庭电话、移动电话";
        System.out.println("加密前的字符串:" + passwordStr);

        strEnc = des.encrypt(passwordStr);// 加密字符串,返回String的密文
        System.out.println("加密后的字符串:" + strEnc);

        strDes = des.decrypt(strEnc);// 把String 类型的密文解密
        System.out.println("解密后的字符串:" + strDes);

        System.out.println("--------------------------------------");
        String key2 = MD5.digest("123456");// "e10adc3949ba59abbe56e057f20f883e";
        SimpleEncryptor des2 = new SimpleEncryptor(key2);

        String hkey = MD5.digest(MD5.digest("123456") + ",10000");

        String passwordStr2 = "15305712181," + hkey;
        System.out.println("加密前的字符串:" + passwordStr2);

        String strEnc2 = des2.encryptAndBase64(passwordStr2);// 加密字符串,返回String的密文
        System.out.println("加密后的字符串:" + strEnc2);

        String strDes2 = des2.decryptAndBase64(strEnc2);// 把String 类型的密文解密
        System.out.println("解密后的字符串:" + strDes2);

        System.out
                .println("验证码:"
                        + MD5.digest(MD5.digest("123456")
                                + ",C,15305712181,0,0,dong,{LOGIN$540403515154020b0501534d040d00040351025200060654020451000c5b0350520005075c060609015f0756},0,10000,"
                                + MD5.digest("123456")));

    }
}
