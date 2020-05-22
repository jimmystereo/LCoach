package com.luntianji.l_coach;

import android.content.IntentFilter;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.luntianji.l_coach.model.Training;

import genomu.firestore_helper.DBCommand;
import genomu.firestore_helper.DBReceiver;
import genomu.command.CreateCommand;
import genomu.command.DeleteCommand;
import genomu.command.UpdateCommand;

import static genomu.firestore_helper.DBEmcee.ACTION01;


public class MyTrainingEditActivity extends AppCompatActivity {

    private static final String TAG = "AddTrainingActivity";

    boolean edit = false;
    boolean create = false;
    Button buttonAdd;
    Button buttonDelete;
    MenuItem buttonToggleEdit;
    String id = "";

    // pojo
    TextView editName;
    TextView editDifficulty;
    TextView editOther;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_training_edit);

        buttonAdd = findViewById(R.id.button_add_my_training);
        buttonDelete = findViewById(R.id.button_delete_my_training);

        // pojo
        editName = findViewById(R.id.edit_my_training_name);
        editDifficulty = findViewById(R.id.edit_my_training_difficulty);
        editOther = findViewById(R.id.edit_my_training_other);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            // pojo
            id = bundle.getString("UpdateTrainingId");
            editName.setText(bundle.getString("UpdateTrainingName"));
            editDifficulty.setText(bundle.getString("UpdateTrainingDifficulty"));
            editOther.setText(bundle.getString("UpdateTrainingOther"));

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
                String difficulty = editDifficulty.getText().toString();
                String other = editOther.getText().toString();

                if (editName.length() > 0) {
                    // pojo
                    addTraining(name, difficulty, other);
                }
                setResult(RESULT_OK);
                finish();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTraining(id);
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
        String difficulty = editDifficulty.getText().toString();
        String other = editOther.getText().toString();

        if (editName.length() > 0) {
            if (id.length() > 0) {
                // pojo
                updateTraining(id, name, difficulty, other);
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
            editDifficulty.setInputType(InputType.TYPE_CLASS_TEXT);
            editOther.setInputType(InputType.TYPE_CLASS_TEXT);

        } else {
            if (buttonToggleEdit != null) {
                buttonToggleEdit.setIcon(android.R.drawable.ic_menu_edit);
            }

            buttonAdd.setVisibility(View.INVISIBLE);
            buttonDelete.setVisibility(View.INVISIBLE);

            // pojo
            editName.setInputType(InputType.TYPE_NULL);
            editDifficulty.setInputType(InputType.TYPE_NULL);
            editOther.setInputType(InputType.TYPE_NULL);

        }
    }

    private void setCreate(boolean newCreate) {
        create = newCreate;
        buttonAdd.setVisibility(View.VISIBLE);

        // pojo
        editName.setInputType(InputType.TYPE_CLASS_TEXT);
        editDifficulty.setInputType(InputType.TYPE_CLASS_TEXT);
        editOther.setInputType(InputType.TYPE_CLASS_TEXT);
    }

    private void updateTraining(String id, String name, String difficulty, String other) {
        // pojo
        Training training = new Training();
        training.setId(id);
        training.setName(name);
        training.setDifficulty(difficulty);
        training.setOther(other);

        DBReceiver receiver = new DBReceiver() {

        };
        registerReceiver(receiver, new IntentFilter(ACTION01));
        DBCommand command = new UpdateCommand<>("my_training_list", this, training);
        command.work();

    }

    private void addTraining(String name, String difficulty, String other) {
        //pojo
        Training training = new Training();
        training.setName(name);
        training.setDifficulty(difficulty);
        training.setOther(other);

        DBReceiver receiver = new DBReceiver() {
        };
        registerReceiver(receiver, new IntentFilter(ACTION01));
        DBCommand command = new CreateCommand("my_training_list", this, training);
        command.work();
    }

    private void deleteTraining(String id) {
        // pojo
        Training training = new Training();
        training.setId(id);

        DBReceiver receiver = new DBReceiver() {
        };
        registerReceiver(receiver, new IntentFilter(ACTION01));
        DBCommand command = new DeleteCommand<>("my_training_list", this, training);
        command.work();
    }
}