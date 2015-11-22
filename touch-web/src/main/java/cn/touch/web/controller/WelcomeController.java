/**
 * 
 */
package cn.touch.web.controller;

import java.beans.PropertyEditorSupport;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.touch.security.shiro.TouchUsernamePasswordToken;
import cn.touch.web.security.shiro.TouchFormAuthenticationFilter;

/**
 * Jan 6, 2015
 *
 * @author <a href="mailto:88052350@qq.com">chegnqiang.han</a>
 */
@Controller
public class WelcomeController {
	private final static Logger logger = LoggerFactory.getLogger(WelcomeController.class);

	@Value("${authclogin}")
	private String loginView;

	@Value("${authspace}")
	private String defaultRedirectView;

	@Value("${web.view.welcome}")
	private String welcomeView;

	@Value("${authcFilter.login}")
	private boolean shiroFilterLogin;// 过滤器登录

	@Value("${skip.security.code}")
	public boolean skipSecurityCode;

	@Autowired
	private HttpServletRequest request;

	private String login2View() {

		String redirectView = "";
		try {
			SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(request);
			if (savedRequest != null && savedRequest.getMethod().equalsIgnoreCase(AccessControlFilter.GET_METHOD)) {
				redirectView = savedRequest.getRequestUrl().substring(request.getContextPath().length());
				if (redirectView.startsWith("/")) {
					redirectView = redirectView.substring(1);
				}
			}
		} catch (Exception e) {
			logger.error("parse redirectView for login succ.", e);
		}

		return "redirect:/" + (StringUtils.isBlank(redirectView) ? defaultRedirectView : redirectView);
	}

	@RequestMapping(value = "/${authspace}/${authclogin}", method = RequestMethod.GET)
	public String login() {
		Subject subject = SecurityUtils.getSubject();
		// Principal principal = (Principal)subject.getPrincipal();

		Object principal = (Object) subject.getPrincipal();
		if (principal != null && subject.isAuthenticated()) {// 已经登录
			return login2View();
		}
		// 未登录
		return loginView;
	}

	@RequestMapping("/${authspace}")
	public String welcome() {
		Subject subject = SecurityUtils.getSubject();
		// Principal principal = (Principal)subject.getPrincipal();

		Object principal = (Object) subject.getPrincipal();
		if (principal == null || !subject.isAuthenticated()) {/*- 未登录，则跳转到登录页,此处不会到达,被shiro过滤到登录页面 */
			return login2View();
		}
		// 登录成功，计数清零、取得上次访问地址等等
		return "forward:/" + welcomeView;
	}

	@RequestMapping(value = "/${authspace}/${authclogin}", method = RequestMethod.POST)
	public String login(HttpServletRequest request,
			@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String username,
			@RequestParam(FormAuthenticationFilter.DEFAULT_PASSWORD_PARAM) String password,
			@RequestParam(required = false, value = TouchFormAuthenticationFilter.DEFAULT_CAPTCHA_PARAM) String captcha,
			Model model) {
		Subject subject = SecurityUtils.getSubject();
		// Principal principal = (Principal)subject.getPrincipal();

		Object principal = (Object) subject.getPrincipal();
		if (principal != null && subject.isAuthenticated()) {// 已经登录
			return login2View();
		}

		if (!shiroFilterLogin) {// 非过滤器登录
			try {
				TouchUsernamePasswordToken token = new TouchUsernamePasswordToken(username, password, false,
						getHost(request), captcha);
				subject.login(token);
				// WebUtils.getAndClearSavedRequest(request);
				return login2View();
			} catch (AuthenticationException e) {
				// 登录失败
				model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, username);
				model.addAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, e.getLocalizedMessage());
				// 纪录失败次数,限制登录等
				return loginView;
			}
		} else {
			// 纪录失败次数,限制登录等
			model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, username);
			return loginView;
		}
	}

	protected String getHost(ServletRequest request) {
		return request.getRemoteHost();
	}

	/**
	 * 初始化数据绑定 1. 将所有传递进来的String进行HTML编码，防止XSS攻击 2. 将字段中Date类型转换为String类型
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		// String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
		binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
			}

			@Override
			public String getAsText() {
				Object value = getValue();
				return value != null ? value.toString() : "";
			}
		});
	}
}
