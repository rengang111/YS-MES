package com.ys.business.service.order;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ys.system.action.model.login.UserInfo;
import com.ys.util.CalendarUtil;
import com.ys.util.DicUtil;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basedao.BaseTransaction;
import com.ys.util.basequery.BaseQuery;
import com.ys.util.basequery.common.BaseModel;
import com.ys.util.basequery.common.Constants;
import com.ys.business.action.model.common.ListOption;
import com.ys.business.action.model.order.PurchaseOrderModel;
import com.ys.business.db.dao.B_ArrivalDao;
import com.ys.business.db.dao.B_FollowDao;
import com.ys.business.db.dao.B_MaterialDao;
import com.ys.business.db.dao.B_PurchaseOrderDao;
import com.ys.business.db.dao.B_PurchaseOrderDetailDao;
import com.ys.business.db.dao.B_PurchasePlanDetailDao;
import com.ys.business.db.dao.B_PurchaseStockInDetailDao;
import com.ys.business.db.data.B_ArrivalData;
import com.ys.business.db.data.B_FollowData;
import com.ys.business.db.data.B_MaterialData;
import com.ys.business.db.data.B_PurchaseOrderData;
import com.ys.business.db.data.B_PurchaseOrderDetailData;
import com.ys.business.db.data.B_PurchasePlanDetailData;
import com.ys.business.db.data.B_PurchaseStockInDetailData;
import com.ys.business.db.data.B_SupplierData;
import com.ys.business.db.data.CommFieldsData;
import com.ys.business.service.common.BusinessService;
import com.ys.business.service.order.CommonService;

@Service
public class PurchaseOrderService extends CommonService {

	DicUtil util = new DicUtil();

	BaseTransaction ts;

	String guid ="";
	private CommFieldsData commData;
	
	private HttpServletRequest request;
	
	private B_PurchaseOrderDao orderDao;
	private B_PurchaseOrderDetailDao detailDao;
	private PurchaseOrderModel reqModel;
	private UserInfo userInfo;
	private BaseModel dataModel;
	private  Model model;
	private HashMap<String, String> userDefinedSearchCase;
	private BaseQuery baseQuery;
	ArrayList<HashMap<String, String>> modelMap = null;	
	HttpSession session;

	public PurchaseOrderService(){
		
	}

	public PurchaseOrderService(Model model,
			HttpServletRequest request,
			HttpSession session,
			PurchaseOrderModel reqModel,
			UserInfo userInfo){
		
		this.orderDao = new B_PurchaseOrderDao();
		this.detailDao = new B_PurchaseOrderDetailDao();
		this.model = model;
		this.reqModel = reqModel;
		this.request = request;
		this.session = session;
		this.userInfo = userInfo;
		this.dataModel = new BaseModel();
		this.userDefinedSearchCase = new HashMap<String, String>();
		this.dataModel.setQueryFileName("/business/order/purchasequerydefine");
		super.request = request;
		super.userInfo = userInfo;
		super.session = session;
		
	}

	public HashMap<String, Object> getContractList(
			String data,String formId,String makeType) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();

		data = URLDecoder.decode(data, "UTF-8");
		
		int iStart = 0;
		int iEnd =0;
		String sEcho = getJsonData(data, "sEcho");	
		String start = getJsonData(data, "iDisplayStart");		
		if (start != null && !start.equals("")){
			iStart = Integer.parseInt(start);
		}
		
		String length = getJsonData(data, "iDisplayLength");
		if (length != null && !length.equals("")){			
			iEnd = iStart + Integer.parseInt(length);			
		}
	
		dataModel.setQueryName("getContractList");
		
		baseQuery = new BaseQuery(request, dataModel);		
		
		String[] keyArr = getSearchKey(formId,data,session);
		String key1 = keyArr[0];
		String key2 = keyArr[1];
		String year   = request.getParameter("year");
		String status = request.getParameter("status");
		String userId = request.getParameter("userId");
		String monthday = request.getParameter("monthday");
		String having = "1=1";
		
		if(isNullOrEmpty(year)){//年份选择
			year =  CalendarUtil.getYear();//当前年
		}
		if(("999").equals(userId)){
			userDefinedSearchCase.put("userId", "");//999:查询全员
		}
		
		//if(isNullOrEmpty(monthday) && ("2").equals(status)){//已入库：全年查询
		//	userDefinedSearchCase.put("year", year);
		//}
		
		if(("G").equals(makeType)){
			//包装件
			//where = "&materialId=G&status=030";
			userDefinedSearchCase.put("materialId", "G");
			userDefinedSearchCase.put("status", "030");
			
			
		}else if(("Z").equals(makeType)){
			//自制件
			//where = "&supplierId=0574YZ00&status=030";
			userDefinedSearchCase.put("supplierId", "0574YZ00");
			userDefinedSearchCase.put("status", "030");
			
		}else if(("C").equals(makeType)){
			//采购件
			//where = "&supplierId2=0574YZ00&materialId2=G&status=030&purchaseType=010";
			userDefinedSearchCase.put("supplierId2", "0574YZ00");
			userDefinedSearchCase.put("status", "030");
			userDefinedSearchCase.put("materialId2", "G");
			//userDefinedSearchCase.put("purchaseType", "010");
			
		}
		//
		if(notEmpty(key1) || notEmpty(key2)){
			userDefinedSearchCase.put("purchaseType", "");//关键字查询,忽略其类型(订购件)
			userDefinedSearchCase.put("supplierId2", "");//关键字查询,忽略其类型(自制件)
			userDefinedSearchCase.put("materialId2", "");//关键字查询,忽略其类型(包装件)
		}
		
		
		if(("2").equals(status)){//已入库
			having = " ((REPLACE(quantity, ',', '') + 0) <= (REPLACE (contractStorage, ',', '') + 0)) ";
		}else if(("3").equals(status)){//未入库已付款
			having = " ((REPLACE(quantity, ',', '') + 0) >  (REPLACE (contractStorage, ',', '') + 0)) ";
			having += " AND ((paymentSts + 0) >  30) ";
			userDefinedSearchCase.put("deliveryDate", "");
		}else if(("1").equals(status)){//未入库
			having = " ((REPLACE(quantity, ',', '') + 0) >  (REPLACE (contractStorage, ',', '') + 0)) ";
			userDefinedSearchCase.put("deliveryDate", "");
		}else{//ALL
			//having = " ((REPLACE(quantity, ',', '') + 0) >  (REPLACE (contractStorage, ',', '') + 0)) ";
			userDefinedSearchCase.put("deliveryDate", "");
			//userDefinedSearchCase.put("year", CalendarUtil.getYear());//当前年
		}
		
		if(("2").equals(status)){//已入库：全年查询
			userDefinedSearchCase.put("year", year);
			userDefinedSearchCase.put("monthday", monthday);
		}else{
			userDefinedSearchCase.put("year", "");
			userDefinedSearchCase.put("monthday", "");			
		}
				
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		String sql = getSortKeyFormWeb(data,baseQuery);
		
