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


	/**
	 * formId
	 */
	public static final String FORM_KEY1 = "key1";//
	public static final String FORM_KEY2 = "key2";//
	public static final String FORM_KEYWORD1 = "keyword1";//
	public static final String FORM_KEYWORD2 = "keyword2";//
	public static final String FORM_MATERIAL = "material";//未检验
	public static final String FORM_PRODUCT= "product";//合格
	public static final String FORM_PRODUCTSEMI = "productsemi";//让步接收
	public static final String FORM_ORDER = "order";//退货
	
	
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
	
	/**
	 * 币种:与字典表关联
	 */
	public static final String CURRENCY_RMB = "035";//人民币
	
	/**
	 * 供应商编号:耀升
	 */
	public static final String SUPPLIER_YS = "0574YS00";
	/**
	 * BOM种类
	 */
	public static final String BOMTYPE_B = "B";//基础BOM
	public static final String BOMTYPE_Q = "Q";//报价BOM
	public static final String BOMTYPE_O = "O";//订单BOM

	/**
	 * 页面访问标识
	 */
	public static final String ACCESSFLG_0 = "";//新建标识
	public static final String ACCESSFLG_1 = "1";//编辑标识
	
	/**
	 * 订单费用种类
	 */
	public static final String ORDEREXPENSE_D = "D";//跟单费用
	public static final String ORDEREXPENSE_C = "C";//客户
	public static final String ORDEREXPENSE_S = "S";//供应商
	public static final String ORDEREXPENSE_W = "W";//车间
	
	/**
	 * 订单确认状态
	 */
	public static final String ORDEREXPENSE_0 = "0";//未确认
	public static final String ORDEREXPENSE_1 = "1";//已确认

	/**
	 * 进料报检状态
	 */
	public static final String ARRIVERECORD_0 = "010";//未检验
	public static final String ARRIVERECORD_1 = "020";//合格
	public static final String ARRIVERECORD_2 = "030";//让步接收
	public static final String ARRIVERECORD_3 = "040";//退货


}
