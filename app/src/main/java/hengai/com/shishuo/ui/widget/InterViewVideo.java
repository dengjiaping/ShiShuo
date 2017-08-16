package hengai.com.shishuo.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import hengai.com.shishuo.R;
import hengai.com.shishuo.bean.HomeBean;

/**
 * Created by yu on 2017/8/15.
 */

public class InterViewVideo extends RelativeLayout {
    @InjectView(R.id.tv_title)
    TextView mTvTitle;
    @InjectView(R.id.im_teacherclass)
    RatioImageView mImTeacherclass;
    @InjectView(R.id.im_play_btn)
    ImageView mImPlayBtn;
    @InjectView(R.id.fl_teacherclass)
    FrameLayout mFlTeacherclass;
    @InjectView(R.id.tv_teacher_name)
    TextView mTvTeacherName;
    @InjectView(R.id.tv_type2)
    TextView mTvType2;
    @InjectView(R.id.tv_teacher_item_comment_num)
    TextView mTvTeacherItemCommentNum;
    @InjectView(R.id.icon_comment)
    ImageView mIconComment;
    @InjectView(R.id.rl_info_container)
    RelativeLayout mRlInfoContainer;

    public InterViewVideo(Context context) {
        this(context, null);
    }

    public InterViewVideo(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.listview_live_item, this);
        ButterKnife.inject(this);
    }

    public void bindView(HomeBean.DataBean.PracticeBean.PracticeListBean practice) {
        mTvTitle.setText(practice.getTitle());
        mTvType2.setText(practice.getClassType());
        mTvTeacherItemCommentNum.setText(practice.getCommnetNum()+"");
        mTvTeacherName.setText(practice.getAuthor());
        Picasso.with(getContext()).load(practice.getCoverImage()).into(mImTeacherclass);

    }
}
