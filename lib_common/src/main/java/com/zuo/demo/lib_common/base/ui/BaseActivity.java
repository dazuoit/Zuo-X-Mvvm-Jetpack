package com.zuo.demo.lib_common.base.ui;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.BarUtils;
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
import com.zuo.demo.lib_common.utils.ActivityManagerUtils;
import com.zuo.demo.lib_common.utils.EmptyUtils;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.ParameterizedType;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;
import me.jessyan.autosize.internal.CustomAdapt;

/**
 * @author zuo
 * @filename: BaseActivity
 * @date: 2020/10/22
 * @description:
 *
 *   提供一个有通用布局下的解决方案,更具具体需求自行更改
 *   默认每个页面都有相同头布局与 上拉与加载控件
 *   每个组件职能边界清晰  组件功能明确, 例如子类 ViewDataBinding 设计理念上最好不要直接调用,如KunMinX所说,所有组件职能边界点到为止,
 *   父类功能越齐全,子类代码越通一化越简单清晰,但是相应的拓展性受限,部分功能冗余,此框架便是在这2者之间寻求平衡,父类仅仅展示通用的部分,经过简单修改便可以全局统一
 *
 * @version: V1.0
 */
public abstract class BaseActivity<VM extends BaseViewModel> extends AppCompatActivity implements CustomAdapt {
	protected ViewDataBinding binding;
	protected VM mViewModel;
	private ViewModelProvider mActivityProvider;
	private RxPermissions mRxPermissions;
	protected BaseActivity eventTag; // eventbus tag
	private RootViewModel rootViewModel;


	protected abstract DataBindingConfig getDataBindingConfig();

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivityManagerUtils.getInstance().addActivity(this);
		initActivity();
		initRoot();
		registorUIChangeLiveDataCallBack();
		initData();
	}

	/**
	 * 初始化界面
	 */
	protected void initActivity() {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

	/**
	 * 初始化根布局
	 */
	private void initRoot() {
		DataBindingConfig dataBindingConfig = getDataBindingConfig();
		mViewModel = getActivityViewModel(getVMClass());
		dataBindingConfig.setStateViewModel(mViewModel);
		rootViewModel = getActivityViewModel(RootViewModel.class);

		ViewRootBinding mActivityRootBinding = DataBindingUtil.setContentView(this, R.layout.view_root);
		mActivityRootBinding.setLifecycleOwner(this);
		mActivityRootBinding.setVariable(BR.vm, rootViewModel);
		mActivityRootBinding.setVariable(BR.myOnLoadMore, new MyOnLoadMore());
		mActivityRootBinding.setVariable(BR.myOnRefresh, new MyOnRefresh());

		mActivityRootBinding.viewStubContent.setOnInflateListener((viewStub, view) -> {
			binding = DataBindingUtil.bind(view);
			binding.setLifecycleOwner(BaseActivity.this);
			binding.setVariable(dataBindingConfig.getVmVariableId(), dataBindingConfig.getStateViewModel());
			SparseArray bindingParams = dataBindingConfig.getBindingParams();
			for (int i = 0, length = bindingParams.size(); i < length; i++) {
				binding.setVariable(bindingParams.keyAt(i), bindingParams.valueAt(i));
			}
		});
		mActivityRootBinding.viewStubContent.getViewStub().setLayoutResource(dataBindingConfig.getLayout());
		mActivityRootBinding.viewStubContent.getViewStub().inflate();

		rootViewModel.enableLoadMore.setValue(setEnableLoadMore());
		rootViewModel.enableRefresh.setValue(setEnableRefresh());
		initTitle(mActivityRootBinding.commonTitle.toolbarRoot);

	}



	/**
	 * 初始化头布局
	 *
	 * @param root
	 */
	private void initTitle(View root) {
		TitleViewModel titleViewModel = getActivityViewModel(TitleViewModel.class);
		CommonToolbarBinding commonToolbarBinding = DataBindingUtil.bind(root);
		commonToolbarBinding.setLifecycleOwner(BaseActivity.this);
		commonToolbarBinding.setVariable(BR.title, titleViewModel);
		setTitle(titleViewModel);
	}


	/**
	 * 设置头布局  ,子类重写
	 *
	 * @param titleViewModel
	 */
	protected void setTitle(TitleViewModel titleViewModel) {
		titleViewModel.setLeftVisable(true)
		.setRightVisable(false);
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
			onBackPressed();
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
	 * 设置 可加载
	 *
	 * @return
	 */
	protected boolean setEnableLoadMore() {
		return false;
	}


	/**
	 * 初始化数据
	 */
	protected void initData() {

	}


	@Override
	protected void onPause() {
		super.onPause();
		KeyboardUtils.hideSoftInput(this);
	}

	@Override
	protected void onDestroy() {
		if (EmptyUtils.isNotEmpty(eventTag) && isEventBusRegistered(eventTag)) {
			unregisterEventBus(eventTag);
		}
		binding.unbind();
		hideLoadView();
		super.onDestroy();
		ActivityManagerUtils.getInstance().finishActivity(this);
	}


	/**
	 * 通用逻辑
	 */
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
		mViewModel.getUC().getOnBackPressedEvent().observe(this, v -> onBackPressed());
		mViewModel.getUC().getCheckToken().observe(this, aBoolean -> {
		});
	}

	//显示加载的View
	public TipDialog showLoadView(String tips) {
		return WaitDialog.show(this, EmptyUtils.isEmpty(tips) ? "载入中..." : tips).setCancelable(true);
	}

	//隐藏加载的View
	public void hideLoadView() {
		WaitDialog.dismiss();
	}

	/**
	 * 展示弹窗提示
	 * @param tip
	 */
	public void showTip(String tip) {
		MessageDialog.show(this, "提示", tip, "确定");
	}


	/**
	 * 校验网络
	 * @return
	 */
	public boolean checkNetWork() {
		if (!NetworkUtils.isConnected()) {
			// 自行处理
			showTip("error net");
		}
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

	/**
	 * 注册eventbus
	 * @param eventTag
	 */
	protected void setEventTag(BaseActivity eventTag) {
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
	 * 设置沉浸式状态栏
	 */
	public void transparentStatusBar() {
		View decView = findViewById(android.R.id.content);
		if (EmptyUtils.isNotEmpty(decView)) {
			if (decView instanceof ViewGroup) {
				ViewGroup vg = 	((ViewGroup) decView);
				if (vg.getChildCount() != 0 ){
					vg.getChildAt(0).setFitsSystemWindows(false);
				}
			}
		}
		BarUtils.transparentStatusBar(this);
	}

	/**
	 * 权限申请
	 *
	 * @param tip
	 * @param permissions
	 */
	@SuppressLint("CheckResult")
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
	 *
	 * @param isLoadMore
	 */
	protected void finishLoadData(boolean isLoadMore) {
		if (rootViewModel != null) {
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
