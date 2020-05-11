package genomu.command;

import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

import genomu.firestore_helper.DBCommand;
import genomu.firestore_helper.HanWen;

public class CreateCommand extends DBCommand {
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
                toastMsg(task.isSuccessful());
                if(task.isSuccessful()){
                    String id = task.getResult().getId();
                    task.getResult().update("id",id);
                }
            }
        });
    }
}