package com.luntianji.l_coach;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luntianji.l_coach.model.Training;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import genomu.command.CreateCommand;
import genomu.firestore_helper.DBCommand;
import genomu.firestore_helper.DBReceiver;

import static genomu.firestore_helper.DBEmcee.ACTION01;

public class StartTrainingActivity extends NavCreater {
    public static boolean opened = false;
    private long fullTime = 1000 * 60 * 15;
    private long timeLeft;
    private boolean start, pause, resume, end = false;
    private RecyclerView recyclerView;
    private TrainingListAdapter trainingListAdapter;
    QuitTrainingFragment quitTrainingFragment;
    private FilterFragment fragment = new FilterFragment();
    private List<Training> tmpList;
    public List rawList = new ArrayList();
    /*type difficulty other people ball**/
    private int[] data = {0, 0, 0, 0, 0};
    private boolean receved = false;
    private static final String TAG = "StartTrainingActivity";
    Button filter;
    Button random_buttom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_training);
        navCreat(R.id.activity_start_training, "Start Training");
        timeLeft = fullTime;
        opened = false;
        filter = (Button) findViewById(R.id.button_training_filter);
        random_buttom = (Button) findViewById(R.id.button_training_random);
        recyclerView = (RecyclerView) findViewById(R.id.start_trainging_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
//        DBReceiver receiver = new DBReceiver() {
//            @Override
//            public void onReceive(List receivedList) {
//                receved = true;

        SharedPreferences sharedPreferences = getSharedPreferences("Training_Raw_List", Activity.MODE_PRIVATE);
        String stringList = sharedPreferences.getString("KEY_RAW_LIST_DATA", "");
        Gson gson = new Gson();
        List<Training> receivedList = gson.fromJson(stringList, new TypeToken<List<Training>>() {
        }.getType());
        rawList.addAll(receivedList);
        tmpList = receivedList;
        trainingListAdapter = new TrainingListAdapter(tmpList, this);
        recyclerView.setAdapter(trainingListAdapter);
//            }
//        };
//        registerReceiver(receiver, new IntentFilter(DBEmcee.ACTION01));
//        DBCommand command = new GetListCommand("start_training_list", this, Training.class);
//        command.work();

    }

    public void getRandom(View view) {
        filter.setBackground(getResources().getDrawable(R.drawable.shape));
        random_buttom.setBackground(getResources().getDrawable(R.drawable.filter_selected_button));
        tmpList.clear();
        for (int i = 0; i < 5; i++) {
            int random = (int) (Math.random() * (rawList.size() - 1));
            tmpList.add((Training) rawList.get(random));
        }
        trainingListAdapter.notifyDataSetChanged();
    }

    public void cancelFilterFragment(View view) {
        fragment.resetSpinner(data);
        fragment.dismiss();
    }

    public void openFilterFragment(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment.setCancelable(false);
        fragment.show(fragmentManager, "FilterFragment");
    }

    public void doneFilterFragment(View v) {
        random_buttom.setBackground(getResources().getDrawable(R.drawable.shape));
        System.arraycopy(fragment.filterData, 0, data, 0, data.length);
        dataSelection();

        if (!checkDefault())
            filter.setBackground(getResources().getDrawable(R.drawable.filter_selected_button));
        else filter.setBackground(getResources().getDrawable(R.drawable.shape));
        fragment.dismiss();
    }

    boolean checkDefault() {
        for (int i : data) {
            if (i != 0) {
                return false;
            }
        }
        return true;
    }

    private void dataSelection() {
        tmpList.clear();
        tmpList.addAll(rawList);
        trainingListAdapter.notifyDataSetChanged();
        Map<Integer, String> type = new HashMap<Integer, String>() {{
            put(1, "整合訓練");
            put(2, "傳球");
            put(3, "發球");
            put(4, "接扣球");
            put(5, "扣球");
        }};
        Map<Integer, String> difficulty = new HashMap<Integer, String>() {{
            put(1, "低");
            put(2, "中");
            put(3, "高");
        }};
        int people = data[3];
        int ball = data[4] - 1;
        for (int i = 0; i < rawList.size(); i++) {
            if (i >= tmpList.size()) {
                break;
            }
            /*篩選訓練種類**/
            if (data[0] != 0 && !type.get(data[0]).equals(tmpList.get(i).getType())) {
                tmpList.remove(i);
                trainingListAdapter.notifyItemRemoved(i);
                i--;
                continue;
            }
            /*篩選難度**/
            if (data[1] != 0 && !difficulty.get(data[1]).equals(tmpList.get(i).getDifficulty())) {
                tmpList.remove(i);
                trainingListAdapter.notifyItemRemoved(i);
                i--;
                continue;
            }
            /*篩選其他條件**/
            if (data[2] == 1) {
                if (tmpList.get(i).getOther().equals("牆壁")) {
                    tmpList.remove(i);
                    trainingListAdapter.notifyItemRemoved(i);
                    i--;
                    continue;
                }
            } else if (data[2] == 2) {
                if (tmpList.get(i).getOther().equals("球場") || tmpList.get(i).getOther().equals("場地")) {
                    tmpList.remove(i);
                    trainingListAdapter.notifyItemRemoved(i);
                    i--;
                    continue;
                }
            } else if (data[2] == 3) {
                if (tmpList.get(i).getOther().equals("球場") || tmpList.get(i).getOther().equals("牆壁") || tmpList.get(i).getOther().equals("場地")) {
                    tmpList.remove(i);
                    trainingListAdapter.notifyItemRemoved(i);
                    i--;
                    continue;
                }
            }
            /*篩選人數**/
            if (people != 0 && people < Integer.parseInt(tmpList.get(i).getLeast_people())) {
                tmpList.remove(i);
                trainingListAdapter.notifyItemRemoved(i);
                i--;
                continue;
            }
            /*篩選球數**/
            if (ball >= 0 && people != 0) {

                double ball_per_people = Double.parseDouble(tmpList.get(i).getBall_per_person());
                double ball_needed = people * ball_per_people;
                if (ball < ball_needed) {
                    tmpList.remove(i);
                    trainingListAdapter.notifyItemRemoved(i);
                    i--;
                    continue;

                }
            }
        }
    }

    public void closeDetail(View view) {
//        AlertDialog.Builder builder1 = new AlertDialog.Builder(this.getBaseContext());
//        builder1.setMessage(R.string.comfirm_quit);
//        builder1.setCancelable(true);
//
//        builder1.setPositiveButton(
//                R.string.comfirm,
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        resetDetail();
//                        dialog.cancel();
//                    }
//                });
//
//        builder1.setNegativeButton(
//                R.string.cancel,
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
//                    }
//                });
//
//        AlertDialog alert11 = builder1.create();
//        alert11.show();
        resetDetail();
    }

    public void comfirmDetail(View view) {

        final TextView cancel = (TextView) findViewById(R.id.back_button);
        final TextView clock = (TextView) findViewById(R.id.training_confirm);
        if (!end) {


            if (!start) {
                cancel.setText("退出訓練");
                start = true;

            } else if (!pause) {
                pause = true;
                clock.setBackgroundResource(R.drawable.comfirm_button_pause);
            } else if (pause) {
                clock.setBackgroundResource(R.drawable.comfirm_button);
                pause = false;
                resume = true;
            }
            CountDownTimer countDownTimer = new CountDownTimer(timeLeft, 1000) {

                public void onTick(long millisUntilFinished) {

                    if (pause || !start) {
                        cancel();
                    } else {
                        int minute = (int) ((millisUntilFinished / 1000) / 60);
                        String minuteS = String.valueOf(minute);
                        int second = (int) ((millisUntilFinished / 1000) % 60 + 1);
                        String secondS = String.valueOf(second);
                        if (minuteS.length() == 1) {
                            minuteS = "0" + minuteS;
                        }
                        if (secondS.length() == 1) {
                            secondS = "0" + secondS;
                        }
                        if (secondS.equals("60")) {
                            secondS = "00";
                            minuteS = String.valueOf(minute + 1);
                        }
                        clock.setText(String.format("%s : %s", minuteS, secondS));
                        timeLeft = millisUntilFinished;
                    }
                }

                public void onFinish() {
                    clock.setText("done!");
                    cancel.setText("返回");
                    end = true;
                    resume = false;
                    start = false;
                    pause = false;
                }
            };
            countDownTimer.start();
        } else {
            resetDetail();
        }

    }

    public void addToMyTraining(View view) {
        // auth
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser == null) {
            createSignInIntent();
            return;
        }
        String userId = firebaseUser.getUid();

        //pojo
        Training training = DailySelectedAdapter.getDetailFragment().training;
        training.setName(training.getName());
        training.setDifficulty(training.getDifficulty());
        training.setOther(training.getOther());
        // need userId
        training.setUserId(userId);

        DBReceiver receiver = new DBReceiver() {
        };
        registerReceiver(receiver, new IntentFilter(ACTION01));
        DBCommand command = new CreateCommand("my_training_list", this, training);
        command.work();
    }

    public void resetDetail() {
        opened = false;
        setTitle("選擇訓練");
        timeLeft = fullTime;
        end = false;
        start = false;
        pause = false;
        resume = false;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.animation_open_fragment, R.anim.animation_close_fragment);
        fragmentTransaction.detach(TrainingListAdapter.getDetailFragment());
        fragmentTransaction.commit();
    }

}