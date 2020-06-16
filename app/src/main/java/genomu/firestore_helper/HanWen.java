package genomu.firestore_helper;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class HanWen {
    private static FirebaseFirestore database = FirebaseFirestore.getInstance();
    private CollectionReference reference;

    public HanWen(String key) {
        this.reference = database.collection(key);
    }

    public void sprout(String field,Object value){
        Map<String ,Object> map = new HashMap<>();
        map.put(field,value);
        reference.add(map);
    }

    public Task<DocumentReference> sprout(Object value){
        return reference.add(value);
    }

    public CollectionReference seek(){
        return reference;
    }

}