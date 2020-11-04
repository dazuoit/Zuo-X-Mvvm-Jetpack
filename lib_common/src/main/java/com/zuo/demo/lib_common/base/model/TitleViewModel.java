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

	public TitleViewModel setLeftVisable(boolean leftBack) {
		this.leftVisable = leftBack;
		return  this;
	}

	public String getTitle() {
		return title;
	}

	public TitleViewModel setTitle(String title) {
		this.title = title;
		return  this;
	}

	public int isTitleShow() {
		return isTitleShow;
	}

	public TitleViewModel setTitleShow(int titleShow) {
		isTitleShow = titleShow;
		return this;
	}

	public String getRightTvDes() {
		return rightTvDes;
	}

	public TitleViewModel setRightTvDes(String rightTvDes) {
		this.rightTvDes = rightTvDes;
		return this;
	}

	public int getRightImage() {
		return rightImage;
	}

	public TitleViewModel setRightImage(int rightImage) {
		this.rightImage = rightImage;
		return this;
	}


	public boolean isRightVisable() {
		return rightVisable;
	}

	public TitleViewModel setRightVisable(boolean rightVisable) {
		this.rightVisable = rightVisable;
		return this;
	}


	public TitleViewModel onRightPressedEvent() {
		getRightPressedEvent().call();
		return this;
	}
}
