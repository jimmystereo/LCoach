package com.luntianji.data_matters;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FireStoreConnector {
    public void pushDataToFireStore(String pathName, Map map){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(pathName).add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG,"Success");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG,"Failed",e);
            }
        });
    }
}
