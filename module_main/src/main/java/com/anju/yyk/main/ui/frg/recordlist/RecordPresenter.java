package com.anju.yyk.main.ui.frg.recordlist;

import com.anju.yyk.common.base.BaseApplication;
import com.anju.yyk.common.entity.response.RecordResponse;
import com.anju.yyk.main.di.component.DaggerMainComponent;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RecordPresenter extends IRecordListContract.RecordListPresenter {
    @Override
    public void getCareRecord(String id, String startTime, String endTime) {
        if (getView() == null){
            return;
        }
        getView().showLoading(true);

        Observable<RecordResponse> observable = mModel.requestCareRecordList(id, startTime, endTime);
        Disposable disposable = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    getView().hideLoading();
                    if (s.getStatus() == 0){
                        getView().getCareRecordSucc(s);
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
    public void getCheckRoom(String id, String startTime, String endTime) {
        if (getView() == null){
            return;
        }
        getView().showLoading(true);

        Observable<RecordResponse> observable = mModel.requestCheckRoomRecordList(id, startTime, endTime);
        Disposable disposable = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    getView().hideLoading();
                    if (s.getStatus() == 0){
                        getView().getCheckRoomSucc(s);
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
    public void getAccident(String id, String startTime, String endTime) {
        if (getView() == null){
            return;
        }
        getView().showLoading(true);

        Observable<RecordResponse> observable = mModel.requestAccidentRecordList(id, startTime, endTime);
        Disposable disposable = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    getView().hideLoading();
                    if (s.getStatus() == 0){
                        getView().getaccidentSucc(s);
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
