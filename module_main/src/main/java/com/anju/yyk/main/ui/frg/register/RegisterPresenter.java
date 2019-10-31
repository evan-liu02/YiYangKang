package com.anju.yyk.main.ui.frg.register;


import com.anju.yyk.common.base.BaseApplication;
import com.anju.yyk.common.base.BaseResponse;
import com.anju.yyk.common.entity.response.CheckRoomListResponse;
import com.anju.yyk.main.di.component.DaggerMainComponent;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RegisterPresenter extends IRegisterContract.RegisterPresenter {
    @Override
    public void getCheckRoomList() {
        if (getView() == null)
            return;

        getView().showLoading(true);

        Observable<CheckRoomListResponse> observable = mModel.requestCheckRoomList();
        Disposable disposable = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    getView().hideLoading();
                    if (s.getStatus() == 0){
                        getView().checkRoomListSucc(s);
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
    public void checkRoomCommit(String hugong_id, String txtContent, boolean isCheck, String laoren_id, Map<String, String> map) {
        if (getView() == null)
            return;

        getView().showLoading(true);

        int checkStatus = -1;

        if (isCheck){
            checkStatus = 1;
        }else {
            checkStatus = 0;
        }

        Observable<BaseResponse> observable = mModel.requestCheckRoomCommit(hugong_id, txtContent, checkStatus, laoren_id, map);
        Disposable disposable = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    getView().hideLoading();
                    if (s.getStatus() == 0){
                        getView().checkRoomCommitSucc();
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
