package hengai.com.shishuo.ui.fragment;

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

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;
import hengai.com.shishuo.R;
import hengai.com.shishuo.utils.T;

/**
 * Created by yu on 2017/8/17.
 */

public class InterViewLiveFragement extends BaseFragment {

    @Override
    protected void startLoadData() {
        onDataLoadedSuccess();
    }

    @Override
    protected View onCreatContentView() {
        View root = View.inflate(getContext(), R.layout.fragment_interview_live, null);
        RefreshLayout refreshLayout = (RefreshLayout) root.findViewById(R.id.refreshLayout);
        ListView listView = (ListView) root.findViewById(R.id.lv_inter_live);

        listView.setAdapter(new MyAdapte());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                T.showShort(getContext(), position + "++++");
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(2000);
            }
        });

        return root;
    }


    private class MyAdapte extends BaseAdapter {

        @Override
        public int getCount() {
            return 12;
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
