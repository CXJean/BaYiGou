package www.yigou.com.bayigou.sort.presenter;

import android.util.Log;

import www.yigou.com.bayigou.sort.bean.Catagory;
import www.yigou.com.bayigou.sort.bean.ProductCatagory;
import www.yigou.com.bayigou.sort.model.HttpSortModel;
import www.yigou.com.bayigou.sort.model.InterSortModel;
import www.yigou.com.bayigou.sort.view.InterSortView;

/**
 * Created by xue on 2017-11-17.
 */

public class SortPresenter implements HttpSortModel.OnCatagoryFinish,HttpSortModel.OnProductCatagoryFinish{

    public static final String TAG="SortPresenter";
    private final InterSortView interSortView;
    private final HttpSortModel httpSortModel;

    public SortPresenter(InterSortView interSortView) {
        this.interSortView = interSortView;
        httpSortModel = new HttpSortModel(this,this);
    }

    public void getCaData(){
        httpSortModel.getCatagory();
    }

    public void getProCaData(String cid){
        Log.d(TAG, "getProCaData: ==========="+cid);
        httpSortModel.getProductCatagory(cid);
    }

    //调取view接口
    @Override
    public void onCataFinish(Catagory catagory) {
        Log.d(TAG, "分类: ==========="+catagory.getData().get(0).getName());

        interSortView.ShowCaData(catagory);
    }

    @Override
    public void onProCaFinish(ProductCatagory productCatagory) {
        Log.d(TAG, "子分类: ==========="+productCatagory.getData().get(0).getName());

        interSortView.ShowProCaData(productCatagory);
    }
}
