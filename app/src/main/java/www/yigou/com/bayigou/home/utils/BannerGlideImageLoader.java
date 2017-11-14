package www.yigou.com.bayigou.home.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by xue on 2017-11-10.
 */

public class BannerGlideImageLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //初始化
        Glide.with(context).load((String)path).into(imageView);
    }
}