package com.zhibitech.easyreport.tools.exceltool.convert;

import com.zhibitech.easyreport.tools.exceltool.validate.ValidateResult;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  姓名 工号
 * @version  [版本号, 2016年6月28日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface DataConvert {


	/** 
	 * <一句话功能简述>
	 * <功能详细描述>
	 * @param cellValues
	 * @param beanDatas
	 * @param result
	 * @param clz
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	<T> T convertToObj(String[] cellValues,String[][] beanDatas, ValidateResult result,Class<T> clz);

}
