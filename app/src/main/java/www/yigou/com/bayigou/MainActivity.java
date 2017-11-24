package www.yigou.com.bayigou;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import www.yigou.com.bayigou.cart.view.Cart;
import www.yigou.com.bayigou.home.view.Home;
import www.yigou.com.bayigou.mine.bean.User;
import www.yigou.com.bayigou.mine.view.LoginActivity;
import www.yigou.com.bayigou.mine.view.Mine;
import www.yigou.com.bayigou.sort.view.Sort;
import www.yigou.com.bayigou.utils.SharedPreferencesUtils;
import www.yigou.com.bayigou.utils.SharedPrefsUtil;
import www.yigou.com.bayigou.utils.SpUtil;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "main";
    @BindView(R.id.rbtn_home)
    RadioButton rbtnHome;
    @BindView(R.id.rbtn_sort)
    RadioButton rbtnSort;
    @BindView(R.id.rbtn_cart)
    RadioButton rbtnCart;
    @BindView(R.id.rbtn_mine)
    RadioButton rbtnMine;

    private FragmentManager fm;
    private FragmentTransaction fTransaction;
    private Home home;
    private Sort sort;
    private Cart cart;
    private Mine mine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化ButterKnife
        ButterKnife.bind(this);

        /*初始化主界面*/
        init();

    }

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

    private void init() {
        //得到事务管理器
        fm = getSupportFragmentManager();
        //开启事物
        fTransaction = fm.beginTransaction();

        home = new Home();
        sort = new Sort();
        cart = new Cart();
        mine = new Mine();

        fTransaction.add(R.id.frags,home).show(home)
                .add(R.id.frags,sort).hide(sort)
                .add(R.id.frags,cart).hide(cart)
                .add(R.id.frags,mine).hide(mine);

        //提交
        fTransaction.commit();
        //设置第一页默认选中
        rbtnHome.setChecked(true);

    }
    //点击切换
    @OnClick({R.id.rbtn_home, R.id.rbtn_sort, R.id.rbtn_cart, R.id.rbtn_mine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rbtn_home:
                fm.beginTransaction().hide(sort).hide(cart).hide(mine).show(home).commit();
                break;
            case R.id.rbtn_sort:
                fm.beginTransaction().hide(home).hide(cart).hide(mine).show(sort).commit();
                break;
            case R.id.rbtn_cart:
                //        //获取登陆状态
                String uid = SpUtil.getString(this, "uid", "");
//                String uid = SharedPrefsUtil.getValue(this, "uid", "");
                Log.d(TAG, "==========fragment页:============= "+uid);
                if (uid.equals("")||uid==null){
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }else{
                    fm.beginTransaction().hide(home).hide(sort).hide(mine).show(cart).commit();
                }
                break;
            case R.id.rbtn_mine:
                fm.beginTransaction().hide(home).hide(sort).hide(cart).show(mine).commit();
                break;
        }
    }

    private long exitTime = 0;
    //退出程序
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
    //计算时间差
    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

}