package com.ys.business.service.customeraddr;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.stereotype.Service;

import com.ys.business.action.model.customeraddr.CustomerAddrModel;
import com.ys.business.db.dao.B_CustomerAddrDao;
import com.ys.business.db.data.B_CustomerAddrData;
import com.ys.business.ejb.BusinessDbUpdateEjb;
import com.ys.system.action.model.login.UserInfo;
import com.ys.util.basequery.common.BaseModel;
import com.ys.system.common.BusinessConstants;
import com.ys.system.service.common.BaseService;
import com.ys.util.CalendarUtil;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basequery.BaseQuery;

import javax.servlet.http.HttpServletRequest;

@Service
public class CustomerAddrService extends BaseService {
 
	public HashMap<String, Object> doSearch(HttpServletRequest request, String data, UserInfo userInfo) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> rtnData = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		int iStart = 0;
		int iEnd =0;
		String sEcho = "";
		String start = "";
		String length = "";
		String key = "";
		
		data = URLDecoder.decode(data, "UTF-8");

		key = getJsonData(data, "keyBackup");
		if (key.equals("")) {
			key = DUMMYKEY;
		}
		sEcho = getJsonData(data, "sEcho");	
		start = getJsonData(data, "iDisplayStart");		
		if (start != null && !start.equals("")){
			iStart = Integer.parseInt(start);			
		}
		
		length = getJsonData(data, "iDisplayLength");
		if (length != null && !length.equals("")){			
			iEnd = iStart + Integer.parseInt(length);			
		}		
		
		dataModel.setQueryFileName("/business/customeraddr/customeraddrquerydefine");
		dataModel.setQueryName("customeraddrquerydefine_search");
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("keyword", key);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(iStart, iEnd);	
		//ArrayList<HashMap<String, String>> dbData1 = dataModel.getYsViewData()
		if ( iEnd > dataModel.getYsViewData().size()){
			
			iEnd = dataModel.getYsViewData().size();
			
		}
		
		modelMap.put("sEcho", sEcho); 
		
		modelMap.put("recordsTotal", dataModel.getRecordCount()); 
		
		modelMap.put("recordsFiltered", dataModel.getRecordCount());
		
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;		

	}

	public CustomerAddrModel doAddInit(HttpServletRequest request) {

		CustomerAddrModel model = new CustomerAddrModel();

		try {
			String key = request.getParameter("key");
			model.setCustomerId(key);
			model.setEndInfoMap("098", "0001", "");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return model;
	
	}

	public CustomerAddrModel doAdd(HttpServletRequest request, String data, UserInfo userInfo) {

		CustomerAddrModel model = new CustomerAddrModel();
		try {
			B_CustomerAddrDao dao = new B_CustomerAddrDao();
			B_CustomerAddrData dbData = new B_CustomerAddrData();

			String guid = BaseDAO.getGuId();
			dbData.setId(guid);
			dbData.setCustomerid(getJsonData(data, "customerId"));
			dbData.setTitle(getJsonData(data, "title"));
			dbData.setAddress(getJsonData(data, "address"));
			dbData.setPostcode(getJsonData(data, "postcode"));
			dbData.setMemo(getJsonData(data, "memo"));
			
			dbData = updateModifyInfo(dbData, userInfo);
			dao.Create(dbData);
			model.setEndInfoMap(NORMAL, "", guid);
		}
		catch(Exception e) {
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return model;
	}	
	
	public CustomerAddrModel doUpdate(HttpServletRequest request, String data, UserInfo userInfo) {
		CustomerAddrModel model = new CustomerAddrModel();
		String id = getJsonData(data, "keyBackup");
		
		try {
			B_CustomerAddrDao dao = new B_CustomerAddrDao();
			B_CustomerAddrData dbData = new B_CustomerAddrData();
			
			dbData.setId(getJsonData(data, "keyBackup"));
			dbData.setCustomerid(getJsonData(data, "customerId"));
			dbData.setTitle(getJsonData(data, "title"));
			dbData.setAddress(getJsonData(data, "address"));
			dbData.setPostcode(getJsonData(data, "postcode"));
			dbData.setMemo(getJsonData(data, "memo"));
			
			dbData = updateModifyInfo(dbData, userInfo);
			dao.Store(dbData);
			model.setEndInfoMap(NORMAL, "", id);
		}
		catch(Exception e) {
			model.setEndInfoMap(SYSTEMERROR, "err001", id);
		}
		
		return model;
	}
	
	public CustomerAddrModel doDelete(String data, UserInfo userInfo){
		
		CustomerAddrModel model = new CustomerAddrModel();
		
		try {
			BusinessDbUpdateEjb bean = new BusinessDbUpdateEjb();
	        
	        bean.executeCustomerAddrDelete(data, userInfo);
	        
	        model.setEndInfoMap(NORMAL, "", "");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return model;
	}
	
	public static B_CustomerAddrData updateModifyInfo(B_CustomerAddrData data, UserInfo userInfo) {
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
	
	public CustomerAddrModel getCustomerAddrDetailInfo(String key) throws Exception {
		CustomerAddrModel model = new CustomerAddrModel();
		B_CustomerAddrDao dao = new B_CustomerAddrDao();
		B_CustomerAddrData dbData = new B_CustomerAddrData();
		dbData.setId(key);
		dbData = (B_CustomerAddrData)dao.FindByPrimaryKey(dbData);
		model.setCustomerAddrData(dbData);
		model.setCustomerId(dbData.getCustomerid());
		
		model.setEndInfoMap("098", "0001", "");
		model.setKeyBackup(dbData.getId());
		
		return model;
	}

}
