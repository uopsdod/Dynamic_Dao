package com.dao.dynamic.jdbc;

//JAVA
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

// SELF
import com.dao.dynamic.SqlProcessor;
import com.dao.dynamic.SqlProvider;

// THIRD PARTY


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
			PreparedStatement query = ((Connection)aCon).prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			/** 更新Query參數 **/
			this.updateParam(query, aObj, tmpFieldMap);
			/** 執行 **/
			switch(super.sqlProvider.getActionType()) {
			case R:
//			result = query.executeAndFetch(aObj.getClass());
				ResultSet rs = query.executeQuery();
				ResultSetMetaData rsmd = rs.getMetaData();
				List<T> resultList = new ArrayList<>();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					String name = rsmd.getColumnName(i);
					System.out.println("rs_name: " + name);
				}
				System.out.println("rs.getFetchSize(): " + rs.getFetchSize());
				try {	
					// 拿取原物件class
					Constructor<T> ctor = (Constructor<T>) aObj.getClass().getConstructor();
			        
					// 開始拿結果
					while(rs.next()) {
//						tblSystemMonitor myTblSystemMonitor = new tblSystemMonitor();
						// 建立物件
						T instance = ctor.newInstance();
						// 開始看總共有哪些欄位要取
						for (int i = 1; i <= rsmd.getColumnCount(); i++) {
							String name = rsmd.getColumnName(i);
							System.out.println("rs_name: " + name);
							// 找對應的欄位去塞值
//							Class myClass = myTblSystemMonitor.getClass();
							Class myClass = instance.getClass();
//							ParameterizedTypeImpl type = new ParameterizedTypeImpl(myClass, new Class[]{myClass});
//							System.out.println("type_name: " + type.getOwnerType().getTypeName()); // X 
//							System.out.println("type_name: " + type.getRawType().getTypeName()); // O 
							Field f = myClass.getDeclaredField(name);
							f.setAccessible(true);
							System.out.println("f.getType(): " + f.getType().getSimpleName());
							if (Integer.class.isAssignableFrom(f.getType())) {
								System.out.println("Integer type");
								Integer val = rs.getInt("status");
								f.set(instance, val);
							}else if (String.class.isAssignableFrom(f.getType())) {
								System.out.println("String type");
								String val = rs.getString("status");
								f.set(instance, val);
							} // 請在持續往下
							System.out.println("end this round ****************");
						} // end of for
						
						// 最後放入list
						resultList.add(instance);
					} // end of while
				} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("tblSystemMonitor_list.size(): " + resultList.size());
				result = resultList;
				break;
		case C:
			query.executeUpdate();
			ResultSet rs_c = query.getGeneratedKeys(); // pk值
//			ResultSet rs = pInsertOid.getGeneratedKeys();
			if (rs_c.next()) {
			  int newId = rs_c.getInt(1);
			  result = newId;
//			  oid.setId(newId);
			}
			break;
		case U:
		case D:
			result = query.executeUpdate(); // 更新比數
			break;
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
