package com.ys.business.service.material;

import java.net.URLDecoder;
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
import com.ys.util.DicUtil;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basedao.BaseTransaction;
import com.ys.util.basequery.BaseQuery;
import com.ys.util.basequery.common.BaseModel;
import com.ys.util.basequery.common.Constants;
import com.ys.business.action.model.common.ListOption;
import com.ys.business.action.model.material.MaterialModel;
import com.ys.business.action.model.order.BomPlanModel;
import com.ys.business.db.dao.B_BomDetailDao;
import com.ys.business.db.dao.B_BomPlanDao;
import com.ys.business.db.dao.B_MaterialDao;
import com.ys.business.db.dao.B_PriceSupplierDao;
import com.ys.business.db.dao.B_PriceSupplierHistoryDao;
import com.ys.business.db.data.B_BomDetailData;
import com.ys.business.db.data.B_BomPlanData;
import com.ys.business.db.data.B_MaterialData;
import com.ys.business.db.data.B_PriceSupplierData;
import com.ys.business.db.data.B_PriceSupplierHistoryData;
import com.ys.business.db.data.CommFieldsData;

@Service
public class MaterialService extends BaseService {

	DicUtil util = new DicUtil();

	BaseTransaction ts;

	String guid ="";
	private CommFieldsData commData;
	
	private HttpServletRequest request;
	
	private B_MaterialData bomPlanData;
	private B_BomDetailData bomDetailData;
	private B_MaterialDao dao;
	private B_BomDetailDao bomDetailDao;
	private MaterialModel reqModel;
	private UserInfo userInfo;
	private BaseModel dataModel;
	private Model model;
	private HashMap<String, String> userDefinedSearchCase;
	private BaseQuery baseQuery;
	ArrayList<HashMap<String, String>> modelMap = null;	
	HttpSession session;
	
	public MaterialService(){
		
	}

	public MaterialService(Model model,
			HttpServletRequest request,
			MaterialModel reqModel,
			UserInfo userInfo){
		
		this.dao = new B_MaterialDao();
		this.model = model;
		this.reqModel = reqModel;
		this.request = request;
		this.userInfo = userInfo;
		this.dataModel = new BaseModel();
		userDefinedSearchCase = new HashMap<String, String>();
		dataModel.setQueryFileName("/business/material/materialquerydefine");
		
	}

	public HashMap<String, Object> search(HttpServletRequest request, 
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
		
		String key1 = getJsonData(data, "keyword1").toUpperCase();
		String key2 = getJsonData(data, "keyword2").toUpperCase();
		
		dataModel.setQueryName("materialquerydefine_search");
		
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
	

	public HashMap<String, Object> supplierPriceView(HttpServletRequest request, 
			String data) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();

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
	

	public HashMap<String, Object> supplierPriceHistory() throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();

		dataModel.setQueryFileName("/business/material/materialquerydefine");
		dataModel.setQueryName("supplierPriceHistory");
		
		baseQuery = new BaseQuery(request, dataModel);

		String supplierId  = request.getParameter("supplierId");
		String materialId  = request.getParameter("materialId");

		userDefinedSearchCase.put("supplierId", supplierId);
		userDefinedSearchCase.put("materialId", materialId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, 0);	 
			
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
	}
	

	public HashMap<String, Object> categorySearch( 
			String data) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		data = URLDecoder.decode(data, "UTF-8");
		
		String key = request.getParameter("key").toUpperCase();
		
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

		dataModel.setQueryFileName("/business/material/materialquerydefine");
		dataModel.setQueryName("categorylist");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("keywords1", key);
		
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
	 * 1.显示当前选中物料的基本信息
	 * 2.显示相关的所有子编码信息(N条数据) 
	 */	
	public Model editPrice(
			String recordId,
			String materialId) {
		
		try {
			//取得产品信息
			SelectSupplier(materialId);
			//取得单价信息
			getSupplierPriceBySupplierId();
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}

		return model;
		
	}
	
	/*
	 * 1.显示当前选中物料的基本信息
	 * 2.显示相关的所有子编码信息(N条数据) 
	 */
	
