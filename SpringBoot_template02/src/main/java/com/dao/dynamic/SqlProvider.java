package com.dao.dynamic;


// JAVA
import java.util.Map;

public abstract class SqlProvider {
	public enum ACTION{
		C,R,U,D
	}
	protected ACTION actionType;

	public abstract <T> String getSql(T aObj, Map<String,Object> tmpFieldMap); // 由CRUD子類別實作
	
	public ACTION getActionType() {
		return this.actionType;
	}
	
}
