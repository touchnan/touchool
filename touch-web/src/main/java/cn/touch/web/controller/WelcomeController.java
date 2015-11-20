/**
 * 
 */
package cn.touch.web.controller;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.touch.security.shiro.TouchUsernamePasswordToken;

/**
 * Jan 6, 2015
 *
 * @author <a href="mailto:88052350@qq.com">chegnqiang.han</a>
 */
@Controller
public class WelcomeController {
	
	@Value("${authclogin}")
	private String loginView;
	
	@Value("${authclogininfo}")
	private String authclogininfo;
	
	@Value("${authspace}")
	private String redirectView;
	
	@Value("${web.view.welcome}")
	private String welcomeView;
	
	
	private String login2View() {
		return "redirect:/"+redirectView;
	}
	
    @RequestMapping(value="/${authspace}/${authclogin}",method = RequestMethod.GET)
    public String login() {
        Subject subject = SecurityUtils.getSubject();
        //Principal principal = (Principal)subject.getPrincipal();
        
        Object principal = (Object)subject.getPrincipal();
        if (principal!=null && subject.isAuthenticated()){//已经登录
        	return login2View();
        }
        //未登录
        return loginView;
    }
    
    @RequestMapping("/${authspace}")
    public String welcome() {
        Subject subject = SecurityUtils.getSubject();
        //Principal principal = (Principal)subject.getPrincipal();
        
        Object principal = (Object)subject.getPrincipal();
        if (principal==null || !subject.isAuthenticated()){/*- 未登录，则跳转到登录页,此处不会到达,被shiro过滤到登录页面 */
            return login2View();
        }
        
        return "forward:/"+welcomeView;
    }    
    
    @RequestMapping(value="/${authspace}/${authclogin}",method = RequestMethod.POST)
    public String login(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String username,@RequestParam(FormAuthenticationFilter.DEFAULT_PASSWORD_PARAM) String password,Model model) {
        Subject subject = SecurityUtils.getSubject();
        //Principal principal = (Principal)subject.getPrincipal();
        
        Object principal = (Object)subject.getPrincipal();
        if (principal!=null && subject.isAuthenticated()){//已经登录
        	return login2View();
        }
        
        try {
        	TouchUsernamePasswordToken token = new TouchUsernamePasswordToken(username,password,false);
        	subject.login(token);
        	//WebUtils.getAndClearSavedRequest(request);
        	return login2View();
        } catch (AuthenticationException e) {
            //登录失败
            model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, username);
            model.addAttribute(StringUtils.isBlank(authclogininfo) ? "info" : authclogininfo, e.getLocalizedMessage());
            //纪录失败次数,限制登录等
            return loginView;
        }
        
    }    
    
	/**
	 * 初始化数据绑定
	 * 1. 将所有传递进来的String进行HTML编码，防止XSS攻击
	 * 2. 将字段中Date类型转换为String类型
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
