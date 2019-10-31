package com.anju.yyk.common.base;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.anju.yyk.common.R;
import com.anju.yyk.common.app.ActManager;
import com.anju.yyk.common.impl.ToolbarListener;
import com.anju.yyk.common.utils.ScreenUtil;
import com.anju.yyk.common.utils.ToastUtil;
import com.anju.yyk.common.utils.eventbus.Event;
import com.anju.yyk.common.utils.eventbus.EventBusUtil;
import com.anju.yyk.common.widget.sweetalert.SweetAlertDialog;
import com.anju.yyk.common.widget.toolbar.NormalToolbar;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 
 * @author wcs
 * 
 * @Package com.leo.common.base
 * 
 * @Description 非MVP模式Activity基类，页面交互简单继承此类
 * 
 * @Date 2019/4/26 16:22
 * 
 * @modify:
 */
public abstract class BaseActivity extends AppCompatActivity implements IBaseView, ToolbarListener {
    private SweetAlertDialog mProgressDialog;
    public Activity mActivity;
    protected ActManager actManager = ActManager.getActManager();
    private Unbinder mUnBinder;

    /** 是否是第一次进入*/
    private boolean isFirstEnter = true;

    protected NormalToolbar mToolbar;
    /** 子类视图容器**/
    private FrameLayout mContainerFl;

    protected View mContainerView = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.common_base_act);
            initBaseViews();
            if (getLayoutId() != 0){
                LayoutInflater inflater = LayoutInflater.from(this);
                mContainerView = inflater.inflate(getLayoutId(), null);
                mUnBinder = ButterKnife.bind(this, mContainerView);
                mContainerFl.removeAllViews();
                mContainerFl.addView(mContainerView);
            }
        }catch (Exception e){
            if (e instanceof InflateException) throw e;
            e.printStackTrace();
        }
        mActivity = this;
        actManager.addActivity(mActivity);

        setupActivityComponent();

        if (isRegisterEventBus()){
            EventBusUtil.register(this);
        }

        init();
        initListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isFirstEnter){
            initData();
            isFirstEnter = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        actManager.removeActivity(mActivity);
        if (mUnBinder != null && mUnBinder != Unbinder.EMPTY)
            mUnBinder.unbind();
        this.mUnBinder = null;

        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
        if (isRegisterEventBus()) {
            EventBusUtil.unregister(this);
        }
    }

    /**
     * 初始化toolbar，为了避免未知坑，此处不用黄油刀，有时候没必要非得踩坑
     */
    private void initBaseViews(){
        mToolbar = findViewById(R.id.common_toolbar);
        mToolbar.setToolbarListener(this);
        mContainerFl = findViewById(R.id.common_fl_container);
    }

    @Override
    public void showToast(String msg) {
        runOnUiThread(() -> ToastUtil.showToast(BaseActivity.this, msg));
    }

    @Override
    public void showToast(int strId) {
        runOnUiThread(() -> ToastUtil.showToast(BaseActivity.this, getString(strId)));
    }

    @Override
    public void showLoading(boolean isCancel) {
        if (!isFinishing()){
            if (mProgressDialog == null){
                mProgressDialog = new SweetAlertDialog(mActivity, SweetAlertDialog.PROGRESS_TYPE);
            }

            if (mProgressDialog.isShowing()){
                mProgressDialog.dismiss();
            }

            mProgressDialog.setTitleText(getString(R.string.common_loading));
            mProgressDialog.setCancelable(isCancel);
            mProgressDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void serverTipError(BaseResponse response) {
        showToast(response.getTitle());
    }

    @Override
    public void showEmptyView() {

    }

    /**
     * 布局id
     * @return 布局id
     */
    protected abstract int getLayoutId();

    /**
     * 初始化变量及页面，此方法在onCreate()中
     */
    protected abstract void init();

    /**
     * 初始化监听，此方法在onCreate()中
     */
    public abstract void initListener();

    /**
     * 初始化数据，onCreate()中init()方法初始化变量完毕后，在onStart中进行数据初始化，联网请求
     */
    public abstract void initData();

    /**
     * 提供单例给实现类
     */
    protected abstract void setupActivityComponent();

    /**
     * 设置是否需要注册EventBus
     * @return 是否注册
     */
    public abstract boolean isRegisterEventBus();

    /**
     * 隐藏标题栏
     */
    public void hideToolbar(){
        mToolbar.hide();
    }

    /**
     * 设置左右按钮文字
     * @param leftStr 左文字
     * @param rightStr 右文字
     */
    public void setToolbarText(String leftStr, String rightStr){
        mToolbar.setLeftText(leftStr);
        mToolbar.setRightText(rightStr);
    }

    /**
     * 设置标题文字
     * @param topicStr 标题
     */
    public void setToolbarTopic(String topicStr){
        mToolbar.setTopic(topicStr);
    }

    /**
     * 设置标题文字
     * @param topicStrId 文字资源ID
     */
    public void setToolbarTopic(int topicStrId){
        mToolbar.setTopic(getString(topicStrId));
    }

    @Override
    public void leftTvListener() {
        finish();
    }

    @Override
    public void rightTvListener() {
        finish();
    }

    @Override
    public void leftIvListener() {
        finish();
    }

    @Override
    public void rightIvListener() {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusCome(Event event){
        if (event != null){
            switch (event.getCode()){
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onStickyEventBusCome(Event event) {
    }

    /**
     * 接受到分发事件
     * @param event 事件
     */
    protected void receiveEvent(Event event){
    }

    /**
     * 接受到分发的粘性事件
     *
     * @param event 粘性事件
     */
    protected void receiveStickyEvent(Event event) {

    }

    /**
     * 设置状态栏颜色
     * @param color 颜色 R.color.xxx
     * @param isOverParent 是否覆盖在布局上
     */
    protected void setStatusBarColor(int color, boolean isOverParent){
        if (isOverParent){
            // 固定状态栏:状态栏覆盖在app布局上面
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);////设置固定状态栏常驻，覆盖app布局
                getWindow().setStatusBarColor(ContextCompat.getColor(this, color));//设置状态栏颜色
            }
        }else {
            // 固定状态栏:状态栏在app布局上面，不覆盖app布局
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);//设置固定状态栏常驻，不覆盖app布局
                getWindow().setStatusBarColor(ContextCompat.getColor(this, color));//设置状态栏颜色
            }
        }
    }

    /**
     * 是否显示状态栏
     * @param enable 是否
     */
    protected void hideStausbar(boolean enable) {
        if (enable) {
            WindowManager.LayoutParams attrs = getWindow().getAttributes();
            attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(attrs);
        } else {
            WindowManager.LayoutParams attrs = getWindow().getAttributes();
            attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(attrs);
        }
    }
}
