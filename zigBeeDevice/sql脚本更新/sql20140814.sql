/*修改device表，新增字段deviceattribute，该字段用于存放最新设备属性报告内容*/
ALTER TABLE device
ADD COLUMN deviceattribute  varchar(50);
/*修改device表，新增字段unit_type，该字段用于存放设备类型，0402、0401为ZONE设备*/
ALTER TABLE device
ADD COLUMN unit_type  varchar(50);

/*2014.08.21 zhuangxd 修改设备名称*/
update modenodelib set deviceName="云智能网关"  where modelId = "Z203";
update modeeplib set deviceName="云智能网关"  where internelModelId = "Z203-1";
update modenodelib set deviceName="ZB02I无线墙面紧急按键"  where modelId = "ZB02I";
update modeeplib set deviceName="ZB02I无线墙面紧急按键"  where internelModelId = "ZB02I-1";
update modenodelib set deviceName="ZB02C无线墙面控制开关"  where modelId = "ZB02C";
update modeeplib set deviceName="ZB02C无线墙面控制开关一路"  where internelModelId = "ZB02C-1";
update modeeplib set deviceName="ZB02C无线墙面控制开关二路"  where internelModelId = "ZB02C-2";
update modeeplib set deviceName="ZB02C无线墙面控制开关三路"  where internelModelId = "ZB02C-3";
update modenodelib set deviceName="Z716A无线室内温度感应器"  where modelId = "Z716A";
update modeeplib set deviceName="Z716A无线室内温度感应器"  where internelModelId = "Z716A-1";
update modenodelib set deviceName="Z211智能红外控制转换器"  where modelId = "Z211";
update modeeplib set deviceName="Z211智能红外控制转换器"  where internelModelId = "Z211-1";
update modenodelib set deviceName="Z312无线门铃按键"  where modelId = "Z312";
update modeeplib set deviceName="Z312无线门铃按键"  where internelModelId = "Z312-1";
update modenodelib set deviceName="Z602A智能声光报警器"  where modelId = "Z602A";
update modeeplib set deviceName="Z602A智能声光报警器"  where internelModelId = "Z602A-1";
update modenodelib set deviceName="ZB11B无线红外人体感应器"  where modelId = "ZB11B";
update modeeplib set deviceName="ZB11B无线红外人体感应器"  where internelModelId = "ZB11B-1";
update modenodelib set deviceName="Z809A无线耗能检测插座"  where modelId = "Z809A";
update modeeplib set deviceName="Z809A无线耗能检测插座"  where internelModelId = "Z809A-1";
update modenodelib set deviceName="Z311J无线窗磁感应器"  where modelId = "Z311J";
update modeeplib set deviceName="Z311J无线窗磁感应器"  where internelModelId = "Z311J-1";
update modenodelib set deviceName="Z602A智能声光报警器"  where modelId = "Z602A";
update modeeplib set deviceName="Z602A智能声光报警器"  where internelModelId = "Z602A-1";
update modenodelib set deviceName="ZB11B无线红外人体感应器"  where modelId = "ZB11B";
update modeeplib set deviceName="ZB11B无线红外人体感应器"  where internelModelId = "ZB11B-1";
update modenodelib set deviceName="Z809A无线耗能检测插座"  where modelId = "Z809A";
update modeeplib set deviceName="Z809A无线耗能检测插座"  where internelModelId = "Z809A-1";
update modenodelib set deviceName="Z311J无线窗磁感应器"  where modelId = "Z311J";
update modeeplib set deviceName="Z311J无线窗磁感应器开关"  where internelModelId = "Z311J-1";
update modeeplib set deviceName="Z311J无线安防窗磁感应器"  where internelModelId = "Z311J-2";
update modenodelib set deviceName="ZC07无线可调光LED灯泡"  where modelId = "ZC07";
update modeeplib set deviceName="ZC07无线可调光LED灯泡"  where internelModelId = "ZC07-1";
update modenodelib set deviceName="Z308无线紧急按钮"  where modelId = "Z308";
update modeeplib set deviceName="Z308无线紧急按钮"  where internelModelId = "Z308-1";


