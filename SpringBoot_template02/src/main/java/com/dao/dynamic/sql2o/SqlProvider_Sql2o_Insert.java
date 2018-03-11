package com.dao.dynamic.sql2o;



// JAVA
import java.lang.reflect.Field;
import java.util.Map;

import com.dao.annotation.FieldNotForDaoSql;
import com.dao.annotation.PrimaryKey;
import com.dao.dynamic.SqlProvider;

// SELF

public class SqlProvider_Sql2o_Insert extends SqlProvider{

	public SqlProvider_Sql2o_Insert() {
		super.actionType = ACTION.C;
	}
	
	@Override
	public <T> String getSql(T aObj, Map<String,Object> aTmpFieldMap) {
//		String sql = Util.getInsertSqlNew(aTableName, aObj);
		StringBuilder sql_insert = new StringBuilder();
		//		"INSERT INTO " + aTableName + " ";
		StringBuilder sql_insert_col = new StringBuilder();
		//" ([Email] ,[Phone] ,[Name],[Message],[TenantID]) ";
		StringBuilder sql_insert_val = new StringBuilder();
		//" VALUES (:aEmail,:aPhone,:aName,:aMessage,:aTenantID)"; 
		
		Field[] fields = aObj.getClass().getDeclaredFields();
//		Util.getConsoleLogger().info("fields.length: " + fields.length);
		
		/** 動態生成WHERE語句(注意: 僅對String型態有效) **/
		for (Field f : fields){
			f.setAccessible(true); // prevent from error of accessing "private" fields
			
			// 若此屬性被標記為不要放入sql指令中，則跳過
			if (f.isAnnotationPresent(FieldNotForDaoSql.class)) {
//				System.out.println("findAll NotForDaoSql annotation is present");
				continue;
			}else if (f.isAnnotationPresent(PrimaryKey.class)) {
				System.out.println("insert PrimaryKey is skipped in insert method - field: " + f.getName());
				continue;
			}			
			
//			Util.getConsoleLogger().info("f.getName(): " + f.getName());
			Object fObj;
			try {
				fObj = f.get(aObj);
				

				
				if (fObj != null){
//					Util.getConsoleLogger().info("fObj: " + fObj);
					if (sql_insert_col.length() > 0){
						sql_insert_col.append(",");
					}
					sql_insert_col.append(f.getName());
					
					if (sql_insert_val.length() > 0){
						sql_insert_val.append(",");
					}
					sql_insert_val.append(":" + f.getName());
					
//					aTmpFieldList.add(f);
					aTmpFieldMap.put(f.getName(), fObj);
				}
				
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}// end of for (Field f : fields)
		
		sql_insert.append("INSERT INTO ").append(aObj.getClass().getSimpleName())
					.append(" (").append(sql_insert_col.toString()).append(") ")
					.append(" VALUES (").append(sql_insert_val.toString()).append(")");
		
		return sql_insert.toString();
	}

}
