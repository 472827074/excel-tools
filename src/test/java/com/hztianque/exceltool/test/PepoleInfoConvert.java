package com.hztianque.exceltool.test;

import java.util.Map;

import com.zhibitech.easyreport.tools.exceltool.impt.AbstractDataConverter;
import com.zhibitech.easyreport.tools.exceltool.util.ValidateUtils;
import com.zhibitech.easyreport.tools.exceltool.validate.ValidateResult;

public class PepoleInfoConvert extends AbstractDataConverter<PeopleInfo>{

	public PepoleInfoConvert(){
		//this.setValidateUtils(new ValidateUtils());
	}

	@Override
	public ValidateResult validate(PeopleInfo entity, int realRow,
			Map<String, String> queryMap, Map<String, Integer> fileIndexMap) {
		ValidateResult vr = new ValidateResult();
		if("张三1".equals(entity.getName())){
			vr.addErrorMessage(fileIndexMap.get("name"), "姓名格式不正确");
		}
		return vr;
	}

}
