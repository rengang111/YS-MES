package com.ys.system.service.organ;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ys.system.service.common.BaseService;
import com.ys.util.basequery.BaseQuery;
import com.ys.util.basequery.common.BaseModel;

@Service
public class OrganService extends BaseService {
 
	/*@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@ModelAttribute("factory") TFactory factory) throws Exception {
		ModelAndView mv = new ModelAndView("/buy001-factory/factory-add");
		
		try{
			
			//所在国家
			List<ListOption> countryList = countryService.getCountryList("COUNTRY", false,false);
			mv.addObject("countryList", countryList);
			
			List<MDict> yearValueList = dictionaryService.getDictionary("YEAR_VALUE", false,false);
			mv.addObject("yearValueList", yearValueList);
			
//			List<MDict> mainMarketList = dictionaryService.getDictionary("MAIN_MARKET", false,false);
			
			List mainMarketList = dictStandardService.getStdDictList("market");
			mv.addObject("mainMarketList", mainMarketList);
			
			List factoryMainBusinessList = (List) classService.getClassList("PRODUCT",false,false).get("data");
			mv.addObject("factoryMainBusinessList", factoryMainBusinessList);
			
			List<MDict> dealStatusList = dictionaryService.getDictionary("STATUS_DEAL", false,false);
			mv.addObject("dealStatusList", dealStatusList);

			commonUtil.mvAddMsg(mv);
			
		}catch(Exception e){
			//System.out.println(e.getMessage());
			Exception ex = ExceptionAdvisor.extransfer(e);
			throw ex;			
		}
		
		return mv;
	}*/
	
	public HashMap<String, Object> doInit(HttpServletRequest request, String data) {
		
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

	public HashMap<String, Object> doSearch(HttpServletRequest request, String data) {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
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
		if (length != null && !length.equals("")){			
			iEnd = iStart + Integer.parseInt(length);			
		}		
		
		String key1 = getJsonData(data, "keywords1");
		String key2 = getJsonData(data, "keywords1");
		userDefinedSearchCase.put("keywords1", key1);
		userDefinedSearchCase.put("keywords1", key2);
		
		BaseModel dataModel = new BaseModel();

		dataModel.setQueryFileName("/organization/orgquerydefine");
		dataModel.setQueryName("orgquerydefine_search");
		try {
			BaseQuery baseQuery = new BaseQuery(request, dataModel);
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
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
