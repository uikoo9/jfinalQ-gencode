package com.uikoo9.gcode.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 字符串工具类<br>
 * 1.判空<br>
 * 2.判非空<br>
 * 3.判任一空<br>
 * 4.判都非空<br>
 * 5.判在数组内<br>
 * 6.判不在数组内<br>
 * 7.to str<br>
 * 8.to int<br>
 * 9.to boolean<br>
 * 10.to html<br>
 * 11.from html<br>
 * 12.from exception<br>
 * 13.判email<br>
 * 14.判n位数字<br>
 * 15.首字母大写<br>
 * 16.首字母小写<br>
 * 17.分隔字符串<br>
 * 18.分隔字符串返回最后一个字符串<br>
 * 19.将驼峰命名的字符串拆分<br>
 * 20.将表名转换为类名<br>
 * 21.将类名转换为表名<br>
 * @author qiaowenbin
 * @version 0.1.0.20141209
 * @history
 * 	0.1.0.20141209<br>
 * 	0.0.12.20141121<br>
 * 	0.0.11.20141117<br>
 * 	0.0.10.20141109<br>
 * 	0.0.9.20141027<br>
 * 	0.0.8.20141015<br>
 * 	0.0.7.20141009<br>
 * 	0.0.6.20140926<br>
 */
public class QStringUtil {

	/**
	 * 判空
	 * @param s 字符串
	 * @return 判断结果
	 */
	public static boolean isEmpty(String s){
		return s == null || s.trim().equals("") ? true : false;
	}
	
	/**
	 * 判非空
	 * @param s 字符串
	 * @return 判断结果
	 */
	public static boolean notEmpty(String s){
		return s != null && !s.trim().equals("") ? true : false;
	}
	
	/**
	 * 判任一空
	 * @param ss
	 * @return
	 */
	public static boolean anyoneEmpty(String... ss){
		if(ss.length == 0) return true;
		
		for(String s : ss){
			if(isEmpty(s)) return true;
		}
		
		return false;
	}
	
	/**
	 * 判都非空
	 * @param ss
	 * @return
	 */
	public static boolean allNotEmpty(String... ss){
		if(ss.length == 0) return false;
		
		for(String s : ss){
			if(isEmpty(s)) return false;
		}
		
		return true;
	}
	
