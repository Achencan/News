package com.example.ccdez.news314;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.ccdez.news314.com.news.fragment.NewsDetail;
import com.example.ccdez.news314.com.news.fragment.NewsKeji;
import com.example.ccdez.news314.com.news.fragment.NewsShehui;
import com.example.ccdez.news314.com.news.fragment.NewsTiyu;
import com.example.ccdez.news314.com.news.fragment.NewsTop;
import com.example.ccdez.news314.com.news.fragment.NewsYule;
import com.example.ccdez.news314.web.FeedBack;

/**
 * 作者：陈灿
 * 功能：简易的新闻客户端
 */
public class MainActivity extends AppCompatActivity {

    private int positon = 0;
    private NewsTop newsTop;
    private NewsKeji newsKeji;
    private NewsTiyu newsTiyu;
    private NewsYule newsYule;
    private NewsShehui newsShehui;
    private Fragment[] fragments;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.top:
                    if (positon != 0) {
                        switchFrament(positon, 0);
                        positon = 0;
                    }
                    return true;
                case R.id.keji:
                    if (positon != 1) {
                        switchFrament(positon, 1);
                        positon = 1;
                    }
                    return true;
                case R.id.tiyu:
                    if (positon != 2) {
                        switchFrament(positon, 2);
                        positon = 2;
                    }
                    return true;
                case R.id.yule:
                    if (positon != 3) {
                        switchFrament(positon, 3);
                        positon = 3;
                    }
                    return true;
                case R.id.shehui:
                    if (positon != 4) {
                        switchFrament(positon, 4);
                        positon = 4;
                    }
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //判断网络状态
        if (!NetworkDetect.isNetworkConnected(this)) {
            Toast.makeText(this, "网络连接不可用", Toast.LENGTH_SHORT).show();
        }

        BottomNavigationView bottomMenu = (BottomNavigationView) findViewById(R.id.bottom);
        bottomMenu.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        initFragments();
    }


    /**
     * 切换Fragment
     *
     * @param lastIndex 上个显示Fragment的索引
     * @param index     需要显示的Fragment的索引
     */
    public void switchFrament(int lastIndex, int index) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.hide(fragments[lastIndex]);
        if (!fragments[index].isAdded()) {
            transaction.add(R.id.content, fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();
    }

    private void initFragments() {
        newsTop = new NewsTop();
        newsKeji = new NewsKeji();
        newsTiyu = new NewsTiyu();
        newsYule = new NewsYule();
        newsShehui = new NewsShehui();
        fragments = new Fragment[]{newsTop, newsKeji, newsTiyu, newsYule, newsShehui};
        positon = 0;
        getFragmentManager().beginTransaction()
                .add(R.id.content, newsTop)
                .show(newsTop)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.finish:
                finish();
                break;
            case R.id.feedback:
                feedBack();
                break;
            case R.id.about:
                aboutApp();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //信息反馈
    public void feedBack(){
        Intent intent = new Intent(MainActivity.this, FeedBack.class);
        startActivity(intent);
    }

    //对话框显示软件信息
    public void aboutApp(){
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                .setIcon(R.drawable.news_icon)
                .setTitle("应用信息")
                .setMessage("一个简洁的新闻浏览应用"+"\n"+"遵循MD设计风格"+"\n"+"卡片式展示新闻列表"+"\n"
                +"流畅的浏览体验"+"\n"+"作者：Achencan"+"\n"+"邮箱：cc.geogle@gmail.com"
                +"\n"+"有问题请发送至作者邮箱");
        dialog.create();
        dialog.show();
    }

}
