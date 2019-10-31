package com.anju.yyk.main.ui.frg.recordlist;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.anju.yyk.common.app.arouter.RouterConstants;
import com.anju.yyk.common.app.arouter.RouterKey;
import com.anju.yyk.common.app.sp.AppSP;
import com.anju.yyk.common.base.BaseMvpFragment;
import com.anju.yyk.common.entity.response.RecordResponse;
import com.anju.yyk.common.utils.AppUtil;
import com.anju.yyk.common.utils.eventbus.Event;
import com.anju.yyk.common.utils.eventbus.EventBusUtil;
import com.anju.yyk.common.utils.eventbus.EventConstant;
import com.anju.yyk.common.utils.klog.KLog;
import com.anju.yyk.common.widget.itemdecoration.SpacesItemDecoration;
import com.anju.yyk.main.R;
import com.anju.yyk.main.R2;
import com.anju.yyk.main.adapter.RecordAdapter;
import com.anju.yyk.main.entity.RecordInfoEntity;
import com.anju.yyk.main.entity.RecordTitleEntity;
import com.anju.yyk.main.widget.DatePickerDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 * @author LeoWang
 *
 * @Package com.anju.yyk.main.ui.frg.recordlist
 *
 * @Description 事故记录
 *
 * @Date 2019/10/21 14:06
 *
 * @modify:
 */
public class AccidentRecordListFrg extends BaseMvpFragment<RecordPresenter, RecordListModel>
        implements IRecordListContract.IRecordListView, DatePickerDialog.DateCallback {
    @BindView(R2.id.tv_time_start)
    TextView mTimeStartTv;

    @BindView(R2.id.tv_time_end)
    TextView mTimeEndTv;

    @BindView(R2.id.sp_man_type)
    NiceSpinner mManTypeNS;

    @BindView(R2.id.btn_search)
    Button mSearchBtn;

    @BindView(R2.id.btn_reset)
    Button mResetBtn;

    @BindView(R2.id.recyclerView)
    RecyclerView mRecyclerView;

    //    private List<String> dataset = new LinkedList<>(Arrays.asList("所有老人", "55-59岁", "60-64岁", "64-69岁", "70岁以上"));
    private List<String> dataset = new LinkedList<>(Arrays.asList("所有老人"));

    private RecordAdapter mAdapter;

    private List<MultiItemEntity> mRecordInfoList = new ArrayList<>();

    private DatePickerDialog mDateDialog;

    /** 0，开始时间；1，结束时间*/
    private int mTimeType = -1;

    private String mStartTime = "";
    private String mEndTime = "";

    private AppSP mAppSP;

    @Override
    public int getLayoutId() {
        return R.layout.home_frg_recordlist;
    }

    @Override
    public void init() {
        mAppSP = new AppSP(mActivity);
        mManTypeNS.attachDataSource(dataset);
        initRecyclerView();
    }

    @Override
    public void initListener() {
        mManTypeNS.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
            }
        });

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.tv_scan_detail){
                    RecordInfoEntity infoEntity = (RecordInfoEntity) mRecordInfoList.get(position);
                    ARouter.getInstance().build(RouterConstants.ACT_URL_RECORD_DETAIL)
                            .withSerializable(RouterKey.RECORD_DETAIL_TAG, infoEntity)
                            .navigation();
                }
            }
        });
    }

    @Override
    protected void initData() {
        Event event = new Event(EventConstant.EventCode.REFRESH_RECORDLIST_FRG);
        EventBusUtil.sendEvent(event);
    }

    @Override
    protected void setupFragmentComponent() {
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

        mAdapter = new RecordAdapter(mRecordInfoList);
        mRecyclerView.setAdapter(mAdapter);
    }

    @OnClick({R2.id.tv_time_start, R2.id.tv_time_end, R2.id.btn_reset, R2.id.btn_search})
    public void onViewClicked(View view){
        if (view.getId() == R.id.tv_time_start){
            mTimeType = 0;
            mDateDialog = new DatePickerDialog();
            mDateDialog.setCallback(this);
            mDateDialog.show(getChildFragmentManager(), "date");
        }else if (view.getId() == R.id.tv_time_end){
            mTimeType = 1;
            mDateDialog = new DatePickerDialog();
            mDateDialog.setCallback(this);
            mDateDialog.show(getChildFragmentManager(), "date");
        }else if (view.getId() == R.id.btn_reset){
            mRecordInfoList.clear();
            mAdapter.notifyDataSetChanged();
        }else if (view.getId() == R.id.btn_search){
            if (!TextUtils.isEmpty(mStartTime) && !TextUtils.isEmpty(mEndTime)){
                mPresenter.getAccident(mAppSP.getUserId(), mStartTime, mEndTime);
            }
        }
    }

    @Override
    public void onDestroy() {
        if (mDateDialog != null){
            if (mDateDialog.isVisible())
                mDateDialog.dismiss();
            mDateDialog.onDestroy();
            mDateDialog = null;
        }
        super.onDestroy();
    }

    @Override
    public void getCareRecordSucc(RecordResponse response) {

    }

    @Override
    public void getCheckRoomSucc(RecordResponse response) {

    }

    @Override
    public void getaccidentSucc(RecordResponse response) {
        mRecordInfoList.clear();
        List<RecordResponse.ListBean> list = response.getList();
        if (list != null && list.size() > 0){
            KLog.d("查房记录集合大小：" + list.size());
            for (RecordResponse.ListBean bean : list){
                RecordTitleEntity title = new RecordTitleEntity();
                title.setTitle(bean.getDate());
                List<RecordResponse.ListBean.RecordsBean> records = bean.getRecords();
                if (records != null && records.size() > 0){
                    for (RecordResponse.ListBean.RecordsBean record : records){
                        RecordInfoEntity info = new RecordInfoEntity();
                        info.setBedId(record.getChuangwei());
                        info.setLaoren(record.getId());
                        info.setName(record.getName());
                        info.setId(record.getId());
                        title.addSubItem(info);
                    }
                }
                mRecordInfoList.add(title);
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSelectDate(String date) {
        if (!TextUtils.isEmpty(date)){
            showToast(date);
            if (mTimeType == 0){
                mStartTime = date;
                mTimeStartTv.setText(mStartTime);
            }else {
                mEndTime = date;
                mTimeEndTv.setText(mEndTime);
            }
        }
    }
}
