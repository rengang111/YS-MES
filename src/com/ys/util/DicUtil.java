package com.ys.util;

import com.ys.business.action.model.common.ListOption;
import com.ys.util.basedao.BaseDAO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.dom4j.Element;

public class DicUtil {
	
	private static final String DATABUFFERRESOURCE = "/setting/Sys_DataBuffer.xml";
	
	public static final String ROLETYPE = "A0";
	public static final String UNIT = "A1";
	public static final String ADDRESS = "A2";
	public static final String DUTIES = "A3";
	public static final String DICSELECTEDFLAG = "A4";
	public static final String DICTYPELEVEL = "A5";
	public static final String SEX = "A6";
	public static final String UNITPROPERTY = "A7";
	public static final String UNITTYPE = "A8";
	public static final String MENUTYPE = "A9";
	public static final String ORGANTYPE = "机构类别";
	public static final String MEASURESTYPE = "计量单位";
	public static final String PURCHASETYPE = "物料采购类别";
	public static final String CURRENCY = "币种";
	public static final String LOADINGPORT = "出运港";
	public static final String DELIVERYPORT = "目的港";
	public static final String MANAGEMENTRATE = "经管费率";
	public static final String BUSINESSTEAM = "业务组";
	public static final String ORDERCOMPANY = "下单公司";
	public static final String PRODUCTCLASSIFY = "版本类别";
	public static final String ORDERNATURE = "订单性质";
	public static final String TAXREBATERATE = "退税率";
	public static final String CHARGETYPE = "扣款方式";

	public static final String PRODUCTMODEL = "产品型号";
	public static final String CONFIRMRESULT = "确认结果";//合格/不合格
	public static final String MOULDTYPE = "模具类型";
	public static final String POSITION = "存放位置";

	public static final String COUNTRY = "国家";
	public static final String ZZMATERIALTYPE = "自制类别";
	
	public static final String ACCEPTRESULT = "验收结果";

	//TODO
	public static final String DENOMINATIONCURRENCY = "币种";
	public static final String SHIPPINGCASE = "出运条件";
	public static final String SUPPLIER_TYPE = "供应商类型";
	public static final String MOULDBELONG = "模具归属";
	public static final String RECEIVEINSPECTION = "进料检验结果";
	public static final String DIC_CHARGERTYPE = "充电器";
	public static final String DIC_BATTERYPACK = "电池包数量";
	public static final String DIC_PURCHASER = "采购方";
	public static final String DIC_PRODUCTDESIGNSTATUS = "做单资料状态";
	public static final String DIC_PACKAGING = "包装方式";
	public static final String DIC_APPROVL = "审核结果";
	public static final String DIC_INVOICETYPE = "发票类型";
	public static final String DIC_PAYMENTMETHOD = "付款方式";
	public static final String DIC_REQUISITION_USEDTYPE = "单独领料用途";
	public static final String DIC_DEPOTLIST = "仓库分类";
	public static final String DIC_STORAGEFINISH = "成品入库完结";
	//HashMap通过id查找
	private static HashMap<String,String> dicMapViaId = new HashMap<String, String>();
	//HashMap通过类型查找
	private static HashMap<String, ArrayList<ArrayList<String>>> dicMapViaType = new HashMap<String, ArrayList<ArrayList<String>>>();	
	//HashMap(单位)
	private static HashMap<String,String> dicMapUnit = new HashMap<String, String>();
	
	private static int parentCodeIndex = 3;
	
	public int getParentCodeIndex() {
		return this.parentCodeIndex;
	} 
	public void setParentCodeIndex(int parentCodeIndex) {
		this.parentCodeIndex = parentCodeIndex;
	}
	
	//根据code值取得codename
	public static String getCodeValue(String dicCode) throws Exception {
		String codeValue = "";
		
		if (dicMapViaId.isEmpty()) {
			getDicValue();
		}
		
		if (dicMapViaId.containsKey(dicCode)) {
			codeValue = dicMapViaId.get(dicCode);
		} else {
			if (dicMapUnit.containsKey(dicCode)) {
				codeValue = dicMapUnit.get(dicCode);
			}
		}
		return codeValue;
	}
	
