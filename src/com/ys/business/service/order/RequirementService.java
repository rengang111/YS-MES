package com.ys.business.service.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.system.service.common.BaseService;
import com.ys.util.DicUtil;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basedao.BaseTransaction;
import com.ys.util.basequery.BaseQuery;
import com.ys.util.basequery.common.BaseModel;
import com.ys.util.basequery.common.Constants;
import com.ys.business.action.model.common.ListOption;
import com.ys.business.action.model.order.RequirementModel;
import com.ys.business.action.model.organ.OrganModel;
import com.ys.business.db.dao.B_MaterialRequirmentDao;
import com.ys.business.db.dao.B_OrderDao;
import com.ys.business.db.dao.B_OrderDetailDao;
import com.ys.business.db.data.B_MaterialRequirmentData;
import com.ys.business.db.data.B_OrderData;
import com.ys.business.db.data.B_OrderDetailData;
import com.ys.business.db.data.CommFieldsData;
import com.ys.business.service.common.BusinessService;

@Service
public class RequirementService extends BaseService {

	DicUtil util = new DicUtil();

	BaseTransaction ts;

	String guid ="";
	private CommFieldsData commData;
	
	private HttpServletRequest request;
	
	private B_MaterialRequirmentDao dao;
	private RequirementModel reqModel;
	private UserInfo userInfo;
	private BaseModel dataModel;
	private  Model model;
	private HashMap<String, String> userDefinedSearchCase;
	private BaseQuery baseQuery;
	ArrayList<HashMap<String, String>> modelMap = null;	

	public RequirementService(){
		
	}

	public RequirementService(Model model,
			HttpServletRequest request,
			RequirementModel reqModel,
			UserInfo userInfo){
		
		this.dao = new B_MaterialRequirmentDao();
		this.model = model;
		this.reqModel = reqModel;
		this.dataModel = new BaseModel();
		this.request = request;
		this.userInfo = userInfo;
		userDefinedSearchCase = new HashMap<String, String>();
		dataModel.setQueryFileName("/business/order/zzorderquerydefine");
		
	}

