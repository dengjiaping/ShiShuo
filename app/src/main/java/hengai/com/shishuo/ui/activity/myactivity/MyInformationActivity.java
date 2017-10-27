package hengai.com.shishuo.ui.activity.myactivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sdsmdg.tastytoast.TastyToast;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import hengai.com.shishuo.R;
import hengai.com.shishuo.utils.SPUtils;
import hengai.com.shishuo.utils.UriToPathUtil;

/**
 * 修改我的个人信息配置
 * Created by yu on 2017/10/26.
 */

public class MyInformationActivity extends AppCompatActivity {
    @InjectView(R.id.tv_name)
    TextView mTvName;
    @InjectView(R.id.tv_preservation)
    TextView mTvPreservation;
    @InjectView(R.id.iv_head)
    CircleImageView mIvHead;
    @InjectView(R.id.et_name)
    EditText mEtName;
    @InjectView(R.id.tv_man)
    TextView mTvMan;
    @InjectView(R.id.tv_woman)
    TextView mTvWoman;
    @InjectView(R.id.et_autograph)
    EditText mEtAutograph;

    String sex="女";
    private static final int PHOTO_REQUEST_GALLERY = 2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_information);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        if(((String) SPUtils.get(getApplicationContext(),"headUrl","1")).equals("1")){

        }else{
            File file= new File((String) SPUtils.get(getApplicationContext(),"headUrl","1"));
            Picasso.with(getApplicationContext()).load(file).into(mIvHead);
        }

        mTvName.setText((String)SPUtils.get(getApplicationContext(),"name","良师"));
        mTvWoman.setTextColor(getResources().getColor(R.color.main_color));
    }

    @OnClick({R.id.iv_return, R.id.tv_preservation, R.id.iv_head, R.id.tv_upload_img, R.id.tv_man, R.id.tv_woman})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                finish();
                break;
            case R.id.tv_preservation:
                preservation();
                break;
            case R.id.iv_head:
                head();
                break;
            case R.id.tv_upload_img:

                break;
            case R.id.tv_man:
                mTvMan.setTextColor(getResources().getColor(R.color.main_color));
                mTvWoman.setTextColor(getResources().getColor(R.color.main_button_enable));
                sex="男";
                break;
            case R.id.tv_woman:
                mTvWoman.setTextColor(getResources().getColor(R.color.main_color));
                mTvMan.setTextColor(getResources().getColor(R.color.main_button_enable));
                sex="女";
                break;
        }
    }

    private void preservation() {
        if(!mEtName.getText().toString().isEmpty()){
            TastyToast.makeText(getApplicationContext(),"保存成功",TastyToast.LENGTH_SHORT,TastyToast.SUCCESS);
             finish();
        }else{
            TastyToast.makeText(getApplicationContext(),"请完善个人信息",TastyToast.LENGTH_SHORT,TastyToast.ERROR);
        }

    }

    private void head() {
        Intent intent = new Intent(); //开启Pictures画面Type设定为image
        intent.setType("image/*"); //使用Intent.ACTION_GET_CONTENT这个Action
        intent.setAction(Intent.ACTION_GET_CONTENT); // 取得相片后返回本画面
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_REQUEST_GALLERY && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                Picasso.with(getApplicationContext()).load(UriToPathUtil.getImageAbsolutePath(MyInformationActivity.this,uri)).into(mIvHead);
                File file = new File(new UriToPathUtil().getImageAbsolutePath(MyInformationActivity.this, uri));
                SPUtils.put(getApplicationContext(),"headUrl",file.getAbsolutePath());
                uploadAvatar(file);
                //网络上传图片
                //TODO
            }
        }
    }

    /**
     * 图片上传
     * @param file
     */
    private void uploadAvatar(File file) {

    }

}
