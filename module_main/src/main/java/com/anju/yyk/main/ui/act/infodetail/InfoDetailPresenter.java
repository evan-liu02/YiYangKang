package com.anju.yyk.main.ui.act.infodetail;

import com.anju.yyk.common.app.sp.AppSP;
import com.anju.yyk.common.base.BaseApplication;
import com.anju.yyk.common.entity.response.DiseaseResponse;
import com.anju.yyk.common.entity.response.HealthResposne;
import com.anju.yyk.common.entity.response.JisShuResponse;
import com.anju.yyk.common.entity.response.OperationResponse;
import com.anju.yyk.common.utils.klog.KLog;
import com.anju.yyk.main.di.component.DaggerMainComponent;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class InfoDetailPresenter extends IInfoDetailContract.IInfoDetailPresenter{

    @Inject
    AppSP mAppSP;

    @Override
    public void getJisShuInfo(String id) {
        if (getView() == null){
            return;
        }
        getView().showLoading(true);

        Observable<JisShuResponse> observable = mModel.requestJisShu(id);
        Disposable disposable = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    KLog.d("返回-------->" + s);
                    if (s.getStatus() == 0){
                        getView().jisShuInfoSucc(s);
                    }else {
                        getView().jisShuInfoSucc(s);
                        getView().showToast(s.getTitle());
                    }
                }, throwable -> {
                    getView().hideLoading();
                    getView().showToast(handleThrowableInP(throwable).message);
                });
        addDisposable(disposable);
    }

    @Override
    public void getHealthInfo(String id) {
        if (getView() == null){
            return;
        }

        Observable<HealthResposne> observable = mModel.requestHealthInfo(id);
        Disposable disposable = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    KLog.d("返回-------->" + s);
                    if (s.getStatus() == 0){
                        getView().healthInfoSucc(s);
                    }else {
                        getView().healthInfoSucc(s);
                        getView().showToast(s.getTitle());
                    }
                }, throwable -> {
                    getView().hideLoading();
                    getView().showToast(handleThrowableInP(throwable).message);
                });
        addDisposable(disposable);
    }

    @Override
    public void getDiseaseInfo(String id) {
        if (getView() == null){
            return;
        }

        Observable<DiseaseResponse> observable = mModel.requestDiseaseInfo(id);
        Disposable disposable = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    KLog.d("返回-------->" + s);
                    if (s.getStatus() == 0){
                        getView().diseaseInfoSucc(s);
                    }else {
                        getView().diseaseInfoSucc(s);
                        getView().showToast(s.getTitle());
                    }
                }, throwable -> {
                    getView().hideLoading();
                    getView().showToast(handleThrowableInP(throwable).message);
                });
        addDisposable(disposable);
    }

    @Override
    public void getOperationInfo(String id) {
        if (getView() == null){
            return;
        }

        Observable<OperationResponse> observable = mModel.requestOperationInfo(id);
        Disposable disposable = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    KLog.d("返回-------->" + s);
                    getView().hideLoading();
                    if (s.getStatus() == 0){
                        getView().operationInfoSucc(s);
                    }else {
                        getView().operationInfoSucc(s);
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
