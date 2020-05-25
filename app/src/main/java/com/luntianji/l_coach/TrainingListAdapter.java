package com.luntianji.l_coach;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.luntianji.l_coach.model.Training;

import java.util.List;

import static com.luntianji.l_coach.StartTrainingActivity.opened;

public class TrainingListAdapter extends RecyclerView.Adapter<TrainingListAdapter.TrainingListViewHolder> {
    private static List<Training> trainingDataset;
    public static int indexName;
    public Training currentTraining;
    static TrainingDetailFragment detailFragment;
    Activity activity;
    private FragmentManager fragmentManager;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    public static class TrainingListViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TrainingDetailFragment fragment;
        AppCompatActivity activity;
        public View view;
        TextView trainingName;
        TextView trainingPreview;
        MaterialCardView cardView;
        public TrainingListViewHolder(View v) {
            super(v);
            view = v;
            cardView = (MaterialCardView) view.findViewById(R.id.cardView4);
            trainingName = (TextView) view.findViewById(R.id.training_name);
            trainingPreview = (TextView) view.findViewById(R.id.training_preview);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(view.getContext(),
//                            "click " +getAdapterPosition(),Toast.LENGTH_SHORT).show();
                    if (!opened) {
                        detailFragment = new TrainingDetailFragment(getAdapterPosition(), (Training) TrainingListAdapter.getTrainingDataSet().get(getAdapterPosition()));
                        activity = (AppCompatActivity) view.getContext();
                        FragmentManager fragmentManager = activity.getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.setCustomAnimations(R.anim.animation_open_fragment, R.anim.animation_close_fragment);
                        fragmentTransaction.add(R.id.start_training_constraint, detailFragment);
                        activity.setTitle("訓練內容");
                        fragmentTransaction.commit();
                        opened = true;
                    }
                }
            });

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TrainingListAdapter(List<Training> myDataset, Activity activity) {
        trainingDataset = myDataset;
        this.activity = activity;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TrainingListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_training_list_item, parent, false);
        TrainingListViewHolder vh = new TrainingListViewHolder(v);
        return vh;
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(TrainingListViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        String difficulty = trainingDataset.get(position).getDifficulty();

        switch (difficulty) {
            case "低":
                holder.cardView.setStrokeColor(activity.getResources().getColor(R.color.training_easy));
                break;
            case "中":
                holder.cardView.setStrokeColor(activity.getResources().getColor(R.color.training_medium));
                break;
            case "高":
                holder.cardView.setStrokeColor(activity.getResources().getColor(R.color.training_hard));
                break;
        }

        holder.trainingName.setText(trainingDataset.get(position).getName());
        holder.trainingPreview.setText(String.format("難度: %s / 特殊條件: %s", trainingDataset.get(position).getDifficulty(), trainingDataset.get(position).getOther()));

    }

    // Return the size of your dataset (invoked by the layout manager)

    public static TrainingDetailFragment getDetailFragment() {
        return detailFragment;
    }

    @Override
    public int getItemCount() {
        return trainingDataset.size();
    }

    public static List<Training> getTrainingDataSet() {
        return trainingDataset;
    }
}