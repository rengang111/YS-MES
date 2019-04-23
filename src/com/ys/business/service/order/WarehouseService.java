package com.ys.business.service.order;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.util.CalendarUtil;
import com.ys.util.DicUtil;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basedao.BaseTransaction;
import com.ys.util.basequery.BaseQuery;
import com.ys.util.basequery.common.BaseModel;
import com.ys.util.basequery.common.Constants;
import com.sun.istack.internal.NotNull;
import com.ys.business.action.model.common.FilePath;
import com.ys.business.action.model.common.FinanceMouthly;
import com.ys.business.action.model.order.OrderModel;
import com.ys.business.action.model.order.ProduceModel;
import com.ys.business.action.model.order.WarehouseModel;
import com.ys.business.db.dao.B_FollowDao;
import com.ys.business.db.dao.B_MaterialDao;
import com.ys.business.db.dao.B_OrderCancelDao;
import com.ys.business.db.dao.B_OrderDao;
import com.ys.business.db.dao.B_OrderDetailDao;
import com.ys.business.db.dao.B_OrderMergeDetailDao;
import com.ys.business.db.dao.B_OrderProduceHideDao;
import com.ys.business.db.dao.B_PurchasePlanDetailDao;
import com.ys.business.db.dao.B_RequisitionDetailDao;
import com.ys.business.db.dao.B_StockOutDao;
import com.ys.business.db.dao.B_StockOutDetailDao;
import com.ys.business.db.dao.S_WarehouseCodeDao;
import com.ys.business.db.data.B_FollowData;
import com.ys.business.db.data.B_MaterialData;
import com.ys.business.db.data.B_OrderCancelData;
import com.ys.business.db.data.B_OrderData;
import com.ys.business.db.data.B_OrderDetailData;
import com.ys.business.db.data.B_OrderMergeDetailData;
import com.ys.business.db.data.B_OrderProduceHideData;
import com.ys.business.db.data.B_PaymentData;
import com.ys.business.db.data.B_PurchasePlanDetailData;
import com.ys.business.db.data.B_RequisitionDetailData;
import com.ys.business.db.data.B_StockOutData;
import com.ys.business.db.data.B_StockOutDetailData;
import com.ys.business.db.data.B_SupplierData;
import com.ys.business.db.data.CommFieldsData;
import com.ys.business.db.data.S_WarehouseCodeData;
import com.ys.business.service.common.BusinessService;

@Service
public class WarehouseService extends CommonService  {

	DicUtil util = new DicUtil();

	BaseTransaction ts;
	String guid ="";
	private CommFieldsData commData;

	private WarehouseModel reqModel;

	private HttpServletRequest request;
	private UserInfo userInfo;
	private BaseModel dataModel;
	private Model model;
	private HashMap<String, String> userDefinedSearchCase;
	private BaseQuery baseQuery;
	ArrayList<HashMap<String, String>> modelMap = null;	
	HashMap<String, Object> hashMap = null;	
	HttpSession session;
	
	public WarehouseService(){
		
	}

	public WarehouseService(
			Model model,
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			WarehouseModel reqModel,
			UserInfo userInfo){
		
		this.model = model;
		this.reqModel = reqModel;
		this.request = request;
		this.userInfo = userInfo;
		this.session = session;
		this.dataModel = new BaseModel();
		this.userDefinedSearchCase = new HashMap<String, String>();
		this.hashMap = new HashMap<>();
		super.request = request;
		super.userInfo = userInfo;
		super.session = session;
		
	}
	
	public HashMap<String, Object> warehouseCodeSearch(String formId,String data) throws Exception {
		
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
		String[] keyArr = getSearchKey(formId,data,session);
		String key1 = keyArr[0];
		String key2 = keyArr[1];

		baseQuery = new BaseQuery(request, dataModel);
		dataModel.setQueryFileName("/business/material/inventoryquerydefine");
		dataModel.setQueryName("getWarehouseCodeList");
				
		baseQuery.getYsQueryData(iStart, iEnd);	 
		
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
	
	@SuppressWarnings("unchecked")
	public void getParentCodeDetail() throws Exception {

		String codeId = request.getParameter("codeId");
		String where = " codeId ='"+codeId+"' AND deleteFlag='0' ";
	
		List<S_WarehouseCodeData> dbData = 
			(List<S_WarehouseCodeData>)new S_WarehouseCodeDao().Find(where);
			
		if(dbData.size() >0){
			//model.addAttribute("parend",dbData.get(0));
			reqModel.setWarehouse(dbData.get(0));
		}
	}
	
	public String getMAXParentCode(String codeId) throws Exception{

		dataModel.setQueryFileName("/business/material/inventoryquerydefine");
		dataModel.setQueryName("getMAXParentCode");
		userDefinedSearchCase.put("parentId", codeId);
		baseQuery = new BaseQuery(request, dataModel);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, 0);
		
		//取得已有的最大流水号
		String code ="";
		if(dataModel.getRecordCount() > 0 ){
			code =dataModel.getYsViewData().get(0).get("sortNo");
		}
		
		return code;
	}
	
