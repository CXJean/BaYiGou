package www.yigou.com.bayigou.mine.presenter;

import android.util.Log;

import www.yigou.com.bayigou.mine.bean.UserBean;
import www.yigou.com.bayigou.mine.model.MineLoginModel;
import www.yigou.com.bayigou.mine.model.MineRegImodel;
import www.yigou.com.bayigou.mine.model.MineRegModel;
import www.yigou.com.bayigou.mine.view.MineILoginView;
import www.yigou.com.bayigou.mine.view.MineIRegView;
import www.yigou.com.bayigou.mine.view.RegActivity;


/**
 * Created by xue on 2017-11-13.
 */

public class MineLoginPresenter implements MineLoginModel.OnFinishLisenter{

    public static final String TAG="MinePresenter";

    private final MineILoginView mineILoginView;
    private final MineLoginModel mineLoginModel;


    public MineLoginPresenter(MineILoginView mineILoginView) {
        this.mineILoginView = mineILoginView;
        this.mineLoginModel = new MineLoginModel();
        mineLoginModel.setOnFinishLisenter(this);
    }

    public void setLoginInfo(String mobile, String password) {
        //将数据传给model层
        mineLoginModel.HttpLogin(mobile,password);
    }

    @Override
    public void onLoginFinish(UserBean userBean) {
        Log.d(TAG, "onLoginFinish: =========="+userBean.getCode());
        String code =userBean.getCode();
        if (code.equals("0")){
            //成功
            mineILoginView.onLoginSuccess(userBean);
        }else if(code.equals("1")){
            //失败
            mineILoginView.onLoginFailed(code);
        }

    }
}
