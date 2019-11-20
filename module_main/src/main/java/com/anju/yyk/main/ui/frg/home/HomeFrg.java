package com.anju.yyk.main.ui.frg.home;

import android.content.Intent;
import android.graphics.Color;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.anju.yyk.common.app.WebUrlConstant;
import com.anju.yyk.common.app.arouter.RouterConstants;
import com.anju.yyk.common.app.arouter.RouterKey;
import com.anju.yyk.common.app.sp.AppSP;
import com.anju.yyk.common.base.BaseApplication;
import com.anju.yyk.common.base.BaseMvpFragment;
import com.anju.yyk.common.entity.response.AttentionCountResponse;
import com.anju.yyk.common.entity.response.PersonListResponse;
import com.anju.yyk.common.utils.AppUtil;
import com.anju.yyk.common.utils.eventbus.Event;
import com.anju.yyk.common.utils.eventbus.EventBusUtil;
import com.anju.yyk.common.utils.eventbus.EventConstant;
import com.anju.yyk.common.utils.klog.KLog;
import com.anju.yyk.common.widget.itemdecoration.SpacesItemDecoration;
import com.anju.yyk.main.R;
import com.anju.yyk.main.R2;
import com.anju.yyk.main.adapter.BedListAdapter;
import com.anju.yyk.main.di.component.DaggerMainComponent;
import com.anju.yyk.main.entity.BedTitleEntity;
import com.anju.yyk.main.utils.IconReplacementSpan;
import com.anju.yyk.main.utils.PersonInfoHelper;
import com.anju.yyk.main.widget.AddTipDialog;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 
 * @author LeoWang
 * 
 * @Package com.anju.yyk.main.ui.frg.home
 * 
 * @Description 首页
 * 
 * @Date 2019/9/5 15:21
 * 
 * @modify:
 */
