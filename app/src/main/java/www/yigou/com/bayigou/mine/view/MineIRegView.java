package www.yigou.com.bayigou.mine.view;

/**
 * Created by xue on 2017-11-13.
 */

public interface MineIRegView {
    /**
     * 代码提供规范
     * 登录成功
     */
    void onRegSuccess(String code);

    /**
     * 登录失败
     *
     * @param error
     */
    void onRegFailed(String error);

}
