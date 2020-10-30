package com.zuo.demo.lib_common.base.adapter;

import com.zuo.demo.lib_common.base.ui.BaseActivity;
import com.zuo.demo.lib_common.base.ui.BaseFragment;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

/**
 * @author zuo
 * @filename: BaseFragmentAdapter
 * @date: 2020/4/13
 * @description: BaseFragmentAdapter 基类
 * @version: 1.0
 */

public abstract class BaseFragmentAdapter extends FragmentStateAdapter  {

	protected  ArrayList<BaseFragment> mList;

	public BaseFragmentAdapter(BaseActivity baseActivity, ArrayList<BaseFragment> list) {
		super(baseActivity);
		this.mList = list;
	}

	@Override
	public Fragment createFragment(int position) {
		return mList.get(position);
	}

	@Override
	public int getItemCount() {
		return  mList.size();
	}


}
