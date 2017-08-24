package hengai.com.shishuo.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.sdsmdg.tastytoast.TastyToast;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import hengai.com.shishuo.R;
import hengai.com.shishuo.bean.MyLiveBean;
import hengai.com.shishuo.ui.adapter.NoBeginAdapter;
import hengai.com.shishuo.network.HiRetorfit;
import hengai.com.shishuo.ui.adapter.PlayBackAdapter;
import hengai.com.shishuo.ui.adapter.TodayLiveAdapter;
import hengai.com.shishuo.utils.SPUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yu on 2017/8/18.
 */

public class MyLiveActivity extends AppCompatActivity {
    @InjectView(R.id.imgbtn_return)
    ImageButton mImgbtnReturn;
    @InjectView(R.id.ll_todayLive)
    LinearLayout mLlTodayLive;
    @InjectView(R.id.ll_noStrat)
    LinearLayout mLlNoStrat;
    @InjectView(R.id.ll_replay)
    LinearLayout mLlReplay;
    @InjectView(R.id.lv_mylive)
    ListView mLvMylive;
    private Context mContext;
    private MyLiveBean.DataBean.TodayLiveBean mTodayLive;
    private List<MyLiveBean.DataBean.NotBeginBean> mNotBeginList;
    private List<MyLiveBean.DataBean.PlayBackBean> mPlayBackList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylive);
        ButterKnife.inject(this);
        mContext=this;
        String channel = (String) SPUtils.get(this, "channel", "1");
        String token = (String) SPUtils.get(this, "token", "1");
        initData(channel,token);
    }

    private void initData(String channel,String token) {

        Call<MyLiveBean> call= HiRetorfit.getInstans().getApi().MyLive(channel,token);
        call.enqueue(new Callback<MyLiveBean>() {
            @Override
            public void onResponse(Call<MyLiveBean> call, Response<MyLiveBean> response) {
                if(response !=null){
                    if(response.body().getResult()==1){
                        mTodayLive = response.body().getData().getTodayLive();
                        mNotBeginList = response.body().getData().getNotBegin();
                        mPlayBackList = response.body().getData().getPlayBack();
                        mLlTodayLive.setSelected(true);
                        mLvMylive.setAdapter(new TodayLiveAdapter(mTodayLive,mContext));
                    }else if(response.body().getResult()==-1){

                    }
                }else if(response.body().getResult()==0){

                }
            }

            @Override
            public void onFailure(Call<MyLiveBean> call, Throwable t) {
                TastyToast.makeText(mContext, "获取数据失败请检查网络设置！", TastyToast.LENGTH_LONG, TastyToast.ERROR);
            }
        });

    }

    @OnClick({R.id.imgbtn_return, R.id.ll_todayLive, R.id.ll_noStrat, R.id.ll_replay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgbtn_return:
                finish();
                break;
            case R.id.ll_todayLive:
                mLlTodayLive.setSelected(true);
                mLlReplay.setSelected(false);
                mLlNoStrat.setSelected(false);
                if(mTodayLive==null){
                    TastyToast.makeText(mContext, "获取数据失败！", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                }else{
                    TodayLiveAdapter todayLiveAdapter=new TodayLiveAdapter(mTodayLive,mContext);
                    mLvMylive.setAdapter(todayLiveAdapter);
                }
                break;
            case R.id.ll_noStrat:
                mLlTodayLive.setSelected(false);
                mLlReplay.setSelected(false);
                mLlNoStrat.setSelected(true);
                if(mNotBeginList==null){
                    TastyToast.makeText(mContext, "获取数据失败！", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                }else{
                    NoBeginAdapter noBeginAdapter=new NoBeginAdapter(mNotBeginList,mContext);
                    mLvMylive.setAdapter(noBeginAdapter);
                }

                break;
            case R.id.ll_replay:
                mLlTodayLive.setSelected(false);
                mLlReplay.setSelected(true);
                mLlNoStrat.setSelected(false);
                if(mPlayBackList==null){
                    TastyToast.makeText(mContext, "获取数据失败！", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                }else{
                    PlayBackAdapter playBackAdapter=new PlayBackAdapter(mPlayBackList,mContext);
                    mLvMylive.setAdapter(playBackAdapter);
                }
                break;
        }
    }
}
