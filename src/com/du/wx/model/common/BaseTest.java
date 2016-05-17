package com.du.wx.model.common;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BaseTest {
	public static ClassPathXmlApplicationContext context = null;
	private static String classpathFile = "classpath:com/du/wx/resources/spring/springContext.xml" ;
	
	public BaseTest() {
		context = new ClassPathXmlApplicationContext(classpathFile) ;
	}
	
	@Test
	public void testNotNull(){
		Assert.assertNotNull(context);
	}
}
