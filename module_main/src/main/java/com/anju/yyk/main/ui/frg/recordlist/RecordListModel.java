package com.anju.yyk.main.ui.frg.recordlist;

import com.anju.yyk.common.base.BaseApplication;
import com.anju.yyk.common.base.BaseModel;
import com.anju.yyk.common.entity.response.RecordResponse;
import com.anju.yyk.common.http.ApiAction;
import com.anju.yyk.common.http.NetManager;
import com.anju.yyk.main.di.component.DaggerMainComponent;

import javax.inject.Inject;

import io.reactivex.Observable;

public class RecordListModel extends BaseModel implements IRecordListContract.IRecordListModel {

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
    public Observable<RecordResponse> requestCareRecordList(String id, String startTime, String endTime) {
        return mNetManager.getApiService().careRecordList(ApiAction.HULIJILU_ACTION, id, startTime, endTime);
    }

    @Override
    public Observable<RecordResponse> requestCheckRoomRecordList(String id, String startTime, String endTime) {
        return mNetManager.getApiService().checkRoomRecordList(ApiAction.LOOK_ROUND_ACTION, id, startTime, endTime);
    }

    @Override
    public Observable<RecordResponse> requestAccidentRecordList(String id, String startTime, String endTime) {
        return mNetManager.getApiService().accidentRecordList(ApiAction.ACCIDENT_ACTION, id, startTime, endTime);
    }
}
