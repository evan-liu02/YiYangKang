package com.anju.yyk.main.ui.act.addtip;

import com.anju.yyk.common.base.BaseApplication;
import com.anju.yyk.common.base.BaseResponse;
import com.anju.yyk.common.entity.response.JisShuResponse;
import com.anju.yyk.common.utils.klog.KLog;
import com.anju.yyk.main.di.component.DaggerMainComponent;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AddTipPresenter extends IAddTipContract.AddTipPresenter{
    @Override
    public void uploadAudio(String content, String filePath) {
        if (getView() == null)
            return;

        getView().showLoading(false);
        RequestBody des = RequestBody.create(content, MediaType.parse("text/plain"));

        File file = new File(filePath);
        RequestBody requestFile = RequestBody.create(file, MediaType.parse("multipart/form-data"));
        MultipartBody.Part body = MultipartBody.Part.createFormData("aFile", file.getName(), requestFile);

        Observable<BaseResponse> observable = mModel.addTip(des, body);
        Disposable disposable = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    getView().hideLoading();
                    KLog.d("上传音频成功");
                    if (s.getStatus() == 0){
                        getView().uploadSucc(s);
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
