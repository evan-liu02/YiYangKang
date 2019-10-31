package com.anju.yyk.main.ui.act.login;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.anju.yyk.common.app.arouter.RouterConstants;
import com.anju.yyk.common.app.sp.AppSP;
import com.anju.yyk.common.app.sp.GlobalSP;
import com.anju.yyk.common.base.BaseActivity;
import com.anju.yyk.common.base.BaseApplication;
import com.anju.yyk.common.base.BaseMvpActivity;
import com.anju.yyk.common.utils.AppUtil;
import com.anju.yyk.main.R;
import com.anju.yyk.main.R2;
import com.anju.yyk.main.di.component.DaggerMainComponent;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = RouterConstants.ACT_URL_LOGIN)
public class LoginAct extends BaseMvpActivity<LoginPresenter, LoginModel> implements ILoginContract.ILoginView{

    @BindView(R2.id.edt_account)
    EditText mAccountEdt;

    @BindView(R2.id.edt_pwd)
    EditText mPwdEdt;

    @BindView(R2.id.cb_remeber)
    CheckBox mRemeberCB;

    @BindView(R2.id.btn_login)
    Button mLoginBtn;

    @Inject
    GlobalSP mGlobalSP;

    @Override
    protected int getLayoutId() {
        return R.layout.home_act_login;
    }

    @Override
    protected void init() {
        hideToolbar();
        AppUtil.statuInScreen(this);
        mRemeberCB.setChecked(mGlobalSP.isRememberPwd());
        if (mGlobalSP.isRememberPwd()){
            mAccountEdt.setText(mGlobalSP.getAccount());
            mPwdEdt.setText(mGlobalSP.getPwd());
        }
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    protected void setupActivityComponent() {
        DaggerMainComponent.builder()
                .appComponent(BaseApplication.getInstance().getAppComponent())
                .build()
                .inject(this);
    }

    @Override
    public boolean isRegisterEventBus() {
        return false;
    }

    @OnClick({R2.id.btn_login})
    public void onViewClicked(View view){
        if (view.getId() == R.id.btn_login){
            String account = mAccountEdt.getText().toString().trim();
            String pwd = mPwdEdt.getText().toString().trim();
            if (TextUtils.isEmpty(account) || TextUtils.isEmpty(pwd)){
                showToast(R.string.home_toast_input_content);
            }else {
                mPresenter.login(account, pwd);
            }
        }
    }

    @Override
    public void loginSucc() {
        if (mRemeberCB.isChecked()){
            mGlobalSP.setAccount(mAccountEdt.getText().toString().trim());
            mGlobalSP.savePwd(mPwdEdt.getText().toString().trim());
            mGlobalSP.saveIsRememberPwd(true);
        }
        ARouter.getInstance().build(RouterConstants.ACT_URL_HOME_PAGE)
                .navigation();
        finish();
    }
}
