package com.anju.yyk.main.ui.frg.careregister;

import com.anju.yyk.common.base.BaseApplication;
import com.anju.yyk.common.base.BaseModel;
import com.anju.yyk.common.base.BaseResponse;
import com.anju.yyk.common.entity.response.HuLiXiangmu0Response;
import com.anju.yyk.common.entity.response.HuLiXiangmu1Response;
import com.anju.yyk.common.http.ApiAction;
import com.anju.yyk.common.http.NetManager;
import com.anju.yyk.main.di.component.DaggerMainComponent;

import javax.inject.Inject;

import io.reactivex.Observable;

public class CareRegModel extends BaseModel implements ICareRegContract.ICareRegModel {

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
    public Observable<HuLiXiangmu0Response> requestHuli0(String id) {
        return mNetManager.getApiService().hulixiangmu0List(ApiAction.HULIXIANGMU0_ACTION, id);
    }

    @Override
    public Observable<HuLiXiangmu1Response> requestHuli1(String id) {
        return mNetManager.getApiService().hulixiangmu1List(ApiAction.HULIXIANGMU1_ACTION, id);
    }

    @Override
    public Observable<BaseResponse> requestComplete(String id, String jifen, String title, String laoren_id, String hugong_id) {
        return mNetManager.getApiService().hulidengji(ApiAction.HULIDENGJI_ACTION, id, jifen, title, laoren_id, hugong_id);
    }
}
