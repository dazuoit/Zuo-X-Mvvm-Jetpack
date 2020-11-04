package com.zuo.xmvvm.fragment;

import android.os.Bundle;
import android.view.View;

import com.zuo.demo.lib_common.base.db.DataBindingConfig;
import com.zuo.demo.lib_common.base.model.TitleViewModel;
import com.zuo.demo.lib_common.base.ui.BaseFragment;
import com.zuo.xmvvm.BR;
import com.zuo.xmvvm.R;

/**
 * @author zuo
 * @filename: DemoFragment
 * @date: 2020/10/27
 * @description: 页面
 * @version: v1.0
 */
public class DemoFragment extends BaseFragment<DemoVeiwModel> {

	public static DemoFragment getInstance(String title) {
		Bundle bundle = new Bundle();
		bundle.putString("name", title);
		DemoFragment fragment = new DemoFragment();
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	protected DataBindingConfig getDataBindingConfig() {
		return new DataBindingConfig(R.layout.fragment_demo, BR.viewModel);
	}

	@Override
	protected void lazyLoad() {
		Bundle arguments = getArguments();
		if (arguments != null) {
			String mTitle = arguments.getString("name");
			mViewModel.name.setValue(mTitle);

		}
	}

	@Override
	protected void setTitle(TitleViewModel titleViewModel) {
		super.setTitle(titleViewModel);
		titleViewModel.setTitleShow(View.GONE);
	}


}
