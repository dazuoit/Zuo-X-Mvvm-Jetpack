package com.zuo.xmvvm.list;

import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kongzue.dialog.interfaces.OnMenuItemClickListener;
import com.zuo.demo.lib_common.base.model.TitleViewModel;
import com.zuo.demo.lib_common.base.ui.BaseRecycleViewActivity;
import com.zuo.demo.lib_common.utils.EmptyUtils;
import com.zuo.xmvvm.net.DataRepository;
import com.zuo.xmvvm.net.NewsDetail;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;

/**
 * @author zuo
 * @filename: ListActivity
 * @date: 2020/10/28
 * @description: 描述
 * @version: 版本号
 */
public class ListActivity extends BaseRecycleViewActivity<ListDemoViewModel, DemoAdapter> {
	@Override
	protected DemoAdapter createAdapter() {
		return new DemoAdapter();
	}

	@Override
	protected void setTitle(TitleViewModel titleViewModel) {
		super.setTitle(titleViewModel);
		titleViewModel.setTitle("网络请求列表");
	}

	@Override
	protected void initData() {
		super.initData();
		mViewModel.getNewsData().observe(this, newsDetail -> {
			hideLoadView();
			finishLoadData(mViewModel.getIsLoadMore().getValue());
			if (newsDetail != null) {
				ArrayList<String> arrayList = new ArrayList<>();
				for (int i = 0; i < newsDetail.getResults().get(0).getSuggests().size(); i++) {
					arrayList.add(newsDetail.getResults().get(0).getSuggests().get(i).getTxt());
				}
				mAdapter.addData(arrayList);
				noMoreData();
			}
		});
		reLoadData();
	}

	@Override
	protected void loadData(boolean isLoadMore) {
		showLoadView(null);
		DataRepository.getInstance().getNews(mViewModel.getNewsData(), mViewModel.getPage());
	}


	@Override
	public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
		ToastUtils.showShort(mAdapter.getData().get(position));
	}
}
