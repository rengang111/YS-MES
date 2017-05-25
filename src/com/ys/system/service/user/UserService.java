package com.ys.system.service.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ys.business.action.model.common.ListOption;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.action.model.user.UserModel;
import com.ys.system.common.BusinessConstants;
import com.ys.system.db.dao.S_DEPTDao;
import com.ys.system.db.dao.S_USERDao;
import com.ys.system.db.data.S_DEPTData;
import com.ys.system.db.data.S_USERData;
import com.ys.system.ejb.DbUpdateEjb;
import com.ys.system.service.common.BaseService;
import com.ys.util.CalendarUtil;
import com.ys.util.DesUtil;
import com.ys.util.DicUtil;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basequery.BaseQuery;
import com.ys.util.basequery.common.BaseModel;

import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;
import javax.sql.rowset.serial.SerialBlob;

@Service
public class UserService extends BaseService {
 
	public HashMap<String, Object> doSearch(HttpServletRequest request, String data, UserInfo userInfo) throws Exception {

		int iStart = 0;
		int iEnd =0;
		String sEcho = "";
		String start = "";
		String length = "";
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		UserModel dataModel = new UserModel();
		
		sEcho = getJsonData(data, "sEcho");	
		start = getJsonData(data, "iDisplayStart");		
		if (start != null && !start.equals("")){
			iStart = Integer.parseInt(start);			
		}
		
		length = getJsonData(data, "iDisplayLength");
		if (length != null && !length.equals("")){			
			iEnd = iStart + Integer.parseInt(length);			
		}		
		
		dataModel.setQueryFileName("/user/userquerydefine");
		dataModel.setQueryName("userquerydefine_search");
		
		String key1 = getJsonData(data, "userIdName");
		String key2 = getJsonData(data, "unitIdName");
		String key3 = getJsonData(data, "dutiesIdName");
		String key4 = getJsonData(data, "userUnitId");
		BaseQuery baseQuery = new BaseQuery(request, dataModel);

		userDefinedSearchCase.put("userIdName", key1);
		userDefinedSearchCase.put("unitIdName", key2);
		userDefinedSearchCase.put("dutiesIdName", key3);
		userDefinedSearchCase.put("userUnitId", key4);
		userDefinedSearchCase.put("userUnitId", userInfo.getUnitId());
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		ArrayList<HashMap<String, String>> userDataList = baseQuery.getYsQueryData(iStart, iEnd);	
		
		if ( iEnd > dataModel.getYsViewData().size()){
			iEnd = dataModel.getYsViewData().size();
		}
		
		for(HashMap<String, String> rowData:userDataList) {
			ArrayList<String> stringPhotoData = new ArrayList<String>();
			stringPhotoData.add(rowData.get("UserID"));
			stringPhotoData.add(rowData.get("ModifyTime"));
			stringPhotoData.add(rowData.get("photo"));
			rowData.put("photo", createPhoto(request, null, stringPhotoData));
		}
		
		modelMap.put("sEcho", sEcho); 
		
		modelMap.put("recordsTotal", dataModel.getRecordCount()); 
		
		modelMap.put("recordsFiltered", dataModel.getRecordCount());
		
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
	}

	public UserModel getDetail(HttpServletRequest request) throws Exception {
		String userId = request.getParameter("userId");
		UserModel userModel = new UserModel();
		
		S_USERDao dao = new S_USERDao();
		S_USERData data = new S_USERData();
		data.setUserid(userId);
		data = (S_USERData)dao.FindByPrimaryKey(data);
		
		userModel.setuserData(data);
		userModel.setUnitId(data.getUnitid());
		userModel.setUnitName(DicUtil.getCodeValue(DicUtil.UNIT + data.getUnitid()));
		userModel.setUserId(data.getUserid());
		userModel.setDutyList(DicUtil.getGroupValue(DicUtil.DUTIES));
		userModel.setSexList(DicUtil.getGroupValue(DicUtil.SEX));
		
		String addressCodeArray[] = data.getAddresscode().split("-");
		String address = "";
		for(String addressCode:addressCodeArray) {
			address += DicUtil.getCodeValue(DicUtil.ADDRESS + addressCode);
		}
		userModel.setAddress(address);
		
		userModel.setPhoto(createPhoto(request, data, null));
		
		return userModel;
	
	}
	
