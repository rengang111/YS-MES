package com.ys.business.service.bom;

import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.ReflectUtils;
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
import com.ys.business.action.model.bom.BomPlanModel;
import com.ys.business.action.model.common.ListOption;
import com.ys.business.action.model.common.TableFields;
import com.ys.business.action.model.contact.ContactModel;
import com.ys.business.action.model.material.MaterialModel;
import com.ys.business.action.model.order.OrderModel;
import com.ys.business.db.dao.B_BomDetailDao;
import com.ys.business.db.dao.B_BomPlanDao;
import com.ys.business.db.dao.B_ContactDao;
import com.ys.business.db.dao.B_MaterialDao;
import com.ys.business.db.dao.B_OrderDao;
import com.ys.business.db.dao.B_OrderDetailDao;
import com.ys.business.db.dao.B_PriceSupplierDao;
import com.ys.business.db.data.B_BomDetailData;
import com.ys.business.db.data.B_BomPlanData;
import com.ys.business.db.data.B_ContactData;
import com.ys.business.db.data.B_MaterialData;
import com.ys.business.db.data.B_OrderData;
import com.ys.business.db.data.B_OrderDetailData;
import com.ys.business.db.data.B_PriceSupplierData;
import com.ys.business.ejb.BusinessDbUpdateEjb;
import com.ys.business.service.common.BusinessService;
import com.ys.util.CalendarUtil;

@Service
public class BomService extends BaseService {

	DicUtil util = new DicUtil();

	BaseTransaction ts;

	TableFields fields;
	String guid ="";
	/*
	private HttpServletRequest request;
	
	public void setRequest(HttpServletRequest request){
		this.request = request;
	}
	public HttpServletRequest getRequest(){
		return this.request;
	}
	public BomService(HttpServletRequest request){
		this.request = request;
	}
	*/
	private HttpServletRequest request;
	
	private B_BomPlanData bomPlanData;
	private B_BomDetailData bomDetailData;
	private B_BomPlanDao bomPlanDao;
	private B_BomDetailDao bomDetailDao;
	private BomPlanModel reqModel;
	private UserInfo userInfo;
	private BaseModel dataModel;
	private  Model model;
	private HashMap<String, String> userDefinedSearchCase;
	private BaseQuery baseQuery;
	ArrayList<HashMap<String, String>> modelMap = null;	

	public BomService(){
		
	}

	public BomService(Model model,
			HttpServletRequest request,
			BomPlanModel reqModel,
			UserInfo userInfo){
		this.bomPlanDao = new B_BomPlanDao();
		this.bomPlanData = new B_BomPlanData();
		this.bomDetailDao = new B_BomDetailDao();
		this.bomDetailData = new B_BomDetailData();
		this.model = model;
		this.reqModel = reqModel;
		this.request = request;
		this.userInfo = userInfo;
		userDefinedSearchCase = new HashMap<String, String>();
		
	}
	public HashMap<String, Object> getBomList( 
			String data) throws Exception {
		
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
		

		dataModel.setQueryFileName("/business/bom/bomquerydefine");
		dataModel.setQueryName("getBomList");
		
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
		modelMap.put("unitList",util.getListOption(DicUtil.MEASURESTYPE, ""));		
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
	}
	
