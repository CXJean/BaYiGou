package www.yigou.com.bayigou.cart.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import www.yigou.com.bayigou.R;
import www.yigou.com.bayigou.cart.CartAdapter.ShopcartAdapter;
import www.yigou.com.bayigou.cart.bean.AddDeleteBean;
import www.yigou.com.bayigou.cart.bean.CartsBean;
import www.yigou.com.bayigou.cart.bean.GoodsInfo;
import www.yigou.com.bayigou.cart.bean.StoreInfo;
import www.yigou.com.bayigou.cart.model.OnGetCartsFinishListener;
import www.yigou.com.bayigou.home.bean.AddCart;
import www.yigou.com.bayigou.home.model.OnDataFinish;
import www.yigou.com.bayigou.mine.bean.User;
import www.yigou.com.bayigou.utils.RetroLoginFactory;
import www.yigou.com.bayigou.utils.SpUtil;

/**
 * Created by xue on 2017-11-09.
 */

public class Cart extends Fragment  implements ShopcartAdapter.CheckInterface,
        ShopcartAdapter.ModifyCountInterface, ShopcartAdapter.GroupEdtorListener{

    public static final String TAG="Cart";

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.subtitle)
    TextView subtitle;
    @BindView(R.id.top_bar)
    LinearLayout topBar;
    @BindView(R.id.exListView)
    ExpandableListView exListView;
    @BindView(R.id.all_chekbox)
    CheckBox allChekbox;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R.id.tv_go_to_pay)
    TextView tvGoToPay;
    @BindView(R.id.ll_info)
    LinearLayout llInfo;
    @BindView(R.id.tv_share)
    TextView tvShare;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.tv_delete)
    TextView tvDelete;
    @BindView(R.id.ll_shar)
    LinearLayout llShar;
    Unbinder unbinder;

    private CartsBean cartsBean;

    private Cart context;
    private double totalPrice = 0.00;// 购买的商品总价
    private int totalCount = 0;// 购买的商品总数量
    private ShopcartAdapter selva;
    private List<StoreInfo> groups = new ArrayList<StoreInfo>();// 组元素数据列表
    private Map<String, List<GoodsInfo>> children = new HashMap<String, List<GoodsInfo>>();// 子元素数据列表
    private int flag = 0;
    String uidStr = "";
    private String uid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_cart, container, false);

        unbinder = ButterKnife.bind(this, view);

        //注册事件
        EventBus.getDefault().register(this);
