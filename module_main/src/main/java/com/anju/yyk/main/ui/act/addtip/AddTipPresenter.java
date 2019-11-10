package com.anju.yyk.main.ui.act.addtip;

import android.text.TextUtils;

import com.anju.yyk.common.base.BaseApplication;
import com.anju.yyk.common.base.BaseResponse;
import com.anju.yyk.common.entity.response.UploadFileResponse;
import com.anju.yyk.common.utils.klog.KLog;
import com.anju.yyk.main.di.component.DaggerMainComponent;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddTipPresenter extends IAddTipContract.AddTipPresenter {
    @Override
    public void addTip(String id, String userId, String content, String filePath) {
        if (getView() == null)
            return;

        getView().showLoading(false);
        /*RequestBody des = RequestBody.create(content, MediaType.parse("text/plain"));

        File file = new File(filePath);
        RequestBody requestFile = RequestBody.create(file, MediaType.parse("multipart/form-data"));
        MultipartBody.Part body = MultipartBody.Part.createFormData("aFile", file.getName(), requestFile);*/
        if (TextUtils.isEmpty(filePath)) {
            Observable<BaseResponse> tipObservable = mModel.addTip(id, userId, content, "");
            Disposable tipDisposable = tipObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(ts -> {
                        getView().hideLoading();
                        KLog.d("添加提醒成功");
                        if (ts.getStatus() == 0) {
                            getView().uploadSucc(ts);
                        } else {
                            getView().showToast(ts.getTitle());
                        }
                    }, throwable -> {
                        getView().hideLoading();
                        getView().showToast(handleThrowableInP(throwable).message);
                    });
            addDisposable(tipDisposable);
        } else {
            Observable<UploadFileResponse> observable = mModel.uploadAudio(filePath);
            Disposable disposable = observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(s -> {
                        KLog.d("上传音频成功");
                        if (s.getStatus() == 1) {
                            Observable<BaseResponse> tipObservable = mModel.addTip(id, userId, content, s.getPath());
                            Disposable tipDisposable = tipObservable.subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(ts -> {
                                        getView().hideLoading();
                                        KLog.d("添加提醒成功");
                                        if (ts.getStatus() == 0) {
                                            getView().uploadSucc(ts);
                                        } else {
                                            getView().showToast(ts.getTitle());
                                        }
                                    }, throwable -> {
                                        getView().hideLoading();
                                        getView().showToast(handleThrowableInP(throwable).message);
                                    });
                            addDisposable(tipDisposable);
                        } else {
                            getView().hideLoading();
                            getView().showToast(s.getMsg());
                        }
                    }, throwable -> {
                        getView().hideLoading();
                        getView().showToast(handleThrowableInP(throwable).message);
                    });
            addDisposable(disposable);
        }
    }

    @Override
    public void setUpPresenterComponent() {
        DaggerMainComponent.builder()
                .appComponent(BaseApplication.getInstance().getAppComponent())
                .build()
                .inject(this);
    }
}
