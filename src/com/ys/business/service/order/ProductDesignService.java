package com.ys.business.service.order;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.AreaBreakType;
import com.itextpdf.layout.property.UnitValue;
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
import com.ys.util.PdfUnit;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basedao.BaseTransaction;
import com.ys.util.basequery.BaseQuery;
import com.ys.util.basequery.common.BaseModel;
import com.ys.util.basequery.common.Constants;

@Service
public class ProductDesignService extends CommonService  {
 
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
		
		dataModel.setQueryName("getProductDesignList");
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
		if(notEmpty(key1) ||  notEmpty(key2)){
			userDefinedSearchCase.put("status1", "");
		}
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		String sql = getSortKeyFormWeb(data,baseQuery);	
		baseQuery.getYsQueryData(sql,iStart, iEnd);	
				
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
				getProductDesignById(oldYsid);
				getOrderDetailById(reqYsid,"edit");

				copyToEdit(oldYsid);
				photoCopy(productid,productid,reqYsid,oldYsid);
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
	

	public void doCopyToEdit() throws Exception {

		String newYsid = request.getParameter("newYSId");
		String oldYsid = request.getParameter("oldYSId");
		String PIId = request.getParameter("PIId");
		String productid = request.getParameter("productId");
		String goBackFlag = request.getParameter("goBackFlag");
		

		//1.判断画面来的耀升编号是否有做单资料
		B_ProductDesignData reqDb = checkProductDesignlById(newYsid);
		
		if(!(reqDb == null || ("").equals(reqDb))){
			//显示该产品最近的耀升编号到编辑页面
			String newProductId = reqDb.getProductid();
			getProductDesignById(newYsid);
			getOrderDetailById(oldYsid,"edit");
			
			copyToEdit(newYsid);
			photoCopy(newProductId,productid,oldYsid,newYsid);
		}

		model.addAttribute("YSId",oldYsid);
		model.addAttribute("PIId",PIId);
		model.addAttribute("goBackFlag",goBackFlag);
			
	}
	
	private void copyToEdit(String YSId) throws Exception{
		
		String productDetailId = getProductDesignlByYSId(YSId);
	
		getShareInfo(productDetailId);
	}
	
	private void photoCopy(
			String productid,
			String newProductid,String newYsid,String oldYsid){
		
		String viewOldFld = session.getServletContext().
				getRealPath(BusinessConstants.PATH_PRODUCTDESIGNVIEW)+
				"/"+productid+"/"+oldYsid;
		String viewNewFld = session.getServletContext().
				getRealPath(BusinessConstants.PATH_PRODUCTDESIGNVIEW)+
				"/"+newProductid+"/"+newYsid;

    	//存储目录
    	String realOldPath = viewOldFld.replaceFirst("img", "file");
    	String realNewPath = viewNewFld.replaceFirst("img", "file");
    	
    	folderCopy(viewOldFld, viewNewFld);
    	folderCopy(realOldPath, realNewPath);
    	
		
	}
	
	public boolean deleteTextPrintFile() throws Exception{
		
		String fileName = request.getParameter("fileName");
		
		return deletePhoto(null,fileName);
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
			getOrderDetailById(YSId,"add");
		
		setDicList();//设置下拉框内容
	
	}
	
	public String doShowDetailHistory() throws Exception{

		String productId = request.getParameter("productId");
		String reqYsid = request.getParameter("YSId");
		
		String ysid = getRpsList(productId,reqYsid);

		if(ysid != null){
			getProductDesignById(ysid);
		}
		
		return ysid;
	}
	
	private String getRpsList(String productId,String reqYsid){
		
		String rtnYsid = reqYsid;
		
		List<B_ProductDesignData> dbList = 
				getProductDesignlByProductId(productId);
		
		if(dbList == null || dbList.size()<=0){
			return null;
		}
				
		HashMap<String, String> map = new HashMap<String, String>();
		boolean reqFlag=false;
		int size = dbList.size(); 
		for(int i=0;i<size;i++){
			
			B_ProductDesignData d = dbList.get(i);
			
			//找到页面来的耀升编号
			if((d.getYsid()).equals(reqYsid)){
				
				if(i>0){
					//上一条
					map.put("ysid_prev", dbList.get(i-1).getYsid());
				}else{
					//第一条就匹配上了,所以没有上一条
					map.put("ysid_prev","");		
				}
				map.put("ysid",dbList.get(i).getYsid());
				
				i++;
				if(i < size){
					//有两条以上,有下一条
					map.put("ysid_next",dbList.get(i).getYsid());
				}else{
					//只有一条的话,没有下一条
					map.put("ysid_next","");
				}
				reqFlag = true;
				break;				
			}			
		}//for
		
		if(reqFlag){

			model.addAttribute("ysidMap",map);
			return rtnYsid;
		}
		
		//该耀升编号没有做单资料的话,默认显示该产品最近的耀升编号
		if(size > 1){
			//2条以上
			map.put("ysid_prev","");
			map.put("ysid", dbList.get(0).getYsid());
			map.put("ysid_next",dbList.get(1).getYsid());
		}else{
			//1条
			map.put("ysid_prev","");
			map.put("ysid", dbList.get(0).getYsid());
			map.put("ysid_next","");
		}
		
		rtnYsid = dbList.get(0).getYsid();
		
		model.addAttribute("ysidMap",map);
		
		return rtnYsid;
	}
	
