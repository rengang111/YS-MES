
package com.ys.business.service.mould;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ys.business.action.model.common.ListOption;
import com.ys.business.action.model.externalsample.ExternalSampleModel;
import com.ys.business.action.model.mouldcontract.MouldContractModel;
import com.ys.business.action.model.mouldcontractdetail.MouldContractDetailModel;
import com.ys.business.db.dao.B_ExternalSampleDao;
import com.ys.business.db.dao.B_LatePerfectionQuestionDao;
import com.ys.business.db.dao.B_LatePerfectionRelationFileDao;
import com.ys.business.db.dao.B_MouldAcceptanceDao;
import com.ys.business.db.dao.B_MouldBaseInfoDao;
import com.ys.business.db.dao.B_MouldDetailDao;
import com.ys.business.db.dao.B_MouldPayInfoDao;
import com.ys.business.db.dao.B_MouldPayListDao;
import com.ys.business.db.dao.B_OrganizationDao;
import com.ys.business.db.dao.B_ProjectTaskDao;
import com.ys.business.db.data.B_ExternalSampleData;
import com.ys.business.db.data.B_LatePerfectionQuestionData;
import com.ys.business.db.data.B_LatePerfectionRelationFileData;
import com.ys.business.db.data.B_MouldAcceptanceData;
import com.ys.business.db.data.B_MouldBaseInfoData;
import com.ys.business.db.data.B_MouldDetailData;
import com.ys.business.db.data.B_MouldPayInfoData;
import com.ys.business.db.data.B_MouldPayListData;
import com.ys.business.db.data.B_OrganizationData;
import com.ys.business.db.data.B_ProjectTaskData;
import com.ys.business.ejb.BusinessDbUpdateEjb;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.action.model.role.RoleModel;
import com.ys.util.basequery.common.BaseModel;
import com.ys.util.basequery.common.Constants;

import net.sf.json.JSONArray;

import com.ys.system.common.BusinessConstants;
import com.ys.system.db.dao.S_ROLEDao;
import com.ys.system.db.dao.S_USERDao;
import com.ys.system.db.data.S_ROLEData;
import com.ys.system.db.data.S_USERData;
import com.ys.system.ejb.DbUpdateEjb;
import com.ys.system.service.common.BaseService;
import com.ys.system.service.common.I_BaseService;
import com.ys.system.service.user.UserService;
import com.ys.util.CalendarUtil;
import com.ys.util.DicUtil;
import com.ys.util.UploadReceiver;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basedao.BaseTransaction;
import com.ys.util.basequery.BaseQuery;

import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;

@Service
public class MouldContractDetailService extends BaseService {

	public MouldContractDetailModel doInit() throws Exception {

		MouldContractDetailModel model = new MouldContractDetailModel();
		
		model.setTabNameList(doOptionChange(DicUtil.MOULDTYPE, ""));
		
		model.setEndInfoMap("098", "0001", "");
		
		return model;
		
	}
	
	public HashMap<String, Object> doGetMouldDetailList(HttpServletRequest request, String data, UserInfo userInfo) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> rtnData = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		String length = "";
		float sumPrice = 0;
		
		data = URLDecoder.decode(data, "UTF-8");
				
		dataModel.setQueryFileName("/business/mouldcontractdetail/mouldcontractdetailquerydefine");
		dataModel.setQueryName("mouldcontractdetailquerydefine_searchmd");
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("productModelId", getJsonData(data, "productModelId"));
		userDefinedSearchCase.put("productModelName",  getJsonData(data, "productModelName"));
		userDefinedSearchCase.put("type",  getJsonData(data, "type"));
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, -1);	
				
		modelMap.put("data", dataModel.getYsViewData());
	
		for(HashMap<String, String>rowData:dataModel.getYsViewData()) {
			String price = rowData.get("price");
			if (price != null && !price.equals("")) {
				sumPrice += Float.parseFloat(price);
			}
		}
		
		modelMap.put("sumPrice", String.valueOf(sumPrice));
		
		return modelMap;		

	}
	
	public ArrayList<ListOption> doOptionChange(String type, String parentCode) {
		DicUtil util = new DicUtil();
		ArrayList<ListOption> optionList = null;
		
		try {
			optionList = util.getListOption(type, parentCode);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return optionList;
	}
	
	public HashMap<String, Object> getProductModelList(HttpServletRequest request, String data) throws Exception {
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		BaseQuery baseQuery = null;
		
		String key = request.getParameter("key").toUpperCase();
		
		dataModel.setQueryFileName("/business/mouldcontractdetail/mouldcontractdetailquerydefine");
		dataModel.setQueryName("mouldcontractdetailquerydefine_searchproductmodel");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0,0);
		
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
	}
	
	public HashMap<String, Object> doProductModelIdSearch(HttpServletRequest request, String data) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();	
		BaseModel dataModel = new BaseModel();	
		BaseQuery baseQuery = null;	
			
		String key = request.getParameter("key").toUpperCase();	
			
		dataModel.setQueryFileName("/business/mouldcontractdetail/mouldcontractquerydefine");	
		dataModel.setQueryName("mouldcontractdetailquerydefine_searchproductmodel");	
			
		baseQuery = new BaseQuery(request, dataModel);	
			
		baseQuery.getYsQueryData(0,0);	
			
		modelMap.put("data", dataModel.getYsViewData());	
			
		return modelMap;	
	}
}
