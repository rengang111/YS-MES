package com.ys.business.service.order;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.system.service.common.BaseService;
import com.ys.util.CalendarUtil;
import com.ys.util.DicUtil;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basedao.BaseTransaction;
import com.ys.util.basequery.BaseQuery;
import com.ys.util.basequery.common.BaseModel;
import com.ys.util.basequery.common.Constants;
import com.ys.business.action.model.order.MaterialModel;
import com.ys.business.db.dao.B_OrderDetailDao;
import com.ys.business.db.dao.B_PriceReferenceDao;
import com.ys.business.db.dao.B_PriceSupplierDao;
import com.ys.business.db.dao.B_PriceSupplierHistoryDao;
import com.ys.business.db.dao.B_PurchaseOrderDao;
import com.ys.business.db.dao.B_PurchaseOrderDetailDao;
import com.ys.business.db.dao.B_PurchasePlanDetailDao;
import com.ys.business.db.dao.S_systemConfigDao;
import com.ys.business.db.data.B_MaterialData;
import com.ys.business.db.data.B_OrderDetailData;
import com.ys.business.db.data.B_PriceReferenceData;
import com.ys.business.db.data.B_PriceSupplierData;
import com.ys.business.db.data.B_PriceSupplierHistoryData;
import com.ys.business.db.data.B_PurchaseOrderData;
import com.ys.business.db.data.B_PurchaseOrderDetailData;
import com.ys.business.db.data.B_PurchasePlanDetailData;
import com.ys.business.db.data.CommFieldsData;
import com.ys.business.db.data.S_systemConfigData;

@Service
public class CommonService extends BaseService {

	DicUtil util = new DicUtil();

	BaseTransaction ts;

	String guid ="";
	private CommFieldsData commData;
	
	public HttpServletRequest request;
	
	private MaterialModel reqModel;
	public UserInfo userInfo;
	private BaseModel dataModel;
	private Model model;
	private HashMap<String, String> userDefinedSearchCase;
	private BaseQuery baseQuery;
	ArrayList<HashMap<String, String>> modelMap = null;	
	public HttpSession session;
	
	public CommonService(){
		this.modelMap = new ArrayList<HashMap<String, String>>();
		this.dataModel = new BaseModel();
		this.userDefinedSearchCase = new HashMap<String, String>();
		dataModel.setQueryFileName("/business/material/materialquerydefine");
		
	}


	
	public HashMap<String, Object> getSupplierList(
			HttpServletRequest request, 
			String data) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();

		String key = request.getParameter("key").toUpperCase();

