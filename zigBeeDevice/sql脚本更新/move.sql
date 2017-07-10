------导出脚本------
#导出家庭表
SELECT house.houseIEEE, house.`name`, house.longitude, house.latitude, house.networkAddress, house.`port`, house.description, house.enableFlag, house.regionCode, house.isonline, house.createtime, house.lasttime, house.clientCode, house.IPK_version, house.HW_version  INTO OUTFILE 'e:/h1.txt' FROM zigbeedevice.house house WHERE house.houseIEEE = '00137A000001013b';

SELECT device.houseIEEE, device.roomId, device.nodeId, device.deviceName, device.ieee, device.ep, device.type, device.device_id, device.source_id, device.device_type, device.AttrInfo, device.isonline, device.createtime, device.lasttime, device.datecode, device.deviceattribute, device.unit_type  INTO OUTFILE 'e:/h2.txt' FROM zigbeedevice.device WHERE device.houseIEEE = '00137A000001013b';

SELECT deviceattribute.houseIEEE, deviceattribute.room_id, deviceattribute.daemonDeviceId, deviceattribute.source_id, deviceattribute.deviceType, deviceattribute.device_ieee, deviceattribute.device_ep, deviceattribute.id_string3, deviceattribute.cluster_id, deviceattribute.attribute_id, deviceattribute.attributeName, deviceattribute.`value`, deviceattribute.lasttime  INTO OUTFILE 'e:/h3.txt'  FROM zigbeedevice.deviceattribute WHERE deviceattribute.houseIEEE = '00137A000001013b';

SELECT node.houseIEEE, node.roomId, node.ieee, node.type, node.ip, node.nodeId, node.nodeType, node.nodeName  INTO OUTFILE 'e:/h4.txt'  FROM zigbeedevice.node WHERE node.houseIEEE = '00137A000001013b';

SELECT houseieee.houseIEEE, houseieee.`name`, houseieee.secretKey, houseieee.vendorCode, houseieee.vendorName, houseieee.encodemethod, houseieee.xmppIp, houseieee.xmppPort, houseieee.cloudIp1, houseieee.cloudPort1, houseieee.cloudIp2, houseieee.cloudPort2, houseieee.description, houseieee.enableFlag, houseieee.createtime, houseieee.lasttime  INTO OUTFILE 'e:/h5.txt' FROM zigbeedevice.houseieee WHERE houseieee.houseIEEE = '00137A000001013b';

SELECT proxyserver.houseIEEE, proxyserver.`name`, proxyserver.serverIp, proxyserver.serverPort, proxyserver.type, proxyserver.secondType, proxyserver.description, proxyserver.enableFlag, proxyserver.createtime, proxyserver.lasttime  INTO OUTFILE 'e:/h6.txt' FROM zigbeedevice.proxyserver WHERE proxyserver.houseIEEE = '00137A000001013b';

#导出电能表
SELECT energy.`name`, energy.houseIEEE, energy.priceType, energy.DSTFlag, energy.DSTbeginTime, energy.DSTendTime, energy.regionCode, energy.energyRefLibId, energy.syncFlag, energy.enabledFlag  INTO OUTFILE 'e:/e1.txt' FROM zigbeedevice.energy WHERE energy.houseIEEE = '00137A000001013b';

SELECT energyfield.houseIEEE, energyfield.energyId, energyfield.energyFieldId, energyfield.`name`, energyfield.startField, energyfield.endField  INTO OUTFILE 'e:/e2.txt' FROM zigbeedevice.energyfield WHERE energyfield.houseIEEE = '00137A000001013b';

SELECT energytime.houseIEEE, energytime.energyId, energytime.energyTimeId, energytime.`name`, energytime.beginTime, energytime.endTime INTO OUTFILE 'e:/e3.txt' FROM zigbeedevice.energytime WHERE energytime.houseIEEE = '00137A000001013b';

SELECT priceparam.houseIEEE, priceparam.energyId, priceparam.energyFieldId, priceparam.energyTimeId, priceparam.`name`, priceparam.price, priceparam.priceDST INTO OUTFILE 'e:/e4.txt' FROM zigbeedevice.priceparam WHERE priceparam.houseIEEE = '00137A000001013b';

SELECT houseparam.houseIEEE, houseparam.totalNumber, houseparam.totalSquare INTO OUTFILE 'e:/e5.txt' FROM zigbeedevice.houseparam WHERE houseparam.houseIEEE = '00137A000001013b';


#导出电能、家庭历史表
SELECT energyhistory_2014.houseIEEE, energyhistory_2014.IEEE, energyhistory_2014.EP, energyhistory_2014.opdatetime, energyhistory_2014.energyValue, energyhistory_2014.energyPrice INTO OUTFILE 'e:/h7.txt' FROM zigbeedevice.energyhistory_2014 WHERE energyhistory_2014.houseIEEE = '00137A000001013b';

Select * From zigbeedevice.deviceoperatehistory_00137a000001013b_2014 Into OutFile 'e:/h8.txt';

