package com.zuo.xmvvm.net;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.zuo.demo.lib_common.base.db.SingleLiveEvent;
import com.zuo.demo.lib_common.net.MJsonUtils;
import com.zuo.demo.lib_common.net.MineObserver;
import com.zuo.demo.lib_common.net.RespData;
import com.zuo.demo.lib_common.net.RetrofitManager;

import io.reactivex.Observable;

/**
 * @author zuo
 * @filename: DataRepository
 * @date: 2020/10/30
 * @description: 描述
 * @version: V1.0
 */
public class DataRepository {

	public static DataRepository getInstance() {
		return DataRepositoryHolder.dataRepository;
	}

	private static class DataRepositoryHolder {
		private static DataRepository dataRepository = new DataRepository();
	}


	protected <T> void forNet(Observable observable, MineObserver<T> observer) {
		if (NetworkUtils.isConnected()) {
			RetrofitManager.getInstance().forNet(observable, observer);
		}
	}

	protected MJsonUtils getNetJson() {
		return new MJsonUtils();
	}


	public void getNews(SingleLiveEvent<NewsDetail> liveData, Integer page) {
		forNet(NetService.getInstance().getCommonService().getNews("mvvm"), new MineObserver<NewsDetail>() {
			@Override
			public void onNext(RespData<NewsDetail> t) {
				liveData.postValue(t.AS);
			}

			@Override
			public void onError(Throwable e) {
				LogUtils.w("getCommonService_error",e.toString());
				liveData.postValue(null);
			}
		});
	}

}
