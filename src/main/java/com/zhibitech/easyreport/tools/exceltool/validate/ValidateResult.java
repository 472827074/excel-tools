package com.zhibitech.easyreport.tools.exceltool.validate;

import java.io.Serializable;

public class ValidateResult extends EntityValidateResult implements
		Serializable {

	private static final long serialVersionUID = 8758821696627253640L;

	

	public void addErrorMessage(int row, int column, String msg) {
		messages.add(
				"在单元格[" + convertToExcelCell(row, column - 1) + "]发现数据错误，错误为："
						+ msg);
	}

	public void addErrorMessage(int row, String msg) {
		if (row > 0) {
			messages.add("单元行第[" + row + "]行" + msg);
		} else if (row == -1) {
			messages.add(msg);
		} else {
			messages.add("单元行第[" + row + "]行" + msg);
		}

	}

	private String convertToExcelCell(int row, int column) {
		return getCellColumnString(column) + row;
	}

	private String getCellColumnString(int column) {
		int letterPrefixIndex = column / 26;
		int letterIndex = column % 26;
		if (letterPrefixIndex > 0) {
			return getCellColumnString(letterPrefixIndex - 1)
					+ getCellColumnString(letterIndex);
		}
		return String.valueOf((char) (65 + letterIndex));
	}

}
