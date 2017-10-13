package hengai.com.shishuo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.sdsmdg.tastytoast.TastyToast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import hengai.com.shishuo.R;

/**
 * Created by yu on 2017/10/13.
 */

public class ActionCommitActivity extends AppCompatActivity {
    @InjectView(R.id.tv_title)
    TextView mTvTitle;
    @InjectView(R.id.et_name)
    EditText mEtName;
    @InjectView(R.id.et_phone)
    EditText mEtPhone;
    @InjectView(R.id.et_wx)
    EditText mEtWx;
    @InjectView(R.id.img_btn_consulting)
    ImageButton mImgBtnConsulting;
    @InjectView(R.id.btn_commit)
    Button mBtnCommit;

    Context mCtx;
    private String mName;
    private String mPhone;
    private String mWx;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_commit);
        ButterKnife.inject(this);
        mCtx=this;
        initData();
    }

    private void initData() {
        mName = mEtName.getText().toString().trim();
        mPhone = mEtPhone.getText().toString().trim();
        mWx = mEtWx.getText().toString().trim();
    }

    @OnClick({R.id.img_btn_consulting, R.id.btn_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_btn_consulting:
                Intent intent = new Intent(mCtx, AllWebActivity.class);
                intent.putExtra("webUrl", "http://html.ecqun.com/kf/sdk/openwin.html?corpid=4752991&cstype=rand&mode=0&cskey=qsocaZNfbAGeeXfU7z");
                startActivity(intent);
                break;
            case R.id.btn_commit:
                if(!mName.isEmpty()&&!mPhone.isEmpty()&&!mWx.isEmpty()){
                    TastyToast.makeText(mCtx,"提交成功",TastyToast.LENGTH_SHORT,TastyToast.ERROR);
                }else{
                    TastyToast.makeText(mCtx,"请先正确填写信息",TastyToast.LENGTH_SHORT,TastyToast.ERROR);
                }
                break;
        }
    }
}
