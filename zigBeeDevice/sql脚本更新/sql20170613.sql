/*修改house表，增加发送短息类型*/
ALTER TABLE house ADD COLUMN smsType TINYINT(2) DEFAULT 0;
/*修改手机号字段长度*/
ALTER TABLE farmuser CHANGE phone phone VARCHAR (50) DEFAULT NULL;

/*
Navicat MySQL Data Transfer 用户区域手机号码绑定信息表

Source Server         : 192.168.1.219
Source Server Version : 50614
Source Host           : 192.168.1.219:3306
Source Database       : zigbeedevice

Target Server Type    : MYSQL
Target Server Version : 50614
File Encoding         : 65001

Date: 2017-06-16 09:33:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for farmuserhousephone
-- ----------------------------
DROP TABLE IF EXISTS `farmuserhousephone`;
CREATE TABLE `farmuserhousephone` (
  `id` tinyint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) DEFAULT NULL,
  `house_ieee` varchar(20) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `sms_flag` varchar(10) DEFAULT '0,0,0,0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_user_house` (`user_name`,`house_ieee`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;