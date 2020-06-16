package genomu.firestore_helper;

import android.app.Activity;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public abstract class DBCommand {

    public  String getCommandName() {
        return commandName;
    }

    public abstract void setCommandName() ;

    public String getSuccessMsg(){
        return commandName + "成功";
    }

    public String getFailMsg(){
        return commandName + "失敗";
    }

    protected String commandName;
    protected HanWen hanWen;
    protected Activity activity;

    public DBCommand(String root,Activity activity) {
        this.hanWen = new HanWen(root);
        this.activity = activity;
        setCommandName();
    }

    public DBCommand(HanWen hanWen,Activity activity) {
        this.hanWen = hanWen;
        this.activity = activity;
        setCommandName();
    }

    protected void completeMsg(boolean isSuccessful){
        String msg;
        if(isSuccessful){
            msg = getSuccessMsg();
        }else{
            msg = getFailMsg();
        }
        Snackbar.make(activity.findViewById(android.R.id.content),msg,Snackbar.LENGTH_SHORT).show();
//        Toast.makeText(activity,msg,Toast.LENGTH_LONG).show();
    }

    public abstract void work();
}