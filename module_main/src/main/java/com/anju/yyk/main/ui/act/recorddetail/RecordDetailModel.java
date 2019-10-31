package com.anju.yyk.main.ui.act.recorddetail;

import com.anju.yyk.common.base.BaseApplication;
import com.anju.yyk.common.base.BaseModel;
import com.anju.yyk.common.entity.response.AccidentDetailResponse;
import com.anju.yyk.common.entity.response.CareDetailResponse;
import com.anju.yyk.common.entity.response.CheckRoomDetailResponse;
import com.anju.yyk.common.http.ApiAction;
import com.anju.yyk.common.http.NetManager;
import com.anju.yyk.main.di.component.DaggerMainComponent;

import javax.inject.Inject;

import io.reactivex.Observable;

public class RecordDetailModel extends BaseModel implements IRecordDetailContract.IRecordDetailModel{

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
    public Observable<CheckRoomDetailResponse> requestCheckRoomDetail(String chafang_id) {
        return mNetManager.getApiService().lookRoundInfoDetail(ApiAction.CHECKROOM_DETAIL_ACTION, chafang_id);
    }

    @Override
    public Observable<CareDetailResponse> requestCareDetail(String laoren_id, String time) {
        return mNetManager.getApiService().careDetail(ApiAction.CARE_DETAIL_ACTION, laoren_id, time);
    }

    @Override
    public Observable<AccidentDetailResponse> requestAccidentDetail(String shigu_id) {
        return mNetManager.getApiService().accidentDetail(ApiAction.ACCIDENT_DETAIL_ACTION, shigu_id);
    }
}
