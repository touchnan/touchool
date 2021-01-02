package cn.touch.security;

import cn.touch.security.crypto.Encoder;
import cn.touch.security.crypto.encode.PKCS5S2Encoder;

import java.util.*;

/**
 * Jan 4, 2015
 * 
 * @author <a href="mailto:88052350@qq.com">chengqiang.han</a>
 */
public interface TouchCredentialsMatcher {


//    /**
//     * @param encoders
//     *            the encoders to set
//     */
//    public default void setEncoders(List<? extends Encoder> encoders) {
////        this.encoders = encoders;
//    }

    default List<? extends Encoder> encoders (){
//        private List<? extends Encoder> encoders = Arrays.asList(new PKCS5S2Encoder());
        return Collections.singletonList(new PKCS5S2Encoder());
    }

    default boolean doCredentialsMatch(String passwd, String credential) {
        for (Encoder encoder : encoders()) {
            if (encoder.canDecode(credential)) {
                return encoder.isValid(passwd, credential);
            }
        }
        return false;
    }

}
