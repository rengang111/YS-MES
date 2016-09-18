package com.ys.util.basequery;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.ys.system.action.model.common.SqlInfo;

public class QueryInfoBean {
	private String poolName = "";
	private String queryName = "";
	private String userDefinedWhere = "";
	private String userDefinedSelect = "";
	private String userDefinedUnionSelect = "";
	private boolean isGetTotalSumFlg = false;
	private QueryWhereBean definedQueryWhere = new QueryWhereBean();
	private QuerySelectBean definedSelect = new QuerySelectBean();
	private QueryFromBean definedFrom = new QueryFromBean();
	private QueryOrderByBean definedOrderBy = new QueryOrderByBean();
	private QueryGroupByBean definedGroupBy = new QueryGroupByBean();
	private QueryHavingBean definedHaving = new QueryHavingBean();
	private QueryUnionBean definedUnion = new QueryUnionBean();
	private QueryPowerBean definedPower = new QueryPowerBean();
	private QueryToTextBean toText = new QueryToTextBean();
	private QueryToExcelBean toExcel = new QueryToExcelBean();
	private QueryToWordBean toWord = new QueryToWordBean();
	private QueryToPdfBean toPdf = new QueryToPdfBean();
	private QueryPageSumBean pageSum = new QueryPageSumBean();
	private QueryTotalSumBean totalSum = new QueryTotalSumBean();
	

