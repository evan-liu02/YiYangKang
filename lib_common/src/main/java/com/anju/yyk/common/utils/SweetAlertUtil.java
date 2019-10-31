package com.anju.yyk.common.utils;

import android.app.Activity;

import com.anju.yyk.common.widget.sweetalert.SweetAlertDialog;


/**
* 
* @ProjectName: IBroadBand
*
* @Title: SweetAlertUtil.java
*
* @Package cn.com.drpeng.runman.utils
*
* @Description: SweetAlert工具类，用于显示各种样式的AlertDialog
*
* @author wcs
*
* @modify: 
*
* @date 2015年8月12日 上午11:52:52
*/
public class SweetAlertUtil {
	
	/**
	 * 只显示标题
	 * @param activity
	 * @param title			标题
	 */
	public static void showBaseAlert(Activity activity, String title){
		new SweetAlertDialog(activity).setTitleText(title).show();
	}
	
	/**
	 * 显示标题和内容
	 * @param activity
	 * @param title			标题
	 * @param content		内容
	 */
	public static void showTitleWithContentAlert(Activity activity, String title, String content){
		new SweetAlertDialog(activity)
			.setTitleText(title)
			.setContentText(content)
			.show();
	}
	
	/**
	 * 显示异常样式
	 * @param activity
	 * @param title			标题
	 * @param content		内容
	 * @param listener		用于Dialog布局按钮的监听
	 */
	public static void showErrorAlert(Activity activity, String title, String content, SweetAlertDialog.OnSweetButtonClickListener listener){
		new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
			.setTitleText(title)
			.setContentText(content)
			.setButtonClickListener(listener)
			.show();
	}
	
	/**
	 * 显示警告样式
	 * @param activity
	 * @param title
	 * @param content
	 * @param confirmText
	 * @param listener
	 */
	public static void showWarnAlert(Activity activity, String title, String content, String confirmText
			, SweetAlertDialog.OnSweetButtonClickListener listener){
		new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
		.setTitleText(title)
		.setContentText(content)
		.setConfirmText(confirmText)
		.setButtonClickListener(listener)
		.show();
	}
	
	/**
	 * 显示成功完成样式
	 * @param activity
	 * @param title			标题
	 * @param content		内容
	 * @param listener		用于Dialog布局按钮的监听
	 */
	public static void showSuccessAlert(Activity activity, String title, String content, SweetAlertDialog.OnSweetButtonClickListener listener){
		new SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE)
			.setTitleText(title)
			.setContentText(content)
			.setButtonClickListener(listener)
			.show();
	}
	
	/**
	 * 自定义头部图像
	 * @param activity
	 * @param title			标题
	 * @param content		内容
	 * @param drawableId	图片id
	 */
	public static void showCustomImageAlert(Activity activity, String title, String content, int drawableId){
		new SweetAlertDialog(activity, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
	    .setTitleText(title)
	    .setContentText(content)
	    .setCustomImage(drawableId)
	    .show();
	}
	
	/**
	 * 显示确认，取消按钮
	 * @param activity
	 * @param title
	 * @param content
	 * @param confirm
	 * @param cancel
	 */
	public static void showConfirmAndCancelAlert(Activity activity, String title, String content, String confirm, String cancel
			, SweetAlertDialog.OnSweetButtonClickListener dialogListener){
		new SweetAlertDialog(activity, SweetAlertDialog.WARNING_TYPE)
		  .setTitleText(title)
		  .setContentText(content)
		  .setCancelText(cancel)
		  .setConfirmText(confirm)
		  .showCancelButton(true)
		  .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
		    @Override
		    public void onClick(SweetAlertDialog sDialog) {
		    	sDialog.dismiss();
		    }
		  })
		  .setButtonClickListener(dialogListener)
		  .show();
	}
	
}
