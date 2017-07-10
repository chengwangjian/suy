/*数据库自定义方法function()用来判断数据库表是否存在*/
DELIMITER $$
CREATE FUNCTION isTableExist(tablename VARCHAR(60))
RETURNS TINYINT 
BEGIN
  SELECT COUNT(*) into @result FROM INFORMATION_SCHEMA.TABLES WHERE  1=1 and TABLE_SCHEMA='zigbeedevice'  
  and TABLE_NAME = tablename;
 RETURN @result;
END  $$

