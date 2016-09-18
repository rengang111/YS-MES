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
		try {
			Logger logger1 = Logger.getLogger(test.class);
			// 记录debug级别的信息  
	        logger1.debug("This is logger1 debug message.");  
	        // 记录info级别的信息  
	        logger1.info("This is logger1 info message.");  
	        // 记录error级别的信息  
	        logger1.error("This is logger1 error message."); 
			
			
			// 记录debug级别的信息  
	        logger.debug("This is debug message.");  
	        // 记录info级别的信息  
	        logger.info("This is info message.");  
	        // 记录error级别的信息  
	        logger.error("This is error message.");  			
			
			/*
			UserInfo userInfo = new UserInfo();
			userInfo.setUserId("369");
			userInfo.setUserType(Constants.USER_SA);
			userInfo.setUnitId("003");
			userInfo.setUserName("测试369");
			userInfo.setUnitName("单位003");
			userInfo.setDeptGuid("115");
			userInfo.sethtmlFile("big2.jpg");
			session.setAttribute(Constants.SESSION_USERINFO, userInfo);
			*/
	        /*
			BaseModel baseModel = new BaseModel();
			baseModel.setQueryFileName("/test/queryDefined");
			baseModel.setQueryName("query1");
			BaseQuery baseQuery = new BaseQuery(request, baseModel);
			System.out.println(baseQuery.getSql());
			*/
			//testService1.test();
			//testService.test();
			//testService.test();
			//blob存取
			/*
			S_USERDao userDao = new S_USERDao();
			S_USERData userData = new S_USERData();

			userData.setUserid("000");
			userData = (S_USERData)userDao.FindByPrimaryKey(userData);
			//userData.setJianpin("sadmin123");
			
			byte[] inBytes = userData.gethtmlFile().getBytes(1, 5012);
			
			FileInputStream fis = null; 
			String fileName = "E:/work/workSpace/CWKJ/WebContent/img/headhtmlFile/Idea.jpg";
			File file = new File(fileName);
			fis = new FileInputStream(file);
			userData.sethtmlFileStream(fis);
			userDao.fileLength = file.length();
			userDao.beanData = userData;
			
			userDao.Store();
			*/
			
			/*
			String path = "/img/headhtmlFile/test.jpg";
			path = request.getSession().getServletContext().getRealPath(path);
			
			ArrayList<ArrayList<String>> dbResult = BaseDAO.execSQL("select HEX(htmlFile) from s_user");
			String outStr = dbResult.get(0).get(0);
			//String inStr = String.valueOf(inBytes);
			byte[] bytes = hexStringToByte(outStr);

			Blob blob = new SerialBlob(bytes);
			InputStream in = blob.getBinaryStream();
			
			long nLen = dbResult.get(0).get(0).length();
            int nSize = (int) nLen;
            byte[] data = new byte[nSize];
            in.read(data);			
			FileOutputStream fout = new FileOutputStream(path);
			fout.write(data);
			fout.flush();
			fout.close();
			in.close();
			/*
			
			//menu，组织机构
			/*
			ArrayList<MenuInfo> menuChain = CommonMenu.getInitMenu("001", false);
			
			ArrayList<MenuInfo> sysMenu = CommonMenu.launchMenu("001", "008", false);
			
			ArrayList<DeptInfo> sysDept = CommonDept.getInitDept("001", "", false);
			
			sysDept = CommonDept.getInitDept("001", "001", false);
			
			String filterSql = CommonDept.getDeptFilterSubSql("001", "", false);
			filterSql = CommonDept.getDeptFilterSubSql("001", "001", false);
			
			sysDept = CommonDept.launchDept("001", "", "001", false);
			sysDept = CommonDept.launchDept("001", "001", "004", true);
			*/
			//System.out.println(BaseQuery.getUuId());

			//字典类
			/*
			System.out.println(DicUtil.getCodeValue("un001001001"));
			System.out.println(DicUtil.getGroupValue("un"));
			System.out.println(DicUtil.getSameParentGroupValue("un", "001", 2));
			*/
			//加密/解密
			/*
			//���������
			String data = dataModel.getFormId();
			
			String desResult = BaseSub.DesEncryptData(data);
			System.out.println("���ܺ�" + desResult);

			//ֱ�ӽ��������ݽ���
			try {
				String decryResult = BaseSub.DesDeEncryptData(desResult);
				System.out.println("���ܺ�" + decryResult);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			*/
			
			//db操作
			/*
			String sql = "SELECT id ,name     FROM tablea   WHERE name  like '%��%'       ORDER BY id";
			System.out.println(BaseDAO.execSQL(sql));
			*/
	        /*
			dataModel.setQueryFileName("/test/mysql");
			dataModel.setQueryName("mysql");
			//dataModel.setUserDefinedWhere("ID > 5");
			//dataModel.setMenuId(menuId);
			BaseQuery baseQuery1 = new BaseQuery(request, dataModel);
			baseQuery1.setNaviStartEndPage(false);
			baseQuery1.getQueryData();
			model.addAttribute("DisplayData", dataModel);
			*/
			/*
			//db操作
			/*
			Connection connection = BaseDao.getConnection();
			Connection connection1 = BaseDao.getConnection("mysql");
			BaseDao.execSQL("select * from tableA");
			connection.close();
			connection1.close();
			*/	
			
			S_DICDao dicDao = new S_DICDao();
			S_DICData dicData = new S_DICData();

			dicData.setDicid("034");
			dicData.setDictypeid("A2");
			//dicData = (S_DICData)dicDao.FindByPrimaryKey(dicData);
			/*
			dicData.setJianpin("sadmin123");
			dicDao.Store(dicData);
			
			dicData.setDicid("034");
			dicData.setDictypeid("A2");
			dicData = (S_DICData)dicDao.FindByPrimaryKey(dicData);
			dicData.setJianpin("sadmin124");
			dicDao.Store(dicData);
			*/
	        
	        testService.test(dataModel.getMenuId(), dataModel.getFormDisp());
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}


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
			testService.test1(dataModel.getMenuId(), dataModel.getFormDisp());
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
