package com.hztianque.exceltool.test;

import com.zhibitech.easyreport.tools.exceltool.BaseEntity;

public class UserInfo extends BaseEntity{
	private String name;
	private int age;
	private String interest;
	private String address;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getInterest() {
		return interest;
	}
	public void setInterest(String interest) {
		this.interest = interest;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

}
