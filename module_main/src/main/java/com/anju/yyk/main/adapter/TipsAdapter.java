package com.anju.yyk.main.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.anju.yyk.common.app.Constants;
import com.anju.yyk.common.entity.response.TipsListResponse;
import com.anju.yyk.main.R;
import com.anju.yyk.main.entity.TipsEntity;
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
 * @Description 提醒适配器
 * 
 * @Date 2019/10/12 15:34
 * 
 * @modify:
 */
public class TipsAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    private TipsCallBack mCallback;

    public TipsAdapter(List<MultiItemEntity> data){
        super(data);
        addItemType(TipsListResponse.TYPE_TITLE, R.layout.home_item_scantip);
        addItemType(TipsListResponse.TYPE_INFO, R.layout.home_item_record);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()){
            case TipsEntity.TYPE_TITLE:
                TipsEntity tipsInfo = (TipsEntity) item;
                helper.setText(R.id.tv_date, tipsInfo.getDate())
                        .setText(R.id.tv_workername, "护理员：" + tipsInfo.getHugong())
                        .setText(R.id.tv_time, tipsInfo.getTime())
                        .setText(R.id.tv_readername, tipsInfo.getYidurenyuan())
                        .addOnClickListener(R.id.tv_play);
                if (TextUtils.isEmpty(tipsInfo.getContent())){
                    helper.setText(R.id.tv_content, "暂无内容");
                }else {
                    helper.setText(R.id.tv_content, tipsInfo.getContent());
                }

                if (TextUtils.isEmpty(tipsInfo.getYidurenyuan())){
                    helper.setGone(R.id.tv_111, false);
                    helper.setGone(R.id.tv_readername, false);
                }else {
                    helper.setGone(R.id.tv_111, true);
                    helper.setGone(R.id.tv_readername, true);
                }

                if (tipsInfo.getStatus() == 0){
                    // 已读
                    helper.getView(R.id.tv_read).setVisibility(View.VISIBLE);
                    helper.getView(R.id.tv_unread).setVisibility(View.GONE);
                    helper.setImageResource(R.id.iv_read_status, R.mipmap.home_ic_read);
                }else {
                    helper.getView(R.id.tv_read).setVisibility(View.GONE);
                    helper.getView(R.id.tv_unread).setVisibility(View.VISIBLE);
                    helper.setImageResource(R.id.iv_read_status, R.mipmap.home_ic_unread);
                    helper.getView(R.id.iv_read_status).setOnClickListener(v -> {
                        if (mCallback != null)
                            mCallback.clickRead(helper.getAdapterPosition(), tipsInfo.getId());
                    });
                }

                if (tipsInfo.getLuyin() != null && tipsInfo.getLuyin().size() > 0){
                    helper.setGone(R.id.tv_play, true);
                }else {
                    helper.setGone(R.id.tv_play, false);
                }

                helper.itemView.setOnClickListener(view -> {
                    int pos = helper.getAdapterPosition();
                    if (tipsInfo.isExpanded()){
                        collapse(pos);
                    }else {
                        expand(pos);
                    }
                    notifyDataSetChanged();
                });
                helper.getView(R.id.tv_play).setOnClickListener(v -> {
                    int pos = helper.getAdapterPosition();
                    if (tipsInfo.isExpanded()){
                        collapse(pos);
                    }else {
                        expand(pos);
                    }
                    notifyDataSetChanged();
                });

                break;
            case TipsEntity.TYPE_INFO:
                TipsEntity.LuyinBean audio = (TipsEntity.LuyinBean) item;
                String[] temp = audio.getLujing().replaceAll("\\\\", "/").split("/");
                if (temp.length > 0){
                    String audioName = temp[temp.length - 1];
//                    helper.setText(R.id.tv_audio_title, audioName);
                    helper.setText(R.id.tv_audio_title, "护理语音");
                    helper.getView(R.id.iv_play_audio).setOnClickListener(v -> {
                        if (mCallback != null)
                            if (!audio.getLujing().startsWith("http")) {
                                mCallback.clickPlay(helper.getView(R.id.iv_play_audio),Constants.AUDIO_HEAD + audio.getLujing());
                            } else {
                                mCallback.clickPlay(helper.getView(R.id.iv_play_audio), audio.getLujing());
                            }
                    });

                    helper.getView(R.id.iv_audio_download).setOnClickListener(v -> {
                        if (mCallback != null)
                            if (!audio.getLujing().startsWith("http")) {
                                mCallback.downLoadAudio(Constants.AUDIO_HEAD + audio.getLujing());
                            } else {
                                mCallback.downLoadAudio(audio.getLujing());
                            }
                    });
                }
                break;
        }
    }

    public void setCallback(TipsCallBack callback){
        this.mCallback = callback;
    }

    public interface TipsCallBack{
        void clickPlay(ImageView image, String audioUrl);
        void downLoadAudio(String audioUrl);
        void clickRead(int position, String zhuyi_id);
    }
}
