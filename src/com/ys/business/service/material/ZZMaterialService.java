package com.ys.business.service.material;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.ys.business.action.model.material.ZZMaterialModel;
import com.ys.business.db.dao.B_MaterialCategoryDao;
import com.ys.business.db.dao.B_MaterialDao;
import com.ys.business.db.dao.B_PriceSupplierDao;
import com.ys.business.db.dao.B_ZZMaterialPriceDao;
import com.ys.business.db.dao.B_ZZRawMaterialDao;
import com.ys.business.db.data.B_BomDetailData;
import com.ys.business.db.data.B_MaterialCategoryData;
import com.ys.business.db.data.B_MaterialData;
import com.ys.business.db.data.B_PriceSupplierData;
import com.ys.business.db.data.B_ZZMaterialPriceData;
import com.ys.business.db.data.B_ZZRawMaterialData;
import com.ys.business.db.data.CommFieldsData;

@Service
public class ZZMaterialService extends BaseService {

	DicUtil util = new DicUtil();

	BaseTransaction ts;

	String guid ="";
	private CommFieldsData commData;
	
	private HttpServletRequest request;
	
	private B_ZZRawMaterialData rawData;
	private B_ZZMaterialPriceData priceData;
	private B_ZZRawMaterialDao rawDao;
	private B_ZZMaterialPriceDao priceDao;
	private ZZMaterialModel reqModel;
	private UserInfo userInfo;
	private BaseModel dataModel;
	private  Model model;
	private HashMap<String, String> userDefinedSearchCase;
	private BaseQuery baseQuery;
	ArrayList<HashMap<String, String>> modelMap = null;	

	public ZZMaterialService(){
		
	}

	public ZZMaterialService(Model model,
			HttpServletRequest request,
			ZZMaterialModel reqModel,
			UserInfo userInfo){
		
		this.rawDao = new B_ZZRawMaterialDao();
		this.priceDao = new B_ZZMaterialPriceDao();
		this.rawData = new B_ZZRawMaterialData();
		this.priceData = new B_ZZMaterialPriceData();
		this.model = model;
		this.reqModel = reqModel;
		this.request = request;
		this.userInfo = userInfo;
		dataModel = new BaseModel();
		userDefinedSearchCase = new HashMap<String, String>();
		
	}
	


	public HashMap<String, Object> search( 
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
		

		dataModel.setQueryFileName("/business/material/zzmaterialquerydefine");
		dataModel.setQueryName("getZZMaterialPriceList");
		
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
			
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
	}

	/*
	 * 自制品单价基本信息insert处理
	 */
	private void insertPrice(
			B_ZZMaterialPriceData data) throws Exception{
		
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"ZZMaterialPriceInsert",userInfo);

		copyProperties(data,commData);

		guid = BaseDAO.getGuId();
		data.setRecordid(guid);
		
