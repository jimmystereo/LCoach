package genomu.firestore_helper;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

public interface DBEmcee {
    String ACTION01 = "genomu.ACTION_01";
    String ACTION02 = "genomu.ACTION_02";
    void goLive(Query query);
    void goLive(Task<DocumentSnapshot> snapshotTask);
}
