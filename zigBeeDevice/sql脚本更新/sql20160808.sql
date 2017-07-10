/*屏蔽掉operateieslib中operateName = "GetLockState" AND operateNameCn="GetLockState"的内容*/
DELETE from operateieslib where  operateName = "GetLockState" AND operateNameCn="GetLockState" ;
