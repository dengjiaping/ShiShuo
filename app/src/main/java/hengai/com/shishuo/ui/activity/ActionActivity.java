package hengai.com.shishuo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import hengai.com.shishuo.R;

/**
 * Created by yu on 2017/10/13.
 */

public class ActionActivity extends AppCompatActivity {


    @InjectView(R.id.lv_action_live)
    ListView mLvActionLive;
    List<String> mList=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);
        ButterKnife.inject(this);
        initData();
        initView();
    }

    private void initView() {
        mLvActionLive.setAdapter(new MyActionAdapter(ActionActivity.this,mList));
        mLvActionLive.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(ActionActivity.this,DetailsActivity.class));
            }
        });
    }

    private void initData() {
    }

    class MyActionAdapter extends BaseAdapter {
        Context mCtx;
        List<String> mList;

        public MyActionAdapter(Context con, List<String> list) {
            mCtx = con;
            mList = list;
        }

        @Override
        public int getCount() {
            return 8;
        }

        @Override
        public Object getItem(int position) {

            return position;
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
           ViewHolder holder=null;
            if(convertView==null){
                convertView = View.inflate(mCtx, R.layout.item_action, null);
                holder=new ViewHolder(convertView);
                convertView.setTag(holder);
            }else{

                holder= (ViewHolder) convertView.getTag();
            }


            return convertView;
        }


    }
    static class ViewHolder {
        @InjectView(R.id.iv_activity)
        ImageView mIvActivity;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
