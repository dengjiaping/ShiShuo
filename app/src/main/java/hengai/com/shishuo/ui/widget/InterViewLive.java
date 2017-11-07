package hengai.com.shishuo.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;
import hengai.com.shishuo.R;
import hengai.com.shishuo.bean.HomeBean;
import hengai.com.shishuo.utils.DateUtil;
import hengai.com.shishuo.utils.LogUtils;

/**
 * Created by yu on 2017/8/15.
 */

public class InterViewLive extends RelativeLayout {
    @InjectView(R.id.tv_face_name)
    TextView mTvFaceName;
    @InjectView(R.id.tv_time_year)
    TextView mTvTimeYear;
    @InjectView(R.id.tv_time_houes)
    TextView mTvTimeHoues;
    @InjectView(R.id.one_image)
    CircleImageView mOneImage;
    @InjectView(R.id.one_name)
    TextView mOneName;
    @InjectView(R.id.ll_techcer1)
    LinearLayout mLlTechcer1;
    @InjectView(R.id.two_image)
    CircleImageView mTwoImage;
    @InjectView(R.id.two_name)
    TextView mTwoName;
    @InjectView(R.id.ll_techcer2)
    LinearLayout mLlTechcer2;
    @InjectView(R.id.tv_enrol)
    TextView mTvEnrol;
    @InjectView(R.id.tv_persornum)
    TextView mTvPersornum;

    public InterViewLive(Context context) {
        this(context, null);
    }

    public InterViewLive(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.inter_list_item1, this);
        ButterKnife.inject(this);
    }

    public void bindView(HomeBean.DataBean.LiveBean liveBean) {

        mTvFaceName.setText(liveBean.getTitle()+"");
        mTvPersornum.setText(liveBean.getPersonNum()+"");
        if(liveBean.isMybuy()){
            mTvEnrol.setText("已报名");
            mTvEnrol.setTextColor(getResources().getColor(R.color.replay));
        }
        mTvTimeYear.setText(DateUtil.getDate(liveBean.getStartDate()));
        mTvTimeHoues.setText(DateUtil.getTime(liveBean.getStartTime())+"-"+DateUtil.getTime(liveBean.getEndTime()));

       if(liveBean.getTeachers().size()==1){
           Picasso.with(getContext()).load(liveBean.getTeachers().get(0).getTeacherIcon()).into(mOneImage);
           mOneName.setText(liveBean.getTeachers().get(0).getTeacherName());
           mLlTechcer2.setVisibility(View.GONE);
       }else if(liveBean.getTeachers().size()==2){
           Picasso.with(getContext()).load(liveBean.getTeachers().get(0).getTeacherIcon()).into(mOneImage);
           mOneName.setText(liveBean.getTeachers().get(0).getTeacherName());
           Picasso.with(getContext()).load(liveBean.getTeachers().get(1).getTeacherIcon()).into(mTwoImage);
           mTwoName.setText(liveBean.getTeachers().get(1).getTeacherName());
       }else{
           mLlTechcer1.setVisibility(View.GONE);
           mLlTechcer2.setVisibility(View.GONE);
       }

    }
}
