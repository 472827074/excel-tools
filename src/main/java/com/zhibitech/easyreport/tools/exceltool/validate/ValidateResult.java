package com.zhibitech.easyreport.tools.exceltool.validate;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

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