	public Model getBomDetailView() 
			throws Exception {
		
		String bomId = request.getParameter("bomId");

		dataModel = new BaseModel();		
		dataModel.setQueryFileName("/business/bom/bomquerydefine");
		dataModel.setQueryName("getBomDetailListByBomId");
		
		baseQuery = new BaseQuery(request, dataModel);

		userDefinedSearchCase.put("keywords1", bomId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		modelMap = baseQuery.getYsQueryData(0, 0);

		model.addAttribute("order",  modelMap.get(0));
		model.addAttribute("bomDetail", modelMap);
		
		return model;
	}
	

	public void getOrderDetailByYSId() throws Exception{
		
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		BaseQuery baseQuery = null;

		dataModel.setQueryFileName("/business/bom/bomquerydefine");
		dataModel.setQueryName("getOrderDetailByYSId");
		
		baseQuery = new BaseQuery(request, dataModel);

		String YSId = request.getParameter("YSId");
		userDefinedSearchCase.put("keywords1", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		baseQuery.getYsQueryData(0, 0);
		
		model.addAttribute("order", dataModel.getYsViewData().get(0));	
		
		reqModel.setManageRateList(
				util.getListOption(DicUtil.MANAGERATE, ""));
		
	}
	
	
	@SuppressWarnings("unchecked")
	public List<B_BomDetailData> getBomDetailByBomId(
			String where ) throws Exception {
		
		List<B_BomDetailData> dbList = null;
				
		try {

			dbList = (List<B_BomDetailData>)bomDetailDao.Find(where);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dbList = null;
		}
		
		return dbList;
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
			HttpServletRequest request) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		BaseQuery baseQuery = null;


		String key = request.getParameter("key").toUpperCase();

		dataModel.setQueryFileName("/business/bom/bomquerydefine");
		dataModel.setQueryName("getMaterialPriceList");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("keywords1", key);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, 0);	 

		modelMap.put("data", dataModel.getYsViewData());
		
		modelMap.put("retValue", "success");			
		
		return modelMap;
	}
	/*
	 * 
	 */
	public HashMap<String, Object> getSupplierPriceList(
			HttpServletRequest request) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		BaseQuery baseQuery = null;


		String key1 = request.getParameter("key1").toUpperCase();
		String key2 = request.getParameter("key2").toUpperCase();

