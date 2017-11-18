package www.yigou.com.bayigou.mine.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import es.dmoral.toasty.Toasty;
import www.yigou.com.bayigou.R;
import www.yigou.com.bayigou.mine.bean.User;

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

    @OnClick(R.id.userImg)
    public void onViewClicked() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);

        startActivity(intent);
    }

    //处理得到的值
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(User user) {
        userNumPhone.setText(user.getUsername());
        //uid
        Toasty.success(getActivity(),user.getUid(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //取消注册事件
        EventBus.getDefault().unregister(this);
    }
}
