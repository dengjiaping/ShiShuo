package hengai.com.shishuo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;

import com.sdsmdg.tastytoast.TastyToast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import hengai.com.shishuo.R;

/**
 * Created by yu on 2017/10/10.
 */

public class OneOnOneActivity extends AppCompatActivity {
    @InjectView(R.id.et_name)
    EditText mEtName;
    @InjectView(R.id.et_phone)
    EditText mEtPhone;
    @InjectView(R.id.et_wx)
    EditText mEtWx;
    @InjectView(R.id.face)
    RadioButton mRbFace;
    @InjectView(R.id.line)
    RadioButton mRbLine;
    @InjectView(R.id.rb_hnan)
    RadioButton mRbHnan;
    @InjectView(R.id.rb_gdong)
    RadioButton mRbGdong;
    @InjectView(R.id.rb_gzhou)
    RadioButton mRbGzhou;
    @InjectView(R.id.img_btn_consulting)
    ImageButton mImgBtnConsulting;
    @InjectView(R.id.btn_commit)
    Button mBtnCommit;
    Context mCtx;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_on);
        ButterKnife.inject(this);
        mCtx=this;
    }

    @OnClick({R.id.face, R.id.line, R.id.rb_hnan, R.id.rb_gdong, R.id.rb_gzhou, R.id.img_btn_consulting, R.id.btn_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.face:

                break;
            case R.id.line:

                break;
            case R.id.rb_hnan:

                break;
            case R.id.rb_gdong:

                break;
            case R.id.rb_gzhou:

                break;
            case R.id.img_btn_consulting:
                Intent intent = new Intent(mCtx, AllWebActivity.class);
                intent.putExtra("webUrl", "http://html.ecqun.com/kf/sdk/openwin.html?corpid=4752991&cstype=rand&mode=0&cskey=qsocaZNfbAGeeXfU7z");
                startActivity(intent);
                break;
            case R.id.btn_commit:
                TastyToast.makeText(OneOnOneActivity.this,"预约成功，良师将会在一天内联系您",TastyToast.LENGTH_LONG,TastyToast.SUCCESS);
                break;
        }
    }
}
