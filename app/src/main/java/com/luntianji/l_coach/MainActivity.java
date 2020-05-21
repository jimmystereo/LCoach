package com.luntianji.l_coach;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luntianji.l_coach.model.Training;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import genomu.command.GetListCommand;
import genomu.firestore_helper.DBCommand;
import genomu.firestore_helper.DBEmcee;
import genomu.firestore_helper.DBReceiver;

public class MainActivity extends NavCreater {
    RecyclerView recyclerView1;
    RecyclerView recyclerView2;
    DailySelectedAdapter dailyAdapter;
    DailySelectedAdapter specialAdapter;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 1000;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.
    public List<Training> rawList = new ArrayList<Training>();
    ViewPager pager = null;
    ArrayList<View> viewContainter = new ArrayList<View>();
    private boolean haveData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navCreat(R.id.activity_main, "Home");
        pager = (ViewPager) this.findViewById(R.id.main_page_picture);
        viewContainter = new ArrayList<View>();
        View view1 = LayoutInflater.from(this).inflate(R.layout.main_pager1, null);
        View view2 = LayoutInflater.from(this).inflate(R.layout.main_pager2, null);
        View view3 = LayoutInflater.from(this).inflate(R.layout.main_pager3, null);
//        View view4 = LayoutInflater.from(this).inflate(R.layout.tab4, null);
//viewContainter新增view
        viewContainter.add(view1);
        viewContainter.add(view2);
        viewContainter.add(view3);
//        viewContainter.add(view4);
        pager.setAdapter(new MyPagerAdapters());
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                currentPage = pager.getCurrentItem();
                if (currentPage == viewContainter.size() - 1) {
                    currentPage = -1;
                }
                pager.setCurrentItem(++currentPage, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

        recyclerView1 = (RecyclerView) findViewById(R.id.daily_selected_recycler_view);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView2 = (RecyclerView) findViewById(R.id.special_selected_recycler_view);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        SharedPreferences sharedPreferences = getSharedPreferences("Training_Raw_List", Activity.MODE_PRIVATE);
        String stringList = sharedPreferences.getString("KEY_RAW_LIST_DATA", "");
        Gson gson = new Gson();
        if (gson.fromJson(stringList, new TypeToken<List<Training>>() {
        }.getType()) != null) {
            rawList = gson.fromJson(stringList, new TypeToken<List<Training>>() {
            }.getType());
            Log.d("MainActivity", "您已經儲存成功");
            dailyAdapter = new DailySelectedAdapter(getRandom(rawList));
            recyclerView1.setAdapter(dailyAdapter);
            specialAdapter = new DailySelectedAdapter(getRandom(rawList));
            recyclerView2.setAdapter(specialAdapter);
            haveData = true;
        }

        DBReceiver receiver = new DBReceiver() {
            @Override
            public void onReceive(List receivedList) {
                if (!haveData) {
                    saveList(receivedList);
                dailyAdapter = new DailySelectedAdapter(receivedList);
                recyclerView1.setAdapter(dailyAdapter);
                specialAdapter = new DailySelectedAdapter(receivedList);
                recyclerView2.setAdapter(specialAdapter);
                }
            }
        };
        registerReceiver(receiver, new IntentFilter(DBEmcee.ACTION01));
        DBCommand command = new GetListCommand("start_training_list", this, Training.class);
        command.work();

    }

    class MyPagerAdapters extends PagerAdapter {
        //返回可以滑動的VIew的個數
        @Override
        public int getCount() {
            return viewContainter.size();
        }

        //滑動切換的時候銷燬當前的元件
        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            ((ViewPager) container).removeView(viewContainter.get(position));
        }

        //將當前檢視新增到container中並返回當前View檢視
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager) container).addView(viewContainter.get(position));
            return viewContainter.get(position);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
    }

    public List<Training> getRandom(List<Training> rawList) {
        List<Training> tmpList = new ArrayList<Training>();
        for(int i = 0;i<5;i++){
        int random = (int) (Math.random() * (rawList.size() - 1));
        tmpList.add(rawList.get(random));}
        return tmpList;

    }

    public void saveList(List<Training> trainingList) {
        rawList = trainingList;
        SharedPreferences sp = getSharedPreferences("Training_Raw_List", Activity.MODE_PRIVATE);//建立sp物件
        Gson gson = new Gson();
        String jsonStr = gson.toJson(trainingList); //將List轉換成Json
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("KEY_RAW_LIST_DATA", jsonStr); //存入json串
        editor.commit();  //提交
        Log.d("MainActivity", "您已經儲存成功");
    }

}
