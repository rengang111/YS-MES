package com.ys.system.service.yssample;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.ys.system.service.common.BaseService;
import com.ys.util.basequery.BaseQuery;
import com.ys.util.basequery.common.BaseModel;

@Service
public class YsSampleService extends BaseService {
 
	public HashMap<String, Object> doSearch(HttpServletRequest request, String data) {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> rtnData = new ArrayList<HashMap<String, String>>();
		try {
			data = URLDecoder.decode(data, "UTF-8");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		int iStart = 0;
		int iEnd =0;
		String sEcho = getJsonData(data, "sEcho");	
		String start = getJsonData(data, "iDisplayStart");		
		if (start != null && !start.equals("")){
			iStart = Integer.parseInt(start);			
		}
		
		String length = getJsonData(data, "iDisplayLength");
		if (length != null){			
			iEnd = iStart + Integer.parseInt(length);			
		}		
		
		String key1 = getJsonData(data, "keyword1");
		String key2 = getJsonData(data, "keyword2");
		
		BaseModel dataModel = new BaseModel();

		dataModel.setQueryFileName("/yssample/yssamplequerydefine");
		dataModel.setQueryName("yssamplequerydefine_search");
		try {
			BaseQuery baseQuery = new BaseQuery(request, dataModel);
			baseQuery.getYsQueryData(iStart, iEnd);	
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		if ( iEnd > dataModel.getYsViewData().size()){
			
			iEnd = dataModel.getYsViewData().size();
			
		}		
		
		modelMap.put("sEcho", sEcho); 
		
		modelMap.put("recordsTotal", dataModel.getRecordCount()); 
		
		modelMap.put("recordsFiltered", dataModel.getRecordCount());
		
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
	}


}
