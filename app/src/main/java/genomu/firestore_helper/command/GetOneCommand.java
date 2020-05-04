package genomu.firestore_helper.command;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import java.io.Serializable;

import genomu.firestore_helper.DBCommand;
import genomu.firestore_helper.DBEmcee;
import genomu.firestore_helper.DBReceiver;
import genomu.firestore_helper.HanWen;

public class GetOneCommand extends DBCommand implements DBEmcee {
    private Activity activity;
    private Class type;
    private Object POJO;
    public GetOneCommand(HanWen hanWen, Activity activity,Object POJO) {
        super(hanWen);
        this.activity = activity;
        this.POJO = POJO;
        this.type = POJO.getClass();
    }

    @Override
    public void work() {
        goLive(hanWen.seek().document(POJO.toString()).get());
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
                    Serializable item = (Serializable) task.getResult().toObject(type);
                    Intent intent = new Intent(ACTION01);
                    intent.putExtra(DBReceiver.DES_POJO,item);
                    activity.sendBroadcast(intent);
                }
            }
        });
    }
}
