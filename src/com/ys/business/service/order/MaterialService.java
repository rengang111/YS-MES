package com.ys.business.service.order;

import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.system.service.common.I_BaseService;
import com.ys.util.DicUtil;
import com.ys.util.UploadReceiver;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basedao.BaseTransaction;
import com.ys.util.basequery.BaseQuery;
import com.ys.util.basequery.common.BaseModel;
import com.ys.util.basequery.common.Constants;
import com.ys.business.action.model.common.ListOption;
import com.ys.business.action.model.order.MaterialModel;
import com.ys.business.db.dao.B_MaterialCategoryDao;
import com.ys.business.db.dao.B_MaterialCostDetailDao;
import com.ys.business.db.dao.B_MaterialDao;
import com.ys.business.db.dao.B_MouldBaseInfoDao;
import com.ys.business.db.dao.B_OrderExpenseDetailDao;
import com.ys.business.db.dao.B_PriceSupplierDao;
import com.ys.business.db.dao.B_PriceSupplierHistoryDao;
import com.ys.business.db.data.B_MaterialCategoryData;
import com.ys.business.db.data.B_MaterialData;
import com.ys.business.db.data.B_MouldBaseInfoData;
import com.ys.business.db.data.B_OrderExpenseDetailData;
import com.ys.business.db.data.B_PriceReferenceData;
import com.ys.business.db.data.B_PriceSupplierData;
import com.ys.business.db.data.B_PriceSupplierHistoryData;
import com.ys.business.db.data.CommFieldsData;

@Service
public class MaterialService extends CommonService implements I_BaseService{

	DicUtil util = new DicUtil();

	BaseTransaction ts;

	String guid ="";
	private CommFieldsData commData;
	
	private HttpServletRequest request;
	
