package com.luntianji.l_coach;

import android.os.Build;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.luntianji.l_coach.model.Training;

import java.util.List;

import static com.luntianji.l_coach.TrainingDetailFragment.opened;


public class TrainingListAdapter extends RecyclerView.Adapter<TrainingListAdapter.TrainingListViewHolder> {
    private static List<Training> trainingDataset;
    public static int indexName;
    public Training currentTraining;
    static TrainingDetailFragment detailFragment;
    AppCompatActivity activity;
    private FragmentManager fragmentManager;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    public class TrainingListViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TrainingDetailFragment fragment;
        public View view;
        TextView trainingName;
        TextView trainingPreview;
        MaterialCardView cardView;
        ImageView trainingIcon;
        ConstraintLayout trainingItem;
        public TrainingListViewHolder(View v) {
            super(v);
            view = v;
            trainingItem = view.findViewById(R.id.trainingItem);
            trainingIcon = view.findViewById(R.id.imageView);
            cardView = (MaterialCardView) view.findViewById(R.id.cardView4);
            trainingName = (TextView) view.findViewById(R.id.training_name);
            trainingPreview = (TextView) view.findViewById(R.id.training_preview);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!opened) {
                        detailFragment = new TrainingDetailFragment((Training) TrainingListAdapter.getTrainingDataSet().get(getAdapterPosition()),activity,1);
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
    public TrainingListAdapter(List<Training> myDataset, AppCompatActivity activity) {
        trainingDataset = myDataset;
        this.activity = activity;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TrainingListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_training_list_item, parent, false);
        return new TrainingListViewHolder(v);
    }


    // Replace the contents of a view (invoked by the layout manager)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(TrainingListViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        String difficulty = trainingDataset.get(position).getDifficulty();
        String type = trainingDataset.get(position).getType();
        switch (type) {
            case "扣球":
                holder.trainingIcon.setImageResource(R.drawable.icon_spike);
                break;
            case "接扣球":
                holder.trainingIcon.setImageResource(R.drawable.icon_catch);
                break;
            case "發球":
                holder.trainingIcon.setImageResource(R.drawable.icon_fa);
                break;
            case "傳球":
                holder.trainingIcon.setImageResource(R.drawable.icon_pass);
                break;
            case "舉球":
                holder.trainingIcon.setImageResource(R.drawable.icon_set);
                break;
            case "自由":
                holder.trainingIcon.setImageResource(R.drawable.icon_free);
                break;
            case "整合訓練":
                holder.trainingIcon.setImageResource(R.drawable.icon_all);
                break;
            case "攔網":
                holder.trainingIcon.setImageResource(R.drawable.icon_block);
                break;
        }
        TypedValue typedValue = new TypedValue();
        int color;
        switch (difficulty){
            case "低":
                holder.trainingItem.setBackground(activity.getDrawable(R.drawable.ripple_transparent));
                activity.getTheme().resolveAttribute(R.attr.training_easy, typedValue, true);
                break;
            case "中":
                holder.trainingItem.setBackground(activity.getDrawable(R.drawable.ripple_yellow));
                activity.getTheme().resolveAttribute(R.attr.training_medium, typedValue, true);
                break;
            case "高":
                holder.trainingItem.setBackground(activity.getDrawable(R.drawable.ripple_red));
                activity.getTheme().resolveAttribute(R.attr.training_hard, typedValue, true);
                break;
        }
        color = ContextCompat.getColor(activity, typedValue.resourceId);
        holder.cardView.setStrokeColor(color);
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