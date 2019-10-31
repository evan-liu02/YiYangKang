package com.anju.yyk.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author LeoWang
 *
 * @Package cn.com.drpeng.runman.common.utils
 *
 * @Description
 *
 * @Date 2019/7/2 13:28
 *
 * @modify:
 */
public class StringUtil {
	// $s代表为字符串数值；$d代表为整数数值；$f代表为浮点型数值。
	
	public static String notNull(String str){
		return notNull(str, "");
	}
	public static String notNull(String str, String defalutStr){
		return str==null?defalutStr:str;
	}
	
	/**
	 * 删除最后一个字符
	 * @param str
	 * @return
	 */
	public static String cutLastChar(String str){
		if (isEmpty(str)) return "";
		return str.substring(0, str.length()-1);
	}
	
	/**
	 * @param str
	 * @param num
	 * @return  
	 */
	public static String subLastString(String str,int num) {
		if(str==null) return "";
		
		return str.substring(str.length()-num);
		
	}
	/**
	 * @param str
	 * @param num
	 * @return  
	 */
	public static String subCutLastString(String str,int num) {
		if(str==null) return "";
		
		return str.substring(0,str.length()-num);
	}
	
	/**
	 * 截取前面一段长度的字符串
	 * @param str
	 * @param length 	0不截取
	 * @return
	 */
	public static String cutStr(String str , int length){
		return cutStrWithDot(str , length,"");
	}
	/**
	 * 截取前面一段长度的字符串
	 * @param str
	 * @param length 	0不截取
	 * @return
	 */
	public static String cutStrWithDot(String str , int length){
		return cutStrWithDot(str , length,"...");
	}
	/**
	 * 截取前面一段长度的字符串
	 * @param str
	 * @param length  	0不截取
	 * @param dot  		最后补上的字符串，默认"..."
	 * @return
	 */
	public static String cutStrWithDot(String str , int length,String dot){
		if (str==null) return "";
		if (dot==null) dot = "";
		if (length<=0) return str;
		if (length<=dot.length()) return dot;
		
		if (str.length()>length)
			return str.substring(0,length - dot.length()) + dot;
		else 
			return str;
	}
	
	/**
	 * url encode
	 * @param para
	 * @return
	 */
	public static String urlEncode(String para){
		return urlEncode(para,"UTF-8");
	}
	/**
	 * url encode
	 * @param para
	 * @return
	 */
	public static String urlEncode(String para,String charset){
		String result = "";
		try{
			result = URLEncoder.encode(para,charset);
		}catch(Exception e){}
		
		return result;
	}
	
	
	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return true - 全为空， false - 有一个不为空
	 */
	public static boolean isEmpty(String ...str){
		if (str == null) return true;
		for (int i=0;i<str.length;i++)
			if (str[i]!=null && !"".equals(str[i]))return false;
		
		return true;
	}
	
	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return true - 有一个不为空， false - 全部不为空
	 */
	public static boolean hasEmpty(String ...str){
		if (str == null) return true;
		for (int i=0;i<str.length;i++)
			if (str[i]==null || "".equals(str[i]))return true;
		
		return false;
	}
	
	
	
	/**
	 * 把字符串转化为int
	 * @return
	 */
	public static int parseInt(String str){
		return parseInt(str, 0);
	}
	/**
	 * 把字符串转化为int
	 * @return
	 */
	public static int parseInt(String str, int defaultValue){
		int result=defaultValue;
		if (str==null) return result;
		try{
			result = Integer.parseInt(str.trim());
		}catch (Exception e){}
		return result;
	}
	
	/**
	 * 把字符串转化为int
	 * @return
	 */
	public static long parseLong(String str){
		long result=0;
		if (str==null) return result;
		try{
			result = Long.parseLong(str.trim());
		}catch (Exception e){}
		return result;
	}
	
	/**
	 * 字符串转为int set
	 * @param t3
	 * @return
	 */
	public static Set<Integer> strToIntSet(String str) {
		Set<Integer> result = new HashSet<Integer>();
		if (isEmpty (str)) return result;
		
		String[] ids = str.split(",");
		for (String s: ids)
			try{
				result.add(Integer.parseInt(s.trim()));
			}catch(Exception e){}
		
		return result;
	}
	
	public static String md5(String string) {

	    byte[] hash;

	    try {

	        hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));




		    StringBuilder hex = new StringBuilder(hash.length * 2);

		    for (byte b : hash) {

		        if ((b & 0xFF) < 0x10) hex.append("0");

		        hex.append(Integer.toHexString(b & 0xFF));

		    }

		    return hex.toString();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return "";

	}
	
	/**
	 * 将字符串转成MD5值
	 * 
	 * @param string
	 * @return
	 */
	public static String stringToMD5(String string) {
		byte[] hash;

		try {
			hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}

		StringBuilder hex = new StringBuilder(hash.length * 2);
		for (byte b : hash) {
			if ((b & 0xFF) < 0x10)
				hex.append("0");
			hex.append(Integer.toHexString(b & 0xFF));
		}

		return hex.toString();
	}
    
	public static String convert(String utfString){
		StringBuilder sb = new StringBuilder();
		int i = -1;
		int pos = 0;
		
		while((i=utfString.indexOf("\\u", pos)) != -1){
			sb.append(utfString.substring(pos, i));
			if(i+5 < utfString.length()){
				pos = i+6;
				sb.append((char)Integer.parseInt(utfString.substring(i+2, i+6), 16));
			}
		}
		sb.append(utfString.substring(pos));
		return sb.toString();
	}
}