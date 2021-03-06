package www.yigou.com.bayigou.home.view;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dou361.ijkplayer.widget.PlayStateParams;
import com.dou361.ijkplayer.widget.PlayerView;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import www.yigou.com.bayigou.R;
import www.yigou.com.bayigou.home.bean.AddCart;
import www.yigou.com.bayigou.home.bean.GoodsInfoBean;
import www.yigou.com.bayigou.home.bean.Pid;
import www.yigou.com.bayigou.utils.RetroLoginFactory;

public class GoodsInfoActivity extends AppCompatActivity {


    public static final String TAG = "GoodsInfoActivity";
    @BindView(R.id.sellerType)
    ImageView sellerType;
    @BindView(R.id.goodsTitle)
    TextView goodsTitle;
    @BindView(R.id.goodsSubhead)
    TextView goodsSubhead;
    @BindView(R.id.goodsPricce)
    TextView goodsPricce;
    @BindView(R.id.salenum)
    TextView salenum;
    @BindView(R.id.productNums)
    TextView productNums;
    @BindView(R.id.sellerImg)
    ImageView sellerImg;
    @BindView(R.id.sellerName)
    TextView sellerName;
    @BindView(R.id.dateInfo)
    TextView dateInfo;
    @BindView(R.id.buyIt)
    Button buyIt;


    private String mpid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_info);
        ButterKnife.bind(this);

        //注册事件
        EventBus.getDefault().register(GoodsInfoActivity.this);

        initData();
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

    @Subscribe(threadMode = ThreadMode.POSTING, sticky = true)
    public void getPid(Pid pid) {
        mpid = pid.getPid();
        Log.d(TAG, "onCreate: pid值===========" + mpid);
    }

    //展示数据
    private void initData() {
        String source = "android";
        Observable<GoodsInfoBean> goodsInfo = RetroLoginFactory.getInstance().getGoodsInfo(mpid, source);

        goodsInfo.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GoodsInfoBean>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: ");

                    }

                    @Override
                    public void onNext(GoodsInfoBean goodsInfoBean) {
                        GoodsInfoBean.SellerBean seller = goodsInfoBean.getSeller();
                        GoodsInfoBean.DataBean goodsData = goodsInfoBean.getData();

                        Log.d(TAG, "onNext:商品详情------- " + goodsInfoBean.getCode() + "========" + goodsInfoBean.getSeller().getName());
                        //分割字符串
                        String[] images = goodsData.getImages().split("\\|");
                        Log.d(TAG, "onNext: ======img====" + images[0].toString() + "====" + images[1] + "========" + images[2] + "========");
//                        ImageLoader.getInstance().displayImage(images[0], goodsImg);
                        //视屏
                        String url = "http://mp4.vjshi.com/2013-05-28/2013052815051372.mp4";
                        new PlayerView(GoodsInfoActivity.this)
                                .setTitle("商品视屏")
                                .setScaleType(PlayStateParams.fitparent)
                                .hideMenu(true)
                                .forbidTouch(false)
                                .setPlaySource(url)
                                .startPlay();

                        ImageLoader.getInstance().displayImage(seller.getIcon(), sellerType);

                        goodsTitle.setText(goodsData.getTitle().toString());
                        goodsSubhead.setText(goodsData.getSubhead());

                        goodsPricce.setText("售价:￥" + goodsData.getPrice() + "");

                        salenum.setText("已售: " + goodsData.getSalenum());
                        productNums.setText("库存: " + seller.getProductNums());

                        ImageLoader.getInstance().displayImage(seller.getIcon(), sellerImg);
                        sellerName.setText(seller.getName());
                        dateInfo.setText("  时间: " + goodsData.getCreatetime());


                    }
                });
    }

    @OnClick({R.id.sellerName, R.id.buyIt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sellerName:
                Toasty.success(this, "商家详情", Toast.LENGTH_SHORT).show();
                break;
            case R.id.buyIt:
                getAddGoods("904", mpid);
                Toasty.success(this, "加入购物车成功" + mpid, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消注册
        EventBus.getDefault().unregister(this);
    }

    public void getAddGoods(String uid, String pid) {

        Observable<AddCart> addCart = RetroLoginFactory.getInstance().getAddCart(uid, pid);

        addCart.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AddCart>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: ");
                    }

                    @Override
                    public void onNext(AddCart addCart) {
                        Log.d(TAG, "onNext: " + addCart.getCode() + "============" + addCart.getMsg());
                    }
                });
    }

}
