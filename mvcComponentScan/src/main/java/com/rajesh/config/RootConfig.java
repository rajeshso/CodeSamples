package com.rajesh.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * This configuration is for non-Web components. As this is a web-mvc project,
 * this config is minimal.
 * 
 * @author Rajesh
 *
 */
@Configuration
@ComponentScan(basePackages = { "com.rajesh" }, excludeFilters = { @Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class) }
)
public class RootConfig {

}
