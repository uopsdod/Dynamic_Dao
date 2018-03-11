package com.dao.dynamic.jdbc;



// JAVA
import java.lang.reflect.Field;
import java.util.Map;

// SELF

// THIRD PARTY
import org.apache.logging.log4j.util.Strings;

import com.dao.annotation.PrimaryKey;
import com.dao.dynamic.SqlProvider;

public class SqlProvider_JDBC_Update extends SqlProvider{

	public SqlProvider_JDBC_Update() {
		super.actionType = ACTION.U;
	}
	
	@Override
	public <T> String getSql(T aObj, Map<String,Object> aTmpFieldMap) {
		StringBuilder sql_update = new StringBuilder();
		StringBuilder sql_update_set = new StringBuilder();
		StringBuilder sql_update_where = new StringBuilder();
		/** 看有那些欄位需要當作搜尋值 **/
		String pKeyFieldName = "";
		Object pKeyFObj = null;
		Field[] fields = aObj.getClass().getDeclaredFields();
		try {
			for (Field f : fields){
				f.setAccessible(true); // prevent from error of accessing "private" fields
				Object fObj = f.get(aObj);
				if (fObj != null) {
					if (f.isAnnotationPresent(PrimaryKey.class)) {
						System.out.println("update PrimaryKey annotation is present");
						//				searchList.add(f.getName());
						//				Util.getConsoleLogger().info("f.getName(): " + f.getName() + " matched");
						if (sql_update_where.length() > 0){
							sql_update_where.append(" AND ");
						}
//						sql_update_where.append(f.getName() + " = :" + f.getName());
						sql_update_where.append(f.getName() + " = ?");
						pKeyFieldName = f.getName();
						pKeyFObj = fObj;
					}else {
						if (sql_update_set.length() > 0){
							sql_update_set.append(",");
						}
//						sql_update_set.append(f.getName() + " = :" + f.getName());
						sql_update_set.append(f.getName() + " = ?");
						
						aTmpFieldMap.put(f.getName(), fObj);
					}
					
//					aTmpFieldList.add(f);
					
				}
			}// end of for (Field f : fields)
			
			// 放入pkey資訊(要放入最後一個，因為WHERE在最後面)
			aTmpFieldMap.put(pKeyFieldName, pKeyFObj);
			
			if (Strings.isBlank(sql_update_where.toString())){
				throw new RuntimeException("WHERE語句為空  請提供搜尋條件");
			}
			
			sql_update.append("UPDATE ").append(aObj.getClass().getSimpleName())
			.append(" SET ").append(sql_update_set)
			.append(" WHERE ").append(sql_update_where.toString());
			
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return sql_update.toString();
	}

}
