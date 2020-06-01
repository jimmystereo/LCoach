package com.luntianji.l_coach;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.luntianji.l_coach.model.Teammate;

import java.util.List;

import genomu.command.FilterCommand;
import genomu.firestore_helper.DBCommand;
import genomu.firestore_helper.DBReceiver;
import genomu.firestore_helper.HanWen;
import genomu.command.GetListCommand;

import static android.app.Activity.RESULT_OK;
import static genomu.firestore_helper.DBEmcee.ACTION01;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class MyTeammateFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 2;
    private OnListFragmentInteractionListener mListener;

    private RecyclerView recyclerView;
    private Context context;
    private RecyclerView.Adapter madapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MyTeammateFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MyTeammateFragment newInstance(int columnCount) {
        MyTeammateFragment fragment = new MyTeammateFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_teammate_list, container, false);


        // Set the adapter
        if (view instanceof RecyclerView) {
            this.context = view.getContext();
            this.recyclerView = (RecyclerView) view;

            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            recyclerView.setHasFixedSize(true);

            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            // apply spacing
            final int spacing = getResources().getDimensionPixelSize(R.dimen.recycler_spacing) / 2;
            recyclerView.setPadding(spacing, spacing, spacing, spacing);
            recyclerView.setClipToPadding(false);
            recyclerView.setClipChildren(false);
            recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    outRect.set(spacing, spacing, spacing, spacing);
                }
            });
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // update whatever your list
        // get data
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid =  user.getUid();
        DBReceiver receiver = new DBReceiver() {
            @Override
            public void onReceive(List receivedList) {
                // specify an adapter (see also next example)
                recyclerView.setAdapter(new MyTeammateRecyclerViewAdapter(receivedList, mListener));
                context.unregisterReceiver(this);
            }
        };
        context.registerReceiver(receiver, new IntentFilter(ACTION01));
        GetListCommand command = new GetListCommand("my_teammates", (Activity) context, Teammate.class);
        command = new FilterCommand(command, "userId", uid);
        command.work();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onClick(Teammate teammate);
    }

}
