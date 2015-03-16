/**
 * 
 */
package cn.touch.control.site;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.touch.control.BaseControl;

/**
 * Mar 16, 2015
 *
 * @author <a href="mailto:88052350@qq.com">chegnqiang.han</a>
 */
@Controller
public class SiteControl extends BaseControl {
	
    @RequestMapping("${web.view.index}")
    public String index() {
        return "site";
    }
    
    @RequestMapping("/site")
    public String site() {
        return "site";
    }    
}
