package com.dao.dynamic.sql2o;


// JAVA
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

// SELF

// THIRD PARTY
import org.sql2o.Query;

import com.dao.dynamic.SqlProcessor;
import com.dao.dynamic.SqlProvider;
import com.dao.dynamic.jdbc.ConProvider_JDBC;

import org.sql2o.Connection;


public class SqlProcessor_Sql2o_Base extends SqlProcessor{
	
	public SqlProcessor_Sql2o_Base() {
		
	}
	
	public SqlProcessor_Sql2o_Base(SqlProvider aSqlProvider) {
		super.sqlProvider = aSqlProvider;
		super.conProvider = new ConProvider_JDBC();
	}
	
//	Sql2OService
//	private Sql2o sql2oForAll = Sql2OService.getSQL2OInstance();
	public <T> Object execute(Object aCon, T aObj) {
		Object result = null;
//		List<Field> tmpFieldList = new ArrayList<>(); // for performance
//		/** 拿取sql **/
//		String sql = this.getSql(aObj.getClass().getSimpleName(), aObj, tmpFieldList);
		Map<String, Object> tmpFieldMap = new HashMap<>(); // for performance
//		String sql = this.getSql(aObj.getClass().getSimpleName(), aObj, tmpFieldList);
		String sql = super.sqlProvider.getSql(aObj, tmpFieldMap);
		System.out.println(super.sqlProvider.getClass().getSimpleName() + " sql_result: " + sql);
		/** 建立Query物件 **/
		Query query = ((Connection)aCon).createQuery(sql.toString()); // 關鍵
		/** 更新Query參數 **/
		this.updateParam(query, aObj, tmpFieldMap);
		/** 執行 **/
		switch(super.sqlProvider.getActionType()) {
		case R:
			result = query.executeAndFetch(aObj.getClass());
			break;
		case C:
			result = query.executeUpdate().getKey(); // pk值
			break;
		case U:
		case D:
			result = query.executeUpdate().getResult(); // 更新比數
			break;
		default:
			break;
		
		}
		return result;
	}
	
	final public <T> void updateParam(Object aQuery, T aObj, Map<String,Object> tmpFieldMap) {
		for (Entry<String, Object> map : tmpFieldMap.entrySet()) {
			((Query)aQuery).addParameter(map.getKey(), map.getValue());
//			
//			Object fObj = null;
//			try {
//				fObj = map.get(aObj);
//			} catch (IllegalArgumentException | IllegalAccessException e) {
//				Util.getConsoleLogger().info("Util.getExceptionMsg(e): " + Util.getExceptionMsg(e));
//				Util.getFileLogger().info("Util.getExceptionMsg(e): " + Util.getExceptionMsg(e));
//			}
//			if (fObj != null){
//				aQuery.addParameter(map.getName(), fObj);
//			}
		};
	}

}
