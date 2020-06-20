package com.luntianji.l_coach;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.luntianji.l_coach.MyTeammateFragment.OnListFragmentInteractionListener;
import com.luntianji.l_coach.model.Teammate;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyTeammateRecyclerViewAdapter extends RecyclerView.Adapter<MyTeammateRecyclerViewAdapter.ViewHolder> {

    private final List<Teammate> mValues;
    private Context context;

    private final OnListFragmentInteractionListener mListener;

    public MyTeammateRecyclerViewAdapter(List<Teammate> items, OnListFragmentInteractionListener listener) {
        mValues = items;

        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_my_teammate, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem =  mValues.get(position);
        holder.mNameView.setText(mValues.get(position).getName());
        holder.mRoleView.setText(mValues.get(position).getRole());
        holder.mNumberView.setText(mValues.get(position).getNumber());

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
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNameView;
        public final TextView mRoleView;
        public final TextView mNumberView;

        public Teammate mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = (TextView) view.findViewById(R.id.teammate_name);
            mRoleView = (TextView) view.findViewById(R.id.teammate_role);
            mNumberView = (TextView) view.findViewById(R.id.teammate_number);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }



}
