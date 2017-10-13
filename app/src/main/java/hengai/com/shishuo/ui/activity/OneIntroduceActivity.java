package hengai.com.shishuo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.daimajia.slider.library.SliderLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import hengai.com.shishuo.R;

/**
 * Created by yu on 2017/10/13.
 */

public class OneIntroduceActivity extends AppCompatActivity {
    @InjectView(R.id.slider)
    SliderLayout mSlider;
    @InjectView(R.id.btn_submit)
    Button mBtnSubmit;
    @InjectView(R.id.iv_kf)
    ImageView mIvKf;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_introduce);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.btn_submit, R.id.iv_kf})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                startActivity(new Intent(OneIntroduceActivity.this,OneOnOneActivity.class));
                finish();
                break;
            case R.id.iv_kf:
                Intent intent = new Intent(OneIntroduceActivity.this, AllWebActivity.class);
                intent.putExtra("webUrl", "http://html.ecqun.com/kf/sdk/openwin.html?corpid=4752991&cstype=rand&mode=0&cskey=qsocaZNfbAGeeXfU7z");
                startActivity(intent);
                break;
        }
    }
}
