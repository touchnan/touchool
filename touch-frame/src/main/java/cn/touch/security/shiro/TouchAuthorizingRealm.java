package cn.touch.security.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * Jan 3, 2015
 * 
 * @author <a href="mailto:88052350@qq.com">chengqiang.han</a>
 */
public class TouchAuthorizingRealm extends AuthorizingRealm {

	private ITouchSubjectDao touchSubjectDao;
	
    public TouchAuthorizingRealm(ITouchSubjectDao touchSubjectDao) {
        this(new TouchCredentialsMatcher(),touchSubjectDao);
    }
    
    /**
     * 
     */
    public TouchAuthorizingRealm(CredentialsMatcher credentialsMatcher,ITouchSubjectDao touchSubjectDao) {
        super.setCredentialsMatcher(credentialsMatcher);
        this.touchSubjectDao = touchSubjectDao;
    }
    
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Object principal  = principals.getPrimaryPrincipal();
		if (principal !=null) {
			TouchPrincipal p = (TouchPrincipal) principal;
			
			//TODO 可更新登录IP和时间
			
			SimpleAuthorizationInfo info = touchSubjectDao.getAuthorizationInfo(p);
			return info;
		}
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    	TouchUsernamePasswordToken uToken = (TouchUsernamePasswordToken) token;
    	/*-
		// 验证码
		Session session = SecurityUtils.getSubject().getSession();
		String code = (String)session.getAttribute(ValidateCodeServlet.VALIDATE_CODE);
		if (StringUtils.isBlank(SuToken.getCaptcha()) || !uToken.getCaptcha().toUpperCase().equals(code)){
			throw new CaptchaException("验证码错误.");
		} */
    	
    	TouchPrincipal principal = touchSubjectDao.getAuthorizationInfo(uToken);
    	String password = principal.getPasswd();
    	principal.clearSensitivity();
        return new SimpleAuthenticationInfo(principal, password, principal.getLoginName());        
    }

}
