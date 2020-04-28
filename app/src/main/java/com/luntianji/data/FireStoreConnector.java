package com.luntianji.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FireStoreConnector {
    ArrayList<Map> dataList = new ArrayList<>();
    private static String data = "整合訓練 模仿訓練 低 2 0 無 2 兩人以上 0 ,整合訓練 單手頂球 中 1 1 無 none 不拘 同人數 ,整合訓練 雙人單手對傳 高 2 0.5 無 32 兩人一組 同組數 ,整合訓練 單手頂球移動 高 1 1 無 5 不拘 同人數 ,整合訓練 單手頂球接力 高 3 0.17 無 5-1 三人以上一組 同組數 ,整合訓練 1球回合賽 中 6 1 球場 13-1 六人以上 1 ,整合訓練 雙人兩球對傳 高 2 1 無 none 兩人一組 組數*2 ,整合訓練 踮腳反應 低 2 0 無 15 兩人以上 0 ,傳球 手腕施力 低 2 0 同組數藥球 17 兩人一組 同組數藥球 ,傳球 雙人對球 低 2 0.5 無 none 兩人一組 同組數 ,傳球 對牆傳球 低 1 1 牆壁 none 不拘 同人數 ,傳球 傳球射籃 中 1 0.33 籃框 22 不拘 至少三人一球 ,傳球 對空傳球 中 1 1 無 23 不拘 同人數 ,傳球 轉半圈對空傳球 中 1 1 無 24 不拘 同人數 ,傳球 雙人背傳 高 2 0.5 無 31 兩人一組 同組數 ,傳球 三人傳球 中 3 0.33 無 33 三人一組 同組數 ,傳球 多人跑傳 中 3 0.17 無 34 三人以上一組 同組數 ,傳球 三角傳球 中 4 0.25 無 35 四人一組 同組數 ,傳球 四角傳球 中 4 0.25 無 36 四人一組 同組數 ,傳球 接牆壁反彈球 中 3 0.67 牆壁 21 三人一組 組數*2以上 ,發球 托球 低 1 1 無 37 不拘 同人數 ,發球 對牆發球 低 1 1 牆壁 41 不拘 同人數 ,發球 發球 低 1 0.5 球場 none 不拘 約等於一半人數 ,發球 九宮格發球 低 1 0.33 球場 43 不拘 同人數減撿球人數 ,發球 控制發球位置 低 1 0.33 球場 44 不拘 同人數減撿球人數 ,發球 發球成功率 低 1 0.33 球場 48 不拘 人數減撿球人數 ,發球 壓力發球 低 6 0.1 球場 49 六人以上 1 ,發球 發與接發競賽 低 7 0.1 球場 50 七人以上 1 ,接扣球 一扣一接 中 2 0.5 無 51 兩人一組 同組數 ,接扣球 雙人輪流接扣 中 2 0.5 無 52 兩人一組 同組數 ,接扣球 三人接舉扣 高 3 0.33 無 53 三人一組 同組數 ,接扣球 多人接舉扣 中 3 0.17 無 none 三人以上一組 同組數 ,接扣球 一扣一高手接 中 2 0.5 無 55 兩人一組 同組數 ,接扣球 列隊接扣 低 5 0.33 無 56 五人以上一組 同組數 ,接扣球 移動接扣 中 2 0.33 無 59 兩人以上一組 同組數 ,接扣球 接扣接嗆 中 3 0.33 無 64 三人以上一組 同組數*2 ,接扣球 三人接扣 中 7 0.14 無 68 七人以上一組 同組數 ,接扣球 魚躍 高 1 0 無 71 不拘 0 ,接扣球 翻滾 高 1 0 無 72 不拘 0 ,接扣球 個人防守 中 2 0.5 無 74 兩人以上一組 組數*2 ,接扣球 雙人防守 中 3 0.4 無 77 三人以上一組 組數*2 ,接扣球 三人防守 中 4 0.4 無 87 四人以上一組 同組數 ,接扣球 觸網球 低 2 0.5 場地 78 兩人一組 同組數 ,接扣球 修正球練習 低 2 0.33 場地 79 兩人以上 同組數 ,扣球 助跑練習 低 1 0 無 102 不拘 0 ,扣球 對牆扣球 低 1 1 牆壁 105 不拘 同人數 ,扣球 最高點接球 低 1 0.5 無 106 一至二人一組 同組數 ,扣球 自由攻擊 中 2 0.5 場地 108 兩人以上 大於1";
    private static String[] list = data.split(",");
    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static void dataUpload() {
        String[] item;
        try {
            Map<String, Object> start_training_list;
            for (int i = 0; i < Array.getLength(list);i++) {
                start_training_list = new HashMap<>();
                item = list[i].trim().split(" ");
                //type	name difficulty	least_people ball_per_person other number amount_people	amount_ball
                start_training_list.put("type", item[0].trim());
                start_training_list.put("name", item[1].trim());
                start_training_list.put("difficulty", item[2].trim());
                start_training_list.put("least_people", item[3].trim());
                start_training_list.put("ball_per_person", item[4].trim());
                start_training_list.put("other", item[5].trim());
                start_training_list.put("number", item[6].trim());
                start_training_list.put("amount_poeple", item[7].trim());
                start_training_list.put("amount_ball", item[8].trim());

                db.collection("TEST").add(start_training_list).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        //Toast.makeText(MainActivity.this, "upload success", Toast.LENGTH_SHORT).show();
                        //number.setText("");name.setText("");amount_ball.setText("");amount_people.setText("");
                        //type.setText("");other.setText("");least_people.setText("");ball_per_people.setText("");
                        //difficulty.setText("");

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Toast.makeText(MainActivity.this, "upload failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (Exception E) {
            E.printStackTrace();
        }
    }
    public ArrayList<Map> getData(String path, String selectItem, String itemNumber){

        db.collection(path)
                .whereEqualTo(selectItem,itemNumber)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                dataList.add(document.getData());
                                //Log.d(TAG, dataList.toString());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
            return dataList;
    }
    public ArrayList<Map> getData(){
        ArrayList<String>data = new ArrayList<>();
        db.collection("TEST")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                dataList.add(document.getData());
                                //Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        return dataList;
    }
}