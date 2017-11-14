package www.yigou.com.bayigou.mine.model;

import www.yigou.com.bayigou.mine.bean.User;
import www.yigou.com.bayigou.mine.bean.UserBean;

/**
 * Created by xue on 2017-11-13.
 */

public interface MineILoginmodel {

    //联网登陆
    void HttpLogin(String mobile, String password);

}
