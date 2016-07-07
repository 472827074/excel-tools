package com.zhibitech.easyreport.tools.exceltool.convert;

import java.beans.PropertyEditor;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.propertyeditors.CustomDateEditor;

import com.zhibitech.easyreport.tools.exceltool.validate.DataValidate;
import com.zhibitech.easyreport.tools.exceltool.validate.ValidateResult;

public abstract class AbstractDataConverter implements DataConvert {

	protected Map<Class<?>, PropertyEditor> propertyEditors = new HashMap<>();
	
	public AbstractDataConverter() {

		propertyEditors.put(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"),true));
	}

	protected DataValidate validate;
	
	
	public void setValidate(DataValidate validate) {
		this.validate = validate;
	}



	/** 
	 * 添加自定义高级编辑器、子类可以在数据转换之前添加编辑器
	 * @param requiredType
	 * @param propertyEditor
	 * @see [类、类#方法、类#成员]
	 */
	protected void addPropertyEditor(Class<?> requiredType, PropertyEditor propertyEditor){
	   this.propertyEditors.put(requiredType, propertyEditor);	
	}
	
	protected ValidateResult validate(Object entity, Map<String, Integer> fileIndexMap){
		return validate.validate(entity, fileIndexMap);
	}

}
