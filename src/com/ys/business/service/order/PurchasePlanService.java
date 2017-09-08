package com.ys.business.service.order;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.system.service.common.BaseService;
import com.ys.util.CalendarUtil;
import com.ys.util.DicUtil;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basedao.BaseTransaction;
import com.ys.util.basequery.BaseQuery;
import com.ys.util.basequery.common.BaseModel;
import com.ys.util.basequery.common.Constants;
import com.ys.business.action.model.order.PurchasePlanModel;
import com.ys.business.db.dao.B_BomDetailDao;
import com.ys.business.db.dao.B_MaterialDao;
import com.ys.business.db.dao.B_OrderDetailDao;
import com.ys.business.db.dao.B_PurchasePlanDao;
import com.ys.business.db.dao.B_PurchasePlanDetailDao;
import com.ys.business.db.dao.B_PriceSupplierDao;
import com.ys.business.db.dao.B_PurchaseOrderDao;
import com.ys.business.db.dao.B_PurchaseOrderDetailDao;
import com.ys.business.db.data.B_BomDetailData;
import com.ys.business.db.data.B_MaterialData;
import com.ys.business.db.data.B_OrderBomData;
import com.ys.business.db.data.B_OrderBomDetailData;
import com.ys.business.db.data.B_OrderDetailData;
import com.ys.business.db.data.B_PurchasePlanData;
import com.ys.business.db.data.B_PurchasePlanDetailData;
import com.ys.business.db.data.B_SupplierData;
import com.ys.business.db.data.B_PriceSupplierData;
import com.ys.business.db.data.B_PurchaseOrderData;
import com.ys.business.db.data.B_PurchaseOrderDetailData;
import com.ys.business.db.data.CommFieldsData;
import com.ys.business.service.common.BusinessService;

@Service
public class PurchasePlanService extends CommonService {

	DicUtil util = new DicUtil();

	BaseTransaction ts;

	String guid ="";
	private CommFieldsData commData;
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	
	private B_PurchasePlanDao planDao;
	private B_PurchasePlanDetailDao planDetailDao;
	private PurchasePlanModel reqModel;
	private BaseModel dataModel;
	private Model model;
	private UserInfo userInfo;
	
	private HashMap<String, String> userDefinedSearchCase;
	private BaseQuery baseQuery;
	ArrayList<HashMap<String, String>> modelMap = null;	

	public PurchasePlanService(){
		
	}

	public PurchasePlanService(Model model,
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			PurchasePlanModel reqModel,
			UserInfo userInfo){
		
		this.planDao = new B_PurchasePlanDao();
		this.planDetailDao = new B_PurchasePlanDetailDao();
		this.model = model;
		this.reqModel = reqModel;
		this.request = request;
		this.response = response;
		this.session = session;
		this.userInfo = userInfo;
		this.dataModel = new BaseModel();
		this.dataModel.setQueryFileName("/business/order/purchasequerydefine");
		this.userDefinedSearchCase = new HashMap<String, String>();
		super.request = request;
		super.userInfo = userInfo;
		super.session = session;
		
	}

	public HashMap<String, Object> getBomList(String data) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		dataModel = new BaseModel();

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
		
		String[] keyArr = getSearchKey(Constants.FORM_PURCHASEPLAN,data,session);
		String key1 = keyArr[0];
		String key2 = keyArr[1];

		dataModel.setQueryFileName("/business/order/purchasequerydefine");
		dataModel.setQueryName("getBomList");
		
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase = new HashMap<String, String>();
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(iStart, iEnd);	 
		
		if ( iEnd > dataModel.getYsViewData().size()){
			
			iEnd = dataModel.getYsViewData().size();			
		}		
		
