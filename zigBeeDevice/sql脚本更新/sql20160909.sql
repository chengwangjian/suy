/*创建资源共享信息表*/
CREATE TABLE `resource` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(255) DEFAULT NULL,
  `houseieee` varchar(255) DEFAULT NULL,
  `deviceuuid` varchar(20) DEFAULT NULL,
  `resname` varchar(255) DEFAULT NULL,
  `restype` varchar(255) DEFAULT NULL,
  `size` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `spath` varchar(255) DEFAULT NULL,
  `taketime` datetime(0) DEFAULT NULL,
  `uploadtime` datetime(0) DEFAULT NULL,
  PRIMARY KEY (`id`),
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8;