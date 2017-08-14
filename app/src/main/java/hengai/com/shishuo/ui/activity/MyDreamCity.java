package hengai.com.shishuo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import hengai.com.shishuo.R;
import hengai.com.shishuo.bean.RegisterBean;
import hengai.com.shishuo.bean.SerializableMap;
import hengai.com.shishuo.network.HiRetorfit;
import hengai.com.shishuo.utils.LogUtils;
import hengai.com.shishuo.utils.SPUtils;
import hengai.com.shishuo.utils.T;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 我的梦想配置第二页
 * Created by yu on 2017/8/7.
 */

public class MyDreamCity extends AppCompatActivity {


    @InjectView(R.id.rb_select_hunan)
    RadioButton mRbSelectHunan;
    @InjectView(R.id.rb_select_guangdong)
    RadioButton mRbSelectGuangdong;
    @InjectView(R.id.rb_select_guangxi)
    RadioButton mRbSelectGuangxi;
    @InjectView(R.id.rb_select_guizhou)
    RadioButton mRbSelectGuizhou;
    @InjectView(R.id.rg_item)
    RadioGroup mRgItem;
    @InjectView(R.id.spinner_dream_city)
    BetterSpinner mSpinnerDreamCity;
    @InjectView(R.id.btn_dream_next)
    Button mBtnDreamNext;

    private String mExam;
    private String mSchool;
    private String mSubject;
    private String mScatgid;
    private String mCatgid;
    private Context mContext;
    private String mToken;

    private String mCityId="1";
    private Map<Integer, String> mMap1;
    private Map<Integer, String> mMap2;
    private String mCity="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mydream2);
        ButterKnife.inject(this);
        mContext=this;
        initView();
        initData();

    }

    private void initView() {
       mSpinnerDreamCity.addTextChangedListener(watcher);
    }
  TextWatcher watcher=new TextWatcher() {
      private CharSequence temp;
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        temp=s;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
     if(temp.toString()!=null&temp.toString().equals("")){
         mRbSelectHunan.setChecked(false);
         mRbSelectGuangdong.setChecked(false);
         mRbSelectGuangxi.setChecked(false);
         mRbSelectGuizhou.setChecked(false);
     }
    }
};
    private void initData() {
        mToken = (String) SPUtils.get(mContext,"token","1");

        mExam=getIntent().getStringExtra("exam");
        mSchool=getIntent().getStringExtra("school");
        mSubject=getIntent().getStringExtra("subject");
        mCatgid=getIntent().getStringExtra("catagId");
        mScatgid=getIntent().getStringExtra("scatgId");

        Bundle bundle=getIntent().getExtras();
        SerializableMap serializableMap= (SerializableMap) bundle.get("map1");
        SerializableMap serializableMap2= (SerializableMap) bundle.get("map2");
        mMap1=serializableMap.getMap();
        mMap2=serializableMap2.getMap();
        LogUtils.d(mExam+"++"+mSchool+"++"+mSubject+"++"+mCatgid+"+++"+mScatgid);
        LogUtils.d(serializableMap.getMap().toString()+"+++++已开通");
        LogUtils.d(serializableMap2.getMap().toString()+"+++++未开通");
        Set set=mMap2.keySet();
        List<String> list=new ArrayList<>();
        for(Object b:set){
            list.add(serializableMap2.getMap().get(b));
        }
        String[] listCity =new String[list.size()];
        for(int i=0;i<list.size();i++){
            listCity[i]=list.get(i);
        }

                //String[] listCity = {"北京","天津","上海","重庆","黑龙江","辽宁","吉林","河北","河南","湖北","山东","山西","安徽","浙江","江苏","福建","海南","四川","云南","青海","甘肃","江西"};
        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, list);*/
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, listCity);
        mSpinnerDreamCity.setAdapter(adapter);

    }
    /**
     * @param map
     * @param value
     * @return  Integer类型的 value对应的Key值
     */
    private Integer getIntKey(Map<Integer, String> map, String value) {
        Integer key = null;
        for (Integer getKey : map.keySet()) {
            if (map.get(getKey).equals(value)) {
                key = getKey;
            }
        }
        return key;
    }

    @OnClick({R.id.rb_select_hunan, R.id.rb_select_guangdong, R.id.rb_select_guangxi, R.id.rb_select_guizhou, R.id.rg_item, R.id.spinner_dream_city, R.id.btn_dream_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_select_hunan:
                mCityId=1+"";
                LogUtils.d(mSpinnerDreamCity.getText().toString());
                if(mSpinnerDreamCity.getText()!=null&!mSpinnerDreamCity.getText().toString().equals("")){
                    mSpinnerDreamCity.setText("");
                }
                break;
            case R.id.rb_select_guangdong:
                mCityId=2+"";
                if(mSpinnerDreamCity.getText()!=null&!mSpinnerDreamCity.getText().toString().equals("")){
                    mSpinnerDreamCity.setText("");
                }

                break;
            case R.id.rb_select_guangxi:
                mCityId=0+"";
                if(mSpinnerDreamCity.getText()!=null&!mSpinnerDreamCity.getText().toString().equals("")){
                    mSpinnerDreamCity.setText("");
                }

                break;
            case R.id.rb_select_guizhou:
                mCityId=3+"";
                if(mSpinnerDreamCity.getText()!=null&!mSpinnerDreamCity.getText().toString().equals("")){
                    mSpinnerDreamCity.setText("");
                }
                break;
            case R.id.rg_item:
                break;
            case R.id.spinner_dream_city:
                break;
            case R.id.btn_dream_next:
                commitSet();
                break;
        }
    }
    private void commitSet(){
        mCity=mSpinnerDreamCity.getText().toString();
        if(mCity!=null&!mCity.equals("")){
            mCityId=getIntKey(mMap2,mSpinnerDreamCity.getText().toString())+"";
        }
        if(!mCityId.equals("0")){
            LogUtils.d("++++最后"+mScatgid+"++"+mCatgid+"+++"+mCityId+"+++"+mToken);
            Call<RegisterBean> call= HiRetorfit.getInstans().getApi().UpSetting("liangshishuo",mCityId,mCatgid,mScatgid,mToken);
            call.enqueue(new Callback<RegisterBean>() {
                @Override
                public void onResponse(Call<RegisterBean> call, Response<RegisterBean> response) {
                    if(response!=null){
                        if(response.body().getResult()==1){
                            T.showShort(mContext,"设置成功");
                            startActivity(new Intent(MyDreamCity.this,MainActivity.class));
                            //TODO

                        }else{
                            T.showShort(mContext,"设置失败");
                        }
                    }else{
                        T.showShort(mContext,"网络错误");
                    }
                }

                @Override
                public void onFailure(Call<RegisterBean> call, Throwable t) {

                }
            });

        }else{
            T.showShort(mContext,"请选择省份");
        }

    }
}
