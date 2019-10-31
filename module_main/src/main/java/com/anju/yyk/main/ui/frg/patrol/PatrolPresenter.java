package com.anju.yyk.main.ui.frg.patrol;

import com.anju.yyk.common.base.BaseApplication;
import com.anju.yyk.common.entity.response.AccidentDetailResponse;
import com.anju.yyk.common.entity.response.PatrolResponse;
import com.anju.yyk.main.di.component.DaggerMainComponent;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PatrolPresenter extends IPatrolContract.PatrolPresenter{
    @Override
    public void getPatrol() {
        if (getView() == null){
            return;
        }
        getView().showLoading(true);

        Observable<PatrolResponse> observable = mModel.requestPatrol();
        Disposable disposable = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    getView().hideLoading();
                    if (s.getStatus() == 0){
                        getView().patrolSucc(s);
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
