package com.dao.dynamic;


// JAVA
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Facade Object for this framework
 * @author sam
 */
public class Assembler {
	
//	private JPF jpf;
	private ConProvider conProvider;
	protected Map<Object,SqlProcessor> SqlProcessor_map = new HashMap<>(); // 讓使用者動態新增
	
//	private static Sql2o sql2oForAll = Sql2OService.getSQL2OInstance();
	
	public Assembler() {}
	
	public Assembler(ConProvider conProvider) {
		super();
//		this.jpf = javaPersistenFramwork;
		this.conProvider = conProvider;
	}

	public Object getTxCon() {
		return this.conProvider.getTxCon();
	}
	public Object getCon() {
		return this.conProvider.getCon();
	}

//	public <T> Object getResult(Object aCon, T aObj, JPF aJPF){
//		Object result = null; 
////		JPF jpf2 = this.getJPF_Map().get(aKey);
//		result = aJPF.execute(aCon, aObj, aJPF.getActionType());
//		return result;
//	}
	
//	public <T> Object getResult(Object aCon, T aObj, Object aKey){
//		Object result = null; 
//		JPF jpf = this.getJPF_Map().get(aKey);
//		result = jpf.execute(aCon, aObj, jpf.getActionType());
//		return result;
//	}
	
	public <T> Object getResult(Object aCon, T aObj, SqlProcessor aJPF){
		return aJPF.execute(aCon, aObj);
	}
	
	public <T> List<T> getResultAsList(Object aCon, T aObj, SqlProcessor aJPF){
//		System.out.println("aJPF.getActionType(): " + aJPF.getActionType().name());
		return (List<T>)aJPF.execute(aCon, aObj);
	}

	public Map<Object, SqlProcessor> getSqlProcessor_map() {
		return SqlProcessor_map;
	}

	public void setSqlProcessor_map(Map<Object, SqlProcessor> sqlProcessor_map) {
		SqlProcessor_map = sqlProcessor_map;
	}

	
}
