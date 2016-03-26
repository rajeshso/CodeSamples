package com.one.two;

import javax.servlet.ServletContext;  
import javax.servlet.ServletException;  
import javax.servlet.ServletRegistration.Dynamic;  
  
import org.springframework.web.WebApplicationInitializer;  
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;  
import org.springframework.web.servlet.DispatcherServlet;  
  
public class WebAppInitializer implements WebApplicationInitializer {
	
	public void onStartup(ServletContext servletContext) throws ServletException {  
		AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
		System.out.println("my ctx"+ ctx);
		 ctx.register(AppConfig.class);
		 System.out.println("my servlet context object"+ servletContext);
		 ctx.setServletContext(servletContext);
		 //null pointer exception...
		 Dynamic servlet = (Dynamic) servletContext.addServlet("dispatchar",new DispatcherServlet(ctx));
		 System.out.println("this is my object of dynamic   "+ servlet);
		 servlet.addMapping("/");
		 servlet.setLoadOnStartup(1);
   }  
}
