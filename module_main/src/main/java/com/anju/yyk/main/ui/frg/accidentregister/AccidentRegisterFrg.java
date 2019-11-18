package com.anju.yyk.main.ui.frg.accidentregister;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anju.yyk.common.app.Constants;
import com.anju.yyk.common.app.arouter.RouterKey;
import com.anju.yyk.common.app.sp.AppSP;
import com.anju.yyk.common.base.BaseFragment;
import com.anju.yyk.common.base.BaseMvpFragment;
import com.anju.yyk.common.base.BaseResponse;
import com.anju.yyk.common.db.helper.DBHelper;
import com.anju.yyk.common.entity.response.PersonListResponse;
import com.anju.yyk.common.utils.AppUtil;
import com.anju.yyk.common.utils.FileUtil;
import com.anju.yyk.common.utils.ImageCompress;
import com.anju.yyk.common.utils.TimeUtil;
import com.anju.yyk.common.utils.klog.KLog;
import com.anju.yyk.common.widget.itemdecoration.SpacesItemDecoration;
import com.anju.yyk.main.R;
import com.anju.yyk.main.R2;
import com.anju.yyk.main.adapter.PatrolAdapter;
import com.anju.yyk.main.adapter.TakePhotoAdapter;
import com.anju.yyk.main.entity.PhotoEntity;
import com.anju.yyk.main.ui.act.addtip.AddTipAct;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class AccidentRegisterFrg extends BaseMvpFragment<AccidentRegPresenter, AccidentRegModel> implements IAccidentRegContract.IAccidentRegView{

    @BindView(R2.id.edt_content)
    EditText mContentEdt;

    @BindView(R2.id.tv_record_time)
    TextView mRecordTimeTv;

    @BindView(R2.id.btn_record)
    Button mRecordBtn;

    @BindView(R2.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R2.id.btn_commit)
    Button mCommitBtn;

    private TakePhotoAdapter mAdapter;

    private List<PhotoEntity> photos = new ArrayList<>();

    // 拍照上传相关
    private Bitmap photo;
    private File file;
    private Bitmap smallBitmap;
    public static String mSmallPicFilePath;
    /** 图片压缩*/
    private ImageCompress mImageCompress;

    /** 打开相机*/
    private final int GET_IMAGE_VIA_CAMERA_BEFORE = 210;

    /** 照片生成完毕，通知刷新列表*/
    private final int CREATED_PHOTO_COMPLETE = 1011;
    /** 准备照片数据*/
    private final int PREPARE_PHOTO_DATA = 1012;
    private final int ALBUM_RESULT_CODE = 1013;

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

    private PersonListResponse.ListBean mPersonInfo;
    private AppSP mAppSP;
    private Queue<String> imagePathList = new LinkedList<String>(); // 存放要上传的图片的路径
    private List<String> imageNameList = new ArrayList<String>(); // 存放上传成功的图片的路径
    private List<String> failedImageList = new ArrayList<String>(); // 存放上传失败的图片路径
    private String content;
    private String audioPath; // 服务器返回的音频路径
    private int btnType = -1;

    @Override
    public int getLayoutId() {
        return R.layout.home_frg_accident;
    }

    @Override
    public void init() {
        if (getArguments() != null){
            mPersonInfo = (PersonListResponse.ListBean) getArguments().getSerializable(RouterKey.BUNDLE_TAG);
        }

        if (getContext() != null) {
            mAppSP = new AppSP(getContext());
        }
//        initVoice();
        initRecyclerView();
        mImageCompress = new ImageCompress();
        mRecordTimeTv.setText(String.format(getString(R.string.common_record_time), 0, 0));
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
        return !isMarshmallow() || (getContext() != null && ContextCompat.checkSelfPermission(getContext(), permission) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermissions(String... permissions) {
        RxPermissions rxPermission = new RxPermissions(this);
        Disposable disposable = rxPermission.requestEach(permissions)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (btnType == 1) {
                            if (hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                openGallery();
                            }
                        } else if (btnType == 2) {
                            if (hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    && hasPermission(Manifest.permission.CAMERA)) {
                                openCamera();
                            }
                        } else if (btnType == 3) {
                            if (hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    && hasPermission(Manifest.permission.RECORD_AUDIO)) {
                                startRecord();
                            }
                        }
                        if (permission.granted) {
                            /*if (Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permission.name)
                                    && hasPermission(Manifest.permission.CAMERA)) {
                                openCamera();
                            }
                            if (Manifest.permission.CAMERA.equals(permission.name)
                                    && hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                openCamera();
                            }
                            if (Manifest.permission.RECORD_AUDIO.equals(permission.name)) {
                                startRecord();
                            }*/
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
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PhotoEntity entity = photos.get(position);
                if (entity.getItemType() == PhotoEntity.ADD_TYPE){
                    showChooseDialog();
                    /*if (!hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        requestPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    } else if (!hasPermission(Manifest.permission.CAMERA)) {
                        requestPermissions(Manifest.permission.CAMERA);
                    } else {
                        openCamera();
                    }*/
                }
            }
        });
    }

    private void openGallery() {
        if (!hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            requestPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        } else {
            Intent albumIntent = new Intent(Intent.ACTION_PICK);
            albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(albumIntent, ALBUM_RESULT_CODE);
        }
    }

    private void showChooseDialog() {
        if (mActivity == null) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        View view = LayoutInflater.from(mActivity).inflate(R.layout.home_dialog, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        Button chooseBtn = view.findViewById(R.id.choose_btn);
        Button cameraBtn = view.findViewById(R.id.camera_btn);
        chooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                btnType = 1;
                openGallery();
            }
        });
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                btnType = 2;
                if (!hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    requestPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                } else if (!hasPermission(Manifest.permission.CAMERA)) {
                    requestPermissions(Manifest.permission.CAMERA);
                } else {
                    openCamera();
                }
            }
        });
        dialog.show();
    }

    private void initRecyclerView(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        GridLayoutManager manager = new GridLayoutManager(mActivity, 3);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(0
                , AppUtil.dip2px(mActivity, 1), AppUtil.getColor(mActivity, R.color.common_divder_color)));

        mAdapter = new TakePhotoAdapter(photos);
        mAdapter.setSpanSizeLookup((gridLayoutManager, position) -> photos.get(position).getSpanSize());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        PhotoEntity addEntity = new PhotoEntity(PhotoEntity.ADD_TYPE);
        addEntity.setSpanSize(1);
        photos.add(addEntity);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void setupFragmentComponent() {

    }

    @Override
    protected boolean isRegisterEventBus() {
        return false;
    }

    /**
     * 打开相机
     */
    private void openCamera(){
        Context context = getContext();
        if (context == null) {
            return;
        }
        if (!AppUtil.isCanTakePhoto()){
            showToast(R.string.toast_sd_freesize_is_not_enough);
            return;
        }

        destoryImage();
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {

            File dir = new File(Constants.SAVE_DIR);

            if (dir.exists()){
                if (!dir.isDirectory()){
                    dir.delete();
                    dir.mkdirs();
                }
            }

            if (!dir.exists()){
                dir.mkdirs();
            }

            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            String fileName = System.currentTimeMillis() + ".jpg";
            file = new File(dir, fileName);
            Uri uri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                uri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file);
            } else {
                uri = Uri.fromFile(file);
            }
            intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            try {
                startActivityForResult(intent, GET_IMAGE_VIA_CAMERA_BEFORE);
            } catch (Exception e) {
                showToast(R.string.toast_open_camera_op);
            }
        } else {
            showToast(R.string.toast_sdcard_error);
        }
    }

    private void destoryImage() {
        if (photo != null) {
            photo.recycle();
            photo = null;
        }
    }

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case PREPARE_PHOTO_DATA:
                    break;
                case CREATED_PHOTO_COMPLETE:
                    BitmapFactory.Options option = new BitmapFactory.Options();
                    option.inSampleSize = 2;
                    photo = BitmapFactory.decodeFile(file.getPath(), option);
                    if (photo != null){
                        try {
                            smallBitmap = mImageCompress.comp(mImageCompress.getimage(file.getAbsolutePath()));
                        } catch (Exception e) {
                            e.printStackTrace();
                            showToast(R.string.error_photo_imagecompress);
                            smallBitmap = null;
                        }
                        // 文件名
                        String filename = TimeUtil.getStringTimestamp();
                        if (!TextUtils.isEmpty(filename)){
                            mSmallPicFilePath = Constants.SAVE_DIR + File.separator + filename + ".jpg";
                            if (smallBitmap != null){
                                FileUtil.createImageFile(mSmallPicFilePath, smallBitmap, Bitmap.CompressFormat.JPEG);
                                PhotoEntity photo = new PhotoEntity(PhotoEntity.NORMAL_TYPE);
                                photo.setSpanSize(1);
                                photo.setPhotoPath(mSmallPicFilePath);
                                photos.add(photo);
                                mAdapter.notifyDataSetChanged();
                                imagePathList.offer(mSmallPicFilePath);
                                // 此处上传图片
//                                mPresenter.uploadPhoto(mSmallPicFilePath);
                                smallBitmap = null;
                            }else {
                                showToast(R.string.error_photo_bitmap_isnull);
                            }
                        }
                        if (btnType == 2) {
                            file.delete();
                        }
                    }
                    hideLoading();
                    break;
            }
        }

    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GET_IMAGE_VIA_CAMERA_BEFORE) {
            if (resultCode == Activity.RESULT_OK){
                if (file != null && file.exists()) {
                    showLoading(true);
                    if (file.length() > 0){
                        handler.sendEmptyMessage(CREATED_PHOTO_COMPLETE);
                    }else {
                        new Thread(new Runnable() {
                            boolean flag = true;
                            @Override
                            public void run() {
                                while(flag){
                                    if (file.length() > 0){
                                        flag = false;
                                        handler.sendEmptyMessage(CREATED_PHOTO_COMPLETE);
                                        break;
                                    }
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        Thread.currentThread().interrupt();
                                    }
                                }
                            }

                        }).start();
                    }
                }
            }else if (resultCode == Activity.RESULT_CANCELED){
                showToast(R.string.toast_cancel_take_photo);
            }
        } else if (requestCode == ALBUM_RESULT_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null && data.getData() != null) {
                    Cursor cursor = null;
                    try {
                        String[] pro = {MediaStore.Images.Media.DATA};
                        cursor = mActivity.getContentResolver().query(data.getData(), pro, null, null, null);
                        if (cursor == null) {
                            return;
                        }
                        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        cursor.moveToFirst();
                        String path = cursor.getString(column_index);
                        file = new File(path);
                        if (file.exists() && file.length() > 0) {
                            handler.sendEmptyMessage(CREATED_PHOTO_COMPLETE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (cursor != null)
                            cursor.close();
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void uploadSucc() {

    }

    private void uploadFile() {
        int size = imagePathList.size();
        if (size > 0) {
            mPresenter.uploadImage(imagePathList.poll());
        } else {
            String imagePath = "";
            if (imageNameList.size() > 0) {
                StringBuilder builder = new StringBuilder();
                int imageSize = imageNameList.size();
                for (int i = 0; i < imageSize; i++) {
                    builder.append(imageNameList.get(i));
                    if (i < imageSize - 1) {
                        builder.append(",");
                    }
                }
                imagePath = builder.toString();
                Log.e("Test", audioPath + ", " + imagePath);
            }
            mPresenter.addAccident(mPersonInfo.getId(), mAppSP.getUserId(), content, audioPath, imagePath);
        }
    }

    @Override
    public void uploadSucc(String filePath) {
//        showToast("上传照片成功");
        if (filePath != null) {
            if (filePath.endsWith("jpg")) {
                if (!imageNameList.contains(filePath)) {
                    imageNameList.add(filePath);
                }
            } else if (filePath.endsWith("mp3")) {
                audioPath = filePath;
            }
        }
        uploadFile();
    }

    @Override
    public void uploadFailed(String filePath) {
        if (filePath.endsWith("jpg")) {
            failedImageList.add(filePath);
        }
        uploadFile();
    }

    @Override
    public void uploadSucc(BaseResponse response) {
//        showToast("上传音频成功");
    }

    @Override
    public void addAccidentSuccess() {
        showToast("登记成功");
        if (mActivity != null) {
            mActivity.finish();
        }
    }

    @Override
    public void addAccidentFailed() {

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

    @OnClick({R2.id.btn_record, R2.id.btn_commit})
    public void onViewClicked(View v){
        if (v.getId() == R.id.btn_record){
            if(!isStart){
                startRecord();
                btnType = 3;
//                mRecordBtn.setText("停止录音");
//                isStart = true;
            }else{
                stopRecord();
                mRecordBtn.setText("开始录音");
                isStart = false;
            }
        }else if (v.getId() == R.id.btn_commit){
            content = mContentEdt.getText().toString().trim();
            if (TextUtils.isEmpty(content)) {
                showToast(R.string.home_add_accident_hint);
                return;
            }
            if (isStart) {
                stopRecord();
            }
            if (failedImageList.size() > 0) {
                imagePathList.addAll(failedImageList);
                failedImageList.clear();
            }
            if (!TextUtils.isEmpty(mFilePath)) {
                mPresenter.uploadFile(mFilePath);
            } else if (imagePathList.size() > 0) {
                uploadFile();
            } else {
                mPresenter.addAccident(mPersonInfo.getId(), mAppSP.getUserId(), content, "", "");
            }
        }
    }

    private void startRecord() {
        if (!hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            requestPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            return;
        }

        if (!hasPermission(Manifest.permission.RECORD_AUDIO)) {
            requestPermissions(Manifest.permission.RECORD_AUDIO);
            return;
        }
        File audioDir = new File(Environment.getExternalStorageDirectory(), AUDIO_DIR_NAME);
        if (mr == null) {
            mFilePath = "";
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
                startTimeCount();
                mFilePath = soundFile.getAbsolutePath();
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
            stopTimeCount();
        }
    }

    @Override
    public void onDestroy() {
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
}