		dataModel.setQueryFileName("/business/material/materialquerydefine");
		dataModel.setQueryName("getSupplierList");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("keywords1", key);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0,0);	 
		
		modelMap.put("data", dataModel.getYsViewData());		
		
		return modelMap;
	}
		
	public HashMap<String, Object> getMaterialMAXId(
			HttpServletRequest request, 
			String data) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		
		String key = request.getParameter("categoryId");

		dataModel.setQueryFileName("/business/material/materialquerydefine");
		dataModel.setQueryName("getMaxMaterialId");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("keywords1", key);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, 0);	 

		modelMap.put("retValue", "success");
		
		int code =Integer.parseInt(dataModel.getYsViewData().get(0).get("serialNumber"));
		
		int categoryId = code + 1;
		
		String codeFormat = String.format("%03d", categoryId); 
		
		modelMap.put("code",categoryId);	
		modelMap.put("codeFormat",codeFormat);			
		
		return modelMap;
	}


	
	/*
	 * 1.新增一条物料单价数据,包括供应商信息处理(一条数据)
	 */
	public Model insertPrice(
			MaterialModel reqFormBean,
			Model rtnModel,
			HttpServletRequest request,
			UserInfo userInfo) throws Exception  {
		
		ts = new BaseTransaction();
		
		try {
			
			ts.begin();
			
			B_PriceSupplierDao priceDao = new B_PriceSupplierDao();
			B_PriceSupplierHistoryDao historyDao = new B_PriceSupplierHistoryDao();
			B_PriceSupplierHistoryData historyData = new B_PriceSupplierHistoryData();
			B_PriceSupplierData reqData = new B_PriceSupplierData();
			B_PriceSupplierData DBData = new B_PriceSupplierData();
			
			reqData = (B_PriceSupplierData)reqFormBean.getPrice();
			
			String materialId = reqData.getMaterialid();
			String supplierId = reqData.getSupplierid();
			String guid = "";
			
			//判断该供应商是否已有报价
			DBData =prePriceCheck(materialId,supplierId);
			
			boolean blPrice = DBData ==null?false:true;
			
			if(blPrice){

				//更新单价表(同时更新createTime)
				commData = commFiledEdit(Constants.ACCESSTYPE_INS,"MaterialUpdate",userInfo);

				copyProperties(DBData,commData);
				DBData.setPrice(reqData.getPrice());
				DBData.setCurrency(reqData.getCurrency());
				DBData.setPriceunit(reqData.getPriceunit());
				DBData.setPricedate(reqData.getPricedate());
				DBData.setPricesource(BusinessConstants.PRICESOURCE_OTHER);
				DBData.setUsedflag(BusinessConstants.MATERIAL_USERD_Y);
				
				priceDao.Store(DBData);	
				
				updatePriceInfo(DBData.getMaterialid());
				
				//插入历史表
				copyProperties(historyData,DBData);
				commData = commFiledEdit(Constants.ACCESSTYPE_INS,"MaterialUpdate",userInfo);

				copyProperties(historyData,commData);
				
				guid = BaseDAO.getGuId();
				historyData.setRecordid(guid);
				
				historyDao.Create(historyData);
				
			}else{

				//插入单价表				
				//新增数据的价格来源设为:其他
				commData = commFiledEdit(Constants.ACCESSTYPE_INS,"MaterialUpdate",userInfo);

				copyProperties(reqData,commData);
	
				guid = BaseDAO.getGuId();
				reqData.setRecordid(guid);
				reqData.setPricesource(BusinessConstants.PRICESOURCE_OTHER);	
				reqData.setUsedflag(BusinessConstants.MATERIAL_USERD_Y);
				
				priceDao.Create(reqData);	

				updatePriceInfo(reqData.getMaterialid());//

				//插入历史表
				copyProperties(historyData,reqData);
				commData = commFiledEdit(Constants.ACCESSTYPE_INS,"MaterialInsert",userInfo);

				copyProperties(historyData,commData);
				
				guid = BaseDAO.getGuId();
				historyData.setRecordid(guid);
				
				historyDao.Create(historyData);	
			}

			
			ts.commit();
			
		}
		catch(Exception e) {
			ts.rollback();
			System.out.println(e.getMessage());
		}
		
		rtnModel.addAttribute("material",reqFormBean);
		
		return rtnModel;
	}	
	

	
	public void updatePriceInfo(String materialId) throws Exception{
		
		B_PriceReferenceDao dao = new B_PriceReferenceDao();
		B_PriceReferenceData dt = new B_PriceReferenceData();


		//删除历史数据
		String astr_Where = "materialId = '"+materialId+"'";
		try{
			dao.RemoveByWhere(astr_Where);
		}catch (Exception e){
			//continue
		}
		
		//设置物料编码
		dt.setMaterialid(materialId);
		
		//编辑最新价格
		dt = getLastPriceInfo(dt,materialId,request);
		//dt.setLastprice(last.getPrice());
		//dt.setLastsupplierid(last.getSupplierid());
		//dt.setLastdate(last.getPricedate());
		
		//编辑最低价格
		dt = getMixPriceInfo(dt,materialId);
		
		
		//更新物料单价参考信息表
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"PriceReferenceInsert",userInfo);
		copyProperties(dt,commData);
						
		String guid = BaseDAO.getGuId();
		dt.setRecordid(guid);
		
		dao.Create(dt);
		
	}

	/**
	 * 更新供应商单价
	 */
	public void updatePriceSupplier(
			String materialId,
			String supplierId,
			String price ) throws Exception {
		
		//List<B_PriceSupplierData> priceList = null;
		B_PriceSupplierData pricedt = null;
		B_PriceSupplierDao dao = new B_PriceSupplierDao();
		B_PriceSupplierHistoryData historyDt = new B_PriceSupplierHistoryData();
		B_PriceSupplierHistoryDao historyDao = new B_PriceSupplierHistoryDao();

		String where ="materialId= '" + materialId + "'" + 
				" AND supplierId = '" + supplierId + "'" ;

		//priceList = (List<B_PriceSupplierData>)dao.Find(where);
		try {
			dao.RemoveByWhere(where);
		} catch (Exception e) {
			//
		}	
		
		pricedt = new B_PriceSupplierData();
		pricedt.setMaterialid(materialId);
		pricedt.setSupplierid(supplierId);
		pricedt.setPrice(price);
		pricedt.setPricedate(CalendarUtil.getToDay());
		
		//处理共通信息					
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,"BaseBomInsert",userInfo);
		copyProperties(pricedt,commData);
		guid = BaseDAO.getGuId();
		pricedt.setRecordid(guid);
		
		dao.Create(pricedt);		

		//更新最新,最低单价
		updatePriceInfo(materialId);
		
		//插入历史表
		//处理共通信息	
		copyProperties(historyDt,pricedt);
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,"BaseBomInsert",userInfo);
		copyProperties(historyDt,commData);		
		guid = BaseDAO.getGuId();
		historyDt.setRecordid(guid);
		
		historyDao.Create(historyDt);
	
	}
	
	/*
	 * 1.显示当前选中物料的基本信息
	 * 2.显示相关的所有子编码信息(N条数据) 
	 */
	private B_PriceReferenceData getMixPriceInfo(
			B_PriceReferenceData dt,
			String materialid) throws Exception{
		
		dataModel.setQueryName("getMinPriceByMaterialId");
		
		baseQuery = new BaseQuery(request, dataModel);

		userDefinedSearchCase.put("materialid", materialid);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		modelMap = baseQuery.getYsFullData();
		
		if(dataModel.getRecordCount() > 0){
			dt.setMinprice(dataModel.getYsViewData().get(0).get("price"));
			dt.setMinsupplierid(dataModel.getYsViewData().get(0).get("supplierId"));
			dt.setMindate(dataModel.getYsViewData().get(0).get("priceDate"));
		}
		return dt;
	}
	

	private B_PriceReferenceData getLastPriceInfo(
			B_PriceReferenceData dt,
			String materialid,
			HttpServletRequest request) throws Exception{

		dataModel.setQueryFileName("/business/material/materialquerydefine");
		dataModel.setQueryName("getLastPriceByMaterialId");
		
		baseQuery = new BaseQuery(request, dataModel);

		userDefinedSearchCase.put("materialid", materialid);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		modelMap = baseQuery.getYsFullData();
		
		if(dataModel.getRecordCount() > 0){
			dt.setLastprice(dataModel.getYsViewData().get(0).get("price"));
			dt.setLastsupplierid(dataModel.getYsViewData().get(0).get("supplierId"));
			dt.setLastdate(dataModel.getYsViewData().get(0).get("priceDate"));
		}
		return dt;
	}
	
	public Model SelectSupplier(String materialid) {

		try{

			dataModel.setQueryFileName("/business/material/materialquerydefine");
			dataModel.setQueryName("getMaterialByMaterialId");
			
			baseQuery = new BaseQuery(request, dataModel);

			userDefinedSearchCase.put("materialid", materialid);
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
			modelMap = baseQuery.getYsQueryData(0, 0);

			model.addAttribute("product",dataModel.getYsViewData().get(0));

			reqModel.setCurrencyList(util.getListOption(DicUtil.CURRENCY, ""));
			
		}catch(Exception e){
			
		}
		
		return model;
		
	}

	public MaterialModel doDeletePrice(
			String delData, UserInfo userInfo) throws Exception{
		
		MaterialModel model = new MaterialModel();
		B_PriceSupplierData data = new B_PriceSupplierData();	
		B_PriceSupplierDao dao = new B_PriceSupplierDao();	

		String materialid = request.getParameter("key");	
		
		try {	
												
			String recordid = request.getParameter("recordId");									
			data.setRecordid(recordid);

			dao.Remove(data);
			

			//更新该物料的最新价格,最低价格
			updatePriceInfo(materialid);
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return model;
	}
	

	public MaterialModel doDeletePriceHistory(
			String delData, UserInfo userInfo) throws Exception{
		
		MaterialModel model = new MaterialModel();
		B_PriceSupplierHistoryData data = new B_PriceSupplierHistoryData();	
		B_PriceSupplierHistoryDao dao = new B_PriceSupplierHistoryDao();	
													
		try {	
												
			String recordid = request.getParameter("recordId");									
			data.setRecordid(recordid);

			dao.Remove(data);
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return model;
	}
	
	
	@SuppressWarnings("unchecked")
	private B_PriceSupplierData prePriceCheck(
			String materialId,
			String supplierId) throws Exception {

		B_PriceSupplierDao dao = new B_PriceSupplierDao();
		List<B_PriceSupplierData> priceList = null;
		B_PriceSupplierData pricedt = null;

		String where = " materialId = '" + materialId +
				"' AND supplierId = '" + supplierId +
				"' AND deleteFlag = '0' ";		

		priceList = (List<B_PriceSupplierData>)dao.Find(where);
		
		if(priceList != null && priceList.size() > 0)
			pricedt = priceList.get(0);	
			
		return pricedt;
	}
	
	/**
	 * 更新订单状态
	 */
	public void updateOrderStatusByYSId(String YSId,String status) throws Exception{
		
		//确认Order表是否存在
		
		B_OrderDetailDao dao = new B_OrderDetailDao();
		B_OrderDetailData dbData = getOrderByYSId(dao,YSId);
		
		if(dbData != null){				
			//update处理
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"OrderStatusUpdate",userInfo);			
			copyProperties(dbData,commData);
			dbData.setStatus(status);

			dao.Store(dbData);				
		}	

	}
	
	@SuppressWarnings("unchecked")
	public B_OrderDetailData getOrderByYSId(
			B_OrderDetailDao dao,
			String YSId ) throws Exception {
		
		List<B_OrderDetailData> dbList = null;
		
		String where = " YSId = '"+YSId +"'" + " AND deleteFlag = '0' ";
						
		dbList = (List<B_OrderDetailData>)dao.Find(where);
		
		if(dbList != null & dbList.size() > 0)
			return dbList.get(0);		
		
		return null;
	}

	/**
	 * 更新合同状态
	 */
	@SuppressWarnings("unchecked")
	public void updateContractStatus(String contractId,String status) throws Exception{
		
		//更新合同状态
		String where = "contractId ='"+contractId + "' AND deleteFlag='0' ";
		B_PurchaseOrderData d = new B_PurchaseOrderData();	
		List<B_PurchaseOrderData> l = 
				(List<B_PurchaseOrderData>)new B_PurchaseOrderDao().Find(where);
		
		if(l ==null || l.size() == 0){
			return ;
		}		
		d = l.get(0);
		d.setStatus(status);
		
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"purchaseOrderUpdate",userInfo);
		copyProperties(d,commData);	
		
		new B_PurchaseOrderDao().Store(d);

	}
	/**
	 * 更新合同明细
	 */
	public void updateContractDetailStatus(B_PurchaseOrderDetailData d) throws Exception{
		
		//更新合同状态
		//B_PurchaseOrderDetailData d = new B_PurchaseOrderData();	
		//List<B_PurchaseOrderData> l = 
		//		(List<B_PurchaseOrderData>)new B_PurchaseOrderDao().Find(where);
		
		//if(l ==null || l.size() == 0){
		//	return ;
		//}		
		//d = l.get(0);
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"purchaseOrderDetailUpdate",userInfo);
		copyProperties(d,commData);	
		
		new B_PurchaseOrderDetailDao().Store(d);

	}
	//更新虚拟库存
	/*
	public void updateInventory(
			B_InventoryData planData) throws Exception{
		
		B_InventoryDao dao = new B_InventoryDao();
		B_InventoryData data = null;
		
		//确认物料的库存是否存在
		data = checkInventoryExsit(planData.getMaterialid());
		
		if(data == null){
			//insert
			data = new B_InventoryData();

			copyProperties(data,planData);
			commData = commFiledEdit(Constants.ACCESSTYPE_INS,
					"AvailabelInsert",userInfo);
			copyProperties(data,commData);
			
			guid = BaseDAO.getGuId();
			data.setRecordid(guid);
			
			dao.Create(data);	
			
		}else{
			//update
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"AvailabelUpdate",userInfo);
			copyProperties(data,commData);
			
			//计算虚拟库存
			String oldAva = data.getAvailabeltopromise();
			String newAva = planData.getAvailabeltopromise();		
			float cunt =Float.parseFloat(oldAva) + Float.parseFloat(newAva);
			
			data.setAvailabeltopromise(String.valueOf(cunt));
			
			dao.Store(data);	
		}
		
	}	


	@SuppressWarnings("unchecked")
	private B_InventoryData checkInventoryExsit(
			String materialId) throws Exception {

		B_InventoryDao dao = new B_InventoryDao();
		B_InventoryData data = null;
		List<B_InventoryData> list = null;

		String where = " materialId = '" + materialId +
				"' AND deleteFlag = '0' ";		

		list = (List<B_InventoryData>)dao.Find(where);
		
		if(list != null && list.size() > 0)
			data = list.get(0);	
			
		return data;
	}
	*/

	/**
	 * 字符串转float
	 * @param s
	 * @return
	 */
	public static float stringToFloat(String s){

		float rtn = 0;
		
		try{
			if(s == null || s.trim().equals(""))
				return rtn;
			
			rtn = Float.parseFloat(s.replace(",",""));
			
			if(Float.isInfinite(rtn) || Float.isNaN(rtn))
				rtn = 0;
			
		}catch(NumberFormatException e){
			e.printStackTrace();
		}
		
		return rtn;		
	}
	
	/**
	 * 字符串转int
	 * @param s
	 * @return
	 */
	public static int stringToInteger(String s){

		int rtn = 0;
		
		try{
			if(s == null || s.trim().equals(""))
				return rtn;
			
			rtn = Integer.parseInt(s.replace(",",""));
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		return rtn;		
	}
	
	
	/**
	 * 换行符转换
	 * @param mystr
	 * @return
	 */
	public String replaceTextArea(String str) {
		if (str == null || str == "") {
			return ("&nbsp;");
		} else {
			str = str.replace("\n\r", "<br>");
			str = str.replace("\r\n", "<br>");
			str = str.replace("\r", "<br>");
			str = str.replace("\n", "");
			str = str.replace("\t", "  ");
			//str = str.replace(" ", "&nbsp;");
			//mystr = mystr.replace("\"", "\\"+"\"");
			return (str);
		}
	}
	
	public B_MaterialData editCustomerId(
			B_MaterialData dbData, String materialId){
		
		String customerId = "";
		String[] splits = materialId.split("[.]");
		if(("I").equals(splits[0])){
			if(splits.length ==4){
				dbData.setProductmodel( splits[1]);
				customerId = splits[2].substring(0, splits[2].length()-3);
			}else if(splits.length ==5){
				dbData.setProductmodel( splits[2]);
				customerId = splits[3].substring(0, splits[3].length()-3);
				
			}
			//编辑客户编号
			dbData.setCustomerid(customerId);	
		}
		
		return dbData;
	}
	
	/** 
	 *  
	 * 文件夹拷贝(文件内含有文件和文件夹) 
	 * @param src 
	 */  
    public static void folderCopy(String src, String des) {  
        File file1=new File(src);  
        File[] fs=file1.listFiles();
        if(fs == null)
        	return;//复制源文件夹里面没有文件的话,直接返回;
        File file2=new File(des);  
        String timeStemp = CalendarUtil.timeStempDate();
        if(!file2.exists()){  
            file2.mkdirs(); 
        }else{
        	file2.renameTo( new File(des+"_"+timeStemp));//已经存在的话,备份后再新建
            file2.mkdirs(); 
        }
        for (File f : fs) {
            if(f.isFile()){  
                fileCopy(f.getPath(),des+"\\"+f.getName()); //调用文件拷贝的方法  
            }else if(f.isDirectory()){  
            	folderCopy(f.getPath(),des+"\\"+f.getName());  
            }  
        }  
    }   
    
    /** 
     * 文件拷贝
     */  
    public static void fileCopy(String src, String target) {  
      
    	InputStream in=null;
    	OutputStream out=null;
        try {
        	 File srcFile = new File(src);
             File targetFile = new File(target);
             in = new FileInputStream(srcFile);
             out = new FileOutputStream(targetFile);
             byte[] bytes = new byte[1024];    
             int len = -1;    
             while((len=in.read(bytes))!=-1)
             {
                 out.write(bytes, 0, len);
             } 
        } catch (FileNotFoundException e) {
            e.printStackTrace(); 
        } catch (IOException e) {
            e.printStackTrace();  
        }finally{
            try {
                if(in!=null)  in.close();  
                if(out!=null)  out.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
                  
        }  
          
          
    }  
	
	public String getContractType(String dicType){

		String type = "";
		switch(dicType){
			case Constants.PURCHASE_TYPE_D:
				type=Constants.CONTRACT_TYPE_D;
				break;
			case Constants.PURCHASE_TYPE_T:
				type=Constants.CONTRACT_TYPE_T;
				break;
			case Constants.PURCHASE_TYPE_Y:
				type=Constants.CONTRACT_TYPE_Y;
				break;	
			case Constants.PURCHASE_TYPE_O:
				type=Constants.CONTRACT_TYPE_O;
				break;	
			default:
				type=Constants.CONTRACT_TYPE_D;
				break;
		}
		
		return type;
	}
	
	/**
	 * 服务器端排序,获取网页传来的sortkey
	 * @param data
	 * @param query
	 * @return sql
	 * @throws Exception
	 */
	public String getSortKeyFormWeb(String data,BaseQuery query) throws Exception{
		String sql = query.getSql();
		if(sql == null || ("").equals(sql.trim()))
			return null;
		int index = sql.indexOf("ORDER BY");
		String iSortCol_0 = getJsonData(data, "iSortCol_0");
		String sSortDir_0 = getJsonData(data, "sSortDir_0");
		if(("").equals(iSortCol_0) || ("0").equals(iSortCol_0))
			return sql;
		String sortColName = getJsonData(data, "mDataProp_"+iSortCol_0);
		String newSql = sql.replace(sql.substring(index),"ORDER BY " + sortColName + " "+ sSortDir_0);
	
		return newSql;
	}
	
	/**
	 * 更新系统信息:汇率
	 */
	@SuppressWarnings("unchecked")
	public void updateExchangeRate(String currencyId,String exRate) {
 		
		try {

			S_systemConfigDao dao = new S_systemConfigDao();
			S_systemConfigData dbData = new S_systemConfigData();
			List<S_systemConfigData> dbList = null;
			String where = null;
			
			//更新汇率
			if(exRate != null && !("").equals(exRate)){
				where = "sysKey='"+currencyId+"'";
				dbList = (List<S_systemConfigData>)dao.Find(where);
				
				if(dbList!=null && dbList.size() >0){

					dbData = dbList.get(0);
					commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
							"SystemConfigUpdate",userInfo);	
					copyProperties(dbData,commData);
					dbData.setSysvalue(exRate);

					dao.Store(dbData);	
				}else{		
					commData = commFiledEdit(Constants.ACCESSTYPE_INS,
							"SystemConfigInsert",userInfo);	
					copyProperties(dbData,commData);	
					dbData.setSyskey(currencyId);
					dbData.setSysvalue(exRate);
					dao.Create(dbData);
				}
				dbList.clear();
			}
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}		
	}
	
	public boolean isNullOrEmpty(String value){
		boolean rtn = false;
		if(value == null || ("").equals(value.trim())){
			rtn = true;
		}
		return rtn;
	}

	public boolean notEmpty(String value){
		boolean rtn = true;
		if(value == null || 
			("").equals(value.trim())){
			rtn = false;
		}
		return rtn;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList removeDuplicate(ArrayList arlList)      
	{      
		HashSet h = new HashSet(arlList);      
		arlList.clear();      
		arlList.addAll(h);
		
		return arlList;
	} 
}
