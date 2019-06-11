package com.ys.business.service.order;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import com.ys.system.action.model.login.UserInfo;
import com.ys.util.CalendarUtil;
import com.ys.util.DicUtil;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basedao.BaseTransaction;
import com.ys.util.basequery.BaseQuery;
import com.ys.util.basequery.common.BaseModel;
import com.ys.util.basequery.common.Constants;
import com.ys.business.action.model.order.ProduceModel;
import com.ys.business.db.dao.B_OrderDetailDao;
import com.ys.business.db.dao.B_OrderMergeDetailDao;
import com.ys.business.db.dao.B_OrderProduceHideDao;
import com.ys.business.db.dao.B_ProducePlanDao;
import com.ys.business.db.dao.S_ProduceLineCodeDao;
import com.ys.business.db.dao.S_WarehouseCodeDao;
import com.ys.business.db.data.B_OrderDetailData;
import com.ys.business.db.data.B_OrderMergeDetailData;
import com.ys.business.db.data.B_OrderProduceHideData;
import com.ys.business.db.data.B_ProducePlanData;
import com.ys.business.db.data.CommFieldsData;
import com.ys.business.db.data.S_ProduceLineCodeData;
import com.ys.business.db.data.S_WarehouseCodeData;

@Service
public class ProduceService extends CommonService  {

	DicUtil util = new DicUtil();

	BaseTransaction ts;
	String guid ="";
	private CommFieldsData commData;

	private ProduceModel reqModel;

	private HttpServletRequest request;
	private UserInfo userInfo;
	private BaseModel dataModel;
	private Model model;
	private HashMap<String, String> userDefinedSearchCase;
	private BaseQuery baseQuery;
	ArrayList<HashMap<String, String>> modelMap = null;	
	HashMap<String, Object> hashMap = null;	
	HttpSession session;
	
	public ProduceService(){
		
	}

