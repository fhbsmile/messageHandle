package com.tsystems.si.aviation.imf.messageHandle;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContext.xml"});
    	// ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContextForComFlight.xml");
    }
}
