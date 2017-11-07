package hengai.com.shishuo.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdsmdg.tastytoast.TastyToast;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;

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
        UMWeb img = new UMWeb(url);
        ShareBoardConfig config = new ShareBoardConfig();
        config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_NONE);
        new ShareAction(InfomationDetailsActivity.this)
                .withMedia(img)
                .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QZONE)
                .setCallback(shareListener)
                .open(config);
    }
    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            TastyToast.makeText(getApplicationContext(), "分享成功", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            TastyToast.makeText(getApplicationContext(), "分享失败", TastyToast.LENGTH_LONG, TastyToast.ERROR);
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            TastyToast.makeText(getApplicationContext(), "取消分享", TastyToast.LENGTH_LONG, TastyToast.INFO);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
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
