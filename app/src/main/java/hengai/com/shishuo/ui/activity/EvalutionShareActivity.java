package hengai.com.shishuo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.sdsmdg.tastytoast.TastyToast;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.utils.SocializeUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import hengai.com.shishuo.R;
import hengai.com.shishuo.utils.LogUtils;

/**
 * Created by yu on 2017/8/24.
 */

public class EvalutionShareActivity extends AppCompatActivity {
    @InjectView(R.id.imgbtn_return)
    ImageButton mImgbtnReturn;
    @InjectView(R.id.user_image)
    CircleImageView mUserImage;
    @InjectView(R.id.tv_user_name)
    TextView mTvUserName;
    @InjectView(R.id.tv_time)
    TextView mTvTime;
    @InjectView(R.id.tv_title)
    TextView mTvTitle;
    @InjectView(R.id.iv_onestart)
    ImageView mIvOnestart;
    @InjectView(R.id.iv_twostart)
    ImageView mIvTwostart;
    @InjectView(R.id.iv_threestart)
    ImageView mIvThreestart;
    @InjectView(R.id.iv_fourstart)
    ImageView mIvFourstart;
    @InjectView(R.id.iv_fivestart)
    ImageView mIvFivestart;
    @InjectView(R.id.tv_money)
    TextView mTvMoney;
    @InjectView(R.id.ll_s)
    LinearLayout mLlS;
    @InjectView(R.id.one_share)
    TextView mOneShare;
    private int mMoney;
    private String mTitle;
    private int mStars;
    Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envalution_share);
        ButterKnife.inject(this);
        mContext = this;
        initData();
        initView();
    }

    private void initView() {
         //TODO
        //进行顶部头像以及用户名的设置
        SimpleDateFormat sdf=new SimpleDateFormat("MM月dd日");
        String date=sdf.format(new java.util.Date());
        mTvTime.setText(date+"  我在良师说");
        mTvTitle.setText("送给"+"“"+mTitle+"”");
        switch (mStars) {
            case 3:
                setTv(3);
                mIvOnestart.setVisibility(View.GONE);
                mIvTwostart.setVisibility(View.GONE);
                break;
            case 4:
                setTv(4);
                mIvOnestart.setVisibility(View.GONE);
                break;
            case 5:
                setTv(5);
                break;
        }
    }

    private void setTv(int i) {
        switch (i) {
            case 3:
                if (mMoney > 0) {
                    mTvMoney.setText("三星好评并打赏了" + mMoney + "元");
                } else {
                    mTvMoney.setText("三星好评" );
                }
                break;
            case 4:
                if (mMoney > 0) {
                    mTvMoney.setText("四星好评并打赏了" + mMoney + "元");
                } else {
                    mTvMoney.setText("四星好评" );
                }
                break;
            case 5:
                if (mMoney > 0) {
                    mTvMoney.setText("五星好评并打赏了" + mMoney + "元");
                } else {
                    mTvMoney.setText("五星好评" );
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    private void initData() {
        mMoney = getIntent().getIntExtra("money", 0);
        mTitle = getIntent().getStringExtra("title");
        mStars = getIntent().getIntExtra("stars", 0);
        LogUtils.d("money" + "=" + mMoney + "++" + mTitle + "++" + mStars);
    }

    @OnClick({R.id.imgbtn_return, R.id.one_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgbtn_return:
                finish();
                break;
            case R.id.one_share:
                share();
                break;
        }
    }

    public Bitmap createViewBitmap(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }

    private void share() {
        UMImage img = new UMImage(EvalutionShareActivity.this, createViewBitmap(mLlS));
        ShareBoardConfig config = new ShareBoardConfig();
        config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_NONE);
        new ShareAction(EvalutionShareActivity.this)
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
            TastyToast.makeText(mContext, "分享成功", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            TastyToast.makeText(mContext, "分享失败", TastyToast.LENGTH_LONG, TastyToast.ERROR);
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            TastyToast.makeText(mContext, "取消分享", TastyToast.LENGTH_LONG, TastyToast.INFO);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }
}
