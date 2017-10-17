package com.ys.business.service.order;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ys.business.action.model.order.InspectionReturnModel;
import com.ys.business.action.model.order.ReceiveInspectionModel;
import com.ys.business.db.dao.B_ArrivalDao;
import com.ys.business.db.dao.B_InspectionProcessDao;
import com.ys.business.db.dao.B_InspectionReturnDao;
import com.ys.business.db.dao.B_PurchaseOrderDao;
import com.ys.business.db.dao.B_PurchaseOrderDetailDao;
import com.ys.business.db.dao.B_ReceiveInspectionDao;
import com.ys.business.db.dao.B_ReceiveInspectionDetailDao;
import com.ys.business.db.data.B_ArrivalData;
import com.ys.business.db.data.B_InspectionProcessData;
import com.ys.business.db.data.B_InspectionReturnData;
import com.ys.business.db.data.B_PurchaseOrderData;
import com.ys.business.db.data.B_PurchaseOrderDetailData;
import com.ys.business.db.data.B_ReceiveInspectionData;
import com.ys.business.db.data.B_ReceiveInspectionDetailData;
import com.ys.business.db.data.CommFieldsData;
import com.ys.business.service.common.BusinessService;
import com.ys.system.action.model.login.UserInfo;
import com.ys.util.basequery.common.BaseModel;
import com.ys.util.basequery.common.Constants;

import jxl.write.DateTime;

