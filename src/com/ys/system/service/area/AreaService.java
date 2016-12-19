package com.ys.system.service.area;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ys.system.action.model.area.AreaModel;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.system.db.dao.S_areaDao;
import com.ys.system.db.data.S_areaData;
import com.ys.system.service.common.BaseService;
import com.ys.util.CalendarUtil;
import com.ys.util.DicUtil;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basedao.BaseTransaction;
import com.ys.util.basequery.BaseQuery;
import com.ys.util.basequery.common.BaseModel;
import com.ys.util.basequery.common.Constants;
import com.ys.business.action.model.material.ZZMaterialModel;
import com.ys.business.db.dao.B_PriceSupplierDao;
import com.ys.business.db.dao.B_ZZMaterialPriceDao;
import com.ys.business.db.dao.B_ZZRawMaterialDao;
import com.ys.business.db.data.B_BomDetailData;
import com.ys.business.db.data.B_PriceSupplierData;
import com.ys.business.db.data.B_ZZMaterialPriceData;
import com.ys.business.db.data.B_ZZRawMaterialData;
import com.ys.business.db.data.CommFieldsData;

@Service
public class AreaService extends BaseService {

	DicUtil util = new DicUtil();

	BaseTransaction ts;

	String guid ="";
	private CommFieldsData commData;
	
	private HttpServletRequest request;
	
	private S_areaData data;
	private S_areaDao dao;
	private B_ZZMaterialPriceDao priceDao;
	private AreaModel reqModel;
	private UserInfo userInfo;
	private BaseModel dataModel;
	private  Model model;
	private HashMap<String, String> userDefinedSearchCase;
	private BaseQuery baseQuery;
	ArrayList<HashMap<String, String>> modelMap = null;	

	public AreaService(){
		
	}

	public AreaService(Model model,
			HttpServletRequest request,
			AreaModel reqModel,
			UserInfo userInfo){
		
		this.dao = new S_areaDao();
		this.priceDao = new B_ZZMaterialPriceDao();
		this.data = new S_areaData();
		this.model = model;
		this.reqModel = reqModel;
		this.request = request;
		this.userInfo = userInfo;
		
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
		String key1 = request.getParameter("keyword1").toUpperCase();
		//String key1 = getJsonData(data, "keyword1").toUpperCase();
		//String key2 = getJsonData(data, "keyword2").toUpperCase();
		

		dataModel.setQueryFileName("/area/areaquerydefine");
		dataModel.setQueryName("areaquerydefine_search");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("keyword1", key1);
		//userDefinedSearchCase.put("keyword2", key2);
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
	 * 1.自制品单价新增处理(一条数据)
	 * 2.自制品原材料新增处理(N条数据)
	 */
	private void insert() throws Exception  {

		
		try {
			
			data = (S_areaData)reqModel.getArea();
			try{
				dao = new S_areaDao(data);
			}catch(Exception e){
				//没找到数据就insert,否则update
			}
			if(dao.beanData.getRecordid() == null ){
				commData = commFiledEdit(Constants.ACCESSTYPE_INS,
						"AreaInsert",userInfo);
	
				copyProperties(data,commData);
	
				guid = BaseDAO.getGuId();
				data.setRecordid(guid);
				
				dao.Create(data);	
			}else{
				commData = commFiledEdit(Constants.ACCESSTYPE_INS,
						"AreaUpdate",userInfo);
	
				copyProperties(data,commData);
				
				dao.Store(data);
			}
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}	


	/*
	 * 1.自制品单价新增处理(一条数据)
	 * 2.自制品原材料新增处理(N条数据)
	 */
	public void delete() throws Exception  {

		
		try {
			
			data = (S_areaData)reqModel.getArea();
			data.setRecordid(request.getParameter("recordId"));
			dao.Remove(data);	
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}	
	

	public Model getDetailView() throws Exception {

		String recordId = request.getParameter("recordId");
		data.setRecordid(recordId);
		
		try{
			dao = new S_areaDao(data);
			reqModel.setArea(dao.beanData);
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		
		return model;
		
	}
	
	public Model insertAndView() throws Exception {

		insert();
		
		return model;
		
	}

	
}
