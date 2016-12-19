package com.ys.system.service.common;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.ys.business.db.data.CommFieldsData;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.util.CalendarUtil;
import com.ys.util.basedao.BaseTransaction;
import com.ys.util.basequery.BaseQuery;
import com.ys.util.basequery.common.Constants;

/**
 * @author 
 *
 */
public class BaseService {
	
	protected final String DUMMYKEY = "-1";
	protected final String SYSTEMERROR = "-1";
	protected final String NORMAL = "000";
	
	/*
	public BaseTransaction baseTransaction;
	
	public void begin() throws Exception {
		baseTransaction = new BaseTransaction();
		baseTransaction.begin();
	}
	
	public void commit() throws Exception {
		baseTransaction.commit();
	}
	
	public void rollback() throws Exception {
		baseTransaction.rollback();
	}
	*/

	public String getJsonData(String formData, String key){
	    String dataValue = new String();
	
	    try
	    {
	    	
	        JSONArray jArray = new JSONArray(formData);
	        
	        for (int i = 0; i < jArray.length(); i++)
	        {
	            JSONObject resultJsnObj = jArray.getJSONObject(i);
	            if (resultJsnObj.get("name").equals(key))
	            {
	            	dataValue = String.valueOf(resultJsnObj.get("value"));
	            	break;
	                //dataValue.add((String) resultJsnObj.get("value"));
	                //if (isOnlyOne)
	                //{
	                //    break;
	                //}
	            }
	        }
	        
	    }
	    catch(Exception e)
	    {
	        System.out.println(e.getMessage());
	    }
	
	    return dataValue;
	}
	
	protected String getMsg(String key) {
		String fileName = "/setting/messages_zh_CN";
		return BaseQuery.getContent(fileName, key);
	}
	
	/*
	 * mapToBean
	 * parm:map
	 */
	public Object mapToObject(
			Map<String, Object> map, 
			Class<?> beanClass)  throws Exception {    
        
		if (map == null || beanClass == null) { 
            return null;  
		}		
        Object obj = beanClass.newInstance();  
  
        BeanUtils.populate(obj, map);  
  
        return obj;  
	}  
	

	/*
	 * mapToBean
	 * parm:map
	 */
	public Object AaaryListMapToObject(
			ArrayList<HashMap<String, String>> ArrayMap, 
			Class<?> beanClass)  throws Exception {    
		
		ArrayList<Object> rtnList = new ArrayList<Object>();
        		
		if (ArrayMap == null || beanClass == null) { 
            return null;  
		}	
		int i = 0;
		for(HashMap<String, String> map:ArrayMap){
			rtnList.add(i,mapToObject((Map) map,beanClass));
			i++;
		}
         
  
        return rtnList;  
	} 
	
	/**
	 * @机能 将source中不为空的值复制到target中;<br>
	 * 1.可以处理相同结构的两个bean,<br>
	 * 2.可以处理不同机构,但一部分的方法名称相同的两个bean;<br>
	 * 
	 */
	public void copyProperties(Object target, Object source) throws Exception {
		
		try{
	        java.lang.reflect.Field[] field = target.getClass().getDeclaredFields();
	        
			//java.lang.reflect.Method[] mm = source.getClass().getDeclaredMethods();
	        for(int j=0 ; j<field.length ; j++){
	        	
	            String name = field[j].getName();    
	            String type = field[j].getGenericType().toString(); 
	            
	            try{
	            	
		            if(type.equals("class java.lang.String")){
		                Method m = target.getClass().getMethod("get"+toUpperCaseFirstOne(name));
		                Method s = source.getClass().getMethod("get"+toUpperCaseFirstOne(name));
		              
		                	String sValue = (String) s.invoke(source);
		                    if(sValue!=null){
		                        m = target.getClass().getMethod("set"+toUpperCaseFirstOne(name), String.class);
		                        m.invoke(target, sValue);
		                    }
		              
		            } else if("Date".equals(type)) {
		                Method m = target.getClass().getMethod("get"+toUpperCaseFirstOne(name));
		                //Date value = (Date) m.invoke(target);    
		                Date value2 = (Date) m.invoke(source);   
		                if(value2 == null){
		                    m = target.getClass().getMethod("set"+toUpperCaseFirstOne(name), Integer.class);
		                    m.invoke(target, value2);
		                }
		            }
	            }catch (Exception e){
	        		//主要一场由有:找不到字段和取值,依然作为正常流程,过滤后继续处理;
	            }
	        }
		}catch (Exception e){
			//复制出错的话,返回原值;
		}
		
    }
	
	/*
	 * 首字母大写
	 */
	private String toUpperCaseFirstOne(String name){
		
		 if(name!= null & name.length()>1){
			 String firstOne = name.substring(0,1);
			 String upper = firstOne.toUpperCase();
			 name = upper +name.substring(1);
		 }
		 return name;		 
	}

	/**表字段的共通编辑方法
	 * 
	 * @param type int DB操作方式:0 插入;1 更新;2 删除
	 * @param method String  FormId
	 * @param userInfo UserInfo
	 * @return CommFieldsData
	 */
	public CommFieldsData commFiledEdit(
			int type,
			String method,
			UserInfo userInfo) {
		
		if(userInfo ==null)
			return null;
		
		CommFieldsData data = new  CommFieldsData();
		data.setFormid(method);
		
		if (type == Constants.ACCESSTYPE_INS) {//insert
			data.setCreateperson(userInfo.getUserId());
			data.setCreatetime(CalendarUtil.fmtDate());
			data.setCreateunitid(userInfo.getUnitId());
			data.setModifyperson(userInfo.getUserId());
			data.setModifytime(CalendarUtil.fmtDate());

		}else{//update
			data.setModifyperson(userInfo.getUserId());
			data.setModifytime(CalendarUtil.fmtDate());
			
		}
		
		if (type == Constants.ACCESSTYPE_DEL) {//delete
			data.setDeleteflag(BusinessConstants.DELETEFLG_DELETED);
		}else{
			data.setDeleteflag(BusinessConstants.DELETEFLG_UNDELETE);
			
		}

		return data;
	}
	
	/**
	 * 
	 * @param s
	 * @return 处理URL传值乱码问题
	 * @throws UnsupportedEncodingException
	 */
	public String convertToUTF8(String s) 
			throws UnsupportedEncodingException{

		return  URLDecoder.decode(s,"utf-8");
		
	}
	
}
