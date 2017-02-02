package com.ys.system.service.common;

public interface I_BaseService {	
	public void setNowUseImage(String key, String fileName) throws Exception;
	public String getNowUseImage(String key) throws Exception;
}