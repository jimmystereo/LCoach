package com.luntianji.l_coach;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
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

    boolean edit = false;
    TextView editName;
    TextView editRole;
    TextView editNumber;
    TextView showName;
    TextView showRole;
    TextView showNumber;
    Button buttonAdd;
    Button buttonDelete;
    MenuItem buttonToggleEdit;

    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_teammate_edit);

        editName = findViewById(R.id.edit_name);
        editRole = findViewById(R.id.edit_role);
        editNumber = findViewById(R.id.edit_number);
        showName = findViewById(R.id.show_name);
        showRole = findViewById(R.id.show_role);
        showNumber = findViewById(R.id.show_number);
        buttonAdd = findViewById(R.id.button_add);
        buttonDelete = findViewById(R.id.button_delete);



        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getString("UpdateTeammateId");
            editName.setText(bundle.getString("UpdateTeammateName"));
            editRole.setText(bundle.getString("UpdateTeammateRole"));
            editNumber.setText(bundle.getString("UpdateTeammateNumber"));
            showName.setText(bundle.getString("UpdateTeammateName"));
            showRole.setText(bundle.getString("UpdateTeammateRole"));
            showNumber.setText(bundle.getString("UpdateTeammateNumber"));

            if (id.length() > 0) {
                setEdit(false);
            }

        } else {
            // create
            setEdit(true);
            buttonDelete.setVisibility(View.INVISIBLE);
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
                String name = showName.getText().toString();
                String role = showRole.getText().toString();
                String number = showNumber.getText().toString();

                deleteTeammate(id, name, role, number);
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // R.menu.mymenu is a reference to an xml file named mymenu.xml which should be inside your res/menu directory.
        // If you don't have res/menu, just create a directory named "menu" inside res
        getMenuInflater().inflate(R.menu.edit, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        buttonToggleEdit = item;
        switch (item.getItemId()) {
            case R.id.button_toggle_edit:
                if (edit) {
                    item.setIcon(android.R.drawable.ic_menu_save);
                } else {
                    item.setIcon(android.R.drawable.ic_menu_edit);
                }
                setEdit(!edit);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void setEdit(boolean newEdit) {
        this.edit = newEdit;
        if (edit) {
            buttonAdd.setVisibility(View.VISIBLE);
            buttonDelete.setVisibility(View.VISIBLE);
            editName.setVisibility(View.VISIBLE);
            editRole.setVisibility(View.VISIBLE);
            editNumber.setVisibility(View.VISIBLE);

            showName.setVisibility(View.INVISIBLE);
            showRole.setVisibility(View.INVISIBLE);
            showNumber.setVisibility(View.INVISIBLE);

//                    item.setIcon()
        } else {

            showName.setVisibility(View.VISIBLE);
            showRole.setVisibility(View.VISIBLE);
            showNumber.setVisibility(View.VISIBLE);


            buttonAdd.setVisibility(View.INVISIBLE);
            buttonDelete.setVisibility(View.INVISIBLE);
            editName.setVisibility(View.INVISIBLE);
            editRole.setVisibility(View.INVISIBLE);
            editNumber.setVisibility(View.INVISIBLE);
        }
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