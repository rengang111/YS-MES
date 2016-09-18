<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" %>
<%@ include file="../common/common.jsp"%>
<script>
	if (window.opener) {
		closeOpenerWindowChain(window.opener);
		self.close();
	} else {
		self.top.location = "${ctx}";
		//window.parent.location = "${ctx}";
		
	}
	function closeOpenerWindowChain(win) {

		if (win.self.opener) {
			closeOpenerWindowChain(win.self.opener);
			win.self.close();
		} else {
			win.self.top.location = "${ctx}";
		}
	}
</Script> 
