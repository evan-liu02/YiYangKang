package com.anju.yyk.main.adapter;

import android.view.View;

import com.anju.yyk.common.utils.eventbus.Event;
import com.anju.yyk.common.utils.eventbus.EventBusUtil;
import com.anju.yyk.common.utils.eventbus.EventConstant;
import com.anju.yyk.main.R;
import com.anju.yyk.main.entity.RecordInfoEntity;
import com.anju.yyk.main.entity.RecordTitleEntity;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class CheckRoomRecordAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    public static final int TYPE_TITLE = 0;
    public static final int TYPE_INFO = 1;

    public CheckRoomRecordAdapter(List<MultiItemEntity> data){
        super(data);
        addItemType(TYPE_TITLE, R.layout.home_item_record_title);
        addItemType(TYPE_INFO, R.layout.home_item_record_info);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()){
            case TYPE_TITLE:
                RecordTitleEntity title = (RecordTitleEntity) item;
                helper.setText(R.id.tv_time, title.getTitle());
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int pos = helper.getAdapterPosition();
                        if (title.isExpanded()){
                            collapse(pos);
                        }else {
                            expand(pos);
                        }
                        notifyDataSetChanged();
                        Event event = new Event(EventConstant.EventCode.REFRESH_RECORDLIST_FRG);
                        EventBusUtil.sendEvent(event);
                    }
                });
                break;
            case TYPE_INFO:
                RecordInfoEntity infoEntity = (RecordInfoEntity) item;
                helper.setText(R.id.tv_name, infoEntity.getName())
                        .setText(R.id.tv_bed_number, infoEntity.getBedId() + "床")
                        .addOnClickListener(R.id.tv_scan_detail);
                break;
        }
    }
}
