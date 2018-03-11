package com.dao.dynamic.sql2o;

import org.sql2o.Sql2o;

import com.util.Util;

public class Service_Sql2O {
	
	// Lazy initialization - holder class idiom for static fields
	// This idiom is almost magical. There's synchronization going on, but it's invisible. The Java Runtime Environment does it for you, behind the scenes. And many VMs actually patch the code to eliminate the synchronization once it's no longer necessary, so this idiom is extremely fast.
	// ref: http://www.oracle.com/technetwork/articles/javase/bloch-effective-08-qa-140880.html
	// 		search for 'Best Practices for Lazy Initialization'
	private Service_Sql2O() {}
	
	private static class Sql2OHolder {
	    static final Sql2o SQL2O;
		static{
//			String DB_URL = Util.getinfo360_DB_URL();
			String DB_URL = Util.getSystemParam().get("DB_URL");
//			String USER = Util.getinfo360_DB_USER();
			String DB_USER = Util.getSystemParam().get("DB_USER");
//			String PASS = Util.getinfo360_DB_PASS();
			String DB_PWD = Util.getSystemParam().get("DB_PWD");
			String DB_DRIVER = Util.getSystemParam().get("DB_DRIVER");
			
			System.out.println("DB_URL: " + DB_URL);
			System.out.println("DB_USER: " + DB_USER);
			System.out.println("DB_PWD: " + DB_PWD);
			System.out.println("DB_DRIVER: " + DB_DRIVER);
			
			try {
				Class.forName(DB_DRIVER);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			SQL2O = new Sql2o(DB_URL, DB_USER, DB_PWD);
		}
	}
	public static Sql2o getSQL2OInstance() { return Sql2OHolder.SQL2O; }	
	// end of Lazy initialization
}
