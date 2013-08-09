package cn.touch.kit.encrypt;

import java.security.Key;
import java.security.SecureRandom;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;

/**
 * 可逆加解密算法，支持DES,DESede,Blowfish
 * 
 * 
 */
public class Encryptor implements IEncryptor {
    public static final String ENC_DES = "DES";
    public static final String ENC_DESede = "DESede";
    public static final String ENC_Blowfish = "Blowfish";

    private Log log = Logs.getLog(getClass());
    private String encoding;// 字符编码
    private String algorithm;// 算法
    private Key key;

    public Encryptor(String keySeed) {
        this(null, keySeed, null);
    }

    public Encryptor(String algorithm, String keySeed) {
        this(algorithm, keySeed, null);
    }

    public Encryptor(String algorithm, String keySeed, String encoding) {
        super();
        this.setEncoding(encoding);
        this.setAlgorithm(algorithm);
        initKey(getAlgorithm(), keySeed);
    }

    /**
     * 根据参数生成KEY
     * 
     * @param algorithm
     * @param keySeed
     */
    private void initKey(String algorithm, String keySeed) {
        // Security.addProvider(new sun.security.provider.Sun());
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        try {
            KeyGenerator kg = KeyGenerator.getInstance(algorithm);
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
            sr.setSeed(keySeed.getBytes(getEncoding()));
            kg.init(sr);
            this.key = kg.generateKey();
            kg = null;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
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
     * @return the algorithm
     */
    public String getAlgorithm() {
        return algorithm;
    }

    /**
     * @param algorithm
     *            the algorithm to set
     */
    public void setAlgorithm(String algorithm) {
        this.algorithm = Strings.isBlank(algorithm) ? Encryptor.ENC_DESede : algorithm;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.zj.pubinfo.security.IEncryptor#encrypt(java.lang.String)
     */
    @Override
    public String encrypt(String src) {
        try {
            byte[] byteMi = getDesCoder(Cipher.ENCRYPT_MODE, src.getBytes(getEncoding()));
            return new String(EncodeUtils.hexEncode(byteMi));
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
    public String decrypt(String src) {
        try {
            byte[] byteMing = getDesCoder(Cipher.DECRYPT_MODE, EncodeUtils.hexDecode(src));
            return new String(byteMing, getEncoding());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.zj.pubinfo.security.IEncryptor#encryptAndBase64(java.lang.String)
     */
    @Override
    public String encryptAndBase64(String src) {
        try {
            byte[] byteMi = getDesCoder(Cipher.ENCRYPT_MODE, src.getBytes(getEncoding()));
            return new String(EncodeUtils.base64Encode(byteMi));
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
            byte[] byteMing = getDesCoder(Cipher.DECRYPT_MODE, EncodeUtils.base64Decode(src));
            return new String(byteMing, getEncoding());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    private byte[] getDesCoder(int mode, byte[] byteS) {
        try {
            Cipher cipher = Cipher.getInstance(getAlgorithm());
            cipher.init(mode, key);
            return cipher.doFinal(byteS);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static void main(String[] args) {
        String key = "EC415B9AES5E2W2FJ4TB46DAUKFD8H45";
        Encryptor des = new Encryptor(key);
        Encryptor des2 = new Encryptor(key);

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String passwordStr = "lym2$571099999997$" + format.format(new Date());
        System.out.println("加密前的字符串:" + passwordStr);

        String strEnc = des.encrypt(passwordStr);// 加密字符串,返回String的密文
        System.out.println("加密后Hex的字符串:" + strEnc);

        String strDes = des2.decrypt(strEnc);// 把String 类型的密文解密
        System.out.println("Hex解密后的字符串:" + strDes);

        String strEnc1 = des.encryptAndBase64(passwordStr);// 加密字符串,返回String的密文
        System.out.println("加密后Base64的字符串:" + strEnc1);

        String strDes2 = des2.decryptAndBase64(strEnc1);// 把String 类型的密文解密
        System.out.println("Base64解密后的字符串:" + strDes2);

    }
}
