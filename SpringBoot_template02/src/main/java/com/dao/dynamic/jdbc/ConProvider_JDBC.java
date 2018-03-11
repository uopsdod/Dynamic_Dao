package com.dao.dynamic.jdbc;


import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

// SELF

//THIRD PARTY
import com.dao.dynamic.ConProvider;

public class ConProvider_JDBC extends ConProvider{
    private final JdbcTemplate jdbcTemplate = Service_JDBC.getJDBCTemplte();
    @Autowired
    public ConProvider_JDBC() {}

//	private Sql2o sql2oForAll = Sql2OService.getSQL2OInstance();
	
	
	@Override
	protected Object getCon() {
		Connection connection = null;
		try {
			connection = this.jdbcTemplate.getDataSource().getConnection();
			return connection;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;	
	}
	@Override
	public Object getTxCon() {
		Connection connection = null;
		try {
			connection = this.jdbcTemplate.getDataSource().getConnection();
			return connection;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;	
	}

}
