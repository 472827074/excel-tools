package com.zhibitech.easyreport.tools.exceltool;

import java.util.Date;

public class BaseEntity {

	private String id;
	private Date createTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
