package com.ys.business.service.order;

import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

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
import com.ys.util.basequery.common.BaseModel;
import com.ys.util.basequery.common.Constants;
import com.ys.util.CalendarUtil;
import com.ys.util.DicUtil;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basedao.BaseTransaction;
import com.ys.util.basequery.BaseQuery;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class ProductDesignService extends CommonService {
 
	DicUtil util = new DicUtil();
	BaseTransaction ts;

	String guid ="";
	private CommFieldsData commData;
	
	private HttpServletRequest request;
	
	private B_ProductDesignDao dao;
	private ProductDesignModel reqModel;
	private UserInfo userInfo;
	private BaseModel dataModel;
	private  Model model;
	private HashMap<String, String> userDefinedSearchCase;
	private BaseQuery baseQuery;
	private MultipartFile[] textPrintFile;
	HashMap<String, Object> modelMap = null;
	HttpSession session;

	public ProductDesignService(){
		
	}

	public ProductDesignService(
			Model model,
			HttpServletRequest request,
			HttpSession session,
			ProductDesignModel reqModel,
			UserInfo userInfo){
		
		this.dao = new B_ProductDesignDao();
		this.model = model;
		this.reqModel = reqModel;
		this.request = request;
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

		String[] keyArr = getSearchKey(Constants.FORM_ARRIVAL,data,session);
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
		
		String[] keyArr = getSearchKey(Constants.FORM_ARRIVAL,data,session);
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

	public boolean addOrView() throws Exception {

		String YSId = request.getParameter("YSId");
		boolean rtn = checkProductDesignlById(YSId);
		
		if(rtn){//做单资料已存在的话,取得明细内容
			getProductDesignById(YSId);
		}else{
			addInit(YSId);
		}
		return rtn;
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

	public void showArrivalDetail() {

		String arrivalId = request.getParameter("arrivalId");
		getArrivaRecord(arrivalId);
	}
	
	public void insertAndView() throws Exception {

		String YSId = insert();
		getProductDesignById(YSId);
	}
	
	public void UpdateAndView() throws Exception {

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
			//做单资料编号:耀升编号-yymmdd
			String productdetailid=YSId+"-"+CalendarUtil.getDateyymmdd();
			design.setProductdetailid(productdetailid);
			updateProductDesign(design);
			
			//做单资料明细部分
			
			//0:先删除旧数据
			deleteProductDesignDetail(productdetailid);
			
			//1:机器配置清单;
			List<B_ProductDesignDetailData> machineList = reqModel.getMachineConfigList();
			for(B_ProductDesignDetailData d:machineList){
				String name = d.getComponentname();
				if(name == null || ("").equals(name))
					continue;
				d.setProductdetailid(productdetailid);
				d.setType(Constants.PRODUCTDETAIL_1);
				
				insertProductDesignDetail(d);
			}
			
			//2:塑料制品清单;
			List<B_ProductDesignDetailData> plasticList = reqModel.getPlasticList();
			for(B_ProductDesignDetailData d:plasticList){
				String name = d.getComponentname();
				if(name == null || ("").equals(name))
					continue;
				d.setProductdetailid(productdetailid);
				d.setType(Constants.PRODUCTDETAIL_2);
				
				insertProductDesignDetail(d);
			}
			
			
			//3:配件清单;
			List<B_ProductDesignDetailData> accessList = reqModel.getAccessoryList();
			for(B_ProductDesignDetailData d:accessList){
				String name = d.getComponentname();
				if(name == null || ("").equals(name))
					continue;
				d.setProductdetailid(productdetailid);
				d.setType(Constants.PRODUCTDETAIL_3);
				
				insertProductDesignDetail(d);
			}
			
			//5:标贴;
			List<B_ProductDesignDetailData> labelList = reqModel.getLabelList();
			for(B_ProductDesignDetailData d:labelList){
				String name = d.getComponentname();
				if(name == null || ("").equals(name))
					continue;
				d.setProductdetailid(productdetailid);
				d.setType(Constants.PRODUCTDETAIL_5);
				
				insertProductDesignDetail(d);
			}
			
			//6:文字印刷;
			List<B_ProductDesignDetailData> textList = reqModel.getTextPrintList();
			for(B_ProductDesignDetailData d:textList){
				String name = d.getComponentname();
				if(name == null || ("").equals(name))
					continue;
				d.setProductdetailid(productdetailid);
				d.setType(Constants.PRODUCTDETAIL_6);
				//文件处理//保存文件的目录  
				 if(null != textPrintFile && textPrintFile.length > 0){  
			            //遍历并保存文件  
			            for(MultipartFile file : textPrintFile){  
					        String savePath = request.getSession().
					        		getServletContext().getRealPath("/") 
					        		+ "/file/productDesign/"
					        		+YSId+"/";
			                file.transferTo(new File(savePath + file.getOriginalFilename()));  
			            }  
			        }  
				insertProductDesignDetail(d);
			}
			
			//6:包装描述;
			List<B_ProductDesignDetailData> packageList = reqModel.getPackageList();
			for(B_ProductDesignDetailData d:packageList){
				String name = d.getComponentname();
				if(name == null || ("").equals(name))
					continue;
				d.setProductdetailid(productdetailid);
				d.setType(Constants.PRODUCTDETAIL_7);
				
				insertProductDesignDetail(d);
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
	
	
	
	@SuppressWarnings("rawtypes")
	private boolean checkProductDesignlById(String YSId) {

		boolean flag = false;
		String where = " YSId = '"+YSId+"' AND deleteFlag='0' ";
		try {
			Vector list = dao.Find(where);
			if(list != null && list.size()>0)
				flag=true;
		} catch (Exception e) {
			// nothing
		}
		return flag;
	}
	
	private void getArrivaRecord(String arrivalId){
		
		try {
			dataModel.setQueryName("getArrivaRecord");
			userDefinedSearchCase.put("arrivalId", arrivalId);
			baseQuery = new BaseQuery(request, dataModel);
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
			baseQuery.getYsFullData();

			model.addAttribute("arrival",dataModel.getYsViewData().get(0));
			//String userId = dataModel.getYsViewData().get(0).get("userId");
			
			model.addAttribute("arrivalList",dataModel.getYsViewData());
			
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}
	
	public void updateInit() throws Exception{

		String YSId = request.getParameter("YSId");
		
		//做单资料明细内容
		getProductDesignById(YSId);		

		//机器配置信息
		getMachineConfiguration();
		
		//塑料制品
		getPlastic();
		
		//配件清单
		getAccessory();
		
		//标贴
		getLabel();
		
		//文字印刷
		getTextPrint();
		
		//包装描述
		getPackage();
		
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
	
	private void updateProductDesign(
			B_ProductDesignData mData) throws Exception{
		B_ProductDesignData db = null;
		
		try{
			db = new B_ProductDesignDao(mData).beanData;
		}catch(Exception e){
			//nothing
		}
		if(db == null || ("").equals(db)){
			insertProductDesign(mData);//找不到就新增一条数据
			return;
		}
		
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"ProductDesignUpdate",userInfo);
		copyProperties(mData,commData);	
		mData.setPackagedescription(
				replaceTextArea(mData.getPackagedescription()));//字符转换
		mData.setStoragedescription(
				replaceTextArea(mData.getStoragedescription()));	
	
		dao.Store(mData);	
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
		
		B_ProductDesignDetailDao dao = new B_ProductDesignDetailDao();
		
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"ProductDesignDetailInsert",userInfo);
		copyProperties(mData,commData);		
		guid = BaseDAO.getGuId();
		mData.setRecordid(guid);
		
		dao.Create(mData);	
	}
	
	private void deleteProductDesignDetail(
			String  productdetailid) throws Exception{
		
		B_ProductDesignDetailDao dao = new B_ProductDesignDetailDao();

		String astr_Where = " productDetailId = '"+productdetailid+"' ";
		dao.RemoveByWhere(astr_Where);	
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
	
	public HashMap<String, Object> getMachineConfiguration() throws Exception {
		
		String productDetailId = request.getParameter("productDetailId");
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
	
	public HashMap<String, Object> getPlastic() throws Exception {
		
		String productDetailId = request.getParameter("productDetailId");
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
	
	public HashMap<String, Object> getAccessory() throws Exception {
		
		String productDetailId = request.getParameter("productDetailId");
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
		
		getPackagePhoto(YSId,productId,"storage","storageFileList","storageFileCount");
		
		return modelMap;
	}
	
	
	public HashMap<String, Object> getProductPhoto() throws Exception {
		
		String YSId = request.getParameter("YSId");
		String productId = request.getParameter("productId");
		//getProductDesignById(YSId);
		
		getPackagePhoto(YSId,productId,"product","productFileList","productFileCount");
		
		return modelMap;
	}
	
	public HashMap<String, Object> getLabelAndPhoto() throws Exception {
		
		getLabel();//标贴
		String YSId = request.getParameter("YSId");
		String productId = request.getParameter("productId");
		//getProductDesignById(YSId);
		
		getPackagePhoto(YSId,productId,"label","labelFileList","labelFileCount");
		
		return modelMap;
	}
	public HashMap<String, Object> getLabel() throws Exception {
		
		String productDetailId = request.getParameter("productDetailId");
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


	public HashMap<String, Object> getTextPrint() throws Exception {
		
		String productDetailId = request.getParameter("productDetailId");
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
	
	public HashMap<String, Object> getPackageAndPhoto() throws Exception {
		
		getPackage();//包装描述
		String YSId = request.getParameter("YSId");
		String productId = request.getParameter("productId");
		//getProductDesignById(YSId);
		
		getPackagePhoto(YSId,productId,"package","packageFileList","packageFileCount");
		
		return modelMap;
	}
	

	public void getPackagePhoto(
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
	
	public HashMap<String, Object> getPackage() throws Exception {
		
		String productDetailId = request.getParameter("productDetailId");
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
			d.setPackagedescription(dataModel.getYsViewData().get(0).get("packageDescription"));
			d.setSealedsample(dataModel.getYsViewData().get(0).get("sealedSample"));
			d.setBatterypack(dataModel.getYsViewData().get(0).get("batteryPackId"));
			d.setChargertype(dataModel.getYsViewData().get(0).get("chargerTypeId"));
			d.setStoragedescription(dataModel.getYsViewData().get(0).get("storageDescription"));
			reqModel.setProductDesign(d);

		}
		
	}
	
	
	public JSONObject uploadPhoto(MultipartFile[] headPhotoFile,String folder) {
		B_ProductDesignData reqDesign = reqModel.getProductDesign();
		String recordid = reqDesign.getRecordid();
		String productId = reqDesign.getProductid();
		String YSId = reqDesign.getYsid();
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
		JSONObject jsonObj = new JSONObject();
		
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

}
