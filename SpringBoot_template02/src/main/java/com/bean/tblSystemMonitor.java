package com.bean;

import com.dao.annotation.PrimaryKey;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class tblSystemMonitor{
	
	@PrimaryKey
	private Integer dbid;
	private String name;
	private String status;
	private Long timestamp;
	public Integer getDbid() {
		return dbid;
	}
	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	
}
