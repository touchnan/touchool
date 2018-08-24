package cn.touch.bootweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by chengqiang.han on 2018/7/16.
 */
@Component
public class ContentArguments {
    @Autowired
    public ContentArguments(ApplicationArguments args) {
        System.out.println("~~~~|||||~~~~");
    }
}
