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
	public static final String FORM_REQUISITION = "requisition";//领料申请
	public static final String FORM_REQUISITIONVIRTUAL = "requisitionvirtual";//虚拟领料
	public static final String FORM_REQUISITION_M = "requisitionmateiral";//直接领料申请
	public static final String FORM_REQUISITION_C = "requisitionzz_C";//吹塑领料申请
	public static final String FORM_REQUISITION_Z = "requisitionzz_Z";//注塑领料申请
	public static final String FORM_REQUISITION_X = "requisitionzz_X";//吸塑领料申请
	public static final String FORM_CONTRACT = "contract";//采购合同
	public static final String FORM_CONTRACT_UNFINISHED = "contract";//未订合同
	public static final String FORM_CONTRACTZZ = "contractzz";//采购合同
	public static final String FORM_PRODUCT= "product";//成品管理
	public static final String FORM_PRODUCTSEMI = "productsemi";//让步接收
	public static final String FORM_ORDER = "order";//订单管理
	public static final String FORM_ORDERCANCEL = "ordercancel";//订单管理
	public static final String FORM_ORDERTRACKING = "ordertracking";//订单跟踪
	public static final String FORM_ORDERBOM= "orderbom";//订单BOM管理
	public static final String FORM_PURCHASEPLAN= "purchaseplan";//采购方案
	public static final String FORM_PURCHASEORDER= "purchaseorder";//采购合同
	public static final String FORM_RECEIVEINSPECTION= "receiveinspection";//进料报检
	public static final String FORM_MATERIALSTORAGE= "materialstorage";//料检入库登记
	public static final String FORM_MATERIALSTOCKOUT= "materialstockout";//料检出库
	public static final String FORM_DEVELOPSTOCKOUT= "developstockout";//研发出库
	public static final String FORM_PRODUCTSTORAGE= "productstorage";//成品入库登记
	public static final String FORM_PRODUCTDETAIL= "productdetail";//做单资料
	public static final String FORM_SUPPLIER= "supplier";//供应商
	public static final String FORM_CUSTOMER = "order";//客户管理
	public static final String FORM_WORKSHOPRETURN = "workshopreturn";//车间退货
	public static final String FORM_INSPECTIONRETURN = "inspectionreturn";//报检退货
	public static final String FORM_PURCHASEROUTINE = "purchaseroutine";//日常采购
	public static final String FORM_PAYMENTREQUEST= "paymentrequest";//应付款申请
	public static final String FORM_PAYMENTAPPROVAL= "paymentapproval";//应付款审核
	public static final String FORM_FINANCESTOCKIN= "financestockin";//财务入库管理
	public static final String FORM_MATERIALSTOCKIN= "materialstockin";//直接入库
	public static final String FORM_STOCKINAPPLY= "stockinapply";//直接入库申请
	public static final String FORM_PRODUCTSTOCKOUT= "productstockout";//成品出库
	public static final String FORM_BEGINNINGINVENTROY= "beginninginventroy";//期初库存
	public static final String FORM_REPORTFORDAYBOOK= "reportForDaybook";//财务流水账
	public static final String FORM_REPORTFORINVENTORY= "reportForInventoryMonthly";//收发存总表
	public static final String FORM_BEGINNINGINVENTROY3= "reportForDaybook3";//收发存总表
	public static final String FORM_DEPOTRETURN= "depotReturn";//仓库退货
	
	
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
	 *订单状态
	 */
	public static final String ORDER_STS_0 = "0";//初始值
	public static final String ORDER_STS_1 = "010";//待合同
	public static final String ORDER_STS_2 = "020";//待到料
	public static final String ORDER_STS_3 = "030";//待交货
	public static final String ORDER_STS_4 = "040";//已入库
	public static final String ORDER_STS_41 = "041";//入库中
	public static final String ORDER_STS_5  = "050";//已出库
	public static final String ORDER_STS_51 = "051";//出库中
	
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
	 * 物料特性
	 */
	public static final String PURCHASETYPE_G = "G";//包装件
	public static final String PURCHASETYPE_H = "H";//人工
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
	 * 做单资料详情
	 */
	public static final String PRODUCTDETAIL_1 = "机器配置";//机器配置清单
	public static final String PRODUCTDETAIL_2 = "塑料制品";	//塑料制品
	public static final String PRODUCTDETAIL_3 = "配件清单";//配件清单
	public static final String PRODUCTDETAIL_4 = "产品收纳";//产品收纳
	public static final String PRODUCTDETAIL_5 = "标贴";	//标贴
	public static final String PRODUCTDETAIL_6 = "文字印刷";//文字印刷
	public static final String PRODUCTDETAIL_7 = "包装描述";

	/**
	 *@category 订单类型
	 *
	 */
	public static final String ORDERTYPE_1 = "010";//正常订单
	public static final String ORDERTYPE_2 = "020";//配件订单
	
	/**
	 *@category 物料采购类别
	 *
	 */
	public static final String PURCHASE_TYPE_D = "010";//订购件
	public static final String PURCHASE_TYPE_T = "020";//通用件
	public static final String PURCHASE_TYPE_Y = "040";//自制品
	public static final String PURCHASE_TYPE_O = "030";//其他
	
	/**
	 *@category 合同类别
	 *
	 */
	public static final String CONTRACT_TYPE_D = "D";//订购件
	public static final String CONTRACT_TYPE_T = "T";//通用件
	public static final String CONTRACT_TYPE_Y = "Y";//自制品
	public static final String CONTRACT_TYPE_O = "O";//其他
	

	/**
	 *@category 合同状态
	 *
	 */
	public static final String CONTRACT_STS_1 = "010";//未执行
	public static final String CONTRACT_STS_2 = "020";//收货中
	public static final String CONTRACT_STS_3 = "030";//已清
	public static final String CONTRACT_STS_4 = "040";//强制结束
	
	/**
	 * 合同执行流程状态
	 */
	public static final String CONTRACT_PROCESS_1 = "010";//待报检
	public static final String CONTRACT_PROCESS_2 = "020";//待入库
	public static final String CONTRACT_PROCESS_3 = "030";//完成
	public static final String CONTRACT_PROCESS_4 = "040";//完成(退货)
	
	
	/**
	 * 到货登记状态
	 */
	
	public static final String ARRIVAL_STS_1 = "010";//待报检
	public static final String ARRIVAL_STS_2 = "020";//已报检

	public static final String ARRIVAL_TYPE_1 = "010";//合同收货
	public static final String ARRIVAL_TYPE_2 = "020";//直接入库
	
	/**
	 * 进料报检结果
	 */
	public static final String ARRIVERECORD_1 = "020";//合格
	public static final String ARRIVERECORD_2 = "030";//让步接收
	public static final String ARRIVERECORD_3 = "040";//退货
	
	/**
	 * 料件出库
	 */
	public static final String STOCKOUT_1 = "010";//待申请
	public static final String STOCKOUT_2 = "020";//待出库
	public static final String STOCKOUT_3 = "030";//已出库

	/**
	 * 退货处理状态
	 */
	
	public static final String INSPECTIONRETURN_STS_1 = "010";//未处理
	public static final String INSPECTIONRETURN_STS_2 = "020";//已处理
	
	/**
	 *@category 订单属性
	 *
	 */
	public static final String ORDER_RETURNQUANTY = "2";//订单返还数量默认值
	
	/**
	 * 领料类别
	 */
	public static final String REQUISITION_PARTS = "010";//装配领料
	public static final String REQUISITION_BLOW = "020";//吹塑
	public static final String REQUISITION_BLISTE = "030";//吸塑
	public static final String REQUISITION_INJECT = "040";//注塑
	public static final String REQUISITION_51 = "051";//直接领料
	public static final String REQUISITION_52 = "052";//研发领料

	public static final String REQUISITION_NORMAL = "010";//正常领料
	public static final String REQUISITION_VIRTUAL = "020";//虚拟领料
	
	/**
	 * 付款状态
	 */
	public static final String payment_010 = "010";//待申请
	public static final String payment_020 = "020";//待审核
	public static final String payment_030 = "030";//待付款
	public static final String payment_040 = "040";//部分付款
	public static final String payment_050 = "050";//完成
	public static final String payment_060 = "060";//审核未通过

	/**
	 * 入库类别
	 */
	public static final String STOCKINTYPE_1 = "010";//直接入库
	public static final String STOCKINTYPE_21 = "021";//原材料入库
	public static final String STOCKINTYPE_22 = "022";//自制件入库
	public static final String STOCKINTYPE_23 = "023";//装配件入库
	public static final String STOCKINTYPE_24 = "024";//包装品入库
	public static final String STOCKINTYPE_3 = "030";//成品入库
	
	/**
	 * 出库类别
	 */
	public static final String STOCKOUTTYPE_1 = "010";//料件出库
	public static final String STOCKOUTTYPE_2 = "020";//成品出库
	public static final String STOCKOUTTYPE_3 = "030";//原材料出库
	public static final String STOCKOUTTYPE_51 = "051";//单独出库
	public static final String STOCKOUTTYPE_52 = "052";//研发出库
	
	/**
	 * 重点关注对象
	 */
	public static final String FOLLOWTYPE_1 = "1";//订单
	
	public static final String FOLLOWSTATUS_0 = "0";//关注
	public static final String FOLLOWSTATUS_1 = "1";//取消关注


	/**
	 * 退货类别
	 */	
	public static final String RETURNTYPE_1 = "010";//检验退货
	public static final String RETURNTYPE_2 = "020";//入库退货
}
