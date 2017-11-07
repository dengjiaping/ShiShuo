package hengai.com.shishuo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sdsmdg.tastytoast.TastyToast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import hengai.com.shishuo.R;
import hengai.com.shishuo.bean.LoginBean;
import hengai.com.shishuo.bean.RegisterBean;
import hengai.com.shishuo.network.HiRetorfit;
import hengai.com.shishuo.utils.SPUtils;
import hengai.com.shishuo.utils.T;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 注册页面
 * Created by yu on 2017/8/4.
 */

public class Login2Activity extends AppCompatActivity {
    @InjectView(R.id.tv_login2_phone)
    TextView mTvLogin2Phone;
    @InjectView(R.id.et_login_phone)
    EditText mEtLoginPhone;

    @InjectView(R.id.btn_login2)
    Button mBtnLogin2;
    @InjectView(R.id.et_login_password)
    EditText mEtLoginPassword;
    private String mPhone;
    private String mChannel = "liangshishuo";
    private Context mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        ButterKnife.inject(this);
        mContext=this;
        init();
    }

    private void init() {
        mPhone = getIntent().getStringExtra("phone");
        mTvLogin2Phone.setText(mPhone);
        mBtnLogin2.setEnabled(false);
        mEtLoginPassword.addTextChangedListener(mWatcher);
    }

    @OnClick({ R.id.btn_login2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login2:
               register();
                break;
        }
    }

    /**
     * 获取验证码
     * @param channel
     * @param mobile
     */
   /* private void code(String channel, String mobile) {
        Call<RegisterBean> call = HiRetorfit.getInstans().getApi().Code(channel, mobile);
        call.enqueue(new Callback<RegisterBean>() {
            @Override
            public void onResponse(Call<RegisterBean> call, Response<RegisterBean> response) {
                if (response != null) {
                    if(response.body().getResult()==1){

                        TastyToast.makeText(getApplicationContext(),response.body().getMessage(),TastyToast.LENGTH_SHORT,TastyToast.INFO);
                    }else{
                        TastyToast.makeText(getApplicationContext(),response.body().getMessage(),TastyToast.LENGTH_SHORT,TastyToast.INFO);
                    }

                } else {
                    T.showShort(Login2Activity.this, "网络错误，请检查您的网络");
                }
            }

            @Override
            public void onFailure(Call<RegisterBean> call, Throwable t) {
                T.showShort(Login2Activity.this, "网络错误，请检查您的网络");
            }
        });
    }
*/
    /**
     * 注册操作
     */
    private void register() {

        if(!mEtLoginPhone.getText().toString().isEmpty()){
            String code=mEtLoginPhone.getText().toString();
            final String pwd=mEtLoginPassword.getText().toString();
            Call<RegisterBean> call=HiRetorfit.getInstans().getApi().Register(mChannel,mPhone,code,pwd,pwd);
            call.enqueue(new Callback<RegisterBean>() {
                @Override
                public void onResponse(Call<RegisterBean> call, Response<RegisterBean> response) {
                    if(response!=null){
                        if(response.body().getResult()==1){
                            TastyToast.makeText(getApplicationContext(),"注册成功",TastyToast.LENGTH_SHORT,TastyToast.SUCCESS);

                            //注册成功后登录
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    login(mChannel,mPhone,pwd);
                                }
                            });
                        }else {
                            TastyToast.makeText(getApplicationContext(),response.body().getMessage(),TastyToast.LENGTH_SHORT,TastyToast.ERROR);
                        }

                    }else{
                        //T.showShort(Login2Activity.this,"网络错误");
                        TastyToast.makeText(getApplicationContext(),"网络错误",TastyToast.LENGTH_SHORT,TastyToast.ERROR);
                    }
                }

                @Override
                public void onFailure(Call<RegisterBean> call, Throwable t) {
                            TastyToast.makeText(Login2Activity.this,"网络错误",TastyToast.LENGTH_SHORT,TastyToast.ERROR);
                }
            });
        }else{

            TastyToast.makeText(Login2Activity.this,"请输入验证码",TastyToast.LENGTH_SHORT,TastyToast.ERROR);
        }
    }


    /**
     * 登录操作
     * @param channel
     * @param phone
     * @param pwd
     */
    private void login(final String channel, final String phone, final String pwd){
        Call<LoginBean> call= HiRetorfit.getInstans().getApi().Login(channel,phone,pwd);
        call.enqueue(new Callback<LoginBean>() {
            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                if(response!=null){
                    LoginBean loginBean=response.body();
                    if(loginBean.getResult()==1){
                        SPUtils.put(getApplicationContext(),"channel",channel);
                        SPUtils.put(getApplicationContext(),"phone",phone);
                        SPUtils.put(getApplicationContext(),"pwd",pwd);
                        SPUtils.put(getApplicationContext(),"name",phone);
                        //SPUtils.put(getApplicationContext(),"uid",loginBean.getUsertoken().getUid());
                        SPUtils.put(getApplicationContext(),"token",loginBean.getUsertoken().getToken());
                        LoginActivity.instance.finish();
                        SPUtils.put(getApplicationContext(),"islogin","Y");
                        startActivity(new Intent(mContext, MyDreamActivity.class));
                        TastyToast.makeText(getApplicationContext(),"登录成功",TastyToast.LENGTH_SHORT,TastyToast.SUCCESS);
                        finish();
                    }
                    if(loginBean.getResult()==-1){
                        TastyToast.makeText(getApplicationContext(),"登录失败",TastyToast.LENGTH_SHORT,TastyToast.ERROR);
                    }

                }
            }

            @Override
            public void onFailure(Call<LoginBean> call, Throwable t) {

            }
        });
    }

    TextWatcher mWatcher = new TextWatcher() {
        private CharSequence temp;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            temp = s;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (temp.length() >= 6) {
                mBtnLogin2.setEnabled(true);
            }
            if (temp.length() < 6) {
                mBtnLogin2.setEnabled(false);
            }

        }
    };
}
