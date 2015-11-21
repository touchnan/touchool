/**
 * 
 */
package cn.touch.security.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * Nov 19, 2015
 *
 * @author <a href="mailto:88052350@qq.com">chegnqiang.han</a>
 */
public class TouchUsernamePasswordToken extends UsernamePasswordToken {
	private static final long serialVersionUID = 6053843857125042096L;

	private String captcha;

	public String getCaptcha() {
		return captcha;
	}

	/**
	 * @param username
	 * @param password
	 * @param rememberMe
	 * @param host
	 */
	public TouchUsernamePasswordToken(String username, String password, boolean rememberMe, String host, String captcha) {
		super(username, password, rememberMe, host);
		this.captcha = captcha;
	}

	
}
