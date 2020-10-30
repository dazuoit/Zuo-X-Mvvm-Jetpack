package com.zuo.xmvvm.net;

import com.zuo.demo.lib_common.net.RetrofitManager;

/**
 * @author zuo
 * @filename: NetService
 * @date: 2020/4/20
 * @description: 描述
 * @version: 版本号
 */
public class NetService {

	public static NetService getInstance() {
		return NetServiceHolder.netServiceHolder;
	}

	private static class NetServiceHolder {
		private static NetService netServiceHolder = new NetService();
	}
	/**
	 * 创建一个公共服务
	 *
	 * @return
	 */
	public CommonService getCommonService() {
		return RetrofitManager.getInstance().getmRetrofit().create(CommonService.class);
	}
}
