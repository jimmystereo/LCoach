package com.luntianji.l_coach;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.luntianji.data.Training;

import java.util.List;

public class TrainingListAdapter extends RecyclerView.Adapter<TrainingListAdapter.TrainingListViewHolder> {
    private List<Training> trainingDataset;
    private  String data;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class TrainingListViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;
        public TextView trainingName;
        public TextView trainingPreview;
        public TrainingListViewHolder(View v) {
            super(v);
            view = v;
            trainingName = (TextView) view.findViewById(R.id.training_name);
            trainingPreview = (TextView) view.findViewById(R.id.training_preview);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TrainingListAdapter(List<Training> myDataset) {
        trainingDataset = myDataset;
    }
    public TrainingListAdapter(String myDataset) {
        data = myDataset;
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
        holder.trainingName.setText(trainingDataset.get(position).getName());
        holder.trainingPreview.setText(String.format("最少人數: %d人", trainingDataset.get(position).getLeast_people()));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return 1;
    }
}
