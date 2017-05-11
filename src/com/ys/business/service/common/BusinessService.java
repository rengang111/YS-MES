package com.ys.business.service.common;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.ys.business.db.data.CommFieldsData;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.util.CalendarUtil;
import com.ys.util.basequery.common.Constants;

public class BusinessService {
	
	
	/**
	 * @return 耀升编号,2位年份+YS
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
	 * @return 耀升编号,3位流水号格式化处理
	 */
	public static String getYSFormatCode(int id,boolean blAdd)
	{
		//
		String ys = getYSCommCode();
		String yscode = getFormatCode(id,blAdd);
		
		String ysfullcode = ys + yscode;

		return ysfullcode;
	}
	
	
	/**
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
	
	/**
	 * 2位流水号格式化处理
	 * blAdd:是否要递增
	 */
	public static String getFormat2Code(int code,boolean blAdd)
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
	
	/**
	 * @return 采购合同编号
	 * @param code1:耀升编号
	 * @param code2:供应商简称
	 * @param code3:流水号
	 * 
	 */
	public static String getContractCode(String code1,String code2,String code3)
	{
		int icode = Integer.parseInt(code3);
		//格式化成3位流水号,并且+1
		String num = getFormatCode(icode,true);
		
		//采购合同编号:16YS081-WL002
		return code1 + "-" + code2 + num;
	}
	
	/**
	 * @return 自制库存订单编号
	 * 
	 */
	public static String getOrderIdZZ(int code,boolean blAdd )
	{
		//格式化成2位流水号,并且+1
		String code1 = BusinessService.getYSKFormat2Code(code,blAdd);
		
		//自制库存订单编号:16YSK01ZZ
		return code1 + BusinessConstants.SHORTNAME_ZZ ;
	}
	
	/**
	 * @return 装配库存订单编号
	 * 
	 */
	public static String getOrderIdZP(int code,boolean blAdd )
	{
		//格式化成2位流水号,并且+1
		String code1 = BusinessService.getYSKFormat2Code(code,blAdd);
		
		//装配库存订单编号:16YSK01ZP
		return code1 + BusinessConstants.SHORTNAME_ZP ;
	}
	
	/**
	 * @return BOM编号
	 * 
	 */
	public static String getBOMFormatId(String code1,int code2,boolean blAdd )
	{
		//格式化成3位流水号,并且+1
		String ft = BusinessService.getFormatCode(code2,blAdd);
		
		//
		return code1 + "." +  ft;
	}

	/**
	 * @return BaseBOM编号:BM.IW03.WTR001.00-00
	 * @param I.IW03.WTR001.00
	 */
	public static String[] getBaseBomId(String code1 )
	{
		String[] rtn = new String[2];
		String tmp2 = code1.substring(2);
		//
		rtn[0] = BusinessConstants.BASEBOM_BM + "."+tmp2;
		rtn[1] = BusinessConstants.BASEBOM_BM + "."+tmp2 + "-"+BusinessConstants.FORMAT_00;
		return rtn;
	}	
	
	
	/**
	 * @return 报价BOM编号
	 * 
	 */
	public static String getBidBOMFormatId(String code1,int code2,boolean blAdd )
	{
		//格式化成2位流水号,并且+1
		String ft = BusinessService.getFormat2Code(code2,blAdd);
		
		String tmp2 = code1.substring(3);
		//
		return BusinessConstants.BASEBOM_BM + "."+tmp2 + "-"+ft;
	}

	/**
	 * @return 首次创建订单BOM编号
	 * 
	 */
	public static String getOrderBOMParentId(String code1)
	{
		String rtn = "";
		
		String tmp2 = code1.substring(2);
		//
		rtn = BusinessConstants.ORDERBOM_BD +tmp2 ;
		
		return rtn;
	}
	

	/**
	 * @return 取得订单BOM编号(流水号)
	 * 
	 */
	public static String getOrderBOMFormatId(String code1,int code2,boolean blAdd )
	{
		String rtn = "";
		//格式化成3位流水号,并且+1
		String ft = BusinessService.getFormat2Code(code2,blAdd);
		
		String tmp2 = code1.substring(2);
		//
		rtn = BusinessConstants.ORDERBOM_BD + "."+tmp2 + "-"+ft;
		
		return rtn;
	}

	/**
	 * @return 取得订单BOM编号(流水号)
	 * 
	 */
	public static String getOrderBOMParentId(String code1,int code2 )
	{
		String rtn = "";
		
		String tmp2 = code1.substring(2);
		//
		rtn = BusinessConstants.ORDERBOM_BD + "."+tmp2 ;
		
		return rtn;
	}
	/**
	 * 
	 */
	 public static String format2Decimal(float value) {
		 return String.valueOf(value);
         //DecimalFormat df = new DecimalFormat("#.00");
		//return df.format(value);
	 }
	 

	/**
	 * 到货登记编号
	 * @return yyMMdd-nn
	 */
	 public static String getArriveRecordId(String code) {

		String today = CalendarUtil.getDateyymmdd();
		//格式化成2位流水号,并且+1
		int num = 0;
		if(!(code ==null || ("").equals(code)))
			num = Integer.parseInt(code);
		
		String ft = BusinessService.getFormat2Code(num,true);
		
		return today + "-" + ft;
	 }
		 
		 
	 
}
