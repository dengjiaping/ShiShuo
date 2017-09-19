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

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import hengai.com.shishuo.R;
import hengai.com.shishuo.ui.widget.PopuRecodeVideo;
import hengai.com.shishuo.utils.T;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellect_exam_2);
        ButterKnife.inject(this);
        mContext = this;
        mBtSelect.setEnabled(false);
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
                mBtTrialTeaching.setSelected(true);
                mBtSpeaking.setSelected(false);
                mBtInterview.setSelected(false);
                mBtAnswer.setSelected(false);
                mBtSelect.setEnabled(true);
                break;
            case R.id.bt_speaking:
                lessonType2 = 0;
                mBtTrialTeaching.setSelected(false);
                mBtSpeaking.setSelected(true);
                mBtInterview.setSelected(false);
                mBtAnswer.setSelected(false);
                mBtSelect.setEnabled(true);
                break;
            case R.id.bt_interview:
                lessonType2 = 3;
                mBtTrialTeaching.setSelected(false);
                mBtSpeaking.setSelected(false);
                mBtInterview.setSelected(true);
                mBtAnswer.setSelected(false);
                mBtSelect.setEnabled(true);
                break;
            case R.id.bt_answer:
                lessonType2 = 4;
                mBtTrialTeaching.setSelected(false);
                mBtSpeaking.setSelected(false);
                mBtInterview.setSelected(false);
                mBtAnswer.setSelected(true);
                mBtSelect.setEnabled(true);
                break;
            case R.id.bt_select:
                intent=new Intent(QuestionActivity.this, StartPrepareActivity.class);
                intent.putExtra("lessonType2", lessonType2);
                intent.putExtra("questionContent", "三年级下|良师智胜");
                startActivity(intent);
                break;
        }
    }
    private View.OnClickListener onitemsClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_recode_video:
                    Intent intent=new Intent(QuestionActivity.this,VideoRecorderActivity_2.class);
                    intent.putExtra("source","recode");
                    startActivity(intent);
                    break;
                case R.id.ll_upload_video:
                    T.showShort(mContext, "上传视频功能暂未实现");
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