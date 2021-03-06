package com.ys.business.service.order;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ys.business.action.model.order.ArrivalModel;
import com.ys.business.action.model.order.StorageModel;
import com.ys.business.db.dao.B_ArrivalDao;
import com.ys.business.db.dao.B_InspectionProcessDao;
import com.ys.business.db.dao.B_MaterialDao;
import com.ys.business.db.dao.B_PurchaseOrderDetailDao;
import com.ys.business.db.dao.B_PurchaseStockInDao;
import com.ys.business.db.dao.B_PurchaseStockInDetailDao;
import com.ys.business.db.data.B_ArrivalData;
import com.ys.business.db.data.B_InspectionProcessData;
import com.ys.business.db.data.B_MaterialData;
import com.ys.business.db.data.B_PurchaseOrderDetailData;
import com.ys.business.db.data.B_PurchaseStockInData;
import com.ys.business.db.data.B_PurchaseStockInDetailData;
import com.ys.business.db.data.B_ReceiveInspectionData;
import com.ys.business.db.data.CommFieldsData;
import com.ys.business.service.common.BusinessService;
import com.ys.system.action.model.login.UserInfo;
import com.ys.util.basequery.common.BaseModel;
import com.ys.util.basequery.common.Constants;
import com.ys.util.CalendarUtil;
import com.ys.util.DicUtil;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basedao.BaseTransaction;
import com.ys.util.basequery.BaseQuery;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class StorageService extends CommonService {
 
	DicUtil util = new DicUtil();
	BaseTransaction ts;

	String guid ="";
	private CommFieldsData commData;
	
	private HttpServletRequest request;
	
	private B_PurchaseStockInDao dao;
	private B_PurchaseStockInDetailDao detaildao;
	private StorageModel reqModel;
	private UserInfo userInfo;
	private BaseModel dataModel;
	private  Model model;
	private HashMap<String, String> userDefinedSearchCase;
	private BaseQuery baseQuery;
	HashMap<String, Object> modelMap = null;
	HttpSession session;	

	public StorageService(){
		
	}

	public StorageService(Model model,
			HttpServletRequest request,
			HttpSession session,
			StorageModel reqModel,
			UserInfo userInfo){
		
		this.dao = new B_PurchaseStockInDao();
		this.detaildao = new B_PurchaseStockInDetailDao();
		this.model = model;
		this.reqModel = reqModel;
		this.request = request;
		this.userInfo = userInfo;
		this.session = session;
		dataModel = new BaseModel();
		modelMap = new HashMap<String, Object>();
		userDefinedSearchCase = new HashMap<String, String>();
		dataModel.setQueryFileName("/business/material/inventoryquerydefine");
		super.request = request;
		super.userInfo = userInfo;
		super.session = session;
		
	}
	public HashMap<String, Object> doSearch( String data) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		int iStart = 0;
		int iEnd =0;
		String sEcho = "";
		String start = "";
		String length = "";
		
		data = URLDecoder.decode(data, "UTF-8");
		
		String result1 = request.getParameter("result1");
		String result2 = request.getParameter("result2");

		String[] keyArr = getSearchKey(Constants.FORM_STORAGE,data,session);
		String key1 = keyArr[0];
		String key2 = keyArr[1];
		
		sEcho = getJsonData(data, "sEcho");	
		start = getJsonData(data, "iDisplayStart");		
		if (start != null && !start.equals("")){
			iStart = Integer.parseInt(start);			
		}
		
		length = getJsonData(data, "iDisplayLength");
		if (length != null && !length.equals("")){			
			iEnd = iStart + Integer.parseInt(length);			
		}		
		
		dataModel.setQueryName("getMaterialCheckInList");
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
		userDefinedSearchCase.put("result1", result1);
		userDefinedSearchCase.put("result2", result2);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(iStart, iEnd);	
				
		if ( iEnd > dataModel.getYsViewData().size()){
			
			iEnd = dataModel.getYsViewData().size();
			
		}
		
		modelMap.put("sEcho", sEcho); 
		
		modelMap.put("recordsTotal", dataModel.getRecordCount()); 
		
		modelMap.put("recordsFiltered", dataModel.getRecordCount());
		
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;		

	}
	

	public HashMap<String, Object> contractArrivalSearch(
			String data) throws Exception {
		
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
	
		dataModel.setQueryName("getArrivaList");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		String[] keyArr = getSearchKey(Constants.FORM_ARRIVAL,data,session);
		String key1 = keyArr[0];
		String key2 = keyArr[1];
		
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
			
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
	}

	public boolean addInit() throws Exception {
		boolean viewFlag=true;
		String contractId = request.getParameter("contractId");
		String arrivalId = request.getParameter("arrivalId");
		String receiptId = request.getParameter("receiptId");
		
		//确认是否已经入库
		if(receiptId ==null || receiptId.equals("")){
			viewFlag = false;
			//取得入库申请编号
			getStorageRecordId(contractId);
			
			//取得该到货编号下的物料信息
			getContractDetail(arrivalId);
		}else{
			model.addAttribute("receiptId",receiptId);//已入库
		}
		
		return viewFlag;
	
	}
	
	public void edit() throws Exception {
		String contractId = request.getParameter("contractId");
		String arrivalId = request.getParameter("arrivalId");
		String receiptId = request.getParameter("receiptId");
		
		//getArrivaRecord(receiptId);
		model.addAttribute("receiptId",receiptId);//已入库
		
	
	}

	public HashMap<String, Object> getStockInDetail() {

		String receiptId = request.getParameter("receiptId");
		return getArrivaRecord(receiptId);
	}
	
	public void insertAndReturn() throws Exception {

		String receiptid = insertStorage(true);
		
		model.addAttribute("receiptId",receiptid);//返回到页面
	}
	
	public void updateAndReturn() throws Exception {

		String receiptid = insertStorage(false);
		
		model.addAttribute("receiptId",receiptid);//返回到页面
	}
	
	
	private String insertStorage(boolean accessFlag){
		String receiptid = "";
		ts = new BaseTransaction();		
		
		try {
			ts.begin();
			
			B_PurchaseStockInData reqData = reqModel.getStock();
			List<B_PurchaseStockInDetailData> reqDataList = 
					reqModel.getStockList();
			
			//采购入库记录
			insertPurchaseStockIn(reqData);
			receiptid = reqData.getReceiptid();
			String arrivalid = reqData.getArrivelid();
			
			//先删除已经存在的入库明细
			String where = " receiptId = '"+receiptid+"'";
			try {
				detaildao.RemoveByWhere(where);
			} catch (Exception e) {
				// nothing
			}
			
			//采购入库记录明细
			for(B_PurchaseStockInDetailData data:reqDataList ){
				String quantity = data.getQuantity();
				String materialid= data.getMaterialid();
				if(quantity == null || quantity.equals("") || quantity.equals("0"))
					continue;
				
				commData = commFiledEdit(Constants.ACCESSTYPE_INS,
						"ArrivalInsert",userInfo);

				copyProperties(data,commData);

				String guid = BaseDAO.getGuId();
				data.setRecordid(guid);
				data.setReceiptid(receiptid);
				//
				detaildao.Create(data);	
				
				//更新当前库存
				if(accessFlag){
					updateMaterial(materialid,quantity);//更新入库记录时,不再更新库存数量

					//更新到货记录的物料状态
					updateInspectionProcess(arrivalid,materialid);
					
					//更新合同的累计入库数量
					updateContractStorage(reqData.getContractid(),materialid,quantity);
					
				}
				
			}
			
			ts.commit();			
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			try {
				ts.rollback();
			} catch (Exception e1) {
				System.out.println(e1.getMessage());
			}
		}
		
		return receiptid;
	}
	
	//更新当前库存:采购入库时，减少“待入库”，增加“当前库存”
	@SuppressWarnings("unchecked")
	private void updateMaterial(
			String materialId,
			String reqQuantity) throws Exception{
	
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
		float iQuantity = stringToFloat(data.getQuantityonhand());
		float ireqQuantity = stringToFloat(reqQuantity);				
		float iNewQuantiy = iQuantity + ireqQuantity;		
		
		//待入库数量
		float istockin = stringToFloat(data.getWaitstockin());		
		float iNewStockIn = istockin - ireqQuantity;
		
		//虚拟库存=当前库存 + 待入库 - 待出库
		float waitstockout = stringToFloat(data.getWaitstockout());//待出库	
		float availabeltopromise = iNewQuantiy + iNewStockIn - waitstockout;
		
		data.setQuantityonhand(String.valueOf(iNewQuantiy));
		data.setWaitstockin(String.valueOf(iNewStockIn));
		data.setAvailabeltopromise(String.valueOf(availabeltopromise));
		
		//更新DB
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"PurchaseStockInUpdate",userInfo);
		copyProperties(data,commData);
		
		dao.Store(data);
		
	}
	

	//
	@SuppressWarnings("unchecked")
	private void updateInspectionProcess(
			String arrivalId,
			String materialId) throws Exception{
	
		B_InspectionProcessData data = new B_InspectionProcessData();
		B_InspectionProcessDao dao = new B_InspectionProcessDao();
		
		String where = "arrivalId ='"+ arrivalId + 
				"' AND materialId ='"+ materialId + 
				"' AND deleteFlag='0' ";
		
		List<B_InspectionProcessData> list = 
				(List<B_InspectionProcessData>)dao.Find(where);
		
		if(list ==null || list.size() == 0){
			return ;
		}

		data = list.get(0);
		
		data.setResult(Constants.ARRIVERECORD_4);//入库完毕
		
		//更新DB
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"PurchaseStockInUpdate",userInfo);
		copyProperties(data,commData);
		
		dao.Store(data);
		
	}
	
	//更新合同的累计入库数量
	@SuppressWarnings("unchecked")
	private void updateContractStorage(
			String contractId,
			String materialId,
			String quantity) throws Exception{
	
		B_PurchaseOrderDetailData data = new B_PurchaseOrderDetailData();
		B_PurchaseOrderDetailDao dao = new B_PurchaseOrderDetailDao();
		
		String where = "contractId ='"+ contractId + 
				"' AND materialId ='"+ materialId + 
				"' AND deleteFlag='0' ";
		
		List<B_PurchaseOrderDetailData> list = 
				(List<B_PurchaseOrderDetailData>)dao.Find(where);
		
		if(list ==null || list.size() == 0)
			return ;		

		String SQuantyDB = list.get(0).getContractstorage();//累计入库		
		float iQuantity = stringToFloat(SQuantyDB);
		float inewQuantity = stringToFloat(quantity);		
		float iNew = iQuantity + inewQuantity;
		
		//更新DB
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"PurchaseStockInUpdate",userInfo);
		copyProperties(data,commData);
		data.setContractstorage(String.valueOf(iNew));
		
		dao.Store(data);
		
	}
	
	private void insertPurchaseStockIn(
			B_PurchaseStockInData stock) throws Exception {

		//删除旧数据,防止数据重复
		String receiptId = stock.getReceiptid();
		String where = " receiptId = '"+receiptId+"'";
		try {
			dao.RemoveByWhere(where);
		} catch (Exception e) {
			// nothing
		}
		
		//插入新数据
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"PurchaseStockInInsert",userInfo);
		copyProperties(stock,commData);
		stock.setKeepuser(userInfo.getUserId());//默认为登陆者	

		String guid = BaseDAO.getGuId();
		stock.setRecordid(guid);
		
		dao.Create(stock);
	}
	
	
	private HashMap<String, Object> getArrivaRecord(String receiptId){

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		try {
			dataModel.setQueryName("getPurchaseStockInById");
			userDefinedSearchCase.put("receiptId", receiptId);
			baseQuery = new BaseQuery(request, dataModel);
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
			baseQuery.getYsFullData();

			modelMap.put("data", dataModel.getYsViewData());
			model.addAttribute("head",dataModel.getYsViewData().get(0));
			model.addAttribute("material",dataModel.getYsViewData());		
			
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}

		return modelMap;
	}
	
		
	public void doDelete(String recordId) throws Exception{
		
		B_ArrivalData data = new B_ArrivalData();	
															
		try {
			
			ts = new BaseTransaction();										
			ts.begin();									
			
			String removeData[] = recordId.split(",");									
			for (String key:removeData) {									
												
				data.setRecordid(key);							
				dao.Remove(data);	
				
			}
			
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
		}
	}
	
	
	
	
	public void getStorageRecordId(String contractId) {

		try {
			dataModel.setQueryName("getMAXStorageRecordId");
			baseQuery = new BaseQuery(request, dataModel);
			userDefinedSearchCase.put("contractId", contractId);
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);			
			baseQuery.getYsFullData();	
			
			//查询出的流水号已经在最大值上 " 加一 "了
			String code = dataModel.getYsViewData().get(0).get("MaxSubId");		
			
			String inspectionId = 
					BusinessService.getStorageRecordId(contractId,code,false);
			
			B_PurchaseStockInData data = new B_PurchaseStockInData();
			data.setReceiptid(inspectionId);
			data.setSubid(code);
			reqModel.setStock(data);
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			reqModel.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
	}
	
	public void getContractDetail(String arrivalId) throws Exception {
		
		dataModel.setQueryName("getMaterialCheckInList");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("arrivalId", arrivalId);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		if(dataModel.getRecordCount() >0){
			model.addAttribute("contract",dataModel.getYsViewData().get(0));
			model.addAttribute("material",dataModel.getYsViewData());			
		}		
	}	
	
	

}
