package com.ys.util.basequery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import com.ys.util.basequery.common.BaseModel;

/**
 * 分页显示的标准类<BR>
 * 
 * 基本操作,是先给予-当前页数一共的数据条数-每页显示的条数<BR>
 * 然后在初始化该类,得到总共页数,和开始序号和结束序号<BR>
 * 然后数据库分页用到开始序号和结束序号,得到数据集合后赋值给该类的list属性<BR>
 * 然后把该类发送到jsp页面,进行访问
 * 
 * @ClassName: PageBean
 * @Description: 分页配置
 * @author 李晓峰
 * @date 2015年12月4日 下午1:30:00
 * @version 1.0
 */
public class PageBean implements Serializable
{
	
	private static int MAXPAGESDISPLAY = 9;
	
	private int pageRecordList[] = {5, 10, 20, 50, 100, 200, 500};
	
    /** 当前页 */
    private int pageIndex;

    /** 总页数 */
    private int totalPages;

    /** 数据条数 */
    private long count;

    /** 每页的数据条数 */
    private int recordsPerPage = 20;

    /** 起始数据位置 */
    private int start;

    /** 分页代码 */
    private String pageHtml = "";

    /** form名称 */
    private String form = "form";

    /** action名称 */
    private String action = "";

    /** 分页导航 */
    private String naviHtml = "";

    /** 显示数据 */
    private ArrayList<ArrayList<String>> viewData = null;
    
    /** 排序字段 */
    private String sortFieldList = "";
    
    /** 画面数据*/
    HttpServletRequest request = null;
    
    /** 共通参数类*/
    BaseModel commonModel = null;
    
    /**翻页导航栏设定 */
    HashMap<String, Boolean> naviBarSetMap = new HashMap<String, Boolean>();
    
    /**是否翻页区分*/
    String turnPageFlg = "";
    
    /** 构造函数
     * 
     * @param HttpServletRequest request
     */
    public PageBean(HttpServletRequest request, BaseModel commonModel) {
    	this.request = request;
    	this.commonModel = commonModel;
    	initBean();
    }

    public void initBean() {

   		this.pageIndex = commonModel.getPageIndex();

    	String flg = commonModel.getFlg();
    	if (flg == null || flg.equals("")) {
    		this.pageIndex = commonModel.getStartIndex();
    	} else {
    		this.pageIndex = this.pageIndex - 1;
    	}
    	
    	int recordPerPageTemp = commonModel.getRecordPerPage();
    	if (recordPerPageTemp == 0) {
    		this.recordsPerPage = pageRecordList[0];
    	} else {
    		this.recordsPerPage = recordPerPageTemp;
    	}
    	
   		this.sortFieldList = commonModel.getSortFieldList();

    	String turnPageFlgTemp = commonModel.getTurnPageFlg();
    	if (turnPageFlgTemp == null || turnPageFlgTemp.equals("")) {
    		this.pageIndex = 0;
    	}

    }
    
    /**
     * form名称的设定
     * 
     * @param form
     *            form名称
     */
    public void setForm(String form)
    {
        this.form = form;
    }

    /**
     * form名称的取得
     * 
     * @return form名称
     */
    public String getForm()
    {
        return form;
    }

