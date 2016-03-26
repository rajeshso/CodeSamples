package com.rajesh.endpoint;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * This enables Component scan for endpoint and its sub packages.
 * Alternatively, you can use the basePackages to specify any packages.
 * @author my pc
 *
 */
@Configuration
@ComponentScan
//@ComponentScan(basePackages="com.rajesh.endpoint")
public class StudioConfiguration {

}
