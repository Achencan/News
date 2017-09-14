package com.example.ccdez.news314;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by ccdez on 2017/9/13 0013.
 * 功能：新闻列表适配类
 */

public class NewsListAdapter extends BaseAdapter {

    public static List<Bean.Second.Third> list;
    private Context context;

    class ViewModule {
        private ImageView news_image;
        private TextView news_title;
        private TextView news_source;
        private TextView news_time;
    }

    public NewsListAdapter(List<Bean.Second.Third> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewModule viewModule ;
        if (convertView == null) {
            viewModule = new ViewModule();
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_layout, null, false);
            viewModule.news_image = (ImageView) convertView.findViewById(R.id.news_image);
            viewModule.news_title = (TextView) convertView.findViewById(R.id.news_title);
            viewModule.news_source = (TextView) convertView.findViewById(R.id.news_source);
            viewModule.news_time = (TextView) convertView.findViewById(R.id.news_time);
            convertView.setTag(viewModule);
        }

        viewModule = (ViewModule) convertView.getTag();
        Glide.with(context).load(list.get(position).thumbnail_pic_s).into(viewModule.news_image);
        viewModule.news_title.setText(list.get(position).title);
        viewModule.news_source.setText(list.get(position).author_name);
        viewModule.news_time.setText(list.get(position).date);
        return convertView;
    }
}
