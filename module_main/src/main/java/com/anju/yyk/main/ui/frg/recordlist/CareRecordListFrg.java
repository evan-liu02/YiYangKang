package com.anju.yyk.main.ui.frg.recordlist;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.anju.yyk.common.app.arouter.RouterConstants;
import com.anju.yyk.common.app.arouter.RouterKey;
import com.anju.yyk.common.base.BaseMvpFragment;
import com.anju.yyk.common.entity.response.PersonListResponse;
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
import com.anju.yyk.main.utils.PersonInfoHelper;
import com.anju.yyk.main.widget.DatePickerDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 
 * @author LeoWang
 * 
 * @Package com.anju.yyk.main.ui.frg.recordlist
 * 
 * @Description 护理记录
 * 
 * @Date 2019/10/21 14:05
 * 
 * @modify:
 */
public class CareRecordListFrg extends BaseMvpFragment<RecordPresenter, RecordListModel>
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
    private String selectedName = "所有人";

    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

    @Override
    public int getLayoutId() {
        return R.layout.home_frg_recordlist;
    }

    @Override
    public void init() {
        mManTypeNS.attachDataSource(dataset);
        if (PersonInfoHelper.personList != null) {
            List<PersonListResponse.ListBean> personList = PersonInfoHelper.personList;
            for (PersonListResponse.ListBean personBean : personList) {
                String item = "[" + +personBean.getChuangwei() + "床" + "]" + personBean.getName();
                if (!dataset.contains(item)) {
                    dataset.add(item);
                }
            }
        }
        initRecyclerView();
    }

    @Override
    public void initListener() {
        mManTypeNS.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
                int index = item.indexOf("]");
                if (index != -1) {
                    selectedName = item.substring(index + 1);
                    Log.e("Test", selectedName);
                } else {
                    selectedName = "所有人";
                }
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

        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        mEndTime = dateFormat.format(date);
        calendar.add(Calendar.DAY_OF_MONTH, -6);
        date = calendar.getTime();
        mStartTime = dateFormat.format(date);
        mTimeStartTv.setText(mStartTime);
        mTimeEndTv.setText(mEndTime);
        mPresenter.getCareRecord("0", mStartTime, mEndTime);
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
                mPresenter.getCareRecord("0", mStartTime, mEndTime);
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
        mRecordInfoList.clear();
        List<RecordResponse.ListBean> list = response.getList();
        if (list != null && list.size() > 0){
            for (RecordResponse.ListBean bean : list){
                RecordTitleEntity title = new RecordTitleEntity();
                title.setTitle(bean.getDate());
                List<RecordResponse.ListBean.RecordsBean> records = bean.getRecords();
                if (records != null && records.size() > 0){
                    for (RecordResponse.ListBean.RecordsBean record : records){
                        RecordInfoEntity info = new RecordInfoEntity();
                        info.setBedId(record.getBedid());
                        info.setLaoren(record.getLaoren());
                        info.setName(record.getName());
                        info.setShijian(record.getShijian());
                        if ("所有人".equals(selectedName) || selectedName.equals(record.getName())) {
                            title.addSubItem(info);
                        }
                    }
                }
                if (title.getSubItems() != null && title.getSubItems().size() > 0) {
                    mRecordInfoList.add(title);
                    Collections.sort(title.getSubItems(), new Comparator<RecordInfoEntity>() {

                        @Override
                        public int compare(RecordInfoEntity recordInfoEntity, RecordInfoEntity t1) {
                            String dateStr1 = recordInfoEntity.getShijian();
                            String dateStr2 = t1.getShijian();
                            try {
                                long time1 = Objects.requireNonNull(dateFormat.parse(dateStr1)).getTime();
                                long time2 = Objects.requireNonNull(dateFormat.parse(dateStr2)).getTime();
                                if (time1 > time2) {
                                    return -1;
                                } else if (time1 < time2) {
                                    return 1;
                                } else {
                                    return 0;
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            return 0;
                        }
                    });
                }
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getCheckRoomSucc(RecordResponse response) {

    }

    @Override
    public void getaccidentSucc(RecordResponse response) {

    }

    @Override
    public void onSelectDate(String date) {
        if (!TextUtils.isEmpty(date)){
//            showToast(date);
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
