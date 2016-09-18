package com.ys.system.service.test;

import javax.naming.Context;

import org.springframework.stereotype.Service;

import com.ys.system.db.dao.S_DICDao;
import com.ys.system.db.data.S_DICData;
import com.ys.system.service.common.BaseService;
import com.ys.util.basedao.BaseTransaction;


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
			
			for(int i = 0; i < 1000000; i++) {
				System.out.println(i);
			}
			
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
