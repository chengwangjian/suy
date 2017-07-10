/*向表deviceattribute中添加字段time：*/
ALTER TABLE deviceattribute ADD COLUMN time datetime(0) DEFAULT NULL;
/*向表deviceattributehistory_houseid_year（类似deviceattributehistory_00137A0000012DFF_2016的都要添加time字段）中添加字段time：*/
ALTER TABLE deviceattributehistory_houseid_year ADD COLUMN time datetime(0) DEFAULT NULL;