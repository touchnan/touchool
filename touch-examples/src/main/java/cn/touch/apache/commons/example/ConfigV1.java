/**
 * 
 */
package cn.touch.apache.commons.example;

import java.util.concurrent.TimeUnit;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

/**
 * Created by <a href="mailto:88052350@qq.com">chengqiang.han</a> on May 10,
 * 2017.
 */
public class ConfigV1 {

	/**
	 * @param args
	 * @throws ConfigurationException 
	 */
	public static void main(String[] args) throws ConfigurationException {
		testReload();

	}
	
	public static void testReload() throws ConfigurationException{
		PropertiesConfiguration config = new PropertiesConfiguration("env.properties");
		config.setReloadingStrategy(new FileChangedReloadingStrategy());
		for (int i = 0; i < 10; i++) {
			System.out.println(config.getString("jdbc.url"));
			System.out.println(config.getString("jdbc.user"));
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
