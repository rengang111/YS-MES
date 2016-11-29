package com.ys.util.basequery.common;

public class Constants {
	
	public static final String SYSTEMPROPERTYFILENAME = "/setting/system";
	public static final String FILTERADMIN = "filteradmin";
	public static final String FILTERDEFINE = "filterdefine";	
	public static final String ADMINROLE = "001";
	public static final String BUSINESSROLE = "003";	
	public static final String USER_SA = "001";
	public static final String USER_ADMIN = "002";
	public static final String USER_BUSINESS = "003";

	
	/*
	 * DB操作方式:0 插入;1 更新;2 删除
	 */
	public static final int ACCESSTYPE_INS = 0;
	public static final int ACCESSTYPE_UPD = 1;
	public static final int ACCESSTYPE_DEL = 2;
	
	/**
	 * 物料分类根目录的固定编码
	 */
	public static final String MATERIALCATEGORY = "000000";
	
	/**
	 * @return String 审批意见
	 * 
	 */
	public static final String APPROVE_DEF = "";
	public static final String APPROVE_OK = "0";
	public static final String APPROVE_NG = "1";
	
	/**
	 *@see "0";新建
	 *@see "1";BOM做成,待评审
	 *@see "2";已审核
	 *@see "3";采购方案做成
	 *@see "4";合同签订 
	 */
	public static final String ORDER_STS_0 = "0";//新建
	public static final String ORDER_STS_1 = "1";//BOM做成,待评审
	public static final String ORDER_STS_2 = "2";//已审核
	public static final String ORDER_STS_3 = "3";//采购方案做成
	public static final String ORDER_STS_4 = "4";//合同签订
}
