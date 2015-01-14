package com.uikoo9.gcode.help;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件工具类<br>
 * 1.获取jar包的路径<br>
 * 2.生成文件 by stream<br>
 * 3.生成文件 to tmp<br>
 * 4.删除文件<br>
 * 5.复制文件 by file<br>
 * 6.复制文件 by stream<br>
 * 7.复制文件 from jar<br>
 * 8.获取文件后缀<br>
 * 9.修改文件后缀<br>
 * 10.获取某文件夹下的所有文件的路径<br>
 * @author qiaowenbin
 * @version 0.0.8.20141229
 * @history
 * 	0.0.8.20141229<br>
 * 	0.0.7.20141209<br>
 * 	0.0.6.20141117<br>
 * 	0.0.5.20141109<br>
 * 	0.0.4.20140803<br>
 */
public class QFileUtil {
	
	/**
	 * jar包得到自身的路径
	 * @return
	 */
	public static String getJarPath() {
		String res = null;
		
		try {
			res = URLDecoder.decode(QPropertiesUtil.class.getProtectionDomain().getCodeSource().getLocation().getFile(), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return res;
	}
	
	/**
	 * 生成文件 by stream
	 * @param in
	 * @param target
	 */
	public static void createFileByInputStream(InputStream in, File targetFile){
		try {
			FileOutputStream fout = new FileOutputStream(targetFile);
			
			byte[] b = new byte[1024];
			int len = 0;
			while ((len = in.read(b)) != -1) {
				fout.write(b, 0, len);
			}
			
			fout.flush();
			in.close();
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 生成文件 to tmp
	 * @param in
	 * @return 临时文件的绝对路径
	 */
	public static String createFileToTmp(String tmpFileName, String tmpFileExt, InputStream in){
		if(QStringUtil.allNotEmpty(new String[]{tmpFileName,tmpFileExt}) && in != null){
			try {
				File tmpFile = File.createTempFile(tmpFileName, tmpFileExt);
				FileOutputStream fout = new FileOutputStream(tmpFile);
				
				byte[] b = new byte[1024];
				int len = 0;
				while ((len = in.read(b)) != -1) {
					fout.write(b, 0, len);
				}
				
				fout.flush();
				in.close();
				fout.close();
				
				return tmpFile.getAbsolutePath();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	/**
	 * 删除文件
	 * @param path
	 */
	public static void delFile(String path){
		File file = new File(path);
		if(file.exists()){
			file.delete();
		}
	}
	
	/**
	 * 复制文件 by file
	 * @param source
	 * @param target
	 */
	public static void copyFile(File source, File target){
		try {
			copyFile(new FileInputStream(source), new FileOutputStream(target));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 复制文件 by stream
	 * @param source
	 * @param target
	 */
	public static void copyFile(FileInputStream source, FileOutputStream target){
		try {
			FileChannel in = null;
			FileChannel out = null;
			
			try{
				in = source.getChannel();
				out = target.getChannel();
				in.transferTo(0, in.size(), out);
			}finally{
				source.close();
				in.close();
				target.close();
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 复制文件 from jar
	 * @param sourcePath
	 * @param destPath	/com/uikoo9/util/view/ucenter/
	 * @param fileName
	 */
	public static void copyFileFormJar(String sourcePath, String destPath, String fileName){
		if(QStringUtil.allNotEmpty(sourcePath, destPath, fileName)){
			InputStream sourceInputStream = QFileUtil.class.getResourceAsStream(sourcePath + fileName);
			File targetFile = new File(destPath + File.separator + fileName);
			if(targetFile.exists()) targetFile.delete();
			
			QFileUtil.createFileByInputStream(sourceInputStream, targetFile);
		}
	}
	
	/**
	 * 获取文件名的后缀
	 * @param fileName 文件名
	 * @return null或文件名的后缀
	 */
	public static String getFileExt(String fileName){
		if(QStringUtil.isEmpty(fileName)) return null;
		
		int dot = fileName.lastIndexOf('.');   
		if(dot == -1 || dot + 1 == fileName.length()) return null;
		
		return fileName.substring(dot + 1);
	}
	
	/**
	 * 修改文件后缀
	 * @param fileName 文件名
	 * @param ext 后缀
	 * @return
	 */
	public static String changeFileExt(String fileName, String ext){
		if(QStringUtil.isEmpty(fileName)) return null;
		
		int dot = fileName.lastIndexOf('.');   
		if(dot == -1 || dot + 1 == fileName.length()) return null;
		
		String fileNameStr = fileName.substring(0, dot + 1);
		return fileNameStr + ext;
	}
	
	public static final List<String> fileList = new ArrayList<String>();
	/**
	 * 获取某路径下的所有文件的绝对路径
	 * @param path
	 */
	public static void getAllFiles(String path){
        File dir = new File(path); 
        File[] files = dir.listFiles(); 
        
        if (files != null){
        	for (int i = 0; i < files.length; i++) { 
        		if (files[i].isDirectory()) { 
        			getAllFiles(files[i].getAbsolutePath()); 
        		} else { 
        			fileList.add(files[i].getAbsolutePath());                    
        		} 
        	} 
        }
	}
	
}
