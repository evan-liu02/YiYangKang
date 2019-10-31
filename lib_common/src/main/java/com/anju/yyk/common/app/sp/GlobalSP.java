package com.anju.yyk.common.app.sp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class GlobalSP {
    /** 文件存储名字*/
    private final String SP_NAME = "yyk_global_sp";
    /** 文件存储*/
    private SharedPreferences mPreferences;
    /** 修改存储编辑器*/
    private SharedPreferences.Editor mEditor;

    public GlobalSP(Context context){
        mPreferences = context.getSharedPreferences(SP_NAME, Activity.MODE_PRIVATE);
        mEditor = mPreferences.edit();
        mEditor.apply();
    }

    public void setAccount(String account){
        mEditor.putString("account", account);
        mEditor.commit();
    }

    /**
     * 获得账号
     * @return 账号
     */
    public String getAccount(){
        return mPreferences.getString("account", "");
    }

    /**
     * 保存密码
     * @param pwd 密码
     */
    public void savePwd(String pwd){
        mEditor.putString("pwd", pwd);
        mEditor.commit();
    }

    /**
     * 获取密码
     * @return 密码
     */
    public String getPwd(){
        return mPreferences.getString("pwd", "");
    }

    /**
     * 保存是否记住密码状态
     * @param isRememberPwd 是否记住密码
     */
    public void saveIsRememberPwd(boolean isRememberPwd){
        mEditor.putBoolean("isRememberPwd", isRememberPwd);
        mEditor.commit();
    }

    /**
     * 获取是否记住密码标识
     * @return 是否记住密码
     */
    public boolean isRememberPwd(){
        return mPreferences.getBoolean("isRememberPwd", false);
    }

    public void clearSP(){
        if (mEditor != null){
            mEditor.clear();
            mEditor.commit();
        }
    }
}
