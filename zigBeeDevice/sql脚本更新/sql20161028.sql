
ALTER TABLE `rssirecord`
ADD COLUMN `LQI`  int(100) NULL AFTER `house_ieee`;

/*rssirecord_houseid_year（类似rssirecord_00137a0000012dff_2016的都要添加LQI字段）中添加字段LQI：*/
ALTER TABLE rssirecord_houseid_year ADD COLUMN `LQI`  int(100) NULL AFTER `house_ieee`;
