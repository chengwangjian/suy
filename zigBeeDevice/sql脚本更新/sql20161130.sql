/* 添加故障列表模块所需要的数据库表*/
/**
 *  id  -- 序号
 *  modelNo  -- 设备型号
 *  descriptionCn  -- 中文描述
 *  descriptionEn  -- 英文描述
 *  createTime  -- 创建时间
 *  lastTime  -- 修改时间
 */
CREATE TABLE `deviceProblem` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`modelNo`  varchar(200) NULL ,
`descriptionCn`  varchar(255) NULL ,
`descriptionEn`  varchar(255) NULL ,
`createTime`  datetime NULL ,
`lastTime`  datetime NULL ,
PRIMARY KEY (`id`)
);


/*拾联用户信息表增加auth字段*/
alter table slplatformuser add column auth varchar(255) default NULL;