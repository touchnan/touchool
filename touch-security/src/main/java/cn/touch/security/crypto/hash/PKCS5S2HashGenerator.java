package cn.touch.security.crypto.hash;

import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;

import cn.touch.security.crypto.SaltGenerator;

/**
 * Jan 3, 2015
 * 
 * @author <a href="mailto:88052350@qq.com">chengqiang.han</a>
 */
public class PKCS5S2HashGenerator extends TouchHashGenerator {

    public PKCS5S2HashGenerator() {
        this(DEFAULT_ITERATION_COUNT, DEFAULT_OUTPUT_SIZE_BITS, SaltGenerator.DEFAULT_LENGTH);
    }

    /**
     * @param iteration_count iteration_count
     * @param output_size_bits output_size_bits
     * @param salt_length salt_length
     */
    public PKCS5S2HashGenerator(int iteration_count, int output_size_bits, int salt_length) {
        super(iteration_count, output_size_bits, salt_length, new PKCS5S2ParametersGenerator());
    }

}
