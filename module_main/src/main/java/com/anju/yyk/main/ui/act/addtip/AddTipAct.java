package com.anju.yyk.main.ui.act.addtip;

import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.anju.yyk.common.app.arouter.RouterConstants;
import com.anju.yyk.common.base.BaseActivity;
import com.anju.yyk.common.base.BaseMvpActivity;
import com.anju.yyk.common.base.BaseResponse;
import com.anju.yyk.common.utils.klog.KLog;
import com.anju.yyk.main.R;
import com.anju.yyk.main.R2;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = RouterConstants.ACT_URL_ADD_TIP)
public class AddTipAct extends BaseMvpActivity<AddTipPresenter, AddTipModel> implements IAddTipContract.IAddTipView{

    @BindView(R2.id.root)
    ConstraintLayout mRoot;

    @BindView(R2.id.iv_close)
    ImageView mCloseIv;

    @BindView(R2.id.edt_tip_content)
    EditText mTipContentEdt;

    @BindView(R2.id.btn_record)
    Button mRecordBtn;

    @BindView(R2.id.tv_record_time)
    TextView mRecordTimeTv;

    @BindView(R2.id.btn_cancel)
    Button mCancelBtn;

    @BindView(R2.id.btn_commit)
    Button mCommitBtn;

    private static final int MAX_VOICE_TIME = 60;
    private static final String AUDIO_DIR_NAME = "yyk_audio";

    private File mAudioDir;
    private String mFilePath;

    private boolean isStart;
    private MediaRecorder mr = null;

    /** 计时*/
    private int mTimeCount;
    private MyRunnable mRunnable;
    private Handler mHandler = new Handler();

    @Override
    protected int getLayoutId() {
        return R.layout.home_act_addtip;
    }

    @Override
    protected void init() {
        hideToolbar();
        initVoice();
        mRecordTimeTv.setText(String.format(getString(R.string.common_record_time), 0, 0));
    }

    private void initVoice() {
        mAudioDir = new File(Environment.getExternalStorageDirectory(), AUDIO_DIR_NAME);
        if (!mAudioDir.exists()) {
            mAudioDir.mkdirs();
        }
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    protected void setupActivityComponent() {

    }

    @Override
    public boolean isRegisterEventBus() {
        return false;
    }

    @OnClick({R2.id.iv_close, R2.id.btn_record, R2.id.btn_cancel, R2.id.btn_commit})
    public void onViewClicked(View v){
        if (v.getId() == R.id.iv_close){
            finish();
        }else if (v.getId() == R.id.btn_record){
            if(!isStart){
                startRecord();
                mRecordBtn.setText("停止录音");
                isStart = true;
            }else{
                stopRecord();
                mRecordBtn.setText("开始录音");
                isStart = false;
            }
        }else if (v.getId() == R.id.btn_cancel){
            finish();
        }else if (v.getId() == R.id.btn_commit){
            if (!TextUtils.isEmpty(mFilePath)){
                String content = mTipContentEdt.getText().toString().trim();
                mPresenter.uploadAudio(content, mFilePath);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void startRecord(){
        if(mr == null){
            mFilePath = "";
            File soundFile = new File(mAudioDir,System.currentTimeMillis()+".amr");
            if(!soundFile.exists()){
                try {
                    soundFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            mr = new MediaRecorder();
            mr.setAudioSource(MediaRecorder.AudioSource.MIC);  //音频输入源
            mr.setOutputFormat(MediaRecorder.OutputFormat.AMR_WB);   //设置输出格式
            mr.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);   //设置编码格式
            mr.setOutputFile(soundFile.getAbsolutePath());
            try {
                mr.prepare();
                mr.start();  //开始录制
                startTimeCount();
                mFilePath = soundFile.getAbsolutePath();
                KLog.d("录音文件路径：" + mFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //停止录制，资源释放
    private void stopRecord(){
        if(mr != null){
            mr.stop();
            mr.release();
            mr = null;
            stopTimeCount();
        }
    }

    @Override
    protected void onDestroy() {
        stopRecord();
        super.onDestroy();
    }

    /**
     * 开始计时
     */
    private void startTimeCount(){
        mTimeCount = 0;
        if (mRunnable == null){
            mRunnable = new MyRunnable();
            mHandler.postDelayed(mRunnable, 0);
        }
    }

    /**
     * 停止计时
     */
    private void stopTimeCount(){
        mHandler.removeCallbacks(mRunnable);
        mRunnable = null;
        KLog.d("停止计时，累计时间：" + mTimeCount + "秒");
    }

    @Override
    public void uploadSucc(BaseResponse response) {

    }

    private class MyRunnable implements Runnable {

        @Override
        public void run() {
            mTimeCount++;
            int m = mTimeCount / 60;
            int s = mTimeCount % 60;
            mRecordTimeTv.setText(String.format(getString(R.string.common_record_time), m, s));
            mHandler.postDelayed(this, 1000);
        }
    }
}
