package com.anju.yyk.main.ui.act.scantips;

import android.media.MediaPlayer;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.anju.yyk.common.entity.response.TipsListResponse;
import com.anju.yyk.common.utils.AppUtil;
import com.anju.yyk.common.utils.SweetAlertUtil;
import com.anju.yyk.common.utils.klog.KLog;
import com.anju.yyk.common.widget.itemdecoration.SpacesItemDecoration;
import com.anju.yyk.common.widget.sweetalert.SweetAlertDialog;
import com.anju.yyk.main.R;
import com.anju.yyk.main.R2;
import com.anju.yyk.main.adapter.TipsAdapter;
import com.anju.yyk.main.entity.TipsEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = RouterConstants.ACT_URL_SCAN_TIPS)
public class ScanTipsAct extends BaseMvpActivity<ScanTipsPresenter, ScanTipsModel>
        implements IScanTipsContract.IScanTipsView, TipsAdapter.TipsCallBack{

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
    ProgressBar mPlayPb;

    @BindView(R2.id.iv_download)
    ImageView mDownLoadIv;

    private TipsAdapter mAdapter;

    private List<MultiItemEntity> mList = new ArrayList<>();

    private int mCurSelectPosition;

    private MediaPlayer mediaPlayer;

    /** 0,播放;1，暂停*/
    private int mPlayStatus = 0;

    private String mTipId;

    @Override
    protected int getLayoutId() {
        return R.layout.home_act_scan_tips;
    }

    @Override
    protected void init() {
        ARouter.getInstance().inject(this);
        mToolbar.setTopicColor(ContextCompat.getColor(this, R.color.common_white));
        mToolbar.ivIsShow(true, false);
        setToolbarTopic(R.string.home_topic_attention);
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
        mHeaderLayout.setVisibility(View.GONE);
        mPresenter.loadTips();
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
        mList.clear();
        for (NewTipsListResponse.ListBean bean : listBeans){
            String date = bean.getDate();
            if (bean.getRecords() != null && bean.getRecords().size() > 0){
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
                        List<TipsEntity.LuyinBean> luyinBeans = new ArrayList<>();
                        for (NewTipsListResponse.ListBean.RecordsBean.LuyinBean audio : record.getLuyin()){
                            TipsEntity.LuyinBean luyin = new TipsEntity.LuyinBean();
                            luyin.setLujing(audio.getLujing());
                            luyinBeans.add(luyin);
                            tip.addSubItem(luyin);
                            luyinBeans.add(luyin);
                        }
                        tip.setLuyin(luyinBeans);
                    }
                    mList.add(tip);
                }
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void getPersonalTipsSucc(List<NewTipsListResponse.ListBean> listBeans) {

    }

    @Override
    public void downLoadSucc() {

    }

    @Override
    public void readedSucc() {
//        ((TipsEntity) mList.get(mCurSelectPosition)).setStatus(0);
//        mAdapter.notifyDataSetChanged();
        mPresenter.loadTips();
    }

    @Override
    public void clickPlay(String audioUrl) {

        if (mediaPlayer == null)
            mediaPlayer = new MediaPlayer();

        boolean isPlaying = false;
        try {
            isPlaying = mediaPlayer.isPlaying();
        }catch (IllegalStateException e){
            mediaPlayer = null;
            mediaPlayer = new MediaPlayer();
        }

        if (isPlaying){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            mediaPlayer = new MediaPlayer();
        }
        preareMediaPlayer(audioUrl);
        startPlay();
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

    private void preareMediaPlayer(String audioUrl){
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(audioUrl);
            mediaPlayer.prepare();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
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
