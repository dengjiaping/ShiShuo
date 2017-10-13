package hengai.com.shishuo.ui.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import hengai.com.shishuo.R;

/**
 * Created by yu on 2017/9/30.
 */

public class PopuCitySelect extends PopupWindow {
    private View mView;

    public PopuCitySelect(Context context, AdapterView.OnItemClickListener itemClickListener, String[] str1, String[] str2) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.popu_city_select, null);
        GridView gridView = (GridView) mView.findViewById(R.id.gv_city);

        gridView.setOnItemClickListener(itemClickListener);
        gridView.setAdapter(new TextAdapter(str1,context));


        //设置PopupWindow的View
        this.setContentView(mView);
        //设置PopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置PopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置PopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.Animation1);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(context.getResources().getColor(R.color.white));
        //设置SelectPicPopupWindow弹出窗体的背景

        this.setBackgroundDrawable(dw);

    }

    class TextAdapter extends BaseAdapter {
        private String[] st1;
        private Context ctx;

        public TextAdapter(String[] str, Context context) {
            st1 = str;
            ctx = context;
        }

        @Override
        public int getCount() {
            return st1.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(ctx, R.layout.item_city_popu, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.mTvCity.setText(st1[position]);
            return convertView;
        }
    }

    static class ViewHolder {
        @InjectView(R.id.tv_city)
        TextView mTvCity;
        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
