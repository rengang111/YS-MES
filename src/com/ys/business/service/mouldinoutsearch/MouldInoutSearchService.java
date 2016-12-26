
package com.ys.business.service.mouldinoutsearch;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.ys.business.action.model.common.ListOption;
import com.ys.business.action.model.mouldinoutsearch.MouldInoutSearchModel;
import com.ys.business.db.dao.B_MouldAcceptanceDao;
import com.ys.business.db.dao.B_MouldBaseInfoDao;
import com.ys.business.db.dao.B_MouldDetailDao;
import com.ys.business.db.dao.B_MouldLendConfirmDao;
import com.ys.business.db.dao.B_MouldLendRegisterDao;
import com.ys.business.db.dao.B_MouldReturnRegisterDao;
import com.ys.business.db.dao.B_MouldPayInfoDao;
import com.ys.business.db.dao.B_MouldPayListDao;
import com.ys.business.db.dao.B_OrganizationDao;
import com.ys.business.db.dao.B_SupplierDao;
import com.ys.business.db.data.B_MouldAcceptanceData;
import com.ys.business.db.data.B_MouldBaseInfoData;
import com.ys.business.db.data.B_MouldDetailData;
import com.ys.business.db.data.B_MouldLendConfirmData;
import com.ys.business.db.data.B_MouldLendRegisterData;
import com.ys.business.db.data.B_MouldReturnRegisterData;
import com.ys.business.db.data.B_MouldPayInfoData;
import com.ys.business.db.data.B_MouldPayListData;
import com.ys.business.db.data.B_OrganizationData;
import com.ys.business.db.data.B_SupplierData;
import com.ys.business.ejb.BusinessDbUpdateEjb;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.system.db.dao.S_DICDao;
import com.ys.system.db.dao.S_USERDao;
import com.ys.system.db.data.S_DICData;
import com.ys.system.db.data.S_USERData;
import com.ys.system.service.common.BaseService;
import com.ys.util.CalendarUtil;
import com.ys.util.DicUtil;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basequery.BaseQuery;
import com.ys.util.basequery.common.BaseModel;

@Service
public class MouldInoutSearchService extends BaseService {

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
		String key1 = "";
		String key2 = "";
		
		data = URLDecoder.decode(data, "UTF-8");

		key1 = getJsonData(data, "keyword1");
		key2 = getJsonData(data, "keyword2");
		
		sEcho = getJsonData(data, "sEcho");	
		start = getJsonData(data, "iDisplayStart");		
		if (start != null && !start.equals("")){
			iStart = Integer.parseInt(start);			
		}
		
		length = getJsonData(data, "iDisplayLength");
		if (length != null && !length.equals("")){			
			iEnd = iStart + Integer.parseInt(length);			
		}		
		
		dataModel.setQueryFileName("/business/mouldinoutsearch/mouldinoutsearchquerydefine");
		dataModel.setQueryName("mouldinoutsearchquerydefine_search");
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
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

	public MouldInoutSearchModel getMouldInoutInfo(HttpServletRequest request) throws Exception {
		BaseModel dataModel = new BaseModel();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		
		MouldInoutSearchModel model = new MouldInoutSearchModel();
		B_MouldLendRegisterDao lendDao = new B_MouldLendRegisterDao();
		B_MouldLendRegisterData lendData = new B_MouldLendRegisterData();
		B_MouldReturnRegisterDao returnDao = new B_MouldReturnRegisterDao();
		B_MouldReturnRegisterData returnData = new B_MouldReturnRegisterData();
		B_MouldLendConfirmDao confirmDao = new B_MouldLendConfirmDao();
		B_MouldLendConfirmData confirmData = new B_MouldLendConfirmData();
		S_DICDao dicDao = new S_DICDao();
		S_DICData dicData = new S_DICData();
		B_SupplierDao supplierDao = new B_SupplierDao();
		B_SupplierData supplierData = new B_SupplierData();
		S_USERDao userDao = new S_USERDao();
		S_USERData userData = new S_USERData();
		
		CalendarUtil calendarUtil;
		
		String key = request.getParameter("key");

		dicData.setDicid(key);
		dicData.setDictypeid(DicUtil.PRODUCTMODEL);
		dicData = (S_DICData)dicDao.FindByPrimaryKey(dicData);
		model.setProductModelName(dicData.getDicdes());
		model.setProductModelNo(dicData.getDicname());

		model.setKeyBackup(key);
		
		model.setEndInfoMap("098", "0001", "");
		
		return model;
		
	}
	
	public HashMap<String, Object> doGetMouldLendDetailList(HttpServletRequest request, String data, UserInfo userInfo) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> rtnData = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		String id = "";
		
		data = URLDecoder.decode(data, "UTF-8");

		id = getJsonData(data, "keyBackup");
		if (id.equals("")) {
			id = "-1";
		}
		
		dataModel.setQueryFileName("/business/mouldinoutsearch/mouldinoutsearchquerydefine");
		dataModel.setQueryName("mouldinoutsearchquerydefine_searchld");
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("productModel", id);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, -1);	
				
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;		

	}

	public HashMap<String, Object> doGetMouldReturnDetailList(HttpServletRequest request, String data, UserInfo userInfo) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> rtnData = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		String id = "";
		
		data = URLDecoder.decode(data, "UTF-8");

		id = getJsonData(data, "keyBackup");
		if (id.equals("")) {
			id = "-1";
		}
		
		dataModel.setQueryFileName("/business/mouldinoutsearch/mouldinoutsearchquerydefine");
		dataModel.setQueryName("mouldinoutsearchquerydefine_searchrd");
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("productModel", id);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, -1);	
				
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;		

	}
	
}
