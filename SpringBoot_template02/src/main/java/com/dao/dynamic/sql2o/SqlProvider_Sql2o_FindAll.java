package com.dao.dynamic.sql2o;


// JAVA
import java.lang.reflect.Field;
import java.util.Date;
import java.util.Map;

// SELF

// THIRD PARTY
import org.apache.logging.log4j.util.Strings;

import com.dao.annotation.FieldNotForDaoSql;
import com.dao.dynamic.SqlProvider;


public class SqlProvider_Sql2o_FindAll extends SqlProvider{

	public SqlProvider_Sql2o_FindAll() {
		super.actionType = ACTION.R;
	}
	
	@Override
	public <T> String getSql(T aObj, Map<String,Object> aTmpFieldMap) {
		
//		String sql_where = null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * ");
		sql.append(" FROM " + aObj.getClass().getSimpleName());
		
		/** 拿取where子句 **/
		Date startMS = new java.util.Date();
		try {
//			System.out.println("aMethod: " + aMethod.getName()); // 使用哪個method請看上層呼叫方法
//			Object sql_where_obj = aMethod.invoke(new Util(), aObj, aStarttime, aEndtime);
//			startMS = Util.printPeriod(startMS, "IxnMap_where子句_耗時");
//			sql_where = sql_where_obj.toString();
//			sql_where = Util.getSelectWhereSqlNew(aObj);
			StringBuilder sql_where = new StringBuilder();
			Field[] fields = aObj.getClass().getDeclaredFields();
//			Util.getConsoleLogger().info("fields.length: " + fields.length);
			
			/** 動態生成WHERE語句(注意: 僅對String型態有效) **/
			for (Field f : fields){
				f.setAccessible(true); // prevent from error of accessing "private" fields
				
				// 若此屬性被標記為不要放入sql指令中，則跳過
				if (f.isAnnotationPresent(FieldNotForDaoSql.class)) {
//					System.out.println("getSelectWhereSqlNew NotForDaoSql annotation is present");
					continue;
				}
				
//				Util.getConsoleLogger().info("getSelectWhereSqlNew f.getName(): " + f.getName());
				Object fObj;
				try {
					fObj = f.get(aObj);
//					String fVal = "";
					
					/** 此段有可能可以去掉,若之後有非字串欄位的需求,可試試看 **/
//					if (fObj instanceof String){
//						fVal = String.class.cast(fObj);
//					}
					
					if (fObj != null){
						if (Strings.isEmpty(sql_where)){
							sql_where.append(" WHERE ");
						}else{
							sql_where.append(" AND ");
						}
						
						sql_where.append( f.getName() + " = :" + f.getName() );
//						sql_where.append( f.getName() + " = ? "); 
						
//						aTmpFieldList.add(f);
						aTmpFieldMap.put(f.getName(), fObj);
					}
					
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}// end of for (Field f : fields)
//			Util.getFileLogger().info("getSelectWhereSqlNew sql_where: " + sql_where);		
//			Util.getConsoleLogger().info("getSelectWhereSqlNew sql_where: " + sql_where);		
			
			if (Strings.isNotBlank(sql_where.toString())){
				sql.append(sql_where);
			}
//			System.out.println("invoke: " + sql_where);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sql.toString();
	}

}
