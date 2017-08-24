package hengai.com.shishuo.ui.adapter;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import hengai.com.shishuo.R;

/**
 * 直播详情图片说明
 * Created by yu on 2017/8/24.
 */

public class LiveDerailImgAdapter extends BaseAdapter {
    String mUrl=null;
    Context mContext=null;
    public LiveDerailImgAdapter(String url, Context context) {
        mUrl=url;
        mContext=context;
    }

    @Override
    public int getCount() {
        if(mUrl==null){
            return 0;
        }else{
            return 1;
        }
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=View.inflate(mContext, R.layout.item_live_detailimg,null);
        ImageView img= (ImageView) convertView.findViewById(R.id.iv_detailimg);
        Picasso.with(mContext).load(mUrl).into(img);
        return convertView;
    }
}
