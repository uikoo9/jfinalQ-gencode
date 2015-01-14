package com.uikoo9.gcode.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 数据库工具类
 * @author qiaowenbin
 */
public class QDbUtil {
	
	/**
	 * get con by path
	 * @param path 数据库配置文件地址
	 * @return
	 * @throws Exception
	 */
	public static Connection getCon(String path) throws Exception{
		Properties properties = QUtil.readProperties(path);
		String dbDriver = properties.getProperty("db.driver");
		String dbUrl = properties.getProperty("db.url");
		String dbUsername = properties.getProperty("db.username");
		String dbPassword = properties.getProperty("db.password");
		
		Class.forName(dbDriver);
		return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
	}
	
	/**
	 * close con
	 * @param con 连接
	 * @throws Exception 异常
	 */
	public static void closeCon(Connection con) throws Exception{
		if(con != null) con.close();
	}
	
	/**
	 * get table info to map
	 * @param tableName
	 * @return
	 */
	public static Map<String, Object> getTableInfoMap(String dbPath, String tableName){
		Map<String, Object> info = new HashMap<String, Object>();
		
		Connection con = null;
		try {
			try{
				if(QStringUtil.notEmpty(dbPath) && QStringUtil.notEmpty(tableName)){
					con = QDbUtil.getCon(dbPath);
					
					String cname = QStringUtil.getClassNameFromTableName(tableName);
					info.put("pname", tableName.split("_")[1]);
					info.put("cname", cname);
					info.put("vname", QStringUtil.firstLower(cname));
					info.put("url", tableName.replace("_", "/").substring(1));
					info.put("ftl", tableName.replace("_", "-").substring(2));
					info.put("tablename", tableName);
					info.put("pkname", QDbUtil.getPkName(con, tableName));
					info.put("cols", QDbUtil.getCols(con, tableName));
				}
			}finally{
				QDbUtil.closeCon(con);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return info;
	}
	
	/**
	 * get pk name
	 * @param con
	 * @param tableName
	 * @return
	 */
	public static String getPkName(Connection con, String tableName) throws Exception{
		ResultSet pkrs = con.getMetaData().getPrimaryKeys(null, null, tableName);
		return pkrs.next() ? pkrs.getString("COLUMN_NAME") : null;
	}
	
	
	/**
	 * get cols info to map list
	 * @param con
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, String>> getCols(Connection con, String tableName) throws Exception{
		List<Map<String, String>> cols = new ArrayList<Map<String,String>>();
		
		String pkName = getPkName(con, tableName);
		ResultSet colrs = con.getMetaData().getColumns(null, "%", tableName, "%");
		while(colrs.next()){
			if(QStringUtil.notEmpty(pkName) && !pkName.equals(colrs.getString("COLUMN_NAME"))){
				Map<String, String> col = new HashMap<String, String>();
				col.put("colname", colrs.getString("COLUMN_NAME"));
				col.put("coltype", colrs.getString("TYPE_NAME"));
				col.put("remarks", colrs.getString("REMARKS"));
				
				cols.add(col);
			}
		}
		
		return cols;
	}
	
}
