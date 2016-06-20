package com.zhibitech.easyreport.tools.exceltool.util;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.zhibitech.framework.core.utils.DateUtil;
@Component("dataConvertUtil")
@Scope("prototype")
public class DataConvertUtil {
	public Date convertToDate(String dateText) {
		if (!StringUtils.isEmpty(dateText)) {
			return null;
		}
		Date result = null;
		result = DateUtil.parseDate(dateText, "yyyy/MM/dd");
		if (result == null) {
			result = DateUtil.parseDate(dateText, "yyyy-MM-dd");
		}
		if (result == null) {
			result = DateUtil.parseDate(dateText, "MM/dd/yy");
		}
		return result;
	}
	public boolean convertToBoolean(String booleanText) {
		if (!StringUtils.isEmpty(booleanText)) {
			return Boolean.FALSE;
		}
		return "是".equals(booleanText.trim()) ? true : false;
	}

	public Double convertToDouble(String doubleText) {
		if (StringUtils.isEmpty(doubleText)) {
			return Double.valueOf(doubleText);
		} else {
			return null;
		}
	}

	public double convertToDoubleValue(String doubleText) {
		Double result = convertToDouble(doubleText);
		return result == null ? 0 : result.doubleValue();
	}

	public BigDecimal converToBigDecimal(String bigText) {
		if (StringUtils.isEmpty(bigText)) {
			return new BigDecimal(bigText);
		} else {
			return null;
		}
	}

	public Long convertToLong(String longText) {
		if (StringUtils.isEmpty(longText)) {
			return Long.valueOf(longText);
		} else {
			return null;
		}
	}

	public Integer convertToInteger(String text) {
		if (StringUtils.isEmpty(text)) {
			return Integer.valueOf(text);
		} else {
			return null;
		}
	}

}
