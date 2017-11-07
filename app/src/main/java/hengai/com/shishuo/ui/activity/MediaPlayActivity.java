package hengai.com.shishuo.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.bokecc.sdk.mobile.play.DWMediaPlayer;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sdsmdg.tastytoast.TastyToast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import hengai.com.shishuo.R;
import hengai.com.shishuo.bean.AddSeeNum;
import hengai.com.shishuo.bean.DianZanBean;
import hengai.com.shishuo.bean.ReViewBean;
import hengai.com.shishuo.bean.VideoCouseInfo;
import hengai.com.shishuo.network.HiRetorfit;
import hengai.com.shishuo.ui.adapter.LessonAppraiseAdapter;
import hengai.com.shishuo.ui.widget.PlayTopPopupWindow;
import hengai.com.shishuo.ui.widget.PopMenu;
import hengai.com.shishuo.ui.widget.PopuIntroduce;
import hengai.com.shishuo.ui.widget.Subtitle;
import hengai.com.shishuo.ui.widget.VerticalSeekBar;
import hengai.com.shishuo.utils.ConfigUtil;
import hengai.com.shishuo.utils.DataSet;
import hengai.com.shishuo.utils.DateUtil;
import hengai.com.shishuo.utils.DownloadController;
import hengai.com.shishuo.utils.ParamsUtil;
import hengai.com.shishuo.utils.SPUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 视频播放界面
 *
 * @author yu.
 */
