package hengai.com.shishuo.ui.activity;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bokecc.sdk.mobile.upload.Uploader;
import com.bokecc.sdk.mobile.upload.VideoInfo;



import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import hengai.com.shishuo.R;
import hengai.com.shishuo.bean.UploadInfo;
import hengai.com.shishuo.ui.widget.CameraPreview;
import hengai.com.shishuo.upload.UploadService;
import hengai.com.shishuo.utils.DataSet;
import hengai.com.shishuo.utils.LogUtils;
import hengai.com.shishuo.utils.T;


/**
 * 视频拍摄界面
 * Created by yu on 2017-09-10.
 */
public class VideoRecorderActivity_2 extends AppCompatActivity implements SurfaceHolder.Callback, View.OnClickListener {
    private static final String TAG = "VideoRecorderActivity_2";


    private ImageView video_start, video_send, checkmonitor;
    //预览SurfaceView
    private SurfaceView mSurfaceView;
    private Camera mCamera;
    //录制视频
    private MediaRecorder mMediaRecorder;
    private SurfaceHolder mSurfaceHolder;
    //屏幕分辨率
    private int videoWidth, videoHeight;
    //判断是否正在录制
    private boolean isRecording;
    //段视频保存的目录
    private File mTargetFile;

    private TextView countdown, tvTitle;
    private Timer timer = null;
    private Dialog dialog;
    private String text;
    private static String fileName;
    private static final int PREVIEW = 0;
    private static final int RECORDING = 1;
    private static final int RECORDED = 2;
    private int status = PREVIEW;
    private Intent service;
    private UploadService.UploadBinder binder;
    private int totalTime = 10;

    int cameraCount = 0;
    private int cameraPosition = 1;//0代表前置摄像头，1代表后置摄像头

