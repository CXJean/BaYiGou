package www.yigou.com.bayigou.mine.presenter;

import android.util.Log;

import www.yigou.com.bayigou.mine.bean.UserBean;
import www.yigou.com.bayigou.mine.model.MineLoginModel;
import www.yigou.com.bayigou.mine.model.MineRegModel;
import www.yigou.com.bayigou.mine.view.MineILoginView;
import www.yigou.com.bayigou.mine.view.MineIRegView;


/**
 * Created by xue on 2017-11-13.
 */

public class MineRegPresenter implements MineRegModel.OnRegFinishLisenter{

    public static final String TAG="MineRegPresenter";


    private final MineIRegView mineIRegView;
    private final MineRegModel mineRegModel ;

    public MineRegPresenter(MineIRegView mineIRegView) {
        this.mineIRegView = mineIRegView;
        this.mineRegModel = new MineRegModel();
        mineRegModel.setOnRegFinishLisenter(this);
    }

    public void setRegInfo(String mobile, String password) {
        Log.d(TAG, "setRegInfo: "+mobile+password);
        //将数据传给model层
        mineRegModel.HttpReg(mobile,password);
    }
    //注册
    @Override
    public void onRegFinish(UserBean userBean) {
        Log.d(TAG, "onLoginFinish: =========="+userBean.getCode());
        String code =userBean.getCode();
        if (code.equals("0")){
            //成功
            mineIRegView.onRegSuccess(code);
        }else if(code.equals("1")){
            //失败
            mineIRegView.onRegFailed(code);
        }
    }
}
