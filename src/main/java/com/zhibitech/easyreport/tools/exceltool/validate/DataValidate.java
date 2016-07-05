package com.zhibitech.easyreport.tools.exceltool.validate;

import java.util.Map;

/**
 * 
 * 数据验证接口
 * @author  姓名 工号
 * @version  [版本号, 2016年7月5日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface DataValidate {

	/** 
	 * 验证并返回错误信息
	 * @param entity 要验证的实体对象
	 * @param fileIndexMap 字段映射
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public ValidateResult validate(Object entity, Map<String, Integer> fileIndexMap);
}
