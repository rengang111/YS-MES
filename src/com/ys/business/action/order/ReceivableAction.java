package com.ys.business.action.order;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ys.business.action.model.order.PaymentModel;
import com.ys.business.action.model.order.ReceivableModel;
import com.ys.business.service.order.ReceivableService;
import com.ys.system.action.common.BaseAction;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.util.basequery.common.Constants;
/**
 * 应收款管理
 * @author rengang
 *
 */
@Controller
@RequestMapping("/business")
public class ReceivableAction extends BaseAction {
	
	@Autowired
	ReceivableService service;
	@Autowired HttpServletRequest request;
	
	UserInfo userInfo = new UserInfo();
	ReceivableModel reqModel = new ReceivableModel();
	HashMap<String, Object> modelMap = new HashMap<String, Object>();
	Model model;
	HttpServletResponse response;
	HttpSession session;
	
	@RequestMapping(value="receivable")
	public String execute(@RequestBody String data, 
			@ModelAttribute("formModel")ReceivableModel dataModel, 
			BindingResult result, 
			Model model, 
			HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String type = request.getParameter("methodtype");
		String rtnUrl = null;
		HashMap<String, Object> dataMap = null;
		
		this.userInfo = (UserInfo)session.getAttribute(
				BusinessConstants.SESSION_USERINFO);
		
		this.service = new 	ReceivableService(model,request,
				 response,session,dataModel,userInfo);
		this.reqModel = dataModel;
		this.model = model;
		this.response = response;
		this.session = session;
		
		if (type == null) {
			type = "";
		} else {
			int q = type.indexOf("?");
			if (q >= 0) {
				type = type.substring(0, q);
			}
		}
		
		switch(type) {
			case "":
			case "init":
				doInit();
				rtnUrl = "/business/finance/receivablemain";
				break;
			case "search":
				dataMap = doSearch(data);
				printOutJsonObj(response, dataMap);
				return null;
			case "getRequisitionDetail"://领料单详情
				//dataMap = getRequisitionDetail();
				printOutJsonObj(response, dataMap);
				return null;
			case "addInit"://单项收款初始化
				doAddInit();
				rtnUrl = "/business/finance/receivableadd";
				break;
			case "ordersAddInit"://合并收款初始化
				ordersAddInit();
				rtnUrl = "/business/finance/receivableadd";
				break;
			case "addContinueInit"://继续收款初始化
				doAddContinueInit();
				rtnUrl = "/business/finance/receivableadd";
				break;
			case "receivableInsert"://收款保存
				doReceivableInsert();
				rtnUrl = "/business/finance/receivableview";
				break;
			case "getReceivableDetail"://收款明细
				dataMap = getReceivableDetailById();
				printOutJsonObj(response, dataMap);
				break;
			case "receivableDetailInit"://收款明细页面初始化
				receivableDetailInit();
				rtnUrl = "/business/finance/receivableview";
				break;
			case "receivableUpdateInit"://收款编辑
				receivableUpdateInit();
				rtnUrl = "/business/finance/receivableedit";
				break;
			case "getProductPhoto"://显示出库单附件
				dataMap = getProductPhoto();
				printOutJsonObj(response, dataMap);
				break;
			case "productPhotoDelete"://删除出库单附件
				dataMap = deletePhoto("product","productFileList","productFileCount");
				printOutJsonObj(response, dataMap);
				break;
			case "editInit"://更新收款初始化
				doEditInit();
				rtnUrl = "/business/finance/receivableedit";
				break;
			case "receivableUpdate"://收款更新
				doReceivableUpdate();
				rtnUrl = "/business/finance/receivableview";
				break;
			case "receivableDelete"://收款删除
				dataMap = doReceivableDelete();
				printOutJsonObj(response, dataMap);
				//rtnUrl = "/business/finance/receivableview";
				break;
			case "getOrderDetail"://订单详情
				dataMap = doGetOrderDetail();
				printOutJsonObj(response, dataMap);
				break;
			case "getOrderDetailByYsids"://订单详情:新建
				dataMap = doGetOrderDetailByYsids();
				printOutJsonObj(response, dataMap);
				break;
		}
		
		return rtnUrl;
	}	
	
	public HashMap<String, Object> getProductPhoto(){	
		
		try {
			modelMap = service.getProductPhoto();
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			modelMap.put(INFO, ERRMSG);
		}
		
		return modelMap;
	}
	
	public HashMap<String, Object> deletePhoto(
			String folderName,String fileList,String fileCount){	
		
		try {
			modelMap = service.deletePhotoAndReload(folderName,fileList,fileCount);
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			modelMap.put(INFO, ERRMSG);
		}
		
		return modelMap;
	}
	
