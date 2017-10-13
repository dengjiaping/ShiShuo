package hengai.com.shishuo.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import hengai.com.shishuo.R;
import hengai.com.shishuo.utils.T;

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
        mLvWritenKonwledge.setAdapter(new MyAdapter(KnowledgeInterpretActivity.this));

    }

    @OnClick(R.id.iv_return)
    public void onClick() {
        finish();
    }


    class MyAdapter extends BaseAdapter {

        private Context mCtx;

        public MyAdapter(Context context) {
            mCtx = context;
        }

        @Override
        public int getCount() {
            return 4;
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
            ViewHolder holder = null;
            if (convertView == null) {

                convertView = View.inflate(KnowledgeInterpretActivity.this, R.layout.item_writen_knowledge, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (position == 3) {
           holder.mRlKnowBtn.setVisibility(View.VISIBLE);
                holder.mBtnKnow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        T.showShort(mCtx,"记住了");
                    }
                });
            }else {
                holder.mRlKnowBtn.setVisibility(View.GONE);
            }
            return convertView;
        }


    }

    static class ViewHolder {
        @InjectView(R.id.tv_knowledge)
        TextView mTvKnowledge;
        @InjectView(R.id.ll_bkg)
        LinearLayout mLlBkg;
        @InjectView(R.id.btn_know)
        Button mBtnKnow;
        @InjectView(R.id.rl_know_btn)
        RelativeLayout mRlKnowBtn;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}
