/**
 * 
 */
package cn.touch.frame;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Aug 11, 2013
 * 
 * @author <a href="mailto:touchnan@gmail.com">chegnqiang.han</a>
 * 
 */
public class Contexts implements ApplicationContextAware {

    private static ApplicationContext context;

    private Contexts() {
        super();
    }

    public static <T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }

    public static Object getBean(String bean) {
        return context.getBean(bean);
    }

    public static <T> T getBean(String bean, Class<T> clazz) {
        return context.getBean(bean, clazz);
    }

    public static Object getBean(String bean, Object... objs) {
        return context.getBean(bean, objs);
    }

	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}    
    
    public static void init(ApplicationContext applicationContext) {
        context = applicationContext;
    }

}
