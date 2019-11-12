package com.anju.yyk.main.ui.frg.record;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.anju.yyk.common.adapter.TabPagerAdapter;
import com.anju.yyk.common.app.arouter.RouterKey;
import com.anju.yyk.common.base.BaseFragment;
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
import com.anju.yyk.main.ui.frg.recordlist.AccidentRecordListFrg;
import com.anju.yyk.main.ui.frg.recordlist.CareRecordListFrg;
import com.anju.yyk.main.ui.frg.recordlist.CheckRoomRecordListFrg;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 
 * @author LeoWang
 * 
 * @Package com.anju.yyk.main.ui.frg.record
 * 
 * @Description 记录查询
 * 
 * @Date 2019/9/5 15:21
 * 
 * @modify:
 */
public class RecordFrg extends BaseFragment {

    @BindView(R2.id.magicIndicator)
    MagicIndicator mIndicator;

    @BindView(R2.id.child_viewPager)
    ViewPager mViewPager;

    private TabPagerAdapter mAdapter;

    private List<Fragment> mFrgs = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.home_frg_record;
    }

    @Override
    public void init() {

    }

    @Override
    public void initListener() {

    }

    @Override
    protected void initData() {
        prepareFrg();
    }

    @Override
    protected void setupFragmentComponent() {

    }

    @Override
    protected boolean isRegisterEventBus() {
        return false;
    }

    private void prepareFrg(){
        CareRecordListFrg frg1 = new CareRecordListFrg();
        mFrgs.add(frg1);

        CheckRoomRecordListFrg frg2 = new CheckRoomRecordListFrg();
        mFrgs.add(frg2);

        AccidentRecordListFrg frg3 = new AccidentRecordListFrg();
        mFrgs.add(frg3);

        List<String> titles = new ArrayList<>();
        titles.add("护理记录");
        titles.add("查房记录");
        titles.add("事故记录");

        mAdapter = new TabPagerAdapter(getChildFragmentManager(), mFrgs, titles);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(2);
        initMagicIndicator(titles);
    }

    private void initMagicIndicator(List<String> titles) {
        mIndicator.setBackgroundColor(Color.parseColor("#ffffff"));
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
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
                simplePagerTitleView.setTextSize(20);//设置导航的文字大小
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
}
