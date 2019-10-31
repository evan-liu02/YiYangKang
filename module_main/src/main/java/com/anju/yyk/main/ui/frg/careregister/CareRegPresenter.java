package com.anju.yyk.main.ui.frg.careregister;

import com.anju.yyk.common.base.BaseApplication;
import com.anju.yyk.common.base.BaseResponse;
import com.anju.yyk.common.entity.response.HuLiXiangmu0Response;
import com.anju.yyk.common.entity.response.HuLiXiangmu1Response;
import com.anju.yyk.main.di.component.DaggerMainComponent;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CareRegPresenter extends ICareRegContract.ICareRegPresenter{

    @Override
    public void huli0(String id) {
        if (getView() == null){
            return;
        }
        getView().showLoading(true);

        Observable<HuLiXiangmu0Response> observable = mModel.requestHuli0(id);
        Disposable disposable = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    getView().hideLoading();
                    if (s.getStatus() == 0){
                        getView().getHuli0Succ(s);
                    }else {
                        getView().hideLoading();
                        getView().showToast(s.getTitle());
                    }
                }, throwable -> {
                    getView().hideLoading();
                    getView().showToast(handleThrowableInP(throwable).message);
                });
        addDisposable(disposable);
    }

    @Override
    public void huli1(String id) {
        if (getView() == null){
            return;
        }

        Observable<HuLiXiangmu1Response> observable = mModel.requestHuli1(id);
        Disposable disposable = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    getView().hideLoading();
                    if (s.getStatus() == 0){
                        getView().getHuli1Succ(s);
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
    public void commitHuli(String id, String jifen, String title, String laoren_id, String hugong_id) {
        if (getView() == null){
            return;
        }
        getView().showLoading(true);

        Observable<BaseResponse> observable = mModel.requestComplete(id, jifen, title, laoren_id, hugong_id);
        Disposable disposable = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    getView().hideLoading();
                    if (s.getStatus() == 0){
                        getView().commitHuliSucc(s);
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
