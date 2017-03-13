package com.ys.system.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.HtmlUtils;

import com.ys.system.action.common.BaseAction;
import com.ys.system.action.model.TestModel;
import com.ys.system.db.dao.S_DICDao;
import com.ys.system.db.data.S_DICData;
import com.ys.system.service.test.TestService;
import com.ys.system.service.test.TestService1;

@Controller
@RequestMapping("/")
public class test extends BaseAction {
	@Autowired
	private TestService testService; 	
	
	@Autowired
	private TestService1 testService1; 	
	
	
	@RequestMapping(value="test123")
	public String doTest(@RequestBody String data, @ModelAttribute("dataModels")TestModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

	        testService.getDB();
	        



		//return "/mainframe/mainframe";
		
		//System.out.println(data);
		try {
			String type=request.getParameter("type");
			if (type != null) {
				response.setContentType("application/json; charset=UTF-8");
				response.getWriter().print("123");
				return null;
			}

			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		//return "/mainframe/mainframe";
		return "/ysdemo/main";
		
	}
	
	@RequestMapping(value="test124")
	public String doTest1(@RequestBody String data, @ModelAttribute("dataModels")TestModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		try {
			//testService.test1(dataModel.getMenuId(), dataModel.getFormDisp());
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return "/ysdemo/main";
	}
	
	@RequestMapping(value="showck")
	public String showCkEditor(@RequestBody String data, @ModelAttribute("dataModels")TestModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		return "/ckeditor/myckeditor";
	}
	
	@RequestMapping(value="saveck",produces="text/HTML")  
	public String saveCkEditor(@RequestBody String data, @ModelAttribute("dataModels")TestModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		saveHtml(request, "test.html", HtmlUtils.htmlUnescape(data));
		return "/ckeditor/myckeditor";
		
	}
	
	@RequestMapping(value="showfinder",produces="text/HTML")  
	public String showFinder(@RequestBody String data, @ModelAttribute("dataModels")TestModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		//saveHtml(request, "test.html", HtmlUtils.htmlUnescape(data));
		//return "/ckeditor/myckeditor";
		request.setAttribute("basepath", "321");
		
		return "/test/testCKFinder";
	}	
	
	@RequestMapping(value="uploadckeditorimagefile")
	public void uploadCkeditorImageFile(HttpServletRequest request, HttpServletResponse response) {

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
	    MultipartFile headPhotoFile = multipartRequest.getFile("upload");
	    
	    //TODO
	    String fileName = "test.jpg";
	    
	    try {
	    	String realPath = request.getSession().getServletContext().getRealPath("img");
			FileUtils.copyInputStreamToFile(headPhotoFile.getInputStream(), new File(realPath, fileName));
	     
	        response.setCharacterEncoding("GBK");  
	        PrintWriter out = response.getWriter();  
		    String callback = request.getParameter("CKEditorFuncNum"); 
	
		    out.println("<script>");
		    //out.println("<body onload=\"<script>alert('ok');</script>\"");
		    out.println("window.parent.CKEDITOR.tools.callFunction("+ callback + ",'" +"img/"+ fileName + "','');"); 
	
		    //out.println("alert('ok');");
		    out.println("</script>");
		    out.flush();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());

		}
	    
	}		
	
	@RequestMapping(value="ys123")
	public String doYsTest(@RequestBody String data, @ModelAttribute("dataModels")TestModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){ 
		System.out.println(data);
		return "lxf521jyy";
		
	}
	
	private static byte[] hexStringToByte(String hex) {
		   int len = (hex.length() / 2);
		   byte[] result = new byte[len];
		   char[] achar = hex.toCharArray();
		   for (int i = 0; i < len; i++) {
		    int pos = i * 2;
		    result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
		   }
		   return result;
		  }
		  
	 private static int toByte(char c) {
	    byte b = (byte) "0123456789ABCDEF".indexOf(c);
	    return b;
	 }
	 
	private String saveHtml(HttpServletRequest request, String htmlFileName, String data) {
		
		String rtnValue = "";
		try {
			String realPath = request.getSession().getServletContext().getRealPath("WEB-INF");

			String htmlFilePath = realPath + File.separator + htmlFileName;
			File htmlFileFile = new File(htmlFilePath);
			
			data = "<link rel='stylesheet' type='text/css' href='../contents.css'>" + "\r\n" + data;
			
			if (!htmlFileFile.exists()) {		
				FileOutputStream fout = new FileOutputStream(htmlFilePath);
				fout.write(data.getBytes());
				fout.flush();
				fout.close();
			}
			rtnValue = "ok";
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return rtnValue;
	}
	

}