    //private DbManager mDb;


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mSurfaceHolder = holder;
        startPreView(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mCamera != null) {
            Log.d(TAG, "surfaceDestroyed: ");
            //停止预览并释放摄像头资源
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
        if (mMediaRecorder != null) {
            mMediaRecorder.release();
            mMediaRecorder = null;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        service = new Intent(getApplicationContext(), UploadService.class);
        bindService(service, serviceConnection, Context.BIND_AUTO_CREATE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_recorder_2);
        initView();

    }

/*private void query(){
    try {
        List<UploadLive> persons = mDb.findAll(UploadLive.class);
        LogUtils.d(persons.toString()+"+++++");
    } catch (DbException e) {
        e.printStackTrace();
    }

}*/

    private void initView() {
        videoWidth = 640;
        videoHeight = 480;
        mSurfaceView = (SurfaceView) findViewById(R.id.camera_preview);
        mSurfaceHolder = mSurfaceView.getHolder();
        //设置屏幕分辨率
        mSurfaceHolder.setFixedSize(videoWidth, videoHeight);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mSurfaceHolder.addCallback(this);
        mMediaRecorder = new MediaRecorder();
        countdown = (TextView) findViewById(R.id.countdown);
        video_start = (ImageView) findViewById(R.id.video_start);
        video_send = (ImageView) findViewById(R.id.im_over_send);
        tvTitle = (TextView) findViewById(R.id.tv_main_title);
        checkmonitor = (ImageView) findViewById(R.id.im_checkmonitor);
        /*int lessonType1 = getIntent().getIntExtra("lessonType1", 0);
        int lessonType2 = getIntent().getIntExtra("lessonType2", 0);
        if (lessonType1 == 0 && (lessonType2 == 0 || lessonType2 == 1) || lessonType1 == 1 && (lessonType2 == 0)) {
            totalTime = 10;
        } else {*/
        //TODO      先弄成15分钟的以后再改
            totalTime = 15;
        //}
        video_start.setOnClickListener(this);
        video_send.setOnClickListener(this);
        checkmonitor.setOnClickListener(this);

    }

    /**
     * 开启后置摄像头预览
     *
     * @param holder
     */
    private void startPreView(SurfaceHolder holder) {
        Log.d(TAG, "startPreView: ");

        if (mCamera == null) {
            mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
        }
        if (mMediaRecorder == null) {
            mMediaRecorder = new MediaRecorder();
        }
        if (mCamera != null) {
            mCamera.setDisplayOrientation(90);
            try {
                mCamera.setPreviewDisplay(holder);
                Camera.Parameters parameters = mCamera.getParameters();
                //实现Camera自动对焦
                List<String> focusModes = parameters.getSupportedFocusModes();
                if (focusModes != null) {
                    for (String mode : focusModes) {
                        mode.contains("continuous-video");
                        parameters.setFocusMode("continuous-video");
                    }
                }
                mCamera.setParameters(parameters);
                mCamera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 开启后置摄像头预览
     *
     * @param holder
     */
    private void startPreViewFrant(SurfaceHolder holder) {
        Log.d(TAG, "startPreViewFrant: ");

        if (mCamera == null) {
            mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
        }
        if (mMediaRecorder == null) {
            mMediaRecorder = new MediaRecorder();
        }
        if (mCamera != null) {
            mCamera.setDisplayOrientation(90);
            try {
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 开始录制
     */
    private void startRecord() {

        if (mMediaRecorder != null) {
            //没有外置存储, 直接停止录制
            if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                T.showShort(VideoRecorderActivity_2.this,"SD卡不存在或写保护,请检查权限设置!");
                return;
            }
            try {

                //mMediaRecorder.reset();
                //让视频横向保存
                //mCamera.setDisplayOrientation(90);
                mCamera.stopPreview();
                mCamera.unlock();
                mMediaRecorder.setCamera(mCamera);
                //从相机采集视频
                mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
                // 从麦克采集音频信息
                mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                // TODO: 2016/10/20  设置视频格式
                mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                /*mMediaRecorder.setVideoSize(videoWidth, videoHeight);
                //每秒的帧数
                mMediaRecorder.setVideoFrameRate(24);*/
                //编码格式
                mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);
                mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                // 设置帧频率，然后就清晰了
                mMediaRecorder.setVideoEncodingBitRate(1 * 1024 * 1024);
                // TODO: 2016/10/20 临时写个文件地址, 稍候该!!!
//                File targetDir = Environment.
//                        getExternalStorageDirectory();
                mTargetFile = getOutputMediaFile();
                mMediaRecorder.setOutputFile(mTargetFile.toString());
                mMediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());
                //解决录制视频, 播放器横向问题
                mMediaRecorder.setOrientationHint(90);

                mMediaRecorder.prepare();
                //正式录制
                mMediaRecorder.start();
                isRecording = true;
                tvTitle.setText("");
                tvTitle.setText("录制中...");
                video_start.setBackgroundResource(R.drawable.video_commit);
                checkmonitor.setVisibility(View.GONE);
                isRecording = true;
                timer = new Timer();
                timer.schedule(new mTimerTask(), 0, 1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            T.showShort(getApplicationContext(),"文件名未创建成功");
        }
    }

    /**
     * 获取视频的保存的本地地址
     *
     * @return
     */


    private static File getOutputMediaFile() {
        File mRecVedioPath = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath()
                + File.separator
                + "iMessage"
                + File.separator + "video");
        if (!mRecVedioPath.exists()) {
            mRecVedioPath.mkdirs();
        }
        fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
                + ".mp4";
        File mediaFile = new File(mRecVedioPath.getPath() + File.separator
                + fileName);
        return mediaFile;
    }

    /**
     * 停止录制 并且保存
     */
    private void stopRecordSave() {
        if (isRecording) {
            mMediaRecorder.stop();
            isRecording = false;
            //Toast.makeText(this, "视频已经放至" + mTargetFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            video_send.setVisibility(View.VISIBLE);
            video_start.setVisibility(View.INVISIBLE);
            countdown.setVisibility(View.INVISIBLE);
            status = RECORDED;
            tvTitle.setText("录制完成");
        }
    }

    /**
     * 停止录制, 不保存
     */
    private void stopRecordUnSave() {
        if (isRecording) {
            mMediaRecorder.stop();
            isRecording = false;
            if (mTargetFile.exists()) {
                //不保存直接删掉
                mTargetFile.delete();
            }
        }
    }

    /**
     * 连接上传视频的服务
     */
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (UploadService.UploadBinder) service;
        }
    };

    /**
     * 上传视频到CC后台
     *
     * @param uploadInfo
     */

    private void startUploadService(UploadInfo uploadInfo) {
        VideoInfo videoInfo = uploadInfo.getVideoInfo();
        service.putExtra("title", videoInfo.getTitle());
        service.putExtra("tag", videoInfo.getTags());
        service.putExtra("desc", videoInfo.getDescription());
        service.putExtra("filePath", videoInfo.getFilePath());
        service.putExtra("uploadId", uploadInfo.getUploadId());



        String videoId = videoInfo.getVideoId();
        if (videoId != null && !"".equals(videoId)) {
            service.putExtra("videoId", videoId);
        }

        this.startService(service);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isRecording && timer != null) {
            timer.cancel();
            timer = null;
        } else if (!isRecording && timer == null && status == RECORDED) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            mCamera.lock(); // take camera access back from
                            mCamera.stopPreview();
                        }
                    });
                }
            }, 500);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!isRecording && timer == null && status == RECORDED) {
            mCamera.stopPreview();
        }
        if (mMediaRecorder != null && isRecording) {
            status = PREVIEW;
            mMediaRecorder.stop();
            mMediaRecorder.reset();
            isRecording = false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.video_start:
                //T.showShort(getApplication(),"我被点击了开始录制");
                if (isRecording) {

                    timer.cancel();
                    timer = null;
                    stopRecordSave();
                    String filePath = mTargetFile.getAbsolutePath();
                    String uploadId = UploadInfo.UPLOAD_PRE.concat(System.currentTimeMillis() + "");
                    VideoInfo videoInfo = new VideoInfo();
                    videoInfo.setTitle("上传进度");
                    videoInfo.setTags("标签");
                    videoInfo.setDescription("描述");
                    videoInfo.setFilePath(filePath);

//                    bindService(service, serviceConnection, Context.BIND_AUTO_CREATE);
                    UploadInfo uploadInfo = new UploadInfo(uploadId, videoInfo, Uploader.UPLOAD, 0, null);
                    DataSet.addUploadInfo(uploadInfo);

//                    sendBroadcast(new Intent(ConfigUtil.ACTION_UPLOAD));
                    if (binder.isStop()){
                        startUploadService(uploadInfo);
                    }

                        Intent intent = new Intent(VideoRecorderActivity_2.this, VideoPublishActivity_2.class);
                        intent.putExtra("filePath", filePath);

                    if(getIntent().getStringExtra("catg1")!=null){
                        intent.putExtra("catg1",getIntent().getStringExtra("catg1"));
                    }
                        startActivity(intent);
                        finish();

                } else if (status == RECORDED) {
                    String filePath = mTargetFile.getAbsolutePath();
                    String uploadId = UploadInfo.UPLOAD_PRE.concat(System.currentTimeMillis() + "");
                    VideoInfo videoInfo = new VideoInfo();
                    videoInfo.setTitle("上传进度");
                    videoInfo.setTags("标签");
                    videoInfo.setDescription("描述");
                    videoInfo.setFilePath(filePath);
//                    bindService(service, serviceConnection, Context.BIND_AUTO_CREATE);
                    UploadInfo uploadInfo = new UploadInfo(uploadId, videoInfo, Uploader.WAIT, 0, null);
                    DataSet.addUploadInfo(uploadInfo);
                    if (binder.isStop()) {
                        startUploadService(uploadInfo);
                    }
                    //TODO
                    //if (getIntent().getStringExtra("source").equals("recode")) {
                        Intent intent = new Intent(VideoRecorderActivity_2.this, VideoPublishActivity_2.class);
                        intent.putExtra("filePath", filePath);
                        startActivity(intent);
                        finish();
                    /*} else {
                        Intent intent = new Intent(VideoRecorderActivity_2.this, VideoPublishNoSelectActivity_2.class);
                        intent.putExtra("lessonType2", getIntent().getIntExtra("lessonType2", 0));
                        intent.putExtra("filePath", filePath);
                        startActivity(intent);
                        finish();
                    }*/

                } else {
                    startRecord();
                }
                break;
            case R.id.im_over_send:
                if (isRecording) {
                    timer.cancel();
                    timer = null;
                    stopRecordUnSave();
                    startRecord();
                }
                break;
            case R.id.im_checkmonitor:
                cameraCount = Camera.getNumberOfCameras();
                Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
                for (int i = 0; i < cameraCount; i++) {
                    Camera.getCameraInfo(i, cameraInfo);
                    if (cameraPosition == 1) {
                        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
//                            mCamera.stopPreview();//停掉原来摄像头的预览
//                            mCamera.release();//释放资源
//                            mCamera = null;//取消原来摄像头
                            surfaceDestroyed(mSurfaceHolder);
                            startPreViewFrant(mSurfaceHolder);
                            cameraPosition = 0;
                            break;
                        }
                    } else {
                        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
//                            mCamera.stopPreview();//停掉原来摄像头的预览
//                            mCamera.release();//释放资源
//                            mCamera = null;//取消原来摄像头
                            surfaceDestroyed(mSurfaceHolder);
                            startPreView(mSurfaceHolder);
                            cameraPosition = 1;
                            break;
                        }
                    }
                }

                break;

        }
    }

    /**
     * 录制完成后按返回键弹出是否删除视频
     *
     * @param keyCode
     * @param event
     * @return
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (isRecording) {
                return false;
            } else if (status != PREVIEW)
                showLostRecoder();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showLostRecoder() {
        View view = View.inflate(VideoRecorderActivity_2.this,
                R.layout.do_lost_recoder_dialog, null);
        dialog = new Dialog(VideoRecorderActivity_2.this,
                R.style.Theme_pop_dialog);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        Button cancel_lost_ok = (Button) view.findViewById(R.id.cancel_lost_ok);
        Button cancel_lost_cancel = (Button) view
                .findViewById(R.id.cancel_lost_cancel);
        cancel_lost_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
                // 删除temp视频
                File file = mTargetFile;
                if (file != null) {
                    if (file.exists()) {
                        file.delete();
                    }
                }
            }
        });
        cancel_lost_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * 计时器的实现
     */
    private class mTimerTask extends TimerTask {

        private long start_time;
        private long end_time;
        private SimpleDateFormat simpleDateFormat;

        public mTimerTask() {
            super();
            start_time = System.currentTimeMillis();
            simpleDateFormat = new SimpleDateFormat("mm:ss");
        }

        @Override
        public void run() {
            end_time = System.currentTimeMillis();
            Date date = new Date(end_time - start_time);
            Message msg = new Message();
            if (end_time - start_time < 60 * totalTime * 1000) {
                msg.what = UPDATETIME;
                msg.obj = simpleDateFormat.format(date);
            } else {
                msg.what = MORETHAN20;
                msg.obj = simpleDateFormat.format(date);
            }
            handler.sendMessage(msg);
        }
    }

    private static final int UPDATETIME = 1;
    private static final int MORETHAN20 = 2;
    //	private static final String TAG = null;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATETIME:
                    text = msg.obj.toString();
                    if (text != null && !"".equals(text.trim())) {
                        String min = text.substring(0, 2);
                        String tim = text.substring(3, 5);
                        int mm = Integer.parseInt(min);
                        int tt = Integer.parseInt(tim);
                        countdown.setVisibility(View.VISIBLE);
                        if (tt < 50)
                            countdown.setText("倒计时: " + (totalTime - 1 - mm) + ":" + (59 - tt));
                        else if (tt >= 50 && tt <= 59)
                            countdown.setText("倒计时: " + (totalTime - 1 - mm) + ":0" + (59 - tt));
                    }
                    break;
                case MORETHAN20:
                    timer.cancel();
                    timer = null;
                    // stop recording and release camera
                    if (mMediaRecorder != null)
                        mMediaRecorder.stop(); // stop the recording
                    releaseMediaRecorder(); // release the MediaRecorder
                    if (mCamera != null) { // object
                        mCamera.lock(); // take camera access back from
                        // MediaRecorder
                        mCamera.stopPreview();
                    }

                    video_send.setVisibility(View.VISIBLE);
                    video_start.setVisibility(View.INVISIBLE);