/*2014/8/21 pengcq 可靠性平台的动作历史记录要增加推送的动作*/
INSERT INTO operatelib(opname,operateName,operateNameCn) VALUES('rebuildNetworkByParam.cgi','rebuildNetworkByParam','重建网络');

UPDATE operatelib SET operateNameCn ='删除离家告警' WHERE opname = 'ArriveDel';
UPDATE operatelib SET operateNameCn ='增加离家告警' WHERE opname = 'ArriveNew';
UPDATE operatelib SET operateNameCn ='增加开锁用户' WHERE opname = 'DoorLockRemoteAddUser.cgi';
UPDATE operatelib SET operateNameCn ='删除开锁用户' WHERE opname = 'DoorLockRemoteDeleteUser.cgi';
UPDATE operatelib SET operateNameCn ='应用本地IR数据' WHERE operateName = 'ApplyDeviceIR';
UPDATE operatelib SET operateNameCn ='发送本地设备IR数据' WHERE operateName = 'ApplyDeviceFlashIR';

/*添加中英文字段*/
ALTER TABLE `deviceattrlib`
CHANGE COLUMN `Decimal` `UniqueName`  bigint(255) NULL DEFAULT NULL AFTER `DataType`;

/*修改deviceattrlib表设备英文属性名称*/
UPDATE `deviceattrlib` SET  `AttrName`='On/off status' WHERE (`ID`='1');
UPDATE `deviceattrlib` SET     `AttrName`='On/off status'  WHERE (`ID`='2');
UPDATE `deviceattrlib` SET     `AttrName`='On/off status' WHERE (`ID`='5');
UPDATE `deviceattrlib` SET      `AttrName`='On/off status'  WHERE (`ID`='10');
UPDATE `deviceattrlib` SET      `AttrName`='On/off status'  WHERE (`ID`='12');
UPDATE `deviceattrlib` SET      `AttrName`='On/off status'  WHERE (`ID`='13');
UPDATE `deviceattrlib` SET      `AttrName`='On/off status'  WHERE (`ID`='17');
UPDATE `deviceattrlib` SET      `AttrName`='On/off status'  WHERE (`ID`='19');
UPDATE `deviceattrlib` SET      `AttrName`='On/off status'  WHERE (`ID`='20');
UPDATE `deviceattrlib` SET      `AttrName`='Level'    WHERE (`ID`='21');
UPDATE `deviceattrlib` SET      `AttrName`='On/off status'  WHERE (`ID`='22');
UPDATE `deviceattrlib` SET      `AttrName`='On/off status'  WHERE (`ID`='23');
UPDATE `deviceattrlib` SET      `AttrName`='On/off status'  WHERE (`ID`='24');
UPDATE `deviceattrlib` SET      `AttrName`='On/off status'  WHERE (`ID`='25');
UPDATE `deviceattrlib` SET      `AttrName`='On/off status'  WHERE (`ID`='28');
UPDATE `deviceattrlib` SET      `AttrName`='On/off status'  WHERE (`ID`='30');
UPDATE `deviceattrlib` SET      `AttrName`='On/off status'  WHERE (`ID`='31');
UPDATE `deviceattrlib` SET      `AttrName`='On/off status'  WHERE (`ID`='32');
UPDATE `deviceattrlib` SET      `AttrName`='On/off status'  WHERE (`ID`='33');
UPDATE `deviceattrlib` SET      `AttrName`='On/off status'  WHERE (`ID`='34');
UPDATE `deviceattrlib` SET      `AttrName`='On/off status'  WHERE (`ID`='35');
UPDATE `deviceattrlib` SET      `AttrName`='On/off status'  WHERE (`ID`='37');
UPDATE `deviceattrlib` SET      `AttrName`='On/off status'  WHERE (`ID`='38');
UPDATE `deviceattrlib` SET      `AttrName`='On/off status'  WHERE (`ID`='39');
UPDATE `deviceattrlib` SET      `AttrName`='On/off status'  WHERE (`ID`='40');
UPDATE `deviceattrlib` SET      `AttrName`='On/off status'  WHERE (`ID`='41');
UPDATE `deviceattrlib` SET      `AttrName`='On/off status'  WHERE (`ID`='42');
UPDATE `deviceattrlib` SET      `AttrName`='On/off status' WHERE (`ID`='43');
UPDATE `deviceattrlib` SET      `AttrName`='Temperature'   WHERE (`ID`='44');
UPDATE `deviceattrlib` SET      `AttrName`='Temperature'   WHERE (`ID`='45');
UPDATE `deviceattrlib` SET      `AttrName`='Temperature'   WHERE (`ID`='46');
UPDATE `deviceattrlib` SET      `AttrName`='Temperature'   WHERE (`ID`='47');
UPDATE `deviceattrlib` SET      `AttrName`='Temperature'   WHERE (`ID`='48');
UPDATE `deviceattrlib` SET      `AttrName`='Illuminance'   WHERE (`ID`='51');
UPDATE `deviceattrlib` SET      `AttrName`='On/off status'  WHERE (`ID`='64');
UPDATE `deviceattrlib` SET      `AttrName`='Level'   WHERE (`ID`='65');
UPDATE `deviceattrlib` SET      `AttrName`='On/off status'  WHERE (`ID`='66');
UPDATE `deviceattrlib` SET     `AttrName`='Zone status'   WHERE (`ID`='68');
UPDATE `deviceattrlib` SET      `AttrName`='Occupancy'  WHERE (`ID`='69');
UPDATE `deviceattrlib` SET     `AttrName`='Zone status'   WHERE (`ID`='70');
UPDATE `deviceattrlib` SET      `AttrName`='Temperature'   WHERE (`ID`='72');
UPDATE `deviceattrlib` SET     `AttrName`='Temperature'   WHERE (`ID`='74');
UPDATE `deviceattrlib` SET      `AttrName`='Occupancy'  WHERE (`ID`='75');
UPDATE `deviceattrlib` SET     `AttrName`='Zone status'   WHERE (`ID`='76');
UPDATE `deviceattrlib` SET     `AttrName`='Zone status'   WHERE (`ID`='77');
UPDATE `deviceattrlib` SET     `AttrName`='Zone status'   WHERE (`ID`='78');
UPDATE `deviceattrlib` SET     `AttrName`='Zone status'   WHERE (`ID`='79');
UPDATE `deviceattrlib` SET     `AttrName`='Zone status'   WHERE (`ID`='82');
UPDATE `deviceattrlib` SET    `AttrName`='Zone status'   WHERE (`ID`='83');
UPDATE `deviceattrlib` SET     `AttrName`='Zone status'   WHERE (`ID`='84');
UPDATE `deviceattrlib` SET     `AttrName`='Zone status'   WHERE (`ID`='86');
UPDATE `deviceattrlib` SET     `AttrName`='Zone status'   WHERE (`ID`='87');
UPDATE `deviceattrlib` SET     `AttrName`='Zone status'   WHERE (`ID`='88');
UPDATE `deviceattrlib` SET     `AttrName`='Zone status'   WHERE (`ID`='89');
UPDATE `deviceattrlib` SET     `AttrName`='Zone status'   WHERE (`ID`='90');
UPDATE `deviceattrlib` SET     `AttrName`='Zone status'   WHERE (`ID`='91');
UPDATE `deviceattrlib` SET     `AttrName`='Zone status'   WHERE (`ID`='92');
UPDATE `deviceattrlib` SET      `AttrName`='On/off status'  WHERE (`ID`='93');
UPDATE `deviceattrlib` SET      `AttrName`='Lock status'  WHERE (`ID`='94');
UPDATE `deviceattrlib` SET    `AttrName`='Zone status'   WHERE (`ID`='96');
UPDATE `deviceattrlib` SET      `AttrName`='On/off status'  WHERE (`ID`='123');
UPDATE `deviceattrlib` SET     `AttrName`='Zone status'   WHERE (`ID`='133');
UPDATE `deviceattrlib` SET      `AttrName`='On/off status'  WHERE (`ID`='138');
UPDATE `deviceattrlib` SET      `AttrName`='Illuminance'   WHERE (`ID`='139');
UPDATE `deviceattrlib` SET     `AttrName`='Current'   WHERE (`ID`='140');
UPDATE `deviceattrlib` SET      `AttrName`='Voltage'   WHERE (`ID`='141');
UPDATE `deviceattrlib` SET      `AttrName`='Power'   WHERE (`ID`='142');
UPDATE `deviceattrlib` SET      `AttrName`='Energy'   WHERE (`ID`='143');
UPDATE `deviceattrlib` SET      `AttrName`='Current'   WHERE (`ID`='144');
UPDATE `deviceattrlib` SET      `AttrName`='Voltage'   WHERE (`ID`='145');
UPDATE `deviceattrlib` SET     `AttrName`='Power'   WHERE (`ID`='146');
UPDATE `deviceattrlib` SET      `AttrName`='Energy'   WHERE (`ID`='147');
UPDATE `deviceattrlib` SET      `AttrName`='Current'   WHERE (`ID`='148');
UPDATE `deviceattrlib` SET      `AttrName`='Voltage'   WHERE (`ID`='149');
UPDATE `deviceattrlib` SET      `AttrName`='Power'   WHERE (`ID`='150');
UPDATE `deviceattrlib` SET      `AttrName`='Energy'   WHERE (`ID`='151');
UPDATE `deviceattrlib` SET      `AttrName`='Current'   WHERE (`ID`='153');
UPDATE `deviceattrlib` SET      `AttrName`='Voltage'   WHERE (`ID`='154');
UPDATE `deviceattrlib` SET      `AttrName`='Power'   WHERE (`ID`='155');
UPDATE `deviceattrlib` SET      `AttrName`='Energy'   WHERE (`ID`='156');
UPDATE `deviceattrlib` SET      `AttrName`='Current'   WHERE (`ID`='157');
UPDATE `deviceattrlib` SET      `AttrName`='Voltage'   WHERE (`ID`='158');
UPDATE `deviceattrlib` SET      `AttrName`='Power'   WHERE (`ID`='159');
UPDATE `deviceattrlib` SET      `AttrName`='Energy'   WHERE (`ID`='160');
UPDATE `deviceattrlib` SET      `AttrName`='Current'   WHERE (`ID`='161');
UPDATE `deviceattrlib` SET      `AttrName`='Voltage'   WHERE (`ID`='162');
UPDATE `deviceattrlib` SET      `AttrName`='Power'   WHERE (`ID`='163');
UPDATE `deviceattrlib` SET      `AttrName`='Energy'   WHERE (`ID`='164');
UPDATE `deviceattrlib` SET      `AttrName`='Current'   WHERE (`ID`='165');
UPDATE `deviceattrlib` SET      `AttrName`='Voltage'   WHERE (`ID`='166');
UPDATE `deviceattrlib` SET      `AttrName`='Power'   WHERE (`ID`='167');
UPDATE `deviceattrlib` SET      `AttrName`='Energy'   WHERE (`ID`='168');
UPDATE `deviceattrlib` SET      `AttrName`='Current'   WHERE (`ID`='169');
UPDATE `deviceattrlib` SET      `AttrName`='Voltage'   WHERE (`ID`='170');
UPDATE `deviceattrlib` SET      `AttrName`='Power'   WHERE (`ID`='171');
UPDATE `deviceattrlib` SET      `AttrName`='Energy'   WHERE (`ID`='172');
UPDATE `deviceattrlib` SET      `AttrName`='Current'   WHERE (`ID`='173');
UPDATE `deviceattrlib` SET      `AttrName`='Voltage'   WHERE (`ID`='174');
UPDATE `deviceattrlib` SET      `AttrName`='Power'   WHERE (`ID`='175');
UPDATE `deviceattrlib` SET      `AttrName`='Energy'   WHERE (`ID`='176');
UPDATE `deviceattrlib` SET      `AttrName`='Current'   WHERE (`ID`='177');
UPDATE `deviceattrlib` SET      `AttrName`='Voltage'   WHERE (`ID`='178');
UPDATE `deviceattrlib` SET      `AttrName`='Power'   WHERE (`ID`='179');
UPDATE `deviceattrlib` SET      `AttrName`='Energy'   WHERE (`ID`='180');
UPDATE `deviceattrlib` SET      `AttrName`='Door status'  WHERE (`ID`='181');
UPDATE `deviceattrlib` SET      `AttrName`='Current'   WHERE (`ID`='182');
UPDATE `deviceattrlib` SET      `AttrName`='Voltage'   WHERE (`ID`='183');
UPDATE `deviceattrlib` SET      `AttrName`='Power'   WHERE (`ID`='184');
UPDATE `deviceattrlib` SET      `AttrName`='Energy'   WHERE (`ID`='185');
UPDATE `deviceattrlib` SET      `AttrName`='Current'   WHERE (`ID`='186');
UPDATE `deviceattrlib` SET      `AttrName`='Voltage'   WHERE (`ID`='187');
UPDATE `deviceattrlib` SET      `AttrName`='Power'   WHERE (`ID`='188');
UPDATE `deviceattrlib` SET      `AttrName`='Energy'   WHERE (`ID`='189');
UPDATE `deviceattrlib` SET      `AttrName`='Level'   WHERE (`ID`='190');
UPDATE `deviceattrlib` SET      `AttrName`='Level'   WHERE (`ID`='191');
UPDATE `deviceattrlib` SET      `AttrName`='Current'   WHERE (`ID`='192');
UPDATE `deviceattrlib` SET      `AttrName`='Voltage'   WHERE (`ID`='193');
UPDATE `deviceattrlib` SET      `AttrName`='Power'   WHERE (`ID`='194');
UPDATE `deviceattrlib` SET      `AttrName`='Energy'   WHERE (`ID`='195');
UPDATE `deviceattrlib` SET      `AttrName`='Current'   WHERE (`ID`='196');
UPDATE `deviceattrlib` SET      `AttrName`='Voltage'   WHERE (`ID`='197');
UPDATE `deviceattrlib` SET      `AttrName`='Power'   WHERE (`ID`='198');
UPDATE `deviceattrlib` SET      `AttrName`='Energy'   WHERE (`ID`='199');
UPDATE `deviceattrlib` SET      `AttrName`='Level'   WHERE (`ID`='200');
UPDATE `deviceattrlib` SET      `AttrName`='Level'   WHERE (`ID`='201');
UPDATE `deviceattrlib` SET      `AttrName`='Level'   WHERE (`ID`='202');
UPDATE `deviceattrlib` SET      `AttrName`='Current'   WHERE (`ID`='203');
UPDATE `deviceattrlib` SET      `AttrName`='Voltage'   WHERE (`ID`='204');
UPDATE `deviceattrlib` SET      `AttrName`='Power'   WHERE (`ID`='205');
UPDATE `deviceattrlib` SET      `AttrName`='Energy'   WHERE (`ID`='206');
UPDATE `deviceattrlib` SET      `AttrName`='Current'   WHERE (`ID`='207');
UPDATE `deviceattrlib` SET      `AttrName`='Voltage'   WHERE (`ID`='208');
UPDATE `deviceattrlib` SET      `AttrName`='Power'   WHERE (`ID`='209');
UPDATE `deviceattrlib` SET      `AttrName`='Energy'   WHERE (`ID`='210');
UPDATE `deviceattrlib` SET      `AttrName`='Current'   WHERE (`ID`='211');
UPDATE `deviceattrlib` SET      `AttrName`='Voltage'   WHERE (`ID`='212');
UPDATE `deviceattrlib` SET      `AttrName`='Power'   WHERE (`ID`='213');
UPDATE `deviceattrlib` SET      `AttrName`='Energy'   WHERE (`ID`='214');
UPDATE `deviceattrlib` SET      `AttrName`='Current'   WHERE (`ID`='215');
UPDATE `deviceattrlib` SET      `AttrName`='Voltage'   WHERE (`ID`='216');
UPDATE `deviceattrlib` SET      `AttrName`='Power'   WHERE (`ID`='217');
UPDATE `deviceattrlib` SET      `AttrName`='Energy'   WHERE (`ID`='218');
UPDATE `deviceattrlib` SET      `AttrName`='Current'   WHERE (`ID`='219');
UPDATE `deviceattrlib` SET      `AttrName`='Voltage'   WHERE (`ID`='220');
UPDATE `deviceattrlib` SET      `AttrName`='Power'   WHERE (`ID`='221');
UPDATE `deviceattrlib` SET      `AttrName`='Energy'   WHERE (`ID`='222');
UPDATE `deviceattrlib` SET      `AttrName`='Current'   WHERE (`ID`='223');
UPDATE `deviceattrlib` SET      `AttrName`='Voltage'   WHERE (`ID`='224');
UPDATE `deviceattrlib` SET      `AttrName`='Power'   WHERE (`ID`='225');
UPDATE `deviceattrlib` SET      `AttrName`='Energy'   WHERE (`ID`='226');
UPDATE `deviceattrlib` SET      `AttrName`='Level'   WHERE (`ID`='227');
UPDATE `deviceattrlib` SET     `AttrName`='Humidity'   WHERE (`ID`='228');
UPDATE `deviceattrlib` SET     `AttrName`='Humidity'   WHERE (`ID`='229');
UPDATE `deviceattrlib` SET     `AttrName`='Humidity'   WHERE (`ID`='230');
UPDATE `deviceattrlib` SET     `AttrName`='Humidity'   WHERE (`ID`='231');
UPDATE `deviceattrlib` SET     `AttrName`='Zone status'   WHERE (`ID`='233');
UPDATE `deviceattrlib` SET     `AttrName`='Zone status'   WHERE (`ID`='234');
UPDATE `deviceattrlib` SET      `AttrName`='Current'   WHERE (`ID`='235');
UPDATE `deviceattrlib` SET      `AttrName`='Voltage'   WHERE (`ID`='236');
UPDATE `deviceattrlib` SET      `AttrName`='Power'   WHERE (`ID`='237');
UPDATE `deviceattrlib` SET      `AttrName`='Energy'   WHERE (`ID`='238');
UPDATE `deviceattrlib` SET      `AttrName`='Current'   WHERE (`ID`='239');
UPDATE `deviceattrlib` SET      `AttrName`='Voltage'   WHERE (`ID`='240');
UPDATE `deviceattrlib` SET      `AttrName`='Power'   WHERE (`ID`='241');
UPDATE `deviceattrlib` SET      `AttrName`='Energy'   WHERE (`ID`='242');
UPDATE `deviceattrlib` SET      `AttrName`='Current'   WHERE (`ID`='243');
UPDATE `deviceattrlib` SET      `AttrName`='Voltage'   WHERE (`ID`='244');
UPDATE `deviceattrlib` SET      `AttrName`='Power'   WHERE (`ID`='245');
UPDATE `deviceattrlib` SET      `AttrName`='Energy'   WHERE (`ID`='246');
UPDATE `deviceattrlib` SET      `AttrName`='Current'   WHERE (`ID`='259');
UPDATE `deviceattrlib` SET      `AttrName`='Voltage'   WHERE (`ID`='260');
UPDATE `deviceattrlib` SET      `AttrName`='Power'   WHERE (`ID`='261');
UPDATE `deviceattrlib` SET      `AttrName`='Energy'   WHERE (`ID`='262');
UPDATE `deviceattrlib` SET      `AttrName`='Current'   WHERE (`ID`='263');
UPDATE `deviceattrlib` SET      `AttrName`='Voltage'   WHERE (`ID`='264');
UPDATE `deviceattrlib` SET      `AttrName`='Power'   WHERE (`ID`='265');
UPDATE `deviceattrlib` SET      `AttrName`='Energy'   WHERE (`ID`='266');
UPDATE `deviceattrlib` SET      `AttrName`='Current'   WHERE (`ID`='267');
UPDATE `deviceattrlib` SET      `AttrName`='Voltage'   WHERE (`ID`='268');
UPDATE `deviceattrlib` SET      `AttrName`='Power'   WHERE (`ID`='269');
UPDATE `deviceattrlib` SET      `AttrName`='Energy'   WHERE (`ID`='270');
UPDATE `deviceattrlib` SET      `AttrName`='On/off status'  WHERE (`ID`='271');
UPDATE `deviceattrlib` SET      `AttrName`='Level'   WHERE (`ID`='272');
UPDATE `deviceattrlib` SET     `AttrName`='Zone status'   WHERE (`ID`='273');
UPDATE `deviceattrlib` SET      `AttrName`='On/off status'  WHERE (`ID`='274');
UPDATE `deviceattrlib` SET      `AttrName`='On/off status'  WHERE (`ID`='275');
UPDATE `deviceattrlib` SET      `AttrName`='On/off status'  WHERE (`ID`='276');
UPDATE `deviceattrlib` SET      `AttrName`='On/off status'  WHERE (`ID`='277');
UPDATE `deviceattrlib` SET      `AttrName`='On/off status'  WHERE (`ID`='278');
UPDATE `deviceattrlib` SET      `AttrName`='On/off status'  WHERE (`ID`='279');
UPDATE `deviceattrlib` SET     `AttrName`='Zone status'   WHERE (`ID`='280');
UPDATE `deviceattrlib` SET     `AttrName`='Zone status'   WHERE (`ID`='281');
UPDATE `deviceattrlib` SET      `AttrName`='Level'   WHERE (`ID`='282');

