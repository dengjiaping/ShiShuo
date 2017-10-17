package hengai.com.shishuo.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import hengai.com.shishuo.R;
import hengai.com.shishuo.bean.InfoMationBean;
import hengai.com.shishuo.network.HiRetorfit;
import hengai.com.shishuo.ui.adapter.FragmentVPAdapter;
import hengai.com.shishuo.ui.fragment.InfomationLFragment;
import hengai.com.shishuo.ui.fragment.InfomationRFragment;
import hengai.com.shishuo.utils.SPUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * 资讯页面
 * Created by yu on 2017/9/19.
 */

public class InfoMationActivity extends AppCompatActivity {
    @InjectView(R.id.imgbtn_return)
    ImageButton mImgbtnReturn;
    @InjectView(R.id.rb_inter)
    Button mRbInter;
    @InjectView(R.id.rb_writen)
    Button mRbWriten;
    @InjectView(R.id.vp_infomation)
    ViewPager mVpInfomation;
    private ArrayList<Fragment> mFragments = new ArrayList<Fragment>();
    private InfomationLFragment mInfomationL=null;
    private InfomationRFragment mInfomationR=null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infomation);
        ButterKnife.inject(this);

       initView();

    }



    private void initView() {
        mInfomationL = new InfomationLFragment();
        mInfomationR = new InfomationRFragment();
        mFragments.add(mInfomationL);
        mFragments.add(mInfomationR);


        mVpInfomation.setCurrentItem(0);
        mRbInter.setSelected(true);
        mVpInfomation.setAdapter(new FragmentVPAdapter(getSupportFragmentManager(),mFragments) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        });


        mVpInfomation.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {

                    mVpInfomation.setCurrentItem(0);
                    mRbInter.setSelected(true);
                    mRbWriten.setSelected(false);

                } else if(position == 1) {

                    mVpInfomation.setCurrentItem(1);
                    mRbInter.setSelected(false);
                    mRbWriten.setSelected(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @OnClick({R.id.imgbtn_return, R.id.rb_inter, R.id.rb_writen})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgbtn_return:
                finish();
                break;
            case R.id.rb_inter:

                mVpInfomation.setCurrentItem(0);
                mRbInter.setSelected(true);
                mRbWriten.setSelected(false);
                break;
            case R.id.rb_writen:

                mVpInfomation.setCurrentItem(1);
                mRbInter.setSelected(false);
                mRbWriten.setSelected(true);
                break;
        }
    }
}
