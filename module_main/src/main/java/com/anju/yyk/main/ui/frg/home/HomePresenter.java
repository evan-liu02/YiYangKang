package com.anju.yyk.main.ui.frg.home;

import com.anju.yyk.common.base.BaseApplication;
import com.anju.yyk.common.base.BaseResponse;
import com.anju.yyk.common.entity.response.AttentionCountResponse;
import com.anju.yyk.common.entity.response.PersonListResponse;
import com.anju.yyk.main.di.component.DaggerMainComponent;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomePresenter extends IHomeContract.HomePresenter {

    @Override
    public void setUpPresenterComponent() {
        DaggerMainComponent.builder()
                .appComponent(BaseApplication.getInstance().getAppComponent())
                .build()
                .inject(this);
    }

    @Override
    public void getAttentionCount() {
        if (getView() == null){
            return;
        }

        getView().showLoading(true);

        Observable<AttentionCountResponse> observable = mModel.requestAttentionCount();
        Disposable disposable = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    if (s.getStatus() == 0){
                        getView().getAttentionCountSucc(s);
                    }else {
                        getView().hideLoading();
                        getView().refreshComplete();
                        getView().showToast(s.getTitle());
                    }
                }, throwable -> {
                    getView().hideLoading();
                    getView().refreshComplete();
                    getView().showToast(handleThrowableInP(throwable).message);
                });
        addDisposable(disposable);
    }

    @Override
    public void getPersonTop() {
        if (getView() == null){
            return;
        }

        Observable<PersonListResponse> observable = mModel.requestPersonTop();
        Disposable disposable = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    if (s.getStatus() == 0){
                        List<PersonListResponse.ListBean> list = s.getList();
                        for (PersonListResponse.ListBean bean : list)
                            bean.setIstop(1);
                        getView().getPersonTop(list);
                    }else {
                        getView().hideLoading();
                        getView().refreshComplete();
//                        getView().showToast(s.getTitle());
                        getView().noTop();
                    }
                }, throwable -> {
                    getView().hideLoading();
                    getView().refreshComplete();
                    getView().showToast(handleThrowableInP(throwable).message);
                });
        addDisposable(disposable);
    }

    @Override
    public void getPersonList() {
        if (getView() == null){
            return;
        }

        Observable<PersonListResponse> observable = mModel.requestPersonList();
        Disposable disposable = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    getView().hideLoading();
                    if (s.getStatus() == 0){
                        List<PersonListResponse.ListBean> list = s.getList();
                        for (PersonListResponse.ListBean bean : list)
                            bean.setIstop(0);
                        getView().getPersonListSucc(list);
                    }else {
                        getView().hideLoading();
                        getView().refreshComplete();
                        getView().showToast(s.getTitle());
                    }
                }, throwable -> {
                    getView().hideLoading();
                    getView().refreshComplete();
                    getView().showToast(handleThrowableInP(throwable).message);
                });
        addDisposable(disposable);
    }

    @Override
    public void changeTopStatus(String personId, int status) {
        if (getView() == null){
            return;
        }

        getView().showLoading(true);

        Observable<BaseResponse> observable = mModel.requestChangeTopStatus(personId, status);
        Disposable disposable = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    getView().hideLoading();
                    if (s.getStatus() == 0){
                        getView().changeTopStatusSucc();
                    }else {
                        getView().hideLoading();
                        getView().refreshComplete();
                        getView().showToast(s.getTitle());
                    }
                }, throwable -> {
                    getView().hideLoading();
                    getView().refreshComplete();
                    getView().showToast(handleThrowableInP(throwable).message);
                });
        addDisposable(disposable);
    }
}
