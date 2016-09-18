package com.ys.util.basequery;

public class QueryToExcelBean {
	private String model = "";
	private String firstRow = "";
	private String firstCol = "";
	private String dataIndex = "";
	
	public String getModel() {
		return this.model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	
	public String getFirstRow() {
		return this.firstRow;
	}
	public void setFirstRow(String firstRow) {
		this.firstRow = firstRow;
	}
	
	public String getFirstCol() {
		return this.firstCol;
	}
	public void setFirstCol(String firstCol) {
		this.firstCol = firstCol;
	}
	
	public String getDataIndex() {
		return this.dataIndex;
	}
	public void setDataIndex(String dataIndex) {
		this.dataIndex = dataIndex;
	}

	public void exportExcel() {
		
	}
}
