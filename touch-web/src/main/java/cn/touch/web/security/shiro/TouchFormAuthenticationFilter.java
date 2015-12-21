/**
 * 
 */
package cn.touch.web.security.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Component;

import cn.touch.security.shiro.TouchUsernamePasswordToken;

/**
 * Nov 21, 2015
 *
 * @author <a href="mailto:88052350@qq.com">chegnqiang.han</a>
 */
@Component
public class TouchFormAuthenticationFilter extends FormAuthenticationFilter {

	public static final String DEFAULT_CAPTCHA_PARAM = "securityCode";

	private String captchaParam = DEFAULT_CAPTCHA_PARAM;

	public String getCaptchaParam() {
		return captchaParam;
	}

	protected String getCaptcha(ServletRequest request) {
		return WebUtils.getCleanParam(request, getCaptchaParam());
	}	

	/* (non-Javadoc)
	 * @see org.apache.shiro.web.filter.authc.FormAuthenticationFilter#createToken(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		String username = getUsername(request);
		String password = getPassword(request);
		boolean rememberMe = isRememberMe(request);
		String host = getHost(request);
		String captcha = getCaptcha(request);
		return new TouchUsernamePasswordToken(username, password, rememberMe, host, captcha);
	}

	/**
	 * @param captchaParam the captchaParam to set
	 */
	public void setCaptchaParam(String captchaParam) {
		this.captchaParam = captchaParam;
	}
	
}
