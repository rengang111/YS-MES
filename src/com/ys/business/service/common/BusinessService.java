package com.ys.business.service.common;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.ys.business.action.model.common.TableFields;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.util.CalendarUtil;
import com.ys.util.basequery.common.Constants;

public class BusinessService {
	
	//表字段的共通编辑方法
	public static TableFields updateModifyInfo(
			int type,
			String method,
			UserInfo userInfo) {
		
		TableFields data = new  TableFields();
		data.setFormid(method);
		
		if (type == Constants.ACCESSTYPE_INS) {//insert
			data.setCreateperson(userInfo.getUserId());
			data.setCreatetime(CalendarUtil.fmtDate());
			data.setCreateunitid(userInfo.getUnitId());

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
	
	/*
	 * 
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
	

	/*
	 * 3位流水号格式化处理
	 * blAdd:是否要递增
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
	public static String getFormatCode(int code,boolean blAdd)
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
	
				
}
