package com.luntianji.l_coach;

import android.app.Activity;
import android.os.Build;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.luntianji.l_coach.model.Training;

import java.util.List;

import static com.luntianji.l_coach.TrainingDetailFragment.opened;


public class DailySelectedAdapter extends RecyclerView.Adapter<DailySelectedAdapter.TrainingListViewHolder> {
    private List<Training> trainingDataset;
    private static List<Training> specialDataset;
    static TrainingDetailFragment detailFragment;
    private String data;
    AppCompatActivity activity;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class TrainingListViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;
        ConstraintLayout constraintLayout;
        TextView trainingName;
        TextView trainingType;
        TextView trainingDifficulty;
        MaterialCardView cardView;

        public TrainingListViewHolder(View v) {
            super(v);
            view = v;
            constraintLayout = (ConstraintLayout) view.findViewById(R.id.daily_selected_constraint);
            cardView = (MaterialCardView) view.findViewById(R.id.fragment_daily_selected);
            trainingName = (TextView) view.findViewById(R.id.daily_selected_name);
            trainingType = (TextView) view.findViewById(R.id.daily_selected_type);
            trainingDifficulty = (TextView) view.findViewById(R.id.daily_selected_difficulty);
            MainActivity.setPosition(getAdapterPosition());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!opened) {

                        detailFragment = new TrainingDetailFragment(getTrainingDataSet().get(getAdapterPosition()),activity,0);

                        FragmentManager fragmentManager = activity.getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.setCustomAnimations(R.anim.animation_open_fragment, R.anim.animation_close_fragment);
                        fragmentTransaction.add(R.id.main_constraint, detailFragment);
                        activity.setTitle("訓練內容");
                        fragmentTransaction.commit();
                        opened = true;
                    }
                }
            });
        }
    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public DailySelectedAdapter(List<Training> myDataset, AppCompatActivity activity) {
        trainingDataset = myDataset;
        this.activity = activity;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public TrainingListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_daily_selected, parent, false);

        TrainingListViewHolder trainingListViewHolder = new TrainingListViewHolder(v);
        return trainingListViewHolder;
    }


    // Replace the contents of a view (invoked by the layout manager)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(TrainingListViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        String difficulty = trainingDataset.get(position).getDifficulty();
        TypedValue typedValue = new TypedValue();
        int color;
        switch (difficulty){
            case "低":
                holder.constraintLayout.setBackground(activity.getDrawable(R.drawable.ripple_transparent));
                activity.getTheme().resolveAttribute(R.attr.training_easy, typedValue, true);
                break;
            case "中":
                holder.constraintLayout.setBackground(activity.getDrawable(R.drawable.ripple_yellow));
                activity.getTheme().resolveAttribute(R.attr.training_medium, typedValue, true);
                break;
            case "高":
                holder.constraintLayout.setBackground(activity.getDrawable(R.drawable.ripple_red));
                activity.getTheme().resolveAttribute(R.attr.training_hard, typedValue, true);
                break;
        }
        color = ContextCompat.getColor(activity, typedValue.resourceId);
        holder.cardView.setStrokeColor(color);
        holder.trainingName.setText(trainingDataset.get(position).getName());
        holder.trainingType.setText(trainingDataset.get(position).getType());
        holder.trainingDifficulty.setText(String.format("難度: %s", trainingDataset.get(position).getDifficulty()));


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return trainingDataset.size();
    }

    //        public void dataSelection(boolean defaultData, int[] filterData){
//            if(!defaultData){
//
//            }
//        }
    public List<Training> getTrainingDataSet() {
        return trainingDataset;
    }

    public static TrainingDetailFragment getDetailFragment() {
        return detailFragment;
    }
}