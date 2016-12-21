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

import com.ys.business.action.model.makedocument.MakeDocumentModel;
import com.ys.business.db.dao.B_BaseTechDocDao;
import com.ys.business.db.dao.B_ContactDao;
import com.ys.business.db.dao.B_CustomerAddrDao;
import com.ys.business.db.dao.B_CustomerDao;
import com.ys.business.db.dao.B_ESRelationFileDao;
import com.ys.business.db.dao.B_ExternalSampleDao;
import com.ys.business.db.dao.B_FolderDao;
import com.ys.business.db.dao.B_LatePerfectionQuestionDao;
import com.ys.business.db.dao.B_LatePerfectionRelationFileDao;
import com.ys.business.db.dao.B_MaterialCategoryDao;
import com.ys.business.db.dao.B_MouldAcceptanceDao;
import com.ys.business.db.dao.B_MouldBaseInfoDao;
import com.ys.business.db.dao.B_MouldDetailDao;
import com.ys.business.db.dao.B_MouldLendConfirmDao;
import com.ys.business.db.dao.B_MouldLendDetailDao;
import com.ys.business.db.dao.B_MouldLendRegisterDao;
import com.ys.business.db.dao.B_MouldPayInfoDao;
import com.ys.business.db.dao.B_MouldPayListDao;
import com.ys.business.db.dao.B_OrganizationDao;
import com.ys.business.db.dao.B_ProcessControlDao;
import com.ys.business.db.dao.B_ProjectRelationFileDao;
import com.ys.business.db.dao.B_ProjectTaskCostDao;
import com.ys.business.db.dao.B_ProjectTaskDao;
import com.ys.business.db.dao.B_ReformLogDao;
import com.ys.business.db.dao.B_SupplierDao;
import com.ys.business.db.dao.B_WorkingFilesDao;
import com.ys.business.db.data.B_BaseTechDocData;
import com.ys.business.db.data.B_ContactData;
import com.ys.business.db.data.B_CustomerAddrData;
import com.ys.business.db.data.B_CustomerData;
import com.ys.business.db.data.B_ESRelationFileData;
import com.ys.business.db.data.B_ExternalSampleData;
import com.ys.business.db.data.B_FolderData;
import com.ys.business.db.data.B_LatePerfectionQuestionData;
import com.ys.business.db.data.B_LatePerfectionRelationFileData;
import com.ys.business.db.data.B_MaterialCategoryData;
import com.ys.business.db.data.B_MouldAcceptanceData;
import com.ys.business.db.data.B_MouldBaseInfoData;
import com.ys.business.db.data.B_MouldDetailData;
import com.ys.business.db.data.B_MouldLendConfirmData;
import com.ys.business.db.data.B_MouldLendDetailData;
import com.ys.business.db.data.B_MouldLendRegisterData;
import com.ys.business.db.data.B_MouldPayInfoData;
import com.ys.business.db.data.B_MouldPayListData;
import com.ys.business.db.data.B_OrganizationData;
import com.ys.business.db.data.B_ProcessControlData;
import com.ys.business.db.data.B_ProjectRelationFileData;
import com.ys.business.db.data.B_ProjectTaskCostData;
import com.ys.business.db.data.B_ProjectTaskData;
import com.ys.business.db.data.B_ReformLogData;
import com.ys.business.db.data.B_SupplierData;
import com.ys.business.db.data.B_WorkingFilesData;
import com.ys.business.db.data.CommFieldsData;
import com.ys.business.service.contact.ContactService;
import com.ys.business.service.customer.CustomerService;
import com.ys.business.service.customeraddr.CustomerAddrService;
import com.ys.business.service.esrelationfile.EsRelationFileService;
import com.ys.business.service.externalsample.ExternalSampleService;
import com.ys.business.service.lateperfection.LatePerfectionService;
import com.ys.business.service.makedocument.MakeDocumentService;
import com.ys.business.service.mouldcontract.MouldContractService;
import com.ys.business.service.mouldlendregister.MouldLendRegisterService;
import com.ys.business.service.processcontrol.ProcessControlService;
import com.ys.business.service.projecttask.ProjectTaskService;
import com.ys.business.service.reformlog.ReformLogService;
import com.ys.business.service.supplier.SupplierService;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.system.service.common.BaseService;
import com.ys.util.CalendarUtil;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basedao.BaseTransaction;
import com.ys.util.basequery.BaseQuery;
import com.ys.util.basequery.common.BaseModel;
import com.ys.util.basequery.common.Constants;												
											
												
public class BusinessDbUpdateEjb  {												
												
	BaseTransaction ts;											
												
	private CommFieldsData commData;											
												
