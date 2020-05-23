package com.luntianji.l_coach;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.luntianji.l_coach.model.Training;

import java.util.List;

public class DailySelectedAdapter extends RecyclerView.Adapter<DailySelectedAdapter.TrainingListViewHolder> {
    private static List<Training> trainingDataset;
    static  TrainingDetailFragment detailFragment;
    private  String data;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class TrainingListViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;
        public TextView trainingName;
        public TextView trainingType;
        public TextView trainingDifficulty;
        public TrainingListViewHolder(View v) {
            super(v);
            view = v;
            trainingName = (TextView) view.findViewById(R.id.daily_selected_name);
            trainingType = (TextView) view.findViewById(R.id.daily_selected_type);
            trainingDifficulty = (TextView) view.findViewById(R.id.daily_selected_difficulty);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(view.getContext(),
//                            "click " +getAdapterPosition(),Toast.LENGTH_SHORT).show();
                    detailFragment = new TrainingDetailFragment(getAdapterPosition(), (Training) DailySelectedAdapter.getTrainingDataSet().get(getAdapterPosition()));
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.anim.animation_open_fragment, R.anim.animation_close_fragment);
                    fragmentTransaction.add(R.id.main_constraint, detailFragment);
                    fragmentTransaction.commit();
                }
            });
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public DailySelectedAdapter(List<Training> myDataset) {
        trainingDataset = myDataset;
    }
    // Create new views (invoked by the layout manager)
    @Override
    public TrainingListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_daily_selected, parent, false);

        TrainingListViewHolder vh = new TrainingListViewHolder(v);
        return vh;
    }



    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(TrainingListViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.trainingName.setText(trainingDataset.get(position).getName());
        holder.trainingType.setText(trainingDataset.get(position).getType());
        holder.trainingType.setText(String.format("難度:%n%s", trainingDataset.get(position).getDifficulty()));


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
    public static List<Training> getTrainingDataSet() {
        return trainingDataset;
    }
    public static TrainingDetailFragment getDetailFragment() {
        return detailFragment;
    }
}
