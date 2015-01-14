package com.uikoo9.gcode.help;

import java.io.IOException;
import java.util.Properties;

/**
 * 资源工具类<br>
 * 1.读取jar包路径<br>
 * 2.读取配置文件<br>
 * 3.获取配置文件中的属性<br>
 * 4.获取配置文件中的属性,返回boolean值<br>
 * @author qiaowenbin
 * @version 0.0.2.20141220
 * @history
 * 	0.0.2.20141220<br>
 * 	0.0.1.20141117<br>
 */
public class QPropertiesUtil {
	
	public static final Properties config = readProperties("/config.properties");
	
	/**
	 * 读取配置文件
	 * @param in
	 * @return
	 */
	public static Properties readProperties(String path){
		Properties p = new Properties();
		try {
			p.load(QPropertiesUtil.class.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return p;
	}
	
	/**
	 * 获取config的value
	 * @param key
	 * @return
	 */
	public static String get(String key){
		return getProperty(config, key);
	}
	
	/**
	 * 获取配置文件中的属性
	 * @param properties
	 * @param key
	 * @return
	 */
	public static String getProperty(Properties properties, String key){
		if(properties != null && QStringUtil.notEmpty(key)){
			return properties.getProperty(key);
		}else{
			return null;
		}
	}
	
	/**
	 * 获取config的值
	 * @param key
	 * @return
	 */
	public static boolean getBoolean(String key){
		return getPropertyToBoolean(config, key);
	}
	
	/**
	 * 获取配置文件中的属性,返回boolean值
	 * @param properties
	 * @param key
	 * @return
	 */
	public static boolean getPropertyToBoolean(Properties properties, String key){
		if(properties != null && QStringUtil.notEmpty(key)){
			return Boolean.parseBoolean(properties.getProperty(key));
		}else{
			return false;
		}
	}
	
}
