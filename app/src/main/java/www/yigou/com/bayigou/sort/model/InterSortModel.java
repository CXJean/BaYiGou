package www.yigou.com.bayigou.sort.model;

/**
 * Created by xue on 2017-11-17.
 */

public interface InterSortModel {

    // 请求分类的接口
    void getCatagory();

    // 请求子分类的接口
    void getProductCatagory(String cid);
}
