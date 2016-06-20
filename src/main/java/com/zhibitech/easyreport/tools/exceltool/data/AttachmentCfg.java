package com.zhibitech.easyreport.tools.exceltool.data;

import java.net.URL;
import java.util.Map;
import java.util.Properties;

import com.google.common.collect.Maps;

/**
 * <pre>
 * 系统全局配置项定义（从classpath:sys-attachment.properties中读取）
 * @ClassName: SystemCfg
 * @author Zhang Zhongxiang<zhangzxiang91@gmail.com>
 * @date 2015年1月20日 上午1:24:07
 */
public class AttachmentCfg {
	/*--------------------------------------------
	|             配置项读取部分                   |
	============================================*/
	private static Properties			properties;
	private static Map<String, String>	config					= Maps.newHashMap();
	static {
		properties = new Properties();
		URL url = null;
		try {
			ClassLoader classloader = Thread.currentThread().getContextClassLoader();
			url = classloader.getResource("attachment.properties");
			properties.load(url.openStream());
			for (String name : properties.stringPropertyNames()) {
				config.put(name, properties.getProperty(name));
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/*--------------------------------------------
	|             配置项预定义部分              |
	============================================*/
	public static String				file_upload_dir			= AttachmentCfg.get("file.upload.dir");
	public static String				file_upload_temp_dir	= AttachmentCfg.get("file.upload.temp.dir");
	public static String				file_upload_user_dir	= AttachmentCfg.get("file.upload.user.dir");
	public static String				file_upload_regulatory_dir	= AttachmentCfg.get("file.upload.regulatory.dir");
	public static String				file_upload_merchants_dir	= AttachmentCfg.get("file.upload.merchants.dir");
	public static String                excel_template_dir = AttachmentCfg.get("excel.template.dir");
	public static String                excel_upload_dir = AttachmentCfg.get("excel.upload.dir");

	public static String get(String key) {
		return config.get(key);
	}

	public static String get(String key, String defaultValue) {
		String value = null;
		if (config.containsKey(key)) {
			value = config.get(key);
		}
		if (value == null || "".equals(value)) {
			value = defaultValue;
		}
		return value;
	}
}
