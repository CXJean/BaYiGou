package www.yigou.com.bayigou.utils;



import java.util.HashMap;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;
import www.yigou.com.bayigou.cart.bean.AddDeleteBean;
import www.yigou.com.bayigou.cart.bean.CartsBean;
import www.yigou.com.bayigou.home.bean.AddCart;
import www.yigou.com.bayigou.home.bean.GoodsInfoBean;
import www.yigou.com.bayigou.home.bean.HomeBean;
import www.yigou.com.bayigou.mine.bean.UserBean;
import www.yigou.com.bayigou.sort.bean.Catagory;
import www.yigou.com.bayigou.sort.bean.ProductCatagory;

/**
 * Created by fan on 2017/11/8.
 */

public interface ApiServer {


//    @GET("v1/restserver/ting?method=baidu.ting.billboard.billList&type=1&size=10&offset=0")
//    Observable<HomeBean> getHome();
//    首页
    @GET("yunifang/mobile/home")
    Observable<HomeBean> getHome();
    //登陆
    @FormUrlEncoded
    @POST("user/login")
    Observable<UserBean> getLogin(@Field("mobile") String mobile, @Field("password") String password);
    //注册
    @FormUrlEncoded
     @POST("user/reg")
    Observable<UserBean>   getReg(@Field("mobile") String mobile, @Field("password") String password);
    //商品详情
    @FormUrlEncoded
    @POST("product/getProductDetail")
    Observable<GoodsInfoBean> getGoodsInfo(@Field("pid") String pid,@Field("source") String source);

    //商品分类接口,无参数
    @GET("product/getCatagory")
    Observable<Catagory> getCatagory();

    //商品子分类接口
    @FormUrlEncoded//读参数进行urlEncoded
    @POST("/product/getProductCatagory")
    Observable<ProductCatagory> getProductCatagory(@FieldMap HashMap<String,String> map);

    //添加购物车
    @FormUrlEncoded//读参数进行urlEncoded
    @POST("/product/addCart")
    Observable<AddCart> getAddCart(@Field("uid") String uid, @Field("pid") String pid);

    //查询购物车
    @FormUrlEncoded//读参数进行urlEncoded
    @POST("/product/getCarts")
    Observable<CartsBean> getSelectCart(@Field("uid") String uid,@Field("source") String source);

//    http://120.27.23.105/product/deleteCart?uid=72&pid=1
    //删除购物车
    @FormUrlEncoded//读参数进行urlEncoded
    @POST("/product/deleteCart")
    Observable<AddDeleteBean> deleteCart(@Field("uid") String uid, @Field("pid") String pid);

}
