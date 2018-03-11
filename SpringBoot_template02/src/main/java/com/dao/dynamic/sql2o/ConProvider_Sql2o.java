package com.dao.dynamic.sql2o;


// SELF

//THIRD PARTY
import org.sql2o.Sql2o;

import com.dao.dynamic.ConProvider;


public class ConProvider_Sql2o extends ConProvider{

	private Sql2o sql2oForAll = Sql2OService.getSQL2OInstance();
	
	@Override
	protected Object getCon() {
		return sql2oForAll.open();
	}
	@Override
	public Object getTxCon() {
		return sql2oForAll.beginTransaction();
	}

}
