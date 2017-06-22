package com.ys.business.action.projectmanage;

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
import com.ys.system.action.common.BaseAction;
import com.ys.system.action.model.login.UserInfo;
import com.ys.business.action.model.makedocument.MakeDocumentModel;
import com.ys.business.action.model.processcontrol.ProcessControlModel;
import com.ys.system.common.BusinessConstants;
import com.ys.business.service.projectmanage.MakeDocumentService;
import com.ys.business.service.projectmanage.ProcessControlService;

@Controller
@RequestMapping("/business")
public class MakeDocumentAction extends BaseAction {
	
	@Autowired
	MakeDocumentService makeDocumentService;
	
	@Autowired
	ProcessControlService processControlService;
	
	
	@RequestMapping(value="makedocument")
	public String execute(@RequestBody String data, @ModelAttribute("dataModels")MakeDocumentModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		String type = request.getParameter("methodtype");
		String rtnUrl = "";
		HashMap<String, Object> dataMap = null;
		MakeDocumentModel viewModel = null;
		
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
				rtnUrl = "/business/makedocument/makedocumentmain";
				break;
			case "search":
				dataMap = doSearch(data, session, request, response);
				printOutJsonObj(response, dataMap);
				return null;
			case "addinit":
			case "updateinit":
				rtnUrl = doGetProjectBaseInfo(model, session, request, response);
				break;
			case "delete":
				viewModel = doDelete(data, session, request, response);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;
			case "deleteDetail":
				viewModel = doDeleteDetail(data, session, request, response);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;
			case "getBaseTechDoc":
				dataMap = getBaseTechDocList(data, session, request, response);
				printOutJsonObj(response, dataMap);
				return null;
			case "getWorkingFileList":
				dataMap = getWorkingFileList(data, session, request, response);
				printOutJsonObj(response, dataMap);
				return null;
			case "addbasetechdocinit":		
			case "updatebasetechdocinit":
				rtnUrl = doUpdateBaseTechDocInit(model, session, request, response);
				break;				
			case "deletebasetechdoc":
				viewModel = doDeleteBaseTechDoc(data, session, request, response);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;				
			case "updatebasetechdoc":
				viewModel = doUpdateBaseTechDoc(data, session, request, response);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;	
			case "addworkingfileinit":
			case "updateworkingfileinit":
				rtnUrl = doUpdateWorkingFileInit(model, session, request, response);
				break;	
			case "deleteworkingfile":
				viewModel = doDeleteWorkingFile(data, session, request, response);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;	
			case "updateworkingfile":
				viewModel = doUpdateWorkingFile(data, session, request, response);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;
			case "showtab":
				rtnUrl = doShowTab(model, session, request, response);
				break;
			case "editfoldername":
				rtnUrl = doUpdateFolderEditInit(model, session, request, response);
				break;
			case "updatefolder":
				viewModel = doUpdateFolder(data, session, request, response);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;
			case "deletefolder":
				viewModel = doDeleteFolder(data, session, request, response);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;
			case "openfilebrowser":
				super.openFileBrowser(request, session, model);
				return null;
		}
		