	public ProduceService(
			Model model,
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			ProduceModel reqModel,
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
	
	public HashMap<String, Object> produceLineCodeSearch(String formId,String data) throws Exception {
		
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
		dataModel.setQueryFileName("/business/order/producequerydefine");
		dataModel.setQueryName("getProduceLineCodeList");
		
		String key = request.getParameter("codeId");
		String sql = baseQuery.getSql();
		sql = sql.replace("#", key);
		System.out.println("生产线编码："+sql);
		
		baseQuery.getYsQueryData(sql,key,iStart, iEnd);	 
		
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
	

	public HashMap<String, Object> producePlanSearch( String data) throws Exception {

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
		String[] keyArr = getSearchKey(Constants.FORM_REQUISITION,data,session);
		String key1 = keyArr[0];
		String key2 = keyArr[1];		

		dataModel.setQueryFileName("/business/order/producequerydefine");
		dataModel.setQueryName("getProduceTaskForPlan");
		String searchFlag = request.getParameter("searchFlag");
		String orderType = request.getParameter("orderType");//订单类型
		String partsType = request.getParameter("partsType");//常规订单，配件单
		String produceLine = URLDecoder.decode(request.getParameter("produceLine"),"UTF-8");
		
		String where = " 1=1 ";
		//*** 关键字
		if(notEmpty(key1) && notEmpty(key2)){
			where = " full_field like '%"+key1+"%' AND full_field like '%"+key2+"%' ";
		}else{
			if(notEmpty(key1)){
				where = " full_field like '%"+key1+"%' ";
			}
			if(notEmpty(key2)){
				where = " full_field like '%"+key2+"%' ";
			}
		}
		
		//*** 快捷查询
		String having1 =" hideFlag='F' ";//false：不显示隐藏
		String having2 = " computeStockinQty+0 < orderQty+0 ";//成品未全部入库
		if(("U").equals(searchFlag)){
			//未安排
			having1 += " AND currentSts IS NULL ";//过滤掉当前任务
		}else if(("C").equals(searchFlag)){
			//当前任务
			having1 += " AND currentSts = '0' AND currentType='31' ";//只显示当前任务
		}else if(("L").equals(searchFlag)){
			//中长期生产计划
			having1 += " AND currentSts = '0' AND currentType='32' ";//中长期生产计划
		}else if(("N").equals(searchFlag)){
			//未领料
			having1 += " AND currentSts = '0' AND currentType='33' ";
		}else if(("F").equals(searchFlag)){
			//成品全部入库	
			having2 = " computeStockinQty+0 >= orderQty+0 ";		
		}else{
			//全状态查询
			having1 = " 1=1 ";
			having2 = " 1=1 ";
		}
		
		//*** 常规订单，配件单
		String where3 = " SUBSTRING_INDEX(SUBSTRING_INDEX(A.materialId,'.',2),'.',-1) NOT IN ('BTR','BNC','CGR','PJ') ";
		if(("P").equals(partsType)){
			//配件单
			where3 = " SUBSTRING_INDEX(SUBSTRING_INDEX(A.materialId,'.',2),'.',-1) IN ('BTR','BNC','CGR','PJ') ";
		}
		
		//*** 生产线
		if(("999").equals(produceLine)){
			produceLine = "";
		}

		userDefinedSearchCase.put("produceLine", produceLine);	
		userDefinedSearchCase.put("orderType", orderType);	
		baseQuery = new BaseQuery(request, dataModel);	
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		String sql = getSortKeyFormWeb(data,baseQuery);	
		
		sql = sql.replace("#0",where );
		sql = sql.replace("#1",having1);
		sql = sql.replace("#2",having2);
		sql = sql.replace("#3",where3);

		List<String> list = new ArrayList<String>();
		list.add(where);
		list.add(having1);
		list.add(having2);
		list.add(where3);
		
		System.out.println("生产计划查询："+sql);
		baseQuery.getYsQueryData(sql,list,iStart, iEnd);	 
		
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
	
	public HashMap<String, Object> getOrderList(String formId,String data) throws Exception {
		
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

		dataModel.setQueryFileName("/business/order/producequerydefine");
		dataModel.setQueryName("getOrderListForTask");
		String searchFlag = request.getParameter("searchFlag");
		String orderType = request.getParameter("orderType");//订单类型
		
		String where = " 1=1 ";
		//*** 关键字
		if(notEmpty(key1) && notEmpty(key2)){
			where = " full_field like '%"+key1+"%' AND full_field like '%"+key2+"%' ";
		}else{
			if(notEmpty(key1)){
				where = " full_field like '%"+key1+"%' ";
			}
			if(notEmpty(key2)){
				where = " full_field like '%"+key2+"%' ";
			}
		}
		
		if(("S").equals(searchFlag)){
			//收起
			dataModel.setQueryName("getOrderListForTaskGroup");
		}	
		
		if(("030").equals(orderType)){
			//查看隐藏数据
			userDefinedSearchCase.put("orderType", "");
		}else{
			userDefinedSearchCase.put("orderType", orderType);			
		}
				
		String having1 =" hideFlag='F' ";//false：不显示隐藏
		if(("Y").equals(searchFlag)){
			//隐藏
			having1 = " hideFlag='T' ";//True:显示隐藏
		}	
		
		//*** 全挪用
		String having2 = "computeStockinQty+0 < orderQty+0";
		if(("N").equals(searchFlag)){
			where = " a.diverFlag='1' AND a.quantity+0 = 0 ";
			having1 = " 1=1 ";
			having2 = " 1=1 ";
		}
		
		baseQuery = new BaseQuery(request, dataModel);	
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		String sql = getSortKeyFormWeb(data,baseQuery);	
		sql = sql.replace("#0",where );
		sql = sql.replace("#1",having1);
		sql = sql.replace("#2",having2);

		List<String> list = new ArrayList<String>();
		list.add(where);
		list.add(having1);
		list.add(having2);
		
		System.out.println("生产任务合并查询："+sql);
		baseQuery.getYsQueryData(sql,list,iStart, iEnd);	 
		
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
	
	public HashMap<String, String> getAndSetSortKey(String data){

		HashMap<String, String> map = new HashMap<String, String>();
		
		String iSortCol_0 = (String) session.getAttribute("iSortCol_0");
		String sSortDir_0 = (String) session.getAttribute("sSortDir_0");
		
		if(isNullOrEmpty(iSortCol_0)){

			iSortCol_0 = getJsonData(data, "iSortCol_0");
			sSortDir_0 = getJsonData(data, "sSortDir_0");
		}
		map.put("iSortCol_0",iSortCol_0);
		map.put("sSortDir_0",sSortDir_0);
		
		//重新存储排序条件
		session.setAttribute("iSortCol_0", iSortCol_0);
		session.setAttribute("sSortDir_0", sSortDir_0);
		
		return map;
	}

	public HashMap<String, Object> getOrderCancelList(String formId,String data) throws Exception {
		
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

		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("getOrderCancelList");	
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
		if(notEmpty(key1) || notEmpty(key2))
			userDefinedSearchCase.put("status", "");
		
		baseQuery = new BaseQuery(request, dataModel);	
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

	public HashMap<String, Object> getOrderExpenseList(String formId,String data) throws Exception {
		
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
		
		//String monthday = request.getParameter("monthday");
		//if(isNullOrEmpty(monthday)){
		//	monthday = CalendarUtil.getToDay();
		//}
		//FinanceMouthly monthly = new FinanceMouthly(monthday);

		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("getOrderExpenseList");	
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
		
		String costType = request.getParameter("costType");
		String having = "1=1";
		
		if(notEmpty(key1) || notEmpty(key2)){
			costType = "";//有查询key，则忽略其状态
			userDefinedSearchCase.put("year", "");//忽略其时间段
			//userDefinedSearchCase.put("endDate", "");//忽略其时间段			
		}
		
		
		if(("C").equals(costType)){
			having=" chejianCost < 0 ";//车间费用

			//userDefinedSearchCase.put("orderStartDate", monthly.getStartDate());
			//userDefinedSearchCase.put("orderEndDate", monthly.getEndDate());
			
		}else if(("S").equals(costType)){
			having=" gongyingshangCost > 0 ";//供应商费用

			//userDefinedSearchCase.put("startDate", monthly.getStartDate());
			//userDefinedSearchCase.put("endDate", monthly.getEndDate());
			
		}else if(("K").equals(costType)){
			having=" kehuCost <> 0 ";//客户费用	
			
			//userDefinedSearchCase.put("orderStartDate", monthly.getStartDate());
			//userDefinedSearchCase.put("orderEndDate", monthly.getEndDate());			
		}else if(("J").equals(costType)){
			having=" jianyanCost <> 0 ";//检验费用
			
			//userDefinedSearchCase.put("orderStartDate", monthly.getStartDate());
			//userDefinedSearchCase.put("orderEndDate", monthly.getEndDate());			
		}else if(("G").equals(costType)){
			having=" gendanCost < 0 ";//跟单费用
			
			//userDefinedSearchCase.put("orderStartDate", monthly.getStartDate());
			//userDefinedSearchCase.put("orderEndDate", monthly.getEndDate());			
		}
		
		baseQuery = new BaseQuery(request, dataModel);	
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		String sql = getSortKeyFormWeb(data,baseQuery);	
		sql = sql.replace("#", having);
		System.out.println("订单过程查询SQL："+sql);	
		baseQuery.getYsQueryData(sql,having,iStart, iEnd);	 
		
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
	
	public HashMap<String, Object> getOrderTrackingList(String formId,String data) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();

		data = URLDecoder.decode(data, "UTF-8");
		
		int iStart = 0;
		int iEnd =0;
		

		String monthday = getJsonData(data, "monthday");
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		Date d = sd.parse(monthday);

		String firstDay = monthday;
		String lastDay = CalendarUtil.getlastDayOfMonth(d);
		
		
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
		
		//"ord.status not in('040','041','050','051') " 
		String whereMonthDay =  " LEFT(ord.materialId,1) <> 'H' " +
							" AND LEFT(ord.materialId, 1) = 'I' ";
		String having = "1=1";
		String follow = "";
		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("orderTrackingList");
		
		
		if(isNullOrEmpty(key1) && isNullOrEmpty(key2)){
			follow  = request.getParameter("follow");//重点关注
			String orderSts = request.getParameter("orderSts");//备货情况
			if(("1").equals(orderSts)){
				//货已备齐
				whereMonthDay += " AND REPLACE(ord.completedQuantity,',','')+0 >= REPLACE(ord.quantity,',','')+0  ";
				having = " stockFlag+0 = 0 ";
			}else if(("2").equals(orderSts)){
				//未齐
				whereMonthDay += " AND REPLACE(ord.completedQuantity,',','')+0 < REPLACE(ord.quantity,',','')+0  ";
				having = "stockFlag+0 > 0";
			}else{
				//已领料
				whereMonthDay += " AND REPLACE(ord.completedQuantity,',','')+0 >= REPLACE(ord.quantity,',','')+0 ";
				whereMonthDay += " AND ord.deliveryDate >= '"+firstDay+"' AND ord.deliveryDate <= '"+lastDay+"' ";
				//having = " stockoutEnd+0 =  0 ";
			}
		}else{
			if(notEmpty(key1) && notEmpty(key2)){
				whereMonthDay = "full_field  like '%"+key1+"%' AND fullField  like '%"+key2+"%' ";
			}else if(notEmpty(key1))
				whereMonthDay = "full_field  like '%"+key1+"%'";
			else
				whereMonthDay = "full_field  like '%"+key2+"%'";

			//whereMonthDay += " AND REPLACE(ord.completedQuantity,',','')+0 = 0  ";
		}
		
		//userDefinedSearchCase.put("keyword1", key1);
		//userDefinedSearchCase.put("keyword2", key2);
		userDefinedSearchCase.put("follow", follow);
		
		baseQuery = new BaseQuery(request, dataModel);	
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		String sql = getSortKeyFormWeb(data,baseQuery);	
		sql = sql.replace("#0", whereMonthDay);
		sql = sql.replace("#1", having);
		System.out.println("订单跟踪:"+sql);
		
		List<String> list = new ArrayList<String>();
		list.add(whereMonthDay);
		list.add(having);
		
		baseQuery.getYsQueryData(sql,list,iStart,iEnd);	 
		
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

	public  HashMap<String, Object> produceTracMerge() throws Exception{
		

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		
		updateTaskMerge();
		
		modelMap = getTaskMerge();
		
		return modelMap;
	}
	
	public  HashMap<String, Object> productTaskOrderHide() throws Exception{
		

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		
		updateProduceOrderHide();
		
		modelMap = getTaskMerge();
		
		return modelMap;
	}
	
	public  HashMap<String, Object> productTaskOrderShow() throws Exception{
		

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		
		updateProduceOrderShow();
		
		modelMap = getTaskMerge();
		
		return modelMap;
	}
	
	private void updateProduceOrderShow() throws Exception{
		
		String checked = request.getParameter("checkedList");
		
		ts = new BaseTransaction();

		try {			
			ts.begin();
			
			List<String> checkedList = setYsidFromSplit(checked);			
			//
			for(String check:checkedList){
				String ysid   = check.split(",")[0];
				String newst  = check.split(",")[1];
				String oldst  = check.split(",")[2];
				
				//恢复订单（在隐藏数据里）				
				if(("T").equals(newst) && ("T").equals(oldst)){
					updateHideOrder(ysid);
				}						
			}
			
			ts.commit();
		}
		catch(Exception e) {
			e.printStackTrace();
			ts.rollback();
		}
	}
	
	private void updateProduceOrderHide() throws Exception{
		
		String checked = request.getParameter("checkedList");
		
		ts = new BaseTransaction();

		try {			
			ts.begin();
			
			List<String> checkedList = setYsidFromSplit(checked);
			
			//
			for(String check:checkedList){
				String ysid   = check.split(",")[0];
				String newst  = check.split(",")[1];
				String oldst  = check.split(",")[2];
				
				//删除页面取消的订单（在隐藏数据里）
				if(("F").equals(newst) && ("T").equals(oldst)){
					deleteHideOrder(ysid);
				}
				//新增隐藏的订单
				if(("T").equals(newst) && ("F").equals(oldst)){
					insertHideOrder(ysid);
				}
				//newst=1,oldst=1的订单:检查订单是否入库，是就删除隐藏，否则保留
				if(("T").equals(newst) && ("T").equals(oldst)){
					checkHideOrder(ysid);
				}
				//newst=0,oldst=0的订单在页面端被过滤掉了。								
			}
			
			ts.commit();
		}
		catch(Exception e) {
			e.printStackTrace();
			ts.rollback();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void deleteHideOrder(String ysid){
		String where = " ysid = '"+ysid +"'AND deleteFlag='0' " ;
		List<B_OrderProduceHideData> list;
		try {
			list = new B_OrderProduceHideDao().Find(where);
			if(list.size() > 0 ){
				B_OrderProduceHideData detail = list.get(0);
				commData = commFiledEdit(Constants.ACCESSTYPE_DEL,
						"OrderDelete",userInfo);
				copyProperties(detail,commData);			
				new B_OrderMergeDetailDao().Store(detail);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	private void insertHideOrder(String ysid){

		try {			
							
			B_OrderProduceHideData hide = new B_OrderProduceHideData();				

			hide.setYsid(ysid);
			hide.setHideflag("T");//隐藏
			
			commData = commFiledEdit(Constants.ACCESSTYPE_INS,
					"订单隐藏insert",userInfo);
			copyProperties(hide,commData);	
			guid = BaseDAO.getGuId();				
			hide.setRecordid(guid);
			
			new B_OrderProduceHideDao().Create(hide);
							
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	@SuppressWarnings("unchecked")
	private void updateHideOrder(String ysid){
		String where = " ysid = '"+ysid +"' AND deleteFlag='0' " ;

		try {			
			List<B_OrderProduceHideData> list = new B_OrderProduceHideDao().Find(where);
			
			if(list.size() > 0 ){
				
				B_OrderProduceHideData hide = list.get(0);				

				hide.setHideflag("F");//不再隐藏，恢复显示
				
				commData = commFiledEdit(Constants.ACCESSTYPE_DEL,
						"订单隐藏恢复显示",userInfo);
				copyProperties(hide,commData);
				
				new B_OrderProduceHideDao().Store(hide);
							
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	private void updateTaskMerge() throws Exception{
		
		String checked = request.getParameter("checkedList");
		
		ts = new BaseTransaction();

		try {			
			ts.begin();
			
			List<String> checkedList = setYsidFromSplit(checked);
			
			//
			for(String check:checkedList){
				String ysid   = check.split(",")[0];
				String newst  = check.split(",")[1];
				String oldst  = check.split(",")[2];
				
				//删除页面取消的订单（在合并数据里）
				if(("0").equals(newst) && ("1").equals(oldst)){
					deleteMergeOrder(ysid);
				}
				//新增合并的订单
				if(("1").equals(newst) && ("0").equals(oldst)){
					insertMergeOrder(ysid);
				}
				//newst=1,oldst=1的订单:检查订单是否入库，是就删除合并，否则保留
				if(("1").equals(newst) && ("1").equals(oldst)){
					checkMergeOrder(ysid);
				}
				//newst=0,oldst=0的订单在页面端被过滤掉了。
				
				
			}
			
			ts.commit();
		}
		catch(Exception e) {
			e.printStackTrace();
			ts.rollback();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void checkMergeOrder(String ysid){
		
		String where = " ysid = '"+ysid +"' AND deleteFlag='0' " ;
		try {			
			List<B_OrderDetailData> list = new B_OrderDetailDao().Find(where);
			B_OrderDetailData order ;
			if(list.size() > 0 ){
				order = list.get(0);	
			}else{
				return;
			}				
			float orderQty = stringToFloat(order.getQuantity());//订单数
			float stockinQty = stringToFloat(order.getCompletedquantity());//累计入库
			
			if(stockinQty >= orderQty){

				deleteMergeOrder(ysid);////已入库完毕的数据，不再合并任务
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	@SuppressWarnings("unchecked")
	private void checkHideOrder(String ysid){
		
		String where = " ysid = '"+ysid +"' AND deleteFlag='0' " ;
		try {			
			List<B_OrderDetailData> list = new B_OrderDetailDao().Find(where);
			B_OrderDetailData order ;
			if(list.size() > 0 ){
				order = list.get(0);	
			}else{
				return;
			}				
			float orderQty = stringToFloat(order.getQuantity());//订单数
			float stockinQty = stringToFloat(order.getCompletedquantity());//累计入库
			
			if(stockinQty >= orderQty){

				deleteHideOrder(ysid);////已入库完毕的数据，不再隐藏
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	@SuppressWarnings("unchecked")
	private void deleteMergeOrder(String ysid){
		String where = " ysid = '"+ysid +"'AND deleteFlag='0' " ;
		List<B_OrderMergeDetailData> list;
		try {
			list = new B_OrderMergeDetailDao().Find(where);
			if(list.size() > 0 ){
				B_OrderMergeDetailData detail = list.get(0);
				commData = commFiledEdit(Constants.ACCESSTYPE_DEL,
						"OrderDelete",userInfo);
				copyProperties(detail,commData);			
				new B_OrderMergeDetailDao().Store(detail);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	@SuppressWarnings("unchecked")
	private void insertMergeOrder(String ysid){
		String where = " ysid = '"+ysid +"' AND deleteFlag='0' " ;

		try {			
			List<B_OrderDetailData> list = new B_OrderDetailDao().Find(where);
			B_OrderDetailData order ;
			if(list.size() > 0 ){
				order = list.get(0);	
			}else{
				return;
			}
				
			float orderQty = stringToFloat(order.getQuantity());//订单数
			float stockinQty = stringToFloat(order.getCompletedquantity());//累计入库
			if(stockinQty >= orderQty){
				return;//已入库完毕的数据，不再合并任务
			}
			
			B_OrderMergeDetailData merge = new B_OrderMergeDetailData();					
			
			//List<B_OrderMergeDetailData> listm = new B_OrderMergeDetailDao().Find(where);
			
			//if(listm.size() > 0 ){

			//	merge.setYsid(ysid);
			//	merge.setMergeid(order.getMachinemodel());
			//	merge.setMachinemodel(order.getMachinemodel());
			//	merge.setViewflag("T");//显示
				
			//	commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
			//			"订单合并update",userInfo);
			//	copyProperties(merge,commData);
			//	new B_OrderMergeDetailDao().Store(merge);
			//}else{

				merge.setYsid(ysid);
				merge.setMergeid(order.getMachinemodel());
				merge.setMachinemodel(order.getMachinemodel());
				merge.setViewflag("T");//显示
				
				commData = commFiledEdit(Constants.ACCESSTYPE_INS,
						"订单合并insert",userInfo);
				copyProperties(merge,commData);	
				guid = BaseDAO.getGuId();				
				merge.setRecordid(guid);
				new B_OrderMergeDetailDao().Create(merge);
			//}
				
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	@SuppressWarnings("unchecked")
	private  List<B_OrderMergeDetailData> getProduceOrderMerge(String ysid) throws Exception{
		
		String where = " ysid ='"+ysid + "' AND deleteFalg='0' ";
				 		
		return new B_OrderMergeDetailDao().Find(where);
		
	}
	private List<String> setYsidFromSplit(String checked){
		
		List<String> checkedList = new ArrayList<String>();
		if(notEmpty(checked)){
			String[] list = checked.split(";");
			if(list.length > 0){
				for(String ysid:list){

					checkedList.add(ysid);
				}
			}
		}
		return checkedList;
	}
	
	private HashMap<String, Object> getTaskMerge(){
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		
		
		
		return modelMap;
	}
	
	public void produceLineCodeSearchInit() throws Exception{
		
		getCategoryId();//生产线一级分类
	}
	
	private void getCategoryId() throws Exception{
		dataModel.setQueryFileName("/business/order/producequerydefine");
		dataModel.setQueryName("produceLineTopCodeIdList");
		baseQuery = new BaseQuery(request, dataModel);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		model.addAttribute("category",dataModel.getYsViewData());
		
	}
	
	
	@SuppressWarnings("unchecked")
	public void getParentCodeDetail() throws Exception {

		String codeId = request.getParameter("codeId");
		String where = " codeId ='"+codeId+"' AND deleteFlag='0' ";
	
		List<S_ProduceLineCodeData> dbData = 
			(List<S_ProduceLineCodeData>)new S_ProduceLineCodeDao().Find(where);
			
		if(dbData.size() >0){
			//model.addAttribute("parend",dbData.get(0));
			reqModel.setProduceLine(dbData.get(0));
		}
	}
	
	public String getMAXParentCode(String codeId) throws Exception{

		dataModel.setQueryFileName("/business/order/producequerydefine");
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
		S_ProduceLineCodeData reqData = reqModel.getProduceLine();

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
		S_ProduceLineCodeData newDt = new S_ProduceLineCodeData();

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
		
		new S_ProduceLineCodeDao().Create(newDt);
				
		
	}	
	
	public void updateWarehouseCode() throws Exception {
		
		S_ProduceLineCodeData reqData = reqModel.getProduceLine();

		S_ProduceLineCodeData db = new S_ProduceLineCodeDao(reqData).beanData;
		
		copyProperties(db,reqData);
		db.setEffectiveflag("Y");//使用标识
		
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"update",userInfo);
		copyProperties(db,commData);

		new S_ProduceLineCodeDao().Store(db);
				
		
	}	

	public void deleteWarehouseCode() throws Exception {

		String recordId = request.getParameter("recordId");
			
		S_ProduceLineCodeData dt = new S_ProduceLineCodeData();
		dt.setRecordid(recordId);
		dt = new S_ProduceLineCodeDao(dt).beanData;
							
		commData = commFiledEdit(Constants.ACCESSTYPE_DEL,
				"insert",userInfo);
		copyProperties(dt,commData);
				
		new S_ProduceLineCodeDao().Store(dt);
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
	

	public void producePlanSearchInit() throws Exception{

		model.addAttribute("produceLineOption",
				getListOptionFromXml(getProduceLineList()));
		
		model.addAttribute("produceLine",getProduceLineCode());
	}
	
	public ArrayList<ArrayList<String>> getProduceLineList() throws Exception{
		
		dataModel.setQueryFileName("/business/order/producequerydefine");
		dataModel.setQueryName("getProduceLineCode");		
		baseQuery = new BaseQuery(request, dataModel);	
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		return baseQuery.getQueryData();
		
	}
	
	@SuppressWarnings("unchecked")
	public boolean setProduceLineById() throws Exception{

		boolean returnValue = true;
		String YSId = request.getParameter("YSId");
		String produceLine = URLDecoder.decode(request.getParameter("produceLine"),"UTF-8");
		
		try{
			String where = "YSId ='" + YSId +"' AND deleteFlag='0' ";
			List<B_ProducePlanData> list = new B_ProducePlanDao().Find(where);
			if(list.size() > 0){
				B_ProducePlanData plan = list.get(0); 
				plan.setProduceline(produceLine);
				commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
						"update",userInfo);
				copyProperties(plan,commData);	
				
				new B_ProducePlanDao().Store(plan);
				
			}else{
				B_ProducePlanData plan = new B_ProducePlanData();
				plan.setProduceline(produceLine);
				plan.setYsid(YSId);
				
				commData = commFiledEdit(Constants.ACCESSTYPE_INS,
						"insert",userInfo);
				copyProperties(plan,commData);	
				guid = BaseDAO.getGuId();				
				plan.setRecordid(guid);
				
				new B_ProducePlanDao().Create(plan);
			}
		}catch(Exception e){
			returnValue = false;
		}
		
		
		return returnValue;
	}
	
	public HashMap<String, Object> getProduceLineByKey() throws Exception{

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		
		String key = request.getParameter("key").trim().toUpperCase();
		
		dataModel.setQueryFileName("/business/order/producequerydefine");
		dataModel.setQueryName("geProduceLineByKey");	
		baseQuery = new BaseQuery(request, dataModel);	
		userDefinedSearchCase.put("keyword1", key);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		baseQuery.getYsFullData();

		modelMap.put("data", dataModel.getYsViewData());		
		modelMap.put("retValue", "success");			
		
		return modelMap;
	}
}
