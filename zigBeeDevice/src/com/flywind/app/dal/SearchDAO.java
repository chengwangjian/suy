package com.flywind.app.dal;

import java.util.List;

import sy.test.User;

public interface SearchDAO {

	List<User> searchUserQuery(String queryName);
}