		return rtnUrl;
	}	
	
	public HashMap<String, Object> doSearch(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		
		try {
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			dataMap = processControlService.doSearch(request, data, userInfo);
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
	
	public String doGetProjectBaseInfo(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		ProcessControlModel dataModel = new ProcessControlModel();
		String key = request.getParameter("key");
		try {
			dataModel = processControlService.getProjectBaseInfo(request, key);
			dataModel = makeDocumentService.getFolderNames(dataModel, request, key);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("发生错误，请联系系统管理员");
		}
		model.addAttribute("DisplayData", dataModel);
		
		return "/business/makedocument/makedocumentedit";
	}		
	
	public HashMap<String, Object> getBaseTechDocList(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		
		try {
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			dataMap = makeDocumentService.doGetBaseTechDocList(request, data, userInfo);
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
	
	public HashMap<String, Object> getWorkingFileList(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		
		try {
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			dataMap = makeDocumentService.doGetWorkingFileList(request, data, userInfo);
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
	
	public String doUpdateBaseTechDocInit(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		MakeDocumentModel dataModel = new MakeDocumentModel();
		String projectId = request.getParameter("projectId");
		String key = request.getParameter("key");
		String type = request.getParameter("type");

		try {
			dataModel = makeDocumentService.doUpdateBaseTechDocInit(request, projectId, key, type);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("发生错误，请联系系统管理员");
		}
		model.addAttribute("DisplayData", dataModel);
		
		return "/business/makedocument/makedocumentbasetechdocedit";
	}	
	
	public String doUpdateWorkingFileInit(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		MakeDocumentModel dataModel = new MakeDocumentModel();
		String projectId = request.getParameter("projectId");
		String key = request.getParameter("key");

		try {
			dataModel = makeDocumentService.doUpdateWorkingFileInit(request, projectId, key);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("发生错误，请联系系统管理员");
		}
		model.addAttribute("DisplayData", dataModel);
		
		return "/business/makedocument/makedocumentworkingfileedit";
	}	
	
	
	public MakeDocumentModel doUpdateBaseTechDoc(String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		MakeDocumentModel model = new MakeDocumentModel();
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		model = makeDocumentService.doUpdateBaseTechDoc(request, data, userInfo);
		
		return model;
	}		
	
	public MakeDocumentModel doUpdateWorkingFile(String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		MakeDocumentModel model = new MakeDocumentModel();
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		model = makeDocumentService.doUpdateWorkingFile(request, data, userInfo);
		
		return model;
	}	
	
	public MakeDocumentModel doDeleteBaseTechDoc(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		MakeDocumentModel model = new MakeDocumentModel();
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		model = makeDocumentService.doDeleteBaseTechDoc(request, data, userInfo);

		return model;
	}
	
	public MakeDocumentModel doDeleteWorkingFile(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		MakeDocumentModel model = new MakeDocumentModel();
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		model = makeDocumentService.doDeleteWorkingFile(request, data, userInfo);

		return model;
	}	
	
	public MakeDocumentModel doDelete(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		MakeDocumentModel model = new MakeDocumentModel();
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		model = makeDocumentService.doDelete(request, data, userInfo);

		return model;
	}

	public MakeDocumentModel doDeleteDetail(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		MakeDocumentModel model = new MakeDocumentModel();
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		model = makeDocumentService.doDelete(request, data, userInfo);

		return model;
	}
	
	public String doShowTab(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		MakeDocumentModel dataModel = new MakeDocumentModel();
		String title = request.getParameter("title");
		String projectId = request.getParameter("projectId");
		
		dataModel = makeDocumentService.getFileNames(request, dataModel, projectId, title);
		
		dataModel.setFolderName(title);
		dataModel.setProjectId(projectId);
		model.addAttribute("DisplayData", dataModel);
		
		return "/common/album/multialbum";
	}	
	
	public String doUpdateFolderEditInit(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		MakeDocumentModel dataModel = new MakeDocumentModel();
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		try {
			dataModel = makeDocumentService.doUpdateFolderEditInit(request, userInfo);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("发生错误，请联系系统管理员");
		}
		model.addAttribute("DisplayData", dataModel);
		
		return "/business/makedocument/tabedit";
	}
	
	public MakeDocumentModel doUpdateFolder(String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		MakeDocumentModel model = new MakeDocumentModel();
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		model = makeDocumentService.doUpdateFolder(request, data, userInfo);
		
		return model;
	}	
	
	public MakeDocumentModel doDeleteFolder(String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		MakeDocumentModel model = new MakeDocumentModel();
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		model = makeDocumentService.doDeleteFolder(request, data, userInfo);
		
		return model;
	}	
}
