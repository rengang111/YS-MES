package com.ys.business.ejb;

import java.util.ArrayList;
import java.util.HashMap;

/*
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.transaction.UserTransaction;
*/
//import javax.security.jacc.PolicyContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ys.business.db.dao.B_ContactDao;
import com.ys.business.db.dao.B_CustomerAddrDao;
import com.ys.business.db.dao.B_CustomerDao;
import com.ys.business.db.dao.B_ESRelationFileDao;
import com.ys.business.db.dao.B_ExternalSampleDao;
import com.ys.business.db.dao.B_OrganBasicInfoDao;
import com.ys.business.db.dao.B_SupplierBasicInfoDao;
import com.ys.business.db.data.B_ContactData;
import com.ys.business.db.data.B_CustomerAddrData;
import com.ys.business.db.data.B_CustomerData;
import com.ys.business.db.data.B_ESRelationFileData;
import com.ys.business.db.data.B_ExternalSampleData;
import com.ys.business.db.data.B_OrganBasicInfoData;
import com.ys.business.db.data.B_SupplierBasicInfoData;
import com.ys.business.service.contact.ContactService;
import com.ys.business.service.customer.CustomerService;
import com.ys.business.service.customeraddr.CustomerAddrService;
import com.ys.business.service.esrelationfile.EsRelationFileService;
import com.ys.business.service.externalsample.ExternalSampleService;
import com.ys.business.service.organ.OrganService;
import com.ys.business.service.supplier.SupplierService;
import com.ys.system.action.model.dic.DicModel;
import com.ys.system.action.model.dic.DicTypeModel;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.action.model.menu.MenuModel;
import com.ys.system.action.model.power.PowerModel;
import com.ys.system.action.model.role.RoleModel;
import com.ys.system.action.model.unit.UnitModel;
import com.ys.system.action.model.user.UserModel;
import com.ys.system.common.BusinessConstants;
import com.ys.system.db.dao.S_DICDao;
import com.ys.system.db.dao.S_DICTYPEDao;
import com.ys.system.db.dao.S_MENUDao;
import com.ys.system.db.dao.S_OPERLOGDao;
import com.ys.system.db.dao.S_POWERDao;
import com.ys.system.db.dao.S_ROLEDao;
import com.ys.system.db.dao.S_ROLEMENUDao;
import com.ys.system.db.dao.S_USERDao;
import com.ys.system.db.data.S_DEPTData;
import com.ys.system.db.data.S_DICData;
import com.ys.system.db.data.S_DICTYPEData;
import com.ys.system.db.data.S_MENUData;
import com.ys.system.db.data.S_OPERLOGData;
import com.ys.system.db.data.S_POWERData;
import com.ys.system.db.data.S_ROLEData;
import com.ys.system.db.data.S_ROLEMENUData;
import com.ys.system.db.data.S_USERData;
import com.ys.system.service.dic.DicService;
import com.ys.system.service.dic.DicTypeService;
import com.ys.system.service.menu.MenuService;
import com.ys.system.service.power.PowerService;
import com.ys.system.service.role.RoleMenuService;
import com.ys.system.service.role.RoleService;
import com.ys.system.service.user.UserService;
import com.ys.util.CalendarUtil;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basedao.BaseTransaction;
import com.ys.util.basequery.BaseQuery;
import com.ys.util.basequery.common.BaseModel;

public class BusinessDbUpdateEjb  {
	
	BaseTransaction ts;
	

