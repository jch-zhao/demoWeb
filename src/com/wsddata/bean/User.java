package com.wsddata.bean;

public class User {
	private String username;
	private String password;
	private int isAdmin;
	private String ipRange;
	
	public String getIpRange() {
		return ipRange;
	}
	public void setIpRange(String ipRange) {
		this.ipRange = ipRange;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
//	public String getPassword() {
//		return password;
//	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}
	
}
