alter table farmuser add unique farmuser_name_index(user_name);
alter table modefarmhvacsub modify column ActType varchar(10) default '0';
/*增加农业权限*/
INSERT INTO reliprivilege(id,privilege_name,parent_id,is_leaf,order_index)
VALUES(1136,'农业项目管理平台',1001,0,14),(1137,'新增用户',1136,1,1),
(1138,'重置密码',1136,1,2),(1139,'删除',1136,1,3);
INSERT INTO relilevelprivilege(id,level_id,privilege_id)
VALUES(513,1,1136),(514,1,1137),(515,1,1138),(516,1,1139);