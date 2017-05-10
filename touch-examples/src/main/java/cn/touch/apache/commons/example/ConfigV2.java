/**
 * 
 */
package cn.touch.apache.commons.example;

import java.util.concurrent.TimeUnit;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.ConfigurationBuilderEvent;
import org.apache.commons.configuration2.builder.ReloadingFileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.event.Event;
import org.apache.commons.configuration2.event.EventListener;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.reloading.PeriodicReloadingTrigger;

/**
 * Created by <a href="mailto:88052350@qq.com">chengqiang.han</a> on May 10,
 * 2017.
 */
public class ConfigV2 {

	/**
	 * @param args
	 * @throws ConfigurationException 
	 */
	public static void main(String[] args) throws ConfigurationException {
		// TODO Auto-generated method stub
		testReload();
	}

	public static void testReload() throws ConfigurationException {

		ReloadingFileBasedConfigurationBuilder<PropertiesConfiguration> builder = new ReloadingFileBasedConfigurationBuilder<PropertiesConfiguration>(
				PropertiesConfiguration.class).configure(new Parameters().properties().setFileName("env.properties")
		// .setThrowExceptionOnMissing(true)
		// .setListDelimiterHandler(new DefaultListDelimiterHandler(';'))
		// .setIncludesAllowed(false)
		);

		// 事件监听，
		builder.addEventListener(ConfigurationBuilderEvent.ANY, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) {
				System.out.println("只有调用Build.getConfiguration(),才触发event?");
			}

		});

		System.out.println(builder.getReloadingController() == null);

		PeriodicReloadingTrigger trigger = new PeriodicReloadingTrigger(builder.getReloadingController(), null, 1,
				TimeUnit.SECONDS);
		trigger.start();

		// try {
		// builder.getConfiguration();
		// System.out.println(trigger.isRunning());
		// } catch (ConfigurationException e) {
		// e.printStackTrace();
		// }

		for (int i = 0; i < 10; i++) {
			//此处注意，只有调用builder.getConfiguration()，才会reload, 
			//如果从Configuration config = builder.getConfiguration()实例化的config.getString(""),无reload效果，信息不更新
			// 囧~~  调试了好几个小时，参考http://stackoverflow.com/questions/36730860/commons-configuration2-reloadingfilebasedconfiguration
			System.out.println(builder.getConfiguration().getString("jdbc.url"));
			System.out.println(builder.getConfiguration().getString("jdbc.user"));
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
