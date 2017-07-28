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
	public static final String FORM_MATERIAL = "material";//
	public static final String FORM_ARRIVAL = "arrival";//到货登记
	public static final String FORM_CONTRACT = "contract";//采购合同
	public static final String FORM_PRODUCT= "product";//成品管理
	public static final String FORM_PRODUCTSEMI = "productsemi";//让步接收
	public static final String FORM_ORDER = "order";//订单管理
	public static final String FORM_ORDERBOM= "orderbom";//订单BOM管理
	public static final String FORM_PURCHASEPLAN= "purchaseplan";//采购方案
	public static final String FORM_PURCHASEORDER= "purchaseorder";//采购合同
	public static final String FORM_RECEIVEINSPECTION= "receiveinspection";//进料报检
	public static final String FORM_STORAGE= "storage";//入库登记
	public static final String FORM_PRODUCTDETAIL= "productdetail";//做单资料
	public static final String FORM_SUPPLIER= "supplier";//供应商
	
	
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
	public static final String ORDER_STS_5 = "5";//合同执行中
	
	/**
	 * 币种:与字典表关联
	 */
	public static final String CURRENCY_RMB = "035";//人民币
	
	/**
	 * 供应商编号:耀升
	 */
	public static final String SUPPLIER_YS = "0574YS00";
	public static final String SUPPLIER_YZ = "0574YZ00";
	public static final String SUPPLIER_YH = "0574YH00";
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
	public static final String ARRIVERECORD_4 = "050";//入库

	/**
	 * 做单资料详情
	 */
	public static final String PRODUCTDETAIL_1 = "机器配置";//机器配置清单
	public static final String PRODUCTDETAIL_2 = "塑料制品";	//塑料制品
	public static final String PRODUCTDETAIL_3 = "配件清单";//配件清单
	public static final String PRODUCTDETAIL_4 = "产品收纳";//产品收纳
	public static final String PRODUCTDETAIL_5 = "标贴";	//标贴
	public static final String PRODUCTDETAIL_6 = "文字印刷";//文字印刷
	public static final String PRODUCTDETAIL_7 = "包装描述";


}
