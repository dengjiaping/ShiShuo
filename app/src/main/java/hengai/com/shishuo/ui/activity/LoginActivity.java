package hengai.com.shishuo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;

import java.util.ArrayList;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import hengai.com.shishuo.R;
import hengai.com.shishuo.utils.LogUtils;
import hengai.com.shishuo.utils.T;

/**
 * Created by yu on 2017/8/4.
 */

public class LoginActivity extends AppCompatActivity {
    @InjectView(R.id.tv_login_phone)
    TextView mTvLoginPhone;
    @InjectView(R.id.et_login_phone)
    EditText mEtLoginPhone;
    @InjectView(R.id.btn_login)
    Button mBtnLogin;
    @InjectView(R.id.imgbtn_qq)
    ImageButton mImgbtnQq;
    @InjectView(R.id.imgbtn_wechat)
    ImageButton mImgbtnWechat;
    @InjectView(R.id.tv_login_server)
    TextView mTvLoginServer;
    public  static LoginActivity instance=null;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        instance=this;
        LogUtils.d("+++" + px2sp(this, 38));

    }

    /**
     * px转换成sp
     */
    private int px2sp(Context context, float pxValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    @OnClick({R.id.btn_login, R.id.imgbtn_qq, R.id.imgbtn_wechat, R.id.tv_login_server})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                startActivity(new Intent(LoginActivity.this,Login2Activity.class));
                break;
            case R.id.imgbtn_qq:

                thirdPartyLogin(SHARE_MEDIA.QQ);
                break;
            case R.id.imgbtn_wechat:
                thirdPartyLogin(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.tv_login_server:
                break;
        }
    }
    private void thirdPartyLogin(SHARE_MEDIA me){
       /* UMShareAPI.get(this).deleteOauth(this, me, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA media) {

            }

            @Override
            public void onComplete(SHARE_MEDIA media, int i, Map<String, String> map) {

            }

            @Override
            public void onError(SHARE_MEDIA media, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(SHARE_MEDIA media, int i) {

            }
        });*/

        UMShareAPI.get(this).getPlatformInfo(this, me, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA media) {

            }

            @Override
            public void onComplete(SHARE_MEDIA media, int i, Map<String, String> map) {
                Toast.makeText(LoginActivity.this, "成功了", Toast.LENGTH_LONG).show();
                LogUtils.d("++++"+map.toString());
            }

            @Override
            public void onError(SHARE_MEDIA media, int i, Throwable throwable) {
                Toast.makeText(LoginActivity.this, "失败", Toast.LENGTH_LONG).show();
                LogUtils.d("++++"+throwable.getMessage());
            }

            @Override
            public void onCancel(SHARE_MEDIA media, int i) {
                Toast.makeText(LoginActivity.this, "取消", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode,resultCode,data);
    }
}
