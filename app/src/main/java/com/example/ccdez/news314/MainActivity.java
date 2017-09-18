package com.example.ccdez.news314;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者：陈灿
 * 功能：简易的新闻客户端
 */
public class MainActivity extends AppCompatActivity {

    private static List<Bean.Second.Third> list;
    private OkHttpClient client = new OkHttpClient();
    private RecyclerView recyclerView;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            list = (List<Bean.Second.Third>) msg.obj;
            RecyclerAdapter adapter = new RecyclerAdapter(list, MainActivity.this);
            adapter.setItemClick(new RecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                    //启动WebActivity
                    Intent intent = new Intent(MainActivity.this, WebActivity.class);
                    String url = RecyclerAdapter.list.get(position).url;
                    intent.putExtra("url", url);
                    startActivity(intent);
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });
            recyclerView.setAdapter(adapter);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_layout);

        //初始化recyclerview
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        //判断网络状态
        if (!NetworkDetect.isNetworkConnected(this)) {
            Toast.makeText(this, "网络连接不可用", Toast.LENGTH_SHORT).show();
        }

        //更新新闻
        openNews();
        //摇一摇更新列表
        updateNews();
    }

    /**
     * okhttp获取json数据
     * 使用gson解析数据
     */
    private void openNews() {

        final Gson gson = new Gson();
        final Request request = new Request.Builder().get()
                .url("http://v.juhe.cn/toutiao/index?type=keji&key=65d4c89f2460e131bd8b288f3f70bff6")
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
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
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
                openNews();
                Toast.makeText(MainActivity.this, "已更新", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
}
