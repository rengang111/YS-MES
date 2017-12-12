package com.ys.util;

import org.aopalliance.intercept.MethodInterceptor;  
import org.aopalliance.intercept.MethodInvocation;  
import org.apache.log4j.Logger;  
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
  
/** 
 * Spring 统一日志处理实现类 
 * @author Andy Chan 
 *  
 */  
@Aspect()
public class LogInterceptor 
{  
	
    @Autowired
	private JdbcTemplate jdbcTemplate;
  	
	@Before(value="execution(* com.ys.business.db.dao.*.*(..))")
    public void invoke(JoinPoint point) 
    {  
        //Logger loger = Logger.getLogger(invocation.getClass());  
  
        //loger.info("--Log By Andy Chan -----------------------------------------------------------------------------");  

        System.out.println("LogInterceptor step 1 ");
        
//        loger.info(invocation.getMethod() + ":BEGIN!--(Andy ChanLOG)");// 方法前的操作  
//        Object obj = invocation.proceed();// 执行需要Log的方法  
//        loger.info(invocation.getMethod() + ":END!--(Andy ChanLOG)");// 方法后的操作  
//        loger.info("-------------------------------------------------------------------------------------------------");  
//  
//        return obj;  
    }  
	
	@Around(value="execution(* com.ys.business.db.dao.*.*(..))")
	public Object watchPerformance(ProceedingJoinPoint joinpoint) throws Throwable {
    	
    	System.out.println("LogInterceptor step 1 ");
    	
       
        System.out.println("begin!");
        long start = System.currentTimeMillis();
        
        Object retVal = joinpoint.proceed();
        
        long end = System.currentTimeMillis();
        System.out.println("end!        performance took "+(end-start)+" milliseconds");
        
        try{
        	
        	String sql = "insert into t_performance(per_id,joint,method,duration,execution_time)values(0,'"
        			+ joinpoint.getSignature().toShortString() + "','"
        			+ joinpoint.getSignature().getName() + "',"
        			+ (end-start) + ",now())";
        	
        	System.out.println(sql);

        	jdbcTemplate.execute(sql);
            
        }catch(Exception ex1){
        	System.out.println(ex1.getMessage());
        }
        
        return retVal;
        
    }
  
}  
