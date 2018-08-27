package cn.touch.bootweb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * Created by chengqiang.han on 2018/8/27.
 */

//@Configuration
//@EnableWebMvc()
//@ImportResource(locations = {"classpath:spring/mvc-context.xml"})
//public class MyWebApplicationInitializer  implements WebApplicationInitializer{
public class MyWebApplicationInitializer {
//    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // Load Spring web application configuration

//        XmlWebApplicationContext cxt = new XmlWebApplicationContext();
//        cxt.setConfigLocation("classpath:spring/mv-context.xml");
//
//        ServletRegistration.Dynamic registration = servletContext.addServlet("dispatcher", new DispatcherServlet(cxt));
//        registration.setLoadOnStartup(1);
//        registration.addMapping("/");

//        AnnotationConfigWebApplicationContext ac = new AnnotationConfigWebApplicationContext();
//        ac.register(AppConfig.class);
//        ac.refresh();

        // Create and register the DispatcherServlet
//        DispatcherServlet servlet = new DispatcherServlet(ac);
//        ServletRegistration.Dynamic registration = servletCxt.addServlet("app", servlet);
//        registration.setLoadOnStartup(1);
//        registration.addMapping("/app/*");
    }
}
