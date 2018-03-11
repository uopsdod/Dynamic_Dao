package com.dao.dynamic.jdbc;

// THIRD PARTY
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class Service_JDBC {
    private static JdbcTemplate jdbcTemplate;
    @Autowired
    public Service_JDBC(JdbcTemplate jdbcTemplate) {
    	Service_JDBC.jdbcTemplate = jdbcTemplate;
    }	
	
	public static JdbcTemplate getJDBCTemplte() { return Service_JDBC.jdbcTemplate; }	
}
