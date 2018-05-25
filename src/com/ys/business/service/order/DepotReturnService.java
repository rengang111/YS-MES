package com.ys.business.service.order;

/**
 * 仓库退货
 */
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
import com.ys.business.action.model.common.ListOption;
import com.ys.business.action.model.order.ContactModel;
import com.ys.business.action.model.order.DepotReturnModel;
import com.ys.business.action.model.order.OrganModel;
import com.ys.business.db.dao.B_ArrivalDao;
import com.ys.business.db.dao.B_ContactDao;
import com.ys.business.db.dao.B_InspectionReturnDao;
import com.ys.business.db.dao.B_MaterialDao;
import com.ys.business.db.dao.B_OrganizationDao;
import com.ys.business.db.data.B_ArrivalData;
import com.ys.business.db.data.B_ContactData;
import com.ys.business.db.data.B_InspectionReturnData;
import com.ys.business.db.data.B_MaterialData;
import com.ys.business.db.data.B_OrganizationData;
import com.ys.business.db.data.CommFieldsData;
import com.ys.business.service.common.BusinessService;

@Service
public class DepotReturnService extends CommonService {
	
	DicUtil util = new DicUtil();
	BaseTransaction ts;

	String guid ="";
	private CommFieldsData commData;
	
	private HttpServletRequest request;
	
	private B_ArrivalDao dao;
	private DepotReturnModel reqModel;
	private UserInfo userInfo;
	private BaseModel dataModel;
	private Model model;
	private HashMap<String, String> userDefinedSearchCase;
	private BaseQuery baseQuery;
	HashMap<String, Object> modelMap = null;
	HttpSession session;	

	public DepotReturnService(){
		
	}

