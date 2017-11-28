package com.ys.util.basequery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Attribute;
import org.dom4j.Element;

import com.ys.system.action.model.common.SqlInfo;
import com.ys.util.XmlUtil;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basequery.common.BaseModel;

public class BaseQuery {
	
	public static final String USERID = "userId";
	public static final String USERTYPE = "userType";
	private final String QUERYDEFINEDRESOURCE_PATH = "/query/";
	private HashMap<String, QueryInfoBean> queryInfoMap = new HashMap<String, QueryInfoBean>();
	private HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
	private String menuId = "";
	
	/**翻页信息   */
	private PageBean pageBean;
	private HttpServletRequest request;
	
	private String sql = "";
	private String sqlCount = "";
	
	private ArrayList<ArrayList<String>> viewData = new ArrayList<ArrayList<String>>();
	private ArrayList<HashMap<String,String>> viewYsData = new ArrayList<HashMap<String,String>>();
	
	/**基础信息    */
	private BaseModel commonModel = new BaseModel();
	
	public BaseQuery(HttpServletRequest request, BaseModel commonModel) throws Exception {
		this.request = request;
		this.commonModel = commonModel;
		
		pageBean = new PageBean(request, commonModel);
				
		//TODO
		//清空语句仅供开发时使用
		queryInfoMap.clear();
	}
		
	
    public void setCommonModel(BaseModel commonModel) {
    	this.commonModel = commonModel;
    	pageBean = new PageBean(request, commonModel);
    }
    public BaseModel getCommonModel() {
    	return this.commonModel;
    }
    
    public void setRequest(HttpServletRequest request) {
    	this.request = request;
    	pageBean = new PageBean(request, commonModel);
    }
    
    public HttpServletRequest getRequest() {
    	return this.request;
    }
    
    public String getTurnPageHtml() {
    	String html = "";
    	
    	if (pageBean.getTotalPages() > 0) {
    		html = pageBean.getHtml();
    	}
    	
    	return html;
    }
    
    public void setNaviStartEndPage(boolean isShow) {
    	this.pageBean.setNaviStartEndPage(isShow);
    }
    public boolean getNaviStartEndPage() {
    	return this.pageBean.getNaviStartEndPage();
    }
    
    public void setNaviPageCount(boolean isShow) {
    	this.pageBean.setNaviPageCount(isShow);
    }
    public boolean getNaviPageCount() {
    	return this.pageBean.getNaviPageCount();
    }
    
    public void setNaviRecordCount(boolean isShow) {
    	this.pageBean.setNaviRecordCount(isShow);
    }
    public boolean getNaviRecordCount() {
    	return this.pageBean.getNaviRecordCount();
    }
    
    public void setNaviPageRedirect(boolean isShow) {
    	this.pageBean.setNaviPageRedirect(isShow);
    }
    public boolean getNaviPageRedirect() {
    	return this.pageBean.getNaviPageRedirect();
    }

    public void setUserDefinedSearchCase(HashMap<String, String> userDefinedSearchCase) {
    	this.userDefinedSearchCase = userDefinedSearchCase;
    }
    public HashMap<String, String> getUserDefinedSearchCase() {
    	return this.userDefinedSearchCase;
    }
    
    public void setNaviPageRecordCount(boolean isShow) {
    	this.pageBean.setNaviPageRecordCount(isShow);
    }
    public boolean getNaviPageRecordCount() {
    	return this.pageBean.getNaviPageRecordCount();
    }    
    
    public void setMenuId(String menuId) {
    	this.menuId = menuId;
    }
    public String getMenuId() {
    	return this.menuId;
    }    
    
    public String getSql() throws Exception {
    	this.sql = getQuerySql(request, commonModel.getQueryName(), commonModel.getUserDefinedWhere());
    	this.sql = QueryInfoBean.addUserDefinedSort(this.sql, pageBean.getSortFieldList());
    	
    	return this.sql;
    }
    
