package com.dao.dynamic;


import java.util.List;
// JAVA
import java.util.Map;

/**
 * 使用流程:
 * execute裡面: sqlProvider.getSql()-> updateParam
 * @author sam
 *
 */
public abstract class SqlProcessor {
	
	protected SqlProvider sqlProvider;
	protected ConProvider conProvider;
	
//	protected Map<Object,JPF> JSP_map = new HashMap<>(); // 讓使用者動態新增
//	protected abstract Object getTxCon();
//	protected abstract Object getCon();
//	protected abstract <T> Object getResult(Object aCon, T aObj); // 由CRUD子類別實作-為了動態回傳不同return type
	
	protected abstract <T> void updateParam(Object aQuery, T aObj, Map<String,Object> tmpFieldMap);
	public abstract <T> Object execute(Object aCon, T aObj);
	
	public <T> Object getResult(Object aCon, T aObj){
		return this.execute(aCon, aObj);
	}
	
	public <T> Object getResult(T aObj){
		return this.execute(this.conProvider.getCon(), aObj);
	}
	
	public <T> List<T> getResultAsList(Object aCon, T aObj){
//		System.out.println("aJPF.getActionType(): " + aJPF.getActionType().name());
		return (List<T>)this.execute(aCon, aObj);
	}
	
	public <T> List<T> getResultAsList(T aObj){
//		System.out.println("aJPF.getActionType(): " + aJPF.getActionType().name());
		return (List<T>)this.execute(this.conProvider.getCon(), aObj);
	}
	public Object getTxCon() {
		return this.conProvider.getTxCon();
	}
	public Object getCon() {
		return this.conProvider.getCon();
	}	
	
}
