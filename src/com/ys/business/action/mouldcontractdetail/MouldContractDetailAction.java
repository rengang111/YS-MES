package com.ys.business.action.mouldcontractdetail;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.ys.system.action.common.BaseAction;
import com.ys.system.action.model.TestModel;
import com.ys.system.action.model.login.UserInfo;
import com.ys.business.action.model.common.ListOption;
import com.ys.business.action.model.externalsample.ExternalSampleModel;
import com.ys.business.action.model.mouldcontract.MouldContractModel;
import com.ys.business.action.model.mouldcontractdetail.MouldContractDetailModel;
import com.ys.business.action.model.processcontrol.ProcessControlModel;
import com.ys.system.common.BusinessConstants;
import com.ys.util.DicUtil;
import com.ys.util.basequery.BaseQuery;

import net.sf.json.JSONArray;

import com.ys.business.service.mouldcontractdetail.MouldContractDetailService;

@Controller
@RequestMapping("/business")
public class MouldContractDetailAction extends BaseAction {
	
	@Autowired
	MouldContractDetailService mouldContractService;
	
	@RequestMapping(value="mouldcontractdetail")
	public String execute(@RequestBody String data, @ModelAttribute("dataModels")MouldContractDetailModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		String type = request.getParameter("methodtype");
		String rtnUrl = "";
		HashMap<String, Object> dataMap = null;
		MouldContractDetailModel viewModel = null;
		
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
				rtnUrl = doInit(model, session, request, response);
				break;				
			case "getMouldDetailList":
				dataMap = getMouldDetailList(data, session, request, response);
				printOutJsonObj(response, dataMap);
				return null;
			case "productModelIdSearch"://供应商查询
				dataMap = doProductModelIdSearch(data, request);
				printOutJsonObj(response, dataMap);
				return null;
		}
		
		return rtnUrl;
	}	
	
	public String doInit(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		MouldContractDetailModel dataModel = new MouldContractDetailModel();
		String key = request.getParameter("key");
		try {
			dataModel = mouldContractService.doInit();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("发生错误，请联系系统管理员");
		}
		model.addAttribute("DisplayData", dataModel);
		return "/business/mouldcontractdetail/mouldcontractdetailmain";
	}	
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> doProductModelIdSearch(@RequestBody String data, HttpServletRequest request){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		//ArrayList<HashMap<String, String>> dbData = new ArrayList<HashMap<String, String>>();
		
		try {
			dataMap = mouldContractService.doProductModelIdSearch(request, data);
			
			//dbData = (ArrayList<HashMap<String, String>>)dataMap.get("data");

		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			//dataMap.put(INFO, ERRMSG);
		}
		
		return dataMap;
	}
	
	public HashMap<String, Object> getMouldDetailList(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		
		try {
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			dataMap = mouldContractService.doGetMouldDetailList(request, data, userInfo);
			ArrayList<HashMap<String, String>> dbData = (ArrayList<HashMap<String, String>>)dataMap.get("data");
			if (dbData.size() == 0) {
				dataMap.put(INFO, NODATAMSG);
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataMap.put(INFO, ERRMSG);
		}
		
		return dataMap;
	}	
	
}
