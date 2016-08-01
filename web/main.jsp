<%@page import="com.cneport.ecss.user.User" contentType="text/html;charset=UTF-8"%>
<%
    if (session.getAttribute("user") == null) {
        response.sendRedirect("index.jsp");
    }
%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%@ include file="commons/page/taglibs.jsp"%>
<html>
	<head>
        <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE6" />
        <base href="<%=basePath%>">
        <%--<%@ include file="commons/page/meta_cneport.jsp"%>
        --%><link rel="stylesheet" type="text/css" href="${base}/commons/scripts/ext/resources/css/ext-all.css" />
        <script type="text/javascript">
        	var gIcNo = '${user.icNo}';
        </script>
		<script type="text/javascript" src="${base}/commons/scripts/ext/ext-base.js"></script>
		<script type="text/javascript" src="${base}/commons/scripts/ext/ext-all.js"></script>
		<script type="text/javascript" src="${base}/commons/scripts/ext/ext-lang-zh_CN.js"></script>
        <link rel="stylesheet" type="text/css" href="${base}/commons/styles/main.css" />
        <link rel="stylesheet" type="text/css" href="${base}/commons/scripts/jquery/tree.css" />
        <script type="text/javascript" src="${base}/commons/scripts/jquery/jquery-1.8.2.js"></script>
        <script type="text/javascript" src="${base}/App.js"></script>
        <script type="text/javascript" src="${base}/commons/scripts/AppLogin.js"></script>
        <script type="text/javascript" src="${base}/commons/scripts/password.js"></script>
        <script type="text/javascript" src="${base}/commons/scripts/lib/BaseComboBox.js"></script>
        <script type="text/javascript" src="${base}/commons/scripts/lib/FieldReadOnlyPlugIn.js"></script>
        <script type="text/javascript" src="${base}/commons/scripts/lib/VTypes.js"></script>
        <script type="text/javascript" src="${base}/commons/scripts/lib/SyisInputPanel.js"></script>
        <script type="text/javascript" src="${base}/commons/scripts/lib/SyisQueryPanel.js"></script>
        <script type="text/javascript" src="${base}/commons/scripts/lib/SyisModifyVeWin.js"></script>
        <script type="text/javascript" src="${base}/commons/scripts/lib/SyisConfirmBillWin.js"></script>
        <script type="text/javascript" src="${base}/commons/scripts/lib/SyisWin.js"></script>
        <script type="text/javascript" src="${base}/commons/scripts/lib/QuickMsg.js"></script>
		<script type="text/javascript" src="swfupload.js"></script>
		<script type="text/javascript" src="uploaderPanel.js"></script>
		<link rel="stylesheet" href="swf.css" type="text/css" />
		<link rel="stylesheet" type="text/css" href="commons/scripts/ext/resources/css/ext-all.css" />
        <script type="text/javascript" src="${base}/commons/scripts/ChangePasswordDialog.js"></script>
        <script type="text/javascript" src="${base}/commons/scripts/ukey.js"></script>
        <script type="text/javascript" src="${base}/commons/scripts/base64.js"></script>
         <link rel="stylesheet" type="text/css" href="${base}/commons/scripts/ux/fileuploadfield/css/fileuploadfield.css" />
        <script type="text/javascript" src="${base}/commons/scripts/ux/fileuploadfield/FileUploadField.js"></script>
       
       
       
    	<title>河南跨境贸易服务系统</title>
    	<script type="text/javascript">
			// ajax回调函数处理系统退出        
		    Ext.Ajax.on('requestcomplete',checkSessionStatus,this);
				function checkSessionStatus(conn,response,options){
				if(response.responseText==''){
					Ext.Msg.progress();
					t=0;
					m=5;
					var task = {
						run:function(){
							Ext.Msg.updateProgress(t,'','页面过期，'+m+'秒后跳转到登陆页面。');
							t+=0.2;
							m-=1;
							if(t>1){
								window.location.href="quit.jsp";
							}
						},interval:1000
					}
					Ext.TaskMgr.start(task);
				}
		    }
			
	    	var __ctxPath="<%=request.getContextPath() %>";
			<%if(session.getAttribute("user") != null){%>
				var userName = "<%=((User)session.getAttribute("user")).getUserName()%>";
			<%}%>
			
	    	Ext.onReady(function(){
	    		App.init();
	    		
	    		Ext.getDoc().on('keydown', function(e){
					if(e.getKey() == e.BACKSPACE && e.getTarget().type == 'text'){
						if(e.getTarget().readOnly){
							e.preventDefault();
						}
					}else if(e.getKey() == e.BACKSPACE && e.getTarget().type == 'textarea'){
						if(e.getTarget().readOnly){
							e.preventDefault();
						}
					}else if(e.getKey() == e.BACKSPACE && e.getTarget().type == 'password'){
						if(e.getTarget().readOnly){
							e.preventDefault();
						}
					}else if(e.getKey() == e.BACKSPACE){
						e.preventDefault();
					}
				}); //禁用Backspace作为后退快捷键

			});
		</script>
	</head>
	<%--<body style="overflow-x: auto;">
		<div id="content"></div> 
	</body>
	
		--%><body style="overflow: hidden;">
		<div id="content"></div> 
	</body>
	
	        <object
			classid="clsid:16F2448E-8C16-11D1-9A11-0080C8E1561F" height=14 id=appOcx style="LEFT: 0px; TOP: 0px; VISIBILITY: hidden" width=14 border="0">
		</object>
		 <OBJECT classid="clsid:2737432E-8F80-465A-9BE8-9936DC5CAC30" height=14 id=SCAClient style="LEFT: 0px; TOP: 0px; VISIBILITY: hidden" width=14 border="0">
		<PARAM NAME="_ExtentX" VALUE="370">
		<PARAM NAME="_ExtentY" VALUE="370">
   </OBJECT>
	
</html>