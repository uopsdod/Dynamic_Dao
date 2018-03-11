package com.dao.dynamic.sql2o;


// JAVA
import java.lang.reflect.Field;
import java.util.Map;

import com.dao.annotation.Starttime;

// SELF

public class SqlProvider_Sql2o_FindAllWithDate extends SqlProvider_Sql2o_FindAll{
	
	String starttime; 
	String endtime;
	
	public SqlProvider_Sql2o_FindAllWithDate(String starttime, String endtime) {
		this.starttime = starttime;
		this.endtime = endtime;
	}
	
	@Override
	public <T> String getSql(T aObj, Map<String,Object> aTmpFieldMap) {
		String sql_where = super.getSql(aObj, aTmpFieldMap);
		StringBuilder sql_where_date = new StringBuilder(sql_where);
		Field[] fields = aObj.getClass().getDeclaredFields();
		for (Field f : fields){
			f.setAccessible(true); // prevent from error of accessing "private" fields
			try {
				
				/** 塞選日期使用 **/
				if (f.isAnnotationPresent(Starttime.class)) {
					System.out.println("getSelectWhereSqlNewWithDate Starttime annotation is present");
					if (!sql_where.contains("WHERE")){
						sql_where_date.append(" WHERE ");
					}else{
						sql_where_date.append(" AND ");
					}
					
					/** 注意此處邏輯: 是去拿table資料列的欄位資訊出來跟此兩input做比較 **/ // 此處設計仍有安全性問題
//					sql_where_date.append("CAST("+ f.getName() + " as datetime) BETWEEN '" + this.starttime + "' and '" + this.endtime +"'");
					sql_where_date.append("CAST("+ f.getName() + " as datetime) BETWEEN " +  ":" + "starttime" + " and " + ":" + "endtime");
					aTmpFieldMap.put("starttime", this.starttime);
					aTmpFieldMap.put("endtime", this.endtime);
					continue;
				}
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		}// end of for (Field f : fields)
		
		return sql_where_date.toString();
	}

}
