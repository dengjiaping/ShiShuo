package hengai.com.shishuo.ui.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
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
import android.widget.TextView;
import android.widget.Toast;

import com.bokecc.sdk.mobile.exception.ErrorCode;
import com.bokecc.sdk.mobile.upload.Uploader;
import com.bumptech.glide.Glide;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import hengai.com.shishuo.R;
import hengai.com.shishuo.bean.MetaUser;
import hengai.com.shishuo.upload.UploadService;
import hengai.com.shishuo.utils.ConfigUtil;
import hengai.com.shishuo.utils.DataSet;
import hengai.com.shishuo.utils.MediaUtil;
import hengai.com.shishuo.utils.ParamsUtil;


/**
 * Created by pc on 2017-05-02.
 */
public class VideoPublishNoSelectActivity_2 extends Activity {
    private boolean isUploading = true;
    private String videoID = null;
    private Button btnPublish = null;
    private ImageView videoiamge = null;
    private File file = null;
    private EditText et_video_description;
    private UploadService.UploadBinder binder;
    private boolean isBind = false;
    private Intent service;
    private int type2;
    private MetaUser user;
    private TextView tvItem, tvInterviewType, tvLevel, tvSubject;
    private ServiceConnection serviceConnection;
    private UploadReceiver receiver;


    //    private BroadcastReceiver brUploadComplete = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            isUploading = false;
//            videoID = intent.getStringExtra("videoID");
//            if (!TextUtils.isEmpty(videoID)) {
//                Toast.makeText(context, "课程已完成上传，请点击“发布”，完成课程发布", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(context, "上传失败，请检查网络后重新上传", Toast.LENGTH_SHORT).show();
//            }
//        }
//    };
    private class UploadReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (!isBind) {
                binderService();
            }

