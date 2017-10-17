package hengai.com.shishuo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import hengai.com.shishuo.R;

/**
 * Created by yu on 2017/10/13.
 */

public class OneIntroduceActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener{
    @InjectView(R.id.slider)
    SliderLayout mSlider;
    @InjectView(R.id.btn_submit)
    Button mBtnSubmit;
    @InjectView(R.id.iv_kf)
    ImageView mIvKf;
    private Context mCtx;
    private List<String> mListUrl=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_introduce);
        ButterKnife.inject(this);
        mCtx=this;
        initData();
        initView();
    }

    private void initView() {
        for(int i=0;i<mListUrl.size();i++){
            TextSliderView textSlider = new TextSliderView(mCtx);
            textSlider.description("跟随良师，方为良师")
                    .image(mListUrl.get(i))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                    .setOnSliderClickListener(this);
            textSlider.bundle(new Bundle());
            //textSlider.getBundle().putInt("extra", beanList.get(i).getId());
            mSlider.addSlider(textSlider);
        }

        mSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSlider.setCustomAnimation(new DescriptionAnimation());
        mSlider.setDuration(4000);
    }

    private void initData() {
        mListUrl=new ArrayList<>();
        mListUrl.add("http://www.liangshiba.com/shx/images/zt_banner.jpg");
        mListUrl.add("http://img.liangshiba.com/uploads/img/2017/05/24/149562002415.jpg");
        mListUrl.add("http://img.liangshiba.com/uploads/img/2017/06/06/149674353426.jpg");
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

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }
}
