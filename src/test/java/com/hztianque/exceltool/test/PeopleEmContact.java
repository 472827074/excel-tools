package com.hztianque.exceltool.test;

import com.zhibitech.easyreport.tools.exceltool.BaseEntity;

/** 
* ClassName: PeopleEmContact <br/>
* Description: 紧急联系人表people_emergency_contact <br/>
* @author     <br/>
*/ 

public class PeopleEmContact extends BaseEntity{

	/**
	 * 紧急联系人
	 */
	private String name;
	/**
	 * 与本人关系
	 */
	private String relation;
	/**
	 * 联系方式
	 */
	private String contactWay;
	/**
	 * 人员基本信息id
	 */
	private String peopleInfo;
	
	/** 排序 */
	private int seq;

	public void setName(String name){
		this.name = name;
	}
	/**
	 * 紧急联系人
	 */
	public String getName(){
		return this.name;
	}
	public void setRelation(String relation){
		this.relation = relation;
	}
	/**
	 * 与本人关系
	 */
	public String getRelation(){
		return this.relation;
	}
	public void setContactWay(String contactWay){
		this.contactWay = contactWay;
	}
	/**
	 * 联系方式
	 */
	public String getContactWay(){
		return this.contactWay;
	}
	public void setPeopleInfo(String peopleInfo){
		this.peopleInfo = peopleInfo;
	}
	/**
	 * 人员基本信息id
	 */
	public String getPeopleInfo(){
		return this.peopleInfo;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
}