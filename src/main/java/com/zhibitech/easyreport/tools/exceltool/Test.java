package com.zhibitech.easyreport.tools.exceltool;

import java.io.File;

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
		System.out.println(user.getPassword());
		ErrorExcelHandle errorExcelHandle = new ErrorExcelHandle("user.xlsx");

	}

}
