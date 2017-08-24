package hengai.com.shishuo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import hengai.com.shishuo.R;
import hengai.com.shishuo.bean.LoginBean;
import hengai.com.shishuo.bean.MobileNumble;
import hengai.com.shishuo.network.HiRetorfit;
import hengai.com.shishuo.utils.LogUtils;
import hengai.com.shishuo.utils.SPUtils;
import hengai.com.shishuo.utils.T;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    public static LoginActivity instance = null;
    private MobileNumble mMobileNumble;
    private String mPhone;


    private String mUmsys;
    private String mUmuid;
    private String mUmname;
    private String mUmgender;
    private String mUmheadurl;
    private String mchcode;
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        instance = this;
        mContext = this;
        mBtnLogin.setEnabled(false);
        mEtLoginPhone.addTextChangedListener(mWatcher);
        String pwd = (String) SPUtils.get(LoginActivity.this, "phone", "1");
        if (!pwd.equals("1")) {
            mEtLoginPhone.setText(pwd);
            mBtnLogin.setEnabled(true);
        }
    }

    TextWatcher mWatcher = new TextWatcher() {
        private CharSequence temp;
        private int editStart;
        private int editEnd;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            temp = s;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            editStart = mEtLoginPhone.getSelectionStart();
            editEnd = mEtLoginPhone.getSelectionEnd();
            if (temp.length() == 11) {
                mBtnLogin.setEnabled(true);
            }
            if (temp.length() < 11) {
                mBtnLogin.setEnabled(false);
            }
            if (temp.length() > 11) {
                T.showShort(LoginActivity.this, "你输入的字数已经超过了限制！");
                s.delete(editStart - 1, editEnd);
                int tempSelection = editStart;
                mEtLoginPhone.setText(s);
                mEtLoginPhone.setSelection(tempSelection);
            }

        }
    };

    private void initphoneLogin() {
        mPhone = mEtLoginPhone.getText().toString().trim().replaceAll(" ", "");
        if (mPhone != null) {
            if (mPhone.length() == 11) {
                login(mPhone);
            } else {
                T.showShort(this, "手机号格式有误");
            }
        } else {
            T.showShort(this, "手机号码不能为空");
        }

    }

    @OnClick({R.id.btn_login, R.id.imgbtn_qq, R.id.imgbtn_wechat, R.id.tv_login_server})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                initphoneLogin();
                //startActivity(new Intent(LoginActivity.this,MyDreamActivity.class));
                //startActivity(new Intent(LoginActivity.this,Login2Activity.class));
                break;
            case R.id.imgbtn_qq:
                //startActivity(new Intent(LoginActivity.this,LoginPwdActivity.class));
                mUmsys = "QQ";
                thirdPartyLogin(SHARE_MEDIA.QQ);
               /* String s1= (String) SPUtils.get(LoginActivity.this,"settings","2");
                LogUtils.d(s1);*/
                break;
            case R.id.imgbtn_wechat:
                //startActivity(new Intent(LoginActivity.this,MainActivity.class));
                mUmsys = "WX";
                thirdPartyLogin(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.tv_login_server:
                startActivity(new Intent(LoginActivity.this, MyDreamActivity.class));
                break;
        }
    }

    /**
     * 验证手机号码是否已注册
     *
     * @param i
     */
    private void login(String i) {
        Call<MobileNumble> call = HiRetorfit.getInstans().getApi().ListPhone(i);
        call.enqueue(new Callback<MobileNumble>() {
            @Override
            public void onResponse(Call<MobileNumble> call, Response<MobileNumble> response) {
                if (response != null) {
                    mMobileNumble = response.body();
                    if (mMobileNumble.getResult() == -1) {
                        T.showShort(LoginActivity.this, mMobileNumble.getMessage());
                    }
                    if (mMobileNumble.getResult() == 0) {
                        Intent intent = new Intent(LoginActivity.this, Login2Activity.class);
                        intent.putExtra("phone", mPhone);
                        startActivity(intent);
                    }
                    if (mMobileNumble.getResult() == 1) {
                        Intent intent = new Intent(LoginActivity.this, LoginPwdActivity.class);
                        intent.putExtra("phone", mPhone);
                        startActivity(intent);
                    }
                    LogUtils.d(mMobileNumble.getMessage() + "++++" + mMobileNumble.getCode());
                } else {
                    T.showShort(LoginActivity.this, "网络错误");
                }

            }

            @Override
            public void onFailure(Call<MobileNumble> call, Throwable t) {

            }
        });
    }
    /*
    * {unionid=, is_yellow_vip=0, screen_name=ω雨下漫步,
    * msg=, vip=0, city=长沙, accessToken=875D68F492900C173B527CCA3A0A2ADE,
     * gender=男, province=湖南, is_yellow_year_vip=0,
     * openid=E1804613953F88B1AA3572E7BDE4830E, yellow_vip_level=0,
     * profile_image_url=http://q.qlogo.cn/qqapp/1105807406/E1804613953F88B1AA3572E7BDE4830E/100,
      * access_token=875D68F492900C173B527CCA3A0A2ADE,
       * iconurl=http://q.qlogo.cn/qqapp/1105807406/E1804613953F88B1AA3572E7BDE4830E/100,
       * name=ω雨下漫步, uid=E1804613953F88B1AA3572E7BDE4830E,
       * expiration=1510193243711, expires_in=1510193243711, ret=0, level=0}
    * */
