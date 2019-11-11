package com.anju.yyk.main.ui.frg.register;

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
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anju.yyk.common.app.Constants;
import com.anju.yyk.common.app.arouter.RouterKey;
import com.anju.yyk.common.app.sp.AppSP;
import com.anju.yyk.common.base.BaseApplication;
import com.anju.yyk.common.base.BaseMvpFragment;
import com.anju.yyk.common.entity.response.CheckRoomListResponse;
import com.anju.yyk.common.entity.response.PersonListResponse;
import com.anju.yyk.common.utils.AppUtil;
import com.anju.yyk.common.utils.FileUtil;
import com.anju.yyk.common.utils.ImageCompress;
import com.anju.yyk.common.utils.TimeUtil;
import com.anju.yyk.common.utils.klog.KLog;
import com.anju.yyk.common.widget.itemdecoration.SpacesItemDecoration;
import com.anju.yyk.main.R;
import com.anju.yyk.main.R2;
import com.anju.yyk.main.adapter.CheckRoomAdapter;
import com.anju.yyk.main.adapter.TakePhotoAdapter;
import com.anju.yyk.main.di.component.DaggerMainComponent;
import com.anju.yyk.main.entity.PhotoEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import kotlin.LateinitKt;

/**
 *
 * @author LeoWang
 *
 * @Package com.anju.yyk.main.ui.frg.checkroomregister
 *
 * @Description 查房登记
 *
 * @Date 2019/10/16 17:28
 *
 * @modify:
 */
public class RegisterFrg extends BaseMvpFragment<RegisterPresenter, RegisterModel>
        implements IRegisterContract.IRegisterView, CheckRoomAdapter.CheckRoomAdapterCallback {

    /** 打开相机*/
    private final int GET_IMAGE_VIA_CAMERA_BEFORE = 210;
    /** 照片生成完毕，通知刷新列表*/
    private final int CREATED_PHOTO_COMPLETE = 1011;
    /** 准备照片数据*/
    private final int PREPARE_PHOTO_DATA = 1012;

    @BindView(R2.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R2.id.recyclerView1)
    RecyclerView mRecordRecyclerView;

    @BindView(R2.id.edt_content)
    EditText mContentEdt;

    private CheckRoomAdapter mAdapter;
    private TakePhotoAdapter mRecordAdapter;

    /** 图片压缩*/
    private ImageCompress mImageCompress;

    private List<CheckRoomListResponse.ListBean> mList = new ArrayList<>();

    private int mCurPosition = 0;

    @Inject
    AppSP mAppSP;

    private PersonListResponse.ListBean mPersonInfo;
    private HashMap<String, String> itemMap = new HashMap<String, String>();
    private List<PhotoEntity> photos = new ArrayList<>();
    private Bitmap photo;
    private File file;
    private Bitmap smallBitmap;
    public static String mSmallPicFilePath;
    private Queue<String> imagePathList = new LinkedList<String>(); // 存放要上传的图片的路径
    private List<String> imageNameList = new ArrayList<String>(); // 存放上传成功的图片的路径
    private List<String> failedImageList = new ArrayList<String>(); // 存放上传失败的图片路径
    private String content;

    @Override
    public int getLayoutId() {
        return R.layout.home_frg_checkroom;
    }

    @Override
    public void init() {
        if (getArguments() != null){
            mPersonInfo = (PersonListResponse.ListBean) getArguments().getSerializable(RouterKey.BUNDLE_TAG);
        }
        mImageCompress = new ImageCompress();
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
        mRecordAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
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
        mPresenter.getCheckRoomList();

        PhotoEntity addEntity = new PhotoEntity(PhotoEntity.ADD_TYPE);
        addEntity.setSpanSize(1);
        photos.add(addEntity);
        mRecordAdapter.notifyDataSetChanged();
    }

    @Override
    protected void setupFragmentComponent() {
        DaggerMainComponent.builder()
                .appComponent(BaseApplication.getInstance().getAppComponent())
                .build()
                .inject(this);
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

        mAdapter = new CheckRoomAdapter(mList);
        mAdapter.setAdapterCallBack(this);
        mRecyclerView.setAdapter(mAdapter);

        mRecordRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecordRecyclerView.setItemAnimator(new DefaultItemAnimator());
        GridLayoutManager manager = new GridLayoutManager(mActivity, 3);
        mRecordRecyclerView.setLayoutManager(manager);
        mRecordRecyclerView.addItemDecoration(new SpacesItemDecoration(0
                , AppUtil.dip2px(mActivity, 1), AppUtil.getColor(mActivity, R.color.common_divder_color)));

        mRecordAdapter = new TakePhotoAdapter(photos);
        mRecordAdapter.setSpanSizeLookup((gridLayoutManager, position) -> photos.get(position).getSpanSize());
        mRecordRecyclerView.setAdapter(mRecordAdapter);
    }

    @Override
    public void checkRoomListSucc(CheckRoomListResponse response) {
        List<CheckRoomListResponse.ListBean> listBeans = response.getList();
        if (listBeans != null && listBeans.size() > 0){
            mList.clear();
            mList.addAll(listBeans);
            mAdapter.notifyDataSetChanged();

            if (mList != null && mList.size() > 0) {
                for (CheckRoomListResponse.ListBean itemBean : mList) {
                    int type = itemBean.getLeixing();
                    if (type == 2) {
                        itemMap.put(itemBean.getLieming(), itemBean.getToggleStatus() == 1 ? "否" : "是");
                    } else if (type == 1) {
                        List<CheckRoomListResponse.ListBean.OptionBean> option = itemBean.getOption();
                        if (option != null && option.size() > 0) {
                            itemMap.put(itemBean.getLieming(), option.get(0).getZhi());
                        }
                    } else if (type == 0) {
                        itemMap.put(itemBean.getLieming(), "");
                    }
                }
            }
        }
    }

    @Override
    public void checkRoomCommitSucc() {
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
            mPresenter.checkRoomCommit(mAppSP.getUserId(), "", false, mPersonInfo.getId(), itemMap, imagePath);
        }
    }

    @Override
    public void uploadSuccess(String filePath) {
        if (filePath != null) {
            if (filePath.endsWith("jpg")) {
                if (!imageNameList.contains(filePath)) {
                    imageNameList.add(filePath);
                }
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
    public void toggle(int position, boolean isCheck, String id) {
        mCurPosition = position;
        KLog.d("点击了第" + position + "行------" + isCheck);
        if (isCheck) {
            mList.get(mCurPosition).setToggleStatus(0);
        } else {
            mList.get(mCurPosition).setToggleStatus(1);
        }
        if (position < mList.size()) {
            String key = mList.get(position).getLieming();
            itemMap.put(key, isCheck ? "是" : "否");
        }
    }

    @Override
    public void selectedItem(int position, String itemText) {
        if (position < mList.size()) {
            String key = mList.get(position).getLieming();
            itemMap.put(key, itemText);
        }
    }

    @Override
    public void textChanged(int position, String text) {
        if (position < mList.size()) {
            String key = mList.get(position).getLieming();
            itemMap.put(key, text);
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
            //11-01 12:37:07.140 10646 10646 E AndroidRuntime: java.lang.IllegalArgumentException: @FieldMap parameters can only be used with form encoding. (parameter #6)
            if (imagePathList.size() > 0) {
                uploadFile();
            } else {
                mPresenter.checkRoomCommit(mAppSP.getUserId(), "", false, mPersonInfo.getId(), itemMap, "");
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
                                mRecordAdapter.notifyDataSetChanged();
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
