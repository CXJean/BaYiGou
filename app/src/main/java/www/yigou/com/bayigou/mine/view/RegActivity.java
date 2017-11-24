package www.yigou.com.bayigou.mine.view;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import www.yigou.com.bayigou.R;
import www.yigou.com.bayigou.mine.presenter.MineRegPresenter;

public class RegActivity extends AppCompatActivity implements MineIRegView{

    @BindView(R.id.reg_back)
    TextView regBack;
    @BindView(R.id.reg_nickname)
    EditText regNickname;
    @BindView(R.id.reg_pass)
    EditText regPass;
    @BindView(R.id.reg_rePass)
    EditText regRePass;
    @BindView(R.id.reg_email)
    EditText regEmail;
    @BindView(R.id.zhuce_button)
    Button zhuceButton;

    public static final String TAG="RegActivity";

    private String regNicknameStr;
    private String regPassStr;
    private String regRePassStr;
    private String regEmailStr;
    private String code;
    private MineRegPresenter mineRegPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        ButterKnife.bind(this);//绑定ButterKnife


        //实例化MinePresenter
        mineRegPresenter = new MineRegPresenter(this);
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

    @OnClick({R.id.reg_back, R.id.zhuce_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.reg_back:
                Toasty.error(this, "返回键", Toast.LENGTH_LONG).show();
                finish();
                break;
            case R.id.zhuce_button:

                regNicknameStr = regNickname.getText().toString().trim();
                regRePassStr = regRePass.getText().toString().trim();
                regPassStr = regPass.getText().toString().trim();
                regEmailStr = regEmail.getText().toString().trim();
                Log.d(TAG, "onViewClicked: ========="+regNicknameStr+regRePassStr);
                /**/
//                Matcher MNickname = pPheoneNum.matcher(NicknameStr);
//                Matcher MregPass = ppass.matcher(regPassStr);
//                Matcher Memail = pEmail.matcher(regEmailStr);
                /*为空*/
                if ((regNicknameStr.equals("") || regNicknameStr == null) && (regRePassStr.equals("") || regRePassStr == null) &&
                        (regPassStr.equals("") || regPassStr == null) && (regEmailStr.equals("") || regEmailStr == null)&&regPassStr.equals(regRePassStr)) {

                    Toasty.error(this, "注册信息不能为空!"+regNicknameStr+regRePassStr, Toast.LENGTH_LONG).show();
                }else{
                    Log.d(TAG, "mineRegPresenter====="+regNicknameStr+regRePassStr);
                    mineRegPresenter.setRegInfo(regNicknameStr, regRePassStr);
                }
                break;
        }
    }

    @Override
    public void onRegSuccess(String code) {
        if (code.equals("0")){
            Toasty.success(RegActivity.this, "注册成功!", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onRegSuccess: ==========="+code);
            finish();
        }
    }
    @Override
    public void onRegFailed(String error) {
        if (error.equals("1")) {
            Toasty.error(RegActivity.this, "注册失败!", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onLoginFailed: ==========="+error);
        }
    }
}
