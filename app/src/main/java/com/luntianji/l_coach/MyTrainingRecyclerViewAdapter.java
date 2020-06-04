package com.luntianji.l_coach;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.luntianji.l_coach.MyTrainingFragment.OnListFragmentInteractionListener;
import com.luntianji.l_coach.model.Training;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Training} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyTrainingRecyclerViewAdapter extends RecyclerView.Adapter<MyTrainingRecyclerViewAdapter.ViewHolder> {

    private final List<Training> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyTrainingRecyclerViewAdapter(List<Training> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_training_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem =  mValues.get(position);
        holder.trainingName.setText(mValues.get(position).getName());
        holder.trainingPreview.setText(String.format("難度: %s / 特殊條件: %s", mValues.get(position).getDifficulty(), mValues.get(position).getOther()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onClick(holder.mItem);
                }
            }
        });

        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onLongClick(holder.mItem);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView trainingName;
        public final TextView trainingPreview;
        public Training mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            trainingName = (TextView) view.findViewById(R.id.training_name);
            trainingPreview = (TextView) view.findViewById(R.id.training_preview);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + trainingPreview.getText() + "'";
        }
    }
}
