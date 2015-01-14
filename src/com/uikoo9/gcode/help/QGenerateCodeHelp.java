package com.uikoo9.gcode.help;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Map;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

/**
 * 代码生成-业务代码
 * @author qiaowenbin
 */
public class QGenerateCodeHelp {
	
	public static final String dbPath = "/com/uikoo9/gcode/db.properties";
	
	/**
	 * 填充表格-获取数据库表信息
	 */
	public static void fillTable(DefaultTableModel model){
		Connection con = null;
		
		try {
			try {
				con = QDbUtil.getCon(dbPath);
				ResultSet rs = con.getMetaData().getTables(null,null,"",null);
				while(rs.next()){
					Vector<String> v = new Vector<String>();
					v.add(0, "");
					v.add(1, rs.getString("TABLE_NAME"));
					
					model.addRow(v);
				}
			} finally {
				QDbUtil.closeCon(con);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 生成一个表对应的代码
	 * @param tableName
	 * @param destBasePath
	 * @return
	 */
	public static String genCodeFromTable(String tableName, String destBasePath){
		StringBuilder res = new StringBuilder();
		
		String tmpBasePath = QFileUtil.getJarPath() + File.separator + "com" + File.separator + "uikoo9";
		try {
			Connection con = null;
			try {
				con = QDbUtil.getCon(dbPath);
				res.append(generateJfinalCode(tableName, destBasePath, tmpBasePath));
			} finally {
				QDbUtil.closeCon(con);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return res.toString();
	}
	
	/**
	 * 生成一个表具体的代码for jfinal
	 * @param tableName
	 * @param destBasePath
	 * @param tmpBasePath
	 * @return
	 */
	public static String generateJfinalCode(String tableName, String destBasePath, String tmpBasePath){
		StringBuilder sb = new StringBuilder();
		
		String folderName = tableName.split("_")[1];
		String ftlName = tableName.replace("_", "-").substring(2);
		String className = QStringUtil.getClassNameFromTableName(tableName);
		
		Map<String, Object> map = QDbUtil.getTableInfoMap(dbPath, tableName);
		String tmpPath 	= tmpBasePath + File.separator + "gcode" + File.separator + "tmp";
		String destFTLPath = 
				destBasePath + File.separator + 
				"WebRoot" + File.separator + 
				"WEB-INF" + File.separator + 
				"view" + File.separator + 
				"manage" + File.separator + 
				folderName;
		String destSRCPath = 
				destBasePath + File.separator + 
				"src" + File.separator + 
				"com" + File.separator + 
				"uikoo9" + File.separator + 
				"manage" + File.separator + 
				folderName + File.separator;
		
		boolean indexFtl	= QVelocityUtil.generateCodeByVelocity(map, destFTLPath, 				ftlName + "-index.ftl", 		tmpPath, "ftl-index.vm");
		boolean inputFtl	= QVelocityUtil.generateCodeByVelocity(map, destFTLPath, 				ftlName + "-input.ftl", 		tmpPath, "ftl-input.vm");
		boolean model 		= QVelocityUtil.generateCodeByVelocity(map, destSRCPath + "model",		className + "Model.java", 		tmpPath, "Model.vm");
		boolean controller 	= QVelocityUtil.generateCodeByVelocity(map, destSRCPath + "controller",	className + "Controller.java",	tmpPath, "Controller.vm");
		
		sb.append("generate jfinal code begin...\r\n");
		sb.append("jfinal flt-index生成" + (indexFtl ? "成功" : "失败") + "!\r\n");
		sb.append("jfinal flt-input生成" + (inputFtl ? "成功" : "失败") + "!\r\n");
		sb.append("jfinal model生成" 	+ (model ? "成功" : "失败") + "!\r\n");
		sb.append("jfinal controller生成"+ (controller ? "成功" : "失败") + "!\r\n");
		sb.append("generate jfinal code end...\r\n");
		
		return sb.toString();
	}
	
}
