package com.hztianque.exceltool.test;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zhibitech.easyreport.tools.exceltool.BaseEntity;
/** 
* ClassName: PeopleInfo <br/>
* Description: 人员基本信息表 <br/>
* @author     <br/>
*/ 

public class PeopleInfo extends BaseEntity{
	
	/**
	 * 紧急联系人
	 */
	private List<PeopleEmContact> peopleEmContactList = new ArrayList<PeopleEmContact>();
	{
		System.out.println(111);
		peopleEmContactList.add(new PeopleEmContact());
	}
	
	/**
	 * 所属网格id
	 */
	private String orgId;
	/**
	 * 所属网格code
	 */
	private String orgCode;
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getPensionType() {
		return pensionType;
	}
	public void setPensionType(String pensionType) {
		this.pensionType = pensionType;
	}
	public String getSelfCareType() {
		return selfCareType;
	}
	public void setSelfCareType(String selfCareType) {
		this.selfCareType = selfCareType;
	}
	public String getCognitionType() {
		return cognitionType;
	}
	public void setCognitionType(String cognitionType) {
		this.cognitionType = cognitionType;
	}
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 身份证
	 */
	private String idcardNum;
	/**
	 * 年龄（附加字段，不是数据库字段）
	 */
	private String age;
	/**
	 * 性别
	 */
	private String gender;
	/**
	 * 民族
	 */
	private String nation;
	/**
	 * 出生日期
	 */
	private Date birthday;
	/**
	 * 籍贯
	 */
	private String placeOrigin;
	/**
	 * 文化程度
	 */
	private String schooling;
	/**
	 * 婚姻状况
	 */
	private String maritalState;
	/**
	 * 居住情况
	 */
	private String homeEnv;
	/**
	 * 手机
	 */
	private String mobileNumber;
	/**
	 * 住宅电话
	 */
	private String telephone;
	/**
	 * 特殊贡献
	 */
	private String specialContribution;
	/**
	 * 申请服务类型
	 */
	private String pensionType;
	
	/**
	 * 生活自理能力类型(1正常、2轻度依赖、3中度依赖、4重度依赖)
	 */
	private String selfCareType;
	/**
	 * 认知能力类型(1正常、2轻度痴呆、3中度痴呆、4重度痴呆)
	 */
	private String cognitionType;
	/**
	 * 户籍地-区
	 */
	private String district;
	/**
	 * 户籍地-乡镇
	 */
	private String town;
	/**
	 * 户籍地-社区
	 */
	private String community;
	/**
	 * 户籍地详细地址
	 */
	private String address;
	/**
	 * 现居地址的详细地址
	 */
	private String resideAddress;
	/**
	 * 现居住地址-区
	 */
	private String resideDistrict;
	/**
	 * 现居住地址-乡镇
	 */
	private String resideTown;
	/**
	 * 现居住地址-社区
	 */
	private String resideCommunity;
	/**
	 * 社区电话
	 */
	private String communityContactWay;
	/**
	 * 服务时间
	 */
	private Integer serviceTime;
	/**
	 * 养老补贴
	 */
	private String pensionSubsidiesType;

	/**
	 * 出生日期-年份
	 */
	private Integer birthdayYear;
	/**
	 * 注销
	 */
	private Integer cancel;
	/**
	 * 注销原因
	 */
	private String cancelReason;
	/**
	 * 注销时间
	 */
	private Date cancelDate;
	
