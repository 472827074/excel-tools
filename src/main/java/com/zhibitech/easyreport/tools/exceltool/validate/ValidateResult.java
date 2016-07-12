package com.zhibitech.easyreport.tools.exceltool.validate;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 验证结果封装
 * @author  yumeng
 * @version  [版本号, 2016年7月5日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ValidateResult implements Serializable {

	private static final long serialVersionUID = 8758821696627253640L;
	private static final String EMPTY_MESSAGE = "";
	private static final String DEFAULT_MSG_SEPERATOR = "\n";
	protected Map<Integer, String> messages = new HashMap<Integer, String>();

	public Map<Integer, String> getMessages() {
		return messages;
	}

	public void addErrorMessage(Integer cellIndex, String msg) {
		this.messages.put(cellIndex, msg);
	}

	public boolean hasError() {
		return messages.size() > 0;
	}

	public String getAllMessages() {
		if (!hasError())
			return EMPTY_MESSAGE;
		StringBuilder sb = new StringBuilder();
		for (Integer key : messages.keySet()) {
			sb.append(messages.get(key));
			sb.append(DEFAULT_MSG_SEPERATOR);
		}
		return sb.toString();
	}

}
