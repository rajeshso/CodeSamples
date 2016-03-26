package com.rajesh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan("com.rajesh")
public class WebConfig extends WebMvcConfigurerAdapter {

	// Configure a JSP view resolver
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/view");
		resolver.setSuffix(".jsp");
		resolver.setExposeContextBeansAsAttributes(true);
		return resolver;
	}

	/**
	 * Configure static content handling
	 * Ask DispatcherServlet to forward requests for static resources to the
	 * servlet container’s default servlet and not to try to handle them itself.
	 */
	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer defaultServletHandlerConfigurer) {
		defaultServletHandlerConfigurer.enable();
	}
}
