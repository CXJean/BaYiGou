package www.yigou.com.bayigou;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
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
import www.yigou.com.bayigou.mine.view.Mine;
import www.yigou.com.bayigou.sort.view.Sort;

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
                fm.beginTransaction().hide(home).hide(sort).hide(mine).show(cart).commit();
                break;
            case R.id.rbtn_mine:
                fm.beginTransaction().hide(home).hide(sort).hide(cart).show(mine).commit();
                break;
        }
    }
}