	public void doInit(){	
		
		String searchType = (String) session.getAttribute("searchType");
		if(searchType == null || ("").equals(searchType))
			searchType = "010";//设置默认值：待收款
		model.addAttribute("searchType",searchType);

	}	

	public void doEditInit(){	
		
		try {
			model.addAttribute("userName",userInfo.getUserName());
			service.receivableEditInit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}	
	
	public void doAddInit(){	
		
		try {
			model.addAttribute("userName",userInfo.getUserName());
			service.receivableAddInit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}	
	
	public void ordersAddInit(){	
		
		try {
			model.addAttribute("userName",userInfo.getUserName());
			service.receivableOrdersAddInit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}	

	public void doAddContinueInit(){	
		
		try {
			model.addAttribute("userName",userInfo.getUserName());
			service.receivableAddContinueInit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}	
	
	public void doReceivableInsert(){	
		
		try {
			service.receivableInsertAndView();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}	

	public void doReceivableUpdate(){	
		
		try {
			service.receivableUpdateAndView();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}	
	

	public HashMap<String, Object> doReceivableDelete(){	
		
		HashMap<String, Object> dataMap = new HashMap<>();
		try {
			service.receivableDelete();
			dataMap.put(INFO, NODATAMSG);
		} catch (Exception e) {
			dataMap.put(INFO, ERRMSG);
			e.printStackTrace();
		}
		
		return dataMap;
	}
	
	public HashMap<String, Object> doGetOrderDetail() throws Exception{	
		
		return service.getOrderDetail();
		
	}	
	
	public HashMap<String, Object> doGetOrderDetailByYsids() throws Exception{	
		
		return service.getOrderDetailByYsids();
		
	}	


	public HashMap<String, Object> getReceivableDetailById() throws Exception{	

		String receivableId = request.getParameter("receivableId");
		return service.getReceivableDetail(receivableId,"");

	}	
	
	public void receivableDetailInit(){	
		
		try {

			String receivableId = request.getParameter("receivableId");
			model.addAttribute("receivableId",receivableId);
			
			//service.receivableDetailViewInit(YSId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}	
	

	public void receivableUpdateInit(){	
		
		try {

			//service.getRecivableDetail(YSId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}	
		
	private HashMap<String, Object> uploadPhoto(
			MultipartFile[] headPhotoFile,
			String folderName,String fileList,String fileCount) {
		
		HashMap<String, Object> map = null;
		
		try {
			 map = service.uploadPhotoAndReload(headPhotoFile,folderName,fileList,fileCount);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return map;
	}
	
	//收款单上传
	@RequestMapping(value="receivabelUpload")
	public String doInit(
			@RequestParam(value = "photoFile", required = false) MultipartFile[] headPhotoFile,
			@RequestBody String data,
			@ModelAttribute("formModel")ReceivableModel dataModel,
			BindingResult result, Model model, HttpSession session, 
			HttpServletRequest request, HttpServletResponse response){

		this.userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		this.service = new ReceivableService(model,request,response,session,dataModel,userInfo);;
		this.reqModel = dataModel;
		this.model = model;
		this.response = response;
		this.session = session;
		HashMap<String, Object> dataMap = null;

		String type = request.getParameter("methodtype");
		
		switch(type) {
		case "":
			break;
		case "uploadPhoto":
			dataMap = uploadPhoto(headPhotoFile,"product","productFileList","productFileCount");
			printOutJsonObj(response, dataMap);
			break;
	
		}
		
		
		return null;
	}
		
	
	@SuppressWarnings({ "unchecked" })
	public HashMap<String, Object> doSearch(@RequestBody String data){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		//优先执行查询按钮事件,清空session中的查询条件
		String sessionFlag = request.getParameter("sessionFlag");
		if(("false").equals(sessionFlag)){
			session.removeAttribute(Constants.FORM_RECEIVABLE+Constants.FORM_KEYWORD1);
			session.removeAttribute(Constants.FORM_RECEIVABLE+Constants.FORM_KEYWORD2);			
		}
		
		try {
			dataMap = service.doSearch(data,"after");
			
			ArrayList<HashMap<String, String>> dbData = 
					(ArrayList<HashMap<String, String>>)dataMap.get("data");
			if (dbData.size() == 0) {
				dataMap.put(INFO, NODATAMSG);
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataMap.put(INFO, ERRMSG);
		}
		

		String searchType = request.getParameter("searchType");
		session.setAttribute("searchType", searchType);
		
		return dataMap;
	}
	
	
}
