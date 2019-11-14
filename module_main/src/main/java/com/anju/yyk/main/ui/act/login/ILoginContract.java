package com.anju.yyk.main.ui.act.login;

import com.anju.yyk.common.base.BasePresenter;
import com.anju.yyk.common.base.IBaseModel;
import com.anju.yyk.common.base.IBaseView;
import com.anju.yyk.common.entity.response.LoginResponse;

import io.reactivex.Observable;

public interface ILoginContract {

    interface ILoginModel extends IBaseModel{
        /**
         * 请求登录
         * @param account 账号
         * @param password 密码
         * @return
         */
        Observable<LoginResponse> requestLogin(String account, String password);
    }

    interface ILoginView extends IBaseView{
        /**
         * 登录成功
         */
        void loginSucc();
        void loginFailed();
    }

    abstract class LoginPresenter extends BasePresenter<ILoginModel, ILoginView>{
        public abstract void login(String account, String password);
    }

}
