package hengai.com.shishuo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

    private Fragment f1,f2,f3,f4;
    private Context mContext;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=this;
        /**
         * 拿到事务管理器并开启事务
         */
        manager=getSupportFragmentManager();
        transaction=manager.beginTransaction();

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {

            @Override
            public void onTabSelected(@IdRes int tabId) {
                manager=getSupportFragmentManager();
                transaction=manager.beginTransaction();

                switch (tabId) {
                    case R.id.tab_interview:
                        hideFragment(transaction);
                        f1 = new InterViewFragment();
                        transaction.replace(R.id.main_fragment,f1);
                        transaction.commit();
                        break;
                    case R.id.tab_written:
                        hideFragment(transaction);
                        f2 = new WrittenFragment();
                        transaction.replace(R.id.main_fragment,f2);
                        transaction.commit();
                        break;
                    case R.id.tab_find:
                        hideFragment(transaction);
                        f3 = new FindFragment();
                        transaction.replace(R.id.main_fragment,f3);
                        transaction.commit();
                        break;
                    case R.id.tab_mine:
                        hideFragment(transaction);
                        f4 = new MineFragment();
                        transaction.replace(R.id.main_fragment,f4);
                        transaction.commit();

                        break;
                }

                //getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment,ob).commit();
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
    /*
    * 去除（隐藏）所有的Fragment
    * */
    private void hideFragment( FragmentTransaction transaction) {
        if (f1 != null) {
            //transaction.hide(f1);隐藏方法也可以实现同样的效果，不过我一般使用去除
            transaction.remove(f1);
        }
        if (f2 != null) {
            //transaction.hide(f2);
            transaction.remove(f2);
        }
        if (f3 != null) {
            //transaction.hide(f3);
            transaction.remove(f3);
        }
        if(f4!=null){
            //transaction.hide(f4);
            transaction.remove(f4);
        }
    }

}
