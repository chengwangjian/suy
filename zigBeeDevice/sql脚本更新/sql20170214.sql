/*添加deviceWarnHistory_00137A00000xxxxx_2017类似的都要加上相应的引索*/
ALTER TABLE `deviceWarnHistory_00137A00000xxxxx_2017` ADD INDEX houseIEEE_wmode(houseIEEE,w_mode);

ALTER TABLE `deviceproblem` ADD INDEX modelNo(modelNo);