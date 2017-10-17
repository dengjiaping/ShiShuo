package hengai.com.shishuo.ui.fragment;

import android.content.Context;
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
import android.widget.ListView;
import android.widget.TextView;

import com.bokecc.sdk.mobile.live.util.LogUtil;
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
import hengai.com.shishuo.bean.InfoMationBean;
import hengai.com.shishuo.network.HiRetorfit;
import hengai.com.shishuo.ui.activity.InfomationDetailsActivity;
import hengai.com.shishuo.utils.SPUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yu on 2017/9/19.
 */

public class InfomationLFragment extends Fragment {

    private String mChennel;
    private String mToken;
    private View mRoot;
    private RefreshLayout mRefreshLayout;
    private ListView mLvInfomation;
    private List<InfoMationBean.DataBean> mList=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRoot = View.inflate(getContext(), R.layout.fragment_infomation_l, null);
        mRefreshLayout = (RefreshLayout) mRoot.findViewById(R.id.refreshLayout);
        mLvInfomation = (ListView) mRoot.findViewById(R.id.lv_inter_live);

        mChennel = (String) SPUtils.get(getContext(), "channel", "liangshishuo");
        mToken = (String) SPUtils.get(getContext(), "token", "1");
        initData();
        return mRoot;
    }

    private boolean initData() {
        //网络请求获取数据
        Call<InfoMationBean> call = HiRetorfit.getInstans().getApi().InfoMationList(mChennel, mToken, "BK");
        call.enqueue(new Callback<InfoMationBean>() {
            @Override
            public void onResponse(Call<InfoMationBean> call, Response<InfoMationBean> response) {
                if (response != null) {
                    if (response.code() == 200) {
                        mList = response.body().getData();
                        LogUtil.d("++++",mList.size()+"xxx");
                        initView();
                    } else {
                        TastyToast.makeText(getContext(), "数据错误", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                    }
                }
            }

            @Override
            public void onFailure(Call<InfoMationBean> call, Throwable t) {
                TastyToast.makeText(getContext(), "网络错误", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
            }
        });
        return true;
    }

    private void initView() {
        MyAdapter myAdapter = new MyAdapter(getContext(), mList);
        mLvInfomation.setAdapter(myAdapter);
        final Intent intent = new Intent(getContext(), InfomationDetailsActivity.class);

        mLvInfomation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent.putExtra("id", mList.get(position).getId() + "");
                startActivity(intent);
            }
        });

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

                refreshlayout.finishRefresh(initData());
                //
                //initData();
            }
        });

        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {

                refreshlayout.finishLoadmore(initData());
            }
        });
    }

    private class MyAdapter extends BaseAdapter {
        private Context mCtx;
        private List<InfoMationBean.DataBean> mList;

        public MyAdapter(Context ctx, List<InfoMationBean.DataBean> list) {
            mCtx = ctx;
            mList = list;
        }

        @Override
        public int getCount() {
            if (mList != null) {
                return mList.size();
            } else {
                return 0;
            }
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_infomation, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (!mList.get(position).getHeadImgUrl().equals("")) {
                holder.mIvInfomation.setVisibility(View.VISIBLE);
                Picasso.with(mCtx).load(mList.get(position).getHeadImgUrl()).into(holder.mIvInfomation);
            }

            holder.mTvTitle.setText(mList.get(position).getTitle());
            holder.mTvName.setText(mList.get(position).getCreator());
            String time=mList.get(position).getCreateTime().substring(0,mList.get(position).getCreateTime().indexOf(" "));
            holder.mTvTime.setText(time);
            holder.mTvPersonNum.setText(mList.get(position).getViewTime()+"");
            holder.mCircleView.setVisibility(View.INVISIBLE);
            if (mList.get(position).getIsRefered().equals("Y")) {

            }else{
                holder.mLine.setVisibility(View.GONE);
                holder.mIvTj.setVisibility(View.GONE);
            }

            return convertView;
        }

    }

    static class ViewHolder {
        @InjectView(R.id.line)
        TextView mLine;
        @InjectView(R.id.iv_tj)
        ImageView mIvTj;
        @InjectView(R.id.iv_infomation)
        ImageView mIvInfomation;
        @InjectView(R.id.tv_title)
        TextView mTvTitle;
        @InjectView(R.id.circle_view)
        CircleImageView mCircleView;
        @InjectView(R.id.tv_name)
        TextView mTvName;
        @InjectView(R.id.tv_time)
        TextView mTvTime;
        @InjectView(R.id.tv_person_num)
        TextView mTvPersonNum;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }


}