	/**
	 * 判在数组内
	 * @param s
	 * @param ss
	 * @return
	 */
	public static boolean isIn(String s, String... ss){
		if(ss != null){
			for(String str : ss){
				if(str.equals(s)){
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * 判不在数组内
	 * @param s
	 * @param ss
	 * @return
	 */
	public static boolean notIn(String s, String... ss){
		return !isIn(s, ss);
	}
	
	/**
	 * to str
	 * @param s
	 * @return
	 */
	public static String toStr(String s){
		return s == null ? "" : s;
	}
	
	/**
	 * to int
	 * @param s
	 * @return
	 */
	public static int toInt(String s){
		return isEmpty(s) ? 0 : Integer.parseInt(s);
	}
	
	/**
	 * to boolean
	 * @param s
	 * @return
	 */
	public static Boolean toBoolean(String s){
		if(s != null){
			s = s.toLowerCase();
			
			if(isIn(s, "yes", "t", "1")){
				return true;
			}
			if(isIn(s, "no", "f", "0")){
				return false;
			}
		}
		
		return null;
	}
	
	/**
	 * to html
	 * @param str
	 * @return
	 */
	public static String toHtml(String str) {
		if (str == null || str.length() == 0) {
			return str;
		}
		return str.replaceAll("&lt;", "<").replaceAll("&gt;", ">")
				.replaceAll("&amp;", "&").replaceAll("&quot;", "\"")
				.replaceAll("&nbsp;", " ");
	}
	
	/**
	 * from html
	 * @param str
	 * @return
	 */
	public static String fromHtml(String str) {
		if (str == null || str.length() == 0) {
			return str;
		}
		StringBuffer buf = new StringBuffer();
		char ch = ' ';
		for (int i = 0; i < str.length(); i++) {
			ch = str.charAt(i);
			if (ch == '<') {
				buf.append("&lt;");
			} else if (ch == '>') {
				buf.append("&gt;");
			} else if (ch == '&') {
				buf.append("&amp;");
			} else if (ch == '"') {
				buf.append("&quot;");
			} else if (ch == ' ') {
				buf.append("&nbsp;");
			} else {
				buf.append(ch);
			}
		}
		return buf.toString();
	}
	
	/**
	 * from exception
	 * @param exception
	 * @return
	 */
	public static String fromException(Exception exception){
		try {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			
			exception.printStackTrace(pw);
			
			pw.close();
			sw.close();

			return sw.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 判email
	 * @param s
	 * @return
	 */
	public static boolean isEmail(String s){
		String pattern = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		return Pattern.compile(pattern).matcher(s).find();
	}
	
	/**
	 * 判n位数字
	 * @param s
	 * @param n
	 * @return
	 */
	public static boolean isNLengthNumber(String s, int n){
		String pattern = "^\\d{" + n + "}$";
		return Pattern.compile(pattern).matcher(s).find();
	}
	
	/**
	 * 首字母大写
	 * @param s
	 * @return
	 */
	public static String firstUpper(String s){
		StringBuilder sb = new StringBuilder(s);
		
		sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
		
		return sb.toString();
	}
	
	/**
	 * 首字母小写
	 * @param s
	 * @return
	 */
	public static String firstLower(String s){
		StringBuilder sb = new StringBuilder(s);
		
		sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));
		
		return sb.toString();
	}
	
	/**
	 * 分隔字符串
	 * @param s 待分隔的字符串
	 * @param split 分隔符
	 * @return 分隔后的字符串List
	 */
	public static List<String> splitToList(String s, String split){
		if(s == null) return null;
		
		return new ArrayList<String>(Arrays.asList(s.split(split)));
	}
	
	/**
	 * 分隔字符串返回最后一个字符串
	 * @param str
	 * @param split
	 * @return
	 */
	public static String splitAndReturnLastString(String str, String split){
		if(isEmpty(str)){
			return "";
		}else{
			String[] ss = str.split(split);
			return ss[ss.length - 1];
		}
	}
	
	/**
	 * 将驼峰命名的字符串拆分
	 * @param s
	 * @return
	 */
	public static List<String> tuoFeng(String s){
		List<String> res = new ArrayList<String>();
		
		if(QStringUtil.notEmpty(s)){
			StringBuilder sb = new StringBuilder();
			for(int i=0; i<s.length(); i++){
				if(i != 0 && Character.isUpperCase(s.charAt(i))){
					res.add(sb.toString());
					sb.delete(0, sb.length());
				}
				
				sb.append(s.charAt(i));
				
				if((i+1) == s.length()){
					res.add(sb.toString());
				}
			}
		}
		
		return res;
	}
	
	/**
	 * 将表名转换为类名
	 * @param tableName
	 * @return
	 */
	public static String getClassNameFromTableName(String tableName){
		StringBuilder sb = new StringBuilder();
		if(QStringUtil.notEmpty(tableName)){
			String[] ss = tableName.split("_");
			for(int i=1; i<ss.length; i++){
				sb.append(QStringUtil.firstUpper(ss[i].toLowerCase()));
			}
		}
		
		return sb.toString();
	}
	
	/**
	 * 将类名转换为表名
	 * @param clazzName
	 * @return
	 */
	public static String getTableNameFromClazzName(String clazzName){
		StringBuilder sb = new StringBuilder("t");
		
		List<String> names = QStringUtil.tuoFeng(clazzName);
		for(String s: names){
			sb.append("_" + s.toLowerCase());
		}
		
		return sb.toString();
	}
	
}
