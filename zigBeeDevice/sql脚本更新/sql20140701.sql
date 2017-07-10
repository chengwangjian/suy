/*IR品牌*/
create table IRBrand (
id bigint(20) NOT NULL AUTO_INCREMENT,
brandName varchar(50),
PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/*遥控器类型*/
create table IRControllType (
id int NOT NULL,
controllTypeName varchar(50),
PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
insert into IRControllType values(0,'AC空调'),(1,'TV'),(2,'TVBox电视机顶盒'),(3,'DVD'),(4,'Projector投影仪'),(5,'其他');

/*IR型号*/
create table IRModel (
id bigint(20) NOT NULL AUTO_INCREMENT,
brandId bigint(20),
modelName varchar(50),
controllType int,
checked tinyint,
PRIMARY KEY (id),
KEY brandId_index (brandId)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/*IR数据*/
create table IRData (
id bigint(20) NOT NULL AUTO_INCREMENT,
data varchar(2000),
pulseCount int,
decodeType varchar(4),
modelId bigint(20),
PRIMARY KEY (id),
KEY modelId_index (modelId)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/*脉冲类型*/
create table IRPulseType (
id int NOT NULL,
pulseTypeName varchar(50),
PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
insert into IRPulseType values(1,'宽脉冲'),(2,'窄脉冲');

/*IR索引数据*/
create table IRIndexData (
id bigint(20) NOT NULL AUTO_INCREMENT,
seq int,
dataId bigint(20),
pulseType int,
bandwidth int,
pulseWidthMin int,
pulseWidthMax int,
PRIMARY KEY (id),
KEY seq_index (seq),
KEY dataId_index (dataId)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/*IR临时表*/
create table IRTemp (
id bigint(20) NOT NULL AUTO_INCREMENT,
seq int,
pulseWidth int,
data varchar(30),
createTime datetime DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/*IRData增加动作名称字段*/
alter table IRData add column actionName varchar(100);

/*---------------node推送增加脚本 begin----------------*/
alter table node add column nodeType tinyint default 0;
alter table node add column nodeName varchar(50);

create table nodeType(
id int NOT NULL,
enName varchar(30),
name varchar(30),
PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
insert into nodeType values(-1,'dtUnKnown','未知'),(0,'dtCoordinator','协调器'),(1,'dtRouter','路由'),(2,'dtEndDevice','EP设备');
/*--------------end---------------*/


/*houseservice 新增监控状态Monitor_state 邮箱email 默认邮箱defaultEmail 邮箱内容emaildescription*/
ALTER TABLE houseservice
ADD COLUMN monitor_state  tinyint(4) NULL DEFAULT NULL AFTER device_is_online,
ADD COLUMN email  varchar(50) NULL AFTER monitor_state,
ADD COLUMN defaultemail  varchar(50) NULL AFTER email,
ADD COLUMN emaildescription  varchar(200) NULL AFTER defaultemail;

-- 20140719
ALTER TABLE `modehouse`
ADD COLUMN `hosueGuid`  varchar(80) NULL AFTER `userId`;

ALTER TABLE `modedevicebind`
ADD COLUMN `sourceOldId`  bigint(20) NULL DEFAULT 0 AFTER `SourceID`,
ADD COLUMN `destOldId`  bigint(20) NULL DEFAULT 0 AFTER `DestID`;

ALTER TABLE `modegroupsub`
ADD COLUMN `deviceOldId`  bigint(20) NULL DEFAULT 0 AFTER `DeviceID`;

ALTER TABLE `modescenesub`
ADD COLUMN `deviceOldId`  bigint(20) NULL DEFAULT 0 AFTER `DeviceID`;

ALTER TABLE `modemacrosub`
ADD COLUMN `destOldId`  bigint(20) NULL DEFAULT 0 AFTER `Dest`;

ALTER TABLE `modeiasmain`
ADD COLUMN `modeEpOldId`  bigint(20) NULL DEFAULT 0 AFTER `ModeEpID`;

ALTER TABLE `modehvacmain`
ADD COLUMN `modeEpOldId`  bigint(20) NULL DEFAULT 0 AFTER `ModeEpID`;

ALTER TABLE `modedevice`
ADD COLUMN `oldModeNodeId`  bigint(20) NULL DEFAULT 0 AFTER `modeNodeId`;

/*2014-7-22新建表apponlineinfo  用于记录APP在线信息*/
CREATE TABLE apponlineinfo (
id  bigint(20) NOT NULL ,
username  varchar(20) NULL ,
app_os  varchar(20) NULL ,
app_version  varchar(20) NULL ,
IPaddress  varchar(20) NULL ,
PRIMARY KEY (id)
)
;

/*2014-7-22表apponlineinfo新增houseieee字段*/
ALTER TABLE apponlineinfo
ADD COLUMN houseieee  varchar(20) NULL AFTER IPaddress;

/*表apponlineinfo新增information字段*/
ALTER TABLE apponlineinfo
ADD COLUMN information  varchar(200) NULL;

ALTER table irmodel add dlTimes int default 0; /*下载次数*/
ALTER table irmodel add checkUserId bigint; /*检测用户*/
ALTER table irmodel add fileSize int; /*文件大小*/
ALTER table irmodel add uploadTime datetime default CURRENT_TIMESTAMP(); /*上传时间*/
ALTER table irmodel add uploadUserId bigint; /*上传用户*/

/*表device新增datecode字段*/
ALTER TABLE device
ADD COLUMN datecode  varchar(50) NULL AFTER lasttime;

/*house新增字段*/
alter TABLE house add column IPK_version varchar(50);
alter TABLE house add column HW_version varchar(50);

/*attributenamelib表重新创建和导入数据*/

ALTER TABLE `device`
MODIFY COLUMN `isonline`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' AFTER `AttrInfo`;

/*app在线信息表新增列广告网址adurl、广告读取状态readstate、云端发布网址时间createtime、APP查询网址时间lasttime、网址标题title*/
ALTER TABLE apponlineinfo
ADD COLUMN adurl  varchar(100) NULL AFTER information,
ADD COLUMN readstate  varchar(2) NULL AFTER adurl,
ADD COLUMN createtime  datetime NULL AFTER readstate,
ADD COLUMN lasttime  datetime NULL AFTER createtime,
ADD COLUMN title  varchar(100) NULL AFTER lasttime;

/*app在线信息表增加isonline字段*/
alter table Apponlineinfo add column isonline varchar(50) default 0; 

ALTER TABLE `reliuser`
MODIFY COLUMN `user_name`  varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL AFTER `id`;
ALTER TABLE `reliuser`
MODIFY COLUMN `pwd`  varchar(40) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL AFTER `user_name`;

-- 20140808
ALTER TABLE `modehouse`
ADD COLUMN `cloudIp`  varchar(200) NULL AFTER `description`;


