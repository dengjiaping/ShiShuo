package hengai.com.shishuo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import hengai.com.shishuo.R;
import hengai.com.shishuo.bean.QuestionBean;
import hengai.com.shishuo.network.HiRetorfit;
import hengai.com.shishuo.ui.widget.PopuRecodeVideo;
import hengai.com.shishuo.utils.LogUtils;
import hengai.com.shishuo.utils.SPUtils;
import hengai.com.shishuo.utils.T;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by yu on 2017/9/6.
 */

public class QuestionActivity extends AppCompatActivity {
    @InjectView(R.id.iv_retrun)
    ImageView mIvRetrun;
    @InjectView(R.id.iv_popu)
    ImageView mIvPopu;
    @InjectView(R.id.ll_video_select)
    LinearLayout mLlVideoSelect;
    @InjectView(R.id.title_bar)
    RelativeLayout mTitleBar;
    @InjectView(R.id.im_bkg)
    ImageView mImBkg;
    @InjectView(R.id.linearLayout)
    RelativeLayout mLinearLayout;
    @InjectView(R.id.bt_trial_teaching)
    Button mBtTrialTeaching;
    @InjectView(R.id.bt_speaking)
    Button mBtSpeaking;
    @InjectView(R.id.bt_interview)
    Button mBtInterview;
    @InjectView(R.id.bt_answer)
    Button mBtAnswer;
    @InjectView(R.id.tablelayout)
    TableLayout mTablelayout;
    @InjectView(R.id.bt_select)
    Button mBtSelect;

    private int lessonType2;
    private Intent intent;
    private String[] photoUrl;
    Context mContext;
    private PopuRecodeVideo mPopu;
    private String mCatg1;
    private String mChannel = "liangshishuo";
    private String mToken;
    private String mCatagid;
    private String mScatagid;
    private List<String> mlist=new ArrayList<String>();
    private String mTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellect_exam_2);
        ButterKnife.inject(this);
        mContext = this;
        mBtSelect.setEnabled(false);
        initData();

    }

    private void initView() {
        if (!mToken.equals("1") && !mCatagid.equals("1") && !mScatagid.equals("1")) {
            Call<QuestionBean> call = HiRetorfit.getInstans().getApi().QuestionTest(mChannel, mToken, mCatagid, mScatagid, mCatg1);
            call.enqueue(new Callback<QuestionBean>() {
                @Override
                public void onResponse(Call<QuestionBean> call, Response<QuestionBean> response) {

                    if(response!=null){
                        if(response.body().getResult()==1){
                          mTitle=response.body().getData().getName();
                          List<QuestionBean.DataBean.FilesBean>  files= response.body().getData().getFiles();
                            if(files.size()>0){
                                for(int i=0;i<files.size();i++){
                                    mlist.add(files.get(i).getUrl());
                                }
                            }
                            if(files.size()!=0){
                                intent = new Intent(QuestionActivity.this, StartPrepareActivity.class);
                                intent.putExtra("lessonType2", lessonType2);
                                intent.putStringArrayListExtra("imgUrl", (ArrayList<String>) mlist);
                                intent.putExtra("questionContent", mTitle);
                                startActivity(intent);
                            }else{
                               TastyToast.makeText(mContext,"未抽到题型",TastyToast.LENGTH_SHORT,TastyToast.INFO);
                            }

                        }
                    }else{
                        TastyToast.makeText(mContext,"数据解析错误",TastyToast.LENGTH_SHORT,TastyToast.ERROR);
                    }
                }

                @Override
                public void onFailure(Call<QuestionBean> call, Throwable t) {
                    TastyToast.makeText(mContext,"网络错误",TastyToast.LENGTH_SHORT,TastyToast.ERROR);
                }
            });
        }else{
            TastyToast.makeText(mContext,"请先配置个人信息",TastyToast.LENGTH_SHORT,TastyToast.INFO);
            startActivity(new Intent(mContext,MyDreamActivity.class));
        }

    }

    private void initData() {
        mToken = (String) SPUtils.get(mContext, "token", "1");
        mCatagid = (String) SPUtils.get(mContext, "catgId", "1");
        mScatagid = (String) SPUtils.get(mContext, "scatgId", "1");
        LogUtils.d("question" + mToken + mCatagid + "+++" + mScatagid);
    }

    boolean ss = true;

    @OnClick({R.id.iv_retrun, R.id.ll_video_select, R.id.bt_trial_teaching, R.id.bt_speaking, R.id.bt_interview, R.id.bt_answer, R.id.bt_select})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_retrun:
                finish();
                break;
            case R.id.ll_video_select:
                if (ss) {
                    ss = false;
                    mIvPopu.setImageResource(R.drawable.sl);
                    showPopup();
                } else {
                    ss = true;
                    mIvPopu.setImageResource(R.drawable.xl);
                    mPopu.dismiss();
                }
                break;
            case R.id.bt_trial_teaching:
                lessonType2 = 2;
                mCatg1="SJ";
                mlist.clear();
                mBtTrialTeaching.setSelected(true);
                mBtSpeaking.setSelected(false);
                mBtInterview.setSelected(false);
                mBtAnswer.setSelected(false);
                mBtSelect.setEnabled(true);
                break;
            case R.id.bt_speaking:
                lessonType2 = 0;
                mCatg1="SK";
                mlist.clear();
                mBtTrialTeaching.setSelected(false);
                mBtSpeaking.setSelected(true);
                mBtInterview.setSelected(false);
                mBtAnswer.setSelected(false);
                mBtSelect.setEnabled(true);
                break;
            case R.id.bt_interview:
                lessonType2 = 3;
                mCatg1="JGH";
                mlist.clear();
                mBtTrialTeaching.setSelected(false);
                mBtSpeaking.setSelected(false);
                mBtInterview.setSelected(true);
                mBtAnswer.setSelected(false);
                mBtSelect.setEnabled(true);
                break;
            case R.id.bt_answer:
                lessonType2 = 4;
                mCatg1="DB";
                mlist.clear();
                mBtTrialTeaching.setSelected(false);
                mBtSpeaking.setSelected(false);
                mBtInterview.setSelected(false);
                mBtAnswer.setSelected(true);
                mBtSelect.setEnabled(true);
                break;
            case R.id.bt_select:
                initView();
                break;
        }
    }

    private View.OnClickListener onitemsClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_recode_video:
                    Intent intent = new Intent(QuestionActivity.this, VideoRecorderActivity_2.class);
                    intent.putExtra("source", "recode");
                    startActivity(intent);
                    break;
                case R.id.ll_upload_video:
                    TastyToast.makeText(mContext,"上传视频功能暂未实现",TastyToast.LENGTH_SHORT,TastyToast.INFO);
                    break;
            }
        }
    };
    PopupWindow.OnDismissListener mOnDismissListener = new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            ss = true;
            mIvPopu.setImageResource(R.drawable.xl);
            mPopu.dismiss();
        }
    };

    private void showPopup() {
        mPopu = new PopuRecodeVideo(QuestionActivity.this, onitemsClick);
        mPopu.setOnDismissListener(mOnDismissListener);
        mPopu.showAsDropDown(QuestionActivity.this.findViewById(R.id.ll_video_select),
                0, 0);

    }


}