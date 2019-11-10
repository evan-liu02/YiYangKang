package com.anju.yyk.main.ui.frg.accidentregister;

import com.anju.yyk.common.base.BaseApplication;
import com.anju.yyk.common.base.BaseResponse;
import com.anju.yyk.common.entity.response.UploadFileResponse;
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

public class AccidentRegPresenter extends IAccidentRegContract.AccidentReg{


    @Override
    public void uploadPhoto(String imgPath) {
        if (getView() == null)
            return;

        getView().showLoading(false);

        File file = new File(imgPath);
        RequestBody requestFile = RequestBody.create(file, MediaType.parse("image/jpeg"));
        MultipartBody.Part body = MultipartBody.Part.createFormData("imageFile", file.getName(), requestFile);

        Observable<BaseResponse> observable = mModel.requestUploadPhoto(body);
        Disposable disposable = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    getView().hideLoading();
                    KLog.d("上传图片成功");
                    if (s.getStatus() == 0){
                        getView().uploadSucc();
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
    public void uploadFile(String filePath) {
        File file = new File(filePath);
        RequestBody requestFile = RequestBody.create(file, MediaType.parse("multipart/form-data"));
        MultipartBody.Part body = MultipartBody.Part.createFormData("aFile", file.getName(), requestFile);

        Observable<UploadFileResponse> observable = mModel.uploadFile(file.getName(), body);
        Disposable disposable = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    KLog.d("上传音频成功");
                    if (s.getStatus() == 1) {
                        getView().hideLoading();
                        getView().uploadSucc(s.getPath());
                    } else {
                        getView().hideLoading();
                        getView().showToast(s.getMsg());
                        getView().uploadFailed(filePath);
                    }
                }, throwable -> {
                    getView().hideLoading();
                    getView().showToast(handleThrowableInP(throwable).message);
                    getView().uploadFailed(filePath);
                });
        addDisposable(disposable);
    }

    @Override
    public void uploadImage(String filePath, String type, int isThumb, int isWater) {
        File file = new File(filePath);
        RequestBody requestFile = RequestBody.create(file, MediaType.parse("image/png"));
        MultipartBody.Part body = MultipartBody.Part.createFormData("aFile", file.getName(), requestFile);

        Observable<UploadFileResponse> observable = mModel.uploadImage(file.getName(), type, isThumb, isWater, body);
        Disposable disposable = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    KLog.d("上传图片成功");
                    if (s.getStatus() == 1) {
                        getView().hideLoading();
                        getView().uploadSucc(s.getPath());
                    } else {
                        getView().hideLoading();
                        getView().showToast(s.getMsg());
                        getView().uploadFailed(filePath);
                    }
                }, throwable -> {
                    getView().hideLoading();
                    getView().showToast(handleThrowableInP(throwable).message);
                    getView().uploadFailed(filePath);
                });
        addDisposable(disposable);
    }

    @Override
    public void addAccident(String id, String userId, String content, String audioPath, String imagePath) {
        Observable<BaseResponse> observable = mModel.addAccident(id, userId, content, audioPath, imagePath);
        Disposable disposable = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    KLog.d("事故添加成功");
                    if (s.getStatus() == 0) {
                        getView().hideLoading();
                        getView().addAccidentSuccess();
                    } else {
                        getView().hideLoading();
                        getView().showToast(s.getMsg());
                        getView().addAccidentFailed();
                    }
                }, throwable -> {
                    getView().hideLoading();
                    getView().showToast(handleThrowableInP(throwable).message);
                    getView().addAccidentFailed();
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
