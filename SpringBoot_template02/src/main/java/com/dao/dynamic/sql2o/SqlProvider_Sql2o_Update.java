package com.dao.dynamic.sql2o;



// JAVA
import java.lang.reflect.Field;
import java.util.Map;

// SELF

// THIRD PARTY
import org.apache.logging.log4j.util.Strings;

import com.dao.annotation.PrimaryKey;
import com.dao.dynamic.SqlProvider;

public class SqlProvider_Sql2o_Update extends SqlProvider{

	public SqlProvider_Sql2o_Update() {
		super.actionType = ACTION.U;
	}
	
	@Override
	public <T> String getSql(T aObj, Map<String,Object> aTmpFieldMap) {
		StringBuilder sql_update = new StringBuilder();
		StringBuilder sql_update_set = new StringBuilder();
		StringBuilder sql_update_where = new StringBuilder();
		/** 看有那些欄位需要當作搜尋值 **/
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
						sql_update_where.append(f.getName() + " = :" + f.getName());
					}else {
						if (sql_update_set.length() > 0){
							sql_update_set.append(",");
						}
						sql_update_set.append(f.getName() + " = :" + f.getName());
					}
					
//					aTmpFieldList.add(f);
					aTmpFieldMap.put(f.getName(), fObj);
				}
			}// end of for (Field f : fields)
			
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
