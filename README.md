# LCoach

![logo](https://i.imgur.com/hr95N5D.png)

Your best free volleyball training app

![screenshot](https://i.imgur.com/G2KVwDV.png)

## Training Set Provided by L Coach

There are a lot of build-in training set which you can choose what you want to start your AMAZING training.

## Custom Training Set

You can override the trainings provided by L Coach to custom everything you want. You can also create a training from scratch!

## Join in Community

You can share your amazing custom training with others by participate in community. You can also download numerous trainings created by others.

## Technology

- Java
- Android Studio
- Material Design

## Our Team

- Developer：Jimmy
- UI/UX designer and product manager：tp6
- Helper：genomu

## 5/5 更新 

## 1.加入篩選器介面

![](https://i.imgur.com/Xv4EfHN.png)

### 選擇完後按確認會將存好資料的int[]傳入Activity中
### 按取消會重置成上次確認狀態(無則預設)

### 資料儲存方式 `int[] = {type, difficulty, other, people, ball}`
### type:       0=預設, 1=整合訓練, 2=傳球, 3=發球, 4=接扣球, 5=扣球
### difficulty: 0=預設, 1=低,      2=中, 3=高
### other:      0=預設, 1=不需牆壁, 2=不需球場, 3=不需牆壁和球場
### people:     0=預設, 1~6對應人數, 7=七人以上
### ball:       0=預設, 1=0顆球, 2~7=n-1顆球, 8=七顆球以上
