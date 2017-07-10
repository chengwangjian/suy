package sy.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 获取properties属性实用类
 * 
 * @author hlw
 * 
 */
public class PropertiesUtil {
	private static final Logger log = Logger.getLogger(PropertiesUtil.class);
	private static Properties props;

	public static String getProperty(String key) {
		if (props == null) {
			props = new Properties();
			InputStream is = PropertiesUtil.class.getClassLoader()
					.getResourceAsStream("resources.properties");
			try {
				props.load(is);
			} catch (IOException e) {
//				e.printStackTrace();
				log.info("载入resources.properties出错" + e.getMessage());
			}
		}
		return props.getProperty(key);
	}
	
	/**
	 * 每次访问都加载key
	 * @author: zhuangxd
	 * 时间：2014-9-4 上午11:47:50
	 * @param key
	 * @return
	 */
	public static String getPropertyLoad(String key) {
//		if (props == null) {
			props = new Properties();
			InputStream is = PropertiesUtil.class.getClassLoader()
					.getResourceAsStream("resources.properties");
			try {
				props.load(is);
			} catch (IOException e) {
//				e.printStackTrace();
				log.info("载入resources.properties出错" + e.getMessage());
			}
//		}
		return props.getProperty(key);
	}
	
	/**
	 * 给配置文件设置值
	 * @author: zhuangxd
	 * 时间：2014-8-8 上午9:20:32
	 * @param key
	 * @param value
	 */
	public static void setProperty(String key, String value) {
		if (props == null) {
			props = new Properties();
			InputStream is = PropertiesUtil.class.getClassLoader()
					.getResourceAsStream("resources.properties");
			try {
				props.load(is);
			} catch (IOException e) {
//				e.printStackTrace();
				log.info("载入resources.properties出错" + e.getMessage());
			}
		}
		props.setProperty(key,value);
	}
	
	/**
	 * 提供载入接口，动态载入配置
	 */
	public static void loadProperties() {
		props = new Properties();
		InputStream is = PropertiesUtil.class.getClassLoader()
				.getResourceAsStream("resources.properties");
		try {
			props.load(is);
		} catch (IOException e) {
			log.info("载入resources.properties出错" + e.getMessage());
		}
	}
	
/*	*//**
	 * 初始化
	 * @author: zhuangxd
	 * 时间：2014-8-8 上午9:18:46
	 * @return
	 *//*
	public static Properties getInstance() {
		if (props == null) {
			props = new Properties();
			InputStream is = PropertiesUtil.class.getClassLoader()
					.getResourceAsStream("resources.properties");
			try {
				props.load(is);
			} catch (IOException e) {
				e.printStackTrace();
				log.error("载入resources.properties出错" + e.getMessage());
			}
		}
		return props;
	}
	
	*//**
	 * 给配置文件设置值
	 * @author: zhuangxd
	 * 时间：2014-8-8 上午9:20:32
	 * @param key
	 * @param value
	 *//*
	public static void setProperty(String key, String value) {
		if (props != null) {
			props.setProperty(key,value);
		}
	}
	
	*//**
	 * 获取配置文件的值
	 * @author: zhuangxd
	 * 时间：2014-8-8 上午9:22:10
	 * @param key
	 * @return
	 *//*
	public static String getProperty2(String key) {
		if (props != null) {
			return props.getProperty(key);
		} else {
			return "0";
		}
	}*/
}
