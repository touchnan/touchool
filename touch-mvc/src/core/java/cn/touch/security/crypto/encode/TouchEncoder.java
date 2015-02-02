/**
 * 
 */
package cn.touch.security.crypto.encode;

import java.util.Arrays;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;

import cn.touch.security.crypto.Encoder;
import cn.touch.security.crypto.HashGenerator;
import cn.touch.security.crypto.SaltGenerator;

/**
 * Jan 3, 2015
 * 
 * @author <a href="mailto:88052350@qq.com">chegnqiang.han</a>
 */
public class TouchEncoder implements Encoder {
    private final String prefix;
    private final HashGenerator hashGenerator;
    private final SaltGenerator saltGenerator;

    /**
     * 
     */
    public TouchEncoder(String identifier, HashGenerator hashGenerator, SaltGenerator saltGenerator) {
        this.prefix = ("{" + identifier + "}");
        this.hashGenerator = hashGenerator;
        this.saltGenerator = saltGenerator;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.touch.security.crypto.Encoder#encode(java.lang.String)
     */
    @Override
    public String encode(String raw) throws IllegalArgumentException {
        Validate.notEmpty(raw, "Password must not be empty");
        byte[] salt = this.saltGenerator.generateSalt(getSaltLength());
        byte[] hash = this.hashGenerator.generateHash(StringUtils.getBytesUtf8(raw), salt);
        String encodedPassword = toEncodedForm(salt, hash);
        return prependPrefix(encodedPassword);
    }

    /**
     * @return
     */
    private int getSaltLength() {
        if (this.hashGenerator.getRequiredSaltLength() > 0)
            return this.hashGenerator.getRequiredSaltLength();
        return SaltGenerator.DEFAULT_LENGTH;
    }

    /**
     * @param encodedPassword
     * @return
     */
    private String prependPrefix(String encodedPassword) {
        return this.prefix + encodedPassword;
    }

    /**
     * @param salt
     * @param hash
     * @return
     */
    private String toEncodedForm(byte[] salt, byte[] hash) {
        byte[] saltAndHash = ArrayUtils.addAll(salt, hash);
        byte[] base64 = Base64.encodeBase64(saltAndHash);
        return StringUtils.newStringUtf8(base64);
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.touch.security.crypto.Encoder#isValid(java.lang.String, java.lang.String)
     */
    @Override
    public boolean isValid(String raw, String prefixedEncoded) throws IllegalArgumentException {
        Validate.notNull(raw);
        Validate.notNull(prefixedEncoded);
        if (!canDecode(prefixedEncoded)) {
            return false;
        }
        String encoded = removePrefix(prefixedEncoded);
        byte[] storedBytes = fromEncodedForm(encoded);
        byte[] salt = ArrayUtils.subarray(storedBytes, 0, getSaltLength());
        byte[] storedHash = ArrayUtils.subarray(storedBytes, getSaltLength(), storedBytes.length);
        byte[] hashAttempt = this.hashGenerator.generateHash(StringUtils.getBytesUtf8(raw), salt);
        return Arrays.equals(storedHash, hashAttempt);
    }

    /**
     * @param encoded
     * @return
     */
    private byte[] fromEncodedForm(String encoded) {
        return Base64.decodeBase64(StringUtils.getBytesUtf8(encoded));
    }

    /**
     * @param prefixedEncoded
     * @return
     */
    private String removePrefix(String prefixedEncoded) {
        return prefixedEncoded.substring(this.prefix.length());
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.touch.security.crypto.Encoder#canDecode(java.lang.String)
     */
    @Override
    public boolean canDecode(String encoded) {
        return (encoded != null) && (encoded.startsWith(this.prefix));
    }

}
