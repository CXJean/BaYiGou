package www.yigou.com.bayigou.mine.view;

import android.content.Intent;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import www.yigou.com.bayigou.R;
import www.yigou.com.bayigou.mine.bean.User;
import www.yigou.com.bayigou.mine.bean.UserBean;
import www.yigou.com.bayigou.mine.presenter.MineLoginPresenter;
import www.yigou.com.bayigou.utils.SharedPreferencesUtils;
import www.yigou.com.bayigou.utils.SharedPrefsUtil;
import www.yigou.com.bayigou.utils.SpUtil;

public class LoginActivity extends AppCompatActivity implements MineILoginView{

    @BindView(R.id.login_back)
    TextView loginBack;
    @BindView(R.id.login_userImg)
    ImageView loginUserImg;
    @BindView(R.id.login_zhuce)
    TextView loginZhuce;
    @BindView(R.id.login_button)
    Button loginButton;
    @BindView(R.id.login_name)
    EditText loginName;
    @BindView(R.id.login_pass)
    EditText loginPass;
    @BindView(R.id.activity_login)
    RelativeLayout activityLogin;
    private String nameStr;
    private String passStr;
    public static final String TAG="LoginActivity";

    private MineLoginPresenter minePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        //实例化MinePresenter
        minePresenter = new MineLoginPresenter(this);
    }
    //沉浸式
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @OnClick({R.id.login_back, R.id.login_userImg, R.id.login_zhuce, R.id.login_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_back://返回
                finish();
                break;
            case R.id.login_userImg:

                break;
            case R.id.login_zhuce://注册
                Intent intent = new Intent(LoginActivity.this, RegActivity.class);
                startActivity(intent);
                break;
            case R.id.login_button://登陆
                nameStr = loginName.getText().toString().trim();
                passStr = loginPass.getText().toString().trim();
                if ((nameStr.equals("") || nameStr == null) && (passStr.equals("") || passStr == null)) {
                    Toasty.error(LoginActivity.this, "手机号，密码不能为空!", Toast.LENGTH_SHORT).show();
                }else {
                    minePresenter.setLoginInfo(nameStr, passStr);
                }
                break;
        }
    }

    @Override
    public void onLoginSuccess(UserBean userBean) {//成功

        Log.d(TAG, "onLoginSuccess: "+userBean.toString());
        String code = userBean.getCode();
        if (code.equals("0")){
            //存入数据
            String uid = userBean.getData().getUid();
            SpUtil.putString(this,"uid",uid+"");
            Log.d(TAG, "刚登陆完: =========="+SharedPrefsUtil.getValue(this,"uid",""));
            //普通事件发送消息给putong1用post方法
            Toasty.success(LoginActivity.this, "登陆成功!", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onLoginSuccess: ==========="+code);

            EventBus.getDefault().post(new User(nameStr,passStr,userBean.getData().getUid()+""));

            finish();
        }
    }

    @Override
    public void onLoginFailed(String error) {//失败
        if (error.equals("1")) {
            Toasty.error(LoginActivity.this, "登陆失败!", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onLoginFailed: ==========="+error);
        }
    }

}