	//根据类型取得所有的字典数据
	public static ArrayList<ArrayList<String>> getGroupValue(String type) throws Exception {

		ArrayList<ArrayList<String>> codeGroupValue = new ArrayList<ArrayList<String>>();
		if (dicMapViaId.isEmpty()) {
			getDicValue();
		}

		if (dicMapViaType.containsKey(type)) {
			codeGroupValue = dicMapViaType.get(type);
		}
		
		return codeGroupValue;
	}
	
	//根据给定的父code得到该父code所属的全部code
	public ArrayList<ArrayList<String>> getSameParentGroupValue(String type, String parentCode, boolean isCycleGet) throws Exception {

		ArrayList<ArrayList<String>> sameParentIdGroupValue = new ArrayList<ArrayList<String>>();
		
		getDicChain(type, parentCode, isCycleGet, sameParentIdGroupValue);
		
		return sameParentIdGroupValue;
	}	
	
	private ArrayList<ArrayList<String>> getDicChain(String type, String parentCode, boolean isCycleGet, ArrayList<ArrayList<String>> sameParentIdGroupValue) throws Exception {

		ArrayList<ArrayList<String>> codeGroupValue = new ArrayList<ArrayList<String>>();
		
		if (dicMapViaId.isEmpty()) {
			getDicValue();
		}
		if (dicMapViaType.containsKey(type)) {
			codeGroupValue = dicMapViaType.get(type);

			for(ArrayList<String> rowData:codeGroupValue) {
				if (rowData.get(parentCodeIndex).equals(parentCode)) {
					sameParentIdGroupValue.add(rowData);
					if (isCycleGet) {
						String subParentCode = rowData.get(0);
						ArrayList<ArrayList<String>> subParentCodeList = getDicChain(type, subParentCode, isCycleGet, sameParentIdGroupValue);
					}
				}
				//if (!subParentCode.equals("")) {
				//	if (subParentCode.substring(0, parentCode.length()).equals(parentCode)) {
				//		sameParentIdGroupValue.add(rowData);
				//	}
				//}
			}
		}		
		
		return sameParentIdGroupValue;
	}
	
	//清空字典
	public static void emptyBuffer(boolean isUn) {
		if (isUn) {
			dicMapUnit.clear();
		} else {
			dicMapViaId.clear();
			dicMapViaType.clear();
		}
		
	}
	
	//重载字典
	public static void refresh() throws Exception {
		getDicValue();
	}
	
	private static void getDicValue() throws Exception {
		
		Element element = XmlUtil.getRootElement(DATABUFFERRESOURCE);
		
		if (element != null) {
			Iterator<Element> iter = element.elementIterator();
			while(iter.hasNext()){
				ArrayList<ArrayList<String>> dicGroupData = null;
			    Element el = (Element)iter.next();
				String sql = el.getText();
				
				dicGroupData = BaseDAO.execSQL(sql, "", 0, 0);
				pushMap(dicGroupData);
			}
		}
			
	}
	
	private static void pushMap(ArrayList<ArrayList<String>> dicGroupData) {
		String preDicType = "";
		boolean isFirst = true;
		ArrayList<ArrayList<String>> groupData = new ArrayList<ArrayList<String>>();
		
		for(ArrayList<String> rowData:dicGroupData) {
			String dicCode = rowData.get(0);
			String curDicType = rowData.get(2);
			if (curDicType.equals(UNIT)) {
				dicMapUnit.put(curDicType + dicCode, rowData.get(1));
			} else {
				dicMapViaId.put(curDicType + dicCode, rowData.get(1));
			}
			if(isFirst) {
				preDicType = curDicType;
				isFirst = false;
			}
			
			if (preDicType.equals(curDicType)) {
				groupData.add(rowData);
			} else {
				dicMapViaType.put(preDicType, groupData);
				groupData = new ArrayList<ArrayList<String>>();
				groupData.add(rowData);
				preDicType = curDicType;
			}
		}
		if (groupData.size() > 0) {
			dicMapViaType.put(preDicType, groupData);
		}
	}
	
	public ArrayList<ListOption> getListOption(String type, String parentCode) throws Exception {
		DicUtil util = new DicUtil();
		ArrayList<ArrayList<String>> dicList = null;
		ArrayList<ListOption> rtnData = new ArrayList<ListOption>();
		
		dicList = util.getSameParentGroupValue(type, parentCode, false);
		for(ArrayList<String>rowData:dicList) {
			ListOption option = new ListOption(rowData.get(0), rowData.get(1));
			rtnData.add(option);
		}
		
		return rtnData;
	}
}