    public void executeSupplierDelete(String keyData, UserInfo userInfo) throws Exception {
    	B_SupplierBasicInfoData data = new B_SupplierBasicInfoData();
		int count = 0;
	
		ts = new BaseTransaction();
		
		try {
			ts.begin();
			String removeData[] = keyData.split(",");
			for (String key:removeData) {
				StringBuffer sql = new StringBuffer("");
				data.setId(key);
				B_SupplierBasicInfoDao dao = new B_SupplierBasicInfoDao(data);
				data = (B_SupplierBasicInfoData)dao.FindByPrimaryKey(data);
				data = SupplierService.updateModifyInfo(dao.beanData, userInfo);				
				
				sql.append("UPDATE b_Contact SET DeleteFlag = '" + BusinessConstants.DELETEFLG_DELETED + "' ");
				sql.append(", ModifyTime = '" + CalendarUtil.fmtDate() + "'");
				sql.append(", ModifyPerson = '" + userInfo.getUserId() + "'");
				sql.append(" WHERE companyCode = '" + data.getSupplierid() + "' AND DELETEFLAG = '" + BusinessConstants.DELETEFLG_UNDELETE + "'");
				BaseDAO.execUpdate(sql.toString());
				
				data.setDeleteflag(BusinessConstants.DELETEFLG_DELETED);
				dao.Store(data);
				
				count++;
			}
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
			throw e;
		}
    }
    
    public void executeOrganDelete(String keyData, UserInfo userInfo) throws Exception {
    	B_OrganBasicInfoData data = new B_OrganBasicInfoData();
		int count = 0;
	
		ts = new BaseTransaction();
		
		try {
			ts.begin();
			String removeData[] = keyData.split(",");
			for (String key:removeData) {
				StringBuffer sql = new StringBuffer("");
				data.setId(key);
				B_OrganBasicInfoDao dao = new B_OrganBasicInfoDao(data);
				data = (B_OrganBasicInfoData)dao.FindByPrimaryKey(data);
				data = OrganService.updateModifyInfo(dao.beanData, userInfo);				
				
				sql.append("UPDATE b_Contact SET DeleteFlag = '" + BusinessConstants.DELETEFLG_DELETED + "' ");
				sql.append(", ModifyTime = '" + CalendarUtil.fmtDate() + "'");
				sql.append(", ModifyPerson = '" + userInfo.getUserId() + "'");
				sql.append(" WHERE companyCode = '" + data.getId() + "' AND DELETEFLAG = '" + BusinessConstants.DELETEFLG_UNDELETE + "'");
				BaseDAO.execUpdate(sql.toString());
				
				data.setDeleteflag(BusinessConstants.DELETEFLG_DELETED);
				dao.Store(data);
				
				count++;
			}
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
			throw e;
		}
    }
    
    public void executeContactDelete(String keyData, UserInfo userInfo) throws Exception {
    	B_ContactData data = new B_ContactData();
		int count = 0;
	
		ts = new BaseTransaction();
		
		try {
			ts.begin();
			String removeData[] = keyData.split(",");
			for (String key:removeData) {
				StringBuffer sql = new StringBuffer("");
				data.setId(key);
				B_ContactDao dao = new B_ContactDao(data);
				data = ContactService.updateModifyInfo(dao.beanData, userInfo);			
				data.setDeleteflag(BusinessConstants.DELETEFLG_DELETED);
				dao.Store(data);
				
				count++;

			}
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
			throw e;
		}
    }
    
    public void executeCustomerDelete(String keyData, UserInfo userInfo) throws Exception {
    	B_CustomerData data = new B_CustomerData();
		int count = 0;
	
		ts = new BaseTransaction();
		
		try {
			ts.begin();
			String removeData[] = keyData.split(",");
			for (String key:removeData) {
				StringBuffer sql = new StringBuffer("");
				data.setId(key);
				B_CustomerDao dao = new B_CustomerDao(data);
				data = CustomerService.updateModifyInfo(dao.beanData, userInfo);
				
				sql.append("UPDATE b_Contact SET DeleteFlag = '" + BusinessConstants.DELETEFLG_DELETED + "' ");
				sql.append(", ModifyTime = '" + CalendarUtil.fmtDate() + "'");
				sql.append(", ModifyPerson = '" + userInfo.getUserId() + "'");
				sql.append(" WHERE companyCode = '" + data.getId() + "' AND DELETEFLAG = '" + BusinessConstants.DELETEFLG_UNDELETE + "'");
				BaseDAO.execUpdate(sql.toString());	
				
				sql = new StringBuffer("");
				sql.append("UPDATE b_customerAddr SET DeleteFlag = '" + BusinessConstants.DELETEFLG_DELETED + "' ");
				sql.append(", ModifyTime = '" + CalendarUtil.fmtDate() + "'");
				sql.append(", ModifyPerson = '" + userInfo.getUserId() + "'");
				sql.append(" WHERE customerId = '" + data.getId() + "' AND DELETEFLAG = '" + BusinessConstants.DELETEFLG_UNDELETE + "'");
				BaseDAO.execUpdate(sql.toString());					
				
				data.setDeleteflag(BusinessConstants.DELETEFLG_DELETED);
				dao.Store(data);
				
				count++;

			}
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
			throw e;
		}
    }
    