    public void executeSupplierDelete(String keyData, UserInfo userInfo) throws Exception {												
    	B_SupplierData data = new B_SupplierData();											
		int count = 0;										
												
		ts = new BaseTransaction();										
												
		try {										
			ts.begin();									
			String removeData[] = keyData.split(",");									
			for (String key:removeData) {									
				StringBuffer sql = new StringBuffer("");								
				data.setRecordid(key);								
				B_SupplierDao dao = new B_SupplierDao(data);								
				data = (B_SupplierData)dao.FindByPrimaryKey(data);								
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
    	B_OrganizationData data = new B_OrganizationData();											
												
		ts = new BaseTransaction();										
												
		try {										
			ts.begin();									
			String removeData[] = keyData.split(",");									
			for (String key:removeData) {									
												
				data.setRecordid(key);								
				B_OrganizationDao dao = new B_OrganizationDao(data);								
				data = (B_OrganizationData)dao.FindByPrimaryKey(data);								
												
				BaseService service = new BaseService();								
												
				commData = service.commFiledEdit(								
						Constants.ACCESSTYPE_DEL,						
						"OrganDelete",						
						userInfo);						
												
				service.copyProperties(data,commData);								
												
				dao.Store(data);								
			}									
			ts.commit();									
		}										
		catch(Exception e) {										
			ts.rollback();									
			throw e;									
		}										
    }												
    												
    public void executeMaterialCategroyDelete(String keyData, UserInfo userInfo) throws Exception {												
    	B_MaterialCategoryData data = new B_MaterialCategoryData();											
												
		ts = new BaseTransaction();										
												
		try {										
			ts.begin();									
			String removeData[] = keyData.split(",");									
			for (String key:removeData) {									
				StringBuffer sql = new StringBuffer("");								
				data.setRecordid(key);								
				B_MaterialCategoryDao dao = new B_MaterialCategoryDao(data);								
				data = (B_MaterialCategoryData)dao.FindByPrimaryKey(data);								
												
				//update处理								
				BaseService service = new BaseService();								
				commData = service.commFiledEdit(								
						Constants.ACCESSTYPE_DEL,						
						"MaterialUpdate",						
						userInfo);						
												
				service.copyProperties(data,commData);								
												
				dao.Store(data);								
												
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
				data.setRecordid(key);								
				B_CustomerDao dao = new B_CustomerDao(data);								
				data = CustomerService.updateModifyInfo(dao.beanData, userInfo);								
												
				sql.append("UPDATE b_Contact SET DeleteFlag = '" + BusinessConstants.DELETEFLG_DELETED + "' ");								
				sql.append(", ModifyTime = '" + CalendarUtil.fmtDate() + "'");								
				sql.append(", ModifyPerson = '" + userInfo.getUserId() + "'");								
				sql.append(" WHERE companyCode = '" + data.getRecordid() + "' AND DELETEFLAG = '" + BusinessConstants.DELETEFLG_UNDELETE + "'");								
				BaseDAO.execUpdate(sql.toString());								
												
				sql = new StringBuffer("");								
				sql.append("UPDATE b_customerAddr SET DeleteFlag = '" + BusinessConstants.DELETEFLG_DELETED + "' ");								
				sql.append(", ModifyTime = '" + CalendarUtil.fmtDate() + "'");								
				sql.append(", ModifyPerson = '" + userInfo.getUserId() + "'");								
				sql.append(" WHERE customerId = '" + data.getRecordid() + "' AND DELETEFLAG = '" + BusinessConstants.DELETEFLG_UNDELETE + "'");								
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
    												
    public String executeProjectTaskAdd(String data, UserInfo userInfo) throws Exception {												
    	B_ProjectTaskData dbData = new B_ProjectTaskData();											
    	B_ProjectTaskDao dao = new B_ProjectTaskDao();											
    	B_ProjectTaskCostData dbCostData = new B_ProjectTaskCostData();											
    	B_ProjectTaskCostDao costDao = new B_ProjectTaskCostDao();											
    												
    	BaseService service = new BaseService();											
		int count = 0;										
		String guid = "";										
												
		ts = new BaseTransaction();										
												
		try {										
			ts.begin();									
												
			guid = BaseDAO.getGuId();									
			dbData.setId(guid);									
			dbData.setProjectid(service.getJsonData(data, "projectId"));									
			dbData.setProjectname(service.getJsonData(data, "projectName"));									
			dbData.setTempversion(service.getJsonData(data, "tempVersion"));									
			dbData.setManager(service.getJsonData(data, "manager"));									
			dbData.setReferprototype(service.getJsonData(data, "referPrototype"));									
			dbData.setDesigncapability(service.getJsonData(data, "designCapability"));									
			dbData.setBegintime(service.getJsonData(data, "beginTime"));									
			dbData.setEndtime(service.getJsonData(data, "endTime"));									
			dbData.setPacking(service.getJsonData(data, "packing"));									
			dbData.setEstimatecost(service.getJsonData(data, "estimateCost"));									
			dbData.setSaleprice(service.getJsonData(data, "salePrice"));									
			dbData.setSales(service.getJsonData(data, "sales"));									
			dbData.setRecoverynum(service.getJsonData(data, "recoveryNum"));									
			dbData.setFailmode(service.getJsonData(data, "failMode"));									
			dbData = ProjectTaskService.updateModifyInfo(dbData, userInfo);									
			dao.Create(dbData);									
												
			//TODO									
			executeUpdateProjectTaskCost(guid, data, userInfo);									
												
			count++;									
												
			ts.commit();									
		}										
		catch(Exception e) {										
			ts.rollback();									
			throw e;									
		}										
												
		return guid;										
    }    												
    												
    public String executeProjectTaskUpdate(String data, UserInfo userInfo) throws Exception {												
    	B_ProjectTaskData dbData = new B_ProjectTaskData();											
    	B_ProjectTaskDao dao = new B_ProjectTaskDao();											
    												
    	BaseService service = new BaseService();											
		int count = 0;										
		String guid = "";										
												
		ts = new BaseTransaction();										
												
		try {										
			ts.begin();									
			String id = service.getJsonData(data, "keyBackup");									
			dbData.setId(id);									
			dbData.setProjectid(service.getJsonData(data, "projectId"));									
			dbData.setProjectname(service.getJsonData(data, "projectName"));									
			dbData.setTempversion(service.getJsonData(data, "tempVersion"));									
			dbData.setManager(service.getJsonData(data, "manager"));									
			dbData.setReferprototype(service.getJsonData(data, "referPrototype"));									
			dbData.setDesigncapability(service.getJsonData(data, "designCapability"));									
			dbData.setBegintime(service.getJsonData(data, "beginTime"));									
			dbData.setEndtime(service.getJsonData(data, "endTime"));									
			dbData.setPacking(service.getJsonData(data, "packing"));									
			dbData.setEstimatecost(service.getJsonData(data, "estimateCost"));									
			dbData.setSaleprice(service.getJsonData(data, "salePrice"));									
			dbData.setSales(service.getJsonData(data, "sales"));									
			dbData.setRecoverynum(service.getJsonData(data, "recoveryNum"));									
			dbData.setFailmode(service.getJsonData(data, "failMode"));									
			dbData = ProjectTaskService.updateModifyInfo(dbData, userInfo);									
			dao.Store(dbData);									
												
			StringBuffer sql = new StringBuffer("");									
			sql.append("DELETE FROM b_projecttaskcost ");									
			sql.append(" WHERE projectId = '" + id + "'");									
			BaseDAO.execUpdate(sql.toString());									
												
			executeUpdateProjectTaskCost(id, data, userInfo);									
												
			count++;									
												
			ts.commit();									
		}										
		catch(Exception e) {										
			ts.rollback();									
			throw e;									
		}										
												
		return guid;										
    }        												
    												
    private void executeUpdateProjectTaskCost(String guid, String data, UserInfo userInfo) throws Exception {												
    	BaseService service = new BaseService();											
    	B_ProjectTaskCostData dbCostData = new B_ProjectTaskCostData();											
    	B_ProjectTaskCostDao costDao = new B_ProjectTaskCostDao();											
    												
    	String minCols[] = service.getJsonData(data, "minCols").split(",");											
		String colCount[] = service.getJsonData(data, "colCount").split(",");										
		String colNames[] = service.getJsonData(data, "colNames").split(",");										
												
		for(int i = 0; i < colNames.length; i++) {										
			String colName = colNames[i];									
			String type = String.valueOf(i);									
			int minCol = Integer.parseInt(minCols[i]);									
												
			for(int j = 0; j < Integer.parseInt(colCount[i]); j++) {									
				String name = "";								
				String cost = "";								
												
				if (j < minCol) {								
					name = ProjectTaskService.staticColName[i][j];							
				} else {								
					name = service.getJsonData(data, colName + "-name" + "-" + String.valueOf(j + 1));							
				}								
				cost = service.getJsonData(data, colName + "-" + String.valueOf(j + 1));								
				if (!name.equals("") || !cost.equals("")) {								
					dbCostData.setId(BaseDAO.getGuId());							
					dbCostData.setProjectid(guid);							
					dbCostData.setType(type);							
					dbCostData.setName(name);							
					dbCostData.setCost(cost);							
					dbCostData.setSortno(j);							
					dbCostData = ProjectTaskService.updateCostDataModifyInfo(dbCostData, userInfo);							
					costDao.Create(dbCostData);							
				}								
			}									
												
		}										
    }												
    												
    public void executeProjectTaskDelete(String keyData, UserInfo userInfo) throws Exception {												
    	B_ProjectTaskData data = new B_ProjectTaskData();											
		int count = 0;										
												
		ts = new BaseTransaction();										
												
		try {										
			ts.begin();									
			String removeData[] = keyData.split(",");									
			for (String key:removeData) {									
				StringBuffer sql = new StringBuffer("");								
				data.setId(key);								
				B_ProjectTaskDao dao = new B_ProjectTaskDao(data);								
				data = ProjectTaskService.updateModifyInfo(dao.beanData, userInfo);								
												
				sql = new StringBuffer("");								
				sql.append("UPDATE b_projectrelationfile SET DeleteFlag = '" + BusinessConstants.DELETEFLG_DELETED + "' ");								
				sql.append(", ModifyTime = '" + CalendarUtil.fmtDate() + "'");								
				sql.append(", ModifyPerson = '" + userInfo.getUserId() + "'");								
				sql.append(" WHERE pjId = '" + data.getId() + "' AND DELETEFLAG = '" + BusinessConstants.DELETEFLG_UNDELETE + "'");								
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
    												
    public void executeProjectRelationFileDelete(String keyData, UserInfo userInfo) throws Exception {												
    	B_ProjectRelationFileData data = new B_ProjectRelationFileData();											
		int count = 0;										
												
		ts = new BaseTransaction();										
												
		try {										
			ts.begin();									
			String removeData[] = keyData.split(",");									
			for (String key:removeData) {									
				StringBuffer sql = new StringBuffer("");								
				data.setId(key);								
				B_ProjectRelationFileDao dao = new B_ProjectRelationFileDao(data);								
				data = (B_ProjectRelationFileData)dao.FindByPrimaryKey(data);								
				//data = ProjectRelationFileService.updateModifyInfo(dao.beanData, userInfo);								
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
    												
    public String executeProcessControlUpdate(String data, UserInfo userInfo) throws Exception {												
    	B_ProcessControlData dbData = new B_ProcessControlData();											
    	B_ProcessControlDao dao = new B_ProcessControlDao();											
    												
    	BaseService service = new BaseService();											
		int count = 0;										
		String info = "";										
												
		ts = new BaseTransaction();										
												
		try {										
			ts.begin();									
			String id = service.getJsonData(data, "keyBackup");									
			String projectId = service.getJsonData(data, "projectId");									
			String type = service.getJsonData(data, "type");									
			String createDate = service.getJsonData(data, "createDate");									
			String expectDate = service.getJsonData(data, "expectDate");									
			String finishTime = service.getJsonData(data, "finishTime");									
			String reason = service.getJsonData(data, "reason");									
			String exceedDate = service.getJsonData(data, "exceedDate2View");									
												
			if (id.equals("")) {									
				//doAdd								
				dbData = new B_ProcessControlData();								
				dbData.setProjectid(projectId);								
				dbData.setType(type);								
				if (createDate.equals("")) {								
					dbData.setCreatedate(null);							
				} else {								
					dbData.setCreatedate(createDate);							
				}								
				if (expectDate.equals("")) {								
					dbData.setExpectdate(null);							
				} else {								
					dbData.setExpectdate(expectDate);							
				}								
				dbData.setReason(reason);								
				if (finishTime.equals("")) {								
					dbData.setFinishtime(null);							
				} else {								
					dbData.setFinishtime(finishTime);							
				}								
				dbData.setConfirm("0");								
				dbData = ProcessControlService.updateModifyInfo(dbData, userInfo);								
												
				String guid = BaseDAO.getGuId();								
				dbData.setId(guid);								
				dao.Create(dbData);								
			} else {									
				//doUpdate or doDelete								
				dbData.setId(id);								
				dbData = (B_ProcessControlData)dao.FindByPrimaryKey(dbData);								
				if (type.length() == 1) {								
					if (expectDate.equals("")) {							
						dbData.setExpectdate(null);						
					} else {							
						dbData.setExpectdate(expectDate);						
					}							
												
					if (finishTime.equals("")) {							
						dbData.setFinishtime(null);						
					} else {							
						dbData.setFinishtime(finishTime);						
					}							
				} else {								
					if (type.substring(1, 2).equals("1")) {							
						if (createDate.equals("")) {						
							dbData.setCreatedate(null);					
						} else {						
							dbData.setCreatedate(createDate);					
						}						
						if (expectDate.equals("")) {						
							dbData.setExpectdate(null);					
						} else {						
							dbData.setExpectdate(expectDate);					
						}						
						dbData.setReason(reason);						
					} else {							
						if (createDate.equals("")) {						
							dbData.setCreatedate(null);					
						} else {						
							dbData.setCreatedate(createDate);					
						}						
						dbData.setReason(reason);						
					}							
				}								
				/*								
 				if (type.length() == 1) {								
					StringBuffer sql = new StringBuffer();							
					if (finishTime.equals("")) {							
						sql.append("UPDATE b_processControl SET finishTime = null ");						
					} else {							
						sql.append("UPDATE b_processControl SET finishTime = '" + finishTime + "'");						
					}							
					sql.append(", ModifyTime = '" + CalendarUtil.fmtDate() + "'");							
					sql.append(", ModifyPerson = '" + userInfo.getUserId() + "'");							
					sql.append(" WHERE projectId = '" + projectId + "' AND DELETEFLAG = '" + BusinessConstants.DELETEFLG_UNDELETE + "' AND TYPE = '" + type + "'");							
												
												
 				}								
 				*/								
				dao.Store(dbData);								
			}									
												
			count++;									
												
			info += type;									
			info += "," + expectDate;									
			info += "," + exceedDate;									
			info += "," + finishTime;									
												
			ts.commit();									
		}										
		catch(Exception e) {										
			ts.rollback();									
			throw e;									
		}										
												
		return info;										
    }												
												
    												
    public void executeProcessControlDelete(String keyData, UserInfo userInfo) throws Exception {												
    	B_ProjectTaskData data = new B_ProjectTaskData();											
		int count = 0;										
												
		ts = new BaseTransaction();										
												
		try {										
			ts.begin();									
			String removeData[] = keyData.split(",");									
			for (String key:removeData) {									
				StringBuffer sql = new StringBuffer("");								
				sql.append("UPDATE b_ProcessControl SET DeleteFlag = '" + BusinessConstants.DELETEFLG_DELETED + "' ");								
				sql.append(", ModifyTime = '" + CalendarUtil.fmtDate() + "'");								
				sql.append(", ModifyPerson = '" + userInfo.getUserId() + "'");								
				sql.append(" WHERE projectId = '" + key + "' AND DELETEFLAG = '" + BusinessConstants.DELETEFLG_UNDELETE + "'");								
				BaseDAO.execUpdate(sql.toString());								
												
				count++;								
												
			}									
			ts.commit();									
		}										
		catch(Exception e) {										
			ts.rollback();									
			throw e;									
		}										
    }    												
        				
    
    public void executeLatePerfectionTPFileDelete(String keyData, UserInfo userInfo) throws Exception {
    	B_LatePerfectionRelationFileDao dao = new B_LatePerfectionRelationFileDao();
    	B_LatePerfectionRelationFileData data = new B_LatePerfectionRelationFileData();											
		int count = 0;					
												
		ts = new BaseTransaction();										
												
		try {										
			ts.begin();									
			String removeData[] = keyData.split(",");									
			for (String key:removeData) {									
				data.setId(key);
				data = (B_LatePerfectionRelationFileData)dao.FindByPrimaryKey(data);
				data = LatePerfectionService.updateTPFileModifyInfo(data, userInfo);
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
    
    public void executeLatePerfectionQuestionDelete(String keyData, UserInfo userInfo) throws Exception {
    	B_LatePerfectionQuestionDao dao = new B_LatePerfectionQuestionDao();
    	B_LatePerfectionQuestionData data = new B_LatePerfectionQuestionData();											
		int count = 0;					
												
		ts = new BaseTransaction();										
												
		try {										
			ts.begin();									
			String removeData[] = keyData.split(",");									
			for (String key:removeData) {									
				data.setId(key);
				data = (B_LatePerfectionQuestionData)dao.FindByPrimaryKey(data);
				data = LatePerfectionService.updateQuestionModifyInfo(data, userInfo);
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
    
    public void executeLatePerfectionDelete(String keyData, UserInfo userInfo) throws Exception {												
										
		int count = 0;										
												
		ts = new BaseTransaction();										
												
		try {										
			ts.begin();									
			String removeData[] = keyData.split(",");									
			for (String key:removeData) {									
				StringBuffer sql = new StringBuffer("");								
				sql.append("UPDATE b_lateperfectionrelationfile SET DeleteFlag = '" + BusinessConstants.DELETEFLG_DELETED + "' ");								
				sql.append(", ModifyTime = '" + CalendarUtil.fmtDate() + "'");								
				sql.append(", ModifyPerson = '" + userInfo.getUserId() + "'");								
				sql.append(" WHERE projectId = '" + key + "' AND DELETEFLAG = '" + BusinessConstants.DELETEFLG_UNDELETE + "'");								
				BaseDAO.execUpdate(sql.toString());								
							
				sql = new StringBuffer("");								
				sql.append("UPDATE b_lateperfectionquestion SET DeleteFlag = '" + BusinessConstants.DELETEFLG_DELETED + "' ");								
				sql.append(", ModifyTime = '" + CalendarUtil.fmtDate() + "'");								
				sql.append(", ModifyPerson = '" + userInfo.getUserId() + "'");								
				sql.append(" WHERE projectId = '" + key + "' AND DELETEFLAG = '" + BusinessConstants.DELETEFLG_UNDELETE + "'");								
				BaseDAO.execUpdate(sql.toString());	
				
				count++;								
												
			}									
			ts.commit();									
		}										
		catch(Exception e) {										
			ts.rollback();									
			throw e;									
		}
    }    												

    public void executeReformLogDelete(String keyData, UserInfo userInfo) throws Exception {
    	B_ReformLogDao dao = new B_ReformLogDao();
    	B_ReformLogData data = new B_ReformLogData();											
		int count = 0;					
												
		ts = new BaseTransaction();										
												
		try {										
			ts.begin();									
			String removeData[] = keyData.split(",");									
			for (String key:removeData) {									
				data.setId(key);
				data = (B_ReformLogData)dao.FindByPrimaryKey(data);
				data = ReformLogService.updateModifyInfo(data, userInfo);
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
    
    public void executeReformLogDeleteByProjectId(String keyData, UserInfo userInfo) throws Exception {												
		
    	BaseService baseService = new BaseService();
		int count = 0;							
						
		ts = new BaseTransaction();										
												
		try {										
			ts.begin();									
			String removeData[] = keyData.split(",");									
			for (String key:removeData) {
				StringBuffer sql = new StringBuffer("");
				sql.append("UPDATE b_ReformLog SET DeleteFlag = '" + BusinessConstants.DELETEFLG_DELETED + "' ");								
				sql.append(", ModifyTime = '" + CalendarUtil.fmtDate() + "'");								
				sql.append(", ModifyPerson = '" + userInfo.getUserId() + "'");								
				sql.append(" WHERE projectId = '" + key + "' AND DELETEFLAG = '" + BusinessConstants.DELETEFLG_UNDELETE + "'");								
				BaseDAO.execUpdate(sql.toString());								
			}
			ts.commit();									
		}										
		catch(Exception e) {										
			ts.rollback();									
			throw e;									
		}
    }

    public void executeBaseTechDocDelete(String keyData, UserInfo userInfo) throws Exception {
    	B_BaseTechDocDao dao = new B_BaseTechDocDao();
    	B_BaseTechDocData data = new B_BaseTechDocData();											
		int count = 0;					
												
		ts = new BaseTransaction();										
												
		try {										
			ts.begin();									
			String removeData[] = keyData.split(",");									
			for (String key:removeData) {									
				data.setId(key);
				data = (B_BaseTechDocData)dao.FindByPrimaryKey(data);
				data = MakeDocumentService.updateBaseTechDocModifyInfo(data, userInfo);
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
    
    public void executeWorkingFileDelete(String keyData, UserInfo userInfo) throws Exception {
    	B_WorkingFilesDao dao = new B_WorkingFilesDao();
    	B_WorkingFilesData data = new B_WorkingFilesData();											
		int count = 0;					
												
		ts = new BaseTransaction();										
												
		try {										
			ts.begin();									
			String removeData[] = keyData.split(",");									
			for (String key:removeData) {									
				data.setId(key);
				data = (B_WorkingFilesData)dao.FindByPrimaryKey(data);
				data = MakeDocumentService.updateWorkingFileModifyInfo(data, userInfo);
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
    
	public boolean doDeleteFolder(HttpServletRequest request, String data, UserInfo userInfo) throws Exception {
		MakeDocumentModel model = new MakeDocumentModel();										
		BaseService service = new BaseService();	
		B_FolderDao dao = new B_FolderDao();
		B_FolderData dbData = new B_FolderData();
		
		String key = "";
		String projectId = service.getJsonData(data, "keyBackup");
		String folderName = service.getJsonData(data, "tabTitle");
		
		boolean isDbOperationSuccess = false;
		
		ts = new BaseTransaction();	
		
		try {
			
			ts.begin();
			
			key = MakeDocumentService.getFolderName(request, projectId, folderName);
			dbData.setId(key);
			dbData = (B_FolderData)dao.FindByPrimaryKey(dbData);
			dbData = MakeDocumentService.updateFolderDataModifyInfo(dbData, userInfo);
			dbData.setDeleteflag(BusinessConstants.DELETEFLG_DELETED);
			dao.Store(dbData);
			
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE b_docfilefolder SET DeleteFlag = '" + BusinessConstants.DELETEFLG_DELETED + "' ");								
			sql.append(", ModifyTime = '" + CalendarUtil.fmtDate() + "'");								
			sql.append(", ModifyPerson = '" + userInfo.getUserId() + "'");								
			sql.append(" WHERE projectId = '" + projectId + "' AND folderId = '" + key + "' AND DELETEFLAG = '" + BusinessConstants.DELETEFLG_UNDELETE + "'");							
			BaseDAO.execUpdate(sql.toString());
			
			ts.commit();
			
			isDbOperationSuccess = true;
			
		}
		catch(Exception e) {
			ts.rollback();
			throw e;
		}
		
		return isDbOperationSuccess;
	}	
    
    public void executeMakeDocumentDelete(String keyData, UserInfo userInfo) throws Exception {												
										
		int count = 0;										
												
		ts = new BaseTransaction();										
												
		try {										
			ts.begin();									
			String removeData[] = keyData.split(",");									
			for (String key:removeData) {									
				StringBuffer sql = new StringBuffer("");								
				sql.append("UPDATE b_BaseTechDoc SET DeleteFlag = '" + BusinessConstants.DELETEFLG_DELETED + "' ");								
				sql.append(", ModifyTime = '" + CalendarUtil.fmtDate() + "'");								
				sql.append(", ModifyPerson = '" + userInfo.getUserId() + "'");								
				sql.append(" WHERE projectId = '" + key + "' AND DELETEFLAG = '" + BusinessConstants.DELETEFLG_UNDELETE + "'");								
				BaseDAO.execUpdate(sql.toString());								
							
				sql = new StringBuffer("");								
				sql.append("UPDATE b_WorkingFiles SET DeleteFlag = '" + BusinessConstants.DELETEFLG_DELETED + "' ");								
				sql.append(", ModifyTime = '" + CalendarUtil.fmtDate() + "'");								
				sql.append(", ModifyPerson = '" + userInfo.getUserId() + "'");								
				sql.append(" WHERE projectId = '" + key + "' AND DELETEFLAG = '" + BusinessConstants.DELETEFLG_UNDELETE + "'");								
				BaseDAO.execUpdate(sql.toString());	
				
				sql = new StringBuffer("");								
				sql.append("UPDATE b_DocFileFolder SET DeleteFlag = '" + BusinessConstants.DELETEFLG_DELETED + "' ");								
				sql.append(", ModifyTime = '" + CalendarUtil.fmtDate() + "'");								
				sql.append(", ModifyPerson = '" + userInfo.getUserId() + "'");								
				sql.append(" WHERE projectId = '" + key + "' AND DELETEFLAG = '" + BusinessConstants.DELETEFLG_UNDELETE + "'");								
				BaseDAO.execUpdate(sql.toString());					
				
				sql = new StringBuffer("");								
				sql.append("UPDATE b_Folder SET DeleteFlag = '" + BusinessConstants.DELETEFLG_DELETED + "' ");								
				sql.append(", ModifyTime = '" + CalendarUtil.fmtDate() + "'");								
				sql.append(", ModifyPerson = '" + userInfo.getUserId() + "'");								
				sql.append(" WHERE projectId = '" + key + "' AND DELETEFLAG = '" + BusinessConstants.DELETEFLG_UNDELETE + "'");								
				BaseDAO.execUpdate(sql.toString());		
				
				count++;								
												
			}									
			ts.commit();									
		}										
		catch(Exception e) {										
			ts.rollback();									
			throw e;									
		}
    }

    public void executeMouldContractDelete(String keyData, UserInfo userInfo) throws Exception {
    	B_MouldBaseInfoData dbData = new B_MouldBaseInfoData();
    	B_MouldBaseInfoDao dbDao = new B_MouldBaseInfoDao();
    	B_MouldAcceptanceData dbAcceptanceData = new B_MouldAcceptanceData();
    	B_MouldAcceptanceDao dbAcceptanceDao = new B_MouldAcceptanceDao();
    	B_MouldPayInfoData dbPayInfoData = new B_MouldPayInfoData();											
    	B_MouldPayInfoDao daoPayInfo = new B_MouldPayInfoDao();
		int count = 0;										
												
		ts = new BaseTransaction();										
												
		try {										
			ts.begin();									
			String removeData[] = keyData.split(",");									
			for (String key:removeData) {
				StringBuffer sql = new StringBuffer("");
				
				dbData.setId(key);
				dbData = (B_MouldBaseInfoData)dbDao.FindByPrimaryKey(dbData);
				dbData = MouldContractService.updateMouldBaseInfoModifyInfo(dbData, userInfo);
				dbData.setDeleteflag(BusinessConstants.DELETEFLG_DELETED);
				dbDao.Store(dbData);
				
				sql = new StringBuffer("");								
				sql.append("UPDATE b_MouldDetail SET DeleteFlag = '" + BusinessConstants.DELETEFLG_DELETED + "' ");								
				sql.append(", ModifyTime = '" + CalendarUtil.fmtDate() + "'");								
				sql.append(", ModifyPerson = '" + userInfo.getUserId() + "'");								
				sql.append(" WHERE mouldBaseId = '" + key + "' AND DELETEFLAG = '" + BusinessConstants.DELETEFLG_UNDELETE + "'");								
				BaseDAO.execUpdate(sql.toString());	
				
				dbAcceptanceData.setMouldbaseid(dbData.getId());
				dbAcceptanceData = (B_MouldAcceptanceData)dbAcceptanceDao.FindByPrimaryKey(dbAcceptanceData);
				dbAcceptanceData = MouldContractService.updateMouldAcceptanceModifyInfo(dbAcceptanceData, userInfo);
				dbAcceptanceData.setDeleteflag(BusinessConstants.DELETEFLG_DELETED);
				dbAcceptanceDao.Store(dbAcceptanceData);
				
				dbPayInfoData.setMouldbaseid(dbData.getId());
				dbPayInfoData = (B_MouldPayInfoData)daoPayInfo.FindByPrimaryKey(dbPayInfoData);
				dbPayInfoData = MouldContractService.updateMouldPayInfoModifyInfo(dbPayInfoData, userInfo);
				dbPayInfoData.setDeleteflag(BusinessConstants.DELETEFLG_DELETED);
				daoPayInfo.Store(dbPayInfoData);
				
				sql = new StringBuffer("");								
				sql.append("UPDATE b_MouldPayList SET DeleteFlag = '" + BusinessConstants.DELETEFLG_DELETED + "' ");								
				sql.append(", ModifyTime = '" + CalendarUtil.fmtDate() + "'");								
				sql.append(", ModifyPerson = '" + userInfo.getUserId() + "'");								
				sql.append(" WHERE mouldBaseId = '" + key + "' AND DELETEFLAG = '" + BusinessConstants.DELETEFLG_UNDELETE + "'");								
				BaseDAO.execUpdate(sql.toString());		
				
				count++;								
												
			}									
			ts.commit();									
		}										
		catch(Exception e) {										
			ts.rollback();									
			throw e;									
		}
    }
    
    public String updateMouldContractPayList(String data, String confirm, UserInfo userInfo) throws Exception {
    	B_MouldPayInfoData dbInfoData = new B_MouldPayInfoData();											
    	B_MouldPayInfoDao daoInfo = new B_MouldPayInfoDao();
    	B_MouldPayListData dbData = new B_MouldPayListData();											
    	B_MouldPayListDao dao = new B_MouldPayListDao();											

    	BaseService service = new BaseService();	
    	
		String key = "";
		if (data.indexOf("keyBackup") < 0) {
			key = data;
		} else {
			key = service.getJsonData(data, "keyBackup");
		}
		String guid = "";
		
		ts = new BaseTransaction();	
		
		try {
			ts.begin();				
			if (key == null || key.equals("")) {
				guid = BaseDAO.getGuId();									
				dbData.setId(guid);									
				dbData.setMouldbaseid(service.getJsonData(data, "mouldBaseId"));
				dbData.setPaytime(service.getJsonData(data, "payTime"));
				dbData.setPay(service.getJsonData(data, "pay"));
				dbData = MouldContractService.updateMouldPayListModifyInfo(dbData, userInfo);
				dao.Create(dbData);
				
				dbInfoData.setMouldbaseid(service.getJsonData(data, "mouldBaseId"));
				dbInfoData.setWithhold(service.getJsonData(data, "withhold"));
				dbInfoData = MouldContractService.updateMouldPayInfoModifyInfo(dbInfoData, userInfo);
				daoInfo.Create(dbInfoData);
				key = guid;
			} else {
				dbData.setId(key);
				dbData = (B_MouldPayListData)dao.FindByPrimaryKey(dbData);

				if (confirm.equals("1")) {
					dbData.setConfirm("1");
					dbData = MouldContractService.updateMouldPayListModifyInfo(dbData, userInfo);
					dao.Store(dbData);
				} else {
					dbData.setPaytime(service.getJsonData(data, "payTime"));
					dbData.setPay(service.getJsonData(data, "pay"));
					dbData = MouldContractService.updateMouldPayListModifyInfo(dbData, userInfo);
					dao.Store(dbData);
					
					dbInfoData.setMouldbaseid(service.getJsonData(data, "mouldBaseId"));
					dbInfoData = (B_MouldPayInfoData)daoInfo.FindByPrimaryKey(dbInfoData);
					dbInfoData.setWithhold(service.getJsonData(data, "withhold"));
					dbInfoData = MouldContractService.updateMouldPayInfoModifyInfo(dbInfoData, userInfo);
					daoInfo.Store(dbInfoData);
				}
			}
			ts.commit();
		}
		catch(Exception e) {										
			ts.rollback();									
			throw e;									
		}										
												
		return guid;
    }
    
    public void executeMouldContractMdDelete(String keyData, UserInfo userInfo) throws Exception {												
    	B_MouldDetailData dbData = new B_MouldDetailData();											
    	B_MouldDetailDao dao = new B_MouldDetailDao();

		int count = 0;										
												
		ts = new BaseTransaction();										
												
		try {										
			ts.begin();

			String removeData[] = keyData.split(",");									
			for (String key:removeData) {									
				dbData.setId(key);
				dbData = (B_MouldDetailData)dao.FindByPrimaryKey(dbData);
				dbData = MouldContractService.updateMdModifyInfo(dbData, userInfo);
				dbData.setDeleteflag(BusinessConstants.DELETEFLG_DELETED);
				dao.Store(dbData);
				count++;												
			}
			ts.commit();									
		}										
		catch(Exception e) {										
			ts.rollback();									
			throw e;									
		}
    }
    
    public void executeMouldContractPayListDelete(String keyData, UserInfo userInfo) throws Exception {												
    	B_MouldPayInfoData dbInfoData = new B_MouldPayInfoData();											
    	B_MouldPayInfoDao daoInfo = new B_MouldPayInfoDao();
    	B_MouldPayListData dbData = new B_MouldPayListData();											
    	B_MouldPayListDao dao = new B_MouldPayListDao();
		int count = 0;										
												
		ts = new BaseTransaction();										
												
		try {										
			ts.begin();
			boolean isFirst = true;
			String removeData[] = keyData.split(",");									
			for (String key:removeData) {									
				StringBuffer sql = new StringBuffer("");								
				sql.append("UPDATE b_MouldPayList SET DeleteFlag = '" + BusinessConstants.DELETEFLG_DELETED + "' ");								
				sql.append(", ModifyTime = '" + CalendarUtil.fmtDate() + "'");								
				sql.append(", ModifyPerson = '" + userInfo.getUserId() + "'");								
				sql.append(" WHERE id = '" + key + "' AND DELETEFLAG = '" + BusinessConstants.DELETEFLG_UNDELETE + "'");								
				BaseDAO.execUpdate(sql.toString());		
				/*
				if (isFirst) {
					isFirst = false;
					dbData.setId(key);
					dbData = (B_MouldPayListData)dao.FindByPrimaryKey(dbData);
					
					dbInfoData.setMouldbaseid(dbData.getMouldbaseid());
					dbInfoData = (B_MouldPayInfoData)daoInfo.FindByPrimaryKey(dbInfoData);
					dbInfoData = MouldContractService.updateMouldPayInfoModifyInfo(dbInfoData, userInfo);
					dbInfoData.setDeleteflag(BusinessConstants.DELETEFLG_DELETED);
					daoInfo.Store(dbInfoData);
				}
				*/
				count++;								
												
			}
			ts.commit();									
		}										
		catch(Exception e) {										
			ts.rollback();									
			throw e;									
		}
    }
    
    public String executeMouldLendRegisterUpdate(HttpServletRequest request, String key, String data, String confirm, UserInfo userInfo) throws Exception {
		B_MouldLendRegisterDao dao = new B_MouldLendRegisterDao();
		B_MouldLendRegisterData dbData = new B_MouldLendRegisterData();
		B_MouldLendConfirmDao confirmDao = new B_MouldLendConfirmDao();
		B_MouldLendConfirmData confirmData = new B_MouldLendConfirmData();
		
		MouldLendRegisterService service = new MouldLendRegisterService();
		String guid = "";
		boolean isConfirmDataExists = false;
		String moudLendNo = "1608J";
		
		ts = new BaseTransaction();	
		try {
			ts.begin();
			if (key == null || key.equals("")) {
				guid = BaseDAO.getGuId();									
				dbData.setId(guid);	
				
				BaseModel dataModel = new BaseModel();
				BaseQuery baseQuery = null;
				dataModel.setQueryFileName("/business/mouldlendregister/mouldlendregisterquerydefine");
				dataModel.setQueryName("mouldlendregisterquerydefine_getserialno");
				baseQuery = new BaseQuery(request, dataModel);
				ArrayList<HashMap<String, String>> mouldLendNoMap = baseQuery.getYsQueryData(0,0);				
				if (mouldLendNoMap.size() > 0) {
					moudLendNo += String.format("%03d", Integer.parseInt(mouldLendNoMap.get(0).get("serialNo")));
				}
				
				dbData.setMouldlendno(moudLendNo);
				dbData.setProductmodel(service.getJsonData(data, "productModelId"));
				dbData.setLendfactory(service.getJsonData(data, "mouldFactoryId"));
				dbData.setLendtime(service.getJsonData(data, "lendTime"));
				dbData.setReturntime(service.getJsonData(data, "returnTime"));
				dbData = service.updateMouldLendRegisterModifyInfo(dbData, userInfo);
				dao.Create(dbData);
				key = guid;
			} else {
				dbData.setId(key);
				dbData = (B_MouldLendRegisterData)dao.FindByPrimaryKey(dbData);
				dbData.setProductmodel(service.getJsonData(data, "productModelId"));
				dbData.setLendfactory(service.getJsonData(data, "mouldFactoryId"));
				dbData.setLendtime(service.getJsonData(data, "lendTime"));
				dbData.setReturntime(service.getJsonData(data, "returnTime"));
				dbData = service.updateMouldLendRegisterModifyInfo(dbData, userInfo);
				dao.Store(dbData);
			}
			
			if (confirm.equals("1")) {
				StringBuffer sql = new StringBuffer();
				sql.append("UPDATE b_MouldLendDetail SET status = '1' ");
				sql.append(", ModifyTime = '" + CalendarUtil.fmtDate() + "'");								
				sql.append(", ModifyPerson = '" + userInfo.getUserId() + "'");								
				sql.append(" WHERE lendId = '" + service.getJsonData(data, "keyBackup") + "' AND DELETEFLAG = '" + BusinessConstants.DELETEFLG_UNDELETE + "'");
				BaseDAO.execUpdate(sql.toString());
			}
			
			try {
				confirmData.setLendid(dbData.getId());
				confirmData = (B_MouldLendConfirmData)confirmDao.FindByPrimaryKey(confirmData);
				isConfirmDataExists = true;
			}
			catch(Exception e) {
				
			}

			confirmData.setProposer(service.getJsonData(data, "proposer"));
			confirmData.setBrokerage(service.getJsonData(data, "brokerage"));
			confirmData.setConfirm(confirm);
			confirmData = service.updateMouldLendConfirmModifyInfo(confirmData, userInfo);
			
			if (isConfirmDataExists) {
				confirmDao.Store(confirmData);
			} else {
				confirmDao.Create(confirmData);
			}
			
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();									
			throw e;	
		}
		
		return key + "|" + moudLendNo;
    }
    
    public void executeMouldLendRegisterDelete(String keyData, UserInfo userInfo) throws Exception {
		B_MouldLendRegisterDao dao = new B_MouldLendRegisterDao();
		B_MouldLendRegisterData dbData = new B_MouldLendRegisterData();
		B_MouldLendConfirmDao confirmDao = new B_MouldLendConfirmDao();
		B_MouldLendConfirmData confirmData = new B_MouldLendConfirmData();
		
		MouldLendRegisterService service = new MouldLendRegisterService();
		
		int count = 0;										
												
		ts = new BaseTransaction();										
												
		try {										
			ts.begin();									
			String removeData[] = keyData.split(",");									
			for (String key:removeData) {
				StringBuffer sql = new StringBuffer("");
				
				dbData.setId(key);
				dbData = (B_MouldLendRegisterData)dao.FindByPrimaryKey(dbData);
				dbData = MouldLendRegisterService.updateMouldLendRegisterModifyInfo(dbData, userInfo);
				dbData.setDeleteflag(BusinessConstants.DELETEFLG_DELETED);
				dao.Store(dbData);
				
				sql = new StringBuffer("");								
				sql.append("UPDATE b_MouldLendDetail SET DeleteFlag = '" + BusinessConstants.DELETEFLG_DELETED + "' ");								
				sql.append(", ModifyTime = '" + CalendarUtil.fmtDate() + "'");								
				sql.append(", ModifyPerson = '" + userInfo.getUserId() + "'");								
				sql.append(" WHERE lendId = '" + dbData.getId() + "' AND DELETEFLAG = '" + BusinessConstants.DELETEFLG_UNDELETE + "'");								
				BaseDAO.execUpdate(sql.toString());	
				
				confirmData.setLendid(dbData.getId());
				confirmData = (B_MouldLendConfirmData)confirmDao.FindByPrimaryKey(confirmData);
				confirmData = MouldLendRegisterService.updateMouldLendConfirmModifyInfo(confirmData, userInfo);
				confirmData.setDeleteflag(BusinessConstants.DELETEFLG_DELETED);
				confirmDao.Store(confirmData);
				
				count++;								
												
			}									
			ts.commit();									
		}										
		catch(Exception e) {										
			ts.rollback();									
			throw e;									
		}
    }

    
    public void executeMouldLendDetailDelete(String keyData, UserInfo userInfo) throws Exception {												
    	B_MouldLendDetailData dbData = new B_MouldLendDetailData();											
    	B_MouldLendDetailDao dao = new B_MouldLendDetailDao();

		int count = 0;										
												
		ts = new BaseTransaction();										
												
		try {										
			ts.begin();

			String removeData[] = keyData.split(",");									
			for (String key:removeData) {									
				dbData.setId(key);
				dbData = (B_MouldLendDetailData)dao.FindByPrimaryKey(dbData);
				dbData = MouldLendRegisterService.updateLdModifyInfo(dbData, userInfo);
				dbData.setDeleteflag(BusinessConstants.DELETEFLG_DELETED);
				dao.Store(dbData);
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
