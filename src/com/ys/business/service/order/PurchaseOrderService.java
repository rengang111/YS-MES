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
import com.ys.business.action.model.order.PurchaseOrderModel;
import com.ys.business.db.dao.B_MaterialDao;
import com.ys.business.db.dao.B_PurchaseOrderDao;
import com.ys.business.db.dao.B_PurchaseOrderDetailDao;
import com.ys.business.db.dao.B_PurchasePlanDetailDao;
import com.ys.business.db.data.B_MaterialData;
import com.ys.business.db.data.B_PurchaseOrderData;
import com.ys.business.db.data.B_PurchaseOrderDetailData;
import com.ys.business.db.data.B_PurchasePlanDetailData;
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
	
		dataModel.setQueryName("getContractList");
		
		baseQuery = new BaseQuery(request, dataModel);		
		
		String[] keyArr = getSearchKey(formId,data,session);
		String key1 = keyArr[0];
		String key2 = keyArr[1];
		
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		String sql = getSortKeyFormWeb(data,baseQuery);	
		baseQuery.getYsQueryData(sql,iStart, iEnd);	 
		
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

			reqSupplierList = removeDuplicate(reqSupplier);
			String materialId = request.getParameter("materialId");
			String contractDelivery = request.getParameter("contractDelivery");

			//更新采购方案,标识出本次创建合同的物料
			//List<B_PurchasePlanDetailData> planDetail = getPurchasePlanDetail(YSId);
			
			//采购合同****************************************************
			
			//旧数据--开始↓:数据取得
			List<B_PurchaseOrderData> contractDBList = 
					getPurchaseOrderFromDB(YSId);
			List<B_PurchaseOrderDetailData> contractDetailDBList = 
					getPurchaseOrderDetailFromDB(YSId);
			
			//旧数据抵消处理:合同
			List<String> reqContractList = new ArrayList<String>();
			for(B_PurchaseOrderData db:contractDBList){

				//int versionOrder = db.getVersion();
				//db.setVersion(versionOrder ++);
				//只处理页面选择的供应商
				if(isExsitCheck(db.getSupplierid(),reqSupplierList)){
					reqContractList.add(db.getContractid());
					deletePurchaseOrder(db);
					
				}
				//db.setTotal(String.valueOf(stringToFloat(db.getTotal()) * (-1)));
				//db.setVersion(versionOrder ++);
				//insertPurchaseOrder2(db);				
			}
			
			//旧数据抵消处理:合同明细
			for(B_PurchaseOrderDetailData db:contractDetailDBList){
				
				//合同明细
				//int versionOrder = db.getVersion();
				//db.setVersion(versionOrder ++);
				//只处理页面选择的供应商
				if(isExsitCheck(db.getContractid(),reqContractList)){

					deletePurchaseOrderDetail(db);
					
					updateMaterial(
							db.getMaterialid(),
							String.valueOf( (-1) * stringToFloat(db.getQuantity()) ),
							"0");//更新虚拟库存
				}
				
				//db.setQuantity(String.valueOf(-1 * stringToFloat(db.getQuantity())));
				//db.setTotalprice(String.valueOf(-1 * stringToFloat(db.getTotalprice())));
				//db.setVersion(versionOrder ++);
				//insertPurchaseOrderDetail2(db);				
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
				
				//
				B_PurchaseOrderData oldDb = getOldContractInfo(YSId,supplierId);
				String contractId = "";
				if(oldDb == null || ("").equals(oldDb)){//insert
					
					//取得供应商的合同流水号
					//父编号:年份+供应商简称
					String type = getContractType(purchaseType);
					
					String typeParentId = BusinessService.getshortYearcode()+type;				
					String supplierParentId = BusinessService.getshortYearcode() + shortName;				
					String typeSubId = getContractTypeCode(typeParentId);
					String suplierSubId = getContractSupplierCode(supplierParentId);

					//3位流水号格式化	
					//采购合同编号:16D081-WL002
					contractId = BusinessService.getContractCode(type,typeSubId, suplierSubId,shortName);
				
					//新增采购合同*************
					B_PurchaseOrderData data = new B_PurchaseOrderData();
					
					data.setYsid(YSId);
					data.setMaterialid(materialId);
					data.setContractid(contractId);
					data.setTypeparentid(typeParentId);
					data.setTypeserial(typeSubId);
					data.setSupplierparentid(supplierParentId);
					data.setSupplierserial(suplierSubId);
					data.setSupplierid(supplierId);
					data.setTotal(total);
					data.setPurchasedate(CalendarUtil.fmtYmdDate());
					data.setDeliverydate(contractDelivery);
					data.setVersion(1);//默认为1
					
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
					
					if(oldDb1 == null || ("").equals(oldDb1)){//insert
						B_PurchaseOrderDetailData d = new B_PurchaseOrderDetailData();				
						d.setYsid(YSId);
						d.setContractid(contractId);
						d.setMaterialid(dt.get("materialId"));
						d.setQuantity(quantity);
						d.setPrice(dt.get("price"));					
						d.setTotalprice(dt.get("totalPrice"));
						d.setUnitquantity(dt.get("unitQuantity"));
						d.setVersion(1);//默认为1
						
						insertPurchaseOrderDetail(d);
						
					}else{//update
						//因为有合同的累计入库数量,所以做更新处理
						oldDb1.setPrice(dt.get("price"));
						oldDb1.setQuantity(quantity);
						oldDb1.setTotalprice(dt.get("totalPrice"));
						oldDb1.setUnitquantity(dt.get("unitQuantity"));
						updatePurchaseOrderDetail(oldDb1);
					}					
					

					updateMaterial(materialId1,quantity,"0");//更新虚拟库存
					
					continue;					
				}		
			}	
			
			//更新订单状态:待到料
			updateOrderStatusByYSId(YSId,Constants.ORDER_STS_2);
			
			/*
			
			//采购合同****************************************************
			
			//供应商集计
			//以采购方案里的供应商为单位集计
			ArrayList<HashMap<String, String>> supplierList = getSupplierList(YSId);
					
			if(supplierList == null || supplierList.size() <= 0)
				return;			
			
			for(int i=0;i<supplierList.size();i++){
				
				String supplierId = supplierList.get(i).get("supplierId");
				String shortName  = supplierList.get(i).get("supplierShortName");
				String total      = supplierList.get(i).get("total");
				String purchaseType = supplierList.get(i).get("purchaseType");
				
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
				
				//取得供应商的合同流水号
				//父编号:年份+供应商简称
				String type = getContractType(purchaseType);
				
				String typeParentId = BusinessService.getshortYearcode()+type;				
				String supplierParentId = BusinessService.getshortYearcode() + shortName;				
				String typeSubId = getContractTypeCode(typeParentId);
				String suplierSubId = getContractSupplierCode(supplierParentId);

				//3位流水号格式化	
				//采购合同编号:16D081-WL002
				String contractId = BusinessService.getContractCode(type,typeSubId, suplierSubId,shortName);
				
				//新增采购合同*************
				B_PurchaseOrderData data = new B_PurchaseOrderData();
				
				data.setYsid(YSId);
				data.setMaterialid(materialId);
				data.setContractid(contractId);
				data.setTypeparentid(typeParentId);
				data.setTypeserial(typeSubId);
				data.setSupplierparentid(supplierParentId);
				data.setSupplierserial(suplierSubId);
				data.setSupplierid(supplierId);
				data.setTotal(total);
				data.setPurchasedate(CalendarUtil.fmtYmdDate());
				data.setDeliverydate(CalendarUtil.dateAddToString(data.getPurchasedate(),20));
				data.setVersion(1);//默认为1
				
				insertPurchaseOrder(data);//新增合同头表
				
				//新增合同明细*************
				List<HashMap<String, String>> dbList = getMaterialGroupList(YSId,supplierId);
				
				for(HashMap<String, String> dt:dbList){					
					
					B_PurchaseOrderDetailData d = new B_PurchaseOrderDetailData();				
					d.setYsid(YSId);
					d.setContractid(contractId);
					d.setMaterialid(dt.get("materialId"));
					d.setQuantity(dt.get("purchaseQuantity"));
					d.setPrice(dt.get("price"));					
					d.setTotalprice(dt.get("totalPrice"));
					d.setUnitquantity(dt.get("unitQuantity"));
					d.setVersion(1);//默认为1
					
					insertPurchaseOrderDetail(d);
										
					continue;					
				}				
			}
			
			*/
			
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
			
			updateOrder(orderData);

			String ysid = orderData.getYsid();
			
			//合同详情 更新 处理			
			List<B_PurchaseOrderDetailData> newDetailList = reqModel.getDetailList();
						
			//页面数据的recordId与DB匹配			
			for(B_PurchaseOrderDetailData data:newDetailList ){
				
				//更新处理
				B_PurchaseOrderDetailData dbDt = updateOrderDetail(data);

				float oldQty = stringToFloat(dbDt.getQuantity());
				//float oldPrice = stringToFloat(dbDt.getPrice());
				float reqQty = stringToFloat(data.getQuantity());
				//float reqPrice = stringToFloat(data.getPrice());
				
				
				//数量有变动才更新库存
				if(!("").equals(dbDt) && oldQty != reqQty) {
					String newQty = String.valueOf(reqQty - oldQty);					
					//恢复库存"待入数量",合同只处理待入数量,待出在采购方案里面
					updateMaterial(data.getMaterialid(),newQty,"0");
				}
			
				/*
				//同步采购方案:单价,数量
				if(isNullOrEmpty(ysid))
					continue;
				
				if(!("").equals(dbDt) && 
						(oldQty != reqQty || oldPrice != reqPrice)){
					
					updatePurchasePlanDetail(ysid,data.getMaterialid(),reqPrice,reqQty);
				}
				
				*/
			}
			
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
			System.out.println(e.getMessage());
		}
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
	private B_PurchaseOrderDetailData updateOrderDetail(
			B_PurchaseOrderDetailData data) throws Exception{
		
		B_PurchaseOrderDetailData rtnDt= new B_PurchaseOrderDetailData();
		try{

			B_PurchaseOrderDetailData db = new B_PurchaseOrderDetailDao(data).beanData;
			
			copyProperties(rtnDt,db);//返回值
			//处理共通信息
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"PurchaseOrderDetailUpdate",userInfo);
			copyProperties(db,commData);		
			//获取页面数据
			db.setQuantity(data.getQuantity());
			db.setPrice(data.getPrice());
			db.setTotalprice(data.getTotalprice());
			
			detailDao.Store(db);
			
		}catch(Exception e){
			
		}
		
		return rtnDt;
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
		//String supplierId = request.getParameter("supplierId");
		
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
		
		//取得供应商的合同流水号
		//父编号:年份+供应商简称
		
		//取得供应商的合同流水号
		//父编号:年份+供应商简称
		//String typeParentId = BusinessService.getshortYearcode()+Constants.PURCHASE_TYPE_T;
		
		//String supplierParentId = BusinessService.getshortYearcode() + shortName;
		
		//String typeSubId = getContractTypeCode(typeParentId);
		//String suplierSubId = getContractSupplierCode(supplierParentId);

		//3位流水号格式化	
		//采购合同编号:16D081-WL002
		//String contractId = BusinessService.getContractCodeD(typeSubId, suplierSubId,shortName);

		
		
		//3位流水号格式化	
		//采购合同编号:16YS081-WL002
		String type = getContractType(purchaseType);
		
		String typeParentId = BusinessService.getshortYearcode()+type;				
		String supplierParentId = BusinessService.getshortYearcode() + shortName;				
		String typeSubId = getContractTypeCode(typeParentId);
		String suplierSubId = getContractSupplierCode(supplierParentId);

		//3位流水号格式化	
		//采购合同编号:16D081-WL002
		String contractId = BusinessService.getContractCode(type,typeSubId, suplierSubId,shortName);


		dt.setYsid(YSId);
		dt.setContractid(contractId);
		dt.setTypeparentid(typeParentId);
		dt.setTypeserial(typeSubId);
		dt.setSupplierparentid(supplierParentId);
		dt.setSupplierserial(suplierSubId);
		
		return dt;

	}
	
	private String insertRoutine(){
		
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
				String purchaseType = d.getContractid();//页面端借用Contractid传值物料特性
				contract = geRoutinePurchaseContractId(contract,YSId,shortName,purchaseType);
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
				updateMaterial(materilid,purchase,requirement);
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
			
			/*
			if(!(YSId == null || ("").equals(YSId))){
				String[] tmp = YSId.split("-");
				if(tmp.length>1)
					YSId = tmp[0];//配件订单的耀升编号形式:17YS001-1
			}
			*/
			//B_PurchaseOrderData contract = reqModel.getContract();
			//List<B_PurchaseOrderData> contractList = reqModel.getContractList();
			//List<B_PurchaseOrderDetailData> detailList = reqModel.getDetailList();
						
			//String YSId = contract.getYsid();
			//String purchaseDate = contract.getPurchasedate();
			//String contractDelivery = contract.getDeliverydate();
			
			
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
				updateMaterial(materilid,purchase,requirement);
			}	

			ts.commit();
			
			//删除既存合同信息
			//deleteContract(YSId);
			
			//删除既存合同明细
			//deleteContractDetail(YSId);			
			
			/*
			//供应商集计
			List<B_SupplierData> supplier = new ArrayList<B_SupplierData>();			
			for(int j=0;j<contractList.size();j++){
				
				String supplierId = contractList.get(j).getSupplierid();
				String shortName  = contractList.get(j).getTypeserial();//临时借用
				String totalPrice = detailList.get(j).getTotalprice();//合同跟明细件数相同
				float f1 = stringToFloat(totalPrice);
				
				if(supplierId == null || supplierId.equals("")){
					continue;
				}
				
				boolean repeatFlag = true;
				for(int i=0;i<supplier.size();i++){
					String id = supplier.get(i).getSupplierid();
					String price = supplier.get(i).getSuppliername();//临时借用
					
					if(supplierId.equals(id)){//计算重复的供应商
						float f2 = stringToFloat(price);
						supplier.get(i).setSuppliername(String.valueOf(f1+f2));//相同供应商的价格合并
						repeatFlag = false;
						break;
					}
				}
				
				if(repeatFlag){//保存不一致的供应商
					B_SupplierData s = new B_SupplierData();
					s.setSupplierid(supplierId);
					s.setShortname(shortName);
					s.setSuppliername(totalPrice);//临时借用
					supplier.add(s);
				}
				
			}//供应商集计
			
			for(B_SupplierData sup: supplier){
				
				String supplierId = sup.getSupplierid();
				String shortName  = sup.getShortname();
				String total  = sup.getSuppliername();//临时借用
								
				float totalf = stringToFloat(total); 
				if(totalf == 0){//采购数量为零的供应商不计入合同
					continue;
				}
				//取得供应商的合同流水号
				//父编号:年份+供应商简称
				String parentId = "";
				if(null != YSId && YSId.length() > 3 ){
					parentId = YSId.substring(0,2);//耀升编号前两位是年份
				}
				parentId = parentId + shortName;

				//取得供应商的合同流水号
				//父编号:年份+供应商简称
				String type = "D";//作为订购件处理
				
				String typeParentId = BusinessService.getshortYearcode()+type;				
				String supplierParentId = BusinessService.getshortYearcode() + shortName;				
				String typeSubId = getContractTypeCode(typeParentId);
				String suplierSubId = getContractSupplierCode(supplierParentId);

				//3位流水号格式化	
				//采购合同编号:16D081-WL002
				contractId = BusinessService.getContractCodeD(typeSubId, suplierSubId,shortName);
				
				//新增采购合同*************
				B_PurchaseOrderData data = new B_PurchaseOrderData();
				
				data.setYsid(YSId);
				//data.setMaterialid(materialId);//配件订单没有统一的产品
				data.setContractid(contractId);
				data.setTypeparentid(typeParentId);
				data.setTypeserial(typeSubId);
				data.setSupplierparentid(supplierParentId);
				data.setSupplierserial(suplierSubId);
				data.setSupplierid(supplierId);
				data.setTotal(total);
				data.setPurchasedate(purchaseDate);
				data.setDeliverydate(contractDelivery);
				data.setVersion(1);//默认为1
				
				insertPurchaseOrder(data);//新增合同头表

				for(int i=0;i<detailList.size();i++){
					B_PurchaseOrderDetailData detail = detailList.get(i);
				
					String id = contractList.get(i).getSupplierid();
					if(supplierId.equals(id)){
						//新增合同明细
						detail.setYsid(YSId);
						detail.setContractid(contractId);
						insertPurchaseOrderDetail(detail);						
					}
				}
				
			}
			*/
			
			
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
			String YSId) throws Exception {

		String where = " YSId = '" + YSId  +"' AND deleteflag = '0'";
		return (List<B_PurchaseOrderDetailData>) new B_PurchaseOrderDetailDao().Find(where);
				
	}
}
