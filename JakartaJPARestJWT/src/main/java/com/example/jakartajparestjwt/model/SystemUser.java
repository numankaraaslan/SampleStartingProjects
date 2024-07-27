package com.example.jakartajparestjwt.model;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import jakarta.security.enterprise.credential.Credential;

public class SystemUser implements Principal
{
	private String username;
	private String password;
	private Set<String> authorities = new HashSet<>();

	public SystemUser()
	{
	}

	public SystemUser(String username)
	{
		this.username = username;
	}

	public SystemUser(String username, String password, Set<String> authorities)
	{
		this.username = username;
		this.password = password;
		this.authorities = authorities;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public void setAuthorities(Set<String> authorities)
	{
		this.authorities = authorities;
	}

	public Set<String> getAuthorities()
	{
		return authorities;
	}

	@Override
	public String getName()
	{
		return username;
	}
}
