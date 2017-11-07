package hengai.com.shishuo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sdsmdg.tastytoast.TastyToast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import hengai.com.shishuo.R;
import hengai.com.shishuo.bean.LessonVideoBean;
import hengai.com.shishuo.network.HiRetorfit;
import hengai.com.shishuo.ui.widget.PopuIntroduce;
import hengai.com.shishuo.ui.widget.PopuSelect;
import hengai.com.shishuo.ui.widget.RatioImageView;
import hengai.com.shishuo.utils.DateUtil;
import hengai.com.shishuo.utils.LogUtils;
import hengai.com.shishuo.utils.SPUtils;
import hengai.com.shishuo.utils.T;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yu on 2017/9/3.
 */

public class LessonVideoActivity extends AppCompatActivity {
    @InjectView(R.id.imgbtn_return)
    ImageButton mImgbtnReturn;
    @InjectView(R.id.img_btn_categry)
    ImageButton mImgBtnCategry;
    @InjectView(R.id.lv_lesson_video)
    ListView mLvLessonVideo;
    @InjectView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @InjectView(R.id.rb_all)
    RadioButton mRbAll;
    @InjectView(R.id.rb_speak)
    RadioButton mRbSpeak;
    @InjectView(R.id.rb_rehearsal)
    RadioButton mRbRehearsal;
    @InjectView(R.id.rb_structured)
    RadioButton mRbStructured;
    @InjectView(R.id.rb_defense)
    RadioButton mRbDefense;
    @InjectView(R.id.rg_item)
    RadioGroup mRgItem;
    @InjectView(R.id.ll_rb)
    LinearLayout mLlRb;
    Context mContext;
    private PopuSelect mPopuSelect;
    private String mChennel;
    private String mToken;

