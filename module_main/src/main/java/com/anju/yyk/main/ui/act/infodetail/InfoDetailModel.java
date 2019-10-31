package com.anju.yyk.main.ui.act.infodetail;

import com.anju.yyk.common.base.BaseApplication;
import com.anju.yyk.common.base.BaseModel;
import com.anju.yyk.common.entity.response.DiseaseResponse;
import com.anju.yyk.common.entity.response.HealthResposne;
import com.anju.yyk.common.entity.response.JisShuResponse;
import com.anju.yyk.common.entity.response.OperationResponse;
import com.anju.yyk.common.http.ApiAction;
import com.anju.yyk.common.http.NetManager;
import com.anju.yyk.main.di.component.DaggerMainComponent;

import javax.inject.Inject;

import io.reactivex.Observable;

public class InfoDetailModel extends BaseModel implements IInfoDetailContract.IInfoDetailModel {

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
    public Observable<JisShuResponse> requestJisShu(String id) {
        return mNetManager.getApiService().familyInfo(ApiAction.FAMILY_INFO_ACTION, id);
    }

    @Override
    public Observable<HealthResposne> requestHealthInfo(String id) {
        return mNetManager.getApiService().healthInfo(ApiAction.HEALTH_ACTION, id);
    }

    @Override
    public Observable<DiseaseResponse> requestDiseaseInfo(String id) {
        return mNetManager.getApiService().diseaseInfo(ApiAction.DISEASE_ACTION, id);
    }

    @Override
    public Observable<OperationResponse> requestOperationInfo(String id) {
        return mNetManager.getApiService().operationInfo(ApiAction.OPERATION_ACTION, id);
    }
}
