/*向表weatherenv中添加字段weather/weatherDay/weatherNight：*/
ALTER TABLE weatherenv ADD COLUMN weather varchar(255) DEFAULT NULL;
ALTER TABLE weatherenv ADD COLUMN weatherDay varchar(255) DEFAULT NULL;
ALTER TABLE weatherenv ADD COLUMN weatherNight varchar(255) DEFAULT NULL;