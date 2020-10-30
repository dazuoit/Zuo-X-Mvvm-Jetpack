package com.zuo.demo.lib_common.base.model;


import android.view.View;

import com.zuo.demo.lib_common.base.db.SingleLiveEvent;

/**
 * @author zuo
 * @filename: TiTleViewModel
 * @date: 2020/10/23
 * @description: 标题相关
 * @version: V1.0
 */
public class TitleViewModel extends BaseViewModel {

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 左边返回键可见否
	 */
	private boolean leftVisable = false;

	/**
	 * 右边布局可见否
	 */
	private boolean rightVisable = false;

	/**
	 * 标题可见否
	 */
	private int isTitleShow = View.VISIBLE;

	/**
	 * 右边文字描述
	 */
	private String rightTvDes;

	/**
	 * 右边图片id
	 */
	private int rightImage;

	/**
	 * 右边点击事件
	 */
	private SingleLiveEvent<Void> onRightPressedEvent; //返回

	public SingleLiveEvent<Void> getRightPressedEvent() {
		return onRightPressedEvent = createLiveData(onRightPressedEvent);
	}

	public boolean isLeftVisable() {
		return leftVisable;
	}

	public void setLeftVisable(boolean leftBack) {
		this.leftVisable = leftBack;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int isTitleShow() {
		return isTitleShow;
	}

	public void setTitleShow(int titleShow) {
		isTitleShow = titleShow;
	}

	public String getRightTvDes() {
		return rightTvDes;
	}

	public void setRightTvDes(String rightTvDes) {
		this.rightTvDes = rightTvDes;
	}

	public int getRightImage() {
		return rightImage;
	}

	public void setRightImage(int rightImage) {
		this.rightImage = rightImage;
	}


	public boolean isRightVisable() {
		return rightVisable;
	}

	public void setRightVisable(boolean rightVisable) {
		this.rightVisable = rightVisable;
	}


	public void onRightPressedEvent() {
		getRightPressedEvent().call();
	}
}
