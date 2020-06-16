package com.luntianji.l_coach;


import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import java.util.Arrays;
import java.util.Objects;


public class FilterFragment extends DialogFragment {
    private Spinner spinner_type;
    private Spinner spinner_difficulty;
    private Spinner spinner_other;
    private Spinner spinner_people;
    private Spinner spinner_ball;

    int[] filterData = {0, 0, 0, 0, 0};
    private static final String TAG = "FilterFragment";
    private View v;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_filter, container, false);
        Spinner spinner = (Spinner) v.findViewById(R.id.filter_type);
        spinner_type = setSpinner(R.array.string_filter_type, spinner);
        spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterData[0] = position;
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinner = (Spinner) v.findViewById(R.id.filter_difficulty);
        spinner_difficulty = setSpinner(R.array.string_filter_difficulty, spinner);
        spinner_difficulty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterData[1] = position;
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinner = (Spinner) v.findViewById(R.id.filter_other);
        spinner_other = setSpinner(R.array.string_filter_other, spinner);
        spinner_other.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterData[2] = position;
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinner = (Spinner) v.findViewById(R.id.filter_people);
        spinner_people = setSpinner(R.array.string_filter_people, spinner);
        spinner_people.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterData[3] = position;
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinner = (Spinner) v.findViewById(R.id.filter_ball);
        spinner_ball = setSpinner(R.array.string_filter_ball, spinner);
        spinner_ball.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterData[4] = position;
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private Spinner setSpinner(int array, Spinner spinner) {
        ArrayAdapter<CharSequence> adapter_difficulty = ArrayAdapter.createFromResource(Objects.requireNonNull(this.getActivity()),
                array, R.layout.filter_fold);
        adapter_difficulty.setDropDownViewResource(R.layout.filter_view);
        spinner.setAdapter(adapter_difficulty);
        return spinner;
    }

    public void resetSpinner(int[] filterData) {
        spinner_type.setSelection(filterData[0]);
        spinner_difficulty.setSelection(filterData[1]);
        spinner_other.setSelection(filterData[2]);
        spinner_people.setSelection(filterData[3]);
        spinner_ball.setSelection(filterData[4]);
    }

    public void setFilterData(int[] filterData) {
        this.filterData = filterData;
    }

    public int[] getFilterData() {
        return filterData;
    }
}
