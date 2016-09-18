package com.ys.util.basequery;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

public class QueryUnionBean {
	private QueryWhereBean queryWhere = new QueryWhereBean();
	private QuerySelectBean querySelect = new QuerySelectBean();
	private QueryFromBean queryFrom = new QueryFromBean();
	private QueryGroupByBean queryGroupBy = new QueryGroupByBean();
	private QueryHavingBean queryHaving = new QueryHavingBean();
	
	public QueryWhereBean getQueryWhere() {
		return this.queryWhere;
	}
	public void setQueryWhere(QueryWhereBean queryWhere) {
		this.queryWhere = queryWhere;
	}
	
	public QuerySelectBean getQuerySelect() {
		return this.querySelect;
	}
	public void setQuerySelect(QuerySelectBean querySelect) {
		this.querySelect = querySelect;
	}
	
	public QueryFromBean getQueryFrom() {
		return this.queryFrom;
	}
	public void setQueryFrom(QueryFromBean queryFrom) {
		this.queryFrom = queryFrom;
	}

	public QueryGroupByBean getQueryGroupBy() {
		return this.queryGroupBy;
	}
	public void setQueryGroupBy(QueryGroupByBean queryGroupBy) {
		this.queryGroupBy = queryGroupBy;
	}
	
	public QueryHavingBean getQueryHavingBean() {
		return this.queryHaving;
	}
	public void setQueryHavingBean(QueryHavingBean queryHaving) {
		this.queryHaving = queryHaving;
	}	
	
	public String getSql(HttpServletRequest request, String userDefinedUnionSelect, String powerSql, HashMap<String, String> userDefinedSearchCase, boolean isGetTotalSumFlg) {
		StringBuffer sql = new StringBuffer("");
		if (userDefinedUnionSelect.equals("")) {
			sql.append(querySelect.getSql());
		} else {
			sql.append("SELECT ");
			sql.append(userDefinedUnionSelect);
		}
		sql.append(queryFrom.getSql());
		queryWhere.setRequest(request);
		sql.append(queryWhere.getSql(userDefinedSearchCase));
		if(!sql.toString().equals("")) {
			sql.append(powerSql);
		}
		if (!isGetTotalSumFlg) {
			sql.append(queryGroupBy.getSql());
			sql.append(queryHaving.getSql());
		}
		
		return sql.toString();
	}
}