		priceDao.Create(data);	

	}
	
	/*
	 * 自制品原材料insert处理
	 */
	private void insertRawMaterial(
			B_ZZRawMaterialData data) throws Exception{
		
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"ZZRawMaterialInsert",userInfo);

		copyProperties(data,commData);

		guid = BaseDAO.getGuId();
		data.setRecordid(guid);
		
		rawDao.Create(data);	

	}	
	
	/*
	 * 供应商单价(耀升YS)insert处理
	 */
	private void insertSupplierPrice(
			B_ZZMaterialPriceData data) throws Exception{
		
		B_PriceSupplierDao dao = new B_PriceSupplierDao();
		B_PriceSupplierData dt = new B_PriceSupplierData();
		
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"ZZRawMaterialInsert",userInfo);
		copyProperties(dt,commData);
		
		guid = BaseDAO.getGuId();
		dt.setRecordid(guid);
		dt.setMaterialid(data.getMaterialid());
		dt.setSupplierid(Constants.SUPPLIER_YS);//耀升为供应商编号
		dt.setCurrency(Constants.CURRENCY_RMB);//人民币
		dt.setPrice(data.getTotalprice());
		dt.setPriceunit("");
		dt.setPricedate(CalendarUtil.fmtYmdDate());
		dt.setPricesource(BusinessConstants.PRICESOURCE_SUPPLIER);
		dt.setUsedflag(BusinessConstants.MATERIAL_USERD_Y);
		
		dao.Create(dt);	

	}	
	
	

	/*
	 * 1.自制品单价更新处理(一条数据)
	 * 2.自制品原材料新增/更新处理(N条数据)
	 */
	private String update() throws Exception  {

		String materialId = "";
		ts = new BaseTransaction();
		
		try {
			
			ts.begin();
						
			priceData = (B_ZZMaterialPriceData)reqModel.getPrice();
			List<B_ZZRawMaterialData> reqDataList = reqModel.getRawMaterials();
			
			//自制品单价新增处理
			updatePrice(priceData);

			//删除旧数据
			materialId = priceData.getMaterialid();
			String where = " materialId = '"+materialId +"'" ;
			deleteRawMaterial(where);
			
			if(reqDataList != null){
				//新增原材料
				for(B_ZZRawMaterialData newData:reqDataList ){
	
					if(!("").equals(newData.getRawmaterialid().trim())){
						
						newData.setMaterialid(materialId);
						insertRawMaterial(newData);		
					}
				}
			}
			//耀升自制品的物料单价表更新处理
			B_PriceSupplierData ys = getPriceSupplierBySupplierId(materialId);
			
			if(null != ys){
				
				updateSupplierPrice(ys,priceData);
				
			}else{
				insertSupplierPrice(priceData);
				
			}
			
			ts.commit();
			
		}
		catch(Exception e) {
			ts.rollback();
			System.out.print(e.toString());
		}
		
		return materialId;
	}	

	/*
	 * 自制品单价基本信息update处理
	 */
	@SuppressWarnings("unchecked")
	private void updatePrice(B_ZZMaterialPriceData reqData){
		
		B_ZZMaterialPriceData db = null ;
		List<B_ZZMaterialPriceData> dbList = null;
		try{
			String materialId = reqData.getMaterialid();
			String astr_Where = 
					" materialId = '"+materialId +"'" + 
					" AND deleteFlag = '0' ";

			dbList = (List<B_ZZMaterialPriceData>)priceDao.Find(astr_Where);
			
			if(null != dbList && dbList.size() > 0){
				
				db = dbList.get(0);
				copyProperties(db,reqData);
				commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"ZZMaterialPriceUpdate",userInfo);

				copyProperties(db,commData);

				priceDao.Store(db);
				
			}else{
				//找不到数据,新增处理
				insertPrice(reqData);
				
			}
		}catch(Exception e){
			
		}
	}
	
	
	/*
	 * 供应商单价(耀升YS)update处理
	 */
	private void updateSupplierPrice(
			B_PriceSupplierData price,
			B_ZZMaterialPriceData dt) throws Exception{
		
		B_PriceSupplierDao dao = new B_PriceSupplierDao();
		
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"ZZRawMaterialInsert",userInfo);
		copyProperties(price,commData);
		
		price.setPrice(dt.getTotalprice());
		price.setPricedate(CalendarUtil.fmtYmdDate());
		price.setPricesource(BusinessConstants.PRICESOURCE_SUPPLIER);
		price.setUsedflag(BusinessConstants.MATERIAL_USERD_Y);
		
		dao.Store(price);	

	}	
	

	
	@SuppressWarnings("unchecked")
	private B_PriceSupplierData getPriceSupplierBySupplierId(String materialId) {
		
		B_PriceSupplierDao dao = new B_PriceSupplierDao();
		B_PriceSupplierData rtnData = null;
		List<B_PriceSupplierData> dbList = null;
				
		try {
			String where = " supplierId = '"+Constants.SUPPLIER_YS +"'" + 
					" AND materialId = '"+materialId +"'" + 
					" AND deleteFlag = '0' ";

			dbList = (List<B_PriceSupplierData>)dao.Find(where);
			
			if(null != dbList && dbList.size() > 0)
				rtnData = dbList.get(0);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			rtnData = null;
		}
		
		return rtnData;
	}
	
	/*
	 * 自制品原材料删除处理
	 */
	private void deleteRawMaterial(
			String where ) {
				
		try {

			rawDao.RemoveByWhere(where);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	private Model getZZPriceDetailView(String materialId) 
			throws Exception {

		dataModel = new BaseModel();		
		dataModel.setQueryFileName("/business/material/zzmaterialquerydefine");
		dataModel.setQueryName("getZZmaterialpriceByMaterialId");
		
		baseQuery = new BaseQuery(request, dataModel);

		userDefinedSearchCase.put("materialId", materialId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		modelMap = baseQuery.getYsQueryData(0, 0);
		
		if(dataModel.getRecordCount() > 0){
			model.addAttribute("price",  modelMap.get(0));
			model.addAttribute("detail", modelMap);
		}
		return model;
	}

	public void getMaterialByMaterialId(String materialId) throws Exception{
	
		dataModel.setQueryFileName("/business/material/materialquerydefine");
		dataModel.setQueryName("getMaterialByMaterialId");
		
		baseQuery = new BaseQuery(request, dataModel);

		userDefinedSearchCase.put("materialid", materialId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		modelMap = baseQuery.getYsQueryData(0, 0);

		model.addAttribute("price",dataModel.getYsViewData().get(0));
		
	}
	/*
	 * 新增物料初始处理
	 */
	public Model createMaterial() {

		try {		
			String materialId = request.getParameter("materialId");
			
			if(null != materialId && !("").equals(materialId))
				getMaterialByMaterialId(materialId);
			
			getOptionList();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return model;
	
	}
	public void getOptionList() throws Exception {
			
		reqModel.setManageRateList(util.getListOption(DicUtil.MANAGEMENTRATE, ""));
		reqModel.setTypeList(util.getListOption(DicUtil.ZZMATERIALTYPE, ""));
	
	}

	public Model getDetailView() throws Exception {

		String materialId = request.getParameter("materialId");
		getZZPriceDetailView(materialId);
		
		return model;
		
	}
	
	public Model getDetailViewH() throws Exception {

		String materialId = request.getParameter("materialId");
		getZZPriceDetailView(materialId);
		
		return model;
		
	}
	
	public Model insertAndView() throws Exception {

		String materialId = update();
		
		getZZPriceDetailView(materialId);
		
		return model;
		
	}
	
	public Model updateAndView() throws Exception {

		String materialId = update();
		
		getZZPriceDetailView(materialId);
		
		return model;
		
	}

	public Model delete(String delData) throws Exception {
													
		try {				
			ts = new BaseTransaction();										
			ts.begin();									
			String removeData[] = delData.split(",");									
			for (String key:removeData) {	
				
				commData = commFiledEdit(Constants.ACCESSTYPE_DEL,
						"ZZMaterialPriceUpdate",userInfo);

				copyProperties(priceData,commData);
				priceData.setRecordid(key);	
				priceDao.Store(priceData);								
			}
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
		}		
		
		return model;		
	}
	
}
