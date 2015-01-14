package com.uikoo9.gcode.help;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 数据库工具类<br>
 * 1.get con by path<br>
 * 2.get con by properties<br>
 * 3.close con<br>
 * 4.update sql by ...<br>
 * 5.update sql by list<br>
 * 6.query<br>
 * 7.count<br>
 * 8.get table info to qtableinfo<br>
 * 9.get table info to map<br>
 * 10.get pk name<br>
 * 11.get cols info to qinfo list<br>
 * 12.get cols info to map<br>
 * 13.get cols info to map list<br>
 * 14.mysql datatype to java datatype<br>
 * @author qiaowenbin
 * @version 0.0.6.20141111
 * @history
 * 	0.0.6.20141111<br>
 * 	0.0.5.20141015<br>
 * 	0.0.4.20141009<br> 
 * 	0.0.3.20140522<br>
 */
public class QDbUtil {
	
	/**
	 * get con by path
	 * @param path 数据库配置文件地址
	 * @return
	 * @throws Exception
	 */
	public static Connection getCon(String path) throws Exception{
		return getCon(QPropertiesUtil.readProperties(path));
	}
	
	/**
	 * get con by properties
	 * @param properties
	 * @return
	 * @throws Exception
	 */
	public static Connection getCon(Properties properties) throws Exception{
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
	 * update sql by ...
	 * @param con 数据库连接
	 * @param sql sql
	 * @param params 参数
	 * @return 结果
	 * @throws SQLException 异常
	 */
	public static int update(Connection con, String sql, Object... params) throws SQLException{
		PreparedStatement ps = con.prepareStatement(sql);
		
		int index = 1;
		for(Object object : params){
			ps.setObject(index++, object);
		}
		
		return ps.executeUpdate();
	}
	
	/**
	 * update sql by list
	 * @param con
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public static int update(Connection con, String sql, List<Object> params) throws SQLException{
		PreparedStatement ps = con.prepareStatement(sql);
		
		int index = 1;
		for(Object object : params){
			ps.setObject(index++, object);
		}
		
		return ps.executeUpdate();
	}
	
	/**
	 * query
	 * @param con 数据库连接
	 * @param page 分页信息
	 * @param sql sql语句
	 * @return 结果集
	 * @throws Exception 异常
	 */
	public static ResultSet query(Connection con, String sql, QPage page) throws Exception{
		StringBuilder sb = new StringBuilder(sql);
		
		// 添加分页信息
		if(page != null){
			sb.append(" limit " + page.getStart() + "," + page.getPageSize());
		}
		
		return con.prepareStatement(sb.toString()).executeQuery();
	}
	
	/**
	 * 对count方法的封装
	 * @param con
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public static int count(Connection con, String sql) throws Exception{
		ResultSet rs = con.prepareStatement(sql).executeQuery();

		return rs.next() ? rs.getInt(1) : 0;
	}
	
	/**
	 * get table info to qtableinfo
	 * @param con
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public static QTableInfo getQTableInfo(String dbPath, String tableName){
		QTableInfo qTableInfo = new QTableInfo();
		
		Connection con = null;
		try {
			try{
				con = QDbUtil.getCon(dbPath);
				String className = QStringUtil.getClassNameFromTableName(tableName);
				
				qTableInfo.setTableName(tableName);
				qTableInfo.setClassName(QStringUtil.getClassNameFromTableName(tableName));
				qTableInfo.setVariableName(QStringUtil.firstLower(className));
				qTableInfo.setPkName(getPkName(con, tableName));
				qTableInfo.setColumns(getColumnInfoList(con, tableName));
			}finally{
				QDbUtil.closeCon(con);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return qTableInfo;
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
	 * get cols info to qinfo list
	 * @param con
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public static List<QColumnInfo> getColumnInfoList(Connection con, String tableName) throws Exception{
		return new ArrayList<QColumnInfo>(getColumnInfoMap(con, tableName).values());
	}
	
	/**
	 * get cols to map
	 * @param con
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public static Map<String, QColumnInfo> getColumnInfoMap(Connection con, String tableName) throws Exception{
		Map<String, QColumnInfo> map = new HashMap<String, QColumnInfo>();
		
		// 找到唯一约束的列集合
		ResultSet rs = con.getMetaData().getIndexInfo(null, null, tableName, true, false);
		List<String> uniqueColumns = new ArrayList<String>();
		while(rs.next()){
			uniqueColumns.add(rs.getString("COLUMN_NAME"));
		}
		
		// 找到列信息
		ResultSet colrs = con.getMetaData().getColumns(null, "%", tableName, "%");
		while(colrs.next()){
			QColumnInfo column = new QColumnInfo();
			column.setColumnName(colrs.getString("COLUMN_NAME"));
			column.setDbType(colrs.getString("TYPE_NAME"));
			column.setJavaType(mysqlToJava(colrs.getString("TYPE_NAME")));
			column.setRemarks(colrs.getString("REMARKS"));
			column.setIsNull(QStringUtil.toBoolean(colrs.getString("IS_NULLABLE")));
			column.setIsAuto(QStringUtil.toBoolean(colrs.getString("IS_AUTOINCREMENT")));
			column.setIsUnique(uniqueColumns.contains(column.getColumnName()));
			
			map.put(column.getColumnName(), column);
		}
		
		return map;
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
	
	/**
	 * mysql datatype to java datatype
	 * @param mysqlType
	 * @return
	 */
	public static String mysqlToJava(String mysqlType){
		if(QStringUtil.notEmpty(mysqlType)){
			if(mysqlType.equals("VARCHAR")){
				return "String";
			}
			if(mysqlType.equals("CHAR")){
				return "String";
			}
			if(mysqlType.equals("BLOB")){
				return "byte[]";
			}
			if(mysqlType.equals("TEXT")){
				return "String";
			}
			if(mysqlType.equals("INT")){
				return "Integer";
			}
			if(mysqlType.equals("BIGINT")){
				return "BigInteger";
			}
			if(mysqlType.equals("FLOAT")){
				return "Float";
			}
			if(mysqlType.equals("DOUBLE")){
				return "Double";
			}
			if(mysqlType.equals("DATE")){
				return "Date";
			}
			if(mysqlType.equals("TIME")){
				return "Time";
			}
			if(mysqlType.equals("DATETIME")){
				return "Timestamp";
			}
			if(mysqlType.equals("TIMESTAMP")){
				return "Timestamp";
			}
			
			return null;
		}else{
			return null;
		}
	}
	
}
