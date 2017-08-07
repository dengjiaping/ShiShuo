package hengai.com.shishuo.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import com.weiwangcn.betterspinner.library.BetterSpinner;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import hengai.com.shishuo.R;

/**
 * Created by yu on 2017/8/6.
 */

public class MyDreamActivity extends AppCompatActivity {

    @InjectView(R.id.rb_teacher_certify)
    RadioButton mRbTeacherCertify;
    @InjectView(R.id.rb_teacher_employ)
    RadioButton mRbTeacherEmploy;
    @InjectView(R.id.rb_select_exam)
    RadioButton mRbSelectExam;
    @InjectView(R.id.rg_item)
    RadioGroup mRgItem;
    @InjectView(R.id.bt_junior_sc)
    RadioButton mBtJuniorSc;
    @InjectView(R.id.bt_high_sc)
    RadioButton mBtHighSc;
    @InjectView(R.id.bt_primary_sc)
    RadioButton mBtPrimarySc;
    @InjectView(R.id.bt_kid_sc)
    RadioButton mBtKidSc;
    @InjectView(R.id.rg_level)
    RadioGroup mRgLevel;
    @InjectView(R.id.spinner1)
    BetterSpinner mSpinner1;
    @InjectView(R.id.btn_dream_next)
    Button mBtnDreamNext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mydream);
        ButterKnife.inject(this);

        String[] list = {"语文","数学","英语","物理","音乐","地理","政治","历史","美术","生物","信息","化学","体育"};
        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, list);*/
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, list);
        mSpinner1.setAdapter(adapter);


    }


    @OnClick({R.id.rb_teacher_certify, R.id.rb_teacher_employ, R.id.rb_select_exam, R.id.rg_item, R.id.bt_junior_sc, R.id.bt_high_sc, R.id.bt_primary_sc, R.id.bt_kid_sc, R.id.rg_level, R.id.spinner1, R.id.btn_dream_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_teacher_certify:
                break;
            case R.id.rb_teacher_employ:
                break;
            case R.id.rb_select_exam:
                break;
            case R.id.rg_item:
                break;
            case R.id.bt_junior_sc:
                break;
            case R.id.bt_high_sc:
                break;
            case R.id.bt_primary_sc:
                break;
            case R.id.bt_kid_sc:
                break;
            case R.id.rg_level:
                break;
            case R.id.spinner1:
                break;
            case R.id.btn_dream_next:
                break;
        }
    }
}
