/*改变字段的长度*/
ALTER TABLE `deviceproblem`
MODIFY COLUMN `descriptionCn`  varchar(10000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `modelNo`,
MODIFY COLUMN `descriptionEn`  varchar(10000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `descriptionCn`;