//        initDatas();
//        initEvents();
        return view;

    }


    //处理得到的值
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(User user) {

//        Log.d("uid", "======事件内=======" + SpUtil.getString(getActivity(),"uid",""));
        uidStr=user.getUid();
        Log.d(TAG, "onMoonEvent:=======传过来的值"+user.getUid().toString());
    }


    private void initEvents() {
        Log.d(TAG, "initEvents: ========商铺数"+groups.size()+"============"+children.size());
        selva = new ShopcartAdapter(groups, children, getActivity());
        selva.setCheckInterface(this);// 关键步骤1,设置复选框接口
        selva.setModifyCountInterface(this);// 关键步骤2,设置数量增减接口
        selva.setmListener(this);
        exListView.setAdapter(selva);
        //默认展开列表
        for (int i = 0; i < selva.getGroupCount(); i++) {
            exListView.expandGroup(i);// 关键步骤3,初始化时，将ExpandableListView以展开的方式呈现
        }
    }
    @Override
    public void onResume() {
        super.onResume();

        uid = SpUtil.getString(getActivity(), "uid", "").toString().trim();

        Log.d(TAG, "onResume: ======== "+uid.toString());
        if (SpUtil.getString(getActivity(), "uid","") instanceof String&&null!= uid){
            Log.d(TAG, "onCreateView请求数据: ------------"+ uid +"----"+ uid.length());
//            String id = (String)uid;
            getSelectCarts("1000");
        }
        setCartNum();
    }

    /**
     * 设置购物车产品数量
     */
    private void setCartNum() {
        int count = 0;
        for (int i = 0; i < groups.size(); i++) {
            groups.get(i).setChoosed(allChekbox.isChecked());
            StoreInfo group = groups.get(i);
            List<GoodsInfo> childs = children.get(group.getId());
            for (GoodsInfo goodsInfo : childs) {
                count += 1;
        }
        }
        title.setText("购物车" + "(" + count + ")");
    }
    /**
     * 模拟数据<br>
     * 遵循适配器的数据列表填充原则，组元素被放在一个List中，对应的组元素下辖的子元素被放在Map中，<br>
     * 其键是组元素的Id(通常是一个唯一指定组元素身份的值)
     */
    private void initDatas(CartsBean cartsBean) {
      /*  for (int i = 0; i < 3; i++) {
            groups.add(new StoreInfo(i + "", "天猫店铺" + (i + 1) + "号店"));
            List<GoodsInfo> products = new ArrayList<GoodsInfo>();
            for (int j = 0; j <= i; j++) {
                int[] img = {R.drawable.goods1, R.drawable.goods2, R.drawable.goods3, R.drawable.goods4, R.drawable.goods5, R.drawable.goods6};
                products.add(new GoodsInfo(j + "", "商品", groups.get(i).getName() + "的第" + (j + 1) + "个商品",
                 12.00 + new Random().nextInt(23),
                  new Random().nextInt(5) + 1,
                   "豪华",
                    "1",
                   img[i * j],
                    6.00 + new Random().nextInt(13)));
            }
            children.put(groups.get(i).getId(), products);// 将组元素的一个唯一值，这里取Id，作为子元素List的Key
        }*/
        Log.d(TAG, "initDatas: "+cartsBean.getData().size()+"============"+cartsBean.toString());
         for (int i = 0; i < cartsBean.getData().size(); i++) {
                groups.add(new StoreInfo(cartsBean.getData().get(i).getSellerid()+"", cartsBean.getData().get(i).getSellerName()));
             Log.d(TAG, "initDatas: "+groups.size()+"=================");
                List<GoodsInfo> products = new ArrayList<GoodsInfo>();
             //此处的数量应该是小于,等于时，数组越界
                for (int j = 0; j < cartsBean.getData().get(i).getList().size(); j++) {
                    String images = cartsBean.getData().get(i).getList().get(j).getImages();
                    String[] img = images.split("[|]");
                    products.add(new GoodsInfo(j + "",
                            "商品",
                            cartsBean.getData().get(i).getList().get(j).getTitle(),
                            cartsBean.getData().get(i).getList().get(j).getPrice() ,
                            cartsBean.getData().get(i).getList().get(j).getNum(),
                            "自然色",
                            "1",
                            img[i * j],
                            cartsBean.getData().get(i).getList().get(j).getBargainPrice()));

                }
             children.put(groups.get(i).getId(), products);// 将组元素的一个唯一值，这里取Id，作为子元素List的Key
         }
        initEvents();
    }

    /**
     * 删除操作<br>
     * 1.不要边遍历边删除，容易出现数组越界的情况<br>
     * 2.现将要删除的对象放进相应的列表容器中，待遍历完后，以removeAll的方式进行删除
     */
    protected void doDelete() {
        List<StoreInfo> toBeDeleteGroups = new ArrayList<StoreInfo>();// 待删除的组元素列表
        for (int i = 0; i < groups.size(); i++) {
            StoreInfo group = groups.get(i);
            if (group.isChoosed()) {
                toBeDeleteGroups.add(group);
            }
            List<GoodsInfo> toBeDeleteProducts = new ArrayList<GoodsInfo>();// 待删除的子元素列表
            List<GoodsInfo> childs = children.get(group.getId());
            for (int j = 0; j < childs.size(); j++) {
                if (childs.get(j).isChoosed()) {
                    toBeDeleteProducts.add(childs.get(j));
                }
            }
            childs.removeAll(toBeDeleteProducts);
            doDeleteCarts(uid+"","1");
        }
        groups.removeAll(toBeDeleteGroups);
        selva.notifyDataSetChanged();
        calculate();
    }


    //获取删除以后的结果的数据
    public void getDeleteData(AddDeleteBean addDeleteBean){
        Log.d(TAG, "getCartData:数据 "+addDeleteBean.getMsg()+"=========="+addDeleteBean.getCode());
        if (addDeleteBean.getCode().equals("")){

        }
    }

    //点击事件
    @OnClick({R.id.back, R.id.subtitle,R.id.all_chekbox, R.id.tv_go_to_pay, R.id.tv_share, R.id.tv_save, R.id.tv_delete})
    public void onViewClicked(View view) {
        AlertDialog alert;
        switch (view.getId()) {
            case R.id.back:
                break;
            case R.id.subtitle:
                if (flag == 0) {
                    llInfo.setVisibility(View.GONE);
                    tvGoToPay.setVisibility(View.GONE);
                    llShar.setVisibility(View.VISIBLE);
                    subtitle.setText("完成");
                } else if (flag == 1) {
                    llInfo.setVisibility(View.VISIBLE);
                    tvGoToPay.setVisibility(View.VISIBLE);
                    llShar.setVisibility(View.GONE);
                    subtitle.setText("编辑");
                }
                flag = (flag + 1) % 2;//其余得到循环执行上面2个不同的功能

                break;

            case R.id.all_chekbox:
                doCheckAll();
                break;

            case R.id.tv_go_to_pay:
                if (totalCount == 0) {
                    Toast.makeText(getContext(), "请选择要支付的商品", Toast.LENGTH_LONG).show();
                    return;
                }
                alert = new AlertDialog.Builder(getContext()).create();
                alert.setTitle("操作提示");
                alert.setMessage("总计:\n" + totalCount + "种商品\n" + totalPrice + "元");
                alert.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        });
                alert.setButton(DialogInterface.BUTTON_POSITIVE, "确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        });
                alert.show();
                break;
            case R.id.tv_share:
                if (totalCount == 0) {
                    Toast.makeText(getContext(), "请选择要分享的商品", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(getActivity(), "分享成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_save:
                if (totalCount == 0) {
                    Toast.makeText(getContext(), "请选择要保存的商品", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(getActivity(), "保存成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_delete:
                if (totalCount == 0) {
                    Toast.makeText(getContext(), "请选择要移除的商品", Toast.LENGTH_LONG).show();
                    return;
                }
                alert = new AlertDialog.Builder(getContext()).create();
                alert.setTitle("操作提示");
                alert.setMessage("您确定要将这些商品从购物车中移除吗？");
                alert.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        });
                alert.setButton(DialogInterface.BUTTON_POSITIVE, "确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                doDelete();
                            }
                        });
                alert.show();
                break;
        }
    }

    @Override
    public void checkGroup(int groupPosition, boolean isChecked) {
        StoreInfo group = groups.get(groupPosition);
        List<GoodsInfo> childs = children.get(group.getId());
        for (int i = 0; i < childs.size(); i++) {
            childs.get(i).setChoosed(isChecked);
        }
        if (isAllCheck())
            allChekbox.setChecked(true);
        else
            allChekbox.setChecked(false);
        selva.notifyDataSetChanged();
        calculate();
    }

    @Override
    public void checkChild(int groupPosition, int childPosition, boolean isChecked) {
        boolean allChildSameState = true;// 判断改组下面的所有子元素是否是同一种状态
        StoreInfo group = groups.get(groupPosition);
        List<GoodsInfo> childs = children.get(group.getId());
        for (int i = 0; i < childs.size(); i++) {
            // 不全选中
            if (childs.get(i).isChoosed() != isChecked) {
                allChildSameState = false;
                break;
            }
        }
        //获取店铺选中商品的总金额
        if (allChildSameState) {
            group.setChoosed(isChecked);// 如果所有子元素状态相同，那么对应的组元素被设为这种统一状态
        } else {
            group.setChoosed(false);// 否则，组元素一律设置为未选中状态
        }

        if (isAllCheck()) {
            allChekbox.setChecked(true);// 全选
        } else {
            allChekbox.setChecked(false);// 反选
        }
        selva.notifyDataSetChanged();
        calculate();

    }
    //判断是否全选
    private boolean isAllCheck() {

        for (StoreInfo group : groups) {
            if (!group.isChoosed())
                return false;
        }
        return true;
    }
    /**
     * 全选与反选
     */
    private void doCheckAll() {
        for (int i = 0; i < groups.size(); i++) {
            groups.get(i).setChoosed(allChekbox.isChecked());
            StoreInfo group = groups.get(i);
            List<GoodsInfo> childs = children.get(group.getId());
            for (int j = 0; j < childs.size(); j++) {
                childs.get(j).setChoosed(allChekbox.isChecked());
            }
        }
        selva.notifyDataSetChanged();
        calculate();
    }
    /**
     * 统计操作<br>
     * 1.先清空全局计数器<br>
     * 2.遍历所有子元素，只要是被选中状态的，就进行相关的计算操作<br>
     * 3.给底部的textView进行数据填充
     */
    private void calculate() {
        totalCount = 0;
        totalPrice = 0.00;
        for (int i = 0; i < groups.size(); i++) {
            StoreInfo group = groups.get(i);
            List<GoodsInfo> childs = children.get(group.getId());
            for (int j = 0; j < childs.size(); j++) {
                GoodsInfo product = childs.get(j);
                if (product.isChoosed()) {
                    totalCount++;
                    totalPrice += product.getPrice() * product.getCount();
                }
            }


        }
        tvTotalPrice.setText("￥" + totalPrice);
        tvGoToPay.setText("去支付(" + totalCount + ")");
    }

    @Override
    public void groupEdit(int groupPosition) {
        groups.get(groupPosition).setIsEdtor(true);
        //刷新适配器
        selva.notifyDataSetChanged();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //删除购物车后动态改变数量
            setCartNum();
        }
    };

    @Override
    public void doIncrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {
        GoodsInfo product = (GoodsInfo) selva.getChild(groupPosition,
                childPosition);
        int currentCount = product.getCount();
        currentCount++;
        product.setCount(currentCount);
        ((TextView) showCountView).setText(currentCount + "");
        selva.notifyDataSetChanged();
        calculate();
    }

    @Override
    public void doDecrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {
        GoodsInfo product = (GoodsInfo) selva.getChild(groupPosition,
                childPosition);
        int currentCount = product.getCount();
        if (currentCount == 1)
            return;
        currentCount--;
        product.setCount(currentCount);
        ((TextView) showCountView).setText(currentCount + "");
        selva.notifyDataSetChanged();
        calculate();
    }

    @Override
    public void childDelete(int groupPosition, int childPosition) {
        children.get(groups.get(groupPosition).getId()).remove(childPosition);
        StoreInfo group = groups.get(groupPosition);
        List<GoodsInfo> childs = children.get(group.getId());
        if (childs.size() == 0) {
            groups.remove(groupPosition);
        }
        selva.notifyDataSetChanged();
        handler.sendEmptyMessage(0);
    }


    //重要
    // 判断是否展示—与RadioGroup等连用，进行点击切换
    @Override
    public void onHiddenChanged(boolean hidden) {
        int k=-1;
//        super.onHiddenChanged(hidden);
        if (k==0){
            if (!hidden){//显示
                onPause();//获取焦点
                k=0;
            }
        }

    }
    //删除购物车
    public void doDeleteCarts(String uid,String pid){

        Log.d(TAG, "网络请求访问购物车==============="+uid+"======="+pid);
        Observable<AddDeleteBean> deletecarts = RetroLoginFactory.getInstance().deleteCart(uid, pid);
            deletecarts.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AddDeleteBean>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: ");
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(AddDeleteBean addDeleteBean) {
                        Log.d(TAG, "查询购物车: "+addDeleteBean.getCode()+"=========="+addDeleteBean.getMsg());
                        if (null!=addDeleteBean){
                            getDeleteData(addDeleteBean);
                        }else{
                            Log.d(TAG, "onNext:购物车-------------- 删除购物车失败");
                        }

                    }
                });
    }
    //请求购物车
    public void getSelectCarts(String uid){

        Log.d(TAG, "网络请求访问购物车==============="+uid);
        Observable<CartsBean> Carts = RetroLoginFactory.getInstance().getSelectCart(uid,"android");
            Carts.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CartsBean>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: ");
                       e.printStackTrace();
                    }

                    @Override
                    public void onNext(CartsBean cartsBean) {
                        Log.d(TAG, "查询购物车: "+cartsBean.getCode()+"=========="+cartsBean.getMsg()+"=========="+cartsBean.getData().get(0).getList().get(0).getTitle());
                        if (null!=cartsBean){
                            initDatas(cartsBean);
                        }else{
                            Log.d(TAG, "onNext:购物车-------------- 请求数据失败");
                        }

                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //取消注册事件
        EventBus.getDefault().unregister(getActivity());
    }

    //解绑
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
