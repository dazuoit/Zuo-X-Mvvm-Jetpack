package com.zuo.xmvvm.list;


import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.zuo.demo.lib_common.base.adapter.BaseRecycleAdaper;
import com.zuo.xmvvm.R;
import com.zuo.xmvvm.databinding.ListItemBinding;

import org.jetbrains.annotations.NotNull;

/**
 * @author zuo
 * @filename: DemoAdapter
 * @date: 2020/10/28
 * @description: 适配器
 * @version: V1.0
 */
public class DemoAdapter extends BaseRecycleAdaper<String , ListItemBinding> {

	public DemoAdapter() {
		super(R.layout.list_item);
	}

	@Override
	protected void bindingData(@NotNull BaseDataBindingHolder<ListItemBinding> holder, String s, ListItemBinding listItemBinding) {
		listItemBinding.setItemName(s);
	}

}
