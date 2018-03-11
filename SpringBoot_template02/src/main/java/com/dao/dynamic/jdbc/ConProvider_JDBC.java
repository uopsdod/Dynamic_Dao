package com.dao.dynamic.jdbc;

// JAVA
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

// SELF
import com.dao.dynamic.ConProvider;

// THIRD PARTY

public class ConProvider_JDBC extends ConProvider{
    private final DataSource ds = Service_JDBC.getJDBCTemplte().getDataSource();
    public ConProvider_JDBC() {}
	
	@Override
	protected Object getCon() {
		Connection connection = null;
		try {
			connection = this.ds.getConnection();
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
			connection = this.ds.getConnection();
			return connection;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;	
	}

}
