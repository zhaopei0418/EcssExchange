<%@page contentType="text/html;charset=UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <title>河南跨境贸易导入系统</title>
    <%@ include file="commons/page/taglibs.jsp"%>
    <base href="<%=basePath%>">
    	<%@ include file="commons/page/meta_cneport.jsp"%>
    
    	<link rel="stylesheet" type="text/css" href="${base}/commons/styles/login.css" />
    	
    	<object
			classid="clsid:16F2448E-8C16-11D1-9A11-0080C8E1561F" height=14 id=appOcx style="LEFT: 0px; TOP: 0px; VISIBILITY: hidden" width=14 border="0">
		</object>
		
    	<script type="text/javascript">
			var __ctxPath = "<%=request.getContextPath() %>";
		</script>
		<link rel="stylesheet" href="swf.css" type="text/css" />
		<link rel="stylesheet" type="text/css" href="commons/scripts/ext/resources/css/ext-all.css" />
		<link rel="stylesheet" type="text/css" href="commons/styles/login.css" />
		<script type="text/javascript" src="commons/scripts/ext/ext-base.js"></script>
		<script type="text/javascript" src="commons/scripts/ext/ext-all.js"></script>
		<script type="text/javascript" src="commons/scripts/ext/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="${base}/commons/scripts/ukey.js"></script>	
		<script type="text/javascript" src="${base}/commons/scripts/ux/ImageButton.js"></script>
	    <script type="text/javascript" src="${base}/commons/scripts/AppLogin.js"></script>	
		<script type="text/javascript">
		Ext.onReady(function(){
			new App.login.LoginWindow().show();
		});
	</script>
		
	<style type="text/css">
	
		.log_user {
		     background: url(${base}/commons/images/user.png) no-repeat 2px 2px !important;
		}
		.log_key {
		     background: url(${base}/commons/images/key.png) no-repeat 2px 2px !important;
		}
		.log_user,.log_key {
				background-color: #FFFFFF !important;
				padding-left: 20px !important;
				font-size: 12px !important;
		}
		#dddd{
			width:100%;
			height:100%;
	    	overflow:hidden;	
		}
		
	    #yyyyyy{
		    width:100%;
			height:100%;
	    }
		
		
	</style>
</head>
<body > 

    <OBJECT classid="clsid:2737432E-8F80-465A-9BE8-9936DC5CAC30" height=14 id=SCAClient style="LEFT: 0px; TOP: 0px; VISIBILITY: hidden" width=14 border="0">
		<PARAM NAME="_ExtentX" VALUE="370">
		<PARAM NAME="_ExtentY" VALUE="370">
   </OBJECT>

     <div id="dddd" >
		
	 </div>

</body>

</html>
