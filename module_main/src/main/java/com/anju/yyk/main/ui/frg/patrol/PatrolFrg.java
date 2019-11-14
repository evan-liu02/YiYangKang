package com.anju.yyk.main.ui.frg.patrol;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anju.yyk.common.app.Constants;
import com.anju.yyk.common.app.sp.AppSP;
import com.anju.yyk.common.base.BaseMvpFragment;
import com.anju.yyk.common.entity.response.PatrolResponse;
import com.anju.yyk.common.entity.response.PersonListResponse;
import com.anju.yyk.common.utils.AppUtil;
import com.anju.yyk.common.utils.FileUtil;
import com.anju.yyk.common.utils.ImageCompress;
import com.anju.yyk.common.utils.TimeUtil;
import com.anju.yyk.common.widget.itemdecoration.SpacesItemDecoration;
import com.anju.yyk.main.R;
import com.anju.yyk.main.R2;
import com.anju.yyk.main.adapter.PatrolAdapter;
import com.anju.yyk.main.adapter.TakePhotoAdapter;
import com.anju.yyk.main.entity.PhotoEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 *
 * @author LeoWang
 *
 * @Package com.anju.yyk.main.ui.frg.patrol
 *
 * @Description 安全巡查
 *
 * @Date 2019/9/5 15:21
 *
 * @modify:
 */
public class PatrolFrg extends BaseMvpFragment<PatrolPresenter, PatrolModel> implements IPatrolContract.IPatrolView, PatrolAdapter.PatrolAdapterCallback {

    /** 打开相机*/
    private final int GET_IMAGE_VIA_CAMERA_BEFORE = 210;
    /** 照片生成完毕，通知刷新列表*/
    private final int CREATED_PHOTO_COMPLETE = 1011;
    /** 准备照片数据*/
    private final int PREPARE_PHOTO_DATA = 1012;

    @BindView(R2.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R2.id.recyclerView1)
    RecyclerView mPhotoRecyclerView;


    @BindView(R2.id.edt_content)
    EditText mContentEdt;

    private String[] mDevicesTitle = null;

    private List<PatrolResponse.ListBean> mPatrols = new ArrayList<>();

    private PatrolAdapter mAdapter;
    private TakePhotoAdapter mPhotoAdapter;

    private ImageCompress mImageCompress;
    private PersonListResponse.ListBean mPersonInfo;
    private HashMap<String, Integer> itemMap = new HashMap<String, Integer>();
    private List<PhotoEntity> photos = new ArrayList<>();
    private Bitmap photo;
    private File file;
    private Bitmap smallBitmap;
    public String mSmallPicFilePath;
    private Queue<String> imagePathList = new LinkedList<String>(); // 存放要上传的图片的路径
    private List<String> imageNameList = new ArrayList<String>(); // 存放上传成功的图片的路径
    private List<String> failedImageList = new ArrayList<String>(); // 存放上传失败的图片路径
    private String content;

    AppSP mAppSP;

    @Override
    public int getLayoutId() {
        return R.layout.home_frg_patrol;
    }

