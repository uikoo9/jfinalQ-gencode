package com.uikoo9.gcode.help;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URLDecoder;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.texen.util.FileUtil;

/**
 * 通用工具类
 * @author qiaowenbin
 */
public class QUtil {
	
	/**
	 * jar包得到自身的路径
	 * @return
	 */
	public static String getJarPath() {
		String res = null;
		
		try {
			res = URLDecoder.decode(QUtil.class.getProtectionDomain().getCodeSource().getLocation().getFile(), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return res;
	}
	
	/**
	 * 读取配置文件
	 * @param in
	 * @return
	 */
	public static Properties readProperties(String path){
		Properties p = new Properties();
		try {
			p.load(QUtil.class.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return p;
	}
	
	/**
	 * 生成代码 by velocity
	 * @param map		变量
	 * @param destPath	目的地址
	 * @param destFile	目的文件名
	 * @param tmpPath	模版地址
	 * @param tmpFile	模版文件名
	 * @return
	 */
	public static boolean generateCodeByVelocity(Map<String, Object> map, String destPath, String destFile, String tmpPath, String tmpFile){
		try {
			// 1.初始化
			Properties properties = new Properties();
			properties.put("file.resource.loader.path", tmpPath);  
			properties.put("input.encoding", "UTF-8");
			properties.put("output.encoding", "UTF-8");
			Velocity.init(properties);
			VelocityContext context = new VelocityContext(map);
				
			// 2.生成代码
			FileUtil.mkdir(destPath);
			BufferedWriter sw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(destPath, destFile)), "UTF-8"));
			Velocity.getTemplate(tmpFile).merge(context, sw);
			sw.flush();
			sw.close();
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
