package com.ys.business.action.model.mouldregister;

import com.ys.util.basequery.common.BaseModel;

import net.sf.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ys.business.action.model.common.ListOption;
import com.ys.business.db.data.B_CustomerAddrData;
import com.ys.business.db.data.B_CustomerData;
import com.ys.business.db.data.B_ExternalSampleData;
import com.ys.business.db.data.B_LatePerfectionQuestionData;
import com.ys.business.db.data.B_LatePerfectionRelationFileData;
import com.ys.business.db.data.B_MouldFactoryData;
import com.ys.business.db.data.B_MouldBaseInfoData;
import com.ys.business.db.data.B_MouldSubData;
import com.ys.business.db.data.B_MouldPayInfoData;
import com.ys.business.db.data.B_MouldPayListData;
import com.ys.business.db.data.B_ProcessControlData;
import com.ys.business.db.data.B_ProjectTaskCostData;
import com.ys.business.db.data.B_ProjectTaskData;
import com.ys.business.db.data.B_ReformLogData;
import com.ys.system.db.data.S_DICData;

public class MouldRegisterModel extends BaseModel {
	private final String className = "com.ys.business.service.mouldcontract.MouldRegisterService";
	private String keyBackup = "";
	private B_MouldBaseInfoData mouldBaseInfoData = new B_MouldBaseInfoData();
	private ArrayList<HashMap<String, String>> mouldSubDatas = new ArrayList<HashMap<String, String>>();
	private ArrayList<HashMap<String, String>> mouldFactoryDatas = new ArrayList<HashMap<String, String>>();

	private List<B_MouldFactoryData> detailLines;
	
	private String productModelId = "";
	private String productModelIdView = "";
	private String productModelName = "";
	private String mouldFactoryId = "";
	private String mouldFactoryName = "";
	private String type = "";
	
	private ArrayList<ListOption> typeList = new ArrayList<ListOption>();
	private ArrayList<ListOption> mouldFactoryList = new ArrayList<ListOption>();
	
	private String imageFileName = "";
	private String[] filenames;
	private String imageKey = "";
	private String path = "";
	private String nowUseImage;
	
	public String getClassName() {
		return this.className;
	}
	
	public String getKeyBackup() {
		
		return this.keyBackup;
	}
	public void setKeyBackup(String keyBackup) {
		this.keyBackup = keyBackup;
	}
	
	public B_MouldBaseInfoData getMouldBaseInfoData() {
		return this.mouldBaseInfoData;
	}
	public void setMouldBaseInfoData(B_MouldBaseInfoData mouldBaseInfoData) {
		this.mouldBaseInfoData = mouldBaseInfoData;
	}

	public ArrayList<HashMap<String, String>> getMouldSubDatas() {
		return this.mouldSubDatas;
	}
	public void setMouldSubDatas(ArrayList<HashMap<String, String>> mouldSubData) {
		this.mouldSubDatas = mouldSubDatas;
	}	
	
	public ArrayList<HashMap<String, String>> getMouldFactoryDatas() {
		return this.mouldFactoryDatas;
	}
	public void setMouldFactoryDatas(ArrayList<HashMap<String, String>> mouldFactoryDatas) {
		this.mouldFactoryDatas = mouldFactoryDatas;
	}	
	public String getProductModelId() {
	    return this.productModelId;
	}
	public void setProductModelId(String productModelId) {
	    this.productModelId = productModelId;
	}
	public String getProductModelIdView() {
	    return this.productModelIdView;
	}
	public void setProductModelIdView(String productModelIdView) {
	    this.productModelIdView = productModelIdView;
	}
	public String getProductModelName() {
	    return this.productModelName;
	}
	public void setProductModelName(String productModelName) {
	    this.productModelName = productModelName;
	}
	public String getMouldFactoryId() {
	    return this.mouldFactoryId;
	}
	public void setMouldFactoryId(String mouldFactoryId) {
	    this.mouldFactoryId = mouldFactoryId;
	}
	public String getMouldFactoryName() {
	    return this.mouldFactoryName;
	}
	public void setMouldFactoryName(String mouldFactoryName) {
	    this.mouldFactoryName = mouldFactoryName;
	}
	public String getType() {
	    return this.type;
	}
	public void setType(String type) {
	    this.type = type;
	}
	public ArrayList<ListOption> getTypeList() {
		return this.typeList;
	}
	public void setTypeList(ArrayList<ListOption> typeList) {
		this.typeList = typeList;
	}
	public ArrayList<ListOption> getMouldFactoryList() {
		return this.mouldFactoryList;
	}
	public void setMouldFactoryList(ArrayList<ListOption> mouldFactoryList) {
		this.mouldFactoryList = mouldFactoryList;
	}
	
	public List<B_MouldFactoryData> getDetailLines() {
		return this.detailLines;
	}
	public void setDetailLines(List<B_MouldFactoryData> detailLines) {
		this.detailLines = detailLines;
	}

	public String getImageFileName() {
		return this.imageFileName;
	}
	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}
	
	public String[] getFilenames() {
		return this.filenames;
	}
	public void setFilenames(String[] filenames) {
		this.filenames = filenames;
	}
	
	public String getImageKey() {
		return this.imageKey;
	}
	public void setImageKey(String imageKey) {
		this.imageKey = imageKey;
	}
	
	public String getPath() {
		return this.path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getNowUseImage() {
		return this.nowUseImage;
	}
	public void setNowUseImage(String nowUseImage) {
		this.nowUseImage = nowUseImage;
	}
}