	private void setDicList() throws Exception{

		model.addAttribute("chargerTypeList",
				util.getListOption(DicUtil.DIC_CHARGERTYPE, ""));
		model.addAttribute("batteryPackList",
				util.getListOption(DicUtil.DIC_BATTERYPACK, ""));
		model.addAttribute("purchaserList",
				util.getListOption(DicUtil.DIC_PURCHASER, ""));
		model.addAttribute("statusList",
				util.getListOption(DicUtil.DIC_PRODUCTDESIGNSTATUS, ""));
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
			String productId = design.getProductid();
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

				//文件处理
				String filename = d.getFilename();
				if(!(filename == null || ("").equals(filename)))
					if(fileCopyToStorageFolder(filename,productId,YSId))
						d.setFilename("");//文件上传失败,清空文件名
				//数据处理
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

	
	@SuppressWarnings({ "unchecked" })
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
	
	
	@SuppressWarnings({ "unchecked" })
	private List<B_ProductDesignData> getProductDesignlByProductId(String productId) {

		List<B_ProductDesignData> dbList = new ArrayList<B_ProductDesignData>();

		String where = " productId = '"+productId+"' AND deleteFlag='0' order by YSId DESC ";
		try {
			dbList = (List<B_ProductDesignData>)dao.Find(where);
			
		} catch (Exception e) {
			// nothing
		}
		return dbList;
	}

	@SuppressWarnings({ "unchecked" })
	private String getProductDesignlByYSId(String YSId) {

		String productDetailId = "";

		String where = " YSId = '"+YSId+"' AND deleteFlag='0' order by YSId DESC ";
		try {
			List<B_ProductDesignData> dbList = (List<B_ProductDesignData>)dao.Find(where);
			
			if(!(dbList == null || ("").equals(dbList))){
				B_ProductDesignData data = dbList.get(0);
				productDetailId = data.getProductdetailid();
				reqModel.setProductDesign(data);
				
			}
		} catch (Exception e) {
			// nothing
		}
		return productDetailId;
	}
	
	private void getShareInfo(String productDetailId) throws Exception{
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
	
	public void updateInit(String YSId) throws Exception{

		
		//做单资料明细内容
		getProductDesignById(YSId);		
		String productDetailId = reqModel.getProductDesign().getProductdetailid();
		
		getShareInfo(productDetailId);
		
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
		mData.setRecordid(db.getRecordid());
		mData.setCreateperson(db.getCreateperson());
		mData.setCreatetime(db.getCreatetime());
		mData.setDeptguid(db.getDeptguid());
		mData.setCreateunitid(db.getCreateunitid());
		mData.setProductdetailid(db.getProductdetailid());
		mData.setSubid(db.getSubid());
		mData.setPackagedescription(
				replaceTextArea(mData.getPackagedescription()));//字符转换
		mData.setStoragedescription(
				replaceTextArea(mData.getStoragedescription()));	
	
		dao.Store(mData);	
		
		productdetailid = db.getProductdetailid();
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
			String  productdetailid) throws Exception{
		
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
		
	public HashMap<String, Object> getProductPhoto() throws Exception {
		
		String YSId = request.getParameter("YSId");
		String productId = request.getParameter("productId");
		//getProductDesignById(YSId);
		
		getPhoto(YSId,productId,"product","productFileList","productFileCount");
		
		return modelMap;
	}
	

	public HashMap<String, Object> getProductStoragePhoto() throws Exception {
		
		String YSId = request.getParameter("YSId");
		String productId = request.getParameter("productId");
		//getProductDesignById(YSId);
		
		getPhoto(YSId,productId,"storage","storageFileList","storageFileCount");
		
		return modelMap;
	}
	
	public HashMap<String, Object> getPackagePhoto() throws Exception {
		
		String YSId = request.getParameter("YSId");
		String productId = request.getParameter("productId");

		getPhoto(YSId,productId,"package","packageFileList","packageFileCount");
		
		return modelMap;
	}
	

	public HashMap<String, Object> getLabelPhoto() throws Exception {
		
		String YSId = request.getParameter("YSId");
		String productId = request.getParameter("productId");
		//getProductDesignById(YSId);

		getPhoto(YSId,productId,"label","labelFileList","labelFileCount");
		
		return modelMap;
	}
	
	public HashMap<String, Object> getLabelAndPhoto() throws Exception {
		
		String YSId = request.getParameter("YSId");
		String productId = request.getParameter("productId");
		String productDetailId = request.getParameter("productDetailId");
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
				
		String backPath = session.getServletContext().
				getRealPath(BusinessConstants.PATH_PRODUCTDESIGNFILE)+
				"/"+productId+"/"+YSId+"/"+folderName;	
		String viewPath = BusinessConstants.PATH_PRODUCTDESIGNVIEW+
				productId+"/"+YSId+"/"+folderName+"/";	
		
		try {
			ArrayList<String> list = getFiles(backPath,viewPath);
			modelMap.put(fileList, list);
			modelMap.put(fileCount, list.size());
							
		}
		catch(Exception e) {
			System.out.println(e.getMessage());

		}		
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
	
	public void getOrderDetailById(String YSId,String type) throws Exception {
		
		dataModel.setQueryName("getOrderDetailById");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("YSId", YSId);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		if(dataModel.getRecordCount() >0){

			dataModel.getYsViewData().get(0)
				.put("productId",dataModel.getYsViewData().get(0).get("materialId"));
			model.addAttribute("product",dataModel.getYsViewData().get(0));
			
			if(("edit").equals(type)){			
				model.addAttribute("deliveryDate",dataModel.getYsViewData().get(0).get("deliveryDate"));//交货时间
				model.addAttribute("quantity",dataModel.getYsViewData().get(0).get("quantity"));//交货数量
			}
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
			//复制新建用
			model.addAttribute("deliveryDate",dataModel.getYsViewData().get(0).get("deliveryDate"));//交货时间
			model.addAttribute("quantity",dataModel.getYsViewData().get(0).get("quantity"));//交货数量

		}
		
	}
	
	
	public HashMap<String, Object> uploadPhotoToTempFolder(
			MultipartFile[] headPhotoFile,String folder,
			String recordid,String productId,String YSId) {
				
		String tempPath = session.getServletContext().
				getRealPath(BusinessConstants.PATH_PRODUCTDESIGNTEMP);	
		
		String photoName = "";
		String orgFileName = "";
		String newFileName = "";
		boolean isSuccess = false;
		HashMap<String, Object> jsonObj = new HashMap<String, Object>();
		

		orgFileName = headPhotoFile[0].getOriginalFilename();
		photoName = orgFileName + "-" + CalendarUtil.fmtDate().replace(" ", "").replace(":", "").replace("-", ""); 
		
		String suffix = orgFileName.substring(orgFileName.lastIndexOf("."));
		newFileName = photoName + suffix;
		
		try {
			//上传到临时目录
			FileUtils.copyInputStreamToFile(headPhotoFile[0].getInputStream(), 
					new File(tempPath, newFileName));			
			
			isSuccess = true;			
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());

		}
		try {
			if (isSuccess) {
				jsonObj.put("result", "0");
				jsonObj.put("path", BusinessConstants.PATH_PRODUCTDESIGNTEMP +
						File.separator + newFileName);
			} else {
				jsonObj.put("result", "1");
				jsonObj.put("message", "图片上传失败");
			}
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return jsonObj;
	}
		

	public HashMap<String, Object> uploadPhoto(
			MultipartFile[] headPhotoFile,String folder,
			String recordid,String productId,String YSId
			) {
		
		String finalUserId = "";
		
		String tempPath = session.getServletContext().
				getRealPath(BusinessConstants.PATH_PRODUCTDESIGNVIEW)+
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
		photoName = finalUserId + "-" + CalendarUtil.timeStempDate(); 
		
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
				jsonObj.put("path", BusinessConstants.PATH_PRODUCTDESIGNVIEW +
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
		//String recordid = request.getParameter("recordId");
		String productId = reqDesign.getProductid();
		String YSId = reqDesign.getYsid();
		//int id = Integer.parseInt(request.getParameter("id"));
		//String finalUserId = "";
		String orgFileName = file.getOriginalFilename();
		
		String tempPath = session.getServletContext().
				getRealPath(BusinessConstants.PATH_PRODUCTDESIGNVIEW)+
				"/"+productId+"/"+YSId+"/"+folder;		

		String realPath = session.getServletContext().
				getRealPath(BusinessConstants.PATH_PRODUCTDESIGNFILE)+
				"/"+productId+"/"+YSId+"/"+folder;
		
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
			//updateTextPrint(recordid,orgFileName);
			
			isSuccess = true;			
			
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());

		}
		try {
			if (isSuccess) {
				jsonObj.put("result", "0");
				jsonObj.put("fileName", orgFileName);
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

	public HashMap<String, Object> uploadFileToTempFolder(
			MultipartFile file,String folder) {
		
		B_ProductDesignData reqDesign = reqModel.getProductDesign();
		//String productId = reqDesign.getProductid();
		//String YSId = reqDesign.getYsid();
		String orgFileName = file.getOriginalFilename();
		
		String tempPath = session.getServletContext().
				getRealPath(BusinessConstants.PATH_PRODUCTDESIGNTEMP);
				//"/"+productId+"/"+YSId+"/"+folder;		
		
		boolean isSuccess = false;
		HashMap<String, Object> jsonObj = new HashMap<String, Object>();

		String timesTmp = CalendarUtil.timeStempDate(); 
		String suffix = orgFileName.substring(orgFileName.lastIndexOf("."));
		String fileName = orgFileName.substring(0, orgFileName.length()-suffix.length())+ "-" + timesTmp; 
		String newFileName = fileName + suffix;
		
		try {
			//文件上传到临时目录
			FileUtils.copyInputStreamToFile(file.getInputStream(), 
					new File(tempPath, newFileName));
			
			isSuccess = true;		
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());

		}
		try {
			if (isSuccess) {
				jsonObj.put("result", "0");
				jsonObj.put("fileName", newFileName);
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
	
	@SuppressWarnings("unchecked")
	public void accessoryConvertAndDownloadToPdf(){
		String YSId = request.getParameter("YSId");
		String productId = request.getParameter("productId");
		String productDetailId = request.getParameter("productDetailId");
		String productClassify = request.getParameter("productClassify1");
		ArrayList<String> list ;
		ArrayList<HashMap<String,String>> mapList;		
		PdfUnit pdf = new PdfUnit(session,response,YSId);  
		Table table;
		Cell cell;
		UnitValue unitValue[];
		
		productClassify = productClassify == null?"":productClassify;
		
	    try {
			
	    	//主题	 
			pdf.doc.add(pdf.addTitleForCenter("配件做单资料"));
			//标题
			pdf.doc.add(pdf.addTitle("订单信息"));
			//做单资料基本信息
			getProductDesignById(YSId);			
			HashMap<String,String> product = 
					(HashMap<String, String>) model.asMap().get("product");
			table = createHeadTable(product,pdf);  
			pdf.doc.add(table); 
				
			//产品图片***************************************************************
			//产品图片-标题
			pdf.doc.add(pdf.addTitle(""));
			pdf.doc.add(pdf.addTitle("产品图片"));
			//产品图片-图片列表
			getPhoto(YSId,productId,"product","productFileList","productFileCount");
			list = (ArrayList<String>) modelMap.get("productFileList");			
			table = createPhotoTable(list,pdf);
			pdf.doc.add(table); 
			
			//产品收纳***************************************************************
			//产品收纳-标题
			pdf.doc.add(pdf.addTitle(""));
			pdf.doc.add(pdf.addTitle("产品收纳-描述信息"));
			unitValue = new UnitValue[]{
                    UnitValue.createPercentValue((float) 100)};
			table = pdf.addTable(unitValue).setMinHeight(50);			
			cell = pdf.addCell(pdf.addContent(
							product.get("storageDescription").replace("<br>", "\n")));
			table.addCell(cell);
			pdf.doc.add(table);
			
			//标贴***************************************************************
			//标贴-标题
			pdf.doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));//换页
			pdf.doc.add(pdf.addTitle(""));
			pdf.doc.add(pdf.addTitle("标贴-描述信息"));
			//标贴-列表
			unitValue = new UnitValue[]{
	                    UnitValue.createPercentValue((float) 5),
	                    UnitValue.createPercentValue((float) 20),
	                    UnitValue.createPercentValue((float) 40),
	                    UnitValue.createPercentValue((float) 20),
	                    UnitValue.createPercentValue((float) 15)};
		    table = pdf.addTable(unitValue);
		    
		    getLabel(productDetailId);
			mapList = (ArrayList<HashMap<String, String>>) model.asMap().get("labelList");
			String[] arrTitleL =
				{ "No","名称","材质及要求","尺寸","备注"}; 
			String[] arrContentL =
				{"rownum","componentName","materialQuality","size","remark"};
			table = createTextTable(arrTitleL,arrContentL,mapList,pdf,table);	
			pdf.doc.add(table); //标贴
			
			//标贴***************************************************************
			//标贴图片-标题
			pdf.doc.add(pdf.addTitle(""));
			pdf.doc.add(pdf.addTitle("标贴-图片"));
			//标贴图片-列表
			getPhoto(YSId,productId,"label","labelFileList","labelFileCount");
			list = (ArrayList<String>) modelMap.get("labelFileList");
			table = createLabelPhotoTable(list,pdf);
			pdf.doc.add(table);
			
			//包装描述***************************************************************
			//包装描述-标题
			pdf.doc.add(pdf.addTitle(""));
			pdf.doc.add(pdf.addTitle("包装描述"));
			//包装描述-列表
			unitValue = new UnitValue[]{
                    UnitValue.createPercentValue((float) 5),
                    UnitValue.createPercentValue((float) 20),
                    UnitValue.createPercentValue((float) 30),
                    UnitValue.createPercentValue((float) 10),
                    UnitValue.createPercentValue((float) 15),
                    UnitValue.createPercentValue((float) 20)};
		    table = pdf.addTable(unitValue);
		    
		    getPackage(productDetailId);
			mapList = (ArrayList<HashMap<String, String>>) model.asMap().get("packageList");
			String[] titleP =
				{ "No","名称","材质","装箱数量","尺寸","备注"}; 
			String[] contentP =
				{"rownum","componentName","materialQuality","packingQty","size","remark"};
			table = createTextTable(titleP,contentP,mapList,pdf,table);	
			pdf.doc.add(table); 

        	pdf.doc.close();  
        	
        	//***********************PDF下载************************//
        	pdf.downloadFile();
        	
        } catch (Exception e) {  
            e.printStackTrace();  
        }  finally {
        	if (pdf.doc !=null){
        		pdf.doc.flush();
        		pdf.doc.close(); 
        		pdf.pdf.close(); 
        	}
		}
	}

	@SuppressWarnings("unchecked")
	public void convertAndDownloadToPdf(){
		String YSId = request.getParameter("YSId");
		String productId = request.getParameter("productId");
		String productDetailId = request.getParameter("productDetailId");
		String productClassify = request.getParameter("productClassify");
		ArrayList<String> list ;
		ArrayList<HashMap<String,String>> mapList;		
		PdfUnit pdf = new PdfUnit(session,response,YSId);  
		Table table;
		Cell cell;
		UnitValue unitValue[];
		
		productClassify = productClassify == null?"":productClassify;
		
	    try {
			
	    	//主题	 
			pdf.doc.add(pdf.addTitleForCenter("做单资料"));
			//标题
			pdf.doc.add(pdf.addTitle("订单信息"));
			//做单资料基本信息
			getProductDesignById(YSId);			
			HashMap<String,String> product = 
					(HashMap<String, String>) model.asMap().get("product");
			table = createHeadTable(product,pdf);  
			pdf.doc.add(table); 
	        
			if(("010").equals(productClassify)){//电动工具

				//机器配置-标题***************************************************************
				pdf.doc.add(pdf.addTitle(""));
				pdf.doc.add(pdf.addTitle("机器配置"));
				//机器配置-列表
				unitValue = new UnitValue[]{
		                    UnitValue.createPercentValue((float) 5),
		                    UnitValue.createPercentValue((float) 13),
		                    UnitValue.createPercentValue((float) 18),
		                    UnitValue.createPercentValue((float) 37),
		                    UnitValue.createPercentValue((float) 10),
		                    UnitValue.createPercentValue((float) 7),
		                    UnitValue.createPercentValue((float) 10)};
			    table = pdf.addTable(unitValue);
			    
				getMachineConfiguration(productDetailId);
				mapList = (ArrayList<HashMap<String, String>>) model.asMap().get("machineConfigList");
				String[] arrTitleM =
					{ "No","名称","ERP编码","产品名称","供应商","采购方","备注"}; 
				String[] arrContentM =
					{"rownum","componentName","materialId","materialName","supplierId", "purchaser","remark"}; 
				table = createTextTable(arrTitleM,arrContentM,mapList,pdf,table);
				
				pdf.doc.add(table); 
			}		
			
			//产品图片***************************************************************
			//产品图片-标题
			pdf.doc.add(pdf.addTitle(""));
			pdf.doc.add(pdf.addTitle("产品图片"));
			//产品图片-图片列表
			getPhoto(YSId,productId,"product","productFileList","productFileCount");
			list = (ArrayList<String>) modelMap.get("productFileList");			
			table = createPhotoTable(list,pdf);
			pdf.doc.add(table); 
			
			if(("010").equals(productClassify) || ("020").equals(productClassify)){

				//塑料制品***************************************************************
				//塑料制品-标题
				pdf.doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));//换页
				pdf.doc.add(pdf.addTitle(""));
				pdf.doc.add(pdf.addTitle("塑料制品"));
				//塑料制品-列表
				unitValue = new UnitValue[]{
		                    UnitValue.createPercentValue((float) 5),
		                    UnitValue.createPercentValue((float) 15),
		                    UnitValue.createPercentValue((float) 18),
		                    UnitValue.createPercentValue((float) 35),
		                    UnitValue.createPercentValue((float) 7),
		                    UnitValue.createPercentValue((float) 13),
		                    UnitValue.createPercentValue((float) 7)};
			    table = pdf.addTable(unitValue);
			    
			    getPlastic(productDetailId);
				mapList = (ArrayList<HashMap<String, String>>) model.asMap().get("plasticList");
				String[] arrTitleP =
					{ "No","名称","ERP编码","产品名称","材质","颜色","备注"}; 
				String[] arrContentP =
					{"rownum","componentName","materialId","materialName","materialQuality", "color","remark"}; 
				table = createTextTable(arrTitleP,arrContentP,mapList,pdf,table);
				
				pdf.doc.add(table);
			}
			if(("010").equals(productClassify)){//电动工具

				//配件清单***************************************************************
				//配件清单-标题
				pdf.doc.add(pdf.addTitle(""));
				pdf.doc.add(pdf.addTitle("配件清单"));
				//配件清单-列表
				unitValue = new UnitValue[]{
	                    UnitValue.createPercentValue((float) 5),
	                    UnitValue.createPercentValue((float) 40),
	                    UnitValue.createPercentValue((float) 15),
	                    UnitValue.createPercentValue((float) 15),
	                    UnitValue.createPercentValue((float) 15),
	                    UnitValue.createPercentValue((float) 10)};
			    table = pdf.addTable(unitValue);
			    
			    getAccessory(productDetailId);
				mapList = (ArrayList<HashMap<String, String>>) model.asMap().get("accessoryList");
				String[] titleS =
					{ "No","名称及规格描述","材质","加工方式","表面处理","备注"}; 
				String[] contentS =
					{"rownum","componentName","materialQuality","process","specification","remark"};
				table = createTextTable(titleS,contentS,mapList,pdf,table);	
				pdf.doc.add(table);
			}
			
			//文字印刷***************************************************************
			//文字印刷-标题
			pdf.doc.add(pdf.addTitle(""));
			pdf.doc.add(pdf.addTitle("文字印刷"));
			//文字印刷-列表
			unitValue = new UnitValue[]{
                    UnitValue.createPercentValue((float) 5),
                    UnitValue.createPercentValue((float) 15),
                    UnitValue.createPercentValue((float) 30),
                    UnitValue.createPercentValue((float) 10),
                    UnitValue.createPercentValue((float) 10),
                    UnitValue.createPercentValue((float) 30)};
		    table = pdf.addTable(unitValue);
		    
		    getTextPrint(productDetailId);
			mapList = (ArrayList<HashMap<String, String>>) model.asMap().get("textPrintList");
			String[] titleT =
				{ "No","名称","材质","尺寸","颜色","文件名"}; 
			String[] contentT =
				{"rownum","componentName","materialQuality","size","color","fileName"};
			table = createTextTable(titleT,contentT,mapList,pdf,table);	
			pdf.doc.add(table); 
			
			//产品收纳***************************************************************
			//产品收纳-标题
			pdf.doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));//换页
			pdf.doc.add(pdf.addTitle(""));
			pdf.doc.add(pdf.addTitle("产品收纳-描述信息"));
			unitValue = new UnitValue[]{
                    UnitValue.createPercentValue((float) 100)};
			table = pdf.addTable(unitValue).setMinHeight(50);			
			cell = pdf.addCell(pdf.addContent(
							product.get("storageDescription").replace("<br>", "\n")));
			table.addCell(cell);
			pdf.doc.add(table);
			//产品收纳-图片
			pdf.doc.add(pdf.addTitle(""));
			pdf.doc.add(pdf.addTitle("产品收纳-图片"));
			getPhoto(YSId,productId,"storage","storageFileList","storageFileCount");
			list = (ArrayList<String>) modelMap.get("storageFileList");
	        table = createPhotoTable(list,pdf);
			pdf.doc.add(table);
			
			//标贴***************************************************************
			//标贴-标题
			pdf.doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));//换页
			pdf.doc.add(pdf.addTitle(""));
			pdf.doc.add(pdf.addTitle("标贴-描述信息"));
			//标贴-列表
			unitValue = new UnitValue[]{
	                    UnitValue.createPercentValue((float) 5),
	                    UnitValue.createPercentValue((float) 20),
	                    UnitValue.createPercentValue((float) 40),
	                    UnitValue.createPercentValue((float) 20),
	                    UnitValue.createPercentValue((float) 15)};
		    table = pdf.addTable(unitValue);
		    
		    getLabel(productDetailId);
			mapList = (ArrayList<HashMap<String, String>>) model.asMap().get("labelList");
			String[] arrTitleL =
				{ "No","名称","材质及要求","尺寸","备注"}; 
			String[] arrContentL =
				{"rownum","componentName","materialQuality","size","remark"};
			table = createTextTable(arrTitleL,arrContentL,mapList,pdf,table);	
			pdf.doc.add(table); //标贴
			
			//标贴***************************************************************
			//标贴图片-标题
			pdf.doc.add(pdf.addTitle(""));
			pdf.doc.add(pdf.addTitle("标贴-图片"));
			//标贴图片-列表
			getPhoto(YSId,productId,"label","labelFileList","labelFileCount");
			list = (ArrayList<String>) modelMap.get("labelFileList");
			table = createLabelPhotoTable(list,pdf);
			pdf.doc.add(table);
			
			//包装描述***************************************************************
			//包装描述-标题
			pdf.doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));//换页
			pdf.doc.add(pdf.addTitle(""));
			pdf.doc.add(pdf.addTitle("包装描述"));
			//包装描述-列表
			unitValue = new UnitValue[]{
                    UnitValue.createPercentValue((float) 5),
                    UnitValue.createPercentValue((float) 20),
                    UnitValue.createPercentValue((float) 30),
                    UnitValue.createPercentValue((float) 10),
                    UnitValue.createPercentValue((float) 15),
                    UnitValue.createPercentValue((float) 20)};
		    table = pdf.addTable(unitValue);
		    
