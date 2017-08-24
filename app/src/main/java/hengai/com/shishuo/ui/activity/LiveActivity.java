package hengai.com.shishuo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import hengai.com.shishuo.R;
import hengai.com.shishuo.ui.adapter.FragmentVPAdapter;
import hengai.com.shishuo.ui.fragment.InterViewLiveFragement;
import hengai.com.shishuo.ui.fragment.WritenLiveFragment;

/**
 * Created by yu on 2017/8/17.
 */

public class LiveActivity extends AppCompatActivity {
    @InjectView(R.id.imgbtn_return)
    ImageButton mImgbtnReturn;
    @InjectView(R.id.rb_inter)
    Button mRbInter;
    @InjectView(R.id.rb_writen)
    Button mRbWriten;
    @InjectView(R.id.vp_live)
    ViewPager mVpLive;
    @InjectView(R.id.img_btn_mylive)
    ImageButton mImgBtnMylive;

    private ArrayList<Fragment> mFragments = new ArrayList<Fragment>();
    private InterViewLiveFragement mInterViewLiveFragement = null;
    private WritenLiveFragment mWritenLiveFragment = null;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);
        ButterKnife.inject(this);
        init();
    }

    private void init() {
        mInterViewLiveFragement=new InterViewLiveFragement();
        mWritenLiveFragment= new WritenLiveFragment();
        mFragments.add(mInterViewLiveFragement);
        mFragments.add(mWritenLiveFragment);

        mVpLive.setCurrentItem(0);
        mRbInter.setSelected(true);
        mVpLive.setAdapter(new FragmentVPAdapter(getSupportFragmentManager(),mFragments) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        });


        mVpLive.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    mVpLive.setCurrentItem(0);
                    mRbInter.setSelected(true);
                    mRbWriten.setSelected(false);
                } else if(position == 1) {
                    mVpLive.setCurrentItem(1);
                    mRbInter.setSelected(false);
                    mRbWriten.setSelected(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @OnClick({R.id.imgbtn_return, R.id.rb_inter, R.id.rb_writen, R.id.img_btn_mylive})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgbtn_return:
                //finish();
                startActivity(new Intent(LiveActivity.this,CourseDetailsActivity.class));
                break;
            case R.id.rb_inter:
                mVpLive.setCurrentItem(0);
                mRbInter.setSelected(true);
                mRbWriten.setSelected(false);
                break;
            case R.id.rb_writen:
                mVpLive.setCurrentItem(1);
                mRbInter.setSelected(false);
                mRbWriten.setSelected(true);
                break;
            case R.id.img_btn_mylive:
               startActivity(new Intent(LiveActivity.this,MyLiveActivity.class));
                //startActivity(new Intent(LiveActivity.this,CourseOneDetailsActivity.class));

                break;
        }
    }
}