public class MediaPlayActivity extends Activity implements
        DWMediaPlayer.OnBufferingUpdateListener,
        DWMediaPlayer.OnInfoListener,
        DWMediaPlayer.OnPreparedListener, DWMediaPlayer.OnErrorListener,
        MediaPlayer.OnVideoSizeChangedListener, SurfaceHolder.Callback, SensorEventListener, OnCompletionListener {

    @InjectView(R.id.iv_share)
    ImageView mIvShare;
    @InjectView(R.id.tv_video_categry)
    TextView mTvVideoCategry;
    @InjectView(R.id.img_dz)
    ImageView mImgDz;
    @InjectView(R.id.tv_dz_num)
    TextView mTvDzNum;
    @InjectView(R.id.img_msg)
    ImageView mImgMsg;
    @InjectView(R.id.tv_msg_num)
    TextView mTvMsgNum;
    @InjectView(R.id.ll_appraise)
    LinearLayout mLlAppraise;
    @InjectView(R.id.tv_profile)
    TextView mTvProfile;
    @InjectView(R.id.ll_pofile)
    LinearLayout mLlPofile;
    @InjectView(R.id.tv_see_num)
    TextView mTvSeeNum;
    @InjectView(R.id.tv_time)
    TextView mTvTime;
    @InjectView(R.id.tv_teacher_num)
    TextView mTvTeacherNum;
    @InjectView(R.id.tv_see_comments)
    TextView mTvSeeComments;
    @InjectView(R.id.rl_student_appraise)
    RelativeLayout mRlStudentAppraise;
    @InjectView(R.id.lv_video_studentappraise)
    ListView mLvVideoStudentappraise;
    @InjectView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @InjectView(R.id.img_btn_appraise)
    ImageButton mImgBtnAppraise;
    private boolean networkConnected = true;
    private DWMediaPlayer player;
    private Subtitle subtitle;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private ProgressBar bufferProgressBar;
    private SeekBar skbProgress;
    private ImageView backPlayList;
    private TextView videoIdText, playDuration, videoDuration;
    private TextView tvDefinition;
    private PopMenu definitionMenu;
    private LinearLayout playerTopLayout, volumeLayout;
    private LinearLayout playerBottomLayout;
    private AudioManager audioManager;
    private VerticalSeekBar volumeSeekBar;
    private int currentVolume;
    private int maxVolume;
    private TextView subtitleText;

    private boolean isLocalPlay;
    private boolean isPrepared;
    private Map<String, Integer> definitionMap;

    private Handler playerHandler;
    private Timer timer = new Timer();
    private TimerTask timerTask, networkInfoTimerTask;

    private int currentScreenSizeFlag = 1;
    private int currrentSubtitleSwitchFlag = 0;
    private int currentDefinitionIndex = 0;
    // 默认设置为普清
    private int defaultDefinition = DWMediaPlayer.NORMAL_DEFINITION;

    private boolean firstInitDefinition = true;
    private String path;

    private Boolean isPlaying;
    // 当player未准备好，并且当前activity经过onPause()生命周期时，此值为true
    private boolean isFreeze = false;
    private boolean isSurfaceDestroy = false;

    int currentPosition;
    private Dialog dialog;

    private String[] definitionArray;
    private final String[] screenSizeArray = new String[]{"满屏", "100%", "75%", "50%"};
    private final String[] subtitleSwitchArray = new String[]{"开启", "关闭"};
    private final String subtitleExampleURL = "http://dev.bokecc.com/static/font/example.utf8.srt";

    private GestureDetector detector;
    private float scrollTotalDistance;
    private int lastPlayPosition, currentPlayPosition;
    private String videoId;
    private RelativeLayout rlPlay;
    private LinearLayout llBelow;
    private WindowManager wm;
    private ImageView ivFullscreen;

    private PopuIntroduce mPopuIntroduce;
    // 隐藏界面的线程
    private Runnable hidePlayRunnable = new Runnable() {
        @Override
        public void run() {
            setLayoutVisibility(View.GONE, false);
        }
    };
    private String mChannel;
    private String mToken;
    private String mCode;
    private VideoCouseInfo.DataBean mData;
    private List<VideoCouseInfo.DataBean.CommentsBean> mList=new ArrayList<VideoCouseInfo.DataBean.CommentsBean>();
    private String mType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.new_ad_media_play);
        ButterKnife.inject(this);
        initData();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        detector = new GestureDetector(this, new MyGesture());

        initView();

        initPlayHander();

        initPlayInfo();

        if (!isLocalPlay) {
            initNetworkTimerTask();
        }
    }

    private boolean initData() {
        mChannel = (String) SPUtils.get(getApplicationContext(), "channel", "1");
        mToken = (String) SPUtils.get(getApplicationContext(), "token", "1");
        mCode = getIntent().getStringExtra("code");

        mType =
                getIntent().getStringExtra("type");
       if (mCode != null) {
             Call<AddSeeNum> call1=HiRetorfit.getInstans().getApi().AddSeeNum(mChannel,mToken,mCode);
            call1.enqueue(new Callback<AddSeeNum>() {
                @Override
                public void onResponse(Call<AddSeeNum> call, Response<AddSeeNum> response) {

                }

                @Override
                public void onFailure(Call<AddSeeNum> call, Throwable throwable) {

                }
            });
            Call<VideoCouseInfo> call = HiRetorfit.getInstans().getApi().LessonVideoCouse(mChannel, mToken, mCode);
            call.enqueue(new Callback<VideoCouseInfo>() {



                @Override
                public void onResponse(Call<VideoCouseInfo> call, Response<VideoCouseInfo> response) {

                    if (response != null) {
                        if (response.body().getResult() == 1) {
                            mData = response.body().getData();
                         for(int i=0;i<mData.getComments().size();i++){
                             if(mData.getComments().get(i).getFromUserType().equals("T")){
                                 mList.add(mData.getComments().get(i));
                             }
                         }
                            initView1();
                        }
                    }
                }

                @Override
                public void onFailure(Call<VideoCouseInfo> call, Throwable t) {
                    TastyToast.makeText(getApplicationContext(), "网络错误", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                }
            });
        } else {
            TastyToast.makeText(getApplicationContext(), "数据错误请联系客服", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
        }

        return true;
    }

    private void initNetworkTimerTask() {
        networkInfoTimerTask = new TimerTask() {
            @Override
            public void run() {
                parseNetworkInfo();
            }
        };

        timer.schedule(networkInfoTimerTask, 0, 600);
    }

    @OnClick({R.id.iv_share, R.id.img_dz, R.id.tv_dz_num, R.id.img_msg, R.id.tv_msg_num, R.id.ll_appraise, R.id.ll_pofile, R.id.tv_see_comments, R.id.img_btn_appraise,R.id.rl_student_appraise})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_share:
                share();
                break;
            case R.id.img_dz:
                dz();
                break;
            case R.id.tv_dz_num:
                break;
            case R.id.img_msg:
                break;
            case R.id.tv_msg_num:
                break;
            case R.id.ll_appraise:

                break;
            case R.id.ll_pofile:
                profilePopu();
                break;
            case R.id.tv_see_comments:
                Intent intent=new Intent(MediaPlayActivity.this,TeacherCommentsActivity.class);
                intent.putExtra("code",mCode);
                intent.putExtra("title",mData.getTitle());
                startActivity(intent);
                break;
            case R.id.img_btn_appraise:
                popu(view);
                break;
            case R.id.rl_student_appraise:

                break;
        }
    }

    private void dz() {
        if (mData.isMyzan()) {
            dzNet("no");
        } else {
            dzNet("yes");
        }
    }

    private void dzNet(final String bool) {
        Call<DianZanBean> call = HiRetorfit.getInstans().getApi().VideoDianZan(mChannel, mToken, mCode, bool);
        call.enqueue(new Callback<DianZanBean>() {
            @Override
            public void onResponse(Call<DianZanBean> call, Response<DianZanBean> response) {
                if (response != null) {
                    if (response.body().getResult() == 1) {
                        if (bool.equals("no")) {
                            mData.setMyzan(false);
                            mImgDz.setImageResource(R.drawable.dz_button);
                            mTvDzNum.setText((mData.getZannum() - 1) + "");
                            mData.setZannum(mData.getZannum() - 1);
                        } else {
                            mData.setMyzan(true);
                            mImgDz.setImageResource(R.drawable.dz_button_after);
                            mTvDzNum.setText((mData.getZannum() + 1) + "");
                            mData.setZannum(mData.getZannum() + 1);
                        }

                    } else {
                        TastyToast.makeText(getApplicationContext(), "点赞失败请联系客服", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                    }
                }
            }

            @Override
            public void onFailure(Call<DianZanBean> call, Throwable throwable) {
                TastyToast.makeText(getApplicationContext(), "网络错误", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
            }
        });
    }

    private void share() {

    }

    private void popu(View view) {
        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(MediaPlayActivity.this).inflate(
                R.layout.popuwindow_revert_appraise, null);
        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);

        TextView tv = (TextView) contentView.findViewById(R.id.tv_revert_name);
        tv.setText("点评：");
        ImageButton btn = (ImageButton) contentView.findViewById(R.id.img_dismiss);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        final EditText etDetail = (EditText) contentView.findViewById(R.id.et_revert_appraise);
        Button revert = (Button) contentView.findViewById(R.id.btn_revert);
        revert.setText("立即点评");
        revert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(etDetail.getText())) {
                    sendComment(popupWindow, etDetail.getText().toString());

                } else {
                    TastyToast.makeText(getApplicationContext(), "请填写评价内容", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                }
            }
        });
        ColorDrawable cd = new ColorDrawable(getResources().getColor(R.color.white));
        popupWindow.setBackgroundDrawable(cd);
        popupWindow.setTouchable(true); // 设置popupwindow可点击
        popupWindow.setOutsideTouchable(false); // 设置popupwindow外部可点击
        //popupWindow.setBackgroundDrawable();
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

    }

    private void sendComment(final PopupWindow popupWindow, String text) {
        Call<ReViewBean> call = HiRetorfit.getInstans().getApi().ReView(mChannel, mToken, mCode, text,"");
        call.enqueue(new Callback<ReViewBean>() {
            @Override
            public void onResponse(Call<ReViewBean> call, Response<ReViewBean> response) {
                if (response != null) {
                    if (response.body().getResult() == 1) {
                        popupWindow.dismiss();
                        TastyToast.makeText(getApplicationContext(), "评论成功", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                    } else {
                        TastyToast.makeText(getApplicationContext(), "评论失败:" + response.body().getMessage(), TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                    }
                }
            }

            @Override
            public void onFailure(Call<ReViewBean> call, Throwable t) {
                TastyToast.makeText(getApplicationContext(), "评论失败：网络错误", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
            }
        });
    }

    private void profilePopu() {
        mPopuIntroduce = new PopuIntroduce(MediaPlayActivity.this, itemsonClick);
        mPopuIntroduce.showAtLocation(MediaPlayActivity.this.findViewById(R.id.lv_video_studentappraise),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    private View.OnClickListener itemsonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mPopuIntroduce.dismiss();

        }
    };

    private void initView1() {
        if(mType.equals("STUD")){
            mRlStudentAppraise.setVisibility(View.GONE);
        }

        mLvVideoStudentappraise.setAdapter(new LessonAppraiseAdapter(MediaPlayActivity.this, mData.getComments()));
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

                refreshlayout.finishRefresh(initData());
                //
                //initData();
            }
        });

        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {

                refreshlayout.finishLoadmore(initData());
            }
        });

        String startDate = DateUtil.getDate(mData.getStartDate());
        mTvTime.setText(startDate + "");
        //TODO
        mTvSeeNum.setText(mData.getViewtime() + "");
        mTvVideoCategry.setText(mData.getTitle());
        mTvDzNum.setText(mData.getZannum() + "");
        mTvMsgNum.setText(mData.getCommentNum() + "");
        mLlPofile.setVisibility(View.INVISIBLE);
        if (mData.isMyzan()) {
            mImgDz.setImageResource(R.drawable.dz_button_after);
        }

       mTvTeacherNum.setText(mList.size()+"");
       /* if(mList.size()==0){
            mTvSeeComments.setEnabled(false);
        }*/

    }

    enum NetworkStatus {
        WIFI,
        MOBILEWEB,
        NETLESS,
    }

    private NetworkStatus currentNetworkStatus;
    ConnectivityManager cm;

    private void parseNetworkInfo() {
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                if (currentNetworkStatus != null && currentNetworkStatus == NetworkStatus.WIFI) {
                    return;
                } else {
                    currentNetworkStatus = NetworkStatus.WIFI;
                    showWifiToast();
                }

            } else {
                if (currentNetworkStatus != null && currentNetworkStatus == NetworkStatus.MOBILEWEB) {
                    return;
                } else {
                    currentNetworkStatus = NetworkStatus.MOBILEWEB;
                    showMobileDialog();
                }
            }

            initTimerTask();
            networkConnected = true;
        } else {
            if (currentNetworkStatus != null && currentNetworkStatus == NetworkStatus.NETLESS) {
                return;
            } else {
                currentNetworkStatus = NetworkStatus.NETLESS;
                showNetlessToast();
            }

            cancelTimerTask();

            networkConnected = false;
        }
    }

    private void showWifiToast() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //TastyToast.makeText(getApplicationContext(), "已切换至wifi", TastyToast.LENGTH_SHORT, TastyToast.INFO);

            }
        });

    }

    private void showMobileDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(MediaPlayActivity.this);
                AlertDialog dialog = builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                }).setPositiveButton("继续", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setMessage("当前为移动网络，是否继续播放？").create();

                dialog.show();
            }
        });

    }

    private void showNetlessToast() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TastyToast.makeText(getApplicationContext(), "当前无网络信号，无法播放", TastyToast.LENGTH_SHORT, TastyToast.INFO);

            }
        });
    }

    private void initTimerTask() {
        cancelTimerTask();

        timerTask = new TimerTask() {
            @Override
            public void run() {

                if (!isPrepared) {
                    return;
                }

                playerHandler.sendEmptyMessage(0);
            }
        };
        timer.schedule(timerTask, 0, 1000);
    }

    ImageView lockView;
    ImageView ivCenterPlay;
    ImageView ivDownload;
    ImageView ivTopMenu;
    TextView tvChangeVideo;
    ImageView ivBackVideo, ivNextVideo, ivPlay;

    private void initView() {

        llBelow = (LinearLayout) findViewById(R.id.ll_below);
        rlPlay = (RelativeLayout) findViewById(R.id.rl_play);

        rlPlay.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!isPrepared) {
                    return true;
                }

                resetHideDelayed();

                // 事件监听交给手势类来处理
                detector.onTouchEvent(event);
                return true;
            }
        });

        rlPlay.setClickable(true);
        rlPlay.setLongClickable(true);
        rlPlay.setFocusable(true);

        ivTopMenu = (ImageView) findViewById(R.id.iv_top_menu);
        ivTopMenu.setOnClickListener(onClickListener);

        surfaceView = (SurfaceView) findViewById(R.id.playerSurfaceView);
        bufferProgressBar = (ProgressBar) findViewById(R.id.bufferProgressBar);

        ivCenterPlay = (ImageView) findViewById(R.id.iv_center_play);
        ivCenterPlay.setOnClickListener(onClickListener);

        backPlayList = (ImageView) findViewById(R.id.backPlayList);
        videoIdText = (TextView) findViewById(R.id.videoIdText);
        ivDownload = (ImageView) findViewById(R.id.iv_download_play);
        ivDownload.setOnClickListener(onClickListener);

        playDuration = (TextView) findViewById(R.id.playDuration);
        videoDuration = (TextView) findViewById(R.id.videoDuration);
        playDuration.setText(ParamsUtil.millsecondsToStr(0));
        videoDuration.setText(ParamsUtil.millsecondsToStr(0));

        ivBackVideo = (ImageView) findViewById(R.id.iv_video_back);
        ivNextVideo = (ImageView) findViewById(R.id.iv_video_next);
        ivPlay = (ImageView) findViewById(R.id.iv_play);

        ivBackVideo.setOnClickListener(onClickListener);
        ivNextVideo.setOnClickListener(onClickListener);
        ivPlay.setOnClickListener(onClickListener);

        tvChangeVideo = (TextView) findViewById(R.id.tv_change_video);
        tvChangeVideo.setOnClickListener(onClickListener);

        tvDefinition = (TextView) findViewById(R.id.tv_definition);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        volumeSeekBar = (VerticalSeekBar) findViewById(R.id.volumeSeekBar);
        volumeSeekBar.setThumbOffset(2);

        volumeSeekBar.setMax(maxVolume);
        volumeSeekBar.setProgress(currentVolume);
        volumeSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);

        skbProgress = (SeekBar) findViewById(R.id.skbProgress);
        skbProgress.setOnSeekBarChangeListener(onSeekBarChangeListener);

        playerTopLayout = (LinearLayout) findViewById(R.id.playerTopLayout);
        volumeLayout = (LinearLayout) findViewById(R.id.volumeLayout);
        playerBottomLayout = (LinearLayout) findViewById(R.id.playerBottomLayout);

        ivFullscreen = (ImageView) findViewById(R.id.iv_fullscreen);

        ivFullscreen.setOnClickListener(onClickListener);
        backPlayList.setOnClickListener(onClickListener);
        tvDefinition.setOnClickListener(onClickListener);

        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS); //2.3及以下使用，不然出现只有声音没有图像的问题
        surfaceHolder.addCallback(this);

        subtitleText = (TextView) findViewById(R.id.subtitleText);

        lockView = (ImageView) findViewById(R.id.iv_lock);
        lockView.setSelected(false);
        lockView.setOnClickListener(onClickListener);

    }

    private void initPlayHander() {
        playerHandler = new Handler() {
            public void handleMessage(Message msg) {

                if (player == null) {
                    return;
                }

                // 刷新字幕
                /*subtitleText.setText(subtitle.getSubtitleByTime(player
                        .getCurrentPosition()));*/

                // 更新播放进度
                currentPlayPosition = player.getCurrentPosition();
                int duration = player.getDuration();

                if (duration > 0) {
                    long pos = skbProgress.getMax() * currentPlayPosition / duration;
                    playDuration.setText(ParamsUtil.millsecondsToStr(player.getCurrentPosition()));
                    skbProgress.setProgress((int) pos);
                }
            }

            ;
        };


    }

    private void initPlayInfo() {

        // 通过定时器和Handler来更新进度
        isPrepared = false;
        player = new DWMediaPlayer();
        player.reset();
        player.setOnErrorListener(this);
        player.setOnCompletionListener(this);
        player.setOnVideoSizeChangedListener(this);
        player.setOnInfoListener(this);

        videoId = getIntent().getStringExtra("videoId");
        //videoIdText.setText(mData.getTitle());
        isLocalPlay = getIntent().getBooleanExtra("isLocalPlay", false);
        try {

            if (!isLocalPlay) {// 播放线上视频
                player.setVideoPlayInfo(videoId, ConfigUtil.USERID, ConfigUtil.API_KEY, this);
                // 设置默认清晰度
                player.setDefaultDefinition(defaultDefinition);

            } else {// 播放本地已下载视频

                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

                if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                    path = Environment.getExternalStorageDirectory() + "/".concat(ConfigUtil.DOWNLOAD_DIR).concat("/").concat(videoId).concat(".mp4");
                    if (!new File(path).exists()) {
                        return;
                    }
                }
            }

        } catch (IllegalArgumentException e) {
            Log.e("player error", e.getMessage());
        } catch (SecurityException e) {
            Log.e("player error", e.getMessage());
        } catch (IllegalStateException e) {
            Log.e("player error", e + "");
        }

        // 设置视频字幕
        subtitle = new Subtitle(new Subtitle.OnSubtitleInitedListener() {

            @Override
            public void onInited(Subtitle subtitle) {
                // 初始化字幕控制菜单
                // TODO 看看是否有问题
            }
        });
        subtitle.initSubtitleResource(subtitleExampleURL);

    }

    private LayoutParams getScreenSizeParams(int position) {
        currentScreenSizeFlag = position;
        int width = 600;
        int height = 400;
        if (isPortrait()) {
            width = wm.getDefaultDisplay().getWidth();
            height = wm.getDefaultDisplay().getHeight() * 2 / 5; //TODO 根据当前布局更改
        } else {
            width = wm.getDefaultDisplay().getWidth();
            height = wm.getDefaultDisplay().getHeight();
        }


        String screenSizeStr = screenSizeArray[position];
        if (screenSizeStr.indexOf("%") > 0) {// 按比例缩放
            int vWidth = player.getVideoWidth();
            if (vWidth == 0) {
                vWidth = 600;
            }

            int vHeight = player.getVideoHeight();
            if (vHeight == 0) {
                vHeight = 400;
            }

            if (vWidth > width || vHeight > height) {
                float wRatio = (float) vWidth / (float) width;
                float hRatio = (float) vHeight / (float) height;
                float ratio = Math.max(wRatio, hRatio);

                width = (int) Math.ceil((float) vWidth / ratio);
                height = (int) Math.ceil((float) vHeight / ratio);
            } else {
                float wRatio = (float) width / (float) vWidth;
                float hRatio = (float) height / (float) vHeight;
                float ratio = Math.min(wRatio, hRatio);

                width = (int) Math.ceil((float) vWidth * ratio);
                height = (int) Math.ceil((float) vHeight * ratio);
            }


            int screenSize = ParamsUtil.getInt(screenSizeStr.substring(0, screenSizeStr.indexOf("%")));
            width = (width * screenSize) / 100;
            height = (height * screenSize) / 100;
        }

        LayoutParams params = new LayoutParams(width, height);
        return params;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            player.reset();
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setOnBufferingUpdateListener(this);
            player.setOnPreparedListener(this);
            player.setDisplay(holder);
            player.setScreenOnWhilePlaying(true);

            if (isLocalPlay) {
                player.setDataSource(path);
            }
            player.setHttpsPlay(false);
            player.prepareAsync();
        } catch (Exception e) {
            Log.e("videoPlayer", "error", e);
        }
        Log.i("videoPlayer", "surface created");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        holder.setFixedSize(width, height);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (player == null) {
            return;
        }
        if (isPrepared) {
            currentPosition = player.getCurrentPosition();
        }

        isPrepared = false;
        isSurfaceDestroy = true;

        player.stop();
        player.reset();

    }

    @Override
    public void onPrepared(MediaPlayer mp) {

        initTimerTask();
        isPrepared = true;
        if (!isFreeze) {
            if (isPlaying == null || isPlaying.booleanValue()) {
                player.start();
                ivPlay.setImageResource(R.drawable.smallstop_ic);
            }
        }

        if (!isLocalPlay) {
            if (currentPosition > 0) {
                player.seekTo(currentPosition);
            } else {
                lastPlayPosition = DataSet.getVideoPosition(videoId);
                if (lastPlayPosition > 0) {
                    player.seekTo(lastPlayPosition);
                }
            }
        }


        definitionMap = player.getDefinitions();
        if (!isLocalPlay) {
            initDefinitionPopMenu();
        }

        bufferProgressBar.setVisibility(View.GONE);
        setSurfaceViewLayout();
        videoDuration.setText(ParamsUtil.millsecondsToStr(player.getDuration()));
    }

    // 设置surfaceview的布局
    private void setSurfaceViewLayout() {
        LayoutParams params = getScreenSizeParams(currentScreenSizeFlag);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        surfaceView.setLayoutParams(params);
    }

    private void initDefinitionPopMenu() {
        if (definitionMap.size() > 1) {
            currentDefinitionIndex = 1;
            Integer[] definitions = new Integer[]{};
            definitions = definitionMap.values().toArray(definitions);
            // 设置默认为普清，所以此处需要判断一下
            for (int i = 0; i < definitions.length; i++) {
                if (definitions[i].intValue() == defaultDefinition) {
                    currentDefinitionIndex = i;
                }
            }

//			firstInitDefinition = false;
        }

        definitionMenu = new PopMenu(this, R.drawable.popdown, currentDefinitionIndex, getResources().getDimensionPixelSize(R.dimen.popmenu_height));
        // 设置清晰度列表
        definitionArray = new String[]{};
        definitionArray = definitionMap.keySet().toArray(definitionArray);

        definitionMenu.addItems(definitionArray);
        definitionMenu.setOnItemClickListener(new PopMenu.OnItemClickListener() {

            @Override
            public void onItemClick(int position) {
                try {

                    currentDefinitionIndex = position;
                    defaultDefinition = definitionMap.get(definitionArray[position]);

                    if (isPrepared) {
                        currentPosition = player.getCurrentPosition();
                        if (player.isPlaying()) {
                            isPlaying = true;
                        } else {
                            isPlaying = false;
                        }
                    }

                    isPrepared = false;

                    setLayoutVisibility(View.GONE, false);
                    bufferProgressBar.setVisibility(View.VISIBLE);

                    player.reset();

                    player.setDefinition(getApplicationContext(), defaultDefinition);

                } catch (IOException e) {
                    Log.e("player error", e.getMessage());
                }

            }
        });
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        skbProgress.setSecondaryProgress(percent);
    }

    OnClickListener onClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            resetHideDelayed();

            switch (v.getId()) {
            /*case R.id.btnPlay:
                if (!isPrepared) {
					return;
				}
				changePlayStatus();
				break;*/

                case R.id.backPlayList:
                    if (isPortrait() || isLocalPlay) {
                        finish();
                    } else {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    }
                    break;
                case R.id.iv_fullscreen:
                    if (isPortrait()) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    } else {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    }
                    break;
                case R.id.tv_definition:
                    definitionMenu.showAsDropDown(v);
                    break;
                case R.id.iv_lock:
                    if (lockView.isSelected()) {
                        lockView.setSelected(false);
                        setLayoutVisibility(View.VISIBLE, true);
                        toastInfo("已解开屏幕");
                    } else {
                        lockView.setSelected(true);
                        setLandScapeRequestOrientation();
                        setLayoutVisibility(View.GONE, true);
                        lockView.setVisibility(View.VISIBLE);
                        toastInfo("已锁定屏幕");
                    }
                    break;
                case R.id.iv_center_play:
                case R.id.iv_play:
                    changePlayStatus();
                    break;
                case R.id.iv_download_play:
                    downloadCurrentVideo();
                    break;
                case R.id.iv_top_menu:
                    setLayoutVisibility(View.GONE, false);
                    showTopPopupWindow();
                    break;
                case R.id.tv_change_video:
                    setLayoutVisibility(View.GONE, false);

                    break;
                case R.id.iv_video_back:

                    break;
                case R.id.iv_video_next:

                    break;
            }
        }
    };

    // 设置横屏的固定方向，禁用掉重力感应方向
    private void setLandScapeRequestOrientation() {
        int rotation = wm.getDefaultDisplay().getRotation();
        // 旋转90°为横屏正向，旋转270°为横屏逆向
        if (rotation == Surface.ROTATION_90) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else if (rotation == Surface.ROTATION_270) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
        }
    }

	/*private void changeToNextVideo(boolean isCompleted) {
        int currentIndex = getCurrentVideoIndex();
		int length = 1;
		int position = 0;
		if (currentIndex == length - 1) {
			position = 0;
		} else {
			position = ++currentIndex;
		}
		changeVideo(position, isCompleted);
	}*/
	/*private void changeVideo(int position, boolean isCompleted) {
		if (isCompleted) {
			updateCompleteDataPosition();
		} else {
			updateDataPosition();
		}
		
		isPrepared = false;
		
		setLayoutVisibility(View.GONE, false);
		bufferProgressBar.setVisibility(View.VISIBLE);
		ivCenterPlay.setVisibility(View.GONE);
		
		currentPosition = 0;
		currentPlayPosition = 0;
		
		cancelTimerTask();
		//TODO
		videoId = "A283A0C6C3DDFFE09C33DC5901307461";
		
		*//*if (playChangeVideoPopupWindow != null) {
			playChangeVideoPopupWindow.setSelectedPosition(getCurrentVideoIndex()).refreshView();
		}*//*
		
		player.pause();
		player.stop();
		player.reset();
		player.setDefaultDefinition(defaultDefinition);
		player.setVideoPlayInfo(videoId, ConfigUtil.USERID, ConfigUtil.API_KEY, MediaPlayActivity.this);
		player.setDisplay(surfaceHolder);
		player.prepareAsync();
	}*/

    PlayTopPopupWindow playTopPopupWindow;

    private void showTopPopupWindow() {
        if (playTopPopupWindow == null) {
            initPlayTopPopupWindow();
        }
        playTopPopupWindow.showAsDropDown(rlPlay);
    }

    private void initPlayTopPopupWindow() {
        playTopPopupWindow = new PlayTopPopupWindow(this, surfaceView.getHeight());
        playTopPopupWindow.setSubtitleCheckLister(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_subtitle_open:// 开启字幕
                        currentScreenSizeFlag = 0;
                        subtitleText.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rb_subtitle_close:// 关闭字幕
                        currentScreenSizeFlag = 1;
                        subtitleText.setVisibility(View.GONE);
                        break;
                }
            }
        });

        playTopPopupWindow.setScreenSizeCheckLister(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int position = 0;
                switch (checkedId) {
                    case R.id.rb_screensize_full:
                        position = 0;
                        break;
                    case R.id.rb_screensize_100:
                        position = 1;
                        break;
                    case R.id.rb_screensize_75:
                        position = 2;
                        break;
                    case R.id.rb_screensize_50:
                        position = 3;
                        break;
                }

                //Toast.makeText(getApplicationContext(), screenSizeArray[position], Toast.LENGTH_SHORT).show();
                LayoutParams params = getScreenSizeParams(position);
                params.addRule(RelativeLayout.CENTER_IN_PARENT);
                surfaceView.setLayoutParams(params);
            }
        });

    }

    private void downloadCurrentVideo() {
        if (DataSet.hasDownloadInfo(videoId)) {
            Toast.makeText(this, "文件已存在", Toast.LENGTH_SHORT).show();
            return;
        }

        DownloadController.insertDownloadInfo(videoId, videoId);
        Toast.makeText(this, "文件已加入下载队列", Toast.LENGTH_SHORT).show();
    }

    private void toastInfo(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }

    OnSeekBarChangeListener onSeekBarChangeListener = new OnSeekBarChangeListener() {
        int progress = 0;

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            if (networkConnected || isLocalPlay) {
                player.seekTo(progress);
                playerHandler.postDelayed(hidePlayRunnable, 5000);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            playerHandler.removeCallbacks(hidePlayRunnable);
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (networkConnected || isLocalPlay) {
                this.progress = progress * player.getDuration() / seekBar.getMax();
            }
        }
    };

    OnSeekBarChangeListener seekBarChangeListener = new OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            currentVolume = progress;
            volumeSeekBar.setProgress(progress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            playerHandler.removeCallbacks(hidePlayRunnable);
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            playerHandler.postDelayed(hidePlayRunnable, 5000);
        }

    };

    // 控制播放器面板显示
    private boolean isDisplay = false;

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        // 监测音量变化
        if (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN
                || event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_UP) {

            int volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            if (currentVolume != volume) {
                currentVolume = volume;
                volumeSeekBar.setProgress(currentVolume);
            }

            if (isPrepared) {
                setLayoutVisibility(View.VISIBLE, true);
            }
        }
        return super.dispatchKeyEvent(event);
    }

    /**
     * @param visibility 显示状态
     * @param isDisplay  是否延迟消失
     */
    private void setLayoutVisibility(int visibility, boolean isDisplay) {
        if (player == null || player.getDuration() <= 0) {
            return;
        }

        playerHandler.removeCallbacks(hidePlayRunnable);

        this.isDisplay = isDisplay;

        if (definitionMenu != null && visibility == View.GONE) {
            definitionMenu.dismiss();
        }

        if (isDisplay) {
            playerHandler.postDelayed(hidePlayRunnable, 5000);
        }

        if (isPortrait()) {
            ivFullscreen.setVisibility(visibility);

            lockView.setVisibility(View.GONE);

            volumeLayout.setVisibility(View.GONE);
            tvDefinition.setVisibility(View.GONE);
            tvChangeVideo.setVisibility(View.GONE);
            ivTopMenu.setVisibility(View.GONE);
            ivBackVideo.setVisibility(View.GONE);
            ivNextVideo.setVisibility(View.GONE);
        } else {
            ivFullscreen.setVisibility(View.GONE);

            lockView.setVisibility(visibility);
            if (lockView.isSelected()) {
                visibility = View.GONE;
            }

            volumeLayout.setVisibility(visibility);
            tvDefinition.setVisibility(visibility);
            tvChangeVideo.setVisibility(visibility);
            ivTopMenu.setVisibility(visibility);
            ivBackVideo.setVisibility(visibility);
            ivNextVideo.setVisibility(visibility);
        }

        if (isLocalPlay) {
            ivDownload.setVisibility(View.GONE);
            ivTopMenu.setVisibility(View.GONE);

            ivBackVideo.setVisibility(View.GONE);
            ivNextVideo.setVisibility(View.GONE);
            tvChangeVideo.setVisibility(View.GONE);
            tvDefinition.setVisibility(View.GONE);
            ivFullscreen.setVisibility(View.INVISIBLE);
        }

        playerTopLayout.setVisibility(visibility);
        playerBottomLayout.setVisibility(visibility);
    }

    private Handler alertHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            toastInfo("视频异常，无法播放。");
            super.handleMessage(msg);
        }

    };

    boolean isBackupPlay = false;

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Message msg = new Message();
        msg.what = what;

        if (!isBackupPlay) {
            startBackupPlay();
        } else {
            if (alertHandler != null) {
                alertHandler.sendMessage(msg);
            }
        }
        return false;
    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
        setSurfaceViewLayout();
    }


    // 重置隐藏界面组件的延迟时间
    private void resetHideDelayed() {
        playerHandler.removeCallbacks(hidePlayRunnable);
        playerHandler.postDelayed(hidePlayRunnable, 5000);
    }

    // 手势监听器类
    private class MyGesture extends SimpleOnGestureListener {

        private Boolean isVideo;
        private float scrollCurrentPosition;
        private float scrollCurrentVolume;

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return super.onSingleTapUp(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (lockView.isSelected()) {
                return true;
            }
            if (isVideo == null) {
                if (Math.abs(distanceX) > Math.abs(distanceY)) {
                    isVideo = true;
                } else {
                    isVideo = false;
                }
            }

            if (isVideo.booleanValue()) {
                parseVideoScroll(distanceX);
            } else {
                parseAudioScroll(distanceY);
            }

            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        private void parseVideoScroll(float distanceX) {
            if (!isDisplay) {
                setLayoutVisibility(View.VISIBLE, true);
            }

            scrollTotalDistance += distanceX;

            float duration = (float) player.getDuration();

            float width = wm.getDefaultDisplay().getWidth() * 0.75f; // 设定总长度是多少，此处根据实际调整
            //右滑distanceX为负
            float currentPosition = scrollCurrentPosition - (float) duration * scrollTotalDistance / width;

            if (currentPosition < 0) {
                currentPosition = 0;
            } else if (currentPosition > duration) {
                currentPosition = duration;
            }

            player.seekTo((int) currentPosition);

            playDuration.setText(ParamsUtil.millsecondsToStr((int) currentPosition));
            int pos = (int) (skbProgress.getMax() * currentPosition / duration);
            skbProgress.setProgress(pos);
        }

        private void parseAudioScroll(float distanceY) {
            if (!isDisplay) {
                setLayoutVisibility(View.VISIBLE, true);
            }
            scrollTotalDistance += distanceY;

            float height = wm.getDefaultDisplay().getHeight() * 0.75f;
            // 上滑distanceY为正
            currentVolume = (int) (scrollCurrentVolume + maxVolume * scrollTotalDistance / height);

            if (currentVolume < 0) {
                currentVolume = 0;
            } else if (currentVolume > maxVolume) {
                currentVolume = maxVolume;
            }

            volumeSeekBar.setProgress(currentVolume);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public void onShowPress(MotionEvent e) {
            super.onShowPress(e);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            scrollTotalDistance = 0f;
            isVideo = null;

            scrollCurrentPosition = (float) player.getCurrentPosition();
            scrollCurrentVolume = currentVolume;

            return super.onDown(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if (lockView.isSelected()) {
                return true;
            }
            if (!isDisplay) {
                setLayoutVisibility(View.VISIBLE, true);
            }
            changePlayStatus();
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            return super.onDoubleTapEvent(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            if (isDisplay) {
                setLayoutVisibility(View.GONE, false);
            } else {
                setLayoutVisibility(View.VISIBLE, true);
            }
            return super.onSingleTapConfirmed(e);
        }
    }

    private void changePlayStatus() {
        if (player.isPlaying()) {
            player.pause();
            ivCenterPlay.setVisibility(View.VISIBLE);
            ivPlay.setImageResource(R.drawable.smallbegin_ic);

        } else {
            player.start();
            ivCenterPlay.setVisibility(View.GONE);
            ivPlay.setImageResource(R.drawable.smallstop_ic);
        }
    }

    private Runnable backupPlayRunnable = new Runnable() {

        @Override
        public void run() {
            startBackupPlay();
        }
    };

    private void startBackupPlay() {
        cancelTimerTask();

        player.setBackupPlay(true);
        isBackupPlay = true;
        player.reset();
        player.prepareAsync();
    }

    private void cancelTimerTask() {
        if (timerTask != null) {
            timerTask.cancel();
        }
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        switch (what) {
            case DWMediaPlayer.MEDIA_INFO_BUFFERING_START:
                if (player.isPlaying()) {
                    bufferProgressBar.setVisibility(View.VISIBLE);
                }

                if (!isBackupPlay) {
                    playerHandler.postDelayed(backupPlayRunnable, 10 * 1000);
                }

                break;
            case DWMediaPlayer.MEDIA_INFO_BUFFERING_END:
                bufferProgressBar.setVisibility(View.GONE);
                playerHandler.removeCallbacks(backupPlayRunnable);
                break;
        }
        return false;
    }

    // 获得当前屏幕的方向
    private boolean isPortrait() {
        int mOrientation = getApplicationContext().getResources().getConfiguration().orientation;
        if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            return false;
        } else {
            return true;
        }
    }

    private int mX, mY, mZ;
    private long lastTimeStamp = 0;
    private Calendar mCalendar;
    private SensorManager sensorManager;

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == null) {
            return;
        }

        if (!lockView.isSelected() && (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)) {
            int x = (int) event.values[0];
            int y = (int) event.values[1];
            int z = (int) event.values[2];
            mCalendar = Calendar.getInstance();
            long stamp = mCalendar.getTimeInMillis() / 1000l;

            int second = mCalendar.get(Calendar.SECOND);// 53

            int px = Math.abs(mX - x);
            int py = Math.abs(mY - y);
            int pz = Math.abs(mZ - z);

            int maxvalue = getMaxValue(px, py, pz);
            if (maxvalue > 2 && (stamp - lastTimeStamp) > 1) {
                lastTimeStamp = stamp;
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
            }
            mX = x;
            mY = y;
            mZ = z;
        }
    }

    /**
     * 获取一个最大值
     *
     * @param px
     * @param py
     * @param pz
     * @return
     */
    private int getMaxValue(int px, int py, int pz) {
        int max = 0;
        if (px > py && px > pz) {
            max = px;
        } else if (py > px && py > pz) {
            max = py;
        } else if (pz > px && pz > py) {
            max = pz;
        }
        return max;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onBackPressed() {

        if (isPortrait() || isLocalPlay) {
            super.onBackPressed();
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    public void onResume() {
        if (isFreeze) {
            isFreeze = false;
            if (isPrepared) {
                player.start();
            }
        } else {
            if (isPlaying != null && isPlaying.booleanValue() && isPrepared) {
                player.start();
            }
        }
        super.onResume();
        if (!isLocalPlay) {
            sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    public void onPause() {
        if (isPrepared) {
            // 如果播放器prepare完成，则对播放器进行暂停操作，并记录状态
            if (player.isPlaying()) {
                isPlaying = true;
            } else {
                isPlaying = false;
            }
            player.pause();
        } else {
            // 如果播放器没有prepare完成，则设置isFreeze为true
            isFreeze = true;
        }
        super.onPause();
    }

    @Override
    protected void onStop() {
        if (!isLocalPlay) {
            sensorManager.unregisterListener(this);
            setLandScapeRequestOrientation();
        }

        cancelTimerTask();
        super.onStop();
    }

    private void updateDataPosition() {
        if (isLocalPlay) {
            return;
        }

        if (currentPlayPosition > 0) {
            if (DataSet.getVideoPosition(videoId) > 0) {
                DataSet.updateVideoPosition(videoId, currentPlayPosition);
            } else {
                DataSet.insertVideoPosition(videoId, currentPlayPosition);
            }
        }
    }

    private void updateCompleteDataPosition() {
        if (DataSet.getVideoPosition(videoId) > 0) {
            DataSet.updateVideoPosition(videoId, currentPlayPosition);
        } else {
            DataSet.insertVideoPosition(videoId, currentPlayPosition);
        }
    }

    @Override
    protected void onDestroy() {
        cancelTimerTask();

        playerHandler.removeCallbacksAndMessages(null);
        playerHandler = null;

        alertHandler.removeCallbacksAndMessages(null);
        alertHandler = null;

        updateDataPosition();

        if (player != null) {
            player.reset();
            player.release();
            player = null;
        }
        if (dialog != null) {
            dialog.dismiss();
        }
        if (!isLocalPlay) {
            networkInfoTimerTask.cancel();
        }
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);

        if (isPrepared) {
            // 刷新界面
            setLayoutVisibility(View.GONE, false);
            setLayoutVisibility(View.VISIBLE, true);
        }

        lockView.setSelected(false);

        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            llBelow.setVisibility(View.VISIBLE);
            ivFullscreen.setImageResource(R.drawable.fullscreen_close);


            if (playTopPopupWindow != null) {
                playTopPopupWindow.dismiss();
            }

        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            llBelow.setVisibility(View.GONE);
            ivFullscreen.setImageResource(R.drawable.fullscreen_open);
        }

        setSurfaceViewLayout();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

        if (isLocalPlay) {
            toastInfo("播放完成！");
            finish();
            return;
        }


    }
}
