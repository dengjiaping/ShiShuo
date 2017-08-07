package hengai.com.shishuo.app;

import android.app.Application;

import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

/**
 * Created by yu on 2017/8/4.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        UMShareAPI.get(this);

    }
    {
        PlatformConfig.setWeixin("wx5231d4d655cbf5c2", "875777926699ad7f9a0ad7675dfe2011");
        PlatformConfig.setQQZone("1105807406", "IIwxLqdIAdZGG6R3");
    }
}
