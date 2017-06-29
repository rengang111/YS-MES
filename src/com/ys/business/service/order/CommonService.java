package com.ys.business.service.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import com.ys.business.action.model.order.MaterialModel;
import com.ys.business.db.dao.B_PriceReferenceDao;
import com.ys.business.db.dao.B_PriceSupplierDao;
import com.ys.business.db.dao.B_PriceSupplierHistoryDao;
import com.ys.business.db.data.B_PriceReferenceData;
import com.ys.business.db.data.B_PriceSupplierData;
import com.ys.business.db.data.B_PriceSupplierHistoryData;
import com.ys.business.db.data.CommFieldsData;

@Service
public class CommonService extends BaseService {

	DicUtil util = new DicUtil();

	BaseTransaction ts;

	String guid ="";
	private CommFieldsData commData;
	
	public HttpServletRequest request;
	
	private MaterialModel reqModel;
	public UserInfo userInfo;
	private BaseModel dataModel;
	private Model model;
	private HashMap<String, String> userDefinedSearchCase;
	private BaseQuery baseQuery;
	ArrayList<HashMap<String, String>> modelMap = null;	
	public HttpSession session;
	
	public CommonService(){
		this.modelMap = new ArrayList<HashMap<String, String>>();
		this.dataModel = new BaseModel();
		this.userDefinedSearchCase = new HashMap<String, String>();
		dataModel.setQueryFileName("/business/material/materialquerydefine");
		
	}


	
	public HashMap<String, Object> getSupplierList(
			HttpServletRequest request, 
			String data) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();

		String key = request.getParameter("key").toUpperCase();

