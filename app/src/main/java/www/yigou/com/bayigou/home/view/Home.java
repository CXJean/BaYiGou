package www.yigou.com.bayigou.home.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;
import www.yigou.com.bayigou.R;
import www.yigou.com.bayigou.home.bean.HomeBean;
import www.yigou.com.bayigou.home.homeAdapter.HomeXrecyclerviewAdapter;
import www.yigou.com.bayigou.home.model.OnDataFinish;
import www.yigou.com.bayigou.home.presenter.HomeHttpPresenter;
import www.yigou.com.bayigou.utils.Api;
import www.yigou.com.bayigou.utils.DividerItemDecoration;

/**
 * Created by xue on 2017-11-09.
 */

public class Home extends Fragment implements HomeView{

    Unbinder unbinder;
    @BindView(R.id.normal_code)
    ImageView normalCode;
    @BindView(R.id.search)
    EditText search;
    @BindView(R.id.normal_message)
    ImageView normalMessage;
    @BindView(R.id.xRecyclerView)
    XRecyclerView xRecyclerView;


    public static final int REQUEST_CODE = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_home, container,false);
        //初始化butterknife
        unbinder = ButterKnife.bind(this, view);
        /*二维码初始化*/
        ZXingLibrary.initDisplayOpinion(getActivity());

        initView();

        return view;

    }

    private void initView() {

        HomeHttpPresenter homeHttpPresenter = new HomeHttpPresenter(this);
        homeHttpPresenter.setHomeData(Api.HOME_URL);

        //线性布局
        xRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        xRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST));

    }

    //点击事件
    @OnClick({R.id.normal_code, R.id.search, R.id.normal_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.normal_code:
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.search:
                Toasty.success(getActivity(), "查询页", Toast.LENGTH_LONG).show();
                break;
            case R.id.normal_message:
                Toasty.success(getActivity(), "更多页", Toast.LENGTH_LONG).show();
                break;
        }
    }
    /*二维码处理值*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(getActivity(), "解析结果:" + result, Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(getActivity(), "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        } else {
            Toasty.warning(getActivity(), "没扫上", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void getHttpHomeData(HomeBean homeBean) {

        Log.d("main",homeBean.getMsg()+homeBean.getCode()+"===========");
        HomeXrecyclerviewAdapter homeXrecyclerviewAdapter = new HomeXrecyclerviewAdapter(getActivity(), homeBean);
        xRecyclerView.setAdapter(homeXrecyclerviewAdapter);
    }
}
