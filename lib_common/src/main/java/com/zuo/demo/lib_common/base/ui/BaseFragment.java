package com.zuo.demo.lib_common.base.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.kongzue.dialog.v3.MessageDialog;
import com.kongzue.dialog.v3.TipDialog;
import com.kongzue.dialog.v3.WaitDialog;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zuo.demo.lib_common.BR;
import com.zuo.demo.lib_common.R;
import com.zuo.demo.lib_common.base.model.BaseViewModel;
import com.zuo.demo.lib_common.base.db.DataBindingConfig;
import com.zuo.demo.lib_common.base.model.RootViewModel;
import com.zuo.demo.lib_common.base.model.TitleViewModel;
import com.zuo.demo.lib_common.databinding.CommonToolbarBinding;
import com.zuo.demo.lib_common.databinding.ViewRootBinding;
import com.zuo.demo.lib_common.model.EventMsg;
import com.zuo.demo.lib_common.model.ZuoGobal;
import com.zuo.demo.lib_common.utils.EmptyUtils;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.ParameterizedType;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;
import me.jessyan.autosize.internal.CustomAdapt;

/**
 * @author zuo
 * @filename: BaseFragment
 * @date: 2020/10/22
 * @description: 基础类 BaseFragment
 * @version: V 1.0
 */
public abstract class BaseFragment<VM extends BaseViewModel> extends LazyLoadFragment implements CustomAdapt {

	private ViewDataBinding binding;
	protected VM mViewModel;
	private ViewModelProvider mActivityProvider;
	private RxPermissions mRxPermissions;
	protected BaseFragment eventTag; // eventbus tag
	protected BaseActivity mActivity;
	private RootViewModel rootViewModel;

