package hengai.com.shishuo.ui.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bokecc.sdk.mobile.exception.ErrorCode;
import com.bokecc.sdk.mobile.upload.Uploader;
import com.bumptech.glide.Glide;
import com.sdsmdg.tastytoast.TastyToast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import hengai.com.shishuo.R;
import hengai.com.shishuo.bean.Deletecomm;
import hengai.com.shishuo.bean.GlobalData;
import hengai.com.shishuo.bean.MetaUser;
import hengai.com.shishuo.network.HiRetorfit;
import hengai.com.shishuo.upload.UploadService;
import hengai.com.shishuo.utils.ClickFilter;
import hengai.com.shishuo.utils.ConfigUtil;
import hengai.com.shishuo.utils.DataSet;
import hengai.com.shishuo.utils.LogUtils;
import hengai.com.shishuo.utils.MediaUtil;
import hengai.com.shishuo.utils.ParamsUtil;
import hengai.com.shishuo.utils.ParseToStringUtil_2;
import hengai.com.shishuo.utils.SPUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by pc on 2017-05-02.
 * implements RadioGroup.OnCheckedChangeListener
 */
public class VideoPublishActivity_2 extends Activity implements RadioGroup.OnCheckedChangeListener {
    @InjectView(R.id.tv_catg)
    TextView mTvCatg;
    @InjectView(R.id.rg_type)
    RadioGroup mRgType;
    @InjectView(R.id.tv_catg1)
    TextView mTvCatg1;
    private boolean isUploading = true;
    private String videoID = null;
    private Button btnPublish = null;
    private ImageView videoiamge = null;
    private File file = null;
    private EditText et_video_description;
    private UploadService.UploadBinder binder;
    private boolean isBind = false;
    private String type2;
    private MetaUser user;
    private Intent service;
    private ServiceConnection serviceConnection;
    private UploadReceiver receiver;
    private TextView tvLevel, tvSubject;
    private RadioGroup rgType;
    private SharedPreferences sp = null;
    private String FILE = "saveUserNamePwd";
    //Call<Deletecomm> UploadVideo(@Query("channel") String channel, @Query("token") String token,@Query("setid") String setid,@Query("videoid") String videoid,@Query("name") String name,@Query("desc") String desc,@Query("ctag1") String ctag1,@Query("ctag2") String ctag2);
    private String channel, token, setid, videoid, name, desc, ctag1 = "1", ctag2;
    //private DbManager mDb;
    private String mFilePath;
    private String mXueduan;
    private String mSubject;
    private String mCatg1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_video_2);
        ButterKnife.inject(this);
        String isuploa = getIntent().getStringExtra("isupload");

        if (isuploa != null) {
            if (isuploa.equals("is")) {
                isUploading = false;
                videoID = getIntent().getStringExtra("videoId");
                LogUtils.d(videoID + "+++++++videoID");
            }
        }

        mFilePath = getIntent().getStringExtra("filePath");
        LogUtils.d(mFilePath + "+++++++");
        //mDb = x.getDb(XutilDb.getDaoConfig());
        receiver = new UploadReceiver();
        this.registerReceiver(receiver, new IntentFilter(ConfigUtil.ACTION_UPLOAD));
        initData();

    }

    private void initData() {
        channel = (String) SPUtils.get(getApplicationContext(), "channel", "1");
        token = (String) SPUtils.get(getApplicationContext(), "token", "1");
        setid = (String) SPUtils.get(getApplicationContext(), "cfgSetId", "1");
        mCatg1 = getIntent().getStringExtra("catg1");
        ctag2 = "STUD";
        mXueduan = (String) SPUtils.get(getApplicationContext(), "school", "1");
        mSubject = (String) SPUtils.get(getApplicationContext(), "subject", "1");
        initView();
    }

    private void initView() {
        if(mCatg1!=null){
            mTvCatg.setVisibility(View.GONE);
            mRgType.setVisibility(View.GONE);
            ctag1=mCatg1;
            switch (mCatg1){
                case "SK":
                    mTvCatg1.setText("说课");
                    break;
                case "DB":
                    mTvCatg1.setText("答辩");
                    break;
                case "JGH":
                    mTvCatg1.setText("结构化");
                    break;
                case "SJ":
                    mTvCatg1.setText("试讲");
                    break;
            }
        }else{
           mTvCatg1.setVisibility(View.GONE);
        }
        tvLevel = (TextView) findViewById(R.id.tv_level);
        tvSubject = (TextView) findViewById(R.id.tv_subject);
        btnPublish = (Button) findViewById(R.id.change_video_publish);
        rgType = (RadioGroup) findViewById(R.id.rg_type);
        rgType.setOnCheckedChangeListener(this);
        tvLevel.setText(mXueduan);
        tvSubject.setText(mSubject);
        // bindService(new Intent(getApplicationContext(), UploadService.class), serviceConnection, BIND_AUTO_CREATE);

        videoiamge = (ImageView) this.findViewById(R.id.im_video_image);
        et_video_description = (EditText) this.findViewById(R.id.et_video_description);


        LogUtils.d(videoID + "-------ID");
        user = GlobalData.instance().getLoginUser();
        if (user != null) {
            if (!user.getLevel().isEmpty()) {
                tvLevel.setText(ParseToStringUtil_2.parseToString("level", Integer.parseInt(user.getLevel())));
            }
            if (!user.getSubject().isEmpty()) {
                tvSubject.setText(ParseToStringUtil_2.parseToString("subject", Integer.parseInt(user.getSubject())));
            }
        }
        service = new Intent(this, UploadService.class);
        binderService();
        file = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
        FileOutputStream baos = null;
        try {
            baos = new FileOutputStream(file);
            Bitmap bitmap = MediaUtil.getVideoFirstFrame(VideoPublishActivity_2.this, Uri.parse(mFilePath));
            if (null != bitmap) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 0, baos);
                Glide.with(VideoPublishActivity_2.this)
                        .load(file.getAbsolutePath())
                        .centerCrop()
                        .into(videoiamge);
                bitmap.recycle();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (baos != null) {
                try {
                    baos.flush();
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        btnPublish.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ClickFilter.filter()) {
                    return;
                }
                if (isUploading) {

                    TastyToast.makeText(getApplicationContext(),"您上传的视频正在审核中，请耐心等待",TastyToast.LENGTH_SHORT,TastyToast.INFO);
                    return;
                }
                if (et_video_description.getText().toString().isEmpty()) {
                    TastyToast.makeText(getApplicationContext(), "标题不能为空", TastyToast.LENGTH_SHORT, TastyToast.ERROR);

                } else {
                    name = et_video_description.getText().toString();
                    if (!ctag1.equals("1")) {
                        if (!TextUtils.isEmpty(videoID)) {
                            uploadVideoLesson(videoID);
                        } else {

                            TastyToast.makeText(getApplicationContext(),"上传视频出现错误，请确保视频上传成功！",TastyToast.LENGTH_SHORT,TastyToast.ERROR);
                        }
                    } else {
                        TastyToast.makeText(getApplicationContext(), "请选择自己的标签", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                    }
                }

            }
        });
    }

    int i = 1;

    private class UploadReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (!isBind) {
                binderService();
            }

            String uploadId = intent.getStringExtra("uploadId");


            videoID = DataSet.getUploadInfo(uploadId).getVideoInfo().getVideoId();


            LogUtils.d("+++++++++这是" + videoID);

            int uploadStatus = intent.getIntExtra("status", ParamsUtil.INVALID);
            if (uploadStatus == Uploader.UPLOAD) {
                TastyToast.makeText(getApplicationContext(),"课程正在上传，可在通知栏查看进度",TastyToast.LENGTH_SHORT,TastyToast.INFO);
            }
            if (uploadStatus == Uploader.FINISH) {
                binder.cancle();
                isUploading = false;
                TastyToast.makeText(getApplicationContext(),"课程已完成上传,请发布课程",TastyToast.LENGTH_SHORT,TastyToast.SUCCESS);

            }
            // 若出现异常，提示用户处理
            int errorCode = intent.getIntExtra("errorCode", ParamsUtil.INVALID);
            if (errorCode == ErrorCode.NETWORK_ERROR.Value()) {

                TastyToast.makeText(getApplicationContext(),"网络异常，请检查",TastyToast.LENGTH_SHORT,TastyToast.ERROR);
            } else if (errorCode == ErrorCode.PROCESS_FAIL.Value()) {
                TastyToast.makeText(getApplicationContext(),"上传失败，请重试",TastyToast.LENGTH_SHORT,TastyToast.ERROR);
            } else if (errorCode == ErrorCode.INVALID_REQUEST.Value()) {

                TastyToast.makeText(getApplicationContext(),"上传失败，请检查账户信息",TastyToast.LENGTH_SHORT,TastyToast.ERROR);
            }
        }
    }

    //连接上传视频的服务

    private void binderService() {
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.i("service disconnected", name + "");
            }

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                binder = (UploadService.UploadBinder) service;
            }
        };

        this.bindService(service, serviceConnection, Context.BIND_AUTO_CREATE);
        isBind = true;
    }

    private void uploadVideoLesson(String videoID) {


        Call<Deletecomm> call = HiRetorfit.getInstans().getApi().UploadVideo(channel, token, setid, videoID, name, "", ctag1, ctag2);
        call.enqueue(new Callback<Deletecomm>() {
            @Override
            public void onResponse(Call<Deletecomm> call, Response<Deletecomm> response) {
                if (response != null) {
                    if (response.body().getResult() == 1) {

                    }
                }
            }

            @Override
            public void onFailure(Call<Deletecomm> call, Throwable t) {
                TastyToast.makeText(getApplicationContext(), "网络错误", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
            }
        });
        TastyToast.makeText(getApplicationContext(), "发布成功", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
        finish();
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.isBind) {
            unbindService(serviceConnection);
            isBind = false;
        }
        unregisterReceiver(receiver);
    }

    public void onBackClicked(View view) {
        finish();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.bt_trial_teaching:
                ctag1 = "SJ";

                break;
            case R.id.bt_speaking:
                ctag1 = "SK";

                break;
            case R.id.bt_interview:
                ctag1 = "JGH";

                break;
            case R.id.bt_answer:
                ctag1 = "DB";

                break;
        }
    }
}
