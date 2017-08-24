package hengai.com.shishuo.network;


import hengai.com.shishuo.bean.CourseDetailsBean;
import hengai.com.shishuo.bean.HomeBean;
import hengai.com.shishuo.bean.InterViewLiveBean;
import hengai.com.shishuo.bean.LoginBean;
import hengai.com.shishuo.bean.MobileNumble;
import hengai.com.shishuo.bean.MyLiveBean;
import hengai.com.shishuo.bean.RegisterBean;

import hengai.com.shishuo.bean.SettingMsgBean;
import hengai.com.shishuo.bean.WrittenLiveBean;
import hengai.com.shishuo.bean.Xueke;
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

    //首页数据的获取
    @GET("homepagejson.html")
    Call<HomeBean> GetHomeData(@Query("channel") String channel, @Query("token") String token);
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
    Call<CourseDetailsBean> CourseDetail(@Query("channel") String channel, @Query("token") String token,@Query("courseid") String courseid);

    @GET("ceshi.json")
    Call<SettingMsgBean> Settings();
    /*@GET("categoryMenu")
    Call<List<CategoryMenuBean>> ListCategory();*/
   /* @GET("recommend")
    Call<List<String>> ListRecommend();
    @GET("category")
    Call<List<CategoryBean>> ListCategory();

    @GET("subject")
    Call<List<SubjectBean>> ListSubject(@Query("index") int index);

    @GET("game")
    Call<List<AppListItemBean>> ListGame(@Query("index") int index);

    @GET("app")
    Call<List<AppListItemBean>> ListApp(@Query("index") int index);

    @GET("home")
    Call<HomeBean> ListHome(@Query("index") int index);
    @GET("detail")
    Call<AppDetailBean> ListDetail(@Query("packageName") String packageName);*/


}
