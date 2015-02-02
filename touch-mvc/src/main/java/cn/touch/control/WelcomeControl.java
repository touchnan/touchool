/**
 * 
 */
package cn.touch.control;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Jan 6, 2015
 *
 * @author <a href="mailto:88052350@qq.com">chegnqiang.han</a>
 */
@Controller
public class WelcomeControl {
    
    @RequestMapping("${web.view.index}")
    public String index() {
        return "index";
    }
    
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
}