	private B_MaterialDao dao;
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
			HttpSession session,
			MaterialModel reqModel,
			UserInfo userInfo){
		
		this.dao = new B_MaterialDao();
		this.model = model;
		this.reqModel = reqModel;
		this.request = request;
		this.userInfo = userInfo;
		this.session = session;
		this.dataModel = new BaseModel();
		userDefinedSearchCase = new HashMap<String, String>();
		dataModel.setQueryFileName("/business/material/materialquerydefine");
		super.request = request;
		super.userInfo = userInfo;
		super.session = session;
		
	}

	public HashMap<String, Object> search(
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
		
		dataModel.setQueryName("materialquerydefine_search");		
		baseQuery = new BaseQuery(request, dataModel);
		
		String[] keyArr = getSearchKey(formId,data,session);
		String key1 = keyArr[0];
		String key2 = keyArr[1];
		
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
		if(notEmpty(key1) || notEmpty(key2) ){
			userDefinedSearchCase.put("purchaseType", "");
		}
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
	
	public HashMap<String, Object> getProductList(String data) throws Exception {
		
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
		
		String[] keyArr = getSearchKey(Constants.FORM_PRODUCT,data,session);
		String key1 = keyArr[0];
		String key2 = keyArr[1];

		dataModel.setQueryName("getProductList");
		
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase = new HashMap<String, String>();
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

	public HashMap<String, Object> getProductSemiList(String data) throws Exception {
		
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
		
		String[] keyArr = getSearchKey(Constants.FORM_PRODUCTSEMI,data,session);
		String key1 = keyArr[0];
		String key2 = keyArr[1];

		dataModel.setQueryName("getProductSemiList");
		
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
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
	}

	public void getProductDetail(String productId) throws Exception {

		dataModel.setQueryName("getProductList");
		
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("keyword1", productId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		baseQuery.getYsFullData();	 
		
		model.addAttribute("product", dataModel.getYsViewData().get(0));

	}
	

	public void getProductSemiDetail(String productId) throws Exception {

		dataModel.setQueryName("getProductSemiList");
		
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("keyword1", productId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		baseQuery.getYsFullData();	 
		
		model.addAttribute("product", dataModel.getYsViewData().get(0));

	}


	public HashMap<String, Object> supplierPriceView(HttpServletRequest request, 
			String data) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();

		//data = URLDecoder.decode(data, "UTF-8");
		data = convertToUTF8(data);

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
	

	public HashMap<String, Object> categorySearch(String key) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();

		dataModel.setQueryFileName("/business/material/materialquerydefine");
		dataModel.setQueryName("categorylist");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("keywords1", key);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, 0);	 
		
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
	}
	
	@SuppressWarnings("unchecked")
	public List<B_MaterialCategoryData> categorySearchMul(
			String where) throws Exception {
				
		B_MaterialCategoryDao dao = new B_MaterialCategoryDao();
		List<B_MaterialCategoryData> categoryList;
		
		String strWhere = " categoryId in(  " + where + ") " +
				" AND deleteFlag = '0' ";	

		categoryList = (List<B_MaterialCategoryData>)dao.Find(strWhere);
				
		return categoryList;
	}
	
	public HashMap<String, Object> getSupplierList2(
			HttpServletRequest request, 
			String data) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();

		String key = request.getParameter("key").toUpperCase();

		dataModel.setQueryFileName("/business/material/materialquerydefine");
		dataModel.setQueryName("getSupplierList2");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("keywords1", key);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);

		
		baseQuery.getYsFullData();	 
		
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

		String sql = baseQuery.getSql();
		String key1 = request.getParameter("supplierId");
		String[] arry = null;
		String where = "";
		if(!(key1==null || ("").equals(key1.trim()))){
			arry = key1.split(",");
			boolean flg = true;
			for(String str:arry){
				if(str ==null || str.trim().equals(""))
					continue;
				
				if(flg){
					where = where + " '" + str +"' ";						
					flg=false;
					continue;
				}
				where = where + ","+ " '" + str +"' ";		
			}

		}else{
			where = " '' ";
		}

		sql = sql.replace("#", where);
			
		baseQuery.getYsQueryData(sql);	 
		
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
	
	public Model MaterailDetail(String recordId,String parentId,String materialId){
		
		String fileName = getMaterialDetail(recordId,parentId);
		
		getFileList(fileName,materialId);
		
		return model;
		
	}
			
	/**
	 * 取得关联子编码的所有物料
	 * @param recordId
	 * @param parentId
	 * @return
	 */
	public String getMaterialDetail(
			String recordId,
			String parentId) {
		MaterialModel Matmodel = new MaterialModel();
		B_MaterialData FormDetail = new B_MaterialData();


		String fileName="";
		String shareModel = "";
		String unitName = "";
		String purchaseTypeName = "";
		
		try {
			
			dataModel.setQueryFileName("/business/material/materialquerydefine");
			dataModel.setQueryName("materialList");
			
			baseQuery = new BaseQuery(request, dataModel);
			
			userDefinedSearchCase.put("parentId", parentId);
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
						FormDetail.setPurchasetype(map.get("purchaseType"));
						FormDetail.setOriginalid(map.get("originalId"));
						Matmodel.setAttribute1(map.get("categoryId"));
						Matmodel.setAttribute2(map.get("categoryName"));
						shareModel = map.get("shareModel");
						unitName = map.get("dicName");
						purchaseTypeName = map.get("purchaseTypeName");
						fileName = map.get("image_filename");
					}
					db.setRecordid(map.get("recordId"));
					db.setSubid(map.get("subId"));
					db.setSubiddes(map.get("subIdDes"));
					db.setParentid(map.get("parentId"));
					db.setMaterialid(map.get("materialId"));
					list.add(db);
				}	
					
			}
			//选择数据明细内容
			Matmodel.setMaterial(FormDetail);
			//子编号信息
			Matmodel.setMaterialLines(list);

			//计量单位
			Matmodel.setUnitname(unitName);
			Matmodel.setPurchaseTypeName(purchaseTypeName);
			Matmodel.setUnit(FormDetail.getUnit());
			Matmodel.setPurchaseType(FormDetail.getPurchasetype());
			Matmodel.setUnitList(util.getListOption(DicUtil.MEASURESTYPE, ""));
			Matmodel.setPurchaseTypeList(util.getListOption(DicUtil.PURCHASETYPE, ""));
				
			Matmodel.setShareModelList(shareModel.split(","));
			Matmodel.setEndInfoMap("098", "0001", "");
			
		}
		catch(Exception e) {
			Matmodel.setEndInfoMap(SYSTEMERROR, "err001", parentId);
		}
		
		model.addAttribute("material", Matmodel);
		
		return fileName;
		
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
								
				//物料编码 = parentId +"."+ subid 
				//物料:G01.D018.YAT001001.000
				//分类:G01.D018.YAT001
				
				/************************/				
				String materialId = parentId + "." + data.getSubid();
				String categoryId = reqData.getCategoryid();
				
				//parentId = materialId.substring(0,materialId.length()-4);
				String serialNumber = parentId.substring( categoryId.length());					
				/************************/				
				
				//设置物料的库存类别
				String type1 = materialId.substring(0,1);
				String type3 = materialId.substring(0,3);
				String stockType ="030";//采购件
				
				switch(type1) {
				case "A":
					stockType = "010";//原材料
					if(("A13").equals(type3)){
						stockType = "011";//色粉
					}
					break;
				case "G":
					stockType = "040";//包装件
					break;
				case "I":
					stockType = "050";//成品
					break;
				case "B":
					if(("B01").equals(type3)){
						stockType = "020";//自制件
					}
					break;
				case "F":
					if(("F01").equals(type3) || 
							("F02").equals(type3) || ("F03").equals(type3) ){
						
						stockType = "020";//自制件
					}
					break;					
				}
				
				reqData.setMaterialid(materialId);
				reqData.setParentid(parentId);
				reqData.setSerialnumber(serialNumber);
				reqData.setSubid(data.getSubid());
				reqData.setSubiddes(data.getSubiddes());
				reqData.setStocktype(stockType);
				
				//编辑产品型号,完整的产品编号:I.D008.WTR001.000 或者 I.BTR.D008.WTR001.000
				reqData = editCustomerId(reqData,materialId);
				
				B_MaterialData record = preMaterialCheckById(materialId);
				if(record ==null || record.equals("")){//物料编号重复check
					
					dao.Create(reqData);
				}
				//把第一条作为为默认对象
				if(frist){
					if(record ==null || record.equals("")){
						selectedRecord = guid;	
					}else{	
						selectedRecord = record.getRecordid();				
					}
					frist = false;
				}
			}
			ts.commit();
			
			//重新查询
			getMaterialDetail(selectedRecord,parentId);
			
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
			//物料:G01.D018.YAT001001.000
			//分类:G01.D018.YAT001
			//String parentId = reqData.getParentid();
			//画面被选中的数据
			String selectedRecord = reqData.getRecordid();
			
			/************************/			
			String selectMaterialId = reqData.getMaterialid();
			String categoryId = reqData.getCategoryid();

			String selectSubId = selectMaterialId.substring(selectMaterialId.length()-3);
			String parentId = selectMaterialId.substring(0,selectMaterialId.length()-4);
			String serialNumber = parentId.substring( categoryId.length());	
			/************************/

			//判断物料编码是否更改
			B_MaterialData mater = preMaterialCheck(selectedRecord);
			
			
			if(mater!=null && mater.getMaterialid() != null && 
					!mater.getMaterialid().equals(selectMaterialId)){
			
				//物料编码更改的情况
				//循环处理子编码
				for(B_MaterialData data:reqDataList ){

					String recordid = data.getRecordid();	
					String subId = data.getSubid();
					String material = parentId + "." + subId;

					
					if(recordid ==null || recordid.equals("")){
						//插入新的数据
						commData = commFiledEdit(Constants.ACCESSTYPE_INS,"MaterialInsert",userInfo);
						copyProperties(reqData,commData);
										
						String guid = BaseDAO.getGuId();
						reqData.setRecordid(guid);
						reqData.setMaterialid(material);
						reqData.setParentid(parentId);	
						reqData.setSubid(subId);
						reqData.setSubiddes(data.getSubiddes());

						dao.Create(reqData);
						continue;
					}

					//取得DB数据
					B_MaterialData dbData = preMaterialCheck(recordid);
					
					//处理共通信息
					commData = commFiledEdit(Constants.ACCESSTYPE_UPD,"MaterialUpdate",userInfo);
					copyProperties(dbData,commData);
					
					//获取被选中数据的信息
					dbData.setMaterialid(material);//
					dbData.setParentid(parentId);//
					dbData.setSerialnumber(serialNumber);//
					dbData.setMaterialname(reqData.getMaterialname());
					dbData.setCategoryid(reqData.getCategoryid());
					dbData.setSharemodel(reqData.getSharemodel());
					dbData.setDescription(reqData.getDescription());
					dbData.setUnit(reqData.getUnit());
					dbData.setSubid(data.getSubid());//
					dbData.setPurchasetype(reqData.getPurchasetype());
					dbData = editCustomerId(dbData,material);
										
					if(data.getSubiddes() != null )
						dbData.setSubiddes(data.getSubiddes());

					dao.Store(dbData);

				}
				
				
			}else{
				
				//循环处理子编码
				boolean updataFlg = false;
				for(B_MaterialData data:reqDataList ){

					String recordid = data.getRecordid();	
					String subId = data.getSubid();
					String subDes = data.getSubiddes();
					String material = parentId + "." + subId;

					//判断是否是被选中的数据
					if(subId != "" && subDes != "" && !subId.equals(selectSubId) ){
						
						if(recordid != null && !recordid.equals(""))
							continue;
						
						//编辑状态下,新增子编码的处理
						commData = commFiledEdit(Constants.ACCESSTYPE_INS,"MaterialInsert",userInfo);
						copyProperties(reqData,commData);
										
						String guid = BaseDAO.getGuId();
						reqData.setRecordid(guid);
						reqData.setMaterialid(material);
						reqData.setParentid(parentId);	
						reqData.setSerialnumber(serialNumber);//
						reqData.setSubid(subId);
						reqData.setSubiddes(subDes);
						reqData = editCustomerId(reqData,parentId);
						
						dao.Create(reqData);
						continue;
					}
					
					//子编码修改处理
					if(updataFlg)
						continue;//只更新被修改的那条数据
					
					//取得DB数据
					B_MaterialData dbData = preMaterialCheck(recordid);
					
					if(dbData != null && !dbData.equals("")){
						
						//只更新被选中数据的物料编号,名称,说明,子编码解释等;
						//处理共通信息
						commData = commFiledEdit(Constants.ACCESSTYPE_UPD,"MaterialUpdate",userInfo);
						copyProperties(dbData,commData);
						
						//获取被选中数据的信息
						dbData.setMaterialid(material);
						dbData.setMaterialname(reqData.getMaterialname());
						dbData.setSharemodel(reqData.getSharemodel());
						dbData.setDescription(reqData.getDescription());
						dbData.setUnit(reqData.getUnit());
						dbData.setSubid(subId);//
						dbData.setSubiddes(data.getSubiddes());
						dbData.setPurchasetype(reqData.getPurchasetype());
						dbData.setOriginalid(reqData.getOriginalid());
						dbData = editCustomerId(dbData,material);

						dao.Store(dbData);
						updataFlg = true;
					}
				}
			}
			
			ts.commit();
			
			//重新查询
			getMaterialDetail(selectedRecord,parentId);		
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
			String materialid) throws Exception{
		
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
			model.setUnitList(util.getListOption(DicUtil.MEASURESTYPE, ""));
			model.setPurchaseTypeList(util.getListOption(DicUtil.PURCHASETYPE, ""));
			model.setEndInfoMap("098", "0001", "");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return model;
	
	}

	
	/*
	 * 
	 */
	public void getProductDeital() throws Exception {

		String materialId = request.getParameter("materialId");
		
		//产品基本信息
		getProductDetail(materialId);
		
		//基础BOM信息
		
		
		//客户报价信息
		
		
		//订单信息
		

	}
	
	public void getProductSemiDeital() throws Exception {

		String materialId = request.getParameter("materialId");
		
		//产品基本信息
		getProductSemiDetail(materialId);
		
		//基础BOM信息
		
		
		//客户报价信息
		
		
		//订单信息
		

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
	
	@SuppressWarnings("unchecked")
	private B_MaterialData preMaterialCheckById(
			String materialId) throws Exception {

		B_MaterialData rtn = null;
		List<B_MaterialData> priceList = null;

		String where = " materialId = '" + materialId +
				"' AND deleteFlag = '0' ";		

		priceList = (List<B_MaterialData>)dao.Find(where);
		
		if(priceList != null && priceList.size() > 0)
			rtn = priceList.get(0);	
			
		return rtn;
	}
	
	public void getFileList(String nowUseImage,String materialId) {
		UploadReceiver uploadReceiver = new UploadReceiver();
		//int arraySize = 0;
		String path = BusinessConstants.BUSINESSPHOTOPATH ;
		String dir = request.getSession().getServletContext().getRealPath("/") + path + materialId ; 
		
		//String nowUseImage = nowFileName;
		
		//ArrayList<ArrayList<String>> fileList = new ArrayList<ArrayList<String>>();
		
		String[] filenames = uploadReceiver.getFileNameList(
				dir + File.separator + BusinessConstants.BUSINESSSMALLPHOTOPATH);
		if (null != filenames && filenames.length > 0){
			
			//将当前图片放到最前
			if (!(null == nowUseImage||nowUseImage.equals(""))){	
				
				ArrayList<String> list_image = new ArrayList<>(Arrays.asList(filenames));
				
				for(String fileName:list_image) {
					if(fileName.equals(nowUseImage)) {
						list_image.remove(nowUseImage);
						break;
					}
				}
				
				list_image.add(0, nowUseImage);		
				
				filenames = new String[list_image.size()];
				int index = 0;
				for(Object fileName:list_image) {
					filenames[index++] = String.valueOf(fileName);
				}
			}			
		}
				
		reqModel.setFileNames(filenames);
		reqModel.setImageKey(materialId);
		reqModel.setPath(path);
		reqModel.setNowUseImage(nowUseImage);
		
		//return model;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setNowUseImage(String key, String fileName) throws Exception {
		
		B_MaterialData dbData = new B_MaterialData();
		
		String where =  " materialId ='" + key +"' AND deleteFlag='0'";
		
		List<B_MaterialData> list = new B_MaterialDao().Find(where);
		if(list != null && list.size() > 0){
			
			dbData = list.get(0);
			commData = commFiledEdit(
					Constants.ACCESSTYPE_UPD,"AlbumUpdate",userInfo);
			copyProperties(dbData,commData);
			dbData.setImage_filename(fileName);		
			
			new B_MaterialDao().Store(dbData);
		}

	}

	@Override
	public String getNowUseImage(String key) throws Exception {
		B_MaterialData dbData = new B_MaterialData();
		
		String nowUseImage = "";
		
		try {
			dbData.setRecordid(key);
			dbData = (B_MaterialData)dao.FindByPrimaryKey(dbData);
			nowUseImage = dbData.getImage_filename();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return nowUseImage;
	}
	
	public void materialCostView() throws Exception {		

		String materialid = request.getParameter("materialId");
		
		//基本信息	
		dataModel.setQueryName("getMaterialByMaterialId");		
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("materialid", materialid);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		modelMap = baseQuery.getYsFullData();

		model.addAttribute("material",dataModel.getYsViewData().get(0));

	}
	
	public Model insertMaterialCost(String data) throws Exception
	{
	  String type = this.request.getParameter("type");
	  String counter1 = getJsonData(data, "counter1");
	  String counter5 = getJsonData(data, "counter5");

	  switch (type){
		  case "M"://材料成本
			  insertCost1(data, 1, counter1, type); break;
		  case "P"://加工描述
			  insertCost1(data, 5, counter5, type); break;
	  }

	  	return this.model;
	}
	
	private String insertCost1(String data, int index, String counter, String type)
			  throws Exception{
		
	  if ((data == null) || (data.trim() == "")) return null;

	  String ysid = "";
	  String materialId = "";
	  this.ts = new BaseTransaction();
	  try
	  {
	    this.ts.begin();

	    B_OrderExpenseDetailData order = new B_OrderExpenseDetailData();

	    materialId = request.getParameter("materialId");
	    String where = " materialId = '" + materialId + 
	      "' AND type = '" + type + 
	      "' AND deleteFlag = " + "0";
	    deleteDocumentary(where);

	    int counterInt = 0;
	    if (notEmpty(counter)) {
	      counterInt = Integer.parseInt(counter);
	    }
	    for (int i = 0; i < counterInt; ++i) {

	    	String name 	  = getJsonData(data, "documentaryLines" + index + "[" + i + "].costname");
	    	String supplierid = getJsonData(data, "documentaryLines" + index + "[" + i + "].supplierid");
	    	String contractid = getJsonData(data, "documentaryLines" + index + "[" + i + "].contractid");
		    String cost 	  = getJsonData(data, "documentaryLines" + index + "[" + i + "].cost");
		    String person     = getJsonData(data, "documentaryLines" + index + "[" + i + "].person");
		    String date 	  = getJsonData(data, "documentaryLines" + index + "[" + i + "].quotationdate");
		    String remarks 	  = getJsonData(data, "documentaryLines" + index + "[" + i + "].remarks");

		    if (isNullOrEmpty(name))
		    	continue;
	      
		    float fcost = stringToFloat(cost);
		    //fcost = Math.abs(fcost);//防止页面输入负数，方便计算，统一成正数。
		    
			  order.setYsid(ysid);
			  order.setSupplierid(supplierid);
			  order.setContractid(contractid);
			  order.setCostname(name);
			  order.setCost(floatToString(fcost));
			  order.setPerson(person);
			  order.setQuotationdate(date);
			  order.setRemarks(remarks);
			  order.setType(type);
			  order.setStatus("0");
			  insertDocumentary(order);
	    }

	    	this.ts.commit();
	  }
	  catch (Exception e) {
		  e.printStackTrace();
		  this.ts.rollback();
	  }

	  return ysid;
	}
	

	private void insertDocumentary(
			B_OrderExpenseDetailData detailData)throws Exception{

		this.commData = commFiledEdit(Constants.ACCESSTYPE_INS, 
				"DocumentaryInsert", userInfo);
		copyProperties(detailData, commData);
		guid = BaseDAO.getGuId();
		detailData.setRecordid(guid);

		new B_OrderExpenseDetailDao().Create(detailData);
	}
	
	private void deleteDocumentary(String where){

		try {
			new B_MaterialCostDetailDao().RemoveByWhere(where);
		}catch (Exception e){
			//e.printStackTrace();
		}
	}
		
}
