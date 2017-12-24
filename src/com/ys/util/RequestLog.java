package com.ys.util;
  
import org.apache.log4j.Level;  
import org.apache.log4j.Logger;  
import org.apache.log4j.net.SyslogAppender;  
  
public class RequestLog {
      
    /** 
     * 继承Level 
     * @author rengang 
     * 
     */  
    private static class RequestLogLevel extends Level{  
        public RequestLogLevel(int level, String levelStr, int syslogEquivalent) {  
            super(level, levelStr, syslogEquivalent);  
        }         
    }  
      
    /** 
     * 自定义级别名称，以及级别范围 
     */  
    private static final Level RequestLevel = new RequestLogLevel(20050,"REQUEST",SyslogAppender.LOG_LOCAL0);  
      
    /** 
     * 使用日志打印logger中的log方法 
     *  
     * @param logger 
     * @param objLogInfo 
     */  
    public static void requestLog(Logger logger,Object objLogInfo){  
        logger.log(RequestLevel, objLogInfo);  
    }    
      
}