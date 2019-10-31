package com.anju.yyk.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;

import com.anju.yyk.common.utils.klog.KLog;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * 文件工具类
 * 2012-10-7
 *
 */
public class FileUtil {

	public static final String TAG = "FileUtil";
	
	/**
	 * 字符集
	 */
	private static final String CHARSET = "utf-8";

	/**
	 * 写入图片文件字节流容量
	 */
	public static final int IO_BUFFER_SIZE = 8 * 1024;

	/**
	 * 在指定路径创建文件
	 * 
	 * @param path
	 * @return
	 */
	public static File getFile(String path) {
		File f = new File(path);
		return f;
	}
	
	/**
	 * 是否存在文件
	 * @param path
	 * @return
	 */
	public static boolean exites(String path){
		return new File(path).exists();
	}
	
	/**
	 * 检查目录是否存在，没有则创建
	 * @param file
	 */
	public static void checkDir(String file){
		File f = new File(file);
		if (f.exists()) return ;
		if (f.isDirectory()) f.mkdirs();
		
		File parent = f.getParentFile();
		if (!parent.exists()) parent.mkdirs();
	}

	/**
	 * 获取本地文件路径，如果有sd卡则返回sd卡路径，否则返回内部存储路径
	 * @return
	 */
	public static String getCacheDir(Context ctx){
		final String storageState = Environment.getExternalStorageState();
		
		if (Environment.MEDIA_MOUNTED.equals(storageState)) 
			return Environment.getExternalStorageDirectory() + "/";
		else
			return ctx.getCacheDir().getPath() + "/";
	}
	
	/**
	 * 读取文件
	 * @param file
	 * @return
	 */
	public static String readFile(String file){
		StringBuffer sb = new StringBuffer();
		BufferedReader in = null;
		try {
			in = new BufferedReader(
							new InputStreamReader(
									new FileInputStream(file),CHARSET));
			String aLine="";
			while ((aLine = in.readLine()) != null) 
				sb.append(aLine + "\n");
		}catch(Exception e){
			KLog.d(e.getMessage());
		}finally{
			try{
				if(in!=null) in.close();
			}catch(Exception e){}
		}
		return sb.toString();
	}
	
	/**
	 * 写入文件
	 * @param file
	 * @param str
	 */
	public static boolean writeFile(String file, String str){
		boolean result = true;
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(
					new OutputStreamWriter(
							new FileOutputStream(file),CHARSET));
			out.append(str);
		}catch(Exception e){
			KLog.d(e.getMessage());
			result = false;
		}finally{
			try{
				if(out!=null) out.close();
			}catch(Exception e){}
		}
		return result;
	}

	/**
	 * 根据图片对象在指定路径创建PNG图片文件
	 * 
	 * @param path
	 *            指定文件路径
	 * @param bmp
	 *            图片对象
	 * @return
	 */
	public static boolean createImageFile(String path, Bitmap bmp, CompressFormat type) {
		boolean flag = false;
		File f = null;
		OutputStream fOut = null;
		try {
			f = new File(path);
			f.createNewFile();

			fOut = new BufferedOutputStream(new FileOutputStream(f),
					IO_BUFFER_SIZE);
			bmp.compress(type, 50, fOut);
			fOut.flush();
			bmp = null;
		} catch (IOException e) {
			KLog.d(e.getMessage());
		} finally {
			if (fOut != null) {
				try {
					fOut.close();
				} catch (IOException e) {
					KLog.d(e.getMessage());
				}
			}
		}
		return flag;
	}
	
	/**
	 * 将图片保存为文件
	 * @param path 路径
	 * @param bmp 图片
	 * @return 是否保存
	 */
	public static boolean saveImageFile(String path, Bitmap bmp){
		if (StringUtil.isEmpty(path)) return false;
		
		if (path.toLowerCase().endsWith(".jpg"))
			return createImageFile( path,  bmp , CompressFormat.JPEG);
		else if (path.toLowerCase().endsWith(".png"))
			return createImageFile( path,  bmp, CompressFormat.PNG);
		
		return false;
	}

	/**
	 * 删除指定路径文件
	 * 
	 * @param filePath 文件路径
	 *
	 * @return 是否成功
	 */
	public static boolean remove(String filePath) {
		boolean flag = false;
		try {
			File f = new File(filePath);
			if (f.exists()) {
				f.delete();
				flag = true;
			}
		} catch (Exception e) {
			KLog.d(e.getMessage());
		}
		return flag;
	}

	/**
	 * 删除指定文件对象
	 * 
	 * @param f 文件对象
	 *
	 * @return 是否
	 */
	public static boolean remove(File f) {
		boolean flag = false;
		try {
			if (f != null && f.exists()) {
				f.delete();
				flag = true;
			}
		} catch (Exception e) {
			KLog.d(e.getMessage());
		}
		return flag;
	}

	/**
	 * 删除指定目录下的所有文件
	 * 
	 * @param folderPath 目录文件路径
	 *
	 * @return 是否
	 */
	public static boolean clearFolder(String folderPath) {
		boolean flag = false;
		try {
			File fFolder = new File(folderPath);
			if (fFolder.exists() && fFolder.isDirectory() && fFolder.canWrite()) {
				File[] fs = fFolder.listFiles();
				for (File f : fs) {
					if (f.exists()) {
						f.delete();
					}
				}
				flag = true;
			}
		} catch (Exception e) {
			KLog.d(e.getMessage());
		}
		return flag;
	}

	/**
	 * 使用文件修改时间，对文件对象集合中的文件进行排序
	 * 
	 * @param fList 文件对象集合
	 *
	 */
	public static void sortWithlastModified(List<File> fList) {
		Collections.sort(fList, mComparator);
	}

	/**
	 * 文件修改时间比较对象
	 */
	private static Comparator<File> mComparator = new Comparator<File>() {
		@Override
		public int compare(File lhs, File rhs) {
			int c = 0;
			if (lhs.lastModified() > rhs.lastModified()) {
				c = 1;
			} else if (lhs.lastModified() < rhs.lastModified()) {
				c = -1;
			}
			return c;
		}
	};
}
