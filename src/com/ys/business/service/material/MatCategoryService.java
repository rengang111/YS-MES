package com.ys.business.service.material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ys.system.action.model.login.UserInfo;
import com.ys.business.action.model.material.MatCategoryModel;
import com.ys.business.db.dao.B_MaterialCategoryDao;
import com.ys.business.db.data.B_MaterialCategoryData;
import com.ys.business.db.data.B_ZZMaterialPriceData;
import com.ys.business.db.data.CommFieldsData;
import com.ys.util.basequery.common.BaseModel;
import com.ys.util.basequery.common.Constants;
import com.ys.system.interceptor.CommonDept;
import com.ys.system.interceptor.DicInfo;
import com.ys.system.service.common.BaseService;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basequery.BaseQuery;
import com.ys.business.ejb.BusinessDbUpdateEjb;
import javax.servlet.http.HttpServletRequest;

@Service
public class MatCategoryService extends BaseService {
	
	private CommFieldsData commData;
	
	public MatCategoryModel doSearch(HttpServletRequest request, MatCategoryModel dataModel, UserInfo userInfo)
			throws Exception {

		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		dataModel.setQueryFileName("/business/material/matclassquerydefine");
		dataModel.setQueryName("matclassquerydefine_search");


		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		//if (userInfo.isSA()) {
			userDefinedSearchCase.put("categoryId", "");
		//} else {
		//	userDefinedSearchCase.put("userUnitId", userInfo.getUnitId());
		//}
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryDataList(0, 0);

		

		return dataModel;
	}

	public MatCategoryModel getDetail(HttpServletRequest request) throws Exception {
		String recordId = request.getParameter("recordId");
		MatCategoryModel categoryModel = new MatCategoryModel();


		B_MaterialCategoryDao dao = new B_MaterialCategoryDao();
		B_MaterialCategoryData data = new B_MaterialCategoryData();
		data.setRecordid(recordId);
		data = (B_MaterialCategoryData) dao.FindByPrimaryKey(data);

		categoryModel.setunitData(data);

		categoryModel.setIsOnlyView("T");

		return categoryModel;

	}

	public MatCategoryModel getParentDeptName(HttpServletRequest request) throws Exception {

		String parentId = request.getParameter("categoryId");
		String parentName = request.getParameter("categoryName");
		parentName = new String(parentName.getBytes("ISO8859-1"), "UTF-8");

		MatCategoryModel MatCategoryModel = new MatCategoryModel();
		MatCategoryModel.setParentCategoryId(parentId.trim());
		MatCategoryModel.setParentCategoryName(parentName);

		return MatCategoryModel;

	}