    public int getRecodCount() throws Exception {
    	int recordCount = getRecordCount(sqlCount, getQueryConnectionDefine(commonModel.getQueryName()));
    	pageBean.setCount(recordCount);
    	
    	return recordCount;
    }
    public int getRecodCount(String where) throws Exception {
    	int recordCount = getRecordCount(sqlCount, getQueryConnectionDefine(commonModel.getQueryName()),where);
    	pageBean.setCount(recordCount);
    	
    	return recordCount;
    }
    public ArrayList<ArrayList<String>> getFullData() throws Exception {
		sql = getSql();
		
    	this.viewData = getTurnPageData(sql, getQueryConnectionDefine(commonModel.getQueryName()), 0, 0, false);
    	return this.viewData;
    }
    
    public ArrayList<HashMap<String, String>> getYsFullData() throws Exception {
		sql = getSql();		
		int recordCount = getRecodCount();
		this.viewYsData =  getYsTurnPageData(sql, getQueryConnectionDefine(commonModel.getQueryName()), 0, 0, true);
		this.commonModel.setYsViewData(viewYsData);
		this.commonModel.setRecordCount(recordCount);
		
		return viewYsData;
    }
        
    public ArrayList<HashMap<String, String>> getYsFullData(String sql) throws Exception {
				
    	this.viewYsData =  getYsTurnPageData(sql, getQueryConnectionDefine(commonModel.getQueryName()), 0, 0, true);
		this.commonModel.setYsViewData(viewYsData);
		this.commonModel.setRecordCount(viewYsData.size());
		return viewYsData;
    	
    }
    
    private ArrayList<ArrayList<String>> getTurnPageData() throws Exception {
    	this.viewData = getTurnPageData(sql, getQueryConnectionDefine(commonModel.getQueryName()), pageBean.getPageIndex(), pageBean.getRecordsPerPage(), true);
		pageBean.setViewData(viewData);
		pageBean.setPageHtml(pageBean.getPageHtml());

    	return this.viewData;
    }
    
	public ArrayList<ArrayList<String>> getQueryData() throws Exception {

		ArrayList<ArrayList<String>> viewData = null;
		
		getSql();
		
		getRecodCount();
		
		viewData = getTurnPageData();
		
		commonModel.setViewData(viewData);
		commonModel.setTurnPageHtml(this.getTurnPageHtml());

		return viewData;

	}
	public ArrayList<HashMap<String, String>> getYsQueryData(String sql, int iStart, int iEnd) throws Exception {

		ArrayList<HashMap<String, String>> ysViewData = null;
		this.sql = sql;
		//getSql();
		
		int recordCount = getRecodCount();
		
		ysViewData = getYsTurnPageData(sql, getQueryConnectionDefine(commonModel.getQueryName()), iStart, iEnd, true);
		
		commonModel.setYsViewData(ysViewData);
		commonModel.setRecordCount(recordCount);
		//commonModel.setTurnPageHtml(this.getTurnPageHtml());

		return ysViewData;

	}

	public ArrayList<HashMap<String, String>> getYsQueryData(
			String sql,String where, int iStart, int iEnd) throws Exception {

		ArrayList<HashMap<String, String>> ysViewData = null;
		this.sql = sql;
		//getSql();
		
		int recordCount = getRecodCount(where);
		
		ysViewData = getYsTurnPageData(sql, getQueryConnectionDefine(commonModel.getQueryName()), iStart, iEnd, true);
		
		commonModel.setYsViewData(ysViewData);
		commonModel.setRecordCount(recordCount);
		//commonModel.setTurnPageHtml(this.getTurnPageHtml());

		return ysViewData;

	}

	public ArrayList<HashMap<String, String>> getYsQueryData(int iStart, int iEnd) throws Exception {

		ArrayList<HashMap<String, String>> ysViewData = null;
		
		getSql();
		
		int recordCount = getRecodCount();
		
		ysViewData = getYsTurnPageData(sql, getQueryConnectionDefine(commonModel.getQueryName()), iStart, iEnd, true);
		
		commonModel.setYsViewData(ysViewData);
		commonModel.setRecordCount(recordCount);
		//commonModel.setTurnPageHtml(this.getTurnPageHtml());

		return ysViewData;

	}
	
	
	public ArrayList<ArrayList<String>> getYsQueryDataList(int iStart, int iEnd) throws Exception {

		ArrayList<ArrayList<String>> ysViewData = null;
		
		getSql();
		
		int recordCount = getRecodCount();
		
		ysViewData = getTurnPageData(sql, getQueryConnectionDefine(commonModel.getQueryName()), iStart, iEnd, true);
		
		commonModel.setViewData(ysViewData);
		commonModel.setRecordCount(recordCount);
		//commonModel.setTurnPageHtml(this.getTurnPageHtml());

		return ysViewData;

	}
	
