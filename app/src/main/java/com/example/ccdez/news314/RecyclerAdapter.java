package com.example.ccdez.news314;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * recyclerview适配器配置
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyHolder> {

    public static List<Bean.Second.Third> list;
    public Context context;

    public OnItemClickListener listener;


    public RecyclerAdapter(List<Bean.Second.Third> list, Context context){
        this.context = context;
        this.list = list;
    }

    class MyHolder extends RecyclerView.ViewHolder{
        ImageView re_image;
        TextView re_title, re_source, re_time;

        public MyHolder(View itemView) {
            super(itemView);

            //获取资源组件
            re_image = (ImageView) itemView.findViewById(R.id.re_image);
            re_title = (TextView) itemView.findViewById(R.id.re_title);
            re_source = (TextView) itemView.findViewById(R.id.re_source);
            re_time = (TextView) itemView.findViewById(R.id.re_time);
        }
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        MyHolder holder = new MyHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_layout, parent, false));

        return holder;
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {

        //配置组件显示数据
        Glide.with(context).load(list.get(position).thumbnail_pic_s).into(holder.re_image);
        holder.re_title.setText(list.get(position).title);
        holder.re_source.setText(list.get(position).author_name);
        holder.re_time.setText(list.get(position).date);

        //又回调则设置点击事件
        if (listener != null){
            //点击监听
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(holder.itemView, position);
                }
            });

            //长按监听
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onItemLongClick(holder.itemView, position);
                    return false;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //暴露接口
    public void setItemClick(OnItemClickListener listener){
        this.listener = listener;
    }

    //点击事件接口
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
}
