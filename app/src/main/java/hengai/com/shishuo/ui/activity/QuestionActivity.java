package hengai.com.shishuo.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.Toast;

import com.bokecc.sdk.mobile.exception.ErrorCode;
import com.bokecc.sdk.mobile.upload.Uploader;
import com.bokecc.sdk.mobile.upload.VideoInfo;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import hengai.com.shishuo.R;
import hengai.com.shishuo.bean.QuestionBean;
import hengai.com.shishuo.bean.UploadInfo;
import hengai.com.shishuo.network.HiRetorfit;
import hengai.com.shishuo.ui.widget.PopuRecodeVideo;
import hengai.com.shishuo.upload.InputInfoActivity;
import hengai.com.shishuo.upload.PictureUtils;

import hengai.com.shishuo.upload.UploadService;
import hengai.com.shishuo.upload.UploadView;
import hengai.com.shishuo.upload.UploadViewAdapter;
import hengai.com.shishuo.utils.ConfigUtil;
import hengai.com.shishuo.utils.DataSet;
import hengai.com.shishuo.utils.LogUtils;
import hengai.com.shishuo.utils.ParamsUtil;
import hengai.com.shishuo.utils.SPUtils;
import hengai.com.shishuo.utils.T;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by yu on 2017/9/6.
 */

public class QuestionActivity extends AppCompatActivity {
    @InjectView(R.id.iv_retrun)
    ImageView mIvRetrun;
    @InjectView(R.id.iv_popu)
    ImageView mIvPopu;
    @InjectView(R.id.ll_video_select)
    LinearLayout mLlVideoSelect;
    @InjectView(R.id.title_bar)
    RelativeLayout mTitleBar;
    @InjectView(R.id.im_bkg)
    ImageView mImBkg;
    @InjectView(R.id.linearLayout)
    RelativeLayout mLinearLayout;
    @InjectView(R.id.bt_trial_teaching)
    Button mBtTrialTeaching;
    @InjectView(R.id.bt_speaking)
    Button mBtSpeaking;
    @InjectView(R.id.bt_interview)
    Button mBtInterview;
    @InjectView(R.id.bt_answer)
    Button mBtAnswer;
    @InjectView(R.id.tablelayout)
    TableLayout mTablelayout;
    @InjectView(R.id.bt_select)
    Button mBtSelect;

    private int lessonType2;
    private Intent intent;
    Context mContext;
    private PopuRecodeVideo mPopu;
    private String mCatg1;
    private String mChannel = "liangshishuo";
    private String mToken;
    private String mCatagid;
    private String mScatagid;
    private List<String> mlist=new ArrayList<String>();
    private String mTitle;



    private ListView uploadListView;
    private UploadViewAdapter uploadAdapter;
    private List<UploadInfo> uploadInfos;
    private UploadService.UploadBinder binder;
    private Intent service;
    private ServiceConnection serviceConnection;
    private UploadReceiver receiver;

