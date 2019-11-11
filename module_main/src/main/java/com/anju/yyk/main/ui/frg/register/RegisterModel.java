package com.anju.yyk.main.ui.frg.register;

import com.anju.yyk.common.base.BaseApplication;
import com.anju.yyk.common.base.BaseModel;
import com.anju.yyk.common.base.BaseResponse;
import com.anju.yyk.common.entity.response.CheckRoomListResponse;
import com.anju.yyk.common.entity.response.UploadImageResponse;
import com.anju.yyk.common.http.ApiAction;
import com.anju.yyk.common.http.NetManager;
import com.anju.yyk.main.di.component.DaggerMainComponent;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

public class RegisterModel extends BaseModel implements IRegisterContract.IRegisterModel {

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
    public Observable<CheckRoomListResponse> requestCheckRoomList() {
        return mNetManager.getApiService().checkRoomList(ApiAction.CHECKROOM_LIST_ACTION);
    }

    @Override
    public Observable<BaseResponse> requestCheckRoomCommit(String hugong_id, String txtContent
            , int isCheck, String laoren_id, Map<String, String> map, String imagePath) {
        return mNetManager.getApiService().checkRoomCommit(ApiAction.CHECKROOM_ADD_ACTION, hugong_id
                , txtContent, isCheck, laoren_id, map, imagePath);
    }

    @Override
    public Observable<UploadImageResponse> uploadImage(String name, MultipartBody.Part file) {
        return mNetManager.getApiService().uploadImage(ApiAction.UPLOAD_IMAGE_ACTION, name, file);
    }
}