	public String getPoolName() {
		return this.poolName;
	}
	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}
	
	public String getQueryName() {
		return this.queryName;
	}
	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}
	
	public String getUserDefinedWhere() {
		return this.userDefinedWhere;
	}
	public void setUserDefinedWhere(String userDefinedWhere) {
		this.userDefinedWhere = userDefinedWhere;
	}
	
	public String getUserDefinedSelect() {
		return this.userDefinedSelect;
	}
	public void setUserDefinedSelect(String userDefinedSelect) {
		this.userDefinedSelect = userDefinedSelect;
	}	
	
	public String getUserDefinedUnionSelect() {
		return this.userDefinedUnionSelect;
	}
	public void setUserDefinedUnionSelect(String userDefinedUnionSelect) {
		this.userDefinedUnionSelect = userDefinedUnionSelect;
	}		
	
	public boolean getIsGetTotalSumFlg() {
		return this.isGetTotalSumFlg;
	}
	public void setIsGetTotalSumFlg(boolean isGetTotalSumFlg) {
		this.isGetTotalSumFlg = isGetTotalSumFlg;
	}
	
	public QueryWhereBean getDefinedQueryWhere() {
		return this.definedQueryWhere;
	}
	public void setDefinedQueryWhere(QueryWhereBean definedQueryWhere) {
		this.definedQueryWhere = definedQueryWhere;
	}
	
	public QuerySelectBean getDefinedSelect() {
		return this.definedSelect;
	}
	public void setDefinedSelect(QuerySelectBean definedSelect) {
		this.definedSelect = definedSelect;
	}
	
	public QueryFromBean getDefinedFrom() {
		return this.definedFrom;
	}
	public void setDefinedFrom(QueryFromBean definedFrom) {
		this.definedFrom = definedFrom;
	}
	
	public QueryOrderByBean getDefinedOrderBy() {
		return this.definedOrderBy;
	}
	public void setDefinedOrderBy(QueryOrderByBean definedOrderBy) {
		this.definedOrderBy = definedOrderBy;
	}
	
	public QueryGroupByBean getDefinedGroupBy() {
		return this.definedGroupBy;
	}
	public void setDefinedGroupBy(QueryGroupByBean definedGroupBy) {
		this.definedGroupBy = definedGroupBy;
	}
	
	public QueryHavingBean getDefinedHaving() {
		return this.definedHaving;
	}
	public void setDefinedHaving(QueryHavingBean definedHaving) {
		this.definedHaving = definedHaving;
	}
	
	public QueryUnionBean getDefinedUnion() {
		return this.definedUnion;
	}
	public void setDefinedUnion(QueryUnionBean definedUnion) {
		this.definedUnion = definedUnion;
	}
	
	public QueryPowerBean getDefinedPower() {
		return this.definedPower;
	}
	public void setDefinedPower(QueryPowerBean definedPower) {
		this.definedPower = definedPower;
	}
	
	public QueryToTextBean getToText() {
		return this.toText;
	}
	public void setToText(QueryToTextBean toText) {
		this.toText = toText;
	}
	
	public QueryToExcelBean getToExcel() {
		return this.toExcel;
	}
	public void setToExcel(QueryToExcelBean toExcel) {
		this.toExcel = toExcel;
	}
	
	public QueryToWordBean getToWord() {
		return this.toWord;
	}
	public void setToWord(QueryToWordBean toWord) {
		this.toWord = toWord;
	}
	
	public QueryToPdfBean getToPdf() {
		return this.toPdf;
	}
	public void setToPdf(QueryToPdfBean toPdf) {
		this.toPdf = toPdf;
	}
	
	public QueryPageSumBean getPageSum() {
		return this.pageSum;
	}
	public void setPageSum(QueryPageSumBean pageSum) {
		this.pageSum = pageSum;
	}	

	public QueryTotalSumBean getTotalSum() {
		return this.totalSum;
	}
	public void setTotalSum(QueryTotalSumBean totalSum) {
		this.totalSum = totalSum;
	}	

	public SqlInfo getSQL(HttpServletRequest request, HashMap<String, String> userDefinedSearchCase, String menuId) throws Exception {
		SqlInfo sqlInfo = new SqlInfo();
		
		StringBuffer sql = new StringBuffer("");
		StringBuffer sqlCount = new StringBuffer("");
		
		String selectSql = "";
		String selectSqlCount = "SELECT COUNT(COUNTNUM) FROM (SELECT 1 AS COUNTNUM ";
		
		String powerSql = definedPower.getSql(request, menuId);
		
		String unionSql = definedUnion.getSql(request, userDefinedUnionSelect, powerSql, userDefinedSearchCase, isGetTotalSumFlg);
		
		definedQueryWhere.setRequest(request);
		String definedQueryWhereSql = definedQueryWhere.getSql(userDefinedSearchCase);
		
		String definedFromSql = definedFrom.getSql();

		if (userDefinedSelect.equals("")) {
			selectSql = definedSelect.getSql();
		} else {
			sql.append("SELECT ");
			selectSql = userDefinedSelect;
			
		}
		sql.append(selectSql);
		sqlCount.append(selectSqlCount);
		sql.append(" ");
		sqlCount.append(" ");
		sql.append(definedFromSql);
		sqlCount.append(definedFromSql);
		sql.append(" ");
		sqlCount.append(" ");

		String tempSql = sql.toString().trim();
		if (tempSql.substring(tempSql.length() - 1, tempSql.length()).equals("$")) {
			sql.replace(tempSql.length() - 1, tempSql.length(), "");
			if (!definedQueryWhereSql.equals("") || !userDefinedWhere.equals("")) {
				sql.append(" WHERE ");
				sqlCount.append(" WHERE ");
			}
		} else {
			if (!definedQueryWhereSql.equals("") || !userDefinedWhere.equals("")) {
				if (tempSql.toLowerCase().indexOf("where") >= 0) {
					sql.append(" AND ");
					sqlCount.append(" AND ");
				} else {
					sql.append(" WHERE ");
					sqlCount.append(" WHERE ");
				}
			}
		}
		
		sql.append(definedQueryWhereSql);
		sqlCount.append(definedQueryWhereSql);
		
		if(!userDefinedWhere.equals("")) {
			if (!definedQueryWhereSql.equals("")) {
				sql.append(" AND ");
				sqlCount.append(" AND ");
			}
			sql.append(userDefinedWhere);
			sqlCount.append(userDefinedWhere);
		}
		
		sql.append(powerSql);
		sqlCount.append(powerSql);
		
		if (!isGetTotalSumFlg) {
			sql.append(" ");
			sqlCount.append(" ");
			sql.append(definedGroupBy.getSql());
			sqlCount.append(definedGroupBy.getSql());
			sql.append(" ");
			sqlCount.append(" ");
			sql.append(definedHaving.getSql());
			sqlCount.append(definedHaving.getSql());
			sql.append(" ");
			sqlCount.append(" ");
		}
		if (unionSql.equals("")) {
			if (!isGetTotalSumFlg) {
				sql.append(definedOrderBy.getSql());
			}
		} else {
			sql.append(" UNION ");
			sqlCount.append(" UNION ");
			sql.append(unionSql);
			sqlCount.append(unionSql);
			sql.append(" ");
			sqlCount.append(" ");
		}

		sqlCount.append(") AS SQLCOUNT");
		sqlInfo.setSql(sql.toString());
		sqlInfo.setSqlCount(sqlCount.toString());
		
		System.out.println(sql.toString());
		
		return sqlInfo;
	}

	public static String addUserDefinedSort(String sql, String orderByList) {
		
		StringBuffer userSql = new StringBuffer(sql);
		
		if (!orderByList.equals("")) {
			userSql.insert(0, "SELECT * FROM (");
			userSql.append(") BSORT ORDER BY ");
			userSql.append(orderByList);
		}
		
		return userSql.toString();
	}
}
