package com.anju.yyk.common.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.anju.yyk.common.R;
import com.anju.yyk.common.utils.klog.KLog;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.List;



/**
 * 
 * @author wcs
 * 
 * @Package cn.com.drpeng.runman.app
 * 
 * @Description app常用工具
 * 
 * @Date 2018/5/3 11:01
 * 
 * @modify:
 */

public class AppUtil {

    private AppUtil(){
        throw new IllegalStateException("don`t need instantiate");
    }

    /**
     * 拍照空间是否够用
     * @return
     */
    public static boolean isCanTakePhoto(){
        if (getSDFreeSize() > 10){
            // 如果剩余空间大于10MB，可以拍照
            return true;
        }else {
            return false;
        }
    }

    /**
     * 获得SD卡剩余空间
     * @return	返回单位MB
     */
    @SuppressWarnings("deprecation")
    private static long getSDFreeSize(){
        //取得SD卡文件路径
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        //获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSize();
        //空闲的数据块的数量
        long freeBlocks = sf.getAvailableBlocks();
        //返回SD卡空闲大小
        //return freeBlocks * blockSize;  //单位Byte
        //return (freeBlocks * blockSize)/1024;   //单位KB
        return (freeBlocks * blockSize)/1024 /1024; //单位MB
    }

    /**
     * 读取照片中exif信息中的旋转角度
     * @param path	照片路径
     * @return	角度
     */
    public static int readPictureDegree(String path){
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 将图片按照某个角度进行旋转
     *
     * @param bm 需要旋转的图片
     * @param degree 旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;

        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }

