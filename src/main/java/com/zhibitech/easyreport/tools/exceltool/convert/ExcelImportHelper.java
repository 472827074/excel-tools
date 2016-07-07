package com.zhibitech.easyreport.tools.exceltool.convert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zhibitech.easyreport.tools.exceltool.util.DataConvertUtil;
import com.zhibitech.easyreport.tools.exceltool.util.ValidateUtils;
import com.zhibitech.easyreport.tools.exceltool.validate.ValidateResult;
import com.zhibitech.framework.core.exception.ServiceException;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import ognl.Ognl;
import ognl.OgnlException;

@Component("excelImportHelper")
public class ExcelImportHelper {

	public final static Logger logger = LoggerFactory
			.getLogger(ExcelImportHelper.class);
	
	
	@Autowired
	protected  DataConvertUtil convertUtil ;
	@Autowired
	protected  ValidateUtils validateUtil;
	public  Object convertEntity(String[][] beanDatas,
			String[] cellValues, Object object, ValidateResult vr) {
		Map<String, String> mapMessages = new HashMap<String, String>();
		int length = beanDatas[0].length > cellValues.length ? cellValues.length
				: beanDatas[0].length;
		try {
			for (int i = 0; i < length; i++) {
				
				if (StringUtils.isEmpty(cellValues[i])) {
					continue;
				}

				if (!validateDate(beanDatas, cellValues, i, object, mapMessages)) {
					continue;
				}
				
				if (!validateBoolean(beanDatas, cellValues, i, object,
						mapMessages)) {
					continue;
				}

				if (!validateInteger(beanDatas, cellValues, i, object,
						mapMessages)) {
					continue;
				}
				if (!validateDouble(beanDatas, cellValues, i, object,
						mapMessages)) {
					continue;
				}
				if (!validateLong(beanDatas, cellValues, i, object, mapMessages)) {
					continue;
				}

				if (!StringUtils.isEmpty(cellValues[i])
						&& !StringUtils.isEmpty(beanDatas[0][i])) {
					Ognl.setValue(beanDatas[0][i], object, cellValues[i]);
				}
			}

		
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("模板错误，请下载最新模板");
		}
	}

	private  boolean validateDate(String[][] beanDatas,
			String[] cellValues, int i, Object object,
			Map<String, String> mapMessages) {
		if (ExcelDataType.DATE.equals(beanDatas[1][i])) {
			
			if (validateUtil.illegalDate(cellValues[i])) {
				mapMessages.put(String.valueOf(i), beanDatas[2][i]
						+ "输入格式不正确");
				return false;
			}
			try {
				Ognl.setValue(beanDatas[0][i], object,
						convertUtil.convertToDate(cellValues[i]));
				return false;
			} catch (OgnlException e) {
				mapMessages.put(String.valueOf(i), beanDatas[2][i]
						+ "日期类型不正确");
			}
		}
		return true;
	}
	
	
	private static boolean validateBoolean(String[][] beanDatas,
			String[] cellValues, int i, Object object,
			Map<String, String> mapMessages) {
		try {
			if (ExcelDataType.BOOLEAN.equals(beanDatas[1][i])
					|| ExcelDataType.BOOLEANS.equals(beanDatas[1][i])) {
				if ("是".equals(cellValues[i]) || "有".equals(cellValues[i])) {
					Ognl.setValue(beanDatas[0][i], object, 1);
				} else {
					Ognl.setValue(beanDatas[0][i], object, 0);
				}
				return false;
			}
		} catch (Exception e) {
			mapMessages.put(String.valueOf(i), beanDatas[2][i]
					+ "输入值不正确");
		}
		return true;
	}

//	private static Boolean validateDictList(String[][] beanDatas,
//			String[] cellValues, int i, Object object,
//			Map<String, String> mapMessages) {
//		try {
//			if (DataType.DATA_TYPE_DICT_LIST.equals(beanDatas[1][i])) {
//				if ("是".equals(cellValues[i]) || "有".equals(cellValues[i])) {
//					List<PropertyDict> dicts = null;
//					if (null == Ognl.getValue(beanDatas[0][i], object)) {
//						dicts = new ArrayList<PropertyDict>();
//						dicts.add(convertUtil.convertToPropertyDict(
//								beanDatas[2][i], beanDatas[3][i]));
//
//						Ognl.setValue(beanDatas[0][i], object, dicts);
//					} else {
//						dicts = (List<PropertyDict>) Ognl.getValue(
//								beanDatas[0][i], object);
//						dicts.add(convertUtil.convertToPropertyDict(
//								beanDatas[2][i], beanDatas[3][i]));
//						Ognl.setValue(beanDatas[0][i], object, dicts);
//					}
//
//					if (beanDatas.length > 6) {
//						if (!"".contains(beanDatas[5][i])) {
//							Ognl.setValue(beanDatas[4][i], object,
//									convertUtil.convertToPropertyDict(
//											beanDatas[5][i], beanDatas[6][i]));
//						} else {
//							Ognl.setValue(beanDatas[4][i], object,
//									beanDatas[6][i]);
//						}
//					}
//
//				}
//				return false;
//			}
//		} catch (Exception e) {
//			logger.error(mapMessages.get(String.valueOf(i + 1)));
//			throw new ServiceException(mapMessages.get(String.valueOf(i + 1)));
//		}
//		return true;
//
//	}