	public ArrayList<ArrayList<String>> getQueryData(String sql) throws Exception {
		ArrayList<ArrayList<String>> viewData = null;
		
		this.sql = sql;
		
		getRecodCount();
		
		viewData = getTurnPageData();
		
		return viewData;

	}	
	public ArrayList<HashMap<String,String>> getYsQueryData(String sql) throws Exception {
		
		this.viewYsData = getYsFullData(sql);
		
		this.commonModel.setYsViewData(viewYsData);
		
		return viewYsData;

	}		
	//���PDF�ļ�
	//toPdf()	
	
	//���Excel�ļ�
	//toExcel()
	
	//���Word�ļ�
	//toWord
	
	//���Text�ļ�
	//toText
	
	private String getQuerySql(HttpServletRequest request, String queryName, String userDefinedWhere) throws Exception {
		QueryInfoBean queryInfo = null;
		String sql = "";
		
		if (!queryInfoMap.containsKey(queryName)) {
			getQueryInfo(queryName, request);
		}
		if (queryInfoMap.containsKey(queryName)) {
			queryInfo = queryInfoMap.get(queryName);
		}
		if (queryInfo != null) {
			queryInfo.setUserDefinedWhere(userDefinedWhere);
			queryInfo.setUserDefinedSelect("");
			queryInfo.setUserDefinedUnionSelect("");
			SqlInfo sqlInfo = queryInfo.getSQL(request, this.userDefinedSearchCase, this.menuId);
			sql = sqlInfo.getSql();
			sqlCount = sqlInfo.getSqlCount();
		} else {
			throw new Exception("查询定义(" + queryName + ")不存在");
		}
		
		return sql;
	}
	
	private String getQueryConnectionDefine(String queryName) {
		String poolName = "";
		QueryInfoBean queryInfo = queryInfoMap.get(queryName);
		if (queryInfo != null) {
			poolName = queryInfo.getPoolName();
		}
		return poolName;
	}
	
	private ArrayList<ArrayList<String>> execQuery(String sql, String dataSourceName) throws Exception {
		ArrayList<ArrayList<String>> viewData = null;
		viewData = BaseDAO.execSQL(sql, dataSourceName, 0, 0);
		/*
		//׷���к�
		if (viewData != null) {
			int count = 1;
			for(ArrayList<String> rowData:viewData) {
				rowData.add(0, String.valueOf(count++));
			}
		}
		*/
		return viewData;
	}

