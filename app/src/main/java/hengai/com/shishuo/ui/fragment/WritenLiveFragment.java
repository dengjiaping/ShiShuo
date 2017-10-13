package hengai.com.shishuo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sdsmdg.tastytoast.TastyToast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;
import hengai.com.shishuo.R;
import hengai.com.shishuo.bean.InterViewLiveBean;
import hengai.com.shishuo.bean.WrittenLiveBean;
import hengai.com.shishuo.network.HiRetorfit;
import hengai.com.shishuo.ui.activity.CourseDetailsActivity;
import hengai.com.shishuo.ui.activity.CourseOneDetailsActivity;
import hengai.com.shishuo.utils.DateUtil;
import hengai.com.shishuo.utils.SPUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yu on 2017/8/17.
 */

public class WritenLiveFragment extends Fragment {
    private List<WrittenLiveBean.DataBean> mList=new ArrayList<>();
    int page = 0;
    int paging = 10;
    int x = 1;
    private ListView mListView;
    private MyAdapter mMyAdapter;
    private View mRoot;
    private RefreshLayout mRefreshLayout;
    private String mChennel;
    private String mToken;
    private Intent mIntent1;
    private Intent mIntent2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRoot = View.inflate(getContext(), R.layout.fragment_writen_live, null);
        mRefreshLayout = (RefreshLayout) mRoot.findViewById(R.id.refreshLayout);
        mListView = (ListView) mRoot.findViewById(R.id.lv_writen_live);
        mChennel = (String) SPUtils.get(getContext(), "channel", "1");
        mToken = (String) SPUtils.get(getContext(), "token", "1");
        mMyAdapter = new MyAdapter();
        mIntent1 = new Intent(getContext(), CourseOneDetailsActivity.class);
        mIntent2 = new Intent(getContext(), CourseDetailsActivity.class);
        initData();

        return mRoot;


    }

    private boolean initData() {

        Call<WrittenLiveBean> call = HiRetorfit.getInstans().getApi().WrittenLive(mChennel, mToken, page, paging);
        call.enqueue(new Callback<WrittenLiveBean>() {
            @Override
            public void onResponse(Call<WrittenLiveBean> call, Response<WrittenLiveBean> response) {
                if (response.body().getResult() == 1) {
                    WrittenLiveBean writtenLiveBean = response.body();
                    mList.addAll(writtenLiveBean.getData());
                    //initView();
                    if (x == 1) {
                        x = 2;
                        initView();
                    } else {
                        mMyAdapter.notifyDataSetChanged();
                    }

                } else if (response.body().getResult() == -1) {
                    TastyToast.makeText(getContext(), "服务器错误", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                } else if (response.body().getResult() == 0) {
                    //TODO
                    TastyToast.makeText(getContext(), "token失效", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                }
            }

            @Override
            public void onFailure(Call<WrittenLiveBean> call, Throwable t) {
                TastyToast.makeText(getContext(), "获取数据失败请检查您的网络！", TastyToast.LENGTH_LONG, TastyToast.ERROR);

            }
        });
        if (mList.size() > 0) {
            return true;
        } else {
            return false;
        }

    }

    protected void initView() {

        mListView.setAdapter(mMyAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mList.get(position).isIsPackage()) {
                    mIntent2.putExtra("id", mList.get(position).getId() + "");
                    startActivity(mIntent2);
                } else {
                    String startDate = DateUtil.getDate(mList.get(position).getStartDate());
                    String startTime = DateUtil.getTime(mList.get(position).getStartTime());

                    mIntent1.putExtra("videoId", mList.get(position).getVideoId());


                    mIntent1.putExtra("num", mList.get(position).getPersonNum() + "");
                    //mIntent1.putExtra("crcode", mList.get(position).getCode());
                    mIntent1.putExtra("date", startDate);
                    mIntent1.putExtra("time", startTime);
                    mIntent1.putExtra("title",mList.get(position).getTitle());
                    mIntent1.putExtra("personNum", mList.get(position).getPersonNum()+"");
                    mIntent1.putExtra("url", mList.get(position).getCourseIntroduction().getIntroduceUrl());
                    startActivity(mIntent1);
                }

            }
        });
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page=0;
                mList.clear();
                refreshlayout.finishRefresh(initData());

                //initData();
            }
        });

        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page = page + 1;
                refreshlayout.finishLoadmore(initData());
            }
        });
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (mList != null) {
                return mList.size();
            } else {
                TastyToast.makeText(getContext(), "数据错误", TastyToast.LENGTH_LONG, TastyToast.ERROR);
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
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_listview_live, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.mTvLiveTitle.setText(mList.get(position).getTitle() + "");
            viewHolder.mTvTimeYear.setText(DateUtil.getDate(mList.get(position).getStartDate()));
            if (mList.get(position).isIsPackage()) {
                viewHolder.mTvLine.setText("—");
                viewHolder.mTvTimeHoues.setText(DateUtil.getDate(mList.get(position).getEndDate()));
            } else {
                viewHolder.mTvTimeHoues.setText(DateUtil.getTime(mList.get(position).getStartTime()) + "开始");
            }

            if (!mList.get(position).isIsRecommend()) {
                viewHolder.mIvLiveTj.setVisibility(View.GONE);
            }

            if (!mList.get(position).isEnrollmentStatus()) {
                viewHolder.mTvEnrol.setText("已报名");
                viewHolder.mTvEnrol.setTextColor(getResources().getColor(R.color.replay));
            }
            viewHolder.mTvPersornum.setText(mList.get(position).getPersonNum() + "");

            if (mList.get(position).getTeachers().size() == 1) {
                Picasso.with(getContext()).load(mList.get(position).getTeachers().get(0).getTeacherIcon()).into(viewHolder.mOneImage);
                viewHolder.mOneName.setText(mList.get(position).getTeachers().get(0).getTeacherName());
                viewHolder.mLlTechcer2.setVisibility(View.GONE);
            } else if (mList.get(position).getTeachers().size() == 2) {
                Picasso.with(getContext()).load(mList.get(position).getTeachers().get(0).getTeacherIcon()).into(viewHolder.mOneImage);
                viewHolder.mOneName.setText(mList.get(position).getTeachers().get(0).getTeacherName());
                Picasso.with(getContext()).load(mList.get(position).getTeachers().get(1).getTeacherIcon()).into(viewHolder.mTwoImage);
                viewHolder.mTwoName.setText(mList.get(position).getTeachers().get(1).getTeacherName());
            } else {
                viewHolder.mLlTechcer2.setVisibility(View.GONE);
                viewHolder.mLlTechcer1.setVisibility(View.GONE);
            }

            return convertView;
        }


    }

    static class ViewHolder {
        @InjectView(R.id.tv_live_title)
        TextView mTvLiveTitle;
        @InjectView(R.id.iv_live_tj)
        ImageView mIvLiveTj;
        @InjectView(R.id.tv_time_year)
        TextView mTvTimeYear;
        @InjectView(R.id.tv_line)
        TextView mTvLine;
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

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}
