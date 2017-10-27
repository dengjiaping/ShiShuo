package hengai.com.shishuo.ui.activity.myactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import hengai.com.shishuo.R;
import hengai.com.shishuo.ui.activity.MyDreamActivity;
import hengai.com.shishuo.utils.SPUtils;

/**
 * Created by yu on 2017/10/27.
 */

public class ShowMySettingActivity extends AppCompatActivity {
    @InjectView(R.id.tv_name)
    TextView mTvName;
    @InjectView(R.id.iv_head)
    CircleImageView mIvHead;
    @InjectView(R.id.tv_nameid)
    TextView mTvNameid;
    @InjectView(R.id.tv_name2)
    TextView mTvName2;
    @InjectView(R.id.tv_type)
    TextView mTvType;
    @InjectView(R.id.tv_level)
    TextView mTvLevel;
    @InjectView(R.id.tv_subject)
    TextView mTvSubject;
    @InjectView(R.id.tv_provinces)
    TextView mTvProvinces;
    private String mExam;
    private String mSchool;
    private String mSubject;
    private String mCity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_mysetting);
        ButterKnife.inject(this);
        initData();

    }

    private void initData() {
        mExam = (String) SPUtils.get(getApplicationContext(), "exam", "1");
        mSchool = (String) SPUtils.get(getApplicationContext(), "school", "1");
        mSubject = (String) SPUtils.get(getApplicationContext(), "subject", "1");
        mCity = (String) SPUtils.get(getApplicationContext(), "city", "1");
        initView();
    }

    private void initView() {
        if (((String) SPUtils.get(getApplicationContext(), "headUrl", "1")).equals("1")) {

        } else {
            File file = new File((String) SPUtils.get(getApplicationContext(), "headUrl", "1"));
            Picasso.with(getApplicationContext()).load(file).into(mIvHead);
        }
        mTvType.setText(mExam);
        mTvLevel.setText(mSchool);
        mTvSubject.setText(mSubject);
        mTvProvinces.setText(mCity);

    }

    @OnClick({R.id.iv_return, R.id.iv_modification, R.id.iv_head, R.id.tv_my_attribute, R.id.btn_changepwd, R.id.tv_pull_out})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                finish();
                break;
            case R.id.iv_modification:
                Intent intent2 = new Intent(ShowMySettingActivity.this, MyInformationActivity.class);
                intent2.putExtra("name", "1");
                startActivityForResult(intent2, 0);
                break;
            case R.id.iv_head:
                Intent intent3 = new Intent(ShowMySettingActivity.this, MyInformationActivity.class);
                intent3.putExtra("name", "1");
                startActivityForResult(intent3, 0);
                break;
            case R.id.tv_my_attribute:
                Intent intent1 = new Intent(ShowMySettingActivity.this, MyDreamActivity.class);
                intent1.putExtra("activity", "1");
                startActivityForResult(intent1, 0);
                break;
            case R.id.btn_changepwd:
                startActivity(new Intent(ShowMySettingActivity.this, ChangePwdActivity.class));
                break;
            case R.id.tv_pull_out:

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        initView();
    }
}
