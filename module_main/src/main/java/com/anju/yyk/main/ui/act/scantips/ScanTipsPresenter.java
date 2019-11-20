package com.anju.yyk.main.ui.act.scantips;

import com.anju.yyk.common.base.BaseApplication;
import com.anju.yyk.common.base.BaseResponse;
import com.anju.yyk.common.entity.response.NewTipsListResponse;
import com.anju.yyk.common.utils.klog.KLog;
import com.anju.yyk.main.di.component.DaggerMainComponent;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ScanTipsPresenter extends IScanTipsContract.ScanTipsPresenter{
    @Override
    public void loadTips() {
        if (getView() == null){
            return;
        }

        getView().showLoading(false);

        Observable<NewTipsListResponse> observable = mModel.requestTips();
        Disposable disposable = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    getView().hideLoading();
                    KLog.d("返回-------->" + s.getList().size());
                    if (s.getStatus() == 0){
                        getView().getTipsSucc(s.getList());
                    }else {
                        getView().showToast(s.getTitle());
                    }
                }, throwable -> {
                    getView().hideLoading();
                    getView().showToast(handleThrowableInP(throwable).message);
                });
        addDisposable(disposable);
    }

    @Override
    public void loadPersonalTips(String laoren_id) {
        if (getView() == null){
            return;
        }

        getView().showLoading(false);

        Observable<NewTipsListResponse> observable = mModel.requestPersonalTips(laoren_id);
        Disposable disposable = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    getView().hideLoading();
//                    KLog.d("返回-------->" + s.getList().size());
                    if (s.getStatus() == 0){
                        getView().getPersonalTipsSucc(s.getList());
                    }else {
                        getView().showToast(s.getTitle());
                    }
                }, throwable -> {
                    getView().hideLoading();
                    getView().showToast(handleThrowableInP(throwable).message);
                });
        addDisposable(disposable);
    }

    @Override
    public void downAudio(String audioUrl) {
        if (getView() == null){
            return;
        }
    }

    @Override
    public void read(String zhuyi_id) {
        if (getView() == null){
            return;
        }

        getView().showLoading(false);

        Observable<BaseResponse> observable = mModel.requestReaded(zhuyi_id);
        Disposable disposable = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    getView().hideLoading();
                    if (s.getStatus() == 0){
                        getView().readedSucc();
                    }else {
                        getView().showToast(s.getTitle());
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
