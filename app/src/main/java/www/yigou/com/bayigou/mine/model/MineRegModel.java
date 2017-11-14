package www.yigou.com.bayigou.mine.model;

import android.util.Log;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import www.yigou.com.bayigou.mine.bean.UserBean;
import www.yigou.com.bayigou.utils.RetroLoginFactory;

/**
 * Created by xue on 2017-11-13.
 * MineModel
 */

public class MineRegModel implements MineRegImodel{

    public static final String TAG="MineRegModel";

    // 定义接口变量
    private OnRegFinishLisenter onRegFinishLisenter;

    //定义接口
    public interface OnRegFinishLisenter{
        void onRegFinish(UserBean userBean);
    }
    public void setOnRegFinishLisenter(OnRegFinishLisenter onRegFinishLisenter){
        this.onRegFinishLisenter = onRegFinishLisenter;
    }

    @Override
    public void HttpReg(String mobile, String password) {
        Log.d(TAG, "HttpReg: ======"+mobile+"======="+password);

        Observable<UserBean> reg = RetroLoginFactory.getInstance().getReg(mobile,password);

            reg.subscribeOn(Schedulers.io())//订阅
                .observeOn(AndroidSchedulers.mainThread())//切换到主线程
                .subscribe(new Observer<UserBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(UserBean userBean) {
                        Log.d(TAG, "onNext: "+userBean.getCode());
                        if (null!=onRegFinishLisenter){
                            onRegFinishLisenter.onRegFinish(userBean);
                        }
                    }
                });
    }
}
