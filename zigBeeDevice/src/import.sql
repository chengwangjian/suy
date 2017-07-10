Load Data local InFile 'd:/h1.txt' Into Table zigbeedevice.house (house.houseIEEE, house.`name`, house.longitude, house.latitude, house.networkAddress, house.`port`, house.description, house.enableFlag, house.regionCode, house.isonline, house.createtime, house.lasttime, house.clientCode, house.IPK_version, house.HW_version);

Load Data local InFile 'd:/h2.txt' Into Table zigbeedevice.device (device.houseIEEE, device.roomId, device.nodeId, device.deviceName, device.ieee, device.ep, device.type, device.device_id, device.source_id, device.device_type, device.AttrInfo, device.isonline, device.createtime, device.lasttime, device.datecode, device.deviceattribute, device.unit_type);

Load Data local InFile 'd:/h3.txt' Into Table zigbeedevice.deviceattribute (deviceattribute.houseIEEE, deviceattribute.room_id, deviceattribute.daemonDeviceId, deviceattribute.source_id, deviceattribute.deviceType, deviceattribute.device_ieee, deviceattribute.device_ep, deviceattribute.id_string3, deviceattribute.cluster_id, deviceattribute.attribute_id, deviceattribute.attributeName, deviceattribute.`value`, deviceattribute.lasttime);

Load Data local InFile 'd:/h4.txt' Into Table zigbeedevice.node (node.houseIEEE, node.roomId, node.ieee, node.type, node.ip, node.nodeId, node.nodeType, node.nodeName);

Load Data local InFile 'd:/h5.txt' Into Table zigbeedevice.houseieee (houseieee.houseIEEE, houseieee.`name`, houseieee.secretKey, houseieee.vendorCode, houseieee.vendorName, houseieee.encodemethod, houseieee.xmppIp, houseieee.xmppPort, houseieee.cloudIp1, houseieee.cloudPort1, houseieee.cloudIp2, houseieee.cloudPort2, houseieee.description, houseieee.enableFlag, houseieee.createtime, houseieee.lasttime);

Load Data local InFile 'd:/h6.txt' Into Table zigbeedevice.proxyserver (proxyserver.houseIEEE, proxyserver.`name`, proxyserver.serverIp, proxyserver.serverPort, proxyserver.type, proxyserver.secondType, proxyserver.description, proxyserver.enableFlag, proxyserver.createtime, proxyserver.lasttime);

Load Data local InFile 'd:/e1.txt' Into Table zigbeedevice.energy (energy.`name`, energy.houseIEEE, energy.priceType, energy.DSTFlag, energy.DSTbeginTime, energy.DSTendTime, energy.regionCode, energy.energyRefLibId, energy.syncFlag, energy.enabledFlag);

Load Data local InFile 'd:/e2.txt' Into Table zigbeedevice.energyfield (energyfield.houseIEEE, energyfield.energyId, energyfield.energyFieldId, energyfield.`name`, energyfield.startField, energyfield.endField);

Load Data local InFile 'd:/e3.txt' Into Table zigbeedevice.energytime (energytime.houseIEEE, energytime.energyId, energytime.energyTimeId, energytime.`name`, energytime.beginTime, energytime.endTime);

Load Data local InFile 'd:/e4.txt' Into Table zigbeedevice.priceparam (priceparam.houseIEEE, priceparam.energyId, priceparam.energyFieldId, priceparam.energyTimeId, priceparam.`name`, priceparam.price, priceparam.priceDST);

Load Data local InFile 'd:/e5.txt' Into Table zigbeedevice.houseparam (houseparam.houseIEEE, houseparam.totalNumber, houseparam.totalSquare);

update zigbeedevice.energyfield set energyId=(select id from zigbeedevice.energy where houseIEEE='00137a000001013b') where houseIEEE='00137a000001013b';

update zigbeedevice.energytime set energyId=(select id from zigbeedevice.energy where houseIEEE='00137a000001013b') where houseIEEE='00137a000001013b';

update zigbeedevice.priceparam set energyId=(select id from zigbeedevice.energy where houseIEEE='00137a000001013b') where houseIEEE='00137a000001013b';

Load Data local InFile 'd:/h7.txt' Into Table zigbeedevice.energyhistory_2014 
(energyhistory_2014.houseIEEE, energyhistory_2014.IEEE, energyhistory_2014.EP, energyhistory_2014.opdatetime, energyhistory_2014.energyValue, energyhistory_2014.energyPrice);

DROP TABLE IF EXISTS zigbeedevice.deviceoperatehistory_00137a000001013b_2014;
CREATE TABLE zigbeedevice.deviceoperatehistory_00137a000001013b_2014 (
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
Load Data local InFile 'd:/h8.txt' Into Table zigbeedevice.deviceoperatehistory_00137a000001013b_2014; 

DROP TABLE IF EXISTS zigbeedevice.deviceattributehistory_00137a000001013b_2014;
CREATE TABLE zigbeedevice.deviceattributehistory_00137a000001013b_2014 (
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
Load Data local InFile 'd:/h9.txt' Into Table zigbeedevice.deviceattributehistory_00137a000001013b_2014; 

DROP TABLE IF EXISTS zigbeedevice.devicewarnhistory_00137a000001013b_2014;
CREATE TABLE zigbeedevice.devicewarnhistory_00137a000001013b_2014 (
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
Load Data local InFile 'd:/h10.txt' Into Table zigbeedevice.devicewarnhistory_00137a000001013b_2014;

#
delete from openfire.ofuser where ofuser.username like '%00137a000001013b%';
delete from openfire.ofroster where ofroster.username like '%00137a000001013b%';
delete from openfire.ofroster where ofroster.jid like '%00137a000001013b%';
Load Data local InFile 'd:/o1.txt' Into Table openfire.ofuser (ofuser.username, ofuser.plainPassword, ofuser.encryptedPassword, ofuser.`name`, ofuser.email, ofuser.creationDate, ofuser.modificationDate);
Load Data local InFile 'd:/o2.txt' Into Table openfire.ofroster (ofroster.username, ofroster.jid, ofroster.sub, ofroster.ask, ofroster.recv, ofroster.nick);
#需要切换数据连接