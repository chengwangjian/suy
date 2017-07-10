/*modefarmhouse表添加3个字段*/     
ALTER TABLE modefarmhouse ADD(
note VARCHARACTER(250) DEFAULT '0' not null,
longitude VARCHARACTER(20) DEFAULT '0' not null,
latitude  VARCHARACTER(20) DEFAULT '0' not null
);