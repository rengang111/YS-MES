package com.ys.business.service.common;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.ys.business.db.data.CommFieldsData;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.util.CalendarUtil;
import com.ys.util.basequery.common.Constants;

public class BusinessService {
	
	
	/**
	 * @return 2位年份+YS
	 */
	public static String getYSCommCode()
	{
		//当前的2位年份取得
		SimpleDateFormat sdf = new SimpleDateFormat(
				BusinessConstants.SHORTNAME_YEAR_YY);
	    Date date = new Date();
	    String nowYear = sdf.format(date);
	        
		//耀升编号
	    String code = nowYear + BusinessConstants.SHORTNAME_YS;

		return code;
	}
	

	/**
	 * @return 2位年份+YSK
	 */
	public static String getYSKCommCode()
	{
		//当前的2位年份取得
		SimpleDateFormat sdf = new SimpleDateFormat(
				BusinessConstants.SHORTNAME_YEAR_YY);
	    Date date = new Date();
	    String nowYear = sdf.format(date);
	        
		//耀升编号+K:库存
	    String code = nowYear + BusinessConstants.SHORTNAME_YSK;

		return code;
	}
	
	/**
	 * @param 流水号
	 * @param blAdd:是否要递增 true:要,false:不要
	 * @return 3位流水号格式化处理
	 */
	public static String getYSFormatCode(int id,boolean blAdd)
	{
		//
		String ys = getYSCommCode();
		String yscode = getFormatCode(id,blAdd);
		
		String ysfullcode = ys + yscode;

		return ysfullcode;
	}
	
	/*
	 * 3位流水号格式化处理
	 * blAdd:是否要递增
	 */
	private static String getFormatCode(int code,boolean blAdd)
	{
		//流水号递增
		if(blAdd){
			code++;
		}
		
		//格式化成3位流水号
		DecimalFormat df = new DecimalFormat(
				BusinessConstants.FORMAT_000);		
		String rtnCode = df.format(code);

		return rtnCode;
	}
	
	/**
	 * @param 流水号
	 * @param blAdd:是否要递增 true:要,false:不要
	 * @return 3位流水号格式化处理
	 */
	public static String getYSKFormat2Code(int id,boolean blAdd)
	{
		//
		String ys = getYSKCommCode();
		String yscode = getFormat2Code(id,blAdd);
		
		String ysfullcode = ys + yscode;

		return ysfullcode;
	}
	
	/*
	 * 2位流水号格式化处理
	 * blAdd:是否要递增
	 */
	private static String getFormat2Code(int code,boolean blAdd)
	{
		//流水号递增
		if(blAdd){
			code++;
		}
		
		//格式化成3位流水号
		DecimalFormat df = new DecimalFormat(
				BusinessConstants.FORMAT_00);		
		
		String rtnCode = df.format(code);

		return rtnCode;
	}
	
				
}
