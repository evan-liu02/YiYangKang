package com.anju.yyk.main.ui.act.homepage;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.anju.yyk.common.app.arouter.RouterConstants;
import com.anju.yyk.common.app.sp.AppSP;
import com.anju.yyk.common.base.BaseActivity;
import com.anju.yyk.common.base.BaseApplication;
import com.anju.yyk.common.utils.SweetAlertUtil;
import com.anju.yyk.common.utils.eventbus.Event;
import com.anju.yyk.common.utils.eventbus.EventConstant;
import com.anju.yyk.common.widget.bottomnavigationviewex.BottomNavigationViewEx;
import com.anju.yyk.common.widget.sweetalert.SweetAlertDialog;
import com.anju.yyk.main.R;
import com.anju.yyk.main.R2;
import com.anju.yyk.main.di.component.DaggerMainComponent;
import com.anju.yyk.main.ui.frg.home.HomeFrg;
import com.anju.yyk.main.ui.frg.patrol.PatrolFrg;
import com.anju.yyk.main.ui.frg.patrol.PatrolWebFrg;
import com.anju.yyk.main.ui.frg.record.RecordFrg;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import javax.inject.Inject;

import butterknife.BindView;

@Route(path = RouterConstants.ACT_URL_HOME_PAGE)
public class HomePageAct extends BaseActivity {
    private long mExitTime = 0;

    /** 当前选中tab位置*/
    private int mCurItemPosition;

    @BindView(R2.id.navigationBar)
    BottomNavigationViewEx mNavigationBar;

    private HomeFrg mHomeFrg;
//    private PatrolFrg mPatrolFrg;
    private RecordFrg mRecordFrg;

    private PatrolWebFrg mPatrolWebFrg;

    private Fragment mCurFrg = new Fragment();

    @Inject
    AppSP mAppSP;

    @Override
    protected int getLayoutId() {
        return R.layout.home_act_mainpage;
    }

    @Override
    protected void init() {
        mToolbar.ivIsShow(false, false);
        setToolbarTopic(R.string.home_topic_appname);
        mToolbar.setRightIvSrc(R.mipmap.home_ic_exchange);
        prepareFrg();
        mNavigationBar.enableAnimation(false);
        mNavigationBar.enableShiftingMode(false);
        mNavigationBar.enableItemShiftingMode(false);
    }

    @Override
    public void initListener() {
        mNavigationBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            private int previousPosition = -1;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int position = 0;
                if (item.getItemId() == R.id.menu_main){
                    position = 0;
                    switchFrg(mHomeFrg).commit();
                }else if (item.getItemId() == R.id.menu_review){
                    position = 2;
//                    switchFrg(mPatrolFrg).commit();
                    switchFrg(mPatrolWebFrg).commit();
                }else if (item.getItemId() == R.id.menu_record){
                    position = 3;
                    switchFrg(mRecordFrg).commit();
                } else if (item.getItemId() == R.id.menu_exchange) {
                    position = 1;
                    rightIvListener();
                }
                mCurItemPosition = position;
                return true;
            }
        });
    }

    private FragmentTransaction switchFrg(Fragment targetFragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!targetFragment.isAdded()){
            if (null != mCurFrg){
                transaction.hide(mCurFrg);
            }
            transaction.add(R.id.fl_container, targetFragment, targetFragment.getClass().getName());
        }else {
            transaction.hide(mCurFrg)
                    .show(targetFragment);
        }
        mCurFrg = targetFragment;
        return transaction;
    }

    @Override
    public void initData() {
        prepareFrg();
        switchFrg(mHomeFrg).commit();
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
        return true;
    }

    private void prepareFrg(){
        mHomeFrg = new HomeFrg();
//        mPatrolFrg = new PatrolFrg();
        mPatrolWebFrg = new PatrolWebFrg();
        mRecordFrg = new RecordFrg();
    }

    @Override
    public void onEventBusCome(Event event) {
        if (event != null){
            switch (event.getCode()){
                case EventConstant.EventCode.REFRESH_PAGE:
                    if (mCurItemPosition == 0)
                        switchFrg(mHomeFrg).commit();

                    break;
                case EventConstant.EventCode.REFRESH_RECORDLIST_FRG:
                    if (mCurItemPosition == 3)
                        switchFrg(mRecordFrg).commit();
                    break;
            }
        }
    }

    @Override
    public void rightIvListener() {
        SweetAlertUtil.showConfirmAndCancelAlert(mActivity, getString(R.string.home_dialog_title_exchange)
                , getString(R.string.home_dialog_content_exchange), getString(R.string.home_dialog_confirm_exchange)
                , getString(R.string.home_dialog_cancle_exchange), finishOrderConfirmListener);
    }

    /**
     * 交班Dialog确认
     */
    private SweetAlertDialog.OnSweetButtonClickListener finishOrderConfirmListener = new SweetAlertDialog.OnSweetButtonClickListener() {

        @Override
        public void onClick(View v) {
            if (v.getId() == com.anju.yyk.common.R.id.confirm_button){
                mAppSP.clearSP();
                ARouter.getInstance().build(RouterConstants.ACT_URL_LOGIN)
                        .navigation();
                finish();
            }
        }
    };
}
