package genomu.command;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import genomu.firestore_helper.DBCommand;
import genomu.firestore_helper.HanWen;

public class DeleteCommand<E> extends DBCommand {
    private E POJO;
    public DeleteCommand(String root, Activity activity,E POJO) {
        super(root,activity);
        this.POJO = POJO;
    }

    @Override
    public void setCommandName() {
        this.commandName = "刪除";
    }

    @Override
    public void work() {
        // plz override POJO toString(){return getId();}
        hanWen.seek().document(POJO.toString()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                toastMsg(task.isSuccessful());
            }
        });
    }
}