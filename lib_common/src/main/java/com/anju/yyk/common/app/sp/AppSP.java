package com.anju.yyk.common.app.sp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class AppSP {
    /** 文件存储名字*/
    private final String SP_NAME = "yyk_app_sp";
    /** 文件存储*/
    private SharedPreferences mPreferences;
    /** 修改存储编辑器*/
    private SharedPreferences.Editor mEditor;

    public AppSP(Context context){
        mPreferences = context.getSharedPreferences(SP_NAME, Activity.MODE_PRIVATE);
        mEditor = mPreferences.edit();
        mEditor.apply();
    }

    public void setToken(String token){
        mEditor.putString("token", token);
        mEditor.commit();
    }

    public String getToken(){
        return mPreferences.getString("token", "");
    }

    public void setUserId(String userId){
        mEditor.putString("userId", userId);
        mEditor.commit();
    }

    public String getUserId(){
        return mPreferences.getString("userId", "");
    }

    public void clearSP(){
        if (mEditor != null){
            mEditor.clear();
            mEditor.commit();
        }
    }
}
