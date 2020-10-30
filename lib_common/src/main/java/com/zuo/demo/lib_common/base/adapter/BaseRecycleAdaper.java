package com.zuo.demo.lib_common.base.adapter;



import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.LayoutRes;
import androidx.databinding.ViewDataBinding;


/**
 * @author zuo
 * @filename: BaseRecycleAdaper
 * @date: 2020/4/21
 * @description: 适配器 基础类
 * @version: 1.0
 */
public abstract class BaseRecycleAdaper<T, K extends ViewDataBinding> extends BaseQuickAdapter<T, BaseDataBindingHolder<K>> {

	public BaseRecycleAdaper(@LayoutRes int  layoutRes) {
		super(layoutRes);
	}


	@Override
	protected  void convert(@NotNull BaseDataBindingHolder<K> holder, T t){
		K binding = holder.getDataBinding();
		if (binding != null){
			bindingData(holder,t,binding);
			//不加会闪烁,所以统一加载基类里
			binding.executePendingBindings();
		}
	}

	protected abstract void bindingData(@NotNull BaseDataBindingHolder<K> holder, T t,K k) ;
}