public class HomeFrg extends BaseMvpFragment<HomePresenter, HomeModel>
        implements IHomeContract.IHomeView, PtrHandler, BedListAdapter.BedListCallback
                , AddTipDialog.AddTipCallback {

    @BindView(R2.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R2.id.fl_ptr)
    PtrFrameLayout mPtrFl;

    @BindView(R2.id.tv_top_count)
    TextView mTopTv;

    private BedListAdapter mAdapter;

    private List<MultiItemEntity> mDedInfoList = new ArrayList<>();

    @Inject
    AppSP mAppSP;

    private AddTipDialog mAddTipDialog;
    private boolean isRefresh;

    /** 录音*/
//    private final int RECORD_AUDIO_REQUESTCODE = 1103;

    @Override
    public int getLayoutId() {
        return R.layout.home_frg_hp;
    }

    @Override
    public void init() {
        initRecyclerView();
    }

    @Override
    public void initListener() {
    }

    @Override
    protected void initData() {
        mPresenter.getAttentionCount(true);
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
        return true;
    }

    private void initRecyclerView(){
        // 初始化自定义header，现在使用默认
        PtrClassicDefaultHeader header = new PtrClassicDefaultHeader(mActivity);
        // 添加header
        mPtrFl.addPtrUIHandler(header);
        // 设置刷新头部布局
//        mPtrFl.setHeaderView(header);
        // 设置监听
        mPtrFl.setPtrHandler(this);
        // 下拉，上拉
        mPtrFl.setMode(PtrFrameLayout.Mode.NONE);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(0
                , AppUtil.dip2px(mActivity, 1), AppUtil.getColor(mActivity, R.color.common_divder_color)));

        mAdapter = new BedListAdapter(mDedInfoList);
        mAdapter.setBedListCallback(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return false;
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        mPresenter.getPersonTop();
    }

    @Override
    public void getAttentionCountSucc(AttentionCountResponse response) {
        /*String str = "有新注意事项" + response.getCount() + "条";
        SpannableString spannableStr = new SpannableString(str);
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            int start = matcher.start(0);
            int end = matcher.end(0);
            IconReplacementSpan hSpan = new IconReplacementSpan(getContext(), Color.RED, Color.WHITE, matcher.group(0));
            spannableStr.setSpan(hSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            mTopTv.setText(spannableStr);
        }*/
        mTopTv.setText(response.getCount());
        if (!isRefresh) {
            mPtrFl.autoRefresh();
        }
    }

    @Override
    public void getPersonTop(List<PersonListResponse.ListBean> list) {
        mDedInfoList.clear();
        BedTitleEntity entity1 = new BedTitleEntity();
        entity1.setTitle("重点照护");
        entity1.setSubItems(list);
        mDedInfoList.add(entity1);
        mPresenter.getPersonList();
    }

    @Override
    public void getPersonListSucc(List<PersonListResponse.ListBean> list) {
        mPtrFl.refreshComplete();
        PersonInfoHelper.personList = list;

        BedTitleEntity entity2 = new BedTitleEntity();
        entity2.setTitle("1-15床");

        BedTitleEntity entity3 = new BedTitleEntity();
        entity3.setTitle("16-30床");

        BedTitleEntity entity4 = new BedTitleEntity();
        entity4.setTitle("31-45床");

        BedTitleEntity entity5 = new BedTitleEntity();
        entity5.setTitle("46-55床");

        for (PersonListResponse.ListBean bean : list){
            if (1 <= bean.getChuangwei() && 15 >= bean.getChuangwei()){
                entity2.addSubItem(bean);
            }else if (16 <= bean.getChuangwei() && 30 >= bean.getChuangwei()){
                entity3.addSubItem(bean);
            }else if (31 <= bean.getChuangwei() && 45 >= bean.getChuangwei()){
                entity4.addSubItem(bean);
            }else if (46 <= bean.getChuangwei() && 55 >= bean.getChuangwei()){
                entity5.addSubItem(bean);
            }
        }

        mDedInfoList.add(entity2);
        mDedInfoList.add(entity3);
        mDedInfoList.add(entity4);
        mDedInfoList.add(entity5);

        mAdapter.notifyDataSetChanged();
        mPtrFl.autoRefresh();

        Event event = new Event(EventConstant.EventCode.REFRESH_PAGE);
        EventBusUtil.sendEvent(event);
    }

    @Override
    public void changeTopStatusSucc() {
        mPresenter.getAttentionCount(true);
    }

    @Override
    public void noTop() {
        mDedInfoList.clear();
        mPresenter.getPersonList();
    }

    @Override
    public void refreshComplete() {
        mPtrFl.refreshComplete();
    }

    @Override
    public void clickTop(PersonListResponse.ListBean bean) {
        int status = -1;
        if (bean.getIstop() == 0){
            status = 0;
        }else {
            status = 1;
        }
        mPresenter.changeTopStatus(bean.getId(), status);
    }

    @Override
    public void scanTips(PersonListResponse.ListBean bean) {
        // 查看提醒列表
        ARouter.getInstance().build(RouterConstants.ACT_URL_SCAN_PERSONAL_TIPS)
                .withSerializable(RouterKey.BUNDLE_TAG, bean)
                .navigation();
    }

    @Override
    public void addTips(PersonListResponse.ListBean bean) {
        ARouter.getInstance().build(RouterConstants.ACT_URL_ADD_TIP)
                .withSerializable(RouterKey.BUNDLE_TAG, bean)
                .navigation();
    }

    @Override
    public void clickItem(PersonListResponse.ListBean bean) {
//        String url = WebUrlConstant.TAKE_CARE_URL + "hugong_id=" + mAppSP.getUserId() + "&id=" + bean.getId();
//        String topic = getString(R.string.common_takecare_record);
        ARouter.getInstance().build(RouterConstants.ACT_URL_PERSON_INFO)
                .withSerializable(RouterKey.BUNDLE_TAG, bean)
                .navigation();
    }

    @Override
    public void onDestroy() {
        if (mAddTipDialog != null){
            if (mAddTipDialog.isShowing())
                mAddTipDialog.dismiss();
            mAddTipDialog.onDetachedFromWindow();
            mAddTipDialog = null;
        }
        super.onDestroy();
    }

    @Override
    public void addAudio() {
    }

    @Override
    public void commitAudio() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick({R2.id.tv_top_layout})
    public void onViewClicked(View v){
        if (v.getId() == R.id.tv_top_layout){
            ARouter.getInstance().build(RouterConstants.ACT_URL_SCAN_TIPS)
                    .navigation();
        }
    }

    @Override
    public void onEventBusCome(Event event) {
        if (event != null) {
            switch (event.getCode()) {
                case EventConstant.EventCode.REFRESH_HOME_TIPS:
                    isRefresh = true;
                    mPresenter.getAttentionCount(false);
                    break;
                default:
                    break;
            }
        }
    }
}
