package hengai.com.shishuo.ui.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
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

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import hengai.com.shishuo.R;
import hengai.com.shishuo.bean.Deletecomm;
import hengai.com.shishuo.bean.ReViewBean;
import hengai.com.shishuo.bean.VideoCouseInfo;
import hengai.com.shishuo.network.HiRetorfit;
import hengai.com.shishuo.ui.widget.PopuIntroduce;
import hengai.com.shishuo.utils.LogUtils;
import hengai.com.shishuo.utils.SPUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yu on 2017/10/24.
 */

public class TeacherCommentsActivity extends AppCompatActivity {
    @InjectView(R.id.img_btn_return)
    ImageButton mImgBtnReturn;
    @InjectView(R.id.tv_title)
    TextView mTvTitle;
    @InjectView(R.id.img_btn_share)
    ImageButton mImgBtnShare;

    @InjectView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @InjectView(R.id.expandable)
    ExpandableListView mExpandable;
    private String mChannel;
    private String mToken;
    private String mCode;
    private VideoCouseInfo.DataBean mData;
    private List<VideoCouseInfo.DataBean.CommentsBean> mList = new ArrayList<VideoCouseInfo.DataBean.CommentsBean>();
    private PopuIntroduce mPopuIntroduce;
    private String mTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_appraise);
        ButterKnife.inject(this);
        mChannel = (String) SPUtils.get(getApplicationContext(), "channel", "1");
        mToken = (String) SPUtils.get(getApplicationContext(), "token", "1");
        mCode = getIntent().getStringExtra("code");
        initData();

    }

    private void initView() {
        mTvTitle.setText(mTitle);
        mExpandable.setGroupIndicator(null);
        mExpandable.setAdapter(new MyAdapter());
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mList.clear();
                refreshlayout.finishRefresh(initData());

            }
        });

        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mList.clear();
                    refreshlayout.finishLoadmore(initData());
            }
        });
    }

    private boolean initData() {
        mTitle = getIntent().getStringExtra("title");
        Call<VideoCouseInfo> call = HiRetorfit.getInstans().getApi().LessonVideoCouse(mChannel, mToken, mCode);
        call.enqueue(new Callback<VideoCouseInfo>() {
            @Override
            public void onResponse(Call<VideoCouseInfo> call, Response<VideoCouseInfo> response) {

                if (response != null) {
                    if (response.body().getResult() == 1) {
                        mData = response.body().getData();

                        for (int i = 0; i < mData.getComments().size(); i++) {
                            if (mData.getComments().get(i).getFromUserType().equals("T")) {
                                mList.add(mData.getComments().get(i));
                            }
                        }
                        initView();
                    }
                }
            }

            @Override
            public void onFailure(Call<VideoCouseInfo> call, Throwable t) {
                TastyToast.makeText(getApplicationContext(), "网络错误", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
            }
        });
        return true;
    }


    @OnClick({R.id.img_btn_return, R.id.img_btn_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_btn_return:
                finish();
                break;
            case R.id.img_btn_share:

                break;
        }
    }

    class MyAdapter extends BaseExpandableListAdapter {
List<VideoCouseInfo.DataBean.CommentsBean.ReplaysBean> list=null;
        @Override
        public int getGroupCount() {
            return mList.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return mList.get(groupPosition).getReplys().size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return null;
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return null;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return 0;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = View.inflate(TeacherCommentsActivity.this, R.layout.item_teacher_comments, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.mTvApraiseName.setText(mList.get(groupPosition).getFromUserName() + "点评");
            viewHolder.mTvApraiseDate.setText(mList.get(groupPosition).getCreateTime());
            if (!mList.get(groupPosition).getFromUserHeadUrl().equals("")) {
                Picasso.with(getApplicationContext()).load(mList.get(groupPosition).getFromUserHeadUrl()).into(viewHolder.mImgHead);
            }
            viewHolder.mTvApraisseDetail.setText(mList.get(groupPosition).getContent());

           viewHolder.mTvReplay.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   popu(v,mList.get(groupPosition).getId()+"");
               }
           });
            return convertView;
        }

        @Override
        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ViewHolder1 viewHolder = null;
            if (convertView == null) {
                convertView = View.inflate(TeacherCommentsActivity.this, R.layout.item_replay_comments, null);
                viewHolder = new ViewHolder1(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder1) convertView.getTag();
            }

            viewHolder.mTvApraiseName.setText(mList.get(groupPosition).getReplys().get(childPosition).getFromUserName());
            viewHolder.mTvApraiseDate.setText(mList.get(groupPosition).getReplys().get(childPosition).getCreateTime());
            if (!mList.get(groupPosition).getReplys().get(childPosition).getFromUserHeadUrl().equals("")) {
                Picasso.with(getApplicationContext()).load(mList.get(groupPosition).getReplys().get(childPosition).getFromUserHeadUrl()).into(viewHolder.mImgHead);
            }
            viewHolder.mTvApraisseDetail.setText(mList.get(groupPosition).getReplys().get(childPosition).getContent());
            viewHolder.mTvTeacher.setText(mList.get(groupPosition).getFromUserName());
            if(!mList.get(groupPosition).getReplys().get(childPosition).getMycomment()){
                viewHolder.mDelete.setVisibility(View.INVISIBLE);
            }
            viewHolder.mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int comm =mList.get(groupPosition).getReplys().get(childPosition).getId();
                    deletecomm(comm);
                }
            });
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

    private void deletecomm(int comm) {
        Call<Deletecomm> call=HiRetorfit.getInstans().getApi().Deletecomm(mChannel,mToken,comm+"");
        call.enqueue(new Callback<Deletecomm>() {
            @Override
            public void onResponse(Call<Deletecomm> call, Response<Deletecomm> response) {
                if(response!=null){
                    if(response.body().getResult()==1){
                        TastyToast.makeText(getApplicationContext(),"删除成功",TastyToast.LENGTH_SHORT,TastyToast.SUCCESS);
                    }else{
                        TastyToast.makeText(getApplicationContext(),"删除失败",TastyToast.LENGTH_SHORT,TastyToast.ERROR);
                    }
                }
            }

            @Override
            public void onFailure(Call<Deletecomm> call, Throwable t) {
                TastyToast.makeText(getApplicationContext(),"网络错误",TastyToast.LENGTH_SHORT,TastyToast.ERROR);
            }
        });
    }

    static class ViewHolder1 {
        @InjectView(R.id.img_head)
        CircleImageView mImgHead;
        @InjectView(R.id.tv_apraise_name)
        TextView mTvApraiseName;
        @InjectView(R.id.tv_teacher)
        TextView mTvTeacher;
        @InjectView(R.id.tv_apraise_date)
        TextView mTvApraiseDate;
        @InjectView(R.id.tv_apraisse_detail)
        TextView mTvApraisseDetail;
        @InjectView(R.id.delete)
        TextView mDelete;

        ViewHolder1(View view) {
            ButterKnife.inject(this, view);
        }
    }


    static class ViewHolder {
        @InjectView(R.id.img_head)
        CircleImageView mImgHead;
        @InjectView(R.id.tv_apraise_name)
        TextView mTvApraiseName;
        @InjectView(R.id.tv_apraise_date)
        TextView mTvApraiseDate;
        @InjectView(R.id.tv_apraisse_detail)
        TextView mTvApraisseDetail;
        @InjectView(R.id.tv_replay)
        TextView mTvReplay;


        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
    private void popu(View view, final String commtId) {
        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(TeacherCommentsActivity.this).inflate(
                R.layout.popuwindow_revert_appraise, null);
        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);

        TextView tv = (TextView) contentView.findViewById(R.id.tv_revert_name);
        tv.setText("回复：");
        ImageButton btn = (ImageButton) contentView.findViewById(R.id.img_dismiss);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        final EditText etDetail = (EditText) contentView.findViewById(R.id.et_revert_appraise);
        Button revert = (Button) contentView.findViewById(R.id.btn_revert);
        revert.setText("立即回复");
        revert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(etDetail.getText())) {
                    sendComment(popupWindow, etDetail.getText().toString(),commtId);

                } else {
                    TastyToast.makeText(getApplicationContext(), "请填写评价内容", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                }
            }
        });
        ColorDrawable cd = new ColorDrawable(getResources().getColor(R.color.white));
        popupWindow.setBackgroundDrawable(cd);
        popupWindow.setTouchable(true); // 设置popupwindow可点击
        popupWindow.setOutsideTouchable(false); // 设置popupwindow外部可点击
        //popupWindow.setBackgroundDrawable();
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

    }
    private void sendComment(final PopupWindow popupWindow, String text,String commentId) {
        Call<ReViewBean> call = HiRetorfit.getInstans().getApi().ReView(mChannel, mToken, mCode, text,commentId);
        call.enqueue(new Callback<ReViewBean>() {
            @Override
            public void onResponse(Call<ReViewBean> call, Response<ReViewBean> response) {
                if (response != null) {
                    if (response.body().getResult() == 1) {
                        popupWindow.dismiss();
                        TastyToast.makeText(getApplicationContext(), "评论成功", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                    } else {
                        TastyToast.makeText(getApplicationContext(), "评论失败:" + response.body().getMessage(), TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                    }
                }
            }

            @Override
            public void onFailure(Call<ReViewBean> call, Throwable t) {
                TastyToast.makeText(getApplicationContext(), "评论失败：网络错误", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
            }
        });
    }
}
