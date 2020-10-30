package com.zuo.xmvvm.main;

import android.Manifest;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zuo.demo.lib_common.base.db.DataBindingConfig;
import com.zuo.demo.lib_common.base.model.TitleViewModel;
import com.zuo.demo.lib_common.base.ui.BaseActivity;
import com.zuo.demo.lib_common.model.EventMsg;
import com.zuo.demo.lib_common.model.ZuoGobal;
import com.zuo.demo.lib_common.utils.EmptyUtils;
import com.zuo.xmvvm.BR;
import com.zuo.xmvvm.R;
import com.zuo.xmvvm.home.HomeActivity;
import com.zuo.xmvvm.list.ListActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * @author zuo
 * @filename: MainActivity
 * @date: 2020/10/21
 * @description: 描述
 * @version: V1.0
 */
public class MainActivity extends BaseActivity<MainActivityViewModel> {
	@Override
	protected DataBindingConfig getDataBindingConfig() {
		return new DataBindingConfig(R.layout.activity_main, BR.viewModel)
				.addBindingParam(BR.click, new ClickProxy());
	}

	@Override
	protected void initActivity() {
		super.initActivity();
		setEventTag(this);
	}

	@Override
	protected void setTitle(TitleViewModel titleViewModel) {
		super.setTitle(titleViewModel);
		titleViewModel.setLeftVisable(false);
		titleViewModel.setTitle("Main");
		titleViewModel.setRightVisable(true);
		titleViewModel.setRightTvDes("列表");
	}


	@Override
	protected void onRightPressEvent(TitleViewModel titleViewModel) {
		ActivityUtils.startActivity(ListActivity.class);
	}

	@Override
	protected void rxPermissionsSuccess(String tip, String... permissions) {
		ToastUtils.showShort("成功");
	}


	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onEvent(EventMsg eventMsg) {
		if (EmptyUtils.isNotEmpty(eventMsg ) &&  "tag".equals(eventMsg.getTag()) ){
			ToastUtils.showShort((String) eventMsg.getData());
		}
	}

	public class ClickProxy {
		public void goHomeActivity() {
			ActivityUtils.startActivity(HomeActivity.class);
		}

		public void getPermisstion() {
			rxPermissions(ZuoGobal.XPERMINSSION_TIP_CAMERA_AND_WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
		}

		public void sentMsg() {
			postEventBus("tag","收到信息");
		}
	}
}