//                    recordReName(uri.toString());
                    countdown.setVisibility(View.INVISIBLE);
                    isRecording = false;
                    status = RECORDED;
                    tvTitle.setText("返回");
                    break;

                default:
                    break;
            }
        }
    };

    private void releaseMediaRecorder() {
        if (mMediaRecorder != null) {
            mMediaRecorder.reset(); // clear recorder configuration
            mMediaRecorder.release(); // release the recorder object
            mMediaRecorder = null;
            mCamera.lock(); // lock camera for later use
        }

        if (mMediaRecorder != null) {
            if (isRecording) {
                try {
                    mMediaRecorder.setOnErrorListener(null);
                    mMediaRecorder.setOnInfoListener(null);
                    mMediaRecorder.stop();

                } catch (RuntimeException e) {
                    e.printStackTrace();

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                        Log.e("test", "sleep for second stop error!!");
                    }
                }
                isRecording = false;
            }

            try {
                mMediaRecorder.stop();
            } catch (Exception e) {
                Log.e("test", "stop fail2", e);
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                Log.e("test", "sleep for reset error Error", e1);
            }

            mMediaRecorder.reset();
            mMediaRecorder.release();

            mMediaRecorder = null;
        }
    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }

    public void onBackClicked(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        VideoRecorderActivity_2.this.finish();
    }
}
