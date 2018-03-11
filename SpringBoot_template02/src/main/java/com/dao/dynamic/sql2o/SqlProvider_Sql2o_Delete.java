package com.dao.dynamic.sql2o;


// JAVA
import java.lang.reflect.Field;
import java.util.Map;

import com.dao.annotation.PrimaryKey;
import com.dao.dynamic.SqlProvider;

//THIRD PARTY

public class SqlProvider_Sql2o_Delete extends SqlProvider{

	public SqlProvider_Sql2o_Delete() {
		super.actionType = ACTION.D;
	}
	
	@Override
	public <T> String getSql(T aObj, Map<String,Object> aTmpFieldMap) {
		/** insert動態指令建立 **/
		StringBuilder sql_delete = new StringBuilder();
		StringBuilder sql_delete_where = new StringBuilder();
		
		/** 看有那些欄位需要當作搜尋值 **/
		Field[] fields = aObj.getClass().getDeclaredFields();
		
		for (Field f : fields){
			try {
				f.setAccessible(true); // prevent from error of accessing "private" fields
				Object fObj = f.get(aObj);
				if (f.isAnnotationPresent(PrimaryKey.class)) {
					System.out.println("update PrimaryKey annotation is present");
					sql_delete_where.append(f.getName() + " = :" + f.getName());
					
					aTmpFieldMap.put(f.getName(), fObj);
	//				aTmpFieldList.add(f);
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}// end of for (Field f : fields)
		
//		if (Strings.isBlank(sql_delete_where.toString())){
		if (sql_delete_where.toString() == null || sql_delete_where.toString().isEmpty()){
			throw new RuntimeException("WHERE語句為空  請提供搜尋條件");
		}
		
		sql_delete.append("DELETE FROM ")
				  .append(aObj.getClass().getSimpleName())
				  .append(" WHERE ").append(sql_delete_where.toString());
			
		return sql_delete.toString();
	}

}
