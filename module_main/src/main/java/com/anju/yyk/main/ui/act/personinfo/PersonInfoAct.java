package com.anju.yyk.main.ui.act.personinfo;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.anju.yyk.common.adapter.TabPagerAdapter;
import com.anju.yyk.common.app.arouter.RouterConstants;
import com.anju.yyk.common.app.arouter.RouterKey;
import com.anju.yyk.common.base.BaseActivity;
import com.anju.yyk.common.entity.response.PersonListResponse;
import com.anju.yyk.common.widget.titles.ColorFlipPagerTitleView;
import com.anju.yyk.magicindicator.MagicIndicator;
import com.anju.yyk.magicindicator.ViewPagerHelper;
import com.anju.yyk.magicindicator.buildins.UIUtil;
import com.anju.yyk.magicindicator.buildins.commonnavigator.CommonNavigator;
import com.anju.yyk.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import com.anju.yyk.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import com.anju.yyk.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import com.anju.yyk.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import com.anju.yyk.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;
import com.anju.yyk.main.R;
import com.anju.yyk.main.R2;
import com.anju.yyk.main.ui.frg.accidentregister.AccidentRegisterFrg;
import com.anju.yyk.main.ui.frg.careregister.CareRegisterFrg;
import com.anju.yyk.main.ui.frg.register.RegisterFrg;
import com.anju.yyk.main.ui.frg.recordlist.CheckRoomRecordListFrg;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 * @author LeoWang
 *
 * @Package com.anju.yyk.main.ui.act.personinfo
 *
 * @Description 个人信息页面
 *
 * @Date 2019/10/16 15:37
 *
 * @modify:
 */
@Route(path = RouterConstants.ACT_URL_PERSON_INFO)
public class PersonInfoAct extends BaseActivity {

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

    @BindView(R2.id.tv_detail_info)
    TextView mDetailInfoTv;

    @BindView(R2.id.magicIndicator)
    MagicIndicator mIndicator;

    @BindView(R2.id.viewPager)
    ViewPager mViewPager;

    private TabPagerAdapter mAdapter;

    private List<Fragment> mFrgs = new ArrayList<>();

    @Autowired(name = RouterKey.BUNDLE_TAG)
    public PersonListResponse.ListBean mPersonInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.home_act_personinfo;
    }

    @Override
    protected void init() {
        ARouter.getInstance().inject(this);
        mToolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.common_theme));
        mToolbar.ivIsShow(true, false);
        setToolbarTopic(R.string.home_topic_person_info);
        prepareFrg();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        if (mPersonInfo.getSex() == 2){
            mSexIv.setImageResource(R.mipmap.home_ic_famale);
        }else {
            mSexIv.setImageResource(R.mipmap.home_ic_male);
        }
        mAgeTv.setText(mPersonInfo.getNianling());
        mNameTv.setText(mPersonInfo.getName());
        mBedIdTv.setText(mPersonInfo.getChuangwei() + "号床");
        mTagTv.setText(mPersonInfo.getHulijibie());

    }

    @Override
    protected void setupActivityComponent() {

    }

    @Override
    public boolean isRegisterEventBus() {
        return false;
    }

    private void prepareFrg(){
        CareRegisterFrg frg1 = new CareRegisterFrg();
        Bundle bundle1 = new Bundle();
        bundle1.putSerializable(RouterKey.BUNDLE_TAG, mPersonInfo);
        frg1.setArguments(bundle1);
        mFrgs.add(frg1);

        RegisterFrg frg2 = new RegisterFrg();
        Bundle bundle2 = new Bundle();
        bundle2.putSerializable(RouterKey.BUNDLE_TAG, mPersonInfo);
        frg2.setArguments(bundle2);
        mFrgs.add(frg2);

        AccidentRegisterFrg frg3 = new AccidentRegisterFrg();
        Bundle bundle3 = new Bundle();
        bundle3.putSerializable(RouterKey.BUNDLE_TAG, mPersonInfo);
        frg3.setArguments(bundle3);
        mFrgs.add(frg3);

        List<String> titles = new ArrayList<>();
        titles.add("护理登记");
        titles.add("查房登记");
        titles.add("事故登记");

        mAdapter = new TabPagerAdapter(getSupportFragmentManager(), mFrgs, titles);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(2);
        initMagicIndicator(titles);
    }

    private void initMagicIndicator(List<String> titles) {
        mIndicator.setBackgroundColor(Color.parseColor("#ffffff"));
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return titles.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorFlipPagerTitleView(context);
                simplePagerTitleView.setText(titles.get(index));
                simplePagerTitleView.setTextSize(14);//设置导航的文字大小
                simplePagerTitleView.setMaxLines(3);
                simplePagerTitleView.setNormalColor(ContextCompat.getColor(context, R.color.common_text_default));
                simplePagerTitleView.setSelectedColor(ContextCompat.getColor(context, R.color.common_theme));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(UIUtil.dip2px(context, 2));
                indicator.setLineWidth(UIUtil.dip2px(context, 13));
                indicator.setRoundRadius(UIUtil.dip2px(context, 3));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(ContextCompat.getColor(context, R.color.common_theme));
                return indicator;
            }
        });
        mIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mIndicator, mViewPager);

    }

    @OnClick({R2.id.tv_detail_info})
    public void onViewClicked(View v){
        if (v.getId() == R.id.tv_detail_info){
            ARouter.getInstance().build(RouterConstants.ACT_URL_INFO_DETAIL)
                    .withSerializable(RouterKey.BUNDLE_TAG, mPersonInfo)
                    .navigation();
        }
    }
}
