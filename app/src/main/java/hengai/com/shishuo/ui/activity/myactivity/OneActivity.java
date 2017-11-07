package hengai.com.shishuo.ui.activity.myactivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

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
import com.sdsmdg.tastytoast.TastyToast;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;


import java.util.logging.LogRecord;

import cn.beecloud.BeeCloud;
import hengai.com.shishuo.R;
import hengai.com.shishuo.app.MyApplication;
import hengai.com.shishuo.ui.activity.LoginActivity;
import hengai.com.shishuo.ui.activity.MainActivity;
import hengai.com.shishuo.utils.DataSet;
import hengai.com.shishuo.utils.LogUtils;
import hengai.com.shishuo.utils.SPUtils;

/**
 * Created by yu on 2017/10/27.
 */

public class OneActivity extends AppCompatActivity {
    MyHandler myHandler;
    private String mIsLogin;
    private String mIsFrist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);

        onCallPermission();


        //wasteTime(); //执行耗时操作
    }

    public void onCallPermission() {
        LogUtils.d("+++++++++来授权");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//判断当前系统的SDK版本是否大于23
            //如果当前申请的权限没有授权
            if (!(checkSelfPermission(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED&& ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED)&&ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED&&ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                LogUtils.d("+++++++++没有授权");
                //第一次请求权限的时候返回false,第二次shouldShowRequestPermissionRationale返回true
                //如果用户选择了“不再提醒”永远返回false。
                if (shouldShowRequestPermissionRationale(android.Manifest.permission.RECORD_AUDIO)) {
                    //Toast.makeText(this, "Please grant the permission this time", Toast.LENGTH_LONG).show();
                }
                //请求权限
                requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA}, 1);
            } else {
                LogUtils.d("+++++++++已经授权");
                myHandler = new MyHandler();
                mIsLogin = (String) SPUtils.get(getApplicationContext(), "islogin", "N");
                mIsFrist = (String) SPUtils.get(getApplicationContext(), "isfrist", "Y");
                myHandler.sendEmptyMessageDelayed(101, 1500);
            }
        }else{
            myHandler = new MyHandler();
            mIsLogin = (String) SPUtils.get(getApplicationContext(), "islogin", "N");
            mIsFrist = (String) SPUtils.get(getApplicationContext(), "isfrist", "Y");
            myHandler.sendEmptyMessageDelayed(101, 1500);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (permissions[0].equals(Manifest.permission.RECORD_AUDIO) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                LogUtils.d("+++++++++授权完成");
                myHandler = new MyHandler();
                mIsLogin = (String) SPUtils.get(getApplicationContext(), "islogin", "N");
                mIsFrist = (String) SPUtils.get(getApplicationContext(), "isfrist", "Y");
                myHandler.sendEmptyMessageDelayed(101, 1500);
            } else {//没有获得到权限
                LogUtils.d("+++++++++权限开启");
                TastyToast.makeText(this, "请开启权限", TastyToast.LENGTH_SHORT, TastyToast.INFO);
                finish();
            }
        }
    }

    void wasteTime() {
        UMShareAPI.get(this);
        //支付
        BeeCloud.setAppIdAndSecret("04250155-4651-42d1-917d-2f793f720806",
                "811671b6-34d6-4db5-b020-484dcc8bf844");

        //初始化数据库和下载数据
        DataSet.init(this);


        myHandler.sendEmptyMessage(101);//通知Handler


    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 101) {
                if (mIsFrist.equals("Y")) {
                    //SPUtils.put(getApplicationContext(), "isfrist", "N");
                    startActivity(new Intent(OneActivity.this, SplashActivity.class));
                    finish();
                } else {
                    if (mIsLogin.equals("N")) {
                        startActivity(new Intent(OneActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        startActivity(new Intent(OneActivity.this, MainActivity.class));
                        finish();
                    }
                }
            }
        }
    }


    /*{
        PlatformConfig.setWeixin("wx5231d4d655cbf5c2", "875777926699ad7f9a0ad7675dfe2011");
        PlatformConfig.setQQZone("1105807406", "IIwxLqdIAdZGG6R3");
        PlatformConfig.setSinaWeibo("2862859337", "06f988828740fee943633953dcf73ba3", "http://liangshiba.com");
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
    }*/
}
