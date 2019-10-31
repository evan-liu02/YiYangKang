package com.anju.yyk;

import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.anju.yyk.common.app.WebUrlConstant;
import com.anju.yyk.common.app.arouter.RouterConstants;
import com.anju.yyk.common.app.arouter.RouterKey;
import com.anju.yyk.common.app.sp.AppSP;
import com.anju.yyk.common.base.BaseActivity;
import com.anju.yyk.common.base.BaseApplication;
import com.anju.yyk.common.utils.AppUtil;
import com.anju.yyk.common.utils.klog.KLog;
import com.anju.yyk.di.component.DaggerYYKComponent;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@Route(path = RouterConstants.ACT_URL_LOADING)
public class LoadingAct extends BaseActivity {

    private Disposable disposable;

    @Inject
    AppSP mAppSP;

    @Override
    protected int getLayoutId() {
        return R.layout.act_loading;
    }

    @Override
    protected void init() {
        hideToolbar();
        AppUtil.statuInScreen(this);

        disposable = Flowable.intervalRange(0, 3 + 1, 0, 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(aLong -> {
                    KLog.d("第" + String.valueOf(aLong) + "秒");
                })
                .doOnComplete(() ->{
                    if (TextUtils.isEmpty(mAppSP.getUserId())){
                        ARouter.getInstance().build(RouterConstants.ACT_URL_LOGIN)
                                .navigation();
                    }else {
                        ARouter.getInstance().build(RouterConstants.ACT_URL_HOME_PAGE)
                                .navigation();
                    }
                    finish();
                }).subscribe();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    protected void setupActivityComponent() {
        DaggerYYKComponent.builder()
                .appComponent(BaseApplication.getInstance().getAppComponent())
                .build()
                .inject(this);
    }

    @Override
    public boolean isRegisterEventBus() {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null){
            disposable.dispose();
        }
    }
}
