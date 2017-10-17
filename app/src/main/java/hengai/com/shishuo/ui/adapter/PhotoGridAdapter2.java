package hengai.com.shishuo.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import hengai.com.shishuo.R;
import hengai.com.shishuo.utils.UriToPathUtil;


/**
 * Created by pc on 2016-11-23.
 */
public class PhotoGridAdapter2 extends BaseAdapter {

    private List<Uri> photoUrls;
    private LayoutInflater layoutInflater;
    private Context context;

    public PhotoGridAdapter2(List<Uri> urls, Context context) {
        this.photoUrls = urls;
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return photoUrls == null ? 0 : photoUrls.size();
    }

    @Override
    public Uri getItem(int position) {
        return photoUrls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        PhotoGridViewHolder photoGridViewHolder;
        if (convertView == null) {
            photoGridViewHolder = new PhotoGridViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_grid_photo, parent, false);
            photoGridViewHolder.imageView = (ImageView) convertView.findViewById(R.id.im_tribe_grid_photo);
            photoGridViewHolder.cancel = (ImageView) convertView.findViewById(R.id.im_cancel);
            photoGridViewHolder.cancel.setVisibility(View.VISIBLE);
            photoGridViewHolder.cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (photoUrls.size() > 0) {
                        photoUrls.remove(photoUrls.get(position));
                        notifyDataSetChanged();
                    }

                }
            });
            convertView.setTag(photoGridViewHolder);
        } else {
            photoGridViewHolder = (PhotoGridViewHolder) convertView.getTag();
        }
        Uri uri = getItem(position);
        String path = UriToPathUtil.getImageAbsolutePath((Activity) context, uri);
        Glide.with(context)
                .load(path)
                .centerCrop()
                .into(photoGridViewHolder.imageView);

        return convertView;
    }

    private static class PhotoGridViewHolder {
        ImageView imageView;
        ImageView cancel;
    }

}

