package com.zhibitech.easyreport.tools.exceltool.convert;

import java.beans.PropertyEditor;
import java.util.Map;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.zhibitech.easyreport.tools.exceltool.validate.ValidateResult;

public class DefaultDataConverter extends AbstractDataConverter {

	@Override
	public <T> T convertToObj(String[] beanDatas,String[] cellValues,  ValidateResult result, Class<T> clz) {
		if (clz == null) {
			throw new IllegalArgumentException("转换类型不能为空");
		}
		T obj;
		try {
			obj = clz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("实例化" + clz.getName() + "失败");
		}
		BeanWrapper bw = new BeanWrapperImpl(obj);
		// 注册用户自定义的编辑器
		for (Map.Entry<Class<?>, PropertyEditor> entry : propertyEditors.entrySet()) {
			bw.registerCustomEditor(entry.getKey(), entry.getValue());
		}
		int length = beanDatas.length > cellValues.length ? cellValues.length : beanDatas.length;
		for (int i = 0; i < length; i++) {
			try {
				bw.setPropertyValue(beanDatas[i], cellValues[i]);
			} catch (Exception e) {
				result.addErrorMessage(i,"数据出错");
			}
		}

		return obj;
	}

}
