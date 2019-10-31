package com.anju.yyk.main.ui.act.scantips;

import com.anju.yyk.common.app.sp.AppSP;
import com.anju.yyk.common.base.BaseApplication;
import com.anju.yyk.common.base.BaseModel;
import com.anju.yyk.common.base.BaseResponse;
import com.anju.yyk.common.entity.response.NewTipsListResponse;
import com.anju.yyk.common.http.ApiAction;
import com.anju.yyk.common.http.NetManager;
import com.anju.yyk.main.di.component.DaggerMainComponent;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class ScanTipsModel extends BaseModel implements IScanTipsContract.IScanTipsModel {

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
    public Observable<NewTipsListResponse> requestTips() {
        return mNetManager.getApiService().tips(ApiAction.TOP_ATTENTION_ACTION, mAppSP.getUserId());
    }

    @Override
    public Observable<NewTipsListResponse> requestPersonalTips(String laoren_id) {
        return mNetManager.getApiService().personalTips(ApiAction.PERSONAL_TIPS_ACTION, mAppSP.getUserId(), laoren_id);
    }

    @Override
    public Observable<ResponseBody> requestDownLoadAudio(String audioUrl) {
        return mNetManager.getApiService().downLoadAudio(audioUrl);
    }

    @Override
    public Observable<BaseResponse> requestReaded(String zhuyi_id) {
        return mNetManager.getApiService().readed(ApiAction.READED_ACTION, mAppSP.getUserId(), zhuyi_id);
    }
}
