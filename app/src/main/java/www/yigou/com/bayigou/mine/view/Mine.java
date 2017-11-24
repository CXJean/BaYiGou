package www.yigou.com.bayigou.mine.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import www.yigou.com.bayigou.R;
import www.yigou.com.bayigou.mine.bean.User;
import www.yigou.com.bayigou.utils.SharedPreferencesUtils;
import www.yigou.com.bayigou.utils.SpUtil;

/**
 * Created by xue on 2017-11-09.
 */

public class Mine extends Fragment {

    @BindView(R.id.userImg)
    ImageView userImg;
    Unbinder unbinder;
    @BindView(R.id.userNumPhone)
    TextView userNumPhone;
    @BindView(R.id.goods)
    TextView goods;
    @BindView(R.id.shop)
    TextView shop;
    @BindView(R.id.footprint)
    TextView footprint;
    @BindView(R.id.btn_outLogin)
    Button btnOutLogin;
    private String userUid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_mine, container, false);
        //注册ButterKnife
        unbinder = ButterKnife.bind(this, view);

        //注册事件
        EventBus.getDefault().register(this);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //处理得到的值
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(User user) {

        Log.d("uid", "======事件内=======" + SpUtil.getString(getActivity(),"uid",""));

        userNumPhone.setText(user.getUsername());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //取消注册事件
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.userImg, R.id.btn_outLogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.userImg:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_outLogin:
                //退出登陆
                showCheckDialog("您将退出登陆!");
                break;
        }
    }
    /**
     2      *  提示对话框
     3      * @param message
     4 */
    public void showCheckDialog(String message){
         new AlertDialog.Builder(getActivity())
         .setTitle("温馨提示")
         .setMessage(message)
         .setPositiveButton("确定", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which) {
                    //清空
                    SpUtil.deleAll(getActivity());
                    Log.d("uid", "========清空==========:----- " + SpUtil.getString(getActivity(),"uid",""));
                    userNumPhone.setText("用户名");
//                    System.exit(0);//退出
                }
         }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
         })
         .create().show();
    }

}
