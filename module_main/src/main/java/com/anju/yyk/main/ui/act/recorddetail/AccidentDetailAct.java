package com.anju.yyk.main.ui.act.recorddetail;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.anju.yyk.common.app.arouter.RouterConstants;
import com.anju.yyk.common.app.arouter.RouterKey;
import com.anju.yyk.common.base.BaseMvpActivity;
import com.anju.yyk.common.entity.response.AccidentDetailResponse;
import com.anju.yyk.common.entity.response.CareDetailResponse;
import com.anju.yyk.common.entity.response.CheckRoomDetailResponse;
import com.anju.yyk.common.utils.AppUtil;
import com.anju.yyk.common.widget.itemdecoration.SpacesItemDecoration;
import com.anju.yyk.main.R;
import com.anju.yyk.main.R2;
import com.anju.yyk.main.adapter.AccidentDetailAdapter;
import com.anju.yyk.main.adapter.RecordDetailAdapter;
import com.anju.yyk.main.adapter.TakePhotoAdapter;
import com.anju.yyk.main.entity.PhotoEntity;
import com.anju.yyk.main.entity.RecordInfoEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@Route(path = RouterConstants.ACT_URL_ACCIDENT_DETAIL)
public class AccidentDetailAct extends BaseMvpActivity<RecordDetailPresenter, RecordDetailModel> implements IRecordDetailContract.IRecordDetailView{
    @BindView(R2.id.iv_sex)
    ImageView mSexIv;

    @BindView(R2.id.tv_age)
    TextView mAgeTv;

    @BindView(R2.id.tv_name)
    TextView mNameTv;

    @BindView(R2.id.tv_bed_number)
    TextView mNumberBed;

    @BindView(R2.id.tv_care_type)
    TextView mCareTypeTv;

    @BindView(R2.id.tv_accident_content)
    TextView mContentTv;

    @BindView(R2.id.recyclerView)
    RecyclerView mRecyclerView;

    @Autowired(name = RouterKey.RECORD_DETAIL_TAG)
    public RecordInfoEntity mInfoEntity;

    private TakePhotoAdapter mAdapter;
    private List<PhotoEntity> photos = new ArrayList<>();

    /*private AccidentDetailAdapter mAdapter;
    private List<AccidentDetailResponse.ListBean> mList = new ArrayList<>();*/

    @Override
    protected int getLayoutId() {
        return R.layout.home_act_accident_detail;
    }

    @Override
    protected void init() {
        ARouter.getInstance().inject(this);
        setToolbarTopic(R.string.home_accident_detail);
        initRecyclerView();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        if (mInfoEntity != null){
            if (mInfoEntity.getSex() == 0){
                mSexIv.setImageResource(R.mipmap.home_ic_famale);
            }else {
                mSexIv.setImageResource(R.mipmap.home_ic_male);
            }
            mNameTv.setText(mInfoEntity.getName());
            mAgeTv.setText(mInfoEntity.getAge() + "岁");
            mNumberBed.setText(mInfoEntity.getBedId() + "床");
            mCareTypeTv.setText(mInfoEntity.getCareType());
        }
        mPresenter.accidentDetail(mInfoEntity.getId());
    }

    @Override
    protected void setupActivityComponent() {

    }

    @Override
    public boolean isRegisterEventBus() {
        return false;
    }

    private void initRecyclerView(){
        /*mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(0
                , AppUtil.dip2px(mActivity, 1), AppUtil.getColor(mActivity, R.color.common_divder_color)));

        mAdapter = new AccidentDetailAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);*/

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        GridLayoutManager manager = new GridLayoutManager(mActivity, 3);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(0
                , AppUtil.dip2px(mActivity, 1), AppUtil.getColor(mActivity, R.color.common_divder_color)));

        mAdapter = new TakePhotoAdapter(photos);
        mAdapter.setSpanSizeLookup((gridLayoutManager, position) -> photos.get(position).getSpanSize());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setVisibility(View.GONE);
    }


    @Override
    public void checkRoomDetailSucc(CheckRoomDetailResponse response) {

    }

    @Override
    public void careDetailSucc(CareDetailResponse response) {

    }

    @Override
    public void accidentDetailSucc(AccidentDetailResponse response) {
        List<AccidentDetailResponse.ListBean> listBeans = response.getList();
        if (listBeans != null && listBeans.size() > 0){
            mAgeTv.setText(listBeans.get(0).getNianling() + "岁");
            mNumberBed.setText(listBeans.get(0).getChuangwei() + "床");
            mCareTypeTv.setText(listBeans.get(0).getHulijibie());
            mContentTv.setText(listBeans.get(0).getContent());
            if ("女".equals(listBeans.get(0).getSex())) {
                mSexIv.setImageResource(R.mipmap.home_ic_famale);
            } else {
                mSexIv.setImageResource(R.mipmap.home_ic_male);
            }
            /*mList.clear();
            mList.addAll(listBeans);
            mAdapter.notifyDataSetChanged();*/
            List<AccidentDetailResponse.Photo> photoList = response.getTupian();
            if (photoList != null && photoList.size() > 0) {
                for (AccidentDetailResponse.Photo photo : photoList) {
                    PhotoEntity photoEntity = new PhotoEntity(PhotoEntity.NORMAL_TYPE);
                    photoEntity.setPhotoPath(photo.getLujing());
                    photoEntity.setSpanSize(1);
                    photos.add(photoEntity);
                }
                mAdapter.notifyDataSetChanged();
                mRecyclerView.setVisibility(View.VISIBLE);
            }
        }
    }
}
