package com.anju.yyk.main.ui.act.login;

import com.anju.yyk.common.base.BaseApplication;
import com.anju.yyk.common.base.BaseModel;
import com.anju.yyk.common.entity.response.LoginResponse;
import com.anju.yyk.common.http.ApiAction;
import com.anju.yyk.common.http.ApiAddr;
import com.anju.yyk.common.http.NetManager;
import com.anju.yyk.main.di.component.DaggerMainComponent;

import javax.inject.Inject;

import io.reactivex.Observable;

public class LoginModel extends BaseModel implements ILoginContract.ILoginModel {

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
    public Observable<LoginResponse> requestLogin(String account, String password) {
        return mNetManager.getApiService().login(ApiAction.LOGIN_ACTION, account, password);
    }
}