		    getPackage(productDetailId);
			mapList = (ArrayList<HashMap<String, String>>) model.asMap().get("packageList");
			String[] titleP =
				{ "No","名称","材质","装箱数量","尺寸","备注"}; 
			String[] contentP =
				{"rownum","componentName","materialQuality","packingQty","size","remark"};
			table = createTextTable(titleP,contentP,mapList,pdf,table);	
			pdf.doc.add(table); 
			
			//包装描述-图片
			pdf.doc.add(pdf.addTitle(""));
			pdf.doc.add(pdf.addTitle("包装描述-图片"));
			//包装描述-图片列表
			getPhoto(YSId,productId,"package","packageFileList","packageFileCount");
			list = (ArrayList<String>) modelMap.get("packageFileList");			
			table = createPhotoTable(list,pdf);
			pdf.doc.add(table); 

        	pdf.doc.close();  
        	
        	//***********************PDF下载************************//
        	pdf.downloadFile();
        	
        } catch (Exception e) {  
            e.printStackTrace();  
        }  finally {
        	if (pdf.doc !=null) {
        		pdf.doc.flush();
        		pdf.doc.close();  
        		pdf.pdf.close();
        	}
		}
	}
	
	public Table createHeadTable(
			HashMap<String,String> product,
			PdfUnit pdf) throws Exception{
		

		int tCol = 6; 
		int tRow = 3;
        UnitValue[] unitValue = new UnitValue[]{
                UnitValue.createPercentValue((float) 12),
                UnitValue.createPercentValue((float) 15),
                UnitValue.createPercentValue((float) 12),
                UnitValue.createPercentValue((float) 19),
                UnitValue.createPercentValue((float) 12),
                UnitValue.createPercentValue((float) 30)};
        Table table = pdf.addTable(unitValue);
        
        String[][] arrTitle = { 
        		{"耀升编号","","产品编号","", "产品名称",""},
        		{"交货时间","", "交货数量","", "封样数量",""}, 
        		{"包装描述","", "资料完成状况","", "版本类别",""}}; 
        String[][] arrContent = { 
        		{"","YSId","","productId","", "materialName"},
        		{"","deliveryDate","", "quantity","", "sealedSample"}, 
        		{"","packageDescription", "","status", "","productClassify"}}; 
		Cell cell = new Cell(); 
        String strTableTitle="";
        Paragraph tableTitle;
        
        for (int i = 0; i < tRow; i++) {  
            for (int j = 0; j < tCol; j++) {
            	
            	if(j%2==0){
	            	strTableTitle = arrTitle[i][j];
		    	    tableTitle = pdf.tableTitleRight(strTableTitle);   
	    	        cell = pdf.addCellEven(tableTitle);
                }else{
	            	strTableTitle = arrContent[i][j];
	            	if(strTableTitle.length()<=0){
	            		tableTitle = pdf.addContent("");
	            	}else{

		    	        tableTitle = pdf.addContent(product.get(strTableTitle));
	            	}
	    	        cell = pdf.addCell(tableTitle);	                	
                }
    	        table.addCell(cell);
            }
            
        }	 
		return table;		
	}
	
	public Table createLabelPhotoTable(
			ArrayList<String> list,
			PdfUnit pdf) throws MalformedURLException{

		UnitValue[] unitValue = new UnitValue[]{
                UnitValue.createPercentValue((float) 100)};
		Table table = pdf.addTable(unitValue);
		
   		for(int i=0;i<list.size();i++){
			String path = list.get(i);
			//显示用目录
			String viewPath = session.getServletContext().getRealPath(path);
			Cell cell = pdf.addCell(pdf.addLargeImage(viewPath));
			table.addCell(cell);			
		}				
		return table;		
	}
	
	public Table createPhotoTable(
			ArrayList<String> list,
			PdfUnit pdf) throws Exception{

		UnitValue[] unitValue = new UnitValue[]{
                UnitValue.createPercentValue((float) 50),
                UnitValue.createPercentValue((float) 50)};
		Table table = pdf.addTable(unitValue);
		
   		for(int i=0;i<list.size();i++){
			String path = list.get(i);
			//显示用目录
			String viewPath = session.getServletContext().getRealPath(path);
			Cell cell = pdf.addCell(pdf.addImage(viewPath));
			table.addCell(cell);
			
			i++;
			if (i == list.size()) {//一行两列,奇数张图片时
				cell = pdf.addCell("");
			}else{
				path = list.get(i);
				//显示用目录
				viewPath = session.getServletContext().getRealPath(path);
				cell = pdf.addCell(pdf.addImage(viewPath));
			}
			table.addCell(cell);			
		}		
		return table;		
	}
	
	public Table createTextTable(
			String[] title,
			String[] content,
			ArrayList<HashMap<String, String>> mapList,
			PdfUnit pdf,
			Table table){
		
		Cell cell;
		//机器配置-表头
		for(int j=0;j<title.length;j++){			
			String t = title[j];
			Paragraph tableTitle = pdf.tableTitle(t);   
	        cell = pdf.addCellEven(tableTitle);				
			table.addCell(cell);
		}
		
		//机器配置-数据
		for(int i=0;i<mapList.size();i++){
			HashMap<String, String> machine = mapList.get(i);
			for(int j=0;j<title.length;j++){					
				
				String cent = content[j];
				String data = machine.get(cent) ==null?"":machine.get(cent);
				Paragraph tableTitle = pdf.addContent(data);

				if(i%2==0){
	    	        cell = pdf.addCell(tableTitle);
				}else{
	    	        cell = pdf.addCellEven(tableTitle);	
				}					
				table.addCell(cell);
			}			
		}		
		return table;		
	}
	
	public void downloadFile() throws Exception {

		String productId = request.getParameter("productId");
		String YSId = request.getParameter("YSId");

		JSONObject jsonObj = new JSONObject();
		
		//得到要下载的文件名
		String fileName = URLDecoder.decode(request.getParameter("fileName"),"UTF-8"); //
		//fileName = new String(fileName.getBytes("iso8859-1"),"UTF-8");
		//上传的文件都是保存在/WEB-INF/upload目录下的子目录当中
		String fileDownloadPath = session.getServletContext().
				getRealPath(BusinessConstants.PATH_PRODUCTDESIGNVIEW)+
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
			return;
		}
		//处理文件名
		String realname = fileName.substring(fileName.indexOf("_")+1);
		new PdfUnit(session,response).downloadFile(fileDownloadPath + "\\" + fileName,realname);
		
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

	public boolean deletePhoto (String path,String fileName)throws Exception {
    	
		boolean rtnFlag = false;
		String tempPath = "";
		String viewPath = "";
		
		if(path == null){//这里只处理文字印刷的文件
			B_ProductDesignData reqDt = reqModel.getProductDesign();
			String YSId = reqDt.getYsid();
			String productId = reqDt.getProductid();
			fileName = URLDecoder.decode(fileName,"UTF-8"); //格式转换

	    	//临时文件
			tempPath = session.getServletContext().
					getRealPath(BusinessConstants.PATH_PRODUCTDESIGNTEMP)+ "/" +fileName;	
	    	//显示用目录
			viewPath = session.getServletContext().
					getRealPath(BusinessConstants.PATH_PRODUCTDESIGNVIEW)+
					"/"+productId+"/"+YSId+"/"+"textPrint/"+ fileName;	
		}else{
	    	//显示用目录
			viewPath = session.getServletContext().getRealPath(path);
		}
		
    	//存储文件
    	String realPath = viewPath.replaceFirst("img", "file");
    			
    	//String file = pathAndName.replace('/', '\\') ;
    	//String file = ctxPath + path.replace('/', '\\') ;
    	//String fileSmall = ctxPath + BusinessConstants.BUSINESSPHOTOPATH.replace('/', '\\') + key + "\\" +  BusinessConstants.BUSINESSSMALLPHOTOPATH.replace('/', '\\') + fileName.replace('/', '\\');

    	File f1 = new File(tempPath); //临时目录,文件用
    	if(f1.exists()) {
    		f1.delete(); 
    		rtnFlag = true;
    	}
    	
    	File f2 = new File(viewPath); //显示目录,文件和图片通用
    	if(f2.exists()) {
    		f2.delete(); 
    		rtnFlag = true;
    	}
    	
    	File f = new File(realPath); //存储目录,文件和图片通用
    	if(f.exists()) {
    		f.delete(); 
    		rtnFlag = true;
    	}
    	
    	return rtnFlag;
    	
    }
	
	public boolean fileCopyToStorageFolder(
			String fileName,String productId,String YSId) {

		boolean rtnbl = false;
    	//临时文件
		String tempPath = session.getServletContext().
				getRealPath(BusinessConstants.PATH_PRODUCTDESIGNTEMP)+ "/" +fileName;
    	//显示用目录
		String viewPath = session.getServletContext().
				getRealPath(BusinessConstants.PATH_PRODUCTDESIGNVIEW)+
				"/"+productId+"/"+YSId+"/"+"textPrint";
		//存储目录
		String backPath = session.getServletContext().
				getRealPath(BusinessConstants.PATH_PRODUCTDESIGNFILE)+
				"/"+productId+"/"+YSId+"/"+"textPrint";	
		
		try {
			File viewT = new File(tempPath);
			if(!viewT.exists()){
				return rtnbl;//临时目录没有的话,表示没有上传新的文件
	    	} 
			File viewF = new File(viewPath);
			if(!viewF.exists()){
				viewF.mkdirs();  
	    	} 
			File backF = new File(backPath);
			if(!backF.exists()){  
				backF.mkdirs();  
		    } 
			InputStream  temp = new FileInputStream(new File(tempPath));
			OutputStream view = new FileOutputStream(viewPath+"\\"+fileName);
			OutputStream back = new FileOutputStream(backPath+"\\"+fileName);
			byte[] buf = new byte[BusinessConstants.MAX_BUFFER_SIZE];
			int len;
			while ((len = temp.read(buf)) > 0) {
				view.write(buf, 0, len);
				back.write(buf, 0, len);
				
			}
			temp.close();
			view.flush();view.close();
			back.flush();back.close();				
			
		} catch (IOException e) {
			rtnbl = true;
			e.printStackTrace();
		}finally{
			File sourceFile = new File(tempPath); //删除临时文件			
			sourceFile.delete();
		}
		
		return rtnbl;
			
	}
    
	public void fileCopyToTempFolder(String folder,
			String fileName,String productId,String YSId) {
				
    	//临时文件
		String tempPath = session.getServletContext().
				getRealPath(BusinessConstants.PATH_PRODUCTDESIGNVIEW)+
				"/"+productId+"/"+YSId+"/"+"textPrint/"+ fileName;
		//存储目录
		String backPath = session.getServletContext().
				getRealPath(BusinessConstants.PATH_PRODUCTDESIGNFILE)+
				"/"+productId+"/"+YSId+"/"+"textPrint/"+ fileName;	
		
		try {
		
			InputStream in = new FileInputStream(backPath);
			OutputStream out = new FileOutputStream(tempPath);
			byte[] buf = new byte[BusinessConstants.MAX_BUFFER_SIZE];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
			
			File sourceFile = new File(tempPath); //删除临时文件			
			sourceFile.delete();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
					
	}
	
	 private void createPdf(){  
	        // 生成的新文件路径  
	        String fileName = "itext-pdf-{0}.pdf";  
	        String newPDFPath = "D:/Temp/pdf/itext-pdf-1.pdf";  
	      
	        try {     
	  
	  
	            //处理中文问题    
	            PdfFont font = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H", false);    
	              
	            PdfWriter writer = new PdfWriter(new FileOutputStream(newPDFPath));  
	            //Initialize PDF document    
	            PdfDocument pdf = new PdfDocument(writer);      
	            Document document = new Document(pdf);  
	              
	            Paragraph p =new Paragraph("你好,世界！hello word!");  
	            p.setFont(font);  
	            p.setFontSize(12);  
	            p.setBorder(new SolidBorder(Color.BLACK,0.5f));  
	            document.add(p);              
	              
	            document.close();  
	            writer.close();  
	            pdf.close();  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	    }  
	    /** 
	     * 使用pdf 模板生成 pdf 文件 
	     *   */  
	    private void fillTemplate() {// 利用模板生成pdf  
	        // 模板路径  
	        String templatePath = "D:/Temp/pdf/pdf-template-form.pdf";  
	        // 生成的新文件路径  
	       // String fileName = StringExtend.format("itext-template-{0}.pdf", DateExtend.getDate("yyyyMMddHHmmss"));  
	        String newPDFPath = "D:/Temp/pdf/";  
	  
	  
	        try {  
	            //Initialize PDF document  
	            PdfDocument pdf = new PdfDocument(new PdfReader(templatePath), new PdfWriter(newPDFPath));  
	            PdfAcroForm form = PdfAcroForm.getAcroForm(pdf, true);  
	            Map<String, PdfFormField> fields = form.getFormFields();  
	              
	            //处理中文问题    
	            PdfFont font = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H", false);    
	            String[] str = {  
	                    "01.hello word!",   
	                    "02.你好，世界！",  
	                    "03.hello word!",   
	                    "04.你好，世界！",  
	                    "05.hello word!",   
	                    "06.你好，世界！",  
	                    "07.hello word!",   
	                    "08.你好，世界！",  
	                    "09.hello word!",   
	                    "10.你好，世界！",  
	                    "11.hello word!",   
	                    "12.你好，世界！", };  
	            int i = 0;  
	            java.util.Iterator<String> it = fields.keySet().iterator();  
	            while (it.hasNext()) {  
	                //获取文本域名称  
	                String name = it.next().toString();  
	                //填充文本域  
	                fields.get(name).setValue(str[i++]).setFont(font).setFontSize(12);  
	                System.out.println(name);  
	            }     
	            form.flattenFields();//设置表单域不可编辑              
	            pdf.close();  
	  
	  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        } catch (Exception e){  
	            e.printStackTrace();  
	        }  
	  
	  
	}
	    
	public HashMap<String, Object> getSupplierFromBom() throws Exception {

		String key1 = request.getParameter("key1").toUpperCase();
		String key2 = request.getParameter("key2");
		
		userDefinedSearchCase.clear();
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("productId", key2);
		
		dataModel = new BaseModel();		
		dataModel.setQueryFileName("/business/order/bomquerydefine");
		dataModel.setQueryName("getBomDetailListByBomId");
		
		baseQuery = new BaseQuery(request, dataModel);	
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);		
		baseQuery.getYsFullData();
	
		modelMap.put("data", dataModel.getYsViewData());		
		modelMap.put("retValue", "success");			
		
		return modelMap;
	}
}	
