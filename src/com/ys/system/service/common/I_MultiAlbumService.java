package com.ys.system.service.common;

public interface I_MultiAlbumService {	
	//public void addMultiAlbumData(String projectId, String folderName, String fileName) throws Exception;
	public String getNowUseImage(String key, int index);
	public void setNowUseImage(String key, int albumCount, int index, String src) throws Exception;
}