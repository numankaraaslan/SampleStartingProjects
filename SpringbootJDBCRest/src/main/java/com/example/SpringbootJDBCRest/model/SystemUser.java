package com.example.SpringbootJDBCRest.model;

public class SystemUser
{
	private String username = "";
	private String password = "";

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public SystemUser()
	{
	}

	@Override
	public String toString()
	{
		return "SystemUser [username=" + username + ", password=" + password + "]";
	}

}