	private ArrayList<ArrayList<String>> getTurnPageData(String sql, String dataSourceName, int pageIndex, int recordPerPage, boolean appendNoFlg) throws Exception {
		ArrayList<ArrayList<String>> viewData = null;
		QueryInfoBean queryInfo = queryInfoMap.get(commonModel.getQueryName());
		QueryTotalSumBean queryTotalSum = queryInfo==null?null:queryInfo.getTotalSum();
		
		int startIndex = (pageIndex) * recordPerPage;
		if (startIndex <= 0) {
			startIndex = 1;
		}
		
		viewData = BaseDAO.execSQL(sql, dataSourceName, startIndex, recordPerPage, queryInfo, appendNoFlg, pageIndex);
		
		//取得总计
		if (queryTotalSum != null && queryTotalSum.getIsView().equals("T")) {
			StringBuffer groupSelectSql = new StringBuffer("");
			String groupSelect = queryTotalSum.getGroupSelect();
			String select = queryTotalSum.getSelect();
			String unionSelect = queryTotalSum.getUnionSelect();
			String viewIndex[] = queryTotalSum.getViewindex().split(",");
			
			groupSelectSql.append("SELECT ");
			groupSelectSql.append(groupSelect);
			groupSelectSql.append(" FROM(");
			queryInfo.setUserDefinedWhere(commonModel.getUserDefinedWhere());
			queryInfo.setUserDefinedSelect(select);
			queryInfo.setUserDefinedUnionSelect(unionSelect);			
			groupSelectSql.append(queryInfo.getSQL(request, userDefinedSearchCase, this.getMenuId()));
			groupSelectSql.append(") GROUPSELECT");
			
			ArrayList<ArrayList<String>> totalSumResult = new ArrayList<ArrayList<String>>();
			ArrayList<String> rowData = new ArrayList<String>();
			totalSumResult = BaseDAO.execSQL(groupSelectSql.toString(), dataSourceName, 0, 0);
			
			for(int i = 0; i < viewData.get(0).size(); i++) {
				rowData.add("");
			}	
			
			if (rowData.size() > 0) {
				for(int i = 0; i < viewIndex.length; i++) {
					if (appendNoFlg && viewData.size() > 1) {
						rowData.set(Integer.parseInt(viewIndex[i]), totalSumResult.get(0).get(i));
					} else {
						rowData.set(Integer.parseInt(viewIndex[i]) - 1, totalSumResult.get(0).get(i));
					}
				}
			}
			
			viewData.add(rowData);
		}
		
		return viewData;
	}
	
	private ArrayList<HashMap<String, String>> getYsTurnPageData(String sql, String dataSourceName, int iStart, int iEnd, boolean appendNoFlg) throws Exception {
		ArrayList<HashMap<String, String>> viewData = null;
		QueryInfoBean queryInfo = queryInfoMap.get(commonModel.getQueryName());
		
		viewData = BaseDAO.execYsSQL(sql, dataSourceName, iStart, iEnd, queryInfo, appendNoFlg);
		
		return viewData;
	}	
	
	private int getRecordCount(String sqlCount, String dataSourceName) throws Exception {
		int recordCount = 0;
		/*
		StringBuffer getCountSql = new StringBuffer("SELECT COUNT(*) ");
		
		getCountSql.append("FROM (");
		
		getCountSql.append(sql);
		getCountSql.append(") ACOUNT");
		*/
		ArrayList<ArrayList<String>> result = execQuery(sqlCount, dataSourceName);
		recordCount = Integer.parseInt(result.get(0).get(0));
		
		//totalSum
		
		return recordCount;
	}	
	
	private int getRecordCount(String sqlCount, String dataSourceName,String where) throws Exception {
		int recordCount = 0;
		sqlCount = sqlCount.replace("#", where);
		ArrayList<ArrayList<String>> result = execQuery(sqlCount, dataSourceName);
		recordCount = Integer.parseInt(result.get(0).get(0));
		
		return recordCount;
	}	
	
