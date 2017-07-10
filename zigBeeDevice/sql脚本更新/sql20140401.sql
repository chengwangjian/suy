/**********************************************2014-04-01 begin 2014-07-02 发布外网*********************************************/
/*添加*/
CREATE TABLE `usermodedevice` (
`ID`  bigint(20) NOT NULL AUTO_INCREMENT ,
`MID`  bigint(20) NULL DEFAULT NULL ,
`HouseID`  bigint(20) NULL DEFAULT NULL ,
`Dest`  bigint(20) NULL DEFAULT NULL ,
PRIMARY KEY (`ID`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

SET FOREIGN_KEY_CHECKS=0;

/*INSERT INTO `userordermain` (`id`, `orderName`, `userId`, `houseId`, `userType`, `userName`, `houseName`, `tel`, `email`, `address`, `contact`, `count`, `suggestion`, `description`, `xmlFile`, `createtime`, `lasttime`) VALUES ('32', '奈伯思标准情景模式导入', '-1', '-1', '0', 'netvox', '小房子', '', '', '地址', '联系人', '1', '建议', '描述', 'd:\\modexml\\小房子-mode.xml;d:\\modexml\\小房子-node.xml', '2013-12-10 09:28:06', '2013-12-10 09:28:06');
INSERT INTO `userordermain` (`id`, `orderName`, `userId`, `houseId`, `userType`, `userName`, `houseName`, `tel`, `email`, `address`, `contact`, `count`, `suggestion`, `description`, `xmlFile`, `createtime`, `lasttime`) VALUES ('33', '奈伯思标准情景模式导入', '-1', '-2', '0', 'netvox', 'Smart Lights', '', '', '地址', '联系人', '1', '建议', '描述', 'd:\\modexml\\Smart Lights-mode.xml;d:\\modexml\\Smart Lights-node.xml', '2013-12-10 09:28:06', '2013-12-10 09:28:06');*/

DROP TABLE IF EXISTS `attributenamelib`;
CREATE TABLE `attributenamelib` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `attributeNameCn` varchar(400) DEFAULT NULL,
  `attributeName` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `attributeName` (`attributeName`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of attributenamelib
-- ----------------------------
INSERT INTO `attributenamelib` VALUES ('1', '开关状态', 'on_off_status');
INSERT INTO `attributenamelib` VALUES ('2', '电流', 'current');
INSERT INTO `attributenamelib` VALUES ('3', '电压', 'voltage');
INSERT INTO `attributenamelib` VALUES ('4', '功率', 'power');
INSERT INTO `attributenamelib` VALUES ('5', '电能', 'energy');
INSERT INTO `attributenamelib` VALUES ('6', '级别控制', 'level');
INSERT INTO `attributenamelib` VALUES ('7', '门锁状态', 'lock_status');
INSERT INTO `attributenamelib` VALUES ('8', '门状态', 'door_status');
INSERT INTO `attributenamelib` VALUES ('9', '亮度', 'brightness');
INSERT INTO `attributenamelib` VALUES ('10', '占有或空闲状态', 'occupied');
INSERT INTO `attributenamelib` VALUES ('11', '温度', 'temperature');
INSERT INTO `attributenamelib` VALUES ('12', '湿度', 'humidity');
INSERT INTO `attributenamelib` VALUES ('13', '开关状态', 'open_close_status');
INSERT INTO `attributenamelib` VALUES ('14', '锁类型', 'lock_type');
INSERT INTO `attributenamelib` VALUES ('15', 'GSM信号强度', 'gsmrssi');
INSERT INTO `attributenamelib` VALUES ('16', '紫外线强度', 'ultraviolet');
INSERT INTO `attributenamelib` VALUES ('17', '红外感应设备失效时间', 'irdisabletime');
INSERT INTO `attributenamelib` VALUES ('18', '设备心跳', 'heartbeat');
INSERT INTO `attributenamelib` VALUES ('19', '停止报警', 'stop');
INSERT INTO `attributenamelib` VALUES ('20', '入侵报警', 'burglar');
INSERT INTO `attributenamelib` VALUES ('21', '火警', 'fire');
INSERT INTO `attributenamelib` VALUES ('22', '紧急报警', 'emergency');
INSERT INTO `attributenamelib` VALUES ('23', '静音', 'mute');
INSERT INTO `attributenamelib` VALUES ('24', '设备故障', 'device trouble');
INSERT INTO `attributenamelib` VALUES ('25', '门铃', 'doorbell');
INSERT INTO `attributenamelib` VALUES ('26', '按时到达', 'on time');
INSERT INTO `attributenamelib` VALUES ('27', '晚归', 'late');
INSERT INTO `attributenamelib` VALUES ('28', '登记或不登记', 'not enroll or enroll');
INSERT INTO `attributenamelib` VALUES ('29', 'Zone设备类型', 'ias zone type');
INSERT INTO `attributenamelib` VALUES ('30', '低压告警设备：ieee ep', '低压告警 ieee ep');
INSERT INTO `attributenamelib` VALUES ('31', '设备状态', 'isonline');

-- ----------------------------
-- Table structure for clientcode
-- ----------------------------
DROP TABLE IF EXISTS `clientcode`;
CREATE TABLE `clientcode` (
  `clientCode` varchar(50) NOT NULL,
  `clientName` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`clientCode`),
  KEY `clientcode` (`clientCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of clientcode
-- ----------------------------
INSERT INTO `clientcode` VALUES ('CS6', 'CS6');
INSERT INTO `clientcode` VALUES ('test', 'test');

-- ----------------------------
-- Table structure for operatelib
-- ----------------------------
DROP TABLE IF EXISTS `operatelib`;
CREATE TABLE `operatelib` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `opname` varchar(200) DEFAULT NULL,
  `optype` varchar(20) DEFAULT NULL,
  `operateName` varchar(400) DEFAULT NULL,
  `operateNameCn` varchar(400) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `opname, optype` (`opname`,`optype`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of operatelib
-- ----------------------------
INSERT INTO `operatelib` VALUES ('1', 'mainsOutLetOperation.cgi', '0', 'TurnOFF', '关');
INSERT INTO `operatelib` VALUES ('2', 'mainsOutLetOperation.cgi', '1', 'TurnON', '开');
INSERT INTO `operatelib` VALUES ('3', 'mainsOutLetOperation.cgi', '2', 'Toggle', '反转');
INSERT INTO `operatelib` VALUES ('4', 'dimmableLightOperation.cgi', '10', 'MoveToLevel', '级别控制');
INSERT INTO `operatelib` VALUES ('5', 'dimmableLightOperation.cgi', '16', 'MoveToLevelWithOnOFF', '开关调级');
INSERT INTO `operatelib` VALUES ('6', 'iasWarningDeviceOperation.cgi', '0', 'StopWarning', '停止报警声');
INSERT INTO `operatelib` VALUES ('7', 'iasWarningDeviceOperation.cgi', '1', 'BurglarWarning', '入侵报警');
INSERT INTO `operatelib` VALUES ('8', 'iasWarningDeviceOperation.cgi', '2', 'FireWarning', '火警');
INSERT INTO `operatelib` VALUES ('9', 'iasWarningDeviceOperation.cgi', '3', 'EmergencyWarning', '紧急报警');
INSERT INTO `operatelib` VALUES ('10', 'iasWarningDeviceOperation.cgi', '4', 'Doorbell', '门铃响了');
INSERT INTO `operatelib` VALUES ('11', 'iasCIEOperation.cgi', '3', 'Disarm', '撤防');
INSERT INTO `operatelib` VALUES ('12', 'iasCIEOperation.cgi', '4', 'ArmDayZone', '布防（Day类型Zone设备）');
INSERT INTO `operatelib` VALUES ('13', 'iasCIEOperation.cgi', '5', 'ArmNightZone', '布防（Night类型Zone设备）');
INSERT INTO `operatelib` VALUES ('14', 'iasCIEOperation.cgi', '10', 'BypassZone', '忽略Zone设备');
INSERT INTO `operatelib` VALUES ('15', 'iasCIEOperation.cgi', '11', 'UnBypassZone', '启用Zone设备');
INSERT INTO `operatelib` VALUES ('16', 'iasCIEOperation.cgi', '12', 'UnEnrollZone', '不登记Zone设备');
INSERT INTO `operatelib` VALUES ('17', 'iasCIEOperation.cgi', '6', 'ArmAllZones', '布防（所有Zone设备）');
INSERT INTO `operatelib` VALUES ('18', 'doorLockOperation.cgi', '7', 'LockDoor', '关锁');
INSERT INTO `operatelib` VALUES ('19', 'doorLockOperation.cgi', '8', 'UnLockDoor', '开锁');
INSERT INTO `operatelib` VALUES ('20', 'doorLockPermitOperatorData.cgi', '-1', 'EncryptLockDoor', '开关锁（加密）');
INSERT INTO `operatelib` VALUES ('21', 'doorLockPermitOperatorData.cgi', null, 'EncryptUnLockDoor', '开关锁（加密）');
INSERT INTO `operatelib` VALUES ('22', 'zbIRApply.cgi --', null, 'ApplyDeviceIR', '发送IR数据');
INSERT INTO `operatelib` VALUES ('23', 'zbIRApply.cgi --', null, 'ApplyDeviceFlashIR', '发送本地IR数据');
INSERT INTO `operatelib` VALUES ('24', 'zbDoMode.cgi 100', '100', 'Do mode', '执行模式');
INSERT INTO `operatelib` VALUES ('25', 'BackupSysData.cgi', '-1', 'BackupSysData', '备份数据');
INSERT INTO `operatelib` VALUES ('26', 'Login', null, 'Login', '登录');
INSERT INTO `operatelib` VALUES ('27', 'zbInitializeGS.cgi --', null, 'InitializeGS', '初始化');
INSERT INTO `operatelib` VALUES ('28', 'DoorLockRemoteAddUser.cgi --', null, 'Add user', '新增用户');
INSERT INTO `operatelib` VALUES ('29', 'DoorLockRemoteDeleteUser.cgi --', null, 'Delete user', '删除用户');
INSERT INTO `operatelib` VALUES ('30', 'setPermitJoinOn.cgi', null, 'SetPermitJoinOn', '打开加网');
INSERT INTO `operatelib` VALUES ('31', 'ArriveDel --', null, 'ArriveDel', 'ArriveDel');
INSERT INTO `operatelib` VALUES ('32', 'ArriveNew --', null, 'ArriveNew', 'ArriveNew');
INSERT INTO `operatelib` VALUES ('33', 'doorLockOperation.cgi 0', '0', 'GetLockState', 'GetLockState');
INSERT INTO `operatelib` VALUES ('34', 'doorLockOperation.cgi 3', '3', 'GetDoorState', 'GetDoorState');
INSERT INTO `operatelib` VALUES ('35', 'iasWarningDeviceOperation.cgi 10', '10', 'GetZoneState', 'GetZoneState');
INSERT INTO `operatelib` VALUES ('36', 'localIASCIEOperation.cgi 3 ', '3', 'GetCIEHeartBeat', 'GetCIEHeartBeat');
INSERT INTO `operatelib` VALUES ('37', 'localIASCIEOperation.cgi', '6', 'DisArm', '撤防');
INSERT INTO `operatelib` VALUES ('38', 'localIASCIEOperation.cgi', '7', 'Arm_All_Zone', '布防（所有Zone设备）');
INSERT INTO `operatelib` VALUES ('39', 'localIASCIEOperation.cgi', '12', 'ByPassZone', '忽略Zone设备');
INSERT INTO `operatelib` VALUES ('40', 'localIASCIEOperation.cgi', '13', 'UnByPassZone', '启用Zone设备');
INSERT INTO `operatelib` VALUES ('41', 'localIASCIEOperation.cgi', '18', 'ArmNightZone', '布防（Night类型Zone设备）');
INSERT INTO `operatelib` VALUES ('42', 'localIASCIEOperation.cgi 19', '19', 'GetArmMode', 'GetArmMode');
INSERT INTO `operatelib` VALUES ('43', 'localIASCIEOperation.cgi 20', '20', 'SetDoorBell', 'SetDoorBell');
INSERT INTO `operatelib` VALUES ('44', 'localIASCIEOperation.cgi 21', '21', 'GetDoorBellStatus', 'GetDoorBellStatus');
INSERT INTO `operatelib` VALUES ('45', 'localIASCIEOperation.cgi 23', '23', 'GetCheckArmTime', 'GetCheckArmTime');
INSERT INTO `operatelib` VALUES ('46', 'mainsOutLetOperation.cgi 16', '16', 'RecalculateEnergy', '重新计算电能');
INSERT INTO `operatelib` VALUES ('47', 'onOffOutputOperation.cgi', '0', 'TurnOn', '开');
INSERT INTO `operatelib` VALUES ('48', 'onOffOutputOperation.cgi', '1', 'TurnOFF', '关');
INSERT INTO `operatelib` VALUES ('49', 'RoomNew', null, 'RoomNew', '新建房间');
INSERT INTO `operatelib` VALUES ('50', 'shadeOperation.cgi', '0', 'TurnOn', '开');
INSERT INTO `operatelib` VALUES ('51', 'shadeOperation.cgi', '1', 'TurnOff', '关');
INSERT INTO `operatelib` VALUES ('52', 'shadeOperation.cgi', '8', 'MoveToLevel', '级别控制');
INSERT INTO `operatelib` VALUES ('53', 'dimmableLightOperation.cgi', '0', 'TurnOff', '关');
INSERT INTO `operatelib` VALUES ('54', 'dimmableLightOperation.cgi', '1', 'TurnOn', '开');
INSERT INTO `operatelib` VALUES ('55', 'dimmableLightOperation.cgi', '2', 'Toggle', '反转');
INSERT INTO `operatelib` VALUES ('56', 'dimmableLightOperation.cgi', '18', 'StepWithOnOFF ', 'StepWithOnOFF');
INSERT INTO `operatelib` VALUES ('57', 'dimmableLightOperation.cgi 23', '23', 'RecalculateEnergy', '重新计算电能');
INSERT INTO `operatelib` VALUES ('58', 'iasWarningDeviceOperation.cgi', '5', 'StartWarningTrouble', 'StartWarningTrouble');
INSERT INTO `operatelib` VALUES ('59', 'iasWarningDeviceOperation.cgi 8', '8', 'SetDuration', 'SetDuration');
INSERT INTO `operatelib` VALUES ('60', 'iasWarningDeviceOperation.cgi', '9', 'FireWarning', '火警');
INSERT INTO `operatelib` VALUES ('61', 'iasWarningDeviceOperation.cgi 14', '14', 'GetPhoneMsg', 'GetPhoneMsg');
INSERT INTO `operatelib` VALUES ('62', 'iasWarningDeviceOperation.cgi 15', '15', 'WriteHeartBeatPeriod', 'WriteHeartBeatPeriod');
INSERT INTO `operatelib` VALUES ('63', 'iasWarningDeviceOperation.cgi 17', '17', 'GetDeviceHeartBeat', 'GetDeviceHeartBeat');
INSERT INTO `operatelib` VALUES ('64', 'localIASCIEOperation.cgi', '10', 'FireWarn', '火警');
INSERT INTO `operatelib` VALUES ('65', 'localIASCIEOperation.cgi', '11', 'PanicWarn', '恐慌报警');
INSERT INTO `operatelib` VALUES ('66', 'onOffOutputOperation.cgi', '2', 'Toggle', '反转');
INSERT INTO `operatelib` VALUES ('67', 'RoomDel', null, 'RoomDel', '删除房间');
INSERT INTO `operatelib` VALUES ('68', 'localIASCIEOperation.cgi', '9', 'EmergencyWarn', '紧急报警');
INSERT INTO `operatelib` VALUES ('69', 'shadeOperation.cgi', '2', 'Toggle', '反转');
INSERT INTO `operatelib` VALUES ('70', 'shadeOperation.cgi', '8', 'TurnOff', '关');
INSERT INTO `operatelib` VALUES ('71', 'shadeOperation.cgi', '16', 'StepWithOnOFF', 'StepWithOnOFF');

-- ----------------------------
-- Table structure for relilevel
-- ----------------------------
DROP TABLE IF EXISTS `relilevel`;
CREATE TABLE `relilevel` (
  `id` int(11) NOT NULL,
  `level_name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of relilevel
-- ----------------------------
INSERT INTO `relilevel` VALUES ('1', '高级');
INSERT INTO `relilevel` VALUES ('2', '中级');
INSERT INTO `relilevel` VALUES ('3', '初级');

-- ----------------------------
-- Table structure for relilevelprivilege
-- ----------------------------
DROP TABLE IF EXISTS `relilevelprivilege`;
CREATE TABLE `relilevelprivilege` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `level_id` int(11) DEFAULT NULL,
  `privilege_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of relilevelprivilege
-- ----------------------------
INSERT INTO `relilevelprivilege` VALUES ('1', '1', '1');
INSERT INTO `relilevelprivilege` VALUES ('2', '1', '2');
INSERT INTO `relilevelprivilege` VALUES ('3', '1', '3');
INSERT INTO `relilevelprivilege` VALUES ('4', '1', '4');
INSERT INTO `relilevelprivilege` VALUES ('5', '1', '5');
INSERT INTO `relilevelprivilege` VALUES ('6', '2', '4');
INSERT INTO `relilevelprivilege` VALUES ('7', '2', '5');
INSERT INTO `relilevelprivilege` VALUES ('8', '3', '5');
INSERT INTO `relilevelprivilege` VALUES ('9', '1', '6');
INSERT INTO `relilevelprivilege` VALUES ('10', '1', '7');

-- ----------------------------
-- Table structure for reliprivilege
-- ----------------------------
DROP TABLE IF EXISTS `reliprivilege`;
CREATE TABLE `reliprivilege` (
  `id` int(11) NOT NULL,
  `privilege_name` varchar(30) DEFAULT NULL,
  `privilege_code` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of reliprivilege
-- ----------------------------
INSERT INTO `reliprivilege` VALUES ('1', '新增管理员', 'add_admin');
INSERT INTO `reliprivilege` VALUES ('2', '修改管理员密码', 'update_admin_pwd');
INSERT INTO `reliprivilege` VALUES ('3', '删除管理员', 'delete_admin');
INSERT INTO `reliprivilege` VALUES ('4', '数据进行处理及分析', 'analysis_data');
INSERT INTO `reliprivilege` VALUES ('5', '修改自己的密码', 'update_self_pwd');
INSERT INTO `reliprivilege` VALUES ('6', '登录管理员界面', 'login_admin_page');
INSERT INTO `reliprivilege` VALUES ('7', '云服务管理', 'cloud_manage');

-- ----------------------------
-- Table structure for hardhistory
-- ----------------------------
DROP TABLE IF EXISTS `hardhistory`;
CREATE TABLE `hardhistory` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `HwVersion` varchar(20) DEFAULT NULL,
  `Extention` varchar(255) DEFAULT NULL,
  `ReleaseHistoryId` bigint(20) DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for houseservice
-- ----------------------------
DROP TABLE IF EXISTS `houseservice`;
CREATE TABLE `houseservice` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `houseIEEE` varchar(50) DEFAULT NULL,
  `videoService` tinyint(4) DEFAULT '0',
  `leaveHomeInform` tinyint(4) DEFAULT '0',
  `lqi_open` tinyint(4) DEFAULT '0',
  `lqi_open_time` varchar(10) DEFAULT NULL,
  `min_lqi` smallint(6) DEFAULT NULL,
  `device_is_online` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for releasehistory
-- ----------------------------
DROP TABLE IF EXISTS `releasehistory`;
CREATE TABLE `releasehistory` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `UpdateTime` datetime DEFAULT NULL,
  `FileName` varchar(255) DEFAULT NULL,
  `DownloadInfo` varchar(255) DEFAULT NULL,
  `Type` int(11) DEFAULT NULL,
  `Ver1` int(255) DEFAULT NULL,
  `Ver2` int(255) DEFAULT NULL,
  `Ver3` int(255) DEFAULT NULL,
  `Ver4` int(255) DEFAULT NULL,
  `Description` varchar(255) DEFAULT NULL,
  `FileSize` varchar(255) DEFAULT NULL,
  `Notifier` int(4) DEFAULT NULL,
  `Version` varchar(255) DEFAULT NULL,
  `HwVersion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for reliuser
-- ----------------------------
DROP TABLE IF EXISTS `reliuser`;
CREATE TABLE `reliuser` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(100) DEFAULT NULL,
  `pwd` varchar(40) DEFAULT NULL,
  `level_id` tinyint(4) DEFAULT '3',
  `enabled` varchar(2) DEFAULT '1',
  `regist_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`,`regist_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rssirecord
-- ----------------------------
DROP TABLE IF EXISTS `rssirecord`;
CREATE TABLE `rssirecord` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `source_ieee` varchar(50) DEFAULT NULL,
  `dest_ieee` varchar(50) DEFAULT NULL,
  `RSSI` smallint(6) DEFAULT NULL,
  `count_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `push_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `house_ieee` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`,`count_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8
PARTITION BY RANGE (YEAR(count_time)*100+MONTH(count_time))
(PARTITION r201406 VALUES LESS THAN (201406) ENGINE = InnoDB,
 PARTITION r201407 VALUES LESS THAN (201407) ENGINE = InnoDB,
 PARTITION r201408 VALUES LESS THAN (201408) ENGINE = InnoDB,
 PARTITION r201409 VALUES LESS THAN (201409) ENGINE = InnoDB,
 PARTITION r201410 VALUES LESS THAN (201410) ENGINE = InnoDB,
 PARTITION r201411 VALUES LESS THAN (201411) ENGINE = InnoDB,
 PARTITION r201412 VALUES LESS THAN (201412) ENGINE = InnoDB,
 PARTITION r201501 VALUES LESS THAN (201501) ENGINE = InnoDB,
 PARTITION r201502 VALUES LESS THAN (201502) ENGINE = InnoDB,
 PARTITION r201503 VALUES LESS THAN (201503) ENGINE = InnoDB,
 PARTITION r201504 VALUES LESS THAN (201504) ENGINE = InnoDB,
 PARTITION r201505 VALUES LESS THAN (201505) ENGINE = InnoDB,
 PARTITION r201506 VALUES LESS THAN (201506) ENGINE = InnoDB,
 PARTITION r201507 VALUES LESS THAN (201507) ENGINE = InnoDB,
 PARTITION r201508 VALUES LESS THAN (201508) ENGINE = InnoDB,
 PARTITION r201509 VALUES LESS THAN (201509) ENGINE = InnoDB,
 PARTITION r201510 VALUES LESS THAN (201510) ENGINE = InnoDB,
 PARTITION r201511 VALUES LESS THAN (201511) ENGINE = InnoDB,
 PARTITION r201512 VALUES LESS THAN (201512) ENGINE = InnoDB,
 PARTITION rcatchall VALUES LESS THAN MAXVALUE ENGINE = InnoDB);

-- ----------------------------
-- Table structure for updatehistory
-- ----------------------------
DROP TABLE IF EXISTS `updatehistory`;
CREATE TABLE `updatehistory` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `UpdateTime` datetime DEFAULT NULL,
  `HouseIEEE` varchar(255) DEFAULT NULL,
  `BeforeVer` varchar(255) DEFAULT NULL,
  `AfterVer` varchar(255) DEFAULT NULL,
  `ReleaseHistoryID` bigint(20) DEFAULT NULL,
  `UpdateStatus` varchar(20) DEFAULT NULL,
  `UpdateOperate` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for useraccount
-- ----------------------------
DROP TABLE IF EXISTS `useraccount`;
CREATE TABLE `useraccount` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_guid` varchar(50) DEFAULT NULL,
  `user_name` varchar(100) DEFAULT NULL,
  `PWD` varchar(50) DEFAULT NULL,
  `from_house_ieee` varchar(50) DEFAULT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modify_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `email` varchar(50) DEFAULT NULL,
  `TEL` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`,`create_time`),
  KEY `user_guid_index` (`user_guid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for userrelevance
-- ----------------------------
DROP TABLE IF EXISTS `userrelevance`;
CREATE TABLE `userrelevance` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `MID` bigint(20) DEFAULT NULL,
  `from_house_ieee` varchar(50) DEFAULT NULL,
  `reg2_house_ieee` varchar(50) DEFAULT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`,`create_time`),
  KEY `mid_index` (`MID`) USING BTREE,
  KEY `reg2_house_ieee_index` (`reg2_house_ieee`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/*修改*/
ALTER TABLE `deviceattribute`
ADD COLUMN `daemonDeviceId`  bigint(20) NULL DEFAULT 0 AFTER `room_id`;
ALTER TABLE `deviceattribute`
ADD COLUMN `source_id`  bigint(20) NULL DEFAULT 1 AFTER `daemonDeviceId`;
ALTER TABLE `deviceattribute`
ADD COLUMN `deviceType`  bigint(20) NULL DEFAULT 1 AFTER `source_id`;
ALTER TABLE `deviceattribute`
ADD COLUMN `id_string3`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `device_ep`;

ALTER TABLE `deviceattributehistory_houseid_year`
ADD COLUMN `daemonDeviceId`  bigint(20) NULL DEFAULT 0 AFTER `room_id`;
ALTER TABLE `deviceattributehistory_houseid_year`
ADD COLUMN `source_id`  bigint(20) NULL DEFAULT 1 AFTER `daemonDeviceId`;
ALTER TABLE `deviceattributehistory_houseid_year`
ADD COLUMN `deviceType`  bigint(20) NULL DEFAULT 1 AFTER `source_id`;
ALTER TABLE `deviceattributehistory_houseid_year`
ADD COLUMN `id_string3`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `device_ep`;

ALTER TABLE `deviceoperatehistory_houseid_year`
ADD COLUMN `daemonDeviceId`  bigint(20) NULL DEFAULT 0 AFTER `room_id`;
ALTER TABLE `deviceoperatehistory_houseid_year`
ADD COLUMN `source_id`  bigint(20) NULL DEFAULT 1 AFTER `daemonDeviceId`;
ALTER TABLE `deviceoperatehistory_houseid_year`
ADD COLUMN `deviceType`  bigint(20) NULL DEFAULT 1 AFTER `source_id`;
ALTER TABLE `deviceoperatehistory_houseid_year`
ADD COLUMN `id_string3`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `device_ep`;

ALTER TABLE `devicewarnhistory_houseid_year`
ADD COLUMN `daemonDeviceId`  bigint(20) NULL DEFAULT 0 AFTER `room_id`;
ALTER TABLE `devicewarnhistory_houseid_year`
ADD COLUMN `source_id`  bigint(20) NULL DEFAULT 1 AFTER `daemonDeviceId`;
ALTER TABLE `devicewarnhistory_houseid_year`
ADD COLUMN `deviceType`  bigint(20) NULL DEFAULT 1 AFTER `source_id`;
ALTER TABLE `devicewarnhistory_houseid_year`
ADD COLUMN `id_string3`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `zone_ep`;
/*
ALTER TABLE `Modenodelib`
ADD COLUMN `source_id`  bigint(20) NULL DEFAULT 1 AFTER `destType`;

ALTER TABLE `modeeplib`
ADD COLUMN `deviceTypeV2`  bigint(20) NULL DEFAULT 1 AFTER `deviceType`;
*/

ALTER TABLE `Modenode`
ADD COLUMN `source_id`  bigint(20) NULL DEFAULT 1 AFTER `modeNodeLibId`;

ALTER TABLE `modedevice`
ADD COLUMN `source_id`  bigint(20) NULL DEFAULT 1 AFTER `modelId`;
ALTER TABLE `modedevice`
ADD COLUMN `deviceType`  bigint(20) NULL DEFAULT 1 AFTER `source_id`;

ALTER TABLE `device`
ADD COLUMN `device_id`  bigint(20) NULL DEFAULT 0 AFTER `type`;
ALTER TABLE `device`
ADD COLUMN `source_id`  bigint(20) NULL DEFAULT 1 AFTER `device_id`;
ALTER TABLE `device`
ADD COLUMN `device_type`  bigint(20) NULL DEFAULT 1 AFTER `source_id`;
ALTER TABLE `device`
ADD COLUMN `AttrInfo`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL  AFTER `device_type`;
ALTER TABLE `device`
ADD COLUMN `isonline`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' AFTER `AttrInfo`;
ALTER TABLE `device`
ADD COLUMN `createtime`  datetime NULL DEFAULT CURRENT_TIMESTAMP  AFTER `isonline`;
ALTER TABLE `device`
ADD COLUMN `lasttime`  datetime NULL DEFAULT CURRENT_TIMESTAMP  AFTER `createtime`;

ALTER TABLE `house`
ADD COLUMN `regionCode`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '厦门' AFTER `enableFlag`;
ALTER TABLE `house`
ADD COLUMN `isonline`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' AFTER `regionCode`;
ALTER TABLE `house`
ADD COLUMN `clientCode`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `lasttime`;

ALTER TABLE `devicepicsetlib`
ADD COLUMN `houseId`  bigint(20) NULL DEFAULT '-1' AFTER `id`;

ALTER TABLE `modescenesub`
ADD COLUMN `Status`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `Para`;

ALTER TABLE `modegroupsub`
ADD COLUMN `Status`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `groupName`;

ALTER TABLE `usermodemain`
ADD COLUMN `EnableCheckOnOff`  tinyint(4) NULL DEFAULT 0 AFTER `Description`;
ALTER TABLE `usermodemain`
ADD COLUMN `orderId`  int(11) NULL DEFAULT 0 AFTER `EnableCheckOnOff`;

ALTER TABLE `modeuser`
ADD COLUMN `level_id`  tinyint(4) NULL DEFAULT 3 AFTER `lasttime`;

/*模式编辑器权限表*/
/*等级表*/
CREATE TABLE modelevel (
  id int NOT NULL,
  level_name varchar(30) DEFAULT NUll,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/*权限表*/
CREATE TABLE modeprivilege (
  id int NOT NULL,
  privilege_name varchar(30) DEFAULT NULL,
  privilege_code varchar(30) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/*等级权限表*/
CREATE TABLE modelevelprivilege (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  level_id int DEFAULT NULL,
  privilege_id int DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/*插入模式权限表*/
INSERT INTO `modeprivilege` VALUES ('1', '模式编辑器后台管理', 'mode_server_manage');
INSERT INTO `modeprivilege` VALUES ('2', '设备管理', 'mode_device_manage');
INSERT INTO `modeprivilege` VALUES ('3', '动作管理', 'mode_action_manage');
INSERT INTO `modeprivilege` VALUES ('4', '用户权限管理', 'mode_user_privilege_manage');

/*插入等级表*/
INSERT INTO `modelevel` VALUES ('1', '高级管理员');
INSERT INTO `modelevel` VALUES ('2', '管理员');
INSERT INTO `modelevel` VALUES ('3', '普通用户');

/*插入模式等级权限关联关系*/
INSERT INTO `modelevelprivilege` VALUES ('1', '1', '1');
INSERT INTO `modelevelprivilege` VALUES ('2', '1', '2');
INSERT INTO `modelevelprivilege` VALUES ('3', '1', '3');
INSERT INTO `modelevelprivilege` VALUES ('4', '1', '4');
INSERT INTO `modelevelprivilege` VALUES ('5', '2', '1');
INSERT INTO `modelevelprivilege` VALUES ('6', '2', '2');
INSERT INTO `modelevelprivilege` VALUES ('7', '2', '3');

/*存储过程 Modescheme、ModesubTest存储过程修改。增加存储过程SPLIT_SUB_STR0、Sorted_Sub_List0 */
/*存储过程*/
DROP PROCEDURE IF EXISTS `Sorted_Sub_List0`;
CREATE PROCEDURE `Sorted_Sub_List0`(IN behavior bigint,IN `houseId` bigint,IN `mid` bigint,IN str VARCHAR(1000))
BEGIN 
	/*目标字符串*/
	set @str = str, @c = '左边',	@b = 1;
	set @houseid=houseId;
	set @mid=mid;
CASE
WHEN behavior=2 THEN
	WHILE @str <> '' DO
			/*调用上面的存储过程*/
			CALL SPLIT_SUB_STR0(@str, ',', @c);
			update usermodemain m set m.orderId=@b where m.ID=@c;
			set @b=@b+1;
	END WHILE;

WHEN behavior=0 THEN
  update usermodemain m SET m.EnableCheckOnOff=0 WHERE m.id=@mid;
	delete d from usermodedevice d where d.HouseID=@houseid and d.MID=@mid;
WHEN behavior=1 THEN
	update usermodemain m SET m.EnableCheckOnOff=1 WHERE m.id=@mid;
	delete d from usermodedevice d where d.HouseID=@houseid and d.MID=@mid;
	WHILE @str <> '' DO
			/*调用上面的存储过程*/
			CALL SPLIT_SUB_STR0(@str, ',', @c);
			insert into usermodedevice(MID,HouseID,Dest) values(@mid,@houseid,@c);
	END WHILE;

WHEN behavior=3 THEN
	Select d.id as deviceId,d.roomId,d.deviceName,r.roomName 
	from modedevice d inner join Moderoom r on d.roomId=r.roomId and d.houseId=r.houseId		
	where d.HouseID=@houseid and(d.deviceId='0002' or d.deviceId='0009' or d.deviceId='0100' or d.deviceId='0101' or d.deviceId='0102')
	ORDER BY r.roomId;

WHEN behavior=4 THEN
	select d.* from usermodedevice d where d.HouseID=@houseid and d.MID=@mid;	

END CASE;
END;

DROP PROCEDURE IF EXISTS `SPLIT_SUB_STR0`;
CREATE PROCEDURE `SPLIT_SUB_STR0`(inout str VARCHAR(1000) ,delimiter VARCHAR(1), out split1 VARCHAR(500))
BEGIN 

/*分割出第一段字符串不包括分隔符的长度*/
DECLARE SUB_STR_LENGTH INT; 

/*SUBSTRING_INDEX函数取得目标字符串左侧第n个分割符左侧的部分，n为负时返回右侧第n个的右部分*/
SET SUB_STR_LENGTH = length(SUBSTRING_INDEX(str,delimiter,1));

/*截取第一段字符串，不包括分隔符，放入输出参数里*/
SET split1 = substring(str, 1, SUB_STR_LENGTH); 

/*取得去掉第一个字符串和分隔符的字符串，进行下次循环取得下个字符串*/
SET str = substring(str, SUB_STR_LENGTH + 2 ); 
/*测试一下输出是否正确*/
SELECT SUB_STR_LENGTH, str, SPLIT1;

END; 

/*Modescheme存储过程*/
DROP PROCEDURE IF EXISTS `Modescheme`;
CREATE PROCEDURE `Modescheme`(IN `behavior` bigint,IN `houseId` bigint,IN `dest` bigint,IN `id` bigint)
BEGIN
	/*Routine body goes here...*/

IF behavior=1 THEN
	select u.* from modeschemesub u where u.id=id;	
ELSEIF behavior=2 THEN
	select m.* from modeschememain m, modeschemesub u where m.id=u.mid and u.id=id;
ELSEIF behavior=3 THEN
	select um.* from usermodesub um,modeschememain m, modeschemesub u
	where m.id=u.mid and um.Dest=u.mid and u.id=id 
	and (um.Act='DeactiveScheme' or um.Act='ActiveScheme' or um.Act='IgnoreScheme');

ELSEIF behavior=4 THEN	
	delete d from usermodesub d where d.Dest in
		(Select u.ID from modeschememain u where d.Act='ActiveScheme' and u.HouseID=HouseID);
	insert into usermodesub (MID,HouseID,Act,Dest,Selectss)
		Select m.ID,m.HouseID,'ActiveScheme',u.id,1 from usermodemain m ,modeschememain u
		where m.HouseID=u.HouseID and u.bAllmodeActive=1 and u.HouseID=HouseID;
ELSEIF behavior=5 THEN
	delete d from usermodesub d where d.Dest in
		(Select u.ID from modeschememain u where d.Act='ActiveIAS' and u.HouseID=HouseID);
	insert into usermodesub (MID,HouseID,Act,Dest,Selectss)
		Select m.ID,m.HouseID,'ActiveIAS',u.id,1 from usermodemain m ,modeiasmain u
		where m.HouseID=u.HouseID and u.bAllmodeActive=1 and u.HouseID=HouseID;
ELSEIF behavior=6 THEN
	delete d from usermodesub d where d.Dest in
		(Select u.ID from modeschememain u where d.Act='ActiveHVAC' and u.HouseID=HouseID);
	insert into usermodesub (MID,HouseID,Act,Dest,Selectss)
		Select m.ID,m.HouseID,'ActiveHVAC',u.id,1 from usermodemain m ,modehvacmain u
		where m.HouseID=u.HouseID and u.bAllmodeActive=1 and u.HouseID=HouseID;

ELSEIF behavior=7 THEN
		Select t.* from modeactlib t GROUP BY t.ActName,t.ExtentionSet ORDER BY t.id;
ELSEIF behavior=8 THEN
		Select t.* from modeactlib t where t.id=id;
END IF;
END;

/*ModesubTest存储过程*/
DROP PROCEDURE IF EXISTS `ModesubTest`;
CREATE PROCEDURE `ModesubTest`(IN `behavior` bigint,IN `houseId` bigint,IN `dest` bigint,IN mid varchar(1000))
BEGIN
	/*Routine body goes here...*/
DECLARE aSql,bSql,cSql VARCHAR(4000);
	set @MID=mid ,@c = '左边' ,@b = 0;
	set @HouseID=houseId;
	set @Dest=dest;
CASE
WHEN behavior=1 THEN
	set @aSql='delete from usermodesub where Dest=? and HouseID=? and 
						(Act=''DeactiveIAS'' or Act=''ActiveIAS'' or Act=''IgnoreIAS'')';
		PREPARE stmtselect FROM @aSql;        
		EXECUTE stmtselect USING @Dest,@HouseID;
	
	set @aSql='',@bSql='',@cSql=''; 
	REPEAT
			CALL SPLIT_SUB_STR0(@mid, ';', @c);
			/*将取得的字符串拼接，测试用*/      
			IF (@b = 0) THEN
         /*Select @c,@b;*/
          if (@c<>'' ) then
					  select Concat('(t.id=',REPLACE(@c,',',' or t.id='),')') into @aSql;	
          end if;
          set @b = 1;
			ELSEIF (@b = 1)  THEN 
          /*Select @c,@b;*/
          if (@c<>'' ) then
					  select Concat('(t.id=',REPLACE(@c,',',' or t.id='),')') into @bSql;	
          end if;
          set @b = 2;
			ELSEIF @b = 2 THEN 
					/*Select @c,@b;*/
					if (@c<>'' ) then
						select Concat('(t.id=',REPLACE(@c,',',' or t.id='),')') into @cSql;
					end if;
			END IF;
	/*当目标字符串为空时，停止循环*/
	UNTIL @mid = ''
	END REPEAT;
	
	IF(@aSql<>'') THEN
			Set @aSql=CONCAT(
				'insert into usermodesub (MID,HouseID,Act,Dest,Selectss)
						Select t.ID,t.HouseID,''DeactiveIAS'',?,0 from usermodemain t where t.houseId=? and ',@aSql);
				PREPARE stmtselect FROM @aSql;        
				EXECUTE stmtselect USING @Dest,@HouseID;	
	END IF;
	IF (@bSql<>'' ) THEN
			Set @aSql=CONCAT(
				'insert into usermodesub (MID,HouseID,Act,Dest,Selectss)
						Select t.ID,t.HouseID,''ActiveIAS'',?,1 from usermodemain t where t.houseId=? and ',@bSql);
				PREPARE stmtselect FROM @aSql;        
				EXECUTE stmtselect USING @Dest,@HouseID;
	END IF;
	IF (@cSql<>'' ) THEN
			Set @aSql=CONCAT(
				'insert into usermodesub (MID,HouseID,Act,Dest,Selectss)
						Select t.ID,t.HouseID,''IgnoreIAS'',?,2 from usermodemain t where t.houseId=? and ',@cSql);
				PREPARE stmtselect FROM @aSql;        
				EXECUTE stmtselect USING @Dest,@HouseID;
	END IF;

WHEN behavior=2 THEN
	set @aSql='delete from usermodesub where Dest=? and HouseID=? and 
						(Act=''DeactiveHVAC'' or Act=''ActiveHVAC'' or Act=''IgnoreHVAC'')';
		PREPARE stmtselect FROM @aSql;        
		EXECUTE stmtselect USING @Dest,@HouseID;

	set @aSql='',@bSql='',@cSql='';  
	REPEAT
			CALL SPLIT_SUB_STR0(@mid, ';', @c);
			/*将取得的字符串拼接，测试用*/      
			IF (@b = 0) THEN
          /*Select @c,@b;*/
          if (@c<>'' ) then
					  select Concat('(t.id=',REPLACE(@c,',',' or t.id='),')') into @aSql;	
          end if;
          set @b = 1;
			ELSEIF (@b = 1)  THEN 
          /*Select @c,@b;*/
          if (@c<>'' ) then
					  select Concat('(t.id=',REPLACE(@c,',',' or t.id='),')') into @bSql;	
          end if;
          set @b = 2;
			ELSEIF @b = 2 THEN 
					/*Select @c,@b;*/
					if (@c<>'' ) then
						select Concat('(t.id=',REPLACE(@c,',',' or t.id='),')') into @cSql;
					end if;
			END IF;
	/*当目标字符串为空时，停止循环*/
	UNTIL @mid = ''
	END REPEAT;
	
	IF (@aSql<>'') THEN
			Set @aSql=CONCAT(
				'insert into usermodesub (MID,HouseID,Act,Dest,Selectss)
						Select t.ID,t.HouseID,''DeactiveHVAC'',?,0 from usermodemain t where t.houseId=? and ',@aSql);
				PREPARE stmtselect FROM @aSql;        
				EXECUTE stmtselect USING @Dest,@HouseID;
	END IF;	
	IF (@bSql<>'' ) THEN
			Set @aSql=CONCAT(
				'insert into usermodesub (MID,HouseID,Act,Dest,Selectss)
						Select t.ID,t.HouseID,''ActiveHVAC'',?,1 from usermodemain t where t.houseId=? and ',@bSql);
				PREPARE stmtselect FROM @aSql;        
				EXECUTE stmtselect USING @Dest,@HouseID;	
	END IF;
	IF (@cSql<>'' ) THEN
			Set @aSql=CONCAT(
				'insert into usermodesub (MID,HouseID,Act,Dest,Selectss)
						Select t.ID,t.HouseID,''IgnoreHVAC'',?,2 from usermodemain t where t.houseId=? and ',@cSql);
				PREPARE stmtselect FROM @aSql;        
				EXECUTE stmtselect USING @Dest,@HouseID;
	END IF;

WHEN behavior=3 THEN
	set @aSql='delete from usermodesub where Dest=? and HouseID=? and 
						(Act=''DeactiveScheme'' or Act=''ActiveScheme'' or Act=''IgnoreScheme'')';
		PREPARE stmtselect FROM @aSql;        
		EXECUTE stmtselect USING @Dest,@HouseID;

	set @aSql='',@bSql='',@cSql=''; 
	REPEAT
			CALL SPLIT_SUB_STR0(@mid, ';', @c);
			/*将取得的字符串拼接，测试用*/
			IF (@b = 0) THEN
          /*Select @c,@b;*/
          if (@c<>'' ) then
					  select Concat('(t.id=',REPLACE(@c,',',' or t.id='),')') into @aSql;	
          end if;
          set @b = 1;
			ELSEIF (@b = 1)  THEN 
          /*Select @c,@b;*/
          if (@c<>'' ) then
					  select Concat('(t.id=',REPLACE(@c,',',' or t.id='),')') into @bSql;	
          end if;
          set @b = 2;
			ELSEIF @b = 2 THEN 
					/*Select @c,@b;*/
					if (@c<>'' ) then
						select Concat('(t.id=',REPLACE(@c,',',' or t.id='),')') into @cSql;
					end if;
			END IF;
	/*当目标字符串为空时，停止循环*/
	UNTIL @mid = ''
	END REPEAT;
	
	IF (@aSql<>'') THEN
			Set @aSql=CONCAT(
				'insert into usermodesub (MID,HouseID,Act,Dest,Selectss)
						Select t.ID,t.HouseID,''DeactiveScheme'',?,0 from usermodemain t where t.houseId=? and ',@aSql);
				PREPARE stmtselect FROM @aSql;        
				EXECUTE stmtselect USING @Dest,@HouseID;
	END IF;
	IF (@bSql<>'' ) THEN	
			Set @aSql=CONCAT(
				'insert into usermodesub (MID,HouseID,Act,Dest,Selectss)
						Select t.ID,t.HouseID,''ActiveScheme'',?,1 from usermodemain t where t.houseId=? and ',@bSql);
				PREPARE stmtselect FROM @aSql;        
				EXECUTE stmtselect USING @Dest,@HouseID;
	END IF;
	IF (@cSql<>'' ) THEN	
			Set @aSql=CONCAT(
				'insert into usermodesub (MID,HouseID,Act,Dest,Selectss)
						Select t.ID,t.HouseID,''IgnoreScheme'',?,2 from usermodemain t where t.houseId=? and ',@cSql);
				PREPARE stmtselect FROM @aSql;        
				EXECUTE stmtselect USING @Dest,@HouseID;
	END IF;


WHEN behavior=4 THEN
	select Concat('(d1.id=',REPLACE(@MID,',',' or d1.id='),')') into @aSql;
	set @aSql=Concat(
'select Count(*) as gCount FROM
(Select M1.modeNodeId,Sum(M1.sCount) as sCount,M1.GroupDec from
	  (select d3.modeNodeId,count(distinct d3.ID,modegroupsub.MID) as sCount,devicelimit.GroupDec 
		from modegroupsub inner join (select d2.modeNodeId,d2.id,d2.modelId from modedevice D1 inner join modedevice d2 on d1.modenodeid=d2.modenodeid
                                  where ',@aSql,') d3 on modegroupsub.DeviceID=d3.id
                      inner join devicelimit on d3.modelId=devicelimit.ModelID
		where modegroupsub.HouseID=?
		group by d3.modeNodeId
union all
		select d3.modeNodeId,count(distinct d3.ID) as sCount,devicelimit.GroupDec 
		from (select D1.modeNodeId,D1.id,D1.modelID from modedevice D1 
                                  where ',@aSql,') d3 
                      inner join devicelimit on d3.modelId=devicelimit.ModelID	
		group by d3.modeNodeId
		) M1 group by M1.modeNodeId
) main where sCount>GroupDec'
);
	PREPARE stmtselect FROM @aSql;
	EXECUTE stmtselect USING @HouseID;

WHEN behavior=5 THEN
	select Concat('(d1.id=',REPLACE(@MID,',',' or d1.id='),')') into @aSql;
	set @aSql=Concat('
Select D1.id,D1.devicename from 
(select Main.modeNodeId FROM
	(Select M1.modeNodeId,Sum(M1.sCount) as sCount,M1.GroupDec from
	  (select d3.modeNodeId,count(distinct d3.ID,modegroupsub.MID) as sCount,devicelimit.GroupDec 
		from modegroupsub inner join (select d2.modeNodeId,d2.id,d2.modelId from modedevice D1 inner join modedevice d2 on d1.modenodeid=d2.modenodeid
                                  where ',@aSql,') d3 on modegroupsub.DeviceID=d3.id
                      inner join devicelimit on d3.modelId=devicelimit.ModelID
		where modegroupsub.HouseID=?
		group by d3.modeNodeId
union all
		select d3.modeNodeId,count(distinct d3.ID) as sCount,devicelimit.GroupDec 
		from (select D1.modeNodeId,D1.id,D1.modelID from modedevice D1 
                                  where ',@aSql,') d3 
                      inner join devicelimit on d3.modelId=devicelimit.ModelID	
		group by d3.modeNodeId
		)M1 group by M1.modeNodeId
	)Main	where sCount>GroupDec
)MNode INNER JOIN modedevice d1 on MNode.ModeNodeID=d1.ModeNodeID
where ',@aSql,''
);
	PREPARE stmtselect FROM @aSql;
	EXECUTE stmtselect USING @HouseID;

WHEN behavior=6 THEN
	select Concat('(d1.id=',REPLACE(@MID,',',' or d1.id='),')') into @aSql;
	set @aSql=Concat('
Select D1.id,D1.devicename from 
(select Main.modeNodeId FROM
	(Select M1.modeNodeId,Sum(M1.sCount) as sCount,M1.SceneDec from
	  (select d3.modeNodeId,count(distinct d3.ID,modescenesub.MID) as sCount,devicelimit.SceneDec 
		from modescenesub inner join (select d2.modeNodeId,d2.id,d2.modelId from modedevice D1 inner join modedevice d2 on d1.modenodeid=d2.modenodeid
                                  where ',@aSql,') d3 on modescenesub.DeviceID=d3.id
                      inner join devicelimit on d3.modelId=devicelimit.ModelID
		where modescenesub.HouseID=?
		group by d3.modeNodeId
union all
		select d3.modeNodeId,count(distinct d3.ID) as sCount,devicelimit.SceneDec 
		from (select D1.modeNodeId,D1.id,D1.modelID from modedevice D1 
                                  where ',@aSql,') d3 
                      inner join devicelimit on d3.modelId=devicelimit.ModelID	
		group by d3.modeNodeId
		)M1 group by M1.modeNodeId
	)Main	where sCount>SceneDec
)MNode INNER JOIN modedevice d1 on MNode.ModeNodeID=d1.ModeNodeID
where ',@aSql,''
);
	PREPARE stmtselect FROM @aSql;
	EXECUTE stmtselect USING @HouseID;


END CASE;

END;
/**********************************************2014-07-01 end*********************************************/