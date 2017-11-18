package www.yigou.com.bayigou.sort.model;

import android.util.Log;

import java.util.HashMap;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import www.yigou.com.bayigou.sort.bean.Catagory;
import www.yigou.com.bayigou.sort.bean.ProductCatagory;
import www.yigou.com.bayigou.utils.Api;
import www.yigou.com.bayigou.utils.ApiServer;
import www.yigou.com.bayigou.utils.RetroLoginFactory;

/**
 * Created by xue on 2017-11-17.
 * 网络请求
 */

public class HttpSortModel implements InterSortModel{

public static final String TAG="HttpSortModel";
    Catagory catagory;

    ProductCatagory productCatagory;
    //商品分类接口
    private OnCatagoryFinish onCatagoryFinish;

    //定义商品分类接口
    public interface OnCatagoryFinish {
        void onCataFinish(Catagory catagory);
    }

    //商品分类接口
    private OnProductCatagoryFinish onProductCatagoryFinish;

    //定义商品子分类接口
    public interface OnProductCatagoryFinish {
        void onProCaFinish(ProductCatagory productCatagory);
    }
    //接口的构造
    public HttpSortModel(OnCatagoryFinish onCatagoryFinish, OnProductCatagoryFinish onProductCatagoryFinish) {
        this.onCatagoryFinish = onCatagoryFinish;
        this.onProductCatagoryFinish = onProductCatagoryFinish;
    }

    //分类
    @Override
    public void getCatagory() {

        Observable<Catagory> catagory = RetroLoginFactory.getInstance().getCatagory();
            catagory.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Catagory>() {
                        @Override
                        public void onCompleted() {
                            Log.d(TAG, "getCatagory===========onCompleted: ");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(TAG, "getCatagory========onCompleted: ");

                        }

                        @Override
                        public void onNext(Catagory catagory) {
                            Log.d(TAG, "getCatagory========onNext: ========="+catagory.getCode());

                            onCatagoryFinish.onCataFinish(catagory);
                        }
                    });
    }

    @Override
    public void getProductCatagory(String cid) {
        HashMap<String,String> map = new HashMap<>();
        map.put("cid",cid);
        Log.d(TAG, "getProductCatagory: ============"+cid);
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Api.LOGIN_REG)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .build();
//        //动态代理得到网络接口
//        ApiServer apiService = retrofit.create(ApiServer.class);
//        Observable<ProductCatagory> productCatagory = apiService.getProductCatagory(map);
//            productCatagory.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<ProductCatagory>() {
//                    @Override
//                    public void onCompleted() {
//                        Log.d(TAG, "getProductCatagory===========onCompleted: ");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d(TAG, "getProductCatagory===========onError: ");
//                    }
//
//                    @Override
//                    public void onNext(ProductCatagory productCatagory) {
//                        Log.d(TAG, "getProductCatagory========onNext: "+productCatagory.getData().get(0).getName());
//                        onProductCatagoryFinish.onProCaFinish(productCatagory);
//                    }
//                });

        Observable<ProductCatagory> productCatagory = RetroLoginFactory.getInstance().getProductCatagory(map);
             productCatagory.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ProductCatagory>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "getProductCatagory===========onCompleted: ");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "getProductCatagory===========onError: ");

                    }

                    @Override
                    public void onNext(ProductCatagory productCatagory) {
                        Log.d(TAG, "getProductCatagory========onNext: "+productCatagory.getData().get(0).getName());
                        onProductCatagoryFinish.onProCaFinish(productCatagory);
                    }
                });
    }
//子分类

}
