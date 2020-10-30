package com.zuo.demo.lib_common.base.ui;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.zuo.demo.lib_common.BR;
import com.zuo.demo.lib_common.R;
import com.zuo.demo.lib_common.base.adapter.BaseRecycleAdaper;
import com.zuo.demo.lib_common.base.model.BaseRecycleViewViewModel;
import com.zuo.demo.lib_common.base.db.DataBindingConfig;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author zuo
 * @filename: BaseRecycleViewFragment
 * @date: 2020/10/23
 * @description: BaseRecycleViewFragment
 * @version: V1.0
 */
public abstract class BaseRecycleViewFragment<vm extends BaseRecycleViewViewModel, T extends BaseRecycleAdaper> extends BaseFragment<vm> implements OnItemClickListener, OnItemChildClickListener {

	protected volatile T mAdapter;

	protected abstract T createAdapter();

	public boolean isNeedNet() {
		return false;
	}

	@Override
	protected DataBindingConfig getDataBindingConfig() {
		mAdapter = createAdapter();
		return new DataBindingConfig(R.layout.view_recycleview_root, BR.viewModel)
				.addBindingParam(BR.layoutManager, getLayoutManager())
				.addBindingParam(BR.rcAdapter, mAdapter);
	}

	protected RecyclerView.LayoutManager getLayoutManager() {
		return new LinearLayoutManager(mActivity);
	}

	@Override
	protected boolean setEnableLoadMore() {
		return true;
	}

	@Override
	protected boolean setEnableRefresh() {
		return true;
	}

	@Override
	protected void initData() {
		mAdapter.setOnItemClickListener(this);
		mAdapter.setOnItemChildClickListener(this);
	}


	protected void reLoadData() {
		checkNetOrLoadData(false);
	}

	protected void checkNetOrLoadData(boolean isLoadMore) {
		mViewModel.getIsLoadMore().setValue(isLoadMore);
		if (isNeedNet() && checkNetWork()) {
			loadData(isLoadMore);
		} else {
			loadData(isLoadMore);
		}
	}

	protected abstract void loadData(boolean isLoadMore);


	@Override
	public void layoutOnRefresh(@NonNull RefreshLayout refreshLayout) {
		refreshLayout.setNoMoreData(false);
		mViewModel.setPage(1);
		mViewModel.setTime(0L);
		mAdapter.setNewInstance(null);
		checkNetOrLoadData(false);
	}

	@Override
	public void layoutOnLoadMore(@NonNull RefreshLayout refreshLayout) {
		checkNetOrLoadData(true);
	}


	@Override
	public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {

	}

	@Override
	public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {

	}

}
