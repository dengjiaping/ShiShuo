package hengai.com.shishuo.network;


import hengai.com.shishuo.bean.AddSeeNum;
import hengai.com.shishuo.bean.Banner;
import hengai.com.shishuo.bean.DianZanBean;
import hengai.com.shishuo.bean.HomeBean;
import hengai.com.shishuo.bean.InfoMationBean;
import hengai.com.shishuo.bean.InfoMationDetailBean;
import hengai.com.shishuo.bean.InterViewLiveBean;
import hengai.com.shishuo.bean.LessonVideoBean;
import hengai.com.shishuo.bean.LiveCourseInfo;
import hengai.com.shishuo.bean.LoginBean;
import hengai.com.shishuo.bean.MobileNumble;
import hengai.com.shishuo.bean.MyLiveBean;
import hengai.com.shishuo.bean.QuestionBean;
import hengai.com.shishuo.bean.ReViewBean;
import hengai.com.shishuo.bean.RegisterBean;

import hengai.com.shishuo.bean.SettingMsgBean;
import hengai.com.shishuo.bean.VideoSetting;
import hengai.com.shishuo.bean.WrittenLiveBean;
import hengai.com.shishuo.bean.VideoCouseInfo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by yu on 2017/7/26.
 */

public interface Api {
    //手机号验证是否注册
    @GET("checkmobile.html")
    Call<MobileNumble> ListPhone(@Query("mobile") String numble);

    //获取验证码
    @GET("preregist.html")
    Call<RegisterBean> Code(@Query("channel") String channel, @Query("mobile") String mobile);

    //注册
    @GET("registuser.html")
    Call<RegisterBean> Register(@Query("channel") String channel, @Query("mobile") String mobile, @Query("smscheckcode") String smscheckcode, @Query("password") String password, @Query("password2") String password2);

    //第三方登录
    @GET("umquicklogin.html")
    Call<LoginBean> QuickLogin(@Query("channel") String channel, @Query("umsys") String umsys, @Query("umuid") String umuid, @Query("umname") String umname, @Query("umgender") String umgender, @Query("umheadurl") String umheadurl, @Query("mchcode") String mchcode);

    //手机密码登录
    @GET("mobilelogin.html")
    Call<LoginBean> Login(@Query("channel") String channel, @Query("mobile") String mobile, @Query("password") String password);

    //获取所有设置
    @GET("listallsetting.html")
    Call<SettingMsgBean> Setting(@Query("channel") String channel, @Query("token") String token);

    //上传用户设置
    @GET("updateallsetting.html")
    Call<RegisterBean> UpSetting(@Query("channel") String channel, @Query("branchid") String branchid, @Query("catgid") String catgid, @Query("scatgid") String scatgid, @Query("token") String token);
    //获得视频平台播放配置参数
    @GET("getvcpsetting.html")
    Call<VideoSetting> VideoSetting(@Query("channel") String channel);
    //首页数据的获取
    @GET("homepagejson.html")
    Call<HomeBean> GetHomeData(@Query("channel") String channel, @Query("token") String token);
    @GET("homebannerjson.html")
    Call<Banner> BannerList(@Query("channel") String channel);
    //获取面试直播的数据
    @GET("listinterviewcourse.html")
    Call<InterViewLiveBean> InterLive(@Query("channel") String channel,@Query("token") String token, @Query("page") int page,@Query("paging") int paging);

    //获取笔试直播的数据
    @GET("listwrittencourse.html")
    Call<WrittenLiveBean> WrittenLive(@Query("channel") String channel, @Query("token") String token,@Query("page") int page,@Query("paging") int paging);
    //获取我的直播的数据
    @GET("mycourse.html")
    Call<MyLiveBean> MyLive(@Query("channel") String channel, @Query("token") String token);

    //获取直播详情的数据
    @GET("getcourseinfo.html")
    Call<LiveCourseInfo> CourseDetail(@Query("channel") String channel, @Query("token") String token, @Query("crcode") String crcode);

    @GET("ceshi.json")
    Call<SettingMsgBean> Settings();
    //视频播放列表
    @GET("listvideotables.html")
    Call<LessonVideoBean> LessonVideo(@Query("channel") String channel, @Query("token") String token,@Query("catgid") String catgid, @Query("scatgid") String scatgid,@Query("ctag1") String ctag1,@Query("ctag2") String ctag2,@Query("page") int page,@Query("paging") int paging);
    //code：课程编码,必输   视频详情点赞
    //zanflag：点赞标志,yes点赞/no取消点赞
    @GET("zanvideotable.html")
    Call<DianZanBean> VideoDianZan(@Query("channel") String channel, @Query("token") String token, @Query("code") String code,@Query("zanflag") String zanflag);
//视频评论  commentid
    @GET("commentvideotable.html")
    Call<ReViewBean> ReView(@Query("channel") String channel, @Query("token") String token, @Query("code") String code, @Query("comment") String comment,@Query("commentid") String commentid);
    //视频详情
    @GET("getvideotable.html")
    Call<VideoCouseInfo> LessonVideoCouse(@Query("channel") String channel, @Query("token") String token, @Query("code") String code);

//视频观看人数的增加
    @GET("playvideotable.html")
    Call<AddSeeNum> AddSeeNum(@Query("channel") String channel, @Query("token") String token, @Query("code") String code);
    @GET("listcoursefilebyram.html")
    Call<QuestionBean> QuestionTest(@Query("channel") String channel, @Query("token") String token, @Query("catgid") String catgid, @Query("scatgid") String scatgid, @Query("ctag1") String ctag1);
    //资讯列表    可加根据参数查询@Query("title") String title,
    @GET("listinfo.html")
    Call<InfoMationBean> InfoMationList(@Query("channel") String channel, @Query("token") String token,@Query("catg3") String ctag3);
    //资讯详情
    @GET("getinfocontent.html")
    Call<InfoMationDetailBean> InfoMationDetail(@Query("channel") String channel, @Query("infoid") String id);



}
