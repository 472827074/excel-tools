package com.zhibitech.exceltool.test;

import java.io.File;

import com.zhibitech.easyreport.tools.exceltool.DefaultExcelReader;
import com.zhibitech.easyreport.tools.exceltool.ErrorExcelHandle;
import com.zhibitech.easyreport.tools.exceltool.ExcelData;
import com.zhibitech.easyreport.tools.exceltool.ExcelReader;
import com.zhibitech.easyreport.tools.exceltool.convert.AbstractDataConverter;
import com.zhibitech.easyreport.tools.exceltool.convert.DefaultDataConverter;
import com.zhibitech.easyreport.tools.exceltool.validate.ValidateResult;

public class Test {

	public static void main(String[] args) throws Exception {
		ExcelReader reader = new DefaultExcelReader();
		String projectDir = System.getProperty("user.dir");
		System.out.println(projectDir);

		ExcelData data = reader.readFile(new File(projectDir + "\\template\\user.xlsx"));
		String excelData [][] = data.getSheetDatas(0);
		String beanData [][] = data.getSheetDatas(1);
		System.out.println(excelData.length);
		AbstractDataConverter convert = new DefaultDataConverter();
		convert.setValidate(null);
		ValidateResult validateResult = new ValidateResult();
		User user = convert.convertToObj(excelData[1], beanData[1], validateResult, User.class);
		System.out.println(user.getAge());
		ErrorExcelHandle errorExcelHandle = new ErrorExcelHandle("user.xlsx");
		errorExcelHandle.addErrorMessage(validateResult, excelData[1], 1);
		errorExcelHandle.outFile();

	}

}
