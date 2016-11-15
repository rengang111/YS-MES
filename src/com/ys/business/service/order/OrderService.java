package com.ys.business.service.order;

import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.system.service.common.BaseService;
import com.ys.util.DicUtil;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basedao.BaseTransaction;
import com.ys.util.basequery.BaseQuery;
import com.ys.util.basequery.common.BaseModel;
import com.ys.util.basequery.common.Constants;
import com.ys.business.action.model.common.ListOption;
import com.ys.business.action.model.common.TableFields;
import com.ys.business.action.model.contact.ContactModel;
import com.ys.business.action.model.material.MaterialModel;
import com.ys.business.action.model.order.OrderModel;
import com.ys.business.db.dao.B_ContactDao;
import com.ys.business.db.dao.B_MaterialDao;
import com.ys.business.db.dao.B_OrderDao;
import com.ys.business.db.dao.B_OrderDetailDao;
import com.ys.business.db.dao.B_PriceSupplierDao;
import com.ys.business.db.data.B_ContactData;
import com.ys.business.db.data.B_MaterialData;
import com.ys.business.db.data.B_OrderData;
import com.ys.business.db.data.B_OrderDetailData;
import com.ys.business.db.data.B_PriceSupplierData;
import com.ys.business.ejb.BusinessDbUpdateEjb;
import com.ys.business.service.common.BusinessService;
import com.ys.util.CalendarUtil;

@Service
public class OrderService extends BaseService {

	DicUtil util = new DicUtil();

	BaseTransaction ts;

	TableFields fields;
	String guid ="";

	public HashMap<String, Object> getOrderList(HttpServletRequest request, 
			String data) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		BaseQuery baseQuery = null;

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
		

		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("getOrderList");
		
		baseQuery = new BaseQuery(request, dataModel);
		
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
		modelMap.put("unitList",doOptionChange(DicUtil.MEASURESTYPE, "").getUnitList());
		
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
	}
	
