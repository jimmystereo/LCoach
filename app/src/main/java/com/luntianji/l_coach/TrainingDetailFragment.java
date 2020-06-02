package com.luntianji.l_coach;

import android.app.Activity;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luntianji.l_coach.model.Training;

import java.util.Arrays;
import java.util.List;

import genomu.command.CreateCommand;
import genomu.firestore_helper.DBCommand;
import genomu.firestore_helper.DBReceiver;

import static com.luntianji.l_coach.NavCreater.RC_SIGN_IN;
import static genomu.firestore_helper.DBEmcee.ACTION01;


public class TrainingDetailFragment extends Fragment {
    public Training training;
    static int activityType;
    public static boolean opened = false;
    private static long fullTime = 1000 * 60 * 15;
    private static long timeLeft;
    private static boolean start;
    private static boolean pause;
    private static boolean resume;
    private static boolean end = false;
    View v;
    private final String TAG = "TrainingDetailFragment";
    static AppCompatActivity activity;
    static TextView cancel;
    static TextView clock;

    public TrainingDetailFragment(Training training, AppCompatActivity activit, int activityTyp) {
        //第三個參數要傳入type 如果是自訂菜單傳2 要新增的話下面有switch的也要新增
        this.training = training;
        activityType = activityTyp;
        activity = activit;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        timeLeft = fullTime;
        v = inflater.inflate(R.layout.fragment_training_detail, container, false);
        TextView trainingName = v.findViewById(R.id.training_detail_name);
        cancel = v.findViewById(R.id.back_button);
        clock = v.findViewById(R.id.training_confirm);
        trainingName.setText(training.getName());
        TextView trainingContent = v.findViewById(R.id.training_detail_content);
        String content = String.format(
                "訓練種類: %s%n" +
                        "訓練難度: %s%n" +
                        "人數: %s%n" +
                        "球數: %s%n" +
                        "特殊條件: %s%n"
                , training.getType(), training.getDifficulty(), training.getAmount_people(), training.getAmount_ball(), training.getOther());
        if (!(training.getDetail() == null || training.getDetail().equals("無"))) {
            content += String.format("訓練內容: %n%s%n", training.getDetail());
        }
        trainingContent.setText(content);
        v.setClickable(true);
        return v;
    }

    public static void comfirmDetail() {

        if (!end) {


            if (!start) {
                cancel.setText("退出訓練");
                start = true;

            } else if (!pause) {
                pause = true;
                clock.setBackgroundResource(R.drawable.comfirm_button_pause);
            } else if (pause) {
                clock.setBackgroundResource(R.drawable.comfirm_button_new);
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

    public static void resetDetail() {
        opened = false;
        timeLeft = fullTime;
        end = false;
        start = false;
        pause = false;
        resume = false;
        switch (activityType) {
            case 0:
                activity.setTitle("L Coach");
                break;
            case 1:
                activity.setTitle("選擇訓練");
                break;
            case 2:
                activity.setTitle("自訂菜單");
                break;
        }


        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.animation_open_fragment, R.anim.animation_close_fragment);
        switch (activityType) {
            case 0:
                fragmentTransaction.detach(DailySelectedAdapter.getDetailFragment());
                break;
            case 1:
                fragmentTransaction.detach(TrainingListAdapter.getDetailFragment());
                break;
            case 2:
                //這裡要取得你的activity上的這個fragment
//                fragmentTransaction.detach(MyTrainingRecyclerViewAdapter.getDetailFragment());
                break;
        }
        fragmentTransaction.commit();
    }
//    public static void addToMyTraining(){
//        // auth
//        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        if (firebaseUser == null) {
//            createSignInIntent();
//            return;
//        }
//        String userId = firebaseUser.getUid();
//
//        //pojo
//        Training training = TrainingListAdapter.getDetailFragment().training;
//        // need userId
//        training.setUserId(userId);
//
//        DBReceiver receiver = new DBReceiver() {
//        };
//        activity.registerReceiver(receiver, new IntentFilter(ACTION01));
//        DBCommand command = new CreateCommand("my_training_list", activity, training);
//        command.work();
//    }
//    void createSignInIntent() {
//        // [START auth_fui_create_intent]
//        // Choose authentication providers
//        List<AuthUI.IdpConfig> providers = Arrays.asList(
//                new AuthUI.IdpConfig.EmailBuilder().build(),
//                new AuthUI.IdpConfig.GoogleBuilder().build());
//
//        // Create and launch sign-in intent
//        startActivityForResult(
//                AuthUI.getInstance()
//                        .createSignInIntentBuilder()
//                        .setAvailableProviders(providers)
//                        .setLogo(R.drawable.ball)      // Set logo drawable
//                        .setTheme(R.style.Theme_MyApp)      // Set theme
//                        .build(),
//                RC_SIGN_IN);
//        // [END auth_fui_create_intent]
//    }
}
