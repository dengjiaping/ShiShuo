package hengai.com.shishuo.app;

import android.app.Application;
import android.content.Context;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.impl.RefreshHeaderWrapper;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import cn.beecloud.BeeCloud;
import hengai.com.shishuo.R;
import hengai.com.shishuo.utils.DataSet;
import hengai.com.shishuo.utils.DownloadController;

/**
 * Created by yu on 2017/8/4.
 */

public class MyApplication extends Application {
    private static MyApplication sInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        sInstance=this;
        UMShareAPI.get(this);
      //支付
        BeeCloud.setAppIdAndSecret("04250155-4651-42d1-917d-2f793f720806",
                "811671b6-34d6-4db5-b020-484dcc8bf844");


        //初始化数据库和下载数据
        DataSet.init(this);




        //DownloadController.init();
// 如果需要开启测试模式
// BeeCloud.setSandbox(true);
// BeeCloud.setAppIdAndSecret("appId", "testSecret");
    }

    {
        PlatformConfig.setWeixin("wx5231d4d655cbf5c2","875777926699ad7f9a0ad7675dfe2011");
        PlatformConfig.setQQZone("1105807406", "IIwxLqdIAdZGG6R3");
        PlatformConfig.setSinaWeibo("2862859337", "06f988828740fee943633953dcf73ba3","http://liangshiba.com");
    }
    static {//static 代码段可以防止内存泄露
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new BezierRadarHeader(context);//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                //return new ClassicsFooter(context).setSpinnerStyle(SpinnerStyle.Translate);
               return new BallPulseFooter(context).setSpinnerStyle(SpinnerStyle.Translate);
            }
        });
    }

}