	public void getZZOrderDetail( 
			String orderId) throws Exception {

		dataModel.setQueryName("getZZOrderDetail");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("orderId", orderId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		modelMap = baseQuery.getYsQueryData(0, 0);
			
		model.addAttribute("order",dataModel.getYsViewData().get(0));		
		model.addAttribute("detail", dataModel.getYsViewData());
		
	}
	
	public void getOrderZZRawList( 
			String YSId) throws Exception {

		dataModel.setQueryName("getOrderZZRawList");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("YSId", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		modelMap = baseQuery.getYsQueryData(0, 0);
			
		model.addAttribute("order",dataModel.getYsViewData().get(0));		
		model.addAttribute("rawDetail", dataModel.getYsViewData());
		
	}
	
	public void getOrderZZRawSum( 
			String YSId) throws Exception {

		dataModel.setQueryName("getOrderZZRawSum");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("YSId", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		modelMap = baseQuery.getYsQueryData(0, 0);
				
		model.addAttribute("rawGroup", dataModel.getYsViewData());
		
	}
	public void getOrderRawList( 
			String YSId) throws Exception {

		dataModel.setQueryName("getOrderRawList");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("YSId", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		modelMap = baseQuery.getYsQueryData(0, 0);
			
		model.addAttribute("order",dataModel.getYsViewData().get(0));		
		model.addAttribute("rawDetail", dataModel.getYsViewData());
		
	}
	
	public void getOrderRawSum( 
			String YSId) throws Exception {

		dataModel.setQueryName("getOrderRawSum");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("YSId", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		modelMap = baseQuery.getYsQueryData(0, 0);
				
		model.addAttribute("rawGroup", dataModel.getYsViewData());
		
	}

	public void getOrderPartList( 
			String YSId) throws Exception {

		dataModel = new BaseModel();
		dataModel.setQueryFileName("/business/order/zzorderquerydefine");
		dataModel.setQueryName("getOrderPartList");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("YSId", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		modelMap = baseQuery.getYsQueryData(0, 0);
	
		model.addAttribute("rawGroup", dataModel.getYsViewData());
		
	}

	public void getRequirementList( 
			String YSId) throws Exception {

		dataModel = new BaseModel();
		dataModel.setQueryFileName("/business/order/zzorderquerydefine");
		dataModel.setQueryName("getRequirementList");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("YSId", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		modelMap = baseQuery.getYsQueryData(0, 0);
	
		model.addAttribute("rawGroup", dataModel.getYsViewData());
		
	}
	
	public void getOrderDetailByYSId( 
			String YSId) throws Exception {

		dataModel = new BaseModel();
		dataModel.setQueryFileName("/business/order/bomquerydefine");
		dataModel.setQueryName("getOrderDetailByYSId");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("keywords1", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		modelMap = baseQuery.getYsQueryData(0, 0);

		model.addAttribute("order",dataModel.getYsViewData().get(0));
		
	}
	
	/*
	 * 
	 */
	public HashMap<String, Object> getZZMaterialList() throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();

		String key = request.getParameter("key").toUpperCase();

		dataModel.setQueryFileName("/business/order/zzorderquerydefine");
		dataModel.setQueryName("zzmaterialList");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("keywords1", key);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, 0);	 

		modelMap.put("data", dataModel.getYsViewData());
		
		modelMap.put("retValue", "success");			
		
		return modelMap;
	}
	
	/*
	 * 1.原材料需求表新增处理(N条数据)
	 */
	public String insert(String YSId) throws Exception  {

		ts = new BaseTransaction();

		try {
			ts.begin();
			
			//首先删除DB中的BOM详情
			String where = " YSId = '"+YSId +"'";
			deleteMaterialRequirement(where);
						
			//处理订单详情数据		
			List<B_MaterialRequirmentData> reqDataList = reqModel.getRequirmentList();
			
			for(B_MaterialRequirmentData data:reqDataList ){

				insertRequirment(data);
				
			}
			
			ts.commit();
			
		}
		catch(Exception e) {
			ts.rollback();
			reqModel.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return YSId;
	}	

	/*
	 * 订单详情插入处理
	 */
	private void insertRequirment(
			B_MaterialRequirmentData data) throws Exception{
		
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"MaterialRequirmentInsert",userInfo);

		copyProperties(data,commData);
		
		guid = BaseDAO.getGuId();
		data.setRecordid(guid);
		
		dao.Create(data);	
	}	
	
	/*
	 * 订单基本信息更新处理
	 */
	public void updateRequirment(
			B_MaterialRequirmentData data) throws Exception{

			//处理共通信息
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"MaterialRequirementUpdate",userInfo);
		copyProperties(dao.beanData,commData);

		//获取页面数据
		dao.beanData.setQuantity(data.getQuantity());
		dao.beanData.setPrice(data.getPrice());
		dao.beanData.setTotalprice(data.getTotalprice());
		
		dao.Store(dao.beanData);

	}

	/*
	 * 审批处理:常规订单是以耀升编号为单位更新,
	 * 库存订单存在多个相同的耀升编号,需要逐条更新
	 */
	public void updateDetailToApprove(String PIId) throws Exception  {

		ts = new BaseTransaction();
		
		try {
			
			ts.begin();

			//根据画面的PiId取得DB中更新前的订单详情 
			List<B_OrderDetailData> detailList = null;

			//库存订单的PI编号,订单编号,耀升编号相同
			String where = " PIId = '"+PIId +"'" + " AND deleteFlag = '0' ";
			//detailList = getOrderDetailByPIId(where);
						
			for(B_OrderDetailData dbData:detailList ){				
					
				//更新处理//处理共通信息
				commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
						"ZZOrderDetailUpdate",userInfo);

				copyProperties(dbData,commData);
				//更新审批状态:下一步是生成物料需求表
				dbData.setStatus(Constants.ORDER_STS_3);
				
				//detailDao.Store(dbData);
			}
			
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
		}
		
	}	

	/*
	 * 删除处理
	 */
	public void deleteMaterialRequirement(String where) 
			throws Exception{
		
		try{
			dao.RemoveByWhere(where);
		}catch(Exception e){
			//nothing
		}	
	}

	public void insertAndView() throws Exception {

		B_MaterialRequirmentData requirement = reqModel.getRequirment();
		String YSId = requirement.getYsid();
		
		insert(YSId);
		
		String sub = YSId.substring(YSId.length()-2);
		
		if (sub.equals(BusinessConstants.SHORTNAME_ZZ)){
			
			getOrderZZRawList(YSId);
			
			getOrderZZRawSum(YSId);	
			
		}else{

			getOrderDetailByYSId(YSId);
			
			getRequirementList(YSId);
			
		}
	}

	public void editZZorder() throws Exception {
		
		String YSId = request.getParameter("YSId");
		
		getOrderZZRawList(YSId);
		
		getOrderZZRawSum(YSId);
	}
	
	public void editorder() throws Exception {
		
		String YSId = request.getParameter("YSId");
		
		getOrderRawList(YSId);
		
		getOrderRawSum(YSId);
	}
	
	public void editorderPart() throws Exception {
		
		String YSId = request.getParameter("YSId");

		getOrderDetailByYSId(YSId);
		
		getOrderPartList(YSId);
	}
	public void approveAndView() throws Exception {
		
		String YSId = request.getParameter("YSId");
		
		updateDetailToApprove(YSId);
		
		//getZZOrderDetail(YSId);	
		
	}
	
}
