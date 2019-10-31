package com.anju.yyk.main.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.anju.yyk.main.R;

public class AddTipDialog extends Dialog implements View.OnClickListener {

    private AddTipCallback mCallback;
    private Context mContext;

    private ImageView mCloseIv;
    private EditText mTipContentEdt;
    private Button mRecordBtn;
    private Button mCancelBtn;
    private Button mCommitBtn;

    public AddTipDialog(@NonNull Context context) {
        super(context, R.style.home_DialogTheme);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView(){
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.home_act_addtip, null);

        mCloseIv = view.findViewById(R.id.iv_close);
        mCloseIv.setOnClickListener(this);
        mTipContentEdt = view.findViewById(R.id.edt_tip_content);
        mRecordBtn = view.findViewById(R.id.btn_record);
        mRecordBtn.setOnClickListener(this);
        mCancelBtn = view.findViewById(R.id.btn_cancel);
        mCommitBtn = view.findViewById(R.id.btn_commit);

        setContentView(view);

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = mContext.getResources().getDisplayMetrics(); // 获取屏幕宽、高
        lp.width = (int) (d.widthPixels * 1);
        lp.height = (int) (d.heightPixels * 1);
        dialogWindow.setAttributes(lp);
    }

    public void setAddTipCallback(AddTipCallback callback){
        this.mCallback = callback;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_close){
            dismiss();
        }else if (v.getId() == R.id.btn_record){
            if (mCallback != null)
                mCallback.addAudio();
        }
    }

    public interface AddTipCallback{
        void addAudio();
        void commitAudio();
    }
}
