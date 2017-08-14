package hengai.com.shishuo.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by yu on 2017/8/7.
 */

public class HiRetorfit {
    private static HiRetorfit mHiRetorfit;
    private static final String BASE_URL = "http://api.iyoukiss.com/";
    //private static final String BASE_URL = "http://10.0.2.2:8080/";
    private final Api mApi;


    //设置宽大处理畸形的json
    private Gson mGson = new GsonBuilder().setLenient().create();

    public static HiRetorfit getInstans() {
        if (mHiRetorfit == null) {
            synchronized (HiRetorfit.class) {
                if (mHiRetorfit == null) {
                    mHiRetorfit = new HiRetorfit();
                }
            }
        }
        return mHiRetorfit;
    }

    private HiRetorfit() {
        //使用Retrofit来实现Api接口 需要配置gson转换器
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(mGson))
                .build();
        mApi = retrofit.create(Api.class);
    }

    public Api getApi() {
        return mApi;
    }
}
