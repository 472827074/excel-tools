package com.zhibitech.easyreport.tools.exceltool;

import java.util.HashMap;
import java.util.Map;

public final class DataConvertDefine {
	static Map<String, String> defines = new HashMap<String, String>();
	static {
		defines.put("userInfo",
				"userInfoDataConverter");
	}

	public static String getConvertBeanDefine(String data_type) {
		return defines.get(data_type);
	}
}
