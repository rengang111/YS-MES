package com.ys.business.service.mouldregister;

import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ckfinder.connector.utils.FileUtils;
import com.ys.business.action.model.common.ListOption;
import com.ys.business.action.model.externalsample.ExternalSampleModel;
import com.ys.business.action.model.mouldregister.MouldRegisterModel;
import com.ys.business.db.dao.B_ExternalSampleDao;
import com.ys.business.db.dao.B_MouldBaseInfoDao;
import com.ys.business.db.data.B_ExternalSampleData;
import com.ys.business.db.data.B_MouldBaseInfoData;
import com.ys.business.db.data.B_MouldFactoryData;
import com.ys.business.db.data.B_MouldSubData;
import com.ys.business.ejb.BusinessDbUpdateEjb;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.system.db.dao.S_DICDao;
import com.ys.system.db.data.S_DICData;
import com.ys.system.service.common.BaseService;
import com.ys.system.service.common.I_BaseService;
import com.ys.system.service.common.I_MultiAlbumService;
import com.ys.util.CalendarUtil;
import com.ys.util.DicUtil;
import com.ys.util.UploadReceiver;
import com.ys.util.basequery.BaseQuery;
import com.ys.util.basequery.common.BaseModel;

@Service
public class MouldRegisterService extends BaseService implements I_BaseService {

	public HashMap<String, Object> doSearch(HttpServletRequest request, String data, UserInfo userInfo) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
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
		
		dataModel.setQueryFileName("/business/mouldregister/mouldregisterquerydefine");
		dataModel.setQueryName("mouldregisterquerydefine_search");
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

	public MouldRegisterModel doAddInit(HttpServletRequest request) throws Exception {
		MouldRegisterModel model = new MouldRegisterModel();
		
		model.setTypeList(doOptionChange(DicUtil.MOULDTYPE, ""));
		model.setMouldFactoryList(doGetMouldFactoryList(request));
		model.setKeyBackup("");
		model.setEndInfoMap("098", "0001", "");
		
		return model;
		
	}
	
	public MouldRegisterModel doUpdateInit(HttpServletRequest request, String key) throws Exception {
		MouldRegisterModel model = new MouldRegisterModel();
		B_MouldBaseInfoDao dao = new B_MouldBaseInfoDao();
		B_MouldBaseInfoData dbData = new B_MouldBaseInfoData();

		if (key != null && !key.equals("")) {
			dbData.setId(key);
			dbData = (B_MouldBaseInfoData)dao.FindByPrimaryKey(dbData);
			model.setMouldBaseInfoData(dbData);
			
			S_DICDao dicDao = new S_DICDao();
			S_DICData dicData = new S_DICData();
			dicData.setDicid(dbData.getProductmodelid());
			dicData.setDictypeid(DicUtil.PRODUCTMODEL);
			dicData = (S_DICData)dicDao.FindByPrimaryKey(dicData);
			model.setProductModelIdView(dicData.getDicname());
			model.setProductModelName(dicData.getDicdes());
			
			HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
			BaseModel dataModel = new BaseModel();
			BaseQuery baseQuery = null;
			dataModel.setQueryFileName("/business/mouldregister/mouldregisterquerydefine");
			dataModel.setQueryName("mouldregisterquerydefine_getsubids");
			baseQuery = new BaseQuery(request, dataModel);
			userDefinedSearchCase.put("mouldId", key);
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
			model.setMouldSubDatas(baseQuery.getYsQueryData(0,0));
			
			userDefinedSearchCase = new HashMap<String, String>();
			dataModel = new BaseModel();
			dataModel.setQueryFileName("/business/mouldregister/mouldregisterquerydefine");
			dataModel.setQueryName("mouldregisterquerydefine_getfactorys");
			baseQuery = new BaseQuery(request, dataModel);
			userDefinedSearchCase.put("mouldId", key);
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
			model.setMouldFactoryDatas(baseQuery.getYsQueryData(0,0));
			
		}
		model.setTypeList(doOptionChange(DicUtil.MOULDTYPE, ""));
		model.setMouldFactoryList(doGetMouldFactoryList(request));
		
		model.setKeyBackup(key);
		
		model.setEndInfoMap("098", "0001", "");
		
		
		return model;
		
	}
	
	public ArrayList<ListOption> doGetMouldFactoryList(HttpServletRequest request) throws Exception {
			
		ArrayList<ListOption> listOption = new ArrayList<ListOption>();	

		HashMap<String, Object> modelData = doFactoryIdSearch(request);
		ArrayList<HashMap<String, String>> datas = (ArrayList<HashMap<String, String>>)modelData.get("data");
		for( HashMap<String, String> rowData:datas) {
			ListOption x = new ListOption(rowData.get("id"), rowData.get("shortName"));
			listOption.add(x);
		}
			
		return listOption;	
	}		

