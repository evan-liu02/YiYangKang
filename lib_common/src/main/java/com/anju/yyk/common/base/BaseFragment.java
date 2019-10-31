package com.anju.yyk.common.base;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.anju.yyk.common.R;
import com.anju.yyk.common.utils.ToastUtil;
import com.anju.yyk.common.utils.eventbus.Event;
import com.anju.yyk.common.utils.eventbus.EventBusUtil;
import com.anju.yyk.common.utils.klog.KLog;
import com.anju.yyk.common.widget.sweetalert.SweetAlertDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 
 * @author LeoWang
 * 
 * @Package com.leo.common.base
 * 
 * @Description fargment基类，简单页面继承此类
 * 
 * @Date 2019/5/6 11:09
 * 
 * @modify:
 */
public abstract class BaseFragment extends Fragment implements IBaseView{
    private SweetAlertDialog mProgressDialog;
    protected Activity mActivity;
    private Unbinder mUnBinder;

    /** 是否是第一次加载*/
    private boolean isFirstEnter = true;

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        onAttachToContext(context);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            onAttachToContext(activity);
        }
    }

    private void onAttachToContext(Context context) {
        mActivity = getActivity();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null){
            if (isRegisterEventBus()){
                EventBusUtil.register(this);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        if (null != inflater){
            view = inflater.inflate(getLayoutId(), container, false);
            mUnBinder = ButterKnife.bind(this, view);

            setupFragmentComponent();

            init();
            initListener();
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        KLog.d(getClass().getSimpleName() + "------>onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        KLog.d(getClass().getSimpleName() + "------>onResume()");
        if (isFirstEnter){
            initData();
            isFirstEnter = false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // UnBinder防止内存泄露
        if (mUnBinder != null && mUnBinder != Unbinder.EMPTY){
            mUnBinder.unbind();
        }
        this.mUnBinder = null;

        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }

        if (isRegisterEventBus()) {
            EventBusUtil.unregister(this);
        }
    }

    @Override
    public void showToast(String msg) {
        mActivity.runOnUiThread(() -> ToastUtil.showToast(getActivity(), msg));
    }

    @Override
    public void showToast(int strId) {
        mActivity.runOnUiThread(() -> ToastUtil.showToast(getActivity(), getString(strId)));
    }

    @Override
    public void showLoading(boolean isCancel) {
        if (mProgressDialog == null){
            mProgressDialog = new SweetAlertDialog(mActivity, SweetAlertDialog.PROGRESS_TYPE);
        }

        if (mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }

        mProgressDialog.setTitleText(getString(R.string.common_loading));
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
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
        // TODO 处理服务器通用错误
    }

    @Override
    public void showEmptyView() {

    }

    /**
     * 布局id
     * @return xml
     */
    public abstract int getLayoutId();

    /**
     * 初始化变量
     */
    public abstract void init();

    /**
     * 初始化监听，此方法在onCreate()中
     */
    public abstract void initListener();

    /**
     * 初始化数据，onCreate()中init()方法初始化变量完毕后，在onStart中进行数据初始化，联网请求
     */
    protected abstract void initData();

    /**
     * 提供单例给实现类
     */
    protected abstract void setupFragmentComponent();

    /**
     * 设置是否需要注册EventBus
     * @return 是否
     */
    protected abstract boolean isRegisterEventBus();

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusCome(Event event){
        if (event != null){
            // TODO 公共事务
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onStickyEventBusCome(Event event) {
        if (event != null) {
            receiveStickyEvent(event);
        }
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


}