    private boolean isBind;
    private Timer timer = new Timer();
    private String currentUploadId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellect_exam_2);
        ButterKnife.inject(this);
        mContext = this;
        initUpload();

        mBtSelect.setEnabled(false);
        initData();

    }

    private void initUpload() {
        receiver = new UploadReceiver();
        this.registerReceiver(receiver, new IntentFilter(ConfigUtil.ACTION_UPLOAD));
        service = new Intent(mContext, UploadService.class);

        binderService();

        RelativeLayout view = new RelativeLayout(mContext);
        view.setBackgroundColor(Color.WHITE);
        RelativeLayout.LayoutParams uploadLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        uploadListView = new ListView(mContext);
        uploadListView.setDivider(getResources().getDrawable(R.drawable.line));
        view.addView(uploadListView, uploadLayoutParams);

        uploadListView.setOnItemClickListener(onItemClickListener);
        uploadListView.setOnCreateContextMenuListener(onCreateContextMenuListener);

        initUploadList();


        timer.schedule(timerTask, 0, 1000);
    }

    private void initView() {
        if (!mToken.equals("1") && !mCatagid.equals("1") && !mScatagid.equals("1")) {
            Call<QuestionBean> call = HiRetorfit.getInstans().getApi().QuestionTest(mChannel, mToken, mCatagid, mScatagid, mCatg1);
            call.enqueue(new Callback<QuestionBean>() {
                @Override
                public void onResponse(Call<QuestionBean> call, Response<QuestionBean> response) {

                    if(response!=null){
                        if(response.body().getResult()==1){
                          mTitle=response.body().getData().getName();
                          List<QuestionBean.DataBean.FilesBean>  files= response.body().getData().getFiles();
                            if(files.size()>0){
                                for(int i=0;i<files.size();i++){
                                    mlist.add(files.get(i).getUrl());
                                }
                            }
                            if(files.size()!=0){
                                intent = new Intent(QuestionActivity.this, StartPrepareActivity.class);
                                intent.putExtra("lessonType2", lessonType2);
                                intent.putStringArrayListExtra("imgUrl", (ArrayList<String>) mlist);
                                intent.putExtra("questionContent", mTitle);
                                startActivity(intent);
                            }else{
                               TastyToast.makeText(mContext,"未抽到题型",TastyToast.LENGTH_SHORT,TastyToast.INFO);
                            }

                        }
                    }else{
                        TastyToast.makeText(mContext,"数据解析错误",TastyToast.LENGTH_SHORT,TastyToast.ERROR);
                    }
                }

                @Override
                public void onFailure(Call<QuestionBean> call, Throwable t) {
                    TastyToast.makeText(mContext,"网络错误",TastyToast.LENGTH_SHORT,TastyToast.ERROR);
                }
            });
        }else{
            TastyToast.makeText(mContext,"请先配置个人信息",TastyToast.LENGTH_SHORT,TastyToast.INFO);
            startActivity(new Intent(mContext,MyDreamActivity.class));
        }

    }

    private void initData() {
        mToken = (String) SPUtils.get(mContext, "token", "1");
        mCatagid = (String) SPUtils.get(mContext, "catgId", "1");
        mScatagid = (String) SPUtils.get(mContext, "scatgId", "1");
        LogUtils.d("question" + mToken + mCatagid + "+++" + mScatagid);
    }

    boolean ss = true;

    @OnClick({R.id.iv_retrun, R.id.ll_video_select, R.id.bt_trial_teaching, R.id.bt_speaking, R.id.bt_interview, R.id.bt_answer, R.id.bt_select})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_retrun:
                finish();
                break;
            case R.id.ll_video_select:
                if (ss) {
                    ss = false;
                    mIvPopu.setImageResource(R.drawable.sl);
                    showPopup();
                } else {
                    ss = true;
                    mIvPopu.setImageResource(R.drawable.xl);
                    mPopu.dismiss();
                }
                break;
            case R.id.bt_trial_teaching:
                lessonType2 = 2;
                mCatg1="SJ";
                mlist.clear();
                mBtTrialTeaching.setSelected(true);
                mBtSpeaking.setSelected(false);
                mBtInterview.setSelected(false);
                mBtAnswer.setSelected(false);
                mBtSelect.setEnabled(true);
                break;
            case R.id.bt_speaking:
                lessonType2 = 0;
                mCatg1="SK";
                mlist.clear();
                mBtTrialTeaching.setSelected(false);
                mBtSpeaking.setSelected(true);
                mBtInterview.setSelected(false);
                mBtAnswer.setSelected(false);
                mBtSelect.setEnabled(true);
                break;
            case R.id.bt_interview:
                lessonType2 = 3;
                mCatg1="JGH";
                mlist.clear();
                mBtTrialTeaching.setSelected(false);
                mBtSpeaking.setSelected(false);
                mBtInterview.setSelected(true);
                mBtAnswer.setSelected(false);
                mBtSelect.setEnabled(true);
                break;
            case R.id.bt_answer:
                lessonType2 = 4;
                mCatg1="DB";
                mlist.clear();
                mBtTrialTeaching.setSelected(false);
                mBtSpeaking.setSelected(false);
                mBtInterview.setSelected(false);
                mBtAnswer.setSelected(true);
                mBtSelect.setEnabled(true);
                break;
            case R.id.bt_select:
                initView();
                break;
        }
    }

    private View.OnClickListener onitemsClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_recode_video:
                    Intent intent = new Intent(QuestionActivity.this, VideoRecorderActivity_2.class);
                    intent.putExtra("source", "recode");
                    startActivity(intent);
                    break;
                case R.id.ll_upload_video:
                    //TastyToast.makeText(mContext,"上传视频功能暂未实现",TastyToast.LENGTH_SHORT,TastyToast.INFO);
                    toListVideo();
                    break;
            }
        }
    };
    PopupWindow.OnDismissListener mOnDismissListener = new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            ss = true;
            mIvPopu.setImageResource(R.drawable.xl);
            mPopu.dismiss();
        }
    };

    private void showPopup() {
        mPopu = new PopuRecodeVideo(QuestionActivity.this, onitemsClick);
        mPopu.setOnDismissListener(mOnDismissListener);
        mPopu.showAsDropDown(QuestionActivity.this.findViewById(R.id.ll_video_select),
                0, 0);

    }

    private void toListVideo(){
        TastyToast.makeText(mContext,"正在搜索视频文件",TastyToast.LENGTH_SHORT,TastyToast.INFO);
        Intent intent = null;
        if(android.os.Build.VERSION.SDK_INT < 19){
            intent = new Intent(Intent.ACTION_GET_CONTENT);
        } else {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        }
        intent.setType("video/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(Intent.createChooser(intent, "请选择一个视频文件"), ConfigUtil.UPLOAD_REQUEST);
        } catch (android.content.ActivityNotFoundException ex) {

            TastyToast.makeText(mContext,"请安装文件管理器",TastyToast.LENGTH_SHORT,TastyToast.INFO);
        }
    }
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

    private void initUploadList() {
        uploadInfos = DataSet.getUploadInfos();

        for (UploadInfo uploadInfo: uploadInfos) {
            if ((uploadInfo.getStatus() == Uploader.UPLOAD) && (binder == null || binder.isStop())){
                startUploadService(uploadInfo);
                currentUploadId = uploadInfo.getUploadId();
                break;
            }
        }

        uploadAdapter = new UploadViewAdapter(mContext, uploadInfos);
        uploadListView.setAdapter(uploadAdapter);
    }
    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            UploadView uploadView = (UploadView)view;
            String uploadId = uploadView.getUploadId();
            if (binder.isStop()) {
                UploadInfo uploadInfo = DataSet.getUploadInfo(uploadId);
                if (uploadInfo != null && uploadInfo.getStatus() != Uploader.FINISH) {
                    startUploadService(uploadInfo);
                }

                currentUploadId = uploadId;

            } else if (uploadId.equals(currentUploadId)) {

                switch (binder.getUploaderStatus()) {
                    case Uploader.UPLOAD:
                        binder.pause();
                        break;
                    case Uploader.PAUSE:
                        binder.upload();
                        break;
                }
            }
        }

    };

    View.OnCreateContextMenuListener onCreateContextMenuListener = new View.OnCreateContextMenuListener() {
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("操作");
            menu.add(0, 0, 0, "删除");
        }
    };

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int selectedPosition = ((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position;//获取点击了第几行
        UploadInfo uploadInfo = (UploadInfo) uploadAdapter.getItem(selectedPosition);
        String uploadId = uploadInfo.getUploadId();

        //通知service取消上传
        if (!binder.isStop() && uploadId.equals(currentUploadId)) {
            binder.cancle();
        }

        //删除记录
        DataSet.removeUploadInfo(uploadId);

        initUploadList();
        uploadAdapter.notifyDataSetChanged();
        uploadListView.invalidate();

        return super.onContextItemSelected(item);
    }

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {

            int progress = binder.getProgress();
            UploadInfo uploadInfo = DataSet.getUploadInfo(currentUploadId);
            int position = uploadInfos.indexOf(uploadInfo);

            if (progress > 0 && uploadInfo != null && position >= 0) {
                uploadInfos.remove(position);

                uploadInfo.setProgress(progress);
                uploadInfo.setProgressText(binder.getProgressText());
                DataSet.updateUploadInfo(uploadInfo);

                uploadInfos.add(position, uploadInfo);
                uploadAdapter.notifyDataSetChanged();
                uploadListView.invalidate();
            }

            super.handleMessage(msg);
        }

    };

    // 通过定时器和Handler来更新进度条
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {

            if (binder == null || binder.isStop() ) {
                return;
            }

            if (currentUploadId == null) {
                currentUploadId = binder.getUploadId();
            }

            if (uploadInfos.isEmpty() || currentUploadId == null) {
                return;
            }

            handler.sendEmptyMessage(0);
        }
    };

    private class UploadReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (!isBind) {
                binderService();
            }

            String uploadId = intent.getStringExtra("uploadId");
            if (uploadId != null) {
                currentUploadId = uploadId;
            }

            //若状态为上传中，重置当前上传view的标记位置
            int uploadStatus = intent.getIntExtra("status", ParamsUtil.INVALID);
            if (uploadStatus == Uploader.UPLOAD) {
                currentUploadId = null;
            }

            //若Uploader当前状态为已上传，自动上传处于等待中状态的视频
            if (uploadStatus == Uploader.FINISH) {
                currentUploadId = null;
                for(UploadInfo uploadInfo : uploadInfos){
                    if (uploadInfo.getStatus() == Uploader.WAIT) {
                        startUploadService(uploadInfo);
                        currentUploadId = uploadInfo.getUploadId();
                        break;
                    }
                }
            }

            initUploadList();
            uploadAdapter.notifyDataSetChanged();
            uploadListView.invalidate();

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

    private void startUploadService(UploadInfo uploadInfo) {
        Intent service = new Intent(mContext, UploadService.class);
        VideoInfo videoInfo = uploadInfo.getVideoInfo();
        service.putExtra("title", videoInfo.getTitle());
        service.putExtra("tag", videoInfo.getTags());
        service.putExtra("desc", videoInfo.getTags());
        service.putExtra("filePath", videoInfo.getFilePath());
        service.putExtra("uploadId", uploadInfo.getUploadId());

        String videoId = videoInfo.getVideoId();
        if (videoId != null && !"".equals(videoId)) {
            service.putExtra("videoId", videoId);
        }

        this.startService(service);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == ConfigUtil.UPLOAD_REQUEST) {

            Intent intent = new Intent(QuestionActivity.this, InputInfoActivity.class);
            String filePath = null;
            int sdkVersion = Integer.valueOf(Build.VERSION.SDK);
            Uri uri = data.getData();

            if (sdkVersion >= 19) {
                filePath = PictureUtils.getPath_above19(QuestionActivity.this, uri);
            } else {
                filePath = PictureUtils.getFilePath_below19(QuestionActivity.this, uri);
            }

//			String filePath = getRealPath(data.getData());

            if (filePath == null) {
                TastyToast.makeText(getApplicationContext(),"文件有误，请重新选择",TastyToast.LENGTH_SHORT,TastyToast.ERROR);
                return;
            }

            intent.putExtra("filePath", filePath);
            startActivity(intent);
        }
    }

    @Override
    public void onDestroy() {

        this.unbindService(serviceConnection);
        isBind = false;
        timerTask.cancel();
        this.unregisterReceiver(receiver);

        super.onDestroy();
    }

    @SuppressLint("NewApi")
    private String getRealPath(Uri uri){
        String filePath = null;
        String uriString = uri.toString();

        if(uriString.startsWith("content://media")){
            filePath = getDataColumn(mContext, uri, null, null);
        } else if (uriString.startsWith("file")){
            filePath = uri.getPath();
        } else if (uriString.startsWith("content://com")){
            String docId = DocumentsContract.getDocumentId(uri);
            String[] split = docId.split(":");
            Uri contentUri = null;
            contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            String selection = "_id=?";
            String[] selectionArgs = new String[] {split[1]};
            filePath = getDataColumn(mContext, contentUri, selection, selectionArgs);
        }

        return filePath;
    }

    private String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String[] column = {MediaStore.MediaColumns.DATA};

        try {
            cursor = context.getContentResolver().query(uri, column, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column[0]);
                return cursor.getString(index);
            }
        } catch (Exception e) {
            Log.e("getRealPath error ", "exception: " + e);
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

}