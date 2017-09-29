package com.example.ccdez.news314.com.news.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ccdez.news314.Bean;
import com.example.ccdez.news314.R;
import com.example.ccdez.news314.RecyclerAdapter;
import com.example.ccdez.news314.WebActivity;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by cc on 17-9-27.
 */

public class NewsYule extends Fragment {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Bean.Second.Third> list;
    private OkHttpClient client = new OkHttpClient();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            list = (List<Bean.Second.Third>) msg.obj;
            list.addAll((List<Bean.Second.Third>) msg.obj);
            RecyclerAdapter adapter = new RecyclerAdapter(list, getActivity());
            adapter.setItemClick(new RecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                    //启动WebActivity
                    Intent intent = new Intent(getActivity(), WebActivity.class);
                    String url = RecyclerAdapter.list.get(position).url;
                    intent.putExtra("url", url);
                    startActivity(intent);

                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);
            swipeRefreshLayout.setRefreshing(false);

        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rec_layout, container, false);
        // 初始化recyclerview
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.blueStatus));

        updateNews();
//        //更新新闻
//        openNews("http://v.juhe.cn/toutiao/index?type=yule&key=110943cb840fba1bc6c341239ab2ed2f");

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //更新新闻
        openNews("http://v.juhe.cn/toutiao/index?type=yule&key=110943cb840fba1bc6c341239ab2ed2f");
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                //更新
                openNews("http://v.juhe.cn/toutiao/index?type=yule&key=110943cb840fba1bc6c341239ab2ed2f");

            }
        });
        updateNews();
    }

    /**
     * okhttp获取json数据
     * 使用gson解析数据
     */
    public void openNews(String url) {

        final Gson gson = new Gson();
        final Request request = new Request.Builder().get()
                .url(url)
                .build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response;
                try {
                    response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String content = response.body().string();
                        Bean bean = gson.fromJson(content, Bean.class);
                        Bean.Second second = bean.result;

                        list = second.data;

                        Message message = handler.obtainMessage();
                        message.obj = list;
                        handler.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 使用加速度传感器
     * 摇一摇更新新闻
     */
    public void updateNews() {
        SensorManager sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    //监听加速度传感器输出信号
    private SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            //取加速度绝对值
            float xValue = Math.abs(event.values[0]);
            float yValue = Math.abs(event.values[1]);
            float zValue = Math.abs(event.values[2]);

            //加速度超过12m/s^2，认为用户摇动手机，更新新闻列表
            if (xValue > 13 || yValue > 13 || zValue > 13) {
                openNews("http://v.juhe.cn/toutiao/index?type=yule&key=110943cb840fba1bc6c341239ab2ed2f");
                Toast.makeText(getActivity(), "已更新", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
}