	@SuppressWarnings("unchecked")
	public void insert(HttpServletRequest request, 
			MatCategoryModel MatCategoryModel,
			UserInfo userInfo) throws Exception {
		
		B_MaterialCategoryDao dao = new B_MaterialCategoryDao();
		B_MaterialCategoryData data = eidtChildId(MatCategoryModel.getunitData());
		List<B_MaterialCategoryData> list = null;

		//物料编码存在check
		String categoryId = data.getCategoryid();
		String astr_Where = " categoryid = '" + categoryId + "' AND deleteFlag='0' ";
		list = (List<B_MaterialCategoryData>)dao.Find(astr_Where);
		
		if(list.size() > 0){
			//编码重复时,更新处理
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,"MaterialCategoryUpdate",userInfo);
			copyProperties(data,commData);
			data.setRecordid(list.get(0).getRecordid());//key保留既存数据的,其他内容从页面重新赋值
			
			dao.Store(data);
			
		}else{
			//正常情况,追加处理
			commData = commFiledEdit(Constants.ACCESSTYPE_INS,"MaterialCategoryInsert",userInfo);
			copyProperties(data,commData);

			data.setRecordid(BaseDAO.getGuId());
			data.setCategoryid(data.getCategoryid().trim());
			data.setParentid(request.getParameter("parentCategoryId"));

			dao.Create(data);			
		}
	}
	
	private B_MaterialCategoryData eidtChildId (B_MaterialCategoryData data){
		
		String categroyId = data.getCategoryid()+"";
		String parentId=data.getParentid()+"";
		
		if (parentId.equals(Constants.MATERIALCATEGORY)){
			data.setChildid(categroyId);
		}else{		
			if(categroyId.length() !=0 && parentId.trim().length() !=0)
			{
				String childid = categroyId.substring(parentId.trim().length());
				int inChar=childid.indexOf(".");
				if(inChar > -1)
				{
					childid = childid.substring(inChar + 1);
				}
				data.setChildid(childid);
				System.out.println("=======================childid================="+childid);
					
			}			
		}
		return data;
	}
	
	public void update(HttpServletRequest request, 
			MatCategoryModel MatCategoryModel, 
			UserInfo userInfo) throws Exception {
		
		B_MaterialCategoryDao dao = new B_MaterialCategoryDao();
		B_MaterialCategoryData data = eidtChildId(MatCategoryModel.getunitData());
				
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,"MaterialCategoryUpdate",userInfo);

		copyProperties(data,commData);
		dao.Store(data);
	}

	public void delete(MatCategoryModel MatCategoryModel, UserInfo userInfo) throws Exception {

		BusinessDbUpdateEjb bean = new BusinessDbUpdateEjb();

		bean.executeMaterialCategroyDelete(MatCategoryModel.getNumCheck(), userInfo);

	}

	public int preCheck(HttpServletRequest request, String operType, String parentUnitId, String userUnitId,
			String userType) throws Exception {

		int result = 0;

		MatCategoryModel MatCategoryModel = new MatCategoryModel();

		MatCategoryModel.setQueryFileName("/business/material/matclassquerydefine");
		MatCategoryModel.setQueryName("unitquerydefine_getparentdeptguid");

		BaseQuery baseQuery = new BaseQuery(request, MatCategoryModel);
		ArrayList<ArrayList<String>> unitDataList = baseQuery.getQueryData();
		// 父单位是否存在
		if (unitDataList.size() == 0) {
			if (!parentUnitId.equals("")) {
				result = 1;
			}
		} else {
			if (!userType.equals(Constants.USER_SA)) {
				String parentId = unitDataList.get(0).get(1);
				if (parentId.length() >= userUnitId.length()) {
					if (!parentId.substring(0, userUnitId.length()).equals(userUnitId)) {
						// 父单位无权限
						result = 2;
					}
				} else {
					// 父单位无权限
					result = 2;
				}
			}

			// 单位名称是否存在
			HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
			userDefinedSearchCase.put("unitIdName", request.getParameter("unitData.unitname"));
			userDefinedSearchCase.put("userUnitId", userUnitId);
			MatCategoryModel.setQueryFileName("/business/material/matclassquerydefine");
			MatCategoryModel.setQueryName("unitquerydefine_confirmunitname");
			baseQuery = new BaseQuery(request, MatCategoryModel);
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
			unitDataList = baseQuery.getQueryData();
			if (operType.equals("add") || operType.equals("addsub")) {
				if (unitDataList.size() > 0) {
					// 单位名称已存在
					result = 3;
				}
			} else {
				if (unitDataList.size() == 0) {
					// 单位名称不存在
					// result = 4;
				} else {
					String updateSrcUnitId = request.getParameter("unitData.unitid");
					if (!unitDataList.get(0).get(1).equals(updateSrcUnitId)) {
						// 单位名称已存在
						result = 3;
					}
				}
			}
		}

		return result;
	}


	

	public ArrayList<DicInfo> getInitMaterial(HttpServletRequest request, String userId, String menuId,
			String unitId, String userType) throws Exception {

		ArrayList<ArrayList<DicInfo>> deptChain = InitMaterial(request, userId, menuId, unitId, userType);

		return CommonDept.sortDept(deptChain);
	}

	private ArrayList<ArrayList<DicInfo>> InitMaterial(HttpServletRequest request, String userId, String menuId,
			String unitId, String userType) throws Exception {
		ArrayList<ArrayList<DicInfo>> deptChain = new ArrayList<ArrayList<DicInfo>>();
		ArrayList<ArrayList<String>> dbResult = new ArrayList<ArrayList<String>>();

			dbResult = getAllMaterial(request, userId, menuId, unitId, userType);

		for (ArrayList<String> rowData : dbResult) {
			ArrayList<DicInfo> deptSubChain = new ArrayList<DicInfo>();

			DicInfo deptInfo = new DicInfo();
			deptInfo.setId(rowData.get(0));
			deptInfo.setName(rowData.get(1));
			deptInfo.setDes(rowData.get(2));
			//deptInfo.setJianpin(rowData.get(3));
			deptInfo.setParentId(rowData.get(3));
			deptInfo.setDeptGuid(rowData.get(4));
			//deptInfo.setSortNo(rowData.get(6));

			// getDeptChain(deptInfo, deptSubChain, dbResult, deptMap);
			deptSubChain.add(deptInfo);
			deptChain.add(deptSubChain);
		}

		return deptChain;
	}

	public ArrayList<ArrayList<String>> getAllMaterial(HttpServletRequest request, String userId, String menuId,
			String unitId, String userType) throws Exception {
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		BaseQuery baseQuery;
		BaseModel baseModel = new BaseModel();
	
		baseModel.setQueryFileName("/business/material/matclassquerydefine");
		baseModel.setQueryName("mainframequery_getallmaterials");
		baseQuery = new BaseQuery(request, baseModel);
	
		result = baseQuery.getFullData();

		return result;
	}

	public ArrayList<DicInfo> launchMaterial(HttpServletRequest request, String userId, String menuId, String categoryId,
			String userType) throws Exception {

		ArrayList<DicInfo> leafDept = new ArrayList<DicInfo>();
		ArrayList<ArrayList<String>> dbResult = new ArrayList<ArrayList<String>>();
	
		dbResult = getLeafDept(request,categoryId);

		for (ArrayList<String> rowData : dbResult) {
			DicInfo deptInfo = new DicInfo();
			deptInfo.setId(rowData.get(0));
			deptInfo.setName(rowData.get(1));
			deptInfo.setDes(rowData.get(2));
			//deptInfo.setJianpin(rowData.get(3));
			deptInfo.setParentId(rowData.get(3));
			deptInfo.setDeptGuid(rowData.get(4));

			leafDept.add(deptInfo);
		}

		return leafDept;
	}

	public ArrayList<ArrayList<String>> getLeafDept(
			HttpServletRequest request,String categoryId) throws Exception {
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		BaseQuery baseQuery;
		BaseModel baseModel = new BaseModel();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();

		baseModel.setQueryFileName("/business/material/matclassquerydefine");
		//baseModel.setQueryName("mainframequery_getleafdept_admin");
		baseModel.setQueryName("mainframequery_getCategoryByParentId");
		baseQuery = new BaseQuery(request, baseModel);
		userDefinedSearchCase.put("parentid", categoryId);

		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);

		result = baseQuery.getFullData();

		return result;

	}
}
