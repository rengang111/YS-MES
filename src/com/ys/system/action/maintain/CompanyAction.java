package com.ys.system.action.maintain;

import java.util.HashMap;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ys.system.action.common.BaseAction;
import com.ys.system.action.model.maintain.CompanyModel;
import com.ys.system.service.maintain.CompanyService;

import com.ys.util.basedao.ListOption;
import com.ys.util.basequery.common.BaseModel;
import com.ys.system.action.model.dic.DicModel;
//import ys.model.TFactory;
//import ys.util.ExceptionAdvisor;

import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class CompanyAction extends BaseAction {
	
	@Autowired CompanyService companyService;
	
	@RequestMapping(value="doInit", method = RequestMethod.GET)
	public String doInit(@RequestBody String data, @ModelAttribute("company")CompanyModel company, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		return "/maintain/orgShow";
		
	}
	
	@RequestMapping(value = "retrieve", method = RequestMethod.GET)
	public String create(@ModelAttribute("company") CompanyModel company) throws Exception {
		//ModelAndView mv = new ModelAndView("/maintain/company");

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		try{
			
			//List<DicModel> dealStatusList = dictionaryService.getDictionary("STATUS_DEAL", false,false);
			//mv.addObject("dealStatusList", dealStatusList);

			modelMap.put("companyList", "");
			
		}catch(Exception e){
			System.out.println(e.getMessage());
			//Exception ex = ExceptionAdvisor.extransfer(e);
			//throw ex;			
		}
		
		return "/maintain/company";
	}
	
	@RequestMapping(value="doSearch")
	@ResponseBody
	public HashMap<String, Object> doAdd(@RequestBody String data, @ModelAttribute("company")CompanyModel company, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){ 
		
		return companyService.doSearch(request, data);
		
	}

}
