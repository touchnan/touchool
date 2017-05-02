/**
 * 
 */
package cn.touch.security.shiro;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

import cn.touch.security.crypto.Encoder;
import cn.touch.security.crypto.encode.PKCS5S2Encoder;

/**
 * Jan 4, 2015
 * 
 * @author <a href="mailto:88052350@qq.com">chengqiang.han</a>
 */
public class TouchCredentialsMatcher implements CredentialsMatcher {
    private List<? extends Encoder> encoders = Arrays.asList(new PKCS5S2Encoder());

    /**
     * @param encoders
     *            the encoders to set
     */
    public void setEncoders(List<? extends Encoder> encoders) {
        this.encoders = encoders;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.apache.shiro.authc.credential.CredentialsMatcher#doCredentialsMatch(org.apache.shiro.authc.AuthenticationToken
     * , org.apache.shiro.authc.AuthenticationInfo)
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken authToken, AuthenticationInfo info) {
        UsernamePasswordToken token = (UsernamePasswordToken) authToken;
        String passwd = new String(token.getPassword());
        for (Encoder encoder : encoders) {
            if (encoder.canDecode((String) info.getCredentials())) {
                return encoder.isValid(passwd, (String) info.getCredentials());
            }
        }
        return false;
    }

}
