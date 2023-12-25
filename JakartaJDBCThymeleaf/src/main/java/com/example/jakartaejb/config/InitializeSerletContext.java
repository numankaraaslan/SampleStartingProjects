package com.example.jakartaejb.config;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import com.example.jakartaejb.Constants;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class InitializeSerletContext implements ServletContextListener
{
	@Override
	public void contextInitialized(ServletContextEvent sce)
	{
		JakartaServletWebApplication application = JakartaServletWebApplication.buildApplication(sce.getServletContext());
		TemplateEngine templateEngine = new TemplateEngine();
		WebApplicationTemplateResolver templateResolver = new WebApplicationTemplateResolver(application);
		templateResolver.setTemplateMode(TemplateMode.HTML);
		templateResolver.setPrefix("/WEB-INF/templates/");
		templateResolver.setSuffix(".html");
		templateResolver.setCacheTTLMs(Long.valueOf(3600000L));
		templateResolver.setCacheable(false);
		templateEngine.setTemplateResolver(templateResolver);
		// maybe templateEngine can be turned into a bean an injected into servlets ??
		sce.getServletContext().setAttribute(Constants.TemplateEngineAttr, templateEngine);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce)
	{
	}
}