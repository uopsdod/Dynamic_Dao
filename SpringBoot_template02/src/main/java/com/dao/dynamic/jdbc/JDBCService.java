package com.dao.dynamic.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.sql2o.Sql2o;

@Component
public class JDBCService {
    private static JdbcTemplate jdbcTemplate;
    @Autowired
    public JDBCService(JdbcTemplate jdbcTemplate) {
    	JDBCService.jdbcTemplate = jdbcTemplate;
    }	
	
	public static JdbcTemplate getJDBCTemplte() { return JDBCService.jdbcTemplate; }	
}
