1/*在zigBeeDevices数据库新建数据表farmwarnhandle*/
DROP TABLE IF EXISTS `farmwarnhandle`;
CREATE TABLE `farmwarnhandle` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `houseieee` varchar(50) DEFAULT NULL,
  `handletime` datetime DEFAULT NULL,
  `note` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

2/*zigBeeDevices数据库以devicewarnhistory开头的数据表添加一个字段hanlde_flag处置默认为0，*/
CALL modifytabfield("deviceattributehistory","hanlde_flag TINYINT(1) DEFAULT 0","zigbeedevice")

3/*zigBeeDevices数据库modefarmhouse表中添加一个字段note*/
alter table modefarmhouse add column note varchar(500) after xmlVersion;