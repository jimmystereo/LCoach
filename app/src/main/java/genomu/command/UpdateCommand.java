package genomu.command;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

import genomu.firestore_helper.DBCommand;
import genomu.firestore_helper.HanWen;

public class UpdateCommand<E> extends DBCommand {
    private E POJO;
    public UpdateCommand(String root, Activity activity, E POJO) {
        super(root,activity);
        this.POJO = POJO;
    }

    @Override
    public void setCommandName() {
        this.commandName = "更新";
    }

    @Override
    public void work() {
        hanWen.seek().document(POJO.toString()).delete();
        hanWen.sprout(POJO).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                toastMsg(task.isSuccessful());
            }
        });
    }
}