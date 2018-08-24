package cn.touch.bootweb;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Created by chengqiang.han on 2018/7/16.
 */
@Component
public class Running implements ApplicationRunner{
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("~~~~~~~~~~~~~~~~~~");
    }
}
