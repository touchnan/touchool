/**
 * 
 */
package cn.touch.security.shiro;

import org.apache.shiro.authz.SimpleAuthorizationInfo;

/**
 * Nov 19, 2015
 *
 * @author <a href="mailto:88052350@qq.com">chengqiang.han</a>
 */
public interface ITouchSubjectDao {

	/**
	 * @param principal
	 * @return
	 */
	SimpleAuthorizationInfo getAuthorizationInfo(TouchPrincipal principal);

	/**
	 * @param userToken
	 * @return
	 */
	TouchPrincipal getAuthorizationInfo(TouchUsernamePasswordToken userToken);

}