		modelMap.put("sEcho", sEcho);		
		modelMap.put("recordsTotal", dataModel.getRecordCount()); 		
		modelMap.put("recordsFiltered", dataModel.getRecordCount());
		modelMap.put("unitList",util.getListOption(DicUtil.MEASURESTYPE, ""));		
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
	}
	
	
	public HashMap<String, Object> getBomApproveList(String data) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		dataModel = new BaseModel();

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
		
		String key1 = getJsonData(data, "keyword1").toUpperCase();
		String key2 = getJsonData(data, "keyword2").toUpperCase();

		dataModel.setQueryFileName("/business/order/purchasequerydefine");
		dataModel.setQueryName("getOrderList");
		
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase = new HashMap<String, String>();
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(iStart, iEnd);	 
		
		modelMap.put("sEcho", sEcho);		
		modelMap.put("recordsTotal", dataModel.getRecordCount()); 		
		modelMap.put("recordsFiltered", dataModel.getRecordCount());
		modelMap.put("unitList",util.getListOption(DicUtil.MEASURESTYPE, ""));		
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
	}
	
	public void getBomDetailView(String bomId) 
			throws Exception {
		
		dataModel = new BaseModel();		
		dataModel.setQueryFileName("/business/order/bomquerydefine");
		dataModel.setQueryName("getBaseBomDetailByBomId");
		
		baseQuery = new BaseQuery(request, dataModel);

		userDefinedSearchCase.put("bomId", bomId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		modelMap = baseQuery.getYsFullData();

		if(dataModel.getRecordCount() > 0){
			model.addAttribute("baseBom", dataModel.getYsViewData().get(0));
			model.addAttribute("bomDetail", dataModel.getYsViewData());
		}
		
	}
	

	public HashMap<String, Object> getOrderDetailByYSId(String YSId) throws Exception{

		HashMap<String, Object> HashMap = new HashMap<String, Object>();

		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("getOrderList");
		
		baseQuery = new BaseQuery(request, dataModel);

		userDefinedSearchCase.put("keywords1", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		baseQuery.getYsQueryData(0, 0);
		
		model.addAttribute("order", dataModel.getYsViewData().get(0));	
		HashMap.put("data", dataModel.getYsViewData());
		return HashMap;
		
	}
	
	
	
	public String getPurchaseId(String YSId) throws Exception {
			
		String timestemp = CalendarUtil.getDateyymmdd();

		return YSId+"-"+ timestemp;
		
	}
	

	/*
	 * update处理
	 */
	private String update(){
		
		ts = new BaseTransaction();
		String  purchaseId="";
		
		try {
			ts.begin();

			B_PurchasePlanData reqPlan = reqModel.getPurchasePlan();
			String YSId = reqPlan.getYsid();
			purchaseId = reqPlan.getPurchaseid();		

			//更新采购方案head表	
			int version = updatePurchasePlan(reqPlan);
						
			//更新前数据取得
			List<B_PurchasePlanDetailData> oldList = getPurchasePlanDetail(YSId);			
			
			//旧数据:采购方案,的待出库"减少"处理
			for(B_PurchasePlanDetailData old:oldList){

				String oldmaterilid = old.getMaterialid();				

				//旧数据物料的待出库"减少"处理
				String stock = String.valueOf(0 - stringToFloat(old.getPurchasequantity()));
				//updateMaterial(oldmaterilid,stock);
				
				old.setPurchasequantity(stock);
				//旧数据的逻辑删除处理
				updatePurchasePlanDetail(old);				
				
			}//for
			
			
			//新数据:采购方案处理
			List<B_PurchasePlanDetailData> list = reqModel.getPlanDetailList();
			
			for(B_PurchasePlanDetailData detail:list){
				String materilid = detail.getMaterialid();
				if(materilid == null || ("").equals(materilid)){
					continue;		
				}
				detail.setPurchaseid(purchaseId);
				detail.setYsid(YSId);
				detail.setVersion(version);
				insertPurchasePlanDetail(detail);			

				//更新虚拟库存
				String stock = detail.getPurchasequantity();
				//updateMaterial(materilid,stock);
				
			}//for		
			
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
		
		return purchaseId;
	}
	
	/*
	 * insert处理
	 */
	@SuppressWarnings("unchecked")
	private String insert(){
		
		ts = new BaseTransaction();
		String  purchaseId="";
		
		try {
			ts.begin();

			B_PurchasePlanData reqPlan = reqModel.getPurchasePlan();
			String YSId = reqPlan.getYsid();
			String materialId = reqPlan.getMaterialid();
			
			//设置采购方案编号
			purchaseId = getPurchaseId(YSId);					

			//采购方案****************************************************
			reqPlan.setPurchaseid(purchaseId);		
			insertPurchasePlan(reqPlan);
			
			List<B_PurchasePlanDetailData> reqPlanList = reqModel.getPlanDetailList();
			
			for(B_PurchasePlanDetailData detail:reqPlanList){
				String materilid = detail.getMaterialid();
				if(materilid == null || ("").equals(materilid)){
					continue;		
				}
				detail.setPurchaseid(purchaseId);
				detail.setYsid(YSId);
				detail.setVersion(1);//第一次生成时,默认:1
				insertPurchasePlanDetail(detail);
				
				//更新虚拟库存
				String purchase = detail.getPurchasequantity();//采购量
				String requirement = detail.getManufacturequantity();//需求量
				updateMaterial(materilid,requirement,purchase);
			}
			
			//采购合同****************************************************
			
			//供应商集计
			//以采购方案里的供应商为单位集计
			ArrayList<HashMap<String, String>> supplierList = getSupplierList(YSId);
					
			if(supplierList == null || supplierList.size() <= 0)
				return purchaseId;
			
			
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
					d.setVersion(1);//默认为1
					
					insertPurchaseOrderDetail(d);
										
					continue;					
				}				
			}			
			
			
/*
			List<B_PurchasePlanDetailData> supplier = new ArrayList<B_PurchasePlanDetailData>();			
			for(int j=0;j<reqPlanList.size();j++){
				
				String supplierId = reqPlanList.get(j).getSupplierid();
				String shortName  = reqPlanList.get(j).getSuppliershortname();
				String totalprice = reqPlanList.get(j).getTotalprice();//总价
				String manufact   = reqPlanList.get(j).getManufacturequantity();//需求量
				String purchase   = reqPlanList.get(j).getPurchasequantity();//采购量
				
				if(supplierId == null || supplierId.equals("")){
					continue;
				}				
				boolean repeatFlag = true;
				for(int i=0;i<supplier.size();i++){
					String id = supplier.get(i).getSupplierid();
					int count = supplier.get(i).getVersion();//借用版本号计算供应商重复次数
					
					if(supplierId.equals(id)){//计算重复的供应商
						float totalprice1 = stringToFloat(totalprice);//总价
						float manufact1   = stringToFloat(manufact);//需求量
						float purchase1   = stringToFloat(purchase);//采购量
						
						float totalprice2 = stringToFloat(supplier.get(i).getTotalprice());
						float manufact2 = stringToFloat(supplier.get(i).getManufacturequantity());//需求量
						float purchase2 = stringToFloat(supplier.get(i).getPurchasequantity());//采购量
						supplier.get(i).setTotalprice(String.valueOf(totalprice1+totalprice2));//相同供应商的价格合并
						supplier.get(i).setManufacturequantity(String.valueOf(manufact1+manufact2));//相同供应商的需求量合并
						supplier.get(i).setPurchasequantity(String.valueOf(purchase1+purchase2));//相同供应商的采购量合并
						supplier.get(i).setVersion(count++);
						repeatFlag = false;
						break;
					}
				}				
				if(repeatFlag){//保存不一致的供应商
					B_PurchasePlanDetailData s = new B_PurchasePlanDetailData();
					s.setSupplierid(supplierId);
					s.setSuppliershortname(shortName);
					s.setTotalprice(totalprice);
					s.setManufacturequantity(manufact);
					s.setPurchasequantity(purchase);
					s.setVersion(0);
					
					supplier.add(s);
				}
				
			}//供应商集计
			
				*/
			//取得合同里的供应商
			//List<B_PurchaseOrderData> supplierContrct = getSupplierContrct(YSId);

			//逻辑删除既存合同信息
			//deleteContract(YSId);
			
			//删除既存合同明细
			//deleteContractDetail(YSId);
			
			/*
			for(int i=0;i<supplier.size();i++){
				
				String supplierId = supplier.get(i).getSupplierid();
				String shortName  = supplier.get(i).getSuppliershortname();
				String total      = supplier.get(i).getTotalprice();
				String purchaseType = supplier.get(i).getPurchasetype();
				int materialCont  = supplier.get(i).getVersion();//供应商对应的物料数量
				
				if(supplierId == null || supplierId.equals("")){
					continue;
				}
				float totalf = stringToFloat(total); 
				if(totalf == 0){//采购数量为零的供应商不计入合同
					continue;
				}
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
				
				//新增采购合同

				B_PurchaseOrderData data = new B_PurchaseOrderData();
				
				data.setRecordid(guid);
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
				
				insertPurchaseOrder(data);//新增合同

				//新增合同明细
				int index = 0;
				for(B_PurchasePlanDetailData dt:reqPlanList){
					
					if (index > materialCont)
						break;//物料处理完后,直接退出
					
					if (dt.getSupplierid().equals(supplierId)){

						B_PurchaseOrderDetailData d = new B_PurchaseOrderDetailData();				
						d.setYsid(YSId);
						d.setContractid(contractId);
						d.setMaterialid(dt.getMaterialid());
						d.setQuantity(dt.getPurchasequantity());
						d.setPrice(dt.getPrice());					
						d.setTotalprice(dt.getTotalprice());
						d.setVersion(1);//默认为1
						
						insertPurchaseOrderDetail(d);
						
						index++;						
						continue;
					}	
				}
				
			}
			*/
			
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
		
		return purchaseId;
	}
	/*
	 * insert处理
	 */
	public void deletePurchasePlan(String YSId) throws Exception{
		
		String astr_Where = " YSId = '" + YSId + "'";
		
		planDetailDao.RemoveByWhere(astr_Where);	

	}	
	/*
	 * insert处理
	 */
	public void insertPurchasePlan(
			B_PurchasePlanData data) throws Exception{
		
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"PurchasePlanInsert",userInfo);
		copyProperties(data,commData);

		guid = BaseDAO.getGuId();
		data.setRecordid(guid);
		data.setPlandate(CalendarUtil.fmtYmdDate());
		data.setVersion(1);//新增时,默认为1
		
		planDao.Create(data);	

	}	
	
	/*
	 * insert处理
	 */
	public void insertPurchasePlanDetail(
			B_PurchasePlanDetailData data) throws Exception{
		
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"PurchasePlanDetailInsert",userInfo);
		copyProperties(data,commData);

		guid = BaseDAO.getGuId();
		data.setRecordid(guid);
		
		planDetailDao.Create(data);	

	}	
		
	//更新虚拟库存:生成物料需求时增加“待出库”
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
		
		iWaitOut = iWaitOut + stringToFloat(requirementOut);
		iWaitIn = iWaitIn + stringToFloat(purchaseIn);
		
		//虚拟库存 = 当前库存 + 待入库 - 待出库
		float availabeltopromise = iOnhand + iWaitIn - iWaitOut;		
		
		//更新DB
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"PurchasePlanInsert",userInfo);
		copyProperties(data,commData);
		
		data.setAvailabeltopromise(String.valueOf(availabeltopromise));//虚拟库存
		data.setWaitstockout(String.valueOf(iWaitOut));//待出库
		data.setWaitstockin(String.valueOf(iWaitIn));//待入库
		
		dao.Store(data);
		
	}
	
	/*
	 * 更新供应商单价
	 */
	@SuppressWarnings("unchecked")
	public void updatePriceSupplier(
			String materialId,
			String supplierId,
			String price ) throws Exception {
		
		List<B_PriceSupplierData> priceList = null;
		B_PriceSupplierData pricedt = null;
		B_PriceSupplierDao dao = new B_PriceSupplierDao();

		String where ="materialId= '" + materialId + "'" + 
				" AND supplierId = '" + supplierId + "'" + 
				" AND deleteFlag = '0' ";		

		priceList = (List<B_PriceSupplierData>)dao.Find(where);
		
		if(priceList != null && priceList.size() > 0){
			
			pricedt = priceList.get(0);	

			if(!price.equals(pricedt.getPrice())){//价格有变动的话,更新为最新价格

				//处理共通信息					
				commData = commFiledEdit(Constants.ACCESSTYPE_UPD,"PriceSupplierUpdate",userInfo);
				copyProperties(pricedt,commData);
				
				pricedt.setPrice(price);
				
				dao.Store(pricedt);
			}
		}
				
	}
	
	/*
	 * BOM详情删除处理
	 */
	public void deletePlanDetail(String where) 
			throws Exception{
		
		try{
			planDao.RemoveByWhere(where);
		}catch(Exception e){
			//nothing
		}	
	}

	/*
	 * 更新
	 */
	private int updatePurchasePlan(B_PurchasePlanData reqPlan) throws Exception{
		
		int version = 0;
		//确认数据是否存在		
		B_PurchasePlanData dbPlan = new B_PurchasePlanDao(reqPlan).beanData;
		if(!(dbPlan == null || ("").equals(dbPlan))){	
			//update处理	
			version = dbPlan.getVersion();
			
			copyProperties(dbPlan,reqPlan);
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"purchasePlanUpdate",userInfo);			
			copyProperties(dbPlan,commData);
			reqPlan.setVersion(version++);
			planDao.Store(dbPlan);

		}else{
			//insert处理
		}
		
		return version;
	}	
	
	/*
	 * 更新
	 */
	private void updatePurchasePlanDetail(B_PurchasePlanDetailData reqPlan) throws Exception{
		
		//确认Order表是否存在		
		reqPlan = new B_PurchasePlanDetailDao(reqPlan).beanData;
		if(!(reqPlan == null || ("").equals(reqPlan))){				
			//update处理			
			commData = commFiledEdit(Constants.ACCESSTYPE_DEL,
					"purchasePlanUpdate",userInfo);			
			copyProperties(reqPlan,commData);

			planDetailDao.Store(reqPlan);	
			
		}else{
			//insert处理
		}
	}	
	
	@SuppressWarnings("unchecked")
	public B_OrderDetailData getOrderByYSId(
			B_OrderDetailDao dao,
			String YSId ) throws Exception {
		
		List<B_OrderDetailData> dbList = null;
		
		String where = " YSId = '"+YSId +"'" + " AND deleteFlag = '0' ";
						
		dbList = (List<B_OrderDetailData>)dao.Find(where);
		
		if(dbList != null & dbList.size() > 0)
			return dbList.get(0);		
		
		return null;
	}

	public void createBomPlan() throws Exception {

		String YSId = request.getParameter("YSId");
		String materialId = request.getParameter("materialId");
		String bomId = BusinessService.getBaseBomId(materialId)[1];
		
		getOrderDetailByYSId(YSId);
		
		getBomDetailView(bomId);	
		
	}
	
	
	

	public void editPurchasePlan() throws Exception {

		String YSId = request.getParameter("YSId");

		getOrderDetailByYSId(YSId);
		
		getPurchaseDetail(YSId);
		
	}

	
	public Model insertAndView() throws Exception {

		String YSId = insert();
		getOrderDetailByYSId(YSId);
		
		return model;
		
	}
	

	public Model updateAndView() throws Exception {

		String YSId = update();
		getOrderDetailByYSId(YSId);
		
		return model;
		
	}
	
	public void showPurchaseDetail() throws Exception {
		
		String YSId = request.getParameter("YSId");	

		getOrderDetailByYSId(YSId);
		
	}
	
	public HashMap<String, Object> purchasePlanView() throws Exception {
		
		String YSId = request.getParameter("YSId");	

		return getPurchaseDetail(YSId);
		
	}
	
	public HashMap<String, Object> getPurchaseDetail(
			String YSId) throws Exception {

		HashMap<String, Object> HashMap = new HashMap<String, Object>();
		dataModel = new BaseModel();		
		dataModel.setQueryFileName("/business/order/purchasequerydefine");
		dataModel.setQueryName("getPurchaseDetail");
		
		baseQuery = new BaseQuery(request, dataModel);

		userDefinedSearchCase.put("YSId", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		baseQuery.getYsFullData();
		
		if(dataModel.getRecordCount() > 0){
			HashMap.put("data", dataModel.getYsViewData());
			model.addAttribute("purchasePlan",dataModel.getYsViewData().get(0));
			model.addAttribute("planDetail",dataModel.getYsViewData());
		}
		return HashMap;
	}
	
	public B_PurchasePlanDetailData getPurchasePlanDetail(
			B_PurchasePlanDetailData data) throws Exception {
		
		return (B_PurchasePlanDetailData) planDao.FindByPrimaryKey(data);
				
	}
	
	@SuppressWarnings("unchecked")
	public List<B_PurchasePlanDetailData> getPurchasePlanDetail(
			String  YSId) throws Exception {
		
		String where = " YSId = '" +YSId + "' ";
		return (List<B_PurchasePlanDetailData>) planDetailDao.Find(where);
				
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

		dataModel.setQueryName("getContractTypeCode");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("supplierParentId", parentId);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, 0);			
		String code =dataModel.getYsViewData().get(0).get("MaxSubId");		
		return code;		
	}	
	
	private void insertPurchaseOrderDetail(B_PurchaseOrderDetailData data) throws Exception{
		B_PurchaseOrderDetailDao dao = new B_PurchaseOrderDetailDao();

		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"RoutinePurchaseOrderDetailInsert",userInfo);
		copyProperties(data,commData);		
		guid = BaseDAO.getGuId();
		data.setRecordid(guid);
		
		dao.Create(data);	
	}
	
	private void insertPurchaseOrder(B_PurchaseOrderData data) throws Exception{
		B_PurchaseOrderDao dao = new B_PurchaseOrderDao();

		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"PurchaseOrderInsert",userInfo);
		copyProperties(data,commData);		
		guid = BaseDAO.getGuId();
		data.setRecordid(guid);
		
		dao.Create(data);	
	}

	public ArrayList<HashMap<String, String>> getSupplierList(String YSId) throws Exception {		

		dataModel.setQueryFileName("/business/order/purchasequerydefine");
		dataModel.setQueryName("getContractSupplierList");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("YSId", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		return baseQuery.getYsFullData();
		
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
}
