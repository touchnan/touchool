package cn.touch.security.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import cn.touch.security.crypto.encode.PKCS5S2Encoder;

/**
 * Jan 3, 2015
 * 
 * @author <a href="mailto:88052350@qq.com">chegnqiang.han</a>
 */
public class TouchAuthorizingRealm extends AuthorizingRealm {
    
    public TouchAuthorizingRealm() {
        this(new TouchCredentialsMatcher());
    }
    
    /**
     * 
     */
    public TouchAuthorizingRealm(CredentialsMatcher credentialsMatcher) {
        super.setCredentialsMatcher(credentialsMatcher);
    }
    
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // TODO Auto-generated method stub
        
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        PKCS5S2Encoder encoder = new PKCS5S2Encoder();
        String v = encoder.encode(new String(upToken.getPassword()));
        return new SimpleAuthenticationInfo(upToken.getUsername(),v,upToken.getUsername()+"realeName");        
    }

}
