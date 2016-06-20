package com.zhibitech.easyreport.tools.exceltool.impt;

import java.util.List;
import java.util.Map;

import com.zhibitech.easyreport.tools.exceltool.validate.ValidateResult;


public interface DataValidateConvert<T> {

	public ValidateResult validate(T entity, int realRow,
			Map<String, String> queryMap,Map<String,Integer> fileIndexMap);

	public T convertToEntity(String[] cellValues, ValidateResult result,
			String[][] beanDatas);
	
	public T saveEntity(T entity);

	public void saveEntity(List<T> data);

	public T updateEntity(T entity);

	public void updateEntity(List<T> data);


}
