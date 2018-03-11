package com.dao.dynamic.jdbc;


import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
// JAVA
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.bean.tblSystemMonitor;

// SELF

// THIRD PARTY
import com.dao.dynamic.SqlProcessor;
import com.dao.dynamic.SqlProvider;


public class SqlProcessor_JDBC_Base extends SqlProcessor{
	
	public SqlProcessor_JDBC_Base() {
		
	}
	
	public SqlProcessor_JDBC_Base(SqlProvider aSqlProvider) {
		super.sqlProvider = aSqlProvider;
	}
	
//	Sql2OService
//	private Sql2o sql2oForAll = Sql2OService.getSQL2OInstance();
	public <T> Object execute(Object aCon, T aObj) {
		Object result = null;
		
		try {
	//		/** 拿取sql **/
			Map<String, Object> tmpFieldMap = new LinkedHashMap<>(); // for performance
			String sql = super.sqlProvider.getSql(aObj, tmpFieldMap);
			System.out.println(super.sqlProvider.getClass().getSimpleName() + " sql_result: " + sql);
			/** 建立Query物件 **/
			PreparedStatement query = ((Connection)aCon).prepareStatement(sql.toString());
			/** 更新Query參數 **/
			this.updateParam(query, aObj, tmpFieldMap);
			/** 執行 **/
			switch(super.sqlProvider.getActionType()) {
			case R:
//			result = query.executeAndFetch(aObj.getClass());
				ResultSet rs = query.executeQuery();
				ResultSetMetaData rsmd = rs.getMetaData();
				List<tblSystemMonitor> tblSystemMonitor_list = new ArrayList<>();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					String name = rsmd.getColumnName(i);
					System.out.println("rs_name: " + name);
				}
				System.out.println("rs.getFetchSize(): " + rs.getFetchSize());
				try {	
					// 開始拿結果
					while(rs.next()) {
						tblSystemMonitor myTblSystemMonitor = new tblSystemMonitor();
						// 開始看總共有哪些欄位要取
						for (int i = 1; i <= rsmd.getColumnCount(); i++) {
							String name = rsmd.getColumnName(i);
							System.out.println("rs_name: " + name);
							// 找對應的欄位去塞值
							Class myClass = myTblSystemMonitor.getClass();
//							ParameterizedTypeImpl type = new ParameterizedTypeImpl(myClass, new Class[]{myClass});
//							System.out.println("type_name: " + type.getOwnerType().getTypeName()); // X 
//							System.out.println("type_name: " + type.getRawType().getTypeName()); // O 
							Field f = myClass.getDeclaredField(name);
							f.setAccessible(true);
							System.out.println("f.getType(): " + f.getType().getSimpleName());
							if (Integer.class.isAssignableFrom(f.getType())) {
								System.out.println("Integer type");
								Integer val = rs.getInt("status");
								f.set(myTblSystemMonitor, val);
							}else if (String.class.isAssignableFrom(f.getType())) {
								System.out.println("String type");
								String val = rs.getString("status");
								f.set(myTblSystemMonitor, val);
							} // 請在持續往下
							System.out.println("end this round ****************");
							
//							Field[] fields = tblSystemMonitor.getClass().getDeclaredFields();
//							/** 動態生成WHERE語句(注意: 僅對String型態有效) **/
//							for (Field f : fields){
//								f.setAccessible(true);
//								if (name.equalsIgnoreCase(f.getName())) {
//									System.out.println("f.getType(): " + f.getType().getSimpleName());
//									if (Integer.class.isAssignableFrom(f.getType())) {
//										System.out.println("Integer type");
//										Integer val = rs.getInt("status");
//										f.set(tblSystemMonitor, val);
//									}else if (String.class.isAssignableFrom(f.getType())) {
//										System.out.println("String type");
//										String val = rs.getString("status");
//										f.set(tblSystemMonitor, val);
//									} // 請在持續往下家
//								}
//							} // end of for 			
						} // end of for
						
						// 最後放入list
						tblSystemMonitor_list.add(myTblSystemMonitor);
					} // end of while
				} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("tblSystemMonitor_list.size(): " + tblSystemMonitor_list.size());
				result = tblSystemMonitor_list;
				break;
//		case C:
//			result = query.executeUpdate().getKey(); // pk值
//			break;
//		case U:
//		case D:
//			result = query.executeUpdate().getResult(); // 更新比數
//			break;
			default:
				break;
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} // 關鍵
		return result;
	}
	
	final public <T> void updateParam(Object aQuery, T aObj, Map<String,Object> tmpFieldMap) {
		int index = 1;
		for (Entry<String, Object> map : tmpFieldMap.entrySet()) {
			try {
				((PreparedStatement)aQuery).setObject(index, map.getValue());
				index += 1;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		};
	}
	
//	public static <T> T fromJsonObject(JSONObject formParamsJsonObj , Class<T> clazz) {
//	      Type type = new ParameterizedTypeImpl(clazz, new Class[]{clazz});
//	      Gson gson=new Gson();
//	      return gson.fromJson(formParamsJsonObj.toString(), type);
//	  }

}
