/*添加多张数据库表字段的自定义函数*/
CREATE PROCEDURE modifyfiled (in tabname varchar(250),in fieldparams varchar(300),in databasename varchar(250))
BEGIN
  DECLARE tablename varchar(90);
  DECLARE done int;
  DECLARE tabblnames CURSOR for SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME LIKE  CONCAT(tabname,"%") and TABLE_SCHEMA = databasename;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
  open tabblnames;  
   repeat  
      fetch tabblnames into tablename ;
       set @sqlStr= CONCAT("ALTER TABLE ",tablename," ADD ", fieldparams);  
       PREPARE  stmt from @sqlStr;
       EXECUTE stmt;
   until done end repeat;  
   close tabblnames; 
END
/*调用方法*/
call modifyfiled('rssirecord','weather varchar(20)','zigbeedevice');