	private  boolean validateInteger(String[][] beanDatas,
			String[] cellValues, int i, Object object,
			Map<String, String> mapMessages) {
		if (ExcelDataType.INTEGER.equals(beanDatas[1][i])) {
			try {
				if("本市".equals(beanDatas[2][i])){
					if("本市".equals(cellValues[i])){
						Ognl.setValue(beanDatas[0][i], object,
								1);
						return false;
					}
					if("非本市".equals(cellValues[i])){
						Ognl.setValue(beanDatas[0][i], object,
								0);
						return false;
					}
				}
				if (validateUtil.illegalNum(cellValues[i])) {
					mapMessages.put(String.valueOf(i), beanDatas[2][i]
							+ "输入格式不正确，只能输入正整数");
					return false;
				}
			
				Ognl.setValue(beanDatas[0][i], object,
						convertUtil.convertToInteger(cellValues[i]));
				return false;
			} catch (Exception e) {
				mapMessages.put(String.valueOf(i), beanDatas[2][i]
						+ "输入不正确，数值过大");
				return false;
			}
		}
		return true;

	}

	private  boolean validateDouble(String[][] beanDatas,
			String[] cellValues, int i, Object  object,
			Map<String, String> mapMessages) {
		if (ExcelDataType.DOUBLE.equals(beanDatas[1][i])) {
			if (validateUtil.illegalPointNumber4(cellValues[i])) {
				mapMessages.put(String.valueOf(i), beanDatas[2][i]
						+ "输入格式不正确，小数点后最多保留2位小数");
				return false;
			}
			try {
				Ognl.setValue(beanDatas[0][i], object,
						convertUtil.convertToDouble(cellValues[i]));
				return false;
			} catch (Exception e) {
				mapMessages.put(String.valueOf(i), beanDatas[2][i]
						+ "输入数据过大");
				return false;
			}
		}
		return true;

	}

	private  boolean validateLong(String[][] beanDatas,
			String[] cellValues, int i, Object object,
			Map<String, String> mapMessages) {
		if (ExcelDataType.LONG.equals(beanDatas[1][i])) {
			if (validateUtil.illegalNum(cellValues[i])) {
				mapMessages.put(String.valueOf(i), beanDatas[2][i]
						+ "输入格式不正确,只能输入正整数");
				return false;
			}
			try {
				Ognl.setValue(beanDatas[0][i], object,
						convertUtil.convertToLong(cellValues[i]));
				return false;
			} catch (Exception e) {
				mapMessages.put(String.valueOf(i), beanDatas[2][i]
						+ "输入值过长");
				return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		String name="王妃";
		System.out.println(HanyuToPinyin(name).toUpperCase());
	}
	
	private static String HanyuToPinyin(String name){
		String pinyinName="";
        char[] nameChar = name.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = 
                                           new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < nameChar.length; i++) {
            if (nameChar[i] > 128) {
                try {
                    pinyinName += PinyinHelper.toHanyuPinyinStringArray
                                           (nameChar[i], defaultFormat)[0];
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } 
        }
        return pinyinName;
    }

	
}
