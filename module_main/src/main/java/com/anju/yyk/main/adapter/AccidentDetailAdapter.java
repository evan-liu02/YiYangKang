package com.anju.yyk.main.adapter;

import com.anju.yyk.common.entity.response.AccidentDetailResponse;
import com.anju.yyk.main.R;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;
/**
 * 
 * @author LeoWang
 * 
 * @Package com.anju.yyk.main.adapter
 * 
 * @Description 事故详情列表适配器
 * 
 * @Date 2019/10/24 17:20
 * 
 * @modify:
 */
public class AccidentDetailAdapter extends BaseMultiItemQuickAdapter<AccidentDetailResponse.ListBean, BaseViewHolder> {

    public AccidentDetailAdapter(List<AccidentDetailResponse.ListBean> data){
        super(data);
        addItemType(AccidentDetailResponse.NORMAL_TYPE, R.layout.home_item_accident);
    }

    @Override
    protected void convert(BaseViewHolder helper, AccidentDetailResponse.ListBean item) {
        switch (helper.getItemViewType()){
            case AccidentDetailResponse.NORMAL_TYPE:
                helper.setText(R.id.tv_name, item.getName())
                        .setText(R.id.tv_age, item.getNianling())
                        .setText(R.id.tv_bedid, item.getChuangwei() + "床")
                        .setText(R.id.tv_tag, item.getHulijibie())
                        .setText(R.id.tv_confirm, item.getContent());
                break;
        }
    }
}
