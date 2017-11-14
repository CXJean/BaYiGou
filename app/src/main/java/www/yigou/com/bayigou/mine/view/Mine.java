package www.yigou.com.bayigou.mine.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import www.yigou.com.bayigou.R;

/**
 * Created by xue on 2017-11-09.
 */

public class Mine extends Fragment{

    @BindView(R.id.userImg)
    ImageView userImg;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_mine, container, false);
        //注册ButterKnife
        unbinder = ButterKnife.bind(this, view);

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
}
