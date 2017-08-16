package hengai.com.shishuo.ui.widget;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import hengai.com.shishuo.R;
import hengai.com.shishuo.bean.HomeBean;
import hengai.com.shishuo.utils.T;

/**
 * Created by yu on 2017/8/14.
 */

public class InterViewHead extends RelativeLayout implements BaseSliderView.OnSliderClickListener {
    private LayoutInflater linflater;
    @InjectView(R.id.slider)
    SliderLayout mSlider;
    @InjectView(R.id.ll_live)
    LinearLayout mLlLive;
    @InjectView(R.id.ll_practice)
    LinearLayout mLlPractice;
    @InjectView(R.id.ll_data)
    LinearLayout mLlData;
    @InjectView(R.id.ll_notice)
    LinearLayout mLlNotice;
    @InjectView(R.id.rl_latest_live)
    RelativeLayout mRlLatestLive;

    public InterViewHead(Context context) {
        this(context, null);
    }

    public InterViewHead(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SliderLayout getslider() {
        return mSlider;
    }

    private void init() {
        View.inflate(getContext(), R.layout.interviewhead, this);
        ButterKnife.inject(this, this);
        linflater = LayoutInflater.from(getContext());
    }

    public void bindView(List<HomeBean.DataBean.BannerBean> beanList) {
        for (int i = 0; i < beanList.size(); i++) {
            TextSliderView textSlider = new TextSliderView(getContext());
            textSlider.image(beanList.get(i).getUrl())
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                    .setOnSliderClickListener(this);
            textSlider.bundle(new Bundle());
            textSlider.getBundle().putInt("extra", beanList.get(i).getId());
            mSlider.addSlider(textSlider);
        }


        mSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSlider.setCustomAnimation(new DescriptionAnimation());
        mSlider.setDuration(4000);
    }

    @OnClick({R.id.ll_live, R.id.ll_practice, R.id.ll_data, R.id.ll_notice, R.id.rl_latest_live})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_live:
                T.showShort(getContext(), "直播");
                break;
            case R.id.ll_practice:
                T.showShort(getContext(), "练课");
                break;
            case R.id.ll_data:
                T.showShort(getContext(), "资料");
                break;
            case R.id.ll_notice:
                T.showShort(getContext(), "公告");
                break;
            case R.id.rl_latest_live:
                T.showShort(getContext(), "最新直播更多");
                break;
        }
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        switch ((int) slider.getBundle().get("extra")) {
            case 1:
                T.showShort(getContext(), "1++");
                break;
            case 2:
                T.showShort(getContext(), "2++");
                break;
            case 3:
                T.showShort(getContext(), "3++");
                break;
        }


    }

}
