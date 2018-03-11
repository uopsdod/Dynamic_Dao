package com.dao.dynamic.jdbc;


// JAVA

// SELF
import com.dao.dynamic.Assembler;

// THIRD PARTY
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * ( 這邊就是客戶端了，各自去用 ，已非開發設計者的責任範圍)
 * 這個class不一定需要，可以直接在外面使用new Assembler(new ConProvider_Sql2o())
 * 
 * @author sam
 */
public class Assembler_JDBC extends Assembler{
//	Info360DaoBase info360Dao = null;
	
	public enum ACTION_JDBC {
		C,R,U,D // 客戶端自行新增 
	}
	
    public Assembler_JDBC() {
    	super(new ConProvider_JDBC());
		this.load();
    }
	
//	public Assembler_JDBC() {
////		super(new JPF_Sql2oBase(), new ConProvider_Sql2o());
//		super(new ConProvider_JDBC());
//		this.load();
//	}

	private void load() {
		this.SqlProcessor_map.put(ACTION_JDBC.R, new SqlProcessor_JDBC_Base(new SqlProvider_JDBC_FindAll()));
//		this.SqlProcessor_map.put(ACTION_SQL2O.C, new SqlProcessor_Sql2o_Base(new SqlProvider_Sql2o_Insert()));
//		this.SqlProcessor_map.put(ACTION_SQL2O.U, new SqlProcessor_Sql2o_Base(new SqlProvider_Sql2o_Update()));
//		this.SqlProcessor_map.put(ACTION_SQL2O.D, new SqlProcessor_Sql2o_Base(new SqlProvider_Sql2o_Delete()));
	}
	
//	public <T> List<T> findAllByDate(T aObj, String aStarttime, String aEndtime) {
//		SqlProcessor_Sql2o_Base jpf_Sql2oBase = new SqlProcessor_Sql2o_Base(new SqlProvider_Sql2o_FindAllWithDate(aStarttime, aEndtime)); // 一次性的使用
////		JPF_Sql2oFindAllWithDate myJPF_Sql2oFindAllWithDate = new SqlProviderFindAllWithDate(aStarttime, aEndtime);
////		myJPF_Sql2oDao.getInfo360Dao().getJPF_Map().put(JPF_Sql2oFindAllWithDate.class.getSimpleName(), myJPF_Sql2oFindAllWithDate);
////		JPF_Sql2oFindAllWithDate jpf_r_date = (JPF_Sql2oFindAllWithDate)myJPF_Sql2oDao.getInfo360Dao().getJPF_Map().get(JPF_Sql2oFindAllWithDate.class.getSimpleName());
////		System.out.println("myJPF_Sql2oFindAllWithDate.getActionType(): " + myJPF_Sql2oFindAllWithDate.getActionType());
//		List<T> result = this.getResultAsList(this.getCon(), aObj, jpf_Sql2oBase);
////		Util.getConsoleLogger().info("findAllByDate() result_findAll_date: " + result);
//		return (List<T>)result;
//	}

}
