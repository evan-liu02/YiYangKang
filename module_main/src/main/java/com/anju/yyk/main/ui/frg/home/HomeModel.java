package com.anju.yyk.main.ui.frg.home;

import com.anju.yyk.common.app.sp.AppSP;
import com.anju.yyk.common.base.BaseApplication;
import com.anju.yyk.common.base.BaseModel;
import com.anju.yyk.common.base.BaseResponse;
import com.anju.yyk.common.entity.response.AttentionCountResponse;
import com.anju.yyk.common.entity.response.PersonListResponse;
import com.anju.yyk.common.http.ApiAction;
import com.anju.yyk.common.http.ApiAddr;
import com.anju.yyk.common.http.NetManager;
import com.anju.yyk.main.di.component.DaggerMainComponent;

import javax.inject.Inject;

import io.reactivex.Observable;

public class HomeModel extends BaseModel implements IHomeContract.IHomeModel {

    @Inject
    NetManager mNetManager;

    @Inject
    AppSP mAppSP;

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
    public Observable<AttentionCountResponse> requestAttentionCount() {
        return mNetManager.getApiService().attentionCount(ApiAction.ATTENTION_COUNT_ACTION, mAppSP.getUserId());
    }

    @Override
    public Observable<PersonListResponse> requestPersonTop() {
        return mNetManager.getApiService().personTop(ApiAction.PERSON_TOP_ACTION, mAppSP.getUserId());
    }

    @Override
    public Observable<PersonListResponse> requestPersonList() {
        return mNetManager.getApiService().personInfo(ApiAction.PERSON_INFO_ACTION, mAppSP.getUserId());
    }

    @Override
    public Observable<BaseResponse> requestChangeTopStatus(String personId, int status) {
        return mNetManager.getApiService().contralTop(ApiAction.CONTRAL_TOP_ACTION, mAppSP.getUserId()
                        , personId, status);
    }
}
