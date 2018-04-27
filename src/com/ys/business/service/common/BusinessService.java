package com.ys.business.service.common;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.ys.system.common.BusinessConstants;
import com.ys.util.CalendarUtil;

public class BusinessService {
	
	
	/**
	 * @return 2位年份
	 */
	public static String getshortYearcode()
	{
		//当前的2位年份取得
		SimpleDateFormat sdf = new SimpleDateFormat(
				BusinessConstants.SHORTNAME_YEAR_YY);
	    Date date = new Date();
	    String nowYear = sdf.format(date);
	        
		return nowYear;
	}
	
	
	/**
	 * @return 耀升编号,2位年份+YS
	 */
	public static String getYSCommCode()
	{
		//两位年份
	    String nowYear = getshortYearcode();
	        
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
	 * @return 耀升编号,4位流水号格式化处理
	 */
	public static String getYSFormatCode(int id,boolean blAdd)
	{
		//
		String ys = getYSCommCode();
		String yscode = getFormat4Code(id,blAdd);
		
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
	 * 3位流水号格式化处理
	 * blAdd:是否要递增
	 */
	public static String getFormat4Code(int code,boolean blAdd)
	{
		//流水号递增
		if(blAdd){
			code++;
		}
		
		//格式化成3位流水号
		DecimalFormat df = new DecimalFormat(
				BusinessConstants.FORMAT_0000);		
		String rtnCode = df.format(code);

		return rtnCode;
	}

	/**
	 * 5位流水号格式化处理
	 * blAdd:是否要递增
	 */
	public static String getFormat5Code(int code,boolean blAdd)
	{
		//流水号递增
		if(blAdd){
			code++;
		}
		
		//格式化成5位流水号
		DecimalFormat df = new DecimalFormat(
				BusinessConstants.FORMAT_00000);		
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
	 * 3位流水号格式化处理
	 * blAdd:是否要递增
	 */
	public static String getFormat3Code(int code,boolean blAdd)
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
	
	public static String getContractCode(
			String YSId,String suppliersubid,String shortname)
	{

		int icode2 = Integer.parseInt(suppliersubid);
		//格式化成4位流水号,并且+1
		String num2 = getFormat4Code(icode2,false);
		
		//采购合同编号:18YS1234-WL0001
		return YSId + "-" + shortname + num2;
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
		rtn = BusinessConstants.ORDERBOM_BD + "."+tmp2 + "-"+BusinessConstants.FORMAT_00;
		
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
		//格式化成3位流水号,并且+1
		int num = 0;
		if(!(code ==null || ("").equals(code)))
			num = Integer.parseInt(code);
		
		String ft = BusinessService.getFormat3Code(num,true);
		
		return today + "-" + ft;
	 }
		

	 /**
	 * 进料检报告编号
	 * @return 16IQC月份-3位流水号
	 */
	 public static String getInspectionRecordId(
			 String parent,String code,boolean addFlag) {

		 StringBuffer sb = new StringBuffer();
	    sb.append(parent);
	    sb.append("-");
	    		
		//格式化成3位流水号,并且+1
		int num = 1;
		if(!(code ==null || ("").equals(code)))
			num = Integer.parseInt(code);
		sb.append( BusinessService.getFormat3Code(num,addFlag));
		
		return sb.toString();
	 }
	 

	 /**
	 * 进料检报告编号的父编号
	 * @return 16IQC月份
	 */
	 public static String getInspectionParentId() {

		 StringBuffer sb = new StringBuffer();
		//当前的2位年份取得
		SimpleDateFormat sdf = new SimpleDateFormat(
				BusinessConstants.SHORTNAME_YEAR_YY);
	    Date date = new Date();
	    sb.append(sdf.format(date));
	    sb.append(BusinessConstants.SHORTNAME_IQC);
	  //当前的2位月份取得	  	    
	    String month = new CalendarUtil().getMonthOfYear();
	    month = getFormat2Code(Integer.parseInt(month),false);
		sb.append(month);		
		
		return sb.toString();
	 }
	 
	 /**
	 * 入库单编号
	 * @return 17RK-00001 5位流水号
	 */
	 public static String getStorageRecordId(
			 String parent,String code,boolean addFlag) {

		StringBuffer sb = new StringBuffer();
		sb.append(parent);
	    sb.append("-");
	    		
		//格式化成5位流水号
		int num = 1;
		if(!(code ==null || ("").equals(code)))
			num = Integer.parseInt(code);
		sb.append(getFormat5Code(num,addFlag));
		
		return sb.toString();
	 }
		
	 
	 /**
	 * 出库单编号
	 * @return 17CK-00001 5位流水号
	 */
	 public static String getStockOutId(
			 String parent,String code,boolean addFlag) {

		StringBuffer sb = new StringBuffer();
	    sb.append(parent);
	    sb.append("-");
	    		
		//格式化成5位流水号
		int num = 1;
		if(!(code ==null || ("").equals(code)))
			num = Integer.parseInt(code);
		sb.append(getFormat5Code(num,addFlag));
		
		return sb.toString();
	 }
	 
	 /**
	 * 付款单编号
	 * @return 17FK-00001 5位流水号
	 */
	 public static String getPaymentId(
			 String parent,String code,boolean addFlag) {

		StringBuffer sb = new StringBuffer();
	    sb.append(parent);
	    sb.append("-");
	    		
		//格式化成5位流水号
		int num = 1;
		if(!(code ==null || ("").equals(code)))
			num = Integer.parseInt(code);
		sb.append(getFormat5Code(num,addFlag));
		
		return sb.toString();
	 }
	 
	 
	 /**
	 * 付款编号=付款单编号-2位流水号
	 * @return 17FK-00001-01 
	 */
	 public static String getPaymentHistoryId(
			 String parent,String code,boolean addFlag) {

		StringBuffer sb = new StringBuffer();
	    sb.append(parent);
	    sb.append("-");
	    		
		//格式化成5位流水号
		int num = 1;
		if(!(code ==null || ("").equals(code)))
			num = Integer.parseInt(code);
		sb.append(getFormat2Code(num,addFlag));
		
		return sb.toString();
	 }
	 
	 /**
		 * @return 做单资料编号
		 * 
		 */
	public static String getProductDesignDetailId(String code1,String code2 )
	{
		//格式化成2位流水号,并且+1
		//String ft = BusinessService.getFormat3Code(code2,blAdd);
		
		//
		return code1 + "-"+code2;
	}
	
	 /**
	 * @return 日常采购的耀升编号
	 * 
	 */
	public static String getRoutinePurchaseYsid()
	{
		//格式化成2位流水号,并且+1
		//String ft = BusinessService.getFormat3Code(code2,blAdd);
		
		//
		return getYSCommCode() + "000";
	}
	 
	 /**
		 * 自制品任务编号
		 * @return 17RW-00001 5位流水号
		 */
	 public static String getProductionTaskId(String parentId,String code) {

		int num = 0;
		if(!(code ==null || ("").equals(code)))
			num = Integer.parseInt(code);
		
		String ft = BusinessService.getFormat5Code(num,false);
		
		return  parentId + "-" + ft;
	 }
	 
	/**
	 * 领料单编号
	 * @return 17LL-00001 5位流水号
	 */
	 public static String getRequisitionId(String parentId,String code) {

		int num = 0;
		if(!(code ==null || ("").equals(code)))
			num = Integer.parseInt(code);
		
		String ft = BusinessService.getFormat5Code(num,false);
		
		return  parentId + "-" +ft;
	 }

	/**
	 * 退货单编号
	 * @return 合同编号-5位流水号
	 */
	 public static String getInspectionReturnId(String contractId,String code,boolean flag) {

		//格式化成2位流水号,并且+1
		int num = 0;
		if(!(code ==null || ("").equals(code)))
			num = Integer.parseInt(code);
		
		String ft = BusinessService.getFormat5Code(num,flag);
		
		return contractId+"-"+ ft;
	 }
			

	/**
	 * @return 入库申请编号
	 * 
	 */
	public static String getStockInApplyId(String parentId,int code2,boolean blAdd )
	{
		//格式化成2位流水号,并且+1
		String ft = BusinessService.getFormat3Code(code2,blAdd);
		
		//
		return parentId + "-" + ft;
	}	
		
}