	/** 补贴开始时间 */
	private Date effectDate;
	/** 批准金额 */
	private Double approvalAmount;
	/** 评估状态（0是未评估过，1是评估中，2是已评估）     */
	private Integer assessmentStatus;
	/**
	 * 服务机构ID
	 */
	private String serviceAgenciesId;
	/**
	 * 护理等级
	 */
	private String careLevel;
	/**
	 * 残障情况
	 */
	private int disability;
	/**
	 * 重大疾病
	 */
	private int diseases;
	/**
	 * 经济条件
	 */
	private String economicConditions;
	/**
	 * 居住环境
	 */
	private String livingConditions;
	/**
	 * 是否本市（1是本市，0非本市）
	 */
	private String isThisCity;
	
	
	public void setOrgCode(String orgCode){
		this.orgCode = orgCode;
	}
	/**
	 * 所属网格code
	 */
	public String getOrgCode(){
		return this.orgCode;
	}
	public void setName(String name){
		this.name = name;
	}
	/**
	 * 姓名
	 */
	public String getName(){
		return this.name;
	}
	public void setIdcardNum(String idcardNum){
		this.idcardNum = idcardNum;
	}
	/**
	 * 身份证
	 */
	public String getIdcardNum(){
		return this.idcardNum;
	}
	public void setGender(String gender){
		this.gender = gender;
	}
	/**
	 * 性别
	 */
	public String getGender(){
		return this.gender;
	}
	public void setNation(String nation){
		this.nation = nation;
	}
	/**
	 * 民族
	 */
	public String getNation(){
		return this.nation;
	}
	public void setBirthday(Date birthday){
		this.birthday = birthday;
	}
	/**
	 * 出生日期
	 */
	public Date getBirthday(){
		return this.birthday;
	}
	public void setPlaceOrigin(String placeOrigin){
		this.placeOrigin = placeOrigin;
	}
	/**
	 * 籍贯
	 */
	public String getPlaceOrigin(){
		return this.placeOrigin;
	}
	public void setSchooling(String schooling){
		this.schooling = schooling;
	}
	/**
	 * 文化程度
	 */
	public String getSchooling(){
		return this.schooling;
	}
	public void setMaritalState(String maritalState){
		this.maritalState = maritalState;
	}
	/**
	 * 婚姻状况
	 */
	public String getMaritalState(){
		return this.maritalState;
	}
	public void setHomeEnv(String homeEnv){
		this.homeEnv = homeEnv;
	}
	/**
	 * 居住情况
	 */
	public String getHomeEnv(){
		return this.homeEnv;
	}
	public void setMobileNumber(String mobileNumber){
		this.mobileNumber = mobileNumber;
	}
	/**
	 * 手机
	 */
	public String getMobileNumber(){
		return this.mobileNumber;
	}
	public void setTelephone(String telephone){
		this.telephone = telephone;
	}
	/**
	 * 住宅电话
	 */
	public String getTelephone(){
		return this.telephone;
	}
	public void setSpecialContribution(String specialContribution){
		this.specialContribution = specialContribution;
	}
	/**
	 * 特殊贡献
	 */
	public String getSpecialContribution(){
		return this.specialContribution;
	}
	public void setDistrict(String district){
		this.district = district;
	}
	/**
	 * 户籍地-区
	 */
	public String getDistrict(){
		return this.district;
	}
	public void setTown(String town){
		this.town = town;
	}
	/**
	 * 户籍地-乡镇
	 */
	public String getTown(){
		return this.town;
	}
	public void setCommunity(String community){
		this.community = community;
	}
	/**
	 * 户籍地-社区
	 */
	public String getCommunity(){
		return this.community;
	}
	public void setResideDistrict(String resideDistrict){
		this.resideDistrict = resideDistrict;
	}
	/**
	 * 现居住地址-区
	 */
	public String getResideDistrict(){
		return this.resideDistrict;
	}
	public void setResideTown(String resideTown){
		this.resideTown = resideTown;
	}
	/**
	 * 现居住地址-乡镇
	 */
	public String getResideTown(){
		return this.resideTown;
	}
	public void setResideCommunity(String resideCommunity){
		this.resideCommunity = resideCommunity;
	}
	/**
	 * 现居住地址-社区
	 */
	public String getResideCommunity(){
		return this.resideCommunity;
	}
	public void setCommunityContactWay(String communityContactWay){
		this.communityContactWay = communityContactWay;
	}
	/**
	 * 社区电话
	 */
	public String getCommunityContactWay(){
		return this.communityContactWay;
	}
	public List<PeopleEmContact> getPeopleEmContactList() {
		return peopleEmContactList;
	}
	public void setPeopleEmContactList(List<PeopleEmContact> peopleEmContactList) {
		this.peopleEmContactList = peopleEmContactList;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getResideAddress() {
		return resideAddress;
	}
	public void setResideAddress(String resideAddress) {
		this.resideAddress = resideAddress;
	}
	public Integer getServiceTime() {
		return serviceTime;
	}
	public void setServiceTime(Integer serviceTime) {
		this.serviceTime = serviceTime;
	}
	public String getPensionSubsidiesType() {
		return pensionSubsidiesType;
	}
	public void setPensionSubsidiesType(String pensionSubsidiesType) {
		this.pensionSubsidiesType = pensionSubsidiesType;
	}
	
	public Integer getBirthdayYear() {
		return birthdayYear;
	}
	public void setBirthdayYear(Integer birthdayYear) {
		this.birthdayYear = birthdayYear;
	}
	public Integer getCancel() {
		return cancel;
	}
	public void setCancel(Integer cancel) {
		this.cancel = cancel;
	}
	public String getCancelReason() {
		return cancelReason;
	}
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	public Date getCancelDate() {
		return cancelDate;
	}
	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}
	public Date getEffectDate() {
		return effectDate;
	}
	public void setEffectDate(Date effectDate) {
		this.effectDate = effectDate;
	}
	public Double getApprovalAmount() {
		return approvalAmount;
	}
	public void setApprovalAmount(Double approvalAmount) {
		this.approvalAmount = approvalAmount;
	}
	public Integer getAssessmentStatus() {
		return assessmentStatus;
	}
	public void setAssessmentStatus(Integer assessmentStatus) {
		this.assessmentStatus = assessmentStatus;
	}
	public String getServiceAgenciesId() {
		return serviceAgenciesId;
	}
	public void setServiceAgenciesId(String serviceAgenciesId) {
		this.serviceAgenciesId = serviceAgenciesId;
	}
	public String getCareLevel() {
		return careLevel;
	}
	public void setCareLevel(String careLevel) {
		this.careLevel = careLevel;
	}

	public int getDisability() {
		return disability;
	}
	public void setDisability(int disability) {
		this.disability = disability;
	}
	public int getDiseases() {
		return diseases;
	}
	public void setDiseases(int diseases) {
		this.diseases = diseases;
	}
	public String getEconomicConditions() {
		return economicConditions;
	}
	public void setEconomicConditions(String economicConditions) {
		this.economicConditions = economicConditions;
	}
	public String getLivingConditions() {
		return livingConditions;
	}
	public void setLivingConditions(String livingConditions) {
		this.livingConditions = livingConditions;
	}
	public String getIsThisCity() {
		return isThisCity;
	}
	public void setIsThisCity(String isThisCity) {
		this.isThisCity = isThisCity;
	}

}