
/*增加查询索引优化*/
ALTER TABLE device ADD INDEX house_ieee_ep(houseIEEE,ieee,ep);
ALTER TABLE deviceWarnHistory_00137A0000012DFF_2016 ADD INDEX house_ieee_ep_wmode(w_mode,houseIEEE,zone_ieee,zone_ep);

SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `slplatformuser`;
CREATE TABLE `slplatformuser` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `type` int(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_username` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;