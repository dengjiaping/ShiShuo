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
import android.widget.ListView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;
import hengai.com.shishuo.R;
import hengai.com.shishuo.ui.activity.InfomationDetailsActivity;
import hengai.com.shishuo.utils.SPUtils;

/**
 * Created by yu on 2017/9/19.
 */

public class InfomationLFragment extends Fragment {

    private String mChennel;
    private String mToken;
    private View mRoot;
    private RefreshLayout mRefreshLayout;
    private ListView mLvInfomation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRoot = View.inflate(getContext(), R.layout.fragment_infomation_l, null);
        mRefreshLayout = (RefreshLayout) mRoot.findViewById(R.id.refreshLayout);
        mLvInfomation = (ListView) mRoot.findViewById(R.id.lv_inter_live);

        mChennel = (String) SPUtils.get(getContext(), "channel", "1");
        mToken = (String) SPUtils.get(getContext(), "token", "1");
        initData();
        return mRoot;
    }

    private void initData() {
        //TODO
        //网络请求获取数据

        initView();

    }

    private void initView() {
        MyAdapter myAdapter = new MyAdapter();
        mLvInfomation.setAdapter(myAdapter);
        final Intent intent = new Intent(getContext(), InfomationDetailsActivity.class);

        mLvInfomation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(intent);
            }
        });

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

                refreshlayout.finishRefresh(2000);
                //
                //initData();
            }
        });

        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {

                refreshlayout.finishLoadmore(2000);
            }
        });
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 10;
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
            ViewHolder holder=null;

            if(convertView==null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_infomation, null);
                 holder=new ViewHolder(convertView);
                convertView.setTag(holder);

            }else{
                holder= (ViewHolder) convertView.getTag();
            }

            if (position % 2 == 0) {
                holder.mLine.setVisibility(View.GONE);
                holder.mIvTj.setVisibility(View.GONE);
                holder.mIvInfomation.setVisibility(View.VISIBLE);

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
