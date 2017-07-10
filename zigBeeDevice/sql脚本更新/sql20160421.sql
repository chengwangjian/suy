/*农业项目*/
CREATE TABLE `farmprivilege` (
  `id` int(11) NOT NULL,
  `privilege_name` varchar(30) DEFAULT NULL,
  `privilege_code` varchar(30) DEFAULT NULL,
  `privilege_name_en` varchar(30) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `is_leaf` int(11) DEFAULT NULL,
  `order_index` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `farmroleprivilege` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL,
  `privilege_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `farmuser` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `pwd` varchar(40) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `level_id` tinyint(4) DEFAULT '3',
  `enabled` varchar(2) DEFAULT '1',
  `regist_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `parent_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`,`regist_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `farmuserrole` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `role_id_index` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


create table farmdevice
(
   id                   bigint not null auto_increment,
   allcount             tinyint,
   curcount             tinyint,
   house_ieee           varchar(20),
   device_id            int,
   rid                  int,
   picname              varchar(20),
   profileid            varchar(10),
   powersource          varchar(30),
   curpowersource       tinyint,
   curpowersourcelevel  int,
   ieee                 varchar(20),
   nwk_addr             varchar(10),
   node_name            varchar(50),
   manufactory          varchar(40),
   zcl_version          varchar(5),
   stack_version        varchar(5),
   app_version          varchar(5),
   hw_version           varchar(5),
   date_code            varchar(10),
   model_id             varchar(15),
   solid_model_id       varchar(25),
   node_type            tinyint,
   status               tinyint,
   ep                   varchar(5),
   device_name          varchar(30),
   ep_model_id          varchar(16),
   device_level         tinyint,
   primary key (id)
);

alter table farmdevice comment '区域设备表(farmdevice)';

/*添加485指令表基于func_num字段的唯一索引*/
alter table z485action add unique action_func_num_index(func_num);

/*farmdevice添加脚本*/
ALTER TABLE farmdevice ADD UNIQUE hieee_ieee_ep_unique(house_ieee, ieee, ep);

/*用户与区域关联表*/
create table farmuserarea
(
   id                   bigint not null auto_increment,
   house_ieee           varchar(20),
   user_id              bigint,
   primary key (id)
);

alter table farmuserarea comment '用户与区域的关联表(farmuserarea)';

/*角色表*/
CREATE TABLE `farmrole` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(30) DEFAULT NULL,
  `level_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*初始化角色*/
INSERT INTO farmrole(id,role_name,level_id) VALUES(1,'管理员',1),(2,'技术人员',2),(3,'普通员工',3);