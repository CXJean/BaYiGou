package www.yigou.com.bayigou.utils;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by xue on 2017-11-09.
 */

public class MyApp extends Application{

    public static MyApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance=this;

        Fresco.initialize(this);//初始化fresco
        /*初始化imageloader*/
        ImageLoaderConfiguration aDefault = ImageLoaderConfiguration.createDefault(getApplicationContext());
        ImageLoader.getInstance().init(aDefault);
    }

}
