package com.ys.system.action.yssample;

import java.util.HashMap;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.ys.system.action.common.BaseAction;
import com.ys.system.action.model.TestModel;
import com.ys.system.service.yssample.YsSampleService;

@Controller
@RequestMapping("/")
public class YsSampleAction extends BaseAction {
	
	@Autowired YsSampleService ysSampleService;
	
	@RequestMapping(value="ysdemomain")
	public String doTest(@RequestBody String data, @ModelAttribute("dataModels")TestModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		return "/ysdemo/main";
		
	}
	
	@RequestMapping(value="yssearch")
	@ResponseBody
	public HashMap<String, Object> doYsTest(@RequestBody String data, @ModelAttribute("dataModels")TestModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){ 
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		
		return ysSampleService.doSearch(request, data);
		
	}

}
