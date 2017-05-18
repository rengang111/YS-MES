package com.ys.system.service.login;

import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import com.ys.util.basequery.common.BaseModel;
import com.ys.system.action.model.login.LoginModel;
import com.ys.system.action.model.login.UserInfo;
import com.ys.util.basequery.common.Constants;
import com.ys.system.db.dao.S_USERDao;
import com.ys.system.db.data.S_USERData;
import com.ys.system.service.common.BaseService;
import com.ys.system.service.user.UserService;
import com.ys.util.CalendarUtil;
import com.ys.util.DesUtil;
import com.ys.util.DicUtil;
import com.ys.util.basequery.BaseQuery;

@Service
public class LoginService extends BaseService {
 
	public LoginModel doLogin(HttpServletRequest request, LoginModel dataModel, String validateCode) {

		//TODO:
		validateCode = "1";
		dataModel.setVerifyCode("1");
		try {
	        if(dataModel.getVerifyCode() == null || dataModel.getVerifyCode().equals("")) { 
	            dataModel.setMessage("验证码为空");         
	        } else {         
	            if (validateCode != null && validateCode.toLowerCase().equals(dataModel.getVerifyCode().toLowerCase())) {
	        		dataModel.setQueryFileName("/login/loginquery");
	        		dataModel.setQueryName("verify");
	        		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
	        		
	        		userDefinedSearchCase.put("loginId", dataModel.getLoginId());
	        		userDefinedSearchCase.put("loginPwd", DesUtil.DesEncryptData(dataModel.getPwd()));
	
	        		BaseQuery baseQuery = new BaseQuery(request, dataModel);
	        		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
	        		ArrayList<ArrayList<String>> result = baseQuery.getFullData();
	        		if (result.size() == 0) {
	        			dataModel.setMessage("用户名错误或者密码不正确");
	        		} else {
	        			
	        			dataModel.setUserId(result.get(0).get(0));
	        			
	        			if (result.get(0).get(1).equals(UserInfo.LOCKED)) {
	        				dataModel.setMessage("用户已被锁定");
	        			} else {
	        				String startDate = result.get(0).get(2);
	        				String endDate = result.get(0).get(3);
	        				
	        				String sysDate = CalendarUtil.fmtDate();
	        				if (CalendarUtil.compareDate(sysDate, startDate) == 1) {
	        					dataModel.setMessage("用户未生效");
	        				} else {
	        					if (CalendarUtil.compareDate(sysDate, endDate) == 0) {
	        						dataModel.setMessage("用户已失效");
	        					}
	        				}
	        			}
	        		}
	            } else {         
	            	dataModel.setMessage("验证码错误");
	            }
	        }
	        
	        if (dataModel.getMessage().equals("")) {
	        	S_USERDao dao = new S_USERDao();
	        	S_USERData data = new S_USERData();
	        	
	        	data.setUserid(dataModel.getUserId());
	        	data = (S_USERData)dao.FindByPrimaryKey(data);
	        	dataModel.setData(data);
	        }
		}
		catch(Exception e) {
			System.out.println(e);
			dataModel.setMessage("系统错误");
		}
		return dataModel;
	}

	public UserInfo setUserInfo(HttpServletRequest request, S_USERData data) throws Exception {
    	UserInfo userInfo = new UserInfo();
    	//userInfo.setData(data);

		userInfo.setUserId(data.getUserid());
		userInfo.setLoginId(data.getLoginid());
		userInfo.setUserName(data.getLoginname());
		userInfo.setUnitId(data.getUnitid());
		userInfo.setDeptGuid(data.getDeptguid());
		
		userInfo.setUnitName(DicUtil.getCodeValue(DicUtil.UNIT + data.getUnitid()));
		
		if (data.getLoginid().equals(UserInfo.SANAME)) {
			userInfo.setUserType(Constants.USER_SA);
		} else {
			BaseModel dataModel = new BaseModel();
    		dataModel.setQueryFileName("/login/loginquery");
    		dataModel.setQueryName("getrole");
    		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
    		
    		userDefinedSearchCase.put("userId", data.getUserid());
    		userDefinedSearchCase.put("unitId", data.getUnitid());

    		BaseQuery baseQuery = new BaseQuery(request, dataModel);
    		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
    		ArrayList<ArrayList<String>> result = baseQuery.getFullData();		
			for(ArrayList<String> rowData:result) {
				if(rowData.get(0).equals(Constants.USER_ADMIN)) {
					userInfo.setUserType(Constants.USER_ADMIN);
					break;
				}
			}	
		}

		UserService userService = new UserService();
		userInfo.setPhoto(userService.createPhoto(request, data, null));
		
		return userInfo;
	}
}
