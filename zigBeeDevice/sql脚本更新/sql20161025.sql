/*向表relilevel中添加字段level_name_en：*/
ALTER TABLE relilevel ADD COLUMN level_name_en varchar(30) DEFAULT NULL;
/*将表relilevel中level_name_en翻译成英文：*/
UPDATE relilevel SET level_name_en = 'Administrators' WHERE id = 1;
UPDATE relilevel SET level_name_en = 'Business' WHERE id = 2;
UPDATE relilevel SET level_name_en = 'Distributor' WHERE id = 3;
UPDATE relilevel SET level_name_en = 'Production' WHERE id = 4;
UPDATE relilevel SET level_name_en = 'Other' WHERE id = 5;

/*向表farmrole中添加字段role_name_en：*/
ALTER TABLE farmrole ADD COLUMN role_name_en varchar(50) DEFAULT NULL;
/*将表farmrole中role_name_en翻译成英文：*/
UPDATE farmrole SET role_name_en = 'Administrators' WHERE id = 1;
UPDATE farmrole SET role_name_en = 'Artisan' WHERE id = 2;
UPDATE farmrole SET role_name_en = 'OrdinaryEmployees' WHERE id = 3;

/*向表messagehistory中添加字段remarkEn：*/
ALTER TABLE messagehistory ADD COLUMN remarkEn varchar(50) DEFAULT NULL;
/*将表messagehistory中remarkEn翻译成英文：*/
UPDATE messagehistory SET remarkEn = 'System SMS' WHERE remark = '系统短信';
UPDATE messagehistory SET remarkEn = 'Early warning message' WHERE remark = '预警短信';

/*向表serverlib中添加字段serverlineEn：*/
ALTER TABLE serverlib ADD COLUMN serverlineEn varchar(100) DEFAULT NULL;
/*将表messagehistory中remarkEn翻译成英文：*/
UPDATE serverlib SET serverlineEn = 'North line' WHERE serverline = '北线';
UPDATE serverlib SET serverlineEn = 'South Line' WHERE serverline = '南线';

/*向表ircontrolltype中添加字段controllTypeNameEn：*/
ALTER TABLE ircontrolltype ADD COLUMN controllTypeNameEn varchar(100) DEFAULT NULL;
/*将表ircontrolltype中remarkEn翻译成英文：*/
UPDATE ircontrolltype SET controllTypeNameEn = 'AC air conditioning' WHERE controllTypeName = 'AC空调';
UPDATE ircontrolltype SET controllTypeNameEn = 'TV' WHERE controllTypeName = 'TV';
UPDATE ircontrolltype SET controllTypeNameEn = 'TVBox TV set top box' WHERE controllTypeName = 'TVBox电视机顶盒';
UPDATE ircontrolltype SET controllTypeNameEn = 'DVD' WHERE controllTypeName = 'DVD';
UPDATE ircontrolltype SET controllTypeNameEn = 'Projector projector' WHERE controllTypeName = 'Projector投影仪';
UPDATE ircontrolltype SET controllTypeNameEn = 'Other' WHERE controllTypeName = '其他';
