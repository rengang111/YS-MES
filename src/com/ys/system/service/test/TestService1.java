package com.ys.system.service.test;

import javax.naming.Context;

import org.springframework.stereotype.Service;

import com.ys.system.db.dao.S_DICDao;
import com.ys.system.db.data.S_DICData;
import com.ys.system.service.common.BaseService;
import com.ys.util.basedao.BaseTransaction;

@Service
public class TestService1 extends BaseService {

	Context ctx;

	public void test() throws Exception {

		//ctx = getInitialContext();
		//String lookupName = getLookupName();
        // 3. Lookup and cast
        //DbUpdateEjbRemote bean = (DbUpdateEjbRemote)ctx.lookup(lookupName);
        
        //bean.executeUpdate();
        
        //ctx.close();
        

	}

	public void test1(String data1, String data2) throws Exception {
		
		BaseTransaction ts = new BaseTransaction();
		
		try {
			
			ts.begin();
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
			ts.commit();
			System.out.println("test124 is over.");
			//rollback();
		}
		catch(Exception e) {
			ts.rollback();
		}
    } 
}
