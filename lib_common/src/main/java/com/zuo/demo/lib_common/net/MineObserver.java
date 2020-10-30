package com.zuo.demo.lib_common.net;


import com.zuo.demo.lib_common.utils.EmptyUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author zuo
 * @filename: MineObserver
 * @date: 2020/4/20
 * @description: 自定义回调  根据要求自己拓展
 * @version: 1.0
 */
public class MineObserver<T> implements Observer<RespData<T>> {

	public MineObserver() {

	}

	@Override
	public void onSubscribe(Disposable d) {

	}

	@Override
	public void onNext(RespData<T> t) {

	}

	@Override
	public void onError(Throwable e) {

	}

	@Override
	public void onComplete() {

	}
}
