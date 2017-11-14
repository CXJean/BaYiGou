package www.yigou.com.bayigou.cart.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import www.yigou.com.bayigou.R;

/**
 * Created by xue on 2017-11-09.
 */

public class Cart extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_cart,  container,false);


        return view;

    }
}
