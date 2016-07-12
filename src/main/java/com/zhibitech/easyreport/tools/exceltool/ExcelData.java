package com.zhibitech.easyreport.tools.exceltool;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  姓名 工号
 * @version  [版本号, 2016年7月5日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ExcelData implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final int DATA = 0;
	public static final int BEAN = 1;
	// 总的sheet,结构为,sheet索引->sheet内容集合，结构为行号->行号对应的列值对集合。
	private Map<Integer, Map<Integer, Map<Integer, String>>> sheets = new HashMap<Integer, Map<Integer, Map<Integer, String>>>();
	// 当前获取的sheet类容集合
	private Map<Integer, Map<Integer, String>> currentSheet = null;

	private Map<String, Integer> filedIndexMap = new HashMap<String, Integer>();

	private List<int[]> sheetCellSize = new ArrayList<int[]>();
    //行列数
	private int[] currentSheetCellSize = null;

	public Map<String, Integer> getFiledIndexMap() {
		String[][] beanDatas = getSheetDatas(ExcelData.BEAN);
		String fieldList[] = beanDatas[0];
		for (int i = 0; i < fieldList.length; i++) {
			filedIndexMap.put(fieldList[i], i);

		}
		return filedIndexMap;
	}

	public int getSheetCount() {
		return sheets.keySet().size();
	}

	public String[][] getSheetDatas(int sheetIndex) {
		// 获取数据行
		int rowCount = getRowCount(sheetIndex);
		// 获取数据列
		int columnCount = getColumnCount(sheetIndex);
		// 当前sheet数据的行列2维数组；
		String[][] result = new String[rowCount][columnCount];
		// 获取指定的sheet数据集合;
		Map<Integer, Map<Integer, String>> sheetDatas = sheets.get(sheetIndex);
		// 迭代数据行;
		for (int row = 0; row < rowCount; row++) {
			// 获取一行数据
			Map<Integer, String> rowData = sheetDatas.get(row);
			for (int column = 0; column < columnCount; column++) {
				// 如果当前行为空，切不包含此列的值,设置空值
				if (rowData == null || !rowData.containsKey(column)) {
					result[row][column] = "";
				} else {
					// 设置当且值
					result[row][column] = rowData.get(Integer.valueOf(column));
				}
			}
		}
		return result;
	}

	public void addWorkSheet() {
		currentSheet = new HashMap<Integer, Map<Integer, String>>();
		sheets.put(Integer.valueOf(getSheetCount()), currentSheet);
		currentSheetCellSize = new int[] { 0, 0 };
		sheetCellSize.add(currentSheetCellSize);
	}

	public void addString(int row, short column, String value) {
		Map<Integer, String> rowData = currentSheet.get(row);
		if (rowData == null) {
			rowData = new HashMap<Integer, String>();
			currentSheet.put(row, rowData);
		}
		rowData.put(Integer.valueOf(column), value);
		updateCellSize(row, column);
	}

	private void updateCellSize(int row, short column) {
		currentSheetCellSize[0] = Math.max(currentSheetCellSize[0], row + 1);
		currentSheetCellSize[1] = Math.max(currentSheetCellSize[1], column + 1);
	}

	private int getColumnCount(int sheetIndex) {
		int[] sizes = sheetCellSize.get(sheetIndex);
		return sizes[1];
	}

	private int getRowCount(int sheetIndex) {
		int[] sizes = sheetCellSize.get(sheetIndex);
		return sizes[0];
	}

	public Map<Integer, Map<Integer, Map<Integer, String>>> getSheets() {
		return sheets;
	}

	public void setSheets(Map<Integer, Map<Integer, Map<Integer, String>>> sheets) {
		this.sheets = sheets;
	}

}
