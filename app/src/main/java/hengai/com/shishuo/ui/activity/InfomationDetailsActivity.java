package hengai.com.shishuo.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import hengai.com.shishuo.R;
import hengai.com.shishuo.bean.InfoMationDetailBean;
import hengai.com.shishuo.network.HiRetorfit;
import hengai.com.shishuo.utils.SPUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yu on 2017/9/19.
 */

public class InfomationDetailsActivity extends AppCompatActivity {
    @InjectView(R.id.iv_return)
    ImageView mIvReturn;
    @InjectView(R.id.iv_start)
    ImageView mIvStart;
    @InjectView(R.id.iv_share)
    ImageView mIvShare;
    @InjectView(R.id.iv_infomation)
    ImageView mIvInfomation;
    @InjectView(R.id.tv_title)
    TextView mTvTitle;
    @InjectView(R.id.circle_view)
    CircleImageView mCircleView;
    @InjectView(R.id.tv_name)
    TextView mTvName;
    @InjectView(R.id.tv_time)
    TextView mTvTime;
    @InjectView(R.id.tv_person_num)
    TextView mTvPersonNum;
    @InjectView(R.id.webv_infomation)
    WebView mWebvInfomation;

    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infomation_details);
        ButterKnife.inject(this);
        initData();
    }

    private void initData() {
        //url="http://mp.weixin.qq.com/s/8mkmc1RNuDZ3TRV3wpPDSQ";
        String mchannel = (String) SPUtils.get(this, "channel", "liangshishuo");
        String id = getIntent().getStringExtra("id");
        Call<InfoMationDetailBean> call = HiRetorfit.getInstans().getApi().InfoMationDetail(mchannel, id);
        call.enqueue(new Callback<InfoMationDetailBean>() {
            @Override
            public void onResponse(Call<InfoMationDetailBean> call, Response<InfoMationDetailBean> response) {
                if (response != null) {
                    if (response.body().getCode() == 200) {
                        url = response.body().getData().getUrl();
                        initView();
                    }
                }
            }

            @Override
            public void onFailure(Call<InfoMationDetailBean> call, Throwable t) {

            }
        });

        //url="http://x.iyoukiss.com/qbexamsess_ent.html?sesscode=6rq1zovb&checkopen=fDjeG2Tz1JEhZtERm4btxsz1NUCfvBrBsf5uhmvsLx4eeFSc&from=groupmessage";
    }

    private void initView() {
        WebSettings settings = mWebvInfomation.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);
        mWebvInfomation.loadUrl(url);
    }


    @OnClick({R.id.iv_return, R.id.iv_start, R.id.iv_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                finish();
                break;
            case R.id.iv_start:
                collect();
                break;
            case R.id.iv_share:
                share();
                break;
        }
    }

    private void share() {

    }

    int x = 1;

    private void collect() {
        if (x == 1) {
            x = 0;
            mIvStart.setImageResource(R.drawable.sc_dl);
        } else {
            x = 1;
            mIvStart.setImageResource(R.drawable.sc);
        }

    }
}
