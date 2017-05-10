package com.ys.business.service.material;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ys.business.action.model.common.ListOption;
import com.ys.business.action.model.material.ArrivalModel;
import com.ys.business.action.model.material.CustomerModel;
import com.ys.business.db.dao.B_ArrivalDao;
import com.ys.business.db.dao.B_CustomerDao;
import com.ys.business.db.dao.B_InventoryDao;
import com.ys.business.db.data.B_ArrivalData;
import com.ys.business.db.data.B_CustomerData;
import com.ys.business.db.data.B_MaterialData;
import com.ys.business.db.data.CommFieldsData;
import com.ys.business.ejb.BusinessDbUpdateEjb;
import com.ys.business.service.common.BusinessService;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.util.basequery.common.BaseModel;
import com.ys.util.basequery.common.Constants;
import com.ys.system.service.common.BaseService;
import com.ys.util.CalendarUtil;
import com.ys.util.DicUtil;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basedao.BaseTransaction;
import com.ys.util.basequery.BaseQuery;

import javax.servlet.http.HttpServletRequest;

@Service
public class ArrivalService extends BaseService {
 
	DicUtil util = new DicUtil();
	BaseTransaction ts;

	String guid ="";
	private CommFieldsData commData;
	
	private HttpServletRequest request;
	
	private B_ArrivalDao dao;
	private ArrivalModel reqModel;
	private UserInfo userInfo;
	private BaseModel dataModel;
	private  Model model;
	private HashMap<String, String> userDefinedSearchCase;
	private BaseQuery baseQuery;
	HashMap<String, Object> modelMap = null;	

	public ArrivalService(){
		
	}

	public ArrivalService(Model model,
			HttpServletRequest request,
			ArrivalModel reqModel,
			UserInfo userInfo){
		
		this.dao = new B_ArrivalDao();
		this.model = model;
		this.reqModel = reqModel;
		this.request = request;
		this.userInfo = userInfo;
		dataModel = new BaseModel();
		modelMap = new HashMap<String, Object>();
		userDefinedSearchCase = new HashMap<String, String>();
		dataModel.setQueryFileName("/business/material/inventoryquerydefine");
		
	}
	public HashMap<String, Object> doSearch( String data) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		int iStart = 0;
		int iEnd =0;
		String sEcho = "";
		String start = "";
		String length = "";
		String key1 = "";
		String key2 = "";
		
		data = URLDecoder.decode(data, "UTF-8");

		key1 = getJsonData(data, "keyword1").toUpperCase();
		key2 = getJsonData(data, "keyword2").toUpperCase();
		
		sEcho = getJsonData(data, "sEcho");	
		start = getJsonData(data, "iDisplayStart");		
		if (start != null && !start.equals("")){
			iStart = Integer.parseInt(start);			
		}
		
		length = getJsonData(data, "iDisplayLength");
		if (length != null && !length.equals("")){			
			iEnd = iStart + Integer.parseInt(length);			
		}		
		
		dataModel.setQueryName("getArrivaList");
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(iStart, iEnd);	
		
		dataModel.setYsViewData(makeAddress(dataModel.getYsViewData()));
		
		if ( iEnd > dataModel.getYsViewData().size()){
			
			iEnd = dataModel.getYsViewData().size();
			
		}
		
		modelMap.put("sEcho", sEcho); 
		
		modelMap.put("recordsTotal", dataModel.getRecordCount()); 
		
