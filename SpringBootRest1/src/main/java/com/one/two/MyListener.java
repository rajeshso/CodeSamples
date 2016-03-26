package com.one.two;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyListener implements ServletContextListener{
public void contextInitialized(ServletContextEvent sce){
System.out.println("My Servlet Context Initialised");
}

public void contextDestroyed(ServletContextEvent sce){
System.out.println("My Servlet Context Destroyed");
}
}
