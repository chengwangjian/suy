/*修改设备类型ID索引*/
ALTER TABLE z485template DROP INDEX template_id_unique;
ALTER TABLE z485template ADD UNIQUE template_id_unique(template_id);
/*增加设备状态属性名*/
INSERT INTO `attributenamelib` (`id`, `attributeNameCn`, `attributeName`, `clusterID`, `attrID`) VALUES ('37', '设备状态', 'isonline', '0000', 'FFFF');
/*485去掉指令的唯一性索引*/
ALTER TABLE z485action DROP INDEX action_cmd_unique;
