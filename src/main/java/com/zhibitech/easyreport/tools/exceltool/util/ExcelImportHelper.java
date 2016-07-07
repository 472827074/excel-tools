package com.zhibitech.easyreport.tools.exceltool.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhibitech.easyreport.tools.exceltool.convert.ExcelDataType;
import com.zhibitech.easyreport.tools.exceltool.validate.ValidateResult;
import com.zhibitech.framework.core.exception.ServiceException;

import ognl.Ognl;
import ognl.OgnlException;

public class ExcelImportHelper {

	public final static Logger logger = LoggerFactory.getLogger(ExcelImportHelper.class);

	public static Object convertEntity(String[][] beanDatas, String[] cellValues, Object object, ValidateResult vr) {
		Map<String, String> mapMessages = new HashMap<String, String>();
		int length = beanDatas[0].length > cellValues.length ? cellValues.length : beanDatas[0].length;
		try {
			for (int i = 0; i < length; i++) {

				if (StringUtils.isEmpty(cellValues[i])) {
					continue;
				}

				if (!validateDate(beanDatas, cellValues, i, object, mapMessages)) {
					continue;
				}

				if (!validateBoolean(beanDatas, cellValues, i, object, mapMessages)) {
					continue;
				}

				if (!validateInteger(beanDatas, cellValues, i, object, mapMessages)) {
					continue;
				}
				if (!validateDouble(beanDatas, cellValues, i, object, mapMessages)) {
					continue;
				}
				if (!validateLong(beanDatas, cellValues, i, object, mapMessages)) {
					continue;
				}

				if (!StringUtils.isEmpty(cellValues[i]) && !StringUtils.isEmpty(beanDatas[0][i])) {
					Ognl.setValue(beanDatas[0][i], object, cellValues[i]);
				}
			}

			return object;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("模板错误，请下载最新模板");
		}
	}

	private static boolean validateDate(String[][] beanDatas, String[] cellValues, int i, Object object,
			Map<String, String> mapMessages) {
		if (ExcelDataType.DATE.equals(beanDatas[1][i])) {

			if (ValidateUtils.illegalDate(cellValues[i])) {
				mapMessages.put(String.valueOf(i), beanDatas[2][i] + "输入格式不正确");
				return false;
			}
			try {
				Ognl.setValue(beanDatas[0][i], object, DataConvertUtil.convertToDate(cellValues[i]));
				return false;
			} catch (OgnlException e) {
				mapMessages.put(String.valueOf(i), beanDatas[2][i] + "日期类型不正确");
			}
		}
		return true;
	}

	private static boolean validateBoolean(String[][] beanDatas, String[] cellValues, int i, Object object,
			Map<String, String> mapMessages) {
		try {
			if (ExcelDataType.BOOLEAN.equals(beanDatas[1][i]) || ExcelDataType.BOOLEANS.equals(beanDatas[1][i])) {
				if ("是".equals(cellValues[i]) || "有".equals(cellValues[i])) {
					Ognl.setValue(beanDatas[0][i], object, 1);
				} else {
					Ognl.setValue(beanDatas[0][i], object, 0);
				}
				return false;
			}
		} catch (Exception e) {
			mapMessages.put(String.valueOf(i), beanDatas[2][i] + "输入值不正确");
		}
		return true;
	}

	// private static Boolean validateDictList(String[][] beanDatas,
	// String[] cellValues, int i, Object object,
	// Map<String, String> mapMessages) {
	// try {
	// if (DataType.DATA_TYPE_DICT_LIST.equals(beanDatas[1][i])) {
	// if ("是".equals(cellValues[i]) || "有".equals(cellValues[i])) {
	// List<PropertyDict> dicts = null;
	// if (null == Ognl.getValue(beanDatas[0][i], object)) {
	// dicts = new ArrayList<PropertyDict>();
	// dicts.add(DataConvertUtil.convertToPropertyDict(
	// beanDatas[2][i], beanDatas[3][i]));
	//
	// Ognl.setValue(beanDatas[0][i], object, dicts);
	// } else {
	// dicts = (List<PropertyDict>) Ognl.getValue(
	// beanDatas[0][i], object);
	// dicts.add(DataConvertUtil.convertToPropertyDict(
	// beanDatas[2][i], beanDatas[3][i]));
	// Ognl.setValue(beanDatas[0][i], object, dicts);
	// }
	//
	// if (beanDatas.length > 6) {
	// if (!"".contains(beanDatas[5][i])) {
	// Ognl.setValue(beanDatas[4][i], object,
	// DataConvertUtil.convertToPropertyDict(
	// beanDatas[5][i], beanDatas[6][i]));
	// } else {
	// Ognl.setValue(beanDatas[4][i], object,
	// beanDatas[6][i]);
	// }
	// }
	//
	// }
	// return false;
	// }
	// } catch (Exception e) {
	// logger.error(mapMessages.get(String.valueOf(i + 1)));
	// throw new ServiceException(mapMessages.get(String.valueOf(i + 1)));
	// }
	// return true;
	//
	// }

	private static boolean validateInteger(String[][] beanDatas, String[] cellValues, int i, Object object,
			Map<String, String> mapMessages) {
		if (ExcelDataType.INTEGER.equals(beanDatas[1][i])) {
			try {
				
				if (ValidateUtils.illegalNum(cellValues[i])) {
					mapMessages.put(String.valueOf(i), beanDatas[2][i] + "输入格式不正确，只能输入正整数");
					return false;
				}

				Ognl.setValue(beanDatas[0][i], object, DataConvertUtil.convertToInteger(cellValues[i]));
				return false;
			} catch (Exception e) {
				mapMessages.put(String.valueOf(i), beanDatas[2][i] + "输入不正确，数值过大");
				return false;
			}
		}
		return true;

	}

	private static boolean validateDouble(String[][] beanDatas, String[] cellValues, int i, Object object,
			Map<String, String> mapMessages) {
		if (ExcelDataType.DOUBLE.equals(beanDatas[1][i])) {
			if (ValidateUtils.illegalPointNumber4(cellValues[i])) {
				mapMessages.put(String.valueOf(i), beanDatas[2][i] + "输入格式不正确，小数点后最多保留2位小数");
				return false;
			}
			try {
				Ognl.setValue(beanDatas[0][i], object, DataConvertUtil.convertToDouble(cellValues[i]));
				return false;
			} catch (Exception e) {
				mapMessages.put(String.valueOf(i), beanDatas[2][i] + "输入数据过大");
				return false;
			}
		}
		return true;

	}

	private static boolean validateLong(String[][] beanDatas, String[] cellValues, int i, Object object,
			Map<String, String> mapMessages) {
		if (ExcelDataType.LONG.equals(beanDatas[1][i])) {
			if (ValidateUtils.illegalNum(cellValues[i])) {
				mapMessages.put(String.valueOf(i), beanDatas[2][i] + "输入格式不正确,只能输入正整数");
				return false;
			}
			try {
				Ognl.setValue(beanDatas[0][i], object, DataConvertUtil.convertToLong(cellValues[i]));
				return false;
			} catch (Exception e) {
				mapMessages.put(String.valueOf(i), beanDatas[2][i] + "输入值过长");
				return false;
			}
		}
		return true;
	}

}