    /**
     * 获取应用版本号，需要判空
     * @param context 当前上下文
     * @param packageName 包名
     * @return 应用版本号
     */
    public static String getVersionName(Context context ,@NonNull String packageName) {
        String version = null;
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(packageName, 0);
            version = info.versionName;
        } catch (Exception e) {
            version = "";
        }
        return version;
    }

    /**
     * 获得app版本号
     * @param context 当前上下文
     * @return 版本号
     */
    public static String getVersionName(Context context) {
        final String packageName = "com.anju.yyk";
        String version = null;
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(packageName, 0);
            version = info.versionName;
        } catch (Exception e) {
            version = "";
        }
        return version;
    }

    /**
     * dip转pix
     *
     * @param dpValue db值
     * @return px值
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = getResources(context).getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 获得资源
     * @param context 上下文
     * @return Resources
     */
    public static Resources getResources(Context context) {
        return context.getResources();
    }

    /**
     * 得到字符数组
     * @param context 上下文
     * @param id StringArray
     * @return 字符资源中数组
     */
    public static String[] getStringArray(Context context, int id) {
        return getResources(context).getStringArray(id);
    }

    /**
     * pix转dip
     * @param context 上下文
     * @param pix px值
     * @return dp值
     */
    public static int pix2dip(Context context, int pix) {
        final float densityDpi = getResources(context).getDisplayMetrics().density;
        return (int) (pix / densityDpi + 0.5f);
    }


    /**
     * 从 dimens 中获得尺寸
     * @param context 上下文
     * @param id dimen~s id
     * @return dimen值
     */
    public static int getDimens(Context context, int id) {
        return (int) getResources(context).getDimension(id);
    }

    /**
     * 从 dimens 中获得尺寸
     * @param context 上下文
     * @param dimenName dimenName
     * @return dimen
     */
    public static float getDimens(Context context, String dimenName) {
        return getResources(context).getDimension(getResources(context).getIdentifier(dimenName, "dimen", context.getPackageName()));
    }

    /**
     * 从String 中获得字符
     * @param context 上下文
     * @param stringID 字符资源id
     * @return 字符
     */
    public static String getString(Context context, int stringID) {
        return getResources(context).getString(stringID);
    }

    /**
     * 从String 中获得字符
     * @param context 上下文
     * @param strName 名称
     * @return 字符
     */

    public static String getString(Context context, String strName) {
        return getString(context, getResources(context).getIdentifier(strName, "string", context.getPackageName()));
    }

    /**
     * 根据 layout 名字获得 id
     * @param layoutName xml文件名
     * @return 获得相应id
     */
    public static int findLayout(Context context, String layoutName) {
        return getResources(context).getIdentifier(layoutName, "layout", context.getPackageName());
    }

    /**
     * 填充view
     * @param detailScreen 屏幕
     * @return view
     */
    public static View inflate(Context context, int detailScreen) {
        return View.inflate(context, detailScreen, null);
    }

    /**
     * 通过资源id获得drawable
     * @param rID 资源ID
     * @return Drawable
     */
    public static Drawable getDrawablebyResource(Context context, int rID) {

        return ContextCompat.getDrawable(context, rID);
    }

    /**
     * 获得屏幕的宽度
     * @param context 上下文
     * @return 宽度
     */
    public static int getScreenWidth(Context context) {
        return getResources(context).getDisplayMetrics().widthPixels;
    }

    /**
     * 获得屏幕的高度
     * @param context 上下文
     * @return 高度
     */
    public static int getScreenHeidth(Context context) {
        return getResources(context).getDisplayMetrics().heightPixels;
    }


    /**
     * 获得颜色
     */
    public static int getColor(Context context, int rid) {
        return ContextCompat.getColor(context, rid);
    }

    /**
     * 获得颜色
     */
    public static int getColor(Context context, String colorName) {
        return getColor(context, getResources(context).getIdentifier(colorName, "color", context.getPackageName()));
    }

    /**
     * 移除孩子
     * @param view 视图
     */
    public static void removeChild(View view) {
        ViewParent parent = view.getParent();
        if (parent instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) parent;
            group.removeView(view);
        }
    }

    public static boolean isEmpty(Object obj) {
        return obj == null;
    }



    /**
     * 全屏,并且沉侵式状态栏
     * @param activity 界面
     */
    public static void statuInScreen(Activity activity) {
        WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
        attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
        activity.getWindow().setAttributes(attrs);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    /**
     * 字符串英文小写转大写
     * @param text 源文字
     * @return 大写文字
     */
    public static String strToUpperCase(String text){
        String str = "";
        if (!TextUtils.isEmpty(text)){
            str = text.toUpperCase();
        }
        return str;
    }

    /**
     * 拨打电话
     * @param activity 当前Activity
     * @param telNumber 电话号码
     */
    public static void callPhone(Activity activity, String telNumber){
        //如果输入不为空创建打电话的Intent
        if(!TextUtils.isEmpty(telNumber) && telNumber.trim().length() != 0){
            Intent phoneIntent = new Intent("android.intent.action.CALL",
                    Uri.parse("tel:" + telNumber));
            try {
                //启动
                activity.startActivity(phoneIntent);
            } catch (Exception e) {
                ToastUtil.showToast(activity, activity.getResources().getString(R.string.common_toast_open_call_phone_op));
            }
        }else{
            //否则Toast提示一下
            ToastUtil.showToast(activity, activity.getString(R.string.common_error_phone_number));
        }
    }

    /**
     * 拨打电话
     * @param context 上下文
     * @param telNumber 电话号码
     */
    public static void callPhone(Context context, String telNumber){
        //如果输入不为空创建打电话的Intent
        if(!TextUtils.isEmpty(telNumber) && telNumber.trim().length() != 0){
            Intent phoneIntent = new Intent("android.intent.action.CALL",
                    Uri.parse("tel:" + telNumber));
            try {
                //启动
                context.startActivity(phoneIntent);
            } catch (Exception e) {
                ToastUtil.showToast(context, context.getResources().getString(R.string.common_toast_open_call_phone_op));
            }
        }else{
            //否则Toast提示一下
            ToastUtil.showToast(context, context.getString(R.string.common_error_phone_number));
        }
    }

    /**
     * 当前包名与YYK是否一致
     * @param context 上下文
     * @return true，一致在前台；false，在后台
     */
    public static boolean isCurrentPkgNameEquelMy(Context context){
        if (!TextUtils.isEmpty(getCurrentPkgName(context))){
            return getCurrentPkgName(context).equals(context.getPackageName());
        }
        return false;
    }

    /**
     * 查询当前进程名
     * @param context 上下文
     * @return 当前进程名
     */
    public static String getCurrentPkgName(Context context) {
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        String pkgName = null;
        if (Build.VERSION.SDK_INT >= 22) {
            ActivityManager.RunningAppProcessInfo currentInfo = null;
            Field field = null;
            int START_TASK_TO_FRONT = 2;
            try {
                field = ActivityManager.RunningAppProcessInfo.class
                        .getDeclaredField("processState");
            } catch (Exception e) {
                e.printStackTrace();
            }

            List<ActivityManager.RunningAppProcessInfo> appList = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo app : appList) {
                if (app.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    Integer state = null;
                    try {
                        state = field.getInt(app);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (state != null && state == START_TASK_TO_FRONT) {
                        currentInfo = app;
                        break;
                    }
                }
            }
            if (currentInfo != null) {
                pkgName = currentInfo.processName;
            }
        } else {
            @SuppressWarnings("deprecation")
            List<ActivityManager.RunningTaskInfo> runTaskInfos = am.getRunningTasks(1);
            // 拿到当前运行的任务栈
            ActivityManager.RunningTaskInfo runningTaskInfo = runTaskInfos
                    .get(0);
            // 拿到要运行的Activity的包名
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                pkgName = runningTaskInfo.baseActivity.getPackageName();
            }
        }

        return pkgName;
    }

    /**
     * 获得登记页面url(物料)
     * @param commonUrl		配置接口返回的Url
     * @param userAccount	客户账号
     * @param employeeNo	员工编号
     * @param employeeName	员工姓名
     * @return Url
     */
    public static String getSuppliesRegister(String commonUrl, String userAccount, String employeeNo, String employeeName
            , String orderId, String order_type, String cityNo){
        String trueName = "";
        try {
            if (!TextUtils.isEmpty(employeeName)){
                trueName = URLEncoder.encode(employeeName, "UTF-8");
                trueName = URLEncoder.encode(trueName, "UTF-8");
            }else {
                trueName = "";
            }
        } catch (Exception e) {
            KLog.d("转码错误");
        }
        String url = "";
        // 通用地址
        url = commonUrl + "?UserID=" + userAccount + "&MemberNo=" + employeeNo
                + "&TrueName=" + trueName + "&OrderID=" + orderId + "&OrderType=" + order_type + "&CityNo=" + cityNo;
        KLog.d("urlEncode:" + url);
        return url;
    }

    /**
     * 获得通用工单工作表单url
     * @param commonUrl 配置接口返回url
     * @param employeeNo 员工编号
     * @param employeeName 员工姓名
     * @param orderId 工单号
     * @return url
     */
    public static String getCommonOrderUrl(String commonUrl, String employeeNo, String employeeName, String orderId
            , String location, String order_type){
        String trueName = "";
        String trueLocation = "";
        try {
            if (!TextUtils.isEmpty(employeeName)){
                trueName = URLEncoder.encode(employeeName, "UTF-8");
                trueName = URLEncoder.encode(trueName, "UTF-8");
            }else {
                trueName = "";
            }

            if (!TextUtils.isEmpty(location)){
                trueLocation = URLEncoder.encode(employeeName, "UTF-8");
                trueLocation = URLEncoder.encode(trueName, "UTF-8");
            }else {
                trueLocation = "";
            }
        } catch (Exception e) {
            KLog.d("转码错误");
        }
        String url = "";
        // 通用地址
        url = commonUrl + "?MemberNo=" + employeeNo
                + "&TrueName=" + trueName + "&OrderID=" + orderId + "&Location=" + trueLocation + "&OrderType=" + order_type;
        KLog.d("urlEncode:" + url);
        return url;
    }

    /**
     * 是否有网络连接
     * @return 网络连接状态
     */
    public static boolean isNetConnected(Context context){
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {

                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {

                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    /**
     * 判断当前网络是否是wifi网络
     * if(activeNetInfo.getType()==ConnectivityManager.TYPE_MOBILE) { //判断3G网
     *
     * @param context 上下文
     * @return boolean
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null){
            NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI;
        }
        return false;
    }


    /**
     * 判断当前网络是否是3G网络
     *
     * @param context 上下文
     * @return boolean
     */
    public static boolean is3G(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null){
            NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetInfo != null
                    && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE;
        }
        return false;
    }

    public static boolean isSupportStepCountSensor(Context context) {
        // 获取传感器管理器的实例
        SensorManager sensorManager = (SensorManager) context
                .getSystemService(context.SENSOR_SERVICE);
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        Sensor detectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        return countSensor != null || detectorSensor != null;
    }

    /**
     * 实现文本复制功能
     * @param content 文本
     */
    public static void copyText(String content, Context context)
    {
        ClipboardManager clipboardManager = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.setPrimaryClip(ClipData.newPlainText(null, content));
        if (clipboardManager.hasPrimaryClip()){
            clipboardManager.getPrimaryClip().getItemAt(0).getText();
        }
    }

}