	private void getQueryInfo(String queryName, HttpServletRequest request) {
		try {
			//Element element = XmlUtil.getRootElement(QUERYDEFINEDRESOURCE);
			//Element element = XmlUtil.getRootElement(QUERYDEFINEDRESOURCE_PATH + queryName + XmlUtil.XMLSUFFIX);
			Element element = XmlUtil.getRootElement(QUERYDEFINEDRESOURCE_PATH + commonModel.getQueryFileName() + XmlUtil.XMLSUFFIX);
			if (element != null) {
				//�������ȡ��Ԫ���µ���Ԫ�����
				Iterator<Element> iter = element.elementIterator();
				while(iter.hasNext()){
				    Element el = (Element)iter.next();
				    QueryInfoBean queryInfo = new QueryInfoBean();
					//��ȡ������ơ�ֵ
					Iterator<Element> subIter = el.elementIterator();
					while(subIter.hasNext()){
						Element subEl = (Element)subIter.next();
					    String elName = subEl.getName().toLowerCase();
					    Attribute subIterAttr = null;
					    Iterator<Attribute> subIterAttrIter = null;
					    switch(elName) {
					    case "pool":
				    		queryInfo.setPoolName(subEl.getText());
					    	break;
					    case "name":
					    	subIterAttrIter = subEl.attributeIterator();
					    	if (subIterAttrIter.hasNext()) {
					    		subIterAttr = (Attribute)subEl.attributeIterator().next();
					    		queryInfo.setQueryName(subIterAttr.getText());
					    	}
					    	break;
					    case "where":
					    	QueryWhereBean qw = resolveWhere(subEl);
					    	queryInfo.setDefinedQueryWhere(qw);
					    	break;
					    case "select":
					    	queryInfo.setDefinedSelect(resolveSelect(subEl));
					    	break;
					    case "from":
					    	queryInfo.setDefinedFrom(resolveFrom(subEl));
					    	break;
					    case "orderby":
					    	queryInfo.setDefinedOrderBy(resolveOrderBy(subEl));
					    	break;
					    case "groupby":
					    	queryInfo.setDefinedGroupBy(resolveGroupBy(subEl));
					    	break;
					    case "having":
					    	queryInfo.setDefinedHaving(resolveHaving(subEl));
					    	break;
					    case "union":
					    	queryInfo.setDefinedUnion(resolveUnion(subEl));
					    	break;
					    case "power":
					    	queryInfo.setDefinedPower(resolvePower(subEl));
					    	break;
					    case "totxt":
					    	queryInfo.setToText(resolveText(subEl));
					    	break;
					    case "toexcel":
					    	queryInfo.setToExcel(resolveExcel(subEl));
					    	break;
					    case "topdf":
					    	queryInfo.setToPdf(resolvePdf(subEl));
					    	break;
					    case "toword":
					    	queryInfo.setToWord(resolveWord(subEl));
					    	break;
					    case "pagecount":
					    	queryInfo.setPageSum(resolvePageCount(subEl));
					    	break;
					    case "totalsum":
					    	queryInfo.setTotalSum(resolveTotalSum(subEl));
					    	break;
					    }
					}
					//��ѯ�����������ж�
					if (!queryInfo.getQueryName().equals("")) {
						queryInfoMap.put(queryInfo.getQueryName(), queryInfo);
					}
					if (queryInfo.getQueryName().equals(queryName)) {
						break;
					}
				}
			}

		}
		catch(Exception e) {
			System.out.println(e.toString());
		}
		
	}
	
	private QueryWhereBean resolveWhere(Element el) {
		QueryWhereBean qw = new QueryWhereBean();

		Iterator<Element> subIter = el.elementIterator();
		while(subIter.hasNext()){
			Element subEl = (Element)subIter.next();
			Iterator<Attribute> subAttr = subEl.attributeIterator();
			QueryWhereSubBean subWhere = new QueryWhereSubBean();
			while(subAttr.hasNext()){
				Attribute attrInfo = (Attribute)subAttr.next();
			    String attrName = attrInfo.getName().toLowerCase();
			    switch(attrName) {
			    case "name":
			    	subWhere.setName(attrInfo.getText());
			    	break;
			    case "des":
			    	subWhere.setDes(attrInfo.getText());
			    	break;
			    case "reqname":
			    	subWhere.setReqName(attrInfo.getText());
			    	break;
			    case "dtype":
			    	subWhere.setDType(attrInfo.getText());
			    	break;
			    case "oper":
			    	subWhere.setOper(attrInfo.getText());
			    	break;
			    case "lg":
			    	subWhere.setLg(attrInfo.getText());
			    	break;
			    case "bt":
			    	subWhere.setBt(attrInfo.getText());
			    	break;
			    }
			}
			qw.addSubWhere(subWhere);
		}
		
		return qw;
	}
	
	private QuerySelectBean resolveSelect(Element el) {
		QuerySelectBean qs = new QuerySelectBean();

		Iterator<Element> subIter = el.elementIterator();
		while(subIter.hasNext()){
			Element subEl = (Element)subIter.next();
			Iterator<Attribute> subAttr = subEl.attributeIterator();
			QuerySelectSubBean subSelect = new QuerySelectSubBean();
			while(subAttr.hasNext()){
				Attribute attrInfo = (Attribute)subAttr.next();
			    String attrName = attrInfo.getName().toLowerCase();
			    switch(attrName) {
			    case "name":
			    	subSelect.setName(attrInfo.getText());
			    	break;
			    case "alias":
			    	subSelect.setAlias(attrInfo.getText());
			    	break;
			    case "ctype":
			    	subSelect.setCType(attrInfo.getText());
			    	break;
			    }
			}
			qs.addSubSelect(subSelect);
		}
		
		return qs;
	}
	
