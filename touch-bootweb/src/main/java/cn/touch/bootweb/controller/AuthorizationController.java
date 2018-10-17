package cn.touch.bootweb.controller;

import cn.touch.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by chengqiang.han on 2018/8/28.
 */
@Controller
public class AuthorizationController extends BaseController {

    @RequestMapping("")
    public @ResponseBody Object auth2() {
        return null;
    }
}
