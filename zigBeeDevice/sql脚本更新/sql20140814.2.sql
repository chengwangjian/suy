
/*新建品牌字典表irbranddictionary*/
CREATE TABLE `irbranddictionary` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `IRType` varchar(20) DEFAULT NULL,
  `BrandCN` varchar(50) DEFAULT NULL,
  `BrandEN` varchar(50) DEFAULT NULL,
  `NameIndex` varchar(20) DEFAULT NULL,
  `UnitCode` varchar(20) DEFAULT NULL,
  `bUse` varchar(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/*往品牌字典表里新增数据*/
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1', '0', '格力', 'GREE', 'G', 'G', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('2', '0', '海尔', 'HAIER', 'H', 'H', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('3', '0', '美的', 'MIDEA', 'M', 'M', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('4', '0', '海信', 'HISENSE', 'H', 'H', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('5', '0', '格兰仕', 'GALANZ', 'G', 'G', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('6', '0', '奥克斯', 'AUX', 'A', 'A', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('7', '0', '三菱重工', 'MITSUBISHI/MHI/BEAVER', 'M', 'M', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('8', '0', '王牌', 'TCL', 'T', 'T', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('9', '0', '志高', 'CHIGO', 'C', 'C', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('10', '0', '大金', 'DAIKIN', 'D', 'D', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('11', '0', '松下', 'PANASONSIC', 'P', 'P', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('12', '0', '长虹', 'CHANGHONG', 'C', 'C', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('13', '0', '三星', 'SAMSUNG', 'S', 'S', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('14', '0', '日立', 'HITACHI', 'H', 'H', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('15', '0', '新科', 'SHINCO', 'S', 'S', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('16', '0', '春兰', 'CHUNLAN', 'C', 'C', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('17', '0', '伊莱克斯', 'ELECTROLUX', 'E', 'E', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('18', '0', '惠而浦', 'WHIRLPOOL', 'W', 'W', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('19', '0', 'LG', 'LG', 'L', 'L', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('20', '0', '约克', 'YORK', 'Y', 'Y', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('21', '0', '富士通/珍宝', 'FUJITSU', 'F', 'F', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('22', '0', '三洋', 'SANYO', 'S', 'S', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('23', '0', 'SKG', 'SKG', 'S', 'S', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('24', '0', '月兔', 'YUETU', 'Y', 'Y', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('25', '0', 'GE通用电气/奇异', 'GE', 'G', 'G', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('26', '0', '索伊', 'SUOYI', 'S', 'S', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('27', '0', '科龙', 'KELON', 'K', 'K', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('28', '0', '康佳', 'KONKA', 'K', 'K', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('29', '0', '科朗', 'KELANG', 'K', 'K', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('30', '0', '小天鹅', 'LITTLE SWAN', 'L', 'L', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('31', '0', '华为', 'HUAWEI', 'H', 'H', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('32', '0', '奥力', 'OLI', 'O', 'O', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('33', '0', '统帅', 'LEADER', 'L', 'L', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('34', '0', '小艾', 'XIAOAI', 'X', 'X', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('35', '0', '欧菱宝', 'OLIMPIA', 'O', 'O', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('36', '0', '华凌', 'HUALING', 'H', 'H', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('37', '0', 'JHS', 'JHS', 'J', 'J', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('38', '0', 'ACSOM', 'ACSOM', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('39', '0', '雅哥', 'ACURA', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('40', '0', '艾德龙', 'ADC', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('41', '0', '爱迪生', 'ADDISON', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('42', '0', '爱德龙', 'AIDELONG', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('43', '0', 'AIRFORCE', 'AIRFORCE', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('44', '0', '爱特', 'AITE', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('45', '0', 'AJIRA', 'AJIRA', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('46', '0', 'ALPIN', 'ALPIN', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('47', '0', '阿玛迪斯', 'AMADUS', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('48', '0', 'AMCOR', 'AMCOR', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('49', '0', 'AMICO', 'AMICO', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('50', '0', '安富生', 'ANFUSHENG', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('51', '0', '冠捷/爱德蒙', 'AOC', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('52', '0', '澳科', 'AOKE', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('53', '0', '爱普顿', 'APTON', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('54', '0', '亚星', 'ASTAR', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('55', '0', 'ATISA', 'ATISA', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('56', '0', '澳柯玛', 'AUCMA', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('57', '0', '百立', 'BAILI', 'B', 'B', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('58', '0', '白雪', 'BAIXUE', 'B', 'B', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('59', '0', '北京京电', 'BEIJINGJINGDIAN', 'B', 'B', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('60', '0', '金宝岛', 'BESTECH', 'B', 'B', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('61', '0', 'BLUE STAR', 'BLUE STAR', 'B', 'B', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('62', '0', '蓝天', 'BLUESKY', 'B', 'B', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('63', '0', '波尔卡', 'BOERKA', 'B', 'B', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('64', '0', '波乐', 'BORLER', 'B', 'B', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('65', '0', '博世', 'BOSCH', 'B', 'B', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('66', '0', '博士', 'BOSHI', 'B', 'B', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('67', '0', '波士高', 'BOSHIGAO', 'B', 'B', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('68', '0', '嘉利捷', 'BOTH/CHIA LI JIE', 'B', 'B', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('69', '0', '波音', 'BOYIN', 'B', 'B', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('70', '0', 'BROS', 'BROS', 'B', 'B', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('71', '0', '兄弟', 'BROTHS', 'B', 'B', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('72', '0', '彩星', 'CAIXING', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('73', '0', '开利', 'CARRIER', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('74', '0', '城堡', 'CASTLE', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('75', '0', '长飞', 'CHANGFEI', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('76', '0', '长风', 'CHANGFENG', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('77', '0', '长府', 'CHANGFU', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('78', '0', '长岭', 'CHANGLING', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('79', '0', '超技温控', 'CHAOJIWENKONG', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('80', '0', '诚宝冷气', 'CHEBO', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('81', '0', '诚远/维修板', 'CHENGYUAN', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('82', '0', '创华', 'CHUANGHUA', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('83', '0', '川井', 'CHUANJING', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('84', '0', '川岩', 'CHUANYAN', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('85', '0', '中兴', 'CHUNGHSIN', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('86', '0', 'CITY', 'CITY', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('87', '0', 'CLAIRE', 'CLAIRE', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('88', '0', '禾阳', 'COLONA', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('89', '0', 'COLONIA', 'COLONIA', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('90', '0', 'COLROLLA', 'COLROLLA', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('91', '0', '高路华', 'CONROWA', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('92', '0', 'CONSUL', 'CONSUL', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('93', '0', '可乐娜', 'CORONA', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('94', '0', '酷哥', 'COUGAR', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('95', '0', 'CROSLEY', 'CROSLEY', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('96', '0', '皇冠', 'CROWN', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('97', '0', '得仕林', 'D&G', 'D', 'D', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('98', '0', '大康', 'DACON', 'D', 'D', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('99', '0', '达富', 'DAF', 'D', 'D', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('100', '0', '大和', 'DAHE', 'D', 'D', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('101', '0', '大井', 'DA-JING', 'D', 'D', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('102', '0', '大金星', 'DAJINXING', 'D', 'D', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('103', '0', '大拇指', 'DAMUZHI', 'D', 'D', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('104', '0', '稻田', 'DAOTIAN', 'D', 'D', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('105', '0', '大宇', 'DEAWOO', 'D', 'D', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('106', '0', '得意', 'DEI', 'D', 'D', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('107', '0', '德龙', 'DELONGHI', 'D', 'D', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('108', '0', '迪帕尔', 'DIBOER', 'D', 'D', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('109', '0', '地中海', 'DIZHONGHAI', 'D', 'D', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('110', '0', '东宝', 'DONGBAO', 'D', 'D', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('111', '0', '东禾', 'DONGHE', 'D', 'D', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('112', '0', '东菱', 'DONGLING', 'D', 'D', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('113', '0', '冬夏', 'DONGXIA', 'D', 'D', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('114', '0', '东新宝', 'DONGXIBAO', 'D', 'D', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('115', '0', '达可', 'DUCKER', 'D', 'D', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('116', '0', '盾安', 'DUNAN', 'D', 'D', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('117', '0', '年代冷气', 'E-CHERN', 'E', 'E', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('118', '0', '宜科', 'ELCO', 'E', 'E', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('119', '0', 'ELECTER', 'ELECTER', 'E', 'E', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('120', '0', 'ELLCHLOR', 'ELLCHLOR', 'E', 'E', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('121', '0', '宇瑟', 'ETHER', 'E', 'E', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('122', '0', '法兰宝', 'FALANBAO', 'F', 'F', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('123', '0', '飞达士', 'FEDDERS', 'F', 'F', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('124', '0', 'FEIEDRICH', 'FEIEDRICH', 'F', 'F', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('125', '0', '飞鹿', 'FEILU', 'F', 'F', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('126', '0', 'FERROLIT', 'FERROLIT', 'F', 'F', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('127', '0', '福士得', 'FIRST', 'F', 'F', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('128', '0', '宝岛冷气', 'FORMASA', 'F', 'F', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('129', '0', '新飞', 'FRESTEC', 'F', 'F', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('130', '0', '富及第', 'FRIGIDAIRE', 'F', 'F', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('131', '0', '冰点', 'FROST', 'F', 'F', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('132', '0', 'FT-AC', 'FT-AC', 'F', 'F', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('133', '0', '府城', 'FU-CHIN', 'F', 'F', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('134', '0', '富士丸', 'FUJIMARU', 'F', 'F', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('135', '0', '船井', 'FUNAI', 'F', 'F', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('136', '0', 'FUNIKI', 'FUNIKI', 'F', 'F', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('137', '0', '高士达', 'GAOSHIDA', 'G', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('138', '0', '格尔', 'GEER', 'G', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('139', '0', '格威尔', 'GEWEIER', 'G', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('140', '0', '格阳', 'GEYANG', 'G', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('141', '0', '吉普生', 'GIBSON', 'G', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('142', '0', 'GLEE', 'GLEE', 'G', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('143', '0', '恪力', 'GLEER', 'G', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('144', '0', '金大阪', 'GOLD-OSAKA', 'G', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('145', '0', '金星', 'GOLDSTAR', 'G', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('146', '0', '光大', 'GUANGDA', 'G', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('147', '0', '国际通', 'GUOJITONG', 'G', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('148', '0', '国菱', 'GUOLING', 'G', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('149', '0', '古桥', 'GUQIAO', 'G', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('150', '0', '华菱', 'HANSA', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('151', '0', '汉唐', 'HANTANG', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('152', '0', '春侨田', 'HASIDA', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('153', '0', 'HELTON', 'HELTON', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('154', '0', 'HEMILTON', 'HEMILTON', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('155', '0', '禾联', 'HERLAN', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('156', '0', '海帝', 'HITEC', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('157', '0', '宏竹', 'HONG-CHU', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('158', '0', '鸿骏', 'HONGIN', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('159', '0', '鸿怡', 'HONGYI', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('160', '0', '豪记冷气', 'HOWGIE', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('161', '0', '三井', 'HSAN-JING', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('162', '0', '华宝', 'HUABAO', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('163', '0', '华高', 'HUAGAO', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('164', '0', '华科', 'HUAKE', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('165', '0', '华美', 'HUAMEI', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('166', '0', '黄河', 'HUANGHE', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('167', '0', '惠尔', 'HUIER', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('168', '0', '汇丰', 'HUIFENG', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('169', '0', '宁波惠康', 'HUIKANG', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('170', '0', '伊瑪', 'IMARFLEX', 'I', 'I', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('171', '0', '迎燕', 'INYAN', 'I', 'I', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('172', '0', '瑞宝', 'JANGPON', 'J', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('173', '0', '佳乐', 'JIALE', 'J', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('174', '0', '江成', 'JIANGCHENG', 'J', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('175', '0', '江南空调', 'JIANGNAN', 'J', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('176', '0', '吉川', 'JICHUN', 'J', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('177', '0', '佶利', 'JILI', 'J', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('178', '0', '金北京', 'JINBEIJING', 'J', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('179', '0', '郑州金达', 'JINDA', 'J', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('180', '0', '金鹿', 'JINLU', 'J', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('181', '0', '金松', 'JINSONG', 'J', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('182', '0', '吉星', 'JIXING', 'J', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('183', '0', '杰士达', 'JMSTAR', 'J', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('184', '0', '江森', 'JOHNSON', 'J', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('185', '0', '峻菱', 'JUN-LING', 'J', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('186', '0', '康得利', 'KADEH', 'K', 'K', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('187', '0', '康宝', 'KAMPO', 'K', 'K', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('188', '0', '康拜恩', 'KANGBAIEN', 'K', 'K', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('189', '0', '康丽', 'KANGLI', 'K', 'K', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('190', '0', '科尔', 'KEER', 'K', 'K', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('191', '0', '科明', 'KEMING', 'K', 'K', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('192', '0', '金菱', 'KINLIN', 'K', 'K', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('193', '0', 'KL', 'KL', 'K', 'K', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('194', '0', 'KLIMATAIR', 'KLIMATAIR', 'K', 'K', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('195', '0', 'KRIS', 'KRIS', 'K', 'K', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('196', '0', '永崧', 'KTAIKIN', 'K', 'K', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('197', '0', 'KT万能遥控器', 'KTWANNENGYAOKONGQI', 'K', 'K', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('198', '0', 'KTY万能遥控器', 'KTYWANNENGYAOKONGQI', 'K', 'K', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('199', '0', '宽菱', 'KUANLING', 'K', 'K', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('200', '0', '朗歌', 'LANGE', 'L', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('201', '0', '利凯尔', 'LIKAIER', 'L', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('202', '0', '小鸭', 'LITTLE DUCK', 'L', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('203', '0', '良峰', 'LIUNG-FENG', 'L', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('204', '0', '龙禾', 'LONGHE', 'L', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('205', '0', '罗兰斯宝', 'LOREN-SEBO', 'L', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('206', '0', '万士益', 'MAXE', 'M', 'M', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('207', '0', '三叶', 'MAZHA/MITSUBA', 'M', 'M', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('208', '0', '麦克维尔', 'MCQUAY', 'M', 'M', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('209', '0', '美典', 'MEIDIAN', 'M', 'M', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('210', '0', '美灵', 'MEILING', 'M', 'M', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('211', '0', 'MILLER', 'MILLER', 'M', 'M', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('212', '0', '明星', 'MINGXING', 'M', 'M', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('213', '0', '名亿', 'MINGYI', 'M', 'M', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('214', '0', 'MITSUKA', 'MITSUKA', 'M', 'M', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('215', '0', '三菱', 'MITSUBISHI', 'M', 'M', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('216', '0', '名隆', 'ML', 'M', 'M', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('217', '0', '国际', 'NATIONNAL', 'N', 'N', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('218', '0', '日电', 'NEC', 'N', 'N', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('219', '0', '内田', 'NEITIAN', 'N', 'N', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('220', '0', '新禾', 'NEOKA', 'N', 'N', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('221', '0', '稜威福', 'NEWAVE', 'N', 'N', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('222', '0', '日元', 'NIKEN', 'N', 'N', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('223', '0', '日光 ', 'NIKKO', 'N', 'N', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('224', '0', '旭光', 'NIKOTOH', 'N', 'N', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('225', '0', '日索', 'NISO', 'N', 'N', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('226', '0', 'NLSEI', 'NLSEI', 'N', 'N', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('227', '0', '新容威', 'N-L-W', 'N', 'N', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('228', '0', 'NORCA', 'NORCA', 'N', 'N', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('229', '0', '新典', 'NORW', 'N', 'N', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('230', '0', '尚洋', 'OCEANAIR', 'O', 'O', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('231', '0', 'OPAL', 'OPAL', 'O', 'O', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('232', '0', '欧林巴斯', 'OULINBASI', 'O', 'O', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('233', '0', '熊猫', 'PANDA', 'P', 'P', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('234', '0', 'PEREG', 'PEREG', 'P', 'P', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('235', '0', '飞歌', 'PHILCO', 'P', 'P', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('236', '0', '飞利浦', 'PHILIPS', 'P', 'P', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('237', '0', 'PILOT', 'PILOT', 'P', 'P', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('238', '0', '品尚', 'PINSHANG', 'P', 'P', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('239', '0', '品首', 'PINSO', 'P', 'P', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('240', '0', 'POLAR-AIR', 'POLAR-AIR', 'P', 'P', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('241', '0', '王子', 'PRINCE', 'P', 'P', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('242', '0', '菩奕', 'PROTEC', 'P', 'P', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('243', '0', '普腾', 'PROTON', 'P', 'P', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('244', '0', '普弈', 'PUYI', 'P', 'P', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('245', '0', '奇迪', 'QIDI', 'Q', 'Q', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('246', '0', '玉兔', 'RABBIT', 'R', 'R', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('247', '0', '乐信牌', 'RASONIC', 'R', 'R', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('248', '0', '蓝波', 'RAYBO', 'R', 'R', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('249', '0', '瑞林', 'REALISE', 'R', 'R', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('250', '0', '乐士', 'RHOSS', 'R', 'R', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('251', '0', '日宝', 'RIBAO', 'R', 'R', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('252', '0', '日彩', 'RICAI', 'R', 'R', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('253', '0', '日江', 'RIJIANG', 'R', 'R', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('254', '0', '日菱', 'ROULIN', 'R', 'R', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('255', '0', '乐华', 'ROWA', 'R', 'R', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('256', '0', '皇家', 'ROYAL', 'R', 'R', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('257', '0', '新笛', 'S&D', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('258', '0', '帅康', 'SACON', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('259', '0', 'SAISON', 'SAISON', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('260', '0', '昇凡', 'SAMFAM', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('261', '0', '声宝', 'SAMPO', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('262', '0', '山水', 'SANSUI', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('263', '0', '三钻', 'SANZUAN', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('264', '0', '莎普罗', 'SAPORO', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('265', '0', '先科', 'SAST', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('266', '0', 'SENSOR', 'SENSOR', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('267', '0', 'SERENE', 'SERENE', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('268', '0', '七星', 'SEVENSTART', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('269', '0', '沙美', 'SHAMEI', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('270', '0', '上灵', 'SHANGLING', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('271', '0', '山星', 'SHANXING', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('272', '0', '夏普', 'SHARP', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('273', '0', '绅宝', 'SHENBAO', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('274', '0', '胜风', 'SHENGFENG', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('275', '0', '上海双菱', 'SHINING', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('276', '0', '新菱', 'SHINLIN', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('277', '0', '双鹿', 'SHUANGLU', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('278', '0', '胜家', 'SINGER', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('279', '0', '新诺', 'SINOLL', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('280', '0', '上菱', 'S-LING', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('281', '0', 'SOGO', 'SOGO', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('282', '0', '松静', 'SONGJING', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('283', '0', '松星', 'SONGXING', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('284', '0', '松洋', 'SONGYANG', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('285', '0', '松格', 'SONKOR', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('286', '0', '松冷', 'SONLEN', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('287', '0', '索华', 'SOVA', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('288', '0', '首华', 'SOWA', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('289', '0', 'SPEED', 'SPEED', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('290', '0', 'STARFLAG', 'STARFLAG', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('291', '0', 'STARIGHT-AIRCON', 'STARIGHT-AIRCON', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('292', '0', '松林夏', 'SUMMER', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('293', '0', '森宝', 'SUNBURG', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('294', '0', '速翼特', 'SWIFT', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('295', '0', '新格', 'SYNCO', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('296', '0', '新帝', 'SYNTIER', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('297', '0', '大矽谷', 'TACICO', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('298', '0', 'TADAIR', 'TADAIR', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('299', '0', 'TADIRAN', 'TADIRAN', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('300', '0', '台林', 'TAILIN', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('301', '0', '台塑', 'TAI SO', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('302', '0', '大欣', 'TAIXIN', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('303', '0', '太亚', 'TAIYA', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('304', '0', '泰陽', 'TAIYO', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('305', '0', '大同', 'TATUNG', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('306', '0', '歌林', 'TEAC/KOLIN', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('307', '0', 'TECHBROTHS', 'TECHBROTHS', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('308', '0', '东元', 'TECO', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('309', '0', '天鹅', 'TIANE', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('310', '0', '天津空调', 'TIANJINKONGTIAO', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('311', '0', '天元', 'TIANYUAN', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('312', '0', '同力', 'TONGLI', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('313', '0', '同益', 'TONGYI', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('314', '0', 'TOP', 'TOP', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('315', '0', '国品', 'TOPPING', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('316', '0', '东芝', 'TOSHIBA', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('317', '0', '东洋', 'TOYO', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('318', '0', '特灵空调', 'TRANE', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('319', '0', '大津', 'TWNICHI', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('320', '0', '优沙', 'UIOUSHA', 'U', 'U', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('321', '0', 'UNI-AIR', 'UNI-AIR', 'U', 'U', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('322', '0', 'U-POINT', 'U-POINT', 'U', 'U', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('323', '0', '维纳斯', 'VENUS', 'V', 'V', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('324', '0', '维有康', 'VIDEOCON', 'V', 'V', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('325', '0', '万宝', 'WANBAO', 'W', 'W', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('326', '0', '威力', 'WEILI', 'W', 'W', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('327', '0', '威灵顿', 'WEILINGDUN', 'W', 'W', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('328', '0', '威特力', 'WEITELI', 'W', 'W', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('329', '0', 'WELL-FOR', 'WELL-FOR', 'W', 'W', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('330', '0', '西屋', 'WESTINGHOUSE', 'W', 'W', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('331', '0', '汇安', 'WHEAN', 'W', 'W', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('332', '0', 'WINDCHILL', 'WINDCHILL', 'W', 'W', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('333', '0', '雾峰', 'WUFENG', 'W', 'W', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('334', '0', '现代', 'XIANDAI', 'X', 'X', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('335', '0', '小金刚', 'XIAOJINGANG', 'X', 'X', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('336', '0', '夏洋', 'XIAYANG', 'X', 'X', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('337', '0', '谐泰', 'XIETAI', 'X', 'X', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('338', '0', '西格玛', 'XIGEMA', 'X', 'X', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('339', '0', '西冷', 'XILENG', 'X', 'X', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('340', '0', '星和', 'XINGHE', 'X', 'X', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('341', '0', '新华宝', 'XINHUABAO', 'X', 'X', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('342', '0', '新乐', 'XINLE', 'X', 'X', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('343', '0', '新凌', 'XINLING', 'X', 'X', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('344', '0', '扬子', 'YAIR', 'Y', 'Y', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('345', '0', '扬帆', 'YANGFAN', 'Y', 'Y', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('346', '0', '耀马', 'YAOMA', 'Y', 'Y', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('347', '0', '移动', 'YIDONG', 'Y', 'Y', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('348', '0', '宇电科技', 'YUDIANKEJI', 'Y', 'Y', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('349', '0', '中意', 'ZHONGYI', 'Z', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('350', '0', '中宇', 'ZHONGYU', 'Z', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('351', '0', '佐丹', 'ZUODAN', 'Z', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('352', '1', '三星', 'SAMSUNG', 'S', 'S', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('353', '1', '海信', 'HISENSE', 'H', 'H', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('354', '1', '王牌', 'TCL', 'T', 'T', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('355', '1', 'LG', 'LG', 'L', 'L', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('356', '1', '长虹', 'CHANGHONG', 'C', 'C', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('357', '1', '康佳', 'KONKA', 'K', 'K', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('358', '1', '创维', 'SKYWORTH', 'S', 'S', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('359', '1', '索尼', 'SONY', 'S', 'S', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('360', '1', '夏普', 'SHARP', 'S', 'S', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('361', '1', '乐视TV', 'LETV', 'L', 'L', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('362', '1', '海尔', 'HAIER', 'H', 'H', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('363', '1', '联想', 'LENOVO', 'L', 'L', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('364', '1', '飞利浦', 'PHILIPS', 'P', 'P', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('365', '1', '小米', 'MI', 'M', 'M', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('366', '1', '冠捷/爱德蒙', 'AOC', 'A', 'A', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('367', '1', '东芝', 'TOSHIBA', 'T', 'T', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('368', '1', '熊猫', 'PANDA', 'P', 'P', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('369', '1', '松下', 'PANASONSIC', 'P', 'P', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('370', '1', '清华同方', 'TONGFANG', 'T', 'T', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('371', '1', '京东方', 'BOE', 'B', 'B', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('372', '1', '三洋', 'SANYO', 'S', 'S', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('373', '1', '乐华', 'ROWA', 'R', 'R', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('374', '1', '创佳', 'CANCA', 'C', 'C', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('375', '1', 'HPP锋派', 'HPPFENGPAI', 'H', 'H', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('376', '1', '盘古', 'PANGOO', 'P', 'P', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('377', '1', '统帅', 'LEADER', 'L', 'L', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('378', '1', '唯视', 'WEISHI', 'W', 'W', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('379', '1', '日立', 'HITACHI', 'H', 'H', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('380', '1', '艾洛维', 'INOVEL', 'I', 'I', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('381', '1', '万利达', 'MALATA', 'M', 'M', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('382', '1', '酷开', 'COOCAA', 'C', 'C', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('383', '1', 'KKTV', 'KKTV', 'K', 'K', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('384', '1', '哈呢', 'HANI', 'H', 'H', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('385', '1', '彩迅', 'CAIXUN', 'C', 'C', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('386', '1', '欧宝丽', 'OBONI', 'O', 'O', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('387', '1', '海力', 'HORION', 'H', 'H', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('388', '1', 'PLATINA', 'PLATINA', 'P', 'P', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('389', '1', '美乐', 'MELODY', 'M', 'M', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('390', '1', '上广电', 'SVA', 'S', 'S', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('391', '1', '模卡', 'MOOKA', 'M', 'M', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('392', '1', '天敏', '10MOONS', '#', '#', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('393', '1', '888牌', '888PAI', '#', '#', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('394', '1', '爱华', 'AIWA', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('395', '1', '雅佳', 'AKAI', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('396', '1', '夏新', 'AMOI', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('397', '1', '兆驰', 'AMTC', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('398', '1', '瑞轩', 'AMTRAN', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('399', '1', '安华', 'ANHUA', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('400', '1', '奥格玛', 'AOGEMA', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('401', '1', '奥林匹亚', 'AOLINPIYA', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('402', '1', '奥林普', 'AOLINPU', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('403', '1', '朝野', 'ASANO', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('404', '1', '奥克斯', 'AUX', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('405', '1', '百合花', 'BAIHEHUA', 'B', 'B', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('406', '1', '百花', 'BAIHUA', 'B', 'B', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('407', '1', '百乐', 'BAILE', 'B', 'B', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('408', '1', '宝花石', 'BAOHUASHI', 'B', 'B', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('409', '1', '宝声', 'BAOSHENG', 'B', 'B', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('410', '1', '步步高', 'BBK', 'B', 'B', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('411', '1', '北京', 'BEIJING', 'B', 'B', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('412', '1', '明基', 'BENQ', 'B', 'B', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('413', '1', '博一', 'BOIOLE', 'B', 'B', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('414', '1', 'BROKSONIC', 'BROKSONIC', 'B', 'B', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('415', '1', '长海', 'CAHNGHAI', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('416', '1', '彩虹', 'CAIHONG', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('417', '1', '彩凌', 'CAILING', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('418', '1', '彩星', 'CAIXING', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('419', '1', '星辰', 'CANDLE/CITIZEN', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('420', '1', '长飞', 'CHANGFEI', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('421', '1', '长风', 'CHANGFENG', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('422', '1', '成都', 'CHENGDU', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('423', '1', '奇美', 'CHIMEI', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('424', '1', '春风', 'CHUNFENG', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('425', '1', '中兴', 'CHUNGHSIN', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('426', '1', '春兰', 'CHUNLAN', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('427', '1', '春笋', 'CHUNSUN', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('428', '1', '高路华', 'CONROWA', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('429', '1', '大宇', 'DEAWOO', 'D', 'D', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('430', '1', '天龙', 'DENON', 'D', 'D', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('431', '1', '德奕优', 'DEO', 'D', 'D', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('432', '1', '东大', 'DONGDA', 'D', 'D', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('433', '1', '东海', 'DONGHAI', 'D', 'D', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('434', '1', '东杰', 'DONGJIE', 'D', 'D', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('435', '1', '冬凌', 'DONGLING', 'D', 'D', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('436', '1', '爱默生', 'EMERSON', 'E', 'E', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('437', '1', '宇瑟', 'ETHER', 'E', 'E', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('438', '1', '新视代', 'FAIRCHILD TV', 'F', 'F', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('439', '1', '飞浪', 'FEILANG', 'F', 'F', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('440', '1', '飞鹿', 'FEILU', 'F', 'F', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('441', '1', '飞燕', 'FEIYAN', 'F', 'F', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('442', '1', '飞跃', 'FEIYUE', 'F', 'F', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('443', '1', '燕声', 'FISHER', 'F', 'F', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('444', '1', '忆丰', 'FLAG', 'F', 'F', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('445', '1', '富士通', 'FUJITSU', 'F', 'F', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('446', '1', '富丽', 'FULI', 'F', 'F', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('447', '1', '船井/富井', 'FUNAI/FULLNESS', 'F', 'F', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('448', '1', '福日', 'FURI', 'F', 'F', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('449', '1', '奇异', 'G.E', 'G', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('450', '1', '港泰', 'GANGTAI', 'G', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('451', '1', '赣新', 'GANXIN', 'G', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('452', '1', '将军', 'GENERAL', 'G', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('453', '1', '敬泰', 'GINTAI', 'G', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('454', '1', 'GLOBECAST', 'GLOBECAST', 'G', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('455', '1', '长城', 'GREAT WALL', 'G', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('456', '1', '海虹', 'HAIHONG', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('457', '1', '海乐', 'HAILE', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('458', '1', '海普', 'HAIPU', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('459', '1', '海燕', 'HAIYAN', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('460', '1', '海斯顿', 'HASDUN', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('461', '1', '恒晨', 'HCN', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('462', '1', '惠科', 'HKC', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('463', '1', '虹美', 'HONGMEI', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('464', '1', '红岩', 'HONGYAN', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('465', '1', '惠浦', 'HPC', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('466', '1', '华发', 'HUAFA', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('467', '1', '黄海美', 'HUANGHAIMEI', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('468', '1', '黄河', 'HUANGHE', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('469', '1', '黄龙', 'HUANGLONG', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('470', '1', '黄山', 'HUANGSHAN', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('471', '1', '环宇', 'HUANYU', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('472', '1', '华强', 'HUAQIANG', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('473', '1', '华日', 'HUARI', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('474', '1', '汇佳板', 'HUIJIABAN', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('475', '1', '豁达特技', 'HUODATAJI', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('476', '1', '现代', 'HYUNDAI', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('477', '1', '皇冠', 'IMPERIAL COWN', 'I', 'I', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('478', '1', '富可视', 'INFOCUS', 'I', 'I', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('479', '1', '影雅', 'INSIGNIA', 'I', 'I', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('480', '1', '奇美/俊', 'JEAN', 'J', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('481', '1', 'JERROLD', 'JERROLD', 'J', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('482', '1', '佳丽彩', 'JIALICAI', 'J', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('483', '1', '金凤', 'JINFENG', 'J', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('484', '1', '金海', 'JINHAI', 'J', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('485', '1', '金立普', 'JINLIPU', 'J', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('486', '1', '金鹊', 'JINQUE', 'J', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('487', '1', '金塔', 'JINTA', 'J', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('488', '1', '金星', 'JINXING', 'J', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('489', '1', '金鑫板', 'JINXINGBAN', 'J', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('490', '1', '集品', 'JIPIN', 'J', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('491', '1', '菊花', 'JUHUA', 'J', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('492', '1', '胜利', 'JVC', 'J', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('493', '1', '凯歌', 'KAIGE', 'K', 'K', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('494', '1', '康虹', 'KANGHONG', 'K', 'K', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('495', '1', '康力', 'KANGLI', 'K', 'K', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('496', '1', '康维', 'KANGWEI', 'K', 'K', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('497', '1', '康艺', 'KANGYI', 'K', 'K', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('498', '1', '康视宝', 'KANSIBO', 'K', 'K', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('499', '1', '嘉华', 'KAWA', 'K', 'K', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('500', '1', '歌林', 'KOLIN', 'K', 'K', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('501', '1', '孔雀', 'KONGQUE', 'K', 'K', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('502', '1', '快乐', 'KUAILE', 'K', 'K', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('503', '1', '昆仑', 'KUNLUN', 'K', 'K', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('504', '1', '蓝星', 'LANXING', 'L', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('505', '1', '乐滋', 'LEZI', 'L', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('506', '1', '利华', 'LIHUA', 'L', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('507', '1', '罗意威', 'LOEWE', 'L', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('508', '1', '龙江', 'LONGJIANG', 'L', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('509', '1', '优盟', 'MAGNA', 'M', 'M', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('510', '1', '满天星', 'MANTIANXING', 'M', 'M', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('511', '1', '梦寐', 'MENGMEI', 'M', 'M', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('512', '1', '三菱', 'MITSUBISHI', 'M', 'M', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('513', '1', '摩卡思', 'MOCOSE', 'M', 'M', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('514', '1', '青云', 'MONIVISION', 'M', 'M', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('515', '1', '摩托罗拉', 'MOTOROLA', 'M', 'M', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('516', '1', '牡丹', 'MUDAN', 'M', 'M', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('517', '1', '南宝', 'NANBAO', 'N', 'N', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('518', '1', '南声', 'NANSHENG', 'N', 'N', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('519', '1', '日电', 'NEC', 'N', 'N', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('520', '1', '尼康', 'NIKON', 'N', 'N', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('521', '1', '金正', 'NINTAUS', 'N', 'N', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('522', '1', '欧凌', 'OULING', 'O', 'O', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('523', '1', '先锋', 'PIONEER', 'P', 'P', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('524', '1', 'PROSCAN', 'PROSCAN', 'P', 'P', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('525', '1', '普腾', 'PROTON', 'P', 'P', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('526', '1', '青岛', 'QINGDAO', 'Q', 'Q', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('527', '1', '摩托罗拉公司', 'QUASAR', 'Q', 'Q', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('528', '1', 'TECHNI SAT数字电视公司', 'RFT', 'R', 'R', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('529', '1', '日红', 'RIHONG', 'R', 'R', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('530', '1', '日派', 'RIPAI', 'R', 'R', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('531', '1', '日声', 'RISHENG', 'R', 'R', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('532', '1', '理想', 'RISUN', 'R', 'R', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('533', '1', '日芝', 'RIZHI', 'R', 'R', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('534', '1', '如意', 'RUYI', 'R', 'R', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('535', '1', '塞格', 'SAIGE', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('536', '1', '声宝', 'SAMPO', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('537', '1', '三键', 'SANJIAN', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('538', '1', '三灵', 'SANLING', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('539', '1', '山水', 'SANSUI', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('540', '1', '三元', 'SANYUAN', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('541', '1', '先科', 'SAST', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('542', '1', '赛维克', 'SAWINK', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('543', '1', '索佳', 'SG', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('544', '1', '山茶', 'SHANCHA', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('545', '1', '上海', 'SHANGHAI', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('546', '1', '韶峰', 'SHAOFENG', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('547', '1', '神彩', 'SHENCAI', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('548', '1', '沈阳', 'SHENYANG', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('549', '1', '狮龙', 'SHERWOOD', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('550', '1', '视朗', 'SHILANG', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('551', '1', '新科', 'SHINCO', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('552', '1', '胜家', 'SINGER', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('553', '1', '松柏', 'SONGBAI', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('554', '1', '松电', 'SONGDIAN', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('555', '1', '声光电视', 'SONIQ', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('556', '1', '索映', 'SONYING', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('557', '1', '首华', 'SOWA', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('558', '1', '数源', 'SOYEA', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('559', '1', '三棱', 'SVL', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('560', '1', '新格', 'SYNCO', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('561', '1', '大矽谷', 'TACICO', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('562', '1', '泰山', 'TAISHAN', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('563', '1', '太尹', 'TAIYIN', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('564', '1', '大同', 'TATUNG', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('565', '1', '东元', 'TECO', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('566', '1', '神州天乐', 'TEINURO', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('567', '1', '泰瑞', 'TERA', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('568', '1', '天鹅', 'TIANE', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('569', '1', '天庚板', 'TIANGENGBAN', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('570', '1', '天科板', 'TIANKEBAN', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('571', '1', '东宝', 'TOBO', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('572', '1', '通广', 'TONGGUANG', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('573', '1', '东帝士', 'TUNTEX', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('574', '1', '友利电', 'UNIDEN', 'U', 'U', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('575', '1', '优拉纳斯', 'URANAS', 'U', 'U', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('576', '1', '优视达', 'USTAR', 'U', 'U', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('577', '1', '优派', 'VIEWSONIC', 'V', 'V', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('578', '1', '威牌', 'VPAI', 'V', 'V', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('579', '1', '华顿', 'WARTON', 'W', 'W', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('580', '1', '西屋', 'WESTINGHOUSE', 'W', 'W', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('581', '1', '襄阳', 'XIANGYANG', 'X', 'X', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('582', '1', '翔宇', 'XIANGYU', 'X', 'X', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('583', '1', '西湖', 'XIHU', 'X', 'X', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('584', '1', '幸福', 'XINGFU', 'X', 'X', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('585', '1', '星海', 'XINGHAI', 'X', 'X', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('586', '1', '鑫萌板', 'XINMENGBAN', 'X', 'X', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('587', '1', '新日松', 'XINRISONG', 'X', 'X', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('588', '1', '新思达', 'XINSIDA', 'X', 'X', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('589', '1', '厦华', 'XOCECO', 'X', 'X', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('590', '1', '雪莲', 'XUELIAN', 'X', 'X', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('591', '1', '雅马哈', 'YAMAHA', 'Y', 'Y', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('592', '1', '莺歌', 'YINGGE', 'Y', 'Y', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('593', '1', '银河', 'YINHE', 'Y', 'Y', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('594', '1', '永宝', 'YONGBAO', 'Y', 'Y', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('595', '1', '永固', 'YONGGU', 'Y', 'Y', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('596', '1', '优图', 'YOUTU', 'Y', 'Y', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('597', '1', '宇航', 'YUHANG', 'Y', 'Y', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('598', '1', '增你智', 'ZENITH', 'Z', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('599', '1', '忠冠', 'ZHONGGUAN', 'Z', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('600', '1', '珠海', 'ZHUHAI', 'Z', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('601', '1', '兆赫', 'ZINWELL', 'Z', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('602', '2', '广东有线带学习', 'GD_GDYOUXIANX', '广东', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('603', '2', '广东有线万能TV', 'GD_GDYOUXIANW', '广东', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('604', '2', '广东开平有线', 'GD_KP-KPYOUXIAN', '广东', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('605', '2', '广东肇庆U互动', 'GD_ZQ-UHUDONG', '广东', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('606', '2', '广东南海同方建衡科技', 'GD_NH-JIANHENG', '广东', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('607', '2', '广东云浮罗定银视数字', 'GD_YFLD-YINSHI', '广东', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('608', '2', '广东潮州潮安', 'GD_CZ-CHAOAN', '广东', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('609', '2', '广州九洲', 'GD_GZ-JIUZHOU', '广东', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('610', '2', '广州广东有线', 'GD_GZ-GDYOUXIAN', '广东', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('611', '2', '广州增城广播', 'GD_GZZC-ZCGUANGBO', '广东', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('612', '2', '广州珠江数码原装', 'GD_GZ-ZHUJIANGSHUMA', '广东', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('613', '2', '深圳天威数字机顶盒', 'GD_SZ-TIANWEISHUZI', '广东', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('614', '2', '深圳创维', 'GD_SZ-CHUANGWEI', '广东', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('615', '2', '深圳九洲', 'GD_SZ-JIUZHOU', '广东', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('616', '2', '深圳高清关外', 'GD_SZ-GUANWAI', '广东', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('617', '2', '珠海广电数字机顶盒', 'GD_ZH-ZHGDSHUZI', '广东', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('618', '2', '珠江数码带学习', 'GD_ZHUJIANGSHUMAX', '广东', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('619', '2', '珠海九洲', 'GD_ZH-JIUZHOU', '广东', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('620', '2', '珠海RC-8A', 'GD-ZH-RC-8A', '广东', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('621', '2', '东莞九洲', 'GD_DG-JIUZHOU', '广东', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('622', '2', '东莞同洲电子', 'GD_DG-TONGZHOU', '广东', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('623', '2', '佛山天柏', 'GD_FS-TIANBAI', '广东', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('624', '2', '佛山同洲', 'GD_FS-TONGZHOU', '广东', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('625', '2', '佛山创维', 'GD_FS-CHUANGWEI', '广东', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('626', '2', '佛山数字电视', 'GD_FS-FSSHUZITV', '广东', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('627', '2', '佛山清华同方', 'GD_FS-TONGFANG', '广东', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('628', '2', '汕头司马浦', 'GD_ST-SIMAPU', '广东', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('629', '2', '汕头潮阳贵屿', 'GD_STCY-GUIYU', '广东', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('630', '2', '广铁数字创维', 'GD_GUANGTIECHUANGWEI', '广东', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('631', '2', '广铁数字九联', 'GD_GUANGTIEJIULIAN', '广东', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('632', '2', '河源广播佳彩', 'GD_HY-JIACAI', '广东', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('633', '2', '河源广电数源', 'GD_HY-HYGDSHUYUAN', '广东', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('634', '2', '东莞广电', 'GD_DG-DGGD', '广东', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('635', '2', '湛江数字', 'GD_ZJ-ZJSHUZI', '广东', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('636', '2', '普宁数字', 'GD_PN-PNSHUZI', '广东', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('637', '2', '博罗有线', 'GD_BL-BLYOUXIAN', '广东', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('638', '2', '梅州有线', 'GD_MZ-MZYOUXIAN', '广东', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('639', '2', '惠东有线', 'GD_HD-HDYOUXIAN', '广东', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('640', '2', '顺德有线', 'GD_SD-SDYOUXIAN', '广东', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('641', '2', '湛江U互动', 'GD_ZJ-UHUDONG', '广东', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('642', '2', '惠州广电网络', 'GD_HZ-HZGDWANGLUO', '广东', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('643', '2', '中山中广视讯', 'GD_ZS-ZHONGGUANG', '广东', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('644', '2', '佛山同洲电子', 'GD_FS-TONGZHOU', '广东', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('645', '2', '清远广播电视台', 'GD_QY-QYGUANGBOTV', '广东', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('646', '2', '增城有线电视台', 'GD_ZC-ZCYOUXIANTV', '广东', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('647', '2', '珠江数码万能TV', 'GD_ZHUJIANGSHUMAW', '广东', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('648', '2', '其他', 'GD_QITA', '广东', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('649', '2', '山东海尔', 'SD_HAIER', '山东', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('650', '2', '山东创维', 'SD_CHUANGWEI', '山东', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('651', '2', '山东威海', 'SD_WEIHAI', '山东', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('652', '2', '山东昌邑', 'SD_CHANGYI', '山东', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('653', '2', '山东峡山', 'SD_XIASHAN', '山东', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('654', '2', '山东临沂华为', 'SD_LY-HUAWEI', '山东', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('655', '2', '山东淮坊青州', 'SD_HF-QINGZHOU', '山东', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('656', '2', '山东邹城金网通', 'SD_ZC-JINWANGTONG', '山东', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('657', '2', '山东烟台数字电视', 'SD_YT-YTSHUZITV', '山东', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('658', '2', '山东淄博', 'SD_ZIBO', '山东', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('659', '2', '山东淄博新视源', 'SD_ZB-XINSHIYUAN', '山东', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('660', '2', '山东淄博数字电视', 'SD_ZB-ZBSHUZITV', '山东', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('661', '2', '山东淄博青鸟华光', 'SD_ZB-QNHUAGUANG', '山东', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('662', '2', '山东淄博同洲电子', 'SD_ZB-TONGZHOU', '山东', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('663', '2', '山东济南海尔-海信机顶盒', 'SD_JN-HAIER/HAIXIN', '山东', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('664', '2', '济南海尔', 'SD_JN-HAIER', '山东', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('665', '2', '济南海信', 'SD_JN-HAIXIN', '山东', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('666', '2', '济南/嘉兴天柏', 'SD_JN/JX-TIANBAI', '山东', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('667', '2', '济南S6102B', 'SD_JN-S6102B', '山东', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('668', '2', '青岛大显', 'SD_QD-DAXIAN', '山东', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('669', '2', '青岛海信', 'SD_QD-HAIXIN', '山东', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('670', '2', '青岛九洲', 'SD_QD-JIUZHOU', '山东', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('671', '2', '邹城有线', 'SD_ZC-ZCYOUXIAN', '山东', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('672', '2', '邹城七合一', 'SD_ZC-ZC7IN', '山东', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('673', '2', '胶州有线', 'SD_JZ-JZYOUXIAN', '山东', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('674', '2', '菏泽有线', 'SD_HZ-HZYOUXIAN', '山东', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('675', '2', '安丘广电网络', 'SD_AQ-AQGD', '山东', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('676', '2', '聊城中广', 'SD_LC-LCZHONGGUANG', '山东', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('677', '2', '其他', 'SD_QITA', '山东', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('678', '2', '福建八合一', 'FJ_FJ8IN', '福建', 'F', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('679', '2', '厦门创维', 'FJ_XM-CHUANGWEI', '福建', 'F', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('680', '2', '厦门大华', 'FJ_XM-DAHUA', '福建', 'F', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('681', '2', '厦门清华同方', 'FJ_XM-TONGFANG', '福建', 'F', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('682', '2', '泉州广电数字机顶盒', 'FJ_QZ-QZGDSHUZI', '福建', 'F', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('683', '2', '泉州广电电信七合一', 'FJ_QZ-QZGDDIANXIN7IN', '福建', 'F', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('684', '2', '泉州广电清华同方', 'FJ_QZ-QZGDTONGFANG', '福建', 'F', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('685', '2', '福州广电六合一', 'FJ_FZ-FZGD6IN', '福建', 'F', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('686', '2', '其他', 'FJ_QITA', '福建', 'F', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('687', '2', '遵义', 'GZ_ZUNYI', '贵州', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('688', '2', '遵义华为', 'GZ_ZY-HUAWEI', '贵州', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('689', '2', '遵义金网通', 'GZ_ZY-JINWANGTONG', '贵州', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('690', '2', '遵义同洲', 'GZ_ZY-TONGZHOU', '贵州', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('691', '2', '遵义数字电视', 'GZ_ZY-ZYSHUZITV', '贵州', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('692', '2', '遵义广电网络', 'GZ_ZY-ZYGD', '贵州', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('693', '2', '遵义广电华为-湖南', 'GZ_ZY-HUAWEI-HUNAN', '贵州', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('694', '2', '其他', 'GZ_QITA', '贵州', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('695', '2', '南昌广电', 'JX_NC-NCGD', '江西', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('696', '2', '江西有线', 'JX_JXYOUXIAN', '江西', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('697', '2', '其他', 'JX_QITA', '江西', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('698', '2', '内蒙古广电网络', 'NMG_NMGGD', '内蒙古', 'N', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('699', '2', '牙克石', 'NMG_YAKESHI', '内蒙古', 'N', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('700', '2', '河北/内蒙古', 'NMG_HB/NMG', '内蒙古', 'N', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('701', '2', '其他', 'NMG_QITA', '内蒙古', 'N', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('702', '2', '上海数字电视', 'SH_SHSHUZITV', '上海', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('703', '2', '其他', 'SH_QITA', '上海', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('704', '2', '湖北视讯', 'HB_SHIXUN', '湖北', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('705', '2', '湖北华为', 'HB_HUAWEI', '湖北', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('706', '2', '湖北武穴', 'HB_WUXUE', '湖北', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('707', '2', '湖北阳新广电网络', 'HB_YX-YXGD', '湖北', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('708', '2', '湖北数字电视', 'HB_HBSHUZITV', '湖北', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('709', '2', '武汉楚天金纬', 'HB_WH-CHUTIANJINWEI', '湖北', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('710', '2', '武汉数字电视', 'HB_WH-WHSHUZITV', '湖北', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('711', '2', '长虹BK12C', 'HB_CHANGHONGBK12C', '湖北', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('712', '2', 'C6252C', 'HB_C6252C', '湖北', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('713', '2', '湖北荆州视信网络', 'HB_JZ-SHIXIN', '湖北', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('714', '2', '湖北荆州视信五合一', 'HB_JZ-SHIXIN5IN', '湖北', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('715', '2', '其他', 'HB_QITA', '湖北', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('716', '2', '四川长虹', 'SC_CHANGHONG', '四川', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('717', '2', '成都数字电视', 'SC_CD-CDSHUZITV', '四川', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('718', '2', '四川成都新大陆', 'SC_CD-XINDALU', '四川', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('719', '2', '四川宜宾机顶盒', 'SC_YIBIN', '四川', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('720', '2', '其他', 'SC_QITA', '四川', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('721', '2', '河北创佳', 'HB_JIACHUANG', '河北', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('722', '2', '河北/内蒙古', 'HB_HB/NMG', '河北', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('723', '2', '河北内蒙古广电', 'HB_NMGGD', '河北', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('724', '2', '河北泊头数字金网通', 'HB_BT-BTJINWANGTONG', '河北', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('725', '2', '河北泊头数字C6252C', 'HB_BT-C6252C', '河北', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('726', '2', '河北广电网络集团', 'HB-HBGDWANGLUO', '河北', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('727', '2', '河北沧州SDT', 'HB_CZ-SDT', '河北', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('728', '2', '承德广通', 'HB_CD-CDGUANGTONG', '河北', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('729', '2', '石家庄新媒体', 'HB_SJZ-XINMEITI', '河北', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('730', '2', '其他', 'HB_QITA', '河北', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('731', '2', '北京海淀区', 'BJ_HAIDINGQU', '北京', 'B', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('732', '2', '北京歌华有线', 'BJ_GEHUAYOUXIAN', '北京', 'B', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('733', '2', '北京歌华升级版原装', 'BJ_GEHUASHENGJI', '北京', 'B', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('734', '2', '其他', 'BJ_QITA', '北京', 'B', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('735', '2', '河南滑县', 'HN_HUAXIAN', '河南', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('736', '2', '河南长垣九州', 'HN_CH-JIUZHOU', '河南', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('737', '2', '郑州威科姆', 'HN_ZZ-WEIKEMU', '河南', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('738', '2', '郑州银河', 'HN_ZZ-YINHE', '河南', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('739', '2', '郑州/北京', 'HN_ZZ/BJ', '河南', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('740', '2', '三门峡灵宝广电', 'HN_SMXLB-LBGD', '河南', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('741', '2', '三门峡金网通简版', 'HN_SMX-JINWANGTONG', '河南', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('742', '2', '三门峡九洲简版', 'HN_SMX-JIUZHOU', '河南', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('743', '2', '三门峡灵宝广电万能TV', 'HN_SMXLB-LBGDW', '河南', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('744', '2', '三门峡', 'HN_SANMENXIA', '河南', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('745', '2', '开封广电同方', 'HN_KF-KFGDTONGFANG', '河南', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('746', '2', '郑州摩托罗拉', 'HN_ZZ-MOTUOLUOLA', '河南', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('747', '2', '洛阳数字电视', 'HN_LY-LYSHUZITV', '河南', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('748', '2', '其他', 'HN_QITA', '河南', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('749', '2', '新疆广电网络', 'XJ_XJGDWANGLUO', '新疆', 'X', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('750', '2', '新疆广电大显', 'XJ_XJGDDAXIAN', '新疆', 'X', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('751', '2', '新疆广电华为', 'XJ_XJGDHUAWEI', '新疆', 'X', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('752', '2', '新疆广电数源', 'XJ_XJGDSHUYUAN', '新疆', 'X', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('753', '2', '同洲电子', 'XJ-TONGZHOU', '新疆', 'X', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('754', '2', '九联金网通', 'XJ_JIULIANJINWANGTONG', '新疆', 'X', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('755', '2', '莱钢数字电视', 'XJ_LAIGANGSHUZITV', '新疆', 'X', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('756', '2', '金亚股份', 'XJ_JINYA', '新疆', 'X', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('757', '2', 'RC1457', 'XJ_RC1457', '新疆', 'X', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('758', '2', '索伊', 'XJ_SUOYI', '新疆', 'X', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('759', '2', '汤姆逊', 'XJ_TONGMUXUN', '新疆', 'X', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('760', '2', '其他', 'XJ_QITA', '新疆', 'X', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('761', '2', '陕西极众', 'SX_JIZONG', '陕西', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('762', '2', '陕西海信', 'SX_HAIXIN', '陕西', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('763', '2', '陕西广电数字机顶盒', 'SX_SXGDSHUZI', '陕西', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('764', '2', '西安爱华', 'SX_XA-AIHUA', '陕西', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('765', '2', '西安创维', 'SX_XA-CHUANGWEI', '陕西', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('766', '2', '西安大华', 'SX_XA-DAHUA', '陕西', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('767', '2', '西安DVB', 'SX_XA-DVB', '陕西', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('768', '2', '西安步步高STC002', 'SX_XA-BUBUGAOSTC002', '陕西', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('769', '2', '西安步步高STC007', 'SX_XA-BUBUGAOSTC007', '陕西', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('770', '2', '其他', 'SX_QITA', '陕西', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('771', '2', '湖南有线', 'HN_HNYOUXIAN', '湖南', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('772', '2', '湖南同辉', 'HN_TONGHUI', '湖南', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('773', '2', '湖南户户通数字', 'HN_HUHUTONGSHUZI', '湖南', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('774', '2', '湖南有线二合一', 'HN_HNYOUXIAN2IN', '湖南', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('775', '2', '郴州有线', 'HN_CZ-CZYOUXIAN', '湖南', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('776', '2', '郴州有线汉寿', 'HN_CZ-CZYXHANSHOU', '湖南', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('777', '2', '高斯贝尔', 'HN_GAOSIBEIER', '湖南', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('778', '2', '其他', 'HN_QITA', '湖南', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('779', '2', '山西阳泉', 'SX_YANGQUAN', '山西', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('780', '2', '山西晋城广电', 'SX_JC-JCGGD', '山西', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('781', '2', '山西晋中数字', 'SX_JZ-JZSHUZI', '山西', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('782', '2', '晋中广电', 'SX_JZ-JZGUANGDIAN', '山西', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('783', '2', '晋中广电四合一', 'SX_JZ-JZGD4IN', '山西', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('784', '2', '太原广电', 'SX_TY-TYGD', '山西', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('785', '2', '太原市有线通', 'SX_TY-TYYOUXIANTONG', '山西', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('786', '2', '太原市华为机顶盒', 'SX_TY-HUAWEI', '山西', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('787', '2', '太原市创维机顶盒', 'SX_TY-CHUANGWEI', '山西', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('788', '2', '太原市泰森机顶盒', 'SX_TY-TAISEN', '山西', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('789', '2', '太原市银河机顶盒', 'SX-TY-YINHE', '山西', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('790', '2', '太原市浪潮机顶盒带学习', 'SX_TY-LANGCHAOX', '山西', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('791', '2', '大同有线', 'SX_DT-DTYOUXIAN', '山西', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('792', '2', '大同数字有线', 'SX_DT-DTSHUZIYOUXIAN', '山西', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('793', '2', '康特集团', 'SX_KANGTEJITUAN', '山西', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('794', '2', '其他', 'SX_QITA', '山西', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('795', '2', '浙江嘉兴', 'ZJ_JIAXING', '浙江', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('796', '2', '浙江东阳电信', 'ZJ_DY-DYDIANXIN', '浙江', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('797', '2', '浙江湖州安吉数字', 'ZJ_HZAJ-AJSHUZI', '浙江', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('798', '2', '浙江象山巨鹰科技', 'ZJ_XS-JUYING', '浙江', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('799', '2', '浙江上虞中广有线', 'ZJ_SY-SYZHONGGUANG', '浙江', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('800', '2', '浙江杭州中广有线', 'ZJ_HZ-HZZHONGGUANG', '浙江', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('801', '2', '杭州大华', 'ZJ_HZ-DAHUA', '浙江', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('802', '2', '杭州大显', 'ZJ_HZ-DAXIAN', '浙江', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('803', '2', '杭州华为', 'ZJ_HZ-HUAWEI', '浙江', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('804', '2', '杭州数源', 'ZJ_HZ-SHUYUAN', '浙江', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('805', '2', '杭州彩视科技', 'ZJ_HZ-CAISHI', '浙江', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('806', '2', '杭州摩托罗拉', 'ZJ_HZ-MOTUOLUOLA', '浙江', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('807', '2', '宁波创维', 'ZJ_NB-CHUANGWEI', '浙江', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('808', '2', '宁波海信', 'ZJ_NB-HAIXIN', '浙江', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('809', '2', '宁波华为', 'ZJ_NB-HUAWEI', '浙江', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('810', '2', '宁波九洲', 'ZJ_NB-JIUZHOU', '浙江', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('811', '2', '宁波同洲电子', 'ZJ_NB-TONGZHOU', '浙江', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('812', '2', '宁波DVB带学习', 'ZJ_NB-DVBX', '浙江', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('813', '2', '宁波镇海同洲电子', 'ZJ_NBZH-TONGZHOU', '浙江', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('814', '2', '宁波九洲RMC-C033', 'ZJ_NB-JIUZHOURMC-C033', '浙江', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('815', '2', '温州华为', 'ZJ_WZ-HUAWEI', '浙江', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('816', '2', '温州四通', 'ZJ_WZ-SITONG', '浙江', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('817', '2', '温州清华同方', 'ZJ_WZ-TONGFANG', '浙江', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('818', '2', '义乌广电', 'ZJ_YW-YWGD', '浙江', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('819', '2', '绍兴华为', 'ZJ_SX-HUAWEI', '浙江', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('820', '2', '绍兴九洲', 'ZJ_SX-JIUZHOU', '浙江', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('821', '2', '扬州TZW-A', 'ZJ_YZ-TZW-A', '浙江', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('822', '2', '扬州数字电视广电网络', 'ZJ_YZ-YZGDWANGLUO', '浙江', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('823', '2', '云和数字', 'ZJ_YH-YHSHUZI', '浙江', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('824', '2', '台州有线', 'ZJ_TZ-TZYOUXIAN', '浙江', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('825', '2', '临海数字', 'ZJ_LH-LHSHUZI', '浙江', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('826', '2', '嘉善数源', 'ZJ_JS-SHUYUAN', '浙江', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('827', '2', '鄞州有线', 'ZJ_YZ-YZYOUXIAN', '浙江', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('828', '2', '东阳数字', 'ZJ_DY-DYSHUZI', '浙江', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('829', '2', '嘉善爱华', 'ZJ_JS-AIHUA', '浙江', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('830', '2', '金华大华', 'ZJ_JH-DAHUA', '浙江', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('831', '2', '临海九合一', 'ZJ_LH-LH9IN', '浙江', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('832', '2', '海盐良友科技', 'ZJ_HY-LIANGYOU', '浙江', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('833', '2', '路桥数字电视', 'ZJ_LQ-LQSHUZITV', '浙江', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('834', '2', '诸暨数字电视', 'ZJ_ZJ-ZJSHUZITV', '浙江', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('835', '2', '湖州数字电视', 'ZJ_HZ-HZSHUZITV', '浙江', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('836', '2', '武义数字电视', 'ZJ_WY-WYSHUZITV', '浙江', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('837', '2', '衢州数字电视', 'ZJ_QZ-QZSHUZITV', '浙江', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('838', '2', '苍南数字电视', 'ZJ_CN-CNSHUZITV', '浙江', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('839', '2', '桐乡广电网络', 'ZJ_TX-TXGD', '浙江', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('840', '2', '永康数字电视', 'ZJ_YK-YKSHUZITV', '浙江', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('841', '2', '开化数字电视', 'ZJ_KH-KHSHUZITV', '浙江', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('842', '2', '济南/嘉兴天柏', 'ZJ-JN/JX-TIANBAI', '浙江', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('843', '2', '其他', 'ZJ_QITA', '浙江', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('844', '2', '黑龙江泰来', 'HLJ_TAILAI', '黑龙江', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('845', '2', '黑龙江龙江网络', 'HLJ_LONGJIANG', '黑龙江', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('846', '2', '黑龙江身农垦有线', 'HLJ_SHENNONGKEN', '黑龙江', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('847', '2', '哈尔滨有线', 'HLJ_HEB-HEBYOUXIAN', '黑龙江', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('848', '2', '哈尔滨延寿', 'HLJ_HEB-HEBYANSHOU', '黑龙江', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('849', '2', '哈尔滨RS-17A', 'HLJ_HEB-RS-17A', '黑龙江', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('850', '2', '哈尔滨百事通', 'HLJ_HEB-BAISHITONG', '黑龙江', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('851', '2', '哈尔滨斯达康', 'HLJ_HEB-SIDAKANG', '黑龙江', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('852', '2', '哈尔滨有线电视DVB', 'HLJ_HEB-HEBYOUXIANDVB', '黑龙江', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('853', '2', '牡丹江RS-26AL', 'HLJ_MDJ-RS-26AL', '黑龙江', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('854', '2', '牡丹江大鹏数字电视', 'HLJ_MDJ-DAPENGSHUZITV', '黑龙江', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('855', '2', '虹企传媒', 'HLJ_HONGQI', '黑龙江', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('856', '2', '大庆银河学习型', 'HLJ_DQ-DQYINHEX', '黑龙江', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('857', '2', '黑河广电机顶盒', 'HLJ_HH-HHGD', '黑龙江', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('858', '2', '齐齐哈尔数字电视', 'HLJ_QQHE-QQHESHUZITV', '黑龙江', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('859', '2', '其他', 'HLJ_QITA', '黑龙江', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('860', '2', '南海', 'HN_NANHAI', '海南', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('861', '2', '南海天柏', 'HN_NANHAITIANBAI', '海南', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('862', '2', '南海创维', 'HN_NANHAICHUANGWEI', '海南', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('863', '2', '海南有线', 'HN_HNYOUXIAN', '海南', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('864', '2', '南海同洲电子', 'HN_NANHAITONGZHOU', '海南', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('865', '2', '南海清华同方', 'HN_NANHAITONGFANG', '海南', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('866', '2', '其他', 'HN_QITA', '海南', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('867', '2', '广西', 'GX_GUANGXI', '广西', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('868', '2', '广西广电网络', 'GX_GXGD', '广西', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('869', '2', '广西有线简版', 'GX_GXYOUXIAN', '广西', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('870', '2', '其他', 'GX_QITA', '广西', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('871', '2', '重庆', 'CQ_CHONGQING', '重庆', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('872', '2', '重庆长虹', 'CQ_CHANGHONG', '重庆', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('873', '2', '重庆创维', 'CQ_CHUANGWEI', '重庆', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('874', '2', '重庆银科', 'CQ_YINHE', '重庆', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('875', '2', '重庆有线', 'CQ_CQYOUXIAN', '重庆', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('876', '2', '其他', 'CQ_QITA', '重庆', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('877', '2', '昆明天柏普天', 'YN_KM-TIANBAIPUTIAN', '云南', 'Y', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('878', '2', '昆明华为机顶盒', 'YN_KM-HUAWEI', '云南', 'Y', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('879', '2', '云南曲靖1 YNSD-Ⅲ', 'YN_QJ1-YNSD-III', '云南', 'Y', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('880', '2', '云南曲靖2', 'YN_QUJING2', '云南', 'Y', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('881', '2', '华为', 'YN_HUAWEI', '云南', 'Y', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('882', '2', '昆广网络', 'YN_KUNGUANG', '云南', 'Y', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('883', '2', '海尔海信', 'YN_HAIERHAIXIN', '云南', 'Y', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('884', '2', '郑州-昆明', 'YN_ZHENGZHOU-KUNMING', '云南', 'Y', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('885', '2', '其他', 'YN_QITA', '云南', 'Y', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('886', '2', '大连海尔', 'LN_DL-HAIER', '辽宁', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('887', '2', '大连大显', 'LN_DL-DAXIAN', '辽宁', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('888', '2', '大连九洲', 'LN_DL-JIUZHOU', '辽宁', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('889', '2', '大连海信', 'LN_DL_HAIXIN', '辽宁', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('890', '2', '大连汤姆森', 'LN_DL-TANGMUSEN', '辽宁', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('891', '2', '沈阳大显', 'LN_SY-DAXIAN', '辽宁', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('892', '2', '沈阳海信', 'LN_SY-HAIXIN', '辽宁', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('893', '2', '沈阳佳创', 'LN_SY-JIACHUANG', '辽宁', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('894', '2', '沈阳九洲', 'LN_SY-JIUZHOU', '辽宁', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('895', '2', '沈阳诺基亚', 'LN_SY-NUOJIYA', '辽宁', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('896', '2', '沈阳汤姆森', 'LN_SY-TANGMUSEN', '辽宁', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('897', '2', '沈阳传媒网络', 'LN_SY-CHUANMEI', '辽宁', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('898', '2', '辽宁朝阳新大陆', 'LN_CY-XINDALU', '辽宁', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('899', '2', '辽宁铁岭天光有线', 'LN_TL-TIANGUANG', '辽宁', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('900', '2', '瓦房店', 'LN_WAFANGDIAN', '辽宁', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('901', '2', '本溪创维', 'LN_BX-CHUANGWEI', '辽宁', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('902', '2', '新民有线', 'LN_XM-XMYOUXIAN', '辽宁', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('903', '2', '本溪广电', 'LN_BX-BXGD', '辽宁', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('904', '2', '大显带学习', 'LN_DAXIANX', '辽宁', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('905', '2', '葫芦岛数字', 'LN_HLD-HLDSHUZI', '辽宁', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('906', '2', '抚顺数字电视', 'LN_FS-FSSHUZITV', '辽宁', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('907', '2', '其他', 'LN_QITA', '辽宁', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('908', '2', '江苏南通RS-30AQ', 'JS_NT-RS-30AQ', '江苏', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('909', '2', '江苏江阴', 'JS_JIANGYIN', '江苏', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('910', '2', '江阴天柏', 'JS_JY-TIANBAI', '江苏', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('911', '2', '南京创维', 'JS_NJ-CHUANGWEI', '江苏', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('912', '2', '南京熊猫', 'JS_NJ-XIONGMAO', '江苏', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('913', '2', '南京广电', 'JS_NJ-NJGD', '江苏', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('914', '2', '南京有线', 'JS_NJ-NJYOUXIAN', '江苏', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('915', '2', '无锡', 'JS_WUXI', '江苏', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('916', '2', '无锡有线', 'JS_WX-WXYOUXIAN', '江苏', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('917', '2', '苏州数字电视', 'JS_SZ-SZSHUZITV', '江苏', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('918', '2', '苏州天柏', 'JS_SZ-TIANBAI', '江苏', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('919', '2', '中兴', 'JS_ZHONGXING', '江苏', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('920', '2', '昆山有线', 'JS_KS-KSYOUXIAN', '江苏', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('921', '2', '盛泽有线', 'JS_SZ-SZYOUXIAN', '江苏', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('922', '2', '百事通-旧', 'JS_BAISHITONG-JIU', '江苏', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('923', '2', '百事通-新', 'JS_BAISHITONG-XIN', '江苏', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('924', '2', '张家港有线', 'JS_ZJG-ZJGYOUXIAN', '江苏', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('925', '2', '海门同洲有线', 'JS_HM-TONGZHOU', '江苏', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('926', '2', '常熟数字电视', 'JS_CS-CSSHUZITV', '江苏', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('927', '2', '其他', 'JS_QITA', '江苏', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('928', '2', '天津长虹', 'TJ_CHANGHONG', '天津', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('929', '2', '天津创维牌原装', 'TJ_CHUANGWEIPAI', '天津', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('930', '2', '天津创维牌带学习', 'TJ_CHUANGWEIPAIX', '天津', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('931', '2', '天津北京牌原装', 'TJ_BEIJINGPAI', '天津', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('932', '2', '天津北京牌带学习', 'TJ_BEIJINGPAIX', '天津', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('933', '2', '其他', 'TJ_QITA', '天津', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('934', '2', '吉林广电网络', 'JL_JLGDWANGLUO', '吉林', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('935', '2', '吉林广电网络客服用', 'JL_JLGDKEFUYONG', '吉林', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('936', '2', '吉林广电网络遥博士', 'JL_JLGDYAOBOSHI', '吉林', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('937', '2', '其他', 'JL_QITA', '吉林', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('938', '2', '甘肃有线', 'GS_GSYOUXIAN', '甘肃', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('939', '2', '甘肃九洲', 'GS_JIUZHOU', '甘肃', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('940', '2', '甘肃静宁', 'GS_JINGNING', '甘肃', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('941', '2', '其他', 'GS_QITA', '甘肃', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('942', '2', '香港高清', 'XG_GAOQING', '香港', 'X', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('943', '2', '香港CBL', 'XG_CBL', '香港', 'X', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('944', '2', '香港NOWTV', 'XG_NOWTV', '香港', 'X', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('945', '2', '香港宽频', 'XG_KUANPIN', '香港', 'X', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('946', '2', '其他', 'XG_QITA', '香港', 'X', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('947', '2', '英集', 'AH_YINGJI', '安徽', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('948', '2', '安广网络', 'AH_ANGUANG', '安徽', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('949', '2', '安广康佳KK-Y305A', 'AH_KANGJIAKK-305A', '安徽', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('950', '2', '安徽铜陵1', 'AH_TONGLING1', '安徽', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('951', '2', '安徽铜陵2海尔', 'AH_TONGLING2HAIER', '安徽', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('952', '2', '安徽阜阳', 'AH_FUYANG', '安徽', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('953', '2', '其他', 'AH_QITA', '安徽', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('954', '2', '百胜', 'QT_BAISHENG', '其他地区', '#', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('955', '2', '北广', 'QT_BEIGUANG', '其他地区', '#', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('956', '2', '长虹', 'QT_CHANGHONG', '其他地区', '#', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('957', '2', '航科', 'QT_HANGKE', '其他地区', '#', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('958', '2', '华为', 'QT_HUAWEI', '其他地区', '#', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('959', '2', '厦华', 'QT_XIAHUA', '其他地区', '#', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('960', '2', '松下', 'QT_SONGXIA', '其他地区', '#', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('961', '2', '天柏', 'QT_TIANBAI', '其他地区', '#', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('962', '2', '佳创', 'QT_JIACHUANG', '其他地区', '#', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('963', '2', '九联', 'QT_JIULIAN', '其他地区', '#', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('964', '2', '九洲', 'QT_JIUZHOU', '其他地区', '#', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('965', '2', '力合', 'QT_LIHE', '其他地区', '#', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('966', '2', '全景', 'QT_QUANJING', '其他地区', '#', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('967', '2', '银河', 'QT_YINHE', '其他地区', '#', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('968', '2', '上广电', 'QT_SHANGGUANGDIAN', '其他地区', '#', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('969', '2', '斯达康', 'QT_SIDAKANG', '其他地区', '#', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('970', '2', 'MATR1X', 'QT_MATR1X', '其他地区', '#', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('971', '2', '聚友网络', 'QT_JUYOUWANGLUO', '其他地区', '#', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('972', '2', '同洲电子', 'QT_TONGZHOUDIANZI', '其他地区', '#', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('973', '2', '成都康特', 'QT_CHENGDUKANGTE', '其他地区', '#', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('974', '2', '东方广视', 'QT_DONGFANGGUANGSHI', '其他地区', '#', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('975', '2', '其他', 'QT_QITA', '其他地区', '#', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('976', '3', '先锋', 'PIONEER', 'P', 'P', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('977', '3', '飞利浦', 'PHILIPS', 'P', 'P', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('978', '3', '先科', 'SAST', 'S', 'S', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('979', '3', '万利达', 'MALATA', 'M', 'M', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('980', '3', '松下', 'PANASONSIC', 'P', 'P', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('981', '3', '索爱', 'SOAIY', 'S', 'S', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('982', '3', '索尼', 'SONY', 'S', 'S', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('983', '3', '杰科', 'GIEC', 'G', 'G', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('984', '3', '奇声', 'QISHENG', 'Q', 'Q', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('985', '3', '新科', 'SHINCO', 'S', 'S', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('986', '3', '夏新', 'AMOI', 'A', 'A', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('987', '3', '步步高', 'BBK', 'B', 'B', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('988', '3', '海信', 'HISENSE', 'H', 'H', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('989', '3', '海尔', 'HAIER', 'H', 'H', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('990', '3', '奔腾', 'BNTN', 'B', 'B', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('991', '3', '日立', 'HITACHI', 'H', 'H', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('992', '3', '康佳', 'KONKA', 'K', 'K', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('993', '3', 'LG', 'LG', 'L', 'L', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('994', '3', '纽曼', 'NEWSMY', 'N', 'N', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('995', '3', '日电', 'NEC', 'N', 'N', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('996', '3', '三星', 'SAMSUNG', 'S', 'S', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('997', '3', '创维', 'SKYWORTH', 'S', 'S', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('998', '3', 'OPPO', 'OPPO', 'O', 'O', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('999', '3', '摩托罗拉', 'MOTOROLA', 'M', 'M', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1000', '3', '中兴', 'ZTE', 'Z', 'Z', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1001', '3', '夏普', 'SHARP', 'S', 'S', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1002', '3', '清华同方', 'TONGFANG', 'T', 'T', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1003', '3', '东芝', 'TOSHIBA', 'T', 'T', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1004', '3', '金正', 'NINTAUS', 'N', 'N', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1005', '3', '谷天', 'GUTIAN', 'G', 'G', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1006', '3', '山水', 'SANSUI', 'S', 'S', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1007', '3', '实益达', 'SEASTAR', 'S', 'S', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1008', '3', '马兰士', 'MARANTZ', 'M', 'M', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1009', '3', '德赛', 'DESAY', 'D', 'D', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1010', '3', '天龙', 'DENON', 'D', 'D', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1011', '3', '巨科', 'JUKE', 'J', 'J', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1012', '3', '华录', 'HUALU', 'H', 'H', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1013', '3', '金业', 'GOLDYIP', 'G', 'G', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1014', '3', '雅马哈', 'YAMAHA', 'Y', 'Y', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1015', '3', '3M', '3M', '#', '#', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1016', '3', 'ACL', 'ACL', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1017', '3', 'ADA', 'ADA', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1018', '3', '爱琴', 'ADCOM', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1019', '3', '爱斯乐', 'AETHRA', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1020', '3', '爱多', 'AIDUO', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1021', '3', '爱华', 'AIWA', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1022', '3', '雅佳', 'AKAI', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1023', '3', '奥莱克', 'ALLLIKE', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1024', '3', '阿尔派', 'ALPINE', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1025', '3', '安礼轩', 'ANLIXUAN', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1026', '3', 'APEX', 'APEX', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1027', '3', '傲立', 'AUDIOLAB', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1028', '3', '皇冠', 'AVANCE', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1029', '3', 'BANG&OIUFSEN', 'BANG&OIUFSEN', 'B', 'B', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1030', '3', '霸王', 'BAWANG', 'B', 'B', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1031', '3', 'BMB', 'BMB', 'B', 'B', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1032', '3', '博士', 'BOSE', 'B', 'B', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1033', '3', 'AUDIO LABS', 'AUDIO LABS', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1034', '3', '卡维', 'CARVER', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1035', '3', '丽声', 'CAV', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1036', '3', '凯音', 'CAYIN', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1037', '3', '长虹', 'CHANGHONG', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1038', '3', 'CHANNEL MASTER', 'CHANNEL MASTER', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1039', '3', '歌乐', 'CLARION', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1040', '3', '星影院', 'DBSTAR', 'D', 'D', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1041', '3', '大宇', 'DEAWOO', 'D', 'D', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1042', '3', 'DMX', 'DMX', 'D', 'D', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1043', '3', '丹尼克斯', 'DYNEX', 'D', 'D', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1044', '3', '忆捷', 'EAGET', 'E', 'E', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1045', '3', '意创生活', 'ECHLIFE', 'E', 'E', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1046', '3', '亿格瑞', 'EGREAT', 'E', 'E', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1047', '3', 'ESCIENT', 'ESCIENT', 'E', 'E', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1048', '3', 'FAROUDJA', 'FAROUDJA', 'F', 'F', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1049', '3', 'FISHER', 'FISHER', 'F', 'F', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1050', '3', '乐动', 'GODOVIC', 'G', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1051', '3', '哈曼卡顿', 'HARMAN KARDON', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1052', '3', '凯蒂猫', 'HELLOKITTY', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1053', '3', '哈曼', 'HNM', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1054', '3', '现代', 'HYUNDAI', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1055', '3', 'INTEGRA', 'INTEGRA', 'I', 'I', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1056', '3', 'JATON', 'JATON', 'J', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1057', '3', 'JBL', 'JBL', 'J', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1058', '3', '剑桥', 'JIANQIAO', 'J', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1059', '3', '胜利', 'JVC', 'J', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1060', '3', '开博尔', 'KAIBOER', 'K', 'K', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1061', '3', '喀秋莎', 'KAQIUSHA', 'K', 'K', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1062', '3', '健伍', 'KENWOOD', 'K', 'K', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1063', '3', '柯达', 'KODAK', 'K', 'K', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1064', '3', '奇力', 'KRELL', 'K', 'K', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1065', '3', '啦啦', 'LALA', 'L', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1066', '3', '乐笙', 'LESHENG', 'L', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1067', '3', '莲', 'LINN', 'L', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1068', '3', '礼意久久', 'LIYI99', 'L', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1069', '3', '龙之牌', 'LONGZHIPAI', 'L', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1070', '3', '马克莱文森', 'MARK LEVINSON', 'M', 'M', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1071', '3', '马仕特', 'MATESTAR', 'M', 'M', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1072', '3', '麦景图', 'MCINTOSH', 'M', 'M', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1073', '3', '妙选', 'MIAOXUAN', 'M', 'M', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1074', '3', '咪宝', 'MIPRO', 'M', 'M', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1075', '3', '三菱', 'MITSUBISHI', 'M', 'M', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1076', '3', '中道', 'NAKAMICHI', 'N', 'N', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1077', '3', '诺普声', 'NOBSOUND', 'N', 'N', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1078', '3', 'NSM', 'NSM', 'N', 'N', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1079', '3', '柯耐弗', 'OKON', 'O', 'O', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1080', '3', '安桥', 'ONKYO', 'O', 'O', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1081', '3', '派高', 'PAIGAO', 'P', 'P', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1082', '3', '熊猫', 'PANDA', 'P', 'P', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1083', '3', '宝丽音', 'PARASOUND', 'P', 'P', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1084', '3', '宝利通', 'POLYCOM', 'P', 'P', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1085', '3', '翩美', 'PRIMARE', 'P', 'P', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1086', '3', '普斯', 'PROCEED', 'P', 'P', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1087', '3', 'PROSCAN', 'PROSCAN', 'P', 'P', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1088', '3', '普腾', 'PROTON', 'P', 'P', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1089', '3', 'QUASAR', 'QUASAR', 'Q', 'Q', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1090', '3', 'REVOX', 'REVOX', 'R', 'R', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1091', '3', '路遥', 'ROTEL', 'R', 'R', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1092', '3', 'RUNCO', 'RUNCO', 'R', 'R', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1093', '3', '三洋', 'SANYO', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1094', '3', '狮龙', 'SHERWOOD', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1095', '3', '灵数码', 'SHUMALING', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1096', '3', '惊艳云十二', 'SKYROCKER', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1097', '3', '首华', 'SOWA', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1098', '3', '实达', 'STAR', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1099', '3', '果珈', 'STAR5', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1100', '3', '孙小胜', 'SUNXIAOSHENG', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1101', '3', '索威', 'SV', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1102', '3', '上广电', 'SVA', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1103', '3', 'SYMA', 'SYMA', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1104', '3', '腾博', 'TANDBERG', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1105', '3', '泰斯康姆', 'TASCAM', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1106', '3', '大同', 'TATUNG', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1107', '3', '王牌', 'TCL', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1108', '3', '蒂雅克', 'TEAC', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1109', '3', '大鹰', 'THETA DIGTAL', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1110', '3', '汤姆逊', 'THOMSON', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1111', '3', '友利电', 'UNIDEN', 'U', 'U', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1112', '3', '威发', 'VIFA', 'V', 'V', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1113', '3', '万宝', 'WANBAO', 'W', 'W', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1114', '3', '万燕', 'WANYAN', 'W', 'W', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1115', '3', '蔚然', 'WEIRAN', 'W', 'W', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1116', '3', '礼无忧', 'WUYOU', 'W', 'W', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1117', '3', '先派', 'XIANPAI', 'X', 'X', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1118', '3', '雅桥', 'YACARE', 'Y', 'Y', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1119', '3', '雅琴', 'YAQIN', 'Y', 'Y', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1120', '3', '柚果', 'YOOGUO', 'Y', 'Y', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1121', '3', '裕兴', 'YUXING', 'Y', 'Y', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1122', '3', '增你智', 'ZENITH', 'Z', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1123', '4', '明基', 'BENQ', 'B', 'B', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1124', '4', '爱普生', 'EPSON', 'E', 'E', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1125', '4', '索尼', 'SONY', 'S', 'S', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1126', '4', '丽讯', 'VIVITEK', 'V', 'V', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1127', '4', '宏碁', 'ACER', 'A', 'A', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1128', '4', '奥图码', 'OPTOMA', 'O', 'O', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1129', '4', '松下', 'PANASONSIC', 'P', 'P', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1130', '4', '霸王兔', 'SANGMAX', 'S', 'S', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1131', '4', '日电', 'NEC', 'N', 'N', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1132', '4', '理光', 'RICOH', 'R', 'R', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1133', '4', '酷乐视', 'COOLUX', 'C', 'C', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1134', '4', '极米', 'XGIMI', 'X', 'X', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1135', '4', '智歌', 'ZECO', 'Z', 'Z', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1136', '4', '创荣', 'CRE', 'C', 'C', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1137', '4', '日立', 'HITACHI', 'H', 'H', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1138', '4', '夏普', 'SHARP', 'S', 'S', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1139', '4', '优派', 'VIEWSONIC', 'V', 'V', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1140', '4', '山水', 'SANSUI', 'S', 'S', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1141', '4', '雅图', 'ACTO', 'A', 'A', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1142', '4', '富可视', 'INFOCUS', 'I', 'I', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1143', '4', 'ASK', 'ASK', 'A', 'A', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1144', '4', '美高', 'MEGO', 'M', 'M', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1145', '4', 'LG', 'LG', 'L', 'L', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1146', '4', '卡西欧', 'CASIO', 'C', 'C', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1147', '4', '轰天炮', 'PONERSAUND', 'P', 'P', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1148', '4', '巴可', 'BARCO', 'B', 'B', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1149', '4', '百度', 'BAIDU', 'B', 'B', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1150', '4', '3M', '3M', '#', '#', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1151', '4', '爱其', 'EIKI', 'E', 'E', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1152', '4', '飞利浦', 'PHILIPS', 'P', 'P', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1153', '4', '科视', 'CHRISTIE', 'C', 'C', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1154', '4', '魔影', 'MOV', 'M', 'M', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1155', '4', '纽曼', 'NEWSMY', 'N', 'N', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1156', '4', '三菱', 'MITSUBISHI', 'M', 'M', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1157', '4', '佳能', 'CANON', 'C', 'C', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1158', '4', '海微', 'HAIWAY', 'H', 'H', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1159', '4', 'DP', 'DP', 'D', 'D', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1160', '4', '胜利', 'JVC', 'J', 'J', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1161', '4', '艾洛维', 'INOVEL', 'I', 'I', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1162', '4', '泰克思达', 'TREKSTOR', 'T', 'T', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1163', '4', '戴尔', 'DELL', 'D', 'D', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1164', '4', '爱国者', 'AIGO', 'A', 'A', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1165', '4', '可儿可思', 'CROCUS', 'C', 'C', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1166', '4', '三浦', 'SAIMPU', 'S', 'S', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1167', '4', '觅乐', 'MIROIR', 'M', 'M', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1168', '4', '惠普', 'HP', 'H', 'H', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1169', '4', 'TELSTAR', 'TELSTAR', 'T', 'T', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1170', '4', 'HTP', 'HTP', 'H', 'H', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1171', '4', '瑞视达', 'RUISHIDA', 'R', 'R', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1172', '4', '倍加乐', 'BIJELA', 'B', 'B', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1173', '4', '华阳', 'HUAYANG', 'H', 'H', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1174', '4', '华硕', 'ASUS', 'A', 'A', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1175', '4', '统帅', 'LEADER', 'L', 'L', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1176', '4', 'AIPTEK', 'AIPTEK', 'A', 'A', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1177', '4', '奇机', 'QIJI', 'Q', 'Q', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1178', '4', '柯达', 'KODAK', 'K', 'K', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1179', '4', '极客投', 'JIKETOU', 'J', 'J', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1180', '4', '索立得', 'SONEED', 'S', 'S', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1181', '4', '爱玛科', 'AIMC', 'A', 'A', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1182', '4', '暴享', 'BAOX', 'B', 'B', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1183', '4', 'CASMELY', 'CASMELY', 'C', 'C', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1184', '4', '普乐士', 'PLUS', 'P', 'P', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1185', '4', 'DIGIMIO', 'DIGIMIO', 'D', 'D', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1186', '4', '果珈', 'STAR5', 'S', 'S', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1187', '4', '阿西艾夫', 'ACAIF', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1188', '4', 'AEE', 'AEE', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1189', '4', '海联达', 'AIGALE', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1190', '4', '阿历克斯', 'ALIKESI', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1191', '4', 'AMPRO', 'AMPRO', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1192', '4', '长虹', 'CHANGHONG', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1193', '4', '畅讯', 'CHANGXUN', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1194', '4', 'CHISHOLM', 'CHISHOLM', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1195', '4', 'COSTAR', 'COSTAR', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1196', '4', 'CTX', 'CTX', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1197', '4', 'DAVIS', 'DAVIS', 'D', 'D', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1198', '4', '晨星', 'DAYSTAR', 'D', 'D', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1199', '4', '德普', 'DEPU', 'D', 'D', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1200', '4', '金视佳', 'DLP', 'D', 'D', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1201', '4', '宝得丽', 'DUKANE', 'D', 'D', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1202', '4', 'DWIN', 'DWIN', 'D', 'D', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1203', '4', 'ELECTROHOME', 'ELECTROHOME', 'E', 'E', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1204', '4', '爱路摩', 'ELMO', 'E', 'E', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1205', '4', '梵斯', 'FANSI', 'F', 'F', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1206', '4', '方正', 'FOUNDER', 'F', 'F', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1207', '4', '富士通', 'FUJISTU', 'F', 'F', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1208', '4', '思益', 'GAOKE', 'G', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1209', '4', '高科', 'GK', 'G', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1210', '4', '美视', 'GRANDVIW', 'G', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1211', '4', '海尔', 'HAIER', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1212', '4', '瀚影', 'HANYING', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1213', '4', '哈曼卡顿', 'HARMAN KARDON', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1214', '4', '惠斯特', 'HT', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1215', '4', '华柏', 'HUABAI', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1216', '4', 'HUGHES', 'HUGHES', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1217', '4', '加贺', 'JIAHE', 'J', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1218', '4', '映美', 'JOLIMARK', 'J', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1219', '4', '康禾', 'KOHO', 'K', 'K', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1220', '4', '朗曼', 'LANGMAN', 'L', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1221', '4', '联想', 'LENOVO', 'L', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1222', '4', '雷克赛恩', 'LUXCINE', 'L', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1223', '4', '万利达', 'MALATA', 'M', 'M', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1224', '4', '美格', 'MEGAPOWER', 'M', 'M', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1225', '4', '米力', 'MILI', 'M', 'M', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1226', '4', '摩图', 'MOTU', 'M', 'M', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1227', '4', 'NVIEW', 'NVIEW', 'N', 'N', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1228', '4', '平达', 'PD', 'P', 'P', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1229', '4', '先锋', 'PIONEER', 'P', 'P', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1230', '4', '宝利莱', 'POLAROID', 'P', 'P', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1231', '4', '拍得丽', 'PREMIER', 'P', 'P', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1232', '4', '宝施玛', 'PROXIMA', 'P', 'P', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1233', '4', '瑞诚', 'RUICHENG', 'R', 'R', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1234', '4', 'RUNCO', 'RUNCO', 'R', 'R', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1235', '4', '三星', 'SAMSUNG', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1236', '4', '三洋', 'SANYO', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1237', '4', '视丽', 'SELECO', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1238', '4', '影极', 'SIDAR', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1239', '4', '索拓', 'SOTO', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1240', '4', 'TELEX', 'TELEX', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1241', '4', '东芝', 'TOSHIBA', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1242', '4', '维度', 'VEIDOO', 'V', 'V', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1243', '4', '网讯', 'VTION', 'V', 'V', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1244', '4', '华银', 'WANIN', 'W', 'W', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1245', '4', 'WOLFVISION', 'WOLFVISION', 'W', 'W', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1246', '4', '雅马哈', 'YAMAHA', 'Y', 'Y', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1247', '4', '震旦', 'ZHENDAN', 'Z', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1248', '4', '中宝', 'ZHONGBAO', 'Z', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1249', '4', '中光学', 'ZHONGGUANGXUE', 'Z', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1250', '6', '爱华', 'AIWA', 'A', 'A', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1251', '6', '大宇', 'DEAWOO', 'D', 'D', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1252', '6', '金星', 'GOLDSTAR', 'G', 'G', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1253', '6', '日立', 'HITACHI', 'H', 'H', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1254', '6', '健伍', 'KENWOOD', 'K', 'K', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1255', '6', 'LG', 'LG', 'L', 'L', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1256', '6', '三菱', 'MITSUBISHI', 'M', 'M', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1257', '6', '日电', 'NEC', 'N', 'N', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1258', '6', 'NOKIA', 'NOKIA', 'N', 'N', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1259', '6', '松下', 'PANASONIC', 'P', 'P', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1260', '6', '飞利浦', 'PHILIPS', 'P', 'P', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1261', '6', '先锋', 'PIONEER', 'P', 'P', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1262', '6', '三星', 'SAMSUNG', 'S', 'S', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1263', '6', '三洋', 'SANYO', 'S', 'S', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1264', '6', '夏普', 'SHARP', 'S', 'S', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1265', '6', '西门子', 'SLEMENS', 'S', 'S', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1266', '6', '索尼', 'SONY', 'S', 'S', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1267', '6', '大同', 'TATUNG', 'T', 'T', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1268', '6', '歌林', 'TEAC', 'T', 'T', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1269', '6', '汤姆逊', 'THOMSOM', 'T', 'T', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1270', '6', '东芝', 'TOSHIBA', 'T', 'T', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1271', '6', '胜利', 'VECTOR', 'V', 'V', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1272', '7', '佳能', 'CANON', 'C', 'C', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1273', '7', '尼康', 'NIKON', 'N', 'N', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1274', '7', '奥林巴斯', 'OLYMPUS', 'O', 'O', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1275', '7', '松下', 'PANASONIC', 'P', 'P', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1276', '7', '宾得', 'PENTAX', 'P', 'P', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1277', '7', '银辉', 'SILVERLIT', 'S', 'S', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1278', '7', '索尼', 'SONY', 'S', 'S', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1279', '7', '斯丹德', 'STD', 'S', 'S', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1280', '7', '哈苏', 'HASSELBLAD', 'H', 'H', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1281', '7', '飞思', 'PHASEONE', 'P', 'P', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1282', '7', '徕卡', 'LEICA', 'L', 'L', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1283', '7', '玛米亚利图', 'MAMIYALEAF', 'M', 'M', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1284', '7', '适马', 'SIGMA', 'S', 'S', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1285', '7', '阿尔帕', 'ALPA', 'A', 'A', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1286', '7', '禄来', 'ROLLEI', 'R', 'R', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1287', '8', '美的', 'MIDEA', 'M', 'M', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1288', '8', '艾美特', 'AIRMATE', 'A', 'A', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1289', '8', '格力', 'GREE', 'G', 'G', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1290', '8', '戴森', 'DYSON', 'D', 'D', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1291', '8', '先锋', 'PIONEER', 'P', 'P', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1292', '8', '华生', 'WAHSON', 'W', 'W', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1293', '8', '赛亿', 'SHINEE', 'S', 'S', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1294', '8', '澳柯玛', 'AUCMA', 'A', 'A', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1295', '8', '富士宝', 'FUSHIBAO', 'F', 'F', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1296', '8', '联创', 'LIANCHUANG', 'L', 'L', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1297', '8', '客浦', 'CAPLE', 'C', 'C', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1298', '8', '海尔', 'HAIER', 'H', 'H', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1299', '8', '长虹', 'CHANGHONG', 'C', 'C', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1300', '8', '森田', 'MORITA', 'M', 'M', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1301', '8', '乐迪', 'NATRIDY', 'N', 'N', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1302', '8', '贝丽', 'BFREE', 'B', 'B', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1303', '8', '长城', 'CHANGCHENG', 'C', 'C', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1304', '8', '志高', 'CHIGO', 'C', 'C', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1305', '8', '勋风', 'XUNFENG', 'X', 'X', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1306', '8', '奥克斯', 'AUX', 'A', 'A', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1307', '8', '天骏小天使', 'TIJUMP', 'T', 'T', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1308', '8', '山湖', 'SVIII', 'S', 'S', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1309', '8', '永生', 'EOSIN', 'E', 'E', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1310', '8', '王牌', 'TCL', 'T', 'T', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1311', '8', '康佳', 'KONKA', 'K', 'K', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1312', '8', '永怡御风', 'YONYIYUFENG', 'Y', 'Y', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1313', '8', 'HOMELEADER', 'HOMELEADER', 'H', 'H', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1314', '8', '卡帝亚', 'KADEER', 'K', 'K', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1315', '8', '荣事达', 'ROYALSTAR', 'R', 'R', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1316', '8', '舒飞', 'SHUFEI', 'S', 'S', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1317', '8', '华宝', 'HUABAO', 'H', 'H', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1318', '8', '欣禾', 'XINHE', 'X', 'X', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1319', '8', '奥乐', 'AOLE', 'A', 'A', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1320', '8', '德努希', 'DENUXI', 'D', 'D', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1321', '8', '大松', 'TOSOT', 'T', 'T', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1322', '8', '宝尔玛', 'BAOERMA', 'B', 'B', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1323', '8', '奥德尔', 'ODOR', 'O', 'O', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1324', '8', '啊逗', 'ADOU', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1325', '8', '爱家乐', 'AKIRA', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1326', '8', '亚摩斯', 'AMOS', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1327', '8', '奥丽思', 'AONICE', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1328', '8', '艾尚东申', 'ASDSDQ', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1329', '8', '蝙蝠', 'BIANFU', 'B', 'B', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1330', '8', '博森', 'BOSEN', 'B', 'B', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1331', '8', '小白象', 'BRAND', 'B', 'B', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1332', '8', '骆驼', 'CAMEL', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1333', '8', '香山', 'CAMRY', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1334', '8', '创意生活', 'CHUANGYISHENGHUO', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1335', '8', '可仕', 'COSI', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1336', '8', '德努西', 'DENNSSI', 'D', 'D', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1337', '8', '钻石', 'DIAMOND', 'D', 'D', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1338', '8', '多丽', 'DUOLI', 'D', 'D', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1339', '8', '灿坤', 'EUPA', 'E', 'E', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1340', '8', '光一', 'GUANGYI', 'G', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1341', '8', '霍尼韦尔', 'HONEYWELL', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1342', '8', '汇成', 'HUICHENG', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1343', '8', '佳星', 'JASUN', 'J', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1344', '8', '金耐佳', 'JINNEIJIA', 'J', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1345', '8', '金世康', 'JINSHIKANG', 'J', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1346', '8', '金松', 'JINSONG', 'J', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1347', '8', '九和宏盛', 'JIUHEHONGSHENG', 'J', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1348', '8', '骏特', 'JUNTE', 'J', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1349', '8', '居优乐', 'JUYOULE', 'J', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1350', '8', '科龙', 'KELON', 'K', 'K', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1351', '8', 'LASKO', 'LASKO', 'L', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1352', '8', '亚立', 'LEXIS', 'L', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1353', '8', '小天鹅', 'LITTLE SWAN', 'L', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1354', '8', '龙的', 'LONGDE', 'L', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1355', '8', '龙昇', 'LONGSHENG', 'L', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1356', '8', '北欧', 'NATHOME', 'N', 'N', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1357', '8', '宁达', 'NINGDA', 'N', 'N', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1358', '8', '松下', 'PANASONSIC', 'P', 'P', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1359', '8', '趣玩', 'QUWAN', 'Q', 'Q', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1360', '8', '雅乐思', 'RILEOSIP', 'R', 'R', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1361', '8', '诺华', 'ROWARD', 'R', 'R', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1362', '8', '赛玛', 'SAIMA', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1363', '8', '桑普', 'SAMPUX', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1364', '8', '三星', 'SAMSUNG', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1365', '8', '尚美佳', 'SAMUCO', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1366', '8', '夏普', 'SHARP', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1367', '8', '视贝', 'SHIBEI', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1368', '8', '时丰', 'SHIFENG', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1369', '8', '双狐', 'SHUANGHU', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1370', '8', '水仙', 'SHUIXIAN', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1371', '8', '丝雨', 'SIYU', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1372', '8', '思露德', 'SLOOD', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1373', '8', '舒乐', 'SOLER', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1374', '8', '斯泰得乐', 'STADLER FORM', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1375', '8', '泰瑞', 'TERA', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1376', '8', '万宝', 'WANBAO', 'W', 'W', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1377', '8', '祥阳', 'XIANGYANG', 'X', 'X', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1378', '8', '小象', 'XIAOXIANG', 'X', 'X', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1379', '8', '小行星', 'XIAOXINGXING', 'X', 'X', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1380', '8', '西摩', 'XIMO', 'X', 'X', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1381', '8', '新的', 'XINDE', 'X', 'X', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1382', '8', '新飞', 'XINFEI', 'X', 'X', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1383', '8', '雄生', 'XIONGSHENG', 'X', 'X', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1384', '8', '迅动', 'XUNDON', 'X', 'X', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1385', '8', '兆城邑', 'ZEI', 'Z', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1386', '8', '智乐', 'ZHILE', 'Z', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1387', '8', '中联', 'ZOLEE', 'Z', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1388', '9', '苹果', 'APPLE', 'A', 'A', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1389', '9', '迪优美特', 'DIYOMATE', 'D', 'D', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1390', '9', '瑞珀', 'REALPLAY', 'R', 'R', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1391', '9', '海信', 'HISENSE', 'H', 'H', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1392', '9', '美如画', 'MYGICA', 'M', 'M', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1393', '9', '高清锐视', 'HDVISION', 'H', 'H', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1394', '9', '旭智', 'XUZHI', 'X', 'X', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1395', '9', '迈乐', 'MELE', 'M', 'M', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1396', '9', '开博尔', 'KAIBOER', 'K', 'K', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1397', '9', '海美迪', 'HIMEDIA', 'H', 'H', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1398', '9', '乐视TV', 'LETV', 'L', 'L', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1399', '9', '小米', 'MI', 'M', 'M', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1400', '9', '华为', 'HUAWEI', 'H', 'H', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1401', '9', '英菲克', 'INPHIC', 'I', 'I', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1402', '9', '智我', 'ZIVOO', 'Z', 'Z', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1403', '9', '天敏', '10MOONS', '#', '#', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1404', '9', '杰科', 'GIEC', 'G', 'G', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1405', '9', '忆典', 'IDER', 'I', 'I', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1406', '9', '创维', 'SKYWORTH', 'S', 'S', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1407', '9', '七V盒子', '7VBOX', '#', '#', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1408', '9', '海联达', 'AIGALE', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1409', '9', '爱家', 'AIKA', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1410', '9', '爱哇咔', 'AIWAKA', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1411', '9', '阿里TV', 'ALITV', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1412', '9', '夏新', 'AMOI', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1413', '9', 'AUDEX', 'AUDEX', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1414', '9', '百度', 'BAIDU', 'B', 'B', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1415', '9', '别致', 'BEIZHI', 'B', 'B', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1416', '9', '百视通', 'BESTV', 'B', 'B', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1417', '9', '比电', 'BIIDI', 'B', 'B', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1418', '9', '致家', 'BLIVE', 'B', 'B', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1419', '9', 'BLUEHOME', 'BLUEHOME', 'B', 'B', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1420', '9', '长虹', 'CHANGHONG', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1421', '9', '中国电信', 'CHINA TELECOM', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1422', '9', 'CLOUD ALIVE', 'CLOUD ALIVE', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1423', '9', '同洲', 'COSHIP', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1424', '9', '华视', 'CTS', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1425', '9', '戴夫迪', 'DAIFUDI', 'D', 'D', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1426', '9', '达客', 'DAKE', 'D', 'D', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1427', '9', '大亚', 'DARE', 'D', 'D', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1428', '9', 'DBSTAR', 'DBSTAR', 'D', 'D', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1429', '9', '数码在线', 'DNET', 'D', 'D', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1430', '9', '多通盒子', 'DUOLINK', 'D', 'D', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1431', '9', '忆捷', 'EAGET', 'E', 'E', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1432', '9', '亿播', 'EBOX', 'E', 'E', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1433', '9', '亿格瑞', 'EGREAT', 'E', 'E', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1434', '9', '电虎', 'ETIGER', 'E', 'E', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1435', '9', '飞看', 'FEIKAN', 'F', 'F', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1436', '9', '烽火', 'FIBERHOME', 'F', 'F', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1437', '9', '第五元素', 'FIFTH ELEMENTS', 'F', 'F', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1438', '9', '饭盒', 'FUNBOX', 'F', 'F', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1439', '9', '杰赛', 'GCI', 'G', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1440', '9', 'GOLDWEB', 'GOLDWEB', 'G', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1441', '9', '海尔', 'HAIER', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1442', '9', '盒天下', 'HETIANXIA', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1443', '9', '海吉雅', 'HIGIA', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1444', '9', '华录', 'HUALU', 'H', 'H', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1445', '9', '影能', 'INENG', 'I', 'I', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1446', '9', '莱蒙', 'LEMOON', 'L', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1447', '9', '礼嘉', 'LIJIA', 'L', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1448', '9', '灵动云莓', 'LINGDONGYUNMEI', 'L', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1449', '9', '麦迪', 'MAHDI', 'M', 'M', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1450', '9', '麦格', 'MAIGE', 'M', 'M', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1451', '9', '麦思美', 'MAXMADE', 'M', 'M', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1452', '9', '麦格菲斯', 'MEGAFEIS', 'M', 'M', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1453', '9', '美的', 'MIDEA', 'M', 'M', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1454', '9', '微力', 'MINIX', 'M', 'M', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1455', '9', '酷道', 'MIROAD', 'M', 'M', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1456', '9', '魔百盒', 'MOBILE BOX', 'M', 'M', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1457', '9', 'MORE-THING', 'MORE-THING', 'M', 'M', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1458', '9', '美赛图', 'MYSOTO', 'M', 'M', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1459', '9', '纽曼', 'NEWSMY', 'N', 'N', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1460', '9', '金正', 'NINTAUS', 'N', 'N', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1461', '9', '图美', 'NOONTEC', 'N', 'N', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1462', '9', '欧慕尼凯', 'OUNKAR', 'O', 'O', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1463', '9', '贝斯特', 'PACE', 'P', 'P', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1464', '9', '飞利浦', 'PHILIPS', 'P', 'P', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1465', '9', '巨影', 'PMAX', 'P', 'P', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1466', '9', 'PPLIVE/华数', 'PPBOX', 'P', 'P', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1467', '9', 'PPLIVE', 'PPTV', 'P', 'P', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1468', '9', '快播', 'QVOD', 'Q', 'Q', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1469', '9', '雷柏', 'RAPOO', 'R', 'R', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1470', '9', '精伦', 'ROUTON', 'R', 'R', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1471', '9', '易播互动', 'SHAREME', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1472', '9', '夏普', 'SHARP', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1473', '9', '世纪格雷', 'SHIJIGELEI', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1474', '9', '新科', 'SHINCO', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1475', '9', '斯波兰', 'SIBOLAN', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1476', '9', '盛熙', 'SINCERE-HOME', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1477', '9', '小技', 'SMALLART', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1478', '9', '索爱', 'SOAIY', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1479', '9', '声物', 'SOOALL', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1480', '9', '果珈', 'STAR5', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1481', '9', '小霸王', 'SUBOR', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1482', '9', '托瓦', 'TAMO', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1483', '9', '糖豆', 'TANGDOU', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1484', '9', '网视', 'TARGETV', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1485', '9', '台硕', 'TASU', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1486', '9', '通播客', 'T-BOKI', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1487', '9', '铁威马', 'TERRAMASTER', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1488', '9', '代代星', 'TIMETOP', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1489', '9', '天猫', 'TMALL', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1490', '9', '普联', 'TP-LINK', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1491', '9', '顺云飞', 'TWONKY', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1492', '9', 'UT斯达康', 'UTSTARCOM', 'U', 'U', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1493', '9', '华广', 'VAKUAN', 'V', 'V', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1494', '9', 'VIAPLAY', 'VIAPLAY', 'V', 'V', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1495', '9', '网讯', 'VTION', 'V', 'V', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1496', '9', '华数', 'WASU', 'W', 'W', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1497', '9', '我播', 'WOBO ', 'W', 'W', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1498', '9', '现代演绎', 'XIANDAIYANYI', 'X', 'X', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1499', '9', '小菜', 'XIAOCAI', 'X', 'X', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1500', '9', '小葱', 'XIAOCHONG', 'X', 'X', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1501', '9', '小美盒子', 'XIAOMEIHEZI', 'X', 'X', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1502', '9', '新浪米多', 'XINLANGMIDUO', 'X', 'X', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1503', '9', '优冠', 'YOUGUAN', 'Y', 'Y', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1504', '9', '云播', 'YUNBO ', 'Y', 'Y', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1505', '9', '云猴', 'YUNHOU', 'Y', 'Y', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1506', '9', '誉宜', 'YUYI', 'Y', 'Y', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1507', '9', '主场', 'ZHUCHANG', 'Z', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1508', '9', '中兴通讯', 'ZTE', 'Z', 'Z', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1509', '10', '雅马哈', 'YAMAHA', 'Y', 'Y', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1510', '10', '飞利浦', 'PHILIPS', 'P', 'P', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1511', '10', '惠威', 'HIVI', 'H', 'H', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1512', '10', '索尼', 'SONY', 'S', 'S', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1513', '10', '先锋', 'PIONEER', 'P', 'P', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1514', '10', '三星', 'SAMSUNG', 'S', 'S', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1515', '10', '天逸', 'WINNER', 'W', 'W', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1516', '10', 'LG', 'LG', 'L', 'L', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1517', '10', '天龙', 'DENON', 'D', 'D', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1518', '10', 'JBL', 'JBL', 'J', 'J', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1519', '10', '尊宝', 'JAMO', 'J', 'J', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1520', '10', 'BT-AUDIO', 'BT-AUDIO', 'B', 'B', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1521', '10', '安桥', 'ONKYO', 'O', 'O', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1522', '10', '松下', 'PANASONSIC', 'P', 'P', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1523', '10', '星工场', 'XINGGONGCHANG', 'X', 'X', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1524', '10', '奋达', 'F&D', 'F', 'F', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1525', '10', '美声', 'MISSION', 'M', 'M', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1526', '10', '意力', 'ELAC', 'E', 'E', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1527', '10', '泛韵', 'PANSTUDIOS', 'P', 'P', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1528', '10', '金榜', 'JINBANG', 'J', 'J', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1529', '10', 'TARGETV', 'TARGETV', 'T', 'T', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1530', '10', 'KEF', 'KEF', 'K', 'K', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1531', '10', 'BANG&OLUFSEN', 'B&O', 'B', 'B', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1532', '10', '盘古', 'PANGOO', 'P', 'P', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1533', '10', '哈曼卡顿', 'HARMAN KARDON', 'H', 'H', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1534', '10', '马兰士', 'MARANTZ', 'M', 'M', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1535', '10', '新科', 'SHINCO', 'S', 'S', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1536', '10', '骑士', 'QAXELECTRIC', 'Q', 'Q', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1537', '10', '波士顿', 'BOSTON', 'B', 'B', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1538', '10', '豪韵', 'HYPERSOUND', 'H', 'H', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1539', '10', '丽声', 'CAV', 'C', 'C', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1540', '10', '英瀚', 'YOHONG', 'Y', 'Y', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1541', '10', '狮乐', 'SHILE', 'S', 'S', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1542', '10', '哈曼', 'HNM', 'H', 'H', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1543', '10', '雷客', 'SKYROCKER', 'S', 'S', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1544', '10', '沃夫德尔', 'WHARFEDALE', 'W', 'W', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1545', '10', '奇声', 'QISHENG', 'Q', 'Q', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1546', '10', '奔腾', 'BNTN', 'B', 'B', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1547', '10', '山水', 'SANSUI', 'S', 'S', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1548', '10', '绅士宝', 'DMSAMSBO', 'D', 'D', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1549', '10', '绅宝', 'SHENBAO', 'S', 'S', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1550', '10', '托瓦', 'TAMO', 'T', 'T', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1551', '10', '日内瓦之声', 'GENEVA', 'G', 'G', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1552', '10', '音王', 'INANDON', 'I', 'I', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1553', '10', '金正', 'NINTAUS', 'N', 'N', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1554', '10', '普乐之声', 'POLK AUDIO', 'P', 'P', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1555', '10', '爱浪', 'AVLIGHT', 'A', 'A', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1556', '10', '派贝斯', 'PBX', 'P', 'P', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1557', '10', '杰士', 'KLIPSCH', 'K', 'K', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1558', '10', '索威', 'SV', 'S', 'S', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1559', '10', '科马', 'KEMA', 'K', 'K', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1560', '10', '卓丽', 'CHARIO', 'C', 'C', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1561', '10', '天朗', 'TANNOY', 'T', 'T', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1562', '10', '盛熙', 'SINCERE-HOME', 'S', 'S', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1563', '10', '铂锐', 'BORAY', 'B', 'B', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1564', '10', '拜亚动力', 'BAIYADONGLIC', 'B', 'B', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1565', '10', '现代', 'HYUNDAI', 'H', 'H', '1');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1566', '10', 'ADA', 'ADA', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1567', '10', '爱琴', 'ADCOM', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1568', '10', 'AMC', 'AMC', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1569', '10', 'ANNO DOMINI', 'ANNO DOMINI', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1570', '10', '圣歌', 'ANTHEM', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1571', '10', '安田', 'ANTIY', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1572', '10', '阿拉贡', 'ARAGON', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1573', '10', '亚特兰大', 'ATLANTIC TECHNOLOGY', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1574', '10', 'AUDIO ACCESS', 'AUDIO ACCESS', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1575', '10', 'AUDIOSOURCE', 'AUDIOSOURCE', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1576', '10', '皇冠', 'AVANCE', 'A', 'A', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1577', '10', 'B&K', 'B&K', 'B', 'B', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1578', '10', 'BALENALD', 'BALENALD', 'B', 'B', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1579', '10', '步步高', 'BBK', 'B', 'B', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1580', '10', 'BOGEN', 'BOGEN', 'B', 'B', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1581', '10', '卡维', 'CARVER', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1582', '10', '长虹', 'CHANGHONG', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1583', '10', 'CHIRO', 'CHIRO', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1584', '10', '架势', 'CLASSE', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1585', '10', '汇点', 'COUNTERPOINT', 'C', 'C', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1586', '10', '科沃斯', 'ECOVACS', 'E', 'E', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1587', '10', '漫步者', 'EDIFIER', 'E', 'E', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1588', '10', 'ENERGY', 'ENERGY', 'E', 'E', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1589', '10', '伊威特', 'E-WITI', 'E', 'E', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1590', '10', 'FAROUDJA', 'FAROUDJA', 'F', 'F', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1591', '10', 'FOSTEX', 'FOSTEX', 'F', 'F', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1592', '10', '歌杰', 'GOOKEE', 'G', 'G', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1593', '10', 'IHO', 'IHO', 'I', 'I', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1594', '10', '燕飞利仕', 'INFINITY', 'I', 'I', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1595', '10', 'INTEGRA', 'INTEGRA', 'I', 'I', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1596', '10', '胜利', 'JVC', 'J', 'J', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1597', '10', 'KDS', 'KDS', 'K', 'K', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1598', '10', '健伍', 'KENWOOD', 'K', 'K', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1599', '10', '健亚', 'KINERGETICS RESEARCH', 'K', 'K', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1600', '10', 'K-LEM', 'K-LEM', 'K', 'K', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1601', '10', '奇力', 'KRELL', 'K', 'K', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1602', '10', 'KUSTOM', 'KUSTOM', 'K', 'K', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1603', '10', '莱斯康', 'LEXICON', 'L', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1604', '10', '莲', 'LINN', 'L', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1605', '10', '罗技', 'LOGITECH', 'L', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1606', '10', '力仕', 'LUXMAN', 'L', 'L', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1607', '10', '万利达', 'MALATA', 'M', 'M', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1608', '10', '马克莱文森', 'MARK LEVINSON', 'M', 'M', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1609', '10', '麦景图', 'MCINTOSH', 'M', 'M', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1610', '10', '英国之宝', 'MERIDIAN', 'M', 'M', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1611', '10', 'NAD', 'NAD', 'N', 'N', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1612', '10', '中道', 'NAKAMICHI', 'N', 'N', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1613', '10', '诺普声', 'NOBSOUND', 'N', 'N', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1614', '10', '宝丽音', 'PARASOUND', 'P', 'P', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1615', '10', '翩美', 'PRIMARE', 'P', 'P', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1616', '10', '普斯', 'PROCEED', 'P', 'P', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1617', '10', '乾龙盛', 'QLS', 'Q', 'Q', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1618', '10', 'REVOX', 'REVOX', 'R', 'R', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1619', '10', '路遥', 'ROTEL', 'R', 'R', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1620', '10', '悦辰', 'RSR', 'R', 'R', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1621', '10', 'RUSSOUND', 'RUSSOUND', 'R', 'R', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1622', '10', '先科', 'SAST', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1623', '10', 'SEARS', 'SEARS', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1624', '10', '狮龙', 'SHERWOOD', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1625', '10', 'SIMA', 'SIMA', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1626', '10', '骄阳', 'SUNFIRE', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1627', '10', '斯特朗', 'SUTHERLAND', 'S', 'S', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1628', '10', '麦拿伦', 'TAG MCLAREN', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1629', '10', '得胜', 'TAKSTAR', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1630', '10', '蒂雅克', 'TEAC', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1631', '10', '神州天乐', 'TEINURO', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1632', '10', '大鹰', 'THETA DIGTAL', 'T', 'T', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1633', '10', '威发', 'VIFA', 'V', 'V', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1634', '10', 'VTREK', 'VTREK', 'V', 'V', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1635', '10', '威格', 'WEIGE', 'W', 'W', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1636', '10', '雅桥', 'YACARE', 'Y', 'Y', '0');
INSERT INTO `irbranddictionary` (`id`, `IRType`, `BrandCN`, `BrandEN`, `NameIndex`, `UnitCode`, `bUse`) VALUES ('1637', '10', '珠江', 'ZHUJIANG', 'Z', 'Z', '0');

/*保存未解码FF的IR数据 */
CREATE TABLE `irmatch` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `houseIEEE` varchar(50) DEFAULT NULL,
  `ieee` varchar(50) DEFAULT NULL,
  `ep` varchar(50) DEFAULT NULL,
  `hadaemonIRIndex` int(20) DEFAULT NULL,
  `data` varchar(2000) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*irdata decodeType增加索引*/
create index irdata_dt_index on irdata(decodeType);

/*匹配IRData的存储过程*/
DROP PROCEDURE IF EXISTS `IRDataSumBrandStyle`;

CREATE PROCEDURE `IRDataSumBrandStyle`(IN IRType Int,IN IRData varchar(2000))
Label_At_Start:
BEGIN  
	/*Routine body goes here...*/
  DECLARE aUniqueID BIGINT;
  DECLARE aCount, I, J, aPulseWidth Integer;
  DECLARE aSql VARCHAR(4000);
  DECLARE TempStr, TempIR VARCHAR(2);
  

  set aUniqueID=Year(Now())*100000000000000+
                Month(NOW())*1000000000000+
                Day(NOW())*10000000000+
                HOUR(Now())*100000000+
                MINUTE(Now())*1000000+
                SECOND(Now())*10000+
                Ceil(RAND()*10000);
  
  set aCount=Ceil(Length(irdata)/2);
  
  if aCount <=1  THEN 
    LEAVE Label_At_Start;
  end if;
  
  set TempStr=SUBSTR(irdata FROM 1 FOR 2);
/*IF TempStr='FF' THEN  
  Set I=1;
  Set J=1;
  WHILE I<aCount DO
    set aPulseWidth=CONV(SUBSTR(irdata FROM I*2+1 FOR 2),16,10);
    IF aPulseWidth<255 THEN
      insert INTO irtempsum (IrType,Uniqueid,Seq,pulsewidth) VALUES(IRType,aUniqueID,J,apulseWidth);
      Set I=I+1;
    ELSE
      set aPulseWidth=CONV(SUBSTR(irdata FROM I*2+3 FOR 4),16,10);
      insert INTO irtempsum (IrType,Uniqueid,Seq,pulsewidth) VALUES(IRType,aUniqueID,J,apulseWidth);
      Set I=I+3;
    END IF; 
    Set J=J+1;   
  END WHILE;
ELSE
  Set I=1;
  WHILE I<aCount DO
    set aPulseWidth=CONV(SUBSTR(irdata FROM I*2+1 FOR 2),16,10);
    insert INTO irtempsum (IrType,Uniqueid,Seq,pulsewidth) VALUES(IRType,aUniqueID,I,apulseWidth);
    Set I=I+1;
  END WHILE;
END IF;*/

IF TempStr='FF' THEN  
  Set I=1;
  Set J=1;
  set @aSql='insert INTO irtempsum (IrType,Uniqueid,Seq,pulsewidth) VALUES(';
  WHILE I<aCount DO
    set aPulseWidth=CONV(SUBSTR(irdata FROM I*2+1 FOR 2),16,10);
    IF aPulseWidth<255 THEN
      /*insert INTO irtempsum (IrType,Uniqueid,Seq,pulsewidth) VALUES(IRType,aUniqueID,J,apulseWidth);*/      
      Set I=I+1;
    ELSE
      set aPulseWidth=CONV(SUBSTR(irdata FROM I*2+3 FOR 4),16,10);
      /*insert INTO irtempsum (IrType,Uniqueid,Seq,pulsewidth) VALUES(IRType,aUniqueID,J,apulseWidth);*/
      Set I=I+3;
    END IF; 
    if J=1 THEN
      set @aSql=CONCAT(@aSql,IRType,',',aUniqueID,',',J,',',apulseWidth,')');
    ELSE
      set @aSql=CONCAT(@aSql,',(',IRType,',',aUniqueID,',',J,',',apulseWidth,')');
    end if;
    Set J=J+1;   
  END WHILE;
ELSE
  Set I=1;
  set @aSql='insert INTO irtempsum (IrType,Uniqueid,Seq,pulsewidth) VALUES(';

  WHILE I<aCount DO
    set aPulseWidth=CONV(SUBSTR(irdata FROM I*2+1 FOR 2),16,10);
    /*insert INTO irtempsum (IrType,Uniqueid,Seq,pulsewidth) VALUES(IRType,aUniqueID,I,apulseWidth);*/
    if I=1 THEN
      set @aSql=CONCAT(@aSql,IRType,',',aUniqueID,',',I,',',apulseWidth,')');
    ELSE
      set @aSql=CONCAT(@aSql,',(',IRType,',',aUniqueID,',',I,',',apulseWidth,')');
    end if;
    Set I=I+1;    
  END WHILE;
END IF;
/*SELECT @aSql;*/
PREPARE stmtselect FROM @aSql;
EXECUTE stmtselect;  
DEALLOCATE PREPARE stmtselect;

IF TempStr='FF' THEN
  Select IRSumResult.*,d.data
   from 
   (Select b1.id,c.brandName,b1.modelName,b1.controllType,b1.checked,c.enbrandName,
    CEIL((Max(MatchCount)/(J-1))*100) as matchPercent
  from 
  (
  select y.dataid,Count(*) as MatchCount
    from irtempsum z  inner join irindexdata_ff y on z.seq = y.seq  
                      inner join irdata a on a.id=y.dataid
                      inner join irmodel b on a.modelId=b.id
  where z.UniqueID=aUniqueID  
    and b.controllType=IrType
    and z.pulseWidth BETWEEN y.pulseWidthMin and y.pulseWidthMax
  group by y.dataId
  ) IRSum inner join irdata a1 on a1.id=IRSum.dataid
          inner join irmodel b1 on a1.modelId=b1.id
          INNER JOIN irbrand c on b1.brandId=c.id
  GROUP BY b1.id
  order by Max(MatchCount) DESC
  LIMIT 20) IRSumResult left join IrData d on IRSumResult.Id=d.modelId
  where d.CommdSequence=1 and IRSumResult.matchPercent>=30;
ELSE
  set @aSql=CONCAT(
  'Select IRSumResult.*,d.data
   from 
   (Select b1.id,c.brandName,b1.modelName,b1.controllType,b1.checked,c.enbrandName,
    CEIL((Max(MatchCount)/(',I,'-1))*100) as matchPercent
  from 
  (
  select y.dataid,Count(*) as MatchCount
    from irtempsum z  inner join irindexdata_',Tempstr,' y on z.seq = y.seq  and z.pulseWidth=y.bandwidth
                      inner join irdata a on a.id=y.dataid
                      inner join irmodel b on a.modelId=b.id
  where z.UniqueID=',aUniqueID,'
    and b.controllType=',IrType,'
  group by y.dataId
  ) IRSum inner join irdata a1 on a1.id=IRSum.dataid
          inner join irmodel b1 on a1.modelId=b1.id
          INNER JOIN irbrand c on b1.brandId=c.id
  GROUP BY b1.id
  order by Max(MatchCount) DESC
  LIMIT 20) IRSumResult left join IrData d on IRSumResult.Id=d.modelId
  where d.CommdSequence=1 and IRSumResult.matchPercent>=30'
  );
  
  PREPARE stmtselect FROM @aSql;
  EXECUTE stmtselect;  
  DEALLOCATE PREPARE stmtselect;
end if;
END Label_At_Start;

/*临时存储表,替换原来的irtemp*/
CREATE TABLE `irtempsum` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`IrType`  int(11) NULL DEFAULT NULL ,
`UniqueID`  bigint(11) NULL DEFAULT NULL ,
`seq`  int(11) NULL DEFAULT NULL ,
`pulseWidth`  int(11) NULL DEFAULT NULL ,
`data`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`createTime`  datetime NULL DEFAULT CURRENT_TIMESTAMP ,
PRIMARY KEY (`id`),
INDEX `idx_seq` (`seq`) USING BTREE ,
INDEX `idx_pulsewidth` (`pulseWidth`) USING BTREE ,
INDEX `idx_IRTempSum_IrType` (`IrType`) USING BTREE ,
INDEX `idx_IRTempSum_UniqueID` (`UniqueID`) USING BTREE ,
INDEX `idx_IRTempSum_Four` (`IrType`, `UniqueID`, `seq`, `pulseWidth`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1;

/*建立定时删除昨天之前（包含昨天）的irtempsum表的JOB*/
drop event if exists event_DeleteOldIrTempData;
CREATE EVENT event_DeleteOldIrTempData ON SCHEDULE EVERY 1 DAY STARTS '2014-09-19 00:10:00'
ON COMPLETION NOT PRESERVE ENABLE 
DO delete from irtempsum where DATEDIFF(Now(),CreateTime)>0;


/*node表新增createtime与lasttime字段*/
ALTER TABLE `node`
ADD COLUMN `createtime`  datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' AFTER `nodeName`,
ADD COLUMN `lasttime`  datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' AFTER `createtime`;

/*存储过程更新*/
DROP PROCEDURE IF EXISTS `EnergyCalculateByYear`;

CREATE PROCEDURE `EnergyCalculateByYear`(IN HouseIEEE varchar(20),IN IEEE varchar(20),IN EP varchar(20),IN OpTime datetime,IN EnergyValue Double)
Label_At_Start: 
BEGIN 
	#Routine body goes here...
  DECLARE LastEnergy, EnergySum, EnergySumTmp, EnergyTmpHour,EnergyTmpTime, EnergyTmpField, 
          BeginEnergy, EndEnergy, EnergyPrice, EnergyPrice1 DOUBLE;
  DECLARE LastOpTime, CurrentTime, DstBeginTIme, DstEndTIme, 
          BeginTimeHour, EndTimeHour, BeginTimeTime, EndTimeTime, BeginTimeTmp1, EndTimeTmp1 datetime;
  DECLARE aCount, I, J, K, Year1, Month1, Day1, Hour1, Minute1,Sec1 INTEGER;
  DECLARE Year2, Month2, Day2, Hour2, Minute2, secDiff,Sec2, IsDst, StartFieldTmp, EndFieldTmp, 
          TmpDone, iContinue, iPriceType,iField, iTime, iDst, EnergyCount, iTable INTEGER;
  DECLARE sSql VARCHAR(4000);
  DECLARE Cur_Time CURSOR for Select t.beginTime,t.EndTime,t.EnergyTimeID from energytime t where t.HouseIEEE=HouseIEEE order by T.EnergyTimeID;    
  DECLARE Cur_Field CURSOR for Select t.StartField,t.EndField,t.EnergyFieldID from energyfield t where t.HouseIEEE=HouseIEEE order by T.EnergyFieldID;
  DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET TmpDone=1;

  SELECT Count(*) into aCount FROM deviceattribute t 
    WHERE t.HouseIEEE=HouseIEEE and t.Device_IEEE=IEEE AND t.Device_EP=EP and t.Cluster_ID='0702' and t.Attribute_ID='E003';
  
  if aCount <> 1  THEN 
    LEAVE Label_At_Start;
  end if;

  SELECT t.Value,t.lasttime into LastEnergy,LastOpTime FROM deviceattribute t 
    WHERE t.HouseIEEE=HouseIEEE and t.Device_IEEE=IEEE AND t.Device_EP=EP and t.Cluster_ID='0702' and t.Attribute_ID='E003';
  SELECT t.PriceType,t.DstFlag,t.DstbeginTIme,t.DstEndTime into iPriceType,iDst,DstbeginTime,dstEndTime from Energy t
    where t.HouseIEEE=HouseIEEE;
  
  Set CurrentTime=OpTime;
  if ((timediff(LastOpTime,CurrentTime)>=0) and (DATEDIFF(CurrentTime,LastOpTime)<=0)) or (LastEnergy>EnergyValue) THEN    
    LEAVE Label_At_Start;
  END if;
  Set secDiff=Hour(timediff(CurrentTime,LastOpTime));
  if (MINUTE(timediff(CurrentTime,LastOpTime))>0) or (SECOND(timediff(CurrentTime,LastOpTime))>0) THEN
    Set secDiff=secDiff+1;
  end if;
  if secDiff=1 THEN
    if ((LastEnergy-EnergyValue)/secDiff>10) THEN
      LEAVE Label_At_Start;
    end IF;
  end IF;
  Set secDiff=Hour(timediff(CurrentTime,LastOpTime))*3600+MINUTE(timediff(CurrentTime,LastOpTime))*60+SECOND(timediff(CurrentTime,LastOpTime));
  
  Set Year1=YEAR(LastOpTime),Month1=Month(LastOpTime), Day1=DAY(LastOpTime), 
      Hour1=Hour(LastOpTime), Minute1=Minute(LastOpTime),Sec1=Second(LastOpTime);
  Set Year2=YEAR(CurrentTime),Month2=Month(CurrentTime), Day2=DAY(CurrentTime), 
      Hour2=Hour(CurrentTime), Minute2=Minute(CurrentTime), Sec2=Second(CurrentTIme);
  Set I=0, aCount=DATEDIFF(CurrentTime,LastOpTime)*24+Hour2-Hour1;
  Set BeginTimeHour=LastOpTime, EndTimeHour=LastOpTime; 
  Set secDiff=secDiff+aCount*3600; 

  WHILE I<=aCount DO    
    IF I=0 THEN
      if i=aCount then
        Set EnergyTmpHour=(EnergyValue-LastEnergy)*((Minute2-Minute1)*60+Sec2-Sec1)/SecDiff;
        select date_add(EndTimeHour, interval Minute2-Minute1 MINUTE) into EndTimeHour;
        select date_add(EndTimeHour, interval Sec2-Sec1 SECOND) into EndTimeHour;      
      ELSE
        Set EnergyTmpHour=(EnergyValue-LastEnergy)*((60-Minute1)*60-Sec1)/SecDiff;
        select date_add(EndTimeHour, interval 60-Minute1-1 MINUTE) into EndTimeHour;
        select date_add(EndTimeHour, interval 60-Sec1-1 SECOND) into EndTimeHour;
      end if;
    elseif I=aCount THEN
      Set EnergyTmpHour=(EnergyValue-LastEnergy)*(Minute2*60+Sec2)/SecDiff;      
      set BeginTimeHour=date_add(EndTimeHour, interval 1 SECOND); 
      set EndTimeHour=CurrentTime;
    ELSE      
      Set EnergyTmpHour=(EnergyValue-LastEnergy)*3600/SecDiff;
      set BeginTimeHour=date_add(EndTimeHour, interval 1 SECOND);
      select date_add(EndTimeHour, interval 1 HOUR) into EndTimeHour; 
    End if;
    
    /*SELECT IFNULL(Sum(T.EnergyValue),0) into EnergySum from EnergyHistory t 
      WHERE t.HouseIEEE=HouseIEEE and t.IEEE=IEEE AND t.EP=EP
        and Year(t.opdatetime)=Year(BeginTimeHour) and Month(t.opdatetime)=Month(BeginTimeHour);*/
    
    Select Count(*) into iTable from information_schema.TABLES t where  t.Table_Name=CONCAT('EnergyHistory_',Year(BeginTimeHour));
    if iTable=0 THEN
      Set @sSql=CONCAT('create  TABLE EnergyHistory_',Year(BeginTimeHour),' like energyhistory');
      PREPARE stmtselect FROM @sSql;
      EXECUTE stmtselect;
      DEALLOCATE PREPARE stmtselect;
    end if;
    Set @HouseIEEE=HouseIEEE;
    Set @IEEE=IEEE;
    Set @EP=EP;
    Set @BeginTimeHour=BeginTimeHour;
    set @sSql=CONCAT('SELECT IFNULL(Sum(T.EnergyValue),0) into @EnergySum from ','EnergyHistory_',Year(BeginTimeHour),' t 
      WHERE t.HouseIEEE=? and t.IEEE=? AND t.EP=?
        and Year(t.opdatetime)=Year(?) and Month(t.opdatetime)=Month(?);');
    PREPARE stmtselect FROM @sSql;
    EXECUTE stmtselect USING @HouseIEEE,@IEEE,@EP,@BeginTimeHour,@BeginTimeHour;
    DEALLOCATE PREPARE stmtselect;
    set EnergySum=@EnergySum;
    Set EnergySumTmp=EnergySum;
    Set TmpDone=0;
    
    if iPriceType>1 then
      OPEN Cur_Time;
      FETCH Cur_Time into BeginTimeTime,EndTimeTime,iTime;
      while TmpDone<>1 DO
        Set iContinue=0;      
        if Time(BeginTimeHour)>=Time(BeginTimeTime) and TIme(EndTimeHour)<=Time(EndTimeTime) THEN
          set BeginTimeTmp1=BeginTimeHour;
          set EndTimeTmp1=EndTimeHour;
        ELSEIF Time(BeginTimeHour)>=Time(BeginTimeTime) and Time(EndTimeHour)>=Time(EndTimeTime) and Time(BeginTimeHour)<Time(EndTimeTime) THEN
          Set BeginTimeTmp1=BeginTimeHour;
          Set EndTimeTmp1=DATE_ADD(Date(EndTimeHour),INTERVAL Time(EndTimeTime) HOUR_SECOND);
        ELSEIF Time(BeginTimeHour)<Time(BeginTimeTime) and Time(EndTimeHour)<Time(EndTimeTime) and Time(EndTimeHour)>Time(BeginTimeTime) THEN
          Set BeginTimeTmp1=DATE_ADD(Date(EndTimeHour),INTERVAL Time(BeginTimeTime) HOUR_SECOND);
          Set EndTimeTmp1=DATE_ADD(Date(EndTimeHour),INTERVAL Time(EndTimeTime) HOUR_SECOND);
        ELSEIF Time(BeginTimeHour)<Time(BeginTimeTime) and Time(EndTimeHour)>Time(EndTimeTime) THEN
          Set BeginTimeTmp1=DATE_ADD(Date(EndTimeHour),INTERVAL Time(BeginTimeTime) HOUR_SECOND);
          Set EndTimeTmp1=DATE_ADD(Date(EndTimeHour),INTERVAL Time(EndTimeTime) HOUR_SECOND);
        ELSE
          Set iContinue=1;          
        end if;  
        if Time(BeginTimeTime)>Time(EndTimeTime) THEN
          set iContinue=1;
        end if;
        
        if iContinue=0 THEN
          set EnergyTmpTime=EnergyTmpHour*((Minute(EndTimeTmp1)-Minute(BeginTimeTmp1))*60+Second(EndTimeTmp1)-Second(BeginTimeTmp1))/((Minute(EndTimeHour)-Minute(BeginTimeHour))*60+Second(EndTimeHour)-Second(BeginTimeHour));
          
          if iPriceType=3 THEN
            OPEN Cur_Field;
            FETCH Cur_Field into BeginEnergy, EndEnergy,iField;
            WHILE tmpDone<>1 DO
              Set iContinue=0;
              Set EnergyTmpField=0;
              if EnergySumTmp>=BeginEnergy and EnergySumTmp+EnergyTmpTime<=EndEnergy THEN
                Set EnergyTmpField=EnergyTmpTime;
              ELSEIF EnergySumTmp>=BeginEnergy and EnergySumTmp<EndEnergy and EnergySumTmp+EnergyTmpTime>EndEnergy THEN
                Set EnergyTmpField=EndEnergy-EnergySumTmp;
              ELSEIF EnergySumTmp<BeginEnergy and EnergySumTmp+EnergyTmpTime<=EndEnergy and EnergySumTmp+EnergyTmpTIme>BeginEnergy THEN
                Set EnergyTmpField=EnergySumTmp+EnergyTmpTime-BeginEnergy;
              ELSEIF EnergySumTmp<BeginEnergy and EnergySumTmp+EnergyTmpTime>EndEnergy THEN
                Set EnergyTmpField=EndEnergy-BeginEnergy;
              ELSE
                Set iContinue=1;
              end if;
              
              if iContinue=0 then
                SELECT case  when iDst=1 and BeginTimeTmp1>=DstBeginTime and BeginTimeTmp1<=DstEndTime 
                                  then t.PriceDst 
                             else t.price 
                       end into EnergyPrice
                  from priceparam t where t.houseIEEE=houseIEEE and t.energyTimeId=iTime and t.energyFieldId=iField;
                
                Set @BeginTimeTmp1=BeginTimeTmp1;
                Set @EnergyTmpField=EnergyTmpField;
                Set @EnergyPrice1=EnergyTmpField*EnergyPrice;
                select @BeginTimeTmp1,@EnergyTmpField,@EnergyPrice1,iField,iTime;
                Set @sSql=CONCAT('SELECT Count(*) into @EnergyCount FROM ',' EnergyHistory_',Year(BeginTimeHour),' t 
                  where t.houseIEEE=? and t.IEEE=? and t.EP=?
                    and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');
                PREPARE stmtselect FROM @sSql;
                EXECUTE stmtselect using @HouseIEEE,@IEEE,@EP,@BeginTimeTmp1,@BeginTimeTmp1;
                DEALLOCATE PREPARE stmtselect;
                Set EnergyCount=@EnergyCount;
                if EnergyCount=0 then 
                  Set @sSql=CONCAT('insert into ',' EnergyHistory_',Year(BeginTimeHour),
                                   '(HouseIEEE,IEEE,EP,opDateTime,EnergyValue,EnergyPrice) values
                                           (?,?,?,?,?,?)');
                else 
                  Set @sSql=CONCAT('update ',' EnergyHistory_',Year(BeginTimeHour),
                                   ' t set t.energyValue=IfNull(t.energyValue,0)+?,
                                             t.energyPrice=IfNull(t.energyPrice,0)+?
                  where t.houseIEEE=? and t.IEEE=? and t.EP=?
                    and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');                  
                end if;
                
                PREPARE stmtselect FROM @sSql;
                if EnergyCount=0 then
                  EXECUTE stmtselect using @HouseIEEE,@IEEE,@EP,@BeginTimeTmp1,@EnergyTmpField,@EnergyPrice1;
                else
                  EXECUTE stmtselect using @EnergyTmpField,@EnergyPrice1,@HouseIEEE,@IEEE,@EP,@BeginTimeTmp1,@BeginTimeTmp1;
                end if;
                DEALLOCATE PREPARE stmtselect;
                /*SELECT Count(*) into EnergyCount FROM energyhistory t 
                  where t.houseIEEE=houseIEEE and t.IEEE=IEEE and t.EP=EP
                    and Date(t.opdatetime)=Date(BeginTimeTmp1) and Hour(t.opdatetime)=Hour(BeginTimeTmp1);
                if EnergyCount=0 then 
                  insert into energyhistory(HouseIEEE,IEEE,EP,opDateTime,EnergyValue,EnergyPrice) values
                                           (HouseIEEE,IEEE,EP,BeginTimeTmp1,EnergyTmpField,EnergyTmpField*EnergyPrice);
                else 
                  update energyhistory t set t.energyValue=t.energyValue+EnergyTmpField,
                                             t.energyPrice=t.energyPrice+EnergyTmpField*EnergyPrice
                  where t.houseIEEE=houseIEEE and t.IEEE=IEEE and t.EP=EP
                    and Date(t.opdatetime)=Date(BeginTimeTmp1) and Hour(t.opdatetime)=Hour(BeginTimeTmp1);
                end if;*/
              end if;
              FETCH Cur_Field into BeginEnergy, EndEnergy,iField;
            end WHILE;
            CLOSE Cur_Field;
            Set tmpDone=0;
            if EnergySumTmp+EnergyTmpTime>EndEnergy THEN
              /*Set EnergyTmpField=EnergySumTmp+EnergyTmpTime-EndEnergy;*/
              Set EnergyTmpField=EnergyTmpTime;

              Set @HouseIEEE=HouseIEEE;
              Set @IEEE=IEEE;
              Set @EP=EP;
              Set @BeginTimeTmp1=BeginTimeTmp1;
              Set @EnergyTmpField=EnergyTmpField;
              Set @EnergyPrice1=EnergyTmpField*EnergyPrice;

              Set @sSql=CONCAT('SELECT Count(*) into @EnergyCount FROM ',' EnergyHistory_',Year(BeginTimeHour),' t 
                  where t.houseIEEE=? and t.IEEE=? and t.EP=?
                    and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');
              PREPARE stmtselect FROM @sSql;
              EXECUTE stmtselect using @HouseIEEE,@IEEE,@EP,@BeginTimeTmp1,@BeginTimeTmp1;
              DEALLOCATE PREPARE stmtselect;
              Set EnergyCount=@EnergyCount;
              if EnergyCount=0 then 
                Set @sSql=CONCAT('insert into ',' EnergyHistory_',Year(BeginTimeHour),
                                 '(HouseIEEE,IEEE,EP,opDateTime,EnergyValue,EnergyPrice) values
                                         (?,?,?,?,?,?)');
              else 
                Set @sSql=CONCAT('update ',' EnergyHistory_',Year(BeginTimeHour),
                                 ' t set t.energyValue=IfNull(t.energyValue,0)+?,
                                         t.energyPrice=IfNull(t.energyPrice,0)+?
                where t.houseIEEE=? and t.IEEE=? and t.EP=?
                  and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');                  
              end if;
              
              PREPARE stmtselect FROM @sSql;
              if EnergyCount=0 then
                EXECUTE stmtselect using @HouseIEEE,@IEEE,@EP,@BeginTimeTmp1,@EnergyTmpField,@EnergyPrice1;
              else
                EXECUTE stmtselect using @EnergyTmpField,@EnergyPrice1,@HouseIEEE,@IEEE,@EP,@BeginTimeTmp1,@BeginTimeTmp1;
              end if;
              DEALLOCATE PREPARE stmtselect;
              /*SELECT Count(*) into EnergyCount FROM energyhistory t 
                  where t.houseIEEE=houseIEEE and t.IEEE=IEEE and t.EP=EP
                    and Date(t.opdatetime)=Date(BeginTimeTmp1) and Hour(t.opdatetime)=Hour(BeginTimeTmp1);
              if EnergyCount=0 then 
                insert into energyhistory(HouseIEEE,IEEE,EP,opDateTime,EnergyValue,EnergyPrice) values
                                         (HouseIEEE,IEEE,EP,BeginTimeTmp1,EnergyTmpField,EnergyTmpField*EnergyPrice);
              else 
                update energyhistory t set t.energyValue=t.energyValue+EnergyTmpField,
                                           t.energyPrice=t.energyPrice+EnergyTmpField*EnergyPrice
                where t.houseIEEE=houseIEEE and t.IEEE=IEEE and t.EP=EP
                  and Date(t.opdatetime)=Date(BeginTimeTmp1) and Hour(t.opdatetime)=Hour(BeginTimeTmp1);
              end if;*/
            end if;
          else
            SELECT case  when iDst=1 and BeginTimeTmp1>=DstBeginTime and BeginTimeTmp1<=DstEndTime 
                                  then t.PriceDst 
                             else t.price 
                       end into EnergyPrice
                  from priceparam t where t.houseIEEE=houseIEEE and t.energyTimeId=iTime;

            Set EnergyTmpField=EnergyTmpTime;
            Set @BeginTimeTmp1=BeginTimeTmp1;
            Set @EnergyTmpField=EnergyTmpField;
            Set @EnergyPrice1=EnergyTmpField*EnergyPrice;

            Set @sSql=CONCAT('SELECT Count(*) into @EnergyCount FROM ',' EnergyHistory_',Year(BeginTimeHour),' t 
                where t.houseIEEE=? and t.IEEE=? and t.EP=?
                  and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');
            PREPARE stmtselect FROM @sSql;
            EXECUTE stmtselect using @HouseIEEE,@IEEE,@EP,@BeginTimeTmp1,@BeginTimeTmp1;
            DEALLOCATE PREPARE stmtselect;
            Set EnergyCount=@EnergyCount;
            if EnergyCount=0 then 
              Set @sSql=CONCAT('insert into ',' EnergyHistory_',Year(BeginTimeHour),
                               '(HouseIEEE,IEEE,EP,opDateTime,EnergyValue,EnergyPrice) values
                                       (?,?,?,?,?,?)');
            else 
              Set @sSql=CONCAT('update ',' EnergyHistory_',Year(BeginTimeHour),
                               ' t set t.energyValue=IfNull(t.energyValue,0)+?,
                                       t.energyPrice=IfNull(t.energyPrice,0)+?
              where t.houseIEEE=? and t.IEEE=? and t.EP=?
                and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');                  
            end if;
              
            PREPARE stmtselect FROM @sSql;
            if EnergyCount=0 then
              EXECUTE stmtselect using @HouseIEEE,@IEEE,@EP,@BeginTimeTmp1,@EnergyTmpField,@EnergyPrice1;
            else
              EXECUTE stmtselect using @EnergyTmpField,@EnergyPrice1,@HouseIEEE,@IEEE,@EP,@BeginTimeTmp1,@BeginTimeTmp1;
            end if;
            DEALLOCATE PREPARE stmtselect;
            
            /*SELECT Count(*) into EnergyCount FROM energyhistory t 
                where t.houseIEEE=houseIEEE and t.IEEE=IEEE and t.EP=EP
                  and Date(t.opdatetime)=Date(BeginTimeTmp1) and Hour(t.opdatetime)=Hour(BeginTimeTmp1);
            if EnergyCount=0 then 
              insert into energyhistory(HouseIEEE,IEEE,EP,opDateTime,EnergyValue,EnergyPrice) values
                                       (HouseIEEE,IEEE,EP,BeginTimeTmp1,EnergyTmpTime,EnergyTmpTime*EnergyPrice);
            else 
              update energyhistory t set t.energyValue=t.energyValue+EnergyTmpTime,
                                         t.energyPrice=t.energyPrice+EnergyTmpTime*EnergyPrice
              where t.houseIEEE=houseIEEE and t.IEEE=IEEE and t.EP=EP
                and Date(t.opdatetime)=Date(BeginTimeTmp1) and Hour(t.opdatetime)=Hour(BeginTimeTmp1);
            end if;*/            
          end if;
        end if;
        FETCH Cur_Time into BeginTimeTime,EndTimeTime,iTime;
      END WHILE;
      CLOSE Cur_Time;
    
      Set I=I+1;
    else        
      OPEN Cur_Field;
      FETCH Cur_Field into BeginEnergy, EndEnergy,iField;
      WHILE tmpDone<>1 DO
        Set iContinue=0;
        Set EnergyTmpField=0;
        if EnergySumTmp>=BeginEnergy and EnergySumTmp+EnergyTmpHour<=EndEnergy THEN
          Set EnergyTmpField=EnergyTmpHour;
          Set iContinue=1;
        ELSEIF EnergySumTmp>=BeginEnergy and EnergySumTmp<EndEnergy and EnergySumTmp+EnergyTmpHour>EndEnergy THEN
          Set EnergyTmpField=EndEnergy-EnergySumTmp;
          Set iContinue=2;
        ELSEIF EnergySumTmp<BeginEnergy and EnergySumTmp+EnergyTmpHour<=EndEnergy and EnergySumTmp+EnergyTmpHour>BeginEnergy THEN
          Set EnergyTmpField=EnergySumTmp+EnergyTmpHour-BeginEnergy;
          Set iContinue=3;
        ELSEIF EnergySumTmp<BeginEnergy and EnergySumTmp+EnergyTmpHour>EndEnergy THEN
          Set EnergyTmpField=EndEnergy-BeginEnergy;
          Set iContinue=4;
        ELSE
          Set iContinue=0;          
        end if;
        
        if iContinue<>0 then  
                  
          SELECT case  when iDst=1 and BeginTimeHour>=DstBeginTime and BeginTimeHour<=DstEndTime 
                            then t.PriceDst 
                       else t.price 
                 end into EnergyPrice
            from priceparam t where t.houseIEEE=houseIEEE and t.energyFieldId=iField;
          Set @HouseIEEE=HouseIEEE;
          Set @IEEE=IEEE;
          Set @EP=EP;
          Set @beginTimeHour=beginTimeHour;
          Set @EnergyTmpField=EnergyTmpField;
          Set @EnergyPrice1=EnergyTmpField*EnergyPrice;
          Set @sSql=CONCAT('SELECT Count(*) into @EnergyCount FROM ',' EnergyHistory_',Year(BeginTimeHour),' t 
              where t.houseIEEE=? and t.IEEE=? and t.EP=?
                and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');
          PREPARE stmtselect FROM @sSql;
          EXECUTE stmtselect using @HouseIEEE,@IEEE,@EP,@beginTimeHour,@beginTimeHour;
          DEALLOCATE PREPARE stmtselect;
          Set EnergyCount=@EnergyCount;
          if EnergyCount=0 then 
            Set @sSql=CONCAT('insert into ',' EnergyHistory_',Year(BeginTimeHour),
                             '(HouseIEEE,IEEE,EP,opDateTime,EnergyValue,EnergyPrice) values
                                     (?,?,?,?,?,?)');
          else 
            Set @sSql=CONCAT('update ',' EnergyHistory_',Year(BeginTimeHour),
                             ' t set t.energyValue=IfNull(t.energyValue,0)+?,
                                     t.energyPrice=IfNull(t.energyPrice,0)+?
            where t.houseIEEE=? and t.IEEE=? and t.EP=?
              and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');                  
          end if;
           
          PREPARE stmtselect FROM @sSql;
          if EnergyCount=0 then
            EXECUTE stmtselect using @HouseIEEE,@IEEE,@EP,@beginTimeHour,@EnergyTmpField,@EnergyPrice1;
          else
            EXECUTE stmtselect using @EnergyTmpField,@EnergyPrice1,@HouseIEEE,@IEEE,@EP,@beginTimeHour,@beginTimeHour;
          end if;
          DEALLOCATE PREPARE stmtselect;
          
          /*SELECT Count(*) into EnergyCount FROM energyhistory t 
            where t.houseIEEE=houseIEEE and t.IEEE=IEEE and t.EP=EP
              and Date(t.opdatetime)=Date(beginTimeHour) and Hour(t.opdatetime)=Hour(BeginTimeHour);
          
          if EnergyCount=0 then 
            insert into energyhistory(HouseIEEE,IEEE,EP,opDateTime,EnergyValue,EnergyPrice) values
                                     (HouseIEEE,IEEE,EP,BeginTimeHour,EnergyTmpField,EnergyTmpField*EnergyPrice);
          else 
            update energyhistory t set t.energyValue=t.energyValue+EnergyTmpField,
                                       t.energyPrice=t.energyPrice+EnergyTmpField*EnergyPrice
            where t.houseIEEE=houseIEEE and t.IEEE=IEEE and t.EP=EP
              and Date(t.opdatetime)=Date(BeginTimeHour) and Hour(t.opdatetime)=Hour(BeginTimeHour);
          end if;*/
        end if;
        FETCH Cur_Field into BeginEnergy, EndEnergy,iField;        
      end WHILE;
      CLOSE Cur_Field;
      Select EnergySumTmp,EnergyTmpHour,EndEnergy,EnergyTmpField;
      if EnergySumTmp+EnergyTmpHour>EndEnergy THEN
        Set EnergyTmpField=EnergyTmpHour;
        /*Set EnergyTmpField=EnergySumTmp+EnergyTmpHour-EndEnergy;*/
        Set @HouseIEEE=HouseIEEE;
        Set @IEEE=IEEE;
        Set @EP=EP;
        Set @beginTimeHour=beginTimeHour;
        Set @EnergyTmpField=EnergyTmpField;
        Set @EnergyPrice1=EnergyTmpField*EnergyPrice;
        Set @sSql=CONCAT('SELECT Count(*) into @EnergyCount FROM ',' EnergyHistory_',Year(BeginTimeHour),' t 
            where t.houseIEEE=? and t.IEEE=? and t.EP=?
              and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');
        PREPARE stmtselect FROM @sSql;
        EXECUTE stmtselect using @HouseIEEE,@IEEE,@EP,@beginTimeHour,@beginTimeHour;
        DEALLOCATE PREPARE stmtselect;
        Set EnergyCount=@EnergyCount;
        if EnergyCount=0 then 
          Set @sSql=CONCAT('insert into ',' EnergyHistory_',Year(BeginTimeHour),
                           '(HouseIEEE,IEEE,EP,opDateTime,EnergyValue,EnergyPrice) values
                                   (?,?,?,?,?,?)');
        else 
          Set @sSql=CONCAT('update ',' EnergyHistory_',Year(BeginTimeHour),
                           ' t set t.energyValue=IfNull(t.energyValue,0)+?,
                                   t.energyPrice=IfNull(t.energyPrice,0)+?
          where t.houseIEEE=? and t.IEEE=? and t.EP=?
            and Date(t.opdatetime)=Date(?) and Hour(t.opdatetime)=Hour(?)');                  
        end if;
          
        PREPARE stmtselect FROM @sSql;
        if EnergyCount=0 then
          EXECUTE stmtselect using @HouseIEEE,@IEEE,@EP,@beginTimeHour,@EnergyTmpField,@EnergyPrice1;
        else
          EXECUTE stmtselect using @EnergyTmpField,@EnergyPrice1,@HouseIEEE,@IEEE,@EP,@beginTimeHour,@beginTimeHour;
        end if;
        DEALLOCATE PREPARE stmtselect;
        
        /*SELECT Count(*) into EnergyCount FROM energyhistory t 
            where t.houseIEEE=houseIEEE and t.IEEE=IEEE and t.EP=EP
              and Date(t.opdatetime)=Date(BeginTimeHour) and Hour(t.opdatetime)=Hour(BeginTimeHour);
          if EnergyCount=0 then 
            insert into energyhistory(HouseIEEE,IEEE,EP,opDateTime,EnergyValue,EnergyPrice) values
                                     (HouseIEEE,IEEE,EP,BeginTimeHour,EnergyTmpField,EnergyTmpField*EnergyPrice);
          else 
            update energyhistory t set t.energyValue=t.energyValue+EnergyTmpField,
                                       t.energyPrice=t.energyPrice+EnergyTmpField*EnergyPrice
            where t.houseIEEE=houseIEEE and t.IEEE=IEEE and t.EP=EP
              and Date(t.opdatetime)=Date(BeginTimeHour) and Hour(t.opdatetime)=Hour(BeginTimeHour);
          end if;*/
      end if;
      Set I=I+1;
    end if;
  end WHILE;  
END Label_At_Start;

/*新建监控日志表*/
CREATE TABLE `monitorlog` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `houseIEEE` varchar(50) DEFAULT NULL,
  `staterecords` tinyint(2) DEFAULT NULL,
  `statechangetime` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*查询撤防时的设备*/
DROP PROCEDURE IF EXISTS `ModemacrosubTest`;

CREATE PROCEDURE `ModemacrosubTest`(IN `behavior` bigint,IN `Id` bigint,IN `houseId` bigint)
BEGIN 
	#Routine body goes here...
DECLARE aCount INTEGER;
IF behavior=1 THEN
		Select t.* from modeactlib t GROUP BY t.ActName,t.ExtentionSet ORDER BY t.ExtentionSet;
ELSEIF behavior=3 THEN
  Select Count(*) into aCount FROM modeactlib t WHERE t.ID=ID 
          and (t.ActName='Disarm' or t.ActName='ArmAllZone' or t.ActName='ArmDayZone'
               or t.ActName='ArmNightZone');
  if aCount=0 then    
    Select r.roomName,D.id,D.roomId,D.deviceName FROM
		(Select  t1.DeviceID,t.id from modeactlib t inner join modeactlib t1 on t.ActName=t1.ActName
		where t.ID=id group by t1.DeviceID) m 	
		inner join modedevice d on m.DeviceID=d.deviceId
    inner join modenodelib nodeL on d.modelId=nodeL.modelId or LOCATE(nodeL.modelId,d.modelId)=1
		inner join modeeplib EPL on EPL.nodeId=nodeL.id and EPL.ep=d.ep
		inner join moderoom r on d.roomId=r.roomId and d.houseId=r.houseId
		where  ((m.ID=16 and (d.modelID='Z210' or d.modelId='Z211')) or  m.ID<>16) and d.houseId=houseId ORDER BY d.roomId;
  else
    select * from (Select r.roomName,D.id,D.roomId,D.deviceName FROM
		(Select  t1.DeviceID,t.id from modeactlib t inner join modeactlib t1 on t.ActName=t1.ActName
		where t.ID=id group by t1.DeviceID) m 	
		inner join modedevice d on m.DeviceID=d.deviceId
    inner join modenodelib nodeL on d.modelId=nodeL.modelId or LOCATE(nodeL.modelId,d.modelId)=1 
		inner join modeeplib EPL on EPL.nodeId=nodeL.id and EPL.ep=d.ep
		inner join moderoom r on d.roomId=r.roomId and d.houseId=r.houseId
		where ((m.ID=16 and (d.modelID='Z210' or d.modelId='Z211')) or  m.ID<>16) and d.houseId=houseId
    UNION
    Select r.roomName,D.id,D.roomId,D.deviceName FROM modedevice d     
    inner join modenodelib nodeL on d.modelId=nodeL.modelId or LOCATE(nodeL.modelId,d.modelId)=1
		inner join modeeplib EPL on EPL.nodeId=nodeL.id and d.deviceId=EPL.deviceId and d.ep=epl.ep  
		inner join moderoom r on d.roomId=r.roomId and d.houseId=r.houseId
		where d.houseId=HouseID and (nodeL.ID=86 or nodeL.ID=145)) Main order by Main.RoomID;
  end if;

ELSEIF behavior=2 THEN
		SELECT attr.*,MIN(attr.ID) from deviceattrlib attr GROUP BY attr.ClusterID, attr.AttrID;
ELSEIF behavior=4 THEN
  Select Count(*) into aCount FROM modeactlib t WHERE t.ID=ID 
          and (t.ActName='Disarm' or t.ActName='ArmAllZone' or t.ActName='ArmDayZone'
               or t.ActName='ArmNightZone');
  if aCount=0 then    
    Select r.roomName,D.id,D.roomId,D.deviceName from modedevice d 
    inner join modenodelib nodeL on d.modelId=nodeL.modelId or LOCATE(nodeL.modelId,d.modelId)=1		
		inner join modeeplib EPL on EPL.nodeId=nodeL.id and EPL.ep=d.ep
		inner join deviceattrlib attr on attr.ModelID=nodeL.modelId and attr.DeviceID=EPL.deviceId
		inner join deviceattrlib attr1 on attr.ClusterID=attr1.ClusterID and Attr.AttrID=attr1.AttrID
		inner join moderoom r on d.roomId=r.roomId and d.houseId=r.houseId
		where attr1.ID=id and r.houseId=houseId ORDER BY d.roomId;
  else
    select * from (Select r.roomName,D.id,D.roomId,D.deviceName from modedevice d 
    inner join modenodelib nodeL on d.modelId=nodeL.modelId or LOCATE(nodeL.modelId,d.modelId)=1		
		inner join modeeplib EPL on EPL.nodeId=nodeL.id and EPL.ep=d.ep
		inner join deviceattrlib attr on attr.ModelID=nodeL.modelId and attr.DeviceID=EPL.deviceId
		inner join deviceattrlib attr1 on attr.ClusterID=attr1.ClusterID and Attr.AttrID=attr1.AttrID
		inner join moderoom r on d.roomId=r.roomId and d.houseId=r.houseId
		where attr1.ID=id and r.houseId=houseId 
    UNION
    Select r.roomName,D.id,D.roomId,D.deviceName FROM modedevice d     
    inner join modenodelib nodeL on d.modelId=nodeL.modelId or LOCATE(nodeL.modelId,d.modelId)=1
		inner join modeeplib EPL on EPL.nodeId=nodeL.id and d.deviceId=EPL.deviceId and d.ep=epl.ep  
		inner join moderoom r on d.roomId=r.roomId and d.houseId=r.houseId
		where d.houseId=HouseID and (nodeL.ID=86 or nodeL.ID=145)) Main order by Main.RoomID;
  end if;

ELSEIF behavior=5 THEN
	Select m.id,m.GroupName as vName 
		from modegroupmain m where m.houseId = houseId;		
ELSEIF behavior=6 THEN	
	Select d.roomId,d.deviceName,m.GroupName,r.roomName,u.id,u.mid,u.DeviceID
		from Modegroupsub u inner join Modegroupmain m on u.mid=m.id
												inner join Modedevice d on u.deviceId=d.id
												inner join Moderoom r on d.houseId=r.houseId and d.roomId=r.roomId 
		where m.houseId = houseId and m.id=id ORDER BY r.roomId;
ELSEIF behavior=7 THEN
	if (id=0) then 
		Select Act.* 
			from modeactlib act WHERE act.Groupabled=1 
			group by act.ActName,act.ExtentionSet ORDER BY Act.ExtentionSet;
	else
		Select Act2.* from modegroupmain m
			inner join modegroupsub gsub on m.id=gsub.MID
			inner join Modedevice d on d.Id=gsub.DeviceID
			inner join modeactlib act on  act.DeviceID=d.deviceId 
      inner join modeactlib act2 on act.grouplevel=act2.GroupLevel and act.ExtentionSet=act2.ExtentionSet 
		where m.ID=id group by act2.ActName,act2.ExtentionSet ORDER BY Act2.ExtentionSet;	
	end if;
ELSEIF behavior=8 THEN
	Select d.id as deviceId,d.roomId,d.deviceName,r.roomName
	from(Select act2.DeviceID from modeactlib act1 inner join modeactlib act2 on act1.grouplevel=act2.GroupLevel and act1.ExtentionSet=act2.ExtentionSet
			where Act1.id=id group by act2.deviceid) act3
	inner join Modedevice d on d.deviceId=act3.DeviceID  
	inner join Moderoom r on d.roomId=r.roomId and d.houseId=r.houseId
	where d.houseId=houseId ORDER BY r.roomId;

ELSEIF behavior=9 THEN
	Select m.id,m.GroupName as vName 
		from modescenemain m where m.houseId = houseId;		
ELSEIF behavior=10 THEN
	Select d.roomId,d.deviceName,m.GroupName,act.UniqueName,act.DataType,r.roomName,u.id,u.mid,u.DeviceID,u.actlibID,u.TransTime
		from modescenesub u inner join modescenemain m on u.mid=m.id
												inner join Modedevice d on u.deviceId=d.id
												inner join modeactlib act on u.actlibID=act.id 
												inner join Moderoom r on d.houseId=r.houseId and d.roomId=r.roomId 
		where m.houseId = houseId and m.id=id ORDER BY r.roomId;
ELSEIF behavior=11 THEN
	Select Act2.* from Modedevice d
		inner join modeactlib act on act.DeviceID=d.deviceId 
		inner join modeactlib act2 on act.grouplevel=act2.GroupLevel and act.ExtentionSet=act2.ExtentionSet 
		where d.ID=id and act2.Sceneabled=1 group by act2.ActName,act2.ExtentionSet ORDER BY Act2.ExtentionSet;
ELSEIF behavior=12 THEN
	Select d.id as deviceId,d.roomId,d.deviceName,r.roomName 
	from(Select act1.DeviceID from modeactlib act1 
			where act1.Sceneabled=1 group by act1.deviceid) act3
	inner join Modedevice d on d.deviceId=act3.DeviceID  
	inner join Moderoom r on d.roomId=r.roomId and d.houseId=r.houseId
	where d.houseId=houseId ORDER BY r.roomId;
END IF;
END;