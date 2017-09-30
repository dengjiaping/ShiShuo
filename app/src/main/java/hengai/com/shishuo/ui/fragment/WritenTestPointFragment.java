package hengai.com.shishuo.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import hengai.com.shishuo.R;

/**
 * Created by yu on 2017/9/26.
 */

public class WritenTestPointFragment extends Fragment {
    @InjectView(R.id.tv_know_num)
    TextView mTvKnowNum;
    @InjectView(R.id.tv_know_masternum)
    TextView mTvKnowMasternum;
    @InjectView(R.id.lv_writen_knowdege)
    ListView mLvWritenKnowdege;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = View.inflate(getContext(), R.layout.fragment_writen_testpoint, null);
        ButterKnife.inject(this, root);
        initData();
        initView();
        return root;
    }

    private void initView() {
     mLvWritenKnowdege.setAdapter(new MyWritenAdapter());
    }

    private void initData() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    class MyWritenAdapter extends BaseAdapter {
        final int TYPE_1 = 0;
        final int TYPE_2 = 1;

        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return TYPE_1;
            } else {
                return TYPE_2;
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
            ViewHolder1 holder1=null;
            ViewHolder2 holder2=null;
            if (convertView == null) {
                switch (getItemViewType(position)) {
                    case TYPE_1:

                        convertView = View.inflate(getContext(), R.layout.item_writen_knowlege, null);
                        holder1 = new ViewHolder1(convertView);
                        convertView.setTag(holder1);

                        break;
                    case TYPE_2:
                        convertView = View.inflate(getContext(), R.layout.item_writen_knowdetail, null);
                        holder2 = new ViewHolder2(convertView);
                        convertView.setTag(holder2);
                        break;
                }
            } else {
                switch (getItemViewType(position)) {
                    case TYPE_1:
                        holder1 = (ViewHolder1) convertView.getTag();
                        break;
                    case TYPE_2:
                        holder2 = (ViewHolder2) convertView.getTag();
                        break;
                }

            }

           showView();
            return convertView;
        }

        private void showView() {


        }


    }

    static class ViewHolder1 {
        @InjectView(R.id.ll_writen)
        LinearLayout mLlWriten;

        ViewHolder1(View view) {
            ButterKnife.inject(this, view);
        }
    }
    static class ViewHolder2 {
        @InjectView(R.id.tv_writen_detail)
        TextView mTvWritenDetail;

        ViewHolder2(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
