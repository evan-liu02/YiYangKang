package com.anju.yyk.main.ui.act.addtip;

import com.anju.yyk.common.base.BaseApplication;
import com.anju.yyk.common.base.BaseModel;
import com.anju.yyk.common.base.BaseResponse;
import com.anju.yyk.common.entity.response.UploadAudioResponse;
import com.anju.yyk.common.http.ApiAction;
import com.anju.yyk.common.http.NetManager;
import com.anju.yyk.main.di.component.DaggerMainComponent;
import com.anju.yyk.main.ui.act.addtip.IAddTipContract.IAddTipModel;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AddTipModel extends BaseModel implements IAddTipContract.IAddTipModel {

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
    public Observable<BaseResponse> addTip(String id, String userId, String content, String filePath) {
        return mNetManager.getApiService().addTip(ApiAction.ADD_TIP_ACTION, id, userId, content, filePath);
    }

    @Override
    public Observable<UploadAudioResponse> uploadAudio(String filePath) {
        File file = new File(filePath);
        RequestBody requestFile = RequestBody.create(file, MediaType.parse("multipart/form-data"));
        MultipartBody.Part body = MultipartBody.Part.createFormData("aFile", file.getName(), requestFile);
        return mNetManager.getApiService().uploadAudio(ApiAction.SOUND_ACTION, file.getName(), body);
    }
}
