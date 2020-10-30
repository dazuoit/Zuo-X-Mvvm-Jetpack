package com.zuo.demo.lib_common.base.model;

import com.zuo.demo.lib_common.base.db.SingleLiveEvent;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;


/**
 * @author zuo
 * @filename: BaseRecycleAdaper
 * @date: 2020/5/2
 * @description: 适配器 基础类
 * @version: 1.0
 */
public class BaseViewModel extends ViewModel  {
	private UIChangeLiveData uc;


	public UIChangeLiveData getUC() {
		if (uc == null) {
			uc = new UIChangeLiveData();
		}
		return uc;
	}


	@Override
	protected void onCleared() {
		super.onCleared();
		//ViewModel销毁时会执行，同时取消所有异步任务
	}


	public final class UIChangeLiveData extends SingleLiveEvent {
		private SingleLiveEvent<Boolean> showLoadViewEvent;  //加载弹窗
		private SingleLiveEvent<String> showMsg;//toast
		private SingleLiveEvent<String> showTip;//弹窗
		private SingleLiveEvent<Boolean> checkNetWork;//网络连接
		private SingleLiveEvent<Boolean> checkToken;//token校验
		private SingleLiveEvent<Void> onBackPressedEvent; //返回

		public SingleLiveEvent<Boolean> getShowLoadViewEvent() {
			return showLoadViewEvent = createLiveData(showLoadViewEvent);
		}

		public SingleLiveEvent<String> getShowMsg() {
			return showMsg = createLiveData(showMsg);
		}

		public SingleLiveEvent<String> getShowTip() {
			return showTip = createLiveData(showTip);
		}

		public SingleLiveEvent<Boolean> getCheckNetWork() {
			return checkNetWork = createLiveData(checkNetWork);
		}

		public SingleLiveEvent<Boolean> getCheckToken() {
			return checkToken = createLiveData(checkToken);
		}

		public SingleLiveEvent<Void> getOnBackPressedEvent() {
			return onBackPressedEvent = createLiveData(onBackPressedEvent);
		}


		@Override
		public void observe(LifecycleOwner owner, Observer observer) {
			super.observe(owner, observer);
		}
	}
	protected  <T> SingleLiveEvent<T> createLiveData(SingleLiveEvent<T> liveData) {
		if (liveData == null) {
			liveData = new SingleLiveEvent<>();
		}
		return liveData;
	}

	/**
	 *
	 * @param liveData
	 * @param t
	 * @param <T> defaute
	 * @return
	 */
	protected  <T> SingleLiveEvent<T> createLiveData(SingleLiveEvent<T> liveData,T t) {
		if (liveData == null) {
			liveData = new SingleLiveEvent<>();
			liveData.setValue(t);
		}
		return liveData;
	}
	/**
	 * 关闭界面
	 */
	public void onBackPress() {
		uc.onBackPressedEvent.call();
	}
}