    int page = 0;
    int paging = 10;
    int bool = 1;
    String[] ctag = {"", "SK", "SJ", "JGH", "DB"};
    List<LessonVideoBean.DataBean.ListBean> list = new ArrayList<>();
    private MyAdapter mMyAdapter;
    private String mType;
    private LessonVideoBean.DataBean mDataBean;
   String type="1";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_video);
        ButterKnife.inject(this);
        mContext = this;
        mChennel = (String) SPUtils.get(mContext, "channel", "1");
        mToken = (String) SPUtils.get(mContext, "token", "1");
        //LogUtils.d(SPUtils.get(mContext, "catgId", "1") + "++++" + SPUtils.get(mContext, "scatgId", "1"));
        mType = "";

        initData(mType);
    }

    private void initView() {
        mMyAdapter = new MyAdapter();
        mLvLessonVideo.setAdapter(mMyAdapter);
        mLvLessonVideo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(LessonVideoActivity.this, MediaPlayActivity.class);
                intent.putExtra("videoId", list.get(position).getVideoId());
                intent.putExtra("code", list.get(position).getCode());
                intent.putExtra("type",list.get(position).getCtag2());
                startActivity(intent);

                //startActivity(new Intent(LessonVideoActivity.this, LessonDetailActivity.class));
            }
        });
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 0;
                list.clear();
                refreshlayout.finishRefresh(initData(mType));

            }
        });

        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if(page<(mDataBean.getTotal()/paging)){
                    LogUtils.d(mDataBean.getTotal()/paging+"++++++++"+page);
                    page = page + 1;
                    refreshlayout.finishLoadmore(initData(mType));
                }else{
                    refreshlayout.finishLoadmore(2000);
                }/*else if(page==(mDataBean.getTotal()/paging)+1){
                    page = page + 1;
                    paging=mDataBean.getTotal()%paging;
                }*/


            }
        });
    }

    private boolean initData(String type) {

        Call<LessonVideoBean> call = HiRetorfit.getInstans().getApi().LessonVideo(mChennel, mToken, "", "",mType, "",page,paging);
        call.enqueue(new Callback<LessonVideoBean>() {
            @Override
            public void onResponse(Call<LessonVideoBean> call, Response<LessonVideoBean> response) {
                if (response!= null) {
                    if (response.body().getResult() == 1) {
                        mDataBean = response.body().getData();
                        list.addAll(response.body().getData().getList());
                        if (bool == 1) {
                            bool = 2;
                            initView();
                        } else {
                            mMyAdapter.notifyDataSetChanged();
                        }

                    } else if (response.body().getResult() == -1) {
                        startActivity(new Intent(LessonVideoActivity.this, LoginActivity.class));
                        TastyToast.makeText(LessonVideoActivity.this, "登录失效", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    } else if (response.body().getResult() == 0) {
                        TastyToast.makeText(LessonVideoActivity.this, "服务器错误", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    }
                } else {

                }


            }

            @Override
            public void onFailure(Call<LessonVideoBean> call, Throwable t) {

            }
        });
        return true;
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (list != null) {
                return list.size();
            } else {
                return 0;
            }
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.item_lesson_video, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.mTvTitle.setText(list.get(position).getTitle());
            String startDate = DateUtil.getDate(list.get(position).getStartDate());
            viewHolder.mTvTime.setText(startDate);
            if(!list.get(position).getVideoHeadUrl().equals("")){
                Picasso.with(LessonVideoActivity.this).load(list.get(position).getVideoHeadUrl()).into(viewHolder.mImTeacherclass);
            }


            if (!list.get(position).getIsRefered().equals("Y")) {
                viewHolder.mIvTj.setVisibility(View.GONE);
            }

            viewHolder.mTvTeacherName.setText(list.get(position).getTeacher());
            viewHolder.mTvTeacherItemCommentNum.setText(list.get(position).getCommentNum()+"");

            switch (list.get(position).getCtag1()) {
                case "SK":
                    viewHolder.mTvType2.setText("说课");
                    break;
                case "JGH":
                    viewHolder.mTvType2.setText("结构化");
                    break;
                case "DB":
                    viewHolder.mTvType2.setText("答辩");
                    break;
                case "SJ":
                    viewHolder.mTvType2.setText("试讲");
                    break;
            }
            switch (list.get(position).getCtag2()) {
                case "STUD":
                    viewHolder.mTvType3.setText("学生");

                    break;
                case "TEACH":
                    viewHolder.mTvType3.setText("老师");

                    break;
            }
            return convertView;
        }

        class ViewHolder {
            @InjectView(R.id.iv_tj)
            ImageView mIvTj;
            @InjectView(R.id.tv_title)
            TextView mTvTitle;
            @InjectView(R.id.tv_time)
            TextView mTvTime;
            @InjectView(R.id.rl_lesson)
            RelativeLayout mRlLesson;
            @InjectView(R.id.im_teacherclass)
            RatioImageView mImTeacherclass;
            @InjectView(R.id.im_play_btn)
            ImageView mImPlayBtn;
            @InjectView(R.id.fl_teacherclass)
            FrameLayout mFlTeacherclass;
            @InjectView(R.id.circle)
            ImageView mCircle;
            @InjectView(R.id.tv_teacher_name)
            TextView mTvTeacherName;
            @InjectView(R.id.tv_type2)
            TextView mTvType2;
            @InjectView(R.id.tv_type3)
            TextView mTvType3;
            @InjectView(R.id.tv_teacher_item_comment_num)
            TextView mTvTeacherItemCommentNum;
            @InjectView(R.id.icon_comment)
            ImageView mIconComment;
            @InjectView(R.id.rl_info_container)
            RelativeLayout mRlInfoContainer;

            ViewHolder(View view) {
                ButterKnife.inject(this, view);
            }
        }
    }


    boolean ss = true;

    @OnClick({R.id.imgbtn_return, R.id.img_btn_categry})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgbtn_return:
                finish();
                break;
            case R.id.img_btn_categry:
                if (ss) {
                    ss = false;
                    mImgBtnCategry.setImageResource(R.drawable.sl);
                    showPopuSelect();
                } else {
                    ss = true;
                    mImgBtnCategry.setImageResource(R.drawable.xl);
                    mPopuSelect.dismiss();
                }
                break;

        }
    }

    PopupWindow.OnDismissListener mOnDismissListener = new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            ss = true;
            mImgBtnCategry.setImageResource(R.drawable.xl);
            mPopuSelect.dismiss();
        }
    };

    private void showPopuSelect() {

        mPopuSelect = new PopuSelect(LessonVideoActivity.this, itemsonClick);
        mPopuSelect.setOnDismissListener(mOnDismissListener);
        mPopuSelect.showAsDropDown(LessonVideoActivity.this.findViewById(R.id.rl_lesson_video),
                0, 0);

    }

    private View.OnClickListener itemsonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rb_all:
                    page=0;
                    list.clear();
                    bool=1;
                    mType = "";
                    initData(mType);
                    mPopuSelect.dismiss();
                    break;
                case R.id.rb_speak:
                    page=0;
                    list.clear();
                    bool=1;
                    mType = "SK";
                    initData(mType);
                    mPopuSelect.dismiss();
                    break;
                case R.id.rb_rehearsal:
                    page=0;
                    list.clear();
                    bool=1;
                    mType = "SJ";
                    initData(mType);
                    mPopuSelect.dismiss();
                    break;
                case R.id.rb_structured:
                    page=0;
                    list.clear();
                    bool=1;
                    mType = "JGH";
                    initData(mType);
                    mPopuSelect.dismiss();
                    break;
                case R.id.rb_defense:
                    page=0;
                    list.clear();
                    bool=1;
                    mType = "DB";
                    initData(mType);
                    mPopuSelect.dismiss();
                    break;
            }

        }
    };
}
