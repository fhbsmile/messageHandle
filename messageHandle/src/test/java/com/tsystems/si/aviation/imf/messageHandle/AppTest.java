package com.tsystems.si.aviation.imf.messageHandle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



/**
 * Unit test for simple App.
 */
public class AppTest {
private static final Logger     logger               = LoggerFactory.getLogger(AppTest.class);
public static void main(String[] args) {
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContextForComFlight.xml");
    //ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContextTest_Dynamic.xml");
	//ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContextTest_Schedule.xml");
	//ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContextTest_Change.xml");
}
}
