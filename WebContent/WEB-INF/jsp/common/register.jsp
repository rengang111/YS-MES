<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<object id="test" classid="clsid:BDEADEF0-C265-11D0-BCED-00A0C90AB50F"  
	  width="0px" height="0px"
	  codebase="${SystemUrl}#version=12,0,4518,1014"
	>
	</object>
</html>
<div style="position:absolute; top:50%;margin-top:-10px;margin-left:20px;">
<button type="button" id="return" class="DTTT_button" onClick="doReturn();"	style="height:25px;">关闭</button>
</div>
<script>
	function doReturn() {
		var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
		parent.layer.close(index); //执行关闭
	}
</script>