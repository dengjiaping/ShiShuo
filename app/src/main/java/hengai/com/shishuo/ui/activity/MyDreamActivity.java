package hengai.com.shishuo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import com.sdsmdg.tastytoast.TastyToast;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import hengai.com.shishuo.R;
import hengai.com.shishuo.bean.DataBean;
import hengai.com.shishuo.bean.SerializableMap;
import hengai.com.shishuo.bean.SettingMsgBean;
import hengai.com.shishuo.network.HiRetorfit;
import hengai.com.shishuo.utils.LogUtils;
import hengai.com.shishuo.utils.SPUtils;
import hengai.com.shishuo.utils.T;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 我的梦想第一页
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
    //初中  ---高中---小学----幼儿
    private String[] highSchoollist = {"语文", "数学", "英语", "物理", "音乐", "地理", "政治", "历史", "美术", "生物", "信息", "化学", "体育"};
    private String[] middleSchoollist = {"语文", "数学", "英语", "物理", "音乐", "地理", "政治", "历史", "美术", "生物", "信息", "化学", "体育"};
    private String[] primarySchoollist = {"语文", "数学", "英语", "音乐", "品德", "美术", "科学", "体育"};
    private String[] nurserySchoollist = {"综合"};
    private String[] Schoollist = {"综合"};
    private String mExam = "1";
    private String mSchool = "1";
    private String mSubject = "1";
    //所有数据
    private DataBean mDataBean;
    private int catgID;
    private int scatgID;

    private String mToken;
    private Context mContext;
    private Map<Integer, String> mMap1;
    private Map<Integer, String> mMap2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mydream);
        ButterKnife.inject(this);
        mContext=this;
        mToken= (String) SPUtils.get(mContext,"token","1");
        LogUtils.d("++++"+mToken);
        initData();


        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, list);*/
        String[] list={"请选择你的教学目标"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item,list);
        mSpinner1.setAdapter(adapter);
        mSpinner1.setVisibility(View.INVISIBLE);
        mBtJuniorSc.setEnabled(false);
        mBtKidSc.setEnabled(false);
        mBtPrimarySc.setEnabled(false);
        mBtHighSc.setEnabled(false);

    }

    private void initView() {
        Map<Integer,String> catgMap=getCatgs();
        mRbTeacherCertify.setText(catgMap.get(1588));
        mRbTeacherEmploy.setText(catgMap.get(1524));
        mRbSelectExam.setText(catgMap.get(1592));

        Map<String,String> leveMap=getLevels();
        List<RadioButton> list1=new ArrayList<>();
        List<String> list=new ArrayList<>();
        list1.add(mBtJuniorSc);
        list1.add(mBtHighSc);
        list1.add(mBtPrimarySc);
        list1.add(mBtKidSc);
        Set set = leveMap.keySet();
        for (Object key : set) {
            list.add(leveMap.get(key));
        }
        for(int i=0;i<list.size();i++){
            list1.get(i).setText(list.get(i));
        }

    }

    /**
     * 将按钮设置为可点击
     */
    private void setTrue(){
        mBtJuniorSc.setEnabled(true);
        mBtKidSc.setEnabled(true);
        mBtPrimarySc.setEnabled(true);
        mBtHighSc.setEnabled(true);
    }

     private String[] getArry(int catgID,String leve){

         List<String> list= new ArrayList<>();
        if(catgID==1524){
            Set set = getScatags1524(leve).keySet();
            for (Object key : set) {
                list.add(getScatags1524(leve).get(key));
            }
        }
         if(catgID==1588){
             Set set = getScatags1588(leve).keySet();
             for (Object key : set) {
                 list.add(getScatags1588(leve).get(key));
             }
         }
         if(catgID==1592){
             Set set = getScatags1591(leve).keySet();
             for (Object key : set) {
                 list.add(getScatags1591(leve).get(key));
             }
         }
         String[] st=new String[list.size()];
         for(int i=0;i<list.size();i++){
            st[i]=list.get(i);
         }
         return st;
    }

    /**
     * 获取所有的设置数据
     */
    private void initData() {
        if(mToken.equals("1")){
            TastyToast.makeText(MyDreamActivity.this,"登录过期请重新登录",TastyToast.LENGTH_SHORT,TastyToast.ERROR);
            startActivity(new Intent(mContext,LoginActivity.class));
            finish();
        }
        Call<SettingMsgBean> call = HiRetorfit.getInstans().getApi().Setting("liangshishuo",mToken);
        call.enqueue(new Callback<SettingMsgBean>() {
            @Override
            public void onResponse(Call<SettingMsgBean> call, Response<SettingMsgBean> response) {
                if(response!=null){
                    SettingMsgBean settingMsgBean=response.body();
                    if(settingMsgBean.getResult()==1){
                        mDataBean = response.body().getData();
                        getBranchs();
                        /*LogUtils.d(mDataBean.getScatgs1524().get(0).getName()+"++++");
                        LogUtils.d(mDataBean.getScatgs1588().get(0).getName()+"++++1");
                        LogUtils.d(getScatags1588("KD").toString()+"++++");
                        LogUtils.d(getScatags1524("KD").toString()+"++++");
                        LogUtils.d(getScatags1591("KD").toString()+"++++");*/

                        initView();
                    }
                }
            }

            @Override
            public void onFailure(Call<SettingMsgBean> call, Throwable t) {
                TastyToast.makeText(MyDreamActivity.this,"获取数据失败请检查网络",TastyToast.LENGTH_SHORT,TastyToast.ERROR);
            }
        });
    }

    private Map<Integer,String> getScatags1524(String leve) {
      Map<Integer,String> map1524=new HashMap<>();
        List<DataBean.CatgsBean> list=mDataBean.getScatgs1524();
        for(int i=0;i<list.size();i++){
            LogUtils.d(list.get(i).getName()+"1524++++"+list.get(i).getLevel()+list.get(i).getId());
            if(list.get(i).getLevel().equals(leve)){
                list.get(i).getName();
                map1524.put(list.get(i).getId(),list.get(i).getName());
            }
        }
        return map1524;
    }
    private Map<Integer,String> getScatags1588(String leve) {
        Map<Integer,String> map1588=new HashMap<>();
        List<DataBean.CatgsBean> list=mDataBean.getScatgs1588();

        for(int i=0;i<list.size();i++){
            LogUtils.d(list.get(i).getName()+"1588++++"+list.get(i).getLevel()+list.get(i).getId());
            if(list.get(i).getLevel().equals(leve)){

                map1588.put(list.get(i).getId(),list.get(i).getName());
            }
        }
        return map1588;
    }
    private Map<Integer,String> getScatags1591(String leve) {
        Map<Integer,String> map1591=new HashMap<>();
        List<DataBean.CatgsBean> list=mDataBean.getScatgs1591();
        for(int i=0;i<list.size();i++){
            LogUtils.d(list.get(i).getName()+"1591++++"+list.get(i).getLevel()+list.get(i).getId());
            if(list.get(i).getLevel().equals(leve)){
                map1591.put(list.get(i).getId(),list.get(i).getName());
            }
        }
        return map1591;
    }

    /**
     * @return 学生学段的Map<String,String>集合 key是学段标识 MID value 初中
     */
    private Map<String,String> getLevels() {
        Map<String,String> levemap=new HashMap<>();
        List<DataBean.LevelsBean> list= mDataBean.getLevels();
        for(int i=0;i<list.size();i++){
            levemap.put(list.get(i).getCode(),list.get(i).getName());
        }
        return levemap;
    }

    /**
     * @return 考试的分类的Map<String,String>集合 key是ID 1524  value 教师招聘考试
     */
    private Map<Integer,String> getCatgs() {
        Map<Integer,String> catgmap=new HashMap<>();
        List<DataBean.CatgsBean> list= mDataBean.getCatgs();
        for(int i=0;i<list.size();i++){
            catgmap.put(list.get(i).getId(),list.get(i).getName());
        }
        return catgmap;
    }

    private void getBranchs() {
        //已开分校的地区
        mMap1 = new HashMap<>();
        //未开分校的地区
        mMap2 = new HashMap<>();
        List<DataBean.BranchsBean> branchslist=mDataBean.getBranchs();
        for(int i=0;i<branchslist.size();i++){
            if(branchslist.get(i).getStatus().equals("Y")){
                //TODO
                //还不明确是哪个ID
                mMap1.put(branchslist.get(i).getId(),branchslist.get(i).getPrvnName());
            }else{
                mMap2.put(branchslist.get(i).getId(),branchslist.get(i).getPrvnName());
            }

        }
        //LogUtils.d(mMap1.toString()+"++++以开通"+mMap2.toString());
    }

    private void adp(String[] i) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, i);
        mSpinner1.setAdapter(adapter);
    }

    /**
     * @param map
     * @param value
     * @return  String类型的 value对应的Key值
     */
    private String getStringKey(Map<String, String> map, String value) {
        String key = null;
        for (String getKey : map.keySet()) {
            if (map.get(getKey).equals(value)) {
                key = getKey;
            }
        }
        return key;
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
    int x=1;
    @OnClick({R.id.rb_teacher_certify, R.id.rb_teacher_employ, R.id.rb_select_exam, R.id.rg_item, R.id.bt_junior_sc, R.id.bt_high_sc, R.id.bt_primary_sc, R.id.bt_kid_sc, R.id.rg_level, R.id.spinner1, R.id.btn_dream_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_teacher_certify:
                mExam= mRbTeacherCertify.getText().toString();
                if(x==1){
                    setTrue();
                    x++;
                }
                break;
            case R.id.rb_teacher_employ:
                mExam = mRbTeacherEmploy.getText().toString();
                if(x==1){
                    setTrue();
                    x++;
                }
                break;
            case R.id.rb_select_exam:
                mExam = mRbSelectExam.getText().toString();
                if(x==1){
                    setTrue();
                    x++;
                }
                break;
            case R.id.rg_item:
                break;
            case R.id.bt_junior_sc:
                mSchool = mBtJuniorSc.getText().toString();
                if(!mExam.equals("1")){
                    String x =getStringKey(getLevels(),mSchool);
                    int catagId=getIntKey(getCatgs(),mExam);
                    String[] s1=getArry(catagId,x);
                    adp(s1);
                    mSpinner1.setVisibility(View.VISIBLE);
                }else{
                    TastyToast.makeText(MyDreamActivity.this,"请选择考试类型",TastyToast.LENGTH_SHORT,TastyToast.INFO);

                }
                break;
            case R.id.bt_high_sc:
                mSchool = mBtHighSc.getText().toString();
                if(!mExam.equals("1")){
                    String x =getStringKey(getLevels(),mSchool);
                    int catagId=getIntKey(getCatgs(),mExam);
                    String[] s1=getArry(catagId,x);
                    adp(s1);
                    mSpinner1.setVisibility(View.VISIBLE);
                }else{
                    TastyToast.makeText(MyDreamActivity.this,"请选择考试类型",TastyToast.LENGTH_SHORT,TastyToast.WARNING);

                }
                break;
            case R.id.bt_primary_sc:
                mSchool =mBtPrimarySc.getText().toString();
                if(!mExam.equals("1")){
                    String x =getStringKey(getLevels(),mSchool);
                    int catagId=getIntKey(getCatgs(),mExam);
                    String[] s1=getArry(catagId,x);
                    adp(s1);
                    mSpinner1.setVisibility(View.VISIBLE);
                }else{
                    TastyToast.makeText(MyDreamActivity.this,"请选择考试类型",TastyToast.LENGTH_SHORT,TastyToast.WARNING);

                }
                break;
            case R.id.bt_kid_sc:
                mSchool = mBtKidSc.getText().toString();
                if(!mExam.equals("1")){
                    String x =getStringKey(getLevels(),mSchool);
                    int catagId=getIntKey(getCatgs(),mExam);
                    String[] s1=getArry(catagId,x);
                    adp(s1);
                    mSpinner1.setVisibility(View.VISIBLE);
                }else{
                    TastyToast.makeText(MyDreamActivity.this,"请选择考试类型",TastyToast.LENGTH_SHORT,TastyToast.WARNING);
                }
                break;
            case R.id.rg_level:
                break;
            case R.id.spinner1:
                if(!mExam.equals("1")&!mSchool.equals("1")){
                    String x =getStringKey(getLevels(),mSchool);
                    int catagId=getIntKey(getCatgs(),mExam);
                    String[] s1=getArry(catagId,x);
                    adp(s1);
                    mSpinner1.setVisibility(View.VISIBLE);
                }else{
                    TastyToast.makeText(MyDreamActivity.this,"请选择考试类型和教学目标",TastyToast.LENGTH_SHORT,TastyToast.WARNING);

                }
                //mSubject = mSpinner1.getText().toString();
                break;
            case R.id.btn_dream_next:
                int scatgId=0;
                mSubject = mSpinner1.getText().toString();

                if (!mExam.equals("1") &!mSchool.equals("1") &!mSubject.equals("1")&!mSubject.equals("")) {
                    Intent intent = new Intent(MyDreamActivity.this, MyDreamCity.class);
                    intent.putExtra("exam", mExam);
                    intent.putExtra("school", mSchool);
                    intent.putExtra("subject", mSubject);
                    int catagId=getIntKey(getCatgs(),mExam);
                    Map<Integer,String> map=new HashMap<>();
                    LogUtils.d(getStringKey(getLevels(),mSchool)+"+++StringKEY");
                    if(catagId==1524){
                        scatgId=getIntKey(getScatags1524(getStringKey(getLevels(),mSchool)),mSubject);
                    }
                    if(catagId==1588){
                        scatgId=getIntKey(getScatags1588(getStringKey(getLevels(),mSchool)),mSubject);
                    }
                    if(catagId==1592){
                        scatgId=getIntKey(getScatags1591(getStringKey(getLevels(),mSchool)),mSubject);
                    }
                    LogUtils.d(getScatags1591(getStringKey(getLevels(),mSchool)).toString()+"++++");
                    //int scatgId=getIntKey(map,mSubject);
                    intent.putExtra("catagId",catagId+"");
                    intent.putExtra("scatgId", scatgId+"");
                    LogUtils.d(scatgId+"++++");
                    LogUtils.d(catagId+"++++");
                    LogUtils.d(mMap1.toString()+"++++m1");
                    LogUtils.d(mMap2.toString()+"++++m2");

                    SerializableMap tmap=new SerializableMap();
                    tmap.setMap(mMap1);
                    SerializableMap tmap2=new SerializableMap();
                    tmap2.setMap(mMap2);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("map1",tmap);
                    bundle.putSerializable("map2",tmap2);
                    intent.putExtras(bundle);
                    //TODO
                    startActivity(intent);
                    finish();
                } else {
                    TastyToast.makeText(MyDreamActivity.this,"您还没有选择完成",TastyToast.LENGTH_SHORT,TastyToast.WARNING);
                }
                break;
        }
    }
}
