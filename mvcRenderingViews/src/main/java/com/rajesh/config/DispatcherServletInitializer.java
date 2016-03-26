package com.rajesh.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
/**
 * The Web container looks for the ServletInitializer for the initialisation. 
 * AbstractAnnotationConfigDispatcherServletInitializer is linked with ServletInitializer.
 * Hence, it is easily picked up. Be guaranteed that this is invoked.
 * Use this initialisation to tell that you prefer Dispatcher Servlet is the Front Controller
 *  
 * @author my pc
 *
 */
public class DispatcherServletInitializer extends
		AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { RootConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { WebConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

}
