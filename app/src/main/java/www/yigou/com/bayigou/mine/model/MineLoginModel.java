package www.yigou.com.bayigou.mine.model;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

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

public class MineLoginModel implements MineILoginmodel{

   public static final String TAG="MineModel";

    // 定义接口变量
    private OnFinishLisenter onFinishLisenter;

    //定义接口
    public interface OnFinishLisenter{
        void onLoginFinish(UserBean userBean);
    }
    public void setOnFinishLisenter(OnFinishLisenter onFinishLisenter){
        this.onFinishLisenter = onFinishLisenter;
    }

    //登陆接口
    @Override
    public void HttpLogin( String mobile, String password) {
//        Map<String,String> map=new HashMap<>();
//        map.put("mobile",user.getUsername());
//        map.put("password",user.getPassword());
        Observable<UserBean> login = RetroLoginFactory.getInstance().getLogin(mobile,password);
            login.subscribeOn(Schedulers.io())//订阅
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
                        if (null!=onFinishLisenter){
                            onFinishLisenter.onLoginFinish(userBean);
                        }
                    }
                });
    }

}
