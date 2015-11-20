package cn.touch.web.spring;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.touch.Contexts;

/**
 * Nov 18, 2015
 *
 * @author <a href="mailto:88052350@qq.com">chegnqiang.han</a>
 */
public class WebContextListener extends ContextLoaderListener {

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.web.context.ContextLoaderListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {
        super.contextInitialized(event);
        Contexts.init(WebApplicationContextUtils.getRequiredWebApplicationContext(event.getServletContext()));
//        registeBeanUtilConverter(event);
    }

//    private void registeBeanUtilConverter(ServletContextEvent event) {
//        Converter cvt = new Converter() {
//            @SuppressWarnings("rawtypes")
//            @Override
//            public Object convert(Class arg0, Object arg1) {
//                if (arg1 == null) {
//                    return null;
//                } else if (arg1 instanceof String && "".equals((String) arg1)) {
//                    return null;
//                } else {
//                    return arg1;
//                }
//            }
//
//        };
//        ConvertUtils.register(cvt, java.lang.Long.class);
//        ConvertUtils.register(cvt, java.lang.Integer.class);
//        ConvertUtils.register(cvt, java.lang.Double.class);
//        ConvertUtils.register(cvt, java.math.BigDecimal.class);
//        ConvertUtils.register(cvt, java.util.Date.class);
//    }
}
