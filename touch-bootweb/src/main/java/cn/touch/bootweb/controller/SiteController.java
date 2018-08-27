/**
 * 
 */
package cn.touch.bootweb.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.touch.web.controller.BaseController;

/**
 * Mar 16, 2015
 *
 * @author <a href="mailto:88052350@qq.com">chengqiang.han</a>
 */
@Controller
public class SiteController extends BaseController {
	
    @RequestMapping("${web.view.welcome}")
    public String welcome() {
        return "welcome";
    }	
	
    @RequestMapping("${web.view.index}")
    public String index() {
        return "site";
    }
    
    @RequestMapping("/site")
    public String site() {
        return "site";
    }
    
    @RequestMapping("/scroll")
    public String scroll() {
    	return "scroll";
    }
    
    @RequestMapping("/json")
    public @ResponseBody Object json() {
    	Map<String,String> n = new HashMap<String,String>();
    	n.put("a", "2");
    	n.put("b", "3");
    	return n;
    	
    	//return "<div class='scroll'>aaa,bbb</div>";
    }
    
    @RequestMapping("/page")
    public String page() {
    	return "page";
    }
}