	private QueryFromBean resolveFrom(Element el) {
		QueryFromBean qf = new QueryFromBean();
    	qf.setCaseInfo(el.getText());
		
		return qf;
	}
	
	private QueryOrderByBean resolveOrderBy(Element el) {
		QueryOrderByBean qo = new QueryOrderByBean();
		qo.setCaseInfo(el.getText());
		
		return qo;
	}
	
	private QueryGroupByBean resolveGroupBy(Element el) {
		QueryGroupByBean qg = new QueryGroupByBean();
		qg.setCaseInfo(el.getText());
		
		return qg;
	}
	
	private QueryHavingBean resolveHaving(Element el) {
		QueryHavingBean qh = new QueryHavingBean();
		qh.setCaseInfo(el.getText());
		
		return qh;
	}	
	
	private QueryUnionBean resolveUnion(Element el) {
		QueryUnionBean qu = new QueryUnionBean();

		Iterator<Element> subIter = el.elementIterator();
	    while(subIter.hasNext()){
			Element subEl = (Element)subIter.next();
			String elName = subEl.getName().toLowerCase();
		    switch(elName) {
		    case "where":
		    	qu.setQueryWhere(resolveWhere(subEl));
		    	break;
		    case "select":
		    	qu.setQuerySelect(resolveSelect(subEl));
		    	break;
		    case "from":
		    	qu.setQueryFrom(resolveFrom(subEl));
		    	break;
		    case "groupby":
	    		qu.setQueryGroupBy(resolveGroupBy(subEl));
		    	break;
		    case "having":
		    	qu.setQueryHavingBean(resolveHaving(subEl));
		    	break;
		    }
		}
		
		return qu;
	}
	
	private QueryPowerBean resolvePower(Element el) {
		QueryPowerBean qp = new QueryPowerBean();
		Iterator<Element> subIter = el.elementIterator();
		while(subIter.hasNext()){
			Element subEl = (Element)subIter.next();
			String elName = subEl.getName().toLowerCase();
		    switch(elName) {
		    case "menuid":
		    	qp.setMenuId(subEl.getText());
		    	break;
		    case "deptfield":
		    	qp.setDeptField(subEl.getText());
		    	break;		
		    }
		}
		
		return qp;
	}
	
	private QueryToTextBean resolveText(Element el) {
		QueryToTextBean qt = new QueryToTextBean();
		qt.setCaseInfo(el.getText());
		
		return qt;
	}	
	
	private QueryToExcelBean resolveExcel(Element el) {
		QueryToExcelBean qe = new QueryToExcelBean();
		Iterator<Element> subIter = el.elementIterator();
		while(subIter.hasNext()){
			Element subEl = (Element)subIter.next();
			String elName = subEl.getName().toLowerCase();
		    switch(elName) {
		    case "model":
		    	qe.setModel(subEl.getText());
		    	break;
		    case "firstrow":
		    	qe.setFirstRow(subEl.getText());
		    	break;
		    case "firstcol":
		    	qe.setFirstCol(subEl.getText());
		    	break;
		    case "dataIndex":
		    	qe.setDataIndex(subEl.getText());
		    	break;
		    }
		}
		
		return qe;
	}	
	
	private QueryToPdfBean resolvePdf(Element el) {
		QueryToPdfBean qp = new QueryToPdfBean();
		Iterator<Element> subIter = el.elementIterator();
		while(subIter.hasNext()){
			Element subEl = (Element)subIter.next();
			String elName = subEl.getName().toLowerCase();
		    switch(elName) {
		    case "model":
		    	qp.setModel(subEl.getText());
		    	break;
		    case "dataindex":
		    	qp.setDataIndex(subEl.getText());
		    	break;
		    }
		}
		
		return qp;
	}	