		dataModel.setQueryFileName("/business/material/materialquerydefine");
		dataModel.setQueryName("getSupplierList");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("keywords1", key);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0,0);	 
		
		modelMap.put("data", dataModel.getYsViewData());		
		
		return modelMap;
	}
		
	public HashMap<String, Object> getMaterialMAXId(
			HttpServletRequest request, 
			String data) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		
		String key = request.getParameter("categoryId");

		dataModel.setQueryFileName("/business/material/materialquerydefine");
		dataModel.setQueryName("getMaxMaterialId");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("keywords1", key);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, 0);	 

		modelMap.put("retValue", "success");
		
		int code =Integer.parseInt(dataModel.getYsViewData().get(0).get("serialNumber"));
		
		int categoryId = code + 1;
		
		String codeFormat = String.format("%03d", categoryId); 
		
		modelMap.put("code",categoryId);	
		modelMap.put("codeFormat",codeFormat);			
		
		return modelMap;
	}


	
	/*
	 * 1.新增一条物料单价数据,包括供应商信息处理(一条数据)
	 */
	public Model insertPrice(
			MaterialModel reqFormBean,
			Model rtnModel,
			HttpServletRequest request,
			UserInfo userInfo) throws Exception  {
		
		ts = new BaseTransaction();
		
		try {
			
			ts.begin();
			
			B_PriceSupplierDao priceDao = new B_PriceSupplierDao();
			B_PriceSupplierHistoryDao historyDao = new B_PriceSupplierHistoryDao();
			B_PriceSupplierHistoryData historyData = new B_PriceSupplierHistoryData();
			B_PriceSupplierData reqData = new B_PriceSupplierData();
			B_PriceSupplierData DBData = new B_PriceSupplierData();
			
			reqData = (B_PriceSupplierData)reqFormBean.getPrice();
			
			String materialId = reqData.getMaterialid();
			String supplierId = reqData.getSupplierid();
			String guid = "";
			
			//判断该供应商是否已有报价
			DBData =prePriceCheck(materialId,supplierId);
			
			boolean blPrice = DBData ==null?false:true;
			
			if(blPrice){

				//更新单价表(同时更新createTime)
				commData = commFiledEdit(Constants.ACCESSTYPE_INS,"MaterialUpdate",userInfo);

				copyProperties(DBData,commData);
				DBData.setPrice(reqData.getPrice());
				DBData.setCurrency(reqData.getCurrency());
				DBData.setPriceunit(reqData.getPriceunit());
				DBData.setPricedate(reqData.getPricedate());
				DBData.setPricesource(BusinessConstants.PRICESOURCE_OTHER);
				DBData.setUsedflag(BusinessConstants.MATERIAL_USERD_Y);
				
				priceDao.Store(DBData);	
				
				updatePriceInfo(DBData.getMaterialid());
				
				//插入历史表
				copyProperties(historyData,DBData);
				commData = commFiledEdit(Constants.ACCESSTYPE_INS,"MaterialUpdate",userInfo);

				copyProperties(historyData,commData);
				
				guid = BaseDAO.getGuId();
				historyData.setRecordid(guid);
				
				historyDao.Create(historyData);
				
			}else{

				//插入单价表				
				//新增数据的价格来源设为:其他
				commData = commFiledEdit(Constants.ACCESSTYPE_INS,"MaterialUpdate",userInfo);

				copyProperties(reqData,commData);
	
				guid = BaseDAO.getGuId();
				reqData.setRecordid(guid);
				reqData.setPricesource(BusinessConstants.PRICESOURCE_OTHER);	
				reqData.setUsedflag(BusinessConstants.MATERIAL_USERD_Y);
				
				priceDao.Create(reqData);	

				updatePriceInfo(reqData.getMaterialid());//

				//插入历史表
				copyProperties(historyData,reqData);
				commData = commFiledEdit(Constants.ACCESSTYPE_INS,"MaterialInsert",userInfo);

				copyProperties(historyData,commData);
				
				guid = BaseDAO.getGuId();
				historyData.setRecordid(guid);
				
				historyDao.Create(historyData);	
			}

			
			ts.commit();
			
		}
		catch(Exception e) {
			ts.rollback();
			System.out.println(e.getMessage());
		}
		
		rtnModel.addAttribute("material",reqFormBean);
		
		return rtnModel;
	}	
	

	
	public void updatePriceInfo(String materialId) throws Exception{
		
		B_PriceReferenceDao dao = new B_PriceReferenceDao();
		B_PriceReferenceData dt = new B_PriceReferenceData();


		//删除历史数据
		String astr_Where = "materialId = '"+materialId+"'";
		try{
			dao.RemoveByWhere(astr_Where);
		}catch (Exception e){
			//continue
		}
		
		//设置物料编码
		dt.setMaterialid(materialId);
		
		//编辑最新价格
		dt = getLastPriceInfo(dt,materialId,request);
		//dt.setLastprice(last.getPrice());
		//dt.setLastsupplierid(last.getSupplierid());
		//dt.setLastdate(last.getPricedate());
		
		//编辑最低价格
		dt = getMixPriceInfo(dt,materialId);
		
		
		//更新物料单价参考信息表
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"PriceReferenceInsert",userInfo);
		copyProperties(dt,commData);
						
		String guid = BaseDAO.getGuId();
		dt.setRecordid(guid);
		
		dao.Create(dt);
		
	}

	/**
	 * 更新供应商单价
	 */
	public void updatePriceSupplier(
			String materialId,
			String supplierId,
			String price ) throws Exception {
		
		//List<B_PriceSupplierData> priceList = null;
		B_PriceSupplierData pricedt = null;
		B_PriceSupplierDao dao = new B_PriceSupplierDao();
		B_PriceSupplierHistoryData historyDt = new B_PriceSupplierHistoryData();
		B_PriceSupplierHistoryDao historyDao = new B_PriceSupplierHistoryDao();

		String where ="materialId= '" + materialId + "'" + 
				" AND supplierId = '" + supplierId + "'" ;

		//priceList = (List<B_PriceSupplierData>)dao.Find(where);
		try {
			dao.RemoveByWhere(where);
		} catch (Exception e) {
			//
		}	
		
		pricedt = new B_PriceSupplierData();
		pricedt.setMaterialid(materialId);
		pricedt.setSupplierid(supplierId);
		pricedt.setPrice(price);
		pricedt.setPricedate(CalendarUtil.getToDay());
		
		//处理共通信息					
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,"BaseBomInsert",userInfo);
		copyProperties(pricedt,commData);
		guid = BaseDAO.getGuId();
		pricedt.setRecordid(guid);
		
		dao.Create(pricedt);		

		//更新最新,最低单价
		updatePriceInfo(materialId);
		
		//插入历史表
		//处理共通信息	
		copyProperties(historyDt,pricedt);
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,"BaseBomInsert",userInfo);
		copyProperties(historyDt,commData);		
		guid = BaseDAO.getGuId();
		historyDt.setRecordid(guid);
		
		historyDao.Create(historyDt);
	
	}
	
	/*
	 * 1.显示当前选中物料的基本信息
	 * 2.显示相关的所有子编码信息(N条数据) 
	 */
	private B_PriceReferenceData getMixPriceInfo(
			B_PriceReferenceData dt,
			String materialid) throws Exception{
		
		dataModel.setQueryName("getMinPriceByMaterialId");
		
		baseQuery = new BaseQuery(request, dataModel);

		userDefinedSearchCase.put("materialid", materialid);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		modelMap = baseQuery.getYsFullData();
		
		if(dataModel.getRecordCount() > 0){
			dt.setMinprice(dataModel.getYsViewData().get(0).get("price"));
			dt.setMinsupplierid(dataModel.getYsViewData().get(0).get("supplierId"));
			dt.setMindate(dataModel.getYsViewData().get(0).get("priceDate"));
		}
		return dt;
	}
	

	private B_PriceReferenceData getLastPriceInfo(
			B_PriceReferenceData dt,
			String materialid,
			HttpServletRequest request) throws Exception{

		dataModel.setQueryFileName("/business/material/materialquerydefine");
		dataModel.setQueryName("getLastPriceByMaterialId");
		
		baseQuery = new BaseQuery(request, dataModel);

		userDefinedSearchCase.put("materialid", materialid);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		modelMap = baseQuery.getYsFullData();
		
		if(dataModel.getRecordCount() > 0){
			dt.setLastprice(dataModel.getYsViewData().get(0).get("price"));
			dt.setLastsupplierid(dataModel.getYsViewData().get(0).get("supplierId"));
			dt.setLastdate(dataModel.getYsViewData().get(0).get("priceDate"));
		}
		return dt;
	}
	
	public Model SelectSupplier(String materialid) {

		try{

			dataModel.setQueryFileName("/business/material/materialquerydefine");
			dataModel.setQueryName("getMaterialByMaterialId");
			
			baseQuery = new BaseQuery(request, dataModel);

			userDefinedSearchCase.put("materialid", materialid);
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
			modelMap = baseQuery.getYsQueryData(0, 0);

			model.addAttribute("product",dataModel.getYsViewData().get(0));

			reqModel.setCurrencyList(util.getListOption(DicUtil.CURRENCY, ""));
			
		}catch(Exception e){
			
		}
		
		return model;
		
	}

	public MaterialModel doDeletePrice(
			String delData, UserInfo userInfo) throws Exception{
		
		MaterialModel model = new MaterialModel();
		B_PriceSupplierData data = new B_PriceSupplierData();	
		B_PriceSupplierDao dao = new B_PriceSupplierDao();	

		String materialid = request.getParameter("key");	
		
		try {	
												
			String recordid = request.getParameter("recordId");									
			data.setRecordid(recordid);

			dao.Remove(data);
			

			//更新该物料的最新价格,最低价格
			updatePriceInfo(materialid);
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return model;
	}
	

	public MaterialModel doDeletePriceHistory(
			String delData, UserInfo userInfo) throws Exception{
		
		MaterialModel model = new MaterialModel();
		B_PriceSupplierHistoryData data = new B_PriceSupplierHistoryData();	
		B_PriceSupplierHistoryDao dao = new B_PriceSupplierHistoryDao();	
													
		try {	
												
			String recordid = request.getParameter("recordId");									
			data.setRecordid(recordid);

			dao.Remove(data);
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return model;
	}
	
	
	@SuppressWarnings("unchecked")
	private B_PriceSupplierData prePriceCheck(
			String materialId,
			String supplierId) throws Exception {

		B_PriceSupplierDao dao = new B_PriceSupplierDao();
		List<B_PriceSupplierData> priceList = null;
		B_PriceSupplierData pricedt = null;

		String where = " materialId = '" + materialId +
				"' AND supplierId = '" + supplierId +
				"' AND deleteFlag = '0' ";		

		priceList = (List<B_PriceSupplierData>)dao.Find(where);
		
		if(priceList != null && priceList.size() > 0)
			pricedt = priceList.get(0);	
			
		return pricedt;
	}
	
	//更新虚拟库存
	/*
	public void updateInventory(
			B_InventoryData planData) throws Exception{
		
		B_InventoryDao dao = new B_InventoryDao();
		B_InventoryData data = null;
		
		//确认物料的库存是否存在
		data = checkInventoryExsit(planData.getMaterialid());
		
		if(data == null){
			//insert
			data = new B_InventoryData();

			copyProperties(data,planData);
			commData = commFiledEdit(Constants.ACCESSTYPE_INS,
					"AvailabelInsert",userInfo);
			copyProperties(data,commData);
			
			guid = BaseDAO.getGuId();
			data.setRecordid(guid);
			
			dao.Create(data);	
			
		}else{
			//update
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"AvailabelUpdate",userInfo);
			copyProperties(data,commData);
			
			//计算虚拟库存
			String oldAva = data.getAvailabeltopromise();
			String newAva = planData.getAvailabeltopromise();		
			float cunt =Float.parseFloat(oldAva) + Float.parseFloat(newAva);
			
			data.setAvailabeltopromise(String.valueOf(cunt));
			
			dao.Store(data);	
		}
		
	}	


	@SuppressWarnings("unchecked")
	private B_InventoryData checkInventoryExsit(
			String materialId) throws Exception {

		B_InventoryDao dao = new B_InventoryDao();
		B_InventoryData data = null;
		List<B_InventoryData> list = null;

		String where = " materialId = '" + materialId +
				"' AND deleteFlag = '0' ";		

		list = (List<B_InventoryData>)dao.Find(where);
		
		if(list != null && list.size() > 0)
			data = list.get(0);	
			
		return data;
	}
	*/
	public static float stringToFloat(String s){

		float rtn = 0;
		
		try{
			if(s == null || s.trim().equals(""))
				return rtn;
			
			rtn = Float.parseFloat(s.replace(",",""));
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		return rtn;		
	}
	
	/**
	 * 换行符转换
	 * @param mystr
	 * @return
	 */
	public String replaceTextArea(String str) {
		if (str == null || str == "") {
			return ("&nbsp;");
		} else {
			str = str.replace("\n\r", "<br>");
			str = str.replace("\r\n", "<br>");
			str = str.replace("\r", "<br>");
			str = str.replace("\t", "  ");
			//str = str.replace(" ", "&nbsp;");
			//mystr = mystr.replace("\"", "\\"+"\"");
			return (str);
		}
	}
}
