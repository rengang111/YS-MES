package com.ys.system.service.test;

import java.util.HashMap;

import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ys.system.db.dao.S_DICDao;
import com.ys.system.db.data.S_DICData;
import com.ys.system.service.common.BaseService;
import com.ys.util.basedao.BaseTransaction;
import com.ys.util.basequery.BaseQuery;
import com.ys.util.basequery.common.BaseModel;


@Service
public class TestService extends BaseService {
 
	//@EJB(beanName="DbUpdateEjb") com.ys.system.ejb.DbUpdateEjbLocal DbUpdateEjb;
	Context ctx;
	
	public void test(String data1, String data2) throws Exception {
		
		BaseTransaction xT = new BaseTransaction();
		
		try {
			
			xT.begin();
			
			S_DICDao dicDao = new S_DICDao();
			S_DICData dicData = new S_DICData();
	
			dicData.setDicid("033");
			dicData.setDictypeid("A2");
			dicData = (S_DICData)dicDao.FindByPrimaryKey(dicData);
			dicData.setJianpin("sadmin123");
			//dicData.setJianpin(data1);
			dicDao.Store(dicData);
			
			dicData.setDicid("034");
			dicData.setDictypeid("A2");
			dicData = (S_DICData)dicDao.FindByPrimaryKey(dicData);
			dicData.setJianpin("sadmin124");
			//dicData.setJianpin(data2);
			dicDao.Store(dicData);
			
			HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
			BaseModel dataModel = new BaseModel();
			dataModel.setQueryFileName("/business/projecttask/projecttaskquerydefine");
			dataModel.setQueryName("projecttaskquerydefine_preCheck");
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			BaseQuery baseQuery = new BaseQuery(request, dataModel);
			userDefinedSearchCase.put("keyword", "123");
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
			
			baseQuery.getQueryData();			
			
			xT.commit();
			System.out.println("test123 is over");
		}
		catch(Exception e) {
			xT.rollback();
		}
		
    }
	
	public void test1(String data1, String data2) throws Exception {
		
		BaseTransaction xT = new BaseTransaction();
		
		try {

			xT.begin();
			S_DICDao dicDao = new S_DICDao();
			S_DICData dicData = new S_DICData();
	
			dicData.setDicid("033");
			dicData.setDictypeid("A2");
			dicData = (S_DICData)dicDao.FindByPrimaryKey(dicData);
			dicData.setJianpin("sadmin123");
			
			dicData.setDicid("099");
			dicDao.Create(dicData);
			//dicData.setJianpin(data1);
			//dicDao.Store(dicData);
			xT.commit();
			System.out.println("test124 is over.");
			//rollback();
		}
		catch(Exception e) {
			xT.rollback();
		}
    }
}
