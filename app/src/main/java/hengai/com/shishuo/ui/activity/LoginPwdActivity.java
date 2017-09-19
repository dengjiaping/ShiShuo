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
 * Created by yu on 2017/8/7.
 */

public class LoginPwdActivity extends AppCompatActivity {
    @InjectView(R.id.tv_login_phone)
    TextView mTvLoginPhone;
    @InjectView(R.id.et_login_phone)
    EditText mEtLoginPhone;
    @InjectView(R.id.btn_login)
    Button mBtnLogin;
    @InjectView(R.id.tv_login_forgetpwd)
    TextView mTvLoginForgetpwd;
    private Context mContext;
    private String mPhone;
    private String mChannel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pwd);
        ButterKnife.inject(this);
        mContext=this;
        mBtnLogin.setEnabled(false);
        initData();
        initView();
    }

    private void initData() {
        mPhone = getIntent().getStringExtra("phone");
        mChannel = "liangshishuo";
    }
    private void initView() {
        mEtLoginPhone.addTextChangedListener(mWatcher);
        String pwd= (String) SPUtils.get(mContext,"pwd","1");
        if(!pwd.equals("1")){
            mEtLoginPhone.setText(pwd);
            mBtnLogin.setEnabled(true);
        }

    }
    TextWatcher mWatcher=new TextWatcher() {
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
                mBtnLogin.setEnabled(true);
            }
            if (temp.length() < 6) {
                mBtnLogin.setEnabled(false);
            }
        }
    };
    @OnClick({R.id.btn_login, R.id.tv_login_forgetpwd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                String pwd=mEtLoginPhone.getText().toString().trim().replace(" ","");
                if(!pwd.equals("")){
                    login(mChannel,mPhone,pwd);
                }else{
                    TastyToast.makeText(LoginPwdActivity.this,"密码不能为空",TastyToast.LENGTH_SHORT,TastyToast.ERROR);
                }

                break;
            case R.id.tv_login_forgetpwd:

                break;
        }
    }
    private void login(final String channel, final String phone, final String pwd){
        Call<LoginBean> call= HiRetorfit.getInstans().getApi().Login(channel,phone,pwd);
        call.enqueue(new Callback<LoginBean>() {
            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                if(response!=null){
                    LoginBean loginBean=response.body();
                    if(loginBean.getResult()==1){
                        SPUtils.put(mContext,"channel",channel);
                        SPUtils.put(mContext,"phone",phone);
                        SPUtils.put(mContext,"pwd",pwd);
                        SPUtils.put(mContext,"uid",loginBean.getUsertoken().getUid());
                        SPUtils.put(mContext,"token",loginBean.getUsertoken().getToken());
                        LoginActivity.instance.finish();
                        TastyToast.makeText(LoginPwdActivity.this,"登录成功",TastyToast.LENGTH_SHORT,TastyToast.SUCCESS);
                        startActivity(new Intent(LoginPwdActivity.this,MainActivity.class));
                        finish();
                    }
                    if(loginBean.getResult()==-1){
                        TastyToast.makeText(LoginPwdActivity.this,"用户名或者密码错误",TastyToast.LENGTH_SHORT,TastyToast.ERROR);
                    }

                }
            }

            @Override
            public void onFailure(Call<LoginBean> call, Throwable t) {
                TastyToast.makeText(LoginPwdActivity.this,"访问错误"+"--"+t,TastyToast.LENGTH_SHORT,TastyToast.ERROR);
            }
        });
    }
}
