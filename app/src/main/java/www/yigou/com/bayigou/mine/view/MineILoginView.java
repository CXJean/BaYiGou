package www.yigou.com.bayigou.mine.view;

import www.yigou.com.bayigou.mine.bean.UserBean;

/**
 * Created by xue on 2017-11-13.
 */

public interface MineILoginView {

    /**
     * 代码提供规范
     * 登录成功
     */
    void onLoginSuccess(UserBean userBean);

    /**
     * 登录失败
     *
     * @param error
     */
    void onLoginFailed(String error);

}
