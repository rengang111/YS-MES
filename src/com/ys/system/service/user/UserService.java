package com.ys.system.service.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.action.model.user.UserModel;
import com.ys.system.common.BusinessConstants;
import com.ys.system.db.dao.S_DEPTDao;
import com.ys.system.db.dao.S_USERDao;
import com.ys.system.db.data.S_DEPTData;
import com.ys.system.db.data.S_USERData;
import com.ys.system.ejb.DbUpdateEjb;
import com.ys.util.CalendarUtil;
import com.ys.util.DesUtil;
import com.ys.util.DicUtil;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basequery.BaseQuery;

import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;
import javax.sql.rowset.serial.SerialBlob;

@Service
public class UserService {
 
	public UserModel doSearch(HttpServletRequest request, UserModel dataModel, UserInfo userInfo) throws Exception {
		ArrayList<ArrayList<String>> userDataList;
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		dataModel.setQueryFileName("/user/userquerydefine");
		dataModel.setQueryName("userquerydefine_search");
		userDefinedSearchCase.put("userUnitId", userInfo.getUnitId());
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		userDataList = baseQuery.getQueryData();
		
		for(ArrayList<String> rowData:userDataList) {
			ArrayList<String> stringPhotoData = new ArrayList<String>();
			stringPhotoData.add(rowData.get(1));
			stringPhotoData.add(rowData.get(6));
			stringPhotoData.add(rowData.get(7));
			rowData.set(7, createPhoto(request, null, stringPhotoData));
		}
		
		baseQuery.getQueryData();
		
		dataModel.setUserId(userInfo.getUserId());
		
		dataModel.setDutyList(DicUtil.getGroupValue(DicUtil.DUTIES));
		dataModel.setSexList(DicUtil.getGroupValue(DicUtil.SEX));
		
		return dataModel;
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
	
	public void doAdd(HttpServletRequest request, UserModel userModel, UserInfo userInfo) throws Exception {
		S_USERDao dao = new S_USERDao();
		
		S_USERData data = userModel.getuserData();
		data = updateModifyInfo(userModel.getuserData(), userInfo);
		
		data = setDeptGuid(request, userModel, data);
		
		String userId = request.getParameter("userData.userid");
		if (userId == null || userId.equals("")) {	
			userId = BaseDAO.getGuId();
		}
		data.setUserid(userId);
		data.setUnitid(userModel.getUnitId());
		
		data = setFlag(data);		
		data.setLoginpwd(DesUtil.DesEncryptData(data.getLoginpwd()));
		data = setPhoto(request, userModel.getPhoto(), data);
		
		try {
			dao.Create(data);
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			if (!userModel.getPhoto().equals("")) {
				data.getPhotoStream().close();
			}
		}		
		movePhoto(request, userModel, userId);
	}	
	
	public void doUpdate(HttpServletRequest request, UserModel userModel, UserInfo userInfo) throws Exception {
		S_USERDao dao = new S_USERDao();
		S_USERData data = updateModifyInfo(userModel.getuserData(), userInfo);
		data = setDeptGuid(request, userModel, data);
		data = setPhoto(request, userModel.getPhoto(), data);
		data.setUnitid(userModel.getUnitId());
		data = setFlag(data);
		//data.setLoginpwd(DesUtil.DesEncryptData(data.getLoginpwd()));
		try {
			dao.Store(data);
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			if (!userModel.getPhoto().equals("")) {
				data.getPhotoStream().close();
			}
		}
		movePhoto(request, userModel, data.getUserid());
	}
	
	public void doDelete(UserModel userModel, UserInfo userInfo) throws Exception {
		
		DbUpdateEjb bean = new DbUpdateEjb();
        
        bean.executeUserDelete(userModel, userInfo);

	}
	
	public void doLock(UserModel userModel, UserInfo userInfo, int isLock) throws Exception {
		
		DbUpdateEjb bean = new DbUpdateEjb();
        
        bean.executeUserLock(userModel, userInfo, String.valueOf(isLock));


	}	
	
	public int preCheck(HttpServletRequest request, String operType, String userUnitId, String userType) throws Exception {
		
		int result = 0;
		
		UserModel userModel = new UserModel();
		
		new HashMap<String, String>();
		//userDefinedSearchCase.put("userIdName", request.getParameter("loginid"));
		//userDefinedSearchCase.put("userUserId", userUnitId);
		userModel.setQueryFileName("/user/userquerydefine");
		userModel.setQueryName("userquerydefine_checkLoginId");
		BaseQuery baseQuery = new BaseQuery(request, userModel);
		//baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
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
				String updateSrcUserId = request.getParameter("userData.loginid");
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
	
	public JSONObject uploadPhoto(HttpServletRequest request, String userId, MultipartFile headPhotoFile) {
		
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
	
	private S_USERData setDeptGuid(HttpServletRequest request, UserModel userModel, S_USERData data) throws Exception {
		S_DEPTDao dao = new S_DEPTDao();
		S_DEPTData deptData = new S_DEPTData();

		deptData.setUnitid(request.getParameter("unitId"));
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
	
	private S_USERData setPhoto(HttpServletRequest request, String photoPath, S_USERData data) throws Exception {

		if (!photoPath.equals("")) {
			FileInputStream fis = null; 
			String realPath = request.getSession().getServletContext().getRealPath(photoPath);
			File file = new File(realPath);
			fis = new FileInputStream(file);
			data.setPhotoStream(fis);
			//data. = file.length();
			//fis.close();
		}
		
		
		return data;
	}
	
	private void movePhoto(HttpServletRequest request, UserModel userModel, String userId) {
		try {
			//String targetPath = request.getSession().getServletContext().getRealPath(Constants.USERHEADPHOTOPATH);
			//targetPath += "//" + userId + Constants.JPEGSUFFIX;
			String srcPath = request.getSession().getServletContext().getRealPath(userModel.getPhoto()); 
			
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
}
