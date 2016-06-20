//package com.hztianque.exceltool.test;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//
//import com.hztianque.exceltool.data.ExcelData;
//import com.hztianque.exceltool.impt.DataValidateConvert;
//import com.hztianque.exceltool.impt.ErrorExcelHandle;
//import com.hztianque.exceltool.impt.ExcelReader;
//import com.hztianque.exceltool.validate.ValidateResult;
//
//public class Test {
//
//	public static void main(String[] args) {
//	   try {
//		ExcelData data = ExcelReader.readFile(new FileInputStream(new File("D:\\info.xls")));
//		String[][] dataList = data.getSheetDatas(0);
//		String[][] beanDatas = data.getSheetDatas(1);
//		String row8[] = dataList[7];
//		String fieldList [] = beanDatas[0];
//		DataValidateConvert<PeopleInfo> dataConvert = new PepoleInfoConvert();
//		ValidateResult vr = new ValidateResult();
//		//第一次类型转换的错误几类型定义验证;
//		PeopleInfo peopleInfo =  dataConvert.convertToEntity(dataList[3], vr, beanDatas);
//		System.err.println(vr.getMapMessages());
////		//第二次，业务级别的验证；
//		ValidateResult vt = dataConvert.validate(peopleInfo, 1, null,data.getFiledIndexMap());
////		vr.addAllErrorMessage(vt);
//		ErrorExcelHandle errorExcelUtil = new ErrorExcelHandle("a","测试从错误分析表");
//		errorExcelUtil.createErrorDataExcel("temp.xls");
//		try {
//			errorExcelUtil.createExcelCite();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
////		vr.getMapMessages().put("1", "格式不正确");
//     	errorExcelUtil.addErrorMessage(vr, dataList[3], 3, "11", 1);
//     	try {
//			errorExcelUtil.outFile(1, 1);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
////		//第二次、业务类型的错误;
//		System.out.println(peopleInfo.getName());
//		System.out.println(dataList[0].length);
//	} catch (FileNotFoundException e) {
//		e.printStackTrace();
//	} catch (IOException e) {
//		e.printStackTrace();
//	}
//
//	}
//
//}
