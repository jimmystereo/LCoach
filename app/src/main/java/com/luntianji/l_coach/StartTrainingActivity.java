package com.luntianji.l_coach;

import android.app.Activity;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
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

public class StartTrainingActivity extends DetailManager {

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
    Button activating_button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_training);
        navCreat(R.id.activity_start_training, "Start Training");
        TrainingDetailFragment.opened = false;
        activating_button = findViewById(R.id.activating_button);
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
        activating_button.setBackground(getResources().getDrawable(R.drawable.shape));
        filter.setBackground(getResources().getDrawable(R.drawable.shape));
        random_buttom.setBackground(getResources().getDrawable(R.drawable.filter_selected_button));
        tmpList.clear();
        for (int i = 0; i < 5; i++) {
            int random = (int) (Math.random() * (rawList.size() - 1));
            tmpList.add((Training) rawList.get(random));
        }
        trainingListAdapter.notifyDataSetChanged();
    }

    public void getActivating(View view) {
        random_buttom.setBackground(getResources().getDrawable(R.drawable.shape));
        activating_button.setBackground(getResources().getDrawable(R.drawable.filter_selected_button));
        filter.setBackground(getResources().getDrawable(R.drawable.shape));

        tmpList.clear();
        if (TrainingDetailFragment.activatingTraining != null) {
            for (Training i : (ArrayList<Training>) rawList) {
                if (i.getName().equals(TrainingDetailFragment.activatingTraining.getName())) {
                    tmpList.add(i);
                }
            }
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
        activating_button.setBackground(getResources().getDrawable(R.drawable.shape));
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

    @Override
    public void addToMyTraining(View view) {
        // auth
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser == null) {
            createSignInIntent();
            return;
        }
        String userId = firebaseUser.getUid();

        //pojo

        Training training = TrainingListAdapter.getDetailFragment().training;
        // need userId
        training.setUserId(userId);

        DBReceiver receiver = new DBReceiver() {
        };
        registerReceiver(receiver, new IntentFilter(ACTION01));
        DBCommand command = new CreateCommand("my_training_list", this, training);
        command.work();
    }
}