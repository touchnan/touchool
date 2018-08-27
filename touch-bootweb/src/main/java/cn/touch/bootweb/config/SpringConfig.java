package cn.touch.bootweb.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.Filter;

/**
 * Created by chengqiang.han on 2018/8/23.
 */
@Configuration
@ImportResource(locations = {"classpath:spring/context.xml","classpath:spring/mvc-context.xml"})
public class SpringConfig {

    @Bean
//    @Order()
    public FilterRegistrationBean characterEncodingFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new CharacterEncodingFilter());
        bean.addInitParameter("encoding", "UTF-8");
//        bean.addInitParameter("forceEncoding", "true");
        bean.addUrlPatterns("/*");
        return bean;
    }

    @Bean
    public FilterRegistrationBean shiroFilterProxy(ShiroFilterFactoryBean shiroFilter) throws Exception {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter((Filter) shiroFilter.getObject());
        bean.addUrlPatterns("/*");
        return bean;
    }
}