    public void executeCustomerAddrDelete(String keyData, UserInfo userInfo) throws Exception {
    	B_CustomerAddrData data = new B_CustomerAddrData();
		int count = 0;
	
		ts = new BaseTransaction();
		
		try {
			ts.begin();
			String removeData[] = keyData.split(",");
			for (String key:removeData) {
				StringBuffer sql = new StringBuffer("");
				data.setId(key);
				B_CustomerAddrDao dao = new B_CustomerAddrDao(data);
				data = CustomerAddrService.updateModifyInfo(dao.beanData, userInfo);			
				data.setDeleteflag(BusinessConstants.DELETEFLG_DELETED);
				dao.Store(data);
				
				count++;

			}
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
			throw e;
		}
    }    

    public void executeExternalSampleDelete(String keyData, UserInfo userInfo) throws Exception {
    	B_ExternalSampleData data = new B_ExternalSampleData();
		int count = 0;
	
		ts = new BaseTransaction();
		
		try {
			ts.begin();
			String removeData[] = keyData.split(",");
			for (String key:removeData) {
				StringBuffer sql = new StringBuffer("");
				data.setId(key);
				B_ExternalSampleDao dao = new B_ExternalSampleDao(data);
				data = ExternalSampleService.updateModifyInfo(dao.beanData, userInfo);
				
				sql.append("UPDATE b_externalsample SET DeleteFlag = '" + BusinessConstants.DELETEFLG_DELETED + "' ");
				sql.append(", ModifyTime = '" + CalendarUtil.fmtDate() + "'");
				sql.append(", ModifyPerson = '" + userInfo.getUserId() + "'");
				sql.append(" WHERE id = '" + data.getId() + "' AND DELETEFLAG = '" + BusinessConstants.DELETEFLG_UNDELETE + "'");
				BaseDAO.execUpdate(sql.toString());	
				
				sql = new StringBuffer("");
				sql.append("UPDATE b_esrelationfile SET DeleteFlag = '" + BusinessConstants.DELETEFLG_DELETED + "' ");
				sql.append(", ModifyTime = '" + CalendarUtil.fmtDate() + "'");
				sql.append(", ModifyPerson = '" + userInfo.getUserId() + "'");
				sql.append(" WHERE esId = '" + data.getId() + "' AND DELETEFLAG = '" + BusinessConstants.DELETEFLG_UNDELETE + "'");
				BaseDAO.execUpdate(sql.toString());					
				
				data.setDeleteflag(BusinessConstants.DELETEFLG_DELETED);
				dao.Store(data);
				
				count++;

			}
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
			throw e;
		}
    }    
    
    public void executeESRelationFileDelete(String keyData, UserInfo userInfo) throws Exception {
    	B_ESRelationFileData data = new B_ESRelationFileData();
		int count = 0;
	
		ts = new BaseTransaction();
		
		try {
			ts.begin();
			String removeData[] = keyData.split(",");
			for (String key:removeData) {
				StringBuffer sql = new StringBuffer("");
				data.setId(key);
				B_ESRelationFileDao dao = new B_ESRelationFileDao(data);
				data = (B_ESRelationFileData)dao.FindByPrimaryKey(data);
				data = EsRelationFileService.updateModifyInfo(dao.beanData, userInfo);			
				data.setDeleteflag(BusinessConstants.DELETEFLG_DELETED);
				dao.Store(data);
				
				count++;

			}
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
			throw e;
		}
    }    
}

