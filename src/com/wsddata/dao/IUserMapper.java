package com.wsddata.dao;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import com.wsddata.bean.*;

public interface IUserMapper {
	public User selectUserByNameAndPassword(String username,String password);
	public User selectUserByID(int id);
	public User selectUserByName(String name);
	public List<User> allUsers();
	public void addUser(User user);
	public void modifyUser(User user);
	public void deleteByID(int id);
}
