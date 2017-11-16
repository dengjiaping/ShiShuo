package hengai.com.shishuo.ui.activity.myactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import hengai.com.shishuo.R;

/**
 * Created by yu on 2017/11/16.
 */

public class MyNewsActivity extends AppCompatActivity {
    @InjectView(R.id.lv_news)
    ListView mLvNews;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.inject(this);
        initData();
        initView();
    }

    private void initView() {
     mLvNews.setAdapter(new MyAdapter());
        final Intent intent=new Intent(MyNewsActivity.this,MyNewsDetailActivity.class);
        mLvNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(intent);
            }
        });
    }

    private void initData() {

    }

    @OnClick(R.id.img_back)
    public void onClick() {
        finish();
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 8;
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
            if(holder==null){
                convertView = View.inflate(MyNewsActivity.this, R.layout.item_news, null);
                 holder=new ViewHolder(convertView);
                 convertView.setTag(holder);
            }else{
                holder= (ViewHolder) convertView.getTag();
            }

            return convertView;
        }


    }
    static class ViewHolder {
        @InjectView(R.id.tv_title)
        TextView mTvTitle;
        @InjectView(R.id.tv_time)
        TextView mTvTime;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
