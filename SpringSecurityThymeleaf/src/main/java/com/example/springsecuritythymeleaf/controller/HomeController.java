package com.example.springsecuritythymeleaf.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController
{
	@GetMapping(path = { "/index" })
	public ModelAndView index(Authentication authentication)
	{
		// localhost:8080/SpringSecurityThymeleaf/index
		return new ModelAndView("index");
	}

	@GetMapping(path = { "/secureindex" })
	public ModelAndView secureindex(Authentication authentication)
	{
		// localhost:8080/SpringSecurityThymeleaf/secureindex
		return new ModelAndView("secureindex");
	}

	@GetMapping(path = "systemlogin")
	public ModelAndView systemlogin()
	{
		// localhost:8080/SpringSecurityThymeleaf/systemlogin
		return new ModelAndView("systemlogin");
	}
}
