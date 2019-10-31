package com.anju.yyk.main.ui.frg.patrol;

import android.view.View;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anju.yyk.common.base.BaseMvpFragment;
import com.anju.yyk.common.entity.response.PatrolResponse;
import com.anju.yyk.common.utils.AppUtil;
import com.anju.yyk.common.widget.itemdecoration.SpacesItemDecoration;
import com.anju.yyk.main.R;
import com.anju.yyk.main.R2;
import com.anju.yyk.main.adapter.PatrolAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 *
 * @author LeoWang
 *
 * @Package com.anju.yyk.main.ui.frg.patrol
 *
 * @Description 安全巡查
 *
 * @Date 2019/9/5 15:21
 *
 * @modify:
 */
public class PatrolFrg extends BaseMvpFragment<PatrolPresenter, PatrolModel> implements IPatrolContract.IPatrolView{

    @BindView(R2.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R2.id.fab)
    FloatingActionButton mFab;

    private String[] mDevicesTitle = null;

    private List<PatrolResponse.ListBean> mPatrols = new ArrayList<>();

    private PatrolAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.home_frg_patrol;
    }

    @Override
    public void init() {
        mDevicesTitle = getResources().getStringArray(R.array.home_devices_title);
//        prepareData();
        initRecyclerView();
    }

    @Override
    public void initListener() {
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("拍照");
            }
        });
    }

    @Override
    protected void initData() {
        mPresenter.getPatrol();
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
        GridLayoutManager manager = new GridLayoutManager(mActivity, 3);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(0
                , AppUtil.dip2px(mActivity, 1), AppUtil.getColor(mActivity, R.color.common_divder_color)));

        mAdapter = new PatrolAdapter(mPatrols);
        mAdapter.setSpanSizeLookup((gridLayoutManager, position) -> mPatrols.get(position).getSpanSize());
        mRecyclerView.setAdapter(mAdapter);
    }

//    private void prepareData(){
//        PatrolEntity title1 = new PatrolEntity(PatrolEntity.TITLE_TYPE);
//        title1.setTitle("设备情况");
//        title1.setSpanSize(3);
//        mPatrols.add(title1);
//
//        for (String s : mDevicesTitle) {
//            PatrolEntity device = new PatrolEntity(PatrolEntity.DEVICE_TYPE);
//            device.setTitle(s);
//            device.setRight(false);
//            device.setSpanSize(3);
//            mPatrols.add(device);
//        }
//
//        PatrolEntity title2 = new PatrolEntity(PatrolEntity.TITLE_TYPE);
//        title2.setTitle("巡查情况");
//        title2.setSpanSize(3);
//        mPatrols.add(title2);
//
//        PatrolEntity detail1 = new PatrolEntity(PatrolEntity.PATROL_TYPE);
//        detail1.setDes("今日检查完毕");
//        detail1.setSpanSize(3);
//        mPatrols.add(detail1);
//
//        PatrolEntity title3 = new PatrolEntity(PatrolEntity.TITLE_TYPE);
//        title3.setTitle("添加照片");
//        title3.setSpanSize(3);
//        mPatrols.add(title3);
//
//        PatrolEntity add = new PatrolEntity(PatrolEntity.ADD_PHOTO_TYPE);
//        add.setSpanSize(1);
//        mPatrols.add(add);
//
//        PatrolEntity photo1 = new PatrolEntity(PatrolEntity.PHOTO_TYPE);
//        photo1.setImgUrl("http://pic13.nipic.com/20110409/7119492_114440620000_2.jpg");
//        photo1.setSpanSize(1);
//        mPatrols.add(photo1);
//
//        PatrolEntity photo2 = new PatrolEntity(PatrolEntity.PHOTO_TYPE);
//        photo2.setImgUrl("http://img2.imgtn.bdimg.com/it/u=3976365452,3683195683&fm=214&gp=0.jpg");
//        photo2.setSpanSize(1);
//        mPatrols.add(photo2);
//
//        PatrolEntity photo3 = new PatrolEntity(PatrolEntity.PHOTO_TYPE);
//        photo3.setImgUrl("http://pic31.nipic.com/20130728/8886898_150339538164_2.jpg");
//        photo3.setSpanSize(1);
//        mPatrols.add(photo3);
//    }

    @Override
    public void patrolSucc(PatrolResponse response) {
        List<PatrolResponse.ListBean> listBeans = response.getList();
        mPatrols.clear();
        PatrolResponse.ListBean title1 = new PatrolResponse.ListBean();
        title1.setTitle("设备情况");
        title1.setmItemType(PatrolResponse.TITLE_TYPE);
        title1.setSpanSize(3);
        mPatrols.add(title1);

        if (listBeans != null && listBeans.size() > 0){
            for (PatrolResponse.ListBean bean : listBeans){
                bean.setSpanSize(3);
                bean.setmItemType(PatrolResponse.DEVICE_TYPE);
                mPatrols.add(bean);
            }
        }

        PatrolResponse.ListBean title2 = new PatrolResponse.ListBean();
        title1.setTitle("巡查情况");
        title1.setmItemType(PatrolResponse.TITLE_TYPE);
        title1.setSpanSize(3);
        mPatrols.add(title2);

        PatrolResponse.ListBean des1 = new PatrolResponse.ListBean();
        title1.setDes("今日检查完毕");
        title1.setmItemType(PatrolResponse.PATROL_TYPE);
        title1.setSpanSize(3);
        mPatrols.add(des1);

        PatrolResponse.ListBean title3 = new PatrolResponse.ListBean();
        title1.setTitle("添加照片");
        title1.setmItemType(PatrolResponse.TITLE_TYPE);
        title1.setSpanSize(3);
        mPatrols.add(title3);

        mAdapter.notifyDataSetChanged();
    }
}
