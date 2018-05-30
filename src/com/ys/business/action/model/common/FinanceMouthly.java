package com.ys.business.action.model.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.ys.util.CalendarUtil;

public class FinanceMouthly {
	
	private String startDate;
	private String endDate;	
	public String getStartDate() {
		return startDate;
	}	
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}	
	
	public FinanceMouthly(){
	}
	public FinanceMouthly(String mouth) throws ParseException{
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = formatter.parse(mouth);
		Calendar c = Calendar.getInstance();//
		c.setTime(date);
		
		//上月25号
		c.add(Calendar.MONTH, -1);//今天的时间月份-1支持1月的上月
		c.set(Calendar.DAY_OF_MONTH, 25);//设置上月25号
		startDate = formatter.format(c.getTime());
		
		//当月26号
		c.clear();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, 26);//设置当月26号
		endDate = formatter.format(c.getTime());
		
	}

}
