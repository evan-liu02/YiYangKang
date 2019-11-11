package com.anju.yyk.main.ui.act.scantips;

import android.media.MediaPlayer;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.anju.yyk.common.app.arouter.RouterConstants;
import com.anju.yyk.common.app.arouter.RouterKey;
import com.anju.yyk.common.base.BaseMvpActivity;
import com.anju.yyk.common.entity.response.NewTipsListResponse;
import com.anju.yyk.common.entity.response.PersonListResponse;
import com.anju.yyk.common.utils.AppUtil;
import com.anju.yyk.common.utils.SweetAlertUtil;
import com.anju.yyk.common.utils.klog.KLog;
import com.anju.yyk.common.widget.itemdecoration.SpacesItemDecoration;
import com.anju.yyk.common.widget.sweetalert.SweetAlertDialog;
import com.anju.yyk.main.R;
import com.anju.yyk.main.R2;
import com.anju.yyk.main.adapter.TipsAdapter;
import com.anju.yyk.main.entity.TipsEntity;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = RouterConstants.ACT_URL_SCAN_PERSONAL_TIPS)
public class ScanPersonalTipsAct extends BaseMvpActivity<ScanTipsPresenter, ScanTipsModel>
        implements IScanTipsContract.IScanTipsView, TipsAdapter.TipsCallBack {
    @BindView(R2.id.cl_header)
    ConstraintLayout mHeaderLayout;

    @BindView(R2.id.iv_sex)
    ImageView mSexIv;

    @BindView(R2.id.tv_age)
    TextView mAgeTv;

    @BindView(R2.id.tv_name)
    TextView mNameTv;

    @BindView(R2.id.tv_bedid)
    TextView mBedIdTv;

    @BindView(R2.id.tv_tag)
    TextView mTagTv;

    @BindView(R2.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R2.id.ll_play_contral)
    LinearLayout mContralLayout;

    @BindView(R2.id.iv_play)
    ImageView mPlayIv;

    @BindView(R2.id.tv_curtime)
    TextView mCurTimeTv;

    @BindView(R2.id.tv_duration)
    TextView mDurationTv;

    @BindView(R2.id.play_pb)
    SeekBar mPlayPb;

    @BindView(R2.id.iv_download)
    ImageView mDownLoadIv;

    /** 可以用于判断从首页哪里跳转过来*/
    @Autowired(name = RouterKey.BUNDLE_TAG)
    public PersonListResponse.ListBean mPersonInfo;

    private TipsAdapter mAdapter;

    private List<MultiItemEntity> mList = new ArrayList<>();

    private int mCurSelectPosition;

    private MediaPlayer mediaPlayer;

    /** 0,播放;1，暂停*/
    private int mPlayStatus = 0;

    private String mTipId;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

    @Override
    protected int getLayoutId() {
        return R.layout.home_act_scan_tips;
    }

    @Override
    protected void init() {
        ARouter.getInstance().inject(this);
        mToolbar.setTopicColor(ContextCompat.getColor(this, R.color.common_white));
        mToolbar.ivIsShow(true, false);
        setToolbarTopic(R.string.home_topic_sacan_tips);
        initRecyclerView();
        mediaPlayer = new MediaPlayer();
    }

    @Override
    public void initListener() {
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> mCurSelectPosition = position);
        mAdapter.setCallback(this);
    }

    @Override
    public void initData() {
        if (mPersonInfo.getSex() == 2){
            mSexIv.setImageResource(R.mipmap.home_ic_famale);
        }else {
            mSexIv.setImageResource(R.mipmap.home_ic_male);
        }
        mAgeTv.setText(mPersonInfo.getNianling() + "岁");
        mNameTv.setText(mPersonInfo.getName());
        mBedIdTv.setText(mPersonInfo.getChuangwei() + "号床");
        mTagTv.setText(mPersonInfo.getHulijibie());

        mPresenter.loadPersonalTips(mPersonInfo.getId());
    }

    private void initRecyclerView(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(0
                , AppUtil.dip2px(mActivity, 1), AppUtil.getColor(mActivity, R.color.common_divder_color)));

        mAdapter = new TipsAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void setupActivityComponent() {

    }

    @Override
    public boolean isRegisterEventBus() {
        return false;
    }

    @Override
    public void getTipsSucc(List<NewTipsListResponse.ListBean> listBeans) {
    }

    @Override
    public void getPersonalTipsSucc(List<NewTipsListResponse.ListBean> listBeans) {
        mList.clear();
        for (NewTipsListResponse.ListBean bean : listBeans){
            String date = bean.getDate();
            if (bean.getRecords() != null && bean.getRecords().size() > 0){
                List<TipsEntity> tempList = new ArrayList<TipsEntity>();
                for (NewTipsListResponse.ListBean.RecordsBean record : bean.getRecords()){
                    TipsEntity tip = new TipsEntity();
                    tip.setDate(date);
                    tip.setId(record.getId());
                    tip.setChuangwei(record.getChuangwei());
                    tip.setContent(record.getContent());
                    tip.setHugong(record.getHugong());
                    tip.setTime(record.getTime());
                    tip.setName(record.getName());
                    tip.setStatus(record.getStatus());
                    tip.setYidurenyuan(record.getYidurenyuan());
                    if (record.getLuyin() != null && record.getLuyin().size() > 0){
                        for (NewTipsListResponse.ListBean.RecordsBean.LuyinBean audio : record.getLuyin()){
                            TipsEntity.LuyinBean luyin = new TipsEntity.LuyinBean();
                            luyin.setLujing(audio.getLujing());
                            tip.addSubItem(luyin);
                        }
                    }
                    tempList.add(tip);
//                    mList.add(tip);
                }
                Collections.sort(tempList, new Comparator<TipsEntity>() {
                    @Override
                    public int compare(TipsEntity tipsEntity, TipsEntity t1) {
                        String dateStr1 = tipsEntity.getDate() + " " + tipsEntity.getTime();
                        String dateStr2 = t1.getDate() + " " + t1.getTime();
                        try {
                            long time1 = Objects.requireNonNull(dateFormat.parse(dateStr1)).getTime();
                            long time2 = Objects.requireNonNull(dateFormat.parse(dateStr2)).getTime();
                            if (time1 > time2) {
                                return -1;
                            } else if (time1 < time2) {
                                return 1;
                            } else {
                                return 0;
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        return 0;
                    }
                });
                mList.addAll(tempList);
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void downLoadSucc() {

    }

    @Override
    public void readedSucc() {
//        ((TipsEntity) mList.get(mCurSelectPosition)).setStatus(0);
//        mAdapter.notifyDataSetChanged();
        mPresenter.loadPersonalTips(mPersonInfo.getId());
    }

    @Override
    public void clickPlay(ImageView image, String audioUrl) {
        mPlayIv = image;
        prepareMediaPlayer(audioUrl);
    }

    @Override
    public void downLoadAudio(String audioUrl) {
        // TODO 下载
    }

    @Override
    public void clickRead(int position, String zhuyi_id) {
        mCurSelectPosition = position;
        mTipId = zhuyi_id;
        SweetAlertUtil.showConfirmAndCancelAlert(mActivity, "标记已读"
                , "标记此提醒为已读", getString(R.string.common_confirm)
                , getString(R.string.common_cancel), finishOrderConfirmListener);
    }

    /**
     * 已读Dialog确认
     */
    private SweetAlertDialog.OnSweetButtonClickListener finishOrderConfirmListener = new SweetAlertDialog.OnSweetButtonClickListener() {

        @Override
        public void onClick(View v) {
            if (v.getId() == com.anju.yyk.common.R.id.confirm_button){
                if (!TextUtils.isEmpty(mTipId)){
                    mPresenter.read(mTipId);
                }
            }
        }
    };

    private void prepareMediaPlayer(String audioUrl){
        releaseMediaPlayer();
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(audioUrl);
            initMediaPlayerListener();
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startPlay(){
        if (mediaPlayer != null){
            if (!mediaPlayer.isPlaying()){
                mediaPlayer.start();
                mPlayIv.setImageResource(R.mipmap.common_ic_pause);
                mPlayStatus = 0;
            }
        }
    }

    private void pausePlay(){
        if (mediaPlayer != null){
            if (mediaPlayer.isPlaying()){
                mediaPlayer.pause();
                mPlayIv.setImageResource(R.mipmap.ic_media_play);
                mPlayStatus = 1;
            }
        }
    }

    private boolean isPlaying() {
        return mediaPlayer != null && mediaPlayer.isPlaying();
    }

    private void initMediaPlayerListener(){
        mediaPlayer.setOnPreparedListener(mp -> {
            startPlay();
            int duration = mp.getDuration();
            if (0 != duration) {
                //更新 seekbar 长度
                mPlayPb.setMax(duration);
                int s = duration / 1000;
                //设置文件时长，单位 "分:秒" 格式
                String total = s / 60 + ":" + s % 60;
                mDurationTv.setText(total);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @OnClick({R2.id.iv_play})
    public void onViewClicked(View v){
        if (v.getId() == R.id.iv_play){
            if (mPlayStatus == 0){
                pausePlay();
            }else {
                startPlay();
            }
        }
    }
}
