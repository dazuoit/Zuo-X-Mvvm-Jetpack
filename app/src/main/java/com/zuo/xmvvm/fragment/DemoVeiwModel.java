package com.zuo.xmvvm.fragment;

import com.zuo.demo.lib_common.base.model.BaseViewModel;

import androidx.lifecycle.MutableLiveData;

/**
 * @author zuo
 * @filename: DemoVeiwModel
 * @date: 2020/10/27
 * @description: 描述
 * @version: V1.0
 */
public class DemoVeiwModel extends BaseViewModel {
	public final MutableLiveData<String> name = new MutableLiveData<>();
}