Select * From zigbeedevice.deviceattributehistory_00137a000001013b_2014 Into OutFile 'e:/h9.txt';

Select * From zigbeedevice.devicewarnhistory_00137a000001013b_2014 Into OutFile 'e:/h10.txt';

#导出openfire表
SELECT ofuser.username, ofuser.plainPassword, ofuser.encryptedPassword, ofuser.`name`, ofuser.email, ofuser.creationDate, ofuser.modificationDate INTO OUTFILE 'e:/o1.txt'  FROM openfire.ofuser WHERE ofuser.username like '%00137A000001013b%';

SELECT ofroster.username, ofroster.jid, ofroster.sub, ofroster.ask, ofroster.recv, ofroster.nick INTO OUTFILE 'e:/o2.txt' FROM openfire.ofroster WHERE (ofroster.jid like '%00137A000001013b%' or ofroster.username like '%00137A000001013b%');


------导入脚本------
#导入家庭表
Load Data InFile 'e:/h1.txt' Into Table zigbeedevice.house (house.houseIEEE, house.`name`, house.longitude, house.latitude, house.networkAddress, house.`port`, house.description, house.enableFlag, house.regionCode, house.isonline, house.createtime, house.lasttime, house.clientCode, house.IPK_version, house.HW_version);

Load Data InFile 'e:/h2.txt' Into Table zigbeedevice.device (device.houseIEEE, device.roomId, device.nodeId, device.deviceName, device.ieee, device.ep, device.type, device.device_id, device.source_id, device.device_type, device.AttrInfo, device.isonline, device.createtime, device.lasttime, device.datecode, device.deviceattribute, device.unit_type);

Load Data InFile 'e:/h3.txt' Into Table zigbeedevice.deviceattribute (deviceattribute.houseIEEE, deviceattribute.room_id, deviceattribute.daemonDeviceId, deviceattribute.source_id, deviceattribute.deviceType, deviceattribute.device_ieee, deviceattribute.device_ep, deviceattribute.id_string3, deviceattribute.cluster_id, deviceattribute.attribute_id, deviceattribute.attributeName, deviceattribute.`value`, deviceattribute.lasttime);

Load Data InFile 'e:/h4.txt' Into Table zigbeedevice.node (node.houseIEEE, node.roomId, node.ieee, node.type, node.ip, node.nodeId, node.nodeType, node.nodeName);

Load Data InFile 'e:/h5.txt' Into Table zigbeedevice.houseieee (houseieee.houseIEEE, houseieee.`name`, houseieee.secretKey, houseieee.vendorCode, houseieee.vendorName, houseieee.encodemethod, houseieee.xmppIp, houseieee.xmppPort, houseieee.cloudIp1, houseieee.cloudPort1, houseieee.cloudIp2, houseieee.cloudPort2, houseieee.description, houseieee.enableFlag, houseieee.createtime, houseieee.lasttime);

Load Data InFile 'e:/h6.txt' Into Table zigbeedevice.proxyserver (proxyserver.houseIEEE, proxyserver.`name`, proxyserver.serverIp, proxyserver.serverPort, proxyserver.type, proxyserver.secondType, proxyserver.description, proxyserver.enableFlag, proxyserver.createtime, proxyserver.lasttime);

#导入电能表
Load Data InFile 'e:/e1.txt' Into Table zigbeedevice.energy (energy.`name`, energy.houseIEEE, energy.priceType, energy.DSTFlag, energy.DSTbeginTime, energy.DSTendTime, energy.regionCode, energy.energyRefLibId, energy.syncFlag, energy.enabledFlag);

Load Data InFile 'e:/e2.txt' Into Table zigbeedevice.energyfield (energyfield.houseIEEE, energyfield.energyId, energyfield.energyFieldId, energyfield.`name`, energyfield.startField, energyfield.endField);

Load Data InFile 'e:/e3.txt' Into Table zigbeedevice.energytime (energytime.houseIEEE, energytime.energyId, energytime.energyTimeId, energytime.`name`, energytime.beginTime, energytime.endTime);

Load Data InFile 'e:/e4.txt' Into Table zigbeedevice.priceparam (priceparam.houseIEEE, priceparam.energyId, priceparam.energyFieldId, priceparam.energyTimeId, priceparam.`name`, priceparam.price, priceparam.priceDST);

Load Data InFile 'e:/e5.txt' Into Table zigbeedevice.houseparam (houseparam.houseIEEE, houseparam.totalNumber, houseparam.totalSquare);

update energyfield set energyId=(select id from energy where houseIEEE='00137A000001013b')where houseIEEE='00137A000001013b';

update energytime set energyId=(select id from energy where houseIEEE='00137A000001013b')where houseIEEE='00137A000001013b';

update priceparam set energyId=(select id from energy where houseIEEE='00137A000001013b')where houseIEEE='00137A000001013b';


#导入电能、家庭历史表
Load Data InFile 'e:/h7.txt' Into Table zigbeedevice.energyhistory_2014 
(energyhistory_2014.houseIEEE, energyhistory_2014.IEEE, energyhistory_2014.EP, energyhistory_2014.opdatetime, energyhistory_2014.energyValue, energyhistory_2014.energyPrice);

