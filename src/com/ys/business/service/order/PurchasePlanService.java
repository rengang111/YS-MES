package com.ys.business.service.order;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import com.ys.business.action.model.order.PurchasePlanModel;
import com.ys.business.db.dao.B_ArrivalDao;
import com.ys.business.db.dao.B_MaterialDao;
import com.ys.business.db.dao.B_OrderDetailDao;
import com.ys.business.db.dao.B_PurchasePlanDao;
import com.ys.business.db.dao.B_PurchasePlanDetailDao;
import com.ys.business.db.dao.B_RawRequirementDao;
import com.ys.business.db.dao.B_StockOutCorrectionDao;
import com.ys.business.db.dao.B_PriceSupplierDao;
import com.ys.business.db.dao.B_PurchaseOrderDao;
import com.ys.business.db.dao.B_PurchaseOrderDetailDao;
import com.ys.business.db.data.B_ArrivalData;
import com.ys.business.db.data.B_MaterialData;
import com.ys.business.db.data.B_OrderDetailData;
import com.ys.business.db.data.B_PurchasePlanData;
import com.ys.business.db.data.B_PurchasePlanDetailData;
import com.ys.business.db.data.B_RawRequirementData;
import com.ys.business.db.data.B_StockOutCorrectionData;
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

	public HashMap<String, Object> getOrderList(String data) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		dataModel = new BaseModel();

		data = URLDecoder.decode(data, "UTF-8");
		String sqlFlag=request.getParameter("sqlFlag");
		String materialType=request.getParameter("materialType");
		
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

		baseQuery = new BaseQuery(request, dataModel);
		dataModel.setQueryFileName("/business/order/purchasequerydefine");
		if(("new").equals(sqlFlag)){
			dataModel.setQueryName("getOrderListForPurchasePlan2");//新合同			
		}else if(("contract").equals(sqlFlag)){
			dataModel.setQueryName("getOrderListForPurchasePlan3");//合同全部完成
		}else{
			dataModel.setQueryName("getOrderListForPurchasePlan");//合同完成部分
		}
		String tmpWhere = "";
		if( ("yszz").equals(materialType) ){
			tmpWhere = " b.supplierId='0574YZ00' ";
		}else if(("order").equals(materialType)){
			tmpWhere = " b.purchaseType='010' AND b.supplierId<>'0574YZ00' ";
		}else if(("package").equals(materialType)){
			tmpWhere = " left(b.materialId,1)='G' ";
		}
		
		userDefinedSearchCase = new HashMap<String, String>();
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
		if(notEmpty(key1) || notEmpty(key2))
			userDefinedSearchCase.put("status", "");//有查询内容时,不再添加其他条件
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		String sql = baseQuery.getSql();
		sql = getSortKeyFormWeb(data,baseQuery);	
		sql = sql.replace("#", tmpWhere);
		
		baseQuery.getYsQueryData(sql,tmpWhere,iStart, iEnd);	 
		
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
	
	public HashMap<String, Object> getBomApproveList(String data) throws Exception {
		
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

	public void getOrderPeijianByPIId(String PIId,String orderType) 
			throws Exception {
			
		dataModel.setQueryFileName("/business/order/bomquerydefine");
		dataModel.setQueryName("getOrderPeijianByPIId");		
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("PIId", PIId);
		userDefinedSearchCase.put("orderType", orderType);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);		
		modelMap = baseQuery.getYsFullData();

		if(dataModel.getRecordCount() > 0){
			model.addAttribute("baseBom", dataModel.getYsViewData().get(0));
			model.addAttribute("bomDetail", dataModel.getYsViewData());
		}		
	}

	public void getBomeDetailAndContract(
			String bomId,String YSId,String productId) 
			throws Exception {
			
		dataModel.setQueryFileName("/business/order/purchasequerydefine");
		dataModel.setQueryName("getBomDetailAndContract");		
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("bomId", bomId);
		userDefinedSearchCase.put("productId", productId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		String sql = baseQuery.getSql();
		sql = sql.replace("#", YSId);		
		modelMap = baseQuery.getYsFullData(sql);

		if(dataModel.getRecordCount() > 0){
			model.addAttribute("baseBom", dataModel.getYsViewData().get(0));
			model.addAttribute("bomDetail", dataModel.getYsViewData());
		}		
		
	}
	public HashMap<String, Object> getOrderDetailByYSId(
			String YSId) throws Exception{


		return getOrderDetailByYSId(YSId,"");
		
	}
	
	public HashMap<String, Object> getOrderDetailByYSId(
			String YSId,String peiYsid) throws Exception{

		HashMap<String, Object> HashMap = new HashMap<String, Object>();

		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("getOrderList");
		
		baseQuery = new BaseQuery(request, dataModel);

		userDefinedSearchCase.put("YSId", YSId);
		userDefinedSearchCase.put("peiYsid", peiYsid);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		baseQuery.getYsFullData();
		
		model.addAttribute("order", dataModel.getYsViewData().get(0));	
		HashMap.put("data", dataModel.getYsViewData());
		return HashMap;
		
	}
	
	public HashMap<String, Object> getOrderDetailById(
			String PIId,String orderType) throws Exception{

		HashMap<String, Object> HashMap = new HashMap<String, Object>();
		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("getOrderList");		
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("PIId", PIId);
		userDefinedSearchCase.put("orderType", orderType);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);		
		baseQuery.getYsFullData();
		
		model.addAttribute("order", dataModel.getYsViewData().get(0));	
		HashMap.put("data", dataModel.getYsViewData());
		
		return HashMap;
		
	}
	
	public String getPurchaseId(String YSId) throws Exception {

		String timestemp = CalendarUtil.getDateyymmdd();
		String purchaseid= YSId+"-"+ timestemp;

		return purchaseid;
		
	}
	

	/*
	 * update处理
	 */
	/*
	private String update(String where){
		
		ts = new BaseTransaction();
		String  YSId="";
		
		try {
			ts.begin();

			System.out.println("采购方案更新处理：①开始,找出页面被删除的物料");
			B_PurchasePlanData reqPlan = reqModel.getPurchasePlan();	

			//采购方案****************************************************
			YSId = reqPlan.getYsid();		
			String purchaseId = updatePurchasePlan(reqPlan);			
						
			//更新前数据取得
			List<B_PurchasePlanDetailData> DBList = getPurchasePlanDetail(where);	
			
			//新数据:采购方案处理
			List<B_PurchasePlanDetailData> webList = reqModel.getPlanDetailList();		
			
			List<B_PurchasePlanDetailData> deleteList = new ArrayList<B_PurchasePlanDetailData>();			
			//找出页面被删除的数据
			for(B_PurchasePlanDetailData db:DBList){
				String reqMate = db.getMaterialid();
				String reqSubNo = db.getSubbomno();
				String reqSupp = db.getSupplierid();
				String reqPurchaseQty = db.getPurchasequantity();//采购量
				if(isNullOrEmpty(reqMate))
					continue;
				
				boolean exflg = true;
				for(B_PurchasePlanDetailData web:webList){
					
					String dbMate = web.getMaterialid();
					String dbSubNo = web.getSubbomno();  
					String dbSupp = web.getSupplierid();
					String dbPurch = web.getPurchasequantity();
					
					if( reqMate.equals(dbMate)  	&& 
						reqSubNo.equals(dbSubNo) 	&&
						reqSupp.equals(dbSupp) 		&&
						reqPurchaseQty.equals(dbPurch)
					){
						
						web.setContractflag(db.getContractflag());//
						web.setPurchasetype(db.getPurchasetype());
						
						exflg = false;
						break;
						
					}//物料是否存在判定
				}
				
				if(exflg){
					B_PurchasePlanDetailData d = new B_PurchasePlanDetailData();
					copyProperties(d,db);					
					deleteList.add(d);
				}
				
			}//找出页面被删除的数据
			
			//旧数据:二级BOM(原材料)的待出库"减少"处理
			System.out.println("采购方案更新处理：②删除原材料的物料需求表（更新前）");
			ArrayList<HashMap<String, String>> list2 = getRawRequirement(YSId);	
			
			for(HashMap<String, String> map2:list2){
				
				String rawmater2 = map2.get("materialId");//二级物料名称(原材料)
				
				//更新虚拟库存
				float purchase = 0;//采购量
				String unit = DicUtil.getCodeValue("换算单位" + map2.get("unitId"));
				float funit = stringToFloat(unit);
				float totalQuantity = stringToFloat(map2.get("quantity"));//配件的采购量作为原材料的需求量
				float requirement = (-1 * totalQuantity / funit);
				
				updateMaterial("二级BOM(原材料)的待出库减少处理（旧数据）",rawmater2,purchase,requirement);						
			}
			
			//旧数据:采购方案,的待出库"减少"处理
			System.out.println("采购方案更新处理：③采购方案（旧数据删除）");	
			for(B_PurchasePlanDetailData old:DBList){

				String oldmaterilid = old.getMaterialid();				

				//旧数据物料的待出库"减少"处理
				//String purchase = String.valueOf(-1 * stringToFloat(old.getPurchasequantity()));
				float requirement = -1 * stringToFloat(old.getManufacturequantity());
				updateMaterial("采购方案（旧数据删除）",oldmaterilid,0,requirement);
				
				//old.setPurchasequantity(purchase);
				//old.setManufacturequantity(String.valueOf(requirement));
				
				//旧数据的删除处理
				deletePurchasePlanDetail(old);				
				
			}//旧数据:采购方案,的待出库"减少"处理
						
			//新数据:采购方案处理
			System.out.println("采购方案更新处理：④采购方案（新数据追加）");					
			for(B_PurchasePlanDetailData detail:webList){
				String materilid = detail.getMaterialid();
				float purchase = stringToFloat(detail.getPurchasequantity());
				if(materilid == null || ("").equals(materilid))
					continue;		
				
				if(purchase == 0)
					detail.setContractflag(0);//不采购的不做合同				
				if(materilid.substring(0,1).equals(Constants.PURCHASETYPE_H))
					detail.setContractflag(0);//人工成本不做合同
				
				detail.setPurchaseid(purchaseId);
				detail.setYsid(YSId);
				insertPurchasePlanDetail(detail);			

				//更新虚拟库存
				Float requirement = stringToFloat(detail.getManufacturequantity());

				updateMaterial("采购方案（新数据追加）",materilid,0,requirement);
				
			}//新数据:采购方案处理
			
			
			//采购合同****************************************************
			System.out.println("采购方案更新处理：⑤需要删除的采购合同");			
			//旧数据:
			for(B_PurchasePlanDetailData dt:deleteList){
				
				String materialId = dt.getMaterialid();
				float purchaseQty = stringToFloat(dt.getPurchasequantity());
				
				String strwhere = " YSId = '" + YSId +"' AND materialId = '" + materialId  +"' AND deleteflag = '0'";
				List<B_PurchaseOrderDetailData> detail = 
						getPurchaseOrderDetailFromDB(strwhere);
				
				if(detail == null || detail.size() == 0)
					continue;

				B_PurchaseOrderDetailData contract = detail.get(0);
				String contractId = contract.getContractid();
				float contractQty = stringToFloat(contract.getQuantity());
				float contractPrice = stringToFloat(contract.getPrice());
				float quantity = contractQty - purchaseQty;
				
				String arrivalFlag = checkContractEnforcement(contractId,materialId);
				if(("保留").equals(arrivalFlag)){
					continue;
				}
				if(quantity <= 0){//

					String where2 = " YSId = '" + YSId +"' AND contractId = '" + contractId  +"' AND deleteflag = '0'";
					List<B_PurchaseOrderDetailData> detailList = 
							getPurchaseOrderDetailFromDB(where2);
					
					if(detailList.size() > 1){
						//一份合同有多个物料,仅删除合同明细
						deletePurchaseOrderDetail(contract);
						
					}else{
						//一份合同只有一个物料,删除明细和头表
						List<B_PurchaseOrderData> contractDBList =  
								getPurchaseOrderFromDB(YSId,contractId);
						
							deletePurchaseOrder(contractDBList.get(0));
							deletePurchaseOrderDetail(contract);
					}
				}else{//套件产品,同一个物料重复出现,只删除其中一个的话,更新剩下的数量
					contract.setQuantity(String.valueOf(quantity));
					contract.setTotalprice(String.valueOf(quantity * contractPrice));
					
					updatePurchaseOrderDetail(contract);
				}
				
				//退还物料库存
				
				updateMaterial(
						materialId, 
						((-1) * purchaseQty),
						0);
				//8.16 页面删除的物料不需要再次退还待出量，
				//因为，该物料已经在DB旧数据处理时退还过了。
			}

			//二级BOM(原材料)物料需求表
			System.out.println("采购方案更新处理：⑥二级BOM(原材料)物料需求表更新");
			ArrayList<HashMap<String, String>> list3 = getRawMaterialGroupList(YSId);	
			
			for(HashMap<String, String> map2:list3){
					
				//更新虚拟库存
				String rawmater2 = map2.get("rawMaterialId");//二级物料名称(原材料)
				float purchase = 0;//采购量
				String unit = DicUtil.getCodeValue("换算单位" + map2.get("unit"));
				float funit = stringToFloat(unit);
				float totalQuantity = stringToFloat(map2.get("purchaseQuantity"));//采购量
				float requirement = ( totalQuantity / funit);
				
				updateMaterial("二级BOM(原材料)物料需求表更新",rawmater2,purchase,requirement);						
			}	
			
			ts.commit();	
			System.out.println("采购方案更新处理：⑦正常结束");
			
		}
		catch(Exception e) {
			try {
				ts.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			System.out.println("采购方案更新处理：异常结束。");
			e.printStackTrace();
		}
		
		return YSId;
	}
	*/
	
	/*
	 * 配件订单的采购方案
	 */
	private String updatePeiJian(){
		
		ts = new BaseTransaction();
		//String  YSId="";
		String peiYsid = request.getParameter("peiYsid");
		String YSId = request.getParameter("YSId");
		if(isNullOrEmpty(peiYsid))
			peiYsid = YSId.split("-")[0]+"P";//重新设值配件订单的耀升编号
		
		try {
			ts.begin();

			B_PurchasePlanData reqPlan = reqModel.getPurchasePlan();
			reqPlan.setYsid(peiYsid);

			//采购方案****************************************************	
			String purchaseId = updatePurchasePlan(reqPlan);
			int line=0;
				System.out.println("****:"+line++);
				
			//更新前数据取得
			String where = " YSId = '" +peiYsid + "' ";
			List<B_PurchasePlanDetailData> DBList = getPurchasePlanDetail(where);	
			System.out.println("****:"+line++);
			
			//新数据:采购方案处理
			List<B_PurchasePlanDetailData> webList = reqModel.getPlanDetailList();
			System.out.println("****:"+line++);		
			
			List<B_PurchasePlanDetailData> deleteList = new ArrayList<B_PurchasePlanDetailData>();			
			System.out.println("****:"+line++);
			//找出页面被删除的数据
			for(B_PurchasePlanDetailData db:DBList){
				String reqMate = db.getMaterialid();
				//String reqSubNo = db.getSubbomno();
				String reqSupp = db.getSupplierid();
				if(isNullOrEmpty(reqMate))
					continue;
				
				boolean exflg = true;
				for(B_PurchasePlanDetailData web:webList){
					
					String dbMate = web.getMaterialid();
					//String dbSubNo = web.getSubbomno();  
					String dbSupp = web.getSupplierid();
					
					if( reqMate.equals(dbMate)  && 
						//reqSubNo.equals(dbSubNo) &&
						reqSupp.equals(dbSupp) ){
						
						web.setContractflag(db.getContractflag());//
						web.setPurchasetype(db.getPurchasetype());
						
						exflg = false;
						break;
						
					}//物料是否存在判定
				}
				
				if(exflg){
					B_PurchasePlanDetailData d = new B_PurchasePlanDetailData();
					copyProperties(d,db);					
					deleteList.add(d);
				}
				
			}//找出页面被删除的数据

			System.out.println("****:"+line++);
			//旧数据:二级BOM(原材料)的待出库"减少"处理
			ArrayList<HashMap<String, String>> list2 = getRawMaterialGroupList(peiYsid);	

			System.out.println("****:"+line++);
			for(HashMap<String, String> map2:list2){
				
				String rawmater2 = map2.get("rawMaterialId");//二级物料名称(原材料)
				
				//更新虚拟库存
				float purchase = 0;//采购量
				String unit = DicUtil.getCodeValue("换算单位" + map2.get("unit"));
				float funit = stringToFloat(unit);
				float totalQuantity = stringToFloat(map2.get("purchaseQuantity"));//采购量
				float requirement = (-1 * totalQuantity / funit);
				
				updateMaterial("配件订单,二级BOM(原材料)的待出库减少处理",rawmater2,purchase,requirement);						
			}

			System.out.println("****:"+line++);
			//旧数据:采购方案,的待出库"减少"处理
			for(B_PurchasePlanDetailData old:DBList){

				String oldmaterilid = old.getMaterialid();				

				//旧数据物料的待出库"减少"处理
				float requirement = -1 * stringToFloat(old.getManufacturequantity());
				updateMaterial("配件订单,采购方案,的待出库减少处理",oldmaterilid,0,requirement);				
				
				//旧数据的删除处理
				deletePurchasePlanDetail(old);				
				
			}//旧数据:采购方案,的待出库"减少"处理

			System.out.println("****:"+line++);			
			//新数据:采购方案处理			
			for(B_PurchasePlanDetailData detail:webList){
				String materilid = detail.getMaterialid();
				float purchase = stringToFloat(detail.getPurchasequantity());
				if(materilid == null || ("").equals(materilid))
					continue;		
				
				if(purchase == 0)
					detail.setContractflag(0);//不采购的不做合同				
				if(materilid.substring(0,1).equals(Constants.PURCHASETYPE_H))
					detail.setContractflag(0);//人工成本不做合同
				
				detail.setPurchaseid(purchaseId);
				detail.setYsid(peiYsid);
				insertPurchasePlanDetail(detail);			

				//更新虚拟库存
				Float requirement = stringToFloat(detail.getManufacturequantity());

				updateMaterial("配件订单,新数据:采购方案处理",materilid,0,requirement);
				
			}//新数据:采购方案处理
			

			System.out.println("****:"+line++);
			//采购合同****************************************************			
			//旧数据:
			for(B_PurchasePlanDetailData dt:deleteList){
				
				String materialId = dt.getMaterialid();
				float purchaseQty = stringToFloat(dt.getPurchasequantity());
				
				String strwhere = " YSId = '" + peiYsid +"' AND materialId = '" + materialId  +"' AND deleteflag = '0'";
				List<B_PurchaseOrderDetailData> detail = 
						getPurchaseOrderDetailFromDB(strwhere);
				
				if(detail == null || detail.size() == 0)
					continue;

				B_PurchaseOrderDetailData contract = detail.get(0);
				String contractId = contract.getContractid();
				float contractQty = stringToFloat(contract.getQuantity());
				float contractPrice = stringToFloat(contract.getPrice());
				float quantity = contractQty - purchaseQty;
				
				String arrivalFlag = checkContractEnforcement(contractId,materialId);
				if(("保留").equals(arrivalFlag)){
					continue;
				}
				if(quantity <= 0){//

					String where2 = " YSId = '" + peiYsid +"' AND contractId = '" + contractId  +"' AND deleteflag = '0'";
					List<B_PurchaseOrderDetailData> detailList = 
							getPurchaseOrderDetailFromDB(where2);
					
					if(detailList.size() > 1){
						//一份合同有多个物料,仅删除合同明细
						deletePurchaseOrderDetail(contract);
						
					}else{
						//一份合同只有一个物料,删除明细和头表
						List<B_PurchaseOrderData> contractDBList =  
								getPurchaseOrderFromDB(peiYsid,contractId);
						
							deletePurchaseOrder(contractDBList.get(0));
							deletePurchaseOrderDetail(contract);
					}
				}else{//套件产品,同一个物料重复出现,只删除其中一个的话,更新剩下的数量
					contract.setQuantity(String.valueOf(quantity));
					contract.setTotalprice(String.valueOf(quantity * contractPrice));
					
					updatePurchaseOrderDetail(contract);
				}
				
				//退换物料的库存
				//if(supplierFlag.equals("1")){//只处理被删除的物料
						updateMaterial(
								"配件订单，被删除的物料库存处理",
								materialId, 
								((-1) * purchaseQty),
								0);				
				//}
			}

			System.out.println("****:"+line++);
			//二级BOM(原材料)物料需求表
			ArrayList<HashMap<String, String>> list3 = getRawMaterialGroupList(peiYsid);	

			System.out.println("****:"+line++);
			for(HashMap<String, String> map2:list3){
					
				//更新虚拟库存
				String rawmater2 = map2.get("rawMaterialId");//二级物料名称(原材料)
				float purchase = 0;//采购量
				String unit = DicUtil.getCodeValue("换算单位" + map2.get("unit"));
				float funit = stringToFloat(unit);
				float totalQuantity = stringToFloat(map2.get("purchaseQuantity"));//采购量
				float requirement = ( totalQuantity / funit);
				
				updateMaterial("配件订单，二级BOM(原材料)物料需求表",rawmater2,purchase,requirement);						
			}	

			System.out.println("****:"+line++);
			ts.commit();		
			
		}
		catch(Exception e) {
			try {
				ts.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
		return peiYsid;
	}
		
		
	/*
	 * insert处理
	 */
	private void insertRoutineContract(){
		
		ts = new BaseTransaction();
		//String  purchaseId="";
		
		try {
			ts.begin();

			B_PurchasePlanData reqPlan = reqModel.getPurchasePlan();
			String YSId = reqPlan.getYsid();
			if(notEmpty(YSId))
				YSId = YSId.toUpperCase();//关联耀升编号存在的情况
			//String materialId = reqPlan.getMaterialid();
							

			//更新虚拟库存****************************************************			
			List<B_PurchasePlanDetailData> reqPlanList = reqModel.getPlanDetailList();
			
			for(B_PurchasePlanDetailData detail:reqPlanList){
				String materilid = detail.getMaterialid();
				if(materilid == null || ("").equals(materilid)){
					continue;		
				}
				
				//更新虚拟库存
				float purchase = stringToFloat(detail.getPurchasequantity());//采购量
				float requirement = 0;//需求量:真实的需求量在订单采购时已经计算过
				updateMaterial("insertRoutineContract",materilid,purchase,requirement);
			}
			
			//采购合同****************************************************
			
			//供应商集计
			//以采购方案里的供应商为单位集计					
			List<B_PurchasePlanDetailData> contract = new ArrayList<B_PurchasePlanDetailData>();	
			
			for(B_PurchasePlanDetailData detail:reqPlanList){
				
				String supplierId1 = detail.getSupplierid();
				String totalprice   = detail.getTotalprice();
				
				if(supplierId1 == null || supplierId1.equals("")){
					continue;
				}
				float totalf1 = stringToFloat(totalprice); 
				if(totalf1 == 0){//采购数量为零的供应商不计入合同
					continue;
				}
				boolean flag = true;
				for(B_PurchasePlanDetailData dt:contract){
					String supplierId2 = dt.getSupplierid();
					if(supplierId1.equals(supplierId2)){
						String totalprice2 = dt.getTotalprice();
						float totalf12 = stringToFloat(totalprice2); 
						dt.setTotalprice(String.valueOf(totalf1 + totalf12));
						flag = false;
						break;
					}
				}
				
				if(flag){
					contract.add(detail);
				}
			}
			
			for(B_PurchasePlanDetailData detail:contract){
				//取得供应商的合同流水号
				//父编号:年份+供应商简称
				String supplierId = detail.getSupplierid();
				String shortName  = detail.getSuppliershortname();
				String total   = detail.getTotalprice();
				String purchaseType = detail.getPurchasetype();
				//String materialId = detail.getMaterialid();
				
				String type = getContractType(purchaseType);
				
				String typeParentId = BusinessService.getshortYearcode()+type;				
				String supplierParentId = BusinessService.getshortYearcode() + shortName;				
				String typeSubId = getContractTypeCode(typeParentId);
				String suplierSubId = getContractSupplierCode(supplierParentId);

				//3位流水号格式化	
				//采购合同编号:16D081-WL002
				String contractId = BusinessService.getContractCode(YSId, suplierSubId,shortName);
				
				//新增采购合同*************
				B_PurchaseOrderData data = new B_PurchaseOrderData();
				
				data.setYsid(YSId);
				//data.setMaterialid(materialId);
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
				for(B_PurchasePlanDetailData dt:reqPlanList){					
					
					String supplierId2 = dt.getSupplierid();
					if(supplierId.equals(supplierId2)){
															
						B_PurchaseOrderDetailData d = new B_PurchaseOrderDetailData();				
						d.setYsid(YSId);
						d.setContractid(contractId);
						d.setMaterialid(dt.getMaterialid());
						d.setQuantity(dt.getPurchasequantity());
						d.setPrice(dt.getPrice());					
						d.setTotalprice(dt.getTotalprice());
						d.setVersion(1);//默认为1
						
						insertPurchaseOrderDetail(d);
											
						continue;	
					}
				}				
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
	}
	
	/*
	 * delete处理
	 */
	private void deletePurchaseOrder(B_PurchaseOrderData db) throws Exception{
		
		commData = commFiledEdit(Constants.ACCESSTYPE_DEL,
				"purchaseOrderdelete",userInfo);			
		copyProperties(db,commData);

		new B_PurchaseOrderDao().Store(db);

	}

	/*
	 * delete处理
	 */
	private void deletePurchaseOrderDetail(B_PurchaseOrderDetailData db) throws Exception{

		commData = commFiledEdit(Constants.ACCESSTYPE_DEL,
				"purchaseOrderDetaildelete",userInfo);			
		copyProperties(db,commData);
		
		new B_PurchaseOrderDetailDao().Store(db);

	}
	
	/*
	 * delete处理
	 */
	public void deletePurchasePlan(B_PurchasePlanData db) throws Exception{
		
		//update处理	
		commData = commFiledEdit(Constants.ACCESSTYPE_DEL,
				"purchasePlanDelete",userInfo);			
		copyProperties(db,commData);
		db.setVersion(db.getVersion()+1);

		planDao.Store(db);

	}
	
	/*
	 * delete处理
	 */
	public void deletePurchasePlan() throws Exception{
		
		//
		ts = new BaseTransaction();
		try{

			ts.begin();
			
			B_PurchasePlanData reqPlan = reqModel.getPurchasePlan();	

			//check采购方案是否可以删除
			
			//采购方案
			String YSId = reqPlan.getYsid();		
			deletePurchasePlan(reqPlan);			
						
			//更新前数据取得
			String where = " YSId='" + YSId + "'";
			List<B_PurchasePlanDetailData> DBList = getPurchasePlanDetail(where);	
			
			//旧数据:二级BOM(原材料)的待出库"减少"处理
			ArrayList<HashMap<String, String>> list2 = getRawMaterialGroupList(YSId);	
			
			for(HashMap<String, String> map2:list2){
				
				String rawmater2 = map2.get("rawMaterialId");//二级物料名称(原材料)
				
				//更新虚拟库存
				float purchase = 0;//采购量
				String unit = DicUtil.getCodeValue("换算单位" + map2.get("unit"));
				float funit = stringToFloat(unit);
				float totalQuantity = stringToFloat(map2.get("purchaseQuantity"));//采购量
				float requirement = (-1 * totalQuantity / funit);
				
				updateMaterial("采购方案删除处理",rawmater2,purchase,requirement);						
			}
			
			//旧数据:采购方案,的待出库"减少"处理
			for(B_PurchasePlanDetailData old:DBList){

				String oldmaterilid = old.getMaterialid();				

				//旧数据物料的待出库"减少"处理
				float requirement = -1 * stringToFloat(old.getManufacturequantity());
				updateMaterial("采购方案删除处理",oldmaterilid,0,requirement);
				
				//旧数据的删除处理
				deletePurchasePlanDetail(old);				
				
			}
			
			ts.commit();
			
		}catch(Exception e){
			ts.rollback();
			e.printStackTrace();
		}	

	}
	

	/*
	 * delete处理
	 */
	private void deletePurchasePlanDetail(B_PurchasePlanDetailData db) throws Exception{
		
		//update处理			
		planDetailDao.Remove(db);

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
		
		planDao.Create(data);	

	}	
	
	/*
	 * insert处理
	 */
	@SuppressWarnings("unchecked")
	public void insertPurchasePlanDetail(
			B_PurchasePlanDetailData data) throws Exception{

		String where = " YSId='" + data.getYsid() + 
					"' AND materialId='" + data.getMaterialid() +"' ";
					
		List<B_PurchasePlanDetailData> list = new B_PurchasePlanDetailDao().Find(where);
		
		if(list.size() > 0 ){
			//update
			B_PurchasePlanDetailData db = list.get(0);

			copyProperties(db,data);
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"更新采购方案物料",userInfo);
			copyProperties(db,commData);
			
			planDetailDao.Store(db);	
			
		}else{
			//insert
			commData = commFiledEdit(Constants.ACCESSTYPE_INS,
					"新增采购方案物料",userInfo);
			copyProperties(data,commData);

			guid = BaseDAO.getGuId();
			data.setRecordid(guid);
			
			planDetailDao.Create(data);	
		}
		

	}	
		
	//更新虚拟库存:生成物料需求时增加“待出库”
	@SuppressWarnings("unchecked")
	private void updateMaterial(
			String action,
			String materialId,
			float purchaseIn,
			float requirementOut) throws Exception{
	
		B_MaterialData data = new B_MaterialData();
		B_MaterialDao dao = new B_MaterialDao();
		
		String where = "materialId ='"+ materialId + "' AND deleteFlag='0' ";
		
		List<B_MaterialData> list = 
				(List<B_MaterialData>)dao.Find(where);
		
		if(list ==null || list.size() == 0){
			return ;
		}

		data = list.get(0);
		
		insertStorageHistory(data,action,String.valueOf(requirementOut));//保留更新前的数据
		
		//当前库存数量
		float iOnhand  = stringToFloat(data.getQuantityonhand());//实际库存
		float iWaitOut = stringToFloat(data.getWaitstockout());//待出库
		float iWaitIn  = stringToFloat(data.getWaitstockin());//待入库
		
		iWaitOut = iWaitOut + requirementOut;
		iWaitIn = iWaitIn + purchaseIn;
		
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
	
	/* 11/8 临时备份
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
		*/
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
	
	/*
	 * 更新
	 */
	@SuppressWarnings("unchecked")
	private String updatePurchasePlan(B_PurchasePlanData reqPlan) throws Exception{
		
		String purchaseId = "";
		String YSId = reqPlan.getYsid();
		B_PurchasePlanData dbPlan = null;		
					
		String where = " YSId = '" + YSId + "' AND deleteflag = '0'";
		List<B_PurchasePlanData> list = new B_PurchasePlanDao().Find(where);			
	
		if(list.size() > 0){	
			//update处理	
			dbPlan = list.get(0);	
			copyProperties(dbPlan,reqPlan);
			
			purchaseId = dbPlan.getPurchaseid();
			if(isNullOrEmpty(purchaseId))
				purchaseId = getPurchaseId(YSId);
			dbPlan.setPurchaseid(purchaseId);
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"purchasePlanUpdate",userInfo);			
			copyProperties(dbPlan,commData);

			planDao.Store(dbPlan);

		}else{
			//insert处理
			purchaseId = getPurchaseId(YSId);
			reqPlan.setPurchaseid(purchaseId);
			insertPurchasePlan(reqPlan);
		}		
		return purchaseId;
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
	
	public String createBomPlanFromOrder() throws Exception {

		String rtnFlag = "新建";
		String YSId = request.getParameter("YSId");
		String materialId = request.getParameter("materialId");
		String bomId = BusinessService.getBaseBomId(materialId)[1];
		
		if(notEmpty(materialId)){
			//半成品的BOM编号规则
			if(("K").equals(materialId.substring(0, 1)))
				bomId = BusinessService.getSemiBaseBomId(materialId)[1];
		}

		getOrderDetailByYSId(YSId);
		//确认采购方案是否存在
		String where = " YSId='" + YSId + "'";
		List<B_PurchasePlanDetailData> detail = getPurchasePlanDetail(where);
		if(detail.size() > 0){
			rtnFlag = "查看";
		}else{			
			getBomDetailView(bomId);	
		}
		
		return rtnFlag;
	}
	
	public void createBomPlan() throws Exception {
		
		String YSId = request.getParameter("YSId");
		String materialId = request.getParameter("materialId");
		
		String bomId = BusinessService.getBaseBomId(materialId)[1];
		if(notEmpty(materialId)){
			//半成品的BOM编号规则
			if(("K").equals(materialId.substring(0, 1)))
				bomId = BusinessService.getSemiBaseBomId(materialId)[1];
			
		}

		getOrderDetailByYSId(YSId);
		
		getBomDetailView(bomId);
		
	}
	

	public void createPeijianPlan() throws Exception {
		
		String PIId = request.getParameter("PIId");
		String orderType = request.getParameter("orderType");

		getOrderDetailById(PIId,orderType);
		
		getOrderPeijianByPIId(PIId,orderType);
	}
	
	public void showPurchasePlan() throws Exception {
		
		String YSId = request.getParameter("YSId");

		getOrderDetailByYSId(YSId);	
		
	}

	public void showPurchasePlanPei() throws Exception {
		
		String YSId = request.getParameter("YSId");
		String peiYsid = request.getParameter("peiYsid");

		getOrderDetailByYSId(YSId,peiYsid);	
		
	}


	public void editPurchasePlan() throws Exception {

		String YSId = request.getParameter("YSId");
		String peiYsid = request.getParameter("peiYsid");
		String orderType = request.getParameter("orderType");
		
		//getOrderDetailByYSId(YSId);
		getOrderDetailByYSId(YSId,peiYsid);
		
		if(("020").equals(orderType))
			YSId = peiYsid;//配件订单
		getPurchaseDetail(YSId);
		
	}

	public void deleteInitPurchasePlan() throws Exception {

		String YSId = request.getParameter("YSId");
		String productId = request.getParameter("productId");
		String bomId = BusinessService.getBaseBomId(productId)[1];
	
		getOrderDetailByYSId(YSId);
			
		getBomeDetailAndContract(bomId,YSId,productId);
	
	}
	
	public Model insertAndView() throws Exception {

		String materialFlag = request.getParameter("materialFlag");
		String YSId  = request.getParameter("YSId");
		StringBuffer where = new StringBuffer();
		where.append(" YSId = '" +YSId + "' AND deleteFlag='0' ");
		switch(materialFlag){
			case "yszz"://重置自制品
				where.append(" AND ");
				where.append(" supplierId ='");
				where.append(Constants.SUPPLIER_YZ);
				where.append("' ");
				break;
			case "package"://重置包装件
				where.append(" AND ");
				where.append(" left(materialId,1) ='");
				where.append(Constants.PURCHASETYPE_G);
				where.append("' ");
				break;
			
		}
		
		updatePurchasePlanByWhere(where.toString());//更新采购方案
		
		getOrderDetailByYSId(YSId);
		
		return model;
		
	}
	
	public Model insertPeiAndView() throws Exception {
		
		String YSId = updatePeiJian();//更新采购方案
		
		getOrderDetailByYSId("",YSId);
		
		return model;
		
	}
	public void purchaseRoutineAdd() throws Exception {

		insertRoutineContract();		
		
	}

	public Model updateAndView() throws Exception {
		String YSId  = request.getParameter("YSId");
		StringBuffer where = new StringBuffer();
		where.append(" YSId = '" +YSId + "' AND deleteFlag='0' ");
		updatePurchasePlanByWhere(where.toString());
		getOrderDetailByYSId(YSId);
		
		return model;
		
	}
	
	public Model updatePeiAndView() throws Exception {
		
		String YSId = updatePeiJian();//更新采购方案
		
		getOrderDetailByYSId("",YSId);
		
		return model;
		
	}
	
	public HashMap<String, Object> updateOrderCost() throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		
		
		
		//更新成本核算
		B_OrderDetailDao dao = new B_OrderDetailDao();
		B_OrderDetailData dt = new B_OrderDetailData();
		B_OrderDetailData reqDt = reqModel.getOrderDetail();
		String recordId = request.getParameter("detailRecordId");
		dt.setRecordid(recordId);
		dt = (B_OrderDetailData) dao.FindByPrimaryKey(dt);
		if(!(dt == null || ("").equals(dt))){
			//update处理		
			copyProperties(dt,reqDt);
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"ExchangerateUpdate",userInfo);			
			copyProperties(dt,commData);
			
			dao.Store(dt);
		}
		
		//更新汇率
		updateExchangeRate(dt.getCurrency(),dt.getExchangerate());
		
		modelMap.put("message", NORMAL);	
		
		return modelMap;
		
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
	
	public void getPurchasePlanByMaterialId() throws Exception {

		String materialId = request.getParameter("materialId");
		dataModel = new BaseModel();		
		dataModel.setQueryFileName("/business/order/purchasequerydefine");
		dataModel.setQueryName("purchasePlanByMaterialId");		
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("materialId", materialId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);		
		baseQuery.getYsFullData();
		
		if(dataModel.getRecordCount() > 0){
			model.addAttribute("material",dataModel.getYsViewData().get(0));
			model.addAttribute("planList",dataModel.getYsViewData());
		}
	}
	
	public void getPurchasePlanForRawByMaterialId() throws Exception {

		String materialId = request.getParameter("materialId");
		dataModel = new BaseModel();		
		dataModel.setQueryFileName("/business/order/purchasequerydefine");
		dataModel.setQueryName("purchasePlanForRawByMaterialId");		
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("materialId", materialId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);	
		String sql = baseQuery.getSql().replace("#", materialId);
		System.out.println("原材料未领料："+sql);
		baseQuery.getYsFullData(sql);
		
		if(dataModel.getRecordCount() > 0){
			model.addAttribute("material",dataModel.getYsViewData().get(0));
			model.addAttribute("planList",dataModel.getYsViewData());
		}
	}
	
	public HashMap<String, Object> getPurchaseMaterialList(String materialRecord) throws Exception {
		HashMap<String, Object> modelMap = new HashMap<String, Object>();

		dataModel.setQueryFileName("/business/material/materialquerydefine");
		dataModel.setQueryName("materialquerydefine_search");		
		baseQuery = new BaseQuery(request, dataModel);			
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);			
		ArrayList<HashMap<String, String>> list = baseQuery.getYsFullData();
		
		ArrayList<HashMap<String, String>> newList = new ArrayList<HashMap<String, String>>();
		String arry = request.getParameter("data");
		//int index = 0;
		for(int i=0;i<list.size();i++){
			String id = list.get(i).get("recordId");
			boolean flag=false;
			for(String key:arry.split(",")){
				if(key.equals(id)){
					newList.add(list.get(i));
					flag = true;
					break;
				}
			}
			if(flag){
				//list.remove(i);
			}
			//index++;
		}
		model.addAttribute("material",newList);
		modelMap.put("material", list);
		
		return modelMap;
	}

	public HashMap<String, Object> getRawMaterialList() throws Exception {

		HashMap<String, Object> HashMap = new HashMap<String, Object>();
		dataModel = new BaseModel();		
		dataModel.setQueryFileName("/business/order/purchasequerydefine");
		dataModel.setQueryName("getRawMaterialList");
		String YSId = request.getParameter("YSId");
		
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("YSId", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);		
		baseQuery.getYsFullData();
		
		if(dataModel.getRecordCount() > 0){
			HashMap.put("data", dataModel.getYsViewData());
		}
		return HashMap;
	}

	public HashMap<String, Object> updateInitPurchasePlan(
			String YSId,String productId) throws Exception {

		HashMap<String, Object> HashMap = new HashMap<String, Object>();
		dataModel = new BaseModel();		
		dataModel.setQueryFileName("/business/order/purchasequerydefine");
		dataModel.setQueryName("updateInitPurchasePlan");
		
		baseQuery = new BaseQuery(request, dataModel);

		//userDefinedSearchCase.put("YSId", YSId);
		userDefinedSearchCase.put("productId", productId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		String sql = baseQuery.getSql();
		sql = sql.replace("#", YSId);
		baseQuery.getYsFullData(sql);
		
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
			String  where) throws Exception {
		
		
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

		dataModel.setQueryName("getContractSupplierCode");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("supplierParentId", parentId);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, 0);			
		String code =dataModel.getYsViewData().get(0).get("MaxSubId");		
		return code;		
	}	

	private void insertPurchaseOrderDetail(B_PurchaseOrderDetailData data) throws Exception{

		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"RoutinePurchaseOrderDetailInsert",userInfo);
		copyProperties(data,commData);		
		guid = BaseDAO.getGuId();
		data.setRecordid(guid);
		
		new B_PurchaseOrderDetailDao().Create(data);	
	}
	
	private void insertPurchaseOrder(B_PurchaseOrderData data) throws Exception{
		B_PurchaseOrderDao dao = new B_PurchaseOrderDao();

		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"PurchaseOrderInsert",userInfo);
		copyProperties(data,commData);		
		guid = BaseDAO.getGuId();
		data.setRecordid(guid);
		data.setStatus(Constants.CONTRACT_STS_1);
		
		dao.Create(data);	
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
	

	private ArrayList<HashMap<String, String>> getRawMaterialList(
			String YSId ) throws Exception {		
		
		dataModel.setQueryName("getRawMaterialList");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("YSId", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		return baseQuery.getYsFullData();
		
	}
	
	private ArrayList<HashMap<String, String>> getRawRequirement(
			String ysid) throws Exception{

		dataModel.setQueryName("getRawRequirementById");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("YSId", ysid);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		return baseQuery.getYsFullData();

	}
	
	

	public ArrayList<HashMap<String, String>> getRawMaterialGroupList(
			String YSId ) throws Exception {		
		
		dataModel.setQueryName("getRawMaterialFromPlan");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("YSId", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		String sql = baseQuery.getSql();
		sql = sql.replace("#", YSId);
		return baseQuery.getYsFullData(sql);
		
	}
	
	
	@SuppressWarnings("unchecked")
	public List<B_PurchaseOrderData> getPurchaseOrderFromDB(
			String YSId,String contractId) throws Exception {
		
		String where = " YSId = '" + YSId +"' AND contractId = '" + contractId  +"' AND deleteflag = '0'";
		return (List<B_PurchaseOrderData>) new B_PurchaseOrderDao().Find(where);
				
	}
	
	@SuppressWarnings("unchecked")
	public List<B_PurchaseOrderDetailData> getPurchaseOrderDetailFromDB(
			String where) throws Exception {

		return (List<B_PurchaseOrderDetailData>) new B_PurchaseOrderDetailDao().Find(where);
				
	}
	
	@SuppressWarnings("unchecked")
	public String checkContractEnforcement(
			String contractId,String materialId) throws Exception{
		String rtnFlag = "保留";

		//1.判断合同是否收货
		String where = " contractId='" + contractId +
				"' AND materialId='" + materialId +
				"' AND deleteFlag='0' ";
		
		List<B_ArrivalData> list = new B_ArrivalDao().Find(where);
		
		if(list == null || list.size() <= 0 )
			return "删除";//未收货
		
		B_ArrivalData arrival = list.get(0);
		//2.判断合同是否报检
		String inspectSts = arrival.getStatus();//报检结果
		if((Constants.ARRIVAL_STS_1).equals(inspectSts))
			return rtnFlag;
		
		String arrvialId = arrival.getArrivalid();

		//3.判断合同质检结果:合格/退货
		HashMap<String, Object> map = 
				getReceiveInspection(arrvialId,materialId);
		
		if (map == null)
			return rtnFlag;//未检验
		
		HashMap<String, String> inspection = (HashMap<String, String>)map.get("inspection");
		String checkResult = inspection.get("checkResult");
		if ((Constants.ARRIVERECORD_3).equals(checkResult))
			return "删除";//退货
		
		return rtnFlag;
	}
	
	//进料报检
	private HashMap<String, Object> getReceiveInspection(
			String arrvialId,String materialId) throws Exception {
		
		HashMap<String, Object> hmap = new HashMap<String, Object>();
		
		this.dataModel.setQueryFileName("/business/order/purchasequerydefine");
		dataModel.setQueryName("receiveInspectionByMaterialId");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("arrvialId", arrvialId);	
		userDefinedSearchCase.put("materialId", materialId);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();
		
		if(dataModel.getRecordCount() > 0 ){
			hmap.put("inspection", dataModel.getYsViewData().get(0));
		}else{
			hmap.put("inspection", null);			
		}
		return hmap;
	}	
	
	/**
	 * 保存领料的修正值:原材料
	 * @return
	 * @throws Exception
	 */
	public void insertStockoutCorrectionRawAndView() throws Exception {
		
		insertStockOutCorrection();//
		
		getPurchasePlanForRawByMaterialId();
		
				
	}

	/**
	 * 保存领料的修正值:采购件
	 * @return
	 * @throws Exception
	 */
	public void insertStockoutCorrectionAndView() throws Exception {

		insertStockOutCorrection();//
		
		getPurchasePlanByMaterialId();
		
				
	}
	private void insertStockOutCorrection(){
		ts = new BaseTransaction();
		
		try {
			ts.begin();

			String materialId = request.getParameter("materialId");
			List<B_StockOutCorrectionData> reqList = reqModel.getCorrectionList();	

			float quantityCount = 0;
			for(B_StockOutCorrectionData stock:reqList){
				float quantity = stringToFloat(stock.getQuantity());
				float lastQty = stringToFloat(stock.getLastquantity());
				
				if(quantity == lastQty)
					continue;
				
				insertStockOutCorrection(stock);

				quantityCount = quantityCount + quantity - lastQty;
				
			}
			//更新虚拟库存
			if(quantityCount > 0)
				updateMaterial("库存修改处理",
						materialId,0,(-1) * quantityCount);//减少"待出
		
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
		
	}
	

	private void insertStockOutCorrection(
			B_StockOutCorrectionData data) throws Exception{

		String where = " YSId = '" + data.getYsid() + "' " +
				" materialId = '" + data.getMaterialid() + "' ";
		try {
			new B_StockOutCorrectionDao().RemoveByWhere(where);	
		} catch (Exception e1) {
			//
		}	

		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"StockOutCorrectionInsert",userInfo);
		copyProperties(data,commData);		
		guid = BaseDAO.getGuId();
		data.setRecordid(guid);
		
		new B_StockOutCorrectionDao().Create(data);	
		
	}
	
	/**
	 * update处理
	 */
	private String updatePurchasePlanByWhere(String where){
		
		ts = new BaseTransaction();
		String  YSId="";
		
		try {
			ts.begin();

			B_PurchasePlanData reqPlan = reqModel.getPurchasePlan();	

			YSId = reqPlan.getYsid();		
			String purchaseId = updatePurchasePlan(reqPlan);			
						
			//更新前DB数据取得
			List<B_PurchasePlanDetailData> DBList = getPurchasePlanDetail(where);	
			
			//新数据:采购方案处理
			List<B_PurchasePlanDetailData> webList = reqModel.getPlanDetailList();		

			System.out.println("采购方案更新处理：① 开始,找出页面被删除的物料");
			List<B_PurchasePlanDetailData> deleteList = FindDeleteMaterial(DBList,webList);
			
			//旧数据:二级BOM(原材料)的待出库"减少"处理
			System.out.println("采购方案更新处理：② 删除旧的原材料物料需求表（更新前）");
			deleteOldRawRequirment(YSId);
			
			//旧数据:采购方案,的待出库"减少"处理
			System.out.println("采购方案更新处理：③ 删除旧的采购方案（更新前）");	
			deleteOldPurchasePlan(DBList);
						
			//采购方案
			System.out.println("采购方案更新处理：④ 新增采购方案（更新）");	
			addPurchasePlan(YSId,purchaseId,webList);
			
			//采购合同：针对页面被删除的物料，如果已做成合同 且未执行
			System.out.println("采购方案更新处理：⑤ 删除采购合同");	
			deleteContract(YSId,deleteList);

			//原材料物料需求表
			System.out.println("采购方案更新处理：⑥ 新增原材料物料需求表（更新）");
			addRawRequirement(YSId);
				
			
			ts.commit();	
			
			System.out.println("采购方案更新处理：⑦ 正常结束");
			
		}
		catch(Exception e) {
			try {
				ts.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			System.out.println("采购方案更新处理：异常结束。");

			e.printStackTrace();
		}
		
		return YSId;
	}
	
	private List<B_PurchasePlanDetailData> FindDeleteMaterial(
			List<B_PurchasePlanDetailData> dbList,
			List<B_PurchasePlanDetailData> webList) throws Exception{
		
		List<B_PurchasePlanDetailData> deleteList = new ArrayList<B_PurchasePlanDetailData>();	
		
		
		//找出页面被删除的数据
		for(B_PurchasePlanDetailData db:dbList){
			String reqMate = db.getMaterialid();
			String reqSubNo = db.getSubbomno();
			String reqSupp = db.getSupplierid();
			String reqPurchaseQty = db.getPurchasequantity();//采购量
			if(isNullOrEmpty(reqMate))
				continue;
			
			boolean exflg = true;
			for(B_PurchasePlanDetailData web:webList){
				
				String dbMate = web.getMaterialid();
				String dbSubNo = web.getSubbomno();  
				String dbSupp = web.getSupplierid();
				String dbPurch = web.getPurchasequantity();
				
				if( reqMate.equals(dbMate)  	&& 
					reqSubNo.equals(dbSubNo) 	&&
					reqSupp.equals(dbSupp) 		&&
					reqPurchaseQty.equals(dbPurch)
				){
					
					web.setContractflag(db.getContractflag());//
					web.setPurchasetype(db.getPurchasetype());
					
					exflg = false;
					break;
					
				}//物料是否存在判定
			}
			
			if(exflg){
				B_PurchasePlanDetailData d = new B_PurchasePlanDetailData();
				copyProperties(d,db);					
				deleteList.add(d);
			}
			
		}//找出页面被删除的数据
		
		return deleteList;
		
	}
	
	/**
	 * 删除原材料物料需求表
	 * @param YSId
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void deleteOldRawRequirment(String YSId) throws Exception{
		
		String where = "YSId = '" + YSId +"' AND deleteFlag='0' ";
		
		List<B_RawRequirementData> list = new B_RawRequirementDao().Find(where);
		
		for(B_RawRequirementData db:list){
			//更新DB
			commData = commFiledEdit(Constants.ACCESSTYPE_DEL,
					"删除采购方案（更新前）",userInfo);
			copyProperties(db,commData);
			new B_RawRequirementDao().Store(db);

			String materialId = db.getMaterialid();
			float requirement = stringToFloat(db.getQuantity()) * (-1);//还原到更新前
			float purchase = 0;//采购量
			updateMaterial("二级BOM(原材料)的待出库减少处理（旧数据）",materialId,purchase,requirement);
		}
		
	}
	
	/**
	 * 删除采购方案
	 * @param YSId
	 * @throws Exception
	 */
	private void deleteOldPurchasePlan(List<B_PurchasePlanDetailData> list) throws Exception{
		
		
		for(B_PurchasePlanDetailData db:list){
			//更新DB
			commData = commFiledEdit(Constants.ACCESSTYPE_DEL,
					"删除采购方案（更新前）",userInfo);
			copyProperties(db,commData);
			new B_PurchasePlanDetailDao().Store(db);//旧数据的删除处理

			String materialId = db.getMaterialid();
			float purchase = 0;//采购量
			float requirement = -1 * stringToFloat(db.getManufacturequantity());
			updateMaterial("采购方案（旧数据删除）",materialId,purchase,requirement);
		}
		
	}
	
	/**
	 * 删除采购合同
	 * @param list
	 * @throws Exception
	 */
	private void deleteContract(
			String YSId,
			List<B_PurchasePlanDetailData> deleteList) throws Exception{
		
		for(B_PurchasePlanDetailData dt:deleteList){
			
			String materialId = dt.getMaterialid();
			float purchaseQty = stringToFloat(dt.getPurchasequantity());
			
			String strwhere = " YSId = '" + YSId +"' AND materialId = '" + materialId  +"' AND deleteflag = '0'";
			List<B_PurchaseOrderDetailData> detail = 
					getPurchaseOrderDetailFromDB(strwhere);
			
			if(detail == null || detail.size() == 0)
				continue;

			B_PurchaseOrderDetailData contract = detail.get(0);
			String contractId = contract.getContractid();
			float contractQty = stringToFloat(contract.getQuantity());
			float contractPrice = stringToFloat(contract.getPrice());
			float quantity = contractQty - purchaseQty;
			
			String arrivalFlag = checkContractEnforcement(contractId,materialId);
			if(("保留").equals(arrivalFlag)){
				continue;
			}
			if(quantity <= 0){//

				String where2 = " YSId = '" + YSId +"' AND contractId = '" + contractId  +"' AND deleteflag = '0'";
				List<B_PurchaseOrderDetailData> detailList = 
						getPurchaseOrderDetailFromDB(where2);
				
				if(detailList.size() > 1){
					//一份合同有多个物料,仅删除合同明细
					deletePurchaseOrderDetail(contract);
					
				}else{
					//一份合同只有一个物料,删除明细和头表
					List<B_PurchaseOrderData> contractDBList =  
							getPurchaseOrderFromDB(YSId,contractId);
					
						deletePurchaseOrder(contractDBList.get(0));
						deletePurchaseOrderDetail(contract);
				}
			}else{//套件产品,同一个物料重复出现,只删除其中一个的话,更新剩下的数量
				contract.setQuantity(String.valueOf(quantity));
				contract.setTotalprice(String.valueOf(quantity * contractPrice));
				
				updatePurchaseOrderDetail(contract);
			}
		
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private void insertRawRequirement(B_RawRequirementData raw) throws Exception{
		
		String where = "YSId = '" + raw.getYsid() +"' AND materialId='" + raw.getMaterialid() +"' ";
		
		List<B_RawRequirementData> list = new B_RawRequirementDao().Find(where);
		
		if(list.size() > 0 ){
			//update
			B_RawRequirementData db = list.get(0);

			copyProperties(db,raw);
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"更新采购方案物料",userInfo);
			copyProperties(db,commData);
			
			new B_RawRequirementDao().Store(db);	
			
		}else{
			//insert
			commData = commFiledEdit(Constants.ACCESSTYPE_INS,
					"新增采购方案物料",userInfo);
			copyProperties(raw,commData);

			guid = BaseDAO.getGuId();
			raw.setRecordid(guid);
			
			new B_RawRequirementDao().Create(raw);	
		}		
	}
	
	private void addPurchasePlan(
			String YSId,
			String purchaseId,
			List<B_PurchasePlanDetailData> webList) throws Exception{
		
		for(B_PurchasePlanDetailData detail:webList){
			
			String materilid = detail.getMaterialid();
			float purchase = stringToFloat(detail.getPurchasequantity());
			
			if(isNullOrEmpty(materilid))
				continue;		
			
			if(purchase == 0)
				detail.setContractflag(0);//不采购的不做合同				
			if(materilid.substring(0,1).equals(Constants.PURCHASETYPE_H))
				detail.setContractflag(0);//人工成本不做合同
			
			detail.setPurchaseid(purchaseId);
			detail.setYsid(YSId);
			insertPurchasePlanDetail(detail);			

			//更新虚拟库存
			float requirement = stringToFloat(detail.getManufacturequantity());
			updateMaterial("采购方案（新数据追加）",materilid,0,requirement);
			
		}//新数据:采购方案处理
	}
	
	private void addRawRequirement(String YSId) throws Exception{
		
		ArrayList<HashMap<String, String>> list3 = getRawMaterialGroupList(YSId);	
		
		for(HashMap<String, String> map2:list3){

			 String parentMat = map2.get("materialId").substring(0, 3);
			 String type = "";
			 if( ("F02").equals(parentMat)){//吹塑:F02
				 type = Constants.REQUISITION_BLOW;
			 }else if( ("F01").equals(parentMat)){//吸塑:F01
				 type = Constants.REQUISITION_BLISTE;
			 }else{//以外
				 type = Constants.REQUISITION_INJECT;
			 }
			 
			String rawmater = map2.get("rawMaterialId");//二级物料名称(原材料)
			float purchase = 0;//采购量
			String unit = DicUtil.getCodeValue("换算单位" + map2.get("rawUnit"));
			float funit = stringToFloat(unit);
			float totalQuantity = stringToFloat(map2.get("purchaseQuantity"));//采购量
			float requirement = ( totalQuantity / funit);
			
			B_RawRequirementData raw = new B_RawRequirementData();
			raw.setYsid(YSId);
			raw.setMaterialid(rawmater);
			raw.setSupplierid(map2.get("supplierId"));
			raw.setQuantity(floatToString(requirement));
			raw.setRawtype(type);
			
			insertRawRequirement(raw);
			
			//更新虚拟库存
			updateMaterial("二级BOM(原材料)物料需求表更新",rawmater,purchase,requirement);						
		}
		
	}
	
	public void rawRequirementALLAdd() throws Exception{
		
		//循环所有的耀升编号，及成品入库情况

		ArrayList<HashMap<String, String>> list3 = getPlanYSId();	
		System.out.println("耀升数量："+list3.size());
		
		//追加原材料物料需求表，及领料数量
		//成品已入库：领料数量=需求量
		//成品未入库：领料数量=实际出库量，如果 > 需求量，取需求量
		int index = 0;
		for(HashMap<String, String> map2:list3){
			
			String YSId = map2.get("YSId");
			String orderQty = map2.get("orderQty");
			String stockInQty = map2.get("stockInQty");
			String completedQty = map2.get("completedQuantity");//虚拟入库数

			float forderQty = stringToFloat(orderQty);
			float fstockInQty = stringToFloat(stockInQty);
			float fcompletedQty = stringToFloat(completedQty);
			
			//
			deleteRawRequirment(YSId);			

			//原材料物料需求表
			if(fstockInQty >= forderQty){

				addRawRequirement(YSId,true);
			}else{

				if(fcompletedQty >= forderQty)
					addRawRequirement(YSId,true);
				else
					addRawRequirement(YSId,false);
			}
			

			System.out.println("耀升编号："+YSId+"，No:"+index);
			index++;
		}
		
	}
	
	/**
	 * 删除原材料物料需求表
	 * @param YSId
	 * @throws Exception
	 */
	private void deleteRawRequirment(String YSId) {
		
		String where = "YSId = '" + YSId +"' AND deleteFlag='0' ";
		
		 try {
			new B_RawRequirementDao().RemoveByWhere(where);
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}
	
	private void addRawRequirement(String YSId,boolean stockinFlag) throws Exception{
		
		ArrayList<HashMap<String, String>> list3 = getRawMaterialGroupList(YSId);
		
		for(HashMap<String, String> map2:list3){

			 String parentMat = map2.get("materialId").substring(0, 3);
			 String type = "";
			 if( ("F02").equals(parentMat)){//吹塑:F02
				 type = Constants.REQUISITION_BLOW;
			 }else if( ("F01").equals(parentMat)){//吸塑:F01
				 type = Constants.REQUISITION_BLISTE;
			 }else{//以外
				 type = Constants.REQUISITION_INJECT;
			 }
			 
			String rawmater = map2.get("rawMaterialId");//二级物料名称(原材料)
			String unit = DicUtil.getCodeValue("换算单位" + map2.get("rawUnit"));
			float funit = stringToFloat(unit);
			float totalQuantity = stringToFloat(map2.get("purchaseQuantity"));//采购量
			String requirement = floatToString( totalQuantity / funit);
			
			
			B_RawRequirementData raw = new B_RawRequirementData();
			raw.setYsid(YSId);
			raw.setMaterialid(rawmater);
			raw.setSupplierid(map2.get("supplierId"));
			raw.setQuantity(requirement);
			raw.setRawtype(type);
			
			if(stockinFlag){
				raw.setStockoutqty(requirement);//成品已入库，默认已领料
			}else{
				//实际领料数量
				float out = stringToFloat(map2.get("stockoutQty"));
				float order = stringToFloat(requirement);
				if(out > order){
					raw.setStockoutqty( requirement);//如果多领了，不计入待出
				}else{
					raw.setStockoutqty( map2.get("stockoutQty"));//实际领料数
				}					
			}
			
			insertRawRequirement(raw);
								
		}
	}
	/*
	private String getRawStockoutQuantity(
			String YSId,String materialId) throws Exception{

		dataModel.setQueryName("rawStockoutQuantity");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("YSId", YSId);	
		userDefinedSearchCase.put("materialId", materialId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();
		
		String rtnValue="0";
		if(dataModel.getRecordCount() >0){
			rtnValue = dataModel.getYsViewData().get(0).get("quantity");
		}
		
		return rtnValue;
	}
	*/
	
	private ArrayList<HashMap<String, String>> getPlanYSId() throws Exception{

		String YSId = request.getParameter("YSId");
		dataModel.setQueryName("getPlanAndStockin");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("YSId", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		return baseQuery.getYsFullData();
		
	}
	
}
