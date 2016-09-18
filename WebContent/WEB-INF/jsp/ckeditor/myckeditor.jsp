<%@ page language="java" pageEncoding="UTF-8"%>  
<!DOCTYPE html>
<html>
<%@ include file="../common/common.jsp"%>
<script type="text/javascript" src="${ctx}/jsp/ckeditor/ckeditor.js"></script>
<head>
	<meta charset="utf-8">
	<meta name="robots" content="noindex, nofollow">
	<title>Classic editor replacing a textarea</title>
</head>

<body>
	<form action="${ctx}/saveck" method="post" >

	<textarea cols="80" id="editor1" name="editor1" rows="10">		&lt;h1&gt;&lt;img alt="Saturn V carrying Apollo 11" class="right" src="http://sdk.ckeditor.com/samples/assets/sample.jpg"/&gt; Apollo 11&lt;/h1&gt;		&lt;p&gt;&lt;strong&gt;Apollo 11&lt;/strong&gt; was the spaceflight that landed the first humans, Americans &lt;a href="http://en.wikipedia.org/wiki/Neil_Armstrong"&gt;Neil		Armstrong&lt;/a&gt; and		&lt;a href="http://en.wikipedia.org/wiki/Buzz_Aldrin"&gt;Buzz Aldrin&lt;/a&gt;, on the Moon on July 20, 1969, at 20:18 UTC. Armstrong became the first to step onto the		lunar surface 6 hours		later on July 21 at 02:56 UTC.&lt;/p&gt;		&lt;p&gt;Armstrong spent about &lt;s&gt;three and a half&lt;/s&gt; two and a half hours outside the spacecraft, Aldrin slightly less; and together they collected 47.5 pounds (21.5&amp;nbsp;kg)		of lunar		material for return to Earth. A third member of the mission, &lt;a href="http://en.wikipedia.org/wiki/Michael_Collins_(astronaut)"&gt;Michael Collins&lt;/a&gt;, piloted		the		&lt;a href="http://en.wikipedia.org/wiki/Apollo_Command/Service_Module"&gt;command&lt;/a&gt; spacecraft alone in lunar orbit until Armstrong and Aldrin returned to it for		the trip back to		Earth.&lt;/p&gt;
	</textarea>

		<p>
			<input type="button" value="submit" onClick="doSave();">
		</p>
	</form>

	<script>
		CKEDITOR.replace( 'editor1' );
	</script>
</body>

<script>
function doSave(){     
    var content123 = CKEDITOR.instances.editor1.getData();//$("#editor1").html();
	console.log(content123);
	
    $.ajax({
        type:"POST",
        url:"${ctx}/saveck",
        data:content123,
		contentType : 'application/json',
		dataType : 'json',        
        success: function(data){

		},
        error : function(XMLHttpRequest, textStatus, errorThrown) {  
			
        } 		    
    });     
}   

</script>
</html>