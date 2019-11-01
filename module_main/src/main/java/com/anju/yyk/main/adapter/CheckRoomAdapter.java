package com.anju.yyk.main.adapter;

import android.text.Spannable;
import android.text.SpannableString;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.anju.yyk.common.entity.response.CheckRoomListResponse;
import com.anju.yyk.common.widget.ToggleButton;
import com.anju.yyk.main.R;
import com.anju.yyk.main.entity.CheckRoomEntity;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;
import org.angmarch.views.SpinnerTextFormatter;

import java.util.ArrayList;
import java.util.List;

public class CheckRoomAdapter extends BaseMultiItemQuickAdapter<CheckRoomListResponse.ListBean, BaseViewHolder> {

    /** 文本*/
    public static final int CHECKROOM_TXT_TYPE = 0;
    /** 下拉*/
    public static final int SPINNER_TYPE = 1;
    /** 开关*/
    public static final int TOGGLE_TYPE = 2;

    private CheckRoomAdapterCallback mCallback;

    public CheckRoomAdapter(List<CheckRoomListResponse.ListBean> data){
        super(data);
        addItemType(CHECKROOM_TXT_TYPE, R.layout.home_item_checkroom0);
        addItemType(SPINNER_TYPE, R.layout.home_item_checkroom1);
        addItemType(TOGGLE_TYPE, R.layout.home_item_checkroom2);
    }

    @Override
    protected void convert(BaseViewHolder helper, CheckRoomListResponse.ListBean item) {
        switch (helper.getItemViewType()){
            case CHECKROOM_TXT_TYPE:
                helper.setText(R.id.tv_title, item.getTitle());
                break;
            case SPINNER_TYPE:
                helper.setText(R.id.tv_title, item.getTitle());
                NiceSpinner np = helper.getView(R.id.sp_type);
                List<CheckRoomListResponse.ListBean.OptionBean> options = item.getOption();
                if (options != null && options.size() > 0){
                    SpinnerTextFormatter textFormatter = new SpinnerTextFormatter<CheckRoomListResponse.ListBean.OptionBean>() {
                        @Override
                        public Spannable format(CheckRoomListResponse.ListBean.OptionBean item) {
                            return new SpannableString(item.getMingcheng() + "|" + item.getZhi());
                        }
                    };
                    np.setSpinnerTextFormatter(textFormatter);
                    np.setSelectedTextFormatter(textFormatter);
                    np.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
                        @Override
                        public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                            CheckRoomListResponse.ListBean.OptionBean item = (CheckRoomListResponse.ListBean.OptionBean) np.getSelectedItem();
                            if (mCallback != null)
                                mCallback.selectedItem(position, item.getZhi());
                        }
                    });
                    np.attachDataSource(options);
                }
                break;
            case TOGGLE_TYPE:
                helper.setText(R.id.tv_title, item.getTitle());
                ToggleButton toggleBtn = helper.getView(R.id.switch_btn);
                toggleBtn.setOnToggleChanged(new ToggleButton.OnToggleChanged() {

                    @Override
                    public void onToggle(boolean on) {
                        if (mCallback != null)
                            mCallback.toggle(helper.getAdapterPosition(), on, item.getLieming());
                        }
//                        isAll = on;
//                        if (isAll){
//                            mChooseEmployeeLayout.setClickable(false);
//                            mEmployeeTv.setTextColor(getColorFormResource(R.color.text_mid_gray));
//                        }else {
//                            mChooseEmployeeLayout.setClickable(true);
//                            mEmployeeTv.setTextColor(getColorFormResource(R.color.global_black));
//                        }
                });
                int status = item.getToggleStatus();
                if (status == 0){
                    // 开
                    toggleBtn.setToggleOn();
                }else {
                    toggleBtn.setToggleOff();
                }
                break;
        }
    }

    public void setAdapterCallBack(CheckRoomAdapterCallback callback){
        this.mCallback = callback;
    }

    public interface CheckRoomAdapterCallback{
        void toggle(int position, boolean isCheck, String id);
        void selectedItem(int position, String itemText);
    }
}
