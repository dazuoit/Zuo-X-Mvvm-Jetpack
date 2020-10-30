package com.zuo.demo.lib_common.glide;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.zhihu.matisse.engine.ImageEngine;

/**
 * @author zuo
 * @filename: MyGlideEngine
 * @date: 2020/4/15
 * @description: 知乎选择图片
 * @version: 1.0
 */
public class MyGlideEngine implements ImageEngine {
	@Override
	public void loadThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView, Uri uri) {
		MyGlide.disPlayByOption(context, uri, imageView, getRequestOptions(context, resize, placeholder, imageView, uri));
	}

	@Override
	public void loadGifThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView, Uri uri) {
		MyGlide.disPlayByOption(context, uri, imageView, getRequestOptions(context, resize, placeholder, imageView, uri));
	}

	@Override
	public void loadImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
		RequestOptions options = new RequestOptions()
				.centerInside()
				.priority(Priority.HIGH)
				.override(resizeX, resizeY);
		MyGlide.disPlayByOption(context, uri, imageView, options);
	}

	@Override
	public void loadGifImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
		RequestOptions options = new RequestOptions()
				.priority(Priority.HIGH)
				.override(resizeX, resizeY);
		MyGlide.disPlayByOption(context, uri, imageView, options);
	}

	@Override
	public boolean supportAnimatedGif() {
		return true;
	}

	public RequestOptions getRequestOptions(Context context, int resize, Drawable placeholder, ImageView imageView, Uri uri) {
		return new RequestOptions()
				.centerCrop()
				.placeholder(placeholder)//这里可自己添加占位图
				.override(resize, resize);
	}
}
