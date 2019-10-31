package com.anju.yyk.common.app;

import android.os.Environment;

public class Constants {
    /** 数据库名称*/
    public static final String DB_NAME = "YYK";
    /** 读取超时*/
    public static final int READ_TIMEOUT = 20;
    /** 连接超时*/
    public static final int CONNECT_TIMEOUT = 60;
    /** 音频保存文件夹*/
    public static final String AUDIO_DIR = "/yyk_audio";
    /** 照片保存文件夹*/
    public static final String PHOTO_DIR = "/yyk_photo";

    public static final String AUDIO_HEAD = "http://";

    /** 照片保存路径*/
    public static final String SAVE_DIR = Environment.getExternalStorageDirectory().getPath() + PHOTO_DIR;
}