            String uploadId = intent.getStringExtra("uploadId");
            if (DataSet.getUploadInfo(uploadId) != null)
                videoID = DataSet.getUploadInfo(uploadId).getVideoInfo().getVideoId();
            //若状态为上传中，重置当前上传view的标记位置
            int uploadStatus = intent.getIntExtra("status", ParamsUtil.INVALID);
            if (uploadStatus == Uploader.UPLOAD) {
                Toast.makeText(context, "课程正在上传，可在通知栏查看进度", Toast.LENGTH_SHORT).show();
            }
            if (uploadStatus == Uploader.FINISH) {
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

    /**
     * 连接上传视频的服务
     */
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_video_noselect_2);
        receiver = new UploadReceiver();
        this.registerReceiver(receiver, new IntentFilter(ConfigUtil.ACTION_UPLOAD));
        System.out.println("++++++"+"进入了pushino2");
        initView();
    }

    private void initView() {
        btnPublish = (Button) findViewById(R.id.charge_video_publish);
        tvItem = (TextView) findViewById(R.id.tv_item);
        tvInterviewType = (TextView) findViewById(R.id.tv_kind);
        tvLevel = (TextView) findViewById(R.id.tv_level);
        tvSubject = (TextView) findViewById(R.id.tv_subject);
        //user = GlobalData.instance().getLoginUser();
        type2 = getIntent().getIntExtra("lessonType2", 0);
        if (type2 == 3) {
            tvInterviewType.setBackgroundResource(R.drawable.grown_label_selected_style);
        } else {
            tvInterviewType.setBackgroundResource(R.drawable.label_selected_style);
        }
        //tvInterviewType.setText(ParseToStringUtil_2.parseToString("type2", type2));
        if (user != null) {
            if (!user.getItem().isEmpty()) {
                //tvItem.setText(ParseToStringUtil_2.parseToString("item", Integer.parseInt(user.getItem())));
            }
            if (!user.getLevel().isEmpty()) {
                //tvLevel.setText(ParseToStringUtil_2.parseToString("level", Integer.parseInt(user.getLevel())));
            }
            if (!user.getSubject().isEmpty()) {
                //tvSubject.setText(ParseToStringUtil_2.parseToString("subject", Integer.parseInt(user.getSubject())));
            }
        }
        service = new Intent(this, UploadService.class);
        binderService();
        videoiamge = (ImageView) this.findViewById(R.id.im_video_image);
        et_video_description = (EditText) this.findViewById(R.id.et_video_description);
        String filePath = getIntent().getStringExtra("filePath");
        file = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
        FileOutputStream baos = null;
        try {
            baos = new FileOutputStream(file);
            Bitmap bitmap = MediaUtil.getVideoFirstFrame(this, Uri.parse(filePath));
            if (null != bitmap) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 0, baos);
                Glide.with(VideoPublishNoSelectActivity_2.this)
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
        /*btnPublish.setOnClickListener(new View.OnClickListener() {
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
        });*/
    }


    private void uploadVideoLesson(String videoID, float price) {
        /*LessonUploadVideo req = new LessonUploadVideo();
        if (user != null) {
            req.setUserID(user.getUserID());
            req.setToken(user.getToken());
            req.setType1(user.getItem());
            req.setType2(type2 + "");
            req.setLevel(user.getLevel());
            req.setSubject(user.getSubject());
        }
        if (price > 0.0f) {
            req.setPrice(price);
        }
        req.setVideoID(videoID);
        if (et_video_description.getText().toString().trim().isEmpty()) {
            req.setDescription(ParseToStringUtil_2.parseToString("item", Integer.parseInt(user.getItem())) + ParseToStringUtil_2.parseToString("level", Integer.parseInt(user.getLevel())) + ParseToStringUtil_2.parseToString("subject", Integer.parseInt(user.getSubject())) + ParseToStringUtil_2.parseToString("type2", type2) + "练习视频");
        } else {
            req.setDescription(et_video_description.getText().toString().trim());
        }
        BoBase<LessonResp> bo = new BoBase<LessonResp>();
        bo.request("/lesson/uploadvideo", req, new LessonResp(), file, new Pipe() {
            @Override
            public void complete(Object owner, Object data, int success) {
                LessonResp lessonResp = (LessonResp) data;
                if (success >= Pipe.NET_SUCCESS) {
                    MetaLesson metaLesson = lessonResp.getLesson();
                    btnPublish.setClickable(false);
                    Helper.showToastOnUiThread(VideoPublishNoSelectActivity_2.this, "视频发布成功！");
                    Intent intent = new Intent(VideoPublishNoSelectActivity_2.this, PublishOverActivity_2.class);
                    intent.putExtra("videoID", videoID);
                    intent.putExtra("description", et_video_description.getText().toString().trim());
                    intent.putExtra("image", BoBase.BASE_URL + metaLesson.getThumbnails());
                    startActivity(intent);
                    finish();
                } else if (success == Pipe.SERVER_ERROR) {
                    if (null == data) {
                        Helper.showToastOnUiThread(VideoPublishNoSelectActivity_2.this, "数据访问出错，请重试");
                    } else {
                        if (lessonResp.getErrcode() == 1) {
                            Helper.showToastOnUiThread(VideoPublishNoSelectActivity_2.this, "权限认证错误，请重新登陆");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(VideoPublishNoSelectActivity_2.this, FirstActivity_2.class));
                                    finish();
                                }
                            });
                        } else if (lessonResp.getErrcode() == 2) {
                            Helper.showToastOnUiThread(VideoPublishNoSelectActivity_2.this, "课程号不存在");
                        } else if (lessonResp.getErrcode() == 3) {
                            Helper.showToastOnUiThread(VideoPublishNoSelectActivity_2.this, "保存失败");
                        } else if (lessonResp.getErrcode() == 4) {
                            Helper.showToastOnUiThread(VideoPublishNoSelectActivity_2.this, "保存失败");
                        }
                    }
                } else {
                    Helper.showToastOnUiThread(VideoPublishNoSelectActivity_2.this, "请求数据失败，请检查网络或重试");
                }
            }
        });*/
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
        //MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //MobclickAgent.onPause(this);
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

}
