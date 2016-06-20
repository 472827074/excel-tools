package com.zhibitech.easyreport.tools.exceltool.validate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;


public class EntityValidateResult implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String EMPTY_MESSAGE = "";
	private static final String DEFAULT_MSG_SEPERATOR = "\n";

	protected List<String> messages = new ArrayList<String>();

	protected Map<String, String> mapMessages = new HashMap<String, String>();

	public void addAllErrorMessage(EntityValidateResult validateResult) {
		messages.addAll(validateResult.messages);
		mapMessages.putAll(validateResult.mapMessages);
	}

	public void addErrorMessage(String msg) {

		if (StringUtils.isEmpty(msg) && msg.indexOf("[", 2) != -1)
			this.mapMessages.put(msg.substring(msg.indexOf("[", 2) + 1,
					msg.lastIndexOf("]")), msg.substring(msg.indexOf("列") + 1));
		this.messages.add(msg);
	}

	public void addErrorMessage(String filed, String msg) {
		this.mapMessages.put(filed.substring(filed.indexOf("[", 2) + 1,
				msg.lastIndexOf("]")), msg);
		this.messages.add(filed + msg);
	}

	public boolean hasError() {
		return messages.size() > 0;
	}

	public boolean hasMapError() {
		return mapMessages.size() > 0;
	}

	public String getMapErrorMessages() {
		if (!hasMapError())
			return EMPTY_MESSAGE;
		StringBuilder sb = new StringBuilder();
		for (String key : mapMessages.keySet()) {
			sb.append(mapMessages.get(key));
			sb.append(DEFAULT_MSG_SEPERATOR);
		}
		return sb.toString();
	}

	public String getErrorMessages() {
		if (!hasError())
			return EMPTY_MESSAGE;
		StringBuilder sb = new StringBuilder();
		for (String message : messages) {
			sb.append(message);
			sb.append(DEFAULT_MSG_SEPERATOR);
		}
		return sb.toString();
	}

}
