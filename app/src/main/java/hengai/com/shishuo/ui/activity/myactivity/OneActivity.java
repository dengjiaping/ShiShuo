package hengai.com.shishuo.ui.activity.myactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.baidu.paysdk.login.Login;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import cn.beecloud.BeeCloud;
import hengai.com.shishuo.R;
import hengai.com.shishuo.app.MyApplication;
import hengai.com.shishuo.ui.activity.LoginActivity;
import hengai.com.shishuo.ui.activity.MainActivity;
import hengai.com.shishuo.utils.DataSet;
import hengai.com.shishuo.utils.SPUtils;

/**
 * Created by yu on 2017/10/27.
 */

public class OneActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        UMShareAPI.get(this);
        //支付
        BeeCloud.setAppIdAndSecret("04250155-4651-42d1-917d-2f793f720806",
                "811671b6-34d6-4db5-b020-484dcc8bf844");


        //初始化数据库和下载数据
        DataSet.init(this);
        String isLogin= (String) SPUtils.get(getApplicationContext(),"islogin","N");
        String isFrist= (String) SPUtils.get(getApplicationContext(),"isfrist","Y");
        if(isFrist.equals("Y")){
            SPUtils.put(getApplicationContext(),"isfrist","N");
            startActivity(new Intent(OneActivity.this, SplashActivity.class));
            finish();
        }else{
            if(isLogin.equals("N")){
                startActivity(new Intent(OneActivity.this, LoginActivity.class));
                finish();
            }else{
                startActivity(new Intent(OneActivity.this, MainActivity.class));
                finish();
            }
        }


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
