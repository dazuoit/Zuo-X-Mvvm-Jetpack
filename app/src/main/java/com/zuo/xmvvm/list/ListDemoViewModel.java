package com.zuo.xmvvm.list;

import com.zuo.demo.lib_common.base.model.BaseRecycleViewViewModel;
import com.zuo.demo.lib_common.base.db.SingleLiveEvent;
import com.zuo.xmvvm.net.NewsDetail;

/**
 * @author zuo
 * @filename: ListDemoViewModel
 * @date: 2020/10/28
 * @description: 描述
 * @version: V1.0
 */
public class ListDemoViewModel extends BaseRecycleViewViewModel {

	public SingleLiveEvent<NewsDetail> newsData ;

	public SingleLiveEvent<NewsDetail> getNewsData() {
		return newsData = createLiveData(newsData);
	}


}