/*
	public HashMap<String, Object> supplierPriceView(HttpServletRequest request, 
			String data) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		BaseQuery baseQuery = null;

		data = URLDecoder.decode(data, "UTF-8");

		dataModel.setQueryFileName("/business/material/materialquerydefine");
		dataModel.setQueryName("supplierPriceList");
		
		baseQuery = new BaseQuery(request, dataModel);

		String materialId  = getJsonData(data, "materialid");

		userDefinedSearchCase.put("keywords1", materialId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, 0);	 
		
		//modelMap.put("unitList",doOptionChange(DicUtil.MEASURESTYPE, "").getUnitList());
		
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
	}
*/
	public ArrayList<HashMap<String, String>> getOrderViewByPIId(
			HttpServletRequest request,
			String PIId ) throws Exception {
		
		ArrayList<HashMap<String, String>> modelMap = null;
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		BaseQuery baseQuery = null;

		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("getOrderViewByPIId");
		
		baseQuery = new BaseQuery(request, dataModel);


		userDefinedSearchCase.put("keywords1", PIId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		modelMap = baseQuery.getYsQueryData(0, 0);
		
		//model.addAttribute("order", dataModel.getYsViewData().get(0));
		//model.addAttribute("detail", dataModel.getYsViewData());
		
		return modelMap;
	}
		

	public HashMap<String, Object> supplierPriceHistory(
			HttpServletRequest request, 
			String data) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		BaseQuery baseQuery = null;

		data = URLDecoder.decode(data, "UTF-8");

		dataModel.setQueryFileName("/business/material/materialquerydefine");
		dataModel.setQueryName("supplierPriceHistory");
		
		baseQuery = new BaseQuery(request, dataModel);

		String supplierId  = request.getParameter("supplierId");

		userDefinedSearchCase.put("keywords1", supplierId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, 0);	 
			
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
	}
	

	public HashMap<String, Object> getCustomer(HttpServletRequest request, 
			String data) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		BaseQuery baseQuery = null;

		data = URLDecoder.decode(data, "UTF-8");
		
		String key = request.getParameter("key").toUpperCase();
		
				

		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("getCustomer");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("keywords1", key);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, 0);	 	
		
		modelMap.put("recordsTotal", dataModel.getRecordCount()); 
		
		modelMap.put("recordsFiltered", dataModel.getRecordCount());

		
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
	}
	
	
	public HashMap<String, Object> getSupplierList(
			HttpServletRequest request, 
			String data) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		BaseModel dataModel = new BaseModel();
		BaseQuery baseQuery = null;

		data = URLDecoder.decode(data, "UTF-8");


		dataModel.setQueryFileName("/business/material/materialquerydefine");
		dataModel.setQueryName("getSupplierList");
		
		String key ="";
		baseQuery = new BaseQuery(request, dataModel);
		String sql = baseQuery.getSql();
		sql = sql.replace("?", key);
		baseQuery.getYsQueryData(0,0);	 
		
		modelMap.put("data", dataModel.getYsViewData());
		
		modelMap.put("retValue", "success");
		
		return modelMap;
	}
	
	
	public HashMap<String, Object> getOrderSubIdByParentId(
			HttpServletRequest request, 
			String data) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		BaseQuery baseQuery = null;

		
		String key = request.getParameter("parentId");

		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("getOrderSubIdByParentId");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("keywords1", key);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, 0);	 

		modelMap.put("retValue", "success");
		
		int code =Integer.parseInt(dataModel.getYsViewData().get(0).get("MaxSubId"));
		
		int newSubid = code + 1;
		
		String codeFormat = String.format("%03d", newSubid); 
		
		modelMap.put("code",newSubid);	
		modelMap.put("codeFormat",codeFormat);			
		
		return modelMap;
	}
	
	/*
	 * 
	 */
	public HashMap<String, Object> getMaterialList(
			HttpServletRequest request, 
			String data) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		BaseQuery baseQuery = null;


		String key = request.getParameter("key").toUpperCase();

		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("materialList");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("keywords1", key);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, 0);	 

		modelMap.put("data", dataModel.getYsViewData());
		
		modelMap.put("retValue", "success");			
		
		return modelMap;
	}
	
	/*
	 * 1.显示当前选中物料的基本信息
	 * 2.显示相关的所有子编码信息(N条数据) 
	 */	
	public Model viewPrice(
			HttpServletRequest request,
			Model model,
			String recordId,
			String parentId) {
		
		MaterialModel Matmodel = new MaterialModel();

		String unitName = "";
		
		try {
			

			B_PriceSupplierDao priceDao = new B_PriceSupplierDao();
			B_PriceSupplierData pricData = new B_PriceSupplierData();
	
		
			pricData.setRecordid(recordId);
			pricData = (B_PriceSupplierData)priceDao.FindByPrimaryKey(pricData);

			
			//选择数据明细内容
			Matmodel.setPrice(pricData);
			//计量单位
			Matmodel.setUnitname(unitName);
			//Matmodel.setUnit(pricData.getUnit());
			Matmodel.setCurrencyList(util.getListOption(DicUtil.CURRENCY, ""));
				
			Matmodel.setEndInfoMap("098", "0001", "");
			
		}
		catch(Exception e) {
			Matmodel.setEndInfoMap(SYSTEMERROR, "err001", parentId);
		}
		
		model.addAttribute("material", Matmodel);
		
		return model;
		
	}
	
	/*
	 * 1.显示当前选中物料的基本信息
	 * 2.显示相关的所有子编码信息(N条数据) 
	 */
	
	public Model view(
			HttpServletRequest request,
			Model model,
			String recordId,
			String parentId) {
		MaterialModel Matmodel = new MaterialModel();
		B_MaterialData FormDetail = new B_MaterialData();


		String shareModel = "";
		String unitName = "";
		
		try {
			
				
			HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
			BaseModel dataModel = new BaseModel();
			BaseQuery baseQuery = null;

			dataModel.setQueryFileName("/business/material/materialquerydefine");
			dataModel.setQueryName("materialList");
			
			baseQuery = new BaseQuery(request, dataModel);
			
			userDefinedSearchCase.put("keywords1", parentId);
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
			baseQuery.getYsQueryData(0, 0);	 
			
			List<B_MaterialData> list = new ArrayList<B_MaterialData>();
			
			if(dataModel!=null & dataModel.getRecordCount() > 0)
			{				
				HashMap<String, String> map = 
						(HashMap<String, String>)dataModel.getYsViewData().get(0);

				for(int i=0;i<dataModel.getRecordCount();i++){
					
					map = (HashMap<String, String>)dataModel.getYsViewData().get(i);
					B_MaterialData db = new B_MaterialData();

					//定位到选择的那条数据
					if(map.get("recordId").equals(recordId)){

						FormDetail.setRecordid(map.get("recordId"));
						FormDetail.setMaterialid(map.get("materialId"));
						FormDetail.setMaterialname(map.get("materialName"));
						FormDetail.setCategoryid(map.get("categoryId"));
						FormDetail.setDescription(map.get("description"));
						FormDetail.setSharemodel(map.get("shareModel"));
						FormDetail.setUnit(map.get("unit"));
						Matmodel.setAttribute1(map.get("categoryId"));
						Matmodel.setAttribute2(map.get("categoryName"));
						shareModel = map.get("shareModel");
						unitName = map.get("dicName");
					}
					db.setRecordid(map.get("recordId"));
					db.setSubid(map.get("subId"));
					db.setSubiddes(map.get("subIdDes"));
					db.setParentid(map.get("parentId"));
					list.add(db);
				}	
					
			}
			//选择数据明细内容
			Matmodel.setMaterial(FormDetail);
			//子编号信息
			Matmodel.setMaterialLines(list);

			//计量单位
			Matmodel.setUnitname(unitName);
			Matmodel.setUnit(FormDetail.getUnit());
			Matmodel.setUnitList(doOptionChange(DicUtil.MEASURESTYPE, "").getUnitList());
				
			Matmodel.setShareModelList(shareModel.split(","));
			Matmodel.setEndInfoMap("098", "0001", "");
			
		}
		catch(Exception e) {
			Matmodel.setEndInfoMap(SYSTEMERROR, "err001", parentId);
		}
		
		model.addAttribute("material", Matmodel);
		
		return model;
		
	}
	
	/*
	 * 1.新增一条物料单价数据,包括供应商信息处理(一条数据)
	 *//*
	public Model insertPrice(
			MaterialModel reqFormBean,
			Model rtnModel,
			HttpServletRequest request,
			UserInfo userInfo) throws Exception  {
		
		ts = new BaseTransaction();
		
		try {
			
			ts.begin();
			
			B_PriceSupplierDao priceDao = new B_PriceSupplierDao();
			B_PriceSupplierData reqData = new B_PriceSupplierData();
			B_PriceSupplierData DBData = new B_PriceSupplierData();
			
			reqData = (B_PriceSupplierData)reqFormBean.getPrice();
			
			String recordeId = reqData.getRecordid();
			String guid = "";
			TableFields commFields=null;
			
			//判断该供应商是否已有报价
			DBData =prePriceCheck(recordeId);
			boolean blPrice = DBData ==null?false:true;
			if(blPrice){

				//更新单价表
				commFields = BusinessService.updateModifyInfo
						(Constants.ACCESSTYPE_UPD,"MaterialUpdate", userInfo);

				DBData.setUpdFields(commFields);
				DBData.setPrice(reqData.getPrice());
				DBData.setCurrency(reqData.getCurrency());
				DBData.setPriceunit(reqData.getPriceunit());
				DBData.setPricedate(reqData.getPricedate());
				DBData.setPricesource(BusinessConstants.PRICESOURCE_OTHER);
				DBData.setUsedflag(BusinessConstants.MATERIAL_USERD_Y);
				
				priceDao.Store(DBData);	
				
				//插入历史表
				commFields = BusinessService.updateModifyInfo
						(Constants.ACCESSTYPE_INS,"MaterialUpdate", userInfo);
				
				guid = BaseDAO.getGuId();
				DBData.setRecordid(guid);
				DBData.setInsFields(commFields);
				
				priceDao.CreateHistory(DBData);
				
			}else{

				//插入单价表				
				//新增数据的价格来源设为:其他
				commFields = BusinessService.updateModifyInfo
						(Constants.ACCESSTYPE_INS,"MaterialInsert", userInfo);
	
				guid = BaseDAO.getGuId();
				reqData.setRecordid(guid);
				reqData.setInsFields(commFields);
				reqData.setPricesource(BusinessConstants.PRICESOURCE_OTHER);	
				reqData.setUsedflag(BusinessConstants.MATERIAL_USERD_Y);
	
				priceDao.Create(reqData);	
				

				//插入历史表
				commFields = BusinessService.updateModifyInfo
						(Constants.ACCESSTYPE_INS,"MaterialInsert", userInfo);
				
				guid = BaseDAO.getGuId();
				reqData.setRecordid(guid);
				reqData.setInsFields(commFields);
				
				priceDao.CreateHistory(reqData);	
			}

			ts.commit();
			
			reqFormBean.setEndInfoMap(NORMAL, "suc001", "");
		}
		catch(Exception e) {
			ts.rollback();
			System.out.println(e.getMessage());
			reqFormBean.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		rtnModel.addAttribute("material",reqFormBean);
		
		return rtnModel;
	}	
	*/
	
	/*
	 * 1.物料新增处理(一条数据)
	 * 2.子编码新增处理(N条数据)
	 */
	public Model insert(
			OrderModel reqFormBean,
			Model rtnModel,
			HttpServletRequest request,
			UserInfo userInfo) throws Exception  {

		ts = new BaseTransaction();

		try {

			int YSMaxid = getYSIdByParentId(request);
			
			ts.begin();
					
			B_OrderData reqData = (B_OrderData)reqFormBean.getOrder();
			
			//插入订单基本信息
			insertOrder(reqData,userInfo);
			
			//处理订单详情数据		
			List<B_OrderDetailData> reqDataList = reqFormBean.getOrderDetailLines();
			
			String piId = reqData.getPiid();
			
			for(B_OrderDetailData data:reqDataList ){
				
				//过滤空行或者被删除的数据
				if(data != null && 
					data.getMaterialid() != null && 
					data.getMaterialid() != ""){

					insertOrderDetail(data, YSMaxid,piId, userInfo);
								
				}	
			
			}
			
			//重新查询,回到查看页面
			//rtnModel = view(request,rtnModel,selectedRecord,parentId);
			
			reqFormBean.setEndInfoMap(NORMAL, "suc001", "");
			
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
			reqFormBean.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return rtnModel;
	}	

	/*
	 * 订单详情插入处理
	 */
	public void insertOrder(
			B_OrderData data,
			UserInfo userInfo) throws Exception{

		B_OrderDao dao = new B_OrderDao();	
		
		fields = BusinessService.updateModifyInfo
		(Constants.ACCESSTYPE_INS,"OrderInsert", userInfo);
		
		guid = BaseDAO.getGuId();
		data.setRecordid(guid);
		data.setInsFields(fields);
		
		dao.Create(data);	
		

	}	
	
	/*
	 * 订单详情插入处理
	 */
	public void insertOrderDetail(
			B_OrderDetailData newData,
			int YSMaxid ,
			String piId,
			UserInfo userInfo) throws Exception{

		B_OrderDetailDao dao = new B_OrderDetailDao();

		String parentid = BusinessService.getYSCommCode();
		
		//
		if(newData.getMaterialid() != null && 
		   newData.getMaterialid() != ""){

			String ysid = BusinessService.getYSFormatCode(YSMaxid,true);
				
			 fields = BusinessService.updateModifyInfo
					(Constants.ACCESSTYPE_INS,"OrderDetailInsert", userInfo);

			guid = BaseDAO.getGuId();
			newData.setRecordid(guid);
			newData.setInsFields(fields);
			newData.setParentid(parentid);
			newData.setSubid(String.valueOf(YSMaxid+1));
			newData.setPiid(piId);
			newData.setYsid(ysid);
			
			dao.Create(newData);
			
			YSMaxid++;
		}	

	}	

	/*
	 * 1.订单基本信息更新处理(1条数据)
	 * 2.订单详情 新增/更新/删除 处理(N条数据)
	 */
	public Model update(
			OrderModel reqForm,
			Model rtnModel, 
			HttpServletRequest request,
			UserInfo userInfo) throws Exception  {

		ts = new BaseTransaction();
		
		try {
			
			int YSMaxid = getYSIdByParentId(request);
			
			ts.begin();
				
			B_OrderData reqOrder = (B_OrderData)reqForm.getOrder();
			
			//订单基本信息更新处理
			updateOrder(reqOrder,userInfo);
			
			//订单详情 更新/删除/插入 处理			
			List<B_OrderDetailData> newDetailList = reqForm.getOrderDetailLines();
			List<B_OrderDetailData> delDetailList = new ArrayList<B_OrderDetailData>();
			
			//过滤页面传来的有效数据
			for(B_OrderDetailData data:newDetailList ){
				if(null == data.getMaterialid()){
					delDetailList.add(data);
				}
			}
			newDetailList.removeAll(delDetailList);
			
			//清空后备用
			delDetailList.clear();
			
			//根据画面的PiId取得DB中更新前的订单详情 
			//key:piId
			ArrayList<HashMap<String, String>> oldDetailList = null;
			String piId = reqOrder.getPiid();
			
			oldDetailList = getOrderViewByPIId(request,piId);
						
			//页面数据的recordId与DB匹配
			//key存在:update;key不存在:insert;
			//剩下未处理的DB数据:delete
			for(B_OrderDetailData newData:newDetailList ){

				String recordid = newData.getRecordid();	

				boolean insFlag = true;
				for(HashMap<String, String> old:oldDetailList ){
					String id = old.get("detailRecordId");
					
					if(recordid.equals(id)){
						
						//更新处理
						updateOrderDetail(newData,userInfo);						
						
						//从old中移除处理过的数据
						oldDetailList.remove("detailRecordId");
						
						insFlag = false;
						
						break;
					}
				}

				//新增处理
				if(insFlag){
					insertOrderDetail(newData, YSMaxid, piId, userInfo);
				}
			}
			
			//删除处理
			if(oldDetailList.size() > 0){					
				deleteOrderDetail(oldDetailList,userInfo);
			}
			
			ts.commit();
			
			//重新查询
			//rtnModel = view(request,rtnModel,selectedRecord,parentId);		
			reqForm.setEndInfoMap(NORMAL, "", "");
		}
		catch(Exception e) {
			ts.rollback();
			reqForm.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
				
		return rtnModel;
	}
	
	/*
	 * 订单基本信息更新处理
	 */
	public void updateOrder(B_OrderData order,UserInfo userInfo) 
			throws Exception{

		String recordid = order.getRecordid();		
		B_OrderDao dao = new B_OrderDao();
		
		//取得更新前数据		
		B_OrderData dbData = getOrderByRecordId(recordid);					
		
		if(null != dbData){
			
			fields = BusinessService.updateModifyInfo
					(Constants.ACCESSTYPE_UPD,"OrderUpdate", userInfo);
	
			//处理共通信息
			dbData.setUpdFields(fields);
			//获取页面数据
			dbData.setOrderid(order.getOrderid());
			dbData.setPaymentterm(order.getPaymentterm());
			dbData.setCurrency(order.getCurrency());;
			dbData.setShippingcase(order.getShippingcase());
			dbData.setDeliverydate(order.getDeliverydate());
			dbData.setOrderdate(order.getOrderdate());
			dbData.setDeliveryport(order.getDeliveryport());
			dbData.setLoadingport(order.getLoadingport());
			dbData.setTotalprice(order.getTotalprice());
			
			dao.Store(dbData);
		}
	}

	/*
	 * 订单基本信息更新处理
	 */
	public void updateOrderDetail(B_OrderDetailData detail,UserInfo userInfo) 
			throws Exception{

		String recordid = detail.getRecordid();		
		B_OrderDetailDao dao = new B_OrderDetailDao();
		
		//取得更新前数据		
		B_OrderDetailData dbData = getOrderDetailByRecordId(recordid);					
		
		if(null != dbData){
			
			fields = BusinessService.updateModifyInfo
					(Constants.ACCESSTYPE_UPD,"OrderDetailUpdate", userInfo);
	
			//处理共通信息
			dbData.setUpdFields(fields);
			//获取页面数据
			dbData.setQuantity(detail.getQuantity());
			dbData.setPrice(detail.getPrice());;
			dbData.setTotalprice(detail.getTotalprice());
			
			dao.Store(dbData);
		}
	}
	
	/*
	 * 订单详情删除处理
	 */
	public void deleteOrderDetail(ArrayList<HashMap<String, String>> detailList,UserInfo userInfo) 
			throws Exception{

		B_OrderDetailDao dao = new B_OrderDetailDao();
		
		for(HashMap<String, String> detail:detailList){
			
			String recordid = detail.get("recordid");		
			
			//取得更新前数据		
			B_OrderDetailData dbData = getOrderDetailByRecordId(recordid);					
			
			if(null != dbData){
				
				fields = BusinessService.updateModifyInfo
						(Constants.ACCESSTYPE_DEL,"OrderDetailDelete", userInfo);
		
				//处理共通信息
				dbData.setUpdFields(fields);
				
				dao.Store(dbData);
			}
		}
	}
	
	
	/*
	 * 1.显示当前选中物料的基本信息
	 * 2.显示相关的所有子编码信息(N条数据) 
	 */
	
	public Model SelectSupplier(
			MaterialModel Matmodel,
			HttpServletRequest request,
			Model model) {
		B_MaterialData dbData = new B_MaterialData();
		B_MaterialDao dao = new B_MaterialDao();

		String key = request.getParameter("recordId");
		String categoryName = request.getParameter("categoryName");	

		try{
			
			categoryName = new String(categoryName.getBytes("ISO8859-1"), "UTF-8");
			
			dbData.setRecordid(key);
			dbData = (B_MaterialData)dao.FindByPrimaryKey(dbData);
			Matmodel.setMaterial(dbData);
			
			Matmodel.setEndInfoMap("098", "0001", "");
			Matmodel.setMaterialid(dbData.getMaterialid());
			Matmodel.setRecordId(dbData.getRecordid());
			Matmodel.setMaterialname(dbData.getMaterialname());
			Matmodel.setCategoryid(dbData.getCategoryid());
			Matmodel.setCategoryname(categoryName);
			Matmodel.setCurrencyList(util.getListOption(DicUtil.CURRENCY, ""));
			model.addAttribute("material", Matmodel);
			
		}catch(Exception e){
			
		}
		return model;
		
	}
	
	/*
	 * 新增物料初始处理
	 */
	public OrderModel createOrder(
			HttpServletRequest request,
			OrderModel reqModel) {

		try {	
			
      
			//耀升编号
	        String paternId = BusinessService.getYSCommCode();
	        
			int YSMaxId = getYSIdByParentId(request);

			reqModel.setYSMaxId(YSMaxId);	
			reqModel.setYSParentId(paternId);
			
			reqModel.setDeliveryPortList(
					util.getListOption(DicUtil.DELIVERYPORT, ""));
			reqModel.setShippingCaseList(
					util.getListOption(DicUtil.SHIPPINGCASE, ""));
			reqModel.setLoadingPortList(
					util.getListOption(DicUtil.LOADINGPORT, ""));
			reqModel.setCurrencyList(
					util.getListOption(DicUtil.CURRENCY, ""));
			
			
			reqModel.setEndInfoMap(NORMAL, "", "");
				

			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			reqModel.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return reqModel;
	
	}

	/*
	 * 取得耀升编号的流水号
	 */
	public int getYSIdByParentId(HttpServletRequest request) 
			throws Exception {
		
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		BaseQuery baseQuery = null;
		
  
		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("getYSIdByParentId");
		
		baseQuery = new BaseQuery(request, dataModel);

		//查询条件
        String paternId = BusinessService.getYSCommCode();
        
		userDefinedSearchCase.put("keywords1", paternId);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, 0);	 
		
		//取得已有的最大流水号
		int code =Integer.parseInt(dataModel.getYsViewData().get(0).get("MaxSubId"));
		
		return code;
	}
	
	public MaterialModel doOptionChange(String type, String parentCode) {
		MaterialModel model = new MaterialModel();
		
		try {
			ArrayList<ListOption> optionList = util.getListOption(type, parentCode);
			model.setUnitList(optionList);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
			model.setUnitList(null);
		}
		
		return model;
	}
	
	private ArrayList<HashMap<String, String>> getOrderDetailbyPiId(
			HttpServletRequest request, 
			String key) throws Exception {
			
			HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
			BaseModel dataModel = new BaseModel();
			dataModel.setQueryFileName("/business/order/orderquerydefine");
			dataModel.setQueryName("getOrderDetailbyPiId");
			BaseQuery baseQuery = new BaseQuery(request, dataModel);
			userDefinedSearchCase.put("keyword", key);
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
			
			return baseQuery.getYsQueryData(0, 0);
						
	}	

	
	public MaterialModel doDelete(String data, UserInfo userInfo){
		
		MaterialModel model = new MaterialModel();
		
		try {
			BusinessDbUpdateEjb bean = new BusinessDbUpdateEjb();
	        
	        bean.executeOrganDelete(data, userInfo);
	        
	        model.setEndInfoMap(NORMAL, "", "");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return model;
	}
	
	
	public ContactModel getContactDetailInfo(String key) throws Exception {
		ContactModel model = new ContactModel();
		B_ContactDao dao = new B_ContactDao();
		B_ContactData dbData = new B_ContactData();
		dbData.setId(key);
		dbData = (B_ContactData)dao.FindByPrimaryKey(dbData);
		model.setContactData(dbData);
		model.setCompanyCode(dbData.getCompanycode());
		
		//model.setSexList(doOptionChange(DicUtil.ORGANTYPE, "").getCategoryList());
		
		model.setEndInfoMap("098", "0001", "");
		model.setKeyBackup(dbData.getId());
		
		return model;
	}
	
	
	private B_OrderData getOrderByRecordId(String key) throws Exception {
		B_OrderDao dao = new B_OrderDao();
		B_OrderData dbData = new B_OrderData();
				
		try {
			dbData.setRecordid(key);
			dbData = (B_OrderData)dao.FindByPrimaryKey(dbData);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dbData = null;
		}
		
		return dbData;
	}
	
	private B_OrderDetailData getOrderDetailByRecordId(String key) throws Exception {
		B_OrderDetailDao dao = new B_OrderDetailDao();
		B_OrderDetailData dbData = new B_OrderDetailData();
				
		try {
			dbData.setRecordid(key);
			dbData = (B_OrderDetailData)dao.FindByPrimaryKey(dbData);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dbData = null;
		}
		
		return dbData;
	}
	
	/*
	private B_PriceSupplierData prePriceCheck(String key) throws Exception {

		B_PriceSupplierDao dao = new B_PriceSupplierDao();
		B_PriceSupplierData pricData = new B_PriceSupplierData();
				
		try {
			pricData.setRecordid(key);
			pricData = (B_PriceSupplierData)dao.FindByPrimaryKey(pricData);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			pricData = null;
		}
		
		return pricData;
	}
	
	private boolean orderDetailExistCheck(HttpServletRequest request,
			String YSId,
			String supplierId,
			B_PriceSupplierData oldPriceData) throws Exception {
			
		boolean rtnValue = false;
		
		//确认该供应商报价是否存在
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		BaseQuery baseQuery = null;
		dataModel.setQueryFileName("/business/orderquerydefine");
		dataModel.setQueryName("orderDetailExistCheck");
		userDefinedSearchCase.put("keywords1", YSId);
		//userDefinedSearchCase.put("keywords2", supplierId);
		baseQuery = new BaseQuery(request, dataModel);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0,0);	
		
		//查询结果判断
		//oldPriceData = baseQuery.getys
		rtnValue =dataModel.getRecordCount()>0?true:false;
		
		return rtnValue;
		
	}

	*/
}