    @Override
    public void init() {
//        mDevicesTitle = getResources().getStringArray(R.array.home_devices_title);
//        prepareData();
        mImageCompress = new ImageCompress();
        mAppSP = new AppSP(mActivity);
        initRecyclerView();
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
                        if (permission.granted) {
                            if (Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permission.name)
                                    && hasPermission(Manifest.permission.CAMERA)) {
                                openCamera();
                            }
                            if (Manifest.permission.CAMERA.equals(permission.name)
                                    && hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                openCamera();
                            }
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
        mPhotoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PhotoEntity entity = photos.get(position);
                if (entity.getItemType() == PhotoEntity.ADD_TYPE){
                    if (!hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        requestPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    } else if (!hasPermission(Manifest.permission.CAMERA)) {
                        requestPermissions(Manifest.permission.CAMERA);
                    } else {
                        openCamera();
                    }
                }
            }
        });
    }

    @Override
    protected void initData() {
        mPresenter.getPatrol();

        PhotoEntity addEntity = new PhotoEntity(PhotoEntity.ADD_TYPE);
        addEntity.setSpanSize(1);
        photos.add(addEntity);
        mPhotoAdapter.notifyDataSetChanged();
    }

    @Override
    protected void setupFragmentComponent() {

    }

    @Override
    protected boolean isRegisterEventBus() {
        return false;
    }

    private void initRecyclerView(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(0
                , AppUtil.dip2px(mActivity, 1), AppUtil.getColor(mActivity, R.color.common_divder_color)));

        mAdapter = new PatrolAdapter(mPatrols);
        mAdapter.setAdapterCallBack(this);
        mAdapter.setSpanSizeLookup((gridLayoutManager, position) -> mPatrols.get(position).getSpanSize());
        mRecyclerView.setAdapter(mAdapter);


        mPhotoRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mPhotoRecyclerView.setItemAnimator(new DefaultItemAnimator());
        GridLayoutManager manager = new GridLayoutManager(mActivity, 3);
        mPhotoRecyclerView.setLayoutManager(manager);
        mPhotoRecyclerView.addItemDecoration(new SpacesItemDecoration(0
                , AppUtil.dip2px(mActivity, 1), AppUtil.getColor(mActivity, R.color.common_divder_color)));

        mPhotoAdapter = new TakePhotoAdapter(photos);
        mPhotoAdapter.setSpanSizeLookup((gridLayoutManager, position) -> photos.get(position).getSpanSize());
        mPhotoRecyclerView.setAdapter(mPhotoAdapter);
    }

    @Override
    public void patrolSucc(PatrolResponse response) {
        List<PatrolResponse.ListBean> listBeans = response.getList();
        mPatrols.clear();

        if (listBeans != null && listBeans.size() > 0){
            for (PatrolResponse.ListBean bean : listBeans){
                bean.setSpanSize(3);
                bean.setmItemType(PatrolResponse.DEVICE_TYPE);
                if ("2".equals(bean.getLeixing())) {
                    itemMap.put(bean.getLieming(), 1);
                }
                mPatrols.add(bean);
            }
        }

        mAdapter.notifyDataSetChanged();
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
            }
            mPresenter.patrolCommit(mAppSP.getUserId(), content, itemMap, imagePath);
        }
    }

    @Override
    public void uploadSuccess(String filePath) {
        if (filePath != null && filePath.endsWith("jpg")) {
            if (!imageNameList.contains(filePath)) {
                imageNameList.add(filePath);
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
    public void patrolCommitSucc() {
        showToast("添加成功");
    }

    @Override
    public void toggle(int position, boolean isCheck) {
        if (position < mPatrols.size()) {
            mPatrols.get(position).setRight(isCheck);
            String key = mPatrols.get(position).getLieming();
            itemMap.put(key, isCheck ? 1 : 0);
        }
    }

    @OnClick({R2.id.btn_commit})
    public void onViewClicked(View view){
        if (view.getId() == R.id.btn_commit) {
            content = mContentEdt.getText().toString().trim();
            if (failedImageList.size() > 0) {
                imagePathList.addAll(failedImageList);
                failedImageList.clear();
            }
            if (imagePathList.size() > 0) {
                uploadFile();
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
                }
                mPresenter.patrolCommit(mAppSP.getUserId(), content, itemMap, imagePath);
            }
        }
    }

    private void destroyImage() {
        if (photo != null) {
            photo.recycle();
            photo = null;
        }
    }

    private void openCamera(){
        Context context = getContext();
        if (context == null) {
            return;
        }
        if (!AppUtil.isCanTakePhoto()) {
            showToast(R.string.toast_sd_freesize_is_not_enough);
            return;
        }

        destroyImage();
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            File dir = new File(Constants.SAVE_DIR);
            if (dir.exists()) {
                if (!dir.isDirectory()) {
                    dir.delete();
                    dir.mkdirs();
                }
            }
            if (!dir.exists()) {
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

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PREPARE_PHOTO_DATA:
                    break;
                case CREATED_PHOTO_COMPLETE:
                    BitmapFactory.Options option = new BitmapFactory.Options();
                    option.inSampleSize = 2;
                    photo = BitmapFactory.decodeFile(file.getPath(), option);
                    if (photo != null) {
                        try {
                            smallBitmap = mImageCompress.comp(mImageCompress.getimage(file.getAbsolutePath()));
                        } catch (Exception e) {
                            e.printStackTrace();
                            showToast(R.string.error_photo_imagecompress);
                            smallBitmap = null;
                        }
                        // 文件名
                        String filename = TimeUtil.getStringTimestamp();
                        if (!TextUtils.isEmpty(filename)) {
                            mSmallPicFilePath = Constants.SAVE_DIR + File.separator + filename + ".jpg";
                            if (smallBitmap != null) {
                                FileUtil.createImageFile(mSmallPicFilePath, smallBitmap, Bitmap.CompressFormat.JPEG);
                                PhotoEntity photo = new PhotoEntity(PhotoEntity.NORMAL_TYPE);
                                photo.setSpanSize(1);
                                photo.setPhotoPath(mSmallPicFilePath);
                                photos.add(photo);
                                mPhotoAdapter.notifyDataSetChanged();
                                imagePathList.offer(mSmallPicFilePath);
                                smallBitmap = null;
                            } else {
                                showToast(R.string.error_photo_bitmap_isnull);
                            }
                        }
                        file.delete();
                    }
                    hideLoading();
                    break;
            }
        }

    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GET_IMAGE_VIA_CAMERA_BEFORE) {
            if (resultCode == Activity.RESULT_OK) {
                if (file != null && file.exists()) {
                    showLoading(true);
                    if (file.length() > 0) {
                        handler.sendEmptyMessage(CREATED_PHOTO_COMPLETE);
                    } else {
                        new Thread(new Runnable() {
                            boolean flag = true;

                            @Override
                            public void run() {
                                while (flag) {
                                    if (file.length() > 0) {
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
            } else if (resultCode == Activity.RESULT_CANCELED) {
                showToast(R.string.toast_cancel_take_photo);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
