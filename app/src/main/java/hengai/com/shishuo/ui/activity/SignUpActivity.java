package hengai.com.shishuo.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import hengai.com.shishuo.R;

/**
 * Created by yu on 2017/8/24.
 */

public class SignUpActivity extends AppCompatActivity {
    @InjectView(R.id.iv_return)
    ImageView mIvReturn;
    @InjectView(R.id.tv_title)
    TextView mTvTitle;
    @InjectView(R.id.tv_into_live)
    TextView mTvIntoLive;
    @InjectView(R.id.tv_sharing)
    TextView mTvSharing;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.inject(this);
        String title=getIntent().getStringExtra("title");
        mTvTitle.setText(title);
    }

    @OnClick({R.id.iv_return, R.id.tv_into_live, R.id.tv_sharing})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                finish();
                break;
            case R.id.tv_into_live:

                break;
            case R.id.tv_sharing:

                break;
        }
    }
}
