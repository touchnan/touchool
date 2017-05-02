/**
 * 
 */
package cn.touch.control;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Jan 6, 2015
 *
 * @author <a href="mailto:88052350@qq.com">chengqiang.han</a>
 */
@Controller
public class WelcomeControl {
    
    @RequestMapping(value="${authspace}${authclogin}",method = RequestMethod.GET)
    public String login() {
        Subject subject = SecurityUtils.getSubject();
        //Principal principal = (Principal)subject.getPrincipal();
        
        Object principal = (Object)subject.getPrincipal();
        if (principal!=null){//已经登录
            return "redirect:/index";
        }
        //未登录
        return "login";
    }
    
    @RequestMapping(value="${authspace}${authclogin}",method = RequestMethod.POST)
    public String login(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String username,Model model) {
        Subject subject = SecurityUtils.getSubject();
        //Principal principal = (Principal)subject.getPrincipal();
        
        Object principal = (Object)subject.getPrincipal();
        if (principal!=null){//已经登录
            return "redirect:/index";
        }
        //登录失败
        model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, username);
        model.addAttribute("info", username);
        //纪录失败次数,限制登录等
        return "login";
    }    
    
    @RequestMapping("${authspace}")
    public String welcome() {
        Subject subject = SecurityUtils.getSubject();
        //Principal principal = (Principal)subject.getPrincipal();
        
        Object principal = (Object)subject.getPrincipal();
        if (principal==null){// 未登录，则跳转到登录页
            return "redirect:/index";
        }
        
        //TODO 清除登录残留信息,并初始加载信息
        
        return "welcome";
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
