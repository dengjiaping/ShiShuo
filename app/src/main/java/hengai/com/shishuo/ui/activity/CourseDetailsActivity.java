package hengai.com.shishuo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sdsmdg.tastytoast.TastyToast;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import hengai.com.shishuo.R;
import hengai.com.shishuo.bean.CourseDetailsBean;
import hengai.com.shishuo.bean.Deletecomm;
import hengai.com.shishuo.bean.LiveCourseInfo;
import hengai.com.shishuo.network.HiRetorfit;
import hengai.com.shishuo.ui.adapter.LiveDerailImgAdapter;
import hengai.com.shishuo.ui.adapter.LiveDetailLeftAdapter;
import hengai.com.shishuo.utils.DateUtil;
import hengai.com.shishuo.utils.SPUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yu on 2017/8/18.
 */

public class CourseDetailsActivity extends AppCompatActivity {
    @InjectView(R.id.img_btn_retern)
    ImageButton mImgBtnRetern;
    @InjectView(R.id.tv_title)
    TextView mTvTitle;
    @InjectView(R.id.class_num)
    TextView mClassNum;
    @InjectView(R.id.person_num)
    TextView mPersonNum;
    @InjectView(R.id.tv_live_time)
    TextView mTvLiveTime;
    @InjectView(R.id.iv_details_left)
    ImageView mIvDetailsLeft;
    @InjectView(R.id.tv_class_arrangument)
    TextView mTvClassArrangument;
    @InjectView(R.id.ll_left)
    LinearLayout mLlLeft;
    @InjectView(R.id.iv_details_right)
    ImageView mIvDetailsRight;
    @InjectView(R.id.tv_class_introduce)
    TextView mTvClassIntroduce;
    @InjectView(R.id.ll_right)
    LinearLayout mLlRight;
    @InjectView(R.id.ll_course_details)
    LinearLayout mLlCourseDetails;
    @InjectView(R.id.rl_top)
    RelativeLayout mRlTop;
    @InjectView(R.id.tv_free)
    TextView mTvFree;
    @InjectView(R.id.ll_consuting)
    LinearLayout mLlConsuting;
    @InjectView(R.id.tv_sign_free)
    TextView mTvSignFree;
    @InjectView(R.id.lv_detail)
    ListView mLvDetail;
    private List<LiveCourseInfo.DataBean.CourseArrangementBean> mList;
    private LiveCourseInfo mCourseDetailsBean;
    Context mContext;
    String url;
    private String mChannel;
    private String mToken;
    private String mCrcode;
    private String mIsMyBuy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        ButterKnife.inject(this);
        mContext = this;
        initData();

    }

    private void initView() {
        mLvDetail.setAdapter(new LiveDetailLeftAdapter(mList,mContext));
        mTvTitle.setText(mCourseDetailsBean.getData().getTitle());
        mClassNum.setText(mCourseDetailsBean.getData().getClassHours()+"课时");
        mPersonNum.setText(mCourseDetailsBean.getData().getPersonNum()+"人");
        String startTime= DateUtil.getDate(mCourseDetailsBean.getData().getStartDate());
        String endTime= DateUtil.getDate(mCourseDetailsBean.getData().getEndDate());
        mTvLiveTime.setText(startTime+"-"+endTime);
        mIvDetailsLeft.setSelected(true);
        mTvClassArrangument.setTextColor(getResources().getColor(R.color.main_color));
        if(mIsMyBuy.equals("Y")){
            mTvFree.setText("已报名");
            mTvFree.setEnabled(false);
        }

    }

    private void initData() {
        mChannel = (String) SPUtils.get(this, "channel", "1");
        mToken = (String) SPUtils.get(this, "token", "1");
        mCrcode = getIntent().getStringExtra("crcode");
        mIsMyBuy = getIntent().getStringExtra("ismybuy");
        url=getIntent().getStringExtra("tvhtml");
        Call<LiveCourseInfo> call = HiRetorfit.getInstans().getApi().CourseDetail(mChannel, mToken, mCrcode);
        call.enqueue(new Callback<LiveCourseInfo>() {
            @Override
            public void onResponse(Call<LiveCourseInfo> call, Response<LiveCourseInfo> response) {
                if (response.body().getResult() == 1) {
                    mCourseDetailsBean = response.body();
                    mList = mCourseDetailsBean.getData().getCourseArrangement();
                    initView();
                } else if (response.body().getResult() == -1) {
                    TastyToast.makeText(mContext, "服务器错误", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                } else if (response.body().getResult() == 0) {
                    //TODO
                    TastyToast.makeText(mContext, "登录失效", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    startActivity(new Intent(mContext,LoginActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<LiveCourseInfo> call, Throwable t) {
                TastyToast.makeText(mContext, "网络错误", TastyToast.LENGTH_LONG, TastyToast.ERROR);
            }
        });
    }

    @OnClick({R.id.img_btn_retern, R.id.ll_left, R.id.ll_right, R.id.ll_consuting, R.id.tv_sign_free})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_btn_retern:
                finish();
                break;
            case R.id.ll_left:
                mLvDetail.setAdapter(new LiveDetailLeftAdapter(mList,mContext));
                mIvDetailsLeft.setSelected(true);
                mTvClassArrangument.setTextColor(getResources().getColor(R.color.main_color));
                mIvDetailsRight.setSelected(false);
                mTvClassIntroduce.setTextColor(getResources().getColor(R.color.main_button_enable));
                break;
            case R.id.ll_right:
                mLvDetail.setAdapter(new LiveDerailImgAdapter(url,mContext));
                mIvDetailsLeft.setSelected(false);
                mTvClassArrangument.setTextColor(getResources().getColor(R.color.main_button_enable));
                mIvDetailsRight.setSelected(true);
                mTvClassIntroduce.setTextColor(getResources().getColor(R.color.main_color));
                break;
            case R.id.ll_consuting:
                Intent intent1 = new Intent(mContext, AllWebActivity.class);
                intent1.putExtra("webUrl", "http://html.ecqun.com/kf/sdk/openwin.html?corpid=4752991&cstype=rand&mode=0&cskey=qsocaZNfbAGeeXfU7z");
                startActivity(intent1);
                break;
            case R.id.tv_sign_free:
                if(mCourseDetailsBean!=null){

                    if(mCourseDetailsBean.getData().getCourseArrangement().size()==0){
                        TastyToast.makeText(mContext, "数据错误请联系客服", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    }else{
                        buy();
                    }
                }else{
                    TastyToast.makeText(mContext, "数据错误", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                }
                break;
        }
    }
    private void buy() {
        Call<Deletecomm> call=HiRetorfit.getInstans().getApi().MyBuy(mChannel,mToken,mCrcode,"");
        call.enqueue(new Callback<Deletecomm>() {
            @Override
            public void onResponse(Call<Deletecomm> call, Response<Deletecomm> response) {
                if(response!=null){
                    if(response.body().getResult()==1){
                        TastyToast.makeText(getApplicationContext(), "报名成功", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                        Intent intent = new Intent(CourseDetailsActivity.this,SignUpActivity.class);
                        intent.putExtra("title",mCourseDetailsBean.getData().getTitle());
                        intent.putExtra("videoId",mCourseDetailsBean.getData().getCourseArrangement().get(0).getVideoId());
                        intent.putExtra("cfgId",mCourseDetailsBean.getData().getCourseArrangement().get(0).getCfgId()+"");
                        startActivity(intent);
                    }else{
                        TastyToast.makeText(getApplicationContext(), "报名失败请联系客服", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    }
                }
            }

            @Override
            public void onFailure(Call<Deletecomm> call, Throwable t) {

            }
        });
    }
}
