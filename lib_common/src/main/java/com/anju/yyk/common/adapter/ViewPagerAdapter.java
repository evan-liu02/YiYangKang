package com.anju.yyk.common.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

/**
 * 
 * @author LeoWang
 * 
 * @Package cn.com.drpeng.runman.common.adapter
 * 
 * @Description ViewPager+Fragment使用此类
 * 
 * @Date 2019/6/4 17:46
 * 
 * @modify:
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private FragmentManager mFragmentManager;

    private FragmentTransaction mFragmentTransaction;

    public List<Fragment> list;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> list){
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mFragmentManager = fm;
        mFragmentTransaction = mFragmentManager.beginTransaction();
        this.list = list;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        mFragmentManager.beginTransaction().show(fragment).commit();
        return fragment;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        Fragment fragment = list.get(position);// 获取要销毁的fragment
        mFragmentManager.beginTransaction().hide(fragment).commit();
    }
}
