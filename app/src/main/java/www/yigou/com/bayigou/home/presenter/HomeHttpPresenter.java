package www.yigou.com.bayigou.home.presenter;

import www.yigou.com.bayigou.home.bean.HomeBean;
import www.yigou.com.bayigou.home.model.HomeHttpModel;
import www.yigou.com.bayigou.home.model.OnDataFinish;
import www.yigou.com.bayigou.home.view.HomeView;

/**
 * Created by xue on 2017-11-09.
 */

public class HomeHttpPresenter implements OnDataFinish {

    private final HomeView homeView;
    private final HomeHttpModel homeHttpModel;

    public HomeHttpPresenter(HomeView homeView) {
        this.homeView = homeView;
        this.homeHttpModel = new HomeHttpModel();
        homeHttpModel.setOnDataFinish(this);
    }
    //给homeHttpModel    url
    public void setHomeData(String url){
        homeHttpModel.getHttpHomeData(url);
    }

    @Override
    public void OnFinishListener(HomeBean homeBean) {
        homeView.getHttpHomeData(homeBean);
    }
}