/*表irbrand新增字段enbrandname，region，firstchar 英文品牌名称、地区、首字母*/
ALTER TABLE irbrand
ADD COLUMN enbrandName varchar(100) NULL AFTER brandName,
ADD COLUMN region  varchar(100) NULL AFTER enbrandName,
ADD COLUMN firstchar varchar(100) NULL AFTER region;

/*irdata 新增三个字段IRCommdid、CommdSequence、actionNameDescription IRData要用到的序号以及actionName描述*/
ALTER TABLE irdata
ADD COLUMN IRCommdid  int(20) NULL AFTER actionName,
ADD COLUMN CommdSequence  int(20) NULL AFTER IRCommdid,
ADD COLUMN actionNameDescription varchar(200) NULL AFTER CommdSequence;

/*irmodel 新增两个字段deviceStyle、remoteControlStyle备用*/
ALTER TABLE irmodel
ADD COLUMN deviceStyle varchar(50) NULL AFTER uploadUserId,
ADD COLUMN remoteControlStyle  varchar(50) NULL AFTER deviceStyle;

/* Z206设备添加 2014.09.10 */
INSERT INTO `modenodelib` (`id`, `nodeType`, `modelId`, `deviceName`, `clusterId`, `clusterName`, `destType`, `source_id`, `deviceType`, `picName`, `description`, `powerType`, `activationMethod`, `resetDefault`, `remark`) VALUES ('145', '11', 'Z206', 'Z206云智能网关', '', '', '', '1', '', 'Z206.jpg', 'Z206作为奈伯思智能家居系统的核心网关，率先实现云端技术、WiFi移动联网技术与ZigBee智能家居物联网术的完美结合，让用户只需要通过手机APP连接Wifi即可控制家里的灯光、窗帘、各种电器等设备的开关，出门在外通过2G/3G网络可监控家中的一切变化。', '使用DC 12V  1.5A电源适配器，接入100-220V的电源', '不需要激活', '详见206说明书', 'Z206作为奈伯思智能家居系统的核心网关，率先实现云端技术、WiFi移动联网技术与ZigBee智能家居物联网术的完美结合，让用户只需要通过手机APP连接Wifi即可控制家里的灯光、窗帘、各种电器等设备的开关，出门在外通过2G/3G网络可监控家中的一切变化。');
INSERT INTO `modeeplib` (`id`, `nodeId`, `deviceId`, `internelModelId`, `deviceName`, `clusterId`, `clusterName`, `picName`, `destType`, `deviceType`, `deviceTypeV2`, `ep`, `description`, `Groupable`) VALUES ('241', '145', '0007', 'Z206-1', 'Z206云智能网关', '', '', '', '', '', '1', '0A', '', '0');
update modenodelib set deviceName="Z203云智能网关"  where modelId = "Z203";
update modeeplib set deviceName="Z203云智能网关"  where internelModelId = "Z203-1";

/* irtemp增加data长度*/
alter table irtemp modify data VARCHAR(2000);

/*irtemp seq字段增加索引*/
create index irtemp_seq_key on irtemp(seq);

/*获取匹配的品牌型号时会用到*/
create index irdata_cs_index on irdata(CommdSequence);


