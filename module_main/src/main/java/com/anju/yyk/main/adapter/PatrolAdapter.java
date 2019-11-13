package com.anju.yyk.main.adapter;

import android.widget.ImageView;
import android.widget.Switch;

import com.anju.yyk.common.base.BaseApplication;
import com.anju.yyk.common.entity.response.PatrolResponse;
import com.anju.yyk.common.imageloader.ImageLoader;
import com.anju.yyk.common.widget.ToggleButton;
import com.anju.yyk.main.R;
import com.anju.yyk.main.di.component.DaggerMainComponent;
import com.anju.yyk.main.entity.PatrolEntity;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import javax.inject.Inject;

public class PatrolAdapter extends BaseMultiItemQuickAdapter<PatrolResponse.ListBean, BaseViewHolder> {

    private List<PatrolEntity> mList;

    @Inject
    ImageLoader mImageLoader;

    public PatrolAdapter(List<PatrolResponse.ListBean> data){
        super(data);
        DaggerMainComponent.builder()
                .appComponent(BaseApplication.getInstance().getAppComponent())
                .build()
                .inject(this);
        addItemType(PatrolResponse.TITLE_TYPE, R.layout.home_item_patrol_title);
        addItemType(PatrolResponse.DEVICE_TYPE, R.layout.home_item_patrol_checkstatus);
        addItemType(PatrolResponse.PATROL_TYPE, R.layout.home_item_patrol_detail);
        addItemType(PatrolResponse.PHOTO_TYPE, R.layout.home_item_patrol_photo);
        addItemType(PatrolResponse.ADD_PHOTO_TYPE, R.layout.home_item_patrol_add);
    }

    @Override
    protected void convert(BaseViewHolder helper, PatrolResponse.ListBean item) {
        switch (helper.getItemViewType()){
            case PatrolResponse.TITLE_TYPE:
                helper.setText(R.id.tv_title, item.getTitle());
                break;
            case PatrolResponse.DEVICE_TYPE:
                helper.setText(R.id.tv_title, item.getTitle());
                ToggleButton toggleButton = helper.getView(R.id.switch_btn);
                if (item.isRight()) {
                    toggleButton.setToggleOn();
                } else {
                    toggleButton.setToggleOff();
                }
                break;
            case PatrolResponse.PATROL_TYPE:
                helper.setText(R.id.tv_detail, item.getDes());
                break;
            case PatrolResponse.ADD_PHOTO_TYPE:
                helper.addOnClickListener(R.id.iv_add);
                break;
            case PatrolResponse.PHOTO_TYPE:
                ImageView photo = helper.getView(R.id.iv_photo);
                mImageLoader.loadImgByUrl(item.getPhotoPath(), photo);
                break;
        }
    }
}