		dataModel.setQueryFileName("/business/bom/bomquerydefine");
		dataModel.setQueryName("getSupplierPriceList");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("keywords1", key1);
		userDefinedSearchCase.put("keywords2", key2);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, 0);	 

		modelMap.put("data", dataModel.getYsViewData());
		
		modelMap.put("retValue", "success");			
		
		return modelMap;
	}
	
	/*
	 * 1.物料新增处理(一条数据)
	 * 2.子编码新增处理(N条数据)
	 */
	public Model insert() throws Exception  {

		ts = new BaseTransaction();

		try {
			
			ts.begin();
					
			B_BomPlanData reqData = (B_BomPlanData)reqModel.getBomPlan();
			
			//插入BOM基本信息
			insertBomPlan(reqData);
			
			//插入BOM详情		
			String bomid = reqData.getBomid();
			List<B_BomDetailData> reqDataList = reqModel.getBomDetailLines();
			
			for(B_BomDetailData data:reqDataList ){
				
				//过滤空行或者被删除的数据
				if(data != null && 
					data.getMaterialid() != null && 
					data.getMaterialid() != ""){
					
					data.setBomid(bomid);
					insertBomDetail(data);	
					
					//供应商单价修改
					String supplierId = data.getSupplierid();
					String price = data.getPrice();
					updatePriceSupplier(supplierId,price);
				}	
			
			}
			
			//重新查询,回到查看页面
			//rtnModel = view(request,rtnModel,selectedRecord,parentId);
			
			reqModel.setEndInfoMap(NORMAL, "suc001", "");
			
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
			reqModel.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return model;
	}	

	/*
	 * BOM基本信息insert处理
	 */
	public void insertBomPlan(
			B_BomPlanData data) throws Exception{
		
		fields = BusinessService.updateModifyInfo
		(Constants.ACCESSTYPE_INS,"BomPlanInsert", userInfo);
		
		guid = BaseDAO.getGuId();
		data.setRecordid(guid);
		data.setInsFields(fields);
		
		bomPlanDao.Create(data);	

	}	
	
	/*
	 * BOM详情插入处理
	 */
	private void insertBomDetail(
			B_BomDetailData detailData) throws Exception{
			
		 fields = BusinessService.updateModifyInfo
				(Constants.ACCESSTYPE_INS,"BomDetailInsert", userInfo);

		guid = BaseDAO.getGuId();
		detailData.setRecordid(guid);
		detailData.setInsFields(fields);
		
		bomDetailDao.Create(detailData);

	}	
	
	/*
	 * 1.订单基本信息更新处理(1条数据)
	 * 2.订单详情 新增/更新/删除 处理(N条数据)
	 */
	public Model update() throws Exception  {

		ts = new BaseTransaction();
		
		try {
			
			ts.begin();
						
			B_BomPlanData reqBom = (B_BomPlanData)reqModel.getBomPlan();
			
			//订单基本信息更新处理
			updateBomPlan(reqBom);
			
			//订单详情 更新/删除/插入 处理			
			List<B_BomDetailData> newDetailList = reqModel.getBomDetailLines();
			List<B_BomDetailData> delDetailList = new ArrayList<B_BomDetailData>();
			
			//过滤页面传来的有效数据
			for(B_BomDetailData data:newDetailList ){
				if(null == data.getMaterialid()){
					delDetailList.add(data);
				}
			}
			newDetailList.removeAll(delDetailList);
			
			//清空后备用
			delDetailList.clear();
			
			//根据画面的PiId取得DB中更新前的订单详情 
			//key:piId
			List<B_BomDetailData> oldDetailList = null;

			String bomId = reqBom.getBomid();
			String where = " bomId = '"+bomId +"'" + " AND deleteFlag = '0' ";
			oldDetailList = getBomDetailByBomId(where);
						
			//页面数据的recordId与DB匹配
			//key存在:update;key不存在:insert;
			//剩下未处理的DB数据:delete
			for(B_BomDetailData newData:newDetailList ){

				String newMaterialid = newData.getMaterialid();	

				boolean insFlag = true;
				for(B_BomDetailData oldData:oldDetailList ){
					String id = oldData.getMaterialid();
					
					if(newMaterialid.equals(id)){
						
						//更新处理
						updateOrderDetail(newData,oldData);						
						
						//从old中移除处理过的数据
						oldDetailList.remove(oldData);
						
						insFlag = false;
						
						break;
					}
				}

				//新增处理
				if(insFlag){
					insertBomDetail(newData);
				}
			}
			
			//删除处理
			if(oldDetailList.size() > 0){					
				deleteOrderDetail(oldDetailList);
			}
			
			ts.commit();
			
			//重新查询
			//rtnModel = view(request,rtnModel,selectedRecord,parentId);		
			reqModel.setEndInfoMap(NORMAL, "", "");
		}
		catch(Exception e) {
			ts.rollback();
			reqModel.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
				
		return model;
	}
	
	/*
	 * BOM基本信息更新处理
	 */
	public void updateBomPlan(B_BomPlanData reqBom) 
			throws Exception{

		String recordid = reqBom.getRecordid();
		
		//取得更新前数据		
		bomPlanData = getBomByRecordId(recordid);					
		
		if(null != bomPlanData){
			
			fields = BusinessService.updateModifyInfo
					(Constants.ACCESSTYPE_UPD,"OrderUpdate", userInfo);
	
			//处理共通信息
			bomPlanData.setUpdFields(fields);
			//获取页面数据
			bomPlanData.setPlandate(reqBom.getPlandate());
			bomPlanData.setManagerate(reqBom.getManagerate());
			bomPlanData.setManagecost(reqBom.getManagecost());
			bomPlanData.setLaborcost(reqBom.getLaborcost());
			bomPlanData.setMaterialcost(reqBom.getMaterialcost());
			bomPlanData.setAccountcost(reqBom.getAccountcost());
			bomPlanData.setProductcost(reqBom.getProductcost());
			
			bomPlanDao.Store(bomPlanData);
		}
	}

	/*
	 * 订单详情更新处理
	 */
	private void updateOrderDetail(
			B_BomDetailData newData,
			B_BomDetailData dbData) 
			throws Exception{
								
		fields = BusinessService.updateModifyInfo
				(Constants.ACCESSTYPE_UPD,"BomDetailUpdate", userInfo);

		//处理共通信息
		dbData.setUpdFields(fields);
		//获取页面数据
		dbData.setQuantity(newData.getQuantity());
		dbData.setPrice(newData.getPrice());;
		dbData.setTotalprice(newData.getTotalprice());
		dbData.setSupplierid(newData.getSupplierid());
		
		bomDetailDao.Store(dbData);

	}
	
	/*
	 * 更新供应商单价
	 */
	@SuppressWarnings("unchecked")
	public void updatePriceSupplier(
			String id,String price ) throws Exception {
		
		List<B_PriceSupplierData> priceList = null;
		B_PriceSupplierData pricedt = null;
		B_PriceSupplierDao dao = new B_PriceSupplierDao();

		String where = " supplierId = '"+id +"'" + " AND deleteFlag = '0' ";		

		priceList = (List<B_PriceSupplierData>)dao.Find(where);
		
		if(priceList != null && priceList.size() > 0)
			pricedt = priceList.get(0);		
		
		fields = BusinessService.updateModifyInfo
		(Constants.ACCESSTYPE_UPD,"PriceSupplierUpdate", userInfo);
		
		//处理共通信息
		pricedt.setUpdFields(fields);
		pricedt.setPrice(price);
		
		dao.Store(pricedt);
				
	}
	
	/*
	 * BOM详情删除处理
	 */
	public void deleteOrderDetail(
			List<B_BomDetailData> oldDetailList) 
			throws Exception{
		
		for(B_BomDetailData detail:oldDetailList){
			
			if(null != detail){
				
				fields = BusinessService.updateModifyInfo
						(Constants.ACCESSTYPE_DEL,"BomDetailDelete", userInfo);
		
				//处理共通信息
				detail.setUpdFields(fields);
				
				bomDetailDao.Store(detail);
			}
		}
	}

	/*
	 * 取得耀升编号的流水号
	 */
	public void getBomIdByMaterialId(String materialId) 
			throws Exception {
		
		dataModel = new BaseModel();
		
		dataModel.setQueryFileName("/business/bom/bomquerydefine");
		dataModel.setQueryName("getBomIdByMaterialId");
		
		baseQuery = new BaseQuery(request, dataModel);

		//查询条件     
		userDefinedSearchCase.put("keywords1", materialId);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, 0);	 
		
		//取得已有的最大流水号
		int code =Integer.parseInt(dataModel.getYsViewData().get(0).get("MaxSubId"));
				
		int newSubid = code + 1;
		
		String codeFormat = String.format("%03d", newSubid); 
		//I01.D019SWC001.00.001
		String bomId = materialId +"."+ codeFormat;
		
		bomPlanData.setBomid(bomId);
		bomPlanData.setSubid(codeFormat);
		
		reqModel.setBomPlan(bomPlanData);		
		model.addAttribute("bomForm",reqModel);
	}	
	
	private B_BomPlanData getBomByRecordId(String key) throws Exception {
		
		bomPlanData = new B_BomPlanData();
				
		try {
			bomPlanData.setRecordid(key);
			bomPlanData = (B_BomPlanData)bomPlanDao.FindByPrimaryKey(bomPlanData);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			bomPlanData = null;
		}
		
		return bomPlanData;
	}

	public Model createBomPlan() throws Exception {

		String materialId = request.getParameter("materialId");   
		getBomIdByMaterialId(materialId);
		
		getOrderDetailByYSId();
		
		return model;
		
	}
	
	public Model copyBomPlan() throws Exception {		

		String bomId = request.getParameter("bomId"); 
		String materialId = "";
		
		if(bomId !=null && bomId.length() > 4)
			materialId=bomId.substring(0,bomId.length() - 4);
		
		getBomIdByMaterialId(materialId);
		
		getBomDetailView();
		
		
		return model;
		
	}
	
	
}
