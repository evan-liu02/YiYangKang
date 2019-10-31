package com.anju.yyk.main.adapter;

import com.anju.yyk.common.entity.response.CheckRoomDetailResponse;
import com.anju.yyk.main.R;
import com.anju.yyk.main.entity.RecordDetailEntity;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;
/**
 * 
 * @author LeoWang
 * 
 * @Package com.anju.yyk.main.adapter
 * 
 * @Description 查房详情适配器
 * 
 * @Date 2019/10/24 10:31
 * 
 * @modify:
 */
public class CheckRoomDetailAdapter extends BaseMultiItemQuickAdapter<CheckRoomDetailResponse.ListBean, BaseViewHolder> {

    public CheckRoomDetailAdapter(List<CheckRoomDetailResponse.ListBean> data){
        super(data);
        addItemType(CheckRoomDetailResponse.NORMAL_TYPE, R.layout.home_item_checkroomdetail);
    }

    @Override
    protected void convert(BaseViewHolder helper, CheckRoomDetailResponse.ListBean item) {
        switch (helper.getItemViewType()){
            case CheckRoomDetailResponse.NORMAL_TYPE:
                helper.setText(R.id.tv_title, item.getTitle())
                        .setText(R.id.tv_confirm, item.getContent());
                break;
        }
    }
}