	private QueryToWordBean resolveWord(Element el) {
		QueryToWordBean qw = new QueryToWordBean();
		Iterator<Element> subIter = el.elementIterator();
		while(subIter.hasNext()){
			Element subEl = (Element)subIter.next();
			String elName = subEl.getName().toLowerCase();
		    switch(elName) {
		    case "model":
		    	qw.setModel(subEl.getText());
		    	break;
		    case "dataindex":
		    	qw.setDataIndex(subEl.getText());
		    	break;
		    }
		}
		
		return qw;
	}	

	private QueryPageSumBean resolvePageCount(Element el) {
		QueryPageSumBean qps = new QueryPageSumBean();
		Iterator<Element> subIter = el.elementIterator();
		while(subIter.hasNext()){
			Element subEl = (Element)subIter.next();
			String elName = subEl.getName().toLowerCase();
		    switch(elName) {
		    case "isview":
		    	qps.setIsView(subEl.getText());
		    	break;
		    case "dataindex":
		    	qps.setDataIndex(subEl.getText());
		    	break;
		    }
		}
		
		return qps;
	}
	
	private QueryTotalSumBean resolveTotalSum(Element el) {
		QueryTotalSumBean qps = new QueryTotalSumBean();
		Iterator<Element> subIter = el.elementIterator();
		while(subIter.hasNext()){
			Element subEl = (Element)subIter.next();
			String elName = subEl.getName().toLowerCase();
		    switch(elName) {
		    case "isview":
		    	qps.setIsView(subEl.getText());
		    	break;
		    case "viewindex":
		    	qps.setViewIndex(subEl.getText());
		    	break;
		    case "groupselect":
		    	qps.setGroupSelect(subEl.getText());
		    	break;
		    case "select":
		    	qps.setSelect(subEl.getText());
		    	break;
		    case "unionselect":
		    	qps.setUnionSelect(subEl.getText());
		    	break;
		    }
		}
		
		return qps;
	}		
	
	public static String processDataType(String value, String dType, String oprStr) {
		StringBuffer sql = new StringBuffer("");
		if (!dType.equals("int")) {
			sql.append(" ");
			if (oprStr.equals("")) {
				sql.append("'");
				sql.append(value);
				sql.append("'");
			} else {
				switch(oprStr) {
				case "like":
					sql.append("like '%");
					sql.append(value);
					sql.append("%'");
					break;
				case "llike":
					sql.append("like '");
					sql.append("%");
					sql.append(value);
					sql.append("'");
					break;
				case "rlike":
					sql.append("like '");
					sql.append(value);
					sql.append("%'");
					break;
				case "in":
					sql.append("in (");
					String valueList[] = value.split(",");
					for (int i = 0; i < valueList.length; i++) {
						if (i > 0) {
							sql.append(", ");
						}
						sql.append("'");
						sql.append(valueList[i]);
						sql.append("'");
					}
					sql.append(")");
				}
			}
			sql.append(" ");
		} else {
			if (value.equals("")) {
				value = "-1";
			}
			if (oprStr.equals("in")) {
				sql.append("in (");
				if (dType.equals("int")) {
					sql.append(value);
				}
				sql.append(")");
			} else {
				sql.append(value);
			}
			sql.append(" ");
		}
		
		return sql.toString();
	}

	public static int getAdminRoleidCount(HttpServletRequest request, String userId, String adminRole) throws Exception {
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		
		BaseModel baseModel = new BaseModel();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		baseModel.setQueryFileName("/common/mainframequery");
		baseModel.setQueryName("mainframequery_getadminroleidcount");
		
		BaseQuery baseQuery = new BaseQuery(request, baseModel);
		userDefinedSearchCase.put("userid", userId);
		userDefinedSearchCase.put("roletype", adminRole);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		result = baseQuery.getFullData();
		
		return Integer.parseInt(result.get(0).get(0));
	}
	
	public static String getContent(String fileName, String key) {

		
		String rtnValue = key;
        Locale locale = new Locale("zh", "CN");
        
        try {
	        ResourceBundle resb = ResourceBundle.getBundle(fileName, locale);
	
	        rtnValue = resb.getString(key);
        }
        catch(Exception e) {
        	System.out.println(e.getMessage());
        }
		return rtnValue;
	 
	}	
	

}
