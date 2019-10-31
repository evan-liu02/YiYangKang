package com.anju.yyk.common.http;

import android.content.Context;

import com.anju.yyk.common.app.Constants;
import com.anju.yyk.common.app.sp.AppSP;

import java.util.concurrent.TimeUnit;

import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 * @author LeoWang
 *
 * @Package cn.com.drpeng.runman.common.http
 *
 * @Description 网络框架管理
 *
 * @Date 2019/5/29 16:21
 *
 * @modify:
 */
public class NetManager {

    private OkHttpClient mOkHttpClient;

    private Retrofit mRetrofit;

    private ApiService mApiService;

    private AppSP appSP;

    public NetManager(Context context){
        appSP = new AppSP(context);
        initOkHttpClient();
        initRetrofit();
        initApiService();
        RetrofitUrlManager.getInstance().setDebug(true);
    }

    private void initOkHttpClient(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLogger());
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        mOkHttpClient = RetrofitUrlManager.getInstance()
                .with(new OkHttpClient.Builder())
                .readTimeout(Constants.READ_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(Constants.CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .addNetworkInterceptor(interceptor)
                .build();
    }

    private void initRetrofit(){
        mRetrofit = new Retrofit.Builder()
                .baseUrl(ApiAddr.BASE_URL)
                .client(mOkHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private void initApiService(){
        mApiService = mRetrofit.create(ApiService.class);
    }

    public ApiService getApiService(){
        return mApiService;
    }

    public ExceptionHandle.ResponeThrowable handleThrowable(Throwable throwable){
        if (throwable instanceof Exception){
            return ExceptionHandle.handleException(throwable);
        }else {
            return new ExceptionHandle.ResponeThrowable(throwable, ExceptionHandle.ERROR.UNKNOWN);
        }
    }
}
