# LCoach
your best free volleyball training app
# 5/5 更新 

## 1.加入篩選器介面
![](https://i.imgur.com/Xv4EfHN.png)
### 選擇完後按確認會將存好資料的int[]傳入Activity中
### 按取消會重置成上次確認狀態(無則預設)

### 資料儲存方式 `int[] = {type, difficulty, other, people, ball}`
### type:       0=預設, 1=整合訓練, 2=傳球, 3=發球, 4=接扣球, 5=扣球
### difficulty: 0=預設, 1=低,      2=中, 3=高
### other:      0=預設, 1=不需牆壁, 2=不需球場
### people:     0=預設, 1~6對應人數, 7=七人以上
### ball:       0=預設, 1=0顆球, 2~7=n-1顆球, 8=七顆球以上
