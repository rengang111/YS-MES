package com.ys.system.service.menu;

import org.springframework.stereotype.Service;

import com.ys.system.action.model.login.UserInfo;
import com.ys.system.action.model.menu.MenuModel;
import com.ys.system.common.BusinessConstants;
import com.ys.system.db.dao.S_MENUDao;
import com.ys.system.db.data.S_MENUData;
import com.ys.system.ejb.DbUpdateEjb;
import com.ys.util.CalendarUtil;
import com.ys.util.DicUtil;
import com.ys.util.basequery.BaseQuery;
import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;

@Service
public class MenuService {
 
	public MenuModel doSearch(HttpServletRequest request, MenuModel dataModel) throws Exception {
		
		dataModel.setMenuTypeList(DicUtil.getGroupValue(DicUtil.MENUTYPE));
		
		dataModel.setQueryFileName("/menu/menuquerydefine");
		dataModel.setQueryName("menuquerydefine_search");
		
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		baseQuery.getQueryData();	
		
		return dataModel;
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

	public void doAdd(MenuModel menuModel, UserInfo userInfo) throws Exception {
		S_MENUDao dao = new S_MENUDao();
		
		S_MENUData data = menuModel.getmenuData();
		data = updateModifyInfo(menuModel.getmenuData(), userInfo);
		if (data.getMenuopenflag() == null || data.getMenuopenflag().equals("")) {
			data.setMenuopenflag("F");
		}
		dao.Create(data);
	}	
	
	public void doUpdate(MenuModel menuModel, UserInfo userInfo) throws Exception {
		S_MENUDao dao = new S_MENUDao();
		S_MENUData data = updateModifyInfo(menuModel.getmenuData(), userInfo);
		if (data.getMenuopenflag() == null || data.getMenuopenflag().equals("")) {
			data.setMenuopenflag("F");
		}
		dao.Store(data);
	}
	
	public void doDelete(HttpServletRequest request, MenuModel menuModel, UserInfo userInfo) throws Exception {
		
		DbUpdateEjb bean = new DbUpdateEjb();
        
        bean.executeMenuDelete(menuModel, userInfo);
        
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
