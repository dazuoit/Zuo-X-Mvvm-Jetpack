package com.zuo.demo.lib_common.glide;

import android.content.Context;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.TransitionOptions;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.zuo.demo.lib_common.R;

/**
 * @author zuo
 * @filename: MyGlide
 * @date: 2020/4/15
 * @description: MyGlide
 * @version: 1.0
 */
public class MyGlide {

	public static void disPlay(Context context, Object object, ImageView imageView){
		disPlayDetails(context,object,imageView,null,null,null);
	}
	public static void disPlayPlace(Context context, Object object, ImageView imageView){
		disPlayDetails(context,object,imageView,new RequestOptions().placeholder(R.mipmap.img_error).error(R.mipmap.img_error_dark),null,null);
	}
	public static void disPlayCircle(Context context, Object object, ImageView imageView){
		disPlayDetails(context,object,imageView,new RequestOptions().bitmapTransform(new CircleCrop()),null,null);
	}
	public static void disPlayRoundedCorners(Context context, Object object, ImageView imageView,int roundingRadius){
		disPlayDetails(context,object,imageView,new RequestOptions().bitmapTransform(new RoundedCorners(roundingRadius)),null,null);
	}

	public static void disPlayByOption(Context context, Object object, ImageView imageView, RequestOptions requestOptions){
		disPlayDetails(context,object,imageView,requestOptions,null,null);
	}

	public static void disPlayDetails(Context context, Object object, ImageView imageView, RequestOptions requestOptions, RequestListener requestListener, TransitionOptions transitionOptions){
		if (context == null || object == null || imageView == null){
			return;
		}
		DrawableCrossFadeFactory drawableCrossFadeFactory = new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build();
		Glide.with(context)
				.load(object)
				.apply(requestOptions == null ? new  RequestOptions() : requestOptions)
				.transition(transitionOptions == null ? DrawableTransitionOptions.with(drawableCrossFadeFactory):  transitionOptions)
				.listener(requestListener)
				.into(imageView);
	}

}
