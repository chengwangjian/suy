/*监控日志查询太慢，未完全用到索引，这里添加下*/
ALTER TABLE house DROP INDEX houseIEEE_index;
ALTER TABLE house ADD INDEX hieee_iline_index(houseIEEE,isonline);
ALTER TABLE houseservice ADD INDEX hieee_mstate_index(houseIEEE,monitor_state);