/**
 * 
 */
package cn.touch;

import java.util.Locale;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;

/**
 * Aug 11, 2013
 * 
 * @author <a href="mailto:touchnan@gmail.com">chengqiang.han</a>
 * 
 */
public class Contexts implements ApplicationContextAware, MessageSource{

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
	 * @see org.springframework.context.MessageSource#getMessage(java.lang.String, java.lang.Object[], java.lang.String, java.util.Locale)
	 */
	@Override
	public String getMessage(String paramString1, Object[] paramArrayOfObject, String paramString2,
			Locale paramLocale) {
		return context.getMessage(paramString1, paramArrayOfObject, paramString2, paramLocale);
	}

	/* (non-Javadoc)
	 * @see org.springframework.context.MessageSource#getMessage(java.lang.String, java.lang.Object[], java.util.Locale)
	 */
	@Override
	public String getMessage(String paramString, Object[] paramArrayOfObject, Locale paramLocale)
			throws NoSuchMessageException {
		return context.getMessage(paramString, paramArrayOfObject, paramLocale);
	}

	/* (non-Javadoc)
	 * @see org.springframework.context.MessageSource#getMessage(org.springframework.context.MessageSourceResolvable, java.util.Locale)
	 */
	@Override
	public String getMessage(MessageSourceResolvable paramMessageSourceResolvable, Locale paramLocale)
			throws NoSuchMessageException {
		return context.getMessage(paramMessageSourceResolvable, paramLocale);
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
