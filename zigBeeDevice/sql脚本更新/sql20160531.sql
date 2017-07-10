/*485权限修改*/
UPDATE reliprivilege SET parent_id=1140 WHERE parent_id=1132;
INSERT INTO reliprivilege(id,privilege_name,privilege_code,privilege_name_en,parent_id,is_leaf,order_index) 
VALUES(1140,'设备类型',NULL,NULL,1132,0,1),(1141,'设备模板',NULL,NULL,1132,0,2);
UPDATE reliprivilege SET privilege_name='新增设备类型',parent_id=1140 WHERE id=1133;
INSERT reliprivilege(id,privilege_name,privilege_code,privilege_name_en,parent_id,is_leaf,order_index) VALUES(1142,'编辑设备类型ID',NULL,NULL,1140,1,4);
INSERT reliprivilege(id,privilege_name,privilege_code,privilege_name_en,parent_id,is_leaf,order_index) VALUES(1143,'新增设备模板',NULL,NULL,1141,1,1);
INSERT reliprivilege(id,privilege_name,privilege_code,privilege_name_en,parent_id,is_leaf,order_index) VALUES(1144,'编辑',NULL,NULL,1141,1,2);
INSERT reliprivilege(id,privilege_name,privilege_code,privilege_name_en,parent_id,is_leaf,order_index) VALUES(1145,'删除',NULL,NULL,1141,1,3);