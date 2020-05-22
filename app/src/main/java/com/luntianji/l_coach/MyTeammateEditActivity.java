package com.luntianji.l_coach;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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

import com.google.android.material.textfield.TextInputLayout;
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
    boolean create = false;
    Button buttonAdd;
    Button buttonDelete;
    MenuItem buttonToggleEdit;
    String id = "";

    // pojo
    TextInputLayout nameField;
    TextInputLayout roleField;
    TextInputLayout numberField;
    TextInputLayout infoField;
    TextView editName;
    TextView editRole;
    TextView editNumber;
    TextView editInfo;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_teammate_edit);

        buttonAdd = findViewById(R.id.button_add);
        buttonDelete = findViewById(R.id.button_delete);

        // pojo
        nameField = findViewById(R.id.name_field);
        roleField = findViewById(R.id.role_field);
        numberField = findViewById(R.id.number_field);
        infoField = findViewById(R.id.info_field);
        editName = findViewById(R.id.edit_name);
        editRole = findViewById(R.id.edit_role);
        editNumber = findViewById(R.id.edit_number);
        editInfo = findViewById(R.id.edit_info);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            // pojo
            id = bundle.getString("UpdateTeammateId");
            editName.setText(bundle.getString("UpdateTeammateName"));
            editRole.setText(bundle.getString("UpdateTeammateRole"));
            editNumber.setText(bundle.getString("UpdateTeammateNumber"));
            editInfo.setText(bundle.getString("UpdateTeammateInfo"));

            if (id.length() > 0) {
                setEdit(false);
            }

        } else {
            // create
            setEdit(true);
            setCreate(true);
            buttonDelete.setVisibility(View.INVISIBLE);
        }

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pojo
                String name = editName.getText().toString();
                String role = editRole.getText().toString();
                String number = editNumber.getText().toString();
                String info = editInfo.getText().toString();

                if (editName.length() > 0) {
                    // pojo
                    addTeammate(name, role, number, info);
                }
                setResult(RESULT_OK);
                finish();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTeammate(id);
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
        if (create) {
            return false;
        }
        getMenuInflater().inflate(R.menu.edit, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        buttonToggleEdit = item;
        switch (item.getItemId()) {
            case R.id.button_toggle_edit:
                if (edit) {
                    onSave();
                }
                setEdit(!edit);

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void onSave() {
        // pojo
        String name = editName.getText().toString();
        String role = editRole.getText().toString();
        String number = editNumber.getText().toString();
        String info = editInfo.getText().toString();

        if (editName.length() > 0) {
            if (id.length() > 0) {
                // pojo
                updateTeammate(id, name, role, number, info);
                setResult(RESULT_OK);
            }
        }
    }

    private void setEdit(boolean newEdit) {
        this.edit = newEdit;
        if (edit) {
            if (buttonToggleEdit != null) {
                buttonToggleEdit.setIcon(android.R.drawable.ic_menu_save);
            }

            buttonDelete.setVisibility(View.VISIBLE);
            buttonAdd.setVisibility(View.INVISIBLE);

            // pojo
            editName.setInputType(InputType.TYPE_CLASS_TEXT);
            editRole.setInputType(InputType.TYPE_CLASS_TEXT);
            editNumber.setInputType(InputType.TYPE_CLASS_TEXT);
            editInfo.setInputType(InputType.TYPE_CLASS_TEXT);

        } else {
            if (buttonToggleEdit != null) {
                buttonToggleEdit.setIcon(android.R.drawable.ic_menu_edit);
            }

            buttonAdd.setVisibility(View.INVISIBLE);
            buttonDelete.setVisibility(View.INVISIBLE);

            // pojo
            editName.setInputType(InputType.TYPE_NULL);
            editRole.setInputType(InputType.TYPE_NULL);
            editNumber.setInputType(InputType.TYPE_NULL);
            editInfo.setInputType(InputType.TYPE_NULL);




        }
    }

    private void setCreate(boolean newCreate) {
        create = newCreate;
        buttonAdd.setVisibility(View.VISIBLE);

        // pojo
        editName.setInputType(InputType.TYPE_CLASS_TEXT);
        editRole.setInputType(InputType.TYPE_CLASS_TEXT);
        editNumber.setInputType(InputType.TYPE_CLASS_TEXT);
        editInfo.setInputType(InputType.TYPE_CLASS_TEXT);
    }

    private void updateTeammate(String id, String name, String role, String number, String info) {
        // pojo
        Teammate teammate = new Teammate(id, name, role, number, info);

        DBReceiver receiver = new DBReceiver() {

        };
        registerReceiver(receiver, new IntentFilter(ACTION01));
        DBCommand command = new UpdateCommand<>("teammates", this, teammate);
        command.work();

    }

    private void addTeammate(String name, String role, String number, String info) {
        // pojo
        Teammate teammate = new Teammate(name, role, number, info);

        DBReceiver receiver = new DBReceiver() {
        };
        registerReceiver(receiver, new IntentFilter(ACTION01));
        DBCommand command = new CreateCommand("teammates", this, teammate);
        command.work();
    }

    private void deleteTeammate(String id) {
        // pojo
        Teammate teammate = new Teammate(id);

        DBReceiver receiver = new DBReceiver() {
        };
        registerReceiver(receiver, new IntentFilter(ACTION01));
        DBCommand command = new DeleteCommand<>("teammates", this, teammate);
        command.work();
    }
}