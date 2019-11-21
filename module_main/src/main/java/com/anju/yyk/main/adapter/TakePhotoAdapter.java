package com.anju.yyk.main.adapter;

import android.view.View;
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
    private boolean showBtn;

    public TakePhotoAdapter(List<PhotoEntity> data, boolean showBtn){
        super(data);
        this.showBtn = showBtn;
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
                if (item.getPhotoPath().startsWith("http")) {
                    mImageLoader.loadImgByUrl(item.getPhotoPath(), imageView);
                } else {
                    mImageLoader.loadImgByLocal(item.getPhotoPath(), imageView);
                }
                View deleteBtn = helper.getView(R.id.btn_delete);
                if (showBtn) {
                    deleteBtn.setVisibility(View.VISIBLE);
                }
                deleteBtn.setOnClickListener(view -> {
                    getData().remove(item);
                    if (callback != null) {
                        callback.photoDelete(helper.getAdapterPosition());
                    }
                    notifyDataSetChanged();
                });
                break;
            case PhotoEntity.ADD_TYPE:
                break;
        }
    }

    private PhotoCallback callback;

    public void setCallback(PhotoCallback callback) {
        this.callback = callback;
    }

    public interface PhotoCallback {
        void photoDelete(int position);
    }
}
