package com.ys.util;

import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class XmlUtil {
	
	private static final String DATASOURCEDEFINITION = "app-context.xml";
	public static final String XMLSUFFIX = ".xml";
	
	public static Object getContent(String xmlFileName, String beanName) {
	    
		Object bean = null;

		ApplicationContext ctx = new ClassPathXmlApplicationContext("/setting/" + xmlFileName);
    	bean = ctx.getBean(beanName);
    	
    	//����ڴ˴��ر����޷�ȡ��connection
	    //((ClassPathXmlApplicationContext)ctx).close();
	    
	    return bean;
	}
	
	public static Element getRootElement(String xmlFileName) {
		Element element = null;
		try {
			WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
	        ServletContext servletContext = webApplicationContext.getServletContext();  
			
			//HttpServletRequest request = (HttpServletRequest)PolicyContext.getContext("javax.servlet.http.HttpServletRequest");
			//ServletContext servletContext = request.getSession().getServletContext(); 
			ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			//ApplicationContext ctx = new ClassPathXmlApplicationContext(DATASOURCEDEFINITION);
			Resource rs = ctx.getResource("classpath:" + xmlFileName);

			if (rs.exists()) {
				SAXReader reader = new SAXReader();
				Document doc = reader.read(rs.getFile());
				element = doc.getRootElement();//��ȡ��Ԫ��
			}
			rs = null;
			ctx = null;
			//((ClassPathXmlApplicationContext)ctx).close();

		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}

		return element;
	}
	
	public static HashMap<String, String> getContentList(Element element, String contentName) {
		HashMap<String, String> contentList = new HashMap<String, String>();
		
		if (element != null) {
			//�������ȡ��Ԫ���µ���Ԫ�����
			Iterator<Element> iter = element.elementIterator();
			while(iter.hasNext()){
			    Element el = (Element)iter.next();
			    contentList.put(el.getText(), "");
			}
		}
		
		return contentList;
	}
	

	

}
