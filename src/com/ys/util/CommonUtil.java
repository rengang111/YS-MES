/*package com.ys.util;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.sun.org.apache.xalan.internal.xsltc.compiler.Pattern;

//import ys.dao.ClassDao;
//import ys.dao.CommonDao;
import ys.dao.DictStandardDao;
import ys.dao.DictionaryDao;
import ys.dto.ListOption;
//import ys.model.MDict;
//import ys.service.ClassService;

@Component
public class CommonUtil {
	
	@Autowired
	private DictionaryDao dictionaryDao;
	
	@Autowired
	private DictStandardDao dictStandardDao;
	
	@Autowired
	private ClassDao classDao;
		
	//�������������ridiobutton���õ����
	public static List addListOptionTop(List list,boolean select ,boolean all){
		
		if (select) {
			ListOption option = new ListOption("--��ѡ��--","--��ѡ��--");
			
			list.add(0,option);
		}	
		
		if (all) {
			ListOption option = new ListOption("ȫ��","ȫ��");
			
			list.add(0,option);
		}	
		
		return list;
	}
	
	//���checkbox�Ķ�ѡ�ַ����ֶα��е���Ӧ����ֵ����
	public String getCheckboxStringConcat(String category,String checkIds){
		
		if ((checkIds == null||checkIds.equals(""))){
			return null;
		}
		
		String[] strArray = checkIds.split(",");
		
		String mbId = "";
		String str_result = "";
		
		for(int i=0;i<strArray.length;i++){
			
			mbId= strArray[i];
			
			if ((mbId == null||mbId.equals(""))){
				return null;
			}else{								
				str_result = str_result + dictionaryDao.getValue(category,mbId) + "";				
			}
			
		}
		
		return str_result;
	}
	
	//��ҵ���ֵ���и��id��ȡֵ
	public String getCheckboxStringConcatStd(String checkIds,String language){
		
		if ((checkIds == null||checkIds.equals(""))){
			return null;
		}
		
		String[] strArray = checkIds.split(",");
		
		String mbId = "";
		String str_result = "";
		
		for(int i=0;i<strArray.length;i++){
			
			mbId= strArray[i];
			
			if ((mbId == null||mbId.equals(""))){
				return null;
			}else{	
				if (language.endsWith("chn")){
					str_result = str_result + dictStandardDao.load(Long.parseLong(mbId)).getNameChn() + " , ";
				}else{
					str_result = str_result + dictStandardDao.load(Long.parseLong(mbId)).getNameEng() + " , ";
				}
			}
			
		}
		
		if (!(str_result == null||str_result.equals(""))){
			
			str_result = str_result.substring(0, str_result.length()-2);
		}
		
		return str_result;
	}
	
	//���checkbox�Ķ�ѡ�ַ����ֶα��е���Ӧ����ֵ����
	public String getCheckboxStringConcatByClass(String category,String checkIds,String language){
		
		if ((checkIds == null||checkIds.equals(""))){
			return null;
		}
		
		String[] strArray = checkIds.split(",");
		
		String mbId = "";
		String str_result = "";
		
		for(int i=0;i<strArray.length;i++){
			
			mbId= strArray[i];
			
			if ((mbId == null||mbId.equals(""))){
				return null;
			}else{		
				
				if (language.endsWith("chn")){
					str_result = str_result + classDao.getClass("PRODUCT",mbId)+ " , ";;	
				}else{
					str_result = str_result + classDao.getClassEng("PRODUCT",mbId)+ " , ";;	
				}		
			}
		}
		
		if (!(str_result == null||str_result.equals(""))){			
			str_result = str_result.substring(0, str_result.length()-2);
		}
		
		return str_result;
	}
	
	*//**
	 * 
	 * @Title: mvAddMsg
	 * @Description: ���Msgֵ
	 * @param mv
	 *//*
	public void mvAddMsg(ModelAndView mv){
		
		mv.addObject("msg", OVLoadProperties.getInstance()
				.getProperties("0001"));
		
		mv.addObject("msg0004", OVLoadProperties.getInstance()
				.getProperties("0004"));
		
		mv.addObject("msg0005", OVLoadProperties.getInstance()
				.getProperties("0005"));
		
		mv.addObject("err001", OVLoadProperties.getInstance()
				.getProperties("err001"));
		
		mv.addObject("err002", OVLoadProperties.getInstance()
				.getProperties("err002"));
		
		mv.addObject("err003", OVLoadProperties.getInstance()
				.getProperties("err003"));
		
		mv.addObject("err004", OVLoadProperties.getInstance()
				.getProperties("err004"));
		
		mv.addObject("err005", OVLoadProperties.getInstance()
				.getProperties("err005"));
		
		mv.addObject("err006", OVLoadProperties.getInstance()
				.getProperties("err006"));
		
		mv.addObject("suc001", OVLoadProperties.getInstance()
				.getProperties("suc001"));
		
	}	
	
	public static int getCharacterPosition(String string,String search,int num){
	    //�����ǻ�ȡ"/"��ŵ�λ��
	    Matcher slashMatcher = java.util.regex.Pattern.compile(search).matcher(string);
	    int mIdx = 0;
	    while(slashMatcher.find()) {
	       mIdx++;
	       //��"/"��ŵ���γ��ֵ�λ��
	       if(mIdx == num){
	          break;
	       }
	    }
	    return slashMatcher.start();
	 }
	
}
*/