	public DepotReturnService(Model model,
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			DepotReturnModel reqModel,
			UserInfo userInfo){
		
		this.dao = new B_ArrivalDao();
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

	public HashMap<String, Object> Init(HttpServletRequest request, String data) {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		try {
			data = URLDecoder.decode(data, "UTF-8");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		int iStart = 0;
		int iEnd =0;
		String sEcho = getJsonData(data, "sEcho");	
		String start = getJsonData(data, "iDisplayStart");		
		if (start != null && !start.equals("")){
			iStart = Integer.parseInt(start);			
		}
		
		String length = getJsonData(data, "iDisplayLength");
		if (length != null){			
			iEnd = iStart + Integer.parseInt(length);			
		}		
				
		BaseModel dataModel = new BaseModel();

		dataModel.setQueryFileName("/yssample/yssamplequerydefine");
		dataModel.setQueryName("yssamplequerydefine_search");
		try {
			BaseQuery baseQuery = new BaseQuery(request, dataModel);
			baseQuery.getYsQueryData(iStart, iEnd);	
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		if ( iEnd > dataModel.getYsViewData().size()){
			
			iEnd = dataModel.getYsViewData().size();
			
		}		
		
		modelMap.put("sEcho", sEcho); 
		
		modelMap.put("recordsTotal", dataModel.getRecordCount()); 
		
		modelMap.put("recordsFiltered", dataModel.getRecordCount());
		
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
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
		String[] keyArr = getSearchKey(formId,data,session);
		String key1 = keyArr[0];
		String key2 = keyArr[1];

		dataModel.setQueryName("getDepotReturnList");
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
		modelMap.put("keyword1",key1);
		modelMap.put("keyword2",key2);
		return modelMap;
	}
	
	public OrganModel insert(HttpServletRequest request, String data, UserInfo userInfo) {

		OrganModel model = new OrganModel();
		try {
			B_OrganizationDao dao = new B_OrganizationDao();
			B_OrganizationData dbData = new B_OrganizationData();

			String guid = BaseDAO.getGuId();
			dbData.setRecordid(guid);
			dbData.setType(getJsonData(data, "type"));
			dbData.setNo(getJsonData(data, "no"));
			dbData.setShortname(getJsonData(data, "shortName"));
			dbData.setFullname(getJsonData(data, "fullName"));
			dbData.setAddress(getJsonData(data, "address"));
	
			
			//插入机构信息表
			commData = commFiledEdit(Constants.ACCESSTYPE_INS,"OrganInsert",userInfo);

			copyProperties(dbData,commData);
			
			dao.Create(dbData);
			
			model.setEndInfoMap(NORMAL, "suc001", "");
			
		}
		catch(Exception e) {
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return model;
	}	
	
	public void createDepotReturnInit() {

		
	
	}
	
	
	public OrganModel doOptionChange(String type, String parentCode) {
		DicUtil util = new DicUtil();
		OrganModel model = new OrganModel();
		
		try {
			ArrayList<ListOption> optionList = util.getListOption(type, parentCode);
			model.setTypeList(optionList);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
			model.setTypeList(null);
		}
		
		return model;
	}

	
	
	public void doDelete() throws Exception{
		

		String recordid = request.getParameter("recordid");	
			
		B_InspectionReturnData data = new B_InspectionReturnData();
		
		data.setRecordid(recordid);

		data = (B_InspectionReturnData) new B_InspectionReturnDao().FindByPrimaryKey(data);
		if(data == null || ("").equals(data))
			return;
		
		//更新DB
		commData = commFiledEdit(Constants.ACCESSTYPE_DEL,
				"DepotReturnDelete",userInfo);
		copyProperties(data,commData);
		
		new B_InspectionReturnDao().Store(data);
		
		
	}
	
	
	public ContactModel getContactDetailInfo(String key) throws Exception {
		ContactModel model = new ContactModel();
		B_ContactDao dao = new B_ContactDao();
		B_ContactData dbData = new B_ContactData();
		dbData.setId(key);
		dbData = (B_ContactData)dao.FindByPrimaryKey(dbData);
		model.setContactData(dbData);
		model.setCompanyCode(dbData.getCompanycode());
		
		model.setSexList(doOptionChange(DicUtil.ORGANTYPE, "").getTypeList());
		
		model.setEndInfoMap("098", "0001", "");
		model.setKeyBackup(dbData.getId());
		
		return model;
	}
	
	public OrganModel getOrganBaseInfo(String key) throws Exception {
		OrganModel model = new OrganModel();
		B_OrganizationDao dao = new B_OrganizationDao();
		B_OrganizationData dbData = new B_OrganizationData();
		dbData.setRecordid(key);
		dbData = (B_OrganizationData)dao.FindByPrimaryKey(dbData);
		model.setOrganData(dbData);
		
		model.setTypeList(doOptionChange(DicUtil.ORGANTYPE, "").getTypeList());
		
		model.setEndInfoMap("098", "0001", "");
		model.setKeyBackup(dbData.getRecordid());
		
		return model;
		
	}
	

	public void update() throws Exception {
		
		B_InspectionReturnData  depotRt = reqModel.getDepotReturn();
		
		ts = new BaseTransaction();
		try{
			ts.begin();

			String oldQuantity = updateReceivInspection(depotRt);
			String newQuantity = depotRt.getReturnquantity();
			updateMaterial(
					depotRt.getMaterialid(),
					newQuantity,
					oldQuantity
					);
			
			ts.commit();
			
		}catch(Exception e){
			e.printStackTrace();
			ts.rollback();
		}
		getDepotReturnDetail(depotRt.getInspectionreturnid());
		
	}
	
	public HashMap<String, Object> getContractDetail(String data) throws Exception {

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
		if (length != null){			
			iEnd = iStart + Integer.parseInt(length);		
		}	
		String key1 = getJsonData(data, "keyword1").trim().toUpperCase();
		String key2 = getJsonData(data, "keyword2").trim().toUpperCase();
				
		BaseModel dataModel = new BaseModel();
		BaseQuery baseQuery = new BaseQuery(request, dataModel);

		dataModel.setQueryFileName("/business/order/purchasequerydefine");
		dataModel.setQueryName("getContractList");
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		String sql = baseQuery.getSql();
		String having = "1=1";
		sql = sql.replace("#", having);
		baseQuery.getYsQueryData(sql,having,iStart, iEnd);	
		
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
	
	public void insert() throws Exception{
		
		B_InspectionReturnData  depotRt = reqModel.getDepotReturn();
				
		depotRt = getReceiveInspectionId(depotRt);
		ts = new BaseTransaction();
		try{
			ts.begin();

			insertReceivInspection(depotRt);
			
			updateMaterial(
					depotRt.getMaterialid(),
					depotRt.getReturnquantity(),
					"0"
					);
			
			ts.commit();
			
		}catch(Exception e){
			e.printStackTrace();
			ts.rollback();
					}
		getDepotReturnDetail(depotRt.getInspectionreturnid());
		
	}
	

	public void edit() throws Exception{
		
		String depotId = request.getParameter("inspectionReturnId");				

		getDepotReturnDetail(depotId);
		
	}
	
	public void getDepotReturnDetail(String depotId) throws Exception {
		
		dataModel.setQueryName("getDepotReturnList");
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("depotId", depotId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);			
		baseQuery.getYsFullData();	
		
		model.addAttribute("depot",dataModel.getYsViewData().get(0));
	}
	

	public B_InspectionReturnData getReceiveInspectionId(B_InspectionReturnData data) throws Exception {
	
		String parentid = data.getContractid();
		dataModel.setQueryName("getMAXInspectionReturnId");
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("parentId", parentid);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);			
		baseQuery.getYsFullData();	
		//查询出的流水号已经在最大值上 " 加一 "了
		String code = dataModel.getYsViewData().get(0).get("MaxSubId");		
		
		String inspectionId = 
				BusinessService.getInspectionReturnId(parentid,code,false);
		
		data.setInspectionreturnid(inspectionId);
		data.setParentid(parentid);
		data.setSubid(code);
		
		return data;		
	}
	
	private void insertReceivInspection(
			B_InspectionReturnData data) throws Exception{
					
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"DepotReturnInsert",userInfo);
		copyProperties(data,commData);
		guid = BaseDAO.getGuId();
		data.setRecordid(guid);
		data.setRemarks(replaceTextArea(data.getRemarks()));
		data.setReturndate(CalendarUtil.fmtDate());
		data.setStatus(Constants.INSPECTIONRETURN_STS_2);//已处理
		data.setReturntype(Constants.RETURNTYPE_2);//入库退货
		
		new B_InspectionReturnDao().Create(data);
	}
	
	//更新当前库存:出库时，减少“当前库存”
	@SuppressWarnings("unchecked")
	private void updateMaterial(
			String materialId,
			String newQuantity,
			String oldQuantity) throws Exception{
	
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
		//考虑到退货的更新处理，库存=库存-本次退货+修改前的退货数（还原）
		float iNewQuantiy = iQuantity - stringToFloat(newQuantity) + stringToFloat(oldQuantity);		
		//待入库
		float waitstockin = stringToFloat(data.getWaitstockin());
		//待出库
		float istockout = stringToFloat(data.getWaitstockout());		
		//float iNewStockOut = istockout - reqQuantity;
		
		//虚拟库存=当前库存 + 待入库 - 待出库
		float availabeltopromise = iNewQuantiy + waitstockin - istockout;
		
		data.setQuantityonhand(String.valueOf(iNewQuantiy));
		//data.setWaitstockout(String.valueOf(iNewStockOut));
		data.setAvailabeltopromise(String.valueOf(availabeltopromise));
		
		//更新DB
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"DepotReturnUpdate",userInfo);
		copyProperties(data,commData);
		
		dao.Store(data);
		
	}	
	
	public void getDepotRentunDeital() throws Exception{
		
		String depotId = request.getParameter("inspectionReturnId");				

		getDepotReturnDetail(depotId);
		
	}
	
	private String updateReceivInspection(
			B_InspectionReturnData data) throws Exception{
					
		String quantity = "0";
		B_InspectionReturnData db = 
				(B_InspectionReturnData) new B_InspectionReturnDao().FindByPrimaryKey(data);
		
		if(db == null || ("").equals(db))
			return quantity;

		quantity = db.getReturnquantity();//更新前的退货数量
		
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"DepotReturnInsert",userInfo);
		copyProperties(db,commData);

		db.setReturnquantity(data.getReturnquantity());
		db.setRemarks(replaceTextArea(data.getRemarks()));
		db.setStatus(Constants.INSPECTIONRETURN_STS_2);//已处理
		db.setReturntype(Constants.RETURNTYPE_2);//仓库退货
		
		new B_InspectionReturnDao().Store(db);
		
		
		return quantity;
	}
}
