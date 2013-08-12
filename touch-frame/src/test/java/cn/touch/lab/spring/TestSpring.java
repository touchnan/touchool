/**
 * 
 */
package cn.touch.lab.spring;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.touch.lab.spring.serv.IUserService;

/**
 * Apr 28, 2013
 * @author <a href="mailto:touchnan@gmail.com">chegnqiang.han</a>
 * 
 */
public class TestSpring {
	private ApplicationContext context;
	
	@Before
	public void before() {
		context =
			    new ClassPathXmlApplicationContext(new String[] {"spring/context.xml"});
	}
	
	@Test
	public void t() {
	    IUserService serv = context.getBean(IUserService.class);
		System.out.println(serv.validate());
	}

}
