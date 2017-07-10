/*2015-3-2 短信权限设置*/
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('8001', '短信历史记录', 'SMS  historical record');
INSERT INTO `reliprivilege` (`id`, `privilege_name`, `privilege_code`) VALUES ('9001', '充值套餐设置', 'Prepaid phone plans set');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('246', '1', '8001');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('247', '2', '8001');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('248', '1', '9001');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('249', '2', '9001');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('250', '3', '9001');
INSERT INTO `relilevelprivilege` (`id`, `level_id`, `privilege_id`) VALUES ('251', '4', '9001');
/*2015-3-2 报警id保存*/
CREATE TABLE `warnsendstate` (
`id`  int(20) NOT NULL AUTO_INCREMENT ,
`warnid`  int(20) NULL ,
`houseIeee`  varchar(50) NULL ,
`SMSstate`  bigint(2) NULL ,
`emailState`  bigint(2) NULL ,
`content`  varchar(200) NULL ,
PRIMARY KEY (`id`)
)
;
/*2015-3-2 手机号码表*/
CREATE TABLE `phoneNO` (
`id`  int(20) NULL  AUTO_INCREMENT ,
`houseIEEE`  varchar(50) NULL ,
`phoneNO`  varchar(50) NULL  ,
PRIMARY KEY (`id`)
)
;
/*2015-3-2 邮箱表*/
CREATE TABLE `warnEmail` (
`id`  int(20) NOT NULL AUTO_INCREMENT ,
`houseIEEE`  varchar(50) NULL ,
`warnEmail`  varchar(50) NULL ,
PRIMARY KEY (`id`)
)
;

/*添加设备属性unknow*/
INSERT INTO `attributenamelib` (`attributeNameCn`, `attributeName`) VALUES ('未识别', 'unknow');
/*modevice增加图片名称字段*/
alter table modedevice add column picName varchar(30) DEFAULT null;

/*删除生产权限可看到服务器管理*/
delete from relilevelprivilege where level_id=4 and privilege_id = 7001;

/*短息历史记录表*/
CREATE TABLE `messagehistory` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `messageid` int(20) DEFAULT NULL,
  `sendtime` datetime DEFAULT NULL,
  `type` varchar(30) DEFAULT NULL,
  `phonenumber` varchar(50) DEFAULT NULL,
  `content` varchar(300) DEFAULT NULL,
  `state` varchar(20) DEFAULT NULL,
  `remark` varchar(50) DEFAULT NULL,
  `houseIeee` varchar(50) DEFAULT NULL,
  `emailcontent` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/*app崩溃日志*/
CREATE TABLE `file_appinfo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `houseIEEE` varchar(50) DEFAULT NULL,
  `device_type` text,
  `sys_ver` varchar(20) DEFAULT NULL,
  `description` text,
  `opdatetime` datetime DEFAULT CURRENT_TIMESTAMP,
  `file_name` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `error_no` varchar(255) DEFAULT '0',
  `oderfilename` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/*ios消息推送*/
CREATE TABLE `warnsend` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `houseIEEE` varchar(50) DEFAULT NULL,
  `username` varchar(100) DEFAULT NULL,
  `deviceToken` varchar(150) DEFAULT NULL,
  `type` varchar(2) DEFAULT NULL,
  `lastTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `isonline` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `houseIEEE` (`houseIEEE`) USING BTREE,
  KEY `type` (`type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


/*2015-3-12 焚风 修改存储过程  behavior=4 and behavior=3 */
DROP PROCEDURE IF EXISTS `ModeiashvacProc`;
CREATE PROCEDURE `ModeiashvacProc`(IN `behavior` bigint,IN `houseId` bigint,IN `id` bigint,IN `mid` bigint)
BEGIN
	#Routine body goes here...
CASE
WHEN behavior=1 THEN
		Select u.* from Modeiassub u,Modeiasmain m where u.mid=m.id and u.houseId = houseId;
WHEN behavior=2 THEN
		Select m.*,lib.attrName,lib.DataType,d.deviceName
		from Modeiasmain m  INNER JOIN Modedevice d on m.modeEpId=d.id
												INNER JOIN Deviceattrlib lib on m.attrlibId=lib.id 
												where m.houseId = houseId;		
WHEN behavior=3 THEN
		Select u.* from Modehvacsub u,Modehvacmain m where u.mid=m.mainid and u.houseId = houseId;
WHEN behavior=4 THEN
		Select m.*,lib.attrName,lib.DataType,d.deviceName
		from Modehvacmain m INNER JOIN Modedevice d on m.modeEpId=d.id
												INNER JOIN Deviceattrlib lib on m.attrlibId=lib.id 
												INNER JOIN modemainclause c on c.id = m.mainID
												where m.houseId = houseId;
/*
WHEN behavior=5 THEN
		Select u.* from Modeiassub u where u.id = id;
WHEN behavior=6 THEN
		Select m.*,lib.attrName,lib.dataType
		from Modehvacsub u,Modeiasmain m,Deviceattrlib lib where u.mid=m.id and u.id = Id and m.attrlibId=lib.id;
WHEN behavior=7 THEN
		Select u.* from Modehvacsub u where u.id = id;
WHEN behavior=8 THEN
		Select m.*,lib.attrName,lib.dataType
		from Modehvacsub u,Modehvacmain m,Deviceattrlib lib where u.mid=m.id and u.id =Id and m.attrlibId=lib.id;
*/
WHEN behavior=9 THEN
		Select u.* from Modeiassub u where u.mid = id;
WHEN behavior=10 THEN
		Select m.*,lib.attrName,lib.dataType
		from Modeiasmain m,Deviceattrlib lib where m.id = id and m.attrlibId=lib.id;
WHEN behavior=11 THEN
		Select u.* from Modehvacsub u where u.mid = id;
WHEN behavior=12 THEN
		Select m.*,lib.attrName,lib.dataType
		from Modehvacmain m,Deviceattrlib lib where m.id =id and m.attrlibId=lib.id;
WHEN behavior=14 THEN
		SELECT m.*, lib.attrName,lib.dataType
    FROM Modehvacmain m LEFT JOIN Deviceattrlib lib on m.attrlibId = lib.id WHERE m.mainID = id;
WHEN behavior=13 THEN
		Select u.*,m.modeName
		from Usermodesub u,Usermodemain m where u.mid=m.id and u.dest = id 
		and (u.Act='DeactiveIAS' or u.Act='ActiveIAS' or u.Act='IgnoreIAS' or u.Act='DeactiveHVAC' or u.Act='ActiveHVAC' or u.Act='IgnoreHVAC');
END CASE;
END;

/*报警类型*/
CREATE TABLE `warntypetable` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ChinesewarnType` varchar(255) DEFAULT NULL,
  `EnglishwarnType` varchar(255) DEFAULT NULL,
  `w_mode` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `warntypetable` VALUES ('1', '停止告警', 'stop', '0');
INSERT INTO `warntypetable` VALUES ('2', '偷窃', 'Burglar', '1');
INSERT INTO `warntypetable` VALUES ('3', '火警', 'Fire', '2');
INSERT INTO `warntypetable` VALUES ('4', '紧急', 'Emergency', '3');
INSERT INTO `warntypetable` VALUES ('5', '静音', 'Mute', '4');
INSERT INTO `warntypetable` VALUES ('6', '故障', 'Device Trouble', '5');
INSERT INTO `warntypetable` VALUES ('7', '门铃', 'Doorbell', '6');
INSERT INTO `warntypetable` VALUES ('8', '按时到达', 'On time', '7');
INSERT INTO `warntypetable` VALUES ('9', '晚归', 'late', '8');
INSERT INTO `warntypetable` VALUES ('10', '到达', 'arrive', '9');
INSERT INTO `warntypetable` VALUES ('11', '离家', 'Leave home', '10');
INSERT INTO `warntypetable` VALUES ('12', '未离家', 'Have not left home', '11');
INSERT INTO `warntypetable` VALUES ('13', '按时离家 ', 'Leave home on time', '12');
INSERT INTO `warntypetable` VALUES ('14', '低压告警', 'lowbattery', '13');

/*添加语言栏*/
ALTER TABLE `warnsend`
ADD COLUMN `Language`  varchar(10) DEFAULT NULL;

/*设备调级0%对应修改*/
update modeactlib set ScenePara = '0600010008000100' where id in (47,49,51);

/*设备属性字典表新增字段*/
ALTER TABLE `attributenamelib`
ADD COLUMN `clusterID`  varchar(20) NULL AFTER `attributeName`,
ADD COLUMN `attrID`  varchar(20) NULL AFTER `clusterID`;

/*设备属性字典表数据*/
UPDATE `attributenamelib` SET `id`='1', `attributeNameCn`='紫外线强度', `attributeName`='ultraviolet', `clusterID`='FE60', `attrID`='0000' WHERE (`id`='1');
UPDATE `attributenamelib` SET `id`='2', `attributeNameCn`='占有状态或空闲状态', `attributeName`='occupied or unoccupied', `clusterID`='0406', `attrID`='0000' WHERE (`id`='2');
UPDATE `attributenamelib` SET `id`='3', `attributeNameCn`='温度', `attributeName`='temperature', `clusterID`='0402', `attrID`='0000' WHERE (`id`='3');
UPDATE `attributenamelib` SET `id`='4', `attributeNameCn`='湿度', `attributeName`='Humidity', `clusterID`='0405', `attrID`='0000' WHERE (`id`='4');
UPDATE `attributenamelib` SET `id`='5', `attributeNameCn`='色温', `attributeName`='Color temperature', `clusterID`='0300', `attrID`='0007' WHERE (`id`='5');
UPDATE `attributenamelib` SET `id`='6', `attributeNameCn`='锁类型', `attributeName`='lock_type', `clusterID`='0101', `attrID`='0001' WHERE (`id`='6');
UPDATE `attributenamelib` SET `id`='7', `attributeNameCn`='门状态', `attributeName`='door_status', `clusterID`='0101', `attrID`='0003' WHERE (`id`='7');
UPDATE `attributenamelib` SET `id`='8', `attributeNameCn`='门锁状态', `attributeName`='lock_status', `clusterID`='0101', `attrID`='0000' WHERE (`id`='8');
UPDATE `attributenamelib` SET `id`='9', `attributeNameCn`='光照亮度', `attributeName`='brightness', `clusterID`='0400', `attrID`='0000' WHERE (`id`='9');
UPDATE `attributenamelib` SET `id`='10', `attributeNameCn`='开关状态', `attributeName`='on_off_status', `clusterID`='0006', `attrID`='0000' WHERE (`id`='10');
UPDATE `attributenamelib` SET `id`='11', `attributeNameCn`='开关状态', `attributeName`='open_close_status', `clusterID`='0102', `attrID`='0000' WHERE (`id`='11');
UPDATE `attributenamelib` SET `id`='12', `attributeNameCn`='级别控制', `attributeName`='level', `clusterID`='0008', `attrID`='0000' WHERE (`id`='12');
UPDATE `attributenamelib` SET `id`='13', `attributeNameCn`='设备心跳', `attributeName`='heartbeat', `clusterID`='0500', `attrID`='0002' WHERE (`id`='13');
UPDATE `attributenamelib` SET `id`='14', `attributeNameCn`='设备故障', `attributeName`='device trouble', `clusterID`='', `attrID`='' WHERE (`id`='14');
UPDATE `attributenamelib` SET `id`='15', `attributeNameCn`='门铃', `attributeName`='doorbell', `clusterID`='', `attrID`='' WHERE (`id`='15');
UPDATE `attributenamelib` SET `id`='16', `attributeNameCn`='静音', `attributeName`='mute', `clusterID`='', `attrID`='' WHERE (`id`='16');
UPDATE `attributenamelib` SET `id`='17', `attributeNameCn`='入侵报警', `attributeName`='burglar', `clusterID`='', `attrID`='' WHERE (`id`='17');
UPDATE `attributenamelib` SET `id`='18', `attributeNameCn`='紧急报警', `attributeName`='emergency', `clusterID`='', `attrID`='' WHERE (`id`='18');
UPDATE `attributenamelib` SET `id`='19', `attributeNameCn`='火警', `attributeName`='fire', `clusterID`='', `attrID`='' WHERE (`id`='19');
UPDATE `attributenamelib` SET `id`='20', `attributeNameCn`='停止报警', `attributeName`='stop', `clusterID`='', `attrID`='' WHERE (`id`='20');
UPDATE `attributenamelib` SET `id`='21', `attributeNameCn`='红外感应设备失效时间', `attributeName`='irdisabletime', `clusterID`='FE60', `attrID`='FFFF' WHERE (`id`='21');
UPDATE `attributenamelib` SET `id`='22', `attributeNameCn`='功率', `attributeName`='power', `clusterID`='0702', `attrID`='E002' WHERE (`id`='22');
UPDATE `attributenamelib` SET `id`='23', `attributeNameCn`='电压', `attributeName`='voltage', `clusterID`='0702', `attrID`='E001' WHERE (`id`='23');
UPDATE `attributenamelib` SET `id`='24', `attributeNameCn`='电能', `attributeName`='energy', `clusterID`='0702', `attrID`='E003' WHERE (`id`='24');
UPDATE `attributenamelib` SET `id`='25', `attributeNameCn`='电流', `attributeName`='current', `clusterID`='0702', `attrID`='E000' WHERE (`id`='25');
UPDATE `attributenamelib` SET `id`='26', `attributeNameCn`='电池电压', `attributeName`='Battery Voltage', `clusterID`='0001', `attrID`='0020' WHERE (`id`='26');
UPDATE `attributenamelib` SET `id`='27', `attributeNameCn`='低电压', `attributeName`='Low Battery Voltage', `clusterID`='', `attrID`='' WHERE (`id`='27');
UPDATE `attributenamelib` SET `id`='28', `attributeNameCn`='登记或是不登记', `attributeName`='enroll or not enroll', `clusterID`='0500', `attrID`='0000' WHERE (`id`='28');
UPDATE `attributenamelib` SET `id`='29', `attributeNameCn`='按时离家', `attributeName`='leave home at time', `clusterID`='', `attrID`='' WHERE (`id`='29');
UPDATE `attributenamelib` SET `id`='30', `attributeNameCn`='按时到达', `attributeName`='arrive on time', `clusterID`='', `attrID`='' WHERE (`id`='30');
UPDATE `attributenamelib` SET `id`='31', `attributeNameCn`='未离开', `attributeName`='at home', `clusterID`='', `attrID`='' WHERE (`id`='31');
UPDATE `attributenamelib` SET `id`='32', `attributeNameCn`='离开', `attributeName`='leave', `clusterID`='', `attrID`='' WHERE (`id`='32');
UPDATE `attributenamelib` SET `id`='33', `attributeNameCn`='达到', `attributeName`='arrival', `clusterID`='', `attrID`='' WHERE (`id`='33');
UPDATE `attributenamelib` SET `id`='34', `attributeNameCn`='晚归', `attributeName`='late', `clusterID`='', `attrID`='' WHERE (`id`='34');
UPDATE `attributenamelib` SET `id`='35', `attributeNameCn`='Zone设备类型', `attributeName`='ias zone type', `clusterID`='0500', `attrID`='0001' WHERE (`id`='35');
UPDATE `attributenamelib` SET `id`='36', `attributeNameCn`='GSM信号强度', `attributeName`='gsmrssi', `clusterID`='FE20', `attrID`='E007' WHERE (`id`='36');
UPDATE `attributenamelib` SET `id`='37', `attributeNameCn`='设备状态', `attributeName`='isonline', `clusterID`='0000', `attrID`='FFFF' WHERE (`id`='37');
UPDATE `attributenamelib` SET `id`='38', `attributeNameCn`='电压', `attributeName`='voltage', `clusterID`='0B04', `attrID`='0505' WHERE (`id`='38');
UPDATE `attributenamelib` SET `id`='39', `attributeNameCn`='电流', `attributeName`='current', `clusterID`='0B04', `attrID`='0508' WHERE (`id`='39');
UPDATE `attributenamelib` SET `id`='40', `attributeNameCn`='功率', `attributeName`='power', `clusterID`='0B04', `attrID`='050B' WHERE (`id`='40');
UPDATE `attributenamelib` SET `id`='41', `attributeNameCn`='心跳', `attributeName`='heartbeat', `clusterID`='FE40', `attrID`='0003' WHERE (`id`='41');
UPDATE `attributenamelib` SET `id`='42', `attributeNameCn`='电能', `attributeName`='energy', `clusterID`='0702', `attrID`='0000' WHERE (`id`='42');
UPDATE `attributenamelib` SET `id`='43', `attributeNameCn`='锁类型', `attributeName`='lock_type', `clusterID`='0101', `attrID`='E000' WHERE (`id`='43');
UPDATE `attributenamelib` SET `id`='44', `attributeNameCn`='报警持续时间', `attributeName`='MaxDuration', `clusterID`='0502', `attrID`='0000' WHERE (`id`='44');
UPDATE `attributenamelib` SET `id`='45', `attributeNameCn`='Zone设备登记CIE地址', `attributeName`='IAS_CIE_Address', `clusterID`='0500', `attrID`='0010' WHERE (`id`='45');
UPDATE `attributenamelib` SET `id`='46', `attributeNameCn`='602设备报警电话1', `attributeName`='Telephone\nNumber 1', `clusterID`='FE20', `attrID`='E001' WHERE (`id`='46');
UPDATE `attributenamelib` SET `id`='47', `attributeNameCn`='602设备报警电话2', `attributeName`='Telephone\nNumber 2', `clusterID`='FE20', `attrID`='E002' WHERE (`id`='47');
UPDATE `attributenamelib` SET `id`='48', `attributeNameCn`='602设备报警电话3', `attributeName`='Telephone\nNumber 3', `clusterID`='FE20', `attrID`='E003' WHERE (`id`='48');
UPDATE `attributenamelib` SET `id`='49', `attributeNameCn`='602设备报警短信内容1', `attributeName`='Message Number 1', `clusterID`='FE20', `attrID`='E004' WHERE (`id`='49');
UPDATE `attributenamelib` SET `id`='50', `attributeNameCn`='602设备报警短信内容2', `attributeName`='Message Number 2', `clusterID`='FE20', `attrID`='E005' WHERE (`id`='50');
UPDATE `attributenamelib` SET `id`='51', `attributeNameCn`='602设备报警短信内容3', `attributeName`='Message Number 3', `clusterID`='FE20', `attrID`='E006' WHERE (`id`='51');
UPDATE `attributenamelib` SET `id`='52', `attributeNameCn`='撤布防的短信密码', `attributeName`='Arm/Disam\nMessgae\nPassword', `clusterID`='FE20', `attrID`='E008' WHERE (`id`='52');

/*设备属性历史字典表添加设备属性名称字段*/
ALTER TABLE `deviceattributehistorydictionary`
ADD COLUMN `attributeName`  varchar(50) NULL AFTER `attribute_id`;
/*设备属性历史字典表添加心跳电池电压名称*/
update deviceattributehistorydictionary set attributeName = 'heartbeat' WHERE cluster_id='0500' and attribute_id = '0002';
update deviceattributehistorydictionary set attributeName = 'battery voltage' WHERE cluster_id='0001' and attribute_id = '0020';

/*修改IAS查询工作集  hebavior=7*/
DROP PROCEDURE IF EXISTS `ModemacrosubProc`;
CREATE  PROCEDURE `ModemacrosubProc`(IN `behavior` bigint,IN `macroId` bigint,IN `type` bigint)
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
		and  ((type=0 and h.CIEStatus=0 and h.ZoneActType=0 ) OR
         (type=1 and h.CIEStatus=0 and h.ZoneActType=1 ) OR
         (type=2 and h.CIEStatus=1 and h.ZoneActType=0 ) OR
         (type=3 and h.CIEStatus=1 and h.ZoneActType=1 ));

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

/*modeeplib、modenodelib、devicebind表新增字段*/
ALTER TABLE `modedevicebind`
ADD COLUMN `clusterNameEn`  varchar(100) NULL AFTER `clusterName`;

ALTER TABLE `modeeplib`
ADD COLUMN `deviceNameEn`  varchar(200) NULL AFTER `deviceName`,
ADD COLUMN `clusterNameEn`  varchar(100) NULL AFTER `clusterName`,
ADD COLUMN `descriptionEn`  varchar(2000) NULL AFTER `description`;

ALTER TABLE `modenodelib`
ADD COLUMN `deviceNameEn`  varchar(200) NULL AFTER `deviceName`,
ADD COLUMN `clusterNameEn`  varchar(100) NULL AFTER `clusterName`,
ADD COLUMN `descriptionEn`  varchar(2000) NULL AFTER `description`,
ADD COLUMN `powerTypeEn`  varchar(200) NULL AFTER `powerType`,
ADD COLUMN `activationMethodEn`  varchar(2000) NULL AFTER `activationMethod`,
ADD COLUMN `resetDefaultEn`  varchar(2000) NULL AFTER `resetDefault`,
ADD COLUMN `remarkEn`  varchar(2000) NULL AFTER `remark`;

/*添加modenodelib、modeeplib表英文字段数据*/
update modenodelib left join modenodelib_en on modenodelib.modelId = modenodelib_en.modelId
set modenodelib.deviceNameEn=modenodelib_en.deviceName,modenodelib.clusterNameEn = modenodelib_en.clusterName,modenodelib.descriptionEn = modenodelib_en.description,
modenodelib.powerTypeEn = modenodelib_en.powerType,modenodelib.activationMethodEn = modenodelib_en.activationMethod,modenodelib.resetDefaultEn=modenodelib_en.resetDefault,
modenodelib.remarkEn=modenodelib_en.remark;

update modeeplib LEFT JOIN modeeplib_en on modeeplib.internelModelId = modeeplib_en.internelModelId and modeeplib.ep= modeeplib_en.ep
set modeeplib.deviceNameEn=modeeplib_en.deviceName,modeeplib.clusterNameEn=modeeplib_en.clusterName; 

/*导入时更新modesub的dest为mainclause的id*/
DROP PROCEDURE IF EXISTS `ModeimportTest`;
CREATE PROCEDURE `ModeimportTest`(IN `behavior` bigint,IN `houseId` bigint)
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
		update usermodesub a inner join modemainclause d on a.Dest=d.oldId and a.HouseID=d.HouseID
		SET a.Dest=d.id WHERE a.houseId=houseId and (a.Act='ActiveIAS' or a.Act='DeactiveIAS' or a.Act='IgnoreIAS');
		update usermodesub a inner join modemainclause e on a.Dest=e.oldId and a.HouseID=e.HouseID
		SET a.Dest=e.id WHERE a.houseId=houseId and (a.Act='ActiveHVAC' or a.Act='DeactiveHVAC' or a.Act='IgnoreHVAC');

WHEN behavior=7 THEN
		SELECT * FROM usermodesub a WHERE a.HouseID=171 ORDER BY a.act;
#		SELECT * FROM modeschemesub c WHERE c.houseId=171 ORDER BY c.act;
		SELECT * FROM modemacromain b WHERE b.HouseID=171 ORDER BY b.oldId;
		SELECT * FROM modeschememain c WHERE c.houseId=171 ORDER BY c.oldId;	
		SELECT * FROM modeiasmain d WHERE d.houseId=171 ORDER BY d.oldId;	
		SELECT * FROM modehvacmain e WHERE e.houseId=171 ORDER BY e.oldId;

END CASE;
END


/*deviceattributehistorydictionary 表新增数据*/
INSERT INTO `deviceattributehistorydictionary` (`deviceName`, `modelId`, `pushCount`, `cluster_id`, `attribute_id`, `attributeName`) VALUES ('Z713', 'Z713E3ED', '24', '0001', '0020', 'battery voltage');
INSERT INTO `deviceattributehistorydictionary` (`deviceName`, `modelId`, `pushCount`, `cluster_id`, `attribute_id`, `attributeName`) VALUES ('ZB02B', 'ZB02BE3ED', '24', '0001', '0020', 'battery voltage');
INSERT INTO `deviceattributehistorydictionary` (`deviceName`, `modelId`, `pushCount`, `cluster_id`, `attribute_id`, `attributeName`) VALUES ('Z711', 'Z711E3ED', '24', '0001', '0020', 'battery voltage');
INSERT INTO `deviceattributehistorydictionary` (`deviceName`, `modelId`, `pushCount`, `cluster_id`, `attribute_id`, `attributeName`) VALUES ('ZB02A', 'ZB02AE3ED', '24', '0001', '0020', 'battery voltage');
update deviceattributehistorydictionary set cluster_id = '0001',attribute_id = '0020',attributeName = 'battery voltage' WHERE modelId = 'Z302GE3ED';
update deviceattributehistorydictionary set cluster_id = '0001',attribute_id = '0020',attributeName = 'battery voltage' WHERE modelId = 'Z302HE3ED';
INSERT INTO `deviceattributehistorydictionary` (`deviceName`, `modelId`, `pushCount`, `cluster_id`, `attribute_id`, `attributeName`) VALUES ('ZB11D', 'ZB11DE2ED', '24', '0001', '0020', 'battery voltage');
INSERT INTO `deviceattributehistorydictionary` (`deviceName`, `modelId`, `pushCount`, `cluster_id`, `attribute_id`, `attributeName`) VALUES ('ZB11B', 'ZB11BE2ED', '24', '0001', '0020', 'battery voltage');
INSERT INTO `deviceattributehistorydictionary` (`deviceName`, `modelId`, `pushCount`, `cluster_id`, `attribute_id`, `attributeName`) VALUES ('ZB01D', 'ZB01DE0ED', '24', '0001', '0020', 'battery voltage');
update deviceattributehistorydictionary set cluster_id = '0001',attribute_id = '0020',attributeName = 'battery voltage' WHERE modelId = 'Z302BE3ED';
update deviceattributehistorydictionary set cluster_id = '0001',attribute_id = '0020',attributeName = 'battery voltage' WHERE modelId = 'ZB01BE0ED';
update deviceattributehistorydictionary set cluster_id = '0001',attribute_id = '0020',attributeName = 'battery voltage',pushCount = '24' WHERE modelId = 'Z716BE3ED';
INSERT INTO `deviceattributehistorydictionary` (`deviceName`, `modelId`, `pushCount`, `cluster_id`, `attribute_id`, `attributeName`) VALUES ('IAS Zone', 'ZA01AE3R', '24', '0500', '0002', 'heartbeat');
INSERT INTO `deviceattributehistorydictionary` (`deviceName`, `modelId`, `pushCount`, `cluster_id`, `attribute_id`, `attributeName`) VALUES ('Z302D', 'Z302DE0ED', '24', '0500', '0002', 'heartbeat');
INSERT INTO `deviceattributehistorydictionary` (`deviceName`, `modelId`, `pushCount`, `cluster_id`, `attribute_id`, `attributeName`) VALUES ('Z302E', 'Z302EE0ED', '24', '0500', '0002', 'heartbeat');


/*devicedictionary表新增数据*/
delete from deviceattributehistorydictionary;
insert into deviceattributehistorydictionary(deviceName,modelId,pushCount,cluster_id,attribute_id,attributeName) values('Z302A','Z302AE0ED',24,'0500','0002','心跳'),('Z302A','Z302AE3ED',24,'0500','0002','心跳'),('Z302B','Z302BE0ED',24,'0001','0020','电池电压'),('Z302B','Z302BE0ED',24,'0400','0000','照度'),('Z302B','Z302BE3ED',24,'0001','0020','电池电压'),('Z302B','Z302BE3ED',24,'0400','0000','照度'),('Z302C','Z302CE0ED',24,'0500','0002','心跳'),('Z302C','Z302CE3ED',24,'0500','0002','心跳'),('Z302D','Z302DE0ED',24,'0500','0002','心跳'),('Z302E','Z302EE0ED',24,'0500','0002','心跳'),('Z302G','Z302GE0ED',24,'0001','0020','电池电压'),('Z302G','Z302GE3ED',24,'0001','0020','电池电压'),('Z302H','Z302HE0ED',24,'0001','0020','电池电压'),('Z302H','Z302HE3ED',24,'0001','0020','电池电压'),('Z302J','Z302JE0ED',24,'0500','0002','心跳'),('Z302J','Z302JE3ED',24,'0500','0002','心跳'),('Z307','Z307E0ED',24,'0500','0002','心跳'),('Z307','Z307E3ED',24,'0500','0002','心跳'),('Z308','Z308E2ED',720,'0500','0002','心跳'),('Z308','Z308E3ED',720,'0500','0002','心跳'),('Z311A','Z311AE2ED',24,'0500','0002','心跳'),('Z311A','Z311AE3ED',24,'0500','0002','心跳'),('Z311B','Z311BE2ED',24,'0001','0020','电池电压'),('Z311B ','Z311BE2ED',24,'0400','0000','照度'),('Z311B','Z311BE3ED',24,'0001','0020','电池电压'),('Z311B ','Z311BE3ED',24,'0400','0000','照度'),('Z311C','Z311CE2ED',24,'0500','0002','心跳'),('Z311C','Z311CE3ED',24,'0500','0002','心跳'),('Z311E','Z311EE2ED',24,'0500','0002','心跳'),('Z311E','Z311EE3ED',24,'0500','0002','心跳'),('Z311G','Z311GE2ED',24,'0001','0020','电池电压'),('Z311G','Z311GE3ED',24,'0001','0020','电池电压'),('Z311H','Z311HE2ED',24,'0001','0020','电池电压'),('Z311H','Z311HE3ED',24,'0001','0020','电池电压'),('Z311J','Z311JE3ED',24,'0500','0002','心跳'),('Z311W','Z311WE3ED',24,'0500','0002','心跳'),('Z312','Z312E2ED',24,'0500','0002','心跳'),('Z312','Z312E3ED',24,'0500','0002','心跳'),('Z312','Z312E3ED',24,'0001','0020','电池电压'),('Z501A','Z501AE0ED',24,'0001','0020','电池电压'),('Z501A','Z501AE3ED',24,'0001','0020','电池电压'),('Z501B','Z501BE0ED',24,'0001','0020','电池电压'),('Z501B','Z501BE3ED',24,'0001','0020','电池电压'),('Z501C','Z501CE0ED',24,'0001','0020','电池电压'),('Z501C','Z501CE3ED',24,'0001','0020','电池电压'),('Z501G','Z501GE3ED',24,'0001','0020','电池电压'),('Z502A','Z502AE3ED',24,'0001','0020','电池电压'),('Z503','Z503E0ED',24,'0001','0020','电池电压'),('Z503','Z503E3ED',24,'0001','0020','电池电压'),('Z602A','Z602AE3R',24,'0500','0002','心跳'),('Z602B','Z602BE3R',24,'0500','0002','心跳'),('Z711','Z711E0ED',24,'0001','0020','电池电压'),('Z711','Z711E0ED',480,'0402','0000','温度'),('Z711','Z711E0ED',480,'0405','0000','湿度'),('Z711','Z711E2ED',24,'0001','0020','电池电压'),('Z711','Z711E2ED',480,'0402','0000','温度'),('Z711','Z711E2ED',480,'0405','0000','湿度'),('Z711','Z711E3ED',24,'0001','0020','电池电压'),('Z711','Z711E3ED',480,'0402','0000','温度'),('Z711','Z711E3ED',480,'0405','0000','湿度'),('Z713','Z713E0ED',24,'0001','0020','电池电压'),('Z713','Z713E0ED',480,'0402','0000','温度'),('Z713','Z713E0ED',480,'0405','0000','湿度'),('Z713','Z713E2ED',24,'0001','0020','电池电压'),('Z713','Z713E2ED',480,'0402','0000','温度'),('Z713','Z713E2ED',480,'0405','0000','湿度'),('Z713','Z713E3ED',24,'0001','0020','电池电压'),('Z713','Z713E3ED',480,'0402','0000','温度'),('Z713','Z713E3ED',480,'0405','0000','湿度'),('Z716A','Z716AE0ED',24,'0001','0020','电池电压'),('Z716A','Z716AE0ED',480,'0402','0000','温度'),('Z716A','Z716AE0ED',480,'0405','0000','湿度'),('Z716A','Z716AE2ED',24,'0001','0020','电池电压'),('Z716A','Z716AE2ED',480,'0402','0000','温度'),('Z716A','Z716AE2ED',480,'0405','0000','湿度'),('Z716A','Z716AE3ED',24,'0001','0020','电池电压'),('Z716A','Z716AE3ED',480,'0402','0000','温度'),('Z716A','Z716AE3ED',480,'0405','0000','湿度'),('Z716B','Z716BE0ED',24,'0001','0020','电池电压'),('Z716B','Z716BE0ED',480,'0402','0000','温度'),('Z716B','Z716BE0ED',480,'0405','0000','湿度'),('Z716B','Z716BE2ED',24,'0001','0020','电池电压'),('Z716B','Z716BE2ED',480,'0402','0000','温度'),('Z716B','Z716BE2ED',480,'0405','0000','湿度'),('Z716B','Z716BE3ED',24,'0001','0020','电池电压'),('Z716B','Z716BE3ED',480,'0402','0000','温度'),('Z716B','Z716BE3ED',480,'0405','0000','湿度'),('Z725A','Z725AE0ED',24,'0001','0020','电池电压'),('Z725A','Z725AE0ED',480,'0402','0000','温度'),('Z725A','Z725AE0ED',480,'0405','0000','湿度'),('Z800','Z800E3R',96,'0006','0000','开关状态'),('Z800','Z800E3R',96,'0702','E003','电能'),('Z800','Z800E3R',96,'0702','E002','功率'),('Z800','Z800E3R',96,'0702','E001','电压'),('Z800','Z800E3R',96,'0702','E000','电流'),('Z801RX','Z801RXE0R',96,'0006','0000','开关状态'),('Z801RX','Z801RXE3R',96,'0006','0000','开关状态'),('Z801TXB','Z801TXBE0ED',24,'0500','0002','心跳'),('Z801TXB','Z801TXBE2ED',24,'0500','0002','心跳'),('Z801TXB','Z801TXBE3ED',24,'0500','0002','心跳');
insert into deviceattributehistorydictionary(deviceName,modelId,pushCount,cluster_id,attribute_id,attributeName) values('Z801WLS','Z801WLSE0ED',24,'0500','0002','心跳'),('Z801WLS','Z801WLSE3ED',24,'0500','0002','心跳'),('Z802','Z802E0R',96,'0006','0000','开关状态'),('Z802','Z802E3R',96,'0006','0000','开关状态'),('Z803','Z803E3R',96,'0006','0000','开关状态'),('Z803','Z803E3R',96,'0702','E003','电能'),('Z803','Z803E3R',96,'0702','E002','功率'),('Z803','Z803E3R',96,'0702','E001','电压'),('Z803','Z803E3R',96,'0702','E000','电流'),('Z805A','Z805AE3R',96,'0006','0000','开关状态'),('Z805A','Z805AE3R',96,'0702','E003','电能'),('Z805A','Z805AE3R',96,'0702','E002','功率'),('Z805A','Z805AE3R',96,'0702','E001','电压'),('Z805A','Z805AE3R',96,'0702','E000','电流'),('Z805B','Z805BE3R',96,'0006','0000','开关状态'),('Z805B','Z805BE3R',96,'0702','E003','电能'),('Z805B','Z805BE3R',96,'0702','E002','功率'),('Z805B','Z805BE3R',96,'0702','E001','电压'),('Z805B','Z805BE3R',96,'0702','E000','电流'),('Z806','Z806E0R',96,'0006','0000','开关状态'),('Z806','Z806E2R',96,'0006','0000','开关状态'),('Z806','Z806E3R',96,'0006','0000','开关状态'),('Z808A','Z808AE3R',96,'0006','0000','开关状态'),('Z808A','Z808AE3R',96,'0702','E003','电能'),('Z808A','Z808AE3R',96,'0702','E002','功率'),('Z808A','Z808AE3R',96,'0702','E001','电压'),('Z808A','Z808AE3R',96,'0702','E000','电流'),('Z808B','Z808BE3R',96,'0006','0000','开关状态'),('Z808B','Z808BE3R',96,'0008','0000','level等级'),('Z808B','Z808BE3R',96,'0702','E003','电能'),('Z808B','Z808BE3R',96,'0702','E002','功率'),('Z808B','Z808BE3R',96,'0702','E001','电压'),('Z808B','Z808BE3R',96,'0702','E000','电流'),('Z809A','Z809AE3R',96,'0006','0000','开关状态'),('Z809A','Z809AE3R',96,'0702','E003','电能'),('Z809A','Z809AE3R',96,'0702','E002','功率'),('Z809A','Z809AE3R',96,'0702','E001','电压'),('Z809A','Z809AE3R',96,'0702','E000','电流'),('Z809B','Z809BE3R',96,'0006','0000','开关状态'),('Z809B','Z809BE3R',96,'0008','0000','level等级'),('Z809B','Z809BE3R',96,'0702','E003','电能'),('Z809B','Z809BE3R',96,'0702','E002','功率'),('Z809B','Z809BE3R',96,'0702','E001','电压'),('Z809B','Z809BE3R',96,'0702','E000','电流'),('Z810B','Z810BE3R',96,'0006','0000','开关状态'),('Z810B','Z810BE3R',96,'0702','E003','电能'),('Z810B','Z810BE3R',96,'0702','E002','功率'),('Z810B','Z810BE3R',96,'0702','E001','电压'),('Z810B','Z810BE3R',96,'0702','E000','电流'),('Z811B','Z811BE3ED',24,'0001','0020','电池电压'),('Z811B','Z811BE3R',96,'0006','0000','开关状态'),('Z811B','Z811BE3R',96,'0008','0000','level等级'),('Z811','Z811E0R',96,'0006','0000','开关状态'),('Z811','Z811E2R',96,'0006','0000','开关状态'),('Z811','Z811E3R',96,'0006','0000','开关状态'),('Z812A','Z812AE2R',96,'0006','0000','开关状态'),('Z812A','Z812AE3R',96,'0006','0000','开关状态'),('Z812B','Z812BE0ED',24,'0001','0020','电池电压'),('Z812B','Z812BE3ED',24,'0001','0020','电池电压'),('Z815A','Z815AE3R',96,'0006','0000','开关状态'),('Z815A','Z815AE3R',96,'0702','E003','电能'),('Z815A','Z815AE3R',96,'0702','E002','功率'),('Z815A','Z815AE3R',96,'0702','E001','电压'),('Z815A','Z815AE3R',96,'0702','E000','电流'),('Z815B','Z815BE3R',96,'0006','0000','开关状态'),('Z815B','Z815BE3R',96,'0702','E003','电能'),('Z815B','Z815BE3R',96,'0702','E002','功率'),('Z815B','Z815BE3R',96,'0702','E001','电压'),('Z815B','Z815BE3R',96,'0702','E000','电流'),('Z815C','Z815CE3R',96,'0006','0000','开关状态'),('Z815C','Z815CE3R',96,'0702','E003','电能'),('Z815C','Z815CE3R',96,'0702','E002','功率'),('Z815C','Z815CE3R',96,'0702','E001','电压'),('Z815C','Z815CE3R',96,'0702','E000','电流'),('Z815D','Z815DE3R',96,'0006','0000','开关状态'),('Z815D','Z815DE3R',96,'0008','0000','level等级'),('Z815D','Z815DE3R',96,'0702','E003','电能'),('Z815D','Z815DE3R',96,'0702','E002','功率'),('Z815D','Z815DE3R',96,'0702','E001','电压'),('Z815D','Z815DE3R',96,'0702','E000','电流'),('Z815E','Z815EE3R',96,'0006','0000','开关状态'),('Z815E','Z815EE3R',96,'0008','0000','level等级'),('Z815E','Z815EE3R',96,'0702','E003','电能'),('Z815E','Z815EE3R',96,'0702','E002','功率'),('Z815E','Z815EE3R',96,'0702','E001','电压'),('Z815E','Z815EE3R',96,'0702','E000','电流'),('Z815I','Z815IE3R',96,'0006','0000','开关状态'),('Z815I','Z815IE3R',96,'0702','E003','电能'),('Z815I','Z815IE3R',96,'0702','E002','功率'),('Z815I','Z815IE3R',96,'0702','E001','电压'),('Z815I','Z815IE3R',96,'0702','E000','电流'),('Z815J','Z815JE3R',96,'0006','0000','开关状态'),('Z815J','Z815JE3R',96,'0702','E003','电能'),('Z815J','Z815JE3R',96,'0702','E002','功率'),('Z815J','Z815JE3R',96,'0702','E001','电压'),('Z815J','Z815JE3R',96,'0702','E000','电流'),('Z815K','Z815KE3R',96,'0006','0000','开关状态'),('Z815K','Z815KE3R',96,'0702','E003','电能'),('Z815K','Z815KE3R',96,'0702','E002','功率'),('Z815K','Z815KE3R',96,'0702','E001','电压');
insert into deviceattributehistorydictionary(deviceName,modelId,pushCount,cluster_id,attribute_id,attributeName) values('Z815K','Z815KE3R',96,'0702','E000','电流'),('Z815L','Z815LE3R',96,'0006','0000','开关状态'),('Z815L','Z815LE3R',96,'0008','0000','level等级'),('Z815L','Z815LE3R',96,'0702','E003','电能'),('Z815L','Z815LE3R',96,'0702','E002','功率'),('Z815L','Z815LE3R',96,'0702','E001','电压'),('Z815L','Z815LE3R',96,'0702','E000','电流'),('Z815M','Z815ME3R',96,'0006','0000','开关状态'),('Z815M','Z815ME3R',96,'0008','0000','level等级'),('Z815M','Z815ME3R',96,'0702','E003','电能'),('Z815M','Z815ME3R',96,'0702','E002','功率'),('Z815M','Z815ME3R',96,'0702','E001','电压'),('Z815M','Z815ME3R',96,'0702','E000','电流'),('Z815N','Z815NE3R',96,'0006','0000','开关状态'),('Z815N','Z815NE3R',96,'0008','0000','level等级'),('Z816B','Z816BE3R',96,'0006','0000','开关状态'),('Z816B','Z816BE3R',96,'0702','E003','电能'),('Z816B','Z816BE3R',96,'0702','E002','功率'),('Z816B','Z816BE3R',96,'0702','E001','电压'),('Z816B','Z816BE3R',96,'0702','E000','电流'),('Z816G','Z816GE3R',96,'0006','0000','开关状态'),('Z816G','Z816GE3R',96,'0702','E003','电能'),('Z816G','Z816GE3R',96,'0702','E002','功率'),('Z816G','Z816GE3R',96,'0702','E001','电压'),('Z816G','Z816GE3R',96,'0702','E000','电流'),('Z816H','Z816HE3R',96,'0006','0000','开关状态'),('Z816H','Z816HE3R',96,'0702','E003','电能'),('Z816H','Z816HE3R',96,'0702','E002','功率'),('Z816H','Z816HE3R',96,'0702','E001','电压'),('Z816H','Z816HE3R',96,'0702','E000','电流'),('Z816I','Z816IE3R',96,'0006','0000','开关状态'),('Z816I','Z816IE3R',96,'0702','E003','电能'),('Z816I','Z816IE3R',96,'0702','E002','功率'),('Z816I','Z816IE3R',96,'0702','E001','电压'),('Z816I','Z816IE3R',96,'0702','E000','电流'),('Z817A','Z817AE3R',96,'0006','0000','开关状态'),('Z817A','Z817AE3R',96,'0702','E003','电能'),('Z817A','Z817AE3R',96,'0702','E002','功率'),('Z817A','Z817AE3R',96,'0702','E001','电压'),('Z817A','Z817AE3R',96,'0702','E000','电流'),('Z817B','Z817BE3R',96,'0006','0000','开关状态'),('Z817B','Z817BE3R',96,'0008','0000','level等级'),('Z817B','Z817BE3R',96,'0702','E003','电能'),('Z817B','Z817BE3R',96,'0702','E002','功率'),('Z817B','Z817BE3R',96,'0702','E001','电压'),('Z817B','Z817BE3R',96,'0702','E000','电流'),('Z817C','Z817CE3R',96,'0006','0000','开关状态'),('Z817C','Z817CE3R',96,'0702','E003','电能'),('Z817C','Z817CE3R',96,'0702','E002','功率'),('Z817C','Z817CE3R',96,'0702','E001','电压'),('Z817C','Z817CE3R',96,'0702','E000','电流'),('Z821','Z821E3R',96,'0006','0000','开关状态'),('Z821','Z821E3R',96,'0702','E003','电能'),('Z821','Z821E3R',96,'0702','E002','功率'),('Z821','Z821E3R',96,'0702','E001','电压'),('Z821','Z821E3R',96,'0702','E000','电流'),('Z825A','Z825AE3R',96,'0006','0000','开关状态'),('Z825A','Z825AE3R',96,'0702','E003','电能'),('Z825A','Z825AE3R',96,'0702','E002','功率'),('Z825A','Z825AE3R',96,'0702','E001','电压'),('Z825A','Z825AE3R',96,'0702','E000','电流'),('Z825B','Z825BE3R',96,'0006','0000','开关状态'),('Z825B','Z825BE3R',96,'0702','E003','电能'),('Z825B','Z825BE3R',96,'0702','E002','功率'),('Z825B','Z825BE3R',96,'0702','E001','电压'),('Z825B','Z825BE3R',96,'0702','E000','电流'),('Z825C','Z825CE3R',96,'0006','0000','开关状态'),('Z825C','Z825CE3R',96,'0702','E003','电能'),('Z825C','Z825CE3R',96,'0702','E002','功率'),('Z825C','Z825CE3R',96,'0702','E001','电压'),('Z825C','Z825CE3R',96,'0702','E000','电流'),('Z825D','Z825DE3R',96,'0006','0000','开关状态'),('Z825D','Z825DE3R',96,'0008','0000','level等级'),('Z825D','Z825DE3R',96,'0702','E003','电能'),('Z825D','Z825DE3R',96,'0702','E002','功率'),('Z825D','Z825DE3R',96,'0702','E001','电压'),('Z825D','Z825DE3R',96,'0702','E000','电流'),('Z825E','Z825EE3R',96,'0006','0000','开关状态'),('Z825E','Z825EE3R',96,'0008','0000','level等级'),('Z825E','Z825EE3R',96,'0702','E003','电能'),('Z825E','Z825EE3R',96,'0702','E002','功率'),('Z825E','Z825EE3R',96,'0702','E001','电压'),('Z825E','Z825EE3R',96,'0702','E000','电流'),('Z825J','Z825JE3R',96,'0006','0000','开关状态'),('Z825J','Z825JE3R',96,'0702','E003','电能'),('Z825J','Z825JE3R',96,'0702','E002','功率'),('Z825J','Z825JE3R',96,'0702','E001','电压'),('Z825J','Z825JE3R',96,'0702','E000','电流'),('Z825K111','Z825K111E3R',96,'0006','0000','开关状态'),('Z825K111','Z825K111E3R',96,'0702','E003','电能'),('Z825K111','Z825K111E3R',96,'0702','E002','功率'),('Z825K111','Z825K111E3R',96,'0702','E001','电压'),('Z825K111','Z825K111E3R',96,'0702','E000','电流'),('Z825K121','Z825K121E3R',96,'0006','0000','开关状态'),('Z825K121','Z825K121E3R',96,'0702','E003','电能'),('Z825K121','Z825K121E3R',96,'0702','E002','功率'),('Z825K121','Z825K121E3R',96,'0702','E001','电压'),('Z825K121','Z825K121E3R',96,'0702','E000','电流'),('Z825K122','Z825K122E3R',96,'0006','0000','开关状态'),('Z825K122','Z825K122E3R',96,'0702','E003','电能');
insert into deviceattributehistorydictionary(deviceName,modelId,pushCount,cluster_id,attribute_id,attributeName) values('Z825K122','Z825K122E3R',96,'0702','E002','功率'),('Z825K122','Z825K122E3R',96,'0702','E001','电压'),('Z825K122','Z825K122E3R',96,'0702','E000','电流'),('Z825K131','Z825K131E3R',96,'0006','0000','开关状态'),('Z825K131','Z825K131E3R',96,'0702','E003','电能'),('Z825K131','Z825K131E3R',96,'0702','E002','功率'),('Z825K131','Z825K131E3R',96,'0702','E001','电压'),('Z825K131','Z825K131E3R',96,'0702','E000','电流'),('Z825K133','Z825K133E3R',96,'0006','0000','开关状态'),('Z825K133','Z825K133E3R',96,'0702','E003','电能'),('Z825K133','Z825K133E3R',96,'0702','E002','功率'),('Z825K133','Z825K133E3R',96,'0702','E001','电压'),('Z825K133','Z825K133E3R',96,'0702','E000','电流'),('Z825K135','Z825K135E3R',96,'0006','0000','开关状态'),('Z825K135','Z825K135E3R',96,'0702','E003','电能'),('Z825K135','Z825K135E3R',96,'0702','E002','功率'),('Z825K135','Z825K135E3R',96,'0702','E001','电压'),('Z825K135','Z825K135E3R',96,'0702','E000','电流'),('Z825K211','Z825K211E3R',96,'0006','0000','开关状态'),('Z825K211','Z825K211E3R',96,'0702','E003','电能'),('Z825K211','Z825K211E3R',96,'0702','E002','功率'),('Z825K211','Z825K211E3R',96,'0702','E001','电压'),('Z825K211','Z825K211E3R',96,'0702','E000','电流'),('Z825K221','Z825K221E3R',96,'0006','0000','开关状态'),('Z825K221','Z825K221E3R',96,'0702','E003','电能'),('Z825K221','Z825K221E3R',96,'0702','E002','功率'),('Z825K221','Z825K221E3R',96,'0702','E001','电压'),('Z825K221','Z825K221E3R',96,'0702','E000','电流'),('Z825K222','Z825K222E3R',96,'0006','0000','开关状态'),('Z825K222','Z825K222E3R',96,'0702','E003','电能'),('Z825K222','Z825K222E3R',96,'0702','E002','功率'),('Z825K222','Z825K222E3R',96,'0702','E001','电压'),('Z825K222','Z825K222E3R',96,'0702','E000','电流'),('Z825K231','Z825K231E3R',96,'0006','0000','开关状态'),('Z825K231','Z825K231E3R',96,'0702','E003','电能'),('Z825K231','Z825K231E3R',96,'0702','E002','功率'),('Z825K231','Z825K231E3R',96,'0702','E001','电压'),('Z825K231','Z825K231E3R',96,'0702','E000','电流'),('Z825K233','Z825K233E3R',96,'0006','0000','开关状态'),('Z825K233','Z825K233E3R',96,'0702','E003','电能'),('Z825K233','Z825K233E3R',96,'0702','E002','功率'),('Z825K233','Z825K233E3R',96,'0702','E001','电压'),('Z825K233','Z825K233E3R',96,'0702','E000','电流'),('Z825K235','Z825K235E3R',96,'0006','0000','开关状态'),('Z825K235','Z825K235E3R',96,'0702','E003','电能'),('Z825K235','Z825K235E3R',96,'0702','E002','功率'),('Z825K235','Z825K235E3R',96,'0702','E001','电压'),('Z825K235','Z825K235E3R',96,'0702','E000','电流'),('Z825K311','Z825K311E3R',96,'0006','0000','开关状态'),('Z825K311','Z825K311E3R',96,'0702','E003','电能'),('Z825K311','Z825K311E3R',96,'0702','E002','功率'),('Z825K311','Z825K311E3R',96,'0702','E001','电压'),('Z825K311','Z825K311E3R',96,'0702','E000','电流'),('Z825K321','Z825K321E3R',96,'0006','0000','开关状态'),('Z825K321','Z825K321E3R',96,'0702','E003','电能'),('Z825K321','Z825K321E3R',96,'0702','E002','功率'),('Z825K321','Z825K321E3R',96,'0702','E001','电压'),('Z825K321','Z825K321E3R',96,'0702','E000','电流'),('Z825K322','Z825K322E3R',96,'0006','0000','开关状态'),('Z825K322','Z825K322E3R',96,'0702','E003','电能'),('Z825K322','Z825K322E3R',96,'0702','E002','功率'),('Z825K322','Z825K322E3R',96,'0702','E001','电压'),('Z825K322','Z825K322E3R',96,'0702','E000','电流'),('Z825K331','Z825K331E3R',96,'0006','0000','开关状态'),('Z825K331','Z825K331E3R',96,'0702','E003','电能'),('Z825K331','Z825K331E3R',96,'0702','E002','功率'),('Z825K331','Z825K331E3R',96,'0702','E001','电压'),('Z825K331','Z825K331E3R',96,'0702','E000','电流'),('Z825K333','Z825K333E3R',96,'0006','0000','开关状态'),('Z825K333','Z825K333E3R',96,'0702','E003','电能'),('Z825K333','Z825K333E3R',96,'0702','E002','功率'),('Z825K333','Z825K333E3R',96,'0702','E001','电压'),('Z825K333','Z825K333E3R',96,'0702','E000','电流'),('Z825K335','Z825K335E3R',96,'0006','0000','开关状态'),('Z825K335','Z825K335E3R',96,'0702','E003','电能'),('Z825K335','Z825K335E3R',96,'0702','E002','功率'),('Z825K335','Z825K335E3R',96,'0702','E001','电压'),('Z825K335','Z825K335E3R',96,'0702','E000','电流'),('Z826A','Z826AE3R',96,'0006','0000','开关状态'),('Z826A','Z826AE3R',96,'0702','E003','电能'),('Z826A','Z826AE3R',96,'0702','E002','功率'),('Z826A','Z826AE3R',96,'0702','E001','电压'),('Z826A','Z826AE3R',96,'0702','E000','电流'),('Z826B','Z826BE3R',96,'0006','0000','开关状态'),('Z826B','Z826BE3R',96,'0702','E003','电能'),('Z826B','Z826BE3R',96,'0702','E002','功率'),('Z826B','Z826BE3R',96,'0702','E001','电压'),('Z826B','Z826BE3R',96,'0702','E000','电流'),('Z826C','Z826CE3R',96,'0006','0000','开关状态'),('Z826C','Z826CE3R',96,'0702','E003','电能'),('Z826C','Z826CE3R',96,'0702','E002','功率'),('Z826C','Z826CE3R',96,'0702','E001','电压'),('Z826C','Z826CE3R',96,'0702','E000','电流'),('Z826D','Z826DE3R',96,'0006','0000','开关状态'),('Z826D','Z826DE3R',96,'0008','0000','level等级'),('Z826D','Z826DE3R',96,'0702','E003','电能'),('Z826D','Z826DE3R',96,'0702','E002','功率'),('Z826D','Z826DE3R',96,'0702','E001','电压'),('Z826D','Z826DE3R',96,'0702','E000','电流'),('Z962A','Z962AE3R',96,'0006','0000','开关状态');
insert into deviceattributehistorydictionary(deviceName,modelId,pushCount,cluster_id,attribute_id,attributeName) values('Z962A','Z962AE3R',96,'0702','E003','电能'),('Z962A','Z962AE3R',96,'0702','E002','功率'),('Z962A','Z962AE3R',96,'0702','E001','电压'),('Z962A','Z962AE3R',96,'0702','E000','电流'),('Z962B','Z962BE3R',96,'0006','0000','开关状态'),('Z962B','Z962BE3R',96,'0702','E003','电能'),('Z962B','Z962BE3R',96,'0702','E002','功率'),('Z962B','Z962BE3R',96,'0702','E001','电压'),('Z962B','Z962BE3R',96,'0702','E000','电流'),('Z962C','Z962CE3R',96,'0006','0000','开关状态'),('Z962C','Z962CE3R',96,'0702','E003','电能'),('Z962C','Z962CE3R',96,'0702','E002','功率'),('Z962C','Z962CE3R',96,'0702','E001','电压'),('Z962C','Z962CE3R',96,'0702','E000','电流'),('Z962G','Z962GE3R',96,'0006','0000','开关状态'),('Z962G','Z962GE3R',96,'0702','E003','电能'),('Z962G','Z962GE3R',96,'0702','E002','功率'),('Z962G','Z962GE3R',96,'0702','E001','电压'),('Z962G','Z962GE3R',96,'0702','E000','电流'),('Z962H','Z962HE3R',96,'0006','0000','开关状态'),('Z962H','Z962HE3R',96,'0702','E003','电能'),('Z962H','Z962HE3R',96,'0702','E002','功率'),('Z962H','Z962HE3R',96,'0702','E001','电压'),('Z962H','Z962HE3R',96,'0702','E000','电流'),('Z962I','Z962IE3R',96,'0006','0000','开关状态'),('Z962I','Z962IE3R',96,'0702','E003','电能'),('Z962I','Z962IE3R',96,'0702','E002','功率'),('Z962I','Z962IE3R',96,'0702','E001','电压'),('Z962I','Z962IE3R',96,'0702','E000','电流'),('Z970','Z970E3ED',24,'0001','0020','电池电压'),('ZA01A','ZA01AE0R',24,'0500','0002','心跳'),('ZA01A','ZA01AE2R',24,'0500','0002','心跳'),('ZA01A','ZA01AE3R',24,'0500','0002','心跳'),('ZA01B','ZA01BE0R',24,'0500','0002','心跳'),('ZA01B','ZA01BE2R',24,'0500','0002','心跳'),('ZA01B','ZA01BE3R',24,'0500','0002','心跳'),('ZA01C','ZA01CE0R',24,'0500','0002','心跳'),('ZA01C','ZA01CE2R',24,'0500','0002','心跳'),('ZA01C','ZA01CE3R',24,'0500','0002','心跳'),('ZA01D','ZA01DE0R',24,'0500','0002','心跳'),('ZA01D','ZA01DE2R',24,'0500','0002','心跳'),('ZA01D','ZA01DE3R',24,'0500','0002','心跳'),('ZA02E','ZA02EE3R',24,'0500','0002','心跳'),('ZB01A','ZB01AE0ED',24,'0500','0002','心跳'),('ZB01A','ZB01AE3ED',24,'0500','0002','心跳'),('ZB01B','ZB01BE0ED',24,'0001','0020','电池电压'),('ZB01B','ZB01BE3ED',24,'0001','0020','电池电压'),('ZB01C','ZB01CE0ED',24,'0500','0002','心跳'),('ZB01C','ZB01CE0ED',480,'0402','0000','温度'),('ZB01C','ZB01CE3ED',24,'0500','0002','心跳'),('ZB01C','ZB01CE3ED',480,'0402','0000','温度'),('ZB01D','ZB01DE0ED',24,'0001','0020','电池电压'),('ZB01D','ZB01DE0ED',24,'0406','0000','占有状态'),('ZB01D','ZB01DE2ED',24,'0001','0020','电池电压'),('ZB01D','ZB01DE2ED',24,'0406','0000','占有状态'),('ZB01D','ZB01DE3ED',24,'0001','0020','电池电压'),('ZB01D','ZB01DE3ED',24,'0406','0000','占有状态'),('ZB02A','ZB02AE0ED',24,'0001','0020','电池电压'),('ZB02A','ZB02AE2ED',24,'0001','0020','电池电压'),('ZB02A','ZB02AE3ED',24,'0001','0020','电池电压'),('ZB02B','ZB02BE0ED',24,'0001','0020','电池电压'),('ZB02B','ZB02BE2ED',24,'0001','0020','电池电压'),('ZB02B','ZB02BE3ED',24,'0001','0020','电池电压'),('ZB02C','ZB02CE0ED',24,'0001','0020','电池电压'),('ZB02C','ZB02CE2ED',24,'0001','0020','电池电压'),('ZB02C','ZB02CE3ED',24,'0001','0020','电池电压'),('ZB02E','ZB02EE0ED',24,'0500','0002','心跳'),('ZB02E','ZB02EE2ED',24,'0500','0002','心跳'),('ZB02E','ZB02EE3ED',24,'0500','0002','心跳'),('ZB02F','ZB02FE0ED',24,'0001','0020','电池电压'),('ZB02F','ZB02FE2ED',24,'0001','0020','电池电压'),('ZB02F','ZB02FE3ED',24,'0001','0020','电池电压'),('ZB02I','ZB02IE2ED',24,'0500','0002','心跳'),('ZB02I','ZB02IE3ED',24,'0500','0002','心跳'),('ZB02I','ZB02IE3ED',24,'0001','0020','电池电压'),('ZB02J','ZB02JE3ED',24,'0001','0020','电池电压'),('ZB11A1','ZB11A1E3ED',24,'0500','0002','心跳'),('ZB11A','ZB11AE2ED',24,'0500','0002','心跳'),('ZB11A','ZB11AE3ED',24,'0500','0002','心跳'),('ZB11B1','ZB11B1E3ED',24,'0001','0020','电池电压'),('ZB11B','ZB11BE2ED',24,'0001','0020','电池电压'),('ZB11B','ZB11BE3ED',24,'0001','0020','电池电压'),('ZB11C1','ZB11C1E3ED',24,'0500','0002','心跳'),('ZB11C1','ZB11C1E3ED',480,'0402','0000','温度'),('ZB11C','ZB11CE2ED',24,'0500','0002','心跳'),('ZB11C','ZB11CE2ED',480,'0402','0000','温度'),('ZB11C','ZB11CE3ED',24,'0500','0002','心跳'),('ZB11C','ZB11CE3ED',480,'0402','0000','温度'),('ZB11D1','ZB11D1E3ED',24,'0001','0020','电池电压'),('ZB11D1','ZB11D1E3ED',24,'0406','0000','占有状态'),('ZB11D','ZB11DE2ED',24,'0001','0020','电池电压'),('ZB11D','ZB11DE2ED',24,'0406','0000','占有状态'),('ZB11D','ZB11DE3ED',24,'0001','0020','电池电压'),('ZB11D','ZB11DE3ED',24,'0406','0000','占有状态'),('ZC06','ZC06E0R',96,'0006','0000','开关状态'),('ZC06','ZC06E0R',96,'0008','0000','level等级'),('ZC06','ZC06E3R',96,'0006','0000','开关状态'),('ZC06','ZC06E3R',96,'0008','0000','level等级'),('ZC07','ZC07E3R',96,'0006','0000','开关状态'),('ZC07','ZC07E3R',96,'0008','0000','level等级');
insert into deviceattributehistorydictionary(deviceName,modelId,pushCount,cluster_id,attribute_id,attributeName) values('ZC09','ZC09E3R',96,'0006','0000','开关状态'),('ZC09','ZC09E3R',96,'0008','0000','level等级'),('ZD01B','ZD01BE0R',96,'0006','0000','开关状态'),('ZD01B','ZD01BE0R',96,'0008','0000','level等级'),('ZD01B','ZD01BE2R',96,'0006','0000','开关状态'),('ZD01B','ZD01BE2R',96,'0008','0000','level等级'),('ZD01B','ZD01BE3R',96,'0006','0000','开关状态'),('ZD01B','ZD01BE3R',96,'0008','0000','level等级'),('ZD01C','ZD01CE3R',96,'0006','0000','开关状态'),('ZD01C','ZD01CE3R',96,'0008','0000','level等级'),('ZD01D','ZD01DE3R',96,'0006','0000','开关状态'),('ZD01D','ZD01DE3R',96,'0008','0000','level等级');

/*devicedictionary补充数据*/
insert into deviceattributehistorydictionary(deviceName,modelId,pushCount,cluster_id,attribute_id,attributeName) values('Z306D','Z306DE0ED',24,'0500','0002','心跳'),('Z306D','Z306DE3ED',24,'0500','0002','心跳'),('Z801TX','Z801TXE0ED',24,'0500','0002','心跳'),('Z801TX','Z801TXE3ED',24,'0500','0002','心跳'),('ZA10','ZA10E0R',96,'0006','0000','开关状态'),('ZA10','ZA10E3R',96,'0006','0000','开关状态');

/*deviceattrlib添加ZB02I、Z302J*/
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('345', 'ZB02I', '0402', '0500', '0002', 'Zone status', '1', '安防设备状态');
INSERT INTO `deviceattrlib` (`ID`, `ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('346', 'Z302J', '0402', '0500', '0002', 'Zone status', '1', '安防设备状态');

/*App首页*/
CREATE TABLE familyEpPage(
`id`  int(20) NOT NULL  AUTO_INCREMENT ,
`ep_id`  int(20) NULL ,
`IRType`  varchar(4) NULL DEFAULT -1 ,
`Order`  varchar(10) NULL ,
`houseid`  int(20) NULL ,
`IEEE`  varchar(20) NULL ,
PRIMARY KEY (`id`)
)
;

CREATE TABLE `familyModePage` (
`id`  int(20) NOT NULL AUTO_INCREMENT ,
`ModeID`  int(20) NULL ,
PRIMARY KEY (`id`)
)
;

CREATE TABLE smartEpPage(
`id`  int(20) NOT NULL  AUTO_INCREMENT ,
`ep_id`  int(20) NULL ,
`IRType`  varchar(4) NULL DEFAULT -1 ,
`Order`  varchar(10) NULL ,
`houseid`  int(20) NULL ,
`IEEE`  varchar(20) NULL ,
PRIMARY KEY (`id`)
)
;

CREATE TABLE `smartModePage` (
`id`  int(20) NOT NULL AUTO_INCREMENT ,
`ModeID`  int(20) NULL ,
PRIMARY KEY (`id`)
)
;

ALTER TABLE `familyeppage`
ADD COLUMN `ep`  varchar(20) ;

ALTER TABLE `smartepPage` 
ADD COLUMN `ep`  varchar(20);

ALTER TABLE `familyeppage`
MODIFY COLUMN `Order`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 0;

ALTER TABLE smartEpPage
MODIFY COLUMN `Order`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 0 ;

ALTER TABLE `familymodepage`
ADD COLUMN `houseId`  int(20) NULL ;

ALTER TABLE `smartmodepage`
ADD COLUMN `houseId`  int(20) NULL ;

ALTER TABLE `smarteppage`
CHANGE COLUMN `Order` `OrderType`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' ;

ALTER TABLE `familyeppage`
CHANGE COLUMN `Order` `OrderType`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' ;

ALTER TABLE `familyeppage`
ADD COLUMN `oldId`  int(20) NULL DEFAULT 0 ;

ALTER TABLE `familymodepage`
ADD COLUMN `oldId`  int(20) NULL DEFAULT 0 ;

ALTER TABLE `smarteppage`
ADD COLUMN `oldId`  int(20) NULL DEFAULT 0;

ALTER TABLE `smartmodepage`
ADD COLUMN `oldId`  int(20) NULL DEFAULT 0;

/*增加一个经销商、内部角色*/
insert into modelevel(id, level_name) values(4, '经销商及内部用户');

/*增加用户、houseIEEE、203用户的关联关系*/
create table modeSchcUser (
id bigint(20) NOT NULL AUTO_INCREMENT,
userId bigint(20),
houseIEEE varchar(20),
schcUserName varchar(30),
schcPassword varchar(30),
PRIMARY KEY (id)
)ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT;

update userordermain set userType=2 where houseName in ('奈伯思智能家商用版模板','奈伯思智能家家居版模板');

/*详情图片*/
CREATE TABLE `imgdetailtab` (
  `imgid` int(11) NOT NULL AUTO_INCREMENT,
  `modeid` varchar(255) DEFAULT NULL,
  `imgdetails` varchar(255) DEFAULT NULL,
  `modeNodeLibIds` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`imgid`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
INSERT INTO `imgdetailtab` VALUES ('1', 'Z203', 'images/Z203.jpg', '86');
INSERT INTO `imgdetailtab` VALUES ('2', 'Z206', 'images/Z206.jpg', '145');
INSERT INTO `imgdetailtab` VALUES ('3', 'Z211', 'images/Z211.jpg', '85');
INSERT INTO `imgdetailtab` VALUES ('4', 'Z308', 'images/Z308.jpg', '67');
INSERT INTO `imgdetailtab` VALUES ('6', 'Z311W', 'images/Z311W.jpg', '121');
INSERT INTO `imgdetailtab` VALUES ('7', 'Z312', 'images/Z312.jpg', '65');
INSERT INTO `imgdetailtab` VALUES ('8', 'Z602A', 'images/Z602A.jpg', '77');
INSERT INTO `imgdetailtab` VALUES ('9', 'Z716A', 'images/Z716A.jpg', '35');
INSERT INTO `imgdetailtab` VALUES ('10', 'ZB02C', 'images/ZB02C.jpg', '45');
INSERT INTO `imgdetailtab` VALUES ('11', 'ZB02I', 'images/ZB02I.jpg', '134');
INSERT INTO `imgdetailtab` VALUES ('12', 'ZB11B', 'images/ZB11B.jpg', '52');

/*ModemacrosubProc*/
DROP PROCEDURE IF EXISTS `ModemacrosubProc`;

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
		SELECT a.*,d.roomId, lib.UniqueName, lib.DataType,lib.UniqueNameEN,lib.ExtentionSet as actExtentionSet,
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
		and  ((type=0 and h.CIEStatus=0 and h.ZoneActType=0 ) OR
         (type=1 and h.CIEStatus=0 and h.ZoneActType=1 ) OR
         (type=2 and h.CIEStatus=1 and h.ZoneActType=0 ) OR
         (type=3 and h.CIEStatus=1 and h.ZoneActType=1 ));

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

/*ModemacrosubTest*/
DROP PROCEDURE IF EXISTS `ModemacrosubTest`;

CREATE DEFINER = `root`@`127.0.0.1` PROCEDURE `ModemacrosubTest`(IN `behavior` bigint,IN `Id` bigint,IN `houseId` bigint)
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
ELSEIF behavior=21 THEN
    SELECT m.*,r.roomName as fangjian,d.ID as aid,d.* from modedevice m 
		INNER JOIN modenodelib nodeL ON m.modelId = nodeL.modelId
		OR LOCATE(nodeL.modelId, m.modelId) = 1
		INNER JOIN modeeplib EPL ON EPL.nodeId = nodeL.id
		AND EPL.ep = m.ep
		INNER JOIN moderoom r ON m.houseId = r.houseId 
		INNER JOIN deviceattrlib d on d.ModelID=nodeL.modelId and d.DeviceID=EPL.deviceId
		WHERE m.roomId = r.roomId and m.houseId = houseId  GROUP BY m.id;
ELSEIF behavior=211 THEN
		SELECT	m.*, r.roomName AS fangjian,d.ID AS aid,	d.* FROM	modedevice m INNER JOIN moderoom r ON m.houseId = r.houseId INNER JOIN modeactlib d ON m.deviceId = d.DeviceID WHERE m.roomId = r.roomId AND m.houseId = houseId GROUP BY m.id;
	  
	
ELSEIF behavior=41 THEN
  Select Count(*) into aCount FROM modeactlib t WHERE t.ID=ID 
          and (t.ActName='Disarm' or t.ActName='ArmAllZone' or t.ActName='ArmDayZone'
               or t.ActName='ArmNightZone');
  if aCount=0 then    
    Select r.roomName,D.id,D.roomId,D.deviceName,attr.*,attr.id as aid from modedevice d 
    inner join modenodelib nodeL on d.modelId=nodeL.modelId or LOCATE(nodeL.modelId,d.modelId)=1		
		inner join modeeplib EPL on EPL.nodeId=nodeL.id and EPL.ep=d.ep
	  inner join moderoom r on d.roomId=r.roomId and d.houseId=r.houseId
		left join deviceattrlib attr on attr.ModelID=nodeL.modelId and attr.DeviceID=EPL.deviceId
		where d.id=id and r.houseId=houseId ORDER BY d.roomId;
  else
    select * from (Select r.roomName,D.id,D.roomId,D.deviceName,attr.*,attr.id as aid from modedevice d 
    inner join modenodelib nodeL on d.modelId=nodeL.modelId or LOCATE(nodeL.modelId,d.modelId)=1		
		inner join modeeplib EPL on EPL.nodeId=nodeL.id and EPL.ep=d.ep
		inner join moderoom r on d.roomId=r.roomId and d.houseId=r.houseId
		LEFT  join deviceattrlib attr on attr.ModelID=nodeL.modelId and attr.DeviceID=EPL.deviceId
		inner join deviceattrlib attr1 on attr.ClusterID=attr1.ClusterID and Attr.AttrID=attr1.AttrID		
		where d.id=id and r.houseId=houseId 
    UNION
    Select r.roomName,D.id,D.roomId,D.deviceName FROM modedevice d     
    inner join modenodelib nodeL on d.modelId=nodeL.modelId or LOCATE(nodeL.modelId,d.modelId)=1
		inner join modeeplib EPL on EPL.nodeId=nodeL.id and d.deviceId=EPL.deviceId and d.ep=epl.ep  
		inner join moderoom r on d.roomId=r.roomId and d.houseId=r.houseId
		where d.houseId=HouseID and (nodeL.ID=86 or nodeL.ID=145)) Main order by Main.RoomID;

  end if;

ELSEIF behavior = 100 THEN
	/*SELECT a.DataType,a.ActName,a.UniqueName,a.UniqueNameEN,a.ID from modedevice m,modeactlib a WHERE m.deviceId = a.deviceId and m.id=houseId and m.deviceId = Id;*/
SELECT m.* from modeactlib m INNER JOIN modedevice d on m.DeviceID=d.deviceId  WHERE d.id=Id;



END IF;
END;

/*设备详情图片*/
SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `imgdetailtab`;
CREATE TABLE `imgdetailtab` (
  `imgid` int(11) NOT NULL AUTO_INCREMENT,
  `modeid` varchar(255) DEFAULT NULL,
  `imgdetails` varchar(255) DEFAULT NULL,
  `modeNodeLibIds` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`imgid`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
INSERT INTO `imgdetailtab` VALUES ('1', 'Z203', 'images/Z203_d.jpg', '86');
INSERT INTO `imgdetailtab` VALUES ('2', 'Z206', 'images/Z206_d.jpg', '145');
INSERT INTO `imgdetailtab` VALUES ('3', 'Z211', 'images/Z211_d.jpg', '85');
INSERT INTO `imgdetailtab` VALUES ('4', 'Z308', 'images/Z308_d.jpg', '67');
INSERT INTO `imgdetailtab` VALUES ('6', 'Z311W', 'images/Z311W_d.jpg', '121');
INSERT INTO `imgdetailtab` VALUES ('7', 'Z312', 'images/Z312_d.jpg', '65');
INSERT INTO `imgdetailtab` VALUES ('8', 'Z602A', 'images/Z602A_d.jpg', '77');
INSERT INTO `imgdetailtab` VALUES ('9', 'Z716A', 'images/Z716A_d.jpg', '35');
INSERT INTO `imgdetailtab` VALUES ('10', 'ZB02C', 'images/ZB02C_d.jpg', '45');
INSERT INTO `imgdetailtab` VALUES ('11', 'ZB02I', 'images/ZB02I_d.jpg', '134');
INSERT INTO `imgdetailtab` VALUES ('12', 'ZB11B', 'images/ZB11B_d.jpg', '52');

/*删除房间之后*/
DROP PROCEDURE IF EXISTS ModedeleteTest;
CREATE PROCEDURE ModedeleteTest(IN behavior bigint,IN houseId bigint,IN id bigint)
BEGIN
CASE 
WHEN behavior=1 THEN
		delete t from usermodemain t where t.houseId=houseId and t.id =id;
		delete t from usermodesub t where t.houseId=houseId and t.mid =id;
WHEN behavior=2 THEN
		delete t from modemacrosub t where t.houseId=houseId and t.mid =id;
		delete t from modemacromain t where t.houseId=houseId and t.id =id;
		delete u,m from modeschemesub u inner join modeschememain m on m.id=u.mid 
					where u.dest=id and act='ApplyMacro';
WHEN behavior=3 THEN
		delete u from modegroupsub u where u.houseId=houseId and u.mid=id;
WHEN behavior=4 THEN
    delete m from modegroupmain m where m.houseId=houseId and m.id=id;
		delete u from modegroupsub u where u.houseId=houseId and u.mid=id;
		delete t from modemacrosub t where t.houseId=houseId and t.Dest=id and t.DestType=1;
WHEN behavior=5 THEN
		delete u from modescenesub u where u.houseId=houseId and u.mid=id;
WHEN behavior=6 THEN	
		delete m from modescenemain m where m.houseId=houseId and m.id=id;
		delete u from modescenesub u where u.houseId=houseId and u.mid=id;
		delete t from modemacrosub t where t.houseId=houseId and t.Dest=id and t.DestType=3;

WHEN behavior=7 THEN	
		delete t from modeiassub t where t.houseId=houseId and t.mid =id;
		delete t from modeiasmain t where t.houseId=houseId and t.id =id;
		delete u,m from modeschemesub u inner join modeschememain m on m.id=u.mid 
					where u.dest =id and act='ActiveIAS';
WHEN behavior=8 THEN	
		delete t from modehvacsub t where t.houseId=houseId and t.mid =id;
		delete t from modehvacmain t where t.houseId=houseId and t.id =id;
		delete u,m from modeschemesub u inner join modeschememain m on m.id=u.mid 
					where u.dest=id and act='ActiveHVAC';
/* 删除设备id */
WHEN behavior=20 THEN	
		delete u from modegroupsub u where u.DeviceID=id;
		delete u from modescenesub u where u.DeviceID=id;
		delete t from modemacrosub t where t.Dest=id and t.DestType=0;		
/* 删除节点id */
WHEN behavior=21 THEN
		delete u from modegroupsub u inner join modedevice d on u.DeviceID=d.id
							where d.modeNodeId=id;
		delete u from modescenesub u inner join modedevice d on u.DeviceID=d.id
							where d.modeNodeId=id;
		delete t from modemacrosub t inner join modedevice d on t.dest=d.id
							where d.modeNodeId=id and t.DestType=0;
	  delete u from modedevicebind u inner join modedevice d on u.SourceID=d.id
              join Modenode f on d.modeNodeId = f.id 
							where f.id = id;							
		delete u from modedevicebind u inner join modedevice d on u.DestID=d.id
							join Modenode f on d.modeNodeId = f.id 
							where f.id = id;		
		delete t from Modedevice t where t.modeNodeId = id;		
/* 删除房间id */
WHEN behavior=22 THEN			
		delete u from modegroupsub u inner join modedevice d on u.DeviceID=d.id
							where d.houseId = houseId and d.roomId = id;
		delete u from modescenesub u inner join modedevice d on u.DeviceID=d.id
							where d.houseId = houseId and d.roomId = id;
		delete t from modemacrosub t inner join modedevice d on t.dest=d.id
							where d.houseId = houseId and d.roomId = id and t.DestType=0;		
		delete u from modedevicebind u inner join modedevice d on u.SourceID=d.id
							where d.houseId = houseId and d.roomId = id ;							
		delete u from modedevicebind u inner join modedevice d on u.DestID=d.id
							where d.houseId = houseId and d.roomId = id ;							
		/*delete t from Modedevice t where t.houseId = houseId and t.roomId = id;		
		delete t from Modenode t where t.houseId = houseId and t.roomId = id;*/
		update modedevice set roomId = -1 where houseId = houseId and roomId = id;
	  update modenode set roomId = -1 where houseId = houseId and roomId = id;
		delete p.* from usermodemain t,usermodesub p where t.houseId = houseId and t.roomId = id
									and t.id =p.mid;
		delete t.* from usermodemain t where t.houseId = houseId and t.roomId = id;
		delete t from Moderoom t where t.houseId = houseId and t.roomId = id;
END CASE;
END;
/*usermodedevice新增索引*/
ALTER TABLE `usermodedevice`
ADD INDEX `Dest` (`Dest`) USING BTREE ,
ADD INDEX `MID` (`MID`, `HouseID`) USING BTREE ;

/*调换标准模板家具版和商业版的顺序*/
update userordermain set id = 1332 where id = 1330;
update userordermain set id = 1330 where id = 1331;


/*字典表新增ep设备*/
INSERT INTO `modeeplib` (`id`, `nodeId`, `deviceId`, `internelModelId`, `deviceName`, `deviceNameEn`, `clusterId`, `clusterName`, `clusterNameEn`, `picName`, `destType`, `deviceType`, `deviceTypeV2`, `ep`, `description`, `descriptionEn`, `Groupable`) VALUES ('423', '223', '0820', 'ZB02G-1', 'ZB02G壁挂无线按键调光开关', 'ZB02G Wall Dimmer', '0006:0008', '开关:调级控制', 'Switch:level control', NULL, '3', 'source', '1', '01', NULL, NULL, '0');
INSERT INTO `modeeplib` (`id`, `nodeId`, `deviceId`, `internelModelId`, `deviceName`, `deviceNameEn`, `clusterId`, `clusterName`, `clusterNameEn`, `picName`, `destType`, `deviceType`, `deviceTypeV2`, `ep`, `description`, `descriptionEn`, `Groupable`) VALUES ('424', '224', '0008', 'Z725C-1', 'Z725C土壤温湿度探测器', 'Z725C Temperature and Humidity Sensor', NULL, NULL, NULL, NULL, NULL, NULL, '1', '0A', NULL, NULL, '0');
INSERT INTO `modeeplib` (`id`, `nodeId`, `deviceId`, `internelModelId`, `deviceName`, `deviceNameEn`, `clusterId`, `clusterName`, `clusterNameEn`, `picName`, `destType`, `deviceType`, `deviceTypeV2`, `ep`, `description`, `descriptionEn`, `Groupable`) VALUES ('425', '225', '0009', 'Z816F-1', 'Z816F欧规电能检测墙面插座', 'Z816F Europe type Power Plug', '0006', '开关', 'Switch', NULL, '3', 'dest', '1', '01', NULL, NULL, '1');
INSERT INTO `modeeplib` (`id`, `nodeId`, `deviceId`, `internelModelId`, `deviceName`, `deviceNameEn`, `clusterId`, `clusterName`, `clusterNameEn`, `picName`, `destType`, `deviceType`, `deviceTypeV2`, `ep`, `description`, `descriptionEn`, `Groupable`) VALUES ('426', '226', '0009', 'Z815G-1', 'Z815G一路墙面电能检测开关', 'Z815G 1st Power Switch', '0006', '开关', 'Switch', NULL, '3', 'dest', '1', '01', NULL, NULL, '1');
INSERT INTO `modeeplib` (`id`, `nodeId`, `deviceId`, `internelModelId`, `deviceName`, `deviceNameEn`, `clusterId`, `clusterName`, `clusterNameEn`, `picName`, `destType`, `deviceType`, `deviceTypeV2`, `ep`, `description`, `descriptionEn`, `Groupable`) VALUES ('427', '226', '0009', 'Z815G-2', 'Z815G二路墙面电能检测开关', 'Z815G 2nd Power Switch', '0006', '开关', 'Switch', NULL, '3', 'dest', '1', '02', NULL, NULL, '1');
INSERT INTO `modeeplib` (`id`, `nodeId`, `deviceId`, `internelModelId`, `deviceName`, `deviceNameEn`, `clusterId`, `clusterName`, `clusterNameEn`, `picName`, `destType`, `deviceType`, `deviceTypeV2`, `ep`, `description`, `descriptionEn`, `Groupable`) VALUES ('428', '227', '0101', 'Z815H-1', 'Z815H调光电能检测开关', 'Z815H Dimmer Switch', '0006:0008', '开关:调级控制', 'Switch:level control', NULL, '3', 'dest', '1', '01', NULL, NULL, '1');
INSERT INTO `modeeplib` (`id`, `nodeId`, `deviceId`, `internelModelId`, `deviceName`, `deviceNameEn`, `clusterId`, `clusterName`, `clusterNameEn`, `picName`, `destType`, `deviceType`, `deviceTypeV2`, `ep`, `description`, `descriptionEn`, `Groupable`) VALUES ('429', '228', '0002', 'Z801RX-1', 'Z801RX一路无线继电器', 'Z801RX 1st output', '0006', '开关', 'Switch', NULL, '3', 'dest', '1', '01', NULL, NULL, '1');
INSERT INTO `modeeplib` (`id`, `nodeId`, `deviceId`, `internelModelId`, `deviceName`, `deviceNameEn`, `clusterId`, `clusterName`, `clusterNameEn`, `picName`, `destType`, `deviceType`, `deviceTypeV2`, `ep`, `description`, `descriptionEn`, `Groupable`) VALUES ('430', '228', '0002', 'Z801RX-2', 'Z801RX二路无线继电器', 'Z801RX 2nd output', '0006', '开关', 'Switch', NULL, '3', 'dest', '1', '02', NULL, NULL, '1');
INSERT INTO `modeeplib` (`id`, `nodeId`, `deviceId`, `internelModelId`, `deviceName`, `deviceNameEn`, `clusterId`, `clusterName`, `clusterNameEn`, `picName`, `destType`, `deviceType`, `deviceTypeV2`, `ep`, `description`, `descriptionEn`, `Groupable`) VALUES ('431', '229', '0000', 'Z802-1', 'Z802一路开关', 'Z802 1st output', '0006', '开关', 'Switch', NULL, '3', 'source', '1', '01', NULL, NULL, '0');
INSERT INTO `modeeplib` (`id`, `nodeId`, `deviceId`, `internelModelId`, `deviceName`, `deviceNameEn`, `clusterId`, `clusterName`, `clusterNameEn`, `picName`, `destType`, `deviceType`, `deviceTypeV2`, `ep`, `description`, `descriptionEn`, `Groupable`) VALUES ('432', '229', '0000', 'Z802-2', 'Z802二路开关', 'Z802 2nd output', '0006', '开关', 'Switch', NULL, '3', 'source', '1', '02', NULL, NULL, '0');
INSERT INTO `modeeplib` (`id`, `nodeId`, `deviceId`, `internelModelId`, `deviceName`, `deviceNameEn`, `clusterId`, `clusterName`, `clusterNameEn`, `picName`, `destType`, `deviceType`, `deviceTypeV2`, `ep`, `description`, `descriptionEn`, `Groupable`) VALUES ('433', '230', '0007', 'Z207', 'Z207云智能网关', 'Z207 CWSHC', NULL, NULL, NULL, NULL, NULL, NULL, '1', '0A', NULL, NULL, '0');
INSERT INTO `modeeplib` (`id`, `nodeId`, `deviceId`, `internelModelId`, `deviceName`, `deviceNameEn`, `clusterId`, `clusterName`, `clusterNameEn`, `picName`, `destType`, `deviceType`, `deviceTypeV2`, `ep`, `description`, `descriptionEn`, `Groupable`) VALUES ('434', '231', '0102', 'ZL01G-1', 'ZL01G 可调光/RGB控制盒', 'ZL01G Dimmer/RGB Controller', '0006:0008', '开关:调级控制', 'Switch:level control', NULL, '3', 'dest', '1', '01', NULL, NULL, '1');

/*字典表新增node*/
INSERT INTO `modenodelib` (`id`, `nodeType`, `modelId`, `deviceName`, `deviceNameEn`, `clusterId`, `clusterName`, `clusterNameEn`, `destType`, `source_id`, `deviceType`, `picName`, `description`, `descriptionEn`, `powerType`, `powerTypeEn`, `activationMethod`, `activationMethodEn`, `resetDefault`, `resetDefaultEn`, `remark`, `remarkEn`) VALUES ('223', '0', 'ZB02G', 'ZB02G壁挂无线按键调光开关', 'ZB02G Wall Dimmer', '0006:0008', '开关:调级控制', '开关:调级控制', '3', '1', 'source', 'ZB02G.jpg', 'ZB02G是壁挂无线按键调光开关。在网络中作为终端设备（End device）使用,不允许其他设备做为其子设备。ZB02G可与具有开关可控或者可调功能的设备控制其开关。', 'ZB02G is a wireless dimmer switch. It is used as a end device in the zigBee network,which doesn\'t allow other devices to be its child device.It can be bound with a device which has switch and level control function to control its on/off and dimming wirelessly. ', 'AAA电池供电 ', '无', '需要激活', '无', '按住绑定键的同时给设备上电，进行恢复出厂设置，看到网络指示灯（绿灯）闪烁，表示恢复完成。', '无', '无', '无');
INSERT INTO `modenodelib` (`id`, `nodeType`, `modelId`, `deviceName`, `deviceNameEn`, `clusterId`, `clusterName`, `clusterNameEn`, `destType`, `source_id`, `deviceType`, `picName`, `description`, `descriptionEn`, `powerType`, `powerTypeEn`, `activationMethod`, `activationMethodEn`, `resetDefault`, `resetDefaultEn`, `remark`, `remarkEn`) VALUES ('224', '0', 'Z725C', 'Z725C土壤湿度探测器', 'Z725C Humidity Sensor', NULL, NULL, NULL, NULL, '1', NULL, 'Z725C.jpg', 'Z725C是湿度探测器。可用于采集周围环境的湿度,将采集到的数据发送给显示设备。', 'Z725C is a humidity detector. It can collect the humidity data of the ambient environment and send the data to the displaying device.', '太阳能充电电池供电', '无', '不需要激活', '无', 'Z725C具有保存数据功能，包括保存其所分配的网络地址，绑定信息等，若要其加入一个新的网络，需要先执行恢复出厂设置的操作。设备上电后，同时按住绑定键和辅助键5秒，状态灯闪烁1次后，开始恢复出厂值，状态灯快闪20次，表示 恢复出厂值完成，设备自动复位寻找加网。', '无', '无', '无');
INSERT INTO `modenodelib` (`id`, `nodeType`, `modelId`, `deviceName`, `deviceNameEn`, `clusterId`, `clusterName`, `clusterNameEn`, `destType`, `source_id`, `deviceType`, `picName`, `description`, `descriptionEn`, `powerType`, `powerTypeEn`, `activationMethod`, `activationMethodEn`, `resetDefault`, `resetDefaultEn`, `remark`, `remarkEn`) VALUES ('225', '0', 'Z816F', 'Z816F欧规电能检测墙面插座', 'Z816F Europe-type Power Plug', '0006', '开关', '开关', '3', '1', 'dest', 'Z816F.jpg', 'Z816F是欧规电能检测墙面插座。可与具有开关功能的设备绑定，通过已绑定设备来控制Z816F外接负载的开和关，也可通过Z816F设备本身自带的开关来控制外接负载。用户可以使用ZiG-BUTLER软件查看当前负载的电流、电压、功率和电能值。', 'Z816F is a Europe-type wireless smart wall socket that allows off site remote control. Users can manually switch on/off the socket or by paired on/off devices or by software. Via software, users are able to monitor the load current, voltage and power and kilo-watt-hour consumption of the attached appliance.', '100-240VAC 50/60HZ电源供电', '无', '不需要激活', '无', '按住绑定键15秒左右网络指示灯闪烁一次，然后在2秒内短按开关键一次，开始执行恢复出厂值的操作，指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，Z816F可重新加网。', '无', '无', '无');
INSERT INTO `modenodelib` (`id`, `nodeType`, `modelId`, `deviceName`, `deviceNameEn`, `clusterId`, `clusterName`, `clusterNameEn`, `destType`, `source_id`, `deviceType`, `picName`, `description`, `descriptionEn`, `powerType`, `powerTypeEn`, `activationMethod`, `activationMethodEn`, `resetDefault`, `resetDefaultEn`, `remark`, `remarkEn`) VALUES ('226', '0', 'Z815G', 'Z815G墙面电能检测开关', 'Z815G Power Switch', '0006', '开关', '开关', '3', '1', 'dest', 'Z815G.jpg', 'Z815G是墙面电能检测开关。可与具有开关功能的设备绑定，通过已绑定设备来控制Z815G开关，也可通过Z815G设备本身自带的开关来控制，Z815G具有两路控制开关,由两个开关控制。用户可以使用ZiG-BUTLER软件查看当前负载的电流、电压、功率和电能值。', 'Z815G is a wall switch with power meter. It can be bound with on/off devices for users to control the on/off of Z815G with the bound device. Z815G can also be controled by its own two-gang switches.Via software, users are able to monitor the load current, voltage and power and kilo-watt-hour consumption of the attached appliance.', '100-240VAC 50/60HZ电源供电 ', '无', '不需要激活', '无', '按住绑定键5秒，网络指示灯闪烁一次，开始执行恢复出厂值的操作，网络指示灯闪烁之后熄灭表示恢复完成，恢复完成后设备会自动复位，Z815G可重新加网。', '无', '无', '无');
INSERT INTO `modenodelib` (`id`, `nodeType`, `modelId`, `deviceName`, `deviceNameEn`, `clusterId`, `clusterName`, `clusterNameEn`, `destType`, `source_id`, `deviceType`, `picName`, `description`, `descriptionEn`, `powerType`, `powerTypeEn`, `activationMethod`, `activationMethodEn`, `resetDefault`, `resetDefaultEn`, `remark`, `remarkEn`) VALUES ('227', '0', 'Z815H', 'Z815H调光电能检测开关', 'Z815H Dimmer Switch with Power Meter', '0006:0008', '开关:调级控制', '开关:调级控制', '3', '1', 'dest', 'Z815H.jpg', 'Z815H是调光电能检测开关。可与具有开关功能/级别控制功能的控制设备绑定,通过控制设备控制Z815H的开关/级别,同时Z815H能够检测负载的电流，电压，功率以及电能。', 'Z815H is a dimmer switch with power meter. It can be bound with on/off or dimmer devices for users to control the on/off and level of Z815H with the bound device. It is equipped with load consumption inspection and load control feature.', 'AC交流供电 ', '无', '不需要激活', '无', '长按绑定键5s，5s时间到后状态指示灯闪1次后就恢复出厂设置成功，此时就可以重新加网。', '无', '无', '无');
INSERT INTO `modenodelib` (`id`, `nodeType`, `modelId`, `deviceName`, `deviceNameEn`, `clusterId`, `clusterName`, `clusterNameEn`, `destType`, `source_id`, `deviceType`, `picName`, `description`, `descriptionEn`, `powerType`, `powerTypeEn`, `activationMethod`, `activationMethodEn`, `resetDefault`, `resetDefaultEn`, `remark`, `remarkEn`) VALUES ('228', '0', 'Z801RX', 'Z801RX无线继电器', 'Z801RX output', '0006', '开关', '开关', '3', '1', 'dest', 'Z801RX.jpg', 'Z801RX是无线继电器。在网络中作为路由设备（Router device）使用,允许其他设备做为其子设备，且具有开关控制功能的设备可与Z801RX绑定控制其继电器的通断。', 'Z801RX is a wireless relay used as a router device in the zigBee network,which allows other devices to be its child device. Devices with on/off control functions can be paired with Z801RX to trigger on/off of the relay.', '100～240V的交流电源', '无', '不需要激活', '无', '按住绑定键的同时给设备上电；复位完成则状态指示灯快速灯闪烁；重新上电，Z801RX可以开始重新加网了。', '无', '无', '无');
INSERT INTO `modenodelib` (`id`, `nodeType`, `modelId`, `deviceName`, `deviceNameEn`, `clusterId`, `clusterName`, `clusterNameEn`, `destType`, `source_id`, `deviceType`, `picName`, `description`, `descriptionEn`, `powerType`, `powerTypeEn`, `activationMethod`, `activationMethodEn`, `resetDefault`, `resetDefaultEn`, `remark`, `remarkEn`) VALUES ('229', '0', 'Z802', 'Z802 开关', 'Z802 output', '0006', '开关', '开关', '3', '1', 'source', 'Z802.jpg', 'Z802是一款专用于楼梯灯、车库灯、路灯等应用控制的RF开关控制模组,由两个内部受控开关及两个外接机械开关构成两路灯的开关控制. 可通过机械开关来控制负载的通断，也可通过无线方式控制负载的通断。同时可通过上位机软件（Zigbutler）查看设备两路的当前通断状态，并可做出相应的控制。', 'Z802 is a RF on/off control module dedicated for staircase lights, garage lights, street lamps and other application controls. It controls on/off of two-gang lights with two internally controled switches and two external mechanical switches.The on/off of its attached appliance can be controlled through the mechanical switches or wirelessly. Users can also view the current status of two gangs of the device via ZigButler and make corresponding control.', '90-264VAC 50/60HZ电源供电 ', '无', '不需要激活', '无', '长按绑定键15S以上，15S内状态指示灯闪烁3次(其中3S闪烁1次，10S闪烁1次,15S闪烁1次)，15S时间到松开绑定键后2S内再短按一次绑定键状态指示灯快闪,表示恢复出厂设置成功。', '无', '无', '无');
INSERT INTO `modenodelib` (`id`, `nodeType`, `modelId`, `deviceName`, `deviceNameEn`, `clusterId`, `clusterName`, `clusterNameEn`, `destType`, `source_id`, `deviceType`, `picName`, `description`, `descriptionEn`, `powerType`, `powerTypeEn`, `activationMethod`, `activationMethodEn`, `resetDefault`, `resetDefaultEn`, `remark`, `remarkEn`) VALUES ('230', '0', 'Z207', 'Z207云智能网关', 'Z207 CWSHC', NULL, NULL, NULL, NULL, '1', NULL, 'Z207.jpg', 'Z207作为奈伯思智能家居系统的核心网关，率先实现云端技术与ZigBee智能家居物联网术的完美结合，让用户只需要通过手机APP连接Wifi即可控制家里的灯光、窗帘、各种电器等设备的开关，出门在外通过2G/3G网络可监控家中的一切变化。', 'As the main controller of Netvox Cloud-based Wireless Smart Home System, Z-207 is a perfect combination of cloud services and ZigBee Home Automation solutions. Z-207 enables users to monitor their lights, curtains, and home appliances via smart home app and Wifi connection, and users can easily monitor their home with 2G/3G network even when they are far away.', 'DC5V 1A电源适配器，接入100-240V的电源', '无', '不需要激活', '无', '登录设备配置后台：                                                          设备部分：系统管理→设置管理→恢复出厂设置\r\n系统部分：智能家居→备份还原数据→还原数据→恢复出厂设置', '无', '无', '无');
INSERT INTO `modenodelib` (`id`, `nodeType`, `modelId`, `deviceName`, `deviceNameEn`, `clusterId`, `clusterName`, `clusterNameEn`, `destType`, `source_id`, `deviceType`, `picName`, `description`, `descriptionEn`, `powerType`, `powerTypeEn`, `activationMethod`, `activationMethodEn`, `resetDefault`, `resetDefaultEn`, `remark`, `remarkEn`) VALUES ('231', '0', 'ZL01G', 'ZL01G 可调光/RGB控制盒', 'ZL01G Dimmer/RGB Controller', '0006:0008', '开关:调级控制', '开关:调级控制', '3', '1', 'dest', 'ZL01G.jpg', 'ZL01G 可调光/RGB控制盒是一款在ZigBee 协议基础上开发的用于调光以及RGB 控制的产品。配合RGB 灯及灯带使用，通过ZigBee 无线网络，可用手机、平板轻松控制灯光的颜色调节。本产品可应用于家庭，办公和酒店等场合。 ', 'ZL01G color dimmer / RGB controller is developed based on ZigBee protocol for dimming and RGB controlling. Matched with RGB LED lamps and strips, ZL01G can be used for color dimming in a ZigBee wireless network via mobile phone and tablet. It can be used at home, offices, hotels and other locations.', '12-24V直流电源', '无', '不需要激活', '无', '按住绑定键15秒，放开绑定键，在2秒内按一次测试键，设备开始恢复出厂设置，网络灯快速闪烁十次后自动重启设备，开始寻找新的网络。', '无', '无', '无');
/*新增设备详情图片*/
INSERT INTO `imgdetailtab` VALUES ('13', 'Z809A', 'images/Z809A_d.jpg', '27');
INSERT INTO `imgdetailtab` VALUES ('14', 'ZC07', 'images/ZC07_d.jpg', '120');
INSERT INTO `imgdetailtab` VALUES ('15', 'Z311J', 'images/Z311J_d.jpg', '119');
INSERT INTO `imgdetailtab` VALUES ('16', 'ZB11C', 'images/ZB11C_d.jpg', '53');

/*执行所有模式时生成生成多条执行条件*/
DROP PROCEDURE IF EXISTS `Modescheme`;
CREATE PROCEDURE `Modescheme`(
	IN `behavior` BIGINT,
	IN `houseId` BIGINT,
	IN `dest` BIGINT,
	IN `id` BIGINT
)
BEGIN
	#Routine body goes here...
	DECLARE
		acount BIGINT;


IF behavior = 1 THEN
	SELECT
		u.*
	FROM
		modeschemesub u
	WHERE
		u.id = id;


ELSEIF behavior = 2 THEN
	SELECT
		m.*
	FROM
		modeschememain m,
		modeschemesub u
	WHERE
		m.id = u.mid
	AND u.id = id;


ELSEIF behavior = 3 THEN
	SELECT
		um.*
	FROM
		usermodesub um,
		modeschememain m,
		modeschemesub u
	WHERE
		m.id = u.mid
	AND um.Dest = u.mid
	AND u.id = id
	AND (
		um.Act = 'DeactiveScheme'
		OR um.Act = 'ActiveScheme'
		OR um.Act = 'IgnoreScheme'
	);


ELSEIF behavior = 4 THEN
	DELETE d
FROM
	usermodesub d
WHERE
	d.Dest IN (
		SELECT
			u.ID
		FROM
			modeschememain u
		WHERE
			d.Act = 'ActiveScheme'
		AND u.HouseID = HouseID
		AND u.bAllmodeActive = 1
	);

INSERT INTO usermodesub (
	MID,
	HouseID,
	Act,
	Dest,
	Selectss
) SELECT
	m.ID,
	m.HouseID,
	'ActiveScheme',
	u.id,
	1
FROM
	usermodemain m,
	modeschememain u
WHERE
	m.HouseID = u.HouseID
AND u.bAllmodeActive = 1
AND u.HouseID = HouseID;


ELSEIF behavior = 5 THEN
	DELETE d
FROM
	usermodesub d
WHERE
	d.Dest IN (
		SELECT
			u.ID
		FROM
			modeschememain u
		WHERE
			d.Act = 'ActiveIAS'
		AND u.HouseID = HouseID
		AND u.bAllmodeActive = 1
	);

INSERT INTO usermodesub (
	MID,
	HouseID,
	Act,
	Dest,
	Selectss
) SELECT
	m.ID,
	m.HouseID,
	'ActiveIAS',
	u.id,
	1
FROM
	usermodemain m,
	modeiasmain u
WHERE
	m.HouseID = u.HouseID
AND u.bAllmodeActive = 1
AND u.HouseID = HouseID;


ELSEIF behavior = 6 THEN
	DELETE d
FROM
	usermodesub d
WHERE
	d.Dest IN (
		SELECT
			u.ID
		FROM
			modeschememain u
		WHERE
			d.Act = 'ActiveHVAC'
		AND u.HouseID = HouseID
		AND u.bAllmodeActive = 1
	);

INSERT INTO usermodesub (
	MID,
	HouseID,
	Act,
	Dest,
	Selectss
) SELECT
	m.ID,
	m.HouseID,
	'ActiveHVAC',
	um.id,
	1
FROM
	usermodemain m,
	modehvacmain u,
	modemainclause um
WHERE
	m.HouseID = u.HouseID
AND um.id = u.mainid
AND u.bAllmodeActive = 1
AND u.HouseID = HouseID;


ELSEIF behavior = 7 THEN
	SELECT
		t.*
	FROM
		modeactlib t
	ORDER BY
		t.id
	LIMIT id,
	houseId;


ELSEIF behavior = 8 THEN
	SELECT
		t.*
	FROM
		modeactlib t
	WHERE
		t.id = id;

#模式计数
ELSEIF behavior = 9 THEN
	#select Count(*) into aCount From usermodemain m Where m.houseid=houseId;
	SELECT
		m1.oldId + 1 INTO aCount
	FROM
		usermodemain m1
	WHERE
		m1.houseid = houseId
	AND m1.oldId + 1 NOT IN (
		SELECT
			m2.oldId
		FROM
			usermodemain m2
		WHERE
			m2.houseid = houseId
	)
	ORDER BY
		oldId ASC
	LIMIT 0,
	1;


IF (aCount IS NULL) THEN
	SELECT
		1 AS aCount;


ELSE
	SELECT
		aCount;


END
IF;


END
IF;


END

/*modeactlib表新增对deviceid为0102的调级动作支持*/
INSERT INTO `modeactlib` (`ActName`, `ExtentionSet`, `ScenePara`, `UniqueName`, `UniqueNameEN`, `DataType`, `DeviceID`, `Groupabled`, `GroupLevel`, `Sceneabled`) VALUES ('MoveToLevelWithOnOff', '100', '06000101080001FF', '调级100%', 'MoveToLevel 100%', '2', '0102', '1', '1', '1');
INSERT INTO `modeactlib` (`ActName`, `ExtentionSet`, `ScenePara`, `UniqueName`, `UniqueNameEN`, `DataType`, `DeviceID`, `Groupabled`, `GroupLevel`, `Sceneabled`) VALUES ('MoveToLevelWithOnOff', '80', '06000101080001CC', '调级80%', 'MoveToLevel 80&', '2', '0102', '1', '1', '1');
INSERT INTO `modeactlib` (`ActName`, `ExtentionSet`, `ScenePara`, `UniqueName`, `UniqueNameEN`, `DataType`, `DeviceID`, `Groupabled`, `GroupLevel`, `Sceneabled`) VALUES ('MoveToLevelWithOnOff', '60', '0600010108000199', '调级60%', 'MoveToLevel 60%', '2', '0102', '1', '1', '1');
INSERT INTO `modeactlib` (`ActName`, `ExtentionSet`, `ScenePara`, `UniqueName`, `UniqueNameEN`, `DataType`, `DeviceID`, `Groupabled`, `GroupLevel`, `Sceneabled`) VALUES ('MoveToLevelWithOnOff', '40', '0600010108000166', '调级40%', 'MoveToLevel 40%', '2', '0102', '1', '1', '1');
INSERT INTO `modeactlib` (`ActName`, `ExtentionSet`, `ScenePara`, `UniqueName`, `UniqueNameEN`, `DataType`, `DeviceID`, `Groupabled`, `GroupLevel`, `Sceneabled`) VALUES ('MoveToLevelWithOnOff', '20', '0600010108000133', '调级20%', 'MoveToLevel 20%', '2', '0102', '1', '1', '1');
INSERT INTO `modeactlib` (`ActName`, `ExtentionSet`, `ScenePara`, `UniqueName`, `UniqueNameEN`, `DataType`, `DeviceID`, `Groupabled`, `GroupLevel`, `Sceneabled`) VALUES ('MoveToLevelWithOnOff', '0', '0600010008000100', '调级0%', 'MoveToLevel 0%', '2', '0102', '1', '1', '1');
UPDATE `modeactlib` SET `DataType`='6',ActName='MoveToColor',UniqueName='设置颜色',UniqueNameEN='MoveToColor' WHERE (`ActName`='LightColor');

/*电能计算，精度更小*/
CREATE TABLE `EnergyDeviceAttribute` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `houseIEEE` varchar(50) DEFAULT NULL,  
  `daemonDeviceId` bigint(20) DEFAULT '0',
  `source_id` bigint(20) DEFAULT '1',
  `deviceType` bigint(20) DEFAULT '1',
  `device_ieee` varchar(50) DEFAULT NULL,
  `device_ep` varchar(50) DEFAULT NULL,
  `id_string3` varchar(50) DEFAULT NULL,
  `cluster_id` varchar(50) DEFAULT NULL,
  `attribute_id` varchar(50) DEFAULT NULL,  
  `value` varchar(50) DEFAULT NULL,
  `lasttime` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `Idx_Energydeviceattribute_1` (`houseIEEE`,`device_ieee`,`device_ep`,`cluster_id`,`attribute_id`) USING BTREE
  
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

INSERT INTO EnergyDeviceAttribute(houseieee,device_ieee,device_ep,Cluster_ID,Attribute_ID,value,Lasttime)
SELECT 
e.houseieee,attr.device_ieee,attr.device_ep,'0702','E003',
attr.value-IFNULL(e15.energyValue,0) as lastValue,
     DATE_SUB(DATE_SUB(attr.lasttime,INTERVAL MINUTE(attr.lasttime) MINUTE),
              INTERVAL SECOND(attr.lasttime) SECOND) as Lasttime
 from houseenergydevice E INNER JOIN deviceattribute attr 
   on E.houseIEEE=attr.houseIEEE and e.ieee=attr.device_ieee and e.ep=attr.device_ep     
left JOIN energyhistory_2015 e15 ON E.houseIEEE=e15.houseIEEE and e.ieee=e15.ieee and e.ep=e15.ep 
where attr.cluster_id='0702' and attr.attribute_id='E003'
  and e.deviceType='0'
  and (e15.opdatetime is null or (Date(attr.lasttime)=Date(e15.opdatetime)
  and Hour(attr.lasttime)=Hour(e15.opdatetime)));

DROP PROCEDURE IF EXISTS `EnergyCalculateByYear`;
CREATE PROCEDURE `EnergyCalculateByYear`(IN HouseIEEE varchar(20),IN IEEE varchar(20),IN EP varchar(20),IN OpTime datetime,IN EnergyValue Double)
Label_At_Start: 
BEGIN
	#Routine body goes here... 

  DECLARE LastEnergy, EnergySum, EnergySumTmp, EnergyTmpHour,EnergyTmpTime, EnergyTmpField, 
          BeginEnergy, EndEnergy, EnergyPrice, EnergyPrice1 DOUBLE;
  DECLARE LastOpTime, CurrentTime, DstBeginTIme, DstEndTIme, 
          BeginTimeHour, EndTimeHour, BeginTimeTime, EndTimeTime, BeginTimeTmp1, EndTimeTmp1 datetime;
  DECLARE aCount, I, J, K, Year1, Month1, Day1, Hour1, Minute1,Sec1 INTEGER;
  DECLARE Year2, Month2, Day2, Hour2, Minute2, secDiff,Sec2, IsDst, StartFieldTmp, EndFieldTmp, 
          TmpDone, iContinue, iPriceType,iField, iTime, iDst, EnergyCount, iTable INTEGER;
  DECLARE sSql VARCHAR(4000);
  DECLARE Cur_Time CURSOR for Select t.beginTime,t.EndTime,t.EnergyTimeID from energytime t where t.HouseIEEE=HouseIEEE order by T.EnergyTimeID;    
  DECLARE Cur_Field CURSOR for Select t.StartField,t.EndField,t.EnergyFieldID from energyfield t where t.HouseIEEE=HouseIEEE order by T.EnergyFieldID;
  DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET TmpDone=1;

  call EnergyCalculateByYearEx(HouseIEEE,IEEE,EP,OpTime,EnergyValue);
  LEAVE Label_At_Start;

  SELECT Count(*) into aCount FROM deviceattribute t 
    WHERE t.HouseIEEE=HouseIEEE and t.Device_IEEE=IEEE AND t.Device_EP=EP and t.Cluster_ID='0702' and t.Attribute_ID='E003';
  
  if aCount <> 1  THEN 
    LEAVE Label_At_Start;
  end if;

  SELECT t.Value,t.lasttime into LastEnergy,LastOpTime FROM deviceattribute t 
    WHERE t.HouseIEEE=HouseIEEE and t.Device_IEEE=IEEE AND t.Device_EP=EP and t.Cluster_ID='0702' and t.Attribute_ID='E003';
  SELECT t.PriceType,t.DstFlag,t.DstbeginTIme,t.DstEndTime into iPriceType,iDst,DstbeginTime,dstEndTime from Energy t
    where t.HouseIEEE=HouseIEEE;
  
  Set CurrentTime=OpTime;
  if ((timediff(LastOpTime,CurrentTime)>=0) and (DATEDIFF(CurrentTime,LastOpTime)<=0)) or (LastEnergy>EnergyValue) THEN    
    LEAVE Label_At_Start;
  END if;
  Set secDiff=Hour(timediff(CurrentTime,LastOpTime));
  if (MINUTE(timediff(CurrentTime,LastOpTime))>0) or (SECOND(timediff(CurrentTime,LastOpTime))>0) THEN
    Set secDiff=secDiff+1;
  end if;

  if secDiff>=1 THEN
    if ((EnergyValue-LastEnergy)/secDiff>10) THEN
      LEAVE Label_At_Start;
    end IF;
  end IF;
  Set secDiff=Hour(timediff(CurrentTime,LastOpTime))*3600+MINUTE(timediff(CurrentTime,LastOpTime))*60+SECOND(timediff(CurrentTime,LastOpTime));
  
  Set Year1=YEAR(LastOpTime),Month1=Month(LastOpTime), Day1=DAY(LastOpTime), 
      Hour1=Hour(LastOpTime), Minute1=Minute(LastOpTime),Sec1=Second(LastOpTime);
  Set Year2=YEAR(CurrentTime),Month2=Month(CurrentTime), Day2=DAY(CurrentTime), 
      Hour2=Hour(CurrentTime), Minute2=Minute(CurrentTime), Sec2=Second(CurrentTIme);
  Set I=0, aCount=DATEDIFF(CurrentTime,LastOpTime)*24+Hour2-Hour1;
  Set BeginTimeHour=LastOpTime, EndTimeHour=LastOpTime; 
  Set secDiff=secDiff+aCount*3600; 

  WHILE I<=aCount DO    
    IF I=0 THEN
      if i=aCount then
        Set EnergyTmpHour=(EnergyValue-LastEnergy)*((Minute2-Minute1)*60+Sec2-Sec1)/SecDiff;
        select date_add(EndTimeHour, interval Minute2-Minute1 MINUTE) into EndTimeHour;
        select date_add(EndTimeHour, interval Sec2-Sec1 SECOND) into EndTimeHour;      
      ELSE
        Set EnergyTmpHour=(EnergyValue-LastEnergy)*((60-Minute1)*60-Sec1)/SecDiff;
        select date_add(EndTimeHour, interval 60-Minute1-1 MINUTE) into EndTimeHour;
        select date_add(EndTimeHour, interval 60-Sec1-1 SECOND) into EndTimeHour;
      end if;
    elseif I=aCount THEN
      Set EnergyTmpHour=(EnergyValue-LastEnergy)*(Minute2*60+Sec2)/SecDiff;      
      set BeginTimeHour=date_add(EndTimeHour, interval 1 SECOND); 
      set EndTimeHour=CurrentTime;
    ELSE      
      Set EnergyTmpHour=(EnergyValue-LastEnergy)*3600/SecDiff;
      set BeginTimeHour=date_add(EndTimeHour, interval 1 SECOND);
      select date_add(EndTimeHour, interval 1 HOUR) into EndTimeHour; 
    End if;
    
    /*SELECT IFNULL(Sum(T.EnergyValue),0) into EnergySum from EnergyHistory t 
      WHERE t.HouseIEEE=HouseIEEE and t.IEEE=IEEE AND t.EP=EP
        and Year(t.opdatetime)=Year(BeginTimeHour) and Month(t.opdatetime)=Month(BeginTimeHour);*/
    
    Select Count(*) into iTable from information_schema.TABLES t where  t.Table_Name=CONCAT('EnergyHistory_',Year(BeginTimeHour));
    if iTable=0 THEN
      Set @sSql=CONCAT('create  TABLE EnergyHistory_',Year(BeginTimeHour),' like energyhistory');
      PREPARE stmtselect FROM @sSql;
      EXECUTE stmtselect;
      DEALLOCATE PREPARE stmtselect;
    end if;
    Set @HouseIEEE=HouseIEEE;
    Set @IEEE=IEEE;
    Set @EP=EP;
    Set @BeginTimeHour=BeginTimeHour;
    set @sSql=CONCAT('SELECT IFNULL(Sum(T.EnergyValue),0) into @EnergySum from ','EnergyHistory_',Year(BeginTimeHour),' t 
      WHERE t.HouseIEEE=? and t.IEEE=? AND t.EP=?
        and Year(t.opdatetime)=Year(?) and Month(t.opdatetime)=Month(?);');
    PREPARE stmtselect FROM @sSql;
    EXECUTE stmtselect USING @HouseIEEE,@IEEE,@EP,@BeginTimeHour,@BeginTimeHour;
    DEALLOCATE PREPARE stmtselect;
    set EnergySum=@EnergySum;
    Set EnergySumTmp=EnergySum;
    Set TmpDone=0;
    
    if iPriceType>1 then
      OPEN Cur_Time;
      FETCH Cur_Time into BeginTimeTime,EndTimeTime,iTime;
      while TmpDone<>1 DO
        Set iContinue=0;      
        if Time(BeginTimeHour)>=Time(BeginTimeTime) and TIme(EndTimeHour)<=Time(EndTimeTime) THEN
          set BeginTimeTmp1=BeginTimeHour;
          set EndTimeTmp1=EndTimeHour;
        ELSEIF Time(BeginTimeHour)>=Time(BeginTimeTime) and Time(EndTimeHour)>=Time(EndTimeTime) and Time(BeginTimeHour)<Time(EndTimeTime) THEN
          Set BeginTimeTmp1=BeginTimeHour;
          Set EndTimeTmp1=DATE_ADD(Date(EndTimeHour),INTERVAL Time(EndTimeTime) HOUR_SECOND);
        ELSEIF Time(BeginTimeHour)<Time(BeginTimeTime) and Time(EndTimeHour)<Time(EndTimeTime) and Time(EndTimeHour)>Time(BeginTimeTime) THEN
          Set BeginTimeTmp1=DATE_ADD(Date(EndTimeHour),INTERVAL Time(BeginTimeTime) HOUR_SECOND);
          Set EndTimeTmp1=DATE_ADD(Date(EndTimeHour),INTERVAL Time(EndTimeTime) HOUR_SECOND);
        ELSEIF Time(BeginTimeHour)<Time(BeginTimeTime) and Time(EndTimeHour)>Time(EndTimeTime) THEN
          Set BeginTimeTmp1=DATE_ADD(Date(EndTimeHour),INTERVAL Time(BeginTimeTime) HOUR_SECOND);
          Set EndTimeTmp1=DATE_ADD(Date(EndTimeHour),INTERVAL Time(EndTimeTime) HOUR_SECOND);
        ELSE
          Set iContinue=1;          
        end if;  
        if Time(BeginTimeTime)>Time(EndTimeTime) THEN
          set iContinue=1;
        end if;
        
        if iContinue=0 THEN
          set EnergyTmpTime=EnergyTmpHour*((Minute(EndTimeTmp1)-Minute(BeginTimeTmp1))*60+Second(EndTimeTmp1)-Second(BeginTimeTmp1))/((Minute(EndTimeHour)-Minute(BeginTimeHour))*60+Second(EndTimeHour)-Second(BeginTimeHour));
          
          if iPriceType=3 THEN
            OPEN Cur_Field;
            FETCH Cur_Field into BeginEnergy, EndEnergy,iField;
            WHILE tmpDone<>1 DO
              Set iContinue=0;
              Set EnergyTmpField=0;
              if EnergySumTmp>=BeginEnergy and EnergySumTmp+EnergyTmpTime<=EndEnergy THEN
                Set EnergyTmpField=EnergyTmpTime;
              ELSEIF EnergySumTmp>=BeginEnergy and EnergySumTmp<EndEnergy and EnergySumTmp+EnergyTmpTime>EndEnergy THEN
                Set EnergyTmpField=EndEnergy-EnergySumTmp;
              ELSEIF EnergySumTmp<BeginEnergy and EnergySumTmp+EnergyTmpTime<=EndEnergy and EnergySumTmp+EnergyTmpTIme>BeginEnergy THEN
                Set EnergyTmpField=EnergySumTmp+EnergyTmpTime-BeginEnergy;
              ELSEIF EnergySumTmp<BeginEnergy and EnergySumTmp+EnergyTmpTime>EndEnergy THEN
                Set EnergyTmpField=EndEnergy-BeginEnergy;
              ELSE
                Set iContinue=1;
              end if;
              
              if iContinue=0 then
                SELECT case  when iDst=1 and BeginTimeTmp1>=DstBeginTime and BeginTimeTmp1<=DstEndTime 
                                  then t.PriceDst 
                             else t.price 
                       end into EnergyPrice
                  from priceparam t where t.houseIEEE=houseIEEE and t.energyTimeId=iTime and t.energyFieldId=iField;
                
                Set @BeginTimeTmp1=BeginTimeTmp1;
                Set @EnergyTmpField=EnergyTmpField;
                Set @EnergyPrice1=EnergyTmpField*EnergyPrice;
                select @BeginTimeTmp1,@EnergyTmpField,@EnergyPrice1,iField,iTime;
                Set @sSql=CONCAT('SELECT Count(*) into @EnergyCount FROM ',' EnergyHistory_',Year(BeginTimeHour),' t 
                  where t.houseIEEE=? and t.IEEE=? and t.EP=?
                    and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');
                PREPARE stmtselect FROM @sSql;
                EXECUTE stmtselect using @HouseIEEE,@IEEE,@EP,@BeginTimeTmp1,@BeginTimeTmp1;
                DEALLOCATE PREPARE stmtselect;
                Set EnergyCount=@EnergyCount;
                if EnergyCount=0 then 
                  Set @sSql=CONCAT('insert into ',' EnergyHistory_',Year(BeginTimeHour),
                                   '(HouseIEEE,IEEE,EP,opDateTime,EnergyValue,EnergyPrice) values
                                           (?,?,?,?,?,?)');
                else 
                  Set @sSql=CONCAT('update ',' EnergyHistory_',Year(BeginTimeHour),
                                   ' t set t.energyValue=IfNull(t.energyValue,0)+?,
                                             t.energyPrice=IfNull(t.energyPrice,0)+?
                  where t.houseIEEE=? and t.IEEE=? and t.EP=?
                    and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');                  
                end if;
                
                PREPARE stmtselect FROM @sSql;
                if EnergyCount=0 then
                  EXECUTE stmtselect using @HouseIEEE,@IEEE,@EP,@BeginTimeTmp1,@EnergyTmpField,@EnergyPrice1;
                else
                  EXECUTE stmtselect using @EnergyTmpField,@EnergyPrice1,@HouseIEEE,@IEEE,@EP,@BeginTimeTmp1,@BeginTimeTmp1;
                end if;
                DEALLOCATE PREPARE stmtselect;
                /*SELECT Count(*) into EnergyCount FROM energyhistory t 
                  where t.houseIEEE=houseIEEE and t.IEEE=IEEE and t.EP=EP
                    and Date(t.opdatetime)=Date(BeginTimeTmp1) and Hour(t.opdatetime)=Hour(BeginTimeTmp1);
                if EnergyCount=0 then 
                  insert into energyhistory(HouseIEEE,IEEE,EP,opDateTime,EnergyValue,EnergyPrice) values
                                           (HouseIEEE,IEEE,EP,BeginTimeTmp1,EnergyTmpField,EnergyTmpField*EnergyPrice);
                else 
                  update energyhistory t set t.energyValue=t.energyValue+EnergyTmpField,
                                             t.energyPrice=t.energyPrice+EnergyTmpField*EnergyPrice
                  where t.houseIEEE=houseIEEE and t.IEEE=IEEE and t.EP=EP
                    and Date(t.opdatetime)=Date(BeginTimeTmp1) and Hour(t.opdatetime)=Hour(BeginTimeTmp1);
                end if;*/
              end if;
              FETCH Cur_Field into BeginEnergy, EndEnergy,iField;
            end WHILE;
            CLOSE Cur_Field;
            Set tmpDone=0;
            if EnergySumTmp+EnergyTmpTime>EndEnergy THEN
              /*Set EnergyTmpField=EnergySumTmp+EnergyTmpTime-EndEnergy;*/
              Set EnergyTmpField=EnergyTmpTime;

              Set @HouseIEEE=HouseIEEE;
              Set @IEEE=IEEE;
              Set @EP=EP;
              Set @BeginTimeTmp1=BeginTimeTmp1;
              Set @EnergyTmpField=EnergyTmpField;
              Set @EnergyPrice1=EnergyTmpField*EnergyPrice;

              Set @sSql=CONCAT('SELECT Count(*) into @EnergyCount FROM ',' EnergyHistory_',Year(BeginTimeHour),' t 
                  where t.houseIEEE=? and t.IEEE=? and t.EP=?
                    and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');
              PREPARE stmtselect FROM @sSql;
              EXECUTE stmtselect using @HouseIEEE,@IEEE,@EP,@BeginTimeTmp1,@BeginTimeTmp1;
              DEALLOCATE PREPARE stmtselect;
              Set EnergyCount=@EnergyCount;
              if EnergyCount=0 then 
                Set @sSql=CONCAT('insert into ',' EnergyHistory_',Year(BeginTimeHour),
                                 '(HouseIEEE,IEEE,EP,opDateTime,EnergyValue,EnergyPrice) values
                                         (?,?,?,?,?,?)');
              else 
                Set @sSql=CONCAT('update ',' EnergyHistory_',Year(BeginTimeHour),
                                 ' t set t.energyValue=IfNull(t.energyValue,0)+?,
                                         t.energyPrice=IfNull(t.energyPrice,0)+?
                where t.houseIEEE=? and t.IEEE=? and t.EP=?
                  and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');                  
              end if;
              
              PREPARE stmtselect FROM @sSql;
              if EnergyCount=0 then
                EXECUTE stmtselect using @HouseIEEE,@IEEE,@EP,@BeginTimeTmp1,@EnergyTmpField,@EnergyPrice1;
              else
                EXECUTE stmtselect using @EnergyTmpField,@EnergyPrice1,@HouseIEEE,@IEEE,@EP,@BeginTimeTmp1,@BeginTimeTmp1;
              end if;
              DEALLOCATE PREPARE stmtselect;
              /*SELECT Count(*) into EnergyCount FROM energyhistory t 
                  where t.houseIEEE=houseIEEE and t.IEEE=IEEE and t.EP=EP
                    and Date(t.opdatetime)=Date(BeginTimeTmp1) and Hour(t.opdatetime)=Hour(BeginTimeTmp1);
              if EnergyCount=0 then 
                insert into energyhistory(HouseIEEE,IEEE,EP,opDateTime,EnergyValue,EnergyPrice) values
                                         (HouseIEEE,IEEE,EP,BeginTimeTmp1,EnergyTmpField,EnergyTmpField*EnergyPrice);
              else 
                update energyhistory t set t.energyValue=t.energyValue+EnergyTmpField,
                                           t.energyPrice=t.energyPrice+EnergyTmpField*EnergyPrice
                where t.houseIEEE=houseIEEE and t.IEEE=IEEE and t.EP=EP
                  and Date(t.opdatetime)=Date(BeginTimeTmp1) and Hour(t.opdatetime)=Hour(BeginTimeTmp1);
              end if;*/
            end if;
          else
            SELECT case  when iDst=1 and BeginTimeTmp1>=DstBeginTime and BeginTimeTmp1<=DstEndTime 
                                  then t.PriceDst 
                             else t.price 
                       end into EnergyPrice
                  from priceparam t where t.houseIEEE=houseIEEE and t.energyTimeId=iTime;

            Set EnergyTmpField=EnergyTmpTime;
            Set @BeginTimeTmp1=BeginTimeTmp1;
            Set @EnergyTmpField=EnergyTmpField;
            Set @EnergyPrice1=EnergyTmpField*EnergyPrice;

            Set @sSql=CONCAT('SELECT Count(*) into @EnergyCount FROM ',' EnergyHistory_',Year(BeginTimeHour),' t 
                where t.houseIEEE=? and t.IEEE=? and t.EP=?
                  and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');
            PREPARE stmtselect FROM @sSql;
            EXECUTE stmtselect using @HouseIEEE,@IEEE,@EP,@BeginTimeTmp1,@BeginTimeTmp1;
            DEALLOCATE PREPARE stmtselect;
            Set EnergyCount=@EnergyCount;
            if EnergyCount=0 then 
              Set @sSql=CONCAT('insert into ',' EnergyHistory_',Year(BeginTimeHour),
                               '(HouseIEEE,IEEE,EP,opDateTime,EnergyValue,EnergyPrice) values
                                       (?,?,?,?,?,?)');
            else 
              Set @sSql=CONCAT('update ',' EnergyHistory_',Year(BeginTimeHour),
                               ' t set t.energyValue=IfNull(t.energyValue,0)+?,
                                       t.energyPrice=IfNull(t.energyPrice,0)+?
              where t.houseIEEE=? and t.IEEE=? and t.EP=?
                and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');                  
            end if;
              
            PREPARE stmtselect FROM @sSql;
            if EnergyCount=0 then
              EXECUTE stmtselect using @HouseIEEE,@IEEE,@EP,@BeginTimeTmp1,@EnergyTmpField,@EnergyPrice1;
            else
              EXECUTE stmtselect using @EnergyTmpField,@EnergyPrice1,@HouseIEEE,@IEEE,@EP,@BeginTimeTmp1,@BeginTimeTmp1;
            end if;
            DEALLOCATE PREPARE stmtselect;
            
            /*SELECT Count(*) into EnergyCount FROM energyhistory t 
                where t.houseIEEE=houseIEEE and t.IEEE=IEEE and t.EP=EP
                  and Date(t.opdatetime)=Date(BeginTimeTmp1) and Hour(t.opdatetime)=Hour(BeginTimeTmp1);
            if EnergyCount=0 then 
              insert into energyhistory(HouseIEEE,IEEE,EP,opDateTime,EnergyValue,EnergyPrice) values
                                       (HouseIEEE,IEEE,EP,BeginTimeTmp1,EnergyTmpTime,EnergyTmpTime*EnergyPrice);
            else 
              update energyhistory t set t.energyValue=t.energyValue+EnergyTmpTime,
                                         t.energyPrice=t.energyPrice+EnergyTmpTime*EnergyPrice
              where t.houseIEEE=houseIEEE and t.IEEE=IEEE and t.EP=EP
                and Date(t.opdatetime)=Date(BeginTimeTmp1) and Hour(t.opdatetime)=Hour(BeginTimeTmp1);
            end if;*/            
          end if;
        end if;
        FETCH Cur_Time into BeginTimeTime,EndTimeTime,iTime;
      END WHILE;
      CLOSE Cur_Time;
    
      Set I=I+1;
    else        
      OPEN Cur_Field;
      FETCH Cur_Field into BeginEnergy, EndEnergy,iField;
      WHILE tmpDone<>1 DO
        Set iContinue=0;
        Set EnergyTmpField=0;
        if EnergySumTmp>=BeginEnergy and EnergySumTmp+EnergyTmpHour<=EndEnergy THEN
          Set EnergyTmpField=EnergyTmpHour;
          Set iContinue=1;
        ELSEIF EnergySumTmp>=BeginEnergy and EnergySumTmp<EndEnergy and EnergySumTmp+EnergyTmpHour>EndEnergy THEN
          Set EnergyTmpField=EndEnergy-EnergySumTmp;
          Set iContinue=2;
        ELSEIF EnergySumTmp<BeginEnergy and EnergySumTmp+EnergyTmpHour<=EndEnergy and EnergySumTmp+EnergyTmpHour>BeginEnergy THEN
          Set EnergyTmpField=EnergySumTmp+EnergyTmpHour-BeginEnergy;
          Set iContinue=3;
        ELSEIF EnergySumTmp<BeginEnergy and EnergySumTmp+EnergyTmpHour>EndEnergy THEN
          Set EnergyTmpField=EndEnergy-BeginEnergy;
          Set iContinue=4;
        ELSE
          Set iContinue=0;          
        end if;
        
        if iContinue<>0 then  
                  
          SELECT case  when iDst=1 and BeginTimeHour>=DstBeginTime and BeginTimeHour<=DstEndTime 
                            then t.PriceDst 
                       else t.price 
                 end into EnergyPrice
            from priceparam t where t.houseIEEE=houseIEEE and t.energyFieldId=iField;
          Set @HouseIEEE=HouseIEEE;
          Set @IEEE=IEEE;
          Set @EP=EP;
          Set @beginTimeHour=beginTimeHour;
          Set @EnergyTmpField=EnergyTmpField;
          Set @EnergyPrice1=EnergyTmpField*EnergyPrice;
          Set @sSql=CONCAT('SELECT Count(*) into @EnergyCount FROM ',' EnergyHistory_',Year(BeginTimeHour),' t 
              where t.houseIEEE=? and t.IEEE=? and t.EP=?
                and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');
          PREPARE stmtselect FROM @sSql;
          EXECUTE stmtselect using @HouseIEEE,@IEEE,@EP,@beginTimeHour,@beginTimeHour;
          DEALLOCATE PREPARE stmtselect;
          Set EnergyCount=@EnergyCount;
          if EnergyCount=0 then 
            Set @sSql=CONCAT('insert into ',' EnergyHistory_',Year(BeginTimeHour),
                             '(HouseIEEE,IEEE,EP,opDateTime,EnergyValue,EnergyPrice) values
                                     (?,?,?,?,?,?)');
          else 
            Set @sSql=CONCAT('update ',' EnergyHistory_',Year(BeginTimeHour),
                             ' t set t.energyValue=IfNull(t.energyValue,0)+?,
                                     t.energyPrice=IfNull(t.energyPrice,0)+?
            where t.houseIEEE=? and t.IEEE=? and t.EP=?
              and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');                  
          end if;
           
          PREPARE stmtselect FROM @sSql;
          if EnergyCount=0 then
            EXECUTE stmtselect using @HouseIEEE,@IEEE,@EP,@beginTimeHour,@EnergyTmpField,@EnergyPrice1;
          else
            EXECUTE stmtselect using @EnergyTmpField,@EnergyPrice1,@HouseIEEE,@IEEE,@EP,@beginTimeHour,@beginTimeHour;
          end if;
          DEALLOCATE PREPARE stmtselect;
          
          /*SELECT Count(*) into EnergyCount FROM energyhistory t 
            where t.houseIEEE=houseIEEE and t.IEEE=IEEE and t.EP=EP
              and Date(t.opdatetime)=Date(beginTimeHour) and Hour(t.opdatetime)=Hour(BeginTimeHour);
          
          if EnergyCount=0 then 
            insert into energyhistory(HouseIEEE,IEEE,EP,opDateTime,EnergyValue,EnergyPrice) values
                                     (HouseIEEE,IEEE,EP,BeginTimeHour,EnergyTmpField,EnergyTmpField*EnergyPrice);
          else 
            update energyhistory t set t.energyValue=t.energyValue+EnergyTmpField,
                                       t.energyPrice=t.energyPrice+EnergyTmpField*EnergyPrice
            where t.houseIEEE=houseIEEE and t.IEEE=IEEE and t.EP=EP
              and Date(t.opdatetime)=Date(BeginTimeHour) and Hour(t.opdatetime)=Hour(BeginTimeHour);
          end if;*/
        end if;
        FETCH Cur_Field into BeginEnergy, EndEnergy,iField;        
      end WHILE;
      CLOSE Cur_Field;
      Select EnergySumTmp,EnergyTmpHour,EndEnergy,EnergyTmpField;
      if EnergySumTmp+EnergyTmpHour>EndEnergy THEN
        Set EnergyTmpField=EnergyTmpHour;
        /*Set EnergyTmpField=EnergySumTmp+EnergyTmpHour-EndEnergy;*/
        Set @HouseIEEE=HouseIEEE;
        Set @IEEE=IEEE;
        Set @EP=EP;
        Set @beginTimeHour=beginTimeHour;
        Set @EnergyTmpField=EnergyTmpField;
        Set @EnergyPrice1=EnergyTmpField*EnergyPrice;
        Set @sSql=CONCAT('SELECT Count(*) into @EnergyCount FROM ',' EnergyHistory_',Year(BeginTimeHour),' t 
            where t.houseIEEE=? and t.IEEE=? and t.EP=?
              and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');
        PREPARE stmtselect FROM @sSql;
        EXECUTE stmtselect using @HouseIEEE,@IEEE,@EP,@beginTimeHour,@beginTimeHour;
        DEALLOCATE PREPARE stmtselect;
        Set EnergyCount=@EnergyCount;
        if EnergyCount=0 then 
          Set @sSql=CONCAT('insert into ',' EnergyHistory_',Year(BeginTimeHour),
                           '(HouseIEEE,IEEE,EP,opDateTime,EnergyValue,EnergyPrice) values
                                   (?,?,?,?,?,?)');
        else 
          Set @sSql=CONCAT('update ',' EnergyHistory_',Year(BeginTimeHour),
                           ' t set t.energyValue=IfNull(t.energyValue,0)+?,
                                   t.energyPrice=IfNull(t.energyPrice,0)+?
          where t.houseIEEE=? and t.IEEE=? and t.EP=?
            and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');                  
        end if;
          
        PREPARE stmtselect FROM @sSql;
        if EnergyCount=0 then
          EXECUTE stmtselect using @HouseIEEE,@IEEE,@EP,@beginTimeHour,@EnergyTmpField,@EnergyPrice1;
        else
          EXECUTE stmtselect using @EnergyTmpField,@EnergyPrice1,@HouseIEEE,@IEEE,@EP,@beginTimeHour,@beginTimeHour;
        end if;
        DEALLOCATE PREPARE stmtselect;
        
        /*SELECT Count(*) into EnergyCount FROM energyhistory t 
            where t.houseIEEE=houseIEEE and t.IEEE=IEEE and t.EP=EP
              and Date(t.opdatetime)=Date(BeginTimeHour) and Hour(t.opdatetime)=Hour(BeginTimeHour);
          if EnergyCount=0 then 
            insert into energyhistory(HouseIEEE,IEEE,EP,opDateTime,EnergyValue,EnergyPrice) values
                                     (HouseIEEE,IEEE,EP,BeginTimeHour,EnergyTmpField,EnergyTmpField*EnergyPrice);
          else 
            update energyhistory t set t.energyValue=t.energyValue+EnergyTmpField,
                                       t.energyPrice=t.energyPrice+EnergyTmpField*EnergyPrice
            where t.houseIEEE=houseIEEE and t.IEEE=IEEE and t.EP=EP
              and Date(t.opdatetime)=Date(BeginTimeHour) and Hour(t.opdatetime)=Hour(BeginTimeHour);
          end if;*/
      end if;
      Set I=I+1;
    end if;
  end WHILE;
  
END Label_At_Start;


DROP PROCEDURE IF EXISTS `EnergyCalculateByYearEx`;
CREATE PROCEDURE `EnergyCalculateByYearEx`(IN HouseIEEE varchar(20),IN IEEE varchar(20),IN EP varchar(20),IN OpTime datetime,IN EnergyValue Double)
Label_At_Start: 
BEGIN
	#Routine body goes here... 

  DECLARE LastEnergy, EnergySum, EnergySumTmp, EnergyTmpHour,EnergyTmpTime, EnergyTmpField, 
          BeginEnergy, EndEnergy, EnergyPrice, EnergyPrice1 DOUBLE;
  DECLARE LastOpTime, CurrentTime, DstBeginTIme, DstEndTIme, 
          BeginTimeHour, EndTimeHour, BeginTimeTime, EndTimeTime, BeginTimeTmp1, EndTimeTmp1 datetime;
  DECLARE aCount, I, J, K, Year1, Month1, Day1, Hour1, Minute1,Sec1 INTEGER;
  DECLARE Year2, Month2, Day2, Hour2, Minute2, secDiff,Sec2, IsDst, StartFieldTmp, EndFieldTmp, 
          TmpDone, iContinue, iPriceType,iField, iTime, iDst, EnergyCount, iTable INTEGER;
  DECLARE sSql VARCHAR(4000);
  DECLARE Cur_Time CURSOR for Select t.beginTime,t.EndTime,t.EnergyTimeID from energytime t where t.HouseIEEE=HouseIEEE order by T.EnergyTimeID;    
  DECLARE Cur_Field CURSOR for Select t.StartField,t.EndField,t.EnergyFieldID from energyfield t where t.HouseIEEE=HouseIEEE order by T.EnergyFieldID;
  DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET TmpDone=1;


  SELECT Count(*) into aCount FROM energydeviceattribute t 
    WHERE t.HouseIEEE=HouseIEEE and t.Device_IEEE=IEEE AND t.Device_EP=EP and t.Cluster_ID='0702' and t.Attribute_ID='E003';
  
  if aCount <> 1  THEN 
    if aCount=0 THEN
      insert into energydeviceattribute(houseieee,device_ieee,device_ep,Cluster_ID,Attribute_ID,value,Lasttime)
      values (HouseIEEE,IEEE,EP,'0702','E003',EnergyValue,
              DATE_SUB(DATE_SUB(OpTime,INTERVAL MINUTE(OpTime) MINUTE),
              INTERVAL SECOND(OpTime) SECOND));
    end if;
    LEAVE Label_At_Start;
  end if;


  SELECT t.Value,t.lasttime into LastEnergy,LastOpTime FROM energydeviceattribute t 
    WHERE t.HouseIEEE=HouseIEEE and t.Device_IEEE=IEEE AND t.Device_EP=EP and t.Cluster_ID='0702' and t.Attribute_ID='E003';
  SELECT t.PriceType,t.DstFlag,t.DstbeginTIme,t.DstEndTime into iPriceType,iDst,DstbeginTime,dstEndTime from Energy t
    where t.HouseIEEE=HouseIEEE;
  
  Set CurrentTime=OpTime;
  if ((timediff(LastOpTime,CurrentTime)>=0) and (DATEDIFF(CurrentTime,LastOpTime)<=0)) or (LastEnergy>EnergyValue) THEN   
    LEAVE Label_At_Start;
  END if;

  /*Set secDiff=Hour(timediff(CurrentTime,LastOpTime));*/
  Set secDiff=DATEDIFF(CurrentTime,LastOpTime)*24+Hour(CurrentTime)-Hour(LastOpTime);

  if (MINUTE(timediff(CurrentTime,LastOpTime))>0) or (SECOND(timediff(CurrentTime,LastOpTime))>0) THEN
    Set secDiff=secDiff+1;
  end if;

  if secDiff>=1 THEN
    if ((EnergyValue-LastEnergy)/secDiff>10) THEN
      LEAVE Label_At_Start;
    end IF;
  end IF;

  /*Set secDiff=Hour(timediff(CurrentTime,LastOpTime))*3600+MINUTE(timediff(CurrentTime,LastOpTime))*60+SECOND(timediff(CurrentTime,LastOpTime));*/
  Set secDiff=(DATEDIFF(CurrentTime,LastOpTime)*24+Hour(CurrentTime)-Hour(LastOpTime))*3600+
              (MINUTE(CurrentTime)-MINUTE(LastOpTime))*60+SECOND(CurrentTime)-SECOND(LastOpTime);
  
  Set Year1=YEAR(LastOpTime),Month1=Month(LastOpTime), Day1=DAY(LastOpTime), 
      Hour1=Hour(LastOpTime), Minute1=Minute(LastOpTime),Sec1=Second(LastOpTime);
  Set Year2=YEAR(CurrentTime),Month2=Month(CurrentTime), Day2=DAY(CurrentTime), 
      Hour2=Hour(CurrentTime), Minute2=Minute(CurrentTime), Sec2=Second(CurrentTIme);
  Set I=0, aCount=DATEDIFF(CurrentTime,LastOpTime)*24+Hour2-Hour1;
  Set BeginTimeHour=LastOpTime, EndTimeHour=LastOpTime; 
  Set secDiff=secDiff+aCount*3600; 

  WHILE I<=aCount DO    
    IF I=0 THEN
      if i=aCount then
        Set EnergyTmpHour=(EnergyValue-LastEnergy)*((Minute2-Minute1)*60+Sec2-Sec1)/SecDiff;
        select date_add(EndTimeHour, interval Minute2-Minute1 MINUTE) into EndTimeHour;
        select date_add(EndTimeHour, interval Sec2-Sec1 SECOND) into EndTimeHour;      
      ELSE
        Set EnergyTmpHour=(EnergyValue-LastEnergy)*((60-Minute1)*60-Sec1)/SecDiff;
        select date_add(EndTimeHour, interval 60-Minute1-1 MINUTE) into EndTimeHour;
        select date_add(EndTimeHour, interval 60-Sec1-1 SECOND) into EndTimeHour;
      end if;
    elseif I=aCount THEN
      Set EnergyTmpHour=(EnergyValue-LastEnergy)*(Minute2*60+Sec2)/SecDiff;      
      set BeginTimeHour=date_add(EndTimeHour, interval 1 SECOND); 
      set EndTimeHour=CurrentTime;
      if aCount>0 THEN
        update energydeviceattribute t 
          set t.lasttime=DATE_SUB(DATE_SUB(OpTime,INTERVAL MINUTE(OpTime) MINUTE),INTERVAL SECOND(OpTime) SECOND),
              t.Value=EnergyValue-EnergyTmpHour
        WHERE t.HouseIEEE=HouseIEEE and t.Device_IEEE=IEEE AND t.Device_EP=EP and t.Cluster_ID='0702' and t.Attribute_ID='E003';
      end if;
      
    ELSE      
      Set EnergyTmpHour=(EnergyValue-LastEnergy)*3600/SecDiff;
      set BeginTimeHour=date_add(EndTimeHour, interval 1 SECOND);
      select date_add(EndTimeHour, interval 1 HOUR) into EndTimeHour; 
    End if;
    
    /*SELECT IFNULL(Sum(T.EnergyValue),0) into EnergySum from EnergyHistory t 
      WHERE t.HouseIEEE=HouseIEEE and t.IEEE=IEEE AND t.EP=EP
        and Year(t.opdatetime)=Year(BeginTimeHour) and Month(t.opdatetime)=Month(BeginTimeHour);*/
    
    Select Count(*) into iTable from information_schema.TABLES t where  t.Table_Name=CONCAT('EnergyHistory_',Year(BeginTimeHour));
    if iTable=0 THEN
      Set @sSql=CONCAT('create  TABLE EnergyHistory_',Year(BeginTimeHour),' like energyhistory');
      PREPARE stmtselect FROM @sSql;
      EXECUTE stmtselect;
      DEALLOCATE PREPARE stmtselect;
    end if;
    Set @HouseIEEE=HouseIEEE;
    Set @IEEE=IEEE;
    Set @EP=EP;
    Set @BeginTimeHour=BeginTimeHour;
    set @sSql=CONCAT('SELECT IFNULL(Sum(T.EnergyValue),0) into @EnergySum from ','EnergyHistory_',Year(BeginTimeHour),' t 
      WHERE t.HouseIEEE=? and t.IEEE=? AND t.EP=?
        and Year(t.opdatetime)=Year(?) and Month(t.opdatetime)=Month(?);');
    PREPARE stmtselect FROM @sSql;
    EXECUTE stmtselect USING @HouseIEEE,@IEEE,@EP,@BeginTimeHour,@BeginTimeHour;
    DEALLOCATE PREPARE stmtselect;
    set EnergySum=@EnergySum;
    Set EnergySumTmp=EnergySum;
    Set TmpDone=0;
    
    if iPriceType>1 then
      OPEN Cur_Time;
      FETCH Cur_Time into BeginTimeTime,EndTimeTime,iTime;
      while TmpDone<>1 DO
        Set iContinue=0;      
        if Time(BeginTimeHour)>=Time(BeginTimeTime) and TIme(EndTimeHour)<=Time(EndTimeTime) THEN
          set BeginTimeTmp1=BeginTimeHour;
          set EndTimeTmp1=EndTimeHour;
        ELSEIF Time(BeginTimeHour)>=Time(BeginTimeTime) and Time(EndTimeHour)>=Time(EndTimeTime) and Time(BeginTimeHour)<Time(EndTimeTime) THEN
          Set BeginTimeTmp1=BeginTimeHour;
          Set EndTimeTmp1=DATE_ADD(Date(EndTimeHour),INTERVAL Time(EndTimeTime) HOUR_SECOND);
        ELSEIF Time(BeginTimeHour)<Time(BeginTimeTime) and Time(EndTimeHour)<Time(EndTimeTime) and Time(EndTimeHour)>Time(BeginTimeTime) THEN
          Set BeginTimeTmp1=DATE_ADD(Date(EndTimeHour),INTERVAL Time(BeginTimeTime) HOUR_SECOND);
          Set EndTimeTmp1=DATE_ADD(Date(EndTimeHour),INTERVAL Time(EndTimeTime) HOUR_SECOND);
        ELSEIF Time(BeginTimeHour)<Time(BeginTimeTime) and Time(EndTimeHour)>Time(EndTimeTime) THEN
          Set BeginTimeTmp1=DATE_ADD(Date(EndTimeHour),INTERVAL Time(BeginTimeTime) HOUR_SECOND);
          Set EndTimeTmp1=DATE_ADD(Date(EndTimeHour),INTERVAL Time(EndTimeTime) HOUR_SECOND);
        ELSE
          Set iContinue=1;          
        end if;  
        if Time(BeginTimeTime)>Time(EndTimeTime) THEN
          set iContinue=1;
        end if;
        
        if iContinue=0 THEN
          set EnergyTmpTime=EnergyTmpHour*((Minute(EndTimeTmp1)-Minute(BeginTimeTmp1))*60+Second(EndTimeTmp1)-Second(BeginTimeTmp1))/((Minute(EndTimeHour)-Minute(BeginTimeHour))*60+Second(EndTimeHour)-Second(BeginTimeHour));
          
          if iPriceType=3 THEN
            OPEN Cur_Field;
            FETCH Cur_Field into BeginEnergy, EndEnergy,iField;
            WHILE tmpDone<>1 DO
              Set iContinue=0;
              Set EnergyTmpField=0;
              if EnergySumTmp>=BeginEnergy and EnergySumTmp+EnergyTmpTime<=EndEnergy THEN
                Set EnergyTmpField=EnergyTmpTime;
              ELSEIF EnergySumTmp>=BeginEnergy and EnergySumTmp<EndEnergy and EnergySumTmp+EnergyTmpTime>EndEnergy THEN
                Set EnergyTmpField=EndEnergy-EnergySumTmp;
              ELSEIF EnergySumTmp<BeginEnergy and EnergySumTmp+EnergyTmpTime<=EndEnergy and EnergySumTmp+EnergyTmpTIme>BeginEnergy THEN
                Set EnergyTmpField=EnergySumTmp+EnergyTmpTime-BeginEnergy;
              ELSEIF EnergySumTmp<BeginEnergy and EnergySumTmp+EnergyTmpTime>EndEnergy THEN
                Set EnergyTmpField=EndEnergy-BeginEnergy;
              ELSE
                Set iContinue=1;
              end if;
              
              if iContinue=0 then
                SELECT case  when iDst=1 and BeginTimeTmp1>=DstBeginTime and BeginTimeTmp1<=DstEndTime 
                                  then t.PriceDst 
                             else t.price 
                       end into EnergyPrice
                  from priceparam t where t.houseIEEE=houseIEEE and t.energyTimeId=iTime and t.energyFieldId=iField;
                
                Set @BeginTimeTmp1=BeginTimeTmp1;
                Set @EnergyTmpField=EnergyTmpField;
                Set @EnergyPrice1=EnergyTmpField*EnergyPrice;
                select @BeginTimeTmp1,@EnergyTmpField,@EnergyPrice1,iField,iTime;
                Set @sSql=CONCAT('SELECT Count(*) into @EnergyCount FROM ',' EnergyHistory_',Year(BeginTimeHour),' t 
                  where t.houseIEEE=? and t.IEEE=? and t.EP=?
                    and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');
                PREPARE stmtselect FROM @sSql;
                EXECUTE stmtselect using @HouseIEEE,@IEEE,@EP,@BeginTimeTmp1,@BeginTimeTmp1;
                DEALLOCATE PREPARE stmtselect;
                Set EnergyCount=@EnergyCount;
                if EnergyCount=0 then 
                  Set @sSql=CONCAT('insert into ',' EnergyHistory_',Year(BeginTimeHour),
                                   '(HouseIEEE,IEEE,EP,opDateTime,EnergyValue,EnergyPrice) values
                                           (?,?,?,?,?,?)');
                else 
                  Set @sSql=CONCAT('update ',' EnergyHistory_',Year(BeginTimeHour),
                                   ' t set t.energyValue=?,
                                             t.energyPrice=?
                  where t.houseIEEE=? and t.IEEE=? and t.EP=?
                    and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');                  
                end if;
                
                PREPARE stmtselect FROM @sSql;
                if EnergyCount=0 then
                  EXECUTE stmtselect using @HouseIEEE,@IEEE,@EP,@BeginTimeTmp1,@EnergyTmpField,@EnergyPrice1;
                else
                  EXECUTE stmtselect using @EnergyTmpField,@EnergyPrice1,@HouseIEEE,@IEEE,@EP,@BeginTimeTmp1,@BeginTimeTmp1;
                end if;
                DEALLOCATE PREPARE stmtselect;
                /*SELECT Count(*) into EnergyCount FROM energyhistory t 
                  where t.houseIEEE=houseIEEE and t.IEEE=IEEE and t.EP=EP
                    and Date(t.opdatetime)=Date(BeginTimeTmp1) and Hour(t.opdatetime)=Hour(BeginTimeTmp1);
                if EnergyCount=0 then 
                  insert into energyhistory(HouseIEEE,IEEE,EP,opDateTime,EnergyValue,EnergyPrice) values
                                           (HouseIEEE,IEEE,EP,BeginTimeTmp1,EnergyTmpField,EnergyTmpField*EnergyPrice);
                else 
                  update energyhistory t set t.energyValue=t.energyValue+EnergyTmpField,
                                             t.energyPrice=t.energyPrice+EnergyTmpField*EnergyPrice
                  where t.houseIEEE=houseIEEE and t.IEEE=IEEE and t.EP=EP
                    and Date(t.opdatetime)=Date(BeginTimeTmp1) and Hour(t.opdatetime)=Hour(BeginTimeTmp1);
                end if;*/
              end if;
              FETCH Cur_Field into BeginEnergy, EndEnergy,iField;
            end WHILE;
            CLOSE Cur_Field;
            Set tmpDone=0;
            if EnergySumTmp+EnergyTmpTime>EndEnergy THEN
              /*Set EnergyTmpField=EnergySumTmp+EnergyTmpTime-EndEnergy;*/
              Set EnergyTmpField=EnergyTmpTime;

              Set @HouseIEEE=HouseIEEE;
              Set @IEEE=IEEE;
              Set @EP=EP;
              Set @BeginTimeTmp1=BeginTimeTmp1;
              Set @EnergyTmpField=EnergyTmpField;
              Set @EnergyPrice1=EnergyTmpField*EnergyPrice;

              Set @sSql=CONCAT('SELECT Count(*) into @EnergyCount FROM ',' EnergyHistory_',Year(BeginTimeHour),' t 
                  where t.houseIEEE=? and t.IEEE=? and t.EP=?
                    and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');
              PREPARE stmtselect FROM @sSql;
              EXECUTE stmtselect using @HouseIEEE,@IEEE,@EP,@BeginTimeTmp1,@BeginTimeTmp1;
              DEALLOCATE PREPARE stmtselect;
              Set EnergyCount=@EnergyCount;
              if EnergyCount=0 then 
                Set @sSql=CONCAT('insert into ',' EnergyHistory_',Year(BeginTimeHour),
                                 '(HouseIEEE,IEEE,EP,opDateTime,EnergyValue,EnergyPrice) values
                                         (?,?,?,?,?,?)');
              else 
                Set @sSql=CONCAT('update ',' EnergyHistory_',Year(BeginTimeHour),
                                 ' t set t.energyValue=?,
                                         t.energyPrice=?
                where t.houseIEEE=? and t.IEEE=? and t.EP=?
                  and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');                  
              end if;
              
              PREPARE stmtselect FROM @sSql;
              if EnergyCount=0 then
                EXECUTE stmtselect using @HouseIEEE,@IEEE,@EP,@BeginTimeTmp1,@EnergyTmpField,@EnergyPrice1;
              else
                EXECUTE stmtselect using @EnergyTmpField,@EnergyPrice1,@HouseIEEE,@IEEE,@EP,@BeginTimeTmp1,@BeginTimeTmp1;
              end if;
              DEALLOCATE PREPARE stmtselect;
              /*SELECT Count(*) into EnergyCount FROM energyhistory t 
                  where t.houseIEEE=houseIEEE and t.IEEE=IEEE and t.EP=EP
                    and Date(t.opdatetime)=Date(BeginTimeTmp1) and Hour(t.opdatetime)=Hour(BeginTimeTmp1);
              if EnergyCount=0 then 
                insert into energyhistory(HouseIEEE,IEEE,EP,opDateTime,EnergyValue,EnergyPrice) values
                                         (HouseIEEE,IEEE,EP,BeginTimeTmp1,EnergyTmpField,EnergyTmpField*EnergyPrice);
              else 
                update energyhistory t set t.energyValue=t.energyValue+EnergyTmpField,
                                           t.energyPrice=t.energyPrice+EnergyTmpField*EnergyPrice
                where t.houseIEEE=houseIEEE and t.IEEE=IEEE and t.EP=EP
                  and Date(t.opdatetime)=Date(BeginTimeTmp1) and Hour(t.opdatetime)=Hour(BeginTimeTmp1);
              end if;*/
            end if;
          else
            SELECT case  when iDst=1 and BeginTimeTmp1>=DstBeginTime and BeginTimeTmp1<=DstEndTime 
                                  then t.PriceDst 
                             else t.price 
                       end into EnergyPrice
                  from priceparam t where t.houseIEEE=houseIEEE and t.energyTimeId=iTime;

            Set EnergyTmpField=EnergyTmpTime;
            Set @BeginTimeTmp1=BeginTimeTmp1;
            Set @EnergyTmpField=EnergyTmpField;
            Set @EnergyPrice1=EnergyTmpField*EnergyPrice;

            Set @sSql=CONCAT('SELECT Count(*) into @EnergyCount FROM ',' EnergyHistory_',Year(BeginTimeHour),' t 
                where t.houseIEEE=? and t.IEEE=? and t.EP=?
                  and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');
            PREPARE stmtselect FROM @sSql;
            EXECUTE stmtselect using @HouseIEEE,@IEEE,@EP,@BeginTimeTmp1,@BeginTimeTmp1;
            DEALLOCATE PREPARE stmtselect;
            Set EnergyCount=@EnergyCount;
            if EnergyCount=0 then 
              Set @sSql=CONCAT('insert into ',' EnergyHistory_',Year(BeginTimeHour),
                               '(HouseIEEE,IEEE,EP,opDateTime,EnergyValue,EnergyPrice) values
                                       (?,?,?,?,?,?)');
            else 
              Set @sSql=CONCAT('update ',' EnergyHistory_',Year(BeginTimeHour),
                               ' t set t.energyValue=?,
                                       t.energyPrice=?
              where t.houseIEEE=? and t.IEEE=? and t.EP=?
                and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');                  
            end if;
              
            PREPARE stmtselect FROM @sSql;
            if EnergyCount=0 then
              EXECUTE stmtselect using @HouseIEEE,@IEEE,@EP,@BeginTimeTmp1,@EnergyTmpField,@EnergyPrice1;
            else
              EXECUTE stmtselect using @EnergyTmpField,@EnergyPrice1,@HouseIEEE,@IEEE,@EP,@BeginTimeTmp1,@BeginTimeTmp1;
            end if;
            DEALLOCATE PREPARE stmtselect;
            
            /*SELECT Count(*) into EnergyCount FROM energyhistory t 
                where t.houseIEEE=houseIEEE and t.IEEE=IEEE and t.EP=EP
                  and Date(t.opdatetime)=Date(BeginTimeTmp1) and Hour(t.opdatetime)=Hour(BeginTimeTmp1);
            if EnergyCount=0 then 
              insert into energyhistory(HouseIEEE,IEEE,EP,opDateTime,EnergyValue,EnergyPrice) values
                                       (HouseIEEE,IEEE,EP,BeginTimeTmp1,EnergyTmpTime,EnergyTmpTime*EnergyPrice);
            else 
              update energyhistory t set t.energyValue=t.energyValue+EnergyTmpTime,
                                         t.energyPrice=t.energyPrice+EnergyTmpTime*EnergyPrice
              where t.houseIEEE=houseIEEE and t.IEEE=IEEE and t.EP=EP
                and Date(t.opdatetime)=Date(BeginTimeTmp1) and Hour(t.opdatetime)=Hour(BeginTimeTmp1);
            end if;*/            
          end if;
        end if;
        FETCH Cur_Time into BeginTimeTime,EndTimeTime,iTime;
      END WHILE;
      CLOSE Cur_Time;
    
      Set I=I+1;
    else        
      OPEN Cur_Field;
      FETCH Cur_Field into BeginEnergy, EndEnergy,iField;
      WHILE tmpDone<>1 DO
        Set iContinue=0;
        Set EnergyTmpField=0;
        if EnergySumTmp>=BeginEnergy and EnergySumTmp+EnergyTmpHour<=EndEnergy THEN
          Set EnergyTmpField=EnergyTmpHour;
          Set iContinue=1;
        ELSEIF EnergySumTmp>=BeginEnergy and EnergySumTmp<EndEnergy and EnergySumTmp+EnergyTmpHour>EndEnergy THEN
          Set EnergyTmpField=EndEnergy-EnergySumTmp;
          Set iContinue=2;
        ELSEIF EnergySumTmp<BeginEnergy and EnergySumTmp+EnergyTmpHour<=EndEnergy and EnergySumTmp+EnergyTmpHour>BeginEnergy THEN
          Set EnergyTmpField=EnergySumTmp+EnergyTmpHour-BeginEnergy;
          Set iContinue=3;
        ELSEIF EnergySumTmp<BeginEnergy and EnergySumTmp+EnergyTmpHour>EndEnergy THEN
          Set EnergyTmpField=EndEnergy-BeginEnergy;
          Set iContinue=4;
        ELSE
          Set iContinue=0;          
        end if;
        
        if iContinue<>0 then  
                  
          SELECT case  when iDst=1 and BeginTimeHour>=DstBeginTime and BeginTimeHour<=DstEndTime 
                            then t.PriceDst 
                       else t.price 
                 end into EnergyPrice
            from priceparam t where t.houseIEEE=houseIEEE and t.energyFieldId=iField;
          Set @HouseIEEE=HouseIEEE;
          Set @IEEE=IEEE;
          Set @EP=EP;
          Set @beginTimeHour=beginTimeHour;
          Set @EnergyTmpField=EnergyTmpField;
          Set @EnergyPrice1=EnergyTmpField*EnergyPrice;
          Set @sSql=CONCAT('SELECT Count(*) into @EnergyCount FROM ',' EnergyHistory_',Year(BeginTimeHour),' t 
              where t.houseIEEE=? and t.IEEE=? and t.EP=?
                and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');
          PREPARE stmtselect FROM @sSql;
          EXECUTE stmtselect using @HouseIEEE,@IEEE,@EP,@beginTimeHour,@beginTimeHour;
          DEALLOCATE PREPARE stmtselect;
          Set EnergyCount=@EnergyCount;
          if EnergyCount=0 then 
            Set @sSql=CONCAT('insert into ',' EnergyHistory_',Year(BeginTimeHour),
                             '(HouseIEEE,IEEE,EP,opDateTime,EnergyValue,EnergyPrice) values
                                     (?,?,?,?,?,?)');
          else 
            Set @sSql=CONCAT('update ',' EnergyHistory_',Year(BeginTimeHour),
                             ' t set t.energyValue=?,
                                     t.energyPrice=?
            where t.houseIEEE=? and t.IEEE=? and t.EP=?
              and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');                  
          end if;
           
          PREPARE stmtselect FROM @sSql;
          if EnergyCount=0 then
            EXECUTE stmtselect using @HouseIEEE,@IEEE,@EP,@beginTimeHour,@EnergyTmpField,@EnergyPrice1;
          else
            EXECUTE stmtselect using @EnergyTmpField,@EnergyPrice1,@HouseIEEE,@IEEE,@EP,@beginTimeHour,@beginTimeHour;
          end if;
          DEALLOCATE PREPARE stmtselect;
          
          /*SELECT Count(*) into EnergyCount FROM energyhistory t 
            where t.houseIEEE=houseIEEE and t.IEEE=IEEE and t.EP=EP
              and Date(t.opdatetime)=Date(beginTimeHour) and Hour(t.opdatetime)=Hour(BeginTimeHour);
          
          if EnergyCount=0 then 
            insert into energyhistory(HouseIEEE,IEEE,EP,opDateTime,EnergyValue,EnergyPrice) values
                                     (HouseIEEE,IEEE,EP,BeginTimeHour,EnergyTmpField,EnergyTmpField*EnergyPrice);
          else 
            update energyhistory t set t.energyValue=t.energyValue+EnergyTmpField,
                                       t.energyPrice=t.energyPrice+EnergyTmpField*EnergyPrice
            where t.houseIEEE=houseIEEE and t.IEEE=IEEE and t.EP=EP
              and Date(t.opdatetime)=Date(BeginTimeHour) and Hour(t.opdatetime)=Hour(BeginTimeHour);
          end if;*/
        end if;
        FETCH Cur_Field into BeginEnergy, EndEnergy,iField;        
      end WHILE;
      CLOSE Cur_Field;
      Select EnergySumTmp,EnergyTmpHour,EndEnergy,EnergyTmpField;
      if EnergySumTmp+EnergyTmpHour>EndEnergy THEN
        Set EnergyTmpField=EnergyTmpHour;
        /*Set EnergyTmpField=EnergySumTmp+EnergyTmpHour-EndEnergy;*/
        Set @HouseIEEE=HouseIEEE;
        Set @IEEE=IEEE;
        Set @EP=EP;
        Set @beginTimeHour=beginTimeHour;
        Set @EnergyTmpField=EnergyTmpField;
        Set @EnergyPrice1=EnergyTmpField*EnergyPrice;
        Set @sSql=CONCAT('SELECT Count(*) into @EnergyCount FROM ',' EnergyHistory_',Year(BeginTimeHour),' t 
            where t.houseIEEE=? and t.IEEE=? and t.EP=?
              and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');
        PREPARE stmtselect FROM @sSql;
        EXECUTE stmtselect using @HouseIEEE,@IEEE,@EP,@beginTimeHour,@beginTimeHour;
        DEALLOCATE PREPARE stmtselect;
        Set EnergyCount=@EnergyCount;
        if EnergyCount=0 then 
          Set @sSql=CONCAT('insert into ',' EnergyHistory_',Year(BeginTimeHour),
                           '(HouseIEEE,IEEE,EP,opDateTime,EnergyValue,EnergyPrice) values
                                   (?,?,?,?,?,?)');
        else 
          Set @sSql=CONCAT('update ',' EnergyHistory_',Year(BeginTimeHour),
                           ' t set t.energyValue=?,
                                   t.energyPrice=?
          where t.houseIEEE=? and t.IEEE=? and t.EP=?
            and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');                  
        end if;
          
        PREPARE stmtselect FROM @sSql;
        if EnergyCount=0 then
          EXECUTE stmtselect using @HouseIEEE,@IEEE,@EP,@beginTimeHour,@EnergyTmpField,@EnergyPrice1;
        else
          EXECUTE stmtselect using @EnergyTmpField,@EnergyPrice1,@HouseIEEE,@IEEE,@EP,@beginTimeHour,@beginTimeHour;
        end if;
        DEALLOCATE PREPARE stmtselect;
        
        /*SELECT Count(*) into EnergyCount FROM energyhistory t 
            where t.houseIEEE=houseIEEE and t.IEEE=IEEE and t.EP=EP
              and Date(t.opdatetime)=Date(BeginTimeHour) and Hour(t.opdatetime)=Hour(BeginTimeHour);
          if EnergyCount=0 then 
            insert into energyhistory(HouseIEEE,IEEE,EP,opDateTime,EnergyValue,EnergyPrice) values
                                     (HouseIEEE,IEEE,EP,BeginTimeHour,EnergyTmpField,EnergyTmpField*EnergyPrice);
          else 
            update energyhistory t set t.energyValue=t.energyValue+EnergyTmpField,
                                       t.energyPrice=t.energyPrice+EnergyTmpField*EnergyPrice
            where t.houseIEEE=houseIEEE and t.IEEE=IEEE and t.EP=EP
              and Date(t.opdatetime)=Date(BeginTimeHour) and Hour(t.opdatetime)=Hour(BeginTimeHour);
          end if;*/
      end if;
      Set I=I+1;
    end if;
  end WHILE;
  
END Label_At_Start;
/*电能计算--end--*/


/*新建ModeEPActLib表*/
CREATE TABLE `ModeEPActLib` (
`ID`  int(20) NOT NULL AUTO_INCREMENT,
`SolidModelID`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`ActID`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`ID`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=COMPACT
;

/*device新增SolidModelID字段*/
ALTER TABLE `device`
ADD COLUMN `SolidModelID`  varchar(50) NULL DEFAULT NULL;

/*新建ModeEPAttrLib表*/
CREATE TABLE `ModeEPAttrLib` (
`id`  int(20) NOT NULL AUTO_INCREMENT,
`SolidModelID`  varchar(255) NULL DEFAULT NULL ,
`ClusterID`  varchar(20) NULL DEFAULT NULL ,
`AttrID`  varchar(20) NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
;

/*modeeplib新增SolidModelID字段*/
ALTER TABLE `modeeplib`
ADD COLUMN `SolidModelID`  varchar(50) NULL DEFAULT NULL;


/*属性历史表重建索引脚本 begin---------*/
ALTER TABLE attributenamelib ADD INDEX clusterID_attrID(clusterID,attrID);
ALTER TABLE deviceattributehistory_houseid_year DROP INDEX opdatetime;
ALTER TABLE deviceattributehistory_houseid_year DROP INDEX attributeName;
ALTER TABLE `deviceattributehistory_houseid_year` DROP INDEX `houseIEEE, room_id, device_ieee, device_ep, cluster_id, attribut`;
ALTER TABLE deviceattributehistory_houseid_year ADD INDEX houseIEEE_deviceIEEE_deviceEP_opdatetime(houseIEEE, device_ieee, device_ep, opdatetime);
ALTER TABLE deviceattributehistory_houseid_year ADD INDEX clusterID_attrID(cluster_id,attribute_id);

DROP PROCEDURE IF EXISTS ReCreateIndexProc_Util;
CREATE PROCEDURE ReCreateIndexProc_Util()
Label_At_Start:
BEGIN
	DECLARE no_more_products INT DEFAULT 0;
	DECLARE updateSql VARCHAR(500);
	DECLARE tableName VARCHAR(50);
	DECLARE devTableCursor CURSOR FOR select a.TABLE_NAME from information_schema.TABLES a where a.TABLE_NAME like 'deviceattributehistory_00137%' and a.TABLE_SCHEMA = 'zigbeedevice';
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET no_more_products = 1;
	
	OPEN devTableCursor;
	loop_replace:LOOP
	FETCH devTableCursor into tableName;
	IF no_more_products = 1 THEN
		LEAVE loop_replace;
	END IF;
	SET updateSql = CONCAT("ALTER TABLE `", tableName , '` DROP INDEX `opdatetime`');
	SET @v_sql = updateSql;
  PREPARE stmt from @v_sql;
  EXECUTE stmt;
	DEALLOCATE PREPARE stmt;

	SET updateSql = CONCAT("ALTER TABLE `", tableName , '` DROP INDEX `attributeName`');
	SET @v_sql = updateSql;
  PREPARE stmt from @v_sql;
  EXECUTE stmt;
	DEALLOCATE PREPARE stmt;

	SET updateSql = CONCAT("ALTER TABLE `", tableName , '` DROP INDEX `houseIEEE, room_id, device_ieee, device_ep, cluster_id, attribut`');
	SET @v_sql = updateSql;
  PREPARE stmt from @v_sql;
  EXECUTE stmt;
	DEALLOCATE PREPARE stmt;

	SET updateSql = CONCAT("ALTER TABLE ", tableName , ' ADD INDEX houseIEEE_deviceIEEE_deviceEP_opdatetime(houseIEEE, device_ieee, device_ep, opdatetime)');
	SET @v_sql = updateSql;
  PREPARE stmt from @v_sql;
  EXECUTE stmt;
	DEALLOCATE PREPARE stmt;

	SET updateSql = CONCAT("ALTER TABLE ", tableName , ' ADD INDEX clusterID_attrID(cluster_id,attribute_id)');
	SET @v_sql = updateSql;
  PREPARE stmt from @v_sql;
  EXECUTE stmt;
	DEALLOCATE PREPARE stmt;

	END LOOP;
	CLOSE devTableCursor;
END Label_At_Start;

CALL ReCreateIndexProc_Util;
/*属性历史表重建索引end----------*/

/*815A缺少电能、电压、功率、电流*/
INSERT INTO `deviceattrlib` (`ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('Z815A', '0009', '0702', 'E000', 'Current', '3', '电流值');
INSERT INTO `deviceattrlib` (`ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('Z815A', '0009', '0702', 'E001', 'Voltage', '3', '电压值');
INSERT INTO `deviceattrlib` (`ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('Z815A', '0009', '0702', 'E002', 'Power', '3', '功率值');
INSERT INTO `deviceattrlib` (`ModelID`, `DeviceID`, `ClusterID`, `AttrID`, `AttrName`, `DataType`, `UniqueName`) VALUES ('Z815A', '0009', '0702', 'E003', 'Energy', '3', '电能值');

/*电能计算修改计算逻辑，不然计算出来的值和实际不符begin-----------*/
ALTER TABLE `energydeviceattribute`
ADD COLUMN `lastopvalue`  varchar(50) NULL AFTER `lasttime`,
ADD COLUMN `lastoptime`  datetime NULL AFTER `lastopvalue`;

update energydeviceattribute
set lastopvalue=Value,
lastoptime=lasttime;

DROP PROCEDURE IF EXISTS `EnergyCalculateByYearEx`;
CREATE PROCEDURE `EnergyCalculateByYearEx`(IN HouseIEEE varchar(20),IN IEEE varchar(20),IN EP varchar(20),IN OpTime datetime,IN EnergyValue Double)
Label_At_Start: 
BEGIN
	#Routine body goes here... 

  DECLARE LastEnergy, EnergySum, EnergySumTmp, EnergyTmpHour,EnergyTmpTime, EnergyTmpField, 
          BeginEnergy, EndEnergy, EnergyPrice, EnergyPrice1 DOUBLE;
  DECLARE LastOpTime, CurrentTime, DstBeginTIme, DstEndTIme, 
          BeginTimeHour, EndTimeHour, BeginTimeTime, EndTimeTime, 
          BeginTimeTmp1, EndTimeTmp1 datetime;
  DECLARE LastOpEnergy DOUBLE;
  DECLARE LastOpDateTime datetime;

  DECLARE aCount, I, J, K, Year1, Month1, Day1, Hour1, Minute1,Sec1 INTEGER;
  DECLARE Year2, Month2, Day2, Hour2, Minute2, secDiff,Sec2, IsDst, StartFieldTmp, EndFieldTmp, 
          TmpDone, iContinue, iPriceType,iField, iTime, iDst, EnergyCount, iTable INTEGER;
  DECLARE sSql VARCHAR(4000);
  DECLARE Cur_Time CURSOR for Select t.beginTime,t.EndTime,t.EnergyTimeID from energytime t where t.HouseIEEE=HouseIEEE order by T.EnergyTimeID;    
  DECLARE Cur_Field CURSOR for Select t.StartField,t.EndField,t.EnergyFieldID from energyfield t where t.HouseIEEE=HouseIEEE order by T.EnergyFieldID;
  DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET TmpDone=1;


  SELECT Count(*) into aCount FROM energydeviceattribute t 
    WHERE t.HouseIEEE=HouseIEEE and t.Device_IEEE=IEEE AND t.Device_EP=EP and t.Cluster_ID='0702' and t.Attribute_ID='E003';
  
  if aCount <> 1  THEN 
    if aCount=0 THEN
      insert into energydeviceattribute
      (houseieee,device_ieee,device_ep,Cluster_ID,Attribute_ID,
       value,Lasttime,lastopvalue,lastoptime)
      values (HouseIEEE,IEEE,EP,'0702','E003',EnergyValue,
              DATE_SUB(DATE_SUB(OpTime,INTERVAL MINUTE(OpTime) MINUTE),
              INTERVAL SECOND(OpTime) SECOND),EnergyValue,OpTime);
    end if;
    LEAVE Label_At_Start;
  end if;


  SELECT t.Value,t.lasttime,t.lastopvalue,t.lastoptime into LastEnergy,LastOpTime,LastOpEnergy,LastOpDateTime
  FROM energydeviceattribute t 
    WHERE t.HouseIEEE=HouseIEEE and t.Device_IEEE=IEEE AND t.Device_EP=EP and t.Cluster_ID='0702' and t.Attribute_ID='E003';
  SELECT t.PriceType,t.DstFlag,t.DstbeginTIme,t.DstEndTime into iPriceType,iDst,DstbeginTime,dstEndTime from Energy t
    where t.HouseIEEE=HouseIEEE;
  
  Set CurrentTime=OpTime;
  if ((timediff(LastOpTime,CurrentTime)>=0) and (DATEDIFF(CurrentTime,LastOpTime)<=0)) or (LastEnergy>EnergyValue) THEN   
    LEAVE Label_At_Start;
  END if;

  /*Set secDiff=Hour(timediff(CurrentTime,LastOpTime));*/
  Set secDiff=DATEDIFF(CurrentTime,LastOpTime)*24+Hour(CurrentTime)-Hour(LastOpTime);
  
  if SecDiff>0 THEN
    set LastOpTime=LastOpDateTime;
    set LastEnergy=LastOpEnergy;
    Set secDiff=DATEDIFF(CurrentTime,LastOpTime)*24+Hour(CurrentTime)-Hour(LastOpTime);
  end IF;

  if (MINUTE(timediff(CurrentTime,LastOpTime))>0) or (SECOND(timediff(CurrentTime,LastOpTime))>0) THEN
    Set secDiff=secDiff+1;
  end if;

  if secDiff>=1 THEN
    if ((EnergyValue-LastEnergy)/secDiff>10) THEN
      LEAVE Label_At_Start;
    end IF;
  end IF;

  /*Set secDiff=Hour(timediff(CurrentTime,LastOpTime))*3600+MINUTE(timediff(CurrentTime,LastOpTime))*60+SECOND(timediff(CurrentTime,LastOpTime));*/
  Set secDiff=(DATEDIFF(CurrentTime,LastOpTime)*24+Hour(CurrentTime)-Hour(LastOpTime))*3600+
              (MINUTE(CurrentTime)-MINUTE(LastOpTime))*60+SECOND(CurrentTime)-SECOND(LastOpTime);
  
  Set Year1=YEAR(LastOpTime),Month1=Month(LastOpTime), Day1=DAY(LastOpTime), 
      Hour1=Hour(LastOpTime), Minute1=Minute(LastOpTime),Sec1=Second(LastOpTime);
  Set Year2=YEAR(CurrentTime),Month2=Month(CurrentTime), Day2=DAY(CurrentTime), 
      Hour2=Hour(CurrentTime), Minute2=Minute(CurrentTime), Sec2=Second(CurrentTIme);
  Set I=0, aCount=DATEDIFF(CurrentTime,LastOpTime)*24+Hour2-Hour1;
  Set BeginTimeHour=LastOpTime, EndTimeHour=LastOpTime; 
  /*Set secDiff=secDiff+aCount*3600; */
 
  WHILE I<=aCount DO    
    IF I=0 THEN
      if i=aCount then
        Set EnergyTmpHour=(EnergyValue-LastEnergy)*((Minute2-Minute1)*60+Sec2-Sec1)/SecDiff;
        select date_add(EndTimeHour, interval Minute2-Minute1 MINUTE) into EndTimeHour;
        select date_add(EndTimeHour, interval Sec2-Sec1 SECOND) into EndTimeHour; 
        update energydeviceattribute t 
          set t.lastoptime=CurrentTime,
              t.LastopValue=EnergyValue
        WHERE t.HouseIEEE=HouseIEEE and t.Device_IEEE=IEEE AND t.Device_EP=EP and t.Cluster_ID='0702' and t.Attribute_ID='E003';     
      ELSE
        Set EnergyTmpHour=(EnergyValue-LastEnergy)*((60-Minute1)*60-Sec1)/SecDiff;
        select date_add(EndTimeHour, interval 60-Minute1-1 MINUTE) into EndTimeHour;
        select date_add(EndTimeHour, interval 60-Sec1-1 SECOND) into EndTimeHour;
      end if;
    elseif I=aCount THEN
      Set EnergyTmpHour=(EnergyValue-LastEnergy)*(Minute2*60+Sec2)/SecDiff;      
      set BeginTimeHour=date_add(EndTimeHour, interval 1 SECOND); 
      set EndTimeHour=CurrentTime;
      if aCount>0 THEN
        update energydeviceattribute t 
          set t.lasttime=DATE_SUB(DATE_SUB(OpTime,INTERVAL MINUTE(OpTime) MINUTE),INTERVAL SECOND(OpTime) SECOND),
              t.Value=EnergyValue-EnergyTmpHour,
              t.lastoptime=CurrentTime,
              t.LastopValue=EnergyValue
        WHERE t.HouseIEEE=HouseIEEE and t.Device_IEEE=IEEE AND t.Device_EP=EP and t.Cluster_ID='0702' and t.Attribute_ID='E003';

      ELSE
        update energydeviceattribute t 
          set t.lastoptime=CurrentTime,
              t.LastopValue=EnergyValue
        WHERE t.HouseIEEE=HouseIEEE and t.Device_IEEE=IEEE AND t.Device_EP=EP and t.Cluster_ID='0702' and t.Attribute_ID='E003';
      end if;      
    ELSE      
      Set EnergyTmpHour=(EnergyValue-LastEnergy)*3600/SecDiff;
      set BeginTimeHour=date_add(EndTimeHour, interval 1 SECOND);
      select date_add(EndTimeHour, interval 1 HOUR) into EndTimeHour; 
    End if;
    
    /*SELECT IFNULL(Sum(T.EnergyValue),0) into EnergySum from EnergyHistory t 
      WHERE t.HouseIEEE=HouseIEEE and t.IEEE=IEEE AND t.EP=EP
        and Year(t.opdatetime)=Year(BeginTimeHour) and Month(t.opdatetime)=Month(BeginTimeHour);*/
    
    Select Count(*) into iTable from information_schema.TABLES t where  t.Table_Name=CONCAT('EnergyHistory_',Year(BeginTimeHour));
    if iTable=0 THEN
      Set @sSql=CONCAT('create  TABLE EnergyHistory_',Year(BeginTimeHour),' like energyhistory');
      PREPARE stmtselect FROM @sSql;
      EXECUTE stmtselect;
      DEALLOCATE PREPARE stmtselect;
    end if;
    Set @HouseIEEE=HouseIEEE;
    Set @IEEE=IEEE;
    Set @EP=EP;
    Set @BeginTimeHour=BeginTimeHour;
    set @sSql=CONCAT('SELECT IFNULL(Sum(T.EnergyValue),0) into @EnergySum from ','EnergyHistory_',Year(BeginTimeHour),' t 
      WHERE t.HouseIEEE=? and t.IEEE=? AND t.EP=?
        and Year(t.opdatetime)=Year(?) and Month(t.opdatetime)=Month(?);');
    PREPARE stmtselect FROM @sSql;
    EXECUTE stmtselect USING @HouseIEEE,@IEEE,@EP,@BeginTimeHour,@BeginTimeHour;
    DEALLOCATE PREPARE stmtselect;
    set EnergySum=@EnergySum;
    Set EnergySumTmp=EnergySum;
    Set TmpDone=0;
    
    if iPriceType>1 then
      OPEN Cur_Time;
      FETCH Cur_Time into BeginTimeTime,EndTimeTime,iTime;
      while TmpDone<>1 DO
        Set iContinue=0;      
        if Time(BeginTimeHour)>=Time(BeginTimeTime) and TIme(EndTimeHour)<=Time(EndTimeTime) THEN
          set BeginTimeTmp1=BeginTimeHour;
          set EndTimeTmp1=EndTimeHour;
        ELSEIF Time(BeginTimeHour)>=Time(BeginTimeTime) and Time(EndTimeHour)>=Time(EndTimeTime) and Time(BeginTimeHour)<Time(EndTimeTime) THEN
          Set BeginTimeTmp1=BeginTimeHour;
          Set EndTimeTmp1=DATE_ADD(Date(EndTimeHour),INTERVAL Time(EndTimeTime) HOUR_SECOND);
        ELSEIF Time(BeginTimeHour)<Time(BeginTimeTime) and Time(EndTimeHour)<Time(EndTimeTime) and Time(EndTimeHour)>Time(BeginTimeTime) THEN
          Set BeginTimeTmp1=DATE_ADD(Date(EndTimeHour),INTERVAL Time(BeginTimeTime) HOUR_SECOND);
          Set EndTimeTmp1=DATE_ADD(Date(EndTimeHour),INTERVAL Time(EndTimeTime) HOUR_SECOND);
        ELSEIF Time(BeginTimeHour)<Time(BeginTimeTime) and Time(EndTimeHour)>Time(EndTimeTime) THEN
          Set BeginTimeTmp1=DATE_ADD(Date(EndTimeHour),INTERVAL Time(BeginTimeTime) HOUR_SECOND);
          Set EndTimeTmp1=DATE_ADD(Date(EndTimeHour),INTERVAL Time(EndTimeTime) HOUR_SECOND);
        ELSE
          Set iContinue=1;          
        end if;  
        if Time(BeginTimeTime)>Time(EndTimeTime) THEN
          set iContinue=1;
        end if;
        
        if iContinue=0 THEN
          set EnergyTmpTime=EnergyTmpHour*((Minute(EndTimeTmp1)-Minute(BeginTimeTmp1))*60+Second(EndTimeTmp1)-Second(BeginTimeTmp1))/((Minute(EndTimeHour)-Minute(BeginTimeHour))*60+Second(EndTimeHour)-Second(BeginTimeHour));
          
          if iPriceType=3 THEN
            OPEN Cur_Field;
            FETCH Cur_Field into BeginEnergy, EndEnergy,iField;
            WHILE tmpDone<>1 DO
              Set iContinue=0;
              Set EnergyTmpField=0;
              if EnergySumTmp>=BeginEnergy and EnergySumTmp+EnergyTmpTime<=EndEnergy THEN
                Set EnergyTmpField=EnergyTmpTime;
              ELSEIF EnergySumTmp>=BeginEnergy and EnergySumTmp<EndEnergy and EnergySumTmp+EnergyTmpTime>EndEnergy THEN
                Set EnergyTmpField=EndEnergy-EnergySumTmp;
              ELSEIF EnergySumTmp<BeginEnergy and EnergySumTmp+EnergyTmpTime<=EndEnergy and EnergySumTmp+EnergyTmpTIme>BeginEnergy THEN
                Set EnergyTmpField=EnergySumTmp+EnergyTmpTime-BeginEnergy;
              ELSEIF EnergySumTmp<BeginEnergy and EnergySumTmp+EnergyTmpTime>EndEnergy THEN
                Set EnergyTmpField=EndEnergy-BeginEnergy;
              ELSE
                Set iContinue=1;
              end if;
              
              if iContinue=0 then
                SELECT case  when iDst=1 and BeginTimeTmp1>=DstBeginTime and BeginTimeTmp1<=DstEndTime 
                                  then t.PriceDst 
                             else t.price 
                       end into EnergyPrice
                  from priceparam t where t.houseIEEE=houseIEEE and t.energyTimeId=iTime and t.energyFieldId=iField;
                
                Set @BeginTimeTmp1=BeginTimeTmp1;
                Set @EnergyTmpField=EnergyTmpField;
                Set @EnergyPrice1=EnergyTmpField*EnergyPrice;
                select @BeginTimeTmp1,@EnergyTmpField,@EnergyPrice1,iField,iTime;
                Set @sSql=CONCAT('SELECT Count(*) into @EnergyCount FROM ',' EnergyHistory_',Year(BeginTimeHour),' t 
                  where t.houseIEEE=? and t.IEEE=? and t.EP=?
                    and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');
                PREPARE stmtselect FROM @sSql;
                EXECUTE stmtselect using @HouseIEEE,@IEEE,@EP,@BeginTimeTmp1,@BeginTimeTmp1;
                DEALLOCATE PREPARE stmtselect;
                Set EnergyCount=@EnergyCount;
                if EnergyCount=0 then 
                  Set @sSql=CONCAT('insert into ',' EnergyHistory_',Year(BeginTimeHour),
                                   '(HouseIEEE,IEEE,EP,opDateTime,EnergyValue,EnergyPrice) values
                                           (?,?,?,?,?,?)');
                else 
                  if I=aCount-1 and aCount>0 then
                    Set @sSql=CONCAT('update ',' EnergyHistory_',Year(BeginTimeHour),
                                     ' t set IfNull(t.energyValue,0)+?,
                                             IfNull(t.energyPrice,0)+?
                    where t.houseIEEE=? and t.IEEE=? and t.EP=?
                      and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');
                  else
                    Set @sSql=CONCAT('update ',' EnergyHistory_',Year(BeginTimeHour),
                                     ' t set t.energyValue=?,
                                               t.energyPrice=?
                    where t.houseIEEE=? and t.IEEE=? and t.EP=?
                      and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');                  
                  end if;
                end if;
                
                PREPARE stmtselect FROM @sSql;
                if EnergyCount=0 then
                  EXECUTE stmtselect using @HouseIEEE,@IEEE,@EP,@BeginTimeTmp1,@EnergyTmpField,@EnergyPrice1;
                else
                  EXECUTE stmtselect using @EnergyTmpField,@EnergyPrice1,@HouseIEEE,@IEEE,@EP,@BeginTimeTmp1,@BeginTimeTmp1;
                end if;
                DEALLOCATE PREPARE stmtselect;
                /*SELECT Count(*) into EnergyCount FROM energyhistory t 
                  where t.houseIEEE=houseIEEE and t.IEEE=IEEE and t.EP=EP
                    and Date(t.opdatetime)=Date(BeginTimeTmp1) and Hour(t.opdatetime)=Hour(BeginTimeTmp1);
                if EnergyCount=0 then 
                  insert into energyhistory(HouseIEEE,IEEE,EP,opDateTime,EnergyValue,EnergyPrice) values
                                           (HouseIEEE,IEEE,EP,BeginTimeTmp1,EnergyTmpField,EnergyTmpField*EnergyPrice);
                else 
                  update energyhistory t set t.energyValue=t.energyValue+EnergyTmpField,
                                             t.energyPrice=t.energyPrice+EnergyTmpField*EnergyPrice
                  where t.houseIEEE=houseIEEE and t.IEEE=IEEE and t.EP=EP
                    and Date(t.opdatetime)=Date(BeginTimeTmp1) and Hour(t.opdatetime)=Hour(BeginTimeTmp1);
                end if;*/
              end if;
              FETCH Cur_Field into BeginEnergy, EndEnergy,iField;
            end WHILE;
            CLOSE Cur_Field;
            Set tmpDone=0;
            if EnergySumTmp+EnergyTmpTime>EndEnergy THEN
              /*Set EnergyTmpField=EnergySumTmp+EnergyTmpTime-EndEnergy;*/
              Set EnergyTmpField=EnergyTmpTime;

              Set @HouseIEEE=HouseIEEE;
              Set @IEEE=IEEE;
              Set @EP=EP;
              Set @BeginTimeTmp1=BeginTimeTmp1;
              Set @EnergyTmpField=EnergyTmpField;
              Set @EnergyPrice1=EnergyTmpField*EnergyPrice;

              Set @sSql=CONCAT('SELECT Count(*) into @EnergyCount FROM ',' EnergyHistory_',Year(BeginTimeHour),' t 
                  where t.houseIEEE=? and t.IEEE=? and t.EP=?
                    and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');
              PREPARE stmtselect FROM @sSql;
              EXECUTE stmtselect using @HouseIEEE,@IEEE,@EP,@BeginTimeTmp1,@BeginTimeTmp1;
              DEALLOCATE PREPARE stmtselect;
              Set EnergyCount=@EnergyCount;
              if EnergyCount=0 then 
                Set @sSql=CONCAT('insert into ',' EnergyHistory_',Year(BeginTimeHour),
                                 '(HouseIEEE,IEEE,EP,opDateTime,EnergyValue,EnergyPrice) values
                                         (?,?,?,?,?,?)');
              else 
                if I=aCount-1 and aCount>0 then
                    Set @sSql=CONCAT('update ',' EnergyHistory_',Year(BeginTimeHour),
                                     ' t set t.energyValue=IfNull(t.energyValue,0)+?,
                                             t.energyPrice=IfNull(t.energyPrice,0)+?
                  where t.houseIEEE=? and t.IEEE=? and t.EP=?
                    and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');
                  else
                    Set @sSql=CONCAT('update ',' EnergyHistory_',Year(BeginTimeHour),
                                     ' t set t.energyValue=?,
                                             t.energyPrice=?
                  where t.houseIEEE=? and t.IEEE=? and t.EP=?
                    and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');                 
                end if;                                  
              end if;
              
              PREPARE stmtselect FROM @sSql;
              if EnergyCount=0 then
                EXECUTE stmtselect using @HouseIEEE,@IEEE,@EP,@BeginTimeTmp1,@EnergyTmpField,@EnergyPrice1;
              else
                EXECUTE stmtselect using @EnergyTmpField,@EnergyPrice1,@HouseIEEE,@IEEE,@EP,@BeginTimeTmp1,@BeginTimeTmp1;
              end if;
              DEALLOCATE PREPARE stmtselect;
              /*SELECT Count(*) into EnergyCount FROM energyhistory t 
                  where t.houseIEEE=houseIEEE and t.IEEE=IEEE and t.EP=EP
                    and Date(t.opdatetime)=Date(BeginTimeTmp1) and Hour(t.opdatetime)=Hour(BeginTimeTmp1);
              if EnergyCount=0 then 
                insert into energyhistory(HouseIEEE,IEEE,EP,opDateTime,EnergyValue,EnergyPrice) values
                                         (HouseIEEE,IEEE,EP,BeginTimeTmp1,EnergyTmpField,EnergyTmpField*EnergyPrice);
              else 
                update energyhistory t set t.energyValue=t.energyValue+EnergyTmpField,
                                           t.energyPrice=t.energyPrice+EnergyTmpField*EnergyPrice
                where t.houseIEEE=houseIEEE and t.IEEE=IEEE and t.EP=EP
                  and Date(t.opdatetime)=Date(BeginTimeTmp1) and Hour(t.opdatetime)=Hour(BeginTimeTmp1);
              end if;*/
            end if;
          else
            SELECT case  when iDst=1 and BeginTimeTmp1>=DstBeginTime and BeginTimeTmp1<=DstEndTime 
                                  then t.PriceDst 
                             else t.price 
                       end into EnergyPrice
                  from priceparam t where t.houseIEEE=houseIEEE and t.energyTimeId=iTime;

            Set EnergyTmpField=EnergyTmpTime;
            Set @BeginTimeTmp1=BeginTimeTmp1;
            Set @EnergyTmpField=EnergyTmpField;
            Set @EnergyPrice1=EnergyTmpField*EnergyPrice;

            Set @sSql=CONCAT('SELECT Count(*) into @EnergyCount FROM ',' EnergyHistory_',Year(BeginTimeHour),' t 
                where t.houseIEEE=? and t.IEEE=? and t.EP=?
                  and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');
            PREPARE stmtselect FROM @sSql;
            EXECUTE stmtselect using @HouseIEEE,@IEEE,@EP,@BeginTimeTmp1,@BeginTimeTmp1;
            DEALLOCATE PREPARE stmtselect;
            Set EnergyCount=@EnergyCount;
            if EnergyCount=0 then 
              Set @sSql=CONCAT('insert into ',' EnergyHistory_',Year(BeginTimeHour),
                               '(HouseIEEE,IEEE,EP,opDateTime,EnergyValue,EnergyPrice) values
                                       (?,?,?,?,?,?)');
            else 
              if I=aCount-1 and aCount>0 then
                Set @sSql=CONCAT('update ',' EnergyHistory_',Year(BeginTimeHour),
                                 ' t set t.energyValue=IfNull(t.energyValue,0)+?,
                                         t.energyPrice=IfNull(t.energyPrice,0)+?
                  where t.houseIEEE=? and t.IEEE=? and t.EP=?
                    and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');
              else
                Set @sSql=CONCAT('update ',' EnergyHistory_',Year(BeginTimeHour),
                                 ' t set t.energyValue=?,
                                   t.energyPrice=?
                  where t.houseIEEE=? and t.IEEE=? and t.EP=?
                    and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');                
              end if;                                
            end if;
              
            PREPARE stmtselect FROM @sSql;
            if EnergyCount=0 then
              EXECUTE stmtselect using @HouseIEEE,@IEEE,@EP,@BeginTimeTmp1,@EnergyTmpField,@EnergyPrice1;
            else
              EXECUTE stmtselect using @EnergyTmpField,@EnergyPrice1,@HouseIEEE,@IEEE,@EP,@BeginTimeTmp1,@BeginTimeTmp1;
            end if;
            DEALLOCATE PREPARE stmtselect;
            
            /*SELECT Count(*) into EnergyCount FROM energyhistory t 
                where t.houseIEEE=houseIEEE and t.IEEE=IEEE and t.EP=EP
                  and Date(t.opdatetime)=Date(BeginTimeTmp1) and Hour(t.opdatetime)=Hour(BeginTimeTmp1);
            if EnergyCount=0 then 
              insert into energyhistory(HouseIEEE,IEEE,EP,opDateTime,EnergyValue,EnergyPrice) values
                                       (HouseIEEE,IEEE,EP,BeginTimeTmp1,EnergyTmpTime,EnergyTmpTime*EnergyPrice);
            else 
              update energyhistory t set t.energyValue=t.energyValue+EnergyTmpTime,
                                         t.energyPrice=t.energyPrice+EnergyTmpTime*EnergyPrice
              where t.houseIEEE=houseIEEE and t.IEEE=IEEE and t.EP=EP
                and Date(t.opdatetime)=Date(BeginTimeTmp1) and Hour(t.opdatetime)=Hour(BeginTimeTmp1);
            end if;*/            
          end if;
        end if;
        FETCH Cur_Time into BeginTimeTime,EndTimeTime,iTime;
      END WHILE;
      CLOSE Cur_Time;
    
      Set I=I+1;
    else        
      OPEN Cur_Field;
      FETCH Cur_Field into BeginEnergy, EndEnergy,iField;
      WHILE tmpDone<>1 DO
        Set iContinue=0;
        Set EnergyTmpField=0;
        if EnergySumTmp>=BeginEnergy and EnergySumTmp+EnergyTmpHour<=EndEnergy THEN
          Set EnergyTmpField=EnergyTmpHour;
          Set iContinue=1;
        ELSEIF EnergySumTmp>=BeginEnergy and EnergySumTmp<EndEnergy and EnergySumTmp+EnergyTmpHour>EndEnergy THEN
          Set EnergyTmpField=EndEnergy-EnergySumTmp;
          Set iContinue=2;
        ELSEIF EnergySumTmp<BeginEnergy and EnergySumTmp+EnergyTmpHour<=EndEnergy and EnergySumTmp+EnergyTmpHour>BeginEnergy THEN
          Set EnergyTmpField=EnergySumTmp+EnergyTmpHour-BeginEnergy;
          Set iContinue=3;
        ELSEIF EnergySumTmp<BeginEnergy and EnergySumTmp+EnergyTmpHour>EndEnergy THEN
          Set EnergyTmpField=EndEnergy-BeginEnergy;
          Set iContinue=4;
        ELSE
          Set iContinue=0;          
        end if;
        
        if iContinue<>0 then  
                  
          SELECT case  when iDst=1 and BeginTimeHour>=DstBeginTime and BeginTimeHour<=DstEndTime 
                            then t.PriceDst 
                       else t.price 
                 end into EnergyPrice
            from priceparam t where t.houseIEEE=houseIEEE and t.energyFieldId=iField;
          Set @HouseIEEE=HouseIEEE;
          Set @IEEE=IEEE;
          Set @EP=EP;
          Set @beginTimeHour=beginTimeHour;
          Set @EnergyTmpField=EnergyTmpField;
          Set @EnergyPrice1=EnergyTmpField*EnergyPrice;
          Set @sSql=CONCAT('SELECT Count(*) into @EnergyCount FROM ',' EnergyHistory_',Year(BeginTimeHour),' t 
              where t.houseIEEE=? and t.IEEE=? and t.EP=?
                and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');
          PREPARE stmtselect FROM @sSql;
          EXECUTE stmtselect using @HouseIEEE,@IEEE,@EP,@beginTimeHour,@beginTimeHour;
          DEALLOCATE PREPARE stmtselect;
          Set EnergyCount=@EnergyCount;
          if EnergyCount=0 then 
            Set @sSql=CONCAT('insert into ',' EnergyHistory_',Year(BeginTimeHour),
                             '(HouseIEEE,IEEE,EP,opDateTime,EnergyValue,EnergyPrice) values
                                     (?,?,?,?,?,?)');
          else 
            if (I=aCount-1) and (aCount>0) then
              Set @sSql=CONCAT('update ',' EnergyHistory_',Year(BeginTimeHour),
                               ' t set t.energyValue=IfNull(t.energyValue,0)+?,
                                       t.energyPrice=IfNull(t.energyPrice,0)+?
                where t.houseIEEE=? and t.IEEE=? and t.EP=?
                  and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');
            else
              Set @sSql=CONCAT('update ',' EnergyHistory_',Year(BeginTimeHour),
                               ' t set t.energyValue=?,
                                       t.energyPrice=?
                where t.houseIEEE=? and t.IEEE=? and t.EP=?
                  and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');              
            end if;
                              
          end if;
           
          PREPARE stmtselect FROM @sSql;
          if EnergyCount=0 then
            EXECUTE stmtselect using @HouseIEEE,@IEEE,@EP,@beginTimeHour,@EnergyTmpField,@EnergyPrice1;
          else
            EXECUTE stmtselect using @EnergyTmpField,@EnergyPrice1,@HouseIEEE,@IEEE,@EP,@beginTimeHour,@beginTimeHour;
          end if;
          DEALLOCATE PREPARE stmtselect;
          
          /*SELECT Count(*) into EnergyCount FROM energyhistory t 
            where t.houseIEEE=houseIEEE and t.IEEE=IEEE and t.EP=EP
              and Date(t.opdatetime)=Date(beginTimeHour) and Hour(t.opdatetime)=Hour(BeginTimeHour);
          
          if EnergyCount=0 then 
            insert into energyhistory(HouseIEEE,IEEE,EP,opDateTime,EnergyValue,EnergyPrice) values
                                     (HouseIEEE,IEEE,EP,BeginTimeHour,EnergyTmpField,EnergyTmpField*EnergyPrice);
          else 
            update energyhistory t set t.energyValue=t.energyValue+EnergyTmpField,
                                       t.energyPrice=t.energyPrice+EnergyTmpField*EnergyPrice
            where t.houseIEEE=houseIEEE and t.IEEE=IEEE and t.EP=EP
              and Date(t.opdatetime)=Date(BeginTimeHour) and Hour(t.opdatetime)=Hour(BeginTimeHour);
          end if;*/
        end if;
        FETCH Cur_Field into BeginEnergy, EndEnergy,iField;        
      end WHILE;
      CLOSE Cur_Field;
      
      if EnergySumTmp+EnergyTmpHour>EndEnergy THEN
        Set EnergyTmpField=EnergyTmpHour;
        /*Set EnergyTmpField=EnergySumTmp+EnergyTmpHour-EndEnergy;*/
        Set @HouseIEEE=HouseIEEE;
        Set @IEEE=IEEE;
        Set @EP=EP;
        Set @beginTimeHour=beginTimeHour;
        Set @EnergyTmpField=EnergyTmpField;
        Set @EnergyPrice1=EnergyTmpField*EnergyPrice;
        Set @sSql=CONCAT('SELECT Count(*) into @EnergyCount FROM ',' EnergyHistory_',Year(BeginTimeHour),' t 
            where t.houseIEEE=? and t.IEEE=? and t.EP=?
              and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');
        PREPARE stmtselect FROM @sSql;
        EXECUTE stmtselect using @HouseIEEE,@IEEE,@EP,@beginTimeHour,@beginTimeHour;
        DEALLOCATE PREPARE stmtselect;
        Set EnergyCount=@EnergyCount;
        if EnergyCount=0 then 
          Set @sSql=CONCAT('insert into ',' EnergyHistory_',Year(BeginTimeHour),
                           '(HouseIEEE,IEEE,EP,opDateTime,EnergyValue,EnergyPrice) values
                                   (?,?,?,?,?,?)');
        else 
          if I=aCount-1 and aCount>0 then
            Set @sSql=CONCAT('update ',' EnergyHistory_',Year(BeginTimeHour),
                             ' t set t.energyValue=IfNull(t.energyValue,0)+?,
                                     t.energyPrice=IfNull(t.energyPrice,0)+?
              where t.houseIEEE=? and t.IEEE=? and t.EP=?
                and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');
          else
            Set @sSql=CONCAT('update ',' EnergyHistory_',Year(BeginTimeHour),
                             ' t set t.energyValue=?,
                                   t.energyPrice=?
            where t.houseIEEE=? and t.IEEE=? and t.EP=?
              and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');              
          end if;
                            
        end if;
          
        PREPARE stmtselect FROM @sSql;
        if EnergyCount=0 then
          EXECUTE stmtselect using @HouseIEEE,@IEEE,@EP,@beginTimeHour,@EnergyTmpField,@EnergyPrice1;
        else
          EXECUTE stmtselect using @EnergyTmpField,@EnergyPrice1,@HouseIEEE,@IEEE,@EP,@beginTimeHour,@beginTimeHour;
        end if;
        DEALLOCATE PREPARE stmtselect;
        
        /*SELECT Count(*) into EnergyCount FROM energyhistory t 
            where t.houseIEEE=houseIEEE and t.IEEE=IEEE and t.EP=EP
              and Date(t.opdatetime)=Date(BeginTimeHour) and Hour(t.opdatetime)=Hour(BeginTimeHour);
          if EnergyCount=0 then 
            insert into energyhistory(HouseIEEE,IEEE,EP,opDateTime,EnergyValue,EnergyPrice) values
                                     (HouseIEEE,IEEE,EP,BeginTimeHour,EnergyTmpField,EnergyTmpField*EnergyPrice);
          else 
            update energyhistory t set t.energyValue=t.energyValue+EnergyTmpField,
                                       t.energyPrice=t.energyPrice+EnergyTmpField*EnergyPrice
            where t.houseIEEE=houseIEEE and t.IEEE=IEEE and t.EP=EP
              and Date(t.opdatetime)=Date(BeginTimeHour) and Hour(t.opdatetime)=Hour(BeginTimeHour);
          end if;*/
      end if;
      Set I=I+1;
    end if;
  end WHILE;
  
END Label_At_Start;
/*电能计算修改计算逻辑，不然计算出来的值和实际不符end-----------*/

/*权限管理---begin-----*/
ALTER TABLE reliprivilege ADD COLUMN privilege_name_en VARCHAR(30); #权限的英文名称
ALTER TABLE reliprivilege ADD COLUMN parent_id INT;	#权限的父节点,首节点为-1
ALTER TABLE reliprivilege ADD COLUMN is_leaf INT; #是否叶子节点,0为否,1为是
ALTER TABLE house ADD COLUMN client_id INT; #客户代码id

CREATE TABLE reliclient ( #客户代码表
id INT NOT NULL AUTO_INCREMENT,
client_code VARCHAR(30), #客户代码
region VARCHAR(30),	#区域
PRIMARY KEY(id)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE relirole ( #角色表
id INT NOT NULL AUTO_INCREMENT,
role_name VARCHAR(30), #角色名称
level_id INT, #级别ID
PRIMARY KEY(id)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE reliroleprivilege ( #角色与权限的关联表
id INT NOT NULL AUTO_INCREMENT,
role_id INT,
privilege_id INT,
PRIMARY KEY(id)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE reliroleclient ( #角色与客户代码的关联表
id INT NOT NULL AUTO_INCREMENT,
role_id INT,
client_id INT,
PRIMARY KEY(id)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE reliuserrole ( #用户与角色的关联表
id INT NOT NULL AUTO_INCREMENT,
user_id INT,
role_id INT,
PRIMARY KEY(id)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/*权限初始数据begin---------*/
DELETE FROM reliprivilege;
INSERT INTO reliprivilege(id, privilege_name, privilege_code, privilege_name_en, parent_id, is_leaf)
VALUES(1001, '奈伯思云平台',NULL,NULL,-1,0),
			(1002, '远程在线升级',NULL,NULL,1001,0),
(1003, '软体版本升级管理',NULL,NULL,1002,0),
(1004, '新增',NULL,NULL,1003,1),
(1005, '编辑',NULL,NULL,1003,1),
(1006, '删除',NULL,NULL,1003,1),
(1007, '硬件版本管理',NULL,NULL,1002,0),
(1008, '新增',NULL,NULL,1007,1),
(1009, '编辑',NULL,NULL,1007,1),
(1010, '删除',NULL,NULL,1007,1),

(1011, '家庭注册管理',NULL,NULL,1001,0),
(1012, '查看详细',NULL,NULL,1011,1),
(1013, '编辑',NULL,NULL,1011,1),
(1014, '删除',NULL,NULL,1011,1),
(1015, '注册',NULL,NULL,1011,1),
(1016, '手工注册',NULL,NULL,1011,1),
(1017, '批量注册',NULL,NULL,1011,1),
(1018, '批量迁移',NULL,NULL,1011,1),

(1019, '云端服务管理',NULL,NULL,1001,0),
(1020, '监控SHC服务',NULL,NULL,1019,0),
(1021, '开启',NULL,NULL,1020,1),
(1022, '关闭',NULL,NULL,1020,1),
(1023, '管理员邮箱设置',NULL,NULL,1020,1),
(1024, '默认邮箱设置',NULL,NULL,1020,1),
(1025, '监控日志',NULL,NULL,1020,1),
(1026, '浏览查看',NULL,NULL,1020,1),
(1027, '导出',NULL,NULL,1020,1),

(1028, '外出管理',NULL,NULL,1019,0),
(1029, '开启',NULL,NULL,1028,1),
(1030, '关闭',NULL,NULL,1028,1),

(1031, '报警录像存储',NULL,NULL,1019,0),
(1032, '开启',NULL,NULL,1031,1),
(1033, '关闭',NULL,NULL,1031,1),

(1034, 'Router鄰居間LQI数据监控',NULL,NULL,1019,0),
(1035, '开启',NULL,NULL,1034,1),
(1036, '关闭',NULL,NULL,1034,1),
(1037, 'LQI阈值设置',NULL,NULL,1034,1),
(1038, 'LQI开启检查时间',NULL,NULL,1034,1),


(1039, '设备是否在线信息推送到云端',NULL,NULL,1019,0),
(1040, '开启',NULL,NULL,1039,1),
(1041, '关闭',NULL,NULL,1039,1),

(1042, '广告网址推送',NULL,NULL,1019,0),
(1043, '管理',NULL,NULL,1042,1),
(1044, '设置',NULL,NULL,1042,1),

(1045, '短信邮件预警推送',NULL,NULL,1019,0),
(1046, '开启',NULL,NULL,1045,1),
(1047, '关闭',NULL,NULL,1045,1),
(1048, '发送系统消息 ',NULL,NULL,1045,1),

(1049, '可靠性平台',NULL,NULL,1001,0),
(1050, '家庭概要',NULL,NULL,1049,1),
(1051, '地图分布',NULL,NULL,1049,1),
(1052, '查看详细信息',NULL,NULL,1049,1),
(1053, '手工升级',NULL,NULL,1049,1),
(1054, 'APP查看和统计',NULL,NULL,1049,1),
(1055, '设备状态列表',NULL,NULL,1049,1),
(1056, '设备属性报告',NULL,NULL,1049,1),
(1057, '导出',NULL,NULL,1049,1),
(1058, '操作历史列表',NULL,NULL,1049,1),
(1059, '查看指定动作',NULL,NULL,1049,1),
(1060, '查看设备记录',NULL,NULL,1049,1),
(1061, '查看设备指定动作记录',NULL,NULL,1049,1),
(1062, '报警历史列表',NULL,NULL,1049,1),
(1063, '网络通信质量列',NULL,NULL,1049,1),
(1064, '监控日志列表表',NULL,NULL,1049,1),
(1065, '浏览查看',NULL,NULL,1049,1),
(1066, '导出',NULL,NULL,1049,1),

(1067, '短信/邮件设置',NULL,NULL,1049,0),
(1068, '预警电话',NULL,NULL,1067,1),
(1069, '添加',NULL,NULL,1067,1),
(1070, '显示 ',NULL,NULL,1067,1),
(1071, '删除',NULL,NULL,1067,1),
(1072, '预警邮箱',NULL,NULL,1067,1),
(1073, '添加 ',NULL,NULL,1067,1),
(1074, '显示',NULL,NULL,1067,1),
(1075, '删除',NULL,NULL,1067,1),

(1076, 'CSHC数据统计',NULL,NULL,1049,0),
(1077, '心跳统计',NULL,NULL,1076,1),
(1078, '浏览查看',NULL,NULL,1076,1),
(1079, '导出',NULL,NULL,1076,1),
(1080, '电池电压统计',NULL,NULL,1076,1),
(1081, '浏览查看',NULL,NULL,1076,1),
(1082, '导出',NULL,NULL,1076,1),
(1083, '电能相关统计',NULL,NULL,1076,1),
(1084, '浏览查看',NULL,NULL,1076,1),
(1085, '导出',NULL,NULL,1076,1),

(1086, '云端IR库管理',NULL,NULL,1001,0),
(1087, '导入',NULL,NULL,1086,1),
(1088, '认证',NULL,NULL,1086,1),
(1089, '编辑',NULL,NULL,1086,1),
(1090, '删除',NULL,NULL,1086,1),
(1091, '下载',NULL,NULL,1086,1),

(1092, 'Burnin管理',NULL,NULL,1001,0),
(1093, '管理列表',NULL,NULL,1092,1),
(1094, '添加',NULL,NULL,1092,1),
(1095, '开',NULL,NULL,1092,1),
(1096, '关',NULL,NULL,1092,1),
(1097, '初始化',NULL,NULL,1092,1),
(1098, '完成',NULL,NULL,1092,1),
(1099, '重新',NULL,NULL,1092,1),
(1100, '完整性',NULL,NULL,1092,1),
(1101, '异常停止列表',NULL,NULL,1092,1),

(1102, '用户管理',NULL,NULL,1001,0),
(1103, '新增',NULL,NULL,1102,1),
(1104, '编辑',NULL,NULL,1102,1),
(1105, '删除',NULL,NULL,1102,1),
(1106, '赋权',NULL,NULL,1102,1),

(1107, '服务器管理',NULL,NULL,1001,0),
(1108, '新增',NULL,NULL,1107,1),
(1109, '编辑',NULL,NULL,1107,1),
(1110, '删除',NULL,NULL,1107,1),
(1111, 'Burnin 登录',NULL,NULL,1107,1),

(1112, '短信历史记录',NULL,NULL,1001,0),
(1113, '导出',NULL,NULL,1112,1),

(1114, '客户代码管理',NULL,NULL,1001,0),
(1115, '新增',NULL,NULL,1114,1),
(1116, '编辑',NULL,NULL,1114,1),
(1117, '删除',NULL,NULL,1114,1),

(1118, '角色管理',NULL,NULL,1001,0),
(1119, '新增',NULL,NULL,1118,1),
(1120, '编辑',NULL,NULL,1118,1),
(1121, '删除',NULL,NULL,1118,1),

(1122, '生产',NULL,NULL,1001,1),
(1123, 'App版本管理',NULL,NULL,1002,0),
(1124, '新增',NULL,NULL,1123,1),
(1125, '修改',NULL,NULL,1123,1),
(1126, '删除',NULL,NULL,1123,1);

/*配置超级管理员账户，需要修改user_id*/
INSERT INTO relirole(id, role_name, level_id) VALUES(1, '管理员', 1);
INSERT INTO reliuserrole(id, user_id, role_id) VALUES(1,24,1);
INSERT INTO reliroleprivilege(id, role_id, privilege_id) SELECT NULL id, 1 role_id, id FROM reliprivilege WHERE is_leaf = 1;

/*自动根据已有的house生成其客户代码*/
DELETE FROM reliclient;
DELETE FROM reliroleclient;
INSERT INTO reliclient(id, client_code, region) SELECT DISTINCT NULL id, clientCode, regionCode FROM house;
/*权限初始数据end---------*/

/*更新家的客户代码*/
UPDATE house INNER JOIN reliclient ON house.clientCode = reliclient.client_code SET house.client_id = reliclient.id;

/*给权限加上序号*/
UPDATE reliprivilege SET privilege_name='监控日志列表' WHERE id = 1064;
UPDATE reliprivilege SET privilege_name='生产注册' WHERE id = 1122;
UPDATE reliprivilege SET privilege_name='权限管理' WHERE id = 1118;
ALTER TABLE reliprivilege ADD COLUMN order_index TINYINT;
UPDATE reliprivilege SET order_index = 0 WHERE id = 1001;
UPDATE reliprivilege SET order_index = 1 WHERE id = 1002;
UPDATE reliprivilege SET order_index = 2 WHERE id = 1011;
UPDATE reliprivilege SET order_index = 3 WHERE id = 1019;
UPDATE reliprivilege SET order_index = 4 WHERE id = 1049;
UPDATE reliprivilege SET order_index = 5 WHERE id = 1086;
UPDATE reliprivilege SET order_index = 6 WHERE id = 1092;
UPDATE reliprivilege SET order_index = 7 WHERE id = 1102;
UPDATE reliprivilege SET order_index = 8 WHERE id = 1122;
UPDATE reliprivilege SET order_index = 9 WHERE id = 1107;
UPDATE reliprivilege SET order_index = 10 WHERE id = 1112;
UPDATE reliprivilege SET order_index = 11 WHERE id = 1114;
UPDATE reliprivilege SET order_index = 12 WHERE id = 1118;

UPDATE reliprivilege SET order_index = 1 WHERE id = 1124;
UPDATE reliprivilege SET order_index = 2 WHERE id = 1125;
UPDATE reliprivilege SET order_index = 3 WHERE id = 1126;

UPDATE reliprivilege SET order_index = 1 WHERE id = 1003;
UPDATE reliprivilege SET order_index = 2 WHERE id = 1007;
UPDATE reliprivilege SET order_index = 3 WHERE id = 1123;
UPDATE reliprivilege SET order_index = 1 WHERE id = 1004;
UPDATE reliprivilege SET order_index = 2 WHERE id = 1005;
UPDATE reliprivilege SET order_index = 3 WHERE id = 1006;
UPDATE reliprivilege SET order_index = 1 WHERE id = 1008;
UPDATE reliprivilege SET order_index = 2 WHERE id = 1009;
UPDATE reliprivilege SET order_index = 3 WHERE id = 1010;

UPDATE reliprivilege SET order_index = 1 WHERE id = 1012;
UPDATE reliprivilege SET order_index = 2 WHERE id = 1013;
UPDATE reliprivilege SET order_index = 3 WHERE id = 1014;
UPDATE reliprivilege SET order_index = 4 WHERE id = 1015;
UPDATE reliprivilege SET order_index = 5 WHERE id = 1016;
UPDATE reliprivilege SET order_index = 6 WHERE id = 1017;
UPDATE reliprivilege SET order_index = 7 WHERE id = 1018;

UPDATE reliprivilege SET order_index = 1 WHERE id = 1020;
UPDATE reliprivilege SET order_index = 2 WHERE id = 1028;
UPDATE reliprivilege SET order_index = 3 WHERE id = 1031;
UPDATE reliprivilege SET order_index = 4 WHERE id = 1034;
UPDATE reliprivilege SET order_index = 5 WHERE id = 1039;
UPDATE reliprivilege SET order_index = 6 WHERE id = 1042;
UPDATE reliprivilege SET order_index = 7 WHERE id = 1045;

UPDATE reliprivilege SET order_index = 1 WHERE id = 1021;
UPDATE reliprivilege SET order_index = 2 WHERE id = 1022;
UPDATE reliprivilege SET order_index = 3 WHERE id = 1023;
UPDATE reliprivilege SET order_index = 4 WHERE id = 1024;
UPDATE reliprivilege SET order_index = 5 WHERE id = 1025;
UPDATE reliprivilege SET order_index = 6 WHERE id = 1026;
UPDATE reliprivilege SET order_index = 7 WHERE id = 1027;

UPDATE reliprivilege SET order_index = 1 WHERE id = 1029;
UPDATE reliprivilege SET order_index = 2 WHERE id = 1030;

UPDATE reliprivilege SET order_index = 1 WHERE id = 1032;
UPDATE reliprivilege SET order_index = 2 WHERE id = 1033;

UPDATE reliprivilege SET order_index = 1 WHERE id = 1035;
UPDATE reliprivilege SET order_index = 2 WHERE id = 1036;
UPDATE reliprivilege SET order_index = 3 WHERE id = 1037;
UPDATE reliprivilege SET order_index = 4 WHERE id = 1038;

UPDATE reliprivilege SET order_index = 1 WHERE id = 1040;
UPDATE reliprivilege SET order_index = 2 WHERE id = 1041;

UPDATE reliprivilege SET order_index = 1 WHERE id = 1043;
UPDATE reliprivilege SET order_index = 2 WHERE id = 1044;

UPDATE reliprivilege SET order_index = 1 WHERE id = 1046;
UPDATE reliprivilege SET order_index = 2 WHERE id = 1047;
UPDATE reliprivilege SET order_index = 3 WHERE id = 1048;

UPDATE reliprivilege SET order_index = 1 WHERE id = 1050;
UPDATE reliprivilege SET order_index = 2 WHERE id = 1051;
UPDATE reliprivilege SET order_index = 3 WHERE id = 1052;
UPDATE reliprivilege SET order_index = 4 WHERE id = 1053;
UPDATE reliprivilege SET order_index = 5 WHERE id = 1054;

UPDATE reliprivilege SET order_index = 1 WHERE id = 1055;
UPDATE reliprivilege SET order_index = 2 WHERE id = 1058;
UPDATE reliprivilege SET order_index = 3 WHERE id = 1062;
UPDATE reliprivilege SET order_index = 4 WHERE id = 1063;
UPDATE reliprivilege SET order_index = 5 WHERE id = 1064;
UPDATE reliprivilege SET order_index = 6 WHERE id = 1067;
UPDATE reliprivilege SET order_index = 7 WHERE id = 1076;

UPDATE reliprivilege SET order_index = 1 WHERE id = 1056;
UPDATE reliprivilege SET order_index = 2 WHERE id = 1057;

UPDATE reliprivilege SET order_index = 1 WHERE id = 1059;
UPDATE reliprivilege SET order_index = 2 WHERE id = 1060;
UPDATE reliprivilege SET order_index = 3 WHERE id = 1061;

UPDATE reliprivilege SET order_index = 1 WHERE id = 1065;
UPDATE reliprivilege SET order_index = 2 WHERE id = 1066;

UPDATE reliprivilege SET order_index = 1 WHERE id = 1068;
UPDATE reliprivilege SET order_index = 2 WHERE id = 1069;
UPDATE reliprivilege SET order_index = 3 WHERE id = 1070;
UPDATE reliprivilege SET order_index = 4 WHERE id = 1071;
UPDATE reliprivilege SET order_index = 5 WHERE id = 1072;
UPDATE reliprivilege SET order_index = 6 WHERE id = 1073;
UPDATE reliprivilege SET order_index = 7 WHERE id = 1074;
UPDATE reliprivilege SET order_index = 8 WHERE id = 1075;

UPDATE reliprivilege SET order_index = 1 WHERE id = 1077;
UPDATE reliprivilege SET order_index = 2 WHERE id = 1078;
UPDATE reliprivilege SET order_index = 3 WHERE id = 1079;
UPDATE reliprivilege SET order_index = 4 WHERE id = 1080;
UPDATE reliprivilege SET order_index = 5 WHERE id = 1081;
UPDATE reliprivilege SET order_index = 6 WHERE id = 1082;
UPDATE reliprivilege SET order_index = 7 WHERE id = 1083;
UPDATE reliprivilege SET order_index = 8 WHERE id = 1084;
UPDATE reliprivilege SET order_index = 9 WHERE id = 1085;

UPDATE reliprivilege SET order_index = 1 WHERE id = 1087;
UPDATE reliprivilege SET order_index = 2 WHERE id = 1088;
UPDATE reliprivilege SET order_index = 3 WHERE id = 1089;
UPDATE reliprivilege SET order_index = 4 WHERE id = 1090;
UPDATE reliprivilege SET order_index = 5 WHERE id = 1091;

UPDATE reliprivilege SET order_index = 1 WHERE id = 1093;
UPDATE reliprivilege SET order_index = 2 WHERE id = 1094;
UPDATE reliprivilege SET order_index = 3 WHERE id = 1095;
UPDATE reliprivilege SET order_index = 4 WHERE id = 1096;
UPDATE reliprivilege SET order_index = 5 WHERE id = 1097;
UPDATE reliprivilege SET order_index = 6 WHERE id = 1098;
UPDATE reliprivilege SET order_index = 7 WHERE id = 1099;
UPDATE reliprivilege SET order_index = 8 WHERE id = 1100;
UPDATE reliprivilege SET order_index = 9 WHERE id = 1101;

UPDATE reliprivilege SET order_index = 1 WHERE id = 1103;
UPDATE reliprivilege SET order_index = 2 WHERE id = 1104;
UPDATE reliprivilege SET order_index = 3 WHERE id = 1105;
UPDATE reliprivilege SET order_index = 4 WHERE id = 1106;

UPDATE reliprivilege SET order_index = 1 WHERE id = 1108;
UPDATE reliprivilege SET order_index = 2 WHERE id = 1109;
UPDATE reliprivilege SET order_index = 3 WHERE id = 1110;
UPDATE reliprivilege SET order_index = 4 WHERE id = 1111;

UPDATE reliprivilege SET order_index = 1 WHERE id = 1113;

UPDATE reliprivilege SET order_index = 1 WHERE id = 1115;
UPDATE reliprivilege SET order_index = 2 WHERE id = 1116;
UPDATE reliprivilege SET order_index = 3 WHERE id = 1117;

UPDATE reliprivilege SET order_index = 1 WHERE id = 1119;
UPDATE reliprivilege SET order_index = 2 WHERE id = 1120;
UPDATE reliprivilege SET order_index = 3 WHERE id = 1121;
/*根据level列出默认权限*/
UPDATE reliprivilege SET is_leaf = 0 WHERE id in (1058,1064,1055);
UPDATE reliprivilege SET parent_id = 1058 WHERE id in (1059,1060,1061);
UPDATE reliprivilege SET parent_id = 1064 WHERE id in (1065,1066);
UPDATE reliprivilege SET parent_id = 1055 WHERE id in (1056,1057);
DELETE FROM relilevelprivilege;
INSERT INTO relilevelprivilege(id, level_id, privilege_id) SELECT NULL id, 1 role_id, id FROM reliprivilege WHERE is_leaf = 1;
INSERT INTO relilevelprivilege(id, level_id, privilege_id)
VALUES(NULL, 3, 1012),(NULL, 3, 1013),(NULL, 3, 1015),(NULL, 3, 1016),(NULL, 3, 1017),
(NULL, 3, 1021),(NULL, 3, 1022),(NULL, 3, 1024),(NULL, 3, 1025),(NULL, 3, 1026),
(NULL, 3, 1027),(NULL, 3, 1029),(NULL, 3, 1030),(NULL, 3, 1032),(NULL, 3, 1033),
(NULL, 3, 1035),(NULL, 3, 1036),(NULL, 3, 1037),(NULL, 3, 1038),(NULL, 3, 1040),
(NULL, 3, 1041),(NULL, 3, 1043),(NULL, 3, 1044),(NULL, 3, 1046),(NULL, 3, 1047),
(NULL, 3, 1048),
(NULL, 3, 1052),(NULL, 3, 1053),(NULL, 3, 1054),(NULL, 3, 1056),
(NULL, 3, 1057),(NULL, 3, 1059),(NULL, 3, 1060),(NULL, 3, 1061),
(NULL, 3, 1062),(NULL, 3, 1063),(NULL, 3, 1065),(NULL, 3, 1066),
(NULL, 3, 1068),(NULL, 3, 1069),(NULL, 3, 1070),(NULL, 3, 1071),(NULL, 3, 1072),
(NULL, 3, 1073),(NULL, 3, 1074),(NULL, 3, 1075),(NULL, 3, 1077),(NULL, 3, 1078),
(NULL, 3, 1079),(NULL, 3, 1080),(NULL, 3, 1081),(NULL, 3, 1082),(NULL, 3, 1083),(NULL, 3, 1084),(NULL, 3, 1085);
INSERT INTO relilevelprivilege(id, level_id, privilege_id)
VALUES(NULL, 4, 1122),(NULL, 4, 1093),(NULL, 4, 1094),(NULL, 4, 1095),(NULL, 4, 1096),
(NULL, 4, 1097),(NULL, 4, 1098),(NULL, 4, 1099),(NULL, 4, 1100),(NULL, 4, 1101);
INSERT INTO relilevelprivilege(id, level_id, privilege_id)
VALUES(NULL, 2, 1003),(NULL, 2, 1007),(NULL, 2, 1123),(NULL, 2, 1012),
(NULL, 2, 1021),(NULL, 2, 1022),(NULL, 2, 1024),(NULL, 2, 1025),(NULL, 2, 1026),
(NULL, 2, 1027),(NULL, 2, 1029),(NULL, 2, 1030),(NULL, 2, 1032),(NULL, 2, 1033),
(NULL, 2, 1035),(NULL, 2, 1036),(NULL, 2, 1037),(NULL, 2, 1038),(NULL, 2, 1040),
(NULL, 2, 1041),(NULL, 2, 1043),(NULL, 2, 1044),(NULL, 2, 1046),(NULL, 2, 1047),
(NULL, 2, 1048),
(NULL, 2, 1052),(NULL, 2, 1054),(NULL, 2, 1056),
(NULL, 2, 1057),(NULL, 2, 1059),(NULL, 2, 1060),(NULL, 2, 1061),
(NULL, 2, 1062),(NULL, 2, 1063),(NULL, 2, 1065),(NULL, 2, 1066),
(NULL, 2, 1068),(NULL, 2, 1069),(NULL, 2, 1070),(NULL, 2, 1071),(NULL, 2, 1072),
(NULL, 2, 1073),(NULL, 2, 1074),(NULL, 2, 1075),(NULL, 2, 1077),(NULL, 2, 1078),
(NULL, 2, 1079),(NULL, 2, 1080),(NULL, 2, 1081),(NULL, 2, 1082),(NULL, 2, 1083),
(NULL, 2, 1084),(NULL, 2, 1085),(NULL, 2, 1086),(NULL, 2, 1051);
INSERT INTO relilevel(id,level_name) VALUES(5, '其他');
/*权限管理-----end-----*/
/*warnsend增加脚标*/
ALTER TABLE warnsend ADD COLUMN footer TINYINT DEFAULT 0;
/*权限管理可靠性平台的层级关系修改--begin--*/
DELETE FROM reliprivilege WHERE id = 1016;
DELETE FROM relilevelprivilege WHERE privilege_id = 1016;
DELETE FROM reliroleprivilege WHERE privilege_id = 1016;
UPDATE reliprivilege SET privilege_name = '注册新帐户' WHERE privilege_name = '注册';
UPDATE reliprivilege SET is_leaf = 0 WHERE id = 1015;
UPDATE reliprivilege SET parent_id = 1015,order_index = 1 WHERE id = 1017;
UPDATE reliprivilege SET order_index = 5 WHERE id = 1018;
UPDATE reliprivilege SET privilege_name = '网络通信质量列表' WHERE id = 1063;
UPDATE reliprivilege SET parent_id = 1050 WHERE id in (1051,1052,1053,1054);
UPDATE reliprivilege SET is_leaf = 0 WHERE id = 1050;
UPDATE reliprivilege SET order_index = 1 WHERE id = 1051;
UPDATE reliprivilege SET order_index = 2 WHERE id = 1052;
UPDATE reliprivilege SET order_index = 3 WHERE id = 1053;
UPDATE reliprivilege SET order_index = 4 WHERE id = 1054;
UPDATE reliprivilege SET order_index = order_index + 1 WHERE parent_id = 1049 AND id != 1050;
/*权限管理可靠性平台的层级关系修改--end--*/
/*权限层级关系修改--begin--*/
INSERT INTO relilevelprivilege(id, level_id, privilege_id) VALUES(NULL, 2, 1112),(NULL, 2, 1113),(NULL, 2, 1114),(NULL, 2, 1115),(NULL, 2, 1116),(NULL, 2, 1117);

DELETE FROM reliprivilege WHERE id = 1016;
DELETE FROM relilevelprivilege WHERE privilege_id = 1016;
DELETE FROM reliroleprivilege WHERE privilege_id = 1016;
UPDATE reliprivilege SET privilege_name = '注册新帐户' WHERE privilege_name = '注册';
UPDATE reliprivilege SET is_leaf = 0 WHERE id = 1015;
UPDATE reliprivilege SET parent_id = 1015,order_index = 1 WHERE id = 1017;
UPDATE reliprivilege SET order_index = 5 WHERE id = 1018;
UPDATE reliprivilege SET privilege_name = '网络通信质量列表' WHERE id = 1063;
UPDATE reliprivilege SET parent_id = 1050 WHERE id in (1051,1052,1053,1054);
UPDATE reliprivilege SET is_leaf = 0 WHERE id = 1050;
UPDATE reliprivilege SET order_index = 1 WHERE id = 1051;
UPDATE reliprivilege SET order_index = 2 WHERE id = 1052;
UPDATE reliprivilege SET order_index = 3 WHERE id = 1053;
UPDATE reliprivilege SET order_index = 4 WHERE id = 1054;
UPDATE reliprivilege SET order_index = order_index + 1 WHERE parent_id = 1049 AND id != 1050;

UPDATE reliprivilege SET parent_id = 1025 WHERE id IN (1026,1027);
UPDATE reliprivilege SET order_index = 1 WHERE id = 1026;
UPDATE reliprivilege SET order_index = 2 WHERE id = 1027;
UPDATE reliprivilege SET is_leaf = 0 WHERE id = 1025;
UPDATE reliprivilege SET parent_id = 1035 WHERE id IN (1037,1038);
UPDATE reliprivilege SET order_index = 1 WHERE id = 1037;
UPDATE reliprivilege SET order_index = 2 WHERE id = 1038;
UPDATE reliprivilege SET is_leaf = 0 WHERE id = 1035;
UPDATE reliprivilege SET parent_id = 1043 WHERE id IN (1044);
UPDATE reliprivilege SET order_index = 1 WHERE id = 1044;
UPDATE reliprivilege SET is_leaf = 0 WHERE id = 1043;

UPDATE reliprivilege SET parent_id = 1077 WHERE id IN (1078,1079);
UPDATE reliprivilege SET order_index = 1 WHERE id = 1078;
UPDATE reliprivilege SET order_index = 2 WHERE id = 1079;
UPDATE reliprivilege SET is_leaf = 0 WHERE id = 1077;
UPDATE reliprivilege SET parent_id = 1080 WHERE id IN (1081,1082);
UPDATE reliprivilege SET order_index = 1 WHERE id = 1081;
UPDATE reliprivilege SET order_index = 2 WHERE id = 1082;
UPDATE reliprivilege SET is_leaf = 0 WHERE id = 1080;
UPDATE reliprivilege SET parent_id = 1083 WHERE id IN (1084,1085);
UPDATE reliprivilege SET order_index = 1 WHERE id = 1084;
UPDATE reliprivilege SET order_index = 2 WHERE id = 1085;
UPDATE reliprivilege SET is_leaf = 0 WHERE id = 1083;
UPDATE reliprivilege SET order_index = 2 WHERE id = 1080;
UPDATE reliprivilege SET order_index = 3 WHERE id = 1083;

UPDATE reliprivilege SET parent_id = 1093 WHERE id IN (1094,1095,1096,1097,1098,1099,1100);
UPDATE reliprivilege SET order_index = 1 WHERE id = 1094;
UPDATE reliprivilege SET order_index = 2 WHERE id = 1095;
UPDATE reliprivilege SET order_index = 3 WHERE id = 1096;
UPDATE reliprivilege SET order_index = 4 WHERE id = 1097;
UPDATE reliprivilege SET order_index = 5 WHERE id = 1098;
UPDATE reliprivilege SET order_index = 6 WHERE id = 1099;
UPDATE reliprivilege SET order_index = 7 WHERE id = 1100;
UPDATE reliprivilege SET is_leaf = 0 WHERE id = 1093;
UPDATE reliprivilege SET order_index = 2 WHERE id = 1101;

UPDATE reliprivilege SET parent_id = 1068 WHERE id IN (1069,1070,1071);
UPDATE reliprivilege SET order_index = 1 WHERE id = 1069;
UPDATE reliprivilege SET order_index = 2 WHERE id = 1070;
UPDATE reliprivilege SET order_index = 3 WHERE id = 1071;
UPDATE reliprivilege SET is_leaf = 0 WHERE id = 1068;
UPDATE reliprivilege SET parent_id = 1072 WHERE id IN (1073,1074,1075);
UPDATE reliprivilege SET order_index = 1 WHERE id = 1073;
UPDATE reliprivilege SET order_index = 2 WHERE id = 1074;
UPDATE reliprivilege SET order_index = 3 WHERE id = 1075;
UPDATE reliprivilege SET is_leaf = 0 WHERE id = 1072;
UPDATE reliprivilege SET order_index = 2 WHERE id = 1072;

UPDATE reliprivilege SET parent_id = 1021 WHERE id IN (1023,1024);
UPDATE reliprivilege SET order_index = 1 WHERE id = 1023;
UPDATE reliprivilege SET order_index = 2 WHERE id = 1024;
UPDATE reliprivilege SET is_leaf = 0 WHERE id = 1021;
UPDATE reliprivilege SET order_index = 3 WHERE id = 1025;
/*权限层级关系修改--end--*/
/*warnsend*/
ALTER TABLE warnsend ADD COLUMN footer TINYINT DEFAULT 0;
/*补电价参数--begin--*/
#1.
INSERT INTO `energyreferencelibrary` (`id`, `name`, `priceType`, `DSTFlag`, `DSTbeginTime`, `DSTendTime`,`regionCode`, `syncFlag`, `enabledFlag`) VALUES ('10001', '非营业电价', '1', '1', '2015-06-01 00:00:00','2015-09-01 00:00:00', 'tw', '1', '1');

INSERT INTO `energyreferencelibrary` (`id`, `name`, `priceType`, `DSTFlag`, `DSTbeginTime`,`DSTendTime`, `regionCode`, `syncFlag`, `enabledFlag`) VALUES ('10002', '营业电价', '1', '1', '2015-06-01 00:00:00','2015-09-01 00:00:00', 'tw', '1', '1');

INSERT INTO `energyreferencelibrary` (`id`, `name`, `priceType`, `DSTFlag`, `DSTbeginTime`,`DSTendTime`, `regionCode`, `syncFlag`, `enabledFlag`) VALUES ('10003', '福利机构非营业电价', '1', '1', '2015-06-01 00:00:00','2015-09-01 00:00:00','tw', '1', '1');


INSERT INTO `energyreferencelibrary` (`id`, `name`, `priceType`, `DSTFlag`, `DSTbeginTime`, `DSTendTime`,`regionCode`, `syncFlag`, `enabledFlag`) VALUES ('10004', '公路照明灯电价', '1', '1', '2015-06-01 00:00:00','2015-09-01 00:00:00', 'tw', '1', '1');


INSERT INTO `energyreferencelibrary` (`id`, `name`, `priceType`, `DSTFlag`, `DSTbeginTime`, `DSTendTime`,`regionCode`, `syncFlag`, `enabledFlag`) VALUES ('10005', '福利机构营业电价', '1', '1', '2015-06-01 00:00:00','2015-09-01 00:00:00', 'tw', '1', '1');

#2.


INSERT INTO `energyfieldreferencelibrary` (`id`, `energyId`, `energyFieldId`, `name`, `startField`, `endField`) VALUES ('10001', '10001', '1', '分档1', '0', '120');


INSERT INTO `energyfieldreferencelibrary` (`id`, `energyId`, `energyFieldId`, `name`, `startField`, `endField`) VALUES ('10002', '10001', '2', '分档2', '120', '330');

INSERT INTO `energyfieldreferencelibrary` (`id`, `energyId`, `energyFieldId`, `name`, `startField`, `endField`) VALUES ('10003', '10001', '3', '分档3', '311', '500');

INSERT INTO `energyfieldreferencelibrary` (`id`, `energyId`, `energyFieldId`, `name`, `startField`, `endField`) VALUES ('10004', '10001', '4', '分档4', '501', '700');

INSERT INTO `energyfieldreferencelibrary` (`id`, `energyId`, `energyFieldId`, `name`, `startField`, `endField`) VALUES ('10005', '10001', '5', '分档5', '701', '1000');

INSERT INTO `energyfieldreferencelibrary` (`id`, `energyId`, `energyFieldId`, `name`, `startField`) VALUES ('10006', '10001', '6', '分档6', '1001');


INSERT INTO `energyfieldreferencelibrary` (`id`, `energyId`, `energyFieldId`, `name`, `startField`, `endField`) VALUES ('10007', '10002', '1', '分档1', '0', '330');

INSERT INTO `energyfieldreferencelibrary` (`id`, `energyId`, `energyFieldId`, `name`, `startField`, `endField`) VALUES ('10008', '10002', '2', '分档2', '331', '700');

INSERT INTO `energyfieldreferencelibrary` (`id`, `energyId`, `energyFieldId`, `name`, `startField`, `endField`) VALUES ('10009', '10002', '3', '分档3', '701', '1500');

INSERT INTO `energyfieldreferencelibrary` (`id`, `energyId`, `energyFieldId`, `name`, `startField`) VALUES ('10010', '10002', '4', '分档4', '1501');

INSERT INTO `energyfieldreferencelibrary` (`id`, `energyId`, `energyFieldId`, `name`, `startField`, `endField`) VALUES ('10011', '10003', '1', '分档1', '0', '120');

INSERT INTO `energyfieldreferencelibrary` (`id`, `energyId`, `energyFieldId`, `name`, `startField`, `endField`) VALUES ('10012', '10003', '2', '分档2', '120', '330');

INSERT INTO `energyfieldreferencelibrary` (`id`, `energyId`, `energyFieldId`, `name`, `startField`, `endField`) VALUES ('10013', '10003', '3', '分档3', '331', '500');


INSERT INTO `energyfieldreferencelibrary` (`id`, `energyId`, `energyFieldId`, `name`, `startField`, `endField`) VALUES ('10014', '10003', '4', '分档4', '501', '700');


INSERT INTO `energyfieldreferencelibrary` (`id`, `energyId`, `energyFieldId`, `name`, `startField`, `endField`) VALUES ('10015', '10003', '5', '分档5', '701', '1000');

INSERT INTO `energyfieldreferencelibrary` (`id`, `energyId`, `energyFieldId`, `name`, `startField`) VALUES ('10016', '10003', '6', '分档6', '1000');

INSERT INTO `energyfieldreferencelibrary` (`id`, `energyId`, `energyFieldId`, `name`, `startField`, `endField`) VALUES ('10017', '10004', '1', '分档1', '0', '120');


INSERT INTO `energyfieldreferencelibrary` (`id`, `energyId`, `energyFieldId`, `name`, `startField`, `endField`) VALUES ('10018', '10004', '2', '分档2', '120', '330');

INSERT INTO `energyfieldreferencelibrary` (`id`, `energyId`, `energyFieldId`, `name`, `startField`, `endField`) VALUES ('10019', '10004', '3', '分档3', '331', '500');


INSERT INTO `energyfieldreferencelibrary` (`id`, `energyId`, `energyFieldId`, `name`, `startField`, `endField`) VALUES ('10020', '10004', '4', '分档4', '501', '700');


INSERT INTO `energyfieldreferencelibrary` (`id`, `energyId`, `energyFieldId`, `name`, `startField`, `endField`) VALUES ('10021', '10004', '5', '分档5', '701', '1000');

INSERT INTO `energyfieldreferencelibrary` (`id`, `energyId`, `energyFieldId`, `name`, `startField`) VALUES ('10022', '10004', '6', '分档6', '1000');

INSERT INTO `energyfieldreferencelibrary` (`id`, `energyId`, `energyFieldId`, `name`, `startField`, `endField`) VALUES ('10023', '10005', '1', '分档1', '0', '330');

INSERT INTO `energyfieldreferencelibrary` (`id`, `energyId`, `energyFieldId`, `name`, `startField`, `endField`) VALUES ('10024', '10005', '2', '分档2', '331', '700');

INSERT INTO `energyfieldreferencelibrary` (`id`, `energyId`, `energyFieldId`, `name`, `startField`, `endField`) VALUES ('10025', '10005', '3', '分档3', '701', '1500');

INSERT INTO `energyfieldreferencelibrary` (`id`, `energyId`, `energyFieldId`, `name`, `startField`) VALUES ('10026', '10005', '4', '分档4', '1501');




#3.

INSERT INTO `priceparamreferencelibrary` (`id`, `energyId`, `energyFieldId`, `energyTimeId`, `name`, `price`,`priceDST`) VALUES ('10001', '10001', '1', '0', '分档1', '1.89','1.89');

INSERT INTO `priceparamreferencelibrary` (`id`, `energyId`, `energyFieldId`, `energyTimeId`, `name`, `price`,`priceDST`) VALUES ('10002', '10001', '2', '0', '分档2', '2.42','2.73');

INSERT INTO `priceparamreferencelibrary` (`id`, `energyId`, `energyFieldId`, `energyTimeId`, `name`, `price`,`priceDST`) VALUES ('10003', '10001', '3', '0', '分档3', '3.3','4');

INSERT INTO `priceparamreferencelibrary` (`id`, `energyId`, `energyFieldId`, `energyTimeId`, `name`, `price`,`priceDST`) VALUES ('10004', '10001', '4', '0', '分档4', '4.24','5.15');

INSERT INTO `priceparamreferencelibrary` (`id`, `energyId`, `energyFieldId`, `energyTimeId`, `name`, `price`,`priceDST`) VALUES ('10005', '10001', '5', '0', '分档5', '4.9','5.99');

INSERT INTO `priceparamreferencelibrary` (`id`, `energyId`, `energyFieldId`, `energyTimeId`, `name`, `price`,`priceDST`) VALUES ('10006', '10001', '6', '0', '分档6', '5.28','6.17');



INSERT INTO `priceparamreferencelibrary` (`id`, `energyId`, `energyFieldId`, `energyTimeId`, `name`, `price`,`priceDST`) VALUES ('10007', '10002', '1', '0', '分档1', '2.45','2.91');


INSERT INTO `priceparamreferencelibrary` (`id`, `energyId`, `energyFieldId`, `energyTimeId`, `name`, `price`,`priceDST`) VALUES ('10008', '10002', '2', '0', '分档2', '3.32','4.04');


INSERT INTO `priceparamreferencelibrary` (`id`, `energyId`, `energyFieldId`, `energyTimeId`, `name`, `price`,`priceDST`) VALUES ('10009', '10002', '3', '0', '分档3', '3.91','4.81');


INSERT INTO `priceparamreferencelibrary` (`id`, `energyId`, `energyFieldId`, `energyTimeId`, `name`, `price`,`priceDST`) VALUES ('10010', '10002', '4', '0', '分档4', '5.31','6.17');
INSERT INTO `priceparamreferencelibrary` (`id`, `energyId`, `energyFieldId`, `energyTimeId`, `name`, `price`,`priceDST`) VALUES ('10011', '10003', '1', '0', '分档1', '1.89','1.89');
INSERT INTO `priceparamreferencelibrary` (`id`, `energyId`, `energyFieldId`, `energyTimeId`, `name`, `price`,`priceDST`) VALUES ('10012', '10003', '2', '0', '分档2', '2.42','2.73');
INSERT INTO `priceparamreferencelibrary` (`id`, `energyId`, `energyFieldId`, `energyTimeId`, `name`, `price`,`priceDST`) VALUES ('10013', '10003', '3', '0', '分档3', '3.3','4');
INSERT INTO `priceparamreferencelibrary` (`id`, `energyId`, `energyFieldId`, `energyTimeId`, `name`, `price`,`priceDST`) VALUES ('10014', '10003', '4', '0', '分档4', '4.24','5.15');
INSERT INTO `priceparamreferencelibrary` (`id`, `energyId`, `energyFieldId`, `energyTimeId`, `name`, `price`,`priceDST`) VALUES ('10015', '10003', '5', '0', '分档5', '4.9','5.99');
INSERT INTO `priceparamreferencelibrary` (`id`, `energyId`, `energyFieldId`, `energyTimeId`, `name`, `price`,`priceDST`) VALUES ('10016', '10003', '6', '0', '分档6', '4.9','5.99');
INSERT INTO `priceparamreferencelibrary` (`id`, `energyId`, `energyFieldId`, `energyTimeId`, `name`, `price`,`priceDST`) VALUES ('10017', '10004', '1', '0', '分档1', '0.95','0.95');
INSERT INTO `priceparamreferencelibrary` (`id`, `energyId`, `energyFieldId`, `energyTimeId`, `name`, `price`,`priceDST`) VALUES ('10018', '10004', '2', '0', '分档2', '1.21','1.37');
INSERT INTO `priceparamreferencelibrary` (`id`, `energyId`, `energyFieldId`, `energyTimeId`, `name`, `price`,`priceDST`) VALUES ('10019', '10004', '3', '0', '分档3', '1.15','2');
INSERT INTO `priceparamreferencelibrary` (`id`, `energyId`, `energyFieldId`, `energyTimeId`, `name`, `price`,`priceDST`) VALUES ('10020', '10004', '4', '0', '分档4', '2.12','2.58');
INSERT INTO `priceparamreferencelibrary` (`id`, `energyId`, `energyFieldId`, `energyTimeId`, `name`, `price`,`priceDST`) VALUES ('10021', '10004', '5', '0', '分档5', '2.45','3');
INSERT INTO `priceparamreferencelibrary` (`id`, `energyId`, `energyFieldId`, `energyTimeId`, `name`, `price`,`priceDST`) VALUES ('10022', '10004', '6', '0', '分档6', '2.64','3.36');
INSERT INTO `priceparamreferencelibrary` (`id`, `energyId`, `energyFieldId`, `energyTimeId`, `name`, `price`,`priceDST`) VALUES ('10023', '10005', '1', '0', '分档1', '2.45','2.91');
INSERT INTO `priceparamreferencelibrary` (`id`, `energyId`, `energyFieldId`, `energyTimeId`, `name`, `price`,`priceDST`) VALUES ('10024', '10005', '2', '0', '分档2', '3.32','4.04');
INSERT INTO `priceparamreferencelibrary` (`id`, `energyId`, `energyFieldId`, `energyTimeId`, `name`, `price`,`priceDST`) VALUES ('10025', '10005', '3', '0', '分档3', '3.91','4.81');
INSERT INTO `priceparamreferencelibrary` (`id`, `energyId`, `energyFieldId`, `energyTimeId`, `name`, `price`,`priceDST`) VALUES ('10026', '10005', '4', '0', '分档4', '5.31','6.73');
#4.
INSERT INTO `region` (`id`, `regionCode`, `regionName`, `description`) VALUES ('10001', 'tw', '台湾', '台湾')

UPDATE `energyfieldreferencelibrary` SET `startField`='330' WHERE (`id`='10003');
UPDATE `energyfieldreferencelibrary` SET `startField`='500' WHERE (`id`='10004');
UPDATE `energyfieldreferencelibrary` SET `startField`='700' WHERE (`id`='10005');
UPDATE `energyfieldreferencelibrary` SET `startField`='1000' WHERE (`id`='10006');
UPDATE `energyfieldreferencelibrary` SET `startField`='330' WHERE (`id`='10008');
UPDATE `energyfieldreferencelibrary` SET `startField`='700' WHERE (`id`='10009');
UPDATE `energyfieldreferencelibrary` SET `startField`='1500' WHERE (`id`='10010');
UPDATE `energyfieldreferencelibrary` SET `startField`='330' WHERE (`id`='10013');
UPDATE `energyfieldreferencelibrary` SET `startField`='500' WHERE (`id`='10014');
UPDATE `energyfieldreferencelibrary` SET `startField`='700' WHERE (`id`='10015');
UPDATE `energyfieldreferencelibrary` SET `startField`='330' WHERE (`id`='10019');
UPDATE `energyfieldreferencelibrary` SET `startField`='500' WHERE (`id`='10020');
UPDATE `energyfieldreferencelibrary` SET `startField`='330' WHERE (`id`='10024');
UPDATE `energyfieldreferencelibrary` SET `startField`='700' WHERE (`id`='10021');
UPDATE `energyfieldreferencelibrary` SET `startField`='700' WHERE (`id`='10025');
UPDATE `energyfieldreferencelibrary` SET `startField`='1500' WHERE (`id`='10026');
UPDATE `priceparamreferencelibrary` SET `priceDST`='6.73' WHERE (`id`='10010');
UPDATE `energyfieldreferencelibrary` SET `endField`='9999' WHERE (`id`='10026');
UPDATE `energyfieldreferencelibrary` SET `endField`='9999' WHERE (`id`='10022');
UPDATE `energyfieldreferencelibrary` SET `endField`='9999' WHERE (`id`='10016');
UPDATE `energyfieldreferencelibrary` SET `endField`='9999' WHERE (`id`='10010');
UPDATE `energyfieldreferencelibrary` SET `endField`='9999' WHERE (`id`='10006');
UPDATE `priceparamreferencelibrary` SET `priceDST`='6.71' WHERE (`id`='10006');

/*--end--*/

/*电能统计会出现负数的情况--begin--*/
DROP PROCEDURE IF EXISTS `EnergyCalculateByYearEx`;
CREATE PROCEDURE `EnergyCalculateByYearEx`(IN HouseIEEE varchar(20),IN IEEE varchar(20),IN EP varchar(20),IN OpTime datetime,IN EnergyValue Double)
Label_At_Start: 
BEGIN
	#Routine body goes here... 

  DECLARE LastEnergy, EnergySum, EnergySumTmp, EnergyTmpHour,EnergyTmpTime, EnergyTmpField, 
          BeginEnergy, EndEnergy, EnergyPrice, EnergyPrice1 DOUBLE;
  DECLARE LastOpTime, CurrentTime, DstBeginTIme, DstEndTIme, 
          BeginTimeHour, EndTimeHour, BeginTimeTime, EndTimeTime, 
          BeginTimeTmp1, EndTimeTmp1 datetime;
  DECLARE LastOpEnergy DOUBLE;
  DECLARE LastOpDateTime datetime;

  DECLARE aCount, I, J, K, Year1, Month1, Day1, Hour1, Minute1,Sec1 INTEGER;
  DECLARE Year2, Month2, Day2, Hour2, Minute2, secDiff,Sec2, IsDst, StartFieldTmp, EndFieldTmp, 
          TmpDone, iContinue, iPriceType,iField, iTime, iDst, EnergyCount, iTable INTEGER;
  DECLARE sSql VARCHAR(4000);
  DECLARE Cur_Time CURSOR for Select t.beginTime,t.EndTime,t.EnergyTimeID from energytime t where t.HouseIEEE=HouseIEEE order by T.EnergyTimeID;    
  DECLARE Cur_Field CURSOR for Select t.StartField,t.EndField,t.EnergyFieldID from energyfield t where t.HouseIEEE=HouseIEEE order by T.EnergyFieldID;
  DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET TmpDone=1;


  SELECT Count(*) into aCount FROM energydeviceattribute t 
    WHERE t.HouseIEEE=HouseIEEE and t.Device_IEEE=IEEE AND t.Device_EP=EP and t.Cluster_ID='0702' and t.Attribute_ID='E003';
  
  if aCount <> 1  THEN 
    if aCount=0 THEN
      insert into energydeviceattribute
      (houseieee,device_ieee,device_ep,Cluster_ID,Attribute_ID,
       value,Lasttime,lastopvalue,lastoptime)
      values (HouseIEEE,IEEE,EP,'0702','E003',EnergyValue,
              DATE_SUB(DATE_SUB(OpTime,INTERVAL MINUTE(OpTime) MINUTE),
              INTERVAL SECOND(OpTime) SECOND),EnergyValue,OpTime);
    end if;
    LEAVE Label_At_Start;
  end if;


  SELECT t.Value,t.lasttime,t.lastopvalue,t.lastoptime into LastEnergy,LastOpTime,LastOpEnergy,LastOpDateTime
  FROM energydeviceattribute t 
    WHERE t.HouseIEEE=HouseIEEE and t.Device_IEEE=IEEE AND t.Device_EP=EP and t.Cluster_ID='0702' and t.Attribute_ID='E003';
  SELECT t.PriceType,t.DstFlag,t.DstbeginTIme,t.DstEndTime into iPriceType,iDst,DstbeginTime,dstEndTime from Energy t
    where t.HouseIEEE=HouseIEEE;
  
  Set CurrentTime=OpTime;
  if ((timediff(LastOpTime,CurrentTime)>=0) and (DATEDIFF(CurrentTime,LastOpTime)<=0)) or (LastEnergy>EnergyValue) THEN   
    LEAVE Label_At_Start;
  END if;
  
  /*Set secDiff=Hour(timediff(CurrentTime,LastOpTime));*/
  Set secDiff=DATEDIFF(CurrentTime,LastOpTime)*24+Hour(CurrentTime)-Hour(LastOpTime);
  
  if SecDiff>0 THEN
    set LastOpTime=LastOpDateTime;
    set LastEnergy=LastOpEnergy;
    Set secDiff=DATEDIFF(CurrentTime,LastOpTime)*24+Hour(CurrentTime)-Hour(LastOpTime);
  end IF;

  if (MINUTE(timediff(CurrentTime,LastOpTime))>0) or (SECOND(timediff(CurrentTime,LastOpTime))>0) THEN
    Set secDiff=secDiff+1;
  end if;

  if secDiff>=1 THEN
    if ((EnergyValue-LastEnergy)/secDiff>10) THEN
      LEAVE Label_At_Start;
    end IF;
  end IF;

  /*Set secDiff=Hour(timediff(CurrentTime,LastOpTime))*3600+MINUTE(timediff(CurrentTime,LastOpTime))*60+SECOND(timediff(CurrentTime,LastOpTime));*/
  Set secDiff=(DATEDIFF(CurrentTime,LastOpTime)*24+Hour(CurrentTime)-Hour(LastOpTime))*3600+
              (MINUTE(CurrentTime)-MINUTE(LastOpTime))*60+SECOND(CurrentTime)-SECOND(LastOpTime);
  
  Set Year1=YEAR(LastOpTime),Month1=Month(LastOpTime), Day1=DAY(LastOpTime), 
      Hour1=Hour(LastOpTime), Minute1=Minute(LastOpTime),Sec1=Second(LastOpTime);
  Set Year2=YEAR(CurrentTime),Month2=Month(CurrentTime), Day2=DAY(CurrentTime), 
      Hour2=Hour(CurrentTime), Minute2=Minute(CurrentTime), Sec2=Second(CurrentTIme);
  Set I=0, aCount=DATEDIFF(CurrentTime,LastOpTime)*24+Hour2-Hour1;
  Set BeginTimeHour=LastOpTime, EndTimeHour=LastOpTime; 
  /*Set secDiff=secDiff+aCount*3600; */
 
  WHILE I<=aCount DO    
    IF I=0 THEN
      if i=aCount then
        Set EnergyTmpHour=(EnergyValue-LastEnergy)*((Minute2-Minute1)*60+Sec2-Sec1)/SecDiff;
        select date_add(EndTimeHour, interval Minute2-Minute1 MINUTE) into EndTimeHour;
        select date_add(EndTimeHour, interval Sec2-Sec1 SECOND) into EndTimeHour; 
        update energydeviceattribute t 
          set t.lastoptime=CurrentTime,
              t.LastopValue=EnergyValue
        WHERE t.HouseIEEE=HouseIEEE and t.Device_IEEE=IEEE AND t.Device_EP=EP and t.Cluster_ID='0702' and t.Attribute_ID='E003';     
      ELSE
        Set EnergyTmpHour=(EnergyValue-LastEnergy)*((60-Minute1)*60-Sec1)/SecDiff;
        select date_add(EndTimeHour, interval 60-Minute1-1 MINUTE) into EndTimeHour;
        select date_add(EndTimeHour, interval 60-Sec1-1 SECOND) into EndTimeHour;
      end if;
    elseif I=aCount THEN
      Set EnergyTmpHour=(EnergyValue-LastEnergy)*(Minute2*60+Sec2)/SecDiff;      
      set BeginTimeHour=date_add(EndTimeHour, interval 1 SECOND); 
      set EndTimeHour=CurrentTime;
      if aCount>0 THEN
        update energydeviceattribute t 
          set t.lasttime=DATE_SUB(DATE_SUB(OpTime,INTERVAL MINUTE(OpTime) MINUTE),INTERVAL SECOND(OpTime) SECOND),
              t.Value=EnergyValue-EnergyTmpHour,
              t.lastoptime=CurrentTime,
              t.LastopValue=EnergyValue
        WHERE t.HouseIEEE=HouseIEEE and t.Device_IEEE=IEEE AND t.Device_EP=EP and t.Cluster_ID='0702' and t.Attribute_ID='E003';

      ELSE
        update energydeviceattribute t 
          set t.lastoptime=CurrentTime,
              t.LastopValue=EnergyValue
        WHERE t.HouseIEEE=HouseIEEE and t.Device_IEEE=IEEE AND t.Device_EP=EP and t.Cluster_ID='0702' and t.Attribute_ID='E003';
      end if;      
    ELSE      
      Set EnergyTmpHour=(EnergyValue-LastEnergy)*3600/SecDiff;
      set BeginTimeHour=date_add(EndTimeHour, interval 1 SECOND);
      select date_add(EndTimeHour, interval 1 HOUR) into EndTimeHour; 
    End if;
    
    /*SELECT IFNULL(Sum(T.EnergyValue),0) into EnergySum from EnergyHistory t 
      WHERE t.HouseIEEE=HouseIEEE and t.IEEE=IEEE AND t.EP=EP
        and Year(t.opdatetime)=Year(BeginTimeHour) and Month(t.opdatetime)=Month(BeginTimeHour);*/
    
    Select Count(*) into iTable from information_schema.TABLES t where  t.Table_Name=CONCAT('EnergyHistory_',Year(BeginTimeHour));
    if iTable=0 THEN
      Set @sSql=CONCAT('create  TABLE EnergyHistory_',Year(BeginTimeHour),' like energyhistory');
      PREPARE stmtselect FROM @sSql;
      EXECUTE stmtselect;
      DEALLOCATE PREPARE stmtselect;
    end if;
    Set @HouseIEEE=HouseIEEE;
    Set @IEEE=IEEE;
    Set @EP=EP;
    Set @BeginTimeHour=BeginTimeHour;
    set @sSql=CONCAT('SELECT IFNULL(Sum(T.EnergyValue),0) into @EnergySum from ','EnergyHistory_',Year(BeginTimeHour),' t 
      WHERE t.HouseIEEE=? and t.IEEE=? AND t.EP=?
        and Year(t.opdatetime)=Year(?) and Month(t.opdatetime)=Month(?);');
    PREPARE stmtselect FROM @sSql;
    EXECUTE stmtselect USING @HouseIEEE,@IEEE,@EP,@BeginTimeHour,@BeginTimeHour;
    DEALLOCATE PREPARE stmtselect;
    set EnergySum=@EnergySum;
    Set EnergySumTmp=EnergySum;
    Set TmpDone=0;
    
    if iPriceType>1 then
      OPEN Cur_Time;
      FETCH Cur_Time into BeginTimeTime,EndTimeTime,iTime;
      
      while TmpDone<>1 DO
        Set iContinue=0; 

        if Time(BeginTimeHour)>=Time(BeginTimeTime) and TIme(EndTimeHour)<=Time(EndTimeTime) THEN
          set BeginTimeTmp1=BeginTimeHour;
          set EndTimeTmp1=EndTimeHour;
        ELSEIF Time(BeginTimeHour)>=Time(BeginTimeTime) and Time(EndTimeHour)>=Time(EndTimeTime) and Time(BeginTimeHour)<Time(EndTimeTime) THEN
          Set BeginTimeTmp1=BeginTimeHour;
          Set EndTimeTmp1=DATE_ADD(Date(EndTimeHour),INTERVAL Time(EndTimeTime) HOUR_SECOND);
        ELSEIF Time(BeginTimeHour)<Time(BeginTimeTime) and Time(EndTimeHour)<Time(EndTimeTime) and Time(EndTimeHour)>Time(BeginTimeTime) THEN
          Set BeginTimeTmp1=DATE_ADD(Date(EndTimeHour),INTERVAL Time(BeginTimeTime) HOUR_SECOND);
          Set EndTimeTmp1=DATE_ADD(Date(EndTimeHour),INTERVAL Time(EndTimeTime) HOUR_SECOND);
        ELSEIF Time(BeginTimeHour)<Time(BeginTimeTime) and Time(EndTimeHour)>Time(EndTimeTime) THEN
          Set BeginTimeTmp1=DATE_ADD(Date(EndTimeHour),INTERVAL Time(BeginTimeTime) HOUR_SECOND);
          Set EndTimeTmp1=DATE_ADD(Date(EndTimeHour),INTERVAL Time(EndTimeTime) HOUR_SECOND);
        ELSE
          Set iContinue=1;          
        end if;  
        if Time(BeginTimeTime)>Time(EndTimeTime) THEN
          set iContinue=1;
        end if;
        if EnergyTmpHour<0 THEN
          Set iContinue=1;
        end if;
        if iContinue=0 THEN
          set EnergyTmpTime=EnergyTmpHour*((Minute(EndTimeTmp1)-Minute(BeginTimeTmp1))*60+Second(EndTimeTmp1)-Second(BeginTimeTmp1))/((Minute(EndTimeHour)-Minute(BeginTimeHour))*60+Second(EndTimeHour)-Second(BeginTimeHour));
          
          if iPriceType=3 THEN
            OPEN Cur_Field;
            FETCH Cur_Field into BeginEnergy, EndEnergy,iField;
            WHILE tmpDone<>1 DO
              Set iContinue=0;
              Set EnergyTmpField=0;
              if EnergySumTmp>=BeginEnergy and EnergySumTmp+EnergyTmpTime<=EndEnergy THEN
                Set EnergyTmpField=EnergyTmpTime;
              ELSEIF EnergySumTmp>=BeginEnergy and EnergySumTmp<EndEnergy and EnergySumTmp+EnergyTmpTime>EndEnergy THEN
                Set EnergyTmpField=EndEnergy-EnergySumTmp;
              ELSEIF EnergySumTmp<BeginEnergy and EnergySumTmp+EnergyTmpTime<=EndEnergy and EnergySumTmp+EnergyTmpTIme>BeginEnergy THEN
                Set EnergyTmpField=EnergySumTmp+EnergyTmpTime-BeginEnergy;
              ELSEIF EnergySumTmp<BeginEnergy and EnergySumTmp+EnergyTmpTime>EndEnergy THEN
                Set EnergyTmpField=EndEnergy-BeginEnergy;
              ELSE
                Set iContinue=1;
              end if;
              if EnergyTmpField<0 THEN
                Set iContinue=1;
              end if;
              if iContinue=0 then
                SELECT case  when iDst=1 and BeginTimeTmp1>=DstBeginTime and BeginTimeTmp1<=DstEndTime 
                                  then t.PriceDst 
                             else t.price 
                       end into EnergyPrice
                  from priceparam t where t.houseIEEE=houseIEEE and t.energyTimeId=iTime and t.energyFieldId=iField;
                
                Set @BeginTimeTmp1=BeginTimeTmp1;
                Set @EnergyTmpField=EnergyTmpField;
                Set @EnergyPrice1=EnergyTmpField*EnergyPrice;
                select @BeginTimeTmp1,@EnergyTmpField,@EnergyPrice1,iField,iTime;
                Set @sSql=CONCAT('SELECT Count(*) into @EnergyCount FROM ',' EnergyHistory_',Year(BeginTimeHour),' t 
                  where t.houseIEEE=? and t.IEEE=? and t.EP=?
                    and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');
                PREPARE stmtselect FROM @sSql;
                EXECUTE stmtselect using @HouseIEEE,@IEEE,@EP,@BeginTimeTmp1,@BeginTimeTmp1;
                DEALLOCATE PREPARE stmtselect;
                Set EnergyCount=@EnergyCount;
                if EnergyCount=0 then 
                  Set @sSql=CONCAT('insert into ',' EnergyHistory_',Year(BeginTimeHour),
                                   '(HouseIEEE,IEEE,EP,opDateTime,EnergyValue,EnergyPrice) values
                                           (?,?,?,?,?,?)');
                else 
                  if I=aCount-1 and aCount>0 then
                    Set @sSql=CONCAT('update ',' EnergyHistory_',Year(BeginTimeHour),
                                     ' t set t.energyValue=IfNull(t.energyValue,0)+?,
                                             t.energyPrice=IfNull(t.energyPrice,0)+?
                    where t.houseIEEE=? and t.IEEE=? and t.EP=?
                      and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');
                  else
                    Set @sSql=CONCAT('update ',' EnergyHistory_',Year(BeginTimeHour),
                                     ' t set t.energyValue=?,
                                               t.energyPrice=?
                    where t.houseIEEE=? and t.IEEE=? and t.EP=?
                      and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');                  
                  end if;
                end if;
                
                PREPARE stmtselect FROM @sSql;
                if EnergyCount=0 then
                  EXECUTE stmtselect using @HouseIEEE,@IEEE,@EP,@BeginTimeTmp1,@EnergyTmpField,@EnergyPrice1;
                else
                  EXECUTE stmtselect using @EnergyTmpField,@EnergyPrice1,@HouseIEEE,@IEEE,@EP,@BeginTimeTmp1,@BeginTimeTmp1;
                end if;
                DEALLOCATE PREPARE stmtselect;
                /*SELECT Count(*) into EnergyCount FROM energyhistory t 
                  where t.houseIEEE=houseIEEE and t.IEEE=IEEE and t.EP=EP
                    and Date(t.opdatetime)=Date(BeginTimeTmp1) and Hour(t.opdatetime)=Hour(BeginTimeTmp1);
                if EnergyCount=0 then 
                  insert into energyhistory(HouseIEEE,IEEE,EP,opDateTime,EnergyValue,EnergyPrice) values
                                           (HouseIEEE,IEEE,EP,BeginTimeTmp1,EnergyTmpField,EnergyTmpField*EnergyPrice);
                else 
                  update energyhistory t set t.energyValue=t.energyValue+EnergyTmpField,
                                             t.energyPrice=t.energyPrice+EnergyTmpField*EnergyPrice
                  where t.houseIEEE=houseIEEE and t.IEEE=IEEE and t.EP=EP
                    and Date(t.opdatetime)=Date(BeginTimeTmp1) and Hour(t.opdatetime)=Hour(BeginTimeTmp1);
                end if;*/
              end if;
              FETCH Cur_Field into BeginEnergy, EndEnergy,iField;
            end WHILE;
            CLOSE Cur_Field;
            Set tmpDone=0;
            if EnergySumTmp+EnergyTmpTime>EndEnergy THEN
              /*Set EnergyTmpField=EnergySumTmp+EnergyTmpTime-EndEnergy;*/
              Set EnergyTmpField=EnergyTmpTime;

              Set @HouseIEEE=HouseIEEE;
              Set @IEEE=IEEE;
              Set @EP=EP;
              Set @BeginTimeTmp1=BeginTimeTmp1;
              Set @EnergyTmpField=EnergyTmpField;
              Set @EnergyPrice1=EnergyTmpField*EnergyPrice;

              Set @sSql=CONCAT('SELECT Count(*) into @EnergyCount FROM ',' EnergyHistory_',Year(BeginTimeHour),' t 
                  where t.houseIEEE=? and t.IEEE=? and t.EP=?
                    and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');
              PREPARE stmtselect FROM @sSql;
              EXECUTE stmtselect using @HouseIEEE,@IEEE,@EP,@BeginTimeTmp1,@BeginTimeTmp1;
              DEALLOCATE PREPARE stmtselect;
              Set EnergyCount=@EnergyCount;
              if EnergyCount=0 then 
                Set @sSql=CONCAT('insert into ',' EnergyHistory_',Year(BeginTimeHour),
                                 '(HouseIEEE,IEEE,EP,opDateTime,EnergyValue,EnergyPrice) values
                                         (?,?,?,?,?,?)');
              else 
                if I=aCount-1 and aCount>0 then
                    Set @sSql=CONCAT('update ',' EnergyHistory_',Year(BeginTimeHour),
                                     ' t set t.energyValue=IfNull(t.energyValue,0)+?,
                                             t.energyPrice=IfNull(t.energyPrice,0)+?
                  where t.houseIEEE=? and t.IEEE=? and t.EP=?
                    and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');
                  else
                    Set @sSql=CONCAT('update ',' EnergyHistory_',Year(BeginTimeHour),
                                     ' t set t.energyValue=?,
                                             t.energyPrice=?
                  where t.houseIEEE=? and t.IEEE=? and t.EP=?
                    and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');                 
                end if;                                  
              end if;
              
              PREPARE stmtselect FROM @sSql;
              if EnergyCount=0 then
                EXECUTE stmtselect using @HouseIEEE,@IEEE,@EP,@BeginTimeTmp1,@EnergyTmpField,@EnergyPrice1;
              else
                EXECUTE stmtselect using @EnergyTmpField,@EnergyPrice1,@HouseIEEE,@IEEE,@EP,@BeginTimeTmp1,@BeginTimeTmp1;
              end if;
              DEALLOCATE PREPARE stmtselect;
              /*SELECT Count(*) into EnergyCount FROM energyhistory t 
                  where t.houseIEEE=houseIEEE and t.IEEE=IEEE and t.EP=EP
                    and Date(t.opdatetime)=Date(BeginTimeTmp1) and Hour(t.opdatetime)=Hour(BeginTimeTmp1);
              if EnergyCount=0 then 
                insert into energyhistory(HouseIEEE,IEEE,EP,opDateTime,EnergyValue,EnergyPrice) values
                                         (HouseIEEE,IEEE,EP,BeginTimeTmp1,EnergyTmpField,EnergyTmpField*EnergyPrice);
              else 
                update energyhistory t set t.energyValue=t.energyValue+EnergyTmpField,
                                           t.energyPrice=t.energyPrice+EnergyTmpField*EnergyPrice
                where t.houseIEEE=houseIEEE and t.IEEE=IEEE and t.EP=EP
                  and Date(t.opdatetime)=Date(BeginTimeTmp1) and Hour(t.opdatetime)=Hour(BeginTimeTmp1);
              end if;*/
            end if;
          else
            SELECT case  when iDst=1 and BeginTimeTmp1>=DstBeginTime and BeginTimeTmp1<=DstEndTime 
                                  then t.PriceDst 
                             else t.price 
                       end into EnergyPrice
                  from priceparam t where t.houseIEEE=houseIEEE and t.energyTimeId=iTime;

            Set EnergyTmpField=EnergyTmpTime;
            Set @BeginTimeTmp1=BeginTimeTmp1;
            Set @EnergyTmpField=EnergyTmpField;
            Set @EnergyPrice1=EnergyTmpField*EnergyPrice;

            Set @sSql=CONCAT('SELECT Count(*) into @EnergyCount FROM ',' EnergyHistory_',Year(BeginTimeHour),' t 
                where t.houseIEEE=? and t.IEEE=? and t.EP=?
                  and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');
            PREPARE stmtselect FROM @sSql;
            EXECUTE stmtselect using @HouseIEEE,@IEEE,@EP,@BeginTimeTmp1,@BeginTimeTmp1;
            DEALLOCATE PREPARE stmtselect;
            Set EnergyCount=@EnergyCount;
            if EnergyCount=0 then 
              Set @sSql=CONCAT('insert into ',' EnergyHistory_',Year(BeginTimeHour),
                               '(HouseIEEE,IEEE,EP,opDateTime,EnergyValue,EnergyPrice) values
                                       (?,?,?,?,?,?)');
            else 
              if I=aCount-1 and aCount>0 then
                Set @sSql=CONCAT('update ',' EnergyHistory_',Year(BeginTimeHour),
                                 ' t set t.energyValue=IfNull(t.energyValue,0)+?,
                                         t.energyPrice=IfNull(t.energyPrice,0)+?
                  where t.houseIEEE=? and t.IEEE=? and t.EP=?
                    and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');
              else
                Set @sSql=CONCAT('update ',' EnergyHistory_',Year(BeginTimeHour),
                                 ' t set t.energyValue=?,
                                   t.energyPrice=?
                  where t.houseIEEE=? and t.IEEE=? and t.EP=?
                    and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');                
              end if;                                
            end if;
              
            PREPARE stmtselect FROM @sSql;
            if EnergyCount=0 then
              EXECUTE stmtselect using @HouseIEEE,@IEEE,@EP,@BeginTimeTmp1,@EnergyTmpField,@EnergyPrice1;
            else
              EXECUTE stmtselect using @EnergyTmpField,@EnergyPrice1,@HouseIEEE,@IEEE,@EP,@BeginTimeTmp1,@BeginTimeTmp1;
            end if;
            DEALLOCATE PREPARE stmtselect;
            
            /*SELECT Count(*) into EnergyCount FROM energyhistory t 
                where t.houseIEEE=houseIEEE and t.IEEE=IEEE and t.EP=EP
                  and Date(t.opdatetime)=Date(BeginTimeTmp1) and Hour(t.opdatetime)=Hour(BeginTimeTmp1);
            if EnergyCount=0 then 
              insert into energyhistory(HouseIEEE,IEEE,EP,opDateTime,EnergyValue,EnergyPrice) values
                                       (HouseIEEE,IEEE,EP,BeginTimeTmp1,EnergyTmpTime,EnergyTmpTime*EnergyPrice);
            else 
              update energyhistory t set t.energyValue=t.energyValue+EnergyTmpTime,
                                         t.energyPrice=t.energyPrice+EnergyTmpTime*EnergyPrice
              where t.houseIEEE=houseIEEE and t.IEEE=IEEE and t.EP=EP
                and Date(t.opdatetime)=Date(BeginTimeTmp1) and Hour(t.opdatetime)=Hour(BeginTimeTmp1);
            end if;*/            
          end if;
        end if;
        FETCH Cur_Time into BeginTimeTime,EndTimeTime,iTime;
      END WHILE;
      CLOSE Cur_Time;
    
      Set I=I+1;
    else        
      OPEN Cur_Field;
      FETCH Cur_Field into BeginEnergy, EndEnergy,iField;
      WHILE tmpDone<>1 DO
        Set iContinue=0;
        Set EnergyTmpField=0;
        if EnergySumTmp>=BeginEnergy and EnergySumTmp+EnergyTmpHour<=EndEnergy THEN
          Set EnergyTmpField=EnergyTmpHour;
          Set iContinue=1;
        ELSEIF EnergySumTmp>=BeginEnergy and EnergySumTmp<EndEnergy and EnergySumTmp+EnergyTmpHour>EndEnergy THEN
          Set EnergyTmpField=EndEnergy-EnergySumTmp;
          Set iContinue=2;
        ELSEIF EnergySumTmp<BeginEnergy and EnergySumTmp+EnergyTmpHour<=EndEnergy and EnergySumTmp+EnergyTmpHour>BeginEnergy THEN
          Set EnergyTmpField=EnergySumTmp+EnergyTmpHour-BeginEnergy;
          Set iContinue=3;
        ELSEIF EnergySumTmp<BeginEnergy and EnergySumTmp+EnergyTmpHour>EndEnergy THEN
          Set EnergyTmpField=EndEnergy-BeginEnergy;
          Set iContinue=4;
        ELSE
          Set iContinue=0;          
        end if;
        if EnergyTmpField<0 THEN
          Set iContinue=0;
        end if;
        if iContinue<>0 then  
                  
          SELECT case  when iDst=1 and BeginTimeHour>=DstBeginTime and BeginTimeHour<=DstEndTime 
                            then t.PriceDst 
                       else t.price 
                 end into EnergyPrice
            from priceparam t where t.houseIEEE=houseIEEE and t.energyFieldId=iField;
          Set @HouseIEEE=HouseIEEE;
          Set @IEEE=IEEE;
          Set @EP=EP;
          Set @beginTimeHour=beginTimeHour;
          Set @EnergyTmpField=EnergyTmpField;
          Set @EnergyPrice1=EnergyTmpField*EnergyPrice;
          Set @sSql=CONCAT('SELECT Count(*) into @EnergyCount FROM ',' EnergyHistory_',Year(BeginTimeHour),' t 
              where t.houseIEEE=? and t.IEEE=? and t.EP=?
                and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');
          PREPARE stmtselect FROM @sSql;
          EXECUTE stmtselect using @HouseIEEE,@IEEE,@EP,@beginTimeHour,@beginTimeHour;
          DEALLOCATE PREPARE stmtselect;
          Set EnergyCount=@EnergyCount;
          if EnergyCount=0 then 
            Set @sSql=CONCAT('insert into ',' EnergyHistory_',Year(BeginTimeHour),
                             '(HouseIEEE,IEEE,EP,opDateTime,EnergyValue,EnergyPrice) values
                                     (?,?,?,?,?,?)');
          else 
            if (I=aCount-1) and (aCount>0) then
              Set @sSql=CONCAT('update ',' EnergyHistory_',Year(BeginTimeHour),
                               ' t set t.energyValue=IfNull(t.energyValue,0)+?,
                                       t.energyPrice=IfNull(t.energyPrice,0)+?
                where t.houseIEEE=? and t.IEEE=? and t.EP=?
                  and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');
            else
              Set @sSql=CONCAT('update ',' EnergyHistory_',Year(BeginTimeHour),
                               ' t set t.energyValue=?,
                                       t.energyPrice=?
                where t.houseIEEE=? and t.IEEE=? and t.EP=?
                  and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');              
            end if;
                              
          end if;
           
          PREPARE stmtselect FROM @sSql;
          if EnergyCount=0 then
            EXECUTE stmtselect using @HouseIEEE,@IEEE,@EP,@beginTimeHour,@EnergyTmpField,@EnergyPrice1;
          else
            EXECUTE stmtselect using @EnergyTmpField,@EnergyPrice1,@HouseIEEE,@IEEE,@EP,@beginTimeHour,@beginTimeHour;
          end if;
          DEALLOCATE PREPARE stmtselect;
          
          /*SELECT Count(*) into EnergyCount FROM energyhistory t 
            where t.houseIEEE=houseIEEE and t.IEEE=IEEE and t.EP=EP
              and Date(t.opdatetime)=Date(beginTimeHour) and Hour(t.opdatetime)=Hour(BeginTimeHour);
          
          if EnergyCount=0 then 
            insert into energyhistory(HouseIEEE,IEEE,EP,opDateTime,EnergyValue,EnergyPrice) values
                                     (HouseIEEE,IEEE,EP,BeginTimeHour,EnergyTmpField,EnergyTmpField*EnergyPrice);
          else 
            update energyhistory t set t.energyValue=t.energyValue+EnergyTmpField,
                                       t.energyPrice=t.energyPrice+EnergyTmpField*EnergyPrice
            where t.houseIEEE=houseIEEE and t.IEEE=IEEE and t.EP=EP
              and Date(t.opdatetime)=Date(BeginTimeHour) and Hour(t.opdatetime)=Hour(BeginTimeHour);
          end if;*/
        end if;
        FETCH Cur_Field into BeginEnergy, EndEnergy,iField;        
      end WHILE;
      CLOSE Cur_Field;
      
      if EnergySumTmp+EnergyTmpHour>EndEnergy THEN
        Set EnergyTmpField=EnergyTmpHour;
        /*Set EnergyTmpField=EnergySumTmp+EnergyTmpHour-EndEnergy;*/
        Set @HouseIEEE=HouseIEEE;
        Set @IEEE=IEEE;
        Set @EP=EP;
        Set @beginTimeHour=beginTimeHour;
        Set @EnergyTmpField=EnergyTmpField;
        Set @EnergyPrice1=EnergyTmpField*EnergyPrice;
        Set @sSql=CONCAT('SELECT Count(*) into @EnergyCount FROM ',' EnergyHistory_',Year(BeginTimeHour),' t 
            where t.houseIEEE=? and t.IEEE=? and t.EP=?
              and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');
        PREPARE stmtselect FROM @sSql;
        EXECUTE stmtselect using @HouseIEEE,@IEEE,@EP,@beginTimeHour,@beginTimeHour;
        DEALLOCATE PREPARE stmtselect;
        Set EnergyCount=@EnergyCount;
        if EnergyCount=0 then 
          Set @sSql=CONCAT('insert into ',' EnergyHistory_',Year(BeginTimeHour),
                           '(HouseIEEE,IEEE,EP,opDateTime,EnergyValue,EnergyPrice) values
                                   (?,?,?,?,?,?)');
        else 
          if I=aCount-1 and aCount>0 then
            Set @sSql=CONCAT('update ',' EnergyHistory_',Year(BeginTimeHour),
                             ' t set t.energyValue=IfNull(t.energyValue,0)+?,
                                     t.energyPrice=IfNull(t.energyPrice,0)+?
              where t.houseIEEE=? and t.IEEE=? and t.EP=?
                and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');
          else
            Set @sSql=CONCAT('update ',' EnergyHistory_',Year(BeginTimeHour),
                             ' t set t.energyValue=?,
                                   t.energyPrice=?
            where t.houseIEEE=? and t.IEEE=? and t.EP=?
              and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');              
          end if;
                            
        end if;
          
        PREPARE stmtselect FROM @sSql;
        if EnergyCount=0 then
          EXECUTE stmtselect using @HouseIEEE,@IEEE,@EP,@beginTimeHour,@EnergyTmpField,@EnergyPrice1;
        else
          EXECUTE stmtselect using @EnergyTmpField,@EnergyPrice1,@HouseIEEE,@IEEE,@EP,@beginTimeHour,@beginTimeHour;
        end if;
        DEALLOCATE PREPARE stmtselect;
        
        /*SELECT Count(*) into EnergyCount FROM energyhistory t 
            where t.houseIEEE=houseIEEE and t.IEEE=IEEE and t.EP=EP
              and Date(t.opdatetime)=Date(BeginTimeHour) and Hour(t.opdatetime)=Hour(BeginTimeHour);
          if EnergyCount=0 then 
            insert into energyhistory(HouseIEEE,IEEE,EP,opDateTime,EnergyValue,EnergyPrice) values
                                     (HouseIEEE,IEEE,EP,BeginTimeHour,EnergyTmpField,EnergyTmpField*EnergyPrice);
          else 
            update energyhistory t set t.energyValue=t.energyValue+EnergyTmpField,
                                       t.energyPrice=t.energyPrice+EnergyTmpField*EnergyPrice
            where t.houseIEEE=houseIEEE and t.IEEE=IEEE and t.EP=EP
              and Date(t.opdatetime)=Date(BeginTimeHour) and Hour(t.opdatetime)=Hour(BeginTimeHour);
          end if;*/
      end if;
      Set I=I+1;
    end if;
  end WHILE;
  
END Label_At_Start
/*--end--*/
/*----监控日志查询建立索引--begin--*/
CREATE INDEX Idx_House_ClientID ON house(client_id);
CREATE INDEX client_id_index ON reliroleclient(client_id);
CREATE INDEX role_id_index ON reliroleclient(role_id);
CREATE INDEX role_id_index ON reliuserrole(role_id);
/*---end---*/
              
              
 /*北京电价*/

INSERT INTO `energyreferencelibrary` (`id`, `name`, `priceType`, `DSTFlag`, `regionCode`, `syncFlag`, `enabledFlag`) VALUES ('10006', '试行阶梯电价', '1', '0', 'bj', '1', '1');

INSERT INTO `energyreferencelibrary` (`id`, `name`, `priceType`, `DSTFlag`, `regionCode`, `syncFlag`, `enabledFlag`) VALUES ('10007', '合表用户电价', '1', '0', 'bj', '1', '1');

INSERT INTO `energyreferencelibrary` (`id`, `name`, `priceType`, `DSTFlag`, `regionCode`, `syncFlag`, `enabledFlag`) VALUES ('10008', '居民价非居民用户电价', '1', '0', 'bj', '1', '1');

INSERT INTO `energyfieldreferencelibrary` (`id`, `energyId`, `energyFieldId`, `name`, `startField`, `endField`) VALUES ('10027', '10006', '1', '分档1', '0', '240');

INSERT INTO `energyfieldreferencelibrary` (`id`, `energyId`, `energyFieldId`, `name`, `startField`, `endField`) VALUES ('10028', '10006', '1', '分档2', '240', '400');

INSERT INTO `energyfieldreferencelibrary` (`id`, `energyId`, `energyFieldId`, `name`, `startField`, `endField`) VALUES ('10029', '10006', '1', '分档3', '400', '9999');

INSERT INTO `energyfieldreferencelibrary` (`id`, `energyId`, `energyFieldId`, `name`, `startField`, `endField`) VALUES ('10030', '10007', '1', '城镇', '0', '9999');

INSERT INTO `energyfieldreferencelibrary` (`id`, `energyId`, `energyFieldId`, `name`, `startField`, `endField`) VALUES ('10031', '10007', '2', '农村', '0', '9999');

INSERT INTO `energyfieldreferencelibrary` (`id`, `energyId`, `energyFieldId`, `name`, `startField`, `endField`) VALUES ('10032', '10008', '1', '分档1', '0', '9999');

UPDATE `energyfieldreferencelibrary` SET `energyFieldId`='2' WHERE (`id`='10028');

UPDATE `energyfieldreferencelibrary` SET `energyFieldId`='3' WHERE (`id`='10029');

INSERT INTO `priceparamreferencelibrary` (`id`, `energyId`, `energyFieldId`, `energyTimeId`, `name`, `price`, `priceDST`) VALUES ('10027', '10006', '1', '0', '分档1', '0.4883', '0.4783');

INSERT INTO `priceparamreferencelibrary` (`id`, `energyId`, `energyFieldId`, `energyTimeId`, `name`, `price`, `priceDST`) VALUES ('10028', '10006', '2', '0', '分档2', '0.5383', '0.5283');

INSERT INTO `priceparamreferencelibrary` (`id`, `energyId`, `energyFieldId`, `energyTimeId`, `name`, `price`, `priceDST`) VALUES ('10029', '10006', '3', '0', '分档3', '0.7883', '0.7783');

INSERT INTO `priceparamreferencelibrary` (`id`, `energyId`, `energyFieldId`, `energyTimeId`, `name`, `price`, `priceDST`) VALUES ('10030', '10007', '1', '0', '城镇', '0.4733', '0.4633');

INSERT INTO `priceparamreferencelibrary` (`id`, `energyId`, `energyFieldId`, `energyTimeId`, `name`, `price`, `priceDST`) VALUES ('10031', '10007', '2', '0', '农村', '0.4433', '0.4333');

INSERT INTO `priceparamreferencelibrary` (`id`, `energyId`, `energyFieldId`, `energyTimeId`, `name`, `price`, `priceDST`) VALUES ('10032', '10008', '1', '0', '分档1', '0.5103', '0.5003');

UPDATE `energyreferencelibrary` SET `name`='城镇合表用户电价' WHERE (`id`='10007');

INSERT INTO `energyreferencelibrary` (`name`, `priceType`, `DSTFlag`, `regionCode`, `syncFlag`, `enabledFlag`) VALUES ('农村合表用户电价', '1', '0', 'bj', '1', '1');

UPDATE `energyreferencelibrary` SET `name`='非居民用户电价' WHERE (`id`='10008');

UPDATE `energyfieldreferencelibrary` SET `name`='分档1' WHERE (`id`='10030');

UPDATE `energyfieldreferencelibrary` SET `energyId`='10009', `energyFieldId`='1', `name`='分档1' WHERE (`id`='10031');

UPDATE `priceparamreferencelibrary` SET `name`='分档1' WHERE (`id`='10030');

UPDATE `priceparamreferencelibrary` SET `energyId`='10009', `energyFieldId`='1', `name`='分档1' WHERE (`id`='10031');


/*---国际化部分---*/
CREATE TABLE `regionLang` (
`id`  int(50) NOT NULL AUTO_INCREMENT ,
`languageIdentity`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`regionId`  int(50) NULL ,
`regionName`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
PRIMARY KEY (`id`)
)
;

INSERT INTO `regionLang` (`regionId`, `languageIdentity`, `regionName`) VALUES ('1', 'JianTi', '厦门');
INSERT INTO `regionLang` (`regionId`, `languageIdentity`, `regionName`) VALUES ('1', 'FanTi', '廈門');
INSERT INTO `regionLang` (`regionId`, `languageIdentity`, `regionName`) VALUES ('1', 'English', 'Xiamen');
INSERT INTO `regionLang` (`regionId`, `languageIdentity`, `regionName`) VALUES ('2', 'JianTi', '北京');
INSERT INTO `regionLang` (`regionId`, `languageIdentity`, `regionName`) VALUES ('2', 'FanTi', '北京');
INSERT INTO `regionLang` (`regionId`, `languageIdentity`, `regionName`) VALUES ('2', 'English', 'Beijing');
UPDATE `regionLang` SET `languageIdentity`='zh-cn' WHERE (`id`='1');
UPDATE `regionLang` SET `languageIdentity`='zh-tw' WHERE (`id`='2');
UPDATE `regionLang` SET `languageIdentity`='en-us' WHERE (`id`='3');
UPDATE `regionLang` SET `languageIdentity`='zh-cn' WHERE (`id`='4');
UPDATE `regionLang` SET `languageIdentity`='zh-tw' WHERE (`id`='5');
UPDATE `regionLang` SET `languageIdentity`='en-us' WHERE (`id`='6');
INSERT INTO `regionLang` (`regionId`, `languageIdentity`, `regionName`) VALUES ('5', 'zh-cn', '台湾');
INSERT INTO `regionLang` (`regionId`, `languageIdentity`, `regionName`) VALUES ('5', 'zh-tw', '台灣');
INSERT INTO `regionLang` (`regionId`, `languageIdentity`, `regionName`) VALUES ('5', 'en-us', 'Taiwan');

CREATE TABLE `energyreferencelibrarylang` (
`id`  int(50) NOT NULL AUTO_INCREMENT ,
`energyId`  int(50) NULL ,
`languageIdentity`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`name`  varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
;
INSERT INTO `energyreferencelibrarylang` (`energyId`, `languageIdentity`, `name`) VALUES ('7', 'zh-cn', '分时配置1');
INSERT INTO `energyreferencelibrarylang` (`energyId`, `languageIdentity`, `name`) VALUES ('7', 'zh-tw', '分時配置1');
INSERT INTO `energyreferencelibrarylang` (`energyId`, `languageIdentity`, `name`) VALUES ('7', 'en-us', 'Timesharing one');
INSERT INTO `energyreferencelibrarylang` (`energyId`, `languageIdentity`, `name`) VALUES ('7', 'zh-cn', '分时配置2');
INSERT INTO `energyreferencelibrarylang` (`energyId`, `languageIdentity`, `name`) VALUES ('7', 'zh-tw', '分時配置2');
INSERT INTO `energyreferencelibrarylang` (`energyId`, `languageIdentity`, `name`) VALUES ('7', 'en-us', 'Timesharing two');
INSERT INTO `energyreferencelibrarylang` (`energyId`, `languageIdentity`, `name`) VALUES ('11', 'zh-cn', '分档配置1');
INSERT INTO `energyreferencelibrarylang` (`energyId`, `languageIdentity`, `name`) VALUES ('11', 'zh-tw', '分檔配置1');
INSERT INTO `energyreferencelibrarylang` (`energyId`, `languageIdentity`, `name`) VALUES ('11', 'en-us', 'Binning Configuration one');
INSERT INTO `energyreferencelibrarylang` (`energyId`, `languageIdentity`, `name`) VALUES ('12', 'zh-cn', '分档配置2');
INSERT INTO `energyreferencelibrarylang` (`energyId`, `languageIdentity`, `name`) VALUES ('12', 'zh-tw', '分檔配置2');
INSERT INTO `energyreferencelibrarylang` (`energyId`, `languageIdentity`, `name`) VALUES ('12', 'en-us', 'Binning Configuration two');
INSERT INTO `energyreferencelibrarylang` (`energyId`, `languageIdentity`, `name`) VALUES ('13', 'zh-cn', '分档分时配置1');
INSERT INTO `energyreferencelibrarylang` (`energyId`, `languageIdentity`, `name`) VALUES ('13', 'zh-tw', '分檔分時配置1');
INSERT INTO `energyreferencelibrarylang` (`energyId`, `languageIdentity`, `name`) VALUES ('13', 'en-us', 'Binning Timesharing configuration one');
INSERT INTO `energyreferencelibrarylang` (`energyId`, `languageIdentity`, `name`) VALUES ('16', 'zh-cn', '分档分时配置2');
INSERT INTO `energyreferencelibrarylang` (`energyId`, `languageIdentity`, `name`) VALUES ('16', 'zh-tw', '分檔分時配置2');
INSERT INTO `energyreferencelibrarylang` (`energyId`, `languageIdentity`, `name`) VALUES ('16', 'en-us', 'Binning Timesharing configuration two');
INSERT INTO `energyreferencelibrarylang` (`energyId`, `languageIdentity`, `name`) VALUES ('10001', 'zh-cn', '非营业电价');
INSERT INTO `energyreferencelibrarylang` (`energyId`, `languageIdentity`, `name`) VALUES ('10001', 'zh-tw', '非營業電價');
INSERT INTO `energyreferencelibrarylang` (`energyId`, `languageIdentity`, `name`) VALUES ('10001', 'en-us', 'Non business electricity price');
INSERT INTO `energyreferencelibrarylang` (`energyId`, `languageIdentity`, `name`) VALUES ('10002', 'zh-cn', '营业电价');
INSERT INTO `energyreferencelibrarylang` (`energyId`, `languageIdentity`, `name`) VALUES ('10002', 'zh-tw', '營業電價');
INSERT INTO `energyreferencelibrarylang` (`energyId`, `languageIdentity`, `name`) VALUES ('10002', 'en-us', 'business electricity price');
INSERT INTO `energyreferencelibrarylang` (`energyId`, `languageIdentity`, `name`) VALUES ('10003', 'zh-cn', '福利机构非营业电价');
INSERT INTO `energyreferencelibrarylang` (`energyId`, `languageIdentity`, `name`) VALUES ('10003', 'zh-tw', '福利機構非營業電價');
INSERT INTO `energyreferencelibrarylang` (`energyId`, `languageIdentity`, `name`) VALUES ('10003', 'en-us', 'Welfare institution Non business electricity price');
INSERT INTO `energyreferencelibrarylang` (`energyId`, `languageIdentity`, `name`) VALUES ('10004', 'zh-cn', '公路照明灯电价');
INSERT INTO `energyreferencelibrarylang` (`energyId`, `languageIdentity`, `name`) VALUES ('10004', 'zh-tw', '公路照明燈電價');
INSERT INTO `energyreferencelibrarylang` (`energyId`, `languageIdentity`, `name`) VALUES ('10004', 'en-us', 'Highway lighting price');
INSERT INTO `energyreferencelibrarylang` (`energyId`, `languageIdentity`, `name`) VALUES ('10005', 'zh-cn', '福利机构营业电价');
INSERT INTO `energyreferencelibrarylang` (`energyId`, `languageIdentity`, `name`) VALUES ('10005', 'zh-tw', '福利機構營業電價');
INSERT INTO `energyreferencelibrarylang` (`energyId`, `languageIdentity`, `name`) VALUES ('10005', 'en-us', 'Welfare institution business electricity price');
INSERT INTO `energyreferencelibrarylang` (`energyId`, `languageIdentity`, `name`) VALUES ('10006', 'zh-cn', '试行阶梯电价');
INSERT INTO `energyreferencelibrarylang` (`energyId`, `languageIdentity`, `name`) VALUES ('10006', 'zh-tw', '試行階梯電價');
INSERT INTO `energyreferencelibrarylang` (`energyId`, `languageIdentity`, `name`) VALUES ('10006', 'en-us', 'Step price');
INSERT INTO `energyreferencelibrarylang` (`energyId`, `languageIdentity`, `name`) VALUES ('10007', 'zh-cn', '城镇合表用户电价');
INSERT INTO `energyreferencelibrarylang` (`energyId`, `languageIdentity`, `name`) VALUES ('10007', 'zh-tw', '城鎮合表用戶電價');
INSERT INTO `energyreferencelibrarylang` (`energyId`, `languageIdentity`, `name`) VALUES ('10007', 'en-us', 'Urban combined table user electricity price');
INSERT INTO `energyreferencelibrarylang` (`energyId`, `languageIdentity`, `name`) VALUES ('10008', 'zh-cn', '非居民用户电价');
INSERT INTO `energyreferencelibrarylang` (`energyId`, `languageIdentity`, `name`) VALUES ('10008', 'zh-tw', '非居民用電價格');
INSERT INTO `energyreferencelibrarylang` (`energyId`, `languageIdentity`, `name`) VALUES ('10008', 'en-us', 'Non resident user electricity price');
INSERT INTO `energyreferencelibrarylang` (`energyId`, `languageIdentity`, `name`) VALUES ('10009', 'zh-cn', '农村合表用户电价');
INSERT INTO `energyreferencelibrarylang` (`energyId`, `languageIdentity`, `name`) VALUES ('10009', 'zh-tw', '農村合表用戶電價');
INSERT INTO `energyreferencelibrarylang` (`energyId`, `languageIdentity`, `name`) VALUES ('10009', 'en-us', 'Rural combined table user electricity price');

CREATE TABLE `energyfieldreferencelibrarylang` (
`id`  int(50) NOT NULL AUTO_INCREMENT ,
`energyfieldId`  int(50) NULL ,
`languageIdentity`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
;

INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10', 'zh-cn', '分档1');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10', 'zh-tw', '分檔1');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10', 'en-us', 'Binning one');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('11', 'zh-cn', '分档2');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('11', 'zh-tw', '分檔2');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('11', 'en-us', 'Binning two');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('12', 'zh-cn', '分档3');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('12', 'zh-tw', '分檔3');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('12', 'en-us', 'Binning three');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('13', 'zh-cn', '分档1');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('13', 'zh-tw', '分檔1');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('13', 'en-us', 'Binning one');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('14', 'zh-cn', '分档2');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('14', 'zh-tw', '分檔2');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('14', 'en-us', 'Binning two');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('15', 'zh-cn', '分档1');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('15', 'zh-tw', '分檔1');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('15', 'en-us', 'Binning one');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('16', 'zh-cn', '分档2');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('16', 'zh-tw', '分檔2');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('16', 'en-us', 'Binning two');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('17', 'zh-cn', '分档3');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('17', 'zh-tw', '分檔3');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('17', 'en-us', 'Binning three');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('18', 'zh-cn', '分档1');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('18', 'zh-tw', '分檔1');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('18', 'en-us', 'Binning one');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('19', 'zh-cn', '分档2');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('19', 'zh-tw', '分檔2');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('19', 'en-us', 'Binning two');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10001', 'zh-cn', '分档1');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10001', 'zh-tw', '分檔1');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10001', 'en-us', 'Binning one');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10002', 'zh-cn', '分档2');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10002', 'zh-tw', '分檔2');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10002', 'en-us', 'Binning two');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10003', 'zh-cn', '分档3');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10003', 'zh-tw', '分檔3');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10003', 'en-us', 'Binning three');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10004', 'zh-cn', '分档4');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10004', 'zh-tw', '分檔4');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10004', 'en-us', 'Binning four');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10005', 'zh-cn', '分档5');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10005', 'zh-tw', '分檔5');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10005', 'en-us', 'Binning five');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10006', 'zh-cn', '分档6');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10006', 'zh-tw', '分檔6');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10006', 'en-us', 'Binning six');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10007', 'zh-cn', '分档1');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10007', 'zh-tw', '分檔1');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10007', 'en-us', 'Binning one');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10008', 'zh-cn', '分档2');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10008', 'zh-tw', '分檔2');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10008', 'en-us', 'Binning two');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10009', 'zh-cn', '分档3');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10009', 'zh-tw', '分檔3');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10009', 'en-us', 'Binning three');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10010', 'zh-cn', '分档4');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10010', 'zh-tw', '分檔4');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10010', 'en-us', 'Binning four');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10011', 'zh-cn', '分档1');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10011', 'zh-tw', '分檔1');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10011', 'en-us', 'Binning one');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10012', 'zh-cn', '分档2');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10012', 'zh-tw', '分檔2');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10012', 'en-us', 'Binning two');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10013', 'zh-cn', '分档3');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10013', 'zh-tw', '分檔3');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10013', 'en-us', 'Binning three');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10014', 'zh-cn', '分档4');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10014', 'zh-tw', '分檔4');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10014', 'en-us', 'Binning four');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10015', 'zh-cn', '分档5');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10015', 'zh-tw', '分檔5');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10015', 'en-us', 'Binning five');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10016', 'zh-cn', '分档6');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10016', 'zh-tw', '分檔6');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10016', 'en-us', 'Binning six');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10017', 'zh-cn', '分档1');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10017', 'zh-tw', '分檔1');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10017', 'en-us', 'Binning one');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10018', 'zh-cn', '分档2');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10018', 'zh-tw', '分檔2');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10018', 'en-us', 'Binning two');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10019', 'zh-cn', '分档3');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10019', 'zh-tw', '分檔3');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10019', 'en-us', 'Binning three');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10020', 'zh-cn', '分档4');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10020', 'zh-tw', '分檔4');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10020', 'en-us', 'Binning four');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10021', 'zh-cn', '分档5');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10021', 'zh-tw', '分檔5');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10021', 'en-us', 'Binning five');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10022', 'zh-cn', '分档6');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10022', 'zh-tw', '分檔6');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10022', 'en-us', 'Binning six');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10023', 'zh-cn', '分档1');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10023', 'zh-tw', '分檔1');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10023', 'en-us', 'Binning one');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10024', 'zh-cn', '分档2');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10024', 'zh-tw', '分檔2');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10024', 'en-us', 'Binning two');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10025', 'zh-cn', '分档3');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10025', 'zh-tw', '分檔3');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10025', 'en-us', 'Binning three');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10026', 'zh-cn', '分档4');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10026', 'zh-tw', '分檔4');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10026', 'en-us', 'Binning four');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10027', 'zh-cn', '分档1');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10027', 'zh-tw', '分檔1');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10027', 'en-us', 'Binning one');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10028', 'zh-cn', '分档2');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10028', 'zh-tw', '分檔2');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10028', 'en-us', 'Binning two');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10029', 'zh-cn', '分档3');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10029', 'zh-tw', '分檔3');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10029', 'en-us', 'Binning three');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10030', 'zh-cn', '分档1');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10030', 'zh-tw', '分檔1');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10030', 'en-us', 'Binning one');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10031', 'zh-cn', '分档1');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10031', 'zh-tw', '分檔1');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10031', 'en-us', 'Binning one');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10032', 'zh-cn', '分档1');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10032', 'zh-tw', '分檔1');
INSERT INTO `energyfieldreferencelibrarylang` (`energyfieldId`, `languageIdentity`, `name`) VALUES ('10032', 'en-us', 'Binning one');

CREATE TABLE `energytimereferencelibrarylang` (
`id`  int(50) NOT NULL AUTO_INCREMENT ,
`energytimerId`  int(50) NULL ,
`languageIdentity`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
;

INSERT INTO `energytimereferencelibrarylang` (`energytimerId`, `languageIdentity`, `name`) VALUES ('19', 'zh-cn', '分时1');
INSERT INTO `energytimereferencelibrarylang` (`energytimerId`, `languageIdentity`, `name`) VALUES ('19', 'zh-tw', '分時1');
INSERT INTO `energytimereferencelibrarylang` (`energytimerId`, `languageIdentity`, `name`) VALUES ('19', 'en-us','Timesharing one');
INSERT INTO `energytimereferencelibrarylang` (`energytimerId`, `languageIdentity`, `name`) VALUES ('20', 'zh-cn', '分时2');
INSERT INTO `energytimereferencelibrarylang` (`energytimerId`, `languageIdentity`, `name`) VALUES ('20', 'zh-tw', '分時2');
INSERT INTO `energytimereferencelibrarylang` (`energytimerId`, `languageIdentity`, `name`) VALUES ('20', 'en-us','Timesharing two');
INSERT INTO `energytimereferencelibrarylang` (`energytimerId`, `languageIdentity`, `name`) VALUES ('21', 'zh-cn', '分时3');
INSERT INTO `energytimereferencelibrarylang` (`energytimerId`, `languageIdentity`, `name`) VALUES ('21', 'zh-tw', '分時3');
INSERT INTO `energytimereferencelibrarylang` (`energytimerId`, `languageIdentity`, `name`) VALUES ('21', 'en-us','Timesharing three');
INSERT INTO `energytimereferencelibrarylang` (`energytimerId`, `languageIdentity`, `name`) VALUES ('22', 'zh-cn', '分时1');
INSERT INTO `energytimereferencelibrarylang` (`energytimerId`, `languageIdentity`, `name`) VALUES ('22', 'zh-tw', '分時1');
INSERT INTO `energytimereferencelibrarylang` (`energytimerId`, `languageIdentity`, `name`) VALUES ('22', 'en-us','Timesharing one');
INSERT INTO `energytimereferencelibrarylang` (`energytimerId`, `languageIdentity`, `name`) VALUES ('23', 'zh-cn', '分时2');
INSERT INTO `energytimereferencelibrarylang` (`energytimerId`, `languageIdentity`, `name`) VALUES ('23', 'zh-tw', '分時2');
INSERT INTO `energytimereferencelibrarylang` (`energytimerId`, `languageIdentity`, `name`) VALUES ('23', 'en-us','Timesharing two');
INSERT INTO `energytimereferencelibrarylang` (`energytimerId`, `languageIdentity`, `name`) VALUES ('24', 'zh-cn', '分时1');
INSERT INTO `energytimereferencelibrarylang` (`energytimerId`, `languageIdentity`, `name`) VALUES ('24', 'zh-tw', '分時1');
INSERT INTO `energytimereferencelibrarylang` (`energytimerId`, `languageIdentity`, `name`) VALUES ('24', 'en-us','Timesharing one');
INSERT INTO `energytimereferencelibrarylang` (`energytimerId`, `languageIdentity`, `name`) VALUES ('25', 'zh-cn', '分时2');
INSERT INTO `energytimereferencelibrarylang` (`energytimerId`, `languageIdentity`, `name`) VALUES ('25', 'zh-tw', '分時2');
INSERT INTO `energytimereferencelibrarylang` (`energytimerId`, `languageIdentity`, `name`) VALUES ('25', 'en-us','Timesharing two');
INSERT INTO `energytimereferencelibrarylang` (`energytimerId`, `languageIdentity`, `name`) VALUES ('26', 'zh-cn', '分时3');
INSERT INTO `energytimereferencelibrarylang` (`energytimerId`, `languageIdentity`, `name`) VALUES ('26', 'zh-tw', '分時3');
INSERT INTO `energytimereferencelibrarylang` (`energytimerId`, `languageIdentity`, `name`) VALUES ('26', 'en-us','Timesharing three');
INSERT INTO `energytimereferencelibrarylang` (`energytimerId`, `languageIdentity`, `name`) VALUES ('27', 'zh-cn', '分时1');
INSERT INTO `energytimereferencelibrarylang` (`energytimerId`, `languageIdentity`, `name`) VALUES ('27', 'zh-tw', '分時1');
INSERT INTO `energytimereferencelibrarylang` (`energytimerId`, `languageIdentity`, `name`) VALUES ('27', 'en-us','Timesharing one');
INSERT INTO `energytimereferencelibrarylang` (`energytimerId`, `languageIdentity`, `name`) VALUES ('28', 'zh-cn', '分时2');
INSERT INTO `energytimereferencelibrarylang` (`energytimerId`, `languageIdentity`, `name`) VALUES ('28', 'zh-tw', '分時2');
INSERT INTO `energytimereferencelibrarylang` (`energytimerId`, `languageIdentity`, `name`) VALUES ('28', 'en-us','Timesharing two');


CREATE TABLE `priceparamreferencelibrarylang` (
`id`  int(50) NOT NULL AUTO_INCREMENT ,
`priceparamId`  int(50) NULL ,
`languageIdentity`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
;


INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('24', 'zh-cn', '分档1');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('24', 'zh-tw', '分檔1');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('24', 'en-us', 'Binning one');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('25', 'zh-cn', '分档2');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('25', 'zh-tw', '分檔2');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('25', 'en-us', 'Binning two');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('26', 'zh-cn', '分档3');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('26', 'zh-tw', '分檔3');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('26', 'en-us', 'Binning three');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('27', 'zh-cn', '分时1');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('27', 'zh-tw', '分時1');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('27', 'en-us', 'Timesharing one');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('28', 'zh-cn', '分时2');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('28', 'zh-tw', '分時2');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('28', 'en-us', 'Timesharing two');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('29', 'zh-cn', '分时3');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('29', 'zh-tw', '分時3');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('29', 'en-us', 'Timesharing three');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('30', 'zh-cn', '分档1分时1');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('30', 'zh-tw', '分檔1分時1');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('30', 'en-us', 'Binning one Timesharing one');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('31', 'zh-cn', '分档1分时2');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('31', 'zh-tw', '分檔1分時2');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('31', 'en-us', 'Binning one Timesharing two');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('32', 'zh-cn', '分档2分时1');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('32', 'zh-tw', '分檔2分時1');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('32', 'en-us', 'Binning two Timesharing one');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('33', 'zh-cn', '分档2分时2');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('33', 'zh-tw', '分檔2分時2');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('33', 'en-us', 'Binning two Timesharing two');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('34', 'zh-cn', '分档1');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('34', 'zh-tw', '分檔1');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('34', 'en-us', 'Binning one');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('35', 'zh-cn', '分档2');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('35', 'zh-tw', '分檔2');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('35', 'en-us', 'Binning two');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('36', 'zh-cn', '分档3');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('36', 'zh-tw', '分檔3');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('36', 'en-us', 'Binning three');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('37', 'zh-cn', '分时1');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('37', 'zh-tw', '分時1');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('37', 'en-us', 'Timesharing one');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('38', 'zh-cn', '分时2');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('38', 'zh-tw', '分時2');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('38', 'en-us', 'Timesharing two');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('39', 'zh-cn', '分时3');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('39', 'zh-tw', '分時3');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('39', 'en-us', 'Timesharing three');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('40', 'zh-cn', '分档1分时2');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('40', 'zh-tw', '分檔1分時2');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('40', 'en-us', 'Binning one Timesharing two');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('41', 'zh-cn', '分档2分时1');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('41', 'zh-tw', '分檔2分時1');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('41', 'en-us', 'Binning two Timesharing one');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('42', 'zh-cn', '分档2分时2');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('42', 'zh-tw', '分檔2分時2');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('42', 'en-us', 'Binning two Timesharing two');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('43', 'zh-cn', '分档1分时1');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('43', 'zh-tw', '分檔1分時1');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('43', 'en-us', 'Binning one Timesharing one');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10001', 'zh-cn', '分档1');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10001', 'zh-tw', '分檔1');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10001', 'en-us', 'Binning one');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10002', 'zh-cn', '分档2');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10002', 'zh-tw', '分檔2');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10002', 'en-us', 'Binning two');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10003', 'zh-cn', '分档3');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10003', 'zh-tw', '分檔3');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10003', 'en-us', 'Binning three');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10004', 'zh-cn', '分档4');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10004', 'zh-tw', '分檔4');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10004', 'en-us', 'Binning four');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10005', 'zh-cn', '分档5');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10005', 'zh-tw', '分檔5');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10005', 'en-us', 'Binning five');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10006', 'zh-cn', '分档6');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10006', 'zh-tw', '分檔6');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10006', 'en-us', 'Binning six');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10007', 'zh-cn', '分档1');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10007', 'zh-tw', '分檔1');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10007', 'en-us', 'Binning one');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10008', 'zh-cn', '分档2');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10008', 'zh-tw', '分檔2');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10008', 'en-us', 'Binning two');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10009', 'zh-cn', '分档3');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10009', 'zh-tw', '分檔3');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10009', 'en-us', 'Binning three');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10010', 'zh-cn', '分档4');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10010', 'zh-tw', '分檔4');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10010', 'en-us', 'Binning four');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10011', 'zh-cn', '分档1');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10011', 'zh-tw', '分檔1');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10011', 'en-us', 'Binning one');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10012', 'zh-cn', '分档2');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10012', 'zh-tw', '分檔2');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10012', 'en-us', 'Binning two');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10013', 'zh-cn', '分档3');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10013', 'zh-tw', '分檔3');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10013', 'en-us', 'Binning three');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10014', 'zh-cn', '分档4');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10014', 'zh-tw', '分檔4');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10014', 'en-us', 'Binning four');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10015', 'zh-cn', '分档5');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10015', 'zh-tw', '分檔5');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10015', 'en-us', 'Binning five');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10016', 'zh-cn', '分档6');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10016', 'zh-tw', '分檔6');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10016', 'en-us', 'Binning six');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10017', 'zh-cn', '分档1');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10017', 'zh-tw', '分檔1');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10017', 'en-us', 'Binning one');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10018', 'zh-cn', '分档2');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10018', 'zh-tw', '分檔2');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10018', 'en-us', 'Binning two');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10019', 'zh-cn', '分档3');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10019', 'zh-tw', '分檔3');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10019', 'en-us', 'Binning three');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10020', 'zh-cn', '分档4');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10020', 'zh-tw', '分檔4');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10020', 'en-us', 'Binning four');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10021', 'zh-cn', '分档5');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10021', 'zh-tw', '分檔5');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10021', 'en-us', 'Binning five');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10022', 'zh-cn', '分档6');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10022', 'zh-tw', '分檔6');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10022', 'en-us', 'Binning six');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10023', 'zh-cn', '分档1');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10023', 'zh-tw', '分檔1');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10023', 'en-us', 'Binning one');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10024', 'zh-cn', '分档2');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10024', 'zh-tw', '分檔2');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10024', 'en-us', 'Binning two');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10025', 'zh-cn', '分档3');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10025', 'zh-tw', '分檔3');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10025', 'en-us', 'Binning three');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10026', 'zh-cn', '分档4');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10026', 'zh-tw', '分檔4');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10026', 'en-us', 'Binning four');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10027', 'zh-cn', '分档1');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10027', 'zh-tw', '分檔1');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10027', 'en-us', 'Binning one');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10028', 'zh-cn', '分档2');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10028', 'zh-tw', '分檔2');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10028', 'en-us', 'Binning two');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10029', 'zh-cn', '分档3');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10029', 'zh-tw', '分檔3');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10029', 'en-us', 'Binning three');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10030', 'zh-cn', '分档1');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10030', 'zh-tw', '分檔1');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10030', 'en-us', 'Binning one');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10031', 'zh-cn', '分档1');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10031', 'zh-tw', '分檔1');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10031', 'en-us', 'Binning one');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10032', 'zh-cn', '分档1');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10032', 'zh-tw', '分檔1');
INSERT INTO `priceparamreferencelibrarylang` (`priceparamId`, `languageIdentity`, `name`) VALUES ('10032', 'en-us', 'Binning one');

ALTER TABLE `energyreferencelibrarylang`
ADD INDEX `lang` (`energyId`, `languageIdentity`) USING BTREE ;

ALTER TABLE `energyfieldreferencelibrarylang`
ADD INDEX `lang` (`energyfieldId`, `languageIdentity`) USING BTREE ;

ALTER TABLE `energytimereferencelibrarylang`
ADD INDEX `lang` (`energytimerId`, `languageIdentity`) USING BTREE ;

ALTER TABLE `priceparamreferencelibrarylang`
ADD INDEX `lang` (`priceparamId`, `languageIdentity`) USING BTREE ;

ALTER TABLE `regionlang`
ADD INDEX `lang` (`regionId`, `languageIdentity`) USING BTREE ;
/*---国际化部分结束---*/

/*--区域码修改--*/
UPDATE `region` SET `id`='5' WHERE regionCode='tw';
UPDATE region SET regionCode='350200' WHERE regionCode='xm';
UPDATE region SET regionCode='1100000000' WHERE regionCode='bj';
UPDATE region SET regionCode='810000' WHERE regionCode='tw';
update energyreferencelibrary set regionCode='350200'WHERE regionCode='xm';
update energyreferencelibrary set regionCode='1100000000'WHERE regionCode='bj';
update energyreferencelibrary set regionCode='810000'WHERE regionCode='tw';
/*--区域码结束--*/

  /*---翻译修改---*/
UPDATE `energyreferencelibrarylang` SET `name`='Rate 1 - TOU' WHERE (`id`='3');
UPDATE `energyreferencelibrarylang` SET `name`='Rate 2 - TOU' WHERE (`id`='6');
UPDATE `energyreferencelibrarylang` SET `name`='Rate 1 - tier' WHERE (`id`='9');
UPDATE `energyreferencelibrarylang` SET `name`='Rate 2 - tier' WHERE (`id`='12');
UPDATE `energyreferencelibrarylang` SET `name`='Rate 1 - tier&TOU' WHERE (`id`='15');
UPDATE `energyreferencelibrarylang` SET `name`='Rate 2 - tier&TOU' WHERE (`id`='18');
UPDATE `energyreferencelibrarylang` SET `name`='tiered price' WHERE (`id`='36');
UPDATE `energyreferencelibrarylang` SET `name`='Urban combinded electric meter user electricity price' WHERE (`id`='39');
UPDATE `energyreferencelibrarylang` SET `name`='Rural combinded electric meter user electricity price' WHERE (`id`='45');

UPDATE energyfieldreferencelibrarylang set name=REPLACE(name,'Binning one','tier 1') ;
UPDATE energyfieldreferencelibrarylang set name=REPLACE(name,'Binning two','tier 2') ;
UPDATE energyfieldreferencelibrarylang set name=REPLACE(name,'Binning three','tier 3') ;
UPDATE energyfieldreferencelibrarylang set name=REPLACE(name,'Binning four','tier 4') ;
UPDATE energyfieldreferencelibrarylang set name=REPLACE(name,'Binning five','tier 5') ;
UPDATE energyfieldreferencelibrarylang set name=REPLACE(name,'Binning six','tier 6') ;

UPDATE energytimereferencelibrarylang set name=REPLACE(name,'Timesharing one','period 1') ;
UPDATE energytimereferencelibrarylang set name=REPLACE(name,'Timesharing two','period 2') ;
UPDATE energytimereferencelibrarylang set name=REPLACE(name,'Timesharing three','period 3') ;
  
UPDATE priceparamreferencelibrarylang set name=REPLACE(name,'Binning one','tier 1') ;
UPDATE priceparamreferencelibrarylang set name=REPLACE(name,'Binning two','tier 2') ;
UPDATE priceparamreferencelibrarylang set name=REPLACE(name,'Binning three','tier 3') ;
UPDATE priceparamreferencelibrarylang set name=REPLACE(name,'Binning four','tier 4') ;
UPDATE priceparamreferencelibrarylang set name=REPLACE(name,'Binning five','tier 5') ;
UPDATE priceparamreferencelibrarylang set name=REPLACE(name,'Binning six','tier 6') ;
UPDATE priceparamreferencelibrarylang set name=REPLACE(name,'Timesharing one','period 1') ;
UPDATE priceparamreferencelibrarylang set name=REPLACE(name,'Timesharing two','period 2') ;
UPDATE priceparamreferencelibrarylang set name=REPLACE(name,'Timesharing three','period 3') ;
  /*---翻译修改结束---*/

  /*--energyreferencelibrarylang数据修正--*/
UPDATE `energyreferencelibrarylang` SET `energyId`='8' WHERE (`id`='4');
UPDATE `energyreferencelibrarylang` SET `energyId`='8' WHERE (`id`='5');
UPDATE `energyreferencelibrarylang` SET `energyId`='8' WHERE (`id`='6');
  /*--energyreferencelibrarylang数据修正结束--*/
  
  
/*2015-11-03 app闪退日志表新增字段*/
ALTER TABLE file_appinfo  ADD  version_release varchar(20);
ALTER TABLE file_appinfo  ADD  cpu varchar(255);
ALTER TABLE file_appinfo  ADD  package varchar(255);
ALTER TABLE file_appinfo  ADD  model varchar(50);
ALTER TABLE file_appinfo  ADD  brand varchar(20);
ALTER TABLE file_appinfo  ADD  resolution varchar(20);
ALTER TABLE file_appinfo  ADD  memory varchar(20);
ALTER TABLE file_appinfo  ADD  app_version varchar(20);
ALTER TABLE file_appinfo  ADD  server_ip varchar(20);
ALTER TABLE file_appinfo  ADD  file_size varchar(20);

/*高级搜索表*/

DROP TABLE IF EXISTS `conditionmain`;
CREATE TABLE `conditionmain` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `conditionsub`;
CREATE TABLE `conditionsub` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `leftBracket` varchar(50) DEFAULT NULL,
  `field` varchar(50) DEFAULT NULL,
  `operator` varchar(50) DEFAULT NULL,
  `content` varchar(50) DEFAULT NULL,
  `rightBracket` varchar(50) DEFAULT NULL,
  `relation` varchar(50) DEFAULT NULL,
  `mid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8;


/*conditionmain添加userid字段*/
ALTER TABLE `conditionmain`
ADD COLUMN `userid`  int NULL AFTER `name`;

/*修改file_appinfo及对应年份表的memory字段长度*/
ALTER table file_appinfo_2015 modify column memory VARCHAR(50);
ALTER table file_appinfo modify column memory VARCHAR(50);


/*添加表devicelog，保存日志*/
CREATE TABLE `devicelog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `houseIeee` varchar(255) DEFAULT NULL,
  `logType` varchar(255) DEFAULT NULL,
  `deviceSN` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

ALTER TABLE devicelog ADD COLUMN time varchar(30) DEFAULT NULL;

/*新增zonewarntypetable表，并插入数据*/
DROP TABLE IF EXISTS `zonewarntypetable`;
CREATE TABLE `zonewarntypetable` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ChinesewarnType` varchar(255) DEFAULT NULL,
  `EnglishwarnType` varchar(255) DEFAULT NULL,
  `w_mode` int(11) DEFAULT NULL,
  `sensortype` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of zonewarntypetable
-- ----------------------------
INSERT INTO `zonewarntypetable` VALUES ('1', '<房间>-烟雾探测报警!', '<room>-smoke detector alarms!', '2', '0028');
INSERT INTO `zonewarntypetable` VALUES ('2', '<房间>-气体探测报警!', '<room>-gas detector alarms!', '2', '002B');
INSERT INTO `zonewarntypetable` VALUES ('3', '<房间>-被水淹探测报警!', '<room>-flooded detector alarms!', '3', '002A');
INSERT INTO `zonewarntypetable` VALUES ('4', '<房间>-门磁设备被拉开了', '<room>-burglar alarm!', '3', '002D');
INSERT INTO `zonewarntypetable` VALUES ('5', '<房间>-有人跌倒了!', '<room>-someone fall!', '3', '002C');
INSERT INTO `zonewarntypetable` VALUES ('6', '<房间>-有人紧急求救!', '<room>-someone is asking for help!', '3', '0115');
INSERT INTO `zonewarntypetable` VALUES ('7', '<房间>-有人入侵了!', '<room>-burglar alarm!', '1', '000D');
INSERT INTO `zonewarntypetable` VALUES ('8', '<房间>-门磁设备被拉开了!', '<room>-burglar alarm!', '1', '0015');
INSERT INTO `zonewarntypetable` VALUES ('9', '<房间>-<设备>被拉开了!', '<room>-<device> is opened!', '6', '0015');
INSERT INTO `zonewarntypetable` VALUES ('10', '有人来访，门铃响了!', 'Door bell rang!', '6', '010F');

/*更新zonewarntypetable*/
update zonewarntypetable set ChinesewarnType='<房间>-有人入侵了!' where w_mode=3 and sensortype='002D';
update zonewarntypetable set ChinesewarnType='<房间>-有人入侵了!' where w_mode=1 and sensortype='0015';

/*更改warntypetable表*/
-- ----------------------------
-- Table structure for warntypetable
-- ----------------------------
DROP TABLE IF EXISTS `warntypetable`;
CREATE TABLE `warntypetable` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ChinesewarnType` varchar(255) DEFAULT NULL,
  `EnglishwarnType` varchar(255) DEFAULT NULL,
  `w_mode` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of warntypetable
-- ----------------------------
INSERT INTO `warntypetable` VALUES ('1', '停止告警', 'stop', '0');
INSERT INTO `warntypetable` VALUES ('2', '偷窃', 'Burglar', '1');
INSERT INTO `warntypetable` VALUES ('3', '火警', 'Fire', '2');
INSERT INTO `warntypetable` VALUES ('4', '紧急', 'Emergency', '3');
INSERT INTO `warntypetable` VALUES ('5', '静音', 'Mute', '4');
INSERT INTO `warntypetable` VALUES ('6', '<房间>-<设备>故障了!', '<room>-<device>failure!', '5');
INSERT INTO `warntypetable` VALUES ('7', '门铃', 'Doorbell', '6');
INSERT INTO `warntypetable` VALUES ('8', '<家人名称>,按时到家了!', '<family name> go home on time!', '7');
INSERT INTO `warntypetable` VALUES ('9', '<家人名称>,回家迟了!', '<family name> go home late!', '8');
INSERT INTO `warntypetable` VALUES ('10', '<家人名称>,到达了!', '<family name> arrival!', '9');
INSERT INTO `warntypetable` VALUES ('11', '<家人名称>,出门了!', '<family name> go out!', '10');
INSERT INTO `warntypetable` VALUES ('12', '<家人名称>,未准时出门!', '<family name> go out not on time!', '11');
INSERT INTO `warntypetable` VALUES ('13', '<家人名称>,按时出门了!', '<family name> go out on time!', '12');
INSERT INTO `warntypetable` VALUES ('14', '<房间>-<设备>没电了!', '<room>-<device>low battery!', '13');
INSERT INTO `warntypetable` VALUES ('15', '门锁被撬，请警惕!', 'lock the lever,please be careful!', '14');
INSERT INTO `warntypetable` VALUES ('16', '门锁被迫开启，请核实!', 'The door was forced to open,pls check!', '16');
INSERT INTO `warntypetable` VALUES ('17', '门锁异常，请检查!', 'Pls check the abnormal door!', '15');
INSERT INTO `warntypetable` VALUES ('18', '<房间>-<设备>超过额定电流!', '<room>-<device> has exceed the rated current and power off!', '44');
INSERT INTO `warntypetable` VALUES ('19', '<房间>-<设备>瞬间电流超过超过150A!', '<room>-<device> instantaneous current is more than 150A,the opwer is off!', '46');

/*修改zonewarntypetable表，Z302E/Z311E 告警的w_mode为1（原为3紧急告警，现为1偷窃告警）*/
update zonewarntypetable set w_mode=1 where w_mode=3 and sensortype='002D';

/*华为日志表添加一字段*/
ALTER TABLE `devicelog`
ADD COLUMN `clazz`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL;


/*203日志表*/
CREATE TABLE `gatewayLog` (
`id`  int NOT NULL ,
`houseIEEE`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
`description`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
`time`  datetime NULL ,
PRIMARY KEY (`id`)
)
;
ALTER TABLE `gatewayLog`
MODIFY COLUMN `id`  int(11) NOT NULL AUTO_INCREMENT FIRST ;

/*添加属性名称*/
INSERT INTO `attributenamelib` (`id`, `attributeNameCn`, `attributeName`, `clusterID`, `attrID`) VALUES ('39', 'X轴加速度', 'X axis acceleration', 'FE60', '000A');
INSERT INTO `attributenamelib` (`id`, `attributeNameCn`, `attributeName`, `clusterID`, `attrID`) VALUES ('40', 'Y轴加速度', 'Y axis acceleration', 'FE60', '000B');
INSERT INTO `attributenamelib` (`id`, `attributeNameCn`, `attributeName`, `clusterID`, `attrID`) VALUES ('41', 'Z轴加速度', 'Z axis acceleration', 'FE60', '000C');
UPDATE attributenamelib SET attributeNameCn=CONCAT(attributeNameCn,'(N/kg)'),attributeName=CONCAT(attributeName,'(N/kg)') WHERE id IN (39,40,41);