DROP TABLE IF EXISTS `deviceoperatehistory_00137a000001013b_2014`;
CREATE TABLE `deviceoperatehistory_00137a000001013b_2014` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `houseIEEE` varchar(50) DEFAULT NULL,
  `username` varchar(100) DEFAULT NULL,
  `room_id` bigint(20) DEFAULT NULL,
  `daemonDeviceId` bigint(20) DEFAULT '0',
  `source_id` bigint(20) DEFAULT '1',
  `deviceType` bigint(20) DEFAULT '1',
  `device_ieee` varchar(50) DEFAULT NULL,
  `device_ep` varchar(50) DEFAULT NULL,
  `id_string3` varchar(50) DEFAULT NULL,
  `opname` varchar(200) DEFAULT NULL,
  `optype` varchar(20) DEFAULT NULL,
  `opparam` varchar(2000) DEFAULT NULL,
  `opdatetime` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `opname` (`opname`),
  KEY `optype` (`optype`),
  KEY `opdatetime` (`opdatetime`),
  KEY `houseIEEE, room_id, device_ieee, device_ep` (`houseIEEE`,`room_id`,`device_ieee`,`device_ep`) USING BTREE,
  KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3953 DEFAULT CHARSET=utf8;
Load Data InFile 'e:/h8.txt' Into Table `deviceoperatehistory_00137a000001013b_2014`; #Lines Terminated By '\r\n';

DROP TABLE IF EXISTS `deviceattributehistory_00137a000001013b_2014`;
CREATE TABLE `deviceattributehistory_00137a000001013b_2014` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `houseIEEE` varchar(50) DEFAULT NULL,
  `room_id` bigint(20) DEFAULT NULL,
  `daemonDeviceId` bigint(20) DEFAULT '0',
  `source_id` bigint(20) DEFAULT '1',
  `deviceType` bigint(20) DEFAULT '1',
  `device_ieee` varchar(50) DEFAULT NULL,
  `device_ep` varchar(50) DEFAULT NULL,
  `id_string3` varchar(50) DEFAULT NULL,
  `cluster_id` varchar(50) DEFAULT NULL,
  `attribute_id` varchar(50) DEFAULT NULL,
  `attributeName` varchar(50) DEFAULT NULL,
  `value` varchar(50) DEFAULT NULL,
  `opdatetime` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `opdatetime` (`opdatetime`) USING BTREE,
  KEY `houseIEEE, room_id, device_ieee, device_ep, cluster_id, attribut` (`houseIEEE`,`room_id`,`device_ieee`,`device_ep`,`cluster_id`,`attribute_id`) USING BTREE,
  KEY `attributeName` (`attributeName`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1481671 DEFAULT CHARSET=utf8;
Load Data InFile 'e:/h9.txt' Into Table `deviceattributehistory_00137a000001013b_2014`; #Lines Terminated By '\r\n';

DROP TABLE IF EXISTS `devicewarnhistory_00137a000001013b_2014`;
CREATE TABLE `devicewarnhistory_00137a000001013b_2014` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `houseIEEE` varchar(50) DEFAULT NULL,
  `room_id` bigint(20) DEFAULT NULL,
  `daemonDeviceId` bigint(20) DEFAULT '0',
  `source_id` bigint(20) DEFAULT '1',
  `deviceType` bigint(20) DEFAULT '1',
  `zone_ieee` varchar(50) DEFAULT NULL,
  `zone_ep` varchar(50) DEFAULT NULL,
  `id_string3` varchar(50) DEFAULT NULL,
  `cie_ieee` varchar(50) DEFAULT NULL,
  `cie_ep` varchar(50) DEFAULT NULL,
  `w_mode` varchar(100) DEFAULT NULL,
  `w_description` varchar(1000) DEFAULT NULL,
  `warndatetime` datetime DEFAULT CURRENT_TIMESTAMP,
  `sendState` varchar(2) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `w_mode` (`w_mode`),
  KEY `warndatetime` (`warndatetime`),
  KEY `houseIEEE, room_id, zone_ieee, zone_ep, cie_ieee, cie_ep` (`houseIEEE`,`room_id`,`zone_ieee`,`zone_ep`,`cie_ieee`,`cie_ep`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=140 DEFAULT CHARSET=utf8;
Load Data InFile 'e:/h10.txt' Into Table `devicewarnhistory_00137a000001013b_2014`; #Lines Terminated By '\r\n';

#导入openfire表
Load Data InFile 'e:/o1.txt' Into Table openfire.ofuser (ofuser.username, ofuser.plainPassword, ofuser.encryptedPassword, ofuser.`name`, ofuser.email, ofuser.creationDate, ofuser.modificationDate);

Load Data InFile 'e:/o2.txt' Into Table openfire.ofroster (ofroster.username, ofroster.jid, ofroster.sub, ofroster.ask, ofroster.recv, ofroster.nick);