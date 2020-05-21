package com.luntianji.l_coach;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


public class TrainingDetailFragment extends Fragment {
    private int itemPosition;
    private final String TAG = "TrainingDetailFragment";

    public TrainingDetailFragment(int position) {
        itemPosition = position;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        TextView textView = (TextView) container.findViewById(R.id.training_detail_name);
//        textView.setText("itemPosition");
        return inflater.inflate(R.layout.fragment_training_detail, container, false);

    }

}
