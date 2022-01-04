# my-family
**自家冰箱管理**
* 透過Line Bot上傳記錄
* 每天中午12點定時排程查詢資料庫
  * 將7天內超過保存期限之物品修改狀態為即將過期
  * 自動推播即將過期之物品到line群組
  
**實際使用畫面**
* 加入Bot好友之後會先需要設定使用冰箱記錄的暱稱
* 之後可以使用 **!指令**來查看機器人相關操作
 
<img src="https://i.imgur.com/Vs26ZYv.jpg" width="400px">
   
    
* 直接透過Bot記錄冰箱物品
   
<img src="https://i.imgur.com/Y1iYkXz.jpg" width="400px">
    
       
* 之後選擇保存期限
       
<img src="https://i.imgur.com/0MAk1ry.jpg" width="400px">
<img src="https://i.imgur.com/mCI9tua.jpg" width="400px">
    
      
* 每天中午會自動推播即將過期之物品
  
<img src="https://i.imgur.com/kBnNjqc.jpg" width="400px">

  
* 點選網頁連結可登入管理或用訪客查看記錄
  
<img src="https://i.imgur.com/4gVS9SZ.jpg" width="400px">

  
* 訪客只能查詢無法刪除
  
<img src="https://i.imgur.com/is5GAL1.jpg" width="400px">


# 使用工具
* [SpringBoot](https://spring.io/projects/spring-boot)
* [Line Message API](https://developers.line.biz/en/docs/messaging-api/overview/)
* [heroku](https://dashboard.heroku.com/)
* [MongoDB](https://www.mongodb.com/)
* [Docker](https://www.docker.com/)
