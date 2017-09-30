package hengai.com.shishuo.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;
import hengai.com.shishuo.R;

/**
 * Created by yu on 2017/9/26.
 */

public class WritenMoldTestFragment extends Fragment {


    @InjectView(R.id.tv_top_one)
    TextView mTvTopOne;
    @InjectView(R.id.tv_top_two)
    TextView mTvTopTwo;
    @InjectView(R.id.tv_top_three)
    TextView mTvTopThree;
    @InjectView(R.id.lv_writen)
    ListView mLvWriten;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = View.inflate(getContext(), R.layout.fragment_writen_moldtest, null);
        ButterKnife.inject(this, root);
        initData();
        initView();
        return root;
    }

    private void initView() {
         mLvWriten.setAdapter(new MyAdapter());
    }

    private void initData() {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    class MyAdapter extends BaseAdapter {

        final int TYPE_1 = 0;
        final int TYPE_2 = 1;
        final int TYPE_3 = 2;
        final int TYPE_4 = 3;
        final int TYPE_5 = 4;
        @Override
        public int getCount() {
            return 10;
        }
        @Override
        public int getViewTypeCount() {
            return 5;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return TYPE_1;
            }else if(position>0&&position<5){
                return TYPE_2;
            }else if(position==5){
                return TYPE_3;
            }else if(position==6){
                return TYPE_4;
            }else{
                return TYPE_5;
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
            ViewHolder1 holder1 = null;
            ViewHolder2 holder2 = null;
            ViewHolder3 holder3 = null;
            ViewHolder4 holder4 = null;
            ViewHolder5 holder5 = null;
            if (convertView == null) {
                switch (getItemViewType(position)) {
                    case TYPE_1:
                        convertView = View.inflate(getContext(), R.layout.item_writen_one, null);
                        holder1 = new ViewHolder1(convertView);
                        convertView.setTag(holder1);
                        break;
                    case TYPE_2:
                        convertView = View.inflate(getContext(), R.layout.item_writen_test, null);
                        holder2 = new ViewHolder2(convertView);
                        convertView.setTag(holder2);
                        break;
                    case TYPE_3:
                        convertView = View.inflate(getContext(), R.layout.item_writen_two, null);
                        holder3 = new ViewHolder3(convertView);
                        convertView.setTag(holder3);
                        break;
                    case TYPE_4:
                        convertView = View.inflate(getContext(), R.layout.item_writen_three, null);
                        holder4= new ViewHolder4(convertView);
                        convertView.setTag(holder4);
                        break;
                    case TYPE_5:
                        convertView = View.inflate(getContext(), R.layout.item_writen_live, null);
                        holder5 = new ViewHolder5(convertView);
                        convertView.setTag(holder5);
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
                    case TYPE_3:
                        holder3 = (ViewHolder3) convertView.getTag();
                        break;
                    case TYPE_4:
                        holder4 = (ViewHolder4) convertView.getTag();
                        break;
                    case TYPE_5:
                        holder5 = (ViewHolder5) convertView.getTag();
                        break;
                }
            }
            switch (getItemViewType(position)) {
                case TYPE_1:
holder1.mLlAddrass.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        showPopu();
    }
    private void showPopu() {

    }
});
                    break;
                case TYPE_2:

                    break;
                case TYPE_3:

                    break;
                case TYPE_4:

                    break;
                case TYPE_5:
                   holder5.mIvLiveTj.setVisibility(View.GONE);
                    break;
            }
            return convertView;
        }


    }

    static class ViewHolder1 {
        @InjectView(R.id.tv_addrass)
        TextView mTvAddrass;
        @InjectView(R.id.ll_addrass)
        LinearLayout mLlAddrass;

        ViewHolder1(View view) {
            ButterKnife.inject(this, view);
        }
    }

    static class ViewHolder2 {
        @InjectView(R.id.iv_icon_boolen)
        ImageView mIvIconBoolen;
        @InjectView(R.id.tv_writen_title)
        TextView mTvWritenTitle;
        @InjectView(R.id.tv_writen_testnum)
        TextView mTvWritenTestnum;
        @InjectView(R.id.tv_writen_num)
        TextView mTvWritenNum;
        @InjectView(R.id.tv_writen_true)
        TextView mTvWritenTrue;

        ViewHolder2(View view) {
            ButterKnife.inject(this, view);
        }
    }

    static class ViewHolder3 {
        @InjectView(R.id.rl_writen_two)
        RelativeLayout mRlWritenTwo;

        ViewHolder3(View view) {
            ButterKnife.inject(this, view);
        }
    }

    static class ViewHolder4 {
        @InjectView(R.id.rl_wrinten_three)
        RelativeLayout mRlWrintenThree;

        ViewHolder4(View view) {
            ButterKnife.inject(this, view);
        }
    }


    static class ViewHolder5 {
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

        ViewHolder5(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
