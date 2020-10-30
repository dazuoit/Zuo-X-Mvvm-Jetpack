package com.zuo.demo.lib_common.net;

import com.zuo.demo.lib_common.model.ZuoGobal;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * FileName: RetrofitManager
 * Author: zuo
 * Date: 2018/6/6
 * Description:网络单例
 * Version: 1.0
 */
public  class RetrofitManager {

	private Retrofit mRetrofit;
	private OkHttpClient.Builder okHttpBuilder;

	public static RetrofitManager getInstance() {
		return RetrofitManagerHolder.retrofitManager;
	}

	private static class RetrofitManagerHolder {
		private static RetrofitManager retrofitManager = new RetrofitManager();
	}
	public Retrofit getmRetrofit() {
		return mRetrofit;
	}
	private RetrofitManager() {
		//给client的builder添加了一个日志拦截器
		HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
		if (ZuoGobal.bIsOpenLog) {
			logging.setLevel(HttpLoggingInterceptor.Level.BODY);
		} else {
			logging.setLevel(HttpLoggingInterceptor.Level.NONE);
		}
		okHttpBuilder = new OkHttpClient.Builder();
		okHttpBuilder.connectTimeout(ZuoGobal.CONNECT_TIME_OUT, TimeUnit.MILLISECONDS).
				readTimeout(ZuoGobal.CONNECT_TIME_OUT, TimeUnit.MILLISECONDS).
				writeTimeout(ZuoGobal.CONNECT_TIME_OUT, TimeUnit.MILLISECONDS);
		okHttpBuilder.addInterceptor(mHeaderInterceptor);
		okHttpBuilder.addInterceptor(logging);
		okHttpBuilder.hostnameVerifier(SSLContextUtil.HOSTNAME_VERIFIER);

		//创建client
		OkHttpClient okHttpClient = okHttpBuilder.build();
		mRetrofit = new Retrofit.Builder()
				.client(okHttpClient)
				.baseUrl(ZuoGobal.URL_ROOT_HOST)
				.addConverterFactory(ScalarsConverterFactory.create())
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.addConverterFactory(GsonConverterFactory.create())
				.build();
	}


	/**
	 * 请求头封装
	 */
	private  Interceptor mHeaderInterceptor = chain -> {
		Request request = chain.request();
		Request.Builder builder = interceptBuild(request.newBuilder());
		//根据要求自己编写
		return chain.proceed(builder.build());
	};

	public  Request.Builder interceptBuild(Request.Builder builder) {
		//header增加
		return builder;
	}


	public <T> void forNet(Observable observable, MineObserver<T> observer) {
		observable.compose(RxAdapter.schedulersTransformer())
				.compose(RxAdapter.exceptionTransformer()).subscribe(observer);
	}
}