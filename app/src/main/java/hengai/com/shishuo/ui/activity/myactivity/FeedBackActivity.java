package hengai.com.shishuo.ui.activity.myactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import hengai.com.shishuo.R;

/**
 * Created by yu on 2017/11/16.
 */

public class FeedBackActivity extends AppCompatActivity {
    @InjectView(R.id.et_appraise)
    EditText mEtAppraise;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.inject(this);

    }

    @OnClick({R.id.img_back, R.id.tv_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_commit:
                commit();
                break;
        }
    }

    private void commit() {

    }
}
