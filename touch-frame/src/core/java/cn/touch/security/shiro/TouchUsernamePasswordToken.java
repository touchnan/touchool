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

	/**
	 * 
	 */
	public TouchUsernamePasswordToken() {
		super();
	}

	/**
	 * @param username
	 * @param password
	 * @param rememberMe
	 * @param host
	 */
	public TouchUsernamePasswordToken(String username, char[] password, boolean rememberMe, String host) {
		super(username, password, rememberMe, host);
	}

	/**
	 * @param username
	 * @param password
	 * @param rememberMe
	 */
	public TouchUsernamePasswordToken(String username, char[] password, boolean rememberMe) {
		super(username, password, rememberMe);
	}

	/**
	 * @param username
	 * @param password
	 * @param host
	 */
	public TouchUsernamePasswordToken(String username, char[] password, String host) {
		super(username, password, host);
	}

	/**
	 * @param username
	 * @param password
	 */
	public TouchUsernamePasswordToken(String username, char[] password) {
		super(username, password);
	}

	/**
	 * @param username
	 * @param password
	 * @param rememberMe
	 * @param host
	 */
	public TouchUsernamePasswordToken(String username, String password, boolean rememberMe, String host) {
		super(username, password, rememberMe, host);
	}

	/**
	 * @param username
	 * @param password
	 * @param rememberMe
	 */
	public TouchUsernamePasswordToken(String username, String password, boolean rememberMe) {
		super(username, password, rememberMe);
	}

	/**
	 * @param username
	 * @param password
	 * @param host
	 */
	public TouchUsernamePasswordToken(String username, String password, String host) {
		super(username, password, host);
	}

	/**
	 * @param username
	 * @param password
	 */
	public TouchUsernamePasswordToken(String username, String password) {
		super(username, password);
	}

	
}
