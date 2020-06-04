package com.luntianji.l_coach;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.luntianji.l_coach.databinding.ActivityMyTeammateEditBinding;
import com.luntianji.l_coach.model.Teammate;

import genomu.firestore_helper.DBCommand;
import genomu.firestore_helper.DBReceiver;
import genomu.command.CreateCommand;
import genomu.command.DeleteCommand;
import genomu.command.UpdateCommand;

import static genomu.firestore_helper.DBEmcee.ACTION01;


public class MyTeammateEditActivity extends AppCompatActivity {

    private static final String TAG = "AddTeammateActivity";

    // binding
    private ActivityMyTeammateEditBinding mBinding;
    private Teammate teammate;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userId = user.getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // binding
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_my_teammate_edit);
        teammate = (Teammate) getIntent().getSerializableExtra("Teammate");
        mBinding.setTeammate(teammate);

        if (teammate.getId() != null) {
            mBinding.setEdit(false);
        } else {
            mBinding.setCreate(true);
        }

        mBinding.buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTeammate();
            }
        });
        mBinding.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTeammate();
            }
        });
    }

    public void onToggleEditClick(View view) {
        if (mBinding.getEdit()) {
            save();
        }
        mBinding.setEdit(!mBinding.getEdit());
    }

    private void save() {
        if (teammate.getName().length() > 0) {
            if (teammate.getId().length() > 0) {
                updateTeammate();
            } else {
                Toast.makeText(this, "id is null", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void updateTeammate() {
        teammate.setUserId(userId);
        DBReceiver receiver = new DBReceiver() {

        };
        registerReceiver(receiver, new IntentFilter(ACTION01));
        DBCommand command = new UpdateCommand<>("my_teammates", this, teammate);
        command.work();
        setResult(RESULT_OK);
    }

    private void addTeammate() {
        teammate.setUserId(userId);
        DBReceiver receiver = new DBReceiver() {
            @Override
            public void onReceive(Object receivedPOJO) {
                super.onReceive(receivedPOJO);
                Teammate newTeammate = (Teammate) receivedPOJO;
                teammate = newTeammate;
                mBinding.setCreate(false);
                mBinding.setEdit(false);
            }
        };
        registerReceiver(receiver, new IntentFilter(ACTION01));
        DBCommand command = new CreateCommand("my_teammates", this, teammate);
        command.work();
        mBinding.setCreate(false);
    }

    private void deleteTeammate() {
        DBReceiver receiver = new DBReceiver() {
        };
        registerReceiver(receiver, new IntentFilter(ACTION01));
        DBCommand command = new DeleteCommand<>("my_teammates", this, teammate);
        command.work();
        setResult(RESULT_OK);
        finish();
    }
}