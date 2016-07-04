package com.zhibitech.easyreport.tools.exceltool.impt;

import java.util.Map;

import com.zhibitech.easyreport.tools.exceltool.validate.ValidateResult;


public interface DataValidateConvert<T> {

	public ValidateResult validate(T entity, int realRow,
			Map<String, String> queryMap,Map<String,Integer> fileIndexMap);

	public T convertToEntity(String[] cellValues, ValidateResult result,
			String[][] beanDatas);


}
