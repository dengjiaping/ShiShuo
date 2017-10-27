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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bokecc.sdk.mobile.exception.ErrorCode;
import com.bokecc.sdk.mobile.upload.Uploader;
import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
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
    @InjectView(R.id.imageView3)
    ImageView mImageView3;
    @InjectView(R.id.tv_main_title)
    TextView mTvMainTitle;
    @InjectView(R.id.main_top_bar)
    RelativeLayout mMainTopBar;
    @InjectView(R.id.im_video_image)
    ImageView mImVideoImage;
    @InjectView(R.id.et_title)
    EditText mEtTitle;
    @InjectView(R.id.rb_speak)
    RadioButton mRbSpeak;
    @InjectView(R.id.rb_rehearsal)
    RadioButton mRbRehearsal;
    @InjectView(R.id.rb_structured)
    RadioButton mRbStructured;
    @InjectView(R.id.rb_defense)
    RadioButton mRbDefense;
    @InjectView(R.id.rg_item)
    RadioGroup mRgItem;
    @InjectView(R.id.tv_categr)
    TextView mTvCategr;
    @InjectView(R.id.tv_xueduan)
    TextView mTvXueduan;
    @InjectView(R.id.tv_subject)
    TextView mTvSubject;
    @InjectView(R.id.btn_upload)
    Button mBtnUpload;
    private boolean isUploading = true;
    private String videoID = null;
    private File file = null;
    private UploadService.UploadBinder binder;
    private boolean isBind = false;
    private Intent service;

    private ServiceConnection serviceConnection;
    private UploadReceiver receiver;

    @OnClick({R.id.imageView3, R.id.rb_speak, R.id.rb_rehearsal, R.id.rb_structured, R.id.rb_defense, R.id.btn_upload})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageView3:
                break;
            case R.id.rb_speak:
                break;
            case R.id.rb_rehearsal:
                break;
            case R.id.rb_structured:
                break;
            case R.id.rb_defense:
                break;
            case R.id.btn_upload:
                upload();
                break;
        }
    }

    private void upload() {
        

    }

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
        ButterKnife.inject(this);
        receiver = new UploadReceiver();
        this.registerReceiver(receiver, new IntentFilter(ConfigUtil.ACTION_UPLOAD));

        initView();
    }

    private void initView() {

        service = new Intent(this, UploadService.class);
        binderService();

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
                        .into(mImVideoImage);
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

    }


    private void uploadVideoLesson(String videoID, float price) {

    }




}
