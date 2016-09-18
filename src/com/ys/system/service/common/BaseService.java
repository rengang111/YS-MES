package com.ys.system.service.common;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ys.util.basedao.BaseTransaction;

/**
 * @author 
 *
 */
public class BaseService {
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
}
