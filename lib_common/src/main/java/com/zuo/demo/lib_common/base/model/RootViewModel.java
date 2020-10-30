package com.zuo.demo.lib_common.base.model;

import androidx.lifecycle.MutableLiveData;

/**
 * @author zuo
 * @filename: RootViewModel
 * @date: 2020/10/23
 * @description: RootViewModel 所有基类页面通用
 * @version: V1.0
 */
public class RootViewModel extends BaseViewModel {
	public final MutableLiveData<Boolean> enableLoadMore = new MutableLiveData<>();
	public final MutableLiveData<Boolean> enableRefresh = new MutableLiveData<>();
	public final MutableLiveData<Boolean> isRefreshFinish = new MutableLiveData<>();
	public final MutableLiveData<Boolean> isLoadMoreFinish = new MutableLiveData<>();
	public final MutableLiveData<Boolean> isNODataMore = new MutableLiveData<>();

}
