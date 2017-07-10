/*增加查询索引优化*/
ALTER TABLE device ADD INDEX house_ieee_ep(houseIEEE,ieee,ep);

/*增加deviceWarnHistory_houseIeee_2016查询索引优化例如：*/
ALTER TABLE deviceWarnHistory_00137A0000012DFF_2016 ADD INDEX house_ieee_ep_wmode(w_mode,houseIEEE,zone_ieee,zone_ep);

/*增加deviceoperatehistory_houseIeee_2016查询索引优化例如：*/
ALTER TABLE deviceoperatehistory_00137A0000012DFF_2016 ADD INDEX house_ieee_ep(houseIEEE,device_ieee,device_ep);

ALTER TABLE device ADD INDEX house_ieee(houseIEEE,ieee);

ALTER TABLE node ADD INDEX house_ieee(houseIEEE,ieee);

/*增加rssirecord_houseIeee_2016查询索引优化例如：*/
ALTER TABLE rssirecord_00137A0000012DFF_2016 ADD INDEX house_ieee(house_ieee,source_ieee);

/*将数据库中（rssirecord）rssirecord_houseIeee_2016中的RSSI字段都改为int类型，例如：*/
alter table rssirecord_00137a0000020494_2016 modify RSSI int(50);
