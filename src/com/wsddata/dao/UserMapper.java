package com.wsddata.dao;

import java.util.List;
import com.wsddata.bean.*;

public interface UserMapper {
	public User selectUserByNameAndPassword(String username,String password);
	public User selectUserByID(int id);
	public User selectUserByName(String name);
	public List<User> allUsers();
	public void addUser(User user);
	public void modifyUser(User user);
	public void deleteByID(int id);
}
