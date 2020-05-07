package com.luntianji.l_coach;

import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.luntianji.l_coach.model.Training;

import genomu.firestore_helper.DBCommand;
import genomu.firestore_helper.DBReceiver;
import genomu.firestore_helper.HanWen;
import genomu.command.CreateCommand;
import genomu.command.DeleteCommand;
import genomu.command.UpdateCommand;

import static genomu.firestore_helper.DBEmcee.ACTION01;


public class MyTrainingEditActivity extends AppCompatActivity {

    private static final String TAG = "AddTrainingActivity";

    TextView editName;
    TextView editDifficulty;
    TextView editOther;
    Button buttonAdd;
    Button buttonDelete;

    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_training_edit);

        editName = findViewById(R.id.edit_my_training_name);
        editDifficulty = findViewById(R.id.edit_my_training_difficulty);
        editOther = findViewById(R.id.edit_my_training_other);
        buttonAdd = findViewById(R.id.button_add_my_training);
        buttonDelete = findViewById(R.id.button_delete_my_training);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getString("UpdateTrainingId");
            editName.setText(bundle.getString("UpdateTrainingName"));
            editDifficulty.setText(bundle.getString("UpdateTrainingDifficulty"));
            editOther.setText(bundle.getString("UpdateTrainingOther"));

        }

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString();
                String difficulty = editDifficulty.getText().toString();
                String other = editOther.getText().toString();

                if (editName.length() > 0) {
                    if (id.length() > 0) {
                        updateTraining(id, name, difficulty, other);
                    } else {
                        addTraining(name, difficulty, other);
                    }
                }
                setResult(RESULT_OK);
                finish();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString();
                String difficulty = editDifficulty.getText().toString();
                String other = editOther.getText().toString();

                deleteTraining(id, name, difficulty, other);
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    private void updateTraining(String id, String name, String difficulty, String other) {
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

//        firestoreDB.collection("trainings")
//                .document(id)
//                .set(training)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Log.e(TAG, "Training document update successful!");
//                        Toast.makeText(getApplicationContext(), "Training has been updated!", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.e(TAG, "Error adding Training document", e);
//                        Toast.makeText(getApplicationContext(), "Training could not be updated!", Toast.LENGTH_SHORT).show();
//                    }
//                });
    }

    private void addTraining(String name, String difficulty, String other) {

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

    private void deleteTraining(String id, String name, String difficulty, String other) {

        Training training = new Training();
        training.setId(id);
        training.setName(name);
        training.setDifficulty(difficulty);
        training.setOther(other);

        DBReceiver receiver = new DBReceiver() {
        };
        registerReceiver(receiver, new IntentFilter(ACTION01));
        DBCommand command = new DeleteCommand<>("my_training_list", this, training);
        command.work();
    }
}