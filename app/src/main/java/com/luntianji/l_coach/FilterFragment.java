package com.luntianji.l_coach;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;


public class FilterFragment extends DialogFragment implements AdapterView.OnItemSelectedListener {
    //    public View creatSpinner(LayoutInflater inflater,ViewGroup container,View v,Layout layout,int arrayId,int id){
//        View v = inflater.inflate(R.layout.fragment_filter, container, false);
//        Spinner spinner = (Spinner) v.findViewById(R.id.filter_type);
//// Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
//                R.array.string_filter_type, R.layout.filter_fold);
//// Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(R.layout.filter_view);
//// Apply the adapter to the spinner
//
//        spinner.setAdapter(adapter);
//        spinner.setOnItemSelectedListener(this);
//    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_filter, container, false);
        Spinner spinner = (Spinner) v.findViewById(R.id.filter_type);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.string_filter_type, R.layout.filter_fold);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.filter_view);
// Apply the adapter to the spinner

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        return v;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this.getActivity(), parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
