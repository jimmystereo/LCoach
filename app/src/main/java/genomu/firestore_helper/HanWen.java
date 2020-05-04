package genomu.firestore_helper;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

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

    public void sprout(Object value){
        reference.add(value).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if(task.isSuccessful()){
                    String id = task.getResult().getId();
                    task.getResult().update("id",id);
                }
            }
        });
    }

    public CollectionReference seek(){
        return reference;
    }

}