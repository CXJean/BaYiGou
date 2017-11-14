package www.yigou.com.bayigou.mine.view;

import android.content.Intent;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import www.yigou.com.bayigou.R;
import www.yigou.com.bayigou.mine.presenter.MineLoginPresenter;

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
    public void onLoginSuccess(String code) {//成功
        if (code.equals("0")){
            Toasty.success(LoginActivity.this, "登陆成功!", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onLoginSuccess: ==========="+code);
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