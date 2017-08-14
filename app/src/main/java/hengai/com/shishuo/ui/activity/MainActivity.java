package hengai.com.shishuo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import hengai.com.shishuo.R;

import hengai.com.shishuo.bean.DataBean;
import hengai.com.shishuo.bean.SettingMsgBean;
import hengai.com.shishuo.bean.TabMessage;
import hengai.com.shishuo.network.HiRetorfit;
import hengai.com.shishuo.ui.fragment.FindFragment;
import hengai.com.shishuo.ui.fragment.InterViewFragment;
import hengai.com.shishuo.ui.fragment.MineFragment;
import hengai.com.shishuo.ui.fragment.WrittenFragment;
import hengai.com.shishuo.utils.LogUtils;
import hengai.com.shishuo.utils.T;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=this;

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                Fragment ob=null;
                switch (tabId) {
                    case R.id.tab_interview:
                        ob = new InterViewFragment();
                        break;
                    case R.id.tab_written:
                        ob = new WrittenFragment();
                        break;
                    case R.id.tab_find:
                        ob = new FindFragment();
                        break;
                    case R.id.tab_mine:
                        ob = new MineFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment,ob).commit();
            }
        });
      /*  bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
            //已经点击了这个标签，又点击一次即重选
                getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment,new InterViewFragment()).commit();
            }
        });*/
    }

}
