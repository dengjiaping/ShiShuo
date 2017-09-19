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


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import hengai.com.shishuo.R;
import hengai.com.shishuo.bean.GlobalData;
import hengai.com.shishuo.bean.LessonUploadVideo;
import hengai.com.shishuo.bean.MetaUser;
import hengai.com.shishuo.upload.UploadService;
import hengai.com.shishuo.utils.ClickFilter;
import hengai.com.shishuo.utils.ConfigUtil;
import hengai.com.shishuo.utils.DataSet;
import hengai.com.shishuo.utils.LogUtils;
import hengai.com.shishuo.utils.MediaUtil;
import hengai.com.shishuo.utils.ParamsUtil;
import hengai.com.shishuo.utils.ParseToStringUtil_2;


/**
 * Created by pc on 2017-05-02.
 * implements RadioGroup.OnCheckedChangeListener
 */
public class VideoPublishActivity_2 extends Activity  implements RadioGroup.OnCheckedChangeListener{
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
    private String userID, token, item, level, subject, province, nickname, avatar, sex, description;
    //private DbManager mDb;
    private String mFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_video_2);
        String isuploa=getIntent().getStringExtra("isupload");
        if(isuploa!=null){
            if(isuploa.equals("is")){
                isUploading=false;
                videoID=getIntent().getStringExtra("videoId");
            }
        }

        mFilePath = getIntent().getStringExtra("filePath");
        LogUtils.d(mFilePath + "+++++++");
        //mDb = x.getDb(XutilDb.getDaoConfig());
        receiver = new UploadReceiver();
        this.registerReceiver(receiver, new IntentFilter(ConfigUtil.ACTION_UPLOAD));

        initView();


    }

    /*private void deleteDb(String videoId) {
        try {
            UploadLive uploadLive = mDb.selector(UploadLive.class).where("videoId", "=", videoId).findFirst();
            mDb.delete(uploadLive);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }*/
    /*private void insert(String videoId, String uploadId, String filePath) {
        try {
            UploadLive uploadLive = new UploadLive();
            uploadLive.setVideoId(videoId);
            uploadLive.setUploadId(uploadId);
            uploadLive.setFilePath(filePath);
            mDb.save(uploadLive);
        } catch (DbException e) {
            e.printStackTrace();
        }


    }*/

    /*private void query() {
        try {
            List<UploadLive> persons = mDb.findAll(UploadLive.class);
            LogUtils.d("+++++" + persons.toString() + "+++++");
        } catch (DbException e) {
            e.printStackTrace();
        }
    }*/

    private void initView() {
        tvLevel = (TextView) findViewById(R.id.tv_level);
        tvSubject = (TextView) findViewById(R.id.tv_subject);
        btnPublish = (Button) findViewById(R.id.charge_video_publish);
        rgType = (RadioGroup) findViewById(R.id.rg_type);
        rgType.setOnCheckedChangeListener(this);
        btnPublish.setEnabled(false);
        // bindService(new Intent(getApplicationContext(), UploadService.class), serviceConnection, BIND_AUTO_CREATE);
//        Toast.makeText(getApplicationContext(), "视频正在后台上传，请查看通知栏进度", Toast.LENGTH_LONG).show();
        videoiamge = (ImageView) this.findViewById(R.id.im_video_image);
        et_video_description = (EditText) this.findViewById(R.id.et_video_description);

        //TODO
       String uploadId = getIntent().getStringExtra("uploadId");
        videoID = DataSet.getUploadInfo(uploadId).getVideoInfo().getVideoId();
         LogUtils.d(videoID+"-------ID");
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
                    Toast.makeText(getApplicationContext(), "您上传的视频正在审核中，请耐心等待", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!TextUtils.isEmpty(videoID)) {
                    uploadVideoLesson(videoID, 0.0f);
                } else {
                    Toast.makeText(getApplicationContext(), "上传视频出现错误，请确保视频上传成功！", Toast.LENGTH_SHORT).show();
                    //TODO 增加视频重传功能， gig007 160916
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
//TODO
            String uploadId = intent.getStringExtra("uploadId");

            LogUtils.d(uploadId + "++++++222");
            LogUtils.d(mFilePath + "++++++222");
            videoID = DataSet.getUploadInfo(uploadId).getVideoInfo().getVideoId();
            if (i == 1) {
                //insert(videoID, uploadId, mFilePath);
                //query();
                i = 2;
            }


            LogUtils.d("+++++++++这是" + videoID);
            //若状态为上传中，重置当前上传view的标记位置
            int uploadStatus = intent.getIntExtra("status", ParamsUtil.INVALID);
            if (uploadStatus == Uploader.UPLOAD) {
                Toast.makeText(context, "课程正在上传，可在通知栏查看进度", Toast.LENGTH_SHORT).show();
            }
            if (uploadStatus == Uploader.FINISH) {
                binder.cancle();
                isUploading = false;
                Toast.makeText(context, "课程已完成上传，请点击“发布”，完成课程发布", Toast.LENGTH_SHORT).show();
            }
            // 若出现异常，提示用户处理
            int errorCode = intent.getIntExtra("errorCode", ParamsUtil.INVALID);
            if (errorCode == ErrorCode.NETWORK_ERROR.Value()) {
                Toast.makeText(context, "网络异常，请检查", Toast.LENGTH_SHORT).show();
            } else if (errorCode == ErrorCode.PROCESS_FAIL.Value()) {
                Toast.makeText(context, "上传失败，请重试", Toast.LENGTH_SHORT).show();
            } else if (errorCode == ErrorCode.INVALID_REQUEST.Value()) {
                Toast.makeText(context, "上传失败，请检查账户信息", Toast.LENGTH_SHORT).show();
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

    private void uploadVideoLesson(String videoID, float price) {
        LessonUploadVideo req = new LessonUploadVideo();
        MetaUser user = GlobalData.instance().getLoginUser();

        if (user != null) {

            req.setUserID(user.getUserID());
            req.setToken(user.getToken());
            req.setType1(user.getItem());
            req.setType2(type2);
            req.setLevel(user.getLevel());
            req.setSubject(user.getSubject());
        }
        if (price > 0.0f) {
            req.setPrice(price);
        }
        req.setVideoID(videoID);

        if (et_video_description.getText().toString().trim().isEmpty()) {
            Toast.makeText(VideoPublishActivity_2.this, "标题不能为空！", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(VideoPublishActivity_2.this, "上传到服务器", Toast.LENGTH_SHORT).show();
            req.setDescription(et_video_description.getText().toString().trim());
            /*BoBase<LessonResp> bo = new BoBase<LessonResp>();
            bo.request("/lesson/uploadvideo", req, new LessonResp(), file, new Pipe() {
                @Override
                public void complete(Object owner, Object data, int success) {
                    LessonResp lessonResp = (LessonResp) data;
                    if (success >= Pipe.NET_SUCCESS) {
                        MetaLesson metaLesson = lessonResp.getLesson();
                        btnPublish.setClickable(false);
                        deleteDb(videoID);
                        Helper.showToastOnUiThread(VideoPublishActivity_2.this, "视频发布成功！");
                        Intent intent = new Intent(VideoPublishActivity_2.this, PublishOverActivity_2.class);
                        intent.putExtra("videoID", videoID);
                        intent.putExtra("description", et_video_description.getText().toString().trim());
                        intent.putExtra("image", BoBase.BASE_URL + metaLesson.getThumbnails());
                        startActivity(intent);
                        finish();
                    } else if (success == Pipe.SERVER_ERROR) {
                        if (null == data) {
                            Helper.showToastOnUiThread(VideoPublishActivity_2.this, "数据访问出错，请重试");
                        } else {
                            if (lessonResp.getErrcode() == 1) {
                                //T.showShort(VideoPublishActivity_2.this,"++++++++++++");

                                Helper.showToastOnUiThread(VideoPublishActivity_2.this, "权限认证错误，请重新登陆");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        startActivity(new Intent(VideoPublishActivity_2.this, FirstActivity_2.class));
                                        finish();
                                    }
                                });
                            } else if (lessonResp.getErrcode() == 2) {
                                Helper.showToastOnUiThread(VideoPublishActivity_2.this, "课程号不存在");
                            } else if (lessonResp.getErrcode() == 3) {
                                Helper.showToastOnUiThread(VideoPublishActivity_2.this, "保存失败");
                            } else if (lessonResp.getErrcode() == 4) {
                                Helper.showToastOnUiThread(VideoPublishActivity_2.this, "保存失败");
                            }
                        }
                    } else {
                        Helper.showToastOnUiThread(VideoPublishActivity_2.this, "请求数据失败，请检查网络或重试");
                    }
                }
            });*/
        }
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
                type2 = "2";
                btnPublish.setEnabled(true);
                break;
            case R.id.bt_speaking:
                type2 = "0";
                btnPublish.setEnabled(true);
                break;
            case R.id.bt_interview:
                type2 = "3";
                btnPublish.setEnabled(true);
                break;
            case R.id.bt_answer:
                type2 = "4";
                btnPublish.setEnabled(true);
                break;
        }
    }
}