    public String getHtml()
    {
    	//initBean();

    	setPageInfo();
    	
        long nextPage = pageIndex + 1;
        long frontPage = pageIndex - 1;
        int endPage = totalPages - 1;

        StringBuilder sb = new StringBuilder();
        //sb.append("<input type=hidden name=totalPages value='" + String.valueOf(totalPages) + "'");
 
        sb.append(" <!--翻页代码 -->");
        
        if (pageIndex == 0)
        {
        	if (getNaviStartEndPage()) {
        		sb.append(" <span>首页</span>");
        	}
            sb.append(" <span>上一页</span>");
        }
        else
        {
        	if (getNaviStartEndPage()) {
	            sb.append(" <a onclick=\"goToPage('"
	                    + this.form + "','" + "0"
	                    + "','');\"href=\"javascript:void(0);\">首页</a>");
        	}
            sb.append(" <a onclick=\"goToPage('"
                    + this.form + "','" + frontPage
                    + "','');\" href=\"javascript:void(0);\">上一页</a>");
        }

        sb.append(this.naviHtml);

        if ((pageIndex + 1) >= totalPages)
        {
            sb.append(" <span>下一页</span>");
            if (getNaviStartEndPage()) {
            	sb.append(" <span>尾页</span>");
            }
        }
        else
        {
            sb.append(" <a onclick=\"goToPage('"
                    + this.form + "','" + nextPage
                    + "','');\" href=\"javascript:void(0);\">下一页</a>");
            if (getNaviStartEndPage()) {
	            sb.append(" <a onclick=\"goToPage('"
	                    + this.form + "','" + endPage
	                    + "','')\" href=\"javascript:void(0);\">尾页</a>");
            }
        }
        if (getNaviPageCount()) {
	        sb.append(" 共     ");
	        sb.append(totalPages);
	        sb.append(" 页&nbsp;&nbsp;");
        }
        if (getNaviRecordCount()) {
	        sb.append(" 共     ");
	        sb.append(count);
	        sb.append(" 条记录&nbsp;&nbsp;");
        }
        if (getNaviPageRedirect()) {
	        sb.append(" 到第 <input type=\"text\" onBlur=\"forEmpty(this);\" onkeyup=\"this.value=((this.value=this.value.replace(/\\D/g,'0'))>"
	                + totalPages
	                + "?"
	                + totalPages
	                + ":this.value)\" onafterpaste=\"this.value=((this.value=this.value.replace(/\\D/g,'0'))>"
	                + totalPages + "?" + totalPages + ":this.value)\" ");
	        sb.append(" name=\"pageIndex\" id=\"pageIndex\" value=\""
	                + (count == 0 ? pageIndex : (pageIndex + 1)) + "\" /> 页 ");
	        if (totalPages > 1)
	        {
	            sb.append(" <a onclick=\"goToPage('"
	                    + this.form
	                    + "','0','flg');\"  href=\"javascript:void(0);\">跳转</a>");
	        }
	        else
	        {
	            sb.append(" <span>跳转</span> ");
	        }
        }
        if (getNaviPageRecordCount()) {
	        sb.append("每页显示");
	        sb.append("<select name='recordPerPage' onChange='recordPerPageChanged();'>/r");
	        for (int i = 0; i < pageRecordList.length; i++) {
	        	String item = String.valueOf(pageRecordList[i]);
	        	if (this.recordsPerPage == pageRecordList[i]) {
	        		sb.append("<option value=" + item + " selected='selected'>" + item + "</option>/r");
	        	} else {
	        		sb.append("<option value=" + item + ">" + item + "</option>/r");
	        	}
	        }
	        sb.append("<select>");
	        sb.append("条记录");
        }
        //sb.append(" </div> ");

        return sb.toString();
    }

    private void setPageInfo()
    {
        // 计算总页数
        if (count > 0)
        {
            totalPages = (int) (count / recordsPerPage);
            if (count % recordsPerPage != 0)
                totalPages++;

            pageIndex = pageIndex >= totalPages ? totalPages - 1
                    : (pageIndex - 1 < 0 ? 0 : pageIndex);
            start = pageIndex * recordsPerPage;
        }
        else
        {
            pageIndex = 0;
            start = 0;
        }

        this.createNavi(MAXPAGESDISPLAY);
    }

    public void createNavi(long num)
    {
        long showNum = num + 1;
        long startIndex = 1;
        long curentPage = pageIndex + 1;
        long total = 0;

        if (totalPages <= showNum)
        {
            total = totalPages;
        }
        else if (totalPages > showNum && curentPage < showNum)
        {
            total = showNum;
        }
        else
        {
            if (((curentPage - 1) % num) == 0)
                startIndex = curentPage;
            else
                startIndex = curentPage - curentPage % showNum;

            if ((totalPages - curentPage) >= showNum)
                total = curentPage + num;
            else
                total = totalPages;
        }

        StringBuilder navi = new StringBuilder();
        int breakFlg = 0;

        if (curentPage >= 5)
        {
            if (curentPage - 4 >= 1)
                startIndex = curentPage - 4;
            if (curentPage + 4 <= totalPages)
                total = curentPage + 4;
        }

        for (long i = startIndex; i <= total; i++, breakFlg++)
        {
            if (breakFlg == showNum)
                break;
            if (i == curentPage)
            {
                navi.append("<span>" + i + "</span>");
            }
            else
            {
                navi.append("<a onclick=\"goToPage('"
                        + this.form
                        + "','"
                        + (i - 1)
                        + "',''); \" href=\"javascript:void(0);\">"
                        + i + "</a>");

            }
            //System.out.println(navi);
        }

        this.naviHtml = navi.toString();
    }
    
    /**
     * 当前页数的设定
     * 
     * @param pageIndex
     *            当前页数
     */
    public void setPageIndex(int pageIndex)
    {
        this.pageIndex = pageIndex;
    }

    /**
     * 当前页数的取得
     * 
     * @return 当前页数
     */
    public int getPageIndex()
    {
        return pageIndex;
    }

    /**
     * 一共的页数的设定
     * 
     * @param totalPages
     *            一共的页数
     */
    public void setTotalPages(int totalPages)
    {
        this.totalPages = totalPages;
    }

