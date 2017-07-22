package com.ys.business.service.order;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

//import com.ckfinder.connector.utils.FileUtils;
import com.ys.business.action.model.order.ProductDesignModel;
import com.ys.business.db.dao.B_ProductDesignDao;
import com.ys.business.db.dao.B_ProductDesignDetailDao;
import com.ys.business.db.data.B_ArrivalData;
import com.ys.business.db.data.B_ProductDesignData;
import com.ys.business.db.data.B_ProductDesignDetailData;
import com.ys.business.db.data.CommFieldsData;
import com.ys.business.service.common.BusinessService;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.util.CalendarUtil;
import com.ys.util.DicUtil;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basedao.BaseTransaction;
import com.ys.util.basequery.BaseQuery;
import com.ys.util.basequery.common.BaseModel;
import com.ys.util.basequery.common.Constants;

@Service
public class ProductDesignService extends CommonService {
 
	DicUtil util = new DicUtil();
	BaseTransaction ts;

	String guid ="";
	private CommFieldsData commData;
	
	private HttpServletRequest request;
	
	private B_ProductDesignDao dao;
	private B_ProductDesignDetailDao detailDao;
	private ProductDesignModel reqModel;
	private UserInfo userInfo;
	private BaseModel dataModel;
	private  Model model;
	private HashMap<String, String> userDefinedSearchCase;
	private BaseQuery baseQuery;
	HashMap<String, Object> modelMap = null;
	HttpSession session;
	HttpServletResponse response;

	public ProductDesignService(){
		
	}

	public ProductDesignService(
			Model model,
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			ProductDesignModel reqModel,
			UserInfo userInfo){
		
		this.dao = new B_ProductDesignDao();
		this.detailDao = new B_ProductDesignDetailDao();
		this.model = model;
		this.reqModel = reqModel;
		this.request = request;
		this.response = response;
		this.userInfo = userInfo;
		this.session = session;
		dataModel = new BaseModel();
		modelMap = new HashMap<String, Object>();
		userDefinedSearchCase = new HashMap<String, String>();
		dataModel.setQueryFileName("/business/material/materialquerydefine");
		super.request = request;
		super.userInfo = userInfo;
		super.session = session;
		
	}
	public HashMap<String, Object> doSearch( String data) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		int iStart = 0;
		int iEnd =0;
		String sEcho = "";
		String start = "";
		String length = "";
		
		data = URLDecoder.decode(data, "UTF-8");

		String[] keyArr = getSearchKey(Constants.FORM_PRODUCTDETAIL,data,session);
		String key1 = keyArr[0];
		String key2 = keyArr[1];
		
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
				
		if ( iEnd > dataModel.getYsViewData().size()){
			
			iEnd = dataModel.getYsViewData().size();
			
		}
		
		modelMap.put("sEcho", sEcho); 
		
		modelMap.put("recordsTotal", dataModel.getRecordCount()); 
		
