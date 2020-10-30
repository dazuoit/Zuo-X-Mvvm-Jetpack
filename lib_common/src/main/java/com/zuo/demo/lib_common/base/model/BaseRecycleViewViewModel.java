package com.zuo.demo.lib_common.base.model;

import com.zuo.demo.lib_common.base.db.SingleLiveEvent;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author zuo
 * @filename: BaseRecycleViewViewModel
 * @date: 2020/4/21
 * @description: 列表基础 VM
 * @version: 1.0
 */
public class BaseRecycleViewViewModel extends BaseViewModel  {
	//分页
	private Integer page = 1 ;
	//时间戳
	private Long time = 0L ;

	private SingleLiveEvent<RecyclerView.LayoutManager> layoutManager ;

	private SingleLiveEvent<Boolean> isLoadMore ;


	public SingleLiveEvent<Boolean>getIsLoadMore() {
		return isLoadMore = createLiveData(isLoadMore,true);
	}
	public SingleLiveEvent<RecyclerView.LayoutManager> getLayoutManager() {
		return layoutManager = createLiveData(layoutManager);
	}


	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}


	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}





}
