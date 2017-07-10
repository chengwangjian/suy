/*485设备的模板设备*/
create table z485template
(
   id                   bigint not null auto_increment,
   template_name        varchar(30),
   template_type        varchar(15),
   primary key (id)
);

alter table z485template comment '模板表';

create table z485action
(
   id                   bigint not null auto_increment,
   action_name          varchar(20),
   action_cmd           varchar(250),
   func_num             varchar(15),
   template_id          bigint,
   primary key (id)
);

alter table z485action comment '动作表';

create table z485devicetempldaterel
(
   id                   bigint not null auto_increment,
   ieee                 varchar(16),
   ep                   varchar(5),
   template_type        varchar(15),
   primary key (id)
);

alter table z485devicetempldaterel comment '设备和模板的关联表';
ALTER TABLE z485devicetempldaterel ADD house_id BIGINT;

/*添加串口指令库管理权限*/
insert into reliprivilege(id,privilege_name,parent_id,is_leaf,order_index) values(1132,'485模板库',1001,0,13);
insert into reliprivilege(id,privilege_name,parent_id,is_leaf,order_index) values(1133,'新增',1132,1,1);
insert into reliprivilege(id,privilege_name,parent_id,is_leaf,order_index) values(1134,'编辑',1132,1,2);
insert into reliprivilege(id,privilege_name,parent_id,is_leaf,order_index) values(1135,'删除',1132,1,3);

insert into relilevelprivilege(level_id,privilege_id) values(1,1132);
insert into relilevelprivilege(level_id,privilege_id) values(1,1133);
insert into relilevelprivilege(level_id,privilege_id) values(1,1134);
insert into relilevelprivilege(level_id,privilege_id) values(1,1135);

insert into reliroleprivilege(role_id,privilege_id) values(9,1132);
insert into reliroleprivilege(role_id,privilege_id) values(9,1133);
insert into reliroleprivilege(role_id,privilege_id) values(9,1134);
insert into reliroleprivilege(role_id,privilege_id) values(9,1135);

/*设备模板关联表添加唯一索引*/
ALTER TABLE z485devicetempldaterel ADD UNIQUE device_temp_rel_index(ieee,ep,template_type,house_id);
