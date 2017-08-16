package hengai.com.shishuo.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import hengai.com.shishuo.R;

/**
 * Created by yu on 2017/8/15.
 */

public class InterViewMid extends RelativeLayout {
    @InjectView(R.id.rl_latest_practice)
    RelativeLayout mRlLatestPractice;
    @InjectView(R.id.tv_item3_1)
    TextView mTvItem31;
    @InjectView(R.id.tv_num)
    TextView mTvNum;
    @InjectView(R.id.iv_dianji)
    ImageView mIvDianji;
    @InjectView(R.id.enter_into)
    RelativeLayout mEnterInto;

    public InterViewMid(Context context) {
        this(context, null);
    }

    public InterViewMid(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.item_interview_mid, this);
        ButterKnife.inject(this);
    }

    public void bindView(int num) {
       mTvNum.setText(num+"位");
    }

}
