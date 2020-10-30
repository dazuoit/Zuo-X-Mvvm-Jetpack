package com.zuo.demo.lib_common.net;

import com.blankj.utilcode.util.ToastUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * FileName: RxAdapter
 * Author: zuo
 * Date: 2018/6/6
 * Description:网络处理
 * Version: 1.0
 */
public class RxAdapter {


    /**
     * 线程调度器
     */
    public static ObservableTransformer schedulersTransformer() {
        return new ObservableTransformer() {
            @Override
            public ObservableSource apply(Observable upstream) {
                return upstream.subscribeOn(Schedulers.newThread())
                        .observeOn(Schedulers.io());
            }
        };
    }

    public static ObservableTransformer exceptionTransformer() {

        return new ObservableTransformer() {
            @Override
            public ObservableSource apply(Observable observable) {
                return observable
                        .map(new HandleFuc())  //这里可以取出BaseResponse中的Result
                        .onErrorResumeNext(new HttpResponseFunc());
            }
        };
    }

    private static class HttpResponseFunc<T> implements Function<Throwable, Observable<T>> {
        @Override
        public Observable<T> apply(Throwable t) {
            ResponseThrowable exception = ExceptionHandler.handleException(t);
            if(exception.code ==  ExceptionHandler.SYSTEM_ERROR.TIMEOUT_ERROR ){
                ToastUtils.showShort("网络不给力哦！");
            }//自己拓展
            return Observable.error(exception);
        }
    }

    private static class HandleFuc implements Function<Object,Object> {

        @Override
        public Object apply(Object o) throws Exception {
            if(o instanceof RespData){
                RespData respDTO = (RespData) o;
//                if(respDTO.code != 200 ){
//                    // ToastUtils.showShort(respDTO.msg);
//                }
            }
            return o;
        }
    }


}
