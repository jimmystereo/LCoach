package genomu.command;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import java.io.Serializable;

import genomu.firestore_helper.DBCommand;
import genomu.firestore_helper.DBEmcee;
import genomu.firestore_helper.DBReceiver;

public class CreateCommand extends DBCommand implements DBEmcee {
    private Object POJO;
    public CreateCommand(String root, Activity activity, Object POJO) {
        super(root,activity);
        this.POJO = POJO;
    }

    @Override
    public void setCommandName() {
        this.commandName = "新增";
    }

    @Override
    public void work() {
        hanWen.sprout(POJO).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                completeMsg(task.isSuccessful());
                if(task.isSuccessful()){
                    String id = task.getResult().getId();
                    task.getResult().update("id",id);
                    goLive(task.getResult().get());
                }
            }
        });
    }

    @Override
    public void goLive(Query query) {

    }

    @Override
    public void goLive(Task<DocumentSnapshot> snapshotTask) {
        snapshotTask.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    Serializable item = (Serializable) task.getResult().toObject(POJO.getClass());
                    Intent intent = new Intent(ACTION01);
                    intent.putExtra(DBReceiver.DES_POJO,item);
                    activity.sendBroadcast(intent);
                }
            }
        });
    }
}