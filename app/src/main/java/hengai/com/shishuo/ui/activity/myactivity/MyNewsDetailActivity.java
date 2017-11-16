package hengai.com.shishuo.ui.activity.myactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import hengai.com.shishuo.R;

/**
 * Created by yu on 2017/11/16.
 */

public class MyNewsDetailActivity extends AppCompatActivity {
    @InjectView(R.id.tv_title)
    TextView mTvTitle;
    @InjectView(R.id.tv_detail)
    TextView mTvDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.img_back)
    public void onClick() {
        finish();
    }
}
