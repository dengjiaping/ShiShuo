package hengai.com.shishuo.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import hengai.com.shishuo.R;

/**
 * Created by yu on 2017/9/26.
 */

public class KnowledgeInterpretActivity extends AppCompatActivity {
    @InjectView(R.id.iv_return)
    ImageView mIvReturn;
    @InjectView(R.id.lv_writen_konwledge)
    ListView mLvWritenKonwledge;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledge_interpret);
        ButterKnife.inject(this);
        initData();
        initView();
    }

    private void initData() {


    }

    private void initView() {


    }

    @OnClick(R.id.iv_return)
    public void onClick() {
        finish();
    }


    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 4;
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

            if (convertView == null) {
                convertView= View.inflate(KnowledgeInterpretActivity.this, R.layout.item_writen_knowledge, null);
                holder=new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
             holder= (ViewHolder) convertView.getTag();
            }

            return convertView;
        }


    }
    static class ViewHolder {
        @InjectView(R.id.tv_knowledge)
        TextView mTvKnowledge;
        @InjectView(R.id.ll_bkg)
        LinearLayout mLlBkg;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