	public void insertWarehouseCode() throws Exception {
		
		String parentCodeId = request.getParameter("parentCodeId");
		S_WarehouseCodeData reqData = reqModel.getWarehouse();

		String parentLevel = reqData.getMultilevel();
		String parentSortNo = reqData.getSortno();
		String codeId = reqData.getCodeid();

		//层次编号
		int iLevel = 1;
		if(notEmpty(parentLevel)){
			iLevel = Integer.parseInt(parentLevel)+1;
		}
		
		//取得最新的子编码
		//设置编码Code
		String newCodeId = parentCodeId + codeId;
		if(isNullOrEmpty(parentCodeId)){
			parentCodeId = "0";//
			newCodeId = codeId;//新建一级编码时，没有父级编码
			
		}
		String subSortNo = getMAXParentCode(parentCodeId);
						
		//设置排序编号，有点复杂
		String newSortNo = "";
		if(isNullOrEmpty(subSortNo)){
			if(isNullOrEmpty(parentSortNo)){
				//第一条 一级编码
				newSortNo = "100";
			}else{
				//第一条 子编码
				newSortNo = parentSortNo + "-" + "100";
			}
		}else{
			if(isNullOrEmpty(parentSortNo)){
				//第N条 一级编码
				int oldSubNo = Integer.parseInt(subSortNo);
				int newSubNo = oldSubNo + 100;
				newSortNo = String.valueOf(newSubNo);
				if (newSortNo.length() > subSortNo.length()){
					newSortNo = String.valueOf( oldSubNo * 10 + 100 );
				}
					
			}else{					
				//第N条 子编码
				String[] split = subSortNo.split("-");
				String maxNo = split[split.length-1];
				int oldSubNo = Integer.parseInt(maxNo);
				int newSubNo = oldSubNo + 100;
				newSortNo = String.valueOf(newSubNo);
				if (newSortNo.length() > maxNo.length()){
					newSortNo = parentSortNo + "-" + String.valueOf(oldSubNo * 10 + 100);	
				}else{
					newSortNo = parentSortNo + "-" + String.valueOf(oldSubNo + 100);	
				}
				
			}
		}
						
		//新增处理
		S_WarehouseCodeData newDt = new S_WarehouseCodeData();

		newDt.setCodeid(newCodeId);
		newDt.setSortno(newSortNo);
		newDt.setParentid(parentCodeId);
		newDt.setSubid(codeId);
		newDt.setCodetype(reqData.getCodetype());
		newDt.setCodename(reqData.getCodename());
		newDt.setRemarks(reqData.getRemarks());
		newDt.setMultilevel(String.valueOf(iLevel));
		newDt.setEffectiveflag("Y");//使用标识
		
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"insert",userInfo);
		copyProperties(newDt,commData);

		guid = BaseDAO.getGuId();
		newDt.setRecordid(guid);
		
		new S_WarehouseCodeDao().Create(newDt);
				
		
	}	
	
	public void updateWarehouseCode() throws Exception {
		
		S_WarehouseCodeData reqData = reqModel.getWarehouse();

		S_WarehouseCodeData db = new S_WarehouseCodeDao(reqData).beanData;
		
		copyProperties(db,reqData);
		db.setEffectiveflag("Y");//使用标识
		
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"update",userInfo);
		copyProperties(db,commData);

		new S_WarehouseCodeDao().Store(db);
				
		
	}	

	public void deleteWarehouseCode() throws Exception {

		String recordId = request.getParameter("recordId");
			
		S_WarehouseCodeData dt = new S_WarehouseCodeData();
		dt.setRecordid(recordId);
		dt = new S_WarehouseCodeDao(dt).beanData;
							
		commData = commFiledEdit(Constants.ACCESSTYPE_DEL,
				"insert",userInfo);
		copyProperties(dt,commData);
				
		new S_WarehouseCodeDao().Store(dt);
	}
	
	public void updateSortNo() throws Exception {

		String recordId = request.getParameter("recordId");
		String sortFlag = request.getParameter("sortFlag");
		
		//D:向下移；U：向上移
		//原则上在同一层次内改变顺序号
		
		S_WarehouseCodeData dt = new S_WarehouseCodeData();
		dt.setRecordid(recordId);
		dt = new S_WarehouseCodeDao(dt).beanData;
							
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"insert",userInfo);
		copyProperties(dt,commData);
				
		new S_WarehouseCodeDao().Store(dt);
	}
		
}
