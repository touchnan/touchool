/**
 * 
 */
package cn.touch.security.crypto.hash;

import org.bouncycastle.crypto.PBEParametersGenerator;
import org.bouncycastle.crypto.params.KeyParameter;

import cn.touch.security.crypto.HashGenerator;

/**
 * Jan 3, 2015
 * 
 * @author <a href="mailto:88052350@qq.com">chegnqiang.han</a>
 */
public class TouchHashGenerator implements HashGenerator {
    private final int ITERATION_COUNT;
    private final int OUTPUT_SIZE_BITS;
    private final int SALT_LENGTH;

    private PBEParametersGenerator generator;

    public TouchHashGenerator(int iteration_count, int output_size_bits, int salt_length,
            PBEParametersGenerator generator) {
        this.ITERATION_COUNT = iteration_count;
        this.OUTPUT_SIZE_BITS = output_size_bits;
        this.SALT_LENGTH = salt_length;
        this.generator = generator;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.touch.security.crypto.HashGenerator#generateHash(byte[], byte[])
     */
    @Override
    public byte[] generateHash(byte[] raw, byte[] salt) {
        generator.init(raw, salt, ITERATION_COUNT);
        KeyParameter output = (KeyParameter) generator.generateDerivedMacParameters(OUTPUT_SIZE_BITS);
        return output.getKey();
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.touch.security.crypto.HashGenerator#getRequiredSaltLength()
     */
    @Override
    public int getRequiredSaltLength() {
        return SALT_LENGTH;
    }

}
