package www.yigou.com.bayigou.sort.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import www.yigou.com.bayigou.R;
import www.yigou.com.bayigou.sort.adapter.CatagoryAdapter;
import www.yigou.com.bayigou.sort.adapter.ProductCatagoryAdapter;
import www.yigou.com.bayigou.sort.adapter.ProductsAdapter;
import www.yigou.com.bayigou.sort.bean.Catagory;
import www.yigou.com.bayigou.sort.bean.ProductCatagory;
import www.yigou.com.bayigou.sort.model.HttpSortModel;
import www.yigou.com.bayigou.sort.presenter.SortPresenter;

/**
 * Created by xue on 2017-11-09.
 */

public class Sort extends Fragment implements InterSortView {

    public static final String TAG="SortFrag";
    @BindView(R.id.sort_left_listview)
    ListView catagoryListview;
    @BindView(R.id.sort_right_listview)
    ListView productListview;
    Unbinder unbinder;
    private SortPresenter sortPresenter;
    private CatagoryAdapter catagoryAdapter;
    private ProductsAdapter productsAdapter;
    private ProductCatagoryAdapter productCatagoryAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_sort, container, false);
        unbinder = ButterKnife.bind(this, view);//绑定ButterKnife

        initPresenter();
        return view;

    }

    private void initPresenter() {
        sortPresenter = new SortPresenter(this);
        sortPresenter.getCaData();//请求左边的数据
    }

    //分类
    @Override
    public void ShowCaData(Catagory catagory) {
        Log.d(TAG, "ShowCaData:---- 分类请求的数据---------- "+catagory.getData().get(0).getName());

        catagoryAdapter = new CatagoryAdapter(getActivity(), catagory);
        catagoryListview.setAdapter(catagoryAdapter);

        catagoryListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sortPresenter.getProCaData(position+"");
            }
        });
        //分类列表点击
        cataClick();
    }
    //子分类
    @Override
    public void ShowProCaData(ProductCatagory productCatagory) {

        productCatagoryAdapter = new ProductCatagoryAdapter(getActivity(),productCatagory);
        productListview.setAdapter(productCatagoryAdapter);
        Log.d(TAG, "------子分类------- "+productCatagory.getData().get(0).getName());

//        productCatagoryAdapter.GetItemData(new ProductCatagoryAdapter.GetItemData() {
//            @Override
//            public void getItemData(String name, String pscid) {
//                Log.d(TAG, "getItemProData: ==========="+name+"=============="+pscid);
//            }
//        });
    }
    //分类列表点击请求子类数据
    private void cataClick() {
        catagoryAdapter.setClickCaName(new CatagoryAdapter.ClickCaName() {
            @Override
            public void ClickCaname(String cid) {
                Log.d(TAG, "ClickCaname: --------------------"+cid);
                sortPresenter.getProCaData(cid);
            }
        });
    }

    //解绑
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