	public UserModel doUpdate(HttpServletRequest request, String formData, UserInfo userInfo, boolean isAdd) {
		S_USERDao dao = new S_USERDao();
		S_USERData data = new S_USERData();
		UserModel model = new UserModel();
		
		try {
			String userid = getJsonData(formData, "userid");
			String loginid = getJsonData(formData, "loginid");
			String loginname = getJsonData(formData, "loginname");
			String jianpin = getJsonData(formData, "jianpin");
			String unitid = getJsonData(formData, "unitId");
			String loginpwd = getJsonData(formData, "loginpwd");
			String sex = getJsonData(formData, "sex");
			String duty = getJsonData(formData, "duty");
			String addresscode = getJsonData(formData, "addresscode");
			String addressuser = getJsonData(formData, "addressuser");
			String postcode = getJsonData(formData, "postcode");
			String telphone = getJsonData(formData, "telphone");
			String handphone = getJsonData(formData, "handphone");
			String email = getJsonData(formData, "email");
			String pwdquestion1 = getJsonData(formData, "pwdquestion1");
			String pwdquestion2 = getJsonData(formData, "pwdquestion2");
			String lockflag = getJsonData(formData, "lockflag");
			String enableflag = getJsonData(formData, "enableflag");
			String enablestarttime = getJsonData(formData, "enablestarttime");
			String enableendtime = getJsonData(formData, "enableendtime");
			String workid = getJsonData(formData, "workid");
			String sortno = getJsonData(formData, "sortno");
			
			if (isAdd) {
				if (userid == null || userid.equals("")) {
					userid = BaseDAO.getGuId();
				}
				data.setUserid(userid);
			} else {
				data.setUserid(userid);
				data = (S_USERData)dao.FindByPrimaryKey(data);
			}
			data.setLoginid(loginid);
			data.setLoginname(loginname);
			data.setJianpin(jianpin);
			data.setLoginpwd(loginpwd);
			data.setSex(sex);
			data.setDuty(duty);
			data.setAddresscode(addresscode);
			data.setAddressuser(addressuser);
			data.setPostcode(postcode);
			data.setTelphone(telphone);
			data.setHandphone(handphone);
			data.setEmail(email);
			data.setPwdquestion1(pwdquestion1);
			data.setPwdquestion2(pwdquestion2);
			data.setLockflag(lockflag);
			if (enableflag.equals("")) {
				data.setEnableendtime(null);
			} else {
				data.setEnableflag(enableflag);
			}
			data.setEnablestarttime(enablestarttime);
			if (enableendtime.equals("")) {
				enableendtime = "2075-12-31 23:59:59";
			}
			data.setEnableendtime(enableendtime);
			data.setWorkid(workid);
			data.setLoginpwd(DesUtil.DesEncryptData(loginpwd));
			if (sortno != null && !sortno.equals("")) {
				data.setSortno(Integer.parseInt(sortno));
			} else {
				data.setSortno(0);
			}
		
			data = setDeptGuid(request, formData, data);
			data = setPhoto(request, formData, data);
			data.setUnitid(unitid);
			data = setFlag(data);
			data = updateModifyInfo(data, userInfo);
		
			if (isAdd) {
				dao.Create(data);
			} else {
				dao.Store(data);
			}
			
			model.setEndInfoMap(BaseService.NORMAL, "", userid);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(BaseService.SYSTEMERROR, "", "");
		}
		finally {
			if (!getJsonData(formData, "photo").equals("")) {
				try {
					data.getPhotoStream().close();
				}
				catch(Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}
		movePhoto(request, formData, data.getUserid());
		
		return model;
	}
	
	public UserModel doDelete(HttpServletRequest request, String formData, UserInfo userInfo) {
		

		DbUpdateEjb bean = new DbUpdateEjb();
		UserModel model = new UserModel();
		
		try {
			bean.executeUserDelete(formData, userInfo);
			model.setEndInfoMap(BaseService.NORMAL, "", "");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(BaseService.SYSTEMERROR, "", "");
		}
		
		return model;

	}
	
	public UserModel doLock(String formData, UserInfo userInfo, int isLock) {

		DbUpdateEjb bean = new DbUpdateEjb();
		UserModel model = new UserModel();
		
		try {
			bean.executeUserLock(formData, userInfo, String.valueOf(isLock));
			model.setEndInfoMap(BaseService.NORMAL, "", "");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(BaseService.SYSTEMERROR, "", "");
		}
		
		return model;
	}	
	
	public int preCheck(HttpServletRequest request, String data, String operType, String userUnitId, String userType) throws Exception {
		
		int result = 0;
		
		UserModel userModel = new UserModel();
		
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		
		userDefinedSearchCase.put("loginid", getJsonData(data, "loginid"));
		userModel.setQueryFileName("/user/userquerydefine");
		userModel.setQueryName("userquerydefine_checkLoginId");
		BaseQuery baseQuery = new BaseQuery(request, userModel);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		ArrayList<ArrayList<String>> userDataList = baseQuery.getQueryData();
		if (operType.equals("add")) {
			if (userDataList.size() > 0) {
				//用户登录名已存在
				result = 1;
			}
		} else {
			if (userDataList.size() == 0) {
				//用户登录名不存在
				//result = 2;
			} else {
				String updateSrcUserId = getJsonData(data, "loginidbackup");
				if (!userDataList.get(0).get(1).equals(updateSrcUserId)) {
					//用户登录名已存在
					result = 3;
				}
			}
		}

		return result;
	}
	
	public static S_USERData updateModifyInfo(S_USERData data, UserInfo userInfo) {
		String createUserId = data.getCreateperson();
		if ( createUserId == null || createUserId.equals("")) {
			data.setCreateperson(userInfo.getUserId());
			data.setCreatetime(CalendarUtil.fmtDate());
			data.setCreateperson(userInfo.getUserId());
			data.setCreateunitid(userInfo.getUnitId());
		}
		data.setModifyperson(userInfo.getUserId());
		data.setModifytime(CalendarUtil.fmtDate());
		data.setDeleteflag(BusinessConstants.DELETEFLG_UNDELETE);
		
		return data;
	}
	
	public JSONObject uploadPhoto(MultipartFile headPhotoFile, HttpServletRequest request) {
		String userId = request.getParameter("userid");
		String finalUserId = "";
		String realPath = request.getSession().getServletContext().getRealPath(BusinessConstants.USERHEADPHOTOTEMPPATH);
		String photoName = "";
		boolean isSuccess = false;
		JSONObject jsonObj = new JSONObject();
		
		if (userId == null || userId.equals("")) {
			finalUserId = BaseDAO.getGuId();
		} else {
			finalUserId = userId;
		}
		
		photoName = finalUserId + "-" + CalendarUtil.fmtDate().replace(" ", "").replace(":", "").replace("-", ""); 
		
		try {
			FileUtils.copyInputStreamToFile(headPhotoFile.getInputStream(), new File(realPath, photoName + BusinessConstants.JPEGSUFFIX));
			
			isSuccess = true;
		}
		catch(Exception e) {
			System.out.println(e.getMessage());

		}
		try {
			if (isSuccess) {
				jsonObj.put("result", "0");
				jsonObj.put("userId", finalUserId);
				jsonObj.put("path", BusinessConstants.USERHEADPHOTOTEMPPATH + File.separator + photoName + BusinessConstants.JPEGSUFFIX);
			} else {
				jsonObj.put("result", "1");
				jsonObj.put("message", "头像上传失败");
			}
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return jsonObj;
	}
	
	public String createPhoto(HttpServletRequest request, S_USERData data, ArrayList<String> strPhotoData) {
		
		String photoFileName = "";
		String rtnValue = "";
		try {
			String realPath = request.getSession().getServletContext().getRealPath(BusinessConstants.USERHEADPHOTOPATH);
			
			Blob blob = null;
			
			if (data != null) {
				photoFileName = data.getUserid() + (data.getModifytime().replace(" ", "").replace(":", "").replace(".", "")) + BusinessConstants.JPEGSUFFIX;
				blob = data.getPhoto();
			} else {
				photoFileName = strPhotoData.get(0) + (strPhotoData.get(1).replace(" ", "").replace(":", "").replace(".", "")) + BusinessConstants.JPEGSUFFIX;
				if (strPhotoData.get(2) != null) {
					byte[] bytes = hexStringToByte(strPhotoData.get(2));
					blob = new SerialBlob(bytes);
				}
			}
			
			if (blob != null) {
				String photoPath = realPath + File.separator + photoFileName;
				File photoFile = new File(photoPath);
				if (!photoFile.exists()) {
					InputStream in = blob.getBinaryStream();
					
					long nLen = blob.length();
		            int nSize = (int) nLen;
		            byte[] blobData = new byte[nSize];
		            in.read(blobData);			
					FileOutputStream fout = new FileOutputStream(photoPath);
					fout.write(blobData);
					fout.flush();
					fout.close();
					in.close();
				}
				rtnValue = BusinessConstants.USERHEADPHOTOPATH + photoFileName;
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return rtnValue;
	}
	
	public int doResetPwd(HttpServletRequest request, UserModel userModel, UserInfo userInfo) throws Exception {
		
		int rtnValue = 0;
		
    	userModel.setQueryFileName("/login/loginquery");
		userModel.setQueryName("verify");
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		
		userDefinedSearchCase.put("loginId", userInfo.geLoginId());
		userDefinedSearchCase.put("loginPwd", DesUtil.DesEncryptData(userModel.getNowPwd()));

		BaseQuery baseQuery = new BaseQuery(request, userModel);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		ArrayList<ArrayList<String>> result = baseQuery.getFullData();
		if (result.size() == 0) {
			rtnValue = 1;
		} else {
			S_USERData data = new S_USERData();
			data.setUserid(userInfo.getUserId());
			S_USERDao dao = new S_USERDao(data);
			data = dao.beanData;
			data.setLoginpwd(DesUtil.DesEncryptData(userModel.getWantPwd()));
			
			dao.Store(data);
		}
	
		return rtnValue;
	}
	
	public ArrayList<ListOption> getUserListByDuty(HttpServletRequest request, String duty) {
		ArrayList<ListOption> optionList = new ArrayList<ListOption>();
		ArrayList<ArrayList<String>> userDataList;
		UserModel dataModel = new UserModel();
		try {
			HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
			dataModel.setQueryFileName("/user/userquerydefine");
			dataModel.setQueryName("userquerydefine_searchbyduty");
			userDefinedSearchCase.put("Duty", duty);
			BaseQuery baseQuery = new BaseQuery(request, dataModel);
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
			userDataList = baseQuery.getQueryData();
			
			for(ArrayList<String>rowData:userDataList) {
				ListOption option = new ListOption(rowData.get(1), rowData.get(2));
				optionList.add(option);
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return optionList;
	}
	
	
	private S_USERData setDeptGuid(HttpServletRequest request, String formData, S_USERData data) throws Exception {
		S_DEPTDao dao = new S_DEPTDao();
		S_DEPTData deptData = new S_DEPTData();

		deptData.setUnitid(getJsonData(formData, "unitId"));
		deptData = (S_DEPTData)dao.FindByPrimaryKey(deptData);

		data.setDeptguid(deptData.getDeptguid());
		
		return data;
	}
	
	private S_USERData setFlag(S_USERData data) {
	
		if (data.getLockflag() == null || data.getLockflag().equals("")) {
			data.setLockflag("0");
		}		
		if (data.getEnableflag() == null || data.getEnableflag().equals("")) {
			data.setEnableflag("0");
		}
		
		return data;
	}
	
	private S_USERData setPhoto(HttpServletRequest request, String formData, S_USERData data) throws Exception {

		String photoPath = getJsonData(formData, "photo");
		if (!photoPath.equals("")) {
			FileInputStream fis = null; 
			String realPath = request.getSession().getServletContext().getRealPath(photoPath);
			File file = new File(realPath);
			fis = new FileInputStream(file);
			data.setPhotoStream(fis);
			//data. = file.length();
			//fis.close();
		} else {
			data.setPhotoStream(null);
		}
		
		
		return data;
	}
	
	private void movePhoto(HttpServletRequest request, String formData, String userId) {
		try {
			//String targetPath = request.getSession().getServletContext().getRealPath(Constants.USERHEADPHOTOPATH);
			//targetPath += "//" + userId + Constants.JPEGSUFFIX;
			String srcPath = request.getSession().getServletContext().getRealPath(getJsonData(formData, "photo")); 
			
			//File targetFile = new File(targetPath);
			File srcFile = new File(srcPath);
			//org.springframework.util.FileCopyUtils.copy(srcFile, targetFile);
			
			srcFile.delete();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
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
	 
	public HashMap<String, Object> doDutiesIdNameSearch(HttpServletRequest request, String data) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		String key = "";
		
		data = URLDecoder.decode(data, "UTF-8");

		key = request.getParameter("key");
		if (key.equals("*")) {
			key = "";
		}
		
		dataModel.setQueryFileName("/unit/unitquerydefine");	
		dataModel.setQueryName("dicSearch");	
		BaseQuery baseQuery = new BaseQuery(request, dataModel);	
		
		userDefinedSearchCase.put("key", key);
		userDefinedSearchCase.put("typeid", DicUtil.DUTIES);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);

		baseQuery.getYsQueryData(0,0);	
			
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;		

	}
	
	public HashMap<String, Object> doUnitSearch(HttpServletRequest request, String data) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		String key = "";
		
		data = URLDecoder.decode(data, "UTF-8");

		key = request.getParameter("key");	
		if (key.equals("*")) {
			key = "";
		}
		dataModel.setQueryFileName("/unit/unitquerydefine");	
		dataModel.setQueryName("unitSearch");	
		BaseQuery baseQuery = new BaseQuery(request, dataModel);	
		
		userDefinedSearchCase.put("key", key);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);

		baseQuery.getYsQueryData(0,0);	
			
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;		

	}
	
	public HashMap<String, Object> doAddressSearch(HttpServletRequest request, String data) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		String key = "";
		
		data = URLDecoder.decode(data, "UTF-8");

		key = request.getParameter("key");	
		if (key.equals("*")) {
			key = "";
		}
		dataModel.setQueryFileName("/unit/unitquerydefine");	
		dataModel.setQueryName("dicSearch");
		userDefinedSearchCase.put("typeid", DicUtil.ADDRESS);
		BaseQuery baseQuery = new BaseQuery(request, dataModel);	
		
		userDefinedSearchCase.put("key", key);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);

		baseQuery.getYsQueryData(0,0);	
			
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;		

	}
}
