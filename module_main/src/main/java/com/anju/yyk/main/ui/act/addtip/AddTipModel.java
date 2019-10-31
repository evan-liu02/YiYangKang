package com.anju.yyk.main.ui.act.addtip;

import com.anju.yyk.common.base.BaseApplication;
import com.anju.yyk.common.base.BaseModel;
import com.anju.yyk.common.base.BaseResponse;
import com.anju.yyk.common.http.NetManager;
import com.anju.yyk.main.di.component.DaggerMainComponent;
import com.anju.yyk.main.ui.act.addtip.IAddTipContract.IAddTipModel;

import javax.inject.Inject;

import io.reactivex.Observable;
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
    public Observable<BaseResponse> addTip(RequestBody des, MultipartBody.Part file) {
        return mNetManager.getApiService().uploadAudio(des, file);
    }
}
