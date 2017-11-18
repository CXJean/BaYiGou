package www.yigou.com.bayigou.sort.view;

import www.yigou.com.bayigou.sort.bean.Catagory;
import www.yigou.com.bayigou.sort.bean.ProductCatagory;

/**
 * Created by xue on 2017-11-17.
 */

public interface InterSortView {

    // 展示左侧数据
    void ShowCaData(Catagory catagory);

    // 展示右侧数据
    void ShowProCaData(ProductCatagory productCatagory);
}