	public HashMap<String, Object> doProductModelIdSearch(HttpServletRequest request) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();	
		BaseModel dataModel = new BaseModel();	
		BaseQuery baseQuery = null;	
			
		//String key = request.getParameter("key").toUpperCase();	
			
		dataModel.setQueryFileName("/business/mouldregister/mouldregisterquerydefine");	
		dataModel.setQueryName("mouldregisterquerydefine_searchproductmodel");	
			
		baseQuery = new BaseQuery(request, dataModel);	
			
		baseQuery.getYsQueryData(0,0);	
			
		modelMap.put("data", dataModel.getYsViewData());	
			
		return modelMap;	
	}
	
	public HashMap<String, Object> doFactoryIdSearch(HttpServletRequest request) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();	
		BaseModel dataModel = new BaseModel();	
		BaseQuery baseQuery = null;	
			
		//String key = request.getParameter("key").toUpperCase();	
			
		dataModel.setQueryFileName("/business/mouldregister/mouldregisterquerydefine");	
		dataModel.setQueryName("mouldregisterquerydefine_searchfactory");	
			
		baseQuery = new BaseQuery(request, dataModel);	
			
		baseQuery.getYsQueryData(0,0);	
			
		modelMap.put("data", dataModel.getYsViewData());	
			
		return modelMap;	
	}
	
	public String doCheckNo(HttpServletRequest request, String data) {
		String message = "";										
		B_MouldBaseInfoDao infoDao = new B_MouldBaseInfoDao();
		B_MouldBaseInfoData infoData = new B_MouldBaseInfoData();
		S_DICDao dicDao = new S_DICDao();
		S_DICData dicData = new S_DICData();
		
		try {
			String no = getJsonData(data, "no");
			
			String type = getJsonData(data, "type");
			dicData.setDicid(type);
			dicData.setDictypeid(DicUtil.MOULDTYPE);
			dicData = (S_DICData)dicDao.FindByPrimaryKey(dicData);
			type = dicData.getDicdes();
			
			String mouldBaseId = getJsonData(data, "mouldBaseId");
			infoData.setId(mouldBaseId);
			infoData = (B_MouldBaseInfoData)infoDao.FindByPrimaryKey(infoData);
			//dicData.setDicid(infoData.getProductmodelid());
			//dicData.setDictypeid(DicUtil.PRODUCTMODEL);
			//dicData = (S_DICData)dicDao.FindByPrimaryKey(dicData);
			String productModelName = infoData.getProductmodelid();
			
			message = "err008";
			if (no.length() == (productModelName.length() + type.length() + 2)) {
				if (no.substring(0, no.length() - 2).equals(productModelName + type)) {
					message = "";
				}
			}
		}
		catch(Exception e) {
			message = "err001";
		}
		
		return message;
	}
	
	public MouldRegisterModel doUpdate(HttpServletRequest request, String data, UserInfo userInfo) throws Exception {
		MouldRegisterModel model = new MouldRegisterModel();

		B_MouldBaseInfoDao dao = new B_MouldBaseInfoDao();
		B_MouldBaseInfoData dbData = new B_MouldBaseInfoData();
		BaseModel dataModel = new BaseModel();
		BaseQuery baseQuery = null;

		BusinessDbUpdateEjb bean = new BusinessDbUpdateEjb();
			
		model = bean.executeMouldRegisterUpdate(request, data, userInfo);
		
		return model;
	}
	
	public MouldRegisterModel doDelete(HttpServletRequest request, String data, UserInfo userInfo){
		
		MouldRegisterModel model = new MouldRegisterModel();
		boolean isDBOperationSuccessed = false;
		try {
			BusinessDbUpdateEjb bean = new BusinessDbUpdateEjb();
	        
	        bean.executeMouldRegisterDelete(data, userInfo);
	        
	        isDBOperationSuccessed = true;
	        
	        model.setEndInfoMap(NORMAL, "", "");
	        
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		if (isDBOperationSuccessed) {
			String removeData[] = data.split(",");			
			for (String key:removeData) {	
				String dir = request.getSession().getServletContext().getRealPath("/")
						+ BusinessConstants.BUSINESSPHOTOPATH + key; 			
				//String dirSmall = dir + BusinessConstants.BUSINESSSMALLPHOTOPATH; 			
				
				FileUtils.delete(new File(dir));
			}
		}
		
		return model;
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
			
			//String key = request.getParameter("key").toUpperCase();
			
			dataModel.setQueryFileName("/business/mouldregister/mouldregisterquerydefine");
			dataModel.setQueryName("mouldregisterquerydefine_searchproductmodel");
			
			baseQuery = new BaseQuery(request, dataModel);
			
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
			baseQuery.getYsQueryData(0,0);
			
			modelMap.put("data", dataModel.getYsViewData());
			
			return modelMap;
	}	

	public String getMouldId(HttpServletRequest request, String data) throws Exception {
		S_DICDao dicDao = new S_DICDao(); 
		S_DICData dicData = new S_DICData();
		
		String mouldId = "";

		String productModelIdView = getJsonData(data, "productModelIdView");
		String type = getJsonData(data, "type");
	
		dicData.setDictypeid(DicUtil.MOULDTYPE);
		dicData.setDicid(type);
		dicData = (S_DICData)dicDao.FindByPrimaryKey(dicData);
		String typeMark = dicData.getDicdes();
		mouldId = productModelIdView + typeMark;
		
		BaseModel dataModel = new BaseModel();
		dataModel.setQueryFileName("/business/mouldregister/mouldregisterquerydefine");
		dataModel.setQueryName("mouldregisterquerydefine_getmouldserialno");
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		userDefinedSearchCase.put("mouldNo", mouldId);
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		ArrayList<HashMap<String, String>> mouldIdMap = baseQuery.getYsQueryData(0,0);
		if (mouldIdMap.size() > 0) {
			mouldId += String.format("%02d", Integer.parseInt(mouldIdMap.get(0).get("serialNo")));
		}

		return mouldId;
	}
	
	public String doRotate(HttpServletRequest request, String data) throws Exception {
		S_DICDao dicDao = new S_DICDao(); 
		S_DICData dicData = new S_DICData();
		
		String mouldId = "";

		String keyBackup = getJsonData(data, "keyBackup");
		String type = getJsonData(data, "type");
		String productModelIdView = getJsonData(data, "productModelIdView");
		String rotateDirect = getJsonData(data, "rotateDirect");
		
		dicData.setDictypeid(DicUtil.MOULDTYPE);
		dicData.setDicid(type);
		dicData = (S_DICData)dicDao.FindByPrimaryKey(dicData);
		String typeMark = dicData.getDicdes();
		mouldId = productModelIdView + typeMark;
		
		BaseModel dataModel = new BaseModel();
		dataModel.setQueryFileName("/business/mouldregister/mouldregisterquerydefine");
		dataModel.setQueryName("mouldregisterquerydefine_rotate");
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		userDefinedSearchCase.put("mouldId", mouldId);
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		ArrayList<HashMap<String, String>> mouldIdMap = baseQuery.getYsQueryData(0,0);
		if (mouldIdMap.size() > 0) {
			int index = -1;
			for(HashMap<String, String> rowData:mouldIdMap) {
				String id = rowData.get("id");
				index++;
				if (id.equals(keyBackup)) {
					if (index == 0) {
						if (rotateDirect.equals("0")) {
							mouldId = id;
							break;
						} else {
							//next one
							if ((index + 1) >= mouldIdMap.size()) {
								mouldId = id;
								break;
							} else {
								mouldId = mouldIdMap.get(index + 1).get("id");
								break;
							}
								
						}
					} else {
						if (index == mouldIdMap.size() - 1) {
							if (rotateDirect.equals("1")) {
								mouldId = id;
							} else {
								if ((index - 1) < 0) {
									mouldId = id;
									break;
								} else {
									mouldId = mouldIdMap.get(index - 1).get("id");
									break;
								}
							}
						} else {
							if (rotateDirect.equals("1")) {
								if ((index + 1) >= mouldIdMap.size()) {
									mouldId = id;
									break;
								} else {
									mouldId = mouldIdMap.get(index + 1).get("id");
									break;
								}
							} else {
								//pre one
								if ((index - 1) < 0) {
									mouldId = id;
									break;
								} else {
									mouldId = mouldIdMap.get(index - 1).get("id");
									break;
								}
							}							
						}
					}
					
				}
			}
		}

		return mouldId;
	}
	
	public static B_MouldBaseInfoData updateMouldBaseInfoModifyInfo(B_MouldBaseInfoData data, UserInfo userInfo) {
		String createUserId = data.getCreateperson();
		if ( createUserId == null || createUserId.equals("")) {
			data.setCreateperson(userInfo.getUserId());
			data.setCreatetime(CalendarUtil.fmtDate());
			data.setCreateunitid(userInfo.getUnitId());
			data.setDeptguid(userInfo.getDeptGuid());
		}
		data.setModifyperson(userInfo.getUserId());
		data.setModifytime(CalendarUtil.fmtDate());
		data.setDeleteflag(BusinessConstants.DELETEFLG_UNDELETE);
		
		return data;
	}
	
	public static B_MouldSubData updateMouldSubModifyInfo(B_MouldSubData data, UserInfo userInfo) {
		String createUserId = data.getCreateperson();
		if ( createUserId == null || createUserId.equals("")) {
			data.setCreateperson(userInfo.getUserId());
			data.setCreatetime(CalendarUtil.fmtDate());
			data.setCreateunitid(userInfo.getUnitId());
			data.setDeptguid(userInfo.getDeptGuid());
		}
		data.setModifyperson(userInfo.getUserId());
		data.setModifytime(CalendarUtil.fmtDate());
		data.setDeleteflag(BusinessConstants.DELETEFLG_UNDELETE);
		
		return data;
	}

	public static B_MouldFactoryData updateMouldFactoryModifyInfo(B_MouldFactoryData data, UserInfo userInfo) {
		String createUserId = data.getCreateperson();
		if ( createUserId == null || createUserId.equals("")) {
			data.setCreateperson(userInfo.getUserId());
			data.setCreatetime(CalendarUtil.fmtDate());
			data.setCreateunitid(userInfo.getUnitId());
			data.setDeptguid(userInfo.getDeptGuid());
		}
		data.setModifyperson(userInfo.getUserId());
		data.setModifytime(CalendarUtil.fmtDate());
		data.setDeleteflag(BusinessConstants.DELETEFLG_UNDELETE);
		
		return data;
	}
	
	private MouldRegisterModel getProductModelName(HttpServletRequest request, MouldRegisterModel model, String productModelId) throws Exception {
		HashMap<String, Object> productModel = doProductModelIdSearch(request);
		ArrayList<HashMap<String, String>> data = (ArrayList<HashMap<String, String>>)productModel.get("data");
		for(HashMap<String, String> rowData:data) {
			if (rowData.get("id").equals(productModelId)) {
				model.setProductModelName(rowData.get("des"));
				model.setProductModelIdView(rowData.get("name"));
				break;
			}
		}
		
		return model;
	}
	
	private MouldRegisterModel getFactoryName(HttpServletRequest request, MouldRegisterModel model, String factoryId) throws Exception {
		HashMap<String, Object> productModel = doProductModelIdSearch(request);
		ArrayList<HashMap<String, String>> data = (ArrayList<HashMap<String, String>>)productModel.get("data");
		for(HashMap<String, String> rowData:data) {
			if (rowData.get("id").equals(factoryId)) {
				model.setMouldFactoryId(rowData.get("no"));
				model.setMouldFactoryName(rowData.get("fullname"));
				break;
			}
		}
		
		return model;
	}
	
	public MouldRegisterModel getFileList(HttpServletRequest request, MouldRegisterModel model) {
		UploadReceiver uploadReceiver = new UploadReceiver();
		int arraySize = 0;
		String dir = request.getSession().getServletContext().getRealPath("/")
				+ BusinessConstants.BUSINESSPHOTOPATH + model.getMouldBaseInfoData().getId() ; 
		
		String nowUseImage = model.getMouldBaseInfoData().getImage_filename();
		
		ArrayList<ArrayList<String>> fileList = new ArrayList<ArrayList<String>>();
		
		String[] filenames = uploadReceiver.getFileNameList(dir + File.separator + BusinessConstants.BUSINESSSMALLPHOTOPATH);
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
				
		model.setFilenames(filenames);
		model.setImageKey(model.getMouldBaseInfoData().getId());
		model.setPath(BusinessConstants.BUSINESSPHOTOPATH);
		model.setNowUseImage(nowUseImage);
		
		return model;
	}
	
	public void setNowUseImage(String key, String src) throws Exception {
		B_MouldBaseInfoDao dao = new B_MouldBaseInfoDao();
		B_MouldBaseInfoData dbData = new B_MouldBaseInfoData();
		
		dbData.setId(key);
		dbData = (B_MouldBaseInfoData)dao.FindByPrimaryKey(dbData);
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		UserInfo userInfo = (UserInfo)request.getSession().getAttribute(BusinessConstants.SESSION_USERINFO);
		
		dbData = updateMouldBaseInfoModifyInfo(dbData, userInfo);
		
		dbData.setImage_filename(src);
		
		dao.Store(dbData);
		
	}
	
	public String getNowUseImage(String key) {
		B_MouldBaseInfoDao dao = new B_MouldBaseInfoDao();
		B_MouldBaseInfoData dbData = new B_MouldBaseInfoData();
		
		String nowUseImage = "";
		
		try {
			dbData.setId(key);
			dbData = (B_MouldBaseInfoData)dao.FindByPrimaryKey(dbData);
			nowUseImage = dbData.getImage_filename();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return nowUseImage;
		
	}	
	
}