		modelMap.put("recordsFiltered", dataModel.getRecordCount());
		
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;		

	}

	public void addInit() {

		//取得到货编号"yyMMdd01"
		getArriveId();
	
	}

	public void insertArrival() {

		String arrivalId = insertAndView();
		getArrivaRecord(arrivalId);
	}
	
	private String insertAndView(){
		String arrivalId = "";
		ts = new BaseTransaction();
		
		
		try {
			ts.begin();
			
			B_ArrivalData reqData = (B_ArrivalData)reqModel.getArrival();
			List<B_ArrivalData> reqDataList = reqModel.getArrivalList();
			
			//删除旧数据
			arrivalId = reqData.getArrivalid();
			deleteArrivalById(arrivalId);
			
			for(B_ArrivalData data:reqDataList ){
				String contract = data.getContractid();
				if(contract == null || contract.equals(""))
					continue;
				
				commData = commFiledEdit(Constants.ACCESSTYPE_INS,
						"ArrivalInsert",userInfo);

				copyProperties(data,commData);

				String guid = BaseDAO.getGuId();
				data.setRecordid(guid);
				data.setArrivalid(arrivalId);
				data.setUserid(userInfo.getUserId());
				data.setArrivedate(reqData.getArrivedate());
				data.setStatus(Constants.ARRIVERECORD_0);//未报检
				
				dao.Create(data);			
			
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
		
		return arrivalId;
	}
	
	private void deleteArrivalById(String arrivalId) {

		String where = " arrivalId = '"+arrivalId+"' AND deleteFlag='0' ";
		try {
			dao.RemoveByWhere(where);
		} catch (Exception e) {
			// nothing
		}
		
	}
	
	private void getArrivaRecord(String arrivalId){
		
		try {
			dataModel.setQueryName("getArrivaRecord");
			userDefinedSearchCase.put("arrivalId", arrivalId);
			baseQuery = new BaseQuery(request, dataModel);
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
			baseQuery.getYsFullData();

			model.addAttribute("arrival",dataModel.getYsViewData().get(0));
			model.addAttribute("arrivalList",dataModel.getYsViewData());
			
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}
	@SuppressWarnings("unchecked")
	private B_CustomerData preCheckId(B_CustomerData  reqData) 
			throws Exception {
		List<B_CustomerData> dbData = null;
		B_CustomerData dt = null;
		String customerId = reqData.getCustomerid();
		String where = " customerId = '" + customerId +"' AND deleteFlag = '0' ";
		
		try {
			dbData = (List<B_CustomerData>)dao.Find(where);
			
			if(dbData.size() > 0)
				dt = dbData.get(0);

		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dt = null;
		}
		
		return dt;
	}
	
	public CustomerModel doOptionChange(String type, String parentCode) {
		
		CustomerModel model = new CustomerModel();
		
		try {
			ArrayList<ListOption> optionList = util.getListOption(type, parentCode);
			model.setUnsureList(optionList);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
			model.setUnsureList(null);
		}
		
		return model;
	}
	
	public CustomerModel doDelete(String data, UserInfo userInfo){
		
		CustomerModel model = new CustomerModel();
		
		try {
			BusinessDbUpdateEjb bean = new BusinessDbUpdateEjb();
	        
	        bean.executeCustomerDelete(data, userInfo);
	        
	        model.setEndInfoMap(NORMAL, "", "");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return model;
	}
	
	private ArrayList<HashMap<String, String>> arrangeUserList(ArrayList<HashMap<String, String>> data) {
		ArrayList<String> userList = new ArrayList<String>();
		HashMap<String, String>rowDataBackup = null;
		HashMap<String, String>rowData = null;
		ArrayList<HashMap<String, String>> rtnData = new ArrayList<HashMap<String, String>>();
		
		for(int i = 0; i < data.size(); i++) {
			rowData = data.get(i);
			String userName = rowData.get("userName");
			if (rowDataBackup == null) {
				rowDataBackup = rowData;
			}
			if (rowData.get("id").equals(rowDataBackup.get("id"))) {
				if (userName != null && !userName.equals("")) {
					userList.add(userName);
				}
			} else {
				rowDataBackup.put("userName", getUserList(userList));
				rtnData.add(rowDataBackup);
				rowDataBackup = rowData;
				userList = new ArrayList<String>();
				if (userName != null && !userName.equals("")) {
					userList.add(userName);
				}

			}
		}
		
		if (rowDataBackup != null) {
			rowData.put("userName", getUserList(userList));
			rtnData.add(rowDataBackup);
		}

		
		return rtnData;
		
	}
	
	public static B_CustomerData updateModifyInfo(B_CustomerData data, UserInfo userInfo) {
		String createUserId = data.getCreateperson();
		if ( createUserId == null || createUserId.equals("")) {
			data.setCreateperson(userInfo.getUserId());
			data.setCreatetime(CalendarUtil.fmtDate());
			data.setCreateunitid(userInfo.getUnitId());
			//data.setDeptguid(userInfo.getDeptGuid());
		}
		data.setModifyperson(userInfo.getUserId());
		data.setModifytime(CalendarUtil.fmtDate());
		data.setDeleteflag(BusinessConstants.DELETEFLG_UNDELETE);
		
		return data;
	}
	private ArrayList<HashMap<String, String>> makeAddress(ArrayList<HashMap<String, String>> data) {

		ArrayList<HashMap<String, String>> rtnData = new ArrayList<HashMap<String, String>>();
		
		for(HashMap<String, String>rowData:data) {
			rowData.put("fullAddress", rowData.get("countryName") + rowData.get("provinceName") + rowData.get("cityName") + rowData.get("address"));
			rtnData.add(rowData);
		}
		
		return rtnData;
	}
	
	private boolean isDataExist(B_CustomerData dbData) {
		boolean rtnValue = false;
		
		try {
			B_CustomerDao dao = new B_CustomerDao();
			dao.FindByPrimaryKey(dbData);
			rtnValue = true;
		}
		catch(Exception e) {
			
		}
		return rtnValue;
		
	}
	
	public void getArriveId() {

		try {
			String key = CalendarUtil.fmtYmdDate();
			dataModel.setQueryName("getMAXArrivalId");
			baseQuery = new BaseQuery(request, dataModel);
			userDefinedSearchCase.put("arriveDate", key);
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);			
			baseQuery.getYsFullData();	
			
			String code = dataModel.getYsViewData().get(0).get("MaxSubId");		
			
			model.addAttribute("arrivalId",
					BusinessService.getArriveRecordId(code));
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			reqModel.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
	}
	
	public Model getCustomerByRecordId(String key) throws Exception {

		dataModel.setQueryName("getCustomerByRecordId");
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("recordId", key);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		baseQuery.getYsQueryData(0, 0);		

		model.addAttribute("customer",dataModel.getYsViewData().get(0));
		
		reqModel.setKeyBackup(key);
		
		return model;
		
	}
	
	public Model getCustomerDetail(String key) throws Exception {
		
		B_CustomerData dbData = new B_CustomerData();
		
		try {
			dbData.setRecordid(key);
			dbData = (B_CustomerData)dao.FindByPrimaryKey(dbData);

			//reqModel.setCustomer(dbData);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dbData = null;
		}
		
		reqModel.setKeyBackup(key);
		
		return model;
		
	}
	
	private ArrayList<ArrayList<String>> preCheckCustomerId(HttpServletRequest request, String key) throws Exception {
		
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		dataModel.setQueryFileName("/business/customer/customerquerydefine");
		dataModel.setQueryName("customerquerydefine_preCheck");
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("keyword", key);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		return baseQuery.getQueryData();
					
	}
	
	private String getUserList(ArrayList<String>userList) {
		String viewUserList = "";
		
		for(String user:userList) {
			viewUserList += "," + user;
		}
		
		if (viewUserList.length() > 0) {
			viewUserList = viewUserList.substring(1, viewUserList.length());
		}
		
		return viewUserList;
	}
	
	public HashMap<String, Object> getCustomerId() throws Exception{
		
		String parentId = request.getParameter("parentId");
		parentId = convertToUTF8(parentId);
		
		dataModel.setQueryName("getCustomerSubId");
		userDefinedSearchCase.put("parentId", parentId);
		baseQuery = new BaseQuery(request, dataModel);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, 0);
		
		//取得已有的最大流水号
		int code =Integer.parseInt(dataModel.getYsViewData().get(0).get("MaxSubId"));
				
		String subid = BusinessService.getFormatCode(code, true);
		
		modelMap.put("subId",subid);
		
		return modelMap;
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> shortNameCheck() throws Exception{
		
		String ExFlag = "";
		String shortName = request.getParameter("shortName");
		shortName = convertToUTF8(shortName).toUpperCase();
		List<B_CustomerData> list = null;
		String astr_Where = "UPPER(shortName) = '"+ shortName + "' " +
							" AND deleteFlag = '0' ";
		list = (List<B_CustomerData>)dao.Find(astr_Where);
	
		if(list != null && list.size() > 0){
			ExFlag = "1";
		}		
		
		modelMap.put("ExFlag",ExFlag);
		
		return modelMap;
	}

}
