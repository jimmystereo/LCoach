package com.luntianji.l_coach;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.luntianji.l_coach.model.Teammate;

import java.util.List;

import genomu.firestore_helper.DBCommand;
import genomu.firestore_helper.DBReceiver;
import genomu.firestore_helper.HanWen;
import genomu.command.CreateCommand;
import genomu.command.DeleteCommand;
import genomu.command.UpdateCommand;

import static genomu.firestore_helper.DBEmcee.ACTION01;


public class MyTeammateEditActivity extends AppCompatActivity {

    private static final String TAG = "AddTeammateActivity";

    TextView editName;
    TextView editRole;
    TextView editNumber;
    Button buttonAdd;
    Button buttonDelete;

    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_teammate_edit);

        editName = findViewById(R.id.edit_name);
        editRole = findViewById(R.id.edit_role);
        editNumber = findViewById(R.id.edit_number);
        buttonAdd = findViewById(R.id.button_add);
        buttonDelete = findViewById(R.id.button_delete);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getString("UpdateTeammateId");
            editName.setText(bundle.getString("UpdateTeammateName"));
            editRole.setText(bundle.getString("UpdateTeammateRole"));
            editNumber.setText(bundle.getString("UpdateTeammateNumber"));

        }

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString();
                String role = editRole.getText().toString();
                String number = editNumber.getText().toString();

                if (editName.length() > 0) {
                    if (id.length() > 0) {
                        updateTeammate(id, name, role, number);
                        setResult(RESULT_OK);
                    } else {
                        addTeammate(name, role, number);
                    }
                }

                finish();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString();
                String role = editRole.getText().toString();
                String number = editNumber.getText().toString();

                deleteTeammate(id, name, role, number);
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    private void updateTeammate(String id, String name, String role, String number) {
        Teammate teammate = new Teammate(id, name, role, number);

        DBReceiver receiver = new DBReceiver() {

        };
        registerReceiver(receiver, new IntentFilter(ACTION01));
        DBCommand command = new UpdateCommand<>("teammates", this, teammate);
        command.work();

//        firestoreDB.collection("teammates")
//                .document(id)
//                .set(teammate)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Log.e(TAG, "Teammate document update successful!");
//                        Toast.makeText(getApplicationContext(), "Teammate has been updated!", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.e(TAG, "Error adding Teammate document", e);
//                        Toast.makeText(getApplicationContext(), "Teammate could not be updated!", Toast.LENGTH_SHORT).show();
//                    }
//                });
    }

    private void addTeammate(String name, String role, String number) {

        Teammate teammate = new Teammate(name, role, number);

        DBReceiver receiver = new DBReceiver() {
        };
        registerReceiver(receiver, new IntentFilter(ACTION01));
        DBCommand command = new CreateCommand("teammates", this,  teammate);
        command.work();
    }

    private void deleteTeammate(String id, String name, String role, String number) {

        Teammate teammate = new Teammate(id, name, role, number);

        DBReceiver receiver = new DBReceiver() {
        };
        registerReceiver(receiver, new IntentFilter(ACTION01));
        DBCommand command = new DeleteCommand<>("teammates", this,  teammate);
        command.work();
    }
}