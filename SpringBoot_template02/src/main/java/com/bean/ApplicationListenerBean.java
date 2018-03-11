package com.bean;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;

import com.dao.dynamic.SqlProcessor;
import com.dao.dynamic.jdbc.Assembler_JDBC;
import com.dao.dynamic.jdbc.Assembler_JDBC.ACTION_JDBC;
import com.dao.dynamic.sql2o.Assembler_Sql2o;
import com.dao.dynamic.sql2o.Assembler_Sql2o.ACTION_SQL2O;
import com.util.Util;

public class ApplicationListenerBean implements ApplicationListener {
	
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ContextRefreshedEvent) {
        	Util.getConsoleLogger().info("ContextRefreshedEvent start ###################");
        	Util.getFileLogger().info("ContextRefreshedEvent start ###################");
        	
        	
    		// sql2o
//    		Object result = null;
//    		tblSystemMonitor tblSystemMonitor = new tblSystemMonitor();
//    		tblSystemMonitor.setStatus("23");
//    		
//    		Assembler_Sql2o myJPF_Sql2oDao = new Assembler_Sql2o();
//    		// findAll
//    		SqlProcessor jpf = myJPF_Sql2oDao.getSqlProcessor_map().get(ACTION_SQL2O.R);
//    		Object con = myJPF_Sql2oDao.getCon();
//    		List<tblSystemMonitor> resultAsList = myJPF_Sql2oDao.getResultAsList(con, tblSystemMonitor, jpf);
//    		Util.getConsoleLogger().info("contextInitialized() tblSystemMonitor_result_findAll: " + resultAsList);

    		// jdbc
    		Object result = null;
    		tblSystemMonitor tblSystemMonitor = new tblSystemMonitor();
    		tblSystemMonitor.setStatus("23");
    		
    		Assembler_JDBC myJPF_JDBCDao = new Assembler_JDBC();
    		
    		// jdbc - findAll 
    		SqlProcessor jpf = myJPF_JDBCDao.getSqlProcessor_map().get(ACTION_JDBC.R);
    		Object con = myJPF_JDBCDao.getCon();
    		List<tblSystemMonitor> resultAsList = myJPF_JDBCDao.getResultAsList(con, tblSystemMonitor, jpf);
    		Util.getConsoleLogger().info("contextInitialized() tblSystemMonitor_result_findAll: " + resultAsList);
   		
    		// jdbc - insert
    		tblSystemMonitor.setStatus("25");
    		jpf = myJPF_JDBCDao.getSqlProcessor_map().get(ACTION_JDBC.C);
    		result = myJPF_JDBCDao.getResult(con, tblSystemMonitor, jpf);
    		Util.getConsoleLogger().info("contextInitialized() tblSystemMonitor_result_insert: " + result);
    		
    		// jdbc - delete
    		tblSystemMonitor.setDbid(Integer.parseInt(result.toString()));
    		jpf = myJPF_JDBCDao.getSqlProcessor_map().get(ACTION_JDBC.D);
    		result = myJPF_JDBCDao.getResult(con, tblSystemMonitor, jpf);
    		Util.getConsoleLogger().info("contextInitialized() tblSystemMonitor_result_delete: " + result);
    		
    		// jdbc - insert
    		tblSystemMonitor.setStatus("25");
    		jpf = myJPF_JDBCDao.getSqlProcessor_map().get(ACTION_JDBC.C);
    		result = myJPF_JDBCDao.getResult(con, tblSystemMonitor, jpf);
    		Util.getConsoleLogger().info("contextInitialized() tblSystemMonitor_result_insert: " + result);
    		
    		// jdbc - update
    		tblSystemMonitor.setDbid(Integer.parseInt(result.toString()));
    		tblSystemMonitor.setName("XXXX");
    		jpf = myJPF_JDBCDao.getSqlProcessor_map().get(ACTION_JDBC.U);
    		result = myJPF_JDBCDao.getResult(con, tblSystemMonitor, jpf);
    		Util.getConsoleLogger().info("contextInitialized() tblSystemMonitor_result_update: " + result);
    		
    		
    		
     		
        	Util.getConsoleLogger().info("ContextRefreshedEvent end ###################");
        	Util.getFileLogger().info("ContextRefreshedEvent end ###################");
        }
        
        if (event instanceof ContextClosedEvent) {
        	Util.getConsoleLogger().info("ContextClosedEvent start ###################");
        	Util.getFileLogger().info("ContextClosedEvent start ###################");
        	
        	Util.getConsoleLogger().info("ContextClosedEvent end ###################");
        	Util.getFileLogger().info("ContextClosedEvent end ###################");
        }
        
    }
    
}