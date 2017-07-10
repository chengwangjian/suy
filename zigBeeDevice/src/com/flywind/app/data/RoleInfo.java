package com.flywind.app.data;

import java.util.List;

public class RoleInfo {
private Long userId;
private List<Long> roleIdList;
public Long getUserId() {
	return userId;
}
public void setUserId(Long userId) {
	this.userId = userId;
}
public List<Long> getRoleIdList() {
	return roleIdList;
}
public void setRoleIdList(List<Long> roleIdList) {
	this.roleIdList = roleIdList;
}
}
