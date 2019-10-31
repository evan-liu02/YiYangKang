package com.anju.yyk.main.adapter;

import android.widget.ImageView;

import com.anju.yyk.common.base.BaseApplication;
import com.anju.yyk.common.imageloader.ImageLoader;
import com.anju.yyk.main.R;
import com.anju.yyk.main.di.component.DaggerMainComponent;
import com.anju.yyk.main.entity.PhotoEntity;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import javax.inject.Inject;

public class TakePhotoAdapter extends BaseMultiItemQuickAdapter<PhotoEntity, BaseViewHolder> {

    @Inject
    ImageLoader mImageLoader;

    public TakePhotoAdapter(List<PhotoEntity> data){
        super(data);
        addItemType(PhotoEntity.NORMAL_TYPE, R.layout.home_item_patrol_photo);
        addItemType(PhotoEntity.ADD_TYPE, R.layout.home_item_patrol_add);
        DaggerMainComponent.builder()
                .appComponent(BaseApplication.getInstance().getAppComponent())
                .build()
                .inject(this);
    }


    @Override
    protected void convert(BaseViewHolder helper, PhotoEntity item) {
        switch (helper.getItemViewType()){
            case PhotoEntity.NORMAL_TYPE:
                ImageView imageView = helper.getView(R.id.iv_photo);
                mImageLoader.loadImgByLocal(item.getPhotoPath(), imageView);
                break;
            case PhotoEntity.ADD_TYPE:
                break;
        }
    }
}
