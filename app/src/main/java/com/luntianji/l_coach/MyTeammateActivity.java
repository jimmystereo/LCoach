package com.luntianji.l_coach;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.luntianji.l_coach.model.Teammate;


public class MyTeammateActivity extends ThemeManager
        implements MyTeammateFragment.OnListFragmentInteractionListener {

    private static final String TAG = MyTeammateActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_teammate);
    }


    @Override
    public void onClick(Teammate teammate) {
        if (teammate == null ||  teammate.getId() == null) {
            Log.d(TAG, "onClick: null teammte");
            return;
        }
        Intent intent = new Intent(this, MyTeammateEditActivity.class);
        intent.putExtra("Teammate", teammate);
        startActivityForResult(intent, 2);

    }

    /** Called when the user taps the floating action bar */
    public void startMyTeammateEditActivity(View view) {
        Teammate teammate = new Teammate();
        Intent intent = new Intent(this, MyTeammateEditActivity.class);
        intent.putExtra("Teammate", teammate);
        startActivityForResult(intent, 2);
    }

}
