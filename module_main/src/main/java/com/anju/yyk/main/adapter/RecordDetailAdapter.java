package com.anju.yyk.main.adapter;

import com.anju.yyk.common.entity.response.CareDetailResponse;
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
 * @Description 护理详情
 *
 * @Date 2019/10/24 10:27
 *
 * @modify:
 */
public class RecordDetailAdapter extends BaseMultiItemQuickAdapter<CareDetailResponse.ListBean, BaseViewHolder> {

    public RecordDetailAdapter(List<CareDetailResponse.ListBean> data){
        super(data);
        addItemType(CareDetailResponse.NORMAL_TYPE, R.layout.home_item_recorddetail);
    }

    @Override
    protected void convert(BaseViewHolder helper, CareDetailResponse.ListBean item) {
        switch (helper.getItemViewType()){
            case CareDetailResponse.NORMAL_TYPE:
                helper.setText(R.id.tv_event, item.getXiangqing())
                        .setText(R.id.tv_operater, "护理员：" + item.getName())
                        .setText(R.id.tv_time, item.getTime());
                break;
        }
    }
}