	public Model view(
			String recordId,
			String parentId) {
		MaterialModel Matmodel = new MaterialModel();
		B_MaterialData FormDetail = new B_MaterialData();


		String shareModel = "";
		String unitName = "";
		
		try {
			
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

				//插入历史表
				copyProperties(historyData,reqData);
				commData = commFiledEdit(Constants.ACCESSTYPE_INS,"MaterialInsert",userInfo);

				copyProperties(historyData,commData);
				
				guid = BaseDAO.getGuId();
				historyData.setRecordid(guid);
				
				historyDao.Create(historyData);	
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
	
	/*
	 * 1.物料新增处理(一条数据)
	 * 2.子编码新增处理(N条数据)
	 */
	public Model insert() throws Exception  {

		ts = new BaseTransaction();
		
		try {
			
			ts.begin();
			
			ArrayList<B_MaterialData> dbList = new ArrayList<B_MaterialData>();
			
			B_MaterialData reqData = (B_MaterialData)reqModel.getMaterial();
			List<B_MaterialData> reqDataList = reqModel.getMaterialLines();
			
			//新增加数据时,父级ID等于物料编号
			String parentId = reqData.getMaterialid();
			String selectedRecord = "";
			
			for(B_MaterialData data:reqDataList ){
				if(!data.getSubiddes().trim().equals("")){
					dbList.add(data);
				}
			}

			//画面传来的数据过滤到list后,
			//作清空处理,用来存放查看页面的数据
			reqDataList.clear();
			
			
			//如果一条数据也没有,默认给当前产品的子编码设为 "000"
			if (dbList.size()==0){
				reqData.setSubid(BusinessConstants.MATERIAL_SUBCOD_DEF);
				dbList.add(reqData);
			}
			boolean frist = true;
			for(B_MaterialData data:dbList ){
									
				commData = commFiledEdit(Constants.ACCESSTYPE_INS,"MaterialInsert",userInfo);

				copyProperties(reqData,commData);

				String guid = BaseDAO.getGuId();
				reqData.setRecordid(guid);
				reqData.setMaterialid(parentId + "." + data.getSubid());
				reqData.setParentid(parentId);
				reqData.setSubid(data.getSubid());
				reqData.setSubiddes(data.getSubiddes());
				
				dao.Create(reqData);	
				
				//把第一条作为为默认对象
				if(frist){
					
					selectedRecord = guid;
					frist = false;
				}
			}
			ts.commit();
			
			//重新查询
			model = view(selectedRecord,parentId);
			
			reqModel.setEndInfoMap(NORMAL, "suc001", "");
		}
		catch(Exception e) {
			ts.rollback();
		}
		
		return model;
	}	

	/*
	 * 1.物料基本信息更新处理(1条数据)
	 * 2.子编码新增处理(N条数据)
	 */
	public Model update() throws Exception  {

		ts = new BaseTransaction();
		
		try {
			
			ts.begin();
			
			B_MaterialDao dao = new B_MaterialDao();
			ArrayList<B_MaterialData> viewList = new ArrayList<B_MaterialData>();
			
			B_MaterialData reqData = (B_MaterialData)reqModel.getMaterial();
			List<B_MaterialData> reqDataList = reqModel.getMaterialLines();

			//物料编码 = parentId +"."+ subid 
			//例:B01.D019001.00
			String parentId = reqData.getParentid();
			//画面被选中的数据
			String selectedRecord = reqData.getRecordid();
			
			//循环处理子编码
			for(B_MaterialData data:reqDataList ){

				String recordid = data.getRecordid();	
				
				//Recorded为空的情况,插入该数据,否则就更新
				if(recordid =="" || recordid == null){					
					
					commData = commFiledEdit(Constants.ACCESSTYPE_INS,"MaterialInsert",userInfo);

					copyProperties(reqData,commData);
									
					String guid = BaseDAO.getGuId();
					reqData.setRecordid(guid);
					reqData.setMaterialid(parentId+"."+data.getSubid());
					reqData.setParentid(parentId);	
					reqData.setSubid(data.getSubid());
					reqData.setSubiddes(data.getSubiddes());

					dao.Create(reqData);
					
					viewList.add(reqData);
					
				}else if (recordid.equals(selectedRecord)){

					//只更新被选中数据的物料编号,名称,说明,子编码解释等;				
					B_MaterialData dbData = preMaterialCheck(recordid);	

					//处理共通信息
					commData = commFiledEdit(Constants.ACCESSTYPE_UPD,"MaterialUpdate",userInfo);
					copyProperties(reqData,commData);
					
					//获取被选中数据的信息
					dbData.setMaterialid(reqData.getMaterialid());
					dbData.setMaterialname(reqData.getMaterialname());
					dbData.setCategoryid(reqData.getCategoryid());
					dbData.setSharemodel(reqData.getSharemodel());
					dbData.setDescription(reqData.getDescription());
					dbData.setUnit(reqData.getUnit());
					//获取子编码list中的子编码解释
					dbData.setSubiddes(data.getSubiddes());
					
					dao.Store(dbData);
										
				}
				
			}
			
			ts.commit();
			
			//重新查询
			model = view(selectedRecord,parentId);		
		}
		catch(Exception e) {
			ts.rollback();
		}
				
		return model;
	}	
	
	/*
	 * 1.显示当前选中物料的基本信息
	 * 2.显示相关的所有子编码信息(N条数据) 
	 */
	
	public Model SelectSupplier(String materialid) {

		try{
			
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
	
	public void getSupplierPriceBySupplierId() throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();

		String materialId = request.getParameter("materialId");
		String supplierId = request.getParameter("supplierId");

		dataModel.setQueryFileName("/business/material/materialquerydefine");
		dataModel.setQueryName("getSupplierPriceBySupplierId");
		
		baseQuery = new BaseQuery(request, dataModel);

		userDefinedSearchCase.put("materialId", materialId);
		userDefinedSearchCase.put("supplierId", supplierId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0,0);	 
		
		model.addAttribute("price", dataModel.getYsViewData().get(0));		
		
	}
	/*
	 * 新增物料初始处理
	 */
	public MaterialModel createMaterial() {

		MaterialModel model = new MaterialModel();

		try {			
			model.setUnitList(doOptionChange(DicUtil.MEASURESTYPE, "").getUnitList());
			model.setEndInfoMap("098", "0001", "");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return model;
	
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
	

	
	public MaterialModel doDelete(
			String delData, UserInfo userInfo) throws Exception{
		
		MaterialModel model = new MaterialModel();
		B_MaterialData data = new B_MaterialData();	
		B_MaterialDao dao = new B_MaterialDao();	
													
		try {	
			
			ts = new BaseTransaction();										
			ts.begin();									
			String removeData[] = delData.split(",");									
			for (String key:removeData) {									
												
				data.setRecordid(key);							
				dao.Remove(data);								
			}
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
		}
		
		return model;
	}
	
	
	private B_MaterialData preMaterialCheck(String key) throws Exception {
		B_MaterialDao dao = new B_MaterialDao();
		B_MaterialData dbData = new B_MaterialData();
				
		try {
			dbData.setRecordid(key);
			dbData = (B_MaterialData)dao.FindByPrimaryKey(dbData);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dbData = null;
		}
		
		return dbData;
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
	
}
