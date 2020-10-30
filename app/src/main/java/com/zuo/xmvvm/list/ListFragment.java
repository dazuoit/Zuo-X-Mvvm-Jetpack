package com.zuo.xmvvm.list;

import android.view.View;

import com.zuo.demo.lib_common.base.model.TitleViewModel;
import com.zuo.demo.lib_common.base.ui.BaseRecycleViewFragment;

import java.util.ArrayList;

/**
 * @author zuo
 * @filename: ListActivity
 * @date: 2020/10/28
 * @description: 描述
 * @version: 版本号
 */
public class ListFragment extends BaseRecycleViewFragment<ListDemoViewModel, DemoAdapter> {


	@Override
	protected DemoAdapter createAdapter() {
		return new DemoAdapter();
	}

	@Override
	protected void setTitle(TitleViewModel titleViewModel) {
		super.setTitle(titleViewModel);
		titleViewModel.setTitleShow(View.GONE);
	}

	@Override
	protected void lazyLoad() {
		reLoadData();
	}

	@Override
	protected void loadData(boolean isLoadMore) {

		// 模拟数据处理
		ArrayList<String> list = new ArrayList<>();
		for (int i = (mViewModel.getPage() - 1) * 20; i < mViewModel.getPage() * 20.; i++) {
			list.add(i + "");
		}
		mAdapter.addData(list);
		mViewModel.setPage(mViewModel.getPage() + 1);
		finishLoadData(isLoadMore);
		if (mViewModel.getPage() > 3) {
			noMoreData();
		}
	}
}
