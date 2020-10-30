package com.zuo.xmvvm.net;


import com.zuo.demo.lib_common.net.RespData;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CommonService {
	@GET("qsonhs.aspx")
	Observable<RespData<NewsDetail>> getNews(@Query("q") String q);
}
