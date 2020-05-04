package genomu.firestore_helper.command;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import genomu.firestore_helper.DBCommand;
import genomu.firestore_helper.DBEmcee;
import genomu.firestore_helper.DBReceiver;
import genomu.firestore_helper.HanWen;

public class GetListCommand extends DBCommand implements DBEmcee {
    private Activity activity;
    private Class E;
    public GetListCommand(HanWen hanWen, Activity activity,Class E) {
        super(hanWen);
        this.activity = activity;
        this.E = E;
    }

    @Override
    public void work() {
        goLive(hanWen.seek());
    }

    @Override
    public void goLive(Query query) {
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    List list = task.getResult().toObjects(E);
                    Intent intent = new Intent(ACTION01);
                    intent.putParcelableArrayListExtra(DBReceiver.DES_LIST, (ArrayList<? extends Parcelable>) list);
                    activity.sendBroadcast(intent);
                }
            }
        });
    }

    @Override
    public void goLive(Task<DocumentSnapshot> snapshotTask) {

    }
}
