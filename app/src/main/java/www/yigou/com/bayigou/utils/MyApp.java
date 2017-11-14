package www.yigou.com.bayigou.utils;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by xue on 2017-11-09.
 */

public class MyApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        Fresco.initialize(this);//初始化fresco
    }
}