/*
* {unionid=o_sbmvzMKS4bKF_Y06FtjiaRD4Lk, screen_name=风亦过～雨亦走,
 * city=长沙,
 *  gender=男, province=湖南, openid=oxCSrv1B0zr8wZeQ_T6WswXaQdiY,
 *  profile_image_url=http://wx.qlogo.cn/mmopen/HxxQHJzRstblWk4jWMg0ja8njyVec8kbK09MMWJFvSdjGz2N9W4KQg4cLQblpRibEkaHmbspXyice16rWVZkia0lib9VDoJu7Mkv/0,
 *  country=中国,  iconurl=http://wx.qlogo.cn/mmopen/HxxQHJzRstblWk4jWMg0ja8njyVec8kbK09MMWJFvSdjGz2N9W4KQg4cLQblpRibEkaHmbspXyice16rWVZkia0lib9VDoJu7Mkv/0,
 *  name=风亦过～雨亦走, uid=o_sbmvzMKS4bKF_Y06FtjiaRD4Lk,
 *  expiration=1502426918261, language=zh_CN, expires_in=1502426918261}
*
*
* */

    private void thirdPartyLogin(SHARE_MEDIA me) {
        UMShareAPI.get(this).getPlatformInfo(this, me, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA media) {

            }

            /*mUmsys;
            private String mUmuid;
            private String mUmname;
            private String mUmgender;
            private String mUmheadurl;
            private String mchcode;*/
            @Override
            public void onComplete(SHARE_MEDIA media, int i, Map<String, String> map) {
                Toast.makeText(LoginActivity.this, "授权成功", Toast.LENGTH_LONG).show();
                LogUtils.d("++++" + map.toString());

                mUmuid = map.get("uid");
                mUmname = map.get("name");
                mUmgender = map.get("gender");
                mUmheadurl = map.get("iconurl");
                TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                mchcode = tm.getDeviceId();
                LogUtils.d("++++这是之后的数据" + "mUmuid=" + mUmuid + "mUmname=" + mUmname + mUmgender + mUmheadurl + mUmsys + mchcode);
                quickLogin("liangshishuo", mUmsys, mUmuid, mUmname, mUmgender, mUmheadurl, mchcode);

            }

            @Override
            public void onError(SHARE_MEDIA media, int i, Throwable throwable) {
                Toast.makeText(LoginActivity.this, "授权失败", Toast.LENGTH_LONG).show();
                LogUtils.d("++++" + throwable.getMessage());
            }

            @Override
            public void onCancel(SHARE_MEDIA media, int i) {
                Toast.makeText(LoginActivity.this, "取消授权", Toast.LENGTH_LONG).show();
            }
        });

    }

    /**
     * 第三方登录
     *
     * @param channel
     * @param umsys
     * @param umuid
     * @param umname
     * @param umgender
     * @param umheadurl
     * @param mchcode
     */
    private void quickLogin(String channel, String umsys, String umuid, final String umname, final String umgender, final String umheadurl, String mchcode) {
        Call<LoginBean> call = HiRetorfit.getInstans().getApi().QuickLogin(channel, umsys, umuid, umname, umgender, umheadurl, mchcode);
        call.enqueue(new Callback<LoginBean>() {
            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                String s1 = (String) SPUtils.get(LoginActivity.this, "settings", "1");
                if (response != null) {
                    LoginBean loginBean = response.body();
                    SPUtils.put(mContext, "uid", loginBean.getUsertoken().getUid());
                    SPUtils.put(mContext, "token", loginBean.getUsertoken().getToken());
                    SPUtils.put(mContext, "umname", umname);
                    SPUtils.put(mContext, "umgender", umgender);
                    SPUtils.put(mContext, "umheadurl", umheadurl);
                    if (s1.equals("1")) {
                        startActivity(new Intent(mContext, MyDreamActivity.class));
                        finish();
                    } else {
                        startActivity(new Intent(mContext, MainActivity.class));
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginBean> call, Throwable t) {
                T.showShort(mContext, "登录失败");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