    /**
     * 一共的页数的取得
     * 
     * @return 一共的页数
     */
    public int getTotalPages()
    {
        return totalPages;
    }

    /**
     * 数据条数的设定
     * 
     * @param count
     *            数据条数
     */
    public void setCount(long count)
    {
        this.count = count;
        this.setPageInfo();
        //this.setPageHtml(getHtml());
    }

    /**
     * 数据条数的取得
     * 
     * @return 数据条数
     */
    public long getCount()
    {
        return count;
    }

    /**
     * 每页的数据条数的设定
     * 
     * @param recordsPerPage
     *            每页的数据条数
     */
    public void setRecordsPerPage(int recordsPerPage)
    {
        this.recordsPerPage = recordsPerPage;
        this.setPageInfo();
    }

    /**
     * 每页的数据条数的取得
     * 
     * @return 每页的数据条数
     */
    public int getRecordsPerPage()
    {
        return recordsPerPage;
    }

    /**
     * 起始数据位置的设定
     * 
     * @param start
     *            起始数据位置
     */
    public void setStart(int start)
    {
        this.start = start;
    }

    /**
     * 起始数据位置的取得
     * 
     * @return 起始数据位置
     */
    public long getStart()
    {
        return start;
    }

    /**
     * 分页的设定
     * 
     * @param pageHtml
     *            分页
     */
    public void setPageHtml(String pageHtml)
    {
        this.pageHtml = pageHtml;
    }

    /**
     * 分页的取得
     * 
     * @return 分页
     */
    public String getPageHtml()
    {
        return pageHtml;
    }

    /**
     * action事件的设定
     * 
     * @param action
     *            action事件
     */
    public void setAction(String action)
    {
        this.action = action;
    }

    /**
     * action事件的取得
     * 
     * @return action事件
     */
    public String getAction()
    {
        return action;
    }
    
    public void setViewData(ArrayList<ArrayList<String>> viewData) {
    	this.viewData = viewData;
    }
    
    public ArrayList<ArrayList<String>> getViewData() {
    	return this.viewData;
    }
    
    public void setSortFieldList(String sortFieldList) {
    	this.sortFieldList = sortFieldList;
    }
    
    public String getSortFieldList() {
    	return this.sortFieldList;
    }

    public void setRequest(HttpServletRequest request) {
    	this.request = request;
    }
    public HttpServletRequest getRequest() {
    	return this.request;
    }

    public void setCommonModel(BaseModel commonModel) {
    	this.commonModel = commonModel;
    	initBean();
    }
    public BaseModel getCommonModel() {
    	return this.commonModel;
    }    
    
    public void setNaviStartEndPage(boolean isShow) {
    	this.naviBarSetMap.put("startendpage", Boolean.valueOf(isShow));
    }
    public boolean getNaviStartEndPage() {
    	boolean isShow = true;
    	if (naviBarSetMap.containsKey("startendpage")) {
    		isShow = naviBarSetMap.get("startendpage").booleanValue();
    	}
    	return isShow;
    }
    
    public void setNaviPageCount(boolean isShow) {
    	this.naviBarSetMap.put("pagecount", Boolean.valueOf(isShow));
    }
    public boolean getNaviPageCount() {
    	boolean isShow = true;
    	if (naviBarSetMap.containsKey("pagecount")) {
    		isShow = naviBarSetMap.get("pagecount").booleanValue();
    	}
    	return isShow;
    }
    
    public void setNaviRecordCount(boolean isShow) {
    	this.naviBarSetMap.put("recordcount", Boolean.valueOf(isShow));
    }
    public boolean getNaviRecordCount() {
    	boolean isShow = true;
    	if (naviBarSetMap.containsKey("recordcount")) {
    		isShow = naviBarSetMap.get("recordcount").booleanValue();
    	}
    	
    	return isShow;
    }
    
    public void setNaviPageRedirect(boolean isShow) {
    	this.naviBarSetMap.put("pageredirect", Boolean.valueOf(isShow));
    }
    public boolean getNaviPageRedirect() {
    	boolean isShow = true;
    	if (naviBarSetMap.containsKey("pageredirect")) {
    		isShow = naviBarSetMap.get("pageredirect").booleanValue();
    	}
    	
    	return isShow;
    }

    public void setNaviPageRecordCount(boolean isShow) {
    	this.naviBarSetMap.put("pagerecordcount", Boolean.valueOf(isShow));
    }
    public boolean getNaviPageRecordCount() {
    	boolean isShow = true;
    	if (naviBarSetMap.containsKey("pagerecordcount")) {
    		isShow = naviBarSetMap.get("pagerecordcount").booleanValue();
    	}
    	
    	return isShow;
    }

}
