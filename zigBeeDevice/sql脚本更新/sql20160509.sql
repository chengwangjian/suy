ALTER TABLE farmdevice MODIFY powersource VARCHAR(70);
/*串口修改---------begin----------*/
/*清空数据*/
DELETE FROM z485action;
DELETE FROM z485devicetempldaterel;
DELETE FROM z485template;
DELETE FROM z485device;
DELETE FROM z485templateaction;
/*模板修改*/
ALTER TABLE z485template DROP template_type;
ALTER TABLE z485template ADD template_id INT;
ALTER TABLE z485template ADD INDEX template_id_unique(template_id);
/*串口设备*/
CREATE TABLE `z485device` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `device_name` varchar(30) DEFAULT NULL,
  `temp_id` int(11) DEFAULT NULL,
	`description` VARCHAR(100) NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='串口设备表';
/*指令模板*/
CREATE TABLE `z485templateaction` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `action_name` varchar(20) DEFAULT NULL,
  `func_num` varchar(30) DEFAULT NULL,
  `temp_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='指令模板表';
/*指令*/
ALTER TABLE z485action DROP func_num;
ALTER TABLE z485action DROP action_name;
ALTER TABLE z485action DROP template_id;
ALTER TABLE z485action ADD device_id BIGINT;
ALTER TABLE z485templateaction ADD UNIQUE temp_id_func_num_index(temp_id,func_num);
ALTER TABLE z485action ADD UNIQUE action_cmd_unique(action_cmd);
ALTER TABLE z485action ADD temp_action_id BIGINT;
/*设备与模板设备关联*/
ALTER TABLE z485devicetempldaterel DROP template_type;
ALTER TABLE z485devicetempldaterel ADD device_id BIGINT;
ALTER TABLE z485devicetempldaterel ADD name VARCHAR(30);
ALTER TABLE z485devicetempldaterel DROP INDEX device_temp_rel_index;
ALTER TABLE z485devicetempldaterel ADD UNIQUE device_temp_rel_unique(ieee,ep,device_id,house_id);
/*功能名称字典表*/
CREATE TABLE `z485funcnumlib` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
	`func_id` int(11) DEFAULT NULL,
  `func_name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='功能名称字典表';
INSERT INTO z485funcnumlib(id,func_id,func_name) 
VALUES(1,1,'开'),(2,2,'关'),(3,3,'读测量温度'),(4,4,'读设定温度'),(5,5,'手动模式'),
(6,6,'自动模式'),(7,7,'锁定模式'),
(8,1000,'设定5.0℃'),
(9,1001,'设定5.5℃'),
(10,1002,'设定6.0℃'),
(11,1003,'设定6.5℃'),
(12,1004,'设定7.0℃'),
(13,1005,'设定7.5℃'),
(14,1006,'设定8.0℃'),
(15,1007,'设定8.5℃'),
(16,1008,'设定9.0℃'),
(17,1009,'设定9.5℃'),
(18,1010,'设定10.0℃'),
(19,1011,'设定10.5℃'),
(20,1012,'设定11.0℃'),
(21,1013,'设定11.5℃'),
(22,1014,'设定12.0℃'),
(23,1015,'设定12.5℃'),
(24,1016,'设定13.0℃'),
(25,1017,'设定13.5℃'),
(26,1018,'设定14.0℃'),
(27,1019,'设定14.5℃'),
(28,1020,'设定15.0℃'),
(29,1021,'设定15.5℃'),
(30,1022,'设定16.0℃'),
(31,1023,'设定16.5℃'),
(32,1024,'设定17.0℃'),
(33,1025,'设定17.5℃'),
(34,1026,'设定18.0℃'),
(35,1027,'设定18.5℃'),
(36,1028,'设定19.0℃'),
(37,1029,'设定19.5℃'),
(38,1030,'设定20.0℃'),
(39,1031,'设定20.5℃'),
(40,1032,'设定21.0℃'),
(41,1033,'设定21.5℃'),
(42,1034,'设定22.0℃'),
(43,1035,'设定22.5℃'),
(44,1036,'设定23.0℃'),
(45,1037,'设定23.5℃'),
(46,1038,'设定24.0℃'),
(47,1039,'设定24.5℃'),
(48,1040,'设定25.0℃'),
(49,1041,'设定25.5℃'),
(50,1042,'设定26.0℃'),
(51,1043,'设定26.5℃'),
(52,1044,'设定27.0℃'),
(53,1045,'设定27.5℃'),
(54,1046,'设定28.0℃'),
(55,1047,'设定28.5℃'),
(56,1048,'设定29.0℃'),
(57,1049,'设定29.5℃'),
(58,1050,'设定30.0℃'),
(59,1051,'设定30.5℃'),
(60,1052,'设定31.0℃'),
(61,1053,'设定31.5℃'),
(62,1054,'设定32.0℃'),
(63,1055,'设定32.5℃'),
(64,1056,'设定33.0℃'),
(65,1057,'设定33.5℃'),
(66,1058,'设定34.0℃'),
(67,1059,'设定34.5℃'),
(68,1060,'设定35.0℃');
/*----------end----------*/


