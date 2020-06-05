package com.luntianji.l_coach;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.TooltipCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luntianji.l_coach.model.DetailManager;
import com.luntianji.l_coach.model.Training;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import genomu.command.CreateCommand;
import genomu.command.GetListCommand;
import genomu.firestore_helper.DBCommand;
import genomu.firestore_helper.DBEmcee;
import genomu.firestore_helper.DBReceiver;

import static com.luntianji.l_coach.TrainingDetailFragment.opened;
import static genomu.firestore_helper.DBEmcee.ACTION01;

public class MainActivity extends DetailManager {
    private static int position;
    private int notificationNum = 0;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        opened = false;
        if(SettingActivity.newTheme){setTheme(R.style.RedTheme);}
        else{setTheme(R.style.Theme_MyApp);}
        setContentView(R.layout.activity_main);
        navCreat(R.id.activity_main, "Home");
        createNotificationChannel();
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
            dailyAdapter = new DailySelectedAdapter(getRandom(rawList), this);
            recyclerView1.setAdapter(dailyAdapter);
            specialAdapter = new DailySelectedAdapter(dataSelect(rawList,"高"), this);
            recyclerView2.setAdapter(specialAdapter);
            DBReceiver receiver = new DBReceiver() {
                @Override
                public void onReceive(List receivedList) {

                    saveList(receivedList);
                    unregisterReceiver(this);
                }
            };
            registerReceiver(receiver, new IntentFilter(DBEmcee.ACTION01));
            DBCommand command = new GetListCommand("start_training_list", this, Training.class);
            command.work();
        } else {
            DBReceiver receiver = new DBReceiver() {
                @Override
                public void onReceive(List receivedList) {

                    saveList(receivedList);
                    dailyAdapter = new DailySelectedAdapter(getRandom(receivedList), MainActivity.this);
                    recyclerView1.setAdapter(dailyAdapter);
                    specialAdapter = new DailySelectedAdapter(dataSelect(receivedList,"高"), MainActivity.this);
                    recyclerView2.setAdapter(specialAdapter);

                    unregisterReceiver(this);
                }
            };
            registerReceiver(receiver, new IntentFilter(DBEmcee.ACTION01));
            DBCommand command = new GetListCommand("start_training_list", this, Training.class);
            command.work();
        }
        disableSeekBar();

    }

    void disableSeekBar() {
        SeekBar ballSeekBar = this.findViewById(R.id.ball_seekbar);
        TooltipCompat.setTooltipText(ballSeekBar, "Send an email");
        ballSeekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
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
        for (int i = 0; i < 5; i++) {
            int random = (int) (Math.random() * (rawList.size() - 1));
            tmpList.add(rawList.get(random));
        }
        return tmpList;

    }

    public List<Training> dataSelect(List<Training> rawList, String dataLevel) {
        List<Training> tmpList = new ArrayList<Training>();

        for (Training t : rawList) {
            if (t.getDifficulty().equals(dataLevel)) {
                tmpList.add(t);
            }
        }
        return tmpList;

    }

    public void saveList(List<Training> trainingList) {

        rawList = trainingList;
        SharedPreferences sp = getSharedPreferences("Training_Raw_List", Activity.MODE_PRIVATE);//建立sp物件
        Gson gson = new Gson();
        String jsonStr = gson.toJson(trainingList); //將List轉換成Json
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.putString("KEY_RAW_LIST_DATA", jsonStr); //存入json串
        editor.apply();  //提交
    }


    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.app_name);
            String description = getString(R.string.app_name);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("訓練完成通知", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static void setPosition(int position){
        MainActivity.position = position;
    }

}
