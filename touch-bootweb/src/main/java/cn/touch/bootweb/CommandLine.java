package cn.touch.bootweb;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

/**
 * Created by chengqiang.han on 2018/7/16.
 */
@Component
@Profile("production")
public class CommandLine implements CommandLineRunner {
    @Value("${my.uuid}")
    private String port;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("==============================!"+port);
        Map<String,Object> map = Collections.singletonMap("","");
        System.out.println(map);
    }
}
