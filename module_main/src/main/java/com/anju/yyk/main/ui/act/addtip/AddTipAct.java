package com.anju.yyk.main.ui.act.addtip;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.anju.yyk.common.app.arouter.RouterConstants;
import com.anju.yyk.common.base.BaseActivity;
import com.anju.yyk.common.base.BaseMvpActivity;
import com.anju.yyk.common.base.BaseResponse;
import com.anju.yyk.common.utils.klog.KLog;
import com.anju.yyk.main.R;
import com.anju.yyk.main.R2;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

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

    /*@BindView(R2.id.tv_record_time)
    TextView mRecordTimeTv;*/

    @BindView(R2.id.chronometer)
    Chronometer mChronometer;

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
        requestPermissions();
//        initVoice();
//        mRecordTimeTv.setText(String.format(getString(R.string.common_record_time), 0, 0));
    }

    private void initVoice() {
        mAudioDir = new File(Environment.getExternalStorageDirectory(), AUDIO_DIR_NAME);
        if (!mAudioDir.exists()) {
            mAudioDir.mkdirs();
        }
    }

    private boolean isMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    private boolean hasPermission(String permission) {
        return !isMarshmallow() || ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        RxPermissions rxPermission = new RxPermissions(this);
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO};
        Disposable disposable = rxPermission.requestEach(permissions)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            // 用户已经同意该权限
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时。还会提示请求权限的对话框
                        } else {
                            // 用户拒绝了该权限，而且选中『不再询问』
                        }
                    }
                });
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
            }else{
                stopRecord();
            }
        }else if (v.getId() == R.id.btn_cancel){
            finish();
        }else if (v.getId() == R.id.btn_commit){
            if (!TextUtils.isEmpty(mFilePath)){
                if (isStart) {
                    stopRecord();
                }
                String content = mTipContentEdt.getText().toString().trim();
                // TODO 上传成功，但是会报错
                mPresenter.uploadAudio(content, mFilePath);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void startRecord(){
        if (!hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            showToast(R.string.home_no_external_storage_tip);
            return;
        }
        if (!hasPermission(Manifest.permission.RECORD_AUDIO)) {
            showToast(R.string.home_no_record_audio_tip);
            return;
        }
        File audioDir = new File(Environment.getExternalStorageDirectory(), AUDIO_DIR_NAME);
        if (mr == null) {
            if (!audioDir.exists()) {
                boolean success = audioDir.mkdirs();
                if (!success) {
                    return;
                }
            }
            File soundFile = new File(audioDir, System.currentTimeMillis() + ".amr");
            if (!soundFile.exists()) {
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
//                startTimeCount();
                mFilePath = soundFile.getAbsolutePath();
                mChronometer.start();
                mRecordBtn.setText("停止录音");
                isStart = true;
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
//            stopTimeCount();
        }
        mChronometer.stop();
        mChronometer.setBase(SystemClock.elapsedRealtime()); // 重置
        mRecordBtn.setText("开始录音");
        isStart = false;
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
//            mRecordTimeTv.setText(String.format(getString(R.string.common_record_time), m, s));
            mHandler.postDelayed(this, 1000);
        }
    }
}
