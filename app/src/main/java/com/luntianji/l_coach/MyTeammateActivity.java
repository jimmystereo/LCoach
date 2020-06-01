package com.luntianji.l_coach;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.luntianji.l_coach.model.Teammate;

import java.util.List;

import genomu.firestore_helper.DBCommand;
import genomu.firestore_helper.DBReceiver;
import genomu.firestore_helper.HanWen;
import genomu.command.GetListCommand;

import static genomu.firestore_helper.DBEmcee.ACTION01;


public class MyTeammateActivity extends AppCompatActivity
        implements MyTeammateFragment.OnListFragmentInteractionListener {

    private static final String TAG = MyTeammateActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(SettingActivity.newTheme){setTheme(R.style.RedTheme);}
        else{setTheme(R.style.Theme_MyApp);}
        setContentView(R.layout.activity_my_teammate);
    }


    @Override
    public void onClick(Teammate teammate) {
        if (teammate == null ||  teammate.getId() == null) {
            Log.d(TAG, "onClick: null teammte");
            return;
        }
        Intent intent = new Intent(this, MyTeammateEditActivity.class);
        intent.putExtra("UpdateTeammateId", teammate.getId());
        intent.putExtra("UpdateTeammateName", teammate.getName());
        intent.putExtra("UpdateTeammateRole", teammate.getRole());
        intent.putExtra("UpdateTeammateNumber", teammate.getNumber());
        intent.putExtra("UpdateTeammateInfo", teammate.getInfo());
        startActivityForResult(intent, 2);

    }

    /** Called when the user taps the floating action bar */
    public void startMyTeammateEditActivity(View view) {
        Intent intent = new Intent(this, MyTeammateEditActivity.class);
        startActivityForResult(intent, 2);
    }

}
