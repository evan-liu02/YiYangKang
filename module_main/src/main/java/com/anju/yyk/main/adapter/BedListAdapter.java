package com.anju.yyk.main.adapter;

import android.view.View;

import com.anju.yyk.common.entity.response.PersonListResponse;
import com.anju.yyk.common.utils.eventbus.Event;
import com.anju.yyk.common.utils.eventbus.EventBusUtil;
import com.anju.yyk.common.utils.eventbus.EventConstant;
import com.anju.yyk.main.R;
import com.anju.yyk.main.entity.BedInfoEntity;
import com.anju.yyk.main.entity.BedTitleEntity;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;
/**
 *
 * @author LeoWang
 *
 * @Package com.anju.yyk.main.adapter
 *
 * @Description 首页列表适配器
 *
 * @Date 2019/9/5 18:46
 *
 * @modify:
 */
public class BedListAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int TYPE_TITLE = 0;
    public static final int TYPE_INFO = 1;

    private BedListCallback mCallback;

    public BedListAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_TITLE, R.layout.home_item_expandle_title);
        addItemType(TYPE_INFO, R.layout.home_item_expandle_info);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()){
            case TYPE_TITLE:
                BedTitleEntity title = (BedTitleEntity) item;
                helper.setText(R.id.tv_title, title.getTitle());
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
                        Event event = new Event(EventConstant.EventCode.REFRESH_PAGE);
                        EventBusUtil.sendEvent(event);
                    }
                });
                break;
            case TYPE_INFO:
                PersonListResponse.ListBean infoEntity = (PersonListResponse.ListBean) item;
                if (infoEntity.getIstop() == 1){
                    // 置顶
                    helper.setText(R.id.tv_fixedtop, "取消置顶");
                }else {
                    helper.setText(R.id.tv_fixedtop, "置顶");
                }
                helper.getView(R.id.tv_fixedtop).setOnClickListener(v -> {
                    if (mCallback != null)
                        mCallback.clickTop(infoEntity);
                });

                helper.getView(R.id.tv_scan_tip).setOnClickListener(v -> {
                    if (mCallback != null)
                        mCallback.scanTips(infoEntity);
                });

                helper.getView(R.id.tv_add_tip).setOnClickListener(v -> {
                    if (mCallback != null)
                        mCallback.addTips(infoEntity);
                });

                helper.itemView.setOnClickListener(v -> {
                    if (mCallback != null)
                        mCallback.clickItem(infoEntity);
                });

                if (infoEntity.getSex() == 2){
                    helper.setImageResource(R.id.iv_sex, R.mipmap.home_ic_famale);
                }else {
                    helper.setImageResource(R.id.iv_sex, R.mipmap.home_ic_male);
                }
                helper.setText(R.id.tv_age, infoEntity.getNianling())
                        .setText(R.id.tv_name, infoEntity.getName())
                        .setText(R.id.tv_bedid, infoEntity.getChuangwei() + "床")
                        .setText(R.id.tv_tag, infoEntity.getHulijibie());
                break;
        }
    }

    public void setBedListCallback(BedListCallback callback){
        this.mCallback = callback;
    }

    public interface BedListCallback{
        void clickTop(PersonListResponse.ListBean bean);
        void scanTips(PersonListResponse.ListBean bean);
        void addTips(PersonListResponse.ListBean bean);
        void clickItem(PersonListResponse.ListBean bean);
    }
}
