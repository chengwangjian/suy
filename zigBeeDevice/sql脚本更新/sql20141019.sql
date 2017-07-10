/*burn in 记录表*/
CREATE TABLE `brinhouse` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userid` bigint(20) DEFAULT NULL,
  `starttime` datetime DEFAULT NULL,
  `endtime` datetime DEFAULT NULL,
  `rFlag` tinyint(2) DEFAULT '0',
  `houseIEEE` varchar(50) DEFAULT NULL,
  `deviceAllFlag` tinyint(2) DEFAULT '0',
  `netFlag` tinyint(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/*IR匹配记录表*/
CREATE TABLE `irmatchrec` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `brandName` varchar(50) DEFAULT NULL,
  `modelName` varchar(50) DEFAULT NULL,
  `controllType` int DEFAULT NULL,
  `checked` tinyint(2) DEFAULT NULL,
  `enbrandName` varchar(100) DEFAULT NULL,
  `matchPercent` tinyint(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/*增加buinin权限*/
insert into reliprivilege values(8,'burn in','burnin');
insert into reliprivilege values(9,'远程升级管理','remoteUpgrade');
insert into reliprivilege values(10,'家庭注册管理','houseRegister');
insert into reliprivilege values(11,'家庭概况','houseInfo');
insert into reliprivilege values(12,'IR库管理','irBase');
insert into relilevelprivilege values(11,1,8);
insert into relilevelprivilege values(12,2,8);
insert into relilevelprivilege values(13,3,8);
insert into relilevelprivilege values(14,1,9);
insert into relilevelprivilege values(15,1,10);
insert into relilevelprivilege values(16,1,11);
insert into relilevelprivilege values(17,1,12);

/* 系统日志表 2014.10.20*/
CREATE TABLE `operatelog` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`title`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`content`  varchar(4000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`type`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`remark`  varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`optime`  datetime NULL DEFAULT CURRENT_TIMESTAMP ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

/*burn in历史记录*/
CREATE TABLE `brinhousehistory` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userid` bigint(20) DEFAULT NULL,
  `starttime` datetime DEFAULT NULL,
  `endtime` datetime DEFAULT NULL,
  `rFlag` tinyint(2) DEFAULT '0',
  `houseIEEE` varchar(50) DEFAULT NULL,
  `deviceAllFlag` tinyint(2) DEFAULT '0',
  `netFlag` tinyint(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/*device是否在线默认为1*/
ALTER TABLE device CHANGE COLUMN isonline isonline varchar(50) DEFAULT 1;

/*burn in brinhouse表新增字段limittime，保存burn in的设置时长*/
ALTER TABLE `brinhouse`
ADD COLUMN `limittime`  int(3) NULL ;
/*burn in brinhousehistory表新增字段limittime，保存burn in的设置时长*/
ALTER TABLE `brinhousehistory`
ADD COLUMN `limittime`  int(3) NULL ;
/*修改device表，新增字段modelId，改字段存放模式类型*/
ALTER TABLE device
ADD COLUMN modelId  varchar(50);
/*向 operatelib插入数据*/
INSERT INTO operatelib(opname,optype,operateName,operateNameCn) VALUES ('iasZoneOperation.cgi', '2', 'GetCIEADDR', '获取CIEADDR');
INSERT INTO operatelib(opname,optype,operateName,operateNameCn) VALUES ('iasZoneOperation.cgi', '3', 'GetZoneType', '获取Zone类型');
INSERT INTO operatelib(opname,optype,operateName,operateNameCn) VALUES ('iasZoneOperation.cgi', '4', 'GetHeartBeatPeriod', '获取Zone心跳周期');
INSERT INTO operatelib(opname,optype,operateName,operateNameCn) VALUES ('iasZoneOperation.cgi', '5', 'GetBatteryLevel', '获取电池等级');
INSERT INTO operatelib(opname,optype,operateName,operateNameCn) VALUES ('iasZoneOperation.cgi', '6', 'GetZoneState', '获取ZoneState');
INSERT INTO operatelib(opname,optype,operateName,operateNameCn) VALUES ('iasZoneOperation.cgi', '7', 'GetZoneStatus', '获取ZoneStatus');
INSERT INTO operatelib(opname,optype,operateName,operateNameCn) VALUES ('iasZoneOperation.cgi', '9', 'SetIRDisableTimeAlarm', 'IRDisableTimeAlarm');
INSERT INTO operatelib(opname,optype,operateName,operateNameCn) VALUES ('iasZoneOperation.cgi', '10', 'GetIRDisableTime', 'IRDisableTime');
INSERT INTO operatelib(opname,optype,operateName,operateNameCn) VALUES ('iasZoneOperation.cgi', '1', 'WriteHeartBeatPeriod', 'WriteHeartBeatPeriod');
INSERT INTO operatelib(opname,optype,operateName,operateNameCn) VALUES ('IASACE.cgi', '0', 'WriteHeartBeatPeriod', 'WriteHeartBeatPeriod');
INSERT INTO operatelib(opname,optype,operateName,operateNameCn) VALUES ('IASACE.cgi', '1', 'RefreshDeviceHeartBeat', 'RefreshDeviceHeartBeat');
INSERT INTO operatelib(opname,optype,operateName,operateNameCn) VALUES ('IASACE.cgi', '2', 'GetDeviceHeartBeat', 'GetDeviceHeartBeat');
INSERT INTO operatelib(opname,optype,operateName,operateNameCn) VALUES ('IASACE.cgi', '3', 'RefreshDeviceCIEAddr', 'RefreshDeviceCIEAddr');


/*203稳定性数据统计配置表*/
CREATE TABLE `deviceattributehistorydictionary` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `deviceName` varchar(200) DEFAULT NULL,
  `modelId` varchar(50) DEFAULT NULL,
  `pushCount` int(4) DEFAULT NULL,
  `cloudCount` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/*deviceattributehistorydictionary添加字段*/
alter table deviceattributehistorydictionary add column cluster_id varchar(50) DEFAULT NULL;
alter table deviceattributehistorydictionary add column attribute_id varchar(50) DEFAULT NULL;

/*message_id*/
ALTER TABLE devicewarnhistory_houseid_year
ADD COLUMN message_id  int(4);


/*向 operatelib插入数据*/
INSERT INTO operatelib(opname,optype,operateName,operateNameCn) VALUES ('onOffSwitchOperation.cgi', '0', 'GetOnOffSwitchType', 'GetOnOffSwitchType');
INSERT INTO operatelib(opname,optype,operateName,operateNameCn) VALUES ('onOffSwitchOperation.cgi', '1', 'GetOnOffSwitchActions', 'GetOnOffSwitchActions');
INSERT INTO operatelib(opname,optype,operateName,operateNameCn) VALUES ('onOffSwitchOperation.cgi', '2', 'ChangeOnOffSwitchActions', 'ChangeOnOffSwitchActions');
INSERT INTO operatelib(opname,optype,operateName,operateNameCn) VALUES ('onOffSwitchOperation.cgi', '3', 'SetIRDisableTimeAlarm', 'SetIRDisableTimeAlarm');
INSERT INTO operatelib(opname,optype,operateName,operateNameCn) VALUES ('onOffSwitchOperation.cgi', '4', 'RefreshIRDisableTimeAlarm', 'RefreshIRDisableTimeAlarm');



/*2014/10/28-pengcq稳定性数据录入--to be continued*/
INSERT INTO `deviceattributehistorydictionary` VALUES ('1', 'Z203', 'Z203AE3', '24', null, '0500', '0002');
INSERT INTO `deviceattributehistorydictionary` VALUES ('2', 'Z308', 'Z308E3ED', '720', null,  '0500', '0002');
INSERT INTO `deviceattributehistorydictionary` VALUES ('3', 'ZB02C', 'ZB02CE3ED', '24', null, '0001', '0020');
INSERT INTO `deviceattributehistorydictionary` VALUES ('4', 'ZB11B', 'ZB11BE3ED', '24', null, '0001', '0020');
INSERT INTO `deviceattributehistorydictionary` VALUES ('5', 'ZB02I', 'ZB02IE3ED', '24', null, '0500', '0002');
INSERT INTO `deviceattributehistorydictionary` VALUES ('7', 'Z809A', 'Z809AE3R', '96', null, '0006', '0000');
INSERT INTO `deviceattributehistorydictionary` VALUES ('8', 'Z311J', 'Z311JE3ED', '24', null, '0500', '0002');
INSERT INTO `deviceattributehistorydictionary` VALUES ('9', 'Z602A', 'Z602AE3R', '24', null, '0500', '0002');
INSERT INTO `deviceattributehistorydictionary` VALUES ('10', 'Z211', 'Z211E3R', '24', null, '0001', '0020');
INSERT INTO `deviceattributehistorydictionary` VALUES ('11', 'Z716A', 'Z716AE3ED', '480', null, '0402', '0000');
INSERT INTO `deviceattributehistorydictionary` VALUES ('12', 'ZC07', 'ZC07E3R', '96', null,  '0006', '0000');
INSERT INTO `deviceattributehistorydictionary` VALUES ('14', 'Z312', 'Z312E3ED', '24', null, '0500', '0002');
INSERT INTO `deviceattributehistorydictionary` VALUES ('15', 'Z302A', 'Z302AE3ED', '24', null,null,null);
INSERT INTO `deviceattributehistorydictionary` VALUES ('16', 'Z311C', 'Z311CE2ED', '24', null,null,null);
INSERT INTO `deviceattributehistorydictionary` VALUES ('17', 'Z307', 'Z307E3ED', '24', null,null,null);
INSERT INTO `deviceattributehistorydictionary` VALUES ('18', 'ZA01D', 'ZA01DE3R', '24', null,null,null);
INSERT INTO `deviceattributehistorydictionary` VALUES ('19', 'ZA01C', 'ZA01CE3R', '24', null,null,null);
INSERT INTO `deviceattributehistorydictionary` VALUES ('20', 'Z602B', 'Z602BE3R', '24', null,null,null);
INSERT INTO `deviceattributehistorydictionary` VALUES ('21', 'ZB01B', 'ZB01BE0ED', '24', null,null,null);
INSERT INTO `deviceattributehistorydictionary` VALUES ('23', 'Z302G', 'Z302GE3ED', '24', null,null,null);
INSERT INTO `deviceattributehistorydictionary` VALUES ('24', 'Z302H', 'Z302HE3ED', '24', null,null,null);
INSERT INTO `deviceattributehistorydictionary` VALUES ('25', 'Z302B', 'Z302BE3ED', '24', null,null,null);
INSERT INTO `deviceattributehistorydictionary` VALUES ('26', 'Z716B', 'Z716BE3ED', '480', null,null,null);
INSERT INTO `deviceattributehistorydictionary` VALUES ('27', 'Z302J', 'Z302JE3ED', '24', null,null,null);
INSERT INTO `deviceattributehistorydictionary` VALUES ('28', 'ZB02F', 'ZB02FE3ED', '24', null,null,null);
INSERT INTO `deviceattributehistorydictionary` VALUES (29, 'ZB11C', 'ZB11CE3ED', 24, null, '0500', '0002');
INSERT INTO `deviceattributehistorydictionary` VALUES (30, 'Z311W', 'Z311WE3ED', 24, null, '0500', '0002');
INSERT INTO `deviceattributehistorydictionary` VALUES (31, 'Z716A', 'Z716AE3ED', 480, null, '0405', '0000');
INSERT INTO `deviceattributehistorydictionary` VALUES (32, 'Z716A', 'Z716AE3ED', 24, null, '0001', '0020');
INSERT INTO `deviceattributehistorydictionary` VALUES (33, 'Z809A', 'Z809AE3R', 96, null, '0702', 'E003');
INSERT INTO `deviceattributehistorydictionary` VALUES (34, 'Z809A', 'Z809AE3R', 96, null, '0702', 'E002');
INSERT INTO `deviceattributehistorydictionary` VALUES (35, 'Z809A', 'Z809AE3R', 96, null, '0702', 'E001');
INSERT INTO `deviceattributehistorydictionary` VALUES (36, 'Z809A', 'Z809AE3R', 96, null, '0702', 'E000');
INSERT INTO `deviceattributehistorydictionary` VALUES (37, 'ZC07', 'ZC07E3R', '96', null,  '0008', '0000');

/*表attributenamelib插入数据*/
INSERT INTO `attributenamelib` VALUES ('32', '温度', 'temp');

/*device state save */
CREATE TABLE `devicemonitorlog` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `houseIEEE` varchar(50) DEFAULT NULL,
  `ieee` varchar(50) DEFAULT NULL,
  `ep` varchar(100) DEFAULT NULL,
  `staterecords` tinyint(2) DEFAULT NULL,
  `statechangetime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*中级用户新增可靠性、ir权限，并修改名称*/
INSERT into relilevelprivilege values(19,2,11);
INSERT into relilevelprivilege values(20,2,12);
update reliprivilege set privilege_name='可靠性管理' where id = 11;
update reliprivilege set privilege_name='用户管理' where id = 6;

/*burn in完整性两个字段*/
ALTER TABLE brinhouse
ADD COLUMN intFlag tinyint(4) DEFAULT NULL,
ADD COLUMN description  varchar(20000) DEFAULT NULL;
ALTER TABLE brinhousehistory
ADD COLUMN intFlag tinyint(4) DEFAULT NULL,
ADD COLUMN description  varchar(20000) DEFAULT NULL;

/*burn in 表brinhouse新增initResult保存初始化结果，0为初始化失败，1为初始化成功*/
ALTER TABLE `brinhouse`
ADD COLUMN `initResult`  tinyint(2) NULL DEFAULT 0 AFTER `description`;
/*burn in 表brinhouse新增initResult保存初始化结果，0为初始化失败，1为初始化成功*/
ALTER TABLE `brinhousehistory`
ADD COLUMN `initResult`  tinyint(2) NULL DEFAULT 0 AFTER `description`;
/*IR匹配表新增houseIEEE字段*/
ALTER TABLE `irmatchrec`
ADD COLUMN `houseIEEE`  varchar(50) NULL AFTER `id`;

/*页面分流器两张表pengcq----urlrecord/historyrecord*/
CREATE TABLE `urlrecord` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(500) DEFAULT NULL,
  `param` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE `historyrecord` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `urlId` int(10) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `ipaddress` varchar(100) DEFAULT NULL,
  `param` varchar(200) DEFAULT NULL,
  `optime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*增加501G EP，修改ep为01的clusterId为null*/
INSERT INTO `modeeplib` (`id`, `nodeId`, `deviceId`, `internelModelId`, `deviceName`, `clusterId`, `clusterName`, `picName`, `destType`, `deviceType`, `deviceTypeV2`, `ep`, `description`, `Groupable`) VALUES ('243', '146', '0004', 'Z501GE3ED-2', 'Z501G安防控制按键二(357)', '0501', '安防控制', '', '3', 'source', '1', '02', '', '0');
INSERT INTO `modeeplib` (`id`, `nodeId`, `deviceId`, `internelModelId`, `deviceName`, `clusterId`, `clusterName`, `picName`, `destType`, `deviceType`, `deviceTypeV2`, `ep`, `description`, `Groupable`) VALUES ('244', '146', '0004', 'Z501GE3ED-3', 'Z501G安防控制按键三(357)', '0501', '安防控制', '', '3', 'source', '1', '03', '', '0');
INSERT INTO `modeeplib` (`id`, `nodeId`, `deviceId`, `internelModelId`, `deviceName`, `clusterId`, `clusterName`, `picName`, `destType`, `deviceType`, `deviceTypeV2`, `ep`, `description`, `Groupable`) VALUES ('245', '146', '0401', 'Z501GE3ED-4', 'Z501G安防控制按键四(357)', '0501', '安防控制', '', '3', 'source', '1', '04', '', '0');
update modeeplib set clusterId=null where id=242;

/*修改relilevel权限表，将中级、初级改为业务、客户*/
UPDATE `relilevel` SET `id`='1', `level_name`='高级' WHERE (`id`='1');
UPDATE `relilevel` SET `id`='2', `level_name`='业务' WHERE (`id`='2');
UPDATE `relilevel` SET `id`='3', `level_name`='客户' WHERE (`id`='3');
INSERT INTO `relilevel` (`id`, `level_name`) value('4','生产');

/*新增reliprivilege表功能*/
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('91', '管理员列表查看', 'admin_lib');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('95', '修改密码', 'update_pwd');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('96', '删除帐户', 'delete_account');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('1001', '家庭统计', 'houseCount');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('1002', '家庭概况各种排序查看', 'houseSortSee');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('1003', '各种关键字搜索', 'houseKeySearch');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('1004', '地图显示SHC分布', 'mapSHC');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('1005', '查看家庭详细信息', 'houseDetailed ');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('1006', 'IPK版本信息查看', 'IPKversion');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('1007', '通过云端连上来的APP查看和个数统计', 'appShowAndCount');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('1008', '手动辅助升级IPK功能', 'upgradeIPK');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('1009', '设备统计', 'deviceCount');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('1010', '设备状态列表各种排序查看', 'deviceSortSee');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('1011', '设备属性报告查看', 'deviceAttribute');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('1012', '设备datecode信息查看', 'deviceDatecode');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('1013', '历史记录列表各种排序', 'historySortSee');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('1014', '查看设备动作记录', 'deviceAction');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('1015', '查看该设备记录', 'deviceRecord');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('1016', '查看该设备的指定动作记录', 'deviceSpecifiedAction');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('1017', '通信状态统计', 'CommunicationStateCount');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('1018', '网络通信质量列表排序查看', 'CommunicationSortSee');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('1019', '各级管理员可查看的SHC范围设置', 'SHCreturn');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('2001', '已注册家庭列表查看功能', 'registeredHouseSee');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('2002', '已注册家庭列表搜索功能', 'registeredHouseSearch');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('2003', '对已注册的编辑功能', 'registeredHouseEdit');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('2004', '对已注册的删除功能', 'registeredHouseDelete');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('2005', '已注册家庭列表手动输入注册功能', 'handRegisteredHouse');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('2006', '已注册家庭列表批量导入注册功能', 'manyRegisteredHouse');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('3001', '软体版本搜索功能', 'softwareVersionSearch');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('3002', 'Z203(Z206) IPK版本列表查看', 'IPKlibSee');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('3003', '软体版本各种排序查看功能', 'softwareSortSee');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('3004', '对已发布软体信息的编辑功能', 'sortwareEdit');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('3005', '对已发布软体信息的删除功能', 'sortwareDelete');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('3006', '新增自动升级软体发布', 'automaticUpdateSoftware');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('3007', '硬件版本管理列表查看', 'hardwareVersion');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('3008', '硬体版本各种排序查看功能', 'hardwareVersionSortSee');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('3009', '对已发布硬件版本号的编辑功能', 'harawareEdit');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('3010', '新增硬件版本号发布功能', 'hardwareVersionRelease');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('4001', '云端服务项目查看', 'serverProjectSee');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('4002', '云端服务列表查看', 'serverLibSee');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('4003', '监控SHC服务单个开启功能', 'oneOpen');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('4004', '监控SHC服务全选开启功能', 'allOpen');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('4005', '监控SHC服务多选开启功能', 'manyOpen');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('4006', '监控SHC服务单个关闭功能', 'oneClose');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('4007', '监控SHC服务全选关闭功能', 'allClose');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('4008', '监控SHC服务多选关闭功能', 'manyClose');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('4009', '状态变化发送的默认邮箱设置', 'defaultEmail');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('4010', '状态变化发送的管理员邮箱设置', 'administratorEmail');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('4011', '外出管理服务单个开启功能', 'outServerOneOpen');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('4012', '外出管理服务全选开启功能', 'outServerAllOpen');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('4013', '外出管理服务多选开启功能', 'outServerManyOpen');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('4014', '外出管理服务单个关闭功能', 'outServerOneClose');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('4015', '外出管理服务全选关闭功能', 'outServerAllClose');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('4016', '外出管理服务多选关闭功能', 'outServerManyClose');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('4017', '报警服务单个开启功能', 'alarmServerOneOpen');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('4018', '报警服务全选开启功能', 'alarmServerAllOpen');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('4019', '报警服务多选开启功能', 'alarmServerManyOpen');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('4020', '报警服务单个关闭功能', 'alarmServerOneClose');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('4021', '报警服务全选关闭功能', 'alarmServerAllClose');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('4022', '报警服务多选关闭功能', 'alarmServerManyClose');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('4023', 'LQI数据监控单个开启功能', 'LQIOneOpen');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('4024', 'LQI数据监控全选开启功能', 'LQIAllOpen');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('4025', 'LQI数据监控多选开启功能', 'LQIManyOpen');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('4026', 'LQI数据监控单个关闭功能', 'LQIOneClose');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('4027', 'LQI数据监控全选关闭功能', 'LQIAllClose');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('4028', 'LQI数据监控多选关闭功能', 'LQIManyClose');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('4029', 'LQI阈值设置', 'LQIValueSet');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('4030', 'LQI数据监控开启时间设置', 'LQITimeSet');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('4031', '设备是否在线单个开启功能', 'deviceOnlineOneOpen');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('4032', '设备是否在线全选开启功能', 'deviceOnlineAllOpen');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('4033', '设备是否在线多选开启功能', 'deviceOnlineManyOpen');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('4034', '设备是否在线单个关闭功能', 'deviceOnlineOneClose');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('4035', '设备是否在线全选关闭功能', 'deviceOnlineAllClose');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('4036', '设备是否在线多选关闭功能', 'deviceOnlineManyClose');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('5001', '云端IR数据列表查看', 'IRLibSee');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('5002', 'IR搜索功操作', 'IRSearch');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('5003', 'IR排序操作', 'IRSort');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('5004', 'IR认证操作', 'IRCertification');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('5005', 'IR删除操作', 'IRDelete');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('5006', 'IR导入IR数据功能', 'IRImport');

/*relilevelprivilege表新增权限与功能的对应关系*/
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('21', '1', '1001');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('22', '2', '1001');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('23', '3', '1001');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('26', '1', '1002');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('27', '2', '1002');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('28', '3', '1002');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('31', '1', '1003');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('32', '2', '1003');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('33', '3', '1003');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('36', '1', '1004');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('37', '2', '1004');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('40', '1', '1005');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('41', '2', '1005');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('42', '3', '1005');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('45', '1', '1006');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('46', '2', '1006');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('47', '3', '1006');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('50', '1', '1007');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('51', '2', '1007');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('52', '3', '1007');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('55', '1', '1008');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('56', '2', '1008');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('59', '1', '1009');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('60', '2', '1009');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('61', '3', '1009');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('64', '1', '1010');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('65', '2', '1010');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('66', '3', '1010');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('69', '1', '1011');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('70', '2', '1011');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('71', '3', '1011');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('74', '1', '1012');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('75', '2', '1012');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('78', '1', '1013');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('79', '2', '1013');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('80', '3', '1013');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('83', '1', '1014');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('84', '2', '1014');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('85', '3', '1014');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('88', '1', '1015');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('89', '2', '1015');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('90', '3', '1015');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('93', '1', '1016');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('94', '2', '1016');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('95', '3', '1016');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('98', '1', '1017');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('99', '2', '1017');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('103', '1', '1018');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('104', '2', '1018');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('105', '3', '1018');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('108', '1', '1019');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('109', '1', '2001');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('110', '2', '2001');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('111', '1', '2002');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('112', '2', '2002');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('113', '1', '2003');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('114', '2', '2003');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('115', '1', '2004');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('116', '2', '2004');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('117', '1', '2005');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('118', '2', '2005');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('119', '1', '2006');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('120', '2', '2006');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('121', '1', '3001');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('122', '2', '3001');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('123', '1', '3002');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('124', '2', '3002');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('125', '1', '3003');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('126', '2', '3003');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('127', '1', '3004');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('128', '2', '3004');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('129', '1', '3005');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('130', '2', '3005');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('131', '1', '3006');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('132', '2', '3006');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('133', '1', '3007');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('134', '2', '3007');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('135', '1', '3008');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('136', '2', '3008');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('137', '1', '3009');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('138', '2', '3009');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('139', '1', '3010');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('140', '2', '3010');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('141', '1', '4001');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('142', '2', '4001');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('143', '1', '4002');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('144', '2', '4002');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('145', '1', '4003');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('146', '2', '4003');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('147', '1', '4004');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('148', '2', '4004');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('149', '1', '4005');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('150', '2', '4005');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('151', '1', '4006');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('152', '2', '4006');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('153', '1', '4007');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('154', '2', '4007');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('155', '1', '4008');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('156', '2', '4008');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('157', '1', '4009');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('158', '2', '4009');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('159', '1', '4010');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('160', '1', '4011');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('161', '2', '4011');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('162', '1', '4012');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('163', '2', '4012');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('164', '1', '4013');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('165', '2', '4013');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('166', '1', '4014');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('167', '2', '4014');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('168', '1', '4015');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('169', '2', '4015');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('170', '1', '4016');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('171', '2', '4016');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('172', '1', '4017');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('173', '2', '4017');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('174', '1', '4018');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('175', '2', '4018');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('176', '1', '4019');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('177', '2', '4019');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('178', '1', '4020');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('179', '2', '4020');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('180', '1', '4021');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('181', '2', '4021');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('182', '1', '4022');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('183', '2', '4022');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('184', '1', '4023');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('185', '2', '4023');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('186', '1', '4024');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('187', '2', '4024');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('188', '1', '4025');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('189', '2', '4025');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('190', '1', '4026');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('191', '2', '4026');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('192', '1', '4027');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('193', '2', '4027');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('194', '1', '4028');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('195', '2', '4028');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('196', '1', '4029');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('197', '2', '4029');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('198', '1', '4030');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('199', '2', '4030');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('200', '1', '4031');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('201', '2', '4031');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('202', '1', '4032');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('203', '2', '4032');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('204', '1', '4033');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('205', '2', '4033');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('206', '1', '4034');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('207', '2', '4034');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('208', '1', '4035');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('209', '2', '4035');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('210', '1', '4036');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('211', '2', '4036');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('212', '1', '5001');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('213', '2', '5001');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('214', '1', '5002');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('215', '2', '5002');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('216', '1', '5003');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('217', '2', '5003');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('218', '1', '5004');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('219', '2', '5004');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('220', '1', '5005');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('221', '2', '5005');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('222', '1', '5006');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('223', '2', '5006');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('225', '1', '91');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('226', '2', '91');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('227', '1', '95');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('228', '2', '95');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('229', '3', '95');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('230', '1', '96');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('231', '2', '96');


/*更新501G第一个EP的deviceId，更新716A名字*/
update modenodelib set deviceName='Z716A无线室内温湿度感应器' where id=35;
update modeeplib set deviceId='0004' where id=241;


update modeeplib set deviceId="0004"  where internelModelId = "Z501GE3ED-1";
update modenodelib set deviceName="Z716A无线室内温湿度感应器"  where modelId = "Z716A";
update modeeplib set deviceName="Z716A无线室内温湿度感应器"  where internelModelId = "Z716A-1";
/*添加时，burnin时间赋初值*/
ALTER TABLE `brinhouse`
MODIFY COLUMN `endtime`  datetime NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE `brinhouse`
MODIFY COLUMN `starttime`  datetime NULL DEFAULT CURRENT_TIMESTAMP;

/* 标准模板导入 */
INSERT INTO `userordermain` (`id`, `orderName`, `userId`, `houseId`, `userType`, `userName`, `houseName`, `tel`, `email`, `address`, `contact`, `count`, `suggestion`, `description`, `xmlFile`, `createtime`, `lasttime`) VALUES ('-7', '奈伯思标准情景模式导入', '-1', '-1', '0', 'netvox', '奈伯思家居版_两房一厅', '', '', '地址', '联系人', '1', '建议', '描述', 'd:\\modexml\\Mode奈伯思家居版_两房一厅.xml;d:\\modexml\\Node奈伯思家居版_两房一厅.xml', '2013-12-10 09:28:06', '2013-12-10 09:28:06');
INSERT INTO `userordermain` (`id`, `orderName`, `userId`, `houseId`, `userType`, `userName`, `houseName`, `tel`, `email`, `address`, `contact`, `count`, `suggestion`, `description`, `xmlFile`, `createtime`, `lasttime`) VALUES ('-6', '奈伯思标准情景模式导入', '-1', '-1', '0', 'netvox', '奈伯思商用版_店铺', '', '', '地址', '联系人', '1', '建议', '描述', 'd:\\modexml\\Mode奈伯思商用版_店铺.xml;d:\\modexml\\Node奈伯思商用版_店铺.xml', '2013-12-10 09:28:06', '2013-12-10 09:28:06');

/* 修改模板表数据 */
ALTER TABLE `deviceattrlib`
MODIFY COLUMN `UniqueName`  varchar(255) NULL DEFAULT NULL AFTER `DataType`;
delete from deviceattrlib;
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('1', 'Z815I', '0009', '0006', '0000', 'On/off status', '2', '开关状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('2', 'Z815K', '0009', '0006', '0000', 'On/off status', '2', '开关状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('5', 'Z806', '0100', '0006', '0000', 'On/off status', '2', '开关状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('10', 'Z805A', '0009', '0006', '0000', 'On/off status', '2', '开关状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('12', 'Z810B', '0009', '0006', '0000', 'On/off status', '2', '开关状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('13', 'Z811', '0100', '0006', '0000', 'On/off status', '2', '开关状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('17', 'Z815J', '0009', '0006', '0000', 'On/off status', '2', '开关状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('19', 'Z815L', '0101', '0006', '0000', 'On/off status', '2', '开关状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('20', 'Z815M', '0101', '0006', '0000', 'On/off status', '2', '开关状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('21', 'Z815M', '0101', '0008', '0000', 'Level', '3', '调级级别');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('22', 'Z817A', '0009', '0006', '0000', 'On/off status', '2', '开关状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('23', 'Z817B', '0101', '0006', '0000', 'On/off status', '2', '开关状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('24', 'Z817C', '0002', '0006', '0000', 'On/off status', '2', '开关状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('25', 'Z825C', '0009', '0006', '0000', 'On/off status', '2', '开关状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('28', 'Z825E', '0101', '0006', '0000', 'On/off status', '2', '开关状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('30', 'ZC06A', '0101', '0006', '0000', 'On/off status', '2', '开关状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('31', 'Z800', '0009', '0006', '0000', 'On/off status', '2', '开关状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('32', 'Z803-1', '0002', '0006', '0000', 'On/off status', '2', '开关状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('33', 'Z803-2', '0002', '0006', '0000', 'On/off status', '2', '开关状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('34', 'Z803-3', '0002', '0006', '0000', 'On/off status', '2', '开关状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('35', 'Z803-4', '0002', '0006', '0000', 'On/off status', '2', '开关状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('37', 'Z808A', '0009', '0006', '0000', 'On/off status', '2', '开关状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('38', 'Z808B', '0101', '0006', '0000', 'On/off status', '2', '开关状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('39', 'Z809A', '0009', '0006', '0000', 'On/off status', '2', '开关状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('40', 'Z809B', '0101', '0006', '0000', 'On/off status', '2', '开关状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('41', 'Z816B', '0009', '0006', '0000', 'On/off status', '2', '开关状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('42', 'Z816G', '0009', '0006', '0000', 'On/off status', '2', '开关状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('43', 'Z816H', '0009', '0006', '0000', 'On/off status', '2', '开关状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('44', 'Z711', '0302', '0402', '0000', 'Temperature', '3', '温度');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('45', 'Z712', '0302', '0402', '0000', 'Temperature', '3', '温度');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('46', 'Z713', '0302', '0402', '0000', 'Temperature', '3', '温度');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('47', 'Z716A', '0302', '0402', '0000', 'Temperature', '3', '温度');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('48', 'Z716B', '0302', '0402', '0000', 'Temperature', '3', '温度');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('51', 'Z302G', '0106', '0400', '0000', 'Illuminance', '3', '照度值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('64', 'Z815N', '0200', '0006', '0000', 'On/off status', '2', '开关状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('65', 'ZD01B', '0200', '0008', '0000', 'Level', '3', '调级级别');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('66', 'ZD01B', '0200', '0006', '0000', 'On/off status', '2', '开关状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('68', 'ZB01A', '0402', '0500', '0002', 'Zone status', '1', '安防设备状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('69', 'ZB01D', '0107', '0406', '0000', 'Occupancy', '2', '红外探测情况');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('70', 'ZB11A', '0402', '0500', '0002', 'Zone status', '1', '安防设备状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('72', 'ZB01C', '0302', '0402', '0000', 'Temperature', '3', '温度');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('74', 'ZB11C', '0302', '0402', '0000', 'Temperature', '3', '温度');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('75', 'ZB11D', '0107', '0406', '0000', 'Occupancy', '2', '红外探测情况');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('76', 'Z302A', '0402', '0500', '0002', 'Zone status', '1', '安防设备状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('77', 'Z302C', '0402', '0500', '0002', 'Zone status', '1', '安防设备状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('78', 'Z302D', '0402', '0500', '0002', 'Zone status', '1', '安防设备状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('79', 'Z302E', '0402', '0500', '0002', 'Zone status', '1', '安防设备状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('82', 'Z306D', '0402', '0500', '0002', 'Zone status', '1', '安防设备状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('83', 'Z311A', '0402', '0500', '0002', 'Zone status', '1', '安防设备状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('84', 'Z311C', '0402', '0500', '0002', 'Zone status', '1', '安防设备状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('86', 'Z307', '0402', '0500', '0002', 'Zone status', '1', '安防设备状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('87', 'Z308', '0402', '0500', '0002', 'Zone status', '1', '安防设备状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('88', 'ZA01A', '0402', '0500', '0002', 'Zone status', '1', '安防设备状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('89', 'ZA01B', '0402', '0500', '0002', 'Zone status', '1', '安防设备状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('90', 'ZA01C', '0402', '0500', '0002', 'Zone status', '1', '安防设备状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('91', 'ZA01D', '0402', '0500', '0002', 'Zone status', '1', '安防设备状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('92', 'ZA02E', '0402', '0500', '0002', 'Zone status', '1', '安防设备状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('93', 'ZA10', '0002', '0006', '0000', 'On/off status', '2', '开关状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('94', 'ZB05', '000A', '0101', '0000', 'Lock status', '2', '锁状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('96', 'Z311E', '0402', '0500', '0002', 'Zone status', '1', '安防设备状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('123', 'Z812A', '0002', '0006', '0000', 'On/off status', '2', '开关状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('133', 'Z801TXB', '0402', '0500', '0002', 'Zone status', '1', '安防设备状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('138', 'Z801RX', '0002', '0006', '0000', 'On/off status', '2', '开关状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('139', 'Z311G', '0106', '0400', '0000', 'Illuminance', '3', '照度值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('140', 'Z817C', '0002', '0702', 'E000', 'Current', '3', '电流值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('141', 'Z817C', '0002', '0702', 'E001', 'Voltage', '3', '电压值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('142', 'Z817C', '0002', '0702', 'E002', 'Power', '3', '功率值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('143', 'Z817C', '0002', '0702', 'E003', 'Energy', '3', '电能值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('144', 'Z803', '0002', '0702', 'E000', 'Current', '3', '电流值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('145', 'Z803', '0002', '0702', 'E001', 'Voltage', '3', '电压值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('146', 'Z803', '0002', '0702', 'E002', 'Power', '3', '功率值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('147', 'Z803', '0002', '0702', 'E003', 'Energy', '3', '电能值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('148', 'Z815I', '0009', '0702', 'E000', 'Current', '3', '电流值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('149', 'Z815I', '0009', '0702', 'E001', 'Voltage', '3', '电压值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('150', 'Z815I', '0009', '0702', 'E002', 'Power', '3', '功率值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('151', 'Z815I', '0009', '0702', 'E003', 'Energy', '3', '电能值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('153', 'Z815J', '0009', '0702', 'E000', 'Current', '3', '电流值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('154', 'Z815J', '0009', '0702', 'E001', 'Voltage', '3', '电压值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('155', 'Z815J', '0009', '0702', 'E002', 'Power', '3', '功率值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('156', 'Z815J', '0009', '0702', 'E003', 'Energy', '3', '电能值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('157', 'Z815K', '0009', '0702', 'E000', 'Current', '3', '电流值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('158', 'Z815K', '0009', '0702', 'E001', 'Voltage', '3', '电压值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('159', 'Z815K', '0009', '0702', 'E002', 'Power', '3', '功率值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('160', 'Z815K', '0009', '0702', 'E003', 'Energy', '3', '电能值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('161', 'Z815L', '0101', '0702', 'E000', 'Current', '3', '电流值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('162', 'Z815L', '0101', '0702', 'E001', 'Voltage', '3', '电压值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('163', 'Z815L', '0101', '0702', 'E002', 'Power', '3', '功率值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('164', 'Z815L', '0101', '0702', 'E003', 'Energy', '3', '电能值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('165', 'Z815M', '0101', '0702', 'E000', 'Current', '3', '电流值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('166', 'Z815M', '0101', '0702', 'E001', 'Voltage', '3', '电压值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('167', 'Z815M', '0101', '0702', 'E002', 'Power', '3', '功率值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('168', 'Z815M', '0101', '0702', 'E003', 'Energy', '3', '电能值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('169', 'Z805', '0009', '0702', 'E000', 'Current', '3', '电流值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('170', 'Z805', '0009', '0702', 'E001', 'Voltage', '3', '电压值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('171', 'Z805', '0009', '0702', 'E002', 'Power', '3', '功率值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('172', 'Z805', '0009', '0702', 'E003', 'Energy', '3', '电能值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('173', 'Z810', '0009', '0702', 'E000', 'Current', '3', '电流值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('174', 'Z810', '0009', '0702', 'E001', 'Voltage', '3', '电压值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('175', 'Z810', '0009', '0702', 'E002', 'Power', '3', '功率值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('176', 'Z810', '0009', '0702', 'E003', 'Energy', '3', '电能值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('177', 'Z817A', '0009', '0702', 'E000', 'Current', '3', '电流值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('178', 'Z817A', '0009', '0702', 'E001', 'Voltage', '3', '电压值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('179', 'Z817A', '0009', '0702', 'E002', 'Power', '3', '功率值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('180', 'Z817A', '0009', '0702', 'E003', 'Energy', '3', '电能值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('181', 'ZB05', '000A', '0101', '0003', 'Door status', '2', '门状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('182', 'Z803', '000D', '0702', 'E000', 'Current', '3', '电流值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('183', 'Z803', '000D', '0702', 'E001', 'Voltage', '3', '电压值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('184', 'Z803', '000D', '0702', 'E002', 'Power', '3', '功率值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('185', 'Z803', '000D', '0702', 'E003', 'Energy', '3', '电能值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('186', 'Z821', '000D', '0702', 'E000', 'Current', '3', '电流值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('187', 'Z821', '000D', '0702', 'E001', 'Voltage', '3', '电压值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('188', 'Z821', '000D', '0702', 'E002', 'Power', '3', '功率值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('189', 'Z821', '000D', '0702', 'E003', 'Energy', '3', '电能值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('190', 'Z817B', '0101', '0008', '0000', 'Level', '3', '调级级别');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('191', 'Z825E', '0101', '0008', '0000', 'Level', '3', '调级级别');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('192', 'Z825E', '0101', '0702', 'E000', 'Current', '3', '电流值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('193', 'Z825E', '0101', '0702', 'E001', 'Voltage', '3', '电压值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('194', 'Z825E', '0101', '0702', 'E002', 'Power', '3', '功率值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('195', 'Z825E', '0101', '0702', 'E003', 'Energy', '3', '电能值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('196', 'Z825C', '0009', '0702', 'E000', 'Current', '3', '电流值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('197', 'Z825C', '0009', '0702', 'E001', 'Voltage', '3', '电压值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('198', 'Z825C', '0009', '0702', 'E002', 'Power', '3', '功率值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('199', 'Z825C', '0009', '0702', 'E003', 'Energy', '3', '电能值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('200', 'ZC06A', '0101', '0008', '0000', 'Level', '3', '调级级别');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('201', 'Z808B', '0101', '0008', '0000', 'Level', '3', '调级级别');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('202', 'Z809B', '0101', '0008', '0000', 'Level', '3', '调级级别');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('203', 'Z800', '0009', '0702', 'E000', 'Current', '3', '电流值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('204', 'Z800', '0009', '0702', 'E001', 'Voltage', '3', '电压值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('205', 'Z800', '0009', '0702', 'E002', 'Power', '3', '功率值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('206', 'Z800', '0009', '0702', 'E003', 'Energy', '3', '电能值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('207', 'Z808A', '0009', '0702', 'E000', 'Current', '3', '电流值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('208', 'Z808A', '0009', '0702', 'E001', 'Voltage', '3', '电压值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('209', 'Z808A', '0009', '0702', 'E002', 'Power', '3', '功率值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('210', 'Z808A', '0009', '0702', 'E003', 'Energy', '3', '电能值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('211', 'Z809A', '0009', '0702', 'E000', 'Current', '3', '电流值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('212', 'Z809A', '0009', '0702', 'E001', 'Voltage', '3', '电压值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('213', 'Z809A', '0009', '0702', 'E002', 'Power', '3', '功率值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('214', 'Z809A', '0009', '0702', 'E003', 'Energy', '3', '电能值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('215', 'Z816B', '0009', '0702', 'E000', 'Current', '3', '电流值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('216', 'Z816B', '0009', '0702', 'E001', 'Voltage', '3', '电压值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('217', 'Z816B', '0009', '0702', 'E002', 'Power', '3', '功率值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('218', 'Z816B', '0009', '0702', 'E003', 'Energy', '3', '电能值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('219', 'Z816G', '0009', '0702', 'E000', 'Current', '3', '电流值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('220', 'Z816G', '0009', '0702', 'E001', 'Voltage', '3', '电压值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('221', 'Z816G', '0009', '0702', 'E002', 'Power', '3', '功率值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('222', 'Z816G', '0009', '0702', 'E003', 'Energy', '3', '电能值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('223', 'Z816H', '0009', '0702', 'E000', 'Current', '3', '电流值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('224', 'Z816H', '0009', '0702', 'E001', 'Voltage', '3', '电压值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('225', 'Z816H', '0009', '0702', 'E002', 'Power', '3', '功率值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('226', 'Z816H', '0009', '0702', 'E003', 'Energy', '3', '电能值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('227', 'Z815N', '0200', '0008', '0000', 'Level', '3', '调级级别');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('228', 'Z711', '0302', '0405', '0001', 'Humidity', '3', '湿度');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('229', 'Z712', '0302', '0405', '0001', 'Humidity', '3', '湿度');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('230', 'Z713', '0302', '0405', '0001', 'Humidity', '3', '湿度');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('231', 'Z716A', '0302', '0405', '0001', 'Humidity', '3', '湿度');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('233', 'ZB01C', '0402', '0500', '0002', 'Zone status', '1', '安防设备状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('234', 'ZB11C', '0402', '0500', '0002', 'Zone status', '1', '安防设备状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('235', 'Z962A', '0009', '0702', 'E000', 'Current', '3', '电流值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('236', 'Z962A', '0009', '0702', 'E001', 'Voltage', '3', '电压值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('237', 'Z962A', '0009', '0702', 'E002', 'Power', '3', '功率值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('238', 'Z962A', '0009', '0702', 'E003', 'Energy', '3', '电能值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('239', 'Z962B', '0009', '0702', 'E000', 'Current', '3', '电流值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('240', 'Z962B', '0009', '0702', 'E001', 'Voltage', '3', '电压值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('241', 'Z962B', '0009', '0702', 'E002', 'Power', '3', '功率值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('242', 'Z962B', '0009', '0702', 'E003', 'Energy', '3', '电能值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('243', 'Z962C', '0009', '0702', 'E000', 'Current', '3', '电流值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('244', 'Z962C', '0009', '0702', 'E001', 'Voltage', '3', '电压值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('245', 'Z962C', '0009', '0702', 'E002', 'Power', '3', '功率值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('246', 'Z962C', '0009', '0702', 'E003', 'Energy', '3', '电能值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('259', 'Z962G', '0009', '0702', 'E000', 'Current', '3', '电流值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('260', 'Z962G', '0009', '0702', 'E001', 'Voltage', '3', '电压值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('261', 'Z962G', '0009', '0702', 'E002', 'Power', '3', '功率值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('262', 'Z962G', '0009', '0702', 'E003', 'Energy', '3', '电能值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('263', 'Z962H', '0009', '0702', 'E000', 'Current', '3', '电流值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('264', 'Z962H', '0009', '0702', 'E001', 'Voltage', '3', '电压值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('265', 'Z962H', '0009', '0702', 'E002', 'Power', '3', '功率值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('266', 'Z962H', '0009', '0702', 'E003', 'Energy', '3', '电能值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('267', 'Z962I', '0009', '0702', 'E000', 'Current', '3', '电流值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('268', 'Z962I', '0009', '0702', 'E001', 'Voltage', '3', '电压值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('269', 'Z962I', '0009', '0702', 'E002', 'Power', '3', '功率值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('270', 'Z962I', '0009', '0702', 'E003', 'Energy', '3', '电能值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('271', 'Z805B', '0009', '0006', '0000', 'On/off status', '2', '开关状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('272', 'Z815L', '0101', '0008', '0000', 'Level', '3', '调级级别');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('273', 'Z801WLS', '0402', '0500', '0002', 'Zone status', '1', '安防设备状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('274', 'Z962A', '0009', '0006', '0000', 'On/off status', '2', '开关状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('275', 'Z962B', '0009', '0006', '0000', 'On/off status', '2', '开关状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('276', 'Z962C', '0009', '0006', '0000', 'On/off status', '2', '开关状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('277', 'Z962G', '0009', '0006', '0000', 'On/off status', '2', '开关状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('278', 'Z962H', '0009', '0006', '0000', 'On/off status', '2', '开关状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('279', 'Z962I', '0009', '0006', '0000', 'On/off status', '2', '开关状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('280', 'Z311W', '0402', '0500', '0002', 'Zone status', '1', '安防设备状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('281', 'Z311J', '0402', '0500', '0002', 'Zone status', '1', '安防设备状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('282', 'ZC07', '0101', '0008', '0000', 'Level', '3', '调级级别');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('283', 'ZC06', '0101', '0008', '0000', 'Level', '3', '调级级别');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('284', 'Z815A', '0009', '0006', '0000', 'On/off status', '2', '开关状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('285', 'Z815I', '0009', '0702', 'E000', 'Current', '3', '电流值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('286', 'Z815I', '0009', '0702', 'E001', 'Voltage', '3', '电压值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('287', 'Z815I', '0009', '0702', 'E002', 'Power', '3', '功率值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('288', 'Z815I', '0009', '0702', 'E003', 'Energy', '3', '电能值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('289', 'Z815B', '0009', '0006', '0000', 'On/off status', '2', '开关状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('290', 'Z815J', '0009', '0702', 'E000', 'Current', '3', '电流值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('291', 'Z815J', '0009', '0702', 'E001', 'Voltage', '3', '电压值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('292', 'Z815J', '0009', '0702', 'E002', 'Power', '3', '功率值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('293', 'Z815J', '0009', '0702', 'E003', 'Energy', '3', '电能值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('294', 'Z815C', '0009', '0006', '0000', 'On/off status', '2', '开关状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('295', 'Z815K', '0009', '0702', 'E000', 'Current', '3', '电流值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('296', 'Z815K', '0009', '0702', 'E001', 'Voltage', '3', '电压值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('297', 'Z815K', '0009', '0702', 'E002', 'Power', '3', '功率值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('298', 'Z815K', '0009', '0702', 'E003', 'Energy', '3', '电能值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('299', 'Z815D', '0101', '0008', '0000', 'Level', '3', '调级级别');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('300', 'Z815D', '0101', '0006', '0000', 'On/off status', '2', '开关状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('301', 'Z815L', '0101', '0702', 'E000', 'Current', '3', '电流值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('302', 'Z815L', '0101', '0702', 'E001', 'Voltage', '3', '电压值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('303', 'Z815L', '0101', '0702', 'E002', 'Power', '3', '功率值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('304', 'Z815L', '0101', '0702', 'E003', 'Energy', '3', '电能值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('305', 'Z815E', '0101', '0006', '0000', 'On/off status', '2', '开关状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('306', 'Z815E', '0101', '0008', '0000', 'Level', '3', '调级级别');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('307', 'Z815M', '0101', '0702', 'E000', 'Current', '3', '电流值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('308', 'Z815M', '0101', '0702', 'E001', 'Voltage', '3', '电压值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('309', 'Z815M', '0101', '0702', 'E002', 'Power', '3', '功率值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('310', 'Z815M', '0101', '0702', 'E003', 'Energy', '3', '电能值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('311', 'Z815N', '0200', '0006', '0000', 'On/off status', '2', '开关状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('312', 'Z815N', '0200', '0008', '0000', 'Level', '3', '调级级别');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('315', 'Z825D', '0101', '0006', '0000', 'On/off status', '2', '开关状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('316', 'Z825C', '0009', '0702', 'E000', 'Current', '3', '电流值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('317', 'Z825C', '0009', '0702', 'E001', 'Voltage', '3', '电压值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('318', 'Z825C', '0009', '0702', 'E002', 'Power', '3', '功率值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('319', 'Z825C', '0009', '0702', 'E003', 'Energy', '3', '电能值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('322', 'Z825A', '0009', '0006', '0000', 'On/off status', '2', '开关状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('323', 'Z825B', '0009', '0006', '0000', 'On/off status', '2', '开关状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('324', 'Z825D', '0101', '0702', 'E000', 'Current', '3', '电流值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('325', 'Z825D', '0101', '0702', 'E001', 'Voltage', '3', '电压值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('326', 'Z825D', '0101', '0702', 'E002', 'Power', '3', '功率值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('327', 'Z825D', '0101', '0702', 'E003', 'Energy', '3', '电能值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('328', 'Z825A', '0009', '0702', 'E000', 'Current', '3', '电流值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('329', 'Z825A', '0009', '0702', 'E001', 'Voltage', '3', '电压值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('330', 'Z825A', '0009', '0702', 'E002', 'Power', '3', '功率值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('331', 'Z825A', '0009', '0702', 'E003', 'Energy', '3', '电能值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('337', 'Z825D', '0101', '0008', '0000', 'Level', '3', '调级级别');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('338', 'Z825J', '0004', '0006', '0000', 'On/off status', '2', '开关状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('339', 'Z825J', '0004', '0702', 'E000', 'Current', '3', '电流值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('340', 'Z825J', '0004', '0702', 'E001', 'Voltage', '3', '电压值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('341', 'Z825J', '0004', '0702', 'E002', 'Power', '3', '功率值');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('342', 'Z825J', '0004', '0702', 'E003', 'Energy', '3', '电能值');

/*增加设备*/
INSERT INTO `zigbeedevice`.`modenodelib` (`id`, `nodeType`, `modelId`, `deviceName`, `clusterId`, `clusterName`, `destType`, `source_id`, `deviceType`, `picName`, `description`, `powerType`, `activationMethod`, `resetDefault`, `remark`) VALUES ('147', '7', 'ZA06', 'ZA06空气蛋', NULL, NULL, '3', '1', 'dest', 'ZA06.jpg', 'ZA06具有检测温湿度、PM2.5、甲醛、CO以及一些有害的有机气体。并能定时采集温湿度、空气质量以及甲醛数据，发送给登记的CIE设备，提示报警。ZA06通过USB供电，短按触摸键可以控制三色灯是否显示当前PM2.5值，设备通过检测到的PM2.5的值，在三色灯上面显示，绿色、黄色、橙色、红色、紫色、褐红色分别代表PM2.5等级，绿色空气质量好、其次按顺序递减，褐红色蓝色最差。', '5V直流电源', '不需要激活', '长按绑定键15s（网络灯闪烁3次以后，设备3s闪烁一次，10s闪烁一次，15s闪烁一次），设备开始恢复出场设置，直到网络灯一直闪烁20次（20，100，100），设备自动复位后，重新请求加网。', '无'); 
INSERT INTO `zigbeedevice`.`modeeplib` (`id`, `nodeId`, `deviceId`, `internelModelId`, `deviceName`, `clusterId`, `clusterName`, `picName`, `destType`, `deviceType`, `deviceTypeV2`, `ep`, `description`, `Groupable`) VALUES ('246', '147', '0302', 'ZA06-1', 'ZA06空气蛋', NULL, NULL, NULL, NULL, NULL, '1', '01', NULL, '0'); 
INSERT INTO `zigbeedevice`.`modeeplib` (`id`, `nodeId`, `deviceId`, `internelModelId`, `deviceName`, `clusterId`, `clusterName`, `picName`, `destType`, `deviceType`, `deviceTypeV2`, `ep`, `description`, `Groupable`) VALUES ('247', '147', '0402', 'ZA06-2', 'ZA06空气蛋', NULL, NULL, NULL, NULL, NULL, '1', '02', NULL, '0'); 
INSERT INTO `zigbeedevice`.`modeeplib` (`id`, `nodeId`, `deviceId`, `internelModelId`, `deviceName`, `clusterId`, `clusterName`, `picName`, `destType`, `deviceType`, `deviceTypeV2`, `ep`, `description`, `Groupable`) VALUES ('248', '147', '0402', 'ZA06-3', 'ZA06空气蛋', NULL, NULL, NULL, NULL, NULL, '1', '03', NULL, '0');

/*burnhouse表新增wrongResult字段，保存异常设备信息*/
ALTER TABLE `brinhouse`
ADD COLUMN `wrongResult`  varchar(1500) NULL ;
/*burnhousehistory表新增wrongResult字段，保存异常设备信息*/
ALTER TABLE `brinhousehistory`
ADD COLUMN `wrongResult`  varchar(1500) NULL ;

/* 修改存储过程 2014.12.10*/
DROP PROCEDURE IF EXISTS `ModeimportTest`;
DELIMITER ;;
CREATE DEFINER = `root`@`%` PROCEDURE `ModeimportTest`(IN `behavior` bigint,IN `houseId` bigint)
BEGIN
	#Routine body goes here...
CASE 
WHEN behavior=1 THEN
		UPDATE modemacrosub a INNER JOIN modegroupmain g ON a.Dest=g.oldId and a.HouseID=g.houseId
		SET a.Dest=g.id WHERE a.houseId=houseId and a.DestType=1;		
		UPDATE modemacrosub a INNER JOIN modescenemain g ON a.Dest=g.oldId and a.HouseID=g.houseId
		SET a.Dest=g.id WHERE a.houseId=houseId and a.DestType=3;	

		UPDATE modegroupsub g INNER JOIN modedevice d ON g.DeviceID=d.oldId and g.HouseID=d.houseId
		SET g.DeviceID=d.id WHERE g.houseId=houseId;
		UPDATE modescenesub g INNER JOIN modedevice d ON g.DeviceID=d.oldId and g.HouseID=d.houseId
		SET g.DeviceID=d.id WHERE g.houseId=houseId;
		UPDATE modemacrosub a INNER JOIN modedevice d ON a.Dest=d.oldId and a.HouseID=d.houseId
		SET a.Dest=d.id WHERE a.houseId=houseId and a.DestType=0;	
		UPDATE modeiasmain m INNER JOIN modedevice d ON m.ModeEpID=d.oldId and m.HouseID=d.houseId
		SET m.ModeEpID=d.id WHERE m.houseId=houseId;
		UPDATE modehvacmain m INNER JOIN modedevice d ON m.ModeEpID=d.oldId and m.HouseID=d.houseId
		SET m.ModeEpID=d.id WHERE m.houseId=houseId;

WHEN behavior=2 THEN
		SELECT * FROM modemacrosub a where a.HouseID=171 and (a.DestType=1 or a.DestType=3);
		SELECT * FROM modegroupmain g where g.HouseID=171 ORDER BY g.oldId;
		SELECT * FROM modescenemain g where g.HouseID=171 ORDER BY g.oldId;

		SELECT * FROM modegroupsub g where g.HouseID=171 ORDER BY g.Id;	
		SELECT * FROM modescenesub g where g.HouseID=171 ORDER BY g.Id;
	  SELECT * FROM modemacrosub b where b.HouseID=171 and b.DestType=0 ORDER BY b.Id;		
		SELECT * FROM modeiasmain d WHERE d.houseId=171 ORDER BY d.Id;	
		SELECT * FROM modehvacmain e WHERE e.houseId=171 ORDER BY e.Id;
		SELECT * FROM modedevice t where t.HouseID=171 ORDER BY t.oldId;		

WHEN behavior=3 THEN
		UPDATE modeiassub u INNER JOIN modemacromain a ON u.Dest=a.oldId and u.HouseID=a.HouseID
		SET u.Dest=a.ID WHERE u.houseId=houseId;
		UPDATE modehvacsub u INNER JOIN modemacromain a ON u.Dest=a.oldId and u.HouseID=a.HouseID
		SET u.Dest=a.ID WHERE u.houseId=houseId;
		UPDATE modehvacsub u INNER JOIN modemacromain a ON u.ResumeDest=a.oldId and u.HouseID=a.HouseID
		SET u.ResumeDest=a.ID WHERE u.houseId=houseId;

WHEN behavior=4 THEN
		SELECT * FROM modeiassub d WHERE d.houseId=171 ORDER BY d.Id;	
		SELECT * FROM modehvacsub e WHERE e.houseId=171 ORDER BY e.Id;
		SELECT * FROM modemacromain b WHERE b.HouseID=171 ORDER BY b.oldId;

WHEN behavior=5 THEN	
		update modeschemesub a inner join modemacromain b on a.Dest=b.oldId and a.HouseID=b.HouseID
		SET a.Dest=b.id WHERE a.houseId=houseId and a.Act='ApplyMacro';
		update modeschemesub a inner join modeiasmain d on a.Dest=d.oldId and a.HouseID=d.HouseID
		SET a.Dest=d.id WHERE a.houseId=houseId and (a.Act='ActiveIAS' or a.Act='DeactiveIAS' or a.Act='IgnoreIAS');
		update modeschemesub a inner join modehvacmain e on a.Dest=e.oldId and a.HouseID=e.HouseID
		SET a.Dest=e.id WHERE a.houseId=houseId and (a.Act='ActiveHVAC' or a.Act='DeactiveHVAC' or a.Act='IgnoreHVAC');

WHEN behavior=6 THEN	
		update usermodesub a inner join modemacromain b on a.Dest=b.oldId and a.HouseID=b.HouseID
		SET a.Dest=b.id WHERE a.houseId=houseId and a.Act='ApplyMacro';
		update usermodesub a inner join modeschememain c on a.Dest=c.oldId and a.HouseID=c.HouseID
		SET a.Dest=c.id WHERE a.houseId=houseId and a.Act='ActiveSheme';		
		update usermodesub a inner join modeiasmain d on a.Dest=d.oldId and a.HouseID=d.HouseID
		SET a.Dest=d.id WHERE a.houseId=houseId and (a.Act='ActiveIAS' or a.Act='DeactiveIAS' or a.Act='IgnoreIAS');
		update usermodesub a inner join modehvacmain e on a.Dest=e.oldId and a.HouseID=e.HouseID
		SET a.Dest=e.id WHERE a.houseId=houseId and (a.Act='ActiveHVAC' or a.Act='DeactiveHVAC' or a.Act='IgnoreHVAC');

WHEN behavior=7 THEN
		SELECT * FROM usermodesub a WHERE a.HouseID=171 ORDER BY a.act;
#		SELECT * FROM modeschemesub c WHERE c.houseId=171 ORDER BY c.act;
		SELECT * FROM modemacromain b WHERE b.HouseID=171 ORDER BY b.oldId;
		SELECT * FROM modeschememain c WHERE c.houseId=171 ORDER BY c.oldId;	
		SELECT * FROM modeiasmain d WHERE d.houseId=171 ORDER BY d.oldId;	
		SELECT * FROM modehvacmain e WHERE e.houseId=171 ORDER BY e.oldId;

END CASE;
END;
;;
DELIMITER ;

/* 修改表 2014.12.10*/
ALTER TABLE `usermodedevice`
ADD COLUMN `oldDestId`  bigint(20) NULL DEFAULT 0 AFTER `Dest`;

/**/
INSERT INTO zigbeedevice.attributenamelib (id, attributeNameCn, attributeName) VALUES ('33', '心跳', 'Zone_Status');
INSERT INTO zigbeedevice.attributenamelib (id, attributeNameCn, attributeName) VALUES ('34', '电池电压', 'BatteryVoltage');

/*新建serverlib 服务器列表 2014.12.10*/
CREATE TABLE `serverlib` (
`id`  int(20) NOT NULL AUTO_INCREMENT ,
`servername`  varchar(100) NULL ,
`serverip`  varchar(50) NULL ,
`maxcshc`  int(100) NULL ,
`serverline`  varchar(100) NULL ,
`warnnum`  int(100) NULL ,
`warnmail`  varchar(200) NULL ,
`serverdescription`  varchar(200) NULL ,
PRIMARY KEY (`id`)
)
;

/*创建英文版ep名称表*/
CREATE TABLE `modeeplib_en` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nodeId` bigint(20) DEFAULT NULL,
  `deviceId` varchar(20) DEFAULT NULL,
  `internelModelId` varchar(50) DEFAULT NULL,
  `deviceName` varchar(200) DEFAULT NULL,
  `clusterId` varchar(200) DEFAULT NULL,
  `clusterName` varchar(100) DEFAULT NULL,
  `picName` varchar(100) DEFAULT NULL,
  `destType` varchar(4) DEFAULT NULL,
  `deviceType` varchar(20) DEFAULT NULL,
  `deviceTypeV2` bigint(20) DEFAULT '1',
  `ep` varchar(50) DEFAULT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `Groupable` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `nodeId` (`nodeId`) USING BTREE,
  KEY `deviceId` (`deviceId`) USING BTREE,
  KEY `internelModelId` (`internelModelId`) USING BTREE,
  KEY `deviceName` (`deviceName`) USING BTREE,
  KEY `destType` (`destType`) USING BTREE,
  KEY `deviceType` (`deviceType`) USING BTREE,
  KEY `clusterId` (`clusterId`) USING BTREE,
  KEY `Groupable` (`Groupable`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=242 DEFAULT CHARSET=utf8;

/*添加英文版ep名称*/
INSERT INTO `modeeplib_en` VALUES ('2', '2', '0009', 'Z815I-1', 'Z815I Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('3', '3', '0009', 'Z815K-1', 'Z815K 1st Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('4', '3', '0009', 'Z815K-2', 'Z815K 2nd Power Switch', '0006', '开关', null, '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('5', '3', '0009', 'Z815K-3', 'Z815K 3rd Power Switch', '0006', '开关', null, '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib_en` VALUES ('6', '4', '0100', 'Z806-1', 'Z806 1st Output', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('7', '4', '0100', 'Z806-2', 'Z806 2nd Output', '0006', '开关', null, '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('8', '5', '0000', 'ZB02A-1', 'ZB02A Wall Switch', '0006', '开关', null, '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('10', '7', '0000', 'Z503-2', 'Z503 Switches', '0006:0008', '开关:调级控制', null, '1', 'source', '1', '0F', '', '0');
INSERT INTO `modeeplib_en` VALUES ('11', '7', '0006', 'Z503-1', 'Z503 security Keypads', '', '', null, '3', '', '1', '0A', '', '0');
INSERT INTO `modeeplib_en` VALUES ('12', '10', '0009', 'Z805A-1', 'Z805A Power Switch', '0006', '开关', null, '3', 'dest', '1', '0A', null, '1');
INSERT INTO `modeeplib_en` VALUES ('13', '11', '0009', 'Z805B-1', 'Z805B Power Switch', '0006', '开关', '', '3', 'dest', '1', '0A', '', '1');
INSERT INTO `modeeplib_en` VALUES ('14', '12', '0009', 'Z810B-1', 'Z810B Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('15', '13', '0100', 'Z811-1', 'Z811 1st Output', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('16', '13', '0100', 'Z811-2', 'Z811 2nd Output', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('17', '13', '0100', 'Z811-3', 'Z811 3rd Output', '0006', '开关', '', '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib_en` VALUES ('18', '13', '0100', 'Z811-4', 'Z811 4th Output', '0006', '开关', '', '3', 'dest', '1', '04', '', '1');
INSERT INTO `modeeplib_en` VALUES ('19', '14', '0009', 'Z815J-1', 'Z815J 1st Power Switch', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('20', '14', '0009', 'Z815J-2', 'Z815J 2nd Power Switch', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('21', '15', '0101', 'Z815L-1', 'Z815L Dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('22', '16', '0101', 'Z815M-1', 'Z815M 1st Dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('23', '16', '0101', 'Z815M-2', 'Z815M 2nd Dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('24', '17', '0009', 'Z817A-1', 'Z817A Power Switch', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('25', '18', '0101', 'Z817B-1', 'Z817B Dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('26', '19', '0002', 'Z817C-1', 'Z817C Power Switch', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('27', '20', '0009', 'Z825C-1', 'Z825C 1st Power Switch', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('28', '20', '0009', 'Z825C-2', 'Z825C 2nd Power Switch', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('29', '20', '0009', 'Z825C-3', 'Z825C 3rd Power Switch', '0006', '开关', '', '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib_en` VALUES ('30', '21', '0101', 'Z825E-1', 'Z825E 1st Dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('31', '21', '0101', 'Z825E-2', 'Z825E 2nd Dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('32', '22', '0101', 'ZC06-1', 'ZC06 Dimmer light', '0006:0008', '开关:调级控制', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('33', '23', '0009', 'Z800-1', 'Z800 Power Plug', '0006', '开关', '', '3', 'dest', '1', '0A', '', '1');
INSERT INTO `modeeplib_en` VALUES ('34', '24', '0002', 'Z803-1', 'Z803 1st Power Plug', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('35', '24', '0002', 'Z803-2', 'Z803 2nd Power Plug', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('36', '24', '0002', 'Z803-3', 'Z803 3rd Power Plug', '0006', '开关', '', '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib_en` VALUES ('37', '24', '0002', 'Z803-4', 'Z803 4th Power Plug', '0006', '开关', null, '3', 'dest', '1', '04', null, '1');
INSERT INTO `modeeplib_en` VALUES ('38', '24', '000D', 'Z803-5', 'Z803 Power Consumption Awareness', '0006', '开关', null, '3', '', '1', '05', null, '0');
INSERT INTO `modeeplib_en` VALUES ('39', '25', '0009', 'Z808A-1', 'Z808A Power Plug', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('40', '26', '0101', 'Z808B-1', 'Z808B Dimmable Power Plug', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('41', '27', '0009', 'Z809A-1', 'Z809A Power Plug', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('42', '28', '0101', 'Z809B-1', 'Z809B Dimmable Power Plug', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('43', '29', '0009', 'Z816B-1', 'Z816B US type Power Plug', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('44', '30', '0009', 'Z816G-1', 'Z816G UK type Power Plug', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('45', '31', '0009', 'Z816H-1', 'Z816H China type Power Plug', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('46', '32', '0302', 'Z711-1', 'Z711 Temperature and Humidity Sensor', null, null, null, null, null, '1', '0A', null, '0');
INSERT INTO `modeeplib_en` VALUES ('47', '33', '0302', 'Z712-1', 'Z712 Temperature and Humidity Sensor', '', '', '', '', '', '1', '0A', '', '0');
INSERT INTO `modeeplib_en` VALUES ('48', '34', '0302', 'Z713-1', 'Z713 Temperature and Humidity Sensor', '', '', null, '', '', '1', '0A', null, '0');
INSERT INTO `modeeplib_en` VALUES ('49', '35', '0302', 'Z716A-1', 'Z716A Temperature and Humidity Sensor', '', '', '', '', '', '1', '0A', '', '0');
INSERT INTO `modeeplib_en` VALUES ('50', '36', '0302', 'Z716B-1', 'Z716B Temperature Sensor', null, null, '', null, null, '1', '0A', '', '0');
INSERT INTO `modeeplib_en` VALUES ('51', '37', '0103', 'Z302B-1', 'Z302B Light Switch', '0006', '开关', null, '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('52', '38', '0104', 'Z302H-1', 'Z302H Light Sensor ', '0006:0008', '开关:调级控制', null, '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('53', '39', '0106', 'Z302G-1', 'Z302G Dimmer Switch', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('54', '40', '0103', 'Z311B-1', 'Z311B Light Switch', '0006', '开关', null, '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('55', '41', '0104', 'Z311H-1', 'Z311H Light Sensor ', '0006:0008', '开关:调级控制', null, '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('56', '42', '0106', 'Z311G-1', 'Z311G Dimmer Switch', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('57', '44', '0000', 'ZB02B-1', 'ZB02B Wall Switch 1st key', '0006', '开关', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('58', '44', '0000', 'ZB02B-2', 'ZB02B Wall Switch 2nd key', '0006', '开关', '', '3', 'source', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('59', '45', '0000', 'ZB02C-1', 'ZB02C Wall Switch 1st key', '0006', '开关', '', '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('60', '45', '0000', 'ZB02C-2', 'ZB02C Wall Switch 2nd key', '0006', '开关', '', '3', 'source', '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('61', '45', '0000', 'ZB02C-3', 'ZB02C Wall Switch 3rd key', '0006', '开关', '', '3', 'source', '1', '03', '', '0');
INSERT INTO `modeeplib_en` VALUES ('62', '46', '0104', 'ZB02F-1', 'ZB02F Wall Dimmer', '0006:0008', '开关:调级控制', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('63', '48', '0000', 'ZB01B-1', 'ZB01B On Off Switch', '0006', '开关', null, '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('64', '49', '0402', 'ZB01C-1', 'ZB01C Motion Detector', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('65', '52', '0000', 'ZB11B-1', 'ZB11B On Off Switch', '0006', '开关', null, '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('66', '53', '0402', 'ZB11C-1', 'ZB11C Motion Detector', null, null, '', null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('67', '55', '0200', 'Z815N-1', 'Z815N Shade Control Switch', '0006:0008', '开关:调级控制', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('68', '56', '0200', 'ZD01B-1', 'ZD01B First Curtain Controller', '0006:0008', '开关:调级控制', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('69', '56', '0200', 'ZD01B-2', 'ZD01B Second Curtain Controller', '0006:0008', '开关:调级控制', null, '3', 'dest', '1', '02', null, '1');
INSERT INTO `modeeplib_en` VALUES ('71', '47', '0402', 'ZB01A-1', 'ZB01A Motion Detector', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('72', '50', '0107', 'ZB01D-1', 'ZB01D Occupancy Sensor', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('73', '51', '0402', 'ZB11A-1', 'ZB11A Motion Detector', '', '', null, '', '', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('74', '49', '0000', 'ZB01C-2', 'ZB01C On Off Switch', '0006', '开关', null, '3', 'source', '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('75', '49', '0302', 'ZB01C-3', 'ZB01C Temperature Sensor', null, null, null, null, null, '1', '03', null, '0');
INSERT INTO `modeeplib_en` VALUES ('76', '53', '0000', 'ZB11C-2', 'ZB11C On Off Switch', '0006', '开关', null, '3', 'source', '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('77', '53', '0302', 'ZB11C-3', 'ZB11C Temperature Sensor', null, null, '', null, null, '1', '03', null, '0');
INSERT INTO `modeeplib_en` VALUES ('78', '54', '0107', 'ZB11D-1', 'ZB11D Occupancy Sensor', '', '', null, '', '', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('79', '57', '0402', 'Z302A-1', 'Z302A Window Intrusion Sensor', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('80', '58', '0402', 'Z302C-1', 'Z302C Window Glass Break Sensor and Intrusion Sensor', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('81', '59', '0402', 'Z302D-1', 'Z302D Panic Button', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('82', '60', '0402', 'Z302E-1', 'Z302E Asset tag', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('83', '61', '0000', 'Z302J-1', 'Z302J on off switch', '0006', '开关', '', '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('84', '61', '0402', 'Z302J-2', 'Z302J Window Intrusion Sensor', '', '', '', '', '', '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('85', '62', '0402', 'Z306D-1', 'Z306D Panic Button and Inductive Charging Base', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('86', '63', '0402', 'Z311A-1', 'Z311AWindow Intrusion Sensor', null, '', '', '', '', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('87', '64', '0402', 'Z311C-1', 'Z311C Window Glass Break Sensor and Intrusion Sensor', null, '', '', '', '', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('88', '65', '0401', 'Z312-1', 'Z312 Door Bell Button', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('89', '66', '0402', 'Z307-1', 'Z307 Fall Sensor and Inductive Charging Base', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('90', '67', '0402', 'Z308-1', 'Z308 Panic Button', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('91', '68', '0402', 'ZA01A-1', 'ZA01A Smoke Detector with Heat Sensor', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('92', '69', '0402', 'ZA01B-1', 'ZA01B Gas Detector with Heat Sensor', '', '', null, '', '', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('93', '70', '0402', 'ZA01C-1', 'ZA01C CO Detector', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('94', '71', '0402', 'ZA01D-1', 'ZA01D Liquefied Gas Detector with Heat Sensor', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('95', '72', '0402', 'ZA02E-1', 'ZA02E Photoelectric Smoke Detector with Heat Sensor', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('96', '73', '0002', 'ZA10-1', 'ZA10 Gas or Water Keeper', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('97', '74', '000A', 'ZB05-1', 'ZB05 Door Lock', '0101', '开关锁', null, '3', 'dest', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('98', '75', '0401', 'ZB02E-1', 'ZB02E Door Bell', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('99', '76', '0402', 'Z311E-1', 'Z311E Asset tag', '', '', null, '', '', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('100', '77', '0403', 'Z602A-1', 'Z602A Siren', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('101', '78', '0403', 'Z602B-1', 'Z602B Siren with GSM', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('102', '79', '0403', 'Z601A-1', 'Z601A Siren', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('103', '80', '000D', 'Z821-1', 'Z821 1st Power Consumption Awareness', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('104', '80', '000D', 'Z821-2', 'Z821 2nd Power Consumption Awareness', null, null, null, null, null, '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('105', '80', '000D', 'Z821-3', 'Z821 3rd Power Consumption Awareness', null, null, null, null, null, '1', '03', null, '0');
INSERT INTO `modeeplib_en` VALUES ('106', '80', '000D', 'Z821-4', 'Z821 4th Power Consumption Awareness', null, null, null, null, null, '1', '04', null, '0');
INSERT INTO `modeeplib_en` VALUES ('107', '80', '000D', 'Z821-5', 'Z821 5th Power Consumption Awareness', null, null, null, null, null, '1', '05', null, '0');
INSERT INTO `modeeplib_en` VALUES ('108', '80', '000D', 'Z821-6', 'Z821 6th Power Consumption Awareness', null, null, null, null, null, '1', '06', null, '0');
INSERT INTO `modeeplib_en` VALUES ('109', '80', '000D', 'Z821-7', 'Z821 7th Power Consumption Awareness', null, null, null, null, null, '1', '07', null, '0');
INSERT INTO `modeeplib_en` VALUES ('110', '80', '000D', 'Z821-8', 'Z821 All Power Consumption Awareness', null, null, null, null, null, '1', '08', null, '0');
INSERT INTO `modeeplib_en` VALUES ('111', '81', '0001', 'Z501A-1', 'Z501A dimmer Switch', '0006:0008', '开关:调级控制', null, '3', 'source', '1', '0A', null, '0');
INSERT INTO `modeeplib_en` VALUES ('112', '81', '0401', 'Z501A-2', 'Z501A security Keypad', '', '', null, '', '', '1', '0B', null, '0');
INSERT INTO `modeeplib_en` VALUES ('113', '82', '0000', 'Z501B-1', 'Z501B 1st Switch', '0006', '开关', null, '3', 'source', '1', '0A', null, '0');
INSERT INTO `modeeplib_en` VALUES ('114', '82', '0000', 'Z501B-2', 'Z501B 2nd Switch', '0006', '开关', null, '3', 'source', '1', '0B', null, '0');
INSERT INTO `modeeplib_en` VALUES ('115', '83', '0001', 'Z501C-1', 'Z501C dimmer Switch', '0006:0008', '开关:调级控制', null, '3', 'source', '1', '0A', null, '0');
INSERT INTO `modeeplib_en` VALUES ('116', '83', '0000', 'Z501C-2', 'Z501C on Off Switch', '0006', '开关', null, '3', 'source', '1', '0B', null, '0');
INSERT INTO `modeeplib_en` VALUES ('117', '84', '0008', 'Z210C-1', 'Z210C Infrared Adpt', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('118', '85', '0008', 'Z211-1', 'Z211 Bidirectional ZigBeeIR Adpt', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('119', '86', '0007', 'Z203-1', 'Z203 CWSHC', null, null, null, null, null, '1', '0A', null, '0');
INSERT INTO `modeeplib_en` VALUES ('120', '87', '0007', 'Z103AC-1', 'Z103AC USB', null, null, null, null, null, '1', '0A', null, '0');
INSERT INTO `modeeplib_en` VALUES ('121', '88', '0007', 'Z103AR-1', 'Z103AR USB', null, null, null, null, null, '1', '0A', null, '0');
INSERT INTO `modeeplib_en` VALUES ('122', '89', '0400', 'Z201B-1', 'Z201B ZigBee802.15.4 router andCIE', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('123', '90', '0400', 'Z201C-1', 'Z201C ZigBee802.15.4 Coord', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('124', '91', '0008', 'Z201R HA-1', 'Z201R ZigBee TCP/IP Gateway', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('125', '92', '0008', 'Z201R-1', 'Z201 ZigBee 802.15.4 Router', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('126', '93', '0002', 'Z812A-1', 'Z812A output', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('127', '93', '0000', 'Z812A-2', 'Z812A 1st Switch', '0006', '开关', null, '3', 'source', '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('128', '93', '0000', 'Z812A-3', 'Z812A 2nd Switch', '0006', '开关', null, '3', 'source', '1', '03', null, '0');
INSERT INTO `modeeplib_en` VALUES ('129', '93', '0000', 'Z812A-4', 'Z812A 3rd Switch', '0006', '开关', null, '3', 'source', '1', '04', null, '0');
INSERT INTO `modeeplib_en` VALUES ('130', '94', '0000', 'Z812B-1', 'Z812B 1st Switch', '0006', '开关', null, '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('131', '94', '0000', 'Z812B-2', 'Z812B 2nd Switch', '0006', '开关', null, '3', 'source', '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('132', '94', '0000', 'Z812B-3', 'Z812B 3rd Switch', '0006', '开关', null, '3', 'source', '1', '03', null, '0');
INSERT INTO `modeeplib_en` VALUES ('133', '94', '0000', 'Z812B-4', 'Z812B 4th Switch', '0006', '开关', null, '3', 'source', '1', '04', null, '0');
INSERT INTO `modeeplib_en` VALUES ('134', '94', '0104', 'Z812B-5', 'Z812B 1st Dimmer Switch', '0006:0008', '开关:调级控制', null, '3', 'source', '1', '05', null, '0');
INSERT INTO `modeeplib_en` VALUES ('135', '94', '0104', 'Z812B-6', 'Z812B 2nd Dimmer Switch', '0006:0008', '开关:调级控制', null, '3', 'source', '1', '06', null, '0');
INSERT INTO `modeeplib_en` VALUES ('136', '95', '0402', 'Z801TXB-1', 'Z801TXB 1st Detector', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('137', '95', '0402', 'Z801TXB-2', 'Z801TXB 2nd Detector', '', '', '', '', '', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('138', '95', '0402', 'Z801TXB-3', 'Z801TXB 3rd Detector', '', '', '', '', '', '1', '03', '', '0');
INSERT INTO `modeeplib_en` VALUES ('139', '95', '0402', 'Z801TXB-4', 'Z801TXB 4th Detector', '', '', '', '', '', '1', '04', '', '0');
INSERT INTO `modeeplib_en` VALUES ('140', '95', '0402', 'Z801TXB-5', 'Z801TXB 5th Detector', '', '', '', '', '', '1', '05', '', '0');
INSERT INTO `modeeplib_en` VALUES ('141', '96', '0002', 'Z801RX', 'Z801RX output', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('142', '101', '0009', 'Z825A-1', 'Z825APower Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('143', '102', '0009', 'Z825B-1', 'Z825B 1st Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('144', '102', '0009', 'Z825B-2', 'Z825B 2nd Power Switch', '0006', '开关', null, '3', 'dest', '1', '02', null, '1');
INSERT INTO `modeeplib_en` VALUES ('145', '103', '0101', 'Z825D-1', 'Z825D Dimmer Switch', '0006:0008', '开关:调级控制', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('146', '100', '0400', 'Z508-1', 'Z508 In Home Display', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('147', '104', '0302', 'Z725A-1', 'Z725A Temperature and Humidity Sensor', null, null, null, null, null, '1', '0A', null, '0');
INSERT INTO `modeeplib_en` VALUES ('148', '106', '0008', 'Z725R-1', 'Z725R Outdoor Range Extender', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('149', '107', '0009', 'Z962A-1', 'Z962A Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('150', '108', '0009', 'Z962B-1', 'Z962B 1st Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('151', '108', '0009', 'Z962B-2', 'Z962B 2nd Power Switch', '0006', '开关', null, '3', 'dest', '1', '02', null, '1');
INSERT INTO `modeeplib_en` VALUES ('152', '109', '0009', 'Z962C-1', 'Z962C 1st Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('153', '109', '0009', 'Z962C-2', 'Z962C 2nd Power Switch', '0006', '开关', null, '3', 'dest', '1', '02', null, '1');
INSERT INTO `modeeplib_en` VALUES ('154', '109', '0009', 'Z962C-3', 'Z962C 3rd Power Switch', '0006', '开关', null, '3', 'dest', '1', '03', null, '1');
INSERT INTO `modeeplib_en` VALUES ('155', '110', '0004', 'Z962D-1', 'Z962D Scene or Mode Selector', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('156', '111', '0004', 'Z962E-1', 'Z962E 1st Scene or Mode Selector', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('157', '111', '0004', 'Z962E-2', 'Z962E 2nd Scene or Mode Selector', null, null, null, null, null, '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('158', '112', '0004', 'Z962F-1', 'Z962F 1st Scene or Mode Selector', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('159', '112', '0004', 'Z962F-2', 'Z962F 2nd Scene or Mode Selector', null, null, null, null, null, '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('160', '112', '0004', 'Z962F-3', 'Z962F 3rd Scene or Mode Selector', null, null, null, null, null, '1', '03', null, '0');
INSERT INTO `modeeplib_en` VALUES ('161', '113', '0009', 'Z962G-1', 'Z962G Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('162', '113', '0004', 'Z962G-2', 'Z962G Scene or Mode Selector', null, null, null, null, null, '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('163', '114', '0009', 'Z962H-1', 'Z962H Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('164', '114', '0004', 'Z962H-2', 'Z962H 1st Scene or Mode Selector', null, null, null, null, null, '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('165', '114', '0004', 'Z962H-3', 'Z962H 2nd Scene or Mode Selector', null, null, null, null, null, '1', '03', null, '0');
INSERT INTO `modeeplib_en` VALUES ('166', '115', '0004', 'Z962I-1', 'Z962I Scene or Mode Selector', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('167', '115', '0009', 'Z962I-2', 'Z962I 1st Power Switch', '0006', '开关', null, '3', 'dest', '1', '02', null, '1');
INSERT INTO `modeeplib_en` VALUES ('168', '115', '0009', 'Z962I-3', 'Z962I 2nd Power Switch', '0006', '开关', null, '3', 'dest', '1', '03', null, '1');
INSERT INTO `modeeplib_en` VALUES ('174', '116', '0402', 'Z801WLS-1', 'Z801WLS 1st water sensor', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('175', '116', '0402', 'Z801WLS-2', 'Z801WLS 2nd water sensor', '', '', '', '', '', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('176', '116', '0402', 'Z801WLS-3', 'Z801WLS 3rd water sensor', '', '', '', '', '', '1', '03', '', '0');
INSERT INTO `modeeplib_en` VALUES ('177', '116', '0402', 'Z801WLS-4', 'Z801WLS 4th water sensor', '', '', '', '', '', '1', '04', '', '0');
INSERT INTO `modeeplib_en` VALUES ('178', '116', '0402', 'Z801WLS-5', 'Z801WLS 5th water sensor', '', '', '', '', '', '1', '05', '', '0');
INSERT INTO `modeeplib_en` VALUES ('179', '117', '000A', 'ZB05A-1', 'ZB05A Door Lock', '0101', '开关锁', '', '3', 'dest', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('180', '118', '0000', 'Z801TX-1', 'Z801TX 1st Switch', '0006', '开关', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('181', '118', '0000', 'Z801TX-2', 'Z801TX  2nd Switch', '0006', '开关', '', '3', 'source', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('182', '118', '0000', 'Z801TX-3', 'Z801TX  3rd Switch', '0006', '开关', '', '3', 'source', '1', '03', '', '0');
INSERT INTO `modeeplib_en` VALUES ('183', '118', '0000', 'Z801TX-4', 'Z801TX  4th Switch', '0006', '开关', '', '3', 'source', '1', '04', '', '0');
INSERT INTO `modeeplib_en` VALUES ('184', '118', '0000', 'Z801TX-5', 'Z801TX  5th Switch', '0006', '开关', '', '3', 'source', '1', '05', '', '0');
INSERT INTO `modeeplib_en` VALUES ('185', '119', '0000', 'Z311J-1', 'Z311J on off switch', '0006', '开关', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('186', '119', '0402', 'Z311J-2', 'Z311J Window Intrusion Sensor', '', '', '', '', '', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('187', '120', '0101', 'ZC07-1', 'ZC07 Dimmer light', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('188', '121', '0402', 'Z311W-1', 'Z311W water sensor', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('190', '122', '0200', 'Z811B-1', 'Z811B 1st Curtain Controller', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('191', '122', '0200', 'Z811B-2', 'Z811B 2nd Curtain Controller', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('192', '123', '0004', 'Z825I-1', 'Z825I 1st Scene or Mode Selector', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('193', '123', '0004', 'Z825I-2', 'Z825I 2nd Scene or Mode Selector', '', '', '', '', '', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('194', '123', '0004', 'Z825I-3', 'Z825I 3rd Scene or Mode Selector', '', '', '', '', '', '1', '03', '', '0');
INSERT INTO `modeeplib_en` VALUES ('195', '123', '0004', 'Z825I-4', 'Z825I 4th Scene or Mode Selector', '', '', '', '', '', '1', '04', '', '0');
INSERT INTO `modeeplib_en` VALUES ('196', '123', '0004', 'Z825I-5', 'Z825I 5th Scene or Mode Selector', '', '', '', '', '', '1', '05', '', '0');
INSERT INTO `modeeplib_en` VALUES ('197', '123', '0004', 'Z825I-6', 'Z825I 6th Scene or Mode Selector', '', '', '', '', '', '1', '06', '', '0');
INSERT INTO `modeeplib_en` VALUES ('201', '124', '0004', 'Z825J-4', 'Z825J 1st Scene or Mode Selector', '', '', '', '', '', '1', '04', '', '0');
INSERT INTO `modeeplib_en` VALUES ('202', '124', '0004', 'Z825J-5', 'Z825J 2nd Scene or Mode Selector', '', '', '', '', '', '1', '05', '', '0');
INSERT INTO `modeeplib_en` VALUES ('203', '124', '0004', 'Z825J-6', 'Z825J 3rd Scene or Mode Selector', '', '', '', '', '', '1', '06', '', '0');
INSERT INTO `modeeplib_en` VALUES ('204', '124', '0009', 'Z825J-1', 'Z825J 1st Output', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('205', '124', '0009', 'Z825J-2', 'Z825J 2nd Output', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('206', '124', '0009', 'Z825J-3', 'Z825J 3rd Output', '0006', '开关', '', '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib_en` VALUES ('207', '125', '0009', 'Z825Q-1', 'Z825Q 1st Power Switch', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('208', '125', '0009', 'Z825Q-2', 'Z825Q 2nd Power Switch', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('209', '125', '0009', 'Z825Q-3', 'Z825Q 3rd Power Switch', '0006', '开关', '', '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib_en` VALUES ('210', '126', '0009', 'Z816I-1', 'Z816I China type Power Plug', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('211', '127', '0000', 'Z817D-1', 'Z817D  On Off Switch', '0006', '开关', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('212', '128', '0008', 'Z800R-1', 'Z800 Plug Router', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('213', '129', '0008', 'Z809C-1', 'Z809C Plug Router', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('214', '130', '0008', 'Z809D-1', 'Z809D Plug Router', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('215', '131', '0001', 'Z501AE3ED-1', 'Z501A dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('216', '131', '0401', 'Z501AE3ED-2', 'Z501A security Keypads', '', '', '', '', '', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('217', '132', '0000', 'Z501BE3ED-1', 'Z501B 1st Switch', '0006', '开关', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('218', '132', '0000', 'Z501BE3ED-2', 'Z501B 2nd Switch', '0006', '开关', '', '3', 'source', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('219', '133', '0001', 'Z501CE3ED-1', 'Z501C dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('220', '133', '0000', 'Z501CE3ED-2', 'Z501C security Keypads', '0006', '开关', '', '3', 'source', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('221', '134', '0402', 'ZB02I-1', 'ZB02IPanic Button', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('222', '137', '0002', 'Z802-1', 'Z802 1st Output', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('223', '137', '0002', 'Z802-2', 'Z802 2nd Output', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('225', '138', '0004', 'ZB02J-1', 'ZB02J 1st Scene or Mode Selector', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('226', '138', '0004', 'ZB02J-2', 'ZB02J 2nd Scene or Mode Selector', '', '', '', '', '', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('227', '138', '0004', 'ZB02J-3', 'ZB02J 3rd Scene or Mode Selector', '', '', '', '', '', '1', '03', '', '0');
INSERT INTO `modeeplib_en` VALUES ('228', '139', '0009', 'Z815A-1', 'Z815A Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('229', '140', '0009', 'Z815B-1', 'Z815B 1st Power Switch', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('230', '140', '0009', 'Z815B-2', 'Z815B 2nd Power Switch', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('231', '141', '0009', 'Z815C-1', 'Z815C 1st Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('232', '141', '0009', 'Z815C-2', 'Z815C 2nd Power Switch', '0006', '开关', null, '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('233', '141', '0009', 'Z815C-3', 'Z815C 3rd Power Switch', '0006', '开关', null, '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib_en` VALUES ('234', '142', '0101', 'Z815D-1', 'Z815D 1st dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('236', '143', '0101', 'Z815E-1', 'Z815E 2nd dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('237', '143', '0101', 'Z815E-2', 'Z815E 3rd dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('238', '144', '0009', 'Z826C-1', 'Z826C 1st Power Switch', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('239', '144', '0009', 'Z826C-2', 'Z826C 2nd Power Switch', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('240', '144', '0009', 'Z826C-3', 'Z826C 3rd Power Switch', '0006', '开关', '', '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib_en` VALUES ('241', '145', '0007', 'Z206-1', 'Z501G 1st security Keypads', '', '', '', '', '', '1', '0A', '', '0');

/*英文设备表*/
CREATE TABLE `modenodelib_en` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nodeType` varchar(100) DEFAULT NULL,
  `modelId` varchar(50) DEFAULT NULL,
  `deviceName` varchar(200) DEFAULT NULL,
  `clusterId` varchar(200) DEFAULT NULL,
  `clusterName` varchar(100) DEFAULT NULL,
  `destType` varchar(4) DEFAULT NULL,
  `source_id` bigint(20) DEFAULT '1',
  `deviceType` varchar(20) DEFAULT NULL,
  `picName` varchar(100) DEFAULT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `powerType` varchar(200) DEFAULT NULL,
  `activationMethod` varchar(2000) DEFAULT NULL,
  `resetDefault` varchar(2000) DEFAULT NULL,
  `remark` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `nodeType` (`nodeType`) USING BTREE,
  KEY `modelId` (`modelId`) USING BTREE,
  KEY `deviceName` (`deviceName`) USING BTREE,
  KEY `clusterId` (`clusterId`) USING BTREE,
  KEY `destType` (`destType`) USING BTREE,
  KEY `deviceType` (`deviceType`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=146 DEFAULT CHARSET=utf8;

/*添加设备英文名称及英文描述*/
INSERT INTO `modenodelib_en` VALUES ('2', '0', 'Z815I', 'Z815I Wall Switch with Power Meter (1-Gang)', '0006', '开关', '3', '1', 'dest', 'Z815I.jpg', 'Z815I is a one-gang Output Switch and its enclosure is designed to replace conventional junction box installation wall switches.  It is equipped with load consumption inspection and load control feature. Users can use ZigBee enabled remote controller and on/off device and smart phone to control Z815I. ', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('3', '0', 'Z815K', 'Z815K Wall Switch with Power Meter (2-Gang)', '0006', '开关', '3', '1', 'dest', 'Z815K.jpg', 'Z815K a three-gang Output Switch and its enclosure is designed to replace conventional junction box installation wall switches.  It is equipped with load consumption inspection and load control feature. Users can use ZigBee enabled remote controller and on/off device and smart phone to control Z815K. ', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('4', '0', 'Z806', 'Z806 Switch Control Unit (2-Output)', '0006', '开关', '3', '1', 'dest', 'Z806.jpg', '\r\nZ806 is a wireless switch relay with two outputs. Users can locally and remotely control it via external switch, paired ZigBee enabled devices and software.', '100～240V的交流电源', '不需要激活', '按住绑定键的同时给设备上电，开始恢复出厂设置，状态指示灯一直闪烁表示擦除完成。之后重新上电，Z806可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('5', '4', 'ZB02A', 'ZB02A Wall Switch (1-Key)', '0006', '开关', '3', '1', 'source', 'ZB02A.jpg', 'ZB02A is a single gang wall switch which can be hung on the wall. Paired with the other On/Off devices, it can be remotely controlled by toggling.', '2节AAA1.5V电池', '拆下面板的上盖，短按绑定键（红色按键），LED网络灯闪5下激活设备。', '拆下面板的上盖，按住绑定键的同时给设备上电，进行恢复出厂设置，看到状态指示灯闪烁，表示恢复完成', '无');
INSERT INTO `modenodelib_en` VALUES ('7', '9', 'Z503', 'Z503 Local Commander', '0006:0008', '开关:调级控制', '1', '1', 'source', 'Z503.jpg', 'Z503 is a multifunctional remote which can turn on/off all devices, dimming, control power socket, emergency button function, arm and disarm security system.', '3V CR2034纽扣电池', '按住“2nd”键和“3”键可激活设备。', '1.同时按住扩展情景键和第二功能按键，给设备上电；2. 如指示灯快速闪烁10次表示恢复出厂值完成，设备自动重启后即可重新加网；3. 如指示灯慢闪3次，表示恢复出厂值失败，请重复步骤1。', '无');
INSERT INTO `modenodelib_en` VALUES ('10', '0', 'Z805A', 'Z805A Euro Type Switch Control Unit with Power Meter (1-Output)', '0006', '开关', '3', '1', 'dest', 'Z805A.jpg', 'Z805A is a wireless switch relay with one output 16A/250V AC. Users can control it via external switch, paired ZigBee enabled devices and software. Via ZigButler, users can check the real-time current/voltage status and kilo-watt-hour consumption of the attached appliance. ', '100～240V的交流电源', '不需要激活', '长按绑定键15S(指示灯闪烁2次,3S闪烁一次，10S闪烁一次），状态灯开始快闪20秒，设备进行恢复出厂设置；20s内短按任意键，Z805B重新请求加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('11', '0', 'Z805B', 'Z805B Switch Control Unit with Power Meter (1-Output)', '0006', '开关', '3', '1', 'dest', 'Z805B.jpg', 'Z805B is wireless switch relay with one output 16A/250V AC. Users can remotely control it via external switch, paired ZigBee enabled devices and softwareVia ZigButler, users can check the real-time current/voltage status and kilo-watt-hour consumption of the attached appliance. 。', '100～240V的交流电源', '不需要激活', '长按绑定键15S(指示灯闪烁2次,3S闪烁一次，10S闪烁一次），状态灯开始快闪20秒，设备进行恢复出厂设置；20s内短按任意键，Z805B重新请求加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('12', '0', 'Z810B', 'Z810B Switch Control Unit with Power Monitoring LCD', '0006', '开关', '3', '1', 'dest', 'Z810B.jpg', 'Z810B is a switch control, power supply is 100-240VAC 50/60HZ. It can be controlled by its manual override switch or by a paired wireless switch or by software. Users can use software and its LCD to check the real-time current/voltage status and kilo-watt-hour consumption of the attached appliance.', '100～240V的交流电源', '不需要激活', '按下绑定键15秒后（图标每隔5秒闪烁一下，闪烁3次），2秒内短按功能键，LCD数值区显示表示恢复完成，之后设备将自动重启。', '无');
INSERT INTO `modenodelib_en` VALUES ('13', '0', 'Z811', 'Z811 Switch Control Unit (4-Output)', '0006', '开关', '3', '1', 'dest', 'Z811.jpg', 'Z811 is a wireless on/off output switch device. Users can control it via paired ZigBee enabled devices and software.', '100～240V的交流电源', '不需要激活', '长按绑定键15秒（状态指示灯3S闪一次，10S闪一次,15S闪一次），然后短按，状态指示灯一直闪烁表示擦除完成。之后指示灯灭掉，指示Z811便可以重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('14', '0', 'Z815J', 'Z815J Wall Switch with Power Meter (2-Gang)', '0006', '开关', '3', '1', 'dest', 'Z815J.jpg', 'Z815J is a two-gang switch and is designed to replace conventional junction box installation wall switches.  It is equipped with load consumption inspection and load control feature.  Users can use ZigBee enabled remote controller and on/off device and smart phone to control Z815J.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('15', '0', 'Z815L', 'Z815L Wall Dimmer Switch with Power Meter (1-Gang)', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z815L.jpg', 'Z815L is a one-gang dimmable switch and is designed to replace conventional junction box installation wall switches.  It is equipped with load consumption inspection and load control feature. Users can use ZigBee enabled remote controller and on/off device and smart phone to control Z815L.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('16', '0', 'Z815M', 'Z815M Wall Dimmer Switch with Power Meter (2-Gang)', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z815M.jpg', 'Z815M is a two-gang dimmable switch and is designed to replace conventional junction box installation wall switches.  It is equipped with load consumption inspection and load control feature. Users can use ZigBee enabled remote controller and on/off device and smart phone to control Z815M.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('17', '0', 'Z817A', 'Z817A Ceiling 16A Relay Switch with Power Meter', '0006', '开关', '3', '1', 'dest', 'Z817A.jpg', 'Z817A is a ceiling mount switch unit. It can do on/off switch and dim the lights through its buttoms, paired ZigBee enabled remote controllers or even software. Energy consumption reading is able to be monitored with software. ', '100～240V的交流电源', '不需要激活', '按住绑定键（正对logo靠左）15秒，设备网络灯闪烁一次，然后在2秒内短按任意键，设备网络灯快闪10次后重启。', '无');
INSERT INTO `modenodelib_en` VALUES ('18', '0', 'Z817B', 'Z817B Ceiling 16A Relay Dimmer with Power Meter', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z817B.jpg', 'Z817B is a ceiling mount dimmable unit. It can do on/off switch and dim the lights through its buttoms, paired ZigBee enabled remote controllers or even software. Energy consumption reading is also able to be monitored with software. ', '100～240V的交流电源', '不需要激活', '按住绑定键（正对logo靠左）15秒，设备网络灯闪烁一次，然后在2秒内短按任意键，设备网络灯快闪10次后重启。', '无');
INSERT INTO `modenodelib_en` VALUES ('19', '0', 'Z817C', 'Z817C Ceiling Motion Detector with On/Off Switch', '0006', '开关', '3', '1', 'dest', 'Z817C.jpg', 'Z825C is a three-gang touch switch and is designed to replace conventional junction box installation wall switches. It is equipped with load consumption inspection and load control feature. Users can use ZigBee enabled remote controller and on/off device and smart phone to control Z825C.', '100～240V的交流电源', '不需要激活', '按住绑定键（正对logo靠左）15秒，设备网络灯闪烁一次，然后在2秒内短按任意键，设备网络灯快闪10次后重启。', '无');
INSERT INTO `modenodelib_en` VALUES ('20', '0', 'Z825C', 'Z825C Touch Panel Switch with Power Meter (3-Gang)', '0006', '开关', '3', '1', 'dest', 'Z825C.jpg', 'Z825C is a three-gang touch switch and is designed to replace conventional junction box installation wall switches. It is equipped with load consumption inspection and load control feature. Users can use ZigBee enabled remote controller and on/off device and smart phone to control Z825C.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('21', '0', 'Z825E', 'Z825E Touch Panel Dimmer Switch with Power Meter (2-Gang)', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z825E.jpg', 'Z825E is a twp-gang touch switch and is designed to replace conventional junction box installation wall switches. It is equipped with load consumption inspection and load control feature. Users can use ZigBee enabled remote controller and on/off device and smart phone to control Z825E.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('22', '0', 'ZC06', 'ZC06 Dimmable LED Tube (120cm)', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'ZC06.jpg', 'ZC06 is a robust ZigBee enabled dimmable LED tube. It has 256 light levels function. It is a long life and light weighted power consumption which conserves 60% comparing to the conventional light tubes. ZC06 utilizes SMT LEDs with its 400 lumen/1.5 meters and 50,000-hour lamp life. ZC06 can be controlled wirelessly via remote controller. It supports AC 100V to 240V with the same controlling features.', '100～240V的交流电源', '不需要激活', '同时长按测试键和绑定键5s，5s时间到后指示灯闪10次后就恢复出厂设置成功，此时就可以重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('23', '1', 'Z800', 'Z800 Power Plug with Power Meter', '0006', '开关', '3', '1', 'dest', 'Z800B.jpg', 'Z800 is a power socket with power consumption monitoring that allows off site remote control. Users can control it by its bottom and by paired ZigBee enabled devices and software. Energy consumption reading is able to be monitored with software.', '100～240V的交流电源', '不需要激活', '长按绑定键15S，状态灯（红灯）快闪20S；设备进入恢复出厂设置；再按任意键重新请求加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('24', '1', 'Z803', 'Z803 Power Strip with Power Monitoring LCD & USB Port', '0006', '开关', '3', '1', 'dest', 'Z803.jpg', 'Z803 is a 4-gang power strip with power consumption display. It has USB jacks that facilitate USB charging purpose and built-in LCD. Users could also wirelessly control the switch through its own bottom, paired devices or software. LCD can display total power consumption of all sockets. Via the built-in LCD or application, users are able to check the consuming current/ voltage/ power/ energy.', '100～240V的交流电源', '不需要激活', '同时按下Match Key 和Bind Key 按键5秒后，！图标闪烁一下，代表长按了5秒。放开按键后，进行出厂设置，设备自动重新上电。', '无');
INSERT INTO `modenodelib_en` VALUES ('25', '1', 'Z808A', 'Z808A Power Plug with Power Monitoring LCD & USB Port', '0006', '开关', '3', '1', 'dest', 'Z808A.jpg', 'Z808A is a wireless smart plug. It has USB jacks that facilitate USB charging purpose and built-in LCD. Users could also wirelessly control the switch through its own bottom, paired devices or software. LCD can display total power consumption of all sockets. Via the built-in LCD or application, users are able to check the consuming current/ voltage/ power/ energy.', '100～240V的交流电源', '不需要激活', '长按绑定键（右边按键）15s（期间网络指示灯闪3次）后释放绑定键，并在2s内短按功能键（左边按键），即开始恢复出厂设置，网络灯快速闪烁十次后自动重启设备，开始寻找新的网络。', '无');
INSERT INTO `modenodelib_en` VALUES ('26', '1', 'Z808B', 'Z808B Dimmable Power Plug with Power Monitoring LCD & USB Port', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z808B.jpg', 'Z808B is a wireless dimmable smart plug. It has USB jacks that facilitate USB charging purpose and built-in LCD. Users could also wirelessly control the switch through its own bottom, paired devices or software. LCD can display total power consumption of all sockets. Via the built-in LCD or application, users are able to check the consuming current/ voltage/ power/ energy.', '100～240V的交流电源', '不需要激活', '长按绑定键（右边按键）15s（期间网络指示灯闪3次）后释放绑定键，并在2s内短按功能键（左边按键），即开始恢复出厂设置，网络灯快速闪烁十次后自动重启设备，开始寻找新的网络。', '无');
INSERT INTO `modenodelib_en` VALUES ('27', '1', 'Z809A', 'Z809A Power Plug with Power Meter', '0006', '开关', '3', '1', 'dest', 'Z809A.jpg', 'Z809A is a smart plug that allows off site remote control. Users can manually switch on/off the socket or by software to control it. Via software, users are able to monitor the load current, voltage and power and kilo-watt-hour consumption of the attached appliance.', '100～240V的交流电源', '不需要激活', '　长按绑定键（侧面）15s（期间网络指示灯闪3次）后释放绑定键，并在2s内短按测试键（正面按键），即开始恢复出厂设置，网络灯快速闪烁十次后自动重启设备，开始寻找新的网络。	无', '无');
INSERT INTO `modenodelib_en` VALUES ('28', '1', 'Z809B', 'Z809B Dimmable Power Plug with Power Meter', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z809B.jpg', 'Z809B is a dimmable smart plug that allows off site remote control. Users can manually switch on/off the socket or by paired derives or by software to control and to dim. Via software, users are able to monitor the load current, voltage and power and kilo-watt-hour consumption of the attached appliance. ', '100～240V的交流电源', '不需要激活', '长按绑定键（侧面）15s（期间网络指示灯闪3次）后释放绑定键，并在2s内短按测试键（正面按键），即开始恢复出厂设置，网络灯快速闪烁十次后自动重启设备，开始寻找新的网络。', '无');
INSERT INTO `modenodelib_en` VALUES ('29', '1', 'Z816B', 'Z816B US Type Wall Socket with Power Meter', '0006', '开关', '3', '1', 'dest', 'Z816B.jpg', 'Z816B is a US type wireless smart wall socket that allows off site remote control. Users can manually switch on/off the socket or by paired derives or by software to control and to dim. Via software, users are able to monitor the load current, voltage and power and kilo-watt-hour consumption of the attached appliance.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('30', '1', 'Z816G', 'Z816G UK Type Wall Socket with Power Meter', '0006', '开关', '3', '1', 'dest', 'Z816G.jpg', 'Z816G is a UK type wireless wall socket, it can directly install in 86 junction box and replace the traditional wall socket. It allows off site remote control. Users can manually switch on/off the socket or by paired derives or by software to control and to dim. Via software, users are able to monitor the load current, voltage and power and kilo-watt-hour consumption of the attached appliance.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('31', '1', 'Z816H', 'Z816H China Type Wall Socket with Power Meter', '0006', '开关', '3', '1', 'dest', 'Z816HI.jpg', 'Z816H is a China type wireless smart wall socket, it can directly install in 86 junction box and replace the traditional wall socket. It allows off site remote control. Users can manually switch on/off the socket or by paired derives or by software to control and to dim. Via software, users are able to monitor the load current, voltage and power and kilo-watt-hour consumption of the attached appliance.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('32', '2', 'Z711', 'Z711 Temperature and Humidity Sensor (Indoor)', null, null, null, '1', null, 'Z711.jpg', 'Z711 is a humidity and temperature sensor. It is used for collecting ambient H/T data and sending them to the display. ', '2节1.5V电池', '按下按键可激活设备', '1.  按住绑定键的同时给设备上电；2.  复位完成则led闪烁30次后停止；3.  重新上电，Z711可以开始重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('33', '2', 'Z712', 'Z712 Temperature and Humidity Sensor (Outdoor)', '', '', '', '1', '', 'Z712.jpg', 'Z712 is used to detect the outdoor humidity and temperature. It also increases a splash proof housing for protection and transmits the collecting data to a displayed device through wireless network.', '2节1.5V电池', '按下按键可激活设备', '1.  按住绑定键的同时给设备上电；2.  复位完成则led闪烁30次后停止；3.  重新上电，Z711可以开始重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('34', '2', 'Z713', 'Z713 Temperature and Humidity Sensor with Solar Panel (Outdoor)', '', '', '', '1', '', 'Z713.jpg', ' Z713 is a detector for humidity, temperature and UV rays with a solar panel charger.  It\'s also equiped with a waterproof housing for protection and used for collecting ambient H/T data and UV intensity, then sending the data to the display.  ', '2节1.5V电池', '按下按键可激活设备', '1.  按住绑定键的同时给设备上电；2.  复位完成则led闪烁30次后停止；3.  重新上电，Z711可以开始重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('35', '2', 'Z716A', 'Z716A Temperature and Humidity Sensor with LCD (Indoor)', '', '', '', '1', '', 'Z716A.jpg', 'Z716A is a humidity and temperature detector which is used for collecting ambient H/T data and display on the LCD.  It can also send the data to the displaying device. ', '2节1.5V电池', '按下右边按键可激活设备', '设备上电后，同时按住“按键1”和“按键2” 5秒，开始恢复出厂值，LCD上显示所有图标画面，表示恢复出厂值完成，设备自动复位寻找加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('36', '2', 'Z716B', 'Z716B Temperature Sensor with LCD (Indoor)', null, null, null, '1', null, 'Z716B.jpg', 'Z716B is a temperature detector which is used for collecting ambient temperature data and display on the LCD.  It can also send the data to the displaying device. ', '2节1.5V电池', '按下右边按键可激活设备', '设备上电后，同时按住“按键1”和“按键2” 5秒，开始恢复出厂值，LCD上显示所有图标画面，表示恢复出厂值完成，设备自动复位寻找加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('37', '3', 'Z302B', 'Z302B On/Off Light Switch', '0006', '开关', '3', '1', 'source', 'Z302B.jpg', 'Z302B is an automatic light switch controller, which can bind with the output switches. When the brightness is lower than a certain level, Z302B will transmit \"ON\" command and control the binding device. When the brightness enhance to a certain level, Z302B will transmit \"OFF\" command to the binding device. ', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('38', '3', 'Z302H', 'Z302H Light Sensor Dimmer Switch', '0006:0008', '开关:调级控制', '3', '1', 'source', 'Z302H.jpg', 'Z302H is a light dimmer which can sense the intensity of the ambient lighting and determine whether it need to change the brightness of its binding lamp. Thus the controlled lamp/light will maintain the certain brightness within the area. ', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('39', '3', 'Z302G', 'Z302G Light Sensor', null, null, null, '1', null, 'Z302G.jpg', 'The Z302G is an automatic detector and reportor for environment illumination.  It can regularly transmit ambient illumination to a device which can receive and display the ambient illumination.  ', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('40', '3', 'Z311B', 'Z311B On/Off Light Switch', '0006', '开关', '3', '1', 'source', 'Z311B.jpg', 'Z311B is an automatic light switch controller, which can bind with the output switches. When the brightness is lower than a certain level, Z311B will transmit \"ON\" command and control the binding device. When the brightness enhance to a certain level, Z311B will transmit \"OFF\" command to the binding device.  ', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '同时按下绑定键和辅助键，两键同时按着5s，直到看到红色指示灯开始快闪，再松开按键。红色指示灯快闪20次后，设备复位将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('41', '3', 'Z311H', 'Z311H Light Sensor Dimmer Switch', '0006:0008', '开关:调级控制', '3', '1', 'source', 'Z311H.jpg', 'Z311H is a light dimmer which can sense the intensity of the ambient lighting and determine whether it need to change the brightness of its binding lamp. Thus the controlled lamp/light will maintain the certain brightness within the area. ', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '同时按下绑定键和辅助键，两键同时按着5s，直到看到红色指示灯开始快闪，再松开按键。红色指示灯快闪20次后，设备复位将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('42', '3', 'Z311G', 'Z311G Light Sensor', '', '', '', '1', '', 'Z311G.jpg', 'The Z311G is an automatic detector and reportor for environment illumination.  It can regularly transmit ambient illumination to a device which can receive and display the ambient illumination. ', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '同时按下绑定键和辅助键，两键同时按着5s，直到看到红色指示灯开始快闪，再松开按键。红色指示灯快闪20次后，设备复位将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('44', '4', 'ZB02B', 'ZB02B Wall Switch (2-Key)', '0006', '开关', '3', '1', 'source', 'ZB02B.jpg', 'ZB02B is a two keys wireless wall-mounted switch. It can be bound with a device which has switch control function and control the switch wirelessly. ', '2节AAA1.5V电池', '拆下面板的上盖，短按绑定键（红色按键），LED网络灯闪5下激活设备。', '拆下面板的上盖，按住绑定键的同时给设备上电，进行恢复出厂设置，看到状态指示灯闪烁，表示恢复完成', '无');
INSERT INTO `modenodelib_en` VALUES ('45', '4', 'ZB02C', 'ZB02C Wall Switch (3-Key)', '0006', '开关', '3', '1', 'source', 'ZB02C.jpg', 'ZB02C is a three keys wireless wall-mounted switch. It can be bound with a device which has switch control function and control the switch wirelessly. ', '2节AAA1.5V电池', '拆下面板的上盖，短按绑定键（红色按键），LED网络灯闪5下激活设备。', '拆下面板的上盖，按住绑定键的同时给设备上电，进行恢复出厂设置，看到状态指示灯闪烁，表示恢复完成', '无');
INSERT INTO `modenodelib_en` VALUES ('46', '4', 'ZB02F', 'ZB02F Wall Dimmer Switch', '0006:0008', '开关:调级控制', '3', '1', 'source', 'ZB02F.jpg', 'ZB02F is a single key wireless wall-mounted switch. It can be bound with a device which has switch and level control function and control the switch wirelessly. ', '2节AAA1.5V电池', '拆下面板的上盖，短按绑定键（红色按键），LED网络灯闪5下激活设备。', '拆下面板的上盖，按住绑定键的同时给设备上电，进行恢复出厂设置，看到状态指示灯闪烁，表示恢复完成。', '无');
INSERT INTO `modenodelib_en` VALUES ('47', '5', 'ZB01A', 'ZB01A Motion Detector', null, null, null, '1', null, 'ZB01A.jpg', 'The infrared detection alarm function of ZB01A can detect the movement of object. And trigger the alarm through the security center. ', '2节CR123A电池或12V直流电源', '按住背面小圆点按键3秒以上，直至状态指示灯快速闪烁2次激活设备', '按住背面小圆点（绑定键）的同时给设备上电，状态灯快闪，即恢复出厂设置操作成功。恢复完成后重新上电就可以重新加入网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('48', '5', 'ZB01B', 'ZB01B Motion Detector with On/Off Light Switch', '0006', '开关', '3', '1', 'source', 'ZB01B.jpg', 'ZB01B is a switch controller for motion detection. It can detect the movement of object through infrared and control the binding device according to the setting of light threshold.', '2节CR123A电池或12V直流电源', '按住背面小圆点按键3秒以上，直至状态指示灯快速闪烁2次激活设备', '按住背面小圆点（绑定键）的同时给设备上电，状态灯快闪，即恢复出厂设置操作成功。恢复完成后重新上电就可以重新加入网络了。', '可通过调节设备背面的光照灵敏度来设置绑定开关的的光照阈值。');
INSERT INTO `modenodelib_en` VALUES ('49', '5', 'ZB01C', 'ZB01C Motion Detector with On/Off Light Switch and Temperature Sensor', '0006', '开关', '3', '1', 'source', 'ZB01C.jpg', 'ZB01C has infrared detecting alarm, infrared on/off control and temperature reporting, three functions. The function of infrared alarm can detect the movement and send the alert through security center. Infrared on/off control function can detect the infrared generated from the moving objects then control the light switch. Temperature reporting function can detect the current temperature of the environment, and report it regularly to the binding device. ', '2节CR123A电池或12V直流电源', '按住背面小圆点按键3秒以上，直至状态指示灯快速闪烁2次激活设备', '按住背面小圆点（绑定键）的同时给设备上电，状态灯快闪，即恢复出厂设置操作成功。恢复完成后重新上电就可以重新加入网络了。', '可通过调节设备背面的光照灵敏度来设置绑定开关的的光照阈值。');
INSERT INTO `modenodelib_en` VALUES ('50', '5', 'ZB01D', 'ZB01D Occupancy Sensor', null, null, null, '1', null, 'ZB01D.jpg', 'ZB01D is an occupancy sensor. It can detect the object movement which produces infrared and report the status to the binding device. ', '2节CR123A电池或12V直流电源', '按住背面小圆点(绑定键)按键3秒以上，直至状态指示灯快速闪烁2次激活设备', '按住背面小圆点（绑定键）的同时给设备上电，状态灯快闪，即恢复出厂设置操作成功。恢复完成后重新上电就可以重新加入网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('51', '5', 'ZB11A', 'ZB11A Motion Detector', '', '', '', '1', '', 'ZB11A.jpg', 'The infrared detection alarm function of ZB11A can detect the movement of object. And trigger the alarm through the security center (CIE).', '2节ER14505 3.6V AA电池供电', '正对logo，短按右侧按钮指示灯闪5下，即可激活设备', '长按辅助键（正对logo右边）10秒，见到绿色指示灯快闪即可松开按键，设备将进行恢复出厂设置操作，完成后将自动关机。', '无');
INSERT INTO `modenodelib_en` VALUES ('52', '5', 'ZB11B', 'ZB11B Motion Detector with On/Off Light Switch', '0006', '开关', '3', '1', 'source', 'ZB11B.jpg', 'ZB11B is a switch controller for motion detection. It can detect the movement of object through infrared and control the binding device according to the setting of light threshold. ', '2节ER14505 3.6V AA电池供电', '正对logo，短按右侧按钮指示灯闪5下，即可激活设备', '长按辅助键（正对logo右边）10秒，见到绿色指示灯快闪即可松开按键，设备将进行恢复出厂设置操作，完成后将自动关机。', '可通过调节设备背面的光照灵敏度来设置绑定开关的的光照阈值。');
INSERT INTO `modenodelib_en` VALUES ('53', '5', 'ZB11C', 'ZB11C Motion Detector with On/Off Light Switch and Temperature Sensor', '0006', '开关', '3', '1', 'source', 'ZB11C.jpg', 'ZB01C has infrared detecting alarm, infrared on/off control and temperature reporting, three functions. The function of infrared alarm can detect the movement and send the alert through security center. Infrared on/off control function can detect the infrared generated from the moving objects then control the light switch. Temperature reporting function can detect the current temperature of the environment, and report it regularly to the binding device. ', '2节ER14505 3.6V AA电池供电', '正对logo，短按右侧按钮指示灯闪5下，即可激活设备', '长按辅助键（正对logo右边）10秒，见到绿色指示灯快闪即可松开按键，设备将进行恢复出厂设置操作，完成后将自动关机。', '可通过调节设备背面的光照灵敏度来设置绑定开关的的光照阈值。');
INSERT INTO `modenodelib_en` VALUES ('54', '5', 'ZB11D', 'ZB11D Occupancy Sensor', '', '', '', '1', '', 'ZB11D.jpg', 'ZB01D is an occupancy sensor. It can detect the object movement which produces infrared and report the status to the binding device. ', '2节ER14505 3.6V AA电池供电', '正对logo，短按右侧按钮指示灯闪5下，即可激活设备', '长按辅助键（正对logo右边）10秒，见到绿色指示灯快闪即可松开按键，设备将进行恢复出厂设置操作，完成后将自动关机。', '无');
INSERT INTO `modenodelib_en` VALUES ('55', '6', 'Z815N', 'Z815N  AC Curtain Controller', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z815N.jpg', 'Z815N is a curtain controller. It can control the curtains on/off and level through its own key, binding equipments or software. At the same time, the users can check the load current, voltage, power and energy at present. ', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', 'Lin接火线，N线是零线接电机的蓝线. L out1接电机的棕线，L out2接电机的黑线。在设备执行窗帘控制功能前,都应先进行“学习上下限”的学习，学习方法请详见设备说明书或在app的设备管理模块，该设备的设置页面进行学习。');
INSERT INTO `modenodelib_en` VALUES ('56', '6', 'ZD01B', 'ZD01B Toggle & Level Curtain Controller (Drape)', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'ZD01B.jpg', 'ZD01B is a curtain controller. It can control the curtains on/off and level through its own key, binding equipments or software.', '12V4A直流电源', '不需要激活', '按住ON1和OFF1键（靠右边一列）的同时给设备上电，开始执行恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重新上电就可以重新加网了', '在设备执行窗帘控制功能前,都应先进行“学习上下限”的学习，学习方法请详见设备说明书或在app的设备管理模块，该设备的设置页面进行学习');
INSERT INTO `modenodelib_en` VALUES ('57', '7', 'Z302A', 'Z302A Window Intrusion Sensor', null, null, null, '1', null, 'Z302A.jpg', 'Z302A is a magnetic device, used as a detection device in the security systems. When the door or window is opened, it will send alarm message to the security center. When the door or window is closed, it will send out the normal status message to the security center. ', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', 'Z302A的本体与门磁的磁铁部分别安装在门或窗户的缝隙的两边，当门或窗被打开，它将发出报警信息给安防中心设备( 即CIE )，当门或窗被关闭好，它会发送出状态恢复正常的信息给CIE。该设备也可以用于冰箱、抽屉等处，用于检测冰箱或抽屉等是否被打开。需要注意防水。');
INSERT INTO `modenodelib_en` VALUES ('58', '7', 'Z302C', 'Z302C Window Sensor with Glass Break Detector', null, null, null, '1', null, 'Z302C.jpg', 'Z302C is a detection device in the security systems, as an alarm devices when the doors and windows were exceptional be open or the glass are broken. ', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', 'Z302C的本体与门磁的磁铁部分别安装在门或窗户的缝隙的两边，当门或窗被打开，它将发出报警信息给安防中心设备( 即CIE )，当门或窗被关闭好，它会发送出状态恢复正常的信息给CIE。该设备也可以用于冰箱、抽屉等处，用于检测冰箱或抽屉等是否被打开。需要注意防水。');
INSERT INTO `modenodelib_en` VALUES ('59', '7', 'Z302D', 'Z302D Emergency Push Button', null, null, null, '1', null, 'Z302D.jpg', 'Z302D is a alarm triggered device, as a detection device in the security systems. It can be worn on the wrist of a child or the elderly, children or the elderly when in need of emergency assistance in danger, press the alarm button Z302D, Z302D immediately security Center sends out an alarm message.', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('60', '7', 'Z302E', 'Z302E Asset tag', null, null, null, '1', null, 'Z302E.jpg', 'Z-302E is equipped with light and movement sensor to detect light intensity and movements in the environment.  When status is changed, Z-302E sends alarm message to CIE.', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '可将该设备放于贵重的物品或抽屉旁，当物品被移动或光线发生变化时，则设备会将报警信息发送给安防中心设备。');
INSERT INTO `modenodelib_en` VALUES ('61', '7', 'Z302J', 'Z302J Window Intrusion Sensor', '0006', '开关', '3', '1', 'source', 'Z302J.jpg', 'Z-302J is a window/door sensor. When window/door status is changed, it can control its binding device according to the configurations. Z-302J also works as sensor of the security center- When window/door is open, it notifies security center CIE; When window/door is closed, it notifies security center CIE. ', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', 'Z302J的本体与门磁的磁铁部分别安装在门或窗户的缝隙的两边，当门或窗被打开，它将发出报警信息给安防中心设备( 即CIE )，当门或窗被关闭好，它会发送出状态恢复正常的信息给CIE。该设备也可以用于冰箱、抽屉等处，用于检测冰箱或抽屉等是否被打开。需要注意防水。');
INSERT INTO `modenodelib_en` VALUES ('62', '7', 'Z306D', 'Z306D Panic Button and Inductive Charging Base', null, null, null, '1', null, 'Z307.jpg', 'Z-306D acts as an end device in the network as well as the mobile tags of the local network. It can be searched and controlled via third-party software such as Netvox Zig-Butler.', '3V CR2450钮扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('63', '7', 'Z311A', 'Z311A Window Sensor', null, '', '', '1', '', 'Z311A.jpg', 'Z-311A is a window/door sensor and it is also a sensor in the security system- When window/door is closed, it notifies security center CIE. ', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '同时按下绑定键和辅助键，两键同时按着5s，直到看到红色指示灯开始快闪，再松开按键。红色指示灯快闪20次后，设备复位将进入power off模式。', 'Z311A的本体与门磁的磁铁部分别安装在门或窗户的缝隙的两边，当门或窗被打开，它将发出报警信息给安防中心设备( 即CIE )，当门或窗被关闭好，它会发送出状态恢复正常的信息给CIE。该设备也可以用于冰箱、抽屉等处，用于检测冰箱或抽屉等是否被打开。需要注意防水。');
INSERT INTO `modenodelib_en` VALUES ('64', '7', 'Z311C', 'Z311C Window Sensor with Glass Break Detector', null, '', '', '1', '', 'Z311C.jpg', 'Z-311C is a sensor in the security system- When window/door is open or window glass is broken, it will send out alarm messages.', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '同时按下绑定键和辅助键，两键同时按着5s，直到看到红色指示灯开始快闪，再松开按键。红色指示灯快闪20次后，设备复位将进入power off模式。', 'Z311A的本体与门磁的磁铁部分别安装在门或窗户的缝隙的两边，当门或窗被打开，它将发出报警信息给安防中心设备( 即CIE )，当门或窗被关闭好，它会发送出状态恢复正常的信息给CIE。该设备也可以用于冰箱、抽屉等处，用于检测冰箱或抽屉等是否被打开。需要注意防水。');
INSERT INTO `modenodelib_en` VALUES ('65', '7', 'Z312', 'Z312 Door Bell Button', null, null, null, '1', null, 'Z312.jpg', 'Z-312 is a door bell. Bind Z-312 with siren device and the siren will be able to generate door bell sounds.', '3V CR2450纽扣电池', '　　　　当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '同时按下绑定键和辅助键，两键同时按着5s，直到看到红色指示灯开始快闪，再松开按键。红色指示灯快闪20次后，设备复位将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('66', '7', 'Z307', 'Z307 Wireless Rechargeable Fall Sensor', null, null, null, '1', null, 'Z307.jpg', 'Z307 is a fall sensor as well as a sensor in security system. It can be worn on waist to detect fall of elderly people and young children. When fall is detected, it sends alarm message to the security center device.', '充电套件', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('67', '7', 'Z308', 'Z308 Wearable Presence Tag + Panic Button', null, null, null, '1', null, 'Z308.jpg', 'Z-308 is an emergency alarm device. As a sensor of the security system, it can be worn on wrist of elderly people or young children. When elderly people or young children press Z308\'s emergency button, it sends out alarm message to security center.', '3V CR2450纽扣电池', '长按按键3s，红色指示灯闪一下，松开手后，绿色指示灯闪五下。即激活设备。', '长按报警键15秒以上，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯快闪10次后，设备进入关机状态，红色指示灯熄灭。', '无');
INSERT INTO `modenodelib_en` VALUES ('68', '7', 'ZA01A', 'ZA01A Smoke Detector (Chemiresistors)', null, null, null, '1', null, 'ZA01A.jpg', 'Z-A01A is an air pollution sensor used in home environment, and it acts as a security device in the network. When air intensity goes above a specific level , the device will generate buzzer sound and send status change message to CIE.', '12V直流电源或100～240V交流供电', '不需要激活', '同时按住绑定键和测试键5秒以上，设备指示灯开始快速闪烁，表示复位完成。之后将设备重启，就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('69', '7', 'ZA01B', 'ZA01B Gas Detector', '', '', '', '1', '', 'ZA01B.jpg', 'Z-A01B is a gas detector and it acts as a security device in the network. When gas intensity goes above a specific level, it will send warning message to CIE and generate buzzer sound to warn the users.', '12V直流电源或100～240V交流供电', '不需要激活', '同时按住绑定键和测试键5秒以上，设备指示灯开始快速闪烁，表示复位完成。之后将设备重启，就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('70', '7', 'ZA01C', 'ZA01C CO Detector', null, null, null, '1', null, 'ZA01C.jpg', 'Z-A01C is a CO detector and it acts as a security device in the network. When CO intensity goes above a specific level, it will send warning message to CIE and generate buzzer sound to warn the users.', '12V直流电源或100～240V交流供电', '不需要激活', '同时按住绑定键和测试键5秒以上，设备指示灯开始快速闪烁，表示复位完成。之后将设备重启，就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('71', '7', 'ZA01D', 'ZA01D Liquefied Petroleum Gas Detector', null, null, null, '1', null, 'ZA01D.jpg', 'Z-A01D is a Liquefied gas detector and it acts as a security device in the network. When liquefied gas intensity goes above a specific level, it will send warning message to CIE and generate buzzer sound to warn the users.', '12V直流电源或100～240V交流供电', '不需要激活', '同时按住绑定键和测试键5秒以上，设备指示灯开始快速闪烁，表示复位完成。之后将设备重启，就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('72', '7', 'ZA02E', 'ZA02E Smoke Detector with Backup Battery (Photoelectric)', null, null, null, '1', null, 'ZA02E.jpg', 'Z-A02E is a smoke detector. When smoke intensity goes above a specific level, it will send warning message to CIE and generate buzzer sound to warn the users.', '12V直流电源或100～241V交流供电', '不需要激活', '同时按住绑定键和测试键5秒以上，设备指示灯开始快速闪烁，表示复位完成。之后将设备重启，就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('73', '7', 'ZA10', 'ZA10 Gas/Water Keeper', '0006', '开关', '3', '1', 'dest', 'ZA10.jpg', 'Z-A10 can be a gas keep or water keep. When Z-A10 is used as a gas keeper, it will turn off the gas when there are fire detection in the network.', '12V直流电源', '不需要激活', '按住按键的同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复出厂设置完成，重新上电就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('74', '7', 'ZB05', 'ZB05 Electronic Door Lock with Fingerprint Identification', '0101', '开关锁', '3', '1', 'dest', 'ZB05.jpg', 'Z-B05 can be unlocked/locked via mechanical keys, passwords, IC cards, and remote control. Via ZigBee wireless network, users can check and control door lock status at any time. Z-B05 door lock provides reliable password security and fire detection for safety purpose.', '8节AA1.5V电池', '不需要激活', '1.按住网络键的同时给设备上电，上电完请不要立即松开按键，等到网络灯开始快速闪烁，提示复位成功；2.重新上电，ZB05可以开始重新加网。', '1、初次使用时，需要注册zigbee：在门打开的状态下，打开内侧用（主锁身）的电池盖和外盒，在右侧下端按“锁卡注册键”。然后再按下面的“ZB注册键”（靠上的按键）。按“*”键，可听到“哔呖呖”的设置音，设置结束。2、默认都为加密的方式，第一次使用时，需要在app中→设备管理模块→进入门锁的设置页面，设置新的用户和密码，才可以对锁进行开关控制。门锁默认有6个空用户，注册新用户前，需要先将这些用户删除。超级密码默认为：123456.usercode即用户名为1-254的数字。');
INSERT INTO `modenodelib_en` VALUES ('75', '7', 'ZB02E', 'ZB02E Door Bell', null, null, null, '1', null, 'ZB02E.jpg', 'Z-B02E is a door bell. Bind Z-B02E with siren device and the siren will be able to generate door bell sounds.', '2节AAA1.5V电池', '拆下面板的上盖，短按绑定键（红色按键），LED网络灯闪5下激活设备。', '拆下面板的上盖，按住绑定键的同时给设备上电，进行恢复出厂设置，看到状态指示灯闪烁，表示恢复完成', '无');
INSERT INTO `modenodelib_en` VALUES ('76', '7', 'Z311E', 'Z311E Asset tag', '', '', '', '1', '', 'Z311E.jpg', 'Z-311E is equipped with light and movement sensor to detect light intensity and movements in the environment.  When status is changed, Z-311E sends alarm message to CIE.', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '同时按下绑定键和辅助键，两键同时按着5s，直到看到红色指示灯开始快闪，再松开按键。红色指示灯快闪20次后，设备复位将进入power off模式。', '可将该设备放于贵重的物品或抽屉旁，当物品被移动或光线发生变化时，则设备会将报警信息发送给安防中心设备。');
INSERT INTO `modenodelib_en` VALUES ('77', '7', 'Z602A', 'Z602A Siren', null, null, null, '1', null, 'Z602A.jpg', 'Z-602A is a siren device which generate buzzer sound and visible LED indication to report events in the security system.', '12V直流电源和3节1.2V可充电备用电池', '不需要激活', '按住设置键（正对logo右侧按键）的同时给设备上电，状态灯快速闪烁且发出两次门铃报警声，即恢复出厂设置操作成功。恢复完成后重新上电就可以重新加入网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('78', '7', 'Z602B', 'Z602B Siren with GSM Connectivity', null, null, null, '1', null, 'Z602B.jpg', 'Z-602B is a siren device which generate buzzer sound and visible LED indication to report events in security system. When Z-602B generate buzzer sounds, it can also call and text a pre-configured phone number according to different alarm levels for users to receive the alarm message in the real time.', '12V直流电源和3节1.2V可充电备用电池', '不需要激活', '按住设置键（正对logo右侧按键）的同时给设备上电，状态灯快速闪烁且发出两次门铃报警声，即恢复出厂设置操作成功。恢复完成后重新上电就可以重新加入网络了。', '该设备具有GSM功能，可在电池盒内装入SIM卡，在app的设备管理模块，进入该设备的设置页面，可设置电话号码，用于报警时，通过拨打电话和发送短信通知用户。设置的短信号码前，需要加国家代码（如中国：86）');
INSERT INTO `modenodelib_en` VALUES ('79', '7', 'Z601A', 'Z601A Siren', null, null, null, '1', null, 'Z601A.jpg', 'Z-601A is a siren device which generate buzzer sound to report events in the security system.', '12V直流电源', '不需要激活', '按住绑定键的同时给设备上电，状态灯快闪，即恢复出厂设置操作成功。恢复完成后重新上电就可以重新加入网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('80', '8', 'Z821', 'Z821 Multi-Channel Energy Power Meter', null, null, null, '1', null, 'Z821.jpg', 'Z-821 is an indoor multi-channel power meter. It can measure voltage of single channel and the total current of all seven channels, and it can provide power and energy data.', '100～240V的交流电源', '不需要激活', '按下Bind Key按键15秒后（状态灯每5秒钟闪烁1次，共闪烁3次，代表长按了15秒），松开按键，在两秒之内短按Match Key进行网络信息的擦除。网络信息擦除后设备自动重启寻找网络。', '将七路CT端子（用于检测电能）接入到Z821两侧的接口。按照CT端子上指示的K→L方向（电流流动的方向）,将导线扣入CT端子，即可检测该路的电能。');
INSERT INTO `modenodelib_en` VALUES ('81', '9', 'Z501A', 'Z501A 4-Key Remote Controller with Arm/Disarm, On/Off, & Panic Button', '0006:0008', '开关:调级控制', '3', '1', 'source', 'Z501A.jpg', 'As a remote control device, Z501A can be bound with on/off or dimmer devices for users to control the devices wirelessly. Z-501A also comes with an emergency button for users to call for help in emergency.', '3V CR2032纽扣电池', '右侧开关off断电，重新上电后即可激活设备。', '按住“开关键” 的同时给设备上电，开始恢复出厂值，指示灯共闪烁20次，之后重新操作组合键就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('82', '9', 'Z501B', 'Z501B 4-Key Remote Controller with On/Off Switch (2-Set)', '0006', '开关', '3', '1', 'source', 'Z501B.jpg', 'As a remote control device, Z-501B can be bound with on/off devices for users to control the devices wirelessly.', '3V CR2032纽扣电池', '右侧开关off断电，重新上电后即可激活设备。', '按住“ON2键” 的同时给设备上电，开始恢复出厂值，指示灯共闪烁20次，之后重新操作组合键就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('83', '9', 'Z501C', 'Z501C 4-Key Remote Controller with Level Control & On/Off Switch', '0006:0008', '开关:调级控制', '3', '1', 'source', 'Z501C.jpg', 'Z501C can be bound with on/off or dimmer devices for users to control the devices wirelessly.', '3V CR2033纽扣电池', '右侧开关off断电，重新上电后即可激活设备。', '按住“ ON/OFF1键” 的同时给设备上电，开始恢复出厂值，指示灯共闪烁20次，之后重新操作组合键就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('84', '10', 'Z210', 'Z210 Infrared Gateway with 1 External Port', null, null, null, '1', null, 'Z210C.jpg', 'Z-210 is the center of the security system to manage security devices. It can also learn and send IR signals for users to control their home appliances such as TV and DVD player.', '12V直流电源', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '使用红外转接器控制家用电器时，需要在app上导入红外数据，若无红外数据时，则需要通过IR学习器或手机app手动学习。');
INSERT INTO `modenodelib_en` VALUES ('85', '10', 'Z211', 'Z211  Infrared Gateway with 4 External Ports', null, null, null, '1', null, 'Z211.jpg', 'Z-211 is a device for IR learning and IR controlling. After learning IR codes, Z-211 can control home appliances which receives IR signals. Also, Z-211 can control ZigBee devices when it receives IR signals from a regular remote control.', '12V直流电源', '不需要激活', '按住绑定键（蓝色按键），同时给设备上电，开始恢复出厂设置，直到状态指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '使用红外转接器控制家用电器时，需要在app上导入红外数据，若无红外数据时，则需要通过IR学习器或手机app手动学习。');
INSERT INTO `modenodelib_en` VALUES ('86', '11', 'Z203', 'Z203  Cloud Wireless Smart Home Center', null, null, null, '1', null, 'Z203.jpg', 'As the main controller of Netvox Cloud-based Wireless Smart Home System, Z-203 is a perfect combination of cloud services, Wifi technology, and ZigBee Home Automation solutions. Z-203 enables users to monitor their lights, curtains, and home appliances via smart home app and Wifi connection, and users can easily monitor their home with 2G/3G network even they are far away.', '12V直流电源', '不需要激活', '详见203说明书', 'Z203可用12V直流电源供电，给Z203上电后，打开手机的WiFi功能，即可搜索到Z203的无线信号。外网网线接入Z203的WAN口，LAN口可直接接IPcamera或电脑，当家中有多个 IPcamera时，需要增加集线器。');
INSERT INTO `modenodelib_en` VALUES ('87', '11', 'Z103AC', 'Z103AC USB Coordinator', null, null, null, '1', null, 'Z103AC.jpg', 'Z103AC is a coordinator with USB port. The devicce can communicate with other Zigbee modules through the port, and thus control the entire system.', 'USB', '不需要激活', '按住PermitJoin键的同时给Z103设备上电，设备指示灯处于快速闪烁状态，表示恢复出厂设置完成，设备重新上电就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('88', '11', 'Z103AR', 'Z103AR USB Router', null, null, null, '1', null, 'Z103AR.jpg', 'Z103AR is a Router with USB port，which can transmit the information to other devices with the USB port driver.', 'USB', '不需要激活', '按住PermitJoin键的同时给Z103设备上电，设备指示灯处于快速闪烁状态，表示恢复出厂设置完成，设备重新上电就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('89', '11', 'Z201B', 'Z201B Coordinator with Security Center (CIE)', null, null, null, '1', null, 'Z201B.jpg', 'Z201B is a HA Coordinator & CIE. With the USB port driver, the device can communicate with other Zigbee modules through the port and allow the enrolling of other devices, thus to control the entire system;', '12V直流电源', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('90', '11', 'Z201C', 'Z201C ZigBee802.15.4 Coord ', null, null, null, '1', null, 'Z201C.jpg', 'Z201C is HA coordinator with USB port,  With the USB port driver, the device can communicate with other Zigbee modules through the port, thus to control the entire system;', '12V直流电源', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('91', '11', 'Z201R HA', 'Z201R HA ZigBee TCP/IP Gateway', null, null, null, '1', null, 'Z201R.jpg', 'HA Repeater', '12V直流电源', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('92', '11', 'Z201R', 'Z201R ZigBee 802.15.4 Router', null, null, null, '1', null, 'Z201R.jpg', 'Location Reference Node', '12V直流电源', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('93', '4', 'Z812A', 'Z812A Programmable 8-Button Scene Control Keypad', '0006', '开关', '3', '1', 'source', 'Z812A.jpg', 'Z-812A is Multi-port Wireless  In-wall Switch Module. It can bind with the devices carrying the function of on/off switch control and enables wireless on/off control.', '100～240V的交流电源', '不需要激活', '上电以后，同时按住OFF3和OFF4键，按住5秒，网络灯灭，然后放开按键，开始恢复出厂值，直到网络灯开始闪烁，表示恢复完成。之后设备将自动复位，可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('94', '4', 'Z812B', 'Z812B Battery Operated 8-Button Control Keypad', '0006:0008', '开关:调级控制', '3', '1', 'source', 'Z812B.jpg', 'Z-812B is Multi-port Wireless  In-wall Switch Module. It can bind with the devices carrying the function of on/off switch control and enables wireless on/off control.', '100～240V的交流电源', '同时按住第一行两个按键即可激活设备。', '同时按住按键3和按键4达3秒，指示灯闪烁1次提示设备开始恢复出厂设置，恢复成功指示灯快闪10次，不成功指示灯无动作。恢复出厂设置之后设备自动重启。', '无');
INSERT INTO `modenodelib_en` VALUES ('95', '7', 'Z801TXB', 'Z801TXB Sensor Signal Tx Module', '', '', '', '1', '', 'Z801TXB.jpg', 'Z801TXB is a pulse Signal Detector，it can be connected with 5 equipment.if Z801txb detects the signal,it will send the command to the security of the ENROLL control center .', '5V直流电源或2节1.5V电池', '不需要激活', '按住绑定键的同时给设备上电，LED2闪烁20次，表示复位完成，之后重新上电，Z801 TXB可以重新加网。 ', '无');
INSERT INTO `modenodelib_en` VALUES ('96', '0', 'Z801RX', 'Z801RX  RX Relay Board', '0006', '开关', '3', '1', 'dest', 'Z801RX.jpg', 'Z801RX is relay board.it is used to control the switch of the equipment,mainly for controling the switch of household appliance.  ', '2节1.2V电池', '不需要激活', '1.  按住绑定键的同时给设备上电；2.  复位完成则状态指示灯快速灯闪烁；3.  重新上电，Z801RX可以开始重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('100', '11', 'Z508', 'Z508 In Home Display', null, null, null, '1', null, 'Z508.jpg', 'Z508 is a Smart In-Home Display for smart home and household energy monitoring. Z-508 works as Smart Home Control Center and allows users to review current and historical energy consumption data.By through Zig-Butler Smart Scheme, users can select and switch any preferable mode setting. With the implementation of CIE, Z-508 is capable of managing Home Security System and tells security related message on its display.', '5V直流电源或2节1.5V电池', '不需要激活', '详见Z508说明书', '无');
INSERT INTO `modenodelib_en` VALUES ('101', '0', 'Z825A', 'Z825A Touch Panel Switch with Power Meter (1-Gang)', '0006', '开关', '3', '1', 'dest', 'Z825A.jpg', 'Z-825A is One Gang In-Wall Switch with Touch Panel and fits with 86*86 junction box and replaces the existing wall switch. It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-825A by ZigBee Remote Control, Smart Phone, On/Off Switch and so on.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('102', '0', 'Z825B', 'Z825B Touch Panel Switch with Power Meter (2-Gang)', '0006', '开关', '3', '1', 'dest', 'Z825B.jpg', 'Z-825B is Two Gang In-Wall Switch with Touch Panel and fits with 86*86 junction box and replaces the existing wall switch. It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-825A by ZigBee Remote Control, Smart Phone, On/Off Switch and so on.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('103', '0', 'Z825D', 'Z825D Touch Panel Dimmer Switch with Power Meter (1-Gang)', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z825D.jpg', 'Z-825D is One Gang In-Wall Dimmer Switch with Touch Panel and fits with 86*86 junction box and replaces the existing wall switch. It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-825A by ZigBee Remote Control, Smart Phone, On/Off Switch and so on.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('104', '2', 'Z725A', 'Z725A Temperature and Humidity Sensor with Solar Battery (Outdoor)', null, null, null, '1', null, 'Z725A.jpg', 'Z-725A is Wireless H/T Sensor and Ultraviolet Detector with Solar Battery Panel. It comes with water-proof enclosure for protective purpose. It is used to measure and collect Humidity, Temperature and Ultraviolet data in the surrounding and direct to the data collector for display.', '太阳能充电电池', '不需要激活', '设备上电后，同时按住“绑定键”和“设置键” 5秒，开始恢复出厂值，LED快闪10次，表示恢复出厂值完成，设备自动复位寻找加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('106', '11', 'Z725R', 'Z725R Repeater with Solar Battery (Outdoor)', null, null, null, '1', null, 'Z725R.jpg', 'Z-725R is a Outdoor type Wireless Repeater，which is use to expand the network range。 ', '太阳能充电电池', '不需要激活', '设备上电后，同时按住绑定键和辅助键5秒，状态灯闪烁1次后，开始恢复出厂值，LED快闪20次，表示恢复出厂值完成，设备自动复位寻找加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('107', '0', 'Z962A', 'Z962A One Gang Wall Switch with Touch Panel', '0006', '开关', '3', '1', 'dest', 'Z962A.jpg', 'Z-962A is One Gang In-Wall Switch with Touch Panel and fits with 86*86 junction box and replaces the existing wall switch. It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-962A by ZigBee Remote Control, Smart Phone, On/Off Switch and so on.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('108', '0', 'Z962B', 'Z962B Two Gang Wall Switch with Touch Panel', '0006', '开关', '3', '1', 'dest', 'Z962B.jpg', 'Z-962B is Two Gang In-Wall Switch with Touch Panel and fits with 86*86 junction box and replaces the existing wall switch. It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-962B by ZigBee Remote Control, Smart Phone, On/Off Switch and so on.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('109', '0', 'Z962C', 'Z962C Three Gang Wall Switch with Touch Panel', '0006', '开关', '3', '1', 'dest', 'Z962C.jpg', 'Z-962C is Three Gang In-Wall Switch with Touch Panel and fits with 86*86 junction box and replaces the existing wall switch. It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-962C by ZigBee Remote Control, Smart Phone, On/Off Switch and so on.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('110', '0', 'Z962D', 'Z962D Scene or Mode Selector 1 key with Touch Panel', null, null, null, '1', null, 'Z962D.jpg', 'Z-962D is Scene and Mode control keypad, by through 1 touch keypad, users can add scene/mode, keep scene storage and manage mode control to carry out memorizing 1 scence control. It also supports Netvox mode control functionality.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib_en` VALUES ('111', '0', 'Z962E', 'Z962E Scene or Mode Selector 2 keys with Touch Panel', null, null, null, '1', null, 'Z962E.jpg', 'Z-962E is Scene and Mode control keypad, by through 2 touch keypad, users can add scene/mode, keep scene storage and manage mode control to carry out memorizing 2 scence control. It also supports Netvox mode control functionality.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib_en` VALUES ('112', '0', 'Z962F', 'Z962F Scene or Mode Selector 3 keys with Touch Panel', null, null, null, '1', null, 'Z962F.jpg', 'Z-962F is Scene and Mode control keypad, by through 3 touch keypad, users can add scene/mode, keep scene storage and manage mode control to carry out memorizing 3 scence control. It also supports Netvox mode control functionality.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib_en` VALUES ('113', '0', 'Z962G', 'Z962G One Scene or Mode Selector And One Switch with Touch Panel', '0006', '开关', '3', '1', 'dest', 'Z962G.jpg', 'Z962G is One Gang Touch Switch and 1 Scence Control Touch Button and fits with 86*86 junction box and replaces the existing wall switch. It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-962G by ZigBee Remote Control, Smart Phone, On/Off Switch and so on. Users can manage scence/mode control with the secnce control button.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib_en` VALUES ('114', '0', 'Z962H', 'Z962H Two Scene or Mode Selector And One Switch with Touch Panel', '0006', '开关', '3', '1', 'dest', 'Z962H.jpg', 'Z962H is One Gang Touch Switch and 2 Scence Control Touch Button and fits with 86*86 junction box and replaces the existing wall switch. It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-825A by ZigBee Remote Control, Smart Phone, On/Off Switch and so on. Users can manage scence/mode control with the secnce control button.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib_en` VALUES ('115', '0', 'Z962I', 'Z962I One Scene or Mode Selector And Two Switch with Touch Panel', '0006', '开关', '3', '1', 'dest', 'Z962I.jpg', 'Z962I is Two Gang Touch Switch and 1 Scence Control Touch Button and fits with 86*86 junction box and replaces the existing wall switch. It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-825A by ZigBee Remote Control, Smart Phone, On/Off Switch and so on. Users can manage scence/mode control with the secnce control button.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib_en` VALUES ('116', '7', 'Z801WLS', 'Z801WLS Water Sensor', '', '', '', '1', '', 'Z801WLS.jpg', 'Z-801WLS is a  a water Sensor,which is used for monitoring leakage situation, and send the alarm information to the security center immediately.', '5V直流电源或2节1.5V电池', '不需要激活', '按住绑定键的同时给设备上电，LED2闪烁20次，表示复位完成，之后重新上电，Z801WLS可以重新加网。 ', '无');
INSERT INTO `modenodelib_en` VALUES ('117', '7', 'ZB05A', 'ZB05A Door Lock with Fingerprint Identification', '0101', '开关锁', '3', '1', 'dest', 'ZB05A.jpg', 'Z-B05A can control lock/unlock of the door that applied to it including mechanical key, access code, IC card, finger print, remote access ect.. By through ZigBee wireless networking, users can monitor and control the door status, manage user ID and passwork anywhere you are. Intellectualized door lock design supports reliable access code safeguard to guarantee home surveillance safety.', '8节AA1.5V电池', '不需要激活', '1.按住网络键的同时给设备上电，上电完请不要立即松开按键，等到网络灯开始快速闪烁，提示复位成功；2.重新上电，ZB05A可以开始重新加网。', '1、初次使用时，需要注册zigbee：在门打开的状态下，打开内侧用（主锁身）的电池盖和外盒，在右侧下端按“锁卡注册键”。然后再按下面的“ZB注册键”（靠上的按键）。按“*”键，可听到“哔呖呖”的设置音，设置结束。2、默认都为加密的方式，第一次使用时，需要在app中→设备管理模块→进入门锁的设置页面，设置新的用户和密码，才可以对锁进行开关控制。门锁默认有6个空用户，注册新用户前，需要先将这些用户删除。超级密码默认为：123456.usercode即用户名为1-254的数字。');
INSERT INTO `modenodelib_en` VALUES ('118', '4', 'Z801TX', 'Z801TX TX Switch Board', '0006', '开关', '3', '1', 'source', 'Z801TX.jpg', 'Z801TX is used as a end device in the zigBee network,which doesn\'t allow other devices to be its child device.It can be extended with five buttons. When the button is trigged，it can send commands to bound device and control its on/off at the detection of a signal.', '无', '无', '无', '无');
INSERT INTO `modenodelib_en` VALUES ('119', '7', 'Z311J', 'Z311J Window Sensor with On/Off Switch', '0006', '开关', '3', '1', 'source', 'Z311J.jpg', 'Z-311J is a window/door sensor. When window/door status is changed, it can control its bound device according to the configurations. Z-302J also works as a detection sensor of the security system- when window/door is open or closed, it also notifies security center with the alarm messages, while sends the information of normal status to CIE when the window/door is closed.', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', 'Z311J的本体与门磁的磁铁部分别安装在门或窗户的缝隙的两边，当门或窗被打开，它将发出报警信息给安防中心设备( 即CIE )，当门或窗被关闭好，它会发送出状态恢复正常的信息给CIE。该设备也可以用于冰箱、抽屉等处，用于检测冰箱或抽屉等是否被打开。需要注意防水。');
INSERT INTO `modenodelib_en` VALUES ('120', '0', 'ZC07', 'ZC07 Dimmable LED Bulb', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'ZC07.jpg', 'ZC07 is a robust ZigBee enabled dimmable LED bulb. It can achieve 256 dimmer light levels and can be controlled wirelessly. By methods such as binding devices, wireless remote control or software operation, it can control light on/off and dimmer. As a 5W bulb, ZC07 applies constant current control mode withthe input voltage from AC 100V to 240V. The inputs of 100V to 240V can achieve the same lighting effects.', '100～240V的交流电源', '不需要激活', '同时长按测试键和绑定键5s，5s时间到后指示灯闪10次后就恢复出厂设置成功，此时就可以重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('121', '7', 'Z311W', 'Z311W Water Sensor', null, null, null, '1', '', 'Z311W.jpg', 'Z311W is a water sensor,which is used for monitoring leakage situation and sending the alarm information to the security center immediately.', '无需外加电源,产品使用内部的3V钮扣电池供电。', '同时按住绑定键和辅助键，看到红色指示灯灯闪烁2次，此时松开按键。', '同时按住绑定键和辅助键，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('122', '6', 'Z811B', 'Z811B Curtain Controller (2- Gang)', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z811B.jpg', 'Z811B is aTwo Gang curtain controller. It can control the curtains on/off and level through its own key, bound devices or software. ', '100-240V AC 50/60HZ电源供电', '不需要激活', '方法一：按住“绑定键（logo旁的小按钮）”上电，指示灯快闪，表示恢复出厂设置完成，再次重新上电后即可。方法二：长按住“绑定键（logo旁的小按钮）”键的15秒待指示灯闪烁一次（此间指示灯3秒、10秒、15秒各闪烁一次），松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，自动重启就可以重新加网了。', 'Lin接火线，N线是零线接电机的零线（一般为蓝线）。EP1：Lout1接电机正转输入（一般为棕线)，Lout2接电机反转输入（一般为黑线）。EP2：Lout3接电机正转输入（一般为棕线)，Lout4接电机反转输入（一般为黑线）。在设备执行窗帘控制功能前,都应先进行“学习上下限”的学习，学习方法请详见设备说明书或在app的设备管理模块，该设备的设置页面进行学习。');
INSERT INTO `modenodelib_en` VALUES ('123', '0', 'Z825I', 'Z825I Touch Panel Programmable Scene Selector (6-Gang)', '', '', '', '1', '', 'Z825I.jpg', 'As a Scene Selector & Mode Selector，Z825I is equipped with six touch buttons. Each can be configured to add scene or control mode (each button can at most at one scene or configure four modes.)', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib_en` VALUES ('124', '0', 'Z825J', 'Z825J Touch Panel 3-Gang Switch with Power Meter and 3-Gang Programmable Scene Selector', '0006', '开关', '3', '1', 'dest', 'Z825J.jpg', 'Z825J is a three-gang touch switch and three-gang Scene Selector,which can be directly installed in 86 junction box to replace the normal wall switch.It monitors and calculates Current, Voltage, Power and Engery. Users can control Z-825J by ZigBee Remote Control, Smart Phone, On/Off Switch and so on. Users can manage scence/mode control with the secnce control button.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib_en` VALUES ('125', '0', 'Z825Q', 'Z825QTouch Panel Switch with Power Meter (3-Gang)', '0006', '开关', '3', '1', 'dest', 'Z825Q.jpg', 'Z825Q is three-gang local switch controller with single livewire for power. It can control its on/off through being bound with  devices with on/off function to control it or through its own keys. Its three-gang on/off is controlled by three keys.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络青绿色指示灯闪烁一次（期间网络青绿色指示灯闪烁4次，其中刚按下闪一次，3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络青绿色指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会进入睡眠模式，短按绑定键Z825Q可重新加网。 ', 'Z825Q具有单路输入，三路输出的能力，接线时，将一种线（N或L，来自市电）接入输入口，然后在三路输出口引出同种类型的输出线（给设备供电），将插头接入到AC  100-240V的电源，给设备上电。');
INSERT INTO `modenodelib_en` VALUES ('126', '1', 'Z816I', 'Z816I  China Type Wall Socket with Power Meter (5-pin)', '0006', '开关', '3', '1', 'dest', 'Z816I.jpg', 'Z816I is a China type wireless current-detection smart wall socket, which can be directly installed in 86 junction box to replace the traditional wall socket. Its on/off can be controlled with its own keys or through its bound devices and software.Via software, users are able to monitor the load current, voltage and power and kilo-watt-hour consumption of the attached appliance.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('127', '5', 'Z817D', 'Z817D  Ceiling Motion Detector', '0006', '开关', '3', '1', 'source', 'Z817D.jpg', 'Z817D has the on/off switch by the infrared detection.When it detects the IR of moving objects,it can control on/off of bound devices.', 'AC100-240V 50/60HZ供电', '不需要激活', '给设备上电，同时按住“按键1”和“按键2”5秒；复位完成则led闪烁30次后自动重新请求加网；', '无');
INSERT INTO `modenodelib_en` VALUES ('128', '11', 'Z800R', 'Z800R Plug Repeater with Outlet', null, null, null, '1', null, 'Z800R.jpg', 'Z800R is a Router Repeaters with a socket,which allow other devices as his children.It can extend the communication distance for other devices.', 'AC 100-250V的电源', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重新上电就可以加入新的网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('129', '1', 'Z809C', 'Z809C Plug Repeater with Backup Battery', null, null, null, '1', null, 'Z809C.jpg', 'Z809C is a Router Repeaters with a socket,which allow other devices as his children.It can extend the communication distance for other devices.', 'AC 100-240V的电源或电池供电（内置电池）', '不需要激活', '长按绑定键15s，15s时间到状态闪1次后放开绑定键，两秒内短按绑定键就状态灯闪烁10次，并自动复位。其中长按绑定键3s，10s和15s的时候状态灯都会依次闪烁一次，以提示所按的时间长短。', '无');
INSERT INTO `modenodelib_en` VALUES ('130', '1', 'Z809D', 'Z809D Plug Repeater with Power Amplifier & Backup Battery', '', '', '', '1', '', 'Z809D.jpg', 'Z809D is a Router Repeaters with a socket,which allow other devices as his children.It can extend the communication distance for other devices.', 'AC 100-240V的电源或电池供电（内置电池）', '不需要激活', '长按绑定键15s，15s时间到指示闪1次后放开绑定键，两秒内短按绑定键就指示灯闪烁10次，并自动重启设备（如果仅仅用电池供电不能重启）。', '无');
INSERT INTO `modenodelib_en` VALUES ('131', '9', 'Z501AE3ED', 'Z501A 4-Key Remote Controller with Arm/Disarm, On/Off, & Panic Button', '0006:0008', '开关:调级控制', '3', '1', 'source', 'Z501A.jpg', 'As a remote control device, Z501A can be bound with on/off switch or dimmer devices for users to control the devices wirelessly. Z-501A also comes with an emergency button for users to call for help in emergency.', '3V CR2032纽扣电池', '右侧开关off断电，重新上电后即可激活设备。', '按住“开关键” 的同时给设备上电，开始恢复出厂值，指示灯共闪烁20次，之后重新操作组合键就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('132', '9', 'Z501BE3ED', 'Z501B 4-Key Remote Controller with On/Off Switch (2-Set)', '0006', '开关', '3', '1', 'source', 'Z501B.jpg', 'Z510B is a remote control,which can be bound to control on/off of other devices.', '3V CR2032纽扣电池', '右侧开关off断电，重新上电后即可激活设备。', '按住“ON2键” 的同时给设备上电，开始恢复出厂值，指示灯共闪烁20次，之后重新操作组合键就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('133', '9', 'Z501CE3ED', 'Z501C 4-Key Remote Controller with Level Control & On/Off Switch', '0006:0008', '开关:调级控制', '3', '1', 'source', 'Z501C.jpg', 'As a remote control device, Z501C can be bound with on/off or dimmer devices for users to control the devices wirelessly. ', '3V CR2033纽扣电池', '右侧开关off断电，重新上电后即可激活设备。', '按住“ ON/OFF1键” 的同时给设备上电，开始恢复出厂值，指示灯共闪烁20次，之后重新操作组合键就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('134', '7', 'ZB02I', 'ZB02I  Emergency Push Button', '', '', '', '1', '', 'ZB02E.jpg', 'ZB02I is an emergency push button working as a detection device in the security system. It can be put on the wall or anyplace in the room .When users in danger are in need of emergency assistance, users can press the alarm button for ZB02I to immediately send out an alarm message to the security center, which will notify the siren to make sound or light to alert family members to offer immediate help. ', 'AAA电池供电', '短按绑定键，若设备仍在网络状态，则绿色指示灯闪烁5次，表示激活成功。', '按住绑定键的同时给设备上电，进行恢复出厂设置，看到指示灯闪烁，表示恢复完成。', '无');
INSERT INTO `modenodelib_en` VALUES ('137', '0', 'Z802', 'Z802 Two Way Switching Load module', '0006', '开关', '3', '1', 'dest', 'Z802.jpg', 'Z802 can be bound with on/off switch for users to control the devices wirelessly.User can switch on/off the appliances attached to it through mechnical switch or wirelessly. Users may also use ZiG-BUTLER to check the current status of the device and make corresponding control.', '100-240VAC 50/60HZ 电源供电', '不需要激活', '按住绑定键的同时，给设备上电，开始恢复出厂值，直到LED1指示灯开始闪烁，表示恢复完成。之后重新上电，Z802可以重新加网了', '无');
INSERT INTO `modenodelib_en` VALUES ('138', '4', 'ZB02J', 'ZB02J Wireless Scene & Mode Selector (3-Key)', '', '', '', '1', '', 'ZB02C.jpg', 'ZB02J is a three-gang scene button, which can be bound with three scenes for users to control them.', '无需外加电源,产品使用内部的2节7号电池供电', '短按绑定键（led网络灯闪5下）', '按住绑定键不松开，装入电池，进行恢复出厂设置，看到状态灯闪烁，表示恢复完成，然后松开按键，拿出电池', '无');
INSERT INTO `modenodelib_en` VALUES ('139', '0', 'Z815A', 'Z815A Wall Switch with Power Meter (1-Gang)', '0006', '开关', '3', '1', 'dest', 'Z815I.jpg', 'Z815A is a one-gang Output switch,which can be directly installed in 86 junction box to replace the existing wall switch.It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-815A by ZigBee Remote Control, Smart Phone, On/Off Switch and other methods.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('140', '0', 'Z815B', 'Z815B Wall Switch with Power Meter (2-Gang)', '0006', '开关', '3', '1', 'dest', 'Z815J.jpg', 'Z815B is a two-gang Output switch,which can be directly installed in 86 junction box to replace the existing wall switch.It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-815B by ZigBee Remote Control, Smart Phone, On/Off Switch and other methods.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('141', '0', 'Z815C', 'Z815C Wall Switch with Power Meter (3-Gang)', '0006', '开关', '3', '1', 'dest', 'Z815K.jpg', 'Z815C is a three-gang Output switch,which can be directly installed in 86 junction box to replace the existing wall switch.It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-815C by ZigBee Remote Control, Smart Phone, On/Off Switch and other methods.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('142', '0', 'Z815D', 'Z815D  Wall Dimmer Switch with Power Meter (1-Gang)', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z815L.jpg', 'Z815D is a one-gang Output dimmer which can control on/off and dimmer and be directly installed in 86 junction box to replace the existing wall switch.It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-815D by ZigBee Remote Control, Smart Phone, On/Off Switch and other methods.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('143', '0', 'Z815E', 'Z815E Wall Dimmer Switch with Power Meter (2-Gang)', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z815M.jpg', 'Z815E is a two-gang Output dimmer which can control on/off and dimmer and be directly installed in 86 junction box to replace the existing wall switch.It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-815D by ZigBee Remote Control, Smart Phone, On/Off Switch and other methods.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('144', '0', 'Z826C', 'Z826C Touch Panel Switch with Power Meter (3-Gang+1 Live Wire Power Supply)', '0006', '开关', '3', '1', 'dest', 'Z825Q.jpg', 'Z826C is three-gang local switch controller with single livewire for power. It can control its on/off through being bound with devices with on/off function to control it or through its own keys. Its three-gang on/off is controlled by three keys.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络青绿色指示灯闪烁一次（期间网络青绿色指示灯闪烁4次，其中刚按下闪一次，3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络青绿色指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会进入睡眠模式，短按绑定键Z826C可重新加网。 ', 'Z826C具有单路输入，三路输出的能力，接线时，将一种线（N或L，来自市电）接入输入口，然后在三路输出口引出同种类型的输出线（给设备供电），将插头接入到AC  100-240V的电源，给设备上电。');
INSERT INTO `modenodelib_en` VALUES ('145', '11', 'Z206', 'Z206 Cloud-Based Wireless Smart Home Controller', '', '', '', '1', '', 'Z206.jpg', 'As the main controller of Netvox Cloud-based Wireless Smart Home System, Z-206 is a perfect combination of cloud services, Wifi technology, and ZigBee Home Automation solutions. Z-206 enables users to monitor their lights, curtains, and home appliances via smart home app and Wifi connection, and users can easily monitor their home with 2G/3G network even they are far away.', '使用DC 12V  1.5A电源适配器，接入100-220V的电源', '不需要激活', '详见206说明书', 'Z206作为奈伯思智能家居系统的核心网关，率先实现云端技术、WiFi移动联网技术与ZigBee智能家居物联网术的完美结合，让用户只需要通过手机APP连接Wifi即可控制家里的灯光、窗帘、各种电器等设备的开关，出门在外通过2G/3G网络可监控家中的一切变化。');

/*新建serverlib 服务器列表 2014.12.10*/
CREATE TABLE `serverlib` (
`id`  int(20) NOT NULL AUTO_INCREMENT ,
`servername`  varchar(100) NULL ,
`serverip`  varchar(50) NULL ,
`maxcshc`  int(100) NULL ,
`serverline`  varchar(100) NULL ,
`warnnum`  int(100) NULL ,
`warnmail`  varchar(200) NULL ,
`serverdescription`  varchar(200) NULL ,
PRIMARY KEY (`id`)
)
;

/*创建英文版ep名称表*/
CREATE TABLE `modeeplib_en` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nodeId` bigint(20) DEFAULT NULL,
  `deviceId` varchar(20) DEFAULT NULL,
  `internelModelId` varchar(50) DEFAULT NULL,
  `deviceName` varchar(200) DEFAULT NULL,
  `clusterId` varchar(200) DEFAULT NULL,
  `clusterName` varchar(100) DEFAULT NULL,
  `picName` varchar(100) DEFAULT NULL,
  `destType` varchar(4) DEFAULT NULL,
  `deviceType` varchar(20) DEFAULT NULL,
  `deviceTypeV2` bigint(20) DEFAULT '1',
  `ep` varchar(50) DEFAULT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `Groupable` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `nodeId` (`nodeId`) USING BTREE,
  KEY `deviceId` (`deviceId`) USING BTREE,
  KEY `internelModelId` (`internelModelId`) USING BTREE,
  KEY `deviceName` (`deviceName`) USING BTREE,
  KEY `destType` (`destType`) USING BTREE,
  KEY `deviceType` (`deviceType`) USING BTREE,
  KEY `clusterId` (`clusterId`) USING BTREE,
  KEY `Groupable` (`Groupable`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=242 DEFAULT CHARSET=utf8;

/*添加英文版ep名称*/
INSERT INTO `modeeplib_en` VALUES ('2', '2', '0009', 'Z815I-1', 'Z815I Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('3', '3', '0009', 'Z815K-1', 'Z815K 1st Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('4', '3', '0009', 'Z815K-2', 'Z815K 2nd Power Switch', '0006', '开关', null, '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('5', '3', '0009', 'Z815K-3', 'Z815K 3rd Power Switch', '0006', '开关', null, '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib_en` VALUES ('6', '4', '0100', 'Z806-1', 'Z806 1st Output', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('7', '4', '0100', 'Z806-2', 'Z806 2nd Output', '0006', '开关', null, '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('8', '5', '0000', 'ZB02A-1', 'ZB02A Wall Switch', '0006', '开关', null, '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('10', '7', '0000', 'Z503-2', 'Z503 Switches', '0006:0008', '开关:调级控制', null, '1', 'source', '1', '0F', '', '0');
INSERT INTO `modeeplib_en` VALUES ('11', '7', '0006', 'Z503-1', 'Z503 security Keypads', '', '', null, '3', '', '1', '0A', '', '0');
INSERT INTO `modeeplib_en` VALUES ('12', '10', '0009', 'Z805A-1', 'Z805A Power Switch', '0006', '开关', null, '3', 'dest', '1', '0A', null, '1');
INSERT INTO `modeeplib_en` VALUES ('13', '11', '0009', 'Z805B-1', 'Z805B Power Switch', '0006', '开关', '', '3', 'dest', '1', '0A', '', '1');
INSERT INTO `modeeplib_en` VALUES ('14', '12', '0009', 'Z810B-1', 'Z810B Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('15', '13', '0100', 'Z811-1', 'Z811 1st Output', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('16', '13', '0100', 'Z811-2', 'Z811 2nd Output', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('17', '13', '0100', 'Z811-3', 'Z811 3rd Output', '0006', '开关', '', '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib_en` VALUES ('18', '13', '0100', 'Z811-4', 'Z811 4th Output', '0006', '开关', '', '3', 'dest', '1', '04', '', '1');
INSERT INTO `modeeplib_en` VALUES ('19', '14', '0009', 'Z815J-1', 'Z815J 1st Power Switch', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('20', '14', '0009', 'Z815J-2', 'Z815J 2nd Power Switch', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('21', '15', '0101', 'Z815L-1', 'Z815L Dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('22', '16', '0101', 'Z815M-1', 'Z815M 1st Dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('23', '16', '0101', 'Z815M-2', 'Z815M 2nd Dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('24', '17', '0009', 'Z817A-1', 'Z817A Power Switch', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('25', '18', '0101', 'Z817B-1', 'Z817B Dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('26', '19', '0002', 'Z817C-1', 'Z817C Power Switch', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('27', '20', '0009', 'Z825C-1', 'Z825C 1st Power Switch', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('28', '20', '0009', 'Z825C-2', 'Z825C 2nd Power Switch', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('29', '20', '0009', 'Z825C-3', 'Z825C 3rd Power Switch', '0006', '开关', '', '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib_en` VALUES ('30', '21', '0101', 'Z825E-1', 'Z825E 1st Dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('31', '21', '0101', 'Z825E-2', 'Z825E 2nd Dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('32', '22', '0101', 'ZC06-1', 'ZC06 Dimmer light', '0006:0008', '开关:调级控制', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('33', '23', '0009', 'Z800-1', 'Z800 Power Plug', '0006', '开关', '', '3', 'dest', '1', '0A', '', '1');
INSERT INTO `modeeplib_en` VALUES ('34', '24', '0002', 'Z803-1', 'Z803 1st Power Plug', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('35', '24', '0002', 'Z803-2', 'Z803 2nd Power Plug', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('36', '24', '0002', 'Z803-3', 'Z803 3rd Power Plug', '0006', '开关', '', '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib_en` VALUES ('37', '24', '0002', 'Z803-4', 'Z803 4th Power Plug', '0006', '开关', null, '3', 'dest', '1', '04', null, '1');
INSERT INTO `modeeplib_en` VALUES ('38', '24', '000D', 'Z803-5', 'Z803 Power Consumption Awareness', '0006', '开关', null, '3', '', '1', '05', null, '0');
INSERT INTO `modeeplib_en` VALUES ('39', '25', '0009', 'Z808A-1', 'Z808A Power Plug', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('40', '26', '0101', 'Z808B-1', 'Z808B Dimmable Power Plug', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('41', '27', '0009', 'Z809A-1', 'Z809A Power Plug', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('42', '28', '0101', 'Z809B-1', 'Z809B Dimmable Power Plug', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('43', '29', '0009', 'Z816B-1', 'Z816B US type Power Plug', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('44', '30', '0009', 'Z816G-1', 'Z816G UK type Power Plug', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('45', '31', '0009', 'Z816H-1', 'Z816H China type Power Plug', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('46', '32', '0302', 'Z711-1', 'Z711 Temperature and Humidity Sensor', null, null, null, null, null, '1', '0A', null, '0');
INSERT INTO `modeeplib_en` VALUES ('47', '33', '0302', 'Z712-1', 'Z712 Temperature and Humidity Sensor', '', '', '', '', '', '1', '0A', '', '0');
INSERT INTO `modeeplib_en` VALUES ('48', '34', '0302', 'Z713-1', 'Z713 Temperature and Humidity Sensor', '', '', null, '', '', '1', '0A', null, '0');
INSERT INTO `modeeplib_en` VALUES ('49', '35', '0302', 'Z716A-1', 'Z716A Temperature and Humidity Sensor', '', '', '', '', '', '1', '0A', '', '0');
INSERT INTO `modeeplib_en` VALUES ('50', '36', '0302', 'Z716B-1', 'Z716B Temperature Sensor', null, null, '', null, null, '1', '0A', '', '0');
INSERT INTO `modeeplib_en` VALUES ('51', '37', '0103', 'Z302B-1', 'Z302B Light Switch', '0006', '开关', null, '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('52', '38', '0104', 'Z302H-1', 'Z302H Light Sensor ', '0006:0008', '开关:调级控制', null, '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('53', '39', '0106', 'Z302G-1', 'Z302G Dimmer Switch', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('54', '40', '0103', 'Z311B-1', 'Z311B Light Switch', '0006', '开关', null, '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('55', '41', '0104', 'Z311H-1', 'Z311H Light Sensor ', '0006:0008', '开关:调级控制', null, '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('56', '42', '0106', 'Z311G-1', 'Z311G Dimmer Switch', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('57', '44', '0000', 'ZB02B-1', 'ZB02B Wall Switch 1st key', '0006', '开关', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('58', '44', '0000', 'ZB02B-2', 'ZB02B Wall Switch 2nd key', '0006', '开关', '', '3', 'source', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('59', '45', '0000', 'ZB02C-1', 'ZB02C Wall Switch 1st key', '0006', '开关', '', '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('60', '45', '0000', 'ZB02C-2', 'ZB02C Wall Switch 2nd key', '0006', '开关', '', '3', 'source', '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('61', '45', '0000', 'ZB02C-3', 'ZB02C Wall Switch 3rd key', '0006', '开关', '', '3', 'source', '1', '03', '', '0');
INSERT INTO `modeeplib_en` VALUES ('62', '46', '0104', 'ZB02F-1', 'ZB02F Wall Dimmer', '0006:0008', '开关:调级控制', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('63', '48', '0000', 'ZB01B-1', 'ZB01B On Off Switch', '0006', '开关', null, '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('64', '49', '0402', 'ZB01C-1', 'ZB01C Motion Detector', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('65', '52', '0000', 'ZB11B-1', 'ZB11B On Off Switch', '0006', '开关', null, '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('66', '53', '0402', 'ZB11C-1', 'ZB11C Motion Detector', null, null, '', null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('67', '55', '0200', 'Z815N-1', 'Z815N Shade Control Switch', '0006:0008', '开关:调级控制', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('68', '56', '0200', 'ZD01B-1', 'ZD01B First Curtain Controller', '0006:0008', '开关:调级控制', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('69', '56', '0200', 'ZD01B-2', 'ZD01B Second Curtain Controller', '0006:0008', '开关:调级控制', null, '3', 'dest', '1', '02', null, '1');
INSERT INTO `modeeplib_en` VALUES ('71', '47', '0402', 'ZB01A-1', 'ZB01A Motion Detector', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('72', '50', '0107', 'ZB01D-1', 'ZB01D Occupancy Sensor', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('73', '51', '0402', 'ZB11A-1', 'ZB11A Motion Detector', '', '', null, '', '', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('74', '49', '0000', 'ZB01C-2', 'ZB01C On Off Switch', '0006', '开关', null, '3', 'source', '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('75', '49', '0302', 'ZB01C-3', 'ZB01C Temperature Sensor', null, null, null, null, null, '1', '03', null, '0');
INSERT INTO `modeeplib_en` VALUES ('76', '53', '0000', 'ZB11C-2', 'ZB11C On Off Switch', '0006', '开关', null, '3', 'source', '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('77', '53', '0302', 'ZB11C-3', 'ZB11C Temperature Sensor', null, null, '', null, null, '1', '03', null, '0');
INSERT INTO `modeeplib_en` VALUES ('78', '54', '0107', 'ZB11D-1', 'ZB11D Occupancy Sensor', '', '', null, '', '', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('79', '57', '0402', 'Z302A-1', 'Z302A Window Intrusion Sensor', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('80', '58', '0402', 'Z302C-1', 'Z302C Window Glass Break Sensor and Intrusion Sensor', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('81', '59', '0402', 'Z302D-1', 'Z302D Panic Button', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('82', '60', '0402', 'Z302E-1', 'Z302E Asset tag', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('83', '61', '0000', 'Z302J-1', 'Z302J on off switch', '0006', '开关', '', '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('84', '61', '0402', 'Z302J-2', 'Z302J Window Intrusion Sensor', '', '', '', '', '', '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('85', '62', '0402', 'Z306D-1', 'Z306D Panic Button and Inductive Charging Base', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('86', '63', '0402', 'Z311A-1', 'Z311AWindow Intrusion Sensor', null, '', '', '', '', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('87', '64', '0402', 'Z311C-1', 'Z311C Window Glass Break Sensor and Intrusion Sensor', null, '', '', '', '', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('88', '65', '0401', 'Z312-1', 'Z312 Door Bell Button', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('89', '66', '0402', 'Z307-1', 'Z307 Fall Sensor and Inductive Charging Base', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('90', '67', '0402', 'Z308-1', 'Z308 Panic Button', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('91', '68', '0402', 'ZA01A-1', 'ZA01A Smoke Detector with Heat Sensor', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('92', '69', '0402', 'ZA01B-1', 'ZA01B Gas Detector with Heat Sensor', '', '', null, '', '', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('93', '70', '0402', 'ZA01C-1', 'ZA01C CO Detector', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('94', '71', '0402', 'ZA01D-1', 'ZA01D Liquefied Gas Detector with Heat Sensor', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('95', '72', '0402', 'ZA02E-1', 'ZA02E Photoelectric Smoke Detector with Heat Sensor', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('96', '73', '0002', 'ZA10-1', 'ZA10 Gas or Water Keeper', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('97', '74', '000A', 'ZB05-1', 'ZB05 Door Lock', '0101', '开关锁', null, '3', 'dest', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('98', '75', '0401', 'ZB02E-1', 'ZB02E Door Bell', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('99', '76', '0402', 'Z311E-1', 'Z311E Asset tag', '', '', null, '', '', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('100', '77', '0403', 'Z602A-1', 'Z602A Siren', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('101', '78', '0403', 'Z602B-1', 'Z602B Siren with GSM', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('102', '79', '0403', 'Z601A-1', 'Z601A Siren', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('103', '80', '000D', 'Z821-1', 'Z821 1st Power Consumption Awareness', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('104', '80', '000D', 'Z821-2', 'Z821 2nd Power Consumption Awareness', null, null, null, null, null, '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('105', '80', '000D', 'Z821-3', 'Z821 3rd Power Consumption Awareness', null, null, null, null, null, '1', '03', null, '0');
INSERT INTO `modeeplib_en` VALUES ('106', '80', '000D', 'Z821-4', 'Z821 4th Power Consumption Awareness', null, null, null, null, null, '1', '04', null, '0');
INSERT INTO `modeeplib_en` VALUES ('107', '80', '000D', 'Z821-5', 'Z821 5th Power Consumption Awareness', null, null, null, null, null, '1', '05', null, '0');
INSERT INTO `modeeplib_en` VALUES ('108', '80', '000D', 'Z821-6', 'Z821 6th Power Consumption Awareness', null, null, null, null, null, '1', '06', null, '0');
INSERT INTO `modeeplib_en` VALUES ('109', '80', '000D', 'Z821-7', 'Z821 7th Power Consumption Awareness', null, null, null, null, null, '1', '07', null, '0');
INSERT INTO `modeeplib_en` VALUES ('110', '80', '000D', 'Z821-8', 'Z821 All Power Consumption Awareness', null, null, null, null, null, '1', '08', null, '0');
INSERT INTO `modeeplib_en` VALUES ('111', '81', '0001', 'Z501A-1', 'Z501A dimmer Switch', '0006:0008', '开关:调级控制', null, '3', 'source', '1', '0A', null, '0');
INSERT INTO `modeeplib_en` VALUES ('112', '81', '0401', 'Z501A-2', 'Z501A security Keypad', '', '', null, '', '', '1', '0B', null, '0');
INSERT INTO `modeeplib_en` VALUES ('113', '82', '0000', 'Z501B-1', 'Z501B 1st Switch', '0006', '开关', null, '3', 'source', '1', '0A', null, '0');
INSERT INTO `modeeplib_en` VALUES ('114', '82', '0000', 'Z501B-2', 'Z501B 2nd Switch', '0006', '开关', null, '3', 'source', '1', '0B', null, '0');
INSERT INTO `modeeplib_en` VALUES ('115', '83', '0001', 'Z501C-1', 'Z501C dimmer Switch', '0006:0008', '开关:调级控制', null, '3', 'source', '1', '0A', null, '0');
INSERT INTO `modeeplib_en` VALUES ('116', '83', '0000', 'Z501C-2', 'Z501C on Off Switch', '0006', '开关', null, '3', 'source', '1', '0B', null, '0');
INSERT INTO `modeeplib_en` VALUES ('117', '84', '0008', 'Z210C-1', 'Z210C Infrared Adpt', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('118', '85', '0008', 'Z211-1', 'Z211 Bidirectional ZigBeeIR Adpt', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('119', '86', '0007', 'Z203-1', 'Z203 CWSHC', null, null, null, null, null, '1', '0A', null, '0');
INSERT INTO `modeeplib_en` VALUES ('120', '87', '0007', 'Z103AC-1', 'Z103AC USB', null, null, null, null, null, '1', '0A', null, '0');
INSERT INTO `modeeplib_en` VALUES ('121', '88', '0007', 'Z103AR-1', 'Z103AR USB', null, null, null, null, null, '1', '0A', null, '0');
INSERT INTO `modeeplib_en` VALUES ('122', '89', '0400', 'Z201B-1', 'Z201B ZigBee802.15.4 router andCIE', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('123', '90', '0400', 'Z201C-1', 'Z201C ZigBee802.15.4 Coord', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('124', '91', '0008', 'Z201R HA-1', 'Z201R ZigBee TCP/IP Gateway', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('125', '92', '0008', 'Z201R-1', 'Z201 ZigBee 802.15.4 Router', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('126', '93', '0002', 'Z812A-1', 'Z812A output', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('127', '93', '0000', 'Z812A-2', 'Z812A 1st Switch', '0006', '开关', null, '3', 'source', '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('128', '93', '0000', 'Z812A-3', 'Z812A 2nd Switch', '0006', '开关', null, '3', 'source', '1', '03', null, '0');
INSERT INTO `modeeplib_en` VALUES ('129', '93', '0000', 'Z812A-4', 'Z812A 3rd Switch', '0006', '开关', null, '3', 'source', '1', '04', null, '0');
INSERT INTO `modeeplib_en` VALUES ('130', '94', '0000', 'Z812B-1', 'Z812B 1st Switch', '0006', '开关', null, '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('131', '94', '0000', 'Z812B-2', 'Z812B 2nd Switch', '0006', '开关', null, '3', 'source', '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('132', '94', '0000', 'Z812B-3', 'Z812B 3rd Switch', '0006', '开关', null, '3', 'source', '1', '03', null, '0');
INSERT INTO `modeeplib_en` VALUES ('133', '94', '0000', 'Z812B-4', 'Z812B 4th Switch', '0006', '开关', null, '3', 'source', '1', '04', null, '0');
INSERT INTO `modeeplib_en` VALUES ('134', '94', '0104', 'Z812B-5', 'Z812B 1st Dimmer Switch', '0006:0008', '开关:调级控制', null, '3', 'source', '1', '05', null, '0');
INSERT INTO `modeeplib_en` VALUES ('135', '94', '0104', 'Z812B-6', 'Z812B 2nd Dimmer Switch', '0006:0008', '开关:调级控制', null, '3', 'source', '1', '06', null, '0');
INSERT INTO `modeeplib_en` VALUES ('136', '95', '0402', 'Z801TXB-1', 'Z801TXB 1st Detector', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('137', '95', '0402', 'Z801TXB-2', 'Z801TXB 2nd Detector', '', '', '', '', '', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('138', '95', '0402', 'Z801TXB-3', 'Z801TXB 3rd Detector', '', '', '', '', '', '1', '03', '', '0');
INSERT INTO `modeeplib_en` VALUES ('139', '95', '0402', 'Z801TXB-4', 'Z801TXB 4th Detector', '', '', '', '', '', '1', '04', '', '0');
INSERT INTO `modeeplib_en` VALUES ('140', '95', '0402', 'Z801TXB-5', 'Z801TXB 5th Detector', '', '', '', '', '', '1', '05', '', '0');
INSERT INTO `modeeplib_en` VALUES ('141', '96', '0002', 'Z801RX', 'Z801RX output', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('142', '101', '0009', 'Z825A-1', 'Z825APower Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('143', '102', '0009', 'Z825B-1', 'Z825B 1st Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('144', '102', '0009', 'Z825B-2', 'Z825B 2nd Power Switch', '0006', '开关', null, '3', 'dest', '1', '02', null, '1');
INSERT INTO `modeeplib_en` VALUES ('145', '103', '0101', 'Z825D-1', 'Z825D Dimmer Switch', '0006:0008', '开关:调级控制', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('146', '100', '0400', 'Z508-1', 'Z508 In Home Display', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('147', '104', '0302', 'Z725A-1', 'Z725A Temperature and Humidity Sensor', null, null, null, null, null, '1', '0A', null, '0');
INSERT INTO `modeeplib_en` VALUES ('148', '106', '0008', 'Z725R-1', 'Z725R Outdoor Range Extender', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('149', '107', '0009', 'Z962A-1', 'Z962A Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('150', '108', '0009', 'Z962B-1', 'Z962B 1st Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('151', '108', '0009', 'Z962B-2', 'Z962B 2nd Power Switch', '0006', '开关', null, '3', 'dest', '1', '02', null, '1');
INSERT INTO `modeeplib_en` VALUES ('152', '109', '0009', 'Z962C-1', 'Z962C 1st Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('153', '109', '0009', 'Z962C-2', 'Z962C 2nd Power Switch', '0006', '开关', null, '3', 'dest', '1', '02', null, '1');
INSERT INTO `modeeplib_en` VALUES ('154', '109', '0009', 'Z962C-3', 'Z962C 3rd Power Switch', '0006', '开关', null, '3', 'dest', '1', '03', null, '1');
INSERT INTO `modeeplib_en` VALUES ('155', '110', '0004', 'Z962D-1', 'Z962D Scene or Mode Selector', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('156', '111', '0004', 'Z962E-1', 'Z962E 1st Scene or Mode Selector', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('157', '111', '0004', 'Z962E-2', 'Z962E 2nd Scene or Mode Selector', null, null, null, null, null, '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('158', '112', '0004', 'Z962F-1', 'Z962F 1st Scene or Mode Selector', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('159', '112', '0004', 'Z962F-2', 'Z962F 2nd Scene or Mode Selector', null, null, null, null, null, '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('160', '112', '0004', 'Z962F-3', 'Z962F 3rd Scene or Mode Selector', null, null, null, null, null, '1', '03', null, '0');
INSERT INTO `modeeplib_en` VALUES ('161', '113', '0009', 'Z962G-1', 'Z962G Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('162', '113', '0004', 'Z962G-2', 'Z962G Scene or Mode Selector', null, null, null, null, null, '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('163', '114', '0009', 'Z962H-1', 'Z962H Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('164', '114', '0004', 'Z962H-2', 'Z962H 1st Scene or Mode Selector', null, null, null, null, null, '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('165', '114', '0004', 'Z962H-3', 'Z962H 2nd Scene or Mode Selector', null, null, null, null, null, '1', '03', null, '0');
INSERT INTO `modeeplib_en` VALUES ('166', '115', '0004', 'Z962I-1', 'Z962I Scene or Mode Selector', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('167', '115', '0009', 'Z962I-2', 'Z962I 1st Power Switch', '0006', '开关', null, '3', 'dest', '1', '02', null, '1');
INSERT INTO `modeeplib_en` VALUES ('168', '115', '0009', 'Z962I-3', 'Z962I 2nd Power Switch', '0006', '开关', null, '3', 'dest', '1', '03', null, '1');
INSERT INTO `modeeplib_en` VALUES ('174', '116', '0402', 'Z801WLS-1', 'Z801WLS 1st water sensor', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('175', '116', '0402', 'Z801WLS-2', 'Z801WLS 2nd water sensor', '', '', '', '', '', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('176', '116', '0402', 'Z801WLS-3', 'Z801WLS 3rd water sensor', '', '', '', '', '', '1', '03', '', '0');
INSERT INTO `modeeplib_en` VALUES ('177', '116', '0402', 'Z801WLS-4', 'Z801WLS 4th water sensor', '', '', '', '', '', '1', '04', '', '0');
INSERT INTO `modeeplib_en` VALUES ('178', '116', '0402', 'Z801WLS-5', 'Z801WLS 5th water sensor', '', '', '', '', '', '1', '05', '', '0');
INSERT INTO `modeeplib_en` VALUES ('179', '117', '000A', 'ZB05A-1', 'ZB05A Door Lock', '0101', '开关锁', '', '3', 'dest', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('180', '118', '0000', 'Z801TX-1', 'Z801TX 1st Switch', '0006', '开关', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('181', '118', '0000', 'Z801TX-2', 'Z801TX  2nd Switch', '0006', '开关', '', '3', 'source', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('182', '118', '0000', 'Z801TX-3', 'Z801TX  3rd Switch', '0006', '开关', '', '3', 'source', '1', '03', '', '0');
INSERT INTO `modeeplib_en` VALUES ('183', '118', '0000', 'Z801TX-4', 'Z801TX  4th Switch', '0006', '开关', '', '3', 'source', '1', '04', '', '0');
INSERT INTO `modeeplib_en` VALUES ('184', '118', '0000', 'Z801TX-5', 'Z801TX  5th Switch', '0006', '开关', '', '3', 'source', '1', '05', '', '0');
INSERT INTO `modeeplib_en` VALUES ('185', '119', '0000', 'Z311J-1', 'Z311J on off switch', '0006', '开关', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('186', '119', '0402', 'Z311J-2', 'Z311J Window Intrusion Sensor', '', '', '', '', '', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('187', '120', '0101', 'ZC07-1', 'ZC07 Dimmer light', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('188', '121', '0402', 'Z311W-1', 'Z311W water sensor', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('190', '122', '0200', 'Z811B-1', 'Z811B 1st Curtain Controller', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('191', '122', '0200', 'Z811B-2', 'Z811B 2nd Curtain Controller', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('192', '123', '0004', 'Z825I-1', 'Z825I 1st Scene or Mode Selector', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('193', '123', '0004', 'Z825I-2', 'Z825I 2nd Scene or Mode Selector', '', '', '', '', '', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('194', '123', '0004', 'Z825I-3', 'Z825I 3rd Scene or Mode Selector', '', '', '', '', '', '1', '03', '', '0');
INSERT INTO `modeeplib_en` VALUES ('195', '123', '0004', 'Z825I-4', 'Z825I 4th Scene or Mode Selector', '', '', '', '', '', '1', '04', '', '0');
INSERT INTO `modeeplib_en` VALUES ('196', '123', '0004', 'Z825I-5', 'Z825I 5th Scene or Mode Selector', '', '', '', '', '', '1', '05', '', '0');
INSERT INTO `modeeplib_en` VALUES ('197', '123', '0004', 'Z825I-6', 'Z825I 6th Scene or Mode Selector', '', '', '', '', '', '1', '06', '', '0');
INSERT INTO `modeeplib_en` VALUES ('201', '124', '0004', 'Z825J-4', 'Z825J 1st Scene or Mode Selector', '', '', '', '', '', '1', '04', '', '0');
INSERT INTO `modeeplib_en` VALUES ('202', '124', '0004', 'Z825J-5', 'Z825J 2nd Scene or Mode Selector', '', '', '', '', '', '1', '05', '', '0');
INSERT INTO `modeeplib_en` VALUES ('203', '124', '0004', 'Z825J-6', 'Z825J 3rd Scene or Mode Selector', '', '', '', '', '', '1', '06', '', '0');
INSERT INTO `modeeplib_en` VALUES ('204', '124', '0009', 'Z825J-1', 'Z825J 1st Output', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('205', '124', '0009', 'Z825J-2', 'Z825J 2nd Output', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('206', '124', '0009', 'Z825J-3', 'Z825J 3rd Output', '0006', '开关', '', '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib_en` VALUES ('207', '125', '0009', 'Z825Q-1', 'Z825Q 1st Power Switch', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('208', '125', '0009', 'Z825Q-2', 'Z825Q 2nd Power Switch', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('209', '125', '0009', 'Z825Q-3', 'Z825Q 3rd Power Switch', '0006', '开关', '', '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib_en` VALUES ('210', '126', '0009', 'Z816I-1', 'Z816I China type Power Plug', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('211', '127', '0000', 'Z817D-1', 'Z817D  On Off Switch', '0006', '开关', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('212', '128', '0008', 'Z800R-1', 'Z800 Plug Router', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('213', '129', '0008', 'Z809C-1', 'Z809C Plug Router', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('214', '130', '0008', 'Z809D-1', 'Z809D Plug Router', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('215', '131', '0001', 'Z501AE3ED-1', 'Z501A dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('216', '131', '0401', 'Z501AE3ED-2', 'Z501A security Keypads', '', '', '', '', '', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('217', '132', '0000', 'Z501BE3ED-1', 'Z501B 1st Switch', '0006', '开关', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('218', '132', '0000', 'Z501BE3ED-2', 'Z501B 2nd Switch', '0006', '开关', '', '3', 'source', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('219', '133', '0001', 'Z501CE3ED-1', 'Z501C dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('220', '133', '0000', 'Z501CE3ED-2', 'Z501C security Keypads', '0006', '开关', '', '3', 'source', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('221', '134', '0402', 'ZB02I-1', 'ZB02IPanic Button', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('222', '137', '0002', 'Z802-1', 'Z802 1st Output', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('223', '137', '0002', 'Z802-2', 'Z802 2nd Output', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('225', '138', '0004', 'ZB02J-1', 'ZB02J 1st Scene or Mode Selector', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('226', '138', '0004', 'ZB02J-2', 'ZB02J 2nd Scene or Mode Selector', '', '', '', '', '', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('227', '138', '0004', 'ZB02J-3', 'ZB02J 3rd Scene or Mode Selector', '', '', '', '', '', '1', '03', '', '0');
INSERT INTO `modeeplib_en` VALUES ('228', '139', '0009', 'Z815A-1', 'Z815A Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('229', '140', '0009', 'Z815B-1', 'Z815B 1st Power Switch', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('230', '140', '0009', 'Z815B-2', 'Z815B 2nd Power Switch', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('231', '141', '0009', 'Z815C-1', 'Z815C 1st Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('232', '141', '0009', 'Z815C-2', 'Z815C 2nd Power Switch', '0006', '开关', null, '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('233', '141', '0009', 'Z815C-3', 'Z815C 3rd Power Switch', '0006', '开关', null, '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib_en` VALUES ('234', '142', '0101', 'Z815D-1', 'Z815D 1st dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('236', '143', '0101', 'Z815E-1', 'Z815E 2nd dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('237', '143', '0101', 'Z815E-2', 'Z815E 3rd dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('238', '144', '0009', 'Z826C-1', 'Z826C 1st Power Switch', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('239', '144', '0009', 'Z826C-2', 'Z826C 2nd Power Switch', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('240', '144', '0009', 'Z826C-3', 'Z826C 3rd Power Switch', '0006', '开关', '', '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib_en` VALUES ('241', '145', '0007', 'Z206-1', 'Z501G 1st security Keypads', '', '', '', '', '', '1', '0A', '', '0');

/*英文设备表*/
CREATE TABLE `modenodelib_en` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nodeType` varchar(100) DEFAULT NULL,
  `modelId` varchar(50) DEFAULT NULL,
  `deviceName` varchar(200) DEFAULT NULL,
  `clusterId` varchar(200) DEFAULT NULL,
  `clusterName` varchar(100) DEFAULT NULL,
  `destType` varchar(4) DEFAULT NULL,
  `source_id` bigint(20) DEFAULT '1',
  `deviceType` varchar(20) DEFAULT NULL,
  `picName` varchar(100) DEFAULT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `powerType` varchar(200) DEFAULT NULL,
  `activationMethod` varchar(2000) DEFAULT NULL,
  `resetDefault` varchar(2000) DEFAULT NULL,
  `remark` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `nodeType` (`nodeType`) USING BTREE,
  KEY `modelId` (`modelId`) USING BTREE,
  KEY `deviceName` (`deviceName`) USING BTREE,
  KEY `clusterId` (`clusterId`) USING BTREE,
  KEY `destType` (`destType`) USING BTREE,
  KEY `deviceType` (`deviceType`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=146 DEFAULT CHARSET=utf8;

/*添加设备英文名称及英文描述*/
INSERT INTO `modenodelib_en` VALUES ('2', '0', 'Z815I', 'Z815I Wall Switch with Power Meter (1-Gang)', '0006', '开关', '3', '1', 'dest', 'Z815I.jpg', 'Z815I is a one-gang Output Switch and its enclosure is designed to replace conventional junction box installation wall switches.  It is equipped with load consumption inspection and load control feature. Users can use ZigBee enabled remote controller and on/off device and smart phone to control Z815I. ', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('3', '0', 'Z815K', 'Z815K Wall Switch with Power Meter (2-Gang)', '0006', '开关', '3', '1', 'dest', 'Z815K.jpg', 'Z815K a three-gang Output Switch and its enclosure is designed to replace conventional junction box installation wall switches.  It is equipped with load consumption inspection and load control feature. Users can use ZigBee enabled remote controller and on/off device and smart phone to control Z815K. ', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('4', '0', 'Z806', 'Z806 Switch Control Unit (2-Output)', '0006', '开关', '3', '1', 'dest', 'Z806.jpg', '\r\nZ806 is a wireless switch relay with two outputs. Users can locally and remotely control it via external switch, paired ZigBee enabled devices and software.', '100～240V的交流电源', '不需要激活', '按住绑定键的同时给设备上电，开始恢复出厂设置，状态指示灯一直闪烁表示擦除完成。之后重新上电，Z806可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('5', '4', 'ZB02A', 'ZB02A Wall Switch (1-Key)', '0006', '开关', '3', '1', 'source', 'ZB02A.jpg', 'ZB02A is a single gang wall switch which can be hung on the wall. Paired with the other On/Off devices, it can be remotely controlled by toggling.', '2节AAA1.5V电池', '拆下面板的上盖，短按绑定键（红色按键），LED网络灯闪5下激活设备。', '拆下面板的上盖，按住绑定键的同时给设备上电，进行恢复出厂设置，看到状态指示灯闪烁，表示恢复完成', '无');
INSERT INTO `modenodelib_en` VALUES ('7', '9', 'Z503', 'Z503 Local Commander', '0006:0008', '开关:调级控制', '1', '1', 'source', 'Z503.jpg', 'Z503 is a multifunctional remote which can turn on/off all devices, dimming, control power socket, emergency button function, arm and disarm security system.', '3V CR2034纽扣电池', '按住“2nd”键和“3”键可激活设备。', '1.同时按住扩展情景键和第二功能按键，给设备上电；2. 如指示灯快速闪烁10次表示恢复出厂值完成，设备自动重启后即可重新加网；3. 如指示灯慢闪3次，表示恢复出厂值失败，请重复步骤1。', '无');
INSERT INTO `modenodelib_en` VALUES ('10', '0', 'Z805A', 'Z805A Euro Type Switch Control Unit with Power Meter (1-Output)', '0006', '开关', '3', '1', 'dest', 'Z805A.jpg', 'Z805A is a wireless switch relay with one output 16A/250V AC. Users can control it via external switch, paired ZigBee enabled devices and software. Via ZigButler, users can check the real-time current/voltage status and kilo-watt-hour consumption of the attached appliance. ', '100～240V的交流电源', '不需要激活', '长按绑定键15S(指示灯闪烁2次,3S闪烁一次，10S闪烁一次），状态灯开始快闪20秒，设备进行恢复出厂设置；20s内短按任意键，Z805B重新请求加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('11', '0', 'Z805B', 'Z805B Switch Control Unit with Power Meter (1-Output)', '0006', '开关', '3', '1', 'dest', 'Z805B.jpg', 'Z805B is wireless switch relay with one output 16A/250V AC. Users can remotely control it via external switch, paired ZigBee enabled devices and softwareVia ZigButler, users can check the real-time current/voltage status and kilo-watt-hour consumption of the attached appliance. 。', '100～240V的交流电源', '不需要激活', '长按绑定键15S(指示灯闪烁2次,3S闪烁一次，10S闪烁一次），状态灯开始快闪20秒，设备进行恢复出厂设置；20s内短按任意键，Z805B重新请求加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('12', '0', 'Z810B', 'Z810B Switch Control Unit with Power Monitoring LCD', '0006', '开关', '3', '1', 'dest', 'Z810B.jpg', 'Z810B is a switch control, power supply is 100-240VAC 50/60HZ. It can be controlled by its manual override switch or by a paired wireless switch or by software. Users can use software and its LCD to check the real-time current/voltage status and kilo-watt-hour consumption of the attached appliance.', '100～240V的交流电源', '不需要激活', '按下绑定键15秒后（图标每隔5秒闪烁一下，闪烁3次），2秒内短按功能键，LCD数值区显示表示恢复完成，之后设备将自动重启。', '无');
INSERT INTO `modenodelib_en` VALUES ('13', '0', 'Z811', 'Z811 Switch Control Unit (4-Output)', '0006', '开关', '3', '1', 'dest', 'Z811.jpg', 'Z811 is a wireless on/off output switch device. Users can control it via paired ZigBee enabled devices and software.', '100～240V的交流电源', '不需要激活', '长按绑定键15秒（状态指示灯3S闪一次，10S闪一次,15S闪一次），然后短按，状态指示灯一直闪烁表示擦除完成。之后指示灯灭掉，指示Z811便可以重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('14', '0', 'Z815J', 'Z815J Wall Switch with Power Meter (2-Gang)', '0006', '开关', '3', '1', 'dest', 'Z815J.jpg', 'Z815J is a two-gang switch and is designed to replace conventional junction box installation wall switches.  It is equipped with load consumption inspection and load control feature.  Users can use ZigBee enabled remote controller and on/off device and smart phone to control Z815J.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('15', '0', 'Z815L', 'Z815L Wall Dimmer Switch with Power Meter (1-Gang)', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z815L.jpg', 'Z815L is a one-gang dimmable switch and is designed to replace conventional junction box installation wall switches.  It is equipped with load consumption inspection and load control feature. Users can use ZigBee enabled remote controller and on/off device and smart phone to control Z815L.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('16', '0', 'Z815M', 'Z815M Wall Dimmer Switch with Power Meter (2-Gang)', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z815M.jpg', 'Z815M is a two-gang dimmable switch and is designed to replace conventional junction box installation wall switches.  It is equipped with load consumption inspection and load control feature. Users can use ZigBee enabled remote controller and on/off device and smart phone to control Z815M.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('17', '0', 'Z817A', 'Z817A Ceiling 16A Relay Switch with Power Meter', '0006', '开关', '3', '1', 'dest', 'Z817A.jpg', 'Z817A is a ceiling mount switch unit. It can do on/off switch and dim the lights through its buttoms, paired ZigBee enabled remote controllers or even software. Energy consumption reading is able to be monitored with software. ', '100～240V的交流电源', '不需要激活', '按住绑定键（正对logo靠左）15秒，设备网络灯闪烁一次，然后在2秒内短按任意键，设备网络灯快闪10次后重启。', '无');
INSERT INTO `modenodelib_en` VALUES ('18', '0', 'Z817B', 'Z817B Ceiling 16A Relay Dimmer with Power Meter', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z817B.jpg', 'Z817B is a ceiling mount dimmable unit. It can do on/off switch and dim the lights through its buttoms, paired ZigBee enabled remote controllers or even software. Energy consumption reading is also able to be monitored with software. ', '100～240V的交流电源', '不需要激活', '按住绑定键（正对logo靠左）15秒，设备网络灯闪烁一次，然后在2秒内短按任意键，设备网络灯快闪10次后重启。', '无');
INSERT INTO `modenodelib_en` VALUES ('19', '0', 'Z817C', 'Z817C Ceiling Motion Detector with On/Off Switch', '0006', '开关', '3', '1', 'dest', 'Z817C.jpg', 'Z825C is a three-gang touch switch and is designed to replace conventional junction box installation wall switches. It is equipped with load consumption inspection and load control feature. Users can use ZigBee enabled remote controller and on/off device and smart phone to control Z825C.', '100～240V的交流电源', '不需要激活', '按住绑定键（正对logo靠左）15秒，设备网络灯闪烁一次，然后在2秒内短按任意键，设备网络灯快闪10次后重启。', '无');
INSERT INTO `modenodelib_en` VALUES ('20', '0', 'Z825C', 'Z825C Touch Panel Switch with Power Meter (3-Gang)', '0006', '开关', '3', '1', 'dest', 'Z825C.jpg', 'Z825C is a three-gang touch switch and is designed to replace conventional junction box installation wall switches. It is equipped with load consumption inspection and load control feature. Users can use ZigBee enabled remote controller and on/off device and smart phone to control Z825C.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('21', '0', 'Z825E', 'Z825E Touch Panel Dimmer Switch with Power Meter (2-Gang)', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z825E.jpg', 'Z825E is a twp-gang touch switch and is designed to replace conventional junction box installation wall switches. It is equipped with load consumption inspection and load control feature. Users can use ZigBee enabled remote controller and on/off device and smart phone to control Z825E.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('22', '0', 'ZC06', 'ZC06 Dimmable LED Tube (120cm)', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'ZC06.jpg', 'ZC06 is a robust ZigBee enabled dimmable LED tube. It has 256 light levels function. It is a long life and light weighted power consumption which conserves 60% comparing to the conventional light tubes. ZC06 utilizes SMT LEDs with its 400 lumen/1.5 meters and 50,000-hour lamp life. ZC06 can be controlled wirelessly via remote controller. It supports AC 100V to 240V with the same controlling features.', '100～240V的交流电源', '不需要激活', '同时长按测试键和绑定键5s，5s时间到后指示灯闪10次后就恢复出厂设置成功，此时就可以重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('23', '1', 'Z800', 'Z800 Power Plug with Power Meter', '0006', '开关', '3', '1', 'dest', 'Z800B.jpg', 'Z800 is a power socket with power consumption monitoring that allows off site remote control. Users can control it by its bottom and by paired ZigBee enabled devices and software. Energy consumption reading is able to be monitored with software.', '100～240V的交流电源', '不需要激活', '长按绑定键15S，状态灯（红灯）快闪20S；设备进入恢复出厂设置；再按任意键重新请求加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('24', '1', 'Z803', 'Z803 Power Strip with Power Monitoring LCD & USB Port', '0006', '开关', '3', '1', 'dest', 'Z803.jpg', 'Z803 is a 4-gang power strip with power consumption display. It has USB jacks that facilitate USB charging purpose and built-in LCD. Users could also wirelessly control the switch through its own bottom, paired devices or software. LCD can display total power consumption of all sockets. Via the built-in LCD or application, users are able to check the consuming current/ voltage/ power/ energy.', '100～240V的交流电源', '不需要激活', '同时按下Match Key 和Bind Key 按键5秒后，！图标闪烁一下，代表长按了5秒。放开按键后，进行出厂设置，设备自动重新上电。', '无');
INSERT INTO `modenodelib_en` VALUES ('25', '1', 'Z808A', 'Z808A Power Plug with Power Monitoring LCD & USB Port', '0006', '开关', '3', '1', 'dest', 'Z808A.jpg', 'Z808A is a wireless smart plug. It has USB jacks that facilitate USB charging purpose and built-in LCD. Users could also wirelessly control the switch through its own bottom, paired devices or software. LCD can display total power consumption of all sockets. Via the built-in LCD or application, users are able to check the consuming current/ voltage/ power/ energy.', '100～240V的交流电源', '不需要激活', '长按绑定键（右边按键）15s（期间网络指示灯闪3次）后释放绑定键，并在2s内短按功能键（左边按键），即开始恢复出厂设置，网络灯快速闪烁十次后自动重启设备，开始寻找新的网络。', '无');
INSERT INTO `modenodelib_en` VALUES ('26', '1', 'Z808B', 'Z808B Dimmable Power Plug with Power Monitoring LCD & USB Port', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z808B.jpg', 'Z808B is a wireless dimmable smart plug. It has USB jacks that facilitate USB charging purpose and built-in LCD. Users could also wirelessly control the switch through its own bottom, paired devices or software. LCD can display total power consumption of all sockets. Via the built-in LCD or application, users are able to check the consuming current/ voltage/ power/ energy.', '100～240V的交流电源', '不需要激活', '长按绑定键（右边按键）15s（期间网络指示灯闪3次）后释放绑定键，并在2s内短按功能键（左边按键），即开始恢复出厂设置，网络灯快速闪烁十次后自动重启设备，开始寻找新的网络。', '无');
INSERT INTO `modenodelib_en` VALUES ('27', '1', 'Z809A', 'Z809A Power Plug with Power Meter', '0006', '开关', '3', '1', 'dest', 'Z809A.jpg', 'Z809A is a smart plug that allows off site remote control. Users can manually switch on/off the socket or by software to control it. Via software, users are able to monitor the load current, voltage and power and kilo-watt-hour consumption of the attached appliance.', '100～240V的交流电源', '不需要激活', '　长按绑定键（侧面）15s（期间网络指示灯闪3次）后释放绑定键，并在2s内短按测试键（正面按键），即开始恢复出厂设置，网络灯快速闪烁十次后自动重启设备，开始寻找新的网络。	无', '无');
INSERT INTO `modenodelib_en` VALUES ('28', '1', 'Z809B', 'Z809B Dimmable Power Plug with Power Meter', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z809B.jpg', 'Z809B is a dimmable smart plug that allows off site remote control. Users can manually switch on/off the socket or by paired derives or by software to control and to dim. Via software, users are able to monitor the load current, voltage and power and kilo-watt-hour consumption of the attached appliance. ', '100～240V的交流电源', '不需要激活', '长按绑定键（侧面）15s（期间网络指示灯闪3次）后释放绑定键，并在2s内短按测试键（正面按键），即开始恢复出厂设置，网络灯快速闪烁十次后自动重启设备，开始寻找新的网络。', '无');
INSERT INTO `modenodelib_en` VALUES ('29', '1', 'Z816B', 'Z816B US Type Wall Socket with Power Meter', '0006', '开关', '3', '1', 'dest', 'Z816B.jpg', 'Z816B is a US type wireless smart wall socket that allows off site remote control. Users can manually switch on/off the socket or by paired derives or by software to control and to dim. Via software, users are able to monitor the load current, voltage and power and kilo-watt-hour consumption of the attached appliance.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('30', '1', 'Z816G', 'Z816G UK Type Wall Socket with Power Meter', '0006', '开关', '3', '1', 'dest', 'Z816G.jpg', 'Z816G is a UK type wireless wall socket, it can directly install in 86 junction box and replace the traditional wall socket. It allows off site remote control. Users can manually switch on/off the socket or by paired derives or by software to control and to dim. Via software, users are able to monitor the load current, voltage and power and kilo-watt-hour consumption of the attached appliance.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('31', '1', 'Z816H', 'Z816H China Type Wall Socket with Power Meter', '0006', '开关', '3', '1', 'dest', 'Z816HI.jpg', 'Z816H is a China type wireless smart wall socket, it can directly install in 86 junction box and replace the traditional wall socket. It allows off site remote control. Users can manually switch on/off the socket or by paired derives or by software to control and to dim. Via software, users are able to monitor the load current, voltage and power and kilo-watt-hour consumption of the attached appliance.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('32', '2', 'Z711', 'Z711 Temperature and Humidity Sensor (Indoor)', null, null, null, '1', null, 'Z711.jpg', 'Z711 is a humidity and temperature sensor. It is used for collecting ambient H/T data and sending them to the display. ', '2节1.5V电池', '按下按键可激活设备', '1.  按住绑定键的同时给设备上电；2.  复位完成则led闪烁30次后停止；3.  重新上电，Z711可以开始重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('33', '2', 'Z712', 'Z712 Temperature and Humidity Sensor (Outdoor)', '', '', '', '1', '', 'Z712.jpg', 'Z712 is used to detect the outdoor humidity and temperature. It also increases a splash proof housing for protection and transmits the collecting data to a displayed device through wireless network.', '2节1.5V电池', '按下按键可激活设备', '1.  按住绑定键的同时给设备上电；2.  复位完成则led闪烁30次后停止；3.  重新上电，Z711可以开始重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('34', '2', 'Z713', 'Z713 Temperature and Humidity Sensor with Solar Panel (Outdoor)', '', '', '', '1', '', 'Z713.jpg', ' Z713 is a detector for humidity, temperature and UV rays with a solar panel charger.  It\'s also equiped with a waterproof housing for protection and used for collecting ambient H/T data and UV intensity, then sending the data to the display.  ', '2节1.5V电池', '按下按键可激活设备', '1.  按住绑定键的同时给设备上电；2.  复位完成则led闪烁30次后停止；3.  重新上电，Z711可以开始重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('35', '2', 'Z716A', 'Z716A Temperature and Humidity Sensor with LCD (Indoor)', '', '', '', '1', '', 'Z716A.jpg', 'Z716A is a humidity and temperature detector which is used for collecting ambient H/T data and display on the LCD.  It can also send the data to the displaying device. ', '2节1.5V电池', '按下右边按键可激活设备', '设备上电后，同时按住“按键1”和“按键2” 5秒，开始恢复出厂值，LCD上显示所有图标画面，表示恢复出厂值完成，设备自动复位寻找加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('36', '2', 'Z716B', 'Z716B Temperature Sensor with LCD (Indoor)', null, null, null, '1', null, 'Z716B.jpg', 'Z716B is a temperature detector which is used for collecting ambient temperature data and display on the LCD.  It can also send the data to the displaying device. ', '2节1.5V电池', '按下右边按键可激活设备', '设备上电后，同时按住“按键1”和“按键2” 5秒，开始恢复出厂值，LCD上显示所有图标画面，表示恢复出厂值完成，设备自动复位寻找加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('37', '3', 'Z302B', 'Z302B On/Off Light Switch', '0006', '开关', '3', '1', 'source', 'Z302B.jpg', 'Z302B is an automatic light switch controller, which can bind with the output switches. When the brightness is lower than a certain level, Z302B will transmit \"ON\" command and control the binding device. When the brightness enhance to a certain level, Z302B will transmit \"OFF\" command to the binding device. ', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('38', '3', 'Z302H', 'Z302H Light Sensor Dimmer Switch', '0006:0008', '开关:调级控制', '3', '1', 'source', 'Z302H.jpg', 'Z302H is a light dimmer which can sense the intensity of the ambient lighting and determine whether it need to change the brightness of its binding lamp. Thus the controlled lamp/light will maintain the certain brightness within the area. ', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('39', '3', 'Z302G', 'Z302G Light Sensor', null, null, null, '1', null, 'Z302G.jpg', 'The Z302G is an automatic detector and reportor for environment illumination.  It can regularly transmit ambient illumination to a device which can receive and display the ambient illumination.  ', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('40', '3', 'Z311B', 'Z311B On/Off Light Switch', '0006', '开关', '3', '1', 'source', 'Z311B.jpg', 'Z311B is an automatic light switch controller, which can bind with the output switches. When the brightness is lower than a certain level, Z311B will transmit \"ON\" command and control the binding device. When the brightness enhance to a certain level, Z311B will transmit \"OFF\" command to the binding device.  ', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '同时按下绑定键和辅助键，两键同时按着5s，直到看到红色指示灯开始快闪，再松开按键。红色指示灯快闪20次后，设备复位将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('41', '3', 'Z311H', 'Z311H Light Sensor Dimmer Switch', '0006:0008', '开关:调级控制', '3', '1', 'source', 'Z311H.jpg', 'Z311H is a light dimmer which can sense the intensity of the ambient lighting and determine whether it need to change the brightness of its binding lamp. Thus the controlled lamp/light will maintain the certain brightness within the area. ', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '同时按下绑定键和辅助键，两键同时按着5s，直到看到红色指示灯开始快闪，再松开按键。红色指示灯快闪20次后，设备复位将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('42', '3', 'Z311G', 'Z311G Light Sensor', '', '', '', '1', '', 'Z311G.jpg', 'The Z311G is an automatic detector and reportor for environment illumination.  It can regularly transmit ambient illumination to a device which can receive and display the ambient illumination. ', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '同时按下绑定键和辅助键，两键同时按着5s，直到看到红色指示灯开始快闪，再松开按键。红色指示灯快闪20次后，设备复位将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('44', '4', 'ZB02B', 'ZB02B Wall Switch (2-Key)', '0006', '开关', '3', '1', 'source', 'ZB02B.jpg', 'ZB02B is a two keys wireless wall-mounted switch. It can be bound with a device which has switch control function and control the switch wirelessly. ', '2节AAA1.5V电池', '拆下面板的上盖，短按绑定键（红色按键），LED网络灯闪5下激活设备。', '拆下面板的上盖，按住绑定键的同时给设备上电，进行恢复出厂设置，看到状态指示灯闪烁，表示恢复完成', '无');
INSERT INTO `modenodelib_en` VALUES ('45', '4', 'ZB02C', 'ZB02C Wall Switch (3-Key)', '0006', '开关', '3', '1', 'source', 'ZB02C.jpg', 'ZB02C is a three keys wireless wall-mounted switch. It can be bound with a device which has switch control function and control the switch wirelessly. ', '2节AAA1.5V电池', '拆下面板的上盖，短按绑定键（红色按键），LED网络灯闪5下激活设备。', '拆下面板的上盖，按住绑定键的同时给设备上电，进行恢复出厂设置，看到状态指示灯闪烁，表示恢复完成', '无');
INSERT INTO `modenodelib_en` VALUES ('46', '4', 'ZB02F', 'ZB02F Wall Dimmer Switch', '0006:0008', '开关:调级控制', '3', '1', 'source', 'ZB02F.jpg', 'ZB02F is a single key wireless wall-mounted switch. It can be bound with a device which has switch and level control function and control the switch wirelessly. ', '2节AAA1.5V电池', '拆下面板的上盖，短按绑定键（红色按键），LED网络灯闪5下激活设备。', '拆下面板的上盖，按住绑定键的同时给设备上电，进行恢复出厂设置，看到状态指示灯闪烁，表示恢复完成。', '无');
INSERT INTO `modenodelib_en` VALUES ('47', '5', 'ZB01A', 'ZB01A Motion Detector', null, null, null, '1', null, 'ZB01A.jpg', 'The infrared detection alarm function of ZB01A can detect the movement of object. And trigger the alarm through the security center. ', '2节CR123A电池或12V直流电源', '按住背面小圆点按键3秒以上，直至状态指示灯快速闪烁2次激活设备', '按住背面小圆点（绑定键）的同时给设备上电，状态灯快闪，即恢复出厂设置操作成功。恢复完成后重新上电就可以重新加入网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('48', '5', 'ZB01B', 'ZB01B Motion Detector with On/Off Light Switch', '0006', '开关', '3', '1', 'source', 'ZB01B.jpg', 'ZB01B is a switch controller for motion detection. It can detect the movement of object through infrared and control the binding device according to the setting of light threshold.', '2节CR123A电池或12V直流电源', '按住背面小圆点按键3秒以上，直至状态指示灯快速闪烁2次激活设备', '按住背面小圆点（绑定键）的同时给设备上电，状态灯快闪，即恢复出厂设置操作成功。恢复完成后重新上电就可以重新加入网络了。', '可通过调节设备背面的光照灵敏度来设置绑定开关的的光照阈值。');
INSERT INTO `modenodelib_en` VALUES ('49', '5', 'ZB01C', 'ZB01C Motion Detector with On/Off Light Switch and Temperature Sensor', '0006', '开关', '3', '1', 'source', 'ZB01C.jpg', 'ZB01C has infrared detecting alarm, infrared on/off control and temperature reporting, three functions. The function of infrared alarm can detect the movement and send the alert through security center. Infrared on/off control function can detect the infrared generated from the moving objects then control the light switch. Temperature reporting function can detect the current temperature of the environment, and report it regularly to the binding device. ', '2节CR123A电池或12V直流电源', '按住背面小圆点按键3秒以上，直至状态指示灯快速闪烁2次激活设备', '按住背面小圆点（绑定键）的同时给设备上电，状态灯快闪，即恢复出厂设置操作成功。恢复完成后重新上电就可以重新加入网络了。', '可通过调节设备背面的光照灵敏度来设置绑定开关的的光照阈值。');
INSERT INTO `modenodelib_en` VALUES ('50', '5', 'ZB01D', 'ZB01D Occupancy Sensor', null, null, null, '1', null, 'ZB01D.jpg', 'ZB01D is an occupancy sensor. It can detect the object movement which produces infrared and report the status to the binding device. ', '2节CR123A电池或12V直流电源', '按住背面小圆点(绑定键)按键3秒以上，直至状态指示灯快速闪烁2次激活设备', '按住背面小圆点（绑定键）的同时给设备上电，状态灯快闪，即恢复出厂设置操作成功。恢复完成后重新上电就可以重新加入网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('51', '5', 'ZB11A', 'ZB11A Motion Detector', '', '', '', '1', '', 'ZB11A.jpg', 'The infrared detection alarm function of ZB11A can detect the movement of object. And trigger the alarm through the security center (CIE).', '2节ER14505 3.6V AA电池供电', '正对logo，短按右侧按钮指示灯闪5下，即可激活设备', '长按辅助键（正对logo右边）10秒，见到绿色指示灯快闪即可松开按键，设备将进行恢复出厂设置操作，完成后将自动关机。', '无');
INSERT INTO `modenodelib_en` VALUES ('52', '5', 'ZB11B', 'ZB11B Motion Detector with On/Off Light Switch', '0006', '开关', '3', '1', 'source', 'ZB11B.jpg', 'ZB11B is a switch controller for motion detection. It can detect the movement of object through infrared and control the binding device according to the setting of light threshold. ', '2节ER14505 3.6V AA电池供电', '正对logo，短按右侧按钮指示灯闪5下，即可激活设备', '长按辅助键（正对logo右边）10秒，见到绿色指示灯快闪即可松开按键，设备将进行恢复出厂设置操作，完成后将自动关机。', '可通过调节设备背面的光照灵敏度来设置绑定开关的的光照阈值。');
INSERT INTO `modenodelib_en` VALUES ('53', '5', 'ZB11C', 'ZB11C Motion Detector with On/Off Light Switch and Temperature Sensor', '0006', '开关', '3', '1', 'source', 'ZB11C.jpg', 'ZB01C has infrared detecting alarm, infrared on/off control and temperature reporting, three functions. The function of infrared alarm can detect the movement and send the alert through security center. Infrared on/off control function can detect the infrared generated from the moving objects then control the light switch. Temperature reporting function can detect the current temperature of the environment, and report it regularly to the binding device. ', '2节ER14505 3.6V AA电池供电', '正对logo，短按右侧按钮指示灯闪5下，即可激活设备', '长按辅助键（正对logo右边）10秒，见到绿色指示灯快闪即可松开按键，设备将进行恢复出厂设置操作，完成后将自动关机。', '可通过调节设备背面的光照灵敏度来设置绑定开关的的光照阈值。');
INSERT INTO `modenodelib_en` VALUES ('54', '5', 'ZB11D', 'ZB11D Occupancy Sensor', '', '', '', '1', '', 'ZB11D.jpg', 'ZB01D is an occupancy sensor. It can detect the object movement which produces infrared and report the status to the binding device. ', '2节ER14505 3.6V AA电池供电', '正对logo，短按右侧按钮指示灯闪5下，即可激活设备', '长按辅助键（正对logo右边）10秒，见到绿色指示灯快闪即可松开按键，设备将进行恢复出厂设置操作，完成后将自动关机。', '无');
INSERT INTO `modenodelib_en` VALUES ('55', '6', 'Z815N', 'Z815N  AC Curtain Controller', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z815N.jpg', 'Z815N is a curtain controller. It can control the curtains on/off and level through its own key, binding equipments or software. At the same time, the users can check the load current, voltage, power and energy at present. ', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', 'Lin接火线，N线是零线接电机的蓝线. L out1接电机的棕线，L out2接电机的黑线。在设备执行窗帘控制功能前,都应先进行“学习上下限”的学习，学习方法请详见设备说明书或在app的设备管理模块，该设备的设置页面进行学习。');
INSERT INTO `modenodelib_en` VALUES ('56', '6', 'ZD01B', 'ZD01B Toggle & Level Curtain Controller (Drape)', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'ZD01B.jpg', 'ZD01B is a curtain controller. It can control the curtains on/off and level through its own key, binding equipments or software.', '12V4A直流电源', '不需要激活', '按住ON1和OFF1键（靠右边一列）的同时给设备上电，开始执行恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重新上电就可以重新加网了', '在设备执行窗帘控制功能前,都应先进行“学习上下限”的学习，学习方法请详见设备说明书或在app的设备管理模块，该设备的设置页面进行学习');
INSERT INTO `modenodelib_en` VALUES ('57', '7', 'Z302A', 'Z302A Window Intrusion Sensor', null, null, null, '1', null, 'Z302A.jpg', 'Z302A is a magnetic device, used as a detection device in the security systems. When the door or window is opened, it will send alarm message to the security center. When the door or window is closed, it will send out the normal status message to the security center. ', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', 'Z302A的本体与门磁的磁铁部分别安装在门或窗户的缝隙的两边，当门或窗被打开，它将发出报警信息给安防中心设备( 即CIE )，当门或窗被关闭好，它会发送出状态恢复正常的信息给CIE。该设备也可以用于冰箱、抽屉等处，用于检测冰箱或抽屉等是否被打开。需要注意防水。');
INSERT INTO `modenodelib_en` VALUES ('58', '7', 'Z302C', 'Z302C Window Sensor with Glass Break Detector', null, null, null, '1', null, 'Z302C.jpg', 'Z302C is a detection device in the security systems, as an alarm devices when the doors and windows were exceptional be open or the glass are broken. ', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', 'Z302C的本体与门磁的磁铁部分别安装在门或窗户的缝隙的两边，当门或窗被打开，它将发出报警信息给安防中心设备( 即CIE )，当门或窗被关闭好，它会发送出状态恢复正常的信息给CIE。该设备也可以用于冰箱、抽屉等处，用于检测冰箱或抽屉等是否被打开。需要注意防水。');
INSERT INTO `modenodelib_en` VALUES ('59', '7', 'Z302D', 'Z302D Emergency Push Button', null, null, null, '1', null, 'Z302D.jpg', 'Z302D is a alarm triggered device, as a detection device in the security systems. It can be worn on the wrist of a child or the elderly, children or the elderly when in need of emergency assistance in danger, press the alarm button Z302D, Z302D immediately security Center sends out an alarm message.', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('60', '7', 'Z302E', 'Z302E Asset tag', null, null, null, '1', null, 'Z302E.jpg', 'Z-302E is equipped with light and movement sensor to detect light intensity and movements in the environment.  When status is changed, Z-302E sends alarm message to CIE.', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '可将该设备放于贵重的物品或抽屉旁，当物品被移动或光线发生变化时，则设备会将报警信息发送给安防中心设备。');
INSERT INTO `modenodelib_en` VALUES ('61', '7', 'Z302J', 'Z302J Window Intrusion Sensor', '0006', '开关', '3', '1', 'source', 'Z302J.jpg', 'Z-302J is a window/door sensor. When window/door status is changed, it can control its binding device according to the configurations. Z-302J also works as sensor of the security center- When window/door is open, it notifies security center CIE; When window/door is closed, it notifies security center CIE. ', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', 'Z302J的本体与门磁的磁铁部分别安装在门或窗户的缝隙的两边，当门或窗被打开，它将发出报警信息给安防中心设备( 即CIE )，当门或窗被关闭好，它会发送出状态恢复正常的信息给CIE。该设备也可以用于冰箱、抽屉等处，用于检测冰箱或抽屉等是否被打开。需要注意防水。');
INSERT INTO `modenodelib_en` VALUES ('62', '7', 'Z306D', 'Z306D Panic Button and Inductive Charging Base', null, null, null, '1', null, 'Z307.jpg', 'Z-306D acts as an end device in the network as well as the mobile tags of the local network. It can be searched and controlled via third-party software such as Netvox Zig-Butler.', '3V CR2450钮扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('63', '7', 'Z311A', 'Z311A Window Sensor', null, '', '', '1', '', 'Z311A.jpg', 'Z-311A is a window/door sensor and it is also a sensor in the security system- When window/door is closed, it notifies security center CIE. ', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '同时按下绑定键和辅助键，两键同时按着5s，直到看到红色指示灯开始快闪，再松开按键。红色指示灯快闪20次后，设备复位将进入power off模式。', 'Z311A的本体与门磁的磁铁部分别安装在门或窗户的缝隙的两边，当门或窗被打开，它将发出报警信息给安防中心设备( 即CIE )，当门或窗被关闭好，它会发送出状态恢复正常的信息给CIE。该设备也可以用于冰箱、抽屉等处，用于检测冰箱或抽屉等是否被打开。需要注意防水。');
INSERT INTO `modenodelib_en` VALUES ('64', '7', 'Z311C', 'Z311C Window Sensor with Glass Break Detector', null, '', '', '1', '', 'Z311C.jpg', 'Z-311C is a sensor in the security system- When window/door is open or window glass is broken, it will send out alarm messages.', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '同时按下绑定键和辅助键，两键同时按着5s，直到看到红色指示灯开始快闪，再松开按键。红色指示灯快闪20次后，设备复位将进入power off模式。', 'Z311A的本体与门磁的磁铁部分别安装在门或窗户的缝隙的两边，当门或窗被打开，它将发出报警信息给安防中心设备( 即CIE )，当门或窗被关闭好，它会发送出状态恢复正常的信息给CIE。该设备也可以用于冰箱、抽屉等处，用于检测冰箱或抽屉等是否被打开。需要注意防水。');
INSERT INTO `modenodelib_en` VALUES ('65', '7', 'Z312', 'Z312 Door Bell Button', null, null, null, '1', null, 'Z312.jpg', 'Z-312 is a door bell. Bind Z-312 with siren device and the siren will be able to generate door bell sounds.', '3V CR2450纽扣电池', '　　　　当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '同时按下绑定键和辅助键，两键同时按着5s，直到看到红色指示灯开始快闪，再松开按键。红色指示灯快闪20次后，设备复位将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('66', '7', 'Z307', 'Z307 Wireless Rechargeable Fall Sensor', null, null, null, '1', null, 'Z307.jpg', 'Z307 is a fall sensor as well as a sensor in security system. It can be worn on waist to detect fall of elderly people and young children. When fall is detected, it sends alarm message to the security center device.', '充电套件', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('67', '7', 'Z308', 'Z308 Wearable Presence Tag + Panic Button', null, null, null, '1', null, 'Z308.jpg', 'Z-308 is an emergency alarm device. As a sensor of the security system, it can be worn on wrist of elderly people or young children. When elderly people or young children press Z308\'s emergency button, it sends out alarm message to security center.', '3V CR2450纽扣电池', '长按按键3s，红色指示灯闪一下，松开手后，绿色指示灯闪五下。即激活设备。', '长按报警键15秒以上，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯快闪10次后，设备进入关机状态，红色指示灯熄灭。', '无');
INSERT INTO `modenodelib_en` VALUES ('68', '7', 'ZA01A', 'ZA01A Smoke Detector (Chemiresistors)', null, null, null, '1', null, 'ZA01A.jpg', 'Z-A01A is an air pollution sensor used in home environment, and it acts as a security device in the network. When air intensity goes above a specific level , the device will generate buzzer sound and send status change message to CIE.', '12V直流电源或100～240V交流供电', '不需要激活', '同时按住绑定键和测试键5秒以上，设备指示灯开始快速闪烁，表示复位完成。之后将设备重启，就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('69', '7', 'ZA01B', 'ZA01B Gas Detector', '', '', '', '1', '', 'ZA01B.jpg', 'Z-A01B is a gas detector and it acts as a security device in the network. When gas intensity goes above a specific level, it will send warning message to CIE and generate buzzer sound to warn the users.', '12V直流电源或100～240V交流供电', '不需要激活', '同时按住绑定键和测试键5秒以上，设备指示灯开始快速闪烁，表示复位完成。之后将设备重启，就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('70', '7', 'ZA01C', 'ZA01C CO Detector', null, null, null, '1', null, 'ZA01C.jpg', 'Z-A01C is a CO detector and it acts as a security device in the network. When CO intensity goes above a specific level, it will send warning message to CIE and generate buzzer sound to warn the users.', '12V直流电源或100～240V交流供电', '不需要激活', '同时按住绑定键和测试键5秒以上，设备指示灯开始快速闪烁，表示复位完成。之后将设备重启，就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('71', '7', 'ZA01D', 'ZA01D Liquefied Petroleum Gas Detector', null, null, null, '1', null, 'ZA01D.jpg', 'Z-A01D is a Liquefied gas detector and it acts as a security device in the network. When liquefied gas intensity goes above a specific level, it will send warning message to CIE and generate buzzer sound to warn the users.', '12V直流电源或100～240V交流供电', '不需要激活', '同时按住绑定键和测试键5秒以上，设备指示灯开始快速闪烁，表示复位完成。之后将设备重启，就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('72', '7', 'ZA02E', 'ZA02E Smoke Detector with Backup Battery (Photoelectric)', null, null, null, '1', null, 'ZA02E.jpg', 'Z-A02E is a smoke detector. When smoke intensity goes above a specific level, it will send warning message to CIE and generate buzzer sound to warn the users.', '12V直流电源或100～241V交流供电', '不需要激活', '同时按住绑定键和测试键5秒以上，设备指示灯开始快速闪烁，表示复位完成。之后将设备重启，就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('73', '7', 'ZA10', 'ZA10 Gas/Water Keeper', '0006', '开关', '3', '1', 'dest', 'ZA10.jpg', 'Z-A10 can be a gas keep or water keep. When Z-A10 is used as a gas keeper, it will turn off the gas when there are fire detection in the network.', '12V直流电源', '不需要激活', '按住按键的同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复出厂设置完成，重新上电就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('74', '7', 'ZB05', 'ZB05 Electronic Door Lock with Fingerprint Identification', '0101', '开关锁', '3', '1', 'dest', 'ZB05.jpg', 'Z-B05 can be unlocked/locked via mechanical keys, passwords, IC cards, and remote control. Via ZigBee wireless network, users can check and control door lock status at any time. Z-B05 door lock provides reliable password security and fire detection for safety purpose.', '8节AA1.5V电池', '不需要激活', '1.按住网络键的同时给设备上电，上电完请不要立即松开按键，等到网络灯开始快速闪烁，提示复位成功；2.重新上电，ZB05可以开始重新加网。', '1、初次使用时，需要注册zigbee：在门打开的状态下，打开内侧用（主锁身）的电池盖和外盒，在右侧下端按“锁卡注册键”。然后再按下面的“ZB注册键”（靠上的按键）。按“*”键，可听到“哔呖呖”的设置音，设置结束。2、默认都为加密的方式，第一次使用时，需要在app中→设备管理模块→进入门锁的设置页面，设置新的用户和密码，才可以对锁进行开关控制。门锁默认有6个空用户，注册新用户前，需要先将这些用户删除。超级密码默认为：123456.usercode即用户名为1-254的数字。');
INSERT INTO `modenodelib_en` VALUES ('75', '7', 'ZB02E', 'ZB02E Door Bell', null, null, null, '1', null, 'ZB02E.jpg', 'Z-B02E is a door bell. Bind Z-B02E with siren device and the siren will be able to generate door bell sounds.', '2节AAA1.5V电池', '拆下面板的上盖，短按绑定键（红色按键），LED网络灯闪5下激活设备。', '拆下面板的上盖，按住绑定键的同时给设备上电，进行恢复出厂设置，看到状态指示灯闪烁，表示恢复完成', '无');
INSERT INTO `modenodelib_en` VALUES ('76', '7', 'Z311E', 'Z311E Asset tag', '', '', '', '1', '', 'Z311E.jpg', 'Z-311E is equipped with light and movement sensor to detect light intensity and movements in the environment.  When status is changed, Z-311E sends alarm message to CIE.', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '同时按下绑定键和辅助键，两键同时按着5s，直到看到红色指示灯开始快闪，再松开按键。红色指示灯快闪20次后，设备复位将进入power off模式。', '可将该设备放于贵重的物品或抽屉旁，当物品被移动或光线发生变化时，则设备会将报警信息发送给安防中心设备。');
INSERT INTO `modenodelib_en` VALUES ('77', '7', 'Z602A', 'Z602A Siren', null, null, null, '1', null, 'Z602A.jpg', 'Z-602A is a siren device which generate buzzer sound and visible LED indication to report events in the security system.', '12V直流电源和3节1.2V可充电备用电池', '不需要激活', '按住设置键（正对logo右侧按键）的同时给设备上电，状态灯快速闪烁且发出两次门铃报警声，即恢复出厂设置操作成功。恢复完成后重新上电就可以重新加入网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('78', '7', 'Z602B', 'Z602B Siren with GSM Connectivity', null, null, null, '1', null, 'Z602B.jpg', 'Z-602B is a siren device which generate buzzer sound and visible LED indication to report events in security system. When Z-602B generate buzzer sounds, it can also call and text a pre-configured phone number according to different alarm levels for users to receive the alarm message in the real time.', '12V直流电源和3节1.2V可充电备用电池', '不需要激活', '按住设置键（正对logo右侧按键）的同时给设备上电，状态灯快速闪烁且发出两次门铃报警声，即恢复出厂设置操作成功。恢复完成后重新上电就可以重新加入网络了。', '该设备具有GSM功能，可在电池盒内装入SIM卡，在app的设备管理模块，进入该设备的设置页面，可设置电话号码，用于报警时，通过拨打电话和发送短信通知用户。设置的短信号码前，需要加国家代码（如中国：86）');
INSERT INTO `modenodelib_en` VALUES ('79', '7', 'Z601A', 'Z601A Siren', null, null, null, '1', null, 'Z601A.jpg', 'Z-601A is a siren device which generate buzzer sound to report events in the security system.', '12V直流电源', '不需要激活', '按住绑定键的同时给设备上电，状态灯快闪，即恢复出厂设置操作成功。恢复完成后重新上电就可以重新加入网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('80', '8', 'Z821', 'Z821 Multi-Channel Energy Power Meter', null, null, null, '1', null, 'Z821.jpg', 'Z-821 is an indoor multi-channel power meter. It can measure voltage of single channel and the total current of all seven channels, and it can provide power and energy data.', '100～240V的交流电源', '不需要激活', '按下Bind Key按键15秒后（状态灯每5秒钟闪烁1次，共闪烁3次，代表长按了15秒），松开按键，在两秒之内短按Match Key进行网络信息的擦除。网络信息擦除后设备自动重启寻找网络。', '将七路CT端子（用于检测电能）接入到Z821两侧的接口。按照CT端子上指示的K→L方向（电流流动的方向）,将导线扣入CT端子，即可检测该路的电能。');
INSERT INTO `modenodelib_en` VALUES ('81', '9', 'Z501A', 'Z501A 4-Key Remote Controller with Arm/Disarm, On/Off, & Panic Button', '0006:0008', '开关:调级控制', '3', '1', 'source', 'Z501A.jpg', 'As a remote control device, Z501A can be bound with on/off or dimmer devices for users to control the devices wirelessly. Z-501A also comes with an emergency button for users to call for help in emergency.', '3V CR2032纽扣电池', '右侧开关off断电，重新上电后即可激活设备。', '按住“开关键” 的同时给设备上电，开始恢复出厂值，指示灯共闪烁20次，之后重新操作组合键就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('82', '9', 'Z501B', 'Z501B 4-Key Remote Controller with On/Off Switch (2-Set)', '0006', '开关', '3', '1', 'source', 'Z501B.jpg', 'As a remote control device, Z-501B can be bound with on/off devices for users to control the devices wirelessly.', '3V CR2032纽扣电池', '右侧开关off断电，重新上电后即可激活设备。', '按住“ON2键” 的同时给设备上电，开始恢复出厂值，指示灯共闪烁20次，之后重新操作组合键就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('83', '9', 'Z501C', 'Z501C 4-Key Remote Controller with Level Control & On/Off Switch', '0006:0008', '开关:调级控制', '3', '1', 'source', 'Z501C.jpg', 'Z501C can be bound with on/off or dimmer devices for users to control the devices wirelessly.', '3V CR2033纽扣电池', '右侧开关off断电，重新上电后即可激活设备。', '按住“ ON/OFF1键” 的同时给设备上电，开始恢复出厂值，指示灯共闪烁20次，之后重新操作组合键就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('84', '10', 'Z210', 'Z210 Infrared Gateway with 1 External Port', null, null, null, '1', null, 'Z210C.jpg', 'Z-210 is the center of the security system to manage security devices. It can also learn and send IR signals for users to control their home appliances such as TV and DVD player.', '12V直流电源', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '使用红外转接器控制家用电器时，需要在app上导入红外数据，若无红外数据时，则需要通过IR学习器或手机app手动学习。');
INSERT INTO `modenodelib_en` VALUES ('85', '10', 'Z211', 'Z211  Infrared Gateway with 4 External Ports', null, null, null, '1', null, 'Z211.jpg', 'Z-211 is a device for IR learning and IR controlling. After learning IR codes, Z-211 can control home appliances which receives IR signals. Also, Z-211 can control ZigBee devices when it receives IR signals from a regular remote control.', '12V直流电源', '不需要激活', '按住绑定键（蓝色按键），同时给设备上电，开始恢复出厂设置，直到状态指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '使用红外转接器控制家用电器时，需要在app上导入红外数据，若无红外数据时，则需要通过IR学习器或手机app手动学习。');
INSERT INTO `modenodelib_en` VALUES ('86', '11', 'Z203', 'Z203  Cloud Wireless Smart Home Center', null, null, null, '1', null, 'Z203.jpg', 'As the main controller of Netvox Cloud-based Wireless Smart Home System, Z-203 is a perfect combination of cloud services, Wifi technology, and ZigBee Home Automation solutions. Z-203 enables users to monitor their lights, curtains, and home appliances via smart home app and Wifi connection, and users can easily monitor their home with 2G/3G network even they are far away.', '12V直流电源', '不需要激活', '详见203说明书', 'Z203可用12V直流电源供电，给Z203上电后，打开手机的WiFi功能，即可搜索到Z203的无线信号。外网网线接入Z203的WAN口，LAN口可直接接IPcamera或电脑，当家中有多个 IPcamera时，需要增加集线器。');
INSERT INTO `modenodelib_en` VALUES ('87', '11', 'Z103AC', 'Z103AC USB Coordinator', null, null, null, '1', null, 'Z103AC.jpg', 'Z103AC is a coordinator with USB port. The devicce can communicate with other Zigbee modules through the port, and thus control the entire system.', 'USB', '不需要激活', '按住PermitJoin键的同时给Z103设备上电，设备指示灯处于快速闪烁状态，表示恢复出厂设置完成，设备重新上电就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('88', '11', 'Z103AR', 'Z103AR USB Router', null, null, null, '1', null, 'Z103AR.jpg', 'Z103AR is a Router with USB port，which can transmit the information to other devices with the USB port driver.', 'USB', '不需要激活', '按住PermitJoin键的同时给Z103设备上电，设备指示灯处于快速闪烁状态，表示恢复出厂设置完成，设备重新上电就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('89', '11', 'Z201B', 'Z201B Coordinator with Security Center (CIE)', null, null, null, '1', null, 'Z201B.jpg', 'Z201B is a HA Coordinator & CIE. With the USB port driver, the device can communicate with other Zigbee modules through the port and allow the enrolling of other devices, thus to control the entire system;', '12V直流电源', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('90', '11', 'Z201C', 'Z201C ZigBee802.15.4 Coord ', null, null, null, '1', null, 'Z201C.jpg', 'Z201C is HA coordinator with USB port,  With the USB port driver, the device can communicate with other Zigbee modules through the port, thus to control the entire system;', '12V直流电源', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('91', '11', 'Z201R HA', 'Z201R HA ZigBee TCP/IP Gateway', null, null, null, '1', null, 'Z201R.jpg', 'HA Repeater', '12V直流电源', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('92', '11', 'Z201R', 'Z201R ZigBee 802.15.4 Router', null, null, null, '1', null, 'Z201R.jpg', 'Location Reference Node', '12V直流电源', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('93', '4', 'Z812A', 'Z812A Programmable 8-Button Scene Control Keypad', '0006', '开关', '3', '1', 'source', 'Z812A.jpg', 'Z-812A is Multi-port Wireless  In-wall Switch Module. It can bind with the devices carrying the function of on/off switch control and enables wireless on/off control.', '100～240V的交流电源', '不需要激活', '上电以后，同时按住OFF3和OFF4键，按住5秒，网络灯灭，然后放开按键，开始恢复出厂值，直到网络灯开始闪烁，表示恢复完成。之后设备将自动复位，可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('94', '4', 'Z812B', 'Z812B Battery Operated 8-Button Control Keypad', '0006:0008', '开关:调级控制', '3', '1', 'source', 'Z812B.jpg', 'Z-812B is Multi-port Wireless  In-wall Switch Module. It can bind with the devices carrying the function of on/off switch control and enables wireless on/off control.', '100～240V的交流电源', '同时按住第一行两个按键即可激活设备。', '同时按住按键3和按键4达3秒，指示灯闪烁1次提示设备开始恢复出厂设置，恢复成功指示灯快闪10次，不成功指示灯无动作。恢复出厂设置之后设备自动重启。', '无');
INSERT INTO `modenodelib_en` VALUES ('95', '7', 'Z801TXB', 'Z801TXB Sensor Signal Tx Module', '', '', '', '1', '', 'Z801TXB.jpg', 'Z801TXB is a pulse Signal Detector，it can be connected with 5 equipment.if Z801txb detects the signal,it will send the command to the security of the ENROLL control center .', '5V直流电源或2节1.5V电池', '不需要激活', '按住绑定键的同时给设备上电，LED2闪烁20次，表示复位完成，之后重新上电，Z801 TXB可以重新加网。 ', '无');
INSERT INTO `modenodelib_en` VALUES ('96', '0', 'Z801RX', 'Z801RX  RX Relay Board', '0006', '开关', '3', '1', 'dest', 'Z801RX.jpg', 'Z801RX is relay board.it is used to control the switch of the equipment,mainly for controling the switch of household appliance.  ', '2节1.2V电池', '不需要激活', '1.  按住绑定键的同时给设备上电；2.  复位完成则状态指示灯快速灯闪烁；3.  重新上电，Z801RX可以开始重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('100', '11', 'Z508', 'Z508 In Home Display', null, null, null, '1', null, 'Z508.jpg', 'Z508 is a Smart In-Home Display for smart home and household energy monitoring. Z-508 works as Smart Home Control Center and allows users to review current and historical energy consumption data.By through Zig-Butler Smart Scheme, users can select and switch any preferable mode setting. With the implementation of CIE, Z-508 is capable of managing Home Security System and tells security related message on its display.', '5V直流电源或2节1.5V电池', '不需要激活', '详见Z508说明书', '无');
INSERT INTO `modenodelib_en` VALUES ('101', '0', 'Z825A', 'Z825A Touch Panel Switch with Power Meter (1-Gang)', '0006', '开关', '3', '1', 'dest', 'Z825A.jpg', 'Z-825A is One Gang In-Wall Switch with Touch Panel and fits with 86*86 junction box and replaces the existing wall switch. It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-825A by ZigBee Remote Control, Smart Phone, On/Off Switch and so on.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('102', '0', 'Z825B', 'Z825B Touch Panel Switch with Power Meter (2-Gang)', '0006', '开关', '3', '1', 'dest', 'Z825B.jpg', 'Z-825B is Two Gang In-Wall Switch with Touch Panel and fits with 86*86 junction box and replaces the existing wall switch. It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-825A by ZigBee Remote Control, Smart Phone, On/Off Switch and so on.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('103', '0', 'Z825D', 'Z825D Touch Panel Dimmer Switch with Power Meter (1-Gang)', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z825D.jpg', 'Z-825D is One Gang In-Wall Dimmer Switch with Touch Panel and fits with 86*86 junction box and replaces the existing wall switch. It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-825A by ZigBee Remote Control, Smart Phone, On/Off Switch and so on.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('104', '2', 'Z725A', 'Z725A Temperature and Humidity Sensor with Solar Battery (Outdoor)', null, null, null, '1', null, 'Z725A.jpg', 'Z-725A is Wireless H/T Sensor and Ultraviolet Detector with Solar Battery Panel. It comes with water-proof enclosure for protective purpose. It is used to measure and collect Humidity, Temperature and Ultraviolet data in the surrounding and direct to the data collector for display.', '太阳能充电电池', '不需要激活', '设备上电后，同时按住“绑定键”和“设置键” 5秒，开始恢复出厂值，LED快闪10次，表示恢复出厂值完成，设备自动复位寻找加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('106', '11', 'Z725R', 'Z725R Repeater with Solar Battery (Outdoor)', null, null, null, '1', null, 'Z725R.jpg', 'Z-725R is a Outdoor type Wireless Repeater，which is use to expand the network range。 ', '太阳能充电电池', '不需要激活', '设备上电后，同时按住绑定键和辅助键5秒，状态灯闪烁1次后，开始恢复出厂值，LED快闪20次，表示恢复出厂值完成，设备自动复位寻找加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('107', '0', 'Z962A', 'Z962A One Gang Wall Switch with Touch Panel', '0006', '开关', '3', '1', 'dest', 'Z962A.jpg', 'Z-962A is One Gang In-Wall Switch with Touch Panel and fits with 86*86 junction box and replaces the existing wall switch. It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-962A by ZigBee Remote Control, Smart Phone, On/Off Switch and so on.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('108', '0', 'Z962B', 'Z962B Two Gang Wall Switch with Touch Panel', '0006', '开关', '3', '1', 'dest', 'Z962B.jpg', 'Z-962B is Two Gang In-Wall Switch with Touch Panel and fits with 86*86 junction box and replaces the existing wall switch. It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-962B by ZigBee Remote Control, Smart Phone, On/Off Switch and so on.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('109', '0', 'Z962C', 'Z962C Three Gang Wall Switch with Touch Panel', '0006', '开关', '3', '1', 'dest', 'Z962C.jpg', 'Z-962C is Three Gang In-Wall Switch with Touch Panel and fits with 86*86 junction box and replaces the existing wall switch. It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-962C by ZigBee Remote Control, Smart Phone, On/Off Switch and so on.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('110', '0', 'Z962D', 'Z962D Scene or Mode Selector 1 key with Touch Panel', null, null, null, '1', null, 'Z962D.jpg', 'Z-962D is Scene and Mode control keypad, by through 1 touch keypad, users can add scene/mode, keep scene storage and manage mode control to carry out memorizing 1 scence control. It also supports Netvox mode control functionality.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib_en` VALUES ('111', '0', 'Z962E', 'Z962E Scene or Mode Selector 2 keys with Touch Panel', null, null, null, '1', null, 'Z962E.jpg', 'Z-962E is Scene and Mode control keypad, by through 2 touch keypad, users can add scene/mode, keep scene storage and manage mode control to carry out memorizing 2 scence control. It also supports Netvox mode control functionality.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib_en` VALUES ('112', '0', 'Z962F', 'Z962F Scene or Mode Selector 3 keys with Touch Panel', null, null, null, '1', null, 'Z962F.jpg', 'Z-962F is Scene and Mode control keypad, by through 3 touch keypad, users can add scene/mode, keep scene storage and manage mode control to carry out memorizing 3 scence control. It also supports Netvox mode control functionality.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib_en` VALUES ('113', '0', 'Z962G', 'Z962G One Scene or Mode Selector And One Switch with Touch Panel', '0006', '开关', '3', '1', 'dest', 'Z962G.jpg', 'Z962G is One Gang Touch Switch and 1 Scence Control Touch Button and fits with 86*86 junction box and replaces the existing wall switch. It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-962G by ZigBee Remote Control, Smart Phone, On/Off Switch and so on. Users can manage scence/mode control with the secnce control button.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib_en` VALUES ('114', '0', 'Z962H', 'Z962H Two Scene or Mode Selector And One Switch with Touch Panel', '0006', '开关', '3', '1', 'dest', 'Z962H.jpg', 'Z962H is One Gang Touch Switch and 2 Scence Control Touch Button and fits with 86*86 junction box and replaces the existing wall switch. It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-825A by ZigBee Remote Control, Smart Phone, On/Off Switch and so on. Users can manage scence/mode control with the secnce control button.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib_en` VALUES ('115', '0', 'Z962I', 'Z962I One Scene or Mode Selector And Two Switch with Touch Panel', '0006', '开关', '3', '1', 'dest', 'Z962I.jpg', 'Z962I is Two Gang Touch Switch and 1 Scence Control Touch Button and fits with 86*86 junction box and replaces the existing wall switch. It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-825A by ZigBee Remote Control, Smart Phone, On/Off Switch and so on. Users can manage scence/mode control with the secnce control button.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib_en` VALUES ('116', '7', 'Z801WLS', 'Z801WLS Water Sensor', '', '', '', '1', '', 'Z801WLS.jpg', 'Z-801WLS is a  a water Sensor,which is used for monitoring leakage situation, and send the alarm information to the security center immediately.', '5V直流电源或2节1.5V电池', '不需要激活', '按住绑定键的同时给设备上电，LED2闪烁20次，表示复位完成，之后重新上电，Z801WLS可以重新加网。 ', '无');
INSERT INTO `modenodelib_en` VALUES ('117', '7', 'ZB05A', 'ZB05A Door Lock with Fingerprint Identification', '0101', '开关锁', '3', '1', 'dest', 'ZB05A.jpg', 'Z-B05A can control lock/unlock of the door that applied to it including mechanical key, access code, IC card, finger print, remote access ect.. By through ZigBee wireless networking, users can monitor and control the door status, manage user ID and passwork anywhere you are. Intellectualized door lock design supports reliable access code safeguard to guarantee home surveillance safety.', '8节AA1.5V电池', '不需要激活', '1.按住网络键的同时给设备上电，上电完请不要立即松开按键，等到网络灯开始快速闪烁，提示复位成功；2.重新上电，ZB05A可以开始重新加网。', '1、初次使用时，需要注册zigbee：在门打开的状态下，打开内侧用（主锁身）的电池盖和外盒，在右侧下端按“锁卡注册键”。然后再按下面的“ZB注册键”（靠上的按键）。按“*”键，可听到“哔呖呖”的设置音，设置结束。2、默认都为加密的方式，第一次使用时，需要在app中→设备管理模块→进入门锁的设置页面，设置新的用户和密码，才可以对锁进行开关控制。门锁默认有6个空用户，注册新用户前，需要先将这些用户删除。超级密码默认为：123456.usercode即用户名为1-254的数字。');
INSERT INTO `modenodelib_en` VALUES ('118', '4', 'Z801TX', 'Z801TX TX Switch Board', '0006', '开关', '3', '1', 'source', 'Z801TX.jpg', 'Z801TX is used as a end device in the zigBee network,which doesn\'t allow other devices to be its child device.It can be extended with five buttons. When the button is trigged，it can send commands to bound device and control its on/off at the detection of a signal.', '无', '无', '无', '无');
INSERT INTO `modenodelib_en` VALUES ('119', '7', 'Z311J', 'Z311J Window Sensor with On/Off Switch', '0006', '开关', '3', '1', 'source', 'Z311J.jpg', 'Z-311J is a window/door sensor. When window/door status is changed, it can control its bound device according to the configurations. Z-302J also works as a detection sensor of the security system- when window/door is open or closed, it also notifies security center with the alarm messages, while sends the information of normal status to CIE when the window/door is closed.', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', 'Z311J的本体与门磁的磁铁部分别安装在门或窗户的缝隙的两边，当门或窗被打开，它将发出报警信息给安防中心设备( 即CIE )，当门或窗被关闭好，它会发送出状态恢复正常的信息给CIE。该设备也可以用于冰箱、抽屉等处，用于检测冰箱或抽屉等是否被打开。需要注意防水。');
INSERT INTO `modenodelib_en` VALUES ('120', '0', 'ZC07', 'ZC07 Dimmable LED Bulb', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'ZC07.jpg', 'ZC07 is a robust ZigBee enabled dimmable LED bulb. It can achieve 256 dimmer light levels and can be controlled wirelessly. By methods such as binding devices, wireless remote control or software operation, it can control light on/off and dimmer. As a 5W bulb, ZC07 applies constant current control mode withthe input voltage from AC 100V to 240V. The inputs of 100V to 240V can achieve the same lighting effects.', '100～240V的交流电源', '不需要激活', '同时长按测试键和绑定键5s，5s时间到后指示灯闪10次后就恢复出厂设置成功，此时就可以重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('121', '7', 'Z311W', 'Z311W Water Sensor', null, null, null, '1', '', 'Z311W.jpg', 'Z311W is a water sensor,which is used for monitoring leakage situation and sending the alarm information to the security center immediately.', '无需外加电源,产品使用内部的3V钮扣电池供电。', '同时按住绑定键和辅助键，看到红色指示灯灯闪烁2次，此时松开按键。', '同时按住绑定键和辅助键，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('122', '6', 'Z811B', 'Z811B Curtain Controller (2- Gang)', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z811B.jpg', 'Z811B is aTwo Gang curtain controller. It can control the curtains on/off and level through its own key, bound devices or software. ', '100-240V AC 50/60HZ电源供电', '不需要激活', '方法一：按住“绑定键（logo旁的小按钮）”上电，指示灯快闪，表示恢复出厂设置完成，再次重新上电后即可。方法二：长按住“绑定键（logo旁的小按钮）”键的15秒待指示灯闪烁一次（此间指示灯3秒、10秒、15秒各闪烁一次），松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，自动重启就可以重新加网了。', 'Lin接火线，N线是零线接电机的零线（一般为蓝线）。EP1：Lout1接电机正转输入（一般为棕线)，Lout2接电机反转输入（一般为黑线）。EP2：Lout3接电机正转输入（一般为棕线)，Lout4接电机反转输入（一般为黑线）。在设备执行窗帘控制功能前,都应先进行“学习上下限”的学习，学习方法请详见设备说明书或在app的设备管理模块，该设备的设置页面进行学习。');
INSERT INTO `modenodelib_en` VALUES ('123', '0', 'Z825I', 'Z825I Touch Panel Programmable Scene Selector (6-Gang)', '', '', '', '1', '', 'Z825I.jpg', 'As a Scene Selector & Mode Selector，Z825I is equipped with six touch buttons. Each can be configured to add scene or control mode (each button can at most at one scene or configure four modes.)', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib_en` VALUES ('124', '0', 'Z825J', 'Z825J Touch Panel 3-Gang Switch with Power Meter and 3-Gang Programmable Scene Selector', '0006', '开关', '3', '1', 'dest', 'Z825J.jpg', 'Z825J is a three-gang touch switch and three-gang Scene Selector,which can be directly installed in 86 junction box to replace the normal wall switch.It monitors and calculates Current, Voltage, Power and Engery. Users can control Z-825J by ZigBee Remote Control, Smart Phone, On/Off Switch and so on. Users can manage scence/mode control with the secnce control button.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib_en` VALUES ('125', '0', 'Z825Q', 'Z825QTouch Panel Switch with Power Meter (3-Gang)', '0006', '开关', '3', '1', 'dest', 'Z825Q.jpg', 'Z825Q is three-gang local switch controller with single livewire for power. It can control its on/off through being bound with  devices with on/off function to control it or through its own keys. Its three-gang on/off is controlled by three keys.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络青绿色指示灯闪烁一次（期间网络青绿色指示灯闪烁4次，其中刚按下闪一次，3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络青绿色指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会进入睡眠模式，短按绑定键Z825Q可重新加网。 ', 'Z825Q具有单路输入，三路输出的能力，接线时，将一种线（N或L，来自市电）接入输入口，然后在三路输出口引出同种类型的输出线（给设备供电），将插头接入到AC  100-240V的电源，给设备上电。');
INSERT INTO `modenodelib_en` VALUES ('126', '1', 'Z816I', 'Z816I  China Type Wall Socket with Power Meter (5-pin)', '0006', '开关', '3', '1', 'dest', 'Z816I.jpg', 'Z816I is a China type wireless current-detection smart wall socket, which can be directly installed in 86 junction box to replace the traditional wall socket. Its on/off can be controlled with its own keys or through its bound devices and software.Via software, users are able to monitor the load current, voltage and power and kilo-watt-hour consumption of the attached appliance.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('127', '5', 'Z817D', 'Z817D  Ceiling Motion Detector', '0006', '开关', '3', '1', 'source', 'Z817D.jpg', 'Z817D has the on/off switch by the infrared detection.When it detects the IR of moving objects,it can control on/off of bound devices.', 'AC100-240V 50/60HZ供电', '不需要激活', '给设备上电，同时按住“按键1”和“按键2”5秒；复位完成则led闪烁30次后自动重新请求加网；', '无');
INSERT INTO `modenodelib_en` VALUES ('128', '11', 'Z800R', 'Z800R Plug Repeater with Outlet', null, null, null, '1', null, 'Z800R.jpg', 'Z800R is a Router Repeaters with a socket,which allow other devices as his children.It can extend the communication distance for other devices.', 'AC 100-250V的电源', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重新上电就可以加入新的网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('129', '1', 'Z809C', 'Z809C Plug Repeater with Backup Battery', null, null, null, '1', null, 'Z809C.jpg', 'Z809C is a Router Repeaters with a socket,which allow other devices as his children.It can extend the communication distance for other devices.', 'AC 100-240V的电源或电池供电（内置电池）', '不需要激活', '长按绑定键15s，15s时间到状态闪1次后放开绑定键，两秒内短按绑定键就状态灯闪烁10次，并自动复位。其中长按绑定键3s，10s和15s的时候状态灯都会依次闪烁一次，以提示所按的时间长短。', '无');
INSERT INTO `modenodelib_en` VALUES ('130', '1', 'Z809D', 'Z809D Plug Repeater with Power Amplifier & Backup Battery', '', '', '', '1', '', 'Z809D.jpg', 'Z809D is a Router Repeaters with a socket,which allow other devices as his children.It can extend the communication distance for other devices.', 'AC 100-240V的电源或电池供电（内置电池）', '不需要激活', '长按绑定键15s，15s时间到指示闪1次后放开绑定键，两秒内短按绑定键就指示灯闪烁10次，并自动重启设备（如果仅仅用电池供电不能重启）。', '无');
INSERT INTO `modenodelib_en` VALUES ('131', '9', 'Z501AE3ED', 'Z501A 4-Key Remote Controller with Arm/Disarm, On/Off, & Panic Button', '0006:0008', '开关:调级控制', '3', '1', 'source', 'Z501A.jpg', 'As a remote control device, Z501A can be bound with on/off switch or dimmer devices for users to control the devices wirelessly. Z-501A also comes with an emergency button for users to call for help in emergency.', '3V CR2032纽扣电池', '右侧开关off断电，重新上电后即可激活设备。', '按住“开关键” 的同时给设备上电，开始恢复出厂值，指示灯共闪烁20次，之后重新操作组合键就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('132', '9', 'Z501BE3ED', 'Z501B 4-Key Remote Controller with On/Off Switch (2-Set)', '0006', '开关', '3', '1', 'source', 'Z501B.jpg', 'Z510B is a remote control,which can be bound to control on/off of other devices.', '3V CR2032纽扣电池', '右侧开关off断电，重新上电后即可激活设备。', '按住“ON2键” 的同时给设备上电，开始恢复出厂值，指示灯共闪烁20次，之后重新操作组合键就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('133', '9', 'Z501CE3ED', 'Z501C 4-Key Remote Controller with Level Control & On/Off Switch', '0006:0008', '开关:调级控制', '3', '1', 'source', 'Z501C.jpg', 'As a remote control device, Z501C can be bound with on/off or dimmer devices for users to control the devices wirelessly. ', '3V CR2033纽扣电池', '右侧开关off断电，重新上电后即可激活设备。', '按住“ ON/OFF1键” 的同时给设备上电，开始恢复出厂值，指示灯共闪烁20次，之后重新操作组合键就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('134', '7', 'ZB02I', 'ZB02I  Emergency Push Button', '', '', '', '1', '', 'ZB02E.jpg', 'ZB02I is an emergency push button working as a detection device in the security system. It can be put on the wall or anyplace in the room .When users in danger are in need of emergency assistance, users can press the alarm button for ZB02I to immediately send out an alarm message to the security center, which will notify the siren to make sound or light to alert family members to offer immediate help. ', 'AAA电池供电', '短按绑定键，若设备仍在网络状态，则绿色指示灯闪烁5次，表示激活成功。', '按住绑定键的同时给设备上电，进行恢复出厂设置，看到指示灯闪烁，表示恢复完成。', '无');
INSERT INTO `modenodelib_en` VALUES ('137', '0', 'Z802', 'Z802 Two Way Switching Load module', '0006', '开关', '3', '1', 'dest', 'Z802.jpg', 'Z802 can be bound with on/off switch for users to control the devices wirelessly.User can switch on/off the appliances attached to it through mechnical switch or wirelessly. Users may also use ZiG-BUTLER to check the current status of the device and make corresponding control.', '100-240VAC 50/60HZ 电源供电', '不需要激活', '按住绑定键的同时，给设备上电，开始恢复出厂值，直到LED1指示灯开始闪烁，表示恢复完成。之后重新上电，Z802可以重新加网了', '无');
INSERT INTO `modenodelib_en` VALUES ('138', '4', 'ZB02J', 'ZB02J Wireless Scene & Mode Selector (3-Key)', '', '', '', '1', '', 'ZB02C.jpg', 'ZB02J is a three-gang scene button, which can be bound with three scenes for users to control them.', '无需外加电源,产品使用内部的2节7号电池供电', '短按绑定键（led网络灯闪5下）', '按住绑定键不松开，装入电池，进行恢复出厂设置，看到状态灯闪烁，表示恢复完成，然后松开按键，拿出电池', '无');
INSERT INTO `modenodelib_en` VALUES ('139', '0', 'Z815A', 'Z815A Wall Switch with Power Meter (1-Gang)', '0006', '开关', '3', '1', 'dest', 'Z815I.jpg', 'Z815A is a one-gang Output switch,which can be directly installed in 86 junction box to replace the existing wall switch.It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-815A by ZigBee Remote Control, Smart Phone, On/Off Switch and other methods.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('140', '0', 'Z815B', 'Z815B Wall Switch with Power Meter (2-Gang)', '0006', '开关', '3', '1', 'dest', 'Z815J.jpg', 'Z815B is a two-gang Output switch,which can be directly installed in 86 junction box to replace the existing wall switch.It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-815B by ZigBee Remote Control, Smart Phone, On/Off Switch and other methods.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('141', '0', 'Z815C', 'Z815C Wall Switch with Power Meter (3-Gang)', '0006', '开关', '3', '1', 'dest', 'Z815K.jpg', 'Z815C is a three-gang Output switch,which can be directly installed in 86 junction box to replace the existing wall switch.It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-815C by ZigBee Remote Control, Smart Phone, On/Off Switch and other methods.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('142', '0', 'Z815D', 'Z815D  Wall Dimmer Switch with Power Meter (1-Gang)', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z815L.jpg', 'Z815D is a one-gang Output dimmer which can control on/off and dimmer and be directly installed in 86 junction box to replace the existing wall switch.It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-815D by ZigBee Remote Control, Smart Phone, On/Off Switch and other methods.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('143', '0', 'Z815E', 'Z815E Wall Dimmer Switch with Power Meter (2-Gang)', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z815M.jpg', 'Z815E is a two-gang Output dimmer which can control on/off and dimmer and be directly installed in 86 junction box to replace the existing wall switch.It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-815D by ZigBee Remote Control, Smart Phone, On/Off Switch and other methods.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('144', '0', 'Z826C', 'Z826C Touch Panel Switch with Power Meter (3-Gang+1 Live Wire Power Supply)', '0006', '开关', '3', '1', 'dest', 'Z825Q.jpg', 'Z826C is three-gang local switch controller with single livewire for power. It can control its on/off through being bound with devices with on/off function to control it or through its own keys. Its three-gang on/off is controlled by three keys.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络青绿色指示灯闪烁一次（期间网络青绿色指示灯闪烁4次，其中刚按下闪一次，3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络青绿色指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会进入睡眠模式，短按绑定键Z826C可重新加网。 ', 'Z826C具有单路输入，三路输出的能力，接线时，将一种线（N或L，来自市电）接入输入口，然后在三路输出口引出同种类型的输出线（给设备供电），将插头接入到AC  100-240V的电源，给设备上电。');
INSERT INTO `modenodelib_en` VALUES ('145', '11', 'Z206', 'Z206 Cloud-Based Wireless Smart Home Controller', '', '', '', '1', '', 'Z206.jpg', 'As the main controller of Netvox Cloud-based Wireless Smart Home System, Z-206 is a perfect combination of cloud services, Wifi technology, and ZigBee Home Automation solutions. Z-206 enables users to monitor their lights, curtains, and home appliances via smart home app and Wifi connection, and users can easily monitor their home with 2G/3G network even they are far away.', '使用DC 12V  1.5A电源适配器，接入100-220V的电源', '不需要激活', '详见206说明书', 'Z206作为奈伯思智能家居系统的核心网关，率先实现云端技术、WiFi移动联网技术与ZigBee智能家居物联网术的完美结合，让用户只需要通过手机APP连接Wifi即可控制家里的灯光、窗帘、各种电器等设备的开关，出门在外通过2G/3G网络可监控家中的一切变化。');

/*新建serverlib 服务器列表 2014.12.10*/
CREATE TABLE `serverlib` (
`id`  int(20) NOT NULL AUTO_INCREMENT ,
`servername`  varchar(100) NULL ,
`serverip`  varchar(50) NULL ,
`maxcshc`  int(100) NULL ,
`serverline`  varchar(100) NULL ,
`warnnum`  int(100) NULL ,
`warnmail`  varchar(200) NULL ,
`serverdescription`  varchar(200) NULL ,
PRIMARY KEY (`id`)
)
;

/*创建英文版ep名称表*/
CREATE TABLE `modeeplib_en` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nodeId` bigint(20) DEFAULT NULL,
  `deviceId` varchar(20) DEFAULT NULL,
  `internelModelId` varchar(50) DEFAULT NULL,
  `deviceName` varchar(200) DEFAULT NULL,
  `clusterId` varchar(200) DEFAULT NULL,
  `clusterName` varchar(100) DEFAULT NULL,
  `picName` varchar(100) DEFAULT NULL,
  `destType` varchar(4) DEFAULT NULL,
  `deviceType` varchar(20) DEFAULT NULL,
  `deviceTypeV2` bigint(20) DEFAULT '1',
  `ep` varchar(50) DEFAULT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `Groupable` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `nodeId` (`nodeId`) USING BTREE,
  KEY `deviceId` (`deviceId`) USING BTREE,
  KEY `internelModelId` (`internelModelId`) USING BTREE,
  KEY `deviceName` (`deviceName`) USING BTREE,
  KEY `destType` (`destType`) USING BTREE,
  KEY `deviceType` (`deviceType`) USING BTREE,
  KEY `clusterId` (`clusterId`) USING BTREE,
  KEY `Groupable` (`Groupable`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=242 DEFAULT CHARSET=utf8;

/*添加英文版ep名称*/
INSERT INTO `modeeplib_en` VALUES ('2', '2', '0009', 'Z815I-1', 'Z815I Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('3', '3', '0009', 'Z815K-1', 'Z815K 1st Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('4', '3', '0009', 'Z815K-2', 'Z815K 2nd Power Switch', '0006', '开关', null, '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('5', '3', '0009', 'Z815K-3', 'Z815K 3rd Power Switch', '0006', '开关', null, '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib_en` VALUES ('6', '4', '0100', 'Z806-1', 'Z806 1st Output', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('7', '4', '0100', 'Z806-2', 'Z806 2nd Output', '0006', '开关', null, '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('8', '5', '0000', 'ZB02A-1', 'ZB02A Wall Switch', '0006', '开关', null, '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('10', '7', '0000', 'Z503-2', 'Z503 Switches', '0006:0008', '开关:调级控制', null, '1', 'source', '1', '0F', '', '0');
INSERT INTO `modeeplib_en` VALUES ('11', '7', '0006', 'Z503-1', 'Z503 security Keypads', '', '', null, '3', '', '1', '0A', '', '0');
INSERT INTO `modeeplib_en` VALUES ('12', '10', '0009', 'Z805A-1', 'Z805A Power Switch', '0006', '开关', null, '3', 'dest', '1', '0A', null, '1');
INSERT INTO `modeeplib_en` VALUES ('13', '11', '0009', 'Z805B-1', 'Z805B Power Switch', '0006', '开关', '', '3', 'dest', '1', '0A', '', '1');
INSERT INTO `modeeplib_en` VALUES ('14', '12', '0009', 'Z810B-1', 'Z810B Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('15', '13', '0100', 'Z811-1', 'Z811 1st Output', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('16', '13', '0100', 'Z811-2', 'Z811 2nd Output', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('17', '13', '0100', 'Z811-3', 'Z811 3rd Output', '0006', '开关', '', '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib_en` VALUES ('18', '13', '0100', 'Z811-4', 'Z811 4th Output', '0006', '开关', '', '3', 'dest', '1', '04', '', '1');
INSERT INTO `modeeplib_en` VALUES ('19', '14', '0009', 'Z815J-1', 'Z815J 1st Power Switch', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('20', '14', '0009', 'Z815J-2', 'Z815J 2nd Power Switch', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('21', '15', '0101', 'Z815L-1', 'Z815L Dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('22', '16', '0101', 'Z815M-1', 'Z815M 1st Dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('23', '16', '0101', 'Z815M-2', 'Z815M 2nd Dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('24', '17', '0009', 'Z817A-1', 'Z817A Power Switch', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('25', '18', '0101', 'Z817B-1', 'Z817B Dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('26', '19', '0002', 'Z817C-1', 'Z817C Power Switch', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('27', '20', '0009', 'Z825C-1', 'Z825C 1st Power Switch', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('28', '20', '0009', 'Z825C-2', 'Z825C 2nd Power Switch', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('29', '20', '0009', 'Z825C-3', 'Z825C 3rd Power Switch', '0006', '开关', '', '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib_en` VALUES ('30', '21', '0101', 'Z825E-1', 'Z825E 1st Dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('31', '21', '0101', 'Z825E-2', 'Z825E 2nd Dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('32', '22', '0101', 'ZC06-1', 'ZC06 Dimmer light', '0006:0008', '开关:调级控制', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('33', '23', '0009', 'Z800-1', 'Z800 Power Plug', '0006', '开关', '', '3', 'dest', '1', '0A', '', '1');
INSERT INTO `modeeplib_en` VALUES ('34', '24', '0002', 'Z803-1', 'Z803 1st Power Plug', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('35', '24', '0002', 'Z803-2', 'Z803 2nd Power Plug', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('36', '24', '0002', 'Z803-3', 'Z803 3rd Power Plug', '0006', '开关', '', '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib_en` VALUES ('37', '24', '0002', 'Z803-4', 'Z803 4th Power Plug', '0006', '开关', null, '3', 'dest', '1', '04', null, '1');
INSERT INTO `modeeplib_en` VALUES ('38', '24', '000D', 'Z803-5', 'Z803 Power Consumption Awareness', '0006', '开关', null, '3', '', '1', '05', null, '0');
INSERT INTO `modeeplib_en` VALUES ('39', '25', '0009', 'Z808A-1', 'Z808A Power Plug', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('40', '26', '0101', 'Z808B-1', 'Z808B Dimmable Power Plug', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('41', '27', '0009', 'Z809A-1', 'Z809A Power Plug', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('42', '28', '0101', 'Z809B-1', 'Z809B Dimmable Power Plug', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('43', '29', '0009', 'Z816B-1', 'Z816B US type Power Plug', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('44', '30', '0009', 'Z816G-1', 'Z816G UK type Power Plug', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('45', '31', '0009', 'Z816H-1', 'Z816H China type Power Plug', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('46', '32', '0302', 'Z711-1', 'Z711 Temperature and Humidity Sensor', null, null, null, null, null, '1', '0A', null, '0');
INSERT INTO `modeeplib_en` VALUES ('47', '33', '0302', 'Z712-1', 'Z712 Temperature and Humidity Sensor', '', '', '', '', '', '1', '0A', '', '0');
INSERT INTO `modeeplib_en` VALUES ('48', '34', '0302', 'Z713-1', 'Z713 Temperature and Humidity Sensor', '', '', null, '', '', '1', '0A', null, '0');
INSERT INTO `modeeplib_en` VALUES ('49', '35', '0302', 'Z716A-1', 'Z716A Temperature and Humidity Sensor', '', '', '', '', '', '1', '0A', '', '0');
INSERT INTO `modeeplib_en` VALUES ('50', '36', '0302', 'Z716B-1', 'Z716B Temperature Sensor', null, null, '', null, null, '1', '0A', '', '0');
INSERT INTO `modeeplib_en` VALUES ('51', '37', '0103', 'Z302B-1', 'Z302B Light Switch', '0006', '开关', null, '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('52', '38', '0104', 'Z302H-1', 'Z302H Light Sensor ', '0006:0008', '开关:调级控制', null, '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('53', '39', '0106', 'Z302G-1', 'Z302G Dimmer Switch', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('54', '40', '0103', 'Z311B-1', 'Z311B Light Switch', '0006', '开关', null, '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('55', '41', '0104', 'Z311H-1', 'Z311H Light Sensor ', '0006:0008', '开关:调级控制', null, '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('56', '42', '0106', 'Z311G-1', 'Z311G Dimmer Switch', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('57', '44', '0000', 'ZB02B-1', 'ZB02B Wall Switch 1st key', '0006', '开关', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('58', '44', '0000', 'ZB02B-2', 'ZB02B Wall Switch 2nd key', '0006', '开关', '', '3', 'source', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('59', '45', '0000', 'ZB02C-1', 'ZB02C Wall Switch 1st key', '0006', '开关', '', '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('60', '45', '0000', 'ZB02C-2', 'ZB02C Wall Switch 2nd key', '0006', '开关', '', '3', 'source', '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('61', '45', '0000', 'ZB02C-3', 'ZB02C Wall Switch 3rd key', '0006', '开关', '', '3', 'source', '1', '03', '', '0');
INSERT INTO `modeeplib_en` VALUES ('62', '46', '0104', 'ZB02F-1', 'ZB02F Wall Dimmer', '0006:0008', '开关:调级控制', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('63', '48', '0000', 'ZB01B-1', 'ZB01B On Off Switch', '0006', '开关', null, '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('64', '49', '0402', 'ZB01C-1', 'ZB01C Motion Detector', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('65', '52', '0000', 'ZB11B-1', 'ZB11B On Off Switch', '0006', '开关', null, '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('66', '53', '0402', 'ZB11C-1', 'ZB11C Motion Detector', null, null, '', null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('67', '55', '0200', 'Z815N-1', 'Z815N Shade Control Switch', '0006:0008', '开关:调级控制', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('68', '56', '0200', 'ZD01B-1', 'ZD01B First Curtain Controller', '0006:0008', '开关:调级控制', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('69', '56', '0200', 'ZD01B-2', 'ZD01B Second Curtain Controller', '0006:0008', '开关:调级控制', null, '3', 'dest', '1', '02', null, '1');
INSERT INTO `modeeplib_en` VALUES ('71', '47', '0402', 'ZB01A-1', 'ZB01A Motion Detector', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('72', '50', '0107', 'ZB01D-1', 'ZB01D Occupancy Sensor', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('73', '51', '0402', 'ZB11A-1', 'ZB11A Motion Detector', '', '', null, '', '', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('74', '49', '0000', 'ZB01C-2', 'ZB01C On Off Switch', '0006', '开关', null, '3', 'source', '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('75', '49', '0302', 'ZB01C-3', 'ZB01C Temperature Sensor', null, null, null, null, null, '1', '03', null, '0');
INSERT INTO `modeeplib_en` VALUES ('76', '53', '0000', 'ZB11C-2', 'ZB11C On Off Switch', '0006', '开关', null, '3', 'source', '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('77', '53', '0302', 'ZB11C-3', 'ZB11C Temperature Sensor', null, null, '', null, null, '1', '03', null, '0');
INSERT INTO `modeeplib_en` VALUES ('78', '54', '0107', 'ZB11D-1', 'ZB11D Occupancy Sensor', '', '', null, '', '', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('79', '57', '0402', 'Z302A-1', 'Z302A Window Intrusion Sensor', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('80', '58', '0402', 'Z302C-1', 'Z302C Window Glass Break Sensor and Intrusion Sensor', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('81', '59', '0402', 'Z302D-1', 'Z302D Panic Button', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('82', '60', '0402', 'Z302E-1', 'Z302E Asset tag', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('83', '61', '0000', 'Z302J-1', 'Z302J on off switch', '0006', '开关', '', '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('84', '61', '0402', 'Z302J-2', 'Z302J Window Intrusion Sensor', '', '', '', '', '', '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('85', '62', '0402', 'Z306D-1', 'Z306D Panic Button and Inductive Charging Base', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('86', '63', '0402', 'Z311A-1', 'Z311AWindow Intrusion Sensor', null, '', '', '', '', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('87', '64', '0402', 'Z311C-1', 'Z311C Window Glass Break Sensor and Intrusion Sensor', null, '', '', '', '', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('88', '65', '0401', 'Z312-1', 'Z312 Door Bell Button', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('89', '66', '0402', 'Z307-1', 'Z307 Fall Sensor and Inductive Charging Base', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('90', '67', '0402', 'Z308-1', 'Z308 Panic Button', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('91', '68', '0402', 'ZA01A-1', 'ZA01A Smoke Detector with Heat Sensor', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('92', '69', '0402', 'ZA01B-1', 'ZA01B Gas Detector with Heat Sensor', '', '', null, '', '', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('93', '70', '0402', 'ZA01C-1', 'ZA01C CO Detector', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('94', '71', '0402', 'ZA01D-1', 'ZA01D Liquefied Gas Detector with Heat Sensor', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('95', '72', '0402', 'ZA02E-1', 'ZA02E Photoelectric Smoke Detector with Heat Sensor', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('96', '73', '0002', 'ZA10-1', 'ZA10 Gas or Water Keeper', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('97', '74', '000A', 'ZB05-1', 'ZB05 Door Lock', '0101', '开关锁', null, '3', 'dest', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('98', '75', '0401', 'ZB02E-1', 'ZB02E Door Bell', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('99', '76', '0402', 'Z311E-1', 'Z311E Asset tag', '', '', null, '', '', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('100', '77', '0403', 'Z602A-1', 'Z602A Siren', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('101', '78', '0403', 'Z602B-1', 'Z602B Siren with GSM', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('102', '79', '0403', 'Z601A-1', 'Z601A Siren', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('103', '80', '000D', 'Z821-1', 'Z821 1st Power Consumption Awareness', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('104', '80', '000D', 'Z821-2', 'Z821 2nd Power Consumption Awareness', null, null, null, null, null, '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('105', '80', '000D', 'Z821-3', 'Z821 3rd Power Consumption Awareness', null, null, null, null, null, '1', '03', null, '0');
INSERT INTO `modeeplib_en` VALUES ('106', '80', '000D', 'Z821-4', 'Z821 4th Power Consumption Awareness', null, null, null, null, null, '1', '04', null, '0');
INSERT INTO `modeeplib_en` VALUES ('107', '80', '000D', 'Z821-5', 'Z821 5th Power Consumption Awareness', null, null, null, null, null, '1', '05', null, '0');
INSERT INTO `modeeplib_en` VALUES ('108', '80', '000D', 'Z821-6', 'Z821 6th Power Consumption Awareness', null, null, null, null, null, '1', '06', null, '0');
INSERT INTO `modeeplib_en` VALUES ('109', '80', '000D', 'Z821-7', 'Z821 7th Power Consumption Awareness', null, null, null, null, null, '1', '07', null, '0');
INSERT INTO `modeeplib_en` VALUES ('110', '80', '000D', 'Z821-8', 'Z821 All Power Consumption Awareness', null, null, null, null, null, '1', '08', null, '0');
INSERT INTO `modeeplib_en` VALUES ('111', '81', '0001', 'Z501A-1', 'Z501A dimmer Switch', '0006:0008', '开关:调级控制', null, '3', 'source', '1', '0A', null, '0');
INSERT INTO `modeeplib_en` VALUES ('112', '81', '0401', 'Z501A-2', 'Z501A security Keypad', '', '', null, '', '', '1', '0B', null, '0');
INSERT INTO `modeeplib_en` VALUES ('113', '82', '0000', 'Z501B-1', 'Z501B 1st Switch', '0006', '开关', null, '3', 'source', '1', '0A', null, '0');
INSERT INTO `modeeplib_en` VALUES ('114', '82', '0000', 'Z501B-2', 'Z501B 2nd Switch', '0006', '开关', null, '3', 'source', '1', '0B', null, '0');
INSERT INTO `modeeplib_en` VALUES ('115', '83', '0001', 'Z501C-1', 'Z501C dimmer Switch', '0006:0008', '开关:调级控制', null, '3', 'source', '1', '0A', null, '0');
INSERT INTO `modeeplib_en` VALUES ('116', '83', '0000', 'Z501C-2', 'Z501C on Off Switch', '0006', '开关', null, '3', 'source', '1', '0B', null, '0');
INSERT INTO `modeeplib_en` VALUES ('117', '84', '0008', 'Z210C-1', 'Z210C Infrared Adpt', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('118', '85', '0008', 'Z211-1', 'Z211 Bidirectional ZigBeeIR Adpt', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('119', '86', '0007', 'Z203-1', 'Z203 CWSHC', null, null, null, null, null, '1', '0A', null, '0');
INSERT INTO `modeeplib_en` VALUES ('120', '87', '0007', 'Z103AC-1', 'Z103AC USB', null, null, null, null, null, '1', '0A', null, '0');
INSERT INTO `modeeplib_en` VALUES ('121', '88', '0007', 'Z103AR-1', 'Z103AR USB', null, null, null, null, null, '1', '0A', null, '0');
INSERT INTO `modeeplib_en` VALUES ('122', '89', '0400', 'Z201B-1', 'Z201B ZigBee802.15.4 router andCIE', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('123', '90', '0400', 'Z201C-1', 'Z201C ZigBee802.15.4 Coord', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('124', '91', '0008', 'Z201R HA-1', 'Z201R ZigBee TCP/IP Gateway', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('125', '92', '0008', 'Z201R-1', 'Z201 ZigBee 802.15.4 Router', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('126', '93', '0002', 'Z812A-1', 'Z812A output', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('127', '93', '0000', 'Z812A-2', 'Z812A 1st Switch', '0006', '开关', null, '3', 'source', '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('128', '93', '0000', 'Z812A-3', 'Z812A 2nd Switch', '0006', '开关', null, '3', 'source', '1', '03', null, '0');
INSERT INTO `modeeplib_en` VALUES ('129', '93', '0000', 'Z812A-4', 'Z812A 3rd Switch', '0006', '开关', null, '3', 'source', '1', '04', null, '0');
INSERT INTO `modeeplib_en` VALUES ('130', '94', '0000', 'Z812B-1', 'Z812B 1st Switch', '0006', '开关', null, '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('131', '94', '0000', 'Z812B-2', 'Z812B 2nd Switch', '0006', '开关', null, '3', 'source', '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('132', '94', '0000', 'Z812B-3', 'Z812B 3rd Switch', '0006', '开关', null, '3', 'source', '1', '03', null, '0');
INSERT INTO `modeeplib_en` VALUES ('133', '94', '0000', 'Z812B-4', 'Z812B 4th Switch', '0006', '开关', null, '3', 'source', '1', '04', null, '0');
INSERT INTO `modeeplib_en` VALUES ('134', '94', '0104', 'Z812B-5', 'Z812B 1st Dimmer Switch', '0006:0008', '开关:调级控制', null, '3', 'source', '1', '05', null, '0');
INSERT INTO `modeeplib_en` VALUES ('135', '94', '0104', 'Z812B-6', 'Z812B 2nd Dimmer Switch', '0006:0008', '开关:调级控制', null, '3', 'source', '1', '06', null, '0');
INSERT INTO `modeeplib_en` VALUES ('136', '95', '0402', 'Z801TXB-1', 'Z801TXB 1st Detector', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('137', '95', '0402', 'Z801TXB-2', 'Z801TXB 2nd Detector', '', '', '', '', '', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('138', '95', '0402', 'Z801TXB-3', 'Z801TXB 3rd Detector', '', '', '', '', '', '1', '03', '', '0');
INSERT INTO `modeeplib_en` VALUES ('139', '95', '0402', 'Z801TXB-4', 'Z801TXB 4th Detector', '', '', '', '', '', '1', '04', '', '0');
INSERT INTO `modeeplib_en` VALUES ('140', '95', '0402', 'Z801TXB-5', 'Z801TXB 5th Detector', '', '', '', '', '', '1', '05', '', '0');
INSERT INTO `modeeplib_en` VALUES ('141', '96', '0002', 'Z801RX', 'Z801RX output', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('142', '101', '0009', 'Z825A-1', 'Z825APower Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('143', '102', '0009', 'Z825B-1', 'Z825B 1st Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('144', '102', '0009', 'Z825B-2', 'Z825B 2nd Power Switch', '0006', '开关', null, '3', 'dest', '1', '02', null, '1');
INSERT INTO `modeeplib_en` VALUES ('145', '103', '0101', 'Z825D-1', 'Z825D Dimmer Switch', '0006:0008', '开关:调级控制', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('146', '100', '0400', 'Z508-1', 'Z508 In Home Display', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('147', '104', '0302', 'Z725A-1', 'Z725A Temperature and Humidity Sensor', null, null, null, null, null, '1', '0A', null, '0');
INSERT INTO `modeeplib_en` VALUES ('148', '106', '0008', 'Z725R-1', 'Z725R Outdoor Range Extender', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('149', '107', '0009', 'Z962A-1', 'Z962A Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('150', '108', '0009', 'Z962B-1', 'Z962B 1st Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('151', '108', '0009', 'Z962B-2', 'Z962B 2nd Power Switch', '0006', '开关', null, '3', 'dest', '1', '02', null, '1');
INSERT INTO `modeeplib_en` VALUES ('152', '109', '0009', 'Z962C-1', 'Z962C 1st Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('153', '109', '0009', 'Z962C-2', 'Z962C 2nd Power Switch', '0006', '开关', null, '3', 'dest', '1', '02', null, '1');
INSERT INTO `modeeplib_en` VALUES ('154', '109', '0009', 'Z962C-3', 'Z962C 3rd Power Switch', '0006', '开关', null, '3', 'dest', '1', '03', null, '1');
INSERT INTO `modeeplib_en` VALUES ('155', '110', '0004', 'Z962D-1', 'Z962D Scene or Mode Selector', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('156', '111', '0004', 'Z962E-1', 'Z962E 1st Scene or Mode Selector', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('157', '111', '0004', 'Z962E-2', 'Z962E 2nd Scene or Mode Selector', null, null, null, null, null, '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('158', '112', '0004', 'Z962F-1', 'Z962F 1st Scene or Mode Selector', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('159', '112', '0004', 'Z962F-2', 'Z962F 2nd Scene or Mode Selector', null, null, null, null, null, '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('160', '112', '0004', 'Z962F-3', 'Z962F 3rd Scene or Mode Selector', null, null, null, null, null, '1', '03', null, '0');
INSERT INTO `modeeplib_en` VALUES ('161', '113', '0009', 'Z962G-1', 'Z962G Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('162', '113', '0004', 'Z962G-2', 'Z962G Scene or Mode Selector', null, null, null, null, null, '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('163', '114', '0009', 'Z962H-1', 'Z962H Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('164', '114', '0004', 'Z962H-2', 'Z962H 1st Scene or Mode Selector', null, null, null, null, null, '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('165', '114', '0004', 'Z962H-3', 'Z962H 2nd Scene or Mode Selector', null, null, null, null, null, '1', '03', null, '0');
INSERT INTO `modeeplib_en` VALUES ('166', '115', '0004', 'Z962I-1', 'Z962I Scene or Mode Selector', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('167', '115', '0009', 'Z962I-2', 'Z962I 1st Power Switch', '0006', '开关', null, '3', 'dest', '1', '02', null, '1');
INSERT INTO `modeeplib_en` VALUES ('168', '115', '0009', 'Z962I-3', 'Z962I 2nd Power Switch', '0006', '开关', null, '3', 'dest', '1', '03', null, '1');
INSERT INTO `modeeplib_en` VALUES ('174', '116', '0402', 'Z801WLS-1', 'Z801WLS 1st water sensor', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('175', '116', '0402', 'Z801WLS-2', 'Z801WLS 2nd water sensor', '', '', '', '', '', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('176', '116', '0402', 'Z801WLS-3', 'Z801WLS 3rd water sensor', '', '', '', '', '', '1', '03', '', '0');
INSERT INTO `modeeplib_en` VALUES ('177', '116', '0402', 'Z801WLS-4', 'Z801WLS 4th water sensor', '', '', '', '', '', '1', '04', '', '0');
INSERT INTO `modeeplib_en` VALUES ('178', '116', '0402', 'Z801WLS-5', 'Z801WLS 5th water sensor', '', '', '', '', '', '1', '05', '', '0');
INSERT INTO `modeeplib_en` VALUES ('179', '117', '000A', 'ZB05A-1', 'ZB05A Door Lock', '0101', '开关锁', '', '3', 'dest', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('180', '118', '0000', 'Z801TX-1', 'Z801TX 1st Switch', '0006', '开关', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('181', '118', '0000', 'Z801TX-2', 'Z801TX  2nd Switch', '0006', '开关', '', '3', 'source', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('182', '118', '0000', 'Z801TX-3', 'Z801TX  3rd Switch', '0006', '开关', '', '3', 'source', '1', '03', '', '0');
INSERT INTO `modeeplib_en` VALUES ('183', '118', '0000', 'Z801TX-4', 'Z801TX  4th Switch', '0006', '开关', '', '3', 'source', '1', '04', '', '0');
INSERT INTO `modeeplib_en` VALUES ('184', '118', '0000', 'Z801TX-5', 'Z801TX  5th Switch', '0006', '开关', '', '3', 'source', '1', '05', '', '0');
INSERT INTO `modeeplib_en` VALUES ('185', '119', '0000', 'Z311J-1', 'Z311J on off switch', '0006', '开关', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('186', '119', '0402', 'Z311J-2', 'Z311J Window Intrusion Sensor', '', '', '', '', '', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('187', '120', '0101', 'ZC07-1', 'ZC07 Dimmer light', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('188', '121', '0402', 'Z311W-1', 'Z311W water sensor', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('190', '122', '0200', 'Z811B-1', 'Z811B 1st Curtain Controller', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('191', '122', '0200', 'Z811B-2', 'Z811B 2nd Curtain Controller', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('192', '123', '0004', 'Z825I-1', 'Z825I 1st Scene or Mode Selector', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('193', '123', '0004', 'Z825I-2', 'Z825I 2nd Scene or Mode Selector', '', '', '', '', '', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('194', '123', '0004', 'Z825I-3', 'Z825I 3rd Scene or Mode Selector', '', '', '', '', '', '1', '03', '', '0');
INSERT INTO `modeeplib_en` VALUES ('195', '123', '0004', 'Z825I-4', 'Z825I 4th Scene or Mode Selector', '', '', '', '', '', '1', '04', '', '0');
INSERT INTO `modeeplib_en` VALUES ('196', '123', '0004', 'Z825I-5', 'Z825I 5th Scene or Mode Selector', '', '', '', '', '', '1', '05', '', '0');
INSERT INTO `modeeplib_en` VALUES ('197', '123', '0004', 'Z825I-6', 'Z825I 6th Scene or Mode Selector', '', '', '', '', '', '1', '06', '', '0');
INSERT INTO `modeeplib_en` VALUES ('201', '124', '0004', 'Z825J-4', 'Z825J 1st Scene or Mode Selector', '', '', '', '', '', '1', '04', '', '0');
INSERT INTO `modeeplib_en` VALUES ('202', '124', '0004', 'Z825J-5', 'Z825J 2nd Scene or Mode Selector', '', '', '', '', '', '1', '05', '', '0');
INSERT INTO `modeeplib_en` VALUES ('203', '124', '0004', 'Z825J-6', 'Z825J 3rd Scene or Mode Selector', '', '', '', '', '', '1', '06', '', '0');
INSERT INTO `modeeplib_en` VALUES ('204', '124', '0009', 'Z825J-1', 'Z825J 1st Output', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('205', '124', '0009', 'Z825J-2', 'Z825J 2nd Output', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('206', '124', '0009', 'Z825J-3', 'Z825J 3rd Output', '0006', '开关', '', '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib_en` VALUES ('207', '125', '0009', 'Z825Q-1', 'Z825Q 1st Power Switch', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('208', '125', '0009', 'Z825Q-2', 'Z825Q 2nd Power Switch', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('209', '125', '0009', 'Z825Q-3', 'Z825Q 3rd Power Switch', '0006', '开关', '', '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib_en` VALUES ('210', '126', '0009', 'Z816I-1', 'Z816I China type Power Plug', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('211', '127', '0000', 'Z817D-1', 'Z817D  On Off Switch', '0006', '开关', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('212', '128', '0008', 'Z800R-1', 'Z800 Plug Router', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('213', '129', '0008', 'Z809C-1', 'Z809C Plug Router', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('214', '130', '0008', 'Z809D-1', 'Z809D Plug Router', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('215', '131', '0001', 'Z501AE3ED-1', 'Z501A dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('216', '131', '0401', 'Z501AE3ED-2', 'Z501A security Keypads', '', '', '', '', '', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('217', '132', '0000', 'Z501BE3ED-1', 'Z501B 1st Switch', '0006', '开关', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('218', '132', '0000', 'Z501BE3ED-2', 'Z501B 2nd Switch', '0006', '开关', '', '3', 'source', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('219', '133', '0001', 'Z501CE3ED-1', 'Z501C dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('220', '133', '0000', 'Z501CE3ED-2', 'Z501C security Keypads', '0006', '开关', '', '3', 'source', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('221', '134', '0402', 'ZB02I-1', 'ZB02IPanic Button', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('222', '137', '0002', 'Z802-1', 'Z802 1st Output', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('223', '137', '0002', 'Z802-2', 'Z802 2nd Output', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('225', '138', '0004', 'ZB02J-1', 'ZB02J 1st Scene or Mode Selector', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('226', '138', '0004', 'ZB02J-2', 'ZB02J 2nd Scene or Mode Selector', '', '', '', '', '', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('227', '138', '0004', 'ZB02J-3', 'ZB02J 3rd Scene or Mode Selector', '', '', '', '', '', '1', '03', '', '0');
INSERT INTO `modeeplib_en` VALUES ('228', '139', '0009', 'Z815A-1', 'Z815A Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('229', '140', '0009', 'Z815B-1', 'Z815B 1st Power Switch', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('230', '140', '0009', 'Z815B-2', 'Z815B 2nd Power Switch', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('231', '141', '0009', 'Z815C-1', 'Z815C 1st Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('232', '141', '0009', 'Z815C-2', 'Z815C 2nd Power Switch', '0006', '开关', null, '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('233', '141', '0009', 'Z815C-3', 'Z815C 3rd Power Switch', '0006', '开关', null, '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib_en` VALUES ('234', '142', '0101', 'Z815D-1', 'Z815D 1st dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('236', '143', '0101', 'Z815E-1', 'Z815E 2nd dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('237', '143', '0101', 'Z815E-2', 'Z815E 3rd dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('238', '144', '0009', 'Z826C-1', 'Z826C 1st Power Switch', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('239', '144', '0009', 'Z826C-2', 'Z826C 2nd Power Switch', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('240', '144', '0009', 'Z826C-3', 'Z826C 3rd Power Switch', '0006', '开关', '', '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib_en` VALUES ('241', '145', '0007', 'Z206-1', 'Z501G 1st security Keypads', '', '', '', '', '', '1', '0A', '', '0');

/*英文设备表*/
CREATE TABLE `modenodelib_en` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nodeType` varchar(100) DEFAULT NULL,
  `modelId` varchar(50) DEFAULT NULL,
  `deviceName` varchar(200) DEFAULT NULL,
  `clusterId` varchar(200) DEFAULT NULL,
  `clusterName` varchar(100) DEFAULT NULL,
  `destType` varchar(4) DEFAULT NULL,
  `source_id` bigint(20) DEFAULT '1',
  `deviceType` varchar(20) DEFAULT NULL,
  `picName` varchar(100) DEFAULT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `powerType` varchar(200) DEFAULT NULL,
  `activationMethod` varchar(2000) DEFAULT NULL,
  `resetDefault` varchar(2000) DEFAULT NULL,
  `remark` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `nodeType` (`nodeType`) USING BTREE,
  KEY `modelId` (`modelId`) USING BTREE,
  KEY `deviceName` (`deviceName`) USING BTREE,
  KEY `clusterId` (`clusterId`) USING BTREE,
  KEY `destType` (`destType`) USING BTREE,
  KEY `deviceType` (`deviceType`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=146 DEFAULT CHARSET=utf8;

/*添加设备英文名称及英文描述*/
INSERT INTO `modenodelib_en` VALUES ('2', '0', 'Z815I', 'Z815I Wall Switch with Power Meter (1-Gang)', '0006', '开关', '3', '1', 'dest', 'Z815I.jpg', 'Z815I is a one-gang Output Switch and its enclosure is designed to replace conventional junction box installation wall switches.  It is equipped with load consumption inspection and load control feature. Users can use ZigBee enabled remote controller and on/off device and smart phone to control Z815I. ', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('3', '0', 'Z815K', 'Z815K Wall Switch with Power Meter (2-Gang)', '0006', '开关', '3', '1', 'dest', 'Z815K.jpg', 'Z815K a three-gang Output Switch and its enclosure is designed to replace conventional junction box installation wall switches.  It is equipped with load consumption inspection and load control feature. Users can use ZigBee enabled remote controller and on/off device and smart phone to control Z815K. ', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('4', '0', 'Z806', 'Z806 Switch Control Unit (2-Output)', '0006', '开关', '3', '1', 'dest', 'Z806.jpg', '\r\nZ806 is a wireless switch relay with two outputs. Users can locally and remotely control it via external switch, paired ZigBee enabled devices and software.', '100～240V的交流电源', '不需要激活', '按住绑定键的同时给设备上电，开始恢复出厂设置，状态指示灯一直闪烁表示擦除完成。之后重新上电，Z806可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('5', '4', 'ZB02A', 'ZB02A Wall Switch (1-Key)', '0006', '开关', '3', '1', 'source', 'ZB02A.jpg', 'ZB02A is a single gang wall switch which can be hung on the wall. Paired with the other On/Off devices, it can be remotely controlled by toggling.', '2节AAA1.5V电池', '拆下面板的上盖，短按绑定键（红色按键），LED网络灯闪5下激活设备。', '拆下面板的上盖，按住绑定键的同时给设备上电，进行恢复出厂设置，看到状态指示灯闪烁，表示恢复完成', '无');
INSERT INTO `modenodelib_en` VALUES ('7', '9', 'Z503', 'Z503 Local Commander', '0006:0008', '开关:调级控制', '1', '1', 'source', 'Z503.jpg', 'Z503 is a multifunctional remote which can turn on/off all devices, dimming, control power socket, emergency button function, arm and disarm security system.', '3V CR2034纽扣电池', '按住“2nd”键和“3”键可激活设备。', '1.同时按住扩展情景键和第二功能按键，给设备上电；2. 如指示灯快速闪烁10次表示恢复出厂值完成，设备自动重启后即可重新加网；3. 如指示灯慢闪3次，表示恢复出厂值失败，请重复步骤1。', '无');
INSERT INTO `modenodelib_en` VALUES ('10', '0', 'Z805A', 'Z805A Euro Type Switch Control Unit with Power Meter (1-Output)', '0006', '开关', '3', '1', 'dest', 'Z805A.jpg', 'Z805A is a wireless switch relay with one output 16A/250V AC. Users can control it via external switch, paired ZigBee enabled devices and software. Via ZigButler, users can check the real-time current/voltage status and kilo-watt-hour consumption of the attached appliance. ', '100～240V的交流电源', '不需要激活', '长按绑定键15S(指示灯闪烁2次,3S闪烁一次，10S闪烁一次），状态灯开始快闪20秒，设备进行恢复出厂设置；20s内短按任意键，Z805B重新请求加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('11', '0', 'Z805B', 'Z805B Switch Control Unit with Power Meter (1-Output)', '0006', '开关', '3', '1', 'dest', 'Z805B.jpg', 'Z805B is wireless switch relay with one output 16A/250V AC. Users can remotely control it via external switch, paired ZigBee enabled devices and softwareVia ZigButler, users can check the real-time current/voltage status and kilo-watt-hour consumption of the attached appliance. 。', '100～240V的交流电源', '不需要激活', '长按绑定键15S(指示灯闪烁2次,3S闪烁一次，10S闪烁一次），状态灯开始快闪20秒，设备进行恢复出厂设置；20s内短按任意键，Z805B重新请求加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('12', '0', 'Z810B', 'Z810B Switch Control Unit with Power Monitoring LCD', '0006', '开关', '3', '1', 'dest', 'Z810B.jpg', 'Z810B is a switch control, power supply is 100-240VAC 50/60HZ. It can be controlled by its manual override switch or by a paired wireless switch or by software. Users can use software and its LCD to check the real-time current/voltage status and kilo-watt-hour consumption of the attached appliance.', '100～240V的交流电源', '不需要激活', '按下绑定键15秒后（图标每隔5秒闪烁一下，闪烁3次），2秒内短按功能键，LCD数值区显示表示恢复完成，之后设备将自动重启。', '无');
INSERT INTO `modenodelib_en` VALUES ('13', '0', 'Z811', 'Z811 Switch Control Unit (4-Output)', '0006', '开关', '3', '1', 'dest', 'Z811.jpg', 'Z811 is a wireless on/off output switch device. Users can control it via paired ZigBee enabled devices and software.', '100～240V的交流电源', '不需要激活', '长按绑定键15秒（状态指示灯3S闪一次，10S闪一次,15S闪一次），然后短按，状态指示灯一直闪烁表示擦除完成。之后指示灯灭掉，指示Z811便可以重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('14', '0', 'Z815J', 'Z815J Wall Switch with Power Meter (2-Gang)', '0006', '开关', '3', '1', 'dest', 'Z815J.jpg', 'Z815J is a two-gang switch and is designed to replace conventional junction box installation wall switches.  It is equipped with load consumption inspection and load control feature.  Users can use ZigBee enabled remote controller and on/off device and smart phone to control Z815J.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('15', '0', 'Z815L', 'Z815L Wall Dimmer Switch with Power Meter (1-Gang)', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z815L.jpg', 'Z815L is a one-gang dimmable switch and is designed to replace conventional junction box installation wall switches.  It is equipped with load consumption inspection and load control feature. Users can use ZigBee enabled remote controller and on/off device and smart phone to control Z815L.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('16', '0', 'Z815M', 'Z815M Wall Dimmer Switch with Power Meter (2-Gang)', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z815M.jpg', 'Z815M is a two-gang dimmable switch and is designed to replace conventional junction box installation wall switches.  It is equipped with load consumption inspection and load control feature. Users can use ZigBee enabled remote controller and on/off device and smart phone to control Z815M.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('17', '0', 'Z817A', 'Z817A Ceiling 16A Relay Switch with Power Meter', '0006', '开关', '3', '1', 'dest', 'Z817A.jpg', 'Z817A is a ceiling mount switch unit. It can do on/off switch and dim the lights through its buttoms, paired ZigBee enabled remote controllers or even software. Energy consumption reading is able to be monitored with software. ', '100～240V的交流电源', '不需要激活', '按住绑定键（正对logo靠左）15秒，设备网络灯闪烁一次，然后在2秒内短按任意键，设备网络灯快闪10次后重启。', '无');
INSERT INTO `modenodelib_en` VALUES ('18', '0', 'Z817B', 'Z817B Ceiling 16A Relay Dimmer with Power Meter', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z817B.jpg', 'Z817B is a ceiling mount dimmable unit. It can do on/off switch and dim the lights through its buttoms, paired ZigBee enabled remote controllers or even software. Energy consumption reading is also able to be monitored with software. ', '100～240V的交流电源', '不需要激活', '按住绑定键（正对logo靠左）15秒，设备网络灯闪烁一次，然后在2秒内短按任意键，设备网络灯快闪10次后重启。', '无');
INSERT INTO `modenodelib_en` VALUES ('19', '0', 'Z817C', 'Z817C Ceiling Motion Detector with On/Off Switch', '0006', '开关', '3', '1', 'dest', 'Z817C.jpg', 'Z825C is a three-gang touch switch and is designed to replace conventional junction box installation wall switches. It is equipped with load consumption inspection and load control feature. Users can use ZigBee enabled remote controller and on/off device and smart phone to control Z825C.', '100～240V的交流电源', '不需要激活', '按住绑定键（正对logo靠左）15秒，设备网络灯闪烁一次，然后在2秒内短按任意键，设备网络灯快闪10次后重启。', '无');
INSERT INTO `modenodelib_en` VALUES ('20', '0', 'Z825C', 'Z825C Touch Panel Switch with Power Meter (3-Gang)', '0006', '开关', '3', '1', 'dest', 'Z825C.jpg', 'Z825C is a three-gang touch switch and is designed to replace conventional junction box installation wall switches. It is equipped with load consumption inspection and load control feature. Users can use ZigBee enabled remote controller and on/off device and smart phone to control Z825C.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('21', '0', 'Z825E', 'Z825E Touch Panel Dimmer Switch with Power Meter (2-Gang)', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z825E.jpg', 'Z825E is a twp-gang touch switch and is designed to replace conventional junction box installation wall switches. It is equipped with load consumption inspection and load control feature. Users can use ZigBee enabled remote controller and on/off device and smart phone to control Z825E.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('22', '0', 'ZC06', 'ZC06 Dimmable LED Tube (120cm)', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'ZC06.jpg', 'ZC06 is a robust ZigBee enabled dimmable LED tube. It has 256 light levels function. It is a long life and light weighted power consumption which conserves 60% comparing to the conventional light tubes. ZC06 utilizes SMT LEDs with its 400 lumen/1.5 meters and 50,000-hour lamp life. ZC06 can be controlled wirelessly via remote controller. It supports AC 100V to 240V with the same controlling features.', '100～240V的交流电源', '不需要激活', '同时长按测试键和绑定键5s，5s时间到后指示灯闪10次后就恢复出厂设置成功，此时就可以重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('23', '1', 'Z800', 'Z800 Power Plug with Power Meter', '0006', '开关', '3', '1', 'dest', 'Z800B.jpg', 'Z800 is a power socket with power consumption monitoring that allows off site remote control. Users can control it by its bottom and by paired ZigBee enabled devices and software. Energy consumption reading is able to be monitored with software.', '100～240V的交流电源', '不需要激活', '长按绑定键15S，状态灯（红灯）快闪20S；设备进入恢复出厂设置；再按任意键重新请求加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('24', '1', 'Z803', 'Z803 Power Strip with Power Monitoring LCD & USB Port', '0006', '开关', '3', '1', 'dest', 'Z803.jpg', 'Z803 is a 4-gang power strip with power consumption display. It has USB jacks that facilitate USB charging purpose and built-in LCD. Users could also wirelessly control the switch through its own bottom, paired devices or software. LCD can display total power consumption of all sockets. Via the built-in LCD or application, users are able to check the consuming current/ voltage/ power/ energy.', '100～240V的交流电源', '不需要激活', '同时按下Match Key 和Bind Key 按键5秒后，！图标闪烁一下，代表长按了5秒。放开按键后，进行出厂设置，设备自动重新上电。', '无');
INSERT INTO `modenodelib_en` VALUES ('25', '1', 'Z808A', 'Z808A Power Plug with Power Monitoring LCD & USB Port', '0006', '开关', '3', '1', 'dest', 'Z808A.jpg', 'Z808A is a wireless smart plug. It has USB jacks that facilitate USB charging purpose and built-in LCD. Users could also wirelessly control the switch through its own bottom, paired devices or software. LCD can display total power consumption of all sockets. Via the built-in LCD or application, users are able to check the consuming current/ voltage/ power/ energy.', '100～240V的交流电源', '不需要激活', '长按绑定键（右边按键）15s（期间网络指示灯闪3次）后释放绑定键，并在2s内短按功能键（左边按键），即开始恢复出厂设置，网络灯快速闪烁十次后自动重启设备，开始寻找新的网络。', '无');
INSERT INTO `modenodelib_en` VALUES ('26', '1', 'Z808B', 'Z808B Dimmable Power Plug with Power Monitoring LCD & USB Port', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z808B.jpg', 'Z808B is a wireless dimmable smart plug. It has USB jacks that facilitate USB charging purpose and built-in LCD. Users could also wirelessly control the switch through its own bottom, paired devices or software. LCD can display total power consumption of all sockets. Via the built-in LCD or application, users are able to check the consuming current/ voltage/ power/ energy.', '100～240V的交流电源', '不需要激活', '长按绑定键（右边按键）15s（期间网络指示灯闪3次）后释放绑定键，并在2s内短按功能键（左边按键），即开始恢复出厂设置，网络灯快速闪烁十次后自动重启设备，开始寻找新的网络。', '无');
INSERT INTO `modenodelib_en` VALUES ('27', '1', 'Z809A', 'Z809A Power Plug with Power Meter', '0006', '开关', '3', '1', 'dest', 'Z809A.jpg', 'Z809A is a smart plug that allows off site remote control. Users can manually switch on/off the socket or by software to control it. Via software, users are able to monitor the load current, voltage and power and kilo-watt-hour consumption of the attached appliance.', '100～240V的交流电源', '不需要激活', '　长按绑定键（侧面）15s（期间网络指示灯闪3次）后释放绑定键，并在2s内短按测试键（正面按键），即开始恢复出厂设置，网络灯快速闪烁十次后自动重启设备，开始寻找新的网络。	无', '无');
INSERT INTO `modenodelib_en` VALUES ('28', '1', 'Z809B', 'Z809B Dimmable Power Plug with Power Meter', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z809B.jpg', 'Z809B is a dimmable smart plug that allows off site remote control. Users can manually switch on/off the socket or by paired derives or by software to control and to dim. Via software, users are able to monitor the load current, voltage and power and kilo-watt-hour consumption of the attached appliance. ', '100～240V的交流电源', '不需要激活', '长按绑定键（侧面）15s（期间网络指示灯闪3次）后释放绑定键，并在2s内短按测试键（正面按键），即开始恢复出厂设置，网络灯快速闪烁十次后自动重启设备，开始寻找新的网络。', '无');
INSERT INTO `modenodelib_en` VALUES ('29', '1', 'Z816B', 'Z816B US Type Wall Socket with Power Meter', '0006', '开关', '3', '1', 'dest', 'Z816B.jpg', 'Z816B is a US type wireless smart wall socket that allows off site remote control. Users can manually switch on/off the socket or by paired derives or by software to control and to dim. Via software, users are able to monitor the load current, voltage and power and kilo-watt-hour consumption of the attached appliance.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('30', '1', 'Z816G', 'Z816G UK Type Wall Socket with Power Meter', '0006', '开关', '3', '1', 'dest', 'Z816G.jpg', 'Z816G is a UK type wireless wall socket, it can directly install in 86 junction box and replace the traditional wall socket. It allows off site remote control. Users can manually switch on/off the socket or by paired derives or by software to control and to dim. Via software, users are able to monitor the load current, voltage and power and kilo-watt-hour consumption of the attached appliance.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('31', '1', 'Z816H', 'Z816H China Type Wall Socket with Power Meter', '0006', '开关', '3', '1', 'dest', 'Z816HI.jpg', 'Z816H is a China type wireless smart wall socket, it can directly install in 86 junction box and replace the traditional wall socket. It allows off site remote control. Users can manually switch on/off the socket or by paired derives or by software to control and to dim. Via software, users are able to monitor the load current, voltage and power and kilo-watt-hour consumption of the attached appliance.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('32', '2', 'Z711', 'Z711 Temperature and Humidity Sensor (Indoor)', null, null, null, '1', null, 'Z711.jpg', 'Z711 is a humidity and temperature sensor. It is used for collecting ambient H/T data and sending them to the display. ', '2节1.5V电池', '按下按键可激活设备', '1.  按住绑定键的同时给设备上电；2.  复位完成则led闪烁30次后停止；3.  重新上电，Z711可以开始重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('33', '2', 'Z712', 'Z712 Temperature and Humidity Sensor (Outdoor)', '', '', '', '1', '', 'Z712.jpg', 'Z712 is used to detect the outdoor humidity and temperature. It also increases a splash proof housing for protection and transmits the collecting data to a displayed device through wireless network.', '2节1.5V电池', '按下按键可激活设备', '1.  按住绑定键的同时给设备上电；2.  复位完成则led闪烁30次后停止；3.  重新上电，Z711可以开始重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('34', '2', 'Z713', 'Z713 Temperature and Humidity Sensor with Solar Panel (Outdoor)', '', '', '', '1', '', 'Z713.jpg', ' Z713 is a detector for humidity, temperature and UV rays with a solar panel charger.  It\'s also equiped with a waterproof housing for protection and used for collecting ambient H/T data and UV intensity, then sending the data to the display.  ', '2节1.5V电池', '按下按键可激活设备', '1.  按住绑定键的同时给设备上电；2.  复位完成则led闪烁30次后停止；3.  重新上电，Z711可以开始重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('35', '2', 'Z716A', 'Z716A Temperature and Humidity Sensor with LCD (Indoor)', '', '', '', '1', '', 'Z716A.jpg', 'Z716A is a humidity and temperature detector which is used for collecting ambient H/T data and display on the LCD.  It can also send the data to the displaying device. ', '2节1.5V电池', '按下右边按键可激活设备', '设备上电后，同时按住“按键1”和“按键2” 5秒，开始恢复出厂值，LCD上显示所有图标画面，表示恢复出厂值完成，设备自动复位寻找加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('36', '2', 'Z716B', 'Z716B Temperature Sensor with LCD (Indoor)', null, null, null, '1', null, 'Z716B.jpg', 'Z716B is a temperature detector which is used for collecting ambient temperature data and display on the LCD.  It can also send the data to the displaying device. ', '2节1.5V电池', '按下右边按键可激活设备', '设备上电后，同时按住“按键1”和“按键2” 5秒，开始恢复出厂值，LCD上显示所有图标画面，表示恢复出厂值完成，设备自动复位寻找加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('37', '3', 'Z302B', 'Z302B On/Off Light Switch', '0006', '开关', '3', '1', 'source', 'Z302B.jpg', 'Z302B is an automatic light switch controller, which can bind with the output switches. When the brightness is lower than a certain level, Z302B will transmit \"ON\" command and control the binding device. When the brightness enhance to a certain level, Z302B will transmit \"OFF\" command to the binding device. ', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('38', '3', 'Z302H', 'Z302H Light Sensor Dimmer Switch', '0006:0008', '开关:调级控制', '3', '1', 'source', 'Z302H.jpg', 'Z302H is a light dimmer which can sense the intensity of the ambient lighting and determine whether it need to change the brightness of its binding lamp. Thus the controlled lamp/light will maintain the certain brightness within the area. ', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('39', '3', 'Z302G', 'Z302G Light Sensor', null, null, null, '1', null, 'Z302G.jpg', 'The Z302G is an automatic detector and reportor for environment illumination.  It can regularly transmit ambient illumination to a device which can receive and display the ambient illumination.  ', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('40', '3', 'Z311B', 'Z311B On/Off Light Switch', '0006', '开关', '3', '1', 'source', 'Z311B.jpg', 'Z311B is an automatic light switch controller, which can bind with the output switches. When the brightness is lower than a certain level, Z311B will transmit \"ON\" command and control the binding device. When the brightness enhance to a certain level, Z311B will transmit \"OFF\" command to the binding device.  ', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '同时按下绑定键和辅助键，两键同时按着5s，直到看到红色指示灯开始快闪，再松开按键。红色指示灯快闪20次后，设备复位将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('41', '3', 'Z311H', 'Z311H Light Sensor Dimmer Switch', '0006:0008', '开关:调级控制', '3', '1', 'source', 'Z311H.jpg', 'Z311H is a light dimmer which can sense the intensity of the ambient lighting and determine whether it need to change the brightness of its binding lamp. Thus the controlled lamp/light will maintain the certain brightness within the area. ', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '同时按下绑定键和辅助键，两键同时按着5s，直到看到红色指示灯开始快闪，再松开按键。红色指示灯快闪20次后，设备复位将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('42', '3', 'Z311G', 'Z311G Light Sensor', '', '', '', '1', '', 'Z311G.jpg', 'The Z311G is an automatic detector and reportor for environment illumination.  It can regularly transmit ambient illumination to a device which can receive and display the ambient illumination. ', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '同时按下绑定键和辅助键，两键同时按着5s，直到看到红色指示灯开始快闪，再松开按键。红色指示灯快闪20次后，设备复位将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('44', '4', 'ZB02B', 'ZB02B Wall Switch (2-Key)', '0006', '开关', '3', '1', 'source', 'ZB02B.jpg', 'ZB02B is a two keys wireless wall-mounted switch. It can be bound with a device which has switch control function and control the switch wirelessly. ', '2节AAA1.5V电池', '拆下面板的上盖，短按绑定键（红色按键），LED网络灯闪5下激活设备。', '拆下面板的上盖，按住绑定键的同时给设备上电，进行恢复出厂设置，看到状态指示灯闪烁，表示恢复完成', '无');
INSERT INTO `modenodelib_en` VALUES ('45', '4', 'ZB02C', 'ZB02C Wall Switch (3-Key)', '0006', '开关', '3', '1', 'source', 'ZB02C.jpg', 'ZB02C is a three keys wireless wall-mounted switch. It can be bound with a device which has switch control function and control the switch wirelessly. ', '2节AAA1.5V电池', '拆下面板的上盖，短按绑定键（红色按键），LED网络灯闪5下激活设备。', '拆下面板的上盖，按住绑定键的同时给设备上电，进行恢复出厂设置，看到状态指示灯闪烁，表示恢复完成', '无');
INSERT INTO `modenodelib_en` VALUES ('46', '4', 'ZB02F', 'ZB02F Wall Dimmer Switch', '0006:0008', '开关:调级控制', '3', '1', 'source', 'ZB02F.jpg', 'ZB02F is a single key wireless wall-mounted switch. It can be bound with a device which has switch and level control function and control the switch wirelessly. ', '2节AAA1.5V电池', '拆下面板的上盖，短按绑定键（红色按键），LED网络灯闪5下激活设备。', '拆下面板的上盖，按住绑定键的同时给设备上电，进行恢复出厂设置，看到状态指示灯闪烁，表示恢复完成。', '无');
INSERT INTO `modenodelib_en` VALUES ('47', '5', 'ZB01A', 'ZB01A Motion Detector', null, null, null, '1', null, 'ZB01A.jpg', 'The infrared detection alarm function of ZB01A can detect the movement of object. And trigger the alarm through the security center. ', '2节CR123A电池或12V直流电源', '按住背面小圆点按键3秒以上，直至状态指示灯快速闪烁2次激活设备', '按住背面小圆点（绑定键）的同时给设备上电，状态灯快闪，即恢复出厂设置操作成功。恢复完成后重新上电就可以重新加入网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('48', '5', 'ZB01B', 'ZB01B Motion Detector with On/Off Light Switch', '0006', '开关', '3', '1', 'source', 'ZB01B.jpg', 'ZB01B is a switch controller for motion detection. It can detect the movement of object through infrared and control the binding device according to the setting of light threshold.', '2节CR123A电池或12V直流电源', '按住背面小圆点按键3秒以上，直至状态指示灯快速闪烁2次激活设备', '按住背面小圆点（绑定键）的同时给设备上电，状态灯快闪，即恢复出厂设置操作成功。恢复完成后重新上电就可以重新加入网络了。', '可通过调节设备背面的光照灵敏度来设置绑定开关的的光照阈值。');
INSERT INTO `modenodelib_en` VALUES ('49', '5', 'ZB01C', 'ZB01C Motion Detector with On/Off Light Switch and Temperature Sensor', '0006', '开关', '3', '1', 'source', 'ZB01C.jpg', 'ZB01C has infrared detecting alarm, infrared on/off control and temperature reporting, three functions. The function of infrared alarm can detect the movement and send the alert through security center. Infrared on/off control function can detect the infrared generated from the moving objects then control the light switch. Temperature reporting function can detect the current temperature of the environment, and report it regularly to the binding device. ', '2节CR123A电池或12V直流电源', '按住背面小圆点按键3秒以上，直至状态指示灯快速闪烁2次激活设备', '按住背面小圆点（绑定键）的同时给设备上电，状态灯快闪，即恢复出厂设置操作成功。恢复完成后重新上电就可以重新加入网络了。', '可通过调节设备背面的光照灵敏度来设置绑定开关的的光照阈值。');
INSERT INTO `modenodelib_en` VALUES ('50', '5', 'ZB01D', 'ZB01D Occupancy Sensor', null, null, null, '1', null, 'ZB01D.jpg', 'ZB01D is an occupancy sensor. It can detect the object movement which produces infrared and report the status to the binding device. ', '2节CR123A电池或12V直流电源', '按住背面小圆点(绑定键)按键3秒以上，直至状态指示灯快速闪烁2次激活设备', '按住背面小圆点（绑定键）的同时给设备上电，状态灯快闪，即恢复出厂设置操作成功。恢复完成后重新上电就可以重新加入网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('51', '5', 'ZB11A', 'ZB11A Motion Detector', '', '', '', '1', '', 'ZB11A.jpg', 'The infrared detection alarm function of ZB11A can detect the movement of object. And trigger the alarm through the security center (CIE).', '2节ER14505 3.6V AA电池供电', '正对logo，短按右侧按钮指示灯闪5下，即可激活设备', '长按辅助键（正对logo右边）10秒，见到绿色指示灯快闪即可松开按键，设备将进行恢复出厂设置操作，完成后将自动关机。', '无');
INSERT INTO `modenodelib_en` VALUES ('52', '5', 'ZB11B', 'ZB11B Motion Detector with On/Off Light Switch', '0006', '开关', '3', '1', 'source', 'ZB11B.jpg', 'ZB11B is a switch controller for motion detection. It can detect the movement of object through infrared and control the binding device according to the setting of light threshold. ', '2节ER14505 3.6V AA电池供电', '正对logo，短按右侧按钮指示灯闪5下，即可激活设备', '长按辅助键（正对logo右边）10秒，见到绿色指示灯快闪即可松开按键，设备将进行恢复出厂设置操作，完成后将自动关机。', '可通过调节设备背面的光照灵敏度来设置绑定开关的的光照阈值。');
INSERT INTO `modenodelib_en` VALUES ('53', '5', 'ZB11C', 'ZB11C Motion Detector with On/Off Light Switch and Temperature Sensor', '0006', '开关', '3', '1', 'source', 'ZB11C.jpg', 'ZB01C has infrared detecting alarm, infrared on/off control and temperature reporting, three functions. The function of infrared alarm can detect the movement and send the alert through security center. Infrared on/off control function can detect the infrared generated from the moving objects then control the light switch. Temperature reporting function can detect the current temperature of the environment, and report it regularly to the binding device. ', '2节ER14505 3.6V AA电池供电', '正对logo，短按右侧按钮指示灯闪5下，即可激活设备', '长按辅助键（正对logo右边）10秒，见到绿色指示灯快闪即可松开按键，设备将进行恢复出厂设置操作，完成后将自动关机。', '可通过调节设备背面的光照灵敏度来设置绑定开关的的光照阈值。');
INSERT INTO `modenodelib_en` VALUES ('54', '5', 'ZB11D', 'ZB11D Occupancy Sensor', '', '', '', '1', '', 'ZB11D.jpg', 'ZB01D is an occupancy sensor. It can detect the object movement which produces infrared and report the status to the binding device. ', '2节ER14505 3.6V AA电池供电', '正对logo，短按右侧按钮指示灯闪5下，即可激活设备', '长按辅助键（正对logo右边）10秒，见到绿色指示灯快闪即可松开按键，设备将进行恢复出厂设置操作，完成后将自动关机。', '无');
INSERT INTO `modenodelib_en` VALUES ('55', '6', 'Z815N', 'Z815N  AC Curtain Controller', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z815N.jpg', 'Z815N is a curtain controller. It can control the curtains on/off and level through its own key, binding equipments or software. At the same time, the users can check the load current, voltage, power and energy at present. ', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', 'Lin接火线，N线是零线接电机的蓝线. L out1接电机的棕线，L out2接电机的黑线。在设备执行窗帘控制功能前,都应先进行“学习上下限”的学习，学习方法请详见设备说明书或在app的设备管理模块，该设备的设置页面进行学习。');
INSERT INTO `modenodelib_en` VALUES ('56', '6', 'ZD01B', 'ZD01B Toggle & Level Curtain Controller (Drape)', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'ZD01B.jpg', 'ZD01B is a curtain controller. It can control the curtains on/off and level through its own key, binding equipments or software.', '12V4A直流电源', '不需要激活', '按住ON1和OFF1键（靠右边一列）的同时给设备上电，开始执行恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重新上电就可以重新加网了', '在设备执行窗帘控制功能前,都应先进行“学习上下限”的学习，学习方法请详见设备说明书或在app的设备管理模块，该设备的设置页面进行学习');
INSERT INTO `modenodelib_en` VALUES ('57', '7', 'Z302A', 'Z302A Window Intrusion Sensor', null, null, null, '1', null, 'Z302A.jpg', 'Z302A is a magnetic device, used as a detection device in the security systems. When the door or window is opened, it will send alarm message to the security center. When the door or window is closed, it will send out the normal status message to the security center. ', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', 'Z302A的本体与门磁的磁铁部分别安装在门或窗户的缝隙的两边，当门或窗被打开，它将发出报警信息给安防中心设备( 即CIE )，当门或窗被关闭好，它会发送出状态恢复正常的信息给CIE。该设备也可以用于冰箱、抽屉等处，用于检测冰箱或抽屉等是否被打开。需要注意防水。');
INSERT INTO `modenodelib_en` VALUES ('58', '7', 'Z302C', 'Z302C Window Sensor with Glass Break Detector', null, null, null, '1', null, 'Z302C.jpg', 'Z302C is a detection device in the security systems, as an alarm devices when the doors and windows were exceptional be open or the glass are broken. ', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', 'Z302C的本体与门磁的磁铁部分别安装在门或窗户的缝隙的两边，当门或窗被打开，它将发出报警信息给安防中心设备( 即CIE )，当门或窗被关闭好，它会发送出状态恢复正常的信息给CIE。该设备也可以用于冰箱、抽屉等处，用于检测冰箱或抽屉等是否被打开。需要注意防水。');
INSERT INTO `modenodelib_en` VALUES ('59', '7', 'Z302D', 'Z302D Emergency Push Button', null, null, null, '1', null, 'Z302D.jpg', 'Z302D is a alarm triggered device, as a detection device in the security systems. It can be worn on the wrist of a child or the elderly, children or the elderly when in need of emergency assistance in danger, press the alarm button Z302D, Z302D immediately security Center sends out an alarm message.', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('60', '7', 'Z302E', 'Z302E Asset tag', null, null, null, '1', null, 'Z302E.jpg', 'Z-302E is equipped with light and movement sensor to detect light intensity and movements in the environment.  When status is changed, Z-302E sends alarm message to CIE.', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '可将该设备放于贵重的物品或抽屉旁，当物品被移动或光线发生变化时，则设备会将报警信息发送给安防中心设备。');
INSERT INTO `modenodelib_en` VALUES ('61', '7', 'Z302J', 'Z302J Window Intrusion Sensor', '0006', '开关', '3', '1', 'source', 'Z302J.jpg', 'Z-302J is a window/door sensor. When window/door status is changed, it can control its binding device according to the configurations. Z-302J also works as sensor of the security center- When window/door is open, it notifies security center CIE; When window/door is closed, it notifies security center CIE. ', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', 'Z302J的本体与门磁的磁铁部分别安装在门或窗户的缝隙的两边，当门或窗被打开，它将发出报警信息给安防中心设备( 即CIE )，当门或窗被关闭好，它会发送出状态恢复正常的信息给CIE。该设备也可以用于冰箱、抽屉等处，用于检测冰箱或抽屉等是否被打开。需要注意防水。');
INSERT INTO `modenodelib_en` VALUES ('62', '7', 'Z306D', 'Z306D Panic Button and Inductive Charging Base', null, null, null, '1', null, 'Z307.jpg', 'Z-306D acts as an end device in the network as well as the mobile tags of the local network. It can be searched and controlled via third-party software such as Netvox Zig-Butler.', '3V CR2450钮扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('63', '7', 'Z311A', 'Z311A Window Sensor', null, '', '', '1', '', 'Z311A.jpg', 'Z-311A is a window/door sensor and it is also a sensor in the security system- When window/door is closed, it notifies security center CIE. ', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '同时按下绑定键和辅助键，两键同时按着5s，直到看到红色指示灯开始快闪，再松开按键。红色指示灯快闪20次后，设备复位将进入power off模式。', 'Z311A的本体与门磁的磁铁部分别安装在门或窗户的缝隙的两边，当门或窗被打开，它将发出报警信息给安防中心设备( 即CIE )，当门或窗被关闭好，它会发送出状态恢复正常的信息给CIE。该设备也可以用于冰箱、抽屉等处，用于检测冰箱或抽屉等是否被打开。需要注意防水。');
INSERT INTO `modenodelib_en` VALUES ('64', '7', 'Z311C', 'Z311C Window Sensor with Glass Break Detector', null, '', '', '1', '', 'Z311C.jpg', 'Z-311C is a sensor in the security system- When window/door is open or window glass is broken, it will send out alarm messages.', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '同时按下绑定键和辅助键，两键同时按着5s，直到看到红色指示灯开始快闪，再松开按键。红色指示灯快闪20次后，设备复位将进入power off模式。', 'Z311A的本体与门磁的磁铁部分别安装在门或窗户的缝隙的两边，当门或窗被打开，它将发出报警信息给安防中心设备( 即CIE )，当门或窗被关闭好，它会发送出状态恢复正常的信息给CIE。该设备也可以用于冰箱、抽屉等处，用于检测冰箱或抽屉等是否被打开。需要注意防水。');
INSERT INTO `modenodelib_en` VALUES ('65', '7', 'Z312', 'Z312 Door Bell Button', null, null, null, '1', null, 'Z312.jpg', 'Z-312 is a door bell. Bind Z-312 with siren device and the siren will be able to generate door bell sounds.', '3V CR2450纽扣电池', '　　　　当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '同时按下绑定键和辅助键，两键同时按着5s，直到看到红色指示灯开始快闪，再松开按键。红色指示灯快闪20次后，设备复位将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('66', '7', 'Z307', 'Z307 Wireless Rechargeable Fall Sensor', null, null, null, '1', null, 'Z307.jpg', 'Z307 is a fall sensor as well as a sensor in security system. It can be worn on waist to detect fall of elderly people and young children. When fall is detected, it sends alarm message to the security center device.', '充电套件', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('67', '7', 'Z308', 'Z308 Wearable Presence Tag + Panic Button', null, null, null, '1', null, 'Z308.jpg', 'Z-308 is an emergency alarm device. As a sensor of the security system, it can be worn on wrist of elderly people or young children. When elderly people or young children press Z308\'s emergency button, it sends out alarm message to security center.', '3V CR2450纽扣电池', '长按按键3s，红色指示灯闪一下，松开手后，绿色指示灯闪五下。即激活设备。', '长按报警键15秒以上，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯快闪10次后，设备进入关机状态，红色指示灯熄灭。', '无');
INSERT INTO `modenodelib_en` VALUES ('68', '7', 'ZA01A', 'ZA01A Smoke Detector (Chemiresistors)', null, null, null, '1', null, 'ZA01A.jpg', 'Z-A01A is an air pollution sensor used in home environment, and it acts as a security device in the network. When air intensity goes above a specific level , the device will generate buzzer sound and send status change message to CIE.', '12V直流电源或100～240V交流供电', '不需要激活', '同时按住绑定键和测试键5秒以上，设备指示灯开始快速闪烁，表示复位完成。之后将设备重启，就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('69', '7', 'ZA01B', 'ZA01B Gas Detector', '', '', '', '1', '', 'ZA01B.jpg', 'Z-A01B is a gas detector and it acts as a security device in the network. When gas intensity goes above a specific level, it will send warning message to CIE and generate buzzer sound to warn the users.', '12V直流电源或100～240V交流供电', '不需要激活', '同时按住绑定键和测试键5秒以上，设备指示灯开始快速闪烁，表示复位完成。之后将设备重启，就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('70', '7', 'ZA01C', 'ZA01C CO Detector', null, null, null, '1', null, 'ZA01C.jpg', 'Z-A01C is a CO detector and it acts as a security device in the network. When CO intensity goes above a specific level, it will send warning message to CIE and generate buzzer sound to warn the users.', '12V直流电源或100～240V交流供电', '不需要激活', '同时按住绑定键和测试键5秒以上，设备指示灯开始快速闪烁，表示复位完成。之后将设备重启，就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('71', '7', 'ZA01D', 'ZA01D Liquefied Petroleum Gas Detector', null, null, null, '1', null, 'ZA01D.jpg', 'Z-A01D is a Liquefied gas detector and it acts as a security device in the network. When liquefied gas intensity goes above a specific level, it will send warning message to CIE and generate buzzer sound to warn the users.', '12V直流电源或100～240V交流供电', '不需要激活', '同时按住绑定键和测试键5秒以上，设备指示灯开始快速闪烁，表示复位完成。之后将设备重启，就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('72', '7', 'ZA02E', 'ZA02E Smoke Detector with Backup Battery (Photoelectric)', null, null, null, '1', null, 'ZA02E.jpg', 'Z-A02E is a smoke detector. When smoke intensity goes above a specific level, it will send warning message to CIE and generate buzzer sound to warn the users.', '12V直流电源或100～241V交流供电', '不需要激活', '同时按住绑定键和测试键5秒以上，设备指示灯开始快速闪烁，表示复位完成。之后将设备重启，就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('73', '7', 'ZA10', 'ZA10 Gas/Water Keeper', '0006', '开关', '3', '1', 'dest', 'ZA10.jpg', 'Z-A10 can be a gas keep or water keep. When Z-A10 is used as a gas keeper, it will turn off the gas when there are fire detection in the network.', '12V直流电源', '不需要激活', '按住按键的同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复出厂设置完成，重新上电就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('74', '7', 'ZB05', 'ZB05 Electronic Door Lock with Fingerprint Identification', '0101', '开关锁', '3', '1', 'dest', 'ZB05.jpg', 'Z-B05 can be unlocked/locked via mechanical keys, passwords, IC cards, and remote control. Via ZigBee wireless network, users can check and control door lock status at any time. Z-B05 door lock provides reliable password security and fire detection for safety purpose.', '8节AA1.5V电池', '不需要激活', '1.按住网络键的同时给设备上电，上电完请不要立即松开按键，等到网络灯开始快速闪烁，提示复位成功；2.重新上电，ZB05可以开始重新加网。', '1、初次使用时，需要注册zigbee：在门打开的状态下，打开内侧用（主锁身）的电池盖和外盒，在右侧下端按“锁卡注册键”。然后再按下面的“ZB注册键”（靠上的按键）。按“*”键，可听到“哔呖呖”的设置音，设置结束。2、默认都为加密的方式，第一次使用时，需要在app中→设备管理模块→进入门锁的设置页面，设置新的用户和密码，才可以对锁进行开关控制。门锁默认有6个空用户，注册新用户前，需要先将这些用户删除。超级密码默认为：123456.usercode即用户名为1-254的数字。');
INSERT INTO `modenodelib_en` VALUES ('75', '7', 'ZB02E', 'ZB02E Door Bell', null, null, null, '1', null, 'ZB02E.jpg', 'Z-B02E is a door bell. Bind Z-B02E with siren device and the siren will be able to generate door bell sounds.', '2节AAA1.5V电池', '拆下面板的上盖，短按绑定键（红色按键），LED网络灯闪5下激活设备。', '拆下面板的上盖，按住绑定键的同时给设备上电，进行恢复出厂设置，看到状态指示灯闪烁，表示恢复完成', '无');
INSERT INTO `modenodelib_en` VALUES ('76', '7', 'Z311E', 'Z311E Asset tag', '', '', '', '1', '', 'Z311E.jpg', 'Z-311E is equipped with light and movement sensor to detect light intensity and movements in the environment.  When status is changed, Z-311E sends alarm message to CIE.', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '同时按下绑定键和辅助键，两键同时按着5s，直到看到红色指示灯开始快闪，再松开按键。红色指示灯快闪20次后，设备复位将进入power off模式。', '可将该设备放于贵重的物品或抽屉旁，当物品被移动或光线发生变化时，则设备会将报警信息发送给安防中心设备。');
INSERT INTO `modenodelib_en` VALUES ('77', '7', 'Z602A', 'Z602A Siren', null, null, null, '1', null, 'Z602A.jpg', 'Z-602A is a siren device which generate buzzer sound and visible LED indication to report events in the security system.', '12V直流电源和3节1.2V可充电备用电池', '不需要激活', '按住设置键（正对logo右侧按键）的同时给设备上电，状态灯快速闪烁且发出两次门铃报警声，即恢复出厂设置操作成功。恢复完成后重新上电就可以重新加入网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('78', '7', 'Z602B', 'Z602B Siren with GSM Connectivity', null, null, null, '1', null, 'Z602B.jpg', 'Z-602B is a siren device which generate buzzer sound and visible LED indication to report events in security system. When Z-602B generate buzzer sounds, it can also call and text a pre-configured phone number according to different alarm levels for users to receive the alarm message in the real time.', '12V直流电源和3节1.2V可充电备用电池', '不需要激活', '按住设置键（正对logo右侧按键）的同时给设备上电，状态灯快速闪烁且发出两次门铃报警声，即恢复出厂设置操作成功。恢复完成后重新上电就可以重新加入网络了。', '该设备具有GSM功能，可在电池盒内装入SIM卡，在app的设备管理模块，进入该设备的设置页面，可设置电话号码，用于报警时，通过拨打电话和发送短信通知用户。设置的短信号码前，需要加国家代码（如中国：86）');
INSERT INTO `modenodelib_en` VALUES ('79', '7', 'Z601A', 'Z601A Siren', null, null, null, '1', null, 'Z601A.jpg', 'Z-601A is a siren device which generate buzzer sound to report events in the security system.', '12V直流电源', '不需要激活', '按住绑定键的同时给设备上电，状态灯快闪，即恢复出厂设置操作成功。恢复完成后重新上电就可以重新加入网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('80', '8', 'Z821', 'Z821 Multi-Channel Energy Power Meter', null, null, null, '1', null, 'Z821.jpg', 'Z-821 is an indoor multi-channel power meter. It can measure voltage of single channel and the total current of all seven channels, and it can provide power and energy data.', '100～240V的交流电源', '不需要激活', '按下Bind Key按键15秒后（状态灯每5秒钟闪烁1次，共闪烁3次，代表长按了15秒），松开按键，在两秒之内短按Match Key进行网络信息的擦除。网络信息擦除后设备自动重启寻找网络。', '将七路CT端子（用于检测电能）接入到Z821两侧的接口。按照CT端子上指示的K→L方向（电流流动的方向）,将导线扣入CT端子，即可检测该路的电能。');
INSERT INTO `modenodelib_en` VALUES ('81', '9', 'Z501A', 'Z501A 4-Key Remote Controller with Arm/Disarm, On/Off, & Panic Button', '0006:0008', '开关:调级控制', '3', '1', 'source', 'Z501A.jpg', 'As a remote control device, Z501A can be bound with on/off or dimmer devices for users to control the devices wirelessly. Z-501A also comes with an emergency button for users to call for help in emergency.', '3V CR2032纽扣电池', '右侧开关off断电，重新上电后即可激活设备。', '按住“开关键” 的同时给设备上电，开始恢复出厂值，指示灯共闪烁20次，之后重新操作组合键就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('82', '9', 'Z501B', 'Z501B 4-Key Remote Controller with On/Off Switch (2-Set)', '0006', '开关', '3', '1', 'source', 'Z501B.jpg', 'As a remote control device, Z-501B can be bound with on/off devices for users to control the devices wirelessly.', '3V CR2032纽扣电池', '右侧开关off断电，重新上电后即可激活设备。', '按住“ON2键” 的同时给设备上电，开始恢复出厂值，指示灯共闪烁20次，之后重新操作组合键就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('83', '9', 'Z501C', 'Z501C 4-Key Remote Controller with Level Control & On/Off Switch', '0006:0008', '开关:调级控制', '3', '1', 'source', 'Z501C.jpg', 'Z501C can be bound with on/off or dimmer devices for users to control the devices wirelessly.', '3V CR2033纽扣电池', '右侧开关off断电，重新上电后即可激活设备。', '按住“ ON/OFF1键” 的同时给设备上电，开始恢复出厂值，指示灯共闪烁20次，之后重新操作组合键就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('84', '10', 'Z210', 'Z210 Infrared Gateway with 1 External Port', null, null, null, '1', null, 'Z210C.jpg', 'Z-210 is the center of the security system to manage security devices. It can also learn and send IR signals for users to control their home appliances such as TV and DVD player.', '12V直流电源', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '使用红外转接器控制家用电器时，需要在app上导入红外数据，若无红外数据时，则需要通过IR学习器或手机app手动学习。');
INSERT INTO `modenodelib_en` VALUES ('85', '10', 'Z211', 'Z211  Infrared Gateway with 4 External Ports', null, null, null, '1', null, 'Z211.jpg', 'Z-211 is a device for IR learning and IR controlling. After learning IR codes, Z-211 can control home appliances which receives IR signals. Also, Z-211 can control ZigBee devices when it receives IR signals from a regular remote control.', '12V直流电源', '不需要激活', '按住绑定键（蓝色按键），同时给设备上电，开始恢复出厂设置，直到状态指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '使用红外转接器控制家用电器时，需要在app上导入红外数据，若无红外数据时，则需要通过IR学习器或手机app手动学习。');
INSERT INTO `modenodelib_en` VALUES ('86', '11', 'Z203', 'Z203  Cloud Wireless Smart Home Center', null, null, null, '1', null, 'Z203.jpg', 'As the main controller of Netvox Cloud-based Wireless Smart Home System, Z-203 is a perfect combination of cloud services, Wifi technology, and ZigBee Home Automation solutions. Z-203 enables users to monitor their lights, curtains, and home appliances via smart home app and Wifi connection, and users can easily monitor their home with 2G/3G network even they are far away.', '12V直流电源', '不需要激活', '详见203说明书', 'Z203可用12V直流电源供电，给Z203上电后，打开手机的WiFi功能，即可搜索到Z203的无线信号。外网网线接入Z203的WAN口，LAN口可直接接IPcamera或电脑，当家中有多个 IPcamera时，需要增加集线器。');
INSERT INTO `modenodelib_en` VALUES ('87', '11', 'Z103AC', 'Z103AC USB Coordinator', null, null, null, '1', null, 'Z103AC.jpg', 'Z103AC is a coordinator with USB port. The devicce can communicate with other Zigbee modules through the port, and thus control the entire system.', 'USB', '不需要激活', '按住PermitJoin键的同时给Z103设备上电，设备指示灯处于快速闪烁状态，表示恢复出厂设置完成，设备重新上电就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('88', '11', 'Z103AR', 'Z103AR USB Router', null, null, null, '1', null, 'Z103AR.jpg', 'Z103AR is a Router with USB port，which can transmit the information to other devices with the USB port driver.', 'USB', '不需要激活', '按住PermitJoin键的同时给Z103设备上电，设备指示灯处于快速闪烁状态，表示恢复出厂设置完成，设备重新上电就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('89', '11', 'Z201B', 'Z201B Coordinator with Security Center (CIE)', null, null, null, '1', null, 'Z201B.jpg', 'Z201B is a HA Coordinator & CIE. With the USB port driver, the device can communicate with other Zigbee modules through the port and allow the enrolling of other devices, thus to control the entire system;', '12V直流电源', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('90', '11', 'Z201C', 'Z201C ZigBee802.15.4 Coord ', null, null, null, '1', null, 'Z201C.jpg', 'Z201C is HA coordinator with USB port,  With the USB port driver, the device can communicate with other Zigbee modules through the port, thus to control the entire system;', '12V直流电源', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('91', '11', 'Z201R HA', 'Z201R HA ZigBee TCP/IP Gateway', null, null, null, '1', null, 'Z201R.jpg', 'HA Repeater', '12V直流电源', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('92', '11', 'Z201R', 'Z201R ZigBee 802.15.4 Router', null, null, null, '1', null, 'Z201R.jpg', 'Location Reference Node', '12V直流电源', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('93', '4', 'Z812A', 'Z812A Programmable 8-Button Scene Control Keypad', '0006', '开关', '3', '1', 'source', 'Z812A.jpg', 'Z-812A is Multi-port Wireless  In-wall Switch Module. It can bind with the devices carrying the function of on/off switch control and enables wireless on/off control.', '100～240V的交流电源', '不需要激活', '上电以后，同时按住OFF3和OFF4键，按住5秒，网络灯灭，然后放开按键，开始恢复出厂值，直到网络灯开始闪烁，表示恢复完成。之后设备将自动复位，可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('94', '4', 'Z812B', 'Z812B Battery Operated 8-Button Control Keypad', '0006:0008', '开关:调级控制', '3', '1', 'source', 'Z812B.jpg', 'Z-812B is Multi-port Wireless  In-wall Switch Module. It can bind with the devices carrying the function of on/off switch control and enables wireless on/off control.', '100～240V的交流电源', '同时按住第一行两个按键即可激活设备。', '同时按住按键3和按键4达3秒，指示灯闪烁1次提示设备开始恢复出厂设置，恢复成功指示灯快闪10次，不成功指示灯无动作。恢复出厂设置之后设备自动重启。', '无');
INSERT INTO `modenodelib_en` VALUES ('95', '7', 'Z801TXB', 'Z801TXB Sensor Signal Tx Module', '', '', '', '1', '', 'Z801TXB.jpg', 'Z801TXB is a pulse Signal Detector，it can be connected with 5 equipment.if Z801txb detects the signal,it will send the command to the security of the ENROLL control center .', '5V直流电源或2节1.5V电池', '不需要激活', '按住绑定键的同时给设备上电，LED2闪烁20次，表示复位完成，之后重新上电，Z801 TXB可以重新加网。 ', '无');
INSERT INTO `modenodelib_en` VALUES ('96', '0', 'Z801RX', 'Z801RX  RX Relay Board', '0006', '开关', '3', '1', 'dest', 'Z801RX.jpg', 'Z801RX is relay board.it is used to control the switch of the equipment,mainly for controling the switch of household appliance.  ', '2节1.2V电池', '不需要激活', '1.  按住绑定键的同时给设备上电；2.  复位完成则状态指示灯快速灯闪烁；3.  重新上电，Z801RX可以开始重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('100', '11', 'Z508', 'Z508 In Home Display', null, null, null, '1', null, 'Z508.jpg', 'Z508 is a Smart In-Home Display for smart home and household energy monitoring. Z-508 works as Smart Home Control Center and allows users to review current and historical energy consumption data.By through Zig-Butler Smart Scheme, users can select and switch any preferable mode setting. With the implementation of CIE, Z-508 is capable of managing Home Security System and tells security related message on its display.', '5V直流电源或2节1.5V电池', '不需要激活', '详见Z508说明书', '无');
INSERT INTO `modenodelib_en` VALUES ('101', '0', 'Z825A', 'Z825A Touch Panel Switch with Power Meter (1-Gang)', '0006', '开关', '3', '1', 'dest', 'Z825A.jpg', 'Z-825A is One Gang In-Wall Switch with Touch Panel and fits with 86*86 junction box and replaces the existing wall switch. It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-825A by ZigBee Remote Control, Smart Phone, On/Off Switch and so on.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('102', '0', 'Z825B', 'Z825B Touch Panel Switch with Power Meter (2-Gang)', '0006', '开关', '3', '1', 'dest', 'Z825B.jpg', 'Z-825B is Two Gang In-Wall Switch with Touch Panel and fits with 86*86 junction box and replaces the existing wall switch. It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-825A by ZigBee Remote Control, Smart Phone, On/Off Switch and so on.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('103', '0', 'Z825D', 'Z825D Touch Panel Dimmer Switch with Power Meter (1-Gang)', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z825D.jpg', 'Z-825D is One Gang In-Wall Dimmer Switch with Touch Panel and fits with 86*86 junction box and replaces the existing wall switch. It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-825A by ZigBee Remote Control, Smart Phone, On/Off Switch and so on.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('104', '2', 'Z725A', 'Z725A Temperature and Humidity Sensor with Solar Battery (Outdoor)', null, null, null, '1', null, 'Z725A.jpg', 'Z-725A is Wireless H/T Sensor and Ultraviolet Detector with Solar Battery Panel. It comes with water-proof enclosure for protective purpose. It is used to measure and collect Humidity, Temperature and Ultraviolet data in the surrounding and direct to the data collector for display.', '太阳能充电电池', '不需要激活', '设备上电后，同时按住“绑定键”和“设置键” 5秒，开始恢复出厂值，LED快闪10次，表示恢复出厂值完成，设备自动复位寻找加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('106', '11', 'Z725R', 'Z725R Repeater with Solar Battery (Outdoor)', null, null, null, '1', null, 'Z725R.jpg', 'Z-725R is a Outdoor type Wireless Repeater，which is use to expand the network range。 ', '太阳能充电电池', '不需要激活', '设备上电后，同时按住绑定键和辅助键5秒，状态灯闪烁1次后，开始恢复出厂值，LED快闪20次，表示恢复出厂值完成，设备自动复位寻找加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('107', '0', 'Z962A', 'Z962A One Gang Wall Switch with Touch Panel', '0006', '开关', '3', '1', 'dest', 'Z962A.jpg', 'Z-962A is One Gang In-Wall Switch with Touch Panel and fits with 86*86 junction box and replaces the existing wall switch. It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-962A by ZigBee Remote Control, Smart Phone, On/Off Switch and so on.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('108', '0', 'Z962B', 'Z962B Two Gang Wall Switch with Touch Panel', '0006', '开关', '3', '1', 'dest', 'Z962B.jpg', 'Z-962B is Two Gang In-Wall Switch with Touch Panel and fits with 86*86 junction box and replaces the existing wall switch. It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-962B by ZigBee Remote Control, Smart Phone, On/Off Switch and so on.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('109', '0', 'Z962C', 'Z962C Three Gang Wall Switch with Touch Panel', '0006', '开关', '3', '1', 'dest', 'Z962C.jpg', 'Z-962C is Three Gang In-Wall Switch with Touch Panel and fits with 86*86 junction box and replaces the existing wall switch. It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-962C by ZigBee Remote Control, Smart Phone, On/Off Switch and so on.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('110', '0', 'Z962D', 'Z962D Scene or Mode Selector 1 key with Touch Panel', null, null, null, '1', null, 'Z962D.jpg', 'Z-962D is Scene and Mode control keypad, by through 1 touch keypad, users can add scene/mode, keep scene storage and manage mode control to carry out memorizing 1 scence control. It also supports Netvox mode control functionality.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib_en` VALUES ('111', '0', 'Z962E', 'Z962E Scene or Mode Selector 2 keys with Touch Panel', null, null, null, '1', null, 'Z962E.jpg', 'Z-962E is Scene and Mode control keypad, by through 2 touch keypad, users can add scene/mode, keep scene storage and manage mode control to carry out memorizing 2 scence control. It also supports Netvox mode control functionality.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib_en` VALUES ('112', '0', 'Z962F', 'Z962F Scene or Mode Selector 3 keys with Touch Panel', null, null, null, '1', null, 'Z962F.jpg', 'Z-962F is Scene and Mode control keypad, by through 3 touch keypad, users can add scene/mode, keep scene storage and manage mode control to carry out memorizing 3 scence control. It also supports Netvox mode control functionality.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib_en` VALUES ('113', '0', 'Z962G', 'Z962G One Scene or Mode Selector And One Switch with Touch Panel', '0006', '开关', '3', '1', 'dest', 'Z962G.jpg', 'Z962G is One Gang Touch Switch and 1 Scence Control Touch Button and fits with 86*86 junction box and replaces the existing wall switch. It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-962G by ZigBee Remote Control, Smart Phone, On/Off Switch and so on. Users can manage scence/mode control with the secnce control button.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib_en` VALUES ('114', '0', 'Z962H', 'Z962H Two Scene or Mode Selector And One Switch with Touch Panel', '0006', '开关', '3', '1', 'dest', 'Z962H.jpg', 'Z962H is One Gang Touch Switch and 2 Scence Control Touch Button and fits with 86*86 junction box and replaces the existing wall switch. It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-825A by ZigBee Remote Control, Smart Phone, On/Off Switch and so on. Users can manage scence/mode control with the secnce control button.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib_en` VALUES ('115', '0', 'Z962I', 'Z962I One Scene or Mode Selector And Two Switch with Touch Panel', '0006', '开关', '3', '1', 'dest', 'Z962I.jpg', 'Z962I is Two Gang Touch Switch and 1 Scence Control Touch Button and fits with 86*86 junction box and replaces the existing wall switch. It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-825A by ZigBee Remote Control, Smart Phone, On/Off Switch and so on. Users can manage scence/mode control with the secnce control button.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib_en` VALUES ('116', '7', 'Z801WLS', 'Z801WLS Water Sensor', '', '', '', '1', '', 'Z801WLS.jpg', 'Z-801WLS is a  a water Sensor,which is used for monitoring leakage situation, and send the alarm information to the security center immediately.', '5V直流电源或2节1.5V电池', '不需要激活', '按住绑定键的同时给设备上电，LED2闪烁20次，表示复位完成，之后重新上电，Z801WLS可以重新加网。 ', '无');
INSERT INTO `modenodelib_en` VALUES ('117', '7', 'ZB05A', 'ZB05A Door Lock with Fingerprint Identification', '0101', '开关锁', '3', '1', 'dest', 'ZB05A.jpg', 'Z-B05A can control lock/unlock of the door that applied to it including mechanical key, access code, IC card, finger print, remote access ect.. By through ZigBee wireless networking, users can monitor and control the door status, manage user ID and passwork anywhere you are. Intellectualized door lock design supports reliable access code safeguard to guarantee home surveillance safety.', '8节AA1.5V电池', '不需要激活', '1.按住网络键的同时给设备上电，上电完请不要立即松开按键，等到网络灯开始快速闪烁，提示复位成功；2.重新上电，ZB05A可以开始重新加网。', '1、初次使用时，需要注册zigbee：在门打开的状态下，打开内侧用（主锁身）的电池盖和外盒，在右侧下端按“锁卡注册键”。然后再按下面的“ZB注册键”（靠上的按键）。按“*”键，可听到“哔呖呖”的设置音，设置结束。2、默认都为加密的方式，第一次使用时，需要在app中→设备管理模块→进入门锁的设置页面，设置新的用户和密码，才可以对锁进行开关控制。门锁默认有6个空用户，注册新用户前，需要先将这些用户删除。超级密码默认为：123456.usercode即用户名为1-254的数字。');
INSERT INTO `modenodelib_en` VALUES ('118', '4', 'Z801TX', 'Z801TX TX Switch Board', '0006', '开关', '3', '1', 'source', 'Z801TX.jpg', 'Z801TX is used as a end device in the zigBee network,which doesn\'t allow other devices to be its child device.It can be extended with five buttons. When the button is trigged，it can send commands to bound device and control its on/off at the detection of a signal.', '无', '无', '无', '无');
INSERT INTO `modenodelib_en` VALUES ('119', '7', 'Z311J', 'Z311J Window Sensor with On/Off Switch', '0006', '开关', '3', '1', 'source', 'Z311J.jpg', 'Z-311J is a window/door sensor. When window/door status is changed, it can control its bound device according to the configurations. Z-302J also works as a detection sensor of the security system- when window/door is open or closed, it also notifies security center with the alarm messages, while sends the information of normal status to CIE when the window/door is closed.', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', 'Z311J的本体与门磁的磁铁部分别安装在门或窗户的缝隙的两边，当门或窗被打开，它将发出报警信息给安防中心设备( 即CIE )，当门或窗被关闭好，它会发送出状态恢复正常的信息给CIE。该设备也可以用于冰箱、抽屉等处，用于检测冰箱或抽屉等是否被打开。需要注意防水。');
INSERT INTO `modenodelib_en` VALUES ('120', '0', 'ZC07', 'ZC07 Dimmable LED Bulb', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'ZC07.jpg', 'ZC07 is a robust ZigBee enabled dimmable LED bulb. It can achieve 256 dimmer light levels and can be controlled wirelessly. By methods such as binding devices, wireless remote control or software operation, it can control light on/off and dimmer. As a 5W bulb, ZC07 applies constant current control mode withthe input voltage from AC 100V to 240V. The inputs of 100V to 240V can achieve the same lighting effects.', '100～240V的交流电源', '不需要激活', '同时长按测试键和绑定键5s，5s时间到后指示灯闪10次后就恢复出厂设置成功，此时就可以重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('121', '7', 'Z311W', 'Z311W Water Sensor', null, null, null, '1', '', 'Z311W.jpg', 'Z311W is a water sensor,which is used for monitoring leakage situation and sending the alarm information to the security center immediately.', '无需外加电源,产品使用内部的3V钮扣电池供电。', '同时按住绑定键和辅助键，看到红色指示灯灯闪烁2次，此时松开按键。', '同时按住绑定键和辅助键，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('122', '6', 'Z811B', 'Z811B Curtain Controller (2- Gang)', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z811B.jpg', 'Z811B is aTwo Gang curtain controller. It can control the curtains on/off and level through its own key, bound devices or software. ', '100-240V AC 50/60HZ电源供电', '不需要激活', '方法一：按住“绑定键（logo旁的小按钮）”上电，指示灯快闪，表示恢复出厂设置完成，再次重新上电后即可。方法二：长按住“绑定键（logo旁的小按钮）”键的15秒待指示灯闪烁一次（此间指示灯3秒、10秒、15秒各闪烁一次），松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，自动重启就可以重新加网了。', 'Lin接火线，N线是零线接电机的零线（一般为蓝线）。EP1：Lout1接电机正转输入（一般为棕线)，Lout2接电机反转输入（一般为黑线）。EP2：Lout3接电机正转输入（一般为棕线)，Lout4接电机反转输入（一般为黑线）。在设备执行窗帘控制功能前,都应先进行“学习上下限”的学习，学习方法请详见设备说明书或在app的设备管理模块，该设备的设置页面进行学习。');
INSERT INTO `modenodelib_en` VALUES ('123', '0', 'Z825I', 'Z825I Touch Panel Programmable Scene Selector (6-Gang)', '', '', '', '1', '', 'Z825I.jpg', 'As a Scene Selector & Mode Selector，Z825I is equipped with six touch buttons. Each can be configured to add scene or control mode (each button can at most at one scene or configure four modes.)', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib_en` VALUES ('124', '0', 'Z825J', 'Z825J Touch Panel 3-Gang Switch with Power Meter and 3-Gang Programmable Scene Selector', '0006', '开关', '3', '1', 'dest', 'Z825J.jpg', 'Z825J is a three-gang touch switch and three-gang Scene Selector,which can be directly installed in 86 junction box to replace the normal wall switch.It monitors and calculates Current, Voltage, Power and Engery. Users can control Z-825J by ZigBee Remote Control, Smart Phone, On/Off Switch and so on. Users can manage scence/mode control with the secnce control button.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib_en` VALUES ('125', '0', 'Z825Q', 'Z825QTouch Panel Switch with Power Meter (3-Gang)', '0006', '开关', '3', '1', 'dest', 'Z825Q.jpg', 'Z825Q is three-gang local switch controller with single livewire for power. It can control its on/off through being bound with  devices with on/off function to control it or through its own keys. Its three-gang on/off is controlled by three keys.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络青绿色指示灯闪烁一次（期间网络青绿色指示灯闪烁4次，其中刚按下闪一次，3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络青绿色指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会进入睡眠模式，短按绑定键Z825Q可重新加网。 ', 'Z825Q具有单路输入，三路输出的能力，接线时，将一种线（N或L，来自市电）接入输入口，然后在三路输出口引出同种类型的输出线（给设备供电），将插头接入到AC  100-240V的电源，给设备上电。');
INSERT INTO `modenodelib_en` VALUES ('126', '1', 'Z816I', 'Z816I  China Type Wall Socket with Power Meter (5-pin)', '0006', '开关', '3', '1', 'dest', 'Z816I.jpg', 'Z816I is a China type wireless current-detection smart wall socket, which can be directly installed in 86 junction box to replace the traditional wall socket. Its on/off can be controlled with its own keys or through its bound devices and software.Via software, users are able to monitor the load current, voltage and power and kilo-watt-hour consumption of the attached appliance.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('127', '5', 'Z817D', 'Z817D  Ceiling Motion Detector', '0006', '开关', '3', '1', 'source', 'Z817D.jpg', 'Z817D has the on/off switch by the infrared detection.When it detects the IR of moving objects,it can control on/off of bound devices.', 'AC100-240V 50/60HZ供电', '不需要激活', '给设备上电，同时按住“按键1”和“按键2”5秒；复位完成则led闪烁30次后自动重新请求加网；', '无');
INSERT INTO `modenodelib_en` VALUES ('128', '11', 'Z800R', 'Z800R Plug Repeater with Outlet', null, null, null, '1', null, 'Z800R.jpg', 'Z800R is a Router Repeaters with a socket,which allow other devices as his children.It can extend the communication distance for other devices.', 'AC 100-250V的电源', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重新上电就可以加入新的网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('129', '1', 'Z809C', 'Z809C Plug Repeater with Backup Battery', null, null, null, '1', null, 'Z809C.jpg', 'Z809C is a Router Repeaters with a socket,which allow other devices as his children.It can extend the communication distance for other devices.', 'AC 100-240V的电源或电池供电（内置电池）', '不需要激活', '长按绑定键15s，15s时间到状态闪1次后放开绑定键，两秒内短按绑定键就状态灯闪烁10次，并自动复位。其中长按绑定键3s，10s和15s的时候状态灯都会依次闪烁一次，以提示所按的时间长短。', '无');
INSERT INTO `modenodelib_en` VALUES ('130', '1', 'Z809D', 'Z809D Plug Repeater with Power Amplifier & Backup Battery', '', '', '', '1', '', 'Z809D.jpg', 'Z809D is a Router Repeaters with a socket,which allow other devices as his children.It can extend the communication distance for other devices.', 'AC 100-240V的电源或电池供电（内置电池）', '不需要激活', '长按绑定键15s，15s时间到指示闪1次后放开绑定键，两秒内短按绑定键就指示灯闪烁10次，并自动重启设备（如果仅仅用电池供电不能重启）。', '无');
INSERT INTO `modenodelib_en` VALUES ('131', '9', 'Z501AE3ED', 'Z501A 4-Key Remote Controller with Arm/Disarm, On/Off, & Panic Button', '0006:0008', '开关:调级控制', '3', '1', 'source', 'Z501A.jpg', 'As a remote control device, Z501A can be bound with on/off switch or dimmer devices for users to control the devices wirelessly. Z-501A also comes with an emergency button for users to call for help in emergency.', '3V CR2032纽扣电池', '右侧开关off断电，重新上电后即可激活设备。', '按住“开关键” 的同时给设备上电，开始恢复出厂值，指示灯共闪烁20次，之后重新操作组合键就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('132', '9', 'Z501BE3ED', 'Z501B 4-Key Remote Controller with On/Off Switch (2-Set)', '0006', '开关', '3', '1', 'source', 'Z501B.jpg', 'Z510B is a remote control,which can be bound to control on/off of other devices.', '3V CR2032纽扣电池', '右侧开关off断电，重新上电后即可激活设备。', '按住“ON2键” 的同时给设备上电，开始恢复出厂值，指示灯共闪烁20次，之后重新操作组合键就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('133', '9', 'Z501CE3ED', 'Z501C 4-Key Remote Controller with Level Control & On/Off Switch', '0006:0008', '开关:调级控制', '3', '1', 'source', 'Z501C.jpg', 'As a remote control device, Z501C can be bound with on/off or dimmer devices for users to control the devices wirelessly. ', '3V CR2033纽扣电池', '右侧开关off断电，重新上电后即可激活设备。', '按住“ ON/OFF1键” 的同时给设备上电，开始恢复出厂值，指示灯共闪烁20次，之后重新操作组合键就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('134', '7', 'ZB02I', 'ZB02I  Emergency Push Button', '', '', '', '1', '', 'ZB02E.jpg', 'ZB02I is an emergency push button working as a detection device in the security system. It can be put on the wall or anyplace in the room .When users in danger are in need of emergency assistance, users can press the alarm button for ZB02I to immediately send out an alarm message to the security center, which will notify the siren to make sound or light to alert family members to offer immediate help. ', 'AAA电池供电', '短按绑定键，若设备仍在网络状态，则绿色指示灯闪烁5次，表示激活成功。', '按住绑定键的同时给设备上电，进行恢复出厂设置，看到指示灯闪烁，表示恢复完成。', '无');
INSERT INTO `modenodelib_en` VALUES ('137', '0', 'Z802', 'Z802 Two Way Switching Load module', '0006', '开关', '3', '1', 'dest', 'Z802.jpg', 'Z802 can be bound with on/off switch for users to control the devices wirelessly.User can switch on/off the appliances attached to it through mechnical switch or wirelessly. Users may also use ZiG-BUTLER to check the current status of the device and make corresponding control.', '100-240VAC 50/60HZ 电源供电', '不需要激活', '按住绑定键的同时，给设备上电，开始恢复出厂值，直到LED1指示灯开始闪烁，表示恢复完成。之后重新上电，Z802可以重新加网了', '无');
INSERT INTO `modenodelib_en` VALUES ('138', '4', 'ZB02J', 'ZB02J Wireless Scene & Mode Selector (3-Key)', '', '', '', '1', '', 'ZB02C.jpg', 'ZB02J is a three-gang scene button, which can be bound with three scenes for users to control them.', '无需外加电源,产品使用内部的2节7号电池供电', '短按绑定键（led网络灯闪5下）', '按住绑定键不松开，装入电池，进行恢复出厂设置，看到状态灯闪烁，表示恢复完成，然后松开按键，拿出电池', '无');
INSERT INTO `modenodelib_en` VALUES ('139', '0', 'Z815A', 'Z815A Wall Switch with Power Meter (1-Gang)', '0006', '开关', '3', '1', 'dest', 'Z815I.jpg', 'Z815A is a one-gang Output switch,which can be directly installed in 86 junction box to replace the existing wall switch.It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-815A by ZigBee Remote Control, Smart Phone, On/Off Switch and other methods.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('140', '0', 'Z815B', 'Z815B Wall Switch with Power Meter (2-Gang)', '0006', '开关', '3', '1', 'dest', 'Z815J.jpg', 'Z815B is a two-gang Output switch,which can be directly installed in 86 junction box to replace the existing wall switch.It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-815B by ZigBee Remote Control, Smart Phone, On/Off Switch and other methods.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('141', '0', 'Z815C', 'Z815C Wall Switch with Power Meter (3-Gang)', '0006', '开关', '3', '1', 'dest', 'Z815K.jpg', 'Z815C is a three-gang Output switch,which can be directly installed in 86 junction box to replace the existing wall switch.It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-815C by ZigBee Remote Control, Smart Phone, On/Off Switch and other methods.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('142', '0', 'Z815D', 'Z815D  Wall Dimmer Switch with Power Meter (1-Gang)', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z815L.jpg', 'Z815D is a one-gang Output dimmer which can control on/off and dimmer and be directly installed in 86 junction box to replace the existing wall switch.It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-815D by ZigBee Remote Control, Smart Phone, On/Off Switch and other methods.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('143', '0', 'Z815E', 'Z815E Wall Dimmer Switch with Power Meter (2-Gang)', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z815M.jpg', 'Z815E is a two-gang Output dimmer which can control on/off and dimmer and be directly installed in 86 junction box to replace the existing wall switch.It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-815D by ZigBee Remote Control, Smart Phone, On/Off Switch and other methods.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('144', '0', 'Z826C', 'Z826C Touch Panel Switch with Power Meter (3-Gang+1 Live Wire Power Supply)', '0006', '开关', '3', '1', 'dest', 'Z825Q.jpg', 'Z826C is three-gang local switch controller with single livewire for power. It can control its on/off through being bound with devices with on/off function to control it or through its own keys. Its three-gang on/off is controlled by three keys.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络青绿色指示灯闪烁一次（期间网络青绿色指示灯闪烁4次，其中刚按下闪一次，3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络青绿色指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会进入睡眠模式，短按绑定键Z826C可重新加网。 ', 'Z826C具有单路输入，三路输出的能力，接线时，将一种线（N或L，来自市电）接入输入口，然后在三路输出口引出同种类型的输出线（给设备供电），将插头接入到AC  100-240V的电源，给设备上电。');
INSERT INTO `modenodelib_en` VALUES ('145', '11', 'Z206', 'Z206 Cloud-Based Wireless Smart Home Controller', '', '', '', '1', '', 'Z206.jpg', 'As the main controller of Netvox Cloud-based Wireless Smart Home System, Z-206 is a perfect combination of cloud services, Wifi technology, and ZigBee Home Automation solutions. Z-206 enables users to monitor their lights, curtains, and home appliances via smart home app and Wifi connection, and users can easily monitor their home with 2G/3G network even they are far away.', '使用DC 12V  1.5A电源适配器，接入100-220V的电源', '不需要激活', '详见206说明书', 'Z206作为奈伯思智能家居系统的核心网关，率先实现云端技术、WiFi移动联网技术与ZigBee智能家居物联网术的完美结合，让用户只需要通过手机APP连接Wifi即可控制家里的灯光、窗帘、各种电器等设备的开关，出门在外通过2G/3G网络可监控家中的一切变化。');

/* 2014.12.15*/
update userordermain set userid=-2  where userid=-1 and housename= 'NetvoxSmart';
update userordermain set userid=-2  where userid=-1 and housename= 'Home';
update userordermain set userid=-2  where userid=-1 and housename= 'Smart Lights';


/*modeactlib表新增UniqueNameEN字段*/
ALTER TABLE `modeactlib`
ADD COLUMN `UniqueNameEN`  varchar(255) NULL AFTER `UniqueName`;

/*往modeactlib uniquenameen里添加*/
UPDATE `modeactlib` SET `ID`='1', `ActName`='TurnOFF', `ExtentionSet`='-1', `ScenePara`='06000100', `UniqueName`='关', `UniqueNameEN`='TurnOFF', `DataType`='1', `DeviceID`='0002', `Groupabled`='1', `GroupLevel`='0', `Sceneabled`='1' WHERE (`ID`='1');
UPDATE `modeactlib` SET `ID`='2', `ActName`='TurnOn', `ExtentionSet`='-1', `ScenePara`='06000101', `UniqueName`='开', `UniqueNameEN`='TurnOn', `DataType`='1', `DeviceID`='0002', `Groupabled`='1', `GroupLevel`='0', `Sceneabled`='1' WHERE (`ID`='2');
UPDATE `modeactlib` SET `ID`='3', `ActName`='Toggle', `ExtentionSet`='-1', `ScenePara`='', `UniqueName`='转换', `UniqueNameEN`='Toggle', `DataType`='1', `DeviceID`='0002', `Groupabled`='1', `GroupLevel`='0', `Sceneabled`='0' WHERE (`ID`='3');
UPDATE `modeactlib` SET `ID`='4', `ActName`='MoveToLevelWithOnOff', `ExtentionSet`='20', `ScenePara`='0600010108000133', `UniqueName`='调级20%', `UniqueNameEN`='MoveToLevel 20%', `DataType`='2', `DeviceID`='0003', `Groupabled`='1', `GroupLevel`='1', `Sceneabled`='1' WHERE (`ID`='4');
UPDATE `modeactlib` SET `ID`='5', `ActName`='Disarm', `ExtentionSet`='-1', `ScenePara`='', `UniqueName`='撤防', `UniqueNameEN`='Disarm', `DataType`='1', `DeviceID`='0400', `Groupabled`='0', `GroupLevel`='2', `Sceneabled`='0' WHERE (`ID`='5');
UPDATE `modeactlib` SET `ID`='6', `ActName`='ArmAllZone', `ExtentionSet`='-1', `ScenePara`='', `UniqueName`='布防', `UniqueNameEN`='ArmAllZone', `DataType`='1', `DeviceID`='0400', `Groupabled`='0', `GroupLevel`='2', `Sceneabled`='0' WHERE (`ID`='6');
UPDATE `modeactlib` SET `ID`='7', `ActName`='ArmDayZone', `ExtentionSet`='-1', `ScenePara`='', `UniqueName`='布防（day）', `UniqueNameEN`='ArmDayZone', `DataType`='1', `DeviceID`='0400', `Groupabled`='0', `GroupLevel`='2', `Sceneabled`='0' WHERE (`ID`='7');
UPDATE `modeactlib` SET `ID`='8', `ActName`='ArmNightZone', `ExtentionSet`='-1', `ScenePara`='', `UniqueName`='布防（night）', `UniqueNameEN`='ArmNightZone', `DataType`='1', `DeviceID`='0400', `Groupabled`='0', `GroupLevel`='2', `Sceneabled`='0' WHERE (`ID`='8');
UPDATE `modeactlib` SET `ID`='9', `ActName`='CIEBypassZone', `ExtentionSet`='-1', `ScenePara`='', `UniqueName`='禁用', `UniqueNameEN`='CIEBypassZone', `DataType`='1', `DeviceID`='0402', `Groupabled`='0', `GroupLevel`='3', `Sceneabled`='0' WHERE (`ID`='9');
UPDATE `modeactlib` SET `ID`='10', `ActName`='CIEUnBypassZone', `ExtentionSet`='-1', `ScenePara`='', `UniqueName`='启用', `UniqueNameEN`='CIEUnBypassZone', `DataType`='1', `DeviceID`='0402', `Groupabled`='0', `GroupLevel`='3', `Sceneabled`='0' WHERE (`ID`='10');
UPDATE `modeactlib` SET `ID`='16', `ActName`='ApplyDeviceIR', `ExtentionSet`='-1', `ScenePara`='', `UniqueName`='发送红外信号', `UniqueNameEN`='ApplyDeviceIR', `DataType`='3', `DeviceID`='0008', `Groupabled`='0', `GroupLevel`='4', `Sceneabled`='0' WHERE (`ID`='16');
UPDATE `modeactlib` SET `ID`='17', `ActName`='TurnOFF', `ExtentionSet`='-1', `ScenePara`='06000100', `UniqueName`='关', `UniqueNameEN`='TurnOFF', `DataType`='1', `DeviceID`='0003', `Groupabled`='0', `GroupLevel`='0', `Sceneabled`='1' WHERE (`ID`='17');
UPDATE `modeactlib` SET `ID`='18', `ActName`='TurnOn', `ExtentionSet`='-1', `ScenePara`='06000101', `UniqueName`='开', `UniqueNameEN`='TurnOn', `DataType`='1', `DeviceID`='0003', `Groupabled`='0', `GroupLevel`='0', `Sceneabled`='1' WHERE (`ID`='18');
UPDATE `modeactlib` SET `ID`='19', `ActName`='Toggle', `ExtentionSet`='-1', `ScenePara`='', `UniqueName`='转换', `UniqueNameEN`='Toggle', `DataType`='1', `DeviceID`='0003', `Groupabled`='0', `GroupLevel`='0', `Sceneabled`='0' WHERE (`ID`='19');
UPDATE `modeactlib` SET `ID`='21', `ActName`='TurnOFF', `ExtentionSet`='-1', `ScenePara`='06000100', `UniqueName`='关', `UniqueNameEN`='TurnOFF', `DataType`='1', `DeviceID`='0009', `Groupabled`='0', `GroupLevel`='0', `Sceneabled`='1' WHERE (`ID`='21');
UPDATE `modeactlib` SET `ID`='22', `ActName`='TurnOn', `ExtentionSet`='-1', `ScenePara`='06000101', `UniqueName`='开', `UniqueNameEN`='TurnOn', `DataType`='1', `DeviceID`='0009', `Groupabled`='0', `GroupLevel`='0', `Sceneabled`='1' WHERE (`ID`='22');
UPDATE `modeactlib` SET `ID`='23', `ActName`='Toggle', `ExtentionSet`='-1', `ScenePara`='', `UniqueName`='转换', `UniqueNameEN`='Toggle', `DataType`='1', `DeviceID`='0009', `Groupabled`='0', `GroupLevel`='0', `Sceneabled`='0' WHERE (`ID`='23');
UPDATE `modeactlib` SET `ID`='24', `ActName`='TurnOFF', `ExtentionSet`='-1', `ScenePara`='06000100', `UniqueName`='关', `UniqueNameEN`='TurnOFF', `DataType`='1', `DeviceID`='0100', `Groupabled`='0', `GroupLevel`='0', `Sceneabled`='1' WHERE (`ID`='24');
UPDATE `modeactlib` SET `ID`='25', `ActName`='TurnOn', `ExtentionSet`='-1', `ScenePara`='06000101', `UniqueName`='开', `UniqueNameEN`='TurnOn', `DataType`='1', `DeviceID`='0100', `Groupabled`='0', `GroupLevel`='0', `Sceneabled`='1' WHERE (`ID`='25');
UPDATE `modeactlib` SET `ID`='26', `ActName`='Toggle', `ExtentionSet`='-1', `ScenePara`='', `UniqueName`='转换', `UniqueNameEN`='Toggle', `DataType`='1', `DeviceID`='0100', `Groupabled`='0', `GroupLevel`='0', `Sceneabled`='0' WHERE (`ID`='26');
UPDATE `modeactlib` SET `ID`='27', `ActName`='TurnOFF', `ExtentionSet`='-1', `ScenePara`='06000100', `UniqueName`='关', `UniqueNameEN`='TurnOFF', `DataType`='1', `DeviceID`='0101', `Groupabled`='0', `GroupLevel`='0', `Sceneabled`='1' WHERE (`ID`='27');
UPDATE `modeactlib` SET `ID`='28', `ActName`='TurnOn', `ExtentionSet`='-1', `ScenePara`='06000101', `UniqueName`='开', `UniqueNameEN`='TurnOn', `DataType`='1', `DeviceID`='0101', `Groupabled`='0', `GroupLevel`='0', `Sceneabled`='1' WHERE (`ID`='28');
UPDATE `modeactlib` SET `ID`='29', `ActName`='Toggle', `ExtentionSet`='-1', `ScenePara`='', `UniqueName`='转换', `UniqueNameEN`='Toggle', `DataType`='1', `DeviceID`='0101', `Groupabled`='0', `GroupLevel`='0', `Sceneabled`='0' WHERE (`ID`='29');
UPDATE `modeactlib` SET `ID`='30', `ActName`='MoveToLevelWithOnOff', `ExtentionSet`='20', `ScenePara`='0600010108000133', `UniqueName`='调级20%', `UniqueNameEN`='MoveToLevel 20%', `DataType`='2', `DeviceID`='0101', `Groupabled`='0', `GroupLevel`='1', `Sceneabled`='1' WHERE (`ID`='30');
UPDATE `modeactlib` SET `ID`='31', `ActName`='TurnOFF', `ExtentionSet`='-1', `ScenePara`='06000100', `UniqueName`='关', `UniqueNameEN`='TurnOFF', `DataType`='1', `DeviceID`='0102', `Groupabled`='0', `GroupLevel`='0', `Sceneabled`='1' WHERE (`ID`='31');
UPDATE `modeactlib` SET `ID`='32', `ActName`='TurnOn', `ExtentionSet`='-1', `ScenePara`='06000101', `UniqueName`='开', `UniqueNameEN`='TurnOn', `DataType`='1', `DeviceID`='0102', `Groupabled`='0', `GroupLevel`='0', `Sceneabled`='1' WHERE (`ID`='32');
UPDATE `modeactlib` SET `ID`='33', `ActName`='Toggle', `ExtentionSet`='-1', `ScenePara`='', `UniqueName`='转换', `UniqueNameEN`='Toggle', `DataType`='1', `DeviceID`='0102', `Groupabled`='0', `GroupLevel`='0', `Sceneabled`='0' WHERE (`ID`='33');
UPDATE `modeactlib` SET `ID`='34', `ActName`='TurnOFF', `ExtentionSet`='-1', `ScenePara`='06000100', `UniqueName`='关', `UniqueNameEN`='TurnOFF', `DataType`='1', `DeviceID`='0200', `Groupabled`='0', `GroupLevel`='0', `Sceneabled`='1' WHERE (`ID`='34');
UPDATE `modeactlib` SET `ID`='35', `ActName`='TurnOn', `ExtentionSet`='-1', `ScenePara`='06000101', `UniqueName`='开', `UniqueNameEN`='TurnOn', `DataType`='1', `DeviceID`='0200', `Groupabled`='0', `GroupLevel`='0', `Sceneabled`='1' WHERE (`ID`='35');
UPDATE `modeactlib` SET `ID`='36', `ActName`='Toggle', `ExtentionSet`='-1', `ScenePara`='', `UniqueName`='转换', `UniqueNameEN`='Toggle', `DataType`='1', `DeviceID`='0200', `Groupabled`='0', `GroupLevel`='0', `Sceneabled`='0' WHERE (`ID`='36');
UPDATE `modeactlib` SET `ID`='37', `ActName`='MoveToLevelWithOnOff', `ExtentionSet`='20', `ScenePara`='0600010108000133', `UniqueName`='调级20%', `UniqueNameEN`='MoveToLevel 20%', `DataType`='2', `DeviceID`='0200', `Groupabled`='0', `GroupLevel`='1', `Sceneabled`='1' WHERE (`ID`='37');
UPDATE `modeactlib` SET `ID`='38', `ActName`='MoveToLevelWithOnOff', `ExtentionSet`='40', `ScenePara`='0600010108000166', `UniqueName`='调级40%', `UniqueNameEN`='MoveToLevel 40%', `DataType`='2', `DeviceID`='0003', `Groupabled`='1', `GroupLevel`='1', `Sceneabled`='1' WHERE (`ID`='38');
UPDATE `modeactlib` SET `ID`='39', `ActName`='MoveToLevelWithOnOff', `ExtentionSet`='60', `ScenePara`='0600010108000199', `UniqueName`='调级60%', `UniqueNameEN`='MoveToLevel 60%', `DataType`='2', `DeviceID`='0003', `Groupabled`='1', `GroupLevel`='1', `Sceneabled`='1' WHERE (`ID`='39');
UPDATE `modeactlib` SET `ID`='40', `ActName`='MoveToLevelWithOnOff', `ExtentionSet`='80', `ScenePara`='06000101080001CC', `UniqueName`='调级80%', `UniqueNameEN`='MoveToLevel 80%', `DataType`='2', `DeviceID`='0003', `Groupabled`='1', `GroupLevel`='1', `Sceneabled`='1' WHERE (`ID`='40');
UPDATE `modeactlib` SET `ID`='41', `ActName`='MoveToLevelWithOnOff', `ExtentionSet`='40', `ScenePara`='0600010108000166', `UniqueName`='调级40%', `UniqueNameEN`='MoveToLevel 40%', `DataType`='2', `DeviceID`='0101', `Groupabled`='0', `GroupLevel`='1', `Sceneabled`='1' WHERE (`ID`='41');
UPDATE `modeactlib` SET `ID`='42', `ActName`='MoveToLevelWithOnOff', `ExtentionSet`='60', `ScenePara`='0600010108000199', `UniqueName`='调级60%', `UniqueNameEN`='MoveToLevel 60%', `DataType`='2', `DeviceID`='0101', `Groupabled`='0', `GroupLevel`='1', `Sceneabled`='1' WHERE (`ID`='42');
UPDATE `modeactlib` SET `ID`='43', `ActName`='MoveToLevelWithOnOff', `ExtentionSet`='80', `ScenePara`='06000101080001CC', `UniqueName`='调级80%', `UniqueNameEN`='MoveToLevel 80%', `DataType`='2', `DeviceID`='0101', `Groupabled`='0', `GroupLevel`='1', `Sceneabled`='1' WHERE (`ID`='43');
UPDATE `modeactlib` SET `ID`='44', `ActName`='MoveToLevelWithOnOff', `ExtentionSet`='40', `ScenePara`='0600010108000166', `UniqueName`='调级40%', `UniqueNameEN`='MoveToLevel 40%', `DataType`='2', `DeviceID`='0200', `Groupabled`='0', `GroupLevel`='1', `Sceneabled`='1' WHERE (`ID`='44');
UPDATE `modeactlib` SET `ID`='45', `ActName`='MoveToLevelWithOnOff', `ExtentionSet`='60', `ScenePara`='0600010108000199', `UniqueName`='调级60%', `UniqueNameEN`='MoveToLevel 60%', `DataType`='2', `DeviceID`='0200', `Groupabled`='0', `GroupLevel`='1', `Sceneabled`='1' WHERE (`ID`='45');
UPDATE `modeactlib` SET `ID`='46', `ActName`='MoveToLevelWithOnOff', `ExtentionSet`='80', `ScenePara`='06000101080001CC', `UniqueName`='调级80%', `UniqueNameEN`='MoveToLevel 80&', `DataType`='2', `DeviceID`='0200', `Groupabled`='0', `GroupLevel`='1', `Sceneabled`='1' WHERE (`ID`='46');
UPDATE `modeactlib` SET `ID`='47', `ActName`='MoveToLevelWithOnOff', `ExtentionSet`='0', `ScenePara`='0600010108000100', `UniqueName`='调级0%', `UniqueNameEN`='MoveToLevel 0%', `DataType`='2', `DeviceID`='0003', `Groupabled`='1', `GroupLevel`='1', `Sceneabled`='1' WHERE (`ID`='47');
UPDATE `modeactlib` SET `ID`='48', `ActName`='MoveToLevelWithOnOff', `ExtentionSet`='100', `ScenePara`='06000101080001FF', `UniqueName`='调级100%', `UniqueNameEN`='MoveToLevel 100%', `DataType`='2', `DeviceID`='0003', `Groupabled`='1', `GroupLevel`='1', `Sceneabled`='1' WHERE (`ID`='48');
UPDATE `modeactlib` SET `ID`='49', `ActName`='MoveToLevelWithOnOff', `ExtentionSet`='0', `ScenePara`='0600010108000100', `UniqueName`='调级0%', `UniqueNameEN`='MoveToLevel 0%', `DataType`='2', `DeviceID`='0101', `Groupabled`='0', `GroupLevel`='1', `Sceneabled`='1' WHERE (`ID`='49');
UPDATE `modeactlib` SET `ID`='50', `ActName`='MoveToLevelWithOnOff', `ExtentionSet`='100', `ScenePara`='06000101080001FF', `UniqueName`='调级100%', `UniqueNameEN`='MoveToLevel 100%', `DataType`='2', `DeviceID`='0101', `Groupabled`='0', `GroupLevel`='1', `Sceneabled`='1' WHERE (`ID`='50');
UPDATE `modeactlib` SET `ID`='51', `ActName`='MoveToLevelWithOnOff', `ExtentionSet`='0', `ScenePara`='0600010108000100', `UniqueName`='调级0%', `UniqueNameEN`='MoveToLevel 0%', `DataType`='2', `DeviceID`='0200', `Groupabled`='0', `GroupLevel`='1', `Sceneabled`='1' WHERE (`ID`='51');
UPDATE `modeactlib` SET `ID`='52', `ActName`='MoveToLevelWithOnOff', `ExtentionSet`='100', `ScenePara`='06000101080001FF', `UniqueName`='调级100%', `UniqueNameEN`='MoveToLevel 100%', `DataType`='2', `DeviceID`='0200', `Groupabled`='0', `GroupLevel`='1', `Sceneabled`='1' WHERE (`ID`='52');
UPDATE `modeactlib` SET `ID`='53', `ActName`='Doorbellon', `ExtentionSet`='-1', `ScenePara`='', `UniqueName`='启用门铃', `UniqueNameEN`='Doorbellon', `DataType`='1', `DeviceID`='0007', `Groupabled`='0', `GroupLevel`='5', `Sceneabled`='0' WHERE (`ID`='53');
UPDATE `modeactlib` SET `ID`='54', `ActName`='DoorbellOff', `ExtentionSet`='-1', `ScenePara`='', `UniqueName`='禁用门铃', `UniqueNameEN`='DoorbellOff', `DataType`='1', `DeviceID`='0007', `Groupabled`='0', `GroupLevel`='5', `Sceneabled`='0' WHERE (`ID`='54');
UPDATE `modeactlib` SET `ID`='55', `ActName`='WarnAndSendSMS', `ExtentionSet`='-1', `ScenePara`='', `UniqueName`='警报设置', `UniqueNameEN`='WarnAndSendSMS', `DataType`='4', `DeviceID`='0403', `Groupabled`='0', `GroupLevel`='6', `Sceneabled`='0' WHERE (`ID`='55');
UPDATE `modeactlib` SET `ID`='57', `ActName`='LightColor', `ExtentionSet`='-1', `ScenePara`='', `UniqueName`='灯光颜色', `UniqueNameEN`='LightColor', `DataType`='5', `DeviceID`='0102', `Groupabled`='0', `GroupLevel`='0', `Sceneabled`='0' WHERE (`ID`='57');


/*把原本Z815K Wall Switch with Power Meter (2-Gang)改成3，3路开关才对*/
UPDATE `modenodelib_en` SET `deviceName`='Z815K Wall Switch with Power Meter (3-Gang)' WHERE (`id`='3');

/* 2014.12.16 */
ALTER TABLE `modemacroproc`
ADD COLUMN `uniqueNameEN`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `uniqueName`;

/* 2014.12.16 */
DROP PROCEDURE IF EXISTS `ModemacrosubProc`;
DELIMITER ;;
CREATE DEFINER = `root`@`%` PROCEDURE `ModemacrosubProc`(IN `behavior` bigint,IN `macroId` bigint,IN `type` bigint)
BEGIN
	#Routine body goes here...
DECLARE aCount INTEGER;
CASE
WHEN behavior=1 THEN
		SELECT a.*,d.roomId, lib.UniqueName, lib.DataType,lib.UniqueNameEN,
		CASE WHEN a.DestType=0 then d.deviceName 
					WHEN a.DestType=1 then g.GroupName 
					WHEN a.DestType=3 then s.GroupName end as deviceName
		FROM modemacrosub a inner join modemacromain m on a.MID=m.ID
												inner join usermodesub um on m.ID=um.Dest and um.Act='ApplyMacro'
												left join modedevice d on a.Dest=d.id
												left join modegroupmain g on a.Dest=g.ID
												left join modescenemain s on a.Dest=s.ID
												left join modeactlib lib on a.ActlibID=lib.ID
		where um.MID=macroId;
WHEN behavior=2 THEN
		SELECT m.*,um.ID as subID,0 as ModeName
		from modemacromain m inner join usermodesub um on m.ID=um.Dest and um.Act='ApplyMacro'
		where um.MID=macroId;

WHEN behavior=3 THEN
		SELECT a.*,d.roomId, lib.UniqueName, lib.DataType,lib.UniqueNameEN,
		CASE WHEN a.DestType=0 then d.deviceName 
					WHEN a.DestType=1 then g.GroupName 
					WHEN a.DestType=3 then s.GroupName end as deviceName
		FROM modemacrosub a inner join modemacromain m on a.MID=m.ID
												left join modedevice d on a.Dest=d.id
												left join modegroupmain g on a.Dest=g.ID
												left join modescenemain s on a.Dest=s.ID
												left join modeactlib lib on a.ActlibID=lib.ID
		where m.ID=macroId;
WHEN behavior=4 THEN
		SELECT m.*,0 as subID,0 as ModeName
		from modemacromain m
		where m.ID=macroId;

WHEN behavior=5 THEN
		SELECT a.*,d.roomId, lib.UniqueName, lib.DataType,lib.UniqueNameEN,
		CASE WHEN a.DestType=0 then d.deviceName 
					WHEN a.DestType=1 then g.GroupName 
					WHEN a.DestType=3 then s.GroupName end as deviceName
		FROM modemacrosub a left join modedevice d on a.Dest=d.id
												left join modegroupmain g on a.Dest=g.ID
												left join modescenemain s on a.Dest=s.ID
												left join modeactlib lib on a.ActlibID=lib.ID
		where a.ID=macroId;
WHEN behavior=6 THEN
	SELECT a.desttype into aCount FROM modemacrosub a
	where a.ID=macroId;
	
	IF(aCount=1) THEN
		SELECT d.roomId, d.deviceName, g.GroupName , u.*,0 as ActlibID,0 as TransTime,0 as DataType,0 as ScenePara,0 as Para
		FROM modemacrosub a inner join modegroupmain g on a.Dest=g.ID
												left join modegroupsub u on a.Dest=u.MID
												left join modedevice d on u.DeviceID=d.id
		where a.ID=macroId;
	ELSEIF(aCount=3) THEN
		SELECT d.roomId, d.deviceName, s.GroupName , lib.DataType, u.*,lib.UniqueNameEN
		FROM modemacrosub a inner join modescenemain s on a.Dest=s.ID											
												left join modescenesub u on a.Dest=u.MID
												left join modedevice d on u.DeviceID=d.id
												left join modeactlib lib on u.ActlibID=lib.ID
		where a.ID=macroId;
	ELSE
		SELECT 0 as id,0 as mid,0 as houseId,0 as deviceId,0 as roomId,0 as deviceName,0 as groupName,
					0 as actlibId,0 as transTime,0 as DataType,0 as ScenePara,0 as Para,0 as status, 0 as deviceOldId;
	END IF;

WHEN behavior=7 THEN
		SELECT m.*,h.ID as subID,0 as ModeName
		from modemacromain m inner join modeiassub h on m.ID=h.Dest
		where h.MID=macroId
		and ((type=0 and h.CIEStatus=0 and h.ZoneActType=0 ) OR
         (type=1 and h.CIEStatus=0 and h.ZoneActType=1 ) OR
         (type=2 and h.CIEStatus=1 and h.ZoneActType=0 ) OR
         (type=3 and h.CIEStatus=1 and h.ZoneActType=1 ) );

WHEN behavior=8 THEN
		SELECT m.*,h.ID as subID,0 as ModeName
		from modemacromain m inner join Modehvacsub h on m.ID=h.Dest
		where h.MID=macroId and h.acttype=type;

WHEN behavior=9 THEN
		SELECT m.*,h.ID as subID,0 as ModeName
		from modemacromain m inner join Modehvacsub h on m.ID=h.ResumeDest
		where h.ID=macroId;

WHEN behavior=10 THEN
		SELECT ma.*,0 as subID,0 as ModeName
		from modemacromain ma 
		where ma.HouseID=macroId;
END CASE;
END;
;;
DELIMITER ;

/* 2014.12.17 */
ALTER TABLE `house`
ADD COLUMN `wanIp`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `networkAddress`,
ADD COLUMN `lanIp`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `wanIp`;

/* 2014.12.17 */
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for modeeplib
-- ----------------------------
DROP TABLE IF EXISTS `modeeplib`;
CREATE TABLE `modeeplib` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nodeId` bigint(20) DEFAULT NULL,
  `deviceId` varchar(20) DEFAULT NULL,
  `internelModelId` varchar(50) DEFAULT NULL,
  `deviceName` varchar(200) DEFAULT NULL,
  `clusterId` varchar(200) DEFAULT NULL,
  `clusterName` varchar(100) DEFAULT NULL,
  `picName` varchar(100) DEFAULT NULL,
  `destType` varchar(4) DEFAULT NULL,
  `deviceType` varchar(20) DEFAULT NULL,
  `deviceTypeV2` bigint(20) DEFAULT '1',
  `ep` varchar(50) DEFAULT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `Groupable` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `nodeId` (`nodeId`) USING BTREE,
  KEY `deviceId` (`deviceId`) USING BTREE,
  KEY `internelModelId` (`internelModelId`) USING BTREE,
  KEY `deviceName` (`deviceName`) USING BTREE,
  KEY `destType` (`destType`) USING BTREE,
  KEY `deviceType` (`deviceType`) USING BTREE,
  KEY `clusterId` (`clusterId`) USING BTREE,
  KEY `Groupable` (`Groupable`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=249 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of modeeplib
-- ----------------------------
INSERT INTO `modeeplib` VALUES ('2', '2', '0009', 'Z815I-1', 'Z815I一路墙面电能检测开关', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib` VALUES ('3', '3', '0009', 'Z815K-1', 'Z815K一路墙面电能检测开关', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib` VALUES ('4', '3', '0009', 'Z815K-2', 'Z815K二路墙面电能检测开关', '0006', '开关', null, '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib` VALUES ('5', '3', '0009', 'Z815K-3', 'Z815K三路墙面电能检测开关', '0006', '开关', null, '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib` VALUES ('6', '4', '0100', 'Z806-1', 'Z806一路嵌墙开关', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib` VALUES ('7', '4', '0100', 'Z806-2', 'Z806二路嵌墙开关', '0006', '开关', null, '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib` VALUES ('8', '5', '0000', 'ZB02A-1', 'ZB02A壁挂无线按键开关一', '0006', '开关', null, '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib` VALUES ('10', '7', '0000', 'Z503-2', 'Z503开关控制按键', '0006:0008', '开关:调级控制', null, '1', 'source', '1', '0F', '', '0');
INSERT INTO `modeeplib` VALUES ('11', '7', '0006', 'Z503-1', 'Z503安防控制按键', '', '', null, '3', '', '1', '0A', '', '0');
INSERT INTO `modeeplib` VALUES ('12', '10', '0009', 'Z805A-1', 'Z805A一路嵌墙开关', '0006', '开关', null, '3', 'dest', '1', '0A', null, '1');
INSERT INTO `modeeplib` VALUES ('13', '11', '0009', 'Z805B-1', 'Z805B一路嵌墙开关', '0006', '开关', '', '3', 'dest', '1', '0A', '', '1');
INSERT INTO `modeeplib` VALUES ('14', '12', '0009', 'Z810B-1', 'Z810B电能检测开关', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib` VALUES ('15', '13', '0100', 'Z811-1', 'Z811一路开关', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib` VALUES ('16', '13', '0100', 'Z811-2', 'Z811二路开关', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib` VALUES ('17', '13', '0100', 'Z811-3', 'Z811三路开关', '0006', '开关', '', '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib` VALUES ('18', '13', '0100', 'Z811-4', 'Z811四路开关', '0006', '开关', '', '3', 'dest', '1', '04', '', '1');
INSERT INTO `modeeplib` VALUES ('19', '14', '0009', 'Z815J-1', 'Z815J一路墙面电能检测开关', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('20', '14', '0009', 'Z815J-2', 'Z815J二路墙面电能检测开关', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib` VALUES ('21', '15', '0101', 'Z815L-1', 'Z815L一路墙面电能检测调光开关', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('22', '16', '0101', 'Z815M-1', 'Z815M一路墙面电能检测调光开关', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('23', '16', '0101', 'Z815M-2', 'Z815M二路墙面电能检测调光开关', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib` VALUES ('24', '17', '0009', 'Z817A-1', 'Z817A一路吸顶电能检测开关', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('25', '18', '0101', 'Z817B-1', 'Z817B一路吸顶电能检测调光开关\n', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('26', '19', '0002', 'Z817C-1', 'Z817C一路吸顶红外感应型开关', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('27', '20', '0009', 'Z825C-1', 'Z825C一路墙面电能检测触摸开关', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('28', '20', '0009', 'Z825C-2', 'Z825C二路墙面电能检测触摸开关', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib` VALUES ('29', '20', '0009', 'Z825C-3', 'Z825C三路墙面电能检测触摸开关', '0006', '开关', '', '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib` VALUES ('30', '21', '0101', 'Z825E-1', 'Z825E一路电能检测触摸调光开关', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('31', '21', '0101', 'Z825E-2', 'Z825E二路电能检测触摸调光开关', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib` VALUES ('32', '22', '0101', 'ZC06-1', 'ZC06LED调光灯管', '0006:0008', '开关:调级控制', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib` VALUES ('33', '23', '0009', 'Z800-1', 'Z800电能检测插座', '0006', '开关', '', '3', 'dest', '1', '0A', '', '1');
INSERT INTO `modeeplib` VALUES ('34', '24', '0002', 'Z803-1', 'Z803一路排插', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('35', '24', '0002', 'Z803-2', 'Z803二路排插\n', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib` VALUES ('36', '24', '0002', 'Z803-3', 'Z803三路排插', '0006', '开关', '', '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib` VALUES ('37', '24', '0002', 'Z803-4', 'Z803四路排插', '0006', '开关', null, '3', 'dest', '1', '04', null, '1');
INSERT INTO `modeeplib` VALUES ('38', '24', '000D', 'Z803-5', 'Z803排插电能统计', '0006', '开关', null, '3', '', '1', '05', null, '0');
INSERT INTO `modeeplib` VALUES ('39', '25', '0009', 'Z808A-1', 'Z808A带USB电能检测插座', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('40', '26', '0101', 'Z808B-1', 'Z808B带USB电能检测调光插座', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('41', '27', '0009', 'Z809A-1', 'Z809A无线耗能检测插座', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('42', '28', '0101', 'Z809B-1', 'Z809B电能检测调光插座', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('43', '29', '0009', 'Z816B-1', 'Z816B美规电能检测墙面插座', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('44', '30', '0009', 'Z816G-1', 'Z816G英规电能检测墙面插座', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('45', '31', '0009', 'Z816H-1', 'Z816H中规电能检测墙面插座', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('46', '32', '0302', 'Z711-1', 'Z711室内温湿度探测器', null, null, null, null, null, '1', '0A', null, '0');
INSERT INTO `modeeplib` VALUES ('47', '33', '0302', 'Z712-1', 'Z712户外温湿度探测器', '', '', '', '', '', '1', '0A', '', '0');
INSERT INTO `modeeplib` VALUES ('48', '34', '0302', 'Z713-1', 'Z713户外温湿度探测器', '', '', null, '', '', '1', '0A', null, '0');
INSERT INTO `modeeplib` VALUES ('49', '35', '0302', 'Z716A-1', 'Z716A无线室内温湿度感应器', '', '', '', '', '', '1', '0A', '', '0');
INSERT INTO `modeeplib` VALUES ('50', '36', '0302', 'Z716B-1', 'Z716B室内带LCD温度探测器', null, null, '', null, null, '1', '0A', '', '0');
INSERT INTO `modeeplib` VALUES ('51', '37', '0103', 'Z302B-1', 'Z302B灯控光感器', '0006', '开关', null, '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('52', '38', '0104', 'Z302H-1', 'Z302H调光器 ', '0006:0008', '开关:调级控制', null, '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('53', '39', '0106', 'Z302G-1', 'Z302G光线感应器', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('54', '40', '0103', 'Z311B-1', 'Z311B灯控光感器', '0006', '开关', null, '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('55', '41', '0104', 'Z311H-1', 'Z311H调光器 ', '0006:0008', '开关:调级控制', null, '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('56', '42', '0106', 'Z311G-1', 'Z311G光线感应器', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib` VALUES ('57', '44', '0000', 'ZB02B-1', 'ZB02B壁挂无线按键开关一', '0006', '开关', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib` VALUES ('58', '44', '0000', 'ZB02B-2', 'ZB02B壁挂无线按键开关二', '0006', '开关', '', '3', 'source', '1', '02', '', '0');
INSERT INTO `modeeplib` VALUES ('59', '45', '0000', 'ZB02C-1', 'ZB02C无线墙面控制开关一路', '0006', '开关', '', '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('60', '45', '0000', 'ZB02C-2', 'ZB02C无线墙面控制开关二路', '0006', '开关', '', '3', 'source', '1', '02', null, '0');
INSERT INTO `modeeplib` VALUES ('61', '45', '0000', 'ZB02C-3', 'ZB02C无线墙面控制开关三路', '0006', '开关', '', '3', 'source', '1', '03', '', '0');
INSERT INTO `modeeplib` VALUES ('62', '46', '0104', 'ZB02F-1', 'ZB02F壁挂无线按键调光开关', '0006:0008', '开关:调级控制', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib` VALUES ('63', '48', '0000', 'ZB01B-1', 'ZB01B动作感应器开关', '0006', '开关', null, '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('64', '49', '0402', 'ZB01C-1', 'ZB01C安防动作感应器', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('65', '52', '0000', 'ZB11B-1', 'ZB11B无线红外人体感应器', '0006', '开关', null, '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('66', '53', '0402', 'ZB11C-1', 'ZB11C安防动作感应器', null, null, '', null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('67', '55', '0200', 'Z815N-1', 'Z815N窗帘墙面开关', '0006:0008', '开关:调级控制', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib` VALUES ('68', '56', '0200', 'ZD01B-1', 'ZD01B一路窗帘控制器', '0006:0008', '开关:调级控制', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib` VALUES ('69', '56', '0200', 'ZD01B-2', 'ZD01B二路窗帘控制器', '0006:0008', '开关:调级控制', null, '3', 'dest', '1', '02', null, '1');
INSERT INTO `modeeplib` VALUES ('71', '47', '0402', 'ZB01A-1', 'ZB01A安防动作感应器', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('72', '50', '0107', 'ZB01D-1', 'ZB01D红外占有感应器', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('73', '51', '0402', 'ZB11A-1', 'ZB11A安防动作感应器', '', '', null, '', '', '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('74', '49', '0000', 'ZB01C-2', 'ZB01C动作感应开关', '0006', '开关', null, '3', 'source', '1', '02', null, '0');
INSERT INTO `modeeplib` VALUES ('75', '49', '0302', 'ZB01C-3', 'ZB01C温度感应器', null, null, null, null, null, '1', '03', null, '0');
INSERT INTO `modeeplib` VALUES ('76', '53', '0000', 'ZB11C-2', 'ZB11C动作感应开关', '0006', '开关', null, '3', 'source', '1', '02', null, '0');
INSERT INTO `modeeplib` VALUES ('77', '53', '0302', 'ZB11C-3', 'ZB11C温度感应器', null, null, '', null, null, '1', '03', null, '0');
INSERT INTO `modeeplib` VALUES ('78', '54', '0107', 'ZB11D-1', 'ZB11D红外占用感应器', '', '', null, '', '', '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('79', '57', '0402', 'Z302A-1', 'Z302A窗磁感应器', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('80', '58', '0402', 'Z302C-1', 'Z302C窗户感应器', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('81', '59', '0402', 'Z302D-1', 'Z302D紧急报警器', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('82', '60', '0402', 'Z302E-1', 'Z302E物品标签', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('83', '61', '0000', 'Z302J-1', 'Z302J门磁感应开关', '0006', '开关', '', '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('84', '61', '0402', 'Z302J-2', 'Z302J安防门磁感应', '', '', '', '', '', '1', '02', null, '0');
INSERT INTO `modeeplib` VALUES ('85', '62', '0402', 'Z306D-1', 'Z306D定位紧急按钮', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('86', '63', '0402', 'Z311A-1', 'Z311A窗磁感应器', null, '', '', '', '', '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('87', '64', '0402', 'Z311C-1', 'Z311C窗户感应器', null, '', '', '', '', '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('88', '65', '0401', 'Z312-1', 'Z312无线门铃按键', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('89', '66', '0402', 'Z307-1', 'Z307跌倒感应器', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('90', '67', '0402', 'Z308-1', 'Z308无线紧急按钮', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('91', '68', '0402', 'ZA01A-1', 'ZA01A烟雾探测器', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('92', '69', '0402', 'ZA01B-1', 'ZA01B瓦斯感应器', '', '', null, '', '', '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('93', '70', '0402', 'ZA01C-1', 'ZA01C一氧化碳感应器', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('94', '71', '0402', 'ZA01D-1', 'ZA01D液化石油气感应器', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('95', '72', '0402', 'ZA02E-1', 'ZA02E烟雾感应器', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('96', '73', '0002', 'ZA10-1', 'ZA10智能阀门', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib` VALUES ('97', '74', '000A', 'ZB05-1', 'ZB05智能门锁', '0101', '开关锁', null, '3', 'dest', '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('98', '75', '0401', 'ZB02E-1', 'ZB02E门铃按键', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('99', '76', '0402', 'Z311E-1', 'Z311E物品标签', '', '', null, '', '', '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('100', '77', '0403', 'Z602A-1', 'Z602A智能声光报警器', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('101', '78', '0403', 'Z602B-1', 'Z602B警报器', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('102', '79', '0403', 'Z601A-1', 'Z601A警报器', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('103', '80', '000D', 'Z821-1', 'Z821一路电能统计', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('104', '80', '000D', 'Z821-2', 'Z821二路电能统计', null, null, null, null, null, '1', '02', null, '0');
INSERT INTO `modeeplib` VALUES ('105', '80', '000D', 'Z821-3', 'Z821三路电能统计', null, null, null, null, null, '1', '03', null, '0');
INSERT INTO `modeeplib` VALUES ('106', '80', '000D', 'Z821-4', 'Z821四路电能统计', null, null, null, null, null, '1', '04', null, '0');
INSERT INTO `modeeplib` VALUES ('107', '80', '000D', 'Z821-5', 'Z821五路电能统计', null, null, null, null, null, '1', '05', null, '0');
INSERT INTO `modeeplib` VALUES ('108', '80', '000D', 'Z821-6', 'Z821六路电能统计', null, null, null, null, null, '1', '06', null, '0');
INSERT INTO `modeeplib` VALUES ('109', '80', '000D', 'Z821-7', 'Z821七路电能统计', null, null, null, null, null, '1', '07', null, '0');
INSERT INTO `modeeplib` VALUES ('110', '80', '000D', 'Z821-8', 'Z821一至七路总电能统计', null, null, null, null, null, '1', '08', null, '0');
INSERT INTO `modeeplib` VALUES ('111', '81', '0001', 'Z501A-1', 'Z501A调级开关控制按键(250)', '0006:0008', '开关:调级控制', null, '3', 'source', '1', '0A', null, '0');
INSERT INTO `modeeplib` VALUES ('112', '81', '0401', 'Z501A-2', 'Z501A安防控制按键(250)', '', '', null, '', '', '1', '0B', null, '0');
INSERT INTO `modeeplib` VALUES ('113', '82', '0000', 'Z501B-1', 'Z501B开关控制按键一(250)', '0006', '开关', null, '3', 'source', '1', '0A', null, '0');
INSERT INTO `modeeplib` VALUES ('114', '82', '0000', 'Z501B-2', 'Z501B开关控制按键二(250)', '0006', '开关', null, '3', 'source', '1', '0B', null, '0');
INSERT INTO `modeeplib` VALUES ('115', '83', '0001', 'Z501C-1', 'Z501C调级开关控制按键(250)', '0006:0008', '开关:调级控制', null, '3', 'source', '1', '0A', null, '0');
INSERT INTO `modeeplib` VALUES ('116', '83', '0000', 'Z501C-2', 'Z501C开关控制按键(250)', '0006', '开关', null, '3', 'source', '1', '0B', null, '0');
INSERT INTO `modeeplib` VALUES ('117', '84', '0008', 'Z210C-1', 'Z210C红外线转接器', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('118', '85', '0008', 'Z211-1', 'Z211智能红外控制转换器', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('119', '86', '0007', 'Z203-1', 'Z203云智能网关', null, null, null, null, null, '1', '0A', null, '0');
INSERT INTO `modeeplib` VALUES ('120', '87', '0007', 'Z103AC-1', 'Z103ACUSB协调器', null, null, null, null, null, '1', '0A', null, '0');
INSERT INTO `modeeplib` VALUES ('121', '88', '0007', 'Z103AR-1', 'Z103ARUSB路由器', null, null, null, null, null, '1', '0A', null, '0');
INSERT INTO `modeeplib` VALUES ('122', '89', '0400', 'Z201B-1', 'Z201BHA 协调器+CIE', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('123', '90', '0400', 'Z201C-1', 'Z201CHA 协调器', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('124', '91', '0008', 'Z201R HA-1', 'Z201RHA中继器', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('125', '92', '0008', 'Z201R-1', 'Z201R定位用参考点', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('126', '93', '0002', 'Z812A-1', 'Z812A一路开关输出', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib` VALUES ('127', '93', '0000', 'Z812A-2', 'Z812A按键开关一', '0006', '开关', null, '3', 'source', '1', '02', null, '0');
INSERT INTO `modeeplib` VALUES ('128', '93', '0000', 'Z812A-3', 'Z812A按键开关二', '0006', '开关', null, '3', 'source', '1', '03', null, '0');
INSERT INTO `modeeplib` VALUES ('129', '93', '0000', 'Z812A-4', 'Z812A按键开关三', '0006', '开关', null, '3', 'source', '1', '04', null, '0');
INSERT INTO `modeeplib` VALUES ('130', '94', '0000', 'Z812B-1', 'Z812B按键开关一', '0006', '开关', null, '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('131', '94', '0000', 'Z812B-2', 'Z812B按键开关二', '0006', '开关', null, '3', 'source', '1', '02', null, '0');
INSERT INTO `modeeplib` VALUES ('132', '94', '0000', 'Z812B-3', 'Z812B按键开关三', '0006', '开关', null, '3', 'source', '1', '03', null, '0');
INSERT INTO `modeeplib` VALUES ('133', '94', '0000', 'Z812B-4', 'Z812B按键开关四', '0006', '开关', null, '3', 'source', '1', '04', null, '0');
INSERT INTO `modeeplib` VALUES ('134', '94', '0104', 'Z812B-5', 'Z812B按键调光开关一', '0006:0008', '开关:调级控制', null, '3', 'source', '1', '05', null, '0');
INSERT INTO `modeeplib` VALUES ('135', '94', '0104', 'Z812B-6', 'Z812B按键调光开关二', '0006:0008', '开关:调级控制', null, '3', 'source', '1', '06', null, '0');
INSERT INTO `modeeplib` VALUES ('136', '95', '0402', 'Z801TXB-1', 'Z801TXB一路脉冲信号探测', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib` VALUES ('137', '95', '0402', 'Z801TXB-2', 'Z801TXB二路脉冲信号探测', '', '', '', '', '', '1', '02', '', '0');
INSERT INTO `modeeplib` VALUES ('138', '95', '0402', 'Z801TXB-3', 'Z801TXB三路脉冲信号探测', '', '', '', '', '', '1', '03', '', '0');
INSERT INTO `modeeplib` VALUES ('139', '95', '0402', 'Z801TXB-4', 'Z801TXB四路脉冲信号探测', '', '', '', '', '', '1', '04', '', '0');
INSERT INTO `modeeplib` VALUES ('140', '95', '0402', 'Z801TXB-5', 'Z801TXB五路脉冲信号探测', '', '', '', '', '', '1', '05', '', '0');
INSERT INTO `modeeplib` VALUES ('141', '96', '0002', 'Z801RX', 'Z801RX弱电继电器', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib` VALUES ('142', '101', '0009', 'Z825A-1', 'Z825A一路触控式墙面开关', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib` VALUES ('143', '102', '0009', 'Z825B-1', 'Z825B一路触控式墙面开关', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib` VALUES ('144', '102', '0009', 'Z825B-2', 'Z825B二路触控式墙面开关', '0006', '开关', null, '3', 'dest', '1', '02', null, '1');
INSERT INTO `modeeplib` VALUES ('145', '103', '0101', 'Z825D-1', 'Z825D一路触控式墙面调光开关', '0006:0008', '开关:调级控制', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib` VALUES ('146', '100', '0400', 'Z508-1', 'Z508LCD资讯显示屏', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('147', '104', '0302', 'Z725A-1', 'Z725A户外温湿度探测器', null, null, null, null, null, '1', '0A', null, '0');
INSERT INTO `modeeplib` VALUES ('148', '106', '0008', 'Z725R-1', 'Z725R户外型网络中继器', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('149', '107', '0009', 'Z962A-1', 'Z962A一路触控式墙面开关', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib` VALUES ('150', '108', '0009', 'Z962B-1', 'Z962B一路触控式墙面开关', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib` VALUES ('151', '108', '0009', 'Z962B-2', 'Z962B二路触控式墙面开关', '0006', '开关', null, '3', 'dest', '1', '02', null, '1');
INSERT INTO `modeeplib` VALUES ('152', '109', '0009', 'Z962C-1', 'Z962C一路触控式墙面开关', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib` VALUES ('153', '109', '0009', 'Z962C-2', 'Z962C二路触控式墙面开关', '0006', '开关', null, '3', 'dest', '1', '02', null, '1');
INSERT INTO `modeeplib` VALUES ('154', '109', '0009', 'Z962C-3', 'Z962C三路触控式墙面开关', '0006', '开关', null, '3', 'dest', '1', '03', null, '1');
INSERT INTO `modeeplib` VALUES ('155', '110', '0004', 'Z962D-1', 'Z962D触摸情景开关按键一', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('156', '111', '0004', 'Z962E-1', 'Z962E触摸情景开关按键一', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('157', '111', '0004', 'Z962E-2', 'Z962E触摸情景开关按键二', null, null, null, null, null, '1', '02', null, '0');
INSERT INTO `modeeplib` VALUES ('158', '112', '0004', 'Z962F-1', 'Z962F触摸情景开关按键一', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('159', '112', '0004', 'Z962F-2', 'Z962F触摸情景开关按键二', null, null, null, null, null, '1', '02', null, '0');
INSERT INTO `modeeplib` VALUES ('160', '112', '0004', 'Z962F-3', 'Z962F触摸情景开关按键三', null, null, null, null, null, '1', '03', null, '0');
INSERT INTO `modeeplib` VALUES ('161', '113', '0009', 'Z962G-1', 'Z962G一路触控式墙面开关', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib` VALUES ('162', '113', '0004', 'Z962G-2', 'Z962G触摸情景开关按键一', null, null, null, null, null, '1', '02', null, '0');
INSERT INTO `modeeplib` VALUES ('163', '114', '0009', 'Z962H-1', 'Z962H一路触控式墙面开关', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib` VALUES ('164', '114', '0004', 'Z962H-2', 'Z962H触摸情景开关按键一', null, null, null, null, null, '1', '02', null, '0');
INSERT INTO `modeeplib` VALUES ('165', '114', '0004', 'Z962H-3', 'Z962H触摸情景开关按键二', null, null, null, null, null, '1', '03', null, '0');
INSERT INTO `modeeplib` VALUES ('166', '115', '0004', 'Z962I-1', 'Z962I触摸情景开关按键一', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('167', '115', '0009', 'Z962I-2', 'Z962I一路触控式墙面开关', '0006', '开关', null, '3', 'dest', '1', '02', null, '1');
INSERT INTO `modeeplib` VALUES ('168', '115', '0009', 'Z962I-3', 'Z962I二路触控式墙面开关', '0006', '开关', null, '3', 'dest', '1', '03', null, '1');
INSERT INTO `modeeplib` VALUES ('174', '116', '0402', 'Z801WLS-1', 'Z801WLS一路水浸报警', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib` VALUES ('175', '116', '0402', 'Z801WLS-2', 'Z801WLS二路水浸报警', '', '', '', '', '', '1', '02', '', '0');
INSERT INTO `modeeplib` VALUES ('176', '116', '0402', 'Z801WLS-3', 'Z801WLS三路水浸报警', '', '', '', '', '', '1', '03', '', '0');
INSERT INTO `modeeplib` VALUES ('177', '116', '0402', 'Z801WLS-4', 'Z801WLS四路水浸报警', '', '', '', '', '', '1', '04', '', '0');
INSERT INTO `modeeplib` VALUES ('178', '116', '0402', 'Z801WLS-5', 'Z801WLS五路水浸报警', '', '', '', '', '', '1', '05', '', '0');
INSERT INTO `modeeplib` VALUES ('179', '117', '000A', 'ZB05A-1', 'ZB05A智能门锁', '0101', '开关锁', '', '3', 'dest', '1', '01', '', '0');
INSERT INTO `modeeplib` VALUES ('180', '118', '0000', 'Z801TX-1', 'Z801TX开关控制一', '0006', '开关', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib` VALUES ('181', '118', '0000', 'Z801TX-2', 'Z801TX开关控制二', '0006', '开关', '', '3', 'source', '1', '02', '', '0');
INSERT INTO `modeeplib` VALUES ('182', '118', '0000', 'Z801TX-3', 'Z801TX开关控制三', '0006', '开关', '', '3', 'source', '1', '03', '', '0');
INSERT INTO `modeeplib` VALUES ('183', '118', '0000', 'Z801TX-4', 'Z801TX开关控制四', '0006', '开关', '', '3', 'source', '1', '04', '', '0');
INSERT INTO `modeeplib` VALUES ('184', '118', '0000', 'Z801TX-5', 'Z801TX开关控制五', '0006', '开关', '', '3', 'source', '1', '05', '', '0');
INSERT INTO `modeeplib` VALUES ('185', '119', '0000', 'Z311J-1', 'Z311J无线窗磁感应器开关', '0006', '开关', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib` VALUES ('186', '119', '0402', 'Z311J-2', 'Z311J无线安防窗磁感应器', '', '', '', '', '', '1', '02', '', '0');
INSERT INTO `modeeplib` VALUES ('187', '120', '0101', 'ZC07-1', 'ZC07无线可调光LED灯泡', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('188', '121', '0402', 'Z311W-1', 'Z311W二路水浸报警', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib` VALUES ('190', '122', '0200', 'Z811B-1', 'Z811B窗帘机控制器一路', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('191', '122', '0200', 'Z811B-2', 'Z811B窗帘机控制器二路', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib` VALUES ('192', '123', '0004', 'Z825I-1', 'Z825I触摸情景开关按键一', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib` VALUES ('193', '123', '0004', 'Z825I-2', 'Z825I触摸情景开关按键二', '', '', '', '', '', '1', '02', '', '0');
INSERT INTO `modeeplib` VALUES ('194', '123', '0004', 'Z825I-3', 'Z825I触摸情景开关按键三', '', '', '', '', '', '1', '03', '', '0');
INSERT INTO `modeeplib` VALUES ('195', '123', '0004', 'Z825I-4', 'Z825I触摸情景开关按键四', '', '', '', '', '', '1', '04', '', '0');
INSERT INTO `modeeplib` VALUES ('196', '123', '0004', 'Z825I-5', 'Z825I触摸情景开关按键五', '', '', '', '', '', '1', '05', '', '0');
INSERT INTO `modeeplib` VALUES ('197', '123', '0004', 'Z825I-6', 'Z825I触摸情景开关按键六', '', '', '', '', '', '1', '06', '', '0');
INSERT INTO `modeeplib` VALUES ('201', '124', '0004', 'Z825J-4', 'Z825J触摸情景开关按键一', '', '', '', '', '', '1', '04', '', '0');
INSERT INTO `modeeplib` VALUES ('202', '124', '0004', 'Z825J-5', 'Z825J触摸情景开关按键二', '', '', '', '', '', '1', '05', '', '0');
INSERT INTO `modeeplib` VALUES ('203', '124', '0004', 'Z825J-6', 'Z825J触摸情景开关按键三', '', '', '', '', '', '1', '06', '', '0');
INSERT INTO `modeeplib` VALUES ('204', '124', '0009', 'Z825J-1', 'Z825J开关输出一路', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('205', '124', '0009', 'Z825J-2', 'Z825J开关输出二路', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib` VALUES ('206', '124', '0009', 'Z825J-3', 'Z825J开关输出三路', '0006', '开关', '', '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib` VALUES ('207', '125', '0009', 'Z825Q-1', 'Z825Q一路墙面电能检测开关', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('208', '125', '0009', 'Z825Q-2', 'Z825Q二路墙面电能检测开关', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib` VALUES ('209', '125', '0009', 'Z825Q-3', 'Z825Q三路墙面电能检测开关', '0006', '开关', '', '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib` VALUES ('210', '126', '0009', 'Z816I-1', 'Z816I中规电能检测墙面插座', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('211', '127', '0000', 'Z817D-1', 'Z817D动作感应器开关', '0006', '开关', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib` VALUES ('212', '128', '0008', 'Z800R-1', 'Z800R带插座无线中继路由器', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('213', '129', '0008', 'Z809C-1', 'Z809C带插座无线中继路由器', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('214', '130', '0008', 'Z809D-1', 'Z809D带插座无线中继路由器', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib` VALUES ('215', '131', '0001', 'Z501AE3ED-1', 'Z501A调级开关控制按键(357)', '0006:0008', '开关:调级控制', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib` VALUES ('216', '131', '0401', 'Z501AE3ED-2', 'Z501A安防控制按键(357)', '', '', '', '', '', '1', '02', '', '0');
INSERT INTO `modeeplib` VALUES ('217', '132', '0000', 'Z501BE3ED-1', 'Z501B开关控制按键一(357)', '0006', '开关', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib` VALUES ('218', '132', '0000', 'Z501BE3ED-2', 'Z501B开关控制按键二(357)', '0006', '开关', '', '3', 'source', '1', '02', '', '0');
INSERT INTO `modeeplib` VALUES ('219', '133', '0001', 'Z501CE3ED-1', 'Z501C调级开关控制按键(357)', '0006:0008', '开关:调级控制', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib` VALUES ('220', '133', '0000', 'Z501CE3ED-2', 'Z501C开关控制按键(357)', '0006', '开关', '', '3', 'source', '1', '02', '', '0');
INSERT INTO `modeeplib` VALUES ('221', '134', '0402', 'ZB02I-1', 'ZB02I无线墙面紧急按键', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib` VALUES ('222', '137', '0002', 'Z802-1', 'Z802一路开关输出', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib` VALUES ('223', '137', '0002', 'Z802-2', 'Z802二路开关输出', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib` VALUES ('225', '138', '0004', 'ZB02J-1', 'ZB02J情景开关按键一', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib` VALUES ('226', '138', '0004', 'ZB02J-2', 'ZB02J情景开关按键二', '', '', '', '', '', '1', '02', '', '0');
INSERT INTO `modeeplib` VALUES ('227', '138', '0004', 'ZB02J-3', 'ZB02J情景开关按键三', '', '', '', '', '', '1', '03', '', '0');
INSERT INTO `modeeplib` VALUES ('228', '139', '0009', 'Z815A-1', 'Z815A一路墙面电能检测开关', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib` VALUES ('229', '140', '0009', 'Z815B-1', 'Z815B一路墙面电能检测开关', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('230', '140', '0009', 'Z815B-2', 'Z815B二路墙面电能检测开关', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib` VALUES ('231', '141', '0009', 'Z815C-1', 'Z815C一路墙面电能检测开关', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib` VALUES ('232', '141', '0009', 'Z815C-2', 'Z815C二路墙面电能检测开关', '0006', '开关', null, '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib` VALUES ('233', '141', '0009', 'Z815C-3', 'Z815C三路墙面电能检测开关', '0006', '开关', null, '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib` VALUES ('234', '142', '0101', 'Z815D-1', 'Z815D一路墙面电能检测调光开关', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('236', '143', '0101', 'Z815E-1', 'Z815E一路墙面电能检测调光开关', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('237', '143', '0101', 'Z815E-2', 'Z815E二路墙面电能检测调光开关', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib` VALUES ('238', '144', '0009', 'Z826C-1', 'Z826C一路墙面电能检测开关', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('239', '144', '0009', 'Z826C-2', 'Z826C二路墙面电能检测开关', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib` VALUES ('240', '144', '0009', 'Z826C-3', 'Z826C三路墙面电能检测开关', '0006', '开关', '', '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib` VALUES ('241', '146', '0004', 'Z501GE3ED-1', 'Z501G安防控制按键(357)1', null, null, '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib` VALUES ('242', '145', '0007', 'Z206-1', 'Z206云智能网关', '', '', '', '', '', '1', '0A', '', '0');
INSERT INTO `modeeplib` VALUES ('243', '146', '0004', 'Z501GE3ED-2', 'Z501G安防控制按键(357)2', null, null, '', '3', 'source', '1', '02', '', '0');
INSERT INTO `modeeplib` VALUES ('244', '146', '0004', 'Z501GE3ED-3', 'Z501G安防控制按键(357)3', null, null, '', '3', 'source', '1', '03', '', '0');
INSERT INTO `modeeplib` VALUES ('245', '146', '0401', 'Z501GE3ED-4', 'Z501G安防控制按键(357)4', '0501', '安防控制', '', '3', 'source', '1', '04', '', '0');
INSERT INTO `modeeplib` VALUES ('246', '147', '0302', 'ZA06-1', 'ZA06空气蛋温湿度探测', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('247', '147', '0402', 'ZA06-2', 'ZA06空气蛋VOC探测', null, null, null, null, null, '1', '02', null, '0');
INSERT INTO `modeeplib` VALUES ('248', '147', '0402', 'ZA06-3', 'ZA06空气蛋空气质量探测', null, null, null, null, null, '1', '03', '', '0');

-- ----------------------------
-- Table structure for modeeplib_en
-- ----------------------------
DROP TABLE IF EXISTS `modeeplib_en`;
CREATE TABLE `modeeplib_en` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nodeId` bigint(20) DEFAULT NULL,
  `deviceId` varchar(20) DEFAULT NULL,
  `internelModelId` varchar(50) DEFAULT NULL,
  `deviceName` varchar(200) DEFAULT NULL,
  `clusterId` varchar(200) DEFAULT NULL,
  `clusterName` varchar(100) DEFAULT NULL,
  `picName` varchar(100) DEFAULT NULL,
  `destType` varchar(4) DEFAULT NULL,
  `deviceType` varchar(20) DEFAULT NULL,
  `deviceTypeV2` bigint(20) DEFAULT '1',
  `ep` varchar(50) DEFAULT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `Groupable` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `nodeId` (`nodeId`) USING BTREE,
  KEY `deviceId` (`deviceId`) USING BTREE,
  KEY `internelModelId` (`internelModelId`) USING BTREE,
  KEY `deviceName` (`deviceName`) USING BTREE,
  KEY `destType` (`destType`) USING BTREE,
  KEY `deviceType` (`deviceType`) USING BTREE,
  KEY `clusterId` (`clusterId`) USING BTREE,
  KEY `Groupable` (`Groupable`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=249 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of modeeplib_en
-- ----------------------------
INSERT INTO `modeeplib_en` VALUES ('2', '2', '0009', 'Z815I-1', 'Z815I Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('3', '3', '0009', 'Z815K-1', 'Z815K 1st Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('4', '3', '0009', 'Z815K-2', 'Z815K 2nd Power Switch', '0006', '开关', null, '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('5', '3', '0009', 'Z815K-3', 'Z815K 3rd Power Switch', '0006', '开关', null, '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib_en` VALUES ('6', '4', '0100', 'Z806-1', 'Z806 1st Output', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('7', '4', '0100', 'Z806-2', 'Z806 2nd Output', '0006', '开关', null, '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('8', '5', '0000', 'ZB02A-1', 'ZB02A Wall Switch', '0006', '开关', null, '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('10', '7', '0000', 'Z503-2', 'Z503 Switches', '0006:0008', '开关:调级控制', null, '1', 'source', '1', '0F', '', '0');
INSERT INTO `modeeplib_en` VALUES ('11', '7', '0006', 'Z503-1', 'Z503 security Keypads', '', '', null, '3', '', '1', '0A', '', '0');
INSERT INTO `modeeplib_en` VALUES ('12', '10', '0009', 'Z805A-1', 'Z805A Power Switch', '0006', '开关', null, '3', 'dest', '1', '0A', null, '1');
INSERT INTO `modeeplib_en` VALUES ('13', '11', '0009', 'Z805B-1', 'Z805B Power Switch', '0006', '开关', '', '3', 'dest', '1', '0A', '', '1');
INSERT INTO `modeeplib_en` VALUES ('14', '12', '0009', 'Z810B-1', 'Z810B Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('15', '13', '0100', 'Z811-1', 'Z811 1st Output', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('16', '13', '0100', 'Z811-2', 'Z811 2nd Output', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('17', '13', '0100', 'Z811-3', 'Z811 3rd Output', '0006', '开关', '', '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib_en` VALUES ('18', '13', '0100', 'Z811-4', 'Z811 4th Output', '0006', '开关', '', '3', 'dest', '1', '04', '', '1');
INSERT INTO `modeeplib_en` VALUES ('19', '14', '0009', 'Z815J-1', 'Z815J 1st Power Switch', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('20', '14', '0009', 'Z815J-2', 'Z815J 2nd Power Switch', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('21', '15', '0101', 'Z815L-1', 'Z815L Dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('22', '16', '0101', 'Z815M-1', 'Z815M 1st Dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('23', '16', '0101', 'Z815M-2', 'Z815M 2nd Dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('24', '17', '0009', 'Z817A-1', 'Z817A Power Switch', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('25', '18', '0101', 'Z817B-1', 'Z817B Dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('26', '19', '0002', 'Z817C-1', 'Z817C Power Switch', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('27', '20', '0009', 'Z825C-1', 'Z825C 1st Power Switch', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('28', '20', '0009', 'Z825C-2', 'Z825C 2nd Power Switch', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('29', '20', '0009', 'Z825C-3', 'Z825C 3rd Power Switch', '0006', '开关', '', '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib_en` VALUES ('30', '21', '0101', 'Z825E-1', 'Z825E 1st Dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('31', '21', '0101', 'Z825E-2', 'Z825E 2nd Dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('32', '22', '0101', 'ZC06-1', 'ZC06 Dimmer light', '0006:0008', '开关:调级控制', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('33', '23', '0009', 'Z800-1', 'Z800 Power Plug', '0006', '开关', '', '3', 'dest', '1', '0A', '', '1');
INSERT INTO `modeeplib_en` VALUES ('34', '24', '0002', 'Z803-1', 'Z803 1st Power Plug', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('35', '24', '0002', 'Z803-2', 'Z803 2nd Power Plug', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('36', '24', '0002', 'Z803-3', 'Z803 3rd Power Plug', '0006', '开关', '', '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib_en` VALUES ('37', '24', '0002', 'Z803-4', 'Z803 4th Power Plug', '0006', '开关', null, '3', 'dest', '1', '04', null, '1');
INSERT INTO `modeeplib_en` VALUES ('38', '24', '000D', 'Z803-5', 'Z803 Power Consumption Awareness', '0006', '开关', null, '3', '', '1', '05', null, '0');
INSERT INTO `modeeplib_en` VALUES ('39', '25', '0009', 'Z808A-1', 'Z808A Power Plug', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('40', '26', '0101', 'Z808B-1', 'Z808B Dimmable Power Plug', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('41', '27', '0009', 'Z809A-1', 'Z809A Power Plug', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('42', '28', '0101', 'Z809B-1', 'Z809B Dimmable Power Plug', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('43', '29', '0009', 'Z816B-1', 'Z816B US type Power Plug', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('44', '30', '0009', 'Z816G-1', 'Z816G UK type Power Plug', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('45', '31', '0009', 'Z816H-1', 'Z816H China type Power Plug', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('46', '32', '0302', 'Z711-1', 'Z711 Temperature and Humidity Sensor', null, null, null, null, null, '1', '0A', null, '0');
INSERT INTO `modeeplib_en` VALUES ('47', '33', '0302', 'Z712-1', 'Z712 Temperature and Humidity Sensor', '', '', '', '', '', '1', '0A', '', '0');
INSERT INTO `modeeplib_en` VALUES ('48', '34', '0302', 'Z713-1', 'Z713 Temperature and Humidity Sensor', '', '', null, '', '', '1', '0A', null, '0');
INSERT INTO `modeeplib_en` VALUES ('49', '35', '0302', 'Z716A-1', 'Z716A Temperature and Humidity Sensor', '', '', '', '', '', '1', '0A', '', '0');
INSERT INTO `modeeplib_en` VALUES ('50', '36', '0302', 'Z716B-1', 'Z716B Temperature Sensor', null, null, '', null, null, '1', '0A', '', '0');
INSERT INTO `modeeplib_en` VALUES ('51', '37', '0103', 'Z302B-1', 'Z302B Light Switch', '0006', '开关', null, '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('52', '38', '0104', 'Z302H-1', 'Z302H Light Sensor ', '0006:0008', '开关:调级控制', null, '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('53', '39', '0106', 'Z302G-1', 'Z302G Dimmer Switch', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('54', '40', '0103', 'Z311B-1', 'Z311B Light Switch', '0006', '开关', null, '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('55', '41', '0104', 'Z311H-1', 'Z311H Light Sensor ', '0006:0008', '开关:调级控制', null, '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('56', '42', '0106', 'Z311G-1', 'Z311G Dimmer Switch', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('57', '44', '0000', 'ZB02B-1', 'ZB02B Wall Switch 1st key', '0006', '开关', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('58', '44', '0000', 'ZB02B-2', 'ZB02B Wall Switch 2nd key', '0006', '开关', '', '3', 'source', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('59', '45', '0000', 'ZB02C-1', 'ZB02C Wall Switch 1st key', '0006', '开关', '', '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('60', '45', '0000', 'ZB02C-2', 'ZB02C Wall Switch 2nd key', '0006', '开关', '', '3', 'source', '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('61', '45', '0000', 'ZB02C-3', 'ZB02C Wall Switch 3rd key', '0006', '开关', '', '3', 'source', '1', '03', '', '0');
INSERT INTO `modeeplib_en` VALUES ('62', '46', '0104', 'ZB02F-1', 'ZB02F Wall Dimmer', '0006:0008', '开关:调级控制', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('63', '48', '0000', 'ZB01B-1', 'ZB01B On Off Switch', '0006', '开关', null, '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('64', '49', '0402', 'ZB01C-1', 'ZB01C Motion Detector', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('65', '52', '0000', 'ZB11B-1', 'ZB11B On Off Switch', '0006', '开关', null, '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('66', '53', '0402', 'ZB11C-1', 'ZB11C Motion Detector', null, null, '', null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('67', '55', '0200', 'Z815N-1', 'Z815N Shade Control Switch', '0006:0008', '开关:调级控制', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('68', '56', '0200', 'ZD01B-1', 'ZD01B First Curtain Controller', '0006:0008', '开关:调级控制', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('69', '56', '0200', 'ZD01B-2', 'ZD01B Second Curtain Controller', '0006:0008', '开关:调级控制', null, '3', 'dest', '1', '02', null, '1');
INSERT INTO `modeeplib_en` VALUES ('71', '47', '0402', 'ZB01A-1', 'ZB01A Motion Detector', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('72', '50', '0107', 'ZB01D-1', 'ZB01D Occupancy Sensor', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('73', '51', '0402', 'ZB11A-1', 'ZB11A Motion Detector', '', '', null, '', '', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('74', '49', '0000', 'ZB01C-2', 'ZB01C On Off Switch', '0006', '开关', null, '3', 'source', '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('75', '49', '0302', 'ZB01C-3', 'ZB01C Temperature Sensor', null, null, null, null, null, '1', '03', null, '0');
INSERT INTO `modeeplib_en` VALUES ('76', '53', '0000', 'ZB11C-2', 'ZB11C On Off Switch', '0006', '开关', null, '3', 'source', '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('77', '53', '0302', 'ZB11C-3', 'ZB11C Temperature Sensor', null, null, '', null, null, '1', '03', null, '0');
INSERT INTO `modeeplib_en` VALUES ('78', '54', '0107', 'ZB11D-1', 'ZB11D Occupancy Sensor', '', '', null, '', '', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('79', '57', '0402', 'Z302A-1', 'Z302A Window Intrusion Sensor', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('80', '58', '0402', 'Z302C-1', 'Z302C Window Glass Break Sensor and Intrusion Sensor', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('81', '59', '0402', 'Z302D-1', 'Z302D Panic Button', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('82', '60', '0402', 'Z302E-1', 'Z302E Asset tag', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('83', '61', '0000', 'Z302J-1', 'Z302J on off switch', '0006', '开关', '', '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('84', '61', '0402', 'Z302J-2', 'Z302J Window Intrusion Sensor', '', '', '', '', '', '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('85', '62', '0402', 'Z306D-1', 'Z306D Panic Button and Inductive Charging Base', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('86', '63', '0402', 'Z311A-1', 'Z311AWindow Intrusion Sensor', null, '', '', '', '', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('87', '64', '0402', 'Z311C-1', 'Z311C Window Glass Break Sensor and Intrusion Sensor', null, '', '', '', '', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('88', '65', '0401', 'Z312-1', 'Z312 Door Bell Button', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('89', '66', '0402', 'Z307-1', 'Z307 Fall Sensor and Inductive Charging Base', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('90', '67', '0402', 'Z308-1', 'Z308 Panic Button', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('91', '68', '0402', 'ZA01A-1', 'ZA01A Smoke Detector with Heat Sensor', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('92', '69', '0402', 'ZA01B-1', 'ZA01B Gas Detector with Heat Sensor', '', '', null, '', '', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('93', '70', '0402', 'ZA01C-1', 'ZA01C CO Detector', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('94', '71', '0402', 'ZA01D-1', 'ZA01D Liquefied Gas Detector with Heat Sensor', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('95', '72', '0402', 'ZA02E-1', 'ZA02E Photoelectric Smoke Detector with Heat Sensor', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('96', '73', '0002', 'ZA10-1', 'ZA10 Gas or Water Keeper', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('97', '74', '000A', 'ZB05-1', 'ZB05 Door Lock', '0101', '开关锁', null, '3', 'dest', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('98', '75', '0401', 'ZB02E-1', 'ZB02E Door Bell', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('99', '76', '0402', 'Z311E-1', 'Z311E Asset tag', '', '', null, '', '', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('100', '77', '0403', 'Z602A-1', 'Z602A Siren', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('101', '78', '0403', 'Z602B-1', 'Z602B Siren with GSM', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('102', '79', '0403', 'Z601A-1', 'Z601A Siren', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('103', '80', '000D', 'Z821-1', 'Z821 1st Power Consumption Awareness', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('104', '80', '000D', 'Z821-2', 'Z821 2nd Power Consumption Awareness', null, null, null, null, null, '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('105', '80', '000D', 'Z821-3', 'Z821 3rd Power Consumption Awareness', null, null, null, null, null, '1', '03', null, '0');
INSERT INTO `modeeplib_en` VALUES ('106', '80', '000D', 'Z821-4', 'Z821 4th Power Consumption Awareness', null, null, null, null, null, '1', '04', null, '0');
INSERT INTO `modeeplib_en` VALUES ('107', '80', '000D', 'Z821-5', 'Z821 5th Power Consumption Awareness', null, null, null, null, null, '1', '05', null, '0');
INSERT INTO `modeeplib_en` VALUES ('108', '80', '000D', 'Z821-6', 'Z821 6th Power Consumption Awareness', null, null, null, null, null, '1', '06', null, '0');
INSERT INTO `modeeplib_en` VALUES ('109', '80', '000D', 'Z821-7', 'Z821 7th Power Consumption Awareness', null, null, null, null, null, '1', '07', null, '0');
INSERT INTO `modeeplib_en` VALUES ('110', '80', '000D', 'Z821-8', 'Z821 All Power Consumption Awareness', null, null, null, null, null, '1', '08', null, '0');
INSERT INTO `modeeplib_en` VALUES ('111', '81', '0001', 'Z501A-1', 'Z501A dimmer Switch', '0006:0008', '开关:调级控制', null, '3', 'source', '1', '0A', null, '0');
INSERT INTO `modeeplib_en` VALUES ('112', '81', '0401', 'Z501A-2', 'Z501A security Keypad', '', '', null, '', '', '1', '0B', null, '0');
INSERT INTO `modeeplib_en` VALUES ('113', '82', '0000', 'Z501B-1', 'Z501B 1st Switch', '0006', '开关', null, '3', 'source', '1', '0A', null, '0');
INSERT INTO `modeeplib_en` VALUES ('114', '82', '0000', 'Z501B-2', 'Z501B 2nd Switch', '0006', '开关', null, '3', 'source', '1', '0B', null, '0');
INSERT INTO `modeeplib_en` VALUES ('115', '83', '0001', 'Z501C-1', 'Z501C dimmer Switch', '0006:0008', '开关:调级控制', null, '3', 'source', '1', '0A', null, '0');
INSERT INTO `modeeplib_en` VALUES ('116', '83', '0000', 'Z501C-2', 'Z501C on Off Switch', '0006', '开关', null, '3', 'source', '1', '0B', null, '0');
INSERT INTO `modeeplib_en` VALUES ('117', '84', '0008', 'Z210C-1', 'Z210C Infrared Adpt', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('118', '85', '0008', 'Z211-1', 'Z211 Bidirectional ZigBeeIR Adpt', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('119', '86', '0007', 'Z203-1', 'Z203 CWSHC', null, null, null, null, null, '1', '0A', null, '0');
INSERT INTO `modeeplib_en` VALUES ('120', '87', '0007', 'Z103AC-1', 'Z103AC USB', null, null, null, null, null, '1', '0A', null, '0');
INSERT INTO `modeeplib_en` VALUES ('121', '88', '0007', 'Z103AR-1', 'Z103AR USB', null, null, null, null, null, '1', '0A', null, '0');
INSERT INTO `modeeplib_en` VALUES ('122', '89', '0400', 'Z201B-1', 'Z201B ZigBee802.15.4 router andCIE', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('123', '90', '0400', 'Z201C-1', 'Z201C ZigBee802.15.4 Coord', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('124', '91', '0008', 'Z201R HA-1', 'Z201R ZigBee TCP/IP Gateway', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('125', '92', '0008', 'Z201R-1', 'Z201 ZigBee 802.15.4 Router', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('126', '93', '0002', 'Z812A-1', 'Z812A output', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('127', '93', '0000', 'Z812A-2', 'Z812A 1st Switch', '0006', '开关', null, '3', 'source', '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('128', '93', '0000', 'Z812A-3', 'Z812A 2nd Switch', '0006', '开关', null, '3', 'source', '1', '03', null, '0');
INSERT INTO `modeeplib_en` VALUES ('129', '93', '0000', 'Z812A-4', 'Z812A 3rd Switch', '0006', '开关', null, '3', 'source', '1', '04', null, '0');
INSERT INTO `modeeplib_en` VALUES ('130', '94', '0000', 'Z812B-1', 'Z812B 1st Switch', '0006', '开关', null, '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('131', '94', '0000', 'Z812B-2', 'Z812B 2nd Switch', '0006', '开关', null, '3', 'source', '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('132', '94', '0000', 'Z812B-3', 'Z812B 3rd Switch', '0006', '开关', null, '3', 'source', '1', '03', null, '0');
INSERT INTO `modeeplib_en` VALUES ('133', '94', '0000', 'Z812B-4', 'Z812B 4th Switch', '0006', '开关', null, '3', 'source', '1', '04', null, '0');
INSERT INTO `modeeplib_en` VALUES ('134', '94', '0104', 'Z812B-5', 'Z812B 1st Dimmer Switch', '0006:0008', '开关:调级控制', null, '3', 'source', '1', '05', null, '0');
INSERT INTO `modeeplib_en` VALUES ('135', '94', '0104', 'Z812B-6', 'Z812B 2nd Dimmer Switch', '0006:0008', '开关:调级控制', null, '3', 'source', '1', '06', null, '0');
INSERT INTO `modeeplib_en` VALUES ('136', '95', '0402', 'Z801TXB-1', 'Z801TXB 1st Detector', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('137', '95', '0402', 'Z801TXB-2', 'Z801TXB 2nd Detector', '', '', '', '', '', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('138', '95', '0402', 'Z801TXB-3', 'Z801TXB 3rd Detector', '', '', '', '', '', '1', '03', '', '0');
INSERT INTO `modeeplib_en` VALUES ('139', '95', '0402', 'Z801TXB-4', 'Z801TXB 4th Detector', '', '', '', '', '', '1', '04', '', '0');
INSERT INTO `modeeplib_en` VALUES ('140', '95', '0402', 'Z801TXB-5', 'Z801TXB 5th Detector', '', '', '', '', '', '1', '05', '', '0');
INSERT INTO `modeeplib_en` VALUES ('141', '96', '0002', 'Z801RX', 'Z801RX output', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('142', '101', '0009', 'Z825A-1', 'Z825APower Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('143', '102', '0009', 'Z825B-1', 'Z825B 1st Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('144', '102', '0009', 'Z825B-2', 'Z825B 2nd Power Switch', '0006', '开关', null, '3', 'dest', '1', '02', null, '1');
INSERT INTO `modeeplib_en` VALUES ('145', '103', '0101', 'Z825D-1', 'Z825D Dimmer Switch', '0006:0008', '开关:调级控制', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('146', '100', '0400', 'Z508-1', 'Z508 In Home Display', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('147', '104', '0302', 'Z725A-1', 'Z725A Temperature and Humidity Sensor', null, null, null, null, null, '1', '0A', null, '0');
INSERT INTO `modeeplib_en` VALUES ('148', '106', '0008', 'Z725R-1', 'Z725R Outdoor Range Extender', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('149', '107', '0009', 'Z962A-1', 'Z962A Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('150', '108', '0009', 'Z962B-1', 'Z962B 1st Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('151', '108', '0009', 'Z962B-2', 'Z962B 2nd Power Switch', '0006', '开关', null, '3', 'dest', '1', '02', null, '1');
INSERT INTO `modeeplib_en` VALUES ('152', '109', '0009', 'Z962C-1', 'Z962C 1st Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('153', '109', '0009', 'Z962C-2', 'Z962C 2nd Power Switch', '0006', '开关', null, '3', 'dest', '1', '02', null, '1');
INSERT INTO `modeeplib_en` VALUES ('154', '109', '0009', 'Z962C-3', 'Z962C 3rd Power Switch', '0006', '开关', null, '3', 'dest', '1', '03', null, '1');
INSERT INTO `modeeplib_en` VALUES ('155', '110', '0004', 'Z962D-1', 'Z962D Scene or Mode Selector', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('156', '111', '0004', 'Z962E-1', 'Z962E 1st Scene or Mode Selector', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('157', '111', '0004', 'Z962E-2', 'Z962E 2nd Scene or Mode Selector', null, null, null, null, null, '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('158', '112', '0004', 'Z962F-1', 'Z962F 1st Scene or Mode Selector', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('159', '112', '0004', 'Z962F-2', 'Z962F 2nd Scene or Mode Selector', null, null, null, null, null, '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('160', '112', '0004', 'Z962F-3', 'Z962F 3rd Scene or Mode Selector', null, null, null, null, null, '1', '03', null, '0');
INSERT INTO `modeeplib_en` VALUES ('161', '113', '0009', 'Z962G-1', 'Z962G Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('162', '113', '0004', 'Z962G-2', 'Z962G Scene or Mode Selector', null, null, null, null, null, '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('163', '114', '0009', 'Z962H-1', 'Z962H Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('164', '114', '0004', 'Z962H-2', 'Z962H 1st Scene or Mode Selector', null, null, null, null, null, '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('165', '114', '0004', 'Z962H-3', 'Z962H 2nd Scene or Mode Selector', null, null, null, null, null, '1', '03', null, '0');
INSERT INTO `modeeplib_en` VALUES ('166', '115', '0004', 'Z962I-1', 'Z962I Scene or Mode Selector', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('167', '115', '0009', 'Z962I-2', 'Z962I 1st Power Switch', '0006', '开关', null, '3', 'dest', '1', '02', null, '1');
INSERT INTO `modeeplib_en` VALUES ('168', '115', '0009', 'Z962I-3', 'Z962I 2nd Power Switch', '0006', '开关', null, '3', 'dest', '1', '03', null, '1');
INSERT INTO `modeeplib_en` VALUES ('174', '116', '0402', 'Z801WLS-1', 'Z801WLS 1st water sensor', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('175', '116', '0402', 'Z801WLS-2', 'Z801WLS 2nd water sensor', '', '', '', '', '', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('176', '116', '0402', 'Z801WLS-3', 'Z801WLS 3rd water sensor', '', '', '', '', '', '1', '03', '', '0');
INSERT INTO `modeeplib_en` VALUES ('177', '116', '0402', 'Z801WLS-4', 'Z801WLS 4th water sensor', '', '', '', '', '', '1', '04', '', '0');
INSERT INTO `modeeplib_en` VALUES ('178', '116', '0402', 'Z801WLS-5', 'Z801WLS 5th water sensor', '', '', '', '', '', '1', '05', '', '0');
INSERT INTO `modeeplib_en` VALUES ('179', '117', '000A', 'ZB05A-1', 'ZB05A Door Lock', '0101', '开关锁', '', '3', 'dest', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('180', '118', '0000', 'Z801TX-1', 'Z801TX 1st Switch', '0006', '开关', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('181', '118', '0000', 'Z801TX-2', 'Z801TX  2nd Switch', '0006', '开关', '', '3', 'source', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('182', '118', '0000', 'Z801TX-3', 'Z801TX  3rd Switch', '0006', '开关', '', '3', 'source', '1', '03', '', '0');
INSERT INTO `modeeplib_en` VALUES ('183', '118', '0000', 'Z801TX-4', 'Z801TX  4th Switch', '0006', '开关', '', '3', 'source', '1', '04', '', '0');
INSERT INTO `modeeplib_en` VALUES ('184', '118', '0000', 'Z801TX-5', 'Z801TX  5th Switch', '0006', '开关', '', '3', 'source', '1', '05', '', '0');
INSERT INTO `modeeplib_en` VALUES ('185', '119', '0000', 'Z311J-1', 'Z311J on off switch', '0006', '开关', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('186', '119', '0402', 'Z311J-2', 'Z311J Window Intrusion Sensor', '', '', '', '', '', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('187', '120', '0101', 'ZC07-1', 'ZC07 Dimmer light', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('188', '121', '0402', 'Z311W-1', 'Z311W water sensor', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('190', '122', '0200', 'Z811B-1', 'Z811B 1st Curtain Controller', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('191', '122', '0200', 'Z811B-2', 'Z811B 2nd Curtain Controller', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('192', '123', '0004', 'Z825I-1', 'Z825I 1st Scene or Mode Selector', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('193', '123', '0004', 'Z825I-2', 'Z825I 2nd Scene or Mode Selector', '', '', '', '', '', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('194', '123', '0004', 'Z825I-3', 'Z825I 3rd Scene or Mode Selector', '', '', '', '', '', '1', '03', '', '0');
INSERT INTO `modeeplib_en` VALUES ('195', '123', '0004', 'Z825I-4', 'Z825I 4th Scene or Mode Selector', '', '', '', '', '', '1', '04', '', '0');
INSERT INTO `modeeplib_en` VALUES ('196', '123', '0004', 'Z825I-5', 'Z825I 5th Scene or Mode Selector', '', '', '', '', '', '1', '05', '', '0');
INSERT INTO `modeeplib_en` VALUES ('197', '123', '0004', 'Z825I-6', 'Z825I 6th Scene or Mode Selector', '', '', '', '', '', '1', '06', '', '0');
INSERT INTO `modeeplib_en` VALUES ('201', '124', '0004', 'Z825J-4', 'Z825J 1st Scene or Mode Selector', '', '', '', '', '', '1', '04', '', '0');
INSERT INTO `modeeplib_en` VALUES ('202', '124', '0004', 'Z825J-5', 'Z825J 2nd Scene or Mode Selector', '', '', '', '', '', '1', '05', '', '0');
INSERT INTO `modeeplib_en` VALUES ('203', '124', '0004', 'Z825J-6', 'Z825J 3rd Scene or Mode Selector', '', '', '', '', '', '1', '06', '', '0');
INSERT INTO `modeeplib_en` VALUES ('204', '124', '0009', 'Z825J-1', 'Z825J 1st Output', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('205', '124', '0009', 'Z825J-2', 'Z825J 2nd Output', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('206', '124', '0009', 'Z825J-3', 'Z825J 3rd Output', '0006', '开关', '', '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib_en` VALUES ('207', '125', '0009', 'Z825Q-1', 'Z825Q 1st Power Switch', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('208', '125', '0009', 'Z825Q-2', 'Z825Q 2nd Power Switch', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('209', '125', '0009', 'Z825Q-3', 'Z825Q 3rd Power Switch', '0006', '开关', '', '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib_en` VALUES ('210', '126', '0009', 'Z816I-1', 'Z816I China type Power Plug', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('211', '127', '0000', 'Z817D-1', 'Z817D  On Off Switch', '0006', '开关', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('212', '128', '0008', 'Z800R-1', 'Z800 Plug Router', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('213', '129', '0008', 'Z809C-1', 'Z809C Plug Router', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('214', '130', '0008', 'Z809D-1', 'Z809D Plug Router', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('215', '131', '0001', 'Z501AE3ED-1', 'Z501A dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('216', '131', '0401', 'Z501AE3ED-2', 'Z501A security Keypads', '', '', '', '', '', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('217', '132', '0000', 'Z501BE3ED-1', 'Z501B 1st Switch', '0006', '开关', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('218', '132', '0000', 'Z501BE3ED-2', 'Z501B 2nd Switch', '0006', '开关', '', '3', 'source', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('219', '133', '0001', 'Z501CE3ED-1', 'Z501C dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('220', '133', '0000', 'Z501CE3ED-2', 'Z501C security Keypads', '0006', '开关', '', '3', 'source', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('221', '134', '0402', 'ZB02I-1', 'ZB02IPanic Button', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('222', '137', '0002', 'Z802-1', 'Z802 1st Output', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('223', '137', '0002', 'Z802-2', 'Z802 2nd Output', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('225', '138', '0004', 'ZB02J-1', 'ZB02J 1st Scene or Mode Selector', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('226', '138', '0004', 'ZB02J-2', 'ZB02J 2nd Scene or Mode Selector', '', '', '', '', '', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('227', '138', '0004', 'ZB02J-3', 'ZB02J 3rd Scene or Mode Selector', '', '', '', '', '', '1', '03', '', '0');
INSERT INTO `modeeplib_en` VALUES ('228', '139', '0009', 'Z815A-1', 'Z815A Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('229', '140', '0009', 'Z815B-1', 'Z815B 1st Power Switch', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('230', '140', '0009', 'Z815B-2', 'Z815B 2nd Power Switch', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('231', '141', '0009', 'Z815C-1', 'Z815C 1st Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('232', '141', '0009', 'Z815C-2', 'Z815C 2nd Power Switch', '0006', '开关', null, '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('233', '141', '0009', 'Z815C-3', 'Z815C 3rd Power Switch', '0006', '开关', null, '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib_en` VALUES ('234', '142', '0101', 'Z815D-1', 'Z815D 1st dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('236', '143', '0101', 'Z815E-1', 'Z815E 2nd dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('237', '143', '0101', 'Z815E-2', 'Z815E 3rd dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('238', '144', '0009', 'Z826C-1', 'Z826C 1st Power Switch', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('239', '144', '0009', 'Z826C-2', 'Z826C 2nd Power Switch', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('240', '144', '0009', 'Z826C-3', 'Z826C 3rd Power Switch', '0006', '开关', '', '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib_en` VALUES ('241', '146', '0004', 'Z501GE3ED-1', 'Z501G 1st security Keypads', '', '', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('242', '145', '0007', 'Z206-1', 'Z206 CWSHC', '', '', '', '', '', '1', '0A', '', '0');
INSERT INTO `modeeplib_en` VALUES ('243', '146', '0004', 'Z501GE3ED-2', 'Z501G 2nd security Keypads', '', '', '', '3', 'source', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('244', '146', '0004', 'Z501GE3ED-3', 'Z501G 3rd security Keypads', '', '', '', '3', 'source', '1', '03', '', '0');
INSERT INTO `modeeplib_en` VALUES ('245', '146', '0401', 'Z501GE3ED-4', 'Z501G 4th security Keypads', '0501', '安防控制', '', '3', 'source', '1', '04', '', '0');
INSERT INTO `modeeplib_en` VALUES ('246', '147', '0302', 'ZA06-1', 'ZA06 Temperature and Humidity Sensor', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('247', '147', '0402', 'ZA06-2', 'ZA06 VOC Sensor', '', '', '', '', '', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('248', '147', '0402', 'ZA06-3', 'ZA06 Air quality Sensor', '', '', '', '', '', '1', '03', '', '0');

-- ----------------------------
-- Table structure for modenodelib
-- ----------------------------
DROP TABLE IF EXISTS `modenodelib`;
CREATE TABLE `modenodelib` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nodeType` varchar(100) DEFAULT NULL,
  `modelId` varchar(50) DEFAULT NULL,
  `deviceName` varchar(200) DEFAULT NULL,
  `clusterId` varchar(200) DEFAULT NULL,
  `clusterName` varchar(100) DEFAULT NULL,
  `destType` varchar(4) DEFAULT NULL,
  `source_id` bigint(20) DEFAULT '1',
  `deviceType` varchar(20) DEFAULT NULL,
  `picName` varchar(100) DEFAULT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `powerType` varchar(200) DEFAULT NULL,
  `activationMethod` varchar(2000) DEFAULT NULL,
  `resetDefault` varchar(2000) DEFAULT NULL,
  `remark` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `nodeType` (`nodeType`) USING BTREE,
  KEY `modelId` (`modelId`) USING BTREE,
  KEY `deviceName` (`deviceName`) USING BTREE,
  KEY `clusterId` (`clusterId`) USING BTREE,
  KEY `destType` (`destType`) USING BTREE,
  KEY `deviceType` (`deviceType`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=163 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of modenodelib
-- ----------------------------
INSERT INTO `modenodelib` VALUES ('2', '0', 'Z815I', 'Z815I单路电能检测墙面开关', '0006', '开关', '3', '1', 'dest', 'Z815I.jpg', 'Z815I是一路输出的开关设备，可直接安装于86盒中，替换家居中的普通墙面开关，能够检测和统计当前负载电流、电压、功率、电能值。用户可采用ZigBee遥控器、手机、按键开关等多种方式控制该设备。', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('3', '0', 'Z815K', 'Z815K三路电能检测墙面开关', '0006', '开关', '3', '1', 'dest', 'Z815K.jpg', 'Z815K有三路输出的开关设备，可直接安装于86盒中，替换家居中的普通墙面开关，能够检测和统计当前负载电流、电压、功率、电能值。用户可采用ZigBee遥控器、手机、按键开关等多种方式控制该设备。', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('4', '0', 'Z806', 'Z806双路嵌墙开关', '0006', '开关', '3', '1', 'dest', 'Z806.jpg', 'Z806是一款无线智能开关控制设备，带有双路通断能力的继电器，用户可以通过外接开关、绑定设备或软件无线控制开关输出。', '100～240V的交流电源', '不需要激活', '按住绑定键的同时给设备上电，开始恢复出厂设置，状态指示灯一直闪烁表示擦除完成。之后重新上电，Z806可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('5', '4', 'ZB02A', 'ZB02单键墙面开关', '0006', '开关', '3', '1', 'source', 'ZB02A.jpg', 'ZB02A是单键无线可壁挂按键开关。可以与有开关控制功能的设备绑定，无线控制其开关。', '2节AAA1.5V电池', '拆下面板的上盖，短按绑定键（红色按键），LED网络灯闪5下激活设备。', '拆下面板的上盖，按住绑定键的同时给设备上电，进行恢复出厂设置，看到状态指示灯闪烁，表示恢复完成', '无');
INSERT INTO `modenodelib` VALUES ('7', '9', 'Z503', 'Z503多功能遙控器', '0006:0008', '开关:调级控制', '1', '1', 'source', 'Z503.jpg', 'Z503功能遥控器,可与开关、调级设备绑定，通过遥控器控制设备动作。也可用于系统布撤防和紧急报警等。', '3V CR2034纽扣电池', '按住“2nd”键和“3”键可激活设备。', '1.同时按住扩展情景键和第二功能按键，给设备上电；2. 如指示灯快速闪烁10次表示恢复出厂值完成，设备自动重启后即可重新加网；3. 如指示灯慢闪3次，表示恢复出厂值失败，请重复步骤1。', '无');
INSERT INTO `modenodelib` VALUES ('10', '0', 'Z805A', 'Z805A单路电能检测嵌墙开关', '0006', '开关', '3', '1', 'dest', 'Z805A.jpg', 'Z805A是一款无线智能开关控制设备，带有单路16A/250V AC通断能力的继电器，用户可以通过外接开关、绑定设备或软件无线控制开关输出，并且可通过ZiG-BUTLER软件查看当前负载的电流电压及功率电能值。', '100～240V的交流电源', '不需要激活', '长按绑定键15S(指示灯闪烁2次,3S闪烁一次，10S闪烁一次），状态灯开始快闪20秒，设备进行恢复出厂设置；20s内短按任意键，Z805B重新请求加网。', '无');
INSERT INTO `modenodelib` VALUES ('11', '0', 'Z805B', 'Z805B单路电能检测嵌墙开关', '0006', '开关', '3', '1', 'dest', 'Z805B.jpg', 'Z805B是一款无线智能开关控制设备，带有单路16A/250V AC通断能力的继电器，用户可以通过外接开关、绑定设备或软件无线控制开关输出，并且可通过软件查看当前负载的电流电压及功率电能值。', '100～240V的交流电源', '不需要激活', '长按绑定键15S(指示灯闪烁2次,3S闪烁一次，10S闪烁一次），状态灯开始快闪20秒，设备进行恢复出厂设置；20s内短按任意键，Z805B重新请求加网。', '无');
INSERT INTO `modenodelib` VALUES ('12', '0', 'Z810B', 'Z810B电能检测开关', '0006', '开关', '3', '1', 'dest', 'Z810B.jpg', 'Z810B是一款无线智能开关控制设备，使用100-240VAC 50/60HZ电源供电，用户可以通过开关按键、绑定设备或软件无线控制开关输出，并且可通过设备自带的LCD显示屏或软件查看当前负载的电流电压及功率电能值。', '100～240V的交流电源', '不需要激活', '按下绑定键15秒后（图标每隔5秒闪烁一下，闪烁3次），2秒内短按功能键，LCD数值区显示表示恢复完成，之后设备将自动重启。', '无');
INSERT INTO `modenodelib` VALUES ('13', '0', 'Z811', 'Z811四路开关', '0006', '开关', '3', '1', 'dest', 'Z811.jpg', 'Z811是一款无线智能开关控制设备，带有四路输出，用户可以通过绑定设备或软件无线控制开关输出。', '100～240V的交流电源', '不需要激活', '长按绑定键15秒（状态指示灯3S闪一次，10S闪一次,15S闪一次），然后短按，状态指示灯一直闪烁表示擦除完成。之后指示灯灭掉，指示Z811便可以重新加网。', '无');
INSERT INTO `modenodelib` VALUES ('14', '0', 'Z815J', 'Z815J双路电能检测墙面开关', '0006', '开关', '3', '1', 'dest', 'Z815J.jpg', 'Z815J有两路输出的开关设备，可直接安装于86盒中，替换家居中的普通墙面开关，能够检测和统计当前负载电流、电压、功率、电能值。用户可采用ZigBee遥控器、手机、按键开关等多种方式控制该设备。', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('15', '0', 'Z815L', 'Z815L单路电能检测可调墙面开关', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z815L.jpg', 'Z815L是有一路输出的调光设备，能实现开关和调光控制，可直接安装于86盒中，替换家居中的普通墙面开关，能够检测和统计当前负载电流、电压、功率、电能值。用户可采用ZigBee遥控器、手机、按键开关等多种方式控制该设备。', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('16', '0', 'Z815M', 'Z815M双路电能检测可调墙面开关', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z815M.jpg', 'Z815M是有两路输出的调光设备，能实现开关和调光控制，可直接安装于86盒中，替换家居中的普通墙面开关，能够检测和统计当前负载电流、电压、功率、电能值。用户可采用ZigBee遥控器、手机、按键开关等多种方式控制该设备。', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('17', '0', 'Z817A', 'Z817A吸顶电能检测开关', '0006', '开关', '3', '1', 'dest', 'Z817A.jpg', 'Z817A为吸顶式开关控制器，是室内使用的智能电气开关。可以通过按键开关、无线遥控或者软件操作等方式来控制输出的接通与断开。用户可以使用软件查看负载的电流、电压、功率、电能等参量。', '100～240V的交流电源', '不需要激活', '按住绑定键（正对logo靠左）15秒，设备网络灯闪烁一次，然后在2秒内短按任意键，设备网络灯快闪10次后重启。', '无');
INSERT INTO `modenodelib` VALUES ('18', '0', 'Z817B', 'Z817B吸顶电能检测调光开关', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z817B.jpg', ' Z817B为吸顶式调光开关控制器，是室内使用的智能电气开关。可以通过按键调级开关、无线遥控或者软件操作等方式来实现开关和调光控制。用户可以使用软件查看负载的电流、电压、功率、电能等参量。', '100～240V的交流电源', '不需要激活', '按住绑定键（正对logo靠左）15秒，设备网络灯闪烁一次，然后在2秒内短按任意键，设备网络灯快闪10次后重启。', '无');
INSERT INTO `modenodelib` VALUES ('19', '0', 'Z817C', 'Z817C吸顶红外感应开关', '0006', '开关', '3', '1', 'dest', 'Z817C.jpg', 'Z817C具有红外检测的功能，当检测到红外信号，则使外接负载接通，过30秒没有再检测到红外信号则使外接负载断开。设备具有检测外接负载的电流，电压，功率，电能的功能，可以使用软件查看检测到的值。', '100～240V的交流电源', '不需要激活', '按住绑定键（正对logo靠左）15秒，设备网络灯闪烁一次，然后在2秒内短按任意键，设备网络灯快闪10次后重启。', '无');
INSERT INTO `modenodelib` VALUES ('20', '0', 'Z825C', 'Z825C三路电能检测触控开关', '0006', '开关', '3', '1', 'dest', 'Z825C.jpg', 'Z825C是三路触控式墙面开关，可直接安装于86盒中，替换家居中的普通墙面开关，能够检测和统计当前负载电流、电压、功率、电能值。用户可采用ZigBee遥控器、手机、按键开关等多种方式控制该设备。', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib` VALUES ('21', '0', 'Z825E', 'Z825E两路电能检测可调触控开关', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z825E.jpg', 'Z825E是两路触控式墙面调光开关，可直接安装于86盒中，替换家居中的普通墙面开关，能够检测和统计当前负载电流、电压、功率、电能值。用户可采用ZigBee遥控器、手机、按键开关等多种方式控制该设备。', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib` VALUES ('22', '0', 'ZC06', 'ZC06LED调光灯管', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'ZC06.jpg', 'ZC06是可实现灯光亮度渐变256级调节,并可接受遥控器无线控制的高可靠性LED灯管,可以绑定设备、无线遥控或者软件操作等方式来实现开关和调光控制。ZC06采用恒流控制方式，输入电压可以从AC 100V到240V，100到240V输入可以达到相同的灯光效果。', '100～240V的交流电源', '不需要激活', '同时长按测试键和绑定键5s，5s时间到后指示灯闪10次后就恢复出厂设置成功，此时就可以重新加网。', '无');
INSERT INTO `modenodelib` VALUES ('23', '1', 'Z800', 'Z800电能检测插座', '0006', '开关', '3', '1', 'dest', 'Z800B.jpg', 'Z800是无线电流检测插座，设备本身自带按键开关，也可以通过绑定设备、软件等控制其开关。可通过软件查看负载的功率、电压、电流和电能情况。', '100～240V的交流电源', '不需要激活', '长按绑定键15S，状态灯（红灯）快闪20S；设备进入恢复出厂设置；再按任意键重新请求加网。', '无');
INSERT INTO `modenodelib` VALUES ('24', '1', 'Z803', 'Z803电能检测排插', '0006', '开关', '3', '1', 'dest', 'Z803.jpg', 'Z803是无线电流检测排插，带有四路插座和2路USB充电接口。可过设备自带的按键开关、已绑定设备、或软件无线控制Z803开关。设备LCD显示屏可显示所有负载的电流电压值功率，电能值。也可使用软件查看当前各个输出端口及总的负载的电流，电压值，功率，电能值。', '100～240V的交流电源', '不需要激活', '同时按下Match Key 和Bind Key 按键5秒后，！图标闪烁一下，代表长按了5秒。放开按键后，进行出厂设置，设备自动重新上电。', '无');
INSERT INTO `modenodelib` VALUES ('25', '1', 'Z808A', 'Z808A带USB电能检测插座', '0006', '开关', '3', '1', 'dest', 'Z808A.jpg', 'Z808A是无线电流检测插座，设备本身自带USB充电接口，和LCD屏幕。可通过按键开关，绑定设备或软件等控制其开关。LCD屏幕或通过软件可查看负载的功率、电压、电流和电能情况。', '100～240V的交流电源', '不需要激活', '长按绑定键（右边按键）15s（期间网络指示灯闪3次）后释放绑定键，并在2s内短按功能键（左边按键），即开始恢复出厂设置，网络灯快速闪烁十次后自动重启设备，开始寻找新的网络。', '无');
INSERT INTO `modenodelib` VALUES ('26', '1', 'Z808B', 'Z808B带USB电能检测调光插座', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z808B.jpg', 'Z808B是无线电流检测调光插座，设备本身自带USB充电接口，和LCD屏幕。可通过按键开关，绑定设备或软件等控制其开关和调光。LCD屏幕或通过软件可查看负载的功率、电压、电流和电能情况。', '100～240V的交流电源', '不需要激活', '长按绑定键（右边按键）15s（期间网络指示灯闪3次）后释放绑定键，并在2s内短按功能键（左边按键），即开始恢复出厂设置，网络灯快速闪烁十次后自动重启设备，开始寻找新的网络。', '无');
INSERT INTO `modenodelib` VALUES ('27', '1', 'Z809A', 'Z809A无线耗能检测插座', '0006', '开关', '3', '1', 'dest', 'Z809A.jpg', 'Z809A是无线电流检测插座，设备本身自带按键开关，也可以通过绑定设备、软件等控制其开关。可通过软件查看负载的功率、电压、电流和电能情况。', '100～240V的交流电源', '不需要激活', '　长按绑定键（侧面）15s（期间网络指示灯闪3次）后释放绑定键，并在2s内短按测试键（正面按键），即开始恢复出厂设置，网络灯快速闪烁十次后自动重启设备，开始寻找新的网络。	无', '无');
INSERT INTO `modenodelib` VALUES ('28', '1', 'Z809B', 'Z809B电能检测调光插座', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z809B.jpg', 'Z809B是无线电流检测调光插座，设备本身自带按键开关，也可以通过绑定设备、软件等控制其开关和调光。可通过软件查看负载的功率、电压、电流和电能情况。', '100～240V的交流电源', '不需要激活', '长按绑定键（侧面）15s（期间网络指示灯闪3次）后释放绑定键，并在2s内短按测试键（正面按键），即开始恢复出厂设置，网络灯快速闪烁十次后自动重启设备，开始寻找新的网络。', '无');
INSERT INTO `modenodelib` VALUES ('29', '1', 'Z816B', 'Z816B美规电能检测墙面插座', '0006', '开关', '3', '1', 'dest', 'Z816B.jpg', 'Z816B是美规无线电流检测墙面插座，设备本身自带按键开关，也可以通过绑定设备、软件等控制其开关。可通过软件查看负载的功率、电压、电流和电能情况。', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('30', '1', 'Z816G', 'Z816G英规电能检测墙面插座', '0006', '开关', '3', '1', 'dest', 'Z816G.jpg', 'Z816G是英规无线电流检测墙面插座，可直接安装于86盒中，替换家居中的普通墙面开关，设备本身自带按键开关，也可以通过绑定设备、软件等控制其开关。可通过软件查看负载的功率、电压、电流和电能情况。', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('31', '1', 'Z816H', 'Z816H中规电能检测墙面插座', '0006', '开关', '3', '1', 'dest', 'Z816HI.jpg', 'Z816H是中规无线电流检测墙面插座，可直接安装于86盒中，替换家居中的普通墙面开关，设备本身自带按键开关，也可以通过绑定设备、软件等控制其开关。可通过软件查看负载的功率、电压、电流和电能情况。', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('32', '2', 'Z711', 'Z711室内型温湿度感应器', null, null, null, '1', null, 'Z711.jpg', 'Z711是温湿度传感器，用于采集周围环境的温湿度数据，发送给显示设备。', '2节1.5V电池', '按下按键可激活设备', '1.  按住绑定键的同时给设备上电；2.  复位完成则led闪烁30次后停止；3.  重新上电，Z711可以开始重新加网。', '无');
INSERT INTO `modenodelib` VALUES ('33', '2', 'Z712', 'Z712户外型温湿度感应器', '', '', '', '1', '', 'Z712.jpg', 'Z712主要是用来检测室外空气中的温度和湿度，同时也增加了防水外壳进行保护，并通过无线网络将收集到的数据传送给其它设备显示出来.', '2节1.5V电池', '按下按键可激活设备', '1.  按住绑定键的同时给设备上电；2.  复位完成则led闪烁30次后停止；3.  重新上电，Z711可以开始重新加网。', '无');
INSERT INTO `modenodelib` VALUES ('34', '2', 'Z713', 'Z713户外型温湿度紫外线感应器', '', '', '', '1', '', 'Z713.jpg', 'Z713是温湿度及紫外线探测器，带太阳能电池板，同时也增加了防水外壳进行保护，用于采集周围环境的温湿度和紫外线强度，并将采集到的数据发送给显示设备。', '2节1.5V电池', '按下按键可激活设备', '1.  按住绑定键的同时给设备上电；2.  复位完成则led闪烁30次后停止；3.  重新上电，Z711可以开始重新加网。', '无');
INSERT INTO `modenodelib` VALUES ('35', '2', 'Z716A', 'Z713户外型温湿度紫外线感应器', '', '', '', '1', '', 'Z716A.jpg', 'Z716A是温湿度探测器，用于采集周围环境的温湿度在LCD上显示，也可将采集到的数据发送给显示设备。', '2节1.5V电池', '按下右边按键可激活设备', '设备上电后，同时按住“按键1”和“按键2” 5秒，开始恢复出厂值，LCD上显示所有图标画面，表示恢复出厂值完成，设备自动复位寻找加网。', '无');
INSERT INTO `modenodelib` VALUES ('36', '2', 'Z716B', 'Z716B带LCD室内型温度感应器', null, null, null, '1', null, 'Z716B.jpg', 'Z716B是温度探测器，用于采集周围环境的温度在LCD上显示，也可将采集到的数据发送给显示设备。', '2节1.5V电池', '按下右边按键可激活设备', '设备上电后，同时按住“按键1”和“按键2” 5秒，开始恢复出厂值，LCD上显示所有图标画面，表示恢复出厂值完成，设备自动复位寻找加网。', '无');
INSERT INTO `modenodelib` VALUES ('37', '3', 'Z302B', 'Z302B灯控光感器', '0006', '开关', '3', '1', 'source', 'Z302B.jpg', 'Z302B是灯光自动开关控制器，它可以与开关输出的设备绑定。当光线减弱到一定程度，Z302B会发送出On(开灯)命令控制与其绑定的设备；当光线增强到一定程度，Z302B发送Off (关灯)命令给其绑定的设备。', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '无');
INSERT INTO `modenodelib` VALUES ('38', '3', 'Z302H', 'Z302H调光器', '0006:0008', '开关:调级控制', '3', '1', 'source', 'Z302H.jpg', 'Z302H是灯光亮度调节器，能够感知外界光线的强度，并且判断是否需要改变其绑定的亮度可调的灯到新的亮度，从而使其控制的灯所照到的环境保持一个亮度。', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '无');
INSERT INTO `modenodelib` VALUES ('39', '3', 'Z302G', 'Z302G光线感应器', null, null, null, '1', null, 'Z302G.jpg', 'Z302G是环境照度自动侦测器及报告器，定时发送环境照度给能接收和显示环境照度的设备。', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '无');
INSERT INTO `modenodelib` VALUES ('40', '3', 'Z311B', 'Z311B灯控光感器', '0006', '开关', '3', '1', 'source', 'Z311B.jpg', 'Z31B是灯光自动开关控制器，它可以与开关输出的设备绑定。当光线减弱到一定程度，Z311B会发送出On(开灯)命令控制与其绑定的设备；当光线增强到一定程度，Z311B发送Off (关灯)命令给其绑定的设备。', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '同时按下绑定键和辅助键，两键同时按着5s，直到看到红色指示灯开始快闪，再松开按键。红色指示灯快闪20次后，设备复位将进入power off模式。', '无');
INSERT INTO `modenodelib` VALUES ('41', '3', 'Z311H', 'Z311H调光器', '0006:0008', '开关:调级控制', '3', '1', 'source', 'Z311H.jpg', 'Z311H是灯光亮度调节器，能够感知外界光线的强度，并且判断是否需要改变其绑定的亮度可调的灯到新的亮度，从而使其控制的灯所照到的环境保持一个亮度。', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '同时按下绑定键和辅助键，两键同时按着5s，直到看到红色指示灯开始快闪，再松开按键。红色指示灯快闪20次后，设备复位将进入power off模式。', '无');
INSERT INTO `modenodelib` VALUES ('42', '3', 'Z311G', 'Z311G光线感应器', '', '', '', '1', '', 'Z311G.jpg', 'Z311G是环境照度自动侦测器及报告器，定时发送环境照度给能接收和显示环境照度的设备。', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '同时按下绑定键和辅助键，两键同时按着5s，直到看到红色指示灯开始快闪，再松开按键。红色指示灯快闪20次后，设备复位将进入power off模式。', '无');
INSERT INTO `modenodelib` VALUES ('44', '4', 'ZB02B', 'ZB02B双路墙面开关', '0006', '开关', '3', '1', 'source', 'ZB02B.jpg', 'ZB02B是双键无线可壁挂按键开关。可以与有开关控制功能的设备绑定，无线控制其开关。', '2节AAA1.5V电池', '拆下面板的上盖，短按绑定键（红色按键），LED网络灯闪5下激活设备。', '拆下面板的上盖，按住绑定键的同时给设备上电，进行恢复出厂设置，看到状态指示灯闪烁，表示恢复完成', '无');
INSERT INTO `modenodelib` VALUES ('45', '4', 'ZB02C', 'ZB02B三路墙面开关', '0006', '开关', '3', '1', 'source', 'ZB02C.jpg', 'ZB02C是三键无线可壁挂按键开关。可以与有开关控制功能的设备绑定，无线控制其开关。', '2节AAA1.5V电池', '拆下面板的上盖，短按绑定键（红色按键），LED网络灯闪5下激活设备。', '拆下面板的上盖，按住绑定键的同时给设备上电，进行恢复出厂设置，看到状态指示灯闪烁，表示恢复完成', '无');
INSERT INTO `modenodelib` VALUES ('46', '4', 'ZB02F', 'ZB02F单路调光开关', '0006:0008', '开关:调级控制', '3', '1', 'source', 'ZB02F.jpg', 'ZB02F是单键无线可壁挂按键开关。可以与有开关和调级控制功能的设备绑定，无线控制设备的开关和调级。', '2节AAA1.5V电池', '拆下面板的上盖，短按绑定键（红色按键），LED网络灯闪5下激活设备。', '拆下面板的上盖，按住绑定键的同时给设备上电，进行恢复出厂设置，看到状态指示灯闪烁，表示恢复完成。', '无');
INSERT INTO `modenodelib` VALUES ('47', '5', 'ZB01A', 'ZB01A安防动作感应器', null, null, null, '1', null, 'ZB01A.jpg', 'ZB01A红外探测报警功能可检测到移动物体，并通过安防中心产生报警。', '2节CR123A电池或12V直流电源', '按住背面小圆点按键3秒以上，直至状态指示灯快速闪烁2次激活设备', '按住背面小圆点（绑定键）的同时给设备上电，状态灯快闪，即恢复出厂设置操作成功。恢复完成后重新上电就可以重新加入网络了。', '无');
INSERT INTO `modenodelib` VALUES ('48', '5', 'ZB01B', 'ZB01B红外人体感应开关', '0006', '开关', '3', '1', 'source', 'ZB01B.jpg', 'ZB01B是移动探测开关控制器，可检测到产生红外线的物体移动时，根据灯光阈值的设定，控制其他与之绑定设备的开关动作。', '2节CR123A电池或12V直流电源', '按住背面小圆点按键3秒以上，直至状态指示灯快速闪烁2次激活设备', '按住背面小圆点（绑定键）的同时给设备上电，状态灯快闪，即恢复出厂设置操作成功。恢复完成后重新上电就可以重新加入网络了。', '可通过调节设备背面的光照灵敏度来设置绑定开关的的光照阈值。');
INSERT INTO `modenodelib` VALUES ('49', '5', 'ZB01C', 'ZB01C带温度探测的红外感应开关', '0006', '开关', '3', '1', 'source', 'ZB01C.jpg', 'ZB01C同时具有红外探测报警、红外探测开关控制和温度报告3种功能。红外探测报警功能可检测到移动物体，并通过安防中心产生报警。红外检测开关控制功能可检测到移动物体所产生的红外线控制灯的亮灭。温度报告功能可检测当前环境的温度，并定期报告给其绑定的设备。', '2节CR123A电池或12V直流电源', '按住背面小圆点按键3秒以上，直至状态指示灯快速闪烁2次激活设备', '按住背面小圆点（绑定键）的同时给设备上电，状态灯快闪，即恢复出厂设置操作成功。恢复完成后重新上电就可以重新加入网络了。', '可通过调节设备背面的光照灵敏度来设置绑定开关的的光照阈值。');
INSERT INTO `modenodelib` VALUES ('50', '5', 'ZB01D', 'ZB01D红外占用感应器', null, null, null, '1', null, 'ZB01D.jpg', 'ZB01D是占有传感器，可检测到产生红外线的物体的移动，并报告状态给其绑定的设备。', '2节CR123A电池或12V直流电源', '按住背面小圆点(绑定键)按键3秒以上，直至状态指示灯快速闪烁2次激活设备', '按住背面小圆点（绑定键）的同时给设备上电，状态灯快闪，即恢复出厂设置操作成功。恢复完成后重新上电就可以重新加入网络了。', '无');
INSERT INTO `modenodelib` VALUES ('51', '5', 'ZB11A', 'ZB11A安防动作感应器', '', '', '', '1', '', 'ZB11A.jpg', 'ZB11A红外探测报警功能可检测到移动物体，并通过安防中心产生报警。', '2节ER14505 3.6V AA电池供电', '正对logo，短按右侧按钮指示灯闪5下，即可激活设备', '长按辅助键（正对logo右边）10秒，见到绿色指示灯快闪即可松开按键，设备将进行恢复出厂设置操作，完成后将自动关机。', '无');
INSERT INTO `modenodelib` VALUES ('52', '5', 'ZB11B', 'ZB11B红外人体感应开关', '0006', '开关', '3', '1', 'source', 'ZB11B.jpg', 'ZB11B是移动探测开关控制器，可检测到产生红外线的物体移动时，根据灯光阈值的设定，控制其他与之绑定设备的开关动作。', '2节ER14505 3.6V AA电池供电', '正对logo，短按右侧按钮指示灯闪5下，即可激活设备', '长按辅助键（正对logo右边）10秒，见到绿色指示灯快闪即可松开按键，设备将进行恢复出厂设置操作，完成后将自动关机。', '可通过调节设备背面的光照灵敏度来设置绑定开关的的光照阈值。');
INSERT INTO `modenodelib` VALUES ('53', '5', 'ZB11C', 'ZB01C带温度探测的红外感应开关', '0006', '开关', '3', '1', 'source', 'ZB11C.jpg', 'ZB11C同时具有红外探测报警、红外探测开关控制和温度报告3种功能。红外探测报警功能可检测到移动物体，并通过安防中心产生报警。红外检测开关控制功能可检测到移动物体所产生的红外线控制灯的亮灭。温度报告功能可检测当前环境的温度，并定期报告给其绑定的设备。', '2节ER14505 3.6V AA电池供电', '正对logo，短按右侧按钮指示灯闪5下，即可激活设备', '长按辅助键（正对logo右边）10秒，见到绿色指示灯快闪即可松开按键，设备将进行恢复出厂设置操作，完成后将自动关机。', '可通过调节设备背面的光照灵敏度来设置绑定开关的的光照阈值。');
INSERT INTO `modenodelib` VALUES ('54', '5', 'ZB11D', 'Z815N窗帘控制电能检测墙面开关', '', '', '', '1', '', 'ZB11D.jpg', 'ZB11D是占有传感器，可检测到产生红外线的物体的移动，并报告状态给其绑定的设备。', '2节ER14505 3.6V AA电池供电', '正对logo，短按右侧按钮指示灯闪5下，即可激活设备', '长按辅助键（正对logo右边）10秒，见到绿色指示灯快闪即可松开按键，设备将进行恢复出厂设置操作，完成后将自动关机。', '无');
INSERT INTO `modenodelib` VALUES ('55', '6', 'Z815N', 'Z815N窗帘控制墙面开关', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z815N.jpg', 'Z815N是窗帘控制器，可以通过自身的按键、绑定的设备或软件控制窗帘的开关和级别。同时用户可以使用软件查看当前负载的电流、电压、功率和电能值。', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', 'Lin接火线，N线是零线接电机的蓝线. L out1接电机的棕线，L out2接电机的黑线。在设备执行窗帘控制功能前,都应先进行“学习上下限”的学习，学习方法请详见设备说明书或在app的设备管理模块，该设备的设置页面进行学习。');
INSERT INTO `modenodelib` VALUES ('56', '6', 'ZD01B', 'ZD01B窗帘控制器', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'ZD01B.jpg', 'ZD01B是窗帘控制器，可以通过自身的按键、绑定的设备或软件控制窗帘的开关和级别。', '12V4A直流电源', '不需要激活', '按住ON1和OFF1键（靠右边一列）的同时给设备上电，开始执行恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重新上电就可以重新加网了', '在设备执行窗帘控制功能前,都应先进行“学习上下限”的学习，学习方法请详见设备说明书或在app的设备管理模块，该设备的设置页面进行学习');
INSERT INTO `modenodelib` VALUES ('57', '7', 'Z302A', 'Z302A窗磁感應器', null, null, null, '1', null, 'Z302A.jpg', 'Z302A是门磁（窗磁）设备，作为安防系统中的1个检测设备，当门或窗被打开，它将发出报警信息给安防中心设备，当门或窗被关闭好，它会发送出状态恢复正常的信息给安防中心设备。', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', 'Z302A的本体与门磁的磁铁部分别安装在门或窗户的缝隙的两边，当门或窗被打开，它将发出报警信息给安防中心设备( 即CIE )，当门或窗被关闭好，它会发送出状态恢复正常的信息给CIE。该设备也可以用于冰箱、抽屉等处，用于检测冰箱或抽屉等是否被打开。需要注意防水。');
INSERT INTO `modenodelib` VALUES ('58', '7', 'Z302C', 'Z302C窗戶感應器', null, null, null, '1', null, 'Z302C.jpg', 'Z302C是安防系统的1个检测设备，作为门窗被异常打开或者门窗玻璃被打碎的报警设备使用。', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', 'Z302C的本体与门磁的磁铁部分别安装在门或窗户的缝隙的两边，当门或窗被打开，它将发出报警信息给安防中心设备( 即CIE )，当门或窗被关闭好，它会发送出状态恢复正常的信息给CIE。该设备也可以用于冰箱、抽屉等处，用于检测冰箱或抽屉等是否被打开。需要注意防水。');
INSERT INTO `modenodelib` VALUES ('59', '7', 'Z302D', 'Z302D緊急報警器', null, null, null, '1', null, 'Z302D.jpg', 'Z302D是紧急报警触发设备，作为安防系统中的1个检测设备，它可以戴在孩子或者老人的手腕，当孩子或者老人遇到危险需要紧急救助的时候，按下Z302D的报警键，Z302D立即向安防中心发送出报警信息。', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '无');
INSERT INTO `modenodelib` VALUES ('60', '7', 'Z302E', 'Z302E贵重物品标签', null, null, null, '1', null, 'Z302E.jpg', 'Z302E设备内置光敏和位移传感器，可用于外界环境光强检测和位置移动检测，当状态改变时，它将发出报警信息给安防中心设备。', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '可将该设备放于贵重的物品或抽屉旁，当物品被移动或光线发生变化时，则设备会将报警信息发送给安防中心设备。');
INSERT INTO `modenodelib` VALUES ('61', '7', 'Z302J', 'Z302J门磁感应开关', '0006', '开关', '3', '1', 'source', 'Z302J.jpg', 'Z302J是门磁设备，当门或窗的状态变化时，能根据配置控制绑定设备的开/关动作；Z302J还作为安防系统中的1个检测设备，当门或窗被打开，它将发出报警信息给安防中心设备，当门或窗被关闭好，它会发送出状态恢复正常的信息给CIE。', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', 'Z302J的本体与门磁的磁铁部分别安装在门或窗户的缝隙的两边，当门或窗被打开，它将发出报警信息给安防中心设备( 即CIE )，当门或窗被关闭好，它会发送出状态恢复正常的信息给CIE。该设备也可以用于冰箱、抽屉等处，用于检测冰箱或抽屉等是否被打开。需要注意防水。');
INSERT INTO `modenodelib` VALUES ('62', '7', 'Z306D', 'Z306D定位紧急按鈕套件', null, null, null, '1', null, 'Z307.jpg', 'Z306D设备在网络中作为ED使用。是location网络的mobile tags。使用第三方软件（如：netvox公司的ZiG-BUTLER软件）通过它进行设备的搜索与控制。', '3V CR2450钮扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '无');
INSERT INTO `modenodelib` VALUES ('63', '7', 'Z311A', 'Z311A窗磁感應器', null, '', '', '1', '', 'Z311A.jpg', 'Z311A是门磁（窗磁）设备，作为安防系统中的1个检测设备，当门或窗被打开，它将发出报警信息给安防中心设备，当门或窗被关闭好，它会发送出状态恢复正常的信息给安防中心设备。', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '同时按下绑定键和辅助键，两键同时按着5s，直到看到红色指示灯开始快闪，再松开按键。红色指示灯快闪20次后，设备复位将进入power off模式。', 'Z311A的本体与门磁的磁铁部分别安装在门或窗户的缝隙的两边，当门或窗被打开，它将发出报警信息给安防中心设备( 即CIE )，当门或窗被关闭好，它会发送出状态恢复正常的信息给CIE。该设备也可以用于冰箱、抽屉等处，用于检测冰箱或抽屉等是否被打开。需要注意防水。');
INSERT INTO `modenodelib` VALUES ('64', '7', 'Z311C', 'Z311C窗戶感應器', null, '', '', '1', '', 'Z311C.jpg', 'Z311C是安防系统的1个检测设备，作为门窗被异常打开或者门窗玻璃被打碎的报警设备使用。', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '同时按下绑定键和辅助键，两键同时按着5s，直到看到红色指示灯开始快闪，再松开按键。红色指示灯快闪20次后，设备复位将进入power off模式。', 'Z311A的本体与门磁的磁铁部分别安装在门或窗户的缝隙的两边，当门或窗被打开，它将发出报警信息给安防中心设备( 即CIE )，当门或窗被关闭好，它会发送出状态恢复正常的信息给CIE。该设备也可以用于冰箱、抽屉等处，用于检测冰箱或抽屉等是否被打开。需要注意防水。');
INSERT INTO `modenodelib` VALUES ('65', '7', 'Z312', 'Z312无线门铃按键', null, null, null, '1', null, 'Z312.jpg', 'Z312门铃按键可与警报器设备绑定,控制其发出门铃响声。', '3V CR2450纽扣电池', '　　　　当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '同时按下绑定键和辅助键，两键同时按着5s，直到看到红色指示灯开始快闪，再松开按键。红色指示灯快闪20次后，设备复位将进入power off模式。', '无');
INSERT INTO `modenodelib` VALUES ('66', '7', 'Z307', 'Z307充电式跌倒感应器', null, null, null, '1', null, 'Z307.jpg', 'Z307是摔倒感知器，作为安防系统中的1个检测设备来使用。它一般佩戴在家中老人和孩子等需要照顾者的腰间，当佩戴者摔倒时，它将发出报警信息给安防中心设备。', '充电套件', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '无');
INSERT INTO `modenodelib` VALUES ('67', '7', 'Z308', 'Z308无线紧急按钮', null, null, null, '1', null, 'Z308.jpg', 'Z308是紧急报警触发设备，作为安防系统中的1个检测设备，它可以戴在孩子或者老人的手腕，当孩子或者老人遇到危险需要紧急救助的时候，按下Z308的报警键，Z308立即向安防中心发送出报警信息。', '3V CR2450纽扣电池', '长按按键3s，红色指示灯闪一下，松开手后，绿色指示灯闪五下。即激活设备。', '长按报警键15秒以上，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯快闪10次后，设备进入关机状态，红色指示灯熄灭。', '无');
INSERT INTO `modenodelib` VALUES ('68', '7', 'ZA01A', 'ZA01A烟雾感应器', null, null, null, '1', null, 'ZA01A.jpg', 'ZA01A是用于家庭环境的有害气体探测的设备，在网络中担当安防设备的角色。当空气中可检测的气体浓度超过设备的界限时，设备发出告警声，同时向安防中心发送状态改变信息。', '12V直流电源或100～240V交流供电', '不需要激活', '同时按住绑定键和测试键5秒以上，设备指示灯开始快速闪烁，表示复位完成。之后将设备重启，就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('69', '7', 'ZA01B', 'ZA01B瓦斯感应器', '', '', '', '1', '', 'ZA01B.jpg', 'ZA01B是瓦斯探测器，在网络中担当安防设备的角色。当空气中瓦丝的浓度超过所设定的界限时，设备将安防中心发送报警信息，设备本身也会有声音的报警，以提醒用户，确保用户生命财产的安全。', '12V直流电源或100～240V交流供电', '不需要激活', '同时按住绑定键和测试键5秒以上，设备指示灯开始快速闪烁，表示复位完成。之后将设备重启，就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('70', '7', 'ZA01C', 'ZA01C一氧化碳感应器', null, null, null, '1', null, 'ZA01C.jpg', 'ZA01C是一氧化碳感应器，在网络中担当安防设备的角色。当空气中一氧化碳的浓度超过所设定的界限时，设备将安防中心发送报警信息，设备本身也会有声音的报警，以提醒用户，确保用户生命财产的安全。', '12V直流电源或100～240V交流供电', '不需要激活', '同时按住绑定键和测试键5秒以上，设备指示灯开始快速闪烁，表示复位完成。之后将设备重启，就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('71', '7', 'ZA01D', 'ZA01D液化石油气感应', null, null, null, '1', null, 'ZA01D.jpg', 'ZA01D是液态瓦斯探测器，在网络中担当安防设备的角色。当空气中液态瓦斯的浓度超过所设定的界限时，设备将安防中心发送报警信息，设备本身也会有声音的报警，以提醒用户，确保用户生命财产的安全。', '12V直流电源或100～240V交流供电', '不需要激活', '同时按住绑定键和测试键5秒以上，设备指示灯开始快速闪烁，表示复位完成。之后将设备重启，就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('72', '7', 'ZA02E', 'ZA02E烟雾感应器', null, null, null, '1', null, 'ZA02E.jpg', 'ZA02E是烟雾探测器，当空气中烟雾的浓度超过所设定的界限时，设备将安防中心发送报警信息，设备本身也会有声音的报警，以提醒用户，确保用户生命财产的安全。', '12V直流电源或100～241V交流供电', '不需要激活', '同时按住绑定键和测试键5秒以上，设备指示灯开始快速闪烁，表示复位完成。之后将设备重启，就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('73', '7', 'ZA10', 'ZA10智能阀门', '0006', '开关', '3', '1', 'dest', 'ZA10.jpg', 'ZA10可作为煤气阀门和水闸开关。做为Gas Keeper时，当网络中报火警时，设备会根据接收到的火警命令将管道关闭。', '12V直流电源', '不需要激活', '按住按键的同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复出厂设置完成，重新上电就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('74', '7', 'ZB05', 'ZB05智能门锁', '0101', '开关锁', '3', '1', 'dest', 'ZB05.jpg', 'ZB05可对锁进行开、关控制，包括机械钥匙、密码、IC卡、远程控制等多种方式。用户可通过ZigBee无线网络随时查看和控制门锁状态，智能化的门锁设计拥有可靠的密码保障和感应火灾性能，保障家居安全。', '8节AA1.5V电池', '不需要激活', '1.按住网络键的同时给设备上电，上电完请不要立即松开按键，等到网络灯开始快速闪烁，提示复位成功；2.重新上电，ZB05可以开始重新加网。', '1、初次使用时，需要注册zigbee：在门打开的状态下，打开内侧用（主锁身）的电池盖和外盒，在右侧下端按“锁卡注册键”。然后再按下面的“ZB注册键”（靠上的按键）。按“*”键，可听到“哔呖呖”的设置音，设置结束。2、默认都为加密的方式，第一次使用时，需要在app中→设备管理模块→进入门锁的设置页面，设置新的用户和密码，才可以对锁进行开关控制。门锁默认有6个空用户，注册新用户前，需要先将这些用户删除。超级密码默认为：123456.usercode即用户名为1-254的数字。');
INSERT INTO `modenodelib` VALUES ('75', '7', 'ZB02E', 'ZB02E门铃按键', null, null, null, '1', null, 'ZB02E.jpg', 'ZB02E门铃按键可与警报器设备绑定,控制其发出门铃响声。', '2节AAA1.5V电池', '拆下面板的上盖，短按绑定键（红色按键），LED网络灯闪5下激活设备。', '拆下面板的上盖，按住绑定键的同时给设备上电，进行恢复出厂设置，看到状态指示灯闪烁，表示恢复完成', '无');
INSERT INTO `modenodelib` VALUES ('76', '7', 'Z311E', 'Z311E贵重物品标签', '', '', '', '1', '', 'Z311E.jpg', 'Z311E设备内置光敏和位移传感器，可用于外界环境光强检测和位置移动检测，当状态改变时，它将发出报警信息给安防中心设备。', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '同时按下绑定键和辅助键，两键同时按着5s，直到看到红色指示灯开始快闪，再松开按键。红色指示灯快闪20次后，设备复位将进入power off模式。', '可将该设备放于贵重的物品或抽屉旁，当物品被移动或光线发生变化时，则设备会将报警信息发送给安防中心设备。');
INSERT INTO `modenodelib` VALUES ('77', '7', 'Z602A', 'Z602A智能声光报警器', null, null, null, '1', null, 'Z602A.jpg', 'Z602A是报警器，通过指示灯闪烁、喇叭发出报警声音对安防系统内的警情进行报警。', '12V直流电源和3节1.2V可充电备用电池', '不需要激活', '按住设置键（正对logo右侧按键）的同时给设备上电，状态灯快速闪烁且发出两次门铃报警声，即恢复出厂设置操作成功。恢复完成后重新上电就可以重新加入网络了。', '无');
INSERT INTO `modenodelib` VALUES ('78', '7', 'Z602B', 'Z602B智能声光报警器', null, null, null, '1', null, 'Z602B.jpg', 'Z602B是报警器，通过指示灯闪烁、喇叭发出报警声音对安防系统内的警情进行报警。\n设备在发出报警音的同时，根据报警的级别选择通过语音呼叫和短信发送方式通知预先设置的手机号，可以实时收发语音信息或短消息。', '12V直流电源和3节1.2V可充电备用电池', '不需要激活', '按住设置键（正对logo右侧按键）的同时给设备上电，状态灯快速闪烁且发出两次门铃报警声，即恢复出厂设置操作成功。恢复完成后重新上电就可以重新加入网络了。', '该设备具有GSM功能，可在电池盒内装入SIM卡，在app的设备管理模块，进入该设备的设置页面，可设置电话号码，用于报警时，通过拨打电话和发送短信通知用户。设置的短信号码前，需要加国家代码（如中国：86）');
INSERT INTO `modenodelib` VALUES ('79', '7', 'Z601A', 'Z601A警报器', null, null, null, '1', null, 'Z601A.jpg', 'Z601A是报警器，通过喇叭发出报警声音对安防系统内的警情进行报警。', '12V直流电源', '不需要激活', '按住绑定键的同时给设备上电，状态灯快闪，即恢复出厂设置操作成功。恢复完成后重新上电就可以重新加入网络了。', '无');
INSERT INTO `modenodelib` VALUES ('80', '8', 'Z821', 'Z821多路电能检测器', null, null, null, '1', null, 'Z821.jpg', 'Z821是室内使用的智能多路电能检测器,可以检测单路输入电源电压和七路设备的电流，并且可以算得其功率、电能等参量。', '100～240V的交流电源', '不需要激活', '按下Bind Key按键15秒后（状态灯每5秒钟闪烁1次，共闪烁3次，代表长按了15秒），松开按键，在两秒之内短按Match Key进行网络信息的擦除。网络信息擦除后设备自动重启寻找网络。', '将七路CT端子（用于检测电能）接入到Z821两侧的接口。按照CT端子上指示的K→L方向（电流流动的方向）,将导线扣入CT端子，即可检测该路的电能。');
INSERT INTO `modenodelib` VALUES ('81', '9', 'Z501A', 'Z501A四鍵遙控器(250)', '0006:0008', '开关:调级控制', '3', '1', 'source', 'Z501A.jpg', 'Z501A做为远程遥控器，它可与开关及级别可控的设备进行绑定，来控制其开关及级别值，同时设备还设有紧急按钮，在遇到紧急情况时按下紧急按钮通知家人以寻求帮助。', '3V CR2032纽扣电池', '右侧开关off断电，重新上电后即可激活设备。', '按住“开关键” 的同时给设备上电，开始恢复出厂值，指示灯共闪烁20次，之后重新操作组合键就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('82', '9', 'Z501B', 'Z501A四鍵可调级遙控器(250)', '0006', '开关', '3', '1', 'source', 'Z501B.jpg', 'Z501B做为远程遥控器，它可与开关可控的设备进行绑定，来控制其他设备的开关。', '3V CR2032纽扣电池', '右侧开关off断电，重新上电后即可激活设备。', '按住“ON2键” 的同时给设备上电，开始恢复出厂值，指示灯共闪烁20次，之后重新操作组合键就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('83', '9', 'Z501C', 'Z501C四鍵可调级遙控器(250)', '0006:0008', '开关:调级控制', '3', '1', 'source', 'Z501C.jpg', 'Z501C可与具有开关功能/级别控制功能的设备绑定控制其开关及级别值。', '3V CR2033纽扣电池', '右侧开关off断电，重新上电后即可激活设备。', '按住“ ON/OFF1键” 的同时给设备上电，开始恢复出厂值，指示灯共闪烁20次，之后重新操作组合键就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('84', '10', 'Z210', 'Z210红外线转接器', null, null, null, '1', null, 'Z210C.jpg', 'Z210具有安防系统控制指示中心的功能，可管理安防设备。此外，Z210具有红外功能，可学习和转发各种红外信号（如电视，DVD等）。', '12V直流电源', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '使用红外转接器控制家用电器时，需要在app上导入红外数据，若无红外数据时，则需要通过IR学习器或手机app手动学习。');
INSERT INTO `modenodelib` VALUES ('85', '10', 'Z211', 'Z211智能红外控制转换器', null, null, null, '1', null, 'Z211.jpg', 'Z211是红外学习及红外控制的设备，经过IR学习后其可以直接控制可以接收IR控制的电器设备，同时可以接收红外遥控器发出IR信号直接控制ZigBee设备。', '12V直流电源', '不需要激活', '按住绑定键（蓝色按键），同时给设备上电，开始恢复出厂设置，直到状态指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '使用红外转接器控制家用电器时，需要在app上导入红外数据，若无红外数据时，则需要通过IR学习器或手机app手动学习。');
INSERT INTO `modenodelib` VALUES ('86', '11', 'Z203', 'Z203云智能网关', null, null, null, '1', null, 'Z203.jpg', 'Z203 作为奈伯思智能家居系统的核心网关，率先实现云端技术、WiFi移动联网技术与ZigBee智能家居物联网术的完美结合，让用户只需要通过手机APP连接Wifi即可控制家里的灯光、窗帘、各种电器等设备的开关，出门在外通过2G/3G网络可监控家中的一切变化。', '12V直流电源', '不需要激活', '详见203说明书', 'Z203可用12V直流电源供电，给Z203上电后，打开手机的WiFi功能，即可搜索到Z203的无线信号。外网网线接入Z203的WAN口，LAN口可直接接IPcamera或电脑，当家中有多个 IPcamera时，需要增加集线器。');
INSERT INTO `modenodelib` VALUES ('87', '11', 'Z103AC', 'Z103USB协调器', null, null, null, '1', null, 'Z103AC.jpg', 'USB协调器', 'USB', '不需要激活', '按住PermitJoin键的同时给Z103设备上电，设备指示灯处于快速闪烁状态，表示恢复出厂设置完成，设备重新上电就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('88', '11', 'Z103AR', 'Z103USB路由器', null, null, null, '1', null, 'Z103AR.jpg', 'USB路由器', 'USB', '不需要激活', '按住PermitJoin键的同时给Z103设备上电，设备指示灯处于快速闪烁状态，表示恢复出厂设置完成，设备重新上电就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('89', '11', 'Z201B', 'Z201B HA 协调器+CIE', null, null, null, '1', null, 'Z201B.jpg', 'HA 协调器+CIE', '12V直流电源', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');
INSERT INTO `modenodelib` VALUES ('90', '11', 'Z201C', 'Z201C HA 协调器', null, null, null, '1', null, 'Z201C.jpg', 'HA 协调器', '12V直流电源', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');
INSERT INTO `modenodelib` VALUES ('91', '11', 'Z201R HA', 'Z201R HA中继器', null, null, null, '1', null, 'Z201R.jpg', 'HA中继器', '12V直流电源', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');
INSERT INTO `modenodelib` VALUES ('92', '11', 'Z201R', 'Z201R 定位用参考点', null, null, null, '1', null, 'Z201R.jpg', '定位参考点', '12V直流电源', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');
INSERT INTO `modenodelib` VALUES ('93', '4', 'Z812A', 'Z812A多键可编程场景控制面板', '0006', '开关', '3', '1', 'source', 'Z812A.jpg', 'Z812A是多路无线可壁挂按键开关。可以与有开关控制功能的设备绑定，无线控制其开关。', '100～240V的交流电源', '不需要激活', '上电以后，同时按住OFF3和OFF4键，按住5秒，网络灯灭，然后放开按键，开始恢复出厂值，直到网络灯开始闪烁，表示恢复完成。之后设备将自动复位，可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('94', '4', 'Z812B', 'Z812B多键电池驱动型控制面板', '0006:0008', '开关:调级控制', '3', '1', 'source', 'Z812B.jpg', 'Z812B是多路无线可壁挂按键开关。可以与有开关控制功能的设备绑定，无线控制其开关。', '100～240V的交流电源', '同时按住第一行两个按键即可激活设备。', '同时按住按键3和按键4达3秒，指示灯闪烁1次提示设备开始恢复出厂设置，恢复成功指示灯快闪10次，不成功指示灯无动作。恢复出厂设置之后设备自动重启。', '无');
INSERT INTO `modenodelib` VALUES ('95', '7', 'Z801TXB', 'Z801TXB脉冲信号探测器', '', '', '', '1', '', 'Z801TXB.jpg', 'Z801TXB脉冲信号探测器', '5V直流电源或2节1.5V电池', '不需要激活', '按住绑定键的同时给设备上电，LED2闪烁20次，表示复位完成，之后重新上电，Z801 TXB可以重新加网。 ', '无');
INSERT INTO `modenodelib` VALUES ('96', '0', 'Z801RX', 'Z801RX弱电继电器', '0006', '开关', '3', '1', 'dest', 'Z801RX.jpg', 'Z801RX弱电继电器', '2节1.2V电池', '不需要激活', '1.  按住绑定键的同时给设备上电；2.  复位完成则状态指示灯快速灯闪烁；3.  重新上电，Z801RX可以开始重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('100', '11', 'Z508', 'Z508LCD资讯显示屏', null, null, null, '1', null, 'Z508.jpg', 'Z508是一款用于显示家居中各类资讯和统计各种居家电能数据的智能显示终端。Z508作为智能家居的集控中心，用户可以在其屏幕上查看当前和历史用电信息。通过ZiG-BUTLER同步下载智能家居模式后通过Z508用户可以方便地对智能家居模式进行切换选择，同时Z508具有CIE的功能可以管理安防系统，并将安防相关信息显示在其屏幕上。', '5V直流电源或2节1.5V电池', '不需要激活', '详见Z508说明书', '无');
INSERT INTO `modenodelib` VALUES ('101', '0', 'Z825A', 'Z825A一路触控式墙面开关', '0006', '开关', '3', '1', 'dest', 'Z825A.jpg', 'Z825A是一路触控式墙面开关，可直接安装于86盒中，替换家居中的普通墙面开关，能够检测和统计当前负载电流、电压、功率、电能值。用户可采用ZigBee遥控器、手机、按键开关等多种方式控制该设备。', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib` VALUES ('102', '0', 'Z825B', 'Z825B二路触控式墙面开关', '0006', '开关', '3', '1', 'dest', 'Z825B.jpg', 'Z825B是两路触控式墙面开关，可直接安装于86盒中，替换家居中的普通墙面开关，能够检测和统计当前负载电流、电压、功率、电能值。用户可采用ZigBee遥控器、手机、按键开关等多种方式控制该设备。', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib` VALUES ('103', '0', 'Z825D', 'Z825D一路触控式墙面调光开关', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z825D.jpg', 'Z825D是一路触控式墙面调光开关，可直接安装于86盒中，替换家居中的普通墙面开关，能够检测和统计当前负载电流、电压、功率、电能值。用户可采用ZigBee遥控器、手机、按键开关等多种方式控制该设备。', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib` VALUES ('104', '2', 'Z725A', 'Z725A户外温湿度探测器', null, null, null, '1', null, 'Z725A.jpg', 'Z725A是温湿度及紫外线探测器，带太阳能电池板，同时也增加了防水外壳进行保护，用于采集周围环境的温湿度和紫外线强度，并将采集到的数据发送给显示设备。', '太阳能充电电池', '不需要激活', '设备上电后，同时按住“绑定键”和“设置键” 5秒，开始恢复出厂值，LED快闪10次，表示恢复出厂值完成，设备自动复位寻找加网。', '无');
INSERT INTO `modenodelib` VALUES ('106', '11', 'Z725R', 'Z725R户外型网络中继器', null, null, null, '1', null, 'Z725R.jpg', 'Z725R户外型网络中继器', '太阳能充电电池', '不需要激活', '设备上电后，同时按住绑定键和辅助键5秒，状态灯闪烁1次后，开始恢复出厂值，LED快闪20次，表示恢复出厂值完成，设备自动复位寻找加网。', '无');
INSERT INTO `modenodelib` VALUES ('107', '0', 'Z962A', 'Z962A一路触控式墙面开关', '0006', '开关', '3', '1', 'dest', 'Z962A.jpg', 'Z962A是一路触控式墙面开关，可直接安装于86盒中，替换家居中的普通墙面开关，能够检测和统计当前负载电流、电压、功率、电能值。用户可采用ZigBee遥控器、手机、按键开关等多种方式控制该设备。', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib` VALUES ('108', '0', 'Z962B', 'Z962B二路触控式墙面开关', '0006', '开关', '3', '1', 'dest', 'Z962B.jpg', 'Z962B是两路触控式墙面开关，可直接安装于86盒中，替换家居中的普通墙面开关，能够检测和统计当前负载电流、电压、功率、电能值。用户可采用ZigBee遥控器、手机、按键开关等多种方式控制该设备。', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib` VALUES ('109', '0', 'Z962C', 'Z962C三路触控式墙面开关', '0006', '开关', '3', '1', 'dest', 'Z962C.jpg', 'Z962C是三路触控式墙面开关，可直接安装于86盒中，替换家居中的普通墙面开关，能够检测和统计当前负载电流、电压、功率、电能值。用户可采用ZigBee遥控器、手机、按键开关等多种方式控制该设备。', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib` VALUES ('110', '0', 'Z962D', 'Z962D一键触摸情景开关', null, null, null, '1', null, 'Z962D.jpg', 'Z962D作为情景和模式选择器，不仅能通过1个触摸按键实现添加组、存储情景和调用场景的功能来实现记忆1个场景的作用。且能支持的奈伯思系统模式控制功能。', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib` VALUES ('111', '0', 'Z962E', 'Z962E双键触摸情景开关', null, null, null, '1', null, 'Z962E.jpg', 'Z962E作为情景和模式选择器，不仅能通过2个触摸按键实现添加组、存储情景和调用场景的功能来实现记忆2个场景的作用。且能支持的奈伯思系统模式控制功能。', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib` VALUES ('112', '0', 'Z962F', 'Z962F三键触摸情景开关', null, null, null, '1', null, 'Z962F.jpg', 'Z962F作为情景和模式选择器，不仅能通过3个触摸按键实现添加组、存储情景和调用场景的功能来实现记忆3个场景的作用。且能支持的奈伯思系统模式控制功能。', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib` VALUES ('113', '0', 'Z962G', 'Z962G一路开关输出和一路情景开关', '0006', '开关', '3', '1', 'dest', 'Z962G.jpg', 'Z962G一路开关输出和一路情景开关，可直接安装于86盒中，替换家居中的普通墙面开关，能够检测和统计当前负载电流、电压、功率、电能值。用户可采用ZigBee遥控器、手机、按键开关等多种方式控制该设备。也可以通过情景开关按键调用情景及模式。', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib` VALUES ('114', '0', 'Z962H', 'Z962H一路开关输出和两路情景开关', '0006', '开关', '3', '1', 'dest', 'Z962H.jpg', 'Z962H一路开关输出和两路情景开关，可直接安装于86盒中，替换家居中的普通墙面开关，能够检测和统计当前负载电流、电压、功率、电能值。用户可采用ZigBee遥控器、手机、按键开关等多种方式控制该设备。也可以通过情景开关按键调用情景及模式。', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib` VALUES ('115', '0', 'Z962I', 'Z962I两路开关输出和一路情景开关', '0006', '开关', '3', '1', 'dest', 'Z962I.jpg', 'Z962I两路开关输出和一路情景开关，可直接安装于86盒中，替换家居中的普通墙面开关，能够检测和统计当前负载电流、电压、功率、电能值。用户可采用ZigBee遥控器、手机、按键开关等多种方式控制该设备。也可以通过情景开关按键调用情景及模式。', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib` VALUES ('116', '7', 'Z801WLS', 'Z801WLS水浸报警器', '', '', '', '1', '', 'Z801WLS.jpg', 'Z801WLS水浸报警器', '5V直流电源或2节1.5V电池', '不需要激活', '按住绑定键的同时给设备上电，LED2闪烁20次，表示复位完成，之后重新上电，Z801WLS可以重新加网。 ', '无');
INSERT INTO `modenodelib` VALUES ('117', '7', 'ZB05A', 'ZB05A智能门锁', '0101', '开关锁', '3', '1', 'dest', 'ZB05A.jpg', 'ZB05A可对锁进行开、关控制，包括机械钥匙、密码、IC卡、指纹、远程控制等多种方式。用户可通过ZigBee无线网络随时查看和控制门锁状态，管理用户名和密码。智能化的门锁设计拥有可靠的密码保障和感应火灾性能，保障家居安全。', '8节AA1.5V电池', '不需要激活', '1.按住网络键的同时给设备上电，上电完请不要立即松开按键，等到网络灯开始快速闪烁，提示复位成功；2.重新上电，ZB05A可以开始重新加网。', '1、初次使用时，需要注册zigbee：在门打开的状态下，打开内侧用（主锁身）的电池盖和外盒，在右侧下端按“锁卡注册键”。然后再按下面的“ZB注册键”（靠上的按键）。按“*”键，可听到“哔呖呖”的设置音，设置结束。2、默认都为加密的方式，第一次使用时，需要在app中→设备管理模块→进入门锁的设置页面，设置新的用户和密码，才可以对锁进行开关控制。门锁默认有6个空用户，注册新用户前，需要先将这些用户删除。超级密码默认为：123456.usercode即用户名为1-254的数字。');
INSERT INTO `modenodelib` VALUES ('118', '4', 'Z801TX', 'Z801TX开关信号转换器', '0006', '开关', '3', '1', 'source', 'Z801TX.jpg', 'NETVOX的Z801TX在网络中作为终端设备（End device）使用,不允许其他设备做为其子设备。Z801TX可外接5个按键。按键触发时，Z801TX检测到信号，发送命令給绑定的设备控制其开关。', '无', '无', '无', '无');
INSERT INTO `modenodelib` VALUES ('119', '7', 'Z311J', 'Z311J无线窗磁感应器', '0006', '开关', '3', '1', 'source', 'Z311J.jpg', 'Z311J是门磁设备，当门或窗的状态变化时，能根据配置控制绑定设备的开/关动作；Z311J还作为安防系统中的1个检测设备，当门或窗被打开，它将发出报警信息给安防中心设备，当门或窗被关闭好，它会发送出状态恢复正常的信息给CIE。', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', 'Z311J的本体与门磁的磁铁部分别安装在门或窗户的缝隙的两边，当门或窗被打开，它将发出报警信息给安防中心设备( 即CIE )，当门或窗被关闭好，它会发送出状态恢复正常的信息给CIE。该设备也可以用于冰箱、抽屉等处，用于检测冰箱或抽屉等是否被打开。需要注意防水。');
INSERT INTO `modenodelib` VALUES ('120', '0', 'ZC07', 'ZC07无线可调光LED灯泡', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'ZC07.jpg', 'ZC07是可实现灯光亮度256级调光,并可接受遥控器无线控制的高可靠性LED灯炮,可以绑定设备、无线遥控或者软件操作等方式来实现开关和调光控制。ZC07为5W灯泡，采用恒流控制方式，输入电压可以从AC 100V到240V，100到240V输入可以达到相同的灯光效果。', '100～240V的交流电源', '不需要激活', '同时长按测试键和绑定键5s，5s时间到后指示灯闪10次后就恢复出厂设置成功，此时就可以重新加网。', '无');
INSERT INTO `modenodelib` VALUES ('121', '7', 'Z311W', 'Z311W水浸报警器', null, null, null, '1', '', 'Z311W.jpg', 'Z311W水浸报警器', '无需外加电源,产品使用内部的3V钮扣电池供电。', '同时按住绑定键和辅助键，看到红色指示灯灯闪烁2次，此时松开按键。', '同时按住绑定键和辅助键，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '无');
INSERT INTO `modenodelib` VALUES ('122', '6', 'Z811B', 'Z811B双路窗帘机控制器', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z811B.jpg', 'Z811B双路是窗帘控制器，可以通过自身的按键、绑定的设备或软件控制窗帘的开关和级别。', '100-240V AC 50/60HZ电源供电', '不需要激活', '方法一：按住“绑定键（logo旁的小按钮）”上电，指示灯快闪，表示恢复出厂设置完成，再次重新上电后即可。方法二：长按住“绑定键（logo旁的小按钮）”键的15秒待指示灯闪烁一次（此间指示灯3秒、10秒、15秒各闪烁一次），松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，自动重启就可以重新加网了。', 'Lin接火线，N线是零线接电机的零线（一般为蓝线）。EP1：Lout1接电机正转输入（一般为棕线)，Lout2接电机反转输入（一般为黑线）。EP2：Lout3接电机正转输入（一般为棕线)，Lout4接电机反转输入（一般为黑线）。在设备执行窗帘控制功能前,都应先进行“学习上下限”的学习，学习方法请详见设备说明书或在app的设备管理模块，该设备的设置页面进行学习。');
INSERT INTO `modenodelib` VALUES ('123', '0', 'Z825I', 'Z825I六路情景控制器', '', '', '', '1', '', 'Z825I.jpg', 'Z825I作为Scene Selector&Mode Selector，它通过6个触摸按键实现添加存储灯光场景来实现记忆6个场景的作用。并且能通过配置，选定与某个情景模式绑定，可实现在面板上对模式的控制，一路按键最多可配置4个情景控制。', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib` VALUES ('124', '0', 'Z825J', 'Z825J三路开关输出和三路情景开关', '0006', '开关', '3', '1', 'dest', 'Z825J.jpg', 'Z825J三路开关输出和三路情景开关，可直接安装于86盒中，替换家居中的普通墙面开关，能够检测和统计当前负载电流、电压、功率、电能值。用户可采用ZigBee遥控器、手机、按键开关等多种方式控制该设备。也可以通过情景开关按键调用情景及模式。', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib` VALUES ('125', '0', 'Z825Q', 'Z825Q三路本地开关控制器', '0006', '开关', '3', '1', 'dest', 'Z825Q.jpg', 'Z825Q是单火线取电的三路本地开关控制器，可与具有开关功能的设备绑定，通过已绑定设备来控制Z825Q开关，也可通过Z825Q设备本身自带的开关来控制，Z825Q具有三路控制开关,由三个开关控制。', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络青绿色指示灯闪烁一次（期间网络青绿色指示灯闪烁4次，其中刚按下闪一次，3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络青绿色指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会进入睡眠模式，短按绑定键Z825Q可重新加网。 ', 'Z825Q具有单路输入，三路输出的能力，接线时，将一种线（N或L，来自市电）接入输入口，然后在三路输出口引出同种类型的输出线（给设备供电），将插头接入到AC  100-240V的电源，给设备上电。');
INSERT INTO `modenodelib` VALUES ('126', '1', 'Z816I', 'Z816I中规电能检测墙面插座', '0006', '开关', '3', '1', 'dest', 'Z816I.jpg', 'Z816I是中规无线电流检测墙面插座，可直接安装于86盒中，替换家居中的普通墙面开关，设备本身自带按键开关，也可以通过绑定设备、软件等控制其开关。可通过软件查看负载的功率、电压、电流和电能情况。', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('127', '5', 'Z817D', 'Z817D吸顶动作感应开关', '0006', '开关', '3', '1', 'source', 'Z817D.jpg', 'Z817D设备具有红外探测开关控制功能，当检测到移动物体所产生的红外线，控制设备的开关。', 'AC100-240V 50/60HZ供电', '不需要激活', '给设备上电，同时按住“按键1”和“按键2”5秒；复位完成则led闪烁30次后自动重新请求加网；', '无');
INSERT INTO `modenodelib` VALUES ('128', '11', 'Z800R', 'Z800R带插座无线中继路由器', null, null, null, '1', null, 'Z800R.jpg', 'Z800R是带一个插座无线路由中继器,在网络中作为路由中继设备，允许其他设备做为其子设备。为其他设备间通信提供扩展通信距离的功能。', 'AC 100-250V的电源', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重新上电就可以加入新的网络了。', '无');
INSERT INTO `modenodelib` VALUES ('129', '1', 'Z809C', 'Z809C带插座无线中继路由器', null, null, null, '1', null, 'Z809C.jpg', 'Z809C是带一个插座无线路由中继器,在网络中作为路由中继设备，允许其他设备做为其子设备。为其他设备间通信提供扩展通信距离的功能。', 'AC 100-240V的电源或电池供电（内置电池）', '不需要激活', '长按绑定键15s，15s时间到状态闪1次后放开绑定键，两秒内短按绑定键就状态灯闪烁10次，并自动复位。其中长按绑定键3s，10s和15s的时候状态灯都会依次闪烁一次，以提示所按的时间长短。', '无');
INSERT INTO `modenodelib` VALUES ('130', '1', 'Z809D', 'Z809D带插座无线中继路由器', '', '', '', '1', '', 'Z809D.jpg', 'Z809D带一个插座无线中继路由器.在网络中作为路由中继设备，允许其他设备做为其子设备。为其他设备间通信提供扩展通信距离的功能。', 'AC 100-240V的电源或电池供电（内置电池）', '不需要激活', '长按绑定键15s，15s时间到指示闪1次后放开绑定键，两秒内短按绑定键就指示灯闪烁10次，并自动重启设备（如果仅仅用电池供电不能重启）。', '无');
INSERT INTO `modenodelib` VALUES ('131', '9', 'Z501AE3ED', 'Z501A四鍵遙控器(357)', '0006:0008', '开关:调级控制', '3', '1', 'source', 'Z501A.jpg', 'Z501A做为远程遥控器，它可与开关及级别可控的设备进行绑定，来控制其开关及级别值，同时设备还设有紧急按钮，在遇到紧急情况时按下紧急按钮通知家人以寻求帮助。', '3V CR2032纽扣电池', '右侧开关off断电，重新上电后即可激活设备。', '按住“开关键” 的同时给设备上电，开始恢复出厂值，指示灯共闪烁20次，之后重新操作组合键就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('132', '9', 'Z501BE3ED', 'Z501B四鍵遙控器(357)', '0006', '开关', '3', '1', 'source', 'Z501B.jpg', 'Z501B做为远程遥控器，它可与开关可控的设备进行绑定，来控制其他设备的开关。', '3V CR2032纽扣电池', '右侧开关off断电，重新上电后即可激活设备。', '按住“ON2键” 的同时给设备上电，开始恢复出厂值，指示灯共闪烁20次，之后重新操作组合键就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('133', '9', 'Z501CE3ED', 'Z501C四鍵遙控器(357)', '0006:0008', '开关:调级控制', '3', '1', 'source', 'Z501C.jpg', 'Z501C可与具有开关功能/级别控制功能的设备绑定控制其开关及级别值。', '3V CR2033纽扣电池', '右侧开关off断电，重新上电后即可激活设备。', '按住“ ON/OFF1键” 的同时给设备上电，开始恢复出厂值，指示灯共闪烁20次，之后重新操作组合键就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('134', '7', 'ZB02I', 'ZB02I无线墙面紧急按键', '', '', '', '1', '', 'ZB02E.jpg', 'ZB02I是紧急报警触发设备，，它作为安防系统中的1个检测设备，可贴于墙上或放置与屋内任何位置。当人们遇到危险需要紧急救助的时候，按下ZB02I的报警键，ZB02I立即向安防中心发送出报警信息，安防中心会使警报器发出声音和灯光提醒，家里人听到或者看到报警信号能立即提供帮助。', 'AAA电池供电', '短按绑定键，若设备仍在网络状态，则绿色指示灯闪烁5次，表示激活成功。', '按住绑定键的同时给设备上电，进行恢复出厂设置，看到指示灯闪烁，表示恢复完成。', '无');
INSERT INTO `modenodelib` VALUES ('137', '0', 'Z802', 'Z802 Zigbee 两用双路开关模块', '0006', '开关', '3', '1', 'dest', 'Z802.jpg', 'Z802可与具有开关功能的设备绑定控制其开关。用戶可以通過機械开关来控制负载的通斷，也可通过無線方式控制负载的通斷。同時可通過上位機軟件（ZiG-BUTLERr）查看設備兩路的當前通斷狀態，并可做出相應的控制', '100-240VAC 50/60HZ 电源供电', '不需要激活', '按住绑定键的同时，给设备上电，开始恢复出厂值，直到LED1指示灯开始闪烁，表示恢复完成。之后重新上电，Z802可以重新加网了', '无');
INSERT INTO `modenodelib` VALUES ('138', '4', 'ZB02J', 'ZB02J三路情景开关', '', '', '', '1', '', 'ZB02C.jpg', 'ZB02J三路情景开关', '无需外加电源,产品使用内部的2节7号电池供电', '短按绑定键（led网络灯闪5下）', '按住绑定键不松开，装入电池，进行恢复出厂设置，看到状态灯闪烁，表示恢复完成，然后松开按键，拿出电池', '无');
INSERT INTO `modenodelib` VALUES ('139', '0', 'Z815A', 'Z815A单路电能检测墙面开关', '0006', '开关', '3', '1', 'dest', 'Z815I.jpg', 'Z815A是一路输出的开关设备，可直接安装于86盒中，替换家居中的普通墙面开关，能够检测和统计当前负载电流、电压、功率、电能值。用户可采用ZigBee遥控器、手机、按键开关等多种方式控制该设备。', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('140', '0', 'Z815B', 'Z815B双路电能检测墙面开关', '0006', '开关', '3', '1', 'dest', 'Z815J.jpg', 'Z815B有两路输出的开关设备，可直接安装于86盒中，替换家居中的普通墙面开关，能够检测和统计当前负载电流、电压、功率、电能值。用户可采用ZigBee遥控器、手机、按键开关等多种方式控制该设备。', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('141', '0', 'Z815C', 'Z815C三路电能检测墙面开关', '0006', '开关', '3', '1', 'dest', 'Z815K.jpg', 'Z815C有三路输出的开关设备，可直接安装于86盒中，替换家居中的普通墙面开关，能够检测和统计当前负载电流、电压、功率、电能值。用户可采用ZigBee遥控器、手机、按键开关等多种方式控制该设备。', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('142', '0', 'Z815D', 'Z815D单路电能检测墙面调光开关', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z815L.jpg', 'Z815D是有一路输出的调光设备，能实现开关和调光控制，可直接安装于86盒中，替换家居中的普通墙面开关，能够检测和统计当前负载电流、电压、功率、电能值。用户可采用ZigBee遥控器、手机、按键开关等多种方式控制该设备。', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('143', '0', 'Z815E', 'Z815E双路电能检测墙面调光开关', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z815M.jpg', 'Z815E是有两路输出的调光设备，能实现开关和调光控制，可直接安装于86盒中，替换家居中的普通墙面开关，能够检测和统计当前负载电流、电压、功率、电能值。用户可采用ZigBee遥控器、手机、按键开关等多种方式控制该设备。', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('144', '0', 'Z826C', 'Z826C三路本地开关控制器', '0006', '开关', '3', '1', 'dest', 'Z825Q.jpg', 'Z826C是单火线取电的三路本地开关控制器，可与具有开关功能的设备绑定，通过已绑定设备来控制Z826C开关，也可通过Z826C设备本身自带的开关来控制，Z826C具有三路控制开关,由三个开关控制。', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络青绿色指示灯闪烁一次（期间网络青绿色指示灯闪烁4次，其中刚按下闪一次，3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络青绿色指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会进入睡眠模式，短按绑定键Z826C可重新加网。 ', 'Z826C具有单路输入，三路输出的能力，接线时，将一种线（N或L，来自市电）接入输入口，然后在三路输出口引出同种类型的输出线（给设备供电），将插头接入到AC  100-240V的电源，给设备上电。');
INSERT INTO `modenodelib` VALUES ('145', '11', 'Z206', 'Z206云智能网关', '', '', '', '1', '', 'Z206.jpg', 'Z206作为奈伯思智能家居系统的核心网关，率先实现云端技术、WiFi移动联网技术与ZigBee智能家居物联网术的完美结合，让用户只需要通过手机APP连接Wifi即可控制家里的灯光、窗帘、各种电器等设备的开关，出门在外通过2G/3G网络可监控家中的一切变化。', '使用DC 12V  1.5A电源适配器，接入100-220V的电源', '不需要激活', '详见206说明书', 'Z206作为奈伯思智能家居系统的核心网关，率先实现云端技术、WiFi移动联网技术与ZigBee智能家居物联网术的完美结合，让用户只需要通过手机APP连接Wifi即可控制家里的灯光、窗帘、各种电器等设备的开关，出门在外通过2G/3G网络可监控家中的一切变化。');
INSERT INTO `modenodelib` VALUES ('146', '9', 'Z501G', 'Z501G遙控器(357)', '0501', '安防控制', '3', '1', 'source', 'Z501A.jpg', 'Z501G做为远程遥控器，它可用来辅助控制安防系统，同时设备还设有紧急按钮，在遇到紧急情况时按下紧急按钮通知家人以寻求帮助。', '3V CR2032纽扣电池', '右侧开关off断电，重新上电后即可激活设备。', '按住“开关键” 的同时给设备上电，开始恢复出厂值，指示灯共闪烁20次，之后重新操作组合键就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('147', '7', 'ZA06', 'ZA06空气蛋', '', '', '3', '1', 'dest', 'ZA06.jpg', 'ZA06具有检测温湿度、PM2.5、甲醛、CO以及一些有害的有机气体。并能定时采集温湿度、空气质量以及甲醛数据，发送给登记的CIE设备，提示报警。ZA06通过USB供电，短按触摸键可以控制三色灯是否显示当前PM2.5值，设备通过检测到的PM2.5的值，在三色灯上面显示，绿色、黄色、橙色、红色、紫色、褐红色分别代表PM2.5等级，绿色空气质量好、其次按顺序递减，褐红色蓝色最差。', '5V直流电源', '不需要激活', '长按绑定键15s（网络灯闪烁3次以后，设备3s闪烁一次，10s闪烁一次，15s闪烁一次），设备开始恢复出场设置，直到网络灯一直闪烁20次（20，100，100），设备自动复位后，重新请求加网。', '无');
INSERT INTO `modenodelib` VALUES ('148', '6', 'Z-D01C', 'Z-D01C无线帘幕控制器（卷帘）', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z-D01BCD.jpg', 'Z-D01C是窗帘控制器，可以通过自身的按键、绑定的设备或软件控制窗帘的开关和级别。', '12V4A直流电源', '不需要激活', '按住ON1和OFF1键（靠右边一列）的同时给设备上电，开始执行恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重新上电就可以重新加网了', '在设备执行窗帘控制功能前,都应先进行“学习上下限”的学习，学习方法请详见设备说明书或在app的设备管理模块，该设备的设置页面进行学习');
INSERT INTO `modenodelib` VALUES ('149', '6', 'Z-D01D', 'Z-D01D无线帘幕控制器（百叶帘）', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z-D01BCD.jpg', 'Z-D01D是窗帘控制器，可以通过自身的按键、绑定的设备或软件控制窗帘的开关和级别。', '12V4A直流电源', '不需要激活', '按住ON1和OFF1键（靠右边一列）的同时给设备上电，开始执行恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重新上电就可以重新加网了', '在设备执行窗帘控制功能前,都应先进行“学习上下限”的学习，学习方法请详见设备说明书或在app的设备管理模块，该设备的设置页面进行学习');
INSERT INTO `modenodelib` VALUES ('150', '0', 'Z-825F', 'Z-825F无线可编程情景触摸开关（单键)', '0006', '开关', '3', '1', 'dest', 'Z825F.jpg', 'Z825F作为情景模式控制器，它拥有1个触摸按键。最多可添加1个情景，或配置4个模式。 ', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib` VALUES ('151', '0', 'Z-825G', 'Z-825G无线可编程情景触摸开关（双键)', '0006', '开关', '3', '1', 'dest', 'Z825G.jpg', 'Z825G作为情景模式控制器，它拥有2个触摸按键。每个按键均可以配置添加情景，或者配置控制模式（每个按键最多添加1个情景，或配置4个模式）。 ', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib` VALUES ('152', '0', 'Z-825H', 'Z-825H无线可编程情景触摸开关（三键)', '0006', '开关', '3', '1', 'dest', 'Z825H.jpg', 'Z825H作为情景模式控制器，它拥有3个触摸按键。每个按键均可以配置添加情景，或者配置控制模式（每个按键最多添加1个情景，或配置4个模式）。 ', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib` VALUES ('153', '0', 'Z-826A', 'Z-826A无线触摸开关（单路+单火线取电）', '0006', '开关', '3', '1', 'dest', 'Z826A.jpg', 'Z826A是单火线取电的单路无线触摸开关，可与具有开关功能的设备绑定，通过已绑定设备来控制Z826A开关，也可通过Z826A设备本身自带的开关来控制', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络青绿色指示灯闪烁一次（期间网络青绿色指示灯闪烁4次，其中刚按下闪一次，3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络青绿色指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会进入睡眠模式，短按绑定键Z826C可重新加网。 ', '无');
INSERT INTO `modenodelib` VALUES ('154', '11', 'Z-L01D', 'Z-L01D工业级IEEE802.15.4/GPIO 适配器', '0006', '开关', '3', '1', 'dest', 'ZL01D.JPG', 'Z-L01D 是GPIO协调器，配合GPIO端口驱动，主设备可以通过该端口 与ZigBee其他模块进行通信，进而对整个系统进行操作。', 'GPIO口', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');
INSERT INTO `modenodelib` VALUES ('155', '0', 'Z-826B', 'Z-826B无线触摸开关（双路+单火线取电）', '0006', '开关', '3', '1', 'dest', 'Z826B.jpg', 'Z826B是单火线取电的单路无线触摸开关，可与具有开关功能的设备绑定，通过已绑定设备来控制Z826B开关，也可通过Z826B设备本身自带的开关来控制', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络青绿色指示灯闪烁一次（期间网络青绿色指示灯闪烁4次，其中刚按下闪一次，3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络青绿色指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会进入睡眠模式，短按绑定键Z826C可重新加网。 ', '无');
INSERT INTO `modenodelib` VALUES ('156', '0', 'Z-826D', 'Z-826D无线调光触摸开关（单路+单火线取电)', '0006', '开关', '3', '1', 'dest', 'Z826D.jpg', 'Z826D是单火线取电的单路调光触摸开关，可与具有开关功能的设备绑定，通过已绑定设备来控制Z826D开关，也可通过Z826D设备本身自带的开关来控制', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络青绿色指示灯闪烁一次（期间网络青绿色指示灯闪烁4次，其中刚按下闪一次，3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络青绿色指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会进入睡眠模式，短按绑定键Z826C可重新加网。', '无');
INSERT INTO `modenodelib` VALUES ('157', '11', 'Z-202E', 'Z-202EIEEE802.15.4/TCP IP 适配器', '0006', '开关', '3', '1', 'dest', 'Z202E.JPG', 'Z202E 是TCP ip协调器，配合网口驱动，主设备可以通过该网口 与ZigBee其他模块进行通信，进而对整个系统进行操作。', '网口', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');
INSERT INTO `modenodelib` VALUES ('158', '11', 'Z-L01A', 'Z-L01A工业级IEEE802.15.4/RS-232 适配器', '0006', '开关', '3', '1', 'dest', 'ZL01A.JPG', 'Z-L01A 是RS协调器，配合RS-232端口驱动，主设备可以通过该端口 与ZigBee其他模块进行通信，进而对整个系统进行操作。', 'RS-232端口', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');
INSERT INTO `modenodelib` VALUES ('159', '11', 'Z-L01B', 'Z-L01B工业级IEEE802.15.4/RS-485适配器', '0006', '开关', '3', '1', 'dest', 'ZL01B.JPG', 'Z-L01B 是RS协调器，配合RS-485端口驱动，主设备可以通过该端口 与ZigBee其他模块进行通信，进而对整个系统进行操作。', 'RS-485端口', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');
INSERT INTO `modenodelib` VALUES ('160', '11', 'Z-L01F', 'Z-L01F工业级IEEE802.15.4/RS-232 透传适配器', '0006', '开关', '3', '1', 'dest', 'ZL01F.jpg', 'Z-L01F 是RS协调器，配合RS-232端口驱动，主设备可以通过该端口 与ZigBee其他模块进行通信，进而对整个系统进行操作。', 'RS-232端口', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');
INSERT INTO `modenodelib` VALUES ('161', '11', 'Z-L01C', 'Z-L01C工业级IEEE802.15.4/TCP IP 适配器', '0006', '开关', '3', '1', 'dest', 'ZL01C.JPG', 'Z-L01C 是TCP ip协调器，配合网口驱动，主设备可以通过该网口 与ZigBee其他模块进行通信，进而对整个系统进行操作。', '网口', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');
INSERT INTO `modenodelib` VALUES ('162', '11', 'Z-L01E', 'Z-L01E工业级IEEE802.15.4/USB适配器', '0006', '开关', '3', '1', 'dest', 'ZL01E.JPG', 'Z-L01E 是USB协调器，配合USB的串口驱动，主设备可以通过该串口 与ZigBee其他模块进行通信，进而对整个系统进行操作。', 'USB', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');

-- ----------------------------
-- Table structure for modenodelib_en
-- ----------------------------
DROP TABLE IF EXISTS `modenodelib_en`;
CREATE TABLE `modenodelib_en` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nodeType` varchar(100) DEFAULT NULL,
  `modelId` varchar(50) DEFAULT NULL,
  `deviceName` varchar(200) DEFAULT NULL,
  `clusterId` varchar(200) DEFAULT NULL,
  `clusterName` varchar(100) DEFAULT NULL,
  `destType` varchar(4) DEFAULT NULL,
  `source_id` bigint(20) DEFAULT '1',
  `deviceType` varchar(20) DEFAULT NULL,
  `picName` varchar(100) DEFAULT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `powerType` varchar(200) DEFAULT NULL,
  `activationMethod` varchar(2000) DEFAULT NULL,
  `resetDefault` varchar(2000) DEFAULT NULL,
  `remark` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `nodeType` (`nodeType`) USING BTREE,
  KEY `modelId` (`modelId`) USING BTREE,
  KEY `deviceName` (`deviceName`) USING BTREE,
  KEY `clusterId` (`clusterId`) USING BTREE,
  KEY `destType` (`destType`) USING BTREE,
  KEY `deviceType` (`deviceType`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=163 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of modenodelib_en
-- ----------------------------
INSERT INTO `modenodelib_en` VALUES ('2', '0', 'Z815I', 'Z815I Wall Switch with Power Meter (1-Gang)', '0006', '开关', '3', '1', 'dest', 'Z815I.jpg', 'Z815I is a one-gang Output Switch and its enclosure is designed to replace conventional junction box installation wall switches.  It is equipped with load consumption inspection and load control feature. Users can use ZigBee enabled remote controller and on/off device and smart phone to control Z815I. ', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('3', '0', 'Z815K', 'Z815K Wall Switch with Power Meter (3-Gang)', '0006', '开关', '3', '1', 'dest', 'Z815K.jpg', 'Z815K a three-gang Output Switch and its enclosure is designed to replace conventional junction box installation wall switches.  It is equipped with load consumption inspection and load control feature. Users can use ZigBee enabled remote controller and on/off device and smart phone to control Z815K. ', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('4', '0', 'Z806', 'Z806 Switch Control Unit (2-Output)', '0006', '开关', '3', '1', 'dest', 'Z806.jpg', '\r\nZ806 is a wireless switch relay with two outputs. Users can locally and remotely control it via external switch, paired ZigBee enabled devices and software.', '100～240V的交流电源', '不需要激活', '按住绑定键的同时给设备上电，开始恢复出厂设置，状态指示灯一直闪烁表示擦除完成。之后重新上电，Z806可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('5', '4', 'ZB02A', 'ZB02A Wall Switch (1-Key)', '0006', '开关', '3', '1', 'source', 'ZB02A.jpg', 'ZB02A is a single gang wall switch which can be hung on the wall. Paired with the other On/Off devices, it can be remotely controlled by toggling.', '2节AAA1.5V电池', '拆下面板的上盖，短按绑定键（红色按键），LED网络灯闪5下激活设备。', '拆下面板的上盖，按住绑定键的同时给设备上电，进行恢复出厂设置，看到状态指示灯闪烁，表示恢复完成', '无');
INSERT INTO `modenodelib_en` VALUES ('7', '9', 'Z503', 'Z503 Local Commander', '0006:0008', '开关:调级控制', '1', '1', 'source', 'Z503.jpg', 'Z503 is a multifunctional remote which can turn on/off all devices, dimming, control power socket, emergency button function, arm and disarm security system.', '3V CR2034纽扣电池', '按住“2nd”键和“3”键可激活设备。', '1.同时按住扩展情景键和第二功能按键，给设备上电；2. 如指示灯快速闪烁10次表示恢复出厂值完成，设备自动重启后即可重新加网；3. 如指示灯慢闪3次，表示恢复出厂值失败，请重复步骤1。', '无');
INSERT INTO `modenodelib_en` VALUES ('10', '0', 'Z805A', 'Z805A Switch Control Unit with Power Meter (1-Output)', '0006', '开关', '3', '1', 'dest', 'Z805A.jpg', 'Z805A is a wireless switch relay with one output 16A/250V AC. Users can control it via external switch, paired ZigBee enabled devices and software. Via ZigButler, users can check the real-time current/voltage status and kilo-watt-hour consumption of the attached appliance. ', '100～240V的交流电源', '不需要激活', '长按绑定键15S(指示灯闪烁2次,3S闪烁一次，10S闪烁一次），状态灯开始快闪20秒，设备进行恢复出厂设置；20s内短按任意键，Z805B重新请求加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('11', '0', 'Z805B', 'Z805B Switch Control Unit with Power Meter (1-Output)', '0006', '开关', '3', '1', 'dest', 'Z805B.jpg', 'Z805B is wireless switch relay with one output 16A/250V AC. Users can remotely control it via external switch, paired ZigBee enabled devices and softwareVia ZigButler, users can check the real-time current/voltage status and kilo-watt-hour consumption of the attached appliance. 。', '100～240V的交流电源', '不需要激活', '长按绑定键15S(指示灯闪烁2次,3S闪烁一次，10S闪烁一次），状态灯开始快闪20秒，设备进行恢复出厂设置；20s内短按任意键，Z805B重新请求加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('12', '0', 'Z810B', 'Z810B Switch Control Unit with Power Monitoring LCD', '0006', '开关', '3', '1', 'dest', 'Z810B.jpg', 'Z810B is a switch control, power supply is 100-240VAC 50/60HZ. It can be controlled by its manual override switch or by a paired wireless switch or by software. Users can use software and its LCD to check the real-time current/voltage status and kilo-watt-hour consumption of the attached appliance.', '100～240V的交流电源', '不需要激活', '按下绑定键15秒后（图标每隔5秒闪烁一下，闪烁3次），2秒内短按功能键，LCD数值区显示表示恢复完成，之后设备将自动重启。', '无');
INSERT INTO `modenodelib_en` VALUES ('13', '0', 'Z811', 'Z811 Switch Control Unit (4-Output)', '0006', '开关', '3', '1', 'dest', 'Z811.jpg', 'Z811 is a wireless on/off output switch device. Users can control it via paired ZigBee enabled devices and software.', '100～240V的交流电源', '不需要激活', '长按绑定键15秒（状态指示灯3S闪一次，10S闪一次,15S闪一次），然后短按，状态指示灯一直闪烁表示擦除完成。之后指示灯灭掉，指示Z811便可以重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('14', '0', 'Z815J', 'Z815J Wall Switch with Power Meter (2-Gang)', '0006', '开关', '3', '1', 'dest', 'Z815J.jpg', 'Z815J is a two-gang switch and is designed to replace conventional junction box installation wall switches.  It is equipped with load consumption inspection and load control feature.  Users can use ZigBee enabled remote controller and on/off device and smart phone to control Z815J.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('15', '0', 'Z815L', 'Z815L Wall Dimmer Switch with Power Meter (1-Gang)', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z815L.jpg', 'Z815L is a one-gang dimmable switch and is designed to replace conventional junction box installation wall switches.  It is equipped with load consumption inspection and load control feature. Users can use ZigBee enabled remote controller and on/off device and smart phone to control Z815L.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('16', '0', 'Z815M', 'Z815M Wall Dimmer Switch with Power Meter (2-Gang)', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z815M.jpg', 'Z815M is a two-gang dimmable switch and is designed to replace conventional junction box installation wall switches.  It is equipped with load consumption inspection and load control feature. Users can use ZigBee enabled remote controller and on/off device and smart phone to control Z815M.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('17', '0', 'Z817A', 'Z817A Ceiling 16A Relay Switch with Power Meter', '0006', '开关', '3', '1', 'dest', 'Z817A.jpg', 'Z817A is a ceiling mount switch unit. It can do on/off switch and dim the lights through its buttoms, paired ZigBee enabled remote controllers or even software. Energy consumption reading is able to be monitored with software. ', '100～240V的交流电源', '不需要激活', '按住绑定键（正对logo靠左）15秒，设备网络灯闪烁一次，然后在2秒内短按任意键，设备网络灯快闪10次后重启。', '无');
INSERT INTO `modenodelib_en` VALUES ('18', '0', 'Z817B', 'Z817B Ceiling 16A Relay Dimmer with Power Meter', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z817B.jpg', 'Z817B is a ceiling mount dimmable unit. It can do on/off switch and dim the lights through its buttoms, paired ZigBee enabled remote controllers or even software. Energy consumption reading is also able to be monitored with software. ', '100～240V的交流电源', '不需要激活', '按住绑定键（正对logo靠左）15秒，设备网络灯闪烁一次，然后在2秒内短按任意键，设备网络灯快闪10次后重启。', '无');
INSERT INTO `modenodelib_en` VALUES ('19', '0', 'Z817C', 'Z817C Ceiling Motion Detector with On/Off Switch', '0006', '开关', '3', '1', 'dest', 'Z817C.jpg', 'Z825C is a three-gang touch switch and is designed to replace conventional junction box installation wall switches. It is equipped with load consumption inspection and load control feature. Users can use ZigBee enabled remote controller and on/off device and smart phone to control Z825C.', '100～240V的交流电源', '不需要激活', '按住绑定键（正对logo靠左）15秒，设备网络灯闪烁一次，然后在2秒内短按任意键，设备网络灯快闪10次后重启。', '无');
INSERT INTO `modenodelib_en` VALUES ('20', '0', 'Z825C', 'Z825C Touch Panel Switch with Power Meter (3-Gang)', '0006', '开关', '3', '1', 'dest', 'Z825C.jpg', 'Z825C is a three-gang touch switch and is designed to replace conventional junction box installation wall switches. It is equipped with load consumption inspection and load control feature. Users can use ZigBee enabled remote controller and on/off device and smart phone to control Z825C.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('21', '0', 'Z825E', 'Z825E Touch Panel Dimmer Switch with Power Meter (2-Gang)', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z825E.jpg', 'Z825E is a twp-gang touch switch and is designed to replace conventional junction box installation wall switches. It is equipped with load consumption inspection and load control feature. Users can use ZigBee enabled remote controller and on/off device and smart phone to control Z825E.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('22', '0', 'ZC06', 'ZC06 Dimmable LED Tube (120cm)', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'ZC06.jpg', 'ZC06 is a robust ZigBee enabled dimmable LED tube. It has 256 light levels function. It is a long life and light weighted power consumption which conserves 60% comparing to the conventional light tubes. ZC06 utilizes SMT LEDs with its 400 lumen/1.5 meters and 50,000-hour lamp life. ZC06 can be controlled wirelessly via remote controller. It supports AC 100V to 240V with the same controlling features.', '100～240V的交流电源', '不需要激活', '同时长按测试键和绑定键5s，5s时间到后指示灯闪10次后就恢复出厂设置成功，此时就可以重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('23', '1', 'Z800', 'Z800 Power Plug with Power Meter', '0006', '开关', '3', '1', 'dest', 'Z800B.jpg', 'Z800 is a power socket with power consumption monitoring that allows off site remote control. Users can control it by its bottom and by paired ZigBee enabled devices and software. Energy consumption reading is able to be monitored with software.', '100～240V的交流电源', '不需要激活', '长按绑定键15S，状态灯（红灯）快闪20S；设备进入恢复出厂设置；再按任意键重新请求加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('24', '1', 'Z803', 'Z803 Power Strip with Power Monitoring LCD & USB Port', '0006', '开关', '3', '1', 'dest', 'Z803.jpg', 'Z803 is a 4-gang power strip with power consumption display. It has USB jacks that facilitate USB charging purpose and built-in LCD. Users could also wirelessly control the switch through its own bottom, paired devices or software. LCD can display total power consumption of all sockets. Via the built-in LCD or application, users are able to check the consuming current/ voltage/ power/ energy.', '100～240V的交流电源', '不需要激活', '同时按下Match Key 和Bind Key 按键5秒后，！图标闪烁一下，代表长按了5秒。放开按键后，进行出厂设置，设备自动重新上电。', '无');
INSERT INTO `modenodelib_en` VALUES ('25', '1', 'Z808A', 'Z808A Power Plug with Power Monitoring LCD & USB Port', '0006', '开关', '3', '1', 'dest', 'Z808A.jpg', 'Z808A is a wireless smart plug. It has USB jacks that facilitate USB charging purpose and built-in LCD. Users could also wirelessly control the switch through its own bottom, paired devices or software. LCD can display total power consumption of all sockets. Via the built-in LCD or application, users are able to check the consuming current/ voltage/ power/ energy.', '100～240V的交流电源', '不需要激活', '长按绑定键（右边按键）15s（期间网络指示灯闪3次）后释放绑定键，并在2s内短按功能键（左边按键），即开始恢复出厂设置，网络灯快速闪烁十次后自动重启设备，开始寻找新的网络。', '无');
INSERT INTO `modenodelib_en` VALUES ('26', '1', 'Z808B', 'Z808B Dimmable Power Plug with Power Monitoring', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z808B.jpg', 'Z808B is a wireless dimmable smart plug. It has USB jacks that facilitate USB charging purpose and built-in LCD. Users could also wirelessly control the switch through its own bottom, paired devices or software. LCD can display total power consumption of all sockets. Via the built-in LCD or application, users are able to check the consuming current/ voltage/ power/ energy.', '100～240V的交流电源', '不需要激活', '长按绑定键（右边按键）15s（期间网络指示灯闪3次）后释放绑定键，并在2s内短按功能键（左边按键），即开始恢复出厂设置，网络灯快速闪烁十次后自动重启设备，开始寻找新的网络。', '无');
INSERT INTO `modenodelib_en` VALUES ('27', '1', 'Z809A', 'Z809A Power Plug with Power Meter', '0006', '开关', '3', '1', 'dest', 'Z809A.jpg', 'Z809A is a smart plug that allows off site remote control. Users can manually switch on/off the socket or by software to control it. Via software, users are able to monitor the load current, voltage and power and kilo-watt-hour consumption of the attached appliance.', '100～240V的交流电源', '不需要激活', '　长按绑定键（侧面）15s（期间网络指示灯闪3次）后释放绑定键，并在2s内短按测试键（正面按键），即开始恢复出厂设置，网络灯快速闪烁十次后自动重启设备，开始寻找新的网络。	无', '无');
INSERT INTO `modenodelib_en` VALUES ('28', '1', 'Z809B', 'Z809B Dimmable Power Plug with Power Meter', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z809B.jpg', 'Z809B is a dimmable smart plug that allows off site remote control. Users can manually switch on/off the socket or by paired derives or by software to control and to dim. Via software, users are able to monitor the load current, voltage and power and kilo-watt-hour consumption of the attached appliance. ', '100～240V的交流电源', '不需要激活', '长按绑定键（侧面）15s（期间网络指示灯闪3次）后释放绑定键，并在2s内短按测试键（正面按键），即开始恢复出厂设置，网络灯快速闪烁十次后自动重启设备，开始寻找新的网络。', '无');
INSERT INTO `modenodelib_en` VALUES ('29', '1', 'Z816B', 'Z816B US Type Wall Socket with Power Meter', '0006', '开关', '3', '1', 'dest', 'Z816B.jpg', 'Z816B is a US type wireless smart wall socket that allows off site remote control. Users can manually switch on/off the socket or by paired derives or by software to control and to dim. Via software, users are able to monitor the load current, voltage and power and kilo-watt-hour consumption of the attached appliance.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('30', '1', 'Z816G', 'Z816G UK Type Wall Socket with Power Meter', '0006', '开关', '3', '1', 'dest', 'Z816G.jpg', 'Z816G is a UK type wireless wall socket, it can directly install in 86 junction box and replace the traditional wall socket. It allows off site remote control. Users can manually switch on/off the socket or by paired derives or by software to control and to dim. Via software, users are able to monitor the load current, voltage and power and kilo-watt-hour consumption of the attached appliance.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('31', '1', 'Z816H', 'Z816H China Type Wall Socket with Power Meter', '0006', '开关', '3', '1', 'dest', 'Z816HI.jpg', 'Z816H is a China type wireless smart wall socket, it can directly install in 86 junction box and replace the traditional wall socket. It allows off site remote control. Users can manually switch on/off the socket or by paired derives or by software to control and to dim. Via software, users are able to monitor the load current, voltage and power and kilo-watt-hour consumption of the attached appliance.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('32', '2', 'Z711', 'Z711 Temperature and Humidity Sensor (Indoor)', null, null, null, '1', null, 'Z711.jpg', 'Z711 is a humidity and temperature sensor. It is used for collecting ambient H/T data and sending them to the display. ', '2节1.5V电池', '按下按键可激活设备', '1.  按住绑定键的同时给设备上电；2.  复位完成则led闪烁30次后停止；3.  重新上电，Z711可以开始重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('33', '2', 'Z712', 'Z712 Temperature and Humidity Sensor (Outdoor)', '', '', '', '1', '', 'Z712.jpg', 'Z712 is used to detect the outdoor humidity and temperature. It also increases a splash proof housing for protection and transmits the collecting data to a displayed device through wireless network.', '2节1.5V电池', '按下按键可激活设备', '1.  按住绑定键的同时给设备上电；2.  复位完成则led闪烁30次后停止；3.  重新上电，Z711可以开始重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('34', '2', 'Z713', 'Z713 Temperature and Humidity Sensor (Outdoor)', '', '', '', '1', '', 'Z713.jpg', ' Z713 is a detector for humidity, temperature and UV rays with a solar panel charger.  It\'s also equiped with a waterproof housing for protection and used for collecting ambient H/T data and UV intensity, then sending the data to the display.  ', '2节1.5V电池', '按下按键可激活设备', '1.  按住绑定键的同时给设备上电；2.  复位完成则led闪烁30次后停止；3.  重新上电，Z711可以开始重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('35', '2', 'Z716A', 'Z716A Temperature and Humidity Sensor with LCD (Indoor)', '', '', '', '1', '', 'Z716A.jpg', 'Z716A is a humidity and temperature detector which is used for collecting ambient H/T data and display on the LCD.  It can also send the data to the displaying device. ', '2节1.5V电池', '按下右边按键可激活设备', '设备上电后，同时按住“按键1”和“按键2” 5秒，开始恢复出厂值，LCD上显示所有图标画面，表示恢复出厂值完成，设备自动复位寻找加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('36', '2', 'Z716B', 'Z716B Temperature Sensor with LCD (Indoor)', null, null, null, '1', null, 'Z716B.jpg', 'Z716B is a temperature detector which is used for collecting ambient temperature data and display on the LCD.  It can also send the data to the displaying device. ', '2节1.5V电池', '按下右边按键可激活设备', '设备上电后，同时按住“按键1”和“按键2” 5秒，开始恢复出厂值，LCD上显示所有图标画面，表示恢复出厂值完成，设备自动复位寻找加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('37', '3', 'Z302B', 'Z302B On/Off Light Switch', '0006', '开关', '3', '1', 'source', 'Z302B.jpg', 'Z302B is an automatic light switch controller, which can bind with the output switches. When the brightness is lower than a certain level, Z302B will transmit \"ON\" command and control the binding device. When the brightness enhance to a certain level, Z302B will transmit \"OFF\" command to the binding device. ', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('38', '3', 'Z302H', 'Z302H Light Sensor Dimmer Switch', '0006:0008', '开关:调级控制', '3', '1', 'source', 'Z302H.jpg', 'Z302H is a light dimmer which can sense the intensity of the ambient lighting and determine whether it need to change the brightness of its binding lamp. Thus the controlled lamp/light will maintain the certain brightness within the area. ', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('39', '3', 'Z302G', 'Z302G Light Sensor', null, null, null, '1', null, 'Z302G.jpg', 'The Z302G is an automatic detector and reportor for environment illumination.  It can regularly transmit ambient illumination to a device which can receive and display the ambient illumination.  ', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('40', '3', 'Z311B', 'Z311B On/Off Light Switch', '0006', '开关', '3', '1', 'source', 'Z311B.jpg', 'Z311B is an automatic light switch controller, which can bind with the output switches. When the brightness is lower than a certain level, Z311B will transmit \"ON\" command and control the binding device. When the brightness enhance to a certain level, Z311B will transmit \"OFF\" command to the binding device.  ', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '同时按下绑定键和辅助键，两键同时按着5s，直到看到红色指示灯开始快闪，再松开按键。红色指示灯快闪20次后，设备复位将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('41', '3', 'Z311H', 'Z311H Light Sensor Dimmer Switch', '0006:0008', '开关:调级控制', '3', '1', 'source', 'Z311H.jpg', 'Z311H is a light dimmer which can sense the intensity of the ambient lighting and determine whether it need to change the brightness of its binding lamp. Thus the controlled lamp/light will maintain the certain brightness within the area. ', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '同时按下绑定键和辅助键，两键同时按着5s，直到看到红色指示灯开始快闪，再松开按键。红色指示灯快闪20次后，设备复位将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('42', '3', 'Z311G', 'Z311G Light Sensor', '', '', '', '1', '', 'Z311G.jpg', 'The Z311G is an automatic detector and reportor for environment illumination.  It can regularly transmit ambient illumination to a device which can receive and display the ambient illumination. ', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '同时按下绑定键和辅助键，两键同时按着5s，直到看到红色指示灯开始快闪，再松开按键。红色指示灯快闪20次后，设备复位将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('44', '4', 'ZB02B', 'ZB02B Wall Switch (2-Key)', '0006', '开关', '3', '1', 'source', 'ZB02B.jpg', 'ZB02B is a two keys wireless wall-mounted switch. It can be bound with a device which has switch control function and control the switch wirelessly. ', '2节AAA1.5V电池', '拆下面板的上盖，短按绑定键（红色按键），LED网络灯闪5下激活设备。', '拆下面板的上盖，按住绑定键的同时给设备上电，进行恢复出厂设置，看到状态指示灯闪烁，表示恢复完成', '无');
INSERT INTO `modenodelib_en` VALUES ('45', '4', 'ZB02C', 'ZB02C Wall Switch (3-Key)', '0006', '开关', '3', '1', 'source', 'ZB02C.jpg', 'ZB02C is a three keys wireless wall-mounted switch. It can be bound with a device which has switch control function and control the switch wirelessly. ', '2节AAA1.5V电池', '拆下面板的上盖，短按绑定键（红色按键），LED网络灯闪5下激活设备。', '拆下面板的上盖，按住绑定键的同时给设备上电，进行恢复出厂设置，看到状态指示灯闪烁，表示恢复完成', '无');
INSERT INTO `modenodelib_en` VALUES ('46', '4', 'ZB02F', 'ZB02F Wall Dimmer Switch', '0006:0008', '开关:调级控制', '3', '1', 'source', 'ZB02F.jpg', 'ZB02F is a single key wireless wall-mounted switch. It can be bound with a device which has switch and level control function and control the switch wirelessly. ', '2节AAA1.5V电池', '拆下面板的上盖，短按绑定键（红色按键），LED网络灯闪5下激活设备。', '拆下面板的上盖，按住绑定键的同时给设备上电，进行恢复出厂设置，看到状态指示灯闪烁，表示恢复完成。', '无');
INSERT INTO `modenodelib_en` VALUES ('47', '5', 'ZB01A', 'ZB01A Motion Detector', null, null, null, '1', null, 'ZB01A.jpg', 'The infrared detection alarm function of ZB01A can detect the movement of object. And trigger the alarm through the security center. ', '2节CR123A电池或12V直流电源', '按住背面小圆点按键3秒以上，直至状态指示灯快速闪烁2次激活设备', '按住背面小圆点（绑定键）的同时给设备上电，状态灯快闪，即恢复出厂设置操作成功。恢复完成后重新上电就可以重新加入网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('48', '5', 'ZB01B', 'ZB01B Motion Detector with On/Off Light Switch', '0006', '开关', '3', '1', 'source', 'ZB01B.jpg', 'ZB01B is a switch controller for motion detection. It can detect the movement of object through infrared and control the binding device according to the setting of light threshold.', '2节CR123A电池或12V直流电源', '按住背面小圆点按键3秒以上，直至状态指示灯快速闪烁2次激活设备', '按住背面小圆点（绑定键）的同时给设备上电，状态灯快闪，即恢复出厂设置操作成功。恢复完成后重新上电就可以重新加入网络了。', '可通过调节设备背面的光照灵敏度来设置绑定开关的的光照阈值。');
INSERT INTO `modenodelib_en` VALUES ('49', '5', 'ZB01C', 'ZB01C Motion Detector and Temperature Sensor', '0006', '开关', '3', '1', 'source', 'ZB01C.jpg', 'ZB01C has infrared detecting alarm, infrared on/off control and temperature reporting, three functions. The function of infrared alarm can detect the movement and send the alert through security center. Infrared on/off control function can detect the infrared generated from the moving objects then control the light switch. Temperature reporting function can detect the current temperature of the environment, and report it regularly to the binding device. ', '2节CR123A电池或12V直流电源', '按住背面小圆点按键3秒以上，直至状态指示灯快速闪烁2次激活设备', '按住背面小圆点（绑定键）的同时给设备上电，状态灯快闪，即恢复出厂设置操作成功。恢复完成后重新上电就可以重新加入网络了。', '可通过调节设备背面的光照灵敏度来设置绑定开关的的光照阈值。');
INSERT INTO `modenodelib_en` VALUES ('50', '5', 'ZB01D', 'ZB01D Occupancy Sensor', null, null, null, '1', null, 'ZB01D.jpg', 'ZB01D is an occupancy sensor. It can detect the object movement which produces infrared and report the status to the binding device. ', '2节CR123A电池或12V直流电源', '按住背面小圆点(绑定键)按键3秒以上，直至状态指示灯快速闪烁2次激活设备', '按住背面小圆点（绑定键）的同时给设备上电，状态灯快闪，即恢复出厂设置操作成功。恢复完成后重新上电就可以重新加入网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('51', '5', 'ZB11A', 'ZB11A Motion Detector', '', '', '', '1', '', 'ZB11A.jpg', 'The infrared detection alarm function of ZB11A can detect the movement of object. And trigger the alarm through the security center (CIE).', '2节ER14505 3.6V AA电池供电', '正对logo，短按右侧按钮指示灯闪5下，即可激活设备', '长按辅助键（正对logo右边）10秒，见到绿色指示灯快闪即可松开按键，设备将进行恢复出厂设置操作，完成后将自动关机。', '无');
INSERT INTO `modenodelib_en` VALUES ('52', '5', 'ZB11B', 'ZB11B Motion Detector with On/Off Light Switch', '0006', '开关', '3', '1', 'source', 'ZB11B.jpg', 'ZB11B is a switch controller for motion detection. It can detect the movement of object through infrared and control the binding device according to the setting of light threshold. ', '2节ER14505 3.6V AA电池供电', '正对logo，短按右侧按钮指示灯闪5下，即可激活设备', '长按辅助键（正对logo右边）10秒，见到绿色指示灯快闪即可松开按键，设备将进行恢复出厂设置操作，完成后将自动关机。', '可通过调节设备背面的光照灵敏度来设置绑定开关的的光照阈值。');
INSERT INTO `modenodelib_en` VALUES ('53', '5', 'ZB11C', 'ZB11C Motion Detector and Temperature Sensor', '0006', '开关', '3', '1', 'source', 'ZB11C.jpg', 'ZB11C has infrared detecting alarm, infrared on/off control and temperature reporting, three functions. The function of infrared alarm can detect the movement and send the alert through security center. Infrared on/off control function can detect the infrared generated from the moving objects then control the light switch. Temperature reporting function can detect the current temperature of the environment, and report it regularly to the binding device. ', '2节ER14505 3.6V AA电池供电', '正对logo，短按右侧按钮指示灯闪5下，即可激活设备', '长按辅助键（正对logo右边）10秒，见到绿色指示灯快闪即可松开按键，设备将进行恢复出厂设置操作，完成后将自动关机。', '可通过调节设备背面的光照灵敏度来设置绑定开关的的光照阈值。');
INSERT INTO `modenodelib_en` VALUES ('54', '5', 'ZB11D', 'ZB11D Occupancy Sensor', '', '', '', '1', '', 'ZB11D.jpg', 'ZB11D is an occupancy sensor. It can detect the object movement which produces infrared and report the status to the binding device. ', '2节ER14505 3.6V AA电池供电', '正对logo，短按右侧按钮指示灯闪5下，即可激活设备', '长按辅助键（正对logo右边）10秒，见到绿色指示灯快闪即可松开按键，设备将进行恢复出厂设置操作，完成后将自动关机。', '无');
INSERT INTO `modenodelib_en` VALUES ('55', '6', 'Z815N', 'Z815N  AC Curtain Controller', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z815N.jpg', 'Z815N is a curtain controller. It can control the curtains on/off and level through its own key, binding equipments or software. At the same time, the users can check the load current, voltage, power and energy at present. ', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', 'Lin接火线，N线是零线接电机的蓝线. L out1接电机的棕线，L out2接电机的黑线。在设备执行窗帘控制功能前,都应先进行“学习上下限”的学习，学习方法请详见设备说明书或在app的设备管理模块，该设备的设置页面进行学习。');
INSERT INTO `modenodelib_en` VALUES ('56', '6', 'ZD01B', 'ZD01B Toggle & Level Curtain Controller (Drape)', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'ZD01B.jpg', 'ZD01B is a curtain controller. It can control the curtains on/off and level through its own key, binding equipments or software.', '12V4A直流电源', '不需要激活', '按住ON1和OFF1键（靠右边一列）的同时给设备上电，开始执行恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重新上电就可以重新加网了', '在设备执行窗帘控制功能前,都应先进行“学习上下限”的学习，学习方法请详见设备说明书或在app的设备管理模块，该设备的设置页面进行学习');
INSERT INTO `modenodelib_en` VALUES ('57', '7', 'Z302A', 'Z302A Window Intrusion Sensor', null, null, null, '1', null, 'Z302A.jpg', 'Z302A is a magnetic device, used as a detection device in the security systems. When the door or window is opened, it will send alarm message to the security center. When the door or window is closed, it will send out the normal status message to the security center. ', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', 'Z302A的本体与门磁的磁铁部分别安装在门或窗户的缝隙的两边，当门或窗被打开，它将发出报警信息给安防中心设备( 即CIE )，当门或窗被关闭好，它会发送出状态恢复正常的信息给CIE。该设备也可以用于冰箱、抽屉等处，用于检测冰箱或抽屉等是否被打开。需要注意防水。');
INSERT INTO `modenodelib_en` VALUES ('58', '7', 'Z302C', 'Z302C Window Sensor with Glass Break Detector', null, null, null, '1', null, 'Z302C.jpg', 'Z302C is a detection device in the security systems, as an alarm devices when the doors and windows were exceptional be open or the glass are broken. ', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', 'Z302C的本体与门磁的磁铁部分别安装在门或窗户的缝隙的两边，当门或窗被打开，它将发出报警信息给安防中心设备( 即CIE )，当门或窗被关闭好，它会发送出状态恢复正常的信息给CIE。该设备也可以用于冰箱、抽屉等处，用于检测冰箱或抽屉等是否被打开。需要注意防水。');
INSERT INTO `modenodelib_en` VALUES ('59', '7', 'Z302D', 'Z302D Emergency Push Button', null, null, null, '1', null, 'Z302D.jpg', 'Z302D is a alarm triggered device, as a detection device in the security systems. It can be worn on the wrist of a child or the elderly, children or the elderly when in need of emergency assistance in danger, press the alarm button Z302D, Z302D immediately security Center sends out an alarm message.', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('60', '7', 'Z302E', 'Z302E Asset tag', null, null, null, '1', null, 'Z302E.jpg', 'Z-302E is equipped with light and movement sensor to detect light intensity and movements in the environment.  When status is changed, Z-302E sends alarm message to CIE.', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '可将该设备放于贵重的物品或抽屉旁，当物品被移动或光线发生变化时，则设备会将报警信息发送给安防中心设备。');
INSERT INTO `modenodelib_en` VALUES ('61', '7', 'Z302J', 'Z302J Window Intrusion Sensor', '0006', '开关', '3', '1', 'source', 'Z302J.jpg', 'Z-302J is a window/door sensor. When window/door status is changed, it can control its binding device according to the configurations. Z-302J also works as sensor of the security center- When window/door is open, it notifies security center CIE; When window/door is closed, it notifies security center CIE. ', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', 'Z302J的本体与门磁的磁铁部分别安装在门或窗户的缝隙的两边，当门或窗被打开，它将发出报警信息给安防中心设备( 即CIE )，当门或窗被关闭好，它会发送出状态恢复正常的信息给CIE。该设备也可以用于冰箱、抽屉等处，用于检测冰箱或抽屉等是否被打开。需要注意防水。');
INSERT INTO `modenodelib_en` VALUES ('62', '7', 'Z306D', 'Z306D Panic Button and Inductive Charging Base', null, null, null, '1', null, 'Z307.jpg', 'Z-306D acts as an end device in the network as well as the mobile tags of the local network. It can be searched and controlled via third-party software such as Netvox Zig-Butler.', '3V CR2450钮扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('63', '7', 'Z311A', 'Z311A Window Sensor', null, '', '', '1', '', 'Z311A.jpg', 'Z-311A is a window/door sensor and it is also a sensor in the security system- When window/door is closed, it notifies security center CIE. ', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '同时按下绑定键和辅助键，两键同时按着5s，直到看到红色指示灯开始快闪，再松开按键。红色指示灯快闪20次后，设备复位将进入power off模式。', 'Z311A的本体与门磁的磁铁部分别安装在门或窗户的缝隙的两边，当门或窗被打开，它将发出报警信息给安防中心设备( 即CIE )，当门或窗被关闭好，它会发送出状态恢复正常的信息给CIE。该设备也可以用于冰箱、抽屉等处，用于检测冰箱或抽屉等是否被打开。需要注意防水。');
INSERT INTO `modenodelib_en` VALUES ('64', '7', 'Z311C', 'Z311C Window Sensor with Glass Break Detector', null, '', '', '1', '', 'Z311C.jpg', 'Z-311C is a sensor in the security system- When window/door is open or window glass is broken, it will send out alarm messages.', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '同时按下绑定键和辅助键，两键同时按着5s，直到看到红色指示灯开始快闪，再松开按键。红色指示灯快闪20次后，设备复位将进入power off模式。', 'Z311A的本体与门磁的磁铁部分别安装在门或窗户的缝隙的两边，当门或窗被打开，它将发出报警信息给安防中心设备( 即CIE )，当门或窗被关闭好，它会发送出状态恢复正常的信息给CIE。该设备也可以用于冰箱、抽屉等处，用于检测冰箱或抽屉等是否被打开。需要注意防水。');
INSERT INTO `modenodelib_en` VALUES ('65', '7', 'Z312', 'Z312 Door Bell Button', null, null, null, '1', null, 'Z312.jpg', 'Z-312 is a door bell. Bind Z-312 with siren device and the siren will be able to generate door bell sounds.', '3V CR2450纽扣电池', '　　　　当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '同时按下绑定键和辅助键，两键同时按着5s，直到看到红色指示灯开始快闪，再松开按键。红色指示灯快闪20次后，设备复位将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('66', '7', 'Z307', 'Z307 Wireless Rechargeable Fall Sensor', null, null, null, '1', null, 'Z307.jpg', 'Z307 is a fall sensor as well as a sensor in security system. It can be worn on waist to detect fall of elderly people and young children. When fall is detected, it sends alarm message to the security center device.', '充电套件', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('67', '7', 'Z308', 'Z308 Wearable Presence Tag + Panic Button', null, null, null, '1', null, 'Z308.jpg', 'Z-308 is an emergency alarm device. As a sensor of the security system, it can be worn on wrist of elderly people or young children. When elderly people or young children press Z308\'s emergency button, it sends out alarm message to security center.', '3V CR2450纽扣电池', '长按按键3s，红色指示灯闪一下，松开手后，绿色指示灯闪五下。即激活设备。', '长按报警键15秒以上，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯快闪10次后，设备进入关机状态，红色指示灯熄灭。', '无');
INSERT INTO `modenodelib_en` VALUES ('68', '7', 'ZA01A', 'ZA01A Smoke Detector (Chemiresistors)', null, null, null, '1', null, 'ZA01A.jpg', 'Z-A01A is an air pollution sensor used in home environment, and it acts as a security device in the network. When air intensity goes above a specific level , the device will generate buzzer sound and send status change message to CIE.', '12V直流电源或100～240V交流供电', '不需要激活', '同时按住绑定键和测试键5秒以上，设备指示灯开始快速闪烁，表示复位完成。之后将设备重启，就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('69', '7', 'ZA01B', 'ZA01B Gas Detector', '', '', '', '1', '', 'ZA01B.jpg', 'Z-A01B is a gas detector and it acts as a security device in the network. When gas intensity goes above a specific level, it will send warning message to CIE and generate buzzer sound to warn the users.', '12V直流电源或100～240V交流供电', '不需要激活', '同时按住绑定键和测试键5秒以上，设备指示灯开始快速闪烁，表示复位完成。之后将设备重启，就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('70', '7', 'ZA01C', 'ZA01C CO Detector', null, null, null, '1', null, 'ZA01C.jpg', 'Z-A01C is a CO detector and it acts as a security device in the network. When CO intensity goes above a specific level, it will send warning message to CIE and generate buzzer sound to warn the users.', '12V直流电源或100～240V交流供电', '不需要激活', '同时按住绑定键和测试键5秒以上，设备指示灯开始快速闪烁，表示复位完成。之后将设备重启，就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('71', '7', 'ZA01D', 'ZA01D Liquefied Petroleum Gas Detector', null, null, null, '1', null, 'ZA01D.jpg', 'Z-A01D is a Liquefied gas detector and it acts as a security device in the network. When liquefied gas intensity goes above a specific level, it will send warning message to CIE and generate buzzer sound to warn the users.', '12V直流电源或100～240V交流供电', '不需要激活', '同时按住绑定键和测试键5秒以上，设备指示灯开始快速闪烁，表示复位完成。之后将设备重启，就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('72', '7', 'ZA02E', 'ZA02E Smoke Detector with Backup Battery', null, null, null, '1', null, 'ZA02E.jpg', 'Z-A02E is a smoke detector. When smoke intensity goes above a specific level, it will send warning message to CIE and generate buzzer sound to warn the users.', '12V直流电源或100～241V交流供电', '不需要激活', '同时按住绑定键和测试键5秒以上，设备指示灯开始快速闪烁，表示复位完成。之后将设备重启，就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('73', '7', 'ZA10', 'ZA10 Gas/Water Keeper', '0006', '开关', '3', '1', 'dest', 'ZA10.jpg', 'Z-A10 can be a gas keep or water keep. When Z-A10 is used as a gas keeper, it will turn off the gas when there are fire detection in the network.', '12V直流电源', '不需要激活', '按住按键的同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复出厂设置完成，重新上电就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('74', '7', 'ZB05', 'ZB05 Electronic Door Lock with Fingerprint Identification', '0101', '开关锁', '3', '1', 'dest', 'ZB05.jpg', 'Z-B05 can be unlocked/locked via mechanical keys, passwords, IC cards, and remote control. Via ZigBee wireless network, users can check and control door lock status at any time. Z-B05 door lock provides reliable password security and fire detection for safety purpose.', '8节AA1.5V电池', '不需要激活', '1.按住网络键的同时给设备上电，上电完请不要立即松开按键，等到网络灯开始快速闪烁，提示复位成功；2.重新上电，ZB05可以开始重新加网。', '1、初次使用时，需要注册zigbee：在门打开的状态下，打开内侧用（主锁身）的电池盖和外盒，在右侧下端按“锁卡注册键”。然后再按下面的“ZB注册键”（靠上的按键）。按“*”键，可听到“哔呖呖”的设置音，设置结束。2、默认都为加密的方式，第一次使用时，需要在app中→设备管理模块→进入门锁的设置页面，设置新的用户和密码，才可以对锁进行开关控制。门锁默认有6个空用户，注册新用户前，需要先将这些用户删除。超级密码默认为：123456.usercode即用户名为1-254的数字。');
INSERT INTO `modenodelib_en` VALUES ('75', '7', 'ZB02E', 'ZB02E Door Bell', null, null, null, '1', null, 'ZB02E.jpg', 'Z-B02E is a door bell. Bind Z-B02E with siren device and the siren will be able to generate door bell sounds.', '2节AAA1.5V电池', '拆下面板的上盖，短按绑定键（红色按键），LED网络灯闪5下激活设备。', '拆下面板的上盖，按住绑定键的同时给设备上电，进行恢复出厂设置，看到状态指示灯闪烁，表示恢复完成', '无');
INSERT INTO `modenodelib_en` VALUES ('76', '7', 'Z311E', 'Z311E Asset tag', '', '', '', '1', '', 'Z311E.jpg', 'Z-311E is equipped with light and movement sensor to detect light intensity and movements in the environment.  When status is changed, Z-311E sends alarm message to CIE.', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '同时按下绑定键和辅助键，两键同时按着5s，直到看到红色指示灯开始快闪，再松开按键。红色指示灯快闪20次后，设备复位将进入power off模式。', '可将该设备放于贵重的物品或抽屉旁，当物品被移动或光线发生变化时，则设备会将报警信息发送给安防中心设备。');
INSERT INTO `modenodelib_en` VALUES ('77', '7', 'Z602A', 'Z602A Siren', null, null, null, '1', null, 'Z602A.jpg', 'Z-602A is a siren device which generate buzzer sound and visible LED indication to report events in the security system.', '12V直流电源和3节1.2V可充电备用电池', '不需要激活', '按住设置键（正对logo右侧按键）的同时给设备上电，状态灯快速闪烁且发出两次门铃报警声，即恢复出厂设置操作成功。恢复完成后重新上电就可以重新加入网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('78', '7', 'Z602B', 'Z602B Siren with GSM Connectivity', null, null, null, '1', null, 'Z602B.jpg', 'Z-602B is a siren device which generate buzzer sound and visible LED indication to report events in security system. When Z-602B generate buzzer sounds, it can also call and text a pre-configured phone number according to different alarm levels for users to receive the alarm message in the real time.', '12V直流电源和3节1.2V可充电备用电池', '不需要激活', '按住设置键（正对logo右侧按键）的同时给设备上电，状态灯快速闪烁且发出两次门铃报警声，即恢复出厂设置操作成功。恢复完成后重新上电就可以重新加入网络了。', '该设备具有GSM功能，可在电池盒内装入SIM卡，在app的设备管理模块，进入该设备的设置页面，可设置电话号码，用于报警时，通过拨打电话和发送短信通知用户。设置的短信号码前，需要加国家代码（如中国：86）');
INSERT INTO `modenodelib_en` VALUES ('79', '7', 'Z601A', 'Z601A Siren', null, null, null, '1', null, 'Z601A.jpg', 'Z-601A is a siren device which generate buzzer sound to report events in the security system.', '12V直流电源', '不需要激活', '按住绑定键的同时给设备上电，状态灯快闪，即恢复出厂设置操作成功。恢复完成后重新上电就可以重新加入网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('80', '8', 'Z821', 'Z821 Multi-Channel Energy Power Meter', null, null, null, '1', null, 'Z821.jpg', 'Z-821 is an indoor multi-channel power meter. It can measure voltage of single channel and the total current of all seven channels, and it can provide power and energy data.', '100～240V的交流电源', '不需要激活', '按下Bind Key按键15秒后（状态灯每5秒钟闪烁1次，共闪烁3次，代表长按了15秒），松开按键，在两秒之内短按Match Key进行网络信息的擦除。网络信息擦除后设备自动重启寻找网络。', '将七路CT端子（用于检测电能）接入到Z821两侧的接口。按照CT端子上指示的K→L方向（电流流动的方向）,将导线扣入CT端子，即可检测该路的电能。');
INSERT INTO `modenodelib_en` VALUES ('81', '9', 'Z501A', 'Z501A 4-Key Remote Controller (250)', '0006:0008', '开关:调级控制', '3', '1', 'source', 'Z501A.jpg', 'As a remote control device, Z501A can be bound with on/off or dimmer devices for users to control the devices wirelessly. Z-501A also comes with an emergency button for users to call for help in emergency.', '3V CR2032纽扣电池', '右侧开关off断电，重新上电后即可激活设备。', '按住“开关键” 的同时给设备上电，开始恢复出厂值，指示灯共闪烁20次，之后重新操作组合键就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('82', '9', 'Z501B', 'Z501B 4-Key Remote Controller (250)', '0006', '开关', '3', '1', 'source', 'Z501B.jpg', 'As a remote control device, Z-501B can be bound with on/off devices for users to control the devices wirelessly.', '3V CR2032纽扣电池', '右侧开关off断电，重新上电后即可激活设备。', '按住“ON2键” 的同时给设备上电，开始恢复出厂值，指示灯共闪烁20次，之后重新操作组合键就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('83', '9', 'Z501C', 'Z501C 4-Key Remote Controller(250)', '0006:0008', '开关:调级控制', '3', '1', 'source', 'Z501C.jpg', 'Z501C can be bound with on/off or dimmer devices for users to control the devices wirelessly.', '3V CR2033纽扣电池', '右侧开关off断电，重新上电后即可激活设备。', '按住“ ON/OFF1键” 的同时给设备上电，开始恢复出厂值，指示灯共闪烁20次，之后重新操作组合键就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('84', '10', 'Z210', 'Z210 Infrared Gateway with 1 External Port', null, null, null, '1', null, 'Z210C.jpg', 'Z-210 is the center of the security system to manage security devices. It can also learn and send IR signals for users to control their home appliances such as TV and DVD player.', '12V直流电源', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '使用红外转接器控制家用电器时，需要在app上导入红外数据，若无红外数据时，则需要通过IR学习器或手机app手动学习。');
INSERT INTO `modenodelib_en` VALUES ('85', '10', 'Z211', 'Z211  Infrared Gateway with 4 External Ports', null, null, null, '1', null, 'Z211.jpg', 'Z-211 is a device for IR learning and IR controlling. After learning IR codes, Z-211 can control home appliances which receives IR signals. Also, Z-211 can control ZigBee devices when it receives IR signals from a regular remote control.', '12V直流电源', '不需要激活', '按住绑定键（蓝色按键），同时给设备上电，开始恢复出厂设置，直到状态指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '使用红外转接器控制家用电器时，需要在app上导入红外数据，若无红外数据时，则需要通过IR学习器或手机app手动学习。');
INSERT INTO `modenodelib_en` VALUES ('86', '11', 'Z203', 'Z203  Cloud Wireless Smart Home Center', null, null, null, '1', null, 'Z203.jpg', 'As the main controller of Netvox Cloud-based Wireless Smart Home System, Z-203 is a perfect combination of cloud services, Wifi technology, and ZigBee Home Automation solutions. Z-203 enables users to monitor their lights, curtains, and home appliances via smart home app and Wifi connection, and users can easily monitor their home with 2G/3G network even they are far away.', '12V直流电源', '不需要激活', '详见203说明书', 'Z203可用12V直流电源供电，给Z203上电后，打开手机的WiFi功能，即可搜索到Z203的无线信号。外网网线接入Z203的WAN口，LAN口可直接接IPcamera或电脑，当家中有多个 IPcamera时，需要增加集线器。');
INSERT INTO `modenodelib_en` VALUES ('87', '11', 'Z103AC', 'Z103AC USB Coordinator', null, null, null, '1', null, 'Z103AC.jpg', 'Z103AC is a coordinator with USB port. The devicce can communicate with other Zigbee modules through the port, and thus control the entire system.', 'USB', '不需要激活', '按住PermitJoin键的同时给Z103设备上电，设备指示灯处于快速闪烁状态，表示恢复出厂设置完成，设备重新上电就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('88', '11', 'Z103AR', 'Z103AR USB Router', null, null, null, '1', null, 'Z103AR.jpg', 'Z103AR is a Router with USB port，which can transmit the information to other devices with the USB port driver.', 'USB', '不需要激活', '按住PermitJoin键的同时给Z103设备上电，设备指示灯处于快速闪烁状态，表示恢复出厂设置完成，设备重新上电就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('89', '11', 'Z201B', 'Z201B Coordinator with Security Center (CIE)', null, null, null, '1', null, 'Z201B.jpg', 'Z201B is a HA Coordinator & CIE. With the USB port driver, the device can communicate with other Zigbee modules through the port and allow the enrolling of other devices, thus to control the entire system;', '12V直流电源', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('90', '11', 'Z201C', 'Z201C ZigBee802.15.4 Coord ', null, null, null, '1', null, 'Z201C.jpg', 'Z201C is HA coordinator with USB port,  With the USB port driver, the device can communicate with other Zigbee modules through the port, thus to control the entire system;', '12V直流电源', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('91', '11', 'Z201R HA', 'Z201R HA ZigBee TCP/IP Gateway', null, null, null, '1', null, 'Z201R.jpg', 'HA Repeater', '12V直流电源', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('92', '11', 'Z201R', 'Z201R ZigBee 802.15.4 Router', null, null, null, '1', null, 'Z201R.jpg', 'Location Reference Node', '12V直流电源', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('93', '4', 'Z812A', 'Z812A Programmable 8-Button Scene Control Keypad', '0006', '开关', '3', '1', 'source', 'Z812A.jpg', 'Z-812A is Multi-port Wireless  In-wall Switch Module. It can bind with the devices carrying the function of on/off switch control and enables wireless on/off control.', '100～240V的交流电源', '不需要激活', '上电以后，同时按住OFF3和OFF4键，按住5秒，网络灯灭，然后放开按键，开始恢复出厂值，直到网络灯开始闪烁，表示恢复完成。之后设备将自动复位，可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('94', '4', 'Z812B', 'Z812B Battery Operated 8-Button Control Keypad', '0006:0008', '开关:调级控制', '3', '1', 'source', 'Z812B.jpg', 'Z-812B is Multi-port Wireless  In-wall Switch Module. It can bind with the devices carrying the function of on/off switch control and enables wireless on/off control.', '100～240V的交流电源', '同时按住第一行两个按键即可激活设备。', '同时按住按键3和按键4达3秒，指示灯闪烁1次提示设备开始恢复出厂设置，恢复成功指示灯快闪10次，不成功指示灯无动作。恢复出厂设置之后设备自动重启。', '无');
INSERT INTO `modenodelib_en` VALUES ('95', '7', 'Z801TXB', 'Z801TXB Sensor Signal Tx Module', '', '', '', '1', '', 'Z801TXB.jpg', 'Z801TXB is a pulse Signal Detector，it can be connected with 5 equipment.if Z801txb detects the signal,it will send the command to the security of the ENROLL control center .', '5V直流电源或2节1.5V电池', '不需要激活', '按住绑定键的同时给设备上电，LED2闪烁20次，表示复位完成，之后重新上电，Z801 TXB可以重新加网。 ', '无');
INSERT INTO `modenodelib_en` VALUES ('96', '0', 'Z801RX', 'Z801RX  RX Relay Board', '0006', '开关', '3', '1', 'dest', 'Z801RX.jpg', 'Z801RX is relay board.it is used to control the switch of the equipment,mainly for controling the switch of household appliance.  ', '2节1.2V电池', '不需要激活', '1.  按住绑定键的同时给设备上电；2.  复位完成则状态指示灯快速灯闪烁；3.  重新上电，Z801RX可以开始重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('100', '11', 'Z508', 'Z508 In Home Display', null, null, null, '1', null, 'Z508.jpg', 'Z508 is a Smart In-Home Display for smart home and household energy monitoring. Z-508 works as Smart Home Control Center and allows users to review current and historical energy consumption data.By through Zig-Butler Smart Scheme, users can select and switch any preferable mode setting. With the implementation of CIE, Z-508 is capable of managing Home Security System and tells security related message on its display.', '5V直流电源或2节1.5V电池', '不需要激活', '详见Z508说明书', '无');
INSERT INTO `modenodelib_en` VALUES ('101', '0', 'Z825A', 'Z825A Touch Panel Switch with Power Meter (1-Gang)', '0006', '开关', '3', '1', 'dest', 'Z825A.jpg', 'Z-825A is One Gang In-Wall Switch with Touch Panel and fits with 86*86 junction box and replaces the existing wall switch. It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-825A by ZigBee Remote Control, Smart Phone, On/Off Switch and so on.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('102', '0', 'Z825B', 'Z825B Touch Panel Switch with Power Meter (2-Gang)', '0006', '开关', '3', '1', 'dest', 'Z825B.jpg', 'Z-825B is Two Gang In-Wall Switch with Touch Panel and fits with 86*86 junction box and replaces the existing wall switch. It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-825B by ZigBee Remote Control, Smart Phone, On/Off Switch and so on.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('103', '0', 'Z825D', 'Z825D Touch Panel Dimmer Switch with Power Meter (1-Gang)', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z825D.jpg', 'Z-825D is One Gang In-Wall Dimmer Switch with Touch Panel and fits with 86*86 junction box and replaces the existing wall switch. It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-825A by ZigBee Remote Control, Smart Phone, On/Off Switch and so on.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('104', '2', 'Z725A', 'Z725A Temperature and Humidity Sensor (Outdoor)', null, null, null, '1', null, 'Z725A.jpg', 'Z-725A is Wireless H/T Sensor and Ultraviolet Detector with Solar Battery Panel. It comes with water-proof enclosure for protective purpose. It is used to measure and collect Humidity, Temperature and Ultraviolet data in the surrounding and direct to the data collector for display.', '太阳能充电电池', '不需要激活', '设备上电后，同时按住“绑定键”和“设置键” 5秒，开始恢复出厂值，LED快闪10次，表示恢复出厂值完成，设备自动复位寻找加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('106', '11', 'Z725R', 'Z725R Repeater with Solar Battery (Outdoor)', null, null, null, '1', null, 'Z725R.jpg', 'Z-725R is a Outdoor type Wireless Repeater，which is use to expand the network range。 ', '太阳能充电电池', '不需要激活', '设备上电后，同时按住绑定键和辅助键5秒，状态灯闪烁1次后，开始恢复出厂值，LED快闪20次，表示恢复出厂值完成，设备自动复位寻找加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('107', '0', 'Z962A', 'Z962A One Gang Wall Switch with Touch Panel', '0006', '开关', '3', '1', 'dest', 'Z962A.jpg', 'Z-962A is One Gang In-Wall Switch with Touch Panel and fits with 86*86 junction box and replaces the existing wall switch. It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-962A by ZigBee Remote Control, Smart Phone, On/Off Switch and so on.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('108', '0', 'Z962B', 'Z962B Two Gang Wall Switch with Touch Panel', '0006', '开关', '3', '1', 'dest', 'Z962B.jpg', 'Z-962B is Two Gang In-Wall Switch with Touch Panel and fits with 86*86 junction box and replaces the existing wall switch. It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-962B by ZigBee Remote Control, Smart Phone, On/Off Switch and so on.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('109', '0', 'Z962C', 'Z962C Three Gang Wall Switch with Touch Panel', '0006', '开关', '3', '1', 'dest', 'Z962C.jpg', 'Z-962C is Three Gang In-Wall Switch with Touch Panel and fits with 86*86 junction box and replaces the existing wall switch. It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-962C by ZigBee Remote Control, Smart Phone, On/Off Switch and so on.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('110', '0', 'Z962D', 'Z962D Scene or Mode Selector 1 key with Touch Panel', null, null, null, '1', null, 'Z962D.jpg', 'Z-962D is Scene and Mode control keypad, by through 1 touch keypad, users can add scene/mode, keep scene storage and manage mode control to carry out memorizing 1 scence control. It also supports Netvox mode control functionality.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib_en` VALUES ('111', '0', 'Z962E', 'Z962E Scene or Mode Selector 2 keys with Touch Panel', null, null, null, '1', null, 'Z962E.jpg', 'Z-962E is Scene and Mode control keypad, by through 2 touch keypad, users can add scene/mode, keep scene storage and manage mode control to carry out memorizing 2 scence control. It also supports Netvox mode control functionality.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib_en` VALUES ('112', '0', 'Z962F', 'Z962F Scene or Mode Selector 3 keys with Touch Panel', null, null, null, '1', null, 'Z962F.jpg', 'Z-962F is Scene and Mode control keypad, by through 3 touch keypad, users can add scene/mode, keep scene storage and manage mode control to carry out memorizing 3 scence control. It also supports Netvox mode control functionality.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib_en` VALUES ('113', '0', 'Z962G', 'Z962G One Scene or Mode Selector & one Switch', '0006', '开关', '3', '1', 'dest', 'Z962G.jpg', 'Z962G is One Gang Touch Switch and 1 Scence Control Touch Button and fits with 86*86 junction box and replaces the existing wall switch. It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-962G by ZigBee Remote Control, Smart Phone, On/Off Switch and so on. Users can manage scence/mode control with the secnce control button.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib_en` VALUES ('114', '0', 'Z962H', 'Z962H Two Scene or Mode Selector & one Switch', '0006', '开关', '3', '1', 'dest', 'Z962H.jpg', 'Z962H is One Gang Touch Switch and 2 Scence Control Touch Button and fits with 86*86 junction box and replaces the existing wall switch. It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-962H by ZigBee Remote Control, Smart Phone, On/Off Switch and so on. Users can manage scence/mode control with the secnce control button.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib_en` VALUES ('115', '0', 'Z962I', 'Z962I One Scene or Mode Selector & Two Switch', '0006', '开关', '3', '1', 'dest', 'Z962I.jpg', 'Z962I is Two Gang Touch Switch and 1 Scence Control Touch Button and fits with 86*86 junction box and replaces the existing wall switch. It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-962I by ZigBee Remote Control, Smart Phone, On/Off Switch and so on. Users can manage scence/mode control with the secnce control button.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib_en` VALUES ('116', '7', 'Z801WLS', 'Z801WLS Water Sensor', '', '', '', '1', '', 'Z801WLS.jpg', 'Z-801WLS is a  a water Sensor,which is used for monitoring leakage situation, and send the alarm information to the security center immediately.', '5V直流电源或2节1.5V电池', '不需要激活', '按住绑定键的同时给设备上电，LED2闪烁20次，表示复位完成，之后重新上电，Z801WLS可以重新加网。 ', '无');
INSERT INTO `modenodelib_en` VALUES ('117', '7', 'ZB05A', 'ZB05A Door Lock with Fingerprint Identification', '0101', '开关锁', '3', '1', 'dest', 'ZB05A.jpg', 'Z-B05A can control lock/unlock of the door that applied to it including mechanical key, access code, IC card, finger print, remote access ect.. By through ZigBee wireless networking, users can monitor and control the door status, manage user ID and passwork anywhere you are. Intellectualized door lock design supports reliable access code safeguard to guarantee home surveillance safety.', '8节AA1.5V电池', '不需要激活', '1.按住网络键的同时给设备上电，上电完请不要立即松开按键，等到网络灯开始快速闪烁，提示复位成功；2.重新上电，ZB05A可以开始重新加网。', '1、初次使用时，需要注册zigbee：在门打开的状态下，打开内侧用（主锁身）的电池盖和外盒，在右侧下端按“锁卡注册键”。然后再按下面的“ZB注册键”（靠上的按键）。按“*”键，可听到“哔呖呖”的设置音，设置结束。2、默认都为加密的方式，第一次使用时，需要在app中→设备管理模块→进入门锁的设置页面，设置新的用户和密码，才可以对锁进行开关控制。门锁默认有6个空用户，注册新用户前，需要先将这些用户删除。超级密码默认为：123456.usercode即用户名为1-254的数字。');
INSERT INTO `modenodelib_en` VALUES ('118', '4', 'Z801TX', 'Z801TX TX Switch Board', '0006', '开关', '3', '1', 'source', 'Z801TX.jpg', 'Z801TX is used as a end device in the zigBee network,which doesn\'t allow other devices to be its child device.It can be extended with five buttons. When the button is trigged，it can send commands to bound device and control its on/off at the detection of a signal.', '无', '无', '无', '无');
INSERT INTO `modenodelib_en` VALUES ('119', '7', 'Z311J', 'Z311J Window Sensor with On/Off Switch', '0006', '开关', '3', '1', 'source', 'Z311J.jpg', 'Z-311J is a window/door sensor. When window/door status is changed, it can control its bound device according to the configurations. Z-311J also works as a detection sensor of the security system- when window/door is open or closed, it also notifies security center with the alarm messages, while sends the information of normal status to CIE when the window/door is closed.', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', 'Z311J的本体与门磁的磁铁部分别安装在门或窗户的缝隙的两边，当门或窗被打开，它将发出报警信息给安防中心设备( 即CIE )，当门或窗被关闭好，它会发送出状态恢复正常的信息给CIE。该设备也可以用于冰箱、抽屉等处，用于检测冰箱或抽屉等是否被打开。需要注意防水。');
INSERT INTO `modenodelib_en` VALUES ('120', '0', 'ZC07', 'ZC07 Dimmable LED Bulb', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'ZC07.jpg', 'ZC07 is a robust ZigBee enabled dimmable LED bulb. It can achieve 256 dimmer light levels and can be controlled wirelessly. By methods such as binding devices, wireless remote control or software operation, it can control light on/off and dimmer. As a 5W bulb, ZC07 applies constant current control mode withthe input voltage from AC 100V to 240V. The inputs of 100V to 240V can achieve the same lighting effects.', '100～240V的交流电源', '不需要激活', '同时长按测试键和绑定键5s，5s时间到后指示灯闪10次后就恢复出厂设置成功，此时就可以重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('121', '7', 'Z311W', 'Z311W Water Sensor', null, null, null, '1', '', 'Z311W.jpg', 'Z311W is a water sensor,which is used for monitoring leakage situation and sending the alarm information to the security center immediately.', '无需外加电源,产品使用内部的3V钮扣电池供电。', '同时按住绑定键和辅助键，看到红色指示灯灯闪烁2次，此时松开按键。', '同时按住绑定键和辅助键，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('122', '6', 'Z811B', 'Z811B Curtain Controller (2- Gang)', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z811B.jpg', 'Z811B is a Two Gang curtain controller. It can control the curtains on/off and level through its own key, bound devices or software. ', '100-240V AC 50/60HZ电源供电', '不需要激活', '方法一：按住“绑定键（logo旁的小按钮）”上电，指示灯快闪，表示恢复出厂设置完成，再次重新上电后即可。方法二：长按住“绑定键（logo旁的小按钮）”键的15秒待指示灯闪烁一次（此间指示灯3秒、10秒、15秒各闪烁一次），松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，自动重启就可以重新加网了。', 'Lin接火线，N线是零线接电机的零线（一般为蓝线）。EP1：Lout1接电机正转输入（一般为棕线)，Lout2接电机反转输入（一般为黑线）。EP2：Lout3接电机正转输入（一般为棕线)，Lout4接电机反转输入（一般为黑线）。在设备执行窗帘控制功能前,都应先进行“学习上下限”的学习，学习方法请详见设备说明书或在app的设备管理模块，该设备的设置页面进行学习。');
INSERT INTO `modenodelib_en` VALUES ('123', '0', 'Z825I', 'Z825I Touch Panel Programmable Scene Selector (6-Gang)', '', '', '', '1', '', 'Z825I.jpg', 'As a Scene Selector & Mode Selector，Z825I is equipped with six touch buttons. Each can be configured to add scene or control mode (each button can at most at one scene or configure four modes.)', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib_en` VALUES ('124', '0', 'Z825J', 'Z825J Touch Panel 3-Gang Switch&Scene Selector', '0006', '开关', '3', '1', 'dest', 'Z825J.jpg', 'Z825J is a three-gang touch switch and three-gang Scene Selector,which can be directly installed in 86 junction box to replace the normal wall switch.It monitors and calculates Current, Voltage, Power and Engery. Users can control Z-825J by ZigBee Remote Control, Smart Phone, On/Off Switch and so on. Users can manage scence/mode control with the secnce control button.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib_en` VALUES ('125', '0', 'Z825Q', 'Z825QTouch Panel Switch with Power Meter (3-Gang)', '0006', '开关', '3', '1', 'dest', 'Z825Q.jpg', 'Z825Q is three-gang local switch controller with single livewire for power. It can control its on/off through being bound with  devices with on/off function to control it or through its own keys. Its three-gang on/off is controlled by three keys.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络青绿色指示灯闪烁一次（期间网络青绿色指示灯闪烁4次，其中刚按下闪一次，3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络青绿色指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会进入睡眠模式，短按绑定键Z825Q可重新加网。 ', 'Z825Q具有单路输入，三路输出的能力，接线时，将一种线（N或L，来自市电）接入输入口，然后在三路输出口引出同种类型的输出线（给设备供电），将插头接入到AC  100-240V的电源，给设备上电。');
INSERT INTO `modenodelib_en` VALUES ('126', '1', 'Z816I', 'Z816I  China Type Wall Socket with Power Meter (5-pin)', '0006', '开关', '3', '1', 'dest', 'Z816I.jpg', 'Z816I is a China type wireless current-detection smart wall socket, which can be directly installed in 86 junction box to replace the traditional wall socket. Its on/off can be controlled with its own keys or through its bound devices and software.Via software, users are able to monitor the load current, voltage and power and kilo-watt-hour consumption of the attached appliance.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('127', '5', 'Z817D', 'Z817D  Ceiling Motion Detector', '0006', '开关', '3', '1', 'source', 'Z817D.jpg', 'Z817D has the on/off switch by the infrared detection.When it detects the IR of moving objects,it can control on/off of bound devices.', 'AC100-240V 50/60HZ供电', '不需要激活', '给设备上电，同时按住“按键1”和“按键2”5秒；复位完成则led闪烁30次后自动重新请求加网；', '无');
INSERT INTO `modenodelib_en` VALUES ('128', '11', 'Z800R', 'Z800R Plug Repeater with Outlet', null, null, null, '1', null, 'Z800R.jpg', 'Z800R is a Router Repeaters with a socket,which allow other devices as his children.It can extend the communication distance for other devices.', 'AC 100-250V的电源', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重新上电就可以加入新的网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('129', '1', 'Z809C', 'Z809C Plug Repeater with Backup Battery', null, null, null, '1', null, 'Z809C.jpg', 'Z809C is a Router Repeaters with a socket,which allow other devices as his children.It can extend the communication distance for other devices.', 'AC 100-240V的电源或电池供电（内置电池）', '不需要激活', '长按绑定键15s，15s时间到状态闪1次后放开绑定键，两秒内短按绑定键就状态灯闪烁10次，并自动复位。其中长按绑定键3s，10s和15s的时候状态灯都会依次闪烁一次，以提示所按的时间长短。', '无');
INSERT INTO `modenodelib_en` VALUES ('130', '1', 'Z809D', 'Z809D Plug Repeater with Power Amplifier & Backup Battery', '', '', '', '1', '', 'Z809D.jpg', 'Z809D is a Router Repeaters with a socket,which allow other devices as his children.It can extend the communication distance for other devices.', 'AC 100-240V的电源或电池供电（内置电池）', '不需要激活', '长按绑定键15s，15s时间到指示闪1次后放开绑定键，两秒内短按绑定键就指示灯闪烁10次，并自动重启设备（如果仅仅用电池供电不能重启）。', '无');
INSERT INTO `modenodelib_en` VALUES ('131', '9', 'Z501AE3ED', 'Z501A 4-Key Remote Controller(357)', '0006:0008', '开关:调级控制', '3', '1', 'source', 'Z501A.jpg', 'As a remote control device, Z501A can be bound with on/off switch or dimmer devices for users to control the devices wirelessly. Z-501A also comes with an emergency button for users to call for help in emergency.', '3V CR2032纽扣电池', '右侧开关off断电，重新上电后即可激活设备。', '按住“开关键” 的同时给设备上电，开始恢复出厂值，指示灯共闪烁20次，之后重新操作组合键就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('132', '9', 'Z501BE3ED', 'Z501B 4-Key Remote Controller(357)', '0006', '开关', '3', '1', 'source', 'Z501B.jpg', 'Z510B is a remote control,which can be bound to control on/off of other devices.', '3V CR2032纽扣电池', '右侧开关off断电，重新上电后即可激活设备。', '按住“ON2键” 的同时给设备上电，开始恢复出厂值，指示灯共闪烁20次，之后重新操作组合键就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('133', '9', 'Z501CE3ED', 'Z501C 4-Key Remote Controller(357)', '0006:0008', '开关:调级控制', '3', '1', 'source', 'Z501C.jpg', 'As a remote control device, Z501C can be bound with on/off or dimmer devices for users to control the devices wirelessly. ', '3V CR2033纽扣电池', '右侧开关off断电，重新上电后即可激活设备。', '按住“ ON/OFF1键” 的同时给设备上电，开始恢复出厂值，指示灯共闪烁20次，之后重新操作组合键就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('134', '7', 'ZB02I', 'ZB02I  Emergency Push Button', '', '', '', '1', '', 'ZB02E.jpg', 'ZB02I is an emergency push button working as a detection device in the security system. It can be put on the wall or anyplace in the room .When users in danger are in need of emergency assistance, users can press the alarm button for ZB02I to immediately send out an alarm message to the security center, which will notify the siren to make sound or light to alert family members to offer immediate help. ', 'AAA电池供电', '短按绑定键，若设备仍在网络状态，则绿色指示灯闪烁5次，表示激活成功。', '按住绑定键的同时给设备上电，进行恢复出厂设置，看到指示灯闪烁，表示恢复完成。', '无');
INSERT INTO `modenodelib_en` VALUES ('137', '0', 'Z802', 'Z802 Two Way Switching Load module', '0006', '开关', '3', '1', 'dest', 'Z802.jpg', 'Z802 can be bound with on/off switch for users to control the devices wirelessly.User can switch on/off the appliances attached to it through mechnical switch or wirelessly. Users may also use ZiG-BUTLER to check the current status of the device and make corresponding control.', '100-240VAC 50/60HZ 电源供电', '不需要激活', '按住绑定键的同时，给设备上电，开始恢复出厂值，直到LED1指示灯开始闪烁，表示恢复完成。之后重新上电，Z802可以重新加网了', '无');
INSERT INTO `modenodelib_en` VALUES ('138', '4', 'ZB02J', 'ZB02J Wireless Scene & Mode Selector (3-Key)', '', '', '', '1', '', 'ZB02C.jpg', 'ZB02J is a three-gang scene button, which can be bound with three scenes for users to control them.', '无需外加电源,产品使用内部的2节7号电池供电', '短按绑定键（led网络灯闪5下）', '按住绑定键不松开，装入电池，进行恢复出厂设置，看到状态灯闪烁，表示恢复完成，然后松开按键，拿出电池', '无');
INSERT INTO `modenodelib_en` VALUES ('139', '0', 'Z815A', 'Z815A Wall Switch with Power Meter (1-Gang)', '0006', '开关', '3', '1', 'dest', 'Z815I.jpg', 'Z815A is a one-gang Output switch,which can be directly installed in 86 junction box to replace the existing wall switch.It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-815A by ZigBee Remote Control, Smart Phone, On/Off Switch and other methods.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('140', '0', 'Z815B', 'Z815B Wall Switch with Power Meter (2-Gang)', '0006', '开关', '3', '1', 'dest', 'Z815J.jpg', 'Z815B is a two-gang Output switch,which can be directly installed in 86 junction box to replace the existing wall switch.It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-815B by ZigBee Remote Control, Smart Phone, On/Off Switch and other methods.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('141', '0', 'Z815C', 'Z815C Wall Switch with Power Meter (3-Gang)', '0006', '开关', '3', '1', 'dest', 'Z815K.jpg', 'Z815C is a three-gang Output switch,which can be directly installed in 86 junction box to replace the existing wall switch.It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-815C by ZigBee Remote Control, Smart Phone, On/Off Switch and other methods.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('142', '0', 'Z815D', 'Z815D  Wall Dimmer Switch with Power Meter (1-Gang)', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z815L.jpg', 'Z815D is a one-gang Output dimmer which can control on/off and dimmer and be directly installed in 86 junction box to replace the existing wall switch.It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-815D by ZigBee Remote Control, Smart Phone, On/Off Switch and other methods.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('143', '0', 'Z815E', 'Z815E Wall Dimmer Switch with Power Meter (2-Gang)', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z815M.jpg', 'Z815E is a two-gang Output dimmer which can control on/off and dimmer and be directly installed in 86 junction box to replace the existing wall switch.It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-815E by ZigBee Remote Control, Smart Phone, On/Off Switch and other methods.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('144', '0', 'Z826C', 'Z826C Touch Panel Switch with Power Meter (3-Gang)', '0006', '开关', '3', '1', 'dest', 'Z825Q.jpg', 'Z826C is three-gang local switch controller with single livewire for power. It can control its on/off through being bound with devices with on/off function to control it or through its own keys. Its three-gang on/off is controlled by three keys.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络青绿色指示灯闪烁一次（期间网络青绿色指示灯闪烁4次，其中刚按下闪一次，3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络青绿色指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会进入睡眠模式，短按绑定键Z826C可重新加网。 ', 'Z826C具有单路输入，三路输出的能力，接线时，将一种线（N或L，来自市电）接入输入口，然后在三路输出口引出同种类型的输出线（给设备供电），将插头接入到AC  100-240V的电源，给设备上电。');
INSERT INTO `modenodelib_en` VALUES ('145', '11', 'Z206', 'Z206 Cloud-Based Wireless Smart Home Controller', '', '', '', '1', '', 'Z206.jpg', 'As the main controller of Netvox Cloud-based Wireless Smart Home System, Z-206 is a perfect combination of cloud services, Wifi technology, and ZigBee Home Automation solutions. Z-206 enables users to monitor their lights, curtains, and home appliances via smart home app and Wifi connection, and users can easily monitor their home with 2G/3G network even they are far away.', '使用DC 12V  1.5A电源适配器，接入100-220V的电源', '不需要激活', '详见206说明书', 'Z206作为奈伯思智能家居系统的核心网关，率先实现云端技术、WiFi移动联网技术与ZigBee智能家居物联网术的完美结合，让用户只需要通过手机APP连接Wifi即可控制家里的灯光、窗帘、各种电器等设备的开关，出门在外通过2G/3G网络可监控家中的一切变化。');
INSERT INTO `modenodelib_en` VALUES ('146', '9', 'Z501G', 'Z501G remote control', '0501', '安防控制', '3', '1', 'source', 'Z501A.jpg', 'As a remote control device, Z501G can be used to assist in controlling the security system. Meanwhile, it is equipped with an emergency button, which can be pressed to call for help in emergency.', '3V CR2032纽扣电池', '右侧开关off断电，重新上电后即可激活设备。', '按住“开关键” 的同时给设备上电，开始恢复出厂值，指示灯共闪烁20次，之后重新操作组合键就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('147', '7', 'ZA06', 'ZA06 Air Egg', '', '', '3', '1', 'dest', 'ZA06.jpg', 'ZA06 is a device tesing temperature,humidity,air quality,pm2.5,co and some other harmful gas. It can timely collect the data of temperature,humidity,air quality and CO data and send them to the CIE device where it is enrolled. With USB power, its three-color LED can be controlled at short pressing its touch button to show the current AQI value. It will show different colors based on the detected AQI value.  Green,yellow,orange,red,purple and maroon respectively stands for different AQI level. Green stands for good air quality, while other colors stand for a worser level of air quality in order. Maroon stands for the worst quality.', '5V直流电源', '不需要激活', '长按绑定键15s（网络灯闪烁3次以后，设备3s闪烁一次，10s闪烁一次，15s闪烁一次），设备开始恢复出场设置，直到网络灯一直闪烁20次（20，100，100），设备自动复位后，重新请求加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('148', '6', 'Z-D01C', 'Z-D01CToggle & Level Curtain Controller (Drape)', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z-D01BCD.jpg', 'ZD01C is a curtain controller. It can control the curtains on/off and level through its own key, binding equipments or software.', '12V4A直流电源', '不需要激活', '按住ON1和OFF1键（靠右边一列）的同时给设备上电，开始执行恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重新上电就可以重新加网了', '在设备执行窗帘控制功能前,都应先进行“学习上下限”的学习，学习方法请详见设备说明书或在app的设备管理模块，该设备的设置页面进行学习');
INSERT INTO `modenodelib_en` VALUES ('149', '6', 'Z-D01D', 'Z-D01DToggle & Level Curtain Controller (Blinds)', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z-D01BCD.jpg', 'ZD01D is a curtain controller. It can control the curtains on/off and level through its own key, binding equipments or software.', '12V4A直流电源', '不需要激活', '按住ON1和OFF1键（靠右边一列）的同时给设备上电，开始执行恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重新上电就可以重新加网了', '在设备执行窗帘控制功能前,都应先进行“学习上下限”的学习，学习方法请详见设备说明书或在app的设备管理模块，该设备的设置页面进行学习');
INSERT INTO `modenodelib_en` VALUES ('150', '0', 'Z-825F', 'Z-825FTouch Panel Programmable Scene Selector (1-Gang)', '0006', '开关', '3', '1', 'dest', 'Z825F.jpg', 'As a Scene Selector & Mode Selector，Z825F is equipped with one touch buttons. It can at most at one scene or configure four modes.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('151', '0', 'Z-825G', 'Z-825GTouch Panel Programmable Scene Selector (2-Gang)', '0006', '开关', '3', '1', 'dest', 'Z825G.jpg', 'As a Scene Selector & Mode Selector，Z825G is equipped with two touch buttons. Each can be configured to add scene or control mode (each button can at most at one scene or configure four modes.)', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('152', '0', 'Z-825H', 'Z-825HTouch Panel Programmable Scene Selector (3-Gang)', '0006', '开关', '3', '1', 'dest', 'Z825H.jpg', 'As a Scene Selector & Mode Selector，Z825H is equipped with three touch buttons. Each can be configured to add scene or control mode (each button can at most at one scene or configure four modes.)', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('153', '0', 'Z-826A', 'Z-826ATouch Panel Switch with Power Meter (1-Gang)', '0006', '开关', '3', '1', 'dest', 'Z826A.jpg', 'Z826A is one-gang touch switch with single livewire for power. It can control its on/off through being bound with  devices with on/off function to control it or through its own keys.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络青绿色指示灯闪烁一次（期间网络青绿色指示灯闪烁4次，其中刚按下闪一次，3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络青绿色指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会进入睡眠模式，短按绑定键Z826C可重新加网。 ', '无');
INSERT INTO `modenodelib_en` VALUES ('154', '11', 'Z-L01D', 'Industrial Grade IEEE 802.15.4/GPIO Adapter', '0006', '开关', '3', '1', 'dest', 'ZL01D.JPG', 'Z103AC is a coordinator with GPIO port. The devicce can communicate with other Zigbee modules through the port, and thus control the entire system.', 'GPIO口', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('155', '0', 'Z-826B', 'Z-826BTouch Panel Switch with Power Meter (2-Gang)', '0006', '开关', '3', '1', 'dest', 'Z826B.jpg', 'Z826B is two-gang touch switch with single livewire for power. It can control its on/off through being bound with  devices with on/off function to control it or through its own keys. Its two-gang on/off is controlled by two keys.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络青绿色指示灯闪烁一次（期间网络青绿色指示灯闪烁4次，其中刚按下闪一次，3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络青绿色指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会进入睡眠模式，短按绑定键Z826C可重新加网。 ', '无');
INSERT INTO `modenodelib_en` VALUES ('156', '0', 'Z-826D', 'Z-826DTouch Panel Dimmer Switch with Power Meter (1-Gang)', '0006', '开关', '3', '1', 'dest', 'Z826D.jpg', 'Z826D is one-gang dimmer switch with single livewire for power. It can control its on/off through being bound with  devices with on/off function to control it or through its own keys.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络青绿色指示灯闪烁一次（期间网络青绿色指示灯闪烁4次，其中刚按下闪一次，3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络青绿色指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会进入睡眠模式，短按绑定键Z826C可重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('157', '11', 'Z-202E', 'Z-202E IEEE 802.15.4/ TCP/IP Gateway Coordinator/Router ', '0006', '开关', '3', '1', 'dest', 'Z202E.JPG', 'Z202E is a coordinator with NET port. The devicce can communicate with other Zigbee modules through the port, and thus control the entire system.', '网口', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('158', '11', 'Z-L01A', 'Z-L01A Industrial Grade IEEE 802.15.4/RS232 Adapter', '0006', '开关', '3', '1', 'dest', 'ZL01A.JPG', 'Z-L01Ais a coordinator with RS-232 port(COM1/COM2). The devicce can communicate with other Zigbee modules through the port, and thus control the entire system.', 'RS-232端口', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('159', '11', 'Z-L01B', 'Industrial Grade IEEE 802.15.4/RS485 Adapter', '0006', '开关', '3', '1', 'dest', 'ZL01B.JPG', 'Z103AC is a coordinator with RS-485 port. The devicce can communicate with other Zigbee modules through the port, and thus control the entire system.', 'RS-485端口', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('160', '11', 'Z-L01F', 'Industrial Grade IEEE 802.15.4/RS232 Straight-Through Adapter', '0006', '开关', '3', '1', 'dest', 'ZL01F.jpg', 'Z103AC is a coordinator with RS-232 port(COM1/COM2). The devicce can communicate with other Zigbee modules through the port, and thus control the entire system.', 'RS-232端口', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('161', '11', 'Z-L01C', 'Industrial Grade IEEE 802.15.4/ TCP/IP Adapter', '0006', '开关', '3', '1', 'dest', 'ZL01C.JPG', 'Z103AC is a coordinator with NET port. The devicce can communicate with other Zigbee modules through the port, and thus control the entire system.', '网口', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('162', '11', 'Z-L01E', 'Industrial Grade IEEE 802.15.4/USB Adapter', '0006', '开关', '3', '1', 'dest', 'ZL01E.JPG', 'Z103AC is a coordinator with USB port. The devicce can communicate with other Zigbee modules through the port, and thus control the entire system.', 'USB', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');


/* 2014.12.18 */
DROP PROCEDURE IF EXISTS `ModemacrosubTest`;
DELIMITER ;;
CREATE DEFINER = `root`@`%` PROCEDURE `ModemacrosubTest`(IN `behavior` bigint,IN `Id` bigint,IN `houseId` bigint)
BEGIN 
	#Routine body goes here...
DECLARE aCount INTEGER;
IF behavior=1 THEN
		Select t.* from modeactlib t GROUP BY t.ActName,t.ExtentionSet ORDER BY t.ExtentionSet;
ELSEIF behavior=3 THEN
  Select Count(*) into aCount FROM modeactlib t WHERE t.ID=ID 
          and (t.ActName='Disarm' or t.ActName='ArmAllZone' or t.ActName='ArmDayZone'
               or t.ActName='ArmNightZone');
  if aCount=0 then    
    Select r.roomName,D.id,D.roomId,D.deviceName FROM
		(Select  t1.DeviceID,t.id from modeactlib t inner join modeactlib t1 on t.ActName=t1.ActName
		where t.ID=id group by t1.DeviceID) m 	
		inner join modedevice d on m.DeviceID=d.deviceId
    inner join modenodelib nodeL on d.modelId=nodeL.modelId or LOCATE(nodeL.modelId,d.modelId)=1
		inner join modeeplib EPL on EPL.nodeId=nodeL.id and EPL.ep=d.ep
		inner join moderoom r on d.roomId=r.roomId and d.houseId=r.houseId
		where  ((m.ID=16 and (d.modelID='Z210' or d.modelId='Z211')) or  m.ID<>16) and d.houseId=houseId ORDER BY d.roomId;
  else
    select * from (Select r.roomName,D.id,D.roomId,D.deviceName FROM
		(Select  t1.DeviceID,t.id from modeactlib t inner join modeactlib t1 on t.ActName=t1.ActName
		where t.ID=id group by t1.DeviceID) m 	
		inner join modedevice d on m.DeviceID=d.deviceId
    inner join modenodelib nodeL on d.modelId=nodeL.modelId or LOCATE(nodeL.modelId,d.modelId)=1 
		inner join modeeplib EPL on EPL.nodeId=nodeL.id and EPL.ep=d.ep
		inner join moderoom r on d.roomId=r.roomId and d.houseId=r.houseId
		where ((m.ID=16 and (d.modelID='Z210' or d.modelId='Z211')) or  m.ID<>16) and d.houseId=houseId
    UNION
    Select r.roomName,D.id,D.roomId,D.deviceName FROM modedevice d     
    inner join modenodelib nodeL on d.modelId=nodeL.modelId or LOCATE(nodeL.modelId,d.modelId)=1
		inner join modeeplib EPL on EPL.nodeId=nodeL.id and d.ep=epl.ep  
		inner join moderoom r on d.roomId=r.roomId and d.houseId=r.houseId
		where d.houseId=HouseID and (nodeL.ID=86 or nodeL.ID=145)) Main order by Main.RoomID;
  end if;

ELSEIF behavior=2 THEN
		SELECT attr.*,MIN(attr.ID) from deviceattrlib attr GROUP BY attr.ClusterID, attr.AttrID;
ELSEIF behavior=4 THEN
  Select Count(*) into aCount FROM modeactlib t WHERE t.ID=ID 
          and (t.ActName='Disarm' or t.ActName='ArmAllZone' or t.ActName='ArmDayZone'
               or t.ActName='ArmNightZone');
  if aCount=0 then    
    Select r.roomName,D.id,D.roomId,D.deviceName from modedevice d 
    inner join modenodelib nodeL on d.modelId=nodeL.modelId or LOCATE(nodeL.modelId,d.modelId)=1		
		inner join modeeplib EPL on EPL.nodeId=nodeL.id and EPL.ep=d.ep
		inner join deviceattrlib attr on attr.ModelID=nodeL.modelId and attr.DeviceID=EPL.deviceId
		inner join deviceattrlib attr1 on attr.ClusterID=attr1.ClusterID and Attr.AttrID=attr1.AttrID
		inner join moderoom r on d.roomId=r.roomId and d.houseId=r.houseId
		where attr1.ID=id and r.houseId=houseId ORDER BY d.roomId;
  else
    select * from (Select r.roomName,D.id,D.roomId,D.deviceName from modedevice d 
    inner join modenodelib nodeL on d.modelId=nodeL.modelId or LOCATE(nodeL.modelId,d.modelId)=1		
		inner join modeeplib EPL on EPL.nodeId=nodeL.id and EPL.ep=d.ep
		inner join deviceattrlib attr on attr.ModelID=nodeL.modelId and attr.DeviceID=EPL.deviceId
		inner join deviceattrlib attr1 on attr.ClusterID=attr1.ClusterID and Attr.AttrID=attr1.AttrID
		inner join moderoom r on d.roomId=r.roomId and d.houseId=r.houseId
		where attr1.ID=id and r.houseId=houseId 
    UNION
    Select r.roomName,D.id,D.roomId,D.deviceName FROM modedevice d     
    inner join modenodelib nodeL on d.modelId=nodeL.modelId or LOCATE(nodeL.modelId,d.modelId)=1
		inner join modeeplib EPL on EPL.nodeId=nodeL.id and d.deviceId=EPL.deviceId and d.ep=epl.ep  
		inner join moderoom r on d.roomId=r.roomId and d.houseId=r.houseId
		where d.houseId=HouseID and (nodeL.ID=86 or nodeL.ID=145)) Main order by Main.RoomID;
  end if;

ELSEIF behavior=5 THEN
	Select m.id,m.GroupName as vName 
		from modegroupmain m where m.houseId = houseId;		
ELSEIF behavior=6 THEN	
	Select d.roomId,d.deviceName,m.GroupName,r.roomName,u.id,u.mid,u.DeviceID
		from Modegroupsub u inner join Modegroupmain m on u.mid=m.id
												inner join Modedevice d on u.deviceId=d.id
												inner join Moderoom r on d.houseId=r.houseId and d.roomId=r.roomId 
		where m.houseId = houseId and m.id=id ORDER BY r.roomId;
ELSEIF behavior=7 THEN
	if (id=0) then 
		Select Act.* 
			from modeactlib act WHERE act.Groupabled=1 
			group by act.ActName,act.ExtentionSet ORDER BY Act.ExtentionSet;
	else
		Select Act2.* from modegroupmain m
			inner join modegroupsub gsub on m.id=gsub.MID
			inner join Modedevice d on d.Id=gsub.DeviceID
			inner join modeactlib act on  act.DeviceID=d.deviceId 
      inner join modeactlib act2 on act.grouplevel=act2.GroupLevel and act.ExtentionSet=act2.ExtentionSet 
		where m.ID=id group by act2.ActName,act2.ExtentionSet ORDER BY Act2.ExtentionSet;	
	end if;
ELSEIF behavior=8 THEN
	Select d.id as deviceId,d.roomId,d.deviceName,r.roomName
	from(Select act2.DeviceID from modeactlib act1 inner join modeactlib act2 on act1.grouplevel=act2.GroupLevel and act1.ExtentionSet=act2.ExtentionSet
			where Act1.id=id group by act2.deviceid) act3
	inner join Modedevice d on d.deviceId=act3.DeviceID  
	inner join Moderoom r on d.roomId=r.roomId and d.houseId=r.houseId
	where d.houseId=houseId ORDER BY r.roomId;

ELSEIF behavior=9 THEN
	Select m.id,m.GroupName as vName 
		from modescenemain m where m.houseId = houseId;		
ELSEIF behavior=10 THEN
	Select d.roomId,d.deviceName,m.GroupName,act.UniqueName,act.DataType,r.roomName,u.id,u.mid,u.DeviceID,u.actlibID,u.TransTime
		from modescenesub u inner join modescenemain m on u.mid=m.id
												inner join Modedevice d on u.deviceId=d.id
												inner join modeactlib act on u.actlibID=act.id 
												inner join Moderoom r on d.houseId=r.houseId and d.roomId=r.roomId 
		where m.houseId = houseId and m.id=id ORDER BY r.roomId;
ELSEIF behavior=11 THEN
	Select Act2.* from Modedevice d
		inner join modeactlib act on act.DeviceID=d.deviceId 
		inner join modeactlib act2 on act.grouplevel=act2.GroupLevel and act.ExtentionSet=act2.ExtentionSet 
		where d.ID=id and act2.Sceneabled=1 group by act2.ActName,act2.ExtentionSet ORDER BY Act2.ExtentionSet;
ELSEIF behavior=12 THEN
	Select d.id as deviceId,d.roomId,d.deviceName,r.roomName 
	from(Select act1.DeviceID from modeactlib act1 
			where act1.Sceneabled=1 group by act1.deviceid) act3
	inner join Modedevice d on d.deviceId=act3.DeviceID  
	inner join Moderoom r on d.roomId=r.roomId and d.houseId=r.houseId
	where d.houseId=houseId ORDER BY r.roomId;
END IF;
END;
;;
DELIMITER ;

/*2014-12-19*/
ALTER TABLE `brinhouse`
MODIFY COLUMN `wrongResult`  text(0) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ;
ALTER TABLE `brinhousehistory`
MODIFY COLUMN `wrongResult`  text(0) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ;
/*2014-12-19*/
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for modeeplib
-- ----------------------------
DROP TABLE IF EXISTS `modeeplib`;
CREATE TABLE `modeeplib` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nodeId` bigint(20) DEFAULT NULL,
  `deviceId` varchar(20) DEFAULT NULL,
  `internelModelId` varchar(50) DEFAULT NULL,
  `deviceName` varchar(200) DEFAULT NULL,
  `clusterId` varchar(200) DEFAULT NULL,
  `clusterName` varchar(100) DEFAULT NULL,
  `picName` varchar(100) DEFAULT NULL,
  `destType` varchar(4) DEFAULT NULL,
  `deviceType` varchar(20) DEFAULT NULL,
  `deviceTypeV2` bigint(20) DEFAULT '1',
  `ep` varchar(50) DEFAULT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `Groupable` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `nodeId` (`nodeId`) USING BTREE,
  KEY `deviceId` (`deviceId`) USING BTREE,
  KEY `internelModelId` (`internelModelId`) USING BTREE,
  KEY `deviceName` (`deviceName`) USING BTREE,
  KEY `destType` (`destType`) USING BTREE,
  KEY `deviceType` (`deviceType`) USING BTREE,
  KEY `clusterId` (`clusterId`) USING BTREE,
  KEY `Groupable` (`Groupable`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=271 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of modeeplib
-- ----------------------------
INSERT INTO `modeeplib` VALUES ('2', '2', '0009', 'Z815I-1', 'Z815I一路墙面电能检测开关', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib` VALUES ('3', '3', '0009', 'Z815K-1', 'Z815K一路墙面电能检测开关', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib` VALUES ('4', '3', '0009', 'Z815K-2', 'Z815K二路墙面电能检测开关', '0006', '开关', null, '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib` VALUES ('5', '3', '0009', 'Z815K-3', 'Z815K三路墙面电能检测开关', '0006', '开关', null, '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib` VALUES ('6', '4', '0100', 'Z806-1', 'Z806一路嵌墙开关', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib` VALUES ('7', '4', '0100', 'Z806-2', 'Z806二路嵌墙开关', '0006', '开关', null, '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib` VALUES ('8', '5', '0000', 'ZB02A-1', 'ZB02A壁挂无线按键开关一', '0006', '开关', null, '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib` VALUES ('10', '7', '0000', 'Z503-2', 'Z503开关控制按键', '0006:0008', '开关:调级控制', null, '1', 'source', '1', '0F', '', '0');
INSERT INTO `modeeplib` VALUES ('11', '7', '0006', 'Z503-1', 'Z503安防控制按键', '', '', null, '3', '', '1', '0A', '', '0');
INSERT INTO `modeeplib` VALUES ('12', '10', '0009', 'Z805A-1', 'Z805A一路嵌墙开关', '0006', '开关', null, '3', 'dest', '1', '0A', null, '1');
INSERT INTO `modeeplib` VALUES ('13', '11', '0009', 'Z805B-1', 'Z805B一路嵌墙开关', '0006', '开关', '', '3', 'dest', '1', '0A', '', '1');
INSERT INTO `modeeplib` VALUES ('14', '12', '0009', 'Z810B-1', 'Z810B电能检测开关', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib` VALUES ('15', '13', '0100', 'Z811-1', 'Z811一路开关', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib` VALUES ('16', '13', '0100', 'Z811-2', 'Z811二路开关', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib` VALUES ('17', '13', '0100', 'Z811-3', 'Z811三路开关', '0006', '开关', '', '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib` VALUES ('18', '13', '0100', 'Z811-4', 'Z811四路开关', '0006', '开关', '', '3', 'dest', '1', '04', '', '1');
INSERT INTO `modeeplib` VALUES ('19', '14', '0009', 'Z815J-1', 'Z815J一路墙面电能检测开关', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('20', '14', '0009', 'Z815J-2', 'Z815J二路墙面电能检测开关', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib` VALUES ('21', '15', '0101', 'Z815L-1', 'Z815L一路墙面电能检测调光开关', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('22', '16', '0101', 'Z815M-1', 'Z815M一路墙面电能检测调光开关', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('23', '16', '0101', 'Z815M-2', 'Z815M二路墙面电能检测调光开关', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib` VALUES ('24', '17', '0009', 'Z817A-1', 'Z817A一路吸顶电能检测开关', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('25', '18', '0101', 'Z817B-1', 'Z817B一路吸顶电能检测调光开关\n', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('26', '19', '0002', 'Z817C-1', 'Z817C一路吸顶红外感应型开关', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('27', '20', '0009', 'Z825C-1', 'Z825C一路墙面电能检测触摸开关', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('28', '20', '0009', 'Z825C-2', 'Z825C二路墙面电能检测触摸开关', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib` VALUES ('29', '20', '0009', 'Z825C-3', 'Z825C三路墙面电能检测触摸开关', '0006', '开关', '', '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib` VALUES ('30', '21', '0101', 'Z825E-1', 'Z825E一路电能检测触摸调光开关', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('31', '21', '0101', 'Z825E-2', 'Z825E二路电能检测触摸调光开关', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib` VALUES ('32', '22', '0101', 'ZC06-1', 'ZC06LED调光灯管', '0006:0008', '开关:调级控制', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib` VALUES ('33', '23', '0009', 'Z800-1', 'Z800电能检测插座', '0006', '开关', '', '3', 'dest', '1', '0A', '', '1');
INSERT INTO `modeeplib` VALUES ('34', '24', '0002', 'Z803-1', 'Z803一路排插', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('35', '24', '0002', 'Z803-2', 'Z803二路排插\n', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib` VALUES ('36', '24', '0002', 'Z803-3', 'Z803三路排插', '0006', '开关', '', '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib` VALUES ('37', '24', '0002', 'Z803-4', 'Z803四路排插', '0006', '开关', null, '3', 'dest', '1', '04', null, '1');
INSERT INTO `modeeplib` VALUES ('38', '24', '000D', 'Z803-5', 'Z803排插电能统计', '0006', '开关', null, '3', '', '1', '05', null, '0');
INSERT INTO `modeeplib` VALUES ('39', '25', '0009', 'Z808A-1', 'Z808A带USB电能检测插座', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('40', '26', '0101', 'Z808B-1', 'Z808B带USB电能检测调光插座', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('41', '27', '0009', 'Z809A-1', 'Z809A无线耗能检测插座', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('42', '28', '0101', 'Z809B-1', 'Z809B电能检测调光插座', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('43', '29', '0009', 'Z816B-1', 'Z816B美规电能检测墙面插座', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('44', '30', '0009', 'Z816G-1', 'Z816G英规电能检测墙面插座', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('45', '31', '0009', 'Z816H-1', 'Z816H中规电能检测墙面插座', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('46', '32', '0302', 'Z711-1', 'Z711室内温湿度探测器', null, null, null, null, null, '1', '0A', null, '0');
INSERT INTO `modeeplib` VALUES ('47', '33', '0302', 'Z712-1', 'Z712户外温湿度探测器', '', '', '', '', '', '1', '0A', '', '0');
INSERT INTO `modeeplib` VALUES ('48', '34', '0302', 'Z713-1', 'Z713户外温湿度探测器', '', '', null, '', '', '1', '0A', null, '0');
INSERT INTO `modeeplib` VALUES ('49', '35', '0302', 'Z716A-1', 'Z716A无线室内温湿度感应器', '', '', '', '', '', '1', '0A', '', '0');
INSERT INTO `modeeplib` VALUES ('50', '36', '0302', 'Z716B-1', 'Z716B室内带LCD温度探测器', null, null, '', null, null, '1', '0A', '', '0');
INSERT INTO `modeeplib` VALUES ('51', '37', '0103', 'Z302B-1', 'Z302B灯控光感器', '0006', '开关', null, '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('52', '38', '0104', 'Z302H-1', 'Z302H调光器 ', '0006:0008', '开关:调级控制', null, '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('53', '39', '0106', 'Z302G-1', 'Z302G光线感应器', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('54', '40', '0103', 'Z311B-1', 'Z311B灯控光感器', '0006', '开关', null, '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('55', '41', '0104', 'Z311H-1', 'Z311H调光器 ', '0006:0008', '开关:调级控制', null, '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('56', '42', '0106', 'Z311G-1', 'Z311G光线感应器', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib` VALUES ('57', '44', '0000', 'ZB02B-1', 'ZB02B壁挂无线按键开关一', '0006', '开关', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib` VALUES ('58', '44', '0000', 'ZB02B-2', 'ZB02B壁挂无线按键开关二', '0006', '开关', '', '3', 'source', '1', '02', '', '0');
INSERT INTO `modeeplib` VALUES ('59', '45', '0000', 'ZB02C-1', 'ZB02C无线墙面控制开关一路', '0006', '开关', '', '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('60', '45', '0000', 'ZB02C-2', 'ZB02C无线墙面控制开关二路', '0006', '开关', '', '3', 'source', '1', '02', null, '0');
INSERT INTO `modeeplib` VALUES ('61', '45', '0000', 'ZB02C-3', 'ZB02C无线墙面控制开关三路', '0006', '开关', '', '3', 'source', '1', '03', '', '0');
INSERT INTO `modeeplib` VALUES ('62', '46', '0104', 'ZB02F-1', 'ZB02F壁挂无线按键调光开关', '0006:0008', '开关:调级控制', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib` VALUES ('63', '48', '0000', 'ZB01B-1', 'ZB01B动作感应器开关', '0006', '开关', null, '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('64', '49', '0402', 'ZB01C-1', 'ZB01C安防动作感应器', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('65', '52', '0000', 'ZB11B-1', 'ZB11B无线红外人体感应器', '0006', '开关', null, '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('66', '53', '0402', 'ZB11C-1', 'ZB11C安防动作感应器', null, null, '', null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('67', '55', '0200', 'Z815N-1', 'Z815N窗帘墙面开关', '0006:0008', '开关:调级控制', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib` VALUES ('68', '56', '0200', 'ZD01B-1', 'ZD01B一路窗帘控制器', '0006:0008', '开关:调级控制', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib` VALUES ('69', '56', '0200', 'ZD01B-2', 'ZD01B二路窗帘控制器', '0006:0008', '开关:调级控制', null, '3', 'dest', '1', '02', null, '1');
INSERT INTO `modeeplib` VALUES ('71', '47', '0402', 'ZB01A-1', 'ZB01A安防动作感应器', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('72', '50', '0107', 'ZB01D-1', 'ZB01D红外占有感应器', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('73', '51', '0402', 'ZB11A-1', 'ZB11A安防动作感应器', '', '', null, '', '', '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('74', '49', '0000', 'ZB01C-2', 'ZB01C动作感应开关', '0006', '开关', null, '3', 'source', '1', '02', null, '0');
INSERT INTO `modeeplib` VALUES ('75', '49', '0302', 'ZB01C-3', 'ZB01C温度感应器', null, null, null, null, null, '1', '03', null, '0');
INSERT INTO `modeeplib` VALUES ('76', '53', '0000', 'ZB11C-2', 'ZB11C动作感应开关', '0006', '开关', null, '3', 'source', '1', '02', null, '0');
INSERT INTO `modeeplib` VALUES ('77', '53', '0302', 'ZB11C-3', 'ZB11C温度感应器', null, null, '', null, null, '1', '03', null, '0');
INSERT INTO `modeeplib` VALUES ('78', '54', '0107', 'ZB11D-1', 'ZB11D红外占用感应器', '', '', null, '', '', '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('79', '57', '0402', 'Z302A-1', 'Z302A窗磁感应器', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('80', '58', '0402', 'Z302C-1', 'Z302C窗户感应器', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('81', '59', '0402', 'Z302D-1', 'Z302D紧急报警器', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('82', '60', '0402', 'Z302E-1', 'Z302E物品标签', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('83', '61', '0000', 'Z302J-1', 'Z302J门磁感应开关', '0006', '开关', '', '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('84', '61', '0402', 'Z302J-2', 'Z302J安防门磁感应', '', '', '', '', '', '1', '02', null, '0');
INSERT INTO `modeeplib` VALUES ('85', '62', '0402', 'Z306D-1', 'Z306D定位紧急按钮', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('86', '63', '0402', 'Z311A-1', 'Z311A窗磁感应器', null, '', '', '', '', '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('87', '64', '0402', 'Z311C-1', 'Z311C窗户感应器', null, '', '', '', '', '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('88', '65', '0401', 'Z312-1', 'Z312无线门铃按键', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('89', '66', '0402', 'Z307-1', 'Z307跌倒感应器', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('90', '67', '0402', 'Z308-1', 'Z308无线紧急按钮', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('91', '68', '0402', 'ZA01A-1', 'ZA01A烟雾探测器', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('92', '69', '0402', 'ZA01B-1', 'ZA01B瓦斯感应器', '', '', null, '', '', '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('93', '70', '0402', 'ZA01C-1', 'ZA01C一氧化碳感应器', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('94', '71', '0402', 'ZA01D-1', 'ZA01D液化石油气感应器', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('95', '72', '0402', 'ZA02E-1', 'ZA02E烟雾感应器', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('96', '73', '0002', 'ZA10-1', 'ZA10智能阀门', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib` VALUES ('97', '74', '000A', 'ZB05-1', 'ZB05智能门锁', '0101', '开关锁', null, '3', 'dest', '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('98', '75', '0401', 'ZB02E-1', 'ZB02E门铃按键', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('99', '76', '0402', 'Z311E-1', 'Z311E物品标签', '', '', null, '', '', '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('100', '77', '0403', 'Z602A-1', 'Z602A智能声光报警器', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('101', '78', '0403', 'Z602B-1', 'Z602B警报器', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('102', '79', '0403', 'Z601A-1', 'Z601A警报器', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('103', '80', '000D', 'Z821-1', 'Z821一路电能统计', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('104', '80', '000D', 'Z821-2', 'Z821二路电能统计', null, null, null, null, null, '1', '02', null, '0');
INSERT INTO `modeeplib` VALUES ('105', '80', '000D', 'Z821-3', 'Z821三路电能统计', null, null, null, null, null, '1', '03', null, '0');
INSERT INTO `modeeplib` VALUES ('106', '80', '000D', 'Z821-4', 'Z821四路电能统计', null, null, null, null, null, '1', '04', null, '0');
INSERT INTO `modeeplib` VALUES ('107', '80', '000D', 'Z821-5', 'Z821五路电能统计', null, null, null, null, null, '1', '05', null, '0');
INSERT INTO `modeeplib` VALUES ('108', '80', '000D', 'Z821-6', 'Z821六路电能统计', null, null, null, null, null, '1', '06', null, '0');
INSERT INTO `modeeplib` VALUES ('109', '80', '000D', 'Z821-7', 'Z821七路电能统计', null, null, null, null, null, '1', '07', null, '0');
INSERT INTO `modeeplib` VALUES ('110', '80', '000D', 'Z821-8', 'Z821一至七路总电能统计', null, null, null, null, null, '1', '08', null, '0');
INSERT INTO `modeeplib` VALUES ('111', '81', '0001', 'Z501A-1', 'Z501A调级开关控制按键(250)', '0006:0008', '开关:调级控制', null, '3', 'source', '1', '0A', null, '0');
INSERT INTO `modeeplib` VALUES ('112', '81', '0401', 'Z501A-2', 'Z501A安防控制按键(250)', '', '', null, '', '', '1', '0B', null, '0');
INSERT INTO `modeeplib` VALUES ('113', '82', '0000', 'Z501B-1', 'Z501B开关控制按键一(250)', '0006', '开关', null, '3', 'source', '1', '0A', null, '0');
INSERT INTO `modeeplib` VALUES ('114', '82', '0000', 'Z501B-2', 'Z501B开关控制按键二(250)', '0006', '开关', null, '3', 'source', '1', '0B', null, '0');
INSERT INTO `modeeplib` VALUES ('115', '83', '0001', 'Z501C-1', 'Z501C调级开关控制按键(250)', '0006:0008', '开关:调级控制', null, '3', 'source', '1', '0A', null, '0');
INSERT INTO `modeeplib` VALUES ('116', '83', '0000', 'Z501C-2', 'Z501C开关控制按键(250)', '0006', '开关', null, '3', 'source', '1', '0B', null, '0');
INSERT INTO `modeeplib` VALUES ('117', '84', '0008', 'Z210C-1', 'Z210C红外线转接器', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('118', '85', '0008', 'Z211-1', 'Z211智能红外控制转换器', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('119', '86', '0007', 'Z203-1', 'Z203云智能网关', null, null, null, null, null, '1', '0A', null, '0');
INSERT INTO `modeeplib` VALUES ('120', '87', '0007', 'Z103AC-1', 'Z103ACUSB协调器', null, null, null, null, null, '1', '0A', null, '0');
INSERT INTO `modeeplib` VALUES ('121', '88', '0007', 'Z103AR-1', 'Z103ARUSB路由器', null, null, null, null, null, '1', '0A', null, '0');
INSERT INTO `modeeplib` VALUES ('122', '89', '0400', 'Z201B-1', 'Z201BHA 协调器+CIE', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('123', '90', '0400', 'Z201C-1', 'Z201CHA 协调器', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('124', '91', '0008', 'Z201R HA-1', 'Z201RHA中继器', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('125', '92', '0008', 'Z201R-1', 'Z201R定位用参考点', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('126', '93', '0002', 'Z812A-1', 'Z812A一路开关输出', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib` VALUES ('127', '93', '0000', 'Z812A-2', 'Z812A按键开关一', '0006', '开关', null, '3', 'source', '1', '02', null, '0');
INSERT INTO `modeeplib` VALUES ('128', '93', '0000', 'Z812A-3', 'Z812A按键开关二', '0006', '开关', null, '3', 'source', '1', '03', null, '0');
INSERT INTO `modeeplib` VALUES ('129', '93', '0000', 'Z812A-4', 'Z812A按键开关三', '0006', '开关', null, '3', 'source', '1', '04', null, '0');
INSERT INTO `modeeplib` VALUES ('130', '94', '0000', 'Z812B-1', 'Z812B按键开关一', '0006', '开关', null, '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('131', '94', '0000', 'Z812B-2', 'Z812B按键开关二', '0006', '开关', null, '3', 'source', '1', '02', null, '0');
INSERT INTO `modeeplib` VALUES ('132', '94', '0000', 'Z812B-3', 'Z812B按键开关三', '0006', '开关', null, '3', 'source', '1', '03', null, '0');
INSERT INTO `modeeplib` VALUES ('133', '94', '0000', 'Z812B-4', 'Z812B按键开关四', '0006', '开关', null, '3', 'source', '1', '04', null, '0');
INSERT INTO `modeeplib` VALUES ('134', '94', '0104', 'Z812B-5', 'Z812B按键调光开关一', '0006:0008', '开关:调级控制', null, '3', 'source', '1', '05', null, '0');
INSERT INTO `modeeplib` VALUES ('135', '94', '0104', 'Z812B-6', 'Z812B按键调光开关二', '0006:0008', '开关:调级控制', null, '3', 'source', '1', '06', null, '0');
INSERT INTO `modeeplib` VALUES ('136', '95', '0402', 'Z801TXB-1', 'Z801TXB一路脉冲信号探测', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib` VALUES ('137', '95', '0402', 'Z801TXB-2', 'Z801TXB二路脉冲信号探测', '', '', '', '', '', '1', '02', '', '0');
INSERT INTO `modeeplib` VALUES ('138', '95', '0402', 'Z801TXB-3', 'Z801TXB三路脉冲信号探测', '', '', '', '', '', '1', '03', '', '0');
INSERT INTO `modeeplib` VALUES ('139', '95', '0402', 'Z801TXB-4', 'Z801TXB四路脉冲信号探测', '', '', '', '', '', '1', '04', '', '0');
INSERT INTO `modeeplib` VALUES ('140', '95', '0402', 'Z801TXB-5', 'Z801TXB五路脉冲信号探测', '', '', '', '', '', '1', '05', '', '0');
INSERT INTO `modeeplib` VALUES ('141', '96', '0002', 'Z801RX', 'Z801RX弱电继电器', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib` VALUES ('142', '101', '0009', 'Z825A-1', 'Z825A一路触控式墙面开关', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib` VALUES ('143', '102', '0009', 'Z825B-1', 'Z825B一路触控式墙面开关', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib` VALUES ('144', '102', '0009', 'Z825B-2', 'Z825B二路触控式墙面开关', '0006', '开关', null, '3', 'dest', '1', '02', null, '1');
INSERT INTO `modeeplib` VALUES ('145', '103', '0101', 'Z825D-1', 'Z825D一路触控式墙面调光开关', '0006:0008', '开关:调级控制', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib` VALUES ('146', '100', '0400', 'Z508-1', 'Z508LCD资讯显示屏', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('147', '104', '0302', 'Z725A-1', 'Z725A户外温湿度探测器', null, null, null, null, null, '1', '0A', null, '0');
INSERT INTO `modeeplib` VALUES ('148', '106', '0008', 'Z725R-1', 'Z725R户外型网络中继器', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('149', '107', '0009', 'Z962A-1', 'Z962A一路触控式墙面开关', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib` VALUES ('150', '108', '0009', 'Z962B-1', 'Z962B一路触控式墙面开关', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib` VALUES ('151', '108', '0009', 'Z962B-2', 'Z962B二路触控式墙面开关', '0006', '开关', null, '3', 'dest', '1', '02', null, '1');
INSERT INTO `modeeplib` VALUES ('152', '109', '0009', 'Z962C-1', 'Z962C一路触控式墙面开关', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib` VALUES ('153', '109', '0009', 'Z962C-2', 'Z962C二路触控式墙面开关', '0006', '开关', null, '3', 'dest', '1', '02', null, '1');
INSERT INTO `modeeplib` VALUES ('154', '109', '0009', 'Z962C-3', 'Z962C三路触控式墙面开关', '0006', '开关', null, '3', 'dest', '1', '03', null, '1');
INSERT INTO `modeeplib` VALUES ('155', '110', '0004', 'Z962D-1', 'Z962D触摸情景开关按键一', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('156', '111', '0004', 'Z962E-1', 'Z962E触摸情景开关按键一', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('157', '111', '0004', 'Z962E-2', 'Z962E触摸情景开关按键二', null, null, null, null, null, '1', '02', null, '0');
INSERT INTO `modeeplib` VALUES ('158', '112', '0004', 'Z962F-1', 'Z962F触摸情景开关按键一', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('159', '112', '0004', 'Z962F-2', 'Z962F触摸情景开关按键二', null, null, null, null, null, '1', '02', null, '0');
INSERT INTO `modeeplib` VALUES ('160', '112', '0004', 'Z962F-3', 'Z962F触摸情景开关按键三', null, null, null, null, null, '1', '03', null, '0');
INSERT INTO `modeeplib` VALUES ('161', '113', '0009', 'Z962G-1', 'Z962G一路触控式墙面开关', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib` VALUES ('162', '113', '0004', 'Z962G-2', 'Z962G触摸情景开关按键一', null, null, null, null, null, '1', '02', null, '0');
INSERT INTO `modeeplib` VALUES ('163', '114', '0009', 'Z962H-1', 'Z962H一路触控式墙面开关', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib` VALUES ('164', '114', '0004', 'Z962H-2', 'Z962H触摸情景开关按键一', null, null, null, null, null, '1', '02', null, '0');
INSERT INTO `modeeplib` VALUES ('165', '114', '0004', 'Z962H-3', 'Z962H触摸情景开关按键二', null, null, null, null, null, '1', '03', null, '0');
INSERT INTO `modeeplib` VALUES ('166', '115', '0004', 'Z962I-1', 'Z962I触摸情景开关按键一', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('167', '115', '0009', 'Z962I-2', 'Z962I一路触控式墙面开关', '0006', '开关', null, '3', 'dest', '1', '02', null, '1');
INSERT INTO `modeeplib` VALUES ('168', '115', '0009', 'Z962I-3', 'Z962I二路触控式墙面开关', '0006', '开关', null, '3', 'dest', '1', '03', null, '1');
INSERT INTO `modeeplib` VALUES ('174', '116', '0402', 'Z801WLS-1', 'Z801WLS一路水浸报警', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib` VALUES ('175', '116', '0402', 'Z801WLS-2', 'Z801WLS二路水浸报警', '', '', '', '', '', '1', '02', '', '0');
INSERT INTO `modeeplib` VALUES ('176', '116', '0402', 'Z801WLS-3', 'Z801WLS三路水浸报警', '', '', '', '', '', '1', '03', '', '0');
INSERT INTO `modeeplib` VALUES ('177', '116', '0402', 'Z801WLS-4', 'Z801WLS四路水浸报警', '', '', '', '', '', '1', '04', '', '0');
INSERT INTO `modeeplib` VALUES ('178', '116', '0402', 'Z801WLS-5', 'Z801WLS五路水浸报警', '', '', '', '', '', '1', '05', '', '0');
INSERT INTO `modeeplib` VALUES ('179', '117', '000A', 'ZB05A-1', 'ZB05A智能门锁', '0101', '开关锁', '', '3', 'dest', '1', '01', '', '0');
INSERT INTO `modeeplib` VALUES ('180', '118', '0000', 'Z801TX-1', 'Z801TX开关控制一', '0006', '开关', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib` VALUES ('181', '118', '0000', 'Z801TX-2', 'Z801TX开关控制二', '0006', '开关', '', '3', 'source', '1', '02', '', '0');
INSERT INTO `modeeplib` VALUES ('182', '118', '0000', 'Z801TX-3', 'Z801TX开关控制三', '0006', '开关', '', '3', 'source', '1', '03', '', '0');
INSERT INTO `modeeplib` VALUES ('183', '118', '0000', 'Z801TX-4', 'Z801TX开关控制四', '0006', '开关', '', '3', 'source', '1', '04', '', '0');
INSERT INTO `modeeplib` VALUES ('184', '118', '0000', 'Z801TX-5', 'Z801TX开关控制五', '0006', '开关', '', '3', 'source', '1', '05', '', '0');
INSERT INTO `modeeplib` VALUES ('185', '119', '0000', 'Z311J-1', 'Z311J无线窗磁感应器开关', '0006', '开关', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib` VALUES ('186', '119', '0402', 'Z311J-2', 'Z311J无线安防窗磁感应器', '', '', '', '', '', '1', '02', '', '0');
INSERT INTO `modeeplib` VALUES ('187', '120', '0101', 'ZC07-1', 'ZC07无线可调光LED灯泡', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('188', '121', '0402', 'Z311W-1', 'Z311W二路水浸报警', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib` VALUES ('190', '122', '0200', 'Z811B-1', 'Z811B窗帘机控制器一路', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('191', '122', '0200', 'Z811B-2', 'Z811B窗帘机控制器二路', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib` VALUES ('192', '123', '0004', 'Z825I-1', 'Z825I触摸情景开关按键一', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib` VALUES ('193', '123', '0004', 'Z825I-2', 'Z825I触摸情景开关按键二', '', '', '', '', '', '1', '02', '', '0');
INSERT INTO `modeeplib` VALUES ('194', '123', '0004', 'Z825I-3', 'Z825I触摸情景开关按键三', '', '', '', '', '', '1', '03', '', '0');
INSERT INTO `modeeplib` VALUES ('195', '123', '0004', 'Z825I-4', 'Z825I触摸情景开关按键四', '', '', '', '', '', '1', '04', '', '0');
INSERT INTO `modeeplib` VALUES ('196', '123', '0004', 'Z825I-5', 'Z825I触摸情景开关按键五', '', '', '', '', '', '1', '05', '', '0');
INSERT INTO `modeeplib` VALUES ('197', '123', '0004', 'Z825I-6', 'Z825I触摸情景开关按键六', '', '', '', '', '', '1', '06', '', '0');
INSERT INTO `modeeplib` VALUES ('201', '124', '0004', 'Z825J-4', 'Z825J触摸情景开关按键一', '', '', '', '', '', '1', '04', '', '0');
INSERT INTO `modeeplib` VALUES ('202', '124', '0004', 'Z825J-5', 'Z825J触摸情景开关按键二', '', '', '', '', '', '1', '05', '', '0');
INSERT INTO `modeeplib` VALUES ('203', '124', '0004', 'Z825J-6', 'Z825J触摸情景开关按键三', '', '', '', '', '', '1', '06', '', '0');
INSERT INTO `modeeplib` VALUES ('204', '124', '0009', 'Z825J-1', 'Z825J开关输出一路', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('205', '124', '0009', 'Z825J-2', 'Z825J开关输出二路', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib` VALUES ('206', '124', '0009', 'Z825J-3', 'Z825J开关输出三路', '0006', '开关', '', '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib` VALUES ('207', '125', '0009', 'Z825Q-1', 'Z825Q一路墙面电能检测开关', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('208', '125', '0009', 'Z825Q-2', 'Z825Q二路墙面电能检测开关', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib` VALUES ('209', '125', '0009', 'Z825Q-3', 'Z825Q三路墙面电能检测开关', '0006', '开关', '', '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib` VALUES ('210', '126', '0009', 'Z816I-1', 'Z816I中规电能检测墙面插座', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('211', '127', '0000', 'Z817D-1', 'Z817D动作感应器开关', '0006', '开关', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib` VALUES ('212', '128', '0008', 'Z800R-1', 'Z800R带插座无线中继路由器', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('213', '129', '0008', 'Z809C-1', 'Z809C带插座无线中继路由器', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('214', '130', '0008', 'Z809D-1', 'Z809D带插座无线中继路由器', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib` VALUES ('215', '131', '0001', 'Z501AE3ED-1', 'Z501A调级开关控制按键(357)', '0006:0008', '开关:调级控制', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib` VALUES ('216', '131', '0401', 'Z501AE3ED-2', 'Z501A安防控制按键(357)', '', '', '', '', '', '1', '02', '', '0');
INSERT INTO `modeeplib` VALUES ('217', '132', '0000', 'Z501BE3ED-1', 'Z501B开关控制按键一(357)', '0006', '开关', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib` VALUES ('218', '132', '0000', 'Z501BE3ED-2', 'Z501B开关控制按键二(357)', '0006', '开关', '', '3', 'source', '1', '02', '', '0');
INSERT INTO `modeeplib` VALUES ('219', '133', '0001', 'Z501CE3ED-1', 'Z501C调级开关控制按键(357)', '0006:0008', '开关:调级控制', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib` VALUES ('220', '133', '0000', 'Z501CE3ED-2', 'Z501C开关控制按键(357)', '0006', '开关', '', '3', 'source', '1', '02', '', '0');
INSERT INTO `modeeplib` VALUES ('221', '134', '0402', 'ZB02I-1', 'ZB02I无线墙面紧急按键', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib` VALUES ('222', '137', '0002', 'Z802-1', 'Z802一路开关输出', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib` VALUES ('223', '137', '0002', 'Z802-2', 'Z802二路开关输出', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib` VALUES ('225', '138', '0004', 'ZB02J-1', 'ZB02J情景开关按键一', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib` VALUES ('226', '138', '0004', 'ZB02J-2', 'ZB02J情景开关按键二', '', '', '', '', '', '1', '02', '', '0');
INSERT INTO `modeeplib` VALUES ('227', '138', '0004', 'ZB02J-3', 'ZB02J情景开关按键三', '', '', '', '', '', '1', '03', '', '0');
INSERT INTO `modeeplib` VALUES ('228', '139', '0009', 'Z815A-1', 'Z815A一路墙面电能检测开关', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib` VALUES ('229', '140', '0009', 'Z815B-1', 'Z815B一路墙面电能检测开关', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('230', '140', '0009', 'Z815B-2', 'Z815B二路墙面电能检测开关', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib` VALUES ('231', '141', '0009', 'Z815C-1', 'Z815C一路墙面电能检测开关', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib` VALUES ('232', '141', '0009', 'Z815C-2', 'Z815C二路墙面电能检测开关', '0006', '开关', null, '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib` VALUES ('233', '141', '0009', 'Z815C-3', 'Z815C三路墙面电能检测开关', '0006', '开关', null, '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib` VALUES ('234', '142', '0101', 'Z815D-1', 'Z815D一路墙面电能检测调光开关', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('236', '143', '0101', 'Z815E-1', 'Z815E一路墙面电能检测调光开关', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('237', '143', '0101', 'Z815E-2', 'Z815E二路墙面电能检测调光开关', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib` VALUES ('238', '144', '0009', 'Z826C-1', 'Z826C一路墙面电能检测开关', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('239', '144', '0009', 'Z826C-2', 'Z826C二路墙面电能检测开关', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib` VALUES ('240', '144', '0009', 'Z826C-3', 'Z826C三路墙面电能检测开关', '0006', '开关', '', '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib` VALUES ('241', '146', '0004', 'Z501GE3ED-1', 'Z501G安防控制按键(357)1', null, null, '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib` VALUES ('242', '145', '0007', 'Z206-1', 'Z206云智能网关', '', '', '', '', '', '1', '0A', '', '0');
INSERT INTO `modeeplib` VALUES ('243', '146', '0004', 'Z501GE3ED-2', 'Z501G安防控制按键(357)2', null, null, '', '3', 'source', '1', '02', '', '0');
INSERT INTO `modeeplib` VALUES ('244', '146', '0004', 'Z501GE3ED-3', 'Z501G安防控制按键(357)3', null, null, '', '3', 'source', '1', '03', '', '0');
INSERT INTO `modeeplib` VALUES ('245', '146', '0401', 'Z501GE3ED-4', 'Z501G安防控制按键(357)4', '0501', '安防控制', '', '3', 'source', '1', '04', '', '0');
INSERT INTO `modeeplib` VALUES ('246', '147', '0302', 'ZA06-1', 'ZA06空气蛋温湿度探测', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib` VALUES ('247', '147', '0402', 'ZA06-2', 'ZA06空气蛋VOC探测', null, null, null, null, null, '1', '02', null, '0');
INSERT INTO `modeeplib` VALUES ('248', '147', '0402', 'ZA06-3', 'ZA06空气蛋空气质量探测', null, null, null, null, null, '1', '03', '', '0');
INSERT INTO `modeeplib` VALUES ('249', '148', '200', 'ZD01C-1', 'ZD01C一路窗帘控制器（卷帘）', '6:08:00', '开关:调级控制', '', '3', 'dest', '1', '1', '', '1');
INSERT INTO `modeeplib` VALUES ('252', '148', '200', 'ZD01C-2', 'ZD01C二路窗帘控制器（卷帘）', '6:08:00', '开关:调级控制', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib` VALUES ('253', '149', '0200', 'ZD01D-1', 'ZD01D一路窗帘控制器（百叶帘）', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('254', '149', '0200', 'ZD01D-2', 'ZD01D二路窗帘控制器（百叶帘）', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib` VALUES ('255', '150', '0004', 'Z825F-1', 'Z-825F触摸情景开关', '0006', '开关', '', '3', 'dest', '1', '01', '', '0');
INSERT INTO `modeeplib` VALUES ('256', '151', '0004', 'Z825G-1', 'Z-825G触摸情景开关按键一', '0006', '开关', '', '3', 'dest', '1', '01', '', '0');
INSERT INTO `modeeplib` VALUES ('257', '151', '0004', 'Z825G-2', 'Z-825G触摸情景开关按键二', '0006', '开关', '', '3', 'dest', '1', '02', '', '0');
INSERT INTO `modeeplib` VALUES ('258', '152', '0004', 'Z825H-1', 'Z-825H触摸情景开关按键一', '0006', '开关', '', '3', 'dest', '1', '01', '', '0');
INSERT INTO `modeeplib` VALUES ('259', '152', '0004', 'Z825H-2', 'Z-825H触摸情景开关按键二', '0006', '开关', '', '3', 'dest', '1', '02', '', '0');
INSERT INTO `modeeplib` VALUES ('260', '153', '0009', 'Z826A-1', 'Z826A墙面电能检测开关', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('261', '155', '0009', 'Z826B-1', 'Z826B一路墙面电能检测开关', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('262', '155', '0009', 'Z826B-2', 'Z826B二路墙面电能检测开关', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib` VALUES ('263', '156', '0009', 'Z826D-1', 'Z826D无线调光触摸开关', '0006', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib` VALUES ('264', '157', '0007', 'Z202E-1', 'IEEE802.15.4/TCP IP 适配器', '0006', '开关:调级控制', '', '3', 'dest', '1', '0A', '', '0');
INSERT INTO `modeeplib` VALUES ('265', '158', '0007', 'ZL01A', '工业级IEEE802.15.4/RS-232 适配器', '', '', '', '', '', '1', '0A', '', '0');
INSERT INTO `modeeplib` VALUES ('266', '159', '0007', 'ZL01B', '工业级IEEE802.15.4/RS-485适配器', '', '', '', '', '', '1', '0A', '', '0');
INSERT INTO `modeeplib` VALUES ('267', '161', '0007', 'ZL01C', '工业级IEEE802.15.4/TCP IP 适配器', '', '', '', '', '', '1', '0A', '', '0');
INSERT INTO `modeeplib` VALUES ('268', '154', '0007', 'ZL01D', '工业级IEEE802.15.4/GPIO 适配器', '', '', '', '', '', '1', '0A', '', '0');
INSERT INTO `modeeplib` VALUES ('269', '162', '0007', 'ZL01E', '工业级IEEE802.15.4/USB适配器', '', '', '', '', '', '1', '0A', '', '0');
INSERT INTO `modeeplib` VALUES ('270', '160', '0007', 'ZL01F', '工业级IEEE802.15.4/RS-232 透传适配器', '', '', '', '', '', '1', '0A', '', '0');

-- ----------------------------
-- Table structure for modeeplib_en
-- ----------------------------
DROP TABLE IF EXISTS `modeeplib_en`;
CREATE TABLE `modeeplib_en` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nodeId` bigint(20) DEFAULT NULL,
  `deviceId` varchar(20) DEFAULT NULL,
  `internelModelId` varchar(50) DEFAULT NULL,
  `deviceName` varchar(200) DEFAULT NULL,
  `clusterId` varchar(200) DEFAULT NULL,
  `clusterName` varchar(100) DEFAULT NULL,
  `picName` varchar(100) DEFAULT NULL,
  `destType` varchar(4) DEFAULT NULL,
  `deviceType` varchar(20) DEFAULT NULL,
  `deviceTypeV2` bigint(20) DEFAULT '1',
  `ep` varchar(50) DEFAULT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `Groupable` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `nodeId` (`nodeId`) USING BTREE,
  KEY `deviceId` (`deviceId`) USING BTREE,
  KEY `internelModelId` (`internelModelId`) USING BTREE,
  KEY `deviceName` (`deviceName`) USING BTREE,
  KEY `destType` (`destType`) USING BTREE,
  KEY `deviceType` (`deviceType`) USING BTREE,
  KEY `clusterId` (`clusterId`) USING BTREE,
  KEY `Groupable` (`Groupable`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=271 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of modeeplib_en
-- ----------------------------
INSERT INTO `modeeplib_en` VALUES ('2', '2', '0009', 'Z815I-1', 'Z815I Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('3', '3', '0009', 'Z815K-1', 'Z815K 1st Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('4', '3', '0009', 'Z815K-2', 'Z815K 2nd Power Switch', '0006', '开关', null, '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('5', '3', '0009', 'Z815K-3', 'Z815K 3rd Power Switch', '0006', '开关', null, '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib_en` VALUES ('6', '4', '0100', 'Z806-1', 'Z806 1st Output', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('7', '4', '0100', 'Z806-2', 'Z806 2nd Output', '0006', '开关', null, '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('8', '5', '0000', 'ZB02A-1', 'ZB02A Wall Switch', '0006', '开关', null, '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('10', '7', '0000', 'Z503-2', 'Z503 Switches', '0006:0008', '开关:调级控制', null, '1', 'source', '1', '0F', '', '0');
INSERT INTO `modeeplib_en` VALUES ('11', '7', '0006', 'Z503-1', 'Z503 security Keypads', '', '', null, '3', '', '1', '0A', '', '0');
INSERT INTO `modeeplib_en` VALUES ('12', '10', '0009', 'Z805A-1', 'Z805A Power Switch', '0006', '开关', null, '3', 'dest', '1', '0A', null, '1');
INSERT INTO `modeeplib_en` VALUES ('13', '11', '0009', 'Z805B-1', 'Z805B Power Switch', '0006', '开关', '', '3', 'dest', '1', '0A', '', '1');
INSERT INTO `modeeplib_en` VALUES ('14', '12', '0009', 'Z810B-1', 'Z810B Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('15', '13', '0100', 'Z811-1', 'Z811 1st Output', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('16', '13', '0100', 'Z811-2', 'Z811 2nd Output', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('17', '13', '0100', 'Z811-3', 'Z811 3rd Output', '0006', '开关', '', '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib_en` VALUES ('18', '13', '0100', 'Z811-4', 'Z811 4th Output', '0006', '开关', '', '3', 'dest', '1', '04', '', '1');
INSERT INTO `modeeplib_en` VALUES ('19', '14', '0009', 'Z815J-1', 'Z815J 1st Power Switch', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('20', '14', '0009', 'Z815J-2', 'Z815J 2nd Power Switch', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('21', '15', '0101', 'Z815L-1', 'Z815L Dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('22', '16', '0101', 'Z815M-1', 'Z815M 1st Dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('23', '16', '0101', 'Z815M-2', 'Z815M 2nd Dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('24', '17', '0009', 'Z817A-1', 'Z817A Power Switch', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('25', '18', '0101', 'Z817B-1', 'Z817B Dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('26', '19', '0002', 'Z817C-1', 'Z817C Power Switch', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('27', '20', '0009', 'Z825C-1', 'Z825C 1st Power Switch', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('28', '20', '0009', 'Z825C-2', 'Z825C 2nd Power Switch', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('29', '20', '0009', 'Z825C-3', 'Z825C 3rd Power Switch', '0006', '开关', '', '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib_en` VALUES ('30', '21', '0101', 'Z825E-1', 'Z825E 1st Dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('31', '21', '0101', 'Z825E-2', 'Z825E 2nd Dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('32', '22', '0101', 'ZC06-1', 'ZC06 Dimmer light', '0006:0008', '开关:调级控制', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('33', '23', '0009', 'Z800-1', 'Z800 Power Plug', '0006', '开关', '', '3', 'dest', '1', '0A', '', '1');
INSERT INTO `modeeplib_en` VALUES ('34', '24', '0002', 'Z803-1', 'Z803 1st Power Plug', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('35', '24', '0002', 'Z803-2', 'Z803 2nd Power Plug', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('36', '24', '0002', 'Z803-3', 'Z803 3rd Power Plug', '0006', '开关', '', '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib_en` VALUES ('37', '24', '0002', 'Z803-4', 'Z803 4th Power Plug', '0006', '开关', null, '3', 'dest', '1', '04', null, '1');
INSERT INTO `modeeplib_en` VALUES ('38', '24', '000D', 'Z803-5', 'Z803 Power Consumption Awareness', '0006', '开关', null, '3', '', '1', '05', null, '0');
INSERT INTO `modeeplib_en` VALUES ('39', '25', '0009', 'Z808A-1', 'Z808A Power Plug', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('40', '26', '0101', 'Z808B-1', 'Z808B Dimmable Power Plug', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('41', '27', '0009', 'Z809A-1', 'Z809A Power Plug', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('42', '28', '0101', 'Z809B-1', 'Z809B Dimmable Power Plug', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('43', '29', '0009', 'Z816B-1', 'Z816B US type Power Plug', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('44', '30', '0009', 'Z816G-1', 'Z816G UK type Power Plug', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('45', '31', '0009', 'Z816H-1', 'Z816H China type Power Plug', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('46', '32', '0302', 'Z711-1', 'Z711 Temperature and Humidity Sensor', null, null, null, null, null, '1', '0A', null, '0');
INSERT INTO `modeeplib_en` VALUES ('47', '33', '0302', 'Z712-1', 'Z712 Temperature and Humidity Sensor', '', '', '', '', '', '1', '0A', '', '0');
INSERT INTO `modeeplib_en` VALUES ('48', '34', '0302', 'Z713-1', 'Z713 Temperature and Humidity Sensor', '', '', null, '', '', '1', '0A', null, '0');
INSERT INTO `modeeplib_en` VALUES ('49', '35', '0302', 'Z716A-1', 'Z716A Temperature and Humidity Sensor', '', '', '', '', '', '1', '0A', '', '0');
INSERT INTO `modeeplib_en` VALUES ('50', '36', '0302', 'Z716B-1', 'Z716B Temperature Sensor', null, null, '', null, null, '1', '0A', '', '0');
INSERT INTO `modeeplib_en` VALUES ('51', '37', '0103', 'Z302B-1', 'Z302B Light Switch', '0006', '开关', null, '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('52', '38', '0104', 'Z302H-1', 'Z302H Light Sensor ', '0006:0008', '开关:调级控制', null, '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('53', '39', '0106', 'Z302G-1', 'Z302G Dimmer Switch', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('54', '40', '0103', 'Z311B-1', 'Z311B Light Switch', '0006', '开关', null, '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('55', '41', '0104', 'Z311H-1', 'Z311H Light Sensor ', '0006:0008', '开关:调级控制', null, '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('56', '42', '0106', 'Z311G-1', 'Z311G Dimmer Switch', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('57', '44', '0000', 'ZB02B-1', 'ZB02B Wall Switch 1st key', '0006', '开关', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('58', '44', '0000', 'ZB02B-2', 'ZB02B Wall Switch 2nd key', '0006', '开关', '', '3', 'source', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('59', '45', '0000', 'ZB02C-1', 'ZB02C Wall Switch 1st key', '0006', '开关', '', '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('60', '45', '0000', 'ZB02C-2', 'ZB02C Wall Switch 2nd key', '0006', '开关', '', '3', 'source', '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('61', '45', '0000', 'ZB02C-3', 'ZB02C Wall Switch 3rd key', '0006', '开关', '', '3', 'source', '1', '03', '', '0');
INSERT INTO `modeeplib_en` VALUES ('62', '46', '0104', 'ZB02F-1', 'ZB02F Wall Dimmer', '0006:0008', '开关:调级控制', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('63', '48', '0000', 'ZB01B-1', 'ZB01B On Off Switch', '0006', '开关', null, '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('64', '49', '0402', 'ZB01C-1', 'ZB01C Motion Detector', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('65', '52', '0000', 'ZB11B-1', 'ZB11B On Off Switch', '0006', '开关', null, '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('66', '53', '0402', 'ZB11C-1', 'ZB11C Motion Detector', null, null, '', null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('67', '55', '0200', 'Z815N-1', 'Z815N Shade Control Switch', '0006:0008', '开关:调级控制', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('68', '56', '0200', 'ZD01B-1', 'ZD01B First Curtain Controller', '0006:0008', '开关:调级控制', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('69', '56', '0200', 'ZD01B-2', 'ZD01B Second Curtain Controller', '0006:0008', '开关:调级控制', null, '3', 'dest', '1', '02', null, '1');
INSERT INTO `modeeplib_en` VALUES ('71', '47', '0402', 'ZB01A-1', 'ZB01A Motion Detector', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('72', '50', '0107', 'ZB01D-1', 'ZB01D Occupancy Sensor', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('73', '51', '0402', 'ZB11A-1', 'ZB11A Motion Detector', '', '', null, '', '', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('74', '49', '0000', 'ZB01C-2', 'ZB01C On Off Switch', '0006', '开关', null, '3', 'source', '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('75', '49', '0302', 'ZB01C-3', 'ZB01C Temperature Sensor', null, null, null, null, null, '1', '03', null, '0');
INSERT INTO `modeeplib_en` VALUES ('76', '53', '0000', 'ZB11C-2', 'ZB11C On Off Switch', '0006', '开关', null, '3', 'source', '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('77', '53', '0302', 'ZB11C-3', 'ZB11C Temperature Sensor', null, null, '', null, null, '1', '03', null, '0');
INSERT INTO `modeeplib_en` VALUES ('78', '54', '0107', 'ZB11D-1', 'ZB11D Occupancy Sensor', '', '', null, '', '', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('79', '57', '0402', 'Z302A-1', 'Z302A Window Intrusion Sensor', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('80', '58', '0402', 'Z302C-1', 'Z302C Window Glass Break Sensor and Intrusion Sensor', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('81', '59', '0402', 'Z302D-1', 'Z302D Panic Button', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('82', '60', '0402', 'Z302E-1', 'Z302E Asset tag', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('83', '61', '0000', 'Z302J-1', 'Z302J on off switch', '0006', '开关', '', '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('84', '61', '0402', 'Z302J-2', 'Z302J Window Intrusion Sensor', '', '', '', '', '', '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('85', '62', '0402', 'Z306D-1', 'Z306D Panic Button and Inductive Charging Base', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('86', '63', '0402', 'Z311A-1', 'Z311AWindow Intrusion Sensor', null, '', '', '', '', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('87', '64', '0402', 'Z311C-1', 'Z311C Window Glass Break Sensor and Intrusion Sensor', null, '', '', '', '', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('88', '65', '0401', 'Z312-1', 'Z312 Door Bell Button', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('89', '66', '0402', 'Z307-1', 'Z307 Fall Sensor and Inductive Charging Base', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('90', '67', '0402', 'Z308-1', 'Z308 Panic Button', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('91', '68', '0402', 'ZA01A-1', 'ZA01A Smoke Detector with Heat Sensor', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('92', '69', '0402', 'ZA01B-1', 'ZA01B Gas Detector with Heat Sensor', '', '', null, '', '', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('93', '70', '0402', 'ZA01C-1', 'ZA01C CO Detector', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('94', '71', '0402', 'ZA01D-1', 'ZA01D Liquefied Gas Detector with Heat Sensor', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('95', '72', '0402', 'ZA02E-1', 'ZA02E Photoelectric Smoke Detector with Heat Sensor', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('96', '73', '0002', 'ZA10-1', 'ZA10 Gas or Water Keeper', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('97', '74', '000A', 'ZB05-1', 'ZB05 Door Lock', '0101', '开关锁', null, '3', 'dest', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('98', '75', '0401', 'ZB02E-1', 'ZB02E Door Bell', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('99', '76', '0402', 'Z311E-1', 'Z311E Asset tag', '', '', null, '', '', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('100', '77', '0403', 'Z602A-1', 'Z602A Siren', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('101', '78', '0403', 'Z602B-1', 'Z602B Siren with GSM', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('102', '79', '0403', 'Z601A-1', 'Z601A Siren', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('103', '80', '000D', 'Z821-1', 'Z821 1st Power Consumption Awareness', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('104', '80', '000D', 'Z821-2', 'Z821 2nd Power Consumption Awareness', null, null, null, null, null, '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('105', '80', '000D', 'Z821-3', 'Z821 3rd Power Consumption Awareness', null, null, null, null, null, '1', '03', null, '0');
INSERT INTO `modeeplib_en` VALUES ('106', '80', '000D', 'Z821-4', 'Z821 4th Power Consumption Awareness', null, null, null, null, null, '1', '04', null, '0');
INSERT INTO `modeeplib_en` VALUES ('107', '80', '000D', 'Z821-5', 'Z821 5th Power Consumption Awareness', null, null, null, null, null, '1', '05', null, '0');
INSERT INTO `modeeplib_en` VALUES ('108', '80', '000D', 'Z821-6', 'Z821 6th Power Consumption Awareness', null, null, null, null, null, '1', '06', null, '0');
INSERT INTO `modeeplib_en` VALUES ('109', '80', '000D', 'Z821-7', 'Z821 7th Power Consumption Awareness', null, null, null, null, null, '1', '07', null, '0');
INSERT INTO `modeeplib_en` VALUES ('110', '80', '000D', 'Z821-8', 'Z821 All Power Consumption Awareness', null, null, null, null, null, '1', '08', null, '0');
INSERT INTO `modeeplib_en` VALUES ('111', '81', '0001', 'Z501A-1', 'Z501A dimmer Switch', '0006:0008', '开关:调级控制', null, '3', 'source', '1', '0A', null, '0');
INSERT INTO `modeeplib_en` VALUES ('112', '81', '0401', 'Z501A-2', 'Z501A security Keypad', '', '', null, '', '', '1', '0B', null, '0');
INSERT INTO `modeeplib_en` VALUES ('113', '82', '0000', 'Z501B-1', 'Z501B 1st Switch', '0006', '开关', null, '3', 'source', '1', '0A', null, '0');
INSERT INTO `modeeplib_en` VALUES ('114', '82', '0000', 'Z501B-2', 'Z501B 2nd Switch', '0006', '开关', null, '3', 'source', '1', '0B', null, '0');
INSERT INTO `modeeplib_en` VALUES ('115', '83', '0001', 'Z501C-1', 'Z501C dimmer Switch', '0006:0008', '开关:调级控制', null, '3', 'source', '1', '0A', null, '0');
INSERT INTO `modeeplib_en` VALUES ('116', '83', '0000', 'Z501C-2', 'Z501C on Off Switch', '0006', '开关', null, '3', 'source', '1', '0B', null, '0');
INSERT INTO `modeeplib_en` VALUES ('117', '84', '0008', 'Z210C-1', 'Z210C Infrared Adpt', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('118', '85', '0008', 'Z211-1', 'Z211 Bidirectional ZigBeeIR Adpt', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('119', '86', '0007', 'Z203-1', 'Z203 CWSHC', null, null, null, null, null, '1', '0A', null, '0');
INSERT INTO `modeeplib_en` VALUES ('120', '87', '0007', 'Z103AC-1', 'Z103AC USB', null, null, null, null, null, '1', '0A', null, '0');
INSERT INTO `modeeplib_en` VALUES ('121', '88', '0007', 'Z103AR-1', 'Z103AR USB', null, null, null, null, null, '1', '0A', null, '0');
INSERT INTO `modeeplib_en` VALUES ('122', '89', '0400', 'Z201B-1', 'Z201B ZigBee802.15.4 router andCIE', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('123', '90', '0400', 'Z201C-1', 'Z201C ZigBee802.15.4 Coord', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('124', '91', '0008', 'Z201R HA-1', 'Z201R ZigBee TCP/IP Gateway', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('125', '92', '0008', 'Z201R-1', 'Z201 ZigBee 802.15.4 Router', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('126', '93', '0002', 'Z812A-1', 'Z812A output', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('127', '93', '0000', 'Z812A-2', 'Z812A 1st Switch', '0006', '开关', null, '3', 'source', '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('128', '93', '0000', 'Z812A-3', 'Z812A 2nd Switch', '0006', '开关', null, '3', 'source', '1', '03', null, '0');
INSERT INTO `modeeplib_en` VALUES ('129', '93', '0000', 'Z812A-4', 'Z812A 3rd Switch', '0006', '开关', null, '3', 'source', '1', '04', null, '0');
INSERT INTO `modeeplib_en` VALUES ('130', '94', '0000', 'Z812B-1', 'Z812B 1st Switch', '0006', '开关', null, '3', 'source', '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('131', '94', '0000', 'Z812B-2', 'Z812B 2nd Switch', '0006', '开关', null, '3', 'source', '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('132', '94', '0000', 'Z812B-3', 'Z812B 3rd Switch', '0006', '开关', null, '3', 'source', '1', '03', null, '0');
INSERT INTO `modeeplib_en` VALUES ('133', '94', '0000', 'Z812B-4', 'Z812B 4th Switch', '0006', '开关', null, '3', 'source', '1', '04', null, '0');
INSERT INTO `modeeplib_en` VALUES ('134', '94', '0104', 'Z812B-5', 'Z812B 1st Dimmer Switch', '0006:0008', '开关:调级控制', null, '3', 'source', '1', '05', null, '0');
INSERT INTO `modeeplib_en` VALUES ('135', '94', '0104', 'Z812B-6', 'Z812B 2nd Dimmer Switch', '0006:0008', '开关:调级控制', null, '3', 'source', '1', '06', null, '0');
INSERT INTO `modeeplib_en` VALUES ('136', '95', '0402', 'Z801TXB-1', 'Z801TXB 1st Detector', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('137', '95', '0402', 'Z801TXB-2', 'Z801TXB 2nd Detector', '', '', '', '', '', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('138', '95', '0402', 'Z801TXB-3', 'Z801TXB 3rd Detector', '', '', '', '', '', '1', '03', '', '0');
INSERT INTO `modeeplib_en` VALUES ('139', '95', '0402', 'Z801TXB-4', 'Z801TXB 4th Detector', '', '', '', '', '', '1', '04', '', '0');
INSERT INTO `modeeplib_en` VALUES ('140', '95', '0402', 'Z801TXB-5', 'Z801TXB 5th Detector', '', '', '', '', '', '1', '05', '', '0');
INSERT INTO `modeeplib_en` VALUES ('141', '96', '0002', 'Z801RX', 'Z801RX output', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('142', '101', '0009', 'Z825A-1', 'Z825APower Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('143', '102', '0009', 'Z825B-1', 'Z825B 1st Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('144', '102', '0009', 'Z825B-2', 'Z825B 2nd Power Switch', '0006', '开关', null, '3', 'dest', '1', '02', null, '1');
INSERT INTO `modeeplib_en` VALUES ('145', '103', '0101', 'Z825D-1', 'Z825D Dimmer Switch', '0006:0008', '开关:调级控制', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('146', '100', '0400', 'Z508-1', 'Z508 In Home Display', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('147', '104', '0302', 'Z725A-1', 'Z725A Temperature and Humidity Sensor', null, null, null, null, null, '1', '0A', null, '0');
INSERT INTO `modeeplib_en` VALUES ('148', '106', '0008', 'Z725R-1', 'Z725R Outdoor Range Extender', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('149', '107', '0009', 'Z962A-1', 'Z962A Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('150', '108', '0009', 'Z962B-1', 'Z962B 1st Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('151', '108', '0009', 'Z962B-2', 'Z962B 2nd Power Switch', '0006', '开关', null, '3', 'dest', '1', '02', null, '1');
INSERT INTO `modeeplib_en` VALUES ('152', '109', '0009', 'Z962C-1', 'Z962C 1st Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('153', '109', '0009', 'Z962C-2', 'Z962C 2nd Power Switch', '0006', '开关', null, '3', 'dest', '1', '02', null, '1');
INSERT INTO `modeeplib_en` VALUES ('154', '109', '0009', 'Z962C-3', 'Z962C 3rd Power Switch', '0006', '开关', null, '3', 'dest', '1', '03', null, '1');
INSERT INTO `modeeplib_en` VALUES ('155', '110', '0004', 'Z962D-1', 'Z962D Scene or Mode Selector', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('156', '111', '0004', 'Z962E-1', 'Z962E 1st Scene or Mode Selector', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('157', '111', '0004', 'Z962E-2', 'Z962E 2nd Scene or Mode Selector', null, null, null, null, null, '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('158', '112', '0004', 'Z962F-1', 'Z962F 1st Scene or Mode Selector', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('159', '112', '0004', 'Z962F-2', 'Z962F 2nd Scene or Mode Selector', null, null, null, null, null, '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('160', '112', '0004', 'Z962F-3', 'Z962F 3rd Scene or Mode Selector', null, null, null, null, null, '1', '03', null, '0');
INSERT INTO `modeeplib_en` VALUES ('161', '113', '0009', 'Z962G-1', 'Z962G Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('162', '113', '0004', 'Z962G-2', 'Z962G Scene or Mode Selector', null, null, null, null, null, '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('163', '114', '0009', 'Z962H-1', 'Z962H Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('164', '114', '0004', 'Z962H-2', 'Z962H 1st Scene or Mode Selector', null, null, null, null, null, '1', '02', null, '0');
INSERT INTO `modeeplib_en` VALUES ('165', '114', '0004', 'Z962H-3', 'Z962H 2nd Scene or Mode Selector', null, null, null, null, null, '1', '03', null, '0');
INSERT INTO `modeeplib_en` VALUES ('166', '115', '0004', 'Z962I-1', 'Z962I Scene or Mode Selector', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('167', '115', '0009', 'Z962I-2', 'Z962I 1st Power Switch', '0006', '开关', null, '3', 'dest', '1', '02', null, '1');
INSERT INTO `modeeplib_en` VALUES ('168', '115', '0009', 'Z962I-3', 'Z962I 2nd Power Switch', '0006', '开关', null, '3', 'dest', '1', '03', null, '1');
INSERT INTO `modeeplib_en` VALUES ('174', '116', '0402', 'Z801WLS-1', 'Z801WLS 1st water sensor', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('175', '116', '0402', 'Z801WLS-2', 'Z801WLS 2nd water sensor', '', '', '', '', '', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('176', '116', '0402', 'Z801WLS-3', 'Z801WLS 3rd water sensor', '', '', '', '', '', '1', '03', '', '0');
INSERT INTO `modeeplib_en` VALUES ('177', '116', '0402', 'Z801WLS-4', 'Z801WLS 4th water sensor', '', '', '', '', '', '1', '04', '', '0');
INSERT INTO `modeeplib_en` VALUES ('178', '116', '0402', 'Z801WLS-5', 'Z801WLS 5th water sensor', '', '', '', '', '', '1', '05', '', '0');
INSERT INTO `modeeplib_en` VALUES ('179', '117', '000A', 'ZB05A-1', 'ZB05A Door Lock', '0101', '开关锁', '', '3', 'dest', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('180', '118', '0000', 'Z801TX-1', 'Z801TX 1st Switch', '0006', '开关', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('181', '118', '0000', 'Z801TX-2', 'Z801TX  2nd Switch', '0006', '开关', '', '3', 'source', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('182', '118', '0000', 'Z801TX-3', 'Z801TX  3rd Switch', '0006', '开关', '', '3', 'source', '1', '03', '', '0');
INSERT INTO `modeeplib_en` VALUES ('183', '118', '0000', 'Z801TX-4', 'Z801TX  4th Switch', '0006', '开关', '', '3', 'source', '1', '04', '', '0');
INSERT INTO `modeeplib_en` VALUES ('184', '118', '0000', 'Z801TX-5', 'Z801TX  5th Switch', '0006', '开关', '', '3', 'source', '1', '05', '', '0');
INSERT INTO `modeeplib_en` VALUES ('185', '119', '0000', 'Z311J-1', 'Z311J on off switch', '0006', '开关', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('186', '119', '0402', 'Z311J-2', 'Z311J Window Intrusion Sensor', '', '', '', '', '', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('187', '120', '0101', 'ZC07-1', 'ZC07 Dimmer light', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('188', '121', '0402', 'Z311W-1', 'Z311W water sensor', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('190', '122', '0200', 'Z811B-1', 'Z811B 1st Curtain Controller', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('191', '122', '0200', 'Z811B-2', 'Z811B 2nd Curtain Controller', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('192', '123', '0004', 'Z825I-1', 'Z825I 1st Scene or Mode Selector', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('193', '123', '0004', 'Z825I-2', 'Z825I 2nd Scene or Mode Selector', '', '', '', '', '', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('194', '123', '0004', 'Z825I-3', 'Z825I 3rd Scene or Mode Selector', '', '', '', '', '', '1', '03', '', '0');
INSERT INTO `modeeplib_en` VALUES ('195', '123', '0004', 'Z825I-4', 'Z825I 4th Scene or Mode Selector', '', '', '', '', '', '1', '04', '', '0');
INSERT INTO `modeeplib_en` VALUES ('196', '123', '0004', 'Z825I-5', 'Z825I 5th Scene or Mode Selector', '', '', '', '', '', '1', '05', '', '0');
INSERT INTO `modeeplib_en` VALUES ('197', '123', '0004', 'Z825I-6', 'Z825I 6th Scene or Mode Selector', '', '', '', '', '', '1', '06', '', '0');
INSERT INTO `modeeplib_en` VALUES ('201', '124', '0004', 'Z825J-4', 'Z825J 1st Scene or Mode Selector', '', '', '', '', '', '1', '04', '', '0');
INSERT INTO `modeeplib_en` VALUES ('202', '124', '0004', 'Z825J-5', 'Z825J 2nd Scene or Mode Selector', '', '', '', '', '', '1', '05', '', '0');
INSERT INTO `modeeplib_en` VALUES ('203', '124', '0004', 'Z825J-6', 'Z825J 3rd Scene or Mode Selector', '', '', '', '', '', '1', '06', '', '0');
INSERT INTO `modeeplib_en` VALUES ('204', '124', '0009', 'Z825J-1', 'Z825J 1st Output', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('205', '124', '0009', 'Z825J-2', 'Z825J 2nd Output', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('206', '124', '0009', 'Z825J-3', 'Z825J 3rd Output', '0006', '开关', '', '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib_en` VALUES ('207', '125', '0009', 'Z825Q-1', 'Z825Q 1st Power Switch', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('208', '125', '0009', 'Z825Q-2', 'Z825Q 2nd Power Switch', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('209', '125', '0009', 'Z825Q-3', 'Z825Q 3rd Power Switch', '0006', '开关', '', '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib_en` VALUES ('210', '126', '0009', 'Z816I-1', 'Z816I China type Power Plug', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('211', '127', '0000', 'Z817D-1', 'Z817D  On Off Switch', '0006', '开关', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('212', '128', '0008', 'Z800R-1', 'Z800 Plug Router', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('213', '129', '0008', 'Z809C-1', 'Z809C Plug Router', null, null, null, null, null, '1', '01', null, '0');
INSERT INTO `modeeplib_en` VALUES ('214', '130', '0008', 'Z809D-1', 'Z809D Plug Router', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('215', '131', '0001', 'Z501AE3ED-1', 'Z501A dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('216', '131', '0401', 'Z501AE3ED-2', 'Z501A security Keypads', '', '', '', '', '', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('217', '132', '0000', 'Z501BE3ED-1', 'Z501B 1st Switch', '0006', '开关', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('218', '132', '0000', 'Z501BE3ED-2', 'Z501B 2nd Switch', '0006', '开关', '', '3', 'source', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('219', '133', '0001', 'Z501CE3ED-1', 'Z501C dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('220', '133', '0000', 'Z501CE3ED-2', 'Z501C security Keypads', '0006', '开关', '', '3', 'source', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('221', '134', '0402', 'ZB02I-1', 'ZB02IPanic Button', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('222', '137', '0002', 'Z802-1', 'Z802 1st Output', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('223', '137', '0002', 'Z802-2', 'Z802 2nd Output', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('225', '138', '0004', 'ZB02J-1', 'ZB02J 1st Scene or Mode Selector', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('226', '138', '0004', 'ZB02J-2', 'ZB02J 2nd Scene or Mode Selector', '', '', '', '', '', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('227', '138', '0004', 'ZB02J-3', 'ZB02J 3rd Scene or Mode Selector', '', '', '', '', '', '1', '03', '', '0');
INSERT INTO `modeeplib_en` VALUES ('228', '139', '0009', 'Z815A-1', 'Z815A Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('229', '140', '0009', 'Z815B-1', 'Z815B 1st Power Switch', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('230', '140', '0009', 'Z815B-2', 'Z815B 2nd Power Switch', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('231', '141', '0009', 'Z815C-1', 'Z815C 1st Power Switch', '0006', '开关', null, '3', 'dest', '1', '01', null, '1');
INSERT INTO `modeeplib_en` VALUES ('232', '141', '0009', 'Z815C-2', 'Z815C 2nd Power Switch', '0006', '开关', null, '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('233', '141', '0009', 'Z815C-3', 'Z815C 3rd Power Switch', '0006', '开关', null, '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib_en` VALUES ('234', '142', '0101', 'Z815D-1', 'Z815D 1st dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('236', '143', '0101', 'Z815E-1', 'Z815E 2nd dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('237', '143', '0101', 'Z815E-2', 'Z815E 3rd dimmer Switch', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('238', '144', '0009', 'Z826C-1', 'Z826C 1st Power Switch', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('239', '144', '0009', 'Z826C-2', 'Z826C 2nd Power Switch', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('240', '144', '0009', 'Z826C-3', 'Z826C 3rd Power Switch', '0006', '开关', '', '3', 'dest', '1', '03', '', '1');
INSERT INTO `modeeplib_en` VALUES ('241', '146', '0004', 'Z501GE3ED-1', 'Z501G 1st security Keypads', '', '', '', '3', 'source', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('242', '145', '0007', 'Z206-1', 'Z206 CWSHC', '', '', '', '', '', '1', '0A', '', '0');
INSERT INTO `modeeplib_en` VALUES ('243', '146', '0004', 'Z501GE3ED-2', 'Z501G 2nd security Keypads', '', '', '', '3', 'source', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('244', '146', '0004', 'Z501GE3ED-3', 'Z501G 3rd security Keypads', '', '', '', '3', 'source', '1', '03', '', '0');
INSERT INTO `modeeplib_en` VALUES ('245', '146', '0401', 'Z501GE3ED-4', 'Z501G 4th security Keypads', '0501', '安防控制', '', '3', 'source', '1', '04', '', '0');
INSERT INTO `modeeplib_en` VALUES ('246', '147', '0302', 'ZA06-1', 'ZA06 Temperature and Humidity Sensor', '', '', '', '', '', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('247', '147', '0402', 'ZA06-2', 'ZA06 VOC Sensor', '', '', '', '', '', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('248', '147', '0402', 'ZA06-3', 'ZA06 Air quality Sensor', '', '', '', '', '', '1', '03', '', '0');
INSERT INTO `modeeplib_en` VALUES ('249', '148', '200', 'ZD01C-1', 'ZD01C First Curtain Controller (Drape)', '6:08:00', '开关:调级控制', '', '3', 'dest', '1', '1', '', '1');
INSERT INTO `modeeplib_en` VALUES ('252', '148', '200', 'ZD01C-2', 'ZD01C Second Curtain Controller(Drape)', '6:08:00', '开关:调级控制', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('253', '149', '0200', 'ZD01D-1', 'ZD01D First Curtain Controller (Blinds)', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('254', '149', '0200', 'ZD01D-2', 'ZD01D Second Curtain Controller (Blinds)', '0006:0008', '开关:调级控制', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('255', '150', '0004', 'Z825F-1', 'Z825G  1st Scene or Mode Selector', '0006', '开关', '', '3', 'dest', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('256', '151', '0004', 'Z825G-1', 'Z825G  2nd Scene or Mode Selector', '0006', '开关', '', '3', 'dest', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('257', '151', '0004', 'Z825G-2', 'Z825H  1st Scene or Mode Selector', '0006', '开关', '', '3', 'dest', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('258', '152', '0004', 'Z825H-1', 'Z825H  2nd Scene or Mode Selector', '0006', '开关', '', '3', 'dest', '1', '01', '', '0');
INSERT INTO `modeeplib_en` VALUES ('259', '152', '0004', 'Z825H-2', 'Z825H  3rd Scene or Mode Selector', '0006', '开关', '', '3', 'dest', '1', '02', '', '0');
INSERT INTO `modeeplib_en` VALUES ('260', '153', '0009', 'Z826A-1', 'Z826A PowerSwitch', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('261', '155', '0009', 'Z826B-1', 'Z826B 1st Power Switch', '0006', '开关', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('262', '155', '0009', 'Z826B-2', 'Z826B 2nd Power Switch', '0006', '开关', '', '3', 'dest', '1', '02', '', '1');
INSERT INTO `modeeplib_en` VALUES ('263', '156', '0009', 'Z826D-1', 'Z826D dimmer Switch', '0006', '开关:调级控制', '', '3', 'dest', '1', '01', '', '1');
INSERT INTO `modeeplib_en` VALUES ('264', '157', '0007', 'Z202E-1', 'Z-202E IEEE 802.15.4/ TCP/IP Gateway Coordinator/Route', '0006', '开关:调级控制', '', '3', 'dest', '1', '0A', '', '0');
INSERT INTO `modeeplib_en` VALUES ('265', '158', '0007', 'ZL01A', 'Z-L01A  IEEE 802.15.4/RS232 Adapter', '', '', '', '', '', '1', '0A', '', '0');
INSERT INTO `modeeplib_en` VALUES ('266', '159', '0007', 'ZL01B', 'ZL01B IEEE 802.15.4/RS485 Adapter', '', '', '', '', '', '1', '0A', '', '0');
INSERT INTO `modeeplib_en` VALUES ('267', '161', '0007', 'Z-L01C', 'ZL01C IEEE 802.15.4/ TCP/IP Adapter', '', '', '', '', '', '1', '0A', '', '0');
INSERT INTO `modeeplib_en` VALUES ('268', '154', '0007', 'ZL01D', 'ZL01D IEEE 802.15.4/GPIO Adapte', '', '', '', '', '', '1', '0A', '', '0');
INSERT INTO `modeeplib_en` VALUES ('269', '162', '0007', 'ZL01E', 'ZL01E IEEE 802.15.4/USB Adapter', '', '', '', '', '', '1', '0A', '', '0');
INSERT INTO `modeeplib_en` VALUES ('270', '160', '0007', 'ZL01F', 'ZL01F IEEE 802.15.4/RS232 Straight-Through Adapter', '', '', '', '', '', '1', '0A', '', '0');

-- ----------------------------
-- Table structure for modenodelib
-- ----------------------------
DROP TABLE IF EXISTS `modenodelib`;
CREATE TABLE `modenodelib` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nodeType` varchar(100) DEFAULT NULL,
  `modelId` varchar(50) DEFAULT NULL,
  `deviceName` varchar(200) DEFAULT NULL,
  `clusterId` varchar(200) DEFAULT NULL,
  `clusterName` varchar(100) DEFAULT NULL,
  `destType` varchar(4) DEFAULT NULL,
  `source_id` bigint(20) DEFAULT '1',
  `deviceType` varchar(20) DEFAULT NULL,
  `picName` varchar(100) DEFAULT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `powerType` varchar(200) DEFAULT NULL,
  `activationMethod` varchar(2000) DEFAULT NULL,
  `resetDefault` varchar(2000) DEFAULT NULL,
  `remark` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `nodeType` (`nodeType`) USING BTREE,
  KEY `modelId` (`modelId`) USING BTREE,
  KEY `deviceName` (`deviceName`) USING BTREE,
  KEY `clusterId` (`clusterId`) USING BTREE,
  KEY `destType` (`destType`) USING BTREE,
  KEY `deviceType` (`deviceType`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=163 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of modenodelib
-- ----------------------------
INSERT INTO `modenodelib` VALUES ('2', '0', 'Z815I', 'Z815I单路电能检测墙面开关', '0006', '开关', '3', '1', 'dest', 'Z815I.jpg', 'Z815I是一路输出的开关设备，可直接安装于86盒中，替换家居中的普通墙面开关，能够检测和统计当前负载电流、电压、功率、电能值。用户可采用ZigBee遥控器、手机、按键开关等多种方式控制该设备。', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('3', '0', 'Z815K', 'Z815K三路电能检测墙面开关', '0006', '开关', '3', '1', 'dest', 'Z815K.jpg', 'Z815K有三路输出的开关设备，可直接安装于86盒中，替换家居中的普通墙面开关，能够检测和统计当前负载电流、电压、功率、电能值。用户可采用ZigBee遥控器、手机、按键开关等多种方式控制该设备。', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('4', '0', 'Z806', 'Z806双路嵌墙开关', '0006', '开关', '3', '1', 'dest', 'Z806.jpg', 'Z806是一款无线智能开关控制设备，带有双路通断能力的继电器，用户可以通过外接开关、绑定设备或软件无线控制开关输出。', '100～240V的交流电源', '不需要激活', '按住绑定键的同时给设备上电，开始恢复出厂设置，状态指示灯一直闪烁表示擦除完成。之后重新上电，Z806可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('5', '4', 'ZB02A', 'ZB02单键墙面开关', '0006', '开关', '3', '1', 'source', 'ZB02A.jpg', 'ZB02A是单键无线可壁挂按键开关。可以与有开关控制功能的设备绑定，无线控制其开关。', '2节AAA1.5V电池', '拆下面板的上盖，短按绑定键（红色按键），LED网络灯闪5下激活设备。', '拆下面板的上盖，按住绑定键的同时给设备上电，进行恢复出厂设置，看到状态指示灯闪烁，表示恢复完成', '无');
INSERT INTO `modenodelib` VALUES ('7', '9', 'Z503', 'Z503多功能遙控器', '0006:0008', '开关:调级控制', '1', '1', 'source', 'Z503.jpg', 'Z503功能遥控器,可与开关、调级设备绑定，通过遥控器控制设备动作。也可用于系统布撤防和紧急报警等。', '3V CR2034纽扣电池', '按住“2nd”键和“3”键可激活设备。', '1.同时按住扩展情景键和第二功能按键，给设备上电；2. 如指示灯快速闪烁10次表示恢复出厂值完成，设备自动重启后即可重新加网；3. 如指示灯慢闪3次，表示恢复出厂值失败，请重复步骤1。', '无');
INSERT INTO `modenodelib` VALUES ('10', '0', 'Z805A', 'Z805A单路电能检测嵌墙开关', '0006', '开关', '3', '1', 'dest', 'Z805A.jpg', 'Z805A是一款无线智能开关控制设备，带有单路16A/250V AC通断能力的继电器，用户可以通过外接开关、绑定设备或软件无线控制开关输出，并且可通过ZiG-BUTLER软件查看当前负载的电流电压及功率电能值。', '100～240V的交流电源', '不需要激活', '长按绑定键15S(指示灯闪烁2次,3S闪烁一次，10S闪烁一次），状态灯开始快闪20秒，设备进行恢复出厂设置；20s内短按任意键，Z805B重新请求加网。', '无');
INSERT INTO `modenodelib` VALUES ('11', '0', 'Z805B', 'Z805B单路电能检测嵌墙开关', '0006', '开关', '3', '1', 'dest', 'Z805B.jpg', 'Z805B是一款无线智能开关控制设备，带有单路16A/250V AC通断能力的继电器，用户可以通过外接开关、绑定设备或软件无线控制开关输出，并且可通过软件查看当前负载的电流电压及功率电能值。', '100～240V的交流电源', '不需要激活', '长按绑定键15S(指示灯闪烁2次,3S闪烁一次，10S闪烁一次），状态灯开始快闪20秒，设备进行恢复出厂设置；20s内短按任意键，Z805B重新请求加网。', '无');
INSERT INTO `modenodelib` VALUES ('12', '0', 'Z810B', 'Z810B电能检测开关', '0006', '开关', '3', '1', 'dest', 'Z810B.jpg', 'Z810B是一款无线智能开关控制设备，使用100-240VAC 50/60HZ电源供电，用户可以通过开关按键、绑定设备或软件无线控制开关输出，并且可通过设备自带的LCD显示屏或软件查看当前负载的电流电压及功率电能值。', '100～240V的交流电源', '不需要激活', '按下绑定键15秒后（图标每隔5秒闪烁一下，闪烁3次），2秒内短按功能键，LCD数值区显示表示恢复完成，之后设备将自动重启。', '无');
INSERT INTO `modenodelib` VALUES ('13', '0', 'Z811', 'Z811四路开关', '0006', '开关', '3', '1', 'dest', 'Z811.jpg', 'Z811是一款无线智能开关控制设备，带有四路输出，用户可以通过绑定设备或软件无线控制开关输出。', '100～240V的交流电源', '不需要激活', '长按绑定键15秒（状态指示灯3S闪一次，10S闪一次,15S闪一次），然后短按，状态指示灯一直闪烁表示擦除完成。之后指示灯灭掉，指示Z811便可以重新加网。', '无');
INSERT INTO `modenodelib` VALUES ('14', '0', 'Z815J', 'Z815J双路电能检测墙面开关', '0006', '开关', '3', '1', 'dest', 'Z815J.jpg', 'Z815J有两路输出的开关设备，可直接安装于86盒中，替换家居中的普通墙面开关，能够检测和统计当前负载电流、电压、功率、电能值。用户可采用ZigBee遥控器、手机、按键开关等多种方式控制该设备。', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('15', '0', 'Z815L', 'Z815L单路电能检测可调墙面开关', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z815L.jpg', 'Z815L是有一路输出的调光设备，能实现开关和调光控制，可直接安装于86盒中，替换家居中的普通墙面开关，能够检测和统计当前负载电流、电压、功率、电能值。用户可采用ZigBee遥控器、手机、按键开关等多种方式控制该设备。', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('16', '0', 'Z815M', 'Z815M双路电能检测可调墙面开关', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z815M.jpg', 'Z815M是有两路输出的调光设备，能实现开关和调光控制，可直接安装于86盒中，替换家居中的普通墙面开关，能够检测和统计当前负载电流、电压、功率、电能值。用户可采用ZigBee遥控器、手机、按键开关等多种方式控制该设备。', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('17', '0', 'Z817A', 'Z817A吸顶电能检测开关', '0006', '开关', '3', '1', 'dest', 'Z817A.jpg', 'Z817A为吸顶式开关控制器，是室内使用的智能电气开关。可以通过按键开关、无线遥控或者软件操作等方式来控制输出的接通与断开。用户可以使用软件查看负载的电流、电压、功率、电能等参量。', '100～240V的交流电源', '不需要激活', '按住绑定键（正对logo靠左）15秒，设备网络灯闪烁一次，然后在2秒内短按任意键，设备网络灯快闪10次后重启。', '无');
INSERT INTO `modenodelib` VALUES ('18', '0', 'Z817B', 'Z817B吸顶电能检测调光开关', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z817B.jpg', ' Z817B为吸顶式调光开关控制器，是室内使用的智能电气开关。可以通过按键调级开关、无线遥控或者软件操作等方式来实现开关和调光控制。用户可以使用软件查看负载的电流、电压、功率、电能等参量。', '100～240V的交流电源', '不需要激活', '按住绑定键（正对logo靠左）15秒，设备网络灯闪烁一次，然后在2秒内短按任意键，设备网络灯快闪10次后重启。', '无');
INSERT INTO `modenodelib` VALUES ('19', '0', 'Z817C', 'Z817C吸顶红外感应开关', '0006', '开关', '3', '1', 'dest', 'Z817C.jpg', 'Z817C具有红外检测的功能，当检测到红外信号，则使外接负载接通，过30秒没有再检测到红外信号则使外接负载断开。设备具有检测外接负载的电流，电压，功率，电能的功能，可以使用软件查看检测到的值。', '100～240V的交流电源', '不需要激活', '按住绑定键（正对logo靠左）15秒，设备网络灯闪烁一次，然后在2秒内短按任意键，设备网络灯快闪10次后重启。', '无');
INSERT INTO `modenodelib` VALUES ('20', '0', 'Z825C', 'Z825C三路电能检测触控开关', '0006', '开关', '3', '1', 'dest', 'Z825C.jpg', 'Z825C是三路触控式墙面开关，可直接安装于86盒中，替换家居中的普通墙面开关，能够检测和统计当前负载电流、电压、功率、电能值。用户可采用ZigBee遥控器、手机、按键开关等多种方式控制该设备。', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib` VALUES ('21', '0', 'Z825E', 'Z825E两路电能检测可调触控开关', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z825E.jpg', 'Z825E是两路触控式墙面调光开关，可直接安装于86盒中，替换家居中的普通墙面开关，能够检测和统计当前负载电流、电压、功率、电能值。用户可采用ZigBee遥控器、手机、按键开关等多种方式控制该设备。', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib` VALUES ('22', '0', 'ZC06', 'ZC06LED调光灯管', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'ZC06.jpg', 'ZC06是可实现灯光亮度渐变256级调节,并可接受遥控器无线控制的高可靠性LED灯管,可以绑定设备、无线遥控或者软件操作等方式来实现开关和调光控制。ZC06采用恒流控制方式，输入电压可以从AC 100V到240V，100到240V输入可以达到相同的灯光效果。', '100～240V的交流电源', '不需要激活', '同时长按测试键和绑定键5s，5s时间到后指示灯闪10次后就恢复出厂设置成功，此时就可以重新加网。', '无');
INSERT INTO `modenodelib` VALUES ('23', '1', 'Z800', 'Z800电能检测插座', '0006', '开关', '3', '1', 'dest', 'Z800B.jpg', 'Z800是无线电流检测插座，设备本身自带按键开关，也可以通过绑定设备、软件等控制其开关。可通过软件查看负载的功率、电压、电流和电能情况。', '100～240V的交流电源', '不需要激活', '长按绑定键15S，状态灯（红灯）快闪20S；设备进入恢复出厂设置；再按任意键重新请求加网。', '无');
INSERT INTO `modenodelib` VALUES ('24', '1', 'Z803', 'Z803电能检测排插', '0006', '开关', '3', '1', 'dest', 'Z803.jpg', 'Z803是无线电流检测排插，带有四路插座和2路USB充电接口。可过设备自带的按键开关、已绑定设备、或软件无线控制Z803开关。设备LCD显示屏可显示所有负载的电流电压值功率，电能值。也可使用软件查看当前各个输出端口及总的负载的电流，电压值，功率，电能值。', '100～240V的交流电源', '不需要激活', '同时按下Match Key 和Bind Key 按键5秒后，！图标闪烁一下，代表长按了5秒。放开按键后，进行出厂设置，设备自动重新上电。', '无');
INSERT INTO `modenodelib` VALUES ('25', '1', 'Z808A', 'Z808A带USB电能检测插座', '0006', '开关', '3', '1', 'dest', 'Z808A.jpg', 'Z808A是无线电流检测插座，设备本身自带USB充电接口，和LCD屏幕。可通过按键开关，绑定设备或软件等控制其开关。LCD屏幕或通过软件可查看负载的功率、电压、电流和电能情况。', '100～240V的交流电源', '不需要激活', '长按绑定键（右边按键）15s（期间网络指示灯闪3次）后释放绑定键，并在2s内短按功能键（左边按键），即开始恢复出厂设置，网络灯快速闪烁十次后自动重启设备，开始寻找新的网络。', '无');
INSERT INTO `modenodelib` VALUES ('26', '1', 'Z808B', 'Z808B带USB电能检测调光插座', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z808B.jpg', 'Z808B是无线电流检测调光插座，设备本身自带USB充电接口，和LCD屏幕。可通过按键开关，绑定设备或软件等控制其开关和调光。LCD屏幕或通过软件可查看负载的功率、电压、电流和电能情况。', '100～240V的交流电源', '不需要激活', '长按绑定键（右边按键）15s（期间网络指示灯闪3次）后释放绑定键，并在2s内短按功能键（左边按键），即开始恢复出厂设置，网络灯快速闪烁十次后自动重启设备，开始寻找新的网络。', '无');
INSERT INTO `modenodelib` VALUES ('27', '1', 'Z809A', 'Z809A无线耗能检测插座', '0006', '开关', '3', '1', 'dest', 'Z809A.jpg', 'Z809A是无线电流检测插座，设备本身自带按键开关，也可以通过绑定设备、软件等控制其开关。可通过软件查看负载的功率、电压、电流和电能情况。', '100～240V的交流电源', '不需要激活', '　长按绑定键（侧面）15s（期间网络指示灯闪3次）后释放绑定键，并在2s内短按测试键（正面按键），即开始恢复出厂设置，网络灯快速闪烁十次后自动重启设备，开始寻找新的网络。	无', '无');
INSERT INTO `modenodelib` VALUES ('28', '1', 'Z809B', 'Z809B电能检测调光插座', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z809B.jpg', 'Z809B是无线电流检测调光插座，设备本身自带按键开关，也可以通过绑定设备、软件等控制其开关和调光。可通过软件查看负载的功率、电压、电流和电能情况。', '100～240V的交流电源', '不需要激活', '长按绑定键（侧面）15s（期间网络指示灯闪3次）后释放绑定键，并在2s内短按测试键（正面按键），即开始恢复出厂设置，网络灯快速闪烁十次后自动重启设备，开始寻找新的网络。', '无');
INSERT INTO `modenodelib` VALUES ('29', '1', 'Z816B', 'Z816B美规电能检测墙面插座', '0006', '开关', '3', '1', 'dest', 'Z816B.jpg', 'Z816B是美规无线电流检测墙面插座，设备本身自带按键开关，也可以通过绑定设备、软件等控制其开关。可通过软件查看负载的功率、电压、电流和电能情况。', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('30', '1', 'Z816G', 'Z816G英规电能检测墙面插座', '0006', '开关', '3', '1', 'dest', 'Z816G.jpg', 'Z816G是英规无线电流检测墙面插座，可直接安装于86盒中，替换家居中的普通墙面开关，设备本身自带按键开关，也可以通过绑定设备、软件等控制其开关。可通过软件查看负载的功率、电压、电流和电能情况。', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('31', '1', 'Z816H', 'Z816H中规电能检测墙面插座', '0006', '开关', '3', '1', 'dest', 'Z816HI.jpg', 'Z816H是中规无线电流检测墙面插座，可直接安装于86盒中，替换家居中的普通墙面开关，设备本身自带按键开关，也可以通过绑定设备、软件等控制其开关。可通过软件查看负载的功率、电压、电流和电能情况。', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('32', '2', 'Z711', 'Z711室内型温湿度感应器', null, null, null, '1', null, 'Z711.jpg', 'Z711是温湿度传感器，用于采集周围环境的温湿度数据，发送给显示设备。', '2节1.5V电池', '按下按键可激活设备', '1.  按住绑定键的同时给设备上电；2.  复位完成则led闪烁30次后停止；3.  重新上电，Z711可以开始重新加网。', '无');
INSERT INTO `modenodelib` VALUES ('33', '2', 'Z712', 'Z712户外型温湿度感应器', '', '', '', '1', '', 'Z712.jpg', 'Z712主要是用来检测室外空气中的温度和湿度，同时也增加了防水外壳进行保护，并通过无线网络将收集到的数据传送给其它设备显示出来.', '2节1.5V电池', '按下按键可激活设备', '1.  按住绑定键的同时给设备上电；2.  复位完成则led闪烁30次后停止；3.  重新上电，Z711可以开始重新加网。', '无');
INSERT INTO `modenodelib` VALUES ('34', '2', 'Z713', 'Z713户外型温湿度紫外线感应器', '', '', '', '1', '', 'Z713.jpg', 'Z713是温湿度及紫外线探测器，带太阳能电池板，同时也增加了防水外壳进行保护，用于采集周围环境的温湿度和紫外线强度，并将采集到的数据发送给显示设备。', '2节1.5V电池', '按下按键可激活设备', '1.  按住绑定键的同时给设备上电；2.  复位完成则led闪烁30次后停止；3.  重新上电，Z711可以开始重新加网。', '无');
INSERT INTO `modenodelib` VALUES ('35', '2', 'Z716A', 'Z713户外型温湿度紫外线感应器', '', '', '', '1', '', 'Z716A.jpg', 'Z716A是温湿度探测器，用于采集周围环境的温湿度在LCD上显示，也可将采集到的数据发送给显示设备。', '2节1.5V电池', '按下右边按键可激活设备', '设备上电后，同时按住“按键1”和“按键2” 5秒，开始恢复出厂值，LCD上显示所有图标画面，表示恢复出厂值完成，设备自动复位寻找加网。', '无');
INSERT INTO `modenodelib` VALUES ('36', '2', 'Z716B', 'Z716B带LCD室内型温度感应器', null, null, null, '1', null, 'Z716B.jpg', 'Z716B是温度探测器，用于采集周围环境的温度在LCD上显示，也可将采集到的数据发送给显示设备。', '2节1.5V电池', '按下右边按键可激活设备', '设备上电后，同时按住“按键1”和“按键2” 5秒，开始恢复出厂值，LCD上显示所有图标画面，表示恢复出厂值完成，设备自动复位寻找加网。', '无');
INSERT INTO `modenodelib` VALUES ('37', '3', 'Z302B', 'Z302B灯控光感器', '0006', '开关', '3', '1', 'source', 'Z302B.jpg', 'Z302B是灯光自动开关控制器，它可以与开关输出的设备绑定。当光线减弱到一定程度，Z302B会发送出On(开灯)命令控制与其绑定的设备；当光线增强到一定程度，Z302B发送Off (关灯)命令给其绑定的设备。', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '无');
INSERT INTO `modenodelib` VALUES ('38', '3', 'Z302H', 'Z302H调光器', '0006:0008', '开关:调级控制', '3', '1', 'source', 'Z302H.jpg', 'Z302H是灯光亮度调节器，能够感知外界光线的强度，并且判断是否需要改变其绑定的亮度可调的灯到新的亮度，从而使其控制的灯所照到的环境保持一个亮度。', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '无');
INSERT INTO `modenodelib` VALUES ('39', '3', 'Z302G', 'Z302G光线感应器', null, null, null, '1', null, 'Z302G.jpg', 'Z302G是环境照度自动侦测器及报告器，定时发送环境照度给能接收和显示环境照度的设备。', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '无');
INSERT INTO `modenodelib` VALUES ('40', '3', 'Z311B', 'Z311B灯控光感器', '0006', '开关', '3', '1', 'source', 'Z311B.jpg', 'Z31B是灯光自动开关控制器，它可以与开关输出的设备绑定。当光线减弱到一定程度，Z311B会发送出On(开灯)命令控制与其绑定的设备；当光线增强到一定程度，Z311B发送Off (关灯)命令给其绑定的设备。', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '同时按下绑定键和辅助键，两键同时按着5s，直到看到红色指示灯开始快闪，再松开按键。红色指示灯快闪20次后，设备复位将进入power off模式。', '无');
INSERT INTO `modenodelib` VALUES ('41', '3', 'Z311H', 'Z311H调光器', '0006:0008', '开关:调级控制', '3', '1', 'source', 'Z311H.jpg', 'Z311H是灯光亮度调节器，能够感知外界光线的强度，并且判断是否需要改变其绑定的亮度可调的灯到新的亮度，从而使其控制的灯所照到的环境保持一个亮度。', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '同时按下绑定键和辅助键，两键同时按着5s，直到看到红色指示灯开始快闪，再松开按键。红色指示灯快闪20次后，设备复位将进入power off模式。', '无');
INSERT INTO `modenodelib` VALUES ('42', '3', 'Z311G', 'Z311G光线感应器', '', '', '', '1', '', 'Z311G.jpg', 'Z311G是环境照度自动侦测器及报告器，定时发送环境照度给能接收和显示环境照度的设备。', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '同时按下绑定键和辅助键，两键同时按着5s，直到看到红色指示灯开始快闪，再松开按键。红色指示灯快闪20次后，设备复位将进入power off模式。', '无');
INSERT INTO `modenodelib` VALUES ('44', '4', 'ZB02B', 'ZB02B双路墙面开关', '0006', '开关', '3', '1', 'source', 'ZB02B.jpg', 'ZB02B是双键无线可壁挂按键开关。可以与有开关控制功能的设备绑定，无线控制其开关。', '2节AAA1.5V电池', '拆下面板的上盖，短按绑定键（红色按键），LED网络灯闪5下激活设备。', '拆下面板的上盖，按住绑定键的同时给设备上电，进行恢复出厂设置，看到状态指示灯闪烁，表示恢复完成', '无');
INSERT INTO `modenodelib` VALUES ('45', '4', 'ZB02C', 'ZB02B三路墙面开关', '0006', '开关', '3', '1', 'source', 'ZB02C.jpg', 'ZB02C是三键无线可壁挂按键开关。可以与有开关控制功能的设备绑定，无线控制其开关。', '2节AAA1.5V电池', '拆下面板的上盖，短按绑定键（红色按键），LED网络灯闪5下激活设备。', '拆下面板的上盖，按住绑定键的同时给设备上电，进行恢复出厂设置，看到状态指示灯闪烁，表示恢复完成', '无');
INSERT INTO `modenodelib` VALUES ('46', '4', 'ZB02F', 'ZB02F单路调光开关', '0006:0008', '开关:调级控制', '3', '1', 'source', 'ZB02F.jpg', 'ZB02F是单键无线可壁挂按键开关。可以与有开关和调级控制功能的设备绑定，无线控制设备的开关和调级。', '2节AAA1.5V电池', '拆下面板的上盖，短按绑定键（红色按键），LED网络灯闪5下激活设备。', '拆下面板的上盖，按住绑定键的同时给设备上电，进行恢复出厂设置，看到状态指示灯闪烁，表示恢复完成。', '无');
INSERT INTO `modenodelib` VALUES ('47', '5', 'ZB01A', 'ZB01A安防动作感应器', null, null, null, '1', null, 'ZB01A.jpg', 'ZB01A红外探测报警功能可检测到移动物体，并通过安防中心产生报警。', '2节CR123A电池或12V直流电源', '按住背面小圆点按键3秒以上，直至状态指示灯快速闪烁2次激活设备', '按住背面小圆点（绑定键）的同时给设备上电，状态灯快闪，即恢复出厂设置操作成功。恢复完成后重新上电就可以重新加入网络了。', '无');
INSERT INTO `modenodelib` VALUES ('48', '5', 'ZB01B', 'ZB01B红外人体感应开关', '0006', '开关', '3', '1', 'source', 'ZB01B.jpg', 'ZB01B是移动探测开关控制器，可检测到产生红外线的物体移动时，根据灯光阈值的设定，控制其他与之绑定设备的开关动作。', '2节CR123A电池或12V直流电源', '按住背面小圆点按键3秒以上，直至状态指示灯快速闪烁2次激活设备', '按住背面小圆点（绑定键）的同时给设备上电，状态灯快闪，即恢复出厂设置操作成功。恢复完成后重新上电就可以重新加入网络了。', '可通过调节设备背面的光照灵敏度来设置绑定开关的的光照阈值。');
INSERT INTO `modenodelib` VALUES ('49', '5', 'ZB01C', 'ZB01C带温度探测的红外感应开关', '0006', '开关', '3', '1', 'source', 'ZB01C.jpg', 'ZB01C同时具有红外探测报警、红外探测开关控制和温度报告3种功能。红外探测报警功能可检测到移动物体，并通过安防中心产生报警。红外检测开关控制功能可检测到移动物体所产生的红外线控制灯的亮灭。温度报告功能可检测当前环境的温度，并定期报告给其绑定的设备。', '2节CR123A电池或12V直流电源', '按住背面小圆点按键3秒以上，直至状态指示灯快速闪烁2次激活设备', '按住背面小圆点（绑定键）的同时给设备上电，状态灯快闪，即恢复出厂设置操作成功。恢复完成后重新上电就可以重新加入网络了。', '可通过调节设备背面的光照灵敏度来设置绑定开关的的光照阈值。');
INSERT INTO `modenodelib` VALUES ('50', '5', 'ZB01D', 'ZB01D红外占用感应器', null, null, null, '1', null, 'ZB01D.jpg', 'ZB01D是占有传感器，可检测到产生红外线的物体的移动，并报告状态给其绑定的设备。', '2节CR123A电池或12V直流电源', '按住背面小圆点(绑定键)按键3秒以上，直至状态指示灯快速闪烁2次激活设备', '按住背面小圆点（绑定键）的同时给设备上电，状态灯快闪，即恢复出厂设置操作成功。恢复完成后重新上电就可以重新加入网络了。', '无');
INSERT INTO `modenodelib` VALUES ('51', '5', 'ZB11A', 'ZB11A安防动作感应器', '', '', '', '1', '', 'ZB11A.jpg', 'ZB11A红外探测报警功能可检测到移动物体，并通过安防中心产生报警。', '2节ER14505 3.6V AA电池供电', '正对logo，短按右侧按钮指示灯闪5下，即可激活设备', '长按辅助键（正对logo右边）10秒，见到绿色指示灯快闪即可松开按键，设备将进行恢复出厂设置操作，完成后将自动关机。', '无');
INSERT INTO `modenodelib` VALUES ('52', '5', 'ZB11B', 'ZB11B红外人体感应开关', '0006', '开关', '3', '1', 'source', 'ZB11B.jpg', 'ZB11B是移动探测开关控制器，可检测到产生红外线的物体移动时，根据灯光阈值的设定，控制其他与之绑定设备的开关动作。', '2节ER14505 3.6V AA电池供电', '正对logo，短按右侧按钮指示灯闪5下，即可激活设备', '长按辅助键（正对logo右边）10秒，见到绿色指示灯快闪即可松开按键，设备将进行恢复出厂设置操作，完成后将自动关机。', '可通过调节设备背面的光照灵敏度来设置绑定开关的的光照阈值。');
INSERT INTO `modenodelib` VALUES ('53', '5', 'ZB11C', 'ZB01C带温度探测的红外感应开关', '0006', '开关', '3', '1', 'source', 'ZB11C.jpg', 'ZB11C同时具有红外探测报警、红外探测开关控制和温度报告3种功能。红外探测报警功能可检测到移动物体，并通过安防中心产生报警。红外检测开关控制功能可检测到移动物体所产生的红外线控制灯的亮灭。温度报告功能可检测当前环境的温度，并定期报告给其绑定的设备。', '2节ER14505 3.6V AA电池供电', '正对logo，短按右侧按钮指示灯闪5下，即可激活设备', '长按辅助键（正对logo右边）10秒，见到绿色指示灯快闪即可松开按键，设备将进行恢复出厂设置操作，完成后将自动关机。', '可通过调节设备背面的光照灵敏度来设置绑定开关的的光照阈值。');
INSERT INTO `modenodelib` VALUES ('54', '6', 'ZB11D', 'Z815N窗帘控制电能检测墙面开关', '', '', '', '1', '', 'ZB11D.jpg', 'ZB11D是占有传感器，可检测到产生红外线的物体的移动，并报告状态给其绑定的设备。', '2节ER14505 3.6V AA电池供电', '正对logo，短按右侧按钮指示灯闪5下，即可激活设备', '长按辅助键（正对logo右边）10秒，见到绿色指示灯快闪即可松开按键，设备将进行恢复出厂设置操作，完成后将自动关机。', '无');
INSERT INTO `modenodelib` VALUES ('55', '6', 'Z815N', 'Z815N窗帘控制墙面开关', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z815N.jpg', 'Z815N是窗帘控制器，可以通过自身的按键、绑定的设备或软件控制窗帘的开关和级别。同时用户可以使用软件查看当前负载的电流、电压、功率和电能值。', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', 'Lin接火线，N线是零线接电机的蓝线. L out1接电机的棕线，L out2接电机的黑线。在设备执行窗帘控制功能前,都应先进行“学习上下限”的学习，学习方法请详见设备说明书或在app的设备管理模块，该设备的设置页面进行学习。');
INSERT INTO `modenodelib` VALUES ('56', '6', 'ZD01B', 'ZD01B窗帘控制器', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'ZD01B.jpg', 'ZD01B是窗帘控制器，可以通过自身的按键、绑定的设备或软件控制窗帘的开关和级别。', '12V4A直流电源', '不需要激活', '按住ON1和OFF1键（靠右边一列）的同时给设备上电，开始执行恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重新上电就可以重新加网了', '在设备执行窗帘控制功能前,都应先进行“学习上下限”的学习，学习方法请详见设备说明书或在app的设备管理模块，该设备的设置页面进行学习');
INSERT INTO `modenodelib` VALUES ('57', '7', 'Z302A', 'Z302A窗磁感應器', null, null, null, '1', null, 'Z302A.jpg', 'Z302A是门磁（窗磁）设备，作为安防系统中的1个检测设备，当门或窗被打开，它将发出报警信息给安防中心设备，当门或窗被关闭好，它会发送出状态恢复正常的信息给安防中心设备。', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', 'Z302A的本体与门磁的磁铁部分别安装在门或窗户的缝隙的两边，当门或窗被打开，它将发出报警信息给安防中心设备( 即CIE )，当门或窗被关闭好，它会发送出状态恢复正常的信息给CIE。该设备也可以用于冰箱、抽屉等处，用于检测冰箱或抽屉等是否被打开。需要注意防水。');
INSERT INTO `modenodelib` VALUES ('58', '7', 'Z302C', 'Z302C窗戶感應器', null, null, null, '1', null, 'Z302C.jpg', 'Z302C是安防系统的1个检测设备，作为门窗被异常打开或者门窗玻璃被打碎的报警设备使用。', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', 'Z302C的本体与门磁的磁铁部分别安装在门或窗户的缝隙的两边，当门或窗被打开，它将发出报警信息给安防中心设备( 即CIE )，当门或窗被关闭好，它会发送出状态恢复正常的信息给CIE。该设备也可以用于冰箱、抽屉等处，用于检测冰箱或抽屉等是否被打开。需要注意防水。');
INSERT INTO `modenodelib` VALUES ('59', '7', 'Z302D', 'Z302D緊急報警器', null, null, null, '1', null, 'Z302D.jpg', 'Z302D是紧急报警触发设备，作为安防系统中的1个检测设备，它可以戴在孩子或者老人的手腕，当孩子或者老人遇到危险需要紧急救助的时候，按下Z302D的报警键，Z302D立即向安防中心发送出报警信息。', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '无');
INSERT INTO `modenodelib` VALUES ('60', '7', 'Z302E', 'Z302E贵重物品标签', null, null, null, '1', null, 'Z302E.jpg', 'Z302E设备内置光敏和位移传感器，可用于外界环境光强检测和位置移动检测，当状态改变时，它将发出报警信息给安防中心设备。', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '可将该设备放于贵重的物品或抽屉旁，当物品被移动或光线发生变化时，则设备会将报警信息发送给安防中心设备。');
INSERT INTO `modenodelib` VALUES ('61', '7', 'Z302J', 'Z302J门磁感应开关', '0006', '开关', '3', '1', 'source', 'Z302J.jpg', 'Z302J是门磁设备，当门或窗的状态变化时，能根据配置控制绑定设备的开/关动作；Z302J还作为安防系统中的1个检测设备，当门或窗被打开，它将发出报警信息给安防中心设备，当门或窗被关闭好，它会发送出状态恢复正常的信息给CIE。', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', 'Z302J的本体与门磁的磁铁部分别安装在门或窗户的缝隙的两边，当门或窗被打开，它将发出报警信息给安防中心设备( 即CIE )，当门或窗被关闭好，它会发送出状态恢复正常的信息给CIE。该设备也可以用于冰箱、抽屉等处，用于检测冰箱或抽屉等是否被打开。需要注意防水。');
INSERT INTO `modenodelib` VALUES ('62', '7', 'Z306D', 'Z306D定位紧急按鈕套件', null, null, null, '1', null, 'Z307.jpg', 'Z306D设备在网络中作为ED使用。是location网络的mobile tags。使用第三方软件（如：netvox公司的ZiG-BUTLER软件）通过它进行设备的搜索与控制。', '3V CR2450钮扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '无');
INSERT INTO `modenodelib` VALUES ('63', '7', 'Z311A', 'Z311A窗磁感應器', null, '', '', '1', '', 'Z311A.jpg', 'Z311A是门磁（窗磁）设备，作为安防系统中的1个检测设备，当门或窗被打开，它将发出报警信息给安防中心设备，当门或窗被关闭好，它会发送出状态恢复正常的信息给安防中心设备。', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '同时按下绑定键和辅助键，两键同时按着5s，直到看到红色指示灯开始快闪，再松开按键。红色指示灯快闪20次后，设备复位将进入power off模式。', 'Z311A的本体与门磁的磁铁部分别安装在门或窗户的缝隙的两边，当门或窗被打开，它将发出报警信息给安防中心设备( 即CIE )，当门或窗被关闭好，它会发送出状态恢复正常的信息给CIE。该设备也可以用于冰箱、抽屉等处，用于检测冰箱或抽屉等是否被打开。需要注意防水。');
INSERT INTO `modenodelib` VALUES ('64', '7', 'Z311C', 'Z311C窗戶感應器', null, '', '', '1', '', 'Z311C.jpg', 'Z311C是安防系统的1个检测设备，作为门窗被异常打开或者门窗玻璃被打碎的报警设备使用。', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '同时按下绑定键和辅助键，两键同时按着5s，直到看到红色指示灯开始快闪，再松开按键。红色指示灯快闪20次后，设备复位将进入power off模式。', 'Z311A的本体与门磁的磁铁部分别安装在门或窗户的缝隙的两边，当门或窗被打开，它将发出报警信息给安防中心设备( 即CIE )，当门或窗被关闭好，它会发送出状态恢复正常的信息给CIE。该设备也可以用于冰箱、抽屉等处，用于检测冰箱或抽屉等是否被打开。需要注意防水。');
INSERT INTO `modenodelib` VALUES ('65', '7', 'Z312', 'Z312无线门铃按键', null, null, null, '1', null, 'Z312.jpg', 'Z312门铃按键可与警报器设备绑定,控制其发出门铃响声。', '3V CR2450纽扣电池', '　　　　当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '同时按下绑定键和辅助键，两键同时按着5s，直到看到红色指示灯开始快闪，再松开按键。红色指示灯快闪20次后，设备复位将进入power off模式。', '无');
INSERT INTO `modenodelib` VALUES ('66', '7', 'Z307', 'Z307充电式跌倒感应器', null, null, null, '1', null, 'Z307.jpg', 'Z307是摔倒感知器，作为安防系统中的1个检测设备来使用。它一般佩戴在家中老人和孩子等需要照顾者的腰间，当佩戴者摔倒时，它将发出报警信息给安防中心设备。', '充电套件', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '无');
INSERT INTO `modenodelib` VALUES ('67', '7', 'Z308', 'Z308无线紧急按钮', null, null, null, '1', null, 'Z308.jpg', 'Z308是紧急报警触发设备，作为安防系统中的1个检测设备，它可以戴在孩子或者老人的手腕，当孩子或者老人遇到危险需要紧急救助的时候，按下Z308的报警键，Z308立即向安防中心发送出报警信息。', '3V CR2450纽扣电池', '长按按键3s，红色指示灯闪一下，松开手后，绿色指示灯闪五下。即激活设备。', '长按报警键15秒以上，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯快闪10次后，设备进入关机状态，红色指示灯熄灭。', '无');
INSERT INTO `modenodelib` VALUES ('68', '7', 'ZA01A', 'ZA01A烟雾感应器', null, null, null, '1', null, 'ZA01A.jpg', 'ZA01A是用于家庭环境的有害气体探测的设备，在网络中担当安防设备的角色。当空气中可检测的气体浓度超过设备的界限时，设备发出告警声，同时向安防中心发送状态改变信息。', '12V直流电源或100～240V交流供电', '不需要激活', '同时按住绑定键和测试键5秒以上，设备指示灯开始快速闪烁，表示复位完成。之后将设备重启，就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('69', '7', 'ZA01B', 'ZA01B瓦斯感应器', '', '', '', '1', '', 'ZA01B.jpg', 'ZA01B是瓦斯探测器，在网络中担当安防设备的角色。当空气中瓦丝的浓度超过所设定的界限时，设备将安防中心发送报警信息，设备本身也会有声音的报警，以提醒用户，确保用户生命财产的安全。', '12V直流电源或100～240V交流供电', '不需要激活', '同时按住绑定键和测试键5秒以上，设备指示灯开始快速闪烁，表示复位完成。之后将设备重启，就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('70', '7', 'ZA01C', 'ZA01C一氧化碳感应器', null, null, null, '1', null, 'ZA01C.jpg', 'ZA01C是一氧化碳感应器，在网络中担当安防设备的角色。当空气中一氧化碳的浓度超过所设定的界限时，设备将安防中心发送报警信息，设备本身也会有声音的报警，以提醒用户，确保用户生命财产的安全。', '12V直流电源或100～240V交流供电', '不需要激活', '同时按住绑定键和测试键5秒以上，设备指示灯开始快速闪烁，表示复位完成。之后将设备重启，就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('71', '7', 'ZA01D', 'ZA01D液化石油气感应', null, null, null, '1', null, 'ZA01D.jpg', 'ZA01D是液态瓦斯探测器，在网络中担当安防设备的角色。当空气中液态瓦斯的浓度超过所设定的界限时，设备将安防中心发送报警信息，设备本身也会有声音的报警，以提醒用户，确保用户生命财产的安全。', '12V直流电源或100～240V交流供电', '不需要激活', '同时按住绑定键和测试键5秒以上，设备指示灯开始快速闪烁，表示复位完成。之后将设备重启，就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('72', '7', 'ZA02E', 'ZA02E烟雾感应器', null, null, null, '1', null, 'ZA02E.jpg', 'ZA02E是烟雾探测器，当空气中烟雾的浓度超过所设定的界限时，设备将安防中心发送报警信息，设备本身也会有声音的报警，以提醒用户，确保用户生命财产的安全。', '12V直流电源或100～241V交流供电', '不需要激活', '同时按住绑定键和测试键5秒以上，设备指示灯开始快速闪烁，表示复位完成。之后将设备重启，就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('73', '7', 'ZA10', 'ZA10智能阀门', '0006', '开关', '3', '1', 'dest', 'ZA10.jpg', 'ZA10可作为煤气阀门和水闸开关。做为Gas Keeper时，当网络中报火警时，设备会根据接收到的火警命令将管道关闭。', '12V直流电源', '不需要激活', '按住按键的同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复出厂设置完成，重新上电就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('74', '7', 'ZB05', 'ZB05智能门锁', '0101', '开关锁', '3', '1', 'dest', 'ZB05.jpg', 'ZB05可对锁进行开、关控制，包括机械钥匙、密码、IC卡、远程控制等多种方式。用户可通过ZigBee无线网络随时查看和控制门锁状态，智能化的门锁设计拥有可靠的密码保障和感应火灾性能，保障家居安全。', '8节AA1.5V电池', '不需要激活', '1.按住网络键的同时给设备上电，上电完请不要立即松开按键，等到网络灯开始快速闪烁，提示复位成功；2.重新上电，ZB05可以开始重新加网。', '1、初次使用时，需要注册zigbee：在门打开的状态下，打开内侧用（主锁身）的电池盖和外盒，在右侧下端按“锁卡注册键”。然后再按下面的“ZB注册键”（靠上的按键）。按“*”键，可听到“哔呖呖”的设置音，设置结束。2、默认都为加密的方式，第一次使用时，需要在app中→设备管理模块→进入门锁的设置页面，设置新的用户和密码，才可以对锁进行开关控制。门锁默认有6个空用户，注册新用户前，需要先将这些用户删除。超级密码默认为：123456.usercode即用户名为1-254的数字。');
INSERT INTO `modenodelib` VALUES ('75', '7', 'ZB02E', 'ZB02E门铃按键', null, null, null, '1', null, 'ZB02E.jpg', 'ZB02E门铃按键可与警报器设备绑定,控制其发出门铃响声。', '2节AAA1.5V电池', '拆下面板的上盖，短按绑定键（红色按键），LED网络灯闪5下激活设备。', '拆下面板的上盖，按住绑定键的同时给设备上电，进行恢复出厂设置，看到状态指示灯闪烁，表示恢复完成', '无');
INSERT INTO `modenodelib` VALUES ('76', '7', 'Z311E', 'Z311E贵重物品标签', '', '', '', '1', '', 'Z311E.jpg', 'Z311E设备内置光敏和位移传感器，可用于外界环境光强检测和位置移动检测，当状态改变时，它将发出报警信息给安防中心设备。', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '同时按下绑定键和辅助键，两键同时按着5s，直到看到红色指示灯开始快闪，再松开按键。红色指示灯快闪20次后，设备复位将进入power off模式。', '可将该设备放于贵重的物品或抽屉旁，当物品被移动或光线发生变化时，则设备会将报警信息发送给安防中心设备。');
INSERT INTO `modenodelib` VALUES ('77', '7', 'Z602A', 'Z602A智能声光报警器', null, null, null, '1', null, 'Z602A.jpg', 'Z602A是报警器，通过指示灯闪烁、喇叭发出报警声音对安防系统内的警情进行报警。', '12V直流电源和3节1.2V可充电备用电池', '不需要激活', '按住设置键（正对logo右侧按键）的同时给设备上电，状态灯快速闪烁且发出两次门铃报警声，即恢复出厂设置操作成功。恢复完成后重新上电就可以重新加入网络了。', '无');
INSERT INTO `modenodelib` VALUES ('78', '7', 'Z602B', 'Z602B智能声光报警器', null, null, null, '1', null, 'Z602B.jpg', 'Z602B是报警器，通过指示灯闪烁、喇叭发出报警声音对安防系统内的警情进行报警。\n设备在发出报警音的同时，根据报警的级别选择通过语音呼叫和短信发送方式通知预先设置的手机号，可以实时收发语音信息或短消息。', '12V直流电源和3节1.2V可充电备用电池', '不需要激活', '按住设置键（正对logo右侧按键）的同时给设备上电，状态灯快速闪烁且发出两次门铃报警声，即恢复出厂设置操作成功。恢复完成后重新上电就可以重新加入网络了。', '该设备具有GSM功能，可在电池盒内装入SIM卡，在app的设备管理模块，进入该设备的设置页面，可设置电话号码，用于报警时，通过拨打电话和发送短信通知用户。设置的短信号码前，需要加国家代码（如中国：86）');
INSERT INTO `modenodelib` VALUES ('79', '7', 'Z601A', 'Z601A警报器', null, null, null, '1', null, 'Z601A.jpg', 'Z601A是报警器，通过喇叭发出报警声音对安防系统内的警情进行报警。', '12V直流电源', '不需要激活', '按住绑定键的同时给设备上电，状态灯快闪，即恢复出厂设置操作成功。恢复完成后重新上电就可以重新加入网络了。', '无');
INSERT INTO `modenodelib` VALUES ('80', '8', 'Z821', 'Z821多路电能检测器', null, null, null, '1', null, 'Z821.jpg', 'Z821是室内使用的智能多路电能检测器,可以检测单路输入电源电压和七路设备的电流，并且可以算得其功率、电能等参量。', '100～240V的交流电源', '不需要激活', '按下Bind Key按键15秒后（状态灯每5秒钟闪烁1次，共闪烁3次，代表长按了15秒），松开按键，在两秒之内短按Match Key进行网络信息的擦除。网络信息擦除后设备自动重启寻找网络。', '将七路CT端子（用于检测电能）接入到Z821两侧的接口。按照CT端子上指示的K→L方向（电流流动的方向）,将导线扣入CT端子，即可检测该路的电能。');
INSERT INTO `modenodelib` VALUES ('81', '9', 'Z501A', 'Z501A四鍵遙控器(250)', '0006:0008', '开关:调级控制', '3', '1', 'source', 'Z501A.jpg', 'Z501A做为远程遥控器，它可与开关及级别可控的设备进行绑定，来控制其开关及级别值，同时设备还设有紧急按钮，在遇到紧急情况时按下紧急按钮通知家人以寻求帮助。', '3V CR2032纽扣电池', '右侧开关off断电，重新上电后即可激活设备。', '按住“开关键” 的同时给设备上电，开始恢复出厂值，指示灯共闪烁20次，之后重新操作组合键就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('82', '9', 'Z501B', 'Z501A四鍵可调级遙控器(250)', '0006', '开关', '3', '1', 'source', 'Z501B.jpg', 'Z501B做为远程遥控器，它可与开关可控的设备进行绑定，来控制其他设备的开关。', '3V CR2032纽扣电池', '右侧开关off断电，重新上电后即可激活设备。', '按住“ON2键” 的同时给设备上电，开始恢复出厂值，指示灯共闪烁20次，之后重新操作组合键就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('83', '9', 'Z501C', 'Z501C四鍵可调级遙控器(250)', '0006:0008', '开关:调级控制', '3', '1', 'source', 'Z501C.jpg', 'Z501C可与具有开关功能/级别控制功能的设备绑定控制其开关及级别值。', '3V CR2033纽扣电池', '右侧开关off断电，重新上电后即可激活设备。', '按住“ ON/OFF1键” 的同时给设备上电，开始恢复出厂值，指示灯共闪烁20次，之后重新操作组合键就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('84', '10', 'Z210', 'Z210红外线转接器', null, null, null, '1', null, 'Z210C.jpg', 'Z210具有安防系统控制指示中心的功能，可管理安防设备。此外，Z210具有红外功能，可学习和转发各种红外信号（如电视，DVD等）。', '12V直流电源', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '使用红外转接器控制家用电器时，需要在app上导入红外数据，若无红外数据时，则需要通过IR学习器或手机app手动学习。');
INSERT INTO `modenodelib` VALUES ('85', '10', 'Z211', 'Z211智能红外控制转换器', null, null, null, '1', null, 'Z211.jpg', 'Z211是红外学习及红外控制的设备，经过IR学习后其可以直接控制可以接收IR控制的电器设备，同时可以接收红外遥控器发出IR信号直接控制ZigBee设备。', '12V直流电源', '不需要激活', '按住绑定键（蓝色按键），同时给设备上电，开始恢复出厂设置，直到状态指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '使用红外转接器控制家用电器时，需要在app上导入红外数据，若无红外数据时，则需要通过IR学习器或手机app手动学习。');
INSERT INTO `modenodelib` VALUES ('86', '11', 'Z203', 'Z203云智能网关', null, null, null, '1', null, 'Z203.jpg', 'Z203 作为奈伯思智能家居系统的核心网关，率先实现云端技术、WiFi移动联网技术与ZigBee智能家居物联网术的完美结合，让用户只需要通过手机APP连接Wifi即可控制家里的灯光、窗帘、各种电器等设备的开关，出门在外通过2G/3G网络可监控家中的一切变化。', '12V直流电源', '不需要激活', '详见203说明书', 'Z203可用12V直流电源供电，给Z203上电后，打开手机的WiFi功能，即可搜索到Z203的无线信号。外网网线接入Z203的WAN口，LAN口可直接接IPcamera或电脑，当家中有多个 IPcamera时，需要增加集线器。');
INSERT INTO `modenodelib` VALUES ('87', '11', 'Z103AC', 'Z103USB协调器', null, null, null, '1', null, 'Z103AC.jpg', 'USB协调器', 'USB', '不需要激活', '按住PermitJoin键的同时给Z103设备上电，设备指示灯处于快速闪烁状态，表示恢复出厂设置完成，设备重新上电就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('88', '11', 'Z103AR', 'Z103USB路由器', null, null, null, '1', null, 'Z103AR.jpg', 'USB路由器', 'USB', '不需要激活', '按住PermitJoin键的同时给Z103设备上电，设备指示灯处于快速闪烁状态，表示恢复出厂设置完成，设备重新上电就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('89', '11', 'Z201B', 'Z201B HA 协调器+CIE', null, null, null, '1', null, 'Z201B.jpg', 'HA 协调器+CIE', '12V直流电源', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');
INSERT INTO `modenodelib` VALUES ('90', '11', 'Z201C', 'Z201C HA 协调器', null, null, null, '1', null, 'Z201C.jpg', 'HA 协调器', '12V直流电源', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');
INSERT INTO `modenodelib` VALUES ('91', '11', 'Z201R HA', 'Z201R HA中继器', null, null, null, '1', null, 'Z201R.jpg', 'HA中继器', '12V直流电源', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');
INSERT INTO `modenodelib` VALUES ('92', '11', 'Z201R', 'Z201R 定位用参考点', null, null, null, '1', null, 'Z201R.jpg', '定位参考点', '12V直流电源', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');
INSERT INTO `modenodelib` VALUES ('93', '4', 'Z812A', 'Z812A多键可编程场景控制面板', '0006', '开关', '3', '1', 'source', 'Z812A.jpg', 'Z812A是多路无线可壁挂按键开关。可以与有开关控制功能的设备绑定，无线控制其开关。', '100～240V的交流电源', '不需要激活', '上电以后，同时按住OFF3和OFF4键，按住5秒，网络灯灭，然后放开按键，开始恢复出厂值，直到网络灯开始闪烁，表示恢复完成。之后设备将自动复位，可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('94', '4', 'Z812B', 'Z812B多键电池驱动型控制面板', '0006:0008', '开关:调级控制', '3', '1', 'source', 'Z812B.jpg', 'Z812B是多路无线可壁挂按键开关。可以与有开关控制功能的设备绑定，无线控制其开关。', '100～240V的交流电源', '同时按住第一行两个按键即可激活设备。', '同时按住按键3和按键4达3秒，指示灯闪烁1次提示设备开始恢复出厂设置，恢复成功指示灯快闪10次，不成功指示灯无动作。恢复出厂设置之后设备自动重启。', '无');
INSERT INTO `modenodelib` VALUES ('95', '7', 'Z801TXB', 'Z801TXB脉冲信号探测器', '', '', '', '1', '', 'Z801TXB.jpg', 'Z801TXB脉冲信号探测器', '5V直流电源或2节1.5V电池', '不需要激活', '按住绑定键的同时给设备上电，LED2闪烁20次，表示复位完成，之后重新上电，Z801 TXB可以重新加网。 ', '无');
INSERT INTO `modenodelib` VALUES ('96', '0', 'Z801RX', 'Z801RX弱电继电器', '0006', '开关', '3', '1', 'dest', 'Z801RX.jpg', 'Z801RX弱电继电器', '2节1.2V电池', '不需要激活', '1.  按住绑定键的同时给设备上电；2.  复位完成则状态指示灯快速灯闪烁；3.  重新上电，Z801RX可以开始重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('100', '11', 'Z508', 'Z508LCD资讯显示屏', null, null, null, '1', null, 'Z508.jpg', 'Z508是一款用于显示家居中各类资讯和统计各种居家电能数据的智能显示终端。Z508作为智能家居的集控中心，用户可以在其屏幕上查看当前和历史用电信息。通过ZiG-BUTLER同步下载智能家居模式后通过Z508用户可以方便地对智能家居模式进行切换选择，同时Z508具有CIE的功能可以管理安防系统，并将安防相关信息显示在其屏幕上。', '5V直流电源或2节1.5V电池', '不需要激活', '详见Z508说明书', '无');
INSERT INTO `modenodelib` VALUES ('101', '0', 'Z825A', 'Z825A一路触控式墙面开关', '0006', '开关', '3', '1', 'dest', 'Z825A.jpg', 'Z825A是一路触控式墙面开关，可直接安装于86盒中，替换家居中的普通墙面开关，能够检测和统计当前负载电流、电压、功率、电能值。用户可采用ZigBee遥控器、手机、按键开关等多种方式控制该设备。', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib` VALUES ('102', '0', 'Z825B', 'Z825B二路触控式墙面开关', '0006', '开关', '3', '1', 'dest', 'Z825B.jpg', 'Z825B是两路触控式墙面开关，可直接安装于86盒中，替换家居中的普通墙面开关，能够检测和统计当前负载电流、电压、功率、电能值。用户可采用ZigBee遥控器、手机、按键开关等多种方式控制该设备。', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib` VALUES ('103', '0', 'Z825D', 'Z825D一路触控式墙面调光开关', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z825D.jpg', 'Z825D是一路触控式墙面调光开关，可直接安装于86盒中，替换家居中的普通墙面开关，能够检测和统计当前负载电流、电压、功率、电能值。用户可采用ZigBee遥控器、手机、按键开关等多种方式控制该设备。', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib` VALUES ('104', '2', 'Z725A', 'Z725A户外温湿度探测器', null, null, null, '1', null, 'Z725A.jpg', 'Z725A是温湿度及紫外线探测器，带太阳能电池板，同时也增加了防水外壳进行保护，用于采集周围环境的温湿度和紫外线强度，并将采集到的数据发送给显示设备。', '太阳能充电电池', '不需要激活', '设备上电后，同时按住“绑定键”和“设置键” 5秒，开始恢复出厂值，LED快闪10次，表示恢复出厂值完成，设备自动复位寻找加网。', '无');
INSERT INTO `modenodelib` VALUES ('106', '11', 'Z725R', 'Z725R户外型网络中继器', null, null, null, '1', null, 'Z725R.jpg', 'Z725R户外型网络中继器', '太阳能充电电池', '不需要激活', '设备上电后，同时按住绑定键和辅助键5秒，状态灯闪烁1次后，开始恢复出厂值，LED快闪20次，表示恢复出厂值完成，设备自动复位寻找加网。', '无');
INSERT INTO `modenodelib` VALUES ('107', '0', 'Z962A', 'Z962A一路触控式墙面开关', '0006', '开关', '3', '1', 'dest', 'Z962A.jpg', 'Z962A是一路触控式墙面开关，可直接安装于86盒中，替换家居中的普通墙面开关，能够检测和统计当前负载电流、电压、功率、电能值。用户可采用ZigBee遥控器、手机、按键开关等多种方式控制该设备。', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib` VALUES ('108', '0', 'Z962B', 'Z962B二路触控式墙面开关', '0006', '开关', '3', '1', 'dest', 'Z962B.jpg', 'Z962B是两路触控式墙面开关，可直接安装于86盒中，替换家居中的普通墙面开关，能够检测和统计当前负载电流、电压、功率、电能值。用户可采用ZigBee遥控器、手机、按键开关等多种方式控制该设备。', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib` VALUES ('109', '0', 'Z962C', 'Z962C三路触控式墙面开关', '0006', '开关', '3', '1', 'dest', 'Z962C.jpg', 'Z962C是三路触控式墙面开关，可直接安装于86盒中，替换家居中的普通墙面开关，能够检测和统计当前负载电流、电压、功率、电能值。用户可采用ZigBee遥控器、手机、按键开关等多种方式控制该设备。', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib` VALUES ('110', '0', 'Z962D', 'Z962D一键触摸情景开关', null, null, null, '1', null, 'Z962D.jpg', 'Z962D作为情景和模式选择器，不仅能通过1个触摸按键实现添加组、存储情景和调用场景的功能来实现记忆1个场景的作用。且能支持的奈伯思系统模式控制功能。', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib` VALUES ('111', '0', 'Z962E', 'Z962E双键触摸情景开关', null, null, null, '1', null, 'Z962E.jpg', 'Z962E作为情景和模式选择器，不仅能通过2个触摸按键实现添加组、存储情景和调用场景的功能来实现记忆2个场景的作用。且能支持的奈伯思系统模式控制功能。', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib` VALUES ('112', '0', 'Z962F', 'Z962F三键触摸情景开关', null, null, null, '1', null, 'Z962F.jpg', 'Z962F作为情景和模式选择器，不仅能通过3个触摸按键实现添加组、存储情景和调用场景的功能来实现记忆3个场景的作用。且能支持的奈伯思系统模式控制功能。', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib` VALUES ('113', '0', 'Z962G', 'Z962G一路开关输出和一路情景开关', '0006', '开关', '3', '1', 'dest', 'Z962G.jpg', 'Z962G一路开关输出和一路情景开关，可直接安装于86盒中，替换家居中的普通墙面开关，能够检测和统计当前负载电流、电压、功率、电能值。用户可采用ZigBee遥控器、手机、按键开关等多种方式控制该设备。也可以通过情景开关按键调用情景及模式。', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib` VALUES ('114', '0', 'Z962H', 'Z962H一路开关输出和两路情景开关', '0006', '开关', '3', '1', 'dest', 'Z962H.jpg', 'Z962H一路开关输出和两路情景开关，可直接安装于86盒中，替换家居中的普通墙面开关，能够检测和统计当前负载电流、电压、功率、电能值。用户可采用ZigBee遥控器、手机、按键开关等多种方式控制该设备。也可以通过情景开关按键调用情景及模式。', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib` VALUES ('115', '0', 'Z962I', 'Z962I两路开关输出和一路情景开关', '0006', '开关', '3', '1', 'dest', 'Z962I.jpg', 'Z962I两路开关输出和一路情景开关，可直接安装于86盒中，替换家居中的普通墙面开关，能够检测和统计当前负载电流、电压、功率、电能值。用户可采用ZigBee遥控器、手机、按键开关等多种方式控制该设备。也可以通过情景开关按键调用情景及模式。', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib` VALUES ('116', '7', 'Z801WLS', 'Z801WLS水浸报警器', '', '', '', '1', '', 'Z801WLS.jpg', 'Z801WLS水浸报警器', '5V直流电源或2节1.5V电池', '不需要激活', '按住绑定键的同时给设备上电，LED2闪烁20次，表示复位完成，之后重新上电，Z801WLS可以重新加网。 ', '无');
INSERT INTO `modenodelib` VALUES ('117', '7', 'ZB05A', 'ZB05A智能门锁', '0101', '开关锁', '3', '1', 'dest', 'ZB05A.jpg', 'ZB05A可对锁进行开、关控制，包括机械钥匙、密码、IC卡、指纹、远程控制等多种方式。用户可通过ZigBee无线网络随时查看和控制门锁状态，管理用户名和密码。智能化的门锁设计拥有可靠的密码保障和感应火灾性能，保障家居安全。', '8节AA1.5V电池', '不需要激活', '1.按住网络键的同时给设备上电，上电完请不要立即松开按键，等到网络灯开始快速闪烁，提示复位成功；2.重新上电，ZB05A可以开始重新加网。', '1、初次使用时，需要注册zigbee：在门打开的状态下，打开内侧用（主锁身）的电池盖和外盒，在右侧下端按“锁卡注册键”。然后再按下面的“ZB注册键”（靠上的按键）。按“*”键，可听到“哔呖呖”的设置音，设置结束。2、默认都为加密的方式，第一次使用时，需要在app中→设备管理模块→进入门锁的设置页面，设置新的用户和密码，才可以对锁进行开关控制。门锁默认有6个空用户，注册新用户前，需要先将这些用户删除。超级密码默认为：123456.usercode即用户名为1-254的数字。');
INSERT INTO `modenodelib` VALUES ('118', '4', 'Z801TX', 'Z801TX开关信号转换器', '0006', '开关', '3', '1', 'source', 'Z801TX.jpg', 'NETVOX的Z801TX在网络中作为终端设备（End device）使用,不允许其他设备做为其子设备。Z801TX可外接5个按键。按键触发时，Z801TX检测到信号，发送命令給绑定的设备控制其开关。', '无', '无', '无', '无');
INSERT INTO `modenodelib` VALUES ('119', '7', 'Z311J', 'Z311J无线窗磁感应器', '0006', '开关', '3', '1', 'source', 'Z311J.jpg', 'Z311J是门磁设备，当门或窗的状态变化时，能根据配置控制绑定设备的开/关动作；Z311J还作为安防系统中的1个检测设备，当门或窗被打开，它将发出报警信息给安防中心设备，当门或窗被关闭好，它会发送出状态恢复正常的信息给CIE。', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', 'Z311J的本体与门磁的磁铁部分别安装在门或窗户的缝隙的两边，当门或窗被打开，它将发出报警信息给安防中心设备( 即CIE )，当门或窗被关闭好，它会发送出状态恢复正常的信息给CIE。该设备也可以用于冰箱、抽屉等处，用于检测冰箱或抽屉等是否被打开。需要注意防水。');
INSERT INTO `modenodelib` VALUES ('120', '0', 'ZC07', 'ZC07无线可调光LED灯泡', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'ZC07.jpg', 'ZC07是可实现灯光亮度256级调光,并可接受遥控器无线控制的高可靠性LED灯炮,可以绑定设备、无线遥控或者软件操作等方式来实现开关和调光控制。ZC07为5W灯泡，采用恒流控制方式，输入电压可以从AC 100V到240V，100到240V输入可以达到相同的灯光效果。', '100～240V的交流电源', '不需要激活', '同时长按测试键和绑定键5s，5s时间到后指示灯闪10次后就恢复出厂设置成功，此时就可以重新加网。', '无');
INSERT INTO `modenodelib` VALUES ('121', '7', 'Z311W', 'Z311W水浸报警器', null, null, null, '1', '', 'Z311W.jpg', 'Z311W水浸报警器', '无需外加电源,产品使用内部的3V钮扣电池供电。', '同时按住绑定键和辅助键，看到红色指示灯灯闪烁2次，此时松开按键。', '同时按住绑定键和辅助键，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '无');
INSERT INTO `modenodelib` VALUES ('122', '6', 'Z811B', 'Z811B双路窗帘机控制器', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z811B.jpg', 'Z811B双路是窗帘控制器，可以通过自身的按键、绑定的设备或软件控制窗帘的开关和级别。', '100-240V AC 50/60HZ电源供电', '不需要激活', '方法一：按住“绑定键（logo旁的小按钮）”上电，指示灯快闪，表示恢复出厂设置完成，再次重新上电后即可。方法二：长按住“绑定键（logo旁的小按钮）”键的15秒待指示灯闪烁一次（此间指示灯3秒、10秒、15秒各闪烁一次），松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，自动重启就可以重新加网了。', 'Lin接火线，N线是零线接电机的零线（一般为蓝线）。EP1：Lout1接电机正转输入（一般为棕线)，Lout2接电机反转输入（一般为黑线）。EP2：Lout3接电机正转输入（一般为棕线)，Lout4接电机反转输入（一般为黑线）。在设备执行窗帘控制功能前,都应先进行“学习上下限”的学习，学习方法请详见设备说明书或在app的设备管理模块，该设备的设置页面进行学习。');
INSERT INTO `modenodelib` VALUES ('123', '0', 'Z825I', 'Z825I六路情景控制器', '', '', '', '1', '', 'Z825I.jpg', 'Z825I作为Scene Selector&Mode Selector，它通过6个触摸按键实现添加存储灯光场景来实现记忆6个场景的作用。并且能通过配置，选定与某个情景模式绑定，可实现在面板上对模式的控制，一路按键最多可配置4个情景控制。', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib` VALUES ('124', '0', 'Z825J', 'Z825J三路开关输出和三路情景开关', '0006', '开关', '3', '1', 'dest', 'Z825J.jpg', 'Z825J三路开关输出和三路情景开关，可直接安装于86盒中，替换家居中的普通墙面开关，能够检测和统计当前负载电流、电压、功率、电能值。用户可采用ZigBee遥控器、手机、按键开关等多种方式控制该设备。也可以通过情景开关按键调用情景及模式。', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib` VALUES ('125', '0', 'Z825Q', 'Z825Q三路本地开关控制器', '0006', '开关', '3', '1', 'dest', 'Z825Q.jpg', 'Z825Q是单火线取电的三路本地开关控制器，可与具有开关功能的设备绑定，通过已绑定设备来控制Z825Q开关，也可通过Z825Q设备本身自带的开关来控制，Z825Q具有三路控制开关,由三个开关控制。', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络青绿色指示灯闪烁一次（期间网络青绿色指示灯闪烁4次，其中刚按下闪一次，3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络青绿色指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会进入睡眠模式，短按绑定键Z825Q可重新加网。 ', 'Z825Q具有单路输入，三路输出的能力，接线时，将一种线（N或L，来自市电）接入输入口，然后在三路输出口引出同种类型的输出线（给设备供电），将插头接入到AC  100-240V的电源，给设备上电。');
INSERT INTO `modenodelib` VALUES ('126', '1', 'Z816I', 'Z816I中规电能检测墙面插座', '0006', '开关', '3', '1', 'dest', 'Z816I.jpg', 'Z816I是中规无线电流检测墙面插座，可直接安装于86盒中，替换家居中的普通墙面开关，设备本身自带按键开关，也可以通过绑定设备、软件等控制其开关。可通过软件查看负载的功率、电压、电流和电能情况。', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('127', '5', 'Z817D', 'Z817D吸顶动作感应开关', '0006', '开关', '3', '1', 'source', 'Z817D.jpg', 'Z817D设备具有红外探测开关控制功能，当检测到移动物体所产生的红外线，控制设备的开关。', 'AC100-240V 50/60HZ供电', '不需要激活', '给设备上电，同时按住“按键1”和“按键2”5秒；复位完成则led闪烁30次后自动重新请求加网；', '无');
INSERT INTO `modenodelib` VALUES ('128', '11', 'Z800R', 'Z800R带插座无线中继路由器', null, null, null, '1', null, 'Z800R.jpg', 'Z800R是带一个插座无线路由中继器,在网络中作为路由中继设备，允许其他设备做为其子设备。为其他设备间通信提供扩展通信距离的功能。', 'AC 100-250V的电源', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重新上电就可以加入新的网络了。', '无');
INSERT INTO `modenodelib` VALUES ('129', '1', 'Z809C', 'Z809C带插座无线中继路由器', null, null, null, '1', null, 'Z809C.jpg', 'Z809C是带一个插座无线路由中继器,在网络中作为路由中继设备，允许其他设备做为其子设备。为其他设备间通信提供扩展通信距离的功能。', 'AC 100-240V的电源或电池供电（内置电池）', '不需要激活', '长按绑定键15s，15s时间到状态闪1次后放开绑定键，两秒内短按绑定键就状态灯闪烁10次，并自动复位。其中长按绑定键3s，10s和15s的时候状态灯都会依次闪烁一次，以提示所按的时间长短。', '无');
INSERT INTO `modenodelib` VALUES ('130', '1', 'Z809D', 'Z809D带插座无线中继路由器', '', '', '', '1', '', 'Z809D.jpg', 'Z809D带一个插座无线中继路由器.在网络中作为路由中继设备，允许其他设备做为其子设备。为其他设备间通信提供扩展通信距离的功能。', 'AC 100-240V的电源或电池供电（内置电池）', '不需要激活', '长按绑定键15s，15s时间到指示闪1次后放开绑定键，两秒内短按绑定键就指示灯闪烁10次，并自动重启设备（如果仅仅用电池供电不能重启）。', '无');
INSERT INTO `modenodelib` VALUES ('131', '9', 'Z501AE3ED', 'Z501A四鍵遙控器(357)', '0006:0008', '开关:调级控制', '3', '1', 'source', 'Z501A.jpg', 'Z501A做为远程遥控器，它可与开关及级别可控的设备进行绑定，来控制其开关及级别值，同时设备还设有紧急按钮，在遇到紧急情况时按下紧急按钮通知家人以寻求帮助。', '3V CR2032纽扣电池', '右侧开关off断电，重新上电后即可激活设备。', '按住“开关键” 的同时给设备上电，开始恢复出厂值，指示灯共闪烁20次，之后重新操作组合键就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('132', '9', 'Z501BE3ED', 'Z501B四鍵遙控器(357)', '0006', '开关', '3', '1', 'source', 'Z501B.jpg', 'Z501B做为远程遥控器，它可与开关可控的设备进行绑定，来控制其他设备的开关。', '3V CR2032纽扣电池', '右侧开关off断电，重新上电后即可激活设备。', '按住“ON2键” 的同时给设备上电，开始恢复出厂值，指示灯共闪烁20次，之后重新操作组合键就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('133', '9', 'Z501CE3ED', 'Z501C四鍵遙控器(357)', '0006:0008', '开关:调级控制', '3', '1', 'source', 'Z501C.jpg', 'Z501C可与具有开关功能/级别控制功能的设备绑定控制其开关及级别值。', '3V CR2033纽扣电池', '右侧开关off断电，重新上电后即可激活设备。', '按住“ ON/OFF1键” 的同时给设备上电，开始恢复出厂值，指示灯共闪烁20次，之后重新操作组合键就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('134', '7', 'ZB02I', 'ZB02I无线墙面紧急按键', '', '', '', '1', '', 'ZB02E.jpg', 'ZB02I是紧急报警触发设备，，它作为安防系统中的1个检测设备，可贴于墙上或放置与屋内任何位置。当人们遇到危险需要紧急救助的时候，按下ZB02I的报警键，ZB02I立即向安防中心发送出报警信息，安防中心会使警报器发出声音和灯光提醒，家里人听到或者看到报警信号能立即提供帮助。', 'AAA电池供电', '短按绑定键，若设备仍在网络状态，则绿色指示灯闪烁5次，表示激活成功。', '按住绑定键的同时给设备上电，进行恢复出厂设置，看到指示灯闪烁，表示恢复完成。', '无');
INSERT INTO `modenodelib` VALUES ('137', '0', 'Z802', 'Z802 Zigbee 两用双路开关模块', '0006', '开关', '3', '1', 'dest', 'Z802.jpg', 'Z802可与具有开关功能的设备绑定控制其开关。用戶可以通過機械开关来控制负载的通斷，也可通过無線方式控制负载的通斷。同時可通過上位機軟件（ZiG-BUTLERr）查看設備兩路的當前通斷狀態，并可做出相應的控制', '100-240VAC 50/60HZ 电源供电', '不需要激活', '按住绑定键的同时，给设备上电，开始恢复出厂值，直到LED1指示灯开始闪烁，表示恢复完成。之后重新上电，Z802可以重新加网了', '无');
INSERT INTO `modenodelib` VALUES ('138', '4', 'ZB02J', 'ZB02J三路情景开关', '', '', '', '1', '', 'ZB02C.jpg', 'ZB02J三路情景开关', '无需外加电源,产品使用内部的2节7号电池供电', '短按绑定键（led网络灯闪5下）', '按住绑定键不松开，装入电池，进行恢复出厂设置，看到状态灯闪烁，表示恢复完成，然后松开按键，拿出电池', '无');
INSERT INTO `modenodelib` VALUES ('139', '0', 'Z815A', 'Z815A单路电能检测墙面开关', '0006', '开关', '3', '1', 'dest', 'Z815I.jpg', 'Z815A是一路输出的开关设备，可直接安装于86盒中，替换家居中的普通墙面开关，能够检测和统计当前负载电流、电压、功率、电能值。用户可采用ZigBee遥控器、手机、按键开关等多种方式控制该设备。', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('140', '0', 'Z815B', 'Z815B双路电能检测墙面开关', '0006', '开关', '3', '1', 'dest', 'Z815J.jpg', 'Z815B有两路输出的开关设备，可直接安装于86盒中，替换家居中的普通墙面开关，能够检测和统计当前负载电流、电压、功率、电能值。用户可采用ZigBee遥控器、手机、按键开关等多种方式控制该设备。', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('141', '0', 'Z815C', 'Z815C三路电能检测墙面开关', '0006', '开关', '3', '1', 'dest', 'Z815K.jpg', 'Z815C有三路输出的开关设备，可直接安装于86盒中，替换家居中的普通墙面开关，能够检测和统计当前负载电流、电压、功率、电能值。用户可采用ZigBee遥控器、手机、按键开关等多种方式控制该设备。', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('142', '0', 'Z815D', 'Z815D单路电能检测墙面调光开关', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z815L.jpg', 'Z815D是有一路输出的调光设备，能实现开关和调光控制，可直接安装于86盒中，替换家居中的普通墙面开关，能够检测和统计当前负载电流、电压、功率、电能值。用户可采用ZigBee遥控器、手机、按键开关等多种方式控制该设备。', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('143', '0', 'Z815E', 'Z815E双路电能检测墙面调光开关', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z815M.jpg', 'Z815E是有两路输出的调光设备，能实现开关和调光控制，可直接安装于86盒中，替换家居中的普通墙面开关，能够检测和统计当前负载电流、电压、功率、电能值。用户可采用ZigBee遥控器、手机、按键开关等多种方式控制该设备。', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('144', '0', 'Z826C', 'Z826C三路本地开关控制器', '0006', '开关', '3', '1', 'dest', 'Z825Q.jpg', 'Z826C是单火线取电的三路本地开关控制器，可与具有开关功能的设备绑定，通过已绑定设备来控制Z826C开关，也可通过Z826C设备本身自带的开关来控制，Z826C具有三路控制开关,由三个开关控制。', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络青绿色指示灯闪烁一次（期间网络青绿色指示灯闪烁4次，其中刚按下闪一次，3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络青绿色指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会进入睡眠模式，短按绑定键Z826C可重新加网。 ', 'Z826C具有单路输入，三路输出的能力，接线时，将一种线（N或L，来自市电）接入输入口，然后在三路输出口引出同种类型的输出线（给设备供电），将插头接入到AC  100-240V的电源，给设备上电。');
INSERT INTO `modenodelib` VALUES ('145', '11', 'Z206', 'Z206云智能网关', '', '', '', '1', '', 'Z206.jpg', 'Z206作为奈伯思智能家居系统的核心网关，率先实现云端技术、WiFi移动联网技术与ZigBee智能家居物联网术的完美结合，让用户只需要通过手机APP连接Wifi即可控制家里的灯光、窗帘、各种电器等设备的开关，出门在外通过2G/3G网络可监控家中的一切变化。', '使用DC 12V  1.5A电源适配器，接入100-220V的电源', '不需要激活', '详见206说明书', 'Z206作为奈伯思智能家居系统的核心网关，率先实现云端技术、WiFi移动联网技术与ZigBee智能家居物联网术的完美结合，让用户只需要通过手机APP连接Wifi即可控制家里的灯光、窗帘、各种电器等设备的开关，出门在外通过2G/3G网络可监控家中的一切变化。');
INSERT INTO `modenodelib` VALUES ('146', '9', 'Z501G', 'Z501G遙控器(357)', '0501', '安防控制', '3', '1', 'source', 'Z501A.jpg', 'Z501G做为远程遥控器，它可用来辅助控制安防系统，同时设备还设有紧急按钮，在遇到紧急情况时按下紧急按钮通知家人以寻求帮助。', '3V CR2032纽扣电池', '右侧开关off断电，重新上电后即可激活设备。', '按住“开关键” 的同时给设备上电，开始恢复出厂值，指示灯共闪烁20次，之后重新操作组合键就可以重新加网了。', '无');
INSERT INTO `modenodelib` VALUES ('147', '7', 'ZA06', 'ZA06空气蛋', '', '', '3', '1', 'dest', 'ZA06.jpg', 'ZA06具有检测温湿度、PM2.5、甲醛、CO以及一些有害的有机气体。并能定时采集温湿度、空气质量以及甲醛数据，发送给登记的CIE设备，提示报警。ZA06通过USB供电，短按触摸键可以控制三色灯是否显示当前PM2.5值，设备通过检测到的PM2.5的值，在三色灯上面显示，绿色、黄色、橙色、红色、紫色、褐红色分别代表PM2.5等级，绿色空气质量好、其次按顺序递减，褐红色蓝色最差。', '5V直流电源', '不需要激活', '长按绑定键15s（网络灯闪烁3次以后，设备3s闪烁一次，10s闪烁一次，15s闪烁一次），设备开始恢复出场设置，直到网络灯一直闪烁20次（20，100，100），设备自动复位后，重新请求加网。', '无');
INSERT INTO `modenodelib` VALUES ('148', '6', 'Z-D01C', 'Z-D01C无线帘幕控制器（卷帘）', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z-D01BCD.jpg', 'Z-D01C是窗帘控制器，可以通过自身的按键、绑定的设备或软件控制窗帘的开关和级别。', '12V4A直流电源', '不需要激活', '按住ON1和OFF1键（靠右边一列）的同时给设备上电，开始执行恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重新上电就可以重新加网了', '在设备执行窗帘控制功能前,都应先进行“学习上下限”的学习，学习方法请详见设备说明书或在app的设备管理模块，该设备的设置页面进行学习');
INSERT INTO `modenodelib` VALUES ('149', '6', 'Z-D01D', 'Z-D01D无线帘幕控制器（百叶帘）', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z-D01BCD.jpg', 'Z-D01D是窗帘控制器，可以通过自身的按键、绑定的设备或软件控制窗帘的开关和级别。', '12V4A直流电源', '不需要激活', '按住ON1和OFF1键（靠右边一列）的同时给设备上电，开始执行恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重新上电就可以重新加网了', '在设备执行窗帘控制功能前,都应先进行“学习上下限”的学习，学习方法请详见设备说明书或在app的设备管理模块，该设备的设置页面进行学习');
INSERT INTO `modenodelib` VALUES ('150', '0', 'Z-825F', 'Z-825F无线可编程情景触摸开关（单键)', '0006', '开关', '3', '1', 'dest', 'Z825F.jpg', 'Z825F作为情景模式控制器，它拥有1个触摸按键。最多可添加1个情景，或配置4个模式。 ', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib` VALUES ('151', '0', 'Z-825G', 'Z-825G无线可编程情景触摸开关（双键)', '0006', '开关', '3', '1', 'dest', 'Z825G.jpg', 'Z825G作为情景模式控制器，它拥有2个触摸按键。每个按键均可以配置添加情景，或者配置控制模式（每个按键最多添加1个情景，或配置4个模式）。 ', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib` VALUES ('152', '0', 'Z-825H', 'Z-825H无线可编程情景触摸开关（三键)', '0006', '开关', '3', '1', 'dest', 'Z825H.jpg', 'Z825H作为情景模式控制器，它拥有3个触摸按键。每个按键均可以配置添加情景，或者配置控制模式（每个按键最多添加1个情景，或配置4个模式）。 ', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib` VALUES ('153', '0', 'Z-826A', 'Z-826A无线触摸开关（单路+单火线取电）', '0006', '开关', '3', '1', 'dest', 'Z826A.jpg', 'Z826A是单火线取电的单路无线触摸开关，可与具有开关功能的设备绑定，通过已绑定设备来控制Z826A开关，也可通过Z826A设备本身自带的开关来控制', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络青绿色指示灯闪烁一次（期间网络青绿色指示灯闪烁4次，其中刚按下闪一次，3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络青绿色指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会进入睡眠模式，短按绑定键Z826C可重新加网。 ', '无');
INSERT INTO `modenodelib` VALUES ('154', '11', 'Z-L01D', 'Z-L01D工业级IEEE802.15.4/GPIO 适配器', '0006', '开关', '3', '1', 'dest', 'ZL01D.jpg', 'Z-L01D 是GPIO协调器，配合GPIO端口驱动，主设备可以通过该端口 与ZigBee其他模块进行通信，进而对整个系统进行操作。', 'GPIO口', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');
INSERT INTO `modenodelib` VALUES ('155', '0', 'Z-826B', 'Z-826B无线触摸开关（双路+单火线取电）', '0006', '开关', '3', '1', 'dest', 'Z826B.jpg', 'Z826B是单火线取电的单路无线触摸开关，可与具有开关功能的设备绑定，通过已绑定设备来控制Z826B开关，也可通过Z826B设备本身自带的开关来控制', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络青绿色指示灯闪烁一次（期间网络青绿色指示灯闪烁4次，其中刚按下闪一次，3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络青绿色指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会进入睡眠模式，短按绑定键Z826C可重新加网。 ', '无');
INSERT INTO `modenodelib` VALUES ('156', '0', 'Z-826D', 'Z-826D无线调光触摸开关（单路+单火线取电)', '0006', '开关', '3', '1', 'dest', 'Z826D.jpg', 'Z826D是单火线取电的单路调光触摸开关，可与具有开关功能的设备绑定，通过已绑定设备来控制Z826D开关，也可通过Z826D设备本身自带的开关来控制', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络青绿色指示灯闪烁一次（期间网络青绿色指示灯闪烁4次，其中刚按下闪一次，3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络青绿色指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会进入睡眠模式，短按绑定键Z826C可重新加网。', '无');
INSERT INTO `modenodelib` VALUES ('157', '11', 'Z-202E', 'Z-202EIEEE802.15.4/TCP IP 适配器', '0006', '开关', '3', '1', 'dest', 'Z202E.jpg', 'Z202E 是TCP ip协调器，配合网口驱动，主设备可以通过该网口 与ZigBee其他模块进行通信，进而对整个系统进行操作。', '网口', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');
INSERT INTO `modenodelib` VALUES ('158', '11', 'Z-L01A', 'Z-L01A工业级IEEE802.15.4/RS-232 适配器', '0006', '开关', '3', '1', 'dest', 'ZL01A.jpg', 'Z-L01A 是RS协调器，配合RS-232端口驱动，主设备可以通过该端口 与ZigBee其他模块进行通信，进而对整个系统进行操作。', 'RS-232端口', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');
INSERT INTO `modenodelib` VALUES ('159', '11', 'Z-L01B', 'Z-L01B工业级IEEE802.15.4/RS-485适配器', '0006', '开关', '3', '1', 'dest', 'ZL01B.jpg', 'Z-L01B 是RS协调器，配合RS-485端口驱动，主设备可以通过该端口 与ZigBee其他模块进行通信，进而对整个系统进行操作。', 'RS-485端口', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');
INSERT INTO `modenodelib` VALUES ('160', '11', 'Z-L01F', 'Z-L01F工业级IEEE802.15.4/RS-232 透传适配器', '0006', '开关', '3', '1', 'dest', 'ZL01F.jpg', 'Z-L01F 是RS协调器，配合RS-232端口驱动，主设备可以通过该端口 与ZigBee其他模块进行通信，进而对整个系统进行操作。', 'RS-232端口', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');
INSERT INTO `modenodelib` VALUES ('161', '11', 'Z-L01C', 'Z-L01CIEEE802.15.4/TCP IP 适配器', '0006', '开关', '3', '1', 'dest', 'ZL01C.jpg', 'Z-L01C 是TCP ip协调器，配合网口驱动，主设备可以通过该网口 与ZigBee其他模块进行通信，进而对整个系统进行操作。', '网口', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');
INSERT INTO `modenodelib` VALUES ('162', '11', 'Z-L01E', 'Z-L01E工业级IEEE802.15.4/USB适配器', '0006', '开关', '3', '1', 'dest', 'ZL01E.jpg', 'Z-L01E 是USB协调器，配合USB的串口驱动，主设备可以通过该串口 与ZigBee其他模块进行通信，进而对整个系统进行操作。', 'USB', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');

-- ----------------------------
-- Table structure for modenodelib_en
-- ----------------------------
DROP TABLE IF EXISTS `modenodelib_en`;
CREATE TABLE `modenodelib_en` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nodeType` varchar(100) DEFAULT NULL,
  `modelId` varchar(50) DEFAULT NULL,
  `deviceName` varchar(200) DEFAULT NULL,
  `clusterId` varchar(200) DEFAULT NULL,
  `clusterName` varchar(100) DEFAULT NULL,
  `destType` varchar(4) DEFAULT NULL,
  `source_id` bigint(20) DEFAULT '1',
  `deviceType` varchar(20) DEFAULT NULL,
  `picName` varchar(100) DEFAULT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `powerType` varchar(200) DEFAULT NULL,
  `activationMethod` varchar(2000) DEFAULT NULL,
  `resetDefault` varchar(2000) DEFAULT NULL,
  `remark` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `nodeType` (`nodeType`) USING BTREE,
  KEY `modelId` (`modelId`) USING BTREE,
  KEY `deviceName` (`deviceName`) USING BTREE,
  KEY `clusterId` (`clusterId`) USING BTREE,
  KEY `destType` (`destType`) USING BTREE,
  KEY `deviceType` (`deviceType`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=163 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of modenodelib_en
-- ----------------------------
INSERT INTO `modenodelib_en` VALUES ('2', '0', 'Z815I', 'Z815I Wall Switch with Power Meter (1-Gang)', '0006', '开关', '3', '1', 'dest', 'Z815I.jpg', 'Z815I is a one-gang Output Switch and its enclosure is designed to replace conventional junction box installation wall switches.  It is equipped with load consumption inspection and load control feature. Users can use ZigBee enabled remote controller and on/off device and smart phone to control Z815I. ', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('3', '0', 'Z815K', 'Z815K Wall Switch with Power Meter (3-Gang)', '0006', '开关', '3', '1', 'dest', 'Z815K.jpg', 'Z815K a three-gang Output Switch and its enclosure is designed to replace conventional junction box installation wall switches.  It is equipped with load consumption inspection and load control feature. Users can use ZigBee enabled remote controller and on/off device and smart phone to control Z815K. ', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('4', '0', 'Z806', 'Z806 Switch Control Unit (2-Output)', '0006', '开关', '3', '1', 'dest', 'Z806.jpg', '\r\nZ806 is a wireless switch relay with two outputs. Users can locally and remotely control it via external switch, paired ZigBee enabled devices and software.', '100～240V的交流电源', '不需要激活', '按住绑定键的同时给设备上电，开始恢复出厂设置，状态指示灯一直闪烁表示擦除完成。之后重新上电，Z806可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('5', '4', 'ZB02A', 'ZB02A Wall Switch (1-Key)', '0006', '开关', '3', '1', 'source', 'ZB02A.jpg', 'ZB02A is a single gang wall switch which can be hung on the wall. Paired with the other On/Off devices, it can be remotely controlled by toggling.', '2节AAA1.5V电池', '拆下面板的上盖，短按绑定键（红色按键），LED网络灯闪5下激活设备。', '拆下面板的上盖，按住绑定键的同时给设备上电，进行恢复出厂设置，看到状态指示灯闪烁，表示恢复完成', '无');
INSERT INTO `modenodelib_en` VALUES ('7', '9', 'Z503', 'Z503 Local Commander', '0006:0008', '开关:调级控制', '1', '1', 'source', 'Z503.jpg', 'Z503 is a multifunctional remote which can turn on/off all devices, dimming, control power socket, emergency button function, arm and disarm security system.', '3V CR2034纽扣电池', '按住“2nd”键和“3”键可激活设备。', '1.同时按住扩展情景键和第二功能按键，给设备上电；2. 如指示灯快速闪烁10次表示恢复出厂值完成，设备自动重启后即可重新加网；3. 如指示灯慢闪3次，表示恢复出厂值失败，请重复步骤1。', '无');
INSERT INTO `modenodelib_en` VALUES ('10', '0', 'Z805A', 'Z805A Switch Control Unit with Power Meter (1-Output)', '0006', '开关', '3', '1', 'dest', 'Z805A.jpg', 'Z805A is a wireless switch relay with one output 16A/250V AC. Users can control it via external switch, paired ZigBee enabled devices and software. Via ZigButler, users can check the real-time current/voltage status and kilo-watt-hour consumption of the attached appliance. ', '100～240V的交流电源', '不需要激活', '长按绑定键15S(指示灯闪烁2次,3S闪烁一次，10S闪烁一次），状态灯开始快闪20秒，设备进行恢复出厂设置；20s内短按任意键，Z805B重新请求加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('11', '0', 'Z805B', 'Z805B Switch Control Unit with Power Meter (1-Output)', '0006', '开关', '3', '1', 'dest', 'Z805B.jpg', 'Z805B is wireless switch relay with one output 16A/250V AC. Users can remotely control it via external switch, paired ZigBee enabled devices and softwareVia ZigButler, users can check the real-time current/voltage status and kilo-watt-hour consumption of the attached appliance. 。', '100～240V的交流电源', '不需要激活', '长按绑定键15S(指示灯闪烁2次,3S闪烁一次，10S闪烁一次），状态灯开始快闪20秒，设备进行恢复出厂设置；20s内短按任意键，Z805B重新请求加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('12', '0', 'Z810B', 'Z810B Switch Control Unit with Power Monitoring LCD', '0006', '开关', '3', '1', 'dest', 'Z810B.jpg', 'Z810B is a switch control, power supply is 100-240VAC 50/60HZ. It can be controlled by its manual override switch or by a paired wireless switch or by software. Users can use software and its LCD to check the real-time current/voltage status and kilo-watt-hour consumption of the attached appliance.', '100～240V的交流电源', '不需要激活', '按下绑定键15秒后（图标每隔5秒闪烁一下，闪烁3次），2秒内短按功能键，LCD数值区显示表示恢复完成，之后设备将自动重启。', '无');
INSERT INTO `modenodelib_en` VALUES ('13', '0', 'Z811', 'Z811 Switch Control Unit (4-Output)', '0006', '开关', '3', '1', 'dest', 'Z811.jpg', 'Z811 is a wireless on/off output switch device. Users can control it via paired ZigBee enabled devices and software.', '100～240V的交流电源', '不需要激活', '长按绑定键15秒（状态指示灯3S闪一次，10S闪一次,15S闪一次），然后短按，状态指示灯一直闪烁表示擦除完成。之后指示灯灭掉，指示Z811便可以重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('14', '0', 'Z815J', 'Z815J Wall Switch with Power Meter (2-Gang)', '0006', '开关', '3', '1', 'dest', 'Z815J.jpg', 'Z815J is a two-gang switch and is designed to replace conventional junction box installation wall switches.  It is equipped with load consumption inspection and load control feature.  Users can use ZigBee enabled remote controller and on/off device and smart phone to control Z815J.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('15', '0', 'Z815L', 'Z815L Wall Dimmer Switch with Power Meter (1-Gang)', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z815L.jpg', 'Z815L is a one-gang dimmable switch and is designed to replace conventional junction box installation wall switches.  It is equipped with load consumption inspection and load control feature. Users can use ZigBee enabled remote controller and on/off device and smart phone to control Z815L.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('16', '0', 'Z815M', 'Z815M Wall Dimmer Switch with Power Meter (2-Gang)', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z815M.jpg', 'Z815M is a two-gang dimmable switch and is designed to replace conventional junction box installation wall switches.  It is equipped with load consumption inspection and load control feature. Users can use ZigBee enabled remote controller and on/off device and smart phone to control Z815M.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('17', '0', 'Z817A', 'Z817A Ceiling 16A Relay Switch with Power Meter', '0006', '开关', '3', '1', 'dest', 'Z817A.jpg', 'Z817A is a ceiling mount switch unit. It can do on/off switch and dim the lights through its buttoms, paired ZigBee enabled remote controllers or even software. Energy consumption reading is able to be monitored with software. ', '100～240V的交流电源', '不需要激活', '按住绑定键（正对logo靠左）15秒，设备网络灯闪烁一次，然后在2秒内短按任意键，设备网络灯快闪10次后重启。', '无');
INSERT INTO `modenodelib_en` VALUES ('18', '0', 'Z817B', 'Z817B Ceiling 16A Relay Dimmer with Power Meter', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z817B.jpg', 'Z817B is a ceiling mount dimmable unit. It can do on/off switch and dim the lights through its buttoms, paired ZigBee enabled remote controllers or even software. Energy consumption reading is also able to be monitored with software. ', '100～240V的交流电源', '不需要激活', '按住绑定键（正对logo靠左）15秒，设备网络灯闪烁一次，然后在2秒内短按任意键，设备网络灯快闪10次后重启。', '无');
INSERT INTO `modenodelib_en` VALUES ('19', '0', 'Z817C', 'Z817C Ceiling Motion Detector with On/Off Switch', '0006', '开关', '3', '1', 'dest', 'Z817C.jpg', 'Z817C is an infrared-based motion sensor. It detects motions within view range and turns ON the electrical appliances wire-attached which will be turned off after 30 seconds if it doesn’t detect continuing motions. Energy consumption reading is able to be monitored with software.', '100～240V的交流电源', '不需要激活', '按住绑定键（正对logo靠左）15秒，设备网络灯闪烁一次，然后在2秒内短按任意键，设备网络灯快闪10次后重启。', '无');
INSERT INTO `modenodelib_en` VALUES ('20', '0', 'Z825C', 'Z825C Touch Panel Switch with Power Meter (3-Gang)', '0006', '开关', '3', '1', 'dest', 'Z825C.jpg', 'Z825C is a three-gang touch switch and is designed to replace conventional junction box installation wall switches. It is equipped with load consumption inspection and load control feature. Users can use ZigBee enabled remote controller and on/off device and smart phone to control Z825C.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('21', '0', 'Z825E', 'Z825E Touch Panel Dimmer Switch with Power Meter (2-Gang)', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z825E.jpg', 'Z825E is a twp-gang touch switch and is designed to replace conventional junction box installation wall switches. It is equipped with load consumption inspection and load control feature. Users can use ZigBee enabled remote controller and on/off device and smart phone to control Z825E.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('22', '0', 'ZC06', 'ZC06 Dimmable LED Tube (120cm)', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'ZC06.jpg', 'ZC06 is a robust ZigBee enabled dimmable LED tube. It has 256 light levels function. It is a long life and light weighted power consumption which conserves 60% comparing to the conventional light tubes. ZC06 utilizes SMT LEDs with its 400 lumen/1.5 meters and 50,000-hour lamp life. ZC06 can be controlled wirelessly via remote controller. It supports AC 100V to 240V with the same controlling features.', '100～240V的交流电源', '不需要激活', '同时长按测试键和绑定键5s，5s时间到后指示灯闪10次后就恢复出厂设置成功，此时就可以重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('23', '1', 'Z800', 'Z800 Power Plug with Power Meter', '0006', '开关', '3', '1', 'dest', 'Z800B.jpg', 'Z800 is a power socket with power consumption monitoring that allows off site remote control. Users can control it by its bottom and by paired ZigBee enabled devices and software. Energy consumption reading is able to be monitored with software.', '100～240V的交流电源', '不需要激活', '长按绑定键15S，状态灯（红灯）快闪20S；设备进入恢复出厂设置；再按任意键重新请求加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('24', '1', 'Z803', 'Z803 Power Strip with Power Monitoring LCD & USB Port', '0006', '开关', '3', '1', 'dest', 'Z803.jpg', 'Z803 is a 4-gang power strip with power consumption display. It has USB jacks that facilitate USB charging purpose and built-in LCD. Users could also wirelessly control the switch through its own bottom, paired devices or software. LCD can display total power consumption of all sockets. Via the built-in LCD or application, users are able to check the consuming current/ voltage/ power/ energy.', '100～240V的交流电源', '不需要激活', '同时按下Match Key 和Bind Key 按键5秒后，！图标闪烁一下，代表长按了5秒。放开按键后，进行出厂设置，设备自动重新上电。', '无');
INSERT INTO `modenodelib_en` VALUES ('25', '1', 'Z808A', 'Z808A Power Plug with Power Monitoring LCD & USB Port', '0006', '开关', '3', '1', 'dest', 'Z808A.jpg', 'Z808A is a wireless smart plug. It has USB jacks that facilitate USB charging purpose and built-in LCD. Users could also wirelessly control the switch through its own bottom, paired devices or software. LCD can display total power consumption of all sockets. Via the built-in LCD or application, users are able to check the consuming current/ voltage/ power/ energy.', '100～240V的交流电源', '不需要激活', '长按绑定键（右边按键）15s（期间网络指示灯闪3次）后释放绑定键，并在2s内短按功能键（左边按键），即开始恢复出厂设置，网络灯快速闪烁十次后自动重启设备，开始寻找新的网络。', '无');
INSERT INTO `modenodelib_en` VALUES ('26', '1', 'Z808B', 'Z808B Dimmable Power Plug with Power Monitoring', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z808B.jpg', 'Z808B is a wireless dimmable smart plug. It has USB jacks that facilitate USB charging purpose and built-in LCD. Users could also wirelessly control the switch through its own bottom, paired devices or software. LCD can display total power consumption of all sockets. Via the built-in LCD or application, users are able to check the consuming current/ voltage/ power/ energy.', '100～240V的交流电源', '不需要激活', '长按绑定键（右边按键）15s（期间网络指示灯闪3次）后释放绑定键，并在2s内短按功能键（左边按键），即开始恢复出厂设置，网络灯快速闪烁十次后自动重启设备，开始寻找新的网络。', '无');
INSERT INTO `modenodelib_en` VALUES ('27', '1', 'Z809A', 'Z809A Power Plug with Power Meter', '0006', '开关', '3', '1', 'dest', 'Z809A.jpg', 'Z809A is a smart plug that allows off site remote control. Users can manually switch on/off the socket or by software to control it. Via software, users are able to monitor the load current, voltage and power and kilo-watt-hour consumption of the attached appliance.', '100～240V的交流电源', '不需要激活', '　长按绑定键（侧面）15s（期间网络指示灯闪3次）后释放绑定键，并在2s内短按测试键（正面按键），即开始恢复出厂设置，网络灯快速闪烁十次后自动重启设备，开始寻找新的网络。	无', '无');
INSERT INTO `modenodelib_en` VALUES ('28', '1', 'Z809B', 'Z809B Dimmable Power Plug with Power Meter', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z809B.jpg', 'Z809B is a dimmable smart plug that allows off site remote control. Users can manually switch on/off the socket or by paired derives or by software to control and to dim. Via software, users are able to monitor the load current, voltage and power and kilo-watt-hour consumption of the attached appliance. ', '100～240V的交流电源', '不需要激活', '长按绑定键（侧面）15s（期间网络指示灯闪3次）后释放绑定键，并在2s内短按测试键（正面按键），即开始恢复出厂设置，网络灯快速闪烁十次后自动重启设备，开始寻找新的网络。', '无');
INSERT INTO `modenodelib_en` VALUES ('29', '1', 'Z816B', 'Z816B US Type Wall Socket with Power Meter', '0006', '开关', '3', '1', 'dest', 'Z816B.jpg', 'Z816B is a US type wireless smart wall socket that allows off site remote control. Users can manually switch on/off the socket or by paired derives or by software to control and to dim. Via software, users are able to monitor the load current, voltage and power and kilo-watt-hour consumption of the attached appliance.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('30', '1', 'Z816G', 'Z816G UK Type Wall Socket with Power Meter', '0006', '开关', '3', '1', 'dest', 'Z816G.jpg', 'Z816G is a UK type wireless wall socket, it can directly install in 86 junction box and replace the traditional wall socket. It allows off site remote control. Users can manually switch on/off the socket or by paired derives or by software to control and to dim. Via software, users are able to monitor the load current, voltage and power and kilo-watt-hour consumption of the attached appliance.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('31', '1', 'Z816H', 'Z816H China Type Wall Socket with Power Meter', '0006', '开关', '3', '1', 'dest', 'Z816HI.jpg', 'Z816H is a China type wireless smart wall socket, it can directly install in 86 junction box and replace the traditional wall socket. It allows off site remote control. Users can manually switch on/off the socket or by paired derives or by software to control and to dim. Via software, users are able to monitor the load current, voltage and power and kilo-watt-hour consumption of the attached appliance.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('32', '2', 'Z711', 'Z711 Temperature and Humidity Sensor (Indoor)', null, null, null, '1', null, 'Z711.jpg', 'Z711 is a humidity and temperature sensor. It is used for collecting ambient H/T data and sending them to the display. ', '2节1.5V电池', '按下按键可激活设备', '1.  按住绑定键的同时给设备上电；2.  复位完成则led闪烁30次后停止；3.  重新上电，Z711可以开始重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('33', '2', 'Z712', 'Z712 Temperature and Humidity Sensor (Outdoor)', '', '', '', '1', '', 'Z712.jpg', 'Z712 is used to detect the outdoor humidity and temperature. It also increases a splash proof housing for protection and transmits the collecting data to a displayed device through wireless network.', '2节1.5V电池', '按下按键可激活设备', '1.  按住绑定键的同时给设备上电；2.  复位完成则led闪烁30次后停止；3.  重新上电，Z711可以开始重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('34', '2', 'Z713', 'Z713 Temperature and Humidity Sensor (Outdoor)', '', '', '', '1', '', 'Z713.jpg', ' Z713 is a detector for humidity, temperature and UV rays with a solar panel charger.  It\'s also equiped with a waterproof housing for protection and used for collecting ambient H/T data and UV intensity, then sending the data to the display.  ', '2节1.5V电池', '按下按键可激活设备', '1.  按住绑定键的同时给设备上电；2.  复位完成则led闪烁30次后停止；3.  重新上电，Z711可以开始重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('35', '2', 'Z716A', 'Z716A Temperature and Humidity Sensor with LCD (Indoor)', '', '', '', '1', '', 'Z716A.jpg', 'Z716A is a humidity and temperature detector which is used for collecting ambient H/T data and display on the LCD.  It can also send the data to the displaying device. ', '2节1.5V电池', '按下右边按键可激活设备', '设备上电后，同时按住“按键1”和“按键2” 5秒，开始恢复出厂值，LCD上显示所有图标画面，表示恢复出厂值完成，设备自动复位寻找加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('36', '2', 'Z716B', 'Z716B Temperature Sensor with LCD (Indoor)', null, null, null, '1', null, 'Z716B.jpg', 'Z716B is a temperature detector which is used for collecting ambient temperature data and display on the LCD.  It can also send the data to the displaying device. ', '2节1.5V电池', '按下右边按键可激活设备', '设备上电后，同时按住“按键1”和“按键2” 5秒，开始恢复出厂值，LCD上显示所有图标画面，表示恢复出厂值完成，设备自动复位寻找加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('37', '3', 'Z302B', 'Z302B On/Off Light Switch', '0006', '开关', '3', '1', 'source', 'Z302B.jpg', 'Z302B is an automatic light switch controller, which can bind with the output switches. When the brightness is lower than a certain level, Z302B will transmit \"ON\" command and control the binding device. When the brightness enhance to a certain level, Z302B will transmit \"OFF\" command to the binding device. ', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('38', '3', 'Z302H', 'Z302H Light Sensor Dimmer Switch', '0006:0008', '开关:调级控制', '3', '1', 'source', 'Z302H.jpg', 'Z302H is a light dimmer which can sense the intensity of the ambient lighting and determine whether it need to change the brightness of its binding lamp. Thus the controlled lamp/light will maintain the certain brightness within the area. ', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('39', '3', 'Z302G', 'Z302G Light Sensor', null, null, null, '1', null, 'Z302G.jpg', 'The Z302G is an automatic detector and reportor for environment illumination.  It can regularly transmit ambient illumination to a device which can receive and display the ambient illumination.  ', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('40', '3', 'Z311B', 'Z311B On/Off Light Switch', '0006', '开关', '3', '1', 'source', 'Z311B.jpg', 'Z311B is an automatic light switch controller, which can bind with the output switches. When the brightness is lower than a certain level, Z311B will transmit \"ON\" command and control the binding device. When the brightness enhance to a certain level, Z311B will transmit \"OFF\" command to the binding device.  ', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '同时按下绑定键和辅助键，两键同时按着5s，直到看到红色指示灯开始快闪，再松开按键。红色指示灯快闪20次后，设备复位将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('41', '3', 'Z311H', 'Z311H Light Sensor Dimmer Switch', '0006:0008', '开关:调级控制', '3', '1', 'source', 'Z311H.jpg', 'Z311H is a light dimmer which can sense the intensity of the ambient lighting and determine whether it need to change the brightness of its binding lamp. Thus the controlled lamp/light will maintain the certain brightness within the area. ', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '同时按下绑定键和辅助键，两键同时按着5s，直到看到红色指示灯开始快闪，再松开按键。红色指示灯快闪20次后，设备复位将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('42', '3', 'Z311G', 'Z311G Light Sensor', '', '', '', '1', '', 'Z311G.jpg', 'The Z311G is an automatic detector and reportor for environment illumination.  It can regularly transmit ambient illumination to a device which can receive and display the ambient illumination. ', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '同时按下绑定键和辅助键，两键同时按着5s，直到看到红色指示灯开始快闪，再松开按键。红色指示灯快闪20次后，设备复位将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('44', '4', 'ZB02B', 'ZB02B Wall Switch (2-Key)', '0006', '开关', '3', '1', 'source', 'ZB02B.jpg', 'ZB02B is a two keys wireless wall-mounted switch. It can be bound with a device which has switch control function and control the switch wirelessly. ', '2节AAA1.5V电池', '拆下面板的上盖，短按绑定键（红色按键），LED网络灯闪5下激活设备。', '拆下面板的上盖，按住绑定键的同时给设备上电，进行恢复出厂设置，看到状态指示灯闪烁，表示恢复完成', '无');
INSERT INTO `modenodelib_en` VALUES ('45', '4', 'ZB02C', 'ZB02C Wall Switch (3-Key)', '0006', '开关', '3', '1', 'source', 'ZB02C.jpg', 'ZB02C is a three keys wireless wall-mounted switch. It can be bound with a device which has switch control function and control the switch wirelessly. ', '2节AAA1.5V电池', '拆下面板的上盖，短按绑定键（红色按键），LED网络灯闪5下激活设备。', '拆下面板的上盖，按住绑定键的同时给设备上电，进行恢复出厂设置，看到状态指示灯闪烁，表示恢复完成', '无');
INSERT INTO `modenodelib_en` VALUES ('46', '4', 'ZB02F', 'ZB02F Wall Dimmer Switch', '0006:0008', '开关:调级控制', '3', '1', 'source', 'ZB02F.jpg', 'ZB02F is a single key wireless wall-mounted switch. It can be bound with a device which has switch and level control function and control the switch wirelessly. ', '2节AAA1.5V电池', '拆下面板的上盖，短按绑定键（红色按键），LED网络灯闪5下激活设备。', '拆下面板的上盖，按住绑定键的同时给设备上电，进行恢复出厂设置，看到状态指示灯闪烁，表示恢复完成。', '无');
INSERT INTO `modenodelib_en` VALUES ('47', '5', 'ZB01A', 'ZB01A Motion Detector', null, null, null, '1', null, 'ZB01A.jpg', 'The infrared detection alarm function of ZB01A can detect the movement of object. And trigger the alarm through the security center. ', '2节CR123A电池或12V直流电源', '按住背面小圆点按键3秒以上，直至状态指示灯快速闪烁2次激活设备', '按住背面小圆点（绑定键）的同时给设备上电，状态灯快闪，即恢复出厂设置操作成功。恢复完成后重新上电就可以重新加入网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('48', '5', 'ZB01B', 'ZB01B Motion Detector with On/Off Light Switch', '0006', '开关', '3', '1', 'source', 'ZB01B.jpg', 'ZB01B is a switch controller for motion detection. It can detect the movement of object through infrared and control the binding device according to the setting of light threshold.', '2节CR123A电池或12V直流电源', '按住背面小圆点按键3秒以上，直至状态指示灯快速闪烁2次激活设备', '按住背面小圆点（绑定键）的同时给设备上电，状态灯快闪，即恢复出厂设置操作成功。恢复完成后重新上电就可以重新加入网络了。', '可通过调节设备背面的光照灵敏度来设置绑定开关的的光照阈值。');
INSERT INTO `modenodelib_en` VALUES ('49', '5', 'ZB01C', 'ZB01C Motion Detector and Temperature Sensor', '0006', '开关', '3', '1', 'source', 'ZB01C.jpg', 'ZB01C has infrared detecting alarm, infrared on/off control and temperature reporting, three functions. The function of infrared alarm can detect the movement and send the alert through security center. Infrared on/off control function can detect the infrared generated from the moving objects then control the light switch. Temperature reporting function can detect the current temperature of the environment, and report it regularly to the binding device. ', '2节CR123A电池或12V直流电源', '按住背面小圆点按键3秒以上，直至状态指示灯快速闪烁2次激活设备', '按住背面小圆点（绑定键）的同时给设备上电，状态灯快闪，即恢复出厂设置操作成功。恢复完成后重新上电就可以重新加入网络了。', '可通过调节设备背面的光照灵敏度来设置绑定开关的的光照阈值。');
INSERT INTO `modenodelib_en` VALUES ('50', '5', 'ZB01D', 'ZB01D Occupancy Sensor', null, null, null, '1', null, 'ZB01D.jpg', 'ZB01D is an occupancy sensor. It can detect the object movement which produces infrared and report the status to the binding device. ', '2节CR123A电池或12V直流电源', '按住背面小圆点(绑定键)按键3秒以上，直至状态指示灯快速闪烁2次激活设备', '按住背面小圆点（绑定键）的同时给设备上电，状态灯快闪，即恢复出厂设置操作成功。恢复完成后重新上电就可以重新加入网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('51', '5', 'ZB11A', 'ZB11A Motion Detector', '', '', '', '1', '', 'ZB11A.jpg', 'The infrared detection alarm function of ZB11A can detect the movement of object. And trigger the alarm through the security center (CIE).', '2节ER14505 3.6V AA电池供电', '正对logo，短按右侧按钮指示灯闪5下，即可激活设备', '长按辅助键（正对logo右边）10秒，见到绿色指示灯快闪即可松开按键，设备将进行恢复出厂设置操作，完成后将自动关机。', '无');
INSERT INTO `modenodelib_en` VALUES ('52', '5', 'ZB11B', 'ZB11B Motion Detector with On/Off Light Switch', '0006', '开关', '3', '1', 'source', 'ZB11B.jpg', 'ZB11B is a switch controller for motion detection. It can detect the movement of object through infrared and control the binding device according to the setting of light threshold. ', '2节ER14505 3.6V AA电池供电', '正对logo，短按右侧按钮指示灯闪5下，即可激活设备', '长按辅助键（正对logo右边）10秒，见到绿色指示灯快闪即可松开按键，设备将进行恢复出厂设置操作，完成后将自动关机。', '可通过调节设备背面的光照灵敏度来设置绑定开关的的光照阈值。');
INSERT INTO `modenodelib_en` VALUES ('53', '5', 'ZB11C', 'ZB11C Motion Detector and Temperature Sensor', '0006', '开关', '3', '1', 'source', 'ZB11C.jpg', 'ZB11C has infrared detecting alarm, infrared on/off control and temperature reporting, three functions. The function of infrared alarm can detect the movement and send the alert through security center. Infrared on/off control function can detect the infrared generated from the moving objects then control the light switch. Temperature reporting function can detect the current temperature of the environment, and report it regularly to the binding device. ', '2节ER14505 3.6V AA电池供电', '正对logo，短按右侧按钮指示灯闪5下，即可激活设备', '长按辅助键（正对logo右边）10秒，见到绿色指示灯快闪即可松开按键，设备将进行恢复出厂设置操作，完成后将自动关机。', '可通过调节设备背面的光照灵敏度来设置绑定开关的的光照阈值。');
INSERT INTO `modenodelib_en` VALUES ('54', '6', 'ZB11D', 'ZB11D Occupancy Sensor', '', '', '', '1', '', 'ZB11D.jpg', 'ZB11D is an occupancy sensor. It can detect the object movement which produces infrared and report the status to the binding device. ', '2节ER14505 3.6V AA电池供电', '正对logo，短按右侧按钮指示灯闪5下，即可激活设备', '长按辅助键（正对logo右边）10秒，见到绿色指示灯快闪即可松开按键，设备将进行恢复出厂设置操作，完成后将自动关机。', '无');
INSERT INTO `modenodelib_en` VALUES ('55', '6', 'Z815N', 'Z815N  AC Curtain Controller', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z815N.jpg', 'Z815N is a curtain controller. It can control the curtains on/off and level through its own key, binding equipments or software. At the same time, the users can check the load current, voltage, power and energy at present. ', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', 'Lin接火线，N线是零线接电机的蓝线. L out1接电机的棕线，L out2接电机的黑线。在设备执行窗帘控制功能前,都应先进行“学习上下限”的学习，学习方法请详见设备说明书或在app的设备管理模块，该设备的设置页面进行学习。');
INSERT INTO `modenodelib_en` VALUES ('56', '6', 'ZD01B', 'ZD01B Toggle & Level Curtain Controller (Drape)', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'ZD01B.jpg', 'ZD01B is a curtain controller. It can control the curtains on/off and level through its own key, binding equipments or software.', '12V4A直流电源', '不需要激活', '按住ON1和OFF1键（靠右边一列）的同时给设备上电，开始执行恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重新上电就可以重新加网了', '在设备执行窗帘控制功能前,都应先进行“学习上下限”的学习，学习方法请详见设备说明书或在app的设备管理模块，该设备的设置页面进行学习');
INSERT INTO `modenodelib_en` VALUES ('57', '7', 'Z302A', 'Z302A Window Intrusion Sensor', null, null, null, '1', null, 'Z302A.jpg', 'Z302A is a magnetic device, used as a detection device in the security systems. When the door or window is opened, it will send alarm message to the security center. When the door or window is closed, it will send out the normal status message to the security center. ', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', 'Z302A的本体与门磁的磁铁部分别安装在门或窗户的缝隙的两边，当门或窗被打开，它将发出报警信息给安防中心设备( 即CIE )，当门或窗被关闭好，它会发送出状态恢复正常的信息给CIE。该设备也可以用于冰箱、抽屉等处，用于检测冰箱或抽屉等是否被打开。需要注意防水。');
INSERT INTO `modenodelib_en` VALUES ('58', '7', 'Z302C', 'Z302C Window Sensor with Glass Break Detector', null, null, null, '1', null, 'Z302C.jpg', 'Z302C is a detection device in the security systems, as an alarm devices when the doors and windows were exceptional be open or the glass are broken. ', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', 'Z302C的本体与门磁的磁铁部分别安装在门或窗户的缝隙的两边，当门或窗被打开，它将发出报警信息给安防中心设备( 即CIE )，当门或窗被关闭好，它会发送出状态恢复正常的信息给CIE。该设备也可以用于冰箱、抽屉等处，用于检测冰箱或抽屉等是否被打开。需要注意防水。');
INSERT INTO `modenodelib_en` VALUES ('59', '7', 'Z302D', 'Z302D Emergency Push Button', null, null, null, '1', null, 'Z302D.jpg', 'Z302D is a alarm triggered device, as a detection device in the security systems. It can be worn on the wrist of a child or the elderly, children or the elderly when in need of emergency assistance in danger, press the alarm button Z302D, Z302D immediately security Center sends out an alarm message.', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('60', '7', 'Z302E', 'Z302E Asset tag', null, null, null, '1', null, 'Z302E.jpg', 'Z-302E is equipped with light and movement sensor to detect light intensity and movements in the environment.  When status is changed, Z-302E sends alarm message to CIE.', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '可将该设备放于贵重的物品或抽屉旁，当物品被移动或光线发生变化时，则设备会将报警信息发送给安防中心设备。');
INSERT INTO `modenodelib_en` VALUES ('61', '7', 'Z302J', 'Z302J Window Intrusion Sensor', '0006', '开关', '3', '1', 'source', 'Z302J.jpg', 'Z-302J is a window/door sensor. When window/door status is changed, it can control its binding device according to the configurations. Z-302J also works as sensor of the security center- When window/door is open, it notifies security center CIE; When window/door is closed, it notifies security center CIE. ', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', 'Z302J的本体与门磁的磁铁部分别安装在门或窗户的缝隙的两边，当门或窗被打开，它将发出报警信息给安防中心设备( 即CIE )，当门或窗被关闭好，它会发送出状态恢复正常的信息给CIE。该设备也可以用于冰箱、抽屉等处，用于检测冰箱或抽屉等是否被打开。需要注意防水。');
INSERT INTO `modenodelib_en` VALUES ('62', '7', 'Z306D', 'Z306D Panic Button and Inductive Charging Base', null, null, null, '1', null, 'Z307.jpg', 'Z-306D acts as an end device in the network as well as the mobile tags of the local network. It can be searched and controlled via third-party software such as Netvox Zig-Butler.', '3V CR2450钮扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('63', '7', 'Z311A', 'Z311A Window Sensor', null, '', '', '1', '', 'Z311A.jpg', 'Z-311A is a window/door sensor and it is also a sensor in the security system- When window/door is closed, it notifies security center CIE. ', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '同时按下绑定键和辅助键，两键同时按着5s，直到看到红色指示灯开始快闪，再松开按键。红色指示灯快闪20次后，设备复位将进入power off模式。', 'Z311A的本体与门磁的磁铁部分别安装在门或窗户的缝隙的两边，当门或窗被打开，它将发出报警信息给安防中心设备( 即CIE )，当门或窗被关闭好，它会发送出状态恢复正常的信息给CIE。该设备也可以用于冰箱、抽屉等处，用于检测冰箱或抽屉等是否被打开。需要注意防水。');
INSERT INTO `modenodelib_en` VALUES ('64', '7', 'Z311C', 'Z311C Window Sensor with Glass Break Detector', null, '', '', '1', '', 'Z311C.jpg', 'Z-311C is a sensor in the security system- When window/door is open or window glass is broken, it will send out alarm messages.', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '同时按下绑定键和辅助键，两键同时按着5s，直到看到红色指示灯开始快闪，再松开按键。红色指示灯快闪20次后，设备复位将进入power off模式。', 'Z311A的本体与门磁的磁铁部分别安装在门或窗户的缝隙的两边，当门或窗被打开，它将发出报警信息给安防中心设备( 即CIE )，当门或窗被关闭好，它会发送出状态恢复正常的信息给CIE。该设备也可以用于冰箱、抽屉等处，用于检测冰箱或抽屉等是否被打开。需要注意防水。');
INSERT INTO `modenodelib_en` VALUES ('65', '7', 'Z312', 'Z312 Door Bell Button', null, null, null, '1', null, 'Z312.jpg', 'Z-312 is a door bell. Bind Z-312 with siren device and the siren will be able to generate door bell sounds.', '3V CR2450纽扣电池', '　　　　当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '同时按下绑定键和辅助键，两键同时按着5s，直到看到红色指示灯开始快闪，再松开按键。红色指示灯快闪20次后，设备复位将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('66', '7', 'Z307', 'Z307 Wireless Rechargeable Fall Sensor', null, null, null, '1', null, 'Z307.jpg', 'Z307 is a fall sensor as well as a sensor in security system. It can be worn on waist to detect fall of elderly people and young children. When fall is detected, it sends alarm message to the security center device.', '充电套件', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('67', '7', 'Z308', 'Z308 Wearable Presence Tag + Panic Button', null, null, null, '1', null, 'Z308.jpg', 'Z-308 is an emergency alarm device. As a sensor of the security system, it can be worn on wrist of elderly people or young children. When elderly people or young children press Z308\'s emergency button, it sends out alarm message to security center.', '3V CR2450纽扣电池', '长按按键3s，红色指示灯闪一下，松开手后，绿色指示灯闪五下。即激活设备。', '长按报警键15秒以上，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯快闪10次后，设备进入关机状态，红色指示灯熄灭。', '无');
INSERT INTO `modenodelib_en` VALUES ('68', '7', 'ZA01A', 'ZA01A Smoke Detector (Chemiresistors)', null, null, null, '1', null, 'ZA01A.jpg', 'Z-A01A is an air pollution sensor used in home environment, and it acts as a security device in the network. When air intensity goes above a specific level , the device will generate buzzer sound and send status change message to CIE.', '12V直流电源或100～240V交流供电', '不需要激活', '同时按住绑定键和测试键5秒以上，设备指示灯开始快速闪烁，表示复位完成。之后将设备重启，就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('69', '7', 'ZA01B', 'ZA01B Gas Detector', '', '', '', '1', '', 'ZA01B.jpg', 'Z-A01B is a gas detector and it acts as a security device in the network. When gas intensity goes above a specific level, it will send warning message to CIE and generate buzzer sound to warn the users.', '12V直流电源或100～240V交流供电', '不需要激活', '同时按住绑定键和测试键5秒以上，设备指示灯开始快速闪烁，表示复位完成。之后将设备重启，就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('70', '7', 'ZA01C', 'ZA01C CO Detector', null, null, null, '1', null, 'ZA01C.jpg', 'Z-A01C is a CO detector and it acts as a security device in the network. When CO intensity goes above a specific level, it will send warning message to CIE and generate buzzer sound to warn the users.', '12V直流电源或100～240V交流供电', '不需要激活', '同时按住绑定键和测试键5秒以上，设备指示灯开始快速闪烁，表示复位完成。之后将设备重启，就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('71', '7', 'ZA01D', 'ZA01D Liquefied Petroleum Gas Detector', null, null, null, '1', null, 'ZA01D.jpg', 'Z-A01D is a Liquefied gas detector and it acts as a security device in the network. When liquefied gas intensity goes above a specific level, it will send warning message to CIE and generate buzzer sound to warn the users.', '12V直流电源或100～240V交流供电', '不需要激活', '同时按住绑定键和测试键5秒以上，设备指示灯开始快速闪烁，表示复位完成。之后将设备重启，就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('72', '7', 'ZA02E', 'ZA02E Smoke Detector with Backup Battery', null, null, null, '1', null, 'ZA02E.jpg', 'Z-A02E is a smoke detector. When smoke intensity goes above a specific level, it will send warning message to CIE and generate buzzer sound to warn the users.', '12V直流电源或100～241V交流供电', '不需要激活', '同时按住绑定键和测试键5秒以上，设备指示灯开始快速闪烁，表示复位完成。之后将设备重启，就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('73', '7', 'ZA10', 'ZA10 Gas/Water Keeper', '0006', '开关', '3', '1', 'dest', 'ZA10.jpg', 'Z-A10 can be a gas keep or water keep. When Z-A10 is used as a gas keeper, it will turn off the gas when there are fire detection in the network.', '12V直流电源', '不需要激活', '按住按键的同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复出厂设置完成，重新上电就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('74', '7', 'ZB05', 'ZB05 Electronic Door Lock with Fingerprint Identification', '0101', '开关锁', '3', '1', 'dest', 'ZB05.jpg', 'Z-B05 can be unlocked/locked via mechanical keys, passwords, IC cards, and remote control. Via ZigBee wireless network, users can check and control door lock status at any time. Z-B05 door lock provides reliable password security and fire detection for safety purpose.', '8节AA1.5V电池', '不需要激活', '1.按住网络键的同时给设备上电，上电完请不要立即松开按键，等到网络灯开始快速闪烁，提示复位成功；2.重新上电，ZB05可以开始重新加网。', '1、初次使用时，需要注册zigbee：在门打开的状态下，打开内侧用（主锁身）的电池盖和外盒，在右侧下端按“锁卡注册键”。然后再按下面的“ZB注册键”（靠上的按键）。按“*”键，可听到“哔呖呖”的设置音，设置结束。2、默认都为加密的方式，第一次使用时，需要在app中→设备管理模块→进入门锁的设置页面，设置新的用户和密码，才可以对锁进行开关控制。门锁默认有6个空用户，注册新用户前，需要先将这些用户删除。超级密码默认为：123456.usercode即用户名为1-254的数字。');
INSERT INTO `modenodelib_en` VALUES ('75', '7', 'ZB02E', 'ZB02E Door Bell', null, null, null, '1', null, 'ZB02E.jpg', 'Z-B02E is a door bell. Bind Z-B02E with siren device and the siren will be able to generate door bell sounds.', '2节AAA1.5V电池', '拆下面板的上盖，短按绑定键（红色按键），LED网络灯闪5下激活设备。', '拆下面板的上盖，按住绑定键的同时给设备上电，进行恢复出厂设置，看到状态指示灯闪烁，表示恢复完成', '无');
INSERT INTO `modenodelib_en` VALUES ('76', '7', 'Z311E', 'Z311E Asset tag', '', '', '', '1', '', 'Z311E.jpg', 'Z-311E is equipped with light and movement sensor to detect light intensity and movements in the environment.  When status is changed, Z-311E sends alarm message to CIE.', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '同时按下绑定键和辅助键，两键同时按着5s，直到看到红色指示灯开始快闪，再松开按键。红色指示灯快闪20次后，设备复位将进入power off模式。', '可将该设备放于贵重的物品或抽屉旁，当物品被移动或光线发生变化时，则设备会将报警信息发送给安防中心设备。');
INSERT INTO `modenodelib_en` VALUES ('77', '7', 'Z602A', 'Z602A Siren', null, null, null, '1', null, 'Z602A.jpg', 'Z-602A is a siren device which generate buzzer sound and visible LED indication to report events in the security system.', '12V直流电源和3节1.2V可充电备用电池', '不需要激活', '按住设置键（正对logo右侧按键）的同时给设备上电，状态灯快速闪烁且发出两次门铃报警声，即恢复出厂设置操作成功。恢复完成后重新上电就可以重新加入网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('78', '7', 'Z602B', 'Z602B Siren with GSM Connectivity', null, null, null, '1', null, 'Z602B.jpg', 'Z-602B is a siren device which generate buzzer sound and visible LED indication to report events in security system. When Z-602B generate buzzer sounds, it can also call and text a pre-configured phone number according to different alarm levels for users to receive the alarm message in the real time.', '12V直流电源和3节1.2V可充电备用电池', '不需要激活', '按住设置键（正对logo右侧按键）的同时给设备上电，状态灯快速闪烁且发出两次门铃报警声，即恢复出厂设置操作成功。恢复完成后重新上电就可以重新加入网络了。', '该设备具有GSM功能，可在电池盒内装入SIM卡，在app的设备管理模块，进入该设备的设置页面，可设置电话号码，用于报警时，通过拨打电话和发送短信通知用户。设置的短信号码前，需要加国家代码（如中国：86）');
INSERT INTO `modenodelib_en` VALUES ('79', '7', 'Z601A', 'Z601A Siren', null, null, null, '1', null, 'Z601A.jpg', 'Z-601A is a siren device which generate buzzer sound to report events in the security system.', '12V直流电源', '不需要激活', '按住绑定键的同时给设备上电，状态灯快闪，即恢复出厂设置操作成功。恢复完成后重新上电就可以重新加入网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('80', '8', 'Z821', 'Z821 Multi-Channel Energy Power Meter', null, null, null, '1', null, 'Z821.jpg', 'Z-821 is an indoor multi-channel power meter. It can measure voltage of single channel and the total current of all seven channels, and it can provide power and energy data.', '100～240V的交流电源', '不需要激活', '按下Bind Key按键15秒后（状态灯每5秒钟闪烁1次，共闪烁3次，代表长按了15秒），松开按键，在两秒之内短按Match Key进行网络信息的擦除。网络信息擦除后设备自动重启寻找网络。', '将七路CT端子（用于检测电能）接入到Z821两侧的接口。按照CT端子上指示的K→L方向（电流流动的方向）,将导线扣入CT端子，即可检测该路的电能。');
INSERT INTO `modenodelib_en` VALUES ('81', '9', 'Z501A', 'Z501A 4-Key Remote Controller (250)', '0006:0008', '开关:调级控制', '3', '1', 'source', 'Z501A.jpg', 'As a remote control device, Z501A can be bound with on/off or dimmer devices for users to control the devices wirelessly. Z-501A also comes with an emergency button for users to call for help in emergency.', '3V CR2032纽扣电池', '右侧开关off断电，重新上电后即可激活设备。', '按住“开关键” 的同时给设备上电，开始恢复出厂值，指示灯共闪烁20次，之后重新操作组合键就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('82', '9', 'Z501B', 'Z501B 4-Key Remote Controller (250)', '0006', '开关', '3', '1', 'source', 'Z501B.jpg', 'As a remote control device, Z-501B can be bound with on/off devices for users to control the devices wirelessly.', '3V CR2032纽扣电池', '右侧开关off断电，重新上电后即可激活设备。', '按住“ON2键” 的同时给设备上电，开始恢复出厂值，指示灯共闪烁20次，之后重新操作组合键就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('83', '9', 'Z501C', 'Z501C 4-Key Remote Controller(250)', '0006:0008', '开关:调级控制', '3', '1', 'source', 'Z501C.jpg', 'Z501C can be bound with on/off or dimmer devices for users to control the devices wirelessly.', '3V CR2033纽扣电池', '右侧开关off断电，重新上电后即可激活设备。', '按住“ ON/OFF1键” 的同时给设备上电，开始恢复出厂值，指示灯共闪烁20次，之后重新操作组合键就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('84', '10', 'Z210', 'Z210 Infrared Gateway with 1 External Port', null, null, null, '1', null, 'Z210C.jpg', 'Z-210 is the center of the security system to manage security devices. It can also learn and send IR signals for users to control their home appliances such as TV and DVD player.', '12V直流电源', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '使用红外转接器控制家用电器时，需要在app上导入红外数据，若无红外数据时，则需要通过IR学习器或手机app手动学习。');
INSERT INTO `modenodelib_en` VALUES ('85', '10', 'Z211', 'Z211  Infrared Gateway with 4 External Ports', null, null, null, '1', null, 'Z211.jpg', 'Z-211 is a device for IR learning and IR controlling. After learning IR codes, Z-211 can control home appliances which receives IR signals. Also, Z-211 can control ZigBee devices when it receives IR signals from a regular remote control.', '12V直流电源', '不需要激活', '按住绑定键（蓝色按键），同时给设备上电，开始恢复出厂设置，直到状态指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '使用红外转接器控制家用电器时，需要在app上导入红外数据，若无红外数据时，则需要通过IR学习器或手机app手动学习。');
INSERT INTO `modenodelib_en` VALUES ('86', '11', 'Z203', 'Z203  Cloud Wireless Smart Home Center', null, null, null, '1', null, 'Z203.jpg', 'As the main controller of Netvox Cloud-based Wireless Smart Home System, Z-203 is a perfect combination of cloud services, Wifi technology, and ZigBee Home Automation solutions. Z-203 enables users to monitor their lights, curtains, and home appliances via smart home app and Wifi connection, and users can easily monitor their home with 2G/3G network even they are far away.', '12V直流电源', '不需要激活', '详见203说明书', 'Z203可用12V直流电源供电，给Z203上电后，打开手机的WiFi功能，即可搜索到Z203的无线信号。外网网线接入Z203的WAN口，LAN口可直接接IPcamera或电脑，当家中有多个 IPcamera时，需要增加集线器。');
INSERT INTO `modenodelib_en` VALUES ('87', '11', 'Z103AC', 'Z103AC USB Coordinator', null, null, null, '1', null, 'Z103AC.jpg', 'Z103AC is a coordinator with USB port. The devicce can communicate with other Zigbee modules through the port, and thus control the entire system.', 'USB', '不需要激活', '按住PermitJoin键的同时给Z103设备上电，设备指示灯处于快速闪烁状态，表示恢复出厂设置完成，设备重新上电就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('88', '11', 'Z103AR', 'Z103AR USB Router', null, null, null, '1', null, 'Z103AR.jpg', 'Z103AR is a Router with USB port，which can transmit the information to other devices with the USB port driver.', 'USB', '不需要激活', '按住PermitJoin键的同时给Z103设备上电，设备指示灯处于快速闪烁状态，表示恢复出厂设置完成，设备重新上电就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('89', '11', 'Z201B', 'Z201B Coordinator with Security Center (CIE)', null, null, null, '1', null, 'Z201B.jpg', 'Z201B is a HA Coordinator & CIE. With the USB port driver, the device can communicate with other Zigbee modules through the port and allow the enrolling of other devices, thus to control the entire system;', '12V直流电源', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('90', '11', 'Z201C', 'Z201C ZigBee802.15.4 Coord ', null, null, null, '1', null, 'Z201C.jpg', 'Z201C is HA coordinator with USB port,  With the USB port driver, the device can communicate with other Zigbee modules through the port, thus to control the entire system;', '12V直流电源', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('91', '11', 'Z201R HA', 'Z201R HA ZigBee TCP/IP Gateway', null, null, null, '1', null, 'Z201R.jpg', 'HA Repeater', '12V直流电源', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('92', '11', 'Z201R', 'Z201R ZigBee 802.15.4 Router', null, null, null, '1', null, 'Z201R.jpg', 'Location Reference Node', '12V直流电源', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('93', '4', 'Z812A', 'Z812A Programmable 8-Button Scene Control Keypad', '0006', '开关', '3', '1', 'source', 'Z812A.jpg', 'Z-812A is Multi-port Wireless  In-wall Switch Module. It can bind with the devices carrying the function of on/off switch control and enables wireless on/off control.', '100～240V的交流电源', '不需要激活', '上电以后，同时按住OFF3和OFF4键，按住5秒，网络灯灭，然后放开按键，开始恢复出厂值，直到网络灯开始闪烁，表示恢复完成。之后设备将自动复位，可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('94', '4', 'Z812B', 'Z812B Battery Operated 8-Button Control Keypad', '0006:0008', '开关:调级控制', '3', '1', 'source', 'Z812B.jpg', 'Z-812B is Multi-port Wireless  In-wall Switch Module. It can bind with the devices carrying the function of on/off switch control and enables wireless on/off control.', '100～240V的交流电源', '同时按住第一行两个按键即可激活设备。', '同时按住按键3和按键4达3秒，指示灯闪烁1次提示设备开始恢复出厂设置，恢复成功指示灯快闪10次，不成功指示灯无动作。恢复出厂设置之后设备自动重启。', '无');
INSERT INTO `modenodelib_en` VALUES ('95', '7', 'Z801TXB', 'Z801TXB Sensor Signal Tx Module', '', '', '', '1', '', 'Z801TXB.jpg', 'Z801TXB is a pulse Signal Detector，it can be connected with 5 equipment.if Z801txb detects the signal,it will send the command to the security of the ENROLL control center .', '5V直流电源或2节1.5V电池', '不需要激活', '按住绑定键的同时给设备上电，LED2闪烁20次，表示复位完成，之后重新上电，Z801 TXB可以重新加网。 ', '无');
INSERT INTO `modenodelib_en` VALUES ('96', '0', 'Z801RX', 'Z801RX  RX Relay Board', '0006', '开关', '3', '1', 'dest', 'Z801RX.jpg', 'Z801RX is relay board.it is used to control the switch of the equipment,mainly for controling the switch of household appliance.  ', '2节1.2V电池', '不需要激活', '1.  按住绑定键的同时给设备上电；2.  复位完成则状态指示灯快速灯闪烁；3.  重新上电，Z801RX可以开始重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('100', '11', 'Z508', 'Z508 In Home Display', null, null, null, '1', null, 'Z508.jpg', 'Z508 is a Smart In-Home Display for smart home and household energy monitoring. Z-508 works as Smart Home Control Center and allows users to review current and historical energy consumption data.By through Zig-Butler Smart Scheme, users can select and switch any preferable mode setting. With the implementation of CIE, Z-508 is capable of managing Home Security System and tells security related message on its display.', '5V直流电源或2节1.5V电池', '不需要激活', '详见Z508说明书', '无');
INSERT INTO `modenodelib_en` VALUES ('101', '0', 'Z825A', 'Z825A Touch Panel Switch with Power Meter (1-Gang)', '0006', '开关', '3', '1', 'dest', 'Z825A.jpg', 'Z-825A is One Gang In-Wall Switch with Touch Panel and fits with 86*86 junction box and replaces the existing wall switch. It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-825A by ZigBee Remote Control, Smart Phone, On/Off Switch and so on.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('102', '0', 'Z825B', 'Z825B Touch Panel Switch with Power Meter (2-Gang)', '0006', '开关', '3', '1', 'dest', 'Z825B.jpg', 'Z-825B is Two Gang In-Wall Switch with Touch Panel and fits with 86*86 junction box and replaces the existing wall switch. It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-825B by ZigBee Remote Control, Smart Phone, On/Off Switch and so on.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('103', '0', 'Z825D', 'Z825D Touch Panel Dimmer Switch with Power Meter (1-Gang)', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z825D.jpg', 'Z-825D is One Gang In-Wall Dimmer Switch with Touch Panel and fits with 86*86 junction box and replaces the existing wall switch. It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-825D by ZigBee Remote Control, Smart Phone, On/Off Switch and so on.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('104', '2', 'Z725A', 'Z725A Temperature and Humidity Sensor (Outdoor)', null, null, null, '1', null, 'Z725A.jpg', 'Z-725A is Wireless H/T Sensor and Ultraviolet Detector with Solar Battery Panel. It comes with water-proof enclosure for protective purpose. It is used to measure and collect Humidity, Temperature and Ultraviolet data in the surrounding and direct to the data collector for display.', '太阳能充电电池', '不需要激活', '设备上电后，同时按住“绑定键”和“设置键” 5秒，开始恢复出厂值，LED快闪10次，表示恢复出厂值完成，设备自动复位寻找加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('106', '11', 'Z725R', 'Z725R Repeater with Solar Battery (Outdoor)', null, null, null, '1', null, 'Z725R.jpg', 'Z-725R is a Outdoor type Wireless Repeater，which is use to expand the network range。 ', '太阳能充电电池', '不需要激活', '设备上电后，同时按住绑定键和辅助键5秒，状态灯闪烁1次后，开始恢复出厂值，LED快闪20次，表示恢复出厂值完成，设备自动复位寻找加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('107', '0', 'Z962A', 'Z962A One Gang Wall Switch with Touch Panel', '0006', '开关', '3', '1', 'dest', 'Z962A.jpg', 'Z-962A is One Gang In-Wall Switch with Touch Panel and fits with 86*86 junction box and replaces the existing wall switch. It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-962A by ZigBee Remote Control, Smart Phone, On/Off Switch and so on.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('108', '0', 'Z962B', 'Z962B Two Gang Wall Switch with Touch Panel', '0006', '开关', '3', '1', 'dest', 'Z962B.jpg', 'Z-962B is Two Gang In-Wall Switch with Touch Panel and fits with 86*86 junction box and replaces the existing wall switch. It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-962B by ZigBee Remote Control, Smart Phone, On/Off Switch and so on.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('109', '0', 'Z962C', 'Z962C Three Gang Wall Switch with Touch Panel', '0006', '开关', '3', '1', 'dest', 'Z962C.jpg', 'Z-962C is Three Gang In-Wall Switch with Touch Panel and fits with 86*86 junction box and replaces the existing wall switch. It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-962C by ZigBee Remote Control, Smart Phone, On/Off Switch and so on.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('110', '0', 'Z962D', 'Z962D Scene or Mode Selector 1 key with Touch Panel', null, null, null, '1', null, 'Z962D.jpg', 'Z-962D is Scene and Mode control keypad, by through 1 touch keypad, users can add scene/mode, keep scene storage and manage mode control to carry out memorizing 1 scence control. It also supports Netvox mode control functionality.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib_en` VALUES ('111', '0', 'Z962E', 'Z962E Scene or Mode Selector 2 keys with Touch Panel', null, null, null, '1', null, 'Z962E.jpg', 'Z-962E is Scene and Mode control keypad, by through 2 touch keypad, users can add scene/mode, keep scene storage and manage mode control to carry out memorizing 2 scence control. It also supports Netvox mode control functionality.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib_en` VALUES ('112', '0', 'Z962F', 'Z962F Scene or Mode Selector 3 keys with Touch Panel', null, null, null, '1', null, 'Z962F.jpg', 'Z-962F is Scene and Mode control keypad, by through 3 touch keypad, users can add scene/mode, keep scene storage and manage mode control to carry out memorizing 3 scence control. It also supports Netvox mode control functionality.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib_en` VALUES ('113', '0', 'Z962G', 'Z962G One Scene or Mode Selector & one Switch', '0006', '开关', '3', '1', 'dest', 'Z962G.jpg', 'Z962G is One Gang Touch Switch and 1 Scence Control Touch Button and fits with 86*86 junction box and replaces the existing wall switch. It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-962G by ZigBee Remote Control, Smart Phone, On/Off Switch and so on. Users can manage scence/mode control with the secnce control button.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib_en` VALUES ('114', '0', 'Z962H', 'Z962H Two Scene or Mode Selector & one Switch', '0006', '开关', '3', '1', 'dest', 'Z962H.jpg', 'Z962H is One Gang Touch Switch and 2 Scence Control Touch Button and fits with 86*86 junction box and replaces the existing wall switch. It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-962H by ZigBee Remote Control, Smart Phone, On/Off Switch and so on. Users can manage scence/mode control with the secnce control button.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib_en` VALUES ('115', '0', 'Z962I', 'Z962I One Scene or Mode Selector & Two Switch', '0006', '开关', '3', '1', 'dest', 'Z962I.jpg', 'Z962I is Two Gang Touch Switch and 1 Scence Control Touch Button and fits with 86*86 junction box and replaces the existing wall switch. It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-962I by ZigBee Remote Control, Smart Phone, On/Off Switch and so on. Users can manage scence/mode control with the secnce control button.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib_en` VALUES ('116', '7', 'Z801WLS', 'Z801WLS Water Sensor', '', '', '', '1', '', 'Z801WLS.jpg', 'Z-801WLS is a  a water Sensor,which is used for monitoring leakage situation, and send the alarm information to the security center immediately.', '5V直流电源或2节1.5V电池', '不需要激活', '按住绑定键的同时给设备上电，LED2闪烁20次，表示复位完成，之后重新上电，Z801WLS可以重新加网。 ', '无');
INSERT INTO `modenodelib_en` VALUES ('117', '7', 'ZB05A', 'ZB05A Door Lock with Fingerprint Identification', '0101', '开关锁', '3', '1', 'dest', 'ZB05A.jpg', 'Z-B05A can control lock/unlock of the door that applied to it including mechanical key, access code, IC card, finger print, remote access ect.. By through ZigBee wireless networking, users can monitor and control the door status, manage user ID and passwork anywhere you are. Intellectualized door lock design supports reliable access code safeguard to guarantee home surveillance safety.', '8节AA1.5V电池', '不需要激活', '1.按住网络键的同时给设备上电，上电完请不要立即松开按键，等到网络灯开始快速闪烁，提示复位成功；2.重新上电，ZB05A可以开始重新加网。', '1、初次使用时，需要注册zigbee：在门打开的状态下，打开内侧用（主锁身）的电池盖和外盒，在右侧下端按“锁卡注册键”。然后再按下面的“ZB注册键”（靠上的按键）。按“*”键，可听到“哔呖呖”的设置音，设置结束。2、默认都为加密的方式，第一次使用时，需要在app中→设备管理模块→进入门锁的设置页面，设置新的用户和密码，才可以对锁进行开关控制。门锁默认有6个空用户，注册新用户前，需要先将这些用户删除。超级密码默认为：123456.usercode即用户名为1-254的数字。');
INSERT INTO `modenodelib_en` VALUES ('118', '4', 'Z801TX', 'Z801TX TX Switch Board', '0006', '开关', '3', '1', 'source', 'Z801TX.jpg', 'Z801TX is used as a end device in the zigBee network,which doesn\'t allow other devices to be its child device.It can be extended with five buttons. When the button is trigged，it can send commands to bound device and control its on/off at the detection of a signal.', '无', '无', '无', '无');
INSERT INTO `modenodelib_en` VALUES ('119', '7', 'Z311J', 'Z311J Window Sensor with On/Off Switch', '0006', '开关', '3', '1', 'source', 'Z311J.jpg', 'Z-311J is a window/door sensor. When window/door status is changed, it can control its bound device according to the configurations. Z-311J also works as a detection sensor of the security system- when window/door is open or closed, it also notifies security center with the alarm messages, while sends the information of normal status to CIE when the window/door is closed.', '3V CR2450纽扣电池', '当需要查看设备是否在网时，可以同时按住绑定键和设置键1秒，状态灯闪烁一次提示激活设备操作成功。此时如果设备在网，则网络灯闪烁5次；如果网络灯只闪烁1次，则设备已脱离网络，正开始尝试加网。', '先按下辅助键(右)、再按绑定键（左），两键同时按着，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', 'Z311J的本体与门磁的磁铁部分别安装在门或窗户的缝隙的两边，当门或窗被打开，它将发出报警信息给安防中心设备( 即CIE )，当门或窗被关闭好，它会发送出状态恢复正常的信息给CIE。该设备也可以用于冰箱、抽屉等处，用于检测冰箱或抽屉等是否被打开。需要注意防水。');
INSERT INTO `modenodelib_en` VALUES ('120', '0', 'ZC07', 'ZC07 Dimmable LED Bulb', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'ZC07.jpg', 'ZC07 is a robust ZigBee enabled dimmable LED bulb. It can achieve 256 dimmer light levels and can be controlled wirelessly. By methods such as binding devices, wireless remote control or software operation, it can control light on/off and dimmer. As a 5W bulb, ZC07 applies constant current control mode withthe input voltage from AC 100V to 240V. The inputs of 100V to 240V can achieve the same lighting effects.', '100～240V的交流电源', '不需要激活', '同时长按测试键和绑定键5s，5s时间到后指示灯闪10次后就恢复出厂设置成功，此时就可以重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('121', '7', 'Z311W', 'Z311W Water Sensor', null, null, null, '1', '', 'Z311W.jpg', 'Z311W is a water sensor,which is used for monitoring leakage situation and sending the alarm information to the security center immediately.', '无需外加电源,产品使用内部的3V钮扣电池供电。', '同时按住绑定键和辅助键，看到红色指示灯灯闪烁2次，此时松开按键。', '同时按住绑定键和辅助键，看到红色指示灯灯闪烁2次，此时继续按着，直到看到红色指示灯开始闪烁，再松开按键，开始恢复出厂值，红色指示灯第二次闪烁说明数据已经恢复完成，红色指示灯快闪10次后，设备将进入power off模式。', '无');
INSERT INTO `modenodelib_en` VALUES ('122', '6', 'Z811B', 'Z811B Curtain Controller (2- Gang)', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z811B.jpg', 'Z811B is a Two Gang curtain controller. It can control the curtains on/off and level through its own key, bound devices or software. ', '100-240V AC 50/60HZ电源供电', '不需要激活', '方法一：按住“绑定键（logo旁的小按钮）”上电，指示灯快闪，表示恢复出厂设置完成，再次重新上电后即可。方法二：长按住“绑定键（logo旁的小按钮）”键的15秒待指示灯闪烁一次（此间指示灯3秒、10秒、15秒各闪烁一次），松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，自动重启就可以重新加网了。', 'Lin接火线，N线是零线接电机的零线（一般为蓝线）。EP1：Lout1接电机正转输入（一般为棕线)，Lout2接电机反转输入（一般为黑线）。EP2：Lout3接电机正转输入（一般为棕线)，Lout4接电机反转输入（一般为黑线）。在设备执行窗帘控制功能前,都应先进行“学习上下限”的学习，学习方法请详见设备说明书或在app的设备管理模块，该设备的设置页面进行学习。');
INSERT INTO `modenodelib_en` VALUES ('123', '0', 'Z825I', 'Z825I Touch Panel Programmable Scene Selector (6-Gang)', '', '', '', '1', '', 'Z825I.jpg', 'As a Scene Selector & Mode Selector，Z825I is equipped with six touch buttons. Each can be configured to add scene or control mode (each button can at most at one scene or configure four modes.)', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib_en` VALUES ('124', '0', 'Z825J', 'Z825J Touch Panel 3-Gang Switch&Scene Selector', '0006', '开关', '3', '1', 'dest', 'Z825J.jpg', 'Z825J is a three-gang touch switch and three-gang Scene Selector,which can be directly installed in 86 junction box to replace the normal wall switch.It monitors and calculates Current, Voltage, Power and Engery. Users can control Z-825J by ZigBee Remote Control, Smart Phone, On/Off Switch and so on. Users can manage scence/mode control with the secnce control button.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '每路触发情景开关应用于什么模式需要在app上，设备管理模块中，找到该设备去设置应用的模式。');
INSERT INTO `modenodelib_en` VALUES ('125', '0', 'Z825Q', 'Z825QTouch Panel Switch with Power Meter (3-Gang)', '0006', '开关', '3', '1', 'dest', 'Z825Q.jpg', 'Z825Q is three-gang local switch controller with single livewire for power. It can control its on/off through being bound with  devices with on/off function to control it or through its own keys. Its three-gang on/off is controlled by three keys.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络青绿色指示灯闪烁一次（期间网络青绿色指示灯闪烁4次，其中刚按下闪一次，3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络青绿色指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会进入睡眠模式，短按绑定键Z825Q可重新加网。 ', 'Z825Q具有单路输入，三路输出的能力，接线时，将一种线（N或L，来自市电）接入输入口，然后在三路输出口引出同种类型的输出线（给设备供电），将插头接入到AC  100-240V的电源，给设备上电。');
INSERT INTO `modenodelib_en` VALUES ('126', '1', 'Z816I', 'Z816I  China Type Wall Socket with Power Meter (5-pin)', '0006', '开关', '3', '1', 'dest', 'Z816I.jpg', 'Z816I is a China type wireless current-detection smart wall socket, which can be directly installed in 86 junction box to replace the traditional wall socket. Its on/off can be controlled with its own keys or through its bound devices and software.Via software, users are able to monitor the load current, voltage and power and kilo-watt-hour consumption of the attached appliance.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('127', '5', 'Z817D', 'Z817D  Ceiling Motion Detector', '0006', '开关', '3', '1', 'source', 'Z817D.jpg', 'Z817D has the on/off switch by the infrared detection.When it detects the IR of moving objects,it can control on/off of bound devices.', 'AC100-240V 50/60HZ供电', '不需要激活', '给设备上电，同时按住“按键1”和“按键2”5秒；复位完成则led闪烁30次后自动重新请求加网；', '无');
INSERT INTO `modenodelib_en` VALUES ('128', '11', 'Z800R', 'Z800R Plug Repeater with Outlet', null, null, null, '1', null, 'Z800R.jpg', 'Z800R is a Router Repeaters with a socket,which allow other devices as his children.It can extend the communication distance for other devices.', 'AC 100-250V的电源', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重新上电就可以加入新的网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('129', '1', 'Z809C', 'Z809C Plug Repeater with Backup Battery', null, null, null, '1', null, 'Z809C.jpg', 'Z809C is a Router Repeaters with a socket,which allow other devices as his children.It can extend the communication distance for other devices.', 'AC 100-240V的电源或电池供电（内置电池）', '不需要激活', '长按绑定键15s，15s时间到状态闪1次后放开绑定键，两秒内短按绑定键就状态灯闪烁10次，并自动复位。其中长按绑定键3s，10s和15s的时候状态灯都会依次闪烁一次，以提示所按的时间长短。', '无');
INSERT INTO `modenodelib_en` VALUES ('130', '1', 'Z809D', 'Z809D Plug Repeater with Power Amplifier & Backup Battery', '', '', '', '1', '', 'Z809D.jpg', 'Z809D is a Router Repeaters with a socket,which allow other devices as his children.It can extend the communication distance for other devices.', 'AC 100-240V的电源或电池供电（内置电池）', '不需要激活', '长按绑定键15s，15s时间到指示闪1次后放开绑定键，两秒内短按绑定键就指示灯闪烁10次，并自动重启设备（如果仅仅用电池供电不能重启）。', '无');
INSERT INTO `modenodelib_en` VALUES ('131', '9', 'Z501AE3ED', 'Z501A 4-Key Remote Controller(357)', '0006:0008', '开关:调级控制', '3', '1', 'source', 'Z501A.jpg', 'As a remote control device, Z501A can be bound with on/off switch or dimmer devices for users to control the devices wirelessly. Z-501A also comes with an emergency button for users to call for help in emergency.', '3V CR2032纽扣电池', '右侧开关off断电，重新上电后即可激活设备。', '按住“开关键” 的同时给设备上电，开始恢复出厂值，指示灯共闪烁20次，之后重新操作组合键就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('132', '9', 'Z501BE3ED', 'Z501B 4-Key Remote Controller(357)', '0006', '开关', '3', '1', 'source', 'Z501B.jpg', 'Z510B is a remote control,which can be bound to control on/off of other devices.', '3V CR2032纽扣电池', '右侧开关off断电，重新上电后即可激活设备。', '按住“ON2键” 的同时给设备上电，开始恢复出厂值，指示灯共闪烁20次，之后重新操作组合键就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('133', '9', 'Z501CE3ED', 'Z501C 4-Key Remote Controller(357)', '0006:0008', '开关:调级控制', '3', '1', 'source', 'Z501C.jpg', 'As a remote control device, Z501C can be bound with on/off or dimmer devices for users to control the devices wirelessly. ', '3V CR2033纽扣电池', '右侧开关off断电，重新上电后即可激活设备。', '按住“ ON/OFF1键” 的同时给设备上电，开始恢复出厂值，指示灯共闪烁20次，之后重新操作组合键就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('134', '7', 'ZB02I', 'ZB02I  Emergency Push Button', '', '', '', '1', '', 'ZB02E.jpg', 'ZB02I is an emergency push button working as a detection device in the security system. It can be put on the wall or anyplace in the room .When users in danger are in need of emergency assistance, users can press the alarm button for ZB02I to immediately send out an alarm message to the security center, which will notify the siren to make sound or light to alert family members to offer immediate help. ', 'AAA电池供电', '短按绑定键，若设备仍在网络状态，则绿色指示灯闪烁5次，表示激活成功。', '按住绑定键的同时给设备上电，进行恢复出厂设置，看到指示灯闪烁，表示恢复完成。', '无');
INSERT INTO `modenodelib_en` VALUES ('137', '0', 'Z802', 'Z802 Two Way Switching Load module', '0006', '开关', '3', '1', 'dest', 'Z802.jpg', 'Z802 can be bound with on/off switch for users to control the devices wirelessly.User can switch on/off the appliances attached to it through mechnical switch or wirelessly. Users may also use ZiG-BUTLER to check the current status of the device and make corresponding control.', '100-240VAC 50/60HZ 电源供电', '不需要激活', '按住绑定键的同时，给设备上电，开始恢复出厂值，直到LED1指示灯开始闪烁，表示恢复完成。之后重新上电，Z802可以重新加网了', '无');
INSERT INTO `modenodelib_en` VALUES ('138', '4', 'ZB02J', 'ZB02J Wireless Scene & Mode Selector (3-Key)', '', '', '', '1', '', 'ZB02C.jpg', 'ZB02J is a three-gang scene button, which can be bound with three scenes for users to control them.', '无需外加电源,产品使用内部的2节7号电池供电', '短按绑定键（led网络灯闪5下）', '按住绑定键不松开，装入电池，进行恢复出厂设置，看到状态灯闪烁，表示恢复完成，然后松开按键，拿出电池', '无');
INSERT INTO `modenodelib_en` VALUES ('139', '0', 'Z815A', 'Z815A Wall Switch with Power Meter (1-Gang)', '0006', '开关', '3', '1', 'dest', 'Z815I.jpg', 'Z815A is a one-gang Output switch,which can be directly installed in 86 junction box to replace the existing wall switch.It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-815A by ZigBee Remote Control, Smart Phone, On/Off Switch and other methods.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('140', '0', 'Z815B', 'Z815B Wall Switch with Power Meter (2-Gang)', '0006', '开关', '3', '1', 'dest', 'Z815J.jpg', 'Z815B is a two-gang Output switch,which can be directly installed in 86 junction box to replace the existing wall switch.It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-815B by ZigBee Remote Control, Smart Phone, On/Off Switch and other methods.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('141', '0', 'Z815C', 'Z815C Wall Switch with Power Meter (3-Gang)', '0006', '开关', '3', '1', 'dest', 'Z815K.jpg', 'Z815C is a three-gang Output switch,which can be directly installed in 86 junction box to replace the existing wall switch.It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-815C by ZigBee Remote Control, Smart Phone, On/Off Switch and other methods.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('142', '0', 'Z815D', 'Z815D  Wall Dimmer Switch with Power Meter (1-Gang)', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z815L.jpg', 'Z815D is a one-gang Output dimmer which can control on/off and dimmer and be directly installed in 86 junction box to replace the existing wall switch.It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-815D by ZigBee Remote Control, Smart Phone, On/Off Switch and other methods.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('143', '0', 'Z815E', 'Z815E Wall Dimmer Switch with Power Meter (2-Gang)', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z815M.jpg', 'Z815E is a two-gang Output dimmer which can control on/off and dimmer and be directly installed in 86 junction box to replace the existing wall switch.It monitors and calculates Current, Voltage, Power and accumulated Engery. Users can control Z-815E by ZigBee Remote Control, Smart Phone, On/Off Switch and other methods.', '100～240V的交流电源', '不需要激活', '按住“绑定键”上电 或者长按绑定键15秒指示灯闪烁一次（期间指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，松开后在两秒内短按任意按键，开始执行恢复出厂设置，直到指示灯快闪，则表示恢复完成，重启后就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('144', '0', 'Z826C', 'Z826C Touch Panel Switch with Power Meter (3-Gang)', '0006', '开关', '3', '1', 'dest', 'Z825Q.jpg', 'Z826C is three-gang local switch controller with single livewire for power. It can control its on/off through being bound with devices with on/off function to control it or through its own keys. Its three-gang on/off is controlled by three keys.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络青绿色指示灯闪烁一次（期间网络青绿色指示灯闪烁4次，其中刚按下闪一次，3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络青绿色指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会进入睡眠模式，短按绑定键Z826C可重新加网。 ', 'Z826C具有单路输入，三路输出的能力，接线时，将一种线（N或L，来自市电）接入输入口，然后在三路输出口引出同种类型的输出线（给设备供电），将插头接入到AC  100-240V的电源，给设备上电。');
INSERT INTO `modenodelib_en` VALUES ('145', '11', 'Z206', 'Z206 Cloud-Based Wireless Smart Home Controller', '', '', '', '1', '', 'Z206.jpg', 'As the main controller of Netvox Cloud-based Wireless Smart Home System, Z-206 is a perfect combination of cloud services, Wifi technology, and ZigBee Home Automation solutions. Z-206 enables users to monitor their lights, curtains, and home appliances via smart home app and Wifi connection, and users can easily monitor their home with 2G/3G network even they are far away.', '使用DC 12V  1.5A电源适配器，接入100-220V的电源', '不需要激活', '详见206说明书', 'Z206作为奈伯思智能家居系统的核心网关，率先实现云端技术、WiFi移动联网技术与ZigBee智能家居物联网术的完美结合，让用户只需要通过手机APP连接Wifi即可控制家里的灯光、窗帘、各种电器等设备的开关，出门在外通过2G/3G网络可监控家中的一切变化。');
INSERT INTO `modenodelib_en` VALUES ('146', '9', 'Z501G', 'Z501G remote control', '0501', '安防控制', '3', '1', 'source', 'Z501A.jpg', 'As a remote control device, Z501G can be used to assist in controlling the security system. Meanwhile, it is equipped with an emergency button, which can be pressed to call for help in emergency.', '3V CR2032纽扣电池', '右侧开关off断电，重新上电后即可激活设备。', '按住“开关键” 的同时给设备上电，开始恢复出厂值，指示灯共闪烁20次，之后重新操作组合键就可以重新加网了。', '无');
INSERT INTO `modenodelib_en` VALUES ('147', '7', 'ZA06', 'ZA06 Air Egg', '', '', '3', '1', 'dest', 'ZA06.jpg', 'ZA06 is a device tesing temperature,humidity,air quality,pm2.5,co and some other harmful gas. It can timely collect the data of temperature,humidity,air quality and CO data and send them to the CIE device where it is enrolled. With USB power, its three-color LED can be controlled at short pressing its touch button to show the current AQI value. It will show different colors based on the detected AQI value.  Green,yellow,orange,red,purple and maroon respectively stands for different AQI level. Green stands for good air quality, while other colors stand for a worser level of air quality in order. Maroon stands for the worst quality.', '5V直流电源', '不需要激活', '长按绑定键15s（网络灯闪烁3次以后，设备3s闪烁一次，10s闪烁一次，15s闪烁一次），设备开始恢复出场设置，直到网络灯一直闪烁20次（20，100，100），设备自动复位后，重新请求加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('148', '6', 'Z-D01C', 'Z-D01CToggle & Level Curtain Controller (Drape)', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z-D01BCD.jpg', 'ZD01C is a curtain controller. It can control the curtains on/off and level through its own key, binding equipments or software.', '12V4A直流电源', '不需要激活', '按住ON1和OFF1键（靠右边一列）的同时给设备上电，开始执行恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重新上电就可以重新加网了', '在设备执行窗帘控制功能前,都应先进行“学习上下限”的学习，学习方法请详见设备说明书或在app的设备管理模块，该设备的设置页面进行学习');
INSERT INTO `modenodelib_en` VALUES ('149', '6', 'Z-D01D', 'Z-D01DToggle & Level Curtain Controller (Blinds)', '0006:0008', '开关:调级控制', '3', '1', 'dest', 'Z-D01BCD.jpg', 'ZD01D is a curtain controller. It can control the curtains on/off and level through its own key, binding equipments or software.', '12V4A直流电源', '不需要激活', '按住ON1和OFF1键（靠右边一列）的同时给设备上电，开始执行恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重新上电就可以重新加网了', '在设备执行窗帘控制功能前,都应先进行“学习上下限”的学习，学习方法请详见设备说明书或在app的设备管理模块，该设备的设置页面进行学习');
INSERT INTO `modenodelib_en` VALUES ('150', '0', 'Z-825F', 'Z-825FTouch Panel Programmable Scene Selector (1-Gang)', '0006', '开关', '3', '1', 'dest', 'Z825F.jpg', 'As a Scene Selector & Mode Selector，Z825F is equipped with one touch buttons. It can at most at one scene or configure four modes.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('151', '0', 'Z-825G', 'Z-825GTouch Panel Programmable Scene Selector (2-Gang)', '0006', '开关', '3', '1', 'dest', 'Z825G.jpg', 'As a Scene Selector & Mode Selector，Z825G is equipped with two touch buttons. Each can be configured to add scene or control mode (each button can at most at one scene or configure four modes.)', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('152', '0', 'Z-825H', 'Z-825HTouch Panel Programmable Scene Selector (3-Gang)', '0006', '开关', '3', '1', 'dest', 'Z825H.jpg', 'As a Scene Selector & Mode Selector，Z825H is equipped with three touch buttons. Each can be configured to add scene or control mode (each button can at most at one scene or configure four modes.)', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络指示灯闪烁一次（期间网络指示灯闪烁3次，其中3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，即可重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('153', '0', 'Z-826A', 'Z-826ATouch Panel Switch with Power Meter (1-Gang)', '0006', '开关', '3', '1', 'dest', 'Z826A.jpg', 'Z826A is one-gang touch switch with single livewire for power. It can control its on/off through being bound with  devices with on/off function to control it or through its own keys.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络青绿色指示灯闪烁一次（期间网络青绿色指示灯闪烁4次，其中刚按下闪一次，3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络青绿色指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会进入睡眠模式，短按绑定键Z826C可重新加网。 ', '无');
INSERT INTO `modenodelib_en` VALUES ('154', '11', 'Z-L01D', 'Z-L01D IEEE 802.15.4/GPIO Adapter', '0006', '开关', '3', '1', 'dest', 'ZL01D.jpg', 'Z-L01D is a coordinator with GPIO port. The devicce can communicate with other Zigbee modules through the port, and thus control the entire system.', 'GPIO口', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('155', '0', 'Z-826B', 'Z-826BTouch Panel Switch with Power Meter (2-Gang)', '0006', '开关', '3', '1', 'dest', 'Z826B.jpg', 'Z826B is two-gang touch switch with single livewire for power. It can control its on/off through being bound with  devices with on/off function to control it or through its own keys. Its two-gang on/off is controlled by two keys.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络青绿色指示灯闪烁一次（期间网络青绿色指示灯闪烁4次，其中刚按下闪一次，3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络青绿色指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会进入睡眠模式，短按绑定键Z826C可重新加网。 ', '无');
INSERT INTO `modenodelib_en` VALUES ('156', '0', 'Z-826D', 'Z-826DTouch Panel Dimmer Switch with Power Meter (1-Gang)', '0006', '开关', '3', '1', 'dest', 'Z826D.jpg', 'Z826D is one-gang dimmer switch with single livewire for power. It can control its on/off through being bound with  devices with on/off function to control it or through its own keys.', '100～240V的交流电源', '不需要激活', '按住绑定键15秒左右网络青绿色指示灯闪烁一次（期间网络青绿色指示灯闪烁4次，其中刚按下闪一次，3秒闪一次，10秒闪一次，15秒闪一次）后释放绑定键，然后在2秒内短按任一开关键一次，开始执行恢复出厂值的操作，网络青绿色指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会进入睡眠模式，短按绑定键Z826C可重新加网。', '无');
INSERT INTO `modenodelib_en` VALUES ('157', '11', 'Z-202E', 'Z-202E IEEE 802.15.4/ TCP/IP Gateway Coordinator/Router ', '0006', '开关', '3', '1', 'dest', 'Z202E.jpg', 'Z202E is a coordinator with NET port. The devicce can communicate with other Zigbee modules through the port, and thus control the entire system.', '网口', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('158', '11', 'Z-L01A', 'Z-L01A Industrial Grade IEEE 802.15.4/RS232 Adapter', '0006', '开关', '3', '1', 'dest', 'ZL01A.jpg', 'Z-L01Ais a coordinator with RS-232 port(COM1/COM2). The devicce can communicate with other Zigbee modules through the port, and thus control the entire system.', 'RS-232端口', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('159', '11', 'Z-L01B', 'Z-L01B IEEE 802.15.4/RS485 Adapter', '0006', '开关', '3', '1', 'dest', 'ZL01B.jpg', 'Z-L01B is a coordinator with RS-485 port. The devicce can communicate with other Zigbee modules through the port, and thus control the entire system.', 'RS-485端口', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('160', '11', 'Z-L01F', 'Z-L01F IEEE 802.15.4/RS232 Straight-Through Adapter', '0006', '开关', '3', '1', 'dest', 'ZL01F.jpg', 'Z-L01F is a coordinator with RS-232 port(COM1/COM2). The devicce can communicate with other Zigbee modules through the port, and thus control the entire system.', 'RS-232端口', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('161', '11', 'Z-L01C', 'Z-L01C IEEE 802.15.4/ TCP/IP Adapter', '0006', '开关', '3', '1', 'dest', 'ZL01C.jpg', 'Z-L01C is a coordinator with NET port. The devicce can communicate with other Zigbee modules through the port, and thus control the entire system.', '网口', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');
INSERT INTO `modenodelib_en` VALUES ('162', '11', 'Z-L01E', 'Z-L01E IEEE 802.15.4/USB Adapter', '0006', '开关', '3', '1', 'dest', 'ZL01E.jpg', 'Z-L01E is a coordinator with USB port. The devicce can communicate with other Zigbee modules through the port, and thus control the entire system.', 'USB', '不需要激活', '按住绑定键，同时给设备上电，开始恢复出厂设置，直到指示灯闪烁，则表示恢复完成，重启就可以加入新的网络了。', '无');

/*2014-12-23*/
UPDATE attributenamelib set attributeName ='hum' where attributeNameCn='湿度'