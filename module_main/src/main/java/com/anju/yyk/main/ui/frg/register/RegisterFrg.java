package com.anju.yyk.main.ui.frg.register;

import android.view.View;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anju.yyk.common.app.arouter.RouterKey;
import com.anju.yyk.common.app.sp.AppSP;
import com.anju.yyk.common.base.BaseApplication;
import com.anju.yyk.common.base.BaseMvpFragment;
import com.anju.yyk.common.entity.response.CheckRoomListResponse;
import com.anju.yyk.common.entity.response.PersonListResponse;
import com.anju.yyk.common.utils.AppUtil;
import com.anju.yyk.common.utils.klog.KLog;
import com.anju.yyk.common.widget.itemdecoration.SpacesItemDecoration;
import com.anju.yyk.main.R;
import com.anju.yyk.main.R2;
import com.anju.yyk.main.adapter.CheckRoomAdapter;
import com.anju.yyk.main.di.component.DaggerMainComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import kotlin.LateinitKt;

/**
 *
 * @author LeoWang
 *
 * @Package com.anju.yyk.main.ui.frg.checkroomregister
 *
 * @Description 查房登记
 *
 * @Date 2019/10/16 17:28
 *
 * @modify:
 */
public class RegisterFrg extends BaseMvpFragment<RegisterPresenter, RegisterModel>
        implements IRegisterContract.IRegisterView, CheckRoomAdapter.CheckRoomAdapterCallback {

    @BindView(R2.id.recyclerView)
    RecyclerView mRecyclerView;

    private CheckRoomAdapter mAdapter;

    private List<CheckRoomListResponse.ListBean> mList = new ArrayList<>();

    private int mCurPosition = 0;

    @Inject
    AppSP mAppSP;

    private PersonListResponse.ListBean mPersonInfo;
    private HashMap<String, String> itemMap = new HashMap<String, String>();

    @Override
    public int getLayoutId() {
        return R.layout.home_frg_checkroom;
    }

    @Override
    public void init() {
        if (getArguments() != null){
            mPersonInfo = (PersonListResponse.ListBean) getArguments().getSerializable(RouterKey.BUNDLE_TAG);
        }
        initRecyclerView();
    }

    @Override
    public void initListener() {

    }

    @Override
    protected void initData() {
        mPresenter.getCheckRoomList();
    }

    @Override
    protected void setupFragmentComponent() {
        DaggerMainComponent.builder()
                .appComponent(BaseApplication.getInstance().getAppComponent())
                .build()
                .inject(this);
    }

    @Override
    protected boolean isRegisterEventBus() {
        return false;
    }

    private void initRecyclerView(){

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(0
                , AppUtil.dip2px(mActivity, 1), AppUtil.getColor(mActivity, R.color.common_divder_color)));

        mAdapter = new CheckRoomAdapter(mList);
        mAdapter.setAdapterCallBack(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void checkRoomListSucc(CheckRoomListResponse response) {
        List<CheckRoomListResponse.ListBean> listBeans = response.getList();
        if (listBeans != null && listBeans.size() > 0){
            mList.clear();
            mList.addAll(listBeans);
            mAdapter.notifyDataSetChanged();

            if (mList != null && mList.size() > 0) {
                for (CheckRoomListResponse.ListBean itemBean : mList) {
                    int type = itemBean.getLeixing();
                    if (type == 2) {
                        itemMap.put(itemBean.getLieming(), itemBean.getToggleStatus() == 1 ? "否" : "是");
                    } else if (type == 1) {
                        List<CheckRoomListResponse.ListBean.OptionBean> option = itemBean.getOption();
                        if (option != null && option.size() > 0) {
                            itemMap.put(itemBean.getLieming(), option.get(0).getZhi());
                        }
                    } else if (type == 0) {
                        itemMap.put(itemBean.getLieming(), "");
                    }
                }
            }
        }
    }

    @Override
    public void checkRoomCommitSucc() {

    }

    @Override
    public void toggle(int position, boolean isCheck, String id) {
        mCurPosition = position;
        KLog.d("点击了第" + position + "行------" + isCheck);
        if (isCheck) {
            mList.get(mCurPosition).setToggleStatus(0);
        } else {
            mList.get(mCurPosition).setToggleStatus(1);
        }
        if (position < mList.size()) {
            String key = mList.get(position).getLieming();
            itemMap.put(key, isCheck ? "是" : "否");
        }
    }

    @Override
    public void selectedItem(int position, String itemText) {
        if (position < mList.size()) {
            String key = mList.get(position).getLieming();
            itemMap.put(key, itemText);
        }
    }

    @OnClick({R2.id.btn_commit})
    public void onViewClicked(View view){
        if (view.getId() == R.id.btn_commit) {
            //11-01 12:37:07.140 10646 10646 E AndroidRuntime: java.lang.IllegalArgumentException: @FieldMap parameters can only be used with form encoding. (parameter #6)
            mPresenter.checkRoomCommit(mAppSP.getUserId(), "", false, mPersonInfo.getId(), itemMap);
        }
    }
}
