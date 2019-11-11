package com.anju.yyk.main.ui.frg.accidentregister;

import com.anju.yyk.common.base.BaseApplication;
import com.anju.yyk.common.base.BaseModel;
import com.anju.yyk.common.base.BaseResponse;
import com.anju.yyk.common.entity.response.UploadFileResponse;
import com.anju.yyk.common.entity.response.UploadImageResponse;
import com.anju.yyk.common.http.ApiAction;
import com.anju.yyk.common.http.NetManager;
import com.anju.yyk.main.di.component.DaggerMainComponent;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AccidentRegModel extends BaseModel implements IAccidentRegContract.IAccidentRegModel {

    @Inject
    NetManager mNetManager;

    @Override
    public void setupModelComponent() {
        DaggerMainComponent.builder()
                .appComponent(BaseApplication.getInstance().getAppComponent())
                .build()
                .inject(this);
    }

    @Override
    public boolean isSetupModelComponent() {
        return true;
    }

    @Override
    public Observable<BaseResponse> addTip(RequestBody des, MultipartBody.Part file) {
        return mNetManager.getApiService().uploadAudio(des, file);
    }

    @Override
    public Observable<BaseResponse> requestUploadPhoto(MultipartBody.Part imageFile) {
        return mNetManager.getApiService().uploadPhoto(imageFile);
    }

    @Override
    public Observable<UploadFileResponse> uploadFile(String name, MultipartBody.Part file) {
        return mNetManager.getApiService().uploadFile(ApiAction.SOUND_ACTION, name, file);
    }

    @Override
    public Observable<UploadImageResponse> uploadImage(String name, MultipartBody.Part file) {
        return mNetManager.getApiService().uploadImage(ApiAction.UPLOAD_IMAGE_ACTION, name, file);
    }

    @Override
    public Observable<BaseResponse> addAccident(String id, String userId, String content, String audioPath, String imagePath) {
        return mNetManager.getApiService().addAccident(ApiAction.ADD_ACCIDENT_ACTION, id, userId, content, audioPath, imagePath);
    }
}
