package com.anju.yyk.main.ui.act.login;

import android.text.TextUtils;

import com.anju.yyk.common.app.sp.AppSP;
import com.anju.yyk.common.base.BaseApplication;
import com.anju.yyk.common.entity.response.LoginResponse;
import com.anju.yyk.common.utils.klog.KLog;
import com.anju.yyk.main.R;
import com.anju.yyk.main.di.component.DaggerMainComponent;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter extends ILoginContract.LoginPresenter{

    @Inject
    AppSP mAppSP;

    @Override
    public void login(String account, String password) {
        if (getView() == null){
            return;
        }
        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)){
            getView().showToast(R.string.common_toast_input_none);
            return;
        }

        getView().showLoading(false);

        Observable<LoginResponse> observable = mModel.requestLogin(account, password);
        Disposable disposable = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    getView().hideLoading();
                    KLog.d("返回-------->" + s);
                    if (s.getStatus() == 0){
                        getView().showToast("登录成功");
                        mAppSP.setUserId(s.getUser_id());
                        getView().loginSucc();
                    }else {
//                        getView().showToast(s.getTitle());
                        getView().loginFailed();
                    }
                }, throwable -> {
                    getView().hideLoading();
                    getView().showToast(handleThrowableInP(throwable).message);
                });
        addDisposable(disposable);
    }

    @Override
    public void setUpPresenterComponent() {
        DaggerMainComponent.builder()
                .appComponent(BaseApplication.getInstance().getAppComponent())
                .build()
                .inject(this);
    }
}
