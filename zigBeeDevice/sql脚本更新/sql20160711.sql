/*增加字段，判断是嘉义网络还是IES，0是IES、1是嘉义*/
ALTER TABLE farmuser ADD COLUMN mode TINYINT DEFAULT 0;

/*创建家微信绑定信息表*/
DROP TABLE IF EXISTS `house_weixin`;
CREATE TABLE `house_weixin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account` varchar(150) DEFAULT NULL,
  `house_ieee` varchar(20) DEFAULT NULL,
  `state` tinyint(4) DEFAULT NULL,
  `StartDate` datetime DEFAULT NULL,
  `EndDate` datetime DEFAULT NULL,
  `account_name` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_account` (`account`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='微信提醒方式绑定(house_weixin)';

/*创建告警信息缓存表*/
SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for cache_warn_message
-- ----------------------------
DROP TABLE IF EXISTS `cache_warn_message`;
CREATE TABLE `cache_warn_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `message_cn` varchar(255) DEFAULT NULL,
  `message_en` varchar(255) DEFAULT NULL,
  `house_ieee` varchar(20) DEFAULT NULL,
  `device_name` varchar(255) DEFAULT NULL,
  `push_time` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `single_column_index_houseieee` (`house_ieee`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8;

