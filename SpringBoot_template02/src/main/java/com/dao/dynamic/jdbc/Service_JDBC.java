package com.dao.dynamic.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.sql2o.Sql2o;

@Component
public class Service_JDBC {
    private static JdbcTemplate jdbcTemplate;
    @Autowired
    public Service_JDBC(JdbcTemplate jdbcTemplate) {
    	Service_JDBC.jdbcTemplate = jdbcTemplate;
    }	
	
	public static JdbcTemplate getJDBCTemplte() { return Service_JDBC.jdbcTemplate; }	
}
