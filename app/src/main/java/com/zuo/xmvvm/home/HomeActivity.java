package com.zuo.xmvvm.home;

import com.yinglan.alphatabs.OnTabChangedListner;
import com.zuo.demo.lib_common.base.adapter.BaseFragmentAdapter;
import com.zuo.demo.lib_common.base.db.DataBindingConfig;
import com.zuo.demo.lib_common.base.model.TitleViewModel;
import com.zuo.demo.lib_common.base.ui.BaseActivity;
import com.zuo.demo.lib_common.base.ui.BaseFragment;
import com.zuo.xmvvm.BR;
import com.zuo.xmvvm.fragment.DemoFragment;
import com.zuo.xmvvm.R;
import com.zuo.xmvvm.list.ListFragment;

import java.util.ArrayList;

import androidx.viewpager2.widget.ViewPager2;

/**
 * @author zuo
 * @filename: HomeActivity
 * @date: 2020/10/27
 * @description: 描述
 * @version: V1.0
 */
public class HomeActivity extends BaseActivity<HomeViewModel> {
	@Override
	protected DataBindingConfig getDataBindingConfig() {
		return new DataBindingConfig(R.layout.activity_home, BR.viewModel).addBindingParam(BR.adapter, new MineViewPagerAdapter(this, getList()))
				.addBindingParam(BR.viewpagerCallback, new CallBack())
				.addBindingParam(BR.pagerChangeCallback, new MyOnTabChangedListner());
	}

	@Override
	protected void setTitle(TitleViewModel titleViewModel) {
		super.setTitle(titleViewModel);
		titleViewModel.setTitle("懒加载");
	}

	private ArrayList<BaseFragment> getList() {
		ArrayList<BaseFragment> arrayList = new ArrayList();
		arrayList.add(DemoFragment.getInstance("1"));
		arrayList.add(DemoFragment.getInstance("2"));
		arrayList.add(new ListFragment());
		return arrayList;
	}


	public class MineViewPagerAdapter extends BaseFragmentAdapter {
		public MineViewPagerAdapter(BaseActivity baseActivity, ArrayList<BaseFragment> list) {
			super(baseActivity, list);
		}
	}

	public class CallBack extends ViewPager2.OnPageChangeCallback {
		@Override
		public void onPageSelected(int position) {
			super.onPageSelected(position);
			if (position != mViewModel.getPosition().getValue()) {
				mViewModel.getPosition().setValue(position);
			}
		}

	}

	public class MyOnTabChangedListner implements OnTabChangedListner {
		@Override
		public void onTabSelected(int position) {
			if (position != mViewModel.getPosition().getValue()) {
				mViewModel.getPosition().setValue(position);
			}
		}
	}
}
