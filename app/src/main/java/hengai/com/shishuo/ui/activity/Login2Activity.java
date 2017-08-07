package hengai.com.shishuo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import hengai.com.shishuo.R;

/**
 * Created by yu on 2017/8/4.
 */

public class Login2Activity extends AppCompatActivity {
    @InjectView(R.id.tv_login2_phone)
    TextView mTvLogin2Phone;
    @InjectView(R.id.et_login_phone)
    EditText mEtLoginPhone;
    @InjectView(R.id.btn_login2_send)
    Button mBtnLogin2Send;
    @InjectView(R.id.btn_login2)
    Button mBtnLogin2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.btn_login2_send, R.id.btn_login2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login2_send:
                break;
            case R.id.btn_login2:
                LoginActivity.instance.finish();
                startActivity(new Intent(Login2Activity.this,MyDreamActivity.class));
            finish();
                break;
        }
    }
}
