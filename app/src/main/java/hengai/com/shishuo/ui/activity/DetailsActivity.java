package hengai.com.shishuo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import hengai.com.shishuo.R;

/**
 * Created by yu on 2017/10/13.
 */

public class DetailsActivity extends AppCompatActivity {
    @InjectView(R.id.iv_retrun)
    ImageButton mIvRetrun;
    @InjectView(R.id.tv_title)
    TextView mTvTitle;
    @InjectView(R.id.iv_icon)
    ImageView mIvIcon;
    @InjectView(R.id.iv_details_left)
    ImageView mIvDetailsLeft;
    @InjectView(R.id.tv_class_arrangument)
    TextView mTvClassArrangument;
    @InjectView(R.id.ll_left)
    LinearLayout mLlLeft;
    @InjectView(R.id.iv_details_right)
    ImageView mIvDetailsRight;
    @InjectView(R.id.tv_class_introduce)
    TextView mTvClassIntroduce;
    @InjectView(R.id.ll_right)
    LinearLayout mLlRight;
    @InjectView(R.id.ll_course_details)
    LinearLayout mLlCourseDetails;
    @InjectView(R.id.iv_detail)
    ImageView mIvDetail;
    @InjectView(R.id.btn_enrol)
    Button mBtnEnrol;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.inject(this);
        initData();
        initView();
    }

    private void initData() {

    }

    private void initView() {

    }

    @OnClick({R.id.iv_retrun, R.id.ll_left, R.id.ll_right, R.id.btn_enrol})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_retrun:
                finish();
                break;
            case R.id.ll_left:
                //TODO
                mIvDetailsLeft.setSelected(true);
                mTvClassArrangument.setTextColor(getResources().getColor(R.color.main_color));
                mIvDetailsRight.setSelected(false);
                mTvClassIntroduce.setTextColor(getResources().getColor(R.color.main_button_enable));
                break;
            case R.id.ll_right:
                mIvDetailsLeft.setSelected(false);
                mTvClassArrangument.setTextColor(getResources().getColor(R.color.main_button_enable));
                mIvDetailsRight.setSelected(true);
                mTvClassIntroduce.setTextColor(getResources().getColor(R.color.main_color));
                break;
            case R.id.btn_enrol:
                startActivity(new Intent(DetailsActivity.this,ActionCommitActivity.class));
                break;
        }
    }
}
