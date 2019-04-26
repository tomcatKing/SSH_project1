package com.cart.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mt.result.JsonResult;
import com.mt.service.ICartService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:spring/applicationContext.xml"})
public class CartTest {
	@Autowired
	private ICartService iCartService;
	
	@Test
	public void testCartList() {
		JsonResult result=iCartService.list(1, 1, 10);
		System.out.println(result.getData().toString());
	}
}
