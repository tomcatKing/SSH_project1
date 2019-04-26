package com.cart.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateTest {
	public static void main(String[] args) throws ParseException {
		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date begin=dfs.parse("2004-03-26 11:30:00");
		java.util.Date end = dfs.parse("2004-03-26 12:29:59");
		long between=(end.getTime()-begin.getTime())/1000;//除以1000是为了转换成秒
		long day1=between/(3600);
		System.out.println(day1>1);
	}
}