		modelMap.put("recordsFiltered", dataModel.getRecordCount());
		
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;		

	}
	

	public HashMap<String, Object> contractArrivalSearch(
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
	
		dataModel.setQueryName("getArrivaList");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		String[] keyArr = getSearchKey(Constants.FORM_PRODUCTDETAIL,data,session);
		String key1 = keyArr[0];
		String key2 = keyArr[1];
		
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

	
	public String doAddOrView() throws Exception {

		String reqYsid = request.getParameter("YSId");
		String PIId = request.getParameter("PIId");
		String productid = request.getParameter("productId");
		String goBackFlag = request.getParameter("goBackFlag");
		

		String rtnFlg = "查看";
		//1.判断画面来的耀升编号是否有做单资料
		B_ProductDesignData reqDb = checkProductDesignlById(reqYsid);
		
		if(reqDb == null || ("").equals(reqDb)){
			B_ProductDesignData dt = new B_ProductDesignData();
			dt.setProductid(productid);
			dt = getMaxProductDesignId(dt);
			String oldYsid = dt.getYsid();
			
			if(oldYsid == null || ("").equals(oldYsid)){
				//画面来的耀升编号既没有做单资料,
				//也没有该产品的历史做单资料
				addInit(reqYsid);
				rtnFlg = "新建";
				
			}else{
				//没有该耀升编号的做单资料,
				//显示该产品最近的耀升编号到编辑页面
				updateInit(oldYsid);
				rtnFlg = "编辑新建";
			}
		}else{	
			getProductDesignById(reqYsid);
			rtnFlg = "查看";		
		}

		model.addAttribute("YSId",reqYsid);
		model.addAttribute("PIId",PIId);
		model.addAttribute("goBackFlag",goBackFlag);
			
		return rtnFlg;
	}
	

	public void deleteTextPrintFile() throws Exception{
		
		String recordId = request.getParameter("recordId");
		String fileName = updateTextPrint(recordId,"");
		
		deletePhoto(null,fileName);
	}
	
	
	public HashMap<String, Object> deletePhotoAndReload(
			String folderName,String fileList,String fileCount) throws Exception {

		String path = request.getParameter("path");
		
		deletePhoto(path,null);
		
		B_ProductDesignData reqDt = reqModel.getProductDesign();
		String YSId = reqDt.getYsid();
		String productId = reqDt.getProductid();
		
		getPhoto(YSId,productId,folderName,fileList,fileCount);
		
		return modelMap;
	}
	
	public HashMap<String, Object> uploadPhotoAndReload(
			MultipartFile[] headPhotoFile,
			String folderName,String fileList,String fileCount) throws Exception {

		B_ProductDesignData reqDt = reqModel.getProductDesign();
		String YSId = reqDt.getYsid();
		String productId = reqDt.getProductid();
		String recordid = reqDt.getRecordid();
		
		uploadPhoto(headPhotoFile,folderName,recordid,productId,YSId);
		
		
		getPhoto(YSId,productId,folderName,fileList,fileCount);
		
		return modelMap;
	}
	
	public void addInit(String YSId) throws Exception {
	
		//取得该耀升编号下的产品信息
		if(!(YSId == null || ("").equals(YSId)))
			getOrderDetailById(YSId);
		
		setDicList();//设置下拉框内容
	
	}
	
	private void setDicList() throws Exception{

		model.addAttribute("chargerTypeList",
				util.getListOption(DicUtil.DIC_CHARGERTYPE, ""));
		model.addAttribute("batteryPackList",
				util.getListOption(DicUtil.DIC_BATTERYPACK, ""));
		model.addAttribute("purchaserList",
				util.getListOption(DicUtil.DIC_PURCHASER, ""));
	}


	
	
	public void doInsertAndView() throws Exception {

		String YSId = insert();
		getProductDesignById(YSId);
	}
	
	public void doUpdateAndView() throws Exception {

		String YSId = insert();
		getProductDesignById(YSId);
	}
	
	private String insert(){
		String YSId = "";
		ts = new BaseTransaction();
				
		try {
			ts.begin();
			
			//做单资料 head处理
			B_ProductDesignData design = reqModel.getProductDesign();
			YSId = design.getYsid();
			String productdetailid = updateProductDesign(design);//更新处理
			
			//做单资料明细部分
			
			//0:先删除旧数据
			deleteProductDesignDetail(productdetailid);
			
			int sortNo = 0;
			//1:机器配置清单;
			List<B_ProductDesignDetailData> machineList = reqModel.getMachineConfigList();
			for(B_ProductDesignDetailData d:machineList){
				String name = d.getComponentname();
				if(name == null || ("").equals(name))
					continue;
				d.setProductdetailid(productdetailid);
				d.setType(Constants.PRODUCTDETAIL_1);
				d.setSortno(String.valueOf(sortNo));
				
				insertProductDesignDetail(d);
				sortNo++;
			}
			
			//2:塑料制品清单;
			sortNo = 0;
			List<B_ProductDesignDetailData> plasticList = reqModel.getPlasticList();
			for(B_ProductDesignDetailData d:plasticList){
				String name = d.getComponentname();
				if(name == null || ("").equals(name))
					continue;
				d.setProductdetailid(productdetailid);
				d.setType(Constants.PRODUCTDETAIL_2);
				d.setSortno(String.valueOf(sortNo));
				
				insertProductDesignDetail(d);
				sortNo++;
			}
			
			
			//3:配件清单;
			sortNo = 0;
			List<B_ProductDesignDetailData> accessList = reqModel.getAccessoryList();
			for(B_ProductDesignDetailData d:accessList){
				String name = d.getComponentname();
				if(name == null || ("").equals(name))
					continue;
				d.setProductdetailid(productdetailid);
				d.setType(Constants.PRODUCTDETAIL_3);
				d.setSortno(String.valueOf(sortNo));
				
				insertProductDesignDetail(d);
				sortNo++;
			}
			
			//5:标贴;
			sortNo = 0;
			List<B_ProductDesignDetailData> labelList = reqModel.getLabelList();
			for(B_ProductDesignDetailData d:labelList){
				String name = d.getComponentname();
				if(name == null || ("").equals(name))
					continue;
				d.setProductdetailid(productdetailid);
				d.setType(Constants.PRODUCTDETAIL_5);
				d.setSortno(String.valueOf(sortNo));
				
				insertProductDesignDetail(d);
				sortNo++;
			}
			
			//6:文字印刷;
			sortNo = 0;
			List<B_ProductDesignDetailData> textList = reqModel.getTextPrintList();
			for(B_ProductDesignDetailData d:textList){
				String name = d.getComponentname();
				if(name == null || ("").equals(name))
					continue;
				d.setProductdetailid(productdetailid);
				d.setType(Constants.PRODUCTDETAIL_6);
				d.setSortno(String.valueOf(sortNo));
				
				insertProductDesignDetail(d);
				sortNo++;
			}
			
			//6:包装描述;
			sortNo = 0;
			List<B_ProductDesignDetailData> packageList = reqModel.getPackageList();
			for(B_ProductDesignDetailData d:packageList){
				String name = d.getComponentname();
				if(name == null || ("").equals(name))
					continue;
				d.setProductdetailid(productdetailid);
				d.setType(Constants.PRODUCTDETAIL_7);
				d.setSortno(String.valueOf(sortNo));
				
				insertProductDesignDetail(d);
				sortNo++;
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
		
		return YSId;
	}

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private B_ProductDesignData checkProductDesignlById(String YSId) {

		List<B_ProductDesignData> dbList = new ArrayList<B_ProductDesignData>();
		B_ProductDesignData db = null;
		String where = " YSId = '"+YSId+"' AND deleteFlag='0' ";
		try {
			dbList = (List<B_ProductDesignData>)dao.Find(where);
			if(dbList != null && dbList.size()>0){
				db = dbList.get(0);
			}
		} catch (Exception e) {
			// nothing
		}
		return db;
	}
	
	
	public void updateInit(String YSId) throws Exception{

		
		//做单资料明细内容
		getProductDesignById(YSId);		
		String productDetailId = reqModel.getProductDesign().getProductdetailid();
		
		//机器配置信息
		getMachineConfiguration(productDetailId);
		
		//塑料制品
		getPlastic(productDetailId);
		
		//配件清单
		getAccessory(productDetailId);
		
		//标贴
		getLabel(productDetailId);
		
		//文字印刷
		getTextPrint(productDetailId);
		
		//包装描述
		getPackage(productDetailId);
		
		setDicList();//设置下拉框内容
	}
	
	public void doDelete(String recordId) throws Exception{
		
		B_ArrivalData data = new B_ArrivalData();	
															
		try {
			
			ts = new BaseTransaction();										
			ts.begin();									
			
			String removeData[] = recordId.split(",");									
			for (String key:removeData) {									
												
				data.setRecordid(key);							
				dao.Remove(data);	
				
			}
			
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
		}
	}
	
	private String  updateProductDesign(
			B_ProductDesignData mData) throws Exception{
		B_ProductDesignData db = null;
		
		String YSId = mData.getYsid();
		String productdetailid = mData.getProductdetailid();
		try{
			//db = new B_ProductDesignDao(mData).beanData;
			db = checkProductDesignlById(YSId);
		}catch(Exception e){
			//nothing
		}
		if(db == null || ("").equals(db)){
			//做单资料编号取得
			mData =getMaxProductDesignId(mData);
			mData.setYsid(YSId);//新建时,录入新的耀升编号
			productdetailid = mData.getProductdetailid();
			insertProductDesign(mData);//找不到就新增一条数据
			return productdetailid;
		}
		
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"ProductDesignUpdate",userInfo);
		copyProperties(mData,commData);	
		mData.setPackagedescription(
				replaceTextArea(mData.getPackagedescription()));//字符转换
		mData.setStoragedescription(
				replaceTextArea(mData.getStoragedescription()));	
	
		dao.Store(mData);	

		return productdetailid;
	}
	

	private String updateTextPrint(
			String recordid,
			String filename) throws Exception{
		
		String oldFile = "";
		B_ProductDesignDetailData db = new B_ProductDesignDetailData();
		db.setRecordid(recordid);
		
		try{
			db = new B_ProductDesignDetailDao(db).beanData;
			if(db == null || ("").equals(db)){			
				return null;
			}
			//既存文件名
			oldFile = db.getFilename();
			
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"ProductDesignDetailUpdate",userInfo);
			copyProperties(db,commData);	
			db.setFilename(filename);
			
			detailDao.Store(db);
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return oldFile;
	}
	
	
	private void insertProductDesign(
			B_ProductDesignData mData) throws Exception{
		
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"ProductDesignInsert",userInfo);
		copyProperties(mData,commData);	
		mData.setPackagedescription(
				replaceTextArea(mData.getPackagedescription()));//字符转换
		mData.setStoragedescription(
				replaceTextArea(mData.getStoragedescription()));	
		guid = BaseDAO.getGuId();
		mData.setRecordid(guid);
		
		dao.Create(mData);	
	}
	
	private void insertProductDesignDetail(
			B_ProductDesignDetailData mData) throws Exception{
				
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"ProductDesignDetailInsert",userInfo);
		copyProperties(mData,commData);		
		guid = BaseDAO.getGuId();
		mData.setRecordid(guid);
		
		detailDao.Create(mData);	
	}
	
	private void deleteProductDesignDetail(
			String  productdetailid){
		
		String astr_Where = " productDetailId = '"+productdetailid+"' ";
		try{
			detailDao.RemoveByWhere(astr_Where);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public void getProductDesignDetail(String contractId) throws Exception {

		
		dataModel.setQueryName("getContractById");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("contractId", contractId);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		if(dataModel.getRecordCount() >0){
			model.addAttribute("contract",dataModel.getYsViewData().get(0));
			model.addAttribute("material",dataModel.getYsViewData());			
		}
		
	}
	
	public HashMap<String, Object> getMachineConfiguration(String productDetailId) throws Exception {
		
		dataModel.setQueryName("getProductDesignDetailById");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("productDetailId", productDetailId);
		userDefinedSearchCase.put("type", Constants.PRODUCTDETAIL_1);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		modelMap.put("data", dataModel.getYsViewData());
		model.addAttribute("machineConfigList",dataModel.getYsViewData());
		model.addAttribute("machineConfigCount",dataModel.getRecordCount());
		
		return modelMap;
		
	}
	
	public HashMap<String, Object> getPlastic(String productDetailId) throws Exception {
		
		dataModel.setQueryName("getProductDesignDetailById");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("productDetailId", productDetailId);
		userDefinedSearchCase.put("type", Constants.PRODUCTDETAIL_2);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		modelMap.put("data", dataModel.getYsViewData());
		model.addAttribute("plasticList",dataModel.getYsViewData());
		model.addAttribute("plasticCount",dataModel.getRecordCount());
		
		return modelMap;
		
	}
	
	public HashMap<String, Object> getAccessory(String productDetailId) throws Exception {
		
		dataModel.setQueryName("getProductDesignDetailById");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("productDetailId", productDetailId);
		userDefinedSearchCase.put("type", Constants.PRODUCTDETAIL_3);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		modelMap.put("data", dataModel.getYsViewData());
		model.addAttribute("accessoryList",dataModel.getYsViewData());
		model.addAttribute("accessoryCount",dataModel.getRecordCount());
		
		return modelMap;
		
	}
	
	public HashMap<String, Object> getProductStoragePhoto() throws Exception {
		
		String YSId = request.getParameter("YSId");
		String productId = request.getParameter("productId");
		//getProductDesignById(YSId);
		
		getPhoto(YSId,productId,"storage","storageFileList","storageFileCount");
		
		return modelMap;
	}
	
	
	public HashMap<String, Object> getProductPhoto() throws Exception {
		
		String YSId = request.getParameter("YSId");
		String productId = request.getParameter("productId");
		//getProductDesignById(YSId);
		
		getPhoto(YSId,productId,"product","productFileList","productFileCount");
		
		return modelMap;
	}
	
	public HashMap<String, Object> getLabelAndPhoto() throws Exception {
		
		String YSId = request.getParameter("YSId");
		String productId = request.getParameter("productId");
		String productDetailId = request.getParameter("productDetailId");
		//getProductDesignById(YSId);
		getLabel(productDetailId);//标贴
		
		getPhoto(YSId,productId,"label","labelFileList","labelFileCount");
		
		return modelMap;
	}
	public HashMap<String, Object> getLabel(String productDetailId) throws Exception {
		
		dataModel.setQueryName("getProductDesignDetailById");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("productDetailId", productDetailId);
		userDefinedSearchCase.put("type", Constants.PRODUCTDETAIL_5);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		modelMap.put("data", dataModel.getYsViewData());
		model.addAttribute("labelList",dataModel.getYsViewData());
		model.addAttribute("labelCount",dataModel.getRecordCount());
		
		return modelMap;
		
	}


	public HashMap<String, Object> getTextPrint(String productDetailId) throws Exception {
		
		dataModel.setQueryName("getProductDesignDetailById");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("productDetailId", productDetailId);
		userDefinedSearchCase.put("type", Constants.PRODUCTDETAIL_6);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();
	
		modelMap.put("data", dataModel.getYsViewData());
		model.addAttribute("textPrintList",dataModel.getYsViewData());
		model.addAttribute("textPrintCount",dataModel.getRecordCount());
		
		return modelMap;
		
	}
	
	public B_ProductDesignData getMaxProductDesignId(
			B_ProductDesignData bean) throws Exception{
		
		String productId = bean.getProductid();
		dataModel.setQueryName("getMaxProductDesignId");		
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("productId", productId);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();	 
		
		//取得已有的最大流水号(sql里已经加1了)
		String subid ="1";
		String ysid ="";
		if(dataModel.getRecordCount() > 0){
			subid =dataModel.getYsViewData().get(0).get("MaxSubId");
			ysid = dataModel.getYsViewData().get(0).get("YSId");
			bean.setYsid(ysid);
		}	
		String productdetailid = 
				BusinessService.getProductDesignDetailId(productId,subid);
			
		bean.setProductdetailid(productdetailid);
		bean.setSubid(subid);
		return bean;
	}
	
	public HashMap<String, Object> getPackageAndPhoto() throws Exception {
		
		String YSId = request.getParameter("YSId");
		String productId = request.getParameter("productId");
		String productDetailId = request.getParameter("productDetailId");
		//getProductDesignById(YSId);

		getPackage(productDetailId);//包装描述
		getPhoto(YSId,productId,"package","packageFileList","packageFileCount");
		
		return modelMap;
	}
	

	public void getPhoto(
			String YSId,String productId,
			String folderName,String fileList,String fileCount) {
		
		//B_ProductDesignData reqDesign = reqModel.getProductDesign();
		//String productId = reqDesign.getProductid();
		//String YSId = reqDesign.getYsid();
		//int id = Integer.parseInt(request.getParameter("id"));
		
		String tempPath = session.getServletContext().
				getRealPath(BusinessConstants.PATH_PRODUCTDESIGNTEMP)+
				"/"+productId+"/"+YSId+"/"+folderName;	
		String viewPath = BusinessConstants.PATH_PRODUCTDESIGNTEMP+
				productId+"/"+YSId+"/"+folderName+"/";	

	
		
		try {
			
			getFiles(tempPath,viewPath,fileList,fileCount);
							
		}
		catch(Exception e) {
			System.out.println(e.getMessage());

		}		
	}
	
	private void getFiles(String filePath,String viewPath,
			String fileList,String fileCount){

		ArrayList<String> filelist = new ArrayList<String>();
		
		File root = new File(filePath);
		File[] files = root.listFiles();
		
		int count = 0;
		try{
			for(File file:files){    
				if(file.isDirectory()){
					 //递归调用
					// getFiles(file.getAbsolutePath());
					// filelist.add(file.getAbsolutePath());
					 //count++;
					 
					 System.out.println("显示"+filePath+"下所有子目录及其文件"+file.getAbsolutePath());
				 }else{
					 filelist.add(viewPath+file.getName());
					 count++;   
					 System.out.println("显示"+filePath+"下所有子目录"+file.getAbsolutePath());
				 } 
				    
			}	
		}catch(Exception e){
			//nothing
		}
		modelMap.put(fileList, filelist);
		modelMap.put(fileCount, count);
	}
	
	public HashMap<String, Object> getPackage(String productDetailId) throws Exception {
		
		dataModel.setQueryName("getProductDesignDetailById");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("productDetailId", productDetailId);
		userDefinedSearchCase.put("type", Constants.PRODUCTDETAIL_7);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		modelMap.put("data", dataModel.getYsViewData());
		model.addAttribute("packageList",dataModel.getYsViewData());
		model.addAttribute("packageCount",dataModel.getRecordCount());
		
		return modelMap;
		
	}
	
	public void getOrderDetailById(String YSId) throws Exception {
		
		dataModel.setQueryName("getOrderDetailById");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("YSId", YSId);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		if(dataModel.getRecordCount() >0){
			model.addAttribute("product",dataModel.getYsViewData().get(0));

		}
		
	}
	
	public void getProductDesignById(String YSId) throws Exception {
		
		dataModel.setQueryName("getProductDesignById");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("YSId", YSId);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		if(dataModel.getRecordCount() >0){
			model.addAttribute("product",dataModel.getYsViewData().get(0));
			B_ProductDesignData d = new B_ProductDesignData();
			d.setYsid(dataModel.getYsViewData().get(0).get("YSId"));
			d.setProductid(dataModel.getYsViewData().get(0).get("productId"));
			d.setProductdetailid(dataModel.getYsViewData().get(0).get("productDetailId"));
			d.setPackagedescription(dataModel.getYsViewData().get(0).get("packageDescription"));
			d.setSealedsample(dataModel.getYsViewData().get(0).get("sealedSample"));
			d.setBatterypack(dataModel.getYsViewData().get(0).get("batteryPackId"));
			d.setChargertype(dataModel.getYsViewData().get(0).get("chargerTypeId"));
			d.setStoragedescription(dataModel.getYsViewData().get(0).get("storageDescription"));
			reqModel.setProductDesign(d);

		}
		
	}
	
	
	public HashMap<String, Object> uploadPhoto(
			MultipartFile[] headPhotoFile,String folder,
			String recordid,String productId,String YSId
			) {
		
		
		//int id = Integer.parseInt(request.getParameter("id"));
		String finalUserId = "";
		
		String tempPath = session.getServletContext().
				getRealPath(BusinessConstants.PATH_PRODUCTDESIGNTEMP)+
				"/"+productId+"/"+YSId+"/"+folder;		

		String realPath = session.getServletContext().
				getRealPath(BusinessConstants.PATH_PRODUCTDESIGNFILE)+
				"/"+productId+"/"+YSId+"/"+folder;
		
		String photoName = "";
		boolean isSuccess = false;
		HashMap<String, Object> jsonObj = new HashMap<String, Object>();
		
		if (recordid == null || recordid.equals("")) {
			finalUserId = BaseDAO.getGuId();
		} else {
			finalUserId = recordid;
		}
		String type = headPhotoFile[0].getOriginalFilename().substring(headPhotoFile[0].getOriginalFilename().lastIndexOf("."));
		photoName = finalUserId + "-" + CalendarUtil.fmtDate().replace(" ", "").replace(":", "").replace("-", ""); 
		
		try {
			//同时copy两份,一份到临时目录,显示用,另一个备份
			FileUtils.copyInputStreamToFile(headPhotoFile[0].getInputStream(), 
					new File(tempPath, photoName + type));

			FileUtils.copyInputStreamToFile(headPhotoFile[0].getInputStream(), 
					new File(realPath, photoName + type));
			
			isSuccess = true;			
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());

		}
		try {
			if (isSuccess) {
				jsonObj.put("result", "0");
				jsonObj.put("userId", finalUserId);
				jsonObj.put("path", BusinessConstants.PATH_PRODUCTDESIGNTEMP +
						productId+
						"/"+YSId+
						"/"+folder+
						File.separator + photoName + type);
			} else {
				jsonObj.put("result", "1");
				jsonObj.put("message", "头像上传失败");
			}
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return jsonObj;
	}
		
	public HashMap<String, Object> uploadFile(MultipartFile file,String folder) {
		B_ProductDesignData reqDesign = reqModel.getProductDesign();
		String recordid = request.getParameter("recordId");
		String productId = reqDesign.getProductid();
		String YSId = reqDesign.getYsid();
		//int id = Integer.parseInt(request.getParameter("id"));
		//String finalUserId = "";
		String orgFileName = file.getOriginalFilename();
		
		String tempPath = session.getServletContext().
				getRealPath(BusinessConstants.PATH_PRODUCTDESIGNTEMP)+
				"/"+productId+"/"+YSId+"/"+folder;		

		String realPath = session.getServletContext().
				getRealPath(BusinessConstants.PATH_PRODUCTDESIGNFILE)+
				"/"+productId+"/"+YSId+"/"+folder;
		
		String savePath = BusinessConstants.PATH_PRODUCTDESIGNTEMP +
				productId+ "/"+YSId+ "/"+folder+
				File.separator + orgFileName;
		boolean isSuccess = false;
		HashMap<String, Object> jsonObj = new HashMap<String, Object>();
		
		//String type = orgFileName.substring(orgFileName.lastIndexOf("."));
		
		try {
			//同时copy两份,一份到临时目录,显示用,另一个备份
			FileUtils.copyInputStreamToFile(file.getInputStream(), 
					new File(tempPath, orgFileName));

			FileUtils.copyInputStreamToFile(file.getInputStream(), 
					new File(realPath, orgFileName));
			
			//更新文字印刷DB
			updateTextPrint(recordid,orgFileName);
			
			isSuccess = true;			
			
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());

		}
		try {
			if (isSuccess) {
				jsonObj.put("result", "0");
				jsonObj.put("path", orgFileName);
			} else {
				jsonObj.put("result", "1");
				jsonObj.put("message", "上传失败");
			}
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return jsonObj;
	}


	public void downloadFile() throws Exception {

		//B_ProductDesignData reqDesign = reqModel.getProductDesign();
		//String recordid = request.getParameter("recordId");
		//String productId = reqDesign.getProductid();
		//String YSId = reqDesign.getYsid();
		String productId = request.getParameter("productId");
		String YSId = request.getParameter("YSId");

		JSONObject jsonObj = new JSONObject();
		
		//得到要下载的文件名
		String fileName = URLDecoder.decode(request.getParameter("fileName"),"UTF-8"); //
		//fileName = new String(fileName.getBytes("iso8859-1"),"UTF-8");
		//上传的文件都是保存在/WEB-INF/upload目录下的子目录当中
		String fileDownloadPath = session.getServletContext().
				getRealPath(BusinessConstants.PATH_PRODUCTDESIGNTEMP)+
				"/"+productId+"/"+YSId+"/"+"textPrint";	
		//通过文件名找出文件的所在目录
		//String path = findFileSavePathByFileName(fileName,fileSaveRootPath);
		//得到要下载的文件
		File file = new File(fileDownloadPath + "\\" + fileName);
		//如果文件不存在
		if(!file.exists()){
			request.setAttribute("message", "您要下载的资源已被删除！！");
			jsonObj.put("message", "下载失败");
			jsonObj.put("result", "1");
			//request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		//处理文件名
		String realname = fileName.substring(fileName.indexOf("_")+1);
		//设置响应头，控制浏览器下载该文件
		response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(realname, "UTF-8"));
		//读取要下载的文件，保存到文件输入流
		FileInputStream in = new FileInputStream(file);
		//创建输出流
		OutputStream out = response.getOutputStream();
		//创建缓冲区
		byte buffer[] = new byte[1024];
		int len = 0;
		//循环将输入流中的内容读取到缓冲区当中
		while((len=in.read(buffer))>0){
			//输出缓冲区的内容到浏览器，实现文件下载
			out.write(buffer, 0, len);
		}
		//关闭文件输入流
		in.close();
		//关闭输出流
		out.close();
	}
		 
		/**
		* @Method: findFileSavePathByFileName
		* @Description: 通过文件名和存储上传文件根目录找出要下载的文件的所在路径
		* @Anthor:孤傲苍狼
		* @param filename 要下载的文件名
		* @param saveRootPath 上传文件保存的根目录，也就是/WEB-INF/upload目录
		* @return 要下载的文件的存储目录
		*/
		public String findFileSavePathByFileName(String filename,String saveRootPath){
			//int hashcode = filename.hashCode();
			//int dir1 = hashcode&0xf; //0--15
			//int dir2 = (hashcode&0xf0)>>4; //0-15
			String dir = saveRootPath;
			//String dir = saveRootPath + "\\" + dir1 + "\\" + dir2; //upload\2\3 upload\3\5
			File file = new File(dir);
			if(!file.exists()){
				//创建目录
				file.mkdirs();
			}
			return dir;
		}
	
		public void deletePhoto (String path,String fileName)throws Exception {
	    	
			String tempPath = "";
			
			if(path == null){//这里只处理文字印刷的文件
				B_ProductDesignData reqDt = reqModel.getProductDesign();
				String YSId = reqDt.getYsid();
				String productId = reqDt.getProductid();
		    	//临时文件
				tempPath = session.getServletContext().
						getRealPath(BusinessConstants.PATH_PRODUCTDESIGNTEMP)+
						"/"+productId+"/"+YSId+"/"+"textPrint/"+ fileName;	
			}else{
		    	//临时文件
				tempPath = session.getServletContext().getRealPath(path);
			}
			
	    	//备份文件
	    	String realPath = tempPath.replaceFirst("img", "file");
	    			
	    	//String file = pathAndName.replace('/', '\\') ;
	    	//String file = ctxPath + path.replace('/', '\\') ;
	    	//String fileSmall = ctxPath + BusinessConstants.BUSINESSPHOTOPATH.replace('/', '\\') + key + "\\" +  BusinessConstants.BUSINESSSMALLPHOTOPATH.replace('/', '\\') + fileName.replace('/', '\\');

	    	File f1 = new File(tempPath); //临时文件
	    	if(f1.exists()) {
	    		f1.delete(); 
	    	}
	    	
	    	File f = new File(realPath); //备份文件
	    	if(f.exists()) {
	    		f.delete(); 
	    	}
	    	
	    }
	    
}
