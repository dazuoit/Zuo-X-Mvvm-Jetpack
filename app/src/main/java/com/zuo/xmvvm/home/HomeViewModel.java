package com.zuo.xmvvm.home;

import com.zuo.demo.lib_common.base.model.BaseViewModel;
import com.zuo.demo.lib_common.base.db.SingleLiveEvent;

/**
 * @author zuo
 * @filename: HomeViewModel
 * @date: 2020/10/27
 * @description: 描述
 * @version: V1.0
 */
public class HomeViewModel extends BaseViewModel {

	private SingleLiveEvent<Integer> position ;

	public SingleLiveEvent<Integer> getPosition() {
		return position = createLiveData(position,-1);
	}


}
