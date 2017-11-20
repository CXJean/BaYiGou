package www.yigou.com.bayigou.cart.model;

import www.yigou.com.bayigou.cart.bean.CartsBean;
import www.yigou.com.bayigou.home.bean.HomeBean;

/**
 * Created by xue on 2017-11-18.
 */

public interface OnGetCartsFinishListener {
    //完成数据请求的监听
    void OnCartsFinishListener(CartsBean cartsBean);
}