import com.ys.util.CalendarUtil;
import com.ys.util.DicUtil;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basedao.BaseTransaction;
import com.ys.util.basequery.BaseQuery;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class InspectionReturnService extends CommonService  {
 
	DicUtil util = new DicUtil();
	BaseTransaction ts;

	String guid ="";
	private CommFieldsData commData;
	
	private HttpServletRequest request;
	
	private B_ReceiveInspectionDao dao;
	private InspectionReturnModel reqModel;
	private UserInfo userInfo;
	private BaseModel dataModel;
	private  Model model;
	private HashMap<String, String> userDefinedSearchCase;
	private BaseQuery baseQuery;
	HashMap<String, Object> modelMap = null;
	HttpSession session;	

	public InspectionReturnService(){
		
	}

	public InspectionReturnService(Model model,
			HttpServletRequest request,
			HttpSession session,
			InspectionReturnModel reqModel,
			UserInfo userInfo){
		
		this.dao = new B_ReceiveInspectionDao();
		this.model = model;
		this.reqModel = reqModel;
		this.request = request;
		this.userInfo = userInfo;
		this.session = session;
		dataModel = new BaseModel();
		modelMap = new HashMap<String, Object>();
		userDefinedSearchCase = new HashMap<String, String>();
		dataModel.setQueryFileName("/business/material/inventoryquerydefine");
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

		String[] keyArr = getSearchKey(Constants.FORM_INSPECTIONRETURN,data,session);
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
		
		dataModel.setQueryName("getInspectionReturnList");
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
		if((key1 !=null && !("").equals(key1)) || 
				(key2 !=null && !("").equals(key2))){
			userDefinedSearchCase.put("checkResult", "");
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
	

	public String addInit() throws Exception {

		//取得进料检报告编号
		//getRecordId();
		
		String arrivalId = request.getParameter("arrivalId");
		String materialId = request.getParameter("materialId");
		
		String inspectArrivalId = getReceiveInspection(arrivalId);
		
		model.addAttribute("resultList",util.getListOption(DicUtil.RECEIVEINSPECTION, ""));

		return inspectArrivalId;
	
	}

	public void updateInit() throws Exception {

		//取得到货信息
		String arrivalId = reqModel.getInspectReturn().getArrivalid() ;
		//String materialId = reqModel.getInspect().getMaterialid();
		String inspectArrivalId = getReceiveInspection(arrivalId);
		
		//model.addAttribute("resultList",util.getListOption(DicUtil.RECEIVEINSPECTION, ""));
	
	}
	public void showArrivalDetail() {

		String arrivalId = request.getParameter("arrivalId");
		getArrivaRecord(arrivalId);
	}
	
	public void insertAndView() throws Exception {

		String arrivalId = insert();

		getReceiveInspection(arrivalId);
	}	
	
	public void updateAndView() throws Exception {

		String arrivalId = update();

		getReceiveInspection(arrivalId);
	}

	private String insert(){
		String arrivalId = "";
		ts = new BaseTransaction();
		
		try {
			ts.begin();
			
			B_InspectionReturnData reqData = reqModel.getInspectReturn();
			List<B_InspectionReturnData> reqList = reqModel.getInspectReturnList();
			//B_InspectionProcessData process = reqModel.getProcess();
			//String result = process.getManagerresult();
			
			//退货编号
			reqData = getReceiveInspectionId(reqData);	
			
			arrivalId = reqData.getArrivalid();
			//退货处理
			for(B_InspectionReturnData data:reqList){
				
				copyProperties(data,reqData);
				insertReceivInspection(data);			
				
				//累计退货数量,退货次数
				updatePurchaseOrderDetail(data);
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
		
		return arrivalId;
	}
	
	
	private String update(){
		String arrivalId = "";
		ts = new BaseTransaction();
		
		try {
			ts.begin();
			
			B_InspectionReturnData reqData = reqModel.getInspectReturn();
			List<B_InspectionReturnData> reqList = reqModel.getInspectReturnList();
			
			String returnId=reqData.getInspectionreturnid();
			String where = "inspectionReturnId = '" + returnId +"'";
			try{
				new B_InspectionReturnDao().RemoveByWhere(where);
			}catch(Exception e){
				//
			}
			
			arrivalId = reqData.getArrivalid();
			//退货处理
			for(B_InspectionReturnData data:reqList){
				
				copyProperties(data,reqData);
				insertReceivInspection(data);			
				
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
		
		return arrivalId;
	}
	
	private void insertReceivInspection(
			B_InspectionReturnData data) throws Exception{
					
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"InspctionReturnInsert",userInfo);
		copyProperties(data,commData);
		guid = BaseDAO.getGuId();
		data.setRecordid(guid);
		data.setStatus(Constants.INSPECTIONRETURN_STS_2);//已处理
		
		new B_InspectionReturnDao().Create(data);
	}

	@SuppressWarnings("unchecked")
	private void updatePurchaseOrderDetail(
			B_InspectionReturnData inspect) throws Exception{
	
		float rtnQuantity = stringToFloat(inspect.getReturnquantity());//本次退货数量
		
		String where = "contractId ='"+ inspect.getContractid() + 
				"' AND materialId ='"+ inspect.getMaterialid() + 
				"' AND deleteFlag='0' ";
		
		List<B_PurchaseOrderDetailData> list = 
				(List<B_PurchaseOrderDetailData>) 
				new B_PurchaseOrderDetailDao().Find(where);
		
		if(list ==null || list.size() == 0)
			return ;		
		B_PurchaseOrderDetailData detail = list.get(0);
		
		int number = stringToInteger(detail.getReturnnumber());//退货次数
		float returngoods = stringToFloat(detail.getQuantity());//累计退货数量
		
		number = number + 1;//累计退货次数
		float totalQuantity = rtnQuantity + returngoods;//累计退货数量
		
		//更新DB
		detail.setReturnnumber(String.valueOf(number));
		detail.setReturngoods(String.valueOf(totalQuantity));
		
		updateContractDetailStatus(detail);	
		
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
	
	
	
	
	public String getReceiveInspection(String arrivalId) throws Exception {

		dataModel.setQueryName("getInspectionReturnList");
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("arrivalId", arrivalId);
		//userDefinedSearchCase.put("materialId", materialId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);			
		baseQuery.getYsFullData();	
		
		model.addAttribute("material",dataModel.getYsViewData());
		model.addAttribute("arrived",dataModel.getYsViewData().get(0));
		String	inspectArrivalId = dataModel.getYsViewData().get(0).get("inspectionReturnId");		
	
		return inspectArrivalId;
	}
	
	public void getContractDetail(String contractId) throws Exception {
		
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
	
	public B_InspectionReturnData getReceiveInspectionId(B_InspectionReturnData data) throws Exception {
	
		String parentid = data.getContractid();
		dataModel.setQueryName("getMAXInspectionReturnId");
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("parentId", parentid);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);			
		baseQuery.getYsFullData();	
		//查询出的流水号已经在最大值上 " 加一 "了
		String code = dataModel.getYsViewData().get(0).get("MaxSubId");		
		
		String inspectionId = 
				BusinessService.getInspectionReturnId(parentid,code,false);
		
		//B_ReceiveInspectionData data = new B_ReceiveInspectionData();
		data.setInspectionreturnid(inspectionId);
		data.setParentid(parentid);
		data.setSubid(code);
	//	reqModel.setInspect(data);
		
		return data;		
	}
	
	public HashMap<String, Object> getArrivalHistory(
			String contractId) throws Exception {
		
		dataModel.setQueryName("getArrivaListByContractId");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("contractId", contractId);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
		
	}
	
	
	public void getContractByArrivalId() throws Exception {

		String contractId = request.getParameter("contractId");
		String arrivalId = request.getParameter("arrivalId");
		
		dataModel.setQueryName("getContractByArrivalId");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("contractId", contractId);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		String sql = baseQuery.getSql();
		sql = sql.replace("#", arrivalId);
		baseQuery.getYsFullData(sql);

		if(dataModel.getRecordCount() >0){
			model.addAttribute("contract",dataModel.getYsViewData().get(0));
			model.addAttribute("material",dataModel.getYsViewData());
			model.addAttribute("arrivalId",arrivalId);
		}		
	}

}
