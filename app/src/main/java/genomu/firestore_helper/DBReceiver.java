package genomu.firestore_helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.Serializable;
import java.util.List;

public abstract class DBReceiver extends BroadcastReceiver {
    public final static String DES_STR = "string_fs_help";
    public final static String DES_LIST = "list_fs_help";
    public final static String DES_POJO = "pojo_fs_help";

    @Override
    public void onReceive(Context context, Intent intent) {
        String string = intent.getStringExtra(DES_STR);
        List list = intent.getParcelableArrayListExtra(DES_LIST);
        Serializable pojo = intent.getSerializableExtra(DES_POJO);
        if(list!=null){
            onReceive(list);
        }
        onReceive(string);

        if(pojo!=null){
            onReceive(pojo);
        }

    }

    public void onReceive(String receivedString){}
    public void onReceive(List receivedList){}
    public void onReceive(Object receivedPOJO){}
}