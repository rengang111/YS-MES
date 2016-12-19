package com.ys.business.ejb;												
												
import com.ys.business.db.dao.B_ContactDao;
import com.ys.business.db.dao.B_CustomerAddrDao;
import com.ys.business.db.dao.B_CustomerDao;
import com.ys.business.db.dao.B_ESRelationFileDao;
import com.ys.business.db.dao.B_ExternalSampleDao;
import com.ys.business.db.dao.B_MaterialCategoryDao;
import com.ys.business.db.dao.B_OrganizationDao;
import com.ys.business.db.dao.B_ProcessControlDao;
import com.ys.business.db.dao.B_ProjectRelationFileDao;
import com.ys.business.db.dao.B_ProjectTaskCostDao;
import com.ys.business.db.dao.B_ProjectTaskDao;
import com.ys.business.db.dao.B_SupplierDao;
import com.ys.business.db.data.B_ContactData;
import com.ys.business.db.data.B_CustomerAddrData;
import com.ys.business.db.data.B_CustomerData;
import com.ys.business.db.data.B_ESRelationFileData;
import com.ys.business.db.data.B_ExternalSampleData;
import com.ys.business.db.data.B_MaterialCategoryData;
import com.ys.business.db.data.B_OrganizationData;
import com.ys.business.db.data.B_ProcessControlData;
import com.ys.business.db.data.B_ProjectRelationFileData;
import com.ys.business.db.data.B_ProjectTaskCostData;
import com.ys.business.db.data.B_ProjectTaskData;
import com.ys.business.db.data.B_SupplierData;
import com.ys.business.db.data.CommFieldsData;
import com.ys.business.service.contact.ContactService;
import com.ys.business.service.customer.CustomerService;
import com.ys.business.service.customeraddr.CustomerAddrService;
import com.ys.business.service.esrelationfile.EsRelationFileService;
import com.ys.business.service.externalsample.ExternalSampleService;
import com.ys.business.service.processcontrol.ProcessControlService;
import com.ys.business.service.projecttask.ProjectTaskService;
import com.ys.business.service.supplier.SupplierService;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.system.service.common.BaseService;
import com.ys.util.CalendarUtil;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basedao.BaseTransaction;
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
        												
}												
