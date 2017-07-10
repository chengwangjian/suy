
SELECT house.houseIEEE, house.`name`, house.longitude, house.latitude, house.networkAddress, house.`port`, house.description, house.enableFlag, house.regionCode, house.isonline, house.createtime, house.lasttime, house.clientCode, house.IPK_version, house.HW_version  INTO OUTFILE 'd:/h1.txt' FROM zigbeedevice.house house WHERE house.houseIEEE = '00137a000001013b';

SELECT device.houseIEEE, device.roomId, device.nodeId, device.deviceName, device.ieee, device.ep, device.type, device.device_id, device.source_id, device.device_type, device.AttrInfo, device.isonline, device.createtime, device.lasttime, device.datecode, device.deviceattribute, device.unit_type  INTO OUTFILE 'd:/h2.txt' FROM zigbeedevice.device WHERE device.houseIEEE = '00137a000001013b';

SELECT deviceattribute.houseIEEE, deviceattribute.room_id, deviceattribute.daemonDeviceId, deviceattribute.source_id, deviceattribute.deviceType, deviceattribute.device_ieee, deviceattribute.device_ep, deviceattribute.id_string3, deviceattribute.cluster_id, deviceattribute.attribute_id, deviceattribute.attributeName, deviceattribute.`value`, deviceattribute.lasttime  INTO OUTFILE 'd:/h3.txt'  FROM zigbeedevice.deviceattribute WHERE deviceattribute.houseIEEE = '00137a000001013b';

SELECT node.houseIEEE, node.roomId, node.ieee, node.type, node.ip, node.nodeId, node.nodeType, node.nodeName  INTO OUTFILE 'd:/h4.txt'  FROM zigbeedevice.node WHERE node.houseIEEE = '00137a000001013b';

SELECT houseieee.houseIEEE, houseieee.`name`, houseieee.secretKey, houseieee.vendorCode, houseieee.vendorName, houseieee.encodemethod, houseieee.xmppIp, houseieee.xmppPort, houseieee.cloudIp1, houseieee.cloudPort1, houseieee.cloudIp2, houseieee.cloudPort2, houseieee.description, houseieee.enableFlag, houseieee.createtime, houseieee.lasttime  INTO OUTFILE 'd:/h5.txt' FROM zigbeedevice.houseieee WHERE houseieee.houseIEEE = '00137a000001013b';

SELECT proxyserver.houseIEEE, proxyserver.`name`, proxyserver.serverIp, proxyserver.serverPort, proxyserver.type, proxyserver.secondType, proxyserver.description, proxyserver.enableFlag, proxyserver.createtime, proxyserver.lasttime  INTO OUTFILE 'd:/h6.txt' FROM zigbeedevice.proxyserver WHERE proxyserver.houseIEEE = '00137a000001013b';


SELECT energy.`name`, energy.houseIEEE, energy.priceType, energy.DSTFlag, energy.DSTbeginTime, energy.DSTendTime, energy.regionCode, energy.energyRefLibId, energy.syncFlag, energy.enabledFlag  INTO OUTFILE 'd:/e1.txt' FROM zigbeedevice.energy WHERE energy.houseIEEE = '00137a000001013b' limit 1;

SELECT energyfield.houseIEEE, energyfield.energyId, energyfield.energyFieldId, energyfield.`name`, energyfield.startField, energyfield.endField  INTO OUTFILE 'd:/e2.txt' FROM zigbeedevice.energyfield WHERE energyfield.houseIEEE = '00137a000001013b';

SELECT energytime.houseIEEE, energytime.energyId, energytime.energyTimeId, energytime.`name`, energytime.beginTime, energytime.endTime INTO OUTFILE 'd:/e3.txt' FROM zigbeedevice.energytime WHERE energytime.houseIEEE = '00137a000001013b';

SELECT priceparam.houseIEEE, priceparam.energyId, priceparam.energyFieldId, priceparam.energyTimeId, priceparam.`name`, priceparam.price, priceparam.priceDST INTO OUTFILE 'd:/e4.txt' FROM zigbeedevice.priceparam WHERE priceparam.houseIEEE = '00137a000001013b';

SELECT houseparam.houseIEEE, houseparam.totalNumber, houseparam.totalSquare INTO OUTFILE 'd:/e5.txt' FROM zigbeedevice.houseparam WHERE houseparam.houseIEEE = '00137a000001013b';


SELECT energyhistory_2014.houseIEEE, energyhistory_2014.IEEE, energyhistory_2014.EP, energyhistory_2014.opdatetime, energyhistory_2014.energyValue, energyhistory_2014.energyPrice INTO OUTFILE 'd:/h7.txt' FROM zigbeedevice.energyhistory_2014 WHERE energyhistory_2014.houseIEEE = '00137a000001013b';

Select * From zigbeedevice.deviceoperatehistory_00137a000001013b_2014 Into OutFile 'd:/h8.txt';

Select * From zigbeedevice.deviceattributehistory_00137a000001013b_2014 Into OutFile 'd:/h9.txt';

Select * From zigbeedevice.devicewarnhistory_00137a000001013b_2014 Into OutFile 'd:/h10.txt';

#
SELECT ofuser.username, ofuser.plainPassword, ofuser.encryptedPassword, ofuser.`name`, ofuser.email, ofuser.creationDate, ofuser.modificationDate INTO OUTFILE 'd:/o1.txt'  FROM openfire.ofuser WHERE ofuser.username like '%00137a000001013b%';
SELECT ofroster.username, ofroster.jid, ofroster.sub, ofroster.ask, ofroster.recv, ofroster.nick INTO OUTFILE 'd:/o2.txt' FROM openfire.ofroster WHERE (ofroster.jid like '%00137a000001013b%' or ofroster.username like '%00137a000001013b%');
#需要切换数据连接
