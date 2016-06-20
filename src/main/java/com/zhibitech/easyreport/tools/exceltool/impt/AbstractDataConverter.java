package com.zhibitech.easyreport.tools.exceltool.impt;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.zhibitech.easyreport.tools.exceltool.util.ValidateUtils;
import com.zhibitech.easyreport.tools.exceltool.validate.ValidateResult;
import com.zhibitech.framework.core.exception.ServiceException;

public abstract class AbstractDataConverter<T> implements DataValidateConvert<T> {
	@Autowired
	private ValidateUtils validateUtils;
	@Autowired
	ExcelImportHelper excelImportHelper;
	public ValidateUtils getValidateUtils() {
		return validateUtils;
	}
//    //非spring环境下的测试使用
//	public void setValidateUtils(ValidateUtils validateUtils) {
//		this.validateUtils = validateUtils;
//	}
    //获取泛型类型
	private Class<T> getRuntimeType() {
		Class<T> entityClass = null;
		Class c = getClass();
		Type type = c.getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			Type[] parameterizedType = ((ParameterizedType) type)
					.getActualTypeArguments();
			entityClass = (Class<T>) parameterizedType[0];
		}
		return entityClass;
	}

	//通过excel隐藏的字段值及数据幻化成对应的实体对象;
	@Override
	public T convertToEntity(String[] cellValues, ValidateResult result,
			String[][] beanDatas) {
		//去除列值中的空格;
		cellValues = validateUtils.cellValueTrim(cellValues);
		T returnObject = null;
		try {
			returnObject = getRuntimeType().newInstance();
			excelImportHelper.convertEntity(beanDatas, cellValues,returnObject, result);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("excel数据转换成对应entity发生异常");
		}

		return returnObject;
	}
	public T saveEntity(T entity){
		return entity;
	}

	public void saveEntity(List<T> data){
		
	}

	public T updateEntity(T entity){
		return entity;
		
	}

	public void updateEntity(List<T> data){
		
	}

}
