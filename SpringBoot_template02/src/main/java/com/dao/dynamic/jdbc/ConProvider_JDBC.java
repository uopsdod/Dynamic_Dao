package com.dao.dynamic.jdbc;

// JAVA
import java.sql.Connection;
import java.sql.SQLException;


// SELF
import com.dao.dynamic.ConProvider;

// THIRD PARTY
import org.springframework.jdbc.core.JdbcTemplate;

public class ConProvider_JDBC extends ConProvider{
    private final JdbcTemplate jdbcTemplate = Service_JDBC.getJDBCTemplte();
    public ConProvider_JDBC() {}
	
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
