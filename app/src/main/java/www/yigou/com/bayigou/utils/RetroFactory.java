package www.yigou.com.bayigou.utils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by fan on 2017/11/8.
 * 网络请求工具类Rejava + ReTrofit + Okhttp3
 */

public class RetroFactory {

    //构造
    private RetroFactory() {

    }

    private static OkHttpClient httpClient = new OkHttpClient.Builder()
//            .addInterceptor(new LoggingInterceptor())//添加拦截器
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();
    private static ApiServer retrofitService = new Retrofit.Builder()
            .baseUrl(Api.HOME_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//            .client(httpClient)//添加拦截器
            .build()
            .create(ApiServer.class);
    //单列模式
    public static ApiServer getInstance() {
        return retrofitService;
    }


}
