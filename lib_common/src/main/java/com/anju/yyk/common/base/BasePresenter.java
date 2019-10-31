package com.anju.yyk.common.base;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.anju.yyk.common.http.ExceptionHandle;
import com.anju.yyk.common.http.NetManager;
import com.anju.yyk.common.impl.IPresenter;
import com.anju.yyk.common.utils.klog.KLog;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 *
 * @author wcs
 *
 * @Package com.leo.common.base
 *
 * @Description 父类注入实例仅声明，需在子类中实现注入方法，避免Injector出现重复导致无法打包
 *
 * @Date 2019/4/26 11:09
 *
 * @modify:
 */
public abstract class BasePresenter<M, V> implements IPresenter {
    protected M mModel;
    private WeakReference<V> mWeakRef;
    private CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    protected NetManager mNetManager;

    public BasePresenter(){
        setUpPresenterComponent();
    }

    void onAttach(M model, V view){
        mModel = model;
        mWeakRef = new WeakReference<>(view);
    }

    private void onDetach(){
        if (null != mWeakRef){
            mWeakRef.clear();
            mWeakRef = null;
            KLog.d("BasePresenter.onDetach-----------------" + this.getClass().toString());
        }
    }

    private boolean isViewAttached(){
        return null != mWeakRef && null != mWeakRef.get();
    }

    protected V getView(){
        return isViewAttached() ? mWeakRef.get() : null;
    }

    protected void addDisposable(Disposable dis){
        disposable.add(dis);
    }

    private void dispose(){
        if (disposable != null){
            disposable.clear();
            KLog.d("BasePresenter.dispose-----------" + this.getClass().toString());
        }
    }

    @Override
    public void onCreate(LifecycleOwner owner) {
        KLog.d("BasePresenter.onCreate" + this.getClass().toString());
    }

    @Override
    public void onStart(LifecycleOwner owner) {
        KLog.d("BasePresenter.onStart" + this.getClass().toString());
    }

    @Override
    public void onResume(LifecycleOwner owner) {
        KLog.d("BasePresenter.onResume" + this.getClass().toString());
    }

    @Override
    public void onPause(LifecycleOwner owner) {
        KLog.d("BasePresenter.onPause" + this.getClass().toString());
    }

    @Override
    public void onStop(LifecycleOwner owner) {
        KLog.d("BasePresenter.onStop" + this.getClass().toString());
    }

    @Override
    public void onDestroy(LifecycleOwner owner) {
        KLog.d("BasePresenter.onDestroy" + this.getClass().toString());
        onDetach();
        dispose();
    }

    @Override
    public void onLifecycleChanged(LifecycleOwner owner, Lifecycle.Event event) {

    }

    public abstract void setUpPresenterComponent();

    /**
     * Presenter中处理网络错误，自动切换Url
     * @param throwable 错误
     * @return 封装后的网络错误信息类
     */
    public ExceptionHandle.ResponeThrowable handleThrowableInP(Throwable throwable){
        ExceptionHandle.ResponeThrowable e = null;
        if (mNetManager != null){
            e = mNetManager.handleThrowable(throwable);
        }else {
            KLog.d("BasePresenter mNetManager is null !!!");
        }
        return e;
    }

}
