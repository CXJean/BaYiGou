package www.yigou.com.bayigou.home.model;

import android.util.Log;

import com.orhanobut.logger.Logger;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import www.yigou.com.bayigou.home.bean.HomeBean;
import www.yigou.com.bayigou.utils.RetroFactory;

/**
 * Created by xue on 2017-11-09.
 * home页的数据请求类
 */

public class HomeHttpModel implements HomeModel{

    private static final String TAG = "homeModel";

    private OnDataFinish onDataFinish;

    //
    public void  setOnDataFinish(OnDataFinish onDataFinish) {
        this.onDataFinish = onDataFinish;
    }

    @Override
    public void getHttpHomeData(String url) {
        final Observable<HomeBean> home = RetroFactory.getInstance().getHome();
            home.subscribeOn(Schedulers.io())//订阅
                .observeOn(AndroidSchedulers.mainThread())//切换到主线程
                .subscribe(new Observer<HomeBean>() {//订阅观察者
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted:--------- ");
                    }

                    //异常
                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError:--------- ");
                    }

                    @Override
                    public void onNext(HomeBean homeBean) {

                        Logger.d(homeBean.getCode()+homeBean.getMsg());
                        //通过接口回调将请求到的值传递出去
                        onDataFinish.OnFinishListener(homeBean);
                    }
                });
    }
}
