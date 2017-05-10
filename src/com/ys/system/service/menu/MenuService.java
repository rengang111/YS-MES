package com.ys.system.service.menu;

import org.springframework.stereotype.Service;

import com.ys.system.action.model.login.UserInfo;
import com.ys.system.action.model.menu.MenuModel;
import com.ys.system.common.BusinessConstants;
import com.ys.system.db.dao.S_MENUDao;
import com.ys.system.db.data.S_MENUData;
import com.ys.system.ejb.DbUpdateEjb;
import com.ys.system.service.common.BaseService;
import com.ys.util.CalendarUtil;
import com.ys.util.DicUtil;
import com.ys.util.basequery.BaseQuery;

import java.util.HashMap;

import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;

@Service
public class MenuService extends BaseService {
 
	public HashMap<String, Object> doSearch(HttpServletRequest request, String data) throws Exception {
		int iStart = 0;
		int iEnd =0;
		String sEcho = "";
		String start = "";
		String length = "";
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		MenuModel dataModel = new MenuModel();
		
		sEcho = getJsonData(data, "sEcho");	
		start = getJsonData(data, "iDisplayStart");		
		if (start != null && !start.equals("")){
			iStart = Integer.parseInt(start);			
		}
		
		length = getJsonData(data, "iDisplayLength");
		if (length != null && !length.equals("")){			
			iEnd = iStart + Integer.parseInt(length);			
		}		
		
		dataModel.setMenuTypeList(DicUtil.getGroupValue(DicUtil.MENUTYPE));
		
		dataModel.setQueryFileName("/menu/menuquerydefine");
		dataModel.setQueryName("menuquerydefine_search");
		
		String key1 = getJsonData(data, "menuId");
		String key2 = getJsonData(data, "menuName");
		String key3 = getJsonData(data, "parentMenuIdName");
		
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("menuId", key1);
		userDefinedSearchCase.put("menuName", key2);
		userDefinedSearchCase.put("parentMenuIdName", key2);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(iStart, iEnd);	
		
		if ( iEnd > dataModel.getYsViewData().size()){
			iEnd = dataModel.getYsViewData().size();
		}
		
		modelMap.put("sEcho", sEcho); 
		
		modelMap.put("recordsTotal", dataModel.getRecordCount()); 
		
		modelMap.put("recordsFiltered", dataModel.getRecordCount());
		
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
	}

	public MenuModel getDetail(HttpServletRequest request) throws Exception {
		String menuId = request.getParameter("menuId");
		MenuModel menuModel = new MenuModel();
		
		menuModel.setMenuTypeList(DicUtil.getGroupValue(DicUtil.MENUTYPE));
		
		S_MENUDao dao = new S_MENUDao();
		S_MENUData data = new S_MENUData();
		data.setMenuid(menuId);
		data = (S_MENUData)dao.FindByPrimaryKey(data);
		
		menuModel.setmenuData(data);
		menuModel.setIsOnlyView("T");
		
		return menuModel;
	
	}
	
	public MenuModel doUpdate(String formData, UserInfo userInfo, boolean isAdd) {
		S_MENUDao dao = new S_MENUDao();
		S_MENUData data = new S_MENUData();
		MenuModel model = new MenuModel();
		
		String operType = getJsonData(formData, "operType");
		String menuparentid = getJsonData(formData, "menuparentid");
		String menuid = getJsonData(formData, "menuid");
		String menuname = getJsonData(formData, "menuname");
		String menudes = getJsonData(formData, "menudes");
		String menuurl = getJsonData(formData, "menuurl");
		String menutype = getJsonData(formData, "menutype");
		String menuviewflag = getJsonData(formData, "menuviewflag");
		String menunnableflag = getJsonData(formData, "menunnableflag");
		String relationalmenuid = getJsonData(formData, "relationalmenuid");
		String menuwfnode = getJsonData(formData, "menuwfnode");
		String menuicon1 = getJsonData(formData, "menuicon1");
		String menuicon2 = getJsonData(formData, "menuicon2");
		String sortno = getJsonData(formData, "sortno");
		String menuopenflag = getJsonData(formData, "menuopenflag");
		
		try {
			
			data.setMenuid(menuid);
			if (!isAdd) {
				data = (S_MENUData)dao.FindByPrimaryKey(data);
			}
			data.setMenuparentid(menuparentid);
			data.setMenuname(menuname);
			data.setMenudes(menudes);
			data.setMenuurl(menuurl);
			data.setMenutype(menutype);
			data.setMenuviewflag(menuviewflag);
			data.setMenunnableflag(menunnableflag);
			data.setRelationalmenuid(relationalmenuid);
			data.setMenuwfnode(menuwfnode);
			data.setMenuicon1(menuicon1);
			data.setMenuicon2(menuicon2);
			if (sortno != null && !sortno.equals("")) {
				data.setSortno(Integer.parseInt(sortno));
			} else {
				data.setSortno(0);
			}
			data.setMenuopenflag(menuopenflag);
			data = updateModifyInfo(data, userInfo);
			if (data.getMenuopenflag() == null || data.getMenuopenflag().equals("")) {
				data.setMenuopenflag("F");
			}
			if (!isAdd) {
				dao.Store(data);
			} else {
				dao.Create(data);
			}
			
			model.setEndInfoMap(BaseService.NORMAL, "", menuparentid);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(BaseService.SYSTEMERROR, "", "");
		}
		
		return model;
	}
	
	public MenuModel doDelete(HttpServletRequest request, String formData, UserInfo userInfo) {
		
		DbUpdateEjb bean = new DbUpdateEjb();
		MenuModel model = new MenuModel();
		
		try {
			bean.executeMenuDelete(formData, userInfo);
			model.setEndInfoMap(BaseService.NORMAL, "", "");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(BaseService.SYSTEMERROR, "", "");
		}
		
		return model;
		
        
	}
	
	public boolean isRightMenuId(String menuId, String operType) {
		
		boolean result = true;
		
		S_MENUDao dao = new S_MENUDao();
		S_MENUData data = new S_MENUData();
		data.setMenuid(menuId);
		
		try {
			data = (S_MENUData)dao.FindByPrimaryKey(data);
			if (operType.equals("add") || operType.equals("addsub")) {
				result = false;
			} else {
				result = true;
			}
		}
		catch(Exception e) {

		}

		return result;
	}
	
	public static S_MENUData updateModifyInfo(S_MENUData data, UserInfo userInfo) {
		String createUserId = data.getCreateperson();
		if ( createUserId == null || createUserId.equals("")) {
			data.setCreateperson(userInfo.getUserId());
			data.setCreatetime(CalendarUtil.fmtDate());
			data.setCreateunitid(userInfo.getUnitId());
		}
		data.setModifyperson(userInfo.getUserId());
		data.setModifytime(CalendarUtil.fmtDate());
		data.setDeleteflag(BusinessConstants.DELETEFLG_UNDELETE);
		
		return data;
	}
}
