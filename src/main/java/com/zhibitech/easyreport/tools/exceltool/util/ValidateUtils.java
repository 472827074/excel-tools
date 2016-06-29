package com.zhibitech.easyreport.tools.exceltool.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.zhibitech.framework.core.utils.DateUtil;


public class ValidateUtils {
	/**
	 * 
	 * 功能：校验字符串 并返回(如果为空或者null)
	 */
	public static boolean emptyString(String text) {
		return StringUtils.isEmpty(text);
	}

	public static boolean nullObj(Object obj) {
		return null == obj;
	}

	public static boolean nullBoolean(Boolean flag) {
		return null == flag;
	}

	public static boolean containValue(String value, String[] values) {
		boolean flag = false;
		for (String vl : values) {
			if (vl.equals(value)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	public static String[] cellValueTrim(String[] cellValues) {
		for (int i = 0; i < cellValues.length; i++) {
			cellValues[i] = cellValues[i].trim();
		}
		return cellValues;
	}

	/**
	 * 功能：验证时间---年-月-日
	 * 
	 */
	/*
	 * public static boolean isDate(String string) { if (string .replaceAll(
	 * "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29))$"
	 * , "").length() == 0) { return true; } return false; }
	 */
	/**
	 * 功能：判断字符串是否为日期格式
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDate(String strDate) {
		Pattern pattern = Pattern
				.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
		Matcher m = pattern.matcher(strDate);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 功能：判断一个字符串是否包含特殊字符 （汉字a-z A-Z 0-9）
	 * 
	 */
	public static boolean isConSpeCharacters(String string) {
		if (string.replaceAll(
				"[\u4e00-\u9fa5]*[a-z]*[A-Z]*\\d*-*_*\\s*）*（*\\(*\\)*", "")
				.length() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 功能：判断一个IP是否合法
	 * 
	 */
	public static boolean isLegalIp(String string) {
		if (string
				.replaceAll(
						"(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])",
						"").length() == 0) {
			return true;
		}
		return false;
	}
    /**
     * 
     * 判断字符串长度范围
     * @param min
     * @param max
     * @param text
     * @return
     * @see [类、类#方法、类#成员]
     */
	public static boolean illegalStringLength(int min, int max, String text) {
		if (emptyString(text)) {
			if (min > 0) {
				return true;
			} else {
				return false;
			}
		}
		// 先去掉输入的字符串中的换行和回车添加的符号
		String[] remove = { "\n" };
		for (int i = 0; i < remove.length; i++) {
			text = text.replaceAll(remove[i], "");
		}
		return text.length() < min || text.length() > max;
	}

	public boolean illegalMobilePhone(String text) {
		if (text.length() < 11 || text.length() > 11) {
			return true;
		}
		return !text.matches("^1[0-9]\\d{9}$");
	}

	public static boolean illegalEmail(String mail) {
		String regex = ("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(mail);
		return !m.find();
	}

	public static boolean illegalWebSite(String mail) {
		String regex = "^(http|https)://(([a-zA-z0-9]|-){1,}\\.){1,}[a-zA-z0-9]{1,}-*";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(mail);
		return !m.find();
	}

	public static boolean illegalTelephone(String text) {
		if (text.length() <= 20 && text.charAt(0) != '-'
				&& text.charAt(text.length() - 1) != '-') {
			for (int i = 0; i < text.length(); i++) {
				int charToInt = text.charAt(i);
				if ((charToInt < 48 && charToInt != 45) || charToInt > 57) {
					return true;
				}
			}
			return false;
		}
		return true;
	}

	// TEST
	public static void main(String[] args) {
		String idcard = "11010119780501117x";
		boolean b = new ValidateUtils().illegalIdcard(idcard);
		System.out.println(b);
		// System.out.println(new ValidateHelper().isDate("2012-12-12"));

	}

	/*********************************** 身份证验证开始 ****************************************/
	/**
	 * 身份证号码验证 1、号码的结构 公民身份号码是特征组合码，由十七位数字本体码和一位校验码组成。排列顺序从左至右依次为：六位数字地址码，
	 * 八位数字出生日期码，三位数字顺序码和一位数字校验码。 2、地址码(前六位数）
	 * 表示编码对象常住户口所在县(市、旗、区)的行政区划代码，按GB/T2260的规定执行。 3、出生日期码（第七位至十四位）
	 * 表示编码对象出生的年、月、日，按GB/T7408的规定执行，年、月、日代码之间不用分隔符。 4、顺序码（第十五位至十七位）
	 * 表示在同一地址码所标识的区域范围内，对同年、同月、同日出生的人编定的顺序号， 顺序码的奇数分配给男性，偶数分配给女性。 5、校验码（第十八位数）
	 * （1）十七位数字本体码加权求和公式 S = Sum(Ai * Wi), i = 0, ... , 16 ，先对前17位数字的权求和
	 * Ai:表示第i位置上的身份证号码数字值 Wi:表示第i位置上的加权因子 Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4
	 * 2 （2）计算模 Y = mod(S, 11) （3）通过模得到对应的校验码 Y: 0 1 2 3 4 5 6 7 8 9 10 校验码: 1 0
	 * X 9 8 7 6 5 4 3 2
	 */

	/**
	 * 功能：身份证的有效验证 是否非法
	 * 
	 * @param IDStr
	 *            身份证号
	 * @return 有效：返回 false 无效：返回true
	 * @throws ParseException
	 */
	public static boolean illegalIdcard(String IDStr) {
		if (null == IDStr || "".equals(IDStr)) {
			return false;
		}
		IDStr = IDStr.toUpperCase();
		// String errorInfo = "";// 记录错误信息
		String[] ValCodeArr = { "1", "0", "X", "9", "8", "7", "6", "5", "4",
				"3", "2" };
		String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
				"9", "10", "5", "8", "4", "2" };
		String Ai = "";
		// ================ 号码的长度 15位或18位 ================
		if (IDStr.length() != 15 && IDStr.length() != 18) {
			// errorInfo = "身份证号码长度应该为15位或18位。";
			return true;
		}
		// =======================(end)========================

		// ================ 数字 除最后以为都为数字 ================
		if (IDStr.length() == 18) {
			Ai = IDStr.substring(0, 17);
		} else if (IDStr.length() == 15) {
			Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
		}
		if (illegalNum(Ai)) {
			// errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
			return true;
		}
		// =======================(end)========================

		// ================ 出生年月是否有效 ================
		String strYear = Ai.substring(6, 10);// 年份
		String strMonth = Ai.substring(10, 12);// 月份
		String strDay = Ai.substring(12, 14);// 月份
		if (isDate(strYear + "-" + strMonth + "-" + strDay) == false) {
			// errorInfo = "身份证生日无效。";
			return true;
		}
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if (Integer.parseInt(strMonth) > 12
					|| Integer.parseInt(strMonth) == 0) {
				// errorInfo = "身份证月份无效";
				return true;
			}
			if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
				// errorInfo = "身份证日期无效";
				return true;
			}
			if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
					|| (gc.getTime().getTime() - s.parse(
							strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
				// errorInfo = "身份证生日不在有效范围。";
				return true;
			}
		} catch (NumberFormatException e) {
			// e.printStackTrace();
			return true;
		} catch (java.text.ParseException e) {
			// e.printStackTrace();
			return true;
		}

		// =====================(end)=====================

		// ================ 地区码时候有效 ================
		Hashtable h = getAreaCode();
		if (h.get(Ai.substring(0, 2)) == null) {
			// errorInfo = "身份证地区编码错误。";
			return true;
		}
		// ==============================================

		// ================ 判断最后一位的值 ================
		/*
		 * int TotalmulAiWi = 0; for (int i = 0; i < 17; i++) { TotalmulAiWi =
		 * TotalmulAiWi + Integer.parseInt(String.valueOf(Ai.charAt(i)))
		 * Integer.parseInt(Wi[i]); } int modValue = TotalmulAiWi % 11; String
		 * strVerifyCode = ValCodeArr[modValue]; Ai = Ai + strVerifyCode;
		 * 
		 * if (IDStr.length() == 18) { if (Ai.equals(IDStr) == false) { //
		 * errorInfo = "身份证无效，不是合法的身份证号码"; return true; } } else { return false;
		 * } // =====================(end)=====================
		 */
		return false;

	}

	/*********************************** 身份证验证结束 ****************************************/

	/**
	 * 身份证地区编码
	 * 
	 * @return
	 */
	private static Hashtable<String, String> getAreaCode() {
		Hashtable<String, String> hashtable = new Hashtable<String, String>();
		hashtable.put("11", "北京");
		hashtable.put("12", "天津");
		hashtable.put("13", "河北");
		hashtable.put("14", "山西");
		hashtable.put("15", "内蒙古");
		hashtable.put("21", "辽宁");
		hashtable.put("22", "吉林");
		hashtable.put("23", "黑龙江");
		hashtable.put("31", "上海");
		hashtable.put("32", "江苏");
		hashtable.put("33", "浙江");
		hashtable.put("34", "安徽");
		hashtable.put("35", "福建");
		hashtable.put("36", "江西");
		hashtable.put("37", "山东");
		hashtable.put("41", "河南");
		hashtable.put("42", "湖北");
		hashtable.put("43", "湖南");
		hashtable.put("44", "广东");
		hashtable.put("45", "广西");
		hashtable.put("46", "海南");
		hashtable.put("50", "重庆");
		hashtable.put("51", "四川");
		hashtable.put("52", "贵州");
		hashtable.put("53", "云南");
		hashtable.put("54", "西藏");
		hashtable.put("61", "陕西");
		hashtable.put("62", "甘肃");
		hashtable.put("63", "青海");
		hashtable.put("64", "宁夏");
		hashtable.put("65", "新疆");
		hashtable.put("71", "台湾");
		hashtable.put("81", "香港");
		hashtable.put("82", "澳门");
		hashtable.put("91", "国外");
		return hashtable;
	}

	/**
	 * 功能：判断字符串是否为数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean illegalNum(String text) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher numbericMatcher = pattern.matcher(text);
		return !numbericMatcher.matches();
	}

	public boolean notLettersOrNum(String text) {
		if (!text.matches("^[A-Za-z0-9]+$")) {
			return true;
		}
		return false;
	}

	/**
	 * 只能是数字、字母、短划线
	 */
	public boolean isCodeValidate(String text) {
		if (!text.matches("^[A-Za-z0-9\\-]+$")) {
			return true;
		}
		return false;
	}

	public boolean endDateIsSameorBigForStartDate(Date endDate, Date startDate) {
		if (endDate.after(startDate)) {
			return true;
		}
		return false;
	}

	public boolean endDateIsTrueSameorBigForStartDate(Date endDate,
			Date startDate) {
		if (endDate.getTime() >= startDate.getTime()) {
			return true;
		}
		return false;
	}

	public static boolean illegalNumeric(String str) {

		if (str.charAt(0) == '.' || str.charAt(str.length() - 1) == '.'
				|| (str.charAt(0) == '-' || str.charAt(0) == '+')
				&& str.charAt(1) == '.') {
			return true;
		} else {
			try {
				Double.parseDouble(str);
				return false;
			} catch (NumberFormatException ex) {
				return true;
			}
		}
	}

	public static boolean illegalNumericRange(String str, double min, double max) {
		BigDecimal value = new BigDecimal(String.valueOf(str));
		BigDecimal one = new BigDecimal(String.valueOf(min));
		BigDecimal two = new BigDecimal(String.valueOf(max));
		return value.compareTo(one) > 0 && value.compareTo(two) < 0;

	}

	public static boolean illegalDate(String strDate) {
		if (StringUtils.isEmpty(strDate)) {
			return false;
		}
		if (illegalLength(strDate)) {
			return true;
		}
		Date date = null;
		date = DateUtil.parseDate(strDate, "yyyy/MM/dd");
		if (null != date && getPrevFourString(strDate, "/") < 1900) {
			return true;
		}
		if (date == null) {
			date = DateUtil.parseDate(strDate, "yyyy-MM-dd");
			if (null != date && getPrevFourString(strDate, "-") < 1900) {
				return true;
			}
		}
		return date == null;
	}

	// 验证日期的长度
	public static boolean illegalLength(String strDate) {
		try {
			if (strDate.indexOf("-") != -1
					&& (strDate.substring(strDate.lastIndexOf("-") + 1)
							.length() > 2 || strDate.substring(
							strDate.indexOf("-") + 1, strDate.lastIndexOf("-"))
							.length() > 2)) {
				return true;
			} else if (strDate.indexOf("/") != -1
					&& (strDate.substring(strDate.lastIndexOf("/") + 1)
							.length() > 2 || strDate.substring(
							strDate.indexOf("/") + 1, strDate.lastIndexOf("/"))
							.length() > 2)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isChinese(String str) {
		String pattern = "^[0-9]*$";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(str);
		return !m.find();
	}

	// 对年份的验证
	public static boolean illegaYear(String strYear) {
		try {
			if (!StringUtils.isEmpty(strYear)) {
				return false;
			}
			Date year = null;
			year = DateUtil.parseDate(strYear, "yyyy");
			if (year == null) {
				return true;
			}
			if (null != year && Integer.parseInt(strYear) < 1900) {
				return true;
			}
		} catch (Exception e) {
			return true;
		}
		return false;
	}

	public static boolean illegalBoolean(String text) {
		if ("是".equals(text) || "否".equals(text)) {
			return false;
		} else {
			return true;
		}
	}

	public static int getPrevFourString(String strDate, String style) {
		String str = strDate.substring(0, strDate.indexOf(style, 1));
		int result = Integer.parseInt(str);
		return result;
	}

	public static boolean illegalUserName(String text) {
		Pattern p = Pattern.compile("^[A-Za-z0-9_]+$");
		Matcher m = p.matcher(text);
		return !m.find();
	}

	/**
	 * 验证是否输入特殊字符(数字,字母,中文字符)
	 */
	public static boolean illegalExculdeParticalChar(String text) {
		return !text.matches("^[A-Za-z0-9\u4E00-\u9FA5]+$");
	}

	/**
	 * 特殊字符验证
	 */
	public static boolean illegalCharCheck(String text) {
		return text
				.matches("^(?:[A-Za-z0-9\\u4E00-\\u9FA5]*[\\(|\\（]*[\\)|\\）]*-*)+$");
	}
	
	/**
	 * 验证是否输入特殊字符(字母,中文字符)
	 */
	public static boolean illegalParticalChar(String text) {
		return !text.matches("^[A-Za-z\u4E00-\u9FA5]+$");
	}

	/**
	 * 验证是否输入特殊字符(只能输入数字,字母)
	 */
	public static boolean illegalExculdeChinese(String text) {
		return !text.matches("^[A-Za-z0-9]+$");
	}

	/**
	 * 验证是否输入特殊字符(数字,字母,中文字符,空格,点)
	 * 
	 */
	public static boolean illegalExculdeParticalChar2(String text) {
		return !text.matches("^[A-Za-z0-9\u4E00-\u9FA5\\s\\.]+$");
	}

	/**
	 * 验证英文名
	 */
	public static boolean illegalEnglishName(String text) {
		return !text.matches("[^\u4e00-\u9fa5]+$");
	}

	/**
	 * 正整数
	 * 
	 * @param text
	 * @return
	 */
	public static boolean illegalNumberZZ(String text) {
		Pattern pattern = Pattern.compile("[^(0|\\-|\\s)*][0-9]*$");
		Matcher numbericMatcher = pattern.matcher(text);
		return !numbericMatcher.matches();

	}

	/**
	 * 只能输入正数
	 * 
	 * @param text
	 * @return
	 */
	public static boolean illegalNumberZS(String text) {
		Pattern pattern = Pattern.compile("[0-9]+(.[0-9]+)?");
		Matcher numbericMatcher = pattern.matcher(text);
		return !numbericMatcher.matches();

	}

	/**
	 * 校验带有1-2位小数的正实数
	 * 
	 * @param text
	 * @return
	 */
	public static boolean illegalPointNumber(String text) {
		Pattern pattern = Pattern.compile("^[0-9]+(.[0-9]{1,2})?$");
		Matcher numbericMatcher = pattern.matcher(text);
		return !numbericMatcher.matches();
	}

	public static boolean illegalInteger(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher numbericMatcher = pattern.matcher(str);
		if (!numbericMatcher.matches()) {
			return true;
		} else {
			try {
				Integer.parseInt(str);
				return false;
			} catch (NumberFormatException ex) {
				return true;
			}
		}
	}

	/**
	 * 校验带有4位小数的正实数
	 * 
	 * @param text
	 * @return
	 */
	public static boolean illegalPointNumber4(String text) {
		if (text.startsWith("0"))
			return false;
		Pattern pattern = Pattern.compile("^[0-9]+(.[0-9]{1,4})?$");
		Matcher numbericMatcher = pattern.matcher(text);
		return !numbericMatcher.matches();
	}

	public static boolean dateIsnotNull(Date date) {
		if (null == date) {
			return false;
		}
		return true;
	}

	public static boolean isAddress(String string) {
		if (string.replaceAll("/^[^~!@$%^&*+]{0,}$/gm", "").length() == 0) {
			return false;
		}
		return true;
	}

}
