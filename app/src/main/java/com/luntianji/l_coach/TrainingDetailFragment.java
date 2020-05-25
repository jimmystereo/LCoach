package com.luntianji.l_coach;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luntianji.l_coach.model.Training;

import java.util.List;


public class TrainingDetailFragment extends Fragment {
    private int itemPosition;
    public Training training;
    private final String TAG = "TrainingDetailFragment";

    public TrainingDetailFragment(int position, Training training) {
        itemPosition = position;
        this.training = training;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_training_detail, container, false);
        TextView trainingName = (TextView) v.findViewById(R.id.training_detail_name);
        trainingName.setText(training.getName());
        TextView trainingContent = (TextView) v.findViewById(R.id.training_detail_content);
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

}
