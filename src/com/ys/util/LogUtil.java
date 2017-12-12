package com.ys.util;
import  java.io.*;
public  class LogUtil{
    static String log="";
/*================================================================ 
* 
* 函 数 名：Log 
* 
* 参　　数： 
* 
* String rows,String Tr_code
* 
* 功能描述: 
* 
* 同步写入日志
* 
* 返 回 值：
* 
* 抛出异常： 
* 
* 作　　者：
* 
================================================================*/     
public static synchronized void Log(String rows,String Tr_code)  {
    String tr_date = "";
    String getdate = CalendarUtil.getDateyymmdd();
    String hostdir = System.getProperty("user.dir");
    String tmp = "";
    //String tpPath = getClass().getResource("").toString();
    //System.out.println(hostdir);
    if(hostdir.indexOf("bin")== -1){
     tmp = "\\bin\\log"+"\\Gr_Log"+getdate+".txt";
    }else{
     tmp = "\\log"+"\\Gr_Log"+getdate+".txt";
     }
    log =hostdir+tmp;
    //System.out.println(log);
//tr_date = getdate.getdatetime();
    try{
        RandomAccessFile  rf1  =  new  RandomAccessFile(log,"rw");
        //if(rf1.length() > 30000){
        // rf1.close();
        // File del = new File(log);
        // del.delete();
// rf1  =  new  RandomAccessFile(log,"rw");
        //}
        rf1.seek(rf1.length());
        rf1.writeBytes(new String("时间 : ".getBytes("gb2312"),"iso-8859-1")+tr_date+"  ");
rf1.writeBytes(new String("类型 : ".getBytes("gb2312"),"iso-8859-1")+new String(Tr_code.getBytes("gb2312"),"iso-8859-1")+"\n");        
        rf1.writeBytes(new String(rows.getBytes("gb2312"),"iso-8859-1")+"\n");
rf1.writeBytes("\n");
//rf1.writeBytes("- - -  - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - "+"\n");        
        rf1.close();
    }catch(Exception e){
    
     System.out.println("errorlog.."+e.getMessage());
    }
}
public void Read_rows(){
   String hostdir = System.getProperty("user.dir");
   //GetDate getdate = new GetDate();
   String tmp = "\\bin\\log"+"\\Gr_Log"+CalendarUtil.getDateyymmdd()+".txt";
   String steName=hostdir+tmp;
   //String steName =hostdir+"\\Gr_Log.txt";
   //String steName =hostdir+"\\Gr_Log.txt";
   String  record  =  new  String();
   RandomAccessFile  rf2;
   try{
   rf2  =  new  RandomAccessFile(log,"r");
   int i = 0;
   while  ((record  =  rf2.readLine())  !=  null)  {
           System.out.println("Value  "+i+":"+record);
   }
   rf2.close();}catch(Exception e){return;}

 }
public static void main(String args[]){
	LogUtil.Log("text","test");
}
}