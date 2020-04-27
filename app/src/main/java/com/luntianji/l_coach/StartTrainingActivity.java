package com.luntianji.l_coach;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.luntianji.data_matters.Training;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StartTrainingActivity extends NavCreater {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_training);
        RecyclerView recyclerView = findViewById(R.id.training_list);

        navCreat(R.id.activity_start_training, "Start Training");
        List<Training> trainings = new ArrayList<>();
        Map<String,Training> trainingMap = new HashMap<>();
        trainingMap.put("WERTWERSDVAE121",Training.dummy);
        trainings.add(Training.dummy);
        trainings.get(0);
    }
    public class TrainingAdater extends RecyclerView.Adapter<TrainingAdater.TrainingViewHolder>{
        private List list;
        @NonNull
        @Override
        public TrainingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull TrainingViewHolder holder, int position) {

    }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class TrainingViewHolder extends RecyclerView.ViewHolder{
            TextView text;

            public TrainingViewHolder(@NonNull View itemView) {
                super(itemView);
                //text = itemView.findViewById(123123);
            }
        }
    }
}
