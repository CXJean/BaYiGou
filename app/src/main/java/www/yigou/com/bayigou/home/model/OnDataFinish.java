package www.yigou.com.bayigou.home.model;

import www.yigou.com.bayigou.home.bean.HomeBean;

/**
 * Created by xue on 2017-11-09.
 * 将数据从httpmodel传到presenter的接口
 */

public interface OnDataFinish {
    //完成数据请求的监听
    void OnFinishListener(HomeBean homeBean);

}
