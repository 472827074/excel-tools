//package com.hztianque.exceltool.test;
//
//import java.util.Map;
//
//import com.hztianque.exceltool.impt.AbstractDataConverter;
//import com.hztianque.exceltool.util.ValidateUtils;
//import com.hztianque.exceltool.validate.ValidateResult;
//
//public class UserInfoConvert extends AbstractDataConverter<UserInfo>{
//
//	@Override
//	public ValidateResult validate(UserInfo entity, int realRow,
//			Map<String, String> queryMap,Map<String ,Integer> fileIndexMap) {
//		ValidateResult vr = new ValidateResult();
//		if("张三1".equals(entity.getName())){
//			vr.addErrorMessage(fileIndexMap.get("name"), "姓名格式不正确");
//		}
//		return vr;
//	}
//	public UserInfoConvert(){
//		this.setValidateUtils(new ValidateUtils());
//	}
//
//	
//}
