package hengai.com.shishuo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bokecc.sdk.mobile.live.DWLive;
import com.bokecc.sdk.mobile.live.DWLiveLoginListener;
import com.bokecc.sdk.mobile.live.Exception.DWLiveException;
import com.bokecc.sdk.mobile.live.pojo.PublishInfo;
import com.bokecc.sdk.mobile.live.pojo.RoomInfo;
import com.bokecc.sdk.mobile.live.pojo.TemplateInfo;
import com.bokecc.sdk.mobile.live.pojo.Viewer;
import com.sdsmdg.tastytoast.TastyToast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import hengai.com.shishuo.R;
import hengai.com.shishuo.live.activity.PcLivePlayActivity;
import hengai.com.shishuo.utils.LogUtils;
import hengai.com.shishuo.utils.SPUtils;
import hengai.com.shishuo.utils.T;

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
    private String mVideoId;
    private String mPhone;
    private String mUserId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.inject(this);
        String title=getIntent().getStringExtra("title");
        mVideoId = getIntent().getStringExtra("videoId");
        mUserId = (String) SPUtils.get(this,getIntent().getStringExtra("cfgId"),"372FAB00A0958D2F");

        mPhone = (String) SPUtils.get(this,"phone","1");
        if(mPhone.equals("1")){
            mPhone = (String) SPUtils.get(this,"umname","良粉");
        }
        mTvTitle.setText(title);
        LogUtils.d(mPhone+"xxxx"+mVideoId+"___"+mUserId);
    }

    @OnClick({R.id.iv_return, R.id.tv_into_live, R.id.tv_sharing})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                finish();
                break;
            case R.id.tv_into_live:
                if(mVideoId!=null){
                    live();
                }else{
                    TastyToast.makeText(SignUpActivity.this, "播放错误请联系客服", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                }

                break;
            case R.id.tv_sharing:
                T.showShort(this,"点击了");
                break;
        }
    }

    private void live() {
        DWLive.getInstance().setDWLiveLoginParams(new DWLiveLoginListener() {
            @Override
            public void onLogin(TemplateInfo templateInfo, Viewer viewer, RoomInfo roomInfo, PublishInfo publishInfo) {

                startActivity(new Intent(SignUpActivity.this, PcLivePlayActivity.class));

            }

            @Override
            public void onException(final DWLiveException e) {
                //toast(mContext, e.getLocalizedMessage());
            }
        }, mUserId,mVideoId, mPhone, "shishuo");
        DWLive.getInstance().startLogin();
        //1F95D50A9DE128D5
        //372FAB00A0958D2F
        //https://view.csslcloud.net/api/view/index?roomid=0D6DAFFEADD16F749C33DC5901307461&userid=372FAB00A0958D2F
    }

}
