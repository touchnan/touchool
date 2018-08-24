package cn.touch.bootweb;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by chengqiang.han on 2018/7/16.
 */
@Component
public class ExitCodeHook {
    @Bean
    public ExitCodeGenerator exit() {
        System.out.println("exit invoke");
        return ()-> 22;
    }

}
