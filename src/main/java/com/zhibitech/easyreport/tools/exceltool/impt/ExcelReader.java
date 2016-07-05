package com.zhibitech.easyreport.tools.exceltool.impt;

import java.io.FileInputStream;
import java.io.IOException;

import com.zhibitech.easyreport.tools.exceltool.data.ExcelData;

/**
 * excel数据读取
 * 
 * @author yumeng
 * @version  [版本号, 2016年6月28日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface ExcelReader {

	public ExcelData readFile(FileInputStream file) throws IOException;

}