	protected abstract DataBindingConfig getDataBindingConfig();

	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);
		mActivity = (BaseActivity) context;
	}


	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initFragment();
	}


	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = initRoot(inflater, container);
		registorUIChangeLiveDataCallBack();
		initData();
		return view;
	}



	protected void initFragment() {
	}

	protected void initData() {
	}
	/**
	 * 初始化根布局
	 */
	private View initRoot(LayoutInflater inflater, ViewGroup container) {
		DataBindingConfig dataBindingConfig = getDataBindingConfig();
		mViewModel = getActivityViewModel(getVMClass());
		dataBindingConfig.setStateViewModel(mViewModel);

		rootViewModel = getActivityViewModel(RootViewModel.class);
		ViewRootBinding mFragmentRootBinding = DataBindingUtil.inflate(inflater, R.layout.view_root, container, false);
		mFragmentRootBinding.setLifecycleOwner(this);
		mFragmentRootBinding.setVariable(BR.vm, rootViewModel);
		mFragmentRootBinding.setVariable(BR.myOnLoadMore, new MyOnLoadMore());
		mFragmentRootBinding.setVariable(BR.myOnRefresh, new MyOnRefresh());

		mFragmentRootBinding.viewStubContent.setOnInflateListener((viewStub, view) -> {
			binding = DataBindingUtil.bind(view);
			binding.setLifecycleOwner(BaseFragment.this);
			binding.setVariable(dataBindingConfig.getVmVariableId(), dataBindingConfig.getStateViewModel());
			SparseArray bindingParams = dataBindingConfig.getBindingParams();
			for (int i = 0, length = bindingParams.size(); i < length; i++) {
				binding.setVariable(bindingParams.keyAt(i), bindingParams.valueAt(i));
			}
		});
		mFragmentRootBinding.viewStubContent.getViewStub().setLayoutResource(dataBindingConfig.getLayout());
		mFragmentRootBinding.viewStubContent.getViewStub().inflate();
		rootViewModel.enableLoadMore.setValue(setEnableLoadMore());
		rootViewModel.enableRefresh.setValue(setEnableRefresh());
		initTitle(mFragmentRootBinding.commonTitle.toolbarRoot);
		return mFragmentRootBinding.getRoot();
	}


	/**
	 * 初始化头布局
	 *
	 * @param root
	 */
	private void initTitle(View root) {
		TitleViewModel titleViewModel = getActivityViewModel(TitleViewModel.class);
		CommonToolbarBinding commonToolbarBinding = DataBindingUtil.bind(root);
		commonToolbarBinding.setLifecycleOwner(BaseFragment.this);
		commonToolbarBinding.setVariable(BR.title, titleViewModel);
		setTitle(titleViewModel);
	}


	/**
	 * 设置头布局  ,子类重写
	 *
	 * @param titleViewModel
	 */
	@SuppressLint("FragmentLiveDataObserve")
	protected void setTitle(TitleViewModel titleViewModel) {
		titleViewModel.setLeftVisable(true);
		titleViewModel.setRightVisable(false);
		titleViewModel.getUC().getOnBackPressedEvent().observe(this, v -> {
			onLeftPressEvent(titleViewModel);
		});
		titleViewModel.getRightPressedEvent().observe(this, v -> {
			onRightPressEvent(titleViewModel);
		});
	}


	/**
	 * 左返回点击事件
	 *
	 * @param titleViewModel
	 */
	protected void onLeftPressEvent(TitleViewModel titleViewModel) {
		if (titleViewModel.isLeftVisable()) {
			mActivity.onBackPressed();
		}
	}

	/**
	 * 右边 文字  或者  图片点击事件
	 *
	 * @param titleViewModel
	 */
	protected void onRightPressEvent(TitleViewModel titleViewModel) {

	}

	/**
	 * 设置 可刷新
	 *
	 * @return
	 */
	protected boolean setEnableRefresh() {
		return false;
	}

	/**
	 * 社会可加载
	 *
	 * @return
	 */
	protected boolean setEnableLoadMore() {
		return false;
	}


	private void initDataBind(LayoutInflater inflater, ViewGroup container) {
		DataBindingConfig dataBindingConfig = getDataBindingConfig();
		mViewModel = getActivityViewModel(getVMClass());
		dataBindingConfig.setStateViewModel(mViewModel);
		binding = DataBindingUtil.inflate(inflater, dataBindingConfig.getLayout(), container, false);
		binding.setLifecycleOwner(this);
		binding.setVariable(dataBindingConfig.getVmVariableId(), dataBindingConfig.getStateViewModel());
		SparseArray bindingParams = dataBindingConfig.getBindingParams();
		for (int i = 0, length = bindingParams.size(); i < length; i++) {
			binding.setVariable(bindingParams.keyAt(i), bindingParams.valueAt(i));
		}
	}



	@Override
	public void onPause() {
		super.onPause();
		KeyboardUtils.hideSoftInput(mActivity);
	}

	@Override
	public void onDestroy() {
		if (EmptyUtils.isNotEmpty(eventTag) && isEventBusRegistered(eventTag)) {
			unregisterEventBus(eventTag);
		}
		binding.unbind();
		hideLoadView();
		super.onDestroy();
	}

	@SuppressLint("FragmentLiveDataObserve")
	protected void registorUIChangeLiveDataCallBack() {
		mViewModel.getUC().getShowLoadViewEvent().observe(this, aBoolean -> {
			if (aBoolean) {
				showLoadView(null);
			} else {
				hideLoadView();
			}
		});
		mViewModel.getUC().getCheckNetWork().observe(this, aBoolean -> {
			if (!aBoolean) {
				showTip("网络已断开");
			}
		});
		mViewModel.getUC().getShowMsg().observe(this, string -> ToastUtils.showShort(string));
		mViewModel.getUC().getShowTip().observe(this, string -> showTip(string));
		mViewModel.getUC().getOnBackPressedEvent().observe(this, v -> {
			mActivity.onBackPressed();
		});
		mViewModel.getUC().getCheckToken().observe(this, aBoolean -> {
		});
	}

	//显示加载的View
	public TipDialog showLoadView(String tips) {
		return WaitDialog.show(mActivity, EmptyUtils.isEmpty(tips) ? "载入中..." : tips).setCancelable(true);
	}

	//隐藏加载的View
	public void hideLoadView() {
		WaitDialog.dismiss();
	}

	public void showTip(String tip) {
		MessageDialog.show(mActivity, "提示", tip, "确定");
	}

	public boolean checkNetWork() {
		return NetworkUtils.isConnected();
	}

	@Override
	public boolean isBaseOnWidth() {
		return false;
	}

	@Override
	public float getSizeInDp() {
		return ZuoGobal.AUTO_SIZE_IN_DP;
	}

	protected void setEventTag(BaseFragment eventTag) {
		if (EmptyUtils.isNotEmpty(eventTag)) {
			this.eventTag = eventTag;
			registerEventBus(eventTag);
		}
	}

	/**
	 * 订阅EventBus
	 *
	 * @param subscribe 订阅
	 */
	protected void registerEventBus(Object subscribe) {
		if (!isEventBusRegistered(subscribe)) {
			EventBus.getDefault().register(subscribe);
		}
	}

	/**
	 * 是否订阅EventBus
	 *
	 * @param subscribe 订阅
	 * @return boolean
	 */
	protected boolean isEventBusRegistered(Object subscribe) {
		return EventBus.getDefault().isRegistered(subscribe);
	}

	/**
	 * 取消订阅EventBus
	 *
	 * @param subscribe
	 */
	protected void unregisterEventBus(Object subscribe) {
		if (isEventBusRegistered(subscribe)) {
			EventBus.getDefault().unregister(subscribe);
		}
	}


	/**
	 * 权限申请
	 *
	 * @param tip
	 * @param permissions
	 */
	public void rxPermissions(String tip, String... permissions) {
		if (mRxPermissions == null) {
			mRxPermissions = new RxPermissions(this);
		}
		if (EmptyUtils.isEmpty(permissions)) {
			LogUtils.w("rxPermissions", "Permissions is empty!!");
			return;
		}
		mRxPermissions.request(permissions)
				.subscribe(granted -> {
					if (granted) {
						rxPermissionsSuccess(tip, permissions);
					} else {
						if (EmptyUtils.isNotEmpty(tip)) {
							ToastUtils.showShort("需要" + tip + "权限");
						}
						PermissionUtils.launchAppDetailsSettings();
					}
				});
	}

	protected void rxPermissionsSuccess(String tip, String... permissions) {
	}

	public Class<VM> getVMClass() {
		//https://blog.csdn.net/lp840312696/article/details/83988637
		//返回表示此类型实际类型参数的 Type 对象的数组()，想要获取第二个泛型的Class，所以索引
		Class<VM> vmClass = (Class<VM>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		return vmClass;
	}

	protected <T extends BaseViewModel> T getActivityViewModel(@NonNull Class<T> modelClass) {
		if (mActivityProvider == null) {
			mActivityProvider = new ViewModelProvider(this);
		}
		return mActivityProvider.get(modelClass);
	}
	public void layoutOnRefresh(@NonNull RefreshLayout refreshLayout) {

	}

	public void layoutOnLoadMore(@NonNull RefreshLayout refreshLayout) {

	}

	public class MyOnRefresh implements OnRefreshListener {

		@Override
		public void onRefresh(@NonNull RefreshLayout refreshLayout) {
			layoutOnRefresh(refreshLayout);
		}
	}

	public class MyOnLoadMore implements OnLoadMoreListener {


		@Override
		public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
			layoutOnLoadMore(refreshLayout);
		}
	}

	/**
	 * 加载数据完成
	 * @param isLoadMore
	 */
	protected void finishLoadData(boolean isLoadMore) {
		if (rootViewModel != null){
			if (!isLoadMore) {
				rootViewModel.isRefreshFinish.setValue(true);
			} else {
				rootViewModel.isLoadMoreFinish.setValue(true);
			}
		}

	}

	/**
	 * 没有更多数据
	 */
	protected void noMoreData() {
		if (rootViewModel != null) {
			rootViewModel.isNODataMore.setValue(true);
		}
	}

	protected void postEventBus(String tag,Object value){
		EventBus.getDefault().post(new EventMsg(tag, value));
	}
}
