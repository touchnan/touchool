package cn.touch.bootweb;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

/**
 * https://www.cnblogs.com/s648667069/p/6513335.html
 * https://blog.csdn.net/wang11234ilike/article/details/60575239
 *
 * https://www.jianshu.com/p/3c94d7e76998?utm_source=oschina-app
 * https://blog.csdn.net/J3oker/article/details/52556008
 */
@SpringBootApplication(scanBasePackages = {"cn.touch.bootweb.config"},exclude = {DataSourceAutoConfiguration.class})
public class TouchBootwebApplication {


//	public static final CountDownLatch downLatch = new CountDownLatch(1);
	public static void main(String[] args) {
		SpringApplication.run(TouchBootwebApplication.class, args);

//		org.springframework.boot.autoconfigure.aop.AopAutoConfiguration
//		org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration
//		org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration

//		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
//			System.out.println("TouchBootwebApplication.main");
//			System.out.println("args = [" + args + "]");
//			System.out.println("args = " + args);
//			downLatch.countDown();
//		}));
//		try {
//			downLatch.await();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}

//		System.exit(0);
	}


//	@Bean
//	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//		return args -> {
//			System.out.println("Let's inspect the beans provided by Spring Boot:");
//			String[] beanNames = ctx.getBeanDefinitionNames();
//			Arrays.sort(beanNames);
//			for (String beanName : beanNames) {
//				System.out.println(beanName);
//			}
//		};
//	}
//
//	@Bean
//	public ExitCodeGenerator exit() {
//		System.out.println("exit invoke");
//		return ()-> 22;
//	}
}