		sql = sql.replace("#", having);
		System.out.println("采购合同查询SQL："+sql);
		
		baseQuery.getYsQueryData(sql,having,iStart, iEnd);	 
		
		if ( iEnd > dataModel.getYsViewData().size()){			
			iEnd = dataModel.getYsViewData().size();			
		}		
		
		modelMap.put("sEcho", sEcho); 		
		modelMap.put("recordsTotal", dataModel.getRecordCount());		
		modelMap.put("recordsFiltered", dataModel.getRecordCount());			
		modelMap.put("data", dataModel.getYsViewData());
		modelMap.put("keyword1",key1);	
		modelMap.put("keyword2",key2);
		
		return modelMap;
	}
	

	public HashMap<String, Object> getUnfinishedContractList(
			String data,String formId) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();

		data = URLDecoder.decode(data, "UTF-8");
		
		int iStart = 0;
		int iEnd =0;
		String sEcho = getJsonData(data, "sEcho");	
		String start = getJsonData(data, "iDisplayStart");		
		if (start != null && !start.equals("")){
			iStart = Integer.parseInt(start);			
		}
		
		String length = getJsonData(data, "iDisplayLength");
		if (length != null && !length.equals("")){			
			iEnd = iStart + Integer.parseInt(length);			
		}
	
		dataModel.setQueryName("unfinishedContractList");
		
		baseQuery = new BaseQuery(request, dataModel);		
		
		String[] keyArr = getSearchKey(formId,data,session);
		String key1 = keyArr[0];
		String key2 = keyArr[1];
		String status = request.getParameter("status");
		String having = "1=1";
		if(notEmpty(key1) || notEmpty(key2)){
			status = "";//关键字查询,忽略其状态
			userDefinedSearchCase.put("purchaseType", "");//关键字查询,忽略其类型(订购件)
			userDefinedSearchCase.put("supplierId2", "");//关键字查询,忽略其类型(自制件)
			userDefinedSearchCase.put("materialId2", "");//关键字查询,忽略其类型(包装件)
		}
		
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		String sql = getSortKeyFormWeb(data,baseQuery);
		
		if(notEmpty(status)){
			having = "((REPLACE(quantity,',','')+0) - (REPLACE(arrivalQty,',','')+0) + (REPLACE(returnQty,',','')+0) ) > 0 ";
		}
		sql = sql.replace("#", having);
		baseQuery.getYsQueryData(sql,having,iStart, iEnd);	 
		
		if ( iEnd > dataModel.getYsViewData().size()){			
			iEnd = dataModel.getYsViewData().size();			
		}		
		
		modelMap.put("sEcho", sEcho); 		
		modelMap.put("recordsTotal", dataModel.getRecordCount());		
		modelMap.put("recordsFiltered", dataModel.getRecordCount());			
		modelMap.put("data", dataModel.getYsViewData());
		modelMap.put("keyword1",key1);	
		modelMap.put("keyword2",key2);
		
		return modelMap;
	}
	
	
	public void getZZOrderDetail( 
			String YSId) throws Exception {

		dataModel.setQueryName("getRequirementListByYSId");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("YSId", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		modelMap = baseQuery.getYsQueryData(0, 0);
			
		model.addAttribute("order",dataModel.getYsViewData().get(0));		
		model.addAttribute("detail", dataModel.getYsViewData());
		
	}

	public  ArrayList<HashMap<String, String>> getRequirementBySupplierId( 
			String YSId,String supplierId) throws Exception {

		dataModel.setQueryName("getRequirementBySupplierId");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("YSId", YSId);
		userDefinedSearchCase.put("supplierId", supplierId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
				
		return baseQuery.getYsQueryData(0, 0);
		
				
	}
	
	public ArrayList<HashMap<String, String>> getSupplierList(String YSId) throws Exception {
		
		ArrayList<HashMap<String, String>> supplierList = null;
		
		dataModel.setQueryName("getContractSupplierList");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("YSId", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		supplierList = baseQuery.getYsQueryData(0, 0);
				
		return supplierList;	
		
	}
	
	
	
	public void getWorkshopReturnList(
			String YSId,String contractId) throws Exception {
				
		dataModel.setQueryName("getWorkshopReturnList");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("YSId", YSId);
		userDefinedSearchCase.put("contractId", contractId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();					
		model.addAttribute("workshop",dataModel.getYsViewData());
	}
	
	public void getWorkshopReturnList(String workshopReturnId) throws Exception {
				
		dataModel.setQueryName("getWorkshopReturnList");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("workshopReturnId", workshopReturnId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();					
		model.addAttribute("workshop",dataModel.getYsViewData().get(0));
		model.addAttribute("workshopDetail",dataModel.getYsViewData());
	}
	
	@SuppressWarnings("unchecked")
	public List<B_PurchaseOrderData> getSupplierContrct(String YSId) throws Exception {
			
		String astr_Where = "YSId = '"+YSId+"'";
		return (List<B_PurchaseOrderData>)orderDao.Find(astr_Where);
	}
	

	/**
	 * 取得合同详情
	 */
	public void getContractDetailList(String contractId) throws Exception {

		if(null == contractId || ("").equals(contractId))
			return;

		dataModel.setQueryFileName("/business/order/purchasequerydefine");
		dataModel.setQueryName("getContractDetailList");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("contractId", contractId);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();	 

		model.addAttribute("contract",dataModel.getYsViewData().get(0));		
		model.addAttribute("detail", dataModel.getYsViewData());
		
	}	
	
	/**
	 * 
	 */
	public void getMaterialDetailList(String supplierId,
			String materialId) throws Exception {

		//if(null == supplierId || ("").equals(supplierId))
		//	return;
	
		dataModel.setQueryName("getMaterialForPurchase");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("contractId", supplierId);
		userDefinedSearchCase.put("contractId", materialId);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();	 

		if(dataModel.getRecordCount() > 0){
			model.addAttribute("contract",dataModel.getYsViewData().get(0));		
			model.addAttribute("detail", dataModel.getYsViewData());
		}
		
	}	
	public  ArrayList<String>  removeDuplicate(List<String> reqSupplierList)  {
		Set<Object> set = new HashSet<Object>();
		ArrayList<String> newList = new  ArrayList<String>();
	    for(Iterator<String> iter = reqSupplierList.iterator(); iter.hasNext();){
	    	String element = iter.next();
	        if(set.add(element))
	        	newList.add(element);
	    } 
	    //reqSupplierList.clear();
	   // reqSupplierList.addAll(newList);
		return newList;
	 }
	
	public  boolean  isExsitCheck(String str,List<String> list)  {
	   for  (Iterator<String> iter  =  list.iterator(); iter.hasNext();)  {
	         Object element  =  iter.next();
	         if  (str.equals(element)){
	        	 return true;
	         }
	     } 
		return false;
	 }
	/**
	 * 1.生成采购合同(一条数据)
	 * 2.合同明细(N条数据)
	 */
	public void insert(String YSId,String inData) throws Exception {

		ts = new BaseTransaction();

		try {
			ts.begin();

			String[] reqSupplierArray = inData.split(",");
			List<String> reqSupplier = new ArrayList<String>();
			List<String> reqSupplierList = new ArrayList<String>();
			List<B_PurchasePlanDetailData> reqMaterail = new ArrayList<B_PurchasePlanDetailData>();
			for(int i=0;i<reqSupplierArray.length;i++){
				String[] tmp = reqSupplierArray[i].split(":");
				reqSupplier.add(i,tmp[0]);
				
				B_PurchasePlanDetailData plan = new B_PurchasePlanDetailData();
				plan.setRecordid(tmp[1]);
				
				reqMaterail.add(i,plan);
			}
			updatePurchasePlanDetail(reqMaterail);//更新采购方案,标识出本次创建合同的物料

			//过滤掉重复的供应商
			reqSupplierList = removeDuplicate(reqSupplier);
			String materialId = request.getParameter("materialId");
			
			//取得页面录入的合同交期
			String contractDelivery = request.getParameter("contractDelivery");
			
			//采购合同****************************************************
			
			//旧数据--开始↓:数据取得
			List<B_PurchaseOrderData> contractDBList = 
					getPurchaseOrderFromDB(YSId);
			
			
			//旧数据抵消处理
			for(B_PurchaseOrderData  contract:contractDBList){
				String contractId = contract.getContractid();
				String supplierId = contract.getSupplierid();
				
				//只处理页面选择的供应商
				if(!(isExsitCheck(supplierId,reqSupplierList))){
					continue;
				}
				
				List<B_PurchaseOrderDetailData> db = 
						getPurchaseOrderDetailFromDB(YSId,contractId);
				
				int dbsize = db.size();
				int deleteCnt = 0;
				for(int i=0;i<dbsize;i++){

					String materialdb = db.get(i).getMaterialid();
					String quantity = db.get(i).getQuantity();
					
					boolean arrivalFlag = checkContractArrival(contractId,materialdb);
					
					//没有收货记录
					if(arrivalFlag){
						deletePurchaseOrderDetail(db.get(i));
						updateMaterial(
								"合同抵消处理",
								materialdb,
								String.valueOf( (-1) * stringToFloat(quantity) ),
								"0");//更新虚拟库存
						
						deleteCnt++;//处理件数
					}
				}
				//该合同编号下的明细全部删除时,头信息也删除处理
				if( dbsize > 0 && dbsize == deleteCnt ){
					deletePurchaseOrder(YSId,contractId);
				}				
			}
			//旧数据处理--结束↑
			
			//新数据取得:从采购方案表中取得,集计单位:供应商
			ArrayList<HashMap<String, String>> reqPlanList = getSupplierList(YSId);
					
			//新数据insert处理
			for(int i=0;i<reqPlanList.size();i++){
				
				String supplierId = reqPlanList.get(i).get("supplierId");
				String shortName  = reqPlanList.get(i).get("supplierShortName");
				String total      = reqPlanList.get(i).get("total");
				String purchaseType = reqPlanList.get(i).get("purchaseType");
				
				if(supplierId == null || supplierId.equals("")){
					continue;
				}
				float totalf = stringToFloat(total); 
				if(totalf == 0){//采购数量为零的供应商不计入合同
					continue;
				}
				
				//只处理页面选择的供应商
				if(!isExsitCheck(supplierId,reqSupplierList))
					continue;
				
				B_PurchaseOrderData oldDb = getOldContractInfo(YSId,supplierId);
				String contractId = "";
				if(oldDb == null){//insert
					
					//取得供应商的合同流水号
					//父编号:年份+供应商简称
					//String type = getContractType(purchaseType);					
					//String typeParentId = BusinessService.getshortYearcode()+type;			
					//String typeSubId = getContractTypeCode(typeParentId);				
					String supplierParentId = BusinessService.getshortYearcode() + shortName;	
					String suplierSubId = getContractSupplierCode(supplierParentId);

					//5位流水号格式化	
					//采购合同编号:16D00081-WL00002
					contractId = BusinessService.getContractCode(YSId,suplierSubId,shortName);
				
					//新增采购合同*************
					B_PurchaseOrderData data = new B_PurchaseOrderData();
					
					data.setYsid(YSId);
					data.setMaterialid(materialId);
					data.setContractid(contractId);
					//data.setTypeparentid(typeParentId);
					//data.setTypeserial(typeSubId);
					data.setSupplierparentid(supplierParentId);
					data.setSupplierserial(suplierSubId);
					data.setSupplierid(supplierId);
					data.setTotal(total);
					data.setPurchasedate(CalendarUtil.fmtYmdDate());
					data.setDeliverydate(contractDelivery);
					data.setVersion(1);//默认为1
					String taxRate = BaseQuery.getContent(Constants.SYSTEMPROPERTYFILENAME, "taxRate");
					data.setTaxrate(taxRate);
					
					insertPurchaseOrder(data);//新增合同头表
					
				}else{//update
					
					contractId = oldDb.getContractid();
					oldDb.setTotal(total);
					updatePurchaseOrder(oldDb);
				}
				
				//新增合同明细*************
				List<HashMap<String, String>> dbList = getMaterialGroupList(YSId,supplierId);
				
				for(HashMap<String, String> dt:dbList){					
					
					String materialId1 = dt.get("materialId");
					B_PurchaseOrderDetailData oldDb1 = getOldContractDetailInfo(contractId,materialId1);
					
					//判断个别物料是否有采购数量
					String quantity = dt.get("purchaseQuantity");
					if(stringToFloat(quantity) == 0)
						continue;
					
					if(oldDb1 == null){//insert
						B_PurchaseOrderDetailData d = new B_PurchaseOrderDetailData();				
						d.setYsid(YSId);
						d.setContractid(contractId);
						d.setMaterialid(dt.get("materialId"));
						d.setDescription(dt.get("description"));
						d.setQuantity(quantity);
						d.setPrice(dt.get("price"));					
						d.setTotalprice(dt.get("totalPrice"));
						d.setUnitquantity(dt.get("unitQuantity"));
						d.setVersion(1);//默认为1
						
						insertPurchaseOrderDetail(d);

						updateMaterial("合同新建处理",materialId1,quantity,"0");//更新虚拟库存
						
					}else{//update
						//
						boolean arrivalFlag = checkContractArrival(contractId,materialId1);						
						//没有收货记录
						if(arrivalFlag){
							
							//String oldDb1Qty = oldDb1.getQuantity();
							//float quantity2 = stringToFloat(quantity) - stringToFloat(oldDb1Qty);									
							//if(quantity2 != 0)
							
							updateMaterial("合同更新处理",materialId1,quantity,"0");//更新虚拟库存
							
							oldDb1.setPrice(dt.get("price"));
							oldDb1.setQuantity(quantity);
							oldDb1.setTotalprice(dt.get("totalPrice"));
							oldDb1.setUnitquantity(dt.get("unitQuantity"));
							updatePurchaseOrderDetail(oldDb1);
						}
					}										
				}		
			}	
			
			//更新订单状态:待到料
			updateOrderStatusByYSId(YSId,Constants.ORDER_STS_2);
			
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
			System.out.println(e.getMessage());
		}
		
	}	
	//更新虚拟库存:生成合同时增加“待入库”
	@SuppressWarnings("unchecked")
	private void updateMaterial(
			String action,
			String materialId,
			String purchaseIn,
			String requirementOut) throws Exception{
	
		B_MaterialData data = new B_MaterialData();
		B_MaterialDao dao = new B_MaterialDao();
		
		String where = "materialId ='"+ materialId + "' AND deleteFlag='0' ";
		
		List<B_MaterialData> list = 
				(List<B_MaterialData>)dao.Find(where);
		
		if(list ==null || list.size() == 0){
			return ;
		}

		data = list.get(0);
		
		insertStorageHistory(data,action,purchaseIn);//保留更新前的数据
		
		//当前库存数量
		float iOnhand  = stringToFloat(data.getQuantityonhand());//实际库存
		float iWaitOut = stringToFloat(data.getWaitstockout());//待出库
		float iWaitIn  = stringToFloat(data.getWaitstockin());//待入库
		
		//iWaitOut = iWaitOut + stringToFloat(requirementOut);
		iWaitIn = iWaitIn + stringToFloat(purchaseIn);
		
		//虚拟库存 = 当前库存 + 待入库 - 待出库
		float availabeltopromise = iOnhand + iWaitIn - iWaitOut;		
		
		//更新DB
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"PurchasePlanInsert",userInfo);
		copyProperties(data,commData);
		
		data.setAvailabeltopromise(String.valueOf(availabeltopromise));//虚拟库存
		//data.setWaitstockout(String.valueOf(iWaitOut));//待出库
		data.setWaitstockin(String.valueOf(iWaitIn));//待入库
		
		dao.Store(data);
		
	}
	/**
	 * 更新采购方案
	 * @param list
	 * @throws Exception
	 */
	private void updatePurchasePlanDetail(
			List<B_PurchasePlanDetailData> list) throws Exception{

		for(B_PurchasePlanDetailData dt:list){
			
			dt = new B_PurchasePlanDetailDao(dt).beanData;
			//更新DB
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"createContract",userInfo);
			copyProperties(dt,commData);			
			dt.setContractflag(0);//本次创建合同
			
			new B_PurchasePlanDetailDao().Store(dt);
		}		
	}
	
	/*
	 * delete处理
	 */
	private void deletePurchaseOrder(B_PurchaseOrderData data) throws Exception{
		
		B_PurchaseOrderData db = new B_PurchaseOrderDao(data).beanData;
		commData = commFiledEdit(Constants.ACCESSTYPE_DEL,
				"purchaseOrderdelete",userInfo);			
		copyProperties(db,commData);

		new B_PurchaseOrderDao().Store(db);

	}

	/*
	 * delete处理
	 */
	private void deletePurchaseOrderDetail(B_PurchaseOrderDetailData data) throws Exception{
		B_PurchaseOrderDetailData db = new B_PurchaseOrderDetailDao(data).beanData;
		commData = commFiledEdit(Constants.ACCESSTYPE_DEL,
				"purchaseOrderDetaildelete",userInfo);			
		copyProperties(db,commData);
		
		new B_PurchaseOrderDetailDao().Store(db);	

	}
	

	@SuppressWarnings("unchecked")
	private void deletePurchaseOrder(String YSId,String contractId) throws Exception{
		
		String where = " YSId = '"+YSId +"'"+
				" AND contractId = '"+contractId +"'" +
				" AND deleteFlag = '0' ";
		List<B_PurchaseOrderData> dbList = new B_PurchaseOrderDao().Find(where);
		
		if(dbList != null & dbList.size() > 0){
			B_PurchaseOrderData db = dbList.get(0);
			commData = commFiledEdit(Constants.ACCESSTYPE_DEL,
					"purchaseOrderdelete",userInfo);			
			copyProperties(db,commData);

			new B_PurchaseOrderDao().Store(db);
		}

	}
	

	@SuppressWarnings("unchecked")
	private void deletePurchaseOrderDetail(String contractId) throws Exception{
		
		String where = " contractId = '"+contractId +"'" +
				" AND deleteFlag = '0' ";
		List<B_PurchaseOrderDetailData> dbList = new B_PurchaseOrderDetailDao().Find(where);
		
		for(int i=0; i < dbList.size();i++){
			B_PurchaseOrderDetailData db = dbList.get(i);
			commData = commFiledEdit(Constants.ACCESSTYPE_DEL,
					"purchaseOrderDetaildelete",userInfo);			
			copyProperties(db,commData);

			new B_PurchaseOrderDetailDao().Store(db);
						
			//恢复库存"待入数量",合同只处理待入数量,待出在采购方案里面
			String newQty = String.valueOf(-1 * stringToFloat(db.getQuantity()));
			updateMaterial("合同删除处理",db.getMaterialid(),newQty,"0");
		}

	}
	
	@SuppressWarnings("unchecked")
	public B_PurchaseOrderData getOldContractInfo(
			String YSId ,String supplierId) throws Exception {
		
		List<B_PurchaseOrderData> dbList = null;
		
		String where = " YSId = '"+YSId +"'"+
				" AND supplierId = '"+supplierId +"'" ;
				//+" AND deleteFlag = '0' ";
						
		dbList = (List<B_PurchaseOrderData>)new B_PurchaseOrderDao().Find(where);
		
		if(dbList != null & dbList.size() > 0)
			return dbList.get(0);		
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public B_PurchaseOrderDetailData getOldContractDetailInfo(
			String contractId ,String materialId) throws Exception {
		
		List<B_PurchaseOrderDetailData> dbList = null;
		
		String where = " contractId = '"+contractId +"'"+
				" AND materialId = '"+materialId +"'";
				//+ " AND deleteFlag = '0' ";
						
		dbList = (List<B_PurchaseOrderDetailData>)new B_PurchaseOrderDetailDao().Find(where);
		
		if(dbList != null & dbList.size() > 0)
			return dbList.get(0);		
		
		return null;
	}
	
	/*
	 * 更新
	 */
	private int updatePurchaseOrderDetail(B_PurchaseOrderDetailData dbPlan) throws Exception{
		
		//update处理	
		int version = dbPlan.getVersion()+1;
		
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"purchasePlanUpdate",userInfo);			
		copyProperties(dbPlan,commData);
		dbPlan.setVersion(version);
		new B_PurchaseOrderDetailDao().Store(dbPlan);
		
		return version;
	}
	
	public ArrayList<HashMap<String, String>> getMaterialGroupList(
			String YSId,String supplierId) throws Exception {		
		
		dataModel.setQueryName("getContractMateriaBySupplier");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("YSId", YSId);
		userDefinedSearchCase.put("supplierId", supplierId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		return baseQuery.getYsFullData();
		
	}

	/*
	 * 更新处理
	 */
	private void updatePurchaseOrder(
			B_PurchaseOrderData data) throws Exception{
		
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"PurchaseOrderUpdate",userInfo);
		copyProperties(data,commData);
		
		orderDao.Store(data);	
	}
	
		
	
	/**
	 * 1.订单基本信息更新处理(1条数据)
	 * 2.订单详情 新增/更新/删除 处理(N条数据)
	 */
	public void update() throws Exception  {

		ts = new BaseTransaction();
				
		try {
			
			ts.begin();	
			
			//合同基本信息
			B_PurchaseOrderData orderData = reqModel.getContract();
			//合同详情
			List<B_PurchaseOrderDetailData> newDetailList = reqModel.getDetailList();
			
			String ysid = orderData.getYsid();
			String contractid = orderData.getContractid();
			
			//删除明细
			deletePurchaseOrderDetail(orderData.getContractid());
			
			if(newDetailList == null){
				//删除合同头信息		
				deletePurchaseOrder(orderData);		
			}else{				
						
				//更新明细
				for(B_PurchaseOrderDetailData data:newDetailList ){
					
					data.setContractid(contractid);
					//恢复库存"待入数量",合同只处理待入数量,待出在采购方案里面			
					String newQty = data.getQuantity();
					float fnewqty = 0;
					if(notEmpty(newQty))
						fnewqty = stringToFloat(newQty.trim());
					
					if(fnewqty == 0){
						data.setQuantity(newQty);
						deletePurchaseOrderDetail(data);
					}else{
						//取得原合同的数量，单价
						B_PurchaseOrderDetailData oldDetail = 
								getOldContractDetailInfo(data.getContractid(),data.getMaterialid());
						
						float oldQty   = stringToFloat(oldDetail.getQuantity());
						float oldPrice = stringToFloat(oldDetail.getPrice());
						float newPrice = stringToFloat(data.getPrice());

						boolean planUpdateFlag = false;
						//*** 合同数量
						if(fnewqty != oldQty){//合同数量有变化
							planUpdateFlag = true;
							updateMaterial("合同更新处理",data.getMaterialid(),newQty,"0");	
						}
						

						//*** 合同单价
						if(newPrice != oldPrice){//单价有变化
							planUpdateFlag = true;
							updateStockinByMaterialId(data.getMaterialid(),data);
						}
						
						//更新采购方案：此更新是单向的，方案有变化时，不会自动更新合同
						if(planUpdateFlag){
							updatePlanDetailByMaterialId(ysid,data);
						}

						updateOrderDetail(data);
					}
					
					
				}				

				//计算退税
				float total = stringToFloat(orderData.getTotal());//合同总金额
				float taxRate = stringToFloat(orderData.getTaxrate());//税率
				float taxExcluded = total / (taxRate / 100 + 1 );//价=合同 / 税率
				float taxes = total - taxExcluded;//税= 合同 - 价
				
				orderData.setTaxes(floatToString(taxes));
				orderData.setTaxrate(floatToString(taxRate));
				orderData.setTaxexcluded(floatToString(taxExcluded));
				
				//更新合同头信息
				updateOrder(orderData);
			}
			
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
			System.out.println(e.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	private void updateStockinByMaterialId(
			String materialId,
			B_PurchaseOrderDetailData contract) throws Exception{
	
		//取得入库信息	
		dataModel.setQueryFileName("/business/material/inventoryquerydefine");
		dataModel.setQueryName("getStockinListById");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("contractId", contract.getContractid());	
		userDefinedSearchCase.put("materialId", materialId);		
	
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, 0);			

		//更新入库信息的单价
		for(int i=0;i<dataModel.getRecordCount();i++){
			HashMap<String, String> record = dataModel.getYsViewData().get(i);
			
			String where = " receiptId ='"+ record.get("receiptId") + "' " + 
					" AND materialId ='"+ record.get("materialId") + "' " + 
					" AND deleteFlag='0' ";
			
			List<B_PurchaseStockInDetailData> list = 
					(List<B_PurchaseStockInDetailData>) new B_PurchaseStockInDetailDao().Find(where);
			
			if(list ==null || list.size() == 0){
				return ;
			}
			B_PurchaseStockInDetailData data = list.get(0);
			
			//更新DB
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"PurchasePlanUpdate",userInfo);
			copyProperties(data,commData);
			
			data.setPrice(contract.getPrice());
			
			new B_PurchaseStockInDetailDao().Store(data);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private void updatePlanDetailByMaterialId(
			String ysid,
			B_PurchaseOrderDetailData contract) throws Exception{
	
		B_PurchasePlanDetailData data = new B_PurchasePlanDetailData();
		B_PurchasePlanDetailDao dao = new B_PurchasePlanDetailDao();
		
		String where = " ysid ='"+ ysid + "' " + 
				" AND materialId ='"+ contract.getMaterialid() + "' " + 
				" AND deleteFlag='0' ";
		
		List<B_PurchasePlanDetailData> list = 
				(List<B_PurchasePlanDetailData>)dao.Find(where);
		
		if(list ==null || list.size() == 0){
			return ;
		}
		data = list.get(0);
				
		//更新DB
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"PurchasePlanUpdate",userInfo);
		copyProperties(data,commData);
		
		data.setPrice(contract.getPrice());
		data.setPurchasequantity(contract.getQuantity());
		
		dao.Store(data);
		
	}
	
	/*
	 * 订单基本信息更新处理
	 */
	private void updateOrder(B_PurchaseOrderData order) 
			throws Exception{

		
		orderDao = new B_PurchaseOrderDao(order);
		
		//处理共通信息
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"PurchaseOrderUpdate",userInfo);
		copyProperties(orderDao.beanData,commData);
		
		//获取页面数据
		copyProperties(orderDao.beanData,order);
		orderDao.beanData.setMemo(replaceTextArea(order.getMemo()));
		
		orderDao.Store(orderDao.beanData);
	
	}
	
	public void updateWorkshopRentunInit() throws Exception{

		String contractId = request.getParameter("contractId");
		String workshopReturnId = request.getParameter("workshopReturnId");
		
		getContractDetailList(contractId);		

		getWorkshopReturnList(workshopReturnId);
	}

	/*
	 * 订单详情更新处理
	 */
	private void updateOrderDetail(
			B_PurchaseOrderDetailData data) throws Exception{
		
		B_PurchaseOrderDetailData rtnDt= new B_PurchaseOrderDetailData();
		try{

			B_PurchaseOrderDetailData db = new B_PurchaseOrderDetailDao(data).beanData;

			//处理共通信息
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"PurchaseOrderDetailUpdate",userInfo);
			copyProperties(db,commData);		
			//获取页面数据
			db.setQuantity(data.getQuantity());
			db.setPrice(data.getPrice());
			db.setTotalprice(data.getTotalprice());
			db.setDescription(data.getDescription());
			
			detailDao.Store(db);
			
		}catch(Exception e){
			
		}
		
	}
	
	
	/**
	 * 合同删除处理
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	private void deleteContract(String where) throws Exception {
		
		B_PurchaseOrderDao dao = new B_PurchaseOrderDao();
		
		//确认数据是否存在		
		List<B_PurchaseOrderData> list = dao.Find(where);
		
		for(B_PurchaseOrderData data:list){

			commData = commFiledEdit(Constants.ACCESSTYPE_DEL,
					"purchasePlanUpdate",userInfo);			
			copyProperties(data,commData);
			dao.Store(data);
		}		
	}
	
	
	
	private String getContractTypeCode(String parentId) throws Exception {

		dataModel.setQueryName("getContractTypeCode");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("typeParentId", parentId);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, 0);		
		String code =dataModel.getYsViewData().get(0).get("MaxSubId");		
		return code;		
	}	

	private String getContractSupplierCode(String parentId) throws Exception {

		dataModel.setQueryName("getContractSupplierCode");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("supplierParentId", parentId);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, 0);			
		String code =dataModel.getYsViewData().get(0).get("MaxSubId");		
		return code;		
	}	

	//生成采购合同--自制品
	public void creatPurchaseOrderZZ() throws Exception {
		
		String YSId = request.getParameter("YSId");
		//String supplierId = request.getParameter("supplierId");
		
		insert(YSId,"");
		
		//ArrayList<ArrayList<String>> supplierList = null; 
		
		//生成采购合同		
		//if(null == supplierId){

		//	supplierId = insert(YSId);
			
		//}else{
		//	getSupplierList(YSId);//设置供应商下拉框
		//}
			
		//取得本次采购合同在该供应商购买的材料list
		//String suppShortName = getRequirementBySupplierId(YSId,supplierId);
		
		//getContractCode(YSId,suppShortName);
	}
	
	//生成采购合同--订单采购
	public void creatPurchaseOrder(String inData) throws Exception {
		
		String YSId = request.getParameter("YSId");
		String peiYsid = request.getParameter("peiYsid");
		if(notEmpty(peiYsid))
			YSId = peiYsid;
		
		insert(YSId,inData);
		
	}
	//生成采购合同--自制品
	public HashMap<String, Object> getContractList() throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		
		String YSId = request.getParameter("YSId");
	
		dataModel.setQueryName("getContractList");
		
		baseQuery = new BaseQuery(request, dataModel);
	
		userDefinedSearchCase.put("YSId", YSId);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		modelMap.put("recordsTotal", dataModel.getRecordCount()); 
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
	}

	
	public HashMap<String, Object> getContractByYSId() throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		
		String YSId = request.getParameter("YSId");
	
		dataModel.setQueryName("getContractByYSId");
		
		baseQuery = new BaseQuery(request, dataModel);
	
		userDefinedSearchCase.put("YSId", YSId);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		modelMap.put("recordsTotal", dataModel.getRecordCount()); 
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
	}
	//
	public HashMap<String, Object> getContractDetail() throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		
		String contractId = request.getParameter("contractId");
	
		dataModel.setQueryName("getContractDetail");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("contractId", contractId);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		modelMap.put("recordsTotal", dataModel.getRecordCount()); 
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
	}
	//
	public HashMap<String, Object> getContractId() throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		
		String contractId = request.getParameter("contractId").trim().toUpperCase();
	
		dataModel.setQueryName("getContractId");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("contractId", contractId);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		modelMap.put("recordsTotal", dataModel.getRecordCount()); 
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
	}
	
	public void updateAndView() throws Exception {
		
		update();

		String contractId = reqModel.getContract().getContractid();
		
		getContractDetailList(contractId);
		
	}
	
	public void deletePurchaseOrder() throws Exception {
		
		ts = new BaseTransaction();

		try {
			ts.begin();
			//合同基本信息
			B_PurchaseOrderData orderData = reqModel.getContract();
			deletePurchaseOrder(orderData);
			
			//合同详情 删除 处理			
			List<B_PurchaseOrderDetailData> newDetailList = reqModel.getDetailList();
						
			//页面数据的recordId与DB匹配			
			for(B_PurchaseOrderDetailData data:newDetailList ){
				
				//删除处理
				deletePurchaseOrderDetail(data);
				
				//恢复库存"待入数量"
				updateMaterial(
						"合同删除处理",
						data.getMaterialid(),
						String.valueOf(-1 * stringToFloat(data.getQuantity())),
						"0");//合同只处理待入数量,待出在采购方案里面
			}
			
			ts.commit();
			
		}catch(Exception e){
			ts.rollback();
			e.printStackTrace();
		}
	}
	

	public void createContractInit() throws Exception {
		
		String supplierId = request.getParameter("supplierId");
		String materialId = request.getParameter("materialId");
		String goBackFlag = request.getParameter("goBackFlag");
		String quantity = request.getParameter("quantity");
		String YSId = request.getParameter("YSId");
		
		getMaterialDetailList(supplierId,materialId);
		
		model.addAttribute("goBackFlag",goBackFlag);
		model.addAttribute("quantity",quantity);
		model.addAttribute("YSId",YSId);
		model.addAttribute("rebateRateList",
				util.getListOption(DicUtil.TAXREBATERATE,""));//退税率
	}
	

	public void createRoutineContract() throws Exception {
		
		//合同数据做成
		String contractId = insertRoutine();
		//跳转到查看页面
		getContractDetailList(contractId);
				
	}
	
	
	private B_PurchaseOrderData geRoutinePurchaseContractId(
			B_PurchaseOrderData dt,
			String YSId,String shortName,String purchaseType) throws Exception{
				
		//3位流水号格式化	
		//采购合同编号:16YS081-WL002
		//String type = getContractType(purchaseType);		
		//String typeParentId = BusinessService.getshortYearcode()+type;				
		//String typeSubId = getContractTypeCode(typeParentId);				
		String supplierParentId = BusinessService.getshortYearcode() + shortName;
		String suplierSubId = getContractSupplierCode(supplierParentId);

		//3位流水号格式化	
		//采购合同编号:16D081-WL002
		String contractId = BusinessService.getContractCode(YSId, suplierSubId,shortName);


		dt.setYsid(YSId);
		dt.setContractid(contractId);
		//dt.setTypeparentid(typeParentId);
		//dt.setTypeserial(typeSubId);
		dt.setSupplierparentid(supplierParentId);
		dt.setSupplierserial(suplierSubId);
		
		return dt;

	}
	
	private String insertRoutine(){
		
		ts = new BaseTransaction();
		String contractId = "";
		try {
			ts.begin();			
			
			B_PurchaseOrderData contract = reqModel.getContract();
			//创建合同编号
			String YSId = contract.getYsid();
			String shortName = reqModel.getShortName();
			String purchaseType = Constants.CONTRACT_TYPE_D;//统一设置为订购件
			
			if(isNullOrEmpty(YSId))
				YSId = BusinessService.getYSCommCode()+"****";
			contract = geRoutinePurchaseContractId(contract,YSId,shortName,purchaseType);
			contractId = contract.getContractid();
			
			//新增常规采购合同
			//计算退税
			float total = stringToFloat(contract.getTotal());//合同总金额
			float taxRate = stringToFloat(contract.getTaxrate());//税率
			float taxExcluded = total / (taxRate / 100 + 1 );//价=合同 / 税率
			float taxes = total - taxExcluded;//税= 合同 - 价
			
			contract.setTaxes(floatToString(taxes));
			contract.setTaxrate(floatToString(taxRate));
			contract.setTaxexcluded(floatToString(taxExcluded));
			
			insertPurchaseOrder(contract);
			
			//合同明细:因为是从物料信息过来的,所以只有一条数据
			List<B_PurchaseOrderDetailData> reqDetail = reqModel.getDetailList();
			for(B_PurchaseOrderDetailData d:reqDetail){


				String purchase = d.getQuantity();//采购量
				String materilid = d.getMaterialid();
				
				if(isNullOrEmpty(materilid) || isNullOrEmpty(purchase))
					continue;//过滤无效数据
				
				//合同明细
				d.setYsid(YSId);
				d.setContractid(contractId);				
				insertPurchaseOrderDetail(d);	
				
				//更新虚拟库存
				String requirement = "0";//需求量:真实的需求量在订单采购时已经计算过
				updateMaterial("常规采购合同新建处理",materilid,purchase,requirement);
			}		
			

			ts.commit();
		}
		catch(Exception e) {
			try {
				ts.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			System.out.println(e.getMessage());
		}
		return contractId;
	}
	
	private void insertPurchaseOrder(B_PurchaseOrderData data) throws Exception{
		
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"PurchaseOrderInsert",userInfo);
		copyProperties(data,commData);		
		guid = BaseDAO.getGuId();
		data.setRecordid(guid);
		
		orderDao.Create(data);	
	}
	
	
	private void insertPurchaseOrderDetail(B_PurchaseOrderDetailData data) throws Exception{
		
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"RoutinePurchaseOrderDetailInsert",userInfo);
		copyProperties(data,commData);		
		guid = BaseDAO.getGuId();
		data.setRecordid(guid);
		
		detailDao.Create(data);	
	}
	

	public void createAcssoryContractInit() throws Exception{

		String YSId = request.getParameter("YSId");
		String orderType = Constants.ORDERTYPE_2;//配件
		this.dataModel.setQueryName("createAcssoryContractInit");

		this.userDefinedSearchCase.put("YSId", YSId);
		this.userDefinedSearchCase.put("orderType", orderType);
		this.baseQuery = new BaseQuery(this.request, this.dataModel);	
		this.baseQuery.setUserDefinedSearchCase(this.userDefinedSearchCase);
		this.baseQuery.getYsFullData();
	
		this.model.addAttribute("order", this.dataModel.getYsViewData().get(0));
		this.model.addAttribute("detail", this.dataModel.getYsViewData());
		this.model.addAttribute("YSId", YSId);
	}
	
	public void createAcssoryContractAndView() throws Exception{

		//跳转到查看页面
		String contractId = insertPurchaseOrderAndDetail();
		getContractDetailList(contractId);
	}
	
	public String insertPurchaseOrderAndDetail() throws Exception{
		ts = new BaseTransaction();

		String contractId = "";
		try {
			ts.begin();
			
			
			//合同明细:因为是从物料信息过来的,所以只有一条数据
			List<B_PurchaseOrderDetailData> reqDetail = reqModel.getDetailList();
			for(B_PurchaseOrderDetailData d:reqDetail){

				B_PurchaseOrderData contract = reqModel.getContract();
				//创建合同编号
				String YSId = contract.getYsid();
				String shortName = reqModel.getShortName();
				contract = geRoutinePurchaseContractId(contract,YSId,shortName,"D");
				contractId = contract.getContractid();
				
				//新增常规采购合同
				insertPurchaseOrder(contract);
				
				//合同明细
				d.setYsid(YSId);
				d.setContractid(contractId);				
				insertPurchaseOrderDetail(d);	
				
				//更新虚拟库存
				String purchase = d.getQuantity();//采购量
				String materilid = d.getMaterialid();
				String requirement = "0";//需求量:真实的需求量在订单采购时已经计算过
				updateMaterial("单独采购合同新建处理",materilid,purchase,requirement);
			}	

			ts.commit();
			
			
		}catch(Exception e) {
			ts.rollback();
			System.out.println(e.getMessage());
			reqModel.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return contractId;
	}

	@SuppressWarnings("unchecked")
	public List<B_PurchaseOrderData> getPurchaseOrderFromDB(
			String YSId) throws Exception {
		
		String where = " YSId = '" + YSId +"' AND deleteflag = '0'";
		return (List<B_PurchaseOrderData>) new B_PurchaseOrderDao().Find(where);
				
	}
	
	@SuppressWarnings("unchecked")
	public List<B_PurchaseOrderDetailData> getPurchaseOrderDetailFromDB(
			String YSId,String contractId) throws Exception {

		String where = " YSId = '" + YSId  +"' AND contractId = '" + contractId  +"' AND deleteflag = '0' ";
		return (List<B_PurchaseOrderDetailData>) new B_PurchaseOrderDetailDao().Find(where);
				
	}
	
	public void getContractListByMaterialId() throws Exception {
		String materialId = request.getParameter("materialId");
		dataModel.setQueryFileName("/business/order/purchasequerydefine");
		dataModel.setQueryName("getRequriementBySupplier");
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("materialId", materialId);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		if(dataModel.getRecordCount() > 0 ){
			model.addAttribute("material", dataModel.getYsViewData().get(0));
			model.addAttribute("contractList", dataModel.getYsViewData());
		}	
	}
	


	public HashMap<String, Object> getContractDeduct() throws Exception {
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		
		String orderExpanseQty = getOrderExpanseByContractId();//订单过程扣款
		
		modelMap =  getContractScrapDeduct();//物料报废扣款
		modelMap.put("orderExpanseQty", orderExpanseQty);
		
		return modelMap;
	}
	

	public HashMap<String, Object> checkContractDelete() throws Exception {
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		
		String contractId = request.getParameter("contractId");
		dataModel.setQueryFileName("/business/order/purchasequerydefine");
		dataModel.setQueryName("arrivalAndReceiveInspection");
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("contractId", contractId);	
		//userDefinedSearchCase.put("checkResult", "040");//退货		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();
		
		if(dataModel.getRecordCount() > 0 ){
			modelMap.put("data",dataModel.getYsViewData());			
		}else{
			modelMap.put("deleteFlag", "OK");
		}
		return modelMap;
	}
	
	private String getOrderExpanseByContractId() throws Exception {
		
		String orderExpanseQty = "0";
		String contractId = request.getParameter("contractId");
		dataModel.setQueryFileName("/business/order/purchasequerydefine");
		dataModel.setQueryName("orderExpanseGroupByContractId");
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("contractId", contractId);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();
		
		if(dataModel.getRecordCount() > 0 ){

			orderExpanseQty =  dataModel.getYsViewData().get(0).get("orderExpanseQty");			
		}
		return orderExpanseQty;
	}

	private void getContractDelayDeduct() throws Exception {
		
		String contractId = request.getParameter("contractId");
		dataModel.setQueryFileName("/business/order/purchasequerydefine");
		dataModel.setQueryName("contractDelayDeduct");
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("contractId", contractId);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();
		
		if(dataModel.getRecordCount() > 0 ){

			String contractQty =  dataModel.getYsViewData().get(0).get("contractQty");
			//String stockinQty =  dataModel.getYsViewData().get(0).get("stockinQty");
			String deliveryDate =  dataModel.getYsViewData().get(0).get("deliveryDate");
			String checkInDate =  dataModel.getYsViewData().get(0).get("checkInDate");
			
			float fcontractQty = stringToFloat(contractQty);
			//float fstockinQty = stringToFloat(stockinQty);
			//延期判断
			int days = Integer.parseInt(CalendarUtil.getDayBetween(deliveryDate,checkInDate));
			if(days > 30){
				//合同入库超期
				double delay = (fcontractQty * 0.01);//合同总金额的百分之一
				model.addAttribute("delay",String.valueOf(delay));
				model.addAttribute("contractId",contractId);
			}else{
				model.addAttribute("delay","0");
			}
		}
	}
	
	
	private HashMap<String, Object> getContractScrapDeduct() throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		String contractId = request.getParameter("contractId");
		dataModel.setQueryFileName("/business/order/purchasequerydefine");
		dataModel.setQueryName("contractListDeductById");
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("materialId", contractId);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		modelMap.put("recordCount", dataModel.getRecordCount());
		modelMap.put("data", dataModel.getYsViewData());	
		
		return modelMap;
	}
	
	@SuppressWarnings("unchecked")
	public boolean checkContractArrival(
			String contractId,String materialId) throws Exception{
		boolean rtnFlag = false;
		
		String where = " contractId='" + contractId +  
				"' AND materialId='" + materialId + 
				"' AND deleteFlag='0' ";
		
		List<B_ArrivalData> list = new B_ArrivalDao().Find(where);
		
		if(list == null || list.size() <= 0 )
			rtnFlag = true;
		
		return rtnFlag;
	}
	
	public void editPurchaseOrder() throws Exception{

		String contractId = reqModel.getContract().getContractid();
		
		getContractDetailList(contractId);	

		model.addAttribute("rebateRateList",
				util.getListOption(DicUtil.TAXREBATERATE,""));//退税率
	}
	
	
	@SuppressWarnings("unchecked")
	public void updateContractDeduct() throws Exception{

		String contractId = request.getParameter("contractId");
		String deduct = request.getParameter("deduct");
		
		String where = " contractId='" + contractId +"' AND deleteFlag='0' ";
		
		List<B_PurchaseOrderData> list = new B_PurchaseOrderDao().Find(where);
		
		if(list != null && list.size() > 0) {
			
			B_PurchaseOrderData db = list.get(0);
			db.setDeduct(deduct);
			db.setDeductflag("1");//有协商扣款
			
			//更新DB
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"协商扣款Update",userInfo);
			copyProperties(db,commData);
			
			new B_PurchaseOrderDao().Store(db);
			
		}

	}
	
	public void purchaseOrderMainInit() throws Exception{

		ArrayList<HashMap<String, String>> list = getPurchaseUserById();
		ArrayList<HashMap<String, String>> year = getYearById();

		model.addAttribute("purchaser",list);
		model.addAttribute("yearList",year);
		model.addAttribute("year",util.getListOption(DicUtil.BUSINESSYEAR, ""));
	}
	
	/**
	 * 合同关注
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> setContractFollow() throws Exception {
	
		HashMap<String, Object> HashMap = new HashMap<String, Object>();
		String followStatus = "";

		String YSId = request.getParameter("YSId");
		String contractId = request.getParameter("contractId");
		String where = " YSId='"+ YSId +
				"' AND contractId='"+ contractId +
				"' AND followType='"+Constants.FOLLOWTYPE_2+"' ";
		List<B_FollowData> list = new B_FollowDao().Find(where);
		
		if(list.size() > 0){
			
			B_FollowData data = list.get(0);		
			String status = data.getStatus();
			
			if((Constants.FOLLOWSTATUS_0).equals(status)){
				//取消关注
				data.setStatus(Constants.FOLLOWSTATUS_1);//取消关注
				commData = commFiledEdit(Constants.ACCESSTYPE_DEL,
						"OrderFollowDelete",userInfo);
			}else{
				//再次关注
				data.setStatus(Constants.FOLLOWSTATUS_0);//关注
				commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
						"OrderFollowInsert",userInfo);
			}
			copyProperties(data,commData);
			
			
			new B_FollowDao().Store(data);
			
			followStatus = data.getStatus();
		}else{
			//首次关注
			B_FollowData data = new B_FollowData();		
			
			commData = commFiledEdit(Constants.ACCESSTYPE_INS,
					"OrderFollowInsert",userInfo);
			copyProperties(data,commData);
			
			guid = BaseDAO.getGuId();
			data.setRecordid(guid);
			data.setYsid(YSId);
			data.setContractid(contractId);
			data.setFollowtype(Constants.FOLLOWTYPE_2);//合同
			data.setStatus(Constants.FOLLOWSTATUS_0);//关注
			
			new B_FollowDao().Create(data);

			followStatus = data.getStatus();
		}

		HashMap.put("status", followStatus);
		return HashMap;
	}
	

	public HashMap<String, Object> getUnStockinContractListById() throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		String supplierId = request.getParameter("supplierId");
		
		dataModel.setQueryFileName("/business/order/purchasequerydefine");
		dataModel.setQueryName("unStockinContractListById");
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("supplierId", supplierId);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		modelMap.put("recordCount", dataModel.getRecordCount());
		modelMap.put("data", dataModel.getYsViewData());	
		
		return modelMap;
	}
	
	/**
	 * 未到合同：收起
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> getUnStockinListForRetract() throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		String supplierId = request.getParameter("supplierId");
		
		dataModel.setQueryFileName("/business/order/purchasequerydefine");
		dataModel.setQueryName("unStockinContractListForRetract");
		baseQuery = new BaseQuery(request, dataModel);		
		//userDefinedSearchCase.put("supplierId", supplierId);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		String sql = baseQuery.getSql();
		sql = sql.replace("#", supplierId);
		System.out.println("未收货供应商别查询："+sql);
		baseQuery.getYsFullData(sql,supplierId);

		modelMap.put("recordCount", dataModel.getRecordCount());
		modelMap.put("data", dataModel.getYsViewData());	
		
		return modelMap